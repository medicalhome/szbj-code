/**
 * [FUN]A01-016 护理医嘱
 * 
 * @version 1.0, 2012/04/09  20:23:00
 
 * @author liu_jingyang/wei_peng
 * @since 1.0
 */

package com.yly.cdr.batch.writer;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.yly.cdr.batch.writer.common.CommonWriterUtils;
import com.yly.cdr.core.Constants;
import com.yly.cdr.entity.CareOrder;
import com.yly.cdr.entity.MedicalVisit;
import com.yly.cdr.entity.MedicationOrder;
import com.yly.cdr.entity.PatientDoctor;
import com.yly.cdr.hl7.dto.poorin200901uv05.CareOrderBatch;
import com.yly.cdr.hl7.dto.poorin200901uv05.POORIN200901UV05;
import com.yly.cdr.service.CommonService;
import com.yly.cdr.util.DataMigrationUtils;
import com.yly.cdr.util.DateUtils;
import com.yly.cdr.util.DictionaryUtils;
import com.yly.cdr.util.StringUtils;

/**
 * 护理医嘱Writer 注意：这里需要根据业务，添加判断条件。如：
 * 1：如果当前消息是子消息，父消息如果存在，则保存子消息内容；如果父消息不存在，则不保存子消息内容。 2：如果是父消息，直
 * 
 * 接保存父消息内容。
 * 
 * @author liu_jingyang/wei_peng
 * @version 1.0
 */
@Component(value = "poorin200901uv05Writer")
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class POORIN200901UV05Writer implements BusinessWriter<POORIN200901UV05> {

	private static final Logger logger = LoggerFactory
			.getLogger(POORIN200901UV05Writer.class);

	private static final Logger loggerBS304 = LoggerFactory.getLogger("BS304");

	@Autowired
	private CommonService commonService;

	@Autowired
	private CommonWriterUtils commonWriterUtils;

	// 护理医嘱关联的就诊记录
	private MedicalVisit medicalVisit;

	// $Author :tongmeng
	// $Date : 2012/5/24 17:45$
	// [BUG]0007507 DELETE BEGIN
	// // $Author :tongmeng
	// // $Date : 2012/5/24 17:45$
	// // [BUG]0006667 ADD BEGIN
	// private String SUPPLIES_CARE_TYPY = "护理类";
	// // [BUG]0006667 ADD END
	// [BUG]0007507 DELETE END

	// 患者内部ID
	private String patientLid;

	// 域代码
	private String patientDomain;

	// 需要更新的护理记录
	private List<CareOrder> careOrdersForUpdate = new ArrayList<CareOrder>();
	
	// 待更新的诊疗记录
	private List<CareOrder> careOrderListForUpdate = new ArrayList<CareOrder>();

		// 待插入的诊疗记录
	private List<CareOrder> careOrderListForInsert = new ArrayList<CareOrder>();

	// 系统的当前时间
	private Date systemTime = DateUtils.getSystemTime();

	// $Author :jin_peng
	// $Date:2013/02/06 13:38
	// [BUG]0013909 ADD BEGIN
	// 过滤已存在的医生集合
	private List<String> filterPatientDoctorList = new ArrayList<String>();

	// [BUG]0013909 ADD END

	// $Author:wei_peng
	// $Date:2012/11/06 11:07
	// [BUG]0011030 ADD BEGIN
	// 新增的患者医生关系记录
	private List<PatientDoctor> patientDoctorList = new ArrayList<PatientDoctor>();

	// [BUG]0011030 ADD END

	// $Author: tong_meng
	// $Date: 2013/8/30 15:00
	// [BUG]0036757 ADD BEGIN
	private String serviceId;
	// [BUG]0036757 ADD END

	private MedicalVisit medicalvisit;

	/**
	 * 数据业务校验
	 */
	public boolean validate(POORIN200901UV05 poorin200901uv05) {
		// $Author :chang_xuewen
		// $Date : 2014/1/22 10:30$
		// [BUG]0042086 MODIFY BEGIN
		/*
		 * if(!poorin200901uv05.getMessage().getOrgCode().equals(poorin200901uv05
		 * .getOrgCode())) { logger.error("消息头机构与消息体机构不一致！消息头机构：{},消息体机构：{}",
		 * poorin200901uv05
		 * .getMessage().getOrgCode(),poorin200901uv05.getOrgCode());
		 * 
		 * loggerBS304.error("Message:{}, 消息头机构与消息体机构不一致！",
		 * poorin200901uv05.toString());
		 * 
		 * return false; }
		 */
		if (StringUtils.isEmpty(poorin200901uv05.getOrgCode())) {
			// $Author :yang_mingjie
			// $Date : 2014/06/26 10:09$
			// [BUG]0045630 MODIFY BEGIN
			poorin200901uv05.setOrgCode(Constants.HOSPITAL_CODE);
			poorin200901uv05.setOrgName(Constants.HOSPITAL_NAME);
			// [BUG]0045630 MODIFY END
		}
/*		// 将医嘱类别编码为09的嘱托类的消息，取消“医嘱序号”、“医嘱编码”、“执行科室编码”、“执行科室名称”的必填项要求
		List<CareOrderBatch> careOrderBatchItemList = poorin200901uv05
				.getCareOrderBatch();
		for (CareOrderBatch cb : careOrderBatchItemList) {
			if(!Constants.ENTRUST_ORDER.equals(cb.getOrderType())){
				if(!StringUtils.isNotNullAll(cb.getOrderSeqnum(),
						cb.getExecDept(),
						cb.getExecDeptName(),
						cb.getSuppliesNo()
						)){
					loggerBS304.error( 
							"Message:{},validate{}",
							cb.toString(),
	                        "非空字段验证未通过! OrderSeqnum = "
	                            + cb.getOrderSeqnum()
	                            + ";SuppliesNo = "
	                            + cb.getOrderNo()
	                            + ";ExecDept = "
	                            + cb.getExecDept()
	                            + ";ExecDeptName = "
	                            + cb.getExecDeptName()
	                            );
					return false;
				}
			}
		}*/
		
		// [BUG]0042086 MODIFY END
		return true;
	}

	/**
	 * 检查消息的依赖关系
	 */
	@Override
	public boolean checkDependency(POORIN200901UV05 poorin200901uv05,
			boolean flag) {
		// 获取患者内部ID
		patientLid = poorin200901uv05.getPatientLid();

		// 域代码
		patientDomain = poorin200901uv05.getPatientDomain();

		// 就诊信息验证
		if(!checkMedicalVisit(poorin200901uv05, flag)){
			return false;
		}
		// 判断患者内部ID是否为空
		if (patientLid == null || patientLid.trim().isEmpty()) {
			logger.error("MessageId:{};患者内部ID为空！", poorin200901uv05
					.getMessage().getId());
			if (flag) {
				loggerBS304.error("Message:{},checkDependency:{}",
						poorin200901uv05.toString(), "患者信息不存在:患者本地ID:"
								+ poorin200901uv05.getPatientLid());
			}
			return false;
		}
		
		// 如果新消息，检查就诊记录
		if (isNewMessage(poorin200901uv05)) {
			// $Author:jia_yanqing
			// $Date:2012/7/26 13:30$
			// [BUG]0008216 ADD BEIGN
			String parentOrderLid = null;
			String orgCode = poorin200901uv05.getOrgCode();
			List<CareOrderBatch> careOrderBatchItemList = poorin200901uv05
					.getCareOrderBatch();
			MedicationOrder medicationOrder;
			// 检查与父医嘱的关联关系
			for (int i = 0; i < careOrderBatchItemList.size(); i++) {
				parentOrderLid = careOrderBatchItemList.get(i)
						.getParentOrderId();

				if (!StringUtils.isEmpty(parentOrderLid)) {
					medicationOrder = commonService.checkParentOrder(
							patientDomain, patientLid, parentOrderLid, orgCode,
							medicalvisit.getVisitSn());
					if (medicationOrder == null) {
						logger.error(
								"MessageId:{};护理医嘱消息新增场合,关联的父医嘱不存在,父医嘱号 {}",
								poorin200901uv05.getMessage().getId(),
								parentOrderLid);
						if (flag) {
							loggerBS304.error("Message:{},checkDependency:{}",
									poorin200901uv05.toString(),
									"护理医嘱消息新增场合,关联的父医嘱不存在:父医嘱号:"
											+ parentOrderLid + "患者本地ID:"
											+ patientLid + "域ID:"
											+ patientDomain + "visitSn:"
											+ medicalVisit.getVisitSn());
						}
						return false;
					}
				}
			}
			// [BUG]0008216 ADD END
			logger.debug("消息新增场景，{}条记录中有{}条在数据库中已存在", 
					poorin200901uv05.getCareOrderBatch().size(), checkCareOrdersExists(poorin200901uv05));
			return true;
		}
		// 如果更新消息，检查护理医嘱是否存在
		else if (isUpdateMessage(poorin200901uv05)) {
			return checkCareOrders(poorin200901uv05); 
		}
		

		logger.error("错误的消息交互类型：" + poorin200901uv05.getTriggerEventCode());
		return false;
	}

	/**
	 * 校验成功后把取到的相应数据进行保存或更新操作
	 */
	@Transactional
	public void saveOrUpdate(POORIN200901UV05 poorin200901uv05) {
		// $Author: tong_meng
		// $Date: 2013/8/30 15:00
		// [BUG]0036757 ADD BEGIN
		serviceId = poorin200901uv05.getMessage().getServiceId();
		// [BUG]0036757 ADD END

		// 从消息中获取护理医嘱信息
		setMessageContent(poorin200901uv05);
		// 新增/更新护理医嘱信息
		 if (isNewMessage(poorin200901uv05)
				 || isUpdateMessage(poorin200901uv05)) {
			// 增加护理医嘱
			commonService.saveList(careOrderListForInsert);
			// 更新护理医嘱
			commonService.updateList(careOrderListForUpdate);
		 }
		// $Author:wei_peng
		// $Date:2012/11/06 11:09
		// [BUG]0011030 ADD BEGIN
		// 新增时保存开嘱医生和对应患者的关系
		for (PatientDoctor patientDoctor : patientDoctorList) {
			commonService.save(patientDoctor);
		}
		// [BUG]0011030 ADD END

		
	}

	/**
	 * 是否是新消息
	 * 
	 * @param poorin200901uv05
	 * @return 是新消息，返回true，否则，返回false
	 */
	private boolean isNewMessage(POORIN200901UV05 poorin200901uv05) {
		return Constants.NEW_MESSAGE_FLAG.equals(poorin200901uv05.getTriggerEventCode())
				|| Constants.V2_NEW_MESSAGE_FLAG.equals(poorin200901uv05.getTriggerEventCode());
	}

	/**
	 * 是否是更新消息
	 * 
	 * @param poorin200901uv05
	 * @return 是更新消息，返回true，否则，返回false
	 */
	 
	 private boolean isUpdateMessage(POORIN200901UV05 poorin200901uv05) {
	   return Constants.UPDATE_MESSAGE_FLAG.equals(poorin200901uv05.getTriggerEventCode())
			   || Constants.V2_UPDATE_MESSAGE_FLAG.equals(poorin200901uv05.getTriggerEventCode()); 
	 }
	  

	/**
	 * 检查就诊记录
	 * 
	 * @param poorin200901uv05
	 * @return 检查就诊记录是否存在，存在，返回true，反之，返回false
	 */
	private boolean checkMedicalVisit(POORIN200901UV05 poorin200901uv05,
			boolean flag) {
		// 获取护理医嘱相关联的就诊记录
		medicalVisit = commonService.mediVisit(
				poorin200901uv05.getPatientDomain(), patientLid,
				poorin200901uv05.getVisitTimes(),
				poorin200901uv05.getOrgCode(),
				poorin200901uv05.getVisitOrdNo(),
				poorin200901uv05.getVisitType());

		// 判断关联的就诊记录是否存在
		if (medicalVisit == null) {
			logger.error("MessageId:{};护理医嘱关联的就诊信息不存在", poorin200901uv05
					.getMessage().getId());
			if (flag) {
				loggerBS304.error(
						"Message:{},checkDependency:{}",
						poorin200901uv05.toString(),
						"护理医嘱关联的就诊记录不存在:就诊次数:"
								+ poorin200901uv05
										.getVisitTimes() + "患者本地ID:"
								+ patientLid + "域ID:"
								+ poorin200901uv05.getPatientDomain()
								+ " 就诊流水号:"
								+ poorin200901uv05.getVisitOrdNo()
								);
			}
			return false;
		} else
			return true;
	}

	/**
	 * 检索消息中的医嘱是否已经存在，并将存在的医嘱保存到careOrdersForUpdate中
	 * @return 存在的医嘱数
	 * */
	private int checkCareOrdersExists(POORIN200901UV05 poorin200901uv05){
		int count = 0;
		for(CareOrderBatch careOrderBatch : poorin200901uv05.getCareOrderBatch()){
			if(checkCareOrder(careOrderBatch, patientDomain)){
				count++;
			}
		}
		return count;
	}
	
	/**
	 * 遍历检查护理医嘱是否存在
	 * 
	 * @param poorin200901uv05
	 * @return 记录是否存在，返回true， 否则，返回false
	 */
	private boolean checkCareOrders(POORIN200901UV05 poorin200901uv05) {
	  List<CareOrderBatch> careOrderBatchs = poorin200901uv05.getCareOrderBatch(); 
	  String patientDomain = poorin200901uv05.getPatientDomain();
	  // 从消息里逐个获得护理医嘱并检查 
	  for(CareOrderBatch careOrderBatch : careOrderBatchs) { 
		  // 对应护理医嘱不存在 
		  if (!checkCareOrder(careOrderBatch, patientDomain)) {
			  logger.error("更新护理医嘱信息不存在，医嘱号为: " + careOrderBatch.getOrderLid()
					  /*+ " 医嘱序号为：" + careOrderBatch.getOrderSeqnum()*/
					  ); 
			  return false; 
			  }
	  } 
	  return true; 
	  }

	/**
	 * 检查一条护理医嘱是否存在
	 * 
	 * @param careOrderBatch
	 * @param patientDomain
	 * @return 记录是否存在，存在返回true，否则为false
	 */
	  private boolean checkCareOrder(CareOrderBatch careOrderBatch, String patientDomain) {
		  // 添加搜索条件（患者本地ID 域代码 医嘱号 删除标识） 
		  Map<String, Object> map = new HashMap<String, Object>(); 
		  map.put("patientLid", patientLid);
		  map.put("patientDomain", patientDomain); 
		  map.put("deleteFlag",Constants.NO_DELETE_FLAG); 
		  map.put("orderLid",careOrderBatch.getOrderLid());
//		  map.put("orderSeqnum",careOrderBatch.getOrderSeqnum()); 
		  // 查找数据库中护理医嘱记录并返回 
		  List<Object>careOrderList = commonService.selectByCondition( CareOrder.class, map);
		  // 判断更新护理医嘱在数据库中是否存在,并保存在全局变量中 
		  if (careOrderList != null &&careOrderList.size() > 0) {
			  careOrdersForUpdate.add((CareOrder)careOrderList.get(0)); 
			  return true; 
		  } 
		  return false;
	  }

	/**
	 * 从消息得到护理医嘱的相关信息
	 * 
	 * @param poorin200901uv05
	 * @return 护理医嘱
	 */
	private void setMessageContent(POORIN200901UV05 poorin200901uv05) {

		List<CareOrderBatch> careOrderBatchs = poorin200901uv05
				.getCareOrderBatch();

		// 获取消息中的护理医嘱信息
		for (CareOrderBatch careOrderBatch : careOrderBatchs) {
			// 将等到的护理医嘱存入集合中
			getCareOrder(poorin200901uv05.getPatientDomain(),
					careOrderBatch, poorin200901uv05.getWardsId(),
					poorin200901uv05.getWardsName(),
					poorin200901uv05.getBedNo(),
					poorin200901uv05.getConfirmPerson(),
					poorin200901uv05.getConfirmPersonName(),
					poorin200901uv05.getConfirmTime(),
					poorin200901uv05.getOrgCode(),
					poorin200901uv05.getOrgName(),
					poorin200901uv05.getOrderDescribe(),
					poorin200901uv05.getOrderSetCode());
		}

	}

	/**
	 * 查找对应的待更新的诊疗医嘱记录
	 * 
	 * @param treatmentItem
	 *            消息中一条诊疗医嘱信息
	 * @return 查找到对应待更新的那条诊疗医嘱
	 */
	private CareOrder getCareOrder(CareOrderBatch careOrderItem) {
		for (CareOrder careOrder : careOrdersForUpdate) {
			// 通过医嘱号判断是否该条记录为待更新的医嘱记录
			if (careOrder.getOrderLid() 
					.equals(careOrderItem.getOrderLid()) 
					/*&& careOrder.getOrderSeqnum().toString().equals(careOrderItem.getOrderSeqnum())*/) {
				return careOrder;
			}
		}
		return null;
	}
	/**
	 * 获取单条的护理医嘱记录
	 * 
	 * @param isNew
	 * @param patientDomain
	 * @param careOrderBatch
	 * @param wardsId
	 * @param bedNo
	 * @param orderType
	 * @param confirmPerson
	 * @param confirmPersonName
	 * @param confirmTime
	 * @param orgCode
	 * @param orgName
	 * @return 一条护理医嘱记录
	 */
	public void getCareOrder(String patientDomain,
			CareOrderBatch careOrderBatch, String wardsId, String wardsName,
			String bedNo, String confirmPerson, String confirmPersonName,
			String confirmTime, String orgCode, String orgName,
			String orderDescribe, String orderSetCode) {
		
		CareOrder careOrder = null;
		CareOrder careOrderUpd = getCareOrder(careOrderBatch);
		if(careOrderUpd != null){
			careOrder = careOrderUpd;
		}else{
			careOrder = new CareOrder();
			// 更新回数
			careOrder.setUpdateCount(Constants.INSERT_UPDATE_COUNT);
		}
			
		// 患者内部序列号
		careOrder.setPatientSn(medicalVisit.getPatientSn());
		// 就诊内部序列号
		careOrder.setVisitSn(medicalVisit.getVisitSn());
		// 患者本地ID
		careOrder.setPatientLid(patientLid);
		// 域代码
		careOrder.setPatientDomain(patientDomain);
		// 医嘱组套编码
		careOrder.setOrderSetCode(orderSetCode);
		// 医嘱描述
		careOrder.setOrderDescribe(orderDescribe);

		// $Author:jia_yanqing
		// $Date:2012/7/26 17:30$
		// [BUG]0008216 ADD BEIGN
		BigDecimal parentSn = null;
		MedicationOrder medicationParentOrder = commonService.checkParentOrder(
				patientDomain, patientLid, careOrderBatch.getParentOrderId(),
				orgCode, medicalVisit.getVisitSn());
		if (medicationParentOrder != null) {
			parentSn = medicationParentOrder.getOrderSn();
		}
		// 父医嘱内部序列号
		careOrder.setParentOrderSn(parentSn);
		// [BUG]0008216 ADD END

		// 医疗机构编码
		careOrder.setOrgCode(orgCode);
		// 医疗机构名称
		careOrder.setOrgName(orgName);

		// 创建时间
		careOrder.setCreateTime(systemTime);
		
		// 删除标识
		careOrder.setDeleteFlag(Constants.NO_DELETE_FLAG);

		// $Author: tong_meng
		// $Date: 2013/8/30 15:00
		// [BUG]0036757 ADD BEGIN
		careOrder.setCreateby(DataMigrationUtils.getDataMigration() + serviceId); // 设置创建人
		// [BUG]0036757 ADD END

		// $Author:wei_peng
		// $Date:2012/11/06 11:09
		// [BUG]0011030 ADD BEGIN
		if (careOrderBatch.getOrderPerson() != null
				&& !careOrderBatch.getOrderPerson().isEmpty()) {
			PatientDoctor patientDoctor = commonWriterUtils.getPatientDoctor(
					medicalVisit.getVisitSn(), medicalVisit.getPatientSn(),
					careOrderBatch.getOrderPerson(),
					careOrderBatch.getOrderPersonName(), systemTime);
			if (patientDoctor != null) {
				// $Author:jin_peng
				// $Date:2013/02/06 13:38
				// [BUG]0013909 MODIFY BEGIN
				// 如果该医生未被处理则进行添加操作
				if (!commonWriterUtils.isExistsPatientDoctor(
						filterPatientDoctorList, medicalVisit.getVisitSn(),
						medicalVisit.getPatientSn(),
						careOrderBatch.getOrderPerson())) {
					// $Author: tong_meng
					// $Date: 2013/8/30 15:00
					// [BUG]0036757 ADD BEGIN
					patientDoctor.setCreateby(DataMigrationUtils.getDataMigration() + serviceId); // 设置创建人
					// [BUG]0036757 ADD END

					patientDoctorList.add(patientDoctor);
				}

				// [BUG]0013909 MODIFY END
			}
		}
		// [BUG]0011030 ADD END

		// $Author:wei_peng
		// $Date:2012/11/13 17:33
		// [BUG]0011421 ADD BEGIN
		// 医嘱交费状态编码
		careOrder.setChargeStatusCode(Constants.CHARGE_STATUS_NOTCHARGE);
		// 医嘱交费状态名称
		
		careOrder.setChargeStatusName(DictionaryUtils.getNameFromDictionary(
				Constants.ORDER_CHARGE_STATUS, Constants.CHARGE_STATUS_NOTCHARGE));
		 
		// [BUG]0011421 ADD END
		// 更新消息
		/*
		 * else { // 遍历库中的消息查找同当前护理医嘱匹配的医嘱 for (CareOrder careOrderForUpdate :
		 * careOrdersForUpdate) { // 找到当前护理医嘱对应的医嘱 if
		 * (isSameCareOrder(careOrderForUpdate, careOrderBatch)) { careOrder =
		 * careOrderForUpdate; break; }
		 * 
		 * }
		 * 
		 * }
		 */

		// $Author :tong_meng
		// $Date : 2013/8/22 16:45$
		// [BUG]0036524 MODIFY BEGIN
		// $Author :tong_meng
		// $Date : 2012/5/24 17:45$
		// [BUG]0007507 MODIFY BEGIN
		// $Author :tong_meng
		// $Date : 2012/5/24 17:45$
		// [BUG]0006667 ADD BEGIN
		/*
		 * if (StringUtils.isEmpty(careOrderBatch.getSuppliesName())) {
		 * careOrder.setOrderName(careOrderBatch.getOrderName());
		 * careOrder.setOrderCode(careOrderBatch.getOrderNo()); } else {
		 */
//		careOrder.setOrderName(careOrderBatch.getSuppliesName());
//		careOrder.setOrderCode(careOrderBatch.getSuppliesNo());
		/* } */
		// [BUG]0007507 MODIFY BEGIN
		// [BUG]0036524 MODIFY END
		// 确认医生
		careOrder.setDoctorConfirmPerson(careOrderBatch.getVerifyPerson());
		// 确认医生姓名
		careOrder.setDoctorConfirmPersonName(careOrderBatch
				.getVerifyPersonName());
		// 确认时间（医生）
		careOrder.setDoctorConfirmTime(DateUtils.stringToDate(careOrderBatch
				.getVerifyTime()));
		// [BUG]0006667 ADD END

		// 更新时间
		careOrder.setUpdateTime(systemTime);
		// 确认护士ID
		careOrder.setNurseConfirmPerson(confirmPerson);
		// 确认护士姓名
		careOrder.setNurseConfirmPersonName(confirmPersonName);
		// 确认时间（护士）
		careOrder.setNurseConfirmTime(DateUtils.stringToDate(confirmTime));
		// 病区ID
		careOrder.setWardsId(wardsId);
		// 病區名稱
		careOrder.setWardsName(DictionaryUtils.getNameFromDictionary(
				Constants.DOMAIN_WARD, wardsId, wardsName));
		// 床号
		careOrder.setBedNo(bedNo);

		// $Author :tong_meng
		// $Date : 2012/5/24 17:45$
		// [BUG]0008016 MODIFY BEGIN
		// 医嘱类型
		careOrder.setOrderType(careOrderBatch.getOrderType());
		// 医嘱类型名称
		careOrder.setOrderTypeName(DictionaryUtils.getNameFromDictionary(
				Constants.DOMAIN_ORDER_TYPE, careOrderBatch.getOrderType(), careOrderBatch.getOrderTypeName()));
		// [BUG]0008016 MODIFY END

		// 本地医嘱号
		careOrder.setOrderLid(careOrderBatch.getOrderLid());

		// $Author :tong_meng
		// $Date : 2012/5/24 17:45$
		// [BUG]0006667 DELETE BEGIN
		// // 医嘱名称
		careOrder.setOrderCode(careOrderBatch.getOrderCode());
		careOrder.setOrderName(careOrderBatch.getOrderName());
		// [BUG]0006667 DELETE END

		// 医嘱执行频率
		careOrder.setFreq(careOrderBatch.getFreq());

		// $Author :tong_meng
		// $Date : 2012/7/2 15:33$
		// [BUG]0007418 ADD BEGIN
		// 医嘱执行频率名稱
		careOrder.setFreqName(DictionaryUtils.getNameFromDictionary(
				Constants.DOMAIN_MEDICINE_FREQ, careOrderBatch.getFreq(), careOrderBatch.getFreqName()));
		// [BUG]0007418 ADD END

		// 下医嘱科室ID
		 careOrder.setOrderDept(careOrderBatch.getOrderDept());

		// $Author :tong_meng
		// $Date : 2012/7/2 15:33$
		// [BUG]0007418 ADD BEGIN
		// 下医嘱科室名稱
		 careOrder.setOrderDeptName(DictionaryUtils.getNameFromDictionary(
				 Constants.DOMAIN_DEPARTMENT, careOrderBatch.getOrderDept(), careOrderBatch.getOrderDeptName()));
		// [BUG]0007418 ADD END
		// 开嘱人ID
		careOrder.setOrderPerson(careOrderBatch.getOrderPerson());
		// 开嘱人姓名
		careOrder.setOrderPersonName(DictionaryUtils.getNameFromDictionary(
				Constants.DOMAIN_STAFF, careOrderBatch.getOrderPerson(), careOrderBatch.getOrderPersonName()));
		// 开嘱时间
		careOrder.setInputTime(DateUtils.stringToDate(careOrderBatch
				.getInputTime()));
		// 医嘱执行科室ID
		careOrder.setExecDept(careOrderBatch.getExecDept());

		// $Author :tong_meng
		// $Date : 2012/7/2 15:33$
		// [BUG]0007418 ADD BEGIN
		// 医嘱执行科室名稱
		careOrder.setExecDeptName(DictionaryUtils.getNameFromDictionary(
				Constants.DOMAIN_DEPARTMENT, careOrderBatch.getExecDept(), careOrderBatch.getExecDeptName()));
		// [BUG]0007418 ADD END
		// $Author :tong_meng
		// $Date : 2012/5/24 17:45$
		// [BUG]0006667 DELETE BEGIN
		// // 预定执行时间
		// careOrder.setPlanExecTime(DateUtils.stringToDate(careOrderBatch.getPlanExecTime()));
		// [BUG]0006667 DELETE END

		// 数量
		careOrder.setQuantity(StringUtils.strToBigDecimal(
				careOrderBatch.getQuantity(), "数量"));
		// 单位
		careOrder.setQuantityUnit(careOrderBatch.getUnit());

		// $Author :tong_meng
		// $Date : 2013/8/8 11:00$
		// [BUG]0035541 MODIFY BEGIN
		// $Author :jia_yanqing
		// $Date : 2012/7/20 17:33$
		// [BUG]0008154 MODIFY BEGIN
		// $Author :tong_meng
		// $Date : 2012/7/2 15:33$
		// [BUG]0007418 MODIFY BEGIN
		// 紧急程度区分
		// careOrder.setUrgentFlag(careOrderBatch.getUrgencyCode());
		// [BUG]0007418 MODIFY END
		// $Author :tong_meng
		// $Date : 2012/7/2 15:33$
		// [BUG]0007418 ADD BEGIN
		// 緊急程度區分名稱
		// careOrder.setUrgencyName(careOrderBatch.getUrgency());
		// 是否加急
		careOrder.setUrgentFlag(StringUtils.getConversionValue(careOrderBatch
				.getUrgency()));
		// [BUG]0007418 ADD END
		// [BUG]0008154 MODIFY END
		// [BUG]0035541 MODIFY END
		// 描述
		careOrder.setDescription(careOrderBatch.getDescription());
		// 嘱托
		careOrder.setComments(careOrderBatch.getComments());

		// $Author :tong_meng
		// $Date : 2012/7/16 20:33$
		// [BUG]0008021 MODIFY BEGIN
		// 父医嘱标示号码
		careOrder.setParentOrderSn(StringUtils.strToBigDecimal(
				careOrderBatch.getParentOrderId(), "父医嘱标示号码"));
		// [BUG]0008021 MODIFY END

		// 互斥医嘱类型
		careOrder.setMutexesOrderType(careOrderBatch.getMutexesOrderType());

		// $Author :tong_meng
		// $Date : 2012/7/2 15:33$
		// [BUG]0007418 ADD BEGIN
		// 互斥医嘱类型名稱
		careOrder.setMutexesOrderTypeName(careOrderBatch
				.getMutexesOrderTypeName());
		// [BUG]0007418 ADD END

		// $Author :tong_meng
		// $Date : 2012/7/16 20:33$
		// [BUG]0008021 MODIFY BEGIN
		// 互斥医嘱标识号码
		// careOrder.setMutexesOrderSn(StringUtils.strToBigDecimal(careOrderBatch.getMutexesOrderId()));
		// [BUG]0008021 MODIFY END

		// Author :jin_peng
		// Date : 2012/11/14 14:56
		// [BUG]0011478 MODIFY BEGIN
		// Author :jia_yanqing
		// Date : 2012/10/19 14:33
		// [BUG]0010624 MODIFY BEGIN
		String orderStatus = null;
		String orderStatusName = null;

		// 门诊医嘱状态为已下达
		/*
		 * if (Constants.PATIENT_DOMAIN_OUTPATIENT.equals(patientDomain)) {
		 * orderStatus = Constants.ORDER_STATUS_ISSUE;
		 * 
		 * orderStatusName = DictionaryCache.getDictionary(
		 * Constants.DOMAIN_ORDER_STATUS).get( Constants.ORDER_STATUS_ISSUE); }
		 * // 住院医嘱状态为已确认 else { orderStatus = Constants.ORDER_STATUS_VALIDATED;
		 * 
		 * orderStatusName = DictionaryCache.getDictionary(
		 * Constants.DOMAIN_ORDER_STATUS).get(
		 * Constants.ORDER_STATUS_VALIDATED); }
		 */

		orderStatus = Constants.ORDER_STATUS_ISSUE;
		orderStatusName = DictionaryUtils.getNameFromDictionary(Constants.DOMAIN_ORDER_STATUS, orderStatus);
				
		// 医嘱状态
		careOrder.setOrderStatus(orderStatus);

		// $Author :tong_meng
		// $Date : 2012/7/1 10:33$
		// [BUG]0007418 ADD BEGIN
		// 设置医嘱状态名称
		careOrder.setOrderStatusName(orderStatusName);
		// [BUG]0007418 ADD END
		// [BUG]0010624 MODIFY END
		// [BUG]0011478 MODIFY END

		// 长期临时标识
/*		careOrder.setUsage(careOrderBatch.getTemporaryFlag(careOrderBatch
				.getFreq()));*/
		String usage = careOrderBatch.getUsage();
		careOrder.setUsage(Constants.LONG_TERM_FLAG.equals(usage) ?
				Constants.LONG_TERM_FLAG : Constants.TEMPORARY_FLAG);
		

		// Author :jia_yanqing
		// Date : 2012/10/19 10:33
		// [BUG]0010629 ADD BEGIN
		// 医嘱开始时间
		careOrder.setOrderStartTime(DateUtils.stringToDate(careOrderBatch
				.getOrderStartTime()));
		// 医嘱停止时间
		careOrder.setOrderEndTime(DateUtils.stringToDate(careOrderBatch
				.getOrderEndTime()));
		// [BUG]0010629 ADD END

		// Author:duxiaolan
		// Date:2014/11/24
		// jiangsu begin
//		if(StringUtils.isEmpty(careOrderBatch.getOrderSeqnum())){
//			careOrder.setOrderSeqnum(1);
//		}else{
//			careOrder.setOrderSeqnum(Integer.valueOf(careOrderBatch
//					.getOrderSeqnum()));
//		}
		
		// jiangsu end

		// Author:wei_peng
		// Date:2013/8/8 10:39
		// [BUG]0035949 ADD BEGIN
		// 是否皮试
		if (Constants.TRUE_FLAG.equals(careOrderBatch.getSkinTestFlag()))
			careOrder.setSkinTestFlag(Constants.T_FLAG_VALUE);
		else if (Constants.FALSE_FLAG.equals(careOrderBatch.getSkinTestFlag()))
			careOrder.setSkinTestFlag(Constants.F_FLAG_VALUE);
		// 是否药观
		if (Constants.TRUE_FLAG.equals(careOrderBatch.getMedViewFlag()))
			careOrder.setMedViewFlag(Constants.MED_VIEW_FLAG);
		else if (Constants.FALSE_FLAG.equals(careOrderBatch.getMedViewFlag()))
			careOrder.setMedViewFlag(Constants.NO_MED_VIEW_FLAG);
		// 是否适应
		if (Constants.TRUE_FLAG.equals(careOrderBatch.getAdaptiveFlag()))
			careOrder.setAdaptiveFlag(Constants.T_FLAG_VALUE);
		else if (Constants.FALSE_FLAG.equals(careOrderBatch.getAdaptiveFlag()))
			careOrder.setAdaptiveFlag(Constants.F_FLAG_VALUE);
		// [BUG]0035949 ADD END

		if (careOrderUpd != null) {
			// 更新人
			careOrder.setUpdateby(serviceId);
			careOrderListForUpdate.add(careOrder);
		} else {
			// 创建人
			careOrder.setCreateby(DataMigrationUtils.getDataMigration() + serviceId);
			careOrderListForInsert.add(careOrder);
		}

	}
}
