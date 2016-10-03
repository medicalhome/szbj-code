package com.yly.cdr.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.yly.cdr.dto.AccessControlDto;
import com.yly.cdr.dto.TimerAndInpatientDto;

/**
 * 用于住院视图操作
 * @author jin_peng
 * @version 1.0, 2012/05/29
 */
public interface InpatientService
{
    // $Author :jin_peng
    // $Date : 2012/09/28 13:48$
    // [BUG]0010132 MODIFY BEGIN
    /**
     * 根据患者内部序列号和某次就诊的某个住院日期获取住院视图列表
     * @param patientSn 患者内部序列号
     * @param inpatientType 住院类型
     * @param inpatientDate 某次就诊的某个住院日期 
     * @param visitSnChange 记录就诊内部序列号，为判断在住院日期内就诊的变化
     * @param rowNumStart 分页起始记录
     * @param rowNumEnd 分页结束记录
     * @param accessControlDto 权限控制类
     * @param useACLFlag 权限控制开关
     * @param pagingKindFlag 向前向后分页标识
     * @param visitRowsCnt 每页显示条数
     * @param userSn 用户sn，用于根据用户sn排除hide_drug表信息
     * @param visitLine 
     * @return 住院视图列表相应对象集合
     */
    @Transactional
    public List<List<List<TimerAndInpatientDto>>> selectInpatientList(
            String patientSn, String inpatientType, String inpatientDate,
            String visitSnChange, int rowNumStart, int rowNumEnd,
            AccessControlDto accessControlDto, Boolean useACLFlag,
            String pagingKindFlag, int visitRowsCnt, String userSn, String orgCode, List<TimerAndInpatientDto> visitLine);

    // $Author :jin_peng
    // $Date : 2012/11/06 13:48$
    // [BUG]0011026 MODIFY BEGIN
    /**
     * 查寻住院视图总记录数
     * @param patientSn 患者内部序列号
     * @param inpatientType 住院类型
     * @param inpatientStartDate 某次住院的住院开始日期
     * @param inpatientEndDate 某次住院的住院结束时间
     * @param dischargeDate 出院日期
     * @param exitInpatient 退院标识
     * @return 住院视图总记录数
     */
    @Transactional
    public int selectTotalCnt(String patientSn, String inpatientType,
            String inpatientStartDate, String inpatientEndDate,
            String dischargeDate, String exitInpatient, String orgCode);

    /**
     * 查找所有的住院视图起始记录号
     * @param patientSn 患者内部序列号
     * @param inpatientType 住院类型
     * @param inpatientStartDate 某次住院的住院开始日期
     * @param inpatientEndDate 某次住院的住院结束时间
     * @param visitSn 住院次数
     * @param exitInpatient 退院标识
     * @return 记录开始显示号
     */
    @Transactional
    public int selectStartNumber(String patientSn, String inpatientType,
            String inpatientStartDate, String inpatientEndDate, String visitSn,
            String exitInpatient, String orgCode);

    /**
     * 查寻住院视图住院次数
     * @param patientSn 患者内部序列号
     * @param inpatientType 住院类型
     * @param exitInpatient 退院标识
     * @return 住院视图住院次数
     */
    @Transactional
    public List<TimerAndInpatientDto> selectVisitTimes(String patientSn,
            String inpatientType, String exitInpatient, String orgCode);

    // [BUG]0010132 MODIFY END
    // [BUG]0011026 MODIFY END

    /**
     * 获取页面显示的检验项目及存在异常的检验具体结果信息
     * @param lrciList 检验大项对象集合
     * @return 检验项目及存在异常的检验具体结果信息
     */
    @Transactional
    public List<TimerAndInpatientDto> getInpatientLabDtoList(
            List<TimerAndInpatientDto> lrciList);

    // $Author:jin_peng
    // $Date:2012/12/25 16:49
    // $[BUG]0012698 ADD BEGIN
    /**
     * 查询住院视图某天具体诊断内容
     * @param visitSn 就诊内部序列号
     * @param inpatientDate 住院日期
     * @param userSn 用户sn，用于根据用户sn排除hide_drug表信息
     * @return 住院视图某天具体诊断内容
     */
    public List<TimerAndInpatientDto> selectSpecificallyDiagnosisList(
            BigDecimal visitSn, String inpatientDate, String userSn);
    
    /**
     * 查询住院视图某天具体长期药物医嘱内容
     * @param visitSn 就诊内部序列号
     * @param inpatientDate 住院日期
     * @param userSn 用户sn，用于根据用户sn排除hide_drug表信息
     * @return 住院视图某天具体长期药物医嘱内容
     */
    public List<TimerAndInpatientDto> selectSpecificallyLongDrug(
            BigDecimal visitSn, String inpatientDate, String userSn);
    
    /**
     * 查询住院视图某天具体临时药物医嘱内容
     * @param visitSn 就诊内部序列号
     * @param inpatientDate 住院日期
     * @param userSn 用户sn，用于根据用户sn排除hide_drug表信息
     * @return 住院视图某天具体临时药物医嘱内容
     */
    public List<TimerAndInpatientDto> selectSpecificallyTempDrug(
            BigDecimal visitSn, String inpatientDate, String userSn);
    
    /**
     * 查询住院视图某天具体检查内容
     * @param visitSn 就诊内部序列号
     * @param inpatientDate 住院日期
     * @param userSn 用户sn，用于根据用户sn排除hide_drug表信息
     * @return 住院视图某天具体检查内容
     */
    public List<TimerAndInpatientDto> selectSpecificallyExam(
            BigDecimal visitSn, String inpatientDate, String userSn);
    
    /**
     * 查询住院视图某天具体检验内容
     * @param visitSn 就诊内部序列号
     * @param inpatientDate 住院日期
     * @param userSn 用户sn，用于根据用户sn排除hide_drug表信息
     * @return 住院视图某天具体检验内容
     */
    public List<TimerAndInpatientDto> selectSpecificallyLab(
            BigDecimal visitSn, String inpatientDate, String userSn);
    
    /**
     * 查询住院视图某天具体手术内容
     * @param visitSn 就诊内部序列号
     * @param inpatientDate 住院日期
     * @param userSn 用户sn，用于根据用户sn排除hide_drug表信息
     * @return 住院视图某天具体手术内容
     */
    public List<TimerAndInpatientDto> selectSpecificallyProcedure(
            BigDecimal visitSn, String inpatientDate, String userSn);
    
    // $Author :chang_xuewen
    // $Date : 2013/10/24 11:00$
    // [BUG]0038443 ADD BEGIN
    /**
     * 查询住院视图某天具体手术相关病历文书内容
     * @param visitSn 就诊内部序列号
     * @param inpatientDate 住院日期
     * @param userSn 用户sn，用于根据用户sn排除hide_drug表信息
     * @return 住院视图某天具体手术内容
     */
    public List<TimerAndInpatientDto> selectSpecificallyProceduredoc(
			BigDecimal visitSn, String inpatientDate, String userSn);
    // [BUG]0038443 ADD BEGIN
    
    /**
     * 查询住院视图某天具体病例文书内容
     * @param visitSn 就诊内部序列号
     * @param inpatientDate 住院日期
     * @param userSn 用户sn，用于根据用户sn排除hide_drug表信息
     * @return 住院视图某天具体病例文书内容
     */
    public List<TimerAndInpatientDto> selectSpecificallyDocument(
            BigDecimal visitSn, String inpatientDate, String userSn);
    
    /**
     * 查询住院视图某天具体非药物医嘱内容
     * @param visitSn 就诊内部序列号
     * @param inpatientDate 住院日期
     * @param userSn 用户sn，用于根据用户sn排除hide_drug表信息
     * @return 住院视图某天具体非药物医嘱内容
     */
    public List<TimerAndInpatientDto> selectSpecificallyNoneDrug(
            BigDecimal visitSn, String inpatientDate, String userSn);

    // $[BUG]0012698 ADD END

}
