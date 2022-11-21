package com.kid.nodeflow.test.parser;

import cn.hutool.core.collection.ListUtil;
import com.kid.nodeflow.paser.helper.XmlParserHelper;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.junit.Test;

public class XmlParserHelperTest {
	@Test
	public void test01() throws DocumentException {
		Document document = DocumentHelper.parseText(
				"<flow><nodes><node id=\"a\" class=\"com.yomahub.liteflow.test.parser.cmp.ACmp\"/><node id=\"b\" class=\"com.yomahub.liteflow.test.parser.cmp.BCmp\"/><node id=\"c\" class=\"com.yomahub.liteflow.test.parser.cmp.CCmp\"/><node id=\"d\" class=\"com.yomahub.liteflow.test.parser.cmp.DCmp\"/><node id=\"e\" class=\"com.yomahub.liteflow.test.parser.cmp.ECmp\"/><node id=\"f\" class=\"com.yomahub.liteflow.test.parser.cmp.FCmp\"/><node id=\"g\" class=\"com.yomahub.liteflow.test.parser.cmp.GCmp\"/></nodes><chain name=\"chain1\"><aaa name=\"张三\">哇咔哇咔</aaa><then value=\"a,c\"/><when value=\"b,d,e(f|g)\"/><then value=\"chain2\"/></chain><chain name=\"chain2\"><then value=\"c,g,f\"/></chain></flow>");
		XmlParserHelper.parseNodes(ListUtil.of(document));
	}

}
