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
import com.founder.fasf.core.util.daohelper.annotation.Parameter;

import java.io.Serializable;

@Entity
@Table(name = "PATIENT_FAV_FOLDER")
public class PatientFavFolder implements Serializable
{
    private BigDecimal folderSn;

    private String folderName;

    private String createUserId;

    private Date createTime;

    private Date updateTime;

    private BigDecimal updateCount;

    private String deleteFlag;

    private Date deleteTime;
    
    private BigDecimal parentFolderSn;

    @Id
    @GeneratedValue(generator = "native-generator")
    @Generator(name = "native-generator", strategy = "native", parameters = { @Parameter(name = "sequence", value = "SEQ_PATIENT_FAV_FOLDER") })
    public BigDecimal getFolderSn()
    {
        return this.folderSn;
    }

    public void setFolderSn(BigDecimal folderSn)
    {
        this.folderSn = folderSn;
    }

    @Column(name = "FOLDER_NAME", nullable = false, length = 100)
    public String getFolderName()
    {
        return this.folderName;
    }

    public void setFolderName(String folderName)
    {
        this.folderName = folderName;
    }

    @Column(name = "CREATE_USER_ID", nullable = false, length = 12)
    public String getCreateUserId()
    {
        return this.createUserId;
    }

    public void setCreateUserId(String createUserId)
    {
        this.createUserId = createUserId;
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

    @Column(name = "PARENT_FOLDER_SN", precision = 22, scale = 0)
    public BigDecimal getParentFolderSn()
    {
        return parentFolderSn;
    }

    public void setParentFolderSn(BigDecimal parentFolderSn)
    {
        this.parentFolderSn = parentFolderSn;
    }
    
}
