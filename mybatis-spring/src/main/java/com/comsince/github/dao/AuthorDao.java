package com.comsince.github.dao;

import com.comsince.github.model.Author;
import org.apache.ibatis.annotations.Param;

/**
 * Created by root on 19-1-18.
 */
public interface AuthorDao {
    Author findOne(@Param("id") int id);
}
