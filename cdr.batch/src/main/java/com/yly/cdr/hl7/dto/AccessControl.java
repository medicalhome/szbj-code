package com.yly.cdr.hl7.dto;

import org.hibernate.validator.constraints.NotEmpty;


public class AccessControl extends BaseDto
{
    /**
     * 操作类型
     */
    @NotEmpty(message="{AccessControl.newUpFlag}")
    private String newUpFlag;
    /**
     * 用户/角色区分 0-用户、1-角色
     */
    @NotEmpty(message="{AccessControl.userOrRole}")
    private String userOrRole;
    /**
     * 用户ID/角色ID
     */
    @NotEmpty(message="{AccessControl.userOrRoleNo}")
    private String userOrRoleNo;
    /**
     * 权限ID
     */
    @NotEmpty(message="{AccessControl.authId}")
    private String authId;
    /**
     * 权限描述
     */
    @NotEmpty(message="{AccessControl.authDesc}")
    private String authDesc;

    public String getNewUpFlag()
    {
        return newUpFlag;
    }

    public String getUserOrRole()
    {
        return userOrRole;
    }

    public String getUserOrRoleNo()
    {
        return userOrRoleNo;
    }

    public String getAuthId()
    {
        return authId;
    }

    public String getAuthDesc()
    {
        return authDesc;
    }

}
