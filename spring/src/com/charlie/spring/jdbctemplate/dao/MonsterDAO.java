package com.charlie.spring.jdbctemplate.dao;

import com.charlie.spring.bean.Monster;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;

@Repository // 将MonsterDAO注入到Spring容器
public class MonsterDAO {
    // 注入一个属性
    @Resource
    private JdbcTemplate jdbcTemplate;

    // 完成保存任务
    public void save(Monster monster) {
        String sql = "insert into monster values(?, ?, ?)";
        int update = jdbcTemplate.update(sql, monster.getMonsterId(), monster.getName(), monster.getSkill());
        System.out.println("update=" + update);
    }
}
