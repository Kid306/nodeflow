package com.kid.nodeflow.enums;

import static com.kid.nodeflow.common.BaseConstant.COMP_PACKAGE;

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
	 * 根据子类Class推测Node类型：如A extend NodeComponent，则可根据A.class获取到getNodeType(NodeComponent.class)
	 */
	public static NodeType guessType(Class<?> clazz) {
		if (clazz == null) {
			return null;
		}
		while (true) {
			Class<?> superclass = clazz.getSuperclass();
			if (Object.class.equals(superclass)) {
				return null;
			}
			String packageName = superclass.getPackage().getName();
			// 如果父类是component包下的组件，则返回组件类型
			if (packageName.startsWith(COMP_PACKAGE)) {
				return NodeType.getNodeType(superclass);
			}
		}
	}

	/**
	 * 根据指定NodeComponent.class获取其对应NodeType
	 */
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
