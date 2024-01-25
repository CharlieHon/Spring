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

    @Override
    public String toString() {
        return "House{" +
                "name='" + name + '\'' +
                '}';
    }
}
