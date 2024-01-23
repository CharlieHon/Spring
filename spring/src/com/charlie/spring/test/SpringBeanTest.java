package com.charlie.spring.test;

import com.charlie.spring.bean.Car;
import com.charlie.spring.bean.Monster;
import com.charlie.spring.service.MemberServiceImpl;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.File;

public class SpringBeanTest {

    @Test
    public void setBeanByProperty() {    // 通过设置内部bean设置属性
        ApplicationContext ioc = new ClassPathXmlApplicationContext("beans.xml");
        MemberServiceImpl memberService2 = ioc.getBean("memberService2", MemberServiceImpl.class);
        memberService2.add();
    }

    @Test
    public void setBeanByRef() {    // 通过ref来设置bean属性
        ApplicationContext ioc = new ClassPathXmlApplicationContext("beans.xml");
        MemberServiceImpl memberService = ioc.getBean("memberService", MemberServiceImpl.class);
        memberService.add();
        /*
        MemberDAOImpl 构造器...
        MemberServiceImpl 构造器...    // 前两行是ioc容器根据配置文件先创建的对象

        MemberServiceImpl 构造器...    // 后两行是ioc.getBean()后根据配置依赖创建的对象
        MemberDAOImpl 构造器...

        MemberServiceImpl add() 方法...   // 调用方法
        MemberDAOImpl add() 方法...
         */
    }

    @Test
    public void getBeanByP() {  // 通过 p名称空间 来配置bean
        ApplicationContext ioc = new ClassPathXmlApplicationContext("beans.xml");
        Monster monster04 = ioc.getBean("monster04", Monster.class);
        System.out.println("monster04=" + monster04);
    }

    @Test
    public void setBeanByConstructor() {    // 通过构造器来设置属性
        ApplicationContext ioc = new ClassPathXmlApplicationContext("beans.xml");
        Monster monster03 = ioc.getBean("monster03", Monster.class);    // 会调用全参构造器
        System.out.println("monster03=0" + monster03);
    }

    @Test
    public void getBeanByType() {   // 按类型来获取bean
        ApplicationContext ioc = new ClassPathXmlApplicationContext("beans.xml");
        // 通过类型来获取bean，要求ioc容器中的同一个类的bean只有有一个，否则会抛出异常 NoUniqueBeanDefinitionException
        Car bean = ioc.getBean(Car.class);
        System.out.println("bean=" + bean);
    }

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
        for (String string : str) {     // 如果不设置id，系统会默认分配id(全类名#0)，如 com.charlie.spring.bean.Monster#0
            System.out.println("names=" + string);  // names=monster01
        }
    }

    @Test
    public void getCar() {
        ApplicationContext ioc = new ClassPathXmlApplicationContext("beans.xml");
        Car car01 = ioc.getBean("car01", Car.class);
        System.out.println("car01=" + car01);
    }

    @Test   // 验证类加载路径
    public void ClassPath() {
        File file = new File(this.getClass().getResource("/").getPath());
        System.out.println("file=" + file);     // file=E:\Spring\spring\out\production\spring
    }
}
