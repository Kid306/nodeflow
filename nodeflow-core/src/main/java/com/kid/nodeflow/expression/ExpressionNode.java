package com.kid.nodeflow.expression;

import com.kid.nodeflow.enums.NodeType;
import com.kid.nodeflow.exception.ExecutableItemNotFoundException;
import com.kid.nodeflow.exception.rt.IllegalNodeExpressionException;
import com.kid.nodeflow.rt.element.Executable;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * <p>
 * 针对Flow中的value的解析结果节点，例如：每个按','分割的都会作为一个<code>ExpressionNode</code>； 又如对于包含了条件表达式的表达式："a, b(c|d),
 * e"，b(c|d)也将被解析为一个ExpressionNode。
 * </p>
 * <p>
 * 其实对于一个复杂的表达式，任何一个嵌套结构都可以作为一个ExpressionNode，例如： "a(b|c(d|e))"，其中假设X =
 * c(d|e)，那么原表达式为：a(b|X)，是条件表达式的最初模样；此时X也将会被解析为一个 ExpressionNode。
 * </p>
 * @author cwk
 * @version 1.0
 */
public class ExpressionNode {

	// 用于解析id[tag]
	private static final Pattern TAG_REGEX = Pattern.compile("\\[\\w+]");
	// 用于解析id[tag]
	private static final Pattern PATTERN = Pattern.compile("[^\\[\\]]+");
	// 该id一定是NodeId
	private String id;

	private String tag;

	// 可能包含一系列Node或Chain子流程
	private List<ExpressionNode> subNode;

	ExpressionNode() {
	}

	ExpressionNode(String id) {
		this.id = id;
	}

	/**
	 * 解析当前的expression，生成一个可执行的Node
	 */
	public static Executable resolve(String expression) {
		try {
			if (ExpressionParserHelper.isNodeExpression(expression, NodeType.NODE_COMMON)) {
				return ExpressionParserHelper.resolveCommonExpression(expression);
			} else if (ExpressionParserHelper.isNodeExpression(expression, NodeType.NODE_IF)) {
				return ExpressionParserHelper.resolveIfExpression(expression);
			}
			throw new IllegalNodeExpressionException("Expression resolve fail: find no available node type for %s", expression);
		} catch (ExecutableItemNotFoundException e) {
			throw new IllegalNodeExpressionException("Illegal node expression: %s; %s", expression, e.getMessage());
		}
	}


	public String getId() {
		return id;
	}

	public String getTag() {
		return tag;
	}

	public ExpressionNode setTag(String tag) {
		this.tag = tag;
		return this;
	}

	public ExpressionNode setId(String id) {
		this.id = id;
		return this;
	}

	public void setSubNode(List<ExpressionNode> subNode) {
		this.subNode = subNode;
	}

	public List<ExpressionNode> getSubNode() {
		return subNode;
	}

	public void addSubNode(ExpressionNode node) {
		// 这里不会有线程安全问题
		if (subNode == null) {
			subNode = new ArrayList<>();
		}
		subNode.add(node);
	}
}
