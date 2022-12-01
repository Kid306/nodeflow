package store.lunangangster.nodeflow.core.component;

import static store.lunangangster.nodeflow.rt.Slot.SlotConstant.IF_PREFIX;

/**
 * 条件Node
 */
public abstract class IfNodeComponent extends NodeComponent {

	@Override
	public void process() {
		boolean result = this.processIf();
		// 根据result结果进行后续流程
		// TODO 目前是有问题的，因为当一个IfNode在同一个Chain中出现多次时(包括子流程)，会共用一个Slot，很可能造成result的覆盖
		this.getSlot().setData(IF_PREFIX + this.getClass().getName(), result);
	}

	public abstract boolean processIf();
}
