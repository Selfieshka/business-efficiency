package com.technokratos.kirillakhmetov.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Aspect
@Component
public class CacheAspect {

    private final Map<String, Object> cache = new HashMap<>();

    @Around("execution(* com.technokratos.kirillakhmetov.service.FinanceService.calculateProfit(..)) && args(key)")
    public Object cacheData(ProceedingJoinPoint pjp, String key) throws Throwable {
        if (cache.containsKey(key)) {
            return cache.get(key);
        }
        Object result = pjp.proceed();
        cache.put(key, result);
        return result;
    }
}
