package com.charlie.spring.component;

import com.charlie.spring.annotation.Component;
import com.charlie.spring.processor.BeanPostProcessor;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/*
1. 这是我们自定义的一个后置处理器
2. 实现了BeanPostProcessor
3. 可以重写 before 和 after方法
4. 在Spring容器中，仍热把MyBeanPostProcessor当作一个Bean对象，注入到容器中
5. 使用 @Component 标识
6. 要让MyBeanPostProcessor成为真正的后置处理器，需要在容器中加入业务代码
7. 还要考虑多个后置处理器对象注入到容器的问题
 */
@Component
public class MyBeanPostProcessor implements BeanPostProcessor {
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) {
        // 注意：后置处理器对容器创建的所有bean都生效，即可以对多个对象编程
        // 日志、权限、事务...
        if (bean instanceof Car) {
            System.out.println("这是一个Car对象，可以进行处理...");
        }
        System.out.println("MyBeanPostProcessor后置处理器 before被调用，bean类型=" + bean.getClass()
                + "，bean的名字=" + beanName);
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) {
        System.out.println("MyBeanPostProcessor后置处理器 after被调用，bean类型=" + bean.getClass()
                + "，bean的名字=" + beanName);

        // 实现AOP，返回代理对象，即对Bean进行包装
        // 1. 先死后活，后面通过注解可以更加灵活
        if ("smartDog".equals(beanName)) {
            // 使用JDK动态代理，返回该bean的代理对象
            Object proxyInstance = Proxy.newProxyInstance(MyBeanPostProcessor.class.getClassLoader(), bean.getClass().getInterfaces(),
                    new InvocationHandler() {
                        @Override
                        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                            System.out.println("method=" + method.getName());
                            Object result = null;
                            // 假设要进行切入的方法是 getSum()，后面可以通过注解变得更加灵活
                            if ("getSum".equals(method.getName())) {
                                // 前置通知
                                MySmartAnimalAspect.showBeginLog();
                                // 执行目标方法
                                result = method.invoke(bean, args);
                                // 返回通知
                                MySmartAnimalAspect.showSuccessLog();
                            } else {
                                result = method.invoke(bean, args);
                            }
                            return result;
                        }
                    });
            // 如果bean是需要返回代理对象的，在这里返回
            return proxyInstance;
        }
        // 不需要AOP处理，就返回原生bean
        return bean;
    }
}
