package com.yly.cdr.batch.writer;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.yly.cdr.core.Constants;
import com.yly.cdr.entity.ClinicalDocument;
import com.yly.cdr.entity.ExaminationItem;
import com.yly.cdr.entity.ExaminationOrder;
import com.yly.cdr.entity.ExaminationResult;
import com.yly.cdr.entity.ExaminationResultDetail;
import com.yly.cdr.entity.ExaminationResultProcedure;
import com.yly.cdr.entity.MedicalImaging;
import com.yly.cdr.entity.MedicalVisit;
import com.yly.cdr.entity.PatientCrossIndex;
import com.yly.cdr.entity.ProcedureOrder;
import com.yly.cdr.hl7.dto.ExamItemMessage;
import com.yly.cdr.hl7.dto.ExaminationItems;
import com.yly.cdr.hl7.dto.LabImageContent;
import com.yly.cdr.hl7.dto.SubExamItemMessage;
import com.yly.cdr.hl7.dto.pocdin000040uv02.AssistantMessage;
import com.yly.cdr.hl7.dto.pocdin000040uv02.ExaminingDoctor;
import com.yly.cdr.hl7.dto.pocdin000040uv02.OperatorMessage;
import com.yly.cdr.hl7.dto.pocdin000040uv02.POCDIN000040UV02;
import com.yly.cdr.hl7.dto.pocdin000040uv02.ReviewDoctor;
import com.yly.cdr.service.ClinicalDocumentService;
import com.yly.cdr.service.CommonService;
import com.yly.cdr.service.ExamService;
import com.yly.cdr.service.LabService;
import com.yly.cdr.util.DateUtils;
import com.yly.cdr.util.StringUtils;

/**
 * [FUN] A02-019 检查报告
 * 
 * @version 1.0, 2012/05/02
 * @author guo_hongyan
 * @since 1.0
 */

@Component(value = "pocdin000040uv02Writer")
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class POCDIN000040UV02Writer implements BusinessWriter<POCDIN000040UV02> {

	private static final Logger logger = LoggerFactory
			.getLogger(POCDIN000040UV02Writer.class);

	private Logger loggerBSDOCUMENT;

	// BS320 检查报告信息服务
	private static final Logger loggerBS320 = LoggerFactory.getLogger("BS320");

	// BS366 病理检查报告信息服务
	private static final Logger loggerBS366 = LoggerFactory.getLogger("BS366");

	// BS368 胃镜检查报告信息服务
	private static final Logger loggerBS368 = LoggerFactory.getLogger("BS368");

	// BS369 心电图检查报告
	private static final Logger loggerBS369 = LoggerFactory.getLogger("BS369");

	// 检验报告序列号sequences
	private static final String REPORT_SEQUENCE = "SEQ_REPORT";

	// 检查项目序列号sequences
	private static final String EXAM_ITEM_SEQUENCE = "SEQ_EXAM_ITEM";

	// 检验影像序列号sequences
	private static final String MEDICAL_IMAGING_SEQUENCE = "SEQ_MEDICAL_IMAGING";

	// 病历文档序列号sequences
	private static final String DOCUMENT_SEQUENCE = "SEQ_DOCUMENT";

	// 不验证就诊和医嘱信息
	private static final String MEDICAL_NO_ORDER_NO = "01";

	// 验证医嘱，不验证就诊
	private static final String MEDICAL_NO_ORDER_YES = "02";

	// 验证就诊，不验证医嘱
	private static final String MEDICAL_YES_ORDER_NO = "03";

	// 就诊和医嘱都验证
	private static final String MEDICAL_YES_ORDER_YES = "04";
	
	// 保存图像开关-关
//	private static final String MEDICAL_IMAGE_CLOSE= "0";

	@Autowired
	private ExamService examService;

	@Autowired
	private CommonService commonService;

	@Autowired
	private LabService labService;
	
	@Autowired
	private ClinicalDocumentService clinicalDocumentService;
	
	private ClinicalDocument clinicalDocument;

	// 病历文档内部序列号
	private BigDecimal documentSn;

	// 患者本地ID
	private String patientLid;

	// $Author :jia_yanqing
	// $Date : 2012/6/12 14:30$
	// [BUG]0006669 DELETE BEGIN
	// 就诊次数
	// private Integer visitTimes;
	// [BUG]0006669 DELETE END

	// 就诊内部序列号
	private BigDecimal visitSn;

	// 患者内部序列号
	private BigDecimal patientSn;

	// 检查报告内部序列号
	private BigDecimal examReportSn;
	
	// 存在检查报告
	private ExaminationResult examResultExist;

	// 存在检查项目
	private ExaminationItem examItemExist;

	// 待删除病历文档内部序列号
	// private BigDecimal documentSnToDelete;

	// 系统时间
	private Date systemTime;

	// $Author :tong_meng
	// $Date : 2012/10/16 14:20$
	// [BUG]0010423 ADD BEGIN
	private String examinationDept;

	private String examinationDeptName;

	// [BUG]0010423 ADD BEGIN

	// $Author:wei_peng
	// $Date:2012/12/27 16:57
	// [BUG]0012385 ADD BEGIN
	// 检查方法
	private String examinationMethod;

	// 检查方法名称
	private String examinationMethodName;

	// [BUG]0012385 ADD END

	// $Author :jia_yanqing
	// $Date : 2012/6/12 14:30$
	// [BUG]0006669 DELETE BEGIN
	// 召回场合存在检查报告
	// private ExaminationResult withdrawExamResultExist;
	// [BUG]0006669 DELETE END

	private String serviceId;

	// 患者域ID
	private String patientDomain;

	private MedicalVisit medicalVisit;

	/**
	 * 数据校验
	 */
	public boolean validate(POCDIN000040UV02 pocdin000040uv02) {

		patientDomain = pocdin000040uv02.getPatientDomain();
		serviceId = pocdin000040uv02.getMessage().getServiceId();
		loggerBSDOCUMENT = getDocumentLogger(serviceId);
		if (StringUtils.isEmpty(pocdin000040uv02.getMessage().getOrgCode())) {
			pocdin000040uv02.getMessage().setOrgCode(Constants.HOSPITAL_CODE);
		}

		// Author :jia_yanqing
		// Date : 2013/1/22 15:00
		// [BUG]0042085 MODIFY BEGIN
		if (pocdin000040uv02.getOrgCode() == null
				|| "".equals(pocdin000040uv02.getOrgCode())) {
			pocdin000040uv02.setOrgCode(Constants.HOSPITAL_CODE);
			pocdin000040uv02.setOrgName(Constants.HOSPITAL_NAME);
		}

		if (!Constants.EXAMREPORT_LEVEL_CONTROL.equals(MEDICAL_NO_ORDER_NO)
				&& !Constants.EXAMREPORT_LEVEL_CONTROL
						.equals(MEDICAL_YES_ORDER_NO)
				&& !Constants.EXAMREPORT_LEVEL_CONTROL
						.equals(MEDICAL_NO_ORDER_YES)
				&& !Constants.EXAMREPORT_LEVEL_CONTROL
						.equals(MEDICAL_YES_ORDER_YES)) {
			logger.error("cdr.properties中EXAMREPORT_LEVEL_CONTROL设置错误！");
			loggerBSDOCUMENT.error("Message:{}, validate:{}",
					pocdin000040uv02.toString(),
					"cdr.properties中EXAMREPORT_LEVEL_CONTROL设置错误！");
			return false;
		}
		/*
		 * if(!pocdin000040uv02.getMessage().getOrgCode().equals(pocdin000040uv02
		 * .getOrgCode())) { logger.error("消息头机构与消息体机构不一致！消息头机构：{},消息体机构：{}",
		 * pocdin000040uv02
		 * .getMessage().getOrgCode(),pocdin000040uv02.getOrgCode());
		 * 
		 * loggerBS320.error("Message:{}, 消息头机构与消息体机构不一致！",
		 * pocdin000040uv02.toString());
		 * 
		 * return false; }
		 */
		// [BUG]0042085 MODIFY END

		// boolean b = true;
		// List<String> validateList = null;
		// Map<String, String> logMap = null;
		// 新增、更新、替换场合
		if (Constants.VERSION_NUMBER_INSERT.equals(pocdin000040uv02
				.getVersionNo())
				|| Constants.VERSION_NUMBER_UPDATE.equals(pocdin000040uv02
						.getVersionNo())
				|| Constants.VERSION_NUMBER_REPLACE.equals(pocdin000040uv02
						.getVersionNo())) {
			// $Author:wei_peng
			// $Date:2012/8/27 15:38
			// $[BUG]0009144 MODIFY BEGIN
			// validateList = new ArrayList<String>();
			// logMap = new HashMap<String, String>();

			// $Author:wei_peng
			// $Date:2012/9/27 18:09
			// $[BUG]0010170 DELETE BEGIN
			// 报告医师ID
			// validateList.add(pocdin000040uv02.getReportDoctor());
			// logMap.put("报告医师ID不能为空！", pocdin000040uv02.getReportDoctor());
			// $[BUG]0010170 DELETE END

			// 报告日期
			// validateList.add(pocdin000040uv02.getReportDate());
			// logMap.put("报告日期不能为空！", pocdin000040uv02.getReportDate());

			// $Author :jin_peng
			// $Date : 2012/11/23 13:59$
			// [BUG]0011864 MODIFY BEGIN
			// $Author :jin_peng
			// $Date : 2012/09/20 14:47$
			// [BUG]0009691 ADD BEGIN
			// 住院时验证医嘱号是否存在
			List<String> orderLidList = pocdin000040uv02.getOrderLid();

			// 检查报告为病理学类型时，则不进行关联医嘱号的非空校验
			if (isPathlogyExamReport(pocdin000040uv02)) {
				return true;
			} else if (Constants.EXAMREPORT_LEVEL_CONTROL
					.equals(MEDICAL_YES_ORDER_YES)
					|| Constants.EXAMREPORT_LEVEL_CONTROL
							.equals(MEDICAL_NO_ORDER_YES)) {
				if (orderLidList != null && !orderLidList.isEmpty()) {
					for (String orderLid : orderLidList) {
						if (StringUtils.isEmpty(orderLid)) {

							logger.error("非空验证未通过，对应医嘱号中有空值存在！");
							loggerBSDOCUMENT.error("Message:{}, validate:{}",
									pocdin000040uv02.toString(),
									"非空验证未通过，对应医嘱号中有空值存在！");

							return false;
						}
					}
				} else {
					logger.error("非空验证未通过，医嘱号不存在");
					loggerBSDOCUMENT.error("Message:{}, validate:{}",
							pocdin000040uv02.toString(), "非空验证未通过，医嘱号不存在");

					return false;
				}
			}
			if (!isPathlogyExamReport(pocdin000040uv02)
					&& (Constants.EXAMREPORT_LEVEL_CONTROL
							.equals(MEDICAL_YES_ORDER_NO) || Constants.EXAMREPORT_LEVEL_CONTROL
							.equals(MEDICAL_YES_ORDER_YES))) {
				if (pocdin000040uv02.getVisitOrdNo() == null
						|| pocdin000040uv02.getVisitOrdNo().isEmpty()) {
					logger.error("就诊流水号不能为空！ serviceid:" + serviceId);

					loggerBSDOCUMENT.error("Message:{}, validate:{}",
							pocdin000040uv02.toString(),
							"就诊流水号不能为空！ serviceid:" + serviceId);

					return false;
				}
			}
		} else {
			logger.error("不可识别该文档的操作版本:" + pocdin000040uv02
					.getVersionNo());

			loggerBSDOCUMENT.error("Message:{}, validate:{}",
					pocdin000040uv02.toString(),
					"不可识别该文档的操作版本:" +  pocdin000040uv02
					.getVersionNo());
			return false;
		}
		return true;
	}

	// [BUG]0009691 ADD END
	// [BUG]0011864 MODIFY END

	/*
	 * if (pocdin000040uv02.getReports() != null) { if
	 * (pocdin000040uv02.getReports().get(0).getExamItems() != null) { for
	 * (ExaminationItems examItems : pocdin000040uv02.getReports().get(
	 * 0).getExamItems()) { // $Author :jin_peng // $Date : 2012/11/23 13:59$ //
	 * [BUG]0011864 DELETE BEGIN // $Author :jin_peng // $Date : 2012/09/20
	 * 14:47$ // [BUG]0009691 ADD BEGIN // 门诊情况下验证相应申请单号是否存在
	 * 
	 * if (Constants.PATIENT_DOMAIN_OUTPATIENT.equals(
	 * pocdin000040uv02.getPatientDomain())) { if
	 * (StringUtils.isEmpty(examItems.getRequestNo())) {
	 * logger.error("非空验证未通过，门诊环境中检查申请单号存在空值！");
	 * 
	 * return false; } }
	 * 
	 * // [BUG]0009691 ADD END // [BUG]0011864 DELETE END
	 * 
	 * // $Author:wei_peng // $Date:2012/9/27 18:09 // $[BUG]0010170 DELETE
	 * BEGIN // 检查医师ID // validateList.add(examItems.getExaminingDoctor()); //
	 * logMap.put("检查医师ID不能为空！",examItems.getExaminingDoctor()); //
	 * $[BUG]0010170 DELETE BEGIN
	 * 
	 * // 检查日期 validateList.add(examItems.getExaminationDate());
	 * logMap.put("检查日期不能为空！", examItems.getExaminationDate());
	 * 
	 * // $Author:tong_meng // $Date:2012/10/16 12:57 // $[BUG]0010423 DELETE
	 * BEGIN
	 * 
	 * // 检查科室 validateList.add(examItems.getExaminationDept());
	 * logMap.put("检查科室不能为空！", examItems.getExaminationDept());
	 * 
	 * // $[BUG]0010423 DELETE END } }
	 * 
	 * }
	 */
	// $[BUG]0009144 MODIFY END

	/*
	 * String[] str = new String[validateList.size()]; b =
	 * StringUtils.isNotNullAll(validateList.toArray(str));
	 * logger.warn("新增或更新时是否所有必填项都有值: {}", b); // $Author:wei_peng //
	 * $Date:2012/8/27 15:39 // $[BUG]0009144 ADD BEGIN // 测试所用非空验证日志 if (b ==
	 * false) { Set<Entry<String, String>> entries = logMap.entrySet();
	 * Iterator<Entry<String, String>> iterator = entries.iterator();
	 * 
	 * while (iterator.hasNext()) { Entry<String, String> entry =
	 * iterator.next();
	 * 
	 * if (StringUtils.isEmpty(entry.getValue())) { logger.error("{} \n",
	 * entry.getKey()); } } } // $[BUG]0009144 ADD END
	 */
	// }
	// $Author :tong_meng
	// $Date : 2013/5/28 17:59$
	// [BUG]0015170 ADD BEGIN
	// CVIS 发的消息
	// if(Constants.MEDICAL_VISIT_CONFIM_LOGIC_TYPE.equals("1")) {
	// if (isCVIS(pocdin000040uv02))
	// {
	// Author :jia_yanqing
	// Date : 2013/10/16 16:00
	// [BUG]0038178 ADD BEGIN
	// 判断是否是超声
	// if (isEC(pocdin000040uv02)) {
	// return examItemNameIsNotNull(pocdin000040uv02);
	// }
	// // 判断是否是冠脉造影，CVIS冠脉造影及介入手术报告的信息及用术者信息去判断
	// else if (isDSA(pocdin000040uv02)) {
	// // Author :tong_meng
	// // Date : 2013/10/21 14:30
	// // [BUG]0038263 ADD BEGIN
	// if (!StringUtils.isNotNullAll(pocdin000040uv02.getItemCode(),
	// pocdin000040uv02.getItemName(),
	// pocdin000040uv02.getOperationDate(),
	// pocdin000040uv02.getOperationRoomName())) {
	// logger.error("非空验证未通过.");
	//
	// loggerBSDOCUMENT.error(
	// "Message:{}, validate:{}",
	// pocdin000040uv02.toString(),
	// "非空验证未通过，检查项目编码：" + pocdin000040uv02.getItemCode()
	// + "，检查项目名称：" + pocdin000040uv02.getItemName()
	// + "，手术日期："
	// + pocdin000040uv02.getOperationDate()
	// + "，手术间名称："
	// + pocdin000040uv02.getOperationRoomName());
	//
	// return false;
	// }
	// // [BUG]0038263 ADD END
	//
	// // Author :tong_meng
	// // Date : 2013/11/29 15:00
	// // [BUG]0040205 MODIFY BEGIN
	// if (pocdin000040uv02.getOperatorDoctors() != null
	// && !pocdin000040uv02.getOperatorDoctors().isEmpty()) {
	// for (OperatorMessage om : pocdin000040uv02.getOperatorDoctors()) {
	// if (StringUtils.isEmpty(om.getOperatorName())) {
	// logger.error("非空验证未通过，消息中存在术者节点，但术者名称不能为空。");
	//
	// loggerBSDOCUMENT.error("Message:{}, validate:{}",
	// pocdin000040uv02.toString(),
	// "非空验证未通过，消息中存在术者节点，但术者名称不能为空。");
	//
	// return false;
	// }
	// }
	//
	// }
	// if (pocdin000040uv02.getItemMessages() == null
	// || pocdin000040uv02.getItemMessages().isEmpty()) {
	// // Author :tong_meng
	// // Date : 2013/10/21 14:30
	// // [BUG]0038263 delete BEGIN
	// /*
	// * logger.error("非空验证未通过，CVIS冠脉造影及介入手术报告的信息不存在。");
	// *
	// * loggerBSDOCUMENT.error("Message:{}, validate:{}",
	// * pocdin000040uv02.toString(),
	// * "非空验证未通过，CVIS冠脉造影及介入手术报告的信息不存在。");
	// */
	// // [BUG]0038263 delete END
	// return true;
	// }
	// return examItemNameIsNotNull(pocdin000040uv02);
	// // [BUG]0040205 MODIFY END
	// }
	// logger.error("CVIS发送的消息既非超声，也非冠脉造影，故无法接收。检查类型："
	// + pocdin000040uv02.getExamType());
	//
	// loggerBSDOCUMENT.error(
	// "Message:{}, validate:{}",
	// pocdin000040uv02.toString(),
	// "CVIS发送的消息既非超声，也非冠脉造影，故无法接收。检查类型："
	// + pocdin000040uv02.getExamType());
	// return false;
	// // [BUG]0038178 ADD BEGIN
	// }
	// }
	// [BUG]0015170 ADD END

	// return true;
	// }

	/**
	 * 判断非空节点是否为空
	 * 
	 * @param itemMessages
	 * @return
	 */
	private boolean examItemNameIsNotNull(POCDIN000040UV02 pocdin000040uv02) {
		boolean flag = true;
		List<ExamItemMessage> itemMessages = pocdin000040uv02.getItemMessages();
		if (itemMessages != null && !itemMessages.isEmpty()) {
			for (ExamItemMessage examItemMessage : itemMessages) {
				if (StringUtils.isEmpty(examItemMessage.getItemName())) {
					flag = false;
					logger.error("项目名称不能为空！");
					loggerBSDOCUMENT.error("Message:{}, validate:{}",
							pocdin000040uv02.toString(), "项目名称不能为空！");
					break;
				} else {
					if (examItemMessage.getSubItemMessages() != null
							&& !examItemMessage.getSubItemMessages().isEmpty()) {
						for (SubExamItemMessage subItemMessage : examItemMessage
								.getSubItemMessages()) {
							if (StringUtils.isEmpty(subItemMessage
									.getItemName())) {
								flag = false;
								logger.error("子项目名称不能为空！");
								loggerBSDOCUMENT.error(
										"Message:{}, validate:{}",
										pocdin000040uv02.toString(),
										"子项目名称不能为空！");
								break;
							}
						}
					}
				}
			}
			if (isDSA(pocdin000040uv02)) {
				// Author :tong_meng
				// Date : 2013/11/29 15:00
				// [BUG]0040205 MODIFY BEGIN
				if (pocdin000040uv02.getOperatorDoctors() != null
						&& !pocdin000040uv02.getOperatorDoctors().isEmpty()) {
					for (OperatorMessage operator : pocdin000040uv02
							.getOperatorDoctors()) {
						if (StringUtils.isEmpty(operator.getOperatorName())) {
							flag = false;
							logger.error("术者名称不能为空！");
							loggerBSDOCUMENT.error("Message:{}, validate:{}",
									pocdin000040uv02.toString(), "术者名称不能为空！");
							break;
						}
					}
				}
				// [BUG]0040205 MODIFY END
				if (pocdin000040uv02.getAssistantDoctors() != null
						&& !pocdin000040uv02.getAssistantDoctors().isEmpty()) {
					for (AssistantMessage assistantMessage : pocdin000040uv02
							.getAssistantDoctors()) {
						if (StringUtils.isEmpty(assistantMessage
								.getOperatorName())) {
							logger.error("助手名称不能为空！");
							loggerBSDOCUMENT.error("Message:{}, validate:{}",
									pocdin000040uv02.toString(), "助手名称不能为空！");
							flag = false;
							break;
						}
					}
				}
			}
		}
		return flag;
	}

	public boolean checkDependency(POCDIN000040UV02 pocdin000040uv02,
			boolean flag) {
		if (pocdin000040uv02 != null) {
			patientLid = pocdin000040uv02.getPatientLid();

			// 北大人民 利用交叉索引表查patientlid
			PatientCrossIndex patientCrossIndex = null;
			// // $Author :jin_peng
			// // $Date : 2012/11/23 13:59$
			// // [BUG]0011864 MODIFY BEGIN
			// // $Author :jin_peng
			// // $Date : 2012/10/19 17:47$
			// // [BUG]0010593 MODIFY BEGIN
			// // $Author:wei_peng
			// // $Date:2012/9/25 15:23
			// // [BUG]0010017 MODIFY BEGIN
			// // $Author :jia_yanqing
			// // $Date : 2012/6/12 14:30$
			// // [BUG]0006669 MODIFY BEGIN
			// // $Author :tongmeng
			// // $Date : 2012/5/29 14:30$
			// // [BUG]0006657 MODIFY BEGIN
			// // 检查报告为病理学类型时，从患者交叉索引表中获取patientLid
			/*
			 * $Author: yu_yzh
			 * $Date: 2015/10/10 14:05
			 * 港大无就诊号（门诊号、住院号）病理不做特殊处理
			 * DELETE BEGIN
			 * */
			/*if (isPathlogyExamReport(pocdin000040uv02)
					|| Constants.EXAMREPORT_LEVEL_CONTROL
							.equalsIgnoreCase(MEDICAL_NO_ORDER_NO)
					|| Constants.EXAMREPORT_LEVEL_CONTROL
							.equals(MEDICAL_NO_ORDER_YES)) {
				patientCrossIndex = commonService.getPatientRecordFromPCI(
						pocdin000040uv02.getPatientDomain(),
						pocdin000040uv02.getMedicalVisitNo(),
						pocdin000040uv02.getOrgCode(),
						// 传入就诊类型
						pocdin000040uv02.getVisitTypeCode());
				if (patientCrossIndex != null) {
					// 患者本地ID
					patientLid = patientCrossIndex.getPatientLid();

					logger.info("检查报告消息,患者本地ID {}", patientLid);

					Map<String, Object> map = new HashMap<String, Object>();
					map.put("deleteFlag", Constants.NO_DELETE_FLAG);
					map.put("patientEid", patientCrossIndex.getPatientEid());

					List<Object> results = commonService.selectByCondition(
							Patient.class, map);
					if (results != null && results.size() > 0) {
						Patient patient = (Patient) results.get(0);
						patientSn = patient.getPatientSn();
					} else {
						logger.error("患者表中没有对应的患者，patientEid = "
								+ patientCrossIndex.getPatientEid());
						if (flag) {
							loggerBSDOCUMENT
									.error("Message:{}, checkDependency:{}",
											pocdin000040uv02.toString(),
											"患者表中没有对应的患者，patientEid = "
													+ patientCrossIndex
															.getPatientEid());
						}
						return false;
					}

				} else {
					logger.error(
							"MessageId:{};交叉索引表没有该患者："
									+ pocdin000040uv02.getPatientDomain()
									+ "患者ID:" + patientLid + "医疗机构编码:"
									+ pocdin000040uv02.getOrgCode() + "就诊号"
									+ pocdin000040uv02.getMedicalVisitNo(),
							pocdin000040uv02.getMessage().getId());
					if (flag) {
						loggerBSDOCUMENT.error("Message:{},checkDependency:{}",
								pocdin000040uv02.toString(), "交叉索引表没有该患者："
										+ pocdin000040uv02.getPatientDomain()
										+ "患者ID:" + patientLid + "医疗机构编码:"
										+ pocdin000040uv02.getOrgCode() + "就诊号"
										+ pocdin000040uv02.getMedicalVisitNo());
					}
					return false;
				}
			} else */
			// 港大无就诊号，病理不做特殊处理
			// DELETE END
			if (Constants.EXAMREPORT_LEVEL_CONTROL
					.equalsIgnoreCase(MEDICAL_YES_ORDER_NO)
					|| Constants.EXAMREPORT_LEVEL_CONTROL
							.equals(MEDICAL_YES_ORDER_YES)) {
				if (Constants.VERSION_NUMBER_INSERT.equals(pocdin000040uv02
						.getVersionNo())
						|| Constants.VERSION_NUMBER_UPDATE
								.equals(pocdin000040uv02.getVersionNo())
						|| Constants.VERSION_NUMBER_REPLACE
								.equals(pocdin000040uv02.getVersionNo())) {
					medicalVisit = commonService.mediVisit(patientDomain,
							patientLid, pocdin000040uv02.getVisitTimes(),
							pocdin000040uv02.getOrgCode(),
							pocdin000040uv02.getVisitOrdNo(),
							pocdin000040uv02.getVisitTypeCode());
					Map<String, Object> condition = new HashMap<String, Object>();
					condition.put("visitTimes",Integer.parseInt(pocdin000040uv02.getVisitTimes()));
					condition.put("visitOrdNo", pocdin000040uv02.getVisitOrdNo());
					condition.put("visitTypeCode", pocdin000040uv02.getVisitTypeCode());
					condition.put("patientDomain", patientDomain);
					condition.put("patientLid", patientLid);
					condition.put("orgCode", pocdin000040uv02.getOrgCode());
					condition.put("deleteFlag", Constants.NO_DELETE_FLAG);
					List<Object> re = commonService.selectByCondition(MedicalVisit.class, condition);
					if(re != null && !re.isEmpty()){
						medicalVisit = (MedicalVisit) re.get(0);
					}
					if (medicalVisit == null) {
						logger.error(
								"MessageId:{};检查申请消息新增场合,就诊信息不存在,域ID:"
										+ pocdin000040uv02.getPatientDomain()
										+ "患者ID:" + patientLid + "就诊次数:"
										+ pocdin000040uv02.getVisitTimes()
										+ "医疗机构编码:"
										+ pocdin000040uv02.getOrgCode()
										+ "就诊流水号"
										+ pocdin000040uv02.getVisitOrdNo()
										+ "就诊类型编码"
										+ pocdin000040uv02.getVisitTypeCode(),
								pocdin000040uv02.getMessage().getId());
						if (flag) {
							loggerBSDOCUMENT.error(
									"Message:{},checkDependency:{}",
									pocdin000040uv02.toString(),
									"检查申请消息新增场合,就诊信息不存在,域ID:"
											+ pocdin000040uv02
													.getPatientDomain()
											+ "患者ID:"
											+ patientLid
											+ "就诊次数:"
											+ pocdin000040uv02.getVisitTimes()
											+ "医疗机构编码:"
											+ pocdin000040uv02.getOrgCode()
											+ "就诊流水号"
											+ pocdin000040uv02.getVisitOrdNo()
											+ "就诊类型编码"
											+ pocdin000040uv02
													.getVisitTypeCode());
						}
						return false;
					}
					visitSn = medicalVisit.getVisitSn();
					patientSn = medicalVisit.getPatientSn();

				}
				// 获得患者本地ID
				// patientLid = commonService.getPatientLid(pocdin000040uv02
				// .getPatientDomain(), pocdin000040uv02.getOrderLid()
				// .get(0), pocdin000040uv02.getOrgCode());
				// logger.info("检查报告消息,患者本地ID {}", patientLid);
			}
			//
			// // [BUG]0010593 MODIFY END
			// // [BUG]0011864 MODIFY END
			//
			// if (StringUtils.isEmpty(patientLid)) {
			// logger.error(
			// "检查报告消息,患者本地ID为空. 域ID：{}；医嘱号或申请单号或就诊号：{}",
			// pocdin000040uv02.getPatientDomain(),
			// "; orderLid = " + pocdin000040uv02.getOrderLid().get(0)
			// + "; medicalVisitNo = "
			// + pocdin000040uv02.getMedicalVisitNo());
			//
			// if (flag) {
			// loggerBSDOCUMENT.error(
			// "Message:{}, checkDependency:{}",
			// pocdin000040uv02.toString(),
			// "检查报告消息,患者本地ID为空. 域ID："
			// + pocdin000040uv02.getPatientDomain()
			// + "；医嘱号或申请单号或就诊号：" + "orderLid = "
			// + pocdin000040uv02.getOrderLid().get(0)
			// + "; medicalVisitNo = "
			// + pocdin000040uv02.getMedicalVisitNo());
			// }
			//
			// return false;
			// }
			//
			// [BUG]0006119 MODIFY END
			// [BUG]0006669 MODIFY END
			// [BUG]0010017 MODIFY END

			// 插入场合
			if (Constants.VERSION_NUMBER_INSERT.equals(pocdin000040uv02
					.getVersionNo())) {

				// logger.info("检查报告新增的场合");
				// 就诊信息
				// $Author :jin_peng
				// $Date : 2012/10/16 18:08$
				// [BUG]0010434 ADD BEGIN
				// 消息版本为新增时需要先判断该消息对应的内容在数据库中是否存在，如果存在则进行更新操作，否则进行新增操作
				getExamResult(pocdin000040uv02);

				// 如果消息中内容存在于数据库中则进行更新操作
				if (examResultExist != null) {
					// $Author :jin_peng
					// $Date : 2012/10/26 15:33$
					// [BUG]0010813 ADD BEGIN
					// 针对多个项目对应同一份报告情况判断是否为更新消息
					if(visitSn==null){
						visitSn=examResultExist.getVisitSn();
					}
					Map<String, Object> itemMap = new HashMap<String, Object>();
					itemMap.put("examReportSn",
							examResultExist.getExamReportSn());
					itemMap.put("deleteFlag", Constants.NO_DELETE_FLAG);

					List<Object> examItemList = commonService
							.selectByCondition(ExaminationItem.class, itemMap);

					if (examItemList != null && !examItemList.isEmpty()) {
						for (int i = 0; i < examItemList.size(); i++) {
							ExaminationItem examItem = (ExaminationItem) examItemList
									.get(i);

							// 如果db中的相应检查项目中的影像索引号与消息中的影像索引号不相等的话则说明为多检查项目对应统一报告情况
							if (examItem.getImageIndexNo() != null
									&& (examItem.getImageIndexNo())
											.equals(pocdin000040uv02
													.getReports().get(0)
													.getImageIndex())) {
								break;
							} else {
								if (i == (examItemList.size() - 1)) {
									logger.debug("多个检查项目对应通一份报告情况");

									return true;
								}
							}
						}
					}

					// [BUG]0010813 ADD END

					logger.info("检查报告更新的场合");

					// 检查报告做更新操作时数据库中相应报告中召回状态为已召回时才可执行更新操作 控制开关(0:开启 1:关闭)
					if (Constants.COMM_INTERFACE
							.equals(Constants.WITH_DRAW_USE_FLAG)) {
						// $Author :jin_peng
						// $Date : 2012/10/17 15:36$
						// [BUG]0010520 ADD BEGIN
						// 检查报告做更新操作时数据库中相应报告中召回状态为已召回时才可执行更新操作
						if (!Constants.WITH_DRAW_FLAG.equals(examResultExist
								.getWithdrawFlag())) {
							logger.error(
									"更新检查报告时相关数据的召回状态并非为已召回状态，暂不做更新操作: {}",
									"params: examReportLid = "
											+ examResultExist
													.getExamReportLid()
											+ "; itemClass = "
											+ examResultExist.getItemClass());

							if (flag) {
								loggerBSDOCUMENT.error(
										"Message:{}, checkDependency:{}",
										pocdin000040uv02.toString(),
										"更新检查报告时相关数据的召回状态并非为已召回状态，暂不做更新操作: "
												+ "params: examReportLid = "
												+ examResultExist
														.getExamReportLid()
												+ "; itemClass = "
												+ examResultExist
														.getItemClass());
							}

							return false;
						}
					}
					// [BUG]0010520 ADD END

					pocdin000040uv02
							.setVersionNo(Constants.VERSION_NUMBER_UPDATE);

					return true;
				}

				// [BUG]0010434 ADD END

				return checkExaminationOrder(pocdin000040uv02, flag);
			}
			// 更新场合
			if (Constants.VERSION_NUMBER_UPDATE.equals(pocdin000040uv02
					.getVersionNo())) {
				logger.info("检查报告更新的场合");
				// 检查报告是否存在
				return checkExamResult(pocdin000040uv02, flag);
			}
			// 替换场合
			if (Constants.VERSION_NUMBER_REPLACE.equals(pocdin000040uv02
					.getVersionNo())) {
				logger.info("检查报告替换的场合");
				return checkExistExamResult(pocdin000040uv02, flag);
			}

			// $Author :jia_yanqing
			// $Date : 2012/6/12 14:30$
			// [BUG]0006669 DELETE BEGIN
			// 召回场合
			/*
			 * if (Constants.getVersionNumberWithdraw().equals(
			 * pocdin000040uv02.getVersionNo())) { logger.info("检查报告召回的场合");
			 * return checkWithdrawExamResult(pocdin000040uv02); }
			 */
			// [BUG]0006669 DELETE END
		}
		logger.error("消息不存在！");
		if (flag) {
			loggerBSDOCUMENT.error("Message:{}, checkDependency:{}",
					pocdin000040uv02.toString(), "消息不存在！");
		}
		return false;
	}

	/**
	 * 
	 * @param baseDto
	 */
	@Transactional
	public void saveOrUpdate(POCDIN000040UV02 pocdin000040uv02) {
		// $Author: tong_meng
		// $Date: 2013/8/30 15:00
		// [BUG]0036757 ADD BEGIN
		// serviceId = pocdin000040uv02.getMessage().getServiceId();
		// [BUG]0036757 ADD END
		systemTime = DateUtils.getSystemTime();
		if (Constants.VERSION_NUMBER_INSERT.equals(pocdin000040uv02
				.getVersionNo())) {
			// 插入场合
			insertExamResult(pocdin000040uv02);
			
		} else if (Constants.VERSION_NUMBER_UPDATE.equals(pocdin000040uv02
				.getVersionNo())
				|| Constants.VERSION_NUMBER_REPLACE.equals(pocdin000040uv02
						.getVersionNo())) {
			// 更新替换场合
			updateExamResult(pocdin000040uv02, examResultExist);
		}
		// $Author :jia_yanqing
		// $Date : 2012/6/12 14:30$
		// [BUG]0006669 DELETE BEGIN
		/*
		 * else if
		 * (Constants.VERSION_NUMBER_WITHDRAW.equals(pocdin000040uv02.getVersionNo
		 * ())) { // 召回场合 withDrawLabResult(pocdin000040uv02); }
		 */
		// [BUG]0006669 DELETE END
	}
	
	
	/**
	 * 从消息中获取CDA图片
	 * 
	 * @param pocdin000040uv03
	 *            消息信息
	 * @return CDA图片信息
	 */
	public MedicalImaging getCDAImage(ClinicalDocument clinicalDocument,POCDIN000040UV02 pocdin000040uv02,
			String flag) {
		MedicalImaging CDAImage = new MedicalImaging();
		CDAImage.setImagingSn(commonService
				.getSequence(MEDICAL_IMAGING_SEQUENCE)); // 设置影像内部序列号
		CDAImage.setImageContent(getImageContentAfterDecode(pocdin000040uv02
				.getImageText())); // 设置影像内容
		CDAImage.setPromptMessage(pocdin000040uv02.getPrompt()); // 设置提示信息
		CDAImage.setDocumentSn(clinicalDocument.getDocumentSn());// 文档内部序列号

		// 图片扩展名为image/jpg的形式时进行判断取值
		String imageExtension = pocdin000040uv02.getImageExtension();
		if (!StringUtils.isEmpty(imageExtension)) {
			if (imageExtension.indexOf("/") > 0) {
				if (imageExtension.split("/").length > 1) {
					imageExtension = imageExtension.split("/")[1];
				} else {
					imageExtension = "";
				}
			}
		}
		CDAImage.setImageFormat(imageExtension); // 设置影像格式
		CDAImage.setCreateTime(systemTime); // 设置该记录创建时间
		CDAImage.setUpdateTime(systemTime); // 设置该记录更新时间
		CDAImage.setUpdateCount(Constants.INSERT_UPDATE_COUNT); // 设置该记录初始更新回数
		CDAImage.setDeleteFlag(Constants.NO_DELETE_FLAG); // 设置该记录初始删除标记
		// $Author: tong_meng
		// $Date: 2013/8/30 15:00
		// [BUG]0036757 ADD BEGIN
		CDAImage.setCreateby(serviceId); // 设置创建人
		// [BUG]0036757 ADD END
		return CDAImage;
	}

	/**
	 * 对二进制的图片信息进行base64的转码操作
	 * 
	 * @param imageText
	 *            二进制图片信息
	 * @return 转码后的字节数据图片
	 */
	public byte[] getImageContentAfterDecode(String imageText) {
		byte[] bt = null;
		if (imageText != null && !imageText.isEmpty()) {
			bt = Base64.decodeBase64(imageText);
		}
		return bt;
	}
	
	// $Author :jia_yanqing
	// $Date : 2012/6/12 14:30$
	// [BUG]0006669 DELETE BEGIN
	/**
	 * 就诊相关信息判断
	 * 
	 * @param pocdin000040uv02
	 *            检查报告消息映射数据
	 * @return true:存在；false:不存在
	 */

	/*
	 * private boolean checkMedicalVisit(POCDIN000040UV02 pocdin000040uv02) { //
	 * 就诊消息映射数据 List<com.yly.cdr.hl7.dto.MedicalVisit> medicalVisit =
	 * pocdin000040uv02.getMedicalVisit(); visitTimes =
	 * Integer.parseInt(medicalVisit.get(0).getVisitTimes());
	 * 
	 * // 就诊信息取得 MedicalVisit medicalVisitResult = commonService.mediVisit(
	 * pocdin000040uv02.getPatientDomain(), patientLid, visitTimes); if
	 * (medicalVisitResult != null) { visitSn = medicalVisitResult.getVisitSn();
	 * patientSn = medicalVisitResult.getPatientSn();
	 * logger.info("检验申请单消息新增场合,就诊信息存在,就诊内部序列号 {},患者内部序列号{}", visitSn,
	 * patientSn); } else { logger.error("检验申请单消息新增场合,就诊信息不存在！"); return false;
	 * } return true; }
	 */
	// [BUG]0006669 DELETE END

	// $Author :jia_yanqing
	// $Date : 2012/6/12 14:30$
	// [BUG]0006669 ADD BEGIN
	/**
	 * 根据患者ID，域ID，医嘱号判断检查医嘱是否存在
	 * 
	 * @param pocdin000040uv02
	 * @param patientCrossIndex
	 * @return
	 */
	private boolean checkExaminationOrder(POCDIN000040UV02 pocdin000040uv02,
			boolean flag) {
		// $Author :jin_peng
		// $Date : 2012/11/23 13:59$
		// [BUG]0011864 MODIFY BEGIN
		// $Author :jin_peng
		// $Date : 2012/09/20 14:47$
		// [BUG]0009691 MODIFY BEGIN
		// 门诊和住院分别用申请单号和医嘱号获取相应检查医嘱进行关联关系验证
		Map<String, Object> map = new HashMap<String, Object>();

		map.put("deleteFlag", Constants.NO_DELETE_FLAG);

		boolean isFirst = true;

		/*
		 * if (Constants.PATIENT_DOMAIN_OUTPATIENT.equals(pocdin000040uv02.
		 * getPatientDomain())) {
		 * 
		 * 
		 * if (pocdin000040uv02.getReports().get(0).getExamItems() != null) { //
		 * 门诊环境下循环申请单编号验证相应关联医嘱是否存在 for (ExaminationItems examItems :
		 * pocdin000040uv02.getReports().get( 0).getExamItems()) {
		 * map.put("requestNo", examItems.getRequestNo());
		 * 
		 * // 查找数据库中报告记录 List<Object> examinationOrderList =
		 * commonService.selectByCondition( ExaminationOrder.class, map);
		 * 
		 * // 判断报告在数据库中是否存在 if (examinationOrderList != null &&
		 * !examinationOrderList.isEmpty()) { //
		 * 因为一个检查报告对应的多个医嘱的就诊和患者都是相同的所以只取一次该信息 if (isFirst) { ExaminationOrder
		 * examinationOrder = (ExaminationOrder) examinationOrderList.get(0);
		 * visitSn = examinationOrder.getVisitSn(); patientSn =
		 * examinationOrder.getPatientSn();
		 * 
		 * // $Author :tong_meng // $Date : 2012/10/16 14:20$ // [BUG]0010423
		 * ADD BEGIN examinationDept = examinationOrder.getExecutiveDept();
		 * examinationDeptName = examinationOrder.getExecDeptName(); //
		 * [BUG]0010423 ADD BEGIN
		 * 
		 * isFirst = false; } } else { // $Author :jin_peng // $Date :
		 * 2012/10/19 17:24$ // [BUG]0010594 ADD BEGIN // 查询手术医嘱是否存在记录 //
		 * 查找数据库中报告记录 List<Object> procedureOrderList =
		 * commonService.selectByCondition( ProcedureOrder.class, map);
		 * 
		 * // 判断手术医嘱是否存在 if (procedureOrderList != null &&
		 * !procedureOrderList.isEmpty()) { //
		 * 因为一个检查报告对应的多个医嘱的就诊和患者都是相同的所以只取一次该信息 if (isFirst) { ProcedureOrder
		 * procedureOrder = (ProcedureOrder) procedureOrderList.get(0); visitSn
		 * = procedureOrder.getVisitSn(); patientSn =
		 * procedureOrder.getPatientSn();
		 * 
		 * examinationDept = procedureOrder.getExecDept(); examinationDeptName =
		 * procedureOrder.getExecDeptName();
		 * 
		 * isFirst = false; } } else { logger.error(
		 * "检查报告消息消息新增场合,检查医嘱或手术医嘱信息不存在！{}", "患者本地ID：patientLid = " + patientLid
		 * + "; 域ID：patientDomain = " + pocdin000040uv02.getPatientDomain() +
		 * "申请单号：requestNo = " + examItems.getRequestNo());
		 * 
		 * return false; }
		 * 
		 * // [BUG]0010594 ADD END } } } } else {
		 */
		// 当检查报告为病理学检查报告时，从患者表获取patient_sn，从就诊表获取visit_sn
		// if (isPathlogyExamReport(pocdin000040uv02)) {
		// map.put("patientEid", patientCrossIndex.getPatientEid());
		// List<Object> results = commonService.selectByCondition(
		// Patient.class, map);
		// if (results != null && results.size() > 0) {
		// Patient patient = (Patient) results.get(0);
		// patientSn = patient.getPatientSn();
		// } else {
		// logger.error("检查报告为病理学类型时，患者表中没有对应的患者，patientEid = "
		// + patientCrossIndex.getPatientEid());
		// if (flag) {
		// loggerBSDOCUMENT.error("Message:{}, checkDependency:{}",
		// pocdin000040uv02.toString(),
		// "检查报告为病理学类型时，患者表中没有对应的患者，patientEid = "
		// + patientCrossIndex.getPatientEid());
		// }
		// return false;
		// }
		//
		// // $Author :jin_peng
		// // $Date : 2013/09/12 15:03$
		// // [BUG]0037331 MODIFY BEGIN
		// MedicalVisit medicalVisit = commonService
		// .mediVisit(
		// pocdin000040uv02.getPatientDomain(),
		// patientLid,
		// StringUtils.isEmpty(pocdin000040uv02
		// .getVisitTimes()) ? null : Integer
		// .parseInt(pocdin000040uv02.getVisitTimes()),
		// pocdin000040uv02.getOrgCode());
		//
		// // [BUG]0037331 MODIFY END
		//
		// if (medicalVisit != null) {
		// visitSn = medicalVisit.getVisitSn();
		// }
		// } else {

		if (!isPathlogyExamReport(pocdin000040uv02)
				&& !Constants.EXAMREPORT_LEVEL_CONTROL
						.equals(MEDICAL_NO_ORDER_NO)
				&& !Constants.EXAMREPORT_LEVEL_CONTROL
						.equals(MEDICAL_YES_ORDER_NO)) {
			map.put("patientLid", patientLid);
			map.put("patientSn", patientSn);
			map.put("patientDomain", pocdin000040uv02.getPatientDomain());
			map.put("orgCode", pocdin000040uv02.getOrgCode());
			if (Constants.EXAMREPORT_LEVEL_CONTROL
					.equals(MEDICAL_YES_ORDER_YES)) {
				map.put("visitSn", visitSn);
			}

			for (String orderOrRequestNo : pocdin000040uv02.getOrderLid()) {
				// 门诊住院统一节点但不同意义，门诊：申请单号，住院：医嘱号
				// if
				// (Constants.PATIENT_DOMAIN_OUTPATIENT.equals(pocdin000040uv02
				// .getPatientDomain())) {
				// map.put("requestNo", orderOrRequestNo);
				// } else {
				map.put("orderLid", orderOrRequestNo);
				// }

				// 查找数据库中报告记录
				List<Object> examinationOrderList = commonService
						.selectByCondition(ExaminationOrder.class, map);

				// 判断报告在数据库中是否存在
				if (examinationOrderList != null
						&& !examinationOrderList.isEmpty()) {
					// 因为一个检查报告对应的多个医嘱的就诊和患者都是相同的所以只取一次该信息
					if (isFirst) {
						ExaminationOrder examinationOrder = (ExaminationOrder) examinationOrderList
								.get(0);
						if (Constants.EXAMREPORT_LEVEL_CONTROL
								.equals(MEDICAL_NO_ORDER_YES)) {
							visitSn = examinationOrder.getVisitSn();
						}
						// patientSn = examinationOrder.getPatientSn();

						// $Author :tong_meng
						// $Date : 2012/10/16 14:20$
						// [BUG]0010423 ADD BEGIN
						examinationDept = examinationOrder.getExecutiveDept();
						examinationDeptName = examinationOrder
								.getExecDeptName();
						// [BUG]0010423 ADD BEGIN

						// $Author:wei_peng
						// $Date:2012/12/27 16:57
						// [BUG]0012385 ADD BEGIN
						// 检查方法
						examinationMethod = examinationOrder
								.getExamMethodCode();
						// 检查方法名称
						examinationMethodName = examinationOrder
								.getExamMethodName();
						// [BUG]0012385 ADD END

						logger.info("检查报告消息新增场合,检查医嘱信息存在,就诊内部序列号 {},患者内部序列号{}",
								visitSn, patientSn);

						isFirst = false;
					}
				} else {
					// $Author :jin_peng
					// $Date : 2012/10/19 17:24$
					// [BUG]0010594 ADD BEGIN
					// 查询手术医嘱是否存在记录
					// 查找数据库中报告记录
					List<Object> procedureOrderList = commonService
							.selectByCondition(ProcedureOrder.class, map);

					// 判断手术医嘱是否存在
					if (procedureOrderList != null
							&& !procedureOrderList.isEmpty()) {
						// 因为一个检查报告对应的多个医嘱的就诊和患者都是相同的所以只取一次该信息
						if (isFirst) {
							ProcedureOrder procedureOrder = (ProcedureOrder) procedureOrderList
									.get(0);
							if (Constants.EXAMREPORT_LEVEL_CONTROL
									.equals(MEDICAL_NO_ORDER_YES)) {
								visitSn = procedureOrder.getVisitSn();
							}
							// patientSn = procedureOrder.getPatientSn();

							examinationDept = procedureOrder.getExecDept();
							examinationDeptName = procedureOrder
									.getExecDeptName();

							logger.info(
									"检查报告消息新增场合,手术医嘱信息存在,就诊内部序列号 {},患者内部序列号{}",
									visitSn, patientSn);

							isFirst = false;
						}
					} else {
						logger.error("检查报告消息消息新增场合,检查医嘱或手术医嘱信息不存在！");

						if (flag) {
							loggerBSDOCUMENT.error(
									"Message:{}, checkDependency:{}",
									pocdin000040uv02.toString(),
									"检查报告消息消息新增场合,检查医嘱或手术医嘱信息不存在！");
						}

						return false;
					}
				}
				// [BUG]0010594 ADD END

			}
		}

		return true;
		// [BUG]0009691 MODIFY END
		// [BUG]0011864 MODIFY END
	}

	// [BUG]0006669 ADD END

	/**
	 * 检查报告是否存在
	 * 
	 * @param pocdin000040uv02检查报告消息映射数据
	 * @return true:存在；false:不存在
	 */
	private boolean checkExamResult(POCDIN000040UV02 pocdin000040uv02,
			boolean flag) {
		// $Author :jin_peng
		// $Date : 2012/10/22 14:59$
		// [BUG]0010434 MODIFY BEGIN
		// 检查报告中是否存在此报告
		this.examResultExist = examService.getReportPresence(
				pocdin000040uv02.getExamReportLid(), pocdin000040uv02
						.getReports().get(0).getItemClass(),
				pocdin000040uv02.getOrgCode());

		// [BUG]0010434 MODIFY END

		if (examResultExist == null) {
			logger.error("检查报告表不存在此报告！");
			if (flag) {
				loggerBSDOCUMENT.error("Message:{}, checkDependency:{}",
						pocdin000040uv02.toString(), "检查报告表不存在此报告！");
			}
			return false;
		} else {
			// 检查报告做更新操作时数据库中相应报告中召回状态为已召回时才可执行更新操作 控制开关(0:开启 1:关闭)
			if (Constants.COMM_INTERFACE.equals(Constants.WITH_DRAW_USE_FLAG)) {

				// $Author :jin_peng
				// $Date : 2012/10/17 15:36$
				// [BUG]0010520 ADD BEGIN
				// 检查报告做更新操作时数据库中相应报告中召回状态为已召回时才可执行更新操作
				if (!Constants.WITH_DRAW_FLAG.equals(examResultExist
						.getWithdrawFlag())) {
					logger.error(
							"更新检查报告时相关数据的召回状态并非为已召回状态，暂不做更新操作: {}",
							"params: examReportLid = "
									+ examResultExist.getExamReportLid()
									+ "; itemClass = "
									+ examResultExist.getItemClass());
					if (flag) {
						loggerBSDOCUMENT.error(
								"Message:{}, checkDependency:{}",
								pocdin000040uv02.toString(),
								"更新检查报告时相关数据的召回状态并非为已召回状态，暂不做更新操作: "
										+ "params: examReportLid = "
										+ examResultExist.getExamReportLid()
										+ "; itemClass = "
										+ examResultExist.getItemClass());
					}
					return false;
				}
			}
			// [BUG]0010520 ADD END
		}

		// documentSnToDelete = examResultExist.getDocumentSn();
		return true;
	}

	// $Author :jin_peng
	// $Date : 2012/10/16 18:08$
	// [BUG]0010434 ADD BEGIN
	/**
	 * 获取已存在的检查报告
	 * 
	 * @param pocdin000040uv02检查报告消息映射数据
	 */
	private void getExamResult(POCDIN000040UV02 pocdin000040uv02) {
		// $Author :jin_peng
		// $Date : 2012/10/22 14:59$
		// [BUG]0010434 MODIFY BEGIN
		// 检查报告中是否存在此报告
		this.examResultExist = examService.getReportPresence(
				pocdin000040uv02.getExamReportLid(), pocdin000040uv02
						.getReports().get(0).getItemClass(),
				pocdin000040uv02.getOrgCode());

		// [BUG]0010434 MODIFY END

		// if (examResultExist != null)
		// {
		// documentSnToDelete = examResultExist.getDocumentSn();
		// }
	}

	// [BUG]0010434 ADD END

	// $Author :jia_yanqing
	// $Date : 2012/6/12 14:30$
	// [BUG]0006669 DELETE BEGIN
	/*
	 * private boolean checkWithdrawExamResult(POCDIN000040UV02
	 * pocdin000040uv02) { // 通过本地报告ID、检查科室查询相应的检验报告
	 * this.withdrawExamResultExist = examService.getReportPresence(
	 * pocdin000040uv02.getExamReportLid(), pocdin000040uv02.getExamDept()); if
	 * (withdrawExamResultExist == null) { logger.error("检查报告表不存在此报告！"); return
	 * false; } return true; }
	 */
	// [BUG]0006669 DELETE END

	/**
	 * 检查报告新规
	 * 
	 * @param pocdin000040uv02
	 *            消息映射数据
	 */
	private void insertExamResult(POCDIN000040UV02 pocdin000040uv02) {
		// $Author :jia_yanqing
		// $Date : 2012/6/12 14:30$
		// [BUG]0006669 ADD BEGIN
		ExaminationResult examinationResult = checkExaminationResult(pocdin000040uv02);

		//增加存储CDA信息
		MedicalImaging medicalImaging = null;
		// $Author :jin_peng
		// $Date : 2012/11/23 13:59$
		// [BUG]0011864 MODIFY BEGIN
		// $Author :jin_peng
		// $Date : 2012/09/20 14:47$
		// [BUG]0009691 MODIFY BEGIN
		/*
		 * List<String> requestNoList = new ArrayList<String>();
		 * 
		 * if (Constants.PATIENT_DOMAIN_OUTPATIENT.equals(pocdin000040uv02.
		 * getPatientDomain())) { if
		 * (pocdin000040uv02.getReports().get(0).getExamItems() != null) { for
		 * (ExaminationItems examItems : pocdin000040uv02.getReports().get(
		 * 0).getExamItems()) { requestNoList.add(examItems.getRequestNo()); } }
		 * }
		 */

		// 新增时先判断数据库是否已经存在该检查报告，若存在，则只更新关联检查医嘱的报告内部序列号
		if (examinationResult != null) {
			examReportSn = examinationResult.getExamReportSn();

			// 检查项目
			List<List<Object>> examItemList = getExamItems(pocdin000040uv02,
					Constants.VERSION_NUMBER_INSERT);
			
			//获取CDA信息
			clinicalDocument = clinicalDocumentService.selectDocByLid(pocdin000040uv02.getExamReportLid(), 
					patientLid, serviceId, pocdin000040uv02.getOrgCode());
			
			medicalImaging = getCDAImage(clinicalDocument,pocdin000040uv02,"");
			// 执行更新操作
			clinicalDocumentService.updateClinicalDocument(
					clinicalDocument, medicalImaging,
					pocdin000040uv02.getMessage(),systemTime);
			
			examService.updateExamOrderReportSn(examReportSn,
					pocdin000040uv02.getPatientDomain(), patientLid,
					pocdin000040uv02.getOrderLid(),
					pocdin000040uv02.getOrgCode(), systemTime, examItemList,
					serviceId, visitSn);

		} else {
			// [BUG]0006669 ADD END

			documentSn = commonService.getSequence(DOCUMENT_SEQUENCE);
			// 检查报告
			List<Object> reportList = getExamResult(pocdin000040uv02, null);

			// 病案文档
			clinicalDocument = getClinicalDocument(
					pocdin000040uv02, (ExaminationResult) reportList.get(0));

			//PDF及图像信息
			medicalImaging = getCDAImage(clinicalDocument,pocdin000040uv02, "");
			
			// $Author :tong_meng
			// $Date : 2013/7/1 14:00$
			// [BUG]0033936 MODIFY BEGIN
			// 检查项目
			List<List<Object>> examItemList = null;
			// [BUG]0033936 MODIFY END

			// 检验影像
			/*List<List<Object>> labImageList = Constants.MEDICAL_IMAGE_FLAG.equals(MEDICAL_IMAGE_CLOSE)?null:getLabImage(pocdin000040uv02,
					Constants.VERSION_NUMBER_INSERT);*/
			List<List<Object>> labImageList = !Boolean.parseBoolean(Constants.MEDICAL_IMAGE_FLAG) ? null : getLabImage(pocdin000040uv02,
					Constants.VERSION_NUMBER_INSERT);
			// $Author :tong_meng
			// $Date : 2013/7/1 14:00$
			// [BUG]0033936 ADD BEGIN
			// $Author :tong_meng
			// $Date : 2013/5/28 17:59$
			// [BUG]0015170 ADD BEGIN
			List<Object> examResult = new ArrayList<Object>();
			ExaminationResultProcedure resultReplication = null;
			// if (isCVIS(pocdin000040uv02)) {
			if (isEC(pocdin000040uv02) || isECG(pocdin000040uv02)) {
				examItemList = getExamItems(pocdin000040uv02,
						Constants.VERSION_NUMBER_INSERT);
				examResult = getResultItems(pocdin000040uv02,
						examItemList.get(0), null);
			} else if (isDSA(pocdin000040uv02)) {
				BigDecimal examResultReplicationSn = commonService
						.getSequence("SEQ_EXAM_RESULT_ITEM");
				resultReplication = getResultProcedure(pocdin000040uv02,
						examResultReplicationSn);
				examResult = getResultItems(pocdin000040uv02, null,
						resultReplication);
			}
			// } else {
			else {
				examItemList = getExamItems(pocdin000040uv02,
						Constants.VERSION_NUMBER_INSERT);
			}
			// }
			// [BUG]0015170 ADD BEGIN
			// [BUG]0033936 ADD END

			examService.updateInsertExamResult(pocdin000040uv02.getMessage(),
					reportList, examItemList, labImageList,
					pocdin000040uv02.getVersionNo(), examReportSn,
					pocdin000040uv02.getPatientDomain(), patientLid,
					resultReplication, examResult,
					pocdin000040uv02.getOrderLid(), clinicalDocument, null,
					pocdin000040uv02.getOrgCode(), systemTime, serviceId,
					visitSn);

			clinicalDocumentService.saveMedicalImaging(clinicalDocument, medicalImaging);
			// $Author :jia_yanqing
			// $Date : 2012/6/12 14:30$
			// [BUG]0006669 ADD BEGIN
		}
		// [BUG]0009691 MODIFY END
		// [BUG]0006669 ADD END
		// [BUG]0011864 MODIFY END
	}

	/**
	 * 保存冠脉造影及介入手术信息
	 * 
	 * @param pocdin000040uv02
	 * @return
	 */
	private ExaminationResultProcedure getResultProcedure(
			POCDIN000040UV02 pocdin000040uv02,
			BigDecimal examResultReplicationSn) {
		/**
		 * 如果是更新场合，物理删除掉找到的所有记录，然后再重新插一遍
		 */
		if (Constants.VERSION_NUMBER_UPDATE.equals(pocdin000040uv02
				.getVersionNo())) {
			Map<String, Object> condition = new HashMap<String, Object>();
			condition.put("examReportSn", examReportSn);
			condition.put("deleteFlag", Constants.NO_DELETE_FLAG);
			List<Object> examResultReplicationList = commonService
					.selectByCondition(ExaminationResultProcedure.class,
							condition);
			commonService.deleteList(examResultReplicationList);
		}
		ExaminationResultProcedure replication = new ExaminationResultProcedure();
		replication.setExamResultProcedureSn(examResultReplicationSn);
		// replication.setImageIndexNo(pocdin000040uv02.getMoviePhoneNo());
		replication.setExamReportSn(examReportSn);
		replication.setOperationDate(DateUtils.stringToDate(pocdin000040uv02
				.getOperationDate()));
		// $Author :tong_meng
		// $Date : 2013/9/26 14:07$
		// [BUG]0037586 ADD BEGIN
		replication.setExaminationItemCode(pocdin000040uv02.getItemCode());
		replication.setExaminationItemName(pocdin000040uv02.getItemName());
		// [BUG]0037586 ADD BEGIN
		replication.setOperationRoomId(pocdin000040uv02.getOperationRoomId());
		replication.setOperationRoomName(pocdin000040uv02
				.getOperationRoomName());
		StringBuffer bufferId = new StringBuffer();
		StringBuffer bufferName = new StringBuffer();
		String subBufferId = null;
		String subBufferName = null;
		// Author :tong_meng
		// Date : 2013/11/29 15:00
		// [BUG]0040205 MODIFY BEGIN
		if (pocdin000040uv02.getOperatorDoctors() != null
				&& !pocdin000040uv02.getOperatorDoctors().isEmpty()) {
			for (OperatorMessage operator : pocdin000040uv02
					.getOperatorDoctors()) {
				bufferId.append(operator.getOperatorCode() + "、");
				bufferName.append(operator.getOperatorName() + "、");
			}
			subBufferId = bufferId.substring(0, bufferId.length() - 1);
			subBufferName = bufferName.substring(0, bufferName.length() - 1);
		}
		// [BUG]0040205 MODIFY END
		replication.setOperatorDoctorId(subBufferId);
		replication.setOperatorDoctorName(subBufferName);
		if (pocdin000040uv02.getAssistantDoctors() != null
				&& !pocdin000040uv02.getAssistantDoctors().isEmpty()) {
			bufferId.delete(0, bufferId.length());
			bufferName.delete(0, bufferName.length());
			for (AssistantMessage assistant : pocdin000040uv02
					.getAssistantDoctors()) {
				bufferId.append(assistant.getOperatorCode() + "、");
				bufferName.append(assistant.getOperatorName() + "、");
			}
			subBufferId = bufferId.substring(0, bufferId.length() - 1);
			subBufferName = bufferName.substring(0, bufferName.length() - 1);
		}
		replication.setOperatorAssistantId(subBufferId);
		replication.setOperatorAssistantName(subBufferName);
		replication.setCreateTime(systemTime);
		replication.setUpdateTime(systemTime);
		replication.setUpdateCount(Constants.INSERT_UPDATE_COUNT);
		replication.setDeleteFlag(Constants.NO_DELETE_FLAG);
		replication.setDeleteTime(null);
		// $Author: tong_meng
		// $Date: 2013/8/30 15:00
		// [BUG]0036757 ADD BEGIN
		replication.setCreateby(serviceId); // 设置创建人
		// [BUG]0036757 ADD END

		return replication;
	}

	/**
	 * 保存检查项目具体结果
	 * 
	 * @param pocdin000040uv02
	 * @param examItemList
	 * @return
	 */
	private List<Object> getResultItems(POCDIN000040UV02 pocdin000040uv02,
			List<Object> examItemList,
			ExaminationResultProcedure resultReplication) {
		/**
		 * 如果是更新场合，物理删除掉找到的所有记录，然后再重新插一遍
		 */
		if (Constants.VERSION_NUMBER_UPDATE.equals(pocdin000040uv02
				.getVersionNo())
				|| Constants.VERSION_NUMBER_REPLACE.equals(pocdin000040uv02
						.getVersionNo())) {
			// Author :tong_meng
			// Date : 2013/10/24 14:30
			// [BUG]0038485 MODIFY BEGIN
			if (examItemList != null && !examItemList.isEmpty()) {
				for (Object object : examItemList) {
					ExaminationItem examReDel = (ExaminationItem) object;
					BigDecimal itemSn = examReDel.getItemSn();
					Map<String, Object> condition = new HashMap<String, Object>();
					if (isEC(pocdin000040uv02) || isECG(pocdin000040uv02))
						condition.put("itemSn", itemSn);
					else
						condition.put("examReportSn",
								resultReplication.getExamReportSn());
					condition.put("deleteFlag", Constants.NO_DELETE_FLAG);
					List<Object> examResultItemList = commonService
							.selectByCondition(ExaminationResultDetail.class,
									condition);
					commonService.deleteList(examResultItemList);
				}
			}
			// [BUG]0038485 MODIFY END
		}
		List<Object> itemMessages = new ArrayList<Object>();

		if (isEC(pocdin000040uv02) || isECG(pocdin000040uv02)) {
			List<ExaminationItems> examinationItems = pocdin000040uv02
					.getReports().get(0).getExamItems();
			for (int index = 0; index < examinationItems.size(); index++) {
				ExaminationItems examinationItem = examinationItems.get(0);
				addItemMessages(examinationItem.getItemMessages(),
						itemMessages, null, pocdin000040uv02, examItemList,
						index);
			}
		} else if (isDSA(pocdin000040uv02)) {
			addItemMessages(pocdin000040uv02.getItemMessages(), itemMessages,
					resultReplication.getExamResultProcedureSn(),
					pocdin000040uv02, examItemList, 0);
		}
		return itemMessages;
	}

	private void addItemMessages(List<ExamItemMessage> examItemMessages,
			List<Object> itemMessages, BigDecimal examResultReplicationSn,
			POCDIN000040UV02 pocdin000040uv02, List<Object> examItemList,
			int index) {
		// Author :tong_meng
		// Date : 2013/10/21 14:30
		// [BUG]0038263 delete BEGIN
		if (examItemMessages.size() == 0 && examItemMessages.isEmpty()) {
			return;
		}
		// [BUG]0038263 delete END

		for (ExamItemMessage examItemMessage : examItemMessages) {
			ExaminationItem examItem = null;
			ExaminationResultDetail item = new ExaminationResultDetail();
			BigDecimal examResultItemSn = commonService
					.getSequence("SEQ_EXAM_RESULT_ITEM");
			item.setExaminationResultDetailSn(examResultItemSn);
			if (isEC(pocdin000040uv02) || isECG(pocdin000040uv02)) {
				examItem = (ExaminationItem) examItemList.get(index);
				item.setItemSn(examItem.getItemSn());
			} else {
				item.setExamResultProcedureSn(examResultReplicationSn);
			}
			item.setItemClass(pocdin000040uv02.getExamType());
			item.setItemCode(examItemMessage.getItemCode());
			item.setItemName(examItemMessage.getItemName());
			if (!StringUtils.isEmpty(examItemMessage.getPqItemTextValue())
					|| !StringUtils
							.isEmpty(examItemMessage.getPqItemTextUnit())) {
				item.setItemValue(examItemMessage.getPqItemTextValue()
						+ examItemMessage.getPqItemTextUnit());
			} else if (!StringUtils.isEmpty(examItemMessage.getItemText())) {
				item.setItemValue(examItemMessage.getItemText());
			} else if (!StringUtils.isEmpty(examItemMessage
					.getPqItemTextValue())
					|| StringUtils.isEmpty(examItemMessage.getPqItemTextUnit())) {
				item.setItemValue(examItemMessage.getPqItemTextValue());
			} else if (StringUtils
					.isEmpty(examItemMessage.getPqItemTextValue())
					|| !StringUtils
							.isEmpty(examItemMessage.getPqItemTextUnit())) {
				item.setItemValue(examItemMessage.getPqItemTextUnit());
			}
			item.setCreateTime(systemTime);
			item.setUpdateTime(systemTime);
			item.setUpdateCount(Constants.INSERT_UPDATE_COUNT);
			item.setDeleteFlag(Constants.NO_DELETE_FLAG);

			// $Author: tong_meng
			// $Date: 2013/8/30 15:00
			// [BUG]0036757 ADD BEGIN
			item.setCreateby(pocdin000040uv02.getMessage().getServiceId()); // 设置创建人
			// [BUG]0036757 ADD END

			item.setDeleteTime(null);

			itemMessages.add(item);

			if (examItemMessage.getSubItemMessages() != null
					&& !examItemMessage.getSubItemMessages().isEmpty()) {
				for (SubExamItemMessage subItem : examItemMessage
						.getSubItemMessages()) {
					ExaminationResultDetail subResultItem = new ExaminationResultDetail();
					subResultItem.setExaminationResultDetailSn(commonService
							.getSequence("SEQ_EXAM_RESULT_ITEM"));
					if (isEC(pocdin000040uv02) || isECG(pocdin000040uv02)) {
						subResultItem.setItemSn(examItem.getItemSn());
					} else {
						subResultItem
								.setExamResultProcedureSn(examResultReplicationSn);
					}
					subResultItem.setParentExamResultDetailSn(StringUtils
							.BigDecimalToStr(examResultItemSn));
					subResultItem.setItemClass(pocdin000040uv02.getExamType());
					subResultItem.setItemCode(subItem.getItemCode());
					subResultItem.setItemName(subItem.getItemName());
					if (!StringUtils.isEmpty(subItem.getPqItemTextValue())
							&& !StringUtils
									.isEmpty(subItem.getPqItemTextUnit())) {
						subResultItem.setItemValue(subItem.getPqItemTextValue()
								+ subItem.getPqItemTextUnit());
					} else if (!StringUtils.isEmpty(subItem.getItemText())) {
						subResultItem.setItemValue(subItem.getItemText());
					} else if (!StringUtils.isEmpty(subItem
							.getPqItemTextValue())
							&& StringUtils.isEmpty(subItem.getPqItemTextUnit())) {
						subResultItem
								.setItemValue(subItem.getPqItemTextValue());
					} else if (StringUtils
							.isEmpty(subItem.getPqItemTextValue())
							&& !StringUtils
									.isEmpty(subItem.getPqItemTextUnit())) {
						subResultItem.setItemValue(subItem.getPqItemTextUnit());
					}
					subResultItem.setText(subItem.getText());
					// subResultItem.setItemValue(subItem.getItemText());
					subResultItem.setCreateTime(systemTime);
					subResultItem.setUpdateTime(systemTime);
					subResultItem.setUpdateCount(Constants.INSERT_UPDATE_COUNT);
					subResultItem.setDeleteFlag(Constants.NO_DELETE_FLAG);
					subResultItem.setDeleteTime(null);
					// $Author: tong_meng
					// $Date: 2013/8/30 15:00
					// [BUG]0036757 ADD BEGIN
					subResultItem.setCreateby(pocdin000040uv02.getMessage()
							.getServiceId()); // 设置创建人
					// [BUG]0036757 ADD END

					itemMessages.add(subResultItem);
				}
			}
		}
	}

	// $Author :jia_yanqing
	// $Date : 2012/6/12 14:30$
	// [BUG]0006669 ADD BEGIN
	/**
	 * 新增时：根据患者Lid（影像号），域ID，报告号查询数据库中报告记录是否已存在
	 * 
	 * @param pocdin000040uv02
	 * @return
	 */
	public ExaminationResult checkExaminationResult(
			POCDIN000040UV02 pocdin000040uv02) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("patientLid", patientLid);
		map.put("patientDomain", pocdin000040uv02.getPatientDomain());
		map.put("orgCode", pocdin000040uv02.getOrgCode());
		map.put("deleteFlag", Constants.NO_DELETE_FLAG);
		map.put("examReportLid", pocdin000040uv02.getExamReportLid());

		// $Author :jin_peng
		// $Date : 2012/10/22 16:55$
		// [BUG]0010601 ADD BEGIN
		// 根据多检查系统报告，增加检查类型查询条件
		map.put("itemClass", pocdin000040uv02.getReports().get(0)
				.getItemClass());

		// [BUG]0010601 ADD END

		// 查找数据库中报告记录
		List<Object> examinationResultList = commonService.selectByCondition(
				ExaminationResult.class, map);
		// 判断报告在数据库中是否存在
		if (examinationResultList != null && examinationResultList.size() > 0) {
			ExaminationResult examinationResult = (ExaminationResult) examinationResultList
					.get(0);
			return examinationResult;
		} else {
			return null;
		}
	}

	// [BUG]0006669 ADD END

	/**
	 * 检查报告更新
	 * 
	 * @param pocdin000040uv02
	 * @param examResult
	 *            已存在检查报告数据
	 */
	private void updateExamResult(POCDIN000040UV02 pocdin000040uv02,
			ExaminationResult examResultReport) {
		// $Author :jin_peng
		// $Date : 2013/07/10 14:38$
		// [BUG]0034638 DELETE BEGIN
		// documentSn = commonService.getSequence(DOCUMENT_SEQUENCE);
		// [BUG]0034638 DELETE END

		// 检查报告
		List<Object> reportList = getExamResult(pocdin000040uv02,
				examResultReport);

		// 病案文档
		ClinicalDocument clinicalDocument = getClinicalDocument(
				pocdin000040uv02, (ExaminationResult) reportList.get(0));

		// 获取检查项目对象集合
		List<List<Object>> examItemList = null;

		// 获取医学影像对象集合
		/*List<List<Object>> resImageList = Constants.MEDICAL_IMAGE_FLAG.equals(MEDICAL_IMAGE_CLOSE)?null:getLabImage(pocdin000040uv02,
				Constants.VERSION_NUMBER_UPDATE);*/
		List<List<Object>> resImageList = !Boolean.parseBoolean(Constants.MEDICAL_IMAGE_FLAG) ? null : getLabImage(pocdin000040uv02,
				Constants.VERSION_NUMBER_UPDATE);
		// $Author :tong_meng
		// $Date : 2013/7/1 14:00$
		// [BUG]0033936 ADD BEGIN
		// $Author :tong_meng
		// $Date : 2013/5/28 17:59$
		// [BUG]0015170 ADD BEGIN
		List<Object> examResult = new ArrayList<Object>();
		ExaminationResultProcedure resultReplication = null;
		// if (isCVIS(pocdin000040uv02)) {
		if (isEC(pocdin000040uv02) || isECG(pocdin000040uv02)) {
			examItemList = getExamItems(pocdin000040uv02,
					Constants.VERSION_NUMBER_INSERT);
			examResult = getResultItems(pocdin000040uv02, examItemList.get(0),
					null);
		} else if (isDSA(pocdin000040uv02)) {
			BigDecimal examResultReplicationSn = commonService
					.getSequence("SEQ_EXAM_RESULT_ITEM");
			resultReplication = getResultProcedure(pocdin000040uv02,
					examResultReplicationSn);
			examResult = getResultItems(pocdin000040uv02, null,
					resultReplication);
		}
		// }
		else {
			examItemList = getExamItems(pocdin000040uv02,
					Constants.VERSION_NUMBER_INSERT);
		}
		// [BUG]0015170 ADD BEGIN
		// [BUG]0033936 ADD END

		// $Author :jin_peng
		// $Date : 2013/07/10 14:38$
		// [BUG]0034638 MODIFY BEGIN
		// $Author :jin_peng
		// $Date : 2012/09/20 14:47$
		// [BUG]0009691 MODIFY BEGIN
		examService.updateInsertExamResult(pocdin000040uv02.getMessage(),
				reportList, examItemList, resImageList,
				pocdin000040uv02.getVersionNo(), null, null, null,
				resultReplication, examResult, null, clinicalDocument, null,
				pocdin000040uv02.getOrgCode(), systemTime, serviceId, visitSn);
		// [BUG]0009691 MODIFY END
		// [BUG]0034638 MODIFY END
		//PDF及图像信息
		MedicalImaging medicalImaging = getCDAImage(clinicalDocument,pocdin000040uv02, "");
		clinicalDocumentService.updateMedicalImaging(clinicalDocument, medicalImaging,
				systemTime);
	}

	/**
	 * 病历文档信息赋值
	 * 
	 * @param pocdin000040uv02
	 * @return
	 */
	private ClinicalDocument getClinicalDocument(
			POCDIN000040UV02 pocdin000040uv02, ExaminationResult examResult) {
		// $Author :jin_peng
		// $Date : 2013/07/10 14:38$
		// [BUG]0034638 MODIFY BEGIN
		ClinicalDocument clinicalDocument = null;

		if (Constants.VERSION_NUMBER_UPDATE.equals(pocdin000040uv02
				.getVersionNo())
				|| Constants.VERSION_NUMBER_REPLACE.equals(pocdin000040uv02
						.getVersionNo())) {
			Map<String, Object> condition = new HashMap<String, Object>();
			condition.put("documentSn", examResult.getDocumentSn());
			condition.put("deleteFlag", Constants.NO_DELETE_FLAG);
			List<Object> clinicalDocuments = commonService.selectByCondition(
					ClinicalDocument.class, condition);
			if (clinicalDocuments != null && !clinicalDocuments.isEmpty()) {
				// 获取病历文档
				clinicalDocument = (ClinicalDocument) clinicalDocuments.get(0);
			}
		} else {
			clinicalDocument = new ClinicalDocument();

			// 文档内部序列号
			clinicalDocument.setDocumentSn(documentSn);
			// 创建时间
			clinicalDocument.setCreateTime(systemTime);
			// 删除标志
			clinicalDocument.setDeleteFlag(Constants.NO_DELETE_FLAG);
			// 更新回数
			clinicalDocument.setUpdateCount(Constants.INSERT_UPDATE_COUNT);
		}

		// [BUG]0034638 MODIFY END

		// 更新时间
		clinicalDocument.setUpdateTime(systemTime);
		// 就诊内部序列号
		clinicalDocument.setVisitSn(examResult.getVisitSn());
		// 患者内部序列号
		clinicalDocument.setPatientSn(examResult.getPatientSn());
		// 域代码
		clinicalDocument.setPatientDomain(pocdin000040uv02.getPatientDomain());
		// 患者本地ID
		clinicalDocument.setPatientLid(patientLid);
		// 文档本地ID
		clinicalDocument.setDocumentLid(pocdin000040uv02.getExamReportLid());
		// 文档类型
		clinicalDocument.setDocumentType(pocdin000040uv02.getDocumentType());
		// $Author:wei_peng
		// $Date:2012/8/30 11:03
		// $[BUG]0009064 MODIFY BEGIN
		// 文档类型名称
		clinicalDocument.setDocumentTypeName(pocdin000040uv02
				.getDocumentTypeName());
		// $[BUG]0009064 MODIFY END
		// 文档名称
		clinicalDocument.setDocumentName(pocdin000040uv02.getDocumentName());
		StringBuffer reportDoctorId = new StringBuffer();
		StringBuffer reportDoctorName = new StringBuffer();
		// 获取检查报告人信息
		getReportDoctorInfo(reportDoctorId, reportDoctorName, pocdin000040uv02);
		// 文档作者名称
		clinicalDocument.setDocumentAuthorName(reportDoctorName.toString());
		// 文档作者ID
		clinicalDocument.setDocumentAuthor(reportDoctorId.toString());
		// 获取最近审核的审核人信息
		ReviewDoctor reviewDoctor = getLatestOrFirstReviewDoctor(
				pocdin000040uv02.getReviewDoctors(), "latest");
		// 文档审核人ID
		clinicalDocument.setReviewPerson(reviewDoctor.getReviewDoctor());
		// 文档审核人姓名
		clinicalDocument
				.setReviewPersonName(reviewDoctor.getReviewDoctorName());
		// 文档审核时间
		clinicalDocument.setReviewTime(DateUtils.stringToDate(reviewDoctor
				.getReviewDate()));
		// 文档做成时间
		clinicalDocument.setWriteTime(DateUtils.stringToDate(pocdin000040uv02
				.getReportDoctors().get(0).getReportDate()));
		// 提交时间（签章时间）
		clinicalDocument.setSignTime(DateUtils.stringToDate(pocdin000040uv02
				.getSignTime()));
		// 电子签章ID
		clinicalDocument.setSignatureId(pocdin000040uv02.getSignatureId());

		// $Author:chunlin jing
		// $Date:2013/7/17
		// $[BUG]0034809 ADD BEGIN
		// 增加消息服务ID
		clinicalDocument.setServiceId(pocdin000040uv02.getMessage()
				.getServiceId());
		// $[BUG]0034809 ADD END

		// 医疗机构编码
		clinicalDocument.setOrgCode(pocdin000040uv02.getOrgCode());
		// 医疗机构名称
		clinicalDocument.setOrgName(pocdin000040uv02.getOrgName());

		// $Author: tong_meng
		// $Date: 2013/8/30 15:00
		// [BUG]0036757 ADD BEGIN
		if (Constants.VERSION_NUMBER_INSERT.equals(pocdin000040uv02
				.getVersionNo())) {
			clinicalDocument.setCreateby(serviceId); // 设置创建人
		} else if (Constants.VERSION_NUMBER_UPDATE.equals(pocdin000040uv02
				.getVersionNo())
				|| Constants.VERSION_NUMBER_REPLACE.equals(pocdin000040uv02
						.getVersionNo())) {
			clinicalDocument.setUpdateby(serviceId); // 设置创建人
		}
		// [BUG]0036757 ADD END

		return clinicalDocument;
	}

	/**
	 * 检查报告消息属性赋值
	 * 
	 * @param pocdin000040uv02
	 * @return
	 */
	private List<Object> getExamResult(POCDIN000040UV02 pocdin000040uv02,
			ExaminationResult examResultExistReport) {
		List<Object> examResultList = new ArrayList<Object>();
		ExaminationResult examResult = null;
		// 报告内容
		List<com.yly.cdr.hl7.dto.ExaminationResult> reports = pocdin000040uv02
				.getReports();
		for (com.yly.cdr.hl7.dto.ExaminationResult examReport : reports) {
			if (Constants.VERSION_NUMBER_UPDATE.equals(pocdin000040uv02
					.getVersionNo())) {
				// 检查报告更新场合
				examReportSn = examResultExistReport.getExamReportSn();
				examResult = examResultExistReport;
				// $Author: tong_meng
				// $Date: 2013/8/30 15:00
				// [BUG]0036757 ADD BEGIN
				examResult.setUpdateby(serviceId); // 设置创建人
				// [BUG]0036757 ADD END
			} else if (Constants.VERSION_NUMBER_REPLACE.equals(pocdin000040uv02
					.getVersionNo())) {
				// 检查报告替换场合
				examReportSn = examResultExistReport.getExamReportSn();
				examResult = examResultExistReport;
				// $Author: tong_meng
				// $Date: 2013/8/30 15:00
				// [BUG]0036757 ADD BEGIN
				examResult.setUpdateby(serviceId); // 设置创建人
				examResult
						.setExamReportLid(pocdin000040uv02.getExamReportLid());
			} else if (Constants.VERSION_NUMBER_INSERT.equals(pocdin000040uv02
					.getVersionNo())) {
				// 检查报告新增场合
				examResult = new ExaminationResult();

				// 检查报告内部序列号
				BigDecimal seq = null;
				seq = commonService.getSequence(REPORT_SEQUENCE);
				examReportSn = seq;
				examResult.setExamReportSn(seq);
				// 就诊内部序列号
				examResult.setVisitSn(visitSn);
				// 患者内部序列号
				examResult.setPatientSn(patientSn);
				// 域代码
				examResult
						.setPatientDomain(pocdin000040uv02.getPatientDomain());
				// 报告本地ID
				examResult
						.setExamReportLid(pocdin000040uv02.getExamReportLid());
				// 患者本地ID
				examResult.setPatientLid(patientLid);
				// 文档内部序列号
				examResult.setDocumentSn(documentSn);

				// $Author :tong_meng
				// $Date : 2013/5/28 17:59$
				// [BUG]0015170 ADD BEGIN
				String patientAge = pocdin000040uv02.getAge();
				if(pocdin000040uv02.getAgeUnit() != null){
					patientAge += pocdin000040uv02.getAgeUnit();
				}
				examResult.setPatientAge(patientAge);
//				examResult.setPatientAge(pocdin000040uv02.getAge());
				// [BUG]0015170 ADD END

				// $Author :tong_meng
				// $Date : 2013/7/1 14:00$
				// [BUG]0033936 ADD BEGIN
				examResult.setDataSourceType(pocdin000040uv02
						.getExamReportRoot());
				if (Constants.EXAM_ITEMCLASS_CORONARY_ANGIOGRAPHY
						.equals(examReport.getItemClass())) {
					examResult
							.setReportTypeCode(examReport.getReportTypeCode());
					examResult
							.setReportTypeName(examReport.getReportTypeName());
				}

				if (isPathlogyExamReport(pocdin000040uv02)) {
					examResult.setPathlogyNo(pocdin000040uv02.getPathlogyNo());
				}
				// [BUG]0033936 ADD END

				// 医疗机构编码
				examResult.setOrgCode(pocdin000040uv02.getOrgCode());
				// 医疗机构名称
				examResult.setOrgName(pocdin000040uv02.getOrgName());
				// 更新回数
				examResult.setUpdateCount(Constants.INSERT_UPDATE_COUNT);
				// 创建时间
				examResult.setCreateTime(systemTime);
				// 删除标志
				examResult.setDeleteFlag(Constants.NO_DELETE_FLAG);

				// $Author: tong_meng
				// $Date: 2013/8/30 15:00
				// [BUG]0036757 ADD BEGIN
				examResult.setCreateby(serviceId); // 设置创建人
				// [BUG]0036757 ADD END

				// $Author: DU_XIAOLAN
				// $Date: 2015/2/2
				// ADD BEGIN
				if (isES(pocdin000040uv02)) {
					examResult.setHpCode(pocdin000040uv02.getHpCode());
					examResult.setHpValue(pocdin000040uv02.getHpValue());
				}
				// ADD END
			}

			// $Author :jin_peng
			// $Date : 2012/10/17 15:36$
			// [BUG]0010520 ADD BEGIN
			// 消息为更新状态时召回状态设为已更新状态
			if (Constants.VERSION_NUMBER_UPDATE.equals(pocdin000040uv02
					.getVersionNo())
					|| Constants.VERSION_NUMBER_REPLACE.equals(pocdin000040uv02
							.getVersionNo())) {
				examResult.setWithdrawFlag(StringUtils.strToBigDecimal(
						Constants.REPORT_MODIFY_FLAG, "召回状态")); // 设置召回标识为已更新状态
			} else {
				examResult.setWithdrawFlag(Constants.NO_WITH_DRAW_FLAG); // 设置召回标识为为召回状态
			}

			// [BUG]0010520 ADD END

			// 报告日期
			examResult.setReportDate(DateUtils.stringToDate(pocdin000040uv02
					.getReportDoctors().get(0).getReportDate()));
			StringBuffer reportDoctorId = new StringBuffer();
			StringBuffer reportDoctorName = new StringBuffer();
			// 获取检查报告人信息
			getReportDoctorInfo(reportDoctorId, reportDoctorName,
					pocdin000040uv02);
			// 报告医师ID
			examResult.setReportDoctor(reportDoctorId.toString());
			// 报告医师姓名
			examResult.setReportDoctorName(reportDoctorName.toString());

			// 审核人信息集合
			List<ReviewDoctor> reviewDoctors = pocdin000040uv02
					.getReviewDoctors();
			for (int i = 0; i < 3; i++) {
				// 获取集合中第一个审核人信息
				ReviewDoctor reviewDoctor = getLatestOrFirstReviewDoctor(
						reviewDoctors, "first");
				switch (i) {
				case 0:
					// 审核日期
					examResult.setReviewDate(DateUtils
							.stringToDate(reviewDoctor.getReviewDate()));
					// 审核医师ID
					examResult.setReviewDoctor(reviewDoctor.getReviewDoctor());
					// 审核医师姓名
					examResult.setReviewDoctorName(reviewDoctor
							.getReviewDoctorName());
					break;
				case 1:
					// 二次审核日期
					examResult.setSecondReviewDate(DateUtils
							.stringToDate(reviewDoctor.getReviewDate()));
					// 二次审核医师ID
					examResult.setSecondReviewDoctor(reviewDoctor
							.getReviewDoctor());
					// 二次审核医师姓名
					examResult.setSecondReviewDoctorName(reviewDoctor
							.getReviewDoctorName());
					break;
				case 2:
					// 三次审核日期
					examResult.setThirdReviewDate(DateUtils
							.stringToDate(reviewDoctor.getReviewDate()));
					// 三次审核医师ID
					examResult.setThirdReviewDoctor(reviewDoctor
							.getReviewDoctor());
					// 三次审核医师姓名
					examResult.setThirdReviewDoctorName(reviewDoctor
							.getReviewDoctorName());
					break;
				}
				if (reviewDoctors.size() > 0) {
					reviewDoctors.remove(reviewDoctor);
				}

			}

			// 更新时间
			examResult.setUpdateTime(systemTime);

			// Author :tong_meng
			// Date : 2013/10/21 14:30
			// [BUG]0038263 MODIFY BEGIN
			if (!isCVIS(pocdin000040uv02)) {
				// 检查日期
				examResult.setExaminationDate(DateUtils
						.stringToDate(pocdin000040uv02.getReports().get(0)
								.getExamItems().get(0).getExaminingDoctors()
								.get(0).getExaminationDate()));
				if (!StringUtils.isEmpty(pocdin000040uv02.getReports().get(0)
						.getExamItems().get(0).getExaminingDoctors().get(0)
						.getExaminationDept())) {
					// 检查科室
					examResult.setExaminationDept(pocdin000040uv02.getReports()
							.get(0).getExamItems().get(0).getExaminingDoctors()
							.get(0).getExaminationDept());
					// $Author:tong_meng
					// $Date:2013/8/6 16:29
					// $[BUG]0035834 MODIFY BEGIN
					examResult.setExamDeptName(pocdin000040uv02.getReports()
							.get(0).getExamItems().get(0).getExaminingDoctors()
							.get(0).getExaminationDeptName());
					// $[BUG]0035834 MODIFY END
				} else {
					examResult.setExaminationDept(examinationDept);
					examResult.setExamDeptName(examinationDeptName);
				}
				// $Author:wei_peng
				// $Date:2012/9/25 16:29
				// $[BUG]0010017 MODIFY BEGIN
				// 影像学表现
				examResult.setImagingFinding(examReport.getExamItems().get(0)
						.getImagingFinding());
				// 影像学结论
				examResult.setImagingConclusion(examReport.getExamItems()
						.get(0).getImagingConclusion());
				// $[BUG]0010017 MODIFY END

				// 检查报告备注
				examResult.setReportMemo(examReport.getExamItems().get(0)
						.getReportMemo());
			} else {
				// 影像学表现
				examResult.setImagingFinding(pocdin000040uv02
						.getObjectiveText());
				// 影像学结论
				examResult.setImagingConclusion(pocdin000040uv02
						.getSubjectiveText());

				if (examReport.getExamItems() != null
						&& !examReport.getExamItems().isEmpty()) {
					if (!StringUtils.isEmpty(examReport.getExamItems().get(0)
							.getImagingFinding())) {
						// 影像学表现
						examResult.setImagingFinding(examReport.getExamItems()
								.get(0).getImagingFinding());
						// 影像学结论
						examResult.setImagingConclusion(examReport
								.getExamItems().get(0).getImagingConclusion());
					}
					if (examReport.getExamItems().get(0).getExaminingDoctors() != null
							&& !examReport.getExamItems().get(0)
									.getExaminingDoctors().isEmpty()) {
						// 检查日期
						examResult.setExaminationDate(DateUtils
								.stringToDate(examReport.getExamItems().get(0)
										.getExaminingDoctors().get(0)
										.getExaminationDate()));

						// 检查报告备注
						examResult.setReportMemo(examReport.getExamItems()
								.get(0).getReportMemo());

						if (!StringUtils.isEmpty(examReport.getExamItems()
								.get(0).getExaminingDoctors().get(0)
								.getExaminationDept())) {
							// 检查科室
							examResult.setExaminationDept(pocdin000040uv02
									.getReports().get(0).getExamItems().get(0)
									.getExaminingDoctors().get(0)
									.getExaminationDept());
							// $Author:tong_meng
							// $Date:2013/8/6 16:29
							// $[BUG]0035834 MODIFY BEGIN
							examResult.setExamDeptName(pocdin000040uv02
									.getReports().get(0).getExamItems().get(0)
									.getExaminingDoctors().get(0)
									.getExaminationDeptName());
							// $[BUG]0035834 MODIFY END
						} else {
							examResult.setExaminationDept(examinationDept);
							examResult.setExamDeptName(examinationDeptName);
						}
					}
				} else {
					examResult.setExaminationDept(examinationDept);
					examResult.setExamDeptName(examinationDeptName);
				}
			}
			// [BUG]0038263 MODIFY END

			// $Author:weipeng
			// $Date:2012/7/4 13:12
			// [BUG]0007681 ADD BEGIN
			// 检查类型代码
			examResult.setItemClass(examReport.getItemClass());
			// 检查类型名称
			examResult.setItemClassName(examReport.getItemClassName());
			// [BUG]0007681 END BEGIN

			// $Author:jia_yanqing
			// $Date:2012/8/29 14:30
			// [BUG]0009249 ADD BEGIN
			// examResult.setImageIndexNo(examReport.getImageIndex());
			// [BUG]0009249 ADD END

			// 临床资料
			// examResult.setClinicalInformation(pocdin000040uv02
			// .getClinicalData());
			// 试剂代码
			examResult.setReagentCode(examReport.getReagentCode());

			// $Author :tong_meng
			// $Date : 2012/7/2 10:33$
			// [BUG]0007418 ADD BEGIN
			// 试剂名称
			examResult.setReagentName(examReport.getReagentName());
			// [BUG]0007418 ADD END

			// $Author :tong_meng
			// $Date : 2013/8/7 15:00$
			// [BUG]0033814 ADD BEGIN
			// 病区
			examResult.setWardId(pocdin000040uv02.getWardId());
			examResult.setWardName(pocdin000040uv02.getWardName());
			// 床号
			examResult.setBedNo(pocdin000040uv02.getBedNo());
			// [BUG]0033814 ADD END

			// 试剂用量
			examResult.setReagentDosage(examReport.getReagentDosage());
			// 试剂用量单位
			examResult.setReagentDosageUnit(examReport.getReagentDosageUnit());

			examResultList.add(examResult);
		}
		return examResultList;
	}

	private List<List<Object>> getExamItems(POCDIN000040UV02 pocdin000040uv02,
			String versionNumber) {
		List<List<Object>> examItemList = new ArrayList<List<Object>>();
		// 新增检查项目对象
		List<Object> examItemAddList = new ArrayList<Object>();
		BigDecimal seq = null;
		ExaminationItem examItem = null;

		// 读取消息中的检查项目信息存入entity中
		if (pocdin000040uv02.getReports().get(0).getExamItems() != null) {
			for (ExaminationItems examItemResult : pocdin000040uv02
					.getReports().get(0).getExamItems()) {
				examItem = new ExaminationItem();

				seq = commonService.getSequence(EXAM_ITEM_SEQUENCE);
				// 检查项目内部序列号
				examItem.setItemSn(seq);
				// 检查报告内部序列号
				examItem.setExamReportSn(examReportSn);
				// $Author:wei_peng
				// $Date:2012/11/29
				// [BUG]0011954 ADD BEGIN
				StringBuffer examiningDoctorId = new StringBuffer();
				StringBuffer examiningDoctorName = new StringBuffer();
				for (int i = 0; i < examItemResult.getExaminingDoctors().size(); i++) {
					// $Author:tong_meng
					// $Date:2013/8/22
					// [BUG]0036486 MODIFY BEGIN
					ExaminingDoctor examiningDoctors = examItemResult
							.getExaminingDoctors().get(i);
					if (i == examItemResult.getExaminingDoctors().size() - 1) {
						examiningDoctorId
								.append(StringUtils.isEmpty(examiningDoctors
										.getExaminingDoctor()) ? ""
										: examiningDoctors.getExaminingDoctor());
						examiningDoctorName.append(StringUtils
								.isEmpty(examiningDoctors
										.getExaminingDoctorName()) ? ""
								: examiningDoctors.getExaminingDoctorName());
					} else {
						examiningDoctorId.append(StringUtils
								.isEmpty(examiningDoctors.getExaminingDoctor()
										+ ",") ? "" : examiningDoctors
								.getExaminingDoctor());
						examiningDoctorName.append(StringUtils
								.isEmpty(examiningDoctors
										.getExaminingDoctorName() + ",") ? ""
								: examiningDoctors.getExaminingDoctorName());
					}
					// [BUG]0036486 MODIFY END
				}
				// [BUG]0011954 ADD END
				// 检查医师ID
				examItem.setExaminingDoctor(examiningDoctorId.toString());
				// 检查医生姓名
				examItem.setExaminingDoctorName(examiningDoctorName.toString());

				// $Author:wei_peng
				// $Date:2012/12/27 16:57
				// [BUG]0012385 MODIFY BEGIN
				if (!StringUtils.isEmpty(examItemResult.getExaminationMethod())) {
					// 检查方法
					examItem.setExaminationMethod(examItemResult
							.getExaminationMethod());
					// 检查方法名称
					examItem.setExaminationMethodName(examItemResult
							.getExaminationMethodName());
				} else {
					// 检查方法
					examItem.setExaminationMethod(examinationMethod);
					// 检查方法名称
					examItem.setExaminationMethodName(examinationMethodName);
				}
				// [BUG]0012385 MOFIFY END

				// $Author:wei_peng
				// $Date:2012/9/25 17:11
				// $[BUG]0010017 MODIFY BEGIN
				examItem.setImageIndexNo(pocdin000040uv02.getReports().get(0)
						.getImageIndex());
				// 影像学表现
				// examItem.setImagingFinding(examItemResult.getImagingFinding());
				// 影像学结论
				// examItem.setImagingConclusion(examItemResult.getImagingConclusion());
				// $[BUG]0010017 MODIFY END
				// 创建时间
				examItem.setCreateTime(systemTime);
				// 更新时间
				examItem.setUpdateTime(systemTime);
				// 更新回数
				examItem.setUpdateCount(Constants.INSERT_UPDATE_COUNT);
				// 删除标志
				examItem.setDeleteFlag(Constants.NO_DELETE_FLAG);

				// $Author: tong_meng
				// $Date: 2013/8/30 15:00
				// [BUG]0036757 ADD BEGIN
				examItem.setCreateby(serviceId); // 设置创建人
				// [BUG]0036757 ADD END

				examItemAddList.add(examItem);
			}
			examItemList.add(examItemAddList);
		}

		// 通过versionNo判断进行新增前的删除(读取原有数据)
		if (Constants.VERSION_NUMBER_UPDATE.equals(pocdin000040uv02
				.getVersionNo())
				|| Constants.VERSION_NUMBER_REPLACE.equals(pocdin000040uv02
						.getVersionNo())) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("examReportSn", examReportSn);
			map.put("deleteFlag", Constants.NO_DELETE_FLAG);
			List<Object> examList = examService.selectExamItemByCondition(map);
			examItemList.add(examList);
		}
		return examItemList;
	}

	/**
	 * 获取检查报告关联的检验影像信息
	 * 
	 * @param pocdin000040uv02
	 *            检查报告CDA对象
	 * @param versionNumber
	 *            版本号
	 * @return 检查报告关联的检验影像信息对象集合
	 */
	private List<List<Object>> getLabImage(POCDIN000040UV02 pocdin000040uv02,
			String versionNumber) {
		List<List<Object>> resList = new ArrayList<List<Object>>();
		BigDecimal seq = null;

		// 新增影像对象
		List<Object> labImageAddList = new ArrayList<Object>();

		// CDA中影像信息(非结构化)
		List<LabImageContent> unstructLabImageContentList = pocdin000040uv02
				.getReports().get(0).getImageText();

		// CDA中检查项目
		List<ExaminationItems> examItemsList = pocdin000040uv02.getReports()
				.get(0).getExamItems();

		// 读取消息中的非结构化影像信息存入entity检验影像中
		if (unstructLabImageContentList != null
				&& !unstructLabImageContentList.isEmpty()) {
			for (LabImageContent labImageContent : unstructLabImageContentList) {
				MedicalImaging medicalImage = new MedicalImaging();
				seq = commonService.getSequence(MEDICAL_IMAGING_SEQUENCE);
				medicalImage.setImagingSn(seq);
				// 检查报告类型
				medicalImage.setRefType(Constants.REPORT_TYPE_EXAM);
				// 报告内部序列号
				medicalImage.setReportSn(examReportSn);
				// 影像内容
				// $Author :jia_yanqing
				// $Date : 2012/6/13 15:30$
				// [BUG]0006921 MODIFY BEGIN
				// medicalImage.setImageContent(labImageContent.getImageText());
				medicalImage
						.setImageContent(getImageContentAfterDecode(labImageContent));
				// [BUG]0006921 MODIFY END

				// 图片扩展名为image/jpg的形式时进行判断取值
				String imageExtension = labImageContent.getImageExtension();
				if (!StringUtils.isEmpty(imageExtension)) {
					if (labImageContent.getImageExtension().indexOf("/") > 0) {
						imageExtension = labImageContent.getImageExtension()
								.split("/")[1];
					}
				}

				medicalImage.setImageFormat(imageExtension);
				medicalImage.setCreateTime(systemTime);
				medicalImage.setUpdateTime(systemTime);
				medicalImage.setUpdateCount(Constants.INSERT_UPDATE_COUNT);
				medicalImage.setDeleteFlag(Constants.NO_DELETE_FLAG);
				// $Author: tong_meng
				// $Date: 2013/8/30 15:00
				// [BUG]0036757 ADD BEGIN
				medicalImage.setCreateby(serviceId); // 设置创建人
				// [BUG]0036757 ADD END

				labImageAddList.add(medicalImage);
			}
		}

		// 遍历消息中检查项目，获取检查项目中的影像信息
		for (int i = 0; i < examItemsList.size(); i++) {
			// 获取消息中结构化的影像信息(与检查项目对应)
			List<LabImageContent> structLabImageContentList = examItemsList
					.get(i).getImageText();
			if (structLabImageContentList != null
					&& !structLabImageContentList.isEmpty()) {
				// 遍历结构化的影像内容
				for (LabImageContent labImageDto : structLabImageContentList) {
					MedicalImaging medicalImage = new MedicalImaging();
					seq = commonService.getSequence(MEDICAL_IMAGING_SEQUENCE);
					medicalImage.setImagingSn(seq);
					// 检查报告类型
					medicalImage.setRefType(Constants.REPORT_TYPE_EXAM);
					// 报告内部序列号
					medicalImage.setReportSn(examReportSn);
					// 影像内容
					// $Author :jia_yanqing
					// $Date : 2012/6/13 15:30$
					// [BUG]0006921 MODIFY BEGIN
					medicalImage
							.setImageContent(getImageContentAfterDecode(labImageDto));
					// [BUG]0006921 MODIFY END

					// 图片扩展名为image/jpg的形式时进行判断取值
					String imageExtension = labImageDto.getImageExtension();
					if (!StringUtils.isEmpty(imageExtension)) {
						if (labImageDto.getImageExtension().indexOf("/") > 0) {
							imageExtension = labImageDto.getImageExtension()
									.split("/")[1];
						}
					}

					medicalImage.setImageFormat(imageExtension);
					medicalImage.setCreateTime(systemTime);
					medicalImage.setUpdateTime(systemTime);
					medicalImage.setUpdateCount(Constants.INSERT_UPDATE_COUNT);
					medicalImage.setDeleteFlag(Constants.NO_DELETE_FLAG);
					// $Author: tong_meng
					// $Date: 2013/8/30 15:00
					// [BUG]0036757 ADD BEGIN
					medicalImage.setCreateby(serviceId); // 设置创建人
					// [BUG]0036757 ADD END

					labImageAddList.add(medicalImage);
				}
			}
		}

		resList.add(labImageAddList);

		// 通过versionNo判断进行新增前的删除
		if (Constants.VERSION_NUMBER_UPDATE.equals(pocdin000040uv02
				.getVersionNo())
				|| Constants.VERSION_NUMBER_REPLACE.equals(pocdin000040uv02
						.getVersionNo())) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("reportSn", examReportSn);
			map.put("refType", Constants.REPORT_TYPE_EXAM);
			map.put("deleteFlag", Constants.NO_DELETE_FLAG);
			List<Object> labImageList = labService
					.selectLabImageByCondition(map);
			resList.add(labImageList);
		}
		return resList;
	}

	// $Author :jia_yanqing
	// $Date : 2012/9/7 15:30$
	// [BUG]0009507 MODIFY BEGIN
	// $Author :jia_yanqing
	// $Date : 2012/6/13 15:30$
	// [BUG]0006921 MODIFY BEGIN
	/**
	 * 将消息中传来的BASE64格式的影像内容解码为字节数组类型
	 * 
	 * @param labImageDto
	 * @return byte[]
	 */
	public byte[] getImageContentAfterDecode(LabImageContent labImageDto) {
		byte[] bt = null;
		if (labImageDto.getImageText() != null
				&& !"".equals(labImageDto.getImageText())) {
			bt = Base64.decodeBase64(labImageDto.getImageText());
		}
		return bt;
	}

	// [BUG]0006921 MODIFY END
	// [BUG]0009507 MODIFY END

	/**
	 * 获取拼接后的检查报告人信息
	 * 
	 * @param reportDoctorId
	 *            报告人Id
	 * @param reportDoctorName
	 *            报告人姓名
	 * @param pocdin000040uv02
	 */
	public void getReportDoctorInfo(StringBuffer reportDoctorId,
			StringBuffer reportDoctorName, POCDIN000040UV02 pocdin000040uv02) {
		for (int i = 0; i < pocdin000040uv02.getReportDoctors().size(); i++) {
			if (i == pocdin000040uv02.getReportDoctors().size() - 1) {
				reportDoctorId.append(pocdin000040uv02.getReportDoctors()
						.get(i).getReportDoctor());
				reportDoctorName.append(pocdin000040uv02.getReportDoctors()
						.get(i).getReportDoctorName());
			} else {
				reportDoctorId.append(pocdin000040uv02.getReportDoctors()
						.get(i).getReportDoctor()
						+ ",");
				reportDoctorName.append(pocdin000040uv02.getReportDoctors()
						.get(i).getReportDoctorName()
						+ ",");
			}
		}
	}

	/**
	 * 获取最近审核的审核人信息
	 * 
	 * @param reviewPersons
	 * @return
	 */
	public ReviewDoctor getLatestOrFirstReviewDoctor(
			List<ReviewDoctor> reviewDoctors, String orderFlag) {
		ReviewDoctor reviewDoctor = new ReviewDoctor();
		for (int i = 0; i < reviewDoctors.size(); i++) {
			if (i == 0) {
				reviewDoctor = reviewDoctors.get(i);
			} else {
				Date date1 = DateUtils.stringToDate(reviewDoctor
						.getReviewDate());
				Date date2 = DateUtils.stringToDate(reviewDoctors.get(i)
						.getReviewDate());
				if ("latest".equals(orderFlag) && date1 != null
						&& date2 != null && date2.after(date1)) {
					reviewDoctor = reviewDoctors.get(i);
				}
				if ("first".equals(orderFlag) && date1 != null && date2 != null
						&& date2.before(date1)) {
					reviewDoctor = reviewDoctors.get(i);
				}
			}
		}
		return reviewDoctor;
	}

	/**
	 * 判断是否为病理学检查报告
	 * 
	 * @param pocdin000040uv02
	 * @return
	 */
	// 病理OT
	private boolean isPathlogyExamReport(POCDIN000040UV02 pocdin000040uv02) {
		return Constants.EXAM_ITEMCLASS_PATHOLOGY.equals(pocdin000040uv02
				.getReports().get(0).getItemClass());
	}

	// 超声心动US
	private boolean isEC(POCDIN000040UV02 pocdin000040uv02) {
		return Constants.EXAM_ITEMCLASS_ECHOCARDIOGRAPHY
				.equals(pocdin000040uv02.getExamType());
	}

	// 心电图ECG
	private boolean isECG(POCDIN000040UV02 pocdin000040uv02) {
		return Constants.EXAM_ITEMCLASS_ELECTROCARDIOGRAPHY
				.equals(pocdin000040uv02.getExamType());
	}

	// 胃镜ES
	private boolean isES(POCDIN000040UV02 pocdin000040uv02) {
		return Constants.EXAM_ITEMCLASS_GASTROSCOPE.equals(pocdin000040uv02
				.getExamType());
	}

	private boolean isDSA(POCDIN000040UV02 pocdin000040uv02) {
		return Constants.EXAM_ITEMCLASS_CORONARY_ANGIOGRAPHY
				.equals(pocdin000040uv02.getExamType())
				|| Constants.EXAM_ITEMCLASS_CORONARY_ANGIOGRAPHY_OPRERATER
						.equals(pocdin000040uv02.getExamType());
	}

	private boolean isCVIS(POCDIN000040UV02 pocdin000040uv02) {
		return Constants.SOURCE_CVIS_EXAM.equals(pocdin000040uv02
				.getExamReportRoot());
	}

	private Logger getDocumentLogger(String serviceId) {
		Logger result = null;
		Field field = null;
		try {
			field = POCDIN000040UV02Writer.class.getDeclaredField("logger"
					+ serviceId.toUpperCase());
			field.setAccessible(true);
			result = (Logger) field.get(this);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

	private boolean checkExistExamResult(POCDIN000040UV02 pocdin000040uv02,
			boolean flag) {
		// TODO Auto-generated method stub
		// 用图像索引号获取检查项目，获得报告内部序列号
		this.examItemExist = examService
				.getExamItemPresenceByIndex(pocdin000040uv02.getReports()
						.get(0).getImageIndex());
		if (examItemExist == null) {
			logger.error("检查项目表不存在此检查项目！", "params:"
					+ pocdin000040uv02.getReports().get(0).getImageIndex());
			if (flag) {
				loggerBSDOCUMENT.error("Message:{}, checkDependency:{}",
						pocdin000040uv02.toString(), "检查项目表不存在此检查项目！"
								+ "params:"
								+ pocdin000040uv02.getReports().get(0)
										.getImageIndex());
			}
			return false;
		} else {
			// 用查到的报告索引号得到报告
			this.examResultExist = examService
					.getExamResultPresenceBySn(examItemExist.getExamReportSn());

			// [BUG]0010434 MODIFY END

			if (examResultExist == null) {
				logger.error("检查报告表不存在此报告！");
				if (flag) {
					loggerBSDOCUMENT.error("Message:{}, checkDependency:{}",
							pocdin000040uv02.toString(), "检查报告表不存在此报告！");
				}
				return false;
			} else {
				// 检查报告做更新操作时数据库中相应报告中召回状态为已召回时才可执行更新操作 控制开关(0:开启 1:关闭)
				if (Constants.COMM_INTERFACE
						.equals(Constants.WITH_DRAW_USE_FLAG)) {
					// $Author :jin_peng
					// $Date : 2012/10/17 15:36$
					// [BUG]0010520 ADD BEGIN
					// 检查报告做更新操作时数据库中相应报告中召回状态为已召回时才可执行替换操作
					if (!Constants.WITH_DRAW_FLAG.equals(examResultExist
							.getWithdrawFlag())) {
						logger.error(
								"替换检查报告时相关数据的召回状态并非为已召回状态，暂不做替换操作: {}",
								"params: examReportLid = "
										+ examResultExist.getExamReportLid()
										+ "; itemClass = "
										+ examResultExist.getItemClass());
						if (flag) {
							loggerBSDOCUMENT.error(
									"Message:{}, checkDependency:{}",
									pocdin000040uv02.toString(),
									"替换检查报告时相关数据的召回状态并非为已召回状态，暂不做替换操作: "
											+ "params: examReportLid = "
											+ examResultExist
													.getExamReportLid()
											+ "; itemClass = "
											+ examResultExist.getItemClass());
						}
						return false;
					}
				}
			}
			// [BUG]0010520 ADD END
		}

		// documentSnToDelete = examResultExist.getDocumentSn();
		return true;

	}

	// $Author :jia_yanqing
	// $Date : 2012/6/12 14:30$
	// [BUG]0006669 DELETE BEGIN
	/**
	 * 检查报告召回操作
	 * 
	 * @param pocdin000040uv01
	 *            检查报告CDA对象
	 */
	/*
	 * private void withDrawLabResult(POCDIN000040UV02 pocdin000040uv02) { //
	 * 获取检查报告和对应的检查召回记录对象集合 List<Object> ResultAndWithDrawList =
	 * getLabResultAndWithDraw(pocdin000040uv02);
	 * 
	 * // 更新检查报告表中的召回状态、添加召回记录 WithdrawRecord wthdrawRecord = (WithdrawRecord)
	 * ResultAndWithDrawList.get(1);
	 * commonService.withdrawLabResult(ResultAndWithDrawList.get(0),
	 * wthdrawRecord); }
	 */
	// [BUG]0006669 DELETE END

	/**
	 * 获取报告召回信息
	 * 
	 * @param pocdin000040uv01
	 *            检查报告CDA对象
	 * @return 报告召回信息
	 */
	// $Author :jia_yanqing
	// $Date : 2012/6/12 14:30$
	// [BUG]0006669 DELETE BEGIN

	/*
	 * private List<Object> getLabResultAndWithDraw( POCDIN000040UV02
	 * pocdin000040uv02) { List<Object> resList = new ArrayList<Object>();
	 * 
	 * // 更需要召回的检查报告存在时更改召回状态 withdrawExamResultExist.setWithdrawFlag(new
	 * BigDecimal(Constants.WITH_DRAW_FLAG));
	 * withdrawExamResultExist.setUpdateTime(systemTime);
	 * 
	 * // 新增作废信息 WithdrawRecord withdrawRecord = new WithdrawRecord();
	 * withdrawRecord
	 * .setRefType(StringUtils.strToBigDecimal(Constants.REPORT_TYPE_EXAM));
	 * withdrawRecord.setReportSn(withdrawExamResultExist.getExamReportSn());
	 * withdrawRecord.setWithdrawPerson(pocdin000040uv02.getWithDrawPerson());
	 * withdrawRecord
	 * .setWithdrawPersonName(pocdin000040uv02.getWithDrawPersonName());
	 * withdrawRecord
	 * .setWithdrawTime(DateUtils.stringToDate(pocdin000040uv02.getWithDrawDate
	 * ()));
	 * withdrawRecord.setWithdrawReason(pocdin000040uv02.getWithdrawReason());
	 * withdrawRecord.setCreateTime(systemTime);
	 * withdrawRecord.setUpdateTime(systemTime);
	 * withdrawRecord.setUpdateCount(new BigDecimal(
	 * Constants.INSERT_UPDATE_COUNT));
	 * withdrawRecord.setDeleteFlag(Constants.NO_DELETE_FLAG);
	 * 
	 * // 添加要更新的检查报告和召回记录对象 resList.add(withdrawExamResultExist);
	 * resList.add(withdrawRecord);
	 * 
	 * return resList; }
	 */
	// [BUG]0006669 DELETE END

}
