package com.kid.nodeflow.test.rt;

import com.kid.nodeflow.config.NodeFlowConfig;
import com.kid.nodeflow.rt.NodeFlowRuntime;
import org.junit.Test;

public class NodeFlowRuntimeTest {


	/**
	 * 用于并发条件下的config创建测试
	 */
	@Test
	public void test01() {
		for (int i = 0; i < 10000; i++) {
			new Thread(() -> {
				NodeFlowRuntime.loadConfig(new NodeFlowConfig());
			}).start();
		}
		while (true) {}
	}
}
