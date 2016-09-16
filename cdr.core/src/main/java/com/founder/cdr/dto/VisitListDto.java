package com.founder.cdr.dto;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 
 * [FUN]V05-101就诊列表
 * @version 1.0, 2012/06/27
 * @author zhangbin
 * @since 1.0 
 * 
 */
public class VisitListDto
{

    // 就诊内部序列号
    private String visitSn;

    // 就诊类别
    private String visitTypeName;

    // $Author:bin_zhang
    // $Date : 2012/11/20 14:10
    // $[BUG]11399 ADD BEGIN
    // 就诊类别代码
    private String visitTypeCode;

    // $[BUG]11399 ADD END

    // $[BUG]11399 ADD BEGIN
    // 就诊次数
    private BigDecimal visitTimes;

    // 就诊医生
    private String visitDoctorName;

    // 就诊(入院)日期
    private Date visitDate;

    // 入院(就诊)科室代码
    private String visitDeptId;

    // 入院(就诊)科室名称
    private String visitDeptName;

    // 就诊状态
    private String visitStatusName;

    // $Author :bin_zhang
    // $Date : 2012/9/04 17:23$
    // [BUG]0009334 ADD BEGIN
    // 就诊号别
    private String registrationClassName;
    // [BUG]0009334 ADD END
    
    // 出院时间
    private Date dischargeDate;
    // $Author :tong_meng
    // $Date : 2013/12/23 10:50$
    // [BUG]0041102 ADD BEGIN
    private String orgCode;
    private String orgName;
    // [BUG]0041102 ADD END
    
    public String getVisitSn()
    {
        return visitSn;
    }

    public void setVisitSn(String visitSn)
    {
        this.visitSn = visitSn;
    }

    public String getVisitTypeName()
    {
        return visitTypeName;
    }

    public void setVisitTypeName(String visitTypeName)
    {
        this.visitTypeName = visitTypeName;
    }

    public BigDecimal getVisitTimes()
    {
        return visitTimes;
    }

    public void setVisitTimes(BigDecimal visitTimes)
    {
        this.visitTimes = visitTimes;
    }

    public String getVisitDoctorName()
    {
        return visitDoctorName;
    }

    public void setVisitDoctorName(String visitDoctorName)
    {
        this.visitDoctorName = visitDoctorName;
    }

    public Date getVisitDate()
    {
        return visitDate;
    }

    public void setVisitDate(Date visitDate)
    {
        this.visitDate = visitDate;
    }

    public String getVisitDeptId()
    {
        return visitDeptId;
    }

    public void setVisitDeptId(String visitDeptId)
    {
        this.visitDeptId = visitDeptId;
    }

    public String getVisitDeptName()
    {
        return visitDeptName;
    }

    public void setVisitDeptName(String visitDeptName)
    {
        this.visitDeptName = visitDeptName;
    }

    public String getVisitStatusName()
    {
        return visitStatusName;
    }

    public void setVisitStatusName(String visitStatusName)
    {
        this.visitStatusName = visitStatusName;
    }

    // $Author :bin_zhang
    // $Date : 2012/9/04 17:23$
    // [BUG]0009334 ADD BEGIN
    public String getRegistrationClassName()
    {
        return registrationClassName;
    }

    public void setRegistrationClassName(String registrationClassName)
    {
        this.registrationClassName = registrationClassName;
    }

    // [BUG]0009334 ADD END

    // $Author:bin_zhang
    // $Date : 2012/11/20 14:10
    // $[BUG]11399 ADD BEGIN
    public String getVisitTypeCode()
    {
        return visitTypeCode;
    }

    public void setVisitTypeCode(String visitTypeCode)
    {
        this.visitTypeCode = visitTypeCode;
    }
    // $[BUG]11399 ADD BEGIN

    public Date getDischargeDate()
    {
        return dischargeDate;
    }

    public void setDischargeDate(Date dischargeDate)
    {
        this.dischargeDate = dischargeDate;
    }

    public String getOrgCode()
    {
        return orgCode;
    }

    public void setOrgCode(String orgCode)
    {
        this.orgCode = orgCode;
    }

    public String getOrgName()
    {
        return orgName;
    }

    public void setOrgName(String orgName)
    {
        this.orgName = orgName;
    }

}
