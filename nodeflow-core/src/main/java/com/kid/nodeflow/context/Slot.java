package com.kid.nodeflow.context;

/**
 * <p>数据槽对象，每一个流程被执行时就会分配一个新的Slot对象</p>
 * 
 * @author cwk
 * @version 1.0
 */
public class Slot {
	// Slot对应的槽位
	private Integer index;

	public Integer getIndex() {
		return index;
	}

	public Slot setIndex(Integer index) {
		this.index = index;
		return this;
	}
}
