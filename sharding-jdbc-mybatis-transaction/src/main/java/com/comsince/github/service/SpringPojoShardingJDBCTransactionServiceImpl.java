package com.comsince.github.service;

import com.comsince.github.dao.OrderItemRepository;
import com.comsince.github.dao.OrderRepository;
import com.comsince.github.dao.entity.Order;
import com.comsince.github.dao.entity.OrderItem;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author comsicne
 *         Copyright (c) [2019] [Meizu.inc]
 * @Time 19-1-30 下午5:16
 **/
@Service("jdbcTransactionService")
public class SpringPojoShardingJDBCTransactionServiceImpl extends ShardingJDBCTransactionService implements SpringPojoTransactionService {

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
