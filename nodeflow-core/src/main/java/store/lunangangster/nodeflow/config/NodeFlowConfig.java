package store.lunangangster.nodeflow.config;

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
	private Boolean parseOnStart;

	/**
	 * 规则文件的配置路径
	 */
	private List<String> sourcePath;

	/**
	 * 初始SLOTS槽大小
	 */
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

	public NodeFlowConfig setSourcePath(List<String> sourcePath) {
		this.sourcePath = sourcePath;
		return this;
	}

	public Integer getSlotsSize() {
		return slotsSize;
	}

	public NodeFlowConfig setSlotsSize(Integer slotsSize) {
		this.slotsSize = slotsSize;
		return this;
	}
}
