package com.kid.nodeflow.test.parser;

import cn.hutool.core.io.IoUtil;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.junit.Test;

public class FlowParserTest {
	@Test
	public void test01() {
		InputStream is = getClass().getResourceAsStream("/core/test.xml");
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		IoUtil.copy(is, os);
		// System.out.println(os.toString().replaceAll(">\\s*?<", "><"));
		Pattern pattern = Pattern.compile("(?<=>)\\s+(?=<)");
		Matcher matcher = pattern.matcher(os.toString());
		System.out.println(matcher.replaceAll(""));
		// System.out.println(os.toString().replaceAll("\\s*(?=<)", ""));
	}
}
