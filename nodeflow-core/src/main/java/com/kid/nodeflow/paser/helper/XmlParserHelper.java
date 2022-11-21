package com.kid.nodeflow.paser.helper;

import static com.kid.nodeflow.common.BaseConstant.CHAIN;
import static com.kid.nodeflow.common.BaseConstant.CHAINS;
import static com.kid.nodeflow.common.BaseConstant.CLASS;
import static com.kid.nodeflow.common.BaseConstant.ID;
import static com.kid.nodeflow.common.BaseConstant.NODE;
import static com.kid.nodeflow.common.BaseConstant.NODES;

import cn.hutool.core.collection.CollUtil;
import com.kid.nodeflow.build.entity.NodeProp;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;
import org.dom4j.Document;
import org.dom4j.Element;

/**
 * 解析基础XML的解析器工具类
 *
 * @author cwk
 * @version 1.0
 */
public class XmlParserHelper {
	private static final Set<String> XML_SET;

	static {
		XML_SET = new HashSet<>();
	}

	private XmlParserHelper() {}

	/**
	 * 解析XML中的&lt;nodes&gt;&lt;/nodes&gt;部分
	 */
	public static void parseNodes(List<Document> documents) {
		for (Document document : documents) {
			Element root = document.getRootElement();
			Element nodes = root.element(NODES);
			Iterator<Element> nodeIter = nodes.elementIterator(NODE);
			while (nodeIter.hasNext()) {
				Element node = nodeIter.next();
				String nodeId = node.attribute(ID).getText();
				String className = node.attribute(CLASS).getText();
				// TODO 创建Node对象
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
	public static void parseChains(List<Document> documents, Consumer<Element> chainParser) {
		parseChains(documents, chainParser, XML_SET);
	}

	/**
	 * <p>解析XML中的&lt;chains&gt;&lt;/chains&gt;部分</p>
	 * <p>由于对于Chain的解析代价可能比较大，所以每次解析前都会进行重复性判断，而不是像Node那样可以直接覆盖，从而减少性能消耗</p>
	 * @param documents   待解析<code>Document</code>对象集合
	 * @param chainParser 用于解析单个chain的方法
	 * @param nameSet     去重集合
	 */
	public static void parseChains(List<Document> documents, Consumer<Element> chainParser, Set<String> nameSet) {
		if (CollUtil.isEmpty(documents)) {
			return;
		}
		for (Document document : documents) {
			Element root = document.getRootElement();
			Iterator<Element> chainIter = root.element(CHAINS).elementIterator(CHAIN);
			while (chainIter.hasNext()) {
				Element chain = chainIter.next();
				String name = chain.attribute("name").getText();
				// 这里用作打印日志
				if (nameSet.add(name)) {
					System.err.printf("chain[%s] is duplicate\n", name);
				} else {
					// 函数调用
					chainParser.accept(chain);
				}
			}
		}
		// 清空容器
		nameSet.clear();
	}
}
