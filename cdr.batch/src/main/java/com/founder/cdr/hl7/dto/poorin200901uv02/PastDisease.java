package com.founder.cdr.hl7.dto.poorin200901uv02;

public class PastDisease
{
    /**
     * 既往史疾病编码
     */
    private String pastDiseaseCode;

    /**
     * 既往史疾病名称
     */
    private String pastDiseaseName;

    public String getPastDiseaseCode()
    {
        return pastDiseaseCode;
    }

    public String getPastDiseaseName()
    {
        return pastDiseaseName;
    }

    public void setPastDiseaseCode(String pastDiseaseCode){
    	this.pastDiseaseCode = pastDiseaseCode;
    }
    
    public void setPastDiseaseName(String pastDiseaseName){
    	this.pastDiseaseName = pastDiseaseName;
    }
    
    @Override
    public String toString()
    {
        StringBuilder builder = new StringBuilder();
        builder.append("PastDisease [pastDiseaseCode=");
        builder.append(pastDiseaseCode);
        builder.append(", pastDiseaseName=");
        builder.append(pastDiseaseName);
        builder.append("]");
        return builder.toString();
    }

}
