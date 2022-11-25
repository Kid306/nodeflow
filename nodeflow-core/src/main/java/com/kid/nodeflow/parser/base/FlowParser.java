package com.kid.nodeflow.parser.base;

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
	public void parse(String content);

	public void parse(List<String> contents);

}
