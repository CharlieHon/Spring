package com.charlie.spring.aop.aspectj;

import org.springframework.stereotype.Component;

@Component
public class Car {
    public void run() {
        System.out.println("小汽车在running...");
    }
}
