package com.founder.cdr.hl7.dto;

import org.hibernate.validator.constraints.NotEmpty;

/**
 * 
 * @author tong_meng
 *
 */
public class WardDictionary extends CodeValueDto
{
    /**
     * 版本号
     */
    private String versionNo;

    /**
     * 操作类型
     */
    @NotEmpty(message = "{WardDictionary.newUpFlag}")
    private String newUpFlag;

    /**
     * 病区编码
     */
    @NotEmpty(message = "{WardDictionary.code}")
    private String code;

    /**
     * 病区名称
     */
    @NotEmpty(message = "{WardDictionary.name}")
    private String name;

    /**
     * 科室编码
     */
    private String deptSn;

    /**
     * 科室名称
     */
    private String deptName;

    /**
     * 开放床位数
     */
    private String openbendNum;

    /**
     * 病房拼音码
     */
    private String wardPyCode;

    /**
     * 病房自定义码
     */
    private String wardDCode;

    /**
     * 科室拼音码
     */
    private String deptPyCode;

    /**
     * 科室自定义码
     */
    private String deptDCode;

    /**
     * 摆药时间
     */
    private String startDayTimeKf;

    /**
     * 按病房标志
     */
    private String wardOnly;

    /**
     * 病房打印细目时间
     */
    private String detailPrintTime;

    /**
     * 住院处编号
     */
    private String adtDeptNo;

    /**
     * 护理单元
     */
    private String nurseUnitFlag;

    /**
     * 编制床位
     */
    private String planBedCount;

    /**
     * 排序码
     */
    private String seqNo;

    /**
     * 大病区标志
     */
    private String wardSelf;

    /**
     * 删除操作员
     */
    private String deletedInputOpera;

    /**
     * 删除日期
     */
    private String deletedInputDate;

    /**
     * 备注
     */
    private String comment;

    /**
     * 开始时间
     */
    private String starttimeFlag;

    /**
     * 拼音码
     */
    private String pyCode;

    public String getVersionNo()
    {
        return versionNo;
    }

    public String getNewUpFlag()
    {
        return newUpFlag;
    }

    public String getCode()
    {
        return code;
    }

    public String getName()
    {
        return name;
    }

    public String getDeptSn()
    {
        return deptSn;
    }

    public String getDeptName()
    {
        return deptName;
    }

    public String getOpenbendNum()
    {
        return openbendNum;
    }

    public String getWardPyCode()
    {
        return wardPyCode;
    }

    public String getWardDCode()
    {
        return wardDCode;
    }

    public String getDeptPyCode()
    {
        return deptPyCode;
    }

    public String getDeptDCode()
    {
        return deptDCode;
    }

    public String getStartDayTimeKf()
    {
        return startDayTimeKf;
    }

    public String getWardOnly()
    {
        return wardOnly;
    }

    public String getDetailPrintTime()
    {
        return detailPrintTime;
    }

    public String getAdtDeptNo()
    {
        return adtDeptNo;
    }

    public String getNurseUnitFlag()
    {
        return nurseUnitFlag;
    }

    public String getPlanBedCount()
    {
        return planBedCount;
    }

    public String getSeqNo()
    {
        return seqNo;
    }

    public String getWardSelf()
    {
        return wardSelf;
    }

    public String getDeletedInputOpera()
    {
        return deletedInputOpera;
    }

    public String getDeletedInputDate()
    {
        return deletedInputDate;
    }

    public String getComment()
    {
        return comment;
    }

    public String getStarttimeFlag()
    {
        return starttimeFlag;
    }

    public String getPyCode()
    {
        return pyCode;
    }

    public String getUniqueId()
    {
        return code;
    }

    public String getOptType()
    {
        return newUpFlag;
    }
    // $Author :tong_meng
    // $Date : 2012/11/05 11:00$
    // [BUG]0011055 MODIFY BEGIN
    public String getForeignKey()
    {
        return deptSn;
    }
    // [BUG]001105 MODIFY END
}
