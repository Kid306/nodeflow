package com.kid.nodeflow.test.parser;

import com.kid.nodeflow.config.NodeFlowConfig;
import com.kid.nodeflow.rt.ChainExecutor;
import com.kid.nodeflow.rt.NodeFlowRuntime;
import org.junit.Before;
import org.junit.Test;

public class FlowParserTest {
	ChainExecutor executor;

	@Before
	public void init() {
		NodeFlowConfig config = new NodeFlowConfig();
		config.addRuleSourcePath("/core/test.xml");
		System.out.println(config.getRuleSourcePath());
		NodeFlowRuntime.loadConfig(config);
		executor = new ChainExecutor();
	}

	/**
	 * 针对Flow的合并测试
	 */
	@Test
	public void test01() {
		System.out.println(executor.exec("chain2"));
	}
}
