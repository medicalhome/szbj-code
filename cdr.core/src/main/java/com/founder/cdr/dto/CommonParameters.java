package com.founder.cdr.dto;

/**
 * 
 * [FUN]V05-101时间轴列表及住院视图所用更多列表参数
 * @version 1.0, 2012/06/11  
 * @author 金鹏
 * @since 1.0
 * 
 */
public class CommonParameters
{
    // 就诊内部序列号
    private String visitSn;

    // 住院日期
    private String inpatientDate;

    // 业务类型
    private String type;

    // $Author :jin_peng
    // $Date : 2012/10/10 18:28$
    // [BUG]0010239 ADD BEGIN
    // 药物医嘱长期临时标识
    private String longTempFlag;

    // 药物医嘱撤销标识
    private String cancelOrderStatus;

    // 长期药物医嘱住院日期
    private String inpatientLongTermDate;

    // [BUG]0010239 ADD END

    public String getVisitSn()
    {
        return visitSn;
    }

    public void setVisitSn(String visitSn)
    {
        this.visitSn = visitSn;
    }

    public String getInpatientDate()
    {
        return inpatientDate;
    }

    public void setInpatientDate(String inpatientDate)
    {
        this.inpatientDate = inpatientDate;
    }

    public String getType()
    {
        return type;
    }

    public void setType(String type)
    {
        this.type = type;
    }

    // $Author :jin_peng
    // $Date : 2012/10/10 18:28$
    // [BUG]0010239 ADD BEGIN
    public String getLongTempFlag()
    {
        return longTempFlag;
    }

    public void setLongTempFlag(String longTempFlag)
    {
        this.longTempFlag = longTempFlag;
    }

    public String getCancelOrderStatus()
    {
        return cancelOrderStatus;
    }

    public void setCancelOrderStatus(String cancelOrderStatus)
    {
        this.cancelOrderStatus = cancelOrderStatus;
    }

    public String getInpatientLongTermDate()
    {
        return inpatientLongTermDate;
    }

    public void setInpatientLongTermDate(String inpatientLongTermDate)
    {
        this.inpatientLongTermDate = inpatientLongTermDate;
    }
    // [BUG]0010239 ADD END

}
