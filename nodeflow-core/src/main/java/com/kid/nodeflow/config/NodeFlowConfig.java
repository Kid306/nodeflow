package com.kid.nodeflow.config;

import java.util.Collections;
import java.util.List;

/**
 * NodeFlow配置类
 *
 * @author cwk
 * @version 1.0
 */
public class NodeFlowConfig {

	/**
	 *  true：在程序启动时就解析配置文件
	 */
	private Boolean parseOnStart = true;

	/**
	 * 规则文件的配置路径
	 */
	private List<String> ruleSourcePath = Collections.singletonList("classpath:flow/test.xml");

	public Boolean getParseOnStart() {
		return parseOnStart;
	}

	public void setParseOnStart(Boolean parseOnStart) {
		this.parseOnStart = parseOnStart;
	}

	public List<String> getRuleSourcePath() {
		return ruleSourcePath;
	}

	public void setRuleSourcePath(List<String> ruleSourcePath) {
		this.ruleSourcePath = ruleSourcePath;
	}
}
