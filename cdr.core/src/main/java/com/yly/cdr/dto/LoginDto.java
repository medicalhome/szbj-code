package com.yly.cdr.dto;

import java.math.BigDecimal;

/**
 * [FUN]V05-101登陆页面用户信息
 * @version 1.0, 2012/07/17  
 * @author 金鹏
 * @since 1.0
 */
public class LoginDto
{
    // 登陆失败标识
    public final static String ERROR_LOGIN = "error";

    // 用户名不存在错误信息
    public final static String NO_EXISTS_USER = "用户名不存在";

    // 密码错误错误信息
    public final static String NO_CORRECT_PASSWORD = "密码输入错误";

    // 用户未激活错误信息
    public final static String USER_NOT_ACTIVE = "该用户账号未激活";

    // 用户不存在异常信息
    public final static String NO_EXISTS_EXCEPTION = "org.springframework.security.core.userdetails.UsernameNotFoundException: User not found";

    // 用户未激活账号异常信息
    public final static String NOT_ACTIVE_EXCEPTION = "org.springframework.security.core.userdetails.UsernameNotFoundException: User not active";

    // 密码错误异常
    public final static String NO_CORRECT_EXCEPTION = "org.springframework.security.authentication.BadCredentialsException: Bad credentials";
    
    // 打印登陆失败log
    public final static String NO_LOGIN_LOG_STATUS = "errorLog";

    // $Author :jin_peng
    // $Date : 2012/09/21 14:01$
    // [BUG]0009561 ADD BEGIN
    // 登陆集成异常信息
    public final static String NO_GET_THROUGH_EXCEPTION = "org.springframework.security.core.userdetails.UsernameNotFoundException: The Authentication was not got through in integrated environment";

    // [BUG]0009561 ADD END

    // 不是医生的错误信息
    public final static String USER_NOT_DOCTOR = "本系统目前只对医生开放";

    // 不是医生异常
    public final static String NOT_DOCTOR_EXCEPTION = "org.springframework.security.core.userdetails.UsernameNotFoundException: User not doctor";

    // 用户id
    private String userId;

    // 登陆密码
    private String passWd;

    // 激活状态
    private String status;

    // 用户名
    private String userName;

    // $Author:wu_jianfeng
    // $Date : 2012/09/12 14:10
    // $[BUG]0009663 MODIFY BEGIN
    // 访问控制实现
    // 工作岗位
    private String jobCategory;

    // $[BUG]0009663 MODIFY END

    // $Author :jin_peng
    // $Date : 2012/09/21 14:01$
    // [BUG]0009561 ADD BEGIN
    // 患者内部序列号
    private BigDecimal patientSn;

    // [BUG]0009561 ADD END

    // $Author :wu_jianfeng
    // $Date : 2012/10/09 14:01$
    // [BUG]0010107 ADD BEGIN
    // 就诊索引视图
    private String deptCode;
    
    // 用户更新状态
    private String initPwd;

    // [BUG]0010107 ADD END

    // $Author: jin_peng
    // $Date : 2013/09/12 15:37
    // $[BUG]0036478 ADD BEGIN
    // 需要修改密码
    public final static String REQUIRE_ALTER_PASSWORD_EXCEPTION = "org.springframework.security.core.userdetails.UsernameNotFoundException: User needed for altering password";

    public final static String REQUIRE_ALTER_PASSWORD = "请修改密码后在此重新登陆";

    public final static String NO_PASSWORD_RESET = "0";

    public final static String PASSWORD_RESET = "1";

    // $[BUG]0036478 ADD END

    // $Author :jin_peng
    // $Date : 2012/11/09 17:51$
    // [BUG]0010795 ADD BEGIN
    public static final String SAVE_LOGIN_INFO = "1";

    public static final String FIRST_LOGIN_COOKIE_NAME = "firstLoginInfo";

    // [BUG]0010795 ADD END

    public String getUserName()
    {
        return userName;
    }

    public void setUserName(String userName)
    {
        this.userName = userName;
    }

    public String getUserId()
    {
        return userId;
    }

    public void setUserId(String userId)
    {
        this.userId = userId;
    }

    public String getPassWd()
    {
        return passWd;
    }

    public void setPassWd(String passWd)
    {
        this.passWd = passWd;
    }

    public String getStatus()
    {
        return status;
    }

    public void setStatus(String status)
    {
        this.status = status;
    }

    // $Author:wu_jianfeng
    // $Date : 2012/09/12 14:10
    // $[BUG]0009663 MODIFY BEGIN
    // 访问控制实现
    public String getJobCategory()
    {
        return jobCategory;
    }

    public void setJobCategory(String jobCategory)
    {
        this.jobCategory = jobCategory;
    }

    // $[BUG]0009663 MODIFY END

    // $Author :jin_peng
    // $Date : 2012/09/21 14:01$
    // [BUG]0009561 ADD BEGIN
    public BigDecimal getPatientSn()
    {
        return patientSn;
    }

    public void setPatientSn(BigDecimal patientSn)
    {
        this.patientSn = patientSn;
    }

    // [BUG]0009561 ADD END

    // $Author :wu_jianfeng
    // $Date : 2012/10/09 14:01$
    // [BUG]0010107 ADD BEGIN
    // 就诊索引视图
    public String getDeptCode()
    {
        return deptCode;
    }

    public void setDeptCode(String deptCode)
    {
        this.deptCode = deptCode;
    }

    public String getPASSWORD_RESET()
    {
        return PASSWORD_RESET;
    }

    // [BUG]0010107 ADD END

    public String getFIRST_LOGIN_COOKIE_NAME()
    {
        return FIRST_LOGIN_COOKIE_NAME;
    }

	public String getInitPwd() {
		return initPwd;
	}

	public void setInitPwd(String initPwd) {
		this.initPwd = initPwd;
	}

}
