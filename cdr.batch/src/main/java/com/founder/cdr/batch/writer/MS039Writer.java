package com.founder.cdr.batch.writer;

import java.math.BigDecimal;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.founder.cdr.core.Constants;
import com.founder.cdr.entity.CodeExamItem;
import com.founder.cdr.hl7.dto.CodeValueDto;
import com.founder.cdr.hl7.dto.ExamItem;

/**
 * 检查项目字典
 * @author tong_meng
 *
 */
@Component(value = "ms039Writer")
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class MS039Writer extends MSBaseWriter<ExamItem>
{
    @Override
    public Object getNewCodeValue(CodeValueDto codeValue)
    {
        ExamItem lab = (ExamItem) codeValue;
        CodeExamItem ei = null;
        ei = new CodeExamItem();
        ei.setCsId(codeSystem.getCsId());
        ei.setCode(lab.getCode());
        ei.setTypeCode(lab.getTypeCode());
        ei.setName(lab.getName());
        ei.setPyCode(lab.getPyCode());
        ei.setReportHeader(lab.getReportHeader());
        ei.setReportFooter(lab.getReportFooter());
        ei.setItemClass(lab.getClasses());
        ei.setExecUnit(lab.getExecUnit());
        ei.setYzOrderCode(lab.getYzOrderCode());
        ei.setSubClass(lab.getSubClass());
        ei.setExamRegion(lab.getExamRegion());
        ei.setKfFlag(lab.getKfFlag());
        ei.setDyFlag(lab.getDyFlag());
        ei.setWcjcFlag(lab.getWcjcFlag());
        ei.setKyFlag(lab.getKfFlag());
        ei.setSeqNo(lab.getSortNo());
        ei.setArriveTime(lab.getArriveTime());
        ei.setAheadExamTime(lab.getAheadExamTime());
        ei.setCompareCode(lab.getCompareCode2());
        ei.setGroupSn(lab.getGroupSn());
        ei.setDefaultTemplate(lab.getDefaultTemplate());
        ei.setComments(lab.getComment());
        ei.setMzZyFlag(lab.getMzZyFlag());
        ei.setUpdateTime(systemDate);
        ei.setCreateTime(systemDate);
        ei.setUpdateCount(Constants.INSERT_UPDATE_COUNT);
        ei.setVersionNo(lab.getVersionNo());
        if (isDeleteMessage(lab.getNewUpFlag()))
        {
            ei.setDeleteFlag(Constants.DELETE_FLAG);
            ei.setDeleteTime(systemDate);
        }
        else if (isNewMessage(lab.getNewUpFlag())
            || isUpdateMessage(lab.getNewUpFlag()))
        {
            ei.setDeleteFlag(Constants.NO_DELETE_FLAG);
            ei.setDeleteTime(null);
        }
        return ei;
    }

    @Override
    public Object getUpdateCodeValue(CodeValueDto codeValue)
    {
        ExamItem lab = (ExamItem) codeValue;
        CodeExamItem ei = getCEIList(lab.getCode(), codeSystem.getCsId());
        ei.setTypeCode(lab.getTypeCode());
        ei.setName(lab.getName());
        ei.setPyCode(lab.getPyCode());
        ei.setReportHeader(lab.getReportHeader());
        ei.setReportFooter(lab.getReportFooter());
        ei.setItemClass(lab.getClasses());
        ei.setExecUnit(lab.getExecUnit());
        ei.setYzOrderCode(lab.getYzOrderCode());
        ei.setSubClass(lab.getSubClass());
        ei.setExamRegion(lab.getExamRegion());
        ei.setKfFlag(lab.getKfFlag());
        ei.setDyFlag(lab.getDyFlag());
        ei.setWcjcFlag(lab.getWcjcFlag());
        ei.setKyFlag(lab.getKfFlag());
        ei.setSeqNo(lab.getSortNo());
        ei.setArriveTime(lab.getArriveTime());
        ei.setAheadExamTime(lab.getAheadExamTime());
        ei.setCompareCode(lab.getCompareCode2());
        ei.setGroupSn(lab.getGroupSn());
        ei.setDefaultTemplate(lab.getDefaultTemplate());
        ei.setComments(lab.getComment());
        ei.setMzZyFlag(lab.getMzZyFlag());
        ei.setUpdateTime(systemDate);
        ei.setVersionNo(lab.getVersionNo());
        ei.setDeleteFlag(Constants.NO_DELETE_FLAG);
        ei.setDeleteTime(null);
        return ei;
    }

    @Override
    public Object getDeleteCodeValue(CodeValueDto codeValue)
    {
        ExamItem lab = (ExamItem) codeValue;
        CodeExamItem ei = getCEIList(lab.getCode(), codeSystem.getCsId());
        ei.setVersionNo(lab.getVersionNo());
        ei.setUpdateTime(systemDate);
        ei.setDeleteTime(systemDate);
        ei.setDeleteFlag(Constants.DELETE_FLAG);
        return ei;
    }

    private CodeExamItem getCEIList(String code, BigDecimal csId)
    {
        return dictionaryService.getCEIList(code, csId);
    }

    @Override
    public String getTableName()
    {
        return "CODE_EXAM_ITEM";
    }

}
