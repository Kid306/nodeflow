package com.kid.nodeflow.test.parser;

import com.kid.nodeflow.paser.base.BaseXmlFlowParser;
import java.util.Collections;
import org.junit.Test;

public class BaseXmlFlowParserTest {
	@Test
	public void testParse() {
		BaseXmlFlowParser xmlParser = new BaseXmlFlowParser();
		xmlParser.parse(Collections.singletonList("<flow>hahaha</flow>"));
	}
}
