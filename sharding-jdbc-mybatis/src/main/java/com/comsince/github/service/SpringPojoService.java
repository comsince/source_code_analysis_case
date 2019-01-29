package com.comsince.github.service;

import com.comsince.github.dao.OrderItemRepository;
import com.comsince.github.dao.OrderRepository;
import com.comsince.github.dao.entity.Order;
import com.comsince.github.dao.entity.OrderItem;
import com.comsince.github.service.impl.CommonServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author comsicne
 *         Copyright (c) [2019] [Meizu.inc]
 * @Time 19-1-28 下午5:15
 **/
@Service
public class SpringPojoService extends CommonServiceImpl{
    @Resource
    private OrderRepository orderRepository;

    @Resource
    private OrderItemRepository orderItemRepository;

    @Override
    protected OrderRepository getOrderRepository() {
        return orderRepository;
    }

    @Override
    protected OrderItemRepository getOrderItemRepository() {
        return orderItemRepository;
    }

    @Override
    protected Order newOrder() {
        return new Order();
    }

    @Override
    protected OrderItem newOrderItem() {
        return new OrderItem();
    }
}
