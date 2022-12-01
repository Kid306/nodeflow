package store.lunangangster.nodeflow.test.context.spring;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import org.junit.Test;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

public class ResourcePatternResolverTest {
	@Test
	public void test01() throws IOException {
		PathMatchingResourcePatternResolver p = new PathMatchingResourcePatternResolver();
		Resource[] resources = p.getResources("classpath:/core/**/test.xml");
		for (Resource resource : resources) {
			InputStream is = resource.getInputStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(is));
			System.out.println(reader.readLine());
			is.close();
		}
	}

	@Test
	public void test02() throws IOException {
		PathMatchingResourcePatternResolver p = new PathMatchingResourcePatternResolver();
		Resource resource = p.getResource("classpath:/core/test.xml");
		InputStream is = resource.getInputStream();
		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		System.out.println(reader.readLine());
		is.close();
	}

	@Test
	public void test03() throws IOException {
		FileInputStream is = new FileInputStream("core/test.xml");
		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		System.out.println(reader.readLine());
		is.close();
	}
}
