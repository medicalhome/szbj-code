/**
 * [FUN]V03-001患者列表
 * [FUN]V04-001患者详细
 * 
 * @version 1.0, 2012/03/12  20:23:00
 
 * @author wu_jianfeng, wen_ruichao
 * @since 1.0
*/

package com.founder.cdr.dao;

import java.util.List;

import com.founder.cdr.dto.AccessControlDto;
import com.founder.cdr.dto.PatientAppointDto;
import com.founder.cdr.dto.PatientCrossIndexDto;
import com.founder.cdr.dto.PatientDto;
import com.founder.cdr.dto.patient.PatientListAdvanceSearchPra;
import com.founder.cdr.dto.patient.PatientListSearchPra;
import com.founder.cdr.dto.patient.RecentPatientListSearchPra;
import com.founder.cdr.entity.Patient;
import com.founder.cdr.entity.PatientAddress;
import com.founder.cdr.entity.PatientContact;
import com.founder.cdr.entity.PatientCredential;
import com.founder.cdr.entity.RiskInformation;
import com.founder.cdr.entity.SocialInsurance;
import com.founder.fasf.core.util.daohelper.annotation.Arguments;
import com.founder.fasf.web.paging.PagingContext;

public interface PatientDao
{
    // $Author : wen_ruichao
    // $Date : 2012/03/12 9:56$  
    // [FUN]V04-001患者详细 ADD BEGIN

    /**
     * 根据患者内部序列号获取患者对象
     * @param patientSn 患者内部序列号
     * @return 患者对象
     */
    @Arguments({ "patientSn" })
    public Patient getPatient(String patientSn);

    /**
     * 根据患者内部序列号获取患者地址列表
     * @param patientSn 患者内部序列号
     * @return 患者地址列表
     */
    @Arguments({ "patientSn" })
    public PatientAddress[] selectPatientAddresses(String patientSn);

    /**
     * 根据患者内部序列号获取患者联系人列表
     * @param patientSn 患者内部序列号
     * @return 患者联系人列表
     */
    @Arguments({ "patientSn" })
    public PatientContact[] selectPatientContacts(String patientSn);

    /**
     * 根据患者内部序列号获取患者证件列表
     * @param patientSn 患者内部序列号
     * @return 患者证件列表
     */
    @Arguments({ "patientSn" })
    public PatientCredential[] selectPatientCredentials(String patientSn);

    /**
     * 根据患者内部序列号获取患者医保信息
     * @param patientSn 患者内部序列号
     * @return 患者医保信息
     */
    @Arguments({ "patientSn" })
    public SocialInsurance getSocialInsurance(String patientSn);

    /**
     * 根据患者内部序列号获取患者危险性信息列表
     * @param patientSn 患者内部序列号
     * @return 患者危险性信息列表
     */
    @Arguments({ "patientSn" })
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
    @Arguments({ "patientName", "genderCode", "visitStartDate", "visitEndDate" })
    List<PatientDto> selectAllPatient(String patientName, String genderCode,
            String visitStartDate, String visitEndDate,
            PagingContext pagingContext);

    // [FUN]V03-001患者列表 ADD END

    // [FUN]V05-001:临床信息集成视图 ADD BEGIN

    /**
     * 根据患者序列号，地址类型，得到患者地址信息
     * @param patientSn 患者序列号
     * @param addressType 地址类型
     * @return 患者地址信息
     */
    @Arguments({ "patientSn", "addressType" })
    List<PatientAddress> selectPatientAddresses(String patientSn,
            String addressType);

    // [FUN]V05-001:临床信息集成视图 ADD END

    // [FUN]A01-002挂号信息 ADD BEGIN

    /**
     * 根据患者域代码，患者本地id，得到患者临时信息
     * @param patientDoamin 患者域代码
     * @param patientLid 患者本地ID
     * @return 患者信息
     */
    @Arguments({ "patientDomain", "patientLid", "orgCode"})
    List<Patient> getPatientByTempPatient(String patientDomain,
            String patientLid,String OrgCode);

    // [FUN]A01-002挂号信息 ADD END

    /**
     * 根据检索条件，查找患者信息
     * @param patientListSearchPra 患者检索条件
     * @param pagingContext
     * @return 患者集合
     */
    @Arguments({ "patientListSearchPra", "visitInfoIsEmpty", "empiInfoIsEmpty" })
    List<PatientDto> selectPatientByCondition(
            PatientListSearchPra patientListSearchPra,
            Boolean visitInfoIsEmpty, Boolean empiInfoIsEmpty,
            PagingContext pagingContext);

    /**
     * 根据检索条件，查找门诊患者信息
     * @param patientListSearchPra 患者检索条件
     * @param pagingContext
     * @return 门诊患者集合
     */
    @Arguments({ "patientListSearchPra" })
    List<PatientDto> selectOutpatientByCondition(
            PatientListSearchPra patientListSearchPra,
            PagingContext pagingContext);

    /**
     * 根据检索条件，查找住院患者信息
     * @param patientListSearchPra 患者检索条件
     * @param pagingContext
     * @return 住院患者集合
     */
    @Arguments({ "patientListSearchPra" })
    List<PatientDto> selectInpatientByCondition(
            PatientListSearchPra patientListSearchPra,
            PagingContext pagingContext);

    /**
     * 根据检索条件，查找最近患者信息
     * @param patientListSearchPra 患者检索条件
     * @param pagingContext
     * @return 最近患者集合
     */
    @Arguments({ "patientListSearchPra" })
    List<PatientDto> selectRecentPatientByCondition(
            RecentPatientListSearchPra patientListSearchPra,
            PagingContext pagingContext);

    /**
     * 根据患者Eid，查找患者交叉索引信息 
     * @param patientEids
     * @return 患者交叉索引信息集合
     */
    @Arguments({ "patientEids", "patientDomains", "patientDomainTag", "orgCode"})
    List<PatientCrossIndexDto> selectPatientCrossIndex(List patientEids,
            List patientDomains,String patientDomainTag, String orgCode);

    // $Author:wu_jianfeng
    // $Date : 2012/09/12 14:10
    // $[BUG]0009752 ADD BEGIN
    // 访问控制实现
    @Arguments({ "patientListSearchPra", "acl" })
    List<PatientDto> selectOutpatientByACLCondition(
            PatientListSearchPra patientListSearchPra, AccessControlDto acl,
            PagingContext pagingContext);

    @Arguments({ "patientListSearchPra", "acl" })
    List<PatientDto> selectInpatientByACLCondition(
            PatientListSearchPra patientListSearchPra, AccessControlDto acl,
            PagingContext pagingContext);

    @Arguments({ "patientListSearchPra", "acl" })
    List<PatientDto> selectRecentPatientByACLCondition(
            RecentPatientListSearchPra patientListSearchPra,
            AccessControlDto acl, PagingContext pagingContext);

    @Arguments({ "patientListSearchPra", "acl", "visitInfoIsEmpty",
            "empiInfoIsEmpty" })
    List<PatientDto> selectPatientByACLCondition(
            PatientListSearchPra patientListSearchPra, AccessControlDto acl,
            Boolean visitInfoIsEmpty, Boolean empiInfoIsEmpty,
            PagingContext pagingContext);

    // $[BUG]0009752 ADD END

    // $Author:wu_jianfeng
    // $Date : 2012/11/1 14:10
    // $[BUG]0010832 ADD BEGIN
    @Arguments({ "sql", "pageNo", "recordStartNum", "recordEndNum" })
    List<PatientDto> selectPatientByASCondition(String sql, int pageNo,
            int recordStartNum, int recordEndNum);

    @Arguments({ "sql" })
    int selectPatientListTotalCnt(String sql);
    // $[BUG]0010832 ADD END
    
    @Arguments({ "patientListSearchPra" })
    List<PatientAppointDto> selectPatientAppointByCondition(
			PatientListSearchPra patientListSearchPra,
			PagingContext pagingContext);
    
    @Arguments({ "patientListSearchPra", "acl"})
    List<PatientAppointDto> selectPatientAppointByACLCondition(
			PatientListSearchPra patientListSearchPra, AccessControlDto acl,
			PagingContext pagingContext);
}
