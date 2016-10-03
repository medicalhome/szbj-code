package com.yly.cdr.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.yly.cdr.entity.NursingOperation;
import com.yly.cdr.entity.PhysicalExamination;

public interface CareService
{
    @Transactional
    public void saveNurPhyExam(List<Object> nursingOperationList,
            List<Object> physicalExamList);

    @Transactional
    public void saveNursingPhyExam(NursingOperation nursingOpreation,
            List<List<Object>> physicalExamList);
    
    // Author :jia_yanqing
    // Date : 2013/1/17 13:33
    // [BUG]0013379 ADD BEGIN
    @Transactional
    public void updateNurPhyExam(List<Object> nurOperationDelList,List<Object> physicalExamDelList,List<Object> nursingOperationList,
            List<Object> physicalExamList, String serviceId);
    // [BUG]0013379 ADD END
    
    // Author :chang_xuewen
    // Date : 2013/11/27 15:00
    // [BUG]0039754 ADD BEGIN
    @Transactional
    public void deleteNurPhyExam(List<Object> nurOperationDelList,List<Object> physicalExamDelList,String serviceId);
    // [BUG]0039754 ADD END
}
