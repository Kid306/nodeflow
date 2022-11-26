package com.kid.nodeflow.test.component;

import com.kid.nodeflow.core.component.IfNodeComponent;
import java.util.Random;

public class ComponentE extends IfNodeComponent {

	@Override
	public boolean processIf() {
		boolean result = (new Random().nextInt() % 2) == 0;
		System.out.println("I'm E, a IfNodeComponent. I choose \" "+ result + "\"");
		return result;
	}
}
