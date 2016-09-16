package com.founder.cdr.entity;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

import com.founder.fasf.core.util.daohelper.annotation.Generator;

import java.io.Serializable;

@Entity
@Table(name = "MEDICAL_IMAGING")
public class MedicalImaging implements Serializable
{

    private BigDecimal imagingSn;

    private BigDecimal reportSn;

    private BigDecimal documentSn;

    private BigDecimal refType;

    private String promptMessage;

    private String imageFormat;

    private Date createTime;

    private Date updateTime;

    private BigDecimal updateCount;

    private String deleteFlag;

    private Date deleteTime;

    private byte[] imageContent;

    private String imageType;

    private String createby;

    private String updateby;

    private String deleteby;
    
    private String filePath;
    @Id
    public BigDecimal getImagingSn()
    {
        return this.imagingSn;
    }

    public void setImagingSn(BigDecimal imagingSn)
    {
        this.imagingSn = imagingSn;
    }

    @Column(name = "REPORT_SN", precision = 22, scale = 0)
    public BigDecimal getReportSn()
    {
        return this.reportSn;
    }

    public void setReportSn(BigDecimal reportSn)
    {
        this.reportSn = reportSn;
    }

    public BigDecimal getDocumentSn()
    {
        return documentSn;
    }

    @Column(name = "DOCUMENT_SN", precision = 22, scale = 0)
    public void setDocumentSn(BigDecimal documentSn)
    {
        this.documentSn = documentSn;
    }

    @Column(name = "REF_TYPE", precision = 22, scale = 0)
    public BigDecimal getRefType()
    {
        return this.refType;
    }

    public void setRefType(BigDecimal refType)
    {
        this.refType = refType;
    }

    @Column(name = "PROMPT_MESSAGE", length = 100)
    public String getPromptMessage()
    {
        return this.promptMessage;
    }

    public void setPromptMessage(String promptMessage)
    {
        this.promptMessage = promptMessage;
    }

    @Column(name = "IMAGE_FORMAT", length = 20)
    public String getImageFormat()
    {
        return this.imageFormat;
    }

    public void setImageFormat(String imageFormat)
    {
        this.imageFormat = imageFormat;
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

    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(name = "IMAGE_CONTENT", columnDefinition = "BLOB")
    public byte[] getImageContent()
    {
        return imageContent;
    }

    public void setImageContent(byte[] imageContent)
    {
        this.imageContent = imageContent;
    }

    @Column(name = "IMAGE_TYPE", length = 2)
    public String getImageType()
    {
        return imageType;
    }

    public void setImageType(String imageType)
    {
        this.imageType = imageType;
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
    
    @Column(name = "FILE_PATH", length = 400)
	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

}
