package com.kid.nodeflow.parser.factory;

import com.kid.nodeflow.parser.base.XmlFlowParser;

/**
 * FlowParser的抽象工厂
 *
 * @author cwk
 * @version 1.0
 */
public interface ParserFactory {
	public XmlFlowParser newXmlFlowParser();
}
