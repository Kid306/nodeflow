package com.kid.nodeflow.expression;

import cn.hutool.core.collection.ListUtil;
import cn.hutool.core.util.StrUtil;
import com.kid.nodeflow.builder.FlowBuilder;
import com.kid.nodeflow.enums.FlowType;
import com.kid.nodeflow.enums.NodeType;
import com.kid.nodeflow.exception.ExecutableItemNotFoundException;
import com.kid.nodeflow.exception.rt.IllegalNodeExpressionException;
import com.kid.nodeflow.rt.FlowBus;
import com.kid.nodeflow.rt.element.Executable;
import com.kid.nodeflow.rt.element.flow.IfFlow;
import java.util.ArrayList;
import java.util.List;

/**
 * 针对Flow中的value的分割解析器
 * @author cwk
 * @version 1.0
 */
public class ExpressionParserHelper {


	/**
	 * 解析节点标签中的value字段，匹配FlowBus中相应的Node。
	 * <p>
	 *   该方法回按照','对value进行分割，可以自动去除空格。 其实这里等价于{@link com.kid.nodeflow.enums.NodeType#NODE_COMMON}的pattern属性，
	 * 	 只不过是一个事物的两种描述：
	 * 	 <ol>
	 * 	   <li>这里更偏向于解决实际问题，快速地解析出用','分割的部分</li>
	 * 	   <li>而NODE_COMMON侧重于普通Node表达式的描述，即一个抽象的Node应该符合怎样的表达式规则</li>
	 * 	 </ol>
	 * </p>
	 */


	public static List<Executable> resolveValue(String value) {
		if (StrUtil.isBlank(value)) {
			return null;
		}
		// 按照','进行分割，会自动去除空白
		// 这里的trim()是必须的，因为如果value的开头有空白，expressions中就会产生一个空白
		String[] expressions = value.trim().split("\\s*,\\s*");
		// // 首先先判断一遍，避免中途发现有不合法的表达式但是已经调用resolve方法带来不必要的性能消耗
		// for (String expression : expressions) {
		// 	// 判断其是否是一个正确的Node表达式
		// 	if (isNodeExpression(expression, NodeType.NODE_COMMON)) {
		// 		throw new IllegalNodeExpressionException("Illegal node expression as %s: \"%s\"",
		// 				NodeType.NODE_COMMON.name(), expression);
		// 	}
		// }
		List<Executable> executableList = new ArrayList<>();
		for (String expression : expressions) {
			// 解析出Node or Flow or Chain
			Executable executable = ExpressionNode.resolve(expression);
			executableList.add(executable);
		}
		return executableList;
	}

	/**
	 * 判断expression是否是nodeType类型的表达式
	 */
	public static boolean isNodeExpression(String expression, NodeType nodeType) {
		return nodeType.pattern.matcher(expression).matches();
	}

	/**
	 * 解析NODE_COMMON类型的expression，没有进行expression检查，默认：true == isNodeExpression(expression, NODE_COMMON)
	 */
	public static Executable resolveCommonExpression(String expression)
			throws ExecutableItemNotFoundException {
		Executable executable = FlowBus.getExecutable(expression);
		if (executable == null) {
			throw new ExecutableItemNotFoundException("no available CommonNode or Chain find: %s", expression);
		}
		return executable;
	}

	/**
	 * 解析NODE_IF类型的expression，没有进行expression检查，默认：true == isNodeExpression(expression, NODE_IF)
	 */
	public static Executable resolveIfExpression(String expression)
			throws ExecutableItemNotFoundException {
		String[] split = expression.split("[\\s*()|]+");
		if (split.length <= 1) {
			throw new IllegalNodeExpressionException("Illegal node expression as %s: \"%s\"",
					NodeType.NODE_IF.name(), expression);
		}
		// 获取条件节点，条件节点要单独构造Flow
		Executable ifNode = FlowBus.getExecutable(split[0]);
		// true分支
		Executable trueBranch = FlowBus.getExecutable(split[1]);
		// false分支
		Executable falseBranch = split.length < 3 ? null : FlowBus.getExecutable(split[2]);
		if (ifNode == null || trueBranch == null) {
			throw new ExecutableItemNotFoundException("no available IfNode find: %s", expression);
		}
		IfFlow ifFlow = FlowBuilder.buildFlow(FlowType.FLOW_IF, new ArrayList<>());
		ifFlow.setExecutableList(ListUtil.toList(ifNode));
		ifFlow.setBranch(trueBranch, falseBranch);
		return ifFlow;
	}
}
