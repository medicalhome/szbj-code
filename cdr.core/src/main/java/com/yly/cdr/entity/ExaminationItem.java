package com.yly.cdr.entity;

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

import java.io.Serializable;

@Entity
@Table(name = "EXAMINATION_ITEM")
public class ExaminationItem implements Serializable
{

    private BigDecimal itemSn;

    private BigDecimal examReportSn;

    private String examiningDoctor;

    private String examiningDoctorName;

    private String examinationMethodName;

    private String examinationMethod;

    private Date createTime;

    private Date updateTime;

    private BigDecimal updateCount;

    private String deleteFlag;

    private Date deleteTime;

    private String imagingFinding;

    private String imagingConclusion;

    private String imageIndexNo;

    private String createby;

    private String updateby;

    private String deleteby;
    @Id
    @GeneratedValue(generator = "guid-generator")
    @Generator(name = "guid-generator", strategy = "guid")
    public BigDecimal getItemSn()
    {
        return this.itemSn;
    }

    public void setItemSn(BigDecimal itemSn)
    {
        this.itemSn = itemSn;
    }

    @Column(name = "EXAM_REPORT_SN", nullable = false, precision = 22, scale = 0)
    public BigDecimal getExamReportSn()
    {
        return this.examReportSn;
    }

    public void setExamReportSn(BigDecimal examReportSn)
    {
        this.examReportSn = examReportSn;
    }

    @Column(name = "EXAMINING_DOCTOR", length = 56)
    public String getExaminingDoctor()
    {
        return this.examiningDoctor;
    }

    public void setExaminingDoctor(String examiningDoctor)
    {
        this.examiningDoctor = examiningDoctor;
    }

    @Column(name = "EXAMINING_DOCTOR_NAME", length = 56)
    public String getExaminingDoctorName()
    {
        return this.examiningDoctorName;
    }

    public void setExaminingDoctorName(String examiningDoctorName)
    {
        this.examiningDoctorName = examiningDoctorName;
    }

    @Column(name = "EXAMINATION_METHOD_NAME", length = 600)
    public String getExaminationMethodName()
    {
        return this.examinationMethodName;
    }

    public void setExaminationMethodName(String examinationMethodName)
    {
        this.examinationMethodName = examinationMethodName;
    }

    @Column(name = "EXAMINATION_METHOD", length = 32)
    public String getExaminationMethod()
    {
        return this.examinationMethod;
    }

    public void setExaminationMethod(String examinationMethod)
    {
        this.examinationMethod = examinationMethod;
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

    @Column(name = "IMAGING_FINDING", length = 56)
    public String getImagingFinding()
    {
        return this.imagingFinding;
    }

    public void setImagingFinding(String imagingFinding)
    {
        this.imagingFinding = imagingFinding;
    }

    @Column(name = "IMAGING_CONCLUSION", length = 56)
    public String getImagingConclusion()
    {
        return this.imagingConclusion;
    }

    public void setImagingConclusion(String imagingConclusion)
    {
        this.imagingConclusion = imagingConclusion;
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

}
