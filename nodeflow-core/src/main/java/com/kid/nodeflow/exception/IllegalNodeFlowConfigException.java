package com.kid.nodeflow.exception;

public class IllegalNodeFlowConfigException extends RuntimeException {
	public IllegalNodeFlowConfigException(String message) {
		super(message);
	}

	public IllegalNodeFlowConfigException(String template, Object... values) {
		super(String.format(template, values));
	}
}
