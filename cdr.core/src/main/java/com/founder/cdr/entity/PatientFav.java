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
@Table(name = "PATIENT_FAV")
public class PatientFav implements Serializable
{

    private BigDecimal patientFavSn;

    private BigDecimal patientSn;

    private BigDecimal folderSn;

    private Date createTime;

    private Date updateTime;

    private BigDecimal updateCount;

    private String deleteFlag;

    private Date deleteTime;
    
    private String memo;

    @Id
    @GeneratedValue(generator = "native-generator")
    @Generator(name = "native-generator", strategy = "native", parameters = { @Parameter(name = "sequence", value = "SEQ_PATIENT_FAV") })
    public BigDecimal getPatientFavSn()
    {
        return this.patientFavSn;
    }

    public void setPatientFavSn(BigDecimal patientFavSn)
    {
        this.patientFavSn = patientFavSn;
    }

    @Column(name = "PATIENT_SN", precision = 22, scale = 0)
    public BigDecimal getPatientSn()
    {
        return this.patientSn;
    }

    public void setPatientSn(BigDecimal patientSn)
    {
        this.patientSn = patientSn;
    }

    @Column(name = "Folder_SN", precision = 22, scale = 0)
    public BigDecimal getFolderSn()
    {
        return this.folderSn;
    }

    public void setFolderSn(BigDecimal folderSn)
    {
        this.folderSn = folderSn;
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
    @Column(name = "Memo", nullable = false, length = 1000)
    public String getMemo()
    {
        return memo;
    }

    public void setMemo(String memo)
    {
        this.memo = memo;
    }
    
    

}
