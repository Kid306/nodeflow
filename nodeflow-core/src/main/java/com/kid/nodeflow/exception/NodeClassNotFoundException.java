package com.kid.nodeflow.exception;

public class NodeClassNotFoundException extends Exception {

	public NodeClassNotFoundException() {
		super();
	}

	public NodeClassNotFoundException(String message) {
		super(message);
	}

	public NodeClassNotFoundException(String template, Object... values) {
		super(String.format(template, values));
	}

	public NodeClassNotFoundException(Throwable cause) {
		super(cause);
	}
}
