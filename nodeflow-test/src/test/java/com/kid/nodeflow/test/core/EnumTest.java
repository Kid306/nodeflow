package com.kid.nodeflow.test.core;

import com.kid.nodeflow.enums.FlowType;
import org.junit.Test;

public class EnumTest {
	@Test
	public void test01() {
		System.out.println(FlowType.getFlowType("then"));
	}
}
