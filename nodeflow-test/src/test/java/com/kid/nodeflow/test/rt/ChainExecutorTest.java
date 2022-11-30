package com.kid.nodeflow.test.rt;

import com.kid.nodeflow.config.NodeFlowConfig;
import com.kid.nodeflow.core.NodeFlowResponse;
import com.kid.nodeflow.rt.ChainExecutor;
import com.kid.nodeflow.rt.NodeFlowRuntime;
import java.util.Collections;
import org.junit.Before;
import org.junit.Test;

public class ChainExecutorTest {
	private ChainExecutor executor;

	@Before
	public void testBefore() {
		NodeFlowConfig config = new NodeFlowConfig();
		config.setSourcePath(Collections.singletonList("/core/test.xml"));
		NodeFlowRuntime.loadConfig(config);
		for (int i = 0; i < 1000; i++) {
			new Thread(() -> {
			}).start();
		}
	}


	@Test
	public void test01() {
		NodeFlowResponse response = executor.exec("chain1");
		System.out.println(response);
	}

}
