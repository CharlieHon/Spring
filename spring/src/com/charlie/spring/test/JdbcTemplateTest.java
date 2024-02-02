package com.charlie.spring.test;

import com.charlie.spring.bean.Monster;
import com.charlie.spring.jdbctemplate.dao.MonsterDAO;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JdbcTemplateTest {
    @Test
    public void testDataSourceByJdbcTemplate() throws SQLException {
        ApplicationContext ioc = new ClassPathXmlApplicationContext("JdbcTemplate_ioc.xml");
        // 因为 com.mchange.v2.c3p0.ComboPooledDataSource 类型实现了 DataSource 接口
        DataSource dataSource = ioc.getBean("dataSource", DataSource.class);
        Connection connection = dataSource.getConnection();
        // 获取到connection=com.mchange.v2.c3p0.impl.NewProxyConnection@124c278f
        System.out.println("获取到connection=" + connection);
        connection.close();
        System.out.println("OK!");
    }

    // 测试通过JdbcTemplate对象完成添加数据
    @Test
    public void addDataByJdbcTemplate() {
        ApplicationContext ioc = new ClassPathXmlApplicationContext("JdbcTemplate_ioc.xml");
        // 获取JdbcTemplate对象
        JdbcTemplate jdbcTemplate = ioc.getBean(JdbcTemplate.class);
        // 1. 添加方式1
        //String sql = "insert into monster values(400, '红孩儿', '三昧真火')";
        //jdbcTemplate.execute(sql);
        // 2. 添加方式2
        String sql = "insert into monster values(?, ?, ?)";
        // 返回表受影响的行数，添加成功返回1
        int affected = jdbcTemplate.update(sql, 700, "牛魔王", "芭蕉扇");
        System.out.println("add OK, affected=" + affected);
    }

    // 修改数据
    @Test
    public void updateDataByJdbcTemplate() {
        ApplicationContext ioc = new ClassPathXmlApplicationContext("JdbcTemplate_ioc.xml");
        // 获取JdbcTemplate对象
        JdbcTemplate jdbcTemplate = ioc.getBean(JdbcTemplate.class);
        // SQL语句
        String sql = "update monster set skill=? where id=?";
        int update = jdbcTemplate.update(sql, "美人计", 300);
        System.out.println("update=" + update);
    }

    // 批量添加数据
    @Test
    public void addBatchByJdbcTemplate() {
        ApplicationContext ioc = new ClassPathXmlApplicationContext("JdbcTemplate_ioc.xml");
        // 获取JdbcTemplate对象
        JdbcTemplate jdbcTemplate = ioc.getBean(JdbcTemplate.class);
        // 1. 确定API，更新->update->batchUpdate
        // public int[] batchUpdate(String sql, List<Object[]> batchArgs)
        // 2. 准备参数
        String sql = "insert into monster values(?, ?, ?)";
        List<Object[]> batchArgs = new ArrayList<>();
        batchArgs.add(new Object[]{600, "老鼠精", "旋风钻"});
        batchArgs.add(new Object[]{500, "黑熊精", "偷袈裟"});
        // 3. 调用
        // 说明：返回结果是一个数组，每个元素对应上面sql语句对表的影响行数
        int[] ints = jdbcTemplate.batchUpdate(sql, batchArgs);
        for (int anInt : ints) {
            System.out.println("anInt=" + anInt);
        }
        System.out.println("批量添加成功~");
    }

    // 查询id=100的monster，并封装到Monster实体对象【实际开发中，非常有用】
    @Test
    public void query1() {
        ApplicationContext ioc = new ClassPathXmlApplicationContext("JdbcTemplate_ioc.xml");
        JdbcTemplate jdbcTemplate = ioc.getBean(JdbcTemplate.class);
        // 1. 确定API：queryForObject()
        // public <T> T queryForObject(String sql, RowMapper<T> rowMapper)
        // 2. 准备参数
        // 数据库中字段与封装字段的不一样，需要使用别名，否则会报错
        String sql = "SELECT id AS monsterId, `name`, skill FROM monster WHERE id=100";
        // 使用 RowMapper接口来对返回的数据进行封装->底层使用的是反射+setter
        // 注意：查询记录表的字段需要和Monster对象的字段名保持一致！
        RowMapper<Monster> rowMapper = new BeanPropertyRowMapper<>(Monster.class);
        Monster monster = jdbcTemplate.queryForObject(sql, rowMapper);
        System.out.println("monster=" + monster);
    }

    // 查询id>=200的monster的，并封装到monster的实例对象
    @Test
    public void query2() {
        ApplicationContext ioc = new ClassPathXmlApplicationContext("JdbcTemplate_ioc.xml");
        JdbcTemplate jdbcTemplate = ioc.getBean(JdbcTemplate.class);
        // 1. API-> query
        // public <T> List<T> query(String sql, RowMapper<T> rowMapper, @Nullable Object... args)
        String sql = "SELECT id AS monsterId, `name`, skill FROM monster WHERE id>=?";
        RowMapper<Monster> rowMapper = new BeanPropertyRowMapper<>(Monster.class);
        List<Monster> monsterList = jdbcTemplate.query(sql, rowMapper, 300);
        for (Monster monster : monsterList) {
            System.out.println("monster=" + monster);
        }
    }

    // 查询返回结果只有一行一列的值，比如查询id=100的妖怪名
    @Test
    public void query3() {
        ApplicationContext ioc = new ClassPathXmlApplicationContext("JdbcTemplate_ioc.xml");
        JdbcTemplate jdbcTemplate = ioc.getBean(JdbcTemplate.class);
        // 确定API->queryForObject
        // public <T> T queryForObject(String sql, Class<T> requiredType)
        String sql = "select `name` from monster where id=?";
        String monsterName = jdbcTemplate.queryForObject(sql, String.class, 100);
        System.out.println("monsterName=" + monsterName);   // 青牛怪
    }

    // 使用Map传入具名参数
    @Test
    public void testDataByNamedParameterJdbcTemplate() {
        ApplicationContext ioc = new ClassPathXmlApplicationContext("JdbcTemplate_ioc.xml");
        // 得到NamedParameterJdbcTemplate bean
        NamedParameterJdbcTemplate namedParameterJdbcTemplate = ioc.getBean(NamedParameterJdbcTemplate.class);
        // 1. 确定API
        // public int update(String sql, Map<String, ?> paramMap)
        // 2. 准备参数 [:my_id, :name, :skill] 要求按照规定的名字来设置参数
        String sql = "insert into monster values (:id, :name, :skill)";
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("id", 800);
        paramMap.put("name", "银角大王");
        paramMap.put("skill", "玉净瓶");
        int update = namedParameterJdbcTemplate.update(sql, paramMap);
        System.out.println("update=" + update);
    }

    // 使用sqlparametersource
    @Test
    public void testDataBySqlParameterSource() {
        ApplicationContext ioc = new ClassPathXmlApplicationContext("JdbcTemplate_ioc.xml");
        NamedParameterJdbcTemplate namedParameterJdbcTemplate = ioc.getBean(NamedParameterJdbcTemplate.class);
        // 1. 确定API
        // public int update(String sql, SqlParameterSource paramSource)
        // public BeanPropertySqlParameterSource(Object object)
        // 注意：这里的id，需要改为 :monsterId，因为底层是通过getter方法获取字段值的
        String sql = "insert into monster values (:monsterId, :name, :skill)";
        Monster monster = new Monster(900, "金角大王", "大葫芦");
        BeanPropertySqlParameterSource sqlParameterSource = new BeanPropertySqlParameterSource(monster);
        int update = namedParameterJdbcTemplate.update(sql, sqlParameterSource);
        System.out.println("update=" + update);
    }

    // 测试 MonsterDAO 依赖注入 JdbcTemplate jdbcTemplate
    @Test
    public void monsterDAO() {
        ApplicationContext ioc = new ClassPathXmlApplicationContext("JdbcTemplate_ioc.xml");
        MonsterDAO monsterDAO = ioc.getBean("monsterDAO", MonsterDAO.class);
        Monster monster = new Monster(1000, "大鹏", "神通广大");
        monsterDAO.save(monster);
        System.out.println("Finish!");
    }
}
