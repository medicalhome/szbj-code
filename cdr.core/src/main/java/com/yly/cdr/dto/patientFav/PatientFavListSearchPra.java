// $Author:bin_zhang
// $Date : 2012/10/11 15:10
// $[BUG]0010244 ADD BEGIN
package com.yly.cdr.dto.patientFav;

import java.math.BigDecimal;
import java.util.List;

public class PatientFavListSearchPra
{

    private String folderSn;

    // 患者姓名
    private String patientName;

    // 性别代码
    private String genderCode;

    public String getFolderSn()
    {
        return folderSn;
    }

    public void setFolderSn(String folderSn)
    {
        this.folderSn = folderSn;
    }

    public String getPatientName()
    {
        return patientName;
    }

    public void setPatientName(String patientName)
    {
        this.patientName = patientName;
    }

    public String getGenderCode()
    {
        return genderCode;
    }

    public void setGenderCode(String genderCode)
    {
        this.genderCode = genderCode;
    }

}

// $[BUG]0010244 ADD END
