package com.yly.cdr.web.loginValidation;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;

import com.yly.cdr.core.Constants;
import com.yly.cdr.service.LoginService;
import com.yly.cdr.util.DateUtils;

/**
 * 用于用户退出时的日志记录操作
 * @author jin_peng
 * @version 1.0, 2012/10/30
 */
public class LoginSecurityContextLogoutHandler extends
        SecurityContextLogoutHandler
{
    private LoginService loginService;

    /**
     * @param request 上下文对象
     * @param response 响应对象
     * @param authentication 用户登陆信息对象
     */
    public void logout(HttpServletRequest request,
            HttpServletResponse response, Authentication authentication)
    {
        // 验证信息存在
        if (authentication != null)
        {
            // 获取用户信息
            LoginUserDetails userDetails = (LoginUserDetails) authentication.getPrincipal();

            // 因关闭浏览器则客户端页面消失所以不能使用跳转页面或重定向跳转的方式进行退出操作，需使用直接服务器端退出
            // 页面关闭时获取关闭方式标识
            String exitKindsName = request.getParameter("exitKindsName");

            // 关闭网页退出时设置退出方式名称
            if (Constants.EXIT_KINDS_FLAG_CLOSED.equals(exitKindsName))
            {
                userDetails.setExitKindsName(Constants.EXIT_KINDS_NAME_CLOSED);
            }

            // 记录用户退出系统日志信息
            loginService.recordSystemExitLog(userDetails.getExitKindsName(),
                    DateFormatUtils.format(DateUtils.getSystemTime(),
                            DateUtils.PATTERN_MINUS_DATETIME),
                    userDetails.getSequenceNo());
        }

        // 执行退出操作
        super.logout(request, response, authentication);
    }

    public LoginService getLoginService()
    {
        return loginService;
    }

    public void setLoginService(LoginService loginService)
    {
        this.loginService = loginService;
    }

}
