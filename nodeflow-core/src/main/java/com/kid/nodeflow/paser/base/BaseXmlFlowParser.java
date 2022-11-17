package com.kid.nodeflow.paser.base;

import cn.hutool.core.collection.CollectionUtil;
import java.util.List;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;

/**
 * 解析Xml规则文件的基础解析器
 *
 * @author cwk
 * @version 1.0
 */
public class BaseXmlFlowParser implements FlowParser {

	/**
	 * 解析文本文件
	 */
	@Override
	public void parse(List<String> contents) {
		if (CollectionUtil.isEmpty(contents)) {
			return;
		}
		for (String content : contents) {
			Document document = null;
			try {
				document = DocumentHelper.parseText(content);
				System.out.println(document);
			} catch (DocumentException e) {
				throw new RuntimeException(e);
			}
		}
	}
}
