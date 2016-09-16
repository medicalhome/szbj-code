/**
 * [FUN]A01-002挂号信息
 * 
 * @version 1.0, 2012/04/09  20:23:00
 
 * @author wu_jianfeng
 * @since 1.0
*/

package com.founder.cdr.batch.writer;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.founder.cdr.cache.DictionaryCache;
import com.founder.cdr.core.Constants;
import com.founder.cdr.entity.MedicalVisit;
import com.founder.cdr.entity.Patient;
import com.founder.cdr.entity.PatientCrossIndex;
import com.founder.cdr.entity.PatientTemp;
import com.founder.cdr.hl7.dto.PatientDto;
import com.founder.cdr.hl7.dto.prpain400001uv01.PRPAIN400001UV01;
import com.founder.cdr.service.CommonService;
import com.founder.cdr.service.PatientService;
import com.founder.cdr.service.VisitService;
import com.founder.cdr.util.DataMigrationUtils;
import com.founder.cdr.util.DateUtils;
import com.founder.cdr.util.DictionaryUtils;
import com.founder.cdr.util.PropertiesUtils;
import com.founder.cdr.util.StringUtils;

@Component(value = "prpain400001uv01Writer")
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class PRPAIN400001UV01Writer implements BusinessWriter<PRPAIN400001UV01>
{

    private static final Logger logger = LoggerFactory.getLogger(PRPAIN400001UV01Writer.class);

    private static final Logger loggerBS001 = LoggerFactory.getLogger("BS001");

    @Autowired
    private VisitService visitService;

    @Autowired
    private PatientService patientService;

    @Autowired
    private CommonService commonService;

    // 与就诊关联的患者
    private Patient patient;

    // 开关开启标识
    private static final String TURN_ON_FLAG = "1";

    // 得到系统当前时间
    private Date systemTime = DateUtils.getSystemTime();

    // 消息id
    private String serviceId;

    /**
     * 业务数据检验
     */
    @Override
    public boolean validate(PRPAIN400001UV01 t)
    {
    	if(Constants.lstDomainInPatient().contains(t.getPatientDomain()) &&
    			Constants.lstVisitTypeInPatient().contains(t.getVisitType())){
    		loggerBS001.error("Message:{}, validate:{}", t.toString(), "挂号的类型不是门诊！");
    		return false;
    	}
    	//挂号时校验，挂号科室，挂号科室名称，就诊时间非空
    	if(!Constants.WITHDRAW_REGISTER_MESSAGE_FLAG.equals(t.getTriggerEventCode())){
    		 // 门诊删除情况下验证申请单号是否为空
            if (StringUtils.isEmpty(t.getRegisteredDept())
            		||StringUtils.isEmpty(t.getVisitDate()))
            {
            	loggerBS001.error("Message:{},validate：{}",
                        t.toString(),
                        "挂号科室、就诊日期不能为空！ 就诊流水号：visitOrdNo = " + t.getVisitOrdNo());

                return false;
            }
    	}
        // // $Author :tong_meng
        // // $Date : 2012/5/15 11:10$
        // // [BUG]0006011 ADD BEGIN
        // // 增加就诊表中挂号序号的类型判断
        // String registeredSequence = t.getRegisteredSequence();
        // String reg = "^\\+?[1-9]*[0-9]*$";
        // if (!registeredSequence.matches(reg))
        // {
        // logger.error("消息数据中的registeredSequence与medical_visit的registered_sequence类型不匹配！");
        // return false;
        // }
        // // [BUG]0006011 ADD END
    	
    	
    	// $Author :chang_xuewen
        // $Date : 2014/1/22 10:30$
        // [BUG]0042086 MODIFY BEGIN
        // $Author:tong_meng
        // $Date:2013/12/03 11:00
        // [BUG]0040270 ADD BEGIN
        /*if (!StringUtils.isNotNullAll(t.getPatients().get(0).getOrgCode(),
                t.getPatients().get(0).getOrgName()))
        {
            logger.error("医疗机构代码：{},医疗机构名称：{}，都不能为空！",
                    t.getPatients().get(0).getOrgCode(),
                    t.getPatients().get(0).getOrgName());
            return false;
        }
        if(!t.getPatients().get(0).getOrgCode().equals(t.getMessage().getOrgCode()))
        {
            logger.error("消息头中的医疗机构代码与消息体中的医疗机构代码不一致！" +
                    "消息头医疗机构代码：{}，消息体医疗机构代码：{}",
                    t.getMessage().getOrgCode(),
                    t.getPatients().get(0).getOrgCode());
            loggerBS001.error("消息头中的医疗机构代码与消息体中的医疗机构代码不一致！" +
                    "消息头医疗机构代码：{}，消息体医疗机构代码：{}",
                    t.getPatients().get(0).getMessage().getOrgCode(),
                    t.getPatients().get(0).getOrgCode());
            return false;
        }*/
        // [BUG]0040270 ADD END
    	// $Author :chang_xuewen
        // $Date : 2014/1/26 15:30$
        // [BUG]0042144 MODIFY BEGIN
    	// $Author :yang_mingjie
    	// $Date : 2014/06/26 10:09$
    	// [BUG]0045630 MODIFY BEGIN 
    	String orgCode=Constants.HOSPITAL_CODE;
        String orgName=Constants.HOSPITAL_NAME;
      //[BUG]0045630 MODIFY END
        for(PatientDto rp : t.getPatients()){
        	if(!StringUtils.isEmpty(rp.getOrgCode())){
            	orgCode = rp.getOrgCode();
            	orgName = rp.getOrgName();
            	break;
            }  
        }    
        for(PatientDto rp : t.getPatients()){
        	if(StringUtils.isEmpty(rp.getOrgCode())){
            	rp.setOrgCode(orgCode);
            	rp.setOrgName(orgName);
            }  
        }     
        // [BUG]0042144 MODIFY END
    	// [BUG]0042086 MODIFY END
        // 检查挂号的域必须是门诊
//        if (t.getPatientDomain().equals(Constants.PATIENT_DOMAIN_OUTPATIENT))
//            return true;
//
//        loggerBS001.error("Message:{}, validate:{}", t.toString(), "挂号的域不是门诊！");
        return true;
    }

    /**
     * 检查消息的依赖关系
     */
    @Override
    public boolean checkDependency(PRPAIN400001UV01 t, boolean flag)
    {
        String checkFlag = PropertiesUtils.getValue("checkDependency.default");

        // 如果是新消息，查找已经存在的患者
        if (isNewMessage(t))
        {
            // $Author :wu_biao
            // $Date : 2012/10/10 14:44$
            // [BUG]9807 ADD BEGIN
            if (TURN_ON_FLAG.equals(checkFlag.trim()))
            {
                findExistsPatient(t, flag);
                return true;
            }
            else
            {
                return findExistsPatient(t, flag);
            }
            // [BUG]9807 ADD END
        }
        // 如果是退号消息，检查依赖的就诊记录
        else if (isWithdrawRegisterMessage(t))
        {
            return checkMedicalVisitExists(t, flag);
        }
        // 没有匹配的消息交互类型
        logger.error("MessageId:{};错误的消息交互类型！", t.getMessage().getId());
        if (flag)
        {
            loggerBS001.error("Message:{},checkDependency:{}", t.toString(),
                    "错误的消息交互类型！");
        }
        return false;
    }

    /**
     * 校验成功后把取到的相应数据进行保存或更新操作
     * @param baseDto xml文件中获取的检验报告CDA相应业务数据
     */
    @Override
    @Transactional
    public void saveOrUpdate(PRPAIN400001UV01 t)
    {
        serviceId = t.getMessage().getServiceId();

        // $Author :tong_meng
        // $Date : 2012/10/12 15:44$
        // [BUG]0010353 MODIFY BEGIN
        MedicalVisit visit = null;

        // 如果新消息，需要考虑患者是否存在
        if (isNewMessage(t))
        {
            // 如果患者不存在，保存临时患者和就诊
            if (!checkPatientExists())
            {
                visit = ConvertDtoToVisitEntity(t);
                visitService.saveWhenNoPatientExists(visit,
                        ConvertDtoToPatientEntity(t),
                        ConvertDtoToPatientTempEntity(t));
            }
            // 如果患者存在，保存就诊
            else
            {
                // 检查就诊表中属否存在这条记录。
                visit = getMedicalVisit(t);
                if (visit == null)
                {
                    // 得到要保存的就诊信息
                    visit = ConvertDtoToVisitEntity(t);
                    commonService.save(visit);
                }
                else
                {
                    // 更新相关属性
                    visit = updateNewMessage(t, visit);
                    commonService.update(visit);
                }
            }
        }
        // 如果是消息更新，更新原消息
        /*
         * else if (isUpdateMessage(t)) { visit = ConvertDtoToVisitEntity(t);
         * commonService.updatePartially(visit, "visitTypeCode",
         * "visitTypeName", "registeredDate", "registeredDeptId",
         * "registeredDeptName", "registeredDoctorId", "registeredDoctorName",
         * "insuranceType", "insuranceTypeName", "registrationClassCode",
         * "registrationClassName", "registrationMethodCode",
         * "registrationMethodName", "registeredWayCode", "registeredWayName",
         * "registeredSequence", "registeredAmpm", "updateTime"); }
         */
        // 如果是退号消息，更新原消息
        else if (isWithdrawRegisterMessage(t))
        {
            visit = ConvertDtoToVisitEntity(t);

            // $Author:jin_peng
            // $Date:2013/07/31 14:45
            // $[BUG]0035481 MODIFY BEGIN
            commonService.updatePartially(visit, "visitStatusCode",
                    "visitStatusName", "updateTime", "updateby");

            // $[BUG]0035481 MODIFY END
        }
        // [BUG]0010353 MODIFY END
    }

    /**
     * 是否是新消息
     * @return 如果是新消息，返回true，如果不是，返回false
     */
    public boolean isNewMessage(PRPAIN400001UV01 t)
    {
        return Constants.NEW_MESSAGE_FLAG.equals(t.getTriggerEventCode());
    }

    /**
     * 是否是消息更新
     * @return 如果是更新消息，返回true，如果不是，返回false
     */
    public boolean isUpdateMessage(PRPAIN400001UV01 t)
    {
        return Constants.UPDATE_MESSAGE_FLAG.equals(t.getTriggerEventCode());
    }

    /**
     * 是否是退号消息
     * @return 如果是更新消息，返回true，如果不是，返回false
     */
    public boolean isWithdrawRegisterMessage(PRPAIN400001UV01 t)
    {
        return Constants.WITHDRAW_REGISTER_MESSAGE_FLAG.equals(t.getTriggerEventCode());
    }

    /**
     * 检查消息关联的就诊是否存在
     * @param t
     * @return 如果存在返回true，否则返回false
     */
    public boolean checkMedicalVisitExists(PRPAIN400001UV01 t, boolean flag)
    {
        // 得到关联的就诊记录
        MedicalVisit visit = getMedicalVisit(t);

        // 如果不存在关联的就诊纪录，返回false
        if (visit == null)
        {
            logger.error(
                    "MessageId:{};就诊记录不存在:就诊次数:" + t.getVisitTimes()
                        + "患者本地ID:" + t.getPatientLid() + "域ID:"
                        + t.getPatientDomain(), t.getMessage().getId());
            if (flag)
            {
                loggerBS001.error(
                        "Message:{},checkDependency:{}",
                        t.toString(),
                        "就诊记录不存在:就诊次数:" + t.getVisitTimes() + "患者本地ID:"
                            + t.getPatientLid() + "域ID:" + t.getPatientDomain());
            }
            return false;
        }

        return true;
    }

    // $Author :jia_yanqing
    // $Date : 2012/7/5 16:45$
    // [BUG]0007275 MODIFY BEGIN

    /**
     * 检查消息关联的患者是否存在
     * @param t
     * @return 
     */
    public boolean findExistsPatient(PRPAIN400001UV01 t, boolean flag)
    {
        // 根据域ID，患者Lid从患者交叉索引查询患者相关信息
        Map<String, Object> condition = new HashMap<String, Object>();
        condition.put("patientDomain", t.getPatientDomain());
        condition.put("patientLid", t.getPatientLid());
        condition.put("deleteFlag", Constants.NO_DELETE_FLAG);
        // $Author:tong_meng
        // $Date:2013/12/03 11:00
        // [BUG]0040270 ADD BEGIN
        // TODO
        condition.put("orgCode", t.getPatients().get(0).getOrgCode());
        // [BUG]0040270 ADD END
        PatientCrossIndex patientCrossIndex = patientService.getPatientCrossIndexByCondition(condition);
        // 如果患者交叉索引表中存在该患者的相关信息，则取出该患者的EMPI_ID，然后根据EMPI_ID，删除标志查询患者信息表
        // 获得患者信息
        String patientEid = null;
        if (patientCrossIndex != null)
        {
            patientEid = patientCrossIndex.getPatientEid();
            patient = patientService.getPatient(patientEid,
                    Constants.NO_DELETE_FLAG);
        }
        // 如果交叉索引表中不存在访患者的相关信息，则通过域ID，患者本地Lid查询患者临时表获得患者信息
        else
        {
            patient = patientService.getPatientByTempPatient(
                    t.getPatientDomain(), t.getPatientLid(), t.getPatients().get(0).getOrgCode());
        }
        
        // V2消息中若有EID，可根据EID查询患者
        if(patient == null && t.getPatientEid() != null){
        	patient = patientService.getPatient(t.getPatientEid(), Constants.NO_DELETE_FLAG);
        }
        
        // $Author :jia_yanqing
        // $Date : 2012/7/10 18:10$
        // [BUG]0007870 ADD BEGIN
        if (patient == null)
        {
            logger.error("MessageId:{};通过患者交叉信息表和患者临时表均找不到患者信息",
                    t.getMessage().getId());
            if (flag)
            {
                loggerBS001.error("Message:{},checkDependency:{}",
                        t.toString(),
                        "EID:" + patientEid + "患者本地ID:" + t.getPatientLid()
                            + "域ID:" + t.getPatientDomain());
            }
            return false;
            // [BUG]0007870 ADD END
        }
        else
        {
            return true;
        }
    }

    // [BUG]0007275 MODIFY END

    /**
     * 检查消息关联的患者是否存在
     * @param t
     * @return 如果存在返回true，否则返回false
     */
    public boolean checkPatientExists()
    {
        return patient != null;
    }

    /**
     * 得到消息关联的就诊记录
     * @param t
     * @return 就诊记录
     */
    public MedicalVisit getMedicalVisit(PRPAIN400001UV01 t)
    {
        // 根据患者本地ID(门诊号)，域代码，就诊次数以及删除标识查找就诊记录
        Map<String, Object> condition = new HashMap<String, Object>();
        if (TURN_ON_FLAG.equals(Constants.MEDICAL_VISIT_CONFIM_LOGIC_TYPE)){
        	condition.put("patientLid", t.getPatientLid());
        	condition.put("patientDomain", t.getPatientDomain());
        	condition.put("orgCode", t.getPatients().get(0).getOrgCode());
        	condition.put("visitTimes",
                  StringUtils.strToBigDecimal(StringUtils.isEmpty(t.getVisitTimes()) ? null:t.getVisitTimes(), "就诊次数"));
        }else{
        	condition.put("patientLid", t.getPatientLid());
        	condition.put("orgCode", t.getPatients().get(0).getOrgCode());
        	condition.put("visitOrdNo", t.getVisitOrdNo());
        	condition.put("visitTypeCode", t.getVisitType());
        }
        condition.put("deleteFlag", Constants.NO_DELETE_FLAG);

        return visitService.selectVisitByCondition(condition);
    }

    // $Author :jia_yanqing
    // $Date : 2012/7/5 16:45$
    // [BUG]0007275 DELETE BEGIN
    /**
     * 得到患者empi_id
     * @return 患者empi_id
     */
    // public String getPatientEid(PRPAIN400001UV01 t)
    // {
    // $Author :tongmeng
    // $Date : 2012/5/22 13:45$
    // [BUG]0006257 MODIFY BEGIN
    // 根据域ID，门诊号去EMPIPerson表中查找患者这个人
    // EmpiPerson person = empiService.getEMPIPerson(t.getPatientDomain(),
    // t.getPatientLid());
    // 存在，将eid返回
    // if (person != null)
    // return person.getEmpiId();
    // [BUG]0006257 MODIFY END
    // 不存在
    // else
    // {
    // TODO 根据门诊号，就诊次数，患者域，从empi查找患者empi_id
    // String outPatientNo = t.getOutpatientNo();
    // String patientDomain = t.getPatientDomain();
    // String visitTimes = t.getVisitTimes();
    // 因为EMPI服务还没有实现， 还需要考虑empi正常和异常的情况）
    // 仅测试用
    // patientEid = "123";
    // logger.error("EMPI ID找不到！");
    // return null;
    // }
    // }
    // [BUG]0007275 DELETE END

    /**
     * 将消息里插入数据，封装到实体里
     * @return 要新增患者实体
     */
    public Patient ConvertDtoToPatientEntity(PRPAIN400001UV01 t)
    {
        Patient patient = new Patient();
        PatientDto patientDto = t.getPatients().get(0);
        //
        // patient.setPatientSn();
        // 患者姓名
        patient.setPatientName(patientDto.getPatientName());
        // 性别代码
        patient.setGenderCode(patientDto.getGenderCode());

        // $Author :tong_meng
        // $Date : 2012/7/1 10:33$
        // [BUG]0007418 ADD BEGIN
        // 设置性别名称
        patient.setGenderName(DictionaryCache.getDictionary(
                Constants.DOMAIN_GENDER).get(patientDto.getGenderCode()));
        // [BUG]0007418 ADD END

        // 出生日期
        patient.setBirthDate(DateUtils.stringToDate(patientDto.getBirthDate()));
        // // 婚姻状况
        // patient.setMarriageStatus(patientDto.getMarriageStatus());
        // // 民族代码
        // patient.setNationCode(patientDto.getNationCode());
        // // 出生地
        // patient.setBirthPlace(patientDto.getBirthPlace());
        // // 国籍代码
        // patient.setNationalityCode(patientDto.getNationalityCode());
        // // 职业代码
        // patient.setOccupationCode(patientDto.getOccupationCode());
        // // 文化程度代码
        // patient.setEducationDegree(patientDto.getEducationDegree());
        // // 工作单位名称
        // patient.setWorkPlace(patientDto.getWorkPlace());
        // // 家庭电话
        // patient.setTelephone(patientDto.getTelephone());

        // TODO 移动电话
        // patient.setMobile(patientDto.getMobile());
        // TODO 电子邮件地址
        // patient.setEmail(patientDto.getEmail());
        // TODO ABO血型
        // patient.setBloodAbo(patientDto.getBloodAbo());
        // TODO RH血型
        // patient.setBloodRh(patientDto.getBloodRh());

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

    /**
     * 将消息里插入数据，封装到实体里
     * @return 要新增临时患者实体
     */
    public PatientTemp ConvertDtoToPatientTempEntity(PRPAIN400001UV01 t)
    {
        PatientTemp tempPatient = new PatientTemp();
        PatientDto patientDto = t.getPatients().get(0);

        // 域代码
        tempPatient.setPatientDomain(patientDto.getPatientDomain());
        // 患者本地ID
        tempPatient.setPatientLid(patientDto.getPatientLid());
        // 患者姓名
        tempPatient.setPatientName(patientDto.getPatientName());
        // 性别代码
        tempPatient.setGenderCode(patientDto.getGenderCode());

        // $Author:wei_peng
        // $Date:2012/8/31 18:16
        // [BUG]0009322 MODIFY BEGIN
        // $Author :tong_meng
        // $Date : 2012/7/1 10:33$
        // [BUG]0007418 ADD BEGIN
        // 设置患者性别名称
        tempPatient.setGenderName(DictionaryCache.getDictionary(
                Constants.DOMAIN_GENDER).get(patientDto.getGenderCode()));
        // [BUG]0007418 ADD END
        // [BUG]0009322 MODIFY END
        // 出生日期
        tempPatient.setBirthDate(DateUtils.stringToDate(patientDto.getBirthDate()));
        // 创建时间
        tempPatient.setCreateTime(systemTime);
        // 更新时间
        tempPatient.setUpdateTime(systemTime);
        // 更新回数
        tempPatient.setUpdateCount(Constants.INSERT_UPDATE_COUNT);
        // 删除标识
        tempPatient.setDeleteFlag(Constants.NO_DELETE_FLAG);
        // 创建者
        tempPatient.setCreateby(DataMigrationUtils.getDataMigration() + serviceId);

        return tempPatient;
    }

    /**
     * 将消息里插入或者更新的数据，封装到实体里
     * @return 要新增或更新的就诊实体
     */
    public MedicalVisit ConvertDtoToVisitEntity(PRPAIN400001UV01 t)
    {
        MedicalVisit visit = null;

        // $Author :tong_meng
        // $Date : 2012/10/15 11:00$
        // [BUG]0010353 MODIFY BEGIN
        if (isNewMessage(t))
        {
            visit = new MedicalVisit();
            visit = updateNewMessage(t, visit);
            // 患者内部序列号
            if (checkPatientExists())
                visit.setPatientSn(patient.getPatientSn());

            // 域代码（患者域）
            visit.setPatientDomain(t.getPatientDomain());
            // 患者本地ID(门诊号)
            visit.setPatientLid(t.getPatientLid());

            // $Author :tongmeng
            // $Date : 2012/5/22 13:45$
            // [BUG]0006257 ADD BEGIN
            // 患者年龄
            visit.setPatientAge(t.getPatients().get(0).getAge());
            // [BUG]0006257 ADD END

            // 就诊次数
            visit.setVisitTimes(StringUtils.strToBigDecimal(t.getVisitTimes(),
                    "就诊次数"));

            // 就诊流水号
            visit.setVisitOrdNo(t.getVisitOrdNo());
            
            String visitStatus = Constants.VISIT_STATUS_REGISTER;
            // $Author :tong_meng
            // $Date : 2012/6/26 10:33$
            // [BUG]0007418 MODIFY BEGIN
            
            // 就诊状态代码-1
            visit.setVisitStatusCode(visitStatus);
            // [BUG]0007418 MODIFY END

            // $Author :tong_meng
            // $Date : 2012/6/26 10:33$
            // [BUG]0007418 MODIFY BEGIN
            // 就诊状态名称-正常
            if(DictionaryCache.getDictionary(Constants.DOMAIN_VISIT_STATUS) != null){
            	visit.setVisitStatusName(DictionaryCache.getDictionary(
                        Constants.DOMAIN_VISIT_STATUS).get(
                        		visitStatus));
            }
            
            // [BUG]0007418 MODIFY END
            
            // $Author:tong_meng
            // $Date:2013/12/03 11:00
            // [BUG]0040270 ADD BEGIN
            // 医疗机构代码
//            visit.setOrgCode(t.getPatients().get(0).getOrgCode());
            visit.setOrgCode(Constants.HOSPITAL_CODE);
            // 医疗机构名称
//            visit.setOrgName(t.getPatients().get(0).getOrgName());
            visit.setOrgName(Constants.HOSPITAL_NAME);
            // [BUG]0040270 ADD END

            // 创建时间
            visit.setCreateTime(systemTime);
            // 更新回数
            visit.setUpdateCount(Constants.INSERT_UPDATE_COUNT);
            // 删除标识
            visit.setDeleteFlag(Constants.NO_DELETE_FLAG);
            // 创建者
            visit.setCreateby(DataMigrationUtils.getDataMigration() + serviceId);
        }
        // [BUG]0010353 MODIFY END
        // 如果是更新消息或者退号消息，得到消息关联的就诊记录
        else if (isWithdrawRegisterMessage(t))
        {
            visit = getMedicalVisit(t);
            // 就诊状态编码-9
            visit.setVisitStatusCode(Constants.VISIT_STATUS_REFUND);
            // 就诊状态名称-退号
            if(DictionaryCache.getDictionary(Constants.DOMAIN_VISIT_STATUS) != null){
            	visit.setVisitStatusName(DictionaryCache.getDictionary(
                        Constants.DOMAIN_VISIT_STATUS).get(
                        Constants.VISIT_STATUS_REFUND));
            }
            // $Author:jin_peng
            // $Date:2013/07/31 14:45
            // $[BUG]0035481 DELETE BEGIN
            // 删除时间
            // visit.setDeleteTime(systemTime);
            // 删除标识
            // visit.setDeleteFlag(Constants.DELETE_FLAG);

            // $[BUG]0035481 DELETE END

            // $Author:jin_peng
            // $Date:2013/07/31 14:45
            // $[BUG]0035481 ADD BEGIN
            visit.setUpdateTime(systemTime);

            // $[BUG]0035481 ADD END

            // 修改者
            visit.setUpdateby(serviceId);
        }

        return visit;
    }

    // $Author :tong_meng
    // $Date : 2012/10/15 11:00$
    // [BUG]0010353 ADD BEGIN
    private MedicalVisit updateNewMessage(PRPAIN400001UV01 t, MedicalVisit visit)
    {
        // $Author :tong_meng
        // $Date : 2012/6/26 10:33$
        // [BUG]0007418 MODIFY BEGIN
        // 就诊类别代码
        visit.setVisitTypeCode(t.getVisitType());
        // [BUG]0007418 MODIFY END

        // $Author :tong_meng
        // $Date : 2012/6/26 10:33$
        // [BUG]0007418 ADD BEGIN
        // 就诊类别名称
        if(DictionaryCache.getDictionary(Constants.DOMAIN_VISIT_TYPE) != null){
        	visit.setVisitTypeName(DictionaryCache.getDictionary(
                    Constants.DOMAIN_VISIT_TYPE).get(
                    		t.getVisitType()));
        }else{
        	visit.setVisitTypeName("门诊");
        }
        // [BUG]0007418 ADD END

        // 挂号日期
        visit.setRegisteredDate(DateUtils.stringToDate(t.getRegisteredDate()));
        // Author :jia_yanqing
        // Date : 2012/11/13 15:33$
        // [BUG]0011448 ADD BEGIN

        // Author :jia_yanqing
        // Date : 2013/4/2 15:33
        // [BUG]0014832 MODIFY BEGIN
        // 就诊日期
        visit.setVisitDate(DateUtils.stringToDate(t.getVisitDate()));
        // [BUG]0014832 MODIFY END

        // 就诊科室代码
        visit.setVisitDeptId(t.getRegisteredDept());
        // 就诊科室名称
//        visit.setVisitDeptName(t.getRegisteredDeptName());
//        if(DictionaryCache.getDictionary(Constants.DOMAIN_DEPARTMENT) != null){
//        	visit.setVisitDeptName(DictionaryCache.getDictionary(
//                    Constants.DOMAIN_DEPARTMENT).get(
//                    		t.getRegisteredDept()));
//        }
        visit.setVisitDeptName(DictionaryUtils.getNameFromDictionary(
        		Constants.DOMAIN_DEPARTMENT, t.getRegisteredDept(), t.getRegisteredDeptName()));
        // [BUG]0011448 ADD END

        // $Author :tong_meng
        // $Date : 2012/6/26 10:33$
        // [BUG]0007418 MODIFY BEGIN
        // 挂号科室代码
        visit.setRegisteredDeptId(t.getRegisteredDept());
        // [BUG]0007418 MODIFY END

        // $Author :tong_meng
        // $Date : 2012/6/26 10:33$
        // [BUG]0007418 ADD BEGIN
        // 挂号科室名称
//        visit.setRegisteredDeptName(t.getRegisteredDeptName());
//        if(DictionaryCache.getDictionary(Constants.DOMAIN_DEPARTMENT) != null){
//        	visit.setRegisteredDeptName(DictionaryCache.getDictionary(
//                    Constants.DOMAIN_DEPARTMENT).get(
//                    		t.getRegisteredDept()));
//        }
        visit.setRegisteredDeptName(DictionaryUtils.getNameFromDictionary(
        		Constants.DOMAIN_DEPARTMENT, t.getRegisteredDept(), t.getRegisteredDeptName()));
        // [BUG]0007418 ADD END

        // 挂号医生编码
        visit.setRegisteredDoctorId(t.getRegisteredDoctor());
        // 挂号医生姓名
//        visit.setRegisteredDoctorName(t.getRegisteredDoctorName());
        //根据字典取人员名册
        visit.setRegisteredDoctorName(DictionaryUtils.getNameFromDictionary(
        		Constants.DOMAIN_STAFF, t.getRegisteredDoctor(), t.getRegisteredDoctorName()));
        
        // $Author :tong_meng
        // $Date : 2012/6/26 10:33$
        // [BUG]0007418 MODIFY BEGIN
        // 就诊号别ID
        visit.setRegistrationClassCode(t.getRegistrationClass());
        // [BUG]0007418 MODIFY END

        // $Author :tong_meng
        // $Date : 2012/6/26 10:33$
        // [BUG]0007418 ADD BEGIN
        // 就诊号别名称
//        visit.setRegistrationClassName(t.getRegistrationClassName());
        // [BUG]0007418 ADD END
        // 根据字典取名称
        visit.setRegistrationClassName(DictionaryUtils.getNameFromDictionary(
        		Constants.DOMAIN_REGISTRATION_CLASS, t.getRegistrationClass(), t.getRegistrationClassName()));

        // $Author :tong_meng
        // $Date : 2012/6/26 10:33$
        // [BUG]0007418 MODIFY BEGIN
        // 就诊号类
        visit.setRegistrationMethodCode(t.getRegisteredWay());
        // [BUG]0007418 MODIFY END

        // $Author :tong_meng
        // $Date : 2012/7/17 10:33$
        // [BUG]0008001 MODIFY BEGIN
        // 就诊号类名称
        visit.setRegistrationMethodName(t.getRegisteredWayName());
        // [BUG]0008001 MODIFY BEGIN

        // $Author :tong_meng
        // $Date : 2012/6/26 10:33$
        // [BUG]0007418 MODIFY BEGIN
        // 挂号方式
//        visit.setRegisteredWayCode(t.getRegisteredWay());
        // [BUG]0007418 MODIFY END

        // $Author :tong_meng
        // $Date : 2012/6/26 10:33$
        // [BUG]0007418 ADD BEGIN
        // 挂号方式名称
//        visit.setRegisteredWayName(t.getRegisteredWayName());
        // [BUG]0007418 ADD END

        // 挂号方式，挂号方式名称
        String registeredWayCode = t.getRegisteredWay();
        String registeredWayName;
        // 空值为现场号，有值为预约号
        if(StringUtils.isEmpty(registeredWayCode)){
        	registeredWayCode = Constants.REGISTER_WAY_CODE_ON_SITE;
        	registeredWayName = Constants.REGISTER_WAY_NAME_ON_SITE;
        } else {
        	registeredWayCode = Constants.REGISTER_WAY_CODE_RESERVATION;
        	registeredWayName = Constants.REGISTER_WAY_NAME_RESERVATION;
        }
        visit.setRegisteredWayCode(registeredWayCode);
        visit.setRegisteredWayName(registeredWayName);
        
        // 挂号序号
        visit.setRegisteredSequence(t.getRegisteredSequence());

        // $Author:wei_peng
        // $Date:2012/10/19 18:00
        // [BUG]0010597 ADD BEGIN
        // 所挂时段编码
        visit.setRegisteredTimeIntervalCode(t.getRegisteredTimeIntervalCode());
        // 所挂时段名称
        visit.setRegisteredTimeIntervalName(t.getRegisteredTimeIntervalName());
        // [BUG]0010597 ADD END

        // $Author :tong_meng
        // $Date : 2012/10/30 15:00$
        // [BUG]0010809 MODIFY BEGIN
        // 费用类别
        visit.setChargeClass(t.getInsuranceType());
//        visit.setChargeClassName(t.getInsuranceTypeName());
        visit.setChargeClassName(DictionaryUtils.getNameFromDictionary(
        		Constants.DOMAIN_CHARGE_CATEGORY, t.getInsuranceType(), t.getInsuranceTypeName()));
        // [BUG]0010809 MODIFY END
        // 更新时间
        visit.setUpdateTime(systemTime);
        
        // 修改者
        visit.setUpdateby(serviceId);
        
        // 就诊次数
        visit.setVisitTimes(StringUtils.strToBigDecimal(t.getVisitTimes(), "就诊次数"));
        
        return visit;
    }
    // [BUG]0010353 ADD END
}
