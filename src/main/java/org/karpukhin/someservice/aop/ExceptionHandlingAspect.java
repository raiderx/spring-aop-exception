package org.karpukhin.someservice.aop;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.support.AopUtils;
import org.springframework.stereotype.Component;
import org.karpukhin.someservice.model.BaseResponse;

import java.lang.reflect.Method;

@Aspect
@Component
@Slf4j
public class ExceptionHandlingAspect {

    @Pointcut("@annotation(org.karpukhin.someservice.aop.Traceable)")
    public void someService() { }

    @Around(value = "someService()")
    public Object aroundAdvice(ProceedingJoinPoint joinPoint) {
        BaseResponse response;
        try {
            response = (BaseResponse) joinPoint.proceed();
            response.setSucceed(true);
        } catch (IllegalArgumentException e) {
            response = handleError(joinPoint, e.getMessage(), e);
        } catch (Throwable t) {
            response = handleError(joinPoint, "Internal error", t);
        }
        return response;
    }

    private BaseResponse handleError(ProceedingJoinPoint joinPoint, String message, Throwable t) {
        MethodSignature signature = (MethodSignature)joinPoint.getSignature();
        Logger logger = LoggerFactory.getLogger(AopUtils.getTargetClass(joinPoint.getTarget()));
        logger.error("Could not execute {}", name(signature.getMethod()), t);
        try {
            BaseResponse response = (BaseResponse)signature.getReturnType().newInstance();
            response.setSucceed(false);
            response.setCause(message);
            return response;
        } catch (IllegalAccessException | InstantiationException e) {
            throw new IllegalArgumentException("Could not create instance if " + signature.getReturnType(), e);
        }
    }

    private String name(Method method) {
        Traceable traceable = method.getAnnotation(Traceable.class);
        if (traceable != null && StringUtils.isNotBlank(traceable.value())) {
            return traceable.value();
        }
        return method.toString();
    }
}
