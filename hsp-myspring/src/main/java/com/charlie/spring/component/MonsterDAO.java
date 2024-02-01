package com.charlie.spring.component;

import com.charlie.spring.annotation.Component;

@Component(value = "monsterDAO")
//@Scope(value = "prototype")   // 默认是单例的
public class MonsterDAO {
    public void hi() {
        System.out.println("MonsterDAO.hi()...");
    }
}
