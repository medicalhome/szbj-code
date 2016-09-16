package com.founder.cdr.batch.writer;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
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

import com.founder.cdr.batch.writer.common.CommonWriterUtils;
import com.founder.cdr.core.Constants;
import com.founder.cdr.entity.CodeLabItem;
import com.founder.cdr.entity.CodeOrderItem;
import com.founder.cdr.entity.LabOrder;
import com.founder.cdr.entity.MedicalVisit;
import com.founder.cdr.entity.NursingOperation;
import com.founder.cdr.entity.PatientDoctor;
import com.founder.cdr.hl7.dto.LabOrderDto;
import com.founder.cdr.hl7.dto.RequestDto;
import com.founder.cdr.hl7.dto.Specimens;
import com.founder.cdr.hl7.dto.poorin200901uv01.POORIN200901UV01;
import com.founder.cdr.service.CommonService;
import com.founder.cdr.service.LabOrderService;
import com.founder.cdr.util.DataMigrationUtils;
import com.founder.cdr.util.DateUtils;
import com.founder.cdr.util.DictionaryUtils;
import com.founder.cdr.util.StringUtils;
import com.founder.sqlparser.util.StringUtil;

/**
 * [FUN] A01-009 检验申请消息
 * 
 * @version 1.0, 2012/05/02
 * @author guo_hongyan
 * @since 1.0
 */
@Component(value = "poorin200901uv01Writer")
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class POORIN200901UV01Writer implements BusinessWriter<POORIN200901UV01> {
	private static final Logger logger = LoggerFactory
			.getLogger(POORIN200901UV01Writer.class);

	private static final Logger loggerBS006 = LoggerFactory.getLogger("BS006");

	@Autowired
	private CommonService commonService;

	@Autowired
	private LabOrderService labOrderService;

	@Autowired
	private CommonWriterUtils commonWriterUtils;

	// 就诊内部序列号
	private BigDecimal visitSn;

	// 患者内部序列号
	private BigDecimal patientSn;

	// 就诊次数
	private String visitTimes;

	// 患者本地ID
	private String patientLid;

	// 以申请单循环的检验医嘱List
	private List<List<LabOrder>> requestExistList;

	// 更新新增标志
	private String newUpFlag;

	private Date sysdate;

	// $Author: tong_meng
	// $Date: 2013/8/30 15:00
	// [BUG]0036757 ADD BEGIN
	private String serviceId;
	
	private MedicalVisit medicalVisitResult;

	// [BUG]0036757 ADD END

	// $Author :tong_meng
	// $Date : 2012/9/14 11:30$
	// [BUG]0009718 MODIFY BEGIN
	// $Author :jia_yanqing
	// $Date : 2012/9/5 15:00$
	// [BUG]0008782 MODIFY BEGIN
	
	/*
	 * $Author: yu_yzh
	 * $Date: 2015/8/6 17:30
	 * 判断患者域id和就诊类型在字典中是否存在
	 * ADD BEGIN
	 * */
	private boolean DomainAndVisitTypeFlag;
	// ADD END
	/**
	 * 数据校验
	 */
	public boolean validate(POORIN200901UV01 poorin200901uv01) {
		// 消息中申请单List
		newUpFlag = poorin200901uv01.getNewUpFlg();
		
		//判断患者域id和就诊类型在字典中是否存在
		DomainAndVisitTypeFlag = containsDomainAndVisitType(poorin200901uv01);
		List<RequestDto> requestDtoList = poorin200901uv01.getRequestList();
		if (isNewMessage() || isUpdateMessage()){
			return validateNewOrUpdateMessge(requestDtoList, poorin200901uv01);
		}
		else if (isDeleteMessage())
			return validateDeleteMessage(requestDtoList, poorin200901uv01);
		else {
			logger.debug("验证交互类型失败");
			return false;
		}
	}

	// [BUG]0008782 MODIFY END
	// [BUG]0009718 MODIFY END

	// $Author :tong_meng
	// $Date : 2012/9/14 11:30$
	// [BUG]0009718 ADD BEGIN
	private boolean validateDeleteMessage(List<RequestDto> requestDtoList,
			POORIN200901UV01 poorin200901uv01) {
		logger.info("消息删除场合");
		// 如果消息是门诊类型。
		// if
		// (Constants.VISIT_TYPE_CODE_OUTPATIENT.equals(poorin200901uv01.getPatientDomain()))
		if (DomainAndVisitTypeFlag) {
			logger.info("门诊消息类型，可以删除 ");
			for (int i = 0; i < requestDtoList.size(); i++) {
				RequestDto requestDto = requestDtoList.get(i);
				if (StringUtils.isEmpty(requestDto.getRequestNo())) {
					loggerBS006.error("Message:{}, validate:{}",
							poorin200901uv01.toString(), "第" + i
									+ "个申请单的申请单号不能为空！");
					return false;
				}
			}
			// 医嘱信息
			return validateOrg(poorin200901uv01);
		} else {
			loggerBS006.error("Message:{}, validate:{}",
					poorin200901uv01.toString(), "不是门诊消息类型，不可以删除");
			return false;
		}
	}

	/**
	 * 当消息是新增和更新消息时，验证消息中的字段是否为空。
	 * 
	 * @param requestDtoList
	 * @param poorin200901uv01
	 * @return
	 */
	private boolean validateNewOrUpdateMessge(List<RequestDto> requestDtoList,
			POORIN200901UV01 poorin200901uv01) {
		// 验证dto中的部分属性不能为空！
		/*
		 * $Author: yu_yzh
		 * $Date: 2015/8/4 17:08
		 * V2消息样例中无对应项，非空校验注释掉
		 * */ 
		if (/*!StringUtils.isNotNullAll(poorin200901uv01.getRequestDoctor(),
				poorin200901uv01.getRequestDoctorName(),
				poorin200901uv01.getRequestDept(),
				poorin200901uv01.getRequestDeptName())*/ false) {
			loggerBS006.error(
					"Message:{}, validate:{}",
					poorin200901uv01.toString(),
					"非空字段验证未通过！requestDoctor："
							+ poorin200901uv01.getRequestDoctor()
							+ "；requestDoctorName："
							+ poorin200901uv01.getRequestDoctorName()
							+ "；requestDept："
							+ poorin200901uv01.getRequestDept()
							+ "；requestDeptName："
							+ poorin200901uv01.getRequestDeptName());
			return false;
		}
		for (RequestDto requestDto : requestDtoList) {
			// $Author :wu_biao
			// $Date : 2012/9/24 11:30$
			// [BUG]0009988 ADD BEGIN
			// 验证申请单中，医嘱类型编码，医嘱类型名称，检验申请日期为非空
			
			/*
			 * $Author: yu_yzh
			 * $Date: 2015/8/6 16:11
			 * V2消息检验申请日期不发，验证注释掉
			 * */
			if (!StringUtils.isNotNullAll(requestDto.getOrderType(),
					requestDto.getOrderTypeName()/*, requestDto.getRequestDate()*/)) {
				loggerBS006.error(
						"Message:{}, validate:{}",
						poorin200901uv01.toString(),
						"非空字段验证未通过！OrderType = " + requestDto.getOrderType()
								+ "; OrderTypeName = "
								+ requestDto.getOrderTypeName()
								+ ";RequestDate = "
								+ requestDto.getRequestDate());
				return false;
			}
			// if
			// (Constants.PATIENT_DOMAIN_OUTPATIENT.equals(poorin200901uv01.getPatientDomain()))
			/*
			 * $Author: yu_yzh
			 * $Date: 2015/8/6 16:18
			 * 申请单号可能为空，加一步验证  MODIFY BEGIN
			 * */
			// OMP^O09消息没有申请单号，不验证
			if (DomainAndVisitTypeFlag && !isOMPO09Message(poorin200901uv01)) {
				if (!StringUtils.isNotNullAll(requestDto.getRequestNo())) {
					loggerBS006.error(
							"Message:{}, validate:{}",
							poorin200901uv01.toString(),
							"非空字段验证未通过！申请单编号：RequestNo = "
									+ requestDto.getRequestNo());
					return false;
				}
			}
			// OMP^O09消息没有申请单号，不验证   MODIFY END
			// [BUG]0009988 ADD END
			List<LabOrderDto> labOrderDtoList = requestDto.getLabOrderDto();
			if (labOrderDtoList == null || labOrderDtoList.size() == 0) {
				loggerBS006.error("Message:{}, validate:{}",
						poorin200901uv01.toString(), "非空字段验证未通过：检验医嘱不能为空！");
				return false;
			}
			for (LabOrderDto labOrderDto : labOrderDtoList) {
				// $Author :tong_meng
				// $Date : 2013/7/31 15:50$
				// [BUG]0035541 MODIFY END 增加医嘱开始时间和医嘱执行频率编码验证
				// 验证检验医嘱中执行科室编码，执行科室名称为非空
				/*
				 * $Author: yu_yzh
				 * $Date: 2015/8/4 17:09
				 * V2消息样例中无对应项，非空校验注释掉，保留科室和项目id
				 * */
				/*
				 * $Author: yu_yzh
				 * $Date: 2015/8/6 16:04
				 * V2消息样例中科室和项目id的验证也放开
				 * */
				if (/*!StringUtils.isNotNullAll(labOrderDto.getDeliveryId(),
						labOrderDto.getDeliveryName(),
						labOrderDto.getItemCode(), labOrderDto.getItemName(),
						labOrderDto.getOrderStartTime(),
						labOrderDto.getOrderExecFreqCode())*/ false) {
					loggerBS006.error(
							"Message:{}, validate:{}",
							poorin200901uv01.toString(),
							"非空字段验证未通过！执行科室编码：DeliveryId = "
									+ labOrderDto.getDeliveryId()
									+ ";执行科室名称：DeliveryName = "
									+ labOrderDto.getDeliveryName()
									+ ";检验项目编码：ItemCode = "
									+ labOrderDto.getItemCode()
									+ ";检验项目名称：ItemName = "
									+ labOrderDto.getItemName()
									+ ";医嘱开始时间：orderStartTime = "
									+ labOrderDto.getOrderStartTime()
									+ ";医嘱执行频率编码：orderStartTime = "
									+ labOrderDto.getOrderStartTime());
					return false;
				}
				// [BUG]0035541 MODIFY END
			}
		}
		return validateOrg(poorin200901uv01);
	}

	// $Author:tong_meng
	// $Date:2013/12/03 11:00
	// [BUG]0040270 ADD BEGIN
	private boolean validateOrg(POORIN200901UV01 poorin200901uv01) {
		// $Author :chang_xuewen
		// $Date : 2014/1/22 10:30$
		// [BUG]0042086 MODIFY BEGIN
		// $Author:tong_meng
		// $Date:2013/12/03 11:00
		// [BUG]0040270 ADD BEGIN
		/*
		 * if(!poorin200901uv01.getOrgCode().equals(poorin200901uv01.getMessage()
		 * .getOrgCode())) { logger.error("消息头中的医疗机构代码与消息体中的医疗机构代码不一致！" +
		 * "消息头医疗机构代码：{}，消息体医疗机构代码：{}",
		 * poorin200901uv01.getMessage().getOrgCode(),
		 * poorin200901uv01.getOrgCode());
		 * loggerBS006.error("消息头中的医疗机构代码与消息体中的医疗机构代码不一致！" +
		 * "消息头医疗机构代码：{}，消息体医疗机构代码：{}",
		 * poorin200901uv01.getMessage().getOrgCode(),
		 * poorin200901uv01.getOrgCode()); return false; }
		 */
		if (StringUtils.isEmpty(poorin200901uv01.getOrgCode())) {
			// $Author :yang_mingjie
			// $Date : 2014/06/26 10:09$
			// [BUG]0045630 MODIFY BEGIN
			poorin200901uv01.setOrgCode(Constants.HOSPITAL_CODE);
			poorin200901uv01.setOrgName(Constants.HOSPITAL_NAME);
			// [BUG]0045630 MODIFY END
		}
		// [BUG]0042086 MODIFY END
		return true;
	}

	// [BUG]0040270 ADD END

	// [BUG]0009718 ADD END

	public boolean checkDependency(POORIN200901UV01 poorin200901uv01,
			boolean flag) {
		// $Author :tong_meng
		// $Date : 2012/5/28 14:00$
		// [BUG]0006657 MODIFY BEGIN
		// 患者本地ID
		patientLid = poorin200901uv01.getPatientLid();
		// [BUG]0006657 MODIFY END

		// 消息中申请单List
		newUpFlag = poorin200901uv01.getNewUpFlg();

		// $Author :tong_meng
		// $Date : 2012/9/14 11:30$
		// [BUG]0009718 ADD BEGIN
		boolean isMedicalVisit=checkMedicalVisit(poorin200901uv01, flag);
		if(!isMedicalVisit){
			return false;
		}
/*		if (isDeleteMessage()) {
			logger.info("消息删除场合");
			// 医嘱信息			
			return checkRequestInfo(poorin200901uv01, flag);
		}
		// [BUG]0009718 ADD END

		else if (isUpdateMessage()) {
			logger.info("消息更新场合");
			// 医嘱信息				
			return checkLabOrderInfo(poorin200901uv01, flag);
		}

		else if (isNewMessage()) {
			logger.info("消息新增场合");
			// 就诊记录判断是否存在
			if(isMedicalVisit)
			return true;
			return true;
		}*/
		if(!isDeleteMessage() && !isUpdateMessage() && !isNewMessage()){
			logger.error("消息交互类型{}不存在！", newUpFlag);
			return false;
		}
		else return true;
	}

	/**
	 * 
	 * @param baseDto
	 */
	@Transactional
	public void saveOrUpdate(POORIN200901UV01 poorin200901uv01) {
		// $Author: tong_meng
		// $Date: 2013/8/30 15:00
		// [BUG]0036757 ADD BEGIN
		serviceId = poorin200901uv01.getMessage().getServiceId();
		// [BUG]0036757 ADD END

		if (poorin200901uv01 != null) {
			if (isDeleteMessage()) {
				deleteLabOrder(poorin200901uv01);
			} else {
				insertOrUpdateLabOrder(poorin200901uv01);
				
				if (poorin200901uv01.getRequestDoctor() != null
						&& !poorin200901uv01.getRequestDoctor().isEmpty()) {
					// 返回空说明医生已存在，不新增
					PatientDoctor patientDoctor = commonWriterUtils
							.getPatientDoctor(visitSn, patientSn,
									poorin200901uv01.getRequestDoctor(),
									poorin200901uv01.getRequestDoctorName(),
									sysdate);
					if (patientDoctor != null) {
						patientDoctor.setCreateby(DataMigrationUtils.getDataMigration() + serviceId); // 设置创建人
						commonService.save(patientDoctor);
					}
				}
			}
		}
	}

	/**
	 * 删除检验遗嘱
	 * 
	 * @param poorin200901uv01
	 */
	private void deleteLabOrder(POORIN200901UV01 poorin200901uv01) {
		List<List<Object>> labOrderAllList = new ArrayList<List<Object>>();
		// 检验医嘱List
		List<Object> labOrderResultList = getLabOrderList(poorin200901uv01,
				poorin200901uv01.getRequestList().get(0),
				requestExistList.get(0));
		labOrderAllList.add(labOrderResultList);
		commonService.updateAllList(labOrderAllList);
	}

	/**
	 * ] 检验医嘱更新
	 * 
	 * @param poorin200901uv01
	 *            检验医嘱消息映射
	 */
	private void updateLabOrder(POORIN200901UV01 poorin200901uv01) {
		List<List<Object>> labOrderAllList = new ArrayList<List<Object>>();
		// 申请单循环
		for (int i = 0; i < poorin200901uv01.getRequestList().size(); i++) {
			// 检验医嘱List
			List<Object> labOrderResultList = getLabOrderList(poorin200901uv01,
					poorin200901uv01.getRequestList().get(i),
					requestExistList.get(i));
			labOrderAllList.add(labOrderResultList);
		}
		// 检验医嘱表更新
		commonService.updateAllList(labOrderAllList);

	}

	/**
	 * 新增检验医嘱
	 * 
	 * @param poorin200901uv01
	 *            检验医嘱消息映射
	 */
	private void insertLabOrder(POORIN200901UV01 poorin200901uv01) {
		List<List<Object>> labOrderAllList = new ArrayList<List<Object>>();
		// 消息中申请单循环
		for (RequestDto requestDto : poorin200901uv01.getRequestList()) {
			// 检验医嘱List
			List<Object> labOrderResultList = getLabOrderList(poorin200901uv01,
					requestDto, null);
			labOrderAllList.add(labOrderResultList);
		}
		// $Author :jin_peng
		// $Date : 2012/9/25 14:46$
		// [BUG]0009863 MODIFY BEGIN
		// 在检验申请新增消息时如果对应的检验申请在数据库中存在则更新相应记录，否则新增相应记录
		if (labOrderAllList != null && !labOrderAllList.isEmpty()) {
			for (List<Object> labList : labOrderAllList) {
				if (labList != null && !labList.isEmpty()) {
					for (Object object : labList) {
						LabOrder labOrder = (LabOrder) object;

						// 判断检验申请是否存在于数据库中，如果存在则更新，否则新增
						if (!StringUtils.isEmpty(StringUtils
								.BigDecimalToStr(labOrder.getOrderSn()))) {
							commonService.update(labOrder);
						} else {
							commonService.save(labOrder);
						}
					}
				}
			}
		}
		// [BUG]0009863 MODIFY END

	}

	/**
	 * 新增和更新状态时，查询数据库中有没有对应的医嘱，有则更新，没有则新增
	 * */
	private void insertOrUpdateLabOrder(POORIN200901UV01 poorin200901uv01){
		List<LabOrder> insertList = new ArrayList<LabOrder>();
		List<LabOrder> updateList = new ArrayList<LabOrder>();
		List<NursingOperation> nurList = new ArrayList<NursingOperation>();
		sysdate = new Date();
		for(RequestDto requestDto : poorin200901uv01.getRequestList()){
			LabOrderDto labOrderDto = requestDto.getLabOrderDto().get(0);
			LabOrder labOrder;
			List<Object> list = labOrderService.getOrderList(labOrderDto.getOrderLid(), 
					patientLid, poorin200901uv01.getPatientDomain());
			if(list != null && !list.isEmpty()){
				labOrder = (LabOrder) list.get(0);
				labOrder.setUpdateby(serviceId);
				updateList.add(labOrder);
				
				if(isChargeMessage()){
					labOrder.setUpdateTime(sysdate);
					// 交费状态
					labOrder.setChargeStatusCode(Constants.CHARGE_STATUS_CHARGED);
					
					labOrder.setChargeStatusName(DictionaryUtils.getNameFromDictionary(
							Constants.ORDER_CHARGE_STATUS, Constants.CHARGE_STATUS_CHARGED));
					// 医嘱状态
					labOrder.setOrderStatus(Constants.ORDER_STATUS_PAYMENT);					
					labOrder.setOrderStatusName(DictionaryUtils.getNameFromDictionary(
							Constants.DOMAIN_ORDER_STATUS, Constants.ORDER_STATUS_PAYMENT));
					// 总费用
					String charge = requestDto.getCharge();
					if(charge != null){
						labOrder.setCharge(StringUtils.strToBigDecimal(charge, null));
					}
					continue;
				}
				
			} else {
				labOrder = new LabOrder();
				insertList.add(labOrder);
				// 就诊内部序列号
				labOrder.setVisitSn(visitSn);
				// 患者内部序列号
				labOrder.setPatientSn(patientSn);
				// 域代码
				labOrder.setPatientDomain(poorin200901uv01
						.getPatientDomain());
				// 患者本地ID
				labOrder.setPatientLid(patientLid);
				// 更新回数
				labOrder.setUpdateCount(Constants.INSERT_UPDATE_COUNT);
				// 创建时间
				labOrder.setCreateTime(sysdate);
				// 删除标志
				labOrder.setDeleteFlag(Constants.NO_DELETE_FLAG);

				// 医疗机构代码
				labOrder.setOrgCode(poorin200901uv01.getOrgCode());
				// 医疗机构名称
				labOrder.setOrgName(poorin200901uv01.getOrgName());
				// 设置创建人
				labOrder.setCreateby(DataMigrationUtils.getDataMigration() + serviceId); 

				// 医嘱交费状态编码
				labOrder.setChargeStatusCode(Constants.CHARGE_STATUS_NOTCHARGE);
				// 医嘱交费状态名称
				labOrder.setChargeStatusName(DictionaryUtils.getNameFromDictionary(Constants.ORDER_CHARGE_STATUS, Constants.CHARGE_STATUS_NOTCHARGE));
				
				// 医嘱执行分类
				labOrder.setOrderExecId(poorin200901uv01.getMessage()
						.getOrderDispatchTypeCode());
			}
			// 申请日期
			if (requestDto.getRequestDate() != null) {
				labOrder.setRequestDate(DateUtils.stringToDate(requestDto
						.getRequestDate()));
			}
			// 本地医嘱号
			labOrder.setOrderLid(labOrderDto.getOrderLid());

			// 医嘱开始时间
			labOrder.setOrderStartTime(DateUtils.stringToDate(labOrderDto.getOrderStartTime()));
			// 医嘱结束时间
			labOrder.setOrderEndTime(DateUtils.stringToDate(labOrderDto.getOrderStartTime()));
			// 医嘱执行频率
			labOrder.setExecutionFrequency(labOrderDto.getOrderExecFreqCode());
			// 医嘱执行频率名称
			labOrder.setExecFreqName(labOrderDto.getOrderExecFreqName());
			// 是否加急
			labOrder.setUrgentFlag(StringUtils
					.getConversionValue(labOrderDto.getUrgentFlag()));
			// 是否药观
			labOrder.setMedViewFlag(StringUtils
					.getConversionValue(labOrderDto.getMedViewFlag()));
			// 是否皮试
			labOrder.setSkinTestFlag(StringUtils
					.getConversionValue(labOrderDto.getSkinTestFlag()));

			// 婴儿编号
			labOrder.setChildNo(labOrderDto.getChildNo());

			// 申请单编号
			if (requestDto.getRequestNo() != null) {
				labOrder.setRequestNo(requestDto.getRequestNo());
			}

/*			Map<String, String> map = getSampleType(requestDto.getSampleInfo(),
					labOrderDto.getSampleId());
			Set<Entry<String, String>> set = map.entrySet();
			for (Entry<String, String> entry : set) {
				System.out.println("Key:" + entry.getKey() + "\tValue:"
						+ entry.getValue());
				// 标本类型
				labOrder.setSampleType(entry.getKey());
				// 标本类型名称
				labOrder.setSampleTypeName(entry.getValue());
			}*/
			
			if(requestDto.getSampleInfo() != null && !requestDto.getSampleInfo().isEmpty()){
				// 标本类型
				labOrder.setSampleType(requestDto.getSampleInfo().get(0).getSampleType());
				// 标本类型名称
				labOrder.setSampleTypeName(requestDto.getSampleInfo().get(0).getSampleTypeName());
			}			
			
			// 更新的时候进行判断，如果医嘱执行状态发生了变更，不是“医嘱已下达”，就不更新相应标本号
			if ((Constants.ORDER_STATUS_ISSUE.equals(labOrder.getOrderStatus())
					|| "".equals(labOrder.getOrderStatus()) || labOrder
					.getOrderStatus() == null) && (DomainAndVisitTypeFlag)) {
				// [BUG]0014485 MODIFY END
				// 添加标本号到检验医嘱中
				labOrder.setSampleNo(labOrderDto.getSampleId());
			}
			// else if
			// (Constants.PATIENT_DOMAIN_INPATIENT.equals(poorin200901uv01.getPatientDomain()))
			else if (DomainAndVisitTypeFlag) {
				if (labOrder.getSampleNo() == null
						|| "".equals(labOrder.getSampleNo())) {
					labOrder.setSampleNo(labOrderDto.getSampleId());
				}
			}
			// [BUG]0041915 MODIFY BEGIN

			// [BUG]0014384 MODIFY END

			// 开嘱时间
			if (poorin200901uv01.getOrderTime() != null) {
//				labOrder.setOrderTime(DateUtils.stringToDate(poorin200901uv01
//						.getOrderTime()));
				labOrder.setOrderTime(DateUtils.stringToDate(requestDto.getRequestDate()));
			}
			if (poorin200901uv01.getRequestDoctor() != null) {
				// 下医嘱人ID
				labOrder.setOrderPerson(requestDto.getOrderPerson());
				// 下医嘱人姓名
				String opName = DictionaryUtils.getNameFromDictionary(
						Constants.DOMAIN_STAFF,
						requestDto.getOrderPerson(),
						requestDto.getOrderPersonName());
				labOrder.setOrderPersonName(opName);
			}

			/*
			 * $Author: yu_yzh $Date: 2015/8/14 14:07 根据字典查询科室名称 MODIFY BEGIN
			 */
			if (poorin200901uv01.getRequestDept() != null) {
				// 下医嘱科室
				labOrder.setOrderDept(poorin200901uv01.getRequestDept());
				String orderDeptName = DictionaryUtils.getNameFromDictionary(
						Constants.DOMAIN_DEPARTMENT,
						poorin200901uv01.getRequestDept(),
						poorin200901uv01.getRequestDeptName());
				labOrder.setOrderDeptName(orderDeptName);
			}
			// 根据CDA赋值
/*			if (labOrderDto.getDeliveryId() != null) {
				// 执行科室
				labOrder.setExecutiveDept(labOrderDto.getDeliveryId());
				String DeptName = DictionaryUtils.getNameFromDictionary(
						Constants.DOMAIN_DEPARTMENT, labOrderDto
								.getDeliveryId(), labOrderDto
								.getDeliveryName());
				labOrder.setExecutiveDeptName(DeptName);
			}*/
			// MODIFY END

			// 检验描述编码
			labOrder.setLabDescriptionCode(labOrderDto
					.getLabDescriptionCode());
			// 检验描述名称
			labOrder.setLabDescriptionName(labOrderDto
					.getLabDescriptionName());

			// 医嘱确认人ID
			labOrder.setConfirmPerson(poorin200901uv01.getConfirmPerson());
			// 医嘱确认人姓名
			labOrder.setConfirmPersonName(poorin200901uv01
					.getConfirmPersonName());
			// 医嘱确认时间
			labOrder.setConfirmTime(DateUtils.stringToDate(poorin200901uv01
					.getConfirmTime()));
			if (labOrderDto.getItemCode() != null) {
				// 检验项目代码
				labOrder.setItemCode(labOrderDto.getItemCode());
				String itemName = DictionaryUtils.getNameFromDictionary(
						Constants.DOMAIN_LAB_ITEM, labOrderDto
								.getItemCode(), labOrderDto
								.getItemName());
				labOrder.setItemName(itemName);
			}
			// MODIFY END

			/*
			 * $Author: yu_yzh $Date: 2015/8/18 11:31 设置项目类别编码，医嘱执行小分类 ADD BEGIN
			 */
			if (labOrderDto.getItemClass() != null) {
				labOrder.setItemClass(labOrderDto.getItemClass());

				labOrder.setOrderExecId(labOrderDto.getItemClass());
			}
			// ADD END

			if (poorin200901uv01.getAdmissionWards() != null) {
				// 病区ID
				labOrder.setWardsId(poorin200901uv01.getAdmissionWards());
				String wardName = DictionaryUtils.getNameFromDictionary(
						Constants.DOMAIN_WARD,
						poorin200901uv01.getAdmissionWards(),
						poorin200901uv01.getAdmissionWardsName());
				labOrder.setWardsName(wardName);
			}
			/*
			 * $Author: yu_yzh $Date: 2015/8/27 11:32 申请原因 ADD BEGIN
			 */
			if (poorin200901uv01.getRequsetReason() != null) {
				labOrder.setRequestReason(poorin200901uv01.getRequsetReason());
			}
			// ADD END

			// 床号
			labOrder.setBedNo(poorin200901uv01.getAdmissionBedNo());
			// 药观编码
			labOrder.setMedicalObs(requestDto.getMedicalObs());
			// 药观名称
			labOrder.setMedicalObsName(requestDto.getMedicalObsName());
			// 备注1
			labOrder.setRequestMemo1(requestDto.getRequestMemo1());
			// 备注2
			labOrder.setRequestMemo2(requestDto.getRequestMemo2());
			// 备注3
			labOrder.setRequestMemo3(requestDto.getRequestMemo3());
			// 备注4
			labOrder.setRequestMemo4(requestDto.getRequestMemo4());
			// 更新时间
			labOrder.setUpdateTime(sysdate);

			// 标准要求
			labOrder.setSampleRequirement(labOrderDto
					.getSampleRequirement());

			String orderStatus = null;
			String orderStatusName = null;

			// 港大住院医嘱开立时执行状态和门诊一样，都为已下达，接入医嘱确认消息后才更改为医嘱已确认。
			orderStatus = Constants.ORDER_STATUS_ISSUE;
			orderStatusName = DictionaryUtils.getNameFromDictionary(
					Constants.DOMAIN_ORDER_STATUS, Constants.ORDER_STATUS_ISSUE);
			// 更新的时候进行判断，如果医嘱执行状态发生了变更，不是“医嘱已下达”，就不更新医嘱执行状态
			if (Constants.ORDER_STATUS_ISSUE.equals(labOrder.getOrderStatus())
					|| "".equals(labOrder.getOrderStatus())
					|| labOrder.getOrderStatus() == null) {
				// 医嘱状态
				labOrder.setOrderStatus(orderStatus);
				// 医嘱状态名称
				labOrder.setOrderStatusName(orderStatusName);
			}

			// 医嘱类型
			labOrder.setOrderType(requestDto.getOrderType());
			// 医嘱类型名称
			labOrder.setOrderTypeName(requestDto.getOrderTypeName());
			// 长期或临时(默认临时)
			labOrder.setTemporaryFlag(requestDto.getTemporaryFlag(null));
			// 诊断(疾病代码)
			if (poorin200901uv01.getVisitList() != null
					&& !poorin200901uv01.getVisitList().isEmpty()
					&& poorin200901uv01.getVisitList().get(0).getDiagnosises() != null
					&& !poorin200901uv01.getVisitList().get(0).getDiagnosises()
							.isEmpty()) {
				labOrder.setDiagnosis(poorin200901uv01.getVisitList().get(0)
						.getDiagnosises().get(0).getDiagnosisName());
			}
		}
		if(!insertList.isEmpty()) {
			List<LabOrder> noRepeatOrderList = new ArrayList<LabOrder>();
			for(LabOrder o1 : insertList){
				boolean flag = false;
				for(LabOrder o2 : noRepeatOrderList){
					if(o1.getOrderLid().equals(o2.getOrderLid())){
						flag = true;
						break;
					}
				}
				if(!flag){
					noRepeatOrderList.add(o1);
					if(isChargeMessage()){
						nurList.add(getNursingOperation(o1, poorin200901uv01));
					}
				}
			}
			insertList = noRepeatOrderList;
			logger.debug("Message:{} 新增了{}条检验医嘱", poorin200901uv01.getMessage().getId(),
					insertList.size());
			commonService.saveList(insertList);
			
		}
		if(!updateList.isEmpty()){
			List<LabOrder> noRepeatOrderList = new ArrayList<LabOrder>();
			for(LabOrder o1 : updateList){
				boolean flag = false;
				for(LabOrder o2 : noRepeatOrderList){
					if(o1.getOrderLid().equals(o2.getOrderLid())){
						flag = true;
						if(isChargeMessage()){
							if(o1.getCharge() != null && o2.getCharge() != null){
								o1.setCharge(o1.getCharge().add(o2.getCharge()));
							}
							else if(o2.getCharge() != null){
								o1.setCharge(o2.getCharge());
							}
						}
						break;
					}
				}
				if(!flag){
					noRepeatOrderList.add(o1);
					if(isChargeMessage()){
						nurList.add(getNursingOperation(o1, poorin200901uv01));
					}
				}
			}
			updateList = noRepeatOrderList;
			logger.debug("Message:{} 更新了{}条检验医嘱", poorin200901uv01.getMessage().getId(),
					updateList.size());
			commonService.updateList(updateList);
		}
		if(!nurList.isEmpty()){
			commonService.saveList(nurList);
		}
	}
	
	/**
	 * 就诊相关信息判断
	 * 
	 * @param labOrderXml
	 *            检验申请消息映射数据
	 * @return true:存在；false:不存在
	 */
	private boolean checkMedicalVisit(POORIN200901UV01 poorin200901uv01,
			boolean flag) {
		// 消息中取到的就诊信息
		List<com.founder.cdr.hl7.dto.MedicalVisit> medicalVisit = poorin200901uv01
				.getVisitList();
		
		/*
		 * $Author: yu_yzh
		 * $Date: 2015/8/6 17:14
		 * 若就诊信息存在，根据就诊信息查询就诊，若就诊信息不存在，尝试通过申请单编号获取就诊
		 * MODIFY BEGIN
		 * */
		
		if(medicalVisit != null && !medicalVisit.isEmpty()){
			visitTimes = medicalVisit.get(0).getVisitTimes();
			// $Author:tong_meng
			// $Date:2013/12/03 11:00
			// [BUG]0040270 MODIFY BEGIN
			// 数据库表中就诊是否存在判定
			medicalVisitResult = commonService.mediVisit(
					poorin200901uv01.getPatientDomain(), patientLid, visitTimes,
					poorin200901uv01.getOrgCode(), medicalVisit.get(0)
							.getVisitOrdNo(), medicalVisit.get(0).getVisitType());
			// [BUG]0040270 MODIFY END
		}	
//		根据申请单编号获取就诊信息
		if(medicalVisitResult == null){
			medicalVisitResult = getVisitByRequestNo(poorin200901uv01);
		}
		// MODIFY END
		
		if (medicalVisitResult != null) {
			// 就诊记录存在
			visitSn = medicalVisitResult.getVisitSn();
			patientSn = medicalVisitResult.getPatientSn();
			logger.info("就诊信息存在,就诊内部序列号 {},患者内部序列号{}", visitSn, patientSn);
		} else {
			
			if(medicalVisit.isEmpty() || medicalVisit.get(0) == null){
				logger.error("bean中就诊信息不存在");
			} else{
				// 就诊记录不存在
				logger.error(
						"MessageId:{};就诊信息不存在!域ID:"
								+ poorin200901uv01.getPatientDomain() + "患者本地ID"
								+ patientLid + "就诊次数" + visitTimes + "医疗机构代码"
								+ poorin200901uv01.getOrgCode() + "就诊流水号"
								+ medicalVisit.get(0).getVisitOrdNo() + "就诊类型"
								+ medicalVisit.get(0).getVisitType(),
						poorin200901uv01.getMessage().getId());
			}
			if (flag) {
				loggerBS006.error("Message:{},checkDependency:{}",
						poorin200901uv01.toString(), "就诊信息不存在!域ID:"
								+ poorin200901uv01.getPatientDomain()
								+ "患者本地ID" + patientLid + "就诊次数" + visitTimes);
			}
			return false;
		}
		return true;
	}

	// $Author :tong_meng
	// $Date : 2012/9/14 11:30$
	// [BUG]0009718 ADD BEGIN
	/**
	 * 删除时，根据申请单号进行删除。
	 * 
	 * @param poorin200901uv01
	 * @return
	 */
	private boolean checkRequestInfo(POORIN200901UV01 poorin200901uv01,
			boolean flag) {
		List<com.founder.cdr.hl7.dto.MedicalVisit> medicalVisit = poorin200901uv01
				.getVisitList();

		requestExistList = new ArrayList<List<LabOrder>>();

		List<String> requestNoList = new ArrayList<String>();
		// 申请单循环
		for (RequestDto requestDto : poorin200901uv01.getRequestList()) {
			// 将消息中的申请单编号放到集合中
			requestNoList.add(requestDto.getRequestNo());
		}

		// $Author:tong_meng
		// $Date:2013/12/03 11:00
		// [BUG]0040270 MODIFY BEGIN
		// 根据申请单集合查询数据
		List<LabOrder> requestDtos = labOrderService
				.getRequestListByRequestNoList(requestNoList, patientLid,
						poorin200901uv01.getPatientDomain(),
						poorin200901uv01.getOrgCode(),
						medicalVisitResult.getVisitSn());
		// [BUG]0040270 MODIFY END

		// 遍历查询出的申请单
		for (LabOrder labOrder : requestDtos) {
			for (int i = 0; i < requestNoList.size(); i++) {
				// 消息中的申请单号
				String requestNo = requestNoList.get(i);
				// 如果消息中的申请单号和数据库中的数据的申请单号相等
				if (requestNo.equals(labOrder.getRequestNo()))
					requestNoList.remove(i); // 如果消息中的申请单编号在数据库中有对应的数据，则会把此申请单编号从集合中删除
			}
		}
		// 如果消息中的申请单编号集合中还有数据，则说明此申请单编号在数据库中未找到关联数据
		if (requestNoList != null && requestNoList.size() != 0) {
			for (String requestErrorNo : requestNoList) {
				logger.error("MessageId:{};申请单号为{}的消息，未在数据库中找到数据！",
						poorin200901uv01.getMessage().getId(), requestErrorNo);
				if (flag) {
					loggerBS006.error("Message:{},checkDependency:{}",
							poorin200901uv01.toString(), "申请单号为"
									+ requestErrorNo + "的消息，未在数据库中找到数据！");
				}
			}
			return false;
		} else
			// 如果消息中的申请单编号集合为空。
			requestExistList.add(requestDtos);
		return true;
	}

	// [BUG]0009718 ADD END

	/**
	 * 检验医嘱信息是否存在
	 * 
	 * @param poorin200901uv01
	 *            消息映射数据
	 * @return true:存在；false:不存在
	 */
	private boolean checkLabOrderInfo(POORIN200901UV01 poorin200901uv01,
			boolean flag) {
		List<com.founder.cdr.hl7.dto.MedicalVisit> medicalVisit = poorin200901uv01
				.getVisitList();

		// 以申请单为单位循环存放的检验医嘱List
		this.requestExistList = new ArrayList<List<LabOrder>>();
		// 申请单循环
		for (RequestDto requestDto : poorin200901uv01.getRequestList()) {
			// 以检验项目为单位的存放检验医嘱List
			List<LabOrder> labOrderExistList = new ArrayList<LabOrder>();

			// 检验项目List
			List<LabOrderDto> labOrderList = requestDto.getLabOrderDto();

			for (LabOrderDto labOrderDto : labOrderList) {
				// $Author:tong_meng
				// $Date:2013/12/03 11:00
				// [BUG]0040270 MODIFY BEGIN
				// 检验医嘱是否存在
				LabOrder labOrderExists = labOrderService.getOrderPresence(
						poorin200901uv01.getPatientDomain(), patientLid,
						labOrderDto.getOrderLid(),
						poorin200901uv01.getOrgCode(),
						medicalVisitResult.getVisitSn());
				// [BUG]0040270 MODIFY END

				if (labOrderExists == null) {
					logger.error(
							"MessageId:{};检验医嘱不存在！域ID:"
									+ poorin200901uv01.getPatientDomain()
									+ "患者本地ID" + patientLid + "本地医嘱号"
									+ labOrderDto.getOrderLid() + ", 医疗机构编码"
									+ poorin200901uv01.getOrgCode(),
							poorin200901uv01.getMessage().getId());
					if (flag) {
						loggerBS006.error(
								"Message:{},checkDependency:{}",
								poorin200901uv01.toString(),
								"检验医嘱不存在！域ID:"
										+ poorin200901uv01.getPatientDomain()
										+ "患者本地ID" + patientLid + "本地医嘱号"
										+ labOrderDto.getOrderLid()
										+ ", 医疗机构编码"
										+ poorin200901uv01.getOrgCode());
					}
					return false;
				} else {
					labOrderExistList.add(labOrderExists);
				}
			}
			this.requestExistList.add(labOrderExistList);
		}
		return true;
	}

	/**
	 * 检验医嘱表Entity List
	 */
	private List<Object> getLabOrderList(POORIN200901UV01 poorin200901uv01,
			RequestDto requestDto, List<LabOrder> labOrderExistList) {
		List<com.founder.cdr.hl7.dto.MedicalVisit> medicalVisit = poorin200901uv01
				.getVisitList();
		// 检验医嘱表存在数据，更新检验医嘱表
		List<Object> labOrderList = new ArrayList<Object>();
		// 检验医嘱entity
		LabOrder labOrder = null;
		// 系统时间取得
		sysdate = DateUtils.getSystemTime();

		// 获取消息中检验医嘱List
		List<LabOrderDto> labOrderV3List = requestDto.getLabOrderDto();
		// 遍历检验医嘱消息
		for (int i = 0; i < labOrderV3List.size(); i++) {
			// 数据库表中存在检验医嘱
			if (labOrderExistList != null) {
				// $Author :tong_meng
				// $Date : 2012/9/14 11:30$
				// [BUG]0009718 ADD BEGIN
				if (isDeleteMessage()) {
					for (LabOrder labOrderDelete : labOrderExistList) {
						labOrderDelete.setDeleteTime(sysdate);
						labOrderDelete.setDeleteFlag(Constants.DELETE_FLAG);
						labOrderDelete.setUpdateTime(sysdate);

						// $Author: tong_meng
						// $Date: 2013/8/30 15:00
						// [BUG]0036757 ADD BEGIN
						labOrderDelete.setDeleteby(serviceId); // 设置创建人
						// [BUG]0036757 ADD END

						labOrderList.add(labOrderDelete);
					}
					break;
				}
				// [BUG]0009718 ADD END
				else {
					labOrder = labOrderExistList.get(i);
					// $Author: tong_meng
					// $Date: 2013/8/30 15:00
					// [BUG]0036757 ADD BEGIN
					labOrder.setUpdateby(serviceId); // 设置创建人
					// [BUG]0036757 ADD END
					
					/*
					 * $Author: yu_yzh
					 * $Date: 2015/8/6 17:49
					 * v2消息中不包含医嘱的完整信息，只更新医嘱状态和交费状态
					 * */
					if(isChargeMessage()){
						labOrder.setChargeStatusCode(Constants.CHARGE_STATUS_CHARGED);
						labOrder.setChargeStatusName(DictionaryUtils.getNameFromDictionary(Constants.ORDER_CHARGE_STATUS, Constants.CHARGE_STATUS_CHARGED));
						
						labOrder.setOrderStatus(Constants.ORDER_STATUS_PAYMENT);
						labOrder.setOrderStatusName(DictionaryUtils.getNameFromDictionary(Constants.DOMAIN_ORDER_STATUS, Constants.ORDER_STATUS_PAYMENT));
						String charge = poorin200901uv01.getRequestList().get(0).getCharge();
						if(charge != null){
							labOrder.setCharge(StringUtils.strToBigDecimal(charge, null));
						}
						
						labOrderList.add(labOrder);
						continue;
					}
				}
			} else {
				// 新增场合(labOrderExistList = null,检验医嘱List为NULL)
				labOrder = new LabOrder();

				// $Author :jin_peng
				// $Date : 2012/9/25 14:46$
				// [BUG]0009863 MODIFY BEGIN
				// 是否新增更新标识
				boolean isAdd = true;

				/*
				 * Author: yu_yzh
				 * Date: 2015/10/28 10:53
				 * 判断消息新增时数据库中是否存在对应的医嘱，不存在则新增，否则更新
				 * MODIFY BEGIN
				 * */
/*				// 在门诊新增情况下判断消息中的检验医嘱是否存在，如果存在则更新，否则新增
				// if
				// (Constants.PATIENT_DOMAIN_OUTPATIENT.equals(poorin200901uv01.getPatientDomain()))
				if (DomainAndVisitTypeFlag) {
					// $Author:tong_meng
					// $Date:2013/12/03 11:00
					// [BUG]0040270 MODIFY BEGIN
					// 通过域ID、患者本地ID、申请单号查找待更新的记录
					// $Author:du_xiaolan
					// $Date:2014/11/10
					// 加上就诊内部序列号visitSn
					List<Object> labRequestList = labOrderService
							.getRequestList(requestDto.getRequestNo(),
									patientLid,
									poorin200901uv01.getPatientDomain(),
									poorin200901uv01.getOrgCode(),
									medicalVisitResult.getVisitSn());
					// [BUG]0040270 MODIFY END

					// 如果检验申请存在则给存在的检验申请赋值
					if (labRequestList != null && !labRequestList.isEmpty()) {
						// 获取检验申请记录
						labOrder = (LabOrder) labRequestList.get(0);

						// $Author: tong_meng
						// $Date: 2013/8/30 15:00
						// [BUG]0036757 ADD BEGIN
						labOrder.setUpdateby(serviceId); // 设置创建人
						// [BUG]0036757 ADD END

						// 设置记录为更新状态
						isAdd = false;
					}
				}*/
				/*
				 * 判断消息新增时数据库中是否存在对应的医嘱，不存在则新增，否则更新
				 * MODIFY END
				 * */
				
				List<Object> getedOrder = labOrderService.getOrderList(labOrderV3List.get(i).getOrderLid(), 
						patientLid, poorin200901uv01.getPatientDomain());
				if(getedOrder != null && !getedOrder.isEmpty()){
					labOrder = (LabOrder) getedOrder.get(0);
					labOrder.setUpdateby(serviceId); // 设置创建人
					isAdd = false;
				}
				
				if (isAdd) {
					// 就诊内部序列号
					labOrder.setVisitSn(visitSn);
					// 患者内部序列号
					labOrder.setPatientSn(patientSn);
					// 域代码
					labOrder.setPatientDomain(poorin200901uv01
							.getPatientDomain());
					// 患者本地ID
					labOrder.setPatientLid(patientLid);
					// 更新回数
					labOrder.setUpdateCount(Constants.INSERT_UPDATE_COUNT);
					// 创建时间
					labOrder.setCreateTime(sysdate);
					// 删除标志
					labOrder.setDeleteFlag(Constants.NO_DELETE_FLAG);

					// $Author:tong_meng
					// $Date:2013/12/03 11:00
					// [BUG]0040270 ADD BEGIN
					// 医疗机构代码
					labOrder.setOrgCode(poorin200901uv01.getOrgCode());
					// 医疗机构名称
					labOrder.setOrgName(poorin200901uv01.getOrgName());
					// [BUG]0040270 ADD END

					// $Author: tong_meng
					// $Date: 2013/8/30 15:00
					// [BUG]0036757 ADD BEGIN
					labOrder.setCreateby(DataMigrationUtils.getDataMigration() + serviceId); // 设置创建人
					// [BUG]0036757 ADD END

					// $Author:wei_peng
					// $Date:2012/11/13 17:33
					// [BUG]0011421 ADD BEGIN
					// 医嘱交费状态编码
					labOrder.setChargeStatusCode(Constants.CHARGE_STATUS_NOTCHARGE);
					// 医嘱交费状态名称
					labOrder.setChargeStatusName(DictionaryUtils.getNameFromDictionary(Constants.ORDER_CHARGE_STATUS, Constants.CHARGE_STATUS_NOTCHARGE));
					// [BUG]0011421 ADD END
					// 医嘱执行分类
					labOrder.setOrderExecId(poorin200901uv01.getMessage()
							.getOrderDispatchTypeCode());
				}

				// [BUG]0009863 MODIFY END
			}
			// 申请日期
			if(requestDto.getRequestDate() != null){
				labOrder.setRequestDate(DateUtils.stringToDate(requestDto
						.getRequestDate()));
			}
			// 本地医嘱号
			labOrder.setOrderLid(labOrderV3List.get(i).getOrderLid());

			// $Author :tong_meng
			// $Date : 2013/7/31 15:50$
			// [BUG]0035541 ADD BEGIN
			// 医嘱开始时间
			labOrder.setOrderStartTime(DateUtils.stringToDate(labOrderV3List
					.get(i).getOrderStartTime()));
			// 医嘱结束时间
			labOrder.setOrderEndTime(DateUtils.stringToDate(labOrderV3List.get(
					i).getOrderStartTime()));
			// 医嘱执行频率
			labOrder.setExecutionFrequency(labOrderV3List.get(i)
					.getOrderExecFreqCode());
			// 医嘱执行频率名称
			labOrder.setExecFreqName(labOrderV3List.get(i)
					.getOrderExecFreqName());
			// 是否加急
			labOrder.setUrgentFlag(StringUtils
					.getConversionValue(labOrderV3List.get(i).getUrgentFlag()));
			// 是否药观
			labOrder.setMedViewFlag(StringUtils
					.getConversionValue(labOrderV3List.get(i).getMedViewFlag()));
			// 是否皮试
			labOrder.setSkinTestFlag(StringUtils
					.getConversionValue(labOrderV3List.get(i).getSkinTestFlag()));
			// [BUG]0035541 ADD END
			
			// $Author :du_xiaolan
			// $Date : 20150409
			// [BUG]0008782 ADD BEGIN
			// 婴儿编号
			labOrder.setChildNo(labOrderV3List.get(i).getChildNo());
			// [BUG]0008782 ADD END			

			// $Author :jia_yanqing
			// $Date : 2012/9/5 15:00$
			// [BUG]0008782 ADD BEGIN
			// 申请单编号
			if(requestDto.getRequestNo() != null){
				labOrder.setRequestNo(requestDto.getRequestNo());
			}
			// [BUG]0008782 ADD END

			// $Author :tong_meng
			// $Date : 2012/7/1 17:33$
			// [BUG]0007418 MODIFY BEGIN
			Map<String, String> map = getSampleType(requestDto.getSampleInfo(),
					labOrderV3List.get(i).getSampleId());
			Set<Entry<String, String>> set = map.entrySet();
			for (Entry<String, String> entry : set) {
				System.out.println("Key:" + entry.getKey() + "\tValue:"
						+ entry.getValue());

				// $Author :tong_meng
				// $Date : 2012/7/12 17:30$
				// [BUG]0007660 MODIFY BEGIN
				// 标本类型
				labOrder.setSampleType(entry.getKey());
				// 标本类型名称
				labOrder.setSampleTypeName(entry.getValue());
				// [BUG]0006119 MODIFY END

			}
			// [BUG]0007418 MODIFY END

			// Author: jia_yanqing
			// Date: 2014/1/17 9:30
			// [BUG]0041915 MODIFY BEGIN
			// Author :jia_yanqing
			// Date : 2013/3/11 14:56
			// [BUG]0014485 MODIFY BEGIN
			// $Author :jin_peng
			// $Date : 2013/03/08 14:19$
			// [BUG]0014384 MODIFY BEGIN
			// 更新的时候进行判断，如果医嘱执行状态发生了变更，不是“医嘱已下达”，就不更新相应标本号
			// if
			// ((Constants.ORDER_STATUS_ISSUE.equals(labOrder.getOrderStatus())
			// || "".equals(labOrder.getOrderStatus()) ||
			// labOrder.getOrderStatus() == null)
			// &&
			// Constants.PATIENT_DOMAIN_OUTPATIENT.equals(poorin200901uv01.getPatientDomain()))

			if ((Constants.ORDER_STATUS_ISSUE.equals(labOrder.getOrderStatus())
					|| "".equals(labOrder.getOrderStatus()) || labOrder
					.getOrderStatus() == null)
					&& (DomainAndVisitTypeFlag)) {
				// [BUG]0014485 MODIFY END

				// $Author:wei_peng
				// $Date:2013/01/21 12:18
				// [BUG]0013527 MODIFY BEGIN
				// $Author :jin_peng
				// $Date : 2012/08/15 16:00$
				// [BUG]0008784 MODIFY BEGIN
				// 添加标本号到检验医嘱中
				labOrder.setSampleNo(labOrderV3List.get(i).getSampleId());
				// [BUG]0008784 MODIFY END
				// [BUG]0013527 MODIFY END
			}
			// else if
			// (Constants.PATIENT_DOMAIN_INPATIENT.equals(poorin200901uv01.getPatientDomain()))
			else if (DomainAndVisitTypeFlag) {
				if (labOrder.getSampleNo() == null
						|| "".equals(labOrder.getSampleNo())) {
					labOrder.setSampleNo(labOrderV3List.get(i).getSampleId());
				}
			}
			// [BUG]0041915 MODIFY BEGIN

			// [BUG]0014384 MODIFY END

			// 开嘱时间
			if(poorin200901uv01.getOrderTime() != null){
//				labOrder.setOrderTime(DateUtils.stringToDate(poorin200901uv01
//						.getOrderTime()));
				labOrder.setOrderTime(DateUtils.stringToDate(requestDto
						.getRequestDate()));
			}
			if(poorin200901uv01.getRequestDoctor() != null){
				// 下医嘱人ID
				labOrder.setOrderPerson(requestDto.getOrderPerson());
				// 下医嘱人姓名
//				labOrder.setOrderPersonName(poorin200901uv01.getRequestDoctorName());
				String opName = DictionaryUtils.getNameFromDictionary(Constants.DOMAIN_STAFF, 
						requestDto.getOrderPerson(), requestDto.getOrderPersonName());
				labOrder.setOrderPersonName(opName);
			}

			/*
			 * $Author: yu_yzh
			 * $Date: 2015/8/14 14:07
			 * 根据字典查询科室名称
			 * MODIFY BEGIN
			 * */
			if(poorin200901uv01.getRequestDept() != null){
				// 下医嘱科室
				labOrder.setOrderDept(poorin200901uv01.getRequestDept());
				String orderDeptName = DictionaryUtils.getNameFromDictionary(Constants.DOMAIN_DEPARTMENT, 
						poorin200901uv01.getRequestDept(), poorin200901uv01.getRequestDeptName());
				labOrder.setOrderDeptName(orderDeptName);
			}
			// 根据CDA赋值
/*			if(labOrderV3List.get(i).getDeliveryId() != null){
				// 执行科室
				labOrder.setExecutiveDept(labOrderV3List.get(i).getDeliveryId());
				String DeptName = DictionaryUtils.getNameFromDictionary(Constants.DOMAIN_DEPARTMENT, 
						labOrderV3List.get(i).getDeliveryId(), labOrderV3List.get(i).getDeliveryName());
				labOrder.setOrderDeptName(DeptName);
			}*/
			
			// MODIFY END
			


			// Author :jia_yanqing
			// Date : 2013/4/25 16:33
			// [BUG]0031757 ADD BEGIN
			// 检验描述编码
			labOrder.setLabDescriptionCode(labOrderV3List.get(i)
					.getLabDescriptionCode());
			// 检验描述名称
			labOrder.setLabDescriptionName(labOrderV3List.get(i)
					.getLabDescriptionName());
			// [BUG]0031757 ADD END

			// 医嘱确认人ID
			labOrder.setConfirmPerson(poorin200901uv01.getConfirmPerson());
			// 医嘱确认人姓名
			labOrder.setConfirmPersonName(poorin200901uv01
					.getConfirmPersonName());
			// 医嘱确认时间
			labOrder.setConfirmTime(DateUtils.stringToDate(poorin200901uv01
					.getConfirmTime()));
			if(labOrderV3List.get(i).getItemCode() != null){
				// 检验项目代码
				labOrder.setItemCode(labOrderV3List.get(i).getItemCode());
				String itemName = DictionaryUtils.getNameFromDictionary(Constants.DOMAIN_LAB_ITEM, 
						labOrderV3List.get(i).getItemCode(), labOrderV3List.get(i).getItemName());
				labOrder.setItemName(itemName);
			}
			// MODIFY END
			
			/*
			 * $Author: yu_yzh
			 * $Date: 2015/8/18 11:31
			 * 设置项目类别编码，医嘱执行小分类
			 * ADD BEGIN
			 * */
			if(labOrderV3List.get(i).getItemClass() != null){
				labOrder.setItemClass(labOrderV3List.get(i).getItemClass());
				
				labOrder.setOrderExecId(labOrderV3List.get(i).getItemClass());
			}
			// ADD END
			
			if(poorin200901uv01.getAdmissionWards() != null){
				// 病区ID
				labOrder.setWardsId(poorin200901uv01.getAdmissionWards());
				String wardName = DictionaryUtils.getNameFromDictionary(Constants.DOMAIN_WARD, 
						poorin200901uv01.getAdmissionWards(), poorin200901uv01.getAdmissionWardsName());
				labOrder.setWardsName(wardName);
			}
			/*
			 * $Author: yu_yzh
			 * $Date: 2015/8/27 11:32
			 * 申请原因
			 * ADD BEGIN
			 * */
			if(poorin200901uv01.getRequsetReason() != null){
				labOrder.setRequestReason(poorin200901uv01.getRequsetReason());	
			}
			// ADD END
			
			// 床号
			labOrder.setBedNo(poorin200901uv01.getAdmissionBedNo());
			// 药观编码
			labOrder.setMedicalObs(requestDto.getMedicalObs());
			// 药观名称
			labOrder.setMedicalObsName(requestDto.getMedicalObsName());
			// 备注1
			labOrder.setRequestMemo1(requestDto.getRequestMemo1());
			// 备注2
			labOrder.setRequestMemo2(requestDto.getRequestMemo2());
			// 备注3
			labOrder.setRequestMemo3(requestDto.getRequestMemo3());
			// 备注4
			labOrder.setRequestMemo4(requestDto.getRequestMemo4());
			// 更新时间
			labOrder.setUpdateTime(sysdate);
			// 检验类别(TODO 数据库中与检验项很像)
			// 标准要求
			labOrder.setSampleRequirement(labOrderV3List.get(i)
					.getSampleRequirement());

			// Author :jin_peng
			// Date : 2012/11/14 14:56
			// [BUG]0011478 MODIFY BEGIN
			String orderStatus = null;
			String orderStatusName = null;

/*			// 门诊医嘱状态为已下达
			if (DomainAndVisitTypeFlag) {
				orderStatus = Constants.ORDER_STATUS_ISSUE;

				orderStatusName = DictionaryCache.getDictionary(
						Constants.DOMAIN_ORDER_STATUS).get(
						Constants.ORDER_STATUS_ISSUE);
			}
			// 住院医嘱状态为已确认
			else {
				orderStatus = Constants.ORDER_STATUS_VALIDATED;

				orderStatusName = DictionaryCache.getDictionary(
						Constants.DOMAIN_ORDER_STATUS).get(
						Constants.ORDER_STATUS_VALIDATED);
			}*/
			// 港大住院医嘱开立时执行状态和门诊一样，都为已下达，接入医嘱确认消息后才更改为医嘱已确认。
			orderStatus = Constants.ORDER_STATUS_ISSUE;

			orderStatusName = DictionaryUtils.getNameFromDictionary(Constants.DOMAIN_ORDER_STATUS, Constants.ORDER_STATUS_ISSUE);
			
			// Author :jia_yanqing
			// Date : 2013/3/11 14:56
			// [BUG]0014485 MODIFY BEGIN
			// $Author :jin_peng
			// $Date : 2013/03/08 14:19$
			// [BUG]0014384 MODIFY BEGIN
			// 更新的时候进行判断，如果医嘱执行状态发生了变更，不是“医嘱已下达”，就不更新医嘱执行状态
			if (Constants.ORDER_STATUS_ISSUE.equals(labOrder.getOrderStatus())
					|| "".equals(labOrder.getOrderStatus())
					|| labOrder.getOrderStatus() == null) {
				// [BUG]0014485 MODIFY END

				// $Author :jia_yanqing
				// $Date : 2012/10/22 17:33$
				// [BUG]0010686 MODIFY BEGIN
				// 医嘱状态
				labOrder.setOrderStatus(orderStatus);

				// $Author :jia_yanqing
				// $Date : 2012/15/10 15:33$
				// [BUG]0010727 MODIFY BEGIN
				// $Author :tong_meng
				// $Date : 2012/7/1 17:33$
				// [BUG]0007418 ADD BEGIN
				// 医嘱状态名称
				labOrder.setOrderStatusName(orderStatusName);
				// [BUG]0007418 ADD BEGIN
				// [BUG]0010686 MODIFY END
				// [BUG]0010727 MODIFY END
				// [BUG]0011478 MODIFY END
			}

			// [BUG]0014384 MODIFY END

			// 医嘱类型
			labOrder.setOrderType(requestDto.getOrderType());

			// $Author:wei_peng
			// $Date:2012/9/1 9:44
			// $[BUG]0009064 MODIFY BEGIN
			// 医嘱类型名称
			labOrder.setOrderTypeName(requestDto.getOrderTypeName());
			// $[BUG]0009064 MODIFY END
			// 长期或临时(默认临时)
			labOrder.setTemporaryFlag(requestDto.getTemporaryFlag(null));
			// $Author:wei_peng
			// $Date:2012/9/1 9:44
			// $[BUG]0009064 MODIFY BEGIN
			// 诊断(疾病代码)
			if(poorin200901uv01.getVisitList() != null && !poorin200901uv01.getVisitList().isEmpty()
					&& poorin200901uv01.getVisitList().get(0).getDiagnosises() != null
					&& !poorin200901uv01.getVisitList().get(0).getDiagnosises().isEmpty()){
				labOrder.setDiagnosis(poorin200901uv01.getVisitList().get(0)
						.getDiagnosises().get(0).getDiagnosisName());
			}			
			// $[BUG]0009064 MODIFY END

			// $Author:wei_peng
			// $Date:2013/7/9 10:52
			// $[BUG]0034583 ADD BEGIN
			if (DomainAndVisitTypeFlag) {
				Map<String, Object> condition = new HashMap<String, Object>();
				condition.put("code", labOrderV3List.get(i).getItemCode());
				condition.put("deleteFlag", Constants.NO_DELETE_FLAG);
				List<Object> objs = commonService.selectByCondition(
						CodeLabItem.class, condition);
				if (objs != null && objs.size() > 0) {
					CodeLabItem codeLabItem = (CodeLabItem) objs.get(0);
					labOrder.setOrderTypeMinCode(codeLabItem
							.getOrderTypeMinCode());
					labOrder.setOrderTypeMinName(codeLabItem
							.getOrderTypeMinName());
				}
			} else if (DomainAndVisitTypeFlag) {
				Map<String, Object> condition = new HashMap<String, Object>();
				condition.put("code", labOrderV3List.get(i).getItemCode());
				condition.put("deleteFlag", Constants.NO_DELETE_FLAG);
				List<Object> objs = commonService.selectByCondition(
						CodeOrderItem.class, condition);
				if (objs != null && objs.size() > 0) {
					CodeOrderItem codeOrderItem = (CodeOrderItem) objs.get(0);
					labOrder.setOrderTypeMinCode(codeOrderItem
							.getMessageTypeCode());
					labOrder.setOrderTypeMinName(codeOrderItem
							.getMessageTypeName());
				}
			}
			// $[BUG]0034583 ADD END

			// 检验医嘱List
			labOrderList.add(labOrder);
		}
		return labOrderList;
	}

	// $Author :tong_meng
	// $Date : 2012/7/1 17:33$
	// [BUG]0007418 MODIFY BEGIN
	/**
	 * 返回标本类型
	 * 
	 * @param specimenList
	 *            标本相关内容List
	 * @param sampleId
	 *            标本号
	 * @return
	 */
	private Map<String, String> getSampleType(List<Specimens> specimenList,
			String sampleId) {
		Map<String, String> map = new HashMap<String, String>();
		// 标本类型
		String sampleType = null;
		String sampleTypeName = null;
		if (specimenList != null && !specimenList.isEmpty()) {
			// 标本List循环
			for (Specimens specimens : specimenList) {
				// 标本号判断（标本相关内容List与医嘱里的标本号不为空判定）
				if (!StringUtil.isEmpty(specimens.getSpecimenid())
						&& !StringUtil.isEmpty(sampleId)) {
					// 标本List中查询与检验医嘱的标本号对应的标本类型
					if (specimens.getSpecimenid().equals(sampleId)) {
						sampleType = specimens.getSampleType();
						sampleTypeName = specimens.getSampleTypeName();
						map.put(sampleType, sampleTypeName);
						return map;
					}
				}
			}
		}
		return map;
	}
	// [BUG]0007418 MODIFY END
	
	/*
	 * $Author: yu_yzh
	 * $Date: 2015/8/3 14:46
	 * 将交互类型判断从代码中整理出统一的方法
	 * ADD BEGIN
	 * */
	/**
	 * 是否新增消息
	 * */
	private boolean isNewMessage(){
		if(newUpFlag == null){
			logger.debug("交互类型为空，newUpFlag == null");
			return false;
		}
		return Constants.NEW_MESSAGE_FLAG.equals(newUpFlag)
				|| Constants.V2_NEW_MESSAGE_FLAG.equals(newUpFlag);
	}
	/**
	 * 是否更新消息
	 * */
	private boolean isUpdateMessage(){
		if(newUpFlag == null){
			logger.debug("交互类型为空，newUpFlag == null");
			return false;
		}
		return Constants.UPDATE_MESSAGE_FLAG.equals(newUpFlag)
				|| Constants.V2_UPDATE_MESSAGE_FLAG.equals(newUpFlag)
				|| Constants.V2_RETURN_MESSAGE_FLAG.equals(newUpFlag)
				|| Constants.V2_CONFIRM_OR_CHARGE_MESSAGE_FLAG.equals(newUpFlag);
	}
	
	/**
	 * 是否确认or交费消息，V2用
	 * */
	private boolean isChargeMessage(){
		if(newUpFlag == null){
			logger.debug("交互类型为空，newUpFlag == null");
			return false;
		}
		return Constants.V2_CONFIRM_OR_CHARGE_MESSAGE_FLAG.equals(newUpFlag);
	}
	
	/**
	 * 是否删除消息
	 * */
	private boolean isDeleteMessage(){
		if(newUpFlag == null){
			logger.debug("交互类型为空，newUpFlag == null");
			return false;
		}
		return Constants.DELETE_MESSAGE_FLAG.equals(newUpFlag);
	}
	// ADD END
	
	/*
	 * $Author: yu_yzh
	 * $Date: 2015/8/6 16:54
	 * 添加根据申请单编号获取就诊信息的方法
	 * ADD BEGIN
	 * */
	/**
	 * 根据申请单编号获取就诊信息
	 * */
	private MedicalVisit getVisitByRequestNo(POORIN200901UV01 poorin200901uv01){
		if(poorin200901uv01.getRequestList() == null || poorin200901uv01.getRequestList().isEmpty()) return null;
		String requestNo = poorin200901uv01.getRequestList().get(0).getRequestNo();
		if(StringUtils.isEmpty(requestNo)) return null;
		MedicalVisit mv = null;
		mv = commonService.mediVisitByRequestNo(LabOrder.class, patientLid, requestNo);
		return mv;
	}
	// ADD END
	
	/*
	 * $Author: yu_yzh
	 * $Date: 2015/8/6 16:54
	 * 整理代码内容，判断患者域id和就诊类型在字典中是否存在
	 * ADD BEGIN
	 * */
	private boolean containsDomainAndVisitType(POORIN200901UV01 poorin200901uv01){
		if(poorin200901uv01.getVisitList() == null || poorin200901uv01.getVisitList().isEmpty()) return false;
		else {
			return Constants.lstDomainOutPatient().contains(
					poorin200901uv01.getPatientDomain())
					&& Constants.lstVisitTypeOutPatient().contains(
							poorin200901uv01.getVisitList().get(0).getVisitType());
		}
	}
	// ADD END
	
	private boolean isOMPO09Message(POORIN200901UV01 poorin200901uv01){
		return Constants.V2.equals(poorin200901uv01.getMessage().getMsgMode())
				&& poorin200901uv01.getMessage().getContent().contains("OMP^O09");
	}
	
	private NursingOperation getNursingOperation(LabOrder labOrder, POORIN200901UV01 poorin200901uv01){
		NursingOperation no = new NursingOperation();
		// 护理操作内部序列号
        no.setOperationSn(commonService.getSequence("SEQ_NURSING_OPERATION"));

        // 就诊内部序列号
        no.setVisitSn(labOrder.getVisitSn());

        // 医嘱内部序列号
        no.setOrderSn(labOrder.getOrderSn());

        // 患者内部序列号
        no.setPatientSn(labOrder.getPatientSn());

        // 域代码
        no.setPatientDomain(labOrder.getPatientDomain());

        // 患者本地ID
        no.setPatientLid(patientLid);

        // 医嘱类型
        no.setOrderType(labOrder.getOrderType());

        // $Author :tong_meng
        // $Date : 2012/7/1 15:33$
        // [BUG]0007418 ADD BEGIN
        // 医嘱类型名稱
        no.setOrderTypeName(labOrder.getOrderTypeName());
        // [BUG]0007418 ADD END

        // Author :jia_yanqing
        // Date : 2013/6/28 16:33
        // [BUG]0033848 ADD BEGIN
        // 医嘱状态编码
        no.setOrderStatusCode(labOrder.getOrderStatus());
        // 医嘱状态名称
        no.setOrderStatusName(labOrder.getOrderStatusName());
        
        // 执行科室编码
        no.setExecuteDeptCode(poorin200901uv01.getRequestDept());
        // 执行科室名称        
        no.setExecuteDeptName(DictionaryUtils.getNameFromDictionary(
        		Constants.DOMAIN_DEPARTMENT, poorin200901uv01.getRequestDept(), poorin200901uv01.getRequestDeptName()));
        // [BUG]0033848 ADD END

        // 操作人ID
        no.setOperatorId(poorin200901uv01.getConfirmPerson());

        // 操作人姓名
        no.setOperatorName(DictionaryUtils.getNameFromDictionary(Constants.DOMAIN_STAFF, 
        		poorin200901uv01.getConfirmPerson(), poorin200901uv01.getConfirmPersonName()));
        

        // 操作时间
        no.setOperationTime(DateUtils.stringToDate(poorin200901uv01.getConfirmTime()));

        // 创建时间
        no.setCreateTime(sysdate);

        // 更新时间
        no.setUpdateTime(sysdate);

        // 更新回数
        no.setUpdateCount(Constants.INSERT_UPDATE_COUNT);

        // 删除标志
        no.setDeleteFlag(Constants.NO_DELETE_FLAG);
        // $Author :chang_xuewen
        // $Date : 2013/08/31 16:00$
        // [BUG]0036757 MODIFY BEGIN
        // 增加人
        no.setCreateby(DataMigrationUtils.getDataMigration() + poorin200901uv01.getMessage().getServiceId());
        // [BUG]0036757 MODIFY END
        no.setOrgCode(poorin200901uv01.getOrgCode());
        no.setOrgName(poorin200901uv01.getOrgName());
		return no;
	}
}
