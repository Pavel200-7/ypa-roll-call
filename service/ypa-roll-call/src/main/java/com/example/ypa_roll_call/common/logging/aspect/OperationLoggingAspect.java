package com.example.ypa_roll_call.common.logging.aspect;


import com.example.ypa_roll_call.common.logging.annotation.LogOperation;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
@Order(Ordered.LOWEST_PRECEDENCE)
public class OperationLoggingAspect {

    @Around("@annotation(logOperation)")
    public Object logOperation(
            ProceedingJoinPoint joinPoint,
            LogOperation logOperation
    ) throws Throwable {
        String operation = logOperation.value();

        log.debug("The operation '{}' has been started", operation);
        try {
            Object result = joinPoint.proceed();
            log.debug("The operation '{}' has been finished successfully", operation);
            return result;
        } catch (Throwable e) {
            log.debug("The operation '{}' has been finished with error: {}",
                    operation, e.getMessage());
            throw e;
        }
    }
}