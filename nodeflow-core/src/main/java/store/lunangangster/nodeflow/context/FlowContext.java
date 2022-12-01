package store.lunangangster.nodeflow.context;

/**
 * NodeFlow上下文容器的抽象接口
 *
 * @author cwk
 * @version 1.0
 */
public interface FlowContext extends Order {
	public <T> T getBean(Class<T> requiredType);

	public Object getBean(String name);

	public <T> T getBean(String name, Class<T> requiredType);
}
