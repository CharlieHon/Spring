package com.charlie.spring.component;

import com.charlie.spring.annotation.*;

/*
说明：MySmartAnimalAspect 当作一个切面来使用
后面再分析如何做得更加灵活
 */
@Aspect // 自定义注解
@Component
public class MySmartAnimalAspect {

    @Before(value = "execution public float com.charlie.spring.component.SmartDog getSum")
    public static void showBeginLog() {
        System.out.println("前置通知执行...");
    }

    @AfterReturning(value = "execution public float com.charlie.spring.component.SmartDog getSum)")
    public static void showSuccessLog() {
        System.out.println("返回通知执行...");
    }
}
