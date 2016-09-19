package com.founder.cdr.dao;

import com.founder.cdr.entity.TestSqlite;
import com.founder.fasf.core.util.daohelper.annotation.Arguments;

import java.math.BigDecimal;
import java.util.List;

/**
 * 类描述
 * Created by masong1 on 2016/9/6.
 */

public interface TestSqliteDao {
    @Arguments({ "age" })
    public List<TestSqlite> getTestSqliteByAge(int age);

    @Arguments({ "name", "age", "address"})
    public void insert(String name, Integer age, String address);
}
