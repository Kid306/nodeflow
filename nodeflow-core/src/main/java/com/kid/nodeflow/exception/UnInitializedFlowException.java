package com.kid.nodeflow.exception;

public class UnInitializedFlowException extends RuntimeException {
	public UnInitializedFlowException(String message) {
		super(message);
	}

	public UnInitializedFlowException(String template, Object... values) {
		super(String.format(template, values));
	}
}
