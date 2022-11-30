package com.kid.nodeflow.test.component.spring;

import com.kid.nodeflow.core.component.NodeComponent;
import org.springframework.stereotype.Component;

@Component("springB")
public class SpringCompB extends NodeComponent {

	@Override
	public void process() {
		System.out.println("I'm springB");
	}
}
