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

### 快速入门

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

### 手动开发-简单的Spring基于XML配置的程序

- ![思路分析](img_12.png)

```java
package com.charlie.spring.applicationcontext;

import com.charlie.spring.bean.Monster;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.File;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 1. 这个程序用于实现Spring的一个简单容器机制
 * 2. 后面会详细实现
 * 3. 这里实现如何将beans.xml文件进行解析，并生成对象，放入容器中
 * 4. 提供一个方法 getBeans(id) 返回对应的对象
 */
public class CharlieApplicationContext {
    private ConcurrentHashMap<String, Object> singletonObjects = new ConcurrentHashMap<>();

    // 构造器：接收一个容器的构造文件，如beans.xml，该文件默认在src下
    public CharlieApplicationContext(String iocBeanXmlFile) throws Exception {
        // 1. 得到类加载路径
        String path = this.getClass().getResource("/").getPath();
        //System.out.println("path=" + path);     // /E:/Spring/spring/out/production/spring/
        // 2. 创建SAXReader
        SAXReader reader = new SAXReader();
        // 3. 得到Document对象
        Document document = reader.read(new File(path + iocBeanXmlFile));
        // 4. 得到rootDocument
        Element rootElement = document.getRootElement();
        // 5. 得到第一个bean-monster01
        Element bean = (Element) rootElement.elements("bean").get(0);
        // 6. 获取到第一个bean-monster01的相关属性
        String id = bean.attributeValue("id");      // monster01
        String classFullPath = bean.attributeValue("class");    // com.charlie.spring.bean.Monster
        // 7. 获取bean下属性 property 值
        List<Element> property = bean.elements("property");
        //for (Element element : property) {
        //    // monsterId: 100;  name: 牛魔王 ...
        //    System.out.println(element.attributeValue("name") + ": " + element.attributeValue("value"));
        //}
        Integer monsterId = Integer.parseInt(property.get(0).attributeValue("value"));
        String name = property.get(1).attributeValue("value");
        String skill = property.get(2).attributeValue("value");
        // 8. 使用反射创建对象
        Class<?> aClass = Class.forName(classFullPath);
        Monster o = (Monster) aClass.newInstance();
        // 给o对象赋值，这里简化为直接赋值
        o.setMonsterId(monsterId);
        o.setName(name);
        o.setSkill(skill);
        // 9. 将创建好的对象放入到singletonObjects中
        singletonObjects.put(id, o);
    }

    public Object getBean(String id) {
        return singletonObjects.get(id);
    }
}
```

### 课后作业

- ![Spring原生容器底层结构](img_13.png)

> 问题1：如果不给bean设置id，不会报错，系统会默认分配id，分配的id的规则是：全类名#0、全类名#1的规则
> - 可以通过 `ioc.getBeanDefinitionNames()` 获取到配置/分配的id值
> 
> 问题2：创建一个Car类，通过ioc容器获取bean对象
> - 略

## Spring管理Bean-IOC

1. Bean管理包括两个方面
   - 创建bean对象
   - 给bean注入属性
2. Bean配置方式
   - 基于XML文件配置方式
   - 基于注解方式

### 基于XML配置bean

- 通过类型获取bean
- ![通过类型获取bean](img_14.png)

```java
package com.charlie.spring.test;

public class SpringBeanTest {

    @Test
    public void getBeanByType() {   // 按类型来获取bean
        ApplicationContext ioc = new ClassPathXmlApplicationContext("beans.xml");
        // 通过类型来获取bean，要求ioc容器中的同一个类的bean只有有一个，否则会抛出异常 NoUniqueBeanDefinitionException
        Car bean = ioc.getBean(Car.class);
        System.out.println("bean=" + bean);
    }
}
```

- 通过构造器配置bean
- ![img_15.png](img_15.png)

```xml
 <!--配置Monster对象，并且指定构造器
 1. constructor-arg 标签可以指定使用构造器的参数
 2. index 表示构造器的第几个参数，从0开始计数
 3. 除了可以通过index，还可以通过name/type来指定参数
 4. 类的构造器，不会有完全相同类型和顺序的构造器，所以可以通过tyep来指定
 -->
 <bean class="com.charlie.spring.bean.Monster" id="monster03">
     <constructor-arg value="200" index="0"/>                    <!--索引-->
     <constructor-arg value="白骨精" name="name"/>                <!--字段名-->
     <constructor-arg value="吸血" type="java.lang.String"/>      <!--类型-->
 </bean>
```

- 通过p名称空间配置bean
- ![通过p名称空间配置bean](img_16.png)

```xml
 <!--通过 p名称空间 来配置bean
 1. 需要添加xmlns，xmlns:p="http://www.springframework.org/schema/p"
 -->
 <bean class="com.charlie.spring.bean.Monster" id="monster04"
       p:monsterId="500"
       p:name="红孩儿"
       p:skill="三味真火"
 />
```

- 引入/注入其它bean对象
- 在Spring的ioc容器，可以通过 `ref` 来实现bean对象的**相互引用**
- ![img_17.png](img_17.png)

```xml
 <!--配置MemberDAOImpl对象-->
 <bean class="com.charlie.spring.dao.MemberDAOImpl" id="memberDAO"/>
 <!--配置MemberServiceImpl对象
 1. ref="memberDAO" 表示 MemberServiceImpl对象属性memberDAO引用的对象的id=memberDAO的对象
 2. 这里体现出Spring容器的依赖注入
 3.注意在spring容器中，是作为一个整体来执行的，即如果引用到的一个bean对象，对配置的顺序没有要求，
     上面memberDAO配置也可以在memberService的配置下面
 4. 建议按顺序配置，在阅读时候比较方便
 -->
 <bean class="com.charlie.spring.service.MemberServiceImpl" id="memberService">
     <property name="memberDAO" ref="memberDAO"/>
 </bean>
```

- 引用/注入内部bean对象
- ![img_18.png](img_18.png)
- 引入/注入集合/数组类型
- ![Master.java](img_19.png)

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:p="http://www.springframework.org/schema/p"
       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd">
    <!--
    1. 配置monster对象/javabean
    2. 在beans中可以配置多个bean
    3. bean表示就是一个java对象
    4. class属性用于指定类的全路径->spring底层使用反射创建
    5. id属性表示该java对象在spring容器中的id，通过id可以获取到该对象
    6. <property name="" value=""> 用于该对象的属性赋值，没有给就是默认值
    -->
    <bean class="com.charlie.spring.bean.Monster" id="monster01">
        <property name="monsterId" value="100"/>
        <property name="name" value="牛魔王"/>
        <property name="skill" value="芭蕉扇"/>
    </bean>
    <bean class="com.charlie.spring.bean.Monster" id="monster02">
        <property name="monsterId" value="200"/>
        <property name="name" value="孙悟空"/>
        <property name="skill" value="七十二变"/>
    </bean>

    <bean class="com.charlie.spring.bean.Car" id="car01">
        <!--解读
        1. 当给某个bean对象设置属性的时候
        2. 底层是使用对应的setter方法完成的，比如setName()
        3. 如果没有这个方法，就会报错
        -->
        <property name="carId" value="100"/>
        <property name="name" value="五菱宏光"/>
        <property name="price" value="8.5"/>
    </bean>

    <!--配置Monster对象，并且指定构造器
    1. constructor-arg 标签可以指定使用构造器的参数
    2. index 表示构造器的第几个参数，从0开始计数
    3. 除了可以通过index，还可以通过name/type来指定参数
    4. 类的构造器，不会有完全相同类型和顺序的构造器，所以可以通过tyep来指定
    -->
    <bean class="com.charlie.spring.bean.Monster" id="monster03">
        <constructor-arg value="200" index="0"/>                    <!--索引-->
        <constructor-arg value="白骨精" name="name"/>                <!--字段名-->
        <constructor-arg value="吸血" type="java.lang.String"/>      <!--类型-->
    </bean>

    <!--通过 p名称空间 来配置bean
    1. 需要添加xmlns，xmlns:p="http://www.springframework.org/schema/p"
    -->
    <bean class="com.charlie.spring.bean.Monster" id="monster04"
          p:monsterId="500"
          p:name="红孩儿"
          p:skill="三味真火"
    />

    <!--配置MemberDAOImpl对象-->
    <bean class="com.charlie.spring.dao.MemberDAOImpl" id="memberDAO"/>
    <!--配置MemberServiceImpl对象
    1. ref="memberDAO" 表示 MemberServiceImpl对象属性memberDAO引用的对象的id=memberDAO的对象
    2. 这里体现出Spring容器的依赖注入
    3.注意在spring容器中，是作为一个整体来执行的，即如果引用到的一个bean对象，对配置的顺序没有要求，
        上面memberDAO配置也可以在memberService的配置下面
    4. 建议按顺序配置，在阅读时候比较方便
    -->
    <bean class="com.charlie.spring.service.MemberServiceImpl" id="memberService">
        <property name="memberDAO" ref="memberDAO"/>
    </bean>

    <!--配置MemberServiceImpl对象-使用内部bean-->
    <bean class="com.charlie.spring.service.MemberServiceImpl" id="memberService2">
        <!--自己配置一个内部bean-->
        <property name="memberDAO">
            <bean class="com.charlie.spring.dao.MemberDAOImpl"/>
        </property>
    </bean>

    <!--配置Mater对象-->
    <bean class="com.charlie.spring.bean.Master" id="master">
        <property name="name" value="太上老君"/>
        <!--给list属性赋值-->
        <property name="monsterList">
            <list>
                <!--引用-->
                <ref bean="monster01"/>
                <ref bean="monster02"/>
                <!--内部bean，可以不设置id-->
                <bean class="com.charlie.spring.bean.Monster">
                    <property name="name" value="老鼠精"/>
                    <property name="monsterId" value="100"/>
                    <property name="skill" value="吃粮食"/>
                </bean>
            </list>
        </property>
        <!--给map属性赋值-->
        <property name="monsterMap">
            <map>
                <entry>
                    <key>
                        <value>monster03</value>
                    </key>
                    <!--这里使用的是外部bean引用-->
                    <ref bean="monster03"/>
                </entry>
                <entry>
                    <key>
                        <value>monster04</value>
                    </key>
                    <!--内部bean-->
                    <bean class="com.charlie.spring.bean.Monster">
                        <property name="name" value="青牛怪"/>
                        <property name="monsterId" value="500"/>
                        <property name="skill" value="乾坤圈"/>
                    </bean>
                </entry>
            </map>
        </property>
        <!--给set属性赋值-->
        <property name="monsterSet">
            <set>
                <ref bean="monster04"/>
                <ref bean="monster01"/>
                <bean class="com.charlie.spring.bean.Monster">
                    <property name="name" value="金角大王"/>
                    <property name="monsterId" value="600"/>
                    <property name="skill" value="玉净瓶"/>
                </bean>
            </set>
        </property>
        <!--给数组属性赋值
        array标签中使用value还是bean,ref 要根据业务决定
        -->
        <property name="monsterName">
            <array>
                <value>小旋风</value>
                <value>奔波儿灞</value>
                <value>九头虫</value>
            </array>
        </property>
        <!--给properties属性赋值:结构K(String)-V(String)-->
        <property name="pros">
            <props>
                <prop key="username">root</prop>
                <prop key="password">123456</prop>
                <prop key="ip">127.0.0.1</prop>
            </props>
        </property>
    </bean>
</beans>
```

- 通过`utillist`进行配置

```xml
 <!--定义一个util:list,并且指定id,可以达到数据复用-->
 <util:list id="myBookList">
     <value>三国演义</value>
     <value>红楼梦</value>
     <value>西游记</value>
     <value>水浒传</value>
 </util:list>
 <!--配置BookStore对象-->
 <bean class="com.charlie.spring.bean.BookStore" id="bookStore">
     <property name="bookList" ref="myBookList"/>
 </bean>
```

- 级联属性赋值
- `Spring`的ioc容器,可以直接给对象属性的属性赋值,即级联属性赋值

```xml
<!--属性级联赋值配置-->
<bean class="com.charlie.spring.bean.Dept" id="dept"/>
<bean class="com.charlie.spring.bean.Emp" id="emp">
  <property name="name" value="jack"/>
  <property name="dept" ref="dept"/>
  <!--这里希望给dept的name属性指定值,级联属性赋值
  dept.name底层是通过Dept的setName方法进行赋值的
  -->
  <property name="dept.name" value="Java开发部门"/>
</bean>
```

#### 通过静态工厂获取对象

```java
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
```

```xml
<!--配置monster对象,通过静态工厂获取
1. 通过静态工厂获取/配置bean
2. class是静态工厂类的全路径
3. factory-method 表示指定静态工厂类的哪个方法返回对象
4. constructor-arg value 指定要返回静态工厂的哪个对象,即factory-method的参数
-->
<bean id="my_monster01" class="com.charlie.spring.factory.MyStaticFactory" factory-method="getMonster">
  <!--指定factory-method的参数-->
  <constructor-arg value="monster02"/>
</bean>
```

#### 通过实例工厂获取对象

```java
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
```

```xml
<bean class="com.charlie.spring.factory.MyInstanceFactory" id="myInstanceFactory01"/>
<bean class="com.charlie.spring.factory.MyInstanceFactory" id="myInstanceFactory02"/>
<!--配置monster对象,通过实例工厂
1. 需要先配置一个实例工厂对象
2. factory-bean 指定使用哪个实例工厂对象返回bean
3. factory-method 指定使用实例工厂的哪个方法返回bean
4. constructor-arg 指定要获取到实例工厂中的哪个monster
-->
<bean id="my_monster02" factory-bean="myInstanceFactory01" factory-method="getMonster">
  <constructor-arg value="monster03"/>
</bean>
<bean id="my_monster04" factory-bean="myInstanceFactory02" factory-method="getMonster">
  <constructor-arg value="monster03"/>
```

#### 通过FactoryBean获取Bean

- 在Spring的ioc容器,通过 `FactoryBean`获取Bean对象

```xml
<!--通过FactoryBean配置monster对象
1. 这里的class指定要使用的FactoryBean
2. key表示就是FactoryBean属性key
3. value就是要获取要获取的对象对应key
-->
<bean id="my_monster05" class="com.charlie.spring.factory.MyFactoryBean">
  <property name="key" value="monster02"/>
</bean>
```

```java
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
```

#### Bean配置信息重用

- 在Spring的ioc容器，提供了一种继承的方式来实现bean配置信息的重用

```xml
<!--配置Monster对象-->
<bean id="monster10" class="com.charlie.spring.bean.Monster">
  <property name="name" value="蜈蚣精"/>
  <property name="monsterId" value="10"/>
  <property name="skill" value="蜇人~"/>
</bean>
<!--
1. 再配置一个Monster对象，属性和 monster10 对象一样
2. parent="monster10" 指定当前这个配置的对象的属性从 id="monster10"的对象来
-->
<bean id="monster11" class="com.charlie.spring.bean.Monster" parent="monster10"/>

<!--
1. 如果bean指定属性abstract=true，表示该bean对象，是用于被继承
2. 这个bean本身就不能被获取/实例化
-->
<bean id="monster12" class="com.charlie.spring.bean.Monster" abstract="true">
  <property name="name" value="黑熊精"/>
  <property name="monsterId" value="12"/>
  <property name="skill" value="偷袈裟"/>
</bean>
<bean id="monster13" class="com.charlie.spring.bean.Monster" parent="monster12"/>
```

#### bean创建顺序

1. 在spring的ioc容器，默认是按照配置的顺序创建bean对象
   - ![img_20.png](img_20.png)
2. 如果设置 `depends-on` 属性，先创建依赖
   - ![img_21.png](img_21.png)
3. 当某个bean的内部属性为另一个bean时，此时如果不配置`depends-on`，仍然会按照顺序创建bean，
   当创建内部属性的bean对象后，再调用该bean的setXXX属性方法设置属性值
   - ![img_22.png](img_22.png)
   - ![img_23.png](img_23.png)

#### Bean的单例和多实例

- 在Spring的ioc容器，默认是按照单例创建的，即配置一个bean对象后，ioc容器只会创建一个bean实例。
   如果希望ioc容器配置的某个bean对象，是以多个实例实行创建的则可以通过配置 `scope="prototype"` 来指定

```xml
<!--配置Cat对象
1. 在默认情况下，scope属性是单例(singleton)
2. 在ioc容器中，只会有一个bean对象
3. 当执行getBean时，返回的是同一个对象
4. 如果希望每次getBean返回一个新的Bean对象，则可以scope="prototype"
-->
<bean class="com.charlie.spring.bean.Cat" id="cat" scope="prototype">
  <property name="name" value="肥肥"/>
  <property name="id" value="100"/>
</bean>
```

> 使用细节
> 1. 默认是单例singleton，在启动容器时，默认就会创建，并放入到 `singletonObjects` 集合
> 2. 当 `<bean scope="prototye">` 设置为多实例机制后，该bean是在getBean时才创建
> 3. 如果是单例，同时希望在getBean时才创建，可以指定懒加载 `lazy-init="true"`(默认为false)
> 4. 通常情况下，`lazy-init`就使用默认值false，在开发看来，用空间换时间是值得的
> 5. 如果 `scope="prototye"` 这时不论 `lazy-init`属性的值是true还是false，都是在getBean时候，才创建对象

#### Bean生命周期

```java
package com.charlie.spring.bean;

public class House {
    private String name;

    public House() {
        System.out.println("House() 构造器...");
    }

    // 1. 这个方法是由程序员编写
    // 2. 根据自己的业务逻辑来写
    public void init() {
        System.out.println("House init()...");
    }

    // 1. 这个方法是由程序员编写
    // 2. 根据自己的业务逻辑来写
    // 3. 方法名也不是固定的
    public void destroy() {
        System.out.println("House destroy()...");
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        System.out.println("setName=" + name);
        this.name = name;
    }
}
```

- 说明：bean对象创建是由JVM完成的，然后执行如下方法
- ![img_24.png](img_24.png)
1. 执行构造器
2. 执行setXXX相关方法
3. 调用bean的初始化方法(需要配置)
4. 使用bean
5. 当容器关闭时候，调用bean的销毁方法(需要配置)

#### 配置Bean后置处理器

1. 在spring的ioc容器，可以配置bean的后置处理器
2. 该处理器/对象会在**bean初始化方法**调用前和初始化方法调用后被调用
3. 程序员可以在后置处理器中编写自己的代码

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd">

    <!--配置House对象-->
    <bean class="com.charlie.spring.bean.House" id="house"
          init-method="init"
          destroy-method="destroy">
        <property name="name" value="美国大House"/>
    </bean>

    <!--配置House对象-->
    <bean class="com.charlie.spring.bean.House" id="house02"
          init-method="init"
          destroy-method="destroy">
        <property name="name" value="苏州园林"/>
    </bean>

    <!--配置后置处理器对象
    1. 当我们在beans02.xml 容器配置文件配置了 MyBeanPostProcessor
    2. 这是后置处理器，就会作用在该容器创建的所有对象
    3. 已经是针对所有对象编程->切面编程AOP
    -->
    <bean class="com.charlie.spring.bean.MyBeanPostProcessor" id="beanPostProcessor"/>
</beans>
```

> 其它说明
> 1. 怎么执行到这个方法？=>使用AOP(反射+动态代理+IO+容器+注解)
> 2. 有什么用？
>    - 可以对IOC容器中所有的对象进行统一处理，比如日志处理/权限校验/安全验证，事务管理
> 3. 针对容器的所有对象吗？是的=>切面编程特点
> 4. 底层机制=>后面实现

```java
package com.charlie.spring.bean;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

// 这是一个后置处理器
public class MyBeanPostProcessor implements BeanPostProcessor {

    /**
     * 在bean的init方法前被调用
     * @param bean 传入的在ioc容器中创建/配置的bean
     * @param beanName bean的id
     * @return Object是程序员对传入的bean进行修改/处理后(如果需要)，再返回的
     */
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        System.out.println("postProcessBeforeInitialization()... bean=" + bean + ", beanName=" + beanName);
        return bean;
    }

    /**
     * 在bean的init方法后被调用
     * @param bean 传入的在ioc容器中创建/配置的bean
     * @param beanName bean的id
     * @return Object是程序员对传入的bean进行修改/处理后(如果需要)，再返回的
     */
    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        //System.out.println("postProcessAfterInitialization()... bean=" + bean + ", beanName=" + beanName);

        // 对多个对象进行处理/编程==>切面编程
        if (bean instanceof House) {
            ((House) bean).setName("皇家园林");
        }
        return bean;
    }
}
```

#### 通过属性文件给bean注入值

```properties
monsterId=1000
# 中文需要使用工具转为unicode编码
name=\u674e\u81ea\u6210
skill=\u95ef\u738b
```

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans>
    <!--指定属性文件
    1. 先把文件设修改成提示 All Problem
    2. 提示错误，将光标放在context 输入alt+enter就会自动引入namespace
    3. location="classpath:my.properties" 表示指定属性文件的位置
    4. 提示：需要带上 classpath
    5. 属性文件有中文，需要转为Unicode编码->使用工具
    -->
    <context:property-placeholder location="classpath:my.properties"/>
    <!--配置monster对象
    1. 通过属性文件给monster对象的属性赋值
    2. 这时属性值通过 ${属性名}
    3. 这里的属性名就是 my.properties 文件中的 k-v 的k
    -->
    <bean class="com.charlie.spring.bean.Monster" id="monster1000">
        <property name="monsterId" value="${monsterId}"/>
        <property name="name" value="${name}"/>
        <property name="skill" value="${skill}"/>
    </bean>
</beans>
```

#### bean的自动装配

```xml
<!--配置OrderDAO-->
<bean class="com.charlie.spring.dao.OrderDAO" id="orderDAO"/>
<!--配置OrderService——自动装配
1. autowire="byType"表示在创建orderService时，通过类型的方式给对象属性 自动完成赋值/引用
2. 比如OrderService对象有属性 private OrderDAO orderDAO;
3. 就会在容器中去找有没有OrderDAO类型对象
4. 如果有，就会自动地装配，提示：如果是按照 byType 方式来装配，这个容器中，不能有两个及以上的该类型对象
5. 如果对象没有属性，则 autowire属性就没有必要设置
-->
<bean class="com.charlie.spring.service.OrderService" id="orderService"
    autowire="byType"/>
<!--配置OrderAction-->
<bean class="com.charlie.spring.web.OrderAction" id="orderAction"
    autowire="byType"/>
```

```xml
<!--配置OrderDAO-->
<bean class="com.charlie.spring.dao.OrderDAO" id="orderDAO"/>
<!--配置OrderService——自动装配
1. autowire="byType"表示在创建orderService时，通过类型的方式给对象属性 自动完成赋值/引用
2. 比如OrderService对象有属性 private OrderDAO orderDAO;
3. 就会在容器中去找有没有OrderDAO类型对象
4. 如果有，就会自动地装配，提示：如果是按照 byType 方式来装配，这个容器中，不能有两个及以上的该类型对象
5. 如果对象没有属性，则 autowire属性就没有必要设置
7. 如果设置的是 autowire="byName"，表示通过名字完成自动装配
8. 比如下面的配置 class="com.charlie.spring.service.OrderService" id="orderService" autowire="byName"
  1) 先看 OrderService 属性 private OrderDAO orderDAO;
  2) 再根据这个属性的 setXxx() 方法的 Xxx 来找对象id
  3) public void setOrderDAO() 就会找id=orderDAO对象来进行自动装配
-->
<bean class="com.charlie.spring.service.OrderService" id="orderService"
    autowire="byName"/>
<!--配置OrderAction-->
<bean class="com.charlie.spring.web.OrderAction" id="orderAction"
    autowire="byName"/>
```

#### Spring EI表达式配置




