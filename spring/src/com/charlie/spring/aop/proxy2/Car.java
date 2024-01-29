package com.charlie.spring.aop.proxy2;

public class Car implements Vehicle {
    @Override
    public void run() {
        //System.out.println("交通工具开始运行了....");
        System.out.println("小汽车在公路 running...");
        //System.out.println("交通停止运行....");
    }

    @Override
    public String fly(int height) {
        System.out.println("小汽车可以飞翔，高度=" + height);
        return "小汽车可以飞翔，高度=" + height;
    }
}
