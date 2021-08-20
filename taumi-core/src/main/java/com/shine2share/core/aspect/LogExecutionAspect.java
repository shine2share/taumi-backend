package com.shine2share.core.aspect;

import com.shine2share.common.BusinessException;
import com.shine2share.common.CommonUtil;
import com.shine2share.common.utils.KafkaUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LogExecutionAspect {
    private final Logger logger = LoggerFactory.getLogger(LogExecutionAspect.class);
    private final KafkaUtils kafkaUtils;

    public LogExecutionAspect(KafkaUtils kafkaUtils) {
        this.kafkaUtils = kafkaUtils;
    }
    @Around("@annotation(com.shine2share.core.aspect.LogExecutionTime)")
    public Object processLogExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        logger.warn("LogExecutionAspect-processLogExecutionTime: " + joinPoint);
        long start = System.currentTimeMillis();
        Object result = joinPoint.proceed();
        String response = CommonUtil.beanToString(result);
        long end = System.currentTimeMillis();
        long executionTime = end - start;
        save2Db();
        return result;
    }
    @AfterThrowing(throwing = "e", pointcut = "@annotation(com.shine2share.core.aspect.LogExecutionTime)")
    public void processExceptionLoggerPointcut(JoinPoint joinPoint, Throwable e) {
        logger.warn("LogExecutionAspect-processExceptionLoggerPointcut: " + joinPoint);
        String message = e.getMessage();
        int httpStatusCode = 500;
        if (e instanceof BusinessException) {
            BusinessException businessException = (BusinessException) e;
            message = businessException.getMessage();
            httpStatusCode = businessException.getHttpStatus().value();
        }
        save2Db();
    }

    private void save2Db() {
    }
}
