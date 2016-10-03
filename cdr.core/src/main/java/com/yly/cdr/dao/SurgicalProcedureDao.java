package com.yly.cdr.dao;

import com.founder.fasf.core.util.daohelper.annotation.Arguments;
import com.founder.fasf.web.paging.PagingContext;
import com.yly.cdr.dto.PhysicalExaminationDto;
import com.yly.cdr.dto.ProcedureRequestDto;
import com.yly.cdr.dto.SurgicalProcedureDto;
import com.yly.cdr.dto.procedure.ProcedureListSearchPra;
import com.yly.cdr.entity.ProcedureOrder;

public interface SurgicalProcedureDao
{

    /**
     * 
     * [FUN]V05-101药物医嘱列表
     * @version 1.0, 2012/03/012  
     * @author 张彬
     * @since 1.0
     *  * 普通搜索
     * 
     * 
     */

    @Arguments({ "patientSn", "operationName", "operationDate1",
            "operationDate2", "surgicalDept", "participantName1",
            "participantName2", "procedureLevel", "workload", "healingGrade" })
    public SurgicalProcedureDto[] selectSurgicalProcedureSearch(
            String patientSn, ProcedureListSearchPra procedureListSearchPra,
            PagingContext pagingContext);

    @Arguments({ "patientSn", "procedureListSearchPra" })
    public SurgicalProcedureDto[] selectProcedureListSearchOb(String patientSn,
            ProcedureListSearchPra procedureListSearchPra,
            PagingContext pagingContext);
    
    @Arguments({ "patientSn", "procedureListSearchPra" })
    public ProcedureRequestDto[] selectProcedureOrdListSearchOb(String patientSn,
            ProcedureListSearchPra procedureListSearchPra,
            PagingContext pagingContext);
    
    // Author:chang_xuewen
    // Date : 2013/10/25 13:59
    // [BUG]0038443 ADD BEGIN
    @Arguments({ "patientSn", "procedureListSearchPra" })
    public ProcedureOrder[] selectProcedureListInpatientView(String patientSn,
            ProcedureListSearchPra procedureListSearchPra,
            PagingContext pagingContext);
    // [BUG]0038443 ADD END
    
    /**
    * V05-701:手术列表
    * V05-703:手术申请详细
    * author:黄洁玉
    * @since 1.0 
    */
    @Arguments({ "requstSn" })
    public ProcedureRequestDto[] selectProcedureRequestDetail(String requestSn);

    @Arguments({ "anesthesiaSn" })
    public PhysicalExaminationDto[] selectPhyExamnation(String anesthesiaSn);

}
