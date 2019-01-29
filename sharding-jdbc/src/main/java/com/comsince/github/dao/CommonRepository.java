package com.comsince.github.dao;

import java.util.List;

/**
 * @author comsicne
 *         Copyright (c) [2019] [Meizu.inc]
 * @Time 19-1-28 下午2:31
 **/
public interface CommonRepository<T> {
    void createTableIfNotExists();

    void dropTable();

    void truncateTable();

    Long insert(T entity);

    void delete(Long id);

    List<T> selectAll();

    List<T> selectRange();
}
