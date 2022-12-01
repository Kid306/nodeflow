package store.lunangangster.nodeflow.exception.rt;

public class UnInitializedFlowException extends RuntimeException {
	public UnInitializedFlowException(String message) {
		super(message);
	}

	public UnInitializedFlowException(String template, Object... values) {
		super(String.format(template, values));
	}
}
