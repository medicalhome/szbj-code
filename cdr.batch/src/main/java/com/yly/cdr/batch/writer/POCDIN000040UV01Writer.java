/**
 * Copryright
 */
package com.yly.cdr.batch.writer;

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

import com.yly.cdr.batch.writer.pocdin000040uv01.POCDIN000040UV01Part;
import com.yly.cdr.batch.writer.pocdin000040uv01.POCDIN000040UV01Validation;
import com.yly.cdr.core.Constants;
import com.yly.cdr.entity.AntibacterialDrug;
import com.yly.cdr.entity.ClinicalDocument;
import com.yly.cdr.entity.LabOrder;
import com.yly.cdr.entity.LabOrderLabResult;
import com.yly.cdr.entity.LabResult;
import com.yly.cdr.entity.LabResultCompositeItem;
import com.yly.cdr.entity.LabResultItem;
import com.yly.cdr.entity.MedicalImaging;
import com.yly.cdr.entity.MedicalVisit;
import com.yly.cdr.entity.WithdrawRecord;
import com.yly.cdr.hl7.dto.ImmunityResult;
import com.yly.cdr.hl7.dto.LabReport;
import com.yly.cdr.hl7.dto.LabReportResult;
import com.yly.cdr.hl7.dto.PreventAllergyDrug;
import com.yly.cdr.hl7.dto.SmearMessage;
import com.yly.cdr.hl7.dto.pocdin000040uv01.POCDIN000040UV01;
import com.yly.cdr.service.ClinicalDocumentService;
import com.yly.cdr.service.CommonService;
import com.yly.cdr.service.LabService;
import com.yly.cdr.util.DateUtils;
import com.yly.cdr.util.StringUtils;

/**
 * 检验报告数据接入writer
 * @author jin_peng
 */
@Component(value = "pocdin000040uv01Writer")
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class POCDIN000040UV01Writer implements BusinessWriter<POCDIN000040UV01>
{

    private static final Logger logger = LoggerFactory.getLogger(POCDIN000040UV01Writer.class);

    private static final Logger loggerBS319 = LoggerFactory.getLogger("BS319");

    private static final Logger loggerBS354 = LoggerFactory.getLogger("BS354");

    // 检验报告序列名称
    private static final String REPORT_SEQUENCE = "SEQ_REPORT";

    // 检验项目序列名称
    private static final String LAB_ITEM_SEQUENCE = "SEQ_LAB_ITEM";

    // 检验影像序列名称
    private static final String MEDICAL_IMAGING_SEQUENCE = "SEQ_MEDICAL_IMAGING";

    // 病历文档序列号sequences
    private static final String DOCUMENT_SEQUENCE = "SEQ_DOCUMENT";

    // 抗菌药物序列号
    private static final String ANTIBACTERIAL_DRUG_SEQUENCE = "SEQ_ANTIBACTERIAL_DRUG";
    
    // 是否校验医嘱-是
 	private static final String ORDER_CHECK= "1";

    // 当前检验报告对应就诊内部序列号
    private BigDecimal visitSn;

    // 当前检验报告对应患者内部序列号
    private BigDecimal patientSn;

    // 当前检验报告对应报告内部序列号
    private BigDecimal reportSn;

    // 当前检验报告对应本地患者ID
    private String patientLid;

    // 检验报告全局变量
    private LabResult labResult;

    // 系统当前时间
    private Date systemTime;

    // 未删除标记
    private String noDeleteFlag;

    @Autowired
    private LabService labService;

    @Autowired
    private CommonService commonService;

    @Autowired
    private POCDIN000040UV01Validation pValidation;

    @Autowired
    private POCDIN000040UV01Part pPart;
    
    @Autowired
	private ClinicalDocumentService clinicalDocumentService;

    // 文档内部序列号
    private BigDecimal documentSn;

    // 待删除病历文档内部序列号
    private BigDecimal documentSnToDelete;

    // 源系统标识
    private String sourceSystemId;

    // $Author: tong_meng
    // $Date: 2013/8/30 15:00
    // [BUG]0036757 ADD BEGIN
    private String serviceId;

    // [BUG]0036757 ADD END

    // Author :jin_peng
    // Date : 2013/09/11 10:12$
    // [BUG]0037243 ADD BEGIN
    // 报告对应医嘱集合
    private List<Object> labOrdersList;

    // [BUG]0037243 ADD BEGIN

    private String labReportTypeCode = null;

    
    // 初始化参数
    public POCDIN000040UV01Writer()
    {
        systemTime = DateUtils.getSystemTime();
        noDeleteFlag = Constants.NO_DELETE_FLAG;
    }

    /**
     * 检验报告在新增或更新场景下非空值验证
     */
    @Override
    public boolean validate(POCDIN000040UV01 pocdin000040uv01)
    {
        if (StringUtils.isEmpty(pocdin000040uv01.getMessage().getOrgCode()))
        {
            pocdin000040uv01.getMessage().setOrgCode(Constants.HOSPITAL_CODE);
        }

        // 获取非空验证结果
        return pValidation.validate(pocdin000040uv01, logger, loggerBS319,
                loggerBS354);
    }

    /**
     * 有关联时进行业务检验
     */
    @Override
    public boolean checkDependency(POCDIN000040UV01 pocdin000040uv01,
            boolean logFlag)
    {
        labReportTypeCode = pocdin000040uv01.getReportTypeCode();
        MedicalVisit medicalVisit = null;
        Map<String, Object> map = null;
        String patientDomain = null;
        String visitTimes = null;
        String labReportLid = null;
        // $Author :jin_peng
        // $Date : 2012/07/26 14:00$
        // [BUG]0010366 MODIFY BEGIN
        // 报告类型编码
        String reportTypeCode = null;

        // $Author :tongmeng
        // $Date : 2012/5/29 14:30$
        // [BUG]0006657 MODIFY BEGIN
        // 获得患者本地ID
        patientLid = pocdin000040uv01.getPatientLid();
        // [BUG]0006119 MODIFY END

        // $Author :jin_peng
        // $Date : 2012/07/26 14:00$
        // [BUG]0008030 MODIFY BEGIN
        // 获取源系统Id
        sourceSystemId = pocdin000040uv01.getSourceSystemId();
        // [BUG]0008030 MODIFY END

        // 检验报告新增时验证相应的就诊信息是否存在
		if (Constants.VERSION_NUMBER_INSERT.equals(pocdin000040uv01
				.getVersionNumber())) {
			
			// 获得患者域ID
			patientDomain = pocdin000040uv01.getPatientDomain();

			// 获得就诊次数
			visitTimes = pocdin000040uv01.getVisitTimes();

			medicalVisit = commonService.mediVisit(patientDomain, patientLid,
					visitTimes, pocdin000040uv01.getOrgCode(),
					pocdin000040uv01.getVisitOrdNo(),
					pocdin000040uv01.getVisitType());

			if (medicalVisit != null) {
				visitSn = medicalVisit.getVisitSn();
				patientSn = medicalVisit.getPatientSn();
				logger.debug(
						"在新增检验报告时相应的就诊信息存在true: {}",
						"params: patientLid = " + patientLid
								+ "; patientDomain = " + patientDomain
								+ "; visitTimes = " + visitTimes
								+ "; orgCode = "
								+ pocdin000040uv01.getOrgCode());
			} else {
				logger.error("检验报告关联就诊不存在： {} 检验报告版本状态: {}", "medicalVisit = "
						+ medicalVisit + "，params: patientLid = " + patientLid
						+ "，patientDomain = " + patientDomain
						+ "，visitTimes = " + visitTimes + "; orgCode = "
						+ pocdin000040uv01.getOrgCode(),
						Constants.VERSION_NUMBER_INSERT);
				if (logFlag) {
					if (Constants.SOURCE_LAB.equals(pocdin000040uv01
							.getSourceSystemId())) {
						loggerBS319.error(
								"Message:{},checkDependency:{}",
								pocdin000040uv01.toString(),
								"检验报告关联就诊不存在：medicalVisit = " + medicalVisit
										+ "，params: patientLid = " + patientLid
										+ "，patientDomain = " + patientDomain
										+ "，visitTimes = " + visitTimes
										+ "; orgCode = "
										+ pocdin000040uv01.getOrgCode()
										+ "。检验报告版本状态："
										+ Constants.VERSION_NUMBER_INSERT);
					} else {
						loggerBS354.error(
								"Message:{},checkDependency:{}",
								pocdin000040uv01.toString(),
								"检验报告关联就诊不存在：medicalVisit = " + medicalVisit
										+ "，params: patientLid = " + patientLid
										+ "，patientDomain = " + patientDomain
										+ "，visitTimes = " + visitTimes
										+ "; orgCode = "
										+ pocdin000040uv01.getOrgCode()
										+ "。检验报告版本状态："
										+ Constants.VERSION_NUMBER_INSERT);
					}
				}
				return false;
			}

			// if(Constants.IF_ORDER_CHECK.equals(ORDER_CHECK)){

			// Author :jin_peng
			// Date : 2013/09/11 10:12$
			// [BUG]0037243 ADD BEGIN
			// 查询相应医嘱集合
			labOrdersList = pPart.checkLabOrder(pocdin000040uv01.getSample()
					.get(0).getSampleNo(), patientLid, patientDomain,
					commonService, pocdin000040uv01.getOrgCode(), visitSn);
			boolean orderExistsFlag = false;
			if (labOrdersList == null || labOrdersList.isEmpty()) {
				// 根据医嘱号查询相应医嘱信息
				logger.debug("根据标本号查询医嘱不存在, 标本号:{}", pocdin000040uv01
						.getSample().get(0).getSampleNo());

				labOrdersList = pPart.checkLabOrderByOrderNo(
						pocdin000040uv01.getOrderNo(), patientLid,
						patientDomain, commonService,
						pocdin000040uv01.getOrgCode(), visitSn);
				
				if (labOrdersList == null || labOrdersList.isEmpty()) {
					logger.error("检验报告新增时关联医嘱不存在：{}", "sample = "
							+ pocdin000040uv01.getSample().get(0).getSampleNo()
							+ "; orderNo = " + pocdin000040uv01.getOrderNo()
							+ "; patientLid = " + patientLid
							+ "; patientDomain = " + patientDomain
							+ "; orgCode = " + pocdin000040uv01.getOrgCode()
							+ "; visitSn = " + visitSn);

					if (logFlag) {
						if (Constants.SOURCE_LAB.equals(pocdin000040uv01
								.getSourceSystemId())) {
							loggerBS319.error(
									"Message:{},checkDependency:{}",
									pocdin000040uv01.toString(),
									"检验报告新增时关联医嘱不存在：sample = "
											+ pocdin000040uv01.getSample()
													.get(0).getSampleNo()
											+ "; patientLid = " + patientLid
											+ "; patientDomain = "
											+ patientDomain + "; orgCode = "
											+ pocdin000040uv01.getOrgCode()
											+ "; visitSn" + visitSn);
						} else {
							loggerBS354.error(
									"Message:{},checkDependency:{}",
									pocdin000040uv01.toString(),
									"检验报告新增时关联医嘱不存在：sample = "
											+ pocdin000040uv01.getSample()
													.get(0).getSampleNo()
											+ "; patientLid = " + patientLid
											+ "; patientDomain = "
											+ patientDomain + "; orgCode = "
											+ pocdin000040uv01.getOrgCode()
											+ "; visitSn" + visitSn);
						}
					}

					orderExistsFlag = false;
				}
				else {
					orderExistsFlag = true;
				}
				// [BUG]0037243 ADD END
			} else {
				//根据标本号检索医嘱存在
				orderExistsFlag = true;
			}
			// 医嘱不存在
			if(!orderExistsFlag){
				return false;
			} else {// 医嘱存在，校验数据库中是否已存在报告。
				labReportLid = pocdin000040uv01.getReportNo();
	            // 获取报告类型编码
	            reportTypeCode = pocdin000040uv01.getReportTypeCode();
	            map = new HashMap<String, Object>();
	            map.put("labReportLid", labReportLid);
	            // 根据系统源id确定检验报告
	            map.put("sourceSystemId", sourceSystemId);
	            // 针对微生物检验报告需要增加报告类型过滤
	            if (Constants.SOURCE_MICROBE.equals(sourceSystemId))
	            {
	                map.put("reportTypeCode", reportTypeCode);
	            }
	            map.put("orgCode", pocdin000040uv01.getOrgCode());
	            map.put("deleteFlag", noDeleteFlag);
	            labResult = labService.selectLabResultByCondition(map);
	            if(labResult != null){
//	            	newReportAlreadyExist = true;
	            	pocdin000040uv01.setVersionNumber(Constants.VERSION_NUMBER_UPDATE);
	            	String log = "新增消息时，报告在数据库中已存在，做更新操作。 labReportLid: " + labReportLid
	            			+ ", sourceSystemId:" + sourceSystemId 
	            			+ (Constants.SOURCE_MICROBE.equals(sourceSystemId) ? ", reportTypeCode: " + reportTypeCode : "");
	            	logger.debug(log);
	            	if(Constants.SOURCE_MICROBE.equals(sourceSystemId)){
	            		loggerBS319.debug(log);
	            	} else {
	            		loggerBS354.debug(log);
	            	}
	            }
	            return true;
			}
		}

        // 检验报告更新时验证相应的报告记录是否存在
        if (Constants.VERSION_NUMBER_UPDATE.equals(pocdin000040uv01.getVersionNumber())
            || Constants.VERSION_NUMBER_WITHDRAW.equals(pocdin000040uv01.getVersionNumber())
            || Constants.VERSION_NUMBER_PART_APPROVED.equals(pocdin000040uv01.getVersionNumber()))
        {
            // Author :jia_yanqing
            // Date : 2013/4/10 17:36$
            // [BUG]0014971 MODIFY BEGIN
            boolean flag = true;
            // 获取本地检验报告ID
            labReportLid = pocdin000040uv01.getReportNo();

            // 获取报告类型编码
            reportTypeCode = pocdin000040uv01.getReportTypeCode();

            map = new HashMap<String, Object>();
            map.put("labReportLid", labReportLid);

            // 根据系统源id确定检验报告
            map.put("sourceSystemId", sourceSystemId);
            // [BUG]0008030 MODIFY END

            // 针对微生物检验报告需要增加报告类型过滤
            if (Constants.SOURCE_MICROBE.equals(sourceSystemId))
            {
                map.put("reportTypeCode", reportTypeCode);
            }

            map.put("orgCode", pocdin000040uv01.getOrgCode());

            map.put("deleteFlag", noDeleteFlag);
            labResult = labService.selectLabResultByCondition(map);

            if (labResult != null)
            {
                documentSnToDelete = labResult.getDocumentSn();
                logger.debug("在更新或作废时对应的检验报告存在true: {}",
                        "params: labReportLid = " + labReportLid
                            + "; sourceSystemId = " + sourceSystemId
                            + "; reportTypeCode = " + reportTypeCode
                            + "; orgCode = " + pocdin000040uv01.getOrgCode());
                // $Author :jin_peng
                // $Date : 2012/10/17 15:36$
                // [BUG]0010520 ADD BEGIN
                // 检验报告做更新操作时数据库中相应报告中召回状态为已召回时才可执行更新操作
                // $Author :jin_peng
                // $Date : 2013/09/18 15:53$
                // [BUG]0037432 MODIFY BEGIN
                if (!Constants.SOURCE_LAB.equals(pocdin000040uv01.getSourceSystemId())
                    || !Constants.VERSION_NUMBER_PART_APPROVED.equals(pocdin000040uv01.getVersionNumber()))
                {
                	// 检查报告做更新操作时数据库中相应报告中召回状态为已召回时才可执行更新操作 控制开关(0:开启 1:关闭)
					if(Constants.COMM_INTERFACE.equals(Constants.WITH_DRAW_USE_FLAG)){
	                    if (!Constants.WITH_DRAW_FLAG.equals(labResult.getWithdrawFlag()))
	                    {
	                        logger.error(
	                                "（除微生物检验报告的临时报告外，需要判断）更新检验报告时相关数据的召回状态并非为已召回状态，暂不做更新操作: {}",
	                                "params: labReportLid = " + labReportLid
	                                    + "; sourceSystemId = " + sourceSystemId
	                                    + "; reportTypeCode = " + reportTypeCode
	                                    + "; orgCode = "
	                                    + pocdin000040uv01.getOrgCode());
	                        if (logFlag)
	                        {
	                            if (Constants.SOURCE_LAB.equals(pocdin000040uv01.getSourceSystemId()))
	                            {
	                                loggerBS319.error(
	                                        "Message:{},checkDependency:{}",
	                                        pocdin000040uv01.toString(),
	                                        "（除微生物检验报告的临时报告外，需要判断）更新检验报告时相关数据的召回状态并非为已召回状态，暂不做更新操作：params: labReportLid = "
	                                            + labReportLid
	                                            + "，sourceSystemId = "
	                                            + sourceSystemId
	                                            + "，reportTypeCode = "
	                                            + reportTypeCode + "; orgCode = "
	                                            + pocdin000040uv01.getOrgCode());
	                            }
	                            else
	                            {
	                                loggerBS354.error(
	                                        "Message:{},checkDependency:{}",
	                                        pocdin000040uv01.toString(),
	                                        "（除微生物检验报告的临时报告外，需要判断）更新检验报告时相关数据的召回状态并非为已召回状态，暂不做更新操作：params: labReportLid = "
	                                            + labReportLid
	                                            + "，sourceSystemId = "
	                                            + sourceSystemId
	                                            + "，reportTypeCode = "
	                                            + reportTypeCode + "; orgCode = "
	                                            + pocdin000040uv01.getOrgCode());
	                            }
	                        }
	
	                        flag = false;
	                    }
					}
                }

                if (Constants.VERSION_NUMBER_PART_APPROVED.equals(pocdin000040uv01.getVersionNumber()))
                {
                    pocdin000040uv01.setVersionNumber(Constants.VERSION_NUMBER_UPDATE);
                }

                // [BUG]0037432 MODIFY END

                if ("S009".equals(sourceSystemId)
                    && "000".equals(pocdin000040uv01.getReviewerId()))
                {
                    flag = true;
                }
                // [BUG]0010520 ADD END
            }
            else
            {
                logger.error("检验报告本身不存在false: {} 检验报告版本状态: {}", "labResult = "
                    + labResult + "，params: labReportLid = " + labReportLid
                    + "，sourceSystemId = " + sourceSystemId
                    + "，reportTypeCode = " + reportTypeCode + "; orgCode = "
                    + pocdin000040uv01.getOrgCode(),
                        pocdin000040uv01.getVersionNumber());
                if (logFlag)
                {
                    if (Constants.SOURCE_LAB.equals(pocdin000040uv01.getSourceSystemId()))
                    {
                        loggerBS319.error(
                                "Message:{},checkDependency:{}",
                                pocdin000040uv01.toString(),
                                "检验报告本身不存在：labResult = " + labResult
                                    + "，params: labReportLid = " + labReportLid
                                    + "，sourceSystemId = " + sourceSystemId
                                    + "，reportTypeCode = " + reportTypeCode
                                    + "; orgCode = "
                                    + pocdin000040uv01.getOrgCode()
                                    + "。检验报告版本状态:"
                                    + pocdin000040uv01.getVersionNumber());
                    }
                    else
                    {
                        loggerBS354.error(
                                "Message:{},checkDependency:{}",
                                pocdin000040uv01.toString(),
                                "检验报告本身不存在：labResult = " + labResult
                                    + "，params: labReportLid = " + labReportLid
                                    + "，sourceSystemId = " + sourceSystemId
                                    + "，reportTypeCode = " + reportTypeCode
                                    + "; orgCode = "
                                    + pocdin000040uv01.getOrgCode()
                                    + "。检验报告版本状态:"
                                    + pocdin000040uv01.getVersionNumber());
                    }
                }

                flag = false;
            }
            // [BUG]0010366 MODIFY END
            return flag;
            // [BUG]0014971 MODIFY END
        }
        logger.error("错误的消息交互类型号：" + pocdin000040uv01.getVersionNumber());
        if (logFlag)
        {
            if (Constants.SOURCE_LAB.equals(pocdin000040uv01.getSourceSystemId()))
            {
                loggerBS319.error("Message:{},checkDependency:{}",
                        pocdin000040uv01.toString(), "错误的消息交互类型号："
                            + pocdin000040uv01.getVersionNumber());
            }
            else
            {
                loggerBS354.error("Message:{},checkDependency:{}",
                        pocdin000040uv01.toString(), "错误的消息交互类型号："
                            + pocdin000040uv01.getVersionNumber());
            }
        }
        return false;
    }

    /**
     * 校验成功后把取到的相应数据进行保存或更新操作
     * @param baseDto xml文件中获取的检验报告CDA相应业务数据
     */
    @Override
    @Transactional
    public void saveOrUpdate(POCDIN000040UV01 pocdin000040uv01)
    {
        // $Author: tong_meng
        // $Date: 2013/8/30 15:00
        // [BUG]0036757 ADD BEGIN
        serviceId = pocdin000040uv01.getMessage().getServiceId();
        // [BUG]0036757 ADD END

        // 根据检验报告CDA中的versionNumber判断新增，修改，召回
        if (Constants.VERSION_NUMBER_INSERT.equals(pocdin000040uv01.getVersionNumber()))
        {
            saveLabResult(pocdin000040uv01);
        }
        else if (Constants.VERSION_NUMBER_UPDATE.equals(pocdin000040uv01.getVersionNumber()))
        {
            updateLabResult(pocdin000040uv01);
        }
        else if (Constants.VERSION_NUMBER_WITHDRAW.equals(pocdin000040uv01.getVersionNumber()))
        {
            withdrawLabResult(pocdin000040uv01);
        }
    }

    /**
     * 检验报告新增
     * @param pocdin000040uv01 检验报告CDA对象
     */
    private void saveLabResult(POCDIN000040UV01 pocdin000040uv01)
    {
        documentSn = commonService.getSequence(DOCUMENT_SEQUENCE);

        // 检验报告对象
        LabResult lResult = pPart.getLabResult(pocdin000040uv01,
                Constants.VERSION_NUMBER_INSERT, labResult, commonService,
                REPORT_SEQUENCE, visitSn, patientSn, patientLid, systemTime,
                noDeleteFlag, documentSn, sourceSystemId);
        reportSn = lResult.getLabReportSn();

        if(lResult != null && labOrdersList != null && !labOrdersList.isEmpty()){
        	for(Object obj : labOrdersList){
        		LabOrder lo = (LabOrder) obj;
        		lo.setExecutiveDept(lResult.getLabDept());
        		lo.setExecutiveDeptName(lResult.getLabDeptName());
        	}
        	commonService.updateList(labOrdersList);
        }
        
        // $Author: jin_peng
        // $Date: 2013/09/03 14:02
        // [BUG]0036979 MODIFY BEGIN
        // 病历文档对象
        ClinicalDocument clinicalDocument = pPart.getClinicalDocument(
                pocdin000040uv01, lResult, documentSn, systemTime, patientLid,
                serviceId, commonService);

        // [BUG]0036979 MODIFY END

        // 检验项目及检验结果集合
        List<List<Object>> resultAndItem = getLabResultAndItem(
                pocdin000040uv01, Constants.VERSION_NUMBER_INSERT);

        // 检验项目集合
        List<Object> lrciList = resultAndItem.get(0);

        // 检验结果集合
        List<Object> labResultItemList = resultAndItem.get(1);

        // 检验结果对应的抗菌药物更新对象集合
        List<Object> antibacterialDrugList = resultAndItem.get(2);

        // 检验影像集合
        List<Object> labImageList =  pPart.getLabImage(pocdin000040uv01,
                Constants.VERSION_NUMBER_INSERT, commonService,
                MEDICAL_IMAGING_SEQUENCE, reportSn, systemTime, noDeleteFlag,
                labService).get(0);

        // Author :jin_peng
        // Date : 2013/09/11 10:12$
        // [BUG]0037243 MODIFY BEGIN
        // $Author: wei_peng
        // $Date: 2013/3/20 15:04
        // [BUG]0014602 ADD BEGIN
        // 检验报告与检验医嘱关系集合
        List<LabOrderLabResult> labOrderLabResults = pPart.getLabOrderLabResult(
                commonService, reportSn, labOrdersList, systemTime, serviceId);
        // [BUG]0014602 ADD END
        // [BUG]0037243 MODIFY END

        // $Author: wei_peng
        // $Date: 2013/3/20 15:04
        // [BUG]0014602 MODIFY BEGIN
        // $Author :jin_peng
        // $Date : 2012/09/20 14:47$
        // [BUG]0009691 MODIFY BEGIN
        // 新增检验报告及其关联对象
        labService.saveLabResult(pocdin000040uv01.getMessage(), lResult,
                lrciList, labResultItemList, antibacterialDrugList,
                labImageList, labOrderLabResults, clinicalDocument);
        // [BUG]0009691 MODIFY END
        // [BUG]0014602 MODIFY END
        MedicalImaging medicalImaging = getCDAImage(pocdin000040uv01,"",clinicalDocument);
		/*// 执行更新操作
		clinicalDocumentService.updateClinicalDocument(
				clinicalDocument, medicalImaging,
				pocdin000040uv01.getMessage(),systemTime);*/
        // 保存图像到本地服务器
        clinicalDocumentService.saveMedicalImaging(clinicalDocument, medicalImaging);
    }

    /**
     * 检验报告修改
     * @param pocdin000040uv01 检验报告CDA对象
     */
    private void updateLabResult(POCDIN000040UV01 pocdin000040uv01)
    {
        // documentSn = commonService.getSequence(DOCUMENT_SEQUENCE);

        // 检验报告更新对象
        LabResult lResult = pPart.getLabResult(pocdin000040uv01,
                Constants.VERSION_NUMBER_UPDATE, labResult, commonService,
                REPORT_SEQUENCE, visitSn, patientSn, patientLid, systemTime,
                noDeleteFlag, null, sourceSystemId);
        reportSn = lResult.getLabReportSn();

        if(lResult != null && labOrdersList != null && !labOrdersList.isEmpty()){
        	for(Object obj : labOrdersList){
        		LabOrder lo = (LabOrder) obj;
        		lo.setExecutiveDept(lResult.getLabDept());
        		lo.setExecutiveDeptName(lResult.getLabDeptName());
        	}
        	commonService.updateList(labOrdersList);
        }
        
        // $Author: jin_peng
        // $Date: 2013/09/03 14:02
        // [BUG]0036979 MODIFY BEGIN
        // 病历文档对象
        ClinicalDocument clinicalDocument = pPart.getClinicalDocument(
                pocdin000040uv01, lResult, null, systemTime, patientLid,
                serviceId, commonService);

        // [BUG]0036979 MODIFY END

        // 获取检验项目和对应的检验结果对象集合
        List<List<Object>> resultAndItem = getLabResultAndItem(
                pocdin000040uv01, Constants.VERSION_NUMBER_UPDATE);

        // 获取检验影像对象集合
        List<List<Object>> resImageList = pPart.getLabImage(pocdin000040uv01,
                Constants.VERSION_NUMBER_UPDATE, commonService,
                MEDICAL_IMAGING_SEQUENCE, reportSn, systemTime, noDeleteFlag,
                labService);

        // 检验结果对应的抗菌药物删除对象集合
        List<Object> antibacterialDrugDeleteList = resultAndItem.get(4);
        List<AntibacterialDrug> unique = new ArrayList<AntibacterialDrug>();
        for(Object antibacterialDrugObject : antibacterialDrugDeleteList)
        {
            AntibacterialDrug antibacterialDrug = (AntibacterialDrug) antibacterialDrugObject;
            boolean flag = false;
            for(AntibacterialDrug d : unique){
            	if(antibacterialDrug.getAntibacterialDrugSn().equals(d.getAntibacterialDrugSn())){
            		flag = true;
            		break;
            	}
            }
            if(!flag) unique.add(antibacterialDrug);
        }
        antibacterialDrugDeleteList.clear();
        antibacterialDrugDeleteList.addAll(unique);

        // 检验结果删除对象集合
        List<Object> labResultItemDeleteList = resultAndItem.get(3);

        // 检验项目删除对象集合
        List<Object> lrciDeleteList = resultAndItem.get(2);

        // 检验影像删除对象集合
        List<Object> labImageDeleteList = resImageList.get(1);

        // 检验项目更新对象集合
        List<Object> lrciList = resultAndItem.get(0);

        // 检验结果更新对象集合
        List<Object> labResultItemList = resultAndItem.get(1);

        // 检验结果对应的抗菌药物更新对象集合
        List<Object> antibacterialDrugList = resultAndItem.get(5);

        // 检验影像更新对象集合
        List<Object> labImageList = resImageList.get(0);

        // 更新检验报告及其相关对象
        labService.updateLabResult(pocdin000040uv01.getMessage(), lResult,
                antibacterialDrugDeleteList, labResultItemDeleteList,
                lrciDeleteList, labImageDeleteList, lrciList,
                labResultItemList, antibacterialDrugList, labImageList,
                clinicalDocument, documentSnToDelete, systemTime);
        
        MedicalImaging medicalImaging = getCDAImage(pocdin000040uv01,"",clinicalDocument);
		// 执行更新操作
		clinicalDocumentService.updateMedicalImaging(clinicalDocument, medicalImaging,systemTime);
    }

    /**
     * 检验报告召回操作
     * @param pocdin000040uv01 检验报告CDA对象
     */
    private void withdrawLabResult(POCDIN000040UV01 pocdin000040uv01)
    {
        // 获取检验报告和对应的检验召回记录对象集合
        List<Object> ResultAndWithdrawList = pPart.getLabResultAndWithdraw(
                pocdin000040uv01, labResult, systemTime, noDeleteFlag);

        // 更新检验报告中的召回状态
        LabResult labResult = (LabResult) ResultAndWithdrawList.get(0);

        // 添加召回记录
        WithdrawRecord wthdrawRecord = (WithdrawRecord) ResultAndWithdrawList.get(1);

        // 召回检验报告
        commonService.withdrawLabResult(labResult, wthdrawRecord);
    }

    /**
     * 获取检验报告关联的检验项目和检验结果的对象集合
     * @param pocdin000040uv01 检验报告CDA对象
     * @param versionNumber 版本号
     * @return 检验项目和检验结果的对象集合
     */
    private List<List<Object>> getLabResultAndItem(
            POCDIN000040UV01 pocdin000040uv01, String versionNumber)
    {
        List<List<Object>> resList = new ArrayList<List<Object>>();

        // 存储新增或修改时的检验大项对象集合
        List<Object> lrciList = new ArrayList<Object>();

        // 存储新增或修改时的检验结果对象集合
        List<Object> labResultList = new ArrayList<Object>();

        // 存储新增或修改时的检验结果对应的抗菌药物对象集合
        List<Object> antsDrugList = new ArrayList<Object>();

        BigDecimal seq = null;
        Date systemTime = DateUtils.getSystemTime();

        // 涂片信息集合
        List<SmearMessage> smearMessageList = null;

        List<ImmunityResult> immunityResultList = null;

        // 获取检验报告项目及结果信息
        List<LabReport> labCompositeAndItemList = pocdin000040uv01.getReport();

        for (LabReport labReport : labCompositeAndItemList)
        {
            LabResultCompositeItem lrci = new LabResultCompositeItem();
            seq = commonService.getSequence(LAB_ITEM_SEQUENCE);
            lrci.setCompositeItemSn(seq); // 设置检验项目内部序列号
            lrci.setLabReportSn(reportSn); // 设置所关联的检验报告内部序列号
            lrci.setItemCode(labReport.getLabItemCode()); // 设置检验大项编码
            lrci.setItemName(labReport.getLabItemName()); // 设置检验大项名称
            // $Author :jin_peng
            // $Date : 2012/07/26 14:57$
            // [BUG]0008030 ADD BEGIN
            if (Constants.SOURCE_MICROBE.equals(sourceSystemId))
            {
                lrci.setWarnFlag(labReport.getWarnFlag());// 设置危险标识编码
                lrci.setWarnFlagName(labReport.getWarnFlagName());// 设置危险标识名称
            }
            // [BUG]0008030 ADD END
            lrci.setCreateTime(systemTime); // 设置该记录创建时间
            lrci.setUpdateTime(systemTime); // 设置该记录更新时间
            lrci.setUpdateCount(Constants.INSERT_UPDATE_COUNT); // 设置该记录初始更新回数
            lrci.setDeleteFlag(noDeleteFlag); // 设置该记录初始删除标记

            // $Author: tong_meng
            // $Date: 2013/8/30 15:00
            // [BUG]0036757 ADD BEGIN
            lrci.setCreateby(pocdin000040uv01.getMessage().getServiceId()); // 设置创建人
            // [BUG]0036757 ADD END

            lrciList.add(lrci);

            // $Author :jin_peng
            // $Date : 2012/07/26 14:57$
            // [BUG]0008030 ADD BEGIN
            // 需新增或更新的检验结果信息,当报告类型为微生物报告时报告中有涂片信息时则涂片信息为检验结果信息，这时检验结果信息不存在；否则相反
            smearMessageList = labReport.getSmearMessage();
            immunityResultList = labReport.getImmunityResult();
            if (smearMessageList != null && !smearMessageList.isEmpty())
            {
                labResultList.addAll(getSmearResultItem(seq, smearMessageList)); // 设置涂片信息集合
            }
            // $Author:wei_peng
            // $Date:2012/9/3
            // $[BUG]0009324 ADD BEGIN
            // 当报告类型为免疫报告时，接入免疫信息到报告结果项表中，其他情况不存在
            else if (immunityResultList != null
                && !immunityResultList.isEmpty())
            {
                labResultList.addAll(getImmunityResultList(seq,
                        immunityResultList));
            }
            // $[BUG]0009324 ADD END
            else
            {
                // 检验结果存在时进行该结果及相应抗菌药物信息获取
                if (labReport.getReportResult() != null
                    && !labReport.getReportResult().isEmpty())
                {
                    List<List<Object>> ressList = getLabResultItemAndAntibacterialDrug(
                            seq, labReport.getReportResult());

                    labResultList.addAll(ressList.get(0)); // 设置检验结果信息集合

                    antsDrugList.addAll(ressList.get(1)); // 设置抗菌药物对象集合
                }
            }
            // [BUG]0008030 ADD END
        }

        // 将需要新增的检验项目和检验结果集合装入要返回的结果集合
        resList.add(lrciList);
        resList.add(labResultList);

        // 修改操作时需要向返回的结果集中增加的删除对象集合
        if (Constants.VERSION_NUMBER_UPDATE.equals(pocdin000040uv01.getVersionNumber()))
        {
            Map<String, Object> lrciMap = new HashMap<String, Object>();
            lrciMap.put("labReportSn", reportSn);
            lrciMap.put("deleteFlag", noDeleteFlag);
            List<Object> LabResultCompositeItemList = labService.selectLabResultCompositeByCondition(lrciMap);

            // 检验结果删除对象集合
            List<Object> labItemList = new ArrayList<Object>();
            // 抗菌药物删除对象集合
            List<Object> antibacterialDrugList = new ArrayList<Object>();

            for (Object object : LabResultCompositeItemList)
            {
                LabResultCompositeItem labResultCompositeItem = (LabResultCompositeItem) object;

                Map<String, Object> itemMap = new HashMap<String, Object>();
                itemMap.put("labResultCompositeItemSn",
                        labResultCompositeItem.getCompositeItemSn());
                itemMap.put("deleteFlag", noDeleteFlag);
                List<Object> labResultItemList = labService.selectLabResultItemByCondition(itemMap);

                // 将根据检验大项查询出的检验结果信息对象统一装入检验结果删除对象集合
                if (labResultItemList != null && !labResultItemList.isEmpty())
                {
                    for (Object labResultItemDeleteObject : labResultItemList)
                    {
                        labItemList.add(labResultItemDeleteObject);

                        // $Author :jin_peng
                        // $Date : 2012/07/26 14:57$
                        // [BUG]0008030 ADD BEGIN
                        // 当检验结果不为涂片信息时增加需要删除的抗菌药物信息
                        LabResultItem labResultItem = (LabResultItem) labResultItemDeleteObject;
                        if (smearMessageList == null
                            || smearMessageList.isEmpty())
                        {
                            Map<String, Object> drupMap = new HashMap<String, Object>();
                            drupMap.put("labResultCompositeItemSn",
                                    labResultItem.getLabResultCompositeItemSn());
                            drupMap.put("itemCode", labResultItem.getItemCode());
                            drupMap.put("deleteFlag", noDeleteFlag);
                            List<Object> antDrugList = labService.selectAntibacterialDrugByCondition(drupMap);

                            // 将根据检验结果查询出的抗菌药物信息统一装入抗菌药物删除对象集合
                            antibacterialDrugList.addAll(antDrugList);
                        }
                        // [BUG]0008030 ADD END
                    }
                }
            }

            // 将更新时需要删除的检验大项和检验结果装入要返回的结果集合
            resList.add(LabResultCompositeItemList);
            resList.add(labItemList);

            // $Author :jin_peng
            // $Date : 2012/07/26 17:20$
            // [BUG]0008030 ADD BEGIN
            // 将需要删除的抗菌药物对象集合装入要返回的结果集合
            resList.add(antibacterialDrugList);
            // [BUG]0008030 ADD END
        }

        // 将需要新增的抗菌药物对象集合装入要返回的结果集合
        resList.add(antsDrugList);

        return resList;
    }

    // $Author :jin_peng
    // $Date : 2012/07/26 14:57$
    // [BUG]0008030 ADD BEGIN
    /**
     * 获取检验报告关联的检验结果及其对应的抗菌药物对象集合
     * @param seq 检验结果关联的检验大项内部序列号
     * @param labReportResult CDA中检验结果对象集合
     * @return 检验结果及其对应的抗菌药物对象集合
     */
    private List<List<Object>> getLabResultItemAndAntibacterialDrug(
            BigDecimal seq, List<LabReportResult> labReportResultList)
    {
        // 需返回的结果结合
        List<List<Object>> resList = new ArrayList<List<Object>>();

        // 存储新增或修改时的检验结果对象集合
        List<Object> labResultList = new ArrayList<Object>();

        // 存储新增或修改时检验结果对应的抗菌药物对象集合
        List<Object> antibacterialDrugList = new ArrayList<Object>();

        // 需新增或更新的检验结果信息
        for (LabReportResult labReportResult : labReportResultList)
        {
            LabResultItem labResultItem = new LabResultItem();
            labResultItem.setLabResultCompositeItemSn(seq); // 设置检验结果关联的检验大项内部序列号
            // 微生物培养获取项目编码节点有所不同
            if (Constants.LAB_REPORT_TYPE_CODE_CULTIVATE.equals(labReportTypeCode))
            {
                // Author :jia_yanqing
                // Date : 2014/4/3 09:00
                // [BUG]0043432 MODIFY BEGIN
                labResultItem.setItemCode(labReportResult.getItemUnitSC());// 设置项目编码
                // [BUG]0043432 MODIFY END
            }
            else
            {
                labResultItem.setItemCode(labReportResult.getItemCode()); // 设置项目编码
            }
            // Author :jia_yanqing
            // Date : 2013/1/30 18:00
            // [BUG]0013725 MODIFY BEGIN
            // 设置项目单位
            if (StringUtils.isEmpty(labReportResult.getItemUnitPQ()))
            {
                labResultItem.setItemUnit(labReportResult.getItemUnitSC());
            }
            else
            {
                labResultItem.setItemUnit(labReportResult.getItemUnitPQ());
            }
            // [BUG]0013725 MODIFY END

            // $Author :tong_meng
            // $Date : 2012/7/19 17:33$
            // [BUG]0005896 ADD BEGIN
            labResultItem.setDisplayOrder(StringUtils.strToBigDecimal(
                    labReportResult.getDisplayOrder(), "显示顺位")); // 设置显示顺位
            // [BUG]0005896 ADD END

            labResultItem.setLowValue(StringUtils.strToBigDecimal(
                    labReportResult.getLowValue(), "正常低值")); // 设置正常低值
            labResultItem.setHighValue(StringUtils.strToBigDecimal(
                    labReportResult.getHighValue(), "正常高值")); // 设置正常高值
            labResultItem.setWarnLowValue(StringUtils.strToBigDecimal(
                    labReportResult.getWarnLowValue(), "警告值低值")); // 设置警告值低值
            labResultItem.setWarnHighValue(StringUtils.strToBigDecimal(
                    labReportResult.getWarnHighValue(), "警告值高值")); // 设置警告值高值
            labResultItem.setNormalFlag(labReportResult.getNormalFlag()); // 设置正常标志
            labResultItem.setNormalFlagName(labReportResult.getNormalFlagName()); // 设置正常标志名称
            labResultItem.setWarnFlag(labReportResult.getWarnFlag()); // 设置危险值标识
            labResultItem.setWarnFlagName(labReportResult.getWarnFlagName()); // 设置危险值标识名称
            // Author :jia_yanqing
            // Date : 2014/4/3 09:00
            // [BUG]0043432 MODIFY BEGIN
            labResultItem.setQualitativeResults(labReportResult.getItemValueSTAndSC()); // 设置检验结果描述
            // [BUG]0043432 ADD END

            // 当报告类型为微生物报告时检验结果的xpath不同
            if (Constants.SOURCE_MICROBE.equals(sourceSystemId))
            {
                if (Constants.LAB_REPORT_TYPE_CODE_CULTIVATE.equals(labReportTypeCode))
                {
                    labResultItem.setQualitativeResults(labReportResult.getLabResult()); // 设置项目值
                }
                else
                {
                    labResultItem.setItemValue(labReportResult.getLabResult()); // 设置项目值
                }

                labResultItem.setItemNameCn(labReportResult.getItemNameEn()); // 设置项目中文名称(对应的为英文名称的xpath)

                labResultItem.setReportMemo(labReportResult.getReportMemo()); // 设置备注信息
            }
            else
            {
                // Author :jia_yanqing
                // Date : 2014/4/29 11:00
                // [BUG]0043755 MODIFY BEGIN
                // Author :jia_yanqing
                // Date : 2013/1/30 18:00
                // [BUG]0013725 MODIFY BEGIN
                // 设置项目值
                // if (StringUtils.isEmpty(labReportResult.getItemValuePQ()))
                // {
                // labResultItem.setItemValue(labReportResult.getItemValueSTAndSC());
                // }
                // else
                // {
                labResultItem.setItemValue(labReportResult.getItemValuePQ());
                // }
                // [BUG]0013725 MODIFY END
                // [BUG]0043755 MODIFY END

                labResultItem.setItemNameCn(labReportResult.getItemNameCn()); // 设置项目中文名称
                labResultItem.setItemNameEn(labReportResult.getItemNameEn()); // 设置项目英文名称
                labResultItem.setNormalRefValueText(labReportResult.getReportMemo()); // 设置备注信息(普通检验报告此字段为参考值文本形式范围)
            }

            labResultItem.setCreateTime(systemTime); // 设置该记录创建时间
            labResultItem.setUpdateTime(systemTime); // 设置该记录更新时间
            labResultItem.setUpdateCount(Constants.INSERT_UPDATE_COUNT); // 设置该记录初始更新回数
            labResultItem.setDeleteFlag(noDeleteFlag); // 设置该记录初始删除标记
            // $Author: tong_meng
            // $Date: 2013/8/30 15:00
            // [BUG]0036757 ADD BEGIN
            labResultItem.setCreateby(serviceId); // 设置报告本地ID
            // [BUG]0036757 ADD END

            labResultList.add(labResultItem);

            // 获取检验报告对应的抗菌药物信息
            if (labReportResult.getPreventAllergyDrug() != null
                && !labReportResult.getPreventAllergyDrug().isEmpty())
            {
                antibacterialDrugList.addAll(getAntibacterialDrugItem(seq,
                        labReportResult.getItemCode(),
                        labReportResult.getPreventAllergyDrug()));
            }
        }

        // 将检验结合及其对应的抗菌药物对象集合装入需返回的对象集合中
        resList.add(labResultList);
        resList.add(antibacterialDrugList);

        return resList;
    }

    // [BUG]0008030 ADD END

    // $Author :jin_peng
    // $Date : 2012/07/26 14:57$
    // [BUG]0008030 ADD BEGIN
    /**
     * 获取检验报告关联的涂片结果
     * @param seq 检验结果关联的检验大项内部序列号
     * @param smearMessageList CDA中涂片结果对象集合
     * @return 涂片结果的对象集合
     */
    private List<Object> getSmearResultItem(BigDecimal seq,
            List<SmearMessage> smearMessageList)
    {
        // 存储新增或修改时的涂片结果对象集合
        List<Object> smearResultList = new ArrayList<Object>();

        // 需新增或更新的检验结果信息(涂片结果)
        for (SmearMessage smearMessage : smearMessageList)
        {
            LabResultItem labResultItem = new LabResultItem();
            labResultItem.setLabResultCompositeItemSn(seq); // 设置检验结果关联的检验大项内部序列号
            labResultItem.setItemCode(smearMessage.getSmearResultCode()); // 设置项目编码
            labResultItem.setQualitativeResults(smearMessage.getSmearResultName()); // 设置检验结果
            labResultItem.setDisplayOrder(StringUtils.strToBigDecimal(
                    smearMessage.getSequenceNumber(), "显示序号")); // 设置显示序号
            // $Author :jin_peng
            // $Date : 2012/08/08 14:57$
            // [BUG]0008625 ADD BEGIN
            labResultItem.setReportMemo(smearMessage.getRemark());// 设置备注
            // [BUG]0008625 ADD END
            labResultItem.setCreateTime(systemTime); // 设置该记录创建时间
            labResultItem.setUpdateTime(systemTime); // 设置该记录更新时间
            labResultItem.setUpdateCount(Constants.INSERT_UPDATE_COUNT); // 设置该记录初始更新回数
            labResultItem.setDeleteFlag(noDeleteFlag); // 设置该记录初始删除标记

            // $Author: tong_meng
            // $Date: 2013/8/30 15:00
            // [BUG]0036757 ADD BEGIN
            labResultItem.setCreateby(serviceId); // 设置报告本地ID
            // [BUG]0036757 ADD END

            smearResultList.add(labResultItem);
        }

        return smearResultList;
    }

    // [BUG]0008030 ADD END

    // $Author :jin_peng
    // $Date : 2012/07/26 14:57$
    // [BUG]0008030 ADD BEGIN
    /**
     * 获取检验结果对应的抗菌药物集合
     * @param seq 抗菌药物信息关联的检验大项内部序列号
     * @parem itemCode 抗菌药物信息关联的检验结果项目编码 
     * @param preventAllergyDrugList 检验结果对应抗菌药物集合
     * @return 抗菌药物对象集合
     */
    private List<Object> getAntibacterialDrugItem(BigDecimal seq,
            String itemCode, List<PreventAllergyDrug> preventAllergyDrugList)
    {
        // 存储新增或修改时的抗菌药物对象集合
        List<Object> antibacterialDrugList = new ArrayList<Object>();
        BigDecimal seqAnt = null;

        // 需新增或更新的检验结果信息(涂片结果)
        for (PreventAllergyDrug preventAllergyDrug : preventAllergyDrugList)
        {
            AntibacterialDrug antibacterialDrug = new AntibacterialDrug();
            seqAnt = commonService.getSequence(ANTIBACTERIAL_DRUG_SEQUENCE);
            antibacterialDrug.setAntibacterialDrugSn(seqAnt);// 设置抗菌药物内部序列号
            antibacterialDrug.setItemCode(itemCode);// 设置项目编码
            antibacterialDrug.setLabResultCompositeItemSn(seq);// 设置检验大项编码
            antibacterialDrug.setDrugNameCn(preventAllergyDrug.getDrugNameCn());// 设置药物中文名称
            antibacterialDrug.setDrugNameEn(preventAllergyDrug.getDrugNameEn());// 设置药物英文名称
            antibacterialDrug.setSensitivity(preventAllergyDrug.getSensitivity());// 设置过敏度
            antibacterialDrug.setMic(preventAllergyDrug.getMic());// 设置mic法测定值
            antibacterialDrug.setKb(preventAllergyDrug.getKb());// 设置kb法测定值
            antibacterialDrug.setBreakpoint(preventAllergyDrug.getKickpoint());// 设置折点
            antibacterialDrug.setCreateTime(systemTime); // 设置该记录创建时间
            antibacterialDrug.setUpdateTime(systemTime); // 设置该记录更新时间
            antibacterialDrug.setUpdateCount(Constants.INSERT_UPDATE_COUNT); // 设置该记录初始更新回数
            antibacterialDrug.setDeleteFlag(noDeleteFlag); // 设置该记录初始删除标记

            // $Author: tong_meng
            // $Date: 2013/11/20 16:55
            // [BUG]0039757 ADD BEGIN
            // 显示序号
            antibacterialDrug.setDisplayOrder(preventAllergyDrug.getSequenceNo());
            // 药物编码
            antibacterialDrug.setDrugCode(preventAllergyDrug.getDrugCode());
            // [BUG]0039757 ADD BEGIN

            // $Author: tong_meng
            // $Date: 2013/8/30 15:00
            // [BUG]0036757 ADD BEGIN
            antibacterialDrug.setCreateby(serviceId); // 设置报告本地ID
            // [BUG]0036757 ADD END

            antibacterialDrugList.add(antibacterialDrug);
        }

        return antibacterialDrugList;
    }

    // [BUG]0008030 ADD END

    // $Author:wei_peng
    // $Date:2012/9/3
    // $[BUG]0009324 ADD BEGIN
    /**
     * 获取检验报告关联的免疫结果
     * @param seq 检验结果关联的检验大项内部序列号
     * @param ImmunityResultList CDA中免疫结果对象集合
     * @return 免疫结果的对象集合
     */
    private List<Object> getImmunityResultList(BigDecimal seq,
            List<ImmunityResult> ImmunityResultList)
    {
        // 存储新增或修改时的免疫结果对象集合
        List<Object> immunityResultList = new ArrayList<Object>();

        // 需新增或更新的检验结果信息(免疫结果)
        for (ImmunityResult immunityResult : ImmunityResultList)
        {
            LabResultItem labResultItem = new LabResultItem();
            labResultItem.setLabResultCompositeItemSn(seq); // 设置检验结果关联的检验大项内部序列号
            labResultItem.setItemCode(immunityResult.getItemCode()); // 设置项目编码
            labResultItem.setItemNameCn(immunityResult.getItemNameCn());// 设置项目名称
            labResultItem.setItemValue(immunityResult.getQualitativeResults());// 设置检验结果
            labResultItem.setDisplayOrder(StringUtils.strToBigDecimal(
                    immunityResult.getDisplayOrder(), "显示序号")); // 设置显示序号
            labResultItem.setReportMemo(immunityResult.getReportMemo());// 设置备注
            labResultItem.setNormalRefValueText(immunityResult.getReferences());// 设置参考信息
            labResultItem.setCreateTime(systemTime); // 设置该记录创建时间
            labResultItem.setUpdateTime(systemTime); // 设置该记录更新时间
            labResultItem.setUpdateCount(Constants.INSERT_UPDATE_COUNT); // 设置该记录初始更新回数
            labResultItem.setDeleteFlag(noDeleteFlag); // 设置该记录初始删除标记
            // $Author: tong_meng
            // $Date: 2013/8/30 15:00
            // [BUG]0040628 ADD BEGIN
            labResultItem.setItemUnit(immunityResult.getQualitativeResultsUnit());
            // [BUG]0040628 ADD END
            // $Author: tong_meng
            // $Date: 2013/8/30 15:00
            // [BUG]0036757 ADD BEGIN
            labResultItem.setCreateby(serviceId); // 设置报告本地ID
            // [BUG]0036757 ADD END

            immunityResultList.add(labResultItem);
        }

        return immunityResultList;
    }
    // $[BUG]0009324 ADD END
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
	
	/**
	 * 从消息中获取CDA图片
	 * 
	 * @param pocdin000040uv03
	 *            消息信息
	 * @return CDA图片信息
	 */
	public MedicalImaging getCDAImage(POCDIN000040UV01 pocdin000040uv01,
			String flag,ClinicalDocument clinicalDocument) {
		MedicalImaging CDAImage = new MedicalImaging();
		CDAImage.setImagingSn(commonService
				.getSequence(MEDICAL_IMAGING_SEQUENCE)); // 设置影像内部序列号
		CDAImage.setImageContent(getImageContentAfterDecode(pocdin000040uv01
				.getImageText())); // 设置影像内容
		CDAImage.setPromptMessage(pocdin000040uv01.getPrompt()); // 设置提示信息
		CDAImage.setDocumentSn(clinicalDocument.getDocumentSn());// 文档内部序列号

		// 图片扩展名为image/jpg的形式时进行判断取值
		String imageExtension = pocdin000040uv01.getImageExtension();
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
}
