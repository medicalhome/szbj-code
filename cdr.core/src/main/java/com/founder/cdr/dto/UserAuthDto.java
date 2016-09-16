package com.founder.cdr.dto;

public class UserAuthDto
{
    private String userId;

    private String systemAuthId;

    /**
     * 权限类型
     */
    private String authType;

    public String getUserId()
    {
        return userId;
    }

    public void setUserId(String userId)
    {
        this.userId = userId;
    }

    public String getSystemAuthId()
    {
        return systemAuthId;
    }

    public void setSystemAuthId(String systemAuthId)
    {
        this.systemAuthId = systemAuthId;
    }

    public String getAuthType()
    {
        return authType;
    }

    public void setAuthType(String authType)
    {
        this.authType = authType;
    }
}
