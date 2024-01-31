package com.charlie.spring.aop.homework2.xml;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

public class MyAspect {

    public void showBeginLog(JoinPoint joinPoint) {
        long l = System.currentTimeMillis();
        System.out.println("开始执行计算：" + l);
    }

    public void showSuccessLog(JoinPoint joinPoint, Object result) {
        long l = System.currentTimeMillis();
        System.out.println("结束执行计算：" + l);
    }
}
