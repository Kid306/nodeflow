package com.kid.nodeflow.test.parser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.junit.Test;

public class PatternTest {
	@Test
	public void test01() {
		Pattern pattern = Pattern.compile("a+");
		Matcher m = pattern.matcher("sfbadsaaaasdasasdfaasafsfafa");
		while(m.find()) {
			System.out.println(m.group());
		}
	}
}
