package com.founder.cdr.hl7.dto;

import java.util.List;

import org.hibernate.validator.constraints.NotEmpty;

import com.founder.cdr.hl7.dto.Diagnosis;

public class MedicalVisit extends BaseDto
{
    /**
     * 门诊号
     */
    private String visitNo;
    /**
     * 住院号
     */
    private String admissionNo;
    /**
     * 就诊次数
     */
  //  @NotEmpty(message="{MedicalVisit.visitTimes}")
    private String visitTimes;
    /**
     * 就诊类别
     */
    @NotEmpty(message="{MedicalVisit.visitType}")
    private String visitType;
    /**
     * 患者科室
     */
    private String visitDept;
    /**
     * 患者科室名称
     */
    private String visitDeptName;
    /**
     * 病区编码
     */
    private String admissionWards;
    /**
     * 床位号
     */
    private String admissionBedNo;
    /**
     * 病区名称
     */
    private String admissionWardsName;
    /**
     * 就诊日期
     */
    private String visitDate;
    /**
     * 诊断
     */
    private List<Diagnosis> diagnosises;
    
    /**
     * 就诊流水号
     */
    @NotEmpty(message="{MedicalVisit.visitOrdNo}")
    private String visitOrdNo;
    /**
     * 就诊类别名称
     */
//    @NotEmpty(message="{MedicalVisit.visitTypeName}")
    private String visitTypeName;

    public void setDiagnosises(List<Diagnosis> diagnosises)
    {
        this.diagnosises = diagnosises;
    }

    public String getVisitNo()
    {
        return visitNo;
    }
    
    

    public String getVisitDate()
    {
        return visitDate;
    }

    public String getAdmissionNo()
    {
        return admissionNo;
    }

	public String getVisitTimes() {
		return visitTimes;
	}

	public String getVisitType()
    {
        return visitType;
    }

    public String getVisitDept()
    {
        return visitDept;
    }

    public String getVisitDeptName()
    {
        return visitDeptName;
    }

    public String getAdmissionWards()
    {
        return admissionWards;
    }

    public String getAdmissionBedNo()
    {
        return admissionBedNo;
    }

    public String getAdmissionWardsName()
    {
        return admissionWardsName;
    }

    public List<Diagnosis> getDiagnosises()
    {
        return diagnosises;
    }
    
    public String getVisitOrdNo()
    {
        return visitOrdNo;
    }
    
	public String getVisitTypeName() {
		return visitTypeName;
	}

    @Override
    public String toString()
    {
        StringBuilder builder = new StringBuilder();
        builder.append("MedicalVisit [visitNo=");
        builder.append(visitNo);
        builder.append(", admissionNo=");
        builder.append(admissionNo);
        builder.append(", visitTimes=");
        builder.append(visitTimes);
        builder.append(", visitOrdNo=");
        builder.append(visitOrdNo);
        builder.append(", visitType=");
        builder.append(visitType);
        builder.append(", diagnosises=");
        builder.append(diagnosises);
        builder.append(", visitTypeName=");
        builder.append(visitTypeName);
        builder.append("]");
        return builder.toString();
    }


}
