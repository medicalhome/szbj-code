// $Author:bin_zhang
// $Date : 2012/12/28 15:10
// $[BUG]0012875 ADD BEGIN
package com.yly.cdr.dto;

import java.util.Date;

public class PatientFolderMemoDto
{
    private String patientSn;

    private String createUserId;
    
    private String folderName;
    
    private Date createTime;
    
    private String memo;

    public String getPatientSn()
    {
        return patientSn;
    }

    public void setPatientSn(String patientSn)
    {
        this.patientSn = patientSn;
    }

    public String getCreateUserId()
    {
        return createUserId;
    }

    public void setCreateUserId(String createUserId)
    {
        this.createUserId = createUserId;
    }

    public String getFolderName()
    {
        return folderName;
    }

    public void setFolderName(String folderName)
    {
        this.folderName = folderName;
    }

    public Date getCreateTime()
    {
        return createTime;
    }

    public void setCreateTime(Date createTime)
    {
        this.createTime = createTime;
    }

    public String getMemo()
    {
        return memo;
    }

    public void setMemo(String memo)
    {
        this.memo = memo;
    }

    
    
}

// $[BUG]0012875 ADD END
