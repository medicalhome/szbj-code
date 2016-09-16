package com.founder.cdr.entity;

import java.io.Serializable;
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
@Table(name = "blood_record_special_request")
public class BloodRecordSpecialRequest implements Serializable
{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private BigDecimal specialSn;

    private BigDecimal menuSn;
    
    private String specialRequestCode;
    
    private String specialRequestName;
    
    private String createby;

    private Date createTime;
    
    private String updateby;

    private Date updateTime;

    private BigDecimal updateCount;
    
    private String deleteby;
    
    private Date deleteTime;

    private String deleteFlag;

   
    @Id
    @GeneratedValue(generator = "native-generator")
    @Generator(name = "native-generator", strategy = "native", parameters = { @Parameter(name = "sequence", value = "seq_record_special_request") })
    public BigDecimal getSpecialSn()
    {
        return this.specialSn;
    }

    public void setSpecialSn(BigDecimal specialSn)
    {
        this.specialSn = specialSn;
    }

    @Column(name = "menu_sn", nullable = false, precision = 22, scale = 0)
    public BigDecimal getMenuSn()
    {
        return this.menuSn;
    }

    public void setMenuSn(BigDecimal menuSn)
    {
        this.menuSn = menuSn;
    }

    @Column(name = "special_request_code", length = 20)
    public String getSpecialRequestCode()
    {
        return this.specialRequestCode;
    }

    public void setSpecialRequestCode(String specialRequestCode)
    {
        this.specialRequestCode = specialRequestCode;
    }
    
    @Column(name = "special_request_name", length = 300)
    public String getSpecialRequestName()
    {
        return this.specialRequestName;
    }

    public void setSpecialRequestName(String specialRequestName)
    {
        this.specialRequestName = specialRequestName;
    }
    
    @Column(name = "createby", length = 12)
    public String getCreateby()
    {
        return this.createby;
    }

    public void setCreateby(String createby)
    {
        this.createby = createby;
    }

    @Temporal(TemporalType.DATE)
    @Column(name = "create_time", nullable = false, length = 7)
    public Date getCreateTime()
    {
        return this.createTime;
    }

    public void setCreateTime(Date createTime)
    {
        this.createTime = createTime;
    }

    @Column(name = "updateby", length = 12)
    public String getUpdateby()
    {
        return this.updateby;
    }

    public void setUpdateby(String updateby)
    {
        this.updateby = updateby;
    }
    
    @Temporal(TemporalType.DATE)
    @Column(name = "update_time", nullable = false, length = 7)
    public Date getUpdateTime()
    {
        return this.updateTime;
    }

    public void setUpdateTime(Date updateTime)
    {
        this.updateTime = updateTime;
    }

    @Column(name = "update_count", nullable = false, precision = 22, scale = 0)
    public BigDecimal getUpdateCount()
    {
        return this.updateCount;
    }

    public void setUpdateCount(BigDecimal updateCount)
    {
        this.updateCount = updateCount;
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

    @Temporal(TemporalType.DATE)
    @Column(name = "delete_time", length = 7)
    public Date getDeleteTime()
    {
        return this.deleteTime;
    }

    public void setDeleteTime(Date deleteTime)
    {
        this.deleteTime = deleteTime;
    }
    
    @Column(name = "delete_flag", nullable = false, length = 1)
    public String getDeleteFlag()
    {
        return this.deleteFlag;
    }

    public void setDeleteFlag(String deleteFlag)
    {
        this.deleteFlag = deleteFlag;
    }

}
