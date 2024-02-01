package com.charlie.spring.process;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

// bean后置处理器，需要在xml文件中配置或者使用注解
//@Component
public class MyBeanPostProcessor implements BeanPostProcessor {
    /**
     * bean的生命周期：
     * 1) 创建bean 2) 执行set相关方法 3) 执行初始化方法 4) 使用bean 5) 当容器关闭时，调用bean的销毁方法
     * 在bean的 init初始化方法前调用
     * @param bean 就是ioc容器返回的bean对象，如果这里被替换或修改，则返回的bean对象也会被修改
     * @param beanName  就是ioc容器配置的bean的名字
     * @return  返回的bean独享
     */
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        System.out.println("postProcessBeforeInitialization 被调用-" + beanName + "-bean=" + bean.getClass());
        return bean;
    }

    // 在bean初始化方法调用后执行
    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        System.out.println("postProcessAfterInitialization 被调用-" + beanName + "-bean=" + bean.getClass());
        return bean;
    }
}
