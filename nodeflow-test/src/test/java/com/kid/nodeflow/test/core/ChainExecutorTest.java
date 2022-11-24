package com.kid.nodeflow.test.core;

import cn.hutool.core.collection.CollUtil;
import com.kid.nodeflow.core.ChainExecutor;
import com.kid.nodeflow.core.NodeFlowResponse;
import com.kid.nodeflow.parser.base.BaseXmlFlowParser;
import com.kid.nodeflow.parser.base.FlowParser;
import org.junit.Before;
import org.junit.Test;

public class ChainExecutorTest {
	private ChainExecutor executor;
	private FlowParser parser;

	@Before
	public void testBefore() {
		executor = new ChainExecutor();
		parser = new BaseXmlFlowParser();
		try {
			parser.parse(
					CollUtil.newArrayList("<flow><nodes><node id=\"a\" class=\"com.kid.nodeflow.test.component.ComponentA\"/><node id=\"b\" class=\"com.kid.nodeflow.test.component.ComponentB\"/></nodes><chains><chain id=\"chain1\"><then value=\"a,b\"/></chain></chains></flow>")
			);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}


	@Test
	public void test01() {
		NodeFlowResponse response = executor.exec("chain1");
		System.out.println(response);
	}
}
