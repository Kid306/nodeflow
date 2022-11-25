package com.kid.nodeflow.parser;

import static com.kid.nodeflow.parser.FlowParserProvider.RuleSourceConstant.BASE_XML_REGEX;

import cn.hutool.core.util.StrUtil;
import com.kid.nodeflow.exception.ParserNotFoundException;
import com.kid.nodeflow.parser.base.FlowParser;
import com.kid.nodeflow.parser.factory.BaseParserFactory;
import com.kid.nodeflow.parser.factory.ParserFactory;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * FlowParser的提供者，可以根据配置文件的类型找到其对应的解析器
 * @author cwk
 * @version 1.0
 */
public class FlowParserProvider {

	private static final ParserFactory BASE_PARSER_FACTORY = new BaseParserFactory();

	private static final Map<Predicate<String>, Supplier<FlowParser>> PARSER_CREATOR = new HashMap<Predicate<String>, Supplier<FlowParser>>() {
		{ put(path -> path.matches(BASE_XML_REGEX), BASE_PARSER_FACTORY::getXmlFlowParser);}
	};

	// 该类是用于复用FlowParser的工具类集合，不保证线程安全
	private static final Set<FlowParser> PARSER_SET = new HashSet<>();

	static class RuleSourceConstant {
		public static final String BASE_XML_REGEX = "^[a-zA-Z0-9:_\\\\]+\\.xml$";
		public static final String EL_XML_REGEX = "^[a-zA-Z0-9:_\\\\]+\\.el\\.xml$";
	}

	/**
	 * 根据RuleSource的文件名获取FlowParser
	 */
	public static FlowParser findParser(String fileName) {
		if (StrUtil.isEmpty(fileName)) {
			throw new ParserNotFoundException("No matching parser for file: %s", fileName);
		}
		return PARSER_CREATOR.get(
				PARSER_CREATOR.keySet().stream().filter(pathPredicate -> pathPredicate.test(fileName))
						.findFirst().orElseThrow(
								() -> new ParserNotFoundException("No matching parser for file: %s", fileName))
		).get();
	}
}
