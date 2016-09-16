package com.founder.cdr.batch.writer;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.founder.cdr.core.Constants;
import com.founder.cdr.entity.CodeMedicalName;
import com.founder.cdr.hl7.dto.CodeValueDto;
import com.founder.cdr.hl7.dto.DrugName;

@Component(value = "ms053Writer")
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class MS053Writer extends MSBaseWriter<DrugName>
{

    @Override
    public Object getNewCodeValue(CodeValueDto codeValue)
    {
        DrugName d = (DrugName) codeValue;
        CodeMedicalName c = null;
        c = new CodeMedicalName();
        c.setCsId(codeSystem.getCsId());
        c.setCode(d.getCode());
        c.setName(d.getName());
        c.setFlag(d.getFlag());
        c.setPrintName(d.getPrintName());
        c.setNameType(d.getNameType());
        c.setPyCode(d.getPyCode());
        c.setCreateTime(systemDate);
        c.setUpdateCount(Constants.INSERT_UPDATE_COUNT);
        c.setUpdateTime(systemDate);
        c.setVersionNo(d.getVersionNo());
        if (isDeleteMessage(d.getNewUpFlag()))
        {
            c.setDeleteFlag(Constants.DELETE_FLAG);
            c.setDeleteTime(systemDate);
        }
        else if (isNewMessage(d.getNewUpFlag())
            || isUpdateMessage(d.getNewUpFlag()))
        {
            c.setDeleteFlag(Constants.NO_DELETE_FLAG);
            c.setDeleteTime(null);
        }
        return c;
    }

    @Override
    public Object getDeleteCodeValue(CodeValueDto codeValue)
    {
        DrugName d = (DrugName) codeValue;
        CodeMedicalName c = getCMNList(d.getCode(), d.getName());
        c.setVersionNo(d.getVersionNo());
        c.setUpdateTime(systemDate);
        c.setDeleteTime(systemDate);
        c.setDeleteFlag(Constants.DELETE_FLAG);
        return c;
    }

    @Override
    public Object getUpdateCodeValue(CodeValueDto codeValue)
    {
        DrugName d = (DrugName) codeValue;
        CodeMedicalName c = getCMNList(d.getCode(), d.getName());
        c.setFlag(d.getFlag());
        c.setPrintName(d.getPrintName());
        c.setNameType(d.getNameType());
        c.setPyCode(d.getPyCode());
        c.setUpdateTime(systemDate);
        c.setVersionNo(d.getVersionNo());
        c.setDeleteFlag(Constants.NO_DELETE_FLAG);
        c.setDeleteTime(null);
        return c;
    }

    private CodeMedicalName getCMNList(String code, String name)
    {
        return dictionaryService.getCMNList(code, name);
    }

    @Override
    public String getTableName()
    {
        return "CODE_MEDICAL_NAME";
    }

}
