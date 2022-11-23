package com.kid.nodeflow.context.element.flow;

import static com.kid.nodeflow.enums.FlowType.FLOW_THEN;

import cn.hutool.core.collection.CollUtil;
import com.kid.nodeflow.enums.FlowType;
import com.kid.nodeflow.context.element.Executable;

public class ThenFlow extends Flow {

	// need no arguments constructor
	public ThenFlow() {}

	@Override
	public void execute() {
		if (CollUtil.isEmpty(executableList)) {
			return;
		}
		// 串行化执行所有子节点
		for (Executable exec : this.executableList) {
			exec.execute();
		}
	}

	@Override
	public FlowType getFlowType() {
		return FLOW_THEN;
	}
}
