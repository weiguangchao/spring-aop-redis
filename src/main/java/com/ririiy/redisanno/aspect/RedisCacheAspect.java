package com.ririiy.redisanno.aspect;

import java.lang.reflect.Method;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.data.redis.core.ValueOperations;

import com.alibaba.fastjson.JSON;
import com.ririiy.redisanno.core.RedisCache;

@Aspect
public class RedisCacheAspect {
	
	@Resource(name = "redisTemplate")
	private ValueOperations<String, String> valueOperations;
	
	@Pointcut("@annotation(com.ririiy.redisanno.core.RedisCache)")
	private void redisCachePoint() {}

	@Pointcut("@annotation(com.ririiy.redisanno.core.RedisCachePut)")
	private void redisCachePutPoint() {}
	
	@Around("redisCachePoint()")
	private Object cache(ProceedingJoinPoint pjp) throws Throwable {
		
		// 目标对象
		Class<?> targetClass = pjp.getTarget().getClass();
		
		// 目标方法
		Method targetMethod = getMethod(pjp);
		
		// 注解信息
		RedisCache redisCache = targetMethod.getAnnotation(RedisCache.class);
		
		// redis中key
		String redisKey = targetClass.getName() + "#" + redisCache.key();
		
		// redis中value
		String redisValue = valueOperations.get(redisKey);
		
		// redis命中
		if (redisValue != null && !redisValue.isEmpty()) {
			return getData(redisValue, redisCache.type(), targetMethod.getReturnType());
		} else {
			Object result = pjp.proceed();
			redisValue = JSON.toJSONString(result);
			// 设置redis缓存
			valueOperations.set(redisKey, redisValue, getExpireTime2Second(redisCache));
			return result;
		}
	}

	private int getExpireTime2Second(RedisCache redisCache) {
		int expireTime = redisCache.expireTime();
		TimeUnit timeUnit = redisCache.timeUnit();
		// 默认是一分钟
		if (expireTime < 60) {
			return 60;
		}
		switch (timeUnit) {
			case MINUTES:
				return expireTime * 60;
			case HOURS:
				return expireTime * 60 * 60;
			case DAYS:
				return expireTime * 60 * 60 * 60;
			default:
				return 60;
		}
	}
	
	private Object getData(String value, Class<?> redisCacheType, Class<?> methodReturnType) {
		if (redisCacheType.equals(Object.class) && !methodReturnType.equals(List.class)) {
			return JSON.parseObject(value, methodReturnType);
		}
		if (!redisCacheType.equals(Object.class) && !methodReturnType.equals(List.class)) {
			return JSON.parseObject(value, methodReturnType);
		}
		if (!redisCacheType.equals(Object.class) && methodReturnType.equals(List.class)) {
			return JSON.parseArray(value, methodReturnType);
		}
		return value;
	}

	private Method getMethod(ProceedingJoinPoint pjp) {
		Method targetMethod = null;
		
		Object[] args = pjp.getArgs();
		// 目标方法的参数
		Class<?>[] argTypes = new Class[args.length];
		for (int i = 0, l = args.length; i < l; i++) {
			argTypes[i] = args[i].getClass();
		}
		
		// 目标方法名
		String methodName = pjp.getSignature().getName();
		
		// target对象的所有方法
		// 依次同方法名和方法参数进行判断
		Method[] methods = pjp.getTarget().getClass().getMethods();
		for(Method method : methods) {
			// 方法名相同
			if (methodName.equals(method.getName())) {
				Class<?>[] parameterTypes = method.getParameterTypes();
				boolean isSameMethod = true;
				// 方法参数个数相同
				if (parameterTypes.length == argTypes.length) {
					// 判断方法参数类型
					for (int i = 0, l = args.length; i < l; i++) {
						if (!parameterTypes[i].isAssignableFrom(argTypes[i])) {
							isSameMethod = false;
						}
					}
				}
				if (isSameMethod) {
					targetMethod = method;
					break;
				}
			} else {
				break;
			}
		}
		
		return targetMethod;
	}
	
}
