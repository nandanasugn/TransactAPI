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
public class AuditingAspect {
    @Pointcut("execution(* com.nandana.transactapi.service.IAuthService.register(..))")
    public void userRegistration() {}

    @Pointcut("execution(* com.nandana.transactapi.service.IAuthService.login(..))")
    public void userLogin() {}

    @Around("userRegistration() || userLogin()")
    public Object auditUserAction(ProceedingJoinPoint joinPoint) throws Throwable {
        String methodName = joinPoint.getSignature().getName();
        String className = joinPoint.getTarget().getClass().getName();

        log.info("AUDIT: Starting {} action in class {}", methodName, className);

        try {
            Object result = joinPoint.proceed();
            log.info("AUDIT: Ending {} action in class {}", methodName, className);
            return result;
        } catch (Throwable throwable) {
            log.error("AUDIT: Failed to complete {} action in class {}", methodName, className, throwable);
            throw throwable;
        }
    }
}
