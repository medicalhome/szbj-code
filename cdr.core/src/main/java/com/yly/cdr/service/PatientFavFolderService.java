package com.yly.cdr.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Transactional;

import com.founder.fasf.web.paging.PagingContext;
import com.yly.cdr.dto.AccessControlDto;
import com.yly.cdr.dto.PatientCrossIndexDto;
import com.yly.cdr.dto.PatientDto;
import com.yly.cdr.dto.PatientFavDto;
import com.yly.cdr.dto.PatientFolderMemoDto;
import com.yly.cdr.dto.patient.PatientListSearchPra;
import com.yly.cdr.dto.patient.RecentPatientListSearchPra;
import com.yly.cdr.dto.patientFav.PatientFavListSearchPra;
import com.yly.cdr.entity.AllergicHistory;
import com.yly.cdr.entity.Patient;
import com.yly.cdr.entity.PatientAddress;
import com.yly.cdr.entity.PatientContact;
import com.yly.cdr.entity.PatientCredential;
import com.yly.cdr.entity.PatientCrossIndex;
import com.yly.cdr.entity.PatientFav;
import com.yly.cdr.entity.PatientFavFolder;
import com.yly.cdr.entity.PatientTemp;
import com.yly.cdr.entity.RiskInformation;
import com.yly.cdr.entity.SocialInsurance;

public interface PatientFavFolderService
{
    /**
     * 创建新的文件夹名称
     * @param folderName 文件加名称
     */
    public int createPatientFavFolder(PatientFavFolder folder);

    /**
     * 删除新的文件夹名称
     * @param folderSn 文件加唯一标识
     */
    public int deletePatientFavFolder(String folderId);

    public List<PatientFavFolder> selectPatientFavFolders(String userId);

    // $Author:bin_zhang
    // $Date : 2012/10/11 15:10
    // $[BUG]0010244 ADD BEGIN
    public List<PatientFavDto> selectPatientFavs(
            PatientFavListSearchPra patientFavListSearchPra,
            PagingContext pagingContext);

    public void deletePatients(String[] patientFavIds);

    // $[BUG]0010244 ADD END

    // $Author:bin_zhang
    // $Date : 2012/10/16 15:10
    // $[BUG]0010398 ADD BEGIN
    public void insertPatientFavs(List<PatientFav> patientFavs);

    public List<PatientFavFolder> selectPatientFavFolders(String userId,
            String patientSn);
    
    // $[BUG]0010398 ADD END
    // $Author:bin_zhang
    // $Date : 2013/1/4 17:21
    // $[BUG]0012875 ADD BEGIN
    public List<PatientFolderMemoDto> selectPatientFolderMemo(String userId, String patientSn);
    // $[BUG]0012875 ADD END
}
