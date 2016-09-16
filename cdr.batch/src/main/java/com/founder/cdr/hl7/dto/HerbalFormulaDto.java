package com.founder.cdr.hl7.dto;


public class HerbalFormulaDto extends BaseDto
{
    /**
     * 数量
     */
    private String quantity;
    /**
     * 数量单位
     */
    private String unit;
    /**
     * 药物名称代码
     */
    private String herbCode;
    /**
     * 药物名称
     */
    private String herbName;
    /**
     * 煎法名称
     */
    private String decoctionMethodName;
    /**
     * 与付数无关标记
     */
    private String numUnconcernedFlag;
    /**
     * 中药包装序号
     */
    private String serialNo;
    /**
     * 嘱咐
     */
    private String medicineDesc;
    /**
     * 药品编码
     */
    private String drugCode;
    /**
     * 特殊用法编码
     */
    private String decoctionMethodCode;
    /**
     * 药物医保类型编码
     */
    private String medicalInsuranceType;
    /**
     * 药物医保类型名称
     */
    private String medicalInsuranceTypeName;

    public String getQuantity()
    {
        return quantity;
    }

    public String getUnit()
    {
        return unit;
    }

    public String getHerbCode()
    {
        return herbCode;
    }

    public String getHerbName()
    {
        return herbName;
    }

    public String getDecoctionMethodName()
    {
        return decoctionMethodName;
    }

    public String getNumUnconcernedFlag()
    {
        return numUnconcernedFlag;
    }

    public String getSerialNo()
    {
        return serialNo;
    }

    public String getMedicineDesc()
    {
        return medicineDesc;
    }

    public String getDrugCode()
    {
        return drugCode;
    }

    public String getDecoctionMethodCode()
    {
        return decoctionMethodCode;
    }

    public String getMedicalInsuranceType()
    {
        return medicalInsuranceType;
    }

    public String getMedicalInsuranceTypeName()
    {
        return medicalInsuranceTypeName;
    }

    public void setHerbName(String herbName)
    {
        this.herbName = herbName;
    }

    @Override
    public String toString()
    {
        StringBuilder builder = new StringBuilder("HerbalFormulaDto {\n");
        builder.append("quantity=");
        builder.append(quantity).append("\n");
        builder.append("unit=");
        builder.append(unit).append("\n");
        builder.append("herbCode=");
        builder.append(herbCode).append("\n");
        builder.append("herbName=");
        builder.append(herbName).append("\n");
        builder.append("decoctionMethod=");
        builder.append("numUnconcernedFlag=");
        builder.append(numUnconcernedFlag).append("\n");
        builder.append("serialNo=");
        builder.append(serialNo).append("\n");
        builder.append("medicineDesc=");
        builder.append(medicineDesc).append("\n");
        builder.append("drugCode=");
        builder.append(drugCode).append("\n");
        builder.append("decoctionMethodCode=");
        builder.append(decoctionMethodCode).append("\n");
        builder.append("medicalInsuranceType=");
        builder.append(medicalInsuranceType).append("\n");
        builder.append("medicalInsuranceTypeName=");
        builder.append(medicalInsuranceTypeName).append("\n");
        builder.append("}");
        return builder.toString();
    }
    
}
