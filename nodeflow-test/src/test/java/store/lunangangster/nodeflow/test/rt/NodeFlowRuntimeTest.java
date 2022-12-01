package store.lunangangster.nodeflow.test.rt;

import store.lunangangster.nodeflow.config.NodeFlowConfig;
import store.lunangangster.nodeflow.rt.NodeFlowRuntime;
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
