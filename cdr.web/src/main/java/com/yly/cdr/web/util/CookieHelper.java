package com.yly.cdr.web.util;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.yly.cdr.util.StringUtils;

/**
 * cookie操作工具
 * @author jin_peng
 * @version 1.0, 2013/11/25
 */
public class CookieHelper
{
    /**
    * 设置cookie
    * @param response
    * @param name  cookie名字
    * @param value cookie值
    * @param maxAge cookie生命周期  以秒为单位
    */
    public static void addCookie(HttpServletResponse response, String name,
            String value, int maxAge)
    {
        Cookie cookie = new Cookie(name, value);
        cookie.setPath("/");

        if (maxAge > 0)
        {
            cookie.setMaxAge(maxAge);
        }

        response.addCookie(cookie);
    }

    /**
    * 根据名字获取cookie
    * @param request
    * @param name cookie名字
    * @return
    */
    public static Cookie getCookieByName(HttpServletRequest request, String name)
    {
        Map<String, Cookie> cookieMap = ReadCookieMap(request);

        if (cookieMap.containsKey(name))
        {
            Cookie cookie = (Cookie) cookieMap.get(name);

            return cookie;
        }

        return null;
    }

    /**
    * 将cookie封装到Map里面
    * @param request
    * @return
    */
    private static Map<String, Cookie> ReadCookieMap(HttpServletRequest request)
    {
        Map<String, Cookie> cookieMap = new HashMap<String, Cookie>();
        Cookie[] cookies = request.getCookies();

        if (cookies != null)
        {
            for (Cookie cookie : cookies)
            {
                cookieMap.put(cookie.getName(), cookie);
            }
        }

        return cookieMap;
    }

    /**  
    * 清除cookie  
    * @param res 
    * @param name  
    */
    public static void removeCookieByName(HttpServletRequest request,
            HttpServletResponse response, String cookieName)
    {
        if (!StringUtils.isEmpty(cookieName))
        {
            Cookie cookieFormer = getCookieByName(request, cookieName);

            if (cookieFormer != null)
            {
                Cookie cookie = new Cookie(cookieName, null);

                cookie.setMaxAge(0);

                cookie.setPath("/");

                response.addCookie(cookie);
            }
        }
    }

}
