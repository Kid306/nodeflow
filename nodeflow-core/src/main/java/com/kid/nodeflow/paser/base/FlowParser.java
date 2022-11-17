package com.kid.nodeflow.paser.base;

import java.util.List;

/**
 * NodeFlow规则文件的解析器接口
 *
 * @author cwk
 * @version 1.0
 */
public interface FlowParser {

	/**
	 * 解析文本文件
	 */
	public void parse(List<String> contents);
}
