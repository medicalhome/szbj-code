/**
 * 用药医嘱
 * 
 * @version 1.0, 2012/05/15  20:23:00
 
 * @author wei_peng
 * @since 1.0
*/
package com.founder.cdr.batch.writer;

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

import com.founder.cdr.batch.writer.common.CommonWriterUtils;
import com.founder.cdr.cache.DictionaryCache;
import com.founder.cdr.core.Constants;
import com.founder.cdr.entity.CodeDrug;
import com.founder.cdr.entity.CodeMedicalName;
import com.founder.cdr.entity.HerbalFormula;
import com.founder.cdr.entity.MedicalVisit;
import com.founder.cdr.entity.MedicationOrder;
import com.founder.cdr.entity.PatientDoctor;
import com.founder.cdr.hl7.dto.HerbalFormulaDto;
import com.founder.cdr.hl7.dto.MedicationOrderDto;
import com.founder.cdr.hl7.dto.poorin200901uv08.POORIN200901UV08;
import com.founder.cdr.service.CommonService;
import com.founder.cdr.service.DictionaryService;
import com.founder.cdr.service.PrescriptionService;
import com.founder.cdr.util.DataMigrationUtils;
import com.founder.cdr.util.DateUtils;
import com.founder.cdr.util.DictionaryUtils;
import com.founder.cdr.util.StringUtils;

@Component(value = "poorin200901uv08Writer")
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class POORIN200901UV08Writer implements BusinessWriter<POORIN200901UV08>
{
    private static final Logger logger = LoggerFactory.getLogger(POORIN200901UV08Writer.class);

    private static final Logger loggerBS311 = LoggerFactory.getLogger("BS311");

    /**
	 * 包装号默认值
	 */
	private static final String SERIAL_DEFAULT = "00";
	
    @Autowired
    private CommonService commonService;

    @Autowired
    private PrescriptionService prescriptionService;

    @Autowired
    private DictionaryService dictionaryService;

    @Autowired
    private CommonWriterUtils commonWriterUtils;

    // 患者内部ID
    private String patientLid;

    // 患者域ID
    private String patientDomain;

    // 用药医嘱关联的就诊记录
    private MedicalVisit medicalVisit;

    // 需要更新的用药医嘱集合
    private List<MedicationOrder> medicationOrdersForUpdate = new ArrayList<MedicationOrder>();
    // 需要新增的用药医嘱集合
    private List<MedicationOrder> medicationOrdersForAdd = new ArrayList<MedicationOrder>();

    // 系统当前时间
    private Date systemTime = DateUtils.getSystemTime();

    // 需要删除的中药配方集合
    private List<HerbalFormula> herbalFormulaDeleteList = new ArrayList<HerbalFormula>();

    // $Author :jin_peng
    // $Date:2013/02/06 13:38
    // [BUG]0013909 ADD BEGIN
    // 过滤已存在的医生集合
    private List<String> filterPatientDoctorList = new ArrayList<String>();

    // [BUG]0013909 ADD END

    // $Author:wei_peng
    // $Date:2012/11/06 10:46
    // [BUG]0011030 ADD BEGIN
    private List<PatientDoctor> patientDoctorList = new ArrayList<PatientDoctor>();

    // [BUG]0011030 ADD END

    // 消息id
    private String serviceId;
    // $Author :chang_xuewen
    // $Date : 2013/12/05 11:00$
    // [BUG]0040271 ADD BEGIN
    private String orgCode;
    private String orgName;
    // [BUG]0040271 ADD END
    // 人民版标版区分处理标识
    private static final String TURN_ON_FLAG = "1";
    /**
     * 数据业务校验
     */
    @Override
    public boolean validate(POORIN200901UV08 message)
    {
    	// $Author :chang_xuewen
        // $Date : 2014/1/22 10:30$
        // [BUG]0042086 MODIFY BEGIN   
    	// $Author :chang_xuewen
        // $Date : 2013/12/06 11:00$
        // [BUG]0040271 ADD BEGIN
        orgCode = message.getOrgCode();
        orgName = message.getOrgName();
    	/*if(!message.getOrgCode().equals(message.getMessage().getOrgCode())){
    		loggerBS311.error(
                    "Message:{}, validate:{}",
                    message.toString(),
                    "消息头与消息体中的医疗机构代码不一致：头：OrgCode = " 
                    + message.getMessage().getOrgCode() 
                    + " ，体：OrgCode ="
                    + message.getOrgCode());
        	return false;
        }*/
    	// [BUG]0040271 ADD END
        if(StringUtils.isEmpty(orgCode)){
        	// $Author :yang_mingjie
        	// $Date : 2014/06/26 10:09$
        	// [BUG]0045630 MODIFY BEGIN 
        	orgCode=Constants.HOSPITAL_CODE;
        	orgName=Constants.HOSPITAL_NAME;
        	//[BUG]0045630 MODIFY END
        }
        // [BUG]0042086 MODIFY END   
        // $Author: jia_yanqing
        // $Date: 2012/8/29 16:10
        // [BUG]0008782 ADD BEGIN
        // 消息中的药物医嘱集合
        List<MedicationOrderDto> medicationOrderDtos = message.getMedicationOrderBatch();
        for (MedicationOrderDto medicationOrderDto : medicationOrderDtos)
        {
            // 校验医嘱中执行科室，执行科室名称，医嘱类别编码，执行频率，总剂量，总剂量单位，
            // 病人原科室编码，病人原科室名称，病人原病区名称，病人原病区名称，开嘱时间，开嘱人编码，
            // 开嘱人姓名，医嘱确认医生时间，医嘱确认医生编码，医嘱确认医生姓名，是否为空
        	// 医嘱序号，医嘱号，用药途径编码,药物使用-次剂量（剂量）,次剂量单位,药物编码,药物名称,医嘱时间类型编码
            if (!StringUtils.isNotNullAll(//medicationOrderDto.getExecDept(),
//                    medicationOrderDto.getExecDeptName(),
                    medicationOrderDto.getOrderType(),
//                    medicationOrderDto.getTotalDosage(),
//                    medicationOrderDto.getTotalDosageUnit(),
//                    medicationOrderDto.getFormerDept(),
//                    medicationOrderDto.getFormerDeptName(),
//                    medicationOrderDto.getFormerWard(),
//                    medicationOrderDto.getFormerWardName(),
                    medicationOrderDto.getOrderTime(),
                    medicationOrderDto.getOrderPerson(),
//                    medicationOrderDto.getOrderPersonName(),
//                    medicationOrderDto.getDoctorConfirmTime(),
//                    medicationOrderDto.getDoctorConfirmPerson(),
//                    medicationOrderDto.getDoctorConfirmPersonName(),
                    medicationOrderDto.getOrderLid(),
//                    medicationOrderDto.getOrderSeqnum(),
//                    medicationOrderDto.getRouteCode(),
//                    medicationOrderDto.getDosage(),
//                    medicationOrderDto.getDosageUnit(),
                    medicationOrderDto.getDrugCode()
//                    medicationOrderDto.getDrugName(),
//                    medicationOrderDto.getOrdExecTypeCode()
            		))
            {
                loggerBS311.error(
                        "Message:{},validate{}",
                        message.toString(),
//                        "非空字段验证未通过! ExecDept = "
                        "非空字段验证未通过!"
//                            + medicationOrderDto.getExecDept()
//                            + "; ExecDeptName = "
//                            + medicationOrderDto.getExecDeptName()
                            + ";OrderType = "
                            + medicationOrderDto.getOrderType()
//                            + "; MedicineFrenquency = "
//                            + medicationOrderDto.getMedicineFrenquency()
//                            + "; TotalDosage = "
//                            + medicationOrderDto.getTotalDosage()
//                            + "; TotalDosageUnit = "
//                            + medicationOrderDto.getTotalDosageUnit()
//                            + "; FormerDept = "
//                            + medicationOrderDto.getFormerDept()
//                            + "; FormerDeptName = "
//                            + medicationOrderDto.getFormerDeptName()
//                            + "; FormerWard = "
//                            + medicationOrderDto.getFormerWard()
//                            + "; FormerWardName = "
//                            + medicationOrderDto.getFormerWardName()
//                            + medicationOrderDto.getSerialNo()
                            + "; OrderTime = "
                            + medicationOrderDto.getOrderTime()
                            + "; OrderPerson = "
                            + medicationOrderDto.getOrderPerson()
                           /* + "; OrderPersonName = "
                            + medicationOrderDto.getOrderPersonName()
                            + "; DoctorConfirmTime = "
                            + medicationOrderDto.getDoctorConfirmTime()
                            + "; DoctorConfirmPerson = "
                            + medicationOrderDto.getDoctorConfirmPerson()
                            + "; DoctorConfirmPersonName = "
                            + medicationOrderDto.getDoctorConfirmPersonName()*/
                            + "; OrderLid = "
                            + medicationOrderDto.getOrderLid()
                            /*+ "; OrderSeqnum = "
                            + medicationOrderDto.getOrderSeqnum()*/
//                            + "; RouteCode = "
//                            + medicationOrderDto.getRouteCode()
//                            + "; Dosage = "
//                            + medicationOrderDto.getDosage()
//                            + "; DosageUnit = "
//                            + medicationOrderDto.getDosageUnit()
                            + "; DrugCode = "
                            + medicationOrderDto.getDrugCode()
                            /*+ "; DrugName = "
                            + medicationOrderDto.getDrugName()
                            + ";OrdExecTypeCode = "
                            + medicationOrderDto.getOrdExecTypeCode()*/
                		);
                return false;
            }
            if (!isHerbal(medicationOrderDto))
            {
                // 如果是西药/成药，使用频率
                if (!StringUtils.isNotNullAll(
                		medicationOrderDto.getMedicineFrenquency()
                		))
                {
                    loggerBS311.error(
                            "Message:{},validate{}",
                            message.toString(),
                            "西药/成药，非空字段验证未通过! MedicineFrenquency = "
                                + medicationOrderDto.getMedicineFrenquency()
                                + ""
//                                "; Dosage = "
//                                + medicationOrderDto.getDosage()
//                                + "; DosageUnit = "
//                                + medicationOrderDto.getDosageUnit()
//                                + "; DrugCode = "
//                                + medicationOrderDto.getDrugCode()
//                                + "; SerialNo = "
//                                + medicationOrderDto.getSerialNo()
                                );
                    return false;
                }
            }
        }
        // [BUG]0008782 ADD END

        // 验证消息里的中药配方记录（多条）
//        return validateHerbalFormulas(message);
        return true;
    }

    /**
     * 检查消息的依赖关系
     */
    @Override
    public boolean checkDependency(POORIN200901UV08 message, boolean flag)
    {
        patientLid = message.getPatientLid();
        // 域ID
        patientDomain = message.getPatientDomain();

        // $Author:jia_yanqing
        // $Date:2012/9/3 15:10
        // $[BUG]0009078 MODIFY BEGIN
        // $Author:wei_peng
        // $Date:2012/8/21 10:42
        // $[BUG]0009077 ADD BEGIN
        // 判断药物医嘱药物名称是否为空
//        if (!validateValues(message, flag))
//        {
//            return false;
//        }
        // $[BUG]0009077 ADD END
        // $[BUG]0009078 MODIFY END

        // 如为新增消息，判断关联就诊记录是否存在
        if (isNewMessage(message))
        {
            // $Author:jia_yanqing
            // $Date:2012/7/26 13:30$
            // [BUG]0006738 ADD BEIGN
//            String parentOrderLid = null;
            // 判断关联就诊记录是否存在
            return checkMedicalVisit(message, flag);
           
            
//            List<MedicationOrderDto> medicationOrderItemList = message.getMedicationOrderBatch();
//            MedicationOrder medicationOrder;
//            // 检查与父医嘱的关联关系
//            for (int i = 0; i < medicationOrderItemList.size(); i++)
//            {
//                parentOrderLid = medicationOrderItemList.get(i).getParentOrderId();
//                if (!StringUtils.isEmpty(parentOrderLid))
//                {
//                    medicationOrder = commonService.checkParentOrder(
//                            patientDomain, patientLid, parentOrderLid, orgCode,medicalVisit.getVisitSn());
//                    if (medicationOrder == null)
//                    {
//                        // $Author:wu_biao
//                        // $Date:2012/9/19 15:10
//                        // $[BUG]0009815 MODIFY BEGIN
//                        // 父医嘱是否同子医嘱在一个消息中的标识
//                        boolean isparentOrderFromMessage = false;
//                        for (int j = 0; j < medicationOrderItemList.size(); j++)
//                        {
//                            if (parentOrderLid.equals(medicationOrderItemList.get(
//                                    j).getOrderLid()))
//                            {
//                                isparentOrderFromMessage = true;
//                                break;
//                            }
//
//                        }
//                        if (!isparentOrderFromMessage)
//                        {
//                            logger.error(
//                                    "MessageId:{};用药医嘱消息新增场合,关联的父医嘱不存在,父医嘱号 {}",
//                                    message.getMessage().getId(),
//                                    parentOrderLid);
//                            if (flag)
//                            {
//                                loggerBS311.error(
//                                        "Message:{},checkDependency:{}",
//                                        message.toString(),
//                                        "用药医嘱消息新增场合,关联的父医嘱不存在,父医嘱号："
//                                            + parentOrderLid);
//                            }
//                            return false;
//                        }
//                        // $[BUG]0009815 MODIFY END
//                    }
//                }
//            }
            // [BUG]0006738 ADD END
            
        }
        // 如为更新消息，判断更新的用药医嘱是否存在
        else if (isUpdateMessage(message)) {
        	return checkMedicationOrders(message);
          }
         
        logger.error("MessageId:{};错误的消息交互类型：" + message.getTriggerEventCode(),
                message.getMessage().getId());
        if (flag)
        {
            loggerBS311.error("Message:{},checkDependency:{}",
                    message.toString(),
                    "错误的消息交互类型：" + message.getTriggerEventCode());
        }
        return false;
    }

    /**
     * 校验成功后把取到的相应数据进行保存或更新操作
     */
    @Override
    @Transactional
    public void saveOrUpdate(POORIN200901UV08 message)
    {
        serviceId = message.getMessage().getServiceId();

        Map<MedicationOrder, List<HerbalFormula>> medicationOrders = getMedicationOrdersFromMessage(message);
        // 新增消息
        if (isNewMessage(message))
        {
//            prescriptionService.saveMedicationOrders(medicationOrders);
            
            // $Author:wu_biao
            // $Date:2012/9/19 15:10
            // $[BUG]0009815 MODIFY BEGIN
            // 消息里的用药医嘱集合
            List<MedicationOrderDto> medicationOrderDtos = message.getMedicationOrderBatch();
            // 遍历所有用药医嘱信息
            for (MedicationOrderDto medicationOrderDto : medicationOrderDtos)
            {
                if (!StringUtils.isEmpty(medicationOrderDto.getParentOrderId()))
                {
                    MedicationOrder medicationParentOrder = commonService.checkParentOrder(
                            patientDomain, patientLid,
                            medicationOrderDto.getParentOrderId(), orgCode,medicalVisit.getVisitSn());
                    if (medicationParentOrder != null)
                    {
                        BigDecimal parentSn = medicationParentOrder.getOrderSn();
                        MedicationOrder medicationOrder = commonService.checkParentOrder(
                                patientDomain, patientLid,
                                medicationOrderDto.getOrderLid(), orgCode,medicalVisit.getVisitSn());
                        if (medicationOrder.getParentOrderSn() == null)
                        {
                            // 父医嘱内部序列号
                            medicationOrder.setParentOrderSn(parentSn);
                            medicationOrder.setCreateby(DataMigrationUtils.getDataMigration() + serviceId);
                            prescriptionService.update(medicationOrder);
                        }
                        // $[BUG]0009815 MODIFY END
                    }

                }
            }
            // $Author:wei_peng
            // $Date:2012/11/06 11:00
            // [BUG]0011030 ADD BEGIN
            // 新增时保存开嘱医生和对应患者的关系
            for (PatientDoctor patientDoctor : patientDoctorList)
            {
                patientDoctor.setCreateby(DataMigrationUtils.getDataMigration() + serviceId);
                commonService.save(patientDoctor);
            }
            // [BUG]0011030 ADD END
        }
        // 更新消息
        else
        {
            if (herbalFormulaDeleteList != null
                && !herbalFormulaDeleteList.isEmpty())
            {
                for (HerbalFormula herbalFormula : herbalFormulaDeleteList)
                {
                    herbalFormula.setDeleteby(serviceId);
                }
            }

//            prescriptionService.updateMedicationOrders(medicationOrders,
//                    herbalFormulaDeleteList);
        }
        // 港大住院药品医嘱中，一个草药也做一个医嘱直接保存
        commonService.saveList(medicationOrdersForAdd);
        commonService.updateList(medicationOrdersForUpdate);
        
        /*
         * // 新增消息 if (isNewMessage(message)) {
         * prescriptionService.saveMedicationOrder(medicationOrders,
         * herbalFormulaSaveList); } // 更新消息 else if (isUpdateMessage(message))
         * { prescriptionService.updateMedicationOrder(medicationOrders,
         * herbalFormulaSaveList, herbalFormulaDeleteList); }
         */
    }

    /**
     * 是否为新增消息
     * @param message 用药医嘱消息
     * @return 是返回true，否则返回false
     */
    public boolean isNewMessage(POORIN200901UV08 message)
    {
        return Constants.NEW_MESSAGE_FLAG.equals(message.getTriggerEventCode())
        		|| Constants.V2_NEW_MESSAGE_FLAG.equals(message.getTriggerEventCode());
    }

    /**
     * 是否为更新消息
     * @param message 用药医嘱消息
     * @return 是返回true，否则返回false
     */
    public boolean isUpdateMessage(POORIN200901UV08 message)
    {
        return Constants.UPDATE_MESSAGE_FLAG.equals(message.getTriggerEventCode())
        		|| Constants.V2_UPDATE_MESSAGE_FLAG.equals(message.getTriggerEventCode());
    }

    /**
     * 验证消息里的中药配方记录（多条）
     * @param message 用药医嘱消息
     * @return 检验通过返回true，否则返回false
     */
//    private boolean validateHerbalFormulas(POORIN200901UV08 message)
//    {
//        // 消息中的药物医嘱集合
//        List<MedicationOrderDto> medicationOrderDtos = message.getMedicationOrderBatch();
//        for (MedicationOrderDto medicationOrderDto : medicationOrderDtos)
//        {
//            // 是中药用药医嘱（含有中药配方项）
//            if (isHerbal(medicationOrderDto))
//            {
//                // 药物医嘱中的中药配方项集合
//                List<HerbalFormulaDto> herbalFormulaDtos = medicationOrderDto.getHerbalFormula();
//                for (HerbalFormulaDto herbalFormulaDto : herbalFormulaDtos)
//                {
//                    // $Author:wu_biao
//                    // $Date:2012/10/25 15:10
//                    // $[BUG]10749 MODIFY BEGIN
//                    // 药物名称
//                    herbalFormulaDto.setHerbName(DictionaryCache.getDictionary(
//                            Constants.DOMAIN_DRUG_DICTIONARY).get(
//                            herbalFormulaDto.getHerbCode()
//                                + herbalFormulaDto.getSerialNo()));
//                    // $[BUG]10749 MODIFY end
//                    // 中药配方项中中药编码、数量、数量单位、包装序号是否为空
//                    if (!StringUtils.isNotNullAll(
//                            herbalFormulaDto.getQuantity(),
//                            herbalFormulaDto.getUnit(),
//                            herbalFormulaDto.getHerbCode(),
//                            herbalFormulaDto.getSerialNo()))
//                    {
//                        loggerBS311.error(
//                                "Message:{},validate{}",
//                                message.toString(),
//                                "用药医嘱中的药物类别为中草药时，配方非空字段验证未通过：quantity = "
//                                    + herbalFormulaDto.getQuantity()
//                                    + ";unit = " + herbalFormulaDto.getUnit()
//                                    + ";HerbCode = "
//                                    + herbalFormulaDto.getHerbCode()
//                                    + ";SerialNo = "
//                                    + herbalFormulaDto.getSerialNo() + ";");
//                        return false;
//                    }
//                }
//            }
//        }
//        return true;
//    }

    // $Author:jia_yanqing
    // $Date:2012/9/3 15:10
    // $[BUG]0009078 MODIFY BEGIN
    /**
     * 验证消息里的中药配方记录（多条）中药物名称是否为空
     * @param medicationOrderDtos 用药医嘱集合
     * @return 验证通过返回true，否则返回false
     */
//    private boolean validateValues(POORIN200901UV08 message, boolean flag)
//    {
//        List<MedicationOrderDto> medicationOrderDtos = message.getMedicationOrderBatch();
//        for (MedicationOrderDto medicationOrderDto : medicationOrderDtos)
//        {
//            // 是中药用药医嘱（含有中药配方项）
//            if (isHerbal(medicationOrderDto))
//            {
//                // 药物医嘱中的中药配方项集合
//                List<HerbalFormulaDto> herbalFormulaDtos = medicationOrderDto.getHerbalFormula();
//                for (HerbalFormulaDto herbalFormulaDto : herbalFormulaDtos)
//                {
//                    // $Author:wu_biao
//                    // $Date:2012/10/25 15:10
//                    // $[BUG]10749 MODIFY BEGIN
//                    // 中药配方项中中药名称为空
//                    if (!StringUtils.isNotNullAll(DictionaryCache.getDictionary(
//                            Constants.DOMAIN_DRUG_DICTIONARY).get(
//                            herbalFormulaDto.getHerbCode()
//                                + herbalFormulaDto.getSerialNo())))
//                    {
//                        logger.error(
//                                "MessageId:{};用药医嘱中的药物类别为中草药时，配方非空字段验证未通过：HerbCode = "
//                                    + herbalFormulaDto.getHerbCode()
//                                    + "SerialNo="
//                                    + herbalFormulaDto.getSerialNo() + ";",
//                                message.getMessage().getId());
//                        if (flag)
//                        {
//                            if (flag)
//                            {
//                                loggerBS311.error(
//                                        "Message:{},checkDependency:{}",
//                                        message.toString(),
//                                        "用药医嘱中的药物类别为中草药时，配方非空字段验证未通过：HerbCode = "
//                                            + herbalFormulaDto.getHerbCode()
//                                            + "SerialNo="
//                                            + herbalFormulaDto.getSerialNo()
//                                            + ";");
//                            }
//                        }
//                        return false;
//                    }
//                    // $[BUG]10749 MODIFY END
//                }
//            }
//        }
//        return true;
//    }

    // $[BUG]0009078 MODIFY END

    /**
     * 判断用药医嘱关联的就诊记录是否存在
     * @param message 用药医嘱消息
     * @return 存在返回true，否则返回false
     */
    private boolean checkMedicalVisit(POORIN200901UV08 message, boolean flag)
    {
        // 获取用药医嘱相关联的就诊记录
        medicalVisit = commonService.mediVisit(message.getPatientDomain(),
                patientLid, message.getVisitTimes(), orgCode,
                message.getVisitOrdNo(),message.getVisitTypeCode());
        // 就诊记录不存在
        if (medicalVisit == null)
        {
            logger.error(
                    "MessageId:{};用药医嘱关联的就诊不存在，域ID："
                        + message.getPatientDomain() + "，患者本地ID：" + patientLid
                        + "，就诊次数：" + message.getVisitTimes()+ "，医疗机构代码：" + orgCode
                        +",就诊流水号："+ message.getVisitOrdNo()+",就诊类型："+message.getVisitTypeCode(),
                    message.getMessage().getId());
            if (flag)
            {
                loggerBS311.error(
                        "Message:{},checkDependency:{}",
                        message.toString(),
                        "用药医嘱关联的就诊不存在，域ID：" + message.getPatientDomain()
                            + "，患者本地ID：" + patientLid + "，就诊次数："
                            + message.getVisitTimes()+ "，医疗机构代码：" + orgCode
                            +",就诊流水号："+ message.getVisitOrdNo()+",就诊类型："+message.getVisitTypeCode());
            }
            return false;
        }
        return true;
    }

    /**
     * 检查所有用药医嘱是否存在
     * @param message 用药医嘱消息
     * @return 全部存在返回true，否则返回false
     */
   
	private boolean checkMedicationOrders(POORIN200901UV08 message) {
      List<MedicationOrderDto> medicationOrderDtos = message.getMedicationOrderBatch(); 
      // 遍历用药医嘱集合 
      for (MedicationOrderDto medicationOrderDto : medicationOrderDtos) { 
    	  // 本地医嘱号 
    	  String orderLid = medicationOrderDto.getOrderLid(); 
    	  String seqNum = medicationOrderDto.getOrderSeqnum();
    	  // 关联用药医嘱记录不存在 
    	  if(!checkMedicationOrder(message.getPatientDomain(), orderLid, seqNum)) {
    		  logger.error("更新用药医嘱信息不存在，医嘱号为" + orderLid+",医嘱序号："+seqNum); 
    		  return false;
    		  } 
    	  } 
      return true; 
      }
    

    /**
     * 检查一条用药医嘱记录是否存在
     * @param patientDomain 域ID
     * @param orderLid 本地医嘱号
     * @return 存在返回true，否则返回false
     */
     private boolean checkMedicationOrder(String patientDomain, String orderLid, String seqNum) { 
    	 return getMedicationOrder(patientDomain, orderLid, seqNum) != null; 
    	 }
     

    /**
     * 根据域代码，本地医嘱号，检索数据库的用药医嘱
     * @param patientDomain 域代码
     * @param orderLid 本地医嘱号
     * @return 如果存在用药医嘱，返回用药医嘱，如果不存在，返回null
     */

	private MedicationOrder getMedicationOrder(String patientDomain, String
     orderLid, String seqNum) {
		// 添加搜索条件（患者内部ID，域ID，本地医嘱号，删除标志）
		Map<String, Object> map = new HashMap<String, Object>(); 
		map.put("patientLid", patientLid);
//		map.put("orderSeqnum", seqNum);
		map.put("patientDomain", patientDomain); 
		map.put("deleteFlag",Constants.NO_DELETE_FLAG); 
		map.put("orderLid", orderLid); 
		// 通过条件查找用药医嘱
      List<Object> medicationOrders = commonService.selectByCondition(MedicationOrder.class, map); 
      // 存在待更新的用药医嘱，将其添加到待更新用药医嘱集合中 
      if(medicationOrders != null && medicationOrders.size() > 0) {
    	  MedicationOrder order = (MedicationOrder) medicationOrders.get(0);
    	  medicationOrdersForUpdate.add(order);
    	  return order;
      }
      else 
    	  return null; 
      }
     

    /**
     * 获取消息中的用药医嘱信息
     * @param message 用药医嘱消息
     * @return 用药医嘱集合
     */
    public Map<MedicationOrder, List<HerbalFormula>> getMedicationOrdersFromMessage(
            POORIN200901UV08 message)
    {
        // 返回结果的用药医嘱集合
        Map<MedicationOrder, List<HerbalFormula>> medicationOrders = new HashMap<MedicationOrder, List<HerbalFormula>>();
        // 消息里的用药医嘱集合
        List<MedicationOrderDto> medicationOrderDtos = message.getMedicationOrderBatch();

        Map<String, String> msgInfoMap = new HashMap<String, String>();

        // 是否是消息
//        msgInfoMap.put("isNewMessage", isNewMessage(message));
        // 域代码
        msgInfoMap.put("patientDomain", message.getPatientDomain());

        medicationOrdersForUpdate = new ArrayList<MedicationOrder>();
        medicationOrdersForAdd = new ArrayList<MedicationOrder>();
        
        // $Author :jia_yanqing
        // $Date : 2012/5/25 10:55$
        // [BUG]0006659 DELETE BEGIN
        // 床号
        // msgInfoMap.put("bedNo", message.getBedNo());
        // 病区
        // msgInfoMap.put("wardsId", message.getWardsId());
        // [BUG]0006659 DELETE END

        // 确认人
        msgInfoMap.put("nurseConfirmPerson", message.getNurseConfirmPerson());
        
        /*msgInfoMap.put("nurseConfirmPersonName",
                message.getNurseConfirmPersonName());*/
        msgInfoMap.put("nurseConfirmPersonName", 
        		DictionaryUtils.getNameFromDictionary(Constants.DOMAIN_STAFF, message.getNurseConfirmPerson(), message.getNurseConfirmPersonName()));
        // 确认时间
        msgInfoMap.put("nurseConfirmTime", message.getNurseConfirmTime());
        
        // 医嘱组套编码
        msgInfoMap.put("orderSetCode", message.getOrderSetCode());
        // 医嘱描述
        msgInfoMap.put("orderDescribe", message.getOrderDescribe());
        // 下医嘱科室ID	
        msgInfoMap.put("orderDept", message.getOrderDept());
        // 下医嘱科室名称
//        msgInfoMap.put("orderDeptName", message.getOrderDeptName());
        msgInfoMap.put("orderDeptName", 
        		DictionaryUtils.getNameFromDictionary(Constants.DOMAIN_DEPARTMENT, message.getOrderDept(), message.getOrderDeptName()));
        

        // 遍历所有用药医嘱信息
        for (MedicationOrderDto medicationOrderDto : medicationOrderDtos)
        {
            // 将消息里用药医嘱转为实体
            MedicationOrder order = ConvertDtoToMedicationOrder(msgInfoMap,
                    medicationOrderDto, message);
            // 如果为草药添加草药配方列表
//            if (isHerbal(medicationOrderDto))
//            {
//                medicationOrders.put(order,
//                        getHerbalFormulas(medicationOrderDto));
//            }
//            else
            if(!medicationOrdersForUpdate.contains(order))
                medicationOrders.put(order, null);
        }

        return medicationOrders;
    }

    /**
     * 通过消息记录封装一条用药医嘱信息
     * @param msgInfoMap 消息中一些信息
     * @param medicationOrderBatch 消息中的一条用药医嘱记录
     * @return 一条用药医嘱信息
     */
    public MedicationOrder ConvertDtoToMedicationOrder(
            Map<String, String> msgInfoMap,
            MedicationOrderDto medicationOrderDto,
            POORIN200901UV08 message)
    {
        // 是否为新增消息
        boolean isNewMessage = isNewMessage(message);
        // 域ID
        String patientDomain = msgInfoMap.get("patientDomain");

        // $Author :jia_yanqing
        // $Date : 2012/5/25 10:55$
        // [BUG]0006659 DELETE BEGIN
        // 床号
        // String bedNo = msgInfoMap.get("bedNo").toString();
        // 病区号
        // String wardsId = msgInfoMap.get("wardsId").toString();
        // [BUG]0006659 DELETE BEGIN

        // 确认人ID
        String nurseConfirmPerson = msgInfoMap.get("nurseConfirmPerson");
        // 确认人姓名
        String nurseConfirmPersonName = msgInfoMap.get("nurseConfirmPersonName");
        // 确认时间
        String nurseConfirmTime = msgInfoMap.get("nurseConfirmTime");
        // 医嘱组套编码
        String orderSetCode = msgInfoMap.get("orderSetCode");
        // 医嘱描述
        String orderDescribe = msgInfoMap.get("orderDescribe");
        // 下医嘱科室名称id
        String orderDept = msgInfoMap.get("orderDept");
        // 下医嘱科室名称
        String orderDeptName = msgInfoMap.get("orderDeptName");
        
        MedicationOrder medicationOrder = null;

        medicationOrder = getMedicationOrder(patientDomain, medicationOrderDto.getOrderLid(), null);
        
        if(medicationOrder != null){
        	// 更新消息
        	medicationOrder.setUpdateby(serviceId);
        } else {// 新增消息
        	// $Author :chang_xuewen
            // $Date : 2013/12/05 11:00$
            // [BUG]0040271 ADD BEGIN
        	medicationOrder = new MedicationOrder();
        	medicationOrdersForAdd.add(medicationOrder);
        	medicationOrder.setOrderSn(commonService.getSequence("SEQ_ORDER"));
        	medicationOrder.setOrgCode(orgCode);
        	medicationOrder.setOrgName(orgName);
            // [BUG]0040271 ADD END
            // 患者内部序列号
            medicationOrder.setPatientSn(medicalVisit.getPatientSn());
            // 就诊内部序列号
            medicationOrder.setVisitSn(medicalVisit.getVisitSn());
            // 域ID
            medicationOrder.setPatientDomain(patientDomain);
            // 患者本地ID
            medicationOrder.setPatientLid(patientLid);

            // $Author:jia_yanqing
            // $Date:2012/7/24 11:30$
            // [BUG]0006738 ADD BEIGN
            BigDecimal parentSn = null;
            MedicationOrder medicationParentOrder = commonService.checkParentOrder(
                    patientDomain, patientLid,
                    medicationOrderDto.getParentOrderId(),orgCode,medicalVisit.getVisitSn());
            if (medicationParentOrder != null)
            {
                parentSn = medicationParentOrder.getOrderSn();
            }
            // 父医嘱内部序列号
            medicationOrder.setParentOrderSn(parentSn);
            // [BUG]0006738 ADD END

            // 更新回数
            medicationOrder.setUpdateCount(Constants.INSERT_UPDATE_COUNT);
            // 创建时间
            medicationOrder.setCreateTime(systemTime);
            // 删除标志
            medicationOrder.setDeleteFlag(Constants.NO_DELETE_FLAG);

            // $Author:wei_peng
            // $Date:2012/11/06 10:52
            // [BUG]0011030 ADD BEGIN
            if (medicationOrderDto.getOrderPerson() != null
                && !medicationOrderDto.getOrderPerson().isEmpty())
            {
                PatientDoctor patientDoctor = commonWriterUtils.getPatientDoctor(
                        medicalVisit.getVisitSn(), medicalVisit.getPatientSn(),
                        medicationOrderDto.getOrderPerson(),
                        medicationOrderDto.getOrderPersonName(), systemTime);
                if (patientDoctor != null)
                {
                    // $Author:jin_peng
                    // $Date:2013/02/06 13:38
                    // [BUG]0013909 MODIFY BEGIN
                    // 如果该医生未被处理则进行添加操作
                    if (!commonWriterUtils.isExistsPatientDoctor(
                            filterPatientDoctorList, medicalVisit.getVisitSn(),
                            medicalVisit.getPatientSn(),
                            medicationOrderDto.getOrderPerson()))
                    {
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
            medicationOrder.setChargeStatusCode(Constants.CHARGE_STATUS_NOTCHARGE);
            // 医嘱交费状态名称
            medicationOrder.setChargeStatusName(DictionaryCache.getDictionary(
                    Constants.ORDER_CHARGE_STATUS).get(
                    Constants.CHARGE_STATUS_NOTCHARGE));
            // [BUG]0011421 ADD END

            medicationOrder.setCreateby(DataMigrationUtils.getDataMigration() + serviceId);
        }

        CodeDrug codeDrug = null;
        // 非中草药类型的用药医嘱
//        if (!isHerbal(medicationOrderDto))
        if (true)
        {
            // $Author :tong_meng
            // $Date : 2012/9/24 14:24$
            // [BUG]0009825 ADD BEGIN
//            codeDrug = prescriptionService.selectCodeDrug(
//                    medicationOrderDto.getDrugCode(),
//                    StringUtils.isEmpty(medicationOrderDto.getSerialNo()) ? SERIAL_DEFAULT:medicationOrderDto.getSerialNo());
            codeDrug = prescriptionService.selectCodeDrugByDrugCode(medicationOrderDto.getDrugCode());
            // [BUG]0009825 ADD END
            // 药物使用-次剂量
            medicationOrder.setDosage(medicationOrderDto.getDosage());
            // 药物使用-次剂量单位
            medicationOrder.setDosageUnit(medicationOrderDto.getDosageUnit());
            // 药物编码
            medicationOrder.setDrugCode(medicationOrderDto.getDrugCode());

            // $Author :tong_meng
            // $Date : 2012/9/24 14:24$
            // [BUG]0009825 MODIFY BEGIN
            // $Author :tong_meng
            // $Date : 2012/7/19 17:05$
            // [BUG]0008099 MODIFY BEGIN
            // $Author :jia_yanqing
            // $Date : 2012/5/24 16:05$
            // [BUG]0006659 DELETE BEGIN
            if (codeDrug != null)
            {
                // $Author :tong_meng
                // $Date : 2012/11/07 11:00$
                // [BUG]0010871 ADD BEGIN
                // 药物类别代码
                medicationOrder.setMedicineClass(codeDrug.getDrugKind());
                if (!StringUtils.isEmpty(codeDrug.getDrugKind()))
                    // 药物类别名称
                    medicationOrder.setMedicineClassName(DictionaryCache.getDictionary(
                            Constants.DOMAIN_MEDICINE_KIND).get(
                            codeDrug.getDrugKind()));
                /*
                 * Author: yu_yzh
                 * Date: 2015/12/10
                 * DELETE BEGIN
                 * */
/*                // 药物常用频率代码
                medicationOrder.setMedicineFrenquency(codeDrug.getFrequCode());
                if (!StringUtils.isEmpty(codeDrug.getFrequCode()))
                    // 药物常用频率名称
                    medicationOrder.setMedicineFreqName(DictionaryCache.getDictionary(
                            Constants.DOMAIN_MEDICINE_FREQ).get(
                            codeDrug.getFrequCode()));*/
                // DELETE END
                
                // 药物名称字典表
                CodeMedicalName cmn = dictionaryService.selectCodeMedicalName(codeDrug.getDrugId());
                // $Author:wei_peng
                // $Date:2012/11/22 11:18
                // [BUG]0011805 ADD BEGIN
                /*
                 * Author: yu_yzh
                 * Date: 2015/12/10
                 * MODIFY BEGIN
                 * */
//                if (cmn != null)
//                    // 药物通用名称
//                    medicationOrder.setApprovedDrugName(cmn.getPrintName());
                // 药物通用名称
                if(cmn != null && cmn.getPrintName() != null && cmn.getPrintName().length() > 0){
                	medicationOrder.setApprovedDrugName(cmn.getPrintName());
                } else {
                	medicationOrder.setApprovedDrugName(codeDrug.getName());	
                }
                
                // 药物英文名
                if(cmn != null){
                	medicationOrder.setEnglishName(cmn.getEnglishName());
                }
                // MODIFY END
                
                // [BUG]0011805 ADD END
                // [BUG]0010871 ADD BEGIN
                // 药物商品名称
                if (!StringUtils.isEmpty(medicationOrderDto.getDrugCode()))
                    // 消息不会传过来drugName，所以要从关联药品字典code_drug查询出name
                    medicationOrder.setDrugName(codeDrug.getName());
                // 药物类型代码
                medicationOrder.setMedicineType(codeDrug.getClassCode());
                if (!StringUtils.isEmpty(codeDrug.getClassCode()))
                    // 药物类型名称
                    medicationOrder.setMedicineTypeName(DictionaryCache.getDictionary(
                            Constants.DOMAIN_MEDICINE_TYPE).get(
                            codeDrug.getClassCode()));
                // 药物剂型代码
                medicationOrder.setMedicineFormCode(codeDrug.getDosage());
                if (!StringUtils.isEmpty(codeDrug.getDosage()))
                    // 药物剂型名称
                    medicationOrder.setMedicineForm(DictionaryCache.getDictionary(
                            Constants.DOMAIN_MEDICINE_FORM).get(
                            codeDrug.getDosage()));
            }
            // [BUG]0006659 DELETE END
            // [BUG]0008099 MODIFY END
            // [BUG]0009825 MODIFY END

            // 用药途径代码
            medicationOrder.setRouteCode(medicationOrderDto.getRouteCode());
            // 用药途径名称
//            medicationOrder.setRouteName(medicationOrderDto.getRouteName());
            medicationOrder.setRouteName(DictionaryUtils.getNameFromDictionary(
            		Constants.DOMAIN_SUPPLY_ROUTE, medicationOrderDto.getRouteCode(), medicationOrderDto.getRouteName()));

            // $Author :jia_yanqing
            // $Date : 2012/5/24 16:05$
            // [BUG]0006659 DELETE BEGIN
            // 药物包装序号
            medicationOrder.setSerialNo(StringUtils.isEmpty(medicationOrderDto.getSerialNo()) ? SERIAL_DEFAULT:medicationOrderDto.getSerialNo());
            // [BUG]0006659 DELETE END

            // $Author:wei_peng
            // $Date:2013/02/04
            // $[BUG]0013740 ADD BEGIN
            // 设置天数
            /*
             * medicationOrder.setDays(StringUtils.strToBigDecimal(
             * medicationOrderDto.getRepeatNumber(), "天数"));
             */
            // 设置领药量
//            medicationOrder.setDispensingQuantity(medicationOrderDto.getObtain());
//            // 设置领药单位
//            medicationOrder.setDispensingUnit(medicationOrderDto.getObtainUnit());
            // $[BUG]0013740 ADD END

        }

        // 设置天数
        medicationOrder.setDays(StringUtils.strToBigDecimal(
                medicationOrderDto.getRepeatNumber(), "天数"));
        
        // 更新时间
        medicationOrder.setUpdateTime(systemTime);

        // $Author :jia_yanqing
        // $Date : 2012/5/25 11:05$
        // [BUG]0006659 DELETE BEGIN
        // 床号
        // medicationOrder.setBedNo(bedNo);
        // 病区ID
        // medicationOrder.setWardsId(wardsId);
        // [BUG]0006659 DELETE END

        // 确认人ID
        medicationOrder.setNurseConfirmPerson(nurseConfirmPerson);
        // 确认人姓名
        medicationOrder.setNurseConfirmPersonName(nurseConfirmPersonName);
        // 确认时间
        medicationOrder.setNurseConfirmTime(DateUtils.stringToDate(nurseConfirmTime));
        // 本地医嘱号
        medicationOrder.setOrderLid(medicationOrderDto.getOrderLid());
        // 医嘱类型
        medicationOrder.setOrderType(medicationOrderDto.getOrderType());
        // 医嘱类型名称
        medicationOrder.setOrderTypeName(medicationOrderDto.getOrderTypeName());
        // 医嘱组套编码
        medicationOrder.setOrderSetCode(orderSetCode);
        // 医嘱描述
        medicationOrder.setOrderDescribe(orderDescribe);
        // 下医嘱科室ID
        medicationOrder.setOrderDept(orderDept);
        // 下医嘱科室名称
        medicationOrder.setOrderDeptName(orderDeptName);

        // $Author :jia_yanqing
        // $Date : 2012/5/24 16:05$
        // [BUG]0006659 DELETE BEGIN
        // 药物类别代码
        // medicationOrder.setMedicineClass(codeDrug.getDrugKind());
        // 药物类别名称
        // medicationOrder.setMedicineClassName(DictionaryCache.getDictionary("MS058").get(codeDrug.getDrugKind()));
        // [BUG]0006659 DELETE END

        // $Author :tong_meng
        // $Date : 2013/4/28 10:33$
        // [BUG]0031572 MODIFY BEGIN
        // 药物使用-频率
        if (!StringUtils.isEmpty(medicationOrderDto.getMedicineFrenquency()))
            medicationOrder.setMedicineFrenquency(medicationOrderDto.getMedicineFrenquency());

        // $Author :tong_meng
        // $Date : 2012/7/1 10:33$
        // [BUG]0007418 ADD BEGIN
        // 药物使用-频率名称
        if (!StringUtils.isEmpty(medicationOrderDto.getMedicineFrenquency())){
//          medicationOrder.setMedicineFreqName(medicationOrderDto.getMedicineFrenquencyName());
        	medicationOrder.setMedicineFreqName(DictionaryUtils.getNameFromDictionary(
            		Constants.DOMAIN_MEDICINE_FREQ, medicationOrderDto.getMedicineFrenquency(), medicationOrderDto.getMedicineFrenquencyName()));
        }

                
        // [BUG]0007418 ADD END
        // [BUG]0031572 MODIFY BEGIN

        // 用药途径编码
        medicationOrder.setRouteCode(medicationOrderDto.getRouteCode());
        // 用药途径名称
//        medicationOrder.setRouteName(medicationOrderDto.getRouteName());
        medicationOrder.setRouteName(DictionaryUtils.getNameFromDictionary(
        		Constants.DOMAIN_SUPPLY_ROUTE, medicationOrderDto.getRouteCode(), medicationOrderDto.getRouteName()));
        // 次剂量
        medicationOrder.setDosage(medicationOrderDto.getDosage());
        // 次剂量单位
        medicationOrder.setDosageUnit(medicationOrderDto.getDosageUnit());
//        // 药物编码	
//        medicationOrder.setDrugCode(medicationOrderDto.getDrugCode());
//        // 药物名称	
//        medicationOrder.setDrugName(medicationOrderDto.getDrugName());
        // 包装序号
//        medicationOrder.setSerialNo(StringUtils.isEmpty(medicationOrderDto.getSerialNo()) ? SERIAL_DEFAULT:medicationOrderDto.getSerialNo());
        // 领药量
        medicationOrder.setDispensingQuantity(medicationOrderDto.getObtain());
        // 领药量单位
        medicationOrder.setDispensingUnit(medicationOrderDto.getObtainDescription());
        // 药物使用总剂量
        medicationOrder.setTotalDosage(medicationOrderDto.getTotalDosage());
        // 药物使用总剂量单位
        medicationOrder.setTotalDosageUnit(medicationOrderDto.getTotalDosageUnit());

        // $Author: wei_peng
        // $Date: 2012/6/12 15:16$
        // [BUG]0006830 MODIFY BEGIN
        // 是否皮试
//        medicationOrder.setSkinTestFlag(StringUtils.getConversionValue(medicationOrderDto.getSkinTestFlag()));
        // [BUG]0006830 MODIFIY END

        // 嘱托
        medicationOrder.setComments(medicationOrderDto.getComments());
        // 长期临时标志
//        if(TURN_ON_FLAG.equals(Constants.MEDICAL_VISIT_CONFIM_LOGIC_TYPE)){
//        	medicationOrder.setTemporaryFlag(medicationOrderDto.getTemporaryFlag(medicationOrderDto.getMedicineFrenquency()));
//        }else{
//        	medicationOrder.setTemporaryFlag(medicationOrderDto.getOrdExecTypeCode());
//        }
        medicationOrder.setTemporaryFlag(
        		Constants.LONG_TERM_FLAG.equals(medicationOrderDto.getTemporaryFlag()) ? Constants.LONG_TERM_FLAG : Constants.TEMPORARY_FLAG);

        
        // Author :jin_peng
        // Date : 2012/11/14 14:56
        // [BUG]0011478 MODIFY BEGIN
        String orderStatus = null;
        String orderStatusName = null;

        /*
         * Author: yu_yzh
         * Date: 2015/10/19 14:57
         * 港大门诊住院医嘱状态都为已下达
         * MODIFY BEGIN
         * */
        /*// 门诊医嘱状态为已下达
        if (Constants.PATIENT_DOMAIN_OUTPATIENT.equals(patientDomain))
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
        }*/
        orderStatus = Constants.ORDER_STATUS_ISSUE;

        orderStatusName = DictionaryCache.getDictionary(
                Constants.DOMAIN_ORDER_STATUS).get(
                Constants.ORDER_STATUS_ISSUE);
        
        //港大门诊住院医嘱状态都为已下达	MODIFY END
        
        // 医嘱状态
        medicationOrder.setOrderStatus(orderStatus);

        // $Author :tong_meng
        // $Date : 2012/7/1 10:33$
        // [BUG]0007418 ADD BEGIN
        // 医嘱状态名称
        medicationOrder.setOrderStatusName(orderStatusName);
        // [BUG]0007418 ADD END
        // [BUG]0011478 MODIFY END

        // 医嘱开立人ID
        medicationOrder.setOrderPerson(medicationOrderDto.getOrderPerson());
        // 医嘱开立人姓名
//        medicationOrder.setOrderPersonName(medicationOrderDto.getOrderPersonName());
        medicationOrder.setOrderPersonName(DictionaryUtils.getNameFromDictionary(
        		Constants.DOMAIN_DEPARTMENT, medicationOrderDto.getOrderPerson(), medicationOrderDto.getOrderPersonName()));

        // $Author :tong_meng
        // $Date : 2012/7/1 10:33$
        // [BUG]0007418 MODIFY BEGIN
        // 医嘱开立科室ID
//        medicationOrder.setOrderDept(medicationOrderDto.getFormerDept());
        // [BUG]0007418 MODIFY END

        // $Author :tong_meng
        // $Date : 2012/7/1 10:33$
        // [BUG]0007418 ADD BEGIN
        // 医嘱开立科室名称
//        medicationOrder.setOrderDeptName(medicationOrderDto.getFormerDeptName());
        // [BUG]0007418 ADD BEGIN

        // 开嘱时间
        medicationOrder.setOrderTime(DateUtils.stringToDate(medicationOrderDto.getOrderTime()));

        // $Author :tong_meng
        // $Date : 2012/7/1 10:33$
        // [BUG]0007418 MODIFY BEGIN
        // 执行科室ID
        medicationOrder.setExecDept(medicationOrderDto.getExecDept());
        // [BUG]0007418 MODIFY END

        // $Author :tong_meng
        // $Date : 2012/7/1 10:33$
        // [BUG]0007418 ADD BEGIN
        // 执行科室ID
//        medicationOrder.setExecDeptName(medicationOrderDto.getExecDeptName());
        medicationOrder.setExecDeptName(DictionaryUtils.getNameFromDictionary(
        		Constants.DOMAIN_DEPARTMENT, medicationOrderDto.getExecDept(), medicationOrderDto.getExecDeptName()));
        // [BUG]0007418 ADD BEGIN

        // $Author :tong_meng
        // $Date : 2013/8/8 11:00$
        // [BUG]0035541 ADD BEGIN
        // $Author :jia_yanqing
        // $Date : 2012/7/20 17:33$
        // [BUG]0008154 MODIFY BEGIN
        // 是否加急
//        medicationOrder.setUrgentFlag(StringUtils.getConversionValue(medicationOrderDto.getUrgency()));
        // [BUG]0008154 MODIFY END
        // 是否适应
        medicationOrder.setAdaptiveFlag(StringUtils.getConversionValue(medicationOrderDto.getAdaptive()));
        // 是否药观
//        medicationOrder.setMedViewFlag(StringUtils.getConversionValue(medicationOrderDto.getMedViewFlag()));
        // [BUG]0035541 ADD END

        // 互斥医嘱类型编码
        medicationOrder.setMutexesOrderType(medicationOrderDto.getMutexesOrderType());
        // 互斥医嘱类型名称
        medicationOrder.setMutexesOrderTypeName(medicationOrderDto.getMutexesOrderTypeName());

        // $Author :jia_yanqing
        // $Date : 2012/5/28 15:05$
        // [BUG]0006659 ADD BEGIN
        // 病人原病区编码
//        medicationOrder.setWardsId(medicationOrderDto.getFormerWard());
        // $Author :jia_yanqing
        // $Date : 2012/10/25 15:05$
        // [BUG]0010765 ADD BEGIN
        // 病人原病区名称
//        medicationOrder.setWardName(medicationOrderDto.getFormerWardName());
        // [BUG]0010765 ADD END

        // 医生确认时间
        medicationOrder.setDoctorConfirmTime(DateUtils.stringToDate(medicationOrderDto.getDoctorConfirmTime()));
        // 医生确认人编码
        medicationOrder.setDoctorConfirmPerson(medicationOrderDto.getDoctorConfirmPerson());
        // 医生确认人姓名
        medicationOrder.setDoctorConfirmPersonName(medicationOrderDto.getDoctorConfirmPersonName());
        // 医嘱开始时间
//        medicationOrder.setOrderStartTime(DateUtils.stringToDate(medicationOrderDto.getOrderStartTime()));
        if(medicationOrderDto.getOrderStartTime() != null){
        	medicationOrder.setOrderStartTime(DateUtils.stringToDate(medicationOrderDto.getOrderStartTime()));
        }
        // 医嘱结束时间
        medicationOrder.setOrderEndTime(DateUtils.stringToDate(medicationOrderDto.getOrderEndTime()));
        // [BUG]0006659 ADD END
        // 医嘱序号
//        medicationOrder.setOrderSeqnum(StringUtils.strToBigDecimal(medicationOrderDto.getOrderSeqnum(),"医嘱序号"));
        return medicationOrder;
    }

    /**
     * 获取需要更新的用药医嘱记录
     * @param medicationOrderDto
     * @return 当前待更新的用药医嘱记录
     */
    private MedicationOrder getMedicationOrder(
            MedicationOrderDto medicationOrderDto)
    {
        MedicationOrder result = new MedicationOrder();
        for (MedicationOrder medicationOrder : medicationOrdersForUpdate)
        {
            // 找到当前用药医嘱消息条目对应的数据库中的待更新用药医嘱
            if (isSameMedicationOrder(medicationOrder, medicationOrderDto))
            {
                // 添加中药配方集合到待删除集合（如用药医嘱类型为中草药）
//                if (isHerbal(medicationOrderDto))
//                    setHerbalFormulaDeleteList(medicationOrder.getOrderSn());
                result = medicationOrder;
                break;
            }
        }

        return result;
    }

    /**
     * 更新用药医嘱时，如为中草药类型，将原有中草药配方加入待删除集合
     * @param medicationOrderBatch
     */
    private void setHerbalFormulaDeleteList(BigDecimal orderSn)
    {
        // 添加搜索条件（用药医嘱内部序列号）
        Map<String, Object> condition = new HashMap<String, Object>();
        condition.put("orderSn", orderSn);
        // 查找到中药配方集合
        List<Object> herbalFormulas = commonService.selectByCondition(
                HerbalFormula.class, condition);
        // 添加到待删除类表中
        for (Object object : herbalFormulas)
        {
            herbalFormulaDeleteList.add((HerbalFormula) object);
        }
    }

    /**
     * 判断消息中一条用药医嘱记录是否为数据库中的这一条用药医嘱记录
     * @param medicationOrder 数据库中的一条用药医嘱记录
     * @param medicationOrderBatch 消息中的一条用药医嘱记录
     * @return 是返回true，否则返回false
     */
    public boolean isSameMedicationOrder(MedicationOrder medicationOrder,
            MedicationOrderDto medicationOrderDto)
    {
        return medicationOrder.getOrderLid().equals(
                medicationOrderDto.getOrderLid())
//                && medicationOrder.getOrderSeqnum().toString().equals(medicationOrderDto.getOrderSeqnum())
                ;
    }

    /**
     * 将用药医嘱中的中草药配方加入待保存集合
     * @param medicationOrderDto 消息中的用药医嘱记录
     * @return 返回中药配方实体集合
     */
//    private List<HerbalFormula> getHerbalFormulas(
//            MedicationOrderDto medicationOrderDto)
//    {
//        // 返回的结果集
//        List<HerbalFormula> result = new ArrayList<HerbalFormula>();
//
//        // 用药医嘱中的中草药配方信息
//        List<HerbalFormulaDto> herbalFormulaDtos = medicationOrderDto.getHerbalFormula();
//        for (HerbalFormulaDto herbalFormulaDto : herbalFormulaDtos)
//        {
//            HerbalFormula herbalFormula = new HerbalFormula();
//            // 药物名称代码
//            herbalFormula.setHerbCode(herbalFormulaDto.getHerbCode());
//
//            // $Author :tong_meng
//            // $Date : 2012/6/26 10:33$
//            // [BUG]0010871 MODIFY BEGIN
//            CodeDrug codeDrug = prescriptionService.selectCodeDrug(
//                    herbalFormulaDto.getHerbCode(),
//                    herbalFormulaDto.getSerialNo());
//            CodeMedicalName cmn;
//            if (codeDrug != null)
//            {
//                cmn = dictionaryService.selectCodeMedicalName(codeDrug.getDrugId());
//                if (cmn != null)
//                    // 药物通用名称
//                    herbalFormula.setApprovedHerbName(cmn.getName());
//                // $Author:wei_peng
//                // $Date:2012/11/22 11:18
//                // [BUG]0011805 ADD BEGIN
//                // 药物名称
//                herbalFormula.setHerbName(codeDrug.getName());
//                // [BUG]0011805 ADD END
//            }
//            // [BUG]0010871 MODIFY END
//
//            // 药物数量
//            herbalFormula.setQuantity(StringUtils.strToBigDecimal(
//                    herbalFormulaDto.getQuantity(), "药物数量"));
//            // 药物数量单位
//            herbalFormula.setUnit(herbalFormulaDto.getUnit());
//            // 是否与付数无关标记
//            herbalFormula.setNumUnconcernedFlag(StringUtils.getConversionValue(herbalFormulaDto.getNumUnconcernedFlag()));
//
//            // $Author :tong_meng
//            // $Date : 2012/6/26 10:33$
//            // [BUG]0007418 MODIFY BEGIN
//            // $Author :tong_meng
//            // $Date : 2012/6/26 10:33$
//            // [BUG]0007418 MODIFY BEGIN
//            // 煎法编码
//            herbalFormula.setDecoctionMethodCode(herbalFormulaDto.getDecoctionMethodCode());
//            // [BUG]0007418 MODIFY END
//
//            // $Author :tong_meng
//            // $Date : 2012/6/26 10:33$
//            // [BUG]0007418 MODIFY BEGIN
//            // 煎法名称
//            herbalFormula.setDecoctionMethodName(herbalFormulaDto.getDecoctionMethodName());
//            // [BUG]0007418 MODIFY END
//
//            // $Author :jia_yanqing
//            // $Date : 2012/5/25 10:55$
//            // [BUG]0006659 ADD BEGIN
//            // 中药包装序号
//            herbalFormula.setSerialNo(herbalFormulaDto.getSerialNo());
//            // [BUG]0006659 ADD BEGIN
//
//            // 创建时间
//            herbalFormula.setCreateTime(systemTime);
//            // 更新时间
//            herbalFormula.setUpdateTime(systemTime);
//            // 删除标志
//            herbalFormula.setDeleteFlag(Constants.NO_DELETE_FLAG);
//            // 更新回数
//            herbalFormula.setUpdateCount(Constants.INSERT_UPDATE_COUNT);
//
//            herbalFormula.setCreateby(serviceId);
//
//            result.add(herbalFormula);
//        }
//
//        return result;
//    }

    /**
     * 一条用药医嘱的医嘱类别是否为中草药
     * @param medicationOrderDto 消息里的用药医嘱
     * @return 是返回true，否则返回false
     */
    public boolean isHerbal(MedicationOrderDto medicationOrderDto)
    {
        // $Author :jia_yanqing
        // $Date : 2012/5/25 10:55$
        // [BUG]0006659 MODIFY BEGIN
        // 根据医嘱类型代码判断是否中草药
        return medicationOrderDto.getOrderType().equals(
                Constants.HERBAL_MEDICINE_CLASS);
        // [BUG]0006659 MODIFY END
    }

}
