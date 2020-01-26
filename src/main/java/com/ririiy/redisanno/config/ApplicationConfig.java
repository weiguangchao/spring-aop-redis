package com.ririiy.redisanno.config;

import java.net.UnknownHostException;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import com.alibaba.fastjson.support.spring.GenericFastJsonRedisSerializer;
import com.ririiy.redisanno.core.EnableRedisCache;

@Configuration
@EnableRedisCache
public class ApplicationConfig {
	
	@Bean
	public RedisTemplate<Object, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory)
			throws UnknownHostException {
		RedisTemplate<Object, Object> redisTemplate = new RedisTemplate<>();
		redisTemplate.setConnectionFactory(redisConnectionFactory);
		GenericFastJsonRedisSerializer fastJsonRedisSerializer = new GenericFastJsonRedisSerializer();
		redisTemplate.setKeySerializer(new StringRedisSerializer());//单独设置keySerializer
	    redisTemplate.setValueSerializer(fastJsonRedisSerializer);//单独设置valueSerializer
		return redisTemplate;
	}
	
}
