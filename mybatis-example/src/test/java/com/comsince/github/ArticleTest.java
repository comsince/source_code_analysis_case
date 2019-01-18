package com.comsince.github;


import com.comsince.github.model.Article;
import com.comsince.github.dao.ArticleDao;
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


public class ArticleTest {
    private SqlSessionFactory sqlSessionFactory;
    private Logger logger = LoggerFactory.getLogger(ArticleTest.class);

    @Before
    public void prepare() throws IOException {
        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
    }

    @Test
    public void testMyBatis() throws IOException {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            ArticleDao articleDao = session.getMapper(ArticleDao.class);
            Article article = articleDao.findOne(1);
            logger.info("articles {}",article);
        } finally {
            session.commit();
            session.close();
        }
    }

    @Test
    public void testSelectOne(){
        SqlSession sqlSession = sqlSessionFactory.openSession();
        try {
            Article article = sqlSession.selectOne("com.comsince.github.dao.ArticleDao.findOne",1);
            logger.info("select one article {}",article);
        } catch (Exception e){

        }finally {
           sqlSession.close();
        }
    }
}
