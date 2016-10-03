package com.yly.cdr.batch.writer;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.yly.cdr.batch.writer.common.CommonWriterUtils;
import com.yly.cdr.cache.DictionaryCache;
import com.yly.cdr.core.Constants;
import com.yly.cdr.entity.CodeChargeItem;
import com.yly.cdr.entity.MedicalVisit;
import com.yly.cdr.entity.MedicationOrder;
import com.yly.cdr.entity.PatientDoctor;
import com.yly.cdr.entity.Prescription;
import com.yly.cdr.entity.TreatmentOrder;
import com.yly.cdr.hl7.dto.TreatmentItem;
import com.yly.cdr.hl7.dto.poorin200901uv12.POORIN200901UV12;
import com.yly.cdr.service.CommonService;
import com.yly.cdr.service.TreatmentService;
import com.yly.cdr.util.DataMigrationUtils;
import com.yly.cdr.util.DateUtils;
import com.yly.cdr.util.DictionaryUtils;
import com.yly.cdr.util.StringUtils;

/**
 * 诊疗Writer 注意：这里需要根据业务，添加判断条件。如：
 * 1：如果当前消息是子消息，父消息如果存在，则保存子消息内容；如果父消息不存在，则不保存子消息内容。 2：如果是父消息，直接保存父消息内容。
 * 
 * @author zhangbin
 * @version 1.0
 */

@Component(value = "poorin200901uv12Writer")
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class POORIN200901UV12Writer implements BusinessWriter<POORIN200901UV12> {

	private static final Logger logger = LoggerFactory
			.getLogger(POORIN200901UV12Writer.class);

	private static final Logger loggerBS303 = LoggerFactory.getLogger("BS303");

	@Autowired
	private CommonService commonService;

	@Autowired
	private TreatmentService treatmentService;

	@Autowired
	private CommonWriterUtils commonWriterUtils;

	// 就诊内部序列号
	private BigDecimal visitSn;

	// 患者内部序列号
	private BigDecimal patientSn;

	// 就诊次数
	private String visitTimes;

	// 患者本地Id
	private String patientLid;

	// 诊疗医嘱
	private TreatmentOrder treatOrderEn;

	// 患者域ID
	private String patientDomain;

	// 更新新增标志
	private String newUpFlag;

	// 循环的医嘱List
	private List<TreatmentOrder> treatOrderList = new ArrayList<TreatmentOrder>();

	// 待更新的诊疗记录
	private List<TreatmentOrder> treatmentOrderListForUpdate = new ArrayList<TreatmentOrder>();

	// 待插入的诊疗记录
	private List<TreatmentOrder> treatmentOrderListForInsert = new ArrayList<TreatmentOrder>();

	// $Author :jin_peng
	// $Date:2013/02/06 13:38
	// [BUG]0013909 ADD BEGIN
	// 过滤已存在的医生集合
	private List<String> filterPatientDoctorList = new ArrayList<String>();

	// [BUG]0013909 ADD END

	// $Author:wei_peng
	// $Date:2012/11/06 10:46
	// [BUG]0011030 ADD BEGIN
	// 患者和医生关联记录结合
	private List<PatientDoctor> patientDoctorList = new ArrayList<PatientDoctor>();

	// [BUG]0011030 ADD END

	// $Author:wei_peng
	// $Date:2012/11/21 11:19
	// [BUG]0011774 ADD BEGIN
	// 待插入的处方记录
	private List<Prescription> prescriptions = new ArrayList<Prescription>();

	// 处方序列名称
	private static final String PRESCRIPTION_SEQUENCE = "SEQ_PRESCRIPTION";

	// [BUG]0011774 ADD END

	// 系统时间取得
	private Date sysdate = DateUtils.getSystemTime();

	private MedicalVisit medicalVisitResult;

	/**
	 * 数据校验
	 * 
	 * @param baseDto
	 */
	public boolean validate(POORIN200901UV12 poorin200901uv12) {
		// $Author :chang_xuewen
		// $Date : 2014/1/22 10:30$
		// [BUG]0042086 MODIFY BEGIN
		String headOrgCode = poorin200901uv12.getMessage().getOrgCode();
		String orgCode = poorin200901uv12.getOrgCode();

		/*
		 * if (!headOrgCode.equals(orgCode)) {
		 * loggerBS303.error("Message:{},validate:{}",
		 * poorin200901uv12.toString(), "消息体与消息头中医疗机构代码不相同。orgCode = " + orgCode
		 * + "; headOrgCode = " + headOrgCode);
		 * 
		 * return false; }
		 */
		if (StringUtils.isEmpty(poorin200901uv12.getOrgCode())) {
			// $Author :yang_mingjie
			// $Date : 2014/06/26 10:09$
			// [BUG]0045630 MODIFY BEGIN
			poorin200901uv12.setOrgCode(Constants.HOSPITAL_CODE);
			poorin200901uv12.setOrgName(Constants.HOSPITAL_NAME);
			// [BUG]0045630 MODIFY END
		}
		// [BUG]0042086 MODIFY END
		// $Author:wei_peng
		// $Date:2012/9/18 14:02
		// $[BUG]0009718 ADD BEGIN
		patientDomain = poorin200901uv12.getPatientDomain();
		// 保存校验结果
		List<String> validateList = null;
		// 测试用日志
		Map<String, String> logMap = null;

		// Author：jia_yanqing
		// Date:2013/4/10 14:52
		// [BUG]0014982 ADD BEGIN
		// 是否所有必填项都有值标志
		Boolean flag = true;
		// [BUG]0014982 ADD END

		if (isNewMessage(poorin200901uv12) || isUpdateMessage(poorin200901uv12)) {
			validateList = new ArrayList<String>();
			logMap = new HashMap<String, String>();

			// $Author:wei_peng
			// $Date:2012/9/18 17:14
			// $[BUG]0009673 ADD BEGIN
			// 住院时添加病人原科室ID和名称的校验
			// if (Constants.PATIENT_DOMAIN_INPATIENT.equals(patientDomain))
			/*
			 * Author：yu_yzh
			 * Date：2015/10/22 15:24
			 * 港大开立医嘱消息中无确认信息
			 * DELETE BEGIN
			 * */
/*			if (Constants.lstDomainInPatient().contains(patientDomain)
					&& Constants.lstVisitTypeInPatient().contains(
							poorin200901uv12.getVisitType())) {
				validateList.add(poorin200901uv12.getNurseConfirmPerson());
				logMap.put("确认护士编码不能为空！",
						poorin200901uv12.getNurseConfirmPerson());

				validateList.add(poorin200901uv12.getNurseConfirmPersonName());
				logMap.put("确认护士名称不能为空！",
						poorin200901uv12.getNurseConfirmPersonName());

				validateList.add(poorin200901uv12.getNurseConfirmTime());
				logMap.put("护士确认时间不能为空！",
						poorin200901uv12.getNurseConfirmTime());

			}*/
			/*
			 * 港大开立医嘱消息中无确认信息
			 * DELETE END
			 * */
			// $[BUG]0009673 ADD END

			// $Author:chunlin jing
			// $Date:2013/5/21 11:30
			// $[BUG]0032744 ADD BEGIN
			// 避免多条医嘱数据为空时只打印最后一条日志记录
			int seqNum = 0;
			// $[BUG]0032744 ADD END

			List<TreatmentItem> treatmentItemList = poorin200901uv12
					.getTreatmentItem();
			for (TreatmentItem treatmentItem : treatmentItemList) {
				seqNum++;

				// 未收费诊疗处方
				if (!"VER".equals(treatmentItem.getPaidFlag())) {
					validateList.add(treatmentItem.getOrderType());
					logMap.put("医嘱类别编码不能为空！" + seqNum,
							treatmentItem.getOrderType());

					validateList.add(treatmentItem.getOrderTime());
					logMap.put("开嘱时间不能为空！" + seqNum,
							treatmentItem.getOrderTime());
					
					validateList.add(treatmentItem.getOrderPerson());
					logMap.put("开嘱人ID不能为空！" + seqNum,
							treatmentItem.getOrderPerson());

					/*
					 * Author：yu_yzh
					 * Date：2015/10/22
					 * 港大名称信息不发送
					 * DELETE BEGIN
					 * */
					/*validateList.add(treatmentItem.getOrderPersonName());
					logMap.put("开嘱人姓名不能为空！" + seqNum,
							treatmentItem.getOrderPersonName());*/

					// 开立时治疗医嘱没有执行科室
					/*validateList.add(treatmentItem.getExecutiveDept());
					logMap.put("执行科室ID不能为空！" + seqNum,
							treatmentItem.getExecutiveDept());*/

					/*validateList.add(treatmentItem.getExecutiveDeptName());
					logMap.put("执行科室名称不能为空！" + seqNum,
							treatmentItem.getExecutiveDeptName());*/
					/*
					 * 港大名称信息不发送
					 * DELETE END
					 * */
					
					// $Author:wu_biao
					// $Date:2012/9/21 17:14
					// $[BUG]0009826 ADD BEGIN
					// validateList.add(treatmentItem.getSerialId());
					// logMap.put("包装序号不能为空！", treatmentItem.getSerialId());
					// $[BUG]0009826 ADD end

					validateList.add(treatmentItem.getItemCode());
					logMap.put("医嘱编码不能为空！" + seqNum,
							treatmentItem.getItemCode());

					// 北大人民
					// validateList.add(treatmentItem.getFormerDept());
					// logMap.put("病人原科室ID不能为空！" + seqNum,
					// treatmentItem.getFormerDept());
					//
					// validateList.add(treatmentItem.getFormerDeptName());
					// logMap.put("病人原科室名称不能为空！" + seqNum,
					// treatmentItem.getFormerDeptName());

					// 门诊时添加处方号校验
					// if
					// (Constants.PATIENT_DOMAIN_OUTPATIENT.equals(patientDomain))
//					if (Constants.lstDomainOutPatient().contains(patientDomain)
//							&& Constants.lstVisitTypeOutPatient().contains(
//									poorin200901uv12.getVisitType())) {
//						validateList.add(treatmentItem.getPrescriptionNo());
//						logMap.put("门诊时,诊疗处方号不能为空 ！" + seqNum,
//								treatmentItem.getPrescriptionNo());
//					}
					// $Author:wei_peng
					// $Date:2012/9/18 17:14
					// $[BUG]0009673 ADD BEGIN
					// 住院时添加病人原科室ID和名称的校验
					// if
					// (Constants.PATIENT_DOMAIN_INPATIENT.equals(patientDomain))
					/*
					 * Author: yu_yzh
					 * Date: 2015/10/22 15:43
					 * 港大开立医嘱无确认内容
					 * DELETE BEGIN
					 * */
/*					if (Constants.lstDomainInPatient().contains(patientDomain)
							&& Constants.lstVisitTypeInPatient().contains(
									poorin200901uv12.getVisitType())) {
						// 北大人民
						// validateList.add(treatmentItem.getFormerWard());
						// logMap.put("病人原病区ID不能为空！" + seqNum,
						// treatmentItem.getFormerWard());
						//
						// validateList.add(treatmentItem.getFormerWardName());
						// logMap.put("病人原病区名称不能为空！" + seqNum,
						// treatmentItem.getFormerWardName());

						validateList
								.add(treatmentItem.getDoctorConfirmPerson());
						logMap.put("医生确认人ID不能为空！" + seqNum,
								treatmentItem.getDoctorConfirmPerson());

						validateList.add(treatmentItem
								.getDoctorConfirmPersonName());
						logMap.put("医生确认人名称不能为空！" + seqNum,
								treatmentItem.getDoctorConfirmPersonName());

						validateList.add(treatmentItem.getDoctorConfirmTime());
						logMap.put("医生确认时间不能为空！" + seqNum,
								treatmentItem.getDoctorConfirmTime());

					}*/
					/*
					 * 港大开立医嘱无确认内容
					 * DELETE END
					 * */
					
					// $[BUG]0009673 ADD END
				}
				// 判断为已收费诊疗处方
//				else if ("VER".equals(treatmentItem.getPaidFlag())) {
//					if (!StringUtils.isNotNullAll(treatmentItem
//							.getPrescriptionNo())) {
//						loggerBS303.error("Message:{},validate:{}",
//								poorin200901uv12.toString(),
//								"非空字段验证未通过: 已收费处方号(PrescriptionNo) = "
//										+ treatmentItem.getPrescriptionNo());
//						return false;
//					}
//				}

			}
			// Author：jia_yanqing
			// Date:2013/4/10 14:52
			// [BUG]0014982 MODIFY BEGIN
			String[] str = new String[validateList.size()];
			flag = StringUtils.isNotNullAll(validateList.toArray(str));
			logger.debug("新增或更新时是否所有必填项是否都有值:{}", flag);
		}
		// [BUG]0014982 MODIFY END
		// 测试所用非空验证日志
		if (!flag) {
			// loggerBS303.error("Message:{},validate:",
			// poorin200901uv12.toString());
			//
			// Set<Entry<String, String>> entries = logMap.entrySet();
			// Iterator<Entry<String, String>> iterator = entries.iterator();
			//
			// while (iterator.hasNext())
			// {
			// Entry<String, String> entry = iterator.next();
			//
			// if (StringUtils.isEmpty(entry.getValue()))
			// {
			// loggerBS303.error(entry.getKey() + ",");
			// }
			// }

			// $Author:chunlin jing
			// $Date:2013/5/21 11:30
			// $[BUG]0032744 ADD BEGIN
			// 将validate没有通过的日志以一行信息输出
			StringBuilder validateBuilder = new StringBuilder();
			Set<Entry<String, String>> entries = logMap.entrySet();
			Iterator<Entry<String, String>> iterator = entries.iterator();
			while (iterator.hasNext()) {
				Entry<String, String> entry = iterator.next();
				if (StringUtils.isEmpty(entry.getValue())) {
					validateBuilder.append(entry.getKey() + ",");
				}
			}
			loggerBS303.error("Message:{},validate:{}",
					poorin200901uv12.toString(), validateBuilder.toString());
			// $[BUG]0032744 ADD END

		}

		return flag;
		// $[BUG]0009718 ADD END
	}

	@Override
	public boolean checkDependency(POORIN200901UV12 poorin200901uv12,
			boolean flag) {
		// $Author :tongmeng
		// $Date : 2012/5/29 14:30$
		// [BUG]0006657 MODIFY BEGIN
		// 患者本地ID
		patientLid = poorin200901uv12.getPatientLid();
		// [BUG]0006657 MODIFY END

		logger.info("诊疗消息更新场合,患者本地ID {}", patientLid);
		// 标志
		newUpFlag = poorin200901uv12.getNewUpFlg();
		// $Author:wei_peng
		// $Date:2012/9/24 13:30
		// $[BUG]0009863 MODIFY BEGIN
		// && Constants.PATIENT_DOMAIN_OUTPATIENT.equals(patientDomain))
		boolean isMedical =  checkMedicalVisit(poorin200901uv12, flag);
		if (!isMedical){return false;}
		if ((isDeleteMessage(poorin200901uv12)
				&& Constants.lstDomainOutPatient().contains(patientDomain) && Constants.lstVisitTypeOutPatient().contains(poorin200901uv12.getVisitType()))
								|| isUpdateMessage(poorin200901uv12)) {
			return checktreatOrderInfo(poorin200901uv12, flag);
		}
		// $[BUG]0009863 MODIFY END
		else if (isNewMessage(poorin200901uv12)) {
        	int count;
        	if((count = selectExistOrders(poorin200901uv12)) > 0){
        		logger.info("消息新增场合，数据库中存在{}条记录", count);
        	}

			// $Author:jia_yanqing
			// $Date:2012/7/24 11:30$
			// [BUG]0007996 ADD BEIGN
			String parentOrderLid = null;
			List<com.yly.cdr.hl7.dto.TreatmentItem> treatOrderItemList = poorin200901uv12
					.getTreatmentItem();
			MedicationOrder medicationOrder;
			// 检查与父医嘱的关联关系
			for (int i = 0; i < treatOrderItemList.size(); i++) {
				parentOrderLid = treatOrderItemList.get(i).getParentOrderLid();
				if (parentOrderLid != null && !"".equals(parentOrderLid)) {
					medicationOrder = checkParentOrder(parentOrderLid,
							poorin200901uv12);
					if (medicationOrder == null) {
						logger.error("MessageId:{};诊疗消息新增场合,关联的父医嘱不存在,父医嘱号 :"
								+ parentOrderLid + "，域ID：" + patientDomain
								+ "，患者本地ID：" + patientLid + "; orgCode = "
								+ poorin200901uv12.getOrgCode(),
								poorin200901uv12.getMessage().getId());

						if (flag) {
							loggerBS303.error("Message:{},checkDependency:{}",
									poorin200901uv12.toString(),
									"诊疗消息新增场合,关联的父医嘱不存在,父医嘱号 :"
											+ parentOrderLid + "，域ID："
											+ patientDomain + "，患者本地ID："
											+ patientLid + "; orgCode = "
											+ poorin200901uv12.getOrgCode());
						}
						return false;
					}
				}
			}
			// [BUG]0007996 ADD END
		}
		return true;
	}

	/**
	 * 消息类型为新增时，先查询数据库中有没有对应的记录，将已存在的记录放入treatOrderList中
	 * @author yu_yzh
	 * @return 返回数据库中存在的医嘱记录数
	 * */
	private int selectExistOrders(POORIN200901UV12 poorin200901uv12){
		List<TreatmentItem> treatmentItemList = poorin200901uv12.getTreatmentItem();
		int existOrderCount = 0;
		for(TreatmentItem treatmentItem : treatmentItemList){
			TreatmentOrder treatmentOrder = treatmentService.getOrder(patientDomain,
					patientLid, treatmentItem.getOrderLid(),
					poorin200901uv12.getOrgCode(),
					medicalVisitResult.getVisitSn(), null);
			if(treatmentOrder != null){
				treatOrderList.add(treatmentOrder);
				existOrderCount++;
			}
		}
		return existOrderCount;
	}
	
	/**
	 * 诊疗医嘱信息是否存在
	 * 
	 * @param poorin200901uv12
	 *            诊疗处置消息映射数据
	 * @return true:存在；false:不存在
	 */
	private boolean checktreatOrderInfo(POORIN200901UV12 poorin200901uv12,
			boolean flag) {
		
		List<TreatmentItem> treatOrderItemList = poorin200901uv12
				.getTreatmentItem();
		for (TreatmentItem treatOrderItem : treatOrderItemList) {
			// 未收费处方时
//			if (!"VER".equals(treatOrderItem.getPaidFlag())) {
				// 诊疗医嘱是否存在
				this.treatOrderEn = treatmentService.getOrder(patientDomain,
						patientLid, treatOrderItem.getOrderLid(),
						poorin200901uv12.getOrgCode(),
						medicalVisitResult.getVisitSn(),
						/*Integer.valueOf(treatOrderItem.getOrderSeqnum())*/
						// 港大没有医嘱序号
						null);
				if (treatOrderEn == null) {
					logger.error("MessageId:{};更新/删除时，诊疗记录不存在。诊疗医嘱本地ID："
							+ treatOrderItem.getOrderLid() + "域ID："
							+ patientDomain + "患者本地ID：" + patientLid
							+ "; orgCode = " + poorin200901uv12.getOrgCode()
							+ "; visitSn=" + medicalVisitResult.getVisitSn(),
							poorin200901uv12.getMessage().getId());
					if (flag) {
						loggerBS303.error(
								"Message:{},checkDependency:{}",
								poorin200901uv12.toString(),
								"更新/删除时，诊疗记录不存在。诊疗医嘱本地ID："
										+ treatOrderItem.getOrderLid() + "域ID："
										+ patientDomain + "患者本地ID："
										+ patientLid + "; orgCode = "
										+ poorin200901uv12.getOrgCode()
										+ "; visitSn="
										+ medicalVisitResult.getVisitSn());
					}
					return false;
				}
				if (treatOrderEn != null) {
					treatOrderList.add(treatOrderEn);
				}
//			}
		}
		return true;
	}

	/**
	 * 就诊相关信息判断
	 * 
	 * @param labOrderXml
	 *            诊疗处置消息映射数据
	 * @return true:存在；false:不存在
	 */
	private boolean checkMedicalVisit(POORIN200901UV12 poorin200901uv12,
			boolean flag) {
		// $Author:jia_yanqing
		// $Date:2012/7/24 11:30$
		// [BUG]0007996 MODIFY BEIGN
		// 就诊次数
		visitTimes = poorin200901uv12.getVisitTimes();
		// [BUG]0007996 MODIFY END
		// 就诊信息取得
		medicalVisitResult = commonService.mediVisit(patientDomain, patientLid,
				visitTimes, poorin200901uv12.getOrgCode(),
				poorin200901uv12.getVisitOrdNo(),
				poorin200901uv12.getVisitType());
		if (medicalVisitResult != null) {
			visitSn = medicalVisitResult.getVisitSn();
			patientSn = medicalVisitResult.getPatientSn();
			logger.info("诊疗消息新增场合,就诊内部序列号 {},患者内部序列号{}", visitSn, patientSn
					+ "; orgCode = " + poorin200901uv12.getOrgCode());
		} else {
			logger.error("MessageId:{};诊疗消息新增场合,就诊信息不存在,域ID：" + patientDomain
					+ "，患者本地ID：" + patientLid + "，就诊次数：" + visitTimes
					+ "; orgCode = " + poorin200901uv12.getOrgCode()
					+ "，就诊流水号：" + poorin200901uv12.getVisitOrdNo() + "，就诊类型："
					+ poorin200901uv12.getVisitType(), poorin200901uv12
					.getMessage().getId());
			if (flag) {
				loggerBS303.error("Message:{},checkDependency:{}",
						poorin200901uv12.toString(), "诊疗消息新增场合,就诊信息不存在,域ID："
								+ patientDomain + "，患者本地ID：" + patientLid
								+ "，就诊次数：" + visitTimes + "; orgCode = "
								+ poorin200901uv12.getOrgCode() + "，就诊流水号："
								+ poorin200901uv12.getVisitOrdNo() + "，就诊类型："
								+ poorin200901uv12.getVisitType());
			}
			return false;
		}
		return true;
	}

	// $Author:jia_yanqing
	// $Date:2012/7/24 11:30$
	// [BUG]0007996 ADD BEIGN
	/**
	 * 检查父医嘱的关联关系
	 * 
	 * @param parentOrderLid
	 * @return MedicationOrder
	 */
	public MedicationOrder checkParentOrder(String parentOrderLid,
			POORIN200901UV12 poorin200901uv12) {
		MedicationOrder medicationOrder = null;
		Map<String, Object> condition = new HashMap<String, Object>();
		condition.put("patientDomain", patientDomain);
		condition.put("patientLid", patientLid);
		condition.put("orderLid", parentOrderLid);
		condition.put("orgCode", poorin200901uv12.getOrgCode());
		condition.put("visitSn", medicalVisitResult.getVisitSn());
		condition.put("deleteFlag", Constants.NO_DELETE_FLAG);
		List medicationOrderList = new ArrayList<MedicationOrder>();
		medicationOrderList = commonService.selectByCondition(
				MedicationOrder.class, condition);
		if (medicationOrderList != null && !medicationOrderList.isEmpty()) {
			medicationOrder = (MedicationOrder) medicationOrderList.get(0);
		}
		return medicationOrder;
	}

	// [BUG]0007996 ADD END

	@Override
	@Transactional
	public void saveOrUpdate(POORIN200901UV12 poorin200901uv12) {
		// Author:jia_yanqing
		// Date:2013/3/4 15:40
		// [BUG]0014140 MODIFY BEGIN
		// $Author:wei_peng
		// $Date:2012/9/24 13:30
		// $[BUG]0009863 MODIFY BEGIN
		// &&
		// Constants.PATIENT_DOMAIN_OUTPATIENT.equals(poorin200901uv12.getPatientDomain()))
//		if (Constants.NEW_MESSAGE_FLAG.equals(newUpFlag)
//				&& (Constants.lstDomainOutPatient().contains(
//						poorin200901uv12.getPatientDomain()) && Constants
//						.lstVisitTypeOutPatient().contains(
//								poorin200901uv12.getVisitType()))) {
//			// 获取已退费的处方列表
//			List<Prescription> prescriptionListForReturnFee = getReturnFeePrescription(
//					poorin200901uv12.getTreatmentItem(), poorin200901uv12,
//					medicalVisitResult.getVisitSn());
//			// 被退费有处方关联的诊疗处置医嘱
//			List<TreatmentOrder> treatmentOrderListForReturnFee = getTreatmentOrderForReturnFee(prescriptionListForReturnFee);
//			treatmentService.deleteForReturnFee(prescriptionListForReturnFee,
//					treatmentOrderListForReturnFee);
//			checktreatOrderInfo(poorin200901uv12, false);
//			updateAndInsertTreatmentOrder(poorin200901uv12);
//		}
		// [BUG]0014140 MODIFY END
		// &&
		// Constants.PATIENT_DOMAIN_INPATIENT.equals(poorin200901uv12.getPatientDomain())
//		else 
		if (isNewMessage(poorin200901uv12)||
				isUpdateMessage(poorin200901uv12)) {
			insertTreatmentOrder(poorin200901uv12);
		}
		// $[BUG]0009863 MODIFY END
		// $Author:wei_peng
		// $Date:2012/9/18 14:27
		// $[BUG]0009718 ADD BEGIN
		// && Constants.PATIENT_DOMAIN_OUTPATIENT.equals(patientDomain)
		else if (isDeleteMessage(poorin200901uv12)
				&& (Constants.lstDomainOutPatient().contains(
						poorin200901uv12.getPatientDomain()) && Constants
						.lstVisitTypeOutPatient().contains(
								poorin200901uv12.getVisitType()))) {
			for (int i = 0; i < treatOrderList.size(); i++) {
				treatOrderList.get(i).setDeleteFlag(Constants.DELETE_FLAG);
				treatOrderList.get(i).setUpdateTime(sysdate);
				treatOrderList.get(i).setDeleteTime(sysdate);
				treatOrderList.get(i).setDeleteby(
						poorin200901uv12.getMessage().getServiceId());
			}
			commonService.updateList(treatOrderList);
		}
		// $[BUG]0009718 ADD END

		// $Author:wei_peng
		// $Date:2012/11/06 11:40
		// [BUG]0011030 ADD BEGIN
		if (isNewMessage(poorin200901uv12)) {
			for (PatientDoctor patientDoctor : patientDoctorList) {
				commonService.save(patientDoctor);
			}
		}
		// [BUG]0011030 ADD END
	}

	// Author:jia_yanqing
	// Date:2013/3/4 15:40
	// [BUG]0014140 ADD BEGIN
	// $Author:wei_peng
	/**
	 * 获取被退费的处方列表
	 * 
	 * @param prescriptionList
	 * @return
	 */
	private List<Prescription> getReturnFeePrescription(
			List<TreatmentItem> treatOrderItemList,
			POORIN200901UV12 poorin200901uv12, BigDecimal visitSn) {
		List<Prescription> prescriptionListForReturnFee = new ArrayList<Prescription>();
		Map<String, Object> prescriptionMap = new HashMap<String, Object>();
		for (TreatmentItem treatmentItem : treatOrderItemList) {
			Prescription prescription = null;
			// 判断为已收费处方
			if ("VER".equals(treatmentItem.getPaidFlag())) {
				prescriptionMap.put("patientDomain", patientDomain);
				prescriptionMap.put("patientLid", patientLid);
				prescriptionMap.put("prescriptionNo",
						treatmentItem.getPrescriptionNo());
				prescriptionMap.put("orgCode", poorin200901uv12.getOrgCode());
				prescriptionMap.put("visitSn", visitSn);
				prescriptionMap.put("deleteFlag", Constants.NO_DELETE_FLAG);
				// 根据相应条件获取数据库中对应处方记录
				List<Object> list = commonService.selectByCondition(
						Prescription.class, prescriptionMap);

				if (list != null && !list.isEmpty()) {
					prescription = (Prescription) list.get(0);
					prescriptionListForReturnFee.add(prescription);
				}
			}
		}
		return prescriptionListForReturnFee;
	}

	/**
	 * 获取已退费处方关联的诊疗医嘱并设置更新时间，删除标志，收费状态为已退费
	 * 
	 * @param prescriptionListForReturnFee
	 * @return
	 */
	private List<TreatmentOrder> getTreatmentOrderForReturnFee(
			List<Prescription> prescriptionListForReturnFee) {
		// 待删除的药物医嘱集合
		List<TreatmentOrder> treatmentListForReturnFee = new ArrayList<TreatmentOrder>();
		Map<String, Object> treatmentOrderMap = new HashMap<String, Object>();
		for (int i = 0; i < prescriptionListForReturnFee.size(); i++) {
			TreatmentOrder treatmentOrder = null;
			Prescription prescription = (Prescription) prescriptionListForReturnFee
					.get(i);
			treatmentOrderMap.put("prescriptionSn",
					prescription.getPrescriptionSn());
			treatmentOrderMap.put("deleteFlag", Constants.NO_DELETE_FLAG);
			// 根据相应条件获取数据库中对应药物医嘱记录
			List<Object> treatmentOrderList = commonService.selectByCondition(
					TreatmentOrder.class, treatmentOrderMap);
			if (treatmentOrderList != null && !treatmentOrderList.isEmpty()) {
				for (int j = 0; j < treatmentOrderList.size(); j++) {
					treatmentOrder = (TreatmentOrder) treatmentOrderList.get(j);
					treatmentListForReturnFee.add(treatmentOrder);
				}
			}
		}
		return treatmentListForReturnFee;
	}

	// [BUG]0014140 ADD END

	private void insertTreatmentOrder(POORIN200901UV12 poorin200901uv12) {
		setTreatMessage(poorin200901uv12);
		// 诊疗处置表新增
		commonService.saveList(treatmentOrderListForInsert);
		// 诊疗处置表更新
		commonService.updateList(treatmentOrderListForUpdate);
	}

	private void updateAndInsertTreatmentOrder(POORIN200901UV12 poorin200901uv12) {

		setTreatMessage(poorin200901uv12);
		// $Author:wei_peng
		// $Date:2012/11/21 11:19
		// [BUG]0011774 ADD BEGIN
		// 处方表新增
		commonService.saveList(prescriptions);
		// [BUG]0011774 ADD END
		// 诊疗处置表更新
		commonService.updateList(treatmentOrderListForUpdate);
		// 诊疗处置表新增
		commonService.saveList(treatmentOrderListForInsert);
	}

	private void setTreatMessage(POORIN200901UV12 poorin200901uv12) {
		TreatmentOrder treatOrder = null;
		List<TreatmentItem> treatOrderItemList = poorin200901uv12
				.getTreatmentItem();
		boolean isRepayMessage = isRepayMessage(treatOrderItemList);
		for (int i = 0; i < treatOrderItemList.size(); i++) {
				TreatmentOrder treatmentOrder = getTreatmentOrder(treatOrderItemList
						.get(i));
				if (treatmentOrder != null) {
					treatOrder = treatmentOrder;
				} else {
					treatOrder = new TreatmentOrder();
					// $Author:jia_yanqing
					// $Date:2012/7/24 11:30$
					// [BUG]0007996 ADD BEIGN
					BigDecimal parentSn = null;
					MedicationOrder medicationOrder = checkParentOrder(
							treatOrderItemList.get(i).getParentOrderLid(),
							poorin200901uv12);
					if (medicationOrder != null) {
						parentSn = medicationOrder.getOrderSn();
					}
					// 父医嘱内部序列号
					treatOrder.setParentOrderSn(parentSn);
					// [BUG]0007996 ADD END

					// 更新回数
					treatOrder.setUpdateCount(Constants.INSERT_UPDATE_COUNT);
					// 创建时间
					treatOrder.setCreateTime(sysdate);
					// 删除标志
					treatOrder.setDeleteFlag(Constants.NO_DELETE_FLAG);
					// 就诊内部序列号
					treatOrder.setVisitSn(visitSn);
					// 患者内部序列号
					treatOrder.setPatientSn(patientSn);
					// 域代码
					treatOrder.setPatientDomain(patientDomain);
					// 患者本地ID
					treatOrder.setPatientLid(patientLid);
					// 本地医嘱号
					treatOrder.setOrderLid(treatOrderItemList.get(i)
							.getOrderLid());
					// $Author:duxiaolan
					// $Date:2014/11/17
					// 江苏人民
					// 医嘱序号
					// 港大无医嘱序号
					/*treatOrder
							.setOrderSeqnum(Integer.valueOf(treatOrderItemList
									.get(i).getOrderSeqnum()));*/
					// 医嘱组套编码
					treatOrder.setOrderSetCode(poorin200901uv12
							.getOrderSetCode());
					// 医嘱描述
					treatOrder.setOrderDescribe(poorin200901uv12
							.getOrderDescribe());
					// $Author:wei_peng
					// $Date:2012/11/06 10:52
					// [BUG]0011030 ADD BEGIN
					if (treatOrderItemList.get(i).getOrderPerson() != null
							&& !treatOrderItemList.get(i).getOrderPerson()
									.isEmpty()) {
						PatientDoctor patientDoctor = commonWriterUtils
								.getPatientDoctor(visitSn, patientSn,
										treatOrderItemList.get(i)
												.getOrderPerson(),
										treatOrderItemList.get(i)
												.getOrderPersonName(), sysdate);
						if (patientDoctor != null) {
							// $Author:jin_peng
							// $Date:2013/02/06 13:38
							// [BUG]0013909 MODIFY BEGIN
							// 如果该医生未被处理则进行添加操作
							if (!commonWriterUtils.isExistsPatientDoctor(
									filterPatientDoctorList, visitSn,
									patientSn, treatOrderItemList.get(i)
											.getOrderPerson())) {
								// $Author :chang_xuewen
								// $Date : 2013/08/31 16:00$
								// [BUG]0036757 MODIFY BEGIN
								// 增加人
								patientDoctor.setCreateby(DataMigrationUtils.getDataMigration() + poorin200901uv12
										.getMessage().getServiceId());
								patientDoctorList.add(patientDoctor);
								// [BUG]0036757 MODIFY END
							}

							// [BUG]0013909 MODIFY END
						}
					}
					// [BUG]0011030 ADD END

					// $Author:wei_peng
					// $Date:2013/3/28 13:51
					// [BUG]0014679 MODIFY BEGIN
					// $Author:wei_peng
					// $Date:2012/11/06 16:11
					// [BUG]0011421 ADD BEGIN
					// 判断是否为退费重收的消息
				//	if (isRepayMessage) {
					if (Constants.lstDomainOutPatient().contains(patientDomain)
							&& Constants.lstVisitTypeOutPatient().contains(
									poorin200901uv12.getVisitType())){
						// 费用状态编码-04
						treatOrder
								.setChargeStatusCode(Constants.CHARGE_STATUS_CHARGED);
						// 费用状态名称-退费重收
						treatOrder.setChargeStatusName(DictionaryCache
								.getDictionary(Constants.ORDER_CHARGE_STATUS)
								.get(Constants.CHARGE_STATUS_CHARGED));
					} else {
						// 费用状态编码-01
						treatOrder
								.setChargeStatusCode(Constants.CHARGE_STATUS_NOTCHARGE);
						// 费用状态名称-未收费
						treatOrder.setChargeStatusName(DictionaryCache
								.getDictionary(Constants.ORDER_CHARGE_STATUS)
								.get(Constants.CHARGE_STATUS_NOTCHARGE));
					}
					// [BUG]0011421 ADD END
					// [BUG]0014679 MODIFY END

				}
				// 频率
				treatOrder.setExecutionFrequency(treatOrderItemList.get(i)
						.getExecutionFrequency());

				// $Author :tong_meng
				// $Date : 2012/6/29 14:33$
				// [BUG]0007418 ADD BEGIN
				// 频率名称
				treatOrder.setExecFreqName(DictionaryUtils.getNameFromDictionary(Constants.DOMAIN_MEDICINE_FREQ, treatOrderItemList.get(i)
						.getExecutionFrequency(), treatOrderItemList.get(i).getExecFreqName()));
				
				// [BUG]0007418 ADD END

				// $Author :tong_meng
				// $Date : 2012/6/29 14:33$
				// [BUG]0007418 MODIFY BEGIN
				// 项目编码
				treatOrder.setItemCode(treatOrderItemList.get(i).getItemCode());
				// [BUG]0007418 MODIFY END

				// $Author :tong_meng
				// $Date : 2013/1/16 14:00$
				// [BUG]0013110 MODIFY BEGIN
				// 项目名称
				treatOrder.setItemName(DictionaryUtils.getNameFromDictionary(
						Constants.DOMAIN_ORDER, treatOrderItemList.get(i).getItemCode(), treatOrderItemList.get(i).getItemName()));
				
				// [BUG]0013110 MODIFY END

				// $Author:wei_peng
				// $Date:2012/6/19 14:32$
				// [BUG]0007126 MODIFY BEIGN
				// $Author:jia_yanqing
				// $Date:2012/6/11 17:30$
				// [BUG]0006983 MODIFY BEIGN
				// 医嘱开立科室ID
				treatOrder.setOrderDept(treatOrderItemList.get(i)
						.getFormerDept());
				// [BUG]0006983 MODIFY END
				// [BUG]0007126 MODIFY END

				// $Author :tong_meng
				// $Date : 2012/6/29 10:33$
				// [BUG]0007418 ADD BEGIN
				// 医嘱开立科室名称
				treatOrder.setOrderDeptName(DictionaryUtils.getNameFromDictionary(
						Constants.DOMAIN_DEPARTMENT, treatOrderItemList.get(i).getFormerDept(), treatOrderItemList.get(i).getFormerDeptName()));
				
				// [BUG]0007418 ADD END
				// 下医嘱人ID
				treatOrder.setOrderPerson(treatOrderItemList.get(i)
						.getOrderPerson());
				// 医嘱开立人
				treatOrder.setOrderPersonName(DictionaryUtils.getNameFromDictionary(
						Constants.DOMAIN_STAFF, treatOrderItemList.get(i).getOrderPerson(), treatOrderItemList.get(i).getOrderPersonName()));
				
				// 数量
				treatOrder.setQuantity(StringUtils.strToBigDecimal(
						treatOrderItemList.get(i).getQuantity(), "数量"));
				// 单位
				treatOrder.setUnit(treatOrderItemList.get(i).getUnit());
				// 规格（从收费项目字典的规格中获取）
				if (!StringUtils.isEmpty(treatOrderItemList.get(i)
						.getItemCode())) {
					CodeChargeItem codeChargeItem = treatmentService
							.getSpecification(treatOrderItemList.get(i)
									.getItemCode());
					treatOrder.setSpecification(codeChargeItem == null ? null
							: codeChargeItem.getSpecification());
				}
				
				// $Author:jia_yanqing
				// $Date:2012/6/12 11:30$
				// [BUG]0006755 MODIFY BEIGN
				// 紧急程度
				// treatOrder.setUrgency(poorin200901uv12.getUrgency());
				// [BUG]0006755 MODIFY END

				// $Author:jia_yanqing
				// $Date:2012/6/11 11:30$
				// [BUG]0006983 MODIFY BEIGN
				// 病区ID
				treatOrder
						.setWardsId(poorin200901uv12.getWards());
				// [BUG]0006983 MODIFY END

				// $Author :tong_meng
				// $Date : 2012/6/29 10:33$
				// [BUG]0007418 ADD BEGIN
				
				treatOrder.setWardName(DictionaryUtils.getNameFromDictionary(
						Constants.DOMAIN_WARD, poorin200901uv12.getWards(), poorin200901uv12.getWardsName()));
				// [BUG]0007418 ADD END

				// Author:jia_yanqing
				// Date:2013/3/15 14:23
				// [BUG]0014590 MODIFY BEGIN
				// $Author:wei_peng
				// $Date:2012/10/16 10:57
				// [BUG]0010409 MODIFY BEGIN
				// 长期或临时
//				treatOrder.setTemporaryFlag(poorin200901uv12
//						.getTemporaryFlag(treatOrderItemList.get(i)
//								.getExecutionFrequency()));
				String tf = treatOrderItemList.get(i).getTemporaryFlag();
				treatOrder.setTemporaryFlag(Constants.LONG_TERM_FLAG.equals(tf) ? 
						Constants.LONG_TERM_FLAG : Constants.TEMPORARY_FLAG);
				// [BUG]0010409 MODIFY END
				// [BUG]0014590 MODIFY END
				// 更新时间
				treatOrder.setUpdateTime(sysdate);

				// $Author:jia_yanqing
				// $Date:2012/6/11 11:30$
				// [BUG]0006983 MODIFY BEIGN
				// $Author:wei_peng$
				// $Date:2012/5/21 11:30$
				// [BUG]0006253 ADD BEIGN
				// 确认人ID
				treatOrder.setNurseConfirmPerson(poorin200901uv12
						.getNurseConfirmPerson());
				// 确认人姓名
				treatOrder.setNurseConfirmPersonName(poorin200901uv12
						.getNurseConfirmPersonName());
				// 确认时间
				treatOrder.setNurseConfirmTime(DateUtils
						.stringToDate(poorin200901uv12.getNurseConfirmTime()));
				// [BUG]0006253 ADD END
				// [BUG]0006983 MODIFY END

				// $Author:jia_yanqing
				// $Date:2012/6/11 11:30$
				// [BUG]0006983 ADD BEIGN
				// 医生确认人
				treatOrder.setDoctorConfirmPerson(treatOrderItemList.get(i)
						.getDoctorConfirmPerson());
				// 医生确认人姓名
				treatOrder.setDoctorConfirmPersonName(treatOrderItemList.get(i)
						.getDoctorConfirmPersonName());
				// 医生确认时间
				treatOrder.setDoctorConfirmTime(DateUtils
						.stringToDate(treatOrderItemList.get(i)
								.getDoctorConfirmTime()));
				// 医嘱开始时间
				treatOrder.setOrderStartTime(DateUtils
						.stringToDate(treatOrderItemList.get(i)
								.getOrderStartTime()));
				// 医嘱结束时间
				treatOrder.setOrderEndTime(DateUtils
						.stringToDate(treatOrderItemList.get(i)
								.getOrderEndTime()));

				// $Author:wei_peng
				// $Date:2012/11/21 11:19
				// [BUG]0011774 ADD BEGIN
				String prescriptionNo = treatOrderItemList.get(i)
						.getPrescriptionNo();

				BigDecimal prescriptionSn = null;
				// 处方号不为空时（门诊时处方号不为空）
				if (!StringUtils.isEmpty(prescriptionNo)) {
					// 取消息中重复的处方号的处方序列号
					prescriptionSn = getPrescriptionSnFromMsg(prescriptionNo);
					if (prescriptionSn == null) {
						// 如果数据库中已存在该处方
						prescriptionSn = getPrescriptionSnFromDB(
								treatOrderItemList.get(i), poorin200901uv12,
								visitSn);
					}
					// 若关联的处方不存在
					if (prescriptionSn == null) {
						// 获取处方序列
						prescriptionSn = commonService
								.getSequence(PRESCRIPTION_SEQUENCE);
						Prescription prescription = new Prescription();
						prescription.setPrescriptionSn(prescriptionSn);
						prescription.setVisitSn(visitSn);
						prescription.setPatientDomain(patientDomain);
						prescription.setPatientLid(patientLid);
						prescription.setPatientSn(patientSn);
						prescription.setPrescriptionNo(prescriptionNo);
						// Author:jia_yanqing
						// Date:2012/12/20 15:05
						// [BUG]0012631 ADD BEGIN
						// 处方类型编码
						prescription
								.setPrescriptionTypeCode(Constants.PRESCRIPTION_TYPE_CODE_TREATMENT);
						// 处方类型名称
//						prescription
//								.setPrescriptionTypeName(DictionaryCache
//										.getDictionary(
//												Constants.DOMAIN_PRESCRIPTION_TYPE)
//										.get(Constants.PRESCRIPTION_TYPE_CODE_TREATMENT));
						// [BUG]0012631 ADD END
						prescription.setCreateTime(sysdate);
						prescription.setUpdateTime(sysdate);
						prescription
								.setUpdateCount(Constants.INSERT_UPDATE_COUNT);
						prescription.setDeleteFlag(Constants.NO_DELETE_FLAG);

						// Author:jia_yanqing
						// Date:2013/06/24 13:05
						// [BUG]0034075 DELETE BEGIN
						// prescription.setReviewPersonId(treatOrderItemList.get(i).getDoctorConfirmPerson());
						// prescription.setReviewPersonName(treatOrderItemList.get(
						// i).getDoctorConfirmPersonName());
						// prescription.setReviewTime(DateUtils.stringToDate(treatOrderItemList.get(
						// i).getDoctorConfirmTime()));
						// [BUG]0034075 DELETE END

						// Author:jia_yanqing
						// Date:2013/06/24 13:05
						// [BUG]0034075 ADD BEGIN
						prescription.setOrderTime(DateUtils
								.stringToDate(treatOrderItemList.get(i)
										.getOrderTime()));
						// [BUG]0034075 ADD END

						// $Author :tong_meng
						// $Date : 2013/7/31 15:50$
						// [BUG]0035541 MODIFY BEGIN
						prescription.setMedViewFlag(StringUtils
								.getConversionValue(treatOrderItemList.get(i)
										.getMedViewFlag()));
						// [BUG]0035541 MODIFY END
						// 增加人
						prescription.setCreateby(DataMigrationUtils.getDataMigration() + poorin200901uv12.getMessage()
								.getServiceId());
						prescription.setOrgCode(poorin200901uv12.getOrgCode());
						prescription.setOrgName(poorin200901uv12.getOrgName());
						prescriptions.add(prescription);
					}
					// 门诊时处方内部序列号
					treatOrder.setPrescriptionSn(prescriptionSn);
				}
				// [BUG]0011774 ADD END

				// [BUG]0006983 ADD END

				// $Author:jia_yanqing
				// $Date:2012/6/11 11:30$
				// [BUG]0006298 ADD BEIGN
				// 医嘱类型代码
				treatOrder.setOrderType(treatOrderItemList.get(i)
						.getOrderType());
				// 医嘱类型名称
				treatOrder.setOrderTypeName(DictionaryUtils.getNameFromDictionary(
						Constants.DOMAIN_ORDER_TYPE, treatOrderItemList.get(i).getOrderType(), treatOrderItemList.get(i).getOrderTypeName()));
				
				// [BUG]0006298 ADD END

				// 开嘱日期
				treatOrder
						.setOrderTime(DateUtils.stringToDate(treatOrderItemList
								.get(i).getOrderTime()));

				// 床号
				treatOrder.setBedNo(poorin200901uv12.getBedNo());
				// 执行科室
				treatOrder.setExecutiveDept(treatOrderItemList.get(i)
						.getExecutiveDept());
				
				// 执行科室名称
				treatOrder.setExecDeptName(DictionaryUtils.getNameFromDictionary(
						Constants.DOMAIN_DEPARTMENT, treatOrderItemList.get(i).getExecutiveDept(), treatOrderItemList.get(i).getExecutiveDeptName()));
				
				// Author :jin_peng
				// Date : 2012/11/14 14:56
				// [BUG]0011478 MODIFY BEGIN
				String orderStatus = null;
				String orderStatusName = null;

				/*
				 * Author: yu_yzh
				 * Date: 2015/10/22 16:21
				 * 港大门诊住院开立医嘱都为已下达
				 * MODIFY BEGIN
				 * */

				orderStatus = Constants.ORDER_STATUS_ISSUE;
				orderStatusName = DictionaryCache.getDictionary(
						Constants.DOMAIN_ORDER_STATUS).get(
						Constants.ORDER_STATUS_ISSUE);
				/*
				 * 港大门诊住院开立医嘱都为已下达
				 * MODIFY END
				 * */
				// Author:jia_yanqing
				// Date:2012/10/17 10:33$
				// [BUG]0010408 ADD BEGIN
				// 医嘱状态
				treatOrder.setOrderStatus(orderStatus);
				// 医嘱状态名称
				treatOrder.setOrderStatusName(orderStatusName);
				// [BUG]0010408 ADD BEGIN
				// [BUG]0011478 MODIFY END

				// $Author :tong_meng
				// $Date : 2013/7/31 15:50$
				// [BUG]0035541 MODIFY BEGIN
				// $Author:jia_yanqing
				// $Date:2012/7/24 14:30$
				// [BUG]0007996 ADD BEIGN
				// 是否加急
				treatOrder.setUrgentFlag(StringUtils
						.getConversionValue(treatOrderItemList.get(i)
								.getUrgentFlag()));
				// 是否药观
				treatOrder.setMedViewFlag(StringUtils
						.getConversionValue(treatOrderItemList.get(i)
								.getMedViewFlag()));
				// [BUG]0035541 MODIFY END
				// $Author :chang_xuewen
				// $Date : 2013/8/9 15:00$
				// [BUG]0035951 ADD BEGIN
				// 是否皮试
				treatOrder.setSkinTestFlag(StringUtils
						.getConversionValue(treatOrderItemList.get(i)
								.getSkinTestFlag()));
				// 是否适应
				treatOrder.setAdaptiveFlag(StringUtils
						.getConversionValue(treatOrderItemList.get(i)
								.getAdaptiveFlag()));
				// [BUG]0035951 ADD END
				// 嘱托信息
				treatOrder.setComments(treatOrderItemList.get(i).getComments());
				// 医嘱备注
				treatOrder.setMemo(treatOrderItemList.get(i).getMemo());
				// 互斥医嘱类型编码
				treatOrder.setMutexesOrderType(treatOrderItemList.get(i)
						.getMutexesOrderType());
				// 互斥医嘱类型名称
				treatOrder.setMutexesOrderTypeName(treatOrderItemList.get(i)
						.getMutexesOrderTypeName());
				treatOrder.setOrgCode(poorin200901uv12.getOrgCode());
				treatOrder.setOrgName(poorin200901uv12.getOrgName());
				// [BUG]0007996 ADD END
				// $Author :chang_xuewen
				// $Date : 2013/08/31 16:00$
				// [BUG]0036757 MODIFY BEGIN
				// 判断是否存在待更新的诊疗记录
				if (treatmentOrder != null) {
					// 更新人
					treatOrder.setUpdateby(poorin200901uv12.getMessage()
							.getServiceId());
					treatmentOrderListForUpdate.add(treatOrder);
				} else {
					// 创建人
					treatOrder.setCreateby(DataMigrationUtils.getDataMigration() + poorin200901uv12.getMessage()
							.getServiceId());
					treatmentOrderListForInsert.add(treatOrder);
				}
				// [BUG]0036757 MODIFY END
			}
	}

	// $Author:jia_yanqing
	// $Date:2012/6/11 20:30$
	// [BUG]0006983 ADD BEIGN
	/**
	 * 根据域ID，患者Lid,处方号获取处方内部序列号
	 * 
	 * @param treatmentItem
	 * @return 处方内部序列号
	 */
	public BigDecimal getPrescriptionSnFromDB(TreatmentItem treatmentItem,
			POORIN200901UV12 poorin200901uv12, BigDecimal visitSn) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("patientLid", patientLid);
		map.put("patientDomain", patientDomain);
		map.put("orgCode", poorin200901uv12.getOrgCode());
		map.put("deleteFlag", Constants.NO_DELETE_FLAG);
		map.put("prescriptionNo", treatmentItem.getPrescriptionNo());
		map.put("visitSn", visitSn);
		// 查找数据库中处方记录
		List<Object> prescriptionList = commonService.selectByCondition(
				Prescription.class, map);
		// 判断处方在数据库中是否存在
		if (prescriptionList != null && prescriptionList.size() > 0) {
			Prescription prescription = (Prescription) prescriptionList.get(0);
			return prescription.getPrescriptionSn();
		} else {
			return null;
		}
	}

	// [BUG]0006983 ADD END

	// Author:jia_yanqing
	// Date:2012/6/11 20:30$
	// [BUG]0034074 ADD BEIGN
	/**
	 * 如果该处方号在消息中有重复的，则返回对应的重复处方号的处方序列号
	 * 
	 * @param prescriptionNo
	 * @return
	 */
	public BigDecimal getPrescriptionSnFromMsg(String prescriptionNo) {
		for (int i = 0; i < prescriptions.size(); i++) {
			if (prescriptionNo.equals(prescriptions.get(i).getPrescriptionNo())) {
				return prescriptions.get(i).getPrescriptionSn();
			}
		}
		return null;
	}

	// [BUG]0034074 ADD END

	// $Author:wei_peng
	// $Date:2012/9/24 13:30
	// $[BUG]0009863 ADD BEGIN
	/**
	 * 查找对应的待更新的诊疗医嘱记录
	 * 
	 * @param treatmentItem
	 *            消息中一条诊疗医嘱信息
	 * @return 查找到对应待更新的那条诊疗医嘱
	 */
	private TreatmentOrder getTreatmentOrder(TreatmentItem treatmentItem) {
		for (TreatmentOrder treatmentOrder : treatOrderList) {
			// 通过医嘱号判断是否该条记录为待更新的医嘱记录
			if (treatmentOrder.getOrderLid() 
					.equals(treatmentItem.getOrderLid()) 
					// 港大没有医嘱序号
					/*&& treatmentOrder.getOrderSeqnum().toString().equals(treatmentItem.getOrderSeqnum())*/) {
				return treatmentOrder;
			}
		}
		return null;
	}

	// $[BUG]0009863 ADD END

	// 判断是否为退费重收消息
	private boolean isRepayMessage(List<TreatmentItem> treatOrderItemList) {
		for (TreatmentItem treatmentItem : treatOrderItemList) {
			// 若已付费标记中传递了处方号，即含有退费的信息，则该条信息为退费重收消息
			if ("VER".equals(treatmentItem.getPaidFlag())
					&& !StringUtils.isEmpty(treatmentItem.getPrescriptionNo())) {
				return true;
			}
		}
		return false;
	}
	
	private boolean isNewMessage(POORIN200901UV12 poorin200901uv12){
		return Constants.NEW_MESSAGE_FLAG.equals(poorin200901uv12.getNewUpFlg())
				|| Constants.V2_NEW_MESSAGE_FLAG.equals(poorin200901uv12.getNewUpFlg());
	}
	
	private boolean isUpdateMessage(POORIN200901UV12 poorin200901uv12){
		return Constants.UPDATE_MESSAGE_FLAG.equals(poorin200901uv12.getNewUpFlg())
				|| Constants.V2_UPDATE_MESSAGE_FLAG.equals(poorin200901uv12.getNewUpFlg());
	}
	
	private boolean isDeleteMessage(POORIN200901UV12 poorin200901uv12){
		return Constants.DELETE_MESSAGE_FLAG.equals(poorin200901uv12.getNewUpFlg())
				|| Constants.V2_CANCEL_MESSAGE_FLAG.equals(poorin200901uv12.getNewUpFlg());
	}
}
