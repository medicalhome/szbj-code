package com.founder.cdr.hl7.dto.msh0001;

import com.founder.cdr.hl7.dto.BaseDto;

public class MSH0001 extends BaseDto
{
    /**
     * 标志
     */
    private String newUpFlag;

    /**
     * 用户ID
     */
    private String userId;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 密码
     */
    private String password;

    /**
     * 性别
     */
    private String sex;

    /**
     * 邮件地址
     */
    private String mobile;

    /**
     * 手机号码
     */
    private String phone;

    /**
     * 部门编码
     */
    private String deptCode;

    /**
     * 在岗状态
     */
    private String employmentStatusCd;

    /**
     * 人员类型
     */
    private String employeeTypeCd;

    /**
     * 人员所属
     */
    private String jobCategory;

    /**
     * 入职日期
     */
    private String serviceStartDate;

    /**
     * 账户状态
     */
    private String status;

    /**
     * 备注
     */
    private String memo;
    
    /**
     * 密码修改标识
     */
    private String resetPasswdFlag;

    public String getNewUpFlag()
    {
        return newUpFlag;
    }

    public String getUserId()
    {
        return userId;
    }

    public String getUserName()
    {
        return userName;
    }

    public String getPassword()
    {
        return password;
    }

    public String getSex()
    {
        return sex;
    }

    public String getMobile()
    {
        return mobile;
    }

    public String getPhone()
    {
        return phone;
    }

    public String getDeptCode()
    {
        return deptCode;
    }

    public String getEmploymentStatusCd()
    {
        return employmentStatusCd;
    }

    public String getEmployeeTypeCd()
    {
        return employeeTypeCd;
    }

    public String getJobCategory()
    {
        return jobCategory;
    }

    public String getServiceStartDate()
    {
        return serviceStartDate;
    }

    public String getStatus()
    {
        return status;
    }

    public String getMemo()
    {
        return memo;
    }

	public String getResetPasswdFlag() {
		return resetPasswdFlag;
	}
    
    
}
