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
@Table(name = "CLINICAL_DOCUMENT")
public class ClinicalDocument implements Serializable
{

    private BigDecimal documentSn;

    private BigDecimal visitSn;

    private BigDecimal patientSn;

    private String patientDomain;

    private String patientLid;

    private String documentType;

    private String documentName;

    private String documentAuthor;

    private String documentAuthorName;

    private Date writeTime;

    private String documentModifier;

    private String documentModifierName;

    private Date modifyTime;

    private String reviewPerson;

    private String reviewPersonName;

    private Date reviewTime;

    private Date submitTime;

    private String digitalSign;

    private String documentUrl;

    private Date signTime;

    private String documentContent;

    private String documentCda;

    private Date createTime;

    private Date updateTime;

    private BigDecimal updateCount;

    private String deleteFlag;

    private Date deleteTime;

    private String documentLid;

    private String signatureId;

    private String documentTypeName;

    private String serviceId;

    private String createby;

    private String updateby;

    private String deleteby;
    
    private String orgCode;
    
    private String orgName;
    /**
     * 申请单类型编码
     */
    private String requestCode;
    
    /**
     * 会诊申请单编号
     */
    private String requestNo;
    /**
     * 入院记录类型编码
     */
    private String inRecordType;
    
    public String getRequestCode() {
		return requestCode;
	}

	public void setRequestCode(String requestCode) {
		this.requestCode = requestCode;
	}

	public String getRequestNo() {
		return requestNo;
	}

	public void setRequestNo(String requestNo) {
		this.requestNo = requestNo;
	}

	@Id
    @GeneratedValue(generator = "native-generator")
    @Generator(name = "native-generator", strategy = "native", parameters = { @Parameter(name = "sequence", value = "SEQ_DOCUMENT") })
    public BigDecimal getDocumentSn()
    {
        return this.documentSn;
    }

    public void setDocumentSn(BigDecimal documentSn)
    {
        this.documentSn = documentSn;
    }

    @Column(name = "VISIT_SN", precision = 22, scale = 0)
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

    @Column(name = "DOCUMENT_TYPE", nullable = false, length = 6)
    public String getDocumentType()
    {
        return this.documentType;
    }

    public void setDocumentType(String documentType)
    {
        this.documentType = documentType;
    }

    @Column(name = "DOCUMENT_NAME", nullable = false, length = 300)
    public String getDocumentName()
    {
        return this.documentName;
    }

    public void setDocumentName(String documentName)
    {
        this.documentName = documentName;
    }

    @Column(name = "DOCUMENT_AUTHOR", length = 60)
    public String getDocumentAuthor()
    {
        return this.documentAuthor;
    }

    public void setDocumentAuthor(String documentAuthor)
    {
        this.documentAuthor = documentAuthor;
    }

    @Column(name = "DOCUMENT_AUTHOR_NAME", length = 56)
    public String getDocumentAuthorName()
    {
        return this.documentAuthorName;
    }

    public void setDocumentAuthorName(String documentAuthorName)
    {
        this.documentAuthorName = documentAuthorName;
    }

    @Temporal(TemporalType.DATE)
    @Column(name = "WRITE_TIME", length = 7)
    public Date getWriteTime()
    {
        return this.writeTime;
    }

    public void setWriteTime(Date writeTime)
    {
        this.writeTime = writeTime;
    }

    @Column(name = "DOCUMENT_MODIFIER", length = 12)
    public String getDocumentModifier()
    {
        return this.documentModifier;
    }

    public void setDocumentModifier(String documentModifier)
    {
        this.documentModifier = documentModifier;
    }

    @Column(name = "DOCUMENT_MODIFIER_NAME", length = 56)
    public String getDocumentModifierName()
    {
        return this.documentModifierName;
    }

    public void setDocumentModifierName(String documentModifierName)
    {
        this.documentModifierName = documentModifierName;
    }

    @Temporal(TemporalType.DATE)
    @Column(name = "MODIFY_TIME", length = 7)
    public Date getModifyTime()
    {
        return this.modifyTime;
    }

    public void setModifyTime(Date modifyTime)
    {
        this.modifyTime = modifyTime;
    }

    @Column(name = "REVIEW_PERSON", length = 56)
    public String getReviewPerson()
    {
        return this.reviewPerson;
    }

    public void setReviewPerson(String reviewPerson)
    {
        this.reviewPerson = reviewPerson;
    }

    @Column(name = "REVIEW_PERSON_NAME", length = 56)
    public String getReviewPersonName()
    {
        return this.reviewPersonName;
    }

    public void setReviewPersonName(String reviewPersonName)
    {
        this.reviewPersonName = reviewPersonName;
    }

    @Temporal(TemporalType.DATE)
    @Column(name = "REVIEW_TIME", length = 7)
    public Date getReviewTime()
    {
        return this.reviewTime;
    }

    public void setReviewTime(Date reviewTime)
    {
        this.reviewTime = reviewTime;
    }

    @Temporal(TemporalType.DATE)
    @Column(name = "SUBMIT_TIME", length = 7)
    public Date getSubmitTime()
    {
        return this.submitTime;
    }

    public void setSubmitTime(Date submitTime)
    {
        this.submitTime = submitTime;
    }

    @Column(name = "DIGITAL_SIGN", length = 1)
    public String getDigitalSign()
    {
        return this.digitalSign;
    }

    public void setDigitalSign(String digitalSign)
    {
        this.digitalSign = digitalSign;
    }

    @Column(name = "DOCUMENT_URL")
    public String getDocumentUrl()
    {
        return this.documentUrl;
    }

    public void setDocumentUrl(String documentUrl)
    {
        this.documentUrl = documentUrl;
    }

    @Temporal(TemporalType.DATE)
    @Column(name = "SIGN_TIME", length = 7)
    public Date getSignTime()
    {
        return this.signTime;
    }

    public void setSignTime(Date signTime)
    {
        this.signTime = signTime;
    }

    @Column(name = "DOCUMENT_CONTENT")
    public String getDocumentContent()
    {
        return this.documentContent;
    }

    public void setDocumentContent(String documentContent)
    {
        this.documentContent = documentContent;
    }

    @Column(name = "DOCUMENT_CDA")
    public String getDocumentCda()
    {
        return this.documentCda;
    }

    public void setDocumentCda(String documentCda)
    {
        this.documentCda = documentCda;
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

    @Column(name = "DOCUMENT_LID", nullable = false, length = 120)
    public String getDocumentLid()
    {
        return this.documentLid;
    }

    public void setDocumentLid(String documentLid)
    {
        this.documentLid = documentLid;
    }

    @Column(name = "SIGNATURE_ID", length = 40)
    public String getSignatureId()
    {
        return this.signatureId;
    }

    public void setSignatureId(String signatureId)
    {
        this.signatureId = signatureId;
    }

    @Column(name = "DOCUMENT_TYPE_NAME", length = 40)
    public String getDocumentTypeName()
    {
        return this.documentTypeName;
    }

    public void setDocumentTypeName(String documentTypeName)
    {
        this.documentTypeName = documentTypeName;
    }

    @Column(name = "SERVICE_ID", length = 32)
    public String getServiceId()
    {
        return serviceId;
    }

    public void setServiceId(String serviceId)
    {
        this.serviceId = serviceId;
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
    
    @Column(name = "ORG_CODE", length = 12)
    public String getOrgCode()
    {
        return this.orgCode;
    }

    public void setOrgCode(String orgCode)
    {
        this.orgCode = orgCode;
    }
    
    @Column(name = "ORG_NAME", length = 12)
    public String getOrgName()
    {
        return this.orgName;
    }

    public void setOrgName(String orgName)
    {
        this.orgName = orgName;
    }
    
    @Column(name = "IN_RECORD_TYPE", length = 24)
    public String getInRecordType()
    {
        return this.inRecordType;
    }

    public void setInRecordType(String inRecordType)
    {
        this.inRecordType = inRecordType;
    }
}
