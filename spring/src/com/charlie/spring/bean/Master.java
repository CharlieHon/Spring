package com.charlie.spring.bean;

import java.util.*;

public class Master {
    private String name;
    private List<Monster> monsterList;
    private Map<String, Monster> monsterMap;
    private Set<Monster> monsterSet;
    // 数组
    private String[] monsterName;
    // Properties是Hashtable的子类，是k-v的形式
    private Properties pros;

    @Override
    public String toString() {
        return "Master{" +
                "name='" + name + '\'' +
                ", monsterList=" + monsterList +
                ", monsterMap=" + monsterMap +
                ", monsterSet=" + monsterSet +
                ", monsterName=" + Arrays.toString(monsterName) +
                ", pros=" + pros +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Monster> getMonsterList() {
        return monsterList;
    }

    public void setMonsterList(List<Monster> monsterList) {
        this.monsterList = monsterList;
    }

    public Map<String, Monster> getMonsterMap() {
        return monsterMap;
    }

    public void setMonsterMap(Map<String, Monster> monsterMap) {
        this.monsterMap = monsterMap;
    }

    public Set<Monster> getMonsterSet() {
        return monsterSet;
    }

    public void setMonsterSet(Set<Monster> monsterSet) {
        this.monsterSet = monsterSet;
    }

    public String[] getMonsterName() {
        return monsterName;
    }

    public void setMonsterName(String[] monsterName) {
        this.monsterName = monsterName;
    }

    public Properties getPros() {
        return pros;
    }

    public void setPros(Properties pros) {
        this.pros = pros;
    }
}
