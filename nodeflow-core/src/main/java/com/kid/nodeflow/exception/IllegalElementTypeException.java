package com.kid.nodeflow.exception;

public class IllegalElementTypeException extends RuntimeException {
	public IllegalElementTypeException(String message) {
		super(message);
	}

	public IllegalElementTypeException(String template, Object... values) {
		super(String.format(template, values));
	}
}
