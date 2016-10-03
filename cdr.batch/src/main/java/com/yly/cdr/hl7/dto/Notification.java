package com.yly.cdr.hl7.dto;

import java.util.List;

import org.hibernate.validator.constraints.NotEmpty;

public class Notification extends BaseDto
{
    /**
     * 操作类型
     */
    @NotEmpty(message = "{Notification.newUpFlag}")
    private String newUpFlag;

    /**
     * 通知对象工号
     */
    @NotEmpty(message = "{Notification.userId}")
    private String userId;

    /**
     * 规则分组
     */
    @NotEmpty(message = "{Notification.ruleGroup}")
    private String ruleGroup;

    /**
     * 通知对象类型
     */
    @NotEmpty(message = "{Notification.userType}")
    private String userType;

    /**
     * 通知详细
     */
    @NotEmpty(message = "{Notification.method}")
    private List<WarningDetail> method;

    /**
     * 科室ID
     */
    private String deptId;

    /**
     * 医疗机构编码
     */
    //@NotEmpty(message = "{message.organizationCode}")
    private String hospitalCode;

    /**
     * 医疗机构名称
     */
    //@NotEmpty(message = "{message.organizationName}")
    private String hospitalName;

    public String getNewUpFlag()
    {
        return newUpFlag;
    }

    public String getUserId()
    {
        return userId;
    }

    public String getRuleGroup()
    {
        return ruleGroup;
    }

    public String getUserType()
    {
        return userType;
    }

    public List<WarningDetail> getMethod()
    {
        return method;
    }

    public String getDeptId()
    {
        return deptId;
    }

    public String getHospitalCode()
    {
        return hospitalCode;
    }

    public String getHospitalName()
    {
        return hospitalName;
    }

    // Author :jia_yanqing
    // Date : 2013/1/22 15:00
    // [BUG]0042085 ADD BEGIN
    public void setHospitalCode(String hospitalCode)
    {
        this.hospitalCode = hospitalCode;
    }

    public void setHospitalName(String hospitalName)
    {
        this.hospitalName = hospitalName;
    }

    // [BUG]0042085 ADD END

    @Override
    public String toString()
    {
        StringBuilder builder = new StringBuilder();
        builder.append("Notification [newUpFlag=");
        builder.append(newUpFlag);
        builder.append(", userId=");
        builder.append(userId);
        builder.append(", ruleGroup=");
        builder.append(ruleGroup);
        builder.append(", userType=");
        builder.append(userType);
        builder.append(", deptId=");
        builder.append(deptId);
        builder.append(", hospitalCode=");
        builder.append(hospitalCode);
        builder.append("]");
        return builder.toString();
    }
}
