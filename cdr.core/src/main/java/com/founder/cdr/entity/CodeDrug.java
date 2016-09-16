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
@Table(name = "CODE_DRUG")
public class CodeDrug implements Serializable
{

    private BigDecimal codeDrugId;

    private BigDecimal csId;

    private String code;

    private String serial;

    private String drugId;

    private String drugStandardId;

    private String name;

    private String dosage;

    private String concentration;

    private String weight;

    private String weightUnit;

    private String volum;

    private String volUnit;

    private String miniUnit;

    private BigDecimal packSize;

    private String packUnit;

    private String specification;

    private String speComment;

    private BigDecimal fixPrice;

    private BigDecimal retprice;

    private String manufactory;

    private String selfFlag;

    private String separateFlag;

    private String supriceFlag;

    private String drugFlag;

    private String pyCode;

    private String infusionFlag;

    private String countryFlag;

    private String divideFlag;

    private String drugKind;

    private String zyBillItem;

    private String mzBillItem;

    private String zyChargeGroup;

    private String mzChargeGroup;

    private String classCode;

    private String extendCode;

    private String supplyCode;

    private String frequCode;

    private String orderDosage;

    private String dosageUnit;

    private BigDecimal buyPrice;

    private String lowdosageFlag;

    private String auditCode;

    private String skinTestFlag;

    private String printName;

    private String licenseNo;

    private String effMonth;

    private String tradMark;

    private String fuFlag;

    private String zySupplyCode;

    private String zyFrequCode;

    private String seqNo;

    private String versionNo;

    private Date createTime;

    private Date updateTime;

    private BigDecimal updateCount;

    private String deleteFlag;

    private Date deleteTime;

    @Id
    @GeneratedValue(generator = "native-generator")
    @Generator(name = "native-generator", strategy = "native", parameters = { @Parameter(name = "sequence", value = "SEQ_CODE_DRUG") })
    public BigDecimal getCodeDrugId()
    {
        return this.codeDrugId;
    }

    public void setCodeDrugId(BigDecimal codeDrugId)
    {
        this.codeDrugId = codeDrugId;
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

    @Column(name = "SERIAL", length = 2)
    public String getSerial()
    {
        return this.serial;
    }

    public void setSerial(String serial)
    {
        this.serial = serial;
    }

    @Column(name = "DRUG_ID", length = 38)
    public String getDrugId()
    {
        return this.drugId;
    }

    public void setDrugId(String drugId)
    {
        this.drugId = drugId;
    }

    @Column(name = "DRUG_STANDARD_ID", length = 38)
    public String getDrugStandardId()
    {
        return this.drugStandardId;
    }

    public void setDrugStandardId(String drugStandardId)
    {
        this.drugStandardId = drugStandardId;
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

    @Column(name = "DOSAGE", length = 38)
    public String getDosage()
    {
        return this.dosage;
    }

    public void setDosage(String dosage)
    {
        this.dosage = dosage;
    }

    @Column(name = "CONCENTRATION", length = 38)
    public String getConcentration()
    {
        return this.concentration;
    }

    public void setConcentration(String concentration)
    {
        this.concentration = concentration;
    }

    @Column(name = "WEIGHT", length = 38)
    public String getWeight()
    {
        return this.weight;
    }

    public void setWeight(String weight)
    {
        this.weight = weight;
    }

    @Column(name = "WEIGHT_UNIT", length = 38)
    public String getWeightUnit()
    {
        return this.weightUnit;
    }

    public void setWeightUnit(String weightUnit)
    {
        this.weightUnit = weightUnit;
    }

    @Column(name = "VOLUM", length = 38)
    public String getVolum()
    {
        return this.volum;
    }

    public void setVolum(String volum)
    {
        this.volum = volum;
    }

    @Column(name = "VOL_UNIT", length = 38)
    public String getVolUnit()
    {
        return this.volUnit;
    }

    public void setVolUnit(String volUnit)
    {
        this.volUnit = volUnit;
    }

    @Column(name = "MINI_UNIT", length = 38)
    public String getMiniUnit()
    {
        return this.miniUnit;
    }

    public void setMiniUnit(String miniUnit)
    {
        this.miniUnit = miniUnit;
    }

    @Column(name = "PACK_SIZE", precision = 36, scale = 0)
    public BigDecimal getPackSize()
    {
        return this.packSize;
    }

    public void setPackSize(BigDecimal packSize)
    {
        this.packSize = packSize;
    }

    @Column(name = "PACK_UNIT", length = 38)
    public String getPackUnit()
    {
        return this.packUnit;
    }

    public void setPackUnit(String packUnit)
    {
        this.packUnit = packUnit;
    }

    @Column(name = "SPECIFICATION", length = 50)
    public String getSpecification()
    {
        return this.specification;
    }

    public void setSpecification(String specification)
    {
        this.specification = specification;
    }

    @Column(name = "SPE_COMMENT", length = 38)
    public String getSpeComment()
    {
        return this.speComment;
    }

    public void setSpeComment(String speComment)
    {
        this.speComment = speComment;
    }

    @Column(name = "FIX_PRICE", precision = 36, scale = 0)
    public BigDecimal getFixPrice()
    {
        return this.fixPrice;
    }

    public void setFixPrice(BigDecimal fixPrice)
    {
        this.fixPrice = fixPrice;
    }

    @Column(name = "RETPRICE", precision = 36, scale = 0)
    public BigDecimal getRetprice()
    {
        return this.retprice;
    }

    public void setRetprice(BigDecimal retprice)
    {
        this.retprice = retprice;
    }

    @Column(name = "MANUFACTORY", length = 38)
    public String getManufactory()
    {
        return this.manufactory;
    }

    public void setManufactory(String manufactory)
    {
        this.manufactory = manufactory;
    }

    @Column(name = "SELF_FLAG", length = 38)
    public String getSelfFlag()
    {
        return this.selfFlag;
    }

    public void setSelfFlag(String selfFlag)
    {
        this.selfFlag = selfFlag;
    }

    @Column(name = "SEPARATE_FLAG", length = 38)
    public String getSeparateFlag()
    {
        return this.separateFlag;
    }

    public void setSeparateFlag(String separateFlag)
    {
        this.separateFlag = separateFlag;
    }

    @Column(name = "SUPRICE_FLAG", length = 38)
    public String getSupriceFlag()
    {
        return this.supriceFlag;
    }

    public void setSupriceFlag(String supriceFlag)
    {
        this.supriceFlag = supriceFlag;
    }

    @Column(name = "DRUG_FLAG", length = 38)
    public String getDrugFlag()
    {
        return this.drugFlag;
    }

    public void setDrugFlag(String drugFlag)
    {
        this.drugFlag = drugFlag;
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

    @Column(name = "INFUSION_FLAG", length = 38)
    public String getInfusionFlag()
    {
        return this.infusionFlag;
    }

    public void setInfusionFlag(String infusionFlag)
    {
        this.infusionFlag = infusionFlag;
    }

    @Column(name = "COUNTRY_FLAG", length = 38)
    public String getCountryFlag()
    {
        return this.countryFlag;
    }

    public void setCountryFlag(String countryFlag)
    {
        this.countryFlag = countryFlag;
    }

    @Column(name = "DIVIDE_FLAG", length = 38)
    public String getDivideFlag()
    {
        return this.divideFlag;
    }

    public void setDivideFlag(String divideFlag)
    {
        this.divideFlag = divideFlag;
    }

    @Column(name = "DRUG_KIND", length = 38)
    public String getDrugKind()
    {
        return this.drugKind;
    }

    public void setDrugKind(String drugKind)
    {
        this.drugKind = drugKind;
    }

    @Column(name = "ZY_BILL_ITEM", length = 38)
    public String getZyBillItem()
    {
        return this.zyBillItem;
    }

    public void setZyBillItem(String zyBillItem)
    {
        this.zyBillItem = zyBillItem;
    }

    @Column(name = "MZ_BILL_ITEM", length = 38)
    public String getMzBillItem()
    {
        return this.mzBillItem;
    }

    public void setMzBillItem(String mzBillItem)
    {
        this.mzBillItem = mzBillItem;
    }

    @Column(name = "ZY_CHARGE_GROUP", length = 38)
    public String getZyChargeGroup()
    {
        return this.zyChargeGroup;
    }

    public void setZyChargeGroup(String zyChargeGroup)
    {
        this.zyChargeGroup = zyChargeGroup;
    }

    @Column(name = "MZ_CHARGE_GROUP", length = 38)
    public String getMzChargeGroup()
    {
        return this.mzChargeGroup;
    }

    public void setMzChargeGroup(String mzChargeGroup)
    {
        this.mzChargeGroup = mzChargeGroup;
    }

    @Column(name = "CLASS_CODE", length = 38)
    public String getClassCode()
    {
        return this.classCode;
    }

    public void setClassCode(String classCode)
    {
        this.classCode = classCode;
    }

    @Column(name = "EXTEND_CODE", length = 38)
    public String getExtendCode()
    {
        return this.extendCode;
    }

    public void setExtendCode(String extendCode)
    {
        this.extendCode = extendCode;
    }

    @Column(name = "SUPPLY_CODE", length = 38)
    public String getSupplyCode()
    {
        return this.supplyCode;
    }

    public void setSupplyCode(String supplyCode)
    {
        this.supplyCode = supplyCode;
    }

    @Column(name = "FREQU_CODE", length = 38)
    public String getFrequCode()
    {
        return this.frequCode;
    }

    public void setFrequCode(String frequCode)
    {
        this.frequCode = frequCode;
    }

    @Column(name = "ORDER_DOSAGE", length = 38)
    public String getOrderDosage()
    {
        return this.orderDosage;
    }

    public void setOrderDosage(String orderDosage)
    {
        this.orderDosage = orderDosage;
    }

    @Column(name = "DOSAGE_UNIT", length = 38)
    public String getDosageUnit()
    {
        return this.dosageUnit;
    }

    public void setDosageUnit(String dosageUnit)
    {
        this.dosageUnit = dosageUnit;
    }

    @Column(name = "BUY_PRICE", precision = 36, scale = 0)
    public BigDecimal getBuyPrice()
    {
        return this.buyPrice;
    }

    public void setBuyPrice(BigDecimal buyPrice)
    {
        this.buyPrice = buyPrice;
    }

    @Column(name = "LOWDOSAGE_FLAG", length = 38)
    public String getLowdosageFlag()
    {
        return this.lowdosageFlag;
    }

    public void setLowdosageFlag(String lowdosageFlag)
    {
        this.lowdosageFlag = lowdosageFlag;
    }

    @Column(name = "AUDIT_CODE", length = 38)
    public String getAuditCode()
    {
        return this.auditCode;
    }

    public void setAuditCode(String auditCode)
    {
        this.auditCode = auditCode;
    }

    @Column(name = "SKIN_TEST_FLAG", length = 38)
    public String getSkinTestFlag()
    {
        return this.skinTestFlag;
    }

    public void setSkinTestFlag(String skinTestFlag)
    {
        this.skinTestFlag = skinTestFlag;
    }

    @Column(name = "PRINT_NAME", length = 60)
    public String getPrintName()
    {
        return this.printName;
    }

    public void setPrintName(String printName)
    {
        this.printName = printName;
    }

    @Column(name = "LICENSE_NO", length = 40)
    public String getLicenseNo()
    {
        return this.licenseNo;
    }

    public void setLicenseNo(String licenseNo)
    {
        this.licenseNo = licenseNo;
    }

    @Column(name = "EFF_MONTH", length = 38)
    public String getEffMonth()
    {
        return this.effMonth;
    }

    public void setEffMonth(String effMonth)
    {
        this.effMonth = effMonth;
    }

    @Column(name = "TRAD_MARK", length = 38)
    public String getTradMark()
    {
        return this.tradMark;
    }

    public void setTradMark(String tradMark)
    {
        this.tradMark = tradMark;
    }

    @Column(name = "FU_FLAG", length = 38)
    public String getFuFlag()
    {
        return this.fuFlag;
    }

    public void setFuFlag(String fuFlag)
    {
        this.fuFlag = fuFlag;
    }

    @Column(name = "ZY_SUPPLY_CODE", length = 38)
    public String getZySupplyCode()
    {
        return this.zySupplyCode;
    }

    public void setZySupplyCode(String zySupplyCode)
    {
        this.zySupplyCode = zySupplyCode;
    }

    @Column(name = "ZY_FREQU_CODE", length = 38)
    public String getZyFrequCode()
    {
        return this.zyFrequCode;
    }

    public void setZyFrequCode(String zyFrequCode)
    {
        this.zyFrequCode = zyFrequCode;
    }

    @Column(name = "SEQ_NO", length = 32)
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
