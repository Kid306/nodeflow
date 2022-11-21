package com.kid.nodeflow.execute.element;

import com.kid.nodeflow.enums.NodeType;

/**
 * 节点的可执行类
 *
 * @author cwk
 * @version 1.0
 */
public class Node implements Executable {

	private String id;

	private Class<?> clazz;

	private NodeType type;

	@Override
	public void execute() {

	}

	public Node() {
	}

	public Node(String id, Class<?> clazz, NodeType type) {
		this.id = id;
		this.clazz = clazz;
		this.type = type;
	}

	public String getId() {
		return id;
	}

	public Node setId(String id) {
		this.id = id;
		return this;
	}

	public Class<?> getClazz() {
		return clazz;
	}

	public Node setClazz(
			Class<?> clazz) {
		this.clazz = clazz;
		return this;
	}

	public NodeType getType() {
		return type;
	}

	public Node setType(NodeType type) {
		this.type = type;
		return this;
	}
}
