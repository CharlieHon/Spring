package com.charlie.spring.tx;

import com.charlie.spring.tx.dao.GoodsDAO;
import com.charlie.spring.tx.service.GoodsService;
import com.charlie.spring.tx.service.MultiplyService;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TxTest {

    @Test
    public void queryPriceById() {
        // 获取到容器
        ApplicationContext ioc = new ClassPathXmlApplicationContext("tx_ioc.xml");
        GoodsDAO goodsDAO = ioc.getBean("goodsDAO", GoodsDAO.class);
        float price = goodsDAO.queryPriceById(2);
        System.out.println("price=" + price);
    }

    @Test
    public void updateBalance() {
        ApplicationContext ioc = new ClassPathXmlApplicationContext("tx_ioc.xml");
        GoodsDAO goodsDAO = ioc.getBean("goodsDAO", GoodsDAO.class);
        goodsDAO.updateBalance(1, 1.0f);
        System.out.println("减少用户余额成功！");
    }

    @Test
    public void updateAmount() {
        ApplicationContext ioc = new ClassPathXmlApplicationContext("tx_ioc.xml");
        GoodsDAO goodsDAO = ioc.getBean("goodsDAO", GoodsDAO.class);
        goodsDAO.updateAmount(1, 1);
        System.out.println("减少库存量成功！");
    }

    // 测试GoodsService-用户购买商品业务
    @Test
    public void goodsServiceTest() {
        ApplicationContext ioc = new ClassPathXmlApplicationContext("tx_ioc.xml");
        GoodsService goodsService = ioc.getBean("goodsService", GoodsService.class);
        goodsService.buyGoods(1, 1, 1);
    }

    // 声明式事务测试
    @Test
    public void goodsServiceBtTxTest() {
        ApplicationContext ioc = new ClassPathXmlApplicationContext("tx_ioc.xml");
        GoodsService goodsService = ioc.getBean("goodsService", GoodsService.class);
        // 这里调用的是进行了 事务声明 的方法
        goodsService.buyGoodsByTx(1, 1, 1);
    }

    // 测试事务传播机制
    @Test
    public void multiBuyGoodsByTxTest() {
        ApplicationContext ioc = new ClassPathXmlApplicationContext("tx_ioc.xml");
        MultiplyService multiplyService = ioc.getBean("multiplyService", MultiplyService.class);
        multiplyService.multiBuyGoodsByTx();
    }

    // 测试默认隔离级别
    @Test
    public void buyGoodsByTxISOLATIONTest() {
        ApplicationContext ioc = new ClassPathXmlApplicationContext("tx_ioc.xml");
        GoodsService goodsService = ioc.getBean("goodsService", GoodsService.class);
        goodsService.buyGoodsByTxISOLATION();
    }

    @Test
    public void buyGoodsByTxTimeout() {
        ApplicationContext ioc = new ClassPathXmlApplicationContext("tx_ioc.xml");
        GoodsService goodsService = ioc.getBean("goodsService", GoodsService.class);
        goodsService.buyGoodsByTxTimeout(1, 1, 1);
    }
}
