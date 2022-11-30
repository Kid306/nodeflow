package com.kid.nodeflow.test.component.spring;

import com.kid.nodeflow.core.component.NodeComponent;
import org.springframework.stereotype.Component;

@Component("springD")
public class SpringCompD extends NodeComponent {

	@Override
	public void process() {
		System.out.println("I'm springD");
	}
}
