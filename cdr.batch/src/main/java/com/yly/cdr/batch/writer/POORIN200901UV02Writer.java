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
import com.yly.cdr.cache.DictionaryCache;
import com.yly.cdr.core.Constants;
import com.yly.cdr.entity.ExaminationOrder;
import com.yly.cdr.entity.MedicalVisit;
import com.yly.cdr.entity.NursingOperation;
import com.yly.cdr.entity.PastHistory;
import com.yly.cdr.entity.PatientDoctor;
import com.yly.cdr.hl7.dto.poorin200901uv02.ExaminationApplication;
import com.yly.cdr.hl7.dto.poorin200901uv02.ExaminationOrderDto;
import com.yly.cdr.hl7.dto.poorin200901uv02.POORIN200901UV02;
import com.yly.cdr.hl7.dto.poorin200901uv02.PastDisease;
import com.yly.cdr.service.CommonService;
import com.yly.cdr.service.ExamService;
import com.yly.cdr.service.impl.CommonServiceImpl;
import com.yly.cdr.util.DataMigrationUtils;
import com.yly.cdr.util.DateUtils;
import com.yly.cdr.util.DictionaryUtils;
import com.yly.cdr.util.StringUtils;

/**
 * 检查申请单Writer 注意：这里需要根据业务，添加判断条件。如：
 * 1：如果当前消息是子消息，父消息如果存在，则保存子消息内容；如果父消息不存在，则不保存子消息内容。 2：如果是父消息，直接保存父消息内容。
 * 
 * @author zhangbin
 * @version 1.0
 */

@Component(value = "poorin200901uv02Writer")
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class POORIN200901UV02Writer implements BusinessWriter<POORIN200901UV02> {

	private static final Logger logger = LoggerFactory
			.getLogger(POORIN200901UV02Writer.class);

	private static final Logger loggerBS002 = LoggerFactory.getLogger("BS002");

	@Autowired
	private ExamService examService;

	@Autowired
	private CommonService commonService;

	@Autowired
	private CommonWriterUtils commonWriterUtils;

	// 关联的就诊记录
	private MedicalVisit medicalVisit;

	// 患者本地Id
	private String patientLid;

	// 系统时间取得
	private Date systemTime = DateUtils.getSystemTime();

	// 数据库中需更新的检查医嘱记录
	private List<ExaminationOrder> examinationOrdersNeedUpdate = new ArrayList<ExaminationOrder>();

	// $Author :jin_peng
	// $Date : 2012/9/14 14:48$
	// [BUG]0009718 ADD BEGIN
	// 需要删除的检查申请对象集合
	private List<Object> examinationOrdersDeleteList;

	// [BUG]0009718 ADD EMD
	// 护理操作记录
	private List<NursingOperation> nurList;
	// 护理操作.护理内部序列号sequences
    private static final String SEQ_NURSING_OPERATION = "SEQ_NURSING_OPERATION";
	// $Author: tong_meng
	// $Date: 2013/8/30 15:00
	// [BUG]0036757 ADD BEGIN
	private String serviceId;

	// [BUG]0036757 ADD END

	// $Author: du_xiaolan
	// $Date: 2014/10/27 9:23
	// 就诊流水号 or 就诊次数 cdr.properties里配置

	/**
	 * 数据校验
	 * 
	 * @param baseDto
	 */
	public boolean validate(POORIN200901UV02 poorin200901uv02) {
		/*
		 * $Author: yu_yzh
		 * $Date: 2015/7/29 10:29
		 * V2消息结构中既往史和诊断不能跟CDR中直接对应，做一步转换
		 * */
		poorin200901uv02.changeObxsToDiagnosisAndPastDiseases();
		
		// $Author :chang_xuewen
		// $Date : 2014/1/22 10:30$
		// [BUG]0042086 MODIFY BEGIN
		// $Author :chang_xuewen
		// $Date : 2013/12/05 11:00$
		// [BUG]0040271 ADD BEGIN
		/*
		 * if(!poorin200901uv02.getOrgCode().equals(poorin200901uv02.getMessage()
		 * .getOrgCode())){ loggerBS002.error( "Message:{}, validate:{}",
		 * poorin200901uv02.toString(), "消息头与消息体中的医疗机构代码不一致：头：OrgCode = " +
		 * poorin200901uv02.getMessage().getOrgCode() + " ，体：OrgCode =" +
		 * poorin200901uv02.getOrgCode()); return false; }
		 */
		if (StringUtils.isEmpty(poorin200901uv02.getOrgCode())) {
			// $Author :yang_mingjie
			// $Date : 2014/06/26 10:09$
			// [BUG]0045630 MODIFY BEGIN
			poorin200901uv02.setOrgCode(Constants.HOSPITAL_CODE);
			poorin200901uv02.setOrgName(Constants.HOSPITAL_NAME);
			// [BUG]0045630 MODIFY END
		}

		// [BUG]0040271 ADD END
		// [BUG]0042086 MODIFY END
		// $Author: tong_meng
		// $Date: 2013/8/30 15:00
		// [BUG]0036757 ADD BEGIN
		serviceId = poorin200901uv02.getMessage().getServiceId();
		// [BUG]0036757 ADD END
		// $Author :jin_peng
		// $Date : 2012/9/14 14:48$
		// [BUG]0009718 MODIFY BEGIN
		// $Author :jia_yanqing
		// $Date : 2012/9/12 15:00$
		// [BUG]0009655 ADD BEGIN
		List<ExaminationApplication> examinationApplicationList = poorin200901uv02
				.getExaminationApplications();
		int len = examinationApplicationList.size();
		for (int i = 0; i < len; i++) {
			ExaminationApplication examinationApplication = examinationApplicationList
					.get(i);

			/*
			 * Author: yu_yzh
			 * Date: 2015/10/23 10:01
			 * 港大OMP^O09中没有申请单号，不验证
			 * */
			if(Constants.V2.equals(poorin200901uv02.getMessage().getMsgMode()) && poorin200901uv02.getMessage().getContent().contains("OMP\\^O09")){
				if (Constants.lstDomainOutPatient().contains(
						poorin200901uv02.getPatientDomain())
						&& Constants.lstVisitTypeOutPatient().contains(
								poorin200901uv02.getVisitTypeCode())) {
					if (!StringUtils.isNotNullAll(examinationApplication
							.getRequestNo())) {
						loggerBS002.error("Message:{}, validate:{}",
								poorin200901uv02.toString(),
								"非空字段验证未通过！申请单编号：RequestNo = "
										+ examinationApplication.getRequestNo());
						return false;
					}

					// 当门诊删除时除验证申请单号外不再对其他字段进行非空校验
					if (isDeleteMessage(poorin200901uv02)) {
						if (i == (len - 1)) {
							return true;
						}

						continue;
					}
				}
			}
			// $Author :chang_xuewen
			// $Date : 2013/12/03 11:00$
			// [BUG]0040271 ADD BEGIN
			// 增加验证医疗机构代码非空校验
			if (!StringUtils.isNotNullAll(poorin200901uv02.getOrgCode())) {
				loggerBS002.error(
						"Message:{}, validate:{}",
						poorin200901uv02.toString(),
						"非空字段验证未通过！医疗机构代码：OrgCode = "
								+ poorin200901uv02.getOrgCode());
				return false;
			}
			// [BUG]0040271 ADD END

			// 根据服务设计书验证非空内容 检查申请部分
			if (!StringUtils.isNotNullAll(
					examinationApplication.getOrderType(),
					examinationApplication.getOrderTypeName()/*,
					examinationApplication.getExecutiveDept(),
					examinationApplication.getExecutiveDeptName()*/)) {
				loggerBS002
						.error("Message:{}, validate:{}", poorin200901uv02
								.toString(), "非空字段验证未通过！医嘱类型：orderType = "
								+ examinationApplication.getOrderType()
								+ "; 医嘱类型名称：orderTypeName = "
								+ examinationApplication.getOrderTypeName()
								+ "; 执行科室：executiveDept = "
								+ examinationApplication.getExecutiveDept()
								+ "; 执行科室名称：executiveDeptName = "
								+ examinationApplication.getExecutiveDeptName());

				return false;
			}

			// 根据服务设计书验证非空内容 检查申请对应的检查医嘱部分
			if (!isOrderValidation(poorin200901uv02,
					examinationApplication.getExaminationOrderDtos())) {
				return false;
			}
		}
		// [BUG]0009655 ADD END
		// [BUG]0009718 MODIFY END

		return true;
	}

	// $Author :jin_peng
	// $Date : 2012/9/16 14:48$
	// [BUG]0009718 ADD BEGIN
	/**
	 * 数据校验医嘱部分字段
	 * 
	 * @param examinationOrderDto
	 *            检查医嘱对象
	 * @return 是否通过验证标识
	 */
	private boolean isOrderValidation(POORIN200901UV02 poorin200901uv02,
			List<ExaminationOrderDto> examinationOrderDtoList) {
		// 是否通过验证标识
		boolean isOrderNotNull = true;

		// 消息中检查医嘱对象集合存在时验证相应字段是否为空,开立/更新申请时验证
		if ( (isNewMessage(poorin200901uv02) || isUpdateMessage(poorin200901uv02)) &&
				examinationOrderDtoList != null	&& !examinationOrderDtoList.isEmpty()) {
			for (ExaminationOrderDto examinationOrderDto : examinationOrderDtoList) {
				// 验证服务设计书中提及的内容是否为空 (检查医嘱)
				if (!StringUtils.isNotNullAll(
						examinationOrderDto.getItemCode(),
						examinationOrderDto.getItemName())) {
					loggerBS002.error("Message:{}, validate:{}",
							examinationOrderDto.toString(),
							"非空字段验证未通过！检查项目代码：itemCode = "
									+ examinationOrderDto.getItemCode()
									+ "; 检查项目名称：itemName = "
									+ examinationOrderDto.getItemName());

					isOrderNotNull = false;
				}

				// $Author :tong_meng
				// $Date : 2012/9/21 15:07$
				// [BUG]9776 ADD BEGIN
				// 如果是HIS住院的情况，验证医嘱号是否为空！
				if (Constants.lstDomainInPatient().contains(
						poorin200901uv02.getPatientDomain())
						&& Constants.lstVisitTypeInPatient().contains(
								poorin200901uv02.getVisitTypeCode())) {
					if (!StringUtils.isNotNullAll(examinationOrderDto
							.getOrderLid())) {
						loggerBS002.error("Message:{}, validate:{}",
								examinationOrderDto.toString(),
								"非空字段验证未通过！医嘱号：orderLid = "
										+ examinationOrderDto.getOrderLid());
						isOrderNotNull = false;
					}
				}
				// [BUG]9776 ADD END
				// $Author :wu_biao
				// $Date : 2012/9/24 15:07$
				// [BUG]9988 ADD BEGIN
				// 如果是HIS门诊的情况，检查方法code、检查方法名称是否为空！
//				if (Constants.lstDomainOutPatient().contains(
//						poorin200901uv02.getPatientDomain())
//						&& Constants.lstVisitTypeOutPatient().contains(
//								poorin200901uv02.getVisitTypeCode())) {
//					if (!StringUtils.isNotNullAll(
//							examinationOrderDto.getExamMethodCode(),
//							examinationOrderDto.getExamMethodName())) {
//						loggerBS002.error(
//								"Message:{}, validate:{}",
//								examinationOrderDto.toString(),
//								"非空字段验证未通过！检查方法代码：examMethodCode = "
//										+ examinationOrderDto
//												.getExamMethodCode()
//										+ "; 检查方法名称：examMethodName = "
//										+ examinationOrderDto
//												.getExamMethodName());
//
//						isOrderNotNull = false;
//					}
//				}
				// [BUG]9988 ADD END
			}
		}

		return isOrderNotNull;
	}

	// [BUG]0009718 ADD END

	public boolean checkDependency(POORIN200901UV02 poorin200901uv02,
			boolean flag) {
		// $Author :tong_meng
		// $Date : 2012/5/28 14:00$
		// [BUG]0006657 MODIFY BEGIN
		// 患者本地ID
		patientLid = poorin200901uv02.getPatientLid();
		logger.info("检查申请消息关联信息校验,患者本地ID {}", patientLid);
		// [BUG]0006657 MODIFY END

		if (patientLid == null) {
			logger.error("MessageId:{};患者本地ID不存在", poorin200901uv02
					.getMessage().getId());
			if (flag) {
				loggerBS002.error("Message:{},checkDependency:{}",
						poorin200901uv02.toString(), "患者本地ID不存在");
			}
			return false;
		}
		boolean isMedicalVisit=checkMedicalVisit(poorin200901uv02, flag);
		if(!isMedicalVisit){
			return false;
		}
		if (isNewMessage(poorin200901uv02)) {
			logger.info("消息新增场合");
			if(isMedicalVisit){
				return true;
			}
		} else if (isUpdateMessage(poorin200901uv02)) {
			int count = checkExamOrderInfo(poorin200901uv02, flag);
			logger.info("消息更新场合");
			return count > 0;
		}
		// $Author :jin_peng
		// $Date : 2012/9/16 14:48$
		// [BUG]0009718 ADD BEGIN
		else if (isDeleteMessage(poorin200901uv02)) {
			logger.info("消息删除场合");
			return checkExamRequestInfo(poorin200901uv02, flag) > 0;
		}
		// [BUG]0009718 ADD END

		logger.error("消息交互类型不存在！");
		return false;
	}

	// $Author :jin_peng
	// $Date : 2012/9/16 14:48$
	// [BUG]0009718 ADD BEGIN
	/**
	 * 检查申请信息是否存在
	 * 
	 * @param poorin200901uv02
	 *            检查申请消息映射数据
	 * @return true:存在；false:不存在
	 */
	private int checkExamRequestInfo(POORIN200901UV02 poorin200901uv02,
			boolean flag) {
		int examinationOrderCount = 0;
		List<Object> examinationRequestList = null;


		// 创建待逻辑删除的检验申请对象集合
		examinationOrdersDeleteList = new ArrayList<Object>();

		List<ExaminationApplication> examinationApplications = poorin200901uv02
				.getExaminationApplications();

		// 遍历消息中的检查申请单
		for (ExaminationApplication examinationApplication : examinationApplications) {
			
			/*
			 * Author: yu_yzh
			 * Date: 2015/10/23 11:18
			 * 港大OMP^O09消息中没有申请单号，根据医嘱号来查 ADD BEGIN
			 * */
			if(isOMPO09Message(poorin200901uv02)){
				examinationRequestList = new ArrayList<Object>();
				for(ExaminationOrderDto examinationOrderDto : examinationApplication.getExaminationOrderDtos()){
					ExaminationOrder eo = examService.getOrder(
							poorin200901uv02.getPatientDomain(), patientLid,
							examinationOrderDto.getOrderLid(),
							poorin200901uv02.getOrgCode(),
							medicalVisit.getVisitSn()
						);
					if(eo != null){
						examinationRequestList.add(eo);
					}
				}
			} 
			// ADD END
			else {
				// $Author :chang_xuewen
				// $Date : 2013/12/03 11:00$
				// [BUG]0040271 MODIFY BEGIN
				// 通过域ID、患者本地ID、申请单号查找待删除的记录
				examinationRequestList = examService.getRequestList(
						poorin200901uv02.getPatientDomain(), patientLid,
						examinationApplication.getRequestNo(),
						poorin200901uv02.getOrgCode(),
						medicalVisit.getVisitSn());
			}
			// [BUG]0040271 MODIFY END

			// 待删除记录不存在
			if (examinationRequestList == null
					|| examinationRequestList.isEmpty()) {
				logger.error(
						"MessageId:{};需删除的检查医嘱不存在，{}",
						poorin200901uv02.getMessage().getId(),
						"申请单号为：" + examinationApplication.getRequestNo()
								+ "; 域ID为："
								+ poorin200901uv02.getPatientDomain()
								+ "; 患者本地ID为：" + patientLid + "; 就诊内部序列号为："
								+ medicalVisit.getVisitSn());

				if (flag) {
					loggerBS002.error(
							"Message:{},checkDependency:{}",
							poorin200901uv02.toString(),
							"需删除的检查医嘱不存在: " + "申请单号为："
									+ examinationApplication.getRequestNo()
									+ "; 域ID为："
									+ poorin200901uv02.getPatientDomain()
									+ "; 患者本地ID为：" + patientLid + "; 就诊内部序列号为："
									+ medicalVisit.getVisitSn()+"; 就诊类型编码"
											+ poorin200901uv02.getVisitTypeCode());
				}
			} else {
				examinationOrderCount += examinationRequestList.size();
				// 设置逻辑删除信息
				setDeleteLogicMessages(examinationRequestList);
				// 记录存在，将医嘱信息添加到待更新列表中
				examinationOrdersDeleteList.addAll(examinationRequestList);
			}
		}

		return examinationOrderCount;
	}

	// [BUG]0009718 ADD END

	// $Author :jin_peng
	// $Date : 2012/9/16 14:48$
	// [BUG]0009718 ADD BEGIN
	/**
	 * 设置相应检查申请对象为删除状态
	 * 
	 * @param examinationRequestList
	 *            待逻辑删除的检查申请对象集合
	 */
	private void setDeleteLogicMessages(List<Object> examinationRequestList) {
		// 循环设置相应检查申请对象的逻辑删除信息
		for (Object object : examinationRequestList) {
			ExaminationOrder examinationOrder = (ExaminationOrder) object;

			// 设置删除标识为已删除状态
			examinationOrder.setDeleteFlag(Constants.DELETE_FLAG);

			// 设置更新时间
			examinationOrder.setUpdateTime(systemTime);

			// 设置删除时间
			examinationOrder.setDeleteTime(systemTime);

			// $Author: tong_meng
			// $Date: 2013/8/30 15:00
			// [BUG]0036757 ADD BEGIN
			examinationOrder.setDeleteby(serviceId); // 设置创建人
			// [BUG]0036757 ADD END

		}
	}

	// [BUG]0009718 ADD END

	/**
	 * 检查医嘱信息是否存在
	 * 
	 * @param 检查申请消息映射数据
	 * @return true:存在；false:不存在
	 */
	private int checkExamOrderInfo(POORIN200901UV02 poorin200901uv02,
			boolean flag) {
		int examinationOrderCount = 0;
		List<ExaminationApplication> examinationApplications = poorin200901uv02
				.getExaminationApplications();
		// 遍历消息中的检查申请单
		for (ExaminationApplication examinationApplication : examinationApplications) {
			// 遍历检查申请单中的检查医嘱
			for (ExaminationOrderDto examinationOrderDto : examinationApplication
					.getExaminationOrderDtos()) {
				// $Author :chang_xuewen
				// $Date : 2013/12/03 11:00$
				// [BUG]0040271 MODIFY BEGIN
				// 通过域ID、患者本地ID、医嘱号查找待更新的那条记录
				ExaminationOrder examinationOrder = null;

				examinationOrder = examService.getOrder(
						poorin200901uv02.getPatientDomain(), patientLid,
						examinationOrderDto.getOrderLid(),
						poorin200901uv02.getOrgCode(),
						medicalVisit.getVisitSn()
					);

				// [BUG]0040271 MODIFY END
				// 记录不存在
				if (examinationOrder == null) {
						logger.error(
								"MessageId:{};需更新的检查医嘱不存在，医嘱号:"
										+ examinationOrderDto.getOrderLid()
										+ "患者本地ID:" + patientLid + "域ID:"
										+ poorin200901uv02.getPatientDomain()
										+ "就诊内部序列号:"
										+ medicalVisit.getVisitSn(),
								poorin200901uv02.getMessage().getId());
						if (flag) {
							loggerBS002.error(
									"Message:{},checkDependency:{}",
									poorin200901uv02.toString(),
									"需更新的检查医嘱不存在，医嘱号:"
											+ examinationOrderDto.getOrderLid()
											+ "患者本地ID:"
											+ patientLid
											+ "域ID:"
											+ poorin200901uv02
													.getPatientDomain()
											+ "就诊内部序列号:"
											+ medicalVisit
													.getVisitSn()
													);

					}
				} else {
					examinationOrderCount++;
					// 记录存在，将医嘱信息添加到待更新列表中
					examinationOrdersNeedUpdate.add(examinationOrder);
				}
			}
		}
		return examinationOrderCount;
	}

	/**
	 * 就诊相关信息判断
	 * 
	 * @param 检查申请消息映射数据
	 * @return true:存在；false:不存在
	 */
	private boolean checkMedicalVisit(POORIN200901UV02 poorin200901uv02,
			boolean flag) {
		// $Author :chang_xuewen
		// $Date : 2013/12/03 11:00$
		// [BUG]0040271 MODIFY BEGIN
		// 就诊信息取得
		medicalVisit = commonService.mediVisit(
				poorin200901uv02.getPatientDomain(), patientLid,
				poorin200901uv02.getVisitTimes(),
				poorin200901uv02.getOrgCode(),
				poorin200901uv02.getvisitOrdNo(),
				poorin200901uv02.getVisitTypeCode());

		// [BUG]0040271 MODIFY END
		
		/*
		 * Author: yu_yzh
		 * Date: 2015/7/27 15:00
		 * Add begin
		 * */
		if(medicalVisit == null){
			medicalVisit = getMedicalVisitByExamRequestNo(poorin200901uv02);
		}
		/*
		 * Add end
		 * */
		if (medicalVisit == null) {
			logger.error(
					"MessageId:{};检查申请消息新增场合,就诊信息不存在,域ID:"
							+ poorin200901uv02.getPatientDomain()
							+ "患者ID:"
							+ patientLid
							+ "就诊次数:"
							+ poorin200901uv02.getVisitTimes()
							+ "医疗机构编码:" + poorin200901uv02.getOrgCode()
							+ "就诊流水号" + poorin200901uv02.getvisitOrdNo()
							+ "就诊类型编码" + poorin200901uv02.getVisitTypeCode(),
					poorin200901uv02.getMessage().getId());
			if (flag) {
				loggerBS002.error(
						"Message:{},checkDependency:{}",
						poorin200901uv02.toString(),
						"检查申请消息新增场合,就诊信息不存在,域ID:"
								+ poorin200901uv02.getPatientDomain()
								+ "患者ID:"
								+ patientLid
								+ "就诊次数:"
								+ poorin200901uv02
										.getVisitTimes() + "医疗机构编码:"
								+ poorin200901uv02.getOrgCode() + "就诊流水号"
								+ poorin200901uv02.getvisitOrdNo()
								+ "就诊类型编码" + poorin200901uv02.getVisitTypeCode());
			}
			return false;
		}
		return true;
	}

	/**
	 * 判断更新或新增
	 * 
	 * @param baseDto
	 */
	@Transactional
	public void saveOrUpdate(POORIN200901UV02 poorin200901uv02) {
		// 待添加既往史集合
		List<PastHistory> pastHistorysToAdd = new ArrayList<PastHistory>();

		// 待删除既往史集合
		List<Object> pastHistorysToDelete = new ArrayList<Object>();

		// $Author :jin_peng
		// $Date : 2012/9/21 17:18$
		// [BUG]0009718 MODIFY BEGIN
		List<ExaminationOrder> examinationOrders = null;

		if (isNewMessage(poorin200901uv02) || isUpdateMessage(poorin200901uv02)) {
			examinationOrders = getExamMessage(poorin200901uv02);
		}
		// [BUG]0009718 MODIFY END

		// 新增消息
		if (isNewMessage(poorin200901uv02)) {
			// $Author :jin_peng
			// $Date : 2012/9/25 14:46$
			// [BUG]0009863 MODIFY BEGIN
			// 在检查申请新增消息时如果对应的检查申请在数据库中存在则更新相应记录，否则新增相应记录
			if (examinationOrders != null && !examinationOrders.isEmpty()) {
				for (ExaminationOrder examinationOrder : examinationOrders) {
					// 消息中检查申请是否存在，存在则更新，不存在则新增
					if (!StringUtils.isEmpty(StringUtils
							.BigDecimalToStr(examinationOrder.getOrderSn()))) {
						commonService.update(examinationOrder);
						// $Author:wei_peng
						// $Date:2012/10/29 17:56
						// [BUG]0010809 ADD BEGIN
						Map<String, Object> condition = new HashMap<String, Object>();
						condition.put("examOrderSn",
								examinationOrder.getOrderSn());
						List<Object> pastHistorysTmp = commonService
								.selectByCondition(PastHistory.class, condition);
						if (pastHistorysTmp != null
								&& pastHistorysTmp.size() > 0) {
							pastHistorysToDelete.addAll(pastHistorysTmp);
						}
						// [BUG]0010809 ADD END
					} else {
						commonService.save(examinationOrder);
					}
				}
			}
			// [BUG]0009863 MODIFY END

			// $Author:wei_peng
			// $Date:2012/11/05 18:48
			// [BUG]0011030 ADD BEGIN
			if (poorin200901uv02.getOrderPerson() != null
					&& !poorin200901uv02.getOrderPerson().isEmpty()) {
				PatientDoctor patientDoctor = commonWriterUtils
						.getPatientDoctor(medicalVisit.getVisitSn(),
								medicalVisit.getPatientSn(),
								poorin200901uv02.getOrderPerson(),
								poorin200901uv02.getOrderPersonName(),
								systemTime);
				if (patientDoctor != null) {
					// $Author: tong_meng
					// $Date: 2013/8/30 15:00
					// [BUG]0036757 ADD BEGIN
					patientDoctor.setCreateby(DataMigrationUtils.getDataMigration() + serviceId); // 设置创建人
					// [BUG]0036757 ADD END
					commonService.save(patientDoctor);
				}
			}
			// [BUG]0011030 ADD END
		}
		// 更新消息
		else if (isUpdateMessage(poorin200901uv02)) {
			commonService.updateList(examinationOrders);
			// $Author:wei_peng
			// $Date:2012/10/29 17:56
			// [BUG]0010809 ADD BEGIN
			for (ExaminationOrder examinationOrder : examinationOrders) {
				Map<String, Object> condition = new HashMap<String, Object>();
				condition.put("examOrderSn", examinationOrder.getOrderSn());
				List<Object> pastHistorysTmp = commonService.selectByCondition(
						PastHistory.class, condition);
				if (pastHistorysTmp != null && pastHistorysTmp.size() > 0) {
					pastHistorysToDelete.addAll(pastHistorysTmp);
				}
			}
			// [BUG]0010809 ADD END
		}
		if(nurList != null && !nurList.isEmpty()){
			commonService.saveList(nurList);
		}
		
		// $Author :jin_peng
		// $Date : 2012/9/16 14:48$
		// [BUG]0009718 ADD BEGIN
		// 删除消息
		else if (isDeleteMessage(poorin200901uv02)) {
			commonService.updatePartiallyAll(examinationOrdersDeleteList,
					"deleteFlag", "updateTime", "deleteTime", "deleteby");
			// [BUG]0009718 ADD END
			// $Author:wei_peng
			// $Date:2012/10/29 17:56
			// [BUG]0010809 ADD BEGIN
			for (Object object : examinationOrdersDeleteList) {
				ExaminationOrder examinationOrder = (ExaminationOrder) object;
				Map<String, Object> condition = new HashMap<String, Object>();
				condition.put("examOrderSn", examinationOrder.getOrderSn());
				List<Object> pastHistorysTmp = commonService.selectByCondition(
						PastHistory.class, condition);
				if (pastHistorysTmp != null && pastHistorysTmp.size() > 0) {
					pastHistorysToDelete.addAll(pastHistorysTmp);
				}
			}
			// [BUG]0010809 ADD END
		}
		// [BUG]0009718 ADD END
		// $Author:wei_peng
		// $Date:2012/10/29 17:56
		// [BUG]0010809 ADD BEGIN
		if (examinationOrders != null && examinationOrders.size() > 0) {
			for (ExaminationOrder examinationOrder : examinationOrders) {
				pastHistorysToAdd.addAll(getPastHistory(poorin200901uv02,
						examinationOrder.getOrderSn()));
			}
		}
		for (Object pastHistory : pastHistorysToDelete) {
			commonService.delete(pastHistory);
		}
		for (PastHistory pastHistory : pastHistorysToAdd) {
			commonService.save(pastHistory);
		}
		// [BUG]0010809 ADD END

	}

	// $Author:wei_peng
	// $Date:2012/10/29 18:27
	// [BUG]0010809 ADD BEGIN
	/**
	 * 获取既往史集合
	 */
	public List<PastHistory> getPastHistory(POORIN200901UV02 poorin200901uv02,
			BigDecimal examOrderSn) {
		List<PastHistory> pastHistorys = new ArrayList<PastHistory>();
		List<PastDisease> pastDiseaseList = poorin200901uv02.getPastDiseases();
		// Author:jia_yanqing
		// Date:2013/4/19 14:27
		// [BUG]0015199 MODIFY BEGIN
		if (pastDiseaseList != null && pastDiseaseList.size() != 0)
		// [BUG]0015199 MODIFY END
		{
			for (PastDisease pastDisease : pastDiseaseList) {
				PastHistory pastHistory = new PastHistory();
				pastHistory.setDiseasesCode(pastDisease.getPastDiseaseCode());
				pastHistory.setDiseasesName(pastDisease.getPastDiseaseName());
				pastHistory.setExamOrderSn(examOrderSn);

				// $Author: tong_meng
				// $Date: 2013/8/30 15:00
				// [BUG]0036757 ADD BEGIN
				pastHistory.setCreateby(DataMigrationUtils.getDataMigration() + serviceId); // 设置创建人
				// [BUG]0036757 ADD END

				pastHistorys.add(pastHistory);
			}
		}
		return pastHistorys;
	}

	// [BUG]0010809 ADD END

	/**
	 * 获取检查医嘱表Entity List
	 */
	public List<ExaminationOrder> getExamMessage(
			POORIN200901UV02 poorin200901uv02) {
		// 检查医嘱表entity list
		List<ExaminationOrder> examinationOrderList = new ArrayList<ExaminationOrder>();

		List<ExaminationApplication> examinationApplications = poorin200901uv02
				.getExaminationApplications();

		// 遍历检查申请单
		for (ExaminationApplication examinationApplication : examinationApplications) {
			// 遍历检查医嘱
			for (ExaminationOrderDto examinationOrderDto : examinationApplication
					.getExaminationOrderDtos()) {
				ExaminationOrder examinationOrder = null;
				
				/*
				 * Author: yu_yzh
				 * Date: 2015/7/27 17:20
				 * 检查医嘱确认消息
				 * Add Begin
				 * */
				if(isConfirmOrChargeMessage(poorin200901uv02)){
					examinationOrder = examService.getOrder(
							poorin200901uv02.getPatientDomain(),
							patientLid, examinationOrderDto.getOrderLid(),
							poorin200901uv02.getOrgCode(),
							medicalVisit.getVisitSn());
					String orderStatus = null;
					String orderStatusName = null;
					
					if(isConfirmMessage(poorin200901uv02)){
						orderStatus = Constants.ORDER_STATUS_CONFIRMED;
						orderStatusName = DictionaryCache.getDictionary(
								Constants.DOMAIN_ORDER_STATUS).get(
								orderStatus);
						examinationOrder.setOrderStatus(orderStatus);
						examinationOrder.setOrderStatusName(orderStatusName);	
					} else if(isChargeMessage(poorin200901uv02)){
						orderStatus = Constants.ORDER_STATUS_PAYMENT;
						orderStatusName = DictionaryCache.getDictionary(
								Constants.DOMAIN_ORDER_STATUS).get(
								orderStatus);
						examinationOrder.setOrderStatus(orderStatus);
						examinationOrder.setOrderStatusName(orderStatusName);
						
						// 医嘱交费状态编码
						examinationOrder
								.setChargeStatusCode(Constants.CHARGE_STATUS_CHARGED);
						// 医嘱交费状态名称
						examinationOrder.setChargeStatusName(DictionaryCache
								.getDictionary(Constants.ORDER_CHARGE_STATUS)
								.get(Constants.CHARGE_STATUS_CHARGED));	
						
						String charge = poorin200901uv02.getExaminationApplications().get(0).getCharge();
						if(charge != null){
							examinationOrder.setCharge(StringUtils.strToBigDecimal(charge, null));
						}
						if(nurList == null){
							nurList = new ArrayList<NursingOperation>();
						}
						nurList.add(getNursingOperation(examinationOrder, poorin200901uv02));
					}
					examinationOrder.setUpdateTime(systemTime);
					examinationOrder.setUpdateby(serviceId);
					examinationOrderList.add(examinationOrder);
					continue;
				}
				/*
				 * 检查医嘱确认消息
				 * Add End
				 * */
				
				// 新增消息
				if (isNewMessage(poorin200901uv02)) {
					examinationOrder = new ExaminationOrder();

					// $Author :jin_peng
					// $Date : 2012/9/25 14:46$
					// [BUG]0009863 MODIFY BEGIN
					// 是否新增更新标识
					boolean isAdd = true;
					
					/*
					 * Author: yu_yzh
					 * Date: 2015/10/28 15:23
					 * 判断消息新增时数据库中是否存在对应的医嘱，不存在则新增，否则更新
					 * MODIFY BEGIN
					 * */
/*					// Author :jia_yanqing
					// Date : 2013/11/13 20:48
					// [BUG]0038805 MODIFY BEGIN
					// 门诊时通过域ID、患者本地ID、申请单号查找待更新的记录
					// Author :du_xiaolan
					// Date : 2014/10/29 
					// 增加流水号
					if (Constants.lstDomainOutPatient().contains(
							poorin200901uv02.getPatientDomain())
							&& Constants.lstVisitTypeOutPatient().contains(
									poorin200901uv02.getVisitTypeCode())) {
						examinationOrder = examService.getRequest(
								poorin200901uv02.getPatientDomain(),
								patientLid,
								examinationApplication.getRequestNo(),
								poorin200901uv02.getOrgCode(),
								medicalVisit.getVisitSn());
					}
					// 住院时通过域ID、患者本地ID、医嘱查找待更新的记录
					else if (Constants.lstDomainInPatient().contains(
							poorin200901uv02.getPatientDomain())
							&& Constants.lstVisitTypeInPatient().contains(
									poorin200901uv02.getVisitTypeCode())) {
						examinationOrder = examService.getOrder(
								poorin200901uv02.getPatientDomain(),
								patientLid, examinationOrderDto.getOrderLid(),
								poorin200901uv02.getOrgCode(),
								medicalVisit.getVisitSn());
					}*/

					examinationOrder = examService.getOrder(
							poorin200901uv02.getPatientDomain(),
							patientLid, examinationOrderDto.getOrderLid(),
							poorin200901uv02.getOrgCode(),
							medicalVisit.getVisitSn());
					/*
					 * 判断消息新增时数据库中是否存在对应的医嘱，不存在则新增，否则更新
					 * MODIFY END
					 * */
					
					// 如果检查申请存在则给存在的检查申请赋值
					if (examinationOrder != null) {
						// 设置记录为更新状态
						isAdd = false;
					}
					// [BUG]0038805 MODIFY END

					// 检查申请为新增状态时进行处理
					if (isAdd) {
						// Author :jia_yanqing
						// Date : 2013/11/13 20:48
						// [BUG]0038805 ADD BEGIN
						examinationOrder = new ExaminationOrder();
						// [BUG]0038805 ADD BEGIN
						// 本地医嘱号
						examinationOrder.setOrderLid(examinationOrderDto
								.getOrderLid());
						// 域ID
						examinationOrder.setPatientDomain(poorin200901uv02
								.getPatientDomain());
						// 患者本地ID
						examinationOrder.setPatientLid(patientLid);
						// 就诊内部序列号
						examinationOrder.setVisitSn(medicalVisit.getVisitSn());
						// 患者内部序列号
						examinationOrder.setPatientSn(medicalVisit
								.getPatientSn());
						// 创建时间
						examinationOrder.setCreateTime(systemTime);
						// 更新回数
						examinationOrder
								.setUpdateCount(Constants.INSERT_UPDATE_COUNT);
						// 删除标志
						examinationOrder
								.setDeleteFlag(Constants.NO_DELETE_FLAG);

						// $Author :chang_xuewen
						// $Date : 2013/12/03 11:00$
						// [BUG]0040271 ADD BEGIN
						examinationOrder.setOrgCode(poorin200901uv02
								.getOrgCode());
						examinationOrder.setOrgName(poorin200901uv02
								.getOrgName());
						// [BUG]0040271 ADD END

						// $Author: tong_meng
						// $Date: 2013/8/30 15:00
						// [BUG]0036757 ADD BEGIN
						examinationOrder.setCreateby(DataMigrationUtils.getDataMigration() + serviceId); // 设置创建人
						// [BUG]0036757 ADD END

						// Author :jin_peng
						// Date : 2012/11/14 14:56
						// [BUG]0011478 MODIFY BEGIN
						String orderStatus = null;
						String orderStatusName = null;

/*						// 门诊医嘱状态为已下达
						if (Constants.lstDomainOutPatient().contains(
								poorin200901uv02.getPatientDomain())
								&& Constants.lstVisitTypeOutPatient().contains(
										poorin200901uv02.getVisitTypeCode())) {
							orderStatus = Constants.ORDER_STATUS_ISSUE;

							orderStatusName = DictionaryCache.getDictionary(
									Constants.DOMAIN_ORDER_STATUS).get(
									Constants.ORDER_STATUS_ISSUE);
						}
						// 住院医嘱状态为已确认
						else if (Constants.lstDomainInPatient().contains(
								poorin200901uv02.getPatientDomain())
								&& Constants.lstVisitTypeInPatient().contains(
										poorin200901uv02.getVisitTypeCode())){
							orderStatus = Constants.ORDER_STATUS_VALIDATED;

							orderStatusName = DictionaryCache.getDictionary(
									Constants.DOMAIN_ORDER_STATUS).get(
									Constants.ORDER_STATUS_VALIDATED);
						}*/
						// 港大住院医嘱开立时执行状态和门诊一样，都为已下达，接入医嘱确认消息后才更改为医嘱已确认。
						orderStatus = Constants.ORDER_STATUS_ISSUE;

						orderStatusName = DictionaryCache.getDictionary(
								Constants.DOMAIN_ORDER_STATUS).get(
								Constants.ORDER_STATUS_ISSUE);

						// 医嘱状态
						examinationOrder.setOrderStatus(orderStatus);

						// $Author :jia_yanqing
						// $Date : 2012/9/5 14:33$
						// [BUG]0008782 MODIFY BEGIN
						// $Author :tong_meng
						// $Date : 2012/7/2 10:33$
						// [BUG]0007418 ADD BEGIN
						// 医嘱状态名称
						examinationOrder.setOrderStatusName(orderStatusName);
						// [BUG]0007418 ADD END
						// [BUG]0008782 MODIFY END
						// [BUG]0011478 MODIFY END

						// $Author:wei_peng
						// $Date:2012/11/13 17:33
						// [BUG]0011421 ADD BEGIN
						// 医嘱交费状态编码
						examinationOrder
								.setChargeStatusCode(Constants.CHARGE_STATUS_NOTCHARGE);
						// 医嘱交费状态名称
						examinationOrder.setChargeStatusName(DictionaryCache
								.getDictionary(Constants.ORDER_CHARGE_STATUS)
								.get(Constants.CHARGE_STATUS_NOTCHARGE));
						// [BUG]0011421 ADD END
						}
						// [BUG]0009863 MODIFY END
						// 医嘱执行分类
						examinationOrder.setOrderExecId(poorin200901uv02.getMessage().getOrderDispatchTypeCode());
				}
				// 更新消息
				else {
					// 获取待更新的医嘱记录
					examinationOrder = getExaminationOrder(examinationOrderDto);

					// $Author: tong_meng
					// $Date: 2013/8/30 15:00
					// [BUG]0036757 ADD BEGIN
					examinationOrder.setUpdateby(serviceId); // 设置创建人
					// [BUG]0036757 ADD END

					examinationOrder.setOrgCode(poorin200901uv02.getOrgCode());
					examinationOrder.setOrgName(poorin200901uv02.getOrgName());

				}
				// 检验医嘱信息添加
				// 检查方法代码
				examinationOrder.setExamMethodCode(examinationOrderDto
						.getExamMethodCode());
				// 检查方法名称
				examinationOrder.setExamMethodName(examinationOrderDto
						.getExamMethodName());
				/*
				 * $Author: yu_yzh
				 * $Date: 2015/7/29 10:37
				 * 添加检查类别信息
				 * ADD BEGIN
				 * */
				
				/*
				 * Author: yu_yzh
				 * Date: 2015/10/23 09:31
				 * 不为空时才设置值，防止更新后丢失内容
				 * */ 
				if(examinationOrderDto.getItemClass() != null){
					//检查类别代码
					examinationOrder.setItemClass(examinationOrderDto.getItemClass());
					//检查类别名称
					examinationOrder.setItemClassName(examinationOrderDto.getItemClassName());
				}

				//ADD END
				/*
				 * $Author: yu_yzh
				 * $Date: 2015/7/31 10:11
				 * 添加检查类型信息
				 * ADD BEGIN
				 * */
				if(examinationOrderDto.getOrderExecId() != null){
					//医嘱执行小分类，同检查类别代码
					examinationOrder.setOrderExecId(examinationOrderDto.getOrderExecId());
				}
				//ADD END
				if(examinationOrderDto.getItemCode() != null){
					// 检查项目代码
					examinationOrder.setItemCode(examinationOrderDto.getItemCode());
					/*
					 * $Author: yu_yzh
					 * $Date: 2015/8/18 17:17
					 * 根据字典取值  MODIFY BEGIN
					 * */
					// 检查项目名称
					String itemName = DictionaryUtils.getNameFromDictionary(Constants.DOMAIN_EXAM_ITEM, 
							examinationOrderDto.getItemCode(), examinationOrderDto.getItemName());
					examinationOrder.setItemName(itemName);
					// 根据字典取值 MODIFY END
				}

				
				// 检查部位代码
				examinationOrder.setRegionCode(examinationOrderDto
						.getRegionCode());
				// 检查部位名称
				examinationOrder.setRegionName(examinationOrderDto
						.getRegionName());
				// 长期或临时
				examinationOrder.setTemporaryFlag(examinationOrderDto
						.getTemporaryFlag(null));
				//医嘱描述
				examinationOrder.setOrderDescribe(examinationOrderDto.getOrderDescribe());

				// $Author :chang_xuewen
				// $Date : 2013/8/12 14:00$
				// [BUG]0035982 ADD BEGIN
				// 医嘱执行频率编码
				examinationOrder.setExecutionFrequency(examinationOrderDto
						.getExecutionFrequency());
				// 医嘱执行频率名称
				examinationOrder.setExecFreqName(examinationOrderDto
						.getExecFreqName());
				// 医嘱开始时间
				examinationOrder.setOrderStartTime(DateUtils
						.stringToDate(examinationOrderDto.getOrderStartTime()));
				// 医嘱结束时间
				examinationOrder.setOrderEndTime(DateUtils
						.stringToDate(examinationOrderDto.getOrderEndTime()));
				// 是否皮试
				examinationOrder.setSkinTestFlag(StringUtils
						.getConversionValue(examinationOrderDto
								.getSkinTestFlag()));
				// 是否加急
				examinationOrder
						.setUrgentFlag(StringUtils
								.getConversionValue(examinationOrderDto
										.getUrgentFlag()));
				// 是否药观
				examinationOrder.setMedViewFlag(StringUtils
						.getConversionValue(examinationOrderDto
								.getMedViewFlag()));
				// [BUG]0035982 ADD END
				// 检查申请单信息添加
				// 申请单编号
				if(examinationApplication.getRequestNo() != null){			
					examinationOrder.setRequestNo(examinationApplication.getRequestNo());
				}
				// 申请日期
				if(examinationApplication.getRequestDate() != null){
					examinationOrder.setRequestDate(DateUtils
							.stringToDate(examinationApplication.getRequestDate()));
				}

				// 医嘱类型代码
				examinationOrder.setOrderType(examinationApplication
						.getOrderType());
				// 医嘱类型名称
				examinationOrder.setOrderTypeName(examinationApplication
						.getOrderTypeName());
				
				if(examinationApplication.getExecutiveDept() != null){
					// 执行科室ID
					examinationOrder.setExecutiveDept(examinationApplication
							.getExecutiveDept());
					/*
					 * $Author: yu_yzh
					 * $Date: 2015/8/12 17:45
					 * 根据科室字典设置名称
					 * MODIFY BEGIN
					 * */
					// $Author :tong_meng
					// $Date : 2012/7/2 10:33$
					// [BUG]0007418 ADD BEGIN
					// 执行科室名称
//					examinationOrder.setExecDeptName(examinationApplication
//							.getExecutiveDeptName());
					// [BUG]0007418 ADD END
					String execDeptName = DictionaryUtils.getNameFromDictionary(Constants.DOMAIN_DEPARTMENT, 
							examinationApplication.getExecutiveDept(), examinationApplication.getExecutiveDeptName());
					examinationOrder.setExecDeptName(execDeptName);			
					// MODIFY END
				}
				
				
				// $Author :tong_meng
				// $Date : 2013/8/12 11:00$
				// [BUG]0036091 ADD BEGIN
				// 标本号
				if(examinationApplication.getSampleNo() != null){
					examinationOrder.setSampleNo(examinationApplication
							.getSampleNo());
				}

				// [BUG]0036091 ADD END

				
				// 标本类型
				if(examinationApplication.getSampleType() != null){
					examinationOrder.setSampleType(examinationApplication.getSampleType());
					/*
					 * $Author: yu_yzh
					 * $Date: 2015/8/12 16:20
					 * 判断字典是否为空
					 * MODIFY BEGIN
					 * */
					// $Author :tong_meng
					// $Date : 2012/7/2 10:33$
					// [BUG]0007418 ADD BEGIN
					// 标本类型名称
					String name = DictionaryUtils.getNameFromDictionary(Constants.DOMAIN_SAMPLE_TYPE, 
							examinationApplication.getSampleType());
					examinationOrder.setSampleTypeName(name);
					// [BUG]0007418 ADD END
					// MODIFY END
				}

				// 标本要求
				examinationOrder.setSampleRequirement(examinationApplication
						.getSampleRequirement());
				// 注意事项
				examinationOrder.setExamNotice(examinationApplication
						.getExamNotice());
				// 检查申请消息信息添加
				// 床号
				examinationOrder.setBedNo(poorin200901uv02.getBedNo());
				// 病区ID
				if(poorin200901uv02.getWardsId() != null){
					examinationOrder.setWardsId(poorin200901uv02.getWardsId());
					// $Author :tong_meng
					// $Date : 2012/7/1 17:33$
					// [BUG]0007418 ADD BEGIN
					// 病区名称
//					examinationOrder.setWardsName(poorin200901uv02.getWardsName());
					//优先根据字典取值
					String wName = DictionaryUtils.getNameFromDictionary(Constants.DOMAIN_WARD, 
							poorin200901uv02.getWardsId(), poorin200901uv02.getWardsName());
					examinationOrder.setWardsName(wName);
					// [BUG]0007418 ADD END
				}
				if(poorin200901uv02.getOrderDept() != null){
					// 开嘱科室ID
					examinationOrder.setOrderDept(poorin200901uv02.getOrderDept());
					/*
					 * $Author: yu_yzh
					 * $Date: 2015/8/12 17:55
					 * 根据字典查科室名称
					 * MODIFY BEGIN
					 * */				
//					Map<String, String> deptDic = DictionaryCache.getDictionary(Constants.DOMAIN_DEPARTMENT);
					String orderDeptName = DictionaryUtils.getNameFromDictionary(Constants.DOMAIN_DEPARTMENT, 
							poorin200901uv02.getOrderDept(), poorin200901uv02.getOrderDeptName());
					examinationOrder.setOrderDeptName(orderDeptName);
					// $Author :tong_meng
					// $Date : 2012/7/1 17:33$
					// [BUG]0007418 ADD BEGIN
					// 开嘱科室名称
//					examinationOrder.setOrderDeptName(poorin200901uv02
//							.getOrderDeptName());
					// [BUG]0007418 ADD END
					// MODIFY END
				}
				
				
				// 开嘱人ID
				examinationOrder.setOrderPerson(poorin200901uv02
						.getOrderPerson());
				/*
				 * $Author: yu_yzh
				 * $Date: 2015/8/25 11:06
				 * 根据字典 MODIFY BEGIN
				 * */
				// 开嘱人姓名
//				examinationOrder.setOrderPersonName(poorin200901uv02
//						.getOrderPersonName());
				examinationOrder.setOrderPersonName(DictionaryUtils.getNameFromDictionary(Constants.DOMAIN_STAFF, 
						poorin200901uv02.getOrderPerson(), poorin200901uv02.getOrderPersonName()));
				// 根据字典 MODIFY END
				// 开嘱时间
				if(poorin200901uv02.getOrderTime() != null){
					examinationOrder.setOrderTime(DateUtils
							.stringToDate(poorin200901uv02.getOrderTime()));
				}
				if(poorin200901uv02.getConfirmPerson() != null){
					// 医嘱确认人ID
					examinationOrder.setConfirmPerson(poorin200901uv02
							.getConfirmPerson());
					// 医嘱确认人姓名
					examinationOrder.setConfirmPersonName(poorin200901uv02
							.getConfirmPersonName());
				}

				// 确认时间
				examinationOrder.setConfirmTime(DateUtils
						.stringToDate(poorin200901uv02.getConfirmTime()));

				// $Author:wei_peng
				// $Date:2012/10/29 17:51
				// [BUG]0010809 DELETE BEGIN
				// $Author:jia_yanqing$
				// $Date:2012/8/9 18:57$
				// [BUG]0008690 ADD BEGIN
				// 既往史疾病编码
				// examinationOrder.setPastDiseaseCode(poorin200901uv02.getPastDiseaseCode());
				// 既往史疾病名称
				// examinationOrder.setPastDiseaseName(poorin200901uv02.getPastDiseaseName());
				// [BUG]0008690 ADD END
				// [BUG]0010809 DELETE END

				// 更新时间
				examinationOrder.setUpdateTime(systemTime);
				// $Author:wei_peng$
				// $Date:2012/5/18 16:57$
				// [BUG]0005751 ADD BEGIN
				// 诊断
				examinationOrder.setDiagnosis(poorin200901uv02.getDiagnosis());
				// [BUG]0005751 ADD END

				// $Author:wei_peng
				// $Date:2013/7/31 16:38
				// [BUG]0035530 ADD BEGIN
				// 申请单详细信息
				examinationOrder.setRequestDetails(examinationApplication
						.getRequestDetails());
				// [BUG]0035530 ADD END
				// TODO 检查类别
				// TODO 紧急程度区分
				// TODO 申请原因
				// TODO 检查条件
				/*
				 * $Author: yu_yzh
				 * $Date: 2015/7/29 : 11:20
				 * 检查费用 ADD BEGIN
				 * */
				if(examinationApplication.getCharge() != null){
					examinationOrder.setCharge(StringUtils.strToBigDecimal(examinationApplication.getCharge(), null));
				}
				// ADD END
				
				examinationOrderList.add(examinationOrder);
			}
		}

		return examinationOrderList;
	}

	/**
	 * 获取待更新的检查医嘱
	 * 
	 * @return 对应数据库中的一条检查医嘱
	 */
	public ExaminationOrder getExaminationOrder(
			ExaminationOrderDto examinationOrderDto) {
		for (ExaminationOrder examinationOrder : examinationOrdersNeedUpdate) {
			// 根据医嘱号判断是否为待更新的医嘱
			if (examinationOrder.getOrderLid().equals(
					examinationOrderDto.getOrderLid())) {
				return examinationOrder;
			}
		}
		return null;
	}

	/**
	 * 判断消息是否为新增消息
	 * 
	 * @param poorin200901uv02
	 *            检查申请消息
	 * @return 是返回true，否则返回false
	 */
	public boolean isNewMessage(POORIN200901UV02 poorin200901uv02) {
		return Constants.NEW_MESSAGE_FLAG.equals(poorin200901uv02.getTriggerEventCode())
				|| Constants.V2_NEW_MESSAGE_FLAG.equals(poorin200901uv02.getTriggerEventCode());
	}

	/*
	 * Author: yu_yzh
	 * Date: 2015/7/27 15:00
	 * Add Begin
	 * */
	/**
	 * 判断是否为检查医嘱确认或收费消息
	 * */
	public boolean isConfirmOrChargeMessage(POORIN200901UV02 poorin200901uv02){
//		return poorin200901uv0.getMessage().getContent().contains("OK");
		return Constants.V2_CONFIRM_OR_CHARGE_MESSAGE_FLAG.equals(poorin200901uv02.getTriggerEventCode());
	}
	/*
	 * Add End
	 * */
	
	/*
	 * $Author: yu_yzh
	 * $Date: 2015/8/7 9:10
	 * 添加判断，是否为确认，是否为收费
	 * */
	/**
	 * 是否为确认
	 * */
	public boolean isConfirmMessage(POORIN200901UV02 poorin200901uv02){
		if(!isConfirmOrChargeMessage(poorin200901uv02)) return false;
		return "NIS".equals(poorin200901uv02.getSender());
	}
	/**
	 * 是否为收费
	 * */
	public boolean isChargeMessage(POORIN200901UV02 poorin200901uv02){
		if(!isConfirmOrChargeMessage(poorin200901uv02)) return false;
		return "HIS".equals(poorin200901uv02.getSender());
	}
	
	//　ADD END
	
	/**
	 * 判断消息是否为更新消息
	 * 
	 * @param poorin200901uv02
	 * @return 是返回true，否则返回false
	 */
	public boolean isUpdateMessage(POORIN200901UV02 poorin200901uv02) {
		return Constants.UPDATE_MESSAGE_FLAG.equals(poorin200901uv02.getTriggerEventCode())
				|| Constants.V2_UPDATE_MESSAGE_FLAG.equals(poorin200901uv02.getTriggerEventCode())
				|| Constants.V2_CONFIRM_OR_CHARGE_MESSAGE_FLAG.equals(poorin200901uv02.getTriggerEventCode());
	}

	// $Author :jin_peng
	// $Date : 2012/9/16 14:48$
	// [BUG]0009718 ADD BEGIN
	/**
	 * 判断消息是否为删除消息
	 * 
	 * @param poorin200901uv02
	 *            检查申请消息对象信息
	 * @return 是返回true，否则返回false
	 */
	public boolean isDeleteMessage(POORIN200901UV02 poorin200901uv02) {
		return poorin200901uv02.getTriggerEventCode().equals(
				Constants.DELETE_MESSAGE_FLAG)
				&& (Constants.lstDomainOutPatient().contains(
						poorin200901uv02.getPatientDomain())
						&& Constants.lstVisitTypeOutPatient().contains(
								poorin200901uv02.getVisitTypeCode()));
	}
	// [BUG]0009718 ADD END
	
	/*
	 * Author: yu_yzh
	 * Date: 2015/7/27 15:00
	 * Add Begin
	 * */
	
	/**
	 * 通过检查申请单编号获取检查医嘱，获得患者就诊信息
	 * */
	private MedicalVisit getMedicalVisitByExamRequestNo(POORIN200901UV02 poorin200901uv02){
		if (poorin200901uv02.getExaminationApplications() == null || poorin200901uv02.getExaminationApplications().isEmpty()) return null;
		String requestNo = poorin200901uv02.getExaminationApplications().get(0).getRequestNo();
		if(StringUtils.isEmpty(requestNo)) return null;
		MedicalVisit mv = null;
		mv = commonService.mediVisitByRequestNo(ExaminationOrder.class, patientLid, requestNo);
		if(mv != null){
			poorin200901uv02.setPatientDomain(mv.getPatientDomain());
		}
		return mv;
	}
	/*
	 * Add End
	 * */
	
	private boolean isOMPO09Message(POORIN200901UV02 poorin200901uv02){
		return Constants.V2.equals(poorin200901uv02.getMessage().getMsgMode())
		&& poorin200901uv02.getMessage().getContent().contains("OMP^O09");
	}
	
	private NursingOperation getNursingOperation(ExaminationOrder examinationOrder, POORIN200901UV02 poorin200901uv02){
		NursingOperation no = new NursingOperation();
		// 护理操作内部序列号
        no.setOperationSn(commonService.getSequence(SEQ_NURSING_OPERATION));

        // 就诊内部序列号
        no.setVisitSn(examinationOrder.getVisitSn());

        // 医嘱内部序列号
        no.setOrderSn(examinationOrder.getOrderSn());

        // 患者内部序列号
        no.setPatientSn(examinationOrder.getPatientSn());

        // 域代码
        no.setPatientDomain(examinationOrder.getPatientDomain());

        // 患者本地ID
        no.setPatientLid(patientLid);

        // 医嘱类型
        no.setOrderType(examinationOrder.getOrderType());

        // $Author :tong_meng
        // $Date : 2012/7/1 15:33$
        // [BUG]0007418 ADD BEGIN
        // 医嘱类型名稱
        no.setOrderTypeName(examinationOrder.getOrderTypeName());
        // [BUG]0007418 ADD END

        // Author :jia_yanqing
        // Date : 2013/6/28 16:33
        // [BUG]0033848 ADD BEGIN
        // 医嘱状态编码
        no.setOrderStatusCode(examinationOrder.getOrderStatus());
        // 医嘱状态名称
        no.setOrderStatusName(examinationOrder.getOrderStatusName());
        
        // 执行科室编码
        no.setExecuteDeptCode(poorin200901uv02.getOrderDept());
        // 执行科室名称        
        no.setExecuteDeptName(DictionaryUtils.getNameFromDictionary(
        		Constants.DOMAIN_DEPARTMENT, poorin200901uv02.getOrderDept(), poorin200901uv02.getOrderDeptName()));
        // [BUG]0033848 ADD END

        // 操作人ID
        no.setOperatorId(poorin200901uv02.getConfirmPerson());

        // 操作人姓名
        no.setOperatorName(DictionaryUtils.getNameFromDictionary(Constants.DOMAIN_STAFF, 
        		poorin200901uv02.getConfirmPerson(), poorin200901uv02.getConfirmPersonName()));
        

        // 操作时间
        no.setOperationTime(DateUtils.stringToDate(poorin200901uv02.getConfirmTime()));

        // 创建时间
        no.setCreateTime(systemTime);

        // 更新时间
        no.setUpdateTime(systemTime);

        // 更新回数
        no.setUpdateCount(Constants.INSERT_UPDATE_COUNT);

        // 删除标志
        no.setDeleteFlag(Constants.NO_DELETE_FLAG);
        // $Author :chang_xuewen
        // $Date : 2013/08/31 16:00$
        // [BUG]0036757 MODIFY BEGIN
        // 增加人
        no.setCreateby(DataMigrationUtils.getDataMigration() + poorin200901uv02.getMessage().getServiceId()
            + '-' + poorin200901uv02.getSender());
        // [BUG]0036757 MODIFY END
        no.setOrgCode(poorin200901uv02.getOrgCode());
        no.setOrgName(poorin200901uv02.getOrgName());
		
		return no;
	}
	
}
