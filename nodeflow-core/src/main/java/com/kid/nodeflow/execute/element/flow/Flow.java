package com.kid.nodeflow.execute.element.flow;

import com.kid.nodeflow.enums.FlowType;
import com.kid.nodeflow.execute.element.Executable;
import java.util.LinkedList;
import java.util.List;

/**
 * 可执行流的抽象类
 *
 * @author cwk
 * @version 1.0
 */
public abstract class Flow implements Executable {

	// 所包含的可执行节点
	protected List<Executable> subFlow = new LinkedList<>();

	private int id;

	public abstract FlowType getFlowType();
}
