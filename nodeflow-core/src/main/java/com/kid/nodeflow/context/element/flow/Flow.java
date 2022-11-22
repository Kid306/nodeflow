package com.kid.nodeflow.context.element.flow;

import com.kid.nodeflow.enums.FlowType;
import com.kid.nodeflow.context.element.Executable;
import java.util.LinkedList;
import java.util.List;

/**
 * <p>可执行流的抽象类</p>
 * <p>
 *   一个&lt;then/&gt;标签，就可以看作是一个Flow。而一个Flow中存在一系列可执行的节点，是通过#value属性来描述的，如：
 * &lt;then value="a,b,c"/&gt;，那么a、b、c分别作为可执行节点
 * </p>
 *
 * @author cwk
 * @version 1.0
 */
public abstract class Flow implements Executable {

	// 所包含的可执行节点
	protected List<Executable> subFlow = new LinkedList<>();

	// 由特定的实现类进行实现
	public abstract FlowType getFlowType();
}
