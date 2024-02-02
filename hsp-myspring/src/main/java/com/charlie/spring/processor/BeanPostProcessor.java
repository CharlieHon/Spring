package com.charlie.spring.processor;

/*
1. 参考原生Spring容器定义的一个接口
2. 该接口有两个方法 postProcessorBeforeInitialization 和 postProcessorAfterInitialization
3. 这两个方法，会对Spring容器的所有Bean生效，已经是切面编程的概念
 */
public interface BeanPostProcessor {

    // 在Bean的初始化方法执行前调用
    default Object postProcessBeforeInitialization(Object bean, String beanName) {
        return bean;
    }

    // 在Bean的初始化方法执行后调用
    default Object postProcessAfterInitialization(Object bean, String beanName) {
        return bean;
    }
}
