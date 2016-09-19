package com.founder.cdr.service.impl;

import com.founder.cdr.dao.TestSqliteDao;
import com.founder.cdr.entity.TestSqlite;
import com.founder.cdr.service.TestSqliteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 类描述
 * Created by masong1 on 2016/9/6.
 */

@Component
public class TestSqliteServiceImpl implements TestSqliteService {
    @Autowired
    private TestSqliteDao testSqliteDao;

    @Transactional
    public List<TestSqlite> getTestSqliteByAge(int age) {
        return testSqliteDao.getTestSqliteByAge(age);
    }

    @Transactional
    public void insert(String name, Integer age, String address) {
        testSqliteDao.insert(name, age, address);
    }
}
