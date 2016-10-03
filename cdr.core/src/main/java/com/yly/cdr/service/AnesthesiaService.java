package com.yly.cdr.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.yly.cdr.entity.Anesthesia;

public interface AnesthesiaService
{
    @Transactional
    public void saveAnesthesia(Anesthesia anesthesia, List<Object> anesthesiaEventList,
            List<Object> physicalExaminationList, List<Object> anesthesiaMedicalTransfuseList, List<Object> inOutDoseList);
    @Transactional
    public void deleteAnesthesia(Anesthesia anesthesia, List<Object> anesthesiaEventList,
            List<Object> physicalExaminationList, List<Object> anesthesiaMedicalTransfuseList, List<Object> inOutDoseList,String serviceId);
}
