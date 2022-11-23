package com.kid.nodeflow.builder.entity;

import com.kid.nodeflow.rt.element.flow.Flow;
import java.util.List;

/**
 * 用于Chain的数据传递类
 *
 * @author cwk
 * @version 1.0
 */
public class ChainProp {
	private String id;

	private List<Flow> flowList;

	public ChainProp() {
	}

	public ChainProp(String id, List<Flow> flowList) {
		this.id = id;
		this.flowList = flowList;
	}

	public void addFlow(Flow flow) {
		if (flowList != null) {
			flowList.add(flow);
		}
	}

	public String getId() {
		return id;
	}

	public ChainProp setId(String id) {
		this.id = id;
		return this;
	}

	public List<Flow> getFlowList() {
		return flowList;
	}

	public ChainProp setFlowList(List<Flow> flowList) {
		this.flowList = flowList;
		return this;
	}
}
