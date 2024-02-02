package com.charlie.spring.component;

import com.charlie.spring.annotation.Component;

@Component(value = "smartDog")
public class SmartDog implements  SmartAnimal{
    @Override
    public float getSum(float i, float j) {
        float res = i + j;
        System.out.println("SmartDog-getSum-res=" + res);
        return res;
    }

    @Override
    public float getSub(float i, float j) {
        float res = i - j;
        System.out.println("SmartDog-getSub-res=" + res);
        return res;
    }
}
