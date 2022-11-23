package com.kid.nodeflow.expression;

import cn.hutool.core.util.StrUtil;
import com.kid.nodeflow.rt.FlowBus;
import com.kid.nodeflow.rt.element.Executable;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 针对Flow中的value的分割解析器
 *
 * @author cwk
 * @version 1.0
 */
public class ExpressionParserHelper {

	private static final Pattern pattern = Pattern.compile(",");

	/**
	 *  解析节点标签中的value字段，匹配FlowBus中相应的Node
	 */
	public static List<Executable> resolveValue(String value) {
		if (StrUtil.isBlank(value)) {
			return null;
		}
		List<Executable> executableList = new ArrayList<>();
		value = value.trim();
		Matcher matcher = pattern.matcher(value);
		while (matcher.find()) {
			String expression = matcher.group().trim();
			// TODO 可能解析出的并不一定是Node对象
			ExpressionNode en = ExpressionNode.resolve(expression);
			executableList.add(FlowBus.getNode(en.getId()));
		}
		return executableList;
	}
}
