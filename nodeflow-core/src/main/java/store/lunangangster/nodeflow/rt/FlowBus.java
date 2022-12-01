package store.lunangangster.nodeflow.rt;

import cn.hutool.core.util.StrUtil;
import store.lunangangster.nodeflow.context.FlowContext;
import store.lunangangster.nodeflow.context.FlowContextHolder;
import store.lunangangster.nodeflow.context.lcoal.LocalFlowContext;
import store.lunangangster.nodeflow.core.component.NodeComponent;
import store.lunangangster.nodeflow.exception.rt.SystemInitializeException;
import store.lunangangster.nodeflow.rt.element.Chain;
import store.lunangangster.nodeflow.rt.element.Executable;
import store.lunangangster.nodeflow.rt.element.Node;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>整个执行流程过程中的元素总线</p>
 * <p>由于Node和Chain的定义是全局的，所以使用了static进行修饰</p>
 * 
 * @author cwk
 * @version 1.0
 */
public class FlowBus {
	// 装载了所有已定义的Node
	private static final Map<String, Node> nodeMap = new HashMap<>();
	// 装载了所有已定义的Chain
	private static final Map<String, Chain> chainMap = new HashMap<>();

	static void init() {
		// do nothing
	}

	/**
	 * 该方法保证了node与子流程chain同名时的优先级
	 */
	public static Executable getExecutable(String id) {
		Node node = getNode(id);
		if (node != null) {
			return node;
		}
		return getChain(id);
	}

	/**
	 * <p>FlowBus的清理工作</p>
	 * <p>该方法是一个非阻塞方法，该方法执行之前必须保证NodeFlow没有启动</p>
	 */
	public static void clear() {
		if (NodeFlowRuntime.isUnInitialized()) {
			nodeMap.clear();
			chainMap.clear();
		}
	}

	/**
	 * 向FlowBus中添加Node，添加之前需要获取Node对应的NodeComponent实例对象。
	 * 针对不同的环境，NodeComponent对象的获取方式是不同的。对于非Spring环境，直接通过class反射生成相应对象；
	 * 而对于Spring环境，需要从IOC容器中获取已经配置的NodeComponent对象。
	 */
	public static void addNode(Node node) {
		if (node == null || StrUtil.isBlank(node.getId()) || node.getClazz() == null || node.getType() == null) {
			return;
		}
		try {
			Class<?> clazz = node.getClazz();
			String id = node.getId();
			NodeComponent nodeComponent;
			// 这里通过spi来判断是否是Spring环境
			FlowContext context = FlowContextHolder.getContext();
			if (LocalFlowContext.class.isAssignableFrom(context.getClass())) {
				// 在非Spring环境下，需要newInstance
				nodeComponent = (NodeComponent) clazz.newInstance();
				node.setNodeComponent(nodeComponent);
			} else {
				// 在Spring环境下，已经保证了Node#nodeComponent被设置了，所以不需要做任何处理
				// nodeComponent = (NodeComponent) context.getBean(id, clazz);
			}
			nodeMap.put(id, node);
		} catch (Exception e) {
			throw new SystemInitializeException(e);
		}
	}

	public static Node getNode(String nodeId) {
		return nodeMap.get(nodeId);
	}

	public static boolean containNode(String nodeId) {
		return nodeMap.containsKey(nodeId);
	}


	public static void addChain(Chain chain) {
		if (chain != null && StrUtil.isNotBlank(chain.getId())) {
			chainMap.put(chain.getId(), chain);
		}
	}

	public static Chain getChain(String chainId) {
		return chainMap.get(chainId);
	}
}
