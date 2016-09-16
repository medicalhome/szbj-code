/**
 * [FUN]V03-001患者列表
 * [FUN]V04-001患者详细
 * 
 * @version 1.0, 2012/03/12  20:23:00
 
 * @author wu_jianfeng, wen_ruichao
 * @since 1.0
*/
package com.founder.cdr.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Transactional;

import com.founder.cdr.dto.AccessControlDto;
import com.founder.cdr.dto.PatientAppointDto;
import com.founder.cdr.dto.PatientCrossIndexDto;
import com.founder.cdr.dto.PatientDto;
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
import com.founder.fasf.web.paging.PagingContext;

public interface PatientService
{
    // $Author : wen_ruichao
    // $Date : 2012/03/12 9:56$  
    // [FUN]V04-001患者详细 ADD BEGIN

    /**
     * 保存患者信息
     * @param patient 患者对象
     */
    public int savePatient(Patient patient);

    /**
     * 保存患者地址信息
     * @param patientAddress
     * @return
     */
    public int savePatientAddreses(PatientAddress[] patientAddresses);

    /**
     * 保存患者联系人信息
     * @param patientContacts
     * @return
     */
    public int savePatientContact(PatientContact[] patientContacts);

    /**
     * 保存患者证件信息
     * @param patientCredentials
     * @return
     */
    public int savePatientCredential(PatientCredential[] patientCredentials);

    /**
    * 保存患者医保信息
    * @param socialInsurances
    * @return
    */
    public int saveSocialInsurance(SocialInsurance socialInsurances);

    /**
    * 保存患者危险性信息
    * @param riskInformations
    * @return
    */
    public int saveRiskInformation(RiskInformation[] riskInformations);

    /**
     * 根据患者内部序列号获取患者对象
     * @param patientSn 患者内部序列号
     * @return 患者对象
     */
    public Patient getPatient(String patientSn);

    /**
     * 根据患者内部序列号获取患者地址列表
     * @param patientSn 患者内部序列号
     * @return 患者地址列表
     */
    public PatientAddress[] selectPatientAddresses(String patientSn);

    /**
     * 根据患者内部序列号获取患者联系人列表
     * @param patientSn 患者内部序列号
     * @return 患者联系人列表
     */
    public PatientContact[] selectPatientContacts(String patientSn);

    /**
     * 根据患者内部序列号获取患者证件列表
     * @param patientSn 患者内部序列号
     * @return 患者证件列表
     */
    public Object selectPatientCredentials(String patientSn);

    /**
     * 根据患者内部序列号获取患者医保信息
     * @param patientSn 患者内部序列号
     * @return 患者医保信息
     */
    public SocialInsurance getSocialInsurance(String patientSn);

    /**
     * 根据患者内部序列号获取患者危险性信息列表
     * @param patientSn 患者内部序列号
     * @return 患者危险性信息列表
     */
    public RiskInformation[] selectRiskInformations(String patientSn);

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
    List<PatientDto> selectAllPatient(String patientName, String genderCode,
            String visitStartDate, String visitEndDate,
            PagingContext pagingContext);

    // [FUN]V03-001患者列表 ADD END

    // [FUN]V05-001:临床信息集成视图 ADD BEGIN

    /**
     * 根据患者序列号，地址类型，得到最新的地址信息
     * @param patientSn 患者序列号
     * @param addressType 地址类型
     * @return 患者最新地址信息
     */
    PatientAddress getLatestPatientAddress(String patientSn, String addressType);

    /**
     * 根据业务系统的域ID和患者LID，得到患者序列号
     * @param domainId 域ID
     * @param patientLid 患者Lid
     * @return patientSn 患者序列号
     */
    String getPatientSn(String domainId, String patientLid);

    /**
     * 根据患者序列号，得到患者警告信息
     * @param patientSn 患者序列号
     * @return 患者警告信息
     */
    String getPatientAlerts(String patientSn);

    /**
     * 获取患者集合(根据多个查询条件)
     * @param patientListSearchPra 患者查询条件
     * @param pagingContext
     * @return 患者集合
     */
    public List<PatientDto> selectPatientByCondition(
            PatientListSearchPra patientListSearchPra,
            Boolean visitInfoIsEmpty, Boolean empiInfoIsEmpty,
            PagingContext pagingContext);

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
    public List<PatientCrossIndexDto> selectPatientCrossIndex(List patientEids,
            List patientDomains, String patientDomainTag, String orgCode);

    // $[BUG]0040770 MODIFY END
    /**
     * 获取最近患者集合(根据多个查询条件)
     * @param patientListSearchPra 患者查询条件
     * @param pagingContext
     * @return 患者集合
     */
    public List<PatientDto> selectRecentPatientByCondition(
            RecentPatientListSearchPra patientListSearchPra,
            PagingContext pagingContext);

    /**
     * 获取门诊患者集合(根据多个查询条件)
     * @param patientListSearchPra 患者查询条件
     * @param pagingContext
     * @return 患者集合
     */
    public List<PatientDto> selectOutpatientByCondition(
            PatientListSearchPra patientListSearchPra,
            PagingContext pagingContext);

    /**
     * 获取住院患者集合(根据多个查询条件)
     * @param patientListSearchPra 患者查询条件
     * @param pagingContext
     * @return 患者集合
     */
    public List<PatientDto> selectInpatientByCondition(
            PatientListSearchPra patientListSearchPra,
            PagingContext pagingContext);

    // [FUN]V05-001:临床信息集成视图 ADD END

    // [FUN]A01-002挂号信息 ADD BEGIN
    /**
     * 获取患者信息(根据多个查询条件)
     * @param map 查询条件
     * @return 患者对象
     */
    public Patient selectPatientByCondition(Map<String, Object> map);

    /**
     * 通过临时患者表里的患者域，患者本地ID，得到患者
     * @param patientDomain 患者域
     * @param patientLid 患者本地ID
     * @return 患者
     */
    public Patient getPatientByTempPatient(String patientDomain,
            String patientLid, String OrgCode);

    // [FUN]A01-002挂号信息 ADD END

    /**
     * 根据患者empi_id，deleteFlag，在患者信息表里查询患者信息
     * @param patientEid
     * @param deleteFlag
     * @return 患者对象
     */
    public Patient getPatient(String patientEid, String deleteFlag);

    /**
     * 根据域ID，患者Lid从患者交叉索引查询患者相关信息
     * @param map
     * @return
     */
    public PatientCrossIndex getPatientCrossIndexByCondition(
            Map<String, Object> map);

    // [FUN]A01-029 过敏和生理状态信息 ADD BEGIN
    /**
     * 获取危险性信息(根据多个查询条件)
     * @param map 查询条件
     * @return 危险性信息
     */
    public List<Object> selectRiskInfoByCondition(Map<String, Object> map);

    /**
     * 获取过敏信息(根据多个查询条件)
     * @param map 查询条件
     * @return 过敏信息
     */
    public List<Object> selectAllergicHistoryByCondition(Map<String, Object> map);

    /**
     * 保存过敏以及危险性消息
     * @param allergicHistories
     * @param riskInfos
     * @return 
     */
    @Transactional
    public void saveAllergicHistoriesAndRiskInfos(
            List<AllergicHistory> allergicHistories,
            List<RiskInformation> riskInfos);

    /**
     * 更新过敏以及危险性消息
     * @param allergicHistories
     * @param riskInfos
     * @param serviceId 
     * @return 
     */
    @Transactional
    public void updateAllergicHistoriesAndRiskInfos(
            List<Object> existsRiskInfos, List<Object> existsAllergicHistories,
            List<AllergicHistory> allergicHistories,
            List<RiskInformation> riskInfos, Date systemTime, String serviceId);

    /**
     * 更新过敏以及危险性消息
     * @param allergicHistories
     * @param riskInfos
     * @return 
     */
    @Transactional
    public void saveAllergicHistoriesAndRiskInfos(Patient patient,
            PatientTemp tempPatient, List<AllergicHistory> allergicHistories,
            List<RiskInformation> riskInfos);

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
            Date systemTime, String orgCode);

    // [BUG]0014137 MODIFY END

    // Author :jia_yanqing
    // Date : 2012/09/12 17:00
    // [BUG]0037319 MODIFY BEGIN
    // $Author :jin_peng
    // $Date : 2012/09/01 11:13$
    // [BUG]0009326 MODIFY BEGIN
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
            List<Map<String, Object>> relevanceBusinessList, String serviceId);

    // [BUG]0009326 MODIFY END
    // [BUG]0037319 MODIFY END

    // $Author :jin_peng
    // $Date : 2012/09/03 10:31$
    // [BUG]0009336 MODIFY BEGIN
    // $Author :chang_xuewen
    // $Date : 2013/08/31 16:00$
    // [BUG]0036757 MODIFY BEGIN
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
            List<Map<String, Object>> relevanceBusinessList, String serviceid);

    // [BUG]0036757 MODIFY END
    // [BUG]0009336 MODIFY END

    // $Author :jin_peng
    // $Date : 2012/09/11 16:00$
    // [BUG]0009644 ADD BEGIN
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
            List<PatientAddress> patientAddressDeleteList);

    // [BUG]0009644 ADD END

    // $Author:wu_jianfeng
    // $Date : 2012/09/12 14:10
    // $[BUG]0009752 ADD BEGIN
    // 访问控制实现
    public List<PatientDto> selectInpatientByCondition(
            PatientListSearchPra patientListSearchPra, AccessControlDto aclDto,
            PagingContext pagingContext);

    public List<PatientDto> selectOutpatientByCondition(
            PatientListSearchPra patientListSearchPra, AccessControlDto aclDto,
            PagingContext pagingContext);

    public List<PatientDto> selectRecentPatientByCondition(
            RecentPatientListSearchPra patientListSearchPra,
            AccessControlDto aclDto, PagingContext pagingContext);

    public List<PatientDto> selectPatientByCondition(
            PatientListSearchPra patientListSearchPra, AccessControlDto aclDto,
            Boolean visitInfoIsEmpty, Boolean empiInfoIsEmpty,
            PagingContext pagingContext);

    // $[BUG]0009752 ADD END

    // $Author:wu_jianfeng
    // $Date : 2012/11/1 14:10
    // $[BUG]0010832 ADD BEGIN
    public List<PatientDto> selectPatientByCondition(
            PatientListAdvanceSearchPra patientListSearchPra,
            PagingContext pagingContext);

    public List<PatientDto> selectPatientByCondition(
            PatientListAdvanceSearchPra patientListSearchPra,
            AccessControlDto aclDto, PagingContext pagingContext);

    public int selectPatientListTotalCnt(
            PatientListAdvanceSearchPra patientListSearchPra,
            AccessControlDto aclDto);

    public int selectPatientListTotalCnt(
            PatientListAdvanceSearchPra patientListSearchPra);
    // $[BUG]0010832 ADD END
    
    public List<PatientAppointDto> selectPatientAppointByCondition(
    		PatientListSearchPra patientListSearchPra,
            PagingContext pagingContext);
    
	public List<PatientAppointDto> selectPatientAppointByCondition(
			PatientListSearchPra patientListSearchPra, AccessControlDto aclDto,
			PagingContext pagingContext) ;
}
