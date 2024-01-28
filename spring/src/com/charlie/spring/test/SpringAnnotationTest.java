package com.charlie.spring.test;

import com.charlie.spring.component.UserAction;
import com.charlie.spring.component.UserService;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SpringAnnotationTest {
    @Test
    public void setProAutowired() {
        ApplicationContext ioc = new ClassPathXmlApplicationContext("beans06.xml");
        UserAction userAction = ioc.getBean("userAction", UserAction.class);
        UserService userService = ioc.getBean("userService", UserService.class);
        System.out.println("ioc中获取的userService=" + userService);
        userAction.sayOk();
    }
}
