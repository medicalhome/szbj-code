package com.yly.cdr.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.founder.fasf.core.util.daohelper.entity.EntityDao;
import com.founder.fasf.web.paging.PagingContext;
import com.yly.cdr.core.Constants;
import com.yly.cdr.dao.SurgicalProcedureDao;
import com.yly.cdr.dto.PhysicalExaminationDto;
import com.yly.cdr.dto.ProcedureRequestDto;
import com.yly.cdr.dto.SurgicalProcedureDto;
import com.yly.cdr.dto.procedure.ProcedureListSearchPra;
import com.yly.cdr.entity.Anesthesia;
import com.yly.cdr.entity.AnesthesiaEvent;
import com.yly.cdr.entity.ProcedureOrder;
import com.yly.cdr.entity.ProcedureParticipants;
import com.yly.cdr.entity.ProcedureRecord;
import com.yly.cdr.entity.SurgicalProcedure;
import com.yly.cdr.entity.TransferHistory;
import com.yly.cdr.service.CommonService;
import com.yly.cdr.service.SurgicalProcedureService;

@Component
public class SurgicalProcedureServiceImpl implements SurgicalProcedureService
{
    @Autowired
    private SurgicalProcedureDao SurgicalProcedureDao;

    @Autowired
    private EntityDao entityDao;

    // $Author :tong_meng
    // $Date : 2012/9/25 14:33$
    // [BUG]0009863 ADD BEGIN
    @Autowired
    private CommonService commonService;

    // [BUG]0009863 ADD END

    @Override
    @Transactional
    public SurgicalProcedureDto[] selectSurgical(String patientSn,
            ProcedureListSearchPra procedureListSearchPra,
            PagingContext pagingContext)
    {
        SurgicalProcedureDto[] Result = SurgicalProcedureDao.selectProcedureListSearchOb(
                patientSn, procedureListSearchPra, pagingContext);
        return Result;
    }
    
    @Override
    @Transactional
    public ProcedureRequestDto[] selectProcedureList(String patientSn,
            ProcedureListSearchPra procedureListSearchPra,
            PagingContext pagingContext)
    {
    	ProcedureRequestDto[] Result = SurgicalProcedureDao.selectProcedureOrdListSearchOb(
                patientSn, procedureListSearchPra, pagingContext);
        return Result;
    }
    
    // Author:chang_xuewen
    // Date : 2013/10/25 13:59
    // [BUG]0038443 ADD BEGIN
    @Override
    @Transactional
    public ProcedureOrder[] selectProcedureListInpatientView(String patientSn,
            ProcedureListSearchPra procedureListSearchPra,
            PagingContext pagingContext)
    {
    	ProcedureOrder[] Result = SurgicalProcedureDao.selectProcedureListInpatientView(
                patientSn, procedureListSearchPra, pagingContext);
        return Result;
    }
    // [BUG]0038443 ADD END
    
    @Override
    @Transactional
    public SurgicalProcedure selectProc(String procedureSn)
    {
        return (SurgicalProcedure) entityDao.selectById(
                SurgicalProcedure.class, procedureSn);
    }

    @Override
    @Transactional
    public List<ProcedureParticipants> selectProparti(String procedureSn)
    {
        Map<String, Object> osCondition = new HashMap<String, Object>();
        osCondition.put("procedureSn", procedureSn);
        List result = new ArrayList<TransferHistory>();
        return result = entityDao.selectByCondition(
                ProcedureParticipants.class, osCondition);
    }

    @Override
    @Transactional
    public Anesthesia selectAnesthesia(String anesthesiaSn)
    {
        return (Anesthesia) entityDao.selectById(Anesthesia.class, anesthesiaSn);
    }

    @Override
    @Transactional
    public List<AnesthesiaEvent> selectAnesEvent(String anesthesiaSn)
    {
        Map<String, Object> osCondition = new HashMap<String, Object>();
        osCondition.put("anesthesiaSn", anesthesiaSn);
        List result = new ArrayList<TransferHistory>();
        return result = entityDao.selectByCondition(AnesthesiaEvent.class,
                osCondition);
    }

    @Override
    @Transactional
    public PhysicalExaminationDto[] selectPhyExamination(String anesthesiaSn)
    {
        PhysicalExaminationDto[] Result = SurgicalProcedureDao.selectPhyExamnation(anesthesiaSn);
        return Result;
    }

    /**
     * 取出申请单中唯一一条数据
     * @param orderLid
     * @param patientDomain
     * @param patientLid
     * @return
     */
//    @Transactional
//    public ProcedureOrder getProOrder(String orderLid, String patientDomain,
//            String patientLid)
//    {
//        Map<String, Object> condition = new HashMap<String, Object>();
//        condition.put("orderLid", orderLid);
//        condition.put("patientDomain", patientDomain);
//        condition.put("patientLid", patientLid);
//        condition.put("deleteFlag", Constants.NO_DELETE_FLAG);
//        List result = new ArrayList<ProcedureOrder>();
//        result = entityDao.selectByCondition(ProcedureOrder.class, condition);
//        if (result.size() == 0)
//        {
//            return null;
//        }
//        return (ProcedureOrder) result.get(0);
//    }

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
            String patientLid, String organizationCode, BigDecimal visitSn)
    {
        Map<String, Object> condition = new HashMap<String, Object>();
        condition.put("requestNo", requestNo);
        condition.put("patientDomain", domainID);
        condition.put("patientLid", patientLid);
        condition.put("orgCode",organizationCode);
    	// Author：yu_yzh
    	// Date: 2016/02/29 13:53
    	// 港大允许不同就诊更新同一手术医嘱，将检索时的就诊内部序列号去除
    	// MODIFY BEGIN
//        condition.put("visitSn",visitSn);
        // MODIFY END
        condition.put("deleteFlag", Constants.NO_DELETE_FLAG);

        List<Object> result = entityDao.selectByCondition(ProcedureOrder.class,
                condition);

        return result;
    }

    // [BUG]0009718 ADD END

    /**
     * 取出申请单中
     * @return
     */
    @Transactional
    public ProcedureOrder getProcedureOrder(String orderSn)
    {
        Map<String, Object> condition = new HashMap<String, Object>();
        condition.put("orderSn", orderSn);
        condition.put("deleteFlag", Constants.NO_DELETE_FLAG);
        List result = new ArrayList<ProcedureOrder>();
        result = entityDao.selectByCondition(ProcedureOrder.class, condition);
        if (result.size() == 0)
        {
            return null;
        }
        return (ProcedureOrder) result.get(0);
    }

    /**
     * 根据就诊内部序号和医嘱号取出手术医嘱
     * */
    @Transactional
    public ProcedureOrder getProcedureOrder(String visitSn, String orderLid){
    	Map<String, Object> condition = new HashMap<String, Object>();
    	// Author：yu_yzh
    	// Date: 2016/02/29 13:53
    	// 港大允许不同就诊更新同一手术医嘱，将检索时的就诊内部序列号去除
    	// MODIFY BEGIN
    	//    	condition.put("visitSn", visitSn);
    	condition.put("orderLid", orderLid);
    	// MODIFY END
    	List result = entityDao.selectByCondition(ProcedureOrder.class, condition);
    	if(result != null && !result.isEmpty()){
    		return (ProcedureOrder) result.get(0);
    	} else {
    		return null;
    	}
    }
    
    
    /**
     * 操作记录
     * @return
     */
    @Override
    @Transactional
    public List<ProcedureRecord> getProcedureList(String procedureSn)
    {
        Map<String, Object> condition = new HashMap<String, Object>();
        condition.put("procedureSn", procedureSn);
        condition.put("deleteFlag", Constants.NO_DELETE_FLAG);
        List result = new ArrayList<ProcedureOrder>();
        result = entityDao.selectByCondition(ProcedureRecord.class, condition);
        return result;
    }

    // $Author :tong_meng
    // $Date : 2012/9/25 14:33$
    // [BUG]0009863 ADD BEGIN
    @Override
    @Transactional
    public void saveOrUpdateProRequest(
            ProcedureOrder procedureOrder,boolean proOrderExistFlag)
    {
        if (proOrderExistFlag)
        {
            entityDao.update(procedureOrder);
        }
        else
        {
            entityDao.insert(procedureOrder);            
        }
    }
    // [BUG]0009863 ADD END

    @Override
    @Transactional
    public void saveOrUpdateProList(List<ProcedureOrder> list, boolean proOrderExistFlag){
        if (proOrderExistFlag)
        {
            for(ProcedureOrder order : list){
            	entityDao.update(order);
            }
        }
        else
        {
            entityDao.insertAll(list.toArray(new ProcedureOrder[list.size()]));            
        }
    }
}
