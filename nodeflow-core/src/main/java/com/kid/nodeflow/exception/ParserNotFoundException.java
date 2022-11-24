package com.kid.nodeflow.exception;

public class ParserNotFoundException extends RuntimeException {
	public ParserNotFoundException(String message) {
		super(message);
	}

	public ParserNotFoundException(String template, Object... values) {
		super(String.format(template, values));
	}
}
