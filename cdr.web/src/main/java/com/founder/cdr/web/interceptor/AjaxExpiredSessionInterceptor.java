package com.founder.cdr.web.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

/**
 * 验证session是否过期
 * @author wu_jianfeng
 * @version 1.0, 2012/06/11
 */
public class AjaxExpiredSessionInterceptor extends HandlerInterceptorAdapter
{
    @Override
    public boolean preHandle(HttpServletRequest request,
            HttpServletResponse response, Object handler) throws Exception
    {
        // 如果是ajax请求响应头会有x-requested-with
        if (request.getHeader("x-requested-with") != null
            && request.getHeader("x-requested-with").equalsIgnoreCase(
                    "XMLHttpRequest"))
        {
            boolean sessionInvalid = (null != request.getRequestedSessionId())
                && !request.isRequestedSessionIdValid();
            // session无效
            if (sessionInvalid)
            {
                // 在响应头设置session状态
                response.setHeader("sessionStatus", "timeout");
                return false;
            }
        }

        return true;
    }
}
