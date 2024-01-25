package com.charlie.spring.web;

import com.charlie.spring.service.OrderService;

// 即Servlet/Controller
public class OrderAction {
    private OrderService orderService;

    public OrderService getOrderService() {
        return orderService;
    }

    public void setOrderService(OrderService orderService) {
        this.orderService = orderService;
    }
}
