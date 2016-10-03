/**
 * [FUN]V03-001诊断 service
 * 
* @version 1.0, 2011/10/28  11:00:00

 * @author  liujingyang to jinpeng * @since 1.0
*/
package com.yly.cdr.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.founder.fasf.core.util.daohelper.entity.EntityDao;
import com.founder.fasf.web.paging.PagingContext;
import com.yly.cdr.core.Constants;
import com.yly.cdr.dao.PrescriptionDao;
import com.yly.cdr.entity.CodeDrug;
import com.yly.cdr.entity.DispensingRecord;
import com.yly.cdr.entity.HerbalFormula;
import com.yly.cdr.entity.MedOrderDispensingRecord;
import com.yly.cdr.entity.MedicationOrder;
import com.yly.cdr.entity.Prescription;
import com.yly.cdr.service.CommonService;
import com.yly.cdr.service.PrescriptionService;

@Component
public class PrescriptionServiceImpl implements PrescriptionService
{
    // 医嘱序列名称
    private static final String ORDER_SEQUENCE = "SEQ_ORDER";

    @Autowired
    private PrescriptionDao prescriptionDao;

    @Autowired
    private EntityDao entityDao;

    @Autowired
    private CommonService commonService;

    @Transactional
    public int save(Object entity)
    {
        return entityDao.insert(entity);
    }

    /**
     * 修改全部信息
     * @param entity 根据业务传入entity
     * @return 是否保存成功标识
     */
    @Transactional
    public int update(Object entity)
    {
        return entityDao.update(entity);
    }

    @Transactional
    public boolean insertPrescription(String patientEid,
            String prescriptionTime, String prescriptionNo,
            String prescriptionClass, String prescriptionType,
            String prescriptionDoctorId, String reviewPerson,
            String reviewTime, String visitSn, String visitTime,
            PagingContext pagingContext)
    {
        boolean flag = prescriptionDao.insertPrescription(patientEid,
                prescriptionTime, prescriptionNo, prescriptionClass,
                prescriptionType, prescriptionDoctorId, reviewPerson,
                reviewTime, visitSn, visitTime, pagingContext);
        return flag;
    }

    /**
     * 处方及其相关联的用药医嘱和中药配方的接入
     * @param prescriptionAddList 需要添加处方对象集合
     * @param medicationOrderAddList 需要添加的用药医嘱对象集合 
     * @param herbalFormulaAddList 需要添加的中药配方对象集合
     */
    // Author :jia_yanqing
    // Date : 2013/3/1 11:00
    // [BUG]14140 MODIFY BEGIN
    @Transactional
    public void saveOrDeletePrescription(
            List<Prescription> prescriptionAddList,
            List<MedicationOrder> medicationOrderAddList,
            List<HerbalFormula> herbalFormulaAddList,
            List<Prescription> prescriptionListForReturnFee,
            List<MedicationOrder> medicationListForReturnFee,
            List<HerbalFormula> herbalFormulaListForReturnFee,
            List<MedOrderDispensingRecord> medOrderDispensingRecordListForReturnFee,
            List<DispensingRecord> dispensingRecordListForReturnFee,
            Date deleteTime)
    {

        // Author :jia_yanqing
        // Date : 2013/3/26 14:00
        // [BUG]14709 ADD BEGIN
        // 对退费后的处方关联的药物医嘱关联的用药医嘱与发药记录执行物理删除操作
        commonService.deleteList(medOrderDispensingRecordListForReturnFee);
        // 对退费后的处方关联的药物医嘱关联用药医嘱与发药记录关联的发药记录执行物理删除操作
        // [BUG]14709 ADD END
        commonService.deleteList(dispensingRecordListForReturnFee);
        // 对退费后的处方关联的药物医嘱的中药配方执行物理删除操作
        commonService.deleteList(herbalFormulaListForReturnFee);
        // 对退费后的处方关联的药物医嘱执行物理删除以及更新收费状态的操作
        commonService.deleteList(medicationListForReturnFee);
        // 对退费后的处方执行物理删除操作
        commonService.deleteList(prescriptionListForReturnFee);

        // 新增处方
        if (prescriptionAddList != null && !prescriptionAddList.isEmpty())
        {
            for (Prescription prescriptionAdd : prescriptionAddList)
            {
                save(prescriptionAdd);
            }
        }

        // 新增用药医嘱
        if (medicationOrderAddList != null && !medicationOrderAddList.isEmpty())
        {
            for (MedicationOrder medicationOrderAdd : medicationOrderAddList)
            {
                save(medicationOrderAdd);
            }
        }

        // 新增中药配方对象，当药物类别为中草药时进行此操作
        if (herbalFormulaAddList != null && !herbalFormulaAddList.isEmpty())
        {
            for (HerbalFormula herbalFormulaAdd : herbalFormulaAddList)
            {
                save(herbalFormulaAdd);
            }
        }
    }

    // [BUG]14140 MODIFY END

    /**
     * 新增用药医嘱和相关中药配方集合
     * @param medicationOrders 用药医嘱对象集合
     * @param herbalFormulaSaveList 待添加的中药配方集合
     */
    @Override
    @Transactional
    public void saveMedicationOrders(
            Map<MedicationOrder, List<HerbalFormula>> medicationOrders)
    {
        List<MedicationOrder> medicationOrderSaveList = new ArrayList<MedicationOrder>();
        List<HerbalFormula> herbalFormulaSaveList = new ArrayList<HerbalFormula>();
        // 从map中提取需要新增的用药医嘱和中草药配方集合
        convertMapToLists(medicationOrders, medicationOrderSaveList,
                herbalFormulaSaveList, true);
        // 新增用药医嘱
        if (medicationOrderSaveList != null
            && !medicationOrderSaveList.isEmpty())
        {
            for (MedicationOrder medicationOrder : medicationOrderSaveList)
            {
                save(medicationOrder);
            }
        }
        // 增加新的中药配方项
        if (herbalFormulaSaveList != null && !herbalFormulaSaveList.isEmpty())
        {
            for (HerbalFormula herbalFormula : herbalFormulaSaveList)
            {
                save(herbalFormula);
            }
        }
    }

    /**
     * 更新用药医嘱和相关中药配方集合
     * @param medicationOrders 用药医嘱对象集合
     * @param herbalFormulaSaveList 待添加的中药配方集合
     * @param herbalFormulaDeleteList 待删除的中药配方集合
     */
    @Override
    @Transactional
    public void updateMedicationOrders(
            Map<MedicationOrder, List<HerbalFormula>> medicationOrders,
            List<HerbalFormula> herbalFormulaDeleteList)
    {
        List<MedicationOrder> medicationOrderUpdateList = new ArrayList<MedicationOrder>();
        List<HerbalFormula> herbalFormulaSaveList = new ArrayList<HerbalFormula>();
        // 从map中提取需要新增的用药医嘱和中草药配方集合
        convertMapToLists(medicationOrders, medicationOrderUpdateList,
                herbalFormulaSaveList, false);
        // 更新用药医嘱
        if (medicationOrderUpdateList != null
            && !medicationOrderUpdateList.isEmpty())
        {
            for (MedicationOrder medicationOrder : medicationOrderUpdateList)
            {
                update(medicationOrder);
            }
        }
        // 删除已有的中药配方项
        if (herbalFormulaDeleteList != null
            && !herbalFormulaDeleteList.isEmpty())
        {
            for (HerbalFormula herbalFormula : herbalFormulaDeleteList)
            {
                herbalFormula.setDeleteFlag(Constants.DELETE_FLAG);
                entityDao.update(herbalFormula);
            }
        }
        // 增加新的中药配方项
        if (herbalFormulaSaveList != null && !herbalFormulaSaveList.isEmpty())
        {
            for (HerbalFormula herbalFormula : herbalFormulaSaveList)
            {
                save(herbalFormula);
            }
        }
    }

    /**
     * 提取用药医嘱信息集合的内容将其存放在另外两个集合中
     * @param medicationOrders 用药医嘱信息集合
     * @param medicationOrderSaveList 需要新增或更新的用药医嘱集合
     * @param herbalFormulaSaveList 需要新增或更新的中草药配方集合
     * @param isNewMessage 是否为新增
     */
    private void convertMapToLists(
            Map<MedicationOrder, List<HerbalFormula>> medicationOrders,
            List<MedicationOrder> medicationOrderSaveOrUpdateList,
            List<HerbalFormula> herbalFormulaSaveList, boolean isNewMessage)
    {
        BigDecimal orderSn = null;
        Iterator<Entry<MedicationOrder, List<HerbalFormula>>> iterator = medicationOrders.entrySet().iterator();
        // 迭代用药医嘱信息集合
        while (iterator.hasNext())
        {
            // 单条用药医嘱信息
            Entry<MedicationOrder, List<HerbalFormula>> entry = iterator.next();
            // 通过map的键取得用药医嘱
            MedicationOrder medicationOrder = entry.getKey();
            // 如果是新消息，获取序列号
            if (isNewMessage)
                medicationOrder.setOrderSn(commonService.getSequence(ORDER_SEQUENCE));
            // 添加信息到列表
            medicationOrderSaveOrUpdateList.add(medicationOrder);
            // 通过map的值取得用药医嘱对应的中草药集合
            List<HerbalFormula> herbalFormulas = entry.getValue();
            if (herbalFormulas != null)
            {
                setOrderSn(herbalFormulas, medicationOrder.getOrderSn());
                // 添加信息到列表
                herbalFormulaSaveList.addAll(herbalFormulas);
            }
        }
    }

    /**
     * 在所有中药配方项中添加医嘱内部序列号
     * @param herbalFormulas 中药配方集合
     * @param orderSn 用药医嘱内部序列号
     */
    private void setOrderSn(List<HerbalFormula> herbalFormulas,
            BigDecimal orderSn)
    {
        for (HerbalFormula herbalFormula : herbalFormulas)
        {
            herbalFormula.setOrderSn(orderSn);
        }
    }

    /**
     * 根据药物编码和包装序号，查询药物信息
     * @param drugCode 药品编码
     * @param serialNo 包装序号
     */
    @Override
    @Transactional
    public CodeDrug selectCodeDrug(String drugCode, String serialNo)
    {
        return prescriptionDao.selectCodeDrug(drugCode, serialNo);
    }

    // $Author :chang_xuewen
    // $Date : 2013/12/04 11:00$
    // [BUG]0040271 MODIFY BEGIN
    /**
     * 根据就诊内部序列号查找处方集合
     * @param visitSn 就诊内部序列号
     * @param dataSource 数据来源（来自处方消息还是诊疗消息）
     * @return 处方集合
     */
    @Transactional
    public List<Prescription> selectPrescriptionsByVisitSn(String visitSn,
            String dataSource,String orgCode)
    {
        return prescriptionDao.selectPrescriptionsByVisitSn(visitSn, dataSource, orgCode);
    }
    // [BUG]0040271 MODIFY END

	@Override
	public Prescription selectPrescriptionsByPrescriptionNo(
			String prescriptionNo, String visitSn, String patientSn) {
		
		return prescriptionDao.selectPrescriptionsByPrescriptionNo(prescriptionNo, visitSn, patientSn);
	}

	@Override
	public Prescription selectPrescriptionByPrescriptionSn(String prescriptionSn,
			String visitSn) {
		Map<String, Object> condition = new HashMap<String, Object>();
		condition.put("deleteFlag", Constants.NO_DELETE_FLAG);
		condition.put("prescriptionSn", prescriptionSn);
		condition.put("visitSn", visitSn);
		List<Object> result = entityDao.selectByCondition(Prescription.class, condition);
		if(result != null && !result.isEmpty()){
			return (Prescription) result.get(0);
		}
		return null;
	}
	
	@Override
	public MedicationOrder selectMedicationOrderByOrderLid(String orderLid,
			String visitSn) {
		Map<String, Object> condition = new HashMap<String, Object>();
		condition.put("deleteFlag", Constants.NO_DELETE_FLAG);
		condition.put("orderLid", orderLid);
		condition.put("visitSn", visitSn);
		List<Object> result = entityDao.selectByCondition(MedicationOrder.class, condition);
		if(result != null && !result.isEmpty()){
			return (MedicationOrder) result.get(0);
		}
		return null;
	}

	@Override
	public List<MedicationOrder> selectMedicationOrdersByPrescriptionSn(
			String prescriptionSn, String visitSn) {
		Map<String, Object> condition = new HashMap<String, Object>();
		condition.put("deleteFlag", Constants.NO_DELETE_FLAG);
		condition.put("prescriptionSn", prescriptionSn);
		condition.put("visitSn", visitSn);
		List<?> result = entityDao.selectByCondition(MedicationOrder.class, condition);
		if(result != null && !result.isEmpty()){
			return (List<MedicationOrder>) result;
		}
		return (new ArrayList<MedicationOrder>());
	}

	@Override
	public CodeDrug selectCodeDrugByDrugId(String drugId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("deleteFlag", Constants.NO_DELETE_FLAG);
		map.put("drugId", drugId);
		List<Object> result = entityDao.selectByCondition(CodeDrug.class, map);
		if(result != null && !result.isEmpty()){
			return (CodeDrug) result.get(0);
		}
		return null;
	}

	@Override
	public CodeDrug selectCodeDrugByDrugCode(String code) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("deleteFlag", Constants.NO_DELETE_FLAG);
		map.put("code", code);
		List<Object> result = entityDao.selectByCondition(CodeDrug.class, map);
		if(result != null && !result.isEmpty()){
			return (CodeDrug) result.get(0);
		}
		return null;
	}
}
