package com.yly.cdr.dto;

import java.util.Date;

public class CodeValueDto
{
    private String versionNo;

    private String deleteFlag;

    public String getVersionNo()
    {
        return versionNo;
    }

    public void setVersionNo(String versionNo)
    {
        this.versionNo = versionNo;
    }

    public String getDeleteFlag()
    {
        return deleteFlag;
    }

    public void setDeleteFlag(String deleteFlag)
    {
        this.deleteFlag = deleteFlag;
    }
    
    /*
     * $Author: yu_yzh
     * $Date: 2015/8/28 11:03
     * 更新时间  ADD BEGIN
     * */
    private Date updateTime;
    
    public Date getUpdateTime(){
    	return updateTime;
    }
    
    public void setUpdateTime(Date date){
    	this.updateTime = date;
    }
    // ADD END
}
