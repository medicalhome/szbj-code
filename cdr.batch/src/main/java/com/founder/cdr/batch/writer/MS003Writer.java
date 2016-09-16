package com.founder.cdr.batch.writer;

import java.math.BigDecimal;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.founder.cdr.core.Constants;
import com.founder.cdr.entity.CodeIcd;
import com.founder.cdr.hl7.dto.CodeValueDto;
import com.founder.cdr.hl7.dto.ICD;

/**
 * 国际疾病-电子病历
 * @author tong_meng
 *
 */
@Component(value = "ms003Writer")
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class MS003Writer extends MSBaseWriter<ICD>
{

    @Override
    public Object getNewCodeValue(CodeValueDto codeValue)
    {
        ICD icd = (ICD) codeValue;
        CodeIcd ci = null;
        ci = new CodeIcd();
        ci.setCsId(codeSystem.getCsId());

        // $Author :tong_meng
        // $Date : 2012/9/6 15:00$
        // [BUG]0009481 ADD BEGIN
        ci.setCode(icd.getCode()); // 疾病编码
        // [BUG]0009481 ADD END

        ci.setName(icd.getName()); // 疾病名称
        ci.setPyCode(icd.getPyCode()); // 拼音码
        ci.setDeptNo(icd.getSpecialFlag2()); // 所属科室
        ci.setIcdCode(icd.getIcdCode()); // ICDcode
        ci.setSource(Constants.EMR); // 来源
        ci.setSeqNo(icd.getSeqNo()); // 排序码
        // Author :jia_yanqing
        // Date : 2013/10/11 17:30
        // [BUG]0037993 ADD BEGIN
        ci.setStandardFlg(icd.getStandardFlg()); //编码类型
        // [BUG]0037993 ADD END
        ci.setVersionNo(icd.getVersionNo());
        ci.setCreateTime(systemDate);
        ci.setUpdateCount(Constants.INSERT_UPDATE_COUNT);
        ci.setUpdateTime(systemDate);
        if (isDeleteMessage(icd.getNewUpFlag()))
        {
            ci.setDeleteFlag(Constants.DELETE_FLAG);
            ci.setDeleteTime(systemDate);
        }
        else if (isNewMessage(icd.getNewUpFlag())
            || isUpdateMessage(icd.getNewUpFlag()))
        {
            ci.setDeleteFlag(Constants.NO_DELETE_FLAG);
            ci.setDeleteTime(null);
        }
        return ci;
    }

    @Override
    public Object getUpdateCodeValue(CodeValueDto codeValue)
    {
        ICD icd = (ICD) codeValue;
        CodeIcd ci = getICDList(icd.getCode(), codeSystem.getCsId());
        ci.setName(icd.getName()); // 疾病名称
        ci.setPyCode(icd.getPyCode()); // 拼音码
        ci.setDeptNo(icd.getSpecialFlag2()); // 所属科室
        ci.setIcdCode(icd.getIcdCode()); // ICDcode
        ci.setSource(Constants.EMR); // 来源
        ci.setSeqNo(icd.getSeqNo()); // 排序码
        // Author :jia_yanqing
        // Date : 2013/10/11 17:30
        // [BUG]0037993 ADD BEGIN
        ci.setStandardFlg(icd.getStandardFlg()); //编码类型
        // [BUG]0037993 ADD END
        ci.setVersionNo(icd.getVersionNo());
        ci.setUpdateTime(systemDate);
        ci.setDeleteFlag(Constants.NO_DELETE_FLAG);
        ci.setDeleteTime(null);
        return ci;
    }

    @Override
    public Object getDeleteCodeValue(CodeValueDto codeValue)
    {
        ICD icd = (ICD) codeValue;
        CodeIcd ci = getICDList(icd.getCode(), codeSystem.getCsId());
        ci.setVersionNo(icd.getVersionNo());
        ci.setUpdateTime(systemDate);
        ci.setDeleteTime(systemDate);
        ci.setDeleteFlag(Constants.DELETE_FLAG);
        return ci;
    }

    private CodeIcd getICDList(String code, BigDecimal csId)
    {
        return dictionaryService.getCIList(code, csId);
    }

    @Override
    public String getTableName()
    {
        return "CODE_ICD";
    }

}
