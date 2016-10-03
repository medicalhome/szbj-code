package com.yly.cdr.core;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.logging.Logger;

import javax.sql.DataSource;

import org.springframework.jdbc.datasource.DataSourceUtils;

public class SpringDataSourceProxy implements DataSource
{
    private DataSource target;
    
    public DataSource getTarget()
    {
        return target;
    }

    public void setTarget(DataSource target)
    {
        this.target = target;
    }

    @Override
    public PrintWriter getLogWriter() throws SQLException
    {
        return this.target.getLogWriter();
    }

    @Override
    public void setLogWriter(PrintWriter out) throws SQLException
    {
        this.target.setLogWriter(out);
    }

    @Override
    public void setLoginTimeout(int seconds) throws SQLException
    {
        this.target.setLoginTimeout(seconds);
    }

    @Override
    public int getLoginTimeout() throws SQLException
    {
        return this.target.getLoginTimeout();
    }

    public Logger getParentLogger() throws SQLFeatureNotSupportedException
    {
        throw new SQLFeatureNotSupportedException();
    }

    @Override
    public <T> T unwrap(Class<T> iface) throws SQLException
    {
        return null;
    }

    @Override
    public boolean isWrapperFor(Class<?> iface) throws SQLException
    {
        return false;
    }

    @Override
    public Connection getConnection() throws SQLException
    {
        return DataSourceUtils.getConnection(target);
    }

    @Override
    public Connection getConnection(String username, String password) throws SQLException
    {
        return this.target.getConnection(username, password);
    }
}
