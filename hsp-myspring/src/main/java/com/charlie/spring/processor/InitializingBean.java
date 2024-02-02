package com.charlie.spring.processor;

/*
1. 根据原生Spring定义了一个 InitializingBean
2. 该InitializingBean接口有一个方法 afterPropertiesSet
3. 该方法在Bean的setter方法后执行，类似于原来的初始化方法
4. 当一个Bean实现了这个接口后，就实现了afterPropertiesSet()，这个方法就是初始化方法
 */
public interface InitializingBean {
    void afterPropertiesSet() throws Exception;
}
