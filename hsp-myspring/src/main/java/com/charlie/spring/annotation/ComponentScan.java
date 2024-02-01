package com.charlie.spring.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 指定要扫描包的路径，如 com.charlie.spring.component
 * 1. @Target(value = ElementType.FIELD) 指定ComponentScan注解可以修饰 Type 程序元素
 * 2. @Retention(value = RetentionPolicy.RUNTIME) 指定ComponentScan注解保留范围
 * 3. String value() default ""; 表示可以传入value参数，用于指定扫描的包
 */
@Target(value = ElementType.TYPE)
@Retention(value = RetentionPolicy.RUNTIME)
public @interface ComponentScan {
    String value() default "";
}
