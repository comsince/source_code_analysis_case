package com.comsince.github.service.impl;

import com.comsince.github.dao.OrderItemRepository;
import com.comsince.github.dao.OrderRepository;
import com.comsince.github.dao.entity.Order;
import com.comsince.github.dao.entity.OrderItem;
import com.comsince.github.service.CommonService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * @author comsicne
 *         Copyright (c) [2019] [Meizu.inc]
 * @Time 19-1-28 下午2:45
 **/
public abstract class CommonServiceImpl implements CommonService{

    Logger logger = LoggerFactory.getLogger(CommonService.class);

    @Override
    public void initEnvironment() {
        getOrderRepository().createTableIfNotExists();
        getOrderItemRepository().createTableIfNotExists();
        getOrderRepository().truncateTable();
        getOrderItemRepository().truncateTable();
    }

    @Override
    public void cleanEnvironment() {
        //getOrderRepository().dropTable();
        //getOrderItemRepository().dropTable();
    }

    @Override
    public void processSuccess() {
        logger.info("-------------- Process Success Begin ---------------");
        List<Long> orderIds = insertData();
        printData();
        //deleteData(orderIds);
        printData();
        logger.info("-------------- Process Success Finish --------------");
    }

    @Override
    public void processSuccess(final boolean isRangeSharding) {
        System.out.println("-------------- Process Success Begin ---------------");
        List<Long> orderIds = insertData();
        printData(isRangeSharding);
        //deleteData(orderIds);
        printData(isRangeSharding);
        System.out.println("-------------- Process Success Finish --------------");
    }

    @Override
    public void processFailure() {

    }

    private List<Long> insertData() {
        logger.info("---------------------------- Insert Data ----------------------------");
        List<Long> result = new ArrayList<>(10);
        for (int i = 1; i <= 10; i++) {
            Order order = newOrder();
            order.setUserId(i);
            order.setStatus("INSERT_TEST");
            getOrderRepository().insert(order);
            OrderItem item = newOrderItem();
            item.setOrderId(order.getOrderId());
            item.setUserId(i);
            item.setStatus("INSERT_TEST");
            getOrderItemRepository().insert(item);
            result.add(order.getOrderId());
        }
        return result;
    }

    private void deleteData(final List<Long> orderIds) {
        logger.info("---------------------------- Delete Data ----------------------------");
        for (Long each : orderIds) {
            getOrderRepository().delete(each);
            getOrderItemRepository().delete(each);
        }
    }

    @Override
    public void printData(final boolean isRangeSharding) {
        if (isRangeSharding) {
            printDataRange();
        } else {
            printDataAll();
        }
    }

    private void printDataRange() {
        System.out.println("---------------------------- Print Order Data -----------------------");
        for (Object each : getOrderRepository().selectRange()) {
            System.out.println(each);
        }
        System.out.println("---------------------------- Print OrderItem Data -------------------");
        for (Object each : getOrderItemRepository().selectRange()) {
            System.out.println(each);
        }
    }

    private void printDataAll() {
        getOrderRepository().selectAll();
        System.out.println("--------------------------------------------------");
        long before = System.nanoTime();
        for (int i = 0; i < 1; i++) {
            getOrderRepository().selectAll();
        }
        System.out.println("Total:" + (System.nanoTime() - before));
//        System.out.println("---------------------------- Print OrderItem Data -------------------");
//        for (Object each : getOrderItemRepository().selectAll()) {
//            System.out.println(each);
//        }
    }

    @Override
    public void printData() {
        logger.info("---------------------------- Print Order Data -----------------------");
        logger.info(getOrderRepository().selectAll().toString());
        logger.info("---------------------------- Print OrderItem Data -------------------");
        logger.info(getOrderItemRepository().selectAll().toString());
    }

    protected abstract OrderRepository getOrderRepository();

    protected abstract OrderItemRepository getOrderItemRepository();

    protected abstract Order newOrder();

    protected abstract OrderItem newOrderItem();
}
