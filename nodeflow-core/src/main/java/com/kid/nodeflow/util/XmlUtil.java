package com.kid.nodeflow.util;

import cn.hutool.core.util.StrUtil;
import java.util.regex.Pattern;

/**
 * 关于Xml文件的工具类
 *
 * @author cwk
 * @version 1.0
 */
public class XmlUtil {
	public static final Pattern COMPACT_PATTERN = Pattern.compile("\\s*(?=>)");


	/**
	 * 用于压缩Xml格式的文件内容。该方法不保证content作为Xml格式的正确性，所以调用之前
	 * 务必保证content是符合Xml规范的
	 */
	public String compact(String content) {
		if (StrUtil.isBlank(content)) {
			return "";
		}
		return COMPACT_PATTERN.matcher(content.trim()).replaceAll("");
	}
}
