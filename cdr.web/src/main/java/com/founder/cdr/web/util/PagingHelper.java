package com.founder.cdr.web.util;

import java.io.UnsupportedEncodingException;
import java.util.Map;

import com.founder.cdr.core.AppSettings;
import com.founder.cdr.core.Constants;
import com.founder.cdr.util.StringUtils;
import com.founder.fasf.web.paging.PagingContext;

public class PagingHelper
{
    public static void initPagingContext(PagingContext context)
    {
        // 设置页面初始每页显示条目数
        int rowCount = 10;
        context.setRowCnt(rowCount);

        // 设置总页数
        int total = context.getTotalRowCnt();
        int pageCount = context.getTotalRowCnt() / rowCount;
        if (total % rowCount > 0)
            pageCount = pageCount + 1;
        context.setTotalPageCnt(pageCount);

        // 页码最多显示10页
        context.setPerPageCnt(10);
        int current = context.getPageNo();
        int start = context.getPageStartNo();
        int pageMax = context.getPerPageCnt();

        // 当直接输入的调整页码大于总页数时，始终显示最后一页
        if (current > pageCount)
        {
            current = pageCount;
            context.setPageNo(current);
        }

        // 当请求显示的当前页不在显示页码范围内时，移动页码显示的开始值
        if (start + pageMax - 1 < current)
        {
            if (current + context.getPerPageCnt() - 1 > pageCount)
                context.setPageStartNo(current - context.getPerPageCnt() + 1);
            else
                context.setPageStartNo(current);
        }
    }

    /**
     * 默认情况下处理分页中页码显示部分
     * @param context 传入分页对象
     * @param appSettings 设置参数属性对象
     */
    public static void initPaging(PagingContext context)
    {
        // 从参数属性文件中获取初始页码最多显示值与页码显示增量
        String pagingDisplayCount = AppSettings.getConfig(Constants.CFG_PAGING_DISPLAY_COUNT);
        String pagingAddCount = AppSettings.getConfig(Constants.CFG_PAGING_ADD_COUNT);

        int perPageCount = Integer.parseInt(pagingDisplayCount);
        int addCount = Integer.parseInt(pagingAddCount);

        initProcessPaging(context, perPageCount, addCount);
    }

    /**
     * 有特殊需求下处理分页中页码显示部分
     * @param context 传入分页对象
     * @param perPageCount 页码最多显示值
     * @param addCount 页码显示增量
     */
    public static void initPaging(PagingContext context, int perPageCount,
            int addCount)
    {
        initProcessPaging(context, perPageCount, addCount);
    }

    /**
     * 处理分页中页码显示部分
     * @param context 传入分页对象
     * @param perPageCount 页码最多显示值
     * @param addCount 页码显示增量
     */
    private static void initProcessPaging(PagingContext context,
            int perPageCount, int addCount)
    {
        // 当前页码
        int current = context.getPageNo();
        // 开始显示页码
        int start = context.getPageStartNo();
        // 结束显示页码
        int end = perPageCount;
        // 总页数
        int totalPageCount = context.getTotalPageCnt();
        // 开始页码变化触发点
        int tiggerPoint = perPageCount - addCount + 1;

        // 当页面有数据页码大于0时进行页码部分处理操作，否则开始页，结束页都为0
        if (current > 0)
        {
            // 当总页数小于等于页码显示最大数时，开始页总为1，结束页总为总页数
            if (totalPageCount <= perPageCount)
            {
                // 设置结束页为总页数
                end = totalPageCount;
            }
            else
            {
                // 当前页如果大于等于触发点时进行增量操作
                if (current >= tiggerPoint)
                {
                    // 增加增量的结束页数
                    end = current + addCount;

                    // 如果进行增量处理完成的结束页大于总页数时进行如下操作
                    if (end > totalPageCount)
                    {
                        // 进行运算设置开始页
                        start = current
                            - (perPageCount - (totalPageCount - current)) + 1;
                        // 这时结束页总为总页数
                        end = totalPageCount;
                    }
                    else
                    {
                        // 如果结束页小于等于总页数时进行正常增量操作
                        start = current - (perPageCount - addCount) + 1;
                    }
                }
            }
        }
        else
        {
            // 当页码为小于0无记录时，开始页，结束页都为0
            start = 0;
            end = 0;
        }

        // 将处理完成的开始页码和结束页码装入分页处理对象，传回页面
        context.setPageStartNo(start);
        context.setPerPageCnt(end);
    }

    // 动态加载检索框-共同代码
    public static String[] condition(String par)
    {
        String[] ary = new String[] { "off", "on" };

        if (!StringUtils.isEmpty(par))
        {
            ary[0] = par;
            if ("off".equals(par))
            {
                ary[1] = "on";
            }
            else
            {
                ary[1] = "off";
            }

        }
        return ary;
    }

    // 转码处理
    public static String convertEncoding(String par)
    {
        String result;
        try
        {
            result = new String(par.getBytes("ISO-8859-1"), "gb2312");
        }
        catch (UnsupportedEncodingException e)
        {
            e.printStackTrace();
            result = null;
        }
        return result;
    }

    //
    public static String parameterValueGet(Map<String, String[]> parameterMap,
            String key)
    {
        String result;
        String[] value = (String[]) parameterMap.get(key);
        result = String.valueOf(value[0]);
        return result;
    }

}
