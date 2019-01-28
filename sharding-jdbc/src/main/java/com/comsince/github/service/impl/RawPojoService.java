package com.comsince.github.service.impl;

import com.comsince.github.dao.OrderItemRepository;
import com.comsince.github.dao.OrderRepository;
import com.comsince.github.dao.entity.Order;
import com.comsince.github.dao.entity.OrderItem;
import com.comsince.github.dao.impl.JDBCOrderItemRepositoryImpl;
import com.comsince.github.dao.impl.JDBCOrderRepositoryImpl;

/**
 * @author comsicne
 *         Copyright (c) [2019] [Meizu.inc]
 * @Time 19-1-28 下午2:52
 **/
public class RawPojoService extends CommonServiceImpl {

    private final OrderRepository orderRepository;

    private final OrderItemRepository orderItemRepository;

    public RawPojoService(final JDBCOrderRepositoryImpl orderRepository, final JDBCOrderItemRepositoryImpl orderItemRepository) {
        this.orderRepository = orderRepository;
        this.orderItemRepository = orderItemRepository;
    }

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