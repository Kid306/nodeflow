package store.lunangangster.nodeflow.exception.rt;

public class IllegalNodeExpressionException extends RuntimeException {
	public IllegalNodeExpressionException(String message) {
		super(message);
	}

	public IllegalNodeExpressionException(String template, Object... values) {
		super(String.format(template, values));
	}
}
