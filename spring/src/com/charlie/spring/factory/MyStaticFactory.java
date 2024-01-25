package com.charlie.spring.factory;

import com.charlie.spring.bean.Monster;

import java.util.HashMap;
import java.util.Map;

// 静态工厂类,可以返回Monster对象
public class MyStaticFactory {
    private static Map<String, Monster> monsterMap;

    // 使用静态代码块进行初始化
    static {
        monsterMap = new HashMap<>();
        monsterMap.put("monster01", new Monster(100, "牛魔王", "芭蕉扇"));
        monsterMap.put("monster02", new Monster(200, "狐狸精", "美人计"));
    }

    public static Monster getMonster(String key) {
        return monsterMap.get(key);
    }
}
