package com.kid.nodeflow.test.exception;

import org.junit.Test;

public class RuntimeExceptionTest {
	@Test
	public void test01() {
		for (int i = 0; i < 100; i++) {
			System.out.println(i);
			if (i == 50) {
				new Thread(() -> methodWithException()).start();
			}
		}
		while (true) {}
	}

	public void methodWithException() {
		int x = 1 / 0;
	}
}
