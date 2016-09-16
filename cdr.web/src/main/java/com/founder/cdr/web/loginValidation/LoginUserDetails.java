package com.founder.cdr.web.loginValidation;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.founder.cdr.web.userSettings.UserSettings;

/**
 * [FUN]V05-101登陆页面存储用户信息
 * @version 1.0, 2012/07/17  
 * @author 金鹏
 * @since 1.0
 */
public class LoginUserDetails implements UserDetails
{
    private static final long serialVersionUID = 5946152436253765975L;
    
    // 用户名
    private String username;

    // 用户密码
    private String password;

    // 显示用户名
    private String displayUsername;

    // 用户是否处于激活状态
    private boolean enabled;

    // 用户权限信息
    private List<GrantedAuthority> authorities;

    // $Author:wu_jianfeng
    // $Date : 2012/09/12 14:10
    // $[BUG]0009663 MODIFY BEGIN
    // 访问控制实现
    // 权限信息
    private AccessControl accessControl;

    // $[BUG]0009663 MODIFY BEGIN

    // $Author :jin_peng
    // $Date : 2012/09/21 14:01$
    // [BUG]0009561 ADD BEGIN
    // 患者本地ID
    private String patientId;

    // 域ID
    private String domainId;

    // [BUG]0009561 ADD END

    // $Author :wu_jianfeng
    // $Date : 2012/10/09 14:01$
    // [BUG]0010107 ADD BEGIN
    // 就诊索引视图
    // 科室编码
    private String deptCode;

    // [BUG]0010107 ADD END

    // $Author :jin_peng
    // $Date : 2012/10/29 17:38$
    // [BUG]0010836 ADD BEGIN
    // 用户日志主键序列号
    private BigDecimal sequenceNo;

    // 登陆时的标识
    private boolean isLogin;

    // 退出系统方式名称
    private String exitKindsName;

    // [BUG]0010836 ADD END

    // $Author: wu_jianfeng
    // $Date : 2012/12/05 17:00
    // $[BUG]0011228 ADD BEGIN
    private List<String> deptIds;

    // $[BUG]0011228 ADD END

    // $Author: wu_jianfeng
    // $Date : 2012/12/20 17:00
    // $[BUG]0012697 ADD BEGIN
    private UserSettings userSettings;
    // $[BUG]0012697 ADD END
    
    // $Author: jin_peng
    // $Date : 2013/09/12 15:37
    // $[BUG]0036478 ADD BEGIN
    private boolean isNeedAlterPw;
    
    // $[BUG]0036478 ADD END
    
    // $Author: jin_peng
    // $Date : 2013/11/25 13:41
    // $[BUG]0039829 ADD BEGIN
    private boolean isEMRFlag;
    
    // $[BUG]0039829 ADD END
    
    // 检验报告本地ID
    private String labReportLid;
    // 检验报告大项编码
    private String labItemCode;
    
    // $Author :jin_peng
    // $Date : 2013/11/28 10:07$
    // [BUG]0038987 ADD BEGIN
    // 输入密码
    private String passwordInput;
    
    // 登陆页面是否选中记住密码标识
    private String saveLoginInfoFlag;
    
    // 是否开启记录登陆信息
    private boolean isLaunchedLoginInfo;
    
    // [BUG]0038987 ADD END
    
    // $Author :chang_xuewen
    // $Date : 2014/01/27 10:00$
    // [BUG]0041864 ADD BEGIN
    private String orgCode;
    //住院类型
    private String inpatientType;
    
    //报告类型编码
    private String reportTypeCode;
    
    // 业务表主键
    private String businessSn;
    
    // 判断是否需要隐藏患者选择列
    private String hidePatientFlag;
    // 链接场景类型
    private String sceneType;
    
    //wang.meng
    //系统Id
    private String systemId;
    
    private String viewId;
    
    private String visitTimes;
    
    
    
	public String getSystemId() {
		return systemId;
	}

	public void setSystemId(String systemId) {
		this.systemId = systemId;
	}

	public String getViewId() {
		return viewId;
	}

	public void setViewId(String viewId) {
		this.viewId = viewId;
	}
	

	public String getVisitTimes() {
		return visitTimes;
	}

	public void setVisitTimes(String visitTimes) {
		this.visitTimes = visitTimes;
	}

	public String getSceneType() {
		return sceneType;
	}

	public void setSceneType(String sceneType) {
		this.sceneType = sceneType;
	}

	public String getInpatientType() {
		return inpatientType;
	}

	public void setInpatientType(String inpatientType) {
		this.inpatientType = inpatientType;
	}

	public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}
	// [BUG]0041864 ADD END
	public void setUsername(String username)
    {
        this.username = username;
    }

    public String getUsername()
    {
        return username;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public String getPassword()
    {
        return password;
    }

    public boolean isEnabled()
    {
        return enabled;
    }

    public void setEnabled(boolean enabled)
    {
        this.enabled = enabled;
    }

    public boolean isAccountNonExpired()
    {
        return enabled;
    }

    public boolean isAccountNonLocked()
    {
        return enabled;
    }

    public boolean isCredentialsNonExpired()
    {
        return enabled;
    }

    public List<GrantedAuthority> getAuthorities()
    {
        return authorities;
    }

    public void setAuthorities(List<GrantedAuthority> authorities)
    {
        this.authorities = authorities;
    }

    public String getDisplayUsername()
    {
        return displayUsername;
    }

    public void setDisplayUsername(String displayUsername)
    {
        this.displayUsername = displayUsername;
    }

    // $Author:wu_jianfeng
    // $Date : 2012/09/12 14:10
    // $[BUG]0009663 MODIFY BEGIN
    // 访问控制实现
    public AccessControl getAccessControl()
    {
        return accessControl;
    }

    public void setAccessControl(AccessControl accessControl)
    {
        this.accessControl = accessControl;
    }

    // $[BUG]0009663 MODIFY END

    // $Author :jin_peng
    // $Date : 2012/09/21 14:01$
    // [BUG]0009561 ADD BEGIN
    public String getPatientId()
    {
        return patientId;
    }

    public void setPatientId(String patientId)
    {
        this.patientId = patientId;
    }

    public String getDomainId()
    {
        return domainId;
    }

    public void setDomainId(String domainId)
    {
        this.domainId = domainId;
    }

    // [BUG]0009561 ADD END

    // $Author :wu_jianfeng
    // $Date : 2012/10/09 14:01$
    // [BUG]0010107 ADD BEGIN
    // 就诊索引视图
    // 科室编码
    public String getDeptCode()
    {
        return deptCode;
    }

    public void setDeptCode(String deptCode)
    {
        this.deptCode = deptCode;
    }

    // [BUG]0010107 ADD END

    // $Author :jin_peng
    // $Date : 2012/10/29 17:38$
    // [BUG]0010836 ADD BEGIN
    public BigDecimal getSequenceNo()
    {
        return sequenceNo;
    }

    public void setSequenceNo(BigDecimal sequenceNo)
    {
        this.sequenceNo = sequenceNo;
    }

    public boolean isLogin()
    {
        return isLogin;
    }

    public void setLogin(boolean isLogin)
    {
        this.isLogin = isLogin;
    }

    public String getExitKindsName()
    {
        return exitKindsName;
    }

    public void setExitKindsName(String exitKindsName)
    {
        this.exitKindsName = exitKindsName;
    }

    // [BUG]0010836 ADD END

    // $Author: wu_jianfeng
    // $Date : 2012/12/05 17:00
    // $[BUG]0011228 ADD BEGIN
    public List<String> getDeptIds()
    {
        return deptIds;
    }

    public void setDeptIds(List<String> deptIds)
    {
        this.deptIds = deptIds;
    }

    // $[BUG]0011228 ADD END

    // $Author: wu_jianfeng
    // $Date : 2012/12/20 17:00
    // $[BUG]0012697 ADD BEGIN
    public UserSettings getUserSettings()
    {
        return userSettings;
    }

    public void setUserSettings(UserSettings userSettings)
    {
        this.userSettings = userSettings;
    }
    // $[BUG]0012697 ADD END

    // $Author: jin_peng
    // $Date : 2013/09/12 15:37
    // $[BUG]0036478 ADD BEGIN
    public boolean isNeedAlterPw()
    {
        return isNeedAlterPw;
    }

    public void setNeedAlterPw(boolean isNeedAlterPw)
    {
        this.isNeedAlterPw = isNeedAlterPw;
    }
    
    // $[BUG]0036478 ADD END

    public String getLabReportLid()
    {
        return labReportLid;
    }

    public void setLabReportLid(String labReportLid)
    {
        this.labReportLid = labReportLid;
    }

    public String getLabItemCode()
    {
        return labItemCode;
    }

    public void setLabItemCode(String labItemCode)
    {
        this.labItemCode = labItemCode;
    }

    public boolean isEMRFlag()
    {
        return isEMRFlag;
    }

    public void setEMRFlag(boolean isEMRFlag)
    {
        this.isEMRFlag = isEMRFlag;
    }

    public String getPasswordInput()
    {
        return passwordInput;
    }

    public void setPasswordInput(String passwordInput)
    {
        this.passwordInput = passwordInput;
    }

    public String getSaveLoginInfoFlag()
    {
        return saveLoginInfoFlag;
    }

    public void setSaveLoginInfoFlag(String saveLoginInfoFlag)
    {
        this.saveLoginInfoFlag = saveLoginInfoFlag;
    }

    public boolean isLaunchedLoginInfo()
    {
        return isLaunchedLoginInfo;
    }

    public void setLaunchedLoginInfo(boolean isLaunchedLoginInfo)
    {
        this.isLaunchedLoginInfo = isLaunchedLoginInfo;
    }

    public String getReportTypeCode()
    {
        return reportTypeCode;
    }

    public void setReportTypeCode(String reportTypeCode)
    {
        this.reportTypeCode = reportTypeCode;
    }

    public String getBusinessSn()
    {
        return businessSn;
    }

    public void setBusinessSn(String businessSn)
    {
        this.businessSn = businessSn;
    }

    public String getHidePatientFlag()
    {
        return hidePatientFlag;
    }

    public void setHidePatientFlag(String hidePatientFlag)
    {
        this.hidePatientFlag = hidePatientFlag;
    }

}
