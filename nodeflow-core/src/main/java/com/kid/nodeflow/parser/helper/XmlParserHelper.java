package com.kid.nodeflow.parser.helper;

import static com.kid.nodeflow.common.BaseConstant.CHAIN;
import static com.kid.nodeflow.common.BaseConstant.CHAINS;
import static com.kid.nodeflow.common.BaseConstant.CLASS;
import static com.kid.nodeflow.common.BaseConstant.ID;
import static com.kid.nodeflow.common.BaseConstant.NODE;
import static com.kid.nodeflow.common.BaseConstant.NODES;

import cn.hutool.core.collection.CollUtil;
import com.kid.nodeflow.builder.entity.NodeProp;
import com.kid.nodeflow.exception.NodeClassNotFoundException;
import com.kid.nodeflow.exception.NodesLabelNotFoundException;
import com.kid.nodeflow.exception.ChainsLabelNotFoundException;
import com.kid.nodeflow.rt.FlowBus;
import com.kid.nodeflow.rt.element.Chain;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;
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
			Element nodes = root.element(NODES);
			if (nodes == null) {
				throw new NodesLabelNotFoundException("nodes label is not found in Rule Source");
			}
			Iterator<Element> nodeIter = nodes.elementIterator(NODE);
			while (nodeIter.hasNext()) {
				Element node = nodeIter.next();
				String nodeId = node.attribute(ID).getText();
				String className = node.attribute(CLASS).getText();
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
	public static void parseChains(List<Document> documents, Consumer<Element> chainParser)
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
	public static void parseChains(List<Document> documents, Consumer<Element> chainParser, Set<String> nameSet)
			throws ChainsLabelNotFoundException {
		if (CollUtil.isEmpty(documents)) {
			return;
		}
		// 首先先获取所有chainId，预处理一遍，防止循环依赖问题
		for (Document document : documents) {
			Element chains = document.getRootElement().element(CHAINS);
			if (chains == null) {
				throw new ChainsLabelNotFoundException("chains label is not found in Rule Source");
			}
			// 向FlowBus中放入占位Chain
			chains.elements(CHAIN).forEach(chain -> FlowBus.addChain(Chain.emptyChain(chain.attribute(ID).getText())));
		}
		for (Document document : documents) {
			Element root = document.getRootElement();
			Element chains = root.element(CHAINS);
			Iterator<Element> chainIter = chains.elementIterator(CHAIN);
			while (chainIter.hasNext()) {
				Element chain = chainIter.next();
				// chainId为null，则不参与解析
				if (chain.attribute(ID) == null) {
					continue;
				}
				String chainId = chain.attribute(ID).getText();
				// 这里用作打印日志
				if (!nameSet.add(chainId)) {
					log.info("chain({}) is duplicate", chainId);
				} else {
					// 函数调用
					chainParser.accept(chain);
					log.info("chain({}) is added to FlowBus", chainId);
				}
			}
		}
		// 清空容器
		nameSet.clear();
	}
}
