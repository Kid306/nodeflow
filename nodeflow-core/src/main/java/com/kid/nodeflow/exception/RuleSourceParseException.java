package com.kid.nodeflow.exception;

public class RuleSourceParseException extends RuntimeException {
	public RuleSourceParseException(String message) {
		super(message);
	}

	public RuleSourceParseException(String template, Object... values) {
		super(String.format(template, values));
	}
}
