package com.ririiy.redisanno.core;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.concurrent.TimeUnit;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface RedisCache {
	
	/** 缓存key */
	String key();
	
	/** 类型 */
	Class<?> type() default Object.class;
	
	/** 过期时间 */
	int expireTime();
	
	/** 时间单位 */
	TimeUnit timeUnit() default TimeUnit.SECONDS;
}
