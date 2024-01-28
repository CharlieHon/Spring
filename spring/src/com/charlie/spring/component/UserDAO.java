package com.charlie.spring.component;

import org.springframework.stereotype.Repository;

// 使用注解 @Repository 标识该类是一个持久化层的类/对象
/*
1. 标记注解后，类名首字母小写作为id的值(默认)
2. 如果设置 value属性，则使用指定的 charlieUserDAO 作为userDAO对象的id
 */
@Repository(value = "charlieUserDAO")
public class UserDAO {

}
