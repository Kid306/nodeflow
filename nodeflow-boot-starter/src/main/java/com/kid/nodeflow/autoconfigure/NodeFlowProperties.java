package com.kid.nodeflow.autoconfigure;

import java.util.List;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "nodeflow", ignoreInvalidFields = true)
public class NodeFlowProperties {

	// 项目启动即解析规则文件
	private Boolean parseOnStart;
	// 规则文件路径
	private List<String> sourcePath;
	// 定位子流程的时机
	private String timeToLocateSubChain;
	// 默认SLOTS槽个数
	private Integer slotsSize;


	public Boolean getParseOnStart() {
		return parseOnStart;
	}

	public void setParseOnStart(Boolean parseOnStart) {
		this.parseOnStart = parseOnStart;
	}

	public List<String> getSourcePath() {
		return sourcePath;
	}

	public void setSourcePath(List<String> sourcePath) {
		this.sourcePath = sourcePath;
	}

	public String getTimeToLocateSubChain() {
		return timeToLocateSubChain;
	}

	public void setTimeToLocateSubChain(String timeToLocateSubChain) {
		this.timeToLocateSubChain = timeToLocateSubChain;
	}

	public Integer getSlotsSize() {
		return slotsSize;
	}

	public void setSlotsSize(Integer slotsSize) {
		this.slotsSize = slotsSize;
	}
}
