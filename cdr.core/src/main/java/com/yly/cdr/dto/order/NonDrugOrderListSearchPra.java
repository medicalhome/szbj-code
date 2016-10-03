package com.yly.cdr.dto.order;

import java.math.BigDecimal;
import java.util.List;

import com.yly.cdr.dto.CommonParameters;
import com.yly.cdr.dto.VisitDateDto;

/**
 * 
 * [FUN]V05-101非药物医嘱列表
 * @version 1.0, 2012/03/12  
 * @author 金鹏
 * @since 1.0
 * 
 */
public class NonDrugOrderListSearchPra extends CommonParameters
{

    // 医嘱类型
    private String orderType;

    // 医嘱名称
    private String orderName;

    // 开嘱科室
    private String orderDept;

    // 开嘱人
    private String orderPerson;

    // 字符类型 开嘱开始时间
    private String orderStrStartTime;

    // 字符类型 结束时间
    private String orderStrEndTime;

    // 执行科室
    private String execDept;

    // $Author :tong_meng
    // $Date : 2012/9/29 14:45$
    // [BUG]0010038 ADD BEGIN
    // 就诊次数
    private String visitTimes;

    // 患者当前的最大就诊次数
    private List<BigDecimal> visitTimesList;

    // [BUG]0010038 ADD END

    // $Author :jin_peng
    // $Date : 2012/11/22 13:34$
    // [BUG]0011026 ADD BEGIN
    // 就诊日期对应的就诊内部序列号
    private String visitDateSn;

    // 患者就诊日期集合
    private List<VisitDateDto> visitDateList;

    // [BUG]0011026 ADD END
    
	// $Author :chang_xuewen
	// $Date : 2013/12/16 11:00$
	// [BUG]0040770 ADD BEGIN
    private String orgCode;
    
	private String orgName;
	// [BUG]0040770 ADD END
	
    public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

    public String getOrderType()
    {
        return orderType;
    }

    public void setOrderType(String orderType)
    {
        this.orderType = orderType;
    }

    public String getOrderName()
    {
        return orderName;
    }

    public void setOrderName(String orderName)
    {
        this.orderName = orderName;
    }

    public String getOrderDept()
    {
        return orderDept;
    }

    public void setOrderDept(String orderDept)
    {
        this.orderDept = orderDept;
    }

    public String getOrderPerson()
    {
        return orderPerson;
    }

    public void setOrderPerson(String orderPerson)
    {
        this.orderPerson = orderPerson;
    }

    public String getOrderStrStartTime()
    {
        return orderStrStartTime;
    }

    public void setOrderStrStartTime(String orderStrStartTime)
    {
        this.orderStrStartTime = orderStrStartTime;
    }

    public String getOrderStrEndTime()
    {
        return orderStrEndTime;
    }

    public void setOrderStrEndTime(String orderStrEndTime)
    {
        this.orderStrEndTime = orderStrEndTime;
    }

    public String getExecDept()
    {
        return execDept;
    }

    public void setExecDept(String execDept)
    {
        this.execDept = execDept;
    }

    public String getVisitTimes()
    {
        return visitTimes;
    }

    public void setVisitTimes(String visitTimes)
    {
        this.visitTimes = visitTimes;
    }

    public List<BigDecimal> getVisitTimesList()
    {
        return visitTimesList;
    }

    public void setVisitTimesList(List<BigDecimal> visitTimesList)
    {
        this.visitTimesList = visitTimesList;
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
}
