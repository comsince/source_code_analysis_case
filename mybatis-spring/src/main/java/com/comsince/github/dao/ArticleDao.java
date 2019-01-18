package com.comsince.github.dao;

import com.comsince.github.model.Article;
import org.apache.ibatis.annotations.Param;

/**
 * Created by comsince
 */
public interface ArticleDao {

    Article findOne(@Param("id") int id);

}
