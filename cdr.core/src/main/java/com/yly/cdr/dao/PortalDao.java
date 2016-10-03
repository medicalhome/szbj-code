package com.yly.cdr.dao;

import java.util.List;

import com.founder.fasf.core.util.daohelper.annotation.Arguments;
import com.founder.fasf.web.paging.PagingContext;
import com.yly.cdr.dto.ExamListDto;
import com.yly.cdr.dto.LabDto;
import com.yly.cdr.dto.MedicationOrderDto;
import com.yly.cdr.dto.doc.DocListSearchParameters;
import com.yly.cdr.dto.drug.DrugListSearchPra;
import com.yly.cdr.dto.exam.ExamListSearchParameters;
import com.yly.cdr.dto.lab.LabListSearchParameters;
import com.yly.cdr.entity.ClinicalDocument;

public interface PortalDao
{
    //检索除病理以外检查列表
    @Arguments({ "patientSn", "examListSearchParameters" })
    public ExamListDto[] selectPortalExamListSearch(String patientSn,
            ExamListSearchParameters examListSearchParameters,
            PagingContext pagingContext);
    
    //根据患者序列号，文档名称，提交开始日期，提交结束日期，获得麻单或护理单列表
    @Arguments({ "patientSn", "docListSearchParameters" })
    List<ClinicalDocument> selectPortalAllDoc(String patientSn,
            DocListSearchParameters docListSearchParameters,
            PagingContext pagingContext);
    
    //根据患者序列号，输液量名称获得输液（葡萄糖，氯化钠，葡萄糖氯化钠）列表
    @Arguments({ "patientSn", "drugListSearchPra", "userSn"})
    MedicationOrderDto[] selectInfusionDrugList(String patientSn,
			DrugListSearchPra drugListSearchPra, String userSn,PagingContext pagingContext);
    
  //检验列表
    @Arguments({ "patientSn", "labListSearchParameters" })
    public LabDto[] selectPortalLabList(String patientSn, LabListSearchParameters labListSearchParameters,
            PagingContext pagingContext);
}
