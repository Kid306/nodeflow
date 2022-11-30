package com.kid.nodeflow.context.spring;

import cn.hutool.core.util.StrUtil;
import com.kid.nodeflow.context.PathResolver;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

/**
 * Spring环境下的类路径解析器，支持所有工程类路径下的文件的解析，支持通配符匹配。使用时需要加上classpath:/classpath*:前缀
 *
 * @author cwk
 * @version 1.0
 */
public class SpringPathResolver implements PathResolver {
	private static final String PATH_PREFIX = "classpath:";

	private static final String PATHS_PREFIX = "classpath*:";

	private static final ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();

	@Override
	public InputStream getResource(String path) throws IOException {
		if (StrUtil.isNotBlank(path)) {
			if (path.startsWith(PATH_PREFIX) || path.startsWith(PATHS_PREFIX)) {
				return resolver.getResource(path).getInputStream();
			}
		}
		return null;
	}

	@Override
	public List<InputStream> getResources(String path) throws IOException {
		List<InputStream> res = null;
		if (StrUtil.isNotBlank(path)) {
			if (path.startsWith(PATH_PREFIX) || path.startsWith(PATHS_PREFIX)) {
				res = new ArrayList<>();
				for (Resource resource : resolver.getResources(path)) {
					if (!resource.exists()) {
						res.add(null);
					} else {
						res.add(resource.getInputStream());
					}
				}
			}
		}
		return res;
	}

	@Override
	public int priority() {
		return 0;
	}
}
