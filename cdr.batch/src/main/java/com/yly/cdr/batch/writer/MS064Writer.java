package com.yly.cdr.batch.writer;

import java.math.BigDecimal;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.yly.cdr.core.Constants;
import com.yly.cdr.entity.CodeLabSubitem;
import com.yly.cdr.hl7.dto.CodeValueDto;
import com.yly.cdr.hl7.dto.LabSubitem;

/**
 * 检验子项目字典
 * @author jia_yanqing
 * @date 2012/11/29
 */
@Component(value = "ms064Writer")
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class MS064Writer extends MSBaseWriter<LabSubitem>
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
        LabSubitem labDto = (LabSubitem) lab;
        CodeLabSubitem li = null;
        li = new CodeLabSubitem();
        li.setCsId(codeSystem.getCsId());
        li.setCode(labDto.getCode());
        li.setName(labDto.getName());
        li.setPyCode(labDto.getPyCode());
        li.setFullName(labDto.getFullName());
        li.setChineseName(labDto.getChineseName());
        li.setDecimals(labDto.getDecimals());
        li.setUnits(labDto.getUnits());
        li.setMaleRefer(labDto.getMaleRefer());
        li.setFemaleRefer(labDto.getFemaleRefer());
        li.setClinicalMeaning(labDto.getClinicalMeaning());
        li.setOperateRemark(labDto.getOperateRemark());
        li.setAppRange(labDto.getAppRange());
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
        LabSubitem labDto = (LabSubitem) codeValue;
        CodeLabSubitem li = getLSIList(labDto.getCode(), codeSystem.getCsId());
        li.setName(labDto.getName());
        li.setPyCode(labDto.getPyCode());
        li.setFullName(labDto.getFullName());
        li.setChineseName(labDto.getChineseName());
        li.setDecimals(labDto.getDecimals());
        li.setUnits(labDto.getUnits());
        li.setMaleRefer(labDto.getMaleRefer());
        li.setFemaleRefer(labDto.getFemaleRefer());
        li.setClinicalMeaning(labDto.getClinicalMeaning());
        li.setOperateRemark(labDto.getOperateRemark());
        li.setAppRange(labDto.getAppRange());
        li.setUpdateTime(systemDate);
        li.setVersionNo(labDto.getVersionNo());
        li.setDeleteFlag(Constants.NO_DELETE_FLAG);
        li.setDeleteTime(null); // 将已经删除的数据的删除时间置空--启用状态
        return li;
    }

    @Override
    public Object getDeleteCodeValue(CodeValueDto codeValue)
    {
        LabSubitem labDto = (LabSubitem) codeValue;
        CodeLabSubitem li = getLSIList(labDto.getCode(), codeSystem.getCsId());
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
    public CodeLabSubitem getLSIList(String code, BigDecimal csId)
    {
        return dictionaryService.getLSIList(code, csId);
    }

    public String getTableName()
    {
        return "CODE_LAB_SUBITEM";
    }

}
