package com.kid.nodeflow.paser.base;

import static com.kid.nodeflow.common.BaseConstant.CHAIN;
import static com.kid.nodeflow.common.BaseConstant.ID;
import static com.kid.nodeflow.common.BaseConstant.VALUE;

import cn.hutool.core.collection.CollUtil;
import com.kid.nodeflow.builder.ChainBuilder;
import com.kid.nodeflow.builder.FlowBuilder;
import com.kid.nodeflow.builder.entity.ChainProp;
import com.kid.nodeflow.context.element.Executable;
import com.kid.nodeflow.enums.FlowType;
import com.kid.nodeflow.exception.rt.IllegalElementTypeException;
import com.kid.nodeflow.exception.rt.IllegalFlowTypeException;
import com.kid.nodeflow.expression.ExpressionParserHelper;
import com.kid.nodeflow.paser.helper.ParserHelper;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * XML解析器的基础实现类
 * @author cwk
 * @version 1.0
 */
public class BaseXmlFlowParser extends XmlFlowParser {
	private static final Logger log = LoggerFactory.getLogger(BaseXmlFlowParser.class);

	/**
	 * 解析单个以XML表示的chain
	 * @param chainElement 单个XML表示的chain
	 */
	@Override
	public void parseChain(Element chainElement) {
		if (!CHAIN.equals(chainElement.getName())) {
			throw new IllegalElementTypeException("illegal element type: [%s] wanna parse to [%s]",
					chainElement.getName(), CHAIN);
		}
		// TODO 这里需要考虑chainId is blank的情况
		String chainId = chainElement.attribute(ID).getText();
		// 使用中间对象而不是创建一个Chain，防止解析中出现异常而导致需要从FlowBus中将Chain移除
		ChainProp chain = new ChainProp(chainId, new ArrayList<>());
		// 1. 解析<chain/>
		Iterator<Element> iter = chainElement.elementIterator();
		while (iter.hasNext()) {
			// 每一个子标签都对应一个Flow
			Element element = iter.next();
			String flowName = element.getName();
			String flowValue = element.attribute(VALUE).getText();
			FlowType flowType;
			try {
				flowType = FlowType.valueOf(flowName);
			} catch (IllegalArgumentException e) {
				// 出现错误的flow的chain将不再解析，在解析阶段直接报错
				log.error("Undefined flow type: {}", flowName);
				throw new IllegalFlowTypeException("Undefined flow type: [%s]", flowName);
			}
			// TODO 解析Value
			List<Executable> executableList = ExpressionParserHelper.resolveValue(flowValue);
			// 考虑相邻Flow的逻辑合并
			if (CollUtil.getLast(chain.getFlowList()).getFlowType().equals(flowType)) {
				ParserHelper.mergeFlow(CollUtil.getLast(chain.getFlowList()), executableList);
			} else {
				// 否则添加Flow
				chain.addFlow(FlowBuilder.buildFlow(flowType, executableList));
			}
		}
		// 向FlowBus中添加Chain
		ChainBuilder.start().id(chain.getId()).flowList(chain.getFlowList()).build();
	}
}
