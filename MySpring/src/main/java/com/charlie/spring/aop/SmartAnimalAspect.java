package com.charlie.spring.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
@Aspect
public class SmartAnimalAspect {
    // 给SmartDog配置前置，返回，异常，最终通知
    @Before(value = "execution(public float SmartDog.getSum(float, float ))")
    public void showBeginLog(JoinPoint joinPoint) {
        Signature signature = joinPoint.getSignature();
        System.out.println("SmartAnimalAspect-切面类showBeginLog()-方法" + signature.getName()
                + "-前置通知-参数：" + Arrays.asList(joinPoint.getArgs()));
    }

    @AfterReturning(value = "execution(public float SmartDog.getSum(float, float))", returning = "res")
    public void showSuccessEnd(JoinPoint joinPoint, Object res) {
        Signature signature = joinPoint.getSignature();
        System.out.println("SmartAnimalAspect-切面类showSuccessEnd()-方法" + signature.getName()
                + "-返回通知-res=" + res);
    }

    @AfterThrowing(value = "execution(public float SmartDog.getSum(float, float))", throwing = "throwable")
    void showExceptionLog(JoinPoint joinPoint, Throwable throwable) {
        Signature signature = joinPoint.getSignature();
        System.out.println("SmartAnimalAspect-切面类showExceptionLog()-方法" + signature.getName()
                + "-异常通知=" + throwable.getClass());
    }

    @After(value = "execution(public float SmartDog.getSum(float, float))")
    public void showFinallyLog(JoinPoint joinPoint) {
        Signature signature = joinPoint.getSignature();
        System.out.println("SmartAnimalAspect-切面类showFinallyLog()-方法" + signature.getName()
                + "方法最终执行完毕");
    }
}
