package com.nandana.transactapi.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Slf4j
@Component
public class LoggingAspect {
    @Pointcut("execution(* com.nandana.transactapi.service.*.*(..))")
    public void serviceMethod() {
    }

    @Pointcut("execution(* com.nandana.transactapi.controller.*.*(..))")
    public void controllerMethod() {
    }

    @Around("serviceMethod() || controllerMethod()")
    public Object logExecution(ProceedingJoinPoint joinPoint) throws Throwable {
        String methodName = joinPoint.getSignature().getName();

        log.info("EXECUTING METHOD: {}", methodName);
        log.info("ARGUMENTS: {}", Arrays.toString(joinPoint.getArgs()));

        long startTime = System.currentTimeMillis();

        Object result;
        try {
            result = joinPoint.proceed();
        } catch (Exception e) {
            log.error("EXCEPTION IN EXECUTING METHOD: {} - {}", methodName, e.getMessage());
            throw e;
        }

        long endTime = System.currentTimeMillis();

        log.info("METHOD EXECUTED: {}", methodName);
        log.info("EXECUTION TIME: {}", endTime - startTime);
        log.info("RETURN VALUE: {}", result);

        return result;
    }
}
