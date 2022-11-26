package com.kid.nodeflow.rt;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * <p>数据槽对象，每一个流程被执行时就会分配一个新的Slot对象</p>
 * 
 * @author cwk
 * @version 1.0
 */
public class Slot {
	public static class SlotConstant {
		public static final String IF_PREFIX = "_IF#";
	}

	// Slot对应的槽位
	private Integer index;

	private final Map<String, Object> data = new ConcurrentHashMap<>();

	public Slot(Integer index) {
		this.index = index;
	}

	public void setData(String key, Object value) {
		data.put(key, value);
	}

	public <T> T getData(String key) {
		return (T) data.get(key);
	}

	public Integer getIndex() {
		return index;
	}

	public Slot setIndex(Integer index) {
		this.index = index;
		return this;
	}
}
