package com.founder.cdr.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Transactional;

import com.founder.cdr.dto.AccessControlDto;
import com.founder.cdr.dto.PatientCrossIndexDto;
import com.founder.cdr.dto.PatientDto;
import com.founder.cdr.dto.PatientFavDto;
import com.founder.cdr.dto.PatientFolderMemoDto;
import com.founder.cdr.dto.patient.PatientListSearchPra;
import com.founder.cdr.dto.patient.RecentPatientListSearchPra;
import com.founder.cdr.dto.patientFav.PatientFavListSearchPra;
import com.founder.cdr.entity.AllergicHistory;
import com.founder.cdr.entity.Patient;
import com.founder.cdr.entity.PatientAddress;
import com.founder.cdr.entity.PatientContact;
import com.founder.cdr.entity.PatientCredential;
import com.founder.cdr.entity.PatientCrossIndex;
import com.founder.cdr.entity.PatientFav;
import com.founder.cdr.entity.PatientFavFolder;
import com.founder.cdr.entity.PatientTemp;
import com.founder.cdr.entity.RiskInformation;
import com.founder.cdr.entity.SocialInsurance;
import com.founder.fasf.web.paging.PagingContext;

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
