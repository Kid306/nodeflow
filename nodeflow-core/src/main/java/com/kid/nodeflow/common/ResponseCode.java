package com.kid.nodeflow.common;

public enum ResponseCode {
	SUCCESS(100, "Execute successfully"),
	RULE_SOURCE_NOT_FOUND(201, "Rule Source file not found"),
	CHAIN_RESOLVE_FAIL(211, "The chain defined in Rule Source resolve fail"),
	CHAIN_NOT_FOUND(212, "The chainId is not found in Rule Source");

	public final int code;

	public final String message;

	ResponseCode(int code, String message) {
		this.code = code;
		this.message = message;
	}
}
