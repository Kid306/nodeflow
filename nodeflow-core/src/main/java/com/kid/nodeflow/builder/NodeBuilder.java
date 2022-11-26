package com.kid.nodeflow.builder;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.kid.nodeflow.enums.NodeType;
import com.kid.nodeflow.exception.rt.NodeBuildException;
import com.kid.nodeflow.rt.FlowBus;
import com.kid.nodeflow.rt.element.Node;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Node构建器
 *
 * @author cwk
 * @version 1.0
 */
public class NodeBuilder {
	private static final Logger log = LoggerFactory.getLogger(NodeBuilder.class);

	private final Node node;

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
		Node oldNode = FlowBus.getNode(node.getId());
		FlowBus.addNode(node);
		log.info("node({}, {}) is added to FlowBus", node.getId(), node.getClazz());
		if (oldNode != null) {
			log.info("node({}, {}) is overwritten", oldNode.getId(), oldNode.getClazz());
		}
	}

	private void checkBuild() {
		boolean isError = StrUtil.isBlank(node.getId()) || node.getClazz() == null || node.getType() == null;
		List<String> errorInfos = null;
		if (!isError) {
			return;
		}
		errorInfos = new ArrayList<>();
		if (StrUtil.isBlank(node.getId())) {
			errorInfos.add("node's id is blank");
		}
		if (node.getClazz() == null) {
			errorInfos.add("node's class is null");
		}
		if (node.getType() == null) {
			errorInfos.add("node's type is null");
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