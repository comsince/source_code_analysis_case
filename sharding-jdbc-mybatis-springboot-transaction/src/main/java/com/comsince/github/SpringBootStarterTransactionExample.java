package com.comsince.github;

import com.comsince.github.service.SpringPojoTransactionService;
import com.comsince.github.service.TransactionService;
import io.shardingsphere.transaction.api.TransactionType;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author comsicne
 *         Copyright (c) [2019] [Meizu.inc]
 * @Time 19-1-31 上午9:37
 **/
@ComponentScan("com.comsince.github")
@MapperScan(basePackages = "com.comsince.github.dao.mybatis")
@SpringBootApplication
public class SpringBootStarterTransactionExample {

    public static void main(final String[] args) {
        try (ConfigurableApplicationContext applicationContext = SpringApplication.run(SpringBootStarterTransactionExample.class, args)) {
            process(applicationContext);
        }
    }

    private static void process(final ConfigurableApplicationContext applicationContext) {
        TransactionService transactionService = getTransactionService(applicationContext);
        transactionService.initEnvironment();
        transactionService.processSuccess(false);
        processFailureSingleTransaction(transactionService, TransactionType.LOCAL);
        processFailureSingleTransaction(transactionService, TransactionType.XA);
        processFailureSingleTransaction(transactionService, TransactionType.BASE);
        processFailureSingleTransaction(transactionService, TransactionType.LOCAL);
        transactionService.cleanEnvironment();
    }

    private static void processFailureSingleTransaction(final TransactionService transactionService, final TransactionType type) {
        try {
            switch (type) {
                case LOCAL:
                    transactionService.processFailureWithLocal();
                    break;
                case XA:
                    transactionService.processFailureWithXa();
                    break;
                case BASE:
                    transactionService.processFailureWithBase();
                    break;
                default:
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            transactionService.printData(false);
        }
    }

    private static TransactionService getTransactionService(final ConfigurableApplicationContext applicationContext) {
        return applicationContext.getBean("jdbcTransactionService", SpringPojoTransactionService.class);
    }
}
