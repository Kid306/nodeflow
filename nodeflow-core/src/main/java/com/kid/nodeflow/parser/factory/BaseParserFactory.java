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

	@Override
	public XmlFlowParser newXmlFlowParser() {
		return new BaseXmlFlowParser();
	}
}
