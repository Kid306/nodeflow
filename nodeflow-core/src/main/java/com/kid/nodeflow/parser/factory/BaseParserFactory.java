package com.kid.nodeflow.parser.factory;

import com.kid.nodeflow.parser.base.BaseXmlFlowParser;
import com.kid.nodeflow.parser.base.XmlFlowParser;

/**
 * 基础RuleSource文件解析类的工厂类
 * 
 * @author cwk
 * @version 1.0
 */
public class BaseParserFactory implements ParserFactory {
	private static BaseXmlFlowParser baseXmlParser;

	/**
	 * 创建一个新的XmlFlowParser
	 */
	@Override
	public XmlFlowParser newXmlFlowParser() {
		baseXmlParser = new BaseXmlFlowParser();
		return baseXmlParser;
	}

	/**
	 * 获取XmlFlowParser
	 */
	@Override
	public XmlFlowParser getXmlFlowParser() {
		if (baseXmlParser == null) {
			synchronized (this) {
				if (baseXmlParser == null) {
					return newXmlFlowParser();
				}
			}
		}
		return baseXmlParser;
	}
}
