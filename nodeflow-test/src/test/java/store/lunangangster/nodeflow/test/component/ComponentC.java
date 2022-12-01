package store.lunangangster.nodeflow.test.component;

import store.lunangangster.nodeflow.core.component.NodeComponent;

public class ComponentC extends NodeComponent {

	@Override
	public void process() {
		System.out.println("I'm C");
	}
}
