# Spring

## 基础介绍

- ![一图胜千言](img.png)

1. `Spring`核心学习内容：IOC、AOP、JDBCTemplate，声明式事务
2. IOC：控制反转，可以管理Java对象
3. AOP：切面编程
4. JDBCTemplate：是Spring提供的一套访问数据库的技术，应用性强，相对好理解
5. 声明式事务：基于IOC/AOP实现事务管理

> 1. `Spring`可以整合其它的框架，即是管理框架的框架
> 2. Spring的两个核心概念：IOC和AOP
> 3. `IOC(Inversion of Control, 控制反转)`，IOC与传统开发模式的对比如下表
> 4. `DI(Dependency Injection, 依赖注入)`，可以理解成是IOC的另外叫法
> 5. `Spring`最大的价值，在于**通过配置给程序提供需要使用的web层[Servlet(Action/Controller)]/Service/DAO/JavaBean对象**，
>   这个是核心价值所在，也是IOC的具体体现——实现**解耦**。

| 传统的开发模式              | IOC的开发模式              |
|----------------------|-----------------------|
| ![传统开发模式](img_1.png) | ![IOC开发模式](img_2.png) |
| 程序读取环境配置，然后自己创建对象    | 容器创建号对象，程序直接使用        |

## 快速入门

> 需求分析：通过Spring的方式(配置文件)，获取JavaBean:Monster对象，并给该对象属性赋值，输出该对象信息

- ![lib](img_3.png)
- ![img_4.png](img_4.png)
- ![img_5.png](img_5.png)

```java
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
        // 类加载路径在out目录下，src路径下的文件与其下的spring目录下文件的相对位置是一致的，所以可以直接使用 "beans.xml" 来获取配置文件
        System.out.println("file=" + file);     // file=E:\Spring\spring\out\production\spring
    }
}
```

- ![img_6.png](img_6.png)
- ![img_7.png](img_7.png)
- ![img_8.png](img_8.png)
- ![img_9.png](img_9.png)
- ![img_10.png](img_10.png)
- ![img_11.png](img_11.png)

