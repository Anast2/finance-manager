package com.itclopedia.cources.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

@Component
@Aspect
@Slf4j
public class ServiceAspect {

    @Pointcut("execution(* com.itclopedia.cources.services.*(..))")
    public void servicePointcut() {
    }

    @AfterThrowing(value = "servicePointcut()", throwing = "exception")
    public void logAfterThrowing(JoinPoint joinPoint, Exception exception) {
        log.error("Exception in method {}, in class {}, with message {}",
                joinPoint.getSignature().getName(), joinPoint.getTarget().getClass().getName(), exception.getMessage());
    }

    @Before(value = "servicePointcut()")
    public void logBefore(JoinPoint joinPoint) {
        log.info("Before method {}, in class {}",
                joinPoint.getSignature().getName(), joinPoint.getTarget().getClass().getName());
    }

    @After(value = "servicePointcut()")
    public void logAfter(JoinPoint joinPoint) {
        log.info("After method {}, in class {}",
                joinPoint.getSignature().getName(), joinPoint.getTarget().getClass().getName());
    }

}
