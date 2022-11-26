package com.kid.nodeflow.exception.rt;

public class RuleSourceParseException extends RuntimeException {
	public RuleSourceParseException(String message) {
		super(message);
	}

	public RuleSourceParseException(String template, Object... values) {
		super(String.format(template, values));
	}
}
