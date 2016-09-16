package com.founder.cdr.hl7.dto;

import org.hibernate.validator.constraints.NotEmpty;

/**
 * 
 * @author tong_meng
 *
 */
public class CodeValues extends CodeValueDto
{
    /**
     * 版本号
     */
    private String versionNo;

    /**
     * 操作类型
     */
    @NotEmpty(message = "{CodeValues.newUpFlag}")
    public String newUpFlag;

    /**
     * CV_CODE
     */
    @NotEmpty(message = "{CodeValues.code}")
    public String code;

    /**
     * CV_NAME
     */
    @NotEmpty(message = "{CodeValues.name}")
    public String name;

    /**
     * 消息源系统编码
     */
    public String sourceSysCode;

    /**
     * 消息目标系统编码
     */
    public String targetSysCode;

    /**
     * 拼音码
     */
    public String pyCode;

    /**
     * 显示顺序 
     */
    public String seqNo;

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

    public String getPyCode()
    {
        return pyCode;
    }

    public String getSeqNo()
    {
        return seqNo;
    }

    public String getSourceSysCode()
    {
        return sourceSysCode;
    }

    public String getTargetSysCode()
    {
        return targetSysCode;
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
        return name;
    }
    // [BUG]001105 MODIFY END
}
