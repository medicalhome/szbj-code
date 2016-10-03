package com.yly.cdr.hl7.dto;

import java.util.List;

public class LabSample extends BaseDto
{
    /**
     * 操作类型
     */
    private String operateType;
    /**
     * 操作类型名称 
     */
    private String operateTypeName;
    /**
     * 采样时间
     */
    private String effectiveTime;
    /**
     * 标本号
     */
    private String sampleNo;
    /**
     * 标本类型
     */
    private String sampleType;
    /**
     * 标本类型名称
     */
    private String sampleTypeName;
    /**
     * 标本量
     */
    private String sampleQuantity;
    /**
     * 标本量单位
     */
    private String sampleQuantityUnit;
    /**
     * 标本描述说明
     */
    private String sampleRequirement;
    /**
     * 采集人
     */
    private String collectionId;
    /**
     * 采集人姓名
     */
    private String collectionName;
    /**
     * 采集地点
     */
    private String collectionPlace;
    /**
     * 采集地点名称
     */
    private String collectionPlaceName;
    /**
     * 接收时间
     */
    private String receiveTime;
    /**
     * 接收人
     */
    private String receiveId;
    /**
     * 接收人姓名
     */
    private String receiveName;
    /**
     * 容器
     */
    private String containerId;
    /**
     * 容器名称
     */
    private String containerName;
    /**
     * 影像内容
     */
    private List<LabImageContent> imageContent;

    public String getOperateType()
    {
        return operateType;
    }

    public String getOperateTypeName()
    {
        return operateTypeName;
    }

    public String getEffectiveTime()
    {
        return effectiveTime;
    }

    public String getSampleNo()
    {
        return sampleNo;
    }

    public String getSampleType()
    {
        return sampleType;
    }

    public String getSampleTypeName()
    {
        return sampleTypeName;
    }

    public String getSampleQuantity()
    {
        return sampleQuantity;
    }

    public String getSampleQuantityUnit()
    {
        return sampleQuantityUnit;
    }

    public String getSampleRequirement()
    {
        return sampleRequirement;
    }

    public String getCollectionId()
    {
        return collectionId;
    }

    public String getCollectionName()
    {
        return collectionName;
    }

    public String getCollectionPlace()
    {
        return collectionPlace;
    }

    public String getCollectionPlaceName()
    {
        return collectionPlaceName;
    }

    public String getReceiveTime()
    {
        return receiveTime;
    }

    public String getReceiveId()
    {
        return receiveId;
    }

    public String getReceiveName()
    {
        return receiveName;
    }

    public String getContainerId()
    {
        return containerId;
    }

    public String getContainerName()
    {
        return containerName;
    }

    public List<LabImageContent> getImageContent()
    {
        return imageContent;
    }

    @Override
    public String toString()
    {
        StringBuilder builder = new StringBuilder();
        builder.append("LabSample [sampleNo=");
        builder.append(sampleNo);
        builder.append("]");
        return builder.toString();
    }
    
}
