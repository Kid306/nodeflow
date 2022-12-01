package store.lunangangster.nodeflow.context.spring;

import store.lunangangster.nodeflow.builder.NodeBuilder;
import store.lunangangster.nodeflow.builder.entity.NodeProp;
import store.lunangangster.nodeflow.context.NodesInitializer;
import java.util.Map;
import java.util.Map.Entry;

/**
 * 基于Spring的NodesInitializer的实现。{@link #initialize()}方法会将加入到IOC容器中的NodeComponent对象
 * 进行解析和处理，从而生成相应的Node对象加载到NodeFlow中去
 */
public class SpringNodesInitializer implements NodesInitializer {

	@Override
	public void initialize() {
		Map<String, NodeProp> nodeCompMap = SpringNodeRegister.getNodeCompMap();
		for (Entry<String, NodeProp> entry : nodeCompMap.entrySet()) {
			NodeProp nodeProp = entry.getValue();
			NodeBuilder.start()
					.id(nodeProp.getId())
					.type(nodeProp.getType())
					.className(nodeProp.getClassName())
					.clazz(nodeProp.getClazz())
					.component(nodeProp.getNodeComponent())
					.build();
		}
	}

	@Override
	public int priority() {
		return 0;
	}
}
