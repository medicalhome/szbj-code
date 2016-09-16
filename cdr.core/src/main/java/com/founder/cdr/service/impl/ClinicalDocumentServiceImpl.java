package com.founder.cdr.service.impl;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.founder.cdr.core.Constants;
import com.founder.cdr.dao.DocumentDao;
import com.founder.cdr.entity.ClinicalDocument;
import com.founder.cdr.entity.Diagnosis;
import com.founder.cdr.entity.InfectiousRecord;
import com.founder.cdr.entity.MedicalImaging;
import com.founder.cdr.entity.Message;
import com.founder.cdr.service.ClinicalDocumentService;
import com.founder.cdr.util.FileUtils;
import com.founder.fasf.core.util.daohelper.entity.EntityDao;

@Component
public class ClinicalDocumentServiceImpl implements ClinicalDocumentService
{

    @Autowired
    private EntityDao entityDao;

    @Autowired
    private DocumentDao documentDao;

    private static final Logger logger = LoggerFactory.getLogger(ClinicalDocumentServiceImpl.class);
    
    @Override
    @Transactional
    public void updateClinicalDocument(ClinicalDocument clinicalDocument,
            MedicalImaging medicalImaging, Message message, Date systemTime)
    {
        entityDao.update(clinicalDocument);
        documentDao.updateClinicalDocumentDocumentCda(message, clinicalDocument);
        if (medicalImaging != null)
        {
            Map<String, Object> condition = new HashMap<String, Object>();
            condition.put("documentSn", clinicalDocument.getDocumentSn());
            condition.put("deleteFlag", Constants.NO_DELETE_FLAG);
            List<Object> medicalImagingDeleteList = entityDao.selectByCondition(
                    MedicalImaging.class, condition);

            for (Object object : medicalImagingDeleteList)
            {
                MedicalImaging medicalImagingToDelete = (MedicalImaging) object;
                medicalImagingToDelete.setDeleteFlag(Constants.DELETE_FLAG);
                medicalImagingToDelete.setDeleteTime(systemTime);
                medicalImagingToDelete.setUpdateTime(systemTime);
                // entityDao.update(medicalImagingToDelete);
                entityDao.updatePartially(medicalImagingToDelete, "deleteFlag",
                        "updateTime", "deleteTime");
//                String filePath = Constants.IMAGE_CONTENT_URL + medicalImagingToDelete.getImagingSn() + "." + medicalImagingToDelete.getImageFormat();
                String filePath = medicalImagingToDelete.getFilePath();
                FileUtils.deleteFiles(filePath);
            }
            byte[] bt = medicalImaging.getImageContent();
            if(bt != null){
				String fileDirectory = getFileDirectory(medicalImaging);
				String fileName;
				if(Constants.HISTORY_CDA_PROCESS_FLAG){
					fileName = clinicalDocument.getDocumentLid() + "."
							+ medicalImaging.getImageFormat();
				} else {
					fileName = medicalImaging.getImagingSn() + "."
							+ medicalImaging.getImageFormat();
				}
				// 以文件形式保存
				try {
					FileUtils.saveMedicalImageToFile(bt, fileDirectory,
							fileName);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				String filePath = fileDirectory + fileName;
				medicalImaging.setImageContent(null);
				medicalImaging.setFilePath(filePath);
				entityDao.insert(medicalImaging);
            } else {
            	logger.debug("图像内容为空，documentSn={}", clinicalDocument.getDocumentSn());
            }
        }

    }

    @Override
    @Transactional
    public void saveClinicalDocument(ClinicalDocument clinicalDocument,
            MedicalImaging medicalImaging, Message message)
    {
        entityDao.insert(clinicalDocument);
        documentDao.updateClinicalDocumentDocumentCda(message, clinicalDocument);
        if (medicalImaging != null && medicalImaging.getImageContent() != null)
        {
            byte[] bt = medicalImaging.getImageContent();
            if(bt != null){
				String fileDirectory = getFileDirectory(medicalImaging);
				String fileName;
				if(Constants.HISTORY_CDA_PROCESS_FLAG){
					fileName = clinicalDocument.getDocumentLid() + "."
							+ medicalImaging.getImageFormat();
				} else {
					fileName = medicalImaging.getImagingSn() + "."
							+ medicalImaging.getImageFormat();
				}
				// 以文件形式保存
				try {
					FileUtils.saveMedicalImageToFile(bt, fileDirectory,
							fileName);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				String filePath = fileDirectory + fileName;
				medicalImaging.setImageContent(null);
				medicalImaging.setFilePath(filePath);
				entityDao.insert(medicalImaging);
            }
        } else {
        	logger.debug("图像内容为空，documentSn={}", clinicalDocument.getDocumentSn());
        }
    }
    
    /**
     * 保存CDA中图像信息
     */
    @Override
    @Transactional
    public void saveMedicalImaging(ClinicalDocument clinicalDocument, MedicalImaging medicalImaging)
    {
        if (medicalImaging != null)
        {
        	if(medicalImaging.getImageContent() != null){
        		byte[] bt = medicalImaging.getImageContent();
				String fileDirectory = getFileDirectory(medicalImaging);
				String fileName;
				if(Constants.HISTORY_CDA_PROCESS_FLAG){
					fileName = clinicalDocument.getDocumentLid() + "."
							+ medicalImaging.getImageFormat();
				} else {
					fileName = medicalImaging.getImagingSn() + "."
							+ medicalImaging.getImageFormat();
				}
				// 以文件形式保存
				try {
					FileUtils.saveMedicalImageToFile(bt, fileDirectory,
							fileName);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				String filePath = fileDirectory + fileName;
				medicalImaging.setImageContent(null);
				medicalImaging.setFilePath(filePath);
				entityDao.insert(medicalImaging);
        	} else {
        		logger.debug("图像内容为空，documentSn={}", medicalImaging.getDocumentSn());
        	}           
        } else {
        	logger.debug("图像内容为空");
        }
    }

    /**
     * 根据文档本地ID和患者本地ID获取病历文档
     * @param documentLid 文档本地ID
     * @param patientLid 患者本地ID
     * @param serviceId 服务ID
     * @param organizationCode 医疗机构编码
     * @return 病历文档
     */
    @Transactional
    public ClinicalDocument selectDocByLid(String documentLid, String patientLid, String serviceId,String organizationCode)
    {
        Map<String, Object> condition = new HashMap<String, Object>();
        condition.put("documentLid", documentLid);
        condition.put("patientLid", patientLid);
        // Author :jia_yanqing
        // Date : 2013/11/11 10:09
        // [BUG]0038477 ADD BEGIN
        condition.put("serviceId", serviceId);
        // [BUG]0038477 ADD END
        condition.put("deleteFlag", Constants.NO_DELETE_FLAG);
        condition.put("orgCode", organizationCode);
        List<Object> docs = entityDao.selectByCondition(ClinicalDocument.class,
                condition);
        return docs != null && docs.size() > 0 ? (ClinicalDocument) docs.get(0)
                : null;
    }
    
    // Author :jia_yanqing
    // Date : 2013/3/5 11:30
    // [BUG]0042714 ADD BEGIN
    @Transactional
    public ClinicalDocument selectDocByLidAndOrg(String documentLid, String serviceId, String organizationCode)
    {
        Map<String, Object> condition = new HashMap<String, Object>();
        condition.put("documentLid", documentLid);
        condition.put("serviceId", serviceId);
        condition.put("deleteFlag", Constants.NO_DELETE_FLAG);
        condition.put("orgCode", organizationCode);
        List<Object> docs = entityDao.selectByCondition(ClinicalDocument.class,
                condition);
        return docs != null && docs.size() > 0 ? (ClinicalDocument) docs.get(0)
                : null;
    }
    
    /*@Transactional
    public InfectiousRecord selectIRByLid(String documentLid, String organizationCode)
    {
        Map<String, Object> condition = new HashMap<String, Object>();
        condition.put("documentLid", documentLid);
        condition.put("deleteFlag", Constants.NO_DELETE_FLAG);
        condition.put("orgCode", organizationCode);
        List<Object> irList = entityDao.selectByCondition(InfectiousRecord.class,
                condition);
        return irList != null && irList.size() > 0 ? (InfectiousRecord) irList.get(0)
                : null;
    }*/
    // [BUG]0042714 ADD END

    @Transactional
    public List<Object> selectMedicalImageByDocSn(String documentSn)
    {
        Map<String, Object> condition = new HashMap<String, Object>();
        condition.put("documentSn", documentSn);
        condition.put("deleteFlag", Constants.NO_DELETE_FLAG);
        return entityDao.selectByCondition(MedicalImaging.class, condition);
    }
    
    @Transactional
    public List<Object> selectDiagnosisByDocSn(String documentSn)
    {
        Map<String, Object> condition = new HashMap<String, Object>();
        condition.put("documentSn", documentSn);
        condition.put("deleteFlag", Constants.NO_DELETE_FLAG);
        return entityDao.selectByCondition(Diagnosis.class, condition);
    }

    @Transactional
    public void deleteClinicalDocument(ClinicalDocument clinicalDocument)
    {
        String serviceId = clinicalDocument.getServiceId();
        List<Object> docImgs = selectMedicalImageByDocSn(clinicalDocument.getDocumentSn().toString());
        // $Author:wei_peng
        // $Date:2013/9/29 14:19
        // $[BUG]0037737 MODIFY BEGIN
        if (Constants.INPATIENT_RECORD_SERVICE_ID.equals(serviceId)
            || Constants.INPATIENT_FRONTPAGE_SERVICE_ID.equals(serviceId)
            || Constants.INPATIENT24_SERVICE_ID.equals(serviceId)
            || Constants.DEATHRECORD24_SERVICE_ID.equals(serviceId))
        {
            entityDao.delete(clinicalDocument);
            for (Object obj : docImgs)
            {
                entityDao.delete(obj);
            }
            // 删除病案首页关联出院诊断
            if(Constants.INPATIENT_FRONTPAGE_SERVICE_ID.equals(serviceId)){
            	List<Object> dignos = selectDiagnosisByDocSn(clinicalDocument.getDocumentSn().toString());
            	for (Object obj : dignos)
                {
                    entityDao.delete(obj);
                }
            }
        }
        else
        {
            entityDao.update(clinicalDocument);
            for (Object obj : docImgs)
            {
                MedicalImaging medicalImaging = (MedicalImaging) obj;
                medicalImaging.setDeleteby(clinicalDocument.getDeleteby());
                medicalImaging.setDeleteFlag(Constants.DELETE_FLAG);
                medicalImaging.setDeleteTime(clinicalDocument.getDeleteTime());
                medicalImaging.setUpdateTime(clinicalDocument.getUpdateTime());
                entityDao.update(medicalImaging);
            }
        }
        // $[BUG]0037737 MODIFY END
        if(docImgs != null && !docImgs.isEmpty()){
        	String[] filePaths = new String[docImgs.size()];
        	int i = 0;
        	for(Object obj : docImgs){
        		MedicalImaging mi = (MedicalImaging) obj;
//        		String filePath = Constants.IMAGE_CONTENT_URL + mi.getImagingSn() + "." + mi.getImageFormat();
        		String filePath = mi.getFilePath();
        		filePaths[i++] = filePath;
        	}
        	FileUtils.deleteFiles(filePaths);
        }
        
    }
    
    public void deleteInfectiousRecord(InfectiousRecord infectiousRecord)
    {
        entityDao.update(infectiousRecord);
    }

	@Override
	public void updateMedicalImaging(ClinicalDocument clinicalDocument,MedicalImaging medicalImaging,
			Date systemTime) {
		if (medicalImaging != null)
        {
            Map<String, Object> condition = new HashMap<String, Object>();
            condition.put("documentSn", clinicalDocument.getDocumentSn());
            condition.put("deleteFlag", Constants.NO_DELETE_FLAG);
            List<Object> medicalImagingDeleteList = entityDao.selectByCondition(
                    MedicalImaging.class, condition);

            for (Object object : medicalImagingDeleteList)
            {
                MedicalImaging medicalImagingToDelete = (MedicalImaging) object;
                medicalImagingToDelete.setDeleteFlag(Constants.DELETE_FLAG);
                medicalImagingToDelete.setDeleteTime(systemTime);
                medicalImagingToDelete.setUpdateTime(systemTime);
                // entityDao.update(medicalImagingToDelete);
                entityDao.updatePartially(medicalImagingToDelete, "deleteFlag",
                        "updateTime", "deleteTime");
//                String filePath = Constants.IMAGE_CONTENT_URL + medicalImagingToDelete.getImagingSn() + "." + medicalImagingToDelete.getImageFormat();
                String filePath = medicalImagingToDelete.getFilePath();
                FileUtils.deleteFiles(filePath);
            }
            byte[] bt = medicalImaging.getImageContent();
            if(bt != null){
            	
            	String fileDirectory = getFileDirectory(medicalImaging);
            	String fileName;
            	if(Constants.HISTORY_CDA_PROCESS_FLAG){
            		fileName = clinicalDocument.getDocumentLid() + "."
            				+ medicalImaging.getImageFormat();
            	} else {
            		fileName = medicalImaging.getImagingSn() + "."
            				+ medicalImaging.getImageFormat();
            	}
            	// 以文件形式保存
                try {
    				FileUtils.saveMedicalImageToFile(bt, fileDirectory, fileName);
    			} catch (IOException e) {
    				// TODO Auto-generated catch block
    				e.printStackTrace();
    			}
                String filePath = fileDirectory + fileName;
                medicalImaging.setImageContent(null);
                medicalImaging.setFilePath(filePath);
                entityDao.insert(medicalImaging);
            } else {
            	logger.debug("图像内容为空，documentSn={}", clinicalDocument.getDocumentSn());
            }
        } else {
        	logger.debug("图像内容为空，documentSn={}", clinicalDocument.getDocumentSn());
        }
	}
	
	private String getFileDirectory(MedicalImaging medicalImaging){
    	// Author: yu_yzh
    	// Date: 2016/8/30 8:57
    	// [BUG] 0081389 CDA历史数据迁移处理 MODIFY BEGIN
		String serviceId = medicalImaging.getCreateby();
    	String fileDirectory;
    	if(Constants.HISTORY_CDA_PROCESS_FLAG){
    		fileDirectory = Constants.IMAGE_CONTENT_URL + serviceId + "/";
    		
    	} else {
        	Date date = medicalImaging.getCreateTime();
        	Calendar c = Calendar.getInstance();
        	c.setTime(date);
        	int year = c.get(Calendar.YEAR);
        	int month = c.get(Calendar.MONTH) + 1;
        	int day = c.get(Calendar.DAY_OF_MONTH);
    		fileDirectory = Constants.IMAGE_CONTENT_URL + year + "/" + month + "/" + day + "/" + serviceId + "/";
    	}
    	// [BUG] 0081389 CDA历史数据迁移处理 MODIFY END
    	return fileDirectory;
	}
}
