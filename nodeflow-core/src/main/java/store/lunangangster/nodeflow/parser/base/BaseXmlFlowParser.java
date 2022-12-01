package store.lunangangster.nodeflow.parser.base;

import cn.hutool.core.collection.CollUtil;
import store.lunangangster.nodeflow.builder.FlowBuilder;
import store.lunangangster.nodeflow.builder.entity.ChainProp;
import store.lunangangster.nodeflow.common.BaseConstant;
import store.lunangangster.nodeflow.parser.helper.ParserHelper;
import store.lunangangster.nodeflow.enums.FlowType;
import store.lunangangster.nodeflow.exception.rt.IllegalFlowTypeException;
import store.lunangangster.nodeflow.expression.ExpressionParserHelper;
import store.lunangangster.nodeflow.rt.element.Executable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.dom4j.Element;

/**
 * XML解析器的基础实现类
 * @author cwk
 * @version 1.0
 */
public class BaseXmlFlowParser extends XmlFlowParser {

	/**
	 * 解析单个以XML表示的chain
	 * @param chainElement 单个XML表示的chain
	 * @return 解析出的ChainProp中间对象
	 */
	@Override
	public ChainProp parseChain(Element chainElement) {
		// 这里不需要考虑chainId is blank的情况
		String chainId = chainElement.attribute(BaseConstant.ID).getText();
		// 使用中间对象而不是创建一个Chain，防止解析中出现异常而导致需要从FlowBus中将Chain移除
		ChainProp chain = new ChainProp(chainId, new ArrayList<>());
		// 1. 解析<chain/>
		Iterator<Element> flowIter = chainElement.elementIterator();
		while (flowIter.hasNext()) {
			// 此时每一个子标签都对应一个Flow
			Element element = flowIter.next();
			String flowName = element.getName();
			String flowValue = element.attribute(BaseConstant.VALUE).getText();
			FlowType flowType = FlowType.getFlowType(flowName);
			// 在BaseXmlFlowParser中，chain标签中只允许出现then标签和when标签
			if (!FlowType.FLOW_THEN.equals(flowType) && !FlowType.FLOW_WHEN.equals(flowType)) {
				throw new IllegalFlowTypeException("Undefined flow type in BaseXmlExpression: %s", flowName);
			}
			// 解析value
			List<Executable> executableList = ExpressionParserHelper.resolveValue(flowValue);
			// 考虑相邻Flow的逻辑合并
			if (CollUtil.isNotEmpty(chain.getFlowList()) && CollUtil.getLast(chain.getFlowList()).getFlowType().equals(flowType)) {
				ParserHelper.mergeFlow(CollUtil.getLast(chain.getFlowList()), executableList);
			} else {
				// 否则添加Flow
				chain.addFlow(FlowBuilder.buildFlow(flowType, executableList));
			}
		}
		return chain;
	}
}
