/**
 * Copryright
 */
package com.yly.cdr.dao;

import java.math.BigDecimal;
import java.util.List;

import com.founder.fasf.core.util.daohelper.annotation.Arguments;
import com.yly.cdr.dto.TimerAndInpatientDto;
import com.yly.cdr.entity.NursingOperation;

/**
 * 用于时间轴视图和住院视图共同操作
 * @author jin_peng
 * @version 1.0, 2012/08/02 
 */
public interface TimerAndInpatientDao
{
    /**
     * 根据检验大项内部序列号查询相应检验具体结果
     * @param lrciSn 检验大项内部序列号
     * @return 检验大项对应具体结果
     */
    @Arguments({ "orderSn" })
    public List<TimerAndInpatientDto> selectLabResultItemList(BigDecimal orderSn);

    // $Author :jin_peng
    // $Date : 2012/08/23 14:10$
    // [BUG]0007863 ADD BEGIN
    /**
     * 根据检验大项内部序列号查询相应检验具体结果及对应的抗菌药物信息
     * @param lrciSn 检验大项内部序列号
     * @return 检验大项对应具体结果及抗菌药物信息
     */
    @Arguments({ "orderSn"})
    public List<TimerAndInpatientDto> selectMicrobeResultItemList(
            BigDecimal orderSn);

    // [BUG]0007863 ADD END

    // $Author:wei_peng
    // $Date:2012/10/10 9:35
    // $[BUG]0010243 ADD BEGIN
    /**
     * 查找检查或检验医嘱相关的护理操作集合
     * @param orderSn 医嘱内部序列号
     * @return 护理操作记录集合
     */
    @Arguments({ "orderSn" })
    public List<NursingOperation> selectNursingOptByOrderSn(BigDecimal orderSn);
    // $[BUG]0010243 ADD END
}
