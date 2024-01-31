package com.charlie.spring.aop.aspectj;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

// 演示环绕通知的时候，需要注释掉之前的切面类的注解，不然会切入两个方法
//@Component  // 会注入到IOC容器
//@Aspect     // 标识是一个切面类，底层有切面编程支撑(动态代理+反射+动态绑定)
public class SmartAnimalAspect2 {
    // 演示环绕通知使用
    // 1. @Around 表示这是一个环绕通知，可以完成其它四个通知的功能(前置、返回、异常、最终/后置)
    // 2. value = "" 切入表达式
    // 3. doAround 表示要切入的方法，调用的结构：try-catch-finally
    @Around(value = "execution(public int com.charlie.spring.aop.aspectj.APo.getSum(int, int))")
    public Object doAround(ProceedingJoinPoint joinPoint) {
        Object result = null;
        String methodName = joinPoint.getSignature().getName();
        try {
            // 1. 相当于前置通知完成的事情
            Object[] args = joinPoint.getArgs();
            List<Object> argList = Arrays.asList(args);
            System.out.println("AOP环绕通知-" + methodName + "-方法开始-参数有：" + argList);
            // 在环绕通知中，一定要调用 joinPoint.proceed() 求执行目标方法
            result = joinPoint.proceed(args);
            // 2. 相当于返回通知完成的事情
            System.out.println("AOP环绕通知-" + methodName + "-方法结束-执行结果：" + result);
        } catch (Throwable throwable) {
            // 3. 相当于异常通知要完成的事情
            System.out.println("AOP环绕通知-" + methodName + "-方法抛出异常-异常对象：" + throwable.getClass());
        } finally {
            // 4. 相当于最终通知完成的事情
            System.out.println("AOP环绕通知-" + methodName + "-方法最终结束...");
        }
        return result;
    }
}
