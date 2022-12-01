package store.lunangangster.nodeflow.context;

/**
 * <p>NodeFlow中所有Node的初始化器，用于解析Node和初始化FlowBus。</p>
 *
 * <p>对于非Spring环境下，不需要对Nodes进行初始化，因为在解析RuleSource对过程中，就已经解析完Nodes部分，
 *  将所有Node的声明添加到FlowBus中。</p>
 *
 * <p>对于Spring环境下，需要将IOC中扫描到的NodeComponent对象build为Node对象添加到FlowBus中</p>
 *
 *
 * @author cwk
 * @version 1.0
 */
public interface NodesInitializer extends Order {

	/**
	 * 初始化方法，执行完该方法后，需要保证NodeFlow系统中所有Node均被解析且装载
	 */
	public void initialize();
}
