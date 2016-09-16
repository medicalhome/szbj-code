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
@Table(name = "exam_result")
public class ExamResult implements Serializable
{
	private static final long serialVersionUID = 1L;

	private BigDecimal resultSn;

    private BigDecimal menuSn;
    
    private String itemCode;
    
    private String itemName;
    
    private String itemResult;
    
    private String createby;
    
    private Date createTime;

    private Date updateTime;

    private BigDecimal updateCount;

    private String deleteFlag;

    private Date deleteTime;

    private String updateby;

    private String deleteby;
    
    @Id
    @GeneratedValue(generator = "native-generator")
    @Generator(name = "native-generator", strategy = "native", parameters = { @Parameter(name = "sequence", value = "seq_exam_result") })
	public BigDecimal getResultSn() {
		return resultSn;
	}

	public void setResultSn(BigDecimal resultSn) {
		this.resultSn = resultSn;
	}

	@Column(name = "menu_sn", length = 12)
	public BigDecimal getMenuSn() {
		return menuSn;
	}

	public void setMenuSn(BigDecimal menuSn) {
		this.menuSn = menuSn;
	}

	@Column(name = "item_code", length = 12)
	public String getItemCode() {
		return itemCode;
	}

	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}

	@Column(name = "item_Name", length = 12)
	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public String getItemResult() {
		return itemResult;
	}

	@Column(name = "item_result", length = 12)
	public void setItemResult(String itemResult) {
		this.itemResult = itemResult;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
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

    @Column(name = "deleteby", length = 12)
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
