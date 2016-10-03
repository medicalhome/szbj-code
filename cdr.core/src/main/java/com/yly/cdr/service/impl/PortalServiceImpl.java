package com.yly.cdr.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.founder.fasf.web.paging.PagingContext;
import com.yly.cdr.dao.PortalDao;
import com.yly.cdr.dto.ExamListDto;
import com.yly.cdr.dto.LabDto;
import com.yly.cdr.dto.MedicationOrderDto;
import com.yly.cdr.dto.doc.DocListSearchParameters;
import com.yly.cdr.dto.drug.DrugListSearchPra;
import com.yly.cdr.dto.exam.ExamListSearchParameters;
import com.yly.cdr.dto.lab.LabListSearchParameters;
import com.yly.cdr.entity.ClinicalDocument;
import com.yly.cdr.service.PortalService;

@Component
public class PortalServiceImpl implements PortalService
{
    @Autowired
    private PortalDao portalDao;

    //检查列表 
	@Override
	public ExamListDto[] selectPortalExamListSearch(String patientSn,
			ExamListSearchParameters examListSearchParameters,
			PagingContext pagingContext) {
		ExamListDto[] emrExamListResult = portalDao.selectPortalExamListSearch(patientSn,
                examListSearchParameters, pagingContext);
        return emrExamListResult;
	}
	//护理记录与麻单列表
	@Override
	public List<ClinicalDocument> selectPortalAllDoc(String patientSn,
			DocListSearchParameters docListSearchParameters,
			PagingContext pagingContext) {
		return portalDao.selectPortalAllDoc(patientSn, docListSearchParameters,
	                pagingContext);
	}
	//输液量统计
	@Override
	public MedicationOrderDto[] selectInfusionDrugList(String patientSn,
			DrugListSearchPra drugListSearchPra,String userSn, PagingContext pagingContext) {
		MedicationOrderDto[] DrugResult = portalDao.selectInfusionDrugList(
                patientSn, drugListSearchPra, userSn,pagingContext);

        return DrugResult;
	}
	//检验列表
	@Override
	public LabDto[] selectPortalLabList(String patientSn,
			LabListSearchParameters labListSearchParameters,
			PagingContext pagingContext) {
		return portalDao.selectPortalLabList(patientSn, labListSearchParameters,
	                pagingContext);
	}
    
}
