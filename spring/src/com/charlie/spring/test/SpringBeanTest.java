package com.charlie.spring.test;

import com.charlie.spring.bean.*;
import com.charlie.spring.component.MyComponent;
import com.charlie.spring.component.UserAction;
import com.charlie.spring.component.UserDAO;
import com.charlie.spring.component.UserService;
//import com.charlie.spring.component.t.Pig;
import com.charlie.spring.service.MemberServiceImpl;
import com.charlie.spring.web.OrderAction;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.File;

public class SpringBeanTest {

    // 通过注解配置bean
    @Test
    public void setBeanByAnnotation() {
        ApplicationContext ioc = new ClassPathXmlApplicationContext("beans05.xml");
        // 在默认情况下，注解表示的类创建对象后，在容器中的id为类名的首字母小写
        UserDAO userDAO = ioc.getBean(UserDAO.class);
        UserService userService = ioc.getBean(UserService.class);
        UserAction userAction = ioc.getBean(UserAction.class);
        MyComponent myComponent = ioc.getBean(MyComponent.class);
        //Pig pig = ioc.getBean(Pig.class);   // component子包下的bean注解，也会被上如
        System.out.println(userDAO);
        //System.out.println("Pig=" + pig);
        System.out.println("OK!");
    }

    @Test
    public void setBeanBySpel() {
        ApplicationContext ioc = new ClassPathXmlApplicationContext("beans04.xml");
        SpELBean spELBean = ioc.getBean("spELBean", SpELBean.class);
        System.out.println("spELBean=" + spELBean);
        /*
        spELBean=SpELBean{name='新西方教育', monster=Monster{monsterId=100, name='黄风怪', skill='沙尘暴'},
        monsterName='黄风怪', crySound='发出喵喵喵声音', bookName='《天龙八部》', result=111.8}
         */
    }

    // 通过自动装配来设置属性
    @Test
    public void setBeanByAutowire() {
        ApplicationContext ioc = new ClassPathXmlApplicationContext("beans03.xml");
        OrderAction orderAction = ioc.getBean("orderAction", OrderAction.class);
        // 验证是否自动装配上
        System.out.println(orderAction.getOrderService());
        System.out.println(orderAction.getOrderService().getOrderDAO());
    }

    // 通过属性文件给bean属性赋值
    @Test
    public void setBeanByFile() {
        ApplicationContext ioc = new ClassPathXmlApplicationContext("beans03.xml");
        Monster bean = ioc.getBean("monster1000", Monster.class);
        System.out.println("bean=" + bean);
    }

    // 后置处理器
    @Test
    public void beanPostProcessor() {
        ApplicationContext ioc = new ClassPathXmlApplicationContext("beans02.xml");
        House house = ioc.getBean("house", House.class);
        System.out.println("使用house=" + house);
        ((ConfigurableApplicationContext) ioc).close();
    }

    // 测试Bean声明周期
    @Test
    public void testBeanLife() {
        ApplicationContext ioc = new ClassPathXmlApplicationContext("beans.xml");
        // 关闭容器
        ((ConfigurableApplicationContext) ioc).close();     // House destroy()...
        System.out.println("OK~");
    }

    // 测试Scope
    @Test
    public void testBeanScope() {
        ApplicationContext ioc = new ClassPathXmlApplicationContext("beans.xml");
        Cat cat = ioc.getBean("cat", Cat.class);
        Cat cat1 = ioc.getBean("cat", Cat.class);
        Cat cat2 = ioc.getBean("cat", Cat.class);
        System.out.println("cat=" + cat);
        System.out.println("cat1=" + cat1);
        System.out.println("cat2=" + cat2);
        System.out.println("OK~");
    }

    @Test
    public void testBeanByCreate() {
        ApplicationContext ioc = new ClassPathXmlApplicationContext("beans.xml");
        System.out.println("OK~");
    }

    // 通过继承来配置Bean
    @Test
    public void getBeanByExtends() {
        ApplicationContext ioc = new ClassPathXmlApplicationContext("beans.xml");
        Monster monster11 = ioc.getBean("monster11", Monster.class);
        System.out.println("monster11=" + monster11);   // Monster{monsterId=10, name='蜈蚣精', skill='蜇人~'}

        // 从 abstract=true 的bean继承的
        Monster monster13 = ioc.getBean("monster13", Monster.class);
        System.out.println("monster13=" + monster13);
    }

    // 通过FactoryBean获取Bean
    @Test
    public void getBeanByFactoryBean() {
        ApplicationContext ioc = new ClassPathXmlApplicationContext("beans.xml");
        Monster my_monster05 = ioc.getBean("my_monster05", Monster.class);
        System.out.println("my_monster05=" + my_monster05); // Monster{monsterId=400, name='奔波儿灞', skill='把唐僧师徒除掉~'}
    }

    // 通过静态工厂类获取bean
    @Test
    public void getBeanByInstanceFactory() {
        ApplicationContext ioc = new ClassPathXmlApplicationContext("beans.xml");
        Monster my_monster02 = ioc.getBean("my_monster02", Monster.class);
        Monster my_monster04 = ioc.getBean("my_monster04", Monster.class);
        System.out.println(my_monster02 == my_monster04);       // false 不是一个实例工厂产生的bean对象
        System.out.println("my_monster02=" + my_monster02);     // Monster{monsterId=300, name='牛魔王~', skill='芭蕉扇'}
    }

    // 通过静态工厂类获取bean
    @Test
    public void getBeanByStaticFactory() {
        ApplicationContext ioc = new ClassPathXmlApplicationContext("beans.xml");
        Monster my_monster01 = ioc.getBean("my_monster01", Monster.class);
        Monster my_monster03 = ioc.getBean("my_monster03", Monster.class);
        System.out.println(my_monster01 == my_monster03);       // true 由同一个静态工厂创建bean对象
        System.out.println("my_monster01=" + my_monster01);     // Monster{monsterId=200, name='狐狸精', skill='美人计'}
    }

    // 给属性进行级联赋值
    @Test
    public void setProByRelation() {
        ApplicationContext ioc = new ClassPathXmlApplicationContext("beans.xml");
        Emp emp = ioc.getBean("emp", Emp.class);
        System.out.println("emp=" + emp);   // emp=Emp{name='jack', dept=Dept{name='Java开发部门'}}
    }

    // 使用util:list名称空间给属性赋值
    @Test
    public void setBeanByUtil() {
        ApplicationContext ioc = new ClassPathXmlApplicationContext("beans.xml");
        BookStore bookStore = ioc.getBean("bookStore", BookStore.class);
        System.out.println("bookStore=" + bookStore);
    }

    // 给集合数组属性进行赋值
    @Test
    public void setBeanByCollection() {
        ApplicationContext ioc = new ClassPathXmlApplicationContext("beans.xml");
        Master master = ioc.getBean("master", Master.class);
        System.out.println("master=" + master);
    }

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
