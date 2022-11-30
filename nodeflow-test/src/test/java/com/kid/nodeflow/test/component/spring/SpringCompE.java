package com.kid.nodeflow.test.component.spring;

import com.kid.nodeflow.core.component.IfNodeComponent;
import java.util.Random;
import org.springframework.stereotype.Component;

@Component("springE")
public class SpringCompE extends IfNodeComponent {

	@Override
	public boolean processIf() {
		boolean result = new Random().nextBoolean();
		System.out.println("I'm springE, a IfNodeComponent. I choose " + result);
		return result;
	}
}
