/**
 * 公应方法
 * 
 * @version 1.0, 2012/2/4  11:00:00

 * @author  liujingyang * @since 1.0
 */
package com.yly.cdr.service.impl;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.founder.fasf.core.util.daohelper.entity.EntityDao;
import com.yly.cdr.cache.DictionaryCache;
import com.yly.cdr.core.Constants;
import com.yly.cdr.dao.CommonDao;
import com.yly.cdr.dao.empiRelationshipBussiness.EmpiRelationshipDao;
import com.yly.cdr.dto.AuditLogDto;
import com.yly.cdr.dto.CommonDto;
import com.yly.cdr.dto.OrderStepDto;
import com.yly.cdr.dto.UserTabColDto;
import com.yly.cdr.entity.BillingItem;
import com.yly.cdr.entity.BloodRequest;
import com.yly.cdr.entity.CareOrder;
import com.yly.cdr.entity.ConsultationOrder;
import com.yly.cdr.entity.ExaminationOrder;
import com.yly.cdr.entity.GeneralOrder;
import com.yly.cdr.entity.LabOrder;
import com.yly.cdr.entity.MedicalImaging;
import com.yly.cdr.entity.MedicalVisit;
import com.yly.cdr.entity.MedicationOrder;
import com.yly.cdr.entity.NursingOperation;
import com.yly.cdr.entity.PatientCrossIndex;
import com.yly.cdr.entity.ProcedureOrder;
import com.yly.cdr.entity.SystemAuditLog;
import com.yly.cdr.entity.TransferHistory;
import com.yly.cdr.entity.TreatmentOrder;
import com.yly.cdr.entity.WithdrawRecord;
import com.yly.cdr.service.CommonService;
import com.yly.cdr.util.DateUtils;
import com.yly.cdr.util.StringUtils;

@Component
public class CommonServiceImpl implements CommonService
{
    @Autowired
    private EntityDao entityDao;

    @Autowired
    private CommonDao commonDao;

    @Autowired
    private EmpiRelationshipDao empiDao;

    public static List<UserTabColDto> userTabColDto;
    
    // 就诊开关开启标识
    private static final String TURN_ON_FLAG = "1";

    /**
     * 查询序列号
     * 
     * @param sequenceName 序列名
     */
    @Transactional
    public BigDecimal getSequence(String sequenceName)
    {
        return commonDao.getSequence(sequenceName);
    }

    /**
     * 公用实体存储方法
     * 
     * @param baseDto
     */
    @Transactional
    public int save(Object entity)
    {
        return entityDao.insert(entity);
    }

    /**
     * 公用实体存储方法
     * 
     * @param baseDto
     */
    @Transactional
    public int saveAll(List<?> entities)
    {
        return entityDao.insertAll(entities.toArray());
    }

    /**
     * 插入数据库
     * @param entityList 新增List
     */
    @Transactional
    public void saveList(List<?> entityList)
    {
        if (entityList != null && !entityList.isEmpty())
        {
            for (Object entity : entityList)
            {
                save(entity);
            }
        }
    }

    /**
     * 插入数据库
     * @param entityAllList 新增List<List>
     */
    @Transactional
    public void saveAllList(List<List<Object>> entityAllList)
    {
        if (entityAllList != null && !entityAllList.isEmpty())
        {
            for (List<Object> entityList : entityAllList)
            {
                saveList(entityList);
            }
        }
    }

    /**
     * 修改部分信息
     * 
     * @param entity
     *            根据业务传入entity
     * @param propertyNames
     *            需要修改的字段名称
     * @return 是否保存成功标识
     */
    @Transactional
    public int updatePartially(Object entity, String... propertyNames)
    {
        return entityDao.updatePartially(entity, propertyNames);
    }

    /**
     * 修改部分信息(多个对象)
     * @param entityList 根据业务传入需要更新的entityList
     * @param propertyNames 需要修改的字段名称
     */
    @Transactional
    public void updatePartiallyAll(List<?> entityList, String... propertyNames)
    {
        if (entityList != null && !entityList.isEmpty())
        {
            for (Object entity : entityList)
            {
                updatePartially(entity, propertyNames);
            }
        }
    }

    /**
     * 修改全部信息
     * 
     * @param entity
     *            根据业务传入entity
     * @return 是否保存成功标识
     */
    @Transactional
    public int update(Object entity)
    {
        return entityDao.update(entity);
    }

    /**
     * @param entityList 更新List
     */
    @Transactional
    public void updateList(List<?> entityList)
    {
        if (entityList != null && !entityList.isEmpty())
        {
            for (Object entity : entityList)
            {
                update(entity);
            }
        }
    }

    /**
     * @param entityList 更新List
     */
    @Transactional
    public void updateAll(List<?> entityList)
    {
        if (entityList != null && !entityList.isEmpty())
        {
            for (Object entity : entityList)
            {
                update(entity);
            }
        }
    }

    /**
     * @param entityListALL 更新List<List>
     */
    @Transactional
    public void updateAllList(List<List<Object>> entityAllList)
    {
        if (entityAllList != null && !entityAllList.isEmpty())
        {
            for (List<?> entityList : entityAllList)
            {
                updateAll(entityList);
            }
        }
    }

    /**
     * 删除全部信息
     * 
     * @param entity
     *            根据业务传入entity
     * @return 是否保存成功标识
     */
    @Transactional
    public int delete(Object entity)
    {
        return entityDao.delete(entity);
    }

    /**
     * 删除信息(多个)
     * @param entityList 根据业务传入entity集合
     */
    @Transactional
    public void deleteList(List<?> entityList)
    {
        if (entityList != null && !entityList.isEmpty())
        {
            for (Object entity : entityList)
            {
                delete(entity);
            }
        }
    }

    /**
     * 获取实体信息(根据多个查询条件)
     * 
     * @param map
     *            查询条件
     * @return 实体对象集合
     */
    @Transactional
    public List<Object> selectByCondition(Class className,
            Map<String, Object> map)
    {
        return entityDao.selectByCondition(className, map);
    }

    // $Author :jia_yanqing
    // $Date : 2012/5/24 11:05$
    // [BUG]0006657 DELETE BEGIN

    /**
     * 患者本地ID取得
     * 
     * @param patientDomain
     *            域代码
     * @param outpatientLid
     *            门诊患者ID
     * @param inpatientLid
     *            住院患者ID
     * @param imageNo
     *            影像号
     * @param physicalexamNo
     *            体检号
     * @return 患者本地ID
     */
//    @Transactional
//    public String getPatientLid(String patientDomain, String outpatientLid,
//            String inpatientLid, String imageNo, String physicalExamNo)
//    {
//        String patientLid = null;
//        if (patientDomain != null)
//        {
//            if (Constants.PATIENT_DOMAIN_OUTPATIENT.equals(patientDomain))
//            {
//                patientLid = outpatientLid;
//            }
//            if (Constants.PATIENT_DOMAIN_INPATIENT.equals(patientDomain))
//            {
//                patientLid = inpatientLid;
//            }
//            if (Constants.PATIENT_DOMAIN_IMAGE.equals(patientDomain))
//            {
//                patientLid = imageNo;
//            }
//            if (Constants.PATIENT_DOMAIN_PHYSICAL_EXAM.equals(patientDomain))
//            {
//                patientLid = physicalExamNo;
//            }
//        }
//        return patientLid;
//    }

    // [BUG]0006657 DELETE END

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
    public MedicalVisit mediVisit(Object ... args)
    {
        MedicalVisit MV = null;
        Map<String, Object> condition = new HashMap<String, Object>();
        if(TURN_ON_FLAG.equals(Constants.MEDICAL_VISIT_CONFIM_LOGIC_TYPE)){
        	if (args[2]==null || StringUtils.isEmpty((String)args[2])){
        		condition.put("visitTimes",null);
        	}else{
        		condition.put("visitTimes",Integer.parseInt((String)args[2]));
        	}
            
        }else{
        	condition.put("visitOrdNo", (String) args[4]);
        	condition.put("visitTypeCode", (String) args[5]);
        }
        condition.put("patientDomain",(String) args[0]);
        condition.put("patientLid",(String) args[1]);
        condition.put("orgCode",(String) args[3]);
        condition.put("deleteFlag", Constants.NO_DELETE_FLAG);
        List visitResult = new ArrayList<MedicalVisit>();
        visitResult = entityDao.selectByCondition(MedicalVisit.class, condition);
        if (visitResult != null && !visitResult.isEmpty())
        {
            MV = (MedicalVisit) visitResult.get(0);
        }
        return MV;
    }
    // [BUG]0040271 MODIFY END
    /*
     * $Author: yu_yzh
     * $Date: 2015/7/28 11:03$
     * 添加通过检查申请单号查找就诊方法
     * ADD BEGIN
     * */
    public MedicalVisit mediVisitByRequestNo(Class<?> clazz, String patientLid, String requestNo){
    	MedicalVisit mv = null;
		if(requestNo != null){
			Map<String, Object> condition = new HashMap<String, Object>();
			condition.put("patientLid", patientLid);
			condition.put("requestNo", requestNo);
			List<?> list = selectByCondition(clazz, condition);
			Object obj = null;
			if(list != null && !list.isEmpty()){
				obj = list.get(0);
			}
			if(obj != null){
				try{
					Field field = clazz.getDeclaredField("visitSn");
					field.setAccessible(true);
					String visitSn = field.get(obj).toString();
					condition.clear();
					condition.put("visitSn", visitSn);
					list = selectByCondition(MedicalVisit.class, condition);
					if(list != null && !list.isEmpty()){
						mv = (MedicalVisit) list.get(0);
					}
				} catch (Exception e){
					e.printStackTrace();
				}
			}
		}
		return mv;
    }
    /*
     * ADD END
     * */
    
    // $Author :chang_xuewen
 	// $Date : 2013/12/03 11:00$
 	// [BUG]0040271 MODIFY BEGIN
    /**
     * 获取父医嘱信息
     * @param patientDomain 域代码
     * @param patientLid   患者本地ID  
     * @param parentOrderLid   父医嘱号
     * @param orgCode	      医疗机构代码
     * @return 实体对象
     */
    @Transactional
    public MedicationOrder checkParentOrder(String patientDomain,
            String patientLid, String parentOrderLid, String orgCode)
    {
        MedicationOrder MO = null;
        Map<String, Object> condition = new HashMap<String, Object>();
        condition.put("patientDomain", patientDomain);
        condition.put("patientLid", patientLid);
        condition.put("orderLid", parentOrderLid);
        condition.put("orgCode", orgCode);
        condition.put("deleteFlag", Constants.NO_DELETE_FLAG);
        List medicationOrder = new ArrayList<MedicationOrder>();
        medicationOrder = entityDao.selectByCondition(MedicationOrder.class,
                condition);
        if (medicationOrder != null && !medicationOrder.isEmpty())
        {
            MO = (MedicationOrder) medicationOrder.get(0);
        }
        return MO;
    }
    /**
     * 获取父医嘱信息
     * @param patientDomain 域代码
     * @param patientLid   患者本地ID  
     * @param parentOrderLid   父医嘱号
     * @param orgCode	      医疗机构代码
     * @return 实体对象
     */
    @Transactional
    public MedicationOrder checkParentOrder(String patientDomain,
            String patientLid, String parentOrderLid, String orgCode, BigDecimal visitSn)
    {
        MedicationOrder MO = null;
        Map<String, Object> condition = new HashMap<String, Object>();
        condition.put("patientDomain", patientDomain);
        condition.put("patientLid", patientLid);
        condition.put("orderLid", parentOrderLid);
        condition.put("orgCode", orgCode);
        condition.put("visitSn", visitSn);
        condition.put("deleteFlag", Constants.NO_DELETE_FLAG);
        List medicationOrder = new ArrayList<MedicationOrder>();
        medicationOrder = entityDao.selectByCondition(MedicationOrder.class,
                condition);
        if (medicationOrder != null && !medicationOrder.isEmpty())
        {
            MO = (MedicationOrder) medicationOrder.get(0);
        }
        return MO;
    }
    // [BUG]0040271 MODIFY END
    /**
     * 检验或检查报告召回操作
     * @param entity 检验或检查报告对象
     * @param wthdrawRecord 检验或检查报告召回对象
     */
    @Transactional
    public void withdrawLabResult(Object entity, WithdrawRecord withdrawRecord)
    {
        // 更新检验或检查报告中的召回状态
        this.updatePartially(entity, "withdrawFlag", "updateTime", "updateby");

        // 添加召回记录
        this.save(withdrawRecord);
    }

    /**
     * 获取转科转床信息
     * @param patientDomain 域代码
     * @param patientLid   患者本地ID  
     * @param orderNo   医嘱本地ID
     * @return 转科转床信息实体对象
     */
    @Transactional
    public TransferHistory getTransferHistory(String patientDomain,
            String patientLid, String orderLid, String orgCode, 
            String dept, String wards, String bedNo)
    {
        TransferHistory transferHistory = null;

        Map<String, Object> condition = new HashMap<String, Object>();
        condition.put("patientDomain", patientDomain);
        condition.put("patientLid", patientLid);
        condition.put("orderLid", orderLid);
        condition.put("orgCode", orgCode);
        condition.put("fromDeptId", dept);
        condition.put("fromWardId", wards);
        condition.put("fromBed", bedNo);
        condition.put("deleteFlag", Constants.NO_DELETE_FLAG);

        List<Object> transferHistoryList = entityDao.selectByCondition(
                TransferHistory.class, condition);

        if (transferHistoryList != null && !transferHistoryList.isEmpty())
        {
            transferHistory = (TransferHistory) transferHistoryList.get(0);
        }

        return transferHistory;
    }

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
            String... fieldsName)
    {
        // 根据消息状态判断新增或更新转科转床记录
        if (Constants.NEW_MESSAGE_FLAG.equals(status))
        {
            this.save(transferHistory);
        }
        else
        {
            this.update(transferHistory);
        }

        // 更新此消息所对应的就诊相关属性
        this.updatePartially(medicalVisit, fieldsName);
    }

    /**
     * 根据传入的医嘱类型获取相应医嘱的class
     * @param orderType 医嘱撤销或停止消息中的医嘱类型
     * @return 相应医嘱的对象class
     */
    @Transactional
    public Class<?> getOrderClass(String orderType)
    {
        Class<?> entityClass = null;

        // 对应护理医嘱
        if (Constants.CARE_ORDER_TABLE_NAME.equals(Constants.ORDER_TYPE_MAP.get(orderType)))
        {
            entityClass = CareOrder.class;
        }
        // 对应用药医嘱
        else if (Constants.MEDICATION_ORDER_TABLE_NAME.equals(Constants.ORDER_TYPE_MAP.get(orderType)))
        {
            entityClass = MedicationOrder.class;
        }
        // 对应诊疗处置医嘱
        else if (Constants.TREATMENT_ORDER_TABLE_NAME.equals(Constants.ORDER_TYPE_MAP.get(orderType)))
        {
            entityClass = TreatmentOrder.class;
        }
        // 对应会诊医嘱
        else if (Constants.CONSULTATION_ORDER_TABLE_NAME.equals(Constants.ORDER_TYPE_MAP.get(orderType)))
        {
            entityClass = ConsultationOrder.class;
        }
        // 对应手术医嘱
        else if (Constants.PROCEDURE_ORDER_TABLE_NAME.equals(Constants.ORDER_TYPE_MAP.get(orderType)))
        {
            entityClass = ProcedureOrder.class;
        }
        // 对应检查医嘱
        else if (Constants.EXAMINATION_ORDER_TABLE_NAME.equals(Constants.ORDER_TYPE_MAP.get(orderType)))
        {
            entityClass = ExaminationOrder.class;
        }
        // 对应检验医嘱
        else if (Constants.LAB_ORDER_TABLE_NAME.equals(Constants.ORDER_TYPE_MAP.get(orderType)))
        {
            entityClass = LabOrder.class;
        }
        // 对应其他医嘱
        else if (Constants.GENERAL_ORDER_TABLE_NAME.equals(Constants.ORDER_TYPE_MAP.get(orderType)))
        {
            entityClass = GeneralOrder.class;
        }
        else if (Constants.BLOOD_REQUEST_OUTPATIENT_TABLE_NAME.equals(Constants.ORDER_TYPE_MAP.get(orderType)))
        {
            entityClass = BloodRequest.class;
        }

        return entityClass;
    }

    /**
     * 根据传入的医嘱状态判断操作记录是存入护理操作表中还是召回记录表中,同时更新相应医嘱的医嘱状态及相应报告的召回状态
     * @param ordersList 需要更新状态的医嘱对象集合
     * @param nurseOperationList 需要保存的操作记录对象集合
     * @param withdrawRecordList 需要保存的召回记录对象集合
     * @param reportList 需要更新的检验或检查报告对象集合
     */
    @Transactional
    public void updateAndSaveOrderStatusOperation(List<Object> ordersList,
            List<Object> nurseOperationList, List<Object> withdrawRecordList,
            List<Object> reportList,List<Object> delReportList, boolean confirmMessageFlag)
    {
        // 更新医嘱相应医嘱的医嘱状态
        for (Object obj : ordersList)
        {
        	List<String> attributeList = new ArrayList<String>();
        	attributeList.add("updateTime");
        	attributeList.add("updateby");
        	attributeList.add("orderStatusName");
        	if (GeneralOrder.class.equals(obj.getClass())
                    || ConsultationOrder.class.equals(obj.getClass())
                    || BloodRequest.class.equals(obj.getClass())){
        		attributeList.add("orderStatusCode");
        	} else {
        		attributeList.add("orderStatus");
        	}
        	if(confirmMessageFlag){
        		if(LabOrder.class.equals(obj.getClass())
    					|| ExaminationOrder.class.equals(obj.getClass())
    					|| ProcedureOrder.class.equals(obj.getClass())){
        			attributeList.add("confirmPerson");
            		attributeList.add("confirmPersonName");
            		attributeList.add("confirmTime");
    			} else if(MedicationOrder.class.equals(obj.getClass())
    					|| TreatmentOrder.class.equals(obj.getClass())
    					|| CareOrder.class.equals(obj.getClass())){
    				attributeList.add("nurseConfirmPerson");
            		attributeList.add("nurseConfirmPersonName");
            		attributeList.add("nurseConfirmTime");
    			} else if(GeneralOrder.class.equals(obj.getClass())){
    				attributeList.add("nurseConfirmPersonId");
            		attributeList.add("nurseConfirmPersonName");
            		attributeList.add("nurseConfirmTime");
    			}
        		
        	}       	
        	updatePartially(obj, attributeList.toArray(new String[attributeList.size()]));
            // $Author :jin_peng
            // $Date : 2012/09/26 15:01$
            // [BUG]0009808 MODIFY BEGIN
            // 添加用血类医嘱情况
/*            if (GeneralOrder.class.equals(obj.getClass())
                || ConsultationOrder.class.equals(obj.getClass())
                || BloodRequest.class.equals(obj.getClass()))
            {
                updatePartially(obj, "orderStatusCode", "orderStatusName",
                        "updateTime", "updateby");
            }
            // [BUG]0009808 MODIFY END
            else if(confirmMessageFlag && 
            		(LabOrder.class.equals(obj.getClass()) 
            				|| ExaminationOrder.class.equals(obj.getClass()))){
            	updatePartially(obj, "orderStatus", "orderStatusName",
                        "updateTime", "updateby",
                        "confirmPerson", "confirmPersonName", "confirmTime");
            }
            else
            {
                updatePartially(obj, "orderStatus", "orderStatusName",
                        "updateTime", "updateby");
            }*/
        }

        // 添加医嘱操作记录
        if (nurseOperationList != null && !nurseOperationList.isEmpty())
        {
            saveList(nurseOperationList);
        }

        // 添加检查检验医嘱召回状态时操作记录
        if (withdrawRecordList != null && !withdrawRecordList.isEmpty())
        {
            saveList(withdrawRecordList);
        }

        // 更新检查检验报告中召回状态
        if (reportList != null && !reportList.isEmpty())
        {
            updatePartiallyAll(reportList, "withdrawFlag", "updateTime",
                    "updateby");
        }
        
        // $Author :tong_meng$
        // $Date : 2013/10/28 14:00$
        // $[BUG]0038527 MODIFY BEGIN$
        // $Note : 增加取消医嘱，逻辑删除对应报告的情况$
        if (delReportList != null && !delReportList.isEmpty())
        {
            updatePartiallyAll(delReportList, "deleteby", "deleteTime",
                    "deleteFlag", "updateTime");
        }
        // $[BUG]0038527 MODIFY END$
    }

    
    /**
     * 获取实体信息(根据该对象主键)
     * @param className 需要查询的对象class
     * @param primaryId 对象主键
     * @return 实体对象
     */
    @Transactional
    public Object selectById(Class<?> className, Object... primaryId)
    {
        return entityDao.selectById(className, primaryId);
    }

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
    public String getPatientLid(String patientDomain, String orderLid,String orgCode)
    {
        Map<String, Object> condition = new HashMap<String, Object>();
        condition.put("patientDomain", patientDomain);
        condition.put("orgCode", orgCode);
        condition.put("deleteFlag", Constants.NO_DELETE_FLAG);

//        if (Constants.PATIENT_DOMAIN_INPATIENT.equals(patientDomain))
//        {
//            condition.put("orderLid", orderLid);
//        }
//        else if (Constants.PATIENT_DOMAIN_OUTPATIENT.equals(patientDomain))
//        {
//            condition.put("requestNo", orderLid);
//        }
        condition.put("orderLid", orderLid);
        List<Object> examOrderList = entityDao.selectByCondition(
                ExaminationOrder.class, condition);

        if (examOrderList != null && !examOrderList.isEmpty())
        {
            ExaminationOrder e = (ExaminationOrder) examOrderList.get(0);

            return e.getPatientLid();
        }
        else
        {
            List<Object> procOrderList = entityDao.selectByCondition(
                    ProcedureOrder.class, condition);

            if (procOrderList != null && !procOrderList.isEmpty())
            {
                ProcedureOrder p = (ProcedureOrder) procOrderList.get(0);

                return p.getPatientLid();
            }
        }

        return null;
    }

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
            String medicalVisitNo, String orgCode, String visitTypeCode)
    {
        Map<String, Object> condition = new HashMap<String, Object>();
        condition.put("deleteFlag", Constants.NO_DELETE_FLAG);
        condition.put("patientDomain", patientDomain);
        condition.put("orgCode", orgCode);
        if (Constants.lstDomainInPatient().contains(patientDomain)
        		&& Constants.lstVisitTypeInPatient().contains(visitTypeCode))
        {
            condition.put("inpatientNo", medicalVisitNo);
        }
        else if (Constants.lstDomainOutPatient().contains(patientDomain)
        		&& Constants.lstVisitTypeOutPatient().contains(visitTypeCode))
        {
            condition.put("outpatientNo", medicalVisitNo);
        }
        List<Object> results = entityDao.selectByCondition(
                PatientCrossIndex.class, condition);
        if (results != null && results.size() > 0)
        {
            return (PatientCrossIndex) results.get(0);
        }
        return null;
    }
    
    /**
     * 通过域ID和就诊号查找患者交叉索引记录
     * @param patientDomain 患者域ID
     * @param patientLid 患者Lid
     * @return 患者交叉索引记录
     */
    @Transactional
    public PatientCrossIndex getPatientRecordFromPCIByLid(String patientDomain,
            String patientLid, String orgCode)
    {
        Map<String, Object> condition = new HashMap<String, Object>();
        condition.put("deleteFlag", Constants.NO_DELETE_FLAG);
        condition.put("patientDomain", patientDomain);
        condition.put("patientLid", patientLid);
        condition.put("orgCode", orgCode);      
 
        List<Object> results = entityDao.selectByCondition(
                PatientCrossIndex.class, condition);
        if (results != null && results.size() > 0)
        {
            return (PatientCrossIndex) results.get(0);
        }
        return null;
    }
    

    // [BUG]0013440 ADD END

    // $Author:jin_peng
    // $Date:2014/04/18 13:20
    // [BUG]0043616 ADD BEGIN
    /**
     * 通过ruleStr是否在ruleList中存在来判断获取-
     * 相应targetStr的值（存在则返回该字符串，不存在则返回null）
     * @param targetStr 待处理字符串
     * @param ruleList 用来判断是否存在的目标集合
     * @param ruleStr 用来判断是否存在的目标字符串
     * @return
     */
    private String getTargetStrOrNullByContainsRule(String targetStr,
            List<String> ruleList, String ruleStr)
    {
        if (StringUtils.contains(ruleList, ruleStr))
        {
            return targetStr;
        }

        return null;
    }

    // [BUG]0043616 ADD END
    
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
            String orgCode, List<String> userTablesName)
    {
     // $Author:jin_peng
        // $Date:2014/04/17 13:14
        // [BUG]0043616 ADD BEGIN
        // 根据patientLid patientDomain orgCode更新相关业务表
        for (String tabName : Constants.UPDATE_RELEVANCE_TBALE_NAME_LIST)
        {
            empiDao.updateRelatedPatientRecord(
                    tabName,
                    patientSn,
                    updateTime,
                    patientLid,
                    patientDomain,
                    getTargetStrOrNullByContainsRule(serviceid, userTablesName,
                            tabName), orgCode);
        }

        // 根据visitSn更新相关业务表
        for (String tabName : Constants.UPDATE_RELEVANCE_TBALE_NAME_FOR_VISIT_LIST)
        {
            empiDao.updateRelatedPatientRecordForVisit(
                    tabName,
                    patientSn,
                    updateTime,
                    patientLid,
                    patientDomain,
                    getTargetStrOrNullByContainsRule(serviceid, userTablesName,
                            tabName), orgCode);
        }

        // 根据patientSn更新相关业务表
        for (String tabName : Constants.UPDATE_RELEVANCE_TBALE_NAME_FOR_PATIENT_LIST)
        {
            empiDao.updateRelatedPatientRecordForPatient(
                    tabName,
                    patientSn,
                    updateTime,
                    patientLid,
                    patientDomain,
                    getTargetStrOrNullByContainsRule(serviceid, userTablesName,
                            tabName), orgCode);
        }

        // [BUG]0043616 ADD END

        /*
         * empiDao.updateAllergicHistory(patientSn, updateTime, patientLid,
         * patientDomain, serviceid, orgCode);
         * 
         * empiDao.updateRiskInformation(patientSn, updateTime, patientLid,
         * patientDomain, serviceid, orgCode);
         * 
         * empiDao.updateCustomizeNotification(patientSn, updateTime,
         * patientLid, patientDomain, orgCode);
         * 
         * empiDao.updateMedicalVisit(patientSn, updateTime, patientLid,
         * patientDomain, serviceid, orgCode);
         * 
         * empiDao.updateExaminationOrder(patientSn, updateTime, patientLid,
         * patientDomain, serviceid, orgCode);
         * 
         * empiDao.updateExaminationResult(patientSn, updateTime, patientLid,
         * patientDomain, serviceid, orgCode);
         * 
         * empiDao.updateDiagnosis(patientSn, updateTime, patientLid,
         * patientDomain, serviceid, orgCode);
         * 
         * empiDao.updateCareOrder(patientSn, updateTime, patientLid,
         * patientDomain, serviceid, orgCode);
         * 
         * empiDao.updateNursingOperation(patientSn, updateTime, patientLid,
         * patientDomain, serviceid, orgCode);
         * 
         * empiDao.updateAdministrationRecord(patientSn, updateTime, patientLid,
         * patientDomain, orgCode);
         * 
         * empiDao.updateMedicationOrder(patientSn, updateTime, patientLid,
         * patientDomain, serviceid, orgCode);
         * 
         * empiDao.updateTransferHistory(patientSn, updateTime, patientLid,
         * patientDomain, serviceid, orgCode);
         * 
         * empiDao.updateGeneralOrder(patientSn, updateTime, patientLid,
         * patientDomain, serviceid, orgCode);
         * 
         * empiDao.updateConsultationOrder(patientSn, updateTime, patientLid,
         * patientDomain, serviceid, orgCode);
         * 
         * empiDao.updatePrescription(patientSn, updateTime, patientLid,
         * patientDomain, serviceid, orgCode);
         * 
         * empiDao.updateTreatmentOrder(patientSn, updateTime, patientLid,
         * patientDomain, serviceid, orgCode);
         * 
         * empiDao.updateProcedureOrder(patientSn, updateTime, patientLid,
         * patientDomain, serviceid, orgCode);
         * 
         * empiDao.updateSurgicalProcedure(patientSn, updateTime, patientLid,
         * patientDomain, orgCode);
         * 
         * empiDao.updateAnesthesia(patientSn, updateTime, patientLid,
         * patientDomain, orgCode);
         * 
         * empiDao.updateBloodRequest(patientSn, updateTime, patientLid,
         * patientDomain, serviceid, orgCode);
         * 
         * empiDao.updateClinicalDocument(patientSn, updateTime, patientLid,
         * patientDomain, serviceid, orgCode);
         * 
         * empiDao.updateBillingReceipt(patientSn, updateTime, patientLid,
         * patientDomain, orgCode);
         * 
         * empiDao.updateBillingItem(patientSn, updateTime, patientLid,
         * patientDomain, orgCode);
         * 
         * empiDao.updateRegistrationFee(patientSn, updateTime, patientLid,
         * patientDomain, orgCode);
         * 
         * empiDao.updateLabOrder(patientSn, updateTime, patientLid,
         * patientDomain, serviceid, orgCode);
         * 
         * empiDao.updateLabResult(patientSn, updateTime, patientLid,
         * patientDomain, serviceid, orgCode);
         */
    }

    // [BUG]0037319 MODIFY END

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
            List<String> tableNames)
    {
        return empiDao.selectExistsColumnOrNot(columnName, tableNames);
    }

    // [BUG]0043616 ADD END
    
    /**
     * 查询影像图片内部序列号集合
     */
    @Override
    @Transactional
    public List<String> selectImageSns(String reportSn)
    {
        Map<String, Object> condition = new HashMap<String, Object>();
        condition.put("reportSn", reportSn);
        condition.put("deleteFlag", Constants.NO_DELETE_FLAG);
        List<Object> medicalImagings = entityDao.selectByCondition(
                MedicalImaging.class, condition);

        List<String> imageSns = null;
        if (medicalImagings != null && medicalImagings.size() > 0)
        {
            imageSns = new ArrayList<String>();
            for (Object obj : medicalImagings)
            {
                MedicalImaging image = (MedicalImaging) obj;
                imageSns.add(image.getImagingSn().toString());
            }

        }
        return imageSns;
    }

    @Transactional
    public boolean checkFlag(Long times, Long totaltimet, Long skipTimes2,
            Long skiptotaltime)
    {

        if (times > skipTimes2)
        {
            return true;
        }
        if (totaltimet > skiptotaltime)
        {
            return true;
        }
        return false;

    }

    @Override
    @Transactional
    public OrderStepDto getOrderStep(String orderStatus, Object order)
            throws Exception
    {
        OrderStepDto orderStep = new OrderStepDto();
        if (ExaminationOrder.class.equals(order.getClass())
            || LabOrder.class.equals(order.getClass()))
        {
            if (Constants.ORDER_STATUS_VALIDATED.equals(orderStatus))
            {
                Field confirmPersonName = order.getClass().getDeclaredField(
                        "confirmPersonName");
                Field confirmTime = order.getClass().getDeclaredField(
                        "confirmTime");
                Field orderDeptName = order.getClass().getDeclaredField(
                        "orderDeptName");
                confirmPersonName.setAccessible(true);
                confirmTime.setAccessible(true);
                orderDeptName.setAccessible(true);
                orderStep.setExecutePersonName(confirmPersonName.get(order) == null ? ""
                        : (String) confirmPersonName.get(order));
                orderStep.setExecuteTime((Date) confirmTime.get(order));
                orderStep.setExecuteDeptName(orderDeptName.get(order) == null ? ""
                        : (String) orderDeptName.get(order));
                orderStep.setOrderStatusName(DictionaryCache.getDictionary(
                        Constants.DOMAIN_ORDER_STATUS).get(
                        Constants.ORDER_STATUS_VALIDATED));
                orderStep.setOrderStatusCode(Constants.ORDER_STATUS_VALIDATED);
            }
            else if (Constants.ORDER_STATUS_ISSUE.equals(orderStatus))
            {
                Field orderPersonName = order.getClass().getDeclaredField(
                        "orderPersonName");
                Field orderTime = order.getClass().getDeclaredField("orderTime");
                Field orderDeptName = order.getClass().getDeclaredField(
                        "orderDeptName");
                orderPersonName.setAccessible(true);
                orderTime.setAccessible(true);
                orderDeptName.setAccessible(true);
                orderStep.setExecutePersonName(orderPersonName.get(order) == null ? ""
                        : (String) orderPersonName.get(order));
                orderStep.setExecuteTime((Date) orderTime.get(order));
                orderStep.setExecuteDeptName(orderDeptName.get(order) == null ? ""
                        : (String) orderDeptName.get(order));
                orderStep.setOrderStatusName(DictionaryCache.getDictionary(
                        Constants.DOMAIN_ORDER_STATUS).get(
                        Constants.ORDER_STATUS_ISSUE));
                orderStep.setOrderStatusCode(Constants.ORDER_STATUS_ISSUE);
            }
            else if (Constants.ORDER_STATUS_CANCEL.equals(orderStatus))
            {
                Field cancelPersonName = order.getClass().getDeclaredField(
                        "cancelPersonName");
                Field cancelTime = order.getClass().getDeclaredField(
                        "cancelTime");
                Field orderDeptName = order.getClass().getDeclaredField(
                        "orderDeptName");
                cancelPersonName.setAccessible(true);
                cancelTime.setAccessible(true);
                orderDeptName.setAccessible(true);
                orderStep.setExecutePersonName(cancelPersonName.get(order) == null ? ""
                        : (String) cancelPersonName.get(order));
                orderStep.setExecuteTime((Date) cancelTime.get(order));
                orderStep.setExecuteDeptName(orderDeptName.get(order) == null ? ""
                        : (String) orderDeptName.get(order));
                orderStep.setOrderStatusName(DictionaryCache.getDictionary(
                        Constants.DOMAIN_ORDER_STATUS).get(
                        Constants.ORDER_STATUS_CANCEL));
                orderStep.setOrderStatusCode(Constants.ORDER_STATUS_CANCEL);
            }
            else if (Constants.ORDER_STATUS_STOP.equals(orderStatus))
            {
                Field stopPersonName = order.getClass().getDeclaredField(
                        "stopPersonName");
                Field sotpTime = order.getClass().getDeclaredField("stopTime");
                Field orderDeptName = order.getClass().getDeclaredField(
                        "orderDeptName");
                stopPersonName.setAccessible(true);
                sotpTime.setAccessible(true);
                orderDeptName.setAccessible(true);
                orderStep.setExecutePersonName(stopPersonName.get(order) == null ? ""
                        : (String) stopPersonName.get(order));
                orderStep.setExecuteTime((Date) sotpTime.get(order));
                orderStep.setExecuteDeptName(orderDeptName.get(order) == null ? ""
                        : (String) orderDeptName.get(order));
                orderStep.setOrderStatusName(DictionaryCache.getDictionary(
                        Constants.DOMAIN_ORDER_STATUS).get(
                        Constants.ORDER_STATUS_STOP));
                orderStep.setOrderStatusCode(Constants.ORDER_STATUS_STOP);
            }
            else if (Constants.ORDER_STATUS_PAYMENT.equals(orderStatus))
            {
                
                orderStep.setExecutePersonName("");
                orderStep.setExecuteTime(null);
                orderStep.setExecuteDeptName("");
                orderStep.setOrderStatusName(DictionaryCache.getDictionary(
                        Constants.DOMAIN_ORDER_STATUS).get(
                        Constants.ORDER_STATUS_PAYMENT));
                orderStep.setOrderStatusCode(Constants.ORDER_STATUS_PAYMENT);
            } else if(Constants.ORDER_STATUS_RETURNED_CHARGE.equals(orderStatus)){
            	orderStep.setExecutePersonName("");
                orderStep.setExecuteTime(null);
                orderStep.setExecuteDeptName("");
                orderStep.setOrderStatusName(DictionaryCache.getDictionary(
                        Constants.DOMAIN_ORDER_STATUS).get(
                        Constants.ORDER_STATUS_RETURNED_CHARGE));
                orderStep.setOrderStatusCode(Constants.ORDER_STATUS_RETURNED_CHARGE);
            }
        }
        return orderStep;
    }

    @Override
    @Transactional
    public List<OrderStepDto> getOrderStepDtos(String orderSn)
    {
        List<OrderStepDto> orderSteps = new ArrayList<OrderStepDto>();
        Map<String, Object> condition = new HashMap<String, Object>();
        condition.put("orderSn", orderSn);
        condition.put("deleteFlag", Constants.NO_DELETE_FLAG);
        List<Object> nursingOperations = entityDao.selectByCondition(
                NursingOperation.class, condition);
        if (nursingOperations != null && nursingOperations.size() > 0)
        {
            for (Object object : nursingOperations)
            {
                NursingOperation nursingOperation = (NursingOperation) object;
                OrderStepDto orderStep = new OrderStepDto();
                orderStep.setExecutePersonName(nursingOperation.getOperatorName() == null ? ""
                        : nursingOperation.getOperatorName());
                orderStep.setExecuteTime(nursingOperation.getOperationTime());
                orderStep.setOrderStatusCode(nursingOperation.getOrderStatusCode());
                orderStep.setOrderStatusName(nursingOperation.getOrderStatusName());
                orderStep.setExecuteDeptName(nursingOperation.getExecuteDeptName() == null ? ""
                        : nursingOperation.getExecuteDeptName());
                orderSteps.add(orderStep);
            }
        }

        return orderSteps;
    }

    @Transactional
    public OrderStepDto getChargeOrderStep(String orderSn)
    {
        Map<String, Object> condition = new HashMap<String, Object>();
        condition.put("orderSn", orderSn);
        condition.put("deleteFlag", Constants.NO_DELETE_FLAG);
        List<Object> objs = entityDao.selectByCondition(BillingItem.class,
                condition);
        if (objs != null && objs.size() > 0)
        {
            BillingItem billingItem = (BillingItem) objs.get(0);
            OrderStepDto orderStep = new OrderStepDto();
            orderStep.setExecutePersonName(billingItem.getConfirmPersonName() == null ? ""
                    : billingItem.getConfirmPersonName());
            orderStep.setExecuteTime(billingItem.getConfirmTime());
            orderStep.setOrderStatusCode(Constants.CHARGE_STATUS_CHARGED);
            orderStep.setOrderStatusName(DictionaryCache.getDictionary(
                    Constants.ORDER_CHARGE_STATUS).get(
                    Constants.CHARGE_STATUS_CHARGED));
            orderStep.setExecuteDeptName("收费处");
            return orderStep;
        }
        return null;
    }

    @Transactional
    public CommonDto getMaxNursingOperDate(BigDecimal visitSn,
            BigDecimal orderSn)
    {
        return commonDao.getMaxNursingOperDate(visitSn, orderSn);
    }

    // $Author :chang_xuewen
    // $Date : 2013/08/26 18:21$
    // [BUG]0036600 ADD BEGIN
    @Override
    @Transactional
    public int getOperation(BigDecimal orderSn, String orderStatus,
            String operationDate)
    {
        // TODO Auto-generated method stub
        return commonDao.selectOperation(orderSn, orderStatus, operationDate);
    }

    // [BUG]0036600 ADD END

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
            String businessDataNo, String operationTime)
    {
        return commonDao.selectAuditLog(accountLogSn, businessDataNo,
                operationTime);
    }

    /**
     * 将构造完成的消息内容及是否发送成功标识更新到相应记录中
     * @param systemAuditLogList 敏感信息审计对象
     * @return
     */
    @Transactional
    public void updateAuditLog(SystemAuditLog systemAuditLog)
    {
        if (systemAuditLog != null)
        {
            entityDao.update(systemAuditLog);
        }
    }

    // [BUG]0037540 ADD END
    @Override
	public int insert(Object entity) {
    	return entityDao.insert(entity);
	}
}
