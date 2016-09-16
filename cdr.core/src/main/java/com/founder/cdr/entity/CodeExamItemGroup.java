package com.founder.cdr.entity;

import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

import com.founder.fasf.core.util.daohelper.annotation.Generator;
import com.founder.fasf.core.util.daohelper.annotation.Parameter;

import java.io.Serializable;

@Entity
@Table(name = "CODE_EXAM_ITEM_GROUP")
public class CodeExamItemGroup implements Serializable
{

    private BigDecimal examItemGroupId;

    private BigDecimal csId;

    private String code;

    private String name;

    private String pyCode;

    private String reportHeader;

    private String reportFooter;

    private String applyTemplate;

    private String appointmentReportCode;

    private String aheadExamTime;

    private String tmReportCode;

    private String scanFlag;

    private String weightFlag;

    private String sameDayFlag;

    private String seqNo;

    private String versionNo;

    private Date createTime;

    private Date updateTime;

    private BigDecimal updateCount;

    private String deleteFlag;

    private Date deleteTime;

    @Id
    @GeneratedValue(generator = "native-generator")
    @Generator(name = "native-generator", strategy = "native", parameters = { @Parameter(name = "sequence", value = "SEQ_CODE_EXAM_ITEM_GROUP") })
    public BigDecimal getExamItemGroupId()
    {
        return this.examItemGroupId;
    }

    public void setExamItemGroupId(BigDecimal examItemGroupId)
    {
        this.examItemGroupId = examItemGroupId;
    }

    @Column(name = "CS_ID", nullable = false, precision = 22, scale = 0)
    public BigDecimal getCsId()
    {
        return this.csId;
    }

    public void setCsId(BigDecimal csId)
    {
        this.csId = csId;
    }

    @Column(name = "CODE", length = 38)
    public String getCode()
    {
        return this.code;
    }

    public void setCode(String code)
    {
        this.code = code;
    }

    @Column(name = "NAME", length = 38)
    public String getName()
    {
        return this.name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    @Column(name = "PY_CODE", length = 38)
    public String getPyCode()
    {
        return this.pyCode;
    }

    public void setPyCode(String pyCode)
    {
        this.pyCode = pyCode;
    }

    @Column(name = "REPORT_HEADER", length = 38)
    public String getReportHeader()
    {
        return this.reportHeader;
    }

    public void setReportHeader(String reportHeader)
    {
        this.reportHeader = reportHeader;
    }

    @Column(name = "REPORT_FOOTER", length = 38)
    public String getReportFooter()
    {
        return this.reportFooter;
    }

    public void setReportFooter(String reportFooter)
    {
        this.reportFooter = reportFooter;
    }

    @Column(name = "APPLY_TEMPLATE", length = 38)
    public String getApplyTemplate()
    {
        return this.applyTemplate;
    }

    public void setApplyTemplate(String applyTemplate)
    {
        this.applyTemplate = applyTemplate;
    }

    @Column(name = "APPOINTMENT_REPORT_CODE", length = 38)
    public String getAppointmentReportCode()
    {
        return this.appointmentReportCode;
    }

    public void setAppointmentReportCode(String appointmentReportCode)
    {
        this.appointmentReportCode = appointmentReportCode;
    }

    @Column(name = "AHEAD_EXAM_TIME", length = 38)
    public String getAheadExamTime()
    {
        return this.aheadExamTime;
    }

    public void setAheadExamTime(String aheadExamTime)
    {
        this.aheadExamTime = aheadExamTime;
    }

    @Column(name = "TM_REPORT_CODE", length = 38)
    public String getTmReportCode()
    {
        return this.tmReportCode;
    }

    public void setTmReportCode(String tmReportCode)
    {
        this.tmReportCode = tmReportCode;
    }

    @Column(name = "SCAN_FLAG", length = 38)
    public String getScanFlag()
    {
        return this.scanFlag;
    }

    public void setScanFlag(String scanFlag)
    {
        this.scanFlag = scanFlag;
    }

    @Column(name = "WEIGHT_FLAG", length = 38)
    public String getWeightFlag()
    {
        return this.weightFlag;
    }

    public void setWeightFlag(String weightFlag)
    {
        this.weightFlag = weightFlag;
    }

    @Column(name = "SAME_DAY_FLAG", length = 38)
    public String getSameDayFlag()
    {
        return this.sameDayFlag;
    }

    public void setSameDayFlag(String sameDayFlag)
    {
        this.sameDayFlag = sameDayFlag;
    }

    @Column(name = "SEQ_NO", length = 32)
    public String getSeqNo()
    {
        return this.seqNo;
    }

    public void setSeqNo(String seqNo)
    {
        this.seqNo = seqNo;
    }

    @Column(name = "VERSION_NO", length = 32)
    public String getVersionNo()
    {
        return versionNo;
    }

    public void setVersionNo(String versionNo)
    {
        this.versionNo = versionNo;
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

}
