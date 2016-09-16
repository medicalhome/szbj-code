// $Author :tong_meng
// $Date : 2013/1/16 14:00$
// [BUG]0013110 ADD BEGIN
package com.founder.cdr.batch.writer;

import java.math.BigDecimal;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.founder.cdr.core.Constants;
import com.founder.cdr.entity.CodeChargeItem;
import com.founder.cdr.hl7.dto.ChargeItem;
import com.founder.cdr.hl7.dto.CodeValueDto;

@Component(value = "ms031Writer")
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class MS031Writer extends MSBaseWriter<ChargeItem>
{
    @Override
    public Object getNewCodeValue(CodeValueDto codeValue)
    {
        ChargeItem chargeItem = (ChargeItem) codeValue;
        CodeChargeItem chi = new CodeChargeItem();
        chi.setCsId(codeSystem.getCsId());
        chi.setItemCode(chargeItem.getItemCode());
        chi.setItemName(chargeItem.getItemName());
        chi.setSpecification(chargeItem.getChargeUnit());
        chi.setPyCode(chargeItem.getPyCode());
        chi.setSeqNo(chargeItem.getSeqNo());
        chi.setVersionNo(chargeItem.getVersionNo());
        chi.setCreateTime(systemDate);
        chi.setUpdateCount(Constants.INSERT_UPDATE_COUNT);
        chi.setUpdateTime(systemDate);
        if (isDeleteMessage(chargeItem.getNewUpFlag()))
        {
            chi.setDeleteFlag(Constants.DELETE_FLAG);
            chi.setDeleteTime(systemDate);
        }
        else if (isNewMessage(chargeItem.getNewUpFlag())
            || isUpdateMessage(chargeItem.getNewUpFlag()))
        {
            chi.setDeleteFlag(Constants.NO_DELETE_FLAG);
            chi.setDeleteTime(null);
        }
        return chi;
    }
    
    @Override
    public Object getUpdateCodeValue(CodeValueDto codeValue)
    {
        ChargeItem chargeItem = (ChargeItem) codeValue;
        CodeChargeItem chi = getCHIList(chargeItem.getItemCode(),codeSystem.getCsId());
        chi.setItemName(chargeItem.getItemName());
        chi.setSpecification(chargeItem.getChargeUnit());
        chi.setPyCode(chargeItem.getPyCode());
        chi.setSeqNo(chargeItem.getSeqNo());
        chi.setVersionNo(chargeItem.getVersionNo());
        chi.setUpdateTime(systemDate);
        chi.setDeleteFlag(Constants.NO_DELETE_FLAG);
        chi.setDeleteTime(null);
        return chi;
    }
    
    @Override
    public Object getDeleteCodeValue(CodeValueDto codeValue)
    {
        ChargeItem chargeItem = (ChargeItem) codeValue;
        CodeChargeItem chi = getCHIList(chargeItem.getItemCode(),codeSystem.getCsId());
        chi.setVersionNo(chargeItem.getVersionNo());
        chi.setUpdateTime(systemDate);
        chi.setDeleteFlag(Constants.DELETE_FLAG);
        chi.setDeleteTime(systemDate);
        return chi;
    }
    
    private CodeChargeItem getCHIList(String itemCode, BigDecimal csId)
    {
        return dictionaryService.getCHIList(itemCode,csId);
    }
    
    @Override
    public String getTableName()
    {
        return "CODE_CHARGE_ITEM";
    }
}
//[BUG]0013110 ADD END
