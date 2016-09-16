package com.founder.cdr.batch.writer;

import java.math.BigDecimal;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.founder.cdr.core.Constants;
import com.founder.cdr.entity.CodeIcd;
import com.founder.cdr.entity.CodeOperation;
import com.founder.cdr.entity.CodeValue;
import com.founder.cdr.hl7.dto.CodeValueDto;
import com.founder.cdr.hl7.dto.CodeValues;
import com.founder.cdr.hl7.dto.ICD;
import com.founder.cdr.hl7.dto.Operation;

/**
 * 手术与操作字典
 * @author jia_yanqing
 *
 */
@Component(value = "ms004Writer")
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class MS004Writer extends MSBaseWriter<Operation>
{
    /**
     * 得到要新增或者更新的实体
     * @param value
     * @return Object
     */
    @Override
    public Object getNewCodeValue(CodeValueDto value)
    {
        Operation operation = (Operation) value;
        CodeOperation co = null;
        co = new CodeOperation();
        co.setCsId(codeSystem.getCsId());
        co.setCode(operation.getCode());
        co.setIcdCode(operation.getIcdCode());
        co.setIcdName(operation.getIcdName());
        co.setPyCode(operation.getPyCode());
        co.setSeqNo(operation.getSeqNo());
        co.setVersionNo(operation.getVersionNo());
        co.setStandardFlg(operation.getStandardFlg()); //编码类型
        co.setUpdateCount(Constants.INSERT_UPDATE_COUNT);
        co.setCreateTime(systemDate);
        co.setUpdateTime(systemDate);
        if (isDeleteMessage(operation.getNewUpFlag()))
        {
            co.setDeleteFlag(Constants.DELETE_FLAG);
            co.setDeleteTime(systemDate);
        }
        else if (isNewMessage(operation.getNewUpFlag())
            || isUpdateMessage(operation.getNewUpFlag()))
        {
            co.setDeleteFlag(Constants.NO_DELETE_FLAG);
            co.setDeleteTime(null);
        }
        return co;
    }

    @Override
    public Object getUpdateCodeValue(CodeValueDto codeValue)
    {
        Operation operation = (Operation) codeValue;
        CodeOperation co = getOperationList(operation.getCode(), codeSystem.getCsId());
        co.setIcdName(operation.getIcdName()); // 疾病名称
        co.setPyCode(operation.getPyCode()); // 拼音码
        co.setIcdCode(operation.getIcdCode()); // ICDcode
        co.setSource(Constants.EMR); // 来源
        co.setSeqNo(operation.getSeqNo()); // 排序码
        co.setStandardFlg(operation.getStandardFlg()); //编码类型
        co.setVersionNo(operation.getVersionNo());
        co.setUpdateTime(systemDate);
        co.setDeleteFlag(Constants.NO_DELETE_FLAG);
        co.setDeleteTime(null);
        return co;
    }

    @Override
    public Object getDeleteCodeValue(CodeValueDto codeValue)
    {
        Operation operation = (Operation) codeValue;
        CodeOperation co = getOperationList(operation.getCode(), codeSystem.getCsId());
        co.setVersionNo(operation.getVersionNo());
        co.setUpdateTime(systemDate);
        co.setDeleteTime(systemDate);
        co.setDeleteFlag(Constants.DELETE_FLAG);
        return co;
    }

    private CodeOperation getOperationList(String code, BigDecimal csId)
    {
        return dictionaryService.getCOList(code, csId);
    }

    @Override
    public String getTableName()
    {
        return "CODE_OPERATION";
    }
}
