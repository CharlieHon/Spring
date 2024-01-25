package com.charlie.spring.factory;

import com.charlie.spring.bean.Monster;

import java.util.HashMap;
import java.util.Map;

public class MyInstanceFactory {
    private Map<String, Monster> monster_map;

    // 通过普通代码块进行初始化
    {
        monster_map = new HashMap<>();
        monster_map.put("monster03", new Monster(300, "牛魔王~", "芭蕉扇"));
        monster_map.put("monster04", new Monster(400, "狐狸精~", "美人计"));
    }

    public Monster getMonster(String key) {
        return monster_map.get(key);
    }
}
