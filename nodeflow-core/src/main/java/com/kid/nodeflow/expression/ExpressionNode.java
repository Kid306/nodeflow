package com.kid.nodeflow.expression;

import cn.hutool.core.util.StrUtil;
import com.kid.nodeflow.exception.IllegalValueExpressionException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 针对Flow中的value的解析结果节点，每个按','分割的都会作为一个<code>ExpressionNode</code>
 *
 * @author cwk
 * @version 1.0
 */
public class ExpressionNode {

	// 用于解析id[tag]
	private static final Pattern pattern = Pattern.compile("[^\\[\\]]+");

	private String id;

	private String tag;


	private ExpressionNode() {}

	/**
	 * 解析当前的expression，生成一个可执行的Node
	 */
	public static ExpressionNode resolve(String expression) {
		expression = expression.trim();
		if (StrUtil.isBlank(expression)) {
			throw new IllegalValueExpressionException("Illegal value expression: %s; Pattern should be not blank", expression);
		}
		if (expression.indexOf(0) == '[' || expression.indexOf(0) == ']') {
			throw new IllegalValueExpressionException("Illegal value expression: %s; id should be not be blank", expression);
		}
		int bucketStart = expression.indexOf('[');
		int bucketEnd = expression.indexOf(']');
		if (bucketStart >= 0 || bucketEnd >= 0) {
			if (bucketStart < 0 || bucketEnd < 0 || bucketEnd <= bucketStart) {
				throw new IllegalValueExpressionException("Illegal value expression: %s; Patterns should be followed id[tag]", expression);
			}
		}
		String id = null;
		String tag = null;
		Matcher matcher = pattern.matcher(expression);
		if (matcher.find()) {
			id = matcher.group().trim();
		}
		if (matcher.find()) {
			tag = matcher.group().trim();
		}
		return new ExpressionNode().setId(id).setTag(tag);
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
}
