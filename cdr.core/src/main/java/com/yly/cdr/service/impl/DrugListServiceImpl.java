package com.yly.cdr.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.founder.fasf.core.util.daohelper.entity.EntityDao;
import com.founder.fasf.web.paging.PagingContext;
import com.yly.cdr.core.Constants;
import com.yly.cdr.dao.DrugListDao;
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
import com.yly.cdr.service.DrugListService;

@Component
public class DrugListServiceImpl implements DrugListService
{
    @Autowired
    private DrugListDao DrugListDao;

    @Autowired
    private EntityDao entityDao;
    
    // 药物医嘱详细信息
    @Transactional
    public MedicationOrderDto selectDrugList(String orderSn)
    {
        return (MedicationOrderDto) DrugListDao.selectMedicationOrder(orderSn);
    }
    
    /**
     * 提供userSN，排除hide_drug信息
     * @author chang_xuewen
     */
    // 药物医嘱详细信息
    @Transactional
    public MedicationOrderDto selectDrugList(String orderSn, String userSn)
    {
        return (MedicationOrderDto) DrugListDao.selectMedicationOrder(orderSn ,userSn);
    }

    /*
     * $Author :bin_zhang $Date : 2012/10/25 13:03$ [BUG]0010500 ADD BEGIN
     */
    // 普通搜索
    @Transactional
    public MedicationOrder[] selectDrugList(String patientSn,
            String approvedDrugName, String orderTime1, String orderTime2,
            String medicineTypeName, String stopTime1, String stopTime2,
            String orderPersonName, String execDept, String temporaryFlag,
            PagingContext pagingContext)

    {
        MedicationOrder[] DrugResult = DrugListDao.selectDrugListSearch(
                patientSn, approvedDrugName, orderTime1, orderTime2,
                medicineTypeName, stopTime1, stopTime2, orderPersonName,
                execDept, temporaryFlag, pagingContext);

        return DrugResult;
    }
    
    /**
     * 提供userSN，排除hide_drug信息
     * @author chang_xuewen
     */
    @Transactional
    public MedicationOrder[] selectDrugList(String patientSn,
            String approvedDrugName, String orderTime1, String orderTime2,
            String medicineTypeName, String stopTime1, String stopTime2,
            String orderPersonName, String execDept, String temporaryFlag,
            String userSn,PagingContext pagingContext)

    {
        MedicationOrder[] DrugResult = DrugListDao.selectDrugListSearch(
                patientSn, approvedDrugName, orderTime1, orderTime2,
                medicineTypeName, stopTime1, stopTime2, orderPersonName,
                execDept, temporaryFlag, userSn, pagingContext);

        return DrugResult;
    }

    // [BUG]0010500 ADD END
       
    /**
     * 提供userSN，排除hide_drug信息
     * @author chang_xuewen
     */
    @Transactional
    public MedicationOrderDto[] selectDrugList(String patientSn,
            DrugListSearchPra drugListSearchPra, String userSn, PagingContext pagingContext)

    {
        MedicationOrderDto[] DrugResult = DrugListDao.selectDrugListSearchOb(
                patientSn, drugListSearchPra, userSn, pagingContext);

        return DrugResult;
    }

    @Transactional
    public DrugOrderDto[] selectRecord(String orderSn)
    {
        DrugOrderDto[] DrugResult = DrugListDao.selectRecord(orderSn);

        return DrugResult;
    }

    @Override
    @Transactional
    public Prescription selectPrescription(String prescriptionSn)
    {

        return (Prescription) entityDao.selectById(Prescription.class,
                prescriptionSn);
    }

    @Override
    @Transactional
    public Prescription selectPrescriptionOb(String orderSn, String userSn)
    {

        return DrugListDao.selectPrescriptionOb(orderSn, userSn);
    }

    @Override
    @Transactional
    public DispensingRecord selectDispensing(String dispensingSn)
    {
        return (DispensingRecord) entityDao.selectById(DispensingRecord.class,
                dispensingSn);
    }

    @Override
    @Transactional
    public List<DispensingRecordDto> selectDispensingList(String orderSn)
    {
        return DrugListDao.selectDispensingList(orderSn);
    }
    
    /**
     * 根据用户sn排除hide_drug表信息
     * @author chang_xuewen
     * @param orderSn
     * @param userSn
     * @return
     */
    @Override
    @Transactional
	public List<DispensingRecordDto> selectDispensingList(String orderSn,
			String userSn) {
		return DrugListDao.selectDispensingList(orderSn,userSn);
	}   

    @Override
    @Transactional
    public List<HerbalFormula> selectHerbal(String orderSn)
    {
        Map<String, Object> osCondition = new HashMap<String, Object>();
        osCondition.put("orderSn", orderSn);
        osCondition.put("deleteFlag", Constants.NO_DELETE_FLAG);
        List result = new ArrayList<HerbalFormula>();
        return result = entityDao.selectByCondition(HerbalFormula.class,
                osCondition);
    }

    /**
     * 查询将与发药记录想关联的用药医嘱信息
     */
    @Override
    @Transactional
    public List<MedicationOrder> selectMedicationOrder(String drugCode, String orderLid,
            String patientDomain, String patientLid, String orgCode)
    {
        Map<String, Object> condition = new HashMap<String, Object>();
//        condition.put("drugCode", drugCode);
        condition.put("orderLid", orderLid);
        condition.put("patientDomain", patientDomain);
        condition.put("patientLid", patientLid);
        condition.put("orgCode", orgCode);
        condition.put("deleteFlag", Constants.NO_DELETE_FLAG);
        List result = entityDao.selectByCondition(MedicationOrder.class,
                condition);
        return result;
    }

    /**
     * 得到唯一一条发药记录
     */
    @Override
    @Transactional
    public DispensingRecord getDisRecord(BigDecimal dispensingSn, String recordNo)
    {
        Map<String, Object> condition = new HashMap<String, Object>();
        condition.put("dispensingSn", dispensingSn);
        condition.put("deleteFlag", Constants.NO_DELETE_FLAG);
        if(!StringUtils.isEmpty(recordNo)){
        	condition.put("recordNo", recordNo);
        }
        List result = entityDao.selectByCondition(DispensingRecord.class,
                condition);
        if (result == null || result.size() == 0)
        {
            return null;
        }
        return (DispensingRecord) result.get(0);
    }

    // Author :jin_peng
    // Date : 2012/11/14 14:56
    // [BUG]0011478 MODIFY BEGIN
    /**
     * 保存发药记录、用药医嘱和发药记录表
     */
    @Override
    @Transactional
    public void saveDispensing(List<DispensingRecord> dispensingRecordList,
            List<MedOrderDispensingRecord> medOrderDispensingRecordList,
            List<MedicationOrder> medicalOrderList)
    {
        for (DispensingRecord dispensingRecord : dispensingRecordList)
        {
            entityDao.insert(dispensingRecord);
        }
        for (MedOrderDispensingRecord medOrderDispensingRecord : medOrderDispensingRecordList)
        {
            entityDao.insert(medOrderDispensingRecord);
        }

        // 添加发药记录对应的用药医嘱信息
        if (medicalOrderList != null && !medicalOrderList.isEmpty())
        {
            for (MedicationOrder medicationOrder : medicalOrderList)
            {
                entityDao.updatePartially(medicationOrder, "orderStatus",
                        "orderStatusName", "updateTime", "updateby");
            }
        }
    }

    // [BUG]0011478 MODIFY END

    /**
     * 查询用药医嘱与发药确认表中的数据
     */
    @Override
    @Transactional
    public List<MedOrderDispensingRecord> selectMedOrderDispensingRecordList(
            BigDecimal orderSn, String batchNo)
    {
        Map<String, Object> condition = new HashMap<String, Object>();
        condition.put("orderSn", orderSn);
        condition.put("deleteFlag", Constants.NO_DELETE_FLAG);
        if(!StringUtils.isEmpty(batchNo)){
        	condition.put("batchNo", batchNo);
        }
        List result = entityDao.selectByCondition(
                MedOrderDispensingRecord.class, condition);
        return result;
    }

    @Override
    @Transactional
    public void updateDispensing(List<DispensingRecord> dispensingRecordList)
    {
        for (DispensingRecord dispensingRecord : dispensingRecordList)
        {
            entityDao.update(dispensingRecord);
        }
    }

    @Override
    @Transactional
    public List<DispensingRecord> selectDispensingRecord(BigDecimal dispensingSn)
    {
        Map<String, Object> condition = new HashMap<String, Object>();
        condition.put("dispensingSn", dispensingSn);
        condition.put("deleteFlag", Constants.NO_DELETE_FLAG);
        List result = entityDao.selectByCondition(DispensingRecord.class,
                condition);
        if (result == null || result.size() == 0)
        {
            return null;
        }
        return result;
    }

    // $Author:bin_zhang
    // $Date:2012/9/28 10:42
    // $[BUG]BUG0010082 ADD BEGIN
    @Override
    @Transactional
    public MedicationOrder[] selectDrugListVist(String visitSn)
    {
        MedicationOrder[] DrugResult = DrugListDao.selectDrugListVisit(visitSn);
        return DrugResult;
    }
    
    /**
     * 提供userSN，排除hide_drug信息
     * @author chang_xuewen
     */
    @Override
    @Transactional
    public MedicationOrder[] selectDrugListVist(String visitSn,String userSn, PagingContext pagingContext)
    {
        MedicationOrder[] DrugResult = DrugListDao.selectDrugListVisit(visitSn, userSn, pagingContext);
        return DrugResult;
    }
    
    // $[BUG]BUG0010082 ADD END

    // $Author :jin_peng
    // $Date : 2012/11/22 13:34$
    // [BUG]0011026 MODIFY BEGIN
    // $Author :tong_meng
    // $Date : 2012/9/29 14:45$
    // [BUG]0010038 ADD BEGIN
    @Override
    @Transactional
    public List<VisitDateDto> selectVisitDate(String patientSn)
    {
        // $Author :tong_meng
        // $Date : 2012/9/29 17:22$
        // [BUG]0010913 MODIFY BEGIN
        return getActuallyVisitDate(DrugListDao.selectVisitDate(patientSn));
        // [BUG]0010913 MODIFY END
    }

    // [BUG]0010038 ADD END
    // [BUG]0011026 MODIFY END

    // $Author :jin_peng
    // $Date : 2012/11/22 13:34$
    // [BUG]0011026 ADD BEGIN
    /**
     * 针对同一天多次就诊情况处理就诊日期显示方式
     * @param visitDateDtoList 就诊日期集合
     * @return 处理完成的就诊日期集合
     */
    private List<VisitDateDto> getActuallyVisitDate(
            List<VisitDateDto> visitDateDtoList)
    {
        List<VisitDateDto> temp = new ArrayList<VisitDateDto>();
        List<String> visitDates = new ArrayList<String>();

        for (VisitDateDto visitDateDto : visitDateDtoList)
        {
            visitDates.add(visitDateDto.getVisitDate());
        }

        for (VisitDateDto visitDateDto : visitDateDtoList)
        {
            String visitDateFullStr = visitDateDto.getVisitDate();

            // 针对同一天多次就诊情况确定就诊日期显示方式
            if (visitDateFullStr.indexOf("(1)") != -1)
            {
                String visitDateStr = visitDateFullStr.substring(0,
                        visitDateFullStr.length() - 3);
                String compareStr = visitDateStr + "(2)";

                if (!visitDates.contains(compareStr))
                {
                    visitDateDto.setVisitDate(visitDateStr);
                }
                else
                {
                    visitDateDto.setVisitDate(visitDateFullStr);
                }

                temp.add(visitDateDto);
            }
            else
            {
                temp.add(visitDateDto);
            }
        }

        return temp;
    }

    // [BUG]0011026 ADD END

    // $Author :jin_peng
    // $Date : 2013/01/07 16:00$
    // [BUG]0012888 ADD BEGIN
    /**
     * 查询药物医嘱饼图需展示内容总数
     * @param patientSn 患者内部序列号
     * @return 药物医嘱饼图需展示内容总数
     */
    @Transactional
    public int selectDrugChartTotal(String patientSn, String userSn)
    {
        List<ChartDto> displayPieTotalList = DrugListDao.selectDrugChart(
                ChartDto.DISPLAY_TOTAL_PIE, patientSn, 0, null, userSn, null);

        int displayPieTotal = displayPieTotalList.get(0).getDisplayPieTotal();

        return displayPieTotal;
    }

    /**
     * 查询药物医嘱饼图需展示内容
     * @param patientSn 患者内部序列号
     * @return 药物医嘱饼图需展示内容
     */
    @Transactional
    public List<ChartDto> selectDrugChartPie(String patientSn,String userSn)
    {
        List<ChartDto> displayPieContent = DrugListDao.selectDrugChart(
                ChartDto.DISPLAY_CONTENT_PIE, patientSn,
                Constants.PIE_DISPLAY_COUNT, null, userSn, null);

        return displayPieContent;
    }

    /**
     * 查询药物医嘱趋势图需展示内容
     * @param patientSn 患者内部序列号
     * @param approvedDrugName 药物通用名
     * @return 药物医嘱趋势图需展示内容
     */
    @Transactional
    public List<ChartDto> selectDrugChartTrend(String patientSn,
            String approvedDrugName, String userSn, PagingContext pagingContext)
    {
        List<ChartDto> displayTrendContent = DrugListDao.selectDrugChart(
                ChartDto.DISPLAY_CONTENT_TREND, patientSn, 0, approvedDrugName,
                userSn, pagingContext);

        return displayTrendContent;
    }

	@Override
	public void updateReturnList(List<DispensingRecord> dispensingRecordList,
			List<MedOrderDispensingRecord> medOrderDispensingRecordList,
			List<MedicationOrder> medicalOrderList) {
		if(medicalOrderList != null && !medicalOrderList.isEmpty()){
			for(MedicationOrder mo : medicalOrderList) entityDao.update(mo);
		}
		if(medOrderDispensingRecordList != null && !medOrderDispensingRecordList.isEmpty()){
			for(MedOrderDispensingRecord modr : medOrderDispensingRecordList) entityDao.update(modr);
		}
		if(dispensingRecordList != null && !dispensingRecordList.isEmpty()){
			for(DispensingRecord dr : dispensingRecordList) entityDao.update(dr);
		}
	}

	@Override
	public BigDecimal getDispensingSn(BigDecimal orderSn) {
		BigDecimal dispensingSn = null;
		List<MedOrderDispensingRecord> list = selectMedOrderDispensingRecordList(orderSn, null);
		if(list != null && !list.isEmpty()){
			dispensingSn = list.get(0).getDispensingSn();
		}
		return dispensingSn;
	}

	
    // [BUG]0012888 ADD END
}
