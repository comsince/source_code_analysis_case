package com.comsince.github.service;

import com.comsince.github.service.impl.CommonServiceImpl;
import io.shardingsphere.transaction.annotation.ShardingTransactionType;
import io.shardingsphere.transaction.api.TransactionType;
import io.shardingsphere.transaction.api.TransactionTypeHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author comsicne
 *         Copyright (c) [2019] [Meizu.inc]
 * @Time 19-1-30 下午5:11
 **/
public abstract class ShardingJDBCTransactionService extends CommonServiceImpl implements TransactionService {

    private Logger logger = LoggerFactory.getLogger(ShardingJDBCTransactionService.class);

    @Override
    @ShardingTransactionType
    @Transactional
    public void processFailureWithLocal() {
        printTransactionType();
        super.processFailure();
    }

    @Override
    @ShardingTransactionType(TransactionType.XA)
    @Transactional
    public void processFailureWithXa() {
        printTransactionType();
        super.processFailure();
    }

    @Override
    @ShardingTransactionType(TransactionType.BASE)
    @Transactional
    public void processFailureWithBase() {
        printTransactionType();
        super.processFailure();
    }

    @Override
    public void printTransactionType() {
        logger.info(String.format("-------------- Process With Transaction %s ---------------", TransactionTypeHolder.get()));
    }
}
