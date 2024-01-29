package com.charlie.spring.depinjection;

// 自定义泛型类
public abstract class BaseDAO<T> {
    public abstract void save();
}
