package com.yly.fasf.web.interceptor;

import com.founder.fasf.core.util.daohelper.SQLCommandFactory;
import com.founder.fasf.core.util.daohelper.SqlCommand;
import com.founder.fasf.core.util.daohelper.SqlCommandDesc;
import com.founder.fasf.core.util.daohelper.impl.CountSQLCommand;
import com.founder.fasf.core.util.daohelper.impl.MethodSqlCommandDescBuilder;
import com.founder.fasf.core.util.daohelper.impl.PagingSQLCommand;
import com.founder.fasf.core.util.daohelper.impl.SortingSQLCommand;
import com.founder.fasf.web.interceptor.WebSQLDaoInterceptor;
import com.founder.fasf.web.paging.PagingContext;
import com.founder.fasf.web.sorting.SortingContext;
import com.yly.fasf.core.util.daohelper.impl.*;

import org.aopalliance.intercept.MethodInvocation;
import org.apache.commons.lang3.ArrayUtils;

import java.lang.reflect.Method;

/**
 * 类描述
 * Created by on 2016/9/18.
 */

public class WebSqliteSQLDaoInterceptor extends WebSQLDaoInterceptor {

    public WebSqliteSQLDaoInterceptor(SQLCommandFactory sqlCommandFactory) {
        super(sqlCommandFactory);
    }

    public Object invoke(MethodInvocation invocation) throws Throwable {
        Method method = invocation.getMethod();
        PagingContext pagingContext = this.abstractPagingContext(invocation);
        if(pagingContext != null && pagingContext.isPagingEnabled()) {
            Object[] builder2 = invocation.getArguments();
            builder2 = ArrayUtils.subarray(builder2, 0, builder2.length - 1);
            int desc2 = ((Integer)this.getTotalRecordCnt(method, builder2)).intValue();
            pagingContext.setTotalRowCnt(desc2);
            int cmd2 = pagingContext.getPageNo();
            if(cmd2 < 1) {
                cmd2 = 1;
            }

            int rowCnt = pagingContext.getRowCnt();
            if(rowCnt <= 0) {
                return null;
            } else {
                int totalPageCnt = desc2 / rowCnt;
                totalPageCnt = desc2 % rowCnt > 0?totalPageCnt + 1:totalPageCnt;
                pagingContext.setTotalPageCnt(totalPageCnt);
                int perPageCnt = pagingContext.getPerPageCnt();
                if(perPageCnt <= 0) {
                    return null;
                } else {
                    int pageStartNo = cmd2 % perPageCnt > 0?cmd2 / perPageCnt:cmd2 / perPageCnt - 1;
                    pageStartNo = pageStartNo * perPageCnt + 1;
                    pagingContext.setPageStartNo(pageStartNo);
                    int offset = cmd2 * rowCnt - rowCnt;
                    if(offset >= desc2) {
                        offset = desc2 - 1;
                    }

                    SortingContext sortingContext = this.abstractSortingContext(invocation);
                    SqlCommand cmd1 = null;
                    Object[] newArgs = null;
                    MethodSqlCommandDescBuilder builder1;
                    SqlCommandDesc desc1;
                    if(sortingContext != null) {
                        newArgs = ArrayUtils.addAll(builder2, new Object[]{sortingContext.getSortIdx(), sortingContext.getSortOrder(), Integer.valueOf(offset), Integer.valueOf(rowCnt)});
                        builder1 = new MethodSqlCommandDescBuilder(method, SortingSQLCommand.class);
                        desc1 = builder1.build();
                        cmd1 = this.sqlCommandFactory.getSQLCommand(desc1);
                    } else {
                        newArgs = ArrayUtils.addAll(builder2, new Object[]{Integer.valueOf(offset), Integer.valueOf(rowCnt)});
                        builder1 = new MethodSqlCommandDescBuilder(method, PagingSQLCommand.class);
                        desc1 = builder1.build();
                        cmd1 = this.sqlCommandFactory.getSQLCommand(desc1);
                    }

                    return cmd1.execute(newArgs);
                }
            }
        } else {
            MethodSqlCommandDescBuilder builder = new MethodSqlCommandDescBuilder(method, SqliteSQLCommand.class);
            SqlCommandDesc desc = builder.build();
            SqlCommand cmd = this.sqlCommandFactory.getSQLCommand(desc);
            return cmd.execute(invocation.getArguments());
        }
    }

    private Object getTotalRecordCnt(Method method, Object[] args) {
        MethodSqlCommandDescBuilder builder = new MethodSqlCommandDescBuilder(method, CountSQLCommand.class);
        SqlCommandDesc desc = builder.build();
        SqlCommand cmd = this.sqlCommandFactory.getSQLCommand(desc);
        return cmd.execute(args);
    }
}
