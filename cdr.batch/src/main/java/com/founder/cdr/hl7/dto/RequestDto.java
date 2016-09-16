package com.founder.cdr.hl7.dto;

import java.util.List;

public class RequestDto extends OrderDto
{
    /**
     * 申请单编号
     */
    private String requestNo;
    /**
     * 医嘱类别
     */
    private String orderType;

    /**
     * 医嘱类别名称
     */
    private String orderTypeName;
    
    /**
     * 开医嘱医生
     */
    private String orderPerson;

    /**
     * 开医嘱医生姓名
     */
    private String orderPersonName;
    

    /**
     * 检验申请日期
     */
    private String requestDate;

    /**
     * 标本
     */
    private List<Specimens> sampleInfo;

    /**
     * 检验医嘱
     */
    private List<LabOrderDto> labOrderDto;

    /*
     * $Author: yu_yzh
     * $Date: 2015/7/31 14:42
     * 添加费用字段
     * ADD BEGIN
     * */
    /**
     * 检验费用
     * */
    private String charge;

	// ADD END
    /**
     * 药观编码
     */
    private String medicalObs;

    /**
     * 药观名称
     */
    private String medicalObsName;

    /**
     * 备注1
     */
    private String requestMemo1;

    /**
     * 备注2
     */
    private String requestMemo2;

    /**
     * 备注3
     */
    private String requestMemo3;

    /**
     * 备注4
     */
    private String requestMemo4;

    public String getRequestNo()
    {
        return requestNo;
    }

    public String getOrderType()
    {
        return orderType;
    }

    public String getOrderTypeName()
    {
        return orderTypeName;
    }

    public String getRequestDate()
    {
        return requestDate;
    }

    public List<Specimens> getSampleInfo()
    {
        return sampleInfo;
    }

    public List<LabOrderDto> getLabOrderDto()
    {
        return labOrderDto;
    }

    public String getMedicalObs()
    {
        return medicalObs;
    }

    public String getMedicalObsName()
    {
        return medicalObsName;
    }

    public String getRequestMemo1()
    {
        return requestMemo1;
    }

    public String getRequestMemo2()
    {
        return requestMemo2;
    }

    public String getRequestMemo3()
    {
        return requestMemo3;
    }

    public String getRequestMemo4()
    {
        return requestMemo4;
    }

    public String getCharge() {
		return charge;
	}

    @Override
    public String toString()
    {
        StringBuilder builder = new StringBuilder();
        builder.append("RequestDto [requestNo=");
        builder.append(requestNo);
        builder.append(", orderType=");
        builder.append(orderType);
        builder.append(", labOrderDto=");
        builder.append(labOrderDto);
        builder.append(", sampleInfo=");
        builder.append(sampleInfo);
        builder.append(", medicalObs=");
        builder.append(medicalObs);
        builder.append("]");
        return builder.toString();
    }

	public String getOrderPerson() {
		return orderPerson;
	}

	public String getOrderPersonName() {
		return orderPersonName;
	}
}
