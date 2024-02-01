package com.charlie.spring.component;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

@Component
public class UserService {
    // 定义一个属性
    // 思考：加入 @Autowired，Spring容器是如何实现依赖注入的
    @Autowired   // 也可以使用 @Resource
    private UserDAO userDAO;

    public void m1() {
        userDAO.hi();
    }

    // 这里需要指定 init() 方法是初始化方法
    @PostConstruct  // 类似于在xml中 <init-method="init">
    public void init() {
        System.out.println("UserService-init()...");
    }

    public void destroy() {
        System.out.println("UserService-destroy()..");
    }
}
