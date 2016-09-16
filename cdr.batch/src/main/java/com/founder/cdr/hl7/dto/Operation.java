package com.founder.cdr.hl7.dto;

import org.hibernate.validator.constraints.NotEmpty;

/**
 * 
 * @author jia_yanqing
 *
 */
public class Operation extends CodeValueDto
{
    /**
     * 版本号
     */
    private String versionNo;

    /**
     * 操作类型
     */
    @NotEmpty(message = "{Operation.newUpFlag}")
    private String newUpFlag;

    /**
     * 编码
     */
    @NotEmpty(message = "{Operation.code}")
    private String code;

    /**
     * ICD码
     */
//    @NotEmpty(message = "{Operation.icdCode}")
    private String icdCode;
    
    /**
     * ICD名称
     */
    @NotEmpty(message = "{Operation.icdName}")
    private String icdName;

    /**
     * 排序码
     */
    private String seqNo;

    /**
     * 拼音码
     */
    private String pyCode;
    
    /**
     * 编码类型
     */
    private String standardFlg;

    public String getNewUpFlag()
    {
        return newUpFlag;
    }

    public String getCode()
    {
        return code;
    }

    public String getIcdCode()
    {
        return icdCode;
    }
    
    public String getIcdName()
    {
        return icdName;
    }

    public String getSeqNo()
    {
        return seqNo;
    }

    public String getPyCode()
    {
        return pyCode;
    }
    
    public String getStandardFlg()
    {
        return standardFlg;
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
