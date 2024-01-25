package com.charlie.spring.bean;

// 部门类
public class Dept {
    private String name;

    public Dept() {}

    @Override
    public String toString() {
        return "Dept{" +
                "name='" + name + '\'' +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
