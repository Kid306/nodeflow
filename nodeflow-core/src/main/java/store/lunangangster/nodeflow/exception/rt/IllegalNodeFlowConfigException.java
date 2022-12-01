package store.lunangangster.nodeflow.exception.rt;

public class IllegalNodeFlowConfigException extends RuntimeException {
	public IllegalNodeFlowConfigException(String message) {
		super(message);
	}

	public IllegalNodeFlowConfigException(String template, Object... values) {
		super(String.format(template, values));
	}
}
