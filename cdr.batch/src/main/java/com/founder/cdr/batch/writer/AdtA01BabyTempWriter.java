package com.founder.cdr.batch.writer;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.founder.cdr.core.Constants;
import com.founder.cdr.entity.Patient;
import com.founder.cdr.entity.PatientCrossIndex;
import com.founder.cdr.hl7.dto.prpain402001uv01.PRPAIN402001UV01;
import com.founder.cdr.service.CommonService;
import com.founder.cdr.service.PatientService;
import com.founder.cdr.util.DateUtils;
import com.founder.cdr.util.DictionaryUtils;


/**
 * @author yu_yzh
 * 婴儿登记临时解决方案，新增婴儿患者记录
 * */
@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class AdtA01BabyTempWriter{
	private static final String PATIENT_SEQUENCE = "SEQ_PATIENT";
    
	private static final String DB_FLAG = "ADT^A01 BABY";
		
	@Autowired
    private CommonService commonService;
    
    @Autowired
    private PatientService patientService;
		
    private Date sysDate = new Date();;
    	
	public boolean checkBabyExists(String patientEid){
		Patient patient = patientService.getPatient(patientEid, Constants.NO_DELETE_FLAG);
		if(patient == null){
			return false;
		} else {			
			return true;
		}		
	}
	@Transactional
	public Patient addPatient(PRPAIN402001UV01 prpain402001uv01){
		Patient patient = new Patient();
		
		// 患者内部序列号
		patient.setPatientSn(commonService.getSequence(PATIENT_SEQUENCE));
		// 出生日期
		String birthDate = prpain402001uv01.getBirthDate();
		patient.setBirthDate(DateUtils.stringToDate(birthDate));
		
		// 性别
		String genderCode = prpain402001uv01.getGenderCode();
		String genderName = DictionaryUtils.getNameFromDictionary(Constants.DOMAIN_GENDER, genderCode);
		patient.setGenderCode(genderCode);
		patient.setGenderName(genderName);
		
		// eid
		String patientEid = prpain402001uv01.getPatientEid();
		patient.setPatientEid(patientEid);
		
		// 姓名
		String patientName = prpain402001uv01.getPatientName();
		patient.setPatientName(patientName);

		patient.setCreateby(DB_FLAG);
		patient.setCreateTime(sysDate);
		patient.setUpdateTime(sysDate);
		patient.setDeleteFlag(Constants.NO_DELETE_FLAG);
		patient.setUpdateCount(Constants.INSERT_UPDATE_COUNT);
		
		patientService.savePatient(patient);
		return patient;
	}
	@Transactional
	public PatientCrossIndex addPatientCrossIndex(PRPAIN402001UV01 prpain402001uv01){
		PatientCrossIndex pci = new PatientCrossIndex();
		// 域id
		String patientDomain = prpain402001uv01.getPatientDomain();
		pci.setPatientDomain(patientDomain);
		
		String patientLid = prpain402001uv01.getPatientLid();
		pci.setPatientLid(patientLid);
		
		String patientEid = prpain402001uv01.getPatientEid();
		pci.setPatientEid(patientEid);
		
		pci.setCreateby(DB_FLAG);
		pci.setCreateTime(sysDate);
		pci.setDeleteFlag(Constants.NO_DELETE_FLAG);
		pci.setUpdateCount(Constants.INSERT_UPDATE_COUNT);
		pci.setUpdateTime(sysDate);
		
		commonService.save(pci);
		return pci;
	}

}
