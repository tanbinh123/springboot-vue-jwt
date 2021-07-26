package com.yccztt.config;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * 使用Aspect配置输出日志
 * @Author hyz
 * @Date 2021/7/26
 */
@Aspect
@Component
public class LogAspect {

    @Pointcut("execution(* com.yccztt..*.*(..))")
    public void log() {}

    /**
     * 进入方法前
     * @param joinPoint
     */
    @Before("log()")
    public void doBefore(JoinPoint joinPoint) {
        System.out.printf("进入%s方法%n", joinPoint.getSignature().getName());
    }

    /**
     * 进入方法后
     * @param joinPoint
     */
    @After("log()")
    public void doAfter(JoinPoint joinPoint) {
        System.out.printf("离开%s方法%n", joinPoint.getSignature().getName());
    }

}
