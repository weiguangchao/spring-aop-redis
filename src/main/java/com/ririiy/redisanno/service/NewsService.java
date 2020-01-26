package com.ririiy.redisanno.service;

import java.util.List;

import com.ririiy.redisanno.entity.News;

public interface NewsService {
	
	List<News> findAll();
	
	News findById(Long id);
	
}
