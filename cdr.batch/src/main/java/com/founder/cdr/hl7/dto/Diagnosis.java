package com.founder.cdr.hl7.dto;

public class Diagnosis extends BaseDto
{
    /**
     * 诊断类别代码
     */
    private String diagnosisType;
    
    /**
     * 诊断类别名称
     */
    private String diagnosisTypeName;
    
    /**
     * 疾病编码
     */
    private String diseaseCode;
    
    /**
     * 疾病名称
     */
    private String diseaseName;
    
    /**
     * 诊断日期
     */
    private String diagnosisDate;
    
    /**
     * 诊断顺位
     */
    private String priorityNumber;

    /**
     * 诊断ID
     */
    private String diagnosisNo;
    
    /**
     * 诊断名称
     */
    private String diagnosisName;
    
	public String getDiagnosisType() {
		return diagnosisType;
	}

	public void setDiagnosisType(String diagnosisType) {
		this.diagnosisType = diagnosisType;
	}

	public String getDiagnosisTypeName() {
		return diagnosisTypeName;
	}

	public void setDiagnosisTypeName(String diagnosisTypeName) {
		this.diagnosisTypeName = diagnosisTypeName;
	}

	public String getDiseaseName() {
		return diseaseName;
	}

	public void setDiseaseName(String diseaseName) {
		this.diseaseName = diseaseName;
	}

	public String getDiseaseCode() {
		return diseaseCode;
	}

	public void setDiseaseCode(String diseaseCode) {
		this.diseaseCode = diseaseCode;
	}
	
	public String getPriorityNumber() {
        return priorityNumber;
    }

    public void setPriorityNumber(String priorityNumber) {
        this.priorityNumber = priorityNumber;
    }

    public String getDiagnosisDate() {
        return diagnosisDate;
    }

    public void setDiagnosisDate(String diagnosisDate) {
        this.diagnosisDate = diagnosisDate;
    }

    public String getDiagnosisNo() {
        return diagnosisNo;
    }

    public void setDiagnosisNo(String diagnosisNo) {
        this.diagnosisNo = diagnosisNo;
    }

    public String getDiagnosisName() {
        return diagnosisName;
    }

    public void setDiagnosisName(String diagnosisName) {
        this.diagnosisName = diagnosisName;
    }

    @Override
    public String toString()
    {
        StringBuilder builder = new StringBuilder();
        builder.append("Diagnosis [diagnosisNo=");
        builder.append(diagnosisNo);
        builder.append("]");
        return builder.toString();
    }
}
