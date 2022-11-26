package com.kid.nodeflow.rt.element.flow;

import static com.kid.nodeflow.enums.FlowType.FLOW_IF;
import static com.kid.nodeflow.rt.Slot.SlotConstant.IF_PREFIX;

import com.kid.nodeflow.enums.FlowType;
import com.kid.nodeflow.rt.DataBus;
import com.kid.nodeflow.rt.Slot;
import com.kid.nodeflow.rt.element.Executable;
import com.kid.nodeflow.rt.element.Node;

public class IfFlow extends Flow {

	private Executable trueBranch;

	private Executable falseBranch;

	// need no arguments constructor
	public IfFlow() {}

	@Override
	public void execute(Integer slotIndex) {
		// 执行条件节点
		Node ifNode = (Node) this.executableList.get(0);
		ifNode.execute(slotIndex);
		// 获取result
		Slot slot = DataBus.getSlot(slotIndex);
		boolean result = slot.getData(IF_PREFIX + ifNode.getClazz().getName());
		if (result) {
			trueBranch.execute(slotIndex);
		} else {
			if (falseBranch != null) {
				falseBranch.execute(slotIndex);
			}
		}
	}

	@Override
	public FlowType getFlowType() {
		return FLOW_IF;
	}

	public Executable getTrueBranch() {
		return trueBranch;
	}

	public Executable getFalseBranch() {
		return falseBranch;
	}

	public void setBranch(Executable trueBranch, Executable falseBranch) {
		this.trueBranch = trueBranch;
		this.falseBranch = falseBranch;
	}
}
