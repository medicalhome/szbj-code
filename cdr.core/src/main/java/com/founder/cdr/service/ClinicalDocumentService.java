package com.founder.cdr.service;

import java.util.Date;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.founder.cdr.entity.ClinicalDocument;
import com.founder.cdr.entity.InfectiousRecord;
import com.founder.cdr.entity.MedicalImaging;
import com.founder.cdr.entity.Message;

public interface ClinicalDocumentService
{

    @Transactional
    public void updateClinicalDocument(ClinicalDocument clinicalDocument,
            MedicalImaging medicalImaging, Message message, Date systemTime);
    
    @Transactional
    public void updateMedicalImaging(ClinicalDocument clinicalDocument,MedicalImaging medicalImaging,  Date systemTime);

    @Transactional
    public void saveClinicalDocument(ClinicalDocument clinicalDocument,
            MedicalImaging medicalImaging, Message message);
    
    /**
     * 保存图像信息
     * @param medicalImaging
     */
    @Transactional
    public void saveMedicalImaging(ClinicalDocument clinicalDocument, MedicalImaging medicalImaging);

    @Transactional
    public void deleteClinicalDocument(ClinicalDocument clinicalDocument);
    
    @Transactional
    public void deleteInfectiousRecord(InfectiousRecord infectiousRecord);

    /**
     * 根据文档本地ID和患者本地ID获取病历文档
     * @param documentLid 文档本地ID
     * @param patientLid 患者本地ID
     * @param serviceId 服务ID
     * @param organizationCode 医疗机构编码
     * @return 病历文档
     */
    @Transactional
    public ClinicalDocument selectDocByLid(String documentLid,String patientLid,String serviceId,String organizationCode);
    
    @Transactional
    public ClinicalDocument selectDocByLidAndOrg(String documentLid, String serviceId, String organizationCode);
    
    
    /*@Transactional
    public InfectiousRecord selectIRByLid(String documentLid, String organizationCode);*/
    
    @Transactional
    public List<Object> selectMedicalImageByDocSn(String documentSn);
}
