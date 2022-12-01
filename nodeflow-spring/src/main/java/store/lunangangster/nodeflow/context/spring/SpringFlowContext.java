package store.lunangangster.nodeflow.context.spring;

import store.lunangangster.nodeflow.context.FlowContext;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * 基于Spring的FlowContext的实现。使用Spring默认的ApplicationContext
 */
public class SpringFlowContext implements FlowContext, ApplicationContextAware {
	private ApplicationContext context;

	@Override
	public <T> T getBean(Class<T> requiredType) {
		return context.getBean(requiredType);
	}

	@Override
	public Object getBean(String name) {
		return context.getBean(name);
	}

	@Override
	public <T> T getBean(String name, Class<T> requiredType) {
		return context.getBean(name, requiredType);
	}

	/**
	 * 交给Spring进行ApplicationContext注入
	 */
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.context = applicationContext;
	}

	@Override
	public int priority() {
		return 0;
	}
}
