/**
 * 公应方法
 * 
* @version 1.0, 2012/2/4  11:00:00

 * @author  liujingyang * @since 1.0
*/
package com.yly.cdr.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Transactional;

import com.yly.cdr.dto.AuditLogDto;
import com.yly.cdr.dto.CommonDto;
import com.yly.cdr.dto.OrderStepDto;
import com.yly.cdr.dto.UserTabColDto;
import com.yly.cdr.entity.MedicalVisit;
import com.yly.cdr.entity.MedicationOrder;
import com.yly.cdr.entity.PatientCrossIndex;
import com.yly.cdr.entity.SystemAuditLog;
import com.yly.cdr.entity.TransferHistory;
import com.yly.cdr.entity.WithdrawRecord;

public interface CommonService
{
    /**
     * 查询序列号
     * @param sequenceName 序列名
     */
    @Transactional
    public BigDecimal getSequence(String sequenceName);

    /**
     * 公用实体存储方法
     * @param entity 根据业务传入entity
     */
    @Transactional
    public int save(Object entity);

    /**
     * 公用实体存储方法
     * @param entity 根据业务传入entity
     */
    @Transactional
    public int saveAll(List<?> entity);

    /**
     * 
     * @param entityList 新增List
     */
    @Transactional
    public void saveList(List<?> entityList);

    /**
     * 
     * @param entityAllList 新增List<List>
     */
    @Transactional
    public void saveAllList(List<List<Object>> entityAllList);

    /**
     * 修改部分信息
     * @param entity 根据业务传入entity
     * @param propertyNames 需要修改的字段名称
     * @return 是否保存成功标识
     */
    @Transactional
    public int updatePartially(Object entity, String... propertyNames);

    /**
     * 修改部分信息(多个对象)
     * @param entityList 根据业务传入需要更新的entityList
     * @param propertyNames 需要修改的字段名称
     */
    @Transactional
    public void updatePartiallyAll(List<?> entityList, String... propertyNames);

    /**
     * 修改全部信息
     * @param entity 根据业务传入entity
     * @return 是否保存成功标识
     */
    @Transactional
    public int update(Object entity);

    /**
     * 更新List
     * @param entityList 更新List
     */
    @Transactional
    public void updateList(List<?> entityList);

    /**
     * 更新List<List>
     * @param entityAllList 业务需要更新List<List>
     */
    @Transactional
    public void updateAllList(List<List<Object>> entityAllList);

    // $Author :chang_xuewen
 	// $Date : 2013/12/03 11:00$
 	// [BUG]0040271 MODIFY BEGIN
    /**
     * 获取就诊信息
     * 以下参数顺序必须固定
     * @param patientDomain 域代码
     * @param patientLid   患者本地ID  
     * @param visitTimes   就诊次数
     * @param orgCode	      医疗机构代码
     * @param deleteFlag   删除标志
     * @return 实体对象
     */
    @Transactional
    public MedicalVisit mediVisit(Object ... args);
    // [BUG]0040271 MODIFY END

    /**
     * 通过申请单号获取就诊
     * @param clazz 申请单对应的医嘱类
     * @param patientLid 患者Id
     * @param requestNo 申请单编号
     */
    @Transactional
    public MedicalVisit mediVisitByRequestNo(Class<?> clazz, String patientLid, String requestNo);
    
    
    /**
     * 获取父医嘱信息
     * @param patientDomain 域代码
     * @param patientLid   患者本地ID  
     * @param parentOrderLid   父医嘱号
     * @return 实体对象
     */
    @Transactional
    public MedicationOrder checkParentOrder(String patientDomain,
            String patientLid, String parentOrderLid, String orgCode);
    
    /**
     * 获取父医嘱信息
     * @param patientDomain 域代码
     * @param patientLid   患者本地ID  
     * @param parentOrderLid   父医嘱号
     * @param visitSn 就诊内部序列号
     * @return 实体对象
     */
    @Transactional
    public MedicationOrder checkParentOrder(String patientDomain,
            String patientLid, String parentOrderLid, String orgCode, BigDecimal visitSn);

    /**
     * 获取转科转床信息
     * @param patientDomain 域代码
     * @param patientLid   患者本地ID  
     * @param orderLid   医嘱本地ID
     * @return 转科转床信息实体对象
     */
    @Transactional
    public TransferHistory getTransferHistory(String patientDomain,
            String patientLid, String orderLid, String orgCode, String dept, String wards, String bedNo);

    /**
     * 根据消息状态新增或更新转科转床记录及相应就诊记录
     * @param status 消息状态
     * @param transferHistory 转科转床对象
     * @param medicalVisit 相关就诊对象
     * @param fieldsName 需更新的属性名
     */
    @Transactional
    public void saveOrUpdateTransferAndMedicalVisit(String status,
            TransferHistory transferHistory, MedicalVisit medicalVisit,
            String... fieldsName);

    /**
     * 获取实体信息(根据多个查询条件)
     * @param map 查询条件
     * @return 实体对象集合
     */
    @Transactional
    public List<Object> selectByCondition(Class className,
            Map<String, Object> map);

    /**
     * 获取实体信息(根据该对象主键)
     * @param className 需要查询的对象class
     * @param primaryId 对象主键
     * @return 实体对象
     */
    @Transactional
    public Object selectById(Class<?> className, Object... primaryId);

    // $Author :jia_yanqing
    // $Date : 2012/5/24 11:05$
    // [BUG]0006657 DELETE BEGIN

    /**
     * 患者本地ID取得
     * @param patientDomain 域代码
     * @param outpatientLid 门诊患者ID
     * @param inpatientLid 住院患者ID
     * @param imageNo 影像号
     * @param physicalexamNo 体检号
     * @return 患者本地ID
     */
//    public String getPatientLid(String patientDomain, String outpatientLid,
//            String inpatientLid, String imageNo, String physicalExamNo);

    // [BUG]0006657 DELETE END
    /**
     * 删除信息
     * @param entity 根据业务传入entity
     * @return 是否保存成功标识
     */
    @Transactional
    public int delete(Object entity);

    /**
     * 删除信息(多个)
     * @param entityList 根据业务传入entity集合
     */
    @Transactional
    public void deleteList(List<?> entityList);

    /**
     * 检验或检查报告召回操作
     * @param entity 检验或检查报告对象
     * @param withdrawRecord 检验或检查报告召回对象
     */
    @Transactional
    public void withdrawLabResult(Object entity, WithdrawRecord withdrawRecord);

    /**
     * 根据传入的医嘱类型获取相应医嘱的class
     * @param orderType 医嘱撤销或停止消息中的医嘱类型
     * @return 相应医嘱的对象class
     */
    public Class<?> getOrderClass(String orderType);

    // $Author :chang_xuewen
    // $Date : 2013/08/26 18:21$
    // [BUG]0036600 ADD BEGIN
    public int getOperation(BigDecimal orderSn, String orderStatus,
            String operDate);

    // [BUG]0036600 ADD END

    // $Author :jin_peng
    // $Date : 2012/08/29 18:21$
    // [BUG]0009257 MODIFY BEGIN
    /**
     * 根据传入的医嘱状态判断操作记录是存入护理操作表中还是召回记录表中,同时更新相应医嘱的医嘱状态及相应报告的召回状态
     * @param ordersList 需要更新状态的医嘱对象集合
     * @param nurseOperationList 需要保存的操作记录对象集合
     * @param withdrawRecordList 需要保存的召回记录对象集合
     * @param reportList 需要更新的检验或检查报告对象集合 
     * @param confirmMessageFlag 判断是否医嘱确认消息（检查检验更新医嘱确认人，确认时间信息）
     */
    @Transactional
    public void updateAndSaveOrderStatusOperation(List<Object> ordersList,
            List<Object> nurseOperationList, List<Object> withdrawRecordList,
            List<Object> reportList,List<Object> delReportList, boolean confirmMessageFlag);

    // [BUG]0009257 MODIFY END

    
    // $Author :jin_peng
    // $Date : 2012/11/23 13:59$
    // [BUG]0011864 MODIFY BEGIN
    // $Author :jin_peng
    // $Date : 2012/10/19 17:47$
    // [BUG]0010593 MODIFY BEGIN
    // $Author:wei_peng
    // $Date:2012/9/25 16:09
    // $[BUG]0010017 ADD BEGIN
    /**
     * 通过患者域ID和医嘱号或申请单号查找检查医嘱或手术医嘱表，获取患者ID
     * @param patientDomain 患者域ID
     * @param orderLid 医嘱号/申请单号
     * @return 患者ID
     */
    @Transactional
    public String getPatientLid(String patientDomain, String orderLid, String orgCode);

    // $[BUG]0010017 ADD END
    // [BUG]0010593 MODIFY END
    // [BUG]0011864 MODIFY END

    // $Author:wei_peng
    // $Date:2013/01/22 16:13
    // [BUG]0013440 ADD BEGIN
    /**
     * 通过域ID和就诊号查找患者交叉索引记录
     * @param patientDomain 患者域ID
     * @param medicalVisitNo 就诊号
     * @return 患者交叉索引记录
     */
    @Transactional
    public PatientCrossIndex getPatientRecordFromPCI(String patientDomain,
            String medicalVisitNo, String orgCode, String visitTypeCode);
    
    /**
     * 通过域ID和就诊号查找患者交叉索引记录
     * @param patientDomain 患者域ID
     * @param patientLid 患者Lid
     * @return 患者交叉索引记录
     */
    @Transactional
    public PatientCrossIndex getPatientRecordFromPCIByLid(String patientDomain,
            String patientLid, String orgCode);

    // [BUG]0013440 ADD END

    // Author :jia_yanqing
    // Date : 2012/09/12 17:00
    // [BUG]0037319 MODIFY BEGIN
    /**
     * 更新患者相关各业务表patientSn
     * @param patientSn 待替换的patientSn
     * @param updateTime 更新时间
     * @param patientLid 患者本地id
     * @param patientDomain 域id
     */
    @Transactional
    public void updateEmpiRelationship(BigDecimal patientSn, String updateTime,
            String patientLid, String patientDomain, String serviceid,
            String orgCode, List<String> userTablesName);

    // [BUG]0037319 MODIFY END

    @Transactional
    public List<String> selectImageSns(String reportSn);

    @Transactional
    public boolean checkFlag(Long times, Long totaltime, Long skipTimes2,
            Long totaltimes);

    @Transactional
    public OrderStepDto getOrderStep(String orderStatus, Object order)
            throws Exception;

    @Transactional
    public List<OrderStepDto> getOrderStepDtos(String orderSn);

    @Transactional
    public OrderStepDto getChargeOrderStep(String orderSn);

    @Transactional
    public CommonDto getMaxNursingOperDate(BigDecimal visitSn,
            BigDecimal orderSn);

    // $Author:jin_peng
    // $Date:2013/09/23 15:50
    // [BUG]0037540 ADD BEGIN
    /**
     * 获取敏感信息审计相关内容
     * @param accountLogSn 用户登陆系统日志内部序列号
     * @param businessDataNo 业务数据标识号
     * @param operationTime 操作时间
     * @return 敏感信息审计相关内容
     */
    @Transactional
    public List<AuditLogDto> selectAuditLog(BigDecimal accountLogSn,
            String businessDataNo, String operationTime);

    /**
     * 将构造完成的消息内容及是否发送成功标识更新到相应记录中
     * @param systemAuditLog 敏感信息审计对象
     * @return
     */
    @Transactional
    public void updateAuditLog(SystemAuditLog systemAuditLog);

    // [BUG]0037540 ADD END
    
    @Transactional
    public int insert(Object entity);

    // $Author:jin_peng
    // $Date:2014/04/17 13:14
    // [BUG]0043616 ADD BEGIN
    /**
     * 查询相应表是否存在某字段
     * @param columnName 待查询字段名
     * @param tableNames tableName范围
     * @return 存在上述字段的相关表信息
     */
    @Transactional
    public List<UserTabColDto> selectExistsColumnOrNot(String columnName,
            List<String> tableNames);
    
    // [BUG]0043616 ADD END
}
