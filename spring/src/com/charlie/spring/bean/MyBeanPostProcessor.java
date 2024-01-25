package com.charlie.spring.bean;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

// 这是一个后置处理器
public class MyBeanPostProcessor implements BeanPostProcessor {

    /**
     * 在bean的init方法前被调用
     * @param bean 传入的在ioc容器中创建/配置的bean
     * @param beanName bean的id
     * @return Object是程序员对传入的bean进行修改/处理后(如果需要)，再返回的
     */
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        System.out.println("postProcessBeforeInitialization()... bean=" + bean + ", beanName=" + beanName);
        return bean;
    }

    /**
     * 在bean的init方法后被调用
     * @param bean 传入的在ioc容器中创建/配置的bean
     * @param beanName bean的id
     * @return Object是程序员对传入的bean进行修改/处理后(如果需要)，再返回的
     */
    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        //System.out.println("postProcessAfterInitialization()... bean=" + bean + ", beanName=" + beanName);

        // 对多个对象进行处理/编程==>切面编程
        if (bean instanceof House) {
            ((House) bean).setName("皇家园林");
        }
        return bean;
    }
}
