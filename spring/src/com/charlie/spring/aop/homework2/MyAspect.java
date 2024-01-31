package com.charlie.spring.aop.homework2;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Aspect
@Order(value = 1)
public class MyAspect {
    @Pointcut(value = "execution(* Cal.*(..))")
    public void myPointCut() {}

    @Before(value = "myPointCut()")
    public void showBeginLog(JoinPoint joinPoint) {
        long l = System.currentTimeMillis();
        System.out.println("开始执行计算：" + l);
    }

    @AfterReturning(value = "myPointCut()", returning = "result")
    public void showSuccessLog(JoinPoint joinPoint, Object result) {
        long l = System.currentTimeMillis();
        System.out.println("结束执行计算：" + l);
    }
}
