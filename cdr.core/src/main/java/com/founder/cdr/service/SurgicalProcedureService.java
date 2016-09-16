/**
 * Copryright
 */
package com.founder.cdr.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.founder.cdr.dto.PhysicalExaminationDto;
import com.founder.cdr.dto.ProcedureRequestDto;
import com.founder.cdr.dto.SurgicalProcedureDto;
import com.founder.cdr.dto.procedure.ProcedureListSearchPra;
import com.founder.cdr.entity.Anesthesia;
import com.founder.cdr.entity.AnesthesiaEvent;
import com.founder.cdr.entity.ProcedureOrder;
import com.founder.cdr.entity.ProcedureParticipants;
import com.founder.cdr.entity.ProcedureRecord;
import com.founder.cdr.entity.SurgicalProcedure;
import com.founder.fasf.web.paging.PagingContext;

/**
 * 
 * @author guo_hongyan
 *
 */
public interface SurgicalProcedureService
{
    /**
     * 
     * [FUN]V05-101手术列表
     * @version 1.0, 2012/03/012  
     * @author 张彬
     * @since 1.0
     * 
     */
    @Transactional
    SurgicalProcedureDto[] selectSurgical(String patientSn,
            ProcedureListSearchPra procedureListSearchPra,
            PagingContext pagingContext);
    
    @Transactional
    ProcedureRequestDto[] selectProcedureList(String patientSn,
            ProcedureListSearchPra procedureListSearchPra,
            PagingContext pagingContext);

    @Transactional
    SurgicalProcedure selectProc(String procedureSn);

    @Transactional
    List<ProcedureParticipants> selectProparti(String procedureSn);

    @Transactional
    Anesthesia selectAnesthesia(String anesthesiaSn);

    @Transactional
    List<AnesthesiaEvent> selectAnesEvent(String anesthesiaSn);

    @Transactional
    PhysicalExaminationDto[] selectPhyExamination(String anesthesiaSn);

//    @Transactional
//    ProcedureOrder getProOrder(String orderLid, String domainID,
//            String patientLid);

    // $Author :jin_peng
    // $Date : 2012/9/17 15:07$
    // [BUG]0009718 ADD BEGIN
    /**
     * 通过申请单号，域ID，患者本地ID获取相应的手术申请信息集合
     * @param requestNo 申请单号
     * @param domainID 域ID
     * @param patientLid 患者本地ID
     * @param organizationCode 医疗机构编码
     * @return 手术申请信息对象集合
     */
    @Transactional
    public List<Object> getProRequest(String requestNo, String domainID,
            String patientLid,String organizationCode, BigDecimal visitSn);

    // [BUG]0009718 ADD END
    @Transactional
    ProcedureOrder getProcedureOrder(String orderSn);

    @Transactional
    List<ProcedureRecord> getProcedureList(String procedureSn);

    // $Author :tong_meng
    // $Date : 2012/9/25 14:33$
    // [BUG]0009863 ADD BEGIN
    /**
     * 
     * @param proOrderExistFlag
     * @param procedureOrder
     */
    @Transactional
    void saveOrUpdateProRequest(ProcedureOrder procedureOrder,
            boolean proOrderExistFlag);
    // [BUG]0009863 ADD END

    // Author:chang_xuewen
    // Date : 2013/10/25 13:59
    // [BUG]0038443 ADD BEGIN
    ProcedureOrder[] selectProcedureListInpatientView(String patientSn,
			ProcedureListSearchPra procedureListSearchPra,
			PagingContext pagingContext);
	// [BUG]0038443 ADD END
    
    @Transactional
    public void saveOrUpdateProList(List<ProcedureOrder> list, boolean proOrderExistFlag);
    
    @Transactional
    public ProcedureOrder getProcedureOrder(String visitSn, String orderLid);
}
