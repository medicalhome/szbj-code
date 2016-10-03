package com.yly.cdr.service;

import org.springframework.transaction.annotation.Transactional;

import com.yly.cdr.entity.TestSqlite;

import java.util.List;

/**
 * 类描述
 * Created by masong1 on 2016/9/6.
 */

public interface TestSqliteService {
    @Transactional
    public List<TestSqlite> getTestSqliteByAge(int age);
    @Transactional
    public void insert(String name, Integer age, String address);
}
