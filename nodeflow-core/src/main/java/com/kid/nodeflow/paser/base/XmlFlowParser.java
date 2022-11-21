package com.kid.nodeflow.paser.base;

import cn.hutool.core.collection.CollUtil;
import com.kid.nodeflow.paser.helper.ParserHelper;
import java.util.ArrayList;
import java.util.List;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

/**
 * 解析Xml规则文件的基础解析器
 *
 * @author cwk
 * @version 1.0
 */
public abstract class XmlFlowParser implements FlowParser {


	/**
	 * 解析Xml文本文件
	 */
	@Override
	public void parse(List<String> contents) throws Exception {
		if (CollUtil.isEmpty(contents)) {
			return;
		}
		List<Document> documents = new ArrayList<>(contents.size());
		for (String content : contents) {
			documents.add(DocumentHelper.parseText(content));
		}
		ParserHelper.parseNodes(documents);
		ParserHelper.parseChains(documents, this::parseChain);
	}

	/**
	 * <p>解析单个以XML表示的chain</p>
	 * <p>交给子类实现</p>
	 */
	public abstract void parseChain(Element chainElement);
}
