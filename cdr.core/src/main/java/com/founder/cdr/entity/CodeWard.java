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
@Table(name = "CODE_WARD")
public class CodeWard implements Serializable
{

    private BigDecimal codeWardId;

    private BigDecimal csId;

    private String code;

    private String name;

    private String deptSn;

    private String deptName;

    private String deptPyCode;

    private String deptDCode;

    private String openbendNum;

    private String wardPyCode;

    private String wardDCode;

    private String startDayTimeKf;

    private String wardOnly;

    private String detailPrintTime;

    private String adtDeptNo;

    private String nurseUnitFlag;

    private String planBedCount;

    private String seqNo;

    private String versionNo;

    private String wardSelf;

    private String deletedInputOpera;

    private String deletedInputDate;

    private String comments;

    private String starttimeFlag;

    private String pyCode;

    private Date createTime;

    private Date updateTime;

    private BigDecimal updateCount;

    private String deleteFlag;

    private Date deleteTime;

    @Id
    @GeneratedValue(generator = "native-generator")
    @Generator(name = "native-generator", strategy = "native", parameters = { @Parameter(name = "sequence", value = "SEQ_CODE_WARD") })
    public BigDecimal getCodeWardId()
    {
        return this.codeWardId;
    }

    public void setCodeWardId(BigDecimal codeWardId)
    {
        this.codeWardId = codeWardId;
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

    @Column(name = "DEPT_SN", length = 38)
    public String getDeptSn()
    {
        return this.deptSn;
    }

    public void setDeptSn(String deptSn)
    {
        this.deptSn = deptSn;
    }

    @Column(name = "DEPT_NAME", length = 38)
    public String getDeptName()
    {
        return this.deptName;
    }

    public void setDeptName(String deptName)
    {
        this.deptName = deptName;
    }

    @Column(name = "DEPT_PY_CODE", length = 38)
    public String getDeptPyCode()
    {
        return this.deptPyCode;
    }

    public void setDeptPyCode(String deptPyCode)
    {
        this.deptPyCode = deptPyCode;
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

    @Column(name = "DEPT_D_CODE", length = 38)
    public String getDeptDCode()
    {
        return this.deptDCode;
    }

    public void setDeptDCode(String deptDCode)
    {
        this.deptDCode = deptDCode;
    }

    @Column(name = "OPENBEND_NUM", length = 38)
    public String getOpenbendNum()
    {
        return this.openbendNum;
    }

    public void setOpenbendNum(String openbendNum)
    {
        this.openbendNum = openbendNum;
    }

    @Column(name = "WARD_PY_CODE", length = 38)
    public String getWardPyCode()
    {
        return this.wardPyCode;
    }

    public void setWardPyCode(String wardPyCode)
    {
        this.wardPyCode = wardPyCode;
    }

    @Column(name = "WARD_D_CODE", length = 38)
    public String getWardDCode()
    {
        return this.wardDCode;
    }

    public void setWardDCode(String wardDCode)
    {
        this.wardDCode = wardDCode;
    }

    @Column(name = "START_DAY_TIME_KF", length = 38)
    public String getStartDayTimeKf()
    {
        return this.startDayTimeKf;
    }

    public void setStartDayTimeKf(String startDayTimeKf)
    {
        this.startDayTimeKf = startDayTimeKf;
    }

    @Column(name = "WARD_ONLY", length = 38)
    public String getWardOnly()
    {
        return this.wardOnly;
    }

    public void setWardOnly(String wardOnly)
    {
        this.wardOnly = wardOnly;
    }

    @Column(name = "DETAIL_PRINT_TIME", length = 38)
    public String getDetailPrintTime()
    {
        return this.detailPrintTime;
    }

    public void setDetailPrintTime(String detailPrintTime)
    {
        this.detailPrintTime = detailPrintTime;
    }

    @Column(name = "ADT_DEPT_NO", length = 38)
    public String getAdtDeptNo()
    {
        return this.adtDeptNo;
    }

    public void setAdtDeptNo(String adtDeptNo)
    {
        this.adtDeptNo = adtDeptNo;
    }

    @Column(name = "NURSE_UNIT_FLAG", length = 38)
    public String getNurseUnitFlag()
    {
        return this.nurseUnitFlag;
    }

    public void setNurseUnitFlag(String nurseUnitFlag)
    {
        this.nurseUnitFlag = nurseUnitFlag;
    }

    @Column(name = "PLAN_BED_COUNT", length = 38)
    public String getPlanBedCount()
    {
        return this.planBedCount;
    }

    public void setPlanBedCount(String planBedCount)
    {
        this.planBedCount = planBedCount;
    }

    @Column(name = "SEQ_NO", length = 38)
    public String getSeqNo()
    {
        return this.seqNo;
    }

    public void setSeqNo(String seqNo)
    {
        this.seqNo = seqNo;
    }

    @Column(name = "WARD_SELF", length = 38)
    public String getWardSelf()
    {
        return this.wardSelf;
    }

    public void setWardSelf(String wardSelf)
    {
        this.wardSelf = wardSelf;
    }

    @Column(name = "DELETED_INPUT_OPERA", length = 38)
    public String getDeletedInputOpera()
    {
        return this.deletedInputOpera;
    }

    public void setDeletedInputOpera(String deletedInputOpera)
    {
        this.deletedInputOpera = deletedInputOpera;
    }

    @Column(name = "DELETED_INPUT_DATE", length = 38)
    public String getDeletedInputDate()
    {
        return this.deletedInputDate;
    }

    public void setDeletedInputDate(String deletedInputDate)
    {
        this.deletedInputDate = deletedInputDate;
    }

    @Column(name = "COMMENTS", length = 200)
    public String getComments()
    {
        return this.comments;
    }

    public void setComments(String comments)
    {
        this.comments = comments;
    }

    @Column(name = "STARTTIME_FLAG", length = 38)
    public String getStarttimeFlag()
    {
        return this.starttimeFlag;
    }

    public void setStarttimeFlag(String starttimeFlag)
    {
        this.starttimeFlag = starttimeFlag;
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
