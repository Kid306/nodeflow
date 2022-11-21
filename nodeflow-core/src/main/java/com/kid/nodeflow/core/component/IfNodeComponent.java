package com.kid.nodeflow.core.component;

/**
 * 条件Node
 */
public abstract class IfNodeComponent extends NodeComponent {

	@Override
	public void process() {
		boolean result = this.processIf();
		// TODO 根据result结果进行后续流程
	}

	protected abstract boolean processIf();
}
