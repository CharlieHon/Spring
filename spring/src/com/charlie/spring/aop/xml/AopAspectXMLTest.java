package com.charlie.spring.aop.xml;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class AopAspectXMLTest {

    @Test
    public void testAspectByXML() {
        ApplicationContext ioc = new ClassPathXmlApplicationContext("beans09.xml");
        SmartAnimal smartAnimal = ioc.getBean(SmartAnimal.class);
        smartAnimal.getSum(10, 2);
    }
}
