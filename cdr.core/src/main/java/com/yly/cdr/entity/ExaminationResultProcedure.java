package com.yly.cdr.entity;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.founder.fasf.core.util.daohelper.annotation.Generator;
import com.founder.fasf.core.util.daohelper.annotation.Parameter;

@Entity
@Table(name = "EXAMINATION_RESULT_PROCEDURE")
public class ExaminationResultProcedure
{
    
    private BigDecimal examResultProcedureSn;
    private BigDecimal examReportSn;
    private String imageIndexNo;
    private Date operationDate;
    private String operationRoomId;
    private String operationRoomName;
    private String operatorDoctorId;
    private String operatorDoctorName;
    private String operatorAssistantId;
    private String operatorAssistantName;
    private Date createTime;
    private Date updateTime;
    private BigDecimal updateCount;
    private String deleteFlag;
    private Date deleteTime;
    private String createby;
    private String updateby;
    private String deleteby;
    private String examinationItemCode;
    private String examinationItemName;

    @Id
    @GeneratedValue(generator = "native-generator")
    @Generator(name = "native-generator", strategy = "native", parameters = { @Parameter(name = "sequence", value = "SEQ_EXAM_RESULT_REPLICATION") })
    public BigDecimal getExamResultProcedureSn()
    {
        return examResultProcedureSn;
    }

    public void setExamResultProcedureSn(BigDecimal examResultProcedureSn)
    {
        this.examResultProcedureSn = examResultProcedureSn;
    }
    
    @Column(name = "EXAM_REPORT_SN", nullable = false)
    public BigDecimal getExamReportSn()
    {
        return examReportSn;
    }

    public void setExamReportSn(BigDecimal examReportSn)
    {
        this.examReportSn = examReportSn;
    }
    @Column(name = "IMAGE_INDEX_NO", length = 48)
    public String getImageIndexNo()
    {
        return imageIndexNo;
    }

    public void setImageIndexNo(String imageIndexNo)
    {
        this.imageIndexNo = imageIndexNo;
    }

    @Temporal(TemporalType.DATE)
    @Column(name = "OPERATION_DATE", length = 7)
    public Date getOperationDate()
    {
        return operationDate;
    }

    public void setOperationDate(Date operationDate)
    {
        this.operationDate = operationDate;
    }

    @Column(name = "OPERATION_DATE", length = 12)
    public String getOperationRoomId()
    {
        return operationRoomId;
    }

    public void setOperationRoomId(String operationRoomId)
    {
        this.operationRoomId = operationRoomId;
    }

    @Column(name = "OPERATION_DATE", length = 250)
    public String getOperationRoomName()
    {
        return operationRoomName;
    }

    public void setOperationRoomName(String operationRoomName)
    {
        this.operationRoomName = operationRoomName;
    }

    @Column(name = "OPERATION_DOCTOR_ID", length = 100)
    public String getOperatorDoctorId()
    {
        return operatorDoctorId;
    }

    public void setOperatorDoctorId(String operatorDoctorId)
    {
        this.operatorDoctorId = operatorDoctorId;
    }
    
    @Column(name = "OPERATION_ASSISTANT_NAME", length = 250)
    public String getOperatorAssistantName()
    {
        return operatorAssistantName;
    }

    public void setOperatorAssistantName(String operatorAssistantName)
    {
        this.operatorAssistantName = operatorAssistantName;
    }

    @Column(name = "OPERATION_DOCTOR_NAME", length = 250)
    public String getOperatorDoctorName()
    {
        return operatorDoctorName;
    }

    public void setOperatorDoctorName(String operatorDoctorName)
    {
        this.operatorDoctorName = operatorDoctorName;
    }
    
    @Column(name = "OPERATION_ASSISTANT_ID", length = 100)
    public String getOperatorAssistantId()
    {
        return operatorAssistantId;
    }

    public void setOperatorAssistantId(String operatorAssistantId)
    {
        this.operatorAssistantId = operatorAssistantId;
    }

    @Temporal(TemporalType.DATE)
    @Column(name = "CREATE_TIME", nullable = false, length = 7)
    public Date getCreateTime()
    {
        return this.createTime;
    }

    public void setCreateTime(Date createTime)
    {
        this.createTime = createTime;
    }

    @Temporal(TemporalType.DATE)
    @Column(name = "UPDATE_TIME", nullable = false, length = 7)
    public Date getUpdateTime()
    {
        return this.updateTime;
    }

    public void setUpdateTime(Date updateTime)
    {
        this.updateTime = updateTime;
    }

    @Column(name = "UPDATE_COUNT", nullable = false, precision = 22, scale = 0)
    public BigDecimal getUpdateCount()
    {
        return this.updateCount;
    }

    public void setUpdateCount(BigDecimal updateCount)
    {
        this.updateCount = updateCount;
    }

    @Column(name = "DELETE_FLAG", nullable = false, length = 1)
    public String getDeleteFlag()
    {
        return this.deleteFlag;
    }

    public void setDeleteFlag(String deleteFlag)
    {
        this.deleteFlag = deleteFlag;
    }

    @Temporal(TemporalType.DATE)
    @Column(name = "DELETE_TIME", length = 7)
    public Date getDeleteTime()
    {
        return this.deleteTime;
    }

    public void setDeleteTime(Date deleteTime)
    {
        this.deleteTime = deleteTime;
    }
    @Column(name = "CREATEBY", length = 12)
    public String getCreateby()
    {
        return this.createby;
    }

    public void setCreateby(String createby)
    {
        this.createby = createby;
    }
    
    @Column(name = "UPDATEBY", length = 12)
    public String getUpdateby()
    {
        return this.updateby;
    }

    public void setUpdateby(String updateby)
    {
        this.updateby = updateby;
    }
    
    @Column(name = "DELETEBY", length = 12)
    public String getDeleteby()
    {
        return this.deleteby;
    }

    public void setDeleteby(String deleteby)
    {
        this.deleteby = deleteby;
    }

    @Column(name = "EXAMINATION_ITEM_CODE", length = 12)
    public String getExaminationItemCode()
    {
        return examinationItemCode;
    }

    public void setExaminationItemCode(String examinationItemCode)
    {
        this.examinationItemCode = examinationItemCode;
    }

    @Column(name = "EXAMINATION_ITEM_NAME", length = 12)
    public String getExaminationItemName()
    {
        return examinationItemName;
    }

    public void setExaminationItemName(String examinationItemName)
    {
        this.examinationItemName = examinationItemName;
    }

}
