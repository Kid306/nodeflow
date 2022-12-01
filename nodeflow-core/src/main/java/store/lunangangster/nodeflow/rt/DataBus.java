package store.lunangangster.nodeflow.rt;

import store.lunangangster.nodeflow.common.BaseConstant;
import store.lunangangster.nodeflow.exception.rt.SystemInitializeException;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * <p>整个执行流程过程中的数据总线，用于管理和分配Slot</p>
 *
 * @author cwk
 * @version 1.0
 */
public class DataBus {

	private static final AtomicBoolean IS_INIT = new AtomicBoolean(false);
	// 槽位当前容量
	private static Integer SLOT_CAPACITY;
	// 总槽位。ConcurrentHashMap会解决线程问题和扩容问题
	private static Map<Integer, Slot> SLOTS;
	// 保存空闲的槽的index
	private static final Queue<Integer> FREE_INDEX = new ConcurrentLinkedQueue<>();

	static void init() {
		if (NodeFlowRuntime.isInitializing()) {
			SLOT_CAPACITY = NodeFlowRuntime.getConfig().getSlotsSize();
			SLOTS = new ConcurrentHashMap<>(SLOT_CAPACITY);
			FREE_INDEX.addAll(IntStream.range(0, SLOT_CAPACITY)
					.boxed().collect(Collectors.toList()));
			IS_INIT.set(true);
		}
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
		Integer index = FREE_INDEX.poll();
		Slot slot = new Slot(index);
		SLOTS.put(index, slot);
		return index;
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
	 * <p>DataBus的清理工作，执行完成之后的状态与执行init方法之前相同</p>
	 * <p>该方法是一个非阻塞方法，该方法执行之前必须保证NodeFlow没有启动</p>
	 */
	public static void clear() {
		// 这里要保证系统没有运行时才可以进行清理工作
		// 注意：系统的停止运行不是不应该在这里完成，或者说该方法不应该阻塞等待系统停止
		if (NodeFlowRuntime.isUnInitialized()) {
			SLOT_CAPACITY = BaseConstant.DEFAULT_SLOTS_SIZE;
			SLOTS.clear();
			FREE_INDEX.clear();
		}
	}

	/**
	 * 扩容操作
	 */
	private synchronized static void capacity() {
		if (!IS_INIT.get()) {
			throw new SystemInitializeException("Data Bus is not init");
		}
		if (FREE_INDEX.isEmpty()) {
			int oldCapacity = SLOT_CAPACITY;
			SLOT_CAPACITY += (SLOT_CAPACITY >> 2) + (SLOT_CAPACITY >> 1);
			// 注意这里只需要向FREE_INDEX中添加可用槽位即可，无需考虑SLOTS对扩容
			IntStream.range(oldCapacity, SLOT_CAPACITY).boxed().forEach(FREE_INDEX::offer);
		}
	}
}
