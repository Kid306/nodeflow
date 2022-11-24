package com.kid.nodeflow.builder;

import com.kid.nodeflow.rt.element.Executable;
import com.kid.nodeflow.rt.element.flow.Flow;
import com.kid.nodeflow.enums.FlowType;
import java.util.List;

/**
 * Flow的构建器
 *
 * @author cwk
 * @version 1.0
 */
public class FlowBuilder {
	private Flow flow;

	/**
	 * 使用{@link FlowType#newFlowInstance()}获取相应Flow的实例对象，使用{@link Flow#init()}初始化executableList
	 */
	public static Flow buildFlow(FlowType flowType) {
		if (flowType == null) {
			return null;
		}
		Flow flow = flowType.newFlowInstance();
		flow.init();
		return flow;
	}

	/**
	 * 使用{@link FlowType#newFlowInstance()}获取相应Flow的实例对象，并且传入executableList
	 */
	public static Flow buildFlow(FlowType flowType, List<Executable> executableList) {
		if (flowType == null) {
			return null;
		}
		Flow flow = flowType.newFlowInstance();
		flow.setExecutableList(executableList);
		return flow;
	}
}
