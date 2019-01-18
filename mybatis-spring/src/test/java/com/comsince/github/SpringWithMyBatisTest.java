package com.comsince.github;

import com.comsince.github.dao.ArticleDao;
import com.comsince.github.dao.AuthorDao;
import com.comsince.github.model.Article;
import com.comsince.github.model.Author;
import com.comsince.github.servive.AuthorService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import java.util.Arrays;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:application-mybatis.xml")
public class SpringWithMyBatisTest implements ApplicationContextAware {

    private ApplicationContext applicationContext;

    @Autowired
    private AuthorDao authorDao;

    @Autowired
    private ArticleDao articleDao;

    @Autowired
    private AuthorService authorService;

    @Before
    public void printBeanInfo() {
        ListableBeanFactory lbf = applicationContext;
        String[] beanNames = lbf.getBeanDefinitionNames();
        Arrays.sort(beanNames);

        System.out.println();
        System.out.println("----------------☆ bean name ☆---------------");
        Arrays.asList(beanNames).subList(0, 5).forEach(System.out::println);
        System.out.println();

        AuthorDao authorDao = (AuthorDao) applicationContext.getBean("authorDao");
        ArticleDao articleDao = (ArticleDao) applicationContext.getBean("articleDao");

        System.out.println("-------------☆ bean class info ☆--------------");
        System.out.println("StudentDao  Class: " + authorDao.getClass());
        System.out.println("ArticleDao Class: " + articleDao.getClass());
        System.out.println("\n--------xxxx---------xxxx---------xxxx--------\n");
    }


    @Test
    public void testOne2One() {
        Article article = articleDao.findOne(1);

        Author author = article.getAuthor();
        article.setAuthor(null);

        System.out.println();
        System.out.println("author info:");
        System.out.println(author);
        System.out.println();
        System.out.println("articles info:");
        System.out.println(article);
    }

    @Test
    public void testOne2Many() {
        Author author = authorDao.findOne(1);

        System.out.println();
        System.out.println("author info:");
        System.out.println(author);
        System.out.println();
        System.out.println("articles info:");
        author.getArticles().forEach(System.out::println);
    }

    @Test
    public void testGetAuthor(){
        Author author = authorService.findOne(1);
        System.out.println("author---> "+author);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
