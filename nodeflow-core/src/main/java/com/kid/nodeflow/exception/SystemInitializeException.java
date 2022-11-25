package com.kid.nodeflow.exception;

public class SystemInitializeException extends RuntimeException {
	public SystemInitializeException(String message) {
		super(message);
	}

	public SystemInitializeException(String template, Object... values) {
		super(String.format(template, values));
	}
}
