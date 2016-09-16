package com.founder.cdr.batch.writer;

import java.math.BigDecimal;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.founder.cdr.core.Constants;
import com.founder.cdr.entity.CodeDrug;
import com.founder.cdr.hl7.dto.CodeValueDto;
import com.founder.cdr.hl7.dto.Drug;
import com.founder.cdr.util.StringUtils;

/**
 * 药品字典
 * @author tong_meng
 *
 */
@Component(value = "ms028Writer")
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class MS028Writer extends MSBaseWriter<Drug>
{
	/**
	 * 包装号默认值
	 */
	private static final String SERIAL_DEFAULT = "00";
	
    @Override
    public Object getNewCodeValue(CodeValueDto codeValue)
    {
        Drug drug = (Drug) codeValue;
        CodeDrug cd = null;
        cd = new CodeDrug();
        cd.setCsId(codeSystem.getCsId());
        cd.setCode(drug.getCode());

        // $Author :tong_meng
        // $Date : 2012/10/29 15:00$
        // [BUG]0010821 MODIFY BEGIN
        cd.setSerial(StringUtils.isEmpty(drug.getSerial()) ? SERIAL_DEFAULT:drug.getSerial());
        // [BUG]0010821 MODIFY END

        cd.setDrugId(StringUtils.isEmpty(drug.getDrugId()) ? drug.getCode():drug.getDrugId());

        // $Author :tong_meng
        // $Date : 2012/9/5 10:00$
        // [BUG]0009337 MODIFY BEGIN
        cd.setDrugStandardId(drug.getDrugStandardId());
        // [BUG]0009337 MODIFY END

        cd.setName(drug.getName());
        cd.setDosage(drug.getDosage());
        cd.setConcentration(drug.getConcentration());
        cd.setWeight(drug.getWeight());
        cd.setWeightUnit(drug.getWeightUnit());
        cd.setVolum(drug.getVolum());
        cd.setVolUnit(drug.getVolUnit());
        cd.setMiniUnit(drug.getMiniUnit());
        cd.setPackSize(StringUtils.strToBigDecimal(drug.getPackSize(), "pe"));
        cd.setPackUnit(drug.getPackUnit());
        cd.setSpecification(drug.getSpecification());
        cd.setSpeComment(drug.getSpeComment());
        cd.setFixPrice(StringUtils.strToBigDecimal(drug.getFixPrice(), "pe"));
        cd.setRetprice(StringUtils.strToBigDecimal(drug.getRetprice(), "pe"));
        cd.setManufactory(drug.getManufactory());
        cd.setSelfFlag(drug.getSelfFlag());
        cd.setSeparateFlag(drug.getSeparateFlag());
        cd.setSupriceFlag(drug.getSupriceFlag());
        cd.setDrugFlag(drug.getDrugFlag());
        cd.setPyCode(drug.getPyCode());
        cd.setInfusionFlag(drug.getInfusionFlag());
        cd.setCountryFlag(drug.getCountryFlag());
        cd.setDivideFlag(drug.getDivideFlag());
        cd.setDrugKind(drug.getDrugKind());
        cd.setZyBillItem(drug.getZyBillItem());
        cd.setMzBillItem(drug.getMzBillItem());
        cd.setZyChargeGroup(drug.getZyChargeGroup());
        cd.setMzChargeGroup(drug.getMzChargeGroup());
        cd.setClassCode(drug.getClassCode());
        cd.setExtendCode(drug.getExtendCode());
        cd.setSupplyCode(drug.getSupplyCode());
        cd.setFrequCode(drug.getFrequCode());
        cd.setOrderDosage(drug.getOrderDosage());
        cd.setDosageUnit(drug.getDosageUnit());
        cd.setBuyPrice(StringUtils.strToBigDecimal(drug.getBuyPrice(), "pe"));
        cd.setLowdosageFlag(drug.getLowdosageFlag());
        cd.setAuditCode(drug.getAuditCode());
        cd.setSkinTestFlag(drug.getSkinTestFlag());
        cd.setPrintName(drug.getPrintName());
        cd.setLicenseNo(drug.getLicenseNo());
        cd.setEffMonth(drug.getEffMonth());
        cd.setTradMark(drug.getTradMark());
        cd.setFuFlag(drug.getFuFlag());
        cd.setZySupplyCode(drug.getZySupplyCode());
        cd.setZyFrequCode(drug.getZyFrequCode());
        cd.setSeqNo(drug.getSeqNo());
        cd.setVersionNo(drug.getVersionNo());
        cd.setCreateTime(systemDate);
        cd.setUpdateCount(Constants.INSERT_UPDATE_COUNT);
        cd.setUpdateTime(systemDate);
        if (isDeleteMessage(drug.getNewUpFlag()))
        {
            cd.setDeleteFlag(Constants.DELETE_FLAG);
            cd.setDeleteTime(systemDate);
        }
        else if (isNewMessage(drug.getNewUpFlag())
            || isUpdateMessage(drug.getNewUpFlag()))
        {
            cd.setDeleteFlag(Constants.NO_DELETE_FLAG);
            cd.setDeleteTime(null);
        }
        return cd;
    }

    @Override
    public Object getUpdateCodeValue(CodeValueDto codeValue)
    {
        Drug drug = (Drug) codeValue;
        // $Author :tong_meng
        // $Date : 2012/10/25 15:00$
        // [BUG]0010742 MODIFY BEGIN
        CodeDrug cd = getCDList(drug.getCode(), codeSystem.getCsId(),
        		StringUtils.isEmpty(drug.getSerial()) ? SERIAL_DEFAULT:drug.getSerial());
        // [BUG]0010742 MODIFY END

        // $Author :tong_meng
        // $Date : 2012/10/29 15:00$
        // [BUG]0010821 MODIFY BEGIN
        cd.setSerial(StringUtils.isEmpty(drug.getSerial()) ? SERIAL_DEFAULT:drug.getSerial());
        // [BUG]0010821 MODIFY END

        cd.setDrugId(StringUtils.isEmpty(drug.getDrugId()) ? drug.getCode():drug.getDrugId());

        // $Author :tong_meng
        // $Date : 2012/9/5 10:00$
        // [BUG]0009337 MODIFY BEGIN
        cd.setDrugStandardId(drug.getDrugStandardId());
        // [BUG]0009337 MODIFY END

        cd.setName(drug.getName());
        cd.setDosage(drug.getDosage());
        cd.setConcentration(drug.getConcentration());
        cd.setWeight(drug.getWeight());
        cd.setWeightUnit(drug.getWeightUnit());
        cd.setVolum(drug.getVolum());
        cd.setVolUnit(drug.getVolUnit());
        cd.setMiniUnit(drug.getMiniUnit());
        cd.setPackSize(StringUtils.strToBigDecimal(drug.getPackSize(), "pe"));
        cd.setPackUnit(drug.getPackUnit());
        cd.setSpecification(drug.getSpecification());
        cd.setSpeComment(drug.getSpeComment());
        cd.setFixPrice(StringUtils.strToBigDecimal(drug.getFixPrice(), "pe"));
        cd.setRetprice(StringUtils.strToBigDecimal(drug.getRetprice(), "pe"));
        cd.setManufactory(drug.getManufactory());
        cd.setSelfFlag(drug.getSelfFlag());
        cd.setSeparateFlag(drug.getSeparateFlag());
        cd.setSupriceFlag(drug.getSupriceFlag());
        cd.setDrugFlag(drug.getDrugFlag());
        cd.setPyCode(drug.getPyCode());
        cd.setInfusionFlag(drug.getInfusionFlag());
        cd.setCountryFlag(drug.getCountryFlag());
        cd.setDivideFlag(drug.getDivideFlag());
        cd.setDrugKind(drug.getDrugKind());
        cd.setZyBillItem(drug.getZyBillItem());
        cd.setMzBillItem(drug.getMzBillItem());
        cd.setZyChargeGroup(drug.getZyChargeGroup());
        cd.setMzChargeGroup(drug.getMzChargeGroup());
        cd.setClassCode(drug.getClassCode());
        cd.setExtendCode(drug.getExtendCode());
        cd.setSupplyCode(drug.getSupplyCode());
        cd.setFrequCode(drug.getFrequCode());
        cd.setOrderDosage(drug.getOrderDosage());
        cd.setDosageUnit(drug.getDosageUnit());
        cd.setBuyPrice(StringUtils.strToBigDecimal(drug.getBuyPrice(), "pe"));
        cd.setLowdosageFlag(drug.getLowdosageFlag());
        cd.setAuditCode(drug.getAuditCode());
        cd.setSkinTestFlag(drug.getSkinTestFlag());
        cd.setPrintName(drug.getPrintName());
        cd.setLicenseNo(drug.getLicenseNo());
        cd.setEffMonth(drug.getEffMonth());
        cd.setTradMark(drug.getTradMark());
        cd.setFuFlag(drug.getFuFlag());
        cd.setZySupplyCode(drug.getZySupplyCode());
        cd.setZyFrequCode(drug.getZyFrequCode());
        cd.setSeqNo(drug.getSeqNo());
        cd.setVersionNo(drug.getVersionNo());
        cd.setUpdateTime(systemDate);
        cd.setDeleteFlag(Constants.NO_DELETE_FLAG);
        cd.setDeleteTime(null);
        return cd;
    }

    @Override
    public Object getDeleteCodeValue(CodeValueDto codeValue)
    {
        Drug drug = (Drug) codeValue;
        // $Author :tong_meng
        // $Date : 2012/10/25 15:00$
        // [BUG]0010742 MODIFY BEGIN
        CodeDrug cd = getCDList(drug.getCode(), codeSystem.getCsId(),
        		StringUtils.isEmpty(drug.getSerial()) ? SERIAL_DEFAULT:drug.getSerial());
        // [BUG]0010742 MODIFY END
        cd.setVersionNo(drug.getVersionNo());
        cd.setUpdateTime(systemDate);
        cd.setDeleteFlag(Constants.DELETE_FLAG);
        cd.setDeleteTime(systemDate);
        return cd;
    }

    // $Author :tong_meng
    // $Date : 2012/10/25 15:00$
    // [BUG]0010742 MODIFY BEGIN
    private CodeDrug getCDList(String code, BigDecimal csId, String serial)
    {
        return dictionaryService.getCDList(code, csId, serial);
    }

    // [BUG]0010742 MODIFY END

    @Override
    public String getTableName()
    {
        return "CODE_DRUG";
    }

}
