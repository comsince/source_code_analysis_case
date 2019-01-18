package com.comsince.github.servive.impl;

import com.comsince.github.dao.AuthorDao;
import com.comsince.github.model.Author;
import com.comsince.github.servive.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by root on 19-1-18.
 */
@Service
@Transactional
public class AuthorServiceImpl implements AuthorService{
    @Autowired
    private AuthorDao authorDao;

    @Override
    public Author findOne(int id) {
        return authorDao.findOne(id);
    }
}
