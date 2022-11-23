package com.kid.nodeflow.context.element.flow;

import static com.kid.nodeflow.enums.FlowType.FLOW_WHEN;

import cn.hutool.core.collection.CollUtil;
import com.kid.nodeflow.enums.FlowType;

public class WhenFlow extends Flow {

	// need no arguments constructor
	public WhenFlow() {}

	@Override
	public void execute() {
		if (CollUtil.isEmpty(executableList)) {
			return;
		}
	}

	@Override
	public FlowType getFlowType() {
		return FLOW_WHEN;
	}
}
