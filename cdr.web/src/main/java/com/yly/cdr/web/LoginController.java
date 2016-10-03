package com.yly.cdr.web;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import com.yly.cdr.core.Constants;
import com.yly.cdr.dto.LoginDto;
import com.yly.cdr.entity.SystemAccountLog;
import com.yly.cdr.service.CommonService;
import com.yly.cdr.service.LoginService;
import com.yly.cdr.util.StringUtils;
import com.yly.cdr.web.loginValidation.LoginUserDetails;
import com.yly.cdr.web.util.CookieHelper;
import com.yly.cdr.web.util.LoginUtil;

/**
 * 登录页面
 * @author jin_peng
 * @version 1.0, 2012/07/17
 */
@Controller
@RequestMapping("/login")
public class LoginController
{
    // $Author :jin_peng
    // $Date : 2012/09/21 14:01$
    // [BUG]0009561 ADD BEGIN
    private LoginService loginService;

    // [BUG]0009561 ADD END

    // $Author :jin_peng
    // $Date : 2012/10/30 14:40$
    // [BUG]0010836 ADD BEGIN
    // 通用service
    private CommonService commonService;

    private static Logger logger = LoggerFactory.getLogger(LoginController.class);

    // [BUG]0010836 ADD END

    /**
     * 登陆安全验证过后进行用户名及错误信息提取
     * @param request 页面上下文对象
     * @return 
     */
    @RequestMapping("/login")
    public ModelAndView init(WebRequest request, HttpServletResponse response) throws Exception
    {
        // 验证错误标识
        String status = request.getParameter("status");
        // 错误信息
        String errorMessage = null;
        // 登陆失败日志输出
        String errorMessageTips = null;
        // 用户名
        String userName = null;
        // 错误信息对象
        Object error = null;
        // 用户对象
        Object user = null;

        // $Author: jin_peng
        // $Date : 2013/09/12 15:37
        // $[BUG]0036478 ADD BEGIN
        // 是否需要密码重置 0不需要 1需要
        String isPasswordReset = LoginDto.NO_PASSWORD_RESET;

        // $[BUG]0036478 ADD END

        // $Author: jin_peng
        // $Date : 2013/09/12 15:37
        // $[BUG]0036478 ADD BEGIN
        userName = request.getParameter("userName");

        // $[BUG]0036478 ADD END

        // 当验证失败时获取错误信息及相应用户名
        if (LoginDto.ERROR_LOGIN.equals(status))
        {
            // 获取存储的用户信息对象
            RequestAttributes attrs = RequestContextHolder.getRequestAttributes();

            // 获取用户对象
            user = attrs.getAttribute(
                    UsernamePasswordAuthenticationFilter.SPRING_SECURITY_LAST_USERNAME_KEY,
                    RequestAttributes.SCOPE_SESSION);

            // 获取错误信息对象
            error = attrs.getAttribute(WebAttributes.AUTHENTICATION_EXCEPTION,
                    RequestAttributes.SCOPE_SESSION);

            // 获取用户名
            userName = String.valueOf(attrs.getAttribute(
                    UsernamePasswordAuthenticationFilter.SPRING_SECURITY_LAST_USERNAME_KEY,
                    RequestAttributes.SCOPE_SESSION));

            // 获取错误信息
            errorMessage = String.valueOf(attrs.getAttribute(
                    WebAttributes.AUTHENTICATION_EXCEPTION,
                    RequestAttributes.SCOPE_SESSION));

            // 根据错误信息获取相应错误提示
            if (LoginDto.NO_EXISTS_EXCEPTION.equals(errorMessage))
            {
                // 用户名不存在
                errorMessage = LoginDto.NO_EXISTS_USER;

                errorMessageTips = "user_not_Found";
            }
            else if (LoginDto.NO_CORRECT_EXCEPTION.equals(errorMessage))
            {
                // 密码输入错误
                errorMessage = LoginDto.NO_CORRECT_PASSWORD;

                errorMessageTips = "password_is_incorrect";
            }
            else if (LoginDto.NOT_ACTIVE_EXCEPTION.equals(errorMessage))
            {
                // 用户未激活
                errorMessage = LoginDto.USER_NOT_ACTIVE;

                errorMessageTips = "user_is_inactive";
            }
            else if (LoginDto.NOT_DOCTOR_EXCEPTION.equals(errorMessage))
            {
                // 用户未激活
                errorMessage = LoginDto.USER_NOT_DOCTOR;

                errorMessageTips = "user_is_not_a_doctor";
            }

            // $Author: jin_peng
            // $Date : 2013/09/12 15:37
            // $[BUG]0036478 ADD BEGIN
            else if (LoginDto.REQUIRE_ALTER_PASSWORD_EXCEPTION.equals(errorMessage))
            {
                // 修改密码提示
                errorMessage = LoginDto.REQUIRE_ALTER_PASSWORD;

                errorMessageTips = "password_alteration_is_required";

                // 需要密码重置
                isPasswordReset = LoginDto.PASSWORD_RESET;
            }

            // $[BUG]0036478 ADD END

            // 进入错误页面时如果用户没有登录过则为空
            if (user == null && error == null)
            {
                userName = "";
                errorMessage = "";
            }
        }

       
        ModelAndView mav = new ModelAndView();
        mav.addObject("userName", userName);
        mav.addObject("errorMessage", errorMessage);
        mav.addObject("errorMessageTips", errorMessageTips);
        mav.addObject("status", status);

        // $Author: yang_jianbo
        // $Date : 2014/08/06 18:37
        // $[BUG]0047263 ADD BEGIN
        // 允许医院定制自己的登录背景图片
        String loginBgPic = Constants.COMPANY_LOGIN_PIC;
        //web服务启动时，如果properties目录下存在定制的图片，会按照用户配置的路径复制到项目中，读取项目
        if(!StringUtils.isEmpty(Constants.HOSPITAL_LOGIN_PIC)){
             //获取cdr项目所在的根路径
             String webPath = new File(this.getClass().getResource("/").getPath()).getAbsolutePath();
             //处理windows与linux之间的差异
             if(webPath.contains("WEB-INF")){
             	webPath = webPath.substring(0,webPath.indexOf("WEB-INF"));
             }
             
             String picPath = Constants.HOSPITAL_PIC_FOLDER + "/" + Constants.HOSPITAL_LOGIN_PIC;
        	
        	 File file = new File(webPath + picPath);
        	 if(file.exists()&&file.isFile()){
        		 loginBgPic = picPath;
        	 }
        }
        mav.addObject("loginBgPic",loginBgPic);
        // $[BUG]0047263 ADD END
        
        
        // $Author: jin_peng
        // $Date : 2013/09/12 15:37
        // $[BUG]0036478 ADD BEGIN
        // 是否重置密码标识
        mav.addObject("isPasswordReset", isPasswordReset);

        // $[BUG]0036478 ADD END

        // 登陆失败加入log日志
        if ("error".equals(status))
        {
            if (userName != null && userName.indexOf("&#64;") >= 0)
            {
                userName = userName.substring(0, userName.indexOf("&#64;"));

                logger.debug("login_tips: 用户{}登陆系统失败，原因：{}", userName,
                        errorMessage);
            }
        }

        // $Author :jin_peng
        // $Date : 2012/09/21 14:01$
        // [BUG]0009561 MODIFY BEGIN
        // 登陆集成情况与内部返回不同页面
        if (LoginDto.NO_GET_THROUGH_EXCEPTION.equals(errorMessage))
        {
            mav.setViewName("/errorIntegrated");
        }
        else if (LoginDto.NO_LOGIN_LOG_STATUS.equals(status))
        {
            return null;
        }
        else
        {
            mav.setViewName("login/login");
        }
        // [BUG]0009561 MODIFY END

        return mav;
    }

    /**
     * 登陆安全验证通过进入选择患者页面
     * @return 
     */
    @RequestMapping("/loginValidationSuccess")
    public ModelAndView initValidation(HttpServletRequest request,
            HttpServletResponse response) throws Exception
    {
        // $Author :jin_peng
        // $Date : 2012/09/21 14:01$
        // [BUG]0009561 ADD BEGIN
        // 根据患者本地ID，域ID查询相应患者的内部序列号
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        LoginUserDetails userDetails = (LoginUserDetails) authentication.getPrincipal();
        // [BUG]0009561 ADD END

        // $Author :jin_peng
        // $Date : 2012/10/29 17:38$
        // [BUG]0010836 ADD BEGIN
        // 记录用户登陆日志
        if (userDetails.isLogin())
        {
            logger.debug("添加用户活动日志");

            // 该标识验证用户为从登陆页面或集成登陆进入
            userDetails.setLogin(false);

            SystemAccountLog systemAccountLog = LoginUtil.getSystemAccountLog(request,
                    userDetails, userDetails.getDomainId());

            // 记录用户登陆日志
            commonService.save(systemAccountLog);

            // $Author :jin_peng
            // $Date : 2013/11/28 10:07$
            // [BUG]0038987 ADD BEGIN
            // 只对页面登陆提供操作登陆相关信息功能
            if (userDetails.isLaunchedLoginInfo())
            {
                processLoginInfo(request, response, userDetails);
            }

            // [BUG]0038987 ADD END
        }

        // [BUG]0010836 ADD END

        ModelAndView mav = new ModelAndView();

        // $Author :jin_peng
        // $Date : 2012/09/21 14:01$
        // [BUG]0009561 MODIFY BEGIN
        // 针对登陆集成跳转不同页面
        if (!StringUtils.isEmpty(userDetails.getPatientId()))
        {
            // 通过患者ID和域ID获取患者内部序列号
            LoginDto loginDto = loginService.selectPatient(
                    userDetails.getPatientId(), userDetails.getDomainId());

            mav.addObject("patientSn",
                    loginDto == null ? "" : loginDto.getPatientSn());
            mav.addObject("patientId", userDetails.getPatientId());
            mav.addObject("patientDomain", userDetails.getDomainId());
            mav.addObject("inpatientType",userDetails.getInpatientType());
            mav.addObject("sceneType",userDetails.getSceneType());
            //wang.meng emr3.0及输液统计
            mav.addObject("systemId",userDetails.getSystemId());
            mav.addObject("viewId",userDetails.getViewId());
            mav.addObject("visitTimes", userDetails.getVisitTimes());
            mav.setViewName("login/successIntegrated");
        }
        // 针对HIS集成检验报告URL
        else if (!StringUtils.isEmpty(userDetails.getLabReportLid()))
        {
            mav.addObject("labReportLid", userDetails.getLabReportLid());
            mav.addObject("labItemCode", userDetails.getLabItemCode());
            mav.addObject("reportTypeCode", userDetails.getReportTypeCode());
            // $Author :chang_xuewen
            // $Date : 2014/01/27 10:00$
            // [BUG]0041864 ADD BEGIN
            mav.addObject("orgCode", userDetails.getOrgCode());
            // [BUG]0041864 ADD BEGIN
            mav.setViewName("login/successIntegrated");
        }
        else if (!StringUtils.isEmpty(userDetails.getBusinessSn()))
        {
            mav.addObject("businessSn", userDetails.getBusinessSn());

            mav.setViewName("login/successIntegrated");
        }
        else
        {
            // $Author :jin_peng
            // $Date : 2012/11/09 17:51$
            // [BUG]0010795 ADD BEGIN
            // 添加域id来显示相应患者页面
            mav.addObject("patientDomain", userDetails.getDomainId());

            // [BUG]0010795 ADD END

            mav.setViewName("login/mainPatient");
        }
        // [BUG]0009561 MODIFY END

        return mav;
    }

    /**
     * 用户登陆信息操作
     * @param request 请求对象
     * @param response 响应对象
     * @param userDetails 登陆信息
     */
    private void processLoginInfo(HttpServletRequest request,
            HttpServletResponse response, LoginUserDetails userDetails)
    {
        int maxAge = Integer.parseInt(Constants.COOKIE_EXPIRE);

        String username = userDetails.getUsername();
        String passwordInput = userDetails.getPasswordInput();
        String firstLoginInfo = username + "," + passwordInput;

        // 判断登陆是否选择记住登陆相关信息
        if (LoginDto.SAVE_LOGIN_INFO.equals(userDetails.getSaveLoginInfoFlag()))
        {
            // 记录初次登陆信息
            CookieHelper.addCookie(response, LoginDto.FIRST_LOGIN_COOKIE_NAME,
                    firstLoginInfo, maxAge);

            // 操作登陆信息
            CookieHelper.addCookie(response, username, firstLoginInfo, maxAge);
        }
        else
        {
            // 移除初次登陆信息
            CookieHelper.removeCookieByName(request, response,
                    LoginDto.FIRST_LOGIN_COOKIE_NAME);

            // 移除登陆信息
            CookieHelper.removeCookieByName(request, response, username);
        }
    }

    /**
     * 用户未登录或session过期后返回登录页面
     * @return 
     */
    @RequestMapping("/loginunauthorized")
    public ModelAndView loginUnauthorized() throws Exception
    {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("/errorExpiry");

        return mav;
    }

    // $Author :jin_peng
    // $Date : 2012/09/21 14:01$
    // [BUG]0009561 ADD BEGIN
    /**
     * 针对与his集成登陆验证
     * @param request 页面上下文对象
     * @return 
     */
    @RequestMapping("/loginIntegrated")
    public ModelAndView loginIntegrated(WebRequest request) throws Exception
    {
        Map<String, String[]> params = request.getParameterMap();

        // 获取登陆集成url传来的参数
        String userId = request.getParameter("userId");
        String patientId = request.getParameter("patientId");
        String domainId = request.getParameter("domainId");
        String hidePatientFlag = request.getParameter("hidePatientFlag");
        hidePatientFlag = StringUtils.isEmpty(hidePatientFlag) ? "false"
                : hidePatientFlag;
        boolean isExistDeptCodeFlag = params.containsKey("deptCode");

        ModelAndView mav = new ModelAndView();
        mav.addObject("userId", userId);
        mav.addObject("patientId", patientId);
        mav.addObject("domainId", domainId);
        mav.addObject("hidePatientFlag", hidePatientFlag);
        mav.addObject("existDeptCodeFlag", isExistDeptCodeFlag);

        if (isExistDeptCodeFlag)
        {
            String deptCode = "";

            if (params.get("deptCode") != null)
            {
                deptCode = params.get("deptCode")[0];
            }

            mav.addObject("deptCode", deptCode);
        }
        
        // 因暂时不验证密码项所以给予一个恒定密码进行验证
        mav.addObject("pword", Constants.PASSWORD_INTEGRATED);
        mav.setViewName("login/loginIntegrated");

        return mav;
    }

    /**
     * 针对与住院视图集成检验登陆验证
     * @param request 页面上下文对象
     * @return 
     */
    @RequestMapping("/loginInpatientIntegrated")
    public ModelAndView loginInpatientIntegrated(WebRequest request)
            throws Exception
    {
        // 获取登陆集成url传来的参数
        String patientId = request.getParameter("patientId");
        String domainId = request.getParameter("domainId");
        String userId = request.getParameter("userId");

        String inpatientType = request.getParameter("inpatientType");
        String sceneType = request.getParameter("sceneType");
        ModelAndView mav = new ModelAndView();
        mav.addObject("userId", userId);
        mav.addObject("patientId", patientId);
        mav.addObject("domainId", domainId);
        mav.addObject("inpatientType", inpatientType);
        mav.addObject("sceneType", sceneType);
        // [BUG]0041864 ADD END
        // 因暂时不验证密码项所以给予一个恒定密码进行验证
        mav.addObject("pword", Constants.PASSWORD_INTEGRATED);
        mav.setViewName("login/loginInpatientIntegrated");

        return mav;
    }

    // [BUG]0009561 ADD END

    /**
     * 针对与his集成检验登陆验证
     * @param request 页面上下文对象
     * @return 
     */
    @RequestMapping("/loginLabReportIntegrated")
    public ModelAndView loginLabReportIntegrated(WebRequest request)
            throws Exception
    {
        // 获取登陆集成url传来的参数
        String reportNo = request.getParameter("reportNo");
        String itemCode = request.getParameter("itemCode");
        String userId = request.getParameter("userId");
        // $Author :chang_xuewen
        // $Date : 2014/01/27 10:00$
        // [BUG]0041864 ADD BEGIN
        String orgCode = request.getParameter("orgCode");
        // $Author :yang_jianbo
        // $Date : 2014/04/11 17:10$
        // [BUG]0043573 兼容性处理，为空的时候默认为北大人民医院
        if(StringUtils.isEmpty(orgCode)){
        	orgCode = "H0001";
        }
        String reportTypeCode = request.getParameter("reportTypeCode");

        ModelAndView mav = new ModelAndView();
        mav.addObject("userId", userId);
        mav.addObject("reportNo", reportNo);
        mav.addObject("itemCode", itemCode);
        mav.addObject("orgCode", orgCode);
        mav.addObject("reportTypeCode", reportTypeCode);
        // [BUG]0041864 ADD END
        // 因暂时不验证密码项所以给予一个恒定密码进行验证
        mav.addObject("pword", Constants.PASSWORD_INTEGRATED);
        mav.setViewName("login/loginLabReportIntegrated");

        return mav;
    }
    /**
     * 针对EMR3.0及输液量统计集成检验登陆验证
     * @param request 页面上下文对象
     * @return 
     */
    @RequestMapping("/loginPortalIntegrated")
    public ModelAndView loginPortalIntegrated(WebRequest request)
            throws Exception
    {
        // 获取登陆集成url传来的参数
//        String patientId = request.getParameter("patientId");
//        String domainId = request.getParameter("domainId");
//        String userId = request.getParameter("userId");
//
//        String systemId = request.getParameter("systemId");
//        String viewId = request.getParameter("viewId");
//        String visitTimes = request.getParameter("visitTimes");
        
        String patientId = request.getParameter("pi");
        String domainId = request.getParameter("di");
        String userId = request.getParameter("ui");

        String systemId = request.getParameter("si");
        String viewId = request.getParameter("vi");
        String visitTimes = request.getParameter("vt");
        ModelAndView mav = new ModelAndView();
        mav.addObject("userId", userId);
        mav.addObject("patientId", patientId);
        mav.addObject("domainId", domainId);
        mav.addObject("systemId", systemId);
        mav.addObject("viewId", viewId);
        mav.addObject("visitTimes", visitTimes);
        // [BUG]0041864 ADD END
        // 因暂时不验证密码项所以给予一个恒定密码进行验证
        mav.addObject("pword", Constants.PASSWORD_INTEGRATED);
        mav.setViewName("login/loginPortalIntegrated");

        return mav;
    }

    /**
     * 针对与his集成检验登陆验证
     * @param request 页面上下文对象
     * @return 
     */
    @RequestMapping("/loginBusinessSnIntegrated")
    public ModelAndView loginBusinessSnIntegrated(WebRequest request)
            throws Exception
    {
        // 获取登陆集成url传来的参数
        String userId = request.getParameter("userId");
        String businessSn = request.getParameter("businessSn");

        ModelAndView mav = new ModelAndView();
        mav.addObject("userId", userId);
        mav.addObject("businessSn", businessSn);

        // 因暂时不验证密码项所以给予一个恒定密码进行验证
        mav.addObject("pword", Constants.PASSWORD_INTEGRATED);
        mav.setViewName("login/loginBusinessSnIntegrated");

        return mav;
    }

/*  移到LoginUtil中
 	// $Author :jin_peng
    // $Date : 2012/10/29 17:38$
    // [BUG]0010836 ADD BEGIN
    *//**
     * 获取客户端ip地址
     * @param request 上下文对象
     * @return 客户端ip地址
     *//*
    private String getClientIpAddr(HttpServletRequest request)
    {
        // 分不同的特殊情况获取用户客户端ip地址
        String ip = request.getHeader("X-Forwarded-For");

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip))
        {
            ip = request.getHeader("Proxy-Client-IP");
        }

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip))
        {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip))
        {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip))
        {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip))
        {
            ip = request.getRemoteAddr();
        }

        return ip;
    }

    // $Author:jin_peng
    // $Date:2013/09/23 15:50
    // [BUG]0037540 ADD BEGIN
    *//**
     * 获取客户端机器名地址
     * @param request 上下文对象
     * @return 客户端ip地址
     *//*
    private String getClientComputerName(HttpServletRequest request, String ip)
    {
        String clientHostName = null;

        try
        {
            clientHostName = InetAddress.getByName(ip).getHostName();
        }
        catch (UnknownHostException e)
        {
            logger.error("获取客户端机器名失败，客户端ip地址为：" + ip);

            e.printStackTrace();
        }

        return clientHostName;
    }

    // [BUG]0037540 ADD END

    *//**
     * 获取用户日志对象
     * @param request 上下文对象
     * @param userDetails 用户信息对象
     * @param patientDomain 域ID
     * @return 已设置完成的用户日志对象
     *//*
    private SystemAccountLog getSystemAccountLog(HttpServletRequest request,
            LoginUserDetails userDetails, String patientDomain)
    {
        // 链接到cdr系统的方式编码
        String linkKindsCode = null;

        // 链接到cdr系统的方式名称
        String linkKindsName = null;

        // 获取系统当前时间
        Date nowDate = DateUtils.getSystemTime();

        // 创建用户日志对象
        SystemAccountLog systemAccountLog = new SystemAccountLog();
        
        // 设置用户主键内部序列号
        systemAccountLog.setAccountLogSn(userDetails.getSequenceNo());
        
        //针对药房链接记录，把链接中userid作为日志的用户名
		if(null != userDetails.getInpatientType() && Constants.obtainViewTypesMap().containsKey(userDetails.getInpatientType())){
			systemAccountLog.setUserId(userDetails.getDisplayUsername());
		}else{
	        // 设置用户ID
	        systemAccountLog.setUserId(userDetails.getUsername());
		}
		
		//针对输液统计，emr3.0，把链接中userid作为日志的用户名 wang.meng
		if(null != userDetails.getViewId() && Constants.obtainViewIdsMap().containsKey(userDetails.getViewId())){
			systemAccountLog.setUserId(userDetails.getDisplayUsername());
		}else{
	        // 设置用户ID
	        systemAccountLog.setUserId(userDetails.getUsername());
		}
        // 设置用户ID
 //       systemAccountLog.setUserId(userDetails.getUsername());

        // 设置用户名称
        systemAccountLog.setUserName(userDetails.getDisplayUsername());

        // 设置用户所在部门
        systemAccountLog.setDeptCode(userDetails.getDeptCode());

        // 设置登陆使用cdr系统开始时间
        systemAccountLog.setUseBeginDate(nowDate);

        // $Author: jin_peng
        // $Date : 2013/11/25 13:41
        // $[BUG]0039829 MODIFY BEGIN
        if (userDetails.isEMRFlag())
        {
            linkKindsCode = Constants.LINK_KINDS_CODE_EMR;

            linkKindsName = Constants.LINK_KINDS_NAME_EMR;
        }
        else
        {
        	 // emr3.0及输液统计
        	if (null != userDetails.getViewId() && !userDetails.getViewId().equals("")){
            	linkKindsCode = userDetails.getViewId();
            	linkKindsName = Constants.obtainSystemIdsMap().get(userDetails.getSystemId())+Constants.obtainViewIdsMap().get(userDetails.getViewId());
            }
            // 分别判断用户链接到cdr系统的方式（登陆页面登陆/集成登陆）
            // 集成门诊
        	else if (Constants.PATIENT_DOMAIN_OUTPATIENT.equals(patientDomain))
            {
                linkKindsCode = Constants.LINK_KINDS_CODE_OUT;

                linkKindsName = Constants.LINK_KINDS_NAME_OUT;
            }
            // 集成住院
            else if (Constants.PATIENT_DOMAIN_INPATIENT.equals(patientDomain))
            {
            	if(null != userDetails.getInpatientType() && Constants.obtainViewTypesMap().containsKey(userDetails.getInpatientType())){//判断是否药房链接
                    linkKindsCode = Constants.LINK_KINDS_CODE_INPATIENT;

                    linkKindsName = Constants.LINK_KINDS_NAME_INPATIENT; 
                    if(null != userDetails.getSceneType() && !userDetails.getSceneType().equals("")){
                    	linkKindsCode = userDetails.getSceneType();
                    	linkKindsName = Constants.obtainSceneTypesMap().get(userDetails.getSceneType());
                    }
            	}
            	else{
                    linkKindsCode = Constants.LINK_KINDS_CODE_IN;

                    linkKindsName = Constants.LINK_KINDS_NAME_IN;            		
            	}
            }
            
            // cdr登陆页面
            else if (StringUtils.isEmpty(patientDomain))
            {
                linkKindsCode = Constants.LINK_KINDS_CODE_CDR;

                linkKindsName = Constants.LINK_KINDS_NAME_CDR;
            }
        }

        // $[BUG]0039829 MODIFY END

        // 设置链接cdr系统方式编码
        systemAccountLog.setLinkKindsCode(linkKindsCode);

        // 设置链接cdr系统方式名称
        systemAccountLog.setLinkKindsName(linkKindsName);

        // 设置用户客户端ip地址
        String ipAddress = getClientIpAddr(request);

        systemAccountLog.setClientIp(ipAddress);

        // $Author:jin_peng
        // $Date:2013/09/23 15:50
        // [BUG]0037540 ADD BEGIN
        // 设置用户客户端机器名
        String computerName = getClientComputerName(request, ipAddress);

        systemAccountLog.setClientComputerName(computerName);

        // [BUG]0037540 ADD END

        // 设置记录创建时间
        systemAccountLog.setCreateTime(nowDate);

        // 设置记录更新时间
        systemAccountLog.setUpdateTime(nowDate);

        // 设置记录新建时默认更新回数
        systemAccountLog.setUpdateCount(Constants.INSERT_UPDATE_COUNT);

        // 设置记录新建时默认删除标识
        systemAccountLog.setDeleteFlag(Constants.NO_DELETE_FLAG);

        return systemAccountLog;
    }*/

    /**
     * 用户注销退出
     * @param request 上下文对象
     * @return 
     */
    @RequestMapping("/loginExited")
    public ModelAndView loginExited(WebRequest request) throws Exception
    {
        // 获取用户退出cdr系统方式标识或名称
        String exitKindsName = request.getParameter("exitKindsName");

        if (StringUtils.isEmpty(exitKindsName))
        {
            return null;
        }

        // 获取用户登陆信息
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // 当用户已登录的情况退出情况记录用户退出日志
        if (authentication != null)
        {
            LoginUserDetails userDetails = (LoginUserDetails) authentication.getPrincipal();

            // 设置退出系统方式名称
            userDetails.setExitKindsName(exitKindsName);
        }
        else
        {
            // 用户未登陆情况退出用户日志则不做处理
            return null;
        }

        ModelAndView mav = new ModelAndView();

        // 设置内部跳转地址
        mav.setViewName("login/successLoginExit");

        return mav;
    }

    // [BUG]0010836 ADD END

    // $Author :jin_peng
    // $Date : 2012/09/21 14:01$
    // [BUG]0009561 ADD BEGIN
    public LoginService getLoginService()
    {
        return loginService;
    }

    public void setLoginService(LoginService loginService)
    {
        this.loginService = loginService;
    }

    // [BUG]0009561 ADD END

    // $Author :jin_peng
    // $Date : 2012/10/30 14:40$
    // [BUG]0010836 ADD BEGIN
    public CommonService getCommonService()
    {
        return commonService;
    }

    public void setCommonService(CommonService commonService)
    {
        this.commonService = commonService;
    }

    // [BUG]0010836 ADD END
}
