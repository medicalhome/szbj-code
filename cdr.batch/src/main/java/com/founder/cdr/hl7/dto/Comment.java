package com.founder.cdr.hl7.dto;


public class Comment extends BaseDto
{
    /**
     * 意见顺序
     */
    private String displayOrder;
    /**
     * 补充意见
     */
    private String supplementaryOpinion;

    public String getDisplayOrder()
    {
        return displayOrder;
    }

    public String getSupplementaryOpinion()
    {
        return supplementaryOpinion;
    }
}
