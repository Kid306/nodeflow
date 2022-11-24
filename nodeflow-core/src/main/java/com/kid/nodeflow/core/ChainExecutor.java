package com.kid.nodeflow.core;

import static com.kid.nodeflow.common.ResponseCode.CHAIN_NOT_FOUND;
import static com.kid.nodeflow.common.ResponseCode.SUCCESS;

import cn.hutool.core.io.IoUtil;
import com.kid.nodeflow.config.NodeFlowConfig;
import com.kid.nodeflow.config.NodeFlowConfigHolder;
import com.kid.nodeflow.exception.ChainNotFoundException;
import com.kid.nodeflow.rt.DataBus;
import com.kid.nodeflow.rt.FlowBus;
import com.kid.nodeflow.rt.element.Chain;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Chain流程的执行器
 *
 * @author cwk
 * @version 1.0
 */
public class ChainExecutor {

	public ChainExecutor() {
		this.init();
	}

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
		System.out.printf("Chain[%s] is assigned executing in Slot[%s]\n", chainId, slotIndex);
		// 解析
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

	/**
	 * <p>Executor的初始化方法，目前可以认为是整个NodeFlow的初始化方法</p>
	 * <p>
	 *   主要作用是
	 *   <ol>
	 *     <li>读取Rule Source文件，并且解析</li>
	 *   </ol>
	 * </p>
	 */
	private void init() {
		NodeFlowConfig config = NodeFlowConfigHolder.getConfig();
		// 1. 读取和解析RuleSource
		List<String> ruleSourcePath = config.getRuleSourcePath();
		List<String> contents = new ArrayList<>();
		// 从类路径读取文件
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		for (String path : ruleSourcePath) {
			InputStream is = getClass().getResourceAsStream(path);
			IoUtil.copy(is, os);
			contents.add(os.toString());
		}
		// 根据文件路径来判断选择哪种解析器
		// TODO 这里调用的findParser方法效率较低，后续需要优化
	}
}
