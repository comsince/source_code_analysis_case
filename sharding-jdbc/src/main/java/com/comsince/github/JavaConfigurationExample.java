package com.comsince.github;

import com.comsince.github.config.MasterSlaveConfiguration;
import com.comsince.github.config.ShardingDatabasesAndTablesConfigurationPrecise;
import com.comsince.github.config.ShardingDatabasesConfigurationPrecise;
import com.comsince.github.config.ShardingTablesConfigurationPrecise;
import com.comsince.github.dao.impl.JDBCOrderItemRepositoryImpl;
import com.comsince.github.dao.impl.JDBCOrderRepositoryImpl;
import com.comsince.github.service.CommonService;
import com.comsince.github.service.impl.RawPojoService;
import com.comsince.github.type.ShardingType;
import com.comsince.github.utils.ExampleConfiguration;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.sql.SQLException;

/**
 * @author comsicne
 *         Copyright (c) [2019] [Meizu.inc]
 * @Time 19-1-28 下午2:10
 **/
public class JavaConfigurationExample {

    private static ShardingType type = ShardingType.SHARDING_DATABASES;
//    private static ShardingType type = ShardingType.SHARDING_TABLES;
//    private static ShardingType type = ShardingType.SHARDING_DATABASES_AND_TABLES;
//    private static ShardingType type = ShardingType.MASTER_SLAVE;
//    private static ShardingType type = ShardingType.SHARDING_MASTER_SLAVE;

    public static void main(final String[] args) throws SQLException {
        process(getDataSource());
    }

    private static DataSource getDataSource() throws SQLException {
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
                //配置主从数据同步
                exampleConfig = new MasterSlaveConfiguration();
                break;
//            case SHARDING_MASTER_SLAVE:
//                exampleConfig = new ShardingMasterSlaveConfiguration();
//                break;
            default:
                throw new UnsupportedOperationException(type.name());
        }
        return exampleConfig.getDataSource();
    }

    private static void process(final DataSource dataSource) {
        CommonService commonService = getCommonService(dataSource);
        commonService.initEnvironment();
        commonService.processSuccess();
        commonService.cleanEnvironment();
    }

    private static CommonService getCommonService(final DataSource dataSource) {
        return new RawPojoService(new JDBCOrderRepositoryImpl(dataSource), new JDBCOrderItemRepositoryImpl(dataSource));
    }
}
