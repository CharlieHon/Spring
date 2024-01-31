package com.charlie.spring.aop.homework2.xml;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class HomeworkTest {
    @Test
    public void aspectTest() {
        ApplicationContext ioc = new ClassPathXmlApplicationContext("beans11.xml");
        Cal myCal = ioc.getBean("myCal", Cal.class);
        myCal.cal1(11);
        System.out.println("=======xml配置方式=======");
        myCal.cal2(5);
    }
}
