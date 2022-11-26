package com.kid.nodeflow.enums;

import com.kid.nodeflow.core.component.IfNodeComponent;
import com.kid.nodeflow.core.component.NodeComponent;
import java.util.regex.Pattern;

/**
 * Node类型枚举类
 *
 * @author cwk
 * @version 1.0
 */
public enum NodeType {

	NODE_COMMON("common", NodeComponent.class, Pattern.compile("^\\w+$")),
	NODE_IF("if", IfNodeComponent.class, Pattern.compile("^\\w+\\s*\\(\\s*\\w+\\s*(\\|\\s*\\w+\\s*)?\\)$"));

	private final String name;
	private final Class<? extends NodeComponent> clazz;
	public final Pattern pattern;

	NodeType(String name, Class<? extends NodeComponent> clazz, Pattern pattern) {
		this.name = name;
		this.clazz = clazz;
		this.pattern = pattern;
	}

	/**
	 * 通过反射获取NodeComponent对象
	 */
	public NodeComponent newNodeCompInstance() {
		NodeComponent nodeComp = null;
		try {
			nodeComp = clazz.newInstance();
		} catch (InstantiationException | IllegalAccessException ignore) {
			// never occur
		}
		return nodeComp;
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
