package com.founder.cdr.batch.writer;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.founder.cdr.core.Constants;
import com.founder.cdr.entity.MedicalVisit;
import com.founder.cdr.entity.NursingOperation;
import com.founder.cdr.entity.PhysicalExamination;
import com.founder.cdr.hl7.dto.Graded;
import com.founder.cdr.hl7.dto.GradedDetail;
import com.founder.cdr.hl7.dto.GradedItems;
import com.founder.cdr.hl7.dto.repcin000070uv01.REPCIN000070UV01;
import com.founder.cdr.service.CareService;
import com.founder.cdr.service.CommonService;
import com.founder.cdr.util.DateUtils;
import com.founder.cdr.util.StringUtils;

/**
 * 医学评分
 * @author tong_meng
 *
 */
@Component(value = "repcin000070uv01Writer")
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class REPCIN000070UV01Writer implements BusinessWriter<REPCIN000070UV01>
{
    private static final Logger logger = LoggerFactory.getLogger(REPCIN000070UV01Writer.class);
    
    // Author :jia_yanqing
    // Date : 2013/06/08 11:00
    // [BUG]0033506 ADD BEGIN
    private static final Logger loggerBS317 = LoggerFactory.getLogger("BS317");
    // [BUG]0033506 ADD END

    @Autowired
    private CommonService commonService;

    @Autowired
    private CareService careService;

    // 护理操作.护理内部序列号sequences
    private static final String SEQ_NURSING_OPERATION = "SEQ_NURSING_OPERATION";

    private static final String SEQ_PHYSICAL_EXAMINATION = "SEQ_PHYSICAL_EXAM";

    // 护理操作内部序列号
    private BigDecimal operationSn;

    // 系统时间
    private Date systemDate = DateUtils.getSystemTime();

    // 检查时间
    private Date examTime;

    // 新增标志
    private String newUpFlag;

    // 消息中的数据
    private Graded graded;

    // 就诊内部序列号
    private BigDecimal visitSn;

    // 患者内部序列号
    private BigDecimal patientSn;

    // 患者域ID
    private String patientDomain;

    // 患者本地ID
    private String patientLid;

    // 消息中的评分大项
    private List<GradedItems> gradedItemList;

    // 消息中的评分小项
    private List<GradedDetail> gradedDetailList;

    // 要保存的大项和小项的集合
    private List<List<Object>> physicalExamList;
    
    // 消息id
    private String serviceId;
    
    // $Author :chang_xuewen
    // $Date : 2013/12/05 11:00$
    // [BUG]0040271 ADD BEGIN
    private String orgCode;
    private String orgName;
    // [BUG]0040271 ADD END

    // $Author :tong_meng
    // $Date : 2012/9/6 15:00$
    // [BUG]0008782 MODIFY BEGIN
    @Override
    public boolean validate(REPCIN000070UV01 t)
    {
    	// $Author :chang_xuewen
        // $Date : 2014/1/22 10:30$
        // [BUG]0042086 MODIFY BEGIN
    	// $Author :chang_xuewen
        // $Date : 2013/12/06 11:00$
        // [BUG]0040271 ADD BEGIN
        orgCode = t.getOrgCode();
        orgName = t.getOrgName();
    	/*if(!t.getOrgCode().equals(t.getMessage().getOrgCode())){
    		loggerBS317.error(
                    "Message:{}, validate:{}",
                    t.toString(),
                    "消息头与消息体中的医疗机构代码不一致：头：OrgCode = " 
                    + t.getMessage().getOrgCode() 
                    + " ，体：OrgCode ="
                    + t.getOrgCode());
        	return false;
        }*/
    	// [BUG]0040271 ADD END
        if(StringUtils.isEmpty(orgCode)){
        	// $Author :yang_mingjie
        	// $Date : 2014/06/26 10:09$
        	// [BUG]0045630 MODIFY BEGIN 
        	orgCode = Constants.HOSPITAL_CODE;
        	orgName = Constants.HOSPITAL_NAME;
        	//[BUG]0045630 MODIFY END
        }
        // [BUG]0042086 MODIFY END
        boolean b = true;

        List<GradedItems> gradedItems = t.getGraded().get(0).getGradedItems();

        for (GradedItems gradedItem : gradedItems)
        {
            List<GradedDetail> gradedDetails = gradedItem.getGradedDetail();
            if (gradedDetails != null && !gradedDetails.isEmpty())
            {

                for (GradedDetail gradedDetail : gradedDetails)
                {
                    if (!StringUtils.isNotNullAll(gradedDetail.getCode(),
                            gradedDetail.getItemsGraded()))
                    {
                        // Author: jia_yanqing
                        // Date: 2013/06/08 11:30
                        // [BUG]: 0033506 MODIFY BEGIN
                        loggerBS317.error("Message:{},validate{}",
                                t.toString(),
                                "医学评分项目细目编码,医学评分项目细目评分不能为空！  医学评分项目细目编码：Code=" 
                                + gradedDetail.getCode()
                                +"医学评分项目细目评分:ItemsGraded"
                                +gradedDetail.getItemsGraded());
                        // [BUG]: 0033506 MODIFY BEGIN
                        return false;
                    }
                }
            }
        }

        return b;
    }

    // [BUG]0008782 MODIFY END

    /**
     * 关联就诊表
     */
    @Override
    public boolean checkDependency(REPCIN000070UV01 t,boolean flag)
    {
        newUpFlag = t.getNewUpFlag();
        if (isMessageExist(t))
        {
            if (Constants.NEW_MESSAGE_FLAG.equals(newUpFlag))
            {
                if (isMedicalVisitExist(t))
                    return true;
                // Author :jia_yanqing
                // Date : 2013/06/08 11:20
                // [BUG]0033506 MODIFY BEGIN
                logger.error("关联就诊信息不存在！");
                if(flag){
                    loggerBS317.error("Message:{},checkDependency:{}",
                            t.toString(),
                            "关联就诊信息不存在！");
                }
                return false;
            }
            else
            {
                logger.error("错误的消息交互类型: {}" , newUpFlag);
                if(flag){
                    loggerBS317.error("Message:{},checkDependency:{}",
                            t.toString(),
                            "错误的消息交互类型");
                }
                // [BUG]0033373 MODIFY END
                return false;
            }
        }
        return false;
    }

    /**
     * 检查循环节点中是否有值
     * @param t
     * @return
     */
    private boolean isMessageExist(REPCIN000070UV01 t)
    {
        List<Graded> gradeds = t.getGraded();
        if (gradeds == null || gradeds.size() == 0)
        {
            logger.error("评分信息不存在！");
            return false;
        }
        graded = gradeds.get(0);
        examTime = DateUtils.stringToDate(graded.getGradedRecordTime());
        gradedItemList = graded.getGradedItems();
        if (gradedItemList == null || gradedItemList.size() == 0)
        {
            logger.error("评分详细不存在！");
            return false;
        }
        // for (GradedItems gradedItems : gradedItemList)
        // {
        // gradedDetailList = gradedItems.getGradedDetail();
        // if (gradedDetailList == null || gradedDetailList.size() == 0)
        // return false;
        // }
        return true;
    }

    /**
     * 查询就诊记录是否存在
     * @param t
     * @return
     */
    private boolean isMedicalVisitExist(REPCIN000070UV01 t)
    {
        patientDomain = graded.getPatientDomain();

        // $Author :tongmeng
        // $Date : 2012/5/29 14:30$
        // [BUG]0006657 MODIFY BEGIN
        patientLid = graded.getPatientLid();
        // [BUG]0006657 MODIFY END

        MedicalVisit medicalVisit = commonService.mediVisit(patientDomain,
                patientLid, graded.getVisitTimes(), orgCode);
        if (medicalVisit == null)
            return false;
        logger.debug("就诊表中存在记录！域ID{},患者本地ID{}", patientDomain, patientLid);
        visitSn = medicalVisit.getVisitSn();
        patientSn = medicalVisit.getPatientSn();
        logger.debug("就诊内部序列号{},患者内部序列号{}", visitSn, patientSn);
        return true;
    }

    /**
     * 插入护理操作表和体格检查
     */
    @Override
    @Transactional
    public void saveOrUpdate(REPCIN000070UV01 t)
    {
        serviceId = t.getMessage().getServiceId();
        
        NursingOperation nursingOpreation = getNursingOperation();
        physicalExamList = getPhysicalExamination();
        if (Constants.NEW_MESSAGE_FLAG.equals(newUpFlag))
            careService.saveNursingPhyExam(nursingOpreation, physicalExamList);
    }

    private NursingOperation getNursingOperation()
    {
        NursingOperation nursingOpreation = new NursingOperation();
        operationSn = commonService.getSequence(SEQ_NURSING_OPERATION);
        nursingOpreation.setOperationSn(operationSn);
        nursingOpreation.setVisitSn(visitSn);
        nursingOpreation.setPatientSn(patientSn);
        nursingOpreation.setPatientDomain(patientDomain);
        nursingOpreation.setPatientLid(patientLid);
        nursingOpreation.setOperatorId(graded.getRecordDoctorID());
        nursingOpreation.setOperatorName(graded.getRecordDoctorName());
        nursingOpreation.setCreateTime(systemDate);
        nursingOpreation.setUpdateTime(systemDate);
        nursingOpreation.setUpdateCount(Constants.INSERT_UPDATE_COUNT);
        nursingOpreation.setDeleteFlag(Constants.NO_DELETE_FLAG);
        nursingOpreation.setCreateby(serviceId);
        // $Author :chang_xuewen
        // $Date : 2013/12/05 11:00$
        // [BUG]0040271 ADD BEGIN
        nursingOpreation.setOrgCode(orgCode);
        nursingOpreation.setOrgName(orgName);
        // [BUG]0040271 ADD END
        return nursingOpreation;
    }

    private List<List<Object>> getPhysicalExamination()
    {
        PhysicalExamination physicalExamination = null;
        // 遍历评分大项
        physicalExamList = new ArrayList<List<Object>>();
        for (GradedItems gradedItems : gradedItemList)
        {

            List<Object> physicalExaminations = new ArrayList<Object>();

            gradedDetailList = gradedItems.getGradedDetail();
            if (gradedDetailList == null || gradedDetailList.size() == 0)
            {
                physicalExamination = new PhysicalExamination();
                // 护理操作内部序列号
                physicalExamination.setOperationSn(operationSn);
                // 项目类型代码
                physicalExamination.setItemTypeCode(gradedItems.getGradedItemsId());
                // 项目类型名称
                physicalExamination.setItemTypeName(gradedItems.getGradedItemsName());
                BigDecimal examinationSn = commonService.getSequence(SEQ_PHYSICAL_EXAMINATION);
                // 体格检查内部序列号
                physicalExamination.setExaminationSn(examinationSn);
                // 评分细目代码
                physicalExamination.setItemCode(null);
                // 评分细目名称
                physicalExamination.setItemName(null);
                // 评分细目结果
                physicalExamination.setItemResult(null);
                // 评分细目结果单位
                physicalExamination.setItemResultUnit(null);
                // 检查时间
                physicalExamination.setExamTime(examTime);
                physicalExamination.setCreateTime(systemDate);
                physicalExamination.setUpdateTime(systemDate);
                physicalExamination.setUpdateCount(Constants.INSERT_UPDATE_COUNT);
                physicalExamination.setDeleteFlag(Constants.NO_DELETE_FLAG);
                physicalExamination.setCreateby(serviceId);
                physicalExamination.setDeleteTime(null);
                // $Author :chang_xuewen
                // $Date : 2013/12/05 11:00$
                // [BUG]0040271 ADD BEGIN
                physicalExamination.setOrgCode(orgCode);
                physicalExamination.setOrgName(orgName);
                // [BUG]0040271 ADD END
                physicalExaminations.add(physicalExamination);
            }
            else
            {
                for (GradedDetail gradedDetail : gradedDetailList)
                {
                    physicalExamination = new PhysicalExamination();
                    // 护理操作内部序列号
                    physicalExamination.setOperationSn(operationSn);
                    // 项目类型代码
                    physicalExamination.setItemTypeCode(gradedItems.getGradedItemsId());
                    // 项目类型名称
                    physicalExamination.setItemTypeName(gradedItems.getGradedItemsName());
                    BigDecimal examinationSn = commonService.getSequence(SEQ_PHYSICAL_EXAMINATION);
                    // 体格检查内部序列号
                    physicalExamination.setExaminationSn(examinationSn);
                    // 评分细目代码
                    physicalExamination.setItemCode(gradedDetail.getCode());
                    // 评分细目名称
                    physicalExamination.setItemName(gradedDetail.getItemsName());
                    // 评分细目结果
                    physicalExamination.setItemResult(gradedDetail.getItemsGraded());
                    // 评分细目结果单位
                    physicalExamination.setItemResultUnit(gradedDetail.getUnit());
                    // 检查时间
                    physicalExamination.setExamTime(examTime);
                    physicalExamination.setCreateTime(systemDate);
                    physicalExamination.setUpdateTime(systemDate);
                    physicalExamination.setUpdateCount(Constants.INSERT_UPDATE_COUNT);
                    physicalExamination.setDeleteFlag(Constants.NO_DELETE_FLAG);
                    physicalExamination.setDeleteTime(null);
                    physicalExamination.setCreateby(serviceId);
                    // $Author :chang_xuewen
                    // $Date : 2013/12/05 11:00$
                    // [BUG]0040271 ADD BEGIN
                    physicalExamination.setOrgCode(orgCode);
                    physicalExamination.setOrgName(orgName);
                    // [BUG]0040271 ADD END
                    physicalExaminations.add(physicalExamination);
                }
            }
            physicalExamList.add(physicalExaminations);
        }
        return physicalExamList;
    }
}
