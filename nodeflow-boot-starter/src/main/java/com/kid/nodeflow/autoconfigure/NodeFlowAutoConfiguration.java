package com.kid.nodeflow.autoconfigure;

import com.kid.nodeflow.config.NodeFlowConfig;
import com.kid.nodeflow.context.FlowContext;
import com.kid.nodeflow.context.spring.SpringFlowContext;
import com.kid.nodeflow.context.spring.SpringNodeRegister;
import com.kid.nodeflow.rt.ChainExecutor;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@AutoConfigureAfter(NodeFlowConfigAutoConfiguration.class)
@ConditionalOnClass(NodeFlowConfig.class)
public class NodeFlowAutoConfiguration {

	/**
	 * 上下文对象
	 */
	@Bean
	public FlowContext springFlowContext(NodeFlowConfig nodeFlowConfig) {
		return new SpringFlowContext();
	}

	/**
	 * NodeComponent的扫描处理器
	 */
	@Bean
	public SpringNodeRegister springNodeRegister(NodeFlowConfig nodeFlowConfig) {
		return new SpringNodeRegister();
	}

	/**
	 * Chain执行器
	 */
	@Bean
	public ChainExecutor chainExecutor(NodeFlowConfig nodeFlowConfig) {
		return new ChainExecutor(nodeFlowConfig);
	}

	/**
	 * SpringBoot环境下的NodeFlow自动初始化器
	 */
	@Bean
	public NodeFlowInitializer nodeFlowInitializer(NodeFlowConfig nodeFlowConfig) {
		return new NodeFlowInitializer(nodeFlowConfig);
	}
}
