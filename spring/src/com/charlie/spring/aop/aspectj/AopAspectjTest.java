package com.charlie.spring.aop.aspectj;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class AopAspectjTest {
    @Test
    public void aPoTestByProxy() {
        ApplicationContext ioc = new ClassPathXmlApplicationContext("beans08.xml");
        // 这里需要通过接口类型来获取到注入的APo对象，即代理对象
        /*
        因为底层是通过接口注入的
        SmartAnimal proxy = (SmartAnimal) Proxy.newProxyInstance(classLoader, interfaces, invocationHandler)
        ioc.getBean() 类似于之前写的 getProxy() 返回的是一个代理对象，虽然其ioc容器中保留的仍然是APo
         */
        SmartAnimal smartAnimal = (SmartAnimal) ioc.getBean("APo");
        //SmartAnimal smartAnimal = ioc.getBean(SmartAnimal.class);
        System.out.println("smartAnimal类型=" + smartAnimal.getClass());  // class com.sun.proxy.$Proxy13
        smartAnimal.getSum(10, 2);
        System.out.println("================");
        smartAnimal.getSub(10, 2);
    }

    @Test
    public void testHomework() {
        ApplicationContext ioc = new ClassPathXmlApplicationContext("beans08.xml");
        UsbInterface phone = ioc.getBean("phone", UsbInterface.class);
        phone.work();
        System.out.println("=============");
        UsbInterface camera = (UsbInterface) ioc.getBean("camera");
        camera.work();
    }
}
