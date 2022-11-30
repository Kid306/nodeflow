package com.kid.nodeflow.autoconfigure;

import com.kid.nodeflow.config.NodeFlowConfig;
import org.springframework.beans.BeanUtils;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@EnableConfigurationProperties(NodeFlowProperties.class)
@PropertySource(
		name = "NodeFlow Default Properties",
		value = "classpath:/META-INF/nodeflow-default.properties")
public class NodeFlowConfigAutoConfiguration {
	@Bean
	public NodeFlowConfig nodeFlowConfig(NodeFlowProperties nodeFlowProperties) {
		NodeFlowConfig nodeFlowConfig = new NodeFlowConfig();
		BeanUtils.copyProperties(nodeFlowProperties, nodeFlowConfig);
		return nodeFlowConfig;
	}
}
