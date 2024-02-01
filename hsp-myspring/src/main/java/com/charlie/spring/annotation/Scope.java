package com.charlie.spring.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

// Scope 可以指定Bean的作用范围{singleton, prototype}
@Target(value = ElementType.TYPE)
@Retention(value = RetentionPolicy.RUNTIME)
public @interface Scope {
    // 通过value指定是singleton还是prototype
    String value() default "";
}
