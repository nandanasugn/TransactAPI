package com.nandana.transactapi.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Slf4j
@Component
public class PerformanceAspect {
    @Pointcut("execution(* com.nandana.transactapi.service.*.*(..))")
    public void serviceLayer() {
    }

    @Around("serviceLayer()")
    public Object monitorPerformance(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();
        long endTime = System.currentTimeMillis();

        Object result = joinPoint.proceed();

        log.info("PERFORMANCE: {} executed in {} ms", joinPoint.getSignature().getName(), (endTime - startTime));
        return result;
    }
}
