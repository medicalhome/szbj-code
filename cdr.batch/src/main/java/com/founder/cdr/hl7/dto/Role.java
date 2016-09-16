package com.founder.cdr.hl7.dto;

import org.hibernate.validator.constraints.NotEmpty;


public class Role extends BaseDto
{
    /**
     * 操作类型
     */
    @NotEmpty(message="{Role.newUpFlag}")
    private String newUpFlag;
    /**
     * 角色ID
     */
    @NotEmpty(message="{Role.roleNo}")
    private String roleNo;
    /**
     * 角色名
     */
    @NotEmpty(message="{Role.roleName}")
    private String roleName;
    /**
     * 职业类别
     */
    @NotEmpty(message="{Role.occupationalType}")
    private String occupationalType;
    /**
     * 角色用户
     */
    // $Author :tong_meng
    // $Date : 2012/9/28 16:20$
    // [BUG]0010191 DELETE BEGIN
    // @NotEmpty(message="{Role.roleUser}")
    // [BUG]0010191 DELETE BEGIN
    private String roleUser;

    public String getNewUpFlag()
    {
        return newUpFlag;
    }

    public String getRoleNo()
    {
        return roleNo;
    }

    public String getRoleName()
    {
        return roleName;
    }

    public String getOccupationalType()
    {
        return occupationalType;
    }

    public String getRoleUser()
    {
        return roleUser;
    }
}
