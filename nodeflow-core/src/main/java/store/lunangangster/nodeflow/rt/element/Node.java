package store.lunangangster.nodeflow.rt.element;

import cn.hutool.core.util.StrUtil;
import store.lunangangster.nodeflow.core.component.NodeComponent;
import store.lunangangster.nodeflow.enums.NodeType;
import store.lunangangster.nodeflow.exception.rt.ComponentInstantiationException;
import store.lunangangster.nodeflow.exception.rt.NodeCanNotExecuteException;

/**
 * 节点的可执行类
 *
 * @author cwk
 * @version 1.0
 */
public class Node implements Executable, Cloneable {

	private String id;

	private String className;

	private Class<?> clazz;

	private NodeType type;

	private NodeComponent nodeComponent;

	/**
	 * Node的执行方法，所有的Executable对象的执行操作都会到这个方法上来。
	 * 一个Node的执行逻辑，实际上是其对应的{@link NodeComponent#process()}的执行逻辑
	 */
	@Override
	public void execute(Integer slotIndex) {
		if (StrUtil.isEmpty(id) || clazz == null || type == null) {
			throw new NodeCanNotExecuteException("Node's message is not complete, can not to execute");
		}
		// 这里需要保证所有的NodeComponent子类都有无参构造方法
		NodeComponent nodeComp = null;
		try {
			// 根据Node的类型创建NodeComponent对象
			// TODO 后续需要修正，不能每次运行时才创建NodeComponent
			nodeComp = (NodeComponent) clazz.newInstance();
			nodeComp.setSlotIndex(slotIndex);
			nodeComp.setNodeId(id);
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

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public NodeComponent getNodeComponent() {
		return nodeComponent;
	}

	public void setNodeComponent(NodeComponent nodeComponent) {
		this.nodeComponent = nodeComponent;
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
