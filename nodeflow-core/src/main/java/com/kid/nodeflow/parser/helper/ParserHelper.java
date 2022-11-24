package com.kid.nodeflow.parser.helper;

import static com.kid.nodeflow.common.BaseConstant.COMP_PACKAGE;
import static com.kid.nodeflow.enums.FlowType.FLOW_THEN;

import cn.hutool.core.util.StrUtil;
import com.kid.nodeflow.builder.NodeBuilder;
import com.kid.nodeflow.builder.entity.NodeProp;
import com.kid.nodeflow.rt.element.Executable;
import com.kid.nodeflow.rt.element.flow.Flow;
import com.kid.nodeflow.enums.NodeType;
import com.kid.nodeflow.exception.UnInitializedFlowException;
import com.kid.nodeflow.exception.NodeClassNotFoundException;
import java.util.List;
import java.util.function.Consumer;
import org.dom4j.Document;
import org.dom4j.Element;

/**
 * 聚合XML、JSON、YML文件的解析器工具类
 *
 * @author cwk
 * @version 1.0
 */
public class ParserHelper {
	private ParserHelper() {}

	/**
	 * 根据Class推测Node类型
	 */
	public static NodeType guessType(Class<?> clazz) {
		if (clazz == null) {
			return null;
		}
		while (true) {
			Class<?> superclass = clazz.getSuperclass();
			if (Object.class.equals(superclass)) {
				return null;
			}
			String packageName = superclass.getPackage().getName();
			// 如果父类是component包下的组件，则返回组件类型
			if (packageName.startsWith(COMP_PACKAGE)) {
				return NodeType.getNodeType(superclass);
			}
		}
	}

	/**
	 * 根据NodeProp来创建Node对象
	 */
	public static void buildNode(NodeProp nodeProp) {
		if (nodeProp == null) {
			return;
		}
		String nodeId = nodeProp.getId();
		String className = nodeProp.getClassName();
		// 推测Node的类型
		if (StrUtil.isBlank(className)) {
			throw new NodeClassNotFoundException("node class not found:[{%s}]", className);
		}
		try {
			Class<?> clazz = Class.forName(className);
			NodeType nodeType = guessType(clazz);
			// 构建Node
			NodeBuilder.start()
					.id(nodeId)
					.clazz(clazz)
					.type(nodeType)
					.build();
		} catch (ClassNotFoundException e) {
			throw new NodeClassNotFoundException("node class not found:[{%s}]", className);
		}
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
	public static void parseNodes(List<Document> documents) {
		XmlParserHelper.parseNodes(documents);
	}

	// XML解析Chains方法
	public static void parseChains(List<Document> documents, Consumer<Element> chainParser) {
		XmlParserHelper.parseChains(documents, chainParser);
	}
}
