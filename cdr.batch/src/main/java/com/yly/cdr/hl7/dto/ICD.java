package com.yly.cdr.hl7.dto;

import org.hibernate.validator.constraints.NotEmpty;

/**
 * 
 * @author tong_meng
 *
 */
public class ICD extends CodeValueDto
{
    /**
     * 版本号
     */
    private String versionNo;

    /**
     * 操作类型
     */
    @NotEmpty(message = "{ICD.newUpFlag}")
    private String newUpFlag;

    /**
     * 疾病编码
     */
    @NotEmpty(message = "{ICD.code}")
    private String code;

    /**
     * 疾病名称
     */
    @NotEmpty(message = "{ICD.name}")
    private String name;

    /**
     * 对应ICD码
     */
    private String icdCode;

    /**
     * 所属科室
     */
    private String specialFlag2;

    /**
     * 排序码
     */
    private String seqNo;

    /**
     * 拼音码
     */
    private String pyCode;
    
    // Author :jia_yanqing
    // Date : 2013/10/11 17:30
    // [BUG]0037993 ADD BEGIN
    /**
     * 编码类型
     */
    private String standardFlg;
    // [BUG]0037993 ADD END

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

    public String getIcdCode()
    {
        return icdCode;
    }

    public String getSpecialFlag2()
    {
        return specialFlag2;
    }

    public String getSeqNo()
    {
        return seqNo;
    }

    public String getPyCode()
    {
        return pyCode;
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

    // Author :jia_yanqing
    // Date : 2013/10/11 17:30
    // [BUG]0037993 ADD BEGIN
    public String getStandardFlg()
    {
        return standardFlg;
    }
    // [BUG]0037993 ADD END
    
    
}
