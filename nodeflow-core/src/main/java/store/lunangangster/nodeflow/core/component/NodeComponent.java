package store.lunangangster.nodeflow.core.component;

import store.lunangangster.nodeflow.rt.DataBus;
import store.lunangangster.nodeflow.rt.Slot;

/**
 * 基础Node抽象类
 *
 * @author cwk
 * @version 1.0
 */
public abstract class NodeComponent {
	// Node#id
	private String nodeId;
	// Node执行时所对应的槽
	private Integer slotIndex;

	public abstract void process();

	public String getNodeId() {
		return nodeId;
	}

	public void setNodeId(String nodeId) {
		this.nodeId = nodeId;
	}

	public Slot getSlot() {
		return DataBus.getSlot(slotIndex);
	}

	public void setSlotIndex(Integer slotIndex) {
		this.slotIndex = slotIndex;
	}
}
