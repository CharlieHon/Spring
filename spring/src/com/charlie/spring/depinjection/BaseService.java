package com.charlie.spring.depinjection;

import org.springframework.beans.factory.annotation.Autowired;

// 自定义泛型类
public class BaseService<T> {
    @Autowired
    private BaseDAO<T> baseDAO;

    public void save() {
        baseDAO.save();
    }
}
