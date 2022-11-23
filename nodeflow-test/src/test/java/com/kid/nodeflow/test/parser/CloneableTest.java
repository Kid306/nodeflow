package com.kid.nodeflow.test.parser;

import com.kid.nodeflow.builder.NodeBuilder;
import com.kid.nodeflow.rt.FlowBus;
import com.kid.nodeflow.rt.element.Node;
import com.kid.nodeflow.core.component.NodeComponent;
import com.kid.nodeflow.enums.NodeType;
import org.junit.Test;

public class CloneableTest {
	@Test
	public void test01() {
		NodeBuilder.start().id("1").clazz(NodeComponent.class).type(NodeType.NODE_COMMON).build();
		Node node = FlowBus.getNode("1");
		Node clone = node.clone();
		System.out.println(node);
		System.out.println(clone);
	}
}
