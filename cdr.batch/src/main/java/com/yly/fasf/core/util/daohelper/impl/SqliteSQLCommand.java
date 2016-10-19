package com.yly.fasf.core.util.daohelper.impl;

import com.founder.fasf.core.util.convert.ConversionUtils;
import com.founder.fasf.core.util.daohelper.SQLTemplateMapping;
import com.founder.fasf.core.util.daohelper.Session;
import com.founder.fasf.core.util.daohelper.SqlCommandDesc;
import com.founder.fasf.core.util.daohelper.impl.AbstractSqlCommand;
import com.founder.fasf.core.util.daohelper.impl.BeanPropertyRowMapper;
import com.founder.fasf.core.util.daohelper.impl.ExtendedColumnMapRowMapper;
import com.founder.fasf.core.util.daohelper.impl.FileSQLTemplateMapping;
import com.founder.sqlmaker.SqlMaker;
import com.yly.cdr.util.RWLock;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SingleColumnRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 类描述
 * Created by on 2016/9/15.
 */

public class SqliteSQLCommand extends AbstractSqlCommand {
    private SQLTemplateMapping templateMapping = new FileSQLTemplateMapping();
    private SqlCommandDesc desc;
    @Autowired
    private RWLock rwlock;

    public SqliteSQLCommand() {
    }

    public SqliteSQLCommand(Session session, SqlCommandDesc desc) {
        super(session, desc);
        this.desc = desc;
    }

    public void init(Session session, SqlCommandDesc desc) {
        super.init(session, desc);
        this.desc = desc;
    }

    protected Object executeSql(String sql, DataSource dataSource) {
        // 读写锁
        Object retObj = null;
        if (this.desc.getName().matches("^(select|get|query)\\w*")) {
            rwlock.getRWLockInstance().readLock().lock();
            retObj = this.query(sql, dataSource);
            rwlock.getRWLockInstance().readLock().unlock();
        }else{
            rwlock.getRWLockInstance().writeLock().lock();
            try {
                retObj = Integer.valueOf(this.getSimpleJdbcTemplate(dataSource).update(sql, new Object[0]));
            } catch (DataAccessException e) {
                System.out.println("###################################################"+
                        e.getCause().getLocalizedMessage().equalsIgnoreCase("database is locked"));
            } finally {
                rwlock.getRWLockInstance().writeLock().unlock();
            }
        }
        return retObj;
    }

    private Object query(String sql, DataSource dataSource) {
        SimpleJdbcTemplate simpleJdbcTemplate = this.getSimpleJdbcTemplate(dataSource);
        Class returnType = this.getReturnType();
        Long ret = null;
        if(!returnType.isPrimitive() && !Number.class.isAssignableFrom(returnType)) {
            Class mappedClass = this.elementType;
            if(mappedClass == null) {
                mappedClass = returnType;
            }

            RowMapper mapper = this.getMapper(mappedClass);
            List resultList = simpleJdbcTemplate.query(sql, mapper, new Object[0]);
            return this.elementType != null?(returnType.isArray()? ConversionUtils.convert(resultList, returnType):resultList):(resultList != null && resultList.size() > 0?resultList.get(0):null);
        } else {
            ret = Long.valueOf(simpleJdbcTemplate.queryForLong(sql, new Object[0]));
            return ConversionUtils.convert(ret, returnType);
        }
    }

    private RowMapper<?> getMapper(Class<?> mappedClass) {
        Object mapper = null;
        if(!mappedClass.isPrimitive() && !Number.class.isAssignableFrom(mappedClass) && !String.class.isAssignableFrom(mappedClass)) {
            if(Map.class.isAssignableFrom(mappedClass)) {
                mapper = new ExtendedColumnMapRowMapper();
            } else {
                mapper = new BeanPropertyRowMapper(mappedClass);
            }
        } else {
            mapper = new SingleColumnRowMapper();
        }

        return (RowMapper)mapper;
    }

    protected String getSql(Map<String, Object> args) {
        String sql = this.getSQLTemplate(args);
        SqlMaker maker = new SqlMaker(sql);

        Map.Entry arg;
        Object val;
        Class clazz;
        for(Iterator i$ = args.entrySet().iterator(); i$.hasNext(); maker.addArg((String)arg.getKey(), val, clazz)) {
            arg = (Map.Entry)i$.next();
            val = arg.getValue();
            clazz = null;
            if(val != null) {
                clazz = val.getClass();
            }
        }

        sql = maker.getSql();
        return sql;
    }

    protected Map<String, Object> assembleSQLArgs(Object[] args) {
        HashMap ret = new HashMap();
        String[] argNames = this.desc.getArgumentNames();
        if(args != null && argNames != null) {
            if(argNames.length > args.length) {
                throw new IllegalArgumentException("unmatch arguments and annotation");
            }

            for(int i = 0; i < argNames.length; ++i) {
                ret.put(argNames[i], args[i]);
            }
        }

        return ret;
    }

    protected void validateArguments(Object[] args) {
    }

    protected String getSQLTemplate(Map<String, Object> args) {
        return this.templateMapping.getSQLTemplate(this.desc);
    }

    protected Class<?> getReturnType() {
        return this.desc.getReturnType();
    }
}
