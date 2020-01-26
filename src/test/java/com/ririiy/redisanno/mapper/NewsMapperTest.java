package com.ririiy.redisanno.mapper;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.ririiy.redisanno.entity.News;

@SpringBootTest
public class NewsMapperTest {

	@Autowired
	private NewsMapper newsMapper;
	
	@Test
	public void test() {
		List<News> newsList = newsMapper.selectList(null);
		newsList.forEach(System.out::println);
	}
	
}
