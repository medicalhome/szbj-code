package com.founder.cdr.dto.drug;

import java.math.BigDecimal;
import java.util.List;

import com.founder.cdr.dto.CommonParameters;
import com.founder.cdr.dto.VisitDateDto;

/**
 * 
 * [FUN]V05-101药物医嘱列表
 * @version 1.0, 2012/03/12  
 * @author 张彬
 * @since 1.0
 * 
 */
public class DrugListSearchPra extends CommonParameters
{

    // 药物通用名
    private String approvedDrugName;

    // $Author :tong_meng
    // $Date : 2012/9/26 16:45$
    // [BUG]00100145 MODIFY BEGIN
    // 药物类型
    private String medicineType;

    // [BUG]00100145 MODIFY END

    // 开医嘱人
    private String orderPersonName;

    // 开嘱日期
    private String orderTimeFrom;

    private String orderTimeTo;

    // 停嘱时间
    private String stopTimeFrom;

    private String stopTimeTo;

    // 医嘱开立科室
    private String orderDept;

    // $Author :tong_meng
    // $Date : 2012/9/26 16:45$
    // [BUG]0010038 ADD BEGIN
    // 就诊次数
    private String visitTimes;

    // 患者当前的最大就诊次数
    private List<BigDecimal> VisitTimesList;

    // 就诊类型
    private String visitType;

    // $Author :tong_meng
    // $Date : 2012/11/06 17:00$
    // [BUG]0010919 DELETE BEGIN
    // 诊断类型
    /* private String diagnoseType; */
    // [BUG]0010919 DELETE END

    // $Author :jin_peng
    // $Date : 2012/11/22 13:34$
    // [BUG]0011026 ADD BEGIN
    // 就诊日期对应的就诊内部序列号
    private String visitDateSn;

    // 患者就诊日期集合
    private List<VisitDateDto> visitDateList;

    // [BUG]0011026 ADD END

    // 疾病名称
    private String diseaseName;

    // [BUG]0010038 ADD BEGIN

    // $Author :tong_meng
    // $Date : 2012/10/23 10:00$
    // [BUG]0009621 ADD BEGIN
    private String temporaryFlag;

    // [BUG]0009621 ADD BEGIN
    //医疗机构
    private String orgCode;

    public String getApprovedDrugName()
    {
        return approvedDrugName;
    }

    public void setApprovedDrugName(String approvedDrugName)
    {
        this.approvedDrugName = approvedDrugName;
    }

    public String getMedicineType()
    {
        return medicineType;
    }

    public void setMedicineType(String medicineType)
    {
        this.medicineType = medicineType;
    }

    public String getOrderPersonName()
    {
        return orderPersonName;
    }

    public void setOrderPersonName(String orderPersonName)
    {
        this.orderPersonName = orderPersonName;
    }

    public String getOrderTimeFrom()
    {
        return orderTimeFrom;
    }

    public void setOrderTimeFrom(String orderTimeFrom)
    {
        this.orderTimeFrom = orderTimeFrom;
    }

    public String getOrderTimeTo()
    {
        return orderTimeTo;
    }

    public void setOrderTimeTo(String orderTimeTo)
    {
        this.orderTimeTo = orderTimeTo;
    }

    public String getStopTimeFrom()
    {
        return stopTimeFrom;
    }

    public void setStopTimeFrom(String stopTimeFrom)
    {
        this.stopTimeFrom = stopTimeFrom;
    }

    public String getStopTimeTo()
    {
        return stopTimeTo;
    }

    public void setStopTimeTo(String stopTimeTo)
    {
        this.stopTimeTo = stopTimeTo;
    }

    public String getOrderDept()
    {
        return orderDept;
    }

    public void setOrderDept(String orderDept)
    {
        this.orderDept = orderDept;
    }

    public String getVisitType()
    {
        return visitType;
    }

    public void setVisitType(String visitType)
    {
        this.visitType = visitType;
    }

    public String getVisitTimes()
    {
        return visitTimes;
    }

    public void setVisitTimes(String visitTimes)
    {
        this.visitTimes = visitTimes;
    }

    public String getDiseaseName()
    {
        return diseaseName;
    }

    public void setDiseaseName(String diseaseName)
    {
        this.diseaseName = diseaseName;
    }

    public List<BigDecimal> getVisitTimesList()
    {
        return VisitTimesList;
    }

    public void setVisitTimesList(List<BigDecimal> visitTimesList)
    {
        VisitTimesList = visitTimesList;
    }

    public String getTemporaryFlag()
    {
        return temporaryFlag;
    }

    public void setTemporaryFlag(String temporaryFlag)
    {
        this.temporaryFlag = temporaryFlag;
    }

    public String getVisitDateSn()
    {
        return visitDateSn;
    }

    public void setVisitDateSn(String visitDateSn)
    {
        this.visitDateSn = visitDateSn;
    }

    public List<VisitDateDto> getVisitDateList()
    {
        return visitDateList;
    }

    public void setVisitDateList(List<VisitDateDto> visitDateList)
    {
        this.visitDateList = visitDateList;
    }

    public String getOrgCode()
    {
        return orgCode;
    }

    public void setOrgCode(String orgCode)
    {
        this.orgCode = orgCode;
    }
    
}
