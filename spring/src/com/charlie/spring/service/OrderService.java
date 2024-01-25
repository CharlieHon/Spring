package com.charlie.spring.service;

import com.charlie.spring.dao.OrderDAO;

public class OrderService {
    private OrderDAO orderDAO;

    public OrderDAO getOrderDAO() {
        return orderDAO;
    }

    public void setOrderDAO(OrderDAO orderDAO) {
        this.orderDAO = orderDAO;
    }
}
