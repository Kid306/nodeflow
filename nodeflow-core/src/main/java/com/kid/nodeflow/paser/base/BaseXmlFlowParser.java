package com.kid.nodeflow.paser.base;

import static com.kid.nodeflow.common.BaseConstant.CHAIN;

import com.kid.nodeflow.exception.IllegalElementTypeException;
import org.dom4j.Element;

/**
 * XML解析器的基础实现类
 * @author cwk
 * @version 1.0
 */
public class BaseXmlFlowParser extends XmlFlowParser {
	/**
	 * 解析单个以XML表示的chain
	 * @param chainElement 单个XML表示的chain
	 */
	@Override
	public void parseChain(Element chainElement) {
		if (!CHAIN.equals(chainElement.getName())) {
			throw new IllegalElementTypeException("illegal element type: [%s] wanna parse to [%s]",
					chainElement.getName(), CHAIN);
		}

	}
}
