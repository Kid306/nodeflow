package store.lunangangster.nodeflow.test.context.local;

import store.lunangangster.nodeflow.context.FlowContext;

public class TestFlowContext implements FlowContext {

	@Override
	public <T> T getBean(Class<T> requiredType) {
		return null;
	}

	@Override
	public Object getBean(String name) {
		return null;
	}

	@Override
	public <T> T getBean(String name, Class<T> requiredType) {
		return null;
	}

	@Override
	public int priority() {
		return Integer.MAX_VALUE;
	}
}
