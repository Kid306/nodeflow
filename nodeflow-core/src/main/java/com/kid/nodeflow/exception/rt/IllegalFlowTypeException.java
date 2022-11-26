package com.kid.nodeflow.exception.rt;

public class IllegalFlowTypeException extends RuntimeException {
	public IllegalFlowTypeException(String message) {
		super(message);
	}

	public IllegalFlowTypeException(String template, Object... values) {
		super(String.format(template, values));
	}
}
