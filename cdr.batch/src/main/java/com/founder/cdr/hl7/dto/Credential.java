package com.founder.cdr.hl7.dto;

public class Credential extends BaseDto
{
    /**
     * 证件号码
     */
    private String credentialNo;
    /**
     * 证件类型
     */
    private String credentialType;
    /**
     * 证件名称
     */
    private String credentialName;

    public String getCredentialNo()
    {
        return credentialNo;
    }

    public String getCredentialType()
    {
        return credentialType;
    }

    public String getCredentialName()
    {
        return credentialName;
    }
}
