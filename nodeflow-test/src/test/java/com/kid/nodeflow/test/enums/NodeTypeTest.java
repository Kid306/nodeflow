package com.kid.nodeflow.test.enums;

import com.kid.nodeflow.enums.NodeType;
import com.kid.nodeflow.test.component.ComponentE;
import java.util.regex.Matcher;
import org.junit.Test;

public class NodeTypeTest {
	@Test
	public void test00() throws InstantiationException, IllegalAccessException {

		System.out.println(ComponentE.class.getName());
	}

	@Test
	public void test01() {
		Matcher matcher = NodeType.NODE_COMMON.pattern.matcher("a(b|c(d|e))");
		while (matcher.find()) {
			System.out.println(matcher.group());
		}
	}

	@Test
	public void test02() {
		Matcher matcher = NodeType.NODE_COMMON.pattern.matcher("a(b|c(d|e@))");
		while (matcher.find()) {
			System.out.println(matcher.group());
		}
	}
}
