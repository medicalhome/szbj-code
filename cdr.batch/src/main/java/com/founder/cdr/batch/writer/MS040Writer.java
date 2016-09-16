package com.founder.cdr.batch.writer;

import java.math.BigDecimal;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.founder.cdr.core.Constants;
import com.founder.cdr.entity.CodeExamItemGroup;
import com.founder.cdr.hl7.dto.CheckItem;
import com.founder.cdr.hl7.dto.CodeValueDto;

/**
 * 检查项目分组
 * @author tong_meng
 *
 */
@Component(value = "ms040Writer")
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class MS040Writer extends MSBaseWriter<CheckItem>
{

    @Override
    public Object getNewCodeValue(CodeValueDto codeValue)
    {
        CheckItem c = (CheckItem) codeValue;
        CodeExamItemGroup ci = null;
        ci = new CodeExamItemGroup();
        ci.setCsId(codeSystem.getCsId());
        ci.setCode(c.getCode()); // 病区编码
        ci.setName(c.getName());
        ci.setPyCode(c.getPyCode());
        ci.setReportHeader(c.getReportHeader());
        ci.setReportFooter(c.getReportFooter());
        ci.setApplyTemplate(c.getApplyTemplate());
        ci.setAppointmentReportCode(c.getAppointmentReportCode());
        ci.setAheadExamTime(c.getAheadExamTime());
        ci.setTmReportCode(c.getTmReportCode());
        ci.setScanFlag(c.getScanFlag());
        ci.setWeightFlag(c.getWeightFlag());
        ci.setSameDayFlag(c.getSameDayFlag());
        ci.setSeqNo(c.getSeqNo());
        ci.setCreateTime(systemDate);
        ci.setUpdateCount(Constants.INSERT_UPDATE_COUNT);
        ci.setUpdateTime(systemDate);
        ci.setVersionNo(c.getVersionNo());
        if (isDeleteMessage(c.getNewUpFlag()))
        {
            ci.setDeleteFlag(Constants.DELETE_FLAG);
            ci.setDeleteTime(systemDate);
        }
        else if (isNewMessage(c.getNewUpFlag())
            || isUpdateMessage(c.getNewUpFlag()))
        {
            ci.setDeleteFlag(Constants.NO_DELETE_FLAG);
            ci.setDeleteTime(null);
        }
        return ci;
    }

    @Override
    public Object getUpdateCodeValue(CodeValueDto codeValue)
    {
        CheckItem c = (CheckItem) codeValue;
        CodeExamItemGroup ci = getCEIGList(c.getCode(), codeSystem.getCsId());
        ci.setName(c.getName());
        ci.setPyCode(c.getPyCode());
        ci.setReportHeader(c.getReportHeader());
        ci.setReportFooter(c.getReportFooter());
        ci.setApplyTemplate(c.getApplyTemplate());
        ci.setAppointmentReportCode(c.getAppointmentReportCode());
        ci.setAheadExamTime(c.getAheadExamTime());
        ci.setTmReportCode(c.getTmReportCode());
        ci.setScanFlag(c.getScanFlag());
        ci.setWeightFlag(c.getWeightFlag());
        ci.setSameDayFlag(c.getSameDayFlag());
        ci.setSeqNo(c.getSeqNo());
        ci.setUpdateTime(systemDate);
        ci.setVersionNo(c.getVersionNo());
        ci.setDeleteFlag(Constants.NO_DELETE_FLAG);
        ci.setDeleteTime(null);
        return ci;
    }

    @Override
    public Object getDeleteCodeValue(CodeValueDto codeValue)
    {
        CheckItem c = (CheckItem) codeValue;
        CodeExamItemGroup ci = getCEIGList(c.getCode(), codeSystem.getCsId());
        ci.setVersionNo(c.getVersionNo());
        ci.setUpdateTime(systemDate);
        ci.setDeleteTime(systemDate);
        ci.setDeleteFlag(Constants.DELETE_FLAG);
        return ci;
    }

    private CodeExamItemGroup getCEIGList(String code, BigDecimal csId)
    {
        return dictionaryService.getCEIPList(code, csId);
    }

    @Override
    public String getTableName()
    {
        return "CODE_EXAM_ITEM_GROUP";
    }

}
