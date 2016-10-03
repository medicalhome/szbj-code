/**
 * Copryright
 */
package com.yly.cdr.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.founder.fasf.web.paging.PagingContext;
import com.yly.cdr.dto.ChartDto;
import com.yly.cdr.dto.DrugOrderDto;
import com.yly.cdr.dto.MedicationOrderDto;
import com.yly.cdr.dto.VisitDateDto;
import com.yly.cdr.dto.drug.DispensingRecordDto;
import com.yly.cdr.dto.drug.DrugListSearchPra;
import com.yly.cdr.entity.DispensingRecord;
import com.yly.cdr.entity.HerbalFormula;
import com.yly.cdr.entity.MedOrderDispensingRecord;
import com.yly.cdr.entity.MedicationOrder;
import com.yly.cdr.entity.Prescription;

/**
 * 
 * @author guo_hongyan
 *
 */
public interface DrugListService
{
    /**
     * 
     * [FUN]V05-101药物医嘱列表
     * @version 1.0, 2012/03/012  
     * @author 张彬
     * @since 1.0
     * 
     */
	
	@Transactional
    MedicationOrderDto selectDrugList(String orderSn);
	/**
     * 根据用户sn排除hide_drug表信息
     * @author chang_xuewen
     * @return
     */
    @Transactional
    MedicationOrderDto selectDrugList(String orderSn, String userSn);

    @Transactional
    Prescription selectPrescription(String prescriptionSn);

    /**
     * 根据用户sn排除hide_drug表信息
     * @author chang_xuewen
     * @return
     */
    @Transactional
    Prescription selectPrescriptionOb(String orderSn, String userSn);

    @Transactional
    DispensingRecord selectDispensing(String dispensingSn);

    @Transactional
    List<DispensingRecordDto> selectDispensingList(String orderSn);
    
    /**
     * 根据用户sn排除hide_drug表信息
     * @author chang_xuewen
     * @param orderSn
     * @param userSn
     * @return
     */
    @Transactional
    List<DispensingRecordDto> selectDispensingList(String orderSn, String userSn);

    @Transactional
    List<HerbalFormula> selectHerbal(String orderSn);

    @Transactional
    DrugOrderDto[] selectRecord(String orderSn);

    /*
     * $Author :bin_zhang $Date : 2012/10/25 13:03$ [BUG]0010500 ADD BEGIN
     */
    @Transactional
    MedicationOrder[] selectDrugList(String patientSn, String approvedDrugName,
            String orderTime1, String orderTime2, String medicineTypeName,
            String stopTime1, String stopTime2, String orderPersonName,
            String execDept, String temporaryFlag, PagingContext pagingContext);

    // $[BUG]BUG0010500 ADD END
    
    /**
     * 提供userSn，供排除hide_drug表信息使用
     * @author chang_xuewen
     * @return
     */
    @Transactional
    MedicationOrder[] selectDrugList(String patientSn, String approvedDrugName,
            String orderTime1, String orderTime2, String medicineTypeName,
            String stopTime1, String stopTime2, String orderPersonName,
            String execDept, String temporaryFlag, String userSn, PagingContext pagingContext);

    // $Author:bin_zhang
    // $Date:2012/9/28 10:42
    // $[BUG]BUG0010082 ADD BEGIN
    @Transactional
    MedicationOrder[] selectDrugListVist(String visitSn);
    
    /**
     * 提供userSn，供排除hide_drug表信息使用
     * @author chang_xuewen
     * @return
     */
    @Transactional
    MedicationOrder[] selectDrugListVist(String visitSn, String userSn, PagingContext pagingContext);

    // $[BUG]BUG0010082 ADD END   
    /**
     * 提供userSn，供排除hide_drug表信息
     * @author chang_xuewen
     * @return
     */
    @Transactional
    MedicationOrderDto[] selectDrugList(String patientSn,
            DrugListSearchPra drugListSearchPra, String userSn, PagingContext pagingContext);

    @Transactional
    List<MedicationOrder> selectMedicationOrder(String drugCode, String orderLid,
            String patientDomain, String patientLid, String orgCode);

    @Transactional
    DispensingRecord getDisRecord(BigDecimal dispensingSn, String recordNo);

    // Author :jin_peng
    // Date : 2012/11/14 14:56
    // [BUG]0011478 MODIFY BEGIN
    // 添加发药记录对应的用药医嘱信息
    @Transactional
    void saveDispensing(List<DispensingRecord> dispensingRecordList,
            List<MedOrderDispensingRecord> medOrderDispensingRecordList,
            List<MedicationOrder> medicalOrderList);

    // [BUG]0011478 MODIFY END

    @Transactional
    List<MedOrderDispensingRecord> selectMedOrderDispensingRecordList(
            BigDecimal orderSn, String batchNo );

    @Transactional
    void updateDispensing(List<DispensingRecord> dispensingRecordList);

    @Transactional
    List<DispensingRecord> selectDispensingRecord(BigDecimal dispensingSn);

    // $Author :jin_peng
    // $Date : 2012/11/22 13:34$
    // [BUG]0011026 MODIFY BEGIN
    // $Author :tong_meng
    // $Date : 2012/9/29 14:45$
    // [BUG]0010038 ADD BEGIN
    @Transactional
    List<VisitDateDto> selectVisitDate(String patientSn);

    // [BUG]0010038 ADD END
    // [BUG]0011026 MODIFY END

    // $Author :jin_peng
    // $Date : 2013/01/07 16:00$
    // [BUG]0012888 ADD BEGIN
    /**
     * 查询药物医嘱饼图需展示内容总数
     * @param patientSn 患者内部序列号
     * @return 药物医嘱饼图需展示内容总数
     */
    public int selectDrugChartTotal(String patientSn,String userSn);

    /**
     * 查询药物医嘱饼图需展示内容
     * @param patientSn 患者内部序列号
     * @return 药物医嘱饼图需展示内容
     */
    public List<ChartDto> selectDrugChartPie(String patientSn,String userSn);

    /**
     * 查询药物医嘱趋势图需展示内容
     * @param patientSn 患者内部序列号
     * @param approvedDrugName 药物通用名
     * @param pagingContext 分页对象
     * @return 药物医嘱趋势图需展示内容
     */
    public List<ChartDto> selectDrugChartTrend(String patientSn,
            String approvedDrugName, String userSn, PagingContext pagingContext);

	// [BUG]0012888 ADD END
    
    /**
     * 更新退药的各个记录，更新（逻辑删除）对应的发药记录，更新对应用药医嘱执行状态
     * */
    @Transactional
    public void updateReturnList(List<DispensingRecord> dispensingRecordList,
            List<MedOrderDispensingRecord> medOrderDispensingRecordList,
            List<MedicationOrder> medicalOrderList);
    
    /**
     * 返回药品医嘱的发药记录序列号，若无发药记录返回null
     * */
    @Transactional
    public BigDecimal getDispensingSn(BigDecimal orderSn);
}
