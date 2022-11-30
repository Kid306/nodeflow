package com.kid.nodeflow.test.context;

import com.kid.nodeflow.context.FlowContextHolder;
import com.kid.nodeflow.context.FlowContext;
import org.junit.Test;

public class FlowContextHolderTest {
	@Test
	public void test01() {
		FlowContext flowContext = FlowContextHolder.getContext();
		System.out.println(flowContext.getClass());
	}
}
