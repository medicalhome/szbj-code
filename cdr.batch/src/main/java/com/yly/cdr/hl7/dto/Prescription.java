package com.yly.cdr.hl7.dto;

import java.util.List;

import org.hibernate.validator.constraints.NotEmpty;

public class Prescription extends BaseDto
{
    /**
     * 处方号或摆药单序号
     */
    @NotEmpty(message = "{Prescription.prescriptionNo}")
    private String prescriptionNo;
    /**
     * 配药人编码
     */
    // $Author :jia_yanqing
    // $Date : 2012/10/08 13:50$
    // [BUG]10234 DELETE BEGIN
    //@NotEmpty(message = "{Prescription.dispenserId}")
    private String dispenserId;
    /**
     * 配药人姓名
     */
    //@NotEmpty(message = "{Prescription.dispenserName}")
    private String dispenserName;
    // [BUG]10234 DELETE END
    /**
     * 发药确认时间
     */
    @NotEmpty(message = "{Prescription.confirmTime}")
    private String confirmTime;
    /**
     * 发药人编码
     */
//    @NotEmpty(message = "{Prescription.confirmPersonId}")
    private String confirmPersonId;
    /**
     * 发药人姓名
     */
//    @NotEmpty(message = "{Prescription.confirmPersonName}")
    private String confirmPersonName;
    /**
     * 请领人编码
     */
    private String receivePersonId;
    /**
     * 请领人姓名
     */
    private String receivePersonName;
    /**
     * 执行科室编码
     */
    private String execDeptId;
    /**
     * 执行科室名称
     */
    private String execDeptName;
    /**
     * 发药科室编码
     */
    private String dispenseDeptId;
    /**
     * 发药科室名称
     */
    private String dispenseDeptName;
    /**
     * 药物信息
     */
    @NotEmpty(message = "{Prescription.drugs}")
    private List<Drugs> drugs;

    public String getPrescriptionNo()
    {
        return prescriptionNo;
    }

    public String getDispenserId()
    {
        return dispenserId;
    }

    public String getDispenserName()
    {
        return dispenserName;
    }

    public String getConfirmTime()
    {
        return confirmTime;
    }

    public String getConfirmPersonId()
    {
        return confirmPersonId;
    }

    public String getConfirmPersonName()
    {
        return confirmPersonName;
    }

    public String getReceivePersonId()
    {
        return receivePersonId;
    }

    public String getReceivePersonName()
    {
        return receivePersonName;
    }

    public String getExecDeptId()
    {
        return execDeptId;
    }

    public String getExecDeptName()
    {
        return execDeptName;
    }

    public String getDispenseDeptId()
    {
        return dispenseDeptId;
    }

    public String getDispenseDeptName()
    {
        return dispenseDeptName;
    }

    public List<Drugs> getDrugs()
    {
        return drugs;
    }

    @Override
    public String toString()
    {
        StringBuilder builder = new StringBuilder();
        builder.append("Prescription [prescriptionNo=");
        builder.append(prescriptionNo);
        builder.append(", confirmTime=");
        builder.append(confirmTime);
        builder.append(", confirmPersonId=");
        builder.append(confirmPersonId);
        builder.append(", confirmPersonName=");
        builder.append(confirmPersonName);
        builder.append(", drugs=");
        builder.append(drugs);
        builder.append("]");
        return builder.toString();
    }

    
}
