package com.kid.nodeflow.exception;

public class NodeBuildException extends RuntimeException {
	public NodeBuildException(String message) {
		super(message);
	}

	public NodeBuildException(String template, Object... values) {
		super(String.format(template, values));
	}
}
