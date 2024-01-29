package com.charlie.spring.aop.proxy3;

import java.lang.reflect.Method;
import java.util.Arrays;

// 自定义编写的一个极简AOP类
public class HspAOP {
    // 在目标对象执行前执行
    public static void before(Object proxy, Method method, Object[] args) {
        // 从AOP看，就是一个横切关注点-前置通知
        System.out.println("(HspAOP)方法执行前-日志-方法名-" + method.getName() + "-参数-" + Arrays.toString(args));
    }

    public static void after(Method method, Object result) {
        // AOP-横切关注点，返回通知
        System.out.println("(HspAOP)方法正常执行后-日志-方法名-" + method.getName() + "-result=" + result);
    }
}
