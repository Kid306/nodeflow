package com.kid.nodeflow.exception.rt;

public class ChainsNotFoundException extends RuntimeException {
	public ChainsNotFoundException(String message) {
		super(message);
	}

	public ChainsNotFoundException(String template, Object... values) {
		super(String.format(template, values));
	}
}
