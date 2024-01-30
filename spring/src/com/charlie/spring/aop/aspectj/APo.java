package com.charlie.spring.aop.aspectj;

import org.springframework.stereotype.Component;

@Component  // 当Spring容器启动时，将APo注入到容器
public class APo implements SmartAnimal {
    @Override
    public int getSum(int num1, int num2) {
        int result = num1 + num2;
        //result = 9 / 0;
        System.out.println("方法内部打印 result=" + result);
        return result;
    }

    @Override
    public int getSub(int num1, int num2) {
        int result = num1 - num2;
        System.out.println("方法内部打印 result=" + result);
        return result;
    }
}
