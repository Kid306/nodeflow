package store.lunangangster.nodeflow.exception.rt;

public class ChainNotFoundException extends RuntimeException {
	public ChainNotFoundException(String message) {
		super(message);
	}

	public ChainNotFoundException(String template, Object... values) {
		super(String.format(template, values));
	}
}
