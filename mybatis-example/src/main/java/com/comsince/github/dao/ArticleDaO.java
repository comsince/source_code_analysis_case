package com.comsince.github.dao;

import com.comsince.github.model.Article;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by comsince
 */
public interface ArticleDaO {

    Article findOne(@Param("id") int id);

}
