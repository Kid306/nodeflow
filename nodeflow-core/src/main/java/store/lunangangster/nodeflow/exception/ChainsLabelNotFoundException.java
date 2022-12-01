package store.lunangangster.nodeflow.exception;

public class ChainsLabelNotFoundException extends Exception {
	public ChainsLabelNotFoundException(String message) {
		super(message);
	}

	public ChainsLabelNotFoundException(String template, Object... values) {
		super(String.format(template, values));
	}
}
