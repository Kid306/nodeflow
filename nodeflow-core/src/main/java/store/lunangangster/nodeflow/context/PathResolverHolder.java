package store.lunangangster.nodeflow.context;

import java.util.Comparator;
import java.util.ServiceLoader;
import java.util.TreeSet;

/**
 * 用于保存和获取PathResolver的类
 */
public class PathResolverHolder {
	private static volatile PathResolver resolver;

	private static final TreeSet<PathResolver> resolverSet = new TreeSet<>(Comparator.comparingInt(Order::priority));

	public static PathResolver getResolver() {
		if (resolver == null) {
			synchronized (PathResolverHolder.class) {
				if (resolver == null) {
					ServiceLoader<PathResolver> serviceLoader = ServiceLoader.load(PathResolver.class);
					serviceLoader.forEach(resolverSet::add);
					resolver = resolverSet.first();
					resolverSet.clear();
				}
			}
		}
		return resolver;
	}
}
