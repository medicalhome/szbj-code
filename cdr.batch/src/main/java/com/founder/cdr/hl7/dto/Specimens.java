package com.founder.cdr.hl7.dto;


public class Specimens extends BaseDto
{
    /**
     * 标本号
     */
    private String specimenid;
    /**
     * 标本类别代码
     */
    private String sampleType;
    /**
     * 标本类别代码名称
     */
    private String sampleTypeName;
    /**
     * 采样时间
     */
    private String effectiveTime;
    /**
     * 测试项目容器ID
     */
    private String itemContainerID;
    /**
     * 测试项目容器名称
     */
    private String itemContainerName;
    /**
     * 采集人Id
     */
    private String assignedId;
    /**
     * 采集人姓名
     */
    private String assignedName;
    /**
     * 采集地点
     */
    private String assignedAddr;

    public String getSpecimenid()
    {
        return specimenid;
    }

    public String getSampleType()
    {
        return sampleType;
    }

    public String getSampleTypeName()
    {
        return sampleTypeName;
    }

    public String getEffectiveTime()
    {
        return effectiveTime;
    }

    public String getItemContainerID()
    {
        return itemContainerID;
    }

    public String getItemContainerName()
    {
        return itemContainerName;
    }

    public String getAssignedId()
    {
        return assignedId;
    }

    public String getAssignedName()
    {
        return assignedName;
    }

    public String getAssignedAddr()
    {
        return assignedAddr;
    }

    @Override
    public String toString()
    {
        StringBuilder builder = new StringBuilder();
        builder.append("Specimens [specimenid=");
        builder.append(specimenid);
        builder.append(", sampleType=");
        builder.append(sampleType);
        builder.append(", sampleTypeName=");
        builder.append(sampleTypeName);
        builder.append(", effectiveTime=");
        builder.append(effectiveTime);
        builder.append(", itemContainerID=");
        builder.append(itemContainerID);
        builder.append(", itemContainerName=");
        builder.append(itemContainerName);
        builder.append(", assignedId=");
        builder.append(assignedId);
        builder.append(", assignedName=");
        builder.append(assignedName);
        builder.append(", assignedAddr=");
        builder.append(assignedAddr);
        builder.append("]");
        return builder.toString();
    }
    
    
}
