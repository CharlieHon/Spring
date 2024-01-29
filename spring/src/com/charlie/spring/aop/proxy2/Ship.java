package com.charlie.spring.aop.proxy2;

public class Ship implements Vehicle {
    @Override
    public void run() {
        //System.out.println("交通工具开始运行了....");
        System.out.println("轮船在水中 running...");
        //System.out.println("交通停止运行....");
    }

    @Override
    public String fly(int height) {
        System.out.println("轮船也可以飞起来了，height=" + height);
        return "轮船也可以飞起来了，height=" + height;
    }
}
