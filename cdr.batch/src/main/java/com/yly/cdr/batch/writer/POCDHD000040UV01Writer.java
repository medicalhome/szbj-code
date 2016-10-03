/**
 * 麻醉记录单服务
 * 
 * @version 1.0
 
 * @author li_shenggen
 * @since 1.0
*/
package com.yly.cdr.batch.writer;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.yly.cdr.batch.writer.common.CommonWriterUtils;
import com.yly.cdr.cache.DictionaryCache;
import com.yly.cdr.core.Constants;
import com.yly.cdr.entity.Anesthesia;
import com.yly.cdr.entity.AnesthesiaEvent;
import com.yly.cdr.entity.AnesthesiaMedicalTransfuse;
import com.yly.cdr.entity.ClinicalDocument;
import com.yly.cdr.entity.Diagnosis;
import com.yly.cdr.entity.InOutDose;
import com.yly.cdr.entity.InfectiousRecord;
import com.yly.cdr.entity.MedicalImaging;
import com.yly.cdr.entity.MedicalVisit;
import com.yly.cdr.entity.NursingOperation;
import com.yly.cdr.entity.PatientCrossIndex;
import com.yly.cdr.entity.PatientDoctor;
import com.yly.cdr.entity.PhysicalExamination;
import com.yly.cdr.entity.ProcedureOrder;
import com.yly.cdr.hl7.dto.ContinuousMedication;
import com.yly.cdr.hl7.dto.InOutDoseMessage;
import com.yly.cdr.hl7.dto.InterruptMedication;
import com.yly.cdr.hl7.dto.MedicationMessage;
import com.yly.cdr.hl7.dto.PhysicalExaminationItem;
import com.yly.cdr.hl7.dto.TimeQuantum;
import com.yly.cdr.hl7.dto.TransfuseMessage;
import com.yly.cdr.hl7.dto.pocdhd000040uv01.POCDHD000040UV01;
import com.yly.cdr.hl7.dto.pocdhd000040uv01.ReviewPersonDto;
import com.yly.cdr.service.AnesthesiaService;
import com.yly.cdr.service.ClinicalDocumentService;
import com.yly.cdr.service.CommonService;
import com.yly.cdr.service.DiagnosisService;
import com.yly.cdr.util.DateUtils;
import com.yly.cdr.util.ObjectUtils;
import com.yly.cdr.util.StringUtils;

/**
 * @author lishenggen
 *
 */
@Component(value = "pocdhd000040uv01Writer")
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class POCDHD000040UV01Writer implements BusinessWriter<POCDHD000040UV01>
{
    // 就诊记录
    private MedicalVisit medicalVisit;
    
    // 患者本地ID
    private String patientLid;

    // 患者域ID
    private String patientDomain;

    private ClinicalDocument clinicalDocument;

    private InfectiousRecord infectiousRecord;

    // CDA图片序列名称
    private static final String MEDICAL_IMAGING_SEQUENCE = "SEQ_MEDICAL_IMAGING";

    // 病历文书序列名称
    private static final String DOCUMENT_SEQUENCE = "SEQ_DOCUMENT";

    // 获取系统时间
    private Date systemTime = DateUtils.getSystemTime();

    @Autowired
    private ClinicalDocumentService clinicalDocumentService;

    @Autowired
    private CommonService commonService;
    @Autowired
    private AnesthesiaService anesthesiaService;

    @Autowired
    private CommonWriterUtils commonWriterUtils;
    

    private static final Logger logger = LoggerFactory.getLogger(POCDHD000040UV01Writer.class);
    // BS327 麻醉记录信息服务
    private static final Logger loggerBS327 = LoggerFactory.getLogger("BS327");
    private String serviceId;
    //关联医嘱号只取第一个
    private String orderlid;
    //麻醉记录单表内部序列号
    private BigDecimal anesthesiaSn;
    // 麻醉记录单序列号sequences
    private static final String SEQ_NURSING_OPERATION = "SEQ_NURSING_OPERATION";
    // 麻醉事件List
    List<Object> anesthesiaEventList;
    // 体格检查List
    List<Object> physicalExaminationList;
    // 麻醉用药输液List
    List<Object> anesthesiaMedicalTransfuseList;
    // 入出量信息List
    List<Object> inOutDoseList;
    
    @Override
    public boolean validate(POCDHD000040UV01 pocdhd000040uv01)
    {
        serviceId = pocdhd000040uv01.getMessage().getServiceId();
        // 获取文档日志输出对象
        //loggerBSDOCUMENT = getDocumentLogger(serviceId);

        if (StringUtils.isEmpty(pocdhd000040uv01.getMessage().getOrgCode()))
        {
            pocdhd000040uv01.getMessage().setOrgCode(Constants.HOSPITAL_CODE);
        }
        if(StringUtils.isEmpty(pocdhd000040uv01.getOrganizationCode())||StringUtils.isEmpty(pocdhd000040uv01.getOrganizationName()))
        {
            pocdhd000040uv01.setOrganizationCode(Constants.HOSPITAL_CODE);
            pocdhd000040uv01.setOrganizationName(Constants.HOSPITAL_NAME);
        }

        String versionNumber = pocdhd000040uv01.getVersionNumber();
        // 非空校验标志
        Boolean flag = true;
        List<String> validateList = new ArrayList<String>();
        // 测试用日志
        Map<String, String> logMap = new HashMap<String, String>();
/*        if (!(isDeleteMessage(pocdhd000040uv01)))
        {*/
            if (!isDeleteMessage(pocdhd000040uv01))
            {
        		// 患者Lid
                validateList.add(pocdhd000040uv01.getPatientLid());
                logMap.put("患者Lid不能为空！", pocdhd000040uv01.getPatientLid());
                
                //就诊流水号
                validateList.add(pocdhd000040uv01.getVisitOrdNo());
                logMap.put("就诊流水号不能为空！", pocdhd000040uv01.getVisitOrdNo());
                
                // 文档类型
                validateList.add(pocdhd000040uv01.getDocumentType());
                logMap.put("文档类型不能为空！", pocdhd000040uv01.getDocumentType());
                // 文档名称
                validateList.add(pocdhd000040uv01.getDocumentName());
                logMap.put("文档名称不能为空！", pocdhd000040uv01.getDocumentName());
                // 域ID
                validateList.add(pocdhd000040uv01.getPatientDomain());
                logMap.put("域ID不能为空！", pocdhd000040uv01.getPatientDomain());
                // 服务ID
                validateList.add(pocdhd000040uv01.getServiceId());
                logMap.put("服务ID不能为空！", pocdhd000040uv01.getServiceId());

	            //就诊类型
	            validateList.add(pocdhd000040uv01.getVisitTypeCode());
	            logMap.put("就诊类型不能为空！", pocdhd000040uv01.getVisitTypeCode());
	            List<String> orderLids = pocdhd000040uv01.getOrderLids();
                if (orderLids.isEmpty())
                {
                    logMap.put("关联医嘱号不能为空！", "orderLids = "
                        + orderLids.size());
                }else{
                    for (int i = 0; i < orderLids.size(); i++)
                    {
                        validateList.add(orderLids.get(i));
                        logMap.put("关联医嘱号第" + i + "个不能为空！",
                                orderLids.get(i));
                    }
                }
                String[] str = new String[validateList.size()];
                flag = StringUtils.isNotNullAll(validateList.toArray(str));
            }
        // 测试所用非空验证日志
        if (flag == false)
        {
            Set<Entry<String, String>> entries = logMap.entrySet();
            Iterator<Entry<String, String>> iterator = entries.iterator();

            StringBuilder logs = new StringBuilder();

            while (iterator.hasNext())
            {
                Entry<String, String> entry = iterator.next();

                if (StringUtils.isEmpty(entry.getValue()))
                {
                    logs.append(entry.getKey() + " ; ");
                    // logger.error("{} \n",entry.getKey());
                }
            }

            loggerBS327.error("Message:{},validate:{}",
                    pocdhd000040uv01.toString(), logs.toString());
        }
        return flag;
    }

    /**
     * 新增/更新时检查与就诊的关联关系
     */
    @Override
    public boolean checkDependency(POCDHD000040UV01 pocdhd000040uv01,
            boolean flag)
    {
            patientLid = pocdhd000040uv01.getPatientLid();
            patientDomain = pocdhd000040uv01.getPatientDomain();
            List<String> orderLids = pocdhd000040uv01.getOrderLids();
            if(orderLids != null && orderLids.size() > 0){
            	orderlid = orderLids.get(0);
            }
            // 获取就诊记录
            medicalVisit = getMedicalVisit(pocdhd000040uv01);
            if (medicalVisit == null)
            {
                logger.error("在新增/更新住院病案消息时相应的就诊信息不存在: {}", "住院病案关联消息");
                if (flag)
                {
                    loggerBS327.error("Message:{},checkDependency:{}",
                            pocdhd000040uv01.toString(),
                            "在新增/更新住院病案消息时相应的就诊信息不存在:住院病案关联消息");
                }
                return false;
            }else{
            	if(Constants.VERSION_NUMBER_UPDATE.equals(pocdhd000040uv01.getVersionNumber())){
            		if(!checkAnesthesia(pocdhd000040uv01)){
	            		loggerBS327.error("Message:{},checkDependency:{}",pocdhd000040uv01.toString(),
	            				"此麻醉记录单不存在数据库中");
	                    if (flag)
	                    {
	                        loggerBS327.error("Message:{},checkDependency:{}",
	                                pocdhd000040uv01.toString(),
	                                "此麻醉记录单不存在数据库中");
	                    }
	                    return false;
            		}
            	}
            	if(Constants.VERSION_NUMBER_INSERT.equals(pocdhd000040uv01.getVersionNumber())){
            		if(checkAnesthesia(pocdhd000040uv01)){
                		loggerBS327.info("此麻醉记录单已存在数据库中,新增信息改为更新消息,Message:{}",pocdhd000040uv01.toString());
                		pocdhd000040uv01.setVersionNumber(Constants.VERSION_NUMBER_UPDATE);
            		}
            	}
            }
        return true;
    }

    /**
     * 新增/更新
     */
    @Override
    @Transactional
    public void saveOrUpdate(POCDHD000040UV01 pocdhd000040uv01)
    {
    	
		systemTime = DateUtils.getSystemTime();
	    // 麻醉事件List
	    anesthesiaEventList = new ArrayList<Object>();
	    // 体格检查List
	    physicalExaminationList = new ArrayList<Object>();
	    // 麻醉用药输液List
	    anesthesiaMedicalTransfuseList = new ArrayList<Object>();
	    // 入出量信息List
	    inOutDoseList = new ArrayList<Object>();
	    //新增操作
		if (Constants.VERSION_NUMBER_INSERT.equals(pocdhd000040uv01
				.getVersionNumber())) {
			
			getInsertAnesthesia(pocdhd000040uv01);
		} else if (Constants.VERSION_NUMBER_UPDATE.equals(pocdhd000040uv01
				.getVersionNumber())) {//更新操作,先删除再插入
			Anesthesia anesthesia = null;
			//1，先删除
			anesthesia = setAnesthesia(pocdhd000040uv01, medicalVisit);
			anesthesiaSn = anesthesia.getAnesthesiaSn();
			getAnesthesiaList(pocdhd000040uv01);
			anesthesiaService.deleteAnesthesia(anesthesia, anesthesiaEventList,
					physicalExaminationList, anesthesiaMedicalTransfuseList,
					inOutDoseList,serviceId);
			//2,再插入
			anesthesiaEventList.clear();
			physicalExaminationList.clear();
			anesthesiaMedicalTransfuseList.clear();
			inOutDoseList.clear();
			pocdhd000040uv01.setVersionNumber(Constants.VERSION_NUMBER_INSERT);
			getInsertAnesthesia(pocdhd000040uv01);
			
		}
    	
    	//CDA整存
        if (isDeleteMessage(pocdhd000040uv01))
        {
            clinicalDocument.setDeleteFlag(Constants.DELETE_FLAG);
            clinicalDocument.setDeleteby(serviceId);
            clinicalDocument.setDeleteTime(systemTime);
            clinicalDocumentService.deleteClinicalDocument(clinicalDocument);
        }
        else
        {
            String flag = "";
            MedicalImaging medicalImaging = null;
            clinicalDocument = checkClinicalDocument(pocdhd000040uv01,medicalVisit);
            if (clinicalDocument != null)
            {
                flag = Constants.VERSION_NUMBER_UPDATE;
                logger.info("数据库存在该记录: {}", "执行更新操作");
                clinicalDocument = getClinicalDocument(pocdhd000040uv01, flag);
                if (Constants.ANESTHESIA_SERVICE_ID.equals(clinicalDocument.getServiceId()))
                {
                    medicalImaging = getCDAImage(pocdhd000040uv01, flag);
                }
                // 执行更新操作
                clinicalDocumentService.updateClinicalDocument(
                        clinicalDocument, medicalImaging,
                        pocdhd000040uv01.getMessage(), systemTime);
            }
            else
            {
                flag = Constants.VERSION_NUMBER_INSERT;
                logger.info("数据库不存在该记录: {}", "执行新增操作");
                clinicalDocument = getClinicalDocument(pocdhd000040uv01, flag);
                if (Constants.ANESTHESIA_SERVICE_ID.equals(clinicalDocument.getServiceId()))
                {
                    medicalImaging = getCDAImage(pocdhd000040uv01, flag);
                }
                // 执行保存操作
                clinicalDocumentService.saveClinicalDocument(clinicalDocument,
                        medicalImaging, pocdhd000040uv01.getMessage());
                // $Author:wei_peng
                // $Date:2012/11/06 13:59
                // [BUG]0011030 ADD BEGIN
                if (pocdhd000040uv01.getDocumentAuthor() != null
                    && !pocdhd000040uv01.getDocumentAuthor().isEmpty())
                {
                    PatientDoctor patientDoctor = commonWriterUtils.getPatientDoctor(
                            medicalVisit.getVisitSn(),
                            medicalVisit.getPatientSn(),
                            pocdhd000040uv01.getDocumentAuthor(),
                            pocdhd000040uv01.getDocumentAuthorName(),
                            systemTime);
                    if (patientDoctor != null)
                    {
                        patientDoctor.setCreateby(serviceId); // 设置创建人
                        commonService.save(patientDoctor);
                    }
                }
            }
        }

    }
    /**
     * 麻醉记录相关表list赋值
     */   
    public void getAnesthesiaList(POCDHD000040UV01 pocdhd000040uv01){
		//麻醉事件List
		setAnesthesiaEventList(pocdhd000040uv01);
		//体格检查List
		setPhysicalExaminationList(pocdhd000040uv01);
		//麻醉用药输液List
		setAnesthesiaMedicalTransfuseList(pocdhd000040uv01);
		//入出量信息List
		setInOutDoseList(pocdhd000040uv01);
    }
    /**
     * 麻醉记录相关表list赋值
     */   
    public void getInsertAnesthesia(POCDHD000040UV01 pocdhd000040uv01){
		Anesthesia anesthesia = null;
		//麻醉记录单实体赋值
		anesthesia = setAnesthesia(pocdhd000040uv01, medicalVisit);
		getAnesthesiaList(pocdhd000040uv01);
		
		anesthesiaService.saveAnesthesia(anesthesia, anesthesiaEventList,
				physicalExaminationList, anesthesiaMedicalTransfuseList,
				inOutDoseList);
    }  
    /**
     * 判断麻醉记录单是否存在
     * @param pocdhd000040uv01
     * @return 是/否
     */
    public boolean checkAnesthesia(POCDHD000040UV01 pocdhd000040uv01){
    	boolean isExist = false;
		List<Object> anesthesiaList = new ArrayList<Object>();
        Map<String, Object> condition = new HashMap<String, Object>();
        condition.put("orderLid", orderlid);
        condition.put("patientSn", medicalVisit.getPatientSn());
        condition.put("visitSn", medicalVisit.getVisitSn());
        condition.put("orgCode", pocdhd000040uv01.getOrganizationCode());
        condition.put("deleteFlag", Constants.NO_DELETE_FLAG);
        anesthesiaList = commonService.selectByCondition(
        		Anesthesia.class, condition);
        if(anesthesiaList != null && anesthesiaList.size() > 0){
        	isExist = true;
        }
        return isExist;
    }
    /**
     * 插入麻醉记录单表
     * 
     * @param pocdhd000040uv01
     * @param medicalVisit
     */
    public Anesthesia setAnesthesia(POCDHD000040UV01 pocdhd000040uv01,MedicalVisit medicalVisit){
    	Anesthesia anesthesia = null;//麻醉记录单表
	    //新增操作
		if (Constants.VERSION_NUMBER_INSERT.equals(pocdhd000040uv01
				.getVersionNumber())) {
	    	if(orderlid != null){
	    		anesthesia = new Anesthesia();
	    		anesthesiaSn = commonService.getSequence(SEQ_NURSING_OPERATION);
	    		anesthesia.setAnesthesiaSn(anesthesiaSn);
	    		anesthesia.setPatientSn(medicalVisit.getPatientSn());
	    		anesthesia.setPatientDomain(medicalVisit.getPatientDomain());
	    		anesthesia.setPatientLid(medicalVisit.getPatientLid());
	    		anesthesia.setVisitSn(medicalVisit.getVisitSn());
	    		anesthesia.setOrderLid(orderlid);
	    		anesthesia.setOrgCode(pocdhd000040uv01.getOrganizationCode());
	    		anesthesia.setOrgName(pocdhd000040uv01.getOrganizationName());
	    		anesthesia.setAnesthesiaDept(pocdhd000040uv01.getAnesthesiaDept());
	    		anesthesia.setAnesthesiaDeptName(pocdhd000040uv01.getAnesthesiaDeptName());
	    		anesthesia.setAnesthesiaGradeCode(pocdhd000040uv01.getAnesthesiaGradeCode());
	    		anesthesia.setAnesthesiaGradeName(pocdhd000040uv01.getAnesthesiaGradeName());
	    		
	    		anesthesia.setAnesthesiaMethod(pocdhd000040uv01.getAnesthesiaMethod());
	    		anesthesia.setAnesthesiaMethodName(pocdhd000040uv01.getAnesthesiaMethodName());
	    		anesthesia.setWorkload(pocdhd000040uv01.getWorkload());
	    		anesthesia.setChineseAnesthesia(pocdhd000040uv01.getChineseAnesthesia()==null ? "0":pocdhd000040uv01.getChineseAnesthesia());
	    		anesthesia.setPrepareTime(DateUtils.stringToDate(pocdhd000040uv01.getPrepareTime()));
	    		anesthesia.setStartTime(DateUtils.stringToDate(pocdhd000040uv01.getStartTime()));
	    		anesthesia.setEndTime(DateUtils.stringToDate(pocdhd000040uv01.getEndTime()));
	    		anesthesia.setCreateTime(systemTime);
	    		anesthesia.setUpdateTime(systemTime);
	    		anesthesia.setDeleteFlag(Constants.NO_DELETE_FLAG);
	    		anesthesia.setUpdateCount(Constants.INSERT_UPDATE_COUNT);
	    	}
		}
	    //更新操作,先删除再插入
		if (Constants.VERSION_NUMBER_UPDATE.equals(pocdhd000040uv01
				.getVersionNumber())) {
			List<Object> anesthesiaList = new ArrayList<Object>();
	        Map<String, Object> condition = new HashMap<String, Object>();
	        condition.put("orderLid", orderlid);
	        condition.put("patientSn", medicalVisit.getPatientSn());
	        condition.put("visitSn", medicalVisit.getVisitSn());
	        condition.put("orgCode", pocdhd000040uv01.getOrganizationCode());
	        condition.put("deleteFlag", Constants.NO_DELETE_FLAG);
	        anesthesiaList = commonService.selectByCondition(
	        		Anesthesia.class, condition);
	        if(anesthesiaList != null && anesthesiaList.size() > 0){
        		anesthesia = (Anesthesia)anesthesiaList.get(0);
	        }
		}
    	return anesthesia;
	}
    
    /**
     * 麻醉事件实体赋值
     * 
     * @param pocdhd000040uv01
     */
    public AnesthesiaEvent createNewAnesthesiaEvent(String type){
    	AnesthesiaEvent anesthesiaEvent = null;
		anesthesiaEvent = new AnesthesiaEvent();
		anesthesiaEvent.setEventSn(commonService.getSequence(SEQ_NURSING_OPERATION));
		anesthesiaEvent.setAnesthesiaSn(anesthesiaSn);
		anesthesiaEvent.setOperationType(type);//'01'代表麻醉医生
		anesthesiaEvent.setCreateTime(systemTime);
		anesthesiaEvent.setUpdateTime(systemTime);
		anesthesiaEvent.setDeleteFlag(Constants.NO_DELETE_FLAG);
		anesthesiaEvent.setUpdateCount(Constants.INSERT_UPDATE_COUNT);
		return anesthesiaEvent;
    }  
    /**
     * 麻醉事件List
     * 
     * @param pocdhd000040uv01
     */
    public void setAnesthesiaEventList(POCDHD000040UV01 pocdhd000040uv01){
		AnesthesiaEvent anesthesiaEvent = null;
	    //新增操作
		if (Constants.VERSION_NUMBER_INSERT.equals(pocdhd000040uv01
				.getVersionNumber())) {
			//麻醉医生
			if(pocdhd000040uv01.getAnesthesiaDoctorList() != null && pocdhd000040uv01.getAnesthesiaDoctorList().size() > 0){
				for(int i=0;i<pocdhd000040uv01.getAnesthesiaDoctorList().size();i++){
					anesthesiaEvent = createNewAnesthesiaEvent("01");//'01'代表麻醉医生
					anesthesiaEvent.setOperator(pocdhd000040uv01.getAnesthesiaDoctorList().get(i).getAnesthesiaDoctorCode());
					anesthesiaEvent.setOperatorName(pocdhd000040uv01.getAnesthesiaDoctorList().get(i).getAnesthesiaDoctorName());
					anesthesiaEvent.setStartTime(DateUtils.stringToDate(pocdhd000040uv01.getStartTime()));
					anesthesiaEvent.setFinishedTime(DateUtils.stringToDate(pocdhd000040uv01.getEndTime()));
					anesthesiaEventList.add(anesthesiaEvent);
				}				
			}
			if(pocdhd000040uv01.getAnesthesiaDoctorList() != null && pocdhd000040uv01.getAnesthesiaDoctorList().size() > 0){
				//手术医生
				for(int i=0;i<pocdhd000040uv01.getAnesthesiaDoctorList().size();i++){
					anesthesiaEvent = createNewAnesthesiaEvent("02");//'02'代表手术医生
					anesthesiaEvent.setOperator(pocdhd000040uv01.getAnesthesiaDoctorList().get(i).getAnesthesiaDoctorCode());
					anesthesiaEvent.setOperatorName(pocdhd000040uv01.getAnesthesiaDoctorList().get(i).getAnesthesiaDoctorName());
					anesthesiaEvent.setStartTime(DateUtils.stringToDate(pocdhd000040uv01.getOperateStartTime()));
					anesthesiaEvent.setFinishedTime(DateUtils.stringToDate(pocdhd000040uv01.getOperateEndTime()));
					anesthesiaEventList.add(anesthesiaEvent);
				}
			}
			//器械护士
			if(pocdhd000040uv01.getApparatusDoctorList() != null && pocdhd000040uv01.getApparatusDoctorList().size() > 0){
				for(int i=0;i<pocdhd000040uv01.getApparatusDoctorList().size();i++){
					anesthesiaEvent = createNewAnesthesiaEvent("03");//'03'代表器械护士
					anesthesiaEvent.setOperator(pocdhd000040uv01.getApparatusDoctorList().get(i).getApparatusDoctorCode());
					anesthesiaEvent.setOperatorName(pocdhd000040uv01.getApparatusDoctorList().get(i).getApparatusDoctorName());
					anesthesiaEvent.setStartTime(DateUtils.stringToDate(pocdhd000040uv01.getOperateStartTime()));
					anesthesiaEvent.setFinishedTime(DateUtils.stringToDate(pocdhd000040uv01.getOperateEndTime()));
					anesthesiaEventList.add(anesthesiaEvent);
				}
			}
			//巡回护士
			if(pocdhd000040uv01.getTourDoctorList() != null && pocdhd000040uv01.getTourDoctorList().size() > 0){
				for(int i=0;i<pocdhd000040uv01.getTourDoctorList().size();i++){
					anesthesiaEvent = createNewAnesthesiaEvent("04");//'04'代表巡回护士
					anesthesiaEvent.setOperator(pocdhd000040uv01.getTourDoctorList().get(i).getTourDoctorCode());
					anesthesiaEvent.setOperatorName(pocdhd000040uv01.getTourDoctorList().get(i).getTourDoctorName());
					anesthesiaEvent.setStartTime(DateUtils.stringToDate(pocdhd000040uv01.getOperateStartTime()));
					anesthesiaEvent.setFinishedTime(DateUtils.stringToDate(pocdhd000040uv01.getOperateEndTime()));
					anesthesiaEventList.add(anesthesiaEvent);
				}
			}			
		}
	    //更新操作
		if (Constants.VERSION_NUMBER_UPDATE.equals(pocdhd000040uv01
				.getVersionNumber())) {
	        Map<String, Object> condition = new HashMap<String, Object>();
	        condition.put("anesthesiaSn", anesthesiaSn);
	        condition.put("orgCode", pocdhd000040uv01.getOrganizationCode());
	        condition.put("deleteFlag", Constants.NO_DELETE_FLAG);
	        anesthesiaEventList = commonService.selectByCondition(
	        		AnesthesiaEvent.class, condition);
		}
    }    
    
    /**
     * 体格检查List
     * 
     * @param pocdhd000040uv01
     */
    public void setPhysicalExaminationList(POCDHD000040UV01 pocdhd000040uv01){
    	PhysicalExamination physicalExamination = null;//体格检查表
    	
		if (Constants.VERSION_NUMBER_INSERT.equals(pocdhd000040uv01
				.getVersionNumber())) {
	    	if(pocdhd000040uv01.getPhysicalExaminationItemList() != null && pocdhd000040uv01.getPhysicalExaminationItemList().size() > 0){
	    		for(PhysicalExaminationItem physicalExaminationItem : pocdhd000040uv01.getPhysicalExaminationItemList()){
	        		physicalExamination = new PhysicalExamination();
	        		physicalExamination.setExaminationSn(commonService.getSequence(SEQ_NURSING_OPERATION));
	        		physicalExamination.setAnesthesiaSn(anesthesiaSn);
	        		physicalExamination.setOrgCode(pocdhd000040uv01.getOrganizationCode());
	        		physicalExamination.setOrgName(pocdhd000040uv01.getOrganizationName());
	        		physicalExamination.setCreateby(serviceId);
	        		physicalExamination.setUpdateTime(systemTime);
	        		physicalExamination.setDeleteFlag(Constants.NO_DELETE_FLAG);
	        		physicalExamination.setCreateTime(systemTime);
	        		physicalExamination.setUpdateCount(Constants.INSERT_UPDATE_COUNT);
	    			physicalExamination.setItemCode(physicalExaminationItem.getPhysicalExaminationItemCode());
	    			physicalExamination.setItemName(physicalExaminationItem.getPhysicalExaminationItemName());
	    			physicalExamination.setItemResult(physicalExaminationItem.getPhysicalExaminationItemResult());
	    			physicalExaminationList.add(physicalExamination);
	    		}
	    	}
		}
	    //更新操作
		if (Constants.VERSION_NUMBER_UPDATE.equals(pocdhd000040uv01
				.getVersionNumber())) {
	        Map<String, Object> condition = new HashMap<String, Object>();
	        condition.put("anesthesiaSn", anesthesiaSn);
	        condition.put("orgCode", pocdhd000040uv01.getOrganizationCode());
	        condition.put("deleteFlag", Constants.NO_DELETE_FLAG);
	        physicalExaminationList = commonService.selectByCondition(
	        		PhysicalExamination.class, condition);
		}
    } 
    
    /**
     *  麻醉输液用药List
     * 
     * @param pocdhd000040uv01
     */
    public void setAnesthesiaMedicalTransfuseList(POCDHD000040UV01 pocdhd000040uv01){
    	AnesthesiaMedicalTransfuse anesthesiaMedicalTransfuse = null;//麻醉输液用药表
    	//连续用药信息
		if (Constants.VERSION_NUMBER_INSERT.equals(pocdhd000040uv01
				.getVersionNumber())) {
	    	if(pocdhd000040uv01.getContinuousMedicationList() != null && pocdhd000040uv01.getContinuousMedicationList().size() > 0){
	    		for(ContinuousMedication continuousMedication : pocdhd000040uv01.getContinuousMedicationList()){
	    			//存在时间段上下限
	    			if(continuousMedication.getTimeQuantumList() != null && continuousMedication.getTimeQuantumList().size() > 0){
	    				for(TimeQuantum timeQuantum : continuousMedication.getTimeQuantumList()){
	            			anesthesiaMedicalTransfuse  = createNewAnesthesiaMedicalTransfuse("01");
	            			anesthesiaMedicalTransfuse.setMedicationCode(continuousMedication.getMedicationCode());
	            			anesthesiaMedicalTransfuse.setMedicationName(continuousMedication.getMedicationName());
	            			anesthesiaMedicalTransfuse.setDosage(continuousMedication.getDosage());
	            			anesthesiaMedicalTransfuse.setDosageUnit(continuousMedication.getDosageUnit());
	            			anesthesiaMedicalTransfuse.setTimeUpper(timeQuantum.getHigh());
	            			anesthesiaMedicalTransfuse.setTimeFloor(timeQuantum.getLow());
	            			anesthesiaMedicalTransfuse.setMedicationEvent(timeQuantum.getText());
	            			anesthesiaMedicalTransfuseList.add(anesthesiaMedicalTransfuse);
	    				}
	    			}else{//不存在时间段上下限
	        			anesthesiaMedicalTransfuse = createNewAnesthesiaMedicalTransfuse("01");
	        			anesthesiaMedicalTransfuse.setMedicationCode(continuousMedication.getMedicationCode());
	        			anesthesiaMedicalTransfuse.setMedicationName(continuousMedication.getMedicationName());
	        			anesthesiaMedicalTransfuse.setDosage(continuousMedication.getDosage());
	        			anesthesiaMedicalTransfuse.setDosageUnit(continuousMedication.getDosageUnit());
	        			anesthesiaMedicalTransfuseList.add(anesthesiaMedicalTransfuse);
	    			}
	    		}
	    	}
	       	//输液信息
	    	if(pocdhd000040uv01.getTransfuseList() != null && pocdhd000040uv01.getTransfuseList().size() > 0){
	    		for(TransfuseMessage transfuseMessage : pocdhd000040uv01.getTransfuseList()){
	    			
	    			if(transfuseMessage.getMedicationMessageList() != null && transfuseMessage.getMedicationMessageList().size() > 0){
	    				for(MedicationMessage medicationMessage : transfuseMessage.getMedicationMessageList()){
	            			if(medicationMessage.getTimeQuantumList() != null && medicationMessage.getTimeQuantumList().size() > 0){
	            				for(TimeQuantum timeQuantum : medicationMessage.getTimeQuantumList()){
		              				anesthesiaMedicalTransfuse = createNewAnesthesiaMedicalTransfuse("02");
		            				anesthesiaMedicalTransfuse.setDosage(transfuseMessage.getDosage());
		            				anesthesiaMedicalTransfuse.setDosageUnit(transfuseMessage.getDosageUnit());
		            				anesthesiaMedicalTransfuse.setDrugsPartCode(transfuseMessage.getTransfusePositionCode());
		            				anesthesiaMedicalTransfuse.setDrugsPartName(transfuseMessage.getTransfusePositionName());
		            				anesthesiaMedicalTransfuse.setMedicationCode(medicationMessage.getMedicationCode());
		            				anesthesiaMedicalTransfuse.setMedicationName(medicationMessage.getMedicationName()); 
		            				anesthesiaMedicalTransfuse.setTimeUpper(timeQuantum.getHigh());
		            				anesthesiaMedicalTransfuse.setTimeFloor(timeQuantum.getLow());
		            				anesthesiaMedicalTransfuse.setMedicationEvent(timeQuantum.getText());
		            				
		                			anesthesiaMedicalTransfuseList.add(anesthesiaMedicalTransfuse);
	            				}
	            			}else{
	            				anesthesiaMedicalTransfuse = createNewAnesthesiaMedicalTransfuse("02");
	            				anesthesiaMedicalTransfuse.setDosage(transfuseMessage.getDosage());
	            				anesthesiaMedicalTransfuse.setDosageUnit(transfuseMessage.getDosageUnit());
	            				anesthesiaMedicalTransfuse.setDrugsPartCode(transfuseMessage.getTransfusePositionCode());
	            				anesthesiaMedicalTransfuse.setDrugsPartName(transfuseMessage.getTransfusePositionName());
	            				anesthesiaMedicalTransfuse.setMedicationCode(medicationMessage.getMedicationCode());
	            				anesthesiaMedicalTransfuse.setMedicationName(medicationMessage.getMedicationName());
	                			anesthesiaMedicalTransfuseList.add(anesthesiaMedicalTransfuse);
	            			}
	    				}
	    			}else{
	        			anesthesiaMedicalTransfuse = createNewAnesthesiaMedicalTransfuse("02");
	        			anesthesiaMedicalTransfuse.setDosage(transfuseMessage.getDosage());
	        			anesthesiaMedicalTransfuse.setDosageUnit(transfuseMessage.getDosageUnit());
	        			anesthesiaMedicalTransfuse.setDrugsPartCode(transfuseMessage.getTransfusePositionCode());
	        			anesthesiaMedicalTransfuse.setDrugsPartName(transfuseMessage.getTransfusePositionName());
	        			anesthesiaMedicalTransfuseList.add(anesthesiaMedicalTransfuse);
	    			}
	    		}
	        	//间断用药信息
	        	if(pocdhd000040uv01.getInterruptMedicationList() != null && pocdhd000040uv01.getInterruptMedicationList().size() > 0){
	        		for(InterruptMedication interruptMedication : pocdhd000040uv01.getInterruptMedicationList()){
		    			anesthesiaMedicalTransfuse = createNewAnesthesiaMedicalTransfuse("03");
		    			anesthesiaMedicalTransfuse.setMedicationEvent(interruptMedication.getText());
		    			anesthesiaMedicalTransfuseList.add(anesthesiaMedicalTransfuse);
	        		}
	        	}
	    	}
    	}
	    //更新操作
		if (Constants.VERSION_NUMBER_UPDATE.equals(pocdhd000040uv01
				.getVersionNumber())) {
	        Map<String, Object> condition = new HashMap<String, Object>();
	        condition.put("anesthesiaSn", anesthesiaSn);
	        condition.put("orgCode", pocdhd000040uv01.getOrganizationCode());
	        condition.put("deleteFlag", Constants.NO_DELETE_FLAG);
	        anesthesiaMedicalTransfuseList = commonService.selectByCondition(
	        		AnesthesiaMedicalTransfuse.class, condition);
		}
    }
    //麻醉输液用药实体部分数据赋值
    public AnesthesiaMedicalTransfuse createNewAnesthesiaMedicalTransfuse(String type){
    	AnesthesiaMedicalTransfuse anesthesiaMedicalTransfuse = null;
    	anesthesiaMedicalTransfuse = new AnesthesiaMedicalTransfuse();
		anesthesiaMedicalTransfuse.setMedicationSn(commonService.getSequence(SEQ_NURSING_OPERATION));
		anesthesiaMedicalTransfuse.setAnesthesiaSn(anesthesiaSn);
		anesthesiaMedicalTransfuse.setMedicationType(type);//输液信息
		anesthesiaMedicalTransfuse.setCreateby(serviceId);
		anesthesiaMedicalTransfuse.setCreateTime(systemTime);
		anesthesiaMedicalTransfuse.setUpdateCount(Constants.INSERT_UPDATE_COUNT);
		anesthesiaMedicalTransfuse.setUpdateTime(systemTime);
		anesthesiaMedicalTransfuse.setDeleteFlag(Constants.NO_DELETE_FLAG);

		return anesthesiaMedicalTransfuse;
    }
    /**
     *  入出量信息List
     * 
     * @param pocdhd000040uv01
     */
    public void setInOutDoseList(POCDHD000040UV01 pocdhd000040uv01){
    	InOutDose inOutDose = null;//入出量信息实体
    	//入出量信息
		if (Constants.VERSION_NUMBER_INSERT.equals(pocdhd000040uv01
				.getVersionNumber())) {
	    	if(pocdhd000040uv01.getInOutDoseMessageList() != null && pocdhd000040uv01.getInOutDoseMessageList().size() > 0){
	    		for(InOutDoseMessage inOutDoseMessage : pocdhd000040uv01.getInOutDoseMessageList()){
	    			inOutDose = new InOutDose();
	    			inOutDose.setInOutDoseSn(commonService.getSequence(SEQ_NURSING_OPERATION));
	    			inOutDose.setAnesthesiaSn(anesthesiaSn);
	    			inOutDose.setInOutItemCode(inOutDoseMessage.getInOutDoseCode());
	    			inOutDose.setInOutItemName(inOutDoseMessage.getInOutDoseName());
	    			inOutDose.setDose(inOutDoseMessage.getInOutDoseValue());
	    			inOutDose.setDoseUnit(inOutDoseMessage.getInOutDoseUnit());
	    			inOutDose.setCreateby(serviceId);
	    			inOutDose.setCreateTime(systemTime);
	    			inOutDose.setUpdateTime(systemTime);
	    			inOutDose.setDeleteFlag(Constants.NO_DELETE_FLAG);
	    			inOutDose.setUpdateCount(Constants.INSERT_UPDATE_COUNT);
	    			inOutDoseList.add(inOutDose);
	    		}
	    	}
    	}
	    //更新操作
		if (Constants.VERSION_NUMBER_UPDATE.equals(pocdhd000040uv01
				.getVersionNumber())) {
	        Map<String, Object> condition = new HashMap<String, Object>();
	        condition.put("anesthesiaSn", anesthesiaSn);
	        condition.put("orgCode", pocdhd000040uv01.getOrganizationCode());
	        condition.put("deleteFlag", Constants.NO_DELETE_FLAG);
	        inOutDoseList = commonService.selectByCondition(
	        		InOutDose.class, condition);
		}
    }
    // $Author:wei_peng
    // $Date:2012/10/25 17:30
    // $[BUG]0010715 ADD BEGIN
    /**
     * 从消息中获取CDA图片
     * @param pocdhd000040uv01 消息信息
     * @return CDA图片信息
     */
    public MedicalImaging getCDAImage(POCDHD000040UV01 pocdhd000040uv01,
            String flag)
    {
        MedicalImaging CDAImage = new MedicalImaging();
        CDAImage.setImagingSn(commonService.getSequence(MEDICAL_IMAGING_SEQUENCE)); // 设置影像内部序列号
        CDAImage.setImageContent(getImageContentAfterDecode(pocdhd000040uv01.getImageText())); // 设置影像内容
        CDAImage.setPromptMessage(pocdhd000040uv01.getPrompt()); // 设置提示信息
        CDAImage.setDocumentSn(clinicalDocument.getDocumentSn());// 文档内部序列号

        // 图片扩展名为image/jpg的形式时进行判断取值
        String imageExtension = pocdhd000040uv01.getImageExtension();
        if (!StringUtils.isEmpty(imageExtension))
        {
            if (imageExtension.indexOf("/") > 0)
            {
                if (imageExtension.split("/").length > 1)
                {
                    imageExtension = imageExtension.split("/")[1];
                }
                else
                {
                    imageExtension = "";
                }
            }
        }
        CDAImage.setImageFormat(imageExtension); // 设置影像格式
        CDAImage.setCreateTime(systemTime); // 设置该记录创建时间
        CDAImage.setUpdateTime(systemTime); // 设置该记录更新时间
        CDAImage.setUpdateCount(Constants.INSERT_UPDATE_COUNT); // 设置该记录初始更新回数
        CDAImage.setDeleteFlag(Constants.NO_DELETE_FLAG); // 设置该记录初始删除标记
        // $Author: tong_meng
        // $Date: 2013/8/30 15:00
        // [BUG]0036757 ADD BEGIN
        CDAImage.setCreateby(serviceId); // 设置创建人
        // [BUG]0036757 ADD END
        return CDAImage;
    }

    /**
     * 对二进制的图片信息进行base64的转码操作
     * @param imageText 二进制图片信息
     * @return 转码后的字节数据图片
     */
    public byte[] getImageContentAfterDecode(String imageText)
    {
        byte[] bt = null;
        if (imageText != null && !imageText.isEmpty())
        {
            bt = Base64.decodeBase64(imageText);
        }
        return bt;
    }

    // $[BUG]0010715 ADD END

    /**
     * 封装新增或者更新的电子病历对象
     * @param pocdhd000040uv01,flag
     * @return 电子病历
     */
    public ClinicalDocument getClinicalDocument(
            POCDHD000040UV01 pocdhd000040uv01, String flag)
    {
        if (Constants.VERSION_NUMBER_INSERT.equals(flag))
        {
            clinicalDocument = new ClinicalDocument();

            // 文档内部序列号
            clinicalDocument.setDocumentSn(commonService.getSequence(DOCUMENT_SEQUENCE));
            // 就诊内部序列号
            clinicalDocument.setVisitSn(medicalVisit.getVisitSn());
            // 患者内部序列号
            clinicalDocument.setPatientSn(medicalVisit.getPatientSn());
            // 域代码
            clinicalDocument.setPatientDomain(patientDomain);
            // 患者本地ID
            clinicalDocument.setPatientLid(patientLid);
            // 文档本地ID
            clinicalDocument.setDocumentLid(pocdhd000040uv01.getDocumentLid());
            // 创建时间
            clinicalDocument.setCreateTime(systemTime);
            // 更新回数
            clinicalDocument.setUpdateCount(Constants.INSERT_UPDATE_COUNT);

            clinicalDocument.setCreateby(serviceId); // 设置创建人
        }
        else
        {
            clinicalDocument.setUpdateby(serviceId);
            clinicalDocument.setUpdateTime(systemTime);// 设置更新时间
        }
        // 文档作者ID
        clinicalDocument.setDocumentAuthor(pocdhd000040uv01.getDocumentAuthor());
        // 文档作者姓名
        clinicalDocument.setDocumentAuthorName(pocdhd000040uv01.getDocumentAuthorName());
        // 作成时间
        clinicalDocument.setWriteTime(DateUtils.stringToDate(pocdhd000040uv01.getWriteTime()));
        
        String document_type = Constants.obtainDocumentTypeMap().get(pocdhd000040uv01.getDocumentType());
        // 文档类型
        clinicalDocument.setDocumentType(document_type);

        // $Author :jia_yanqing
        // $Date : 2012/7/31 10:33$
        // [BUG]0008337 MODIFY BEGIN
        // $Author :tong_meng
        // $Date : 2012/7/1 16:33$
        // [BUG]0007418 ADD BEGIN
        // 文档类型名称
        clinicalDocument.setDocumentTypeName(Constants.obtainDocumentTypeNameMap().get(document_type));
        // [BUG]0007418 ADD END
        // [BUG]0008337 MODIFY END

        // $Author:wei_peng
        // $Date: 2012/7/16 13:43$
        // [BUG]0007946 ADD BEGIN
        // 文档类型名称
        clinicalDocument.setServiceId(pocdhd000040uv01.getServiceId());
        // [BUG]0007946 ADD END

        // $Author:wei_peng
        // $Date: 2012/7/24 11:23$
        // [BUG]0008061 ADD BEGIN
        // 文档修改者ID
        clinicalDocument.setDocumentModifier(pocdhd000040uv01.getDocumentModifier());
        // 文档修改者名称
        clinicalDocument.setDocumentModifierName(pocdhd000040uv01.getDocumentModifierName());
        // 文档修改时间
        clinicalDocument.setModifyTime(DateUtils.stringToDate(pocdhd000040uv01.getModifyTime()));
        // 获取最近审核的审核人信息
        ReviewPersonDto reviewPerson = getLatestReviewPerson(pocdhd000040uv01.getReviewPersons());
        // 文档审核者ID
        clinicalDocument.setReviewPerson(reviewPerson.getReviewPerson());
        // 文档审核者姓名
        clinicalDocument.setReviewPersonName(reviewPerson.getReviewPersonName());
        // 文档审核时间
        clinicalDocument.setReviewTime(DateUtils.stringToDate(reviewPerson.getReviewTime()));
        // [BUG]0008061 ADD END

        // 文档名称
        clinicalDocument.setDocumentName(pocdhd000040uv01.getDocumentName());

        // Author:jia_yanqing
        // Date: 2013/4/10 14:23
        // [BUG]0014977 MODIFY BEGIN
        // 提交时间（即签章时间或修改时间或作成时间）
        if (pocdhd000040uv01.getSignTime() != null
            && !"".equals(pocdhd000040uv01.getSignTime()))
        {
            clinicalDocument.setSubmitTime(DateUtils.stringToDate(pocdhd000040uv01.getSignTime()));
        }
        else if (pocdhd000040uv01.getModifyTime() != null
            && !"".equals(pocdhd000040uv01.getModifyTime()))
        {
            clinicalDocument.setSubmitTime(DateUtils.stringToDate(pocdhd000040uv01.getModifyTime()));
        }
        else if (clinicalDocument.getWriteTime() != null
            && !"".equals(pocdhd000040uv01.getWriteTime()))
        {
            clinicalDocument.setSubmitTime(DateUtils.stringToDate(pocdhd000040uv01.getWriteTime()));
        }
        // [BUG]0014977 MODIFY END

        // 电子签名时间
        clinicalDocument.setSignTime(DateUtils.stringToDate(pocdhd000040uv01.getSignTime()));
        // 电子签章ID
        clinicalDocument.setSignatureId(pocdhd000040uv01.getSignatureId());
        // 医疗机构编码
        clinicalDocument.setOrgCode(pocdhd000040uv01.getOrganizationCode());
        // 医疗机构名称
        clinicalDocument.setOrgName(pocdhd000040uv01.getOrganizationName());
        // 更新时间
        clinicalDocument.setUpdateTime(systemTime);
        // 删除标志
        clinicalDocument.setDeleteFlag(Constants.NO_DELETE_FLAG);

        return clinicalDocument;
    }

    /**
     * 根据域ID，病人本地LID，病历号查询是否已经存在消息中的住院病案
     * 
     * @param pocdhd000040uv01
     * @return ClinicalDocument
     */
    public ClinicalDocument checkClinicalDocument(
            POCDHD000040UV01 pocdhd000040uv01,MedicalVisit medicalVisit)
    {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("patientDomain", patientDomain);
        map.put("patientLid", patientLid);
        map.put("documentLid", pocdhd000040uv01.getDocumentLid());
        // Author :jia_yanqing
        // Date : 2013/11/11 10:09
        // [BUG]0038477 ADD BEGIN
        map.put("serviceId", pocdhd000040uv01.getServiceId());
        // [BUG]0038477 ADD END
        map.put("orgCode", pocdhd000040uv01.getOrganizationCode());
        map.put("visitSn", medicalVisit.getVisitSn());
        map.put("deleteFlag", Constants.NO_DELETE_FLAG);
        
        List<Object> clinicalDocumentList = commonService.selectByCondition(
                ClinicalDocument.class, map);
        if (clinicalDocumentList != null && !clinicalDocumentList.isEmpty())
        {
            clinicalDocument = (ClinicalDocument) clinicalDocumentList.get(0);
        }

        return clinicalDocument;
    }

    // $Author :jin_peng
    // $Date : 2013/10/25 10:34$
    // [BUG]0038524 MODIFY BEGIN
    /**
     * 获取电子病历关联的就诊记录
     * @param pocdhd000040uv01
     * @return 就诊记录
     */
    public MedicalVisit getMedicalVisit(POCDHD000040UV01 pocdhd000040uv01)
    {

        MedicalVisit medicalVisit = null;

        // 如果为麻醉，术前文档则通过医嘱查询就诊
        /*
         * if (isAnesthesiaPreoperation(serviceId)) { //
         * 门诊和住院分别用申请单号和医嘱号获取相应手术医嘱及就诊 Map<String, Object> map = new
         * HashMap<String, Object>();
         * 
         * map.put("deleteFlag", Constants.NO_DELETE_FLAG);
         * map.put("patientLid", patientLid); map.put("patientDomain",
         * patientDomain);
         * 
         * String orderOrRequest = "";
         * 
         * for (String orderOrRequestNo : pocdhd000040uv01.getOrderLids()) {
         * orderOrRequest += orderOrRequestNo + "，";
         * 
         * // 门诊住院统一节点但不同意义，门诊：申请单号，住院：医嘱号 if
         * (Constants.PATIENT_DOMAIN_OUTPATIENT.equals(patientDomain)) {
         * map.put("requestNo", orderOrRequestNo); } else { map.put("orderLid",
         * orderOrRequestNo); }
         * 
         * List<Object> procedureOrders = commonService.selectByCondition(
         * ProcedureOrder.class, map);
         * 
         * // 判断报告在数据库中是否存在 if (procedureOrders != null &&
         * !procedureOrders.isEmpty()) { ProcedureOrder procedureOrder =
         * (ProcedureOrder) procedureOrders.get(0);
         * 
         * // 根据手术医嘱中visitSn获取相应就诊 Map<String, Object> mapVisit = new
         * HashMap<String, Object>();
         * 
         * mapVisit.put("visitSn", procedureOrder.getVisitSn());
         * 
         * mapVisit.put("deleteFlag", Constants.NO_DELETE_FLAG);
         * 
         * List<Object> medicalVisits = commonService.selectByCondition(
         * MedicalVisit.class, mapVisit);
         * 
         * if (medicalVisits != null && !medicalVisits.isEmpty()) { medicalVisit
         * = (MedicalVisit) medicalVisits.get(0);
         * 
         * break; } } }
         * 
         * if (medicalVisit == null) {
         * loggerBSDOCUMENT.error("Message:{},validate:{}",
         * pocdhd000040uv01.toString(),
         * "麻醉或术前文档获取相应医嘱及就诊为空 : orderLidOrRequestNo = " + orderOrRequest +
         * "；patientLid = " + patientLid + "；patientDomain = " + patientDomain);
         * } } else {
         */
        // 就诊次数
        String visitTimes = pocdhd000040uv01.getVisitTimes();

        String visitTypeCode = pocdhd000040uv01.getVisitTypeCode();
        
        if(Constants.INPATIENT_FRONTPAGE_SERVICE_ID.equals(pocdhd000040uv01.getMessage().getServiceId())){
        	visitTypeCode = Constants.VISIT_TYPE_CODE_INPATIENT;
        	// 根据域ID,患者本地ID，就诊次数获取就诊记录
        	Map<String, Object> condition = new HashMap<String, Object>();
        	condition.put("patientDomain",patientDomain);
            condition.put("patientLid",patientLid);
            condition.put("orgCode",pocdhd000040uv01.getOrganizationCode());
            condition.put("deleteFlag", Constants.NO_DELETE_FLAG);
            condition.put("visitTimes",visitTimes);
            condition.put("visitTypeCode", visitTypeCode);
            List visitResult = new ArrayList<MedicalVisit>();
            visitResult = commonService.selectByCondition(MedicalVisit.class, condition);
            if (visitResult != null && !visitResult.isEmpty())
            {
            	medicalVisit = (MedicalVisit) visitResult.get(0);
            }
        }else{
        	// 根据域ID,患者本地ID，就诊流水号获取就诊记录
            medicalVisit = commonService.mediVisit(patientDomain, patientLid,visitTimes,
                    pocdhd000040uv01.getOrganizationCode(),pocdhd000040uv01.getVisitOrdNo(),visitTypeCode);
        }
       
        return medicalVisit;
    }

    // [BUG]0038524 MODIFY END

    /**
     * 获取最近审核的审核人信息
     * @param reviewPersons
     * @return
     */
    public ReviewPersonDto getLatestReviewPerson(
            List<ReviewPersonDto> reviewPersons)
    {
        ReviewPersonDto reviewPerson = new ReviewPersonDto();
        for (int i = 0; i < reviewPersons.size(); i++)
        {
            if (i == 0)
            {
                reviewPerson = reviewPersons.get(i);
            }
            else
            {
                Date date1 = DateUtils.stringToDate(reviewPerson.getReviewTime());
                Date date2 = DateUtils.stringToDate(reviewPersons.get(i).getReviewTime());
                // $Author:wei_peng
                // $Date:2012/8/24 11:52
                // $[BUG]0009159 MODIFY BEGIN
                if (date1 != null && date2 != null && date2.after(date1))
                {
                    reviewPerson = reviewPersons.get(i);
                }
                // $[BUG]0009159 MODIFY END
            }
        }
        return reviewPerson;
    }

    /**
     * CVIS EP手术操作记录报告判断手术医嘱是否存在
     * @param pocdhd000040uv01
     * @return
     */
    private boolean checkProcedureOrder(POCDHD000040UV01 pocdhd000040uv01,
            boolean flag, MedicalVisit medicalVisit)
    {
        // 门诊和住院分别用申请单号和医嘱号获取相应手术医嘱进行关联关系验证
        Map<String, Object> map = new HashMap<String, Object>();

        map.put("deleteFlag", Constants.NO_DELETE_FLAG);
        map.put("patientLid", patientLid);
        map.put("patientDomain", pocdhd000040uv01.getPatientDomain());
        map.put("orgCode", pocdhd000040uv01.getOrganizationCode());
        map.put("visitSn", medicalVisit.getVisitSn());

        boolean isExist = true;

        for (String orderOrRequestNo : pocdhd000040uv01.getOrderLids())
        {
            // 门诊住院统一节点但不同意义，门诊：申请单号，住院：医嘱号
       //     if (Constants.PATIENT_DOMAIN_OUTPATIENT.equals(pocdhd000040uv01.getPatientDomain()))
        	if (Constants.lstDomainOutPatient().contains(
        			pocdhd000040uv01.getPatientDomain())
					&& Constants.lstVisitTypeOutPatient().contains(
							pocdhd000040uv01.getVisitTypeCode()))
            {
                map.put("requestNo", orderOrRequestNo);
            }
            else
            {
                map.put("orderLid", orderOrRequestNo);
            }

            List<Object> procedureOrders = commonService.selectByCondition(
                    ProcedureOrder.class, map);

            // 判断报告在数据库中是否存在
            if (procedureOrders == null || procedureOrders.isEmpty())
            {
                isExist = false;
                if (flag)
                {
                    loggerBS327.error("Message:{},validate:{}",
                            pocdhd000040uv01.toString(), "住院医嘱号/门诊申请单号为 : "
                                + orderOrRequestNo + " 的手术医嘱/手术申请单不存在！");
                }
                break;
            }
        }
        return isExist;
    }

    /**
     * 判断消息是否为门急诊病历消息
     * @param message
     * @return 是返回true，否则返回false
     */
    private boolean isOutpatientMessage(POCDHD000040UV01 message)
    {
        return Constants.OUTPATIENT_SERVICE_ID.equals(message.getServiceId());
    }

    /**
     * 判断消息的交互类型是否为删除
     * @param message
     * @return 是返回true，否则返回false
     */
    private boolean isDeleteMessage(POCDHD000040UV01 message)
    {
        return Constants.VERSION_NUMBER_WITHDRAW.equals(message.getVersionNumber());
    }

    /**
     * CVIS EP手术操作记录报告
     * @param serviceId
     * @return
     */
    private boolean isCVISEPReport(String serviceId)
    {
        return Constants.SOURCE_CVIS_EXAM.equals(serviceId);
    }

    // $Author :jin_peng
    // $Date : 2013/10/25 10:34$
    // [BUG]0038524 ADD BEGIN
    /**
     * 是否麻醉，术前文档
     * @param serviceId 服务id
     * @return 是否麻醉，术前文档标识
     */
    /*
     * private boolean isAnesthesiaPreoperation(String serviceId) { return
     * Constants.SERVICE_ID_ANESTHESIA.equals(serviceId) ||
     * Constants.SERVICE_ID_PREOPERATION.equals(serviceId); }
     */

    // [BUG]0038524 ADD END

    // 获取病历文书对应的日志处理对象
    private Logger getDocumentLogger(String serviceId)
    {
        Logger result = null;
        Field field = null;
        try
        {
            field = POCDHD000040UV01Writer.class.getDeclaredField("logger"
                + serviceId.toUpperCase());
            field.setAccessible(true);
            result = (Logger) field.get(this);
        }
        catch (Exception e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return result;
    }
    
    public List<Object> getDiagnosisMessage(POCDHD000040UV01 pocdhd000040uv01)
    {
    	boolean mainFlag = true;
        List<Object> diagnosisList = new ArrayList<Object>();
        List<com.yly.cdr.hl7.dto.Diagnosis> mainDiagnosisList = pocdhd000040uv01.getDiagnosis();
        // 循环取出消息中的主诊断
        for (com.yly.cdr.hl7.dto.Diagnosis mainDiagnosis : mainDiagnosisList)
        {
            // 设置每个诊断共同的属性
            Diagnosis diagnosis = getNewDiagnosis();
            // 诊断类别代码
            diagnosis.setDiagnosisTypeCode(Constants.DOMAIN_DIAGNOSIS_TYPE_OUTPATIENT);
            // 诊断类别名称
            diagnosis.setDiagnosisTypeName(DictionaryCache.getDictionary(
                    Constants.DOMAIN_DIAGNOSIS_TYPE).get(Constants.DOMAIN_DIAGNOSIS_TYPE_OUTPATIENT));

            // 疾病代码
            diagnosis.setDiseaseCode(mainDiagnosis.getDiseaseCode());
            // 疾病名称
            diagnosis.setDiseaseName(mainDiagnosis.getDiseaseName());
            // 诊断时间
            diagnosis.setDiagnosisDate(DateUtils.stringToDate(mainDiagnosis.getDiagnosisDate()));
            // 医疗机构代码
            diagnosis.setOrgCode(pocdhd000040uv01.getOrganizationCode());
            
            // 医疗机构名称
            diagnosis.setOrgName(pocdhd000040uv01.getOrganizationName());
            // [BUG]0040270 ADD END
            
            if (mainFlag)
            {
                // 主诊断标识
                diagnosis.setMainDiagnosisFlag(Constants.MAIN_DIAGNOSIS);
                mainFlag = false;
            }
            else
                // 次诊断标识
                diagnosis.setMainDiagnosisFlag(Constants.MINOR_DIAGNOSIS);

            diagnosisList.add(diagnosis);

        }
        return diagnosisList;
    }
    
    private Diagnosis getNewDiagnosis()
    {
        Diagnosis diagnosis = new Diagnosis();

        // 创建时间
        diagnosis.setCreateTime(systemTime);
        // 更新时间
        diagnosis.setUpdateTime(systemTime);
        // 更新回数
        diagnosis.setUpdateCount(Constants.INSERT_UPDATE_COUNT);
        // 删除标志
        diagnosis.setDeleteFlag(Constants.NO_DELETE_FLAG);
        // 就诊内部序列号
        diagnosis.setVisitSn(medicalVisit.getVisitSn());
        // 患者内部序列号
        diagnosis.setPatientSn(medicalVisit.getPatientSn());
        // 域代码
        diagnosis.setPatientDomain(medicalVisit.getPatientDomain());
        // 患者本地ID
        diagnosis.setPatientLid(medicalVisit.getPatientLid());
        // 文档id
        diagnosis.setDocumentSn(clinicalDocument.getDocumentSn());
        // 设置创建人
        diagnosis.setCreateby(serviceId); 

        return diagnosis;
    }
}
