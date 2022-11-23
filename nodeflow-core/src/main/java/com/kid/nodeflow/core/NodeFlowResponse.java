package com.kid.nodeflow.core;

import com.kid.nodeflow.common.ResponseCode;

/**
 * 执行结果相应体
 * 
 * @author cwk
 * @version 1.0
 */
public class NodeFlowResponse {
	private boolean success;

	private String message;

	private int code;

	private Throwable cause;

	private NodeFlowResponse() {}

	public static NodeFlowResponse getInstance() {
		return new NodeFlowResponse();
	}

	public NodeFlowResponse success(boolean success) {
		this.success = success;
		return this;
	}

	public NodeFlowResponse message(String message) {
		this.message = message;
		return this;
	}

	public NodeFlowResponse status(ResponseCode respCode) {
		if (respCode != null) {
			this.code = respCode.code;
			this.message = respCode.message;
		}
		return this;
	}

	public NodeFlowResponse code(int code) {
		this.code = code;
		return this;
	}

	public NodeFlowResponse cause(Throwable cause) {
		this.cause = cause;
		return this;
	}

	public boolean isSuccess() {
		return success;
	}

	public String getMessage() {
		return message;
	}

	public int getCode() {
		return code;
	}

	public Throwable getCause() {
		return cause;
	}
}
