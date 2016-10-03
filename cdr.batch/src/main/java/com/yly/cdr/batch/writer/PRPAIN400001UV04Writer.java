/**
 * 入出留观数据接入
 * @author jia_yanqing
 * @date 2012/05/14
 */
package com.yly.cdr.batch.writer;

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
import com.yly.cdr.entity.MedicalVisit;
import com.yly.cdr.entity.TransferHistory;
import com.yly.cdr.hl7.dto.prpain400001uv04.PRPAIN400001UV04;
import com.yly.cdr.service.CommonService;
import com.yly.cdr.util.DateUtils;
import com.yly.cdr.util.StringUtils;

@Component(value = "prpain400001uv04Writer")
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class PRPAIN400001UV04Writer implements BusinessWriter<PRPAIN400001UV04>
{
    private static final Logger logger = LoggerFactory.getLogger(PRPAIN400001UV04Writer.class);

    private static final Logger loggerBS308 = LoggerFactory.getLogger("BS308");

    // 就诊记录
    private MedicalVisit medicalVisit;

    // 待更新的入/出留观记录
    private TransferHistory transferHistoryToUpdate;

    // 患者本地ID
    private String patientLid;

    // 获取系统时间
    private Date systemTime = DateUtils.getSystemTime();

    @Autowired
    private CommonService commonService;
    
    // 消息id
    private String serviceId;

    /**
     * 业务数据非空检验
     */
    @Override
    public boolean validate(PRPAIN400001UV04 prpain400001uv04)
    {
    	// $Author :chang_xuewen
        // $Date : 2014/1/22 10:30$
        // [BUG]0042086 MODIFY BEGIN
        /*if(!prpain400001uv04.getOrganizationCode().equals(prpain400001uv04.getMessage().getOrgCode()))
        {
        	loggerBS308.error(
					"Message:{},validate:{}",
					prpain400001uv04.toString(),
					"消息头中的医疗机构编码和消息体中的医疗机构编码不同，消息头中的医疗机构编码="
							+ prpain400001uv04.getMessage().getOrgCode()
							+ "，消息体中的医疗机构编码="
							+ prpain400001uv04.getOrganizationCode());
			return false;
        }*/
    	if(StringUtils.isEmpty(prpain400001uv04.getOrganizationCode())){
    		// $Author :yang_mingjie
        	// $Date : 2014/06/26 10:09$
        	// [BUG]0045630 MODIFY BEGIN 
    		prpain400001uv04.setOrganizationCode(Constants.HOSPITAL_CODE);
    		prpain400001uv04.setOrganizationName(Constants.HOSPITAL_NAME);
    		//[BUG]0045630 MODIFY END
        }  
    	// [BUG]0042086 MODIFY END
    	if (Constants.IN_OBSERVE_FLAG.equals(prpain400001uv04.getInOutFlag()))
        {
            // Author :jia_yanqing
            // Date : 2013/3/22 10:30
            // [BUG]0014673 MODIFY BEGIN
            // 验证入留观消息中入留观医生编码，入留观医生姓名，入留观病区编码，入留观病区名称，入留观床位号为非空
            if (!StringUtils.isNotNullAll(prpain400001uv04.getToExecPersonId(),
                    prpain400001uv04.getToExecPersonName(),
                    prpain400001uv04.getToWards(),
                    prpain400001uv04.getToWardsName(),
                    prpain400001uv04.getToBed()))
            {
                loggerBS308.error(
                        "Message:{},validate:{}",
                        prpain400001uv04.toString(),
                        "非空字段验证未通过! 入留观医生编码：ToExecPersonId = "
                            + prpain400001uv04.getToExecPersonId()
                            + ";入留观医生姓名：ToExecPersonName = "
                            + prpain400001uv04.getToExecPersonName()
                            + ";入留观病区编码：ToWards = "
                            + prpain400001uv04.getToWards()
                            + ";入留观病区名称：ToWardsName = "
                            + prpain400001uv04.getToWardsName()
                            + ";入留观床位号：ToBed = " + prpain400001uv04.getToBed());
                return false;
            }
            // [BUG]0014673 MODIFY END
        }
        else if (Constants.OUT_OBSERVE_FLAG.equals(prpain400001uv04.getInOutFlag()))
        {
            // Author :jia_yanqing
            // Date : 2013/5/9 11:01
            // [BUG]0014785 MODIFY BEGIN
            // Author :jia_yanqing
            // Date : 2013/3/28 10:30
            // [BUG]0014785 MODIFY BEGIN
            // 验证出留观消息中出留观医生编码，出留观医生名称为非空
            if (!StringUtils.isNotNullAll(
                    prpain400001uv04.getFromExecPersonId(),
                    prpain400001uv04.getFromExecPersonName()))
            {
                loggerBS308.error(
                        "Message:{},validate{}",
                        prpain400001uv04.toString(),
                        "非空字段验证未通过! 出留观医生编码：FromExecPersonId = "
                            + prpain400001uv04.getFromExecPersonId()
                            + ";出留观医生名称：FromExecPersonName = "
                            + prpain400001uv04.getFromExecPersonName());
                return false;
            }
            // [BUG]0014785 MODIFY END
            // [BUG]0014785 MODIFY END
        }
        // $Author :tong_meng
        // $Date : 2012/9/14 11:30$
        // [BUG]0009742 MODIFY BEGIN
        else
        {
            loggerBS308.error("Message:{},validate{}",
                    prpain400001uv04.toString(),
                    "错误的入留观标识：" + prpain400001uv04.getInOutFlag());
            return false;
        }
        // [BUG]0009742 ADD BEGIN
        return true;
    }

    /**
     * 检查消息的依赖关系
     */
    @Override
    public boolean checkDependency(PRPAIN400001UV04 prpain400001uv04,
            boolean flag)
    {
        // $Author :tongmeng
        // $Date : 2012/5/29 14:30$
        // [BUG]0006657 MODIFY BEGIN
        // 获取患者本地ID
        patientLid = prpain400001uv04.getPatientLid();
        // [BUG]0006657 MODIFY END
        // 获取就诊记录
        medicalVisit = getMedicalVisit(prpain400001uv04);
        if (medicalVisit == null)
        {
            logger.error("MessageId:{};在新增或更新入/出留观消息时相应的就诊信息不存在，域ID："
                + prpain400001uv04.getPatientDomain() + "，患者本地ID：" + patientLid
                + "，就诊次数：" + prpain400001uv04.getVisitTimes(),
                    prpain400001uv04.getMessage().getId());
            if (flag)
            {
                loggerBS308.error(
                        "Message:{},checkDependency:{}",
                        prpain400001uv04.toString(),
                        "在新增或更新入/出留观消息时相应的就诊信息不存在，域ID："
                            + prpain400001uv04.getPatientDomain() + "，患者本地ID："
                            + patientLid + "，就诊次数："
                            + prpain400001uv04.getVisitTimes());
            }
            return false;
        }
        // $Author:wei_peng
        // $Date:2012/10/15 18:09
        // $[BUG]0010334 ADD BEGIN
        // 如果是更新消息
        if (Constants.UPDATE_MESSAGE_FLAG.equals(prpain400001uv04.getTriggerEvent()))
        {

            // 通过就诊获取对应的入\出留观记录
            List<Object> transferHistorys = getTransferHistorys(prpain400001uv04);
            // 对应入\出留观记录不存在
            if (transferHistorys == null || transferHistorys.size() == 0)
            {
                logger.error(
                        "MessageId:{};待更新的的入出留观记录不存在，就诊内部序列号："
                            + medicalVisit.getVisitSn() + "，入出留观标识："
                            + prpain400001uv04.getInOutFlag(),
                        prpain400001uv04.getMessage().getId());
                if (flag)
                {
                    loggerBS308.error(
                            "Message:{},checkDependency:{}",
                            prpain400001uv04.toString(),
                            "待更新的的入出留观记录不存在，就诊内部序列号："
                                + medicalVisit.getVisitSn() + "，入出留观标识："
                                + prpain400001uv04.getInOutFlag());
                }
                return false;
            }
            else
            {
                // 获取入\出留观记录赋值公共变量
                transferHistoryToUpdate = (TransferHistory) transferHistorys.get(0);
            }
        }
        // $[BUG]0010334 ADD END

        return true;
    }

    /**
     * 校验成功后把取到的相应数据进行保存操作
     * @param PRPAIN400001UV041 prpain400001uv04 
     */
    @Override
    @Transactional
    public void saveOrUpdate(PRPAIN400001UV04 prpain400001uv04)
    {
        serviceId = prpain400001uv04.getMessage().getServiceId();
        
        TransferHistory transferHistory = getTransferHistory(prpain400001uv04);

        // $Author:wei_peng
        // $Date:2012/10/15 18:09
        // $[BUG]0010334 MODIFY BEGIN
        if (Constants.NEW_MESSAGE_FLAG.equals(prpain400001uv04.getTriggerEvent()))
        {
            // 增加入/出留观信息
            commonService.save(transferHistory);
        }
        else if (Constants.UPDATE_MESSAGE_FLAG.equals(prpain400001uv04.getTriggerEvent()))
        {
            // 更新入/出留观信息
            commonService.update(transferHistory);
        }
        // $[BUG]0010334 MODIFY BEGIN

    }

    // $Author :jia_yanqing
    // $Date : 2012/7/16 20:33$
    // [BUG]0006546 MODIFY BEGIN
    /**
     * 获取消息中的入/出留观内容
     * @param prpain400001uv04
     * @return 入/出留观对象
     */
    public TransferHistory getTransferHistory(PRPAIN400001UV04 prpain400001uv04)
    {
        TransferHistory transferHistory = null;

        if (Constants.NEW_MESSAGE_FLAG.equals(prpain400001uv04.getTriggerEvent()))
        {
            transferHistory = new TransferHistory();

            // 就诊内部序列号
            transferHistory.setVisitSn(medicalVisit.getVisitSn());

            // 患者内部序列号
            transferHistory.setPatientSn(medicalVisit.getPatientSn());

            // 域代码
            transferHistory.setPatientDomain(prpain400001uv04.getPatientDomain());

            // 患者本地Lid
            transferHistory.setPatientLid(patientLid);

            // 更新回数
            transferHistory.setUpdateCount(Constants.INSERT_UPDATE_COUNT);

            // 创建时间
            transferHistory.setCreateTime(systemTime);

            // 删除标志
            transferHistory.setDeleteFlag(Constants.NO_DELETE_FLAG);
            
            // 创建者
            transferHistory.setCreateby(serviceId);
        }
        else if (Constants.UPDATE_MESSAGE_FLAG.equals(prpain400001uv04.getTriggerEvent()))
        {
            transferHistory = transferHistoryToUpdate;
            
            // 修改者
            transferHistory.setUpdateby(serviceId);
        }

        // 更新时间
        transferHistory.setUpdateTime(systemTime);
        // 执行时间
        transferHistory.setExecTime(DateUtils.stringToDate(prpain400001uv04.getExecTime()));
        // 医疗机构编码
        transferHistory.setOrgCode(prpain400001uv04.getOrganizationCode());
        // 医疗机构名称
        transferHistory.setOrgName(prpain400001uv04.getOrganizationName());

        // 如果新消息为入留观消息
        if (Constants.IN_OBSERVE_FLAG.equals(prpain400001uv04.getInOutFlag()))
        {
            // 入留观医生标识/执行人ID
            transferHistory.setExecPersonId(prpain400001uv04.getToExecPersonId());
            // 入留观医生姓名/执行人姓名
            transferHistory.setExecPersonName(prpain400001uv04.getToExecPersonName());
            // 病情分级代码
            transferHistory.setGradingCode(prpain400001uv04.getGradingCode());
            // 病情分级名称
            transferHistory.setGradingName(prpain400001uv04.getGradingName());

            // $Author :tong_meng
            // $Date : 2012/6/25 14:33$
            // [BUG]0007418 MODIFY BEGIN
            // 转入病区ID
            transferHistory.setToWardId(prpain400001uv04.getToWards());
            // [BUG]0007418 MODIFY END

            // $Author :tong_meng
            // $Date : 2012/6/25 14:33$
            // [BUG]0007418 ADD BEGIN
            // 转出病区名称
            transferHistory.setToWardName(prpain400001uv04.getToWardsName());
            // [BUG]0007418 ADD END

            // 转入床号
            transferHistory.setToBed(prpain400001uv04.getToBed());

            // $Author:wei_peng
            // $Date:2012/10/15 18:09
            // $[BUG]0010334 ADD BEGIN
            // 操作类型标识
            transferHistory.setOrderTypeCode(Constants.TRANSFER_HISTORY_IN_OBSERVE);

            // 操作类型名称
            transferHistory.setOrderTypeName("入留观");
            // $[BUG]0010334 ADD END

        }
        // 如果新消息为出留观消息
        else if (Constants.OUT_OBSERVE_FLAG.equals(prpain400001uv04.getInOutFlag()))
        {
            // 出留观医生标识/执行人ID
            transferHistory.setExecPersonId(prpain400001uv04.getFromExecPersonId());
            // 出留观医生姓名/执行人姓名
            transferHistory.setExecPersonName(prpain400001uv04.getFromExecPersonName());
            // 出留观方式编码
            transferHistory.setViewRoomOutWayCode(prpain400001uv04.getViewRoomOutWayCode());
            // 出留观方式名称
            transferHistory.setViewRoomOutWayName(prpain400001uv04.getViewRoomOutWayName());
            // 病人意识编码
            transferHistory.setPatientLocCode(prpain400001uv04.getPatientLocCode());
            // 病人意识名称
            transferHistory.setPatientLocName(prpain400001uv04.getPatientLocName());

            // $Author :tong_meng
            // $Date : 2012/6/25 14:33$
            // [BUG]0007418 MODIFY BEGIN
            // 出留观去向编码
            transferHistory.setDestinationCode(prpain400001uv04.getDestinationCode());
            // [BUG]0007418 MODIFY BEGIN

            // $Author :tong_meng
            // $Date : 2012/6/25 14:33$
            // [BUG]0007418 ADD BEGIN
            // 出留观去向名称
            transferHistory.setDestinationName(prpain400001uv04.getDestinationName());
            // [BUG]0007418 ADD END

            // $Author :tong_meng
            // $Date : 2012/6/25 14:33$
            // [BUG]0007418 MODIFY BEGIN
            // 转出病区ID
            transferHistory.setFromWardId(prpain400001uv04.getFromWards());
            // [BUG]0007418 MODIFY END

            // $Author :tong_meng
            // $Date : 2012/6/25 14:33$
            // [BUG]0007418 ADD BEGIN
            // 转出病区名称
            transferHistory.setFromWardName(prpain400001uv04.getFromWardsName());
            // [BUG]0007418 ADD END

            // 转出床号
            transferHistory.setFromBed(prpain400001uv04.getFromBed());
            // $Author:wei_peng
            // $Date:2012/10/15 18:09
            // $[BUG]0010334 ADD BEGIN
            // 操作类型标识编码
            transferHistory.setOrderTypeCode(Constants.TRANSFER_HISTORY_OUT_OBSERVE);

            // 操作类型标识名称
            transferHistory.setOrderTypeName("出留观");
            // $[BUG]0010334 ADD END
        }

        return transferHistory;
    }

    // [BUG]0006546 MODIFY END

    /**
     * 获取入留观消息关联的就诊记录
     * @param prpain400001uv04
     * @return 就诊记录
     */
    public MedicalVisit getMedicalVisit(PRPAIN400001UV04 prpain400001uv04)
    {
        // 患者域ID
        String patientDomain = prpain400001uv04.getPatientDomain();

        // 就诊次数
        String visitTimes = prpain400001uv04.getVisitTimes();

        // 根据域ID,患者本地ID，就诊次数获取就诊记录
        MedicalVisit medicalVisit = commonService.mediVisit(patientDomain,
                patientLid, visitTimes,prpain400001uv04.getOrganizationCode());

        return medicalVisit;
    }

    // $Author:wei_peng
    // $Date:2012/10/15 18:09
    // $[BUG]0010334 ADD BEGIN
    /**
     * 查找就诊对应的入/出留观记录
     * @param prpain400001uv04
     * @return 入/出留观记录
     */
    private List<Object> getTransferHistorys(PRPAIN400001UV04 prpain400001uv04)
    {
        Map<String, Object> condition = new HashMap<String, Object>();
        condition.put("visitSn", medicalVisit.getVisitSn());
        condition.put("deleteFlag", Constants.NO_DELETE_FLAG);
        condition.put("orgCode",prpain400001uv04.getOrganizationCode());
        // 入留观消息
        if (Constants.IN_OBSERVE_FLAG.equals(prpain400001uv04.getInOutFlag()))
        {
            // 查询条件添加入留观标识
            condition.put("orderTypeCode",
                    Constants.TRANSFER_HISTORY_IN_OBSERVE);
        }
        // 出留观消息
        else if (Constants.OUT_OBSERVE_FLAG.equals(prpain400001uv04.getInOutFlag()))
        {
            // 查询条件添加出留观标识
            condition.put("orderTypeCode",
                    Constants.TRANSFER_HISTORY_OUT_OBSERVE);
        }
        return commonService.selectByCondition(TransferHistory.class, condition);
    }
    // $[BUG]0010334 ADD END

}
