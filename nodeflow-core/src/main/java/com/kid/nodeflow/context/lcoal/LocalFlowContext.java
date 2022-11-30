package com.kid.nodeflow.context.lcoal;

import com.kid.nodeflow.context.FlowContext;
import com.kid.nodeflow.exception.rt.NoSuchComponentException;

/**
 * FlowContext的默认实现
 *
 * @author cwk
 * @version 1.0
 */
public class LocalFlowContext extends Local implements FlowContext {

	// no argument constructor needed
	public LocalFlowContext() {}

	@Override
	public <T> T getBean(Class<T> requiredType) {
		throw new NoSuchComponentException(requiredType.getName());
	}

	@Override
	public Object getBean(String name) {
		throw new NoSuchComponentException(name);
	}

	@Override
	public <T> T getBean(String name, Class<T> requiredType) {
		throw new NoSuchComponentException(requiredType.getName() + "%" + name);
	}
}
