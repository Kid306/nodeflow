package com.kid.nodeflow.test;

import com.kid.nodeflow.config.NodeFlowConfig;
import com.kid.nodeflow.context.FlowContext;
import com.kid.nodeflow.rt.ChainExecutor;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class ApplicationTest {
	@Autowired
	FlowContext springFlowContext;
	@Autowired
	NodeFlowConfig nodeFlowConfig;
	@Autowired
	ApplicationContext context;
	@Autowired
	ChainExecutor executor;

	@Test
	public void test01() {
		System.out.println(executor.exec("chain4"));
	}
}
