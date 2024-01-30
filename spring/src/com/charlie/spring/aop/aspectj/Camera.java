package com.charlie.spring.aop.aspectj;

import org.springframework.stereotype.Component;

@Component
public class Camera implements UsbInterface {
    @Override
    public void work() {
        System.out.println("相机的接口不晓得...");
    }
}
