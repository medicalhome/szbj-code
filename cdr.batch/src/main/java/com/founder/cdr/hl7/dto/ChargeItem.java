package com.founder.cdr.hl7.dto;

import org.hibernate.validator.constraints.NotEmpty;


public class ChargeItem extends CodeValueDto
{
    /**
     * 版本号
     */
    private String versionNo;
    /**
     * 操作类型
     */
    @NotEmpty(message = "{ChargeItem.newUpFlag}")
    private String newUpFlag;
    /**
     * 项目编码
     */
    @NotEmpty(message = "{ChargeItem.itemCode}")
    private String itemCode;
    /**
     * 项目名称
     */
    @NotEmpty(message = "{ChargeItem.itemName}")
    private String itemName;
    /**
     * 打印名称
     */
    private String printName;
    /**
     * 所属科室
     */
    private String specialFlag2;
    /**
     * 执行科室
     */
    private String execUnit;
    /**
     * 拼音码
     */
    private String pyCode;
    /**
     * 借方科目
     */
    private String debitCode;
    /**
     * 贷方科目
     */
    private String creditCode;
    /**
     * 项目分类码
     */
    private String auditCode;
    /**
     * 财务核算码
     */
    private String auditCode2;
    /**
     * 实收金额
     */
    private String chargePrice;
    /**
     * 收费单位
     */
    private String chargeUnit;
    /**
     * 生效金额
     */
    private String effectivePrice;
    /**
     * 生效日期
     */
    private String effectiveDate;
    /**
     * 住院帐单码
     */
    private String zyBillItem;
    /**
     * 门诊账单码
     */
    private String mzBillItem;
    /**
     * 门诊住院标志
     */
    private String zyMzFlag;
    /**
     * 比例1
     */
    private String percentag1;
    /**
     * 比例2
     */
    private String percentag2;
    /**
     * 比例3
     */
    private String percentag3;
    /**
     * 比例4
     */
    private String percentag4;
    /**
     * 比例5
     */
    private String percentag5;
    /**
     * 金额1
     */
    private String amount1;
    /**
     * 金额2
     */
    private String amount2;
    /**
     * 金额3
     */
    private String amount3;
    /**
     * 金额4
     */
    private String amount4;
    /**
     * 金额5
     */
    private String amount5;
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
     * 住院项目类别
     */
    private String zyChargeGroup;
    /**
     * 门诊项目类别
     */
    private String mzChargeGroup;
    /**
     * 项目分类
     */
    private String chargeClass;
    /**
     * 住院确认标志
     */
    private String zyConfirmFlag;
    /**
     * 门诊确认标志
     */
    private String mzConfirmFlag;
    /**
     * 备注
     */
    private String comments;
    /**
     * 扩展码
     */
    private String extendCode;
    /**
     * 扩展标志
     */
    private String extendFlag;
    /**
     * 内部码
     */
    private String internalCode;
    /**
     * 生效日期
     */
    private String ineffectiveDate;
    /**
     * 最小价格
     */
    private String minPrice;
    /**
     * 最大价格
     */
    private String maxPrice;
    /**
     * 大红本项目名称W码
     */
    private String xmName;
    /**
     * 大红本注示
     */
    private String comment2;
    /**
     * 厂商
     */
    private String manuName;
    /**
     * 医保适应症
     */
    private String indications;
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

    public String getItemCode()
    {
        return itemCode;
    }

    public String getItemName()
    {
        return itemName;
    }

    public String getPrintName()
    {
        return printName;
    }

    public String getSpecialFlag2()
    {
        return specialFlag2;
    }

    public String getExecUnit()
    {
        return execUnit;
    }

    public String getPyCode()
    {
        return pyCode;
    }

    public String getDebitCode()
    {
        return debitCode;
    }

    public String getCreditCode()
    {
        return creditCode;
    }

    public String getAuditCode()
    {
        return auditCode;
    }

    public String getAuditCode2()
    {
        return auditCode2;
    }

    public String getChargePrice()
    {
        return chargePrice;
    }

    public String getChargeUnit()
    {
        return chargeUnit;
    }

    public String getEffectivePrice()
    {
        return effectivePrice;
    }

    public String getEffectiveDate()
    {
        return effectiveDate;
    }

    public String getZyBillItem()
    {
        return zyBillItem;
    }

    public String getMzBillItem()
    {
        return mzBillItem;
    }

    public String getZyMzFlag()
    {
        return zyMzFlag;
    }

    public String getPercentag1()
    {
        return percentag1;
    }

    public String getPercentag2()
    {
        return percentag2;
    }

    public String getPercentag3()
    {
        return percentag3;
    }

    public String getPercentag4()
    {
        return percentag4;
    }

    public String getPercentag5()
    {
        return percentag5;
    }

    public String getAmount1()
    {
        return amount1;
    }

    public String getAmount2()
    {
        return amount2;
    }

    public String getAmount3()
    {
        return amount3;
    }

    public String getAmount4()
    {
        return amount4;
    }

    public String getAmount5()
    {
        return amount5;
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

    public String getZyChargeGroup()
    {
        return zyChargeGroup;
    }

    public String getMzChargeGroup()
    {
        return mzChargeGroup;
    }

    public String getChargeClass()
    {
        return chargeClass;
    }

    public String getZyConfirmFlag()
    {
        return zyConfirmFlag;
    }

    public String getMzConfirmFlag()
    {
        return mzConfirmFlag;
    }

    public String getComments()
    {
        return comments;
    }

    public String getExtendCode()
    {
        return extendCode;
    }

    public String getExtendFlag()
    {
        return extendFlag;
    }

    public String getInternalCode()
    {
        return internalCode;
    }

    public String getIneffectiveDate()
    {
        return ineffectiveDate;
    }

    public String getMinPrice()
    {
        return minPrice;
    }

    public String getMaxPrice()
    {
        return maxPrice;
    }

    public String getXmName()
    {
        return xmName;
    }

    public String getComment2()
    {
        return comment2;
    }

    public String getManuName()
    {
        return manuName;
    }

    public String getIndications()
    {
        return indications;
    }

    public String getSeqNo()
    {
        return seqNo;
    }
    
    public String getUniqueId()
    {
        return itemCode;
    }

    public String getOptType()
    {
        return newUpFlag;
    }
    
    //TODO 当一个主键不能确定一条记录时，可使用主键+外键锁定，此外键备用
    public String getForeignKey()
    {
        return "";
    }
}
