package com.charlie.spring.tx.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service
public class MultiplyService {
    @Resource
    private GoodsService goodsService;

    /*
    1. multiBuyGoodsByTx有两次购买商品操作
    2. buyGoodsByTx 和 buyGoodsByTx2 都是声明式事务
    3. 当前 上两个方法 使用的传播属性是默认的 REQUIRED 即会当作一个整体事务进行管理，
        比如 buyGoodsByTx 方法成功，但 buyGoodsByTx2 方法失败，会造成整个事务回滚
    4. 如果 buyGoodsByTx 和 buyGoodsByTx2 事务传播属性修改成 REQUIRES_NEW ，则
       这时两个方法的事务是独立的，其中一个方法出现异常，不影响另一个事务的提交
     */
    @Transactional
    public void multiBuyGoodsByTx() {
        goodsService.buyGoodsByTx(1, 1, 1);
        goodsService.buyGoodsByTx2(1, 1, 1);
    }
}
