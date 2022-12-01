package store.lunangangster.nodeflow.exception;

public class RuleSourceParseException extends Exception {
	public RuleSourceParseException(String message) {
		super(message);
	}

	public RuleSourceParseException(String template, Object... values) {
		super(String.format(template, values));
	}
}
