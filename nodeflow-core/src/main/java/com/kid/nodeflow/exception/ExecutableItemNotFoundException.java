package com.kid.nodeflow.exception;

public class ExecutableItemNotFoundException extends Exception {
	public ExecutableItemNotFoundException(String message) {
		super(message);
	}

	public ExecutableItemNotFoundException(String template, Object... values) {
		super(String.format(template, values));
	}
}
