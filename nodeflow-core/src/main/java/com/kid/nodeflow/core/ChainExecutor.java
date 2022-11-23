package com.kid.nodeflow.core;

import static com.kid.nodeflow.common.ResponseCode.CHAIN_NOT_FOUND;
import static com.kid.nodeflow.common.ResponseCode.SUCCESS;

import com.kid.nodeflow.exception.ChainNotFoundException;
import com.kid.nodeflow.rt.DataBus;
import com.kid.nodeflow.rt.FlowBus;
import com.kid.nodeflow.rt.element.Chain;

/**
 * Chain流程的执行器
 *
 * @author cwk
 * @version 1.0
 */
public class ChainExecutor {

	/**
	 * 执行指定id的chain流程
	 */
	public NodeFlowResponse exec(String chainId) {
		Integer slotIndex = DataBus.nextSlot();
		return this.exec(chainId, slotIndex);
	}

	/**
	 * 执行指定id的chain流程，使用slotIndex处的Slot，见{@link com.kid.nodeflow.rt.Slot}
	 */
	private NodeFlowResponse exec(String chainId, Integer slotIndex) {
		Chain chain = FlowBus.getChain(chainId);
		if (chain == null) {
			return NodeFlowResponse.getInstance()
					.success(false)
					.status(CHAIN_NOT_FOUND)
					.cause(new ChainNotFoundException("chainId: %s, is not defined in Rule Source"));
		}
		chain.execute(slotIndex);
		return NodeFlowResponse.getInstance()
				.success(true)
				.status(SUCCESS);
	}
}
