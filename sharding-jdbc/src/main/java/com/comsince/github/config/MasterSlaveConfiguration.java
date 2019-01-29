package com.comsince.github.config;

import com.comsince.github.utils.DataSourceUtil;
import com.comsince.github.utils.ExampleConfiguration;
import io.shardingsphere.api.config.rule.MasterSlaveRuleConfiguration;
import io.shardingsphere.shardingjdbc.api.MasterSlaveDataSourceFactory;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author comsicne
 *         Copyright (c) [2019] [Meizu.inc]
 * @Time 19-1-28 下午4:10
 **/
public class MasterSlaveConfiguration implements ExampleConfiguration{
    @Override
    public DataSource getDataSource() throws SQLException {
        MasterSlaveRuleConfiguration masterSlaveRuleConfig = new MasterSlaveRuleConfiguration();
        masterSlaveRuleConfig.setName("demo_ds_master_slave");
        masterSlaveRuleConfig.setMasterDataSourceName("demo_ds_master");
        masterSlaveRuleConfig.setSlaveDataSourceNames(Arrays.asList("demo_ds_slave_0", "demo_ds_slave_1"));
        return MasterSlaveDataSourceFactory.createDataSource(createDataSourceMap(), masterSlaveRuleConfig, new ConcurrentHashMap<String, Object>(), new Properties());
    }

    private Map<String, DataSource> createDataSourceMap() {
        Map<String, DataSource> result = new HashMap<>();
        result.put("demo_ds_master", DataSourceUtil.createDataSource("demo_ds_master"));
        result.put("demo_ds_slave_0", DataSourceUtil.createDataSource("demo_ds_slave_0"));
        result.put("demo_ds_slave_1", DataSourceUtil.createDataSource("demo_ds_slave_1"));
        return result;
    }
}
