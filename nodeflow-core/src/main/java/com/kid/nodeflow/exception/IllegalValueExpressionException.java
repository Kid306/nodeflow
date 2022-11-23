package com.kid.nodeflow.exception;

public class IllegalValueExpressionException extends RuntimeException{
	public IllegalValueExpressionException(String message) {
		super(message);
	}

	public IllegalValueExpressionException(String template, Object... values) {
		super(String.format(template, values));
	}
}
