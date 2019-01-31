package com.comsince.github;

import com.comsince.github.service.SpringPojoTransactionService;
import com.comsince.github.service.TransactionService;
import com.comsince.github.type.ShardingType;
import io.shardingsphere.transaction.api.TransactionType;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author comsicne
 *         Copyright (c) [2019] [Meizu.inc]
 * @Time 19-1-30 下午5:10
 **/
public class SpringNamespaceTransactionExample {
    private static ShardingType type = ShardingType.SHARDING_DATABASES;
//    private static ShardingType type = ShardingType.SHARDING_TABLES;
//    private static ShardingType type = ShardingType.SHARDING_DATABASES_AND_TABLES;
//    private static ShardingType type = ShardingType.MASTER_SLAVE;
//    private static ShardingType type = ShardingType.SHARDING_MASTER_SLAVE;

    //    private static boolean isRangeSharding = true;
    private static boolean isRangeSharding = false;

    public static void main(final String[] args) {
        try (ConfigurableApplicationContext applicationContext = new ClassPathXmlApplicationContext(isRangeSharding ? getApplicationFileRange() : getApplicationFilePrecise())) {
            process(applicationContext);
        }
    }

    private static String getApplicationFilePrecise() {
        switch (type) {
            case SHARDING_DATABASES:
                return "application-sharding-databases-precise.xml";
            case SHARDING_TABLES:
                return "application-sharding-tables-precise.xml";
            case SHARDING_DATABASES_AND_TABLES:
                return "application-sharding-databases-tables-precise.xml";
            case MASTER_SLAVE:
                return "application-master-slave.xml";
            case SHARDING_MASTER_SLAVE:
                return "application-sharding-master-slave-precise.xml";
            default:
                throw new UnsupportedOperationException(type.name());
        }
    }

    private static String getApplicationFileRange() {
        switch (type) {
            case SHARDING_DATABASES:
                return "META-INF/application-sharding-databases-range.xml";
            case SHARDING_TABLES:
                return "META-INF/application-sharding-tables-range.xml";
            case SHARDING_DATABASES_AND_TABLES:
                return "META-INF/application-sharding-databases-tables-range.xml";
            case MASTER_SLAVE:
                return "META-INF/application-master-slave.xml";
            case SHARDING_MASTER_SLAVE:
                return "META-INF/application-sharding-master-slave-range.xml";
            default:
                throw new UnsupportedOperationException(type.name());
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
