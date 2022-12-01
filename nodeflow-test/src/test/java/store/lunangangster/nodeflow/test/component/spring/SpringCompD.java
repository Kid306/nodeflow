package store.lunangangster.nodeflow.test.component.spring;

import store.lunangangster.nodeflow.core.component.NodeComponent;
import org.springframework.stereotype.Component;

@Component("springD")
public class SpringCompD extends NodeComponent {

	@Override
	public void process() {
		System.out.println("I'm springD");
	}
}
