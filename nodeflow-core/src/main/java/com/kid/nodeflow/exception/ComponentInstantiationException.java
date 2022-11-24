package com.kid.nodeflow.exception;

public class ComponentInstantiationException extends RuntimeException {
	public ComponentInstantiationException(String message) {
		super(message);
	}

	public ComponentInstantiationException(String template, Object... values) {
		super(String.format(template, values));
	}
}
