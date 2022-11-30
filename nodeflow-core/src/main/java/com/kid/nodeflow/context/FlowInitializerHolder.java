package com.kid.nodeflow.context;

import java.util.Comparator;
import java.util.ServiceLoader;
import java.util.TreeSet;

/**
 * 用于保存和获取FlowInitializer的类
 */
public class FlowInitializerHolder {
	private static volatile NodesInitializer initializer;

	private static final TreeSet<NodesInitializer> initializerSet = new TreeSet<>(Comparator.comparingInt(Order::priority));

	public static NodesInitializer getInitializer() {
		if (initializer == null) {
			synchronized (FlowInitializerHolder.class) {
				if (initializer == null) {
					ServiceLoader<NodesInitializer> serviceLoader = ServiceLoader.load(NodesInitializer.class);
					serviceLoader.forEach(initializerSet::add);
					initializer = initializerSet.first();
					initializerSet.clear();
				}
			}
		}
		return initializer;
	}
}
