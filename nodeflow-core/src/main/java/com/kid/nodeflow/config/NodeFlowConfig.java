package com.kid.nodeflow.config;

import static com.kid.nodeflow.common.BaseConstant.DEFAULT_SLOTS_SIZE;

import cn.hutool.core.collection.CollUtil;
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
	private List<String> ruleSourcePath = CollUtil.list(false, "classpath:flow/test.xml");

	/**
	 * 定位子流程的时机： runtime | resolve
	 * TODO 目前只支持runtime
	 */
	private String timeToLocateSubProcess = "runtime";

	/**
	 * 初始SLOTS槽大小
	 */
	private Integer initialSlotsSize = DEFAULT_SLOTS_SIZE;

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

	public void addRuleSourcePath(String path) {
		ruleSourcePath.add(path);
	}

	public Integer getInitialSlotsSize() {
		return initialSlotsSize;
	}

	public void setInitialSlotsSize(Integer initialSlotsSize) {
		this.initialSlotsSize = initialSlotsSize;
	}

	public String getTimeToLocateSubProcess() {
		return timeToLocateSubProcess;
	}

	public void setTimeToLocateSubProcess(String timeToLocateSubProcess) {
		this.timeToLocateSubProcess = timeToLocateSubProcess;
	}
}
