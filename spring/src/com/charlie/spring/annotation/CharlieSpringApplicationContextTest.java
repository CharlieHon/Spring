package com.charlie.spring.annotation;

import com.charlie.spring.component.MyComponent;
import com.charlie.spring.component.UserAction;
import com.charlie.spring.component.UserDAO;
import com.charlie.spring.component.UserService;

public class CharlieSpringApplicationContextTest {
    public static void main(String[] args) {
        CharlieSpringApplicationContext ioc = new CharlieSpringApplicationContext(CharlieSpringConfig.class);

        UserAction userAction = (UserAction) ioc.getBean("UserAction");
        System.out.println(userAction);

        MyComponent myComponent = (MyComponent) ioc.getBean("MyComponent");
        System.out.println(myComponent);

        UserService userService = (UserService) ioc.getBean("UserService");
        System.out.println(userService);

        UserDAO userDAO =  (UserDAO) ioc.getBean("UserDAO");
        System.out.println(userDAO);

        System.out.println("OK!");
    }
}
