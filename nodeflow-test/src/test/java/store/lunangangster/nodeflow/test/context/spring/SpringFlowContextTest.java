package store.lunangangster.nodeflow.test.context.spring;

import cn.hutool.core.collection.ListUtil;
import store.lunangangster.nodeflow.config.NodeFlowConfig;
import store.lunangangster.nodeflow.context.FlowContextHolder;
import store.lunangangster.nodeflow.context.spring.SpringFlowContext;
import store.lunangangster.nodeflow.core.NodeFlowResponse;
import store.lunangangster.nodeflow.rt.ChainExecutor;
import store.lunangangster.nodeflow.rt.NodeFlowRuntime;
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
