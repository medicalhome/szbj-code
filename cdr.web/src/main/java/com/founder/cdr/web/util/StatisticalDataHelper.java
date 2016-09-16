package com.founder.cdr.web.util;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.web.util.AntUrlPathMatcher;
import org.springframework.security.web.util.UrlMatcher;
import org.springframework.stereotype.Component;

import com.founder.cdr.core.Constants;
import com.founder.cdr.entity.SystemImpressionLog;

/**
 * web应用服务器相关统计操作工具
 * @author jin_peng
 * @version 1.0, 2013/11/26
 */
@Component
public class StatisticalDataHelper
{
    private boolean stripQueryStringFromUrls;

    /**
     * 获取系统时间ms
     * @return 系统时间
     */
    public static long getSystemCurrentTime()
    {
        return System.currentTimeMillis();
    }

    /**
     * 过滤配置的url内容
     * @param url 待验证的url
     * @param noFilterList 过滤的url配置集合
     * @return 是否为过滤配置的url
     */
    public boolean isFiltratedOrNot(String url, List<String> noFilterList)
    {
        boolean isFiltrate = true;

        // 截取有效url
        if (stripQueryStringFromUrls)
        {
            int firstQuestionMarkIndex = url.indexOf("?");

            if (firstQuestionMarkIndex != -1)
            {
                url = url.substring(0, firstQuestionMarkIndex);
            }
        }

        UrlMatcher matcher = new AntUrlPathMatcher();

        // url匹配验证
        for (String pathPattern : noFilterList)
        {
            if (matcher.pathMatchesUrl(pathPattern, url))
            {
                isFiltrate = false;

                break;
            }
        }

        return isFiltrate;
    }

    /**
     * 记录请求响应内容
     * @param accountLogSn 用户日志序列号
     * @param requestUrl 请求地址
     * @param requestParams 请求参数
     * @param responseTimeMillis 响应完成毫秒
     * @param responseSystemTime 相应完成时间
     * @return 痕迹操作对象
     */
    public SystemImpressionLog getSystemImpressionLog(BigDecimal accountLogSn,
            String requestUrl, String requestParams,
            BigDecimal responseTimeMillis, Date responseSystemTime)
    {
        SystemImpressionLog systemImpression = new SystemImpressionLog();

        systemImpression.setAccountLogSn(accountLogSn);

        systemImpression.setRequestUrl(requestUrl);

        systemImpression.setRequestParams(requestParams);

        systemImpression.setResponseTimeMillis(responseTimeMillis);

        systemImpression.setCreateTime(responseSystemTime);

        systemImpression.setUpdateTime(responseSystemTime);

        systemImpression.setUpdateCount(Constants.INSERT_UPDATE_COUNT);

        systemImpression.setDeleteFlag(Constants.NO_DELETE_FLAG);

        return systemImpression;
    }

    /**
     * 获取请求地址
     * @param request 请求对象
     * @return 请求地址
     */
    public String getRequestUrl(HttpServletRequest request)
    {
        String requestUrl = request.getServletPath();

        return requestUrl;
    }

    /**
     * 获取请求参数
     * @param request 请求对象
     * @return 请求参数拼接内容 
     */
    public String getRequestParams(HttpServletRequest request)
    {
        StringBuffer sBuffer = new StringBuffer();

        @SuppressWarnings("unchecked")
        Map<String, String[]> requestParamsMap = request.getParameterMap();

        if (requestParamsMap != null && !requestParamsMap.isEmpty())
        {
            Set<Entry<String, String[]>> paramsSet = requestParamsMap.entrySet();
            Iterator<Entry<String, String[]>> paramsIterator = paramsSet.iterator();
            int count = 0;

            while (paramsIterator.hasNext())
            {
                Entry<String, String[]> paramsEntry = paramsIterator.next();

                String paramsKey = paramsEntry.getKey();
                String[] paramsValuesArray = paramsEntry.getValue();
                String paramsValues = "";

                if (paramsValuesArray != null && paramsValuesArray.length != 0)
                {
                    int paramsValuesCount = paramsValuesArray.length;

                    for (int v = 0; v < paramsValuesCount; v++)
                    {
                        paramsValues += paramsValuesArray[v];

                        if (v != (paramsValuesCount - 1))
                        {
                            paramsValues += ",";
                        }
                    }
                }

                if (count != 0)
                {
                    sBuffer.append("&");
                }

                sBuffer.append(paramsKey);
                sBuffer.append("=");
                sBuffer.append(paramsValues);

                count++;
            }
        }

        return sBuffer.toString();
    }

    /**
     * 获取请求处理及响应完成时间
     * @param beginTimeMillis 处理开始毫秒
     * @param endTimeMillis 处理结束毫秒
     * @return 处理毫秒
     */
    public BigDecimal getResponseTimeMillis(long beginTimeMillis,
            long endTimeMillis)
    {
        return new BigDecimal(endTimeMillis - beginTimeMillis);
    }

    public boolean isStripQueryStringFromUrls()
    {
        return stripQueryStringFromUrls;
    }

    public void setStripQueryStringFromUrls(boolean stripQueryStringFromUrls)
    {
        this.stripQueryStringFromUrls = stripQueryStringFromUrls;
    }

}
