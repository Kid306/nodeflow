package com.kid.nodeflow.build.entity;

/**
 * 用于Node的数据传递类
 *
 * @author cwk
 * @version 1.0
 */
public class NodeProp {
	private String id;

	private String className;

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
}
