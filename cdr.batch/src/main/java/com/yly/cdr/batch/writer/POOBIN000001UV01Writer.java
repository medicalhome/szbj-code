/**
 * [FUN]A01-029 过敏和生理状态信息
 * 
 * @version 1.0, 2012/04/09  20:23:00
 
 * @author wu_jianfeng
 * @since 1.0
*/
package com.yly.cdr.batch.writer;

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

import com.yly.cdr.core.Constants;
import com.yly.cdr.entity.AllergicHistory;
import com.yly.cdr.entity.Patient;
import com.yly.cdr.entity.PatientCrossIndex;
import com.yly.cdr.entity.RiskInformation;
import com.yly.cdr.hl7.dto.poobin000001uv01.AllergicHistoryDto;
import com.yly.cdr.hl7.dto.poobin000001uv01.POOBIN000001UV01;
import com.yly.cdr.hl7.dto.poobin000001uv01.RiskInformationDto;
import com.yly.cdr.service.PatientService;
import com.yly.cdr.util.DateUtils;
import com.yly.cdr.util.StringUtils;

@Component(value = "poobin000001uv01Writer")
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class POOBIN000001UV01Writer implements BusinessWriter<POOBIN000001UV01>
{

    private static final Logger logger = LoggerFactory.getLogger(POOBIN000001UV01Writer.class);

    private static final Logger loggerBS306 = LoggerFactory.getLogger("BS306");

    @Autowired
    private PatientService patientService;

    // $Author :jia_yanqing
    // $Date : 2012/7/3 14:45$
    // [BUG]0007617 DELETE BEGIN
    // @Autowired
    // private EMPIService empiService;
    // [BUG]0007617 DELETE END

    // 与此消息关联的患者
    private Patient patient;

    // 患者本地ID
    private String patientLid;

    // 得到系统当前时间
    private Date systemTime = DateUtils.getSystemTime();
    
    // $Author: tong_meng
    // $Date: 2013/8/30 15:00
    // [BUG]0036757 ADD BEGIN
    private String serviceId;
    // [BUG]0036757 ADD END
    
    private String orgCode;

    /**
     * 业务数据检验
     */
    @Override
    public boolean validate(POOBIN000001UV01 t)
    {
    	orgCode = t.getOrgCode();   
    	
    	// Author :jia_yanqing
        // Date : 2013/1/22 15:00
        // [BUG]0042085 MODIFY BEGIN
    	if(orgCode == null || "".equals(orgCode))
    	{
    	    orgCode = Constants.HOSPITAL_CODE;
    	    t.setOrgName(Constants.HOSPITAL_NAME);
    	}
    	// $Author :chang_xuewen
        // $Date : 2013/12/19 16:00$
        // [BUG]0040961 ADD BEGIN
    	/*
    	if(!orgCode.equals(t.getMessage().getOrgCode())){
    		loggerBS306.error(
                    "Message:{}, validate:{}",
                    t.toString(),
                    "消息头与消息体中的医疗机构代码不一致：头：OrgCode = " 
                    + t.getMessage().getOrgCode() 
                    + " ，体：OrgCode ="
                    + orgCode);
        	return false;
        }
        */
    	// [BUG]0040961 ADD END
    	// [BUG]0042085 MODIFY END
    	
        // $Author :tong_meng
        // $Date : 2012/9/21 15:07$
        // [BUG]9776 ADD BEGIN
        if (Constants.PATIENT_DOMAIN_OUTPATIENT.equals(t.getPatientDomain()))
        {
            List<RiskInformationDto> informationDtoList = t.getRiskInformations();
            for (RiskInformationDto riskInformationDto : informationDtoList)
            {
                if (StringUtils.isEmpty(riskInformationDto.getRiskCode()))
                {
                    loggerBS306.error("Message:{},validate:{}", t.toString(),
                            "生理状态码不能为空！" + riskInformationDto.getRiskCode());
                    return false;
                }
            }
        }
        // [BUG]9776 ADD END

        // $Author :tong_meng
        // $Date : 2012/9/26 9:30$
        // [BUG]0009965 ADD BEGIN
        /*
         * if (t.getAllergicHistories() != null ||
         * !t.getAllergicHistories().isEmpty()) { for (AllergicHistoryDto
         * allergicHistoryDto : t.getAllergicHistories()) { if
         * (!StringUtils.isNotNullAll(
         * allergicHistoryDto.getAllergenDiagnosticsCode(),
         * allergicHistoryDto.getAllergenDiagnosticsName())) { logger.error(
         * "非空字段验证未通过：{}", "过敏诊断码 ：" +
         * allergicHistoryDto.getAllergenDiagnosticsCode() + "；过敏诊断名称 ：" +
         * allergicHistoryDto.getAllergenDiagnosticsName()); return false; } } }
         */
        // [BUG]0009965 ADD END
        return true;
    }

    /**
     * 检查消息的依赖关系
     */
    @Override
    public boolean checkDependency(POOBIN000001UV01 t, boolean flag)
    {
        // 如果不是更新消息，返回false
        if (!isNewMessage(t))
        {
            logger.error("MessageId:{};错误的消息交互类型：" + t.getTriggerEventCode(),
                    t.getMessage().getId());
            if (flag)
            {
                loggerBS306.error("Message:{},checkDependency:{}",
                        t.toString(), "错误的消息交互类型：" + t.getTriggerEventCode());
            }
            return false;
        }

        patientLid = t.getPatientLid();

        // $Author :wei_peng
        // $Date : 2012/8/27 15:56$
        // [BUG]0009144 ADD BEGIN
        // $Author :tongmeng
        // $Date : 2012/5/24 9:45$
        // [BUG]0006613 ADD BEGIN
        // 查找已存在的患者,患者不存在返回！
        if (!findExistsPatient(t))
        {
            logger.error(
                    "MessageId:{};患者不存在，患者本地ID：" + patientLid + "域ID："
                        + t.getPatientDomain(), t.getMessage().getId());
            if (flag)
            {
                loggerBS306.error(
                        "Message:{},checkDependency:{}",
                        t.toString(),
                        "患者不存在，患者本地ID：" + patientLid + "域ID："
                            + t.getPatientDomain());
            }
            return false;
        }
        // [BUG]0006613 ADD END
        // [BUG]0009144 ADD END

        /*
         * // 如果是更新消息 if (isUpdateMessage(t) && (existsRiskInfos == null ||
         * existsAllergicHistories == null)) { // $Author :jia_yanqing // $Date
         * : 2012/7/3 14:45$ // [BUG]0007617 DELETE BEGIN // 如果患者不存在，返回false //
         * if (!checkPatientExists()) // return false; // [BUG]0007617 DELETE
         * END
         * 
         * // 检查消息里所有的危险性信息与过敏信息 // $Author :jia_yanqing // $Date : 2012/7/3
         * 14:45$ // [BUG]0007617 MODIFY BEGIN // if (existsRiskInfos ==
         * null||existsAllergicHistories == null) // {
         * logger.error("消息为更新时，数据库中危险性信息或者过敏信息不存在！"); return false; // } //
         * [BUG]0007617 MODIFY END }
         */
        return true;
    }

    /**
     * 保存或更新过敏和不良反应消息
     */
    @Override
    @Transactional
    public void saveOrUpdate(POOBIN000001UV01 t)
    {
        // $Author: tong_meng
        // $Date: 2013/8/30 15:00
        // [BUG]0036757 ADD BEGIN
        serviceId = t.getMessage().getServiceId();
        // [BUG]0036757 ADD END
        
        // 存放数据库已存在的危险性信息，消息更新时使用
        List<Object> existsRiskInfos = getRiskInfoFromDB(t);
        // 存放数据库已存在的过敏性信息，消息更新时使用
        List<Object> existsAllergicHistories = getAllergicHistoryFromDB(t);

        // 从消息得到要处理的危险性信息集合
        List<RiskInformation> riskInfos = getRiskInfosFromMessage(t);
        // 从消息得到要处理的过敏信息集合
        List<AllergicHistory> allergicHistories = getAllergicHistoriesFromMessage(t);

        // $Author:wei_peng
        // $Date:2012/9/27 11:35
        // $[BUG]0009967 MODIFY BEGIN
        // 如果是新增消息
        // if (isNewMessage(t))
        // {
        // $Author :tongmeng
        // $Date : 2012/5/24 9:45$
        // [BUG]0006613 DELETE BEGIN
        // 如果患者存在，保存所有的过敏及危险性信息
        // if (checkPatientExists())
        // [BUG]0006613 DELETE END
        // patientService.saveAllergicHistoriesAndRiskInfos(allergicHistories,riskInfos);

        // $Author :tongmeng
        // $Date : 2012/5/24 9:45$
        // [BUG]0006613 DELETE BEGIN
        // 如果患者不存在，保存患者信息以及所有的过敏及危险性信息
        // else
        // patientService.saveAllergicHistoriesAndRiskInfos(
        // ConvertDtoToPatientEntity(t),
        // ConvertDtoToPatientTempEntity(t), allergicHistories,
        // riskInfos);
        // [BUG]0006613 DELETE END

        // }
        // else if (isUpdateMessage(t))
        // {
        patientService.updateAllergicHistoriesAndRiskInfos(existsRiskInfos,
                existsAllergicHistories, allergicHistories, riskInfos,
                systemTime,serviceId);
        // }
        // $[BUG]0009967 MODIFY END
    }

    /**
     * 是否是新消息
     * @return 如果是新消息，返回true，如果不是，返回false
     */
    public boolean isNewMessage(POOBIN000001UV01 t)
    {
        return Constants.NEW_MESSAGE_FLAG.equals(t.getTriggerEventCode());
    }

    /**
     * 是否是消息更新
     * @return 如果是更新消息，返回true，如果不是，返回false
     */
    /*
     * public boolean isUpdateMessage(POOBIN000001UV01 t) { return
     * Constants.UPDATE_MESSAGE_FLAG.equals(t.getTriggerEventCode()); }
     */

    // $Author :jia_yanqing
    // $Date : 2012/7/3 16:45$
    // [BUG]0007617 MODIFY BEGIN
    /**
     * 检查消息关联的患者是否存在
     * @param t
     * @return 如果存在返回true，否则返回false
     */
    public boolean findExistsPatient(POOBIN000001UV01 poobin000001uv01)
    {
        // 根据域ID，患者Lid从患者交叉索引查询患者相关信息
        Map<String, Object> condition = new HashMap<String, Object>();
        condition.put("patientDomain", poobin000001uv01.getPatientDomain());
        condition.put("patientLid", patientLid);
        // $Author :chang_xuewen
        // $Date : 2014/01/14 10:00$
        // [BUG]0041862 ADD BEGIN
        condition.put("orgCode", orgCode);
        // [BUG]0041862 ADD END
        condition.put("deleteFlag", Constants.NO_DELETE_FLAG);
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
                    poobin000001uv01.getPatientDomain(), patientLid,orgCode);
        }

        // $Author :tongmeng
        // $Date : 2012/5/24 9:45$
        // [BUG]0006613 DELETE BEGIN
        // 如果empi id找不到患者，通过患者域，患者本地ID(门诊号），从患者临时表去查找患者
        // if (patient == null)
        // patient = patientService.getPatientByTempPatient(
        // t.getPatientDomain(), t.getOutpatientLid());
        // [BUG]0006613 DELETE END

        if (patient == null)
        {
            logger.warn("通过患者交叉信息表和患者临时表均找不到患者信息");
            return false;
        }
        return true;
    }

    // [BUG]0007617 MODIFY END

    // $Author :jia_yanqing
    // $Date : 2012/7/3 16:45$
    // [BUG]0007617 DELETE BEGIN
    /**
     * 得到患者empi_id
     * @return 患者empi_id
     */
    /*
     * private String getPatientEid(POOBIN000001UV01 t) {
     * 
     * // $Author :tongmeng // $Date : 2012/5/30 11:30$ // [BUG]0006657 MODIFY
     * BEGIN // 如果消息里有患者empi_id, 得到消息的empi_id EmpiPerson person =
     * empiService.getEMPIPerson(t.getPatientDomain(), patientLid); if (person
     * != null) return person.getEmpiId(); //
     * 如果消息里没有，根据消息里的患者本地ID，患者域，从empi查找患者empi_id else { // TODO
     * 调用EMPI服务，得到患者的EMPI_ID // String patientLid = t.getPatientLid(); // String
     * patientDomain = t.getPatientDomain();
     * 
     * // 因为EMPI服务还没有实现，仅测试用 // patientEid = "123";
     * 
     * return null; } // [BUG]0006657 MODIFY END }
     */
    // [BUG]0007617 DELETE END

    /**
     * 检查患者是否存在
     * @return 如果存在返回true，否则返回false
     */
    private Boolean checkPatientExists()
    {
        return patient != null;
    }

    // $Author :jia_yanqing
    // $Date : 2012/7/3 14:30$
    // [BUG]0007617 DELETE BEGIN
    /**
     * 检查危险性信息
     * @param t
     * @return
     */
    /*
     * public Boolean checkRiskInfos(POOBIN000001UV01 t) { // 从消息里得到危险性信息集合
     * List<RiskInformationDto> riskInfoDtos = t.getRiskInformations(); //
     * 检查消息里每一个危险性信息在数据库是否存在，如果有一个不存在，返回false for (RiskInformationDto
     * riskInfoDto : riskInfoDtos) { RiskInformation riskInfo =
     * getRiskInfoFromDB(riskInfoDto); if (riskInfo == null) {
     * logger.error("更新危险性信息时找不到危险性原记录， 危险性名称={}", riskInfoDto.getRiskName());
     * return false; } else existsRiskInfos.add(riskInfo); }
     * 
     * return true; }
     */
    /**
     * 检查过敏信息
     * @param t
     * @return
     */
    /*
     * public Boolean checkAllergicHistories(POOBIN000001UV01 t) { //
     * 从消息里得到过敏信息集合 List<AllergicHistoryDto> allergicHistoryDtos =
     * t.getAllergicHistories(); // 检查消息里每一个过敏信息在数据库是否存在，如果有一个不存在，返回false for
     * (AllergicHistoryDto allergicHistoryDto : allergicHistoryDtos) {
     * AllergicHistory allergicHistory =
     * getAllergicHistoryFromDB(allergicHistoryDto); if (allergicHistory ==
     * null) { logger.error("更新过敏信息时找不到过敏原记录， 过敏源代码={}，过敏源名称={}",
     * allergicHistoryDto.getAllergenCode(),
     * allergicHistoryDto.getAllergicSource()); return false; } else
     * existsAllergicHistories.add(allergicHistory); }
     * 
     * return true; }
     */
    // [BUG]0007617 DELETE END

    /**
     * 从消息里得到危险性信息
     * @return 要新增和更新的危险性信息集合
     */
    public List<RiskInformation> getRiskInfosFromMessage(POOBIN000001UV01 t)
    {
        // 从消息里得到危险性信息集合
        List<RiskInformationDto> riskInfoDtos = t.getRiskInformations();

        // 危险性信息集合
        List<RiskInformation> riskInfos = new ArrayList<RiskInformation>();

        for (RiskInformationDto riskInfoDto : riskInfoDtos)
        {
            RiskInformation riskInfo = new RiskInformation();

            // $Author :jia_yanqing
            // $Date : 2012/7/3 14:30$
            // [BUG]0007617 DELETE BEGIN
            // if (isUpdateMessage(t))
            // riskInfo = getRiskInfo(riskInfoDto);
            // [BUG]0007617 DELETE END

            // 危险性名称
            riskInfo.setRiskName(riskInfoDto.getRiskName());
            
            // $Author :chang_xuewen
            // $Date : 2013/12/19 16:00$
            // [BUG]0040961 ADD BEGIN
            riskInfo.setOrgCode(orgCode);
            riskInfo.setOrgName(t.getOrgName());
            // [BUG]0040961 ADD END

            // if (isNewMessage(t))
            // {
            // 如果患者存在
            if (checkPatientExists())
            {
                // 患者内部序列号
                riskInfo.setPatientSn(patient.getPatientSn());
            }
            // 创建时间
            riskInfo.setCreateTime(systemTime);
            // 更新时间
            riskInfo.setUpdateTime(systemTime);
            // 更新回数
            riskInfo.setUpdateCount(Constants.INSERT_UPDATE_COUNT);
            // 删除标识
            riskInfo.setDeleteFlag(Constants.NO_DELETE_FLAG);
            
            // $Author: tong_meng
            // $Date: 2013/8/30 15:00
            // [BUG]0036757 ADD BEGIN
            riskInfo.setCreateby(serviceId); // 设置创建人
            // [BUG]0036757 ADD END

            // $Author :jia_yanqing
            // $Date : 2012/7/3 18:00$
            // [BUG]0007617 ADD BEGIN
            // 域ID
            riskInfo.setPatientDomain(t.getPatientDomain());
            // 患者本地Lid
            riskInfo.setPatientLid(patientLid);
            // [BUG]0007617 ADD END

            // $Author :jia_yanqing
            // $Date : 2012/7/3 14:30$
            // [BUG]0007617 DELETE BEGIN
            // }

            // else if (isUpdateMessage(t))
            // {
            // 更新时间
            // riskInfo.setUpdateTime(systemTime);
            // }
            // [BUG]0007617 DELETE END

            riskInfos.add(riskInfo);
        }

        return riskInfos;
    }

    /**
     * 从消息里得到过敏信息
     * @return 要新增和更新的过敏信息集合
     */
    public List<AllergicHistory> getAllergicHistoriesFromMessage(
            POOBIN000001UV01 t)
    {
        // 从消息里得到过敏信息集合
        List<AllergicHistoryDto> allergicHistoryDtos = t.getAllergicHistories();
        // 过敏信息集合
        List<AllergicHistory> allergicHistories = new ArrayList<AllergicHistory>();

        for (AllergicHistoryDto allergicHistoryDto : allergicHistoryDtos)
        {
            AllergicHistory allergicHistory = new AllergicHistory();
            
            // $Author :chang_xuewen
            // $Date : 2013/12/05 11:00$
            // [BUG]0040271 ADD BEGIN
            allergicHistory.setOrgCode(orgCode);
            allergicHistory.setOrgName(t.getOrgName());
            // [BUG]0040271 ADD END

            // $Author :jia_yanqing
            // $Date : 2012/7/3 14:30$
            // [BUG]0007617 DELETE BEGIN
            // if (isUpdateMessage(t))
            // allergicHistory = getAllergicHistory(allergicHistoryDto);
            // [BUG]0007617 DELETE END

            // 过敏原代码
            allergicHistory.setAllergenCode(allergicHistoryDto.getAllergenCode());
            // 过敏药物名称
            allergicHistory.setAllergicSource(allergicHistoryDto.getAllergicSource());
            // 过敏严重性代码
            allergicHistory.setSeriousness(allergicHistoryDto.getSeriousness());

            // $Author :tong_meng
            // $Date : 2012/7/1 10:33$
            // [BUG]0007418 ADD BEGIN
            // 过敏严重性名称
            allergicHistory.setSeriousnessName(allergicHistoryDto.getSeriousnessName());
            // [BUG]0007418 ADD END

            // 过敏开始日期
            allergicHistory.setAllergicStartDate(DateUtils.stringToDate(allergicHistoryDto.getAllergicStartDate()));

            // $Author :jia_yanqing
            // $Date : 2012/7/3 17:30$
            // [BUG]0007617 DELETE BEGIN
            // 过敏症状停止日期
            // allergicHistory.setAllergicStopDate(DateUtils.stringToDate(allergicHistoryDto.getAllergicStopDate()));
            // [BUG]0007617 DELETE END

            // 过敏类型
            allergicHistory.setAllergicType(allergicHistoryDto.getAllergicType());

            // $Author :tong_meng
            // $Date : 2012/7/1 10:33$
            // [BUG]0007418 ADD BEGIN
            // 过敏类型名称
            allergicHistory.setAllergicTypeName(allergicHistoryDto.getAllergicTypeName());
            // [BUG]0007418 ADD END

            // 是否家族史
            allergicHistory.setFamilyHistoryFlag(allergicHistoryDto.getFamilyHistoryFlag());
            // 备注
            allergicHistory.setMemo(allergicHistoryDto.getMemo());

            // if (isNewMessage(t))
            // /{
            // 检查患者是否存在
            if (checkPatientExists())
            {
                // 患者内部序列号
                allergicHistory.setPatientSn(patient.getPatientSn());
            }

            // $Author :jia_yanqing
            // $Date : 2012/7/3 18:00$
            // [BUG]0007617 ADD BEGIN
            // 域ID
            allergicHistory.setPatientDomain(t.getPatientDomain());
            // 患者本地Lid
            allergicHistory.setPatientLid(patientLid);
            // [BUG]0007617 ADD END

            // 创建时间
            allergicHistory.setCreateTime(systemTime);
            // 更新时间
            allergicHistory.setUpdateTime(systemTime);
            // 更新回数
            allergicHistory.setUpdateCount(Constants.INSERT_UPDATE_COUNT);
            // 删除标识
            allergicHistory.setDeleteFlag(Constants.NO_DELETE_FLAG);
            
            // $Author: tong_meng
            // $Date: 2013/8/30 15:00
            // [BUG]0036757 ADD BEGIN
            allergicHistory.setCreateby(serviceId); // 设置创建人
            // [BUG]0036757 ADD END

            // 将新增的过敏信息，添加到新增的集合
            // $Author :jia_yanqing
            // $Date : 2012/7/3 14:30$
            // [BUG]0007617 DELETE BEGIN
            // }

            // else if (isUpdateMessage(t))
            // {
            // 更新时间
            // allergicHistory.setUpdateTime(systemTime);
            // }
            // [BUG]0007617 DELETE END

            allergicHistories.add(allergicHistory);
        }

        return allergicHistories;
    }

    // $Author :jia_yanqing
    // $Date : 2012/8/2 17:30$
    // [BUG]0008403 MODIFY BEGIN
    // $Author :jia_yanqing
    // $Date : 2012/7/3 14:30$
    // [BUG]0007617 MODIFY BEGIN
    /**
     * 检查数据库，返回该患者存在的过敏信息
     * @param poobin000001uv01
     * @return 返回过敏信息
     */
    private List<Object> getAllergicHistoryFromDB(
            POOBIN000001UV01 poobin000001uv01)
    {
        // 根据域ID，患者Lid查找过敏记录
        Map<String, Object> condition = new HashMap<String, Object>();
        condition.put("patientDomain", poobin000001uv01.getPatientDomain());
        condition.put("patientLid", patientLid);
        condition.put("deleteFlag", Constants.NO_DELETE_FLAG);
        condition.put("orgCode", orgCode);
        // $Author:wei_peng
        // $Date:2012/9/27 11:35
        // $[BUG]0009967 MODIFY BEGIN
        return patientService.selectAllergicHistoryByCondition(condition);
        /*
         * if (existsAllergicHistories == null) {
         * logger.info("更新过敏信息时找不到过敏原记录"); }
         */
        // $[BUG]0009967 MODIFY END
    }

    /**
     * 检查数据库，返回该患者存在的危险性信息
     * @param poobin000001uv01
     * @return 返回危险性信息
     */
    private List<Object> getRiskInfoFromDB(POOBIN000001UV01 poobin000001uv01)
    {
        // 根据域ID，患者Lid查找危险性信息记录
        Map<String, Object> condition = new HashMap<String, Object>();
        condition.put("patientDomain", poobin000001uv01.getPatientDomain());
        condition.put("patientLid", patientLid);
        condition.put("deleteFlag", Constants.NO_DELETE_FLAG);
        condition.put("orgCode", orgCode);
        // $Author:wei_peng
        // $Date:2012/9/27 11:35
        // $[BUG]0009967 MODIFY BEGIN
        return patientService.selectRiskInfoByCondition(condition);
        /*
         * if (existsRiskInfos == null) { logger.info("更新危险性信息时找不到危险性原记录"); }
         */
        // $[BUG]0009967 MODIFY END
    }
    // [BUG]0007617 MODIFY END
    // [BUG]0008403 MODIFY END

    // $Author :jia_yanqing
    // $Date : 2012/7/3 14:30$
    // [BUG]0007617 DELETE BEGIN
    /**
     * 返回已在数据库存在的危险性信息
     * @param riskInfoDto
     * @return
     */
    /*
     * private RiskInformation getRiskInfo(RiskInformationDto riskInfoDto) {
     * RiskInformation result = new RiskInformation(); for (RiskInformation
     * riskInfo : existsRiskInfos) { // TODO 危险性名称相同，认为是同一条记录 if
     * (isTheSameRiskInfo(riskInfo, riskInfoDto)) { result = riskInfo; break; }
     * } return result; }
     */

    /**
     * 是否是同一个危险性信息记录
     * @param allergicHistory
     * @param allergicHistoryDto
     * @return 如果是同一条记录，返回true，否则，返回false
     */
    /*
     * private Boolean isTheSameRiskInfo(RiskInformation riskInfo,
     * RiskInformationDto riskInfoDto) { // TODO 危险性名称相同，认为是同一条记录 return
     * riskInfo.getRiskName().equals(riskInfoDto.getRiskName()); }
     */

    /**
     * 返回已存在的 过敏信息
     * @param riskInfoDto
     * @return
     */
    /*
     * private AllergicHistory getAllergicHistory( AllergicHistoryDto
     * allergicHistoryDto) { AllergicHistory result = new AllergicHistory(); for
     * (AllergicHistory allergicHistory : existsAllergicHistories) { // 是否是同一条记录
     * if (isTheSameAllergiceHistory(allergicHistory, allergicHistoryDto)) {
     * result = allergicHistory; break; }
     * 
     * } return result; }
     */

    /**
     * 是否是同一个过敏信息记录
     * @param allergicHistory
     * @param allergicHistoryDto
     * @return 如果是同一条记录，返回true，否则，返回false
     */
    /*
     * private Boolean isTheSameAllergiceHistory(AllergicHistory
     * allergicHistory, AllergicHistoryDto allergicHistoryDto) { // TODO
     * 过敏原和过敏药物相同，认为是同一条记录 return allergicHistory.getAllergenCode().equals(
     * allergicHistoryDto.getAllergenCode()) &&
     * allergicHistory.getAllergicSource().equals(
     * allergicHistoryDto.getAllergicSource()); }
     */
    // [BUG]0007617 DELETE END

    // $Author :tongmeng
    // $Date : 2012/5/24 9:45$
    // [BUG]0006613 DELETE BEGIN
    // /**
    // * 将消息里插入数据，封装到实体里
    // * @return 要新增患者实体
    // */
    // private Patient ConvertDtoToPatientEntity(POOBIN000001UV01 t)
    // {
    // Patient patient = new Patient();
    // PatientDto patientDto = t.getPatients().get(0);
    // //
    // // patient.setPatientSn();
    // // 患者姓名
    // patient.setPatientName(patientDto.getPatientName());
    // // 性别代码
    // patient.setGenderCode(patientDto.getGenderCode());
    // // 出生日期
    // patient.setBirthDate(DateUtils.stringToDate(patientDto.getBirthDate()));
    //
    // // 创建时间
    // patient.setCreateTime(systemTime);
    // // 更新时间
    // patient.setUpdateTime(systemTime);
    // // 更新回数
    // patient.setUpdateCount(StringUtils.strToBigDecimal(Constants.INSERT_UPDATE_COUNT));
    // // 删除标识
    // patient.setDeleteFlag(Constants.NO_DELETE_FLAG);
    //
    // return patient;
    // }
    //
    // /**
    // * 将消息里插入数据，封装到实体里
    // * @return 要新增临时患者实体
    // */
    // private PatientTemp ConvertDtoToPatientTempEntity(POOBIN000001UV01 t)
    // {
    // PatientTemp tempPatient = new PatientTemp();
    // PatientDto patientDto = t.getPatients().get(0);
    //
    // // 域代码
    // tempPatient.setPatientDomain(patientDto.getPatientDomain());
    // // 患者本地ID
    // tempPatient.setPatientLid(patientLid);
    // // 患者姓名
    // tempPatient.setPatientName(patientDto.getPatientName());
    // // 性别代码
    // tempPatient.setGenderCode(patientDto.getGenderCode());
    // // 出生日期
    // tempPatient.setBirthDate(DateUtils.stringToDate(patientDto.getBirthDate()));
    // // 创建时间
    // tempPatient.setCreateTime(systemTime);
    // // 更新时间
    // tempPatient.setUpdateTime(systemTime);
    // // 更新回数
    // tempPatient.setUpdateCount(StringUtils.strToBigDecimal(Constants.INSERT_UPDATE_COUNT));
    // // 删除标识
    // tempPatient.setDeleteFlag(Constants.NO_DELETE_FLAG);
    //
    // return tempPatient;
    // }
    // [BUG]0006613 DELETE END
}
