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
import com.yly.cdr.entity.CodeDrug;
import com.yly.cdr.entity.CodeMedicalName;
import com.yly.cdr.entity.DispensingRecord;
import com.yly.cdr.entity.HerbalFormula;
import com.yly.cdr.entity.MedOrderDispensingRecord;
import com.yly.cdr.entity.MedicalVisit;
import com.yly.cdr.entity.MedicationOrder;
import com.yly.cdr.entity.NursingOperation;
import com.yly.cdr.entity.PatientDoctor;
import com.yly.cdr.entity.Prescription;
import com.yly.cdr.hl7.dto.HerbalFormulaDto;
import com.yly.cdr.hl7.dto.MedicationOrderDto;
import com.yly.cdr.hl7.dto.porxin010370uv01.PORXIN010370UV01;
import com.yly.cdr.hl7.dto.porxin010370uv01.PrescriptionDto;
import com.yly.cdr.service.CommonService;
import com.yly.cdr.service.DictionaryService;
import com.yly.cdr.service.PrescriptionService;
import com.yly.cdr.util.DataMigrationUtils;
import com.yly.cdr.util.DateUtils;
import com.yly.cdr.util.DictionaryUtils;
import com.yly.cdr.util.StringUtils;

/**
 * 处方数据接入writer
 * @author jin_peng
 * @version 1.0f
 */
@Component(value = "porxin010370uv01Writer")
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class PORXIN010370UV01Writer implements BusinessWriter<PORXIN010370UV01>
{

    // Author :jia_yanqing
    // Date : 2012/10/15 10:00
    // [BUG]0010351+0010274 MODIFY BEGIN
    private static final Logger logger = LoggerFactory.getLogger(PORXIN010370UV01Writer.class);

    private static final Logger loggerBS302 = LoggerFactory.getLogger("BS302");

    // $Author :jia_yanqing
    // $Date : 2012/8/29 14:55$
    // [BUG]0009250 MODIFY BEGIN
    // 该常量被移入常量类Constants管理 [BUG]0009330
    // [BUG]0009250 MODIFY BEGIN

    // 医嘱序列名称
    private static final String ORDER_SEQUENCE = "SEQ_ORDER";

    // 处方序列名称
    private static final String PRESCRIPTION_SEQUENCE = "SEQ_PRESCRIPTION";

    // 消息对应处方集合
    private List<PrescriptionDto> prescriptionList = new ArrayList<PrescriptionDto>();

    // 待添加处方类表
    private List<Prescription> prescriptionAddList = new ArrayList<Prescription>();

    // 处方对应用药医嘱插入对象集合
    private List<MedicationOrder> medicationOrderAddList = new ArrayList<MedicationOrder>();

    // 用药医嘱药物类别为中草药时的中药配方插入对象集合
    private List<HerbalFormula> herbalFormulaAddList = new ArrayList<HerbalFormula>();

    // 消息中的中草药对象集合
    private List<HerbalFormulaDto> herbalFormulaListForValidate = new ArrayList<HerbalFormulaDto>();

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

    // 就诊内部序列号
    private BigDecimal visitSn;

    // 患者内部序列号
    private BigDecimal patientSn;

    // 医嘱内部序列号
    private BigDecimal orderSn;

    // 获取域ID
    private String patientDomain;

    // 获取就诊次数
    private String visitTimes;

    // 获取患者本地ID
    private String patientLid;

    // 获取系统当前时间
    private Date systemTime;
    
    // 就诊类别
    private String visitType;
    // 就诊流水号
    private String visitOrdNo;

    // 同一消息将父子医嘱同时传来时，将这种父子医嘱相关关系保存在map中
    private Map<BigDecimal, String> medicationOrderMap = new HashMap<BigDecimal, String>();

    @Autowired
    private CommonService commonService;

    @Autowired
    private PrescriptionService prescriptionService;
    
    // $Author :tong_meng
    // $Date : 2012/11/07 11:00$
    // [BUG]0010871 ADD BEGIN
    @Autowired
    private DictionaryService dictionaryService;

    // [BUG]0010871 ADD END

    @Autowired
    private CommonWriterUtils commonWriterUtils;
    
    private String serviceId; 
    // $Author :chang_xuewen
    // $Date : 2013/12/05 11:00$
    // [BUG]0040271 ADD BEGIN
    private String orgCode;
    private String orgName;
    // [BUG]0040271 ADD END
    
    /*
     * Author: yu_yzh
     * Date: 2015/11/06 11:46
     * ADD BEGIN
     * */
    private List<Prescription> deletePrescriptionList;
    private List<MedicationOrder> deleteMedicationOrderList;
    private List<NursingOperation> nursingOperationList;
    // 为dto和数据库中实体的映射map，用于修改医嘱状态等内容
    private Map<MedicationOrderDto, MedicationOrder> dtoEntityMap;
    private List<Prescription> addPrescriptionList;
    private List<Prescription> updatePrescriptionList;
    private List<MedicationOrder> updateMedicationOrderList;
    private List<MedicationOrder> addMedicationOrderList;
    // ADD BEGIN
    
    /**
     * 处方消息初始化参数设置
     */
    public PORXIN010370UV01Writer()
    {
        systemTime = DateUtils.getSystemTime();
    }

    /**
     * 数据分场景非空校验  当前药品类别为中草药时需验证相应一些字段不为空
     * @param porxin010370uv01 处方信息
     */
    public boolean validate(PORXIN010370UV01 porxin010370uv01)
    {
    	// $Author :chang_xuewen
        // $Date : 2014/1/22 10:30$
        // [BUG]0042086 MODIFY BEGIN
    	// $Author :chang_xuewen
        // $Date : 2013/12/05 11:00$
        // [BUG]0040271 ADD BEGIN
        /*orgCode = porxin010370uv01.getOrgCode();
        orgName = porxin010370uv01.getOrgName();*/
    	
    	// 港大医疗机构编码从配置文件取
    	orgCode=Constants.HOSPITAL_CODE;
    	orgName=Constants.HOSPITAL_NAME;
    	/*if(!porxin010370uv01.getOrgCode().equals(porxin010370uv01.getMessage().getOrgCode())){
    		loggerBS302.error(
                    "Message:{}, validate:{}",
                    porxin010370uv01.toString(),
                    "消息头与消息体中的医疗机构代码不一致：头：OrgCode = " 
                    + porxin010370uv01.getMessage().getOrgCode() 
                    + " ，体：OrgCode ="
                    + porxin010370uv01.getOrgCode());
        	return false;
        }*/
/*        if(StringUtils.isEmpty(porxin010370uv01.getOrgCode())){
        	// $Author :yang_mingjie
        	// $Date : 2014/06/26 10:09$
        	// [BUG]0045630 MODIFY BEGIN 
        	orgCode=Constants.HOSPITAL_CODE;
        	orgName=Constants.HOSPITAL_NAME;
        	//[BUG]0045630 MODIFY END
        } */
    	// [BUG]0040271 ADD END
        // [BUG]0042086 MODIFY END
        boolean flag = true;
        // Author :jia_yanqing
        // Date : 2012/10/16 16:00
        // [BUG]10422 MODIFY BEGIN
        // 处方消息中处方记录集合
        prescriptionList = porxin010370uv01.getPrescriptions();
        if (isDeleteMessage(porxin010370uv01))
        {
            flag = validateHasCharge(porxin010370uv01);
        }

        else if (isNewMessage(porxin010370uv01))
        {
            flag = validateNoCharge(porxin010370uv01);
        }
        // [BUG]10422 MODIFY END
        else if (isRenewMessage(porxin010370uv01))
        {
            flag = validateHasCharge(porxin010370uv01);
            flag = validateNoCharge(porxin010370uv01);
        }       
        	
        return flag;
    }

    /**
     * 验证已收费处方的非空项
     * @param preDto
     * @return
     */
    public boolean validateHasCharge(PORXIN010370UV01 message)
    {
        boolean flag = true;
        if (prescriptionList != null && prescriptionList.size() != 0)
        {
            for (PrescriptionDto preDto : prescriptionList)
            {
                // 判断为已收费处方
                if (preDto.getPaidFlag() != null
                    && !"".equals(preDto.getPaidFlag()))
                {
                    // 验证已收费处方中的非空项（处方号，处方类别）
                    if (!StringUtils.isNotNullAll(preDto.getPrescriptionNo(),
                            preDto.getPrescriptionClass()))
                    {
                        loggerBS302.error(
                                "Message:{},validate{}",
                                message.toString(),
                                "非空字段验证未通过! 处方号:PrescriptionNo ="
                                    + preDto.getPrescriptionNo()
                                    + "处方类别:PrescriptionClass = "
                                    + preDto.getPrescriptionClass());
                        flag = false;
                    }
                }
            }
        }
        return flag;
    }

    public boolean validateNoCharge(PORXIN010370UV01 message)
    {
        boolean flag = true;
        // 所有处方消息中全部用药医嘱记录集合
        List<MedicationOrderDto> medicineOrderList = new ArrayList<MedicationOrderDto>();
        if (prescriptionList != null && prescriptionList.size() != 0)
        {
            for (PrescriptionDto preDto : prescriptionList)
            {
                // 判断为未收费处方
                if (preDto.getPaidFlag() == null
                    || "".equals(preDto.getPaidFlag()))
                {
                    // $Author :jia_yanqing
                    // $Date : 2012/10/9 15:55$
                    // [BUG]0010247 MODIFY BEGIN
                    // 验证未收费处方中的非空项（处方号，处方类别，开方医师ID，开方医师名称，开方科室ID，开方科室名称，开方时间）
                	/*
                	 * Author: yu_yzh
                	 * Date: 2015/10/22 09:50
                	 * 港大处方类别，开方医生名称，开方科室名称非空校验取消
                	 * */
                	if (!StringUtils.isNotNullAll(preDto.getPrescriptionNo(),
//                            preDto.getPrescriptionClass(),
                            preDto.getPrescriptionDoctorId(),
                            /*preDto.getPrescriptionDoctorName(),*/
                            preDto.getPrescriptionDept(),
                            /*preDto.getPrescriptionDeptName(),*/
                            preDto.getPrescriptionTime()))
                    {
                        loggerBS302.error(
                                "Message:{},validate{}",
                                message.toString(),
                                "非空字段验证未通过! 处方号:PrescriptionNo ="
                                    + preDto.getPrescriptionNo()
/*                                    + "处方类别:PrescriptionClass = "
                                    + preDto.getPrescriptionClass()*/
                                    + "开方医师ID:PrescriptionDoctorId = "
                                    + preDto.getPrescriptionDoctorId()
                                    /*+ "; 开方医师名称:PrescriptionDoctorName = "
                                    + preDto.getPrescriptionDoctorName()*/
                                    + ";开方科室ID:PrescriptionDept = "
                                    + preDto.getPrescriptionDept()
                                    /*+ "; 开方科室名称:PrescriptionDeptName = "
                                    + preDto.getPrescriptionDeptName()*/
                                    + ";开方时间:PrescriptionTime = "
                                    + preDto.getPrescriptionTime());
                        flag = false;
                    }
                    // [BUG]0010247 MODIFY END
                    // 取得处方消息中的用药医嘱对象集合
                    if (preDto.getMedicineOrder() != null
                        && preDto.getMedicineOrder().size() != 0)
                    {
                        medicineOrderList.addAll(preDto.getMedicineOrder());
                    }
                }
            }
        }
        // 取得药物类别，主要取得类别为中草药
        if (medicineOrderList != null && medicineOrderList.size() != 0)
        {
            for (MedicationOrderDto medicationOrderDto : medicineOrderList)
            {
                // 验证医嘱中医嘱号，药物标识，处方类型编码，频次编码，总用量，总用量单位
            	/*
            	 * Author: yu_yzh
            	 * Date: 2015/10/22 09:50
            	 * 港大处方类别非空校验取消
            	 * */
                if (!StringUtils.isNotNullAll(medicationOrderDto.getOrderLid(),
                        medicationOrderDto.getDrugType(),
//                        medicationOrderDto.getPrescriptionType(),
                        medicationOrderDto.getMedicineFrenquency()/*,
                        medicationOrderDto.getTotalDosage(),
                        medicationOrderDto.getTotalDosageUnit()*/))
                {
                    loggerBS302.error(
                            "Message:{},validate{}",
                            message.toString(),
                            "非空字段验证未通过! 医嘱号:OrderLid = "
                                + medicationOrderDto.getOrderLid()
                                + "; 药物标识:DrugType = "
                                + medicationOrderDto.getDrugType()
                                /*+ ";处方类型编码:PrescriptionType = "
                                + medicationOrderDto.getPrescriptionType()*/
                                + "; 频次编码:MedicineFrenquency = "
                                + medicationOrderDto.getMedicineFrenquency()
                                /*+ ";总用量:TotalDosage = "
                                + medicationOrderDto.getTotalDosage()
                                + ";总用量单位:TotalDosageUnit = "
                                + medicationOrderDto.getTotalDosageUnit()*/);
                    flag = false;
                }
                if (isHerbalMedicationOrder(medicationOrderDto))
                {
                    // 如果是草药
                    // 校验医嘱中执行科室编码，执行科室名称
                    if (!StringUtils.isNotNullAll(
                            medicationOrderDto.getExecDept(),
                            medicationOrderDto.getExecDeptName()))
                    {
                        loggerBS302.error("Message:{},validate{}",
                                message.toString(),
                                "非空字段验证未通过! 执行科室编码:ExecDept = "
                                    + medicationOrderDto.getExecDept()
                                    + "; 执行科室名称:ExecDeptName = "
                                    + medicationOrderDto.getExecDeptName());
                        flag = false;
                    }
                    if (medicationOrderDto.getHerbalFormula() != null
                        && medicationOrderDto.getHerbalFormula().size() != 0)
                    {
                        herbalFormulaListForValidate.addAll(medicationOrderDto.getHerbalFormula());
                    }

                }
                else
                {
                    // 如果是西药/成药
                    // 校验医嘱中天数，给药途径编码，次剂量，次剂量单位，领药量，领药量单位，药品编码，包装序号
                    /*
                     * Author: yu_yzh
                     * Date: 2015/10/22 09:53
                     * 港大药品领药量，灵药单位，包装序号非空校验取消
                     * */
                	if (!StringUtils.isNotNullAll(
                            medicationOrderDto.getRepeatNumber(),
                            medicationOrderDto.getRouteCode(),
                            medicationOrderDto.getDosage(),
                            medicationOrderDto.getDosageUnit(),
                            /*medicationOrderDto.getObtain(),
                            medicationOrderDto.getObtainUnit(),*/
                            medicationOrderDto.getDrugCode()/*,
                            medicationOrderDto.getSerialNo()*/))
                    {
                        loggerBS302.error(
                                "Message:{},validate{}",
                                message.toString(),
                                "非空字段验证未通过!天数:RepeatNumber = "
                                    + medicationOrderDto.getRepeatNumber()
                                    + "; 给药途径编码:RouteCode = "
                                    + medicationOrderDto.getRouteCode()
                                    + "; 次剂量:Dosage = "
                                    + medicationOrderDto.getDosage()
                                    + "; 次剂量单位:DosageUnit = "
                                    + medicationOrderDto.getDosageUnit()
                                    /*+ "; 领药量:Obtain = "
                                    + medicationOrderDto.getObtain()
                                    + "; 领药量单位:ObtainUnit = "
                                    + medicationOrderDto.getObtainUnit()*/
                                    + "; 药品编码:DrugCode = "
                                    + medicationOrderDto.getDrugCode()
                                    /*+ "; 包装序号:SerialNo = "
                                    + medicationOrderDto.getSerialNo()*/);
                        flag = false;
                    }
                }
            }
        }
        if (herbalFormulaListForValidate != null
            && herbalFormulaListForValidate.size() != 0)
        {
            // 判断中草药配方中每一味要中的必填字段是否为空
            for (HerbalFormulaDto herbalFormula : herbalFormulaListForValidate)
            {
                // $Author:wu_biao
                // $Date:2012/10/25 15:10
                // $[BUG]10749 MODIFY BEGIN
                // $Author:wei_peng
                // $Date:2012/8/30 16:50
                // $[BUG]0009256 MODIFY BEGIN
                // 从字典中获取草药的名称
                herbalFormula.setHerbName(DictionaryCache.getDictionary(
                        Constants.DOMAIN_DRUG_DICTIONARY).get(
                        herbalFormula.getHerbCode()
                            + herbalFormula.getSerialNo()));
                // $[BUG]0009256 MODIFY END
                // $[BUG]10749 MODIFY END
                // $Author:jia_yanqing
                // $Date:2012/9/3 16:50
                // $[BUG]0008782 MODIFY BEGIN
                // 校验处方中中草药配方的非空项（数量，数量单位，中药编码，包装序号）
                if (!StringUtils.isNotNullAll(herbalFormula.getQuantity(),
                        herbalFormula.getUnit(), herbalFormula.getHerbCode(),
                        herbalFormula.getSerialNo()))
                {
                    loggerBS302.error(
                            "Message:{},validate{}",
                            message.toString(),
                            "处方类别为中草药时中药配方非空字段验证未通过! 数量:quantity = "
                                + herbalFormula.getQuantity()
                                + "; 数量单位:unit = " + herbalFormula.getUnit()
                                + "; 中药编码:HerbCode = "
                                + herbalFormula.getHerbCode()
                                + "; 包装序号:SerialNo = "
                                + herbalFormula.getSerialNo());
                    flag = false;
                }
                // $[BUG]0008782 MODIFY END
            }
        }
        return flag;
    }

    /**
     * 有关联时进行业务检验
     * @param porxin010370uv01 处方信息
     */
    @Override
    public boolean checkDependency(PORXIN010370UV01 porxin010370uv01,
            boolean flag)
    {
        // 获取域ID
        patientDomain = porxin010370uv01.getPatientDomain();

        // 获取就诊次数
        visitTimes = porxin010370uv01.getVisitTime();

        // $Author :jia_yanqing
        // $Date : 2012/5/25 16:55$
        // [BUG]0006657 MODIFY BEGIN
        // 获取患者本地ID
        patientLid = porxin010370uv01.getPatientLid();
        // [BUG]0006657 MODIFY END

        visitType = porxin010370uv01.getVisitType();
        
        visitOrdNo = porxin010370uv01.getVisitOrdNo();
        
        // $Author :chang_xuewen
        // $Date : 2014/2/10 10:30$
        // [BUG]0042178 MODIFY BEGIN
        // 获得处方消息对应的就诊对象
        MedicalVisit medicalVisit = commonService.mediVisit(patientDomain,
                patientLid, visitTimes, orgCode, visitOrdNo, visitType);
        
        // 新增处方时判断是否已找到相应的就诊记录
        if (medicalVisit == null)
        {
            logger.error("MessageId:{}此条处方消息未找到相应就诊记录: {}",
                    porxin010370uv01.getMessage().getId(), " patientLid = "
                        + patientLid + "; patientDomain = " + patientDomain
                        + "; visitTimes = " + visitTimes
                        + "; orgCode = " + orgCode
                        + "; visitType = " + visitType
                        + "; visitOrdNo = " + visitOrdNo);
            if (flag)
            {
                loggerBS302.error("Message:{},checkDependency:{}",
                        porxin010370uv01.toString(),
                        "此条处方消息未找到相应就诊记录: patientLid = " + patientLid
                            + "; patientDomain = " + patientDomain
                            + "; visitTimes = " + visitTimes
                            + "; orgCode = " + orgCode
                            + "; visitType = " + visitType
                            + "; visitOrdNo = " + visitOrdNo);
            }
            return false;
        }
        // [BUG]0042178 MODIFY END
        // 获取处方新增或更新时的一些就诊信息
        visitSn = medicalVisit.getVisitSn(); // 就诊内部序列号
        patientSn = medicalVisit.getPatientSn(); // 患者内部序列号

        if (isNewMessage(porxin010370uv01)
            || isRenewMessage(porxin010370uv01))
        {
            // $Author:jia_yanqing
            // $Date:2012/8/31 18:30$
            // [BUG]0009310 ADD BEIGN
            for (PrescriptionDto preDto : prescriptionList)
            {
                if (preDto != null)
                {
                    if (preDto.getPaidFlag() == null
                        || "".equals(preDto.getPaidFlag()))
                    {
                        List<MedicationOrderDto> medicationOrderItemList = preDto.getMedicineOrder();
                        MedicationOrder medicationOrder;
                        String parentOrderLid = null;
                        // 检查与父医嘱的关联关系
                        for (int i = 0; i < medicationOrderItemList.size(); i++)
                        {
                            parentOrderLid = medicationOrderItemList.get(i).getParentOrderId();
                            if (parentOrderLid != null
                                && !"".equals(parentOrderLid))
                            {
                            	// $Author :chang_xuewen
                                // $Date : 2014/2/10 10:30$
                                // [BUG]0042178 MODIFY BEGIN
                                medicationOrder = commonService.checkParentOrder(
                                        patientDomain, patientLid,
                                        parentOrderLid,orgCode);
                                // [BUG]0042178 MODIFY END
                                if (medicationOrder == null)
                                {
                                    // $Author:tong_meng
                                    // $Date:2012/12/19 14:00
                                    // $[BUG]0012632 MODIFY BEGIN
                                    String tempFlag = "false";
                                    for (MedicationOrderDto medicationOrderDto : medicationOrderItemList)
                                    {
                                        if (parentOrderLid.equals(medicationOrderDto.getOrderLid()))
                                        {
                                            tempFlag = "true";
                                            break;
                                        }
                                    }
                                    if (!"true".equals(tempFlag))
                                    {
                                        logger.error(
                                                "MessageId:{}用药医嘱消息新增场合,关联的父医嘱不存在,父医嘱号 {}",
                                                porxin010370uv01.getMessage().getId(),
                                                parentOrderLid);
                                        if (flag)
                                        {
                                            loggerBS302.error(
                                                    "Message:{},checkDependency:{}",
                                                    porxin010370uv01.toString(),
                                                    "用药医嘱消息新增场合,关联的父医嘱不存在,父医嘱号 "
                                                        + parentOrderLid
                                                        + "域ID" + patientDomain
                                                        + "患者本地ID" + patientLid);
                                        }
                                        return false;
                                    }
                                    // $[BUG]0012632 MODIFY BEGIN
                                }
                            }
                        }
                    }
                }
            }
            // [BUG]0009310 ADD END

            // $Author:wei_peng
            // $Date:2012/8/30 16:50
            // $[BUG]0009256 MODIFY BEGIN
            for (HerbalFormulaDto herbalFormula : herbalFormulaListForValidate)
            {
                if (StringUtils.isEmpty(herbalFormula.getHerbName()))
                {
                    logger.error(
                            "MessageId:{};处方中的药物类别为中草药时，配方非空字段验证未通过：herbName = "
                                + herbalFormula.getHerbName() + ";",
                            porxin010370uv01.getMessage().getId());
                    if (flag)
                    {
                        loggerBS302.error("Message:{},checkDependency:{}",
                                porxin010370uv01.toString(),
                                "处方中的药物类别为中草药时，配方非空字段验证未通过：herbName = "
                                    + herbalFormula.getHerbName() + ";");
                    }
                    return false;
                }
            }
            // $[BUG]0009256 MODIFY END
        }

        if(isUpdateMessage(porxin010370uv01)){
			for (PrescriptionDto preDto : prescriptionList) {
				Prescription pre = prescriptionService.selectPrescriptionsByPrescriptionNo(
						preDto.getPrescriptionNo(), visitSn.toString(), patientSn.toString());
				if(pre == null) return false;
				else {
					List<MedicationOrder> medOrders = prescriptionService.
							selectMedicationOrdersByPrescriptionSn(pre.getPrescriptionSn().toString(), visitSn.toString());
					for(MedicationOrderDto dto : preDto.getMedicineOrder()){
						boolean exist = false;
						for(MedicationOrder medOrder : medOrders){
							if(medOrder.getOrderLid().equals(dto.getOrderLid())){
								exist = true;
								break;
							}
						}
						if(!exist) return false;
					}
				}
			}
        }
       
        if(isDeleteMessage(porxin010370uv01)){
        	List<String> result = checkDeleteList(porxin010370uv01);
        	if(!result.isEmpty()){
        		StringBuilder sb = new StringBuilder();
        		for(String s : result){
        			sb.append(s);
        			sb.append("\r\n");
        		}
        		logger.error(sb.toString());
        	}
        	return false;
        }
        return true;
    }

    /**
     * 校验成功后把取到的相应处方数据进行保存或更新操作
     * @param porxin010370uv01 处方信息
     */
    // Author :jia_yanqing
    // Date : 2013/3/1 11:00
    // [BUG]14140 MODIFY BEGIN
    @Transactional
    public void saveOrUpdate(PORXIN010370UV01 porxin010370uv01)
    {
        serviceId = porxin010370uv01.getMessage().getServiceId();
        if(isDeleteMessage(porxin010370uv01)){
        	setDeleteList();
        	commonService.updateList(deletePrescriptionList);
        	commonService.updateList(deleteMedicationOrderList);
        }
        if(isUpdateMessage(porxin010370uv01) 
        		|| isNewMessage(porxin010370uv01)){
        	setAddAndUpdateList(porxin010370uv01);
        	commonService.saveList(addPrescriptionList);
        	commonService.updateList(updatePrescriptionList);
        	commonService.saveList(addMedicationOrderList);
        	commonService.updateList(updateMedicationOrderList);
        }
        
        /*
         * Author: yu_yzh
         * Date: 2015/11/08 19:05
         * DELETE BEGIN
         * */ 
        /*// 数据库中待删除的处方集合
        List<Object> prescriptionDeleteList = null;
        // 数据库中待删除的药物医嘱集合
        List<Object> medicationOrderDeleteList = null;
        // 数据库中待删除的药物医嘱集合
        List<Object> herbalFormulaDeleteList = null;
        if (isDeleteMessage(porxin010370uv01)
            || isNewMessage(porxin010370uv01))
        {
            // 获取数据库中待删除的处方集合
            prescriptionDeleteList = getPrescriptionForDeleteFromDB(porxin010370uv01);
            // 获取数据库中待删除的药物医嘱集合
            medicationOrderDeleteList = getMedicationOrderForDeleteFromDB(prescriptionDeleteList);
            // 获取数据库中待删除的药物医嘱集合
            herbalFormulaDeleteList = getHerbalFormulaForDeleteFromDB(medicationOrderDeleteList);
        }

        // $Author:wei_peng
        // $Date:2013/4/7 18:11
        // [BUG]0014904 ADD BEGIN
        // 当中药配方不为空时进行删除，也就是药物类别为中草药时进行的操作
        commonService.deleteList(herbalFormulaDeleteList);
        // 当用药医嘱删除集合不为空时删除用药医嘱
        commonService.deleteList(medicationOrderDeleteList);
        // 当处方删除集合不为空是进行删除
        commonService.deleteList(prescriptionDeleteList);
        // [BUG]0014904 ADD END

        if (isNewMessage(porxin010370uv01))
        {
            // 获取需要新增的处方集合及其对应的药物医嘱集合及其对应的中药配方集合
            setPrescription(porxin010370uv01);
            // $Author:wei_peng
            // $Date:2012/11/06 13:44
            // [BUG]0011030 ADD BEGIN
            // 新增时添加患者医生记录集合
            for (PatientDoctor patientDoctor : patientDoctorList)
            {
                commonService.save(patientDoctor);
            }
            // [BUG]0011030 ADD END
        }
        // 被退费的处方列表
        List<Prescription> prescriptionListForReturnFee = null;
        List<MedicationOrder> medicationListForReturnFee = null;
        List<HerbalFormula> herbalFormulaListForReturnFee = null;
        List<MedOrderDispensingRecord> medOrderDispensingRecordListForReturnFee = null;
        List<DispensingRecord> dispensingRecordListForReturnFee = null;
        if (isRenewMessage(porxin010370uv01))
        {
            // 获取需要新增的处方集合及其对应的药物医嘱集合及其对应的中药配方集合
            setPrescription(porxin010370uv01);
            // 获取已退费的处方列表
            prescriptionListForReturnFee = getReturnFeePrescription(prescriptionList);
            // 获取已退费处方关联的药物医嘱
            medicationListForReturnFee = getMedicationOrderForReturnFee(prescriptionListForReturnFee);
            // 获取已退费处方关联的药物医嘱关联的中药配方
            herbalFormulaListForReturnFee = getHerbalFormulaForReturnFee(medicationListForReturnFee);
            // 获取已退费处方关联的药物医嘱关联的用药医嘱与发药记录
            medOrderDispensingRecordListForReturnFee = getMedOrderDispensingRecordForReturnFee(medicationListForReturnFee);
            // 获取已退费处方关联的药物医嘱关联用药医嘱与发药记录关联的发药记录
            dispensingRecordListForReturnFee = getDispensingRecordForReturnFee(medOrderDispensingRecordListForReturnFee);
            // $Author:wei_peng
            // $Date:2012/11/06 13:44
            // [BUG]0011030 ADD BEGIN
            // 新增时添加患者医生记录集合
            for (PatientDoctor patientDoctor : patientDoctorList)
            {
                commonService.save(patientDoctor);
            }
            // [BUG]0011030 ADD END
        }

        // Author :tong_meng
        // Date : 2013/3/12 18:00
        // [BUG]0014522 MODIFY BEGIN
        // 当父子医嘱来自同一消息时，更新子医嘱中父医嘱内部序列号
        setParentOrderSn();
        // [BUG]0014522 MODIFY END

        // 根据triggerEvent判断新增或更新处方及其相关联对应的用药医嘱和中药配方
        prescriptionService.saveOrDeletePrescription(prescriptionAddList,
                medicationOrderAddList, herbalFormulaAddList,
                prescriptionListForReturnFee, medicationListForReturnFee,
                herbalFormulaListForReturnFee,
                medOrderDispensingRecordListForReturnFee,
                dispensingRecordListForReturnFee, systemTime);*/
        // DELETE END
    }

    // [BUG]14140 MODIFY END

    // Author :jia_yanqing
    // Date : 2013/3/1 11:00
    // [BUG]14140 ADD BEGIN
    /**
     * 获取被退费的处方列表
     * @param prescriptionList
     * @return
     */
    private List<Prescription> getReturnFeePrescription(
            List<PrescriptionDto> prescriptionList)
    {
        List<Prescription> prescriptionListForReturnFee = new ArrayList<Prescription>();
        Map<String, Object> prescriptionMap = new HashMap<String, Object>();
        if (prescriptionList != null && prescriptionList.size() != 0)
        {
            for (PrescriptionDto preDto : prescriptionList)
            {
                Prescription prescription = null;
                // 判断为已收费处方
                if (preDto.getPaidFlag() != null
                    && !"".equals(preDto.getPaidFlag()))
                {
                    prescriptionMap.put("patientDomain", patientDomain);
                    prescriptionMap.put("patientLid", patientLid);
                    prescriptionMap.put("prescriptionNo",
                            preDto.getPrescriptionNo());
                    prescriptionMap.put("deleteFlag", Constants.NO_DELETE_FLAG);
                    prescriptionMap.put("orgCode", orgCode);
                    // 根据相应条件获取数据库中对应处方记录
                    List<Object> list = commonService.selectByCondition(
                            Prescription.class, prescriptionMap);

                    if (list != null && !list.isEmpty())
                    {
                        prescription = (Prescription) list.get(0);
                        prescriptionListForReturnFee.add(prescription);
                    }
                }
            }
        }
        return prescriptionListForReturnFee;
    }

    /**
     * 获取已退费处方的关联医嘱并设置更新时间，删除标志，收费状态为已退费
     * @param prescriptionListForReturnFee
     * @return
     */
    private List<MedicationOrder> getMedicationOrderForReturnFee(
            List<Prescription> prescriptionListForReturnFee)
    {
        // 待删除的药物医嘱集合
        List<MedicationOrder> medicationListForReturnFee = new ArrayList<MedicationOrder>();
        Map<String, Object> medicationOrderMap = new HashMap<String, Object>();
        for (int i = 0; i < prescriptionListForReturnFee.size(); i++)
        {
            MedicationOrder medicationOrder = null;
            Prescription prescription = (Prescription) prescriptionListForReturnFee.get(i);
            medicationOrderMap.put("prescriptionSn",
                    prescription.getPrescriptionSn());
            medicationOrderMap.put("deleteFlag", Constants.NO_DELETE_FLAG);
            medicationOrderMap.put("orgCode", orgCode);
            // 根据相应条件获取数据库中对应药物医嘱记录
            List<Object> medicationOrderList = commonService.selectByCondition(
                    MedicationOrder.class, medicationOrderMap);
            if (medicationOrderList != null && !medicationOrderList.isEmpty())
            {
                for (int j = 0; j < medicationOrderList.size(); j++)
                {
                    medicationOrder = (MedicationOrder) medicationOrderList.get(j);
                    medicationListForReturnFee.add(medicationOrder);
                }
            }
        }
        return medicationListForReturnFee;
    }

    /**
     * 获取已退费处方的关联医嘱的中药配方并设置更新时间，删除标志
     * @param medicationOrderDeleteList
     * @return
     */
    private List<HerbalFormula> getHerbalFormulaForReturnFee(
            List<MedicationOrder> medicationListForReturnFee)
    {
        // 待删除的中药配方集合
        List<HerbalFormula> herbalFormulaListForReturnFee = new ArrayList<HerbalFormula>();
        Map<String, Object> herbalFormulaMap = new HashMap<String, Object>();
        for (int i = 0; i < medicationListForReturnFee.size(); i++)
        {
            HerbalFormula herbalFormula = null;
            MedicationOrder medicationOrder = (MedicationOrder) medicationListForReturnFee.get(i);
            herbalFormulaMap.put("orderSn", medicationOrder.getOrderSn());
            herbalFormulaMap.put("deleteFlag", Constants.NO_DELETE_FLAG);
            // 根据相应条件获取数据库中对应中药配方记录
            List<Object> herbalFormulaList = commonService.selectByCondition(
                    HerbalFormula.class, herbalFormulaMap);
            if (herbalFormulaList != null && !herbalFormulaList.isEmpty())
            {
                for (int j = 0; j < herbalFormulaList.size(); j++)
                {
                    herbalFormula = (HerbalFormula) herbalFormulaList.get(j);
                    herbalFormulaListForReturnFee.add(herbalFormula);
                }
            }
        }
        return herbalFormulaListForReturnFee;
    }

    // [BUG]14140 ADD END

    // Author :jia_yanqing
    // Date : 2013/3/26 13:00
    // [BUG]14709 ADD BEGIN
    /**
     * 获取已退费处方关联的药物医嘱关联的用药医嘱与发药记录
     * @param medicationOrderDeleteList
     * @return
     */
    private List<MedOrderDispensingRecord> getMedOrderDispensingRecordForReturnFee(
            List<MedicationOrder> medicationListForReturnFee)
    {
        // 待删除的用药医嘱与发药记录
        List<MedOrderDispensingRecord> medOrderDispensingRecordListForReturnFee = new ArrayList<MedOrderDispensingRecord>();
        Map<String, Object> medOrderDispensingRecordMap = new HashMap<String, Object>();
        for (int i = 0; i < medicationListForReturnFee.size(); i++)
        {
            MedOrderDispensingRecord medOrderDispensingRecord = null;
            MedicationOrder medicationOrder = (MedicationOrder) medicationListForReturnFee.get(i);
            medOrderDispensingRecordMap.put("orderSn",
                    medicationOrder.getOrderSn());
            medOrderDispensingRecordMap.put("deleteFlag",
                    Constants.NO_DELETE_FLAG);
            // 根据相应条件获取数据库中对应用药医嘱与发药记录
            List<Object> medOrderDispensingRecordList = commonService.selectByCondition(
                    MedOrderDispensingRecord.class, medOrderDispensingRecordMap);
            if (medOrderDispensingRecordList != null
                && !medOrderDispensingRecordList.isEmpty())
            {
                for (int j = 0; j < medOrderDispensingRecordList.size(); j++)
                {
                    medOrderDispensingRecord = (MedOrderDispensingRecord) medOrderDispensingRecordList.get(j);
                    medOrderDispensingRecordListForReturnFee.add(medOrderDispensingRecord);
                }
            }
        }
        return medOrderDispensingRecordListForReturnFee;
    }

    /**
     * 获取已退费处方关联的药物医嘱关联用药医嘱与发药记录关联的发药记录
     * @param medicationOrderDeleteList
     * @return
     */
    private List<DispensingRecord> getDispensingRecordForReturnFee(
            List<MedOrderDispensingRecord> medOrderDispensingRecordListForReturnFee)
    {
        // 待删除的发药记录集合
        List<DispensingRecord> dispensingRecordListForReturnFee = new ArrayList<DispensingRecord>();
        Map<String, Object> dispensingRecordMap = new HashMap<String, Object>();
        for (int i = 0; i < medOrderDispensingRecordListForReturnFee.size(); i++)
        {
            DispensingRecord dispensingRecord = null;
            MedOrderDispensingRecord medOrderDispensingRecord = (MedOrderDispensingRecord) medOrderDispensingRecordListForReturnFee.get(i);
            dispensingRecordMap.put("dispensingSn",
                    medOrderDispensingRecord.getDispensingSn());
            dispensingRecordMap.put("deleteFlag", Constants.NO_DELETE_FLAG);
            dispensingRecordMap.put("orgCode", orgCode);
            // 根据相应条件获取数据库中对应发药记录
            List<Object> dispensingRecordList = commonService.selectByCondition(
                    DispensingRecord.class, dispensingRecordMap);
            if (dispensingRecordList != null && !dispensingRecordList.isEmpty())
            {
                for (int j = 0; j < dispensingRecordList.size(); j++)
                {
                    dispensingRecord = (DispensingRecord) dispensingRecordList.get(j);
                    dispensingRecordListForReturnFee.add(dispensingRecord);
                }
            }
        }
        return dispensingRecordListForReturnFee;
    }

    // [BUG]14709 ADD END

    // Author :jia_yanqing
    // Date : 2012/10/16 16:00
    // [BUG]10422 MODIFY BEGIN
    /**
     * 根据相应条件获取数据库中对应处方记录
     * @param porxin010370uv01
     * @return 处方集合
     */
    private List<Object> getPrescriptionForDeleteFromDB(
            PORXIN010370UV01 porxin010370uv01)
    {
        // 待删除的处方集合
        List<Object> prescriptionDeleteList = new ArrayList<Object>();
        // 数据库中全部处方集合(来自处方消息的记录)
        List<Prescription> prescriptionAllList = prescriptionService.selectPrescriptionsByVisitSn(
                visitSn.toString(), "prescription", orgCode);

        boolean flag;
        // 获取要删除的未收费的处方
        for (int i = 0; i < prescriptionAllList.size(); i++)
        {
            flag = true;
            Prescription pre = prescriptionAllList.get(i);
            if (prescriptionList != null && prescriptionList.size() != 0)
            {
                for (PrescriptionDto preDto : prescriptionList)
                {
                    // 判断为已收费处方
                    if (preDto.getPaidFlag() != null
                        && !"".equals(preDto.getPaidFlag()))
                    {
                        // 判断消息中处方是否为已收费的处方
                        if (preDto.getPrescriptionNo().equals(
                                pre.getPrescriptionNo()))
                        {
                            flag = false;
                        }
                    }
                }
            }
            if (flag)
            {
                prescriptionDeleteList.add(pre);
            }
        }
        return prescriptionDeleteList;
    }

    // [BUG]10422 MODIFY END

    /**
     * 根据相应条件获取数据库中对应药物医嘱记录
     * @param prescriptionDeleteList
     * @return 药物医嘱集合
     */
    private List<Object> getMedicationOrderForDeleteFromDB(
            List<Object> prescriptionDeleteList)
    {
        // 待删除的药物医嘱集合
        List<Object> medicationOrderDeleteList = new ArrayList<Object>();
        Map<String, Object> medicationOrderMap = new HashMap<String, Object>();
        for (int i = 0; i < prescriptionDeleteList.size(); i++)
        {
            Prescription prescription = (Prescription) prescriptionDeleteList.get(i);
            medicationOrderMap.put("prescriptionSn",
                    prescription.getPrescriptionSn());
            medicationOrderMap.put("deleteFlag", Constants.NO_DELETE_FLAG);
            medicationOrderMap.put("orgCode", orgCode);
            // 根据相应条件获取数据库中对应药物医嘱记录
            List<Object> medicationOrderList = commonService.selectByCondition(
                    MedicationOrder.class, medicationOrderMap);
            if (medicationOrderList != null && !medicationOrderList.isEmpty())
            {
                medicationOrderDeleteList.addAll(medicationOrderList);
            }
        }
        return medicationOrderDeleteList;
    }

    /**
     * 根据相应条件获取数据库中对应中药配方记录
     * @param medicationOrderDeleteList
     * @return 中药配方集合
     */
    private List<Object> getHerbalFormulaForDeleteFromDB(
            List<Object> medicationOrderDeleteList)
    {
        // 待删除的中药配方集合
        List<Object> herbalFormulaDeleteList = new ArrayList<Object>();
        Map<String, Object> herbalFormulaMap = new HashMap<String, Object>();
        for (int i = 0; i < medicationOrderDeleteList.size(); i++)
        {
            MedicationOrder medicationOrder = (MedicationOrder) medicationOrderDeleteList.get(i);
            herbalFormulaMap.put("orderSn", medicationOrder.getOrderSn());
            herbalFormulaMap.put("deleteFlag", Constants.NO_DELETE_FLAG);
            // 根据相应条件获取数据库中对应中药配方记录
            List<Object> herbalFormulaList = commonService.selectByCondition(
                    HerbalFormula.class, herbalFormulaMap);
            if (herbalFormulaList != null && !herbalFormulaList.isEmpty())
            {
                herbalFormulaDeleteList.addAll(herbalFormulaList);
            }
        }
        return herbalFormulaDeleteList;
    }

    /**
     * 获取处方新增或更新时的记录对象
     * @param porxin010370uv01 处方信息
     * @return 处方记录
     */
    private void setPrescription(PORXIN010370UV01 porxin010370uv01)
    {
        Prescription prescription = null;
        for (PrescriptionDto prescriptionDto : prescriptionList)
        {
            // 未付费处方添加处方信息
            if (prescriptionDto.getPaidFlag() == null
                || "".equals(prescriptionDto.getPaidFlag()))
            {
                prescription = new Prescription();
                // 获取处方序列
                BigDecimal prescriptionSn = commonService.getSequence(PRESCRIPTION_SEQUENCE);
                // 设置处方内部序列号
                prescription.setPrescriptionSn(prescriptionSn);
                // 设置对应就诊内部序列号
                prescription.setVisitSn(visitSn);
                // 设置患者内部序列号
                prescription.setPatientSn(patientSn);
                // 设置域ID
                prescription.setPatientDomain(patientDomain);
                // 设置患者本地ID
                prescription.setPatientLid(patientLid);
                // 设置记录创建时间
                prescription.setCreateTime(systemTime);
                // 设置创建服务
                prescription.setCreateby(DataMigrationUtils.getDataMigration() + serviceId);
                // 设置新增时初始更新回数
                prescription.setUpdateCount(Constants.INSERT_UPDATE_COUNT);
                // 设置新增时初始删除标志
                prescription.setDeleteFlag(Constants.NO_DELETE_FLAG);
                // 设置患者年龄
                prescription.setPatientAge(porxin010370uv01.getAge());
                // 设置处方号
                prescription.setPrescriptionNo(prescriptionDto.getPrescriptionNo());
                // $Author :tong_meng
                // $Date : 2012/6/26 14:33$
                // [BUG]0007418 MODIFY BEGIN
                // 设置处方类别编码
                prescription.setPrescriptionClassCode(prescriptionDto.getPrescriptionClass());
                // [BUG]0007418 MODIFY END
                // $Author :tong_meng
                // $Date : 2012/6/26 14:33$
                // [BUG]0007418 ADD BEGIN
                // 设置处方类别名称
                prescription.setPrescriptionClassName(prescriptionDto.getPrescriptionClassName());
                // [BUG]0007418 ADD END
                if (prescriptionDto.getMedicineOrder() != null
                    && prescriptionDto.getMedicineOrder().size() != 0)
                {
                    // $Author :tong_meng
                    // $Date : 2012/6/26 14:33$
                    // [BUG]0007418 MODIFY BEGIN
                    // 设置处方类型编号
                    prescription.setPrescriptionTypeCode(prescriptionDto.getMedicineOrder().get(
                            0).getPrescriptionType());
                    // [BUG]0007418 MODIFY END

                    // $Author :tong_meng
                    // $Date : 2012/6/26 14:33$
                    // [BUG]0007418 ADD BEGIN
                    // 设置处方类型名称
                    prescription.setPrescriptionTypeName(prescriptionDto.getMedicineOrder().get(
                            0).getPrescriptionTypeName());
                    // [BUG]0007418 ADD END
                    // 是否药观
                    prescription.setMedViewFlag(StringUtils.getConversionValue(prescriptionDto.getMedicineOrder().get(
                            0).getMedViewFlag()));
                }
                // 设置开方时间
                prescription.setOrderTime(DateUtils.stringToDate(prescriptionDto.getPrescriptionTime()));
                // 设置审核时间
                prescription.setReviewTime(DateUtils.stringToDate(prescriptionDto.getReviewTime()));
                // 设置审核人编码
                prescription.setReviewPersonId(prescriptionDto.getReviewPerson());
                // 设置审核人姓名
                prescription.setReviewPersonName(prescriptionDto.getReviewPersonName());
                // $Author :jia_yanqing
                // $Date : 2012/5/25 16:55$
                // [BUG]0006666 DELETE BEGIN
                /*
                 * // 设置药观药物编码
                 * prescription.setMedViewCode(porxin010370uv01.getMedViewCode
                 * ()); // 设置药观药物名称
                 * //prescription.setMedViewName(porxin010370uv01
                 * .getMedViewName()); // 设置是否药观标识
                 * //prescription.setMedViewFlag(
                 * StringUtils.getConversionValue(porxin010370uv01
                 * .getMedViewFlag()));
                 */
                // [BUG]0006666 DELETE END
                // 设置更新时间
                prescription.setUpdateTime(systemTime);

                // $Author:wei_peng
                // $Date:2012/11/06 13:32
                // [BUG]0011030 ADD BEGIN
                if (prescriptionDto.getPrescriptionDoctorId() != null
                    && !prescriptionDto.getPrescriptionDoctorId().isEmpty())
                {
                    PatientDoctor patientDoctor = commonWriterUtils.getPatientDoctor(
                            visitSn, patientSn,
                            prescriptionDto.getPrescriptionDoctorId(),
                            prescriptionDto.getPrescriptionDoctorName(),
                            systemTime);
                    if (patientDoctor != null)
                    {
                        patientDoctor.setCreateby(DataMigrationUtils.getDataMigration() + serviceId);
                        // $Author:jin_peng
                        // $Date:2013/02/06 13:38
                        // [BUG]0013909 MODIFY BEGIN
                        // 如果该医生未被处理则进行添加操作
                        if (!commonWriterUtils.isExistsPatientDoctor(
                                filterPatientDoctorList, visitSn, patientSn,
                                prescriptionDto.getPrescriptionDoctorId()))
                        {
                            patientDoctorList.add(patientDoctor);
                        }
                        // [BUG]0013909 MODIFY END
                    }
                    
                }
                // [BUG]0011030 ADD END
                // $Author :chang_xuewen
                // $Date : 2013/12/05 11:00$
                // [BUG]0040271 ADD BEGIN
                prescription.setOrgCode(orgCode);
                prescription.setOrgName(orgName);
                // [BUG]0040271 ADD END
                prescriptionAddList.add(prescription);
                setMedicationOrder(prescriptionDto, prescriptionSn,
                        porxin010370uv01.getTriggerEvent());
            }
        }

    }

    /**
     * 判断用药医嘱是否包含草药
     */
    private boolean isHerbalMedicationOrder(
            MedicationOrderDto medicationOrderDto)
    {
        // $Author :jia_yanqing
        // $Date : 2013/02/20 15:00$
        // [BUG]0014009 MODIFY BEGIN
        // $Author :jin_peng
        // $Date : 2013/02/19 11:20$
        // [BUG]0013981 MODIFY BEGIN
        // $Author :jia_yanqing
        // $Date : 2012/8/29 14:55$
        // [BUG]0009250 MODIFY BEGIN
        return Constants.MEDICAL_CLASS_HERBAL.equals(medicationOrderDto.getDrugType());
        // [BUG]0009250 MODIFY END
        // [BUG]0013981 MODIFY END
        // [BUG]0014009 MODIFY END
    }

    /**
     * 设置处方对应的需要新增的用药医嘱记录集合
     * @param prescriptionDto 处方信息
     * @param prescriptionSn 处方内部序列号
     */
    private void setMedicationOrder(PrescriptionDto prescriptionDto,
            BigDecimal prescriptionSn, String newUpFlag)
    {
        // 设置CDR用药医嘱对象集合
        for (MedicationOrderDto medicineOrderDto : prescriptionDto.getMedicineOrder())
        {
            MedicationOrder medicationOrder = new MedicationOrder();
            // 获取用药医嘱内部序列号
            orderSn = commonService.getSequence(ORDER_SEQUENCE);
            // dto中添加ordersn
            medicineOrderDto.setOrderSn(orderSn);
            // 设置用药医嘱内部序列号
            medicationOrder.setOrderSn(orderSn);
            // 设置就诊内部序列号
            medicationOrder.setVisitSn(visitSn);
            // 设置处方内部序列号
            medicationOrder.setPrescriptionSn(prescriptionSn);
            // 设置患者内部序列号
            medicationOrder.setPatientSn(patientSn);
            // 设置域ID
            medicationOrder.setPatientDomain(patientDomain);
            // 设置患者本地ID
            medicationOrder.setPatientLid(patientLid);
            // 设置本地医嘱号
            medicationOrder.setOrderLid(medicineOrderDto.getOrderLid());

            // $Author :chang_xuewen
            // $Date : 2013/12/05 11:00$
            // [BUG]0040271 ADD BEGIN
            medicationOrder.setOrgCode(orgCode);
            medicationOrder.setOrgName(orgName);
            // [BUG]0040271 ADD END
            // Author :jia_yanqing
            // Date : 2012/11/13 17:50$
            // [BUG]0011469 MODIFY BEGIN
            if (isHerbalMedicationOrder(medicineOrderDto))
            {
                // 设置医嘱类型
                medicationOrder.setOrderType(Constants.HERBAL_MEDICINE_CLASS);
                // 设置医嘱类型名称
                medicationOrder.setOrderTypeName(DictionaryCache.getDictionary(
                        Constants.DOMAIN_ORDER_TYPE).get(
                        Constants.HERBAL_MEDICINE_CLASS));
            }
            // $Author :tong_meng
            // $Date : 2013/1/29 20:40$
            // [BUG]0013740 ADD BEGIN
            else
            {
                // 设置天数
                medicationOrder.setDays(StringUtils.strToBigDecimal(
                        medicineOrderDto.getRepeatNumber(), "天数"));
                // 设置领药量
                medicationOrder.setDispensingQuantity(medicineOrderDto.getObtain());
                // 设置领药单位
                medicationOrder.setDispensingUnit(medicineOrderDto.getObtainUnit());
            }
            // [BUG]0013740 ADD END
            // [BUG]0011469 MODIFY BEGIN

            // 设置药物编码
            medicationOrder.setDrugCode(medicineOrderDto.getDrugCode());

            // $Author :tong_meng
            // $Date : 2012/11/07 11:00$
            // [BUG]0010871 MODIFY BEGIN
            CodeDrug codeDrug = null;
            if (StringUtils.isNotNullAll(medicineOrderDto.getDrugCode(),
                    medicineOrderDto.getSerialNo()))
                codeDrug = prescriptionService.selectCodeDrug(
                        medicineOrderDto.getDrugCode(),
                        medicineOrderDto.getSerialNo());
            // $Author :tong_meng
            // $Date : 2012/7/18 13:30$
            // [BUG]0007988 ADD BEGIN
            if (codeDrug != null)
            {
                if (!StringUtils.isEmpty(medicineOrderDto.getDrugCode()))
                {
                    // $Author:wu_biao
                    // $Date:2012/10/25 15:10
                    // $[BUG]10749 MODIFY BEGIN
                    // 消息不会传过来drugName，所以要从关联药品字典code_drug查询出name
                    // $Author :tong_meng
                    // $Date : 2012/8/15 14:30$
                    // [BUG]0007988 ADD BEGIN
                    // $Author :tong_meng
                    // $Date : 2012/7/16 21:00$
                    // [BUG]0006666 ADD BEGIN
                    // 设置药物名称
                    medicationOrder.setDrugName(codeDrug.getName());
                    // [BUG]0006666 ADD BEGIN
                    // [BUG]0007988 ADD BEGIN
                    // $[BUG]10749 MODIFY END
                }
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
                // 药物常用频率代码
//                medicationOrder.setMedicineFrenquency(codeDrug.getFrequCode());
//                if (!StringUtils.isEmpty(codeDrug.getFrequCode()))
//                    // 药物常用频率名称
//                    medicationOrder.setMedicineFreqName(DictionaryCache.getDictionary(
//                            Constants.DOMAIN_MEDICINE_FREQ).get(
//                            codeDrug.getFrequCode()));

                /*
                 * Author: yu_yzh
                 * Date: 2015/11/16 15:22
                 * 通用名称从药品字典表中取 MODIFY BEGIN
                 * */
                /*                
                // $Author:wei_peng
                // $Date:2012/11/22 11:18
                // [BUG]0011805 ADD BEGIN
                if (cmn != null)
                    
                // [BUG]0011805 ADD END
*/                	
                // 药物通用名称
                CodeMedicalName cmn = dictionaryService.selectCodeMedicalName(codeDrug.getDrugId());
                if(cmn != null && cmn.getPrintName() != null && cmn.getPrintName().length() > 0){
                	medicationOrder.setApprovedDrugName(cmn.getPrintName());
                } else {
                	medicationOrder.setApprovedDrugName(codeDrug.getName());	
                }
                
                // 药物英文名
                if(cmn != null){
                	medicationOrder.setEnglishName(cmn.getEnglishName());
                }
                
                // 通用名称从药品字典表中取 MODIFY END
                
                // 药物商品名称
                if (!StringUtils.isEmpty(medicineOrderDto.getDrugCode()))
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
                // [BUG]0010871 ADD BEGIN
            }
            // [BUG]0007988 ADD END
            // [BUG]0010871 MODIFY END

            // Author :jia_yanqing
            // Date : 2012/5/25 16:55
            // [BUG]0006666 DELETE BEGIN
            /*
             * // 设置药物名称
             * medicationOrder.setDrugName(medicineOrder.getDrugName()); //
             * 设置药物通用名
             * medicationOrder.setApprovedDrugName(medicineOrder.getApprovedDrugName
             * ()); // 设置药物类别
             * medicationOrder.setMedicineClass(medicineOrder.getMedicineClass
             * ()); // 设置药物类型
             * medicationOrder.setMedicineType(medicineOrder.getMedicineType());
             * // 设置药物类型名称
             * medicationOrder.setMedicineTypeName(medicineOrder.getMedicineTypeName
             * ());
             */
            // [BUG]0006666 DELETE END
            // 设置药物使用-次剂量
            medicationOrder.setDosage(medicineOrderDto.getDosage());
            // 设置药物使用-次剂量单位
            medicationOrder.setDosageUnit(medicineOrderDto.getDosageUnit());
            // 设置药物使用-总剂量
            medicationOrder.setTotalDosage(medicineOrderDto.getTotalDosage());
            // 设置药物使用-总剂量单位
            medicationOrder.setTotalDosageUnit(medicineOrderDto.getTotalDosageUnit());
            // 设置用药途径代码
            medicationOrder.setRouteCode(medicineOrderDto.getRouteCode());
            // 设置用药途径名称
            medicationOrder.setRouteName(DictionaryUtils.getNameFromDictionary(
            		Constants.DOMAIN_SUPPLY_ROUTE, medicineOrderDto.getRouteCode(), medicineOrderDto.getRouteName()));
            
            // 设置药物使用-频率
            medicationOrder.setMedicineFrenquency(medicineOrderDto.getMedicineFrenquency());
            // 设置药物使用-频率名称
            medicationOrder.setMedicineFreqName(DictionaryUtils.getNameFromDictionary(
            		Constants.DOMAIN_MEDICINE_FREQ, medicineOrderDto.getMedicineFrenquency(), medicineOrderDto.getMedicineFrenquencyName()));
            
            // $Author :jia_yanqing
            // $Date : 2012/5/25 16:55$
            // [BUG]0006666 DELETE BEGIN
            /*
             * // 设置药物剂型代码
             * medicationOrder.setMedicineFormCode(medicineOrder.getMedicineFormCode
             * ()); // 设置药物剂型名称
             * medicationOrder.setMedicineForm(medicineOrder.getMedicineForm());
             */
            // [BUG]0006666 DELETE END
            // 设置是否皮试
            medicationOrder.setSkinTestFlag(StringUtils.getConversionValue(medicineOrderDto.getSkinTestFlag()));
            // $Author:wei_peng
            // $Date:2013/8/9 11:30
            // [BUG]0035985 ADD BEGIN
            // 是否加急
            medicationOrder.setUrgentFlag(StringUtils.getConversionValue(medicineOrderDto.getUrgency()));
            // 是否适应
            medicationOrder.setAdaptiveFlag(StringUtils.getConversionValue(medicineOrderDto.getAdaptive()));
            // 是否药观
            medicationOrder.setMedViewFlag(StringUtils.getConversionValue(medicineOrderDto.getMedViewFlag()));
            // [BUG]0035985 ADD END
            // $Author :jia_yanqing
            // $Date : 2012/5/25 16:55$
            // [BUG]0006666 DELETE BEGIN
            /*
             * // $Author :tong_meng // $Date : 2012/5/21 19:40$ // [BUG]0006098
             * MODIFY BEGIN // 设置皮试结果
             * medicationOrder.setSkinTestResult(medicineOrder
             * .getSkinTestResult()); // [BUG]0006098 MODIFY END
             */
            // [BUG]0006666 DELETE END
            // 设置嘱托
            medicationOrder.setComments(medicineOrderDto.getComments());
            // 设置医嘱开立科室ID
            medicationOrder.setOrderDept(prescriptionDto.getPrescriptionDept());
            // $Author :tong_meng
            // $Date : 2012/7/1 10:33$
            // [BUG]0007418 ADD BEGIN
            // 设置医嘱开立科室名称
            medicationOrder.setOrderDeptName(prescriptionDto.getPrescriptionDeptName());
            // [BUG]0007418 ADD END
            // 设置医嘱开立人ID
            medicationOrder.setOrderPerson(prescriptionDto.getPrescriptionDoctorId());
            // 设置医嘱开立人姓名
            medicationOrder.setOrderPersonName(prescriptionDto.getPrescriptionDoctorName());
            // 设置开嘱时间
            medicationOrder.setOrderTime(DateUtils.stringToDate(prescriptionDto.getPrescriptionTime()));

            // Author :jin_peng
            // Date : 2012/11/14 14:56
            // [BUG]0011478 MODIFY BEGIN
            String orderStatus = null;
            String orderStatusName = null;

            /*
             * Author: yu_yzh
             * Date: 2015/10/22 16:25
             * 港大门诊住院开立医嘱都为已下达
             * MODIFY BEGIN
             * */
/*            // 门诊医嘱状态为已下达，门诊医嘱长期临时标识为临时
            if (Constants.PATIENT_DOMAIN_OUTPATIENT.equals(patientDomain))
            {
                orderStatus = Constants.ORDER_STATUS_ISSUE;

                orderStatusName = DictionaryCache.getDictionary(
                        Constants.DOMAIN_ORDER_STATUS).get(
                        Constants.ORDER_STATUS_ISSUE);
            }
            // 住院医嘱状态为已确认，长期和临时标识通过药物使用频率名称判断
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
            /*
             * 港大门诊住院开立医嘱都为已下达
             * MODIFY END
             * */
            // $Author:wei_peng
            // $Date:2013/3/29 10:15
            // [BUG]0014783 MODIFY BEGIN
            // 设置长期临时标识
//            medicationOrder.setTemporaryFlag(medicineOrderDto.getTemporaryFlag(medicineOrderDto.getMedicineFrenquency()));
            // 标识不与长期医嘱标识相同的都设置为临时医嘱
            medicationOrder.setTemporaryFlag(
            		Constants.LONG_TERM_FLAG.equals(medicineOrderDto.getTemporaryFlag()) ? Constants.LONG_TERM_FLAG : Constants.TEMPORARY_FLAG);
            // [BUG]0014783 MODIFY END

            // 设置医嘱状态
            medicationOrder.setOrderStatus(orderStatus);
            // $Author :tong_meng
            // $Date : 2012/7/1 10:33$
            // [BUG]0007418 ADD BEGIN
            // 设置医嘱状态名称
            medicationOrder.setOrderStatusName(orderStatusName);
            // [BUG]0007418 ADD END
            // [BUG]0011478 MODIFY END

            // $Author :tong_meng
            // $Date : 2012/7/1 10:33$
            // [BUG]0013744 MODIFY BEGIN
            // $Author :jia_yanqing
            // $Date : 2012/5/28 13:55$
            // [BUG]0006666 DELETE BEGIN
            // 设置执行科室ID
            medicationOrder.setExecDept(medicineOrderDto.getExecDept());
            // 设置执行科室名称
            medicationOrder.setExecDeptName(DictionaryUtils.getNameFromDictionary(
            		Constants.DOMAIN_DEPARTMENT, medicineOrderDto.getExecDept(), medicineOrderDto.getExecDeptName()));
            // [BUG]0006666 DELETE BEGIN
            // [BUG]0013744 MODIFY END

            // 设置父医嘱标识号码
            // medicationOrder.setParentOrderId(StringUtils.strToBigDecimal(medicineOrder.getParentOrderId()));

            // $Author :jia_yanqing
            // $Date : 2012/5/28 11:25$
            // [BUG]0006666 ADD BEGIN
            // 药物包装序号
            medicationOrder.setSerialNo(medicineOrderDto.getSerialNo());
            // [BUG]0006666 ADD BEGIN

            // $Author:jia_yanqing
            // $Date:2012/08/31 18:30$
            // [BUG]0009310 ADD BEIGN
            BigDecimal parentSn = null;
            // 子医嘱
            MedicationOrder medicationParentOrder = null;
            if (!StringUtils.isEmpty(medicineOrderDto.getParentOrderId()))
            {
                medicationParentOrder = commonService.checkParentOrder(
                        patientDomain, patientLid,
                        medicineOrderDto.getParentOrderId(),orgCode);
                // 数据库中能查到父医嘱
                if (medicationParentOrder != null)
                {
                    parentSn = medicationParentOrder.getOrderSn();
                }
                // Author :tong_meng
                // Date : 2013/3/12 18:00
                // [BUG]0014522 MODIFY BEGIN
                // 父子医嘱一起传过来时，数据库中查不到父医嘱，消息中一定有父医嘱传过来，将父子医嘱关系保存到map中
                else
                {
                    medicationOrderMap.put(orderSn,
                            medicineOrderDto.getParentOrderId());
                }
                // [BUG]0014522 MODIFY END
            }
            // 父医嘱内部序列号
            medicationOrder.setParentOrderSn(parentSn);
            // [BUG]0009310 ADD END
            // 设置记录创建时间
            medicationOrder.setCreateTime(systemTime);
            // 设置消息服务
            medicationOrder.setCreateby(DataMigrationUtils.getDataMigration() + serviceId);
            // 设置记录更新时间
            medicationOrder.setUpdateTime(systemTime);
            // 设置记录更新回数
            medicationOrder.setUpdateCount(Constants.INSERT_UPDATE_COUNT);
            // 设置记录删除标志
            medicationOrder.setDeleteFlag(Constants.NO_DELETE_FLAG);

            // $Author:wei_peng
            // $Date:2012/11/13 17:33
            // [BUG]0011421 ADD BEGIN
            if (isRenewMessage(newUpFlag))
            {
                // 医嘱交费状态编码
                medicationOrder.setChargeStatusCode(Constants.CHARGE_STATUS_REPAYCHARGE);
                // 医嘱交费状态名称
                medicationOrder.setChargeStatusName(DictionaryCache.getDictionary(
                        Constants.ORDER_CHARGE_STATUS).get(
                        Constants.CHARGE_STATUS_REPAYCHARGE));
            }
            else
            {
                // 医嘱交费状态编码
                medicationOrder.setChargeStatusCode(Constants.CHARGE_STATUS_NOTCHARGE);
                // 医嘱交费状态名称
                medicationOrder.setChargeStatusName(DictionaryCache.getDictionary(
                        Constants.ORDER_CHARGE_STATUS).get(
                        Constants.CHARGE_STATUS_NOTCHARGE));
            }
            // [BUG]0011421 ADD END

            // 将用设置完成用药医嘱对象添加到用药医嘱对象集合中
            medicationOrderAddList.add(medicationOrder);
            if (isHerbalMedicationOrder(medicineOrderDto))
            {
                setHerbalFormula(medicineOrderDto);
            }
        }
    }

    // [BUG]0010351+0010274 MODIFY END

    /**
     * 设置处方对应的用药医嘱所对应的需要新增的中药配方记录集合
     * @param medicineOrderDto
     */
    private void setHerbalFormula(MedicationOrderDto medicineOrderDto)
    {
        for (HerbalFormulaDto herbalFormula : medicineOrderDto.getHerbalFormula())
        {
            HerbalFormula hbFormula = new HerbalFormula();
            // 医嘱内部序列号
            hbFormula.setOrderSn(medicineOrderDto.getOrderSn());
            // 药物名称代码
            hbFormula.setHerbCode(herbalFormula.getHerbCode());
            // $Author:wei_peng
            // $Date:2012/11/22 11:18
            // [BUG]0011805 DELETE BEGIN
            // $Author:wu_biao
            // $Date:2012/10/25 15:10
            // $[BUG]10749 MODIFY BEGIN
            // $Author:wei_peng
            // $Date:2012/8/30 16:50
            // $[BUG]0009256 MODIFY BEGIN
            // 药物名称
            /*
             * hbFormula.setHerbName(DictionaryCache.getDictionary(
             * Constants.DOMAIN_DRUG_DICTIONARY).get(
             * herbalFormula.getHerbCode() + herbalFormula.getSerialNo()));
             */
            // $[BUG]0009256 MODIFY END
            // $[BUG]10749 MODIFY END
            // [BUG]0011805 DELETE END

            // $Author :tong_meng
            // $Date : 2012/6/26 10:33$
            // [BUG]0010871 MODIFY BEGIN
            CodeDrug codeDrug = prescriptionService.selectCodeDrug(
                    herbalFormula.getHerbCode(), herbalFormula.getSerialNo());
            CodeMedicalName cmn;
            if (codeDrug != null)
            {
                cmn = dictionaryService.selectCodeMedicalName(codeDrug.getDrugId());
                if (cmn != null)
                    // 药物通用名称
                    hbFormula.setApprovedHerbName(cmn.getName());
                // $Author:wei_peng
                // $Date:2012/11/22 11:18
                // [BUG]0011805 ADD BEGIN
                hbFormula.setHerbName(codeDrug.getName());
                // [BUG]0011805 ADD END
            }
            // [BUG]0010871 MODIFY END

            // 药物数量
            hbFormula.setQuantity(StringUtils.strToBigDecimal(
                    herbalFormula.getQuantity(), "药物数量"));

            // 药物数量单位
            hbFormula.setUnit(herbalFormula.getUnit());
            // $Author :tong_meng
            // $Date : 2012/6/26 10:33$
            // [BUG]0007418 MODIFY BEGIN
            // $Author :tong_meng
            // $Date : 2012/5/16 17:00$
            // [BUG]0006444 MODIFY BEGIN
            // 煎法代码
            hbFormula.setDecoctionMethodCode(herbalFormula.getDecoctionMethodCode());
            // [BUG]0006444 MODIFY END
            // [BUG]0007418 MODIFY END

            // $Author :tong_meng
            // $Date : 2012/6/26 10:33$
            // [BUG]0007988 MODIFY BEGIN
            // $Author :tong_meng
            // $Date : 2012/6/26 10:33$
            // [BUG]0007418 ADD BEGIN
            // 煎法名称
            hbFormula.setDecoctionMethodName(herbalFormula.getDecoctionMethodName());
            // [BUG]0007418 ADD END
            // [BUG]0007988 MODIFY END

            // 是否与付数无关
            hbFormula.setNumUnconcernedFlag(StringUtils.getConversionValue(herbalFormula.getNumUnconcernedFlag()));

            // $Author :jia_yanqing
            // $Date : 2012/5/28 11:00$
            // [BUG]0006666 ADD BEGIN
            // 草药包装序号
            hbFormula.setSerialNo(herbalFormula.getSerialNo());
            // [BUG]0006666 ADD BEGIN
            // 删除标志
            hbFormula.setDeleteFlag(Constants.NO_DELETE_FLAG);
            // 创建时间
            hbFormula.setCreateTime(systemTime);
            // 消息服务
            hbFormula.setCreateby(DataMigrationUtils.getDataMigration() + serviceId);
            // 更新回数
            hbFormula.setUpdateCount(Constants.INSERT_UPDATE_COUNT);
            // 更新时间
            hbFormula.setUpdateTime(systemTime);
            // 删除时间
            hbFormula.setDeleteTime(null);
            // 将设置好的中药配方对象添加到需要新增的中药配方对象集合中
            herbalFormulaAddList.add(hbFormula);
        }
    }

    // 当父子医嘱来自同一消息时，更新子医嘱中父医嘱内部序列号
    private void setParentOrderSn()
    {
        Iterator<Entry<BigDecimal, String>> iterator = medicationOrderMap.entrySet().iterator();

        while (iterator.hasNext())
        {
            Entry<BigDecimal, String> entry = (Entry<BigDecimal, String>) iterator.next();
            BigDecimal orderSnKey = entry.getKey();
            // 通过key获取待添加父医嘱信息的子医嘱对象
            for (MedicationOrder medicationOrder : medicationOrderAddList)
            {
                if (medicationOrder.getOrderSn().equals(orderSnKey))
                {
                    String parentOrderId = entry.getValue();
                    // 通过value获取父医嘱对象，从中查找orderSn（通过消息中子医嘱parentOrderId和父医嘱orderId的对应关系来匹配）
                    for (MedicationOrder medicationParentOrder : medicationOrderAddList)
                    {
                        if (parentOrderId.equals(medicationParentOrder.getOrderLid()))
                        {
                            medicationOrder.setParentOrderSn(medicationParentOrder.getOrderSn());
                        }
                    }
                }
            }
        }
    }
    
    private void setAddAndUpdateList(PORXIN010370UV01 porxin010370uv01){
    	List<PrescriptionDto> prescriptionDtos = porxin010370uv01.getPrescriptions();
    	addMedicationOrderList = new ArrayList<MedicationOrder>();
    	updateMedicationOrderList = new ArrayList<MedicationOrder>();
    	addPrescriptionList = new ArrayList<Prescription>();
    	updatePrescriptionList = new ArrayList<Prescription>();
    	for(PrescriptionDto prescriptionDto : prescriptionDtos){
    		Prescription prescription = prescriptionService.selectPrescriptionsByPrescriptionNo(
    				prescriptionDto.getPrescriptionNo(), visitSn.toString(), patientSn.toString());
    		// 设置新增处方
    		if(prescription == null){
    			prescription = new Prescription();    			
                // 获取处方序列
                BigDecimal prescriptionSn = commonService.getSequence(PRESCRIPTION_SEQUENCE);
                // 设置处方内部序列号
                prescription.setPrescriptionSn(prescriptionSn);
                // 设置对应就诊内部序列号
                prescription.setVisitSn(visitSn);
                // 设置患者内部序列号
                prescription.setPatientSn(patientSn);
                // 设置域ID
                prescription.setPatientDomain(patientDomain);
                // 设置患者本地ID
                prescription.setPatientLid(patientLid);
                // 设置记录创建时间
                prescription.setCreateTime(systemTime);
                // 设置创建服务
                prescription.setCreateby(DataMigrationUtils.getDataMigration() + serviceId);
                // 设置新增时初始更新回数
                prescription.setUpdateCount(Constants.INSERT_UPDATE_COUNT);
                // 设置新增时初始删除标志
                prescription.setDeleteFlag(Constants.NO_DELETE_FLAG);
                // 设置患者年龄
                prescription.setPatientAge(porxin010370uv01.getAge());
                // 设置处方号
                prescription.setPrescriptionNo(prescriptionDto.getPrescriptionNo());
                // 设置处方类别编码
                prescription.setPrescriptionClassCode(prescriptionDto.getPrescriptionClass());
                // 设置处方类别名称
                prescription.setPrescriptionClassName(prescriptionDto.getPrescriptionClassName());
                if (prescriptionDto.getMedicineOrder() != null
                    && prescriptionDto.getMedicineOrder().size() != 0)
                {
                    // 设置处方类型编号
                    prescription.setPrescriptionTypeCode(prescriptionDto.getMedicineOrder().get(
                            0).getPrescriptionType());
                    // 设置处方类型名称
                    prescription.setPrescriptionTypeName(prescriptionDto.getMedicineOrder().get(
                            0).getPrescriptionTypeName());
                    // 是否药观
                    prescription.setMedViewFlag(StringUtils.getConversionValue(prescriptionDto.getMedicineOrder().get(
                            0).getMedViewFlag()));
                }
                // 设置开方时间
                prescription.setOrderTime(DateUtils.stringToDate(prescriptionDto.getPrescriptionTime()));
                // 设置审核时间
                prescription.setReviewTime(DateUtils.stringToDate(prescriptionDto.getReviewTime()));
                // 设置审核人编码
                prescription.setReviewPersonId(prescriptionDto.getReviewPerson());
                // 设置审核人姓名
                prescription.setReviewPersonName(prescriptionDto.getReviewPersonName());
                // 设置更新时间
                prescription.setUpdateTime(systemTime);
                if (prescriptionDto.getPrescriptionDoctorId() != null
                    && !prescriptionDto.getPrescriptionDoctorId().isEmpty())
                {
                    PatientDoctor patientDoctor = commonWriterUtils.getPatientDoctor(
                            visitSn, patientSn,
                            prescriptionDto.getPrescriptionDoctorId(),
                            prescriptionDto.getPrescriptionDoctorName(),
                            systemTime);
                    if (patientDoctor != null)
                    {
                        patientDoctor.setCreateby(DataMigrationUtils.getDataMigration() + serviceId);
                        // 如果该医生未被处理则进行添加操作
                        if (!commonWriterUtils.isExistsPatientDoctor(
                                filterPatientDoctorList, visitSn, patientSn,
                                prescriptionDto.getPrescriptionDoctorId()))
                        {
                            patientDoctorList.add(patientDoctor);
                        }
                    }
                    
                }
                prescription.setOrgCode(orgCode);
                prescription.setOrgName(orgName);
                
                addPrescriptionList.add(prescription);
    		} else {
    			prescription.setUpdateby(serviceId);
    			prescription.setUpdateTime(systemTime);
    			updatePrescriptionList.add(prescription);
    		}
    		// 以下为用药医嘱部分///////////////////////////////////////////
    		for(MedicationOrderDto medicationOrderDto : prescriptionDto.getMedicineOrder()){
    			MedicationOrder medicationOrder = prescriptionService.selectMedicationOrderByOrderLid(
    					medicationOrderDto.getOrderLid(), visitSn.toString());
    			if(medicationOrder == null){
    				// 检索不到用药医嘱，做新增操作
    				medicationOrder = new MedicationOrder();
    				addMedicationOrderList.add(medicationOrder);
    				medicationOrder.setCreateTime(systemTime);
    				medicationOrder.setCreateby(DataMigrationUtils.getDataMigration() + serviceId);
    				medicationOrder.setUpdateCount(Constants.INSERT_UPDATE_COUNT);
    				BigDecimal orderSn = commonService.getSequence(ORDER_SEQUENCE);
    				// 设置用药医嘱内部序列号
    	            medicationOrder.setOrderSn(orderSn);
    	            
    	            // 设置医嘱交费状态
    	            medicationOrder.setChargeStatusCode(Constants.CHARGE_STATUS_NOTCHARGE);
    	            medicationOrder.setChargeStatusName(DictionaryUtils.getNameFromDictionary(
    	            		Constants.ORDER_CHARGE_STATUS, Constants.CHARGE_STATUS_NOTCHARGE));
    	            
    			} else {
    				// 更新操作
    				updateMedicationOrderList.add(medicationOrder);
    			}
    			// 以下为新增/更新共同部分/////////////////////////////////////////////////////////////////
    			
	            // 设置就诊内部序列号
	            medicationOrder.setVisitSn(visitSn);
	            // 设置处方内部序列号
	            medicationOrder.setPrescriptionSn(prescription.getPrescriptionSn());
	            // 设置患者内部序列号
	            medicationOrder.setPatientSn(patientSn);
	            // 设置域ID
	            medicationOrder.setPatientDomain(patientDomain);
	            // 设置患者本地ID
	            medicationOrder.setPatientLid(patientLid);
	            // 设置本地医嘱号
	            medicationOrder.setOrderLid(medicationOrderDto.getOrderLid());
	            
	            medicationOrder.setOrgCode(orgCode);
	            medicationOrder.setOrgName(orgName);
	            
	            //医嘱类型
	            medicationOrder.setOrderType(medicationOrderDto.getOrderType());
	            medicationOrder.setOrderTypeName(medicationOrderDto.getOrderTypeName());
	            
	            // 设置天数
                medicationOrder.setDays(StringUtils.strToBigDecimal(
                        medicationOrderDto.getRepeatNumber(), "天数"));
                // 设置领药量
                medicationOrder.setDispensingQuantity(medicationOrderDto.getObtain());
                // 设置领药单位
                medicationOrder.setDispensingUnit(medicationOrderDto.getObtainUnit());
                
                // 设置药物编码
                medicationOrder.setDrugCode(medicationOrderDto.getDrugCode());
                CodeDrug codeDrug = null;
                codeDrug = prescriptionService.selectCodeDrugByDrugCode(medicationOrderDto.getDrugCode());
/*                if (StringUtils.isNotNullAll(medicationOrderDto.getDrugCode(),
                        medicationOrderDto.getSerialNo()))
                    codeDrug = prescriptionService.selectCodeDrug(
                            medicationOrderDto.getDrugCode(),
                            medicationOrderDto.getSerialNo());*/
                if (codeDrug != null) {
                    if (!StringUtils.isEmpty(medicationOrderDto.getDrugCode())){
                        medicationOrder.setDrugName(codeDrug.getName());
                    }
                    // 药物类别代码
                    medicationOrder.setMedicineClass(codeDrug.getDrugKind());
                    if (!StringUtils.isEmpty(codeDrug.getDrugKind()))
                        // 药物类别名称                    	
                        medicationOrder.setMedicineClassName(DictionaryUtils.getNameFromDictionary(
                        		Constants.DOMAIN_MEDICINE_KIND, codeDrug.getDrugKind()));
                    
                    // 药物通用名称
                    CodeMedicalName cmn = dictionaryService.selectCodeMedicalName(codeDrug.getCode());
                    if(cmn != null && cmn.getPrintName() != null && cmn.getPrintName().length() > 0){
                    	medicationOrder.setApprovedDrugName(cmn.getPrintName());
                    } else {
                    	medicationOrder.setApprovedDrugName(codeDrug.getName());	
                    }
                    
                    // 药物英文名
                    if(cmn != null){
                    	medicationOrder.setEnglishName(cmn.getEnglishName());
                    }
                    
                    // 通用名称从药品字典表中取 MODIFY END
                    
                    // 药物商品名称
                    if (!StringUtils.isEmpty(medicationOrderDto.getDrugCode()))
                        // 消息不会传过来drugName，所以要从关联药品字典code_drug查询出name
                        medicationOrder.setDrugName(codeDrug.getName());

                    // 药物类型代码
                    medicationOrder.setMedicineType(codeDrug.getClassCode());
                    if (!StringUtils.isEmpty(codeDrug.getClassCode()))
                        // 药物类型名称                    	
                        medicationOrder.setMedicineTypeName(DictionaryUtils.getNameFromDictionary(
                        		Constants.DOMAIN_MEDICINE_TYPE, codeDrug.getClassCode()));
                    // 药物剂型代码
                    medicationOrder.setMedicineFormCode(codeDrug.getDosage());
                    if (!StringUtils.isEmpty(codeDrug.getDosage()))
                        // 药物剂型名称                    	
                        medicationOrder.setMedicineForm(DictionaryUtils.getNameFromDictionary(
                        		Constants.DOMAIN_MEDICINE_FORM, codeDrug.getDosage()));
                } else {
                	// 数据中无药品字典的信息，根据消息设置药品名称
                	medicationOrder.setDrugName(medicationOrderDto.getDrugName());
                	
                	// 设置药物使用-频率
                    medicationOrder.setMedicineFrenquency(medicationOrderDto.getMedicineFrenquency());
                    // 设置药物使用-频率名称
                    medicationOrder.setMedicineFreqName(DictionaryUtils.getNameFromDictionary(
                    		Constants.DOMAIN_MEDICINE_FREQ, medicationOrderDto.getMedicineFrenquency(), medicationOrderDto.getMedicineFrenquencyName()));

                }
                // 设置药物使用-次剂量
                medicationOrder.setDosage(medicationOrderDto.getDosage());
                // 设置药物使用-次剂量单位
                medicationOrder.setDosageUnit(medicationOrderDto.getDosageUnit());
                // 设置药物使用-总剂量
                medicationOrder.setTotalDosage(medicationOrderDto.getTotalDosage());
                // 设置药物使用-总剂量单位
                medicationOrder.setTotalDosageUnit(medicationOrderDto.getTotalDosageUnit());
                // 设置是否皮试
                medicationOrder.setSkinTestFlag(StringUtils.getConversionValue(medicationOrderDto.getSkinTestFlag()));
                // 是否加急
                medicationOrder.setUrgentFlag(StringUtils.getConversionValue(medicationOrderDto.getUrgency()));
                // 是否适应
                medicationOrder.setAdaptiveFlag(StringUtils.getConversionValue(medicationOrderDto.getAdaptive()));
                // 是否药观
                medicationOrder.setMedViewFlag(StringUtils.getConversionValue(medicationOrderDto.getMedViewFlag()));
                // 设置嘱托
                medicationOrder.setComments(medicationOrderDto.getComments());
                // 设置医嘱开立科室ID
                medicationOrder.setOrderDept(prescriptionDto.getPrescriptionDept());
                // 设置医嘱开立科室名称
                medicationOrder.setOrderDeptName(DictionaryUtils.getNameFromDictionary(
                		Constants.DOMAIN_DEPARTMENT, prescriptionDto.getPrescriptionDept(), prescriptionDto.getPrescriptionDeptName()));
                // 设置医嘱开立人ID
                medicationOrder.setOrderPerson(prescriptionDto.getPrescriptionDoctorId());
                // 设置医嘱开立人姓名
                medicationOrder.setOrderPersonName(DictionaryUtils.getNameFromDictionary(
                		Constants.DOMAIN_STAFF, prescriptionDto.getPrescriptionDoctorId(), prescriptionDto.getPrescriptionDoctorName()));
                // 设置开嘱时间
                medicationOrder.setOrderTime(DateUtils.stringToDate(prescriptionDto.getPrescriptionTime()));
                                
                // 设置药物使用-频率
                medicationOrder.setMedicineFrenquency(medicationOrderDto.getMedicineFrenquency());
                // 设置药物使用-频率名称
                medicationOrder.setMedicineFreqName(DictionaryUtils.getNameFromDictionary(
                		Constants.DOMAIN_MEDICINE_FREQ, medicationOrderDto.getMedicineFrenquency(), medicationOrderDto.getMedicineFrenquencyName()));

                
                // 设置用药途径代码
                medicationOrder.setRouteCode(medicationOrderDto.getRouteCode());
                // 设置用药途径名称
                medicationOrder.setRouteName(DictionaryUtils.getNameFromDictionary(
                		Constants.DOMAIN_SUPPLY_ROUTE, medicationOrderDto.getRouteCode(), medicationOrderDto.getRouteName()));
                
                
                // 港大门诊住院开立医嘱都为已下达
                String orderStatus = Constants.ORDER_STATUS_ISSUE;
                String orderStatusName = DictionaryUtils.getNameFromDictionary(
                		Constants.DOMAIN_ORDER_STATUS, Constants.ORDER_STATUS_ISSUE);
                // 设置医嘱状态
                medicationOrder.setOrderStatus(orderStatus);
                // 设置医嘱状态名称
                medicationOrder.setOrderStatusName(orderStatusName);
                
                // 标识不与长期医嘱标识相同的都设置为临时医嘱
                medicationOrder.setTemporaryFlag(
                		Constants.LONG_TERM_FLAG.equals(medicationOrderDto.getTemporaryFlag()) ? Constants.LONG_TERM_FLAG : Constants.TEMPORARY_FLAG);
                //  用药开始时间
                if(medicationOrderDto.getOrderStartTime() != null){
                	medicationOrder.setOrderStartTime(DateUtils.stringToDate(medicationOrderDto.getOrderStartTime()));
                }
                
                // 设置执行科室ID
                medicationOrder.setExecDept(medicationOrderDto.getExecDept());
                // 设置执行科室名称
                medicationOrder.setExecDeptName(DictionaryUtils.getNameFromDictionary(
                		Constants.DOMAIN_DEPARTMENT, medicationOrderDto.getExecDept(), medicationOrderDto.getExecDeptName()));
                // 药物包装序号
                medicationOrder.setSerialNo(medicationOrderDto.getSerialNo());
                // 更新者
                medicationOrder.setUpdateby(serviceId);
				// 更新时间
                medicationOrder.setUpdateTime(systemTime);
                // 设置记录删除标志
                medicationOrder.setDeleteFlag(Constants.NO_DELETE_FLAG);   
                
                if(porxin010370uv01.getWardsId() != null){
                    // 病区编码
                    medicationOrder.setWardsId(porxin010370uv01.getWardsId());
                    // 病区名称
                    medicationOrder.setWardName(DictionaryUtils.getNameFromDictionary(
                    		Constants.DOMAIN_WARD, porxin010370uv01.getWardsId(), porxin010370uv01.getWardsName()));
                }
                
                medicationOrder.setBedNo(porxin010370uv01.getBedNo());
    		}
    	}
    }
    
    /**
     * 处方作废时，可能存在只作废处方中部分药品医嘱，检索数据库中存在的处方和药品医嘱。
     * @return 若有不存在的内容，返回
     * */
    private List<String> checkDeleteList(PORXIN010370UV01 porxin010370uv01){
    	List<String> resultList = new ArrayList<String>();
    	List<PrescriptionDto> prescriptionDtos = porxin010370uv01.getPrescriptions();
    	deletePrescriptionList = new ArrayList<Prescription>();
    	nursingOperationList = new ArrayList<NursingOperation>();
    	dtoEntityMap = new HashMap<MedicationOrderDto, MedicationOrder>();
    	for(PrescriptionDto prescriptionDto : prescriptionDtos){
    		// 消息中处方中的药品列表为空，不做处理
    		if(prescriptionDto.getMedicineOrder() == null 
    				|| prescriptionDto.getMedicineOrder().isEmpty())continue;
    		
    		Prescription prescription = prescriptionService.selectPrescriptionsByPrescriptionNo(
    				prescriptionDto.getPrescriptionNo(), visitSn.toString(), patientSn.toString());
    		
    		if(prescription != null){
    			List<MedicationOrder> medicationOrderList = prescriptionService.selectMedicationOrdersByPrescriptionSn(
    					prescription.getPrescriptionSn().toString(), visitSn.toString());
    			// 数据库中已存在的要做撤销操作的药品医嘱
    			List<MedicationOrder> deleteOrderList = new ArrayList<MedicationOrder>();
    			StringBuilder s = null;
    			for(MedicationOrderDto medicationOrderDto : prescriptionDto.getMedicineOrder()){
        			boolean matchFlag = false;
    				for(MedicationOrder medicationOrder : medicationOrderList){
    					if(medicationOrder.getOrderLid().equals(medicationOrderDto.getOrderLid())){
    						matchFlag = true;
    			    		dtoEntityMap.put(medicationOrderDto, medicationOrder);
    						deleteOrderList.add(medicationOrder);
    						break;
    					}
    				}
    				if(!matchFlag){
    					if(s == null) s = new StringBuilder();
    					s.append(medicationOrderDto.getOrderLid() + ", ");
    				}
    			}
    			// 作废消息中的药品医嘱在数据库中检索不到
    			if(s != null){
    				s.insert(0, "医嘱号为：");
    				s.append("的用药医嘱不存在");
    				resultList.add(s.toString());
    			}
    			// 处方中的药品已经全部撤销，该条处方放入删除列表中
    			if(medicationOrderList.size() == deleteOrderList.size()){
    				deletePrescriptionList.add(prescription);
    			}
    			// 将要撤销的用药医嘱加入到总的撤销医嘱列表中
    			deleteMedicationOrderList.addAll(deleteOrderList);
//    			nursingOperationList.addAll(getNursingOperationList(prescriptionDto, deleteOrderList));
    		} else {
    			String s = "处方不存在，处方号：" + prescriptionDto.getPrescriptionNo();
    			resultList.add(s);
    		}
    	}   	
    	return resultList;
    }
    
    private void setDeleteList(){
    	Set<Entry<MedicationOrderDto, MedicationOrder>>  entrySet =  dtoEntityMap.entrySet();
    	Iterator<Entry<MedicationOrderDto, MedicationOrder>> iterator = entrySet.iterator();
    	while(iterator.hasNext()){
    		Entry<MedicationOrderDto, MedicationOrder> entry = iterator.next();
    		MedicationOrderDto medicationOrderDto = entry.getKey();
    		MedicationOrder medicationOrder = entry.getValue();
    		// 长期用药医嘱停止
			if(Constants.LONG_TERM_FLAG.equals(medicationOrder.getTemporaryFlag())){
				medicationOrder.setStopPerson(medicationOrderDto.getOrderPerson());
				medicationOrder.setStopPersonName(DictionaryUtils.getNameFromDictionary(
						Constants.DOMAIN_STAFF, medicationOrderDto.getOrderPerson(), medicationOrderDto.getOrderPersonName()));
				medicationOrder.setOrderStatus(Constants.ORDER_STATUS_STOP);
				medicationOrder.setOrderStatusName(DictionaryUtils.getNameFromDictionary(
						Constants.DOMAIN_ORDER_STATUS, Constants.ORDER_STATUS_STOP));
			} else {// 临时用药医嘱撤销
				medicationOrder.setCancelPerson(medicationOrderDto.getOrderPerson());
				medicationOrder.setCancelPersonName(DictionaryUtils.getNameFromDictionary(
						Constants.DOMAIN_STAFF, medicationOrderDto.getOrderPerson(), medicationOrderDto.getOrderPersonName()));
				medicationOrder.setOrderStatus(Constants.ORDER_STATUS_CANCEL);
				medicationOrder.setOrderStatusName(DictionaryUtils.getNameFromDictionary(
						Constants.DOMAIN_ORDER_STATUS, Constants.ORDER_STATUS_CANCEL));
			}
			medicationOrder.setUpdateby(serviceId);
    		medicationOrder.setUpdateTime(systemTime);
    	}
    	for(Prescription prescription : deletePrescriptionList){
			prescription.setDeleteby(serviceId);
    		prescription.setDeleteFlag(Constants.DELETE_FLAG);
    		prescription.setDeleteTime(systemTime);
    		prescription.setUpdateby(serviceId);
    		prescription.setUpdateTime(systemTime);
    	}
    }
        
    private List<NursingOperation> getNursingOperationList(PrescriptionDto prescriptionDto, List<MedicationOrder> medicationOrderList){
    	List<NursingOperation> noList = new ArrayList<NursingOperation>();
    	for(MedicationOrder medicationOrder : deleteMedicationOrderList){
    		NursingOperation  nursingOperation = new NursingOperation();
    		
    		BigDecimal seq = commonService.getSequence("SEQ_NURSING_OPERATION");
    		// 护理操作内部序列号
            nursingOperation.setOperationSn(seq);
            // 就诊内部序列号
            nursingOperation.setVisitSn(medicationOrder.getVisitSn());
            // 医嘱内部序列号
            nursingOperation.setOrderSn(medicationOrder.getOrderSn());           
            // 患者内部序列号
            nursingOperation.setPatientSn(patientSn);
            // 域代码
            nursingOperation.setPatientDomain(patientDomain);
            // 患者本地ID
            nursingOperation.setPatientLid(patientLid);            
            // 医嘱类型
            nursingOperation.setOrderType(medicationOrder.getOrderType());
            // 医嘱类型名稱
            nursingOperation.setOrderTypeName(medicationOrder.getOrderTypeName());           
            // 医嘱状态编码
            nursingOperation.setOrderStatusCode(medicationOrder.getOrderStatus());
            // 医嘱状态名称
            nursingOperation.setOrderStatusName(medicationOrder.getOrderStatusName());
            // 执行科室编码
            nursingOperation.setExecuteDeptCode(prescriptionDto.getPrescriptionDept());
            // 执行科室名称
            nursingOperation.setExecuteDeptName(DictionaryUtils.getNameFromDictionary(
            		Constants.DOMAIN_DEPARTMENT, prescriptionDto.getPrescriptionDept(), prescriptionDto.getPrescriptionDeptName()));
            
            if(Constants.LONG_TERM_FLAG.equals(medicationOrder.getTemporaryFlag())){
                // 操作人ID
                nursingOperation.setOperatorId(prescriptionDto.getPrescriptionDoctorId());
                // 操作人姓名
                nursingOperation.setOperatorName(DictionaryUtils.getNameFromDictionary(
                		Constants.DOMAIN_STAFF, prescriptionDto.getPrescriptionDoctorId(), prescriptionDto.getPrescriptionDoctorName()));
            } else {
                // 操作人ID
                nursingOperation.setOperatorId(prescriptionDto.getPrescriptionDoctorId());
                // 操作人姓名
                nursingOperation.setOperatorName(prescriptionDto.getPrescriptionDoctorId());
            }
            // 操作时间
            nursingOperation.setOperationTime(DateUtils.stringToDate(prescriptionDto.getPrescriptionTime()));            
            // 创建时间
            nursingOperation.setCreateTime(systemTime);
            // 更新时间
            nursingOperation.setUpdateTime(systemTime);
            // 更新回数
            nursingOperation.setUpdateCount(Constants.INSERT_UPDATE_COUNT);
            // 删除标志
            nursingOperation.setDeleteFlag(Constants.NO_DELETE_FLAG);
            // 增加人
            nursingOperation.setCreateby(DataMigrationUtils.getDataMigration() + serviceId);
            
            noList.add(nursingOperation);
    	}
    	return noList;
    }
    
    private boolean isDeleteMessage(PORXIN010370UV01 porxin010370uv01){
    	return Constants.DELETE_MESSAGE_FLAG.equals(porxin010370uv01.getTriggerEvent())
    			|| Constants.V2_CANCEL_MESSAGE_FLAG.equals(porxin010370uv01.getTriggerEvent())
    			|| Constants.CANCEL_MESSAGE_FLAG.equals(porxin010370uv01.getTriggerEvent())
    			|| Constants.STOP_MESSAGE_FLAG.equals(porxin010370uv01.getTriggerEvent());
    }
    
    private boolean isRenewMessage(PORXIN010370UV01 porxin010370uv01){
    	return Constants.RENEW_MESSAGE_FLAG.equals(porxin010370uv01.getTriggerEvent());
    }
    
    private boolean isRenewMessage(String newUpFlag){
    	return Constants.RENEW_MESSAGE_FLAG.equals(newUpFlag);
    }
    
    private boolean isUpdateMessage(PORXIN010370UV01 porxin010370uv01){
    	return Constants.V2_UPDATE_MESSAGE_FLAG.equals(porxin010370uv01.getTriggerEvent());
    }
    
    private boolean isNewMessage(PORXIN010370UV01 porxin010370uv01){
    	return Constants.NEW_MESSAGE_FLAG.equals(porxin010370uv01.getTriggerEvent())
    			|| Constants.V2_NEW_MESSAGE_FLAG.equals(porxin010370uv01.getTriggerEvent());
    }
}
