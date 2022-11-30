package com.kid.nodeflow.autoconfigure;

import com.kid.nodeflow.config.NodeFlowConfig;
import com.kid.nodeflow.rt.NodeFlowRuntime;
import org.springframework.beans.factory.InitializingBean;

/**
 * NodeFlow系统初始化器，用于SpringBoot初始化bean之后NodeFlow的自动初始化工作
 *
 * @author cwk
 * @version 1.0
 */
public class NodeFlowInitializer implements InitializingBean {
	private final NodeFlowConfig nodeFlowConfig;

	public NodeFlowInitializer(NodeFlowConfig nodeFlowConfig) {
		this.nodeFlowConfig = nodeFlowConfig;
	}

	@Override
	public void afterPropertiesSet() {
		NodeFlowRuntime.init(nodeFlowConfig);
	}
}
