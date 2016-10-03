package com.yly.cdr.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Transactional;

import com.founder.fasf.web.paging.PagingContext;
import com.yly.cdr.dto.ExamCVISDto;
import com.yly.cdr.dto.ExamListDto;
import com.yly.cdr.dto.LabDto;
import com.yly.cdr.dto.MedicationOrderDto;
import com.yly.cdr.dto.OrderStepDto;
import com.yly.cdr.dto.doc.DocListSearchParameters;
import com.yly.cdr.dto.drug.DrugListSearchPra;
import com.yly.cdr.dto.exam.ExamListSearchParameters;
import com.yly.cdr.dto.exam.WithdrawRecordDto;
import com.yly.cdr.dto.lab.LabListSearchParameters;
import com.yly.cdr.entity.ClinicalDocument;
import com.yly.cdr.entity.ExaminationOrder;
import com.yly.cdr.entity.ExaminationResult;
import com.yly.cdr.entity.ExaminationResultDetail;
import com.yly.cdr.entity.ExaminationResultProcedure;
import com.yly.cdr.entity.MedicalImaging;
import com.yly.cdr.entity.MedicalVisit;
import com.yly.cdr.entity.Message;

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
