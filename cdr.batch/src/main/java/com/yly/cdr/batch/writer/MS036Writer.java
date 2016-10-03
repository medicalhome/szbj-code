package com.yly.cdr.batch.writer;

import java.math.BigDecimal;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.yly.cdr.core.Constants;
import com.yly.cdr.entity.CodeOrderItem;
import com.yly.cdr.hl7.dto.CodeOrderItemDto;
import com.yly.cdr.hl7.dto.CodeValueDto;


/**
 * 医嘱字典
 * @author yu_yzh
 * */
@Component(value = "ms036Writer")
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class MS036Writer extends MSBaseWriter<CodeOrderItemDto>{

	@Override
	public Object getNewCodeValue(CodeValueDto codeValue) {
		CodeOrderItemDto dto = (CodeOrderItemDto) codeValue;
		CodeOrderItem coi = new CodeOrderItem();
		coi.setCsId(codeSystem.getCsId());
		coi.setCode(dto.getCode());
		coi.setValue(dto.getName());
		coi.setMessageTypeCode(dto.getMessageTypeCode());
		coi.setMessageTypeName(dto.getMessageTypeName());
		coi.setPyCode(dto.getPyCode());
		coi.setSeqNo(dto.getSeqNo());
        coi.setUpdateTime(systemDate);
        coi.setCreateTime(systemDate);
        coi.setUpdateCount(Constants.INSERT_UPDATE_COUNT);
        coi.setVersionNo(dto.getVersionNo());
        if (isDeleteMessage(dto.getNewUpFlag()))
        {
        	coi.setDeleteFlag(Constants.DELETE_FLAG);
        	coi.setDeleteTime(systemDate);
        }
        else if (isNewMessage(dto.getNewUpFlag())
            || isUpdateMessage(dto.getNewUpFlag()))
        {
        	coi.setDeleteFlag(Constants.NO_DELETE_FLAG);
        	coi.setDeleteTime(null);
        }       
		return coi;
	}

	@Override
	public Object getDeleteCodeValue(CodeValueDto codeValue) {
		CodeOrderItemDto dto = (CodeOrderItemDto) codeValue;
		CodeOrderItem coi = getOI(dto.getCode(), codeSystem.getCsId());
		coi.setVersionNo(dto.getVersionNo());
		coi.setUpdateTime(systemDate);
		coi.setDeleteTime(systemDate);
		coi.setDeleteFlag(Constants.DELETE_FLAG);
		return coi;
	}

	@Override
	public Object getUpdateCodeValue(CodeValueDto codeValue) {
		CodeOrderItemDto dto = (CodeOrderItemDto) codeValue;
		CodeOrderItem coi = getOI(dto.getCode(), codeSystem.getCsId());
		coi.setValue(dto.getName());
		coi.setMessageTypeCode(dto.getMessageTypeCode());
		coi.setMessageTypeName(dto.getMessageTypeName());
		coi.setPyCode(dto.getPyCode());
		coi.setSeqNo(dto.getSeqNo());
        coi.setUpdateTime(systemDate);
        coi.setCreateTime(systemDate);
        coi.setUpdateCount(Constants.INSERT_UPDATE_COUNT);
        coi.setVersionNo(dto.getVersionNo());
		return coi;
	}

	@Override
	public String getTableName() {
		return "CODE_ORDER_ITEM";
	}

	public CodeOrderItem getOI(String code, BigDecimal csId){
		return dictionaryService.getOI(code, csId);
	}

}
