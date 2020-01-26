package com.ririiy.redisanno.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ririiy.redisanno.core.RedisCache;
import com.ririiy.redisanno.entity.News;
import com.ririiy.redisanno.mapper.NewsMapper;
import com.ririiy.redisanno.service.NewsService;

@Service
public class NewsServiceImpl implements NewsService {
	
	@Autowired
	private NewsMapper newsMapper;

	@RedisCache(key = "news", type=News.class, expireTime = 60)
	@Override
	public List<News> findAll() {
		return newsMapper.selectList(null);
	}

	@RedisCache(key = "news_id", expireTime = 60)
	@Override
	public News findById(Long id) {
		return newsMapper.selectById(id);
	}

}
