package com.charlie.spring.bean;

public class Monster {
    private Integer monsterId;
    private String name;
    private String skill;

    // 无参构造器必须要写，因为反射的时候需要使用
    public Monster() {}

    public Monster(Integer monsterId, String name, String skill) {
        this.monsterId = monsterId;
        this.name = name;
        this.skill = skill;
    }

    public Integer getMonsterId() {
        return monsterId;
    }

    @Override
    public String toString() {
        return "Monster{" +
                "monsterId=" + monsterId +
                ", name='" + name + '\'' +
                ", skill='" + skill + '\'' +
                '}';
    }

    public void setMonsterId(Integer monsterId) {
        this.monsterId = monsterId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSkill() {
        return skill;
    }

    public void setSkill(String skill) {
        this.skill = skill;
    }
}
