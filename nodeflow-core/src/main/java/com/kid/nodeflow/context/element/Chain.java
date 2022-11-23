package com.kid.nodeflow.context.element;

import com.kid.nodeflow.context.element.flow.Flow;
import java.util.List;

/**
 * <p>流程链的可执行类</p>
 * <p>一个&lt;chain/&gt;的定义就对应于一个Chain对象</p>
 *
 * @author cwk
 * @version 1.0
 */
public class Chain implements Executable {

	private String id;

	/**
	 * 一个Chain中所有的可执行流。可执行流的定义见{@link Flow}
	 */
	private List<Flow> flowList;

	public Chain() {
	}

	public Chain(String id, List<Flow> flowList) {
		this.id = id;
		this.flowList = flowList;
	}

	/**
	 * Chain的执行方法
	 */
	@Override
	public void execute() {

	}

	public String getId() {
		return id;
	}

	public Chain setId(String id) {
		this.id = id;
		return this;
	}

	public List<Flow> getFlowList() {
		return flowList;
	}

	public Chain setFlowList(List<Flow> flowList) {
		this.flowList = flowList;
		return this;
	}
}
