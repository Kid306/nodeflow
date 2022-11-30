package com.kid.nodeflow.context;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * 用于规则文件解析的路径解析器
 *
 * @author cwk
 * @version 1.0
 */
public interface PathResolver extends Order {

	public InputStream getResource(String path) throws IOException;

	public List<InputStream> getResources(String path) throws IOException;
}
