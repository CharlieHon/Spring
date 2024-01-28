package com.charlie.spring.component;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;

// @Controller 标识该类是一个控制器
@Controller
public class UserAction {
    // xml 配置 ref
    // 1) 在IOC容器中查找待装配的组件的类型，如果有唯一的bean匹配(按类型来)，则使用该bean装配
    // 2) 如待装配的类型对应的bean在IOC容器中有多个，则使用待装配的属性的名字(userService)作为id再进行查找
    //      找到就装配，找不到就抛出异常
    //@Autowired

    // 1) @Resource 有两个属性是比较重要的，分别是name和type，Spring将@Resource注解的name属性解析为bean的名字
    //      而type属性则解析为bean的类型，如果使用name属性，则使用byName的自动注入策略，
    //      而使用type属性时则使用byType自动注入策略，这时要求只能有一个该类型的
    // 2) 如果@Resource没有指定name和type，则先使用byName注入策略，如果匹配不到，再使用byType策略，如果都不成功，就会报错
    //@Resource

    //@Autowired
    //@Qualifier(value = "userService200")
    // 通过 @Autowired + @Qualifier(value = "userService200") 可以指定装配属性的id

    @Resource
    private UserService userService;

    public void sayOk() {
        System.out.println("UserAction 的sayOK()");
        System.out.println("userAction中装配的userService属性=" + userService);
        userService.hi();
    }
}
