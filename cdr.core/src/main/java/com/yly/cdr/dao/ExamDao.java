package com.yly.cdr.dao;

import java.math.BigDecimal;
import java.util.List;

import com.founder.fasf.core.util.daohelper.annotation.Arguments;
import com.founder.fasf.web.paging.PagingContext;
import com.yly.cdr.dto.ExamCVISDto;
import com.yly.cdr.dto.ExamListDto;
import com.yly.cdr.dto.exam.ExamListSearchParameters;
import com.yly.cdr.dto.exam.WithdrawRecordDto;
import com.yly.cdr.entity.ExaminationOrder;
import com.yly.cdr.entity.ExaminationResultDetail;
import com.yly.cdr.entity.MedicalImaging;

public interface ExamDao
{
    /**
     * 
     * [FUN]V07-101检查列表
     * @version 1.0, 2012/03/15  
     * @author 郭红燕
     * @since 1.0
     * 高级搜索
     * 
     */
    @Arguments({ "patientSn", "examListSearchParameters" })
    public ExamListDto[] selectExamListSearch(String patientSn,
            ExamListSearchParameters examListSearchParameters,
            PagingContext pagingContext);

    // $Author:bin_zhang
    // $Date:2012/9/28 10:42
    // $[BUG]BUG0010082 ADD BEGIN
    @Arguments({ "visitSn" })
    public ExamListDto[] selectExamList(String visitSn);

    // $[BUG]BUG0010082 ADD END
    
    // $Author:chang_xuewen
    // $Date:2013/07/01 17:10
    // $[BUG]0032417 ADD BEGIN
    /**
     * 增加pagingContext满足门诊视图翻页需求
     * @author chang_xuewen
     * @param visitSn
     * @param pagingContext
     * @return
     */
    @Arguments({ "visitSn" })
    public ExamListDto[] selectExamList(String visitSn,PagingContext pagingContext);
    // $[BUG]0032417 ADD END
    
    @Arguments({ "reportSn", "itemSn" })
    public ExamListDto[] selectExamDetail(String reportSn, String itemSn);

    // Author:jin_peng
    // Date:2013/01/29 15:57
    // [BUG]0013729 MODIFY BEGIN
    @Arguments({ "patientSn", "examinationRegion", "examinationItem", "orderSn" })
    public ExamListDto[] selectRelatedExam(String patientSn,
            String examinationRegion, String examinationItem, String orderSn);

    // [BUG]0013729 MODIFY END

    @Arguments({ "reportSn" })
    public ExamListDto[] selectExamOrderDetail(String reportSn);

    @Arguments({ "reportSn" })
    public WithdrawRecordDto[] selectWithdrawRecord(String reportSn);

    @Arguments({ "imagingSn" })
    public MedicalImaging[] selectImageBySn(String imagingSn);

    // $Author :jin_peng
    // $Date : 2012/11/23 13:59$
    // [BUG]0011864 MODIFY BEGIN
    // $Author :jin_peng
    // $Date : 2012/09/20 14:47$
    // [BUG]0009691 MODIFY BEGIN
    /**
     * 根据本地医嘱ID查找检查医嘱
     * @param orderLidList 检查医嘱List
     * @param outpatientDomain 域ID为门诊
     */
    @Arguments({ "patientDomain", "patientLid", "orderLidList", "orgCode",
            "outpatientDomain",  "visitSn"})
    public ExaminationOrder[] getExamOrderByOrderLids(
            String patientDomain, String patientLid, List<String> orderLidList,String orgCode,
            String outpatientDomain, BigDecimal visitSn);

    // [BUG]0009691 MODIFY END
    // [BUG]0011864 MODIFY END

    // $Author :jin_peng
    // $Date : 2012/11/23 13:59$
    // [BUG]0011864 MODIFY BEGIN
    // $Author :jin_peng
    // $Date : 2012/10/19 17:47$
    // [BUG]0010594 ADD BEGIN
    /**
     * 根据本地医嘱ID更新相应的手术医嘱的检查报告内部序列号
     * @param reportSn 检查报告内部序列号
     * @param orderLidList 手术医嘱List
     * @param outpatientDomain 域ID为门诊
     */
    @Arguments({ "reportSn", "patientDomain", "patientLid", "orderLidList",
            "outpatientDomain" })
    public void updateProcOrderReportSn(BigDecimal reportSn,
            String patientDomain, String patientLid, List<String> orderLidList,
            String outpatientDomain);

    // [BUG]0010594 ADD END
    // [BUG]0011864 MODIFY END
    
    @Arguments({"patientSn","itemClass", "itemSn","reportSn","DSA"})
    public ExamCVISDto selectExamCVISDetail(String patientSn, String itemClass,String itemSn,String reportSn, String examItemclassCoronaryAngiography);

    @Arguments({"itemSn","itemClass","examResultProcedureSn"})
    public List<ExaminationResultDetail> selectExamCVISItem(String itemSn, String itemClass, String examResultProcedureSn);
    
    @Arguments({"reportSn"})
    BigDecimal selectDocumentSnByResultSn(String reportSn);

}
