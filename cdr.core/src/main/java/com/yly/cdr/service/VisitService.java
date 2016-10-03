/**
 * Copryright
 */
package com.yly.cdr.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Transactional;

import com.founder.fasf.web.paging.PagingContext;
import com.yly.cdr.dto.BloodDeliveryOrderDto;
import com.yly.cdr.dto.BloodRequestDto;
import com.yly.cdr.dto.KeyValueDto;
import com.yly.cdr.dto.VisitDateDto;
import com.yly.cdr.dto.VisitDetailDto;
import com.yly.cdr.dto.VisitIndexListDto;
import com.yly.cdr.dto.VisitListDto;
import com.yly.cdr.dto.exam.WithdrawRecordDto;
import com.yly.cdr.dto.visit.LastVisitSearchPra;
import com.yly.cdr.dto.visit.VisitIndexListSearchPra;
import com.yly.cdr.dto.visit.VisitListSearchPra;
import com.yly.cdr.entity.MedicalVisit;
import com.yly.cdr.entity.Patient;
import com.yly.cdr.entity.PatientTemp;
import com.yly.cdr.entity.TransferHistory;
import com.yly.cdr.entity.UserFeedback;
import com.yly.cdr.entity.WithdrawRecord;

/**
 * 
 * @author guo_hongyan
 *
 */
public interface VisitService
{
    /**
     * 
     * [FUN]V05-101就诊列表
     * @version 1.0, 2012/03/06  
     * @author 郭红燕
     * @since 1.0
     * 
     */
    // $Author:wu_jianfeng
    // $Date : 2012/10/25 14:10
    // $[BUG]0010778 MODIFY BEGIN
    @Transactional
    VisitListDto[] selectVisitList(VisitListSearchPra visitListSearchPra,
            PagingContext pagingContext);

    // $[BUG]0010778 MODIFY END

    @Transactional
    VisitListDto[] selectVisitList(String patientSn,
            VisitListSearchPra visitListSearchPra, PagingContext pagingContext);

    @Transactional
    VisitDetailDto selectVisitDetail(String visitSn);
    
    @Transactional
    MedicalVisit selectMedicalVisitById(String visitSn);

    @Transactional
    List<TransferHistory> selectTransferHistory(String patientSn, String visitSn);

    @Transactional
    BloodRequestDto[] selectBoodRequest(String patientSn, String visitSn);

    @Transactional
    BloodDeliveryOrderDto[] selectBoodDeOrder(String visitSn, String requestSn);

    // $Author : wu_jianfeng
    // $Date : 2012/03/12 9:56$  
    // [FUN]V05-001:临床信息集成视图 ADD BEGIN

    /**
     * 根据患者序列号，得到患者最后的就诊记录
     * @param patientSn 患者序列号
     * @return 患者最后的就诊记录
     */
    MedicalVisit getLastMedicalVisit(String patientSn);

    /**
     * 根据患者序列号，得到患者的就诊科室信息
     * @param patientSn 患者序列号
     * @return 患者的就诊科室记录
     */
    List<String> getMedicalVisitDepartments(String patientSn,
            PagingContext pagingContext);

    // [FUN]V05-001:临床信息集成视图 ADD END

    // [FUN]A01-002挂号信息 ADD BEGIN
    /**
     * 获取就诊信息(根据多个查询条件)
     * @param map 查询条件
     * @return 就诊对象
     */
    @Transactional
    public MedicalVisit selectVisitByCondition(Map<String, Object> map);

    @Transactional
    public List<MedicalVisit> mediVisit(String patientDomain,
            String patientLid, Integer visitTimes);

    /**
     * 在没有患者存在的情况下，保存就诊信息，患者保存到临时患者表
     * @param visit 就诊
     * @param patient 患者
     * @param tempPatient 临时患者
     */
    @Transactional
    public void saveWhenNoPatientExists(MedicalVisit visit, Patient patient,
            PatientTemp tempPatient);

    /**
     * 出院召回时更新关联就诊记录，并新增召回记录
     * @param medicalVisit 就诊
     * @param withdrawRecord 召回记录
     */
    @Transactional
    public void saveWithdrawMedicalVisit(MedicalVisit medicalVisit,
            WithdrawRecord withdrawRecord);

    WithdrawRecordDto[] selectWithdrawRecord(String visitSn);

    // $Author:wu_jianfeng
    // $Date : 2012/09/28 14:10
    // $[BUG]0010082 ADD BEGIN
    // 门诊视图
    // $Author:wu_jianfeng
    // $Date : 2012/10/25 14:10
    // $[BUG]0010778 MODIFY BEGIN
    public List<VisitDateDto> getOutpatientVisitDateList(
            VisitListSearchPra visitListSearchPra);

    // $[BUG]0010778 MODIFY END
    // $[BUG]0010082 ADD END

    // $Author:wu_jianfeng
    // $Date : 2012/10/8 14:10
    // $[BUG]0010107 ADD BEGIN
    // 就诊索引视图
    @Transactional
    List<VisitIndexListDto> selectVisitIndexList(String patientSn,
            VisitIndexListSearchPra visitListSearchPra);

    // $[BUG]0010107 ADD END

    // $Author:wu_jianfeng
    // $Date : 2012/10/8 14:10
    // $[BUG]0010244 ADD BEGIN
    @Transactional
    public List<MedicalVisit> selectLastMedicalVisits(
            LastVisitSearchPra searchPra);

    // $[BUG]0010244 ADD END

    // $Author:wei_peng
    // $Date:2012/10/18 10:42
    // $[BUG]0010276 ADD BEGIN
    /**
     * 用户反馈意见查询
     * @param userId 用户ID
     * @return 用户反馈意见集合
     */
    @Transactional
    public List<UserFeedback> selectUserFeedbacks(String userId,
            String parentFeedbackSn);

    /**
     * 保存用户的反馈意见
     * @param userFeedback
     */
    @Transactional
    public BigDecimal saveUserFeedback(UserFeedback userFeedback);

    // $[BUG]0010276 ADD END

    // $Author:wu_jianfeng
    // $Date : 2012/11/15 14:10
    // $[BUG]0011399 ADD BEGIN
    public List<MedicalVisit> getInpatientVisitDoctors(List<String> visitSns);

    // $[BUG]0011399 ADD END

    // $Author:wu_jianfeng
    // $Date : 2012/11/20 14:10
    // $[BUG]0011694 ADD BEGIN
    public List<KeyValueDto> getVisitDepts(String patientSn,
            List visitTypeInpatientCode, String sysDate);
    // $[BUG]0011694 ADD END
    
    // Author:jia_yanqing
    // Date : 2012/12/25 16:21
    // [BUG]0012701 ADD BEGIN
    public int getOutCount(String patientSn,String sysDate,String visitTypeCode);
    public int getInCount(String patientSn,String visitTypeCode);
    // [BUG]0012701 ADD END
    
    // Author: jia_yanqing
    // Date: 2013/3/6 14:11$
    // [BUG]0014327 MODIFY BEGIN
    /**
     * 获取该患者的最小住院次数
     * @param patientSn
     * @return 最小住院次数
     */
    public int selectMinVisitTimes(BigDecimal patientSn, String orgCode);
    // [BUG]0014327 MODIFY END
    
    /**
     * 获取该次就诊对应的转区转床记录
     * @param visitSn 就诊内部序列号
     * @return 就诊对应的转区转床记录集合
     */
    public List<Object> selectTransferHistoryByVisitSn(BigDecimal visitSn); 
}
