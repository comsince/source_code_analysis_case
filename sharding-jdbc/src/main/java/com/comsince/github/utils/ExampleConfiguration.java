package com.comsince.github.utils;

import javax.sql.DataSource;
import java.sql.SQLException;

/**
 * @author comsicne
 *         Copyright (c) [2019] [Meizu.inc]
 * @Time 19-1-28 上午11:40
 **/
public interface ExampleConfiguration {
    DataSource getDataSource() throws SQLException;
}
