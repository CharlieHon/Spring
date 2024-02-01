package com.charlie.spring.component;

import com.charlie.spring.annotation.Autowired;
import com.charlie.spring.annotation.Component;
import com.charlie.spring.annotation.Scope;

// MonsterService对象是一个service
// 1. 如果指定value，那么在注入spring容器时，以指定为准
// 2. 如果没有指定value，则使用类名首字母小写
@Component(value = "monsterService")  // 把MonsterService对象注入到我们自己的spring容器
@Scope(value = "prototype")
public class MonsterService {
    // 这里使用自定义的Autowired来修饰属性，表示该属性是通过容器完成依赖注入的
    // 说明：自定义实现按照名字来进行组装即可
    @Autowired
    private MonsterDAO monsterDAO;

    public void m1() {
        monsterDAO.hi();
    }
}
