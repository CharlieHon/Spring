package com.charlie.spring.tx.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;

@Repository // 将GoodsDAO对象注入到Spring容器
public class GoodsDAO {
    @Resource
    private JdbcTemplate jdbcTemplate;

    // 根据商品goods_id，查询其价格
    public float queryPriceById(Integer id) {
        String sql = "select price from goods where goods_id=?";
        Float price = jdbcTemplate.queryForObject(sql, Float.class, id);
        return price;
    }

    // 根据用户user_id，修改其余额
    public void updateBalance(Integer user_id, Float money) {
        String sql = "update user_account set money=money-? where user_id=?";
        jdbcTemplate.update(sql, money, user_id);
    }

    // 根据商品goods_id，更新商品数量
    public void updateAmount(Integer goods_id, Integer amount) {
        String sql = "update goods_amount set goods_num=goods_num-? where goods_id=?";
        jdbcTemplate.update(sql, amount, goods_id);
    }

    // 根据商品goods_id，查询其价格
    public float queryPriceById2(Integer id) {
        String sql = "select price from goods where goods_id=?";
        Float price = jdbcTemplate.queryForObject(sql, Float.class, id);
        return price;
    }

    // 根据用户user_id，修改其余额
    public void updateBalance2(Integer user_id, Float money) {
        String sql = "update user_account set money=money-? where user_id=?";
        jdbcTemplate.update(sql, money, user_id);
    }

    // 根据商品goods_id，更新商品数量
    public void updateAmount2(Integer goods_id, Integer amount) {
        String sql = "update goods_amount set goods_num=goods_num-? where goods_id=?";
        jdbcTemplate.update(sql, amount, goods_id);
    }
}
