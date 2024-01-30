package com.charlie.spring.aop.aspectj;

import org.springframework.stereotype.Component;

@Component  // 把Phone对象当作一个主键注入容器
public class Phone implements UsbInterface {
    @Override
    public void work() {
        System.out.println("iPhone使用不同的接口");
    }
}
