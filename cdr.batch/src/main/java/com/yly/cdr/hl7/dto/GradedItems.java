package com.yly.cdr.hl7.dto;

import java.util.List;

import org.hibernate.validator.constraints.NotEmpty;

import com.yly.cdr.hl7.dto.GradedDetail;

public class GradedItems extends BaseDto
{
    /**
     * 评分项目代码
     */
    @NotEmpty(message = "{GradedItems.gradedItemsId}")
    private String gradedItemsId;
    /**
     * 评分项目名称
     */
    private String gradedItemsName;
    /**
     * 总分
     */
    @NotEmpty(message = "{GradedItems.totalPoints}")
    private String totalPoints;
    /**
     * 评分状态
     */
    private String gradedStatus;
    /**
     * 评分细目
     */
    private List<GradedDetail> gradedDetail;

    public String getGradedItemsId()
    {
        return gradedItemsId;
    }

    public String getGradedItemsName()
    {
        return gradedItemsName;
    }

    public String getTotalPoints()
    {
        return totalPoints;
    }

    public String getGradedStatus()
    {
        return gradedStatus;
    }

    public List<GradedDetail> getGradedDetail()
    {
        return gradedDetail;
    }
    
    // Author :jia_yanqing
    // Date : 2013/06/08 11:30
    // [BUG]0033506 ADD BEGIN
    @Override
    public String toString()
    {
        StringBuilder builder = new StringBuilder();
        builder.append("GradedItems [gradedItemsId=");
        builder.append(gradedItemsId);
        builder.append(", totalPoints=");
        builder.append(totalPoints);
        return builder.toString();
    }
    // [BUG]0033506 ADD END
}
