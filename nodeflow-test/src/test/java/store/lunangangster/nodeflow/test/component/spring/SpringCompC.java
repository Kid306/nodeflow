package store.lunangangster.nodeflow.test.component.spring;

import store.lunangangster.nodeflow.core.component.NodeComponent;
import org.springframework.stereotype.Component;

@Component("springC")
public class SpringCompC extends NodeComponent {

	@Override
	public void process() {
		System.out.println("I'm springC");
	}
}
