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
@Table(name = "CODE_EXAM_ITEM")
public class CodeExamItem implements Serializable
{

    private BigDecimal codeExamItemId;

    private BigDecimal csId;

    private String code;

    private String typeCode;

    private String name;

    private String pyCode;

    private String reportHeader;

    private String reportFooter;

    private String itemClass;

    private String execUnit;

    private String yzOrderCode;

    private String subClass;

    private String examRegion;

    private String kfFlag;

    private String dyFlag;

    private String wcjcFlag;

    private String kyFlag;

    private String seqNo;

    private String versionNo;

    private String arriveTime;

    private String aheadExamTime;

    private String compareCode;

    private String groupSn;

    private String defaultTemplate;

    private String comments;

    private String mzZyFlag;

    private Date createTime;

    private Date updateTime;

    private BigDecimal updateCount;

    private String deleteFlag;

    private Date deleteTime;

    @Id
    @GeneratedValue(generator = "native-generator")
    @Generator(name = "native-generator", strategy = "native", parameters = { @Parameter(name = "sequence", value = "SEQ_CODE_EXAM_ITEM") })
    public BigDecimal getCodeExamItemId()
    {
        return this.codeExamItemId;
    }

    public void setCodeExamItemId(BigDecimal codeExamItemId)
    {
        this.codeExamItemId = codeExamItemId;
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

    @Column(name = "TYPE_CODE", length = 38)
    public String getTypeCode()
    {
        return this.typeCode;
    }

    public void setTypeCode(String typeCode)
    {
        this.typeCode = typeCode;
    }

    @Column(name = "NAME", length = 50)
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

    @Column(name = "ITEM_CLASS", length = 38)
    public String getItemClass()
    {
        return this.itemClass;
    }

    public void setItemClass(String itemClass)
    {
        this.itemClass = itemClass;
    }

    @Column(name = "EXEC_UNIT", length = 38)
    public String getExecUnit()
    {
        return this.execUnit;
    }

    public void setExecUnit(String execUnit)
    {
        this.execUnit = execUnit;
    }

    @Column(name = "YZ_ORDER_CODE", length = 38)
    public String getYzOrderCode()
    {
        return this.yzOrderCode;
    }

    public void setYzOrderCode(String yzOrderCode)
    {
        this.yzOrderCode = yzOrderCode;
    }

    @Column(name = "SUB_CLASS", length = 38)
    public String getSubClass()
    {
        return this.subClass;
    }

    public void setSubClass(String subClass)
    {
        this.subClass = subClass;
    }

    @Column(name = "EXAM_REGION", length = 38)
    public String getExamRegion()
    {
        return this.examRegion;
    }

    public void setExamRegion(String examRegion)
    {
        this.examRegion = examRegion;
    }

    @Column(name = "KF_FLAG", length = 38)
    public String getKfFlag()
    {
        return this.kfFlag;
    }

    public void setKfFlag(String kfFlag)
    {
        this.kfFlag = kfFlag;
    }

    @Column(name = "DY_FLAG", length = 38)
    public String getDyFlag()
    {
        return this.dyFlag;
    }

    public void setDyFlag(String dyFlag)
    {
        this.dyFlag = dyFlag;
    }

    @Column(name = "WCJC_FLAG", length = 38)
    public String getWcjcFlag()
    {
        return this.wcjcFlag;
    }

    public void setWcjcFlag(String wcjcFlag)
    {
        this.wcjcFlag = wcjcFlag;
    }

    @Column(name = "KY_FLAG", length = 38)
    public String getKyFlag()
    {
        return this.kyFlag;
    }

    public void setKyFlag(String kyFlag)
    {
        this.kyFlag = kyFlag;
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

    @Column(name = "VERSION_NO", length = 32)
    public String getVersionNo()
    {
        return versionNo;
    }

    public void setVersionNo(String versionNo)
    {
        this.versionNo = versionNo;
    }

    @Column(name = "ARRIVE_TIME", length = 38)
    public String getArriveTime()
    {
        return this.arriveTime;
    }

    public void setArriveTime(String arriveTime)
    {
        this.arriveTime = arriveTime;
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

    @Column(name = "COMPARE_CODE", length = 38)
    public String getCompareCode()
    {
        return this.compareCode;
    }

    public void setCompareCode(String compareCode)
    {
        this.compareCode = compareCode;
    }

    @Column(name = "GROUP_SN", length = 38)
    public String getGroupSn()
    {
        return this.groupSn;
    }

    public void setGroupSn(String groupSn)
    {
        this.groupSn = groupSn;
    }

    @Column(name = "DEFAULT_TEMPLATE", length = 38)
    public String getDefaultTemplate()
    {
        return this.defaultTemplate;
    }

    public void setDefaultTemplate(String defaultTemplate)
    {
        this.defaultTemplate = defaultTemplate;
    }

    @Column(name = "COMMENTS", length = 250)
    public String getComments()
    {
        return this.comments;
    }

    public void setComments(String comments)
    {
        this.comments = comments;
    }

    @Column(name = "MZ_ZY_FLAG", length = 38)
    public String getMzZyFlag()
    {
        return this.mzZyFlag;
    }

    public void setMzZyFlag(String mzZyFlag)
    {
        this.mzZyFlag = mzZyFlag;
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
