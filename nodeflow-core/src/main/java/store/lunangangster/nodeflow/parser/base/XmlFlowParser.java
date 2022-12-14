package store.lunangangster.nodeflow.parser.base;

import cn.hutool.core.collection.CollUtil;
import store.lunangangster.nodeflow.builder.entity.ChainProp;
import store.lunangangster.nodeflow.parser.helper.ParserHelper;
import store.lunangangster.nodeflow.exception.NodeClassNotFoundException;
import store.lunangangster.nodeflow.exception.RuleSourceParseException;
import store.lunangangster.nodeflow.exception.ChainsLabelNotFoundException;
import java.util.ArrayList;
import java.util.List;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

/**
 * 解析Xml规则文件的抽象解析器。所有解析Xml规则文件的解析器都需要继承这个类，
 * 并且实现其parseChain方法
 *
 * @author cwk
 * @version 1.0
 */
public abstract class XmlFlowParser implements FlowParser {

	/**
	 * 解析文本文件
	 */
	@Override
	public void parse(String content) throws RuleSourceParseException {
		this.parse(CollUtil.newArrayList(content));
	}

	/**
	 * 解析Xml文本文件
	 */
	@Override
	public void parse(List<String> contents) throws RuleSourceParseException {
		if (CollUtil.isEmpty(contents)) {
			return;
		}
		List<Document> documents = new ArrayList<>(contents.size());
		try {
			for (String content : contents) {
				documents.add(DocumentHelper.parseText(content));
			}
			ParserHelper.parseNodes(documents);
			ParserHelper.parseChains(documents, this::parseChain);
		} catch (DocumentException | ChainsLabelNotFoundException | NodeClassNotFoundException e) {
			throw new RuleSourceParseException("Rule Source File of Xml parse error: %s", e.getMessage());
		}
	}

	/**
	 * <p>解析单个以XML表示的chain</p>
	 * <p>交给子类实现</p>
	 */
	public abstract ChainProp parseChain(Element chainElement);
}
