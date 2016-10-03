package com.yly.cdr.dao;

import java.util.List;

import com.founder.fasf.core.util.daohelper.annotation.Arguments;
import com.founder.fasf.web.paging.PagingContext;
import com.yly.cdr.dto.ChartDto;
import com.yly.cdr.dto.DrugOrderDto;
import com.yly.cdr.dto.MedicationOrderDto;
import com.yly.cdr.dto.VisitDateDto;
import com.yly.cdr.dto.drug.DispensingRecordDto;
import com.yly.cdr.dto.drug.DrugListSearchPra;
import com.yly.cdr.entity.MedicationOrder;
import com.yly.cdr.entity.Prescription;

public interface DrugListDao
{

    /**
     * 
     * [FUN]V05-101药物医嘱详细
     * @version 1.0, 2012/03/012  
     * @author 张彬
     * @since 1.0
     *  * 根据ordersn查找药物医嘱详细信息
     * 
     * 
     */
	@Arguments({ "orderSn"})
    public MedicationOrderDto selectMedicationOrder(String orderSn);
	
	/**
     * 提供userSN，排除hide_drug信息
     * @author chang_xuewen
     */
    @Arguments({ "orderSn", "userSn" })
    public MedicationOrderDto selectMedicationOrder(String orderSn, String userSn);

    /**
     * 提供userSN，排除hide_drug信息
     * @author chang_xuewen
     */
    @Arguments({ "orderSn", "userSn" })
    public Prescription selectPrescriptionOb(String orderSn, String userSn);

    @Arguments({ "orderSn" })
    public DrugOrderDto[] selectRecord(String orderSn);

    @Arguments({ "orderSn" })
    public List<DispensingRecordDto> selectDispensingList(String orderSn);
    
    /**
     * 根据用户sn排除hide_drug表信息
     * @author chang_xuewen
     * @param orderSn
     * @param userSn
     * @return
     */
    @Arguments({ "orderSn","userSn" })
    public List<DispensingRecordDto> selectDispensingList(String orderSn, String userSn);

    /**
     * 
     * [FUN]V05-101药物医嘱列表
     * @version 1.0, 2012/03/012  
     * @author 张彬
     * @since 1.0
     *  * 搜索
     * 
     * 
     */
    /*
     * $Author :bin_zhang $Date : 2012/10/25 13:03$ [BUG]0010500 MODIFY BEGIN
     */
    @Arguments({ "patientSn", "approvedDrugName", "orderTime1", "orderTime2",
            "medicineTypeName", "stopTime1", "stopTime2", "orderPersonName",
            "execDept", "temporaryFlag" })
    public MedicationOrder[] selectDrugListSearch(String patientSn,
            String approvedDrugName, String orderTime1, String orderTime2,
            String medicineTypeName, String stopTime1, String stopTime2,
            String orderPersonName, String execDept, String temporaryFlag,
            PagingContext pagingContext);

    // [BUG]0010500 MODIFY END */
    
    /**
     * [FUN]药物医嘱列表   根据用户SN排除hide_drug表信息
     * @author chang_xuewen
     * @return
     */
    @Arguments({ "patientSn", "approvedDrugName", "orderTime1", "orderTime2",
        "medicineTypeName", "stopTime1", "stopTime2", "orderPersonName",
        "execDept", "temporaryFlag", "userSn" })
    public MedicationOrder[] selectDrugListSearch(String patientSn,
        String approvedDrugName, String orderTime1, String orderTime2,
        String medicineTypeName, String stopTime1, String stopTime2,
        String orderPersonName, String execDept, String temporaryFlag,
        String userSn,PagingContext pagingContext);

    /**
     * 提供userSN，排除hide_drug信息
     * @author chang_xuewen
     */
    @Arguments({ "patientSn", "drugListSearchPra", "userSn"})
    public MedicationOrderDto[] selectDrugListSearchOb(String patientSn,
            DrugListSearchPra drugListSearchPra, String userSn, PagingContext pagingContext);

    // $Author:bin_zhang
    // $Date:2012/9/28 10:42
    // $[BUG]BUG0010082 ADD BEGIN
    @Arguments({ "visitSn" })
    public MedicationOrder[] selectDrugListVisit(String visitSn);
    
    /**
     * 提供userSN，排除hide_drug信息
     * @author chang_xuewen
     */
    @Arguments({ "visitSn","userSn" })
    public MedicationOrder[] selectDrugListVisit(String visitSn, String userSn, PagingContext pagingContext);

    // $[BUG]BUG0010082 ADD END

    // $Author :jin_peng
    // $Date : 2012/11/22 13:34$
    // [BUG]0011026 MODIFY BEGIN
    // $Author :tong_meng
    // $Date : 2012/9/29 14:45$
    // [BUG]0010038 ADD BEGIN
    // $Author :tong_meng
    // $Date : 2012/9/29 17:22$
    // [BUG]0010913 MODIFY BEGIN
    @Arguments({ "patientSn" })
    public List<VisitDateDto> selectVisitDate(String patientSn);

    // [BUG]0010913 MODIFY END
    // [BUG]0010038 ADD END
    // [BUG]0011026 MODIFY END

    // $Author :jin_peng
    // $Date : 2013/01/07 16:00$
    // [BUG]0012888 ADD BEGIN
    /**
     * 查询药物医嘱图形内容
     * @param itemFlag 查询项目标识
     * @param patientSn 患者内部序列号
     * @param displayPieCount 饼图显示记录数
     * @param approvedDrugName 药物通用名
     * @return 查询药物医嘱图形内容
     */
    @Arguments({ "itemFlag", "patientSn", "displayPieCount", "approvedDrugName" })
    public List<ChartDto> selectDrugChart(String itemFlag, String patientSn,
            int displayPieCount, String approvedDrugName,
            PagingContext pagingContext);

    // [BUG]0012888 ADD END

    /**
     * 查询药物医嘱图形内容 ,增加用户sn过滤hide_drug表信息
     * @author chang_xuewen
     * @return 查询药物医嘱图形内容
     */
    @Arguments({ "itemFlag", "patientSn", "displayPieCount", "approvedDrugName", "userSn" })
    public List<ChartDto> selectDrugChart(String itemFlag, String patientSn,
            int displayPieCount, String approvedDrugName, String userSn,
            PagingContext pagingContext);
    
}
