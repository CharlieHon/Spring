package com.charlie.spring.aop.xml;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;

import java.util.Arrays;

// 这是一个基于xml配置的切面类
public class SmartAnimalAspect {

    public void showBeginLog(JoinPoint joinPoint) {
        // 通过链接点对象joinPoint可以获取方法签名，即com.charlie.spring.aop.aspectj.APo.getSum(int, int)
        Signature signature = joinPoint.getSignature();
        System.out.println("基于xml-showBeginLog()[使用切入点表达式重用]-方法执行前-日志-方法名-" + signature.getName() + "-参数-" +
                Arrays.toString(joinPoint.getArgs()));
    }

    public void showSuccessEndLog(JoinPoint joinPoint, Object res) {
        Signature signature = joinPoint.getSignature();
        System.out.println("基于xml-showSuccessEndLog()-方法执行正常结束-日志-方法名-" + signature.getName()
                + "-返回结果=" + res);
    }

    public void showExceptionLog(JoinPoint joinPoint, Throwable throwable) {
        Signature signature = joinPoint.getSignature();
        System.out.println("基于xml-showExceptionLog()-方法执行异常-日志-方法名-" + signature.getName() + "-异常信息=" + throwable);
    }

    public void showFinallyEndLog(JoinPoint joinPoint) {
        Signature signature = joinPoint.getSignature();
        System.out.println("基于xml-showFinallyEndLog()-方法执行异常-日志-方法名-" + signature.getName());
    }
}
