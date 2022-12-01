package store.lunangangster.nodeflow.parser.factory;

import store.lunangangster.nodeflow.parser.base.XmlFlowParser;

/**
 * FlowParser的抽象工厂
 *
 * @author cwk
 * @version 1.0
 */
public interface ParserFactory {

	/**
	 * 创建一个新的XmlFlowParser
	 */
	public XmlFlowParser newXmlFlowParser();

	/**
	 * 获取XmlFlowParser
	 */
	public XmlFlowParser getXmlFlowParser();
}
