package com.yly.cdr.util.dbhelper;

import com.founder.fasf.core.util.dbhelper.Dialect;

import java.util.UUID;

public class SQLiteDialect extends Dialect {
    public SQLiteDialect() {
    }

    protected String getLimitString(String query, boolean hasOffset) {
        return (new StringBuffer(query.length() + 21)).append(query).append(hasOffset?" LIMIT /*limit*/ OFFSET /*offset*/":" LIMIT /*limit*/").toString();
    }

    public boolean supportIdentity() {
        return true;
    }

    public String getSelectGUIDString() {
        return UUID.randomUUID().toString();
    }

}
