package com.ririiy.redisanno.service;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.ririiy.redisanno.entity.News;

@SpringBootTest
public class NewsServiceTest {

	@Autowired
	private NewsService newsService;
	
	@Test
	public void testFindAll() {
		List<News> newsList = newsService.findAll();
		newsList.forEach(System.out::println);
	}
	
	@Test
	public void testFindById() {
		News news = newsService.findById(23L);
		System.out.println(news);
	}

}
