package com.kid.nodeflow.context;

import com.kid.nodeflow.enums.NodeType;
import com.kid.nodeflow.context.element.Chain;
import com.kid.nodeflow.context.element.Node;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>整个执行流程过程中的元素总线</p>
 * <p>由于Node和Chain的定义是全局的，所以使用了static进行修饰</p>
 * 
 * @author cwk
 * @version 1.0
 */
public class FlowBus {
	// 装载了所有已定义的Node
	private static final Map<String, Node> nodeMap = new HashMap<>();

	// 装载了所有已定义的Chain
	private static final Map<String, Chain> chainMap = new HashMap<>();

	public static void addNode(String nodeId, Class<?> clazz, NodeType type) {
		nodeMap.put(nodeId, new Node(nodeId, clazz, type));
	}

	public static Node getNode(String nodeId) {
		return nodeMap.get(nodeId);
	}
}
