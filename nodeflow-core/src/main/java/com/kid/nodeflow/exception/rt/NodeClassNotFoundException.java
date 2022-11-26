package com.kid.nodeflow.exception.rt;

public class NodeClassNotFoundException extends RuntimeException {

	public NodeClassNotFoundException(String message) {
		super(message);
	}

	public NodeClassNotFoundException(String template, Object... values) {
		super(String.format(template, values));
	}
}
