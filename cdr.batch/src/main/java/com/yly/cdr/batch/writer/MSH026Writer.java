package com.yly.cdr.batch.writer;

import java.math.BigDecimal;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.yly.cdr.core.Constants;
import com.yly.cdr.entity.CodeWard;
import com.yly.cdr.hl7.dto.CodeValueDto;
import com.yly.cdr.hl7.dto.WardDictionary;

/**
 * 病区字典
 * @author tong_meng
 *
 */
@Component(value = "msh026Writer")
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class MSH026Writer extends MSBaseWriter<WardDictionary>
{
    @Override
    public Object getNewCodeValue(CodeValueDto codeValue)
    {
        WardDictionary w = (WardDictionary) codeValue;
        CodeWard cw = new CodeWard();
        cw.setCsId(codeSystem.getCsId());
        cw.setCode(w.getCode()); // 病区编码
        cw.setName(w.getName()); // 病区名称
        cw.setDeptSn(w.getDeptSn()); // 科室编码
        cw.setDeptName(w.getDeptName());// 科室名称
        cw.setDeptPyCode(w.getDeptPyCode()); // 科室拼音码
        cw.setDeptDCode(w.getDeptDCode()); // 科室自定义码
        cw.setOpenbendNum(w.getOpenbendNum()); // 开放床位数
        cw.setWardPyCode(w.getWardPyCode()); // 病房拼音码
        cw.setWardDCode(w.getWardDCode()); // 病房自定义码

        // $Author :tong_meng
        // $Date : 2012/9/5 10:00$
        // [BUG]0009337 MODIFY BEGIN
        cw.setStartDayTimeKf(w.getStartDayTimeKf()); // 摆药时间
        // [BUG]0009337 MODIFY END

        cw.setWardOnly(w.getWardOnly()); // 按病房标志

        // $Author :tong_meng
        // $Date : 2012/9/5 10:00$
        // [BUG]0009337 MODIFY BEGIN
        cw.setDetailPrintTime(w.getDetailPrintTime()); // 病房打印细目时间
        // [BUG]0009337 MODIFY END

        cw.setAdtDeptNo(w.getAdtDeptNo()); // 住院处编号
        cw.setNurseUnitFlag(w.getNurseUnitFlag()); // 护理单元
        cw.setPlanBedCount(w.getPlanBedCount()); // 编制床位
        cw.setSeqNo(w.getSeqNo()); // 排序码
        cw.setWardSelf(w.getWardSelf()); // 大病区标志

        // $Author :tong_meng
        // $Date : 2012/9/5 10:00$
        // [BUG]0009337 MODIFY BEGIN
        cw.setDeletedInputDate(w.getDeletedInputDate()); // 删除操作员
        // [BUG]0009337 MODIFY END

        cw.setDeletedInputOpera(w.getDeletedInputOpera()); // 删除操作员
        cw.setComments(w.getComment()); // 备注
        cw.setStarttimeFlag(w.getStarttimeFlag()); // 开始时间
        cw.setPyCode(w.getPyCode()); // 拼音码
        cw.setCreateTime(systemDate);
        cw.setUpdateCount(Constants.INSERT_UPDATE_COUNT);
        cw.setUpdateTime(systemDate);
        cw.setVersionNo(w.getVersionNo()); // 版本号
        if (isDeleteMessage(w.getNewUpFlag()))
        {
            cw.setDeleteFlag(Constants.DELETE_FLAG);
            cw.setDeleteTime(systemDate);
        }
        else if (isNewMessage(w.getNewUpFlag())
            || isUpdateMessage(w.getNewUpFlag()))
        {
            cw.setDeleteFlag(Constants.NO_DELETE_FLAG);
            cw.setDeleteTime(null);
        }
        return cw;
    }

    @Override
    public Object getUpdateCodeValue(CodeValueDto codeValue)
    {
        WardDictionary w = (WardDictionary) codeValue;

        // $Author :tong_meng
        // $Date : 2012/11/05 11:00$
        // [BUG]0011055 MODIFY BEGIN
        // $Author :tong_meng
        // $Date : 2012/10/25 15:00$
        // [BUG]0010742 MODIFY BEGIN
        CodeWard cw = getWDList(w.getCode(), codeSystem.getCsId(),
                w.getDeptSn());
        // [BUG]0010742 MODIFY END
        // [BUG]0011055 MODIFY END

        cw.setName(w.getName()); // 病区名称
        cw.setDeptSn(w.getDeptSn()); // 科室编码
        cw.setDeptName(w.getDeptName());// 科室名称
        cw.setDeptPyCode(w.getDeptPyCode()); // 科室拼音码
        cw.setDeptDCode(w.getDeptDCode()); // 科室自定义码
        cw.setOpenbendNum(w.getOpenbendNum()); // 开放床位数
        cw.setWardPyCode(w.getWardPyCode()); // 病房拼音码
        cw.setWardDCode(w.getWardDCode()); // 病房自定义码

        // $Author :tong_meng
        // $Date : 2012/9/5 10:00$
        // [BUG]0009337 MODIFY BEGIN
        cw.setStartDayTimeKf(w.getStartDayTimeKf()); // 摆药时间
        // [BUG]0009337 MODIFY END

        cw.setWardOnly(w.getWardOnly()); // 按病房标志

        // $Author :tong_meng
        // $Date : 2012/9/5 10:00$
        // [BUG]0009337 MODIFY BEGIN
        cw.setDetailPrintTime(w.getDetailPrintTime()); // 病房打印细目时间
        // [BUG]0009337 MODIFY END

        cw.setAdtDeptNo(w.getAdtDeptNo()); // 住院处编号
        cw.setNurseUnitFlag(w.getNurseUnitFlag()); // 护理单元
        cw.setPlanBedCount(w.getPlanBedCount()); // 编制床位
        cw.setSeqNo(w.getSeqNo()); // 排序码
        cw.setWardSelf(w.getWardSelf()); // 大病区标志

        // $Author :tong_meng
        // $Date : 2012/9/5 10:00$
        // [BUG]0009337 MODIFY BEGIN
        cw.setDeletedInputDate(w.getDeletedInputDate()); // 删除操作员
        // [BUG]0009337 MODIFY END

        cw.setDeletedInputOpera(w.getDeletedInputOpera()); // 删除操作员
        cw.setComments(w.getComment()); // 备注
        cw.setStarttimeFlag(w.getStarttimeFlag()); // 开始时间
        cw.setPyCode(w.getPyCode()); // 拼音码
        cw.setUpdateTime(systemDate);
        cw.setVersionNo(w.getVersionNo()); // 版本号
        cw.setDeleteFlag(Constants.NO_DELETE_FLAG);
        cw.setDeleteTime(null);
        return cw;
    }

    @Override
    public Object getDeleteCodeValue(CodeValueDto codeValue)
    {
        WardDictionary w = (WardDictionary) codeValue;
        // $Author :tong_meng
        // $Date : 2012/11/05 11:00$
        // [BUG]0011055 MODIFY BEGIN
        // $Author :tong_meng
        // $Date : 2012/10/25 15:00$
        // [BUG]0010742 MODIFY BEGIN
        CodeWard li = getWDList(w.getCode(), codeSystem.getCsId(),
                w.getDeptSn());
        // [BUG]0010742 MODIFY END
        // [BUG]0011055 MODIFY END
        li.setVersionNo(w.getVersionNo());
        li.setUpdateTime(systemDate);
        li.setDeleteTime(systemDate);
        li.setDeleteFlag(Constants.DELETE_FLAG);
        return li;
    }

    // $Author :tong_meng
    // $Date : 2012/10/25 15:00$
    // [BUG]0010742 MODIFY BEGIN
    private CodeWard getWDList(String code, BigDecimal csId, String name)
    {
        return dictionaryService.getCWList(code, csId, name);
    }

    // [BUG]0010742 MODIFY END

    @Override
    public String getTableName()
    {
        return "CODE_WARD";
    }

}
