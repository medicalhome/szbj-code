package com.yly.cdr.batch.writer;

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

import com.yly.cdr.core.Constants;
import com.yly.cdr.dao.InpatientDao;
import com.yly.cdr.entity.MedicalVisit;
import com.yly.cdr.entity.TransferHistory;
import com.yly.cdr.hl7.dto.prpain302011uv01.PRPAIN302011UV01;
import com.yly.cdr.hl7.dto.prpain400001uv03.PRPAIN400001UV03;
import com.yly.cdr.service.CommonService;
import com.yly.cdr.util.DataMigrationUtils;
import com.yly.cdr.util.DateUtils;
import com.yly.cdr.util.DictionaryUtils;
import com.yly.cdr.util.StringUtils;

/**
 * [FUN] A01-017 转科/转区转床 (急诊留观,住院护士站)
 * @version 1.0, 2012/05/02
 * @author guo_hongyan
 * @since 1.0
 */
@Component(value = "prpain302011uv01Writer")
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class PRPAIN302011UV01Writer implements BusinessWriter<PRPAIN302011UV01>
{
    private static final Logger logger = LoggerFactory.getLogger(PRPAIN302011UV01Writer.class);

    private static final Logger loggerBS352 = LoggerFactory.getLogger("BS352");

    @Autowired
    private CommonService commonService;
    @Autowired
    private InpatientDao inpatientDao;

    // 就诊内部序列号
    private BigDecimal visitSn;

    // 患者内部序列号
    private BigDecimal patientSn;

    // 域代码
    private String patientDomain;
    
    // 患者本地ID
    private String patientLid;
    
    // 转科转床区分
    private String orderTypeCode;
    
	// 医嘱本地ID
    private String orderLid;

    // 转科转床
    private TransferHistory transferHistory;

    // 就诊
    private MedicalVisit medicalVisit;

    // 系统时间
    private Date sysdate;

    public boolean validate(PRPAIN302011UV01 prpain302011uv01)
    {
        // Author:jia_yanqing
        // Date:2012/10/30 15:20
        // [BUG]10905 MODIFY BEGIN
        // $Author:wei_peng
        // $Date:2012/9/21 14:02
        // $[BUG]0009776 ADD BEGIN
        /*
         * if (Constants.PATIENT_DOMAIN_INPATIENT.equals(prpain302011uv01.
         * getPatientDomain()) &&
         * StringUtils.isEmpty(prpain302011uv01.getOrderLid())) {
         * logger.error("住院时，医嘱号不能为空！"); return false; }
         */
        // $[BUG]0009776 ADD END
        // [BUG]10905 MODIFY END
        
    	// $Author :chang_xuewen
        // $Date : 2014/1/22 10:30$
        // [BUG]0042086 MODIFY BEGIN
        // $Author:tong_meng
        // $Date:2013/12/03 11:00
        // [BUG]0040270 ADD BEGIN
        /*if(!prpain302011uv01.getOrgCode().equals(prpain302011uv01.getMessage().getOrgCode()))
        {
            logger.error("消息头中的医疗机构代码与消息体中的医疗机构代码不一致！" +
                    "消息头医疗机构代码：{}，消息体医疗机构代码：{}",
                    prpain302011uv01.getMessage().getOrgCode(),
                    prpain302011uv01.getOrgCode());
            return false;
        }*/
        // [BUG]0040270 ADD END
    	if(StringUtils.isEmpty(prpain302011uv01.getOrgCode())){
    		// $Author :yang_mingjie
        	// $Date : 2014/06/26 10:09$
        	// [BUG]0045630 MODIFY BEGIN 
    		prpain302011uv01.setOrgCode(Constants.HOSPITAL_CODE);
    		prpain302011uv01.setOrgName(Constants.HOSPITAL_NAME);
    		//[BUG]0045630 MODIFY END
        }  
    	// [BUG]0042086 MODIFY END
        return true;
    }

    public boolean checkDependency(PRPAIN302011UV01 prpain302011uv01,
            boolean flag)
    {
        // $Author :jia_yanqing
        // $Date : 2012/5/25 16:55$
        // [BUG]0006657 MODIFY BEGIN
        // 患者本地ID
        patientLid = prpain302011uv01.getPatientLid();
    	// 本地医嘱号
        orderLid = prpain302011uv01.getOrderLid();
        // 转科转床区分
        orderTypeCode = prpain302011uv01.getOrderTypeCode();
        // 域代码
        patientDomain = prpain302011uv01.getPatientDomain();
        
        logger.info("患者本地ID {}", patientLid);
        // [BUG]0006657 MODIFY END

        // $Author :jia_yanqing
        // $Date : 2012/7/16 13:55$
        // [BUG]0007955 DELETE BEGIN
        // if
        // (Constants.UPDATE_MESSAGE_FLAG.equals(prpain302011uv01.getNewUpFlg()))
        // {
        // logger.info("消息更新场合");
        // // 转区转床是否存在
        // return checkTransferResultExist(prpain302011uv01);
        // }
        // else
        // [BUG]0007955 DELETE END

        // 获得此转科消息对应就诊对象
        medicalVisit = commonService.mediVisit(patientDomain, patientLid,
                prpain302011uv01.getVisitTimes(),
                prpain302011uv01.getOrgCode(),prpain302011uv01.getVisitOrdNo(),prpain302011uv01.getVisitTypeCode());
    	//判断就诊是否存在
        if (medicalVisit == null)
        {
        	logger.error("MessageId:{};当前转科消息新增时所对应的就诊信息不存在，{}",
                    prpain302011uv01.getMessage().getId(), "域ID："
                        + patientDomain + "，患者本地ID：" + patientLid
                        + "，就诊次数：" + prpain302011uv01.getVisitTimes()
                        + "; orgCode = " + prpain302011uv01.getOrgCode());
            if (flag)
            {
                loggerBS352.error(
                        "Message:{},checkDependency:{}",
                        prpain302011uv01.toString(),
                        "当前转科消息新增时所对应的就诊信息不存在，" + "域ID：" + patientDomain
                            + "，患者本地ID：" + patientLid + "，就诊次数："
                            + prpain302011uv01.getVisitTimes()
                            + "; orgCode = "
                            + prpain302011uv01.getOrgCode());
            }
            return false;
        }
        else
        {
        	visitSn = medicalVisit.getVisitSn();
        	//判断转科，换床，包床记录是否存在
        	List<Object> transferHistorys = getTransferHistorys(prpain302011uv01);
        	if(transferHistorys == null || transferHistorys.size() == 0 )
        	{
        		if(Constants.DELETE_MESSAGE_FLAG.equals(prpain302011uv01.getNewUpFlg())){
        			logger.error("MessageId:{};取消包床时所对应的包床记录不存在，域ID：" + patientDomain
                          + "，患者本地ID：" + patientLid + "， orgCode = " + prpain302011uv01.getOrgCode(),
                              prpain302011uv01.getMessage().getId());
        			return false;
        		}
        		prpain302011uv01.setNewUpFlg(Constants.NEW_MESSAGE_FLAG); 
        		loggerBS352.error("数据库中不存在转科，换床包床记录，交互类型置为新增！");
        	}
        	else 
        	{
        		transferHistory = (TransferHistory)transferHistorys.get(0);
        	}
        }	
        // 转科消息新增状态时校验
        /*if (Constants.NEW_MESSAGE_FLAG.equals(prpain302011uv01.getNewUpFlg()))
        {
            // 当新增转科记录时需判断相应的就诊信息是否存在
            if (medicalVisit == null)
            {
                logger.error("MessageId:{};当前转科消息新增时所对应的就诊信息不存在，{}",
                        prpain302011uv01.getMessage().getId(), "域ID："
                            + patientDomain + "，患者本地ID：" + patientLid
                            + "，就诊次数：" + prpain302011uv01.getVisitTimes()
                            + "; orgCode = " + prpain302011uv01.getOrgCode());
                if (flag)
                {
                    loggerBS352.error(
                            "Message:{},checkDependency:{}",
                            prpain302011uv01.toString(),
                            "当前转科消息新增时所对应的就诊信息不存在，" + "域ID：" + patientDomain
                                + "，患者本地ID：" + patientLid + "，就诊次数："
                                + prpain302011uv01.getVisitTimes()
                                + "; orgCode = "
                                + prpain302011uv01.getOrgCode());
                }
                return false;
            }
        }*/

//        // 转科消息更新状态时校验
//        if (Constants.UPDATE_MESSAGE_FLAG.equals(prpain302011uv01.getNewUpFlg()))
//        {
//            // 获得转科更新时所对应的转科记录
//            transferHistory = commonService.getTransferHistory(patientDomain,
//                    patientLid, orderLid, prpain302011uv01.getOrgCode(),
//                    prpain302011uv01.getFromDept(),prpain302011uv01.getFromWards(),prpain302011uv01.getFromBed());
//
//            // 判断需要更新的转科记录是否存在
//            if (transferHistory == null)
//            {
//                logger.error("MessageId:{};更新时转科记录不存在，域ID：" + patientDomain
//                    + "，患者本地ID：" + patientLid + "，本地医嘱号:" + orderLid
//                    + "; orgCode = " + prpain302011uv01.getOrgCode(),
//                        prpain302011uv01.getMessage().getId());
//                if (flag)
//                {
//                    loggerBS352.error("Message:{},checkDependency:{}",
//                            prpain302011uv01.toString(), "更新时转科记录不存在，域ID："
//                                + patientDomain + "，患者本地ID：" + patientLid
//                                + "，本地医嘱号:" + orderLid + "; orgCode = "
//                                + prpain302011uv01.getOrgCode());
//                }
//                return false;
//            }
//        }
        /*
        // 判断转出科室与患者当前所在科室是否相同 (转科消息新增或更新均校验)
        if (!prpain302011uv01.getFromDept().equals(
                medicalVisit.getDischargeDeptId()))
        {
            logger.error(
                    "MessageId:{};当前转科消息中的转出科室和相应就诊表中患者当前所在科室不相符，转出科室："
                        + prpain302011uv01.getFromDept() + "，患者当前所在科室："
                        + medicalVisit.getDischargeDeptId(),
                    prpain302011uv01.getMessage().getId());
            if (flag)
            {
                loggerBS352.error("Message:{},checkDependency:{}",
                        prpain302011uv01.toString(),
                        "当前转科消息中的转出科室和相应就诊表中患者当前所在科室不相符，转出科室："
                            + prpain302011uv01.getFromDept() + "，患者当前所在科室："
                            + medicalVisit.getDischargeDeptId());
            }
            return false;
        }

        // 判断转出病区与患者当前所在病区是否相同 (转科消息新增或更新均校验)
        
        if (!prpain302011uv01.getFromWards().equals(
                medicalVisit.getDischargeWardId()))
        {
            logger.error(
                    "MessageId:{};当前转科消息中的转出病区和相应就诊表中患者当前所在病区不相符  ，转出病区："
                        + prpain302011uv01.getFromWards() + "，患者当前所在病区："
                        + medicalVisit.getDischargeWardId(),
                    prpain302011uv01.getMessage().getId());
            if (flag)
            {
                loggerBS352.error("Message:{},checkDependency:{}",
                        prpain302011uv01.toString(),
                        "当前转科消息中的转出病区和相应就诊表中患者当前所在病区不相符  ，转出病区： "
                            + prpain302011uv01.getFromWards() + "，患者当前所在病区："
                            + medicalVisit.getDischargeWardId());
            }
            return false;
        }

        // 判断转出床号与患者当前所在床号是否相同 (转科消息新增或更新均校验)
        if (!prpain302011uv01.getFromBed().equals(
                medicalVisit.getDischargeBedNo()))
        {
            logger.error(
                    "MessageId:{};当前转科消息中的转出床号和相应就诊表中患者当前所在床号不相符， 转出床号："
                        + prpain302011uv01.getFromBed() + "，患者当前所在床号："
                        + medicalVisit.getDischargeBedNo(),
                    prpain302011uv01.getMessage().getId());
            if (flag)
            {
                loggerBS352.error("Message:{},checkDependency:{}",
                        prpain302011uv01.toString(),
                        "当前转科消息中的转出床号和相应就诊表中患者当前所在床号不相符， 转出床号："
                            + prpain302011uv01.getFromBed() + "，患者当前所在床号：{}"
                            + medicalVisit.getDischargeBedNo());
            }
            return false;
        }*/

        return true;
    }

    @Transactional
    public void saveOrUpdate(PRPAIN302011UV01 prpain302011uv01)
    {
    	this.setFromDeptWardBed(prpain302011uv01);
        // 转科转床取得
        TransferHistory transferHisresult = getTransferResult(prpain302011uv01,
        		transferHistory);
        // 更新就诊数据
        MedicalVisit visitResult = getMedicalVisitTransfer(prpain302011uv01,
                medicalVisit);
      //取消包床
        if(Constants.DELETE_MESSAGE_FLAG.equals(prpain302011uv01.getNewUpFlg()))
	   	{
//        	commonService.saveOrUpdateTransferAndMedicalVisit(
//            		prpain302011uv01.getNewUpFlg(), transferHisresult,
//            		visitResult, "deleteFlag", "deleteTime",
//                    "deleteby","updateTime", "updateby");
        	commonService.update(transferHisresult);
        }
        else
        {
        // 插入或更新转科转床表、更新就诊表（出院病房、出院床号）
        commonService.saveOrUpdateTransferAndMedicalVisit(
        		prpain302011uv01.getNewUpFlg(), transferHisresult,
        		visitResult, "dischargeDeptId", "dischargeDeptName",
                "dischargeWardId", "dischargeWardName", "dischargeBedNo",
                "updateTime", "updateby");
        }
    }


    /**
     * 就诊信息判定
     * @param prpain302011uv01 转区转床映射信息
     * @return
     */
//    private boolean checkMedicalVisitInfo(PRPAIN302011UV01 prpain302011uv01,
//            boolean flag)
//    {
//        // $Author:tong_meng
//        // $Date:2013/12/03 11:00
//        // [BUG]0040270 MODIFY BEGIN
//        // 就诊信息取得
//    	medicalVisit = commonService.mediVisit(
//                prpain302011uv01.getPatientDomain(), patientLid,
//                visitTimes.intValue(),prpain302011uv01.getOrgCode(),prpain302011uv01.getVisitOrdNo(),prpain302011uv01.getVisitTypeCode());
//        // [BUG]0040270 MODIFY END
//        if (medicalVisit != null)
//        {
//            visitSn = medicalVisit.getVisitSn();
//            patientSn = medicalVisit.getPatientSn();
//            logger.info("就诊内部序列号 {},患者内部序列号{}", visitSn, patientSn);
//
//            /** 消息中科室、就诊.出院科室是否相同判断，不同的情况，则不能进行以下任何操作，
//                                          等转科更新后再更新（先转科再转床时，但转床消息先到，所以要等转科消息更新后，
//                                          转床消息报再更新）
//            **/
//            if (!StringUtils.isEmpty(prpain302011uv01.getFromDept())
//                && !StringUtils.isEmpty(medicalVisit.getDischargeDeptId()))
//            {
//                if (!prpain302011uv01.getFromDept().equals(
//                        medicalVisit.getDischargeDeptId()))
//                {
//                    logger.error(
//                            "MessageId:{};转入科室与就诊表中的出院科室不同，不能进行更新操作，转入科室编码："
//                                + prpain302011uv01.getFromDept() + "，出院科室编码："
//                                + medicalVisit.getDischargeDeptId(),
//                            prpain302011uv01.getMessage().getId());
//                    if (flag)
//                    {
//                        loggerBS352.error(
//                                "Message:{},checkDependency:{}",
//                                prpain302011uv01.toString(),
//                                "转入科室与就诊表中的出院科室不同，不能进行更新操作，转入科室编码："
//                                    + prpain302011uv01.getFromDept()
//                                    + "，出院科室编码："
//                                    + medicalVisit.getDischargeDeptId());
//                    }
//                    return false;
//                }
//            }
//            else
//            {
//                logger.error("MessageId:{};转入科室与就诊表中的出院科室为空，转入科室："
//                    + prpain302011uv01.getFromDept() + ",出院科室："
//                    + medicalVisit.getDischargeDeptId(),
//                        prpain302011uv01.getMessage().getId());
//                if (flag)
//                {
//                    loggerBS352.error(
//                            "Message:{},checkDependency:{}",
//                            prpain302011uv01.toString(),
//                            "转入科室与就诊表中的出院科室为空，转入科室："
//                                + prpain302011uv01.getFromDept() + "，出院科室："
//                                + medicalVisit.getDischargeDeptId());
//                }
//                return false;
//            }
//        }
//        else
//        {
//            logger.error(
//                    "MessageId:{};就诊信息不存在，域ID："
//                        + prpain302011uv01.getPatientDomain() + "，患者本地ID："
//                        + patientLid + "，就诊次数：" + visitTimes.intValue(),
//                    prpain302011uv01.getMessage().getId());
//            if (flag)
//            {
//                loggerBS352.error("Message:{},checkDependency:{}",
//                        prpain302011uv01.toString(), "就诊信息不存在，域ID："
//                            + prpain302011uv01.getPatientDomain() + "，患者本地ID："
//                            + patientLid + "，就诊次数：" + visitTimes.intValue());
//            }
//            return false;
//        }
//        return true;
//    }

    /**
     * 转区转房信息取得
     * @param prpain302011uv01 转区转床消息映射数据
     * @param transferResult 存在的转区转床数据
     * @return
     */
    private TransferHistory getTransferResult(
            PRPAIN302011UV01 prpain302011uv01, TransferHistory transferResult)
    {
        TransferHistory transferHistory = null;

        sysdate = DateUtils.getSystemTime();

        // $Author :jia_yanqing
        // $Date : 2012/7/16 13:55$
        // [BUG]0007955 DELETE BEGIN
         if
         (Constants.UPDATE_MESSAGE_FLAG.equals(prpain302011uv01.getNewUpFlg()))
         {
        // 更新场合
        	 transferHistory = transferResult;
        // 就诊数据取得
        // checkMedicalVisitInfo(prpain302011uv01);
        // [BUG]0007955 DELETE END

         }
         else
        //取消包床
    	 if
    	 (Constants.DELETE_MESSAGE_FLAG.equals(prpain302011uv01.getNewUpFlg()))
    	 {
    		 transferHistory = transferResult;
    		 transferHistory.setDeleteby(prpain302011uv01.getMessage().getServiceId());
    		 transferHistory.setDeleteFlag(Constants.DELETE_FLAG);
    		 transferHistory.setDeleteTime(DateUtils.stringToDate(prpain302011uv01.getToTime()));
    		 transferHistory.setUpdateTime(sysdate);
    		 transferHistory.setUpdateby(prpain302011uv01.getMessage().getServiceId());
    		 return transferHistory;
    	 }
    	 else
        if (Constants.NEW_MESSAGE_FLAG.equals(prpain302011uv01.getNewUpFlg()))
        {
            // 插入场合
            transferHistory = new TransferHistory();
            // 就诊内部序列号
            transferHistory.setVisitSn(medicalVisit.getVisitSn());
            // 患者内部序列号
            transferHistory.setPatientSn(medicalVisit.getPatientSn());
            // 域代码
            transferHistory.setPatientDomain(prpain302011uv01.getPatientDomain());
            // 患者本地ID
            transferHistory.setPatientLid(patientLid);

            // $Author:wei_peng
            // $Date:2012/10/16 10:17
            // [BUG]0010334 MODIFY BEGIN
            // $Author :tong_meng
            // $Date : 2012/6/25 14:33$
            // [BUG]0007418 MODIFY BEGIN
            // 操作类型标识
            transferHistory.setOrderTypeCode(orderTypeCode);
            // [BUG]0007418 MODIFY END

            // $Author :tong_meng
            // $Date : 2012/6/25 14:33$
            // [BUG]0007418 ADD BEGIN
            // 操作类型名称
            if(Constants.TRANSFER_HISTORY_TRANS_BED.equals(orderTypeCode)){
            	transferHistory.setOrderTypeName("转区转床");
            }else if(Constants.TRANSFER_HISTORY_TRANS_ANSWER.equals(orderTypeCode)){
            	transferHistory.setOrderTypeName("转科");
            }else if(Constants.ORDER_TYPE_TRANSFER.equals(orderTypeCode)){
            	transferHistory.setOrderTypeName("包床");
            }
            
            // [BUG]0007418 ADD END
            // [BUG]0010334 MODIFY END

            // 创建时间
            transferHistory.setCreateTime(sysdate);
            // 更新回数
            transferHistory.setUpdateCount(Constants.INSERT_UPDATE_COUNT);
            // 删除标志
            transferHistory.setDeleteFlag(Constants.NO_DELETE_FLAG);
            // $Author :chang_xuewen
            // $Date : 2013/08/31 16:00$
            // [BUG]0036757 MODIFY BEGIN
            // 创建人
            transferHistory.setCreateby(DataMigrationUtils.getDataMigration() + prpain302011uv01.getMessage().getServiceId());
            // [BUG]0036757 MODIFY END
            
            // $Author:tong_meng
            // $Date:2013/12/03 11:00
            // [BUG]0040270 ADD BEGIN
            // 医疗机构代码
            transferHistory.setOrgCode(prpain302011uv01.getOrgCode());
            // 医疗机构名称
            transferHistory.setOrgName(prpain302011uv01.getOrgName());
            // [BUG]0040270 ADD END
            
        }
        // 本地医嘱号
        transferHistory.setOrderLid(prpain302011uv01.getOrderLid());

        // $Author :tong_meng
        // $Date : 2012/6/25 14:33$
        // [BUG]0007418 MODIFY BEGIN
        // 转出科室编号
        transferHistory.setFromDeptId(prpain302011uv01.getFromDept());
        // [BUG]0007418 MODIFY END

        // $Author :tong_meng
        // $Date : 2012/6/25 14:33$
        // [BUG]0007418 ADD BEGIN
        // 转出科室名称
//        transferHistory.setFromDeptName(prpain302011uv01.getFromDeptName());
        transferHistory.setFromDeptName(DictionaryUtils.getNameFromDictionary(
        		Constants.DOMAIN_DEPARTMENT, prpain302011uv01.getFromDept(), prpain302011uv01.getFromDeptName()));
        // [BUG]0007418 ADD END

        // $Author :tong_meng
        // $Date : 2012/6/25 14:33$
        // [BUG]0007418 MODIFY BEGIN
        // 转出病区编码
        transferHistory.setFromWardId(prpain302011uv01.getFromWards());
        // [BUG]0007418 MODIFY END

        // $Author :jia_yanqing
        // $Date : 2012/7/16 13:55$
        // [BUG]0007955 MODIFY BEGIN
        // $Author :tong_meng
        // $Date : 2012/6/25 14:33$
        // [BUG]0007418 ADD BEGIN
        // 转出病区名称
//        transferHistory.setFromWardName(prpain302011uv01.getFromWardsName());
        transferHistory.setFromWardName(DictionaryUtils.getNameFromDictionary(
        		Constants.DOMAIN_WARD, prpain302011uv01.getFromWards(), prpain302011uv01.getFromWardsName()));
        // [BUG]0007418 ADD END
        // [BUG]0007955 MODIFY END

        // 转出房间编码
        transferHistory.setOldRoomCode(prpain302011uv01.getFromRoomCode());
        // 转出房间号
        transferHistory.setOldRoomNo(prpain302011uv01.getFromRoomNo());
        // 转出床号
        transferHistory.setFromBed(prpain302011uv01.getFromBed());

        // $Author :tong_meng
        // $Date : 2012/6/25 14:33$
        // [BUG]0007418 MODIFY BEGIN
        // 转入科室ID
        transferHistory.setToDeptId(prpain302011uv01.getToDept());
        // [BUG]0007418 MODIFY END
        // $Author :tong_meng
        // $Date : 2012/6/25 14:33$
        // [BUG]0007418 ADD BEGIN
        // 转入科室名称
//        transferHistory.setToDeptName(prpain302011uv01.getToDeptName());
        transferHistory.setToDeptName(DictionaryUtils.getNameFromDictionary(
        		Constants.DOMAIN_DEPARTMENT, prpain302011uv01.getToDept(), prpain302011uv01.getToDeptName()));
        // [BUG]0007418 ADD END

        // $Author :tong_meng
        // $Date : 2012/6/25 14:33$
        // [BUG]0007418 MODIFY BEGIN
        // 设置转入病区
        transferHistory.setToWardId(prpain302011uv01.getToWards());
        // [BUG]0007418 MODIFY END

        // $Author :jia_yanqing
        // $Date : 2012/7/16 13:55$
        // [BUG]0007955 MODIFY BEGIN
        // $Author :tong_meng
        // $Date : 2012/6/25 14:33$
        // [BUG]0007418 ADD BEGIN
        // 转入病区名称
//        transferHistory.setToWardName(prpain302011uv01.getToWardsName());
        transferHistory.setToWardName(DictionaryUtils.getNameFromDictionary(
        		Constants.DOMAIN_WARD, prpain302011uv01.getToWards(), prpain302011uv01.getToWardsName()));
        // [BUG]0007418 ADD END
        // [BUG]0007955 MODIFY END

        // 转入房间编码
        transferHistory.setNewRoomCode(prpain302011uv01.getToRoomCode());
        // 转入房间号
        transferHistory.setNewRoomNo(prpain302011uv01.getToRoomNo());
        // 转入床号
        transferHistory.setToBed(prpain302011uv01.getToBed());

        // $Author:wei_peng
        // $Date:2013/03/07 16:24
        // [BUG]0014402 ADD BEGIN
        transferHistory.setExecTime(DateUtils.stringToDate(prpain302011uv01.getToTime()));
        // [BUG]0014402 ADD END

        // 更新时间
        transferHistory.setUpdateTime(sysdate);
        // $Author :chang_xuewen
        // $Date : 2013/08/31 16:00$
        // [BUG]0036757 MODIFY BEGIN
        if(Constants.NEW_MESSAGE_FLAG.equals(prpain302011uv01.getNewUpFlg())){
        	// 新增人
        	transferHistory.setCreateby(DataMigrationUtils.getDataMigration() + prpain302011uv01.getMessage().getServiceId());
        }else
        	// 更新人
        	transferHistory.setUpdateby(prpain302011uv01.getMessage().getServiceId());
        // [BUG]0036757 MODIFY END
        return transferHistory;
    }

    /**
     * 
     * @param prpain302011uv01 转区转床消息映射数据
     * @param medicalVisit 存在就诊数据
     * @return
     */
    private MedicalVisit getMedicalVisitTransfer(
            PRPAIN302011UV01 prpain302011uv01, MedicalVisit medicalVisit)
    {
        MedicalVisit visitTransfer = null;
        visitTransfer = new MedicalVisit();

        // 就诊内部序列号
        visitTransfer.setVisitSn(medicalVisit.getVisitSn());
        // 出院病房
        visitTransfer.setDischargeWardId(prpain302011uv01.getToWards());
        // 出院床号
        visitTransfer.setDischargeBedNo(prpain302011uv01.getToBed());

        // $Author :jia_yanqing
        // $Date : 2012/7/16 13:55$
        // [BUG]0007955 ADD BEGIN
        // 出院病区名称
//        visitTransfer.setDischargeWardName(prpain302011uv01.getToWardsName());
        visitTransfer.setDischargeWardName(DictionaryUtils.getNameFromDictionary(
        		Constants.DOMAIN_WARD, prpain302011uv01.getToWards(), prpain302011uv01.getToWardsName()));
        // 出院科室
        visitTransfer.setDischargeDeptId(prpain302011uv01.getToDept());
        // 出院科室名称
//       visitTransfer.setDischargeDeptName(prpain302011uv01.getToDeptName());
        visitTransfer.setDischargeDeptName(DictionaryUtils.getNameFromDictionary(
        		Constants.DOMAIN_DEPARTMENT, prpain302011uv01.getToDept(), prpain302011uv01.getToDeptName()));
        // [BUG]0007955 ADD END

        // 更新时间
        visitTransfer.setUpdateTime(sysdate);
        // 更新回数
        visitTransfer.setUpdateCount(medicalVisit.getUpdateCount());
        // $Author :chang_xuewen
        // $Date : 2013/08/31 16:00$
        // [BUG]0036757 MODIFY BEGIN
        // 更新人
        visitTransfer.setUpdateby(prpain302011uv01.getMessage().getServiceId());
        // [BUG]0036757 MODIFY END
        
        // $Author:tong_meng
        // $Date:2013/12/03 11:00
        // [BUG]0040270 ADD BEGIN
        // 医疗机构代码
        visitTransfer.setOrgCode(prpain302011uv01.getOrgCode());
        // 医疗机构名称
        visitTransfer.setOrgName(prpain302011uv01.getOrgName());
        // [BUG]0040270 ADD END
        
        return visitTransfer;
    }
    /**
     * 查找就诊对应的转科换床包床记录
     * @param prpain302011uv01
     * @return 转科换床包床记录
     */
    private List<Object> getTransferHistorys(PRPAIN302011UV01 prpain302011uv01)
    {
        Map<String, Object> condition = new HashMap<String, Object>();
        condition.put("visitSn", medicalVisit.getVisitSn());
        condition.put("patientLid", medicalVisit.getPatientLid());
        condition.put("patientDomain", medicalVisit.getPatientDomain());
        condition.put("deleteFlag", Constants.NO_DELETE_FLAG);
        condition.put("orgCode",prpain302011uv01.getOrgCode());
        condition.put("orderTypeCode",orderTypeCode);
            
        return commonService.selectByCondition(TransferHistory.class, condition);
    }
    /**
     * 从数据库里获取原科室，原病区，原床位信息
     */
    private void setFromDeptWardBed(PRPAIN302011UV01 prpain302011uv01)
    {
    	List<TransferHistory> transferHistorys = inpatientDao.selectTransferHistoryList(patientLid, visitSn);
    	if(transferHistorys != null && transferHistorys.size() > 0){
    		TransferHistory transferHistory = transferHistorys.get(0);
    		prpain302011uv01.setFromDept(transferHistory.getToDeptId());
    		prpain302011uv01.setFromWards(transferHistory.getToWardId());
    		prpain302011uv01.setFromRoomNo(transferHistory.getNewRoomNo());
    		prpain302011uv01.setFromBed(transferHistory.getToBed());
    	}
    }
}
