/**
 * Copryright
 */
package com.founder.cdr.dao;

import java.math.BigDecimal;
import java.util.List;

import com.founder.cdr.dto.TimerAndInpatientDto;
import com.founder.fasf.core.util.daohelper.annotation.Arguments;

/**
 * 用于时间轴视图操作
 * @author jin_peng
 * @version 1.0, 2012/05/23
 */
public interface TimerDao
{

    /**
     * 根据患者内部序列号和就诊日期获取时间轴列表头信息
     * @param itemFlag 标识获取时间轴中哪部分信息，例如：visit代表获取就诊信息
     * @param patientSn 患者内部序列号
     * @param visitDate 就诊日期
     * @param visitTypeCodeInpatient 就诊类型代码（住院）
     * @param rowNumStart 分页开始记录数
     * @param rowNumEnd 分页结束记录数
     * @return 时间轴列表头信息相应对象集合
     */
    @Arguments({ "itemFlag", "patientSn", "visitDate",
            "visitTypeCodeInpatient", "rowNumStart", "rowNumEnd"})
    public List<TimerAndInpatientDto> selectTimerList(String itemFlag,
            String patientSn, String visitDate, String visitTypeCodeInpatient,
            int rowNumStart, int rowNumEnd);

    // $Author :jin_peng
    // $Date : 2012/11/26 10:45$
    // [BUG]0011851 MODIFY BEGIN
    /**
     * 根据相应患者的就诊时的就诊内部序列号获取时间轴列表，具体信息部分 例如：诊断，用药医嘱等
     * @param itemFlag 标识获取时间轴中哪部分信息，例如：visit代表获取就诊信息
     * @param visitSn 就诊内部序列号集合
     * @param displayCount 具体内容部分每Td中内容条数
     * @return 时间轴列表具体信息对象集合
     */
    @Arguments({ "itemFlag", "visitSnList", "displayCount", "userSn" })
    public List<TimerAndInpatientDto> selectTimerList(String itemFlag,
            List<BigDecimal> visitSnList, int displayCount, String userSn);

    // [BUG]0011851 MODIFY END

    // $Author :jin_peng
    // $Date : 2012/11/05 13:54$
    // [BUG]0011016 MODIFY BEGIN
    /**
     * 查询患者所有就诊记录条数
     * @param patientSn 患者内部序列号
     * @param visitDate 住院日期
     * @param visitTypeCodeInpatient 住院类型编码
     * @param isValidation 是否需要验证记录是否存在
     * @return 未删除就诊记录总条数
     */
    @Arguments({ "patientSn", "visitDate", "visitTypeCodeInpatient",
            "isValidation" })
    public int selectTotalCnt(String patientSn, String visitDate,
            String visitTypeCodeInpatient, boolean isValidation);
    // [BUG]0011016 MODIFY END
}
