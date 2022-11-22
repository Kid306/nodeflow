package com.kid.nodeflow.context;

import static com.kid.nodeflow.common.BaseConstant.DEFAULT_SLOTS_SIZE;

import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * <p>整个执行流程过程中的数据总线，用于管理和分配Slot</p>
 *
 * @author cwk
 * @version 1.0
 */
public class DataBus {
	// 槽位当前容量
	private static Integer SLOT_CAPACITY = DEFAULT_SLOTS_SIZE;

	// 总槽位。ConcurrentHashMap会解决线程问题和扩容问题
	private static Map<Integer, Slot> SLOTS = new ConcurrentHashMap<>(DEFAULT_SLOTS_SIZE, 0.75f);

	// 保存空闲的槽的index
	private static final Queue<Integer> FREE_INDEX = new ConcurrentLinkedQueue<>();

	static {
		init();
	}

	private static void init() {
		FREE_INDEX.addAll(IntStream.range(0, DEFAULT_SLOTS_SIZE).boxed().collect(Collectors.toList()));
	}

	private DataBus() {}

	/**
	 * 获取下一个可用槽位
	 * @return 下一个可用槽位的index。当无可用槽时返回null
	 */
	public static Integer nextSlot() {
		if (FREE_INDEX.isEmpty()) {
			capacity();
		}
		// CAS保证线程安全
		return FREE_INDEX.poll();
	}

	/**
	 * 释放当前槽位
	 */
	public static void freeSlot(Integer index) {
		if (SLOTS.containsKey(index)) {
			FREE_INDEX.offer(index);
		}
	}

	/**
	 * 获取对应槽位中的Slot
	 */
	public static Slot getSlot(Integer index) {
			return SLOTS.get(index);
	}

	/**
	 * 扩容操作
	 */
	private synchronized static void capacity() {
		if (FREE_INDEX.isEmpty()) {
			int oldCapacity = SLOT_CAPACITY;
			SLOT_CAPACITY += (SLOT_CAPACITY >> 2) + (SLOT_CAPACITY >> 1);
			// 注意这里只需要向FREE_INDEX中添加可用槽位即可，无需考虑SLOTS对扩容
			IntStream.range(oldCapacity, SLOT_CAPACITY).boxed().forEach(FREE_INDEX::offer);
		}
	}
}
