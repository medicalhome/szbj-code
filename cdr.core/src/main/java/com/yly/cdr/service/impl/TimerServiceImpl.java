package com.yly.cdr.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.yly.cdr.core.Constants;
import com.yly.cdr.dao.TimerAndInpatientDao;
import com.yly.cdr.dao.TimerDao;
import com.yly.cdr.dto.AccessControlDto;
import com.yly.cdr.dto.TimerAndInpatientDto;
import com.yly.cdr.service.TimerService;

/**
 * 用于时间轴视图操作
 * @author jin_peng
 * @version 1.0, 2012/05/23
 */
@Component
public class TimerServiceImpl implements TimerService
{
    @Autowired
    private TimerDao timerDao;

    @Autowired
    private TimerAndInpatientDao timerAndInpatientDao;

    /**
     * 根据患者内部序列号和就诊日期获取时间轴列表
     * @param patientSn 患者内部序列号
     * @param visitDate 就诊日期 
     * @return 时间轴列表相应对象集合
     */
    @Transactional
    public List<List<List<TimerAndInpatientDto>>> selectTimerList(
            String patientSn, String visitDate, int rowNumStart, int rowNumEnd,
            AccessControlDto accessControlDto, Boolean useACLFlag, String userSn)
    {
        // 声明时间轴列表所有信息对象集合
        List<List<List<TimerAndInpatientDto>>> timerAllList = new ArrayList<List<List<TimerAndInpatientDto>>>();

        // 声明时间轴列表中具体信息对象集合
        List<List<TimerAndInpatientDto>> medicalList = new ArrayList<List<TimerAndInpatientDto>>();
        List<List<TimerAndInpatientDto>> diagnosisList = new ArrayList<List<TimerAndInpatientDto>>();
        List<List<TimerAndInpatientDto>> drugOrderList = new ArrayList<List<TimerAndInpatientDto>>();
        List<List<TimerAndInpatientDto>> examinationList = new ArrayList<List<TimerAndInpatientDto>>();
        List<List<TimerAndInpatientDto>> labList = new ArrayList<List<TimerAndInpatientDto>>();
        List<List<TimerAndInpatientDto>> procedureList = new ArrayList<List<TimerAndInpatientDto>>();
        List<List<TimerAndInpatientDto>> documenationList = new ArrayList<List<TimerAndInpatientDto>>();
        List<List<TimerAndInpatientDto>> noDrugOrderList = new ArrayList<List<TimerAndInpatientDto>>();

        // $Author :jin_peng
        // $Date : 2012/11/26 10:45$
        // [BUG]0011851 MODIFY BEGIN
        // 一次性获取各业务信息对象集合
        List<TimerAndInpatientDto> diagnosisAllList = new ArrayList<TimerAndInpatientDto>();
        List<TimerAndInpatientDto> drugOrderAllList = new ArrayList<TimerAndInpatientDto>();
        List<TimerAndInpatientDto> examinationAllList = new ArrayList<TimerAndInpatientDto>();
        List<TimerAndInpatientDto> labAllList = new ArrayList<TimerAndInpatientDto>();
        List<TimerAndInpatientDto> procedureAllList = new ArrayList<TimerAndInpatientDto>();
        List<TimerAndInpatientDto> documenationAllList = new ArrayList<TimerAndInpatientDto>();
        List<TimerAndInpatientDto> noDrugOrderAllList = new ArrayList<TimerAndInpatientDto>();

        // 相应就诊内部序列号集合
        List<BigDecimal> visitSnList = new ArrayList<BigDecimal>();

        // 时间轴页面具体内容每td内容显示条数
        int displayCount = Constants.TIMER_INPATIENT_DISPLAY_TD_COUNT + 1;

        // 根据相应查询条件查询就诊表中该患者历次就诊信息
        List<TimerAndInpatientDto> medTempList = timerDao.selectTimerList(
                TimerAndInpatientDto.TIMER_VISIT, patientSn, visitDate,
                Constants.VISIT_TYPE_CODE_INPATIENT, rowNumStart, rowNumEnd);

        // 声明将查询到的就诊信息正序或倒叙装入的集合
        List<TimerAndInpatientDto> medList = null;

        // 根据该患者历次就诊内部序列号查询相应的患者活动信息，例如：诊断，药物医嘱等
        if (medTempList != null && !medTempList.isEmpty())
        {
            BigDecimal visitSn = null;

            // 如果就诊日期为空则将查询到的就诊信息倒叙装入的集合，否则按正常顺序进行操作
            if (visitDate != null && visitDate.length() != 0)
            {
                // 查询条件不为空时按正序排列
                medList = medTempList;
            }
            else
            {
                // 查询条件为空时按倒叙排列
                medList = new ArrayList<TimerAndInpatientDto>();

                for (int i = medTempList.size() - 1; i >= 0; i--)
                {
                    medList.add(medTempList.get(i));
                }
            }

            // 获取相应就诊内部序列号集合
            for (TimerAndInpatientDto timerDto : medList)
            {
                visitSnList.add(timerDto.getVisitSn());
            }

            // 获取相应就诊条件下的相应诊断信息对象集合
            if ((useACLFlag && accessControlDto.getClinicalInfoAuth01())
                || !useACLFlag)
            {
                diagnosisAllList = timerDao.selectTimerList(
                        TimerAndInpatientDto.DIAGNOSIS, visitSnList,
                        displayCount, userSn);
            }

            // 获取相应就诊条件下的相应药物医嘱信息对象集合
            if ((useACLFlag && accessControlDto.getClinicalInfoAuth02())
                || !useACLFlag)
            {
                drugOrderAllList = timerDao.selectTimerList(
                        TimerAndInpatientDto.DRUG_ORDER, visitSnList,
                        displayCount, userSn);
            }

            // 获取相应就诊条件下的相应检查信息对象集合
            if ((useACLFlag && accessControlDto.getClinicalInfoAuth06())
                || !useACLFlag)
            {
                examinationAllList = timerDao.selectTimerList(
                        TimerAndInpatientDto.EXAMINATION, visitSnList,
                        displayCount, userSn);
            }

            // 获取相应就诊条件下的相应检验信息对象集合
            if ((useACLFlag && accessControlDto.getClinicalInfoAuth05())
                || !useACLFlag)
            {
                labAllList = timerDao.selectTimerList(TimerAndInpatientDto.LAB,
                        visitSnList, displayCount, userSn);
            }

            // 获取相应就诊条件下的相应手术信息对象集合
            if ((useACLFlag && accessControlDto.getClinicalInfoAuth04())
                || !useACLFlag)
            {
                procedureAllList = timerDao.selectTimerList(
                        TimerAndInpatientDto.PROCEDURE, visitSnList,
                        displayCount, userSn);
            }

            // 获取相应就诊条件下的相应病例文书信息对象集合
            if ((useACLFlag && accessControlDto.getClinicalInfoAuth07())
                || !useACLFlag)
            {
                documenationAllList = timerDao.selectTimerList(
                        TimerAndInpatientDto.DOCUMENTATION, visitSnList,
                        displayCount, userSn);
            }

            // 获取相应就诊条件下的相应非药物医嘱信息对象集合
            if ((useACLFlag && accessControlDto.getClinicalInfoAuth03())
                || !useACLFlag)
            {
                noDrugOrderAllList = timerDao.selectTimerList(
                        TimerAndInpatientDto.NO_DRUG_ORDER, visitSnList,
                        displayCount, userSn);
            }

            for (TimerAndInpatientDto timerDto : medList)
            {
                // 获取visitSn
                visitSn = timerDto.getVisitSn();

                if ((useACLFlag && accessControlDto.getClinicalInfoAuth01())
                    || !useACLFlag)
                {
                    // 查询相应诊断信息
                    diagnosisList.add(getEachBusinessList(diagnosisAllList,
                            visitSn));
                }
                if ((useACLFlag && accessControlDto.getClinicalInfoAuth02())
                    || !useACLFlag)
                {
                    // 查询相应用药医嘱信息
                    drugOrderList.add(getEachBusinessList(drugOrderAllList,
                            visitSn));
                }
                if ((useACLFlag && accessControlDto.getClinicalInfoAuth06())
                    || !useACLFlag)
                {
                    // $Author:tong_meng
                    // $Date:2013/11/19 09:02
                    // $[BUG]0039539 MODIFY BEGIN
                    // 查询相应检查信息
                    examinationList.add(getExamDtoList(getEachBusinessList(examinationAllList,
                            visitSn),timerAndInpatientDao));
                    // $[BUG]0039539 MODIFY END
                }
                if ((useACLFlag && accessControlDto.getClinicalInfoAuth05())
                    || !useACLFlag)
                {
                    // 查询相应用检验信息
                    labList.add(getTimerLabDtoList(getEachBusinessList(
                            labAllList, visitSn)));
                }
                if ((useACLFlag && accessControlDto.getClinicalInfoAuth04())
                    || !useACLFlag)
                {
                    // 查询相应手术信息
                    procedureList.add(getEachBusinessList(procedureAllList,
                            visitSn));
                }
                if ((useACLFlag && accessControlDto.getClinicalInfoAuth07())
                    || !useACLFlag)
                {
                    // 查询相应病例文书信息
                    documenationList.add(getEachBusinessList(
                            documenationAllList, visitSn));
                }
                if ((useACLFlag && accessControlDto.getClinicalInfoAuth03())
                    || !useACLFlag)
                {
                    // 查询相应用非用药医嘱信息
                    noDrugOrderList.add(getEachBusinessList(noDrugOrderAllList,
                            visitSn));
                }
            }
        }
        else
        {
            // 如果就诊为空则新创建一个就诊对象集合
            medList = new ArrayList<TimerAndInpatientDto>();
        }

        // [BUG]0011851 MODIFY END

        // 如果就诊对象集合中该对象不足一页显示的则占位补充
        for (int i = 7 - medList.size(); i > 0; i--)
        {
            TimerAndInpatientDto timerDto = new TimerAndInpatientDto();
            timerDto.setExistsFlag(TimerAndInpatientDto.NO_EXISTS_FLAG);// 设置无就诊记录标识
            medList.add(timerDto);

            // 当无就诊记录时需添加初始值
            diagnosisList.add(null);
            drugOrderList.add(null);
            examinationList.add(null);
            labList.add(null);
            procedureList.add(null);
            documenationList.add(null);
            noDrugOrderList.add(null);
        }

        // 装入就诊记录对象集合
        medicalList.add(medList);

        // 将时间轴页面所需所有信息对象装入一个list
        timerAllList.add(medicalList);
        timerAllList.add(diagnosisList);
        timerAllList.add(drugOrderList);
        timerAllList.add(examinationList);
        timerAllList.add(labList);
        timerAllList.add(procedureList);
        timerAllList.add(documenationList);
        timerAllList.add(noDrugOrderList);

        return timerAllList;
    }

    // $Author :jin_peng
    // $Date : 2012/11/26 10:45$
    // [BUG]0011851 ADD BEGIN
    /**
     * 获取给定就诊范围内的各业务对象集合
     * @param businessAllList 各业务相应就诊范围内所有对象集合
     * @param visitSn 给定的某个就诊内部序列号
     * @return 给定就诊范围内的各业务对象集合
     */
    private List<TimerAndInpatientDto> getEachBusinessList(
            List<TimerAndInpatientDto> businessAllList, BigDecimal visitSn)
    {
        // 需装入的按给定就诊内部序列号获取的记录集合
        List<TimerAndInpatientDto> eachBusinessList = null;

        if (businessAllList != null && !businessAllList.isEmpty())
        {
            eachBusinessList = new ArrayList<TimerAndInpatientDto>();

            // 将匹配给定就诊内部序列号的记录装入返回结果结合中
            for (int i = 0; i < businessAllList.size(); i++)
            {
                TimerAndInpatientDto tid = businessAllList.get(i);

                if (visitSn.compareTo(tid.getVisitSn()) == 0)
                {
                    eachBusinessList.add(tid);
                }
            }

            // 将已获取的对象从整个大集合中删除
            businessAllList.removeAll(eachBusinessList);
        }

        return eachBusinessList;
    }

    // [BUG]0011851 ADD END

    // $Author :jin_peng
    // $Date : 2012/11/05 13:54$
    // [BUG]0011016 MODIFY BEGIN
    /**
     * 查找就诊记录的总数
     * @param patientSn 患者内部序列号
     * @param visitDate 住院日期
     * @return 就诊记录的总数
     */
    @Transactional
    public int selectTotalCnt(String patientSn, String visitDate)
    {
        return timerDao.selectTotalCnt(patientSn, visitDate,
                Constants.VISIT_TYPE_CODE_INPATIENT, false);
    }

    // [BUG]0011016 MODIFY END

    /**
     * 获取页面显示的检验项目及存在异常的检验具体结果信息
     * @param lrciList 检验大项对象集合
     * @return 检验项目及存在异常的检验具体结果信息
     */
    @Transactional
    public List<TimerAndInpatientDto> getTimerLabDtoList(
            List<TimerAndInpatientDto> lrciList)
    {
        return new TimerAndInpatientDto().getLabDtoList(lrciList,
                timerAndInpatientDao);
    }

    // $Author:tong_meng
    // $Date:2013/11/19 09:02
    // $[BUG]0039539 MODIFY BEGIN
    private List<TimerAndInpatientDto> getExamDtoList(
            List<TimerAndInpatientDto> eachBusinessList,
            TimerAndInpatientDao timerAndInpatientDao)
    {
        return new TimerAndInpatientDto().getExamDtoList(eachBusinessList,
                timerAndInpatientDao);
   }
    // $[BUG]0039539 MODIFY END

    
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
    public int selectValidationTotalCnt(String patientSn, String visitDate)
    {
        return timerDao.selectTotalCnt(patientSn, visitDate,
                Constants.VISIT_TYPE_CODE_INPATIENT, true);
    }
    // [BUG]0011016 ADD END

}
