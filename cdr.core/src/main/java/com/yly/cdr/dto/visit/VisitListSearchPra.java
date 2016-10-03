package com.yly.cdr.dto.visit;

import java.util.List;

/**
 * 
 * [FUN]V05-101就诊列表
 * @version 1.0, 2012/05/12  
 * @author 张彬
 * @since 1.0
 * 
 */
public class VisitListSearchPra
{
    // 就诊日期
    private String visitDateFromStr;

    private String visitDateToStr;

    // 就诊类别
    private String visitTypeCode;

    // 入院(就诊)科室代码
    private String visitDeptId;

    // 入院出院日期
    private String dischargeDateFromStr;

    private String dischargeDateToStr;

    // 就诊医生
    private String visitDoctorName;

    // 就诊号别
    private String registrationClassCode;

    // $Author:wu_jianfeng
    // $Date : 2012/10/25 14:10
    // $[BUG]0010778 ADD BEGIN
    //当前日期
    private String sysDate;
    
    //就诊类型--住院
    private List visitTypeInpatientCode; 

    //患者内部序列号
    private String patientSn; 
    // $[BUG]0010778 ADD END
    
    // $Author :tong_meng
    // $Date : 2013/12/23 10:50$
    // [BUG]0041102 ADD BEGIN
    private String orgCode;
    private String orgName;
    // [BUG]0041102 ADD END
    
    public String getPatientSn()
    {
        return patientSn;
    }

    public void setPatientSn(String patientSn)
    {
        this.patientSn = patientSn;
    }

    public String getSysDate()
    {
        return sysDate;
    }

    public void setSysDate(String sysDate)
    {
        this.sysDate = sysDate;
    }

    public List getVisitTypeInpatientCode()
    {
        return visitTypeInpatientCode;
    }

    public void setVisitTypeInpatientCode(List visitTypeInpatientCode)
    {
        this.visitTypeInpatientCode = visitTypeInpatientCode;
    }

    public String getVisitDateFromStr()
    {
        return visitDateFromStr;
    }

    public void setVisitDateFromStr(String visitDateFromStr)
    {
        this.visitDateFromStr = visitDateFromStr;
    }

    public String getVisitDateToStr()
    {
        return visitDateToStr;
    }

    public void setVisitDateToStr(String visitDateToStr)
    {
        this.visitDateToStr = visitDateToStr;
    }

    

    public String getDischargeDateFromStr()
    {
        return dischargeDateFromStr;
    }

    public void setDischargeDateFromStr(String dischargeDateFromStr)
    {
        this.dischargeDateFromStr = dischargeDateFromStr;
    }

    public String getDischargeDateToStr()
    {
        return dischargeDateToStr;
    }

    public void setDischargeDateToStr(String dischargeDateToStr)
    {
        this.dischargeDateToStr = dischargeDateToStr;
    }

    public String getVisitDoctorName()
    {
        return visitDoctorName;
    }

    public void setVisitDoctorName(String visitDoctorName)
    {
        this.visitDoctorName = visitDoctorName;
    }

    public String getVisitTypeCode()
    {
        return visitTypeCode;
    }

    public void setVisitTypeCode(String visitTypeCode)
    {
        this.visitTypeCode = visitTypeCode;
    }

    public String getVisitDeptId()
    {
        return visitDeptId;
    }

    public void setVisitDeptId(String visitDeptId)
    {
        this.visitDeptId = visitDeptId;
    }

    public String getRegistrationClassCode()
    {
        return registrationClassCode;
    }

    public void setRegistrationClassCode(String registrationClassCode)
    {
        this.registrationClassCode = registrationClassCode;
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
