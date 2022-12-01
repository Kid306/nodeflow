package store.lunangangster.nodeflow.exception.rt;

public class NoSuchComponentException extends RuntimeException {
	public NoSuchComponentException(String message) {
		super(message);
	}

	public NoSuchComponentException(String template, Object... values) {
		super(String.format(template, values));
	}
}
