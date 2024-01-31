package com.charlie.spring.aop.homework2;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class HomeworkTest {
    @Test
    public void aspectTest() {
        ApplicationContext ioc = new ClassPathXmlApplicationContext("beans10.xml");
        Cal myCal = ioc.getBean("myCal", Cal.class);
        myCal.cal1(11);
        System.out.println("=======注解方式=======");
        myCal.cal2(5);
    }
}
