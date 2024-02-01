package com.charlie.spring.component;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

// 就是一个Controller
// 在默认情况下，配置的 @Component @Repository @Service @Controller 都是单例
// @Scope(value = "prototype") 表示以多实例形式，返回 UserAction bean
// 思考：Spring容器底层如何实现
@Component
@Scope(value = "prototype")
public class UserAction {
}
