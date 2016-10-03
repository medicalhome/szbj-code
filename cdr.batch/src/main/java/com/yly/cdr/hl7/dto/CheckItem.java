package com.yly.cdr.hl7.dto;

import org.hibernate.validator.constraints.NotEmpty;

/**
 * 
 * @author tong_meng
 *
 */
public class CheckItem extends CodeValueDto
{
    /**
     * 版本号
     */
    private String versionNo;

    /**
     * 操作类型
     */
    @NotEmpty(message = "{CheckItem.newUpFlag}")
    private String newUpFlag;

    /**
     * 分组编码
     */
    @NotEmpty(message = "{CheckItem.code}")
    private String code;

    /**
     * 分组名称
     */
    @NotEmpty(message = "{CheckItem.name}")
    private String name;

    /**
     * 报表尾
     */
    private String reportFooter;

    /**
     * 报表头
     */
    private String reportHeader;

    /**
     * 对应报表号
     */
    private String appointmentReportCode;

    /**
     * 提前分钟
     */
    private String aheadExamTime;

    /**
     * 预约单条码号
     */
    private String tmReportCode;

    /**
     * 是否扫描
     */
    private String scanFlag;

    /**
     * 是否称体重
     */
    private String weightFlag;

    /**
     * 是否同一天
     */
    private String sameDayFlag;

    /**
     * 科室模板
     */
    private String applyTemplate;

    /**
     * 排序码
     */
    private String seqNo;

    /**
     * 拼音码
     */
    private String pyCode;

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

    public String getReportFooter()
    {
        return reportFooter;
    }

    public String getReportHeader()
    {
        return reportHeader;
    }

    public String getAppointmentReportCode()
    {
        return appointmentReportCode;
    }

    public String getAheadExamTime()
    {
        return aheadExamTime;
    }

    public String getTmReportCode()
    {
        return tmReportCode;
    }

    public String getScanFlag()
    {
        return scanFlag;
    }

    public String getWeightFlag()
    {
        return weightFlag;
    }

    public String getSameDayFlag()
    {
        return sameDayFlag;
    }

    public String getApplyTemplate()
    {
        return applyTemplate;
    }

    public String getVersionNo()
    {
        return versionNo;
    }

    public String getSeqNo()
    {
        return seqNo;
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
}
