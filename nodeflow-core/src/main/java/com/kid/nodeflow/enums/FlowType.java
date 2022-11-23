package com.kid.nodeflow.enums;

import com.kid.nodeflow.rt.element.flow.Flow;
import com.kid.nodeflow.rt.element.flow.ThenFlow;
import com.kid.nodeflow.rt.element.flow.WhenFlow;

/**
 * 已定义的Flow类型枚举类
 *
 * @author cwk
 * @version 1.0
 */
public enum FlowType {
	FLOW_THEN("then", ThenFlow.class),
	FLOW_WHEN("when", WhenFlow.class);

	private final String type;

	private final Class<? extends Flow> clazz;

	FlowType(String type, Class<? extends Flow> clazz) {
		this.type = type;
		this.clazz = clazz;
	}

	public Class<? extends Flow> getClazz() {
		return clazz;
	}

	public String getType() {
		return this.type;
	}

	/**
	 * 通过反射获取Flow对象
	 */
	public Flow getFlowInstance() {
		Flow flow = null;
		try {
			flow = clazz.newInstance();
		} catch (InstantiationException | IllegalAccessException ignore) {
			// never occur
		}
		return flow;
	}
}
