package com.kid.nodeflow.test.parser;

import com.kid.nodeflow.paser.base.BaseXmlFlowParser;
import com.kid.nodeflow.paser.base.XmlFlowParser;
import java.util.Collections;
import org.junit.Test;

public class XmlFlowParserTest {
	@Test
	public void testParse() {
		XmlFlowParser xmlParser = new BaseXmlFlowParser();
		try {
			xmlParser.parse(Collections.singletonList("<flow><nodes><node id=\"a\" class=\"com.yomahub.liteflow.test.parser.cmp.ACmp\"/><node id=\"b\" class=\"com.yomahub.liteflow.test.parser.cmp.BCmp\"/><node id=\"c\" class=\"com.yomahub.liteflow.test.parser.cmp.CCmp\"/><node id=\"d\" class=\"com.yomahub.liteflow.test.parser.cmp.DCmp\"/><node id=\"e\" class=\"com.yomahub.liteflow.test.parser.cmp.ECmp\"/><node id=\"f\" class=\"com.yomahub.liteflow.test.parser.cmp.FCmp\"/><node id=\"g\" class=\"com.yomahub.liteflow.test.parser.cmp.GCmp\"/></nodes><chain name=\"chain1\"><aaa name=\"张三\">哇咔哇咔</aaa><then value=\"a,c\"/><when value=\"b,d,e(f|g)\"/><then value=\"chain2\"/></chain><chain name=\"chain2\"><then value=\"c,g,f\"/></chain></flow>\n"));
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
