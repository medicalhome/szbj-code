package com.yly.cdr.batch.writer;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.yly.cdr.cache.DictionaryCache;
import com.yly.cdr.core.Constants;
import com.yly.cdr.entity.MedicalVisit;
import com.yly.cdr.entity.WithdrawRecord;
import com.yly.cdr.hl7.dto.prpain402007uv01.PRPAIN402007UV01;
import com.yly.cdr.service.CommonService;
import com.yly.cdr.service.VisitService;
import com.yly.cdr.util.DateUtils;
import com.yly.cdr.util.StringUtils;

/**
 * 出院召回消息数据接入writer
 * @author wei_peng
 */
@Component(value = "prpain402007uv01Writer")
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class PRPAIN402007UV01Writer implements BusinessWriter<PRPAIN402007UV01>
{

    private static final Logger logger = LoggerFactory.getLogger(PRPAIN402003UV02Writer.class);

    private static final Logger loggerBS321 = LoggerFactory.getLogger("BS321");

    @Autowired
    private VisitService visitService;

    @Autowired
    private CommonService commonService;

    // 关联就诊记录
    private MedicalVisit medicalVisit;

    // 系统时间
    private Date systemTime = DateUtils.getSystemTime();
    
    // 消息id
    private String serviceId;

    /**
     * 数据非空校验
     */
    @Override
    public boolean validate(PRPAIN402007UV01 message)
    {
    	// $Author :chang_xuewen
        // $Date : 2014/1/22 10:30$
        // [BUG]0042086 MODIFY BEGIN
        /*if(!message.getOrganizationCode().equals(message.getMessage().getOrgCode()))
        {
        	loggerBS321.error(
					"Message:{},validate:{}",
					message.toString(),
					"消息头中的医疗机构编码和消息体中的医疗机构编码不同，消息头中的医疗机构编码="
							+ message.getMessage().getOrgCode()
							+ "，消息体中的医疗机构编码="
							+ message.getOrganizationCode());
			return false;
        }*/
    	if(StringUtils.isEmpty(message.getOrganizationCode())){
    		// $Author :yang_mingjie
        	// $Date : 2014/06/26 10:09$
        	// [BUG]0045630 MODIFY BEGIN 
    		message.setOrganizationCode(Constants.HOSPITAL_CODE);
    		message.setOrganizationName(Constants.HOSPITAL_NAME);
    		//[BUG]0045630 MODIFY END
    	}
    	// [BUG]0042086 MODIFY BEGIN
    	return true;
    }

    /**
     * 数据关联校验
     */
    @Override
    public boolean checkDependency(PRPAIN402007UV01 message, boolean flag)
    {
        String patientDomain = message.getPatientDomain();
        String patientLid = message.getPatientLid();
        String visitTimes = message.getVisitTimes();
        // 获取关联就诊记录
        medicalVisit = commonService.mediVisit(patientDomain, patientLid,
                visitTimes,message.getOrganizationCode(), message.getVisitOrdNo(), message.getVisitType());
        // 存在关联就诊记录
        if (medicalVisit != null)
        {
            logger.debug("已找到对应的出院记录：patientLid=" + patientLid
                + ", patientDomain=" + patientDomain + ", visitTimes=" + visitTimes + ", visitOrdNo=" + message.getVisitOrdNo() + ", visitType=" + message.getVisitType());
            return true;
        }
        logger.error("MessageId:{};关联出院记录未找到，患者本地ID：" + patientLid + "，域ID"
            + patientDomain + "，就诊次数：" + visitTimes + ", 就诊流水号：" + message.getVisitOrdNo() + ", 就诊类型：" + message.getVisitType(),
                message.getMessage().getId());
        if (flag)
        {
            loggerBS321.error("Message:{},checkDependency:{}",
                    message.toString(), "关联出院记录未找到，患者本地ID：" + patientLid
                        + "，域ID" + patientDomain + "，就诊次数：" + visitTimes + ", 就诊流水号：" + message.getVisitOrdNo() + ", 就诊类型：" + message.getVisitType());
        }
        return false;
    }

    /**
     * 保存更新消息内容
     */
    @Override
    @Transactional
    public void saveOrUpdate(PRPAIN402007UV01 message)
    {
        serviceId = message.getMessage().getServiceId();
        
        // $Author :tong_meng
        // $Date : 2012/6/26 10:33$
        // [BUG]0007418 MODIFY BEGIN
        // 就诊状态代码
        medicalVisit.setVisitStatusCode(Constants.VISIT_STATUS_IN_HOSPITAL);
        // [BUG]0007418 MODIFY END

        // $Author :tong_meng
        // $Date : 2012/6/26 10:33$
        // [BUG]0007418 MODIFY BEGIN
        // 就诊状态名称
        medicalVisit.setVisitStatusName(DictionaryCache.getDictionary(
                Constants.DOMAIN_VISIT_STATUS).get(
                Constants.VISIT_STATUS_IN_HOSPITAL));
        // [BUG]0007418 MODIFY END
        
        // 清空出院日期
        medicalVisit.setDischargeDate(null);
        
        // $Author:wei_peng
        // $Date:2012/8/2 15:20
        // [BUG]0008287 ADD BEIGIN
        // 出院状态代码
        medicalVisit.setDischargeStatusCode(Constants.VISIT_STATUS_WITHDRAW);
        // 出院状态名称
        medicalVisit.setDischargeStatusName(DictionaryCache.getDictionary(
                Constants.DOMAIN_VISIT_STATUS).get(
                Constants.VISIT_STATUS_WITHDRAW));
        // 更新时间
        medicalVisit.setUpdateTime(systemTime);
        // [BUG]0008287 ADD END

        // $Author:wei_peng
        // $Date:2013/01/28 13:53
        // [BUG]0013688 ADD BEGIN
        // 出院病区ID
        medicalVisit.setDischargeWardId(message.getWardsId());
        // 出院病区名称
        medicalVisit.setDischargeWardName(message.getWardsName());
        // 出院床号
        medicalVisit.setDischargeBedNo(message.getBedNo());
        // [BUG]0013688 ADD END
        // 修改者
        medicalVisit.setUpdateby(serviceId);

        WithdrawRecord withdrawRecord = getWithdrawRecord(message);
        visitService.saveWithdrawMedicalVisit(medicalVisit, withdrawRecord);
    }

    /**
     * 获取召回信息
     * @param message 消息内容
     * @return 召回信息
     */
    private WithdrawRecord getWithdrawRecord(PRPAIN402007UV01 message)
    {
        WithdrawRecord withdrawRecord = new WithdrawRecord();
        // 召回时间
        withdrawRecord.setWithdrawTime(DateUtils.stringToDate(message.getWithdrawTime()));
        // 召回原因
        withdrawRecord.setWithdrawReason(message.getWithdrawReason());
        // 召回人ID
        withdrawRecord.setWithdrawPerson(message.getWithdrawPerson());
        // 召回人姓名
        withdrawRecord.setWithdrawPersonName(message.getWithdrawPersonName());
        // 创建时间
        withdrawRecord.setCreateTime(systemTime);
        // 删除标志
        withdrawRecord.setDeleteFlag(Constants.NO_DELETE_FLAG);
        // 更新回数
        withdrawRecord.setUpdateCount(Constants.INSERT_UPDATE_COUNT);
        // 更新时间
        withdrawRecord.setUpdateTime(systemTime);
        // 就诊内部序列号
        withdrawRecord.setVisitSn(medicalVisit.getVisitSn());
        // 创建者
        withdrawRecord.setCreateby(serviceId);
        return withdrawRecord;
    }

}
