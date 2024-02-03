package com.charlie.spring.tx.service;

import com.charlie.spring.tx.dao.GoodsDAO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service    // 将GoodsService对象注入到Spring容器
public class GoodsService {
    // 定义属性GoodsDAO
    @Resource
    private GoodsDAO goodsDAO;

    /**
     * 编写一个方法，完成用户购买商品的业务
     *
     * @param userId  用户id
     * @param goodsId 商品id
     * @param amount  商品数量
     */
    public void buyGoods(int userId, int goodsId, int amount) {
        System.out.println("用户购买信息：userId" + userId + " goodsId=" + goodsId
                + " 购买数量=" + amount);
        // 1. 得到商品数量
        float price = goodsDAO.queryPriceById(goodsId);
        // 2. 减少用户的余额
        goodsDAO.updateBalance(userId, price * amount);
        // 3. 减少库存量
        goodsDAO.updateAmount(goodsId, amount);
        System.out.println("用户购买成功！");
    }

    /* @Transactional 注解说明
    1. 使用 @Transactional 可以进行声明式事务控制
    2. 即将标识的方法 buyGoodsByTx 中，对数据库的操作视为一个 事务 管理
    3. @Transactional 底层使用的仍然是AOP机制
    4. 底层是使用动态代理对象来调用buyGoodsByTx
    5. 在执行 buyGoodsByTx 之前，先调用 事务管理器的 doBegin()，再调用 buyGoodsByTx
        如果没有发生异常，则调用 事务管理器的 doCommit()，如果发生异常，则调用其 doRollBack()方法
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void buyGoodsByTx(int userId, int goodsId, int amount) {
        System.out.println("用户购买信息：userId" + userId + " goodsId=" + goodsId
                + " 购买数量=" + amount);
        // 1. 得到商品数量
        float price = goodsDAO.queryPriceById(goodsId);
        // 2. 减少用户的余额
        goodsDAO.updateBalance(userId, price * amount);
        // 3. 减少库存量
        goodsDAO.updateAmount(goodsId, amount);
        System.out.println("用户购买成功！");
    }

    // 演示事务传播机制
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void buyGoodsByTx2(int userId, int goodsId, int amount) {
        System.out.println("用户购买信息：userId" + userId + " goodsId=" + goodsId
                + " 购买数量=" + amount);
        // 1. 得到商品数量
        float price = goodsDAO.queryPriceById2(goodsId);
        // 2. 减少用户的余额
        goodsDAO.updateBalance2(userId, price * amount);
        // 3. 减少库存量
        goodsDAO.updateAmount2(goodsId, amount);
        System.out.println("用户购买成功！");
    }

    // 1. 在默认情况下，声明式事务的隔离级别是 REPEATABLE_READ
    // 2. 设置隔离级别 isolation = Isolation.READ_COMMITTED 读已提交
    //      表示只要是提交的数据，在当前事务时可以读取到最新数据的
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public void buyGoodsByTxISOLATION() {
        // 查询两次商品的价格
        float price = goodsDAO.queryPriceById(1);
        System.out.println("第一次查询的price=" + price);
        float price2 = goodsDAO.queryPriceById(1);
        System.out.println("第一次查询的price2=" + price2);
    }

    // 1. 演示事务超时回滚，以 秒 为单位
    // 2. timeout = 2 表示 buyGoodsByTxTimeout 如果执行时间超过2秒，该事务就进行回滚
    // 3. 如果没有设置 timeout ，默认值为-1 使用事务的默认超时事件或者不支持
    @Transactional(timeout = 2)
    public void buyGoodsByTxTimeout(int userId, int goodsId, int amount) {
        System.out.println("用户购买信息：userId" + userId + " goodsId=" + goodsId
                + " 购买数量=" + amount);
        // 1. 得到商品数量
        float price = goodsDAO.queryPriceById(goodsId);
        // 2. 减少用户的余额
        goodsDAO.updateBalance(userId, price * amount);

        // 模拟超时
        System.out.println("=======模拟超时开始==========");
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("=======模拟超时结束==========");

        // 3. 减少库存量
        goodsDAO.updateAmount(goodsId, amount);
        System.out.println("用户购买成功！");
    }
}
