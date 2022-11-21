package com.kid.nodeflow.build;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.kid.nodeflow.enums.NodeType;
import com.kid.nodeflow.exception.NodeBuildException;
import com.kid.nodeflow.execute.FlowBus;
import com.kid.nodeflow.execute.element.Node;
import java.util.ArrayList;
import java.util.List;

/**
 * Node构建器
 *
 * @author cwk
 * @version 1.0
 */
public class NodeBuilder {

	private Node node;

	private NodeBuilder() {
		this.node = new Node();
	}

	/**
	 * 开始构建Node
	 */
	public static NodeBuilder start() {
		return new NodeBuilder();
	}

	/**
	 * 构建结束
	 */
	public void build() {
		// build前的简单检查
		checkBuild();
		// 向FlowBus中添加Node
		FlowBus.addNode(node.getId(), node.getClazz(), node.getType());
	}

	private void checkBuild() {
		List<String> errorInfos = new ArrayList<>();
		if (StrUtil.isBlank(node.getId())) {
			errorInfos.add("node id is blank");
		}
		if (node.getClazz() == null) {
			errorInfos.add("node class is null");
		}
		if (node.getType() == null) {
			errorInfos.add("node type is null");
		}
		throw new NodeBuildException(CollUtil.join(errorInfos, ", ", "[", "]"));
	}

	public NodeBuilder id(String id) {
		this.node.setId(id);
		return this;
	}

	public NodeBuilder clazz(Class<?> clazz) {
		this.node.setClazz(clazz);
		return this;
	}

	public NodeBuilder type(NodeType type) {
		this.node.setType(type);
		return this;
	}
}