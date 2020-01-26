package com.ririiy.redisanno.cache;

import javax.annotation.Resource;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.ValueOperations;

@SpringBootTest
public class NewsCacheTest {

	@Resource(name = "redisTemplate")
	private ValueOperations<String, String> valueOperations;

	@Test
	public void testSet() {
		valueOperations.set("news_01", "123");
	}
	
	@Test
	public void testGet() {
		String str = valueOperations.get("news_01");
		System.out.println(str);
	}

}
