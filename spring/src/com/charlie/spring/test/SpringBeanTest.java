package com.charlie.spring.test;

import com.charlie.spring.bean.Monster;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.File;

public class SpringBeanTest {

    @Test
    public void getMonster() {
        // 1. 创建容器 ApplicationContext
        // 2. 该容器和一个配置文件关联
        ApplicationContext ioc = new ClassPathXmlApplicationContext("beans.xml");
        // 3. 可以通过getBean获取对应的对象，默认返回的是Object，但是运行类型是Monster
        Monster monster01 = (Monster) ioc.getBean("monster01"); // 传入id
        // 4. 输出
        System.out.println("monster01=" + monster01 + " 运行类型=" + monster01.getClass());
        System.out.println("monster01=" + monster01 + " 属性name=" + monster01.getName());
        // 5. 也可以在获取的时候，直接指定Class类型
        Monster monster011 = ioc.getBean("monster01", Monster.class);   // 传入id，类类型
        System.out.println("monster011=" + monster011);
        // 6. 查看容器注入了哪些bean对象，输出bean的id
        String[] str = ioc.getBeanDefinitionNames();
        for (String string : str) {
            System.out.println("names=" + string);  // names=monster01
        }
    }

    @Test   // 验证类加载路径
    public void ClassPath() {
        File file = new File(this.getClass().getResource("/").getPath());
        System.out.println("file=" + file);     // file=E:\Spring\spring\out\production\spring
    }
}
