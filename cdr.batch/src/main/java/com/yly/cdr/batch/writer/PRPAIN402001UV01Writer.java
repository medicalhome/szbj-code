/**
 * 入院消息数据接入
 * 
 * @date 2012/04/17
 * @author jia_yanqing
 * @version 1.0
 */
package com.yly.cdr.batch.writer;

import java.math.BigDecimal;
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

import com.yly.cdr.cache.DictionaryCache;
import com.yly.cdr.core.Constants;
import com.yly.cdr.entity.MedicalVisit;
import com.yly.cdr.entity.Patient;
import com.yly.cdr.entity.PatientCrossIndex;
import com.yly.cdr.entity.PatientTemp;
import com.yly.cdr.hl7.dto.prpain402001uv01.PRPAIN402001UV01;
import com.yly.cdr.service.CommonService;
import com.yly.cdr.service.PatientService;
import com.yly.cdr.service.VisitService;
import com.yly.cdr.util.DataMigrationUtils;
import com.yly.cdr.util.DateUtils;
import com.yly.cdr.util.DictionaryUtils;
import com.yly.cdr.util.PropertiesUtils;
import com.yly.cdr.util.StringUtils;

@Component(value = "prpain402001uv01Writer")
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class PRPAIN402001UV01Writer implements BusinessWriter<PRPAIN402001UV01>
{

    private static final Logger logger = LoggerFactory.getLogger(PRPAIN402001UV01Writer.class);

    private static final Logger loggerBS310 = LoggerFactory.getLogger("BS310");

    // 患者內部序列号
    private BigDecimal patientSn;

    // 患者
    private Patient patient;

    // 患者本地id
    private String patientLid;

    private Date systemTime = DateUtils.getSystemTime();

    // 就診記錄
    private MedicalVisit medicalVisit;

    @Autowired
    private VisitService visitService;

    @Autowired
    private CommonService commonService;

    @Autowired
    private PatientService patientService;

    private static final String TURN_ON_FLAG = "1";
    
    // 消息id
    private String serviceId;
    
    @Autowired
    private AdtA01BabyTempWriter tmpWriter;
    
    /**
     * 业务数据非空检验
     */
    @Override
    public boolean validate(PRPAIN402001UV01 prpain402001uv01)
    {
    	// $Author :chang_xuewen
        // $Date : 2014/1/22 10:30$
        // [BUG]0042086 MODIFY BEGIN
        // $Author:tong_meng
        // $Date:2013/12/03 11:00
        // [BUG]0040270 ADD BEGIN
        /*if(!prpain402001uv01.getOrgCode().equals(prpain402001uv01.getMessage().getOrgCode()))
        {
            logger.error("消息头中的医疗机构代码与消息体中的医疗机构代码不一致！" +
                    "消息头医疗机构代码：{}，消息体医疗机构代码：{}",
                    prpain402001uv01.getMessage().getOrgCode(),
                    prpain402001uv01.getOrgCode());
            loggerBS310.error("消息头中的医疗机构代码与消息体中的医疗机构代码不一致！" +
                    "消息头医疗机构代码：{}，消息体医疗机构代码：{}",
                    prpain402001uv01.getMessage().getOrgCode(),
                    prpain402001uv01.getOrgCode());
            return false;
        }*/
    	if(StringUtils.isEmpty(prpain402001uv01.getOrgCode())){
    		// $Author :yang_mingjie
        	// $Date : 2014/06/26 10:09$
        	// [BUG]0045630 MODIFY BEGIN 
    		prpain402001uv01.setOrgCode(Constants.HOSPITAL_CODE);
    		prpain402001uv01.setOrgName(Constants.HOSPITAL_NAME);
    		//[BUG]0045630 MODIFY END
        }  
    	// [BUG]0042086 MODIFY END
        return true;
    }

    /**
     * 检查消息的依赖关系
     */
    @Override
    public boolean checkDependency(PRPAIN402001UV01 prpain402001uv01,
            boolean flag)
    {
    	
        // $Author :tongmeng
        // $Date : 2012/5/29 14:30$
        // [BUG]0006657 MODIFY BEGIN
        // 获取患者本地ID
        patientLid = prpain402001uv01.getPatientLid();
        // [BUG]0006657 MODIFY END

        // Author: yu_yzh
        // Date: 2016/01/15
        // 婴儿登记临时解决方案 ADD BEGIN
    	if(Constants.BABY_FLAG.equals(prpain402001uv01.getBabyFlag()) && !tmpWriter.checkBabyExists(prpain402001uv01.getPatientEid())){
    		patient = tmpWriter.addPatient(prpain402001uv01);
    		patientSn = patient.getPatientSn();
    		tmpWriter.addPatientCrossIndex(prpain402001uv01);
    		logger.debug("婴儿登记，新增患者记录");
    		return true;
    	}
        // 婴儿登记临时解决方案 ADD END
    	
        /*
         * if
         * (Constants.NEW_MESSAGE_FLAG.equals(prpain402001uv01.getNewUpFlag()))
         * { // 根据患者empi_id，deleteFlag，在患者信息表里查询患者信息 Patient patient =
         * patientService.getPatient(patientEid, Constants.NO_DELETE_FLAG); if
         * (patient != null) { patientSn = patient.getPatientSn();
         * logger.warn("在新增入院信息时相应的患者信息存在true: {}", "入院信息关联消息"); return true; }
         * // 如果empi_id不存在，则通过患者域，患者本地ID(门诊号），从患者临时表去查找患者 else { patient =
         * patientService.getPatientByTempPatient(
         * prpain402001uv01.getPatientDomain(),
         * prpain402001uv01.getOutpatientLid()); if(patient != null) { patientSn
         * = patient.getPatientSn(); logger.warn("在新增入院信息时相应的患者临时信息存在true: {}",
         * "入院信息关联消息"); return true; } else {
         * logger.warn("在新增入院信息时相应的患者信息和患者临时信息不存在false: {}", "入院信息关联消息"); return
         * false; } } }
         */
        // 新增时，不需要判断依赖关系
        if (Constants.NEW_MESSAGE_FLAG.equals(prpain402001uv01.getNewUpFlag()))
        {
        	boolean pFlag = findExistsPatient(prpain402001uv01, flag);
        	if(!pFlag) return false;
        	
        	medicalVisit = getMedicalVisit(prpain402001uv01);
            if (medicalVisit != null){
            	prpain402001uv01.setNewUpFlag(Constants.UPDATE_MESSAGE_FLAG);
            	logger.warn("新增入院信息时就诊已存在，做更新操作");
            }
            return true;
        }
        else if (Constants.UPDATE_MESSAGE_FLAG.equals(prpain402001uv01.getNewUpFlag()))
        {
            // 获取入院記錄
            medicalVisit = getMedicalVisit(prpain402001uv01);
            if (medicalVisit != null)
            {
                // visitSn = medicalVisit.getVisitSn();
                logger.warn("在更新入院信息时相应的就诊信息存在:{}", "入院信息关联消息");
                return true;
            }
            else
            {
                logger.error("MessageId:{};在更新入院信息时相应的就诊信息不存在，域ID："
                    + prpain402001uv01.getPatientDomain() + "，患者本地ID："
                    + patientLid + "，就诊次数：" + prpain402001uv01.getVisitTimes(),prpain402001uv01.getMessage().getId());
                if (flag)
                {
                    loggerBS310.error(
                            "Message:{},checkDependency:{}",
                            prpain402001uv01.toString(),
                            "在更新入院信息时相应的就诊信息不存在，域ID："
                                + prpain402001uv01.getPatientDomain()
                                + "，患者本地ID：" + patientLid + "，就诊次数："
                                + prpain402001uv01.getVisitTimes());
                }
                return false;
            }
        }
        else
        {
            logger.error("MessageId:{};错误的消息交互类型：" + prpain402001uv01.getNewUpFlag(),prpain402001uv01.getMessage().getId());
            if (flag)
            {
                loggerBS310.error("Message:{},checkDependency:{}",
                        prpain402001uv01.toString(), "错误的消息交互类型："
                            + prpain402001uv01.getNewUpFlag());
            }
            return false;
        }
    }

    /**
     * 校验成功后把取到的相应数据进行保存或更新操作
     * @param PRPAIN402001UV01 prpain402001uv01
     */
    @Override
    @Transactional
    public void saveOrUpdate(PRPAIN402001UV01 prpain402001uv01)
    {
        serviceId = prpain402001uv01.getMessage().getServiceId();
        
        // 获取入院消息中就诊表的内容
        MedicalVisit medicalVisit = getMessageContent(prpain402001uv01);

        // $Author :jia_yanqing
        // $Date : 2012/7/5 16:45$
        // [BUG]0007275 MODIFY BEGIN
        if (Constants.NEW_MESSAGE_FLAG.equals(prpain402001uv01.getNewUpFlag()))
        {
            // Patient patient = new Patient();
            // if (patientEid != null && !patientEid.isEmpty())
            // {
            // 根据患者empi_id，deleteFlag，在患者信息表里查询患者信息
            // patient = patientService.getPatient(patientEid,
            // Constants.NO_DELETE_FLAG);

            if (patient != null)
            {
                commonService.save(medicalVisit);
            }
            // }
            // [BUG]0007275 MODIFY BEGIN

            // 患者表中不存在患者信息，保存就诊信息的同时，保存患者基本信息及患者临时表信息
            else
            {
                // 获取入院消息中患者表的内容
                patient = getPatientContent(prpain402001uv01);

                // 获取入院消息中患者临时表的内容
                PatientTemp patientTemp = getPatientTempContent(prpain402001uv01);

                // 保存就诊记录，患者基本信息，患者临时信息
                visitService.saveWhenNoPatientExists(medicalVisit, patient,
                        patientTemp);
            }

        }
        else if (Constants.UPDATE_MESSAGE_FLAG.equals(prpain402001uv01.getNewUpFlag()))
        {
            commonService.update(medicalVisit);
        }
    }

    /**
     * 获取入院消息新增或者更新的内容
     * @param prpain402001uv01
     * @return MedicalVisit
     */
    private MedicalVisit getMessageContent(PRPAIN402001UV01 prpain402001uv01)
    {
        MedicalVisit medicalVisit = new MedicalVisit();

        if (Constants.NEW_MESSAGE_FLAG.equals(prpain402001uv01.getNewUpFlag()))
        {
            // 设置患者内部序列号
            medicalVisit.setPatientSn(patientSn);

            // 设置患者本地Lid
            medicalVisit.setPatientLid(patientLid);

            // 设置患者域代码
            medicalVisit.setPatientDomain(prpain402001uv01.getPatientDomain());

            // 设置创建时间
            medicalVisit.setCreateTime(systemTime);

            // 设置更新回数
            medicalVisit.setUpdateCount(Constants.INSERT_UPDATE_COUNT);
            
            // 创建者
            medicalVisit.setCreateby(DataMigrationUtils.getDataMigration() + serviceId);
            
            // 设置出院科室ID
            medicalVisit.setDischargeDeptId(prpain402001uv01.getVisitDept());

            // 设置出院科室名称
//            medicalVisit.setDischargeDeptName(prpain402001uv01.getVisitDeptName());
//            if(DictionaryCache.getDictionary(Constants.DOMAIN_DEPARTMENT) != null){
//            	medicalVisit.setDischargeDeptName(DictionaryCache.getDictionary(
//                        Constants.DOMAIN_DEPARTMENT).get(
//                        		prpain402001uv01.getVisitDept()));
//            }
            medicalVisit.setDischargeDeptName(DictionaryUtils.getNameFromDictionary(
            		Constants.DOMAIN_DEPARTMENT, prpain402001uv01.getVisitDept(), prpain402001uv01.getVisitDeptName()));
            
            // 设置出院病区
            medicalVisit.setDischargeWardId(prpain402001uv01.getAdmissionWards());

            // 设置出院病区名称
//            medicalVisit.setDischargeWardName(prpain402001uv0t1.getAdmissionWardsName());
            medicalVisit.setDischargeWardName(DictionaryUtils.getNameFromDictionary(
            		Constants.DOMAIN_WARD, prpain402001uv01.getAdmissionWards(), prpain402001uv01.getAdmissionWardsName()));
            
            // 设置出院床号
            medicalVisit.setDischargeBedNo(prpain402001uv01.getAdmissionBedNo());
            
            // $Author:tong_meng
            // $Date:2013/12/03 11:00
            // [BUG]0040270 ADD BEGIN
            // 医疗机构代码
            medicalVisit.setOrgCode(prpain402001uv01.getOrgCode());
            // 医疗机构名称
            medicalVisit.setOrgName(prpain402001uv01.getOrgName());
            // [BUG]0040270 ADD BEGIN
        }
        else if (Constants.UPDATE_MESSAGE_FLAG.equals(prpain402001uv01.getNewUpFlag()))
        {
            // 获取入院記錄
            medicalVisit = getMedicalVisit(prpain402001uv01);
            
            // 修改者
            medicalVisit.setUpdateby(serviceId);
            
            // 当更新的就诊没有对应的转区转床记录时才更新出院的病区科室床号
            List<Object> transferHistorys = visitService.selectTransferHistoryByVisitSn(medicalVisit.getVisitSn());
            if(transferHistorys == null || transferHistorys.size() == 0){
                // 设置出院科室ID
                medicalVisit.setDischargeDeptId(prpain402001uv01.getVisitDept());

                // 设置出院科室名称
//                medicalVisit.setDischargeDeptName(prpain402001uv01.getVisitDeptName());
                medicalVisit.setDischargeDeptName(DictionaryUtils.getNameFromDictionary(
                		Constants.DOMAIN_DEPARTMENT, prpain402001uv01.getVisitDept(), prpain402001uv01.getVisitDeptName()));
                
                // 设置出院病房
                medicalVisit.setDischargeWardId(prpain402001uv01.getAdmissionWards());

                // 设置出院病区名称
//                medicalVisit.setDischargeWardName(prpain402001uv01.getAdmissionWardsName());
                medicalVisit.setDischargeWardName(DictionaryUtils.getNameFromDictionary(
                		Constants.DOMAIN_WARD, prpain402001uv01.getAdmissionWards(), prpain402001uv01.getAdmissionWardsName()));
                
                // 设置出院床号
                medicalVisit.setDischargeBedNo(prpain402001uv01.getAdmissionBedNo());
            }
        }

        // 设置患者入院次数/就诊次数
        medicalVisit.setVisitTimes(StringUtils.strToBigDecimal(
                prpain402001uv01.getVisitTimes(), "入院次数/就诊次数"));

        // 设置住院（就诊）时间
        medicalVisit.setVisitDate(DateUtils.stringToDate(prpain402001uv01.getVisitDate()));
        // $Author :tong_meng
        // $Date : 2012/9/27 11:00$
        // [BUG]0009980 MODIFY BEGIN
        // $Author :tong_meng
        // $Date : 2012/6/26 10:33$
        // [BUG]0007418 MODIFY BEGIN
        // 设置入院状态/就诊状态
        medicalVisit.setVisitStatusCode(Constants.VISIT_STATUS_IN_HOSPITAL);
        // 设置紧急程度编码
        medicalVisit.setUrgentLevelCode(prpain402001uv01.getVisitStatus());
        // 设置紧急程度名称
//        medicalVisit.setUrgentLevelName(prpain402001uv01.getVisitStatusName());
        medicalVisit.setUrgentLevelName(DictionaryUtils.getNameFromDictionary(
        		Constants.URGENT_LEVEL, prpain402001uv01.getVisitStatus(), prpain402001uv01.getVisitStatusName()));
        // [BUG]0007418 MODIFY END
        // [BUG]0009980 MODIFY END

        // $Author :tong_meng
        // $Date : 2012/6/26 10:33$
        // [BUG]0007418 MODIFY BEGIN
        // 就诊状态名称
        if(DictionaryCache.getDictionary(Constants.DOMAIN_VISIT_STATUS) != null){
        	medicalVisit.setVisitStatusName(DictionaryCache.getDictionary(
                    Constants.DOMAIN_VISIT_STATUS).get(
                    Constants.VISIT_STATUS_IN_HOSPITAL));
        }
        
        // [BUG]0007418 MODIFY END

        // 设置病区号
        medicalVisit.setAdmissionWardId(prpain402001uv01.getAdmissionWards());

        // 设置病区名称
//        medicalVisit.setAdmissionWardName(prpain402001uv01.getAdmissionWardsName());
        medicalVisit.setAdmissionWardName(DictionaryUtils.getNameFromDictionary(
                		Constants.DOMAIN_WARD, prpain402001uv01.getAdmissionWards(), prpain402001uv01.getAdmissionWardsName()));
        // 设置病床号
        medicalVisit.setAdmissionBedNo(prpain402001uv01.getAdmissionBedNo());

        // 设置入院医生
        medicalVisit.setAdmissionDoctorId(prpain402001uv01.getAdmissionDoctor());

        // 设置入院医生姓名
//        medicalVisit.setAdmissionDoctorName(prpain402001uv01.getAdmissionDoctorName());
        medicalVisit.setAdmissionDoctorName(DictionaryUtils.getNameFromDictionary(
        		Constants.DOMAIN_STAFF, prpain402001uv01.getAdmissionDoctor(), prpain402001uv01.getAdmissionDoctorName()));

        // $Author:wei_peng
        // $Date:2012/7/19 14:53
        // [BUG]0008049 ADD BEGIN
        // 住院医生
        medicalVisit.setResidentDoctorId(prpain402001uv01.getResidentDoctor());
        // 住院医生姓名
        medicalVisit.setResidentDoctorName(prpain402001uv01.getResidentDoctorName());
        // 主治医生
        medicalVisit.setAttendingDoctorId(prpain402001uv01.getAttendingDoctor());
        // 主治医生姓名
        medicalVisit.setAttendingDoctorName(prpain402001uv01.getAttendingDoctorName());
        // 主任医生
        medicalVisit.setDeptDirectorId(prpain402001uv01.getDeptDoctor());
        // 主任医生姓名
        medicalVisit.setDeptDirectorName(prpain402001uv01.getDeptDoctorName());
        // [BUG]0008049 ADD END

        // $Author :tong_meng
        // $Date : 2012/6/26 10:33$
        // [BUG]0007418 MODIFY BEGIN
        // 设置入院科室ID
        medicalVisit.setVisitDeptId(prpain402001uv01.getVisitDept());
        // [BUG]0007418 MODIFY END

        // $Author :tong_meng
        // $Date : 2012/6/26 10:33$
        // [BUG]0007418 ADD BEGIN
        // 设置入院科室名称
//        medicalVisit.setVisitDeptName(prpain402001uv01.getVisitDeptName());
//        if(DictionaryCache.getDictionary(Constants.DOMAIN_DEPARTMENT) != null){
//        	medicalVisit.setVisitDeptName(DictionaryCache.getDictionary(
//                    Constants.DOMAIN_DEPARTMENT).get(
//                    		prpain402001uv01.getVisitDept()));
//        }
        medicalVisit.setVisitDeptName(DictionaryUtils.getNameFromDictionary(
        		Constants.DOMAIN_DEPARTMENT, prpain402001uv01.getVisitDept(), prpain402001uv01.getVisitDeptName()));
        // [BUG]0007418 ADD END

        // $Author :tong_meng
        // $Date : 2012/8/20 11:33$
        // [BUG]0009024 MODIFY BEGIN
        // 设置就诊类别
//        medicalVisit.setVisitTypeCode(prpain402001uv01.getVisitType());
        //就诊类别设置为住院
        medicalVisit.setVisitTypeCode(Constants.VISIT_TYPE_CODE_INPATIENT);

        // 设置就诊类别名称
        if(DictionaryCache.getDictionary(Constants.DOMAIN_VISIT_TYPE) != null){
        	medicalVisit.setVisitTypeName(DictionaryCache.getDictionary(
                    Constants.DOMAIN_VISIT_TYPE).get(
                    		prpain402001uv01.getVisitType()));
        }else{
        	medicalVisit.setVisitTypeName("住院");
        }
        
        
        // 设置就诊流水号
        medicalVisit.setVisitOrdNo(prpain402001uv01.getVisitOrdNo());

        // [BUG]0009024 MODIFY END

        // $Author :tong_meng
        // $Date : 2012/8/20 11:33$
        // [BUG]0009024 ADD BEGIN
        // 设置入院类别
        medicalVisit.setAdmitingSourceCode(prpain402001uv01.getAdmitingSourceCode());

        // 设置入院类别名称
//        medicalVisit.setAdmitingSourceName(prpain402001uv01.getAdmitingSourceName());
        medicalVisit.setAdmitingSourceName(DictionaryUtils.getNameFromDictionary(
        		Constants.DOMAIN_IPATIENT_SOURCE, prpain402001uv01.getAdmitingSourceCode(), prpain402001uv01.getAdmitingSourceName()));
        // [BUG]0009024 ADD BEGIN

        // 费用类别编码
        medicalVisit.setChargeClass(prpain402001uv01.getChargeClass());
        // 费用类别名称
//        medicalVisit.setChargeClassName(prpain402001uv01.getChargeClassName());
        medicalVisit.setChargeClassName(DictionaryUtils.getNameFromDictionary(
        		Constants.DOMAIN_CHARGE_INPATIENT, prpain402001uv01.getChargeClass(), prpain402001uv01.getChargeClassName()));
        // Author :jia_yanqing
        // Date : 2013/5/28 13:30
        // [BUG]0033056 ADD BEGIN 
        // 籍贯编码
        medicalVisit.setNativeplaceCode(prpain402001uv01.getNativeplaceCode());
        // 籍贯名称
        medicalVisit.setNativeplaceName(prpain402001uv01.getNativeplaceName());
        // [BUG]0033056 ADD END 
        
        // Author:wei_peng
        // Date:2013/7/26 15:04
        // [BUG]0035362 ADD BEGIN
        // 年龄
        medicalVisit.setPatientAge(prpain402001uv01.getAge());
        // [BUG]0035362 ADD END

        // 设置更新时间
        medicalVisit.setUpdateTime(systemTime);

        // 设置删除标志
        medicalVisit.setDeleteFlag(Constants.NO_DELETE_FLAG);

        return medicalVisit;
    }

    // 获取入院消息中患者表的内容
    public Patient getPatientContent(PRPAIN402001UV01 prpain402001uv01)
    {
        Patient patient = new Patient();

        // 患者姓名
        patient.setPatientName(prpain402001uv01.getPatientName());

        // 性别代码
        patient.setGenderCode(prpain402001uv01.getGenderCode());

        // $Author :tong_meng
        // $Date : 2012/7/1 10:33$
        // [BUG]0007418 ADD BEGIN
        // 设置性别名称
        if(DictionaryCache.getDictionary(Constants.DOMAIN_GENDER) != null){
        	patient.setGenderName(DictionaryCache.getDictionary(
                    Constants.DOMAIN_GENDER).get(prpain402001uv01.getGenderCode()));
        }
        
        // [BUG]0007418 ADD END

        // 出生日期
        patient.setBirthDate(DateUtils.stringToDate(prpain402001uv01.getBirthDate()));

        // $Author: wei_peng
        // $Date: 2012/6/20 10:30$
        // $[BUG]0007123 DELETE BEGIN
        // 婚姻状况
        // patient.setMarriageStatus(prpain402001uv01.getMarriageStatus());
        // 民族代码
        // patient.setNationCode(prpain402001uv01.getNationCode());
        // 出生地
        // patient.setBirthPlace(prpain402001uv01.getBirthPlace());
        // 国籍代码
        // patient.setNationalityCode(prpain402001uv01.getNationalityCode());
        // 文化程度代码
        // patient.setEducationDegree(prpain402001uv01.getEducationDegree());
        // 工作单位名称
        // patient.setWorkPlace(prpain402001uv01.getWorkPlace());
        // $[BUG]0007123 DELETE END

        // 创建时间
        patient.setCreateTime(systemTime);
        // 更新时间
        patient.setUpdateTime(systemTime);
        // 更新回数
        patient.setUpdateCount(Constants.INSERT_UPDATE_COUNT);
        // 删除标识
        patient.setDeleteFlag(Constants.NO_DELETE_FLAG);
        
        // 创建者
        patient.setCreateby(DataMigrationUtils.getDataMigration() + serviceId);

        return patient;
    }

    // 获取入院消息中患者临时表的内容
    public PatientTemp getPatientTempContent(PRPAIN402001UV01 prpain402001uv01)
    {
        PatientTemp patientTemp = new PatientTemp();

        // 域代码
        patientTemp.setPatientDomain(prpain402001uv01.getPatientDomain());

        // 患者本地ID
        patientTemp.setPatientLid(patientLid);

        // 姓名
        patientTemp.setPatientName(prpain402001uv01.getPatientName());

        // 性别代码
        patientTemp.setGenderCode(prpain402001uv01.getGenderCode());

        // $Author :tong_meng
        // $Date : 2012/7/1 10:33$
        // [BUG]0007418 ADD BEGIN
        // 设置患者性别名称
        if(DictionaryCache.getDictionary(Constants.DOMAIN_GENDER) != null){
        	patientTemp.setGenderName(DictionaryCache.getDictionary(
                    Constants.DOMAIN_GENDER).get(prpain402001uv01.getGenderCode()));
        }
        
        // [BUG]0007418 ADD END

        // 出生日期
        patientTemp.setBirthDate(DateUtils.stringToDate(prpain402001uv01.getBirthDate()));

        // 创建时间
        patientTemp.setCreateTime(systemTime);
        // 更新时间
        patientTemp.setUpdateTime(systemTime);
        // 更新回数
        patientTemp.setUpdateCount(Constants.INSERT_UPDATE_COUNT);
        // 删除标识
        patientTemp.setDeleteFlag(Constants.NO_DELETE_FLAG);
        
        // 创建者
        patientTemp.setCreateby(DataMigrationUtils.getDataMigration() + serviceId);

        return patientTemp;
    }

    // $Author :jia_yanqing
    // $Date : 2012/7/5 16:45$
    // [BUG]0007275 DELETE BEGIN
    /**
     * 得到患者empi_id
     * @return 患者empi_id
     */
    // public String getPatientEid(PRPAIN402001UV01 prpain402001uv01)
    // {
    // 如果消息里有患者empi_id, 得到消息的empi_id
    // EmpiPerson person = empiService.getEMPIPerson(
    // prpain402001uv01.getPatientDomain(),
    // prpain402001uv01.getPatientLid());
    // if (person != null)
    // return person.getEmpiId();
    // 如果消息里没有，根据消息里的门诊号，就诊次数，患者域，从empi查找患者empi_id
    // else
    // {
    // TODO 根据门诊号，就诊次数，患者域，从empi查找患者empi_id
    // String outPatientNo = t.getOutpatientNo();
    // String patientDomain = t.getPatientDomain();
    // String visitTimes = t.getVisitTimes();
    // 因为EMPI服务还没有实现，仅测试用
    // patientEid = "123";
    // logger.error("EMPI ID 找不到！");
    // return null;
    // }
    // }
    // [BUG]0007275 DELETE END

    /**
     * 得到消息关联的就诊记录
     * @param toHospital
     * @return 就诊记录
     */
    public MedicalVisit getMedicalVisit(PRPAIN402001UV01 prpain402001uv01)
    {
        // 患者域ID
        String patientDomain = prpain402001uv01.getPatientDomain();
        // 就诊次数
        String visitTimes = prpain402001uv01.getVisitTimes();

        MedicalVisit medicalVisit = commonService.mediVisit(patientDomain,
                patientLid, visitTimes, prpain402001uv01.getOrgCode(),prpain402001uv01.getVisitOrdNo(),prpain402001uv01.getVisitType());

        return medicalVisit;
    }

    /**
     * 根据消息中域ID和患者ID从患者交叉索引表中，取得患者EID，如果找不到相应记录，则从患者基本信息（临时）表中，取得患者内部序列号。
     * @param t
     */
    public boolean findExistsPatient(PRPAIN402001UV01 prpain402001uv01,
            boolean flag)
    {
        String checkFlag = PropertiesUtils.getValue("checkDependency.default");

        // 根据域ID，患者Lid从患者交叉索引查询患者相关信息
        Map<String, Object> condition = new HashMap<String, Object>();
        condition.put("patientDomain", prpain402001uv01.getPatientDomain());
        condition.put("patientLid", prpain402001uv01.getPatientLid());
        condition.put("deleteFlag", Constants.NO_DELETE_FLAG);
        // $Author:tong_meng
        // $Date:2013/12/03 11:00
        // [BUG]0040270 ADD BEGIN
        // TODO
        condition.put("orgCode", prpain402001uv01.getOrgCode());
        // [BUG]0040270 ADD END

        PatientCrossIndex patientCrossIndex = patientService.getPatientCrossIndexByCondition(condition);
        // 如果患者交叉索引表中存在该患者的相关信息，则取出该患者的EMPI_ID，然后根据EMPI_ID，删除标志查询患者信息表
        // 获得患者信息
        if (patientCrossIndex != null)
        {
            String patientEid = patientCrossIndex.getPatientEid();
            patient = patientService.getPatient(patientEid,
                    Constants.NO_DELETE_FLAG);
        }
        // 如果交叉索引表中不存在访患者的相关信息，则通过域ID，患者本地Lid查询患者临时表获得患者信息
        else
        {
            patient = patientService.getPatientByTempPatient(
                    prpain402001uv01.getPatientDomain(),
                    prpain402001uv01.getPatientLid(),prpain402001uv01.getOrgCode());
        }

        // V2消息中若有EID，可根据EID查询患者
        if(patient == null && prpain402001uv01.getPatientEid() != null){
        	patient = patientService.getPatient(prpain402001uv01.getPatientEid(), Constants.NO_DELETE_FLAG);
        }
        
        // 开关未开启才进行关联的判断
        if (patient == null && !TURN_ON_FLAG.equals(checkFlag.trim()))
        {
            logger.error("MessageId:{};通过患者交叉信息表和患者临时表均找不到患者信息，域ID："
                + prpain402001uv01.getPatientDomain() + "，患者本地ID："
                + prpain402001uv01.getPatientLid(),
                    prpain402001uv01.getMessage().getId() + "，医疗机构代码"
                        + prpain402001uv01.getOrgCode());
            if (flag)
            {
                loggerBS310.error(
                        "Message:{},checkDependency:{}",
                        prpain402001uv01.toString(),
                        "通过患者交叉信息表和患者临时表均找不到患者信息，域ID："
                            + prpain402001uv01.getPatientDomain() + "，患者本地ID："
                            + prpain402001uv01.getPatientLid() + "，医疗机构代码"
                            + prpain402001uv01.getOrgCode());
            }
            return false;
        }
        else
        {
            patientSn = patient.getPatientSn();
            return true;
        }

    }

}
