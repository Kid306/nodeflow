package store.lunangangster.nodeflow.rt.element;

import store.lunangangster.nodeflow.rt.Slot;
import store.lunangangster.nodeflow.rt.element.flow.Flow;

/**
 * <p>执行流程中，所有可执行对象的公共接口</p>
 * <p>
 *   被执行当对象都会被分配{@link Slot}，用于存储上下文数据。
 *   典型的Executable对象有：
 *    <ul>
 *      <li>{@link Node}</li>
 *      <li>{@link Chain}</li>
 *      <li>{@link Flow}</li>
 *    </ul>
 * </p>
 *
 * @author cwk
 * @version 1.0
 */
public interface Executable {
	/**
	 * <p>可执行接口，只有当Executable对象执行时，才会分配Slot对象</p>
	 * <p>该方法被调用时，即为Executable对象分配Slot时</p>
	 */
	public void execute(Integer slotIndex);
}
