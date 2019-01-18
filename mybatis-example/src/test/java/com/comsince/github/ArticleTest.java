package com.comsince.github;


import com.comsince.github.model.Article;
import com.comsince.github.dao.ArticleDaO;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by root on 19-1-18.
 */
public class ArticleTest {
    private SqlSessionFactory sqlSessionFactory;
    private Logger logger = LoggerFactory.getLogger(ArticleTest.class);

    @Before
    public void prepare() throws IOException {
        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        inputStream.close();
    }

    @Test
    public void testMyBatis() throws IOException {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            ArticleDaO articleDao = session.getMapper(ArticleDaO.class);
            Article article = articleDao.
                    findOne(1);
            logger.info("articles {}",article);
        } finally {
            session.commit();
            session.close();
        }
    }
}
