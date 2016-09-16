package com.founder.cdr.dto;

import java.math.BigDecimal;
import java.util.List;

public class PatientFavFolderDto
{
    private BigDecimal folderSn;

    private String folderName;
    
    private List<PatientFavFolderDto> childrenFolders;

    public BigDecimal getFolderSn()
    {
        return folderSn;
    }

    public void setFolderSn(BigDecimal folderSn)
    {
        this.folderSn = folderSn;
    }

    public String getFolderName()
    {
        return folderName;
    }

    public void setFolderName(String folderName)
    {
        this.folderName = folderName;
    }

    public List<PatientFavFolderDto> getChildrenFolders()
    {
        return childrenFolders;
    }

    public void setChildrenFolders(List<PatientFavFolderDto> childrenFolders)
    {
        this.childrenFolders = childrenFolders;
    }
    
}
