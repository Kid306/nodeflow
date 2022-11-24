package com.kid.nodeflow.rt.element;

import cn.hutool.core.util.StrUtil;
import com.kid.nodeflow.core.component.NodeComponent;
import com.kid.nodeflow.enums.NodeType;
import com.kid.nodeflow.exception.ComponentInstantiationException;
import com.kid.nodeflow.exception.NodeCanNotExecuteException;

/**
 * 节点的可执行类
 *
 * @author cwk
 * @version 1.0
 */
public class Node implements Executable, Cloneable {

	private String id;

	private Class<?> clazz;

	private NodeType type;

	/**
	 * Node的执行方法，所有的Executable对象的执行操作都会到这个方法上来
	 */
	@Override
	public void execute(Integer slotIndex) {
		if (StrUtil.isEmpty(id) || clazz == null || type == null) {
			throw new NodeCanNotExecuteException("Node's message is not complete, can not to execute");
		}
		// 这里需要保证所有的NodeComponent子类都有无参构造方法
		NodeComponent nodeComp = null;
		try {
			nodeComp = (NodeComponent) clazz.newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			throw new ComponentInstantiationException("Class[%s] have no public|no arguments Constructor for instantiation", clazz);
		}
		nodeComp.process();
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

	@Override
	public Node clone() {
		try {
			// 浅拷贝，对于引用类型属性，都不会额外拷贝一份
			return (Node) super.clone();
		} catch (CloneNotSupportedException e) {
			throw new AssertionError();
		}
	}
}
