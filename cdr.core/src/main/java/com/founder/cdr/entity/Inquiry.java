package com.founder.cdr.entity;

import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

import com.founder.fasf.core.util.daohelper.annotation.Generator;

import java.io.Serializable;

@Entity
@Table(name = "INQUIRY")
public class Inquiry implements Serializable
{

    private BigDecimal inquirySn;

    private BigDecimal documentSn;

    private String inquiryCategory;

    private String inquiryItem;

    private String inquiryResult;

    private String inquiryCategoryName;

    private String inquiryItemName;

    @Id
    @GeneratedValue(generator = "guid-generator")
    @Generator(name = "guid-generator", strategy = "guid")
    public BigDecimal getInquirySn()
    {
        return this.inquirySn;
    }

    public void setInquirySn(BigDecimal inquirySn)
    {
        this.inquirySn = inquirySn;
    }

    @Column(name = "DOCUMENT_SN", precision = 22, scale = 0)
    public BigDecimal getDocumentSn()
    {
        return this.documentSn;
    }

    public void setDocumentSn(BigDecimal documentSn)
    {
        this.documentSn = documentSn;
    }

    @Column(name = "INQUIRY_CATEGORY", length = 20)
    public String getInquiryCategory()
    {
        return this.inquiryCategory;
    }

    public void setInquiryCategory(String inquiryCategory)
    {
        this.inquiryCategory = inquiryCategory;
    }

    @Column(name = "INQUIRY_ITEM", length = 20)
    public String getInquiryItem()
    {
        return this.inquiryItem;
    }

    public void setInquiryItem(String inquiryItem)
    {
        this.inquiryItem = inquiryItem;
    }

    @Column(name = "INQUIRY_RESULT")
    public String getInquiryResult()
    {
        return this.inquiryResult;
    }

    public void setInquiryResult(String inquiryResult)
    {
        this.inquiryResult = inquiryResult;
    }

    @Column(name = "INQUIRY_CATEGORY_NAME", length = 40)
    public String getInquiryCategoryName()
    {
        return this.inquiryCategoryName;
    }

    public void setInquiryCategoryName(String inquiryCategoryName)
    {
        this.inquiryCategoryName = inquiryCategoryName;
    }

    @Column(name = "INQUIRY_ITEM_NAME", length = 40)
    public String getInquiryItemName()
    {
        return this.inquiryItemName;
    }

    public void setInquiryItemName(String inquiryItemName)
    {
        this.inquiryItemName = inquiryItemName;
    }

}
