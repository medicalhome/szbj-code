package com.yly.cdr.entity;

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
@Table(name = "TRANSFER_HISTORY")
public class TransferHistory implements Serializable
{

    private BigDecimal transferSn;

    private BigDecimal visitSn;

    private BigDecimal patientSn;

    private String patientDomain;

    private String patientLid;

    private String orderPersonName;

    private Date orderTime;

    private String confirmPersonName;

    private Date confirmTime;

    private String cancelPersonName;

    private Date cancelTime;

    private String fromBed;

    private String toBed;

    private Date createTime;

    private Date updateTime;

    private BigDecimal updateCount;

    private String deleteFlag;

    private Date deleteTime;

    private String execPersonId;

    private String execPersonName;

    private Date execTime;

    private String orderLid;

    private String orderTypeCode;

    private String orderTypeName;

    private String orderDeptId;

    private String orderDeptName;

    private String fromDeptId;

    private String fromDeptName;

    private String fromWardId;

    private String fromWardName;

    private String toDeptId;

    private String toDeptName;

    private String toWardId;

    private String toWardName;

    private String destinationCode;

    private String destinationName;

    private String orderPersonId;

    private String confirmPersonId;

    private String cancelPersonId;

    private String patientLocCode;

    private String patientLocName;

    private String gradingCode;

    private String gradingName;

    private String viewRoomOutWayCode;

    private String viewRoomOutWayName;
    
    private String createby;
    
    private String updateby;
    private String deleteby;
    // $Author:tong_meng
    // $Date:2013/12/03 11:00
    // [BUG]0040270 ADD BEGIN
    // 医疗机构代码
    private String orgCode;
    // 医疗机构名称
    private String orgName;
    
    private String newRoomCode;
    
    private String newRoomNo;
    
    private String oldRoomCode;
    
    private String oldRoomNo;
    // [BUG]0040270 ADD END
    
    @Id
    @GeneratedValue(generator = "native-generator")
    @Generator(name = "native-generator", strategy = "native", parameters = { @Parameter(name = "sequence", value = "SEQ_ORDER") })
    public BigDecimal getTransferSn()
    {
        return this.transferSn;
    }

    public void setTransferSn(BigDecimal transferSn)
    {
        this.transferSn = transferSn;
    }

    @Column(name = "VISIT_SN", nullable = false, precision = 22, scale = 0)
    public BigDecimal getVisitSn()
    {
        return this.visitSn;
    }

    public void setVisitSn(BigDecimal visitSn)
    {
        this.visitSn = visitSn;
    }

    @Column(name = "PATIENT_SN", nullable = false, precision = 22, scale = 0)
    public BigDecimal getPatientSn()
    {
        return this.patientSn;
    }

    public void setPatientSn(BigDecimal patientSn)
    {
        this.patientSn = patientSn;
    }

    @Column(name = "PATIENT_DOMAIN", nullable = false, length = 6)
    public String getPatientDomain()
    {
        return this.patientDomain;
    }

    public void setPatientDomain(String patientDomain)
    {
        this.patientDomain = patientDomain;
    }

    @Column(name = "PATIENT_LID", nullable = false, length = 12)
    public String getPatientLid()
    {
        return this.patientLid;
    }

    public void setPatientLid(String patientLid)
    {
        this.patientLid = patientLid;
    }

    @Column(name = "ORDER_PERSON_NAME", length = 192)
    public String getOrderPersonName()
    {
        return this.orderPersonName;
    }

    public void setOrderPersonName(String orderPersonName)
    {
        this.orderPersonName = orderPersonName;
    }

    @Temporal(TemporalType.DATE)
    @Column(name = "ORDER_TIME", length = 7)
    public Date getOrderTime()
    {
        return this.orderTime;
    }

    public void setOrderTime(Date orderTime)
    {
        this.orderTime = orderTime;
    }

    @Column(name = "CONFIRM_PERSON_NAME", length = 192)
    public String getConfirmPersonName()
    {
        return this.confirmPersonName;
    }

    public void setConfirmPersonName(String confirmPersonName)
    {
        this.confirmPersonName = confirmPersonName;
    }

    @Temporal(TemporalType.DATE)
    @Column(name = "CONFIRM_TIME", length = 7)
    public Date getConfirmTime()
    {
        return this.confirmTime;
    }

    public void setConfirmTime(Date confirmTime)
    {
        this.confirmTime = confirmTime;
    }

    @Column(name = "CANCEL_PERSON_NAME", length = 40)
    public String getCancelPersonName()
    {
        return this.cancelPersonName;
    }

    public void setCancelPersonName(String cancelPersonName)
    {
        this.cancelPersonName = cancelPersonName;
    }

    @Temporal(TemporalType.DATE)
    @Column(name = "CANCEL_TIME", length = 7)
    public Date getCancelTime()
    {
        return this.cancelTime;
    }

    public void setCancelTime(Date cancelTime)
    {
        this.cancelTime = cancelTime;
    }

    @Column(name = "FROM_BED", length = 12)
    public String getFromBed()
    {
        return this.fromBed;
    }

    public void setFromBed(String fromBed)
    {
        this.fromBed = fromBed;
    }

    @Column(name = "TO_BED", length = 12)
    public String getToBed()
    {
        return this.toBed;
    }

    public void setToBed(String toBed)
    {
        this.toBed = toBed;
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

    @Column(name = "EXEC_PERSON_ID", length = 12)
    public String getExecPersonId()
    {
        return this.execPersonId;
    }

    public void setExecPersonId(String execPersonId)
    {
        this.execPersonId = execPersonId;
    }

    @Column(name = "EXEC_PERSON_NAME", length = 192)
    public String getExecPersonName()
    {
        return this.execPersonName;
    }

    public void setExecPersonName(String execPersonName)
    {
        this.execPersonName = execPersonName;
    }

    @Temporal(TemporalType.DATE)
    @Column(name = "EXEC_TIME", length = 7)
    public Date getExecTime()
    {
        return this.execTime;
    }

    public void setExecTime(Date execTime)
    {
        this.execTime = execTime;
    }

    @Column(name = "ORDER_LID", length = 64)
    public String getOrderLid()
    {
        return this.orderLid;
    }

    public void setOrderLid(String orderLid)
    {
        this.orderLid = orderLid;
    }

    @Column(name = "ORDER_TYPE_CODE", length = 6)
    public String getOrderTypeCode()
    {
        return this.orderTypeCode;
    }

    public void setOrderTypeCode(String orderTypeCode)
    {
        this.orderTypeCode = orderTypeCode;
    }

    @Column(name = "ORDER_TYPE_NAME", length = 40)
    public String getOrderTypeName()
    {
        return this.orderTypeName;
    }

    public void setOrderTypeName(String orderTypeName)
    {
        this.orderTypeName = orderTypeName;
    }

    @Column(name = "ORDER_DEPT_ID", length = 12)
    public String getOrderDeptId()
    {
        return this.orderDeptId;
    }

    public void setOrderDeptId(String orderDeptId)
    {
        this.orderDeptId = orderDeptId;
    }

    @Column(name = "ORDER_DEPT_NAME", length = 192)
    public String getOrderDeptName()
    {
        return this.orderDeptName;
    }

    public void setOrderDeptName(String orderDeptName)
    {
        this.orderDeptName = orderDeptName;
    }

    @Column(name = "FROM_DEPT_ID", length = 12)
    public String getFromDeptId()
    {
        return this.fromDeptId;
    }

    public void setFromDeptId(String fromDeptId)
    {
        this.fromDeptId = fromDeptId;
    }

    @Column(name = "FROM_DEPT_NAME", length = 192)
    public String getFromDeptName()
    {
        return this.fromDeptName;
    }

    public void setFromDeptName(String fromDeptName)
    {
        this.fromDeptName = fromDeptName;
    }

    @Column(name = "FROM_WARD_ID", length = 12)
    public String getFromWardId()
    {
        return this.fromWardId;
    }

    public void setFromWardId(String fromWardId)
    {
        this.fromWardId = fromWardId;
    }

    @Column(name = "FROM_WARD_NAME", length = 192)
    public String getFromWardName()
    {
        return this.fromWardName;
    }

    public void setFromWardName(String fromWardName)
    {
        this.fromWardName = fromWardName;
    }

    @Column(name = "TO_DEPT_ID", length = 12)
    public String getToDeptId()
    {
        return this.toDeptId;
    }

    public void setToDeptId(String toDeptId)
    {
        this.toDeptId = toDeptId;
    }

    @Column(name = "TO_DEPT_NAME", length = 192)
    public String getToDeptName()
    {
        return this.toDeptName;
    }

    public void setToDeptName(String toDeptName)
    {
        this.toDeptName = toDeptName;
    }

    @Column(name = "TO_WARD_ID", length = 12)
    public String getToWardId()
    {
        return this.toWardId;
    }

    public void setToWardId(String toWardId)
    {
        this.toWardId = toWardId;
    }

    @Column(name = "TO_WARD_NAME", length = 192)
    public String getToWardName()
    {
        return this.toWardName;
    }

    public void setToWardName(String toWardName)
    {
        this.toWardName = toWardName;
    }

    @Column(name = "DESTINATION_CODE", length = 12)
    public String getDestinationCode()
    {
        return this.destinationCode;
    }

    public void setDestinationCode(String destinationCode)
    {
        this.destinationCode = destinationCode;
    }

    @Column(name = "DESTINATION_NAME", length = 200)
    public String getDestinationName()
    {
        return this.destinationName;
    }

    public void setDestinationName(String destinationName)
    {
        this.destinationName = destinationName;
    }

    @Column(name = "ORDER_PERSON_ID", length = 12)
    public String getOrderPersonId()
    {
        return this.orderPersonId;
    }

    public void setOrderPersonId(String orderPersonId)
    {
        this.orderPersonId = orderPersonId;
    }

    @Column(name = "CONFIRM_PERSON_ID", length = 12)
    public String getConfirmPersonId()
    {
        return this.confirmPersonId;
    }

    public void setConfirmPersonId(String confirmPersonId)
    {
        this.confirmPersonId = confirmPersonId;
    }

    @Column(name = "CANCEL_PERSON_ID", length = 12)
    public String getCancelPersonId()
    {
        return this.cancelPersonId;
    }

    public void setCancelPersonId(String cancelPersonId)
    {
        this.cancelPersonId = cancelPersonId;
    }

    @Column(name = "PATIENT_LOC_CODE", length = 12)
    public String getPatientLocCode()
    {
        return this.patientLocCode;
    }

    public void setPatientLocCode(String patientLocCode)
    {
        this.patientLocCode = patientLocCode;
    }

    @Column(name = "PATIENT_LOC_NAME", length = 32)
    public String getPatientLocName()
    {
        return this.patientLocName;
    }

    public void setPatientLocName(String patientLocName)
    {
        this.patientLocName = patientLocName;
    }

    @Column(name = "GRADING_CODE", length = 12)
    public String getGradingCode()
    {
        return this.gradingCode;
    }

    public void setGradingCode(String gradingCode)
    {
        this.gradingCode = gradingCode;
    }

    @Column(name = "GRADING_NAME", length = 32)
    public String getGradingName()
    {
        return this.gradingName;
    }

    public void setGradingName(String gradingName)
    {
        this.gradingName = gradingName;
    }

    @Column(name = "VIEW_ROOM_OUT_WAY_CODE", length = 12)
    public String getViewRoomOutWayCode()
    {
        return this.viewRoomOutWayCode;
    }

    public void setViewRoomOutWayCode(String viewRoomOutWayCode)
    {
        this.viewRoomOutWayCode = viewRoomOutWayCode;
    }

    @Column(name = "VIEW_ROOM_OUT_WAY_NAME", length = 32)
    public String getViewRoomOutWayName()
    {
        return this.viewRoomOutWayName;
    }

    public void setViewRoomOutWayName(String viewRoomOutWayName)
    {
        this.viewRoomOutWayName = viewRoomOutWayName;
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
    @Column(name = "ORG_CODE", length = 50)
    public String getOrgCode()
    {
        return orgCode;
    }

    public void setOrgCode(String orgCode)
    {
        this.orgCode = orgCode;
    }

    @Column(name = "ORG_NAME", length = 50)
    public String getOrgName()
    {
        return orgName;
    }

    public void setOrgName(String orgName)
    {
        this.orgName = orgName;
    }

    @Column(name = "NEW_ROOM_CODE", length = 12)
	public String getNewRoomCode() {
		return newRoomCode;
	}

	public void setNewRoomCode(String newRoomCode) {
		this.newRoomCode = newRoomCode;
	}

	@Column(name = "NEW_ROOM_NO", length = 192)
	public String getNewRoomNo() {
		return newRoomNo;
	}

	public void setNewRoomNo(String newRoomNo) {
		this.newRoomNo = newRoomNo;
	}

	@Column(name = "OLD_ROOM_CODE", length = 12)
	public String getOldRoomCode() {
		return oldRoomCode;
	}

	public void setOldRoomCode(String oldRoomCode) {
		this.oldRoomCode = oldRoomCode;
	}

	@Column(name = "OLD_ROOM_NO", length = 192)
	public String getOldRoomNo() {
		return oldRoomNo;
	}

	public void setOldRoomNo(String oldRoomNo) {
		this.oldRoomNo = oldRoomNo;
	}

}
