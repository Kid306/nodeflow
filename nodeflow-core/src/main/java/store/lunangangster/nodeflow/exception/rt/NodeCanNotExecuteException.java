package store.lunangangster.nodeflow.exception.rt;

public class NodeCanNotExecuteException extends RuntimeException {
	public NodeCanNotExecuteException(String message) {
		super(message);
	}

	public NodeCanNotExecuteException(String template, Object... values) {
		super(String.format(template, values));
	}
}
