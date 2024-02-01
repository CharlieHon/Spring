package com.charlie.spring;

import com.charlie.spring.aop.SmartAnimal;
import com.charlie.spring.component.UserAction;
import com.charlie.spring.component.UserDAO;
import com.charlie.spring.component.UserService;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class AppMain {
    public static void main(String[] args) {
        // 测试看是否可以得到spring容器中的bean，同时看看依赖注入是否成功
        ApplicationContext ioc = new ClassPathXmlApplicationContext("beans.xml");
        UserAction userAction = ioc.getBean("userAction", UserAction.class);
        UserAction userAction2 = ioc.getBean("userAction", UserAction.class);
        System.out.println("userAction=" + userAction);
        System.out.println("userAction2=" + userAction2);

        UserDAO userDAO = ioc.getBean("userDAO", UserDAO.class);
        System.out.println("userDAO=" + userDAO);

        UserService userService = ioc.getBean("userService", UserService.class);
        System.out.println("userService=" + userService);

        // 测试一下当前的依赖注入
        userService.m1();
    }

    @Test
    public void testSmartDog() {
        ApplicationContext ioc = new ClassPathXmlApplicationContext("beans.xml");
        SmartAnimal smartAnimal = ioc.getBean(SmartAnimal.class);
        smartAnimal.getSum(10, 2);
    }
}
