package store.lunangangster.nodeflow.exception;

public class NodesLabelNotFoundException extends RuntimeException {
	public NodesLabelNotFoundException(String message) {
		super(message);
	}

	public NodesLabelNotFoundException(String template, Object... values) {
		super(String.format(template, values));
	}
}
