package com.yly.cdr.batch.writer;

import java.math.BigDecimal;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.yly.cdr.core.Constants;
import com.yly.cdr.entity.CodeIcd;
import com.yly.cdr.hl7.dto.CodeValueDto;
import com.yly.cdr.hl7.dto.EmergencyTreatment;
import com.yly.cdr.util.StringUtils;

/**
 * 国际疾病-门急诊
 * @author tong_meng
 *
 */
@Component(value = "ms002Writer")
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class MS002Writer extends MSBaseWriter<EmergencyTreatment>
{

    @Override
    public Object getNewCodeValue(CodeValueDto codeValue)
    {
        EmergencyTreatment et = (EmergencyTreatment) codeValue;
        CodeIcd ci = new CodeIcd();
        ci = new CodeIcd();
        ci.setCsId(codeSystem.getCsId());
        ci.setCode(et.getCode()); // 疾病编码
        ci.setName(et.getName()); // 疾病名称
        ci.setDiseaseReason(et.getDiseaseReason()); // 分类统计码
        ci.setDeathReason(et.getDeathReason()); // 死亡原因码
        ci.setPyCode(et.getPyCode()); // 拼音码
        ci.setFlag(StringUtils.strToBigDecimal(et.getFlag(), "级别")); // 级别
        ci.setAddCode(et.getAddCode()); // 临床表现自添加标志
        ci.setExtraFlag(et.getExtraFlag()); // 额外标志
        ci.setIcdVer(et.getIcdVer()); // ICD版本
        ci.setSource(Constants.OUTPATIENT); // 来源
        ci.setSeqNo(et.getSeqNo()); // 排序码
        ci.setVersionNo(et.getVersionNo());
        ci.setCreateTime(systemDate);
        ci.setUpdateCount(Constants.INSERT_UPDATE_COUNT);
        ci.setUpdateTime(systemDate);
        if (isDeleteMessage(et.getNewUpFlag()))
        {
            ci.setDeleteFlag(Constants.DELETE_FLAG);
            ci.setDeleteTime(systemDate);
        }
        else if (isNewMessage(et.getNewUpFlag())
            || isUpdateMessage(et.getNewUpFlag()))
        {
            ci.setDeleteFlag(Constants.NO_DELETE_FLAG);
            ci.setDeleteTime(null);
        }
        return ci;
    }

    @Override
    public Object getUpdateCodeValue(CodeValueDto codeValue)
    {
        EmergencyTreatment et = (EmergencyTreatment) codeValue;
        CodeIcd ci = getCIList(et.getCode(), codeSystem.getCsId());
        ci.setName(et.getName()); // 疾病名称
        ci.setDiseaseReason(et.getDiseaseReason()); // 分类统计码
        ci.setDeathReason(et.getDeathReason()); // 死亡原因码
        ci.setPyCode(et.getPyCode()); // 拼音码
        ci.setFlag(StringUtils.strToBigDecimal(et.getFlag(), "级别")); // 级别
        ci.setAddCode(et.getAddCode()); // 临床表现自添加标志
        ci.setExtraFlag(et.getExtraFlag()); // 额外标志
        ci.setIcdVer(et.getIcdVer()); // ICD版本
        ci.setSource(Constants.OUTPATIENT); // 来源
        ci.setSeqNo(et.getSeqNo()); // 排序码
        ci.setVersionNo(et.getVersionNo());
        ci.setUpdateTime(systemDate);
        ci.setDeleteFlag(Constants.NO_DELETE_FLAG);
        ci.setDeleteTime(null);
        return ci;
    }

    @Override
    public Object getDeleteCodeValue(CodeValueDto codeValue)
    {
        EmergencyTreatment et = (EmergencyTreatment) codeValue;
        CodeIcd ci = getCIList(et.getCode(), codeSystem.getCsId());
        ci.setVersionNo(et.getVersionNo());
        ci.setUpdateTime(systemDate);
        ci.setDeleteTime(systemDate);
        ci.setDeleteFlag(Constants.DELETE_FLAG);
        return ci;
    }

    private CodeIcd getCIList(String code, BigDecimal csId)
    {
        return dictionaryService.getCIList(code, csId);
    }

    @Override
    public String getTableName()
    {
        return "CODE_ICD";
    }

}
