package com.yly.cdr.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.founder.fasf.core.util.daohelper.entity.EntityDao;
import com.founder.fasf.web.paging.PagingContext;
import com.yly.cdr.dao.PatientFavDao;
import com.yly.cdr.dto.PatientFavDto;
import com.yly.cdr.dto.PatientFolderMemoDto;
import com.yly.cdr.dto.patientFav.PatientFavListSearchPra;
import com.yly.cdr.entity.ExaminationOrder;
import com.yly.cdr.entity.PatientFav;
import com.yly.cdr.entity.PatientFavFolder;
import com.yly.cdr.service.PatientFavFolderService;
import com.yly.cdr.util.StringUtils;

@Component
public class PatientFavFolderServiceImpl implements PatientFavFolderService
{
    @Autowired
    private EntityDao entityDao;

    @Autowired
    private PatientFavDao patientFavDao;

    @Override
    @Transactional
    public int createPatientFavFolder(PatientFavFolder folder)
    {
        return entityDao.insert(folder);
    }

    @Override
    @Transactional
    public int deletePatientFavFolder(String folderId)
    {
        deleteChildFolders(folderId);
        Object favFolder = entityDao.selectById(PatientFavFolder.class,
                folderId);
        if (!StringUtils.isEmpty(folderId))
            patientFavDao.deletePatientFavs(folderId);
        
        return entityDao.delete(favFolder);
    }
    // 递归删除子文件夹和文件夹内容
    private void deleteChildFolders(String parentFolderSn){
        Map<String,Object> condition = new HashMap<String,Object>();
        condition.put("parentFolderSn", parentFolderSn);
        List<Object> results = entityDao.selectByCondition(PatientFavFolder.class, condition);
        if(results != null && results.size() > 0){
            for(Object obj:results){
                PatientFavFolder patientFavFolder = (PatientFavFolder)obj;
                String folderSn = patientFavFolder.getFolderSn().toString();
                deleteChildFolders(folderSn);
                patientFavDao.deletePatientFavs(folderSn);
                entityDao.delete(patientFavFolder);
            }
        }
    }

    @Override
    @Transactional
    public List<PatientFavFolder> selectPatientFavFolders(String userId)
    {
        List result = new ArrayList<PatientFavFolder>();
        return result = patientFavDao.selectPatientFolders(userId, null);
    }

    // $Author:bin_zhang
    // $Date : 2012/10/11 15:10
    // $[BUG]0010244 ADD BEGIN
    @Override
    @Transactional
    public List<PatientFavDto> selectPatientFavs(
            PatientFavListSearchPra patientFavListSearchPra,
            PagingContext pagingContext)
    {
        List patientList = patientFavDao.selectPatientByCondition(
                patientFavListSearchPra, pagingContext);
        return patientList;
    }

    @Override
    @Transactional
    public void deletePatients(String[] patientFavIds)
    {
        for (String patientFavId : patientFavIds)
        {
            if (!StringUtils.isEmpty(patientFavId))
            {
                Object favPerson = entityDao.selectById(PatientFav.class,
                        patientFavId);
                entityDao.delete(favPerson);
            }
        }
    }

    // $[BUG]0010244 ADD END

    // $Author:bin_zhang
    // $Date : 2012/10/16 15:10
    // $[BUG]0010398 ADD BEGIN
    @Override
    @Transactional
    public void insertPatientFavs(List<PatientFav> patientFavs)
    {
        for (PatientFav patientFav : patientFavs)
        {
            entityDao.insert(patientFav);
        }
    }

    @Override
    @Transactional
    public List<PatientFavFolder> selectPatientFavFolders(String userId,
            String patientSn)
    {
        List result = new ArrayList<PatientFavFolder>();
        return result = patientFavDao.selectPatientFolders(userId, patientSn);
    }
    // $[BUG]0010398 ADD END
    
    // $Author:bin_zhang
    // $Date : 2013/1/4 17:21
    // $[BUG]0012875 ADD BEGIN
    @Override
    @Transactional
    public List<PatientFolderMemoDto> selectPatientFolderMemo(String userId,
            String patientSn)
    {
        List result = new ArrayList<PatientFolderMemoDto>();
        return result = patientFavDao.selectPatientFolderMemo(userId, patientSn);
    }
 // $[BUG]0012875 ADD END
    

}
