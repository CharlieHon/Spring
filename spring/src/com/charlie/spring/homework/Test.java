package com.charlie.spring.homework;

public class Test {
    public static void main(String[] args) {
        MySpringApplicationContext ioc = new MySpringApplicationContext(MySpringConfig.class);
        System.out.println("OK!");
    }
}
