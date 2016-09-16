package com.founder.cdr.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Transactional;

import com.founder.cdr.entity.CodeDrug;
import com.founder.cdr.entity.DispensingRecord;
import com.founder.cdr.entity.HerbalFormula;
import com.founder.cdr.entity.MedOrderDispensingRecord;
import com.founder.cdr.entity.MedicationOrder;
import com.founder.cdr.entity.Prescription;
import com.founder.fasf.web.paging.PagingContext;

public interface PrescriptionService
{
    @Transactional
    public boolean insertPrescription(String patientEid,
            String prescriptionTime, String prescriptionNo,
            String prescriptionClass, String prescriptionType,
            String prescriptionDoctorId, String reviewPerson,
            String reviewTime, String visitSn, String visitTime,
            PagingContext pagingContext);

    @Transactional
    public int save(Object entity);

    /**
     * 修改全部信息
     * @param entity 根据业务传入entity
     * @return 是否保存成功标识
     */
    @Transactional
    public int update(Object entity);

    /**
     * 处方及其相关联的用药医嘱和中药配方的接入
     * @param prescriptionAddList 需要添加处方对象集合
     * @param medicationOrderAddList 需要添加的用药医嘱对象集合 
     * @param herbalFormulaAddList 需要添加的中药配方对象集合
     * @param deleteTime 医嘱删除时间
     */
    // Author :jia_yanqing
    // Date : 2013/3/26 14:00
    // [BUG]14709 MODIFY BEGIN
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
            Date deleteTime);

    // [BUG]14140 MODIFY END
    // [BUG]14709 MODIFY END

    /**
     * 新增用药医嘱和相关中药配方集合
     * @param medicationOrders 用药医嘱对象集合
     * @param herbalFormulaSaveList 待添加的中药配方集合
     */
    @Transactional
    public void saveMedicationOrders(
            Map<MedicationOrder, List<HerbalFormula>> medicationOrders);

    /**
     * 更新用药医嘱和相关中药配方集合
     * @param medicationOrders 用药医嘱对象集合
     * @param herbalFormulaSaveList 待添加的中药配方集合
     * @param herbalFormulaDeleteList 待删除的中药配方集合
     */
    @Transactional
    public void updateMedicationOrders(
            Map<MedicationOrder, List<HerbalFormula>> medicationOrders,
            List<HerbalFormula> herbalFormulaDeleteList);

    /**
     * 根据药物编码和包装序号，查询药物信息
     * @param drugCode 药品编码
     * @param serialNo 包装序号
     * @return
     */
    @Transactional
    public CodeDrug selectCodeDrug(String drugCode, String serialNo);

    // $Author :chang_xuewen
    // $Date : 2013/12/04 11:00$
    // [BUG]0040271 MODIFY BEGIN
    /**
     * 根据就诊内部序列号查找处方集合
     * @param visitSn 就诊内部序列号
     * @param dataSource 数据来源（来自处方还是诊疗）
     * @return 处方集合
     */
    @Transactional
    public List<Prescription> selectPrescriptionsByVisitSn(String visitSn,
            String dataSource,String orgCode);
    // [BUG]0040271 MODIFY END
    
    /*
     * Author: yu_yzh
     * Date: 2015/11/06 10:13
     * ADD BEGIN
     * */
    /**
     * 根据处方号查询处方
     * @param prescriptionNo 处方号
     * @return 处方
     * */
    @Transactional
    public Prescription selectPrescriptionsByPrescriptionNo(String prescriptionNo,
            String visitSn, String patientSn);
    
    /**
     * 根据用药医嘱号查询用药医嘱
     * */
    @Transactional
    public MedicationOrder selectMedicationOrderByOrderLid(String orderLid, String visitSn);
    
    /**
     * 根据处方号查询该处方下的所有用药医嘱
     * */
    @Transactional
    public List<MedicationOrder> selectMedicationOrdersByPrescriptionSn(String prescriptionSn, String visitSn);
 // ADD END

    /**
     * 根据处方内部序列号查询处方
     * */
    @Transactional
	public Prescription selectPrescriptionByPrescriptionSn(String prescriptionSn,
			String visitSn);
    
    /**
     * 根据药品id查询药品字典
     * */
    @Transactional
    public CodeDrug selectCodeDrugByDrugId(String drugId);
    
    /**
     * 根据药品code查询药品字典
     * */
    @Transactional
    public CodeDrug selectCodeDrugByDrugCode(String code);
}
