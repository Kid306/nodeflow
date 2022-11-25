package com.kid.nodeflow.test.config;

import com.kid.nodeflow.rt.NodeFlowRuntime;
import org.junit.Test;

public class NodeFlowRuntimeTest {
	@Test
	public void test01() {
		for (int i = 0; i < 10000; i++) {
			new Thread(() -> {
				System.out.println(NodeFlowRuntime.getConfig());
			}).start();
		}
		while (true) {}
	}
}
