package com.kid.nodeflow.test.context.spring;

import cn.hutool.core.collection.ListUtil;
import com.kid.nodeflow.config.NodeFlowConfig;
import com.kid.nodeflow.context.FlowContextHolder;
import com.kid.nodeflow.context.spring.SpringFlowContext;
import com.kid.nodeflow.core.NodeFlowResponse;
import com.kid.nodeflow.rt.ChainExecutor;
import com.kid.nodeflow.rt.NodeFlowRuntime;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SpringFlowContextTest {
	ChainExecutor executor;

	@Before
	public void testBefore() {
		// TODO 问题：这里获得applicationContext时就会去解析NodeComponent的子类，
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext(
				"applicationContext.xml");
		System.out.println(applicationContext);
		SpringFlowContext context = (SpringFlowContext) FlowContextHolder.getContext();
		context.setApplicationContext(applicationContext);

		NodeFlowConfig config = new NodeFlowConfig();
		config.setSourcePath(ListUtil.toList("/core/test.xml"));
		executor = NodeFlowRuntime.loadConfig(config);
	}

	@Test
	public void test01() {
		NodeFlowResponse response = executor.exec("chain4");
		System.out.println(response);
	}
}
