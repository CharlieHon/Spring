package com.charlie.spring.component;

import com.charlie.spring.annotation.Component;
import com.charlie.spring.processor.InitializingBean;

@Component(value = "monsterDAO")
//@Scope(value = "prototype")   // 默认是单例的
public class MonsterDAO implements InitializingBean {
    public void hi() {
        System.out.println("MonsterDAO.hi()...");
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("MonsterDAO 初始化方法被调用...");
    }
}
