package cn.how2j.test;

import org.junit.Test;

import cn.hutool.core.io.resource.ClassPathResource;

public class TestFile {

	@Test
	public void test1() {
		ClassPathResource resource = new ClassPathResource("META-INF/MANIFEST.MF");
		System.out.println(resource.readUtf8Str());
	}
}
