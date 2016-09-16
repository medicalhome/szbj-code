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
import com.founder.cdr.entity.MedicalVisit;
import com.founder.cdr.entity.PatientDoctorIncharge;
import com.founder.cdr.hl7.dto.DoctorInCharge;
import com.founder.cdr.hl7.dto.bs359.BS359;
import com.founder.cdr.service.CommonService;
import com.founder.cdr.util.DataMigrationUtils;
import com.founder.cdr.util.DateUtils;
import com.founder.cdr.util.DictionaryUtils;
import com.founder.cdr.util.StringUtils;

@Component(value = "bs359Writer")
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class BS359Writer implements BusinessWriter<BS359> {

	@Autowired
	private CommonService commonService;

	private Logger logger = LoggerFactory.getLogger(BS359Writer.class);

	private BigDecimal visitSn = null;

	private BigDecimal patientSn = null;

	private List<String> lstTitle = Constants.lstTitleTypeName();

	private MedicalVisit medicalVisit;

	/**
	 * 数据业务校验
	 */
	@Override
	public boolean validate(BS359 bs359) {
		// 为兼容,当消息中医疗机构ID或者医疗机构名称为空时，默认是北京大学人民医院
		if (StringUtils.isEmpty(bs359.getHospitalCode())) {
			// $Author :yang_mingjie
			// $Date : 2014/06/26 10:09$
			// [BUG]0045630 MODI6FY BEGIN
			bs359.setHospitalCode(Constants.HOSPITAL_CODE);
			bs359.setHospitalName(Constants.HOSPITAL_NAME);
		}
		// [BUG]0045630 MODIFY END
		// $Author :du_xiaolan
		// $Date : 2014/11/27
		// 验证职称类别名称
/*		if (null == lstTitle || lstTitle.equals("")) {
			logger.error("BS359，请在cdr.properties配置TITLE_TYPE_NAME");
			return false;
		} else {
			for (DoctorInCharge charge : bs359.getDoctors()) {
				if (!lstTitle.contains(charge.getTitleTypeName())) {
					logger.error("BS359，该患者的职称类别名称为："
							+ charge.getTitleTypeName() + "，不在配置范围内");
					return false;
				}
			}
		}*/
		return true;
	}

	@Override
	public boolean checkDependency(BS359 bs359, boolean falg) {
		medicalVisit = commonService.mediVisit(bs359.getDomainId(),
				bs359.getPatientId(), bs359.getTimes(),
				bs359.getHospitalCode(), bs359.getVisitOrdNo(),
				bs359.getVisitType());

		if (medicalVisit == null) {
			logger.error("需要操作的患者就诊信息不存在！患者ID：{}，医疗结构代码：{}",
					bs359.getPatientId(), bs359.getHospitalCode());
			return false;
		}

		visitSn = medicalVisit.getVisitSn();
		patientSn = medicalVisit.getPatientSn();
		return true;
	}

	@Transactional
	@Override
	public void saveOrUpdate(BS359 bs359) {
		Date systemDate = new Date();
		
		PatientDoctorIncharge entity = new PatientDoctorIncharge();
		for (DoctorInCharge deletecharge : bs359.getDoctors()) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("visitSn", visitSn);
			map.put("titleTypeName", deletecharge.getTitleTypeName());

			List<Object> list = commonService.selectByCondition(
					PatientDoctorIncharge.class, map);
			if (list.size() > 0) {
				for (int j = 0; j < list.size(); j++) {
					entity = (PatientDoctorIncharge) list.get(j);
					commonService.delete(entity);
				}
			}
		}

		for (DoctorInCharge charge : bs359.getDoctors()) {
			entity = new PatientDoctorIncharge();
			entity.setVisitSn(visitSn);
			entity.setPatientSn(patientSn);
			entity.setOrgCode(bs359.getHospitalCode());
			entity.setOrgName(bs359.getHospitalName());
			entity.setPatientDomain(bs359.getDomainId());
			entity.setDoctorId(charge.getDoctorId());
//			entity.setDoctorName(charge.getDoctorName());
			entity.setDoctorName(DictionaryUtils.getNameFromDictionary(
	        		Constants.DOMAIN_STAFF, charge.getDoctorId(), charge.getDoctorName()));
			if(charge.getTakeoverTime() != null){
				entity.setTakeoverTime(DateUtils.stringToDate(charge.getTakeoverTime()));
			}			
			if(charge.getExitTime() != null){
				entity.setExitTime(DateUtils.stringToDate(charge.getExitTime()));
			}			
			entity.setChargeFlag(charge.getAction());
			entity.setDeleteFlag(Constants.NO_DELETE_FLAG);
			entity.setCreateby(DataMigrationUtils.getDataMigration() + bs359.getMessage().getServiceId());
			entity.setCreateTime(systemDate);
			entity.setUpdateby(bs359.getMessage().getServiceId());
			entity.setUpdateTime(systemDate);
			entity.setUpdateCount(Constants.INSERT_UPDATE_COUNT);
			entity.setTitleTypeName(charge.getTitleTypeName());
			commonService.insert(entity);
		}
	}
}
