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

import java.io.Serializable;

@Entity
@Table(name="SETTLEMENT")
public class Settlement implements Serializable
{

    private BigDecimal settlementSn;
    private BigDecimal visitSn;
    private Date settlementTime;
    private String settlementPersonId;
    private String settlementPersonName;
    private BigDecimal patientSn;
    private BigDecimal ledgerSn;
    private BigDecimal zyDetailSn;
    private BigDecimal receiptSn;
    private String receiptNo;
    private BigDecimal chargeTotal;

    @Id
    @GeneratedValue(generator = "guid-generator")
    @Generator(name = "guid-generator", strategy = "guid")
    public BigDecimal getSettlementSn() 
    {
        return this.settlementSn;
    }
    
    public void setSettlementSn(BigDecimal settlementSn) 
    {
        this.settlementSn = settlementSn;
    }

        
    @Column(name="VISIT_SN", precision=22, scale=0)
    public BigDecimal getVisitSn() 
    {
        return this.visitSn;
    }
    
    public void setVisitSn(BigDecimal visitSn) 
    {
        this.visitSn = visitSn;
    }

        @Temporal(TemporalType.DATE)
    @Column(name="SETTLEMENT_TIME", length=7)
    public Date getSettlementTime() 
    {
        return this.settlementTime;
    }
    
    public void setSettlementTime(Date settlementTime) 
    {
        this.settlementTime = settlementTime;
    }

        
    @Column(name="SETTLEMENT_PERSON_ID", length=12)
    public String getSettlementPersonId() 
    {
        return this.settlementPersonId;
    }
    
    public void setSettlementPersonId(String settlementPersonId) 
    {
        this.settlementPersonId = settlementPersonId;
    }

        
    @Column(name="SETTLEMENT_PERSON_NAME", length=50)
    public String getSettlementPersonName() 
    {
        return this.settlementPersonName;
    }
    
    public void setSettlementPersonName(String settlementPersonName) 
    {
        this.settlementPersonName = settlementPersonName;
    }

        
    @Column(name="PATIENT_SN", precision=22, scale=0)
    public BigDecimal getPatientSn() 
    {
        return this.patientSn;
    }
    
    public void setPatientSn(BigDecimal patientSn) 
    {
        this.patientSn = patientSn;
    }

        
    @Column(name="LEDGER_SN", precision=22, scale=0)
    public BigDecimal getLedgerSn() 
    {
        return this.ledgerSn;
    }
    
    public void setLedgerSn(BigDecimal ledgerSn) 
    {
        this.ledgerSn = ledgerSn;
    }

        
    @Column(name="ZY_DETAIL_SN", precision=22, scale=0)
    public BigDecimal getZyDetailSn() 
    {
        return this.zyDetailSn;
    }
    
    public void setZyDetailSn(BigDecimal zyDetailSn) 
    {
        this.zyDetailSn = zyDetailSn;
    }

        
    @Column(name="RECEIPT_SN", precision=22, scale=0)
    public BigDecimal getReceiptSn() 
    {
        return this.receiptSn;
    }
    
    public void setReceiptSn(BigDecimal receiptSn) 
    {
        this.receiptSn = receiptSn;
    }

        
    @Column(name="RECEIPT_NO", length=32)
    public String getReceiptNo() 
    {
        return this.receiptNo;
    }
    
    public void setReceiptNo(String receiptNo) 
    {
        this.receiptNo = receiptNo;
    }

        
    @Column(name="CHARGE_TOTAL", precision=14, scale=4)
    public BigDecimal getChargeTotal() 
    {
        return this.chargeTotal;
    }
    
    public void setChargeTotal(BigDecimal chargeTotal) 
    {
        this.chargeTotal = chargeTotal;
    }

}
