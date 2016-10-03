package com.yly.cdr.hl7.dto;

import org.hibernate.validator.constraints.NotEmpty;

import com.yly.cdr.util.StringUtils;

/**
 * 
 * @author tong_meng
 *
 */
public class Drug extends CodeValueDto
{
    /**
     * 版本号
     */
    private String versionNo;

    /**
     * 操作类型
     */
    @NotEmpty(message = "{Drug.newUpFlag}")
    private String newUpFlag;

    /**
     * 药品编码
     */
    @NotEmpty(message = "{Drug.code}")
    private String code;

    /**
     * 药品名称
     */
    @NotEmpty(message = "{Drug.name}")
    private String name;

    /**
     * 包装序号
     */
    private String serial;

    /**
     * 药品分类码
     */
    private String drugId;

    /**
     * 国标分类码
     */
    private String drugStandardId;

    /**
     * 剂型
     */
    private String dosage;

    /**
     * 浓度
     */
    private String concentration;

    /**
     * 重量
     */
    private String weight;

    /**
     * 重量单位
     */
    private String weightUnit;

    /**
     * 体积
     */
    private String volum;

    /**
     * 体积单位
     */
    private String volUnit;

    /**
     * 最小单位
     */
    private String miniUnit;

    /**
     * 包装量
     */
    private String packSize;

    /**
     * 包装单位
     */
    private String packUnit;

    /**
     * 规格
     */
    private String specification;

    /**
     * 规格备注
     */
    private String speComment;

    /**
     * 批发价
     */
    private String fixPrice;

    /**
     * 零售价
     */
    private String retprice;

    /**
     * 制药厂
     */
    private String manufactory;

    /**
     * 自费标志
     */
    private String selfFlag;

    /**
     * 单列标志
     */
    private String separateFlag;

    /**
     * 贵重标志
     */
    private String supriceFlag;

    /**
     * 毒麻标志
     */
    private String drugFlag;

    /**
     * 拼音码
     */
    private String pyCode;

    /**
     * 大输液标志
     */
    private String infusionFlag;

    /**
     * 进口
     */
    private String countryFlag;

    /**
     * 分装标志
     */
    private String divideFlag;

    /**
     * 药品类别
     */
    private String drugKind;

    /**
     * 住院账单码
     */
    private String zyBillItem;

    /**
     * 门诊账单码
     */
    private String mzBillItem;

    /**
     * 住院用药品分组
     */
    private String zyChargeGroup;

    /**
     * 门诊用药品分组
     */
    private String mzChargeGroup;

    /**
     * 药品类别码
     */
    private String classCode;

    /**
     * 药品与外界衔接码
     */
    private String extendCode;

    /**
     * 常用服法
     */
    private String supplyCode;

    /**
     * 常用频率
     */
    private String frequCode;

    /**
     * 常用剂量
     */
    private String orderDosage;

    /**
     * 常用计量单位
     */
    private String dosageUnit;

    /**
     * 购入价
     */
    private String buyPrice;

    /**
     * 大规格小剂量标志
     */
    private String lowdosageFlag;

    /**
     * 核算码
     */
    private String auditCode;

    /**
     * 皮试标志
     */
    private String skinTestFlag;

    /**
     * 打印名称
     */
    private String printName;

    /**
     * 文号
     */
    private String licenseNo;

    /**
     * 有效月份
     */
    private String effMonth;

    /**
     * 商标
     */
    private String tradMark;

    /**
     * ‘副’影响标志
     */
    private String fuFlag;

    /**
     * 住院使用给药方式
     */
    private String zySupplyCode;

    /**
     * 住院使用频率
     */
    private String zyFrequCode;

    /**
     * 排序码 
     */
    private String seqNo;

    public String getVersionNo()
    {
        return versionNo;
    }

    public String getNewUpFlag()
    {
        return newUpFlag;
    }

    public String getCode()
    {
        return code;
    }

    public String getName()
    {
        return name;
    }

    public String getSerial()
    {
        return serial;
    }

    public String getDrugId()
    {
        return drugId;
    }

    public String getDrugStandardId()
    {
        return drugStandardId;
    }

    public String getDosage()
    {
        return dosage;
    }

    public String getConcentration()
    {
        return concentration;
    }

    public String getWeight()
    {
        return weight;
    }

    public String getWeightUnit()
    {
        return weightUnit;
    }

    public String getVolum()
    {
        return volum;
    }

    public String getVolUnit()
    {
        return volUnit;
    }

    public String getMiniUnit()
    {
        return miniUnit;
    }

    public String getPackSize()
    {
        return packSize;
    }

    public String getPackUnit()
    {
        return packUnit;
    }

    public String getSpecification()
    {
        return specification;
    }

    public String getSpeComment()
    {
        return speComment;
    }

    public String getFixPrice()
    {
        return fixPrice;
    }

    public String getRetprice()
    {
        return retprice;
    }

    public String getManufactory()
    {
        return manufactory;
    }

    public String getSelfFlag()
    {
        return selfFlag;
    }

    public String getSeparateFlag()
    {
        return separateFlag;
    }

    public String getSupriceFlag()
    {
        return supriceFlag;
    }

    public String getDrugFlag()
    {
        return drugFlag;
    }

    public String getPyCode()
    {
        return pyCode;
    }

    public String getInfusionFlag()
    {
        return infusionFlag;
    }

    public String getCountryFlag()
    {
        return countryFlag;
    }

    public String getDivideFlag()
    {
        return divideFlag;
    }

    public String getDrugKind()
    {
        return drugKind;
    }

    public String getZyBillItem()
    {
        return zyBillItem;
    }

    public String getMzBillItem()
    {
        return mzBillItem;
    }

    public String getZyChargeGroup()
    {
        return zyChargeGroup;
    }

    public String getMzChargeGroup()
    {
        return mzChargeGroup;
    }

    public String getClassCode()
    {
        return classCode;
    }

    public String getExtendCode()
    {
        return extendCode;
    }

    public String getSupplyCode()
    {
        return supplyCode;
    }

    public String getFrequCode()
    {
        return frequCode;
    }

    public String getOrderDosage()
    {
        return orderDosage;
    }

    public String getDosageUnit()
    {
        return dosageUnit;
    }

    public String getBuyPrice()
    {
        return buyPrice;
    }

    public String getLowdosageFlag()
    {
        return lowdosageFlag;
    }

    public String getAuditCode()
    {
        return auditCode;
    }

    public String getSkinTestFlag()
    {
        return skinTestFlag;
    }

    public String getPrintName()
    {
        return printName;
    }

    public String getLicenseNo()
    {
        return licenseNo;
    }

    public String getEffMonth()
    {
        return effMonth;
    }

    public String getTradMark()
    {
        return tradMark;
    }

    public String getFuFlag()
    {
        return fuFlag;
    }

    public String getZySupplyCode()
    {
        return zySupplyCode;
    }

    public String getZyFrequCode()
    {
        return zyFrequCode;
    }

    public String getSeqNo()
    {
        return seqNo;
    }

    public String getUniqueId()
    {
        return code;
    }

    public String getOptType()
    {
        return newUpFlag;
    }
    
    // $Author :tong_meng
    // $Date : 2012/11/05 11:00$
    // [BUG]0011055 MODIFY BEGIN
    public String getForeignKey()
    {
        return StringUtils.isEmpty(serial) ? "00" : serial;
    }
    // [BUG]001105 MODIFY END
}
