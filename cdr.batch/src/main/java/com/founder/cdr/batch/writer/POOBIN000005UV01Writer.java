package com.founder.cdr.batch.writer;

import java.math.BigDecimal;
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

import com.founder.cdr.cache.DictionaryCache;
import com.founder.cdr.core.Constants;
import com.founder.cdr.entity.MedicalVisit;
import com.founder.cdr.entity.NursingOperation;
import com.founder.cdr.entity.PhysicalExamination;
import com.founder.cdr.hl7.dto.NursingOperationDto;
import com.founder.cdr.hl7.dto.PhysicalExaminationDto;
import com.founder.cdr.hl7.dto.poobin000005uv01.POOBIN000005UV01;
import com.founder.cdr.service.CareService;
import com.founder.cdr.service.CommonService;
import com.founder.cdr.util.DateUtils;
import com.founder.cdr.util.StringUtils;

/**
 * [FUN] 生命体征
 * @version 1.0, 2012/05/07
 * @author guo_hongyan
 * @since 1.0
 *
 */
@Component(value = "poobin000005uv01Writer")
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class POOBIN000005UV01Writer implements BusinessWriter<POOBIN000005UV01>
{

    private static final Logger logger = LoggerFactory.getLogger(POOBIN000005UV01.class);

    // $Author :jin_peng
    // $Date : 2013/10/28 13:40$
    // [BUG]0038604 ADD BEGIN
    private static final Logger loggerBS315 = LoggerFactory.getLogger("BS315");

    // [BUG]0038604 ADD END

    @Autowired
    private CommonService commonService;

    @Autowired
    private CareService careService;

    // 护理操作.护理内部序列号sequences
    private static final String SEQ_NURSING_OPERATION = "SEQ_NURSING_OPERATION";

    // 患者本地ID
    private String patientLid;

    // 就诊内部序列号
    private BigDecimal visitSn;

    // 患者内部序列号
    private BigDecimal patientSn;

    // 存放消息中护理操作List
    List<Object> nurOperationList;

    // 体格检查List
    List<Object> physicalExamList;

    // Author :jia_yanqing
    // Date : 2013/1/17 13:33
    // [BUG]0013379 ADD BEGIN
    // 待更新的护理操作List
    List<Object> nurOperationDelList;

    // 待更新的体格检查List
    List<Object> physicalExamDelList;

    // [BUG]0013379 ADD END
    
    // Author :chang_xuewen
    // Date : 2013/11/27 15:33
    // [BUG]0039754 ADD BEGIN
    // 待更新的护理操作List
    List<Object> nurOperationUpdList;

    // 待更新的体格检查List
    List<Object> physicalExamUpdList;

    // [BUG]0039754 ADD END

    // 系统时间
    private Date sysdate;

    // 护理内部序列号
    private BigDecimal operationSn;

    // $Author: tong_meng
    // $Date: 2013/8/30 15:00
    // [BUG]0036757 ADD BEGIN
    private String serviceId;
    // [BUG]0036757 ADD END
    
    private String orgCode;
    
    private String orgName;
	
    public boolean validate(POOBIN000005UV01 poobin000005uv01)
    {
        // Author :jia_yanqing
        // Date : 2013/1/22 15:00
        // [BUG]0042085 MODIFY BEGIN
        if(poobin000005uv01.getOrganizationCode() == null || "".equals(poobin000005uv01.getOrganizationCode()))
        {
            poobin000005uv01.setOrganizationCode(Constants.HOSPITAL_CODE);
            poobin000005uv01.setOrganizationName(Constants.HOSPITAL_NAME);
        }
        /*
        if(!poobin000005uv01.getOrganizationCode().equals(poobin000005uv01.getMessage().getOrgCode()))
        {
        	loggerBS315.error(
					"Message:{},validate:{}",
					poobin000005uv01.toString(),
					"消息头中的医疗机构编码和消息体中的医疗机构编码不同，消息头中的医疗机构编码="
							+ poobin000005uv01.getMessage().getOrgCode()
							+ "，消息体中的医疗机构编码="
							+ poobin000005uv01.getOrganizationCode());
			return false;
        }
        */
        // [BUG]0042085 MODIFY END
        
    	// Author: jia_yanqing
        // Date: 2013/11/4 10:30
        // [BUG]0038885 ADD BEGIN
        List<NursingOperationDto> nursingOperationDtoList = poobin000005uv01.getNursingOperation();
        for(NursingOperationDto nod: nursingOperationDtoList)
        {
            List<PhysicalExaminationDto> physicalExaminationDtoList = nod.getPhysicalExamDto();
            //港大发的消息中可能会有为空测量值的项，验证改为不全为空则通过，并把dto中的空测量值项移出list
            int nullFlag = 0;
            List<PhysicalExaminationDto> nullList = new ArrayList<PhysicalExaminationDto>();
            for(PhysicalExaminationDto ped: physicalExaminationDtoList)
            {
                //如果检查项目为血压时，判断舒张压与收缩压是否为空
                if(Constants.PHYSICAL_BLOOD_PRESSURE.equals(ped.getItemTypeCode()))
                {
                    //舒张压为空时
                    if (StringUtils.isEmpty(ped.getDiastolicPressure()))
                    {
//                        loggerBS315.error("编码:{}, validate:{}",
//                                ped.getItemTypeCode(), "舒张压不能为空");
//                        return false;
                    	loggerBS315.debug("编码:{}, validate:{}", ped.getItemTypeCode(), "舒张压为空");
                    	nullFlag++;
                    	nullList.add(ped);
                    }
                    //收缩压为空时
                    else if(StringUtils.isEmpty(ped.getSystolicPressure()))
                    {
//                        loggerBS315.error("编码:{},validate:{}",
//                                ped.getItemTypeCode(), "收缩压不能为空");
//                        return false;
                    	loggerBS315.debug("编码:{}, validate:{}", ped.getItemTypeCode(), "收缩压为空");
                    	nullFlag++;
                    	nullList.add(ped);
                    }
                }
                //除血压之外的其它检查项目时
                else
                {
                    //测量值为空时
                    if(StringUtils.isEmpty(ped.getItemResult()))
                    {
//                        loggerBS315.error("编码:{},validate:{}",
//                                ped.getItemTypeCode(), "测量值不能为空");
//                        return false;
                    	loggerBS315.debug("编码:{}, validate:{}", ped.getItemTypeCode(), ped.getItemTypeName() + "为空");
                    	nullFlag++;
                    	nullList.add(ped);
                    }
                }
            }
            if(nullFlag == physicalExaminationDtoList.size()){
            	loggerBS315.error("validate:{}, {}", nod, "测量值全部为空");            	
            } else {
            	physicalExaminationDtoList.removeAll(nullList);
            }
        }
        // [BUG]0038885 ADD END
        return true;
    }

    // Author :jia_yanqing
    // Date : 2013/1/17 13:33
    // [BUG]0013379 MODIFY BEGIN
    public boolean checkDependency(POOBIN000005UV01 poobin000005uv01,
            boolean flag)
    {
        boolean resultFlag = true;
        // $Author :jin_peng
        // $Date : 2013/10/28 13:40$
        // [BUG]0038604 MODIFY BEGIN
        if (poobin000005uv01 != null)
        {
            patientLid = poobin000005uv01.getPatientLid();
            logger.info("患者本地ID {}", patientLid);
            // 就诊表是否存在
            resultFlag = checkMedicalVisit(poobin000005uv01, flag);
        }
        if (resultFlag)
        {
            if (isUpdateMessage(poobin000005uv01.getNewUpFlag()) || isDeleteMessage(poobin000005uv01.getNewUpFlag()))
            {
                List<Object> nurOperationDelList = getNurOperationDelList(poobin000005uv01.getOperationId(),poobin000005uv01,Constants.DELETE_MESSAGE_FLAG);
                if (nurOperationDelList != null
                    && !nurOperationDelList.isEmpty())
                {
                    logger.info("数据库中存在要更新或删除的护理操作记录！");
                }
                else
                {
                    logger.error("数据库中不存在要更新或删除的护理操作记录！");

                    if (flag)
                    {
                        loggerBS315.error(
                                "Message:{},checkDependency:{}",
                                poobin000005uv01.toString(),
                                "数据库中不存在要更新或删除的护理操作记录！消息交互类型："
                                    + poobin000005uv01.getNewUpFlag());
                    }

                    return false;
                }
            }
        }

        // [BUG]0038604 MODIFY END

        // Author :jia_yanqing
        // Date : 2013/6/9 11:13
        // [BUG]0033654 MODIFY BEGIN
        return resultFlag;
        // [BUG]0033654 MODIFY END
    }

    // [BUG]0013379 MODIFY END

    @Transactional
    public void saveOrUpdate(POOBIN000005UV01 poobin000005uv01)
    {
        // $Author: tong_meng
        // $Date: 2013/8/30 15:00
        // [BUG]0036757 ADD BEGIN
        serviceId = poobin000005uv01.getMessage().getServiceId();
        // [BUG]0036757 ADD END
        orgCode = poobin000005uv01.getOrganizationCode();
        orgName = poobin000005uv01.getOrganizationName();
        
        nurOperationList = new ArrayList<Object>();
        physicalExamList = new ArrayList<Object>();
        // Author :chang_xuewen
        // Date : 2013/11/27 15:33
        // [BUG]0039754 ADD BEGIN
        nurOperationUpdList = new ArrayList<Object>();
        physicalExamUpdList = new ArrayList<Object>();
        // [BUG]0039754 ADD END
        // Author :jia_yanqing
        // Date : 2013/1/17 13:33
        // [BUG]0013379 MODIFY BEGIN
        // 护理操作List、体格检查List取得以及要更新的护理操作List、体格检查List
        getNurOperation(poobin000005uv01);
        // DB更新/插入
        if (isNewMessage(poobin000005uv01.getNewUpFlag()))
        {
            careService.saveNurPhyExam(nurOperationList, physicalExamList);
        }
        // $Author: chang_xuewen
        // $Date: 2013/11/27 15:00
        // [BUG]0039754 MODIFY BEGIN
        else if (isUpdateMessage(poobin000005uv01.getNewUpFlag()))
        {
            careService.updateNurPhyExam(nurOperationUpdList,
            		physicalExamUpdList, nurOperationList, physicalExamList,
                    serviceId);
        }else if (isDeleteMessage(poobin000005uv01.getNewUpFlag()))
        {
        	careService.deleteNurPhyExam(nurOperationDelList, physicalExamDelList,serviceId);
        }
        // [BUG]0039754 MODIFY BEGIN
        // [BUG]0013379 MODIFY END
    }

    /**
     * 就诊数据check
     * @param poobin000005uv01
     * @return true:就诊存在；false：就诊不存在
     */
    private boolean checkMedicalVisit(POOBIN000005UV01 poobin000005uv01,
            boolean flag)
    {
//        // 通过域代码、患者本地ID、就诊次数，从DB中取得就诊数据
//        MedicalVisit visitResult = commonService.mediVisit(
//                poobin000005uv01.getPatientDomain(), patientLid,
//                Integer.parseInt(poobin000005uv01.getVisitTime()),poobin000005uv01.getOrganizationCode());
    	// 通过域id，患者本地id，就诊流水号，就诊类型获取就诊
    	MedicalVisit visitResult = null;
    	String visitTimes = poobin000005uv01.getVisitTime();
        visitResult = commonService.mediVisit(poobin000005uv01.getPatientDomain(), patientLid,
        		visitTimes == null ? 0 : Integer.parseInt(visitTimes), poobin000005uv01.getOrganizationCode(), 
        				poobin000005uv01.getVisitOrdNo(), poobin000005uv01.getVisitType());
        
        if (visitResult != null)
        {
            // 就诊内部序列号
            visitSn = visitResult.getVisitSn();
            // 患者内部序列号
            patientSn = visitResult.getPatientSn();
            logger.info("就诊存在,就诊内部序列号 {},患者内部序列号{}", visitSn, patientSn);
        }
        else
        {
            logger.error("就诊不存在！");

            // $Author :jin_peng
            // $Date : 2013/10/28 13:40$
            // [BUG]0038604 ADD BEGIN
            if (flag)
            {
                loggerBS315.error("Message:{},checkDependency:{}",
                        poobin000005uv01.toString(), "就诊不存在！消息交互类型："
                            + poobin000005uv01.getNewUpFlag());
            }

            // [BUG]0038604 ADD END

            return false;
        }

        return true;
    }

    /**
     * 护理操作entity返回
     * @param poobin000005uv01
     * @return
     */
    private List<Object> getNurOperation(POOBIN000005UV01 poobin000005uv01)
    {
        // Author :jia_yanqing
        // Date : 2013/1/17 13:33
        // [BUG]0013379 ADD BEGIN
        // 操作码
        String operationId = poobin000005uv01.getOperationId();

        if (isDeleteMessage(poobin000005uv01.getNewUpFlag()))
        {
            nurOperationDelList = getNurOperationDelList(operationId,poobin000005uv01,Constants.DELETE_MESSAGE_FLAG);
            physicalExamDelList = getPhysicalExamDelList(nurOperationDelList,poobin000005uv01,Constants.DELETE_MESSAGE_FLAG);
        }else if(isUpdateMessage(poobin000005uv01.getNewUpFlag()))
        {
        	getNurOperationDelList(operationId,poobin000005uv01,Constants.UPDATE_MESSAGE_FLAG);
        	getPhysicalExamDelList(nurOperationUpdList,poobin000005uv01,Constants.UPDATE_MESSAGE_FLAG);
        }
        // [BUG]0013379 ADD END
        
        
        if(isNewMessage(poobin000005uv01.getNewUpFlag())){
            NursingOperation nursingOperation = null;
            if (poobin000005uv01.getNursingOperation() != null
                    && !poobin000005uv01.getNursingOperation().isEmpty())
            {
                for (NursingOperationDto nurOperationDto : poobin000005uv01.getNursingOperation())
                {
                    nursingOperation = new NursingOperation();
                    sysdate = DateUtils.getSystemTime();
                    // 护理内部序列号
                    operationSn = commonService.getSequence(SEQ_NURSING_OPERATION);
                    nursingOperation.setOperationSn(operationSn);
                    // 就诊内部序列号
                    nursingOperation.setVisitSn(visitSn);
                    // 患者内部序列号
                    nursingOperation.setPatientSn(patientSn);
                    // 域代码
                    nursingOperation.setPatientDomain(poobin000005uv01.getPatientDomain());
                    // 患者本地ID
                    nursingOperation.setPatientLid(patientLid);
                    // Author :jia_yanqing
                    // Date : 2013/1/17 13:33
                    // [BUG]0013379 ADD BEGIN
                    // 操作码
                    nursingOperation.setOperationId(operationId);
                    // [BUG]0013379 ADD END
                    
                    // $Author :jia_yanqing
                    // $Date : 2012/7/19 13:33$
                    // [BUG]0007783 MODIFY BEGIN
                    // 护理操作项目类目
                    // nursingOperation.setOperationType(nurOperationDto.getOperationType());
                    // 护理操作项目类目名称
                    // nursingOperation.setOperationTypeName(nurOperationDto.getOperationTypeName());
                    // 护理人ID
                    nursingOperation.setOperatorId(nurOperationDto.getOperatorId());
                    // 护理人姓名
                    nursingOperation.setOperatorName(nurOperationDto.getOperatorName());
                    // 护理类型代码
                    // nursingOperation.setCareType(nurOperationDto.getCareType());
                    
                    // $Author :tong_meng
                    // $Date : 2012/7/1 10:33$
                    // [BUG]0007418 ADD BEGIN
                    // 护理类型代码名稱
                    // nursingOperation.setCareTypeName(nurOperationDto.getCareTypeName());
                    // [BUG]0007418 ADD END
                    
                    // 操作开始时间
                    // nursingOperation.setBeginTime(DateUtils.stringToDate(nurOperationDto.getBeginTime()));
                    // 操作结束时间
                    // nursingOperation.setEndTime(DateUtils.stringToDate(nurOperationDto.getEndTime()));
                    // 效果评价
                    // nursingOperation.setOperationEvaluation(nurOperationDto.getOperationEvaluation());
                    // 审核人
                    // nursingOperation.setReviewerId(poobin000005uv01.getReviewerId());
                    // 审核人姓名
                    // nursingOperation.setReviewerName(poobin000005uv01.getReviewerName());
                    // 审核时间
                    // nursingOperation.setReviewerTime(DateUtils.stringToDate(poobin000005uv01.getReviewerTime()));
                    
                    // 护理操作时间
                    nursingOperation.setOperationTime(DateUtils.stringToDate(nurOperationDto.getNursingOperationTime()));
                    // [BUG]0007783 MODIFY BEGIN
                    // 更新回数
                    nursingOperation.setUpdateCount(Constants.INSERT_UPDATE_COUNT);
                    // 创建时间
                    nursingOperation.setCreateTime(sysdate);
                    // 删除标志
                    nursingOperation.setDeleteFlag(Constants.NO_DELETE_FLAG);
                    // 更新时间
                    nursingOperation.setUpdateTime(sysdate);
                    
                    // $Author: tong_meng
                    // $Date: 2013/8/30 15:00
                    // [BUG]0036757 ADD BEGIN
                    nursingOperation.setCreateby(serviceId); // 设置创建人
                    // [BUG]0036757 ADD END
                    
                    // 医疗机构编码
                    nursingOperation.setOrgCode(orgCode);
                    // 医疗机构名称
                    nursingOperation.setOrgName(orgName);
                    
                    // 体格检查List
                    getPhysicalExamination(nurOperationDto);
                    
                    nurOperationList.add(nursingOperation);
                }
            }
        }
        return nurOperationList;
    }

    /**
     * 返回体格检查消息中信息与DB中存在数据
     * @param poobin000005uv01
     * @return
     */
    private List<Object> getPhysicalExamination(
            NursingOperationDto nurOperationDto)
    {
        // 体格检查
        // PhysicalExamination physicalExamination = null;
        // 从消息中取出的体格检查内容
        if (nurOperationDto.getPhysicalExamDto() != null
            && !nurOperationDto.getPhysicalExamDto().isEmpty())
        {
            // 遍历体格检查消息
            for (PhysicalExaminationDto phyExamDto : nurOperationDto.getPhysicalExamDto())
            {
            	
            	if (phyExamDto.getItemResult() != null
                    && !"".equals(phyExamDto.getItemResult()))
                {
                    PhysicalExamination physicalExamination = new PhysicalExamination();
                    // 护理内部序列号
                    physicalExamination.setOperationSn(operationSn);
                    // 项目类型代码
                    physicalExamination.setItemTypeCode(phyExamDto.getItemTypeCode());
                    
                    // $Author:wei_peng
                    // $Date:2012/8/31 18:38
                    // $[BUG]0009064 MODIFY BEGIN
                    // 项目类型名称
//                    physicalExamination.setItemTypeName(phyExamDto.getItemTypeName());
                    // $[BUG]0009064 MODIFY END
                    
                    /*
                     * $Author: yu_yzh
                     * $Date: 2015/8/20 8:51
                     * 若项目类型名称无，根据字典查名称
                     * MODIFY BEGIN
                     * */
                    if(phyExamDto.getItemTypeName() == null){
                    	Map<String, String> dic = DictionaryCache.getDictionary(Constants.DOMAIN_PHYSICAN_EXAM_ITEM);
                    	physicalExamination.setItemTypeName(dic == null ? null : dic.get(phyExamDto.getItemTypeCode()));
                    } else {
                    	physicalExamination.setItemTypeName(phyExamDto.getItemTypeName());
                    }
                    // MODIFY END
                    
                    // $Author :jia_yanqing
                    // $Date : 2012/7/19 13:33$
                    // [BUG]0007783 MODIFY BEGIN
                    // 项目代码
                    // physicalExamination.setItemCode(phyExamDto.getItemCode());
                    // 项目名称
                    // physicalExamination.setItemName(phyExamDto.getItemName());
                    // [BUG]0007783 MODIFY END

                    // 项目结果
                    physicalExamination.setItemResult(phyExamDto.getItemResult());
                    // 项目结果单位
                    physicalExamination.setItemResultUnit(phyExamDto.getItemResultUnit());
                    
                    /*
                	 * $Author: yu_yzh
                	 * $Date: 2015/8/20 11:40
                	 * 收缩压（高压），舒张压（低压）设置高低压标识，并将项目代码设置为血压
                	 * ADD BEGIN
                	 * */ 
                	if( Constants.PHYSICAL_BLOOD_SYSTOLIC.equals(phyExamDto.getItemTypeCode())){
                		physicalExamination.setBloodPressureFlag(Constants.HIGH_PRESSURE);
                		physicalExamination.setItemTypeCode(Constants.PHYSICAL_BLOOD_SYSTOLIC);
                	} else if( Constants.PHYSICAL_BLOOD_DIASTOLIC.equals(phyExamDto.getItemTypeCode())){
                		physicalExamination.setBloodPressureFlag(Constants.LOW_PRESSURE);
                		physicalExamination.setItemTypeCode(Constants.PHYSICAL_BLOOD_DIASTOLIC);
                	} 
                	/* 
                	 * ADD BEGIN
                	 */ 
                    
                    // 检查时间
                    physicalExamination.setExamTime(DateUtils.stringToDate(phyExamDto.getPhysicalTime()));

                    // $Author :jia_yanqing
                    // $Date : 2012/7/19 13:33$
                    // [BUG]0007783 MODIFY BEGIN
                    // 检查描述
                    // physicalExamination.setExamDescription(phyExamDto.getExamDescription());
                    // [BUG]0007783 MODIFY END

                    // 创建时间
                    physicalExamination.setCreateTime(sysdate);
                    // 更新时间
                    physicalExamination.setUpdateTime(sysdate);
                    // 更新回数
                    physicalExamination.setUpdateCount(Constants.INSERT_UPDATE_COUNT);
                    // 删除标志
                    physicalExamination.setDeleteFlag(Constants.NO_DELETE_FLAG);

                    // $Author: tong_meng
                    // $Date: 2013/8/30 15:00
                    // [BUG]0036757 ADD BEGIN
                    physicalExamination.setCreateby(serviceId); // 设置创建人
                    // [BUG]0036757 ADD END
                    
                    // 医疗机构编码
                    physicalExamination.setOrgCode(orgCode);
                    // 医疗机构名称
                    physicalExamination.setOrgName(orgName);

                    physicalExamList.add(physicalExamination);
                }
            	
            	/*
            	 * $Author: yu_yzh
            	 * $Date: 2015/8/20 11:44
            	 * 现在收缩压，舒张压也用itemTypeCode和itemResult保存
            	 * DELETE BEGIN
            	 * */
                // $Author :jia_yanqing
                // $Date : 2012/7/19 13:33$
                // [BUG]0007783 ADD BEGIN
                // 用来存放血压检测
                /*else
                {
                    // 用来存入低压
                    PhysicalExamination physicalExamination1 = new PhysicalExamination();

                    // 护理内部序列号
                    physicalExamination1.setOperationSn(operationSn);
                    // 项目类型代码
                    physicalExamination1.setItemTypeCode(phyExamDto.getItemTypeCode());
                    // $Author:wei_peng
                    // $Date:2012/8/31 18:38
                    // $[BUG]0009064 MODIFY BEGIN
                    // 项目类型名称
                    physicalExamination1.setItemTypeName(phyExamDto.getItemTypeName());
                    // $[BUG]0009064 MODIFY END
                    // 项目结果-高压
                    physicalExamination1.setItemResult(phyExamDto.getSystolicPressure());
                    // 项目结果单位
                    physicalExamination1.setItemResultUnit(phyExamDto.getSystolicPressureUnit());
                    // 检查时间
                    physicalExamination1.setExamTime(DateUtils.stringToDate(phyExamDto.getPhysicalTime()));
                    // 高压低压标志-高压
                    physicalExamination1.setBloodPressureFlag(Constants.HIGH_PRESSURE);
                    // 创建时间
                    physicalExamination1.setCreateTime(sysdate);
                    // 更新时间
                    physicalExamination1.setUpdateTime(sysdate);
                    // 更新回数
                    physicalExamination1.setUpdateCount(Constants.INSERT_UPDATE_COUNT);
                    // 删除标志
                    physicalExamination1.setDeleteFlag(Constants.NO_DELETE_FLAG);

                    // $Author: tong_meng
                    // $Date: 2013/8/30 15:00
                    // [BUG]0036757 ADD BEGIN
                    physicalExamination1.setCreateby(serviceId); // 设置创建人
                    // [BUG]0036757 ADD END
                    
                    // 医疗机构编码
                    physicalExamination1.setOrgCode(orgCode);
                    // 医疗机构名称
                    physicalExamination1.setOrgName(orgName);

                    physicalExamList.add(physicalExamination1);

                    // 用来存入高压
                    PhysicalExamination physicalExamination2 = new PhysicalExamination();
                    // 护理内部序列号
                    physicalExamination2.setOperationSn(operationSn);
                    // 项目类型代码
                    physicalExamination2.setItemTypeCode(phyExamDto.getItemTypeCode());
                    // $Author:wei_peng
                    // $Date:2012/8/31 18:38
                    // $[BUG]0009064 MODIFY BEGIN
                    // 项目类型名称
                    physicalExamination2.setItemTypeName(phyExamDto.getItemTypeName());
                    // $[BUG]0009064 MODIFY BEGIN
                    // 项目结果-低压
                    physicalExamination2.setItemResult(phyExamDto.getDiastolicPressure());
                    // 项目结果单位
                    physicalExamination2.setItemResultUnit(phyExamDto.getDiastolicPressureUnit());
                    // 检查时间
                    physicalExamination2.setExamTime(DateUtils.stringToDate(phyExamDto.getPhysicalTime()));
                    // 高压低压标志-低压
                    physicalExamination2.setBloodPressureFlag(Constants.LOW_PRESSURE);
                    // 创建时间
                    physicalExamination2.setCreateTime(sysdate);
                    // 更新时间
                    physicalExamination2.setUpdateTime(sysdate);
                    // 更新回数
                    physicalExamination2.setUpdateCount(Constants.INSERT_UPDATE_COUNT);
                    // 删除标志
                    physicalExamination2.setDeleteFlag(Constants.NO_DELETE_FLAG);

                    // $Author: tong_meng
                    // $Date: 2013/8/30 15:00
                    // [BUG]0036757 ADD BEGIN
                    physicalExamination2.setCreateby(serviceId); // 设置创建人
                    // [BUG]0036757 ADD END
                    
                    // 医疗机构编码
                    physicalExamination2.setOrgCode(orgCode);
                    // 医疗机构名称
                    physicalExamination2.setOrgName(orgName);

                    physicalExamList.add(physicalExamination2);
                }*/
                // [BUG]0007783 ADD END
            	// DELETE END
            }
        }
        return physicalExamList;
    }

    // Author :jia_yanqing
    // Date : 2013/1/17 13:33
    // [BUG]0013379 ADD BEGIN
    /**
     * 获取数据库中已经存在的跟本消息属于同一次操作的护理操作
     * @param operationId
     * @return
     */
    public List<Object> getNurOperationDelList(String operationId,POOBIN000005UV01 poobin000005uv01,String updateFlag)
    {
        List<Object> nurOperationDelList = new ArrayList<Object>();
        Map<String, Object> condition = new HashMap<String, Object>();
        condition.put("operationId", operationId);
        condition.put("visitSn", visitSn);
        condition.put("orgCode", poobin000005uv01.getOrganizationCode());
        condition.put("deleteFlag", Constants.NO_DELETE_FLAG);
        nurOperationDelList = commonService.selectByCondition(
                NursingOperation.class, condition);
        // Author :chang_xuewen
        // Date : 2013/11/27 15:33
        // [BUG]0039754 ADD BEGIN
        // 如果是更新标志，循环【数据库】中的操作记录，遍历更新
        if(isUpdateMessage(updateFlag) && !nurOperationDelList.isEmpty()){
        	for(Object obj : nurOperationDelList){
        		NursingOperation nurOper = (NursingOperation) obj;
        		for (NursingOperationDto nurOperationDto : poobin000005uv01.getNursingOperation()){
        			// 【数据库】中操作记录操作id相同的【消息】中的操作记录
                	if(nurOper.getOperationId().equals(poobin000005uv01.getOperationId())){
            			sysdate = DateUtils.getSystemTime();
                        // 就诊内部序列号
                        nurOper.setVisitSn(visitSn);
                        // 患者内部序列号
                        nurOper.setPatientSn(patientSn);
                        // 域代码
                        nurOper.setPatientDomain(poobin000005uv01.getPatientDomain());
                        // 患者本地ID
                        nurOper.setPatientLid(patientLid);
                        // 操作码
                        nurOper.setOperationId(operationId);
                        // 护理人ID
                        nurOper.setOperatorId(nurOperationDto.getOperatorId());
                        // 护理人姓名
                        nurOper.setOperatorName(nurOperationDto.getOperatorName());
                        // 护理操作时间
                        nurOper.setOperationTime(DateUtils.stringToDate(nurOperationDto.getNursingOperationTime()));
                        // 医疗机构编码
                        nurOper.setOrgCode(poobin000005uv01.getOrganizationCode());
                        // 医疗机构名称 
                        nurOper.setOrgName(poobin000005uv01.getOrganizationName());
                        // 更新时间
                        nurOper.setUpdateTime(sysdate);
                        // 设置更新人
                        nurOper.setUpdateby(serviceId); 
                        
                        nurOperationUpdList.add(nurOper);
                    }
            	}
        	}
        }     
        // [BUG]0039754 ADD END
        return nurOperationDelList;
    }

    /**
     * 获取数据库中已经存在的跟本消息属于同一次操作的体格检查列表
     * @param nurOperationDelList
     * @return
     */
    private List<Object> getPhysicalExamDelList(List<Object> nurOperationDelList,POOBIN000005UV01 poobin000005uv01,String updateFlag)
    {    	
        List<Object> physicalExamDelList = new ArrayList<Object>();
        for (int i = 0; i < nurOperationDelList.size(); i++)
        {
            NursingOperation nursingOperation = (NursingOperation) nurOperationDelList.get(i);
            List<Object> physicalExamDel = new ArrayList<Object>();
            Map<String, Object> condition = new HashMap<String, Object>();
            condition.put("operationSn", nursingOperation.getOperationSn());
            condition.put("deleteFlag", Constants.NO_DELETE_FLAG);
            physicalExamDel = commonService.selectByCondition(
                    PhysicalExamination.class, condition);
            /**if(physicalExamDel!=null&&physicalExamDel.size()!=0)
            {
                for(Object o: physicalExamDel)
                {
                    physicalExamDelList.add(o);
                }
            }*/ 
            // Author :chang_xuewen
            // Date : 2013/11/27 15:33
            // [BUG]0039754 MODIFY BEGIN
            List<Object> peList = new ArrayList<Object>();
            // 循环取得【消息】中操作记录
            for (NursingOperationDto nurOperationDto : poobin000005uv01.getNursingOperation())
            {
            	// 得到与当前【数据库】中操作记录操作id相同的【消息】中的操作记录
                // Author: jia_yanqing
                // Date: 2013/11/27 13:00
                // [BUG]0040071 MODIFY BEGIN
            	if(nursingOperation.getOperationId().equals(poobin000005uv01.getOperationId())){
            	// [BUG]0040071 MODIFY END
            		// 循环取得【消息】中该操作记录对应的所有体格检查
	            	for(PhysicalExaminationDto pedto : nurOperationDto.getPhysicalExamDto()){
	            		// 循环取得【数据库】当前操作记录中的体格检查
	            		for (Object peDel : physicalExamDel){
	                    	PhysicalExamination pe = (PhysicalExamination) peDel;
	                    	// 【数据库】中的体格检查item_type_code与【消息】中相应字段进行比较
	                    	if(pedto.getItemTypeCode().equals(pe.getItemTypeCode())){
	                    		if(isUpdateMessage(updateFlag)){
	                    			if (pedto.getItemResult() != null && !"".equals(pedto.getItemResult()) && !"4008".equals(pedto.getItemTypeCode())){
                                        // 项目类型代码
                                        pe.setItemTypeCode(pedto.getItemTypeCode());
                                        // 项目类型名称
                                        pe.setItemTypeName(pedto.getItemTypeName());
                                        // 项目结果
                                        pe.setItemResult(pedto.getItemResult());
                                        // 项目结果单位
                                        pe.setItemResultUnit(pedto.getItemResultUnit());
                                        // 检查时间
                                        pe.setExamTime(DateUtils.stringToDate(pedto.getPhysicalTime()));
                                        // 更新时间
                                        pe.setUpdateTime(sysdate);
                                        // 更新回数
                                        // pe.setUpdateCount(Constants.INSERT_UPDATE_COUNT);
                                        // 设置更新人
                                        pe.setUpdateby(serviceId); 
                                        // 设置医疗机构编码
                                        pe.setOrgCode(orgCode);
                                        // 设置医疗机构名称
                                        pe.setOrgName(orgName);

                                        physicalExamUpdList.add(pe);
                                    }
                                    // 用来存放血压检测
                                    if(Constants.HIGH_PRESSURE.equals(pe.getBloodPressureFlag()) && !StringUtils.isEmpty(pedto.getSystolicPressure()) && "4008".equals(pedto.getItemTypeCode()))
                                    {
                                        // 项目类型代码
                                        pe.setItemTypeCode(pedto.getItemTypeCode());
                                        // 项目类型名称
                                        pe.setItemTypeName(pedto.getItemTypeName());
                                        // 项目结果-高压
                                        pe.setItemResult(pedto.getSystolicPressure());
                                        // 项目结果单位
                                        pe.setItemResultUnit(pedto.getSystolicPressureUnit());
                                        // 检查时间
                                        pe.setExamTime(DateUtils.stringToDate(pedto.getPhysicalTime()));
                                        // 高压低压标志-高压
                                        pe.setBloodPressureFlag(Constants.HIGH_PRESSURE);
                                        // 更新时间
                                        pe.setUpdateTime(sysdate);
                                        // 设置更新人
                                        pe.setUpdateby(serviceId);
                                        // 设置医疗机构编码
                                        pe.setOrgCode(orgCode);
                                        // 设置医疗机构名称
                                        pe.setOrgName(orgName);
                                        physicalExamUpdList.add(pe);
                                    }
                                    if(Constants.LOW_PRESSURE.equals(pe.getBloodPressureFlag()) && !StringUtils.isEmpty(pedto.getDiastolicPressure()) && "4008".equals(pedto.getItemTypeCode()))
                                    {
                                        // 项目类型代码
                                        pe.setItemTypeCode(pedto.getItemTypeCode());
                                        // 项目类型名称
                                        pe.setItemTypeName(pedto.getItemTypeName());
                                        // 项目结果-低压
                                        pe.setItemResult(pedto.getDiastolicPressure());
                                        // 项目结果单位
                                        pe.setItemResultUnit(pedto.getDiastolicPressureUnit());
                                        // 检查时间
                                        pe.setExamTime(DateUtils.stringToDate(pedto.getPhysicalTime()));
                                        // 高压低压标志-低压
                                        pe.setBloodPressureFlag(Constants.LOW_PRESSURE);
                                        // 更新时间
                                        pe.setUpdateTime(sysdate);
                                        // 设置更新人
                                        pe.setUpdateby(serviceId); 
                                        // 设置医疗机构编码
                                        pe.setOrgCode(orgCode);
                                        // 设置医疗机构名称
                                        pe.setOrgName(orgName);

                                        physicalExamUpdList.add(pe);
                                    }
	                    		}else if(Constants.DELETE_MESSAGE_FLAG.equals(updateFlag)){
	                    			peList.add(pe);
	                    		}
	                    	}
	                    }
	            	}
                }
            }       
            // [BUG]0039754 MODIFY END
            physicalExamDelList.addAll(peList);
        }
        return physicalExamDelList;
    }
    
    // [BUG]0013379 ADD END
    
    private boolean isNewMessage(POOBIN000005UV01 poobin000005uv01){
    	return isNewMessage(poobin000005uv01.getNewUpFlag());
    }
    
    private boolean isNewMessage(String flag){
    	return Constants.NEW_MESSAGE_FLAG.equals(flag);
    }
    
    private boolean isDeleteMessage(POOBIN000005UV01 poobin000005uv01){
    	return isDeleteMessage(poobin000005uv01.getNewUpFlag());
    }
    
    private boolean isDeleteMessage(String flag){
    	return Constants.DELETE_MESSAGE_FLAG.equals(flag);
    }
    
    private boolean isUpdateMessage(POOBIN000005UV01 poobin000005uv01){
    	return isUpdateMessage(poobin000005uv01.getNewUpFlag());
    }
    
    private boolean isUpdateMessage(String flag){
    	return Constants.UPDATE_MESSAGE_FLAG.equals(flag);
    }
}
