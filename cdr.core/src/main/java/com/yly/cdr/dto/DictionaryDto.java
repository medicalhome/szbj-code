package com.yly.cdr.dto;

/**
 * 数据字典dto
 * 
 * @author wen_ruichao
 * @version 1.0
 */
public class DictionaryDto
{

    private String csCode;

    private String csName;

    private String cvId;

    private String cvCode;

    private String cvValue;

    private String pyCode;

    private String seqNo;
    
    // Author :jin_peng
    // Date : 2013/04/02 14:56
    // [BUG]0014747 ADD BEGIN
    private String fullName;
    
    // [BUG]0014747 ADD END

    public String getCsCode()
    {
        return csCode;
    }

    public void setCsCode(String csCode)
    {
        this.csCode = csCode;
    }

    public String getCsName()
    {
        return csName;
    }

    public void setCsName(String csName)
    {
        this.csName = csName;
    }

    public String getCvCode()
    {
        return cvCode;
    }

    public void setCvCode(String cvCode)
    {
        this.cvCode = cvCode;
    }

    public String getCvValue()
    {
        return cvValue;
    }

    public void setCvValue(String cvValue)
    {
        this.cvValue = cvValue;
    }

    public String getPyCode()
    {
        return pyCode;
    }

    public void setPyCode(String pyCode)
    {
        this.pyCode = pyCode;
    }

    public String getCvId()
    {
        return cvId;
    }

    public void setCvId(String cvId)
    {
        this.cvId = cvId;
    }

    public String getSeqNo()
    {
        return seqNo;
    }

    public void setSeqNo(String seqNo)
    {
        this.seqNo = seqNo;
    }

    // Author :jin_peng
    // Date : 2013/04/02 14:56
    // [BUG]0014747 ADD BEGIN
    public String getFullName()
    {
        return fullName;
    }

    public void setFullName(String fullName)
    {
        this.fullName = fullName;
    }
    
 // [BUG]0014747 ADD END
}
