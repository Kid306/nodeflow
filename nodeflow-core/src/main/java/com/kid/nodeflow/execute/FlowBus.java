package com.kid.nodeflow.execute;

import com.kid.nodeflow.enums.NodeType;
import com.kid.nodeflow.execute.element.Chain;
import com.kid.nodeflow.execute.element.Node;
import java.util.HashMap;
import java.util.Map;

/**
 * 整个执行流程过程中的元素总线
 * 
 * @author cwk
 * @version 1.0
 */
public class FlowBus {
	private static final Map<String, Node> nodeMap = new HashMap<>();

	private static final Map<String, Chain> chainMap = new HashMap<>();

	public static void addNode(String nodeId, Class<?> clazz, NodeType type) {
		nodeMap.put(nodeId, new Node(nodeId, clazz, type));
	}

	public static Node getNode(String nodeId) {
		return nodeMap.get(nodeId);
	}
}
