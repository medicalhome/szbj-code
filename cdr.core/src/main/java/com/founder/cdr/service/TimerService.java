package com.founder.cdr.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.founder.cdr.dto.AccessControlDto;
import com.founder.cdr.dto.TimerAndInpatientDto;

/**
 * 用于时间轴视图操作
 * @author jin_peng
 * @version 1.0, 2012/05/23
 */
public interface TimerService
{
    /**
     * 根据患者内部序列号和就诊日期获取时间轴列表
     * @param patientSn 患者内部序列号
     * @param visitDate 就诊日期
     * @param rowNumStart 开始记录
     * @param rowNumEnd 结束记录
     * @param accessControlDto 访问控制DTO
     * @param userSn 用户sn，用于根据sn排除hide_drug表信息
     * @return 时间轴列表相应对象集合
     */
    @Transactional
    public List<List<List<TimerAndInpatientDto>>> selectTimerList(
            String patientSn, String visitDate, int rowNumStart, int rowNumEnd,
            AccessControlDto accessControlDto, Boolean useACLFlag, String userSn);

    /**
     * 查询患者所有就诊记录条数
     * @param patientSn 患者内部序列号
     * @param visitDate 住院日期
     * @return 未删除就诊记录总条数
     */
    @Transactional
    public int selectTotalCnt(String patientSn, String visitDate);

    // $Author :jin_peng
    // $Date : 2012/11/05 13:54$
    // [BUG]0011016 ADD BEGIN
    /**
     * 查询患者所有就诊记录条数
     * @param patientSn 患者内部序列号
     * @param visitDate 住院日期
     * @return 某就诊日期后就诊记录总条数
     */
    @Transactional
    public int selectValidationTotalCnt(String patientSn, String visitDate);

    // [BUG]0011016 ADD END

    /**
     * 获取页面显示的检验项目及存在异常的检验具体结果信息
     * @param lrciList 检验大项对象集合
     * @return 检验项目及存在异常的检验具体结果信息
     */
    @Transactional
    public List<TimerAndInpatientDto> getTimerLabDtoList(
            List<TimerAndInpatientDto> lrciList);
}
