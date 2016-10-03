package com.yly.cdr.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.founder.fasf.core.util.daohelper.entity.EntityDao;
import com.founder.fasf.web.paging.PagingContext;
import com.yly.cdr.core.Constants;
import com.yly.cdr.dao.BloodDeliveryOrderDao;
import com.yly.cdr.dao.BloodRequestDao;
import com.yly.cdr.dao.VisitDao;
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
import com.yly.cdr.service.CommonService;
import com.yly.cdr.service.VisitService;

@Component
public class VisitServiceImpl implements VisitService
{
    @Autowired
    private VisitDao visitDao;

    @Autowired
    private BloodDeliveryOrderDao bloodDao;

    @Autowired
    private BloodRequestDao bloodRequestDao;

    @Autowired
    private EntityDao entityDao;

    @Autowired
    private CommonService commonService;

    // $Author:wu_jianfeng
    // $Date : 2012/10/25 14:10
    // $[BUG]0010778 MODIFY BEGIN
    @Transactional
    public VisitListDto[] selectVisitList(
            VisitListSearchPra visitListSearchPra, PagingContext pagingContext)
    {
        VisitListDto[] visitResult = visitDao.selectVisitList(
                visitListSearchPra, pagingContext);
        return visitResult;
    }

    // $[BUG]0010778 MODIFY END

    // 普通搜索
    @Transactional
    public VisitListDto[] selectVisitList(String patientSn,
            String visitDateFromStr, String visitDateToStr, String visitType,
            String visitDept, String dischargeDateFromStr,
            String dischargeDateToStr, String visitDoctorName,
            String registrationClass, PagingContext pagingContext)
    {
        VisitListDto[] visitResult = visitDao.selectVisitListSearch(patientSn,
                visitDateFromStr, visitDateToStr, visitType, visitDept,
                dischargeDateFromStr, dischargeDateToStr, visitDoctorName,
                registrationClass, pagingContext);
        return visitResult;
    }

    /**
     * 就诊详细
     */
    @Transactional
    public VisitDetailDto selectVisitDetail(String visitSn)
    {
        VisitDetailDto visitDetail = visitDao.selectVisitDetail(visitSn);
        return visitDetail;
    }

    /**
     * 获取就诊信息(根据就诊内部序列号)
     * @param visitSn 就诊内部序列号
     * @return 就诊对象
     */
    @Transactional
    public MedicalVisit selectMedicalVisitById(String visitSn)
    {
        List<Object> medicalVisits = null;
        if(!StringUtils.isEmpty(visitSn)){
            Map<String, Object> condition = new HashMap<String, Object>();
            condition.put("visitSn", visitSn);
            condition.put("deleteFlag", Constants.NO_DELETE_FLAG);
            medicalVisits = entityDao.selectByCondition(
                    MedicalVisit.class, condition);            
        }
        return medicalVisits != null && medicalVisits.size() > 0 ? (MedicalVisit) medicalVisits.get(0)
                : null;
    }

    /**
     * 就诊-转科转区转床
     */
    @Transactional
    @SuppressWarnings("unchecked")
    public List<TransferHistory> selectTransferHistory(String patientSn,
            String visitSn)
    {
    	// Author: chang_xuewen
        // Date: 2013/10/22 10:20$
        // [BUG]0038313 MODIFY BEGIN
        List result = new ArrayList<TransferHistory>();
        return result = visitDao.selectTransferHistory(patientSn, visitSn);
        // [BUG]0038313 MODIFY END
    }

    /**
     * 用血申请单
     */
    @Transactional
    @SuppressWarnings("unchecked")
    public BloodRequestDto[] selectBoodRequest(String patientSn, String visitSn)
    {
        BloodRequestDto[] bloodReResult = bloodRequestDao.selectBloodReResult(
                visitSn, patientSn);
        return bloodReResult;
    }

    /**
     * 取血单
     */
    @Transactional
    public BloodDeliveryOrderDto[] selectBoodDeOrder(String visitSn,
            String requestSn)
    {
        BloodDeliveryOrderDto[] boodDeOrResult = bloodDao.selectBoodDeOrder(
                visitSn, requestSn);
        return boodDeOrResult;
    }

    /**
     * V05-001:临床信息集成视图
     * 根据患者序列号，得到患者最后的就诊记录
     * @param patientSn 患者序列号
     * @return 患者最后的就诊记录
     */
    @Transactional
    public MedicalVisit getLastMedicalVisit(String patientSn)
    {
        MedicalVisit visit = visitDao.getLastMedicalVisit(patientSn);
        return visit;
    }

    /**
     * V05-001:临床信息集成视图
     * 根据患者序列号，得到患者的就诊科室信息
     * @param patientSn 患者序列号
     * @return 患者的就诊科室记录
     */
    @Transactional
    public List<String> getMedicalVisitDepartments(String patientSn,
            PagingContext pagingContext)

    {
        List<String> lst = visitDao.getMedicalVisitDepartments(patientSn,
                pagingContext);
        return lst;
    }

    // [FUN]A01-002挂号信息 ADD BEGIN

    /**
     * 获取就诊信息(根据多个查询条件)
     * @param map 查询条件
     * @return 就诊对象
     */
    @Transactional
    public MedicalVisit selectVisitByCondition(Map<String, Object> map)
    {
        List<Object> visitList = entityDao.selectByCondition(
                MedicalVisit.class, map);

        if (visitList != null && visitList.size() > 0)
            return (MedicalVisit) visitList.get(0);
        else
            return null;
    }

    @Override
    @Transactional
    public List<MedicalVisit> mediVisit(String patientDomain,
            String patientLid, Integer visitTimes)
    {
        Map<String, Object> condition = new HashMap<String, Object>();
        condition.put("patientDomain", patientDomain);
        condition.put("patientLid", patientLid);
        condition.put("visitTimes", visitTimes);
        List visitResult = new ArrayList<MedicalVisit>();
        visitResult = entityDao.selectByCondition(MedicalVisit.class, condition);
        return visitResult;
    }

    /**
     * 在没有患者存在的情况下，保存就诊信息，患者保存到临时患者表
     * @param visit 就诊
     * @param patient 患者
     * @param tempPatient 临时患者
     */
    @Override
    @Transactional
    public void saveWhenNoPatientExists(MedicalVisit visit, Patient patient,
            PatientTemp tempPatient)
    {
        BigDecimal patientSn = commonService.getSequence("SEQ_PATIENT");
        patient.setPatientSn(patientSn);
        tempPatient.setPatientSn(patientSn);
        visit.setPatientSn(patientSn);

        commonService.save(patient);
        commonService.save(tempPatient);
        commonService.save(visit);
    }

    @Override
    @Transactional
    public VisitListDto[] selectVisitList(String patientSn,
            VisitListSearchPra visitListSearchPra, PagingContext pagingContext)
    {
        VisitListDto[] visitResult = visitDao.selectVisitListSearchOb(
                patientSn, visitListSearchPra, pagingContext);

        return visitResult;
    }

    /**
     * 出院召回时更新关联就诊记录，并新增召回记录
     * @param medicalVisit 就诊
     * @param withdrawRecord 召回记录
     */
    @Override
    @Transactional
    public void saveWithdrawMedicalVisit(MedicalVisit medicalVisit,
            WithdrawRecord withdrawRecord)
    {
        // 更新相关就诊信息的就诊状态和住院状态
        commonService.updatePartially(medicalVisit, "visitStatusCode",
                "visitStatusName", "dischargeStatusCode","dischargeDate",
                "dischargeStatusName", "updateTime", "dischargeWardId",
                "dischargeWardName", "dischargeBedNo", "updateby");
        // 保存召回信息
        commonService.save(withdrawRecord);

    }

    @Override
    @Transactional
    public WithdrawRecordDto[] selectWithdrawRecord(String visitSn)
    {
        return visitDao.selectWithdrawRecord(visitSn);
    }

    // $Author:wu_jianfeng
    // $Date : 2012/09/28 14:10
    // $[BUG]0010082 ADD BEGIN
    // 门诊视图
    // $Author:wu_jianfeng
    // $Date : 2012/10/25 14:10
    // $[BUG]0010778 MODIFY BEGIN
    @Override
    @Transactional
    public List<VisitDateDto> getOutpatientVisitDateList(
            VisitListSearchPra visitListSearchPra)
    {
        List<VisitDateDto> visitDateDtos = visitDao.selectOutpatientVisitDates(visitListSearchPra);
        List<VisitDateDto> temp = new ArrayList<VisitDateDto>();
        List<String> visitDates = new ArrayList<String>();

        for (VisitDateDto visitDateDto : visitDateDtos)
        {
            visitDates.add(visitDateDto.getVisitDate());
        }

        for (VisitDateDto visitDateDto : visitDateDtos)
        {
            String visitDateFullStr = visitDateDto.getVisitDate();

            if (visitDateFullStr.indexOf("(1)") != -1)
            {
                String visitDateStr = visitDateFullStr.substring(0,
                        visitDateFullStr.length() - 3);
                String compareStr = visitDateStr + "(2)";
                if (!visitDates.contains(compareStr))
                    visitDateDto.setVisitDate(visitDateStr);
                else
                    visitDateDto.setVisitDate(visitDateFullStr);
                temp.add(visitDateDto);
            }
            else
                temp.add(visitDateDto);
        }

        return temp;
    }

    // $[BUG]0010082 ADD END
    // $[BUG]0010778 MODIFY END

    // $Author:wu_jianfeng
    // $Date : 2012/10/8 14:10
    // $[BUG]0010107 ADD BEGIN
    // 就诊索引视图
    @Override
    @Transactional
    public List<VisitIndexListDto> selectVisitIndexList(String patientSn,
            VisitIndexListSearchPra visitListSearchPra)
    {
        List<VisitIndexListDto> visitResult = visitDao.selectVisitIndexList(
                patientSn, visitListSearchPra);
        return visitResult;
    }

    // $[BUG]0010107 ADD END

    // $Author:wu_jianfeng
    // $Date : 2012/10/8 14:10
    // $[BUG]0010244 ADD BEGIN

    @Override
    @Transactional
    public List<MedicalVisit> selectLastMedicalVisits(
            LastVisitSearchPra searchPra)
    {
        List<MedicalVisit> visits = visitDao.selectLastMedicalVisits(searchPra);
        return visits;
    }

    // $[BUG]0010244 ADD END

    // $Author:wei_peng
    // $Date:2012/10/18 10:42
    // $[BUG]0010276 ADD BEGIN
    /**
     * 用户反馈意见查询
     * @param userId 用户ID
     * @return 用户反馈意见集合
     */
    @Override
    @Transactional
    public List<UserFeedback> selectUserFeedbacks(String userId,
            String parentFeedbackSn)
    {
        return visitDao.selectUserFeedbacks(userId, parentFeedbackSn);
    }

    /**
     * 保存用户的反馈意见
     * @param userFeedback
     */
    @Override
    @Transactional
    public BigDecimal saveUserFeedback(UserFeedback userFeedback)
    {
        BigDecimal parentFeedbackSn = userFeedback.getParentFeedbackSn();
        entityDao.insert(userFeedback);
        BigDecimal replyCount = userFeedback.getReplyCount();
        if (parentFeedbackSn != null)
        {
            Map<String, Object> condition = new HashMap<String, Object>();
            condition.put("deleteFlag", Constants.NO_DELETE_FLAG);
            condition.put("feedbackSn", parentFeedbackSn);
            List<Object> userFeedbacks = entityDao.selectByCondition(
                    UserFeedback.class, condition);
            if (userFeedbacks != null && userFeedbacks.size() > 0)
            {
                UserFeedback parentUserFeedback = (UserFeedback) userFeedbacks.get(0);
                parentUserFeedback.setReplyCount(parentUserFeedback.getReplyCount().add(
                        new BigDecimal("1")));
                entityDao.update(parentUserFeedback);
                replyCount = parentUserFeedback.getReplyCount();
            }
        }
        return replyCount;
    }

    // $[BUG]0010276 ADD END

    // $Author:wu_jianfeng
    // $Date : 2012/11/15 14:10
    // $[BUG]0011399 ADD BEGIN
    @Override
    @Transactional
    public List<MedicalVisit> getInpatientVisitDoctors(List<String> visitSns)
    {
        List<MedicalVisit> visits = visitDao.selectInpatientVisitDoctors(visitSns);
        return visits;
    }

    // $[BUG]0011399 ADD END

    // $Author:wu_jianfeng
    // $Date : 2012/11/20 14:10
    // $[BUG]0011694 ADD BEGIN
    @Transactional
    public List<KeyValueDto> getVisitDepts(String patientSn,
            List visitTypeInpatientCode, String sysdate)
    {
        List<KeyValueDto> visitDepts = visitDao.selectVisitDepts(patientSn,
                visitTypeInpatientCode, sysdate);
        return visitDepts;
    }

    // $[BUG]0011694 ADD END

    // Author:jia_yanqing
    // Date : 2012/12/25 16:21
    // [BUG]0012701 ADD BEGIN
    @Transactional
    public int getOutCount(String patientSn, String sysDate,String visitTypeCode)
    {
        int outCount = visitDao.getOutPatientCount(patientSn, sysDate, visitTypeCode);
        return outCount;
    }
    
    @Transactional
    public int getInCount(String patientSn,String visitTypeCode)
    {
        int inCount = visitDao.getInPatientCount(patientSn, visitTypeCode);
        return inCount;
    }

    // [BUG]0012701 ADD END

    // Author: jia_yanqing
    // Date: 2013/3/6 14:11$
    // [BUG]0014327 MODIFY BEGIN
    @Override
    @Transactional
    public int selectMinVisitTimes(BigDecimal patientSn, String orgCode)
    {
        return visitDao.selectMinVisitTimes(patientSn, orgCode);
    }
    // [BUG]0014327 MODIFY END
    
    /**
     * 获取该次就诊对应的转区转床记录
     * @param visitSn 就诊内部序列号
     * @return 就诊对应的转区转床记录集合
     */
    @Override
    @Transactional
    public List<Object> selectTransferHistoryByVisitSn(BigDecimal visitSn)
    {
        Map<String,Object> condition = new HashMap<String,Object>();
        condition.put("visitSn", visitSn);
        condition.put("deleteFlag", Constants.NO_DELETE_FLAG);
        return entityDao.selectByCondition(TransferHistory.class, condition);
    }
    
}
