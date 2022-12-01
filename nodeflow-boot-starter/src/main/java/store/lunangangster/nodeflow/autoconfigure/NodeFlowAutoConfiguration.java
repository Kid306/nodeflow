package store.lunangangster.nodeflow.autoconfigure;

import store.lunangangster.nodeflow.config.NodeFlowConfig;
import store.lunangangster.nodeflow.context.FlowContext;
import store.lunangangster.nodeflow.context.spring.SpringFlowContext;
import store.lunangangster.nodeflow.context.spring.SpringNodeRegister;
import store.lunangangster.nodeflow.rt.ChainExecutor;
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
