package com.yly.cdr.batch.writer;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.yly.cdr.cache.DictionaryCache;
import com.yly.cdr.core.Constants;
import com.yly.cdr.entity.BloodLabResult;
import com.yly.cdr.entity.BloodRequest;
import com.yly.cdr.entity.BloodSpecialRequest;
import com.yly.cdr.entity.MedicalVisit;
import com.yly.cdr.hl7.dto.BloodRequestBatch;
import com.yly.cdr.hl7.dto.Diagnosis;
import com.yly.cdr.hl7.dto.Histories;
import com.yly.cdr.hl7.dto.LabResults;
import com.yly.cdr.hl7.dto.SpecialRequest;
import com.yly.cdr.hl7.dto.poorin200901uv04.POORIN200901UV04;
import com.yly.cdr.service.BloodRequestService;
import com.yly.cdr.service.CommonService;
import com.yly.cdr.util.DateUtils;
import com.yly.cdr.util.StringUtils;

/**
 * 输血申请消息数据接入
 * @author huang_jieyu
 *
 */
@Component(value = "poorin200901uv04Writer")
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class POORIN200901UV04Writer implements BusinessWriter<POORIN200901UV04>
{
    private static final Logger logger = LoggerFactory.getLogger(POORIN200901UV04Writer.class);

    private static final Logger loggerBS008 = LoggerFactory.getLogger("BS008");

    @Autowired
    private BloodRequestService bloodRequestService;

    @Autowired
    private CommonService commonService;

    // 用血申请单内部序列号
    private BigDecimal requestSn;

    // 就诊内部序列号
    private BigDecimal visitSn;

    // 患者内部序列号
    private BigDecimal patientSn;

    // 患者本地ID
    private String patientLid;

    // 消息中的数据
    private BloodRequestBatch bloodRequestBatch;

    // 系统时间
    Date systemDate = DateUtils.getSystemTime();

    // $Author :jin_peng
    // $Date : 2012/09/03 13:46$
    // [BUG]0009333 ADD BEGIN
    // 新增状态时是否已经存在该新增记录，默认为不存在
    private boolean isNewExists;

    // [BUG]0009333 ADD END
    
    // $Author: tong_meng
    // $Date: 2013/8/30 15:00
    // [BUG]0036757 ADD BEGIN
    private String serviceId;
    // [BUG]0036757 ADD END

	/**
	 * 数据校验
	 * 
	 * @param baseDto
	 */
	public boolean validate(POORIN200901UV04 poorin200901uv04) {

		// 获取输血单对象信息
		bloodRequestBatch = poorin200901uv04.getBloodRequest().get(0);
		// $Author :chang_xuewen
        // $Date : 2014/1/22 10:30$
        // [BUG]0042086 MODIFY BEGIN   
		/*if (!bloodRequestBatch.getOrganizationCode().equals(
				poorin200901uv04.getMessage().getOrgCode())) {
			loggerBS008.error(
					"Message:{}, validate:{}",
					poorin200901uv04.toString(),
					"消息头中的医疗机构编码和消息体中的医疗机构编码不同，消息头中的医疗机构编码="
							+ poorin200901uv04.getMessage().getOrgCode()
							+ "，消息体中的医疗机构编码="
							+ bloodRequestBatch.getOrganizationCode());
			return false;
		}*/
		if(StringUtils.isEmpty(bloodRequestBatch.getOrganizationCode())){
			// $Author :yang_mingjie
        	// $Date : 2014/06/26 10:09$
        	// [BUG]0045630 MODIFY BEGIN 
			bloodRequestBatch.setOrganizationCode(Constants.HOSPITAL_CODE);
			bloodRequestBatch.setOrganizationName(Constants.HOSPITAL_NAME);
			//[BUG]0045630 MODIFY END
        }
    	// [BUG]0042086 MODIFY END 
		// $Author :jin_peng
		// $Date : 2012/08/24 15:59$
		// [BUG]0009149 ADD BEGIN
		// 针对消息内容相同都为新增情况下，如果已存在一条记录则其余不接入
		if (Constants.NEW_MESSAGE_FLAG.equals(poorin200901uv04.getNewUpFlag())) {

		}
		// [BUG]0009149 ADD END
		// $Author:wei_peng
		// $Date:2013/3/21 14:41
		// [BUG]0014679 ADD BEGIN
		else {
			loggerBS008.error("Message:{}, validate:{}",
					poorin200901uv04.toString(), "错误的消息交互类型："
							+ poorin200901uv04.getNewUpFlag());
			return false;
		}
		// [BUG]0014679 ADD END
		// $Author:wei_peng
		// $Date:2012/9/21 14:38
		// $[BUG]0009776 ADD BEGIN
		List<BloodRequestBatch> bloodRequests = poorin200901uv04
				.getBloodRequest();
		for (BloodRequestBatch bloodRequest : bloodRequests) {
			if (Constants.lstDomainInPatient().contains(bloodRequest
					.getPatientDomain()) && Constants.lstVisitTypeInPatient().contains(bloodRequest.getMedicalVisit().get(0).getVisitType())
					&& !StringUtils.isNotNullAll(bloodRequest.getWardsNo(),
							bloodRequest.getWardsName(),
							bloodRequest.getBedNo())) {
				loggerBS008.error("Message:{}, validate:{}",
						poorin200901uv04.toString(), "住院时，非空字段验证未通过：病区编码："
								+ bloodRequest.getWardsNo() + "，病区名称："
								+ bloodRequest.getWardsName() + "，床号："
								+ bloodRequest.getBedNo());
				return false;
			}
		}
		// $[BUG]0009776 ADD END
		return true;
	}

	public boolean checkDependency(POORIN200901UV04 poorin200901uv04,
			boolean flag) {
        // 患者本地ID
        patientLid = bloodRequestBatch.getPatientLid();
        // [BUG]0006657 MODIFY END

		// 就诊次数
		String visitTimes = bloodRequestBatch
				.getMedicalVisit().get(0).getVisitTimes();
		logger.warn("就诊次数{}", visitTimes);
		// add by li_shenggen begin 2014/11/14 10:53
		// 就诊流水号
		String visitOrdNo = bloodRequestBatch.getMedicalVisit().get(0).getVisitOrdNo();
		// 就诊类型
		String visitTypeCode = bloodRequestBatch.getMedicalVisit().get(0).getVisitType();
		logger.warn("就诊流水号:{}, 就诊类型:{}", visitOrdNo,visitTypeCode);
		// end 
		// $Author :tong_meng
		// $Date : 2012/09/10 15:00$
		// [BUG]0009536 ADD BEGIN
		// $Author :jin_peng
		// $Date : 2012/08/24 15:59$
		// [BUG]0009149 ADD BEGIN
		// 针对消息内容相同都为新增情况下，如果已存在一条记录则其余不接入
		/*
		 * bloodRequestResult = bloodRequestService.getAdvicePresence(
		 * bloodRequestBatch.getPatientDomain(), patientLid,
		 * bloodRequestBatch.getOrderLid());
		 */

        // [BUG]0009149 ADD END
        // [BUG]0009536 ADD END

		// 就诊信息
		MedicalVisit medicalVisit = commonService.mediVisit(
				bloodRequestBatch.getPatientDomain(), patientLid, visitTimes,bloodRequestBatch.getOrganizationCode(),visitOrdNo,visitTypeCode);
		// 如果不存在，不能新增
		if (medicalVisit == null) {
			logger.error("MessageId:{};就诊表中不存在相应的数据，不可以新增！域ID:"
					+ bloodRequestBatch.getPatientDomain() + "患者本地ID:"
					+ patientLid + "就诊次数:" + visitTimes + "就诊流水号:" + visitOrdNo+ "就诊类型:" + visitTypeCode, poorin200901uv04
					.getMessage().getId());
			if (flag) {
				loggerBS008
						.error("Message:{},checkDependency:{}",
								poorin200901uv04.toString(),
								"就诊表中不存在相应的数据，不可以新增！域ID:"
										+ bloodRequestBatch.getPatientDomain()
										+ "患者本地ID:" + patientLid + "就诊次数:"
										+ visitTimes + "就诊流水号:" + visitOrdNo+ "就诊类型:" + visitTypeCode);
			}
			return false;
		}
		// 如果存在，可以新增
		else {
			// 获取DB中该新增输血单信息
			BloodRequest brtResult = bloodRequestService.getAdvicePresence(medicalVisit.getVisitSn(),
					bloodRequestBatch.getPatientDomain(),
					bloodRequestBatch.getPatientLid(),
					bloodRequestBatch.getRequestNo(),
					bloodRequestBatch.getOrganizationCode());

			// 如果新增输血单信息已存在，则不再做该消息处理
			if (brtResult != null) {
				// $Author :jin_peng
				// $Date : 2012/09/03 13:46$
				// [BUG]0009333 MODIFY BEGIN
				logger.warn(
						"该新增输血申请信息已存在,不再进行处理! {}",
						"params: patientLid = "
								+ bloodRequestBatch.getPatientLid()
								+ "; patientDomain = "
								+ bloodRequestBatch.getPatientDomain()
								+ "; requestNo = "
								+ bloodRequestBatch.getRequestNo()
								+ "; visitSn = "
								+ medicalVisit.getVisitSn());

                // 设置已存在该记录
                isNewExists = true;
			}
			// 取出就診表中的就診內部序列號
			visitSn = medicalVisit.getVisitSn();
			// 患者內部序列號
			patientSn = medicalVisit.getPatientSn();
			logger.debug("就诊表中的就诊内部序列号   {}  ，患者内部序列号    {} ", visitSn,
					patientSn);
			logger.debug("就诊表中存在相关的数据，可以新增！");
			return true;
		}
		// $Author:wei_peng
		// $Date:2013/3/21 14:41
		// [BUG]0014679 DELETE BEGIN
		// 如果是 update , 查询存不存在这条输血申请单
		/*
		 * else { // 根据条件查询要更新的输血申请单 bloodRequestResult =
		 * bloodRequestService.getAdvicePresence(
		 * bloodRequestBatch.getPatientDomain(), patientLid,
		 * bloodRequestBatch.getRequestNo()); logger.debug("用血申请单内部序列号",
		 * bloodRequestResult.getRequestSn());
		 * 
		 * // $Author :tong_meng // $Date : 2012/09/10 15:00$ // [BUG]0009536
		 * ADD BEGIN logger.debug("患者域ID{},本地医嘱号{}",
		 * bloodRequestBatch.getPatientDomain(),
		 * bloodRequestBatch.getOrderLid()); // [BUG]0009536 ADD END
		 * 
		 * // 不存在 if (bloodRequestResult == null) {
		 * logger.error("要更新的用血申请单不存在，不能更新！"); return false; } // 存在 else {
		 * logger.debug("要更新的用血申请单存在，可以更新！"); return true; } }
		 */
		// [BUG]0014679 DELETE END
	}

    /**
     * 
     * @param poorin200901uv04
     */
    @Transactional
    public void saveOrUpdate(POORIN200901UV04 poorin200901uv04)
    {
        // $Author: tong_meng
        // $Date: 2013/8/30 15:00
        // [BUG]0036757 ADD BEGIN
        serviceId = poorin200901uv04.getMessage().getServiceId();
        // [BUG]0036757 ADD END
        
        // $Author :jin_peng
        // $Date : 2012/09/03 13:46$
        // [BUG]0009333 ADD BEGIN
        // 判断如果新增记录标识为true则直接标识该消息已经执行成功，不做任何操作
        if (isNewExists)
        {
            return;
        }
        // [BUG]0009333 ADD END

        systemDate = DateUtils.getSystemTime();
        // 得到要更新或者保存的用血申请单的实体类
        BloodRequest bloodRequest = getBloodRequest();
        // 取血单特殊要求
        List<Object> bloodSpeRequestList = getSpeRequestList(bloodRequest);
        // 输血申请检验项目
        List<Object> bloodLabResultList = getBloodLabResultList(bloodRequest);
        // 保存用血申请和血液特殊要求
        bloodRequestService.saveBlood(bloodRequest, bloodSpeRequestList,
                bloodLabResultList);
        // $Author:wei_peng
        // $Date:2013/3/21 14:41
        // [BUG]0014679 DELETE BEGIN
        /*
         * else { List<BloodSpecialRequest> specialRequestList =
         * getSpecialRequestList(bloodRequest);
         * 
         * List<BloodLabResult> bloodLabResultListForDelete =
         * getBloodLabResultDeleteList(bloodRequest); // 更新用血申请和血液特殊要求
         * bloodRequestService.updateBlood(bloodRequest, bloodSpeRequestList,
         * specialRequestList, bloodLabResultList, bloodLabResultListForDelete);
         * }
         */
        // [BUG]0014679 DELETE END
    }

    /**
     * 血液特殊要求集合
     * @param bloodRequestResult
     * @return
     */
    private List<Object> getSpeRequestList(BloodRequest bloodRequest)
    {
        // $Author :tongmeng
        // $Date : 2012/5/23 14:45$
        // [BUG]0006609 DELETE BEGIN

        // 根据申请单内部序列号找到血液特殊要求集合
        // List<BloodSpecialRequest> specialRequestList =
        // bloodRequestService.getSpecialRequest(bloodRequest.getRequestSn());

        // [BUG]0006609 MODIFY END

        // 用来封装血液特殊要求的集合
        List<Object> speRequestList = new ArrayList<Object>();
        // 消息中血液特殊要求数据
        List<SpecialRequest> specialRequests = bloodRequestBatch.getSpecialRequest();
        // 遍历每一个条数据
        for (int i = 0; i < specialRequests.size(); i++)
        {
            SpecialRequest specialRequest = specialRequests.get(i);

            // 得到血液特殊要求实体
            BloodSpecialRequest speRequest = getSpecialRequest(bloodRequest,
                    specialRequest);
            // 将血液特殊要求添加到集合中
            speRequestList.add(speRequest);
        }
        return speRequestList;
    }

    /**
     * 获取输血申请检验项目集合
     * @param bloodRequest
     * @return
     */
    private List<Object> getBloodLabResultList(BloodRequest bloodRequest)
    {
        List<Object> bloodLabResultList = new ArrayList<Object>();
        // 消息中的输血检验结果集合
        List<LabResults> labResults = bloodRequestBatch.getLabResults();
        for (LabResults labResult : labResults)
        {
            BloodLabResult bloodLabResult = new BloodLabResult();
            // 输血申请单内部序列号
            bloodLabResult.setRequestSn(bloodRequest.getRequestSn());
            // 检验项目编码
            bloodLabResult.setItemCode(labResult.getLabResultCode());
            if (!StringUtils.isEmpty(labResult.getLabResultUnit()))
            {
                // 检验项目值
                bloodLabResult.setItemValue(labResult.getLabResultName());
                // 检验项目单位
                bloodLabResult.setItemUnit(labResult.getLabResultUnit());
            }
            else if (!StringUtils.isEmpty(labResult.getLabResultName()))
            {
                // 检验结果
                bloodLabResult.setItemResult(labResult.getLabResultName());
            }    // add by li_shenggen begin 2014/11/12
            else
            {
                // 检验结果（血型检验时使用）
                bloodLabResult.setItemResult(labResult.getLabResultNameBlood());
                // add by li_shenggen begin 2014/11/12
                // 验血日期
                bloodLabResult.setItemResultDate(DateUtils.stringToDate(labResult.getLabResultDate()));
                //end
            }
            // 创建时间
            bloodLabResult.setCreateTime(systemDate);
            // 更新时间
            bloodLabResult.setUpdateTime(systemDate);
            // 更新回数
            bloodLabResult.setUpdateCount(Constants.INSERT_UPDATE_COUNT);
            // 删除标志
            bloodLabResult.setDeleteFlag(Constants.NO_DELETE_FLAG);
            
            // $Author: tong_meng
            // $Date: 2013/8/30 15:00
            // [BUG]0036757 ADD BEGIN
            bloodLabResult.setCreateby(serviceId); // 设置创建人
            // [BUG]0036757 ADD END
            
            bloodLabResultList.add(bloodLabResult);
        }
        return bloodLabResultList;
    }

    /**
     * 根据业务需要保存血液特殊要求表
     * @param bloodRequest 
     * @param specialRequest    
     * @return
     */
    private BloodSpecialRequest getSpecialRequest(BloodRequest bloodRequest,
            SpecialRequest specialRequest)
    {
        BloodSpecialRequest bloodSpecialRequest = new BloodSpecialRequest();
        // 用血申请内部序列号
        bloodSpecialRequest.setRequestSn(bloodRequest.getRequestSn());
        // $Author :tong_meng
        // $Date : 2012/5/22 17:30$
        // [BUG]0006235 MODIFY BEGIN
        // 血液特殊要求
        bloodSpecialRequest.setSpecialRequest(specialRequest.getSpecialRequestName());
        // [BUG]0006235 MODIFY END
        // 输血特殊要求编码
        bloodSpecialRequest.setSpecialRequestCode(specialRequest.getSpecialRequestCode());
        // 创建时间
        bloodSpecialRequest.setCreateTime(systemDate);
        // 更新时间
        bloodSpecialRequest.setUpdateTime(systemDate);
        // 设置更新回数
        bloodSpecialRequest.setUpdateCount(Constants.INSERT_UPDATE_COUNT);
        // 删除标志
        bloodSpecialRequest.setDeleteFlag(Constants.NO_DELETE_FLAG);
        
        // $Author: tong_meng
        // $Date: 2013/8/30 15:00
        // [BUG]0036757 ADD BEGIN
        bloodSpecialRequest.setCreateby(serviceId); // 设置创建人
        // [BUG]0036757 ADD END

        return bloodSpecialRequest;
    }

    /**
     * 将要保存或者更新的血液申请单封装到实体类中 
     * @param bloodRequestsXml
     * @param medicalVisit
     * @param bloodRequestResult
     * @param newUpFlag
     * @return  返回封装的实体类
     */
    private BloodRequest getBloodRequest()
    {
        // 用血申请单存在数据，更新用血申请单表
        BloodRequest bloodRequests = new BloodRequest();
        // 用血申请单内部序列号
        requestSn = commonService.getSequence("SEQ_REQUEST");
        bloodRequests.setRequestSn(requestSn);
        // 就诊内部序列号
        bloodRequests.setVisitSn(visitSn);
        // 患者内部序列号
        bloodRequests.setPatientSn(patientSn);
        // 更新回数
        bloodRequests.setUpdateCount(Constants.INSERT_UPDATE_COUNT);
        // 创建时间
        bloodRequests.setCreateTime(systemDate);
        // 更新时间
        bloodRequests.setUpdateTime(systemDate);
        // 删除标志
        bloodRequests.setDeleteFlag(Constants.NO_DELETE_FLAG);
        
        // $Author: tong_meng
        // $Date: 2013/8/30 15:00
        // [BUG]0036757 ADD BEGIN
        bloodRequests.setCreateby(serviceId); // 设置创建人
        // [BUG]0036757 ADD END
        
        // 域代码
        bloodRequests.setPatientDomain(bloodRequestBatch.getPatientDomain());
        // 患者本地ID
        bloodRequests.setPatientLid(patientLid);

        // $Author :tong_meng
        // $Date : 2012/09/10 15:00$
        // [BUG]0009536 ADD BEGIN
        // 本地医嘱号
        bloodRequests.setOrderLid(bloodRequestBatch.getOrderLid());
        // [BUG]0009536 ADD END

        // 申请日期
        bloodRequests.setRequestDate(DateUtils.stringToDate(bloodRequestBatch.getRequestDate()));
        // 申请单编号
        bloodRequests.setRequestNo(bloodRequestBatch.getRequestNo());

        // $Author :tong_meng
        // $Date : 2012/6/25 14:33$
        // [BUG]0007418 MODIFY BEGIN
        // 血液品种代码
        bloodRequests.setBloodClassCode(bloodRequestBatch.getBloodClass());
        // [BUG]0007418 MODIFY END

        // $Author :tong_meng
        // $Date : 2012/6/25 14:33$
        // [BUG]0007418 ADD BEGIN
        // 血液品种名称
        bloodRequests.setBloodClassName(bloodRequestBatch.getBloodClassName());
        // [BUG]0007418 ADD END

        // Author :jin_peng
        // Date : 2012/11/14 14:56
        // [BUG]0011478 ADD BEGIN
        String orderStatus = null;
        String orderStatusName = null;

        // 门诊医嘱状态为已下达
        if (Constants.PATIENT_DOMAIN_OUTPATIENT.equals(bloodRequestBatch.getPatientDomain()))
        {
            orderStatus = Constants.ORDER_STATUS_ISSUE;

            orderStatusName = DictionaryCache.getDictionary(
                    Constants.DOMAIN_ORDER_STATUS).get(
                    Constants.ORDER_STATUS_ISSUE);
        }
        // 住院医嘱状态为已确认
        else
        {
            orderStatus = Constants.ORDER_STATUS_VALIDATED;

            orderStatusName = DictionaryCache.getDictionary(
                    Constants.DOMAIN_ORDER_STATUS).get(
                    Constants.ORDER_STATUS_VALIDATED);
        }

        // 设置医嘱状态
        bloodRequests.setOrderStatusCode(orderStatus);

        // 设置医嘱状态名称
        bloodRequests.setOrderStatusName(orderStatusName);

        // [BUG]0011478 ADD END

        // 输血量
        bloodRequests.setQuantity(StringUtils.strToBigDecimal(
                bloodRequestBatch.getAmount(), "输血量"));

        // 输血量单位
        bloodRequests.setQuantityUnit(bloodRequestBatch.getUnit());

        // $Author :tong_meng
        // $Date : 2012/6/26 10:33$
        // [BUG]0007418 MODIFY BEGIN
        // 输血目的编码
        bloodRequests.setBloodReasonCode(bloodRequestBatch.getBloodReasonCode());
        // [BUG]0007418 MODIFY END

        // $Author :tong_meng
        // $Date : 2012/6/26 10:33$
        // [BUG]0007418 ADD BEGIN
        // 输血目的描述
        bloodRequests.setBloodReasonName(bloodRequestBatch.getBloodReasonName());
        // [BUG]0007418 ADD END

        // 输血日期
        bloodRequests.setTransfusionDate(DateUtils.stringToDate(bloodRequestBatch.getTransfusionDate()));

        // $Author :tong_meng
        // $Date : 2012/6/26 10:33$
        // [BUG]0007418 MODIFY BEGIN
        // 申请科室编码
        bloodRequests.setRequestDeptId(bloodRequestBatch.getRequestDept());
        // [BUG]0007418 MODIFY END

        // $Author :tong_meng
        // $Date : 2012/6/26 10:33$
        // [BUG]0007418 ADD BEGIN
        // 申请科室名称
        bloodRequests.setRequestDeptName(bloodRequestBatch.getRequestDeptName());
        // [BUG]0007418 ADD END

        // $Author: wei_peng
        // $Date: 2012/7/26 17:40
        // [BUG]0006101 ADD BEGIN
        // 描述
        bloodRequests.setDescription(bloodRequestBatch.getDescription());
        // ABO血型代码
        bloodRequests.setBloodAboCode(bloodRequestBatch.getBloodABOCode());
        // ABO血型名称
        bloodRequests.setBloodAboName(bloodRequestBatch.getBloodABOName());
        // Rh血型代码
        bloodRequests.setBloodRhCode(bloodRequestBatch.getBloodRhCode());
        // Rh血型名称
        bloodRequests.setBloodRhName(bloodRequestBatch.getBloodRhName());
        // [BUG]0006101 ADD END

        // $Author: wei_peng
        // $Date:2012/8/14 15:21
        // [BUG]0008311 ADD BEGIN
        // 确认人ID
        bloodRequests.setConfirmPerson(bloodRequestBatch.getConfirmPerson());
        // 确认人姓名
        bloodRequests.setConfirmPersonName(bloodRequestBatch.getConfirmPersonName());
        // 确认时间
        bloodRequests.setConfirmTime(DateUtils.stringToDate(bloodRequestBatch.getConfirmTime()));
        // [BUG]0008311 ADD END

        // 病人病史信息循环
        for (Histories history : bloodRequestBatch.getHistories())
        {
            if (Constants.PREGNANCY_CODE.equals(history.getHistoryCode()))
            {
                // 是否怀孕
                bloodRequests.setPregnancyFlag(Constants.TRUE_FLAG.equals(history.getHistory()) ? Constants.T_FLAG_VALUE
                        : Constants.F_FLAG_VALUE);
                // 怀孕次数
                bloodRequests.setPregnancyCount(history.getHistoryTime());
            }
            else if (Constants.CHILDBIRTH_CODE.equals(history.getHistoryCode()))
            {
                // 是否生产
                bloodRequests.setChildbirthFlag(Constants.TRUE_FLAG.equals(history.getHistory()) ? Constants.T_FLAG_VALUE
                        : Constants.F_FLAG_VALUE);
                // 生产次数
                bloodRequests.setChildbirthCount(history.getHistoryTime());
            }
            else if (Constants.HISTORY_CODE.equals(history.getHistoryCode()))
            {
                // 是否输血史
                bloodRequests.setHistoryFlag(history.getHistoryTime());
            }
            else if (Constants.ADVERSE_HISTORY_CODE.equals(history.getHistoryCode()))
            {
                // 是否不良输血史
                bloodRequests.setAdverseHistoryFlag(history.getHistoryTime());
            }
            // $Author: tong_meng
            // $Date:2012/10/16 16:30
            // [BUG]0008311 ADD BEGIN
            else if (Constants.NEW_BORN_CODE.equals(history.getHistoryCode()))
            {
                // 是否是新生儿
                bloodRequests.setNewbornFlag(Constants.TRUE_FLAG.equals(history.getHistory()) ? Constants.T_FLAG_VALUE
                        : Constants.F_FLAG_VALUE);
            }
            else if (Constants.OTHER_DISEASE_CODE.equals(history.getHistoryCode()))
            {
                // 是否其他系统疾病
                bloodRequests.setOtherDiseaseFlag(Constants.TRUE_FLAG.equals(history.getHistory()) ? Constants.T_FLAG_VALUE
                        : Constants.F_FLAG_VALUE);
                // 疾病名称
                bloodRequests.setOtherDiseaseName(history.getHistoryTime());
            }
            // [BUG]0008311 ADD END
            // $Author:wei_peng
            // $Date:2012/12/12 11:20
            // [BUG]0012376 ADD BEGIN
            else if (Constants.SELF_BLOOD_CODE.equals(history.getHistoryCode()))
            {
                // 是否自采血
                bloodRequests.setSelfBloodFlag(Constants.TRUE_FLAG.equals(history.getHistory()) ? Constants.T_FLAG_VALUE
                        : Constants.F_FLAG_VALUE);
            }
            // [BUG]0012376 ADD END
            // add by li_shenggen begin 2014/11/13
            else if (Constants.CROSS_MATCH_BLOOD_CODE.equals(history.getHistoryCode()))
            {
            	// 是否交叉配血
                bloodRequests.setCrossMatchBloodFlag(Constants.TRUE_FLAG.equals(history.getHistory()) ? Constants.T_FLAG_VALUE
                        : Constants.F_FLAG_VALUE);
                bloodRequests.setCrossMatchBloodName(history.getHistoryTime());
            }
            else if (Constants.RESERVE_CODE.equals(history.getHistoryCode()))
            {
                // 是否备用
                bloodRequests.setReserveFlag(Constants.TRUE_FLAG.equals(history.getHistory()) ? Constants.T_FLAG_VALUE
                        : Constants.F_FLAG_VALUE);
                bloodRequests.setReserveName(history.getHistoryTime());                
            }
            else if (Constants.STEM_CELL_TRANSPLANT_CODE.equals(history.getHistoryCode()))
            {
                // 是否干细胞移植后循环
                bloodRequests.setStemCellTransplantFlag(Constants.TRUE_FLAG.equals(history.getHistory()) ? Constants.T_FLAG_VALUE
                        : Constants.F_FLAG_VALUE);
                bloodRequests.setStemCellTransplantName(history.getHistoryTime());                
            }
            // end
        }

        // 申请医生
        bloodRequests.setRequestPersonId(bloodRequestBatch.getRequestPerson());
        // 申请医生姓名
        bloodRequests.setRequestPersonName(bloodRequestBatch.getRequestPersonName());
        // 紧急程度
        bloodRequests.setUrgencyCode(bloodRequestBatch.getUrgency());
        // 紧急程度名称
        bloodRequests.setUrgencyName(bloodRequestBatch.getUrgencyName());

		// $Author:wei_peng
		// $Date:2013/3/25 10:51
		// [BUG]0014719 ADD BEGIN
		bloodRequests
				.setClinicalDiagnosis(getClinicalDiagnosis(bloodRequestBatch));
		// [BUG]0014719 ADD END
		// 医疗机构编码
		bloodRequests.setOrgCode(bloodRequestBatch.getOrganizationCode());
		// 医疗机构名称
		bloodRequests.setOrgName(bloodRequestBatch.getOrganizationName());
		return bloodRequests;
	}

    // $Author:wei_peng
    // $Date:2013/3/25 11:03
    // [BUG]0014719 ADD BEGIN
    // 使用临床诊断节点中的疾病名称项拼接临床诊断信息
    private String getClinicalDiagnosis(BloodRequestBatch bloodRequestBatch)
    {
        List<Diagnosis> clinicalDiagnosis = bloodRequestBatch.getDiagnosis();
        StringBuffer clinicalDiagnosisStr = new StringBuffer();
        if (clinicalDiagnosis != null && clinicalDiagnosis.size() > 0)
        {
            for (int i = 0; i < clinicalDiagnosis.size(); i++)
            {
                clinicalDiagnosisStr.append(clinicalDiagnosis.get(i).getDiseaseName());
                if (i != clinicalDiagnosis.size() - 1)
                {
                    clinicalDiagnosisStr.append("，");
                }
            }
        }
        return clinicalDiagnosisStr.toString();
    }
    // [BUG]0014719 ADD END

}
