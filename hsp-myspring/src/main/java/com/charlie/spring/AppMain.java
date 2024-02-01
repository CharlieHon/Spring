package com.charlie.spring;

import com.charlie.spring.component.MonsterDAO;
import com.charlie.spring.component.MonsterService;
import com.charlie.spring.ioc.MySpringApplicationContext;
import com.charlie.spring.ioc.MySpringConfig;
import org.junit.Test;

public class AppMain {
    @Test
    public void test1() {
        MySpringApplicationContext ioc = new MySpringApplicationContext(MySpringConfig.class);
        //MonsterDAO monsterDAO = (MonsterDAO) ioc.getBean("monsterDAO");
        //MonsterDAO monsterDAO2 = (MonsterDAO) ioc.getBean("monsterDAO");
        //System.out.println("monsterDAO=" + monsterDAO);
        //System.out.println("monsterDAO2=" + monsterDAO2);

        // 测试一下依赖注入的功能
        MonsterService monsterService = (MonsterService) ioc.getBean("monsterService");
        monsterService.m1();
        System.out.println("OK!");
    }
}
