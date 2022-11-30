package com.kid.nodeflow.context.lcoal;

import com.kid.nodeflow.context.Order;

/**
 * 本地环境下所有的优先级设置，默认优先级最低
 *
 * @author cwk
 * @version 1.0
 */
public class Local implements Order {

	@Override
	public int priority() {
		return Integer.MAX_VALUE;
	}
}
