package com.kid.nodeflow.enums;

import com.kid.nodeflow.core.component.IfNodeComponent;
import com.kid.nodeflow.core.component.NodeComponent;

/**
 * Node类型枚举类
 *
 * @author cwk
 * @version 1.0
 */
public enum NodeType {

	NODE_COMMON("common", NodeComponent.class),
	NODE_IF("if", IfNodeComponent.class);

	private final String name;
	private final Class<? extends NodeComponent> clazz;

	NodeType(String name, Class<? extends NodeComponent> clazz) {
		this.name = name;
		this.clazz = clazz;
	}

	public static NodeType getNodeType(Class<?> clazz) {
		if (clazz == null) {
			return null;
		}
		for (NodeType type : values()) {
			if (type.getClazz().equals(clazz)) {
				return type;
			}
		}
		return null;
	}


	public String getName() {
		return name;
	}

	public Class<? extends NodeComponent> getClazz() {
		return clazz;
	}
}
