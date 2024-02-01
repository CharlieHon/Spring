package com.charlie.spring.component;

import com.charlie.spring.annotation.Component;
import com.charlie.spring.annotation.Scope;

// MonsterService对象是一个service
// 1. 如果指定value，那么在注入spring容器时，以指定为准
// 2. 如果没有指定value，则使用类名首字母小写
@Component(value = "monsterService")  // 把MonsterService对象注入到我们自己的spring容器
@Scope(value = "prototype")
public class MonsterService {
}
