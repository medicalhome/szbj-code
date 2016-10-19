package com.yly.cdr.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.yly.cdr.dao.TestSqliteDao;
import com.yly.cdr.entity.TestSqlite;
import com.yly.cdr.service.TestSqliteService;

import java.util.List;

/**
 * 类描述
 * Created by on 2016/9/6.
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
