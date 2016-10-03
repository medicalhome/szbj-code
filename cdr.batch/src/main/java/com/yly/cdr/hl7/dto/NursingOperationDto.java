package com.yly.cdr.hl7.dto;

import java.util.List;

import org.hibernate.validator.constraints.NotEmpty;

public class NursingOperationDto extends BaseDto
{
    /**
     * 护理操作时间
     */
    @NotEmpty(message = "{NursingOperationDto.nursingOperationTime}")
    private String nursingOperationTime;
    /**
     * 操作执行人编码
     */
//    @NotEmpty(message = "{NursingOperationDto.operatorId}")
    private String operatorId;
    /**
     * 操作执行人名称
     */
//    @NotEmpty(message = "{NursingOperationDto.operatorName}")
    private String operatorName;
    /**
     * 体格检查
     */
    @NotEmpty(message = "{NursingOperationDto.physicalExamDto}")
    private List<PhysicalExaminationDto> physicalExamDto;

    public String getNursingOperationTime()
    {
        return nursingOperationTime;
    }

    public String getOperatorId()
    {
        return operatorId;
    }

    public String getOperatorName()
    {
        return operatorName;
    }

    public List<PhysicalExaminationDto> getPhysicalExamDto()
    {
        return physicalExamDto;
    }
}
