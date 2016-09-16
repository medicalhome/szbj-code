package com.founder.cdr.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Transactional;

import com.founder.cdr.dto.ExamCVISDto;
import com.founder.cdr.dto.ExamListDto;
import com.founder.cdr.dto.LabDto;
import com.founder.cdr.dto.MedicationOrderDto;
import com.founder.cdr.dto.OrderStepDto;
import com.founder.cdr.dto.doc.DocListSearchParameters;
import com.founder.cdr.dto.drug.DrugListSearchPra;
import com.founder.cdr.dto.exam.ExamListSearchParameters;
import com.founder.cdr.dto.exam.WithdrawRecordDto;
import com.founder.cdr.dto.lab.LabListSearchParameters;
import com.founder.cdr.entity.ClinicalDocument;
import com.founder.cdr.entity.ExaminationOrder;
import com.founder.cdr.entity.ExaminationResult;
import com.founder.cdr.entity.ExaminationResultDetail;
import com.founder.cdr.entity.ExaminationResultProcedure;
import com.founder.cdr.entity.MedicalImaging;
import com.founder.cdr.entity.MedicalVisit;
import com.founder.cdr.entity.Message;
import com.founder.fasf.web.paging.PagingContext;

public interface PortalService
{
	
	
	/**
	 * 检索除病理以外检查列表
	 * @param patientSn
	 * @param examListSearchParameters
	 * @param pagingContext
	 * @return
	 */
    @Transactional
    ExamListDto[] selectPortalExamListSearch(String patientSn,
            ExamListSearchParameters examListSearchParameters,
            PagingContext pagingContext);
    /**
     * 根据患者序列号，文档名称，提交开始日期，提交结束日期，获得麻单或护理单列表
     * @param patientSn
     * @param docListSearchParameters
     * @param pagingContext
     * @return
     */
    List<ClinicalDocument> selectPortalAllDoc(String patientSn,
            DocListSearchParameters docListSearchParameters,
            PagingContext pagingContext);
    
    /**
     * 根据患者序列号，输液量名称获得输液（葡萄糖，氯化钠，葡萄糖氯化钠）列表
     * @param patientSn
     * @param drugListSearchPra
     * @param userSn
     * @param pagingContext
     * @return
     */
    @Transactional
    MedicationOrderDto[] selectInfusionDrugList(String patientSn,
            DrugListSearchPra drugListSearchPra,  String userSn,PagingContext pagingContext);
    
    /**
     * 获取检验报告列表
     * @param patientSn 患者内部序列号
     * @param labListSearchParameters 查询条件对象
     * @param pagingContext 分页对象
     * @return 检验报告列表对象集合
     */
    @Transactional
    public LabDto[] selectPortalLabList(String patientSn,
            LabListSearchParameters labListSearchParameters,
            PagingContext pagingContext);

    
}
