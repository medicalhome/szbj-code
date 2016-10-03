package com.yly.cdr.batch.writer;

import java.math.BigDecimal;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.yly.cdr.core.Constants;
import com.yly.cdr.entity.CodeLabItem;
import com.yly.cdr.hl7.dto.CodeValueDto;
import com.yly.cdr.hl7.dto.LabItem;

/**
 * 检验项目字典
 * @author tong_meng
 *
 */
@Component(value = "ms033Writer")
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class MS033Writer extends MSBaseWriter<LabItem>
{
    /**
     * 得到要新增或者更新的实体
     * @param li
     * @param systemDate
     * @param lab
     * @return
     */
    @Override
    public Object getNewCodeValue(CodeValueDto lab)
    {
        LabItem labDto = (LabItem) lab;
        CodeLabItem li = null;
        li = new CodeLabItem();
        li.setCsId(codeSystem.getCsId());
        li.setCode(labDto.getCode());
        li.setName(labDto.getName());
        li.setItemClass(labDto.getClasses());
        li.setPerformUnit(labDto.getPerformUnit());
        li.setVessel(labDto.getVessel());
        li.setDosage(labDto.getDosage());
        li.setSampleCode(labDto.getSampleCode());
        li.setPyCode(labDto.getPyCode());
        li.setEmergencyFlag(labDto.getEmergencyFlag());
        li.setWbCode(labDto.getWbCode());
        li.setYzOrderCode(labDto.getYzOrderCode());
        li.setSeqNo(labDto.getSortNo());
        li.setExecUnit(labDto.getExecNuit());
        li.setGroupSn(labDto.getGroupSn());
        li.setSrFlag(labDto.getSrFlag());
        li.setUpdateTime(systemDate);
        li.setCreateTime(systemDate);
        li.setUpdateCount(Constants.INSERT_UPDATE_COUNT);
        li.setVersionNo(labDto.getVersionNo());
        if (isDeleteMessage(labDto.getNewUpFlag()))
        {
            li.setDeleteFlag(Constants.DELETE_FLAG);
            li.setDeleteTime(systemDate);
        }
        else if (isNewMessage(labDto.getNewUpFlag())
            || isUpdateMessage(labDto.getNewUpFlag()))
        {
            li.setDeleteFlag(Constants.NO_DELETE_FLAG);
            li.setDeleteTime(null);
        }
        return li;
    }

    @Override
    public Object getUpdateCodeValue(CodeValueDto codeValue)
    {
        LabItem labDto = (LabItem) codeValue;
        CodeLabItem li = getLIList(labDto.getCode(), codeSystem.getCsId());
        li.setName(labDto.getName());
        li.setItemClass(labDto.getClasses());
        li.setPerformUnit(labDto.getPerformUnit());
        li.setVessel(labDto.getVessel());
        li.setDosage(labDto.getDosage());
        li.setSampleCode(labDto.getSampleCode());
        li.setPyCode(labDto.getPyCode());
        li.setEmergencyFlag(labDto.getEmergencyFlag());
        li.setWbCode(labDto.getWbCode());
        li.setYzOrderCode(labDto.getYzOrderCode());
        li.setSeqNo(labDto.getSortNo());
        li.setExecUnit(labDto.getExecNuit());
        li.setGroupSn(labDto.getGroupSn());
        li.setSrFlag(labDto.getSrFlag());
        li.setUpdateTime(systemDate);
        li.setVersionNo(labDto.getVersionNo());
        li.setDeleteFlag(Constants.NO_DELETE_FLAG);
        li.setDeleteTime(null); // 将已经删除的数据的删除时间置空--启用状态
        return li;
    }

    @Override
    public Object getDeleteCodeValue(CodeValueDto codeValue)
    {
        LabItem labDto = (LabItem) codeValue;
        CodeLabItem li = getLIList(labDto.getCode(), codeSystem.getCsId());
        li.setVersionNo(labDto.getVersionNo());
        li.setUpdateTime(systemDate);
        li.setDeleteTime(systemDate);
        li.setDeleteFlag(Constants.DELETE_FLAG);
        return li;
    }

    /**
     * 
     * @param csId
     * @param code
     * @return
     */
    public CodeLabItem getLIList(String code, BigDecimal csId)
    {
        return dictionaryService.getLIList(code, csId);
    }

    public String getTableName()
    {
        return "CODE_LAB_ITEM";
    }

}
