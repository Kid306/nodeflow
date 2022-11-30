package com.kid.nodeflow.context.lcoal;

import cn.hutool.core.collection.ListUtil;
import com.kid.nodeflow.context.PathResolver;
import java.io.InputStream;
import java.util.List;

/**
 * PathResolver的本地实现，只支持当前工程类路径下的文件的解析，不支持通配符匹配
 *
 * @author cwk
 * @version 1.0
 */
public class LocalPathResolver extends Local implements PathResolver {

	@Override
	public InputStream getResource(String path) {
		return getClass().getResourceAsStream(path);
	}

	@Override
	public List<InputStream> getResources(String path) {
		return ListUtil.toList(getResource(path));
	}
}
