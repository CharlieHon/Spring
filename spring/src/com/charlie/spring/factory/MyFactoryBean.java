package com.charlie.spring.factory;

import com.charlie.spring.bean.Monster;
import org.springframework.beans.factory.FactoryBean;

import java.util.HashMap;
import java.util.Map;

public class MyFactoryBean implements FactoryBean<Monster> {

    // 这个keu就是配置时候,指定要获取的对应key
    private String key;
    private Map<String, Monster> monster_map;

    {   // 通过代码块进行初始化
        monster_map = new HashMap<>();
        monster_map.put("monster01", new Monster(300, "黄风怪", "风暴"));
        monster_map.put("monster02", new Monster(400, "奔波儿灞", "把唐僧师徒除掉~"));
    }

    public void setKey(String key) {
        this.key = key;
    }

    @Override
    public Monster getObject() throws Exception {
        return monster_map.get(key);
    }

    @Override
    public Class<?> getObjectType() {
        return Monster.class;
    }

    @Override
    public boolean isSingleton() {  // 这里指定是否返回是单例
        return true;
    }
}
