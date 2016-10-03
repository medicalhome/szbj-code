package com.yly.cdr.batch.writer;

import java.math.BigDecimal;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.yly.cdr.core.Constants;
import com.yly.cdr.entity.CodeValue;
import com.yly.cdr.hl7.dto.CodeValueDto;
import com.yly.cdr.hl7.dto.CodeValues;

/**
 * 通用术语
 * @author tong_meng
 *
 */
@Component(value = "msh0002Writer")
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class MSH0002Writer extends MSBaseWriter<CodeValues>
{
    /**
     * 得到要新增或者更新的实体
     * @param li
     * @param systemDate
     * @param lab
     * @return
     */
    @Override
    public Object getNewCodeValue(CodeValueDto value)
    {
        CodeValues valueDto = (CodeValues) value;
        CodeValue cv = null;
        cv = new CodeValue();
        cv.setCsId(codeSystem.getCsId());
        cv.setCvCode(valueDto.getCode());
        cv.setCvValue(valueDto.getName());
        cv.setPyCode(valueDto.getPyCode());
        cv.setSeqNo(valueDto.getSeqNo());
        cv.setVersionNo(valueDto.getVersionNo());
        cv.setUpdateCount(Constants.INSERT_UPDATE_COUNT);
        cv.setCreateTime(systemDate);
        cv.setUpdateTime(systemDate);
        if (isDeleteMessage(valueDto.getNewUpFlag()))
        {
            cv.setDeleteFlag(Constants.DELETE_FLAG);
            cv.setDeleteTime(systemDate);
        }
        else if (isNewMessage(valueDto.getNewUpFlag())
            || isUpdateMessage(valueDto.getNewUpFlag()))
        {
            cv.setDeleteFlag(Constants.NO_DELETE_FLAG);
            cv.setDeleteTime(null);
        }
        return cv;
    }

    @Override
    public Object getUpdateCodeValue(CodeValueDto value)
    {
        CodeValues valueDto = (CodeValues) value;
        // $Author :tong_meng
        // $Date : 2012/10/25 15:00$
        // [BUG]0010742 MODIFY BEGIN
        CodeValue cv = getCVList(valueDto.getCode(), codeSystem.getCsId(),
                valueDto.getName());
        // [BUG]0010742 MODIFY END
        cv.setCvValue(valueDto.getName());
        cv.setPyCode(valueDto.getPyCode());
        cv.setSeqNo(valueDto.getSeqNo());
        cv.setVersionNo(valueDto.getVersionNo());

        // $Author :tong_meng
        // $Date : 2012/9/6 15:00$
        // [BUG]0009448 ADD BEGIN
        cv.setUpdateTime(systemDate);
        // [BUG]0009448 ADD END

        cv.setDeleteFlag(Constants.NO_DELETE_FLAG);
        cv.setDeleteTime(null);
        return cv;
    }

    @Override
    public Object getDeleteCodeValue(CodeValueDto value)
    {
        CodeValues valueDto = (CodeValues) value;
        // $Author :tong_meng
        // $Date : 2012/10/25 15:00$
        // [BUG]0010742 MODIFY BEGIN
        CodeValue cv = getCVList(valueDto.getCode(), codeSystem.getCsId(),
                valueDto.getName());
        // [BUG]0010742 MODIFY END
        cv.setVersionNo(valueDto.getVersionNo());
        cv.setUpdateTime(systemDate);
        cv.setDeleteTime(systemDate);
        cv.setDeleteFlag(Constants.DELETE_FLAG);
        return cv;
    }

    // $Author :tong_meng
    // $Date : 2012/10/25 15:00$
    // [BUG]0010742 MODIFY BEGIN
    public CodeValue getCVList(String code, BigDecimal csId, String name)
    {
        return dictionaryService.getCVList(code, csId, codeSystem.getCsCode());
    }

    // [BUG]0010742 MODIFY END

    public String getTableName()
    {
        return "CODE_VALUE";
    }

}
