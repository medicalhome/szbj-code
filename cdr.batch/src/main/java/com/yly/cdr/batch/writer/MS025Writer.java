package com.yly.cdr.batch.writer;

import java.math.BigDecimal;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.yly.cdr.core.Constants;
import com.yly.cdr.entity.CodeDepartment;
import com.yly.cdr.hl7.dto.CodeValueDto;
import com.yly.cdr.hl7.dto.Dept;

/**
 * 科室字典
 * @author tong_meng
 *
 */
@Component(value = "ms025Writer")
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class MS025Writer extends MSBaseWriter<Dept>
{

    @Override
    public Object getNewCodeValue(CodeValueDto codeValue)
    {
        Dept dept = (Dept) codeValue;
        CodeDepartment codeDept = null;
        codeDept = new CodeDepartment();
        codeDept.setCsId(codeSystem.getCsId());
        codeDept.setCode(dept.getCode()); // 科室编码
        codeDept.setName(dept.getName()); // 科室名称

        // $Author:wei_peng
        // $Date:2012/11/27 17:32
        // [BUG]0011945 ADD BEGIN
        codeDept.setOtherName(dept.getOtherName());// 别名
        codeDept.setOpenFlag(dept.getOpenFlag());// 开放标志
        codeDept.setMzFlag(dept.getMzFlag());// 门诊住院标志
        codeDept.setErpCode(dept.getErpCode());// ERP对照码
        codeDept.setAddress(dept.getAddress());// 地点
        codeDept.setIncomeType(dept.getIncomeType());// 收入类型
        // [BUG]0011945 ADD END

        codeDept.setPyCode(dept.getPyCode()); // 拼音码
        codeDept.setSeqNo(dept.getSeqNo()); // 排序码
        codeDept.setVersionNo(dept.getVersionNo());
        codeDept.setCreateTime(systemDate);
        codeDept.setUpdateCount(Constants.INSERT_UPDATE_COUNT);
        codeDept.setUpdateTime(systemDate);
        if (isDeleteMessage(dept.getNewUpFlag()))
        {
            codeDept.setDeleteFlag(Constants.DELETE_FLAG);
            codeDept.setDeleteTime(systemDate);
        }
        else if (isNewMessage(dept.getNewUpFlag())
            || isUpdateMessage(dept.getNewUpFlag()))
        {
            codeDept.setDeleteFlag(Constants.NO_DELETE_FLAG);
            codeDept.setDeleteTime(null);
        }
        return codeDept;
    }

    @Override
    public Object getUpdateCodeValue(CodeValueDto codeValue)
    {
        Dept dept = (Dept) codeValue;
        CodeDepartment codeDept = getCDList(dept.getCode(),
                codeSystem.getCsId());
        codeDept.setName(dept.getName()); // 科室名称

        // $Author:wei_peng
        // $Date:2012/11/27 17:32
        // [BUG]0011945 ADD BEGIN
        codeDept.setOtherName(dept.getOtherName());// 别名
        codeDept.setOpenFlag(dept.getOpenFlag());// 开放标志
        codeDept.setMzFlag(dept.getMzFlag());// 门诊住院标志
        codeDept.setErpCode(dept.getErpCode());// ERP对照码
        codeDept.setAddress(dept.getAddress());// 地点
        codeDept.setIncomeType(dept.getIncomeType());// 收入类型
        // [BUG]0011945 ADD END

        codeDept.setPyCode(dept.getPyCode()); // 拼音码
        codeDept.setSeqNo(dept.getSeqNo()); // 排序码
        codeDept.setVersionNo(dept.getVersionNo());
        codeDept.setUpdateTime(systemDate);
        codeDept.setDeleteFlag(Constants.NO_DELETE_FLAG);
        codeDept.setDeleteTime(null);
        return codeDept;
    }

    @Override
    public Object getDeleteCodeValue(CodeValueDto codeValue)
    {
        Dept dept = (Dept) codeValue;
        CodeDepartment codeDept = getCDList(dept.getCode(),
                codeSystem.getCsId());
        codeDept.setVersionNo(dept.getVersionNo());
        codeDept.setUpdateTime(systemDate);
        codeDept.setDeleteFlag(Constants.DELETE_FLAG);
        codeDept.setDeleteTime(systemDate);
        return codeDept;
    }

    private CodeDepartment getCDList(String code, BigDecimal csId)
    {
        return dictionaryService.getCDIPList(code, csId);
    }

    @Override
    public String getTableName()
    {
        return "CODE_DEPARTMENT";
    }
}
