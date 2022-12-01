package store.lunangangster.nodeflow.rt.element.flow;

import static store.lunangangster.nodeflow.enums.FlowType.FLOW_THEN;

import store.lunangangster.nodeflow.enums.FlowType;
import store.lunangangster.nodeflow.rt.element.Executable;

public class ThenFlow extends Flow {

	// need no arguments constructor
	public ThenFlow() {}

	@Override
	public void execute(Integer slotIndex) {
		// 串行化执行所有子节点
		for (Executable exec : this.executableList) {
			exec.execute(slotIndex);
		}
	}

	@Override
	public FlowType getFlowType() {
		return FLOW_THEN;
	}
}
