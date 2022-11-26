package com.kid.nodeflow.test.core;

import com.kid.nodeflow.enums.FlowType;
import java.util.Arrays;
import org.junit.Test;

public class EnumTest {
	@Test
	public void test01() {
		System.out.println(FlowType.getFlowType("then"));
	}

	@Test
	public void test02() {
		String value = "  a, b   ";
		System.out.println(Arrays.toString(value.split("[\\s*,]+")));
	}
}
