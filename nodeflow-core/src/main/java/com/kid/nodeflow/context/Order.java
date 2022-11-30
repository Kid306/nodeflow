package com.kid.nodeflow.context;

/**
 * 用于设置优先级的接口
 *
 * @author cwk
 * @version 1.0
 */
public interface Order {

	/**
	 * 以int表示的优先级，优先级越高，值越小。最高优先级为0
	 */
	public int priority();
}
