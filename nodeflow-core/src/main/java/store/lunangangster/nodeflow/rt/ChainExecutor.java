package store.lunangangster.nodeflow.rt;

import store.lunangangster.nodeflow.common.ResponseCode;
import store.lunangangster.nodeflow.config.NodeFlowConfig;
import store.lunangangster.nodeflow.core.NodeFlowResponse;
import store.lunangangster.nodeflow.exception.rt.ChainNotFoundException;
import store.lunangangster.nodeflow.rt.element.Chain;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Chain流程的执行器
 *
 * @author cwk
 * @version 1.0
 */
public class ChainExecutor {
	private NodeFlowConfig nodeFlowConfig;

	private static final Logger log = LoggerFactory.getLogger(ChainExecutor.class);

	// public ChainExecutor() {
	// 	if (needInit()) {
	// 		// 初始化是一个全局并且十分消耗性能的工作，要保证同步
	// 		synchronized (NodeFlowRuntime.class) {
	// 			if (needInit()) {
	// 				NodeFlowRuntime.init();
	// 			}
	// 		}
	// 	}
	// }

	public ChainExecutor(NodeFlowConfig nodeFlowConfig) {
		this.nodeFlowConfig = nodeFlowConfig;
	}

	/**
	 * 执行指定id的chain流程
	 */
	public NodeFlowResponse exec(String chainId) {
		if (needInit()) {
			if (!nodeFlowConfig.getParseOnStart()) {
				NodeFlowRuntime.init();
			} else {
				return NodeFlowResponse.getInstance()
						.success(false)
						.status(ResponseCode.SYSTEM_NOT_INIT);
			}
		}
		Integer slotIndex = DataBus.nextSlot();
		log.info("Chain[{}] is assigned executing in Slot[{}]", chainId, slotIndex);
		try {
			return this.exec(chainId, slotIndex);
		} catch (Exception cause) {
			cause.printStackTrace();
			return NodeFlowResponse.getInstance()
					.success(false)
					.message(cause.getMessage())
					.cause(cause);
		} finally {
			DataBus.freeSlot(slotIndex);
			log.info("Slot[{}] is set free", slotIndex);
			NodeFlowRuntime.setInit();
		}
	}

	/**
	 * 执行指定id的chain流程，使用slotIndex处的Slot，见{@link Slot}
	 */
	private NodeFlowResponse exec(String chainId, Integer slotIndex) {
		// 解析
		Chain chain = FlowBus.getChain(chainId);
		if (chain == null) {
			return NodeFlowResponse.getInstance()
					.success(false)
					.status(ResponseCode.CHAIN_NOT_FOUND)
					.cause(new ChainNotFoundException("chainId: %s, is not defined in Rule Source"));
		}
		chain.execute(slotIndex);
		return NodeFlowResponse.getInstance()
				.success(true)
				.status(ResponseCode.SUCCESS);
	}

	/**
	 * 该方法用于系统开始之前NodeFlow是否需要初始化的判断。在第一次初始化完成之后，
	 * 即使该方法返回false，也可以进行初始化操作
	 */
	private boolean needInit() {
		return NodeFlowRuntime.needInit();
	}
}
