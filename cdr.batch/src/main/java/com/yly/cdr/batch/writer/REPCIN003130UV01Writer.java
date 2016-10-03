/**
 * Copryright
 */
package com.yly.cdr.batch.writer;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.yly.cdr.core.Constants;
import com.yly.cdr.entity.MedicalVisit;
import com.yly.cdr.entity.TransferHistory;
import com.yly.cdr.hl7.dto.repcin003130uv01.REPCIN003130UV01;
import com.yly.cdr.service.CommonService;
import com.yly.cdr.util.DateUtils;
import com.yly.cdr.util.StringUtils;

/**
 * 转科数据接入writer
 * @author jin_peng
 */
@Component(value = "repcin003130uv01Writer")
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class REPCIN003130UV01Writer implements BusinessWriter<REPCIN003130UV01>
{
    private static final Logger logger = LoggerFactory.getLogger(REPCIN003130UV01Writer.class);

    private static final Logger loggerBS312 = LoggerFactory.getLogger("BS312");

    // 就诊对象
    private MedicalVisit medicalVisit;

    // 转科转床对象
    private TransferHistory transferHistory;

    // 域代码
    private String patientDomain;

    // 患者本地ID
    private String patientLid;

    // 医嘱本地ID
    private String orderLid;

    @Autowired
    private CommonService commonService;

    // 消息id
    private String serviceId;

    /**
     * 转科消息字段非空校验
     */
    @Override
    public boolean validate(REPCIN003130UV01 repcin003130uv01)
    {
    	// $Author :chang_xuewen
        // $Date : 2014/1/22 10:30$
        // [BUG]0042086 MODIFY BEGIN
        String headOrgCode = repcin003130uv01.getMessage().getOrgCode();
        String orgCode = repcin003130uv01.getOrgCode();

        /*if (!headOrgCode.equals(orgCode))
        {
            logger.error("MessageId:{};消息体与消息头中的医疗机构代码不相同，{}",
                    repcin003130uv01.getMessage().getId(), "orgCode = "
                        + orgCode + "; headOrgCode = " + headOrgCode);

            loggerBS312.error("Message:{},validate:{}",
                    repcin003130uv01.toString(), "消息体与消息头中的医疗机构代码不相同，"
                        + "orgCode = " + orgCode + "; headOrgCode = "
                        + headOrgCode);

            return false;
        }*/
        if(StringUtils.isEmpty(orgCode)){
        	// $Author :yang_mingjie
        	// $Date : 2014/06/26 10:09$
        	// [BUG]0045630 MODIFY BEGIN 
        	repcin003130uv01.setOrgCode(Constants.HOSPITAL_CODE);
        	repcin003130uv01.setOrgName(Constants.HOSPITAL_NAME);
        	//[BUG]0045630 MODIFY END
        }
        // [BUG]0042086 MODIFY END
        return true;
    }

    /**
     * 有关联时进行业务校验 新增时判断相应就诊是否存在 更新时判断相应转科记录是否存在
     */
    @Override
    public boolean checkDependency(REPCIN003130UV01 repcin003130uv01,
            boolean flag)
    {
        // $Author :tongmeng
        // $Date : 2012/5/29 14:30$
        // [BUG]0006657 MODIFY BEGIN
        // 患者本地ID
        patientLid = repcin003130uv01.getPatientLid();
        // [BUG]0006657 MODIFY END

        // 域代码
        patientDomain = repcin003130uv01.getPatientDomain();
        // 本地医嘱号
        orderLid = repcin003130uv01.getOrderLid();

        // 获得此转科消息对应就诊对象
        medicalVisit = commonService.mediVisit(patientDomain, patientLid,
                repcin003130uv01.getVisitTimes(),
                repcin003130uv01.getOrgCode());

        // 转科消息新增状态时校验
        if (Constants.NEW_MESSAGE_FLAG.equals(repcin003130uv01.getTriggerEvent()))
        {
            // 当新增转科记录时需判断相应的就诊信息是否存在
            if (medicalVisit == null)
            {
                logger.error("MessageId:{};当前转科消息新增时所对应的就诊信息不存在，{}",
                        repcin003130uv01.getMessage().getId(), "域ID："
                            + patientDomain + "，患者本地ID：" + patientLid
                            + "，就诊次数：" + repcin003130uv01.getVisitTimes()
                            + "; orgCode = " + repcin003130uv01.getOrgCode());
                if (flag)
                {
                    loggerBS312.error(
                            "Message:{},checkDependency:{}",
                            repcin003130uv01.toString(),
                            "当前转科消息新增时所对应的就诊信息不存在，" + "域ID：" + patientDomain
                                + "，患者本地ID：" + patientLid + "，就诊次数："
                                + repcin003130uv01.getVisitTimes()
                                + "; orgCode = "
                                + repcin003130uv01.getOrgCode());
                }
                return false;
            }
        }

        // 转科消息更新状态时校验
        if (Constants.UPDATE_MESSAGE_FLAG.equals(repcin003130uv01.getTriggerEvent()))
        {
            // 获得转科更新时所对应的转科记录
            transferHistory = commonService.getTransferHistory(patientDomain,
                    patientLid, orderLid, repcin003130uv01.getOrgCode(),
                    repcin003130uv01.getFromDept(),repcin003130uv01.getFromWards(),repcin003130uv01.getFromBed());

            // 判断需要更新的转科记录是否存在
            if (transferHistory == null)
            {
                logger.error("MessageId:{};更新时转科记录不存在，域ID：" + patientDomain
                    + "，患者本地ID：" + patientLid + "，本地医嘱号:" + orderLid
                    + "; orgCode = " + repcin003130uv01.getOrgCode(),
                        repcin003130uv01.getMessage().getId());
                if (flag)
                {
                    loggerBS312.error("Message:{},checkDependency:{}",
                            repcin003130uv01.toString(), "更新时转科记录不存在，域ID："
                                + patientDomain + "，患者本地ID：" + patientLid
                                + "，本地医嘱号:" + orderLid + "; orgCode = "
                                + repcin003130uv01.getOrgCode());
                }
                return false;
            }
        }

        // 判断转出科室与患者当前所在科室是否相同 (转科消息新增或更新均校验)
        if (!repcin003130uv01.getFromDept().equals(
                medicalVisit.getDischargeDeptId()))
        {
            logger.error(
                    "MessageId:{};当前转科消息中的转出科室和相应就诊表中患者当前所在科室不相符，转出科室："
                        + repcin003130uv01.getFromDept() + "，患者当前所在科室："
                        + medicalVisit.getDischargeDeptId(),
                    repcin003130uv01.getMessage().getId());
            if (flag)
            {
                loggerBS312.error("Message:{},checkDependency:{}",
                        repcin003130uv01.toString(),
                        "当前转科消息中的转出科室和相应就诊表中患者当前所在科室不相符，转出科室："
                            + repcin003130uv01.getFromDept() + "，患者当前所在科室："
                            + medicalVisit.getDischargeDeptId());
            }
            return false;
        }

        // 判断转出病区与患者当前所在病区是否相同 (转科消息新增或更新均校验)
        if (!repcin003130uv01.getFromWards().equals(
                medicalVisit.getDischargeWardId()))
        {
            logger.error(
                    "MessageId:{};当前转科消息中的转出病区和相应就诊表中患者当前所在病区不相符  ，转出病区："
                        + repcin003130uv01.getFromWards() + "，患者当前所在病区："
                        + medicalVisit.getDischargeWardId(),
                    repcin003130uv01.getMessage().getId());
            if (flag)
            {
                loggerBS312.error("Message:{},checkDependency:{}",
                        repcin003130uv01.toString(),
                        "当前转科消息中的转出病区和相应就诊表中患者当前所在病区不相符  ，转出病区： "
                            + repcin003130uv01.getFromWards() + "，患者当前所在病区："
                            + medicalVisit.getDischargeWardId());
            }
            return false;
        }

        // 判断转出床号与患者当前所在床号是否相同 (转科消息新增或更新均校验)
        if (!repcin003130uv01.getFromBed().equals(
                medicalVisit.getDischargeBedNo()))
        {
            logger.error(
                    "MessageId:{};当前转科消息中的转出床号和相应就诊表中患者当前所在床号不相符， 转出床号："
                        + repcin003130uv01.getFromBed() + "，患者当前所在床号："
                        + medicalVisit.getDischargeBedNo(),
                    repcin003130uv01.getMessage().getId());
            if (flag)
            {
                loggerBS312.error("Message:{},checkDependency:{}",
                        repcin003130uv01.toString(),
                        "当前转科消息中的转出床号和相应就诊表中患者当前所在床号不相符， 转出床号："
                            + repcin003130uv01.getFromBed() + "，患者当前所在床号：{}"
                            + medicalVisit.getDischargeBedNo());
            }
            return false;
        }

        return true;
    }

    /**
     * 获取到相应转科数据和相应就诊数据进行新增或更新操作
     * @param repcin003130uv01 xml文件中获取的转科相应业务数据
     */
    @Override
    @Transactional
    public void saveOrUpdate(REPCIN003130UV01 repcin003130uv01)
    {
        serviceId = repcin003130uv01.getMessage().getServiceId();

        // 设置此消息相应转科转床记录
        setTransferHistory(repcin003130uv01);

        // 设置此消息相应就诊记录
        setMedicalVisit(repcin003130uv01);

        // $Author :jia_yanqing
        // $Date : 2012/8/1 13:33$
        // [BUG]0008467 MODIFY BEGIN
        // 根据此消息状态新增或修改相应转科转床及就诊记录
        commonService.saveOrUpdateTransferAndMedicalVisit(
                repcin003130uv01.getTriggerEvent(), transferHistory,
                medicalVisit, "dischargeDeptId", "dischargeDeptName",
                "dischargeWardId", "dischargeWardName", "dischargeBedNo",
                "updateTime", "updateby");
        // [BUG]0008467 MODIFY END
    }

    /**
     * 设置新增或修改状态时的转科转床记录对象
     */
    private void setTransferHistory(REPCIN003130UV01 repcin003130uv01)
    {
        // 取得系统当前时间
        Date systemTime = DateUtils.getSystemTime();

        // 当状态为新增时需要设置的转科对象属性
        if (Constants.NEW_MESSAGE_FLAG.equals(repcin003130uv01.getTriggerEvent()))
        {
            transferHistory = new TransferHistory();

            // 设置就诊内部序列号
            transferHistory.setVisitSn(medicalVisit.getVisitSn());

            // 设置患者内部序列号
            transferHistory.setPatientSn(medicalVisit.getPatientSn());

            // 设置域ID
            transferHistory.setPatientDomain(patientDomain);

            // 设置患者本地ID
            transferHistory.setPatientLid(patientLid);

            // $Author:tong_meng
            // $Date:2013/7/24 11:00
            // [BUG]0035063 MODIFY BEGIN
            // $Author:wei_peng
            // $Date:2012/10/16 10:17
            // [BUG]0010334 MODIFY BEGIN
            // $Author :tong_meng
            // $Date : 2012/6/25 14:33$
            // [BUG]0007418 MODIFY BEGIN
            // 操作类型标识
            transferHistory.setOrderTypeCode(Constants.TRANSFER_HISTORY_TRANS_ANSWER);
            // [BUG]0007418 MODIFY END

            // $Author :tong_meng
            // $Date : 2012/6/25 14:33$
            // [BUG]0007418 ADD BEGIN
            // 操作类型名称
            transferHistory.setOrderTypeName("转科");
            // [BUG]0007418 ADD END
            // [BUG]0010334 MODIFY END
            // [BUG]0035063 MODIFY END

            // 设置本地医嘱号
            transferHistory.setOrderLid(orderLid);

            // 设置创建时间
            transferHistory.setCreateTime(systemTime);

            // 设置新增时的更新回数
            transferHistory.setUpdateCount(Constants.INSERT_UPDATE_COUNT);

            // 设置新增时的删除标记
            transferHistory.setDeleteFlag(Constants.NO_DELETE_FLAG);

            // 设置创建者
            transferHistory.setCreateby(serviceId);
        }
        else
        {
            // 设置修改者
            transferHistory.setUpdateby(serviceId);
        }

        // 设置执行人ID
        transferHistory.setExecPersonId(repcin003130uv01.getExecPerson());

        // 设置执行人姓名
        transferHistory.setExecPersonName(repcin003130uv01.getExecPersonName());

        // 设置执行时间
        transferHistory.setExecTime(DateUtils.stringToDate(repcin003130uv01.getExecTime()));

        // $Author :tong_meng
        // $Date : 2012/6/25 14:33$
        // [BUG]0007418 MODIFY BEGIN
        // 转出科室编号
        transferHistory.setFromDeptId(repcin003130uv01.getFromDept());
        // [BUG]0007418 MODIFY END

        // $Author :tong_meng
        // $Date : 2012/6/25 14:33$
        // [BUG]0007418 ADD BEGIN
        // 转出科室名称
        transferHistory.setFromDeptName(repcin003130uv01.getFromDeptName());
        // [BUG]0007418 ADD END

        // $Author :tong_meng
        // $Date : 2012/6/25 14:33$
        // [BUG]0007418 MODIFY BEGIN
        // 设置转出病区编码
        transferHistory.setFromWardId(repcin003130uv01.getFromWards());
        // [BUG]0007418 MODIFY END

        // $Author :tong_meng
        // $Date : 2012/6/25 14:33$
        // [BUG]0007418 ADD BEGIN
        transferHistory.setFromWardName(repcin003130uv01.getFromWardsName());
        // [BUG]0007418 ADD END

        // 设置转出床号
        transferHistory.setFromBed(repcin003130uv01.getFromBed());

        // $Author :tong_meng
        // $Date : 2012/6/25 14:33$
        // [BUG]0007418 MODIFY BEGIN
        // 转入科室ID
        transferHistory.setToDeptId(repcin003130uv01.getToDept());
        // [BUG]0007418 MODIFY END

        // $Author :tong_meng
        // $Date : 2012/6/25 14:33$
        // [BUG]0007418 ADD BEGIN
        // 转入科室名称
        transferHistory.setToDeptName(repcin003130uv01.getToDeptName());
        // [BUG]0007418 ADD END

        // $Author :tong_meng
        // $Date : 2012/6/25 14:33$
        // [BUG]0007418 MODIFY BEGIN
        // 设置转入病区
        transferHistory.setToWardId(repcin003130uv01.getToWards());
        // [BUG]0007418 MODIFY END

        // $Author :tong_meng
        // $Date : 2012/6/25 14:33$
        // [BUG]0007418 ADD BEGIN
        // 转入病区名称
        transferHistory.setToWardName(repcin003130uv01.getToWardsName());
        // [BUG]0007418 ADD END

        // 设置转入床号
        transferHistory.setToBed(repcin003130uv01.getToBed());

        // 设置更新时间
        transferHistory.setUpdateTime(systemTime);

        // 设置医疗机构代码
        transferHistory.setOrgCode(repcin003130uv01.getOrgCode());

        // 设置医疗机构名称
        transferHistory.setOrgName(repcin003130uv01.getOrgName());
    }

    /**
     * 设置转科完成后相应就诊记录中患者所在科室，所在病区，所在床号，更新时间
     */
    private void setMedicalVisit(REPCIN003130UV01 repcin003130uv01)
    {
        // $Author :tong_meng
        // $Date : 2012/6/25 14:33$
        // [BUG]0007418 MODIFY BEGIN
        // 设置出院科室ID
        medicalVisit.setDischargeDeptId(repcin003130uv01.getToDept());
        // [BUG]0007418 MODIFY END

        // $Author :tong_meng
        // $Date : 2012/6/25 14:33$
        // [BUG]0007418 ADD BEGIN
        // 设置出院科室名稱
        medicalVisit.setDischargeDeptName(repcin003130uv01.getToDeptName());
        // [BUG]0007418 ADD BEGIN

        // 设置出院病区
        medicalVisit.setDischargeWardId(repcin003130uv01.getToWards());

        // $Author :jia_yanqing
        // $Date : 2012/8/1 13:33$
        // [BUG]0008467 ADD BEGIN
        // 设置出院病区名称
        medicalVisit.setDischargeWardName(repcin003130uv01.getToWardsName());
        // [BUG]0008467 ADD END

        // 设置出院床号
        medicalVisit.setDischargeBedNo(repcin003130uv01.getToBed());

        // 设置更新时间
        medicalVisit.setUpdateTime(DateUtils.getSystemTime());

        // 设置修改者
        medicalVisit.setUpdateby(serviceId);
    }

}
