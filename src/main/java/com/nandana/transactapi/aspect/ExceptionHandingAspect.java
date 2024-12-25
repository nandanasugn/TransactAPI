package com.nandana.transactapi.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Slf4j
@Component
public class ExceptionHandingAspect {
    @Pointcut("execution(* com.nandana.transactapi.service.*.*(..))")
    public void serviceLayer() {
    }

    @AfterThrowing(pointcut = "serviceLayer()", throwing = "ex")
    public void handleException(Exception ex) {
        log.error("EXCEPTION: {}", ex.getMessage());
    }
}
