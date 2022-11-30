package com.kid.nodeflow.test.parser;

import cn.hutool.core.collection.ListUtil;
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
		config.setSourcePath(ListUtil.toList("/core/test.xml"));
		System.out.println(config.getSourcePath());
		executor = NodeFlowRuntime.loadConfig(config);
	}

	// /**
	//  * 针对Flow的合并测试
	//  */
	// @Test
	// public void test01_flowMerge() {
	// 	System.out.println(executor.exec("chain2"));
	// }

	@Test
	public void test02_ifNodeComponent() {
		System.out.println(executor.exec("chain1"));
	}
}
