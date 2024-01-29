package com.charlie.spring.aop.proxy3;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;

// 可以返回一个动态代理对象，可以执行 APo对象的方法
public class SmartProxyProvider {
    // 定义要执行的目标对象，该对象需要实现 SmartAnimal接口
    private SmartAnimal target_obj;

    // 构造器
    public SmartProxyProvider(SmartAnimal target_obj) {
        this.target_obj = target_obj;
    }

    //// 在目标对象执行前执行
    //public void before(Object proxy, Method method, Object[] args) {
    //    // 从AOP看，就是一个横切关注点-前置通知
    //    System.out.println("(before)方法执行前-日志-方法名-" + method.getName() + "-参数-" + Arrays.toString(args));
    //}
    //
    //// 在目标对象执行后执行
    //public void after(Method method, Object result) {
    //    // AOP-横切关注点，返回通知
    //    System.out.println("(after)方法正常执行后-日志-方法名-" + method.getName() + "-result=" + result);
    //}

    // 方法，可以返回代理对象，该代理对象可以执行目标对象
    public SmartAnimal getProxy() {
        // 1) 先得到类加载器/对象
        ClassLoader classLoader = target_obj.getClass().getClassLoader();
        // 2) 得到要执行的目标对象的接口信息
        Class<?>[] interfaces = target_obj.getClass().getInterfaces();
        // 3) 创建 InvocationHandler
        InvocationHandler invocationHandler = new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                Object result = null;
                try {
                    // before
                    HspAOP.before(proxy, method, args);
                    // 使用反射调用方法
                    result =  method.invoke(target_obj, args);
                    // after
                    HspAOP.after(method, result);
                    return result;
                } catch (Exception e) {
                    e.printStackTrace();
                    //  AOP-横切关注点，异常通知
                    System.out.println("方法执行异常-日志-方法名-" + method.getName() + "-异常类型=" + e.getClass().getName());
                } finally { // 不管是否出现异常，最终都会执行到 finally{}
                    // AOP-横切关注点-最终通知
                    System.out.println("方法最终结束-日志-方法名-" + method.getName());
                }
                return result;
            }
        };
        // 4. 创建并返回代理对象
        return (SmartAnimal) Proxy.newProxyInstance(classLoader, interfaces, invocationHandler);
    }
}
