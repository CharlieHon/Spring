package com.charlie.spring.aop.aspectj;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.*;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Arrays;

// 切面类，类似于之前写的MyProxyProvider，但是功能强大很多
@Component  // 会注入到IOC容器
@Order(value = 1)
@Aspect     // 标识是一个切面类，底层有切面编程支撑(动态代理+反射+动态绑定)
public class SmartAnimalAspect3 {

    @Before(value = "execution(public int com.charlie.spring.aop.aspectj.APo.getSum(int, int))")
    public void showBeginLog(JoinPoint joinPoint) {
        // 通过链接点对象joinPoint可以获取方法签名，即com.charlie.spring.aop.aspectj.APo.getSum(int, int)
        Signature signature = joinPoint.getSignature();
        System.out.println("SmartAnimalAspect3切面类-showBeginLog()[使用切入点表达式重用]-方法执行前-日志-方法名-" + signature.getName() + "-参数-" +
                Arrays.toString(joinPoint.getArgs()));
    }

    @AfterReturning(value = "execution(public int com.charlie.spring.aop.aspectj.APo.getSum(int, int))",
            returning = "res") // 返回通知
    public void showSuccessEndLog(JoinPoint joinPoint, Object res) {
        Signature signature = joinPoint.getSignature();
        System.out.println("SmartAnimalAspect3切面类-showSuccessEndLog()-方法执行正常结束-日志-方法名-" + signature.getName()
                + "-返回结果=" + res);
    }

    @AfterThrowing(value = "execution(public int com.charlie.spring.aop.aspectj.APo.getSum(int, int))",
            throwing = "throwable")
    public void showExceptionLog(JoinPoint joinPoint, Throwable throwable) {
        Signature signature = joinPoint.getSignature();
        System.out.println("SmartAnimalAspect3切面类-showExceptionLog()-方法执行异常-日志-方法名-" + signature.getName() + "-异常信息=" + throwable);
    }

    @After(value = "execution(public int com.charlie.spring.aop.aspectj.APo.getSum(int, int))") // 返回通知
    public void showFinallyEndLog(JoinPoint joinPoint) {
        Signature signature = joinPoint.getSignature();
        System.out.println("SmartAnimalAspect3切面类-showFinallyEndLog()-方法执行异常-日志-方法名-" + signature.getName());
    }
}
