package com.kid.nodeflow.test.annotation;

import com.kid.nodeflow.annotation.Priority;
import org.junit.Test;

public class PriorityTest {
	@Test
	public void test01() {
		Priority priority = TestClass.class.getAnnotation(Priority.class);
		System.out.println(priority.value());
		// Override override = TestClass.class.getAnnotation(Override.class);
		// System.out.println(override);
	}
}
