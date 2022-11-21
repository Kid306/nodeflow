package com.kid.nodeflow.execute.element.flow;

import static com.kid.nodeflow.enums.FlowType.FLOW_THEN;

import com.kid.nodeflow.enums.FlowType;
import com.kid.nodeflow.execute.element.Executable;

public class ThenFlow extends Flow {

	@Override
	public void execute() {
		// 串行化执行所有子节点
		for (Executable exec : this.subFlow) {
			exec.execute();
		}
	}

	@Override
	public FlowType getFlowType() {
		return FLOW_THEN;
	}
}
