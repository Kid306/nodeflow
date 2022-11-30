package com.kid.nodeflow.context.spring;

import com.kid.nodeflow.builder.entity.NodeProp;
import com.kid.nodeflow.core.component.NodeComponent;
import com.kid.nodeflow.enums.NodeType;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

/**
 * 用于Spring环境下的Node的扫描与解析，并将解析出的Node添加到FlowBus中
 *
 * @author cwk
 * @version 1.0
 */
public class SpringNodeRegister implements BeanPostProcessor {

	private static final Map<String, NodeProp> nodeCompMap = new HashMap<>();

	@Override
	public Object postProcessBeforeInitialization(Object bean, String beanName)
			throws BeansException {
		return bean;
	}

	/**
	 * 扫描到Bean之后的操作，需要将NodeComponent的子类对象添加到FlowBus中
	 */
	@Override
	public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
		if (NodeComponent.class.isAssignableFrom(bean.getClass())) {
			Class<?> clazz = bean.getClass();
			String className = clazz.getName();
			NodeType type = NodeType.guessType(clazz);
			NodeComponent component = (NodeComponent) bean;
			System.out.println(clazz);
			// 这里不能解析ApplicationContext时就buildNode，因为buildNode时需要得到ApplicationContext对象，造成了循环依赖
			NodeProp nodeProp = new NodeProp().setId(beanName).setClazz(clazz).setClassName(className)
					.setType(type).setNodeComponent(component);
			nodeCompMap.put(beanName, nodeProp);
		}
		return bean;
	}

	public static Map<String, NodeProp> getNodeCompMap() {
		return nodeCompMap;
	}
}
