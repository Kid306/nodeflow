package store.lunangangster.nodeflow.context;

import java.util.Comparator;
import java.util.ServiceLoader;
import java.util.TreeSet;

/**
 * 用于保存和获取FlowContext的类
 *
 * @author cwk
 * @version 1.0
 */
public class FlowContextHolder {

	private static final TreeSet<FlowContext> contextSet = new TreeSet<>(Comparator.comparingInt(Order::priority));

	private static volatile FlowContext context;

	public static FlowContext getContext() {
		if (context == null) {
			synchronized (FlowContextHolder.class) {
				if (context == null) {
					ServiceLoader<FlowContext> serviceLoader = ServiceLoader.load(FlowContext.class);
					serviceLoader.forEach(contextSet::add);
					context = contextSet.first();
					contextSet.clear();
				}
			}
		}
		return context;
	}
}
