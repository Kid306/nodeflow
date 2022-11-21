package com.kid.nodeflow.enums;

public enum FlowType {
	FLOW_THEN("then"),
	FLOW_WHEN("when");

	private final String type;

	FlowType(String type) {
		this.type = type;
	}

	public String getType() {
		return this.type;
	}
}
