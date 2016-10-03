package com.yly.cdr.hl7.dto;

import org.hibernate.validator.constraints.NotEmpty;

/**
 * 
 * @author tong_meng
 *
 */
public class LabItem extends CodeValueDto
{
    /**
     * 版本号
     */
    private String versionNo;

    /**
     * 操作类型
     */
    @NotEmpty(message = "{LabItem.newUpFlag}")
    private String newUpFlag;

    /**
     * 项目编码
     */
    @NotEmpty(message = "{LabItem.code}")
    private String code;

    /**
     * 项目名称
     */
    @NotEmpty(message = "{LabItem.name}")
    private String name;

    /**
     * 检验分类编码
     */
    private String classes;

    /**
     * 执行科室
     */
    private String performUnit;

    /**
     * 检验物容器
     */
    private String vessel;

    /**
     * 检验物剂量
     */
    private String dosage;

    /**
     * 检验物编码
     */
    private String sampleCode;

    /**
     * 拼音码
     */
    private String pyCode;

    /**
     * 加急标志
     */
    private String emergencyFlag;

    /**
     * 五笔码
     */
    private String wbCode;

    /**
     * 医嘱对照码
     */
    private String yzOrderCode;

    /**
     * 排序
     */
    private String sortNo;

    /**
     * 执行科室
     */
    private String execNuit;

    /**
     * 分组
     */
    private String groupSn;

    /**
     * 科研标志
     */
    private String srFlag;

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

    public String getClasses()
    {
        return classes;
    }

    public String getPerformUnit()
    {
        return performUnit;
    }

    public String getVessel()
    {
        return vessel;
    }

    public String getDosage()
    {
        return dosage;
    }

    public String getSampleCode()
    {
        return sampleCode;
    }

    public String getPyCode()
    {
        return pyCode;
    }

    public String getEmergencyFlag()
    {
        return emergencyFlag;
    }

    public String getWbCode()
    {
        return wbCode;
    }

    public String getYzOrderCode()
    {
        return yzOrderCode;
    }

    public String getSortNo()
    {
        return sortNo;
    }

    public String getExecNuit()
    {
        return execNuit;
    }

    public String getGroupSn()
    {
        return groupSn;
    }

    public String getSrFlag()
    {
        return srFlag;
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
