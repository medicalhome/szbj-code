package com.yly.cdr.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.founder.fasf.core.util.daohelper.entity.EntityDao;
import com.yly.cdr.core.Constants;
import com.yly.cdr.entity.Anesthesia;
import com.yly.cdr.entity.AnesthesiaEvent;
import com.yly.cdr.entity.AnesthesiaMedicalTransfuse;
import com.yly.cdr.entity.InOutDose;
import com.yly.cdr.entity.NursingOperation;
import com.yly.cdr.entity.PhysicalExamination;
import com.yly.cdr.service.AnesthesiaService;
import com.yly.cdr.service.CareService;
import com.yly.cdr.service.CommonService;
import com.yly.cdr.util.DateUtils;

@Component
public class AnesthesiaServiceImpl implements AnesthesiaService
{
    @Autowired
    private CommonService commonService;

    @Autowired
    private EntityDao entityDao;

    /**
     * 新增麻醉记录单，麻醉事件，体格检查，麻醉用药输液，入出量信息
     * @param anesthesia 麻醉记录单实体
     * @param anesthesiaEventList 麻醉事件List
     * @param physicalExaminationList 体格检查List
     * @param anesthesiaMedicalTransfuseList 麻醉用药输液List
     * @param inOutDoseList 入出量信息List
     */
    @Transactional
    public void saveAnesthesia(Anesthesia anesthesia, List<Object> anesthesiaEventList,
            List<Object> physicalExaminationList, List<Object> anesthesiaMedicalTransfuseList, List<Object> inOutDoseList)
    {
    	commonService.save(anesthesia);
        commonService.saveList(anesthesiaEventList);
        commonService.saveList(physicalExaminationList);
        commonService.saveList(anesthesiaMedicalTransfuseList);
        commonService.saveList(inOutDoseList);
    }
    
    /**
     * 删除麻醉记录单，麻醉事件，体格检查，麻醉用药输液，入出量信息
     * @param anesthesia 麻醉记录单实体
     * @param anesthesiaEventList 麻醉事件List
     * @param physicalExaminationList 体格检查List
     * @param anesthesiaMedicalTransfuseList 麻醉用药输液List
     * @param inOutDoseList 入出量信息List
     */
    @Transactional
    public void deleteAnesthesia(Anesthesia anesthesia, List<Object> anesthesiaEventList,
            List<Object> physicalExaminationList, List<Object> anesthesiaMedicalTransfuseList, List<Object> inOutDoseList,String serviceId)
    {
    	Date sysdate;
    	sysdate = DateUtils.getSystemTime();
    	if(anesthesia != null){
    		anesthesia.setDeleteFlag(Constants.DELETE_FLAG);
    		anesthesia.setDeleteTime(sysdate);
    		entityDao.update(anesthesia);
    	}
    	if(anesthesiaEventList != null && anesthesiaEventList.size()>0){
    		for(Object obj: anesthesiaEventList){
    			AnesthesiaEvent anesthesiaEvent = (AnesthesiaEvent)obj;
    			anesthesiaEvent.setDeleteFlag(Constants.DELETE_FLAG);
    			anesthesiaEvent.setDeleteTime(sysdate);
    			entityDao.update(anesthesiaEvent);
    		}
    	}
    	if(physicalExaminationList != null && physicalExaminationList.size()>0){
    		for(Object obj: physicalExaminationList){
    			PhysicalExamination physicalExamination = (PhysicalExamination)obj;
    			physicalExamination.setDeleteFlag(Constants.DELETE_FLAG);
    			physicalExamination.setDeleteTime(sysdate);
    			physicalExamination.setDeleteby(serviceId);
    			entityDao.update(physicalExamination);
    		}
    	}
    	if(anesthesiaMedicalTransfuseList != null && anesthesiaMedicalTransfuseList.size()>0){
    		for(Object obj: anesthesiaMedicalTransfuseList){
    			AnesthesiaMedicalTransfuse anesthesiaMedicalTransfuse = (AnesthesiaMedicalTransfuse)obj;
    			anesthesiaMedicalTransfuse.setDeleteFlag(Constants.DELETE_FLAG);
    			anesthesiaMedicalTransfuse.setDeleteTime(sysdate);
    			anesthesiaMedicalTransfuse.setDeleteby(serviceId);
    			entityDao.update(anesthesiaMedicalTransfuse);
    		}
    	}
    	if(inOutDoseList != null && inOutDoseList.size()>0){
    		for(Object obj: inOutDoseList){
    			InOutDose inOutDose = (InOutDose)obj;
    			inOutDose.setDeleteFlag(Constants.DELETE_FLAG);
    			inOutDose.setDeleteTime(sysdate);
    			inOutDose.setDeleteby(serviceId);
    			entityDao.update(inOutDose);
    		}
    	}
    }
}
