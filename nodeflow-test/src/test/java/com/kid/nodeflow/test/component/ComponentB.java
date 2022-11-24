package com.kid.nodeflow.test.component;

import com.kid.nodeflow.core.component.NodeComponent;

public class ComponentB extends NodeComponent {

	@Override
	public void process() {
		System.out.println("I'm B");
	}
}
