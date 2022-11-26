package com.kid.nodeflow.exception.rt;

public class ParserNotFoundException extends RuntimeException {
	public ParserNotFoundException(String message) {
		super(message);
	}

	public ParserNotFoundException(String template, Object... values) {
		super(String.format(template, values));
	}
}
