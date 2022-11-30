package com.kid.nodeflow.builder.entity;

import com.kid.nodeflow.core.component.NodeComponent;
import com.kid.nodeflow.enums.NodeType;

/**
 * 用于Node的数据传递类
 *
 * @author cwk
 * @version 1.0
 */
public class NodeProp {
	private String id;

	private String className;

	private Class<?> clazz;

	private NodeType type;

	private NodeComponent nodeComponent;

	public NodeProp() {
	}

	public String getId() {
		return id;
	}

	public NodeProp setId(String id) {
		this.id = id;
		return this;
	}

	public String getClassName() {
		return className;
	}

	public NodeProp setClassName(String className) {
		this.className = className;
		return this;
	}

	public Class<?> getClazz() {
		return clazz;
	}

	public NodeProp setClazz(Class<?> clazz) {
		this.clazz = clazz;
		return this;
	}

	public NodeType getType() {
		return type;
	}

	public NodeProp setType(NodeType type) {
		this.type = type;
		return this;
	}

	public NodeComponent getNodeComponent() {
		return nodeComponent;
	}

	public NodeProp setNodeComponent(NodeComponent nodeComponent) {
		this.nodeComponent = nodeComponent;
		return this;
	}
}
