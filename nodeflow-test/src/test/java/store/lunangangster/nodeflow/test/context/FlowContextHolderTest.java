package store.lunangangster.nodeflow.test.context;

import store.lunangangster.nodeflow.context.FlowContextHolder;
import store.lunangangster.nodeflow.context.FlowContext;
import org.junit.Test;

public class FlowContextHolderTest {
	@Test
	public void test01() {
		FlowContext flowContext = FlowContextHolder.getContext();
		System.out.println(flowContext.getClass());
	}
}
