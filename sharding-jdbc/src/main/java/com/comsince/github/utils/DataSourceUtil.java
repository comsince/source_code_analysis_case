package com.comsince.github.utils;

import org.apache.commons.dbcp.BasicDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;

/**
 * @author comsicne
 *         Copyright (c) [2019] [Meizu.inc]
 * @Time 19-1-28 下午2:19
 **/
public class DataSourceUtil {
    private static Logger logger = LoggerFactory.getLogger(DataSourceUtil.class);
    private static final String HOST = "localhost";

    private static final int PORT = 3306;

    private static final String USER_NAME = "root";

    private static final String PASSWORD = "123456";

    public static DataSource createDataSource(final String dataSourceName) {
        BasicDataSource result = new BasicDataSource();
        try {
            result.setDriverClassName(com.mysql.jdbc.Driver.class.getName());
            result.setUrl(String.format("jdbc:mysql://%s:%s/%s", HOST, PORT, dataSourceName));
            result.setUsername(USER_NAME);
            result.setPassword(PASSWORD);
        } catch (Exception e){
            logger.error("createDateSource error ",e);
        }

        return result;
    }
}
