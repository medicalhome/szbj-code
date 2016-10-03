package com.yly.cdr.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Transactional;

import com.founder.fasf.web.paging.PagingContext;
import com.yly.cdr.dto.ExamCVISDto;
import com.yly.cdr.dto.ExamListDto;
import com.yly.cdr.dto.OrderStepDto;
import com.yly.cdr.dto.exam.ExamListSearchParameters;
import com.yly.cdr.dto.exam.WithdrawRecordDto;
import com.yly.cdr.entity.ClinicalDocument;
import com.yly.cdr.entity.ExaminationItem;
import com.yly.cdr.entity.ExaminationOrder;
import com.yly.cdr.entity.ExaminationResult;
import com.yly.cdr.entity.ExaminationResultDetail;
import com.yly.cdr.entity.ExaminationResultProcedure;
import com.yly.cdr.entity.MedicalImaging;
import com.yly.cdr.entity.MedicalVisit;
import com.yly.cdr.entity.Message;

public interface ExamService {
	@Transactional
	ExamListDto[] selectExamListSearch(String patientSn,
			ExamListSearchParameters examListSearchParameters,
			PagingContext pagingContext);

	// $Author:bin_zhang
	// $Date:2012/9/28 10:42
	// $[BUG]BUG0010082 ADD BEGIN
	@Transactional
	ExamListDto[] selectExamListSearch(String visitSn);

	// $[BUG]BUG0010082 ADD END

	// $Author:chang_xuewen
	// $Date:2013/07/01 17:10
	// $[BUG]0032417 ADD BEGIN
	/**
	 * 增加pagingContext满足门诊视图翻页需求
	 * 
	 * @param visitSn
	 * @param pagingContext
	 * @return
	 */
	@Transactional
	ExamListDto[] selectExamListSearch(String visitSn,
			PagingContext pagingContext);

	// $[BUG]0032417 ADD END

	@Transactional
	ExamListDto[] selectExamDetail(String reportSn, String itemSn);

	// Author:jin_peng
	// Date:2013/01/29 15:57
	// [BUG]0013729 MODIFY BEGIN
	@Transactional
	ExamListDto[] selectRelatedExam(String patientSn, String examinationRegion,
			String examinationItem, String orderSn);

	// [BUG]0013729 MODIFY END

	@Transactional
	ExaminationOrder selectExamOrderDetail(String orderSn);

	@Transactional
	WithdrawRecordDto[] selectWithdrawRecord(String reportSn);

	@Transactional
	public MedicalVisit mediVisit(String patientDomain, String patientLid,
			Integer visitTimes);

	@Transactional
	public List<Object> selectMedicalVisitById(Map<String, Object> map);

	@Transactional
	public List<Object> selectExaminationOrderById(Map<String, Object> map);

	// $Author :chang_xuewen
	// $Date : 2013/12/03 11:00$
	// [BUG]0040271 MODIFY BEGIN
	// $Author :du_xiaolan
	// $Date : 2014/10/28
	/**
	 * 获取医嘱信息 以下参数顺序必须固定
	 * 
	 * @param patientDomain
	 *            域代码
	 * @param patientLid
	 *            患者本地ID
	 * @param orderLid
	 *            医嘱号
	 * @param orgCode
	 *            医疗机构代码
	 * @param visitSn
	 *            就诊内部序列号
	 * @return 实体对象
	 */

	@Transactional
	public ExaminationOrder getOrder(Object... args);

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
	 * 通过申请单号查询出相应的检查申请对象集合
	 * 
	 * @param patientDomain
	 *            域ID
	 * @param patientLid
	 *            患者本地ID
	 * @param requestNo
	 *            申请单号
	 * @param orgCode
	 *            医疗机构代码
	 * @param visitSn
	 *            就诊内部序列号
	 * @return 检查申请对象集合
	 */
	@Transactional
	public ExaminationOrder getRequest(Object... args);

	// [BUG]0040271 MODIFY END
	// [BUG]0009718 ADD END
	// [BUG]0038805 MODIFY END

	// Author :jia_yanqing
	// Date : 2013/11/13 20:48
	// [BUG]0038805 ADD BEGIN
	// $Author :chang_xuewen
	// $Date : 2013/12/03 11:00$
	// [BUG]0040271 MODIFY BEGIN
	// Author :du_xiaolan
	// Date :2014/10/29
	// [action]add visitSn
	/**
	 * 通过申请单号查询出相应的检查申请对象集合
	 * 
	 * @param patientDomain
	 *            域ID
	 * @param patientLid
	 *            患者本地ID
	 * @param requestNo
	 *            申请单号
	 * @param orgCode
	 *            医疗机构代码
	 * @param visitSn
	 *            就诊内部序列号
	 * @return 检查申请对象集合
	 */
	@Transactional
	public List<Object> getRequestList(Object... args);

	// [BUG]0040271 MODIFY END
	// [BUG]0038805 ADD END

	// $Author :jin_peng
	// $Date : 2012/10/22 14:59$
	// [BUG]0010434 MODIFY BEGIN
	@Transactional
	ExaminationResult getReportPresence(String examReportLid, String itemClass,
			String orgCode);

	// [BUG]0010434 MODIFY END

	// $Author :jin_peng
	// $Date : 2012/11/23 13:59$
	// [BUG]0011864 MODIFY BEGIN
	// $Author :jin_peng
	// $Date : 2012/09/20 14:47$
	// [BUG]0009691 MODIFY BEGIN
	// 添加申请单号集合
	@Transactional
	public void updateExamOrderReportSn(BigDecimal reportSn,
			String patientDomain, String patientLid, List<String> orderLidList,
			String orgCode, Date systemTime, List<List<Object>> examItemList,
			String serviceId, BigDecimal visitSn);

	// [BUG]0009691 MODIFY END
	// [BUG]0011864 MODIFY END

	@Transactional
	public List<Object> selectExamItemByCondition(Map<String, Object> map);

	// $Author :jin_peng
	// $Date : 2012/11/23 13:59$
	// [BUG]0011864 MODIFY BEGIN
	// $Author :jin_peng
	// $Date : 2012/09/20 14:47$
	// [BUG]0009691 MODIFY BEGIN
	// 添加申请单号集合
	@Transactional
	void updateInsertExamResult(Message message, List<Object> examResultList,
			List<List<Object>> examItemList,
			List<List<Object>> medicalImagesList, String versionNumber,
			BigDecimal reportSn, String patientDomain, String patientLid,
			ExaminationResultProcedure resultReplication,
			List<Object> examResult, List<String> orderLidList,
			ClinicalDocument clinicalDocument, BigDecimal documentSn,
			String orgCode, Date systemTime, String serviceId,
			BigDecimal visitSn);

	// [BUG]0009691 MODIFY END
	// [BUG]0011864 MODIFY END

	@Transactional
	MedicalImaging selectImageBySn(String imagingSn);

	@Transactional
	ExamCVISDto selectExamCVISDetail(String patientSn, String itemClass,
			String itemSn, String reportSn);

	@Transactional
	List<ExaminationResultDetail> selectExamCVISItem(String itemSn,
			String itemClass, String examResultProcedureSn);

	@Transactional
	List<OrderStepDto> selectExamOrderSteps(ExaminationOrder examOrder,
			String domain) throws Exception;
	
	/**
	 * 查询医嘱闭环（港大）
	 * */ 
	@Transactional
	List<OrderStepDto> selectExamOrderStepsForGD(ExaminationOrder examOrder) throws Exception;
	
	@Transactional
	ExaminationItem getExamItemPresenceByIndex(String imageIndex);

	ExaminationResult getExamResultPresenceBySn(BigDecimal examReportSn);
	
	BigDecimal selectDocumentSnByResultSn(String reportSn);
}
