package com.yly.cdr.hl7.dto;

import org.hibernate.validator.constraints.NotEmpty;

/**
 * 
 * @author tong_meng
 *
 */
public class EmergencyTreatment extends CodeValueDto
{
    /**
     * 版本号
     */
    private String versionNo;

    /**
     * 操作类型
     */
    @NotEmpty(message = "{EmergencyTreatment.newUpFlag}")
    private String newUpFlag;

    /**
     * 疾病编码
     */
    @NotEmpty(message = "{EmergencyTreatment.code}")
    private String code;

    /**
     * 疾病名称
     */
    @NotEmpty(message = "{EmergencyTreatment.name}")
    private String name;

    /**
     * 分类统计码
     */
    private String diseaseReason;

    /**
     * 死亡原因码
     */
    private String deathReason;

    /**
     * 拼音码
     */
    private String pyCode;

    /**
     * 级别
     */
    private String flag;

    /**
     * 临床表现自添加标志
     */
    private String addCode;

    /**
     * 额外标志
     */
    private String extraFlag;

    /**
     * ICD版本
     */
    private String icdVer;

    /**
     * 排序码 
     */
    private String seqNo;

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

    public String getDiseaseReason()
    {
        return diseaseReason;
    }

    public String getDeathReason()
    {
        return deathReason;
    }

    public String getPyCode()
    {
        return pyCode;
    }

    public String getFlag()
    {
        return flag;
    }

    public String getAddCode()
    {
        return addCode;
    }

    public String getExtraFlag()
    {
        return extraFlag;
    }

    public String getIcdVer()
    {
        return icdVer;
    }

    public String getSeqNo()
    {
        return seqNo;
    }

    public String getVersionNo()
    {
        return versionNo;
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
