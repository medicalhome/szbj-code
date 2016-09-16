package com.founder.cdr.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.founder.cdr.dao.PortalDao;
import com.founder.cdr.dto.ExamListDto;
import com.founder.cdr.dto.LabDto;
import com.founder.cdr.dto.MedicationOrderDto;
import com.founder.cdr.dto.doc.DocListSearchParameters;
import com.founder.cdr.dto.drug.DrugListSearchPra;
import com.founder.cdr.dto.exam.ExamListSearchParameters;
import com.founder.cdr.dto.lab.LabListSearchParameters;
import com.founder.cdr.entity.ClinicalDocument;
import com.founder.cdr.service.PortalService;
import com.founder.fasf.web.paging.PagingContext;

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
