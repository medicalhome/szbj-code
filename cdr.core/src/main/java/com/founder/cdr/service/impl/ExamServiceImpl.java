package com.founder.cdr.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.founder.cdr.core.Constants;
import com.founder.cdr.dao.DiagnosisDao;
import com.founder.cdr.dao.DocumentDao;
import com.founder.cdr.dao.ExamDao;
import com.founder.cdr.dto.ExamCVISDto;
import com.founder.cdr.dto.ExamListDto;
import com.founder.cdr.dto.OrderStepDto;
import com.founder.cdr.dto.exam.ExamListSearchParameters;
import com.founder.cdr.dto.exam.WithdrawRecordDto;
import com.founder.cdr.entity.ClinicalDocument;
import com.founder.cdr.entity.ExamAppointment;
import com.founder.cdr.entity.ExamOrderExamResult;
import com.founder.cdr.entity.ExaminationItem;
import com.founder.cdr.entity.ExaminationOrder;
import com.founder.cdr.entity.ExaminationResult;
import com.founder.cdr.entity.ExaminationResultDetail;
import com.founder.cdr.entity.ExaminationResultProcedure;
import com.founder.cdr.entity.MedicalImaging;
import com.founder.cdr.entity.MedicalVisit;
import com.founder.cdr.entity.Message;
import com.founder.cdr.service.CommonService;
import com.founder.cdr.service.ExamService;
import com.founder.cdr.util.DateUtils;
import com.founder.fasf.core.util.daohelper.entity.EntityDao;
import com.founder.fasf.web.paging.PagingContext;

@Component
public class ExamServiceImpl implements ExamService {
	@Autowired
	private ExamDao examDao;

	@Autowired
	private DiagnosisDao diagnosisDao;

	@Autowired
	private EntityDao entityDao;

	@Autowired
	private CommonService commonService;

	@Autowired
	private DocumentDao documentDao;

	/**
	 * 高级查询
	 */
	@Transactional
	public ExamListDto[] selectExamListSearch(String patientSn,
			ExamListSearchParameters examListSearchParameters,
			PagingContext pagingContext) {
		ExamListDto[] examListResult = examDao.selectExamListSearch(patientSn,
				examListSearchParameters, pagingContext);
		return examListResult;
	}

	/**
	 * 检查
	 */
	@Transactional
	public ExamListDto[] selectExamDetail(String reportSn, String itemSn) {
		return examDao.selectExamDetail(reportSn, itemSn);
	}

	// Author:jin_peng
	// Date:2013/01/29 15:57
	// [BUG]0013729 MODIFY BEGIN
	/**
	 * 检查-关联查询
	 */
	@Transactional
	public ExamListDto[] selectRelatedExam(String patientSn,
			String examinationRegion, String examinationItem, String orderSn) {
		return examDao.selectRelatedExam(patientSn, examinationRegion,
				examinationItem, orderSn);
	}

	// [BUG]0013729 MODIFY END

	/**
	 * 检查医嘱查询
	 */
	@Transactional
	public ExaminationOrder selectExamOrderDetail(String orderSn) {
		Map<String, Object> condition = new HashMap<String, Object>();
		condition.put("orderSn", orderSn);
		condition.put("deleteFlag", Constants.NO_DELETE_FLAG);
		List<Object> examOrderList = commonService.selectByCondition(
				ExaminationOrder.class, condition);
		if (examOrderList != null && examOrderList.size() > 0) {
			return (ExaminationOrder) examOrderList.get(0);
		}
		return null;
	}

	@Transactional
	public WithdrawRecordDto[] selectWithdrawRecord(String reportSn) {
		return examDao.selectWithdrawRecord(reportSn);
	}

	@Override
	@Transactional
	public List<Object> selectMedicalVisitById(Map<String, Object> map) {
		return entityDao.selectByCondition(MedicalVisit.class, map);
	}

	@Override
	@Transactional
	public List<Object> selectExaminationOrderById(Map<String, Object> map) {
		return entityDao.selectByCondition(ExaminationOrder.class, map);
	}

	@Override
	@Transactional
	public MedicalVisit mediVisit(String patientDomain, String patientLid,
			Integer visitTimes) {
		Map<String, Object> condition = new HashMap<String, Object>();
		condition.put("patientDomain", patientDomain);
		condition.put("patientLid", patientLid);
		condition.put("visitTimes", visitTimes);
		List visitResult = new ArrayList<MedicalVisit>();
		visitResult = entityDao
				.selectByCondition(MedicalVisit.class, condition);
		MedicalVisit MV = (MedicalVisit) visitResult.get(0);
		return MV;
	}

	// $Author :chang_xuewen
	// $Date : 2013/12/03 11:00$
	// [BUG]0040271 MODIFY BEGIN
	@Override
	@Transactional
	public ExaminationOrder getOrder(Object... args) {
		Map<String, Object> condition = new HashMap<String, Object>();
		condition.put("patientDomain", args[0]);
		condition.put("patientLid", args[1]);
		condition.put("orderLid", args[2]);
		condition.put("orgCode", args[3]);
		condition.put("visitSn", args[4]);
		condition.put("deleteFlag", Constants.NO_DELETE_FLAG);
		List<Object> result = entityDao.selectByCondition(
				ExaminationOrder.class, condition);
		ExaminationOrder resultExam = null;
		if (result != null && result.size() > 0) {
			resultExam = (ExaminationOrder) result.get(0);
		}
		return resultExam;
	}

	// [BUG]0040271 MODIFY END

	// Author :jia_yanqing
	// Date : 2013/11/13 20:48
	// [BUG]0038805 MODIFY BEGIN
	// $Author :jin_peng
	// $Date : 2012/9/16 14:48$
	// [BUG]0009718 ADD BEGIN
	// $Author :chang_xuewen
	// $Date : 2013/12/03 11:00$
	// [BUG]0040271 MODIFY BEGIN
	/**
	 * 通过申请单号查询出相应的检查申请对象
	 * 
	 * @param patientDomain
	 *            域ID
	 * @param patientLid
	 *            患者本地ID
	 * @param requestNo
	 *            申请单号
	 * @return 检查申请对象集合
	 */
	@Transactional
	public ExaminationOrder getRequest(Object... args) {
		Map<String, Object> condition = new HashMap<String, Object>();

		condition.put("patientDomain", args[0]);
		condition.put("patientLid", args[1]);
		condition.put("requestNo", args[2]);
		condition.put("orgCode", args[3]);
		condition.put("visitSn", args[4]);

		condition.put("deleteFlag", Constants.NO_DELETE_FLAG);

		// 通过查询条件获取相应的检查申请对象集合
		List<Object> result = entityDao.selectByCondition(
				ExaminationOrder.class, condition);
		ExaminationOrder resultExam = null;
		if (result != null && result.size() > 0) {
			resultExam = (ExaminationOrder) result.get(0);
		}
		return resultExam;
	}

	// [BUG]0040271 MODIFY END
	// [BUG]0009718 ADD END
	// [BUG]0038805 MODIFY END

	// Author :jia_yanqing
	// Date : 2013/11/13 20:48
	// [BUG]0038805 ADD BEGIN
	// $Author :chang_xuewen
	// $Date : 2013/12/03 11:00$
	// [BUG]0040271 MODIFY BEGIN
	/**
	 * 通过申请单号查询出相应的检查申请对象
	 * 
	 * @param patientDomain
	 *            域ID
	 * @param patientLid
	 *            患者本地ID
	 * @param requestNo
	 *            申请单号
	 * @param orgCode
	 *            医疗机构代码
	 * @return 检查申请对象集合
	 */
	@Transactional
	public List<Object> getRequestList(Object... args) {
		Map<String, Object> condition = new HashMap<String, Object>();

		condition.put("patientDomain", args[0]);
		condition.put("patientLid", args[1]);
		condition.put("requestNo", args[2]);
		condition.put("orgCode", args[3]);
		condition.put("visitSn", args[4]);
		condition.put("deleteFlag", Constants.NO_DELETE_FLAG);

		// 通过查询条件获取相应的检查申请对象集合
		List<Object> result = entityDao.selectByCondition(
				ExaminationOrder.class, condition);
		return result;
	}

	// [BUG]0040271 MODIFY END
	// [BUG]0038805 ADD END

	// $Author :jin_peng
	// $Date : 2012/10/22 14:59$
	// [BUG]0010434 MODIFY BEGIN
	/**
	 * 检验报告查询 examReportLid 报告本地ID examDept 检查科室
	 */
	@Transactional
	public ExaminationResult getReportPresence(String examReportLid,
			String itemClass, String orgCode) {
		Map<String, Object> condition = new HashMap<String, Object>();
		condition.put("examReportLid", examReportLid);
		condition.put("itemClass", itemClass);
		condition.put("orgCode", orgCode);
		condition.put("deleteFlag", Constants.NO_DELETE_FLAG);

		List<Object> reportResult = entityDao.selectByCondition(
				ExaminationResult.class, condition);

		if (reportResult != null && !reportResult.isEmpty()) {
			return (ExaminationResult) reportResult.get(0);
		} else {
			return null;
		}
	}

	// [BUG]0010434 MODIFY END

	// $Author :jin_peng
	// $Date : 2012/11/23 13:59$
	// [BUG]0011864 MODIFY BEGIN
	// $Author :jin_peng
	// $Date : 2012/09/20 14:47$
	// [BUG]0009691 MODIFY BEGIN
	@Transactional
	public void updateExamOrderReportSn(BigDecimal reportSn,
			String patientDomain, String patientLid, List<String> orderLidList,
			String orgCode, Date systemTime, List<List<Object>> examItemList,
			String serviceId, BigDecimal visitSn) {
		if (orderLidList != null && orderLidList.size() > 0) {
			// $Author:wei_peng
			// $Date:2013/05/07 10:26
			// [BUG]0031568 MODIFY BEGIN
			ExaminationOrder[] examOrders = examDao.getExamOrderByOrderLids(
					patientDomain, patientLid, orderLidList, orgCode,
					Constants.PATIENT_DOMAIN_OUTPATIENT, visitSn);
			List<ExamOrderExamResult> examOrderExamResultList = new ArrayList<ExamOrderExamResult>();
			for (ExaminationOrder examOrder : examOrders) {
				ExamOrderExamResult examOrderExamResult = new ExamOrderExamResult();
				examOrderExamResult.setExamOrderSn(examOrder.getOrderSn());
				examOrderExamResult.setExamResultSn(reportSn);
				examOrderExamResult.setCreateTime(systemTime);
				examOrderExamResult.setUpdateTime(systemTime);
				examOrderExamResult
						.setUpdateCount(Constants.INSERT_UPDATE_COUNT);
				examOrderExamResult.setDeleteFlag(Constants.NO_DELETE_FLAG);

				// $Author: tong_meng
				// $Date: 2013/8/30 15:00
				// [BUG]0036757 ADD BEGIN
				examOrderExamResult.setCreateby(serviceId); // 设置创建人
				// [BUG]0036757 ADD END

				examOrderExamResultList.add(examOrderExamResult);
			}
			commonService.saveList(examOrderExamResultList);
			// [BUG]0031568 MODIFY END

			// $Author :jin_peng
			// $Date : 2012/10/19 17:47$
			// [BUG]0010594 ADD BEGIN
			examDao.updateProcOrderReportSn(reportSn, patientDomain,
					patientLid, orderLidList,
					Constants.PATIENT_DOMAIN_OUTPATIENT);
			// [BUG]0010594 ADD END
		}

		// 新增检查项目
		if (examItemList != null && !examItemList.isEmpty()) {
			for (Object examItemObject : examItemList.get(0)) {
				ExaminationItem examItem = (ExaminationItem) examItemObject;
				commonService.save(examItem);
			}
		}
	}

	// [BUG]0009691 MODIFY END
	// [BUG]0011864 MODIFY END

	/**
	 * 检查报告中检查项目查询
	 * 
	 * @param map
	 * @return
	 */
	@Transactional
	public List<Object> selectExamItemByCondition(Map<String, Object> map) {
		return entityDao.selectByCondition(ExaminationItem.class, map);
	}

	// $Author :jin_peng
	// $Date : 2013/07/10 14:38$
	// [BUG]0034638 MODIFY BEGIN
	// $Author :jin_peng
	// $Date : 2012/11/23 13:59$
	// [BUG]0011864 MODIFY BEGIN
	// $Author :jin_peng
	// $Date : 2012/09/20 14:47$
	// [BUG]0009691 MODIFY BEGIN
	/**
	 * 检查报告消息更新/插入操作
	 * 
	 * @param message
	 *            消息
	 * @param examResultList
	 *            检查报告内容
	 * @param examItemList
	 *            检查项目内容
	 * @param medicalImagesList医学影像
	 * @param versionNumber
	 *            版本号
	 * @param reportSn
	 *            检查报告内部序列号
	 * @param patientDomain
	 *            域代码
	 * @param patientLid
	 *            患者本地ID
	 * @param orderLidList
	 *            本地医嘱号List
	 * @param clinicalDocument
	 *            病历文档
	 * @param documentSn
	 *            原病历文档内部序列号
	 * @param systemTime
	 *            消息处理时间
	 */
	@Transactional
	public void updateInsertExamResult(Message message,
			List<Object> examResultList, List<List<Object>> examItemList,
			List<List<Object>> medicalImagesList, String versionNumber,
			BigDecimal reportSn, String patientDomain, String patientLid,
			ExaminationResultProcedure resultReplication,
			List<Object> examResults, List<String> orderLidList,
			ClinicalDocument clinicalDocument, BigDecimal documentSn,
			String orgCode, Date systemTime, String serviceId,
			BigDecimal visitSn) {
		if (Constants.VERSION_NUMBER_UPDATE.equals(versionNumber)
				|| Constants.VERSION_NUMBER_REPLACE.equals(versionNumber)) {
			// 检查报告更新
			if (examResultList != null && !examResultList.isEmpty()) {
				for (Object examResultObject : examResultList) {
					ExaminationResult examResult = (ExaminationResult) examResultObject;
					commonService.update(examResult);
				}
			}

			// 更新病例文档内容
			if (clinicalDocument != null) {
				commonService.update(clinicalDocument);
				documentDao.updateClinicalDocumentDocumentCda(message,
						clinicalDocument);
			}

			// 获取检查项目表中原有数据
			List<Object> examItemDeleteList = null;
			// 获取检查项目表中原中数据
			List<Object> examItemAddList = null;
			if (examItemList != null && !examItemList.isEmpty()) {
				examItemAddList = examItemList.get(0);
				examItemDeleteList = examItemList.get(1);
			}
			// 删除检查项目
			if (examItemDeleteList != null && !examItemDeleteList.isEmpty()) {
				for (Object examItemDeleteObject : examItemDeleteList) {
					commonService.delete(examItemDeleteObject);
				}
			}
			// 新增检查项目
			if (examItemAddList != null && !examItemAddList.isEmpty()) {
				for (Object examItemAddObject : examItemAddList) {
					ExaminationItem examItemAdd = (ExaminationItem) examItemAddObject;
					commonService.save(examItemAdd);
				}
			}

			// 取得医学影像中数据库原有数据
			List<Object> labImageDeleteList = null;
			// 检查报告消息中影像映射数据取得
			List<Object> labImageList = null;

			if (medicalImagesList != null && !medicalImagesList.isEmpty()) {
				labImageList = medicalImagesList.get(0);
				labImageDeleteList = medicalImagesList.get(1);
			}

			// 删除医学影像
			if (labImageDeleteList != null && !labImageDeleteList.isEmpty()) {
				for (Object labImageDeleteObject : labImageDeleteList) {
					commonService.delete(labImageDeleteObject);
				}
			}

			// 插入医学影像
			if (labImageList != null && labImageList.size() != 0) {
				for (Object labImageObject : labImageList) {
					MedicalImaging medicalImage = (MedicalImaging) labImageObject;

					// $Author :tong_meng
					// $Date : 2012/7/11 16:00$
					// [BUG]0007849 MODIFY BEGIN
					byte[] bt = medicalImage.getImageContent();
					medicalImage.setImageContent(null);
					commonService.save(medicalImage);
					medicalImage.setImageContent(bt);
					commonService.update(medicalImage);
					// [BUG]0007849 MODIFY END
				}
			}

			// $Author :jin_peng
			// $Date : 2013/07/10 14:38$
			// [BUG]0034638 DELETE BEGIN
			// 删除病历文档
			// Map<String, Object> condition = new HashMap<String, Object>();
			// condition.put("documentSn", documentSn);
			// condition.put("deleteFlag", Constants.NO_DELETE_FLAG);
			// List<Object> clinicalDocuments = entityDao.selectByCondition(
			// ClinicalDocument.class, condition);
			// if (clinicalDocuments != null && clinicalDocuments.size() > 0)
			// {
			// // Author :jia_yanqing
			// // Date : 2013/3/21 13:00
			// // [BUG]0014658 MODIFY BEGIN
			// // 病历文档改为物理删除
			// ClinicalDocument cd = (ClinicalDocument)
			// clinicalDocuments.get(0);
			// // cd.setDeleteFlag(Constants.DELETE_FLAG);
			// // cd.setDeleteTime(systemTime);
			// entityDao.delete(cd);
			// }

			// [BUG]0034638 DELETE END

			// $Author :tong_meng
			// $Date : 2013/5/28 17:59$
			// [BUG]0015170 ADD BEGIN
			if (resultReplication != null) {
				commonService.save(resultReplication);
			}

			if (examResults != null && !examResults.isEmpty()) {
				for (Object object : examResults) {
					commonService.save(object);
				}
			}
			// [BUG]0015170 ADD BEGIN

		}

		// [BUG]0014658 MODIFY END

		if (Constants.VERSION_NUMBER_INSERT.equals(versionNumber)) {
			// 新增病历文档
			commonService.save(clinicalDocument);
			documentDao.updateClinicalDocumentDocumentCda(message,
					clinicalDocument);

			// 新增检查报告
			if (examResultList != null) {
				for (Object examResultObject : examResultList) {
					ExaminationResult examResult = (ExaminationResult) examResultObject;
					commonService.save(examResult);
				}
			}

			// 新增检查项目
			if (examItemList != null && !examItemList.isEmpty()) {
				for (Object examItemObject : examItemList.get(0)) {
					ExaminationItem examItem = (ExaminationItem) examItemObject;
					commonService.save(examItem);
				}
			}

			// 新增医学影像
			if (medicalImagesList != null && !medicalImagesList.isEmpty()) {
				for (Object labImageObject : medicalImagesList.get(0)) {
					MedicalImaging medicalImaging = (MedicalImaging) labImageObject;
					byte[] bt = medicalImaging.getImageContent();
					medicalImaging.setImageContent(null);
					commonService.save(medicalImaging);
					medicalImaging.setImageContent(bt);
					commonService.update(medicalImaging);

				}
			}

			// $Author :tong_meng
			// $Date : 2013/5/28 17:59$
			// [BUG]0015170 ADD BEGIN
			if (resultReplication != null) {
				commonService.save(resultReplication);
			}

			if (examResults != null && !examResults.isEmpty()) {
				for (Object object : examResults) {
					commonService.save(object);
				}
			}
			// [BUG]0015170 ADD BEGIN

			// $Author :jin_peng
			// $Date : 2012/09/20 14:47$
			// [BUG]0009691 MODIFY BEGIN
			// 更新检查医嘱中的检查报告内部序列号
			if (visitSn != null) {
				this.updateExamOrderReportSn(reportSn, patientDomain,
						patientLid, orderLidList, orgCode, systemTime, null,
						message.getServiceId(), visitSn);
			}
			// [BUG]0009691 MODIFY END
		}

	}

	// [BUG]0009691 MODIFY END
	// [BUG]0011864 MODIFY END
	// [BUG]0034638 MODIFY END

	// $Author:bin_zhang
	// $Date:2012/9/28 10:42
	// $[BUG]BUG0010082 ADD BEGIN
	@Override
	@Transactional
	public ExamListDto[] selectExamListSearch(String visitSn) {
		ExamListDto[] examResult = examDao.selectExamList(visitSn);
		return examResult;
	}

	// $[BUG]BUG0010082 ADD END

	// $Author:chang_xuewen
	// $Date:2013/07/01 17:10
	// $[BUG]0032417 ADD BEGIN
	/**
	 * 增加pagingContext满足门诊视图翻页需求
	 */
	@Override
	@Transactional
	public ExamListDto[] selectExamListSearch(String visitSn,
			PagingContext pagingContext) {
		ExamListDto[] examResult = examDao.selectExamList(visitSn,
				pagingContext);
		return examResult;
	}

	// $[BUG]0032417 ADD END

	@Override
	@Transactional
	public MedicalImaging selectImageBySn(String imagingSn) {
		MedicalImaging image = null;
		MedicalImaging[] images = examDao.selectImageBySn(imagingSn);
		if (images != null && images.length != 0) {
			image = images[0];
		}
		return image;
	}

	@Override
	@Transactional
	public ExamCVISDto selectExamCVISDetail(String patientSn, String itemClass,
			String itemSn, String reportSn) {
		return examDao.selectExamCVISDetail(patientSn, itemClass, itemSn,
				reportSn, Constants.EXAM_ITEMCLASS_CORONARY_ANGIOGRAPHY);
	}

	@Override
	@Transactional
	public List<ExaminationResultDetail> selectExamCVISItem(String itemSn,
			String itemClass, String examResultProcedureSn) {
		return examDao.selectExamCVISItem(itemSn, itemClass,
				examResultProcedureSn);
	}

	@Override
	@Transactional
	public List<OrderStepDto> selectExamOrderSteps(ExaminationOrder examOrder,
			String domain) throws Exception {
		// 保存医嘱状态列表
		List<OrderStepDto> orderSteps = new ArrayList<OrderStepDto>();
		// 若医嘱为临时医嘱
		if (Constants.TEMPORARY_FLAG.equals(examOrder.getTemporaryFlag())) {
			String orderStatus = examOrder.getOrderStatus();
			// 三种医嘱状态DTO封装（下达、确认、付费）
			OrderStepDto orderStepIssue = commonService.getOrderStep(
					Constants.ORDER_STATUS_ISSUE, examOrder);
			OrderStepDto orderStepValidated = commonService.getOrderStep(
					Constants.ORDER_STATUS_VALIDATED, examOrder);
			OrderStepDto orderStepPayment = commonService.getOrderStep(
					Constants.ORDER_STATUS_PAYMENT, examOrder);

			// 根据不同的情况为医嘱状态列表添加信息
			if (Constants.ORDER_STATUS_ISSUE.equals(orderStatus)) {
				orderSteps.add(orderStepIssue);
				orderSteps.add(orderStepPayment);
			} else if (Constants.ORDER_STATUS_VALIDATED.equals(orderStatus)) {
				orderSteps.add(orderStepIssue);
				orderSteps.add(orderStepValidated);
			} else {
				orderSteps.add(orderStepIssue);
				if (Constants.PATIENT_DOMAIN_INPATIENT.equals(domain)) {
					orderSteps.add(orderStepValidated);
				} else {
					orderSteps.add(orderStepPayment);
				}
				orderSteps.addAll(commonService.getOrderStepDtos(examOrder
						.getOrderSn().toString()));
			}
			// 获取已收费信息节点
			if (Constants.PATIENT_DOMAIN_OUTPATIENT.equals(examOrder
					.getPatientDomain())
					&& examOrder.getChargeStatusCode() != null
					&& !Constants.CHARGE_STATUS_NOTCHARGE.equals(examOrder
							.getChargeStatusCode())) {
				/*
				 * OrderStepDto chargeOrderStep =
				 * commonService.getChargeOrderStep
				 * (examOrder.getOrderSn().toString()); if (chargeOrderStep !=
				 * null) orderSteps.add(chargeOrderStep);
				 */
			}
			// 设置医嘱已预约节点显示信息
			for (OrderStepDto orderStep : orderSteps) {
				if (Constants.ORDER_STATUS_APPOINTMENT.equals(orderStep
						.getOrderStatusCode())) {
					String examAppointment = getExamAppointment(examOrder
							.getOrderSn().toString());
					orderStep.setStatusContent(examAppointment);
				}
				if (Constants.ORDER_STATUS_APPOINTMENT.equals(orderStep
						.getOrderStatusCode())
						|| Constants.ORDER_STATUS_CANCEL_APPOINTMENT
								.equals(orderStep.getOrderStatusCode())) {
					orderStep.setExecuteDeptName("预约中心");
				}

			}

			// 对列表内容使用执行时间字段进行升序排列
			Collections.sort(orderSteps);
		}
		return orderSteps;
	}
	
	@Transactional
	@Override
	public List<OrderStepDto> selectExamOrderStepsForGD(
			ExaminationOrder examOrder) throws Exception {
		// 保存医嘱状态列表
		List<OrderStepDto> orderSteps = new ArrayList<OrderStepDto>();
		// 若医嘱为临时医嘱
		if (Constants.TEMPORARY_FLAG.equals(examOrder.getTemporaryFlag())) {
			OrderStepDto orderStepIssue = commonService.getOrderStep(
					Constants.ORDER_STATUS_ISSUE, examOrder);
			orderSteps.add(orderStepIssue);
			orderSteps.addAll(commonService.getOrderStepDtos(examOrder
					.getOrderSn().toString()));
/*			if(!Constants.CHARGE_STATUS_NOTCHARGE.equals(examOrder.getChargeStatusCode())){
				OrderStepDto orderStepPayment = commonService.getOrderStep(
						Constants.ORDER_STATUS_PAYMENT, examOrder);
				orderSteps.add(orderStepPayment);
				if(Constants.CHARGE_STATUS_RETURNCHARGE.equals(examOrder.getChargeStatusCode())){
					OrderStepDto orderStepReturn = commonService.getOrderStep(
							Constants.ORDER_STATUS_RETURNED_CHARGE, examOrder);
					orderSteps.add(orderStepReturn);
				}
			}*/
			Collections.sort(orderSteps);
		}
		return orderSteps;
	}
	

	// 获取检查预约信息
	@Transactional
	private String getExamAppointment(String orderSn) {
		StringBuilder examAppString = new StringBuilder();
		Map<String, Object> condition = new HashMap<String, Object>();
		condition.put("orderSn", orderSn);
		Object[] examApps = entityDao.selectByCondition(ExamAppointment.class,
				condition).toArray();
		if (examApps != null && examApps.length > 0) {
			Arrays.sort(examApps, new Comparator<Object>() {
				@Override
				public int compare(Object o1, Object o2) {
					ExamAppointment examApp1 = (ExamAppointment) o1;
					ExamAppointment examApp2 = (ExamAppointment) o2;
					return DateUtils.compareDate(examApp1.getCreateTime(),
							examApp2.getCreateTime());
				}
			});
			ExamAppointment examApp = (ExamAppointment) examApps[0];
			examAppString
					.append("预约号:" + examApp.getAppointmentNo() + "\\n")
					.append("预约时间："
							+ DateUtils.dateToString(
									examApp.getAppointmentTime(),
									"yyyy-MM-dd HH:mm") + "\\n")
					.append("预约员：" + examApp.getAppPerformerName() + "\\n")
					.append("预约设备：" + examApp.getAppDeviceName() + "\\n")
					.append("执行科室：" + examApp.getExecDeptName());
		}
		return examAppString.toString();
	}

	@Transactional
	public ExaminationItem getExamItemPresenceByIndex(String imageIndex) {
		// TODO Auto-generated method stub
		Map<String, Object> condition = new HashMap<String, Object>();
		condition.put("imageIndexNo", imageIndex);

		List<Object> examItem = entityDao.selectByCondition(
				ExaminationItem.class, condition);

		if (examItem != null && !examItem.isEmpty()) {

			return ((ExaminationItem) examItem.get(0));

		} else {
			return null;
		}
	}

	@Transactional
	public ExaminationResult getExamResultPresenceBySn(BigDecimal examReportSn) {
		// TODO Auto-generated method stub
		Map<String, Object> condition = new HashMap<String, Object>();
		condition.put("examReportSn", examReportSn);

		List<Object> examResult = entityDao.selectByCondition(
				ExaminationResult.class, condition);

		if (examResult != null && !examResult.isEmpty()) {

			return ((ExaminationResult) examResult.get(0));

		} else {
			return null;
		}
	}

	@Override
	public BigDecimal selectDocumentSnByResultSn(String reportSn) {
		return examDao.selectDocumentSnByResultSn(reportSn);
	}


}
