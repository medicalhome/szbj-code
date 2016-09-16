package com.founder.cdr.hl7.dto;

import java.util.List;

import org.hibernate.validator.constraints.NotEmpty;

import com.founder.cdr.util.DateUtils;

public class ExaminationResult extends BaseDto
{
    /**
     * 试剂代码
     */
    private String reagentCode;

    /**
     * 试剂名称
     */
    private String reagentName;

    /**
     * 试剂用量
     */
    private String reagentDosage;

    /**
     * 试剂用量单位
     */
    private String reagentDosageUnit;

    /**
     * 检查项目
     */
    private List<ExaminationItems> examItems;

    /**
     * 影像内容
     */
    private List<LabImageContent> imageText;

    // $Author:wei_peng
    // $Date:2012/9/25 13:45
    // $[BUG]0010017 DELETE BEGIN
    /**
     * 影像学表现
     */
    // private String imagingFinding;

    /**
     * 影像学结论
     */
    // private String imagingConclusion;
    // $[BUG]0010017 DELETE END

    /**
     *检查类型代码 
     */
   // @NotEmpty(message = "{ExaminationResult.itemClass}")
    private String itemClass;

    /**
     *检查类型名称 
     */
    private String itemClassName;

    // $Author:jin_peng
    // $Date:2012/11/23 17:35
    // $[BUG]0011888 MODIFY BEGIN
    /**
     * 图像索引号
     */
    @NotEmpty(message = "{ExaminationResult.imageIndex}")
    private String imageIndex;

    // $[BUG]0011888 MODIFY END
    
    // $Author :tong_meng
    // $Date : 2013/7/1 14:00$
    // [BUG]0033936 ADD BEGIN
    private String reportTypeCode;
    
    private String reportTypeName;
    // [BUG]0033936 ADD END

    public String getItemClass()
    {
        return itemClass;
    }

    public String getItemClassName()
    {
        return itemClassName;
    }

    public String getReagentCode()
    {
        return reagentCode;
    }

    public String getReagentName()
    {
        return reagentName;
    }

    public String getReagentDosage()
    {
        return reagentDosage;
    }

    public String getReagentDosageUnit()
    {
        return reagentDosageUnit;
    }

    public List<ExaminationItems> getExamItems()
    {
        return examItems;
    }

    public List<LabImageContent> getImageText()
    {
        return imageText;
    }

    public String getImageIndex()
    {
        return imageIndex;
    }

    public String getReportTypeCode()
    {
        return reportTypeCode;
    }

    public String getReportTypeName()
    {
        return reportTypeName;
    }
    
    @Override
    public String toString()
    {
        StringBuilder builder = new StringBuilder();
        builder.append("ExaminationResult [");
        builder.append("reagentCode=");
        builder.append(reagentCode).append(", ");
        builder.append("reagentName=");
        builder.append(reagentName).append(", ");
        builder.append("reagentDosage=");
        builder.append(reagentDosage).append(", ");
        builder.append("reagentDosageUnit=");
        builder.append(reagentDosageUnit).append(", ");
        builder.append("examItems=");
        builder.append(examItems).append(", ");
        builder.append("itemClass=");
        builder.append(itemClass).append(", ");
        builder.append("itemClassName=");
        builder.append(itemClassName).append(", ");
        builder.append("reportTypeCode=");
        builder.append(reportTypeCode).append(", ");
        builder.append("reportTypeName=");
        builder.append(reportTypeName).append(", ");
        builder.append("]");
        return builder.toString();
    }
}
