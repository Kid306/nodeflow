package com.kid.nodeflow.exception.rt;

public class IllegalValueExpressionException extends RuntimeException{
	public IllegalValueExpressionException(String message) {
		super(message);
	}

	public IllegalValueExpressionException(String template, Object... values) {
		super(String.format(template, values));
	}
}
