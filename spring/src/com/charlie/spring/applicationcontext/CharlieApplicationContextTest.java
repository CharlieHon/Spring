package com.charlie.spring.applicationcontext;

import com.charlie.spring.bean.Monster;

import java.util.concurrent.ConcurrentHashMap;

/**
 * 1. 这个程序用于实现Spring的一个简单容器机制
 * 2. 后面会详细实现
 * 3. 这里实现如何将beans.xml文件进行解析，并生成对象，放入容器中
 * 4. 提供一个方法 getBeans(id) 返回对应的对象
 */
public class CharlieApplicationContextTest {
    public static void main(String[] args) throws Exception {
        CharlieApplicationContext ioc = new CharlieApplicationContext("beans.xml");
        Monster monster01 = (Monster) ioc.getBean("monster01");
        System.out.println("monster01=" + monster01);   // Monster{monsterId=100, name='牛魔王', skill='芭蕉扇'}
        System.out.println("OK~");
    }
}
