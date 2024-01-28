package com.charlie.spring.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 1. @Target(ElementType.TYPE) 指定ComponentScn注解可以修饰Type程序元素
 * 2. @Retention(RetentionPolicy.RUNTIME) 指定ComponentScan注解作用/保留范围
 * 3. String value() default ""; 标识ComponentScan可以传入value属性
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ComponentScan {
    String value() default "";
}
