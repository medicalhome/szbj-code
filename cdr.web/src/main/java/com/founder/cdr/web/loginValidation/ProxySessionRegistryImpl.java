package com.founder.cdr.web.loginValidation;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistryImpl;

import com.founder.cdr.core.Constants;
import com.founder.cdr.service.LoginService;
import com.founder.cdr.util.DateUtils;
import com.founder.cdr.util.StringUtils;

/**
 * 用于session失效时用户退出时的日志记录操作
 * @author jin_peng
 * @version 1.0, 2012/10/31
 */
public class ProxySessionRegistryImpl extends SessionRegistryImpl
{
    private LoginService loginService;

    /**
     * session失效时记录用户日志信息
     * @param sessionId session唯一标识
     */
    public void removeSessionInformation(String sessionId)
    {
        // 通过标识用户登陆的sessionId获取相应用户的会话信息
        SessionInformation info = getSessionInformation(sessionId);

        // 如果会话信息为空证明为用户没有登陆注册该使用的session标识
        if (info == null)
        {
            return;
        }

        // 获取用户信息
        LoginUserDetails loginUserDetails = (LoginUserDetails) info.getPrincipal();

        // 如果用户信息存在并且是session失效导致的系统退出的情况下记录用户退出系统日志信息
        if (loginUserDetails != null
            && StringUtils.isEmpty(loginUserDetails.getExitKindsName()))
        {
            // 添加用户退出系统日志信息
            loginService.recordSystemExitLog(Constants.EXIT_KINDS_NAME_EXPIRY,
                    DateFormatUtils.format(DateUtils.getSystemTime(),
                            DateUtils.PATTERN_MINUS_DATETIME),
                    loginUserDetails.getSequenceNo());
        }

        // 执行该用户会话信息和用户信息的清除工作
        super.removeSessionInformation(sessionId);
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
