package com.charlie.spring;

import com.charlie.spring.annotation.AfterReturning;
import com.charlie.spring.annotation.Before;
import com.charlie.spring.component.MonsterDAO;
import com.charlie.spring.component.MonsterService;
import com.charlie.spring.component.MySmartAnimalAspect;
import com.charlie.spring.component.SmartAnimal;
import com.charlie.spring.ioc.MySpringApplicationContext;
import com.charlie.spring.ioc.MySpringConfig;
import org.junit.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class AppMain {
    @Test
    public void test1() {
        MySpringApplicationContext ioc = new MySpringApplicationContext(MySpringConfig.class);
        //MonsterDAO monsterDAO = (MonsterDAO) ioc.getBean("monsterDAO");
        //MonsterDAO monsterDAO2 = (MonsterDAO) ioc.getBean("monsterDAO");
        //System.out.println("monsterDAO=" + monsterDAO);
        //System.out.println("monsterDAO2=" + monsterDAO2);

        // 测试一下依赖注入的功能
        MonsterService monsterService = (MonsterService) ioc.getBean("monsterService");
        monsterService.m1();
        System.out.println("OK!");
    }

    @Test
    public void testAOP() {
        MySpringApplicationContext ioc = new MySpringApplicationContext(MySpringConfig.class);
        SmartAnimal smartDog = (SmartAnimal) ioc.getBean("smartDog");
        System.out.println("smartDog=" + smartDog.getClass());  // class com.sun.proxy.$Proxy5
        smartDog.getSum(10, 2);
        System.out.println("==========================");
        smartDog.getSub(10, 2);
        System.out.println("OK!");
    }

    @Test
    public void testAOPByAnnotation() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Class<MySmartAnimalAspect> mySmartAnimalAspectClass = MySmartAnimalAspect.class;
        // 1. 得到注解类中声明的方法
        for (Method declaredMethod : mySmartAnimalAspectClass.getDeclaredMethods()) {
            // 2. 根据方法的注解进行处理
            if (declaredMethod.isAnnotationPresent(Before.class)) {
                // 3. 得到方法名，如 showBeginLog, showSuccessLog
                String methodName = declaredMethod.getName();
                System.out.println("前置通知：" + methodName);
                Before beforeAnnotation = declaredMethod.getAnnotation(Before.class);
                // 4. 得到注解类配置的切入表达式，如 value = "execution(public float SmartDog.getSum(float, float))"
                String target = beforeAnnotation.value();
                System.out.println("切入对象：" + target);
                // 5. 拿到并执行该方法
                //Method declaredMethod1 = mySmartAnimalAspectClass.getDeclaredMethod(methodName);
                declaredMethod.invoke(new MySmartAnimalAspect());
            } else if (declaredMethod.isAnnotationPresent(AfterReturning.class)) {
                // showSuccessLog
                String methodName = declaredMethod.getName();
                System.out.println("返回通知：" + methodName);
                AfterReturning afterReturningAnnotation = declaredMethod.getAnnotation(AfterReturning.class);
                String target = afterReturningAnnotation.value();
                System.out.println("切入对象：" + target);
                //Method declaredMethod1 = mySmartAnimalAspectClass.getDeclaredMethod(methodName);
                declaredMethod.invoke(new MySmartAnimalAspect());
            }
            System.out.println("================");
        }
    }
}
