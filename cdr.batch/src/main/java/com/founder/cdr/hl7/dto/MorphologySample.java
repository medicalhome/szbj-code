package com.founder.cdr.hl7.dto;

import java.util.List;

import org.hibernate.validator.constraints.NotEmpty;

public class MorphologySample extends BaseDto
{
    /**
     * 标本采集日期(取材日期)
     */
    private String effectiveTime;
    /**
     * 取材部位编码
     */
    private String samplingPartsBody;
    /**
     * 取材部位名称
     */
    private String samplingPartsBodyName;
    /**
     * 标本条码号
     */
    //@NotEmpty(message="{MorphologySample.sampleNo}")
    private String sampleNo;
    /**
     * 标本类型编码
     */
    private String sampleType;
    /**
     * 标本类型名称
     */
    private String sampleTypeName;
    /**
     * 接收时间
     */
    private String receiveTime;
    /**
     * 送检医院
     */
    private String submitHospital;
    /**
     * 方法描述
     */
    private String methodDescription;
    /**
     * 染色方法编码
     */
    private String dyeMethodCode;
    /**
     * 染色方法名称
     */
    private String dyeMethodName;
    /**
     * 制备方法编码
     */
    private String prepareMethodCode;
    /**
     * 制备方法名称
     */
    private String prepareMethodName;
    /**
     * 检测方法编码
     */
    private String detectMethodCode;
    /**
     * 检测方法名称
     */
    private String detectMethodName;
    /**
     * 影像信息
     */
    private List<MorphologyImageContent> morphologyImageContent;

    public String getEffectiveTime()
    {
        return effectiveTime;
    }

    public String getSamplingPartsBody()
    {
        return samplingPartsBody;
    }

    public String getSamplingPartsBodyName()
    {
        return samplingPartsBodyName;
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

    public String getReceiveTime()
    {
        return receiveTime;
    }

    public String getSubmitHospital()
    {
        return submitHospital;
    }

    public String getMethodDescription()
    {
        return methodDescription;
    }

    public String getDyeMethodCode()
    {
        return dyeMethodCode;
    }

    public String getDyeMethodName()
    {
        return dyeMethodName;
    }

    public String getPrepareMethodCode()
    {
        return prepareMethodCode;
    }

    public String getPrepareMethodName()
    {
        return prepareMethodName;
    }

    public String getDetectMethodCode()
    {
        return detectMethodCode;
    }

    public String getDetectMethodName()
    {
        return detectMethodName;
    }

    public List<MorphologyImageContent> getMorphologyImageContent()
    {
        return morphologyImageContent;
    }
}
