package com.founder.cdr.entity;

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
@Table(name = "WARNING_NOTICE")
public class WarningNotice
{
    private BigDecimal warningNoticeSn;
    private String unitId;
    private String userId;
    private String rulegroupId;
    private String userType;
    private Date createTime;
    private Date updateTime;
    private BigDecimal updateCount;
    private String deleteFlag;
    private Date deleteTime;
    private String orgCode;
    private String orgName;
    /* $Author :chang_xuewen
     * $Date : 2014/03/21 11:00$
     * [BUG]0043136 ADD BEGIN
     */
    private String wardNo;
    /*[BUG]0043136 ADD END*/
    @Id
    @GeneratedValue(generator = "native-generator")
    @Generator(name = "native-generator", strategy = "native", parameters = { @Parameter(name = "sequence", value = "SEQ_WARNING_NOTICE") })
    public BigDecimal getWarningNoticeSn()
    {
        return warningNoticeSn;
    }
    public void setWarningNoticeSn(BigDecimal warningNoticeSn)
    {
        this.warningNoticeSn = warningNoticeSn;
    }

    @Column(name = "UNIT_ID", length = 50)
    public String getUnitId()
    {
        return unitId;
    }
    public void setUnitId(String unitId)
    {
        this.unitId = unitId;
    }
    
    
    @Column(name = "USER_ID", nullable = false, length = 50)
    public String getUserId()
    {
        return userId;
    }
    public void setUserId(String userId)
    {
        this.userId = userId;
    }
    
    @Column(name = "RULEGROUP_ID", nullable = false, length = 50)
    public String getRulegroupId()
    {
        return rulegroupId;
    }
    public void setRulegroupId(String rulegroupId)
    {
        this.rulegroupId = rulegroupId;
    }
    
    @Column(name = "USER_TYPE", length = 10)
    public String getUserType()
    {
        return userType;
    }
    public void setUserType(String userType)
    {
        this.userType = userType;
    }
    
    @Temporal(TemporalType.DATE)
    @Column(name = "CREATE_TIME", nullable = false, length = 7)
    public Date getCreateTime()
    {
        return createTime;
    }

    public void setCreateTime(Date createTime)
    {
        this.createTime = createTime;
    }

    @Temporal(TemporalType.DATE)
    @Column(name = "UPDATE_TIME", nullable = false, length = 7)
    public Date getUpdateTime()
    {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime)
    {
        this.updateTime = updateTime;
    }

    @Column(name = "UPDATE_COUNT", nullable = false, precision = 22, scale = 0)
    public BigDecimal getUpdateCount()
    {
        return updateCount;
    }

    public void setUpdateCount(BigDecimal updateCount)
    {
        this.updateCount = updateCount;
    }

    @Column(name = "DELETE_FLAG", nullable = false, length = 1)
    public String getDeleteFlag()
    {
        return deleteFlag;
    }

    public void setDeleteFlag(String deleteFlag)
    {
        this.deleteFlag = deleteFlag;
    }

    @Temporal(TemporalType.DATE)
    @Column(name = "DELETE_TIME", length = 7)
    public Date getDeleteTime()
    {
        return deleteTime;
    }

    public void setDeleteTime(Date deleteTime)
    {
        this.deleteTime = deleteTime;
    }
    
    @Column(name = "ORG_CODE", length = 50)
	public String getOrgCode() {
		return orgCode;
	}
	
	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}
	
	@Column(name = "ORG_NAME", length = 50)
	public String getOrgName() {
		return orgName;
	}
	
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	@Column(name = "WARD_NO", length = 50)
	public String getWardNo() {
		return wardNo;
	}
	
	public void setWardNo(String wardNo) {
		this.wardNo = wardNo;
	}
}
