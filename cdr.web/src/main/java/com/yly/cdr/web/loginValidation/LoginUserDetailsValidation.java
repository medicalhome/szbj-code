package com.yly.cdr.web.loginValidation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.googlecode.ehcache.annotations.Cacheable;
import com.yly.cdr.core.Constants;
import com.yly.cdr.dto.LoginDto;
import com.yly.cdr.entity.SystemSetting;
import com.yly.cdr.entity.SystemUserDept;
import com.yly.cdr.service.AccessControlService;
import com.yly.cdr.service.CommonService;
import com.yly.cdr.service.LoginService;
import com.yly.cdr.web.userSettings.UserSettings;

/**
 * 用于验证用户信息是否有效并存储该用户信息
 * @author jin_peng
 * @version 1.0, 2012/07/17
 */
public class LoginUserDetailsValidation implements UserDetailsService
{
    private LoginService loginService;

    // $Author:wu_jianfeng
    // $Date : 2012/09/12 14:10
    // $[BUG]0009663 MODIFY BEGIN
    // 访问控制实现
    private AccessControlService accessControlService;

    // $[BUG]0009663 MODIFY END

    // $Author :jin_peng
    // $Date : 2012/10/29 17:38$
    // [BUG]0010836 ADD BEGIN
    // 通用service
    private CommonService commonService;

    // 用户日志序列号
    private static String SEQUENCE_SYSTEM_ACCOUNT_LOG = "SEQ_SYSTEM_ACCOUNT_LOG";

    // [BUG]0010836 ADD END

    // 根据加密策略注入加密
    private PasswordEncoder passwordEncoder;

    // $Author :jin_peng
    // $Date : 2013/10/18 13:02$
    // [BUG]0038232 ADD BEGIN
    // 特殊人群权限配置
    public String peopleGroupSpecific;

    // [BUG]0038232 ADD END

    /**
     * 获取用户信息验证是否有效并储存用户信息用于后期角色权限验证时所用
     * @param username 用户id
     * @return 用户信息对象
     */
    @Cacheable(cacheName = "securedObjectCache")
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException, DataAccessException
    {
        // 用户存储信息对象
        LoginUserDetails userDetails = new LoginUserDetails();

        // $Author :jin_peng
        // $Date : 2013/11/28 10:07$
        // [BUG]0038987 ADD BEGIN
        String[] userLogin = username.split("@");

        if (userLogin.length > 1)
        {
            username = userLogin[0];

            userDetails.setPasswordInput(userLogin[1]);

            userDetails.setSaveLoginInfoFlag(userLogin[2]);

            userDetails.setLaunchedLoginInfo(true);
        }

        // [BUG]0038987 ADD END

        // $Author :jin_peng
        // $Date : 2012/09/21 14:01$
        // [BUG]0009561 ADD BEGIN
        // 登陆集成时对参数username进行处理
        String[] userParams = username.split(";");

        // 判断是否为登陆集成方式
        if (userParams.length > 1)
        {
            // 获取用户ID
            username = userParams[0];

            // 获取患者ID
            userDetails.setPatientId(userParams[1]);

            // 获取域ID
            userDetails.setDomainId(userParams[2]);

            // 获取患者列表是否隐藏标识
            userDetails.setHidePatientFlag(userParams[3]);
        }

        // [BUG]0009561 ADD END

        // 统一参数处理逻辑，将参数字符串切分存入map中
        String[] userParamPairs = username.split("&");
        Map<String, String> userParamMap = new HashMap<String, String>();

        if (userParamPairs.length > 1)
        {
            for (String userParamPair : userParamPairs)
            {
                String[] userParamCouple = userParamPair.split("=");
                userParamMap.put(userParamCouple[0],
                        userParamCouple.length > 1 ? userParamCouple[1] : "");
            }

            // 针对HIS检验报告URL链接的参数设置
            userDetails.setLabReportLid(userParamMap.get("reportNo"));
            userDetails.setLabItemCode(userParamMap.get("itemCode"));
            userDetails.setOrgCode(userParamMap.get("orgCode"));
            userDetails.setReportTypeCode(userParamMap.get("reportTypeCode"));
            if (userParamMap.get("userId") != null
                && !userParamMap.get("userId").isEmpty())
                username = userParamMap.get("userId");
        }

        // 外部链接通过主键与cdr系统集成
        String[] userBusinessParamPairs = username.split("-");
        Map<String, String> userBusinessParamMap = new HashMap<String, String>();

        if (userBusinessParamPairs.length > 1)
        {
            for (String userBusinessParamPair : userBusinessParamPairs)
            {
                String[] userBusinessParamCouple = userBusinessParamPair.split("=");
                userBusinessParamMap.put(
                        userBusinessParamCouple[0],
                        userBusinessParamCouple.length > 1 ? userBusinessParamCouple[1]
                                : "");
            }

            // 针对HIS检验报告URL链接的参数设置
            userDetails.setBusinessSn(userBusinessParamMap.get("businessSn"));
            if (userBusinessParamMap.get("userId") != null
                && !userBusinessParamMap.get("userId").isEmpty())
                username = userBusinessParamMap.get("userId");
        }

        // 针对住院视图集成的处理
        // 统一参数处理逻辑，将参数字符串切分存入map中
        String[] userParamPair = username.split("#");
        Map<String, String> paramMap = new HashMap<String, String>();

        if (userParamPair.length > 1)
        {
            for (String userParamPai : userParamPair)
            {
                String[] paramCouple = userParamPai.split("=");
                paramMap.put(paramCouple[0],
                        paramCouple.length > 1 ? paramCouple[1] : "");
            }

            // 针对住院视图URL链接的参数设置
            userDetails.setPatientId(paramMap.get("patientId"));
            userDetails.setDomainId(paramMap.get("domainId"));
            userDetails.setInpatientType(paramMap.get("inpatientType"));
            userDetails.setSceneType(paramMap.get("sceneType"));            
            if (paramMap.get("userId") != null
                && !paramMap.get("userId").isEmpty())
                username = paramMap.get("userId");
        }
        // 针对输液量统计及EMR3.0对外链接的处理wang.meng
        // 统一参数处理逻辑，将参数字符串切分存入map中
        String[] userPortalParamPair = username.split("%");
        Map<String, String> paramPortalMap = new HashMap<String, String>();

        if (userPortalParamPair.length > 1)
        {
            for (String userParamPai : userPortalParamPair)
            {
                String[] paramCouple = userParamPai.split("=");
                paramPortalMap.put(paramCouple[0],
                        paramCouple.length > 1 ? paramCouple[1] : "");
            }

            // 针对URL链接的参数设置
            userDetails.setPatientId(paramPortalMap.get("patientId"));
            userDetails.setDomainId(paramPortalMap.get("domainId"));
            userDetails.setViewId(paramPortalMap.get("viewId"));
            userDetails.setSystemId(paramPortalMap.get("systemId"));
            userDetails.setVisitTimes(paramPortalMap.get("visitTimes"));
            if (paramPortalMap.get("userId") != null
                && !paramPortalMap.get("userId").isEmpty())
                username = paramPortalMap.get("userId");
        }
        List<LoginDto> userMessagesList = null;
        boolean isDisplayUserId = false;
        // 针对药房链接设置默认登录用户id
        if (null != paramMap.get("inpatientType") && Constants.obtainViewTypesMap().containsKey(paramMap.get("inpatientType")))
        {
            userMessagesList = loginService.selectUserMessages(username);
            if (userMessagesList == null || userMessagesList.isEmpty())
            {
                username = Constants.USER_NAME;
                isDisplayUserId = true;
                userMessagesList = loginService.selectUserMessages(username);
            }
            /*
             * else{ userMessagesList = null; }
             */
        }
        else
        {
            // 获取相应用户信息
            userMessagesList = loginService.selectUserMessages(username);
        }
        // 获取相应用户信息
        // List<LoginDto> userMessagesList =
        // loginService.selectUserMessages(username);

        // 当相应用户信息存在时保存该用户信息
        if (userMessagesList != null && !userMessagesList.isEmpty())
        {
            LoginDto userMessage = userMessagesList.get(0);

            // $Author: jin_peng
            // $Date : 2013/09/12 15:37
            // $[BUG]0036478 ADD BEGIN
            if (userParams.length == 1 && userParamPairs.length == 1
                && userParamPair.length == 1
                && userBusinessParamPairs.length == 1)
            {
//                if (passwordEncoder.encodePassword(username, null).equals(
//                        userMessage.getPassWd().toLowerCase()))
//                {
//                    userDetails.setNeedAlterPw(true);
//                }
            	if("0".equals(userMessage.getInitPwd())){
            		userDetails.setNeedAlterPw(true);
            	}
            }

            // $[BUG]0036478 ADD END

            // $Author :jin_peng
            // $Date : 2012/09/21 14:01$
            // [BUG]0009561 MODIFY BEGIN
            // 登陆集成时对异常进行处理
            if (!Constants.ACCOUNT_STATUS_ACTIVE.equals(userMessage.getStatus()))
            {
                throw new UsernameNotFoundException(exceptionPrompt(userParams,
                        "User not active"));
            }

            // $Author :jin_peng
            // $Date : 2013/10/18 13:02$
            // [BUG]0038232 MODIFY BEGIN
            // $Author:wu_jianfeng
            // $Date : 2012/09/12 14:10
            // $[BUG]0009663 ADD BEGIN
            // 访问控制实现
            /*
             * $Author :chang_xuewen $Date : 2013/11/08 11:00$ [BUG]0039195
             * DELETE BEGIN
             */
            /*
             * if (AccessControl.useACL()) { List<String> peopleGroupSpecifics =
             * obtainSpecificPeople();
             * 
             * if (peopleGroupSpecifics == null ||
             * !peopleGroupSpecifics.contains(username)) { if
             * (!Constants.ACL_OCCUPATION_TYPE_DOCTOR
             * .equals(userMessage.getJobCategory())) { throw new
             * UsernameNotFoundException(exceptionPrompt( userParams,
             * "User not doctor")); } } }
             */
            /* [BUG]0039195 DELETE END */
            // $[BUG]0009663 ADD END
            // [BUG]0038232 MODIFY END

            // 创建用户角色权限对象集合
            List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
            // 下面开始储存相应用户信息
            userDetails.setUsername(username); // 用户id
            // $Author :li_shenggen
            // $Date : 2014/08/27 15:35$
            // [BUG]0045918 ADD BEGIN
            if (isDisplayUserId)
            {
                userDetails.setDisplayUsername(paramMap.get("userId"));// 用户名
            }
            else
            {
                userDetails.setDisplayUsername(userMessage.getUserName());// 用户名
            }
         // [BUG]0045918 ADD END
            // userDetails.setDisplayUsername(userMessage.getUserName());// 用户名

            // $Author :wu_jianfeng
            // $Date : 2012/10/09 14:01$
            // [BUG]0010107 ADD BEGIN
            // 就诊索引视图
            userDetails.setDeptCode(userMessage.getDeptCode());
            // [BUG]0010107 ADD END

            // 登陆集成时针对目前没有密码情况密码暂时用一个恒定的常量
            if (userParams.length > 1 || userParamPairs.length > 1
                || userParamPair.length > 1
                || userBusinessParamPairs.length > 1||userPortalParamPair.length > 1)
            {
                userDetails.setPassword(Constants.PASSWORD_MD5_INTEGRATED);// 用户密码(登陆集成)
            }
            else
            {
                userDetails.setPassword(userMessage.getPassWd().toLowerCase());// 用户密码
            }
            // [BUG]0009561 MODIFY END

            // $Author :jin_peng
            // $Date : 2012/10/29 17:38$
            // [BUG]0010836 ADD BEGIN
            // 设置用户日志主键序列号
            userDetails.setSequenceNo(commonService.getSequence(SEQUENCE_SYSTEM_ACCOUNT_LOG));

            // 设置登陆标识
            userDetails.setLogin(true);

            // [BUG]0010836 ADD END

            userDetails.setEnabled(true); // 用户是否处于激活状态

            // $Author:jin_peng
            // $Date : 2013/11/11 10:00
            // $[BUG]0039228 MODIFY BEGIN
            // 针对电子病历集成
            List<String> deptIds = new ArrayList<String>();

            if (userParams.length > 1 && Boolean.parseBoolean(userParams[4]))
            {
                // $Author: jin_peng
                // $Date : 2013/11/25 13:41
                // $[BUG]0039829 ADD BEGIN
                userDetails.setEMRFlag(true);

                // $[BUG]0039829 ADD END

                String[] deptCodeStrs = userParams[5].split(",");

                if (deptCodeStrs != null && deptCodeStrs.length != 0)
                {
                    for (String deptCode : deptCodeStrs)
                    {
                        if (deptCode != null && !deptCode.trim().isEmpty())
                        {
                            deptIds.add(deptCode);
                        }
                    }
                }
            }
            else
            {
                // $Author:wu_jianfeng
                // $Date : 2012/12/05 17:00
                // $[BUG]0011228 ADD BEGIN
                Map<String, Object> condition = new HashMap<String, Object>();
                condition.put("userId", userDetails.getUsername());
                condition.put("deleteFlag", Constants.NO_DELETE_FLAG);
                // 根据用户ID，检索对应的科室（可能存在多个）
                List<Object> systemUserDeptList = commonService.selectByCondition(
                        SystemUserDept.class, condition);

                for (Object item : systemUserDeptList)
                {
                    SystemUserDept systemUserDept = (SystemUserDept) item;
                    String deptId = systemUserDept.getDeptCode();
                    if (deptId != null && !deptId.trim().isEmpty())
                        deptIds.add(deptId);

                }
            }

            userDetails.setDeptIds(deptIds);

            // $[BUG]0011228 ADD END
            // $[BUG]0039228 MODIFY END

            // $Author:wu_jianfeng
            // $Date : 2012/12/21 14:10
            // $[BUG]0012967 ADD BEGIN
            SystemSetting systemSetting = new SystemSetting();
            Map<String, Object> ssCondition = new HashMap<String, Object>();
            ssCondition.put("userSn", userDetails.getUsername());
            ssCondition.put("deleteFlag", Constants.NO_DELETE_FLAG);
            List<Object> systemSettings = commonService.selectByCondition(
                    SystemSetting.class, ssCondition);
            if (systemSettings.size() != 0)
                systemSetting = (SystemSetting) systemSettings.get(0);
            userDetails.setUserSettings(new UserSettings(systemSetting));
            // $[BUG]0012967 ADD END

            // $Author:wu_jianfeng
            // $Date : 2012/09/12 14:10
            // $[BUG]0009663 ADD BEGIN
            // 访问控制实现
            if (AccessControl.useACL())
                userDetails.setAccessControl(new AccessControl(
                        accessControlService.getUserAuths(username)));
            // $[BUG]0009663 ADD END
            GrantedAuthority authority = new GrantedAuthorityImpl("ROLE_ADMIN");// 设置用户角色权限（暂为测试用）
            authorities.add(authority);
            userDetails.setAuthorities(authorities);
        }
        else
        {
            // $Author :jin_peng
            // $Date : 2012/09/21 14:01$
            // [BUG]0009561 MODIFY BEGIN
            // 登陆集成时对异常进行处理
            throw new UsernameNotFoundException(exceptionPrompt(userParams,	
                    "User not found"));
            // [BUG]0009561 MODIFY END
        }
        return userDetails;
    }

    // $Author :jin_peng
    // $Date : 2012/09/21 14:01$
    // [BUG]0009561 ADD BEGIN
    /**
     * 根据登陆集成和普通情况获取异常信息
     * @param userParams 用户信息
     * @param prompt 异常信息
     * @return 异常信息
     */
    private String exceptionPrompt(String[] userParams, String prompt)
    {
        // 判断是否为登陆集成方式
        if (userParams.length > 1)
        {
            // 登陆集成环境给出异常信息
            prompt = "The Authentication was not got through in integrated environment";
        }

        return prompt;
    }

    // [BUG]0009561 ADD END

    // $Author :jin_peng
    // $Date : 2013/10/18 13:02$
    // [BUG]0038232 ADD BEGIN
    public List<String> obtainSpecificPeople()
    {
        List<String> specificPeople = null;

        String[] secificPeopleArray = peopleGroupSpecific.split(";");

        if (secificPeopleArray != null && secificPeopleArray.length != 0)
        {
            specificPeople = new ArrayList<String>();

            for (String specificPeopleStr : secificPeopleArray)
            {
                specificPeople.add(specificPeopleStr);
            }
        }

        return specificPeople;
    }

    // [BUG]0038232 ADD END

    public LoginService getLoginService()
    {
        return loginService;
    }

    public void setLoginService(LoginService loginService)
    {
        this.loginService = loginService;
    }

    // $Author:wu_jianfeng
    // $Date : 2012/09/12 14:10
    // $[BUG]0009663 MODIFY BEGIN
    // 访问控制实现
    public AccessControlService getAccessControlService()
    {
        return accessControlService;
    }

    public void setAccessControlService(
            AccessControlService accessControlService)
    {
        this.accessControlService = accessControlService;
    }

    // $[BUG]0009663 MODIFY END

    // $Author :jin_peng
    // $Date : 2012/10/29 17:38$
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

    public PasswordEncoder getPasswordEncoder()
    {
        return passwordEncoder;
    }

    public void setPasswordEncoder(PasswordEncoder passwordEncoder)
    {
        this.passwordEncoder = passwordEncoder;
    }

    public String getPeopleGroupSpecific()
    {
        return peopleGroupSpecific;
    }

    public void setPeopleGroupSpecific(String peopleGroupSpecific)
    {
        this.peopleGroupSpecific = peopleGroupSpecific;
    }

}
