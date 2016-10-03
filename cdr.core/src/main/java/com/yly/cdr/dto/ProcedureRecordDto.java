package com.yly.cdr.dto;

import java.util.Date;

import java.math.BigDecimal;

/**
 * V05-704:手术操作列表
 * 显示手术操作列表信息画面
 * author:黄洁玉
 * @param request
 * @return
 */
public class ProcedureRecordDto
{
    // 手术记录内部序列号
    private BigDecimal recordSn;

    // 手术内部序列号
    private String procedureSn;

    // 操作类型
    private String operationType;

    // 操作人员
    private String operator;

    // 操作时间
    private Date operationTime;

    // 操作内容
    private String operationDescription;

    public BigDecimal getRecordSn()
    {
        return recordSn;
    }

    public void setRecordSn(BigDecimal recordSn)
    {
        this.recordSn = recordSn;
    }

    public String getProcedureSn()
    {
        return procedureSn;
    }

    public void setProcedureSn(String procedureSn)
    {
        this.procedureSn = procedureSn;
    }

    public String getOperationType()
    {
        return operationType;
    }

    public void setOperationType(String operationType)
    {
        this.operationType = operationType;
    }

    public String getOperator()
    {
        return operator;
    }

    public void setOperator(String operator)
    {
        this.operator = operator;
    }

    public Date getOperationTime()
    {
        return operationTime;
    }

    public void setOperationTime(Date operationTime)
    {
        this.operationTime = operationTime;
    }

    public String getOperationDescription()
    {
        return operationDescription;
    }

    public void setOperationDescription(String operationDescription)
    {
        this.operationDescription = operationDescription;
    }
}
