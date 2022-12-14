package store.lunangangster.nodeflow.exception.rt;

public class ComponentInstantiationException extends RuntimeException {
	public ComponentInstantiationException(String message) {
		super(message);
	}

	public ComponentInstantiationException(String template, Object... values) {
		super(String.format(template, values));
	}
}
