package com.kid.nodeflow.builder;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.kid.nodeflow.context.FlowBus;
import com.kid.nodeflow.context.element.Chain;
import com.kid.nodeflow.context.element.flow.Flow;
import com.kid.nodeflow.exception.rt.NodeBuildException;
import java.util.ArrayList;
import java.util.List;

/**
 * Chain的构建器
 *
 * @author cwk
 * @version 1.0
 */
public class ChainBuilder {

	private final Chain chain;

	private ChainBuilder() {
		this.chain = new Chain();
	}

	public void build() {
		// build前的简单检查
		checkBuild();
		// 向FlowBus中添加Chain
		FlowBus.addChain(chain);
	}

	/**
	 * 开始构建Chain
	 */
	public static ChainBuilder start() {
		return new ChainBuilder();
	}

	public ChainBuilder id(String id) {
		this.chain.setId(id);
		return this;
	}

	public ChainBuilder flowList(List<Flow> flowList) {
		this.chain.setFlowList(flowList);
		return this;
	}

	private void checkBuild() {
		boolean isError = StrUtil.isBlank(chain.getId()) || CollUtil.isEmpty(chain.getFlowList());
		List<String> errorInfos = null;
		if (!isError) {
			return;
		}
		errorInfos = new ArrayList<>();
		if (StrUtil.isBlank(chain.getId())) {
			errorInfos.add("chain's id is blank");
		}
		if (CollUtil.isEmpty(chain.getFlowList())) {
			errorInfos.add("chain's flowList is null or empty");
		}
		throw new NodeBuildException(CollUtil.join(errorInfos, ", ", "[", "]"));
	}
}
