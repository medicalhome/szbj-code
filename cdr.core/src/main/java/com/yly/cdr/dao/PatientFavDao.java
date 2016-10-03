package com.yly.cdr.dao;

import java.util.List;

import com.founder.fasf.core.util.daohelper.annotation.Arguments;
import com.founder.fasf.web.paging.PagingContext;
import com.yly.cdr.dto.PatientFavDto;
import com.yly.cdr.dto.PatientFolderMemoDto;
import com.yly.cdr.dto.patientFav.PatientFavListSearchPra;
import com.yly.cdr.entity.PatientFavFolder;

public interface PatientFavDao
{

    // $Author:bin_zhang
    // $Date : 2012/10/11 15:10
    // $[BUG]0010244 ADD BEGIN
    /**
     * 根据检索条件，查找文件夹中的患者信息
     * @param patientFavListSearchPra 患者检索条件
     * @param pagingContext
     * @return 
     */
    @Arguments({ "patientFavListSearchPra" })
    List<PatientFavDto> selectPatientByCondition(
            PatientFavListSearchPra patientFavListSearchPra,
            PagingContext pagingContext);

    @Arguments({ "userId", "patientSn" })
    List<PatientFavFolder> selectPatientFolders(String userId, String patientSn);
    // $Author:bin_zhang
    // $Date : 2013/1/4 17:21
    // $[BUG]0012875 ADD BEGIN
    @Arguments({ "userId", "patientSn" })
    List<PatientFolderMemoDto> selectPatientFolderMemo(String userId, String patientSn);
   // $[BUG]0012875 ADD END
    @Arguments({ "folderId" })
    void deletePatientFavs(String folderId);
    // $[BUG]0010244 ADD END
}
