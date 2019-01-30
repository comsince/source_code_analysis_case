package com.comsince.github.service.impl;

import com.comsince.github.dao.JDBCOrderItemTransactionRepositotyImpl;
import com.comsince.github.dao.JDBCOrderTransactionRepositoryImpl;
import com.comsince.github.dao.OrderItemRepository;
import com.comsince.github.dao.OrderRepository;
import com.comsince.github.dao.entity.Order;
import com.comsince.github.dao.entity.OrderItem;
import com.comsince.github.service.TransactionService;
import io.shardingsphere.transaction.api.TransactionType;
import io.shardingsphere.transaction.api.TransactionTypeHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * @author comsicne
 *         Copyright (c) [2019] [Meizu.inc]
 * @Time 19-1-30 上午10:46
 **/
public final class RawPojoTransactionService extends CommonServiceImpl implements TransactionService {

    Logger logger = LoggerFactory.getLogger("RawPojoTransactionService");

    private final JDBCOrderTransactionRepositoryImpl orderRepository;

    private final JDBCOrderItemTransactionRepositotyImpl orderItemRepository;

    private Connection insertConnection;

    public RawPojoTransactionService(final JDBCOrderTransactionRepositoryImpl orderRepository,
                                     final JDBCOrderItemTransactionRepositotyImpl orderItemRepository, final DataSource dataSource) throws SQLException {
        this.orderRepository = orderRepository;
        this.orderItemRepository = orderItemRepository;
        this.insertConnection = dataSource.getConnection();
        orderRepository.setInsertConnection(insertConnection);
        orderItemRepository.setInsertConnection(insertConnection);
    }

    @Override
    public void processFailureWithLocal() {
        TransactionTypeHolder.set(TransactionType.LOCAL);
        printTransactionType();
        executeFailure();
    }

    @Override
    public void processFailureWithXa() {
        TransactionTypeHolder.set(TransactionType.XA);
        printTransactionType();
        executeFailure();
    }

    @Override
    public void processFailureWithBase() {
        TransactionTypeHolder.set(TransactionType.BASE);
        printTransactionType();
        executeFailure();
    }

//    @Override
//    public void processSuccess(boolean isRangeSharding) {
//        TransactionTypeHolder.set(TransactionType.LOCAL);
//        printTransactionType();
//        executeSuccess(isRangeSharding);
//    }

    @Override
    public void printTransactionType() {
        logger.info(String.format("-------------- Process With Transaction %s ---------------", TransactionTypeHolder.get()));

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

    private void executeSuccess(boolean isRangeSharding){
        try {
            beginTransaction();
            super.processSuccess(isRangeSharding);
            commitTransaction();
        } catch (RuntimeException ex) {
            logger.error("executeSuccess "+ex.getMessage());
            rollbackTransaction();
            super.printData(false);
        }
    }

    private void executeFailure() {
        try {
            beginTransaction();
            super.processFailure();
            commitTransaction();
        } catch (RuntimeException ex) {
            logger.error("executeFailure "+ex.getMessage());
            rollbackTransaction();
            super.printData(false);
        }
    }

    private void beginTransaction() {
        try {
            if (null != this.insertConnection && !this.insertConnection.isClosed()) {
                this.insertConnection.setAutoCommit(false);
            }
        } catch (SQLException ignored) {
        }
    }

    private void commitTransaction() {
        try {
            if (null != this.insertConnection && !this.insertConnection.isClosed()) {
                this.insertConnection.commit();
            }
        } catch (SQLException ignored) {
        }
    }

    private void rollbackTransaction() {
        try {
            if (null != this.insertConnection && !this.insertConnection.isClosed()) {
                this.insertConnection.rollback();
            }
        } catch (SQLException ignored) {
        }
    }
}