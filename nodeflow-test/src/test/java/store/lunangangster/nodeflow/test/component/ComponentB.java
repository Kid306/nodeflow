package store.lunangangster.nodeflow.test.component;

import store.lunangangster.nodeflow.core.component.NodeComponent;

public class ComponentB extends NodeComponent {

	@Override
	public void process() {
		System.out.println("I'm B");
	}
}
