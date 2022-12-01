package store.lunangangster.nodeflow.parser.helper;

import static store.lunangangster.nodeflow.enums.FlowType.FLOW_THEN;

import store.lunangangster.nodeflow.builder.NodeBuilder;
import store.lunangangster.nodeflow.builder.entity.ChainProp;
import store.lunangangster.nodeflow.builder.entity.NodeProp;
import store.lunangangster.nodeflow.enums.NodeType;
import store.lunangangster.nodeflow.exception.ChainsLabelNotFoundException;
import store.lunangangster.nodeflow.exception.NodeClassNotFoundException;
import store.lunangangster.nodeflow.exception.rt.UnInitializedFlowException;
import store.lunangangster.nodeflow.rt.element.Executable;
import store.lunangangster.nodeflow.rt.element.flow.Flow;
import java.util.List;
import java.util.function.Function;
import org.dom4j.Document;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 聚合XML、JSON、YML文件的解析器工具类
 *
 * @author cwk
 * @version 1.0
 */
public class ParserHelper {
	private static final Logger log = LoggerFactory.getLogger(ParserHelper.class);

	private ParserHelper() {}


	/**
	 * 根据NodeProp来创建Node对象，并且加载到FlowBus中
	 */
	public static void buildNode(NodeProp nodeProp) throws NodeClassNotFoundException {
		if (nodeProp == null) {
			return;
		}
		String nodeId = nodeProp.getId();
		String className = nodeProp.getClassName();
		Class<?> clazz;
		NodeType nodeType;
		try {
			// 获取原class
			clazz = Class.forName(className);
			//
			nodeType = NodeType.guessType(clazz);
			if (nodeType == null) {
				throw new NodeClassNotFoundException();
			}
		} catch (ClassNotFoundException e) {
			throw new NodeClassNotFoundException(e);
		}
		// 构建Node
		NodeBuilder.start()
				.id(nodeId)
				.className(className)
				.clazz(clazz)
				.type(nodeType)
				.build();
	}

	/**
	 * 相邻Flow的合并逻辑
	 */
	public static void mergeFlow(Flow flow, List<Executable> executableList) {
		// TODO 这里仅仅考虑了ThenFlow的合并逻辑，其余后续优化
		if (FLOW_THEN.equals(flow.getFlowType())) {
			if (!flow.addExecutable(executableList)) {
				throw new UnInitializedFlowException("flow is not initialized");
			}
		} else {
			return;
		}
	}

	// XML解析Nodes方法
	public static void parseNodes(List<Document> documents) throws NodeClassNotFoundException {
		XmlParserHelper.parseNodes(documents);
	}

	// XML解析Chains方法
	public static void parseChains(List<Document> documents, Function<Element, ChainProp> chainParser)
			throws ChainsLabelNotFoundException {
		XmlParserHelper.parseChains(documents, chainParser);
	}
}
