/**
 * [FUN]V03-001患者列表
 * [FUN]V04-001患者详细
 * 
 * @version 1.0, 2012/03/12  20:23:00
 
 * @author wu_jianfeng, wen_ruichao
 * @since 1.0
*/
package com.founder.cdr.service.impl;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.founder.cdr.core.Constants;
import com.founder.cdr.dao.AllergicHistoryDao;
import com.founder.cdr.dao.PatientDao;
import com.founder.cdr.dto.AccessControlDto;
import com.founder.cdr.dto.AllergicHistoryDto;
import com.founder.cdr.dto.PatientAppointDto;
import com.founder.cdr.dto.PatientCrossIndexDto;
import com.founder.cdr.dto.PatientDto;
import com.founder.cdr.dto.UserTabColDto;
import com.founder.cdr.dto.patient.PatientListAdvanceSearchPra;
import com.founder.cdr.dto.patient.PatientListSearchPra;
import com.founder.cdr.dto.patient.RecentPatientListSearchPra;
import com.founder.cdr.entity.AllergicHistory;
import com.founder.cdr.entity.Patient;
import com.founder.cdr.entity.PatientAddress;
import com.founder.cdr.entity.PatientContact;
import com.founder.cdr.entity.PatientCredential;
import com.founder.cdr.entity.PatientCrossIndex;
import com.founder.cdr.entity.PatientTemp;
import com.founder.cdr.entity.RiskInformation;
import com.founder.cdr.entity.SocialInsurance;
import com.founder.cdr.service.CommonService;
import com.founder.cdr.service.PatientService;
import com.founder.cdr.sql.PatientListACLAdvanceSearchSql;
import com.founder.cdr.sql.PatientListAdvanceSearchSql;
import com.founder.cdr.util.StringUtils;
import com.founder.fasf.core.util.daohelper.entity.EntityDao;
import com.founder.fasf.web.paging.PagingContext;

@Component
public class PatientServiceImpl implements PatientService
{
    @Autowired
    private EntityDao entityDao;

    @Autowired
    private PatientDao patientDao;

    @Autowired
    private AllergicHistoryDao allergicHistoryDao;

    @Autowired
    private CommonService commonService;

    private static final String EMPI_TABLE_FILTER_COLUMN = "UPDATEBY";

    private static List<UserTabColDto> userTabColList;
    
    private static List<String> userTablesName;

    // $Author : wen_ruichao
    // $Date : 2012/03/12 9:56$  
    // [FUN]V04-001患者详细 ADD BEGIN

    /**
     * 保存患者信息
     * @param patient 患者对象
     */
    @Transactional
    public int savePatient(Patient patient)
    {
        return entityDao.insert(patient);
    }

    /**
     * 保存患者地址信息
     * @param patientAddress
     * @return
     */
    @Transactional
    public int savePatientAddreses(PatientAddress[] patientAddresses)
    {
        return entityDao.insertAll(patientAddresses);
    }

    /**
     * 保存患者联系人信息
     * @param patientContacts
     * @return
     */
    @Transactional
    public int savePatientContact(PatientContact[] patientContacts)
    {
        return entityDao.insertAll(patientContacts);
    }

    /**
     * 保存患者证件信息
     * @param patientCredentials
     * @return
     */
    @Transactional
    public int savePatientCredential(PatientCredential[] patientCredentials)
    {
        return entityDao.insertAll(patientCredentials);
    }

    /**
    * 保存患者医保信息
    * @param socialInsurance
    * @return
    */
    @Transactional
    public int saveSocialInsurance(SocialInsurance socialInsurance)
    {
        return entityDao.insert(socialInsurance);
    }

    /**
    * 保存患者危险性信息
    * @param riskInformations
    * @return
    */
    @Transactional
    public int saveRiskInformation(RiskInformation[] riskInformations)
    {
        return entityDao.insertAll(riskInformations);
    }

    /**
    * 保存患者过敏信息
    * @param allergicHistories
    * @return
    */
    @Transactional
    public int saveAllergicHistory(AllergicHistory[] allergicHistories)
    {
        return entityDao.insertAll(allergicHistories);
    }

    /**
     * 根据患者内部序列号获取患者对象
     * @param patientSn 患者内部序列号
     * @return 患者对象
     */
    @Transactional
    public Patient getPatient(String patientSn)
    {
        return patientDao.getPatient(patientSn);
    }

    /**
     * 根据患者内部序列号获取患者对象
     * @param patientSn 患者内部序列号
     * @return 患者对象
     */
    @Transactional
    public PatientAddress[] selectPatientAddresses(String patientSn)
    {
        return patientDao.selectPatientAddresses(patientSn);
    }

    /**
     * 根据患者内部序列号获取患者联系人列表
     * @param patientSn 患者内部序列号
     * @return 患者联系人列表
     */
    @Transactional
    public PatientContact[] selectPatientContacts(String patientSn)
    {
        return patientDao.selectPatientContacts(patientSn);
    }

    /**
     * 根据患者内部序列号获取患者证件列表
     * @param patientSn 患者内部序列号
     * @return 患者证件列表
     */
    @Transactional
    public PatientCredential[] selectPatientCredentials(String patientSn)
    {
        return patientDao.selectPatientCredentials(patientSn);
    }

    /**
     * 根据患者内部序列号获取患者医保信息
     * @param patientSn 患者内部序列号
     * @return 患者医保信息
     */
    @Transactional
    public SocialInsurance getSocialInsurance(String patientSn)
    {
        return patientDao.getSocialInsurance(patientSn);
    }

    /**
     * 根据患者内部序列号获取患者危险性信息列表
     * @param patientSn 患者内部序列号
     * @return 患者危险性信息列表
     */
    @Transactional
    public RiskInformation[] selectRiskInformations(String patientSn)
    {
        return patientDao.selectRiskInformations(patientSn);
    }

    // [FUN]V04-001患者详细 ADD END

    // $Author : wu_jianfeng
    // $Date : 2012/03/12 9:56$  
    // [FUN]V03-001患者列表 ADD BEGIN

    /**
     * 根据患者姓名，性别代码，就诊开始日期，就诊结束日期，获得患者列表
     * @param patientName 患者姓名
     * @param genderCode 性别代码
     * @param visitStartDate 就诊开始日期
     * @param visitEndDate 就诊结束日期
     * @return 患者信息列表
     */
    @Transactional
    public List<PatientDto> selectAllPatient(String patientName,
            String genderCode, String visitStartDate, String visitEndDate,
            PagingContext pagingContext)
    {
        List<PatientDto> lpd = patientDao.selectAllPatient(patientName,
                genderCode, visitStartDate, visitEndDate, pagingContext);
        return lpd;
    }

    // [FUN]V03-001患者列表 ADD END

    // [FUN]V05-001:临床信息集成视图 ADD BEGIN

    /**
     * 根据患者序列号，地址类型，得到最新的地址信息
     * @param patientSn 患者序列号
     * @param addressType 地址类型
     * @return 患者最新地址信息
     */
    @Transactional
    public PatientAddress getLatestPatientAddress(String patientSn,
            String addressType)
    {
        List<PatientAddress> lpa = patientDao.selectPatientAddresses(patientSn,
                addressType);
        if (lpa.size() == 0)
            return null;
        else
            return lpa.get(0);
    }

    /**
     * 根据业务系统的域ID和患者LID，得到患者序列号
     * @param domainId 域ID
     * @param patientLid 患者Lid
     * @return patientSn 患者序列号
     */
    public String getPatientSn(String domainId, String patientLid)
    {
        return null;
    }

    /**
     * 根据患者序列号，得到患者警告信息
     * @param patientSn 患者序列号
     * @return 患者警告信息
     */
    @Transactional
    public String getPatientAlerts(String patientSn)
    {
        String patientAlerts = "";

        // 取患者的过敏史信息
        AllergicHistoryDto[] lah = allergicHistoryDao.selectAllergyList(
                patientSn, null);
        for (AllergicHistoryDto ah : lah)
        {
            // $Author :jin_peng
            // $Date : 2013/01/30 16:01$
            // [BUG]0013784 MODIFY BEGIN
            String allergicSource = ah.getAllergicSource();
            if (allergicSource != null)
            {
                if (patientAlerts != "")
                    patientAlerts += "|";
                patientAlerts += allergicSource;
            }

            // [BUG]0013784 MODIFY END
        }

        // 取患者的危险性信息
        RiskInformation[] lri = selectRiskInformations(patientSn);
        for (RiskInformation ri : lri)
        {
            String riskName = ri.getRiskName();
            if (riskName != null)
            {
                if (patientAlerts != "")
                    patientAlerts += "|";
                patientAlerts += ri.getRiskName();
            }
        }

        return patientAlerts;
    }

    /**
     * 获取患者集合(根据多个查询条件)
     * @param patientListSearchPra 患者查询条件
     * @param pagingContext
     * @return 患者集合
     */
    @Override
    @Transactional
    public List<PatientDto> selectPatientByCondition(
            PatientListSearchPra patientListSearchPra,
            Boolean visitInfoIsEmpty, Boolean empiInfoIsEmpty,
            PagingContext pagingContext)
    {
        List patientList = patientDao.selectPatientByCondition(
                patientListSearchPra, visitInfoIsEmpty, empiInfoIsEmpty,
                pagingContext);
        return patientList;
    }

    /**
     * 获取门诊患者集合(根据多个查询条件)
     * @param patientListSearchPra 患者查询条件
     * @param pagingContext
     * @return 患者集合
     */
    @Override
    @Transactional
    public List<PatientDto> selectOutpatientByCondition(
            PatientListSearchPra patientListSearchPra,
            PagingContext pagingContext)
    {
        List patientList = patientDao.selectOutpatientByCondition(
                patientListSearchPra, pagingContext);
        return patientList;
    }

    /**
     * 获取门诊患者集合(根据多个查询条件)
     * @param patientListSearchPra 患者查询条件
     * @param pagingContext
     * @return 患者集合
     */
    @Override
    @Transactional
    public List<PatientDto> selectInpatientByCondition(
            PatientListSearchPra patientListSearchPra,
            PagingContext pagingContext)
    {
        List patientList = patientDao.selectInpatientByCondition(
                patientListSearchPra, pagingContext);
        return patientList;
    }

    // $Author:chang_xuewen
    // $Date : 2013/12/18 14:10
    // $[BUG]0040770 MODIFY BEGIN
    /**
     * 根据患者EMPI_EID,得到患者交叉索引信息
     * @param patientEids
     * @param patientDomains
     * @param patientDomainTag
     * @return
     */
    @Override
    @Transactional
    public List<PatientCrossIndexDto> selectPatientCrossIndex(List patientEids,
            List patientDomains, String patientDomainTag, String orgCode)
    {
        List<PatientCrossIndexDto> patientCrossIndexDtos = patientDao.selectPatientCrossIndex(
                patientEids, patientDomains, patientDomainTag, orgCode);
        return patientCrossIndexDtos;
    }

    // $[BUG]0040770 MODIFY END
    /**
     * 获取最近患者集合
     * @param pagingContext
     * @return 患者集合
     */
    @Override
    @Transactional
    public List<PatientDto> selectRecentPatientByCondition(
            RecentPatientListSearchPra patientListSearchPra,
            PagingContext pagingContext)
    {
        List patientList = patientDao.selectRecentPatientByCondition(
                patientListSearchPra, pagingContext);
        return patientList;
    }

    // [FUN]V05-001:临床信息集成视图 ADD END

    // [FUN]A01-002挂号信息 ADD BEGIN
    /**
     * 获取患者信息(根据多个查询条件)
     * @param map 查询条件
     * @return 患者对象集合
     */
    @Transactional
    public Patient selectPatientByCondition(Map<String, Object> map)
    {
        List<Object> patientList = entityDao.selectByCondition(Patient.class,
                map);
        if (patientList != null && patientList.size() > 0)
            return (Patient) patientList.get(0);
        else
            return null;
    }

    /**
     * 通过临时患者表里的患者域，患者本地ID，得到患者
     * @param patientDomain 患者域
     * @param patientLid 患者本地ID
     * @return 患者
     */
    @Transactional
    public Patient getPatientByTempPatient(String patientDomain,
            String patientLid, String OrgCode)
    {
        List<Patient> patients = patientDao.getPatientByTempPatient(
                patientDomain, patientLid, OrgCode);
        if (patients.size() == 0)
            return null;
        else
            return patients.get(0);
    }

    // [FUN]A01-002挂号信息 ADD END

    /**
     * 根据患者empi_id，deleteFlag，在患者信息表里查询患者信息
     * @param patientEid
     * @param deleteFlag
     * @return 患者对象
     */
    @Transactional
    public Patient getPatient(String patientEid, String deleteFlag)
    {
        Map<String, Object> condition = new HashMap<String, Object>();
        condition.put("patientEid", patientEid);
        condition.put("deleteFlag", deleteFlag);
        return selectPatientByCondition(condition);
    }

    /**
     * 根据域ID，患者Lid从患者交叉索引查询患者相关信息
     * @param map
     * @return
     */
    @Transactional
    public PatientCrossIndex getPatientCrossIndexByCondition(
            Map<String, Object> map)
    {
        List<Object> patientCrossIndexList = entityDao.selectByCondition(
                PatientCrossIndex.class, map);
        if (patientCrossIndexList != null && patientCrossIndexList.size() > 0)
            return (PatientCrossIndex) patientCrossIndexList.get(0);
        else
            return null;
    }

    // [FUN]A01-029 过敏和生理状态信息 ADD BEGIN
    /**
     * 获取危险性信息(根据多个查询条件)
     * @param map 查询条件
     * @return 危险性信息
     */
    @Override
    @Transactional
    public List<Object> selectRiskInfoByCondition(Map<String, Object> map)
    {
        return entityDao.selectByCondition(RiskInformation.class, map);
    }

    /**
     * 获取过敏信息(根据多个查询条件)
     * @param map 查询条件
     * @return 过敏信息
     */
    @Override
    @Transactional
    public List<Object> selectAllergicHistoryByCondition(Map<String, Object> map)
    {
        return entityDao.selectByCondition(AllergicHistory.class, map);
    }

    /**
     * 保存过敏以及危险性消息
     * @param allergicHistories
     * @param riskInfos
     * @return 
     */
    @Override
    @Transactional
    public void saveAllergicHistoriesAndRiskInfos(
            List<AllergicHistory> allergicHistories,
            List<RiskInformation> riskInfos)
    {
        // 插入所有新增的过敏信息
        for (AllergicHistory allergicHistory : allergicHistories)
        {
            entityDao.insert(allergicHistory);
        }
        // 插入所有新增的危险性信息
        for (RiskInformation riskInfo : riskInfos)
        {
            entityDao.insert(riskInfo);
        }
    }

    /**
     * 更新过敏以及危险性消息（删除数据库中原记录，然后新增加现在消息中的记录）
     * @param allergicHistories
     * @param riskInfos
     * @return 
     */
    @Override
    @Transactional
    public void updateAllergicHistoriesAndRiskInfos(
            List<Object> existsRiskInfos, List<Object> existsAllergicHistories,
            List<AllergicHistory> allergicHistories,
            List<RiskInformation> riskInfos, Date systemTime, String serviceId)
    {
        // 删除该患者在数据库中存在的危险性原记录
        if (existsRiskInfos != null && existsRiskInfos.size() != 0)
        {
            for (int i = 0; i < existsRiskInfos.size(); i++)
            {
                RiskInformation riskInfoToDelete = (RiskInformation) existsRiskInfos.get(i);
                riskInfoToDelete.setDeleteFlag(Constants.DELETE_FLAG);
                riskInfoToDelete.setDeleteTime(systemTime);
                riskInfoToDelete.setUpdateTime(systemTime);
                // $Author: tong_meng
                // $Date: 2013/8/30 15:00
                // [BUG]0036757 ADD BEGIN
                riskInfoToDelete.setDeleteby(serviceId); // 设置创建人
                // [BUG]0036757 ADD END

                entityDao.update(riskInfoToDelete);
            }
        }
        // 删除该患者在数据库中存在的过敏性原记录
        if (existsAllergicHistories != null
            && existsAllergicHistories.size() != 0)
        {
            for (int i = 0; i < existsAllergicHistories.size(); i++)
            {
                AllergicHistory allergicHistory = (AllergicHistory) existsAllergicHistories.get(i);
                allergicHistory.setDeleteFlag(Constants.DELETE_FLAG);
                allergicHistory.setDeleteTime(systemTime);
                allergicHistory.setUpdateTime(systemTime);
                // $Author: tong_meng
                // $Date: 2013/8/30 15:00
                // [BUG]0036757 ADD BEGIN
                allergicHistory.setDeleteby(serviceId); // 设置创建人
                // [BUG]0036757 ADD END

                entityDao.update(allergicHistory);
            }
        }
        // 插入所有新增的过敏信息
        for (AllergicHistory allergicHistory : allergicHistories)
        {
            entityDao.insert(allergicHistory);
        }
        // 插入所有新增的危险性信息
        for (RiskInformation riskInfo : riskInfos)
        {
            entityDao.insert(riskInfo);
        }
    }

    /**
     * 更新过敏以及危险性消息
     * @param allergicHistories
     * @param riskInfos
     * @return 
     */
    @Override
    @Transactional
    public void saveAllergicHistoriesAndRiskInfos(Patient patient,
            PatientTemp tempPatient, List<AllergicHistory> allergicHistories,
            List<RiskInformation> riskInfos)
    {
        BigDecimal patientSn = commonService.getSequence("SEQ_PATIENT");
        patient.setPatientSn(patientSn);
        tempPatient.setPatientSn(patientSn);
        for (AllergicHistory allergicHistory : allergicHistories)
            allergicHistory.setPatientSn(patientSn);
        for (RiskInformation riskInfo : riskInfos)
            riskInfo.setPatientSn(patientSn);

        // 保存患者
        commonService.save(patient);
        // 保存临时患者
        commonService.save(tempPatient);
        // 保存所有的危险性信息及过敏信息
        this.saveAllergicHistoriesAndRiskInfos(allergicHistories, riskInfos);
    }

    // [FUN]A01-029 过敏和生理状态信息 ADD END

    // $Author :jin_peng
    // $Date : 2013/02/27 10:23$
    // [BUG]0014137 MODIFY BEGIN
    /**
     * 获取已经设置患者内部序列号的所有数据库对象集合(根据患者基本信息消息中的域id和患者本地id进行对应得到待操作对象)
     * @param patientDomain 域id
     * @param patientLid 患者本地id
     * @param patientSn 患者内部序列号
     * @param systemTime 系统当前时间
     * @return 已设值完成的数据库中符合条件的所有对象集合
     */
    public Map<String, Object> getHaveSetFieldsAllRelevanceObject(
            String patientDomain, String patientLid, BigDecimal patientSn,
            Date systemTime, String orgCode)
    {
        /*
         * List<Object> resList = new ArrayList<Object>();
         * 
         * // 设置相应查询条件 Map<String, Object> selectConditionMap = new
         * HashMap<String, Object>();
         * 
         * selectConditionMap.put("patientDomain", patientDomain);
         * 
         * selectConditionMap.put("patientLid", patientLid);
         * 
         * selectConditionMap.put("deleteFlag", Constants.NO_DELETE_FLAG);
         * 
         * // 根据所有相关业务对象循环赋值 for (Class<?> objClass :
         * Constants.UPDATE_RELEVANCE_CLASS_LIST) { // 根据相应查询条件获取相对应的相关业务对象集合
         * List<Object> objList = commonService.selectByCondition(objClass,
         * selectConditionMap);
         * 
         * // 如果相应查询结果存在则对所有相关业务对象进行属性设置 if (objList != null &&
         * !objList.isEmpty()) { // 设置需要设置的属性名及属性值 Map<String, Object>
         * setFieldsMap = new HashMap<String, Object>();
         * 
         * setFieldsMap.put("patientSn", patientSn);
         * 
         * setFieldsMap.put("updateTime", systemTime);
         * 
         * // 根据查询结果循环设置相应对象属性 for (Object obj : objList) { // 设置相应对象的属性值
         * ObjectUtils.setObjectPrivateFields(obj, setFieldsMap);
         * 
         * // 将已设置完成的对象添加到返回集合中 resList.add(obj); } } }
         * 
         * return resList;
         */

        // 设置更新患者相关业务表的更新信息
        Map<String, Object> updateRelationshipMap = new HashMap<String, Object>();

        updateRelationshipMap.put("patientDomain", patientDomain);

        updateRelationshipMap.put("patientLid", patientLid);

        updateRelationshipMap.put("patientSn", patientSn);

        updateRelationshipMap.put("systemTime", systemTime);

        updateRelationshipMap.put("orgCode", orgCode);

        return updateRelationshipMap;
    }

    // [BUG]0014137 MODIFY END

    // Author :jia_yanqing
    // Date : 2012/09/12 17:00
    // [BUG]0037319 MODIFY BEGIN
    /**
     * 新增 更新 删除传入的对象(新建患者基本信息服务)
     * @param saveOrUpdatePatient 需要新增或更新的患者基本信息对象
     * @param isSaveOrUpdate 新建或更新患者标识
     * @param deleteSnPatientList 需要逻辑删除的患者基本信息对象集合
     * @param patientContactList 需要新增的联系人对象集合
     * @param patientCredentialList 需要新增的证件对象集合
     * @param patientAddressList 需要新增的地址信息对象集合
     * @param patientCrossIndexAddList 需要新增的患者关联关系对象集合
     * @param patientCrossIndexUpdateList 需要更新的患者关联关系对象集合
     * @param patientTempList 需要逻辑删除的患者临时信息对象集合
     * @param relevanceBusinessList 需要更新患者内部序列号的所有对应业务对象集合
     */
    @Transactional
    public void saveOrUpdatePatientRelevance(Patient saveOrUpdatePatient,
            boolean isSaveOrUpdate, List<Patient> deleteSnPatientList,
            List<PatientContact> patientContactList,
            List<PatientCredential> patientCredentialList,
            List<PatientAddress> patientAddressList,
            List<PatientCrossIndex> patientCrossIndexAddList,
            List<PatientCrossIndex> patientCrossIndexUpdateList,
            List<PatientTemp> patientTempList,
            List<Map<String, Object>> relevanceBusinessList, String serviceid)
    // [BUG]0037319 MODIFY END
    {
        // $Author :jin_peng
        // $Date : 2012/09/01 11:13$
        // [BUG]0009326 MODIFY BEGIN
        if (saveOrUpdatePatient != null)
        {
            if (isSaveOrUpdate)
            {
                // 新增患者基本信息
                commonService.save(saveOrUpdatePatient);
            }
            else
            {
                // 更新患者基本信息
                commonService.update(saveOrUpdatePatient);
            }
        }
        // [BUG]0009326 MODIFY END

        // 新增联系人信息
        if (patientContactList != null && !patientContactList.isEmpty())
        {
            commonService.saveList(patientContactList);
        }

        // 新增证件信息
        if (patientCredentialList != null && !patientCredentialList.isEmpty())
        {
            commonService.saveList(patientCredentialList);
        }

        // 新增地址信息
        if (patientAddressList != null && !patientAddressList.isEmpty())
        {
            commonService.saveList(patientAddressList);
        }
        
        // 更新所有对应业务的患者内部序列号
        if (relevanceBusinessList != null && !relevanceBusinessList.isEmpty())
        {
            // $Author:jin_peng
            // $Date:2014/04/17 13:14
            // [BUG]0043616 ADD BEGIN
            // Author :jia_yanqing
            // Date : 2012/09/12 17:00
            // [BUG]0037319 MODIFY BEGIN
            // $Author :jin_peng
            // $Date : 2013/02/27 10:23$
            // [BUG]0014137 MODIFY BEGIN
            // commonService.updatePartiallyAll(relevanceBusinessList,
            // "patientSn", "updateTime");
            obtainTablesNameByUserTabCols();

            for (Map<String, Object> map : relevanceBusinessList)
            {
                commonService.updateEmpiRelationship(
                        StringUtils.strToBigDecimal(
                                String.valueOf(map.get("patientSn")), "患者内部序列号"),
                        DateFormat.getDateTimeInstance().format(new Date()),
                        String.valueOf(map.get("patientLid")),
                        String.valueOf(map.get("patientDomain")), serviceid,
                        String.valueOf(map.get("orgCode")), userTablesName);
            }

            // [BUG]0014137 MODIFY END
            // [BUG]0037319 MODIFY END
            // [BUG]0043616 ADD END
        }

        // 新增患者关联关系信息
        if (patientCrossIndexAddList != null
            && !patientCrossIndexAddList.isEmpty())
        {
            commonService.saveList(patientCrossIndexAddList);
        }

        // 更新患者关联关系信息
        if (patientCrossIndexUpdateList != null
            && !patientCrossIndexUpdateList.isEmpty())
        {
            commonService.updateList(patientCrossIndexUpdateList);
        }

        // 逻辑删除患者临时信息
        if (patientTempList != null && !patientTempList.isEmpty())
        {
            commonService.updatePartiallyAll(patientTempList, "deleteFlag",
                    "updateTime", "deleteTime", "deleteby");
        }

        // 逻辑删除患者基本信息
        if (deleteSnPatientList != null && !deleteSnPatientList.isEmpty())
        {
            commonService.updatePartiallyAll(deleteSnPatientList, "deleteFlag",
                    "updateTime", "deleteTime", "deleteby");
        }
    }

    /**
     * 新增 更新传入的对象(更新患者基本信息服务)
     * @param updatePatient 需要更新的eid对应患者基本信息对象
     * @param patientContactList 需要新增的联系人对象集合
     * @param patientCredentialList 需要新增的证件对象集合
     * @param patientAddressList 需要新增的地址信息对象集合
     * @param patientContactDeleteList 需要删除的联系人对象集合
     * @param patientCredentialDeleteList 需要删除的证件对象集合
     * @param patientAddressDeleteList 需要删除的地址信息对象集合
     * @param patientCrossIndexAddList 需要新增的交叉索引记录对象集合
     * @param patientCrossIndexUpdateList 需要更新的交叉索引记录对象集合
     * @param deleteSnPatientList 需要逻辑删除的患者基本信息对象集合
     * @param patientTempList 需要逻辑删除的患者临时信息对象集合
     * @param relevanceBusinessList 需要更新患者内部序列号的所有对应业务对象
     */
    @Transactional
    public void saveOrUpdatePatientRelevance(Patient updatePatient,
            List<PatientContact> patientContactList,
            List<PatientCredential> patientCredentialList,
            List<PatientAddress> patientAddressList,
            List<Object> patientContactDeleteList,
            List<Object> patientCredentialDeleteList,
            List<Object> patientAddressDeleteList,
            List<PatientCrossIndex> patientCrossIndexAddList,
            List<PatientCrossIndex> patientCrossIndexUpdateList,
            List<Patient> deleteSnPatientList,
            List<PatientTemp> patientTempList,
            List<Map<String, Object>> relevanceBusinessList, String serviceid)
    {
        // 更新eid对象的患者基本信息
        if (updatePatient != null)
        {
            commonService.update(updatePatient);
        }

        // 新增联系人信息
        if (patientContactList != null && !patientContactList.isEmpty())
        {
            commonService.saveList(patientContactList);
        }

        // 新增证件信息
        if (patientCredentialList != null && !patientCredentialList.isEmpty())
        {
            commonService.saveList(patientCredentialList);
        }

        // 新增地址信息
        if (patientAddressList != null && !patientAddressList.isEmpty())
        {
            commonService.saveList(patientAddressList);
        }

        // 删除老的联系人信息
        if (patientContactDeleteList != null
            && !patientContactDeleteList.isEmpty())
        {
            for (Object object : patientContactDeleteList)
            {
                PatientContact patientContactToDelete = (PatientContact) object;
                patientContactToDelete.setDeleteFlag(Constants.DELETE_FLAG);
                patientContactToDelete.setDeleteby(serviceid);
                entityDao.update(patientContactToDelete);
            }
        }

        // 删除老的证件信息
        if (patientCredentialDeleteList != null
            && !patientCredentialDeleteList.isEmpty())
        {
            for (Object object : patientCredentialDeleteList)
            {
                PatientCredential patientCredentialToDelete = (PatientCredential) object;
                patientCredentialToDelete.setDeleteFlag(Constants.DELETE_FLAG);
                patientCredentialToDelete.setDeleteby(serviceid);
                entityDao.update(patientCredentialToDelete);
            }
        }

        // 删除老的地址信息
        if (patientAddressDeleteList != null
            && !patientAddressDeleteList.isEmpty())
        {
            for (Object object : patientAddressDeleteList)
            {
                PatientAddress patientAdressToDelete = (PatientAddress) object;
                patientAdressToDelete.setDeleteFlag(Constants.DELETE_FLAG);
                patientAdressToDelete.setDeleteby(serviceid);
                entityDao.update(patientAdressToDelete);
            }
        }
        
        // 更新所有对应业务的患者内部序列号
        if (relevanceBusinessList != null && !relevanceBusinessList.isEmpty())
        {
            // $Author:jin_peng
            // $Date:2014/04/17 13:14
            // [BUG]0043616 ADD BEGIN
            // Author :jia_yanqing
            // Date : 2012/09/12 17:00
            // [BUG]0037319 MODIFY BEGIN
            // $Author :jin_peng
            // $Date : 2013/02/27 10:23$
            // [BUG]0014137 MODIFY BEGIN
            // commonService.updatePartiallyAll(relevanceBusinessList,
            // "patientSn", "updateTime");
            obtainTablesNameByUserTabCols();

            for (Map<String, Object> map : relevanceBusinessList)
            {
                commonService.updateEmpiRelationship(
                        StringUtils.strToBigDecimal(
                                String.valueOf(map.get("patientSn")), "患者内部序列号"),
                        DateFormat.getDateTimeInstance().format(new Date()),
                        String.valueOf(map.get("patientLid")),
                        String.valueOf(map.get("patientDomain")), serviceid,
                        String.valueOf(map.get("orgCode")), userTablesName);
            }

            // [BUG]0014137 MODIFY END
            // [BUG]0037319 MODIFY END
            // [BUG]0043616 ADD END
        }

        // 新增患者关联关系信息
        if (patientCrossIndexAddList != null
            && !patientCrossIndexAddList.isEmpty())
        {
            commonService.saveList(patientCrossIndexAddList);
        }

        // 更新患者关联关系信息
        if (patientCrossIndexUpdateList != null
            && !patientCrossIndexUpdateList.isEmpty())
        {
            commonService.updateList(patientCrossIndexUpdateList);
        }

        // $Author :jin_peng
        // $Date : 2012/09/03 10:31$
        // [BUG]0009336 ADD BEGIN
        // 逻辑删除患者临时信息
        if (patientTempList != null && !patientTempList.isEmpty())
        {
            commonService.updatePartiallyAll(patientTempList, "deleteFlag",
                    "updateTime", "deleteTime");
        }

        // 逻辑删除患者基本信息
        if (deleteSnPatientList != null && !deleteSnPatientList.isEmpty())
        {
            commonService.updatePartiallyAll(deleteSnPatientList, "deleteFlag",
                    "updateTime", "deleteTime");
        }
        // [BUG]0009336 ADD END
    }

    // $Author:jin_peng
    // $Date:2014/04/17 13:14
    // [BUG]0043616 ADD BEGIN
    /**
     * 通过updateby字段查询包含该字段的表相关内容并设置到指定变量中
     */
    private void obtainTablesRelatedByColumnUpdateby()
    {
        if (userTabColList == null)
        {
            List<String> targetList = new ArrayList<String>();
            targetList.addAll(Constants.UPDATE_RELEVANCE_TBALE_NAME_LIST);
            targetList.addAll(Constants.UPDATE_RELEVANCE_TBALE_NAME_FOR_VISIT_LIST);
            targetList.addAll(Constants.UPDATE_RELEVANCE_TBALE_NAME_FOR_PATIENT_LIST);
            
            userTabColList = commonService.selectExistsColumnOrNot(
                    EMPI_TABLE_FILTER_COLUMN,
                    targetList);
        }
    }
    
    private void obtainTablesNameByUserTabCols()
    {
        if(userTablesName == null)
        {
            obtainTablesRelatedByColumnUpdateby();
            
            userTablesName = new ArrayList<String>();
            
            for (UserTabColDto userTabCol : userTabColList)
            {
                userTablesName.add(userTabCol.getTableName());
            }
        }
    }
    
    // [BUG]0043616 ADD END

    // $Author :jin_peng
    // $Date : 2012/09/11 16:00$
    // [BUG]0009644 ADD BEGIN
    // $Author :chang_xuewen
    // $Date : 2013/08/31 16:00$
    // [BUG]0036757 MODIFY BEGIN
    /**
     * 删除传入的对象(删除患者基本信息服务)
     * @param patientDeleteList 需要逻辑删除的患者基本信息对象集合
     * @param patientContactDeleteList 需要逻辑删除的患者联系人对象集合
     * @param patientCredentialDeleteList 需要逻辑删除的患者证件对象集合
     * @param patientAddressDeleteList 需要逻辑删除的患者地址对象集合
     */
    @Transactional
    public void deletePatientRelevance(List<Patient> patientDeleteList,
            List<PatientContact> patientContactDeleteList,
            List<PatientCredential> patientCredentialDeleteList,
            List<PatientAddress> patientAddressDeleteList)
    {
        // 逻辑删除患者基本信息
        if (patientDeleteList != null && !patientDeleteList.isEmpty())
        {
            commonService.updatePartiallyAll(patientDeleteList, "deleteFlag",
                    "updateTime", "deleteTime", "deleteby");
        }

        // 逻辑删除患者联系人信息
        if (patientContactDeleteList != null
            && !patientContactDeleteList.isEmpty())
        {
            commonService.updatePartiallyAll(patientContactDeleteList,
                    "deleteFlag", "updateTime", "deleteTime", "deleteby");
        }

        // 逻辑删除患者证件信息
        if (patientCredentialDeleteList != null
            && !patientCredentialDeleteList.isEmpty())
        {
            commonService.updatePartiallyAll(patientCredentialDeleteList,
                    "deleteFlag", "updateTime", "deleteTime", "deleteby");
        }

        // 逻辑删除患者地址信息
        if (patientAddressDeleteList != null
            && !patientAddressDeleteList.isEmpty())
        {
            commonService.updatePartiallyAll(patientAddressDeleteList,
                    "deleteFlag", "updateTime", "deleteTime", "deleteby");
        }
    }

    // [BUG]0036757 MODIFY END
    // [BUG]0009644 ADD END

    // $Author:wu_jianfeng
    // $Date : 2012/09/12 14:10
    // $[BUG]0009752 ADD BEGIN
    // 访问控制实现
    @Override
    @Transactional
    public List<PatientDto> selectInpatientByCondition(
            PatientListSearchPra patientListSearchPra, AccessControlDto aclDto,
            PagingContext pagingContext)
    {
        List patientList = patientDao.selectInpatientByACLCondition(
                patientListSearchPra, aclDto, pagingContext);
        return patientList;
    }

    @Override
    @Transactional
    public List<PatientDto> selectOutpatientByCondition(
            PatientListSearchPra patientListSearchPra, AccessControlDto aclDto,
            PagingContext pagingContext)
    {
        List patientList = patientDao.selectOutpatientByACLCondition(
                patientListSearchPra, aclDto, pagingContext);
        return patientList;
    }

    @Override
    @Transactional
    public List<PatientDto> selectRecentPatientByCondition(
            RecentPatientListSearchPra patientListSearchPra,
            AccessControlDto aclDto, PagingContext pagingContext)
    {
        List patientList = patientDao.selectRecentPatientByACLCondition(
                patientListSearchPra, aclDto, pagingContext);
        return patientList;
    }

    @Override
    @Transactional
    public List<PatientDto> selectPatientByCondition(
            PatientListSearchPra patientListSearchPra, AccessControlDto aclDto,
            Boolean visitInfoIsEmpty, Boolean empiInfoIsEmpty,
            PagingContext pagingContext)
    {
        List patientList = patientDao.selectPatientByACLCondition(
                patientListSearchPra, aclDto, visitInfoIsEmpty,
                empiInfoIsEmpty, pagingContext);
        return patientList;
    }

    // $[BUG]0009752 ADD END

    // $Author:wu_jianfeng
    // $Date : 2012/11/1 14:10
    // $[BUG]0010832 ADD BEGIN
    @Override
    @Transactional
    public List<PatientDto> selectPatientByCondition(
            PatientListAdvanceSearchPra patientListSearchPra,
            PagingContext pagingContext)
    {
        String sql = PatientListAdvanceSearchSql.getSQL(patientListSearchPra);
        int pageCount = pagingContext.getRowCnt();
        int pageNo = pagingContext.getPageNo();
        int recordStartNum = (pageNo - 1) * pageCount;
        int recordEndNum = pageNo * pageCount;
        List patientList = patientDao.selectPatientByASCondition(sql, pageNo,
                recordStartNum, recordEndNum);
        return patientList;
    }

    @Override
    @Transactional
    public List<PatientDto> selectPatientByCondition(
            PatientListAdvanceSearchPra patientListSearchPra,
            AccessControlDto aclDto, PagingContext pagingContext)
    {
        String sql = PatientListACLAdvanceSearchSql.getSQL(aclDto,
                patientListSearchPra);
        int pageCount = pagingContext.getRowCnt();
        int pageNo = pagingContext.getPageNo();
        int recordStartNum = (pageNo - 1) * pageCount;
        int recordEndNum = pageNo * pageCount;
        List patientList = patientDao.selectPatientByASCondition(sql, pageNo,
                recordStartNum, recordEndNum);
        return patientList;
    }

    @Override
    @Transactional
    public int selectPatientListTotalCnt(
            PatientListAdvanceSearchPra patientListSearchPra,
            AccessControlDto aclDto)
    {
        String sql = PatientListACLAdvanceSearchSql.getSQL(aclDto,
                patientListSearchPra);
        int total_count = patientDao.selectPatientListTotalCnt(sql);
        return total_count;
    }

    @Override
    @Transactional
    public int selectPatientListTotalCnt(
            PatientListAdvanceSearchPra patientListSearchPra)
    {
        String sql = PatientListAdvanceSearchSql.getSQL(patientListSearchPra);
        int total_count = patientDao.selectPatientListTotalCnt(sql);
        return total_count;
    }
    // $[BUG]0010832 ADD END
	
    @Override
	public List<PatientAppointDto> selectPatientAppointByCondition(
			PatientListSearchPra patientListSearchPra,
			PagingContext pagingContext) {
		return  patientDao.selectPatientAppointByCondition(
                patientListSearchPra, pagingContext);
	}
    @Override
	public List<PatientAppointDto> selectPatientAppointByCondition(
			PatientListSearchPra patientListSearchPra, AccessControlDto aclDto,
			PagingContext pagingContext) {
		return  patientDao.selectPatientAppointByACLCondition(
                patientListSearchPra, aclDto,pagingContext);
	}
    

}
