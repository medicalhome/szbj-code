package com.yly.cdr.hl7.dto;

/**
 * 
 * @author tong_meng
 *
 */
public class CodeValueDto extends BaseDto
{
    /**
     * 术语消息记录的唯一标识
     * @return
     */
    public String getUniqueId()
    {
        return "";
    }

    /**
     * 术语消息记录的操作类型
     * @return
     */
    public String getOptType()
    {
        return "";
    }
    
    // $Author :tong_meng
    // $Date : 2012/11/05 11:00$
    // [BUG]0011055 MODIFY BEGIN
    // $Author :tong_meng
    // $Date : 2012/10/25 15:00$
    // [BUG]0010742 ADD BEGIN
    /**
     * 当术语不能用一个字段查询时，
     * 标识术语消息的外键
     * @return
     */
    public String getForeignKey()
    {
        return "";
    }
    // [BUG]0010742 ADD END
    // [BUG]001105 MODIFY END
}
