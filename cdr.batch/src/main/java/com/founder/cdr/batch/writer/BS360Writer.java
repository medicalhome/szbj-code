package com.founder.cdr.batch.writer;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.founder.cdr.core.Constants;
import com.founder.cdr.entity.BloodRecord;
import com.founder.cdr.entity.BloodRecordMenu;
import com.founder.cdr.entity.BloodRecordSpecialRequest;
import com.founder.cdr.entity.BloodRequest;
import com.founder.cdr.entity.ExamResult;
import com.founder.cdr.entity.MedicalVisit;
import com.founder.cdr.hl7.dto.BloodRecordDto;
import com.founder.cdr.hl7.dto.BloodRecordSpecialRequestDto;
import com.founder.cdr.hl7.dto.ExamResultDto;
import com.founder.cdr.hl7.dto.bs360.BS360;
import com.founder.cdr.util.DateUtils;
import com.founder.cdr.util.StringUtils;
import com.founder.fasf.core.util.daohelper.entity.EntityDao;

@Component(value = "bs360Writer")
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class BS360Writer implements BusinessWriter<BS360> {

	@Autowired
    private EntityDao entityDao;
	
	private Logger logger = LoggerFactory.getLogger(BS360Writer.class);
	
	private BigDecimal visitSn = null;
	
	private BigDecimal patientSn = null;
	
	private BigDecimal requestSn = null;
	
	private String serviceId = "BS360";
	
	// 人民版标版区分处理标识
    private static final String TURN_ON_FLAG = "1";
    
	/**
     * 数据业务校验
     */
	@Override
	public boolean validate(BS360 bs360) {
		//为兼容,当消息中医疗机构ID或者医疗机构名称为空时，默认是北京大学人民医院
		if(StringUtils.isEmpty(bs360.getHospitalCode())){
			// $Author :yang_mingjie
        	// $Date : 2014/06/26 10:09$
        	// [BUG]0045630 MODIFY BEGIN 
			bs360.setHospitalCode(Constants.HOSPITAL_CODE);
			bs360.setHospitalName(Constants.HOSPITAL_NAME);
			//[BUG]0045630 MODIFY END
        }  
		return true;
	}

	@Override
	public boolean checkDependency(BS360 bs360, boolean falg) {
		//1. 检查患者是否存在就诊信息
		Map<String, Object> p = new HashMap<String, Object>();
		p.put("patientLid", bs360.getPatientLid());
		p.put("patientDomain", bs360.getDomainLid());
		if(TURN_ON_FLAG.equals(Constants.MEDICAL_VISIT_CONFIM_LOGIC_TYPE)){
			p.put("visitTimes", bs360.getTimes());
		}else{
			p.put("visitOrdNo", bs360.getVisitOrdNo());
			p.put("visitTypeCode", bs360.getVisitTypeCode());
		}
		p.put("orgCode", bs360.getHospitalCode());
		p.put("deleteFlag", Constants.NO_DELETE_FLAG);
		List<Object> pList = entityDao.selectByCondition(MedicalVisit.class, p);
		
		if(pList== null || pList.isEmpty()){
			logger.error("需要操作的患者就诊信息不存在！患者ID：{}，就诊次数：{}，就诊流水号：" + bs360.getVisitOrdNo(), 
					bs360.getPatientLid(),bs360.getTimes());
			return false;
		}else{
			MedicalVisit v = new MedicalVisit();
			v = (MedicalVisit)pList.get(0);
			visitSn = v.getVisitSn();
			patientSn = v.getPatientSn();
		}
		
		/**
		 * 2014-4-23注销，原因如下：据血库张工反馈，有可能没输血申请单，还是要发送输血记录单信息，对于手术室的人，不存在工作站，对于生命垂危的病人，
		 * 需要马上进行输血,直接由医生开一个纸质的申请单，然后就可以开输血记录单。
		 */
		//2. 检查申请单信息是否存在
		Map<String, Object> map = new HashMap<String, Object>();
        map.put("requestNo", bs360.getBloodRequestLid());
        map.put("patientLid", bs360.getPatientLid());
        map.put("orgCode", bs360.getHospitalCode());
        map.put("patientDomain", bs360.getDomainLid());
        
		List<Object> bList = entityDao.selectByCondition(BloodRequest.class, map);
		if(bList.size()>0){
			BloodRequest r = new BloodRequest();
			r = (BloodRequest)bList.get(0);
			requestSn = r.getRequestSn();
		}
		return true;
	}

	@Override
	@Transactional
	public void saveOrUpdate(BS360 bs360) {
        BloodRecordMenu m = new BloodRecordMenu();
        BloodRecord r = new BloodRecord();
        BloodRecordSpecialRequest  s = new BloodRecordSpecialRequest();
        ExamResult e = new ExamResult();
        
        //0:新增  1：修改  -1：删除
        boolean insert = Constants.VERSION_NUMBER_INSERT.equalsIgnoreCase(bs360.getAction());
        boolean delete = Constants.VERSION_NUMBER_WITHDRAW.equalsIgnoreCase(bs360.getAction());

        Map<String, Object> map = new HashMap<String, Object>();
        
        if(insert){
        	//根据文档号判断是否存在该输血记录单，存在则不重复记录
        	map = new HashMap<String, Object>();
        	map.put("menuLid", bs360.getMenuLid());
        	List<Object> menuList = entityDao.selectByCondition(BloodRecordMenu.class, map);
        	if(menuList.size()==0){
            	m.setRequestSn(requestSn);
                m.setVisitSn(visitSn);
                m.setPatientSn(patientSn);
                m.setPatientLid(bs360.getPatientLid());
                m.setMenuLid(bs360.getMenuLid());
                m.setOrgCode(bs360.getHospitalCode());
                m.setOrgName(bs360.getHospitalName());
                m.setPatientDomain(bs360.getDomainLid());
                m.setRequestBloodAboCode(bs360.getRequestBloodABOCode());
                m.setRequestBloodAboName(bs360.getRequestBloodABOName());
                m.setRequestBloodRhCode(bs360.getRequestBloodRHCode());
                m.setRequestBloodRhName(bs360.getRequestBloodRHName());
                m.setRecheckBloodAboCode(bs360.getRecheckBloodABOCode());
                m.setRecheckBloodAboName(bs360.getRecheckBloodABOName());
                m.setRecheckBloodRhCode(bs360.getRecheckBloodRHCode());
                m.setRecheckBloodRhName(bs360.getRecheckBloodRHName());
               
                m.setRemark(bs360.getRemark());
                m.setEffectiveTime(DateUtils.stringToDate(bs360.getEffectiveTime()));
                m.setCreateby(serviceId);
                m.setCreateTime(new Date());
                m.setUpdateby(serviceId);
                m.setUpdateTime(new Date());
                m.setDeleteFlag(Constants.NO_DELETE_FLAG);
                m.setUpdateCount(Constants.INSERT_UPDATE_COUNT);
           	 	entityDao.insert(m);
           	 	
           	    for(BloodRecordDto b : bs360.getBloodRecords()){
	             	r = new BloodRecord();
	             	r.setMenuSn(m.getMenuSn());
	             	r.setRequestSn(requestSn);
	             	r.setDifferenceCode(b.getDifferenceCode());
	             	r.setBloodType(b.getBloodType());
	             	r.setBloodBagBarCode(b.getBloodBagBarCode());
	             	r.setUnit(b.getUnit());
	             	r.setQuality(b.getQuality());
	             	r.setSupplyBloodClassCode(b.getSupplyBloodTypeCode());
	             	r.setSupplyBloodClassName(b.getSupplyBloodTypeName());
	             	r.setAssignedPersonCode(b.getAssignedPersonCode());
	             	r.setAssignedPersonName(b.getAssignedPersonName());
	             	r.setPlayingEntityCode(b.getPlayingEntityCode());
	             	r.setPlayingEntityName(b.getPlayingEntityName());
	             	r.setPlayingDate(DateUtils.stringToDate(b.getPlayingDate()));
	             	r.setMatchBloodDate(DateUtils.stringToDate(b.getMatchBloodDate()));
	             	r.setMatchBloodPersonCode(b.getMatchBloodPersonCode());
	             	r.setMatchBloodPersonName(b.getMatchBloodPersonName());
	             	r.setCrossMatchResult(b.getCrossMatchResult());
	             	r.setMajorCrossMatchResult(b.getMajorCrossMatchResult());
	             	r.setMinorCrossMatchResult(b.getMinorCrossMatchResult());
	             	r.setSendBloodDate(DateUtils.stringToDate(b.getSendBloodDate()));
	             	r.setCreateby(serviceId);
	             	r.setCreateTime(new Date());
	             	r.setUpdateby(serviceId);
	             	r.setUpdateTime(new Date());
	             	r.setDeleteFlag(Constants.NO_DELETE_FLAG);
	             	r.setUpdateCount(Constants.INSERT_UPDATE_COUNT);
	                entityDao.insert(r);
                }
           	 
    	       	for(ExamResultDto d : bs360.getExamResults()){
    	        	e = new ExamResult();
    	        	e.setMenuSn(m.getMenuSn());
    	        	e.setItemCode(d.getItemCode());
    	        	e.setItemName(d.getItemName());
    	        	e.setItemResult(d.getItemResult());
    	        	e.setCreateby(serviceId);
    		        e.setCreateTime(new Date());
    		        e.setUpdateby(serviceId);
    		        e.setUpdateTime(new Date());
    		        e.setDeleteFlag(Constants.NO_DELETE_FLAG);
    		        e.setUpdateCount(Constants.INSERT_UPDATE_COUNT);
    	            entityDao.insert(e);
    	        }
    	       		
    	       	for(BloodRecordSpecialRequestDto rs : bs360.getSpecialRequests()){
    	        	s = new BloodRecordSpecialRequest();
    	        	s.setMenuSn(m.getMenuSn());
    	        	s.setSpecialRequestCode(rs.getBloodSpecialRequestCode());
    	        	s.setSpecialRequestName(rs.getBloodSpecialRequestName());
    	        	s.setCreateby(serviceId);
    		        s.setCreateTime(new Date());
    		        s.setUpdateby(serviceId);
    		        s.setUpdateTime(new Date());
    		        s.setDeleteFlag(Constants.NO_DELETE_FLAG);
    		        s.setUpdateCount(Constants.INSERT_UPDATE_COUNT);
    	            entityDao.insert(s);
    	        }
        	}
        }else if(delete){
        	//退血，可能是部分退血，也可能是全部退血，如果检测到所有的血都被退掉后，输血记录单直接逻辑删除，变为无效
        	map = new HashMap<String, Object>();
        	if(requestSn!=null){
        		map.put("requestSn", requestSn);
        	}
            map.put("visitSn", visitSn);
            map.put("patientSn", patientSn);
   		    List<Object> menuList = entityDao.selectByCondition(BloodRecordMenu.class, map);
   		    
   		    int cnt = 0;
            if(menuList.size()>0){
            	for(int i=0;i<menuList.size();i++){
            		
                	for(BloodRecordDto b : bs360.getBloodRecords()){
                		map = new HashMap<String, Object>();
                		if(requestSn!=null){
                    		map.put("requestSn", requestSn);
                    	}
                        map.put("differenceCode", b.getDifferenceCode());
                        map.put("bloodBagBarCode", b.getBloodBagBarCode());
                        List<Object> recordList2 = entityDao.selectByCondition(BloodRecord.class, map);
                		for(int j=0;j<recordList2.size();j++){
                			r = (BloodRecord)recordList2.get(j);
                			r.setDeleteby(serviceId);
                    		r.setDeleteTime(new Date());
                    		r.setDeleteFlag("1");
                    		entityDao.update(r);
                		}
                	}
                	
                	m = (BloodRecordMenu)menuList.get(i);
                	map = new HashMap<String, Object>();
            		map.put("menuSn", m.getMenuSn());
            		map.put("deleteFlag", "0");
            		List<Object> recordList1 = entityDao.selectByCondition(BloodRecord.class, map);
            		cnt = recordList1.size();
            		
                	//如果退出的血袋跟记录的总血袋数量相同，则将输血记录单信息删除。
                	if(cnt==0){
                		m.setDeleteby(serviceId);
                		m.setDeleteTime(new Date());
                		m.setDeleteFlag("1");
                		entityDao.update(m);
                		
                		//删除附属信息
                		List<Object> examList = entityDao.selectByCondition(ExamResult.class, map);
                		for(int k=0;k<examList.size();k++){
                			e = (ExamResult)examList.get(k);
                			e.setDeleteby(serviceId);
                    		e.setDeleteTime(new Date());
                    		e.setDeleteFlag("1");
                    		entityDao.update(e);
                		}
                		
                		List<Object> specialList = entityDao.selectByCondition(BloodRecordSpecialRequest.class, map);
                		for(int o=0;o<specialList.size();o++){
                			s = (BloodRecordSpecialRequest)specialList.get(o);
                			s.setDeleteby(serviceId);
                    		s.setDeleteTime(new Date());
                    		s.setDeleteFlag("1");
                    		entityDao.update(s);
                		}
                	}
            	}
            }
        }
	}
}
