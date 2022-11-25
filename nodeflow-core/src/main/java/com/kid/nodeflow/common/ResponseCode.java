package com.kid.nodeflow.common;

public enum ResponseCode {
	SYSTEM_NOT_INIT(10, "NodeFlow haven't init"),
	RULE_SOURCE_NOT_FOUND(101, "Rule Source file not found"),
	CHAIN_RESOLVE_FAIL(111, "The chain defined in Rule Source resolve fail"),
	CHAIN_NOT_FOUND(112, "The chainId is not found in Rule Source"),
	SUCCESS(500, "Execute successfully");

	/**
	 * code[100, 200)：解析过程中出现的问题
	 * code <= 300：NodeFlow启动之前产生的Response
	 * code > 300 ：ChainExecutor运行之后产生的Response
	 */
	public final int code;

	public final String message;

	ResponseCode(int code, String message) {
		this.code = code;
		this.message = message;
	}
}
