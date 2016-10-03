package com.yly.cdr.dao.empiRelationshipBussiness;

import java.math.BigDecimal;
import java.util.List;

import com.founder.fasf.core.util.daohelper.annotation.Arguments;
import com.yly.cdr.dto.UserTabColDto;

public interface EmpiRelationshipDao
{
    // Author :jia_yanqing
    // Date : 2012/09/12 17:00
    // [BUG]0037319 MODIFY BEGIN
    /**
     * 更新患者相关各业务表patientSn
     * @param patientSn 待替换的patientSn
     * @param updateTime 更新时间
     * @param patientLid 患者本地id
     * @param patientDomain 域id
     * @return 是否正常完成标识
     */
    @Arguments({ "patientSn", "updateTime", "patientLid", "patientDomain",
            "serviceid", "orgCode" })
    public int updateAllergicHistory(BigDecimal patientSn, String updateTime,
            String patientLid, String patientDomain, String serviceid,
            String orgCode);

    /**
     * 更新患者相关各业务表patientSn
     * @param patientSn 待替换的patientSn
     * @param updateTime 更新时间
     * @param patientLid 患者本地id
     * @param patientDomain 域id
     * @return 是否正常完成标识
     */
    @Arguments({ "patientSn", "updateTime", "patientLid", "patientDomain",
            "serviceid", "orgCode" })
    public int updateRiskInformation(BigDecimal patientSn, String updateTime,
            String patientLid, String patientDomain, String serviceid,
            String orgCode);

    /**
     * 更新患者相关各业务表patientSn
     * @param patientSn 待替换的patientSn
     * @param updateTime 更新时间
     * @param patientLid 患者本地id
     * @param patientDomain 域id
     * @return 是否正常完成标识
     */
    @Arguments({ "patientSn", "updateTime", "patientLid", "patientDomain",
            "orgCode" })
    public int updateCustomizeNotification(BigDecimal patientSn,
            String updateTime, String patientLid, String patientDomain,
            String orgCode);

    /**
     * 更新患者相关各业务表patientSn
     * @param patientSn 待替换的patientSn
     * @param updateTime 更新时间
     * @param patientLid 患者本地id
     * @param patientDomain 域id
     * @return 是否正常完成标识
     */
    @Arguments({ "patientSn", "updateTime", "patientLid", "patientDomain",
            "serviceid", "orgCode" })
    public int updateMedicalVisit(BigDecimal patientSn, String updateTime,
            String patientLid, String patientDomain, String serviceid,
            String orgCode);

    /**
     * 更新患者相关各业务表patientSn
     * @param patientSn 待替换的patientSn
     * @param updateTime 更新时间
     * @param patientLid 患者本地id
     * @param patientDomain 域id
     * @return 是否正常完成标识
     */
    @Arguments({ "patientSn", "updateTime", "patientLid", "patientDomain",
            "serviceid", "orgCode" })
    public int updateExaminationOrder(BigDecimal patientSn, String updateTime,
            String patientLid, String patientDomain, String serviceid,
            String orgCode);

    /**
     * 更新患者相关各业务表patientSn
     * @param patientSn 待替换的patientSn
     * @param updateTime 更新时间
     * @param patientLid 患者本地id
     * @param patientDomain 域id
     * @return 是否正常完成标识
     */
    @Arguments({ "patientSn", "updateTime", "patientLid", "patientDomain",
            "serviceid", "orgCode" })
    public int updateExaminationResult(BigDecimal patientSn, String updateTime,
            String patientLid, String patientDomain, String serviceid,
            String orgCode);

    /**
     * 更新患者相关各业务表patientSn
     * @param patientSn 待替换的patientSn
     * @param updateTime 更新时间
     * @param patientLid 患者本地id
     * @param patientDomain 域id
     * @return 是否正常完成标识
     */
    @Arguments({ "patientSn", "updateTime", "patientLid", "patientDomain",
            "serviceid", "orgCode" })
    public int updateDiagnosis(BigDecimal patientSn, String updateTime,
            String patientLid, String patientDomain, String serviceid,
            String orgCode);

    /**
     * 更新患者相关各业务表patientSn
     * @param patientSn 待替换的patientSn
     * @param updateTime 更新时间
     * @param patientLid 患者本地id
     * @param patientDomain 域id
     * @return 是否正常完成标识
     */
    @Arguments({ "patientSn", "updateTime", "patientLid", "patientDomain",
            "serviceid", "orgCode" })
    public int updateCareOrder(BigDecimal patientSn, String updateTime,
            String patientLid, String patientDomain, String serviceid,
            String orgCode);

    /**
     * 更新患者相关各业务表patientSn
     * @param patientSn 待替换的patientSn
     * @param updateTime 更新时间
     * @param patientLid 患者本地id
     * @param patientDomain 域id
     * @return 是否正常完成标识
     */
    @Arguments({ "patientSn", "updateTime", "patientLid", "patientDomain",
            "serviceid", "orgCode" })
    public int updateNursingOperation(BigDecimal patientSn, String updateTime,
            String patientLid, String patientDomain, String serviceid,
            String orgCode);

    /**
     * 更新患者相关各业务表patientSn
     * @param patientSn 待替换的patientSn
     * @param updateTime 更新时间
     * @param patientLid 患者本地id
     * @param patientDomain 域id
     * @return 是否正常完成标识
     */
    @Arguments({ "patientSn", "updateTime", "patientLid", "patientDomain",
            "orgCode" })
    public int updateAdministrationRecord(BigDecimal patientSn,
            String updateTime, String patientLid, String patientDomain,
            String orgCode);

    /**
     * 更新患者相关各业务表patientSn
     * @param patientSn 待替换的patientSn
     * @param updateTime 更新时间
     * @param patientLid 患者本地id
     * @param patientDomain 域id
     * @return 是否正常完成标识
     */
    @Arguments({ "patientSn", "updateTime", "patientLid", "patientDomain",
            "serviceid", "orgCode" })
    public int updateMedicationOrder(BigDecimal patientSn, String updateTime,
            String patientLid, String patientDomain, String serviceid,
            String orgCode);

    /**
     * 更新患者相关各业务表patientSn
     * @param patientSn 待替换的patientSn
     * @param updateTime 更新时间
     * @param patientLid 患者本地id
     * @param patientDomain 域id
     * @return 是否正常完成标识
     */
    @Arguments({ "patientSn", "updateTime", "patientLid", "patientDomain",
            "serviceid", "orgCode" })
    public int updateTransferHistory(BigDecimal patientSn, String updateTime,
            String patientLid, String patientDomain, String serviceid,
            String orgCode);

    /**
     * 更新患者相关各业务表patientSn
     * @param patientSn 待替换的patientSn
     * @param updateTime 更新时间
     * @param patientLid 患者本地id
     * @param patientDomain 域id
     * @return 是否正常完成标识
     */
    @Arguments({ "patientSn", "updateTime", "patientLid", "patientDomain",
            "serviceid", "orgCode" })
    public int updateGeneralOrder(BigDecimal patientSn, String updateTime,
            String patientLid, String patientDomain, String serviceid,
            String orgCode);

    /**
     * 更新患者相关各业务表patientSn
     * @param patientSn 待替换的patientSn
     * @param updateTime 更新时间
     * @param patientLid 患者本地id
     * @param patientDomain 域id
     * @return 是否正常完成标识
     */
    @Arguments({ "patientSn", "updateTime", "patientLid", "patientDomain",
            "serviceid", "orgCode" })
    public int updateConsultationOrder(BigDecimal patientSn, String updateTime,
            String patientLid, String patientDomain, String serviceid,
            String orgCode);

    /**
     * 更新患者相关各业务表patientSn
     * @param patientSn 待替换的patientSn
     * @param updateTime 更新时间
     * @param patientLid 患者本地id
     * @param patientDomain 域id
     * @return 是否正常完成标识
     */
    @Arguments({ "patientSn", "updateTime", "patientLid", "patientDomain",
            "serviceid", "orgCode" })
    public int updatePrescription(BigDecimal patientSn, String updateTime,
            String patientLid, String patientDomain, String serviceid,
            String orgCode);

    /**
     * 更新患者相关各业务表patientSn
     * @param patientSn 待替换的patientSn
     * @param updateTime 更新时间
     * @param patientLid 患者本地id
     * @param patientDomain 域id
     * @return 是否正常完成标识
     */
    @Arguments({ "patientSn", "updateTime", "patientLid", "patientDomain",
            "serviceid", "orgCode" })
    public int updateTreatmentOrder(BigDecimal patientSn, String updateTime,
            String patientLid, String patientDomain, String serviceid,
            String orgCode);

    /**
     * 更新患者相关各业务表patientSn
     * @param patientSn 待替换的patientSn
     * @param updateTime 更新时间
     * @param patientLid 患者本地id
     * @param patientDomain 域id
     * @return 是否正常完成标识
     */
    @Arguments({ "patientSn", "updateTime", "patientLid", "patientDomain",
            "serviceid", "orgCode" })
    public int updateProcedureOrder(BigDecimal patientSn, String updateTime,
            String patientLid, String patientDomain, String serviceid,
            String orgCode);

    /**
     * 更新患者相关各业务表patientSn
     * @param patientSn 待替换的patientSn
     * @param updateTime 更新时间
     * @param patientLid 患者本地id
     * @param patientDomain 域id
     * @return 是否正常完成标识
     */
    @Arguments({ "patientSn", "updateTime", "patientLid", "patientDomain",
            "orgCode" })
    public int updateSurgicalProcedure(BigDecimal patientSn, String updateTime,
            String patientLid, String patientDomain, String orgCode);

    /**
     * 更新患者相关各业务表patientSn
     * @param patientSn 待替换的patientSn
     * @param updateTime 更新时间
     * @param patientLid 患者本地id
     * @param patientDomain 域id
     * @return 是否正常完成标识
     */
    @Arguments({ "patientSn", "updateTime", "patientLid", "patientDomain",
            "orgCode" })
    public int updateAnesthesia(BigDecimal patientSn, String updateTime,
            String patientLid, String patientDomain, String orgCode);

    /**
     * 更新患者相关各业务表patientSn
     * @param patientSn 待替换的patientSn
     * @param updateTime 更新时间
     * @param patientLid 患者本地id
     * @param patientDomain 域id
     * @return 是否正常完成标识
     */
    @Arguments({ "patientSn", "updateTime", "patientLid", "patientDomain",
            "serviceid", "orgCode" })
    public int updateBloodRequest(BigDecimal patientSn, String updateTime,
            String patientLid, String patientDomain, String serviceid,
            String orgCode);

    /**
     * 更新患者相关各业务表patientSn
     * @param patientSn 待替换的patientSn
     * @param updateTime 更新时间
     * @param patientLid 患者本地id
     * @param patientDomain 域id
     * @return 是否正常完成标识
     */
    @Arguments({ "patientSn", "updateTime", "patientLid", "patientDomain",
            "serviceid", "orgCode" })
    public int updateClinicalDocument(BigDecimal patientSn, String updateTime,
            String patientLid, String patientDomain, String serviceid,
            String orgCode);

    /**
     * 更新患者相关各业务表patientSn
     * @param patientSn 待替换的patientSn
     * @param updateTime 更新时间
     * @param patientLid 患者本地id
     * @param patientDomain 域id
     * @return 是否正常完成标识
     */
    @Arguments({ "patientSn", "updateTime", "patientLid", "patientDomain",
            "orgCode" })
    public int updateBillingReceipt(BigDecimal patientSn, String updateTime,
            String patientLid, String patientDomain, String orgCode);

    /**
     * 更新患者相关各业务表patientSn
     * @param patientSn 待替换的patientSn
     * @param updateTime 更新时间
     * @param patientLid 患者本地id
     * @param patientDomain 域id
     * @return 是否正常完成标识
     */
    @Arguments({ "patientSn", "updateTime", "patientLid", "patientDomain",
            "orgCode" })
    public int updateBillingItem(BigDecimal patientSn, String updateTime,
            String patientLid, String patientDomain, String orgCode);

    /**
     * 更新患者相关各业务表patientSn
     * @param patientSn 待替换的patientSn
     * @param updateTime 更新时间
     * @param patientLid 患者本地id
     * @param patientDomain 域id
     * @return 是否正常完成标识
     */
    @Arguments({ "patientSn", "updateTime", "patientLid", "patientDomain",
            "orgCode" })
    public int updateRegistrationFee(BigDecimal patientSn, String updateTime,
            String patientLid, String patientDomain, String orgCode);

    /**
     * 更新患者相关各业务表patientSn
     * @param patientSn 待替换的patientSn
     * @param updateTime 更新时间
     * @param patientLid 患者本地id
     * @param patientDomain 域id
     * @return 是否正常完成标识
     */
    @Arguments({ "patientSn", "updateTime", "patientLid", "patientDomain",
            "serviceid", "orgCode" })
    public int updateLabOrder(BigDecimal patientSn, String updateTime,
            String patientLid, String patientDomain, String serviceid,
            String orgCode);

    /**
     * 更新患者相关各业务表patientSn
     * @param patientSn 待替换的patientSn
     * @param updateTime 更新时间
     * @param patientLid 患者本地id
     * @param patientDomain 域id
     * @return 是否正常完成标识
     */
    @Arguments({ "patientSn", "updateTime", "patientLid", "patientDomain",
            "serviceid", "orgCode" })
    public int updateLabResult(BigDecimal patientSn, String updateTime,
            String patientLid, String patientDomain, String serviceid,
            String orgCode);

    // [BUG]0037319 MODIFY END

    // Author :jin_peng
    // Date : 2014/04/17 11:24
    // [BUG]0043616 ADD BEGIN
    /**
     * 动态更新患者相关各业务表
     * @param tableName 待更新业务表表名
     * @param patientSn 待替换的patientSn
     * @param updateTime 更新时间
     * @param patientLid 患者本地id
     * @param patientDomain 域id
     * @param orgCode 医疗机构代码
     * @param serviceId 消息服务id
     * @return 是否正常完成标识
     */
    @Arguments({ "tableName", "patientSn", "updateTime", "patientLid",
            "patientDomain", "serviceId", "orgCode" })
    public int updateRelatedPatientRecord(String tableName,
            BigDecimal patientSn, String updateTime, String patientLid,
            String patientDomain, String serviceId, String orgCode);

    /**
     * 动态更新患者相关各业务表 根据visit
     * @param tableName 待更新业务表表名
     * @param patientSn 待替换的patientSn
     * @param updateTime 更新时间
     * @param patientLid 患者本地id
     * @param patientDomain 域id
     * @param orgCode 医疗机构代码
     * @param serviceId 消息服务id
     * @return 是否正常完成标识
     */
    @Arguments({ "tableName", "patientSn", "updateTime", "patientLid",
            "patientDomain", "serviceId", "orgCode" })
    public int updateRelatedPatientRecordForVisit(String tableName,
            BigDecimal patientSn, String updateTime, String patientLid,
            String patientDomain, String serviceId, String orgCode);

    /**
     * 动态更新患者相关各业务表 根据patient
     * @param tableName 待更新业务表表名
     * @param patientSn 待替换的patientSn
     * @param updateTime 更新时间
     * @param patientLid 患者本地id
     * @param patientDomain 域id
     * @param orgCode 医疗机构代码
     * @param serviceId 消息服务id
     * @return 是否正常完成标识
     */
    @Arguments({ "tableName", "patientSn", "updateTime", "patientLid",
            "patientDomain", "serviceId", "orgCode" })
    public int updateRelatedPatientRecordForPatient(String tableName,
            BigDecimal patientSn, String updateTime, String patientLid,
            String patientDomain, String serviceId, String orgCode);

    /**
     * 查询相应表是否存在某字段
     * @param columnName 待查询字段名
     * @param tableNames tableName范围
     * @return 存在上述字段的相关表信息
     */
    @Arguments({ "columnName", "tableNames" })
    public List<UserTabColDto> selectExistsColumnOrNot(String columnName,
            List<String> tableNames);

    // [BUG]0043616 ADD END

}
