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
@Table(name = "INPATIENT_CHARGE")
public class InpatientCharge implements Serializable
{

    private BigDecimal chargeSn;

    private BigDecimal documentSn;

    @Id
    @GeneratedValue(generator = "guid-generator")
    @Generator(name = "guid-generator", strategy = "guid")
    public BigDecimal getChargeSn()
    {
        return this.chargeSn;
    }

    public void setChargeSn(BigDecimal chargeSn)
    {
        this.chargeSn = chargeSn;
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

}
