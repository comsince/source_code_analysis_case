package com.comsince.github;

import com.comsince.github.config.MasterSlaveConfiguration;
import com.comsince.github.config.ShardingDatabasesAndTablesConfigurationPrecise;
import com.comsince.github.config.ShardingDatabasesConfigurationPrecise;
import com.comsince.github.config.ShardingTablesConfigurationPrecise;
import com.comsince.github.dao.JDBCOrderItemTransactionRepositotyImpl;
import com.comsince.github.dao.JDBCOrderTransactionRepositoryImpl;
import com.comsince.github.service.TransactionService;
import com.comsince.github.service.impl.RawPojoTransactionService;
import com.comsince.github.type.ShardingType;
import com.comsince.github.utils.ExampleConfiguration;
import io.shardingsphere.transaction.api.TransactionType;
import javax.sql.DataSource;
import java.sql.SQLException;

/**
 * @author comsicne
 *         Copyright (c) [2019] [Meizu.inc]
 * @Time 19-1-30 上午10:40
 **/
public class JavaConfigurationTransactionExample {

    private static ShardingType type = ShardingType.SHARDING_DATABASES;
//    private static ShardingType type = ShardingType.SHARDING_TABLES;
//    private static ShardingType type = ShardingType.SHARDING_DATABASES_AND_TABLES;
//    private static ShardingType type = ShardingType.MASTER_SLAVE;
//    private static ShardingType type = ShardingType.SHARDING_MASTER_SLAVE;

    //    private static boolean isRangeSharding = true;
    private static boolean isRangeSharding = false;

    public static void main(final String[] args) throws SQLException {
        //PropertyConfigurator.configure(JavaConfigurationTransactionExample.class.getClassLoader().getResourceAsStream("log4j.xml"));
        process(isRangeSharding ? getDataSourceRange() : getDataSourcePrecise());
    }

    private static DataSource getDataSourcePrecise() throws SQLException {
        ExampleConfiguration exampleConfig;
        switch (type) {
            case SHARDING_DATABASES:
                exampleConfig = new ShardingDatabasesConfigurationPrecise();
                break;
            case SHARDING_TABLES:
                exampleConfig = new ShardingTablesConfigurationPrecise();
                break;
            case SHARDING_DATABASES_AND_TABLES:
                exampleConfig = new ShardingDatabasesAndTablesConfigurationPrecise();
                break;
            case MASTER_SLAVE:
                exampleConfig = new MasterSlaveConfiguration();
                break;
//            case SHARDING_MASTER_SLAVE:
//                exampleConfig = new ShardingMasterSlaveConfigurationPrecise();
//                break;
            default:
                throw new UnsupportedOperationException(type.name());
        }
        return exampleConfig.getDataSource();
    }

    private static DataSource getDataSourceRange() throws SQLException {
        ExampleConfiguration exampleConfig = null;
//        switch (type) {
//            case SHARDING_DATABASES:
//                exampleConfig = new ShardingDatabasesConfigurationRange();
//                break;
//            case SHARDING_TABLES:
//                exampleConfig = new ShardingTablesConfigurationRange();
//                break;
//            case SHARDING_DATABASES_AND_TABLES:
//                exampleConfig = new ShardingDatabasesAndTablesConfigurationRange();
//                break;
//            case MASTER_SLAVE:
//                exampleConfig = new MasterSlaveConfiguration();
//                break;
//            case SHARDING_MASTER_SLAVE:
//                exampleConfig = new ShardingMasterSlaveConfigurationRange();
//                break;
//            default:
//                throw new UnsupportedOperationException(type.name());
//        }
        return exampleConfig.getDataSource();
    }

    private static void process(final DataSource dataSource) throws SQLException {
        TransactionService transactionService = getTransactionService(dataSource);
        transactionService.initEnvironment();
        transactionService.processSuccess(isRangeSharding);
        processFailureSingleTransaction(transactionService, TransactionType.LOCAL);
        processFailureSingleTransaction(transactionService, TransactionType.XA);
        //processFailureSingleTransaction(transactionService, TransactionType.BASE);
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

    private static TransactionService getTransactionService(final DataSource dataSource) throws SQLException {
        return new RawPojoTransactionService(new JDBCOrderTransactionRepositoryImpl(dataSource), new JDBCOrderItemTransactionRepositotyImpl(dataSource), dataSource);
    }
}
