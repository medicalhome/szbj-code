package com.yly.cdr.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.yly.cdr.core.Constants;
import com.yly.cdr.dao.InpatientDao;
import com.yly.cdr.dao.TimerAndInpatientDao;
import com.yly.cdr.dto.AccessControlDto;
import com.yly.cdr.dto.TimerAndInpatientDto;
import com.yly.cdr.entity.NursingOperation;
import com.yly.cdr.service.InpatientService;
import com.yly.cdr.util.DateUtils;
import com.yly.cdr.util.StringUtils;

/**
 * 用于住院视图操作
 * @author jin_peng 
 * @version 1.0, 2012/05/29
 */
@Component
public class InpatientServiceImpl implements InpatientService
{
    private static Logger logger = LoggerFactory.getLogger(InpatientServiceImpl.class);

    @Autowired
    private InpatientDao inpatientDao;

    @Autowired
    private TimerAndInpatientDao timerAndInpatientDao;

    // 需为数字的生命体征项目标识（如体温、血压）
    private static final String NUM = "1";

    // 不需为数字的生命体征项目标识（呼吸，尿量）
    private static final String NO_NUM = "0";

    // $Author :jin_peng
    // $Date : 2012/09/27 10:48$
    // [BUG]0010132 MODIFY BEGIN
    /**
     * 根据患者内部序列号和某次就诊的某个住院日期获取住院视图列表
     * @param patientSn 患者内部序列号
     * @param inpatientType 住院类型
     * @param inpatientStartDate 某次就诊的某个住院日期
     * @param visitSnChange 判断住院开始时间标识 暂为保留
     * @param rowNumStart 分页起始记录
     * @param rowNumEnd 分页结束记录
     * @param accessControlDto 权限控制类
     * @param useACLFlag 权限控制开关
     * @param pagingKindFlag 分页标识（向前，向后）
     * @param visitRowsCnt 每页显示条数
     * @return 住院视图列表相应对象集合
     */
    @Transactional
    public List<List<List<TimerAndInpatientDto>>> selectInpatientList(
            String patientSn, String inpatientType, String inpatientStartDate,
            String visitSnChange, int rowNumStart, int rowNumEnd,
            AccessControlDto accessControlDto, Boolean useACLFlag,
            String pagingKindFlag, int visitRowsCnt, String userSn, String orgCode, List<TimerAndInpatientDto> visitLine)
    {
        // 声明住院视图列表所有信息对象集合
        List<List<List<TimerAndInpatientDto>>> inpatientAllList = new ArrayList<List<List<TimerAndInpatientDto>>>();

        // 声明住院视图三测单中具体对象集合
        // 把每个三测单图形内容装入list
        List<TimerAndInpatientDto> temList = new ArrayList<TimerAndInpatientDto>();
        List<TimerAndInpatientDto> bpLowList = new ArrayList<TimerAndInpatientDto>();
        List<TimerAndInpatientDto> bpHighList = new ArrayList<TimerAndInpatientDto>();
        List<TimerAndInpatientDto> pulList = new ArrayList<TimerAndInpatientDto>();
        List<TimerAndInpatientDto> breList = new ArrayList<TimerAndInpatientDto>();

        // 将每个三测单图形内容集合装入统一格式
        List<List<TimerAndInpatientDto>> temperatureList = new ArrayList<List<TimerAndInpatientDto>>();
        List<List<TimerAndInpatientDto>> bloodPressureLowList = new ArrayList<List<TimerAndInpatientDto>>();
        List<List<TimerAndInpatientDto>> bloodPressureHighList = new ArrayList<List<TimerAndInpatientDto>>();
        List<List<TimerAndInpatientDto>> pulseList = new ArrayList<List<TimerAndInpatientDto>>();
        List<List<TimerAndInpatientDto>> breatheList = new ArrayList<List<TimerAndInpatientDto>>();

        // 将三测单除图形内容外的内容集合添加到list中
        List<List<TimerAndInpatientDto>> defacateList = new ArrayList<List<TimerAndInpatientDto>>();
        List<List<TimerAndInpatientDto>> inputList = new ArrayList<List<TimerAndInpatientDto>>();
        List<List<TimerAndInpatientDto>> peeList = new ArrayList<List<TimerAndInpatientDto>>();
        List<List<TimerAndInpatientDto>> weightList = new ArrayList<List<TimerAndInpatientDto>>();

        // $Author:jin_peng
        // $Date:2012/12/28 17:01
        // $[BUG]0012992 DELETE BEGIN
        // $Author:wei_peng
        // $Date:2012/9/29 10:16
        // $[BUG]0010038 ADD BEGIN
        // List<List<TimerAndInpatientDto>> painAssessmentList = new
        // ArrayList<List<TimerAndInpatientDto>>();
        // $[BUG]0010038 ADD END

        // $Author:jin_peng
        // $Date:2012/12/28 17:01
        // $[BUG]0012992 ADD BEGIN
        // 疼痛指标（活动）
        // List<List<TimerAndInpatientDto>> painAssessmentActiveList = new
        // ArrayList<List<TimerAndInpatientDto>>();

        // $[BUG]0012992 DELETE END

        // 合并后的疼痛指标
        List<List<TimerAndInpatientDto>> painAssessmentMergedList = new ArrayList<List<TimerAndInpatientDto>>();

        // $[BUG]0012992 ADD END

        // 声明住院视图列表中具体信息对象集合
        List<List<TimerAndInpatientDto>> inpatientList = new ArrayList<List<TimerAndInpatientDto>>();
        List<List<TimerAndInpatientDto>> diagnosisList = new ArrayList<List<TimerAndInpatientDto>>();
        List<List<TimerAndInpatientDto>> drugOrderList = new ArrayList<List<TimerAndInpatientDto>>();
        List<List<TimerAndInpatientDto>> examinationList = new ArrayList<List<TimerAndInpatientDto>>();
        List<List<TimerAndInpatientDto>> labList = new ArrayList<List<TimerAndInpatientDto>>();
        List<List<TimerAndInpatientDto>> procedureList = new ArrayList<List<TimerAndInpatientDto>>();
        // $Author :chang_xuewen
    	// $Date : 2013/10/24 11:00$
    	// [BUG]0038443 ADD BEGIN
        List<List<TimerAndInpatientDto>> proceduredocList = new ArrayList<List<TimerAndInpatientDto>>();
    	// [BUG]0038443 ADD END
        List<List<TimerAndInpatientDto>> documenationList = new ArrayList<List<TimerAndInpatientDto>>();
        List<List<TimerAndInpatientDto>> noDrugOrderList = new ArrayList<List<TimerAndInpatientDto>>();

        // $Author:jin_peng
        // $Date:2012/11/12 15:09
        // $[BUG]0011225 MODIFY BEGIN
        // 获取给定住院日期范围的所有相关业务记录集合
        List<TimerAndInpatientDto> diagnosisAllList = new ArrayList<TimerAndInpatientDto>();
        List<TimerAndInpatientDto> drugAllList = new ArrayList<TimerAndInpatientDto>();
        List<TimerAndInpatientDto> examAllList = new ArrayList<TimerAndInpatientDto>();
        List<TimerAndInpatientDto> labAllList = new ArrayList<TimerAndInpatientDto>();
        List<TimerAndInpatientDto> procedureAllList = new ArrayList<TimerAndInpatientDto>();
        List<TimerAndInpatientDto> proceduredocAllList = new ArrayList<TimerAndInpatientDto>();
        List<TimerAndInpatientDto> documentAllList = new ArrayList<TimerAndInpatientDto>();
        List<TimerAndInpatientDto> noDrugAllList = new ArrayList<TimerAndInpatientDto>();

        // 获取给定住院日期范围的所有相关体格检查记录集合
        List<TimerAndInpatientDto> temAllList = new ArrayList<TimerAndInpatientDto>();
        List<TimerAndInpatientDto> bpLowAllList = new ArrayList<TimerAndInpatientDto>();
        List<TimerAndInpatientDto> bpHighAllList = new ArrayList<TimerAndInpatientDto>();
        List<TimerAndInpatientDto> pulAllList = new ArrayList<TimerAndInpatientDto>();
        List<TimerAndInpatientDto> breAllList = new ArrayList<TimerAndInpatientDto>();
        List<TimerAndInpatientDto> defacateAllList = new ArrayList<TimerAndInpatientDto>();
        List<TimerAndInpatientDto> inputAllList = new ArrayList<TimerAndInpatientDto>();
        List<TimerAndInpatientDto> peeAllList = new ArrayList<TimerAndInpatientDto>();
        List<TimerAndInpatientDto> weightAllList = new ArrayList<TimerAndInpatientDto>();
        List<TimerAndInpatientDto> painAssessmentAllList = new ArrayList<TimerAndInpatientDto>();
        List<TimerAndInpatientDto> painAssessmentActiveAllList = new ArrayList<TimerAndInpatientDto>();

        // $Author :jin_peng
        // $Date : 2012/10/10 18:28$
        // [BUG]0010239 ADD BEGIN
        // 药物长期医嘱
        List<TimerAndInpatientDto> longTermDrugOrder = new ArrayList<TimerAndInpatientDto>();

        // 药物长期医嘱显示集合
        List<List<TimerAndInpatientDto>> longTermDrugOrderList = new ArrayList<List<TimerAndInpatientDto>>();

        // 住院视图页面具体内容每td内容显示条数
        int displayCount = Constants.TIMER_INPATIENT_DISPLAY_TD_COUNT + 1;

        // 每页实际显示条数(向前翻页)
        int actuallyDisplayForwardCount = 0;

        // 每页实际显示条数(向后翻页)
        int actuallyDisplayBackwardCount = 0;

        // 根据相应查询条件查询就诊表中该患者历次住院信息
        List<TimerAndInpatientDto> inpTempList;
		if (visitLine == null) {
			inpTempList = inpatientDao.selectInpatientList(
					TimerAndInpatientDto.INPATIENT_VISIT, patientSn,
					inpatientType, inpatientStartDate, rowNumStart, rowNumEnd,
					Constants.EXIT_INPATIENT, orgCode);
		} else {

	        inpTempList = new ArrayList<TimerAndInpatientDto>();
	        if(!StringUtils.isEmpty(inpatientStartDate)){
	        	for(int i = rowNumStart; i <= rowNumEnd; i++){
	        		inpTempList.add(visitLine.get(i - 1));
	        	}
	        } else {        	
	        	for(int i = rowNumStart; i <= rowNumEnd; i++){
	        		inpTempList.add(visitLine.get(visitLine.size() - i));
	        	}
	        }
		}

        // 声明将查询到的住院信息正序或倒叙装入的集合
        List<TimerAndInpatientDto> inpList = null;

        // 当住院记录存在时进行按次住院分割处理
        if (rowNumStart != 0)
        {
            String inpatientDate = null;

            // 对长期药物医嘱判断每种药物是否是第一次匹配给定住院日期
            List<Integer> longTermAppearedfirst = new ArrayList<Integer>();

            // 实际处理的住院记录集合
            inpList = new ArrayList<TimerAndInpatientDto>();

            // 如果住院开始日期为不为空则查询结果为正序
            if (inpatientStartDate != null && inpatientStartDate.length() != 0)
            {
                // 翻页向前时将查询结果倒序处理，当住院日期与入院时间相等时进行截断处理
                if (Constants.PAGING_FORWARD.equals(pagingKindFlag))
                {
                    for (int i = inpTempList.size() - 1; i >= 0; i--)
                    {
                        TimerAndInpatientDto timerAndInpatientDto = inpTempList.get(i);

                        // 将处理完成的住院记录正序装入实际处理集合
                        inpList.add(0, timerAndInpatientDto);

                        // 将住院日期与入院日期比较，相等时进行截断，截断剩余部分将在下次查询时进行处理
                        if (timerAndInpatientDto.getInpatientDate().compareTo(
                                timerAndInpatientDto.getVisitDate()) == 0)
                        {
                            actuallyDisplayForwardCount = i;

                            break;
                        }
                    }
                }
                else
                {
                    // 翻页向后或点击查询时将查询结果正序处理，当住院日期与出院时间相等时进行截断处理
                    for (int i = 0; i < inpTempList.size(); i++)
                    {
                        TimerAndInpatientDto timerAndInpatientDto = inpTempList.get(i);

                        // 将查询结果倒序装入实际处理集合
                        inpList.add(timerAndInpatientDto);

                        // 将住院日期与出院日期进行比较，相等时进行截断，截断剩余部分将在下次查询时进行处理
                        if (timerAndInpatientDto.getDischargeDate() != null
                            && DateUtils.dateToString(
                                    timerAndInpatientDto.getInpatientDate(),
                                    "yyyy-MM-dd").equals(
                                    DateUtils.dateToString(
                                            timerAndInpatientDto.getDischargeDate(),
                                            "yyyy-MM-dd")))
                        {
                            actuallyDisplayBackwardCount = inpTempList.size()
                                - i - 1;

                            break;
                        }
                    }
                }
            }
            else
            {
                // 如果住院开始日期为为空则查询结果为倒序
                // 当翻页为向后翻页时进行一下处理
                if (Constants.PAGING_BACKWARD.equals(pagingKindFlag))
                {
                    // 将查询结果倒叙处理，并与出院日期比较，如果相等则进行截断处理
                    for (int i = inpTempList.size() - 1; i >= 0; i--)
                    {
                        TimerAndInpatientDto timerAndInpatientDto = inpTempList.get(i);

                        // 将查询结果正序装入实际处理集合
                        inpList.add(timerAndInpatientDto);

                        // 将住院时间与出院时间进行比较，如果相等则进行截断处理，剩余部分将下次查询时继续处理
                        if (timerAndInpatientDto.getDischargeDate() != null
                            && DateUtils.dateToString(
                                    timerAndInpatientDto.getInpatientDate(),
                                    "yyyy-MM-dd").equals(
                                    DateUtils.dateToString(
                                            timerAndInpatientDto.getDischargeDate(),
                                            "yyyy-MM-dd")))
                        {
                            actuallyDisplayBackwardCount = i;

                            break;
                        }
                    }
                }
                else
                {
                    // 当翻页为向前或点击查询时将查询结果正序处理，并判断住院日期是否与入院日期相等，如果相等则进行截断处理
                    for (int i = 0; i < inpTempList.size(); i++)
                    {
                        TimerAndInpatientDto timerAndInpatientDto = inpTempList.get(i);

                        // 将查询结果倒序装入实际处理集合
                        inpList.add(0, timerAndInpatientDto);

                        // 将住院日期与入院日期进行比较，如果相等则进行截断操作，剩余部分将在下次查询中继续处理
                        if (timerAndInpatientDto.getInpatientDate().compareTo(
                                timerAndInpatientDto.getVisitDate()) == 0)
                        {
                            actuallyDisplayForwardCount = inpTempList.size()
                                - i - 1;

                            break;
                        }
                    }
                }
            }

            // 根据条件查询相应的用药长期医嘱
            longTermDrugOrder = inpatientDao.selectInpatientList(
                    TimerAndInpatientDto.LONG_TERM_DRUG_ORDER,
                    inpList.get(0).getVisitSn(), getListDate(inpList, 0),
                    getListDate(inpList, 1), getListDate(inpList, 2),
                    getListDate(inpList, 3), getListDate(inpList, 4),
                    getListDate(inpList, 5), getListDate(inpList, 6),
                    Constants.ORDER_STATUS_CANCEL, Constants.LONG_TERM_FLAG,userSn,
                    displayCount, orgCode);

            // 根据相应查询条件查询所有当页日期中的每个日期的记录总条数(长期药物医嘱)
            List<BigDecimal> longTermDrugOrderCount = inpatientDao.selectInpatientList(
                    TimerAndInpatientDto.LONG_TERM_DRUG_ORDER_COUNT,
                    inpList.get(0).getVisitSn(), getListDate(inpList, 0),
                    getListDate(inpList, 1), getListDate(inpList, 2),
                    getListDate(inpList, 3), getListDate(inpList, 4),
                    getListDate(inpList, 5), getListDate(inpList, 6),
                    Constants.ORDER_STATUS_CANCEL, Constants.LONG_TERM_FLAG,
                    userSn, orgCode);

            // 获取住院某日期范围内开始日期和结束日期
            String inpatientBeginDate = DateUtils.dateToString(
                    inpList.get(0).getInpatientDate(),
                    DateUtils.PATTERN_MINUS_DATE);

            String inpatientEndDate = DateUtils.dateToString(
                    inpList.get((inpList.size() - 1)).getInpatientDate(),
                    DateUtils.PATTERN_MINUS_DATE);

            // 获取就诊内部序列号
            BigDecimal visitAllSn = inpList.get(0).getVisitSn();

            // 查询体格检查中体温
            temAllList = inpatientDao.selectInpatientList(
                    TimerAndInpatientDto.PHYSICAL, visitAllSn,
                    TimerAndInpatientDto.TEMPERATURE_ITEM_UNIT,
                    inpatientBeginDate, inpatientEndDate,
                    Constants.PHYSICAL_TEMPERATURE, NUM,orgCode);

            // 查询体格检查中血压低压
            bpLowAllList = inpatientDao.selectInpatientList(
                    TimerAndInpatientDto.PHYSICAL, visitAllSn,
                    TimerAndInpatientDto.BLOOD_PRESSURE_ITEM_UNIT,
                    inpatientBeginDate, inpatientEndDate,
                    Constants.PHYSICAL_BLOOD_DIASTOLIC, Constants.LOW_PRESSURE,
                    NUM,orgCode);

            // 查询体格检查中血压高压
            bpHighAllList = inpatientDao.selectInpatientList(
                    TimerAndInpatientDto.PHYSICAL, visitAllSn,
                    TimerAndInpatientDto.BLOOD_PRESSURE_ITEM_UNIT,
                    inpatientBeginDate, inpatientEndDate,
                    Constants.PHYSICAL_BLOOD_SYSTOLIC, Constants.HIGH_PRESSURE,
                    NUM,orgCode);

            // 查询体格检查中脉搏
            pulAllList = inpatientDao.selectInpatientList(
                    TimerAndInpatientDto.PHYSICAL, visitAllSn,
                    TimerAndInpatientDto.PULSE_ITEM_UNIT, inpatientBeginDate,
                    inpatientEndDate, Constants.PHYSICAL_PULSE, NUM,orgCode);

            // 查询体格检查中呼吸
            breAllList = inpatientDao.selectInpatientList(
                    TimerAndInpatientDto.PHYSICAL, visitAllSn, "",
                    inpatientBeginDate, inpatientEndDate,
                    Constants.PHYSICAL_BREATHE, NO_NUM,orgCode);

            // 查询体格检查中大便次数
            defacateAllList = inpatientDao.selectInpatientList(
                    TimerAndInpatientDto.PHYSICAL, visitAllSn, "",
                    inpatientBeginDate, inpatientEndDate,
                    Constants.PHYSICAL_DEFECATE_TIMES, NO_NUM,orgCode);

            // 查询体格检查中输入液量
            inputAllList = inpatientDao.selectInpatientList(
                    TimerAndInpatientDto.PHYSICAL, visitAllSn, "",
                    inpatientBeginDate, inpatientEndDate,
                    Constants.PHYSICAL_INPUT_QUANTITY, NO_NUM,orgCode);

            // 查询体格检查中尿量
            peeAllList = inpatientDao.selectInpatientList(
                    TimerAndInpatientDto.PHYSICAL, visitAllSn, "",
                    inpatientBeginDate, inpatientEndDate,
                    Constants.PHYSICAL_PEE_QUANTITY, NO_NUM,orgCode);

            // 查询体格检查中体重
            weightAllList = inpatientDao.selectInpatientList(
                    TimerAndInpatientDto.PHYSICAL, visitAllSn, "",
                    inpatientBeginDate, inpatientEndDate,
                    Constants.PHYSICAL_WEIGHT, NO_NUM,orgCode);

            // 疼痛评估指标
            painAssessmentAllList = inpatientDao.selectInpatientList(
                    TimerAndInpatientDto.PHYSICAL, visitAllSn, "",
                    inpatientBeginDate, inpatientEndDate,
                    Constants.PHYSICAL_PAIN_ASSESSMENT, NO_NUM,orgCode);

            // 疼痛评估指标(活动)
            painAssessmentActiveAllList = inpatientDao.selectInpatientList(
                    TimerAndInpatientDto.PHYSICAL, visitAllSn, "",
                    inpatientBeginDate, inpatientEndDate,
                    Constants.PHYSICAL_PAIN_ASSESSMENT_ACTIVE, NO_NUM,orgCode);

            if ((useACLFlag && accessControlDto.getClinicalInfoAuth01())
                || !useACLFlag)
            {
                // 获取日期范围内相应诊断信息
                diagnosisAllList = inpatientDao.selectInpatientList(
                        TimerAndInpatientDto.DIAGNOSIS, visitAllSn,
                        inpatientBeginDate, inpatientEndDate, displayCount, orgCode, 0);
            }

            if ((useACLFlag && accessControlDto.getClinicalInfoAuth02())
                || !useACLFlag)
            {
                // 获取日期范围内相应临时用药医嘱信息
                drugAllList = inpatientDao.selectInpatientList(
                        TimerAndInpatientDto.DRUG_ORDER, visitAllSn,
                        inpatientBeginDate, inpatientEndDate,
                        Constants.ORDER_STATUS_CANCEL,
                        Constants.TEMPORARY_FLAG, userSn, displayCount, orgCode, 0);
            }

            if ((useACLFlag && accessControlDto.getClinicalInfoAuth06())
                || !useACLFlag)
            {
                // 获取日期范围内相应检查信息
                examAllList = inpatientDao.selectInpatientList(
                        TimerAndInpatientDto.EXAMINATION, visitAllSn,
                        inpatientBeginDate, inpatientEndDate, displayCount, orgCode, 0);
            }

            if ((useACLFlag && accessControlDto.getClinicalInfoAuth05())
                || !useACLFlag)
            {
                // 获取日期范围内相应检验信息
                labAllList = inpatientDao.selectInpatientList(
                        TimerAndInpatientDto.LAB, visitAllSn,
                        inpatientBeginDate, inpatientEndDate, displayCount, orgCode, 0);
            }

            // $Author :chang_xuewen
            // $Date : 2013/10/24 11:00$
            // [BUG]0038443 MODIFY BEGIN
            if ((useACLFlag && accessControlDto.getClinicalInfoAuth04())
                || !useACLFlag)
            {
                // 获取日期范围内相应手术信息
                procedureAllList = inpatientDao.selectInpatientList(
                        TimerAndInpatientDto.PROCEDURE, visitAllSn,
                        inpatientBeginDate, inpatientEndDate, displayCount, orgCode, 0);
            }
            // [BUG]0038443 MODIFY END
            
            // $Author :chang_xuewen
            // $Date : 2013/10/24 11:00$
            // [BUG]0038443 ADD BEGIN
            // 病历文书手术内容
            if ((useACLFlag && accessControlDto.getClinicalInfoAuth07())
                    || !useACLFlag)
            {
                // 获取日期范围内相应病例文书手术信息
                proceduredocAllList = inpatientDao.selectInpatientList(
                        "proceduredoc", visitAllSn,
                        inpatientBeginDate, inpatientEndDate, displayCount, orgCode, 0);
            }
            // [BUG]0038443 ADD END
            
            if ((useACLFlag && accessControlDto.getClinicalInfoAuth07())
                || !useACLFlag)
            {
                // 获取日期范围内相应病例文书信息
                documentAllList = inpatientDao.selectInpatientList(
                        TimerAndInpatientDto.DOCUMENTATION, visitAllSn,
                        inpatientBeginDate, inpatientEndDate, displayCount, orgCode, 0);
            }

            if ((useACLFlag && accessControlDto.getClinicalInfoAuth03())
                || !useACLFlag)
            {
                // 获取日期范围内相应非药物医嘱信息
                noDrugAllList = inpatientDao.selectInpatientList(
                        TimerAndInpatientDto.NO_DRUG_ORDER, visitAllSn,
                        inpatientBeginDate, inpatientEndDate, displayCount, orgCode, 0);
            }

            // 实际处理的住院记录
            for (int i = 0; i < inpList.size(); i++)
            {
                TimerAndInpatientDto timerAndInpatientDto = inpList.get(i);

                // 获取住院日期
                inpatientDate = DateUtils.dateToString(
                        timerAndInpatientDto.getInpatientDate(),
                        DateUtils.PATTERN_MINUS_DATE);

                // 设置每页实际显示条数(向前翻页)
                timerAndInpatientDto.setActuallyDisplayForwardCount(actuallyDisplayForwardCount);
                // 设置每页实际显示条数(向后翻页)
                timerAndInpatientDto.setActuallyDisplayBackwardCount(actuallyDisplayBackwardCount);

                // 住院日期默认显示颜色
                timerAndInpatientDto.setInpatientDateDisplayColor(TimerAndInpatientDto.COLOR_BLACK);

                // 当就诊改变时给予颜色标识
                if (timerAndInpatientDto.getVisitDate() != null)
                {
                    if (timerAndInpatientDto.getInpatientDate().compareTo(
                            timerAndInpatientDto.getVisitDate()) == 0)
                    {
                        timerAndInpatientDto.setInpatientDateDisplayColor(TimerAndInpatientDto.COLOR_BLUE);
                    }
                }
                else
                {
                    logger.error("入院日期为空或值无效: {}"
                        + timerAndInpatientDto.getVisitDate());
                }

                // 查询体格检查中体温
                temList.addAll(newList(getEachBusinessList(temAllList,
                        inpatientDate)));

                // 查询体格检查中血压低压
                bpLowList.addAll(newList(getEachBusinessList(bpLowAllList,
                        inpatientDate)));

                // 查询体格检查中血压高压
                bpHighList.addAll(newList(getEachBusinessList(bpHighAllList,
                        inpatientDate)));

                // 查询体格检查中脉搏
                pulList.addAll(newList(getEachBusinessList(pulAllList,
                        inpatientDate)));

                // 查询体格检查中呼吸
                breList.addAll(pickBreatheValue(getEachBusinessList(breAllList,
                        inpatientDate)));

                // 查询体格检查中大便次数
                defacateList.add(getAccumulativePERecord(getEachBusinessList(
                        defacateAllList, inpatientDate)));

                // 查询体格检查中输入液量
                inputList.add(getAccumulativePERecord(getEachBusinessList(
                        inputAllList, inpatientDate)));

                // 查询体格检查中尿量
                peeList.add(getAccumulativePERecord(getEachBusinessList(
                        peeAllList, inpatientDate)));

                // 查询体格检查中体重
                weightList.add(getLatestPERecord(getEachBusinessList(
                        weightAllList, inpatientDate)));

                // $Author:jin_peng
                // $Date:2012/12/28 17:01
                // $[BUG]0012992 DELETE BEGIN
                // $Author:wei_peng
                // $Date:2012/9/29 10:16
                // $[BUG]0010038 ADD BEGIN
                // 疼痛评估指标
                // painAssessmentList.add(getEachBusinessList(
                // painAssessmentAllList, inpatientDate));
                // $[BUG]0010038 ADD END

                // $Author:jin_peng
                // $Date:2012/12/28 17:01
                // $[BUG]0012992 ADD BEGIN
                // 疼痛评估指标(活动)
                // painAssessmentActiveList.add(getEachBusinessList(
                // painAssessmentActiveAllList, inpatientDate));

                // $[BUG]0012992 ADD END
                // $[BUG]0012992 DELETE END

                // $Author:jin_peng
                // $Date:203/01/04 10:38
                // $[BUG]0012992 ADD BEGIN
                // 合并两疼痛评估指标内容
                painAssessmentMergedList.add(getPainAssessmentMergedList(
                        getLatestPERecord(getEachBusinessList(
                                painAssessmentAllList, inpatientDate)),
                        getLatestPERecord(getEachBusinessList(
                                painAssessmentActiveAllList, inpatientDate))));

                // $[BUG]0012992 ADD END

                if ((useACLFlag && accessControlDto.getClinicalInfoAuth01())
                    || !useACLFlag)
                {
                    // 查询相应诊断信息
                    diagnosisList.add(getEachBusinessList(diagnosisAllList,
                            inpatientDate));
                }

                if ((useACLFlag && accessControlDto.getClinicalInfoAuth02())
                    || !useACLFlag)
                {
                    // 获取匹配的长期药物医嘱
                    longTermDrugOrderList.add(getLongTermDrugOrderList(
                            longTermDrugOrder,
                            inpatientDate,
                            longTermAppearedfirst,
                            Integer.parseInt(StringUtils.BigDecimalToStr(longTermDrugOrderCount.get(i)))));

                    // 查询相应临时用药医嘱信息
                    drugOrderList.add(getEachBusinessList(drugAllList,
                            inpatientDate));
                }

                if ((useACLFlag && accessControlDto.getClinicalInfoAuth06())
                    || !useACLFlag)
                {
                    // 查询相应检查信息
                    examinationList.add(getInpatientExamDtoList(newList(getEachBusinessList(
                            examAllList, inpatientDate))));
                }

                if ((useACLFlag && accessControlDto.getClinicalInfoAuth05())
                    || !useACLFlag)
                {
                    // 查询相应用检验信息
                    labList.add(getInpatientLabDtoList(newList(getEachBusinessList(
                            labAllList, inpatientDate))));
                }

                if ((useACLFlag && accessControlDto.getClinicalInfoAuth04())
                    || !useACLFlag)
                {
                    // 查询相应手术信息
                    procedureList.add(getEachBusinessList(procedureAllList,
                            inpatientDate));
                }
                
                // $Author :chang_xuewen
                // $Date : 2013/10/24 11:00$
                // [BUG]0038443 ADD BEGIN
                if ((useACLFlag && accessControlDto.getClinicalInfoAuth07())
                        || !useACLFlag)
                {
                    // 查询相应病例文书手术信息
                    proceduredocList.add(getEachBusinessList(proceduredocAllList,
                    inpatientDate));
                }
                // [BUG]0038443 ADD END
                
                if ((useACLFlag && accessControlDto.getClinicalInfoAuth07())
                    || !useACLFlag)
                {
                    // 查询相应病例文书信息
                    documenationList.add(getEachBusinessList(documentAllList,
                            inpatientDate));
                }

                if ((useACLFlag && accessControlDto.getClinicalInfoAuth03())
                    || !useACLFlag)
                {
                    // 查询相应用非用药医嘱信息
                    noDrugOrderList.add(getEachBusinessList(noDrugAllList,
                            inpatientDate));
                }

                // $[BUG]0011225 MODIFY END
            }
        }
        else
        {
            // 如果住院信息为空则新创建一个住院对象集合
            inpList = new ArrayList<TimerAndInpatientDto>();
        }

        // 如果住院对象集合中该对象不足一页显示的则占位补充
        for (int i = visitRowsCnt - inpList.size() - 1; i >= 0; i--)
        {
            TimerAndInpatientDto timerAndInpatientDto = new TimerAndInpatientDto();
            timerAndInpatientDto.setExistsFlag(TimerAndInpatientDto.NO_EXISTS_FLAG);// 设置无住院记录标识
            inpList.add(timerAndInpatientDto);

            // 当无住院记录时需添加初始值，以便循环显示页面
            breList.addAll(pickBreatheValue(null));
            defacateList.add(null);
            inputList.add(null);
            peeList.add(null);
            weightList.add(null);

            // $Author:jin_peng
            // $Date:2012/12/28 17:01
            // $[BUG]0012992 DELETE BEGIN
            // $Author:wei_peng
            // $Date:2012/9/29 10:16
            // $[BUG]0010038 ADD BEGIN
            // painAssessmentList.add(null);
            // $[BUG]0010038 ADD END

            // $Author:jin_peng
            // $Date:2012/12/28 17:01
            // $[BUG]0012992 ADD BEGIN
            // 疼痛评估指标(活动)
            // painAssessmentActiveList.add(null);

            // $[BUG]0012992 ADD END
            // $[BUG]0012992 DELETE END

            // $Author:jin_peng
            // $Date:203/01/04 10:38
            // $[BUG]0012992 ADD BEGIN
            // 合并两疼痛评估指标内容
            painAssessmentMergedList.add(null);

            // $[BUG]0012992 ADD END

            diagnosisList.add(null);
            drugOrderList.add(null);
            examinationList.add(null);
            labList.add(null);
            procedureList.add(null);
            // $Author :chang_xuewen
            // $Date : 2013/10/24 11:00$
            // [BUG]0038443 ADD BEGIN
            proceduredocList.add(null);
            // [BUG]0038443 ADD END
            documenationList.add(null);
            noDrugOrderList.add(null);
            longTermDrugOrderList.add(null);
        }

        // 装入住院记录及体格检查对象集合
        inpatientList.add(inpList);
        temperatureList.add(temList);
        bloodPressureLowList.add(bpLowList);
        bloodPressureHighList.add(bpHighList);
        pulseList.add(pulList);
        breatheList.add(breList);

        // 将住院视图页面所需所有信息对象装入list
        inpatientAllList.add(inpatientList);
        inpatientAllList.add(diagnosisList);
        inpatientAllList.add(drugOrderList);
        inpatientAllList.add(examinationList);
        inpatientAllList.add(labList);
        inpatientAllList.add(procedureList);
        inpatientAllList.add(documenationList);
        inpatientAllList.add(noDrugOrderList);

        // 将三测单除图形外内容集合装入list
        inpatientAllList.add(defacateList);
        inpatientAllList.add(inputList);
        inpatientAllList.add(peeList);
        inpatientAllList.add(weightList);
        inpatientAllList.add(breatheList);
        // $Author:jin_peng
        // $Date:203/01/04 10:38
        // $[BUG]0012992 ADD BEGIN
        // 合并两疼痛评估指标内容
        inpatientAllList.add(painAssessmentMergedList);
        // $[BUG]0012992 ADD END

        // 将三测单图形内容集合装入list
        inpatientAllList.add(temperatureList);
        inpatientAllList.add(bloodPressureLowList);
        inpatientAllList.add(bloodPressureHighList);
        inpatientAllList.add(pulseList);

        // $Author:jin_peng
        // $Date:2012/12/28 17:01
        // $[BUG]0012992 DELETE BEGIN
        // $Author:wei_peng
        // $Date:2012/9/29 10:16
        // $[BUG]0010038 ADD BEGIN
        // inpatientAllList.add(painAssessmentList);
        // $[BUG]0010038 ADD END
        // $[BUG]0012992 DELETE END

        inpatientAllList.add(longTermDrugOrderList);
        // [BUG]0010239 ADD END

        // $Author:jin_peng
        // $Date:2012/12/28 17:01
        // $[BUG]0012992 DELETE BEGIN
        // $Author:jin_peng
        // $Date:2012/12/28 17:01
        // $[BUG]0012992 ADD BEGIN
        // 疼痛指标（活动）
        // inpatientAllList.add(painAssessmentActiveList);

        // $[BUG]0012992 ADD BEGIN
        // $[BUG]0012992 DELETE END
        // $Author :chang_xuewen
        // $Date : 2013/10/24 11:00$
        // [BUG]0038443 ADD BEGIN
        inpatientAllList.add(proceduredocList);
        // [BUG]0038443 ADD END

        return inpatientAllList;
    }

    // [BUG]0010132 MODIFY END

    // $Author:jin_peng
    // $Date:203/01/04 10:38
    // $[BUG]0012992 ADD BEGIN
    /**
     * 合并活动与静止疼痛指标
     * @param motionlessList 静止疼痛指标集合
     * @param motionList 活动疼痛指标集合
     * @return 合并后的疼痛指标集合
     */
    private List<TimerAndInpatientDto> getPainAssessmentMergedList(
            List<TimerAndInpatientDto> motionlessList,
            List<TimerAndInpatientDto> motionList)
    {
        List<TimerAndInpatientDto> itemResultsMergedList = null;

        // 合并后的结果
        String itemResults = null;

        String itemMotionless = null;
        String itemMotion = null;

        // 静止疼痛指标存在情况下合并到最终疼痛指标结果中
        if (motionlessList != null && !motionlessList.isEmpty())
        {
            itemMotionless = motionlessList.get(0).getItemResult();

            if (!StringUtils.isEmpty(itemMotionless))
            {
                itemResults = itemMotionless + " /";
            }
        }

        // 活动疼痛指标存在情况下合并到最终疼痛指标结果中
        if (motionList != null && !motionList.isEmpty())
        {
            itemMotion = motionList.get(0).getItemResult();

            // 分静止疼痛指标是否存在来决定拼接内容的变化
            if (!StringUtils.isEmpty(itemMotion))
            {
                if (!StringUtils.isEmpty(itemResults))
                {
                    itemResults += " " + itemMotion;
                }
                else
                {
                    itemResults = "/ " + itemMotion;
                }
            }
        }

        // 如果拼接疼痛指标存在则返回该合并后的疼痛指标内容
        if (!StringUtils.isEmpty(itemResults))
        {
            itemResultsMergedList = new ArrayList<TimerAndInpatientDto>();

            TimerAndInpatientDto painAssessmentMerged = new TimerAndInpatientDto();

            painAssessmentMerged.setItemResult(itemResults);

            itemResultsMergedList.add(painAssessmentMerged);
        }

        return itemResultsMergedList;
    }

    // $[BUG]0012992 ADD END

    /**
     * 实例一个装有TimerAndInpatientDto对象集合
     * @param list 待实例化的集合
     * @return 装有TimerAndInpatientDto对象集合
     */
    private List<TimerAndInpatientDto> newList(List<TimerAndInpatientDto> list)
    {
        if (list == null)
        {
            return new ArrayList<TimerAndInpatientDto>();
        }

        return list;
    }

    // $Author:jin_peng
    // $Date:2012/11/12 15:09
    // $[BUG]0011225 ADD BEGIN
    /**
     * 获取给定住院日期的给业务对象集合
     * @param businessAllList 各业务住院日期范围内所有对象集合
     * @param inpatientDate 给定的某个住院日期
     * @return 给定日期内的各业务对象集合
     */
    private List<TimerAndInpatientDto> getEachBusinessList(
            List<TimerAndInpatientDto> businessAllList, String inpatientDate)
    {
        // 需装入的按给定日期获取的记录集合
        List<TimerAndInpatientDto> eachBusinessList = null;

        if (businessAllList != null && !businessAllList.isEmpty())
        {
            eachBusinessList = new ArrayList<TimerAndInpatientDto>();

            // 将匹配给定日期的记录装入返回结果结合中
            for (int i = 0; i < businessAllList.size(); i++)
            {
                TimerAndInpatientDto tid = businessAllList.get(i);

                if (inpatientDate.equals(DateUtils.dateToString(
                        tid.getInpatientDate(), DateUtils.PATTERN_MINUS_DATE)))
                {
                    eachBusinessList.add(tid);
                }
            }

            // 将已获取的对象从整个大集合中删除
            businessAllList.removeAll(eachBusinessList);
        }

        return eachBusinessList;
    }

    // $[BUG]0011225 ADD END

    /**
     * 按住院日期分别获取该天按每四个小时划分的三测单中呼吸部分的第一个值
     * @param breatheList 没有过滤时的呼吸对象集合
     * @return 过滤后的呼吸对象集合
     */
    private List<TimerAndInpatientDto> pickBreatheValue(
            List<TimerAndInpatientDto> breatheList)
    {
        // 实例过滤后的呼吸对象集合
        List<TimerAndInpatientDto> resList = new ArrayList<TimerAndInpatientDto>();
        // 获取每条记录的时间(小时)
        int hour = 0;
        // 三测单时间划分的小时数倍数定义
        int hourMultiple = 4;

        // 按每天每四个小时划分的的第一个呼吸值
        TimerAndInpatientDto firstBreathe = null;
        TimerAndInpatientDto secondBreathe = null;
        TimerAndInpatientDto thirdBreathe = null;
        TimerAndInpatientDto fourthBreathe = null;
        TimerAndInpatientDto fifthBreathe = null;
        TimerAndInpatientDto sixthBreathe = null;

        if (breatheList != null)
        {
            for (TimerAndInpatientDto breatheDto : breatheList)
            {
                hour = breatheDto.getExamTimeHour();

                // 获取每天0-4(不包含第4小时)小时范围内的第一个呼吸对象
                if (hour > 0 * hourMultiple && hour <= 1 * hourMultiple)
                {
                    if (firstBreathe == null)
                    {
                        firstBreathe = breatheDto;
                    }
                }

                // 获取每天4-8(不包含第8小时)小时范围内的第一个呼吸对象
                if (hour > 1 * hourMultiple && hour <= 2 * hourMultiple)
                {
                    if (secondBreathe == null)
                    {
                        secondBreathe = breatheDto;
                    }
                }

                // 获取每天8-12(不包含第12小时)小时范围内的第一个呼吸对象
                if (hour > 2 * hourMultiple && hour <= 3 * hourMultiple)
                {
                    if (thirdBreathe == null)
                    {
                        thirdBreathe = breatheDto;
                    }
                }

                // 获取每天12-16(不包含第16小时)小时范围内的第一个呼吸对象
                if (hour > 3 * hourMultiple && hour <= 4 * hourMultiple)
                {
                    if (fourthBreathe == null)
                    {
                        fourthBreathe = breatheDto;
                    }
                }

                // 获取每天16-20(不包含第20小时)小时范围内的第一个呼吸对象
                if (hour > 4 * hourMultiple && hour <= 5 * hourMultiple)
                {
                    if (fifthBreathe == null)
                    {
                        fifthBreathe = breatheDto;
                    }
                }

                // 获取每天20-24(不包含第24小时)小时范围内的第一个呼吸对象
                if (hour > 5 * hourMultiple && hour <= 6 * hourMultiple)
                {
                    if (sixthBreathe == null)
                    {
                        sixthBreathe = breatheDto;
                    }
                }
            }
        }

        // 将已过滤完成的对象转入集合
        resList.add(firstBreathe);
        resList.add(secondBreathe);
        resList.add(thirdBreathe);
        resList.add(fourthBreathe);
        resList.add(fifthBreathe);
        resList.add(sixthBreathe);

        return resList;
    }

    // $Author :jin_peng
    // $Date : 2012/11/06 13:48$
    // [BUG]0011026 MODIFY BEGIN
    // $Author :jin_peng
    // $Date : 2012/09/28 13:48$
    // [BUG]0010132 MODIFY BEGIN
    /**
     * 查寻住院视图总记录数
     * @param patientSn 患者内部序列号
     * @param inpatientType 住院类型
     * @param inpatientStartDate 某次住院的住院开始日期
     * @param inpatientEndDate 某次住院的住院结束时间
     * @param dischargeDate 出院时间
     * @param exitInpatient 退院标识
     * @return 住院视图总记录数
     */
    @Transactional
    public int selectTotalCnt(String patientSn, String inpatientType,
            String inpatientStartDate, String inpatientEndDate,
            String dischargeDate, String exitInpatient, String orgCode)
    {
        return inpatientDao.selectTotalCnt(patientSn, inpatientType,
                inpatientStartDate, inpatientEndDate, dischargeDate,
                exitInpatient, orgCode);
    }

    // [BUG]0010132 MODIFY END

    // $Author:wei_peng
    // $Date:2012/10/10 13:19
    // $[BUG]0010243 MODIFY BEGIN
    /**
     * 获取页面显示的检验项目及存在异常的检验具体结果信息
     * @param lrciList 检验大项对象集合
     * @return 检验项目及存在异常的检验具体结果信息
     */
    @Transactional
    public List<TimerAndInpatientDto> getInpatientLabDtoList(
            List<TimerAndInpatientDto> lrciList)
    {
        List<TimerAndInpatientDto> labOrderList = new TimerAndInpatientDto().getLabDtoList(
                lrciList, timerAndInpatientDao);
        
        // $Author:jin_peng
        // $Date:2013/10/14 15:47
        // $[BUG]0038068 DELETE BEGIN
        /**StringBuffer stringBuffer = null;
        for (TimerAndInpatientDto labOrder : labOrderList)
        {
            stringBuffer = new StringBuffer();
            stringBuffer.append("<table id=tblid style=width:400px;border:0px; cellspacing=1 cellpadding=2> ");
            
            if (labOrder.getLabReportSn() == null) 
            {
                List<NursingOperation> nursingOptList = timerAndInpatientDao.selectNursingOptByOrderSn(labOrder.getLabOrderSn());

                // $Author:jin_peng
                // $Date:2012/11/01 16:03
                // $[BUG]0010908 MODIFY BEGIN
                if (nursingOptList != null && !nursingOptList.isEmpty())
                {
                    StringBuffer operatorNames = new StringBuffer();
                    StringBuffer operatorItemNames = new StringBuffer();
                    StringBuffer operationTimes = new StringBuffer();
                    for (NursingOperation nursingOpt:nursingOptList)
                    {
                        operatorNames.append("<td>"
                                + (StringUtils.isEmpty(nursingOpt.getOperatorName()) ? ""
                                        : nursingOpt.getOperatorName()) + "</td>");
                        operatorItemNames.append("<td class=NURSINGVALIDATION><FONT>"
                                // Author:jia_yanqing
                                // Date:2013/7/2 16:55
                                // [BUG]0033848 MODIFY BEGIN
                                + (StringUtils.isEmpty(nursingOpt.getOrderStatusName()) ? ""
                                        : nursingOpt.getOrderStatusName())
                                        + "</FONT></td>");
                                // [BUG]0033848 MODIFY END
                        operationTimes.append("<td>"
                                + (nursingOpt.getOperationTime() == null ? ""
                                        : DateUtils.dateToString(
                                                nursingOpt.getOperationTime(),
                                                "yyyy-MM-dd HH:mm")) + "</td>");                            
                    }
                    stringBuffer.append("<tr height=24 ><td><b>操作人姓名</b></td>").append(operatorNames).append("</tr>");
                    stringBuffer.append("<tr height=24 ><td><b>操作名称</b></td>").append(operatorItemNames).append("</tr>");
                    stringBuffer.append("<tr height=24 ><td><b>操作时间</b></td>").append(operationTimes).append("</tr>");
                }
                else
                {
                    stringBuffer.append("<tr class=LABNURSINGNONE height=24></tr>");
                }
    
                // $[BUG]0010908 MODIFY END
                 
                 } else { stringBuffer.append("<tr class=even height=24>");
                 stringBuffer
                 .append("<td class=label><FONT>检验科室：</FONT></td><td class=dataitem>"
                 + (labOrder.getLabDeptName() == null ? "" :
                 labOrder.getLabDeptName()) + "</td>");
                 stringBuffer.append("<td class=label>检验医生：</td><td class=dataitem>"
                 + (labOrder.getTesterName() == null ? "" :
                 labOrder.getTesterName()) + "</td>");
                 stringBuffer.append("</tr>");
                 stringBuffer.append("<tr class=odd height=24>");
                 stringBuffer.append
                 ("<td class=label>检验日期：</td><td class=dataitem>" +
                 (labOrder.getTestDate() == null ? "" : "<fmt:formatDate value=" +
                 labOrder.getTestDate() + " type=date pattern=yyyy-MM-dd />") +
                 "</td>");
                 stringBuffer.append("<td class=label>检验结果：</td><td class=dataitem>"
                 + (labOrder.getTestResults() == null ? "" :
                 labOrder.getTestResults()) + "</td>");
                 stringBuffer.append("</tr>"); }
            }
            
            stringBuffer.append("</table>");
            labOrder.setExamOrLabTipContent(stringBuffer.toString());
        }*/
        
        // $[BUG]0038068 DELETE END
        
        return labOrderList;
    }

    // $[BUG]0010243 MODIFY END


    // $Author:tong_meng
    // $Date:2013/11/19 09:02
    // $[BUG]0039539 MODIFY BEGIN
    // $Author:wei_peng
    // $Date:2012/10/9 16:24
    // $[BUG]0010243 ADD BEGIN
    /**
     * 添加检查医嘱Tip显示内容
     * @param examList 检查医嘱集合
     * @return 检查医嘱集合
     */
    @Transactional
    public List<TimerAndInpatientDto> getInpatientExamDtoList(
            List<TimerAndInpatientDto> examList)
    {
        
        List<TimerAndInpatientDto> examOrderList = new TimerAndInpatientDto().getExamDtoList(
                examList, timerAndInpatientDao);

        return examOrderList;
        /*StringBuffer stringBuffer = null;
        for (TimerAndInpatientDto examOrder : examList)
        {
            stringBuffer = new StringBuffer();
            stringBuffer.append("<table id=tblid style=width:400px;border:0px; cellspacing=1 cellpadding=2 class=table>");
            if (examOrder.getExamReportSn() == null)
            {
                List<NursingOperation> nursingOptList = timerAndInpatientDao.selectNursingOptByOrderSn(examOrder.getExamOrderSn());

                // $Author:jin_peng
                // $Date:2012/11/01 16:03
                // $[BUG]0010908 MODIFY BEGIN
                if (nursingOptList != null && !nursingOptList.isEmpty())
                {
                    StringBuffer operatorNames = new StringBuffer();
                    StringBuffer operatorItemNames = new StringBuffer();
                    StringBuffer operationTimes = new StringBuffer();
                    for (NursingOperation nursingOpt:nursingOptList)
                    {
                        operatorNames.append("<td>"
                                + (StringUtils.isEmpty(nursingOpt.getOperatorName()) ? ""
                                        : nursingOpt.getOperatorName()) + "</td>");
                        operatorItemNames.append("<td><FONT>"
                                // Author:jia_yanqing
                                // Date:2013/7/2 16:55
                                // [BUG]0033848 MODIFY BEGIN
                                + (StringUtils.isEmpty(nursingOpt.getOrderStatusName()) ? ""
                                        : nursingOpt.getOrderStatusName())
                                        + "</FONT></td>");
                               // [BUG]0033848 MODIFY END
                        operationTimes.append("<td>"
                                + (nursingOpt.getOperationTime() == null ? ""
                                        : DateUtils.dateToString(
                                                nursingOpt.getOperationTime(),
                                                "yyyy-MM-dd HH:mm")) + "</td>");                            
                    }
                    stringBuffer.append("<tr height=24 ><td><b>操作人姓名</b></td>").append(operatorNames).append("</tr>");
                    stringBuffer.append("<tr height=24 ><td><b>操作名称</b></td>").append(operatorItemNames).append("</tr>");
                    stringBuffer.append("<tr height=24 ><td><b>操作时间</b></td>").append(operationTimes).append("</tr>");
                }
                else
                {
                    stringBuffer.append("<tr class=NURSINGNONE height=24></tr>");
                }
            }
            else
            {
                stringBuffer.append("<tr class=even height=24>");
                stringBuffer.append("<td class=label><FONT>检查类别：</FONT></td><td class=dataitem>"
                    + (examOrder.getItemClassName() == null ? ""
                            : examOrder.getItemClassName()) + "</td>");
                stringBuffer.append("<td class=label>报告医生：</td><td class=dataitem>"
                    + (examOrder.getReportDoctorName() == null ? ""
                            : examOrder.getReportDoctorName()) + "</td>");
                stringBuffer.append("</tr>");
                stringBuffer.append("<tr class=odd height=24>");
                stringBuffer.append("<td class=label>检查科室：</td><td class=dataitem>"
                    + (examOrder.getExamDeptName() == null ? ""
                            : examOrder.getExamDeptName()) + "</td>");
                stringBuffer.append("<td class=label>检查结果：</td><td class=dataitem>"
                    + (examOrder.getImagingFinding() == null ? ""
                            : StringUtils.replaceStr(examOrder.getImagingFinding()))
                    + "</td>");
                stringBuffer.append("</tr>");
            }

            // $[BUG]0010908 MODIFY END

            stringBuffer.append("</table>");
            examOrder.setExamOrLabTipContent(stringBuffer.toString());
        }
        return examList;*/
    }

    // $[BUG]0010243 ADD END
    // $[BUG]0039539 MODIFY END
    
    // $Author :jin_peng
    // $Date : 2012/09/28 13:48$
    // [BUG]0010132 ADD BEGIN
    /**
     * 查找所有的住院视图起始记录号
     * @param patientSn 患者内部序列号
     * @param inpatientType 住院类型
     * @param inpatientStartDate 某次住院的住院开始日期
     * @param inpatientEndDate 某次住院的住院结束时间
     * @param visitSn 就诊内部序列号
     * @param exitInpatient 退院标识
     * @return 记录开始显示号
     */
    @Transactional
    public int selectStartNumber(String patientSn, String inpatientType,
            String inpatientStartDate, String inpatientEndDate, String visitSn,
            String exitInpatient, String orgCode)
    {
        return inpatientDao.selectStartNumber(patientSn, inpatientType,
                inpatientStartDate, inpatientEndDate, visitSn, exitInpatient, orgCode);
    }

    /**
     * 查寻住院视图住院次数
     * @param patientSn 患者内部序列号
     * @param inpatientType 住院类型
     * @param exitInpatient 退院标识
     * @return 住院视图住院次数
     */
    @Transactional
    public List<TimerAndInpatientDto> selectVisitTimes(String patientSn,
            String inpatientType, String exitInpatient, String orgCode)
    {
        return inpatientDao.selectVisitTimes(patientSn, inpatientType,
                exitInpatient, orgCode);
    }

    // [BUG]0010132 ADD END
    // [BUG]0011026 MODIFY END

    // $Author :jin_peng
    // $Date : 2012/10/10 18:28$
    // [BUG]0010239 ADD BEGIN
    /**
     * 从给定的list中查找给定的第几个日期，如果存在则返回该日期，不存在则返回null
     * @param inpList 给定的包含日期的集合
     * @param sequence 序号
     * @return 日期
     */
    private String getListDate(List<TimerAndInpatientDto> inpList, int sequence)
    {
        String resDateStr = null;

        if (inpList != null && !inpList.isEmpty())
        {
            if (sequence < inpList.size())
            {
                resDateStr = DateUtils.dateToString(
                        inpList.get(sequence).getInpatientDate(),
                        DateUtils.PATTERN_MINUS_DATE);
            }
        }

        return resDateStr;
    }

    /**
     * 根据显示日期判断长期用药医嘱的显示内容
     * @param longTermDrugOrderList 长期用药医嘱内容对象集合
     * @param inpatientDate 住院时间
     * @param longTermAppearedfirst 对长期药物医嘱判断每种药物是否是第一次匹配给定住院日期
     * @param longTermEachCount 长期医嘱每条日期中记录总条数
     * @return 判断处理完成的长期用药医嘱对象集合
     */
    private List<TimerAndInpatientDto> getLongTermDrugOrderList(
            List<TimerAndInpatientDto> longTermDrugOrderList,
            String inpatientDate, List<Integer> longTermAppearedfirst,
            int longTermEachCount)
    {
        // 需要在页面显示的长期医嘱对象集合
        List<TimerAndInpatientDto> longDrugOrderList = null;

        Date inpDate = DateUtils.stringToDate(inpatientDate,
                DateUtils.PATTERN_MINUS_DATE);

        if (longTermDrugOrderList != null && !longTermDrugOrderList.isEmpty())
        {
            longDrugOrderList = new ArrayList<TimerAndInpatientDto>();

            // 获取住院日期匹配的长期药物医嘱
            for (int i = 0; i < longTermDrugOrderList.size(); i++)
            {
                TimerAndInpatientDto tiDto = longTermDrugOrderList.get(i);
                TimerAndInpatientDto t = null;

                // 对传入的统一操作对象进行克隆
                try
                {
                    t = (TimerAndInpatientDto) tiDto.clone();
                }
                catch (CloneNotSupportedException e)
                {
                    e.printStackTrace();
                }

                // 如果给定住院日期在长期医嘱有效范围内则向返回集合中添加该医嘱
                if (inpDate.compareTo(t.getOrderStartTime()) >= 0
                    && (t.getStopTime() == null || inpDate.compareTo(t.getStopTime()) <= 0))
                {
                    // 按药物为基本看每种药物是否为第一次匹配给定的住院日期，如果是则给予标记，在页面上特殊显示
                    if (!longTermAppearedfirst.contains(i))
                    {
                        t.setIsFirst(TimerAndInpatientDto.IS_FIRST);

                        longTermAppearedfirst.add(i);
                    }

                    // 设置每行曲线显示颜色
                    t.setLineColor(getLineColor(i));

                    // 设置住院日期
                    t.setInpatientDate(inpDate);

                    // 设置每个住院日期对应的长期医嘱记录总条数
                    t.setLongTermEachCount(longTermEachCount);

                    longDrugOrderList.add(t);
                }
                else
                {
                    TimerAndInpatientDto n = new TimerAndInpatientDto();

                    // 设置就诊内部序列号
                    n.setVisitSn(t.getVisitSn());

                    // 设置住院日期
                    n.setInpatientDate(inpDate);

                    // 设置每个住院日期对应的长期医嘱记录总条数
                    n.setLongTermEachCount(longTermEachCount);

                    longDrugOrderList.add(n);
                }
            }
        }

        return longDrugOrderList;
    }

    /**
     * 获取长期药物医嘱曲线颜色
     * @param count 序号
     * @return 曲线颜色
     */
    private String getLineColor(int count)
    {
        String lineColor = null;

        if (count == 0)
        {
            lineColor = "#6666ff";
        }
        else if (count == 1)
        {
            lineColor = "#ff6666";
        }
        else if (count == 2)
        {
            lineColor = "#99cc66";
        }
        else if (count == 3)
        {
            lineColor = "#cc00ff";
        }
        else if (count == 4)
        {
            lineColor = "#cc0033";
        }

        return lineColor;
    }

    // [BUG]0010239 ADD END

    // $Author:jin_peng
    // $Date:2012/12/25 16:49
    // $[BUG]0012698 ADD BEGIN
    /**
     * 查询住院视图某天具体业务内容
     * @param visitSn 就诊内部序列号
     * @param inpatientDate 住院日期
     * @return 住院视图某天具体业务内容
     */
    @Transactional
    public List<TimerAndInpatientDto> selectSpecificallyDiagnosisList(
            BigDecimal visitSn, String inpatientDate, String userSn)
    {
        List<TimerAndInpatientDto> specificallyDiagnosisList = inpatientDao.selectInpatientSpecificallyList(
                TimerAndInpatientDto.DIAGNOSIS, visitSn, inpatientDate);

        return specificallyDiagnosisList;
    }

    /**
     * 查询住院视图某天具体长期药物医嘱内容
     * @param visitSn 就诊内部序列号
     * @param inpatientDate 住院日期
     * @return 住院视图某天具体长期药物医嘱内容
     */
    @Transactional
    public List<TimerAndInpatientDto> selectSpecificallyLongDrug(
            BigDecimal visitSn, String inpatientDate, String userSn)
    {
        List<TimerAndInpatientDto> specificallyLongDrugList = inpatientDao.selectInpatientSpecificallyList(
                TimerAndInpatientDto.LONG_TERM_DRUG_ORDER, visitSn,
                inpatientDate, Constants.LONG_TERM_FLAG,
                Constants.ORDER_STATUS_CANCEL,
                userSn);
        return specificallyLongDrugList;
    }

    /**
     * 查询住院视图某天具体临时药物医嘱内容
     * @param visitSn 就诊内部序列号
     * @param inpatientDate 住院日期
     * @return 住院视图某天具体临时药物医嘱内容
     */
    @Transactional
    public List<TimerAndInpatientDto> selectSpecificallyTempDrug(
            BigDecimal visitSn, String inpatientDate, String userSn)
    {
        List<TimerAndInpatientDto> specificallyTempDrugList = inpatientDao.selectInpatientSpecificallyList(
                TimerAndInpatientDto.TEMPORARY_DRUG_ORDER, visitSn,
                inpatientDate, Constants.TEMPORARY_FLAG,
                Constants.ORDER_STATUS_CANCEL,
                userSn);
        return specificallyTempDrugList;
    }

    /**
     * 查询住院视图某天具体检查内容
     * @param visitSn 就诊内部序列号
     * @param inpatientDate 住院日期
     * @return 住院视图某天具体检查内容
     */
    @Transactional
    public List<TimerAndInpatientDto> selectSpecificallyExam(
            BigDecimal visitSn, String inpatientDate, String userSn)
    {
        List<TimerAndInpatientDto> specificallyDiagnosisList = inpatientDao.selectInpatientSpecificallyList(
                TimerAndInpatientDto.EXAMINATION, visitSn, inpatientDate);
        return specificallyDiagnosisList;
    }

    /**
     * 查询住院视图某天具体检验内容
     * @param visitSn 就诊内部序列号
     * @param inpatientDate 住院日期
     * @return 住院视图某天具体检验内容
     */
    @Transactional
    public List<TimerAndInpatientDto> selectSpecificallyLab(BigDecimal visitSn,
            String inpatientDate, String userSn)
    {
        List<TimerAndInpatientDto> specificallyDiagnosisList = inpatientDao.selectInpatientSpecificallyList(
                TimerAndInpatientDto.LAB, visitSn, inpatientDate);
        return specificallyDiagnosisList;
    }

    /**
     * 查询住院视图某天具体手术内容
     * @param visitSn 就诊内部序列号
     * @param inpatientDate 住院日期
     * @return 住院视图某天具体手术内容
     */
    @Transactional
    public List<TimerAndInpatientDto> selectSpecificallyProcedure(
            BigDecimal visitSn, String inpatientDate, String userSn)
    {
        List<TimerAndInpatientDto> specificallyDiagnosisList = inpatientDao.selectInpatientSpecificallyList(
                TimerAndInpatientDto.PROCEDURE, visitSn, inpatientDate);
        return specificallyDiagnosisList;
    }
    
    // $Author :chang_xuewen
    // $Date : 2013/10/24 11:00$
    // [BUG]0038443 ADD BEGIN
    /**
     * 查询住院视图某天具体手术相关病历文书内容
     * @param visitSn 就诊内部序列号
     * @param inpatientDate 住院日期
     * @return 住院视图某天具体手术内容
     */
    @Transactional
    public List<TimerAndInpatientDto> selectSpecificallyProceduredoc(
            BigDecimal visitSn, String inpatientDate, String userSn)
    {
        List<TimerAndInpatientDto> specificallyProceduredocList = inpatientDao.selectInpatientSpecificallyList(
                "proceduredoc", visitSn, inpatientDate);
        return specificallyProceduredocList;
    }
    // [BUG]0038443 ADD END
    
    /**
     * 查询住院视图某天具体病例文书内容
     * @param visitSn 就诊内部序列号
     * @param inpatientDate 住院日期
     * @return 住院视图某天具体病例文书内容
     */
    @Transactional
    public List<TimerAndInpatientDto> selectSpecificallyDocument(
            BigDecimal visitSn, String inpatientDate, String userSn)
    {
        List<TimerAndInpatientDto> specificallyDiagnosisList = inpatientDao.selectInpatientSpecificallyList(
                TimerAndInpatientDto.DOCUMENTATION, visitSn, inpatientDate);
        return specificallyDiagnosisList;
    }

    /**
     * 查询住院视图某天具体非药物医嘱内容
     * @param visitSn 就诊内部序列号
     * @param inpatientDate 住院日期
     * @return 住院视图某天具体非药物医嘱内容
     */
    @Transactional
    public List<TimerAndInpatientDto> selectSpecificallyNoneDrug(
            BigDecimal visitSn, String inpatientDate, String userSn)
    {
        List<TimerAndInpatientDto> specificallyDiagnosisList = inpatientDao.selectInpatientSpecificallyList(
                TimerAndInpatientDto.NO_DRUG_ORDER, visitSn, inpatientDate);
        return specificallyDiagnosisList;
    }

    // $[BUG]0012698 ADD END

    // $Author:wei_peng
    // $Date:2013/01/16 14:03
    // [BUG]0013310 ADD BEGIN
    /**
     * 获取检查时间最近的体格检查结果记录，并以集合形式返回
     * @param physicalExaminations 一天内的体格检查结果集合
     * @return 一天内最近一次结果值对象
     */
    public List<TimerAndInpatientDto> getLatestPERecord(
            List<TimerAndInpatientDto> physicalExaminations)
    {
        List<TimerAndInpatientDto> results = new ArrayList<TimerAndInpatientDto>();
        if (physicalExaminations != null && physicalExaminations.size() > 0)
        {
            TimerAndInpatientDto result = physicalExaminations.get(0);
            for (int i = 1; i < physicalExaminations.size(); i++)
            {

                if (physicalExaminations.get(i).getInpatientDate().after(
                        result.getInpatientDate()))
                {
                    result = physicalExaminations.get(i);
                }
            }
            results.add(result);
        }
        return results;
    }

    /**
     * 获取体格检查结果记录一天内的累计值，并以集合形式返回
     * @param physicalExaminations 一天内的体格检查结果集合
     * @return 一天内的累计结果值对象
     */
    public List<TimerAndInpatientDto> getAccumulativePERecord(
            List<TimerAndInpatientDto> physicalExaminations)
    {
        List<TimerAndInpatientDto> results = new ArrayList<TimerAndInpatientDto>();
        if (physicalExaminations != null && physicalExaminations.size() > 0)
        {
            double result = 0;
            for (int i = 0; i < physicalExaminations.size(); i++)
            {
                if (StringUtils.isNumber(physicalExaminations.get(i).getItemResult()))
                {
                    result += Double.parseDouble(physicalExaminations.get(i).getItemResult());
                }
                else
                {
                    results.add(physicalExaminations.get(i));
                    return results;
                }
            }
            physicalExaminations.get(0).setItemResult(result + "");
            results.add(physicalExaminations.get(0));
        }
        return results;
    }
    // [BUG]0013310 ADD END
}
