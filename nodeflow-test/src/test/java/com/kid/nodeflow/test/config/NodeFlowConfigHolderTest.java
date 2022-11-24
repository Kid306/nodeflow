package com.kid.nodeflow.test.config;

import com.kid.nodeflow.config.NodeFlowConfigHolder;
import org.junit.Test;

public class NodeFlowConfigHolderTest {
	@Test
	public void test01() {
		for (int i = 0; i < 10000; i++) {
			new Thread(() -> {
				System.out.println(NodeFlowConfigHolder.getConfig());
			}).start();
		}
		while (true) {}
	}
}
