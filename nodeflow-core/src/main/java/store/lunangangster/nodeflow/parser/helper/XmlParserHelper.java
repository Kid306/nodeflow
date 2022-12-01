package store.lunangangster.nodeflow.parser.helper;

import cn.hutool.core.collection.CollUtil;
import store.lunangangster.nodeflow.builder.ChainBuilder;
import store.lunangangster.nodeflow.builder.entity.ChainProp;
import store.lunangangster.nodeflow.builder.entity.NodeProp;
import store.lunangangster.nodeflow.common.BaseConstant;
import store.lunangangster.nodeflow.exception.ChainsLabelNotFoundException;
import store.lunangangster.nodeflow.exception.NodeClassNotFoundException;
import store.lunangangster.nodeflow.exception.NodesLabelNotFoundException;
import store.lunangangster.nodeflow.rt.FlowBus;
import store.lunangangster.nodeflow.rt.element.Chain;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import org.dom4j.Document;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 解析基础XML的解析器工具类
 *
 * @author cwk
 * @version 1.0
 */
public class XmlParserHelper {
	private static final Logger log = LoggerFactory.getLogger(XmlParserHelper.class);

	private static final Set<String> XML_SET = new HashSet<>();


	private XmlParserHelper() {}

	/**
	 * 解析XML中的&lt;nodes&gt;&lt;/nodes&gt;部分
	 */
	public static void parseNodes(List<Document> documents) throws NodeClassNotFoundException {
		for (Document document : documents) {
			Element root = document.getRootElement();
			Element nodes = root.element(BaseConstant.NODES);
			if (nodes == null) {
				throw new NodesLabelNotFoundException("nodes label is not found in Rule Source");
			}
			Iterator<Element> nodeIter = nodes.elementIterator(BaseConstant.NODE);
			while (nodeIter.hasNext()) {
				Element node = nodeIter.next();
				String nodeId = node.attribute(BaseConstant.ID).getText();
				String className = node.attribute(BaseConstant.CLASS).getText();
				NodeProp nodeProp = new NodeProp().setId(nodeId).setClassName(className);
				ParserHelper.buildNode(nodeProp);
			}
		}
	}

	/**
	 * <p>解析XML中的&lt;chains&gt;&lt;/chains&gt;部分</p>
	 * <p>使用默认的集合进行去重</p>
	 * @param documents   待解析<code>Document</code>对象集合
	 * @param chainParser 用于解析单个chain的方法
	 */
	public static void parseChains(List<Document> documents, Function<Element, ChainProp> chainParser)
			throws ChainsLabelNotFoundException {
		parseChains(documents, chainParser, XML_SET);
	}

	/**
	 * <p>解析XML中的&lt;chains&gt;&lt;/chains&gt;部分</p>
	 * <p>
	 *   由于对于Chain的解析代价可能比较大，所以每次解析前都会进行重复性判断，而不是像Node那样可以直接覆盖，从而减少性能消耗。
	 *   由此产生的结论是：重复定义的chain，优先定义的优先生效
	 * </p>
	 * @param documents   待解析<code>Document</code>对象集合
	 * @param chainParser 用于解析单个chain的方法
	 * @param nameSet     去重集合
	 */
	public static void parseChains(List<Document> documents, Function<Element, ChainProp> chainParser, Set<String> nameSet)
			throws ChainsLabelNotFoundException {
		if (CollUtil.isEmpty(documents)) {
			return;
		}
		// 首先先获取所有chainId，预处理一遍，防止循环依赖问题
		for (Document document : documents) {
			Element chains = document.getRootElement().element(BaseConstant.CHAINS);
			if (chains == null) {
				throw new ChainsLabelNotFoundException("chains label is not found in Rule Source");
			}
			// 进行chainId去重
			chains.elements(BaseConstant.CHAIN).forEach(chain -> {
				String chainId = chain.attribute(BaseConstant.ID).getText();
				if (!nameSet.add(chainId)) {
					log.info("chain({}) is duplicate", chainId);
				}
			});
			// 向FlowBus中放入占位Chain
			nameSet.forEach(chainId -> FlowBus.addChain(Chain.emptyChain(chainId)));
		}
		for (Document document : documents) {
			Element root = document.getRootElement();
			Element chains = root.element(BaseConstant.CHAINS);
			Iterator<Element> chainIter = chains.elementIterator(BaseConstant.CHAIN);
			while (chainIter.hasNext()) {
				Element chain = chainIter.next();
				// chainId为null，则不参与解析
				if (chain.attribute(BaseConstant.ID) == null) {
					continue;
				}
				String chainId = chain.attribute(BaseConstant.ID).getText();
				// 第一次匹配的解析，后续重复的不再解析
				if (nameSet.remove(chainId)) {
					// 函数调用
					ChainProp chainProp = chainParser.apply(chain);
					// 向FlowBus中添加Chain，注意不是直接添加，而是一个属性copy过程，目的就是为了保证占位Chain的正确指向
					ChainBuilder.start().assign().id(chainProp.getId()).flowList(chainProp.getFlowList()).build();
				}
			}
		}
	}
}
