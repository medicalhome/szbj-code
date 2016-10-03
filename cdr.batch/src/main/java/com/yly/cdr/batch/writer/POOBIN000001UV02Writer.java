package com.yly.cdr.batch.writer;

import java.util.ArrayList;
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

import com.yly.cdr.cache.DictionaryCache;
import com.yly.cdr.core.Constants;
import com.yly.cdr.entity.Diagnosis;
import com.yly.cdr.entity.MedicalVisit;
import com.yly.cdr.hl7.dto.MainDiagnosis;
import com.yly.cdr.hl7.dto.poobin000001uv02.POOBIN000001UV02;
import com.yly.cdr.service.CommonService;
import com.yly.cdr.service.DiagnosisService;
import com.yly.cdr.util.DataMigrationUtils;
import com.yly.cdr.util.DateUtils;
import com.yly.cdr.util.DictionaryUtils;
import com.yly.cdr.util.StringUtils;

/**
 * 诊断Writer 注意：这里需要根据业务，添加判断条件。如：
 * 1：如果当前消息是子消息，父消息如果存在，则保存子消息内容；如果父消息不存在，则不保存子消息内容。 2：如果是父消息，直接保存父消息内容。
 * 
 * @author zhangbin
 * @version 1.0
 */

@Component(value = "poobin000001uv02Writer")
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class POOBIN000001UV02Writer implements BusinessWriter<POOBIN000001UV02>
{
    private static final Logger logger = LoggerFactory.getLogger(POOBIN000001UV02Writer.class);

    private static final Logger loggerBS301 = LoggerFactory.getLogger("BS301");

    @Autowired
    private CommonService commonService;

    @Autowired
    private DiagnosisService diagnosisService;

    // $Author :wu_jianfeng
    // $Date : 2012/9/25 15:00$
    // [BUG]0009863 MODIFY BEGIN
    // $Author :tong_meng
    // $Date : 2012/9/6 15:00$
    // [BUG]0008782 MODIFY BEGIN
    // // 就诊内部序列号
    // private BigDecimal visitSn;
    //
    // // 患者内部序列号
    // private BigDecimal patientSn;
    //
    // // 就诊次数
    // private Integer visitTimes;
    //
    // // 患者本地ID
    // private String patientLid;
    //
    // // 更新新增标志
    // private String newUpFlag;

    
    // 就诊记录
    private MedicalVisit medicalVisitResult;

    // [BUG]0008782 MODIFY END

    // 系统时间取得
    private Date sysdate = DateUtils.getSystemTime();

    // [BUG]0009863 MODIFY END
    
    // $Author: tong_meng
    // $Date: 2013/8/30 15:00
    // [BUG]0036757 ADD BEGIN
    private String serviceId;
    // [BUG]0036757 ADD END

    private List<Diagnosis> deleteList;
    
    /**
     * 数据校验
     * 
     * @param baseDto
     */
    // $Author :tong_meng
    // $Date : 2012/9/6 15:00$
    // [BUG]0008782 MODIFY BEGIN
    public boolean validate(POOBIN000001UV02 poobin000001uv02)
    {
        // Author :jia_yanqing
        // Date : 2013/1/22 15:00
        // [BUG]0042085 MODIFY BEGIN
        if(poobin000001uv02.getOrgCode() == null || "".equals(poobin000001uv02.getOrgCode()))
        {
            poobin000001uv02.setOrgCode(Constants.HOSPITAL_CODE);
            poobin000001uv02.setOrgName(Constants.HOSPITAL_NAME);
        }
        // $Author:tong_meng
        // $Date:2013/12/03 11:00
        // [BUG]0040270 ADD BEGIN
        /*
        if(!poobin000001uv02.getOrgCode().equals(poobin000001uv02.getMessage().getOrgCode()))
        {
            logger.error("消息头中的医疗机构代码与消息体中的医疗机构代码不一致！" +
                    "消息头医疗机构代码：{}，消息体医疗机构代码：{}",
                    poobin000001uv02.getMessage().getOrgCode(),
                    poobin000001uv02.getOrgCode());
            loggerBS301.error("消息头中的医疗机构代码与消息体中的医疗机构代码不一致！" +
                    "消息头医疗机构代码：{}，消息体医疗机构代码：{}",
                    poobin000001uv02.getMessage().getOrgCode(),
                    poobin000001uv02.getOrgCode());
            return false;
        }
        */
        // [BUG]0040270 ADD END
        // [BUG]0042085 MODIFY END
        
        List<MainDiagnosis> mainDiagnosises = poobin000001uv02.getDiagnosis();
        // $Author :chang_xuewen
        // $Date : 2013/8/1 17:00
        // $[BUG]0035553 MODIFY BEGIN
        if (mainDiagnosises != null && !mainDiagnosises.isEmpty())
            for (MainDiagnosis mainDiagnosis : mainDiagnosises)
            {
                /*
                 * List<MinorDiagnosis> minorDiagnosises =
                 * mainDiagnosis.getMinorDiagnosis();
                 */
                /*
                 * if (minorDiagnosises != null && !minorDiagnosises.isEmpty())
                 * {
                 */
                /*
                 * for (MinorDiagnosis minorDiagnosis : minorDiagnosises) {
                 */
            	// $Author :chang_xuewen
                // $Date : 2013/8/29 14:00
                // $[BUG]0036738 MODIFY BEGIN
            	if(Constants.lstDomainInPatient().contains(poobin000001uv02.getPatientDomain()) &&
            			Constants.lstVisitTypeInPatient().contains(poobin000001uv02.getVisitType())){
            		if (!StringUtils.isNotNullAll(mainDiagnosis.getDiagnosisType(),
                            mainDiagnosis.getDiseaseName()/*,
                            mainDiagnosis.getDiagnosisDate(),
                            mainDiagnosis.getDiagnosisDoctor(),
                            mainDiagnosis.getDiagnosisDoctorName(),
                            mainDiagnosis.getDiagnosticDept(),
                            mainDiagnosis.getDiagnosticDeptName()*/))
            		{
            			loggerBS301.error(
                                "Message:{},validate:{}",
                                poobin000001uv02.toString(),
                                "非空字段验证未通过！诊断类别编码不能为空，"
                                    + mainDiagnosis.getDiagnosisType()
                                    + "，诊断结果-疾病名称不能为空，"
                                    + mainDiagnosis.getDiseaseName()/* + "，诊断时间不能为空，"
                                    + mainDiagnosis.getDiagnosisDate()*/
//                                    + "，诊断医生编码不能为空，"
//                                    + mainDiagnosis.getDiagnosisDoctor()
//                                    + "，诊断医生姓名不能为空："
//                                    + mainDiagnosis.getDiagnosisDoctorName()
/*                                    + "诊断科室编码不能为空："
                                    + mainDiagnosis.getDiagnosticDept()
                                    + "，诊断科室名称不能为空："
                                    + mainDiagnosis.getDiagnosticDeptName()*/);
                        return false;
            		}
            	}
            	else
            	{
            		if (!StringUtils.isNotNullAll(mainDiagnosis.getDiagnosisType(),
                            mainDiagnosis.getDiseaseCode()/*,
                            mainDiagnosis.getDiseaseName(),
                            mainDiagnosis.getDiagnosisDate(),
                            mainDiagnosis.getDiagnosisDoctor(),
                            mainDiagnosis.getDiagnosisDoctorName(),
                            mainDiagnosis.getDiagnosticDept(),
                            mainDiagnosis.getDiagnosticDeptName()*/))
                    {
                        loggerBS301.error(
                                "Message:{},validate:{}",
                                poobin000001uv02.toString(),
                                "非空字段验证未通过！诊断类别编码不能为空，"
                                    + mainDiagnosis.getDiagnosisType()
                                    + "，诊断结果-疾病编码不能为空，"
                                    + mainDiagnosis.getDiseaseCode()
//                                    + "，诊断结果-疾病名称不能为空，"
//                                    + mainDiagnosis.getDiseaseName() + "，诊断时间不能为空，"
//                                    + mainDiagnosis.getDiagnosisDate()
//                                    + "，诊断医生编码不能为空，"
//                                    + mainDiagnosis.getDiagnosisDoctor()
//                                    + "，诊断医生姓名不能为空："
//                                    + mainDiagnosis.getDiagnosisDoctorName()
/*                                    + "诊断科室编码不能为空："
                                    + mainDiagnosis.getDiagnosticDept()
                                    + "，诊断科室名称不能为空："
                                    + mainDiagnosis.getDiagnosticDeptName()*/);
                        return false;
                    }
            	}
                // $[BUG]0036738 MODIFY END
                
                /*
                 * } }
                 */
                // $[BUG]0035553 MODIFY END
            }
        return true;
    }

    // [BUG]0008782 MODIFY END

    @Override
    public boolean checkDependency(POOBIN000001UV02 poobin000001uv02,
            boolean flag)
    {
        // $Author :wu_jianfeng
        // $Date : 2012/9/25 15:00$
        // [BUG]0009863 MODIFY BEGIN

        // $Author :jia_yanqing
        // $Date : 2012/6/6 19:00$
        // [BUG]0006775 MODIFY BEGIN
        // $Author :tongmeng
        // $Date : 2012/5/28 14:00$
        // [BUG]0006657 MODIFY BEGIN
        // 患者本地ID
        logger.info("诊断消息的患者本地ID {}", poobin000001uv02.getPatientLid());
        // [BUG]0006657 MODIFY END
        // [BUG]0006775 MODIFY END

        // $Author :jia_yanqing
        // $Date : 2012/6/7 15:00$
        // [BUG]0006798 MODIFY BEGIN

        if (isNewMessage(poobin000001uv02))
        {
        	if(checkMedicalVisit(poobin000001uv02, flag)){
        		logger.info("就诊信息存在");
        		return true;
        	} else {
        		logger.info("就诊信息不存在，{}", poobin000001uv02);
        		return false;
        	}
        }
        if(isCancelMessage(poobin000001uv02)){
        	if(checkMedicalVisit(poobin000001uv02, flag)){
        		logger.info("就诊信息存在");
        		List<String> noExistList = checkDiagnosisExist(poobin000001uv02);
        		if(noExistList == null || noExistList.isEmpty()){
        			logger.info("需要进行删除操作的诊断存在");
        			return true;
        		} else {
        			logger.info("需要进行删除操作的诊断不存在，serialNum={}", noExistList);
        			return false;
        		}
        	} else {
        		logger.info("就诊信息不存在，{}", poobin000001uv02);
        		return false;
        	}
        }
        
        logger.error("MessageId:{};没有对应的消息交互类型！",
                poobin000001uv02.getMessage().getId());
        if (flag)
        {
            loggerBS301.error("Message:{},checkDependency:{}",
                    poobin000001uv02.toString(), "没有对应的消息交互类型！");
        }
        return false;
        // [BUG]0006798 MODIFY END
        // [BUG]0009863 MODIFY BEGIN

    }

    // $Author :wu_jianfeng
    // $Date : 2012/9/25 15:00$
    // [BUG]0009863 ADD BEGIN
    private boolean isNewMessage(POOBIN000001UV02 poobin000001uv02)
    {
        return Constants.NEW_MESSAGE_FLAG.equals(poobin000001uv02.getNewUpFlg())
        		|| Constants.V2_NEW_MESSAGE_FLAG.equals(poobin000001uv02.getNewUpFlg());
    }

    // [BUG]0009863 ADD END

    private boolean isCancelMessage(POOBIN000001UV02 poobin000001uv02){
    	return Constants.CANCEL_MESSAGE_FLAG.equals(poobin000001uv02.getNewUpFlg())
        		|| Constants.V2_CANCEL_MESSAGE_FLAG.equals(poobin000001uv02.getNewUpFlg());
    }
    
    /**
     * 就诊相关信息判断
     * @param  诊断消息映射数据
     * @return true:存在；false:不存在
     */
    private boolean checkMedicalVisit(POOBIN000001UV02 poobin000001uv02,
            boolean flag)
    {
        // $Author :wu_jianfeng
        // $Date : 2012/9/25 15:00$
        // [BUG]0009863 MODIFY BEGIN
        // $Author :jia_yanqing
        // $Date : 2012/6/15 15:00$
        // [BUG]0006966 MODIFY BEGIN
        String visitTimes = poobin000001uv02.getVisitTimes();
        String patientLid = poobin000001uv02.getPatientLid();
        String patientDomain = poobin000001uv02.getPatientDomain();

        // [BUG]0006966 MODIFY END

        String strVisitOrdNo = poobin000001uv02.getVisitOrdNo();
        // $Author:tong_meng
        // $Date:2013/12/03 11:00
        // [BUG]0040270 MODIFY BEGIN
        // 就诊信息取得
        medicalVisitResult = commonService.mediVisit(patientDomain, patientLid,
                visitTimes,poobin000001uv02.getOrgCode(),strVisitOrdNo,poobin000001uv02.getVisitType());
        
        if (medicalVisitResult != null)
        {
            logger.info("患者域ID{}" + patientDomain + "患者本地ID{}" + patientLid
                + "就诊次数{}" + visitTimes + "医疗机构代码"
                + poobin000001uv02.getOrgCode());
        }
        else
        {
            // $Author :jia_yanqing
            // $Date : 2012/6/6 19:00$
            // [BUG]0006775 MODIFY BEGIN
            logger.error("MessageId:{};患者本地ID：{} 的就诊关联记录不存在",
                    poobin000001uv02.getMessage().getId(), patientLid);
            // [BUG]0006775 MODIFY END
            if (flag)
            {
                loggerBS301.error("Message:{},checkDependency:{}",
                        poobin000001uv02.toString(),
                        "就诊记录不存在:患者本地ID:" + patientLid + "患者域ID"
                            + patientDomain + "就诊次数" + visitTimes + "医疗机构代码"
                            + poobin000001uv02.getOrgCode());
            }
            return false;
        }
        // [BUG]0040270 MODIFY END
        return true;
        // [BUG]0009863 MODIFY END
    }

    // $Author :wu_jianfeng
    // $Date : 2012/9/25 15:00$
    // [BUG]0009863 DELETE BEGIN
    // /**
    // * 诊断相关信息判断（根据域ID，患者本地ID，诊断号判断诊断信息是否存在）
    // * @param poobin000001uv02
    // * @return boolean
    // */
    // public boolean checkDiagnosis(POOBIN000001UV02 poobin000001uv02)
    // {
    // Map<String, Object> map = new HashMap<String, Object>();
    // BigDecimal visitSn = medicalVisitResult.getVisitSn();
    // String patientLid = poobin000001uv02.getPatientLid();
    // // 通过就诊号与删除标志查找就诊记录
    // map.put("visitSn", visitSn);
    // map.put("deleteFlag", Constants.NO_DELETE_FLAG);
    // List<Object> diagnosisList = commonService.selectByCondition(
    // Diagnosis.class, map);
    // if (diagnosisList != null)
    // {
    // logger.error("患者本地ID：{} 的诊断关联记录存在", patientLid);
    // return true;
    // }
    // logger.error("患者本地ID：{} 的诊断关联记录不存在", patientLid);
    // return false;
    // }
    // [BUG]0009863 DELETE END

    /**
     * 校验成功后把取到的相应数据进行诊断信息的更新或添加操作
     * @param poobin000001uv02 xml文件中获取的诊断信息相应业务数据
     */
    @Override
    @Transactional
    public void saveOrUpdate(POOBIN000001UV02 poobin000001uv02)
    {
    	if(isCancelMessage(poobin000001uv02)){
    		deleteDiagnosis();
    		return;
    	}
    	
    	// 如果没有消息内容，删除所有诊断
    	List<MainDiagnosis> mainDiagnosises = poobin000001uv02.getDiagnosis();
    	if(Constants.lstDomainOutPatient().contains(poobin000001uv02.getPatientDomain()) &&
    			Constants.lstVisitTypeOutPatient().contains(poobin000001uv02.getVisitType())
    			&& (mainDiagnosises==null || mainDiagnosises.size()==0)){
//    		diagnosisService.deleteDiagnosis(medicalVisitResult.getVisitSn().toString(),null);
    	}else{
	        // $Author: tong_meng
	        // $Date: 2013/8/30 15:00
	        // [BUG]0036757 ADD BEGIN
	        serviceId = poobin000001uv02.getMessage().getServiceId();
	        // [BUG]0036757 ADD END
	
	        // $Author :wu_jianfeng
	        // $Date : 2012/9/25 15:00$
	        // [BUG]0009863 MODIFY BEGIN
	
	        // $Author :jia_yanqing
	        // $Date : 2012/6/15 15:00$
	        // [BUG]0006966 MODIFY BEGIN
	
	        // $Author :jia_yanqing
	        // $Date : 2012/6/14 15:00$
	        // [BUG]0006668 ADD BEGIN
	
	        // MedicalVisit medicalVisit = new MedicalVisit();
	        // 获得就诊记录更新对象
	        getMedicalVisitForUpdate(poobin000001uv02);
	        // [BUG]0006668 ADD END
	
	        // $Author :jia_yanqing
	        // $Date : 2012/6/7 15:00$
	        // [BUG]0006798 MODIFY BEGIN
	        if (isNewMessage(poobin000001uv02))
	        {
	            List<Object> diagnosisResultList = getDiagnosisMessage(poobin000001uv02);
	            diagnosisService.saveList(diagnosisResultList, medicalVisitResult);
	        }
	        // [BUG]0006798 MODIFY END
	        // [BUG]0009863 MODIFY END
    	} 

    }

    // $Author :wu_jianfeng
    // $Date : 2012/9/25 15:00$
    // [BUG]0009863 DELETE BEGIN
    /**
     * 诊断更新
     * @param poobin000001uv02 诊断消息映射
     */
    // private void updateDiagnosis(POOBIN000001UV02 poobin000001uv02,
    // MedicalVisit medicalVisit)
    // {
    // Map<String, Object> map = new HashMap<String, Object>();
    // // 通过就诊号与删除标志查找就诊记录
    // map.put("visitSn", visitSn);
    // map.put("deleteFlag", Constants.NO_DELETE_FLAG);
    // List<Object> diagnosisList = commonService.selectByCondition(
    // Diagnosis.class, map);
    //
    // // 获得诊断结果集
    // List<Object> diagnosisResultList = getDiagnosisMessage(poobin000001uv02);
    // // $Author :jia_yanqing
    // // $Date : 2012/6/14 15:00$
    // // [BUG]0006668 MODIFY BEGIN
    // // 更新诊断结果集
    // diagnosisService.updateDiagnosis(diagnosisList, diagnosisResultList,
    // medicalVisit);
    // // [BUG]0006668 MODIFY END
    // }
    // [BUG]0009863 DELETE END

    // $Author :wu_jianfeng
    // $Date : 2012/9/25 15:00$
    // [BUG]0009863 DELETE BEGIN
    // /**
    // * 诊断添加
    // * @param poobin000001uv02 诊断消息映射
    // */
    // private void insertDiagnosis(POOBIN000001UV02 poobin000001uv02,
    // MedicalVisit medicalVisit)
    // {
    // // 获得诊断结果集
    // List<Object> diagnosisResultList = getDiagnosisMessage(poobin000001uv02);
    //
    // // $Author :jia_yanqing
    // // $Date : 2012/6/14 15:00$
    // // [BUG]0006668 MODIFY BEGIN
    // // 诊断表新增，就诊表更新
    // diagnosisService.saveList(diagnosisResultList, medicalVisit);
    // // [BUG]0006668 MODIFY END
    // }
    // [BUG]0009863 DELETE END

    // $Author :jia_yanqing
    // $Date : 2012/6/14 15:00$
    // [BUG]0006668 ADD BEGIN
    /**
     * 获得就诊更新对象-当域ID为门诊时，诊断中的诊断医生需更新到就诊表中的就诊医生。
     * return MedicalVisit就诊对象
     */
    public MedicalVisit getMedicalVisitForUpdate(
            POOBIN000001UV02 poobin000001uv02)
    {
        // 更新就诊的就诊医生为诊断的诊断医生
        medicalVisitResult.setVisitDoctorId(poobin000001uv02.getDiagnosis().get(
                0).getDiagnosisDoctor());
        
        medicalVisitResult.setVisitDoctorName(DictionaryUtils.getNameFromDictionary(
        		Constants.DOMAIN_STAFF, poobin000001uv02.getDiagnosis().get(0).getDiagnosisDoctor(), poobin000001uv02.getDiagnosis().get(0).getDiagnosisDoctorName())); // 设置更新时间
        
        // $Author: tong_meng
        // $Date: 2013/8/30 15:00
        // [BUG]0036757 ADD BEGIN
        medicalVisitResult.setUpdateby(serviceId); // 设置创建人
        // [BUG]0036757 ADD END
        
        // Author :tong_meng
        // Date : 2013/8/22 13:00
        // [BUG]0036346 MODIFY BEGIN
        // Author :jia_yanqing
        // Date : 2012/6/14 15:00
        // [BUG]0033791 ADD BEGIN
        if (Constants.lstDomainOutPatient().contains(poobin000001uv02.getPatientDomain()) &&
    			Constants.lstVisitTypeOutPatient().contains(poobin000001uv02.getVisitType()))
        {
            // 就诊状态 - 4
            medicalVisitResult.setVisitStatusCode(Constants.VISIT_STATUS_SEEOUT);
            // 就诊状态名称 - 看完病
            medicalVisitResult.setVisitStatusName(DictionaryCache.getDictionary(
                    Constants.DOMAIN_VISIT_STATUS).get(
                            Constants.VISIT_STATUS_SEEOUT));
        }
        // [BUG]0033791 ADD END
        // [BUG]0036346 MODIFY END
        medicalVisitResult.setUpdateTime(sysdate);
        return medicalVisitResult;
    }

    // [BUG]0006668 ADD END

    public List<Object> getDiagnosisMessage(POOBIN000001UV02 poobin000001uv02)
    {
        List<Object> diagnosisList = new ArrayList<Object>();
        List<MainDiagnosis> mainDiagnosisList = poobin000001uv02.getDiagnosis();
        // 循环取出消息中的主诊断
        for (MainDiagnosis mainDiagnosis : mainDiagnosisList)
        {
            // 设置每个诊断共同的属性
            Diagnosis diagnosis = getNewDiagnosis(poobin000001uv02);
            // 诊断类别代码
            diagnosis.setDiagnosisTypeCode(mainDiagnosis.getDiagnosisType());
            
            /*
             * $Author: yu_yzh
             * $Date: 2015/8/17 8:57
             * 根据字典获取获取诊断类别名称
             * [BUG] 0059166 MODIFY BEGIN
             * */
            // 诊断类别名称
//            diagnosis.setDiagnosisTypeName(mainDiagnosis.getDiagnosisTypeName());
            String dtName = DictionaryUtils.getNameFromDictionary(Constants.DOMAIN_DIAGNOSIS_TYPE, 
            		mainDiagnosis.getDiagnosisType(), mainDiagnosis.getDiagnosisTypeName());
            diagnosis.setDiagnosisTypeName(dtName);
            // [BUG] 0059166 MODIFY END
            

            // $Author :jia_yanqing
            // $Date : 2012/7/27 14:33$
            // [BUG]0006778 MODIFY BEGIN
            // 是否待查
//            if (Constants.CERTAIN.equals(mainDiagnosis.getUncertainFlag()))
//            {
//                diagnosis.setUncertainFlag(Constants.CERTAIN_DB);
//            }
//            else if (Constants.UN_CERTAIN.equals(mainDiagnosis.getUncertainFlag()))
//            {
//                diagnosis.setUncertainFlag(Constants.UN_CERTAIN_DB);
//            }
            // [BUG]0006778 MODIFY BEGIN
            if(Constants.CERTAIN_DB.equals(mainDiagnosis.getUncertainFlag())){
            	diagnosis.setUncertainFlag(Constants.CERTAIN_DB);
            } else {
            	diagnosis.setUncertainFlag(Constants.UN_CERTAIN_DB);
            }
            	
            // 疾病代码
            diagnosis.setDiseaseCode(mainDiagnosis.getDiseaseCode());
            // 疾病名称
//            diagnosis.setDiseaseName(mainDiagnosis.getDiseaseName());
            /*
             * $Author: yu_yzh
             * $Date: 2015//9/10 18:14
             * 优先取字典名称 ADD BEGIN
             * */
            String diseaseName = DictionaryUtils.getNameFromDictionary(Constants.DOMAIN_ICD_OUTPATIENT, 
            		mainDiagnosis.getDiseaseCode(), mainDiagnosis.getDiseaseName());
            diagnosis.setDiseaseName(diseaseName);
            
            // 优先取字典名称 ADD END
            // 诊断时间
            diagnosis.setDiagnosisDate(DateUtils.stringToDate(mainDiagnosis.getDiagnosisDate()));
            // 诊断医生代码
            diagnosis.setDiagnosisDoctorId(mainDiagnosis.getDiagnosisDoctor());
            
            /*
             * $Author: yu_yzh
             * $Date: 2015/8/17 9:14
             * 根据字典获取名称
             * [BUG] 0059166 MODIFY BEGIN
             * */
            // 诊断医生姓名
//            diagnosis.setDiagnosisDoctorName(mainDiagnosis.getDiagnosisDoctorName());
            String docName = DictionaryUtils.getNameFromDictionary(Constants.DOMAIN_STAFF, 
            		mainDiagnosis.getDiagnosisDoctor(), mainDiagnosis.getDiagnosisDoctorName());
            diagnosis.setDiagnosisDoctorName(docName);
            // [BUG] 0059166 MODIFY END
            
            // $Author :tong_meng
            // $Date : 2012/6/25 14:33$
            // [BUG]0007418 MODIFY BEGIN
            // 诊断科室编码
            diagnosis.setDiagnosticDeptId(mainDiagnosis.getDiagnosticDept());
            // [BUG]0007418 MODIFY END

            /*
             * $Author: yu_yzh
             * $Date: 2015/8/17 9:23
             * 根据字典获取名称
             * [BUG] 0059166 MODIFY BEGIN
             * */
            // $Author :tong_meng
            // $Date : 2012/6/25 14:33$
            // [BUG]0007418 MODIFY BEGIN
            // 诊断科室名称
//            diagnosis.setDiagnosticDeptName(mainDiagnosis.getDiagnosticDeptName());
            // [BUG]0007418 MODIFY END
            String deptName = DictionaryUtils.getNameFromDictionary(Constants.DOMAIN_DEPARTMENT, 
            		mainDiagnosis.getDiagnosticDept(), mainDiagnosis.getDiagnosticDeptName());
            diagnosis.setDiagnosticDeptName(deptName);
            
            // [BUG] 0059166 MODIFY END
            
            // $Author:tong_meng
            // $Date:2013/12/03 11:00
            // [BUG]0040270 ADD BEGIN
            // 医疗机构代码
            diagnosis.setOrgCode(poobin000001uv02.getOrgCode());
            
            // 医疗机构名称
            diagnosis.setOrgName(poobin000001uv02.getOrgName());
            // [BUG]0040270 ADD END
            
            // Author :jin_peng
            // Date : 2013/08/05 17:12$
            // [BUG]0035785 MODIFY BEGIN
            // Author :jia_yanqing
            // Date : 2013/6/24 15:33$
            // [BUG]0034098 ADD BEGIN
            // 是否传染病
            if (Constants.TRUE_FLAG.equals(mainDiagnosis.getInfectFlag()))
            {
                diagnosis.setContagiousFlag(Constants.T_FLAG_VALUE);
            }
            else
            {
                diagnosis.setContagiousFlag(Constants.F_FLAG_VALUE);
            }

            // [BUG]0034098 ADD END
            // [BUG]0035785 MODIFY END

            // $Author :chang_xuewen
            // $Date : 2013/8/1 17:00
            // $[BUG]0035553 MODIFY BEGIN
            if (Constants.TRUE_FLAG.equals(mainDiagnosis.getIsMainDiagnosis()))
            {
                // 主诊断标识
                diagnosis.setMainDiagnosisFlag(Constants.MAIN_DIAGNOSIS);
            }
            else
                // 次诊断标识
                diagnosis.setMainDiagnosisFlag(Constants.MINOR_DIAGNOSIS);

            diagnosis.setSerialnumber(mainDiagnosis.getSerialnumber());
            
            diagnosisList.add(diagnosis);
            /*
             * List<MinorDiagnosis> minorDiagnosisList =
             * mainDiagnosis.getMinorDiagnosis(); // 循环取出消息中的次诊断 for
             * (MinorDiagnosis minorDiagnosis : minorDiagnosisList) { diagnosis
             * = getNewDiagnosis(poobin000001uv02); // 诊断类别代码
             * diagnosis.setDiagnosisTypeCode
             * (minorDiagnosis.getDiagnosisType()); // 诊断类别名称
             * diagnosis.setDiagnosisTypeName
             * (minorDiagnosis.getDiagnosisTypeName()); // 疾病代码
             * diagnosis.setDiseaseCode(minorDiagnosis.getDiseaseCode()); //
             * 疾病名称 diagnosis.setDiseaseName(minorDiagnosis.getDiseaseName());
             * 
             * // 是否待查 if
             * (Constants.CERTAIN.equals(minorDiagnosis.getUncertainFlag())) {
             * diagnosis.setUncertainFlag(Constants.CERTAIN_DB); } else if
             * (Constants.UN_CERTAIN.equals(minorDiagnosis.getUncertainFlag()))
             * { diagnosis.setUncertainFlag(Constants.UN_CERTAIN_DB); }
             * 
             * // 诊断时间
             * diagnosis.setDiagnosisDate(DateUtils.stringToDate(minorDiagnosis
             * .getDiagnosisDate())); // 诊断医生代码
             * diagnosis.setDiagnosisDoctorId(minorDiagnosis
             * .getDiagnosisDoctor()); // 诊断医生姓名
             * diagnosis.setDiagnosisDoctorName
             * (minorDiagnosis.getDiagnosisDoctorName());
             * 
             * // $Author :tong_meng // $Date : 2012/6/25 14:33$ // [BUG]0007418
             * MODIFY BEGIN // 诊断科室编码
             * diagnosis.setDiagnosticDeptId(minorDiagnosis
             * .getDiagnosticDept()); // [BUG]0007418 MODIFY END
             * 
             * // $Author :tong_meng // $Date : 2012/6/25 14:33$ // [BUG]0007418
             * MODIFY BEGIN // 诊断科室名称
             * diagnosis.setDiagnosticDeptName(minorDiagnosis
             * .getDiagnosticDeptName()); // [BUG]0007418 MODIFY END
             * 
             * // 次诊断标识
             * diagnosis.setMainDiagnosisFlag(Constants.MINOR_DIAGNOSIS);
             * 
             * 
             * // $Author :jia_yanqing // $Date : 2012/6/6 16:00$ //
             * [BUG]0006778 MODIFY BEGIN // 是否待查
             * if(N_CERTAIN_FLAG.equals(secDiagnosisList
             * .get(i).getUncertainFlag())) // [BUG]0006778 MODIFY END //是否待查 {
             * diagnosis.setUncertainFlag(N_MAIN); } else {
             * diagnosis.setUncertainFlag(U_MAIN); } // $Author :tongmeng //
             * $Date : 2012/5/28 14:00$ // [BUG]0006668 ADD BEGIN //
             * [BUG]0006668 ADD END
             * 
             * // 是否主诊断 diagnosis.setMainDiagnosisFlag(N_MAIN);
             * 
             * 
             * // Author :jia_yanqing // Date : 2013/6/24 15:33$ // [BUG]0034098
             * ADD BEGIN // 是否传染病
             * diagnosis.setContagiousFlag(minorDiagnosis.getInfectFlag()); //
             * [BUG]0034098 ADD END
             * 
             * // 插入一条记录判断 diagnosisList.add(diagnosis); } // $[BUG]0035553
             * MODIFY END
             */
            /*
             * // $Author :jia_yanqing // $Date : 2012/6/6 16:00$ //[BUG]0006778
             * MODIFY BEGIN // 是否待查
             * if(N_CERTAIN_FLAG.equals(poobin000001uv02.getUncertainFlag()))
             * //[BUG]0006778 MODIFY END { diagnosis.setUncertainFlag(N_MAIN); }
             * else { diagnosis.setUncertainFlag(U_MAIN); } // 是否主诊断
             * diagnosis.setMainDiagnosisFlag(N_MAIN);
             */

        }
        return diagnosisList;
    }

    /**
     * 子诊断与父诊断之间公共的属性
     * @param poobin000001uv02 诊断消息映射
     * return Diagnosis
     */
    // $Author :wu_jianfeng
    // $Date : 2012/9/25 15:00$
    // [BUG]0009863 MODIFY BEGIN
    public Diagnosis getNewDiagnosis(POOBIN000001UV02 poobin000001uv02)
    {
        Diagnosis diagnosis = new Diagnosis();

        // 创建时间
        diagnosis.setCreateTime(sysdate);
        // 更新时间
        diagnosis.setUpdateTime(sysdate);
        // 更新回数
        diagnosis.setUpdateCount(Constants.INSERT_UPDATE_COUNT);
        // 删除标志
        diagnosis.setDeleteFlag(Constants.NO_DELETE_FLAG);
        // 就诊内部序列号
        diagnosis.setVisitSn(medicalVisitResult.getVisitSn());
        // 患者内部序列号
        diagnosis.setPatientSn(medicalVisitResult.getPatientSn());
        // 域代码
        diagnosis.setPatientDomain(poobin000001uv02.getPatientDomain());
        // 患者本地ID
        diagnosis.setPatientLid(poobin000001uv02.getPatientLid());
        
        // $Author: tong_meng
        // $Date: 2013/8/30 15:00
        // [BUG]0036757 ADD BEGIN
        diagnosis.setCreateby(DataMigrationUtils.getDataMigration() + serviceId); // 设置创建人
        // [BUG]0036757 ADD END


        /*
         * // 是否传染
         * diagnosis.setContagiousFlag(poobin000001uv02.getContagiousFlag()); //
         * 诊断日期
         * diagnosis.setDiagnosisDate(DateUtils.stringToDate(poobin000001uv02
         * .getDiagnosisDate()));
         */

        // $Author :tongmeng
        // $Date : 2012/5/28 14:00$
        // [BUG]0006668 DELETE BEGIN
        // // 诊断依据代码
        // diagnosis.setDiagnoiseBasis(poobin000001uv02.getDiagnoiseBasis());
        // // 诊断依据
        // diagnosis.setDiagnoiseBasisName(poobin000001uv02.getDiagnoiseBasis_name());
        // [BUG]0006668 DELETE END

        /*
         * // 诊断医生ID
         * diagnosis.setDiagnosisDoctor(poobin000001uv02.getDiagnosisDoctor());
         * // 诊断医生姓名
         * diagnosis.setDiagnosisDoctorName(poobin000001uv02.getDiagnosisDoctorName
         * ()); // 诊断科室
         * diagnosis.setDiagnosticDept(poobin000001uv02.getDiagnosticDept());
         */

        return diagnosis;
    }
    // [BUG]0006966 MODIFY END
    // [BUG]0009863 MODIFY END
    
    private List<String> checkDiagnosisExist(POOBIN000001UV02 poobin000001uv02){
    	List<MainDiagnosis> mainDiagnosises = poobin000001uv02.getDiagnosis();
    	List<String> noExistList = new ArrayList<String>();
    	for(MainDiagnosis diagnosis : mainDiagnosises){
    		String diseaseCode = diagnosis.getDiseaseCode();
//    		String diseaseName = diagnosis.getDiseaseName();
    		String serialnumber = diagnosis.getSerialnumber();
    		String visitSn = medicalVisitResult.getVisitSn().toString();
    		String deleteFlag = Constants.NO_DELETE_FLAG;
    		Map<String, Object> condition = new HashMap<String, Object>();
    		condition.put("visitSn", visitSn);
    		condition.put("serialnumber", serialnumber);
    		condition.put("deleteFlag", deleteFlag);
    		condition.put("diseaseCode", diseaseCode);
//    		condition.put("diseaseName", diseaseName);
    		
    		List<Object> result = commonService.selectByCondition(Diagnosis.class, condition);
    		if(result == null || result.isEmpty()){
    			noExistList.add(serialnumber);
    		} else {
    			if(deleteList == null){
    				deleteList = new ArrayList<Diagnosis>();
    			}
    			for(Object obj : result){
    				Diagnosis diag = (Diagnosis) obj;
    				deleteList.add(diag);
    			}
    		}
    	}
    	return noExistList;
    }
    
    private void deleteDiagnosis(){
    	for(Diagnosis diag : deleteList){
    		diag.setDeleteFlag(Constants.DELETE_FLAG);
    		diag.setDeleteby(serviceId);
    		diag.setDeleteTime(sysdate);
    		diag.setUpdateby(serviceId);
    		diag.setUpdateTime(sysdate);
    	}
    	commonService.updatePartiallyAll(deleteList, "deleteFlag", "deleteby", "deleteTime", "updateby", "updateTime");
    }
    
}
