/**
 * 入科出科
 * 
 * @version 1.0, 2012/05/07  17:53:00
 
 * @author wei_peng
 * @since 1.0
*/
package com.founder.cdr.batch.writer;

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

import com.founder.cdr.core.Constants;
import com.founder.cdr.entity.MedicalVisit;
import com.founder.cdr.entity.TransferHistory;
import com.founder.cdr.hl7.dto.prpain400001uv03.PRPAIN400001UV03;
import com.founder.cdr.service.CommonService;
import com.founder.cdr.util.DataMigrationUtils;
import com.founder.cdr.util.DateUtils;
import com.founder.cdr.util.DictionaryUtils;
import com.founder.cdr.util.StringUtils;

@Component(value = "prpain400001uv03Writer")
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class PRPAIN400001UV03Writer implements BusinessWriter<PRPAIN400001UV03>
{

    private static final Logger logger = LoggerFactory.getLogger(PRPAIN400001UV03Writer.class);
    private static final Logger loggerBS316 = LoggerFactory.getLogger("BS316");

    @Autowired
    private CommonService commonService;

    // 患者本地ID
    private String patientLid;

    // 入科或出科关联的就诊信息
    private MedicalVisit medicalVisit;
    // 待更新的入/出科记录
    private TransferHistory transferHistoryToUpdate;
    // 系统的当前时间
    private Date systemTime = DateUtils.getSystemTime();
    
    // 消息id
    private String serviceId;

    /**
     * 数据业务校验
     */
    @Override
    public boolean validate(PRPAIN400001UV03 message)
    {
    	// $Author :chang_xuewen
        // $Date : 2014/1/22 10:30$
        // [BUG]0042086 MODIFY BEGIN
        // $Author:tong_meng
        // $Date:2013/12/03 11:00
        // [BUG]0040270 ADD BEGIN
        /*if(!message.getOrgCode().equals(message.getMessage().getOrgCode()))
        {
            logger.error("消息头中的医疗机构代码与消息体中的医疗机构代码不一致！" +
                    "消息头医疗机构代码：{}，消息体医疗机构代码：{}",
                    message.getMessage().getOrgCode(),
                    message.getOrgCode());
            return false;
        }*/
        // [BUG]0040270 ADD END
    	if(StringUtils.isEmpty(message.getOrgCode())){
    		// $Author :yang_mingjie
        	// $Date : 2014/06/26 10:09$
        	// [BUG]0045630 MODIFY BEGIN 
    		message.setOrgCode(Constants.HOSPITAL_CODE);
    		message.setOrgName(Constants.HOSPITAL_NAME);
    		//[BUG]0045630 MODIFY END
        }  
    	// [BUG]0042086 MODIFY END
       // if (isTransferIn(message))
       // {
            // 验证入科消息中入科操作者编码，入科科室编码，入科科室名称，入科病区编码，入科病区名称为非空
            if (!StringUtils.isNotNullAll(message.getInExecPersonId(),
                    message.getDeptId(), 
//                    message.getDeptName(),
                    message.getWardsId()
//                    , message.getWardsName()
                    ))
            {
                loggerBS316.error(
                        "Message:{},validate:{}",
                        message.toString(),
                        "非空字段验证未通过! 入科操作者编码：InExecPersonId = "
                            + message.getInExecPersonId() + ";入科科室编码：DeptId = "
//                            + message.getDeptId() + ";入科科室名称：DeptName = "
                            + message.getDeptName() + ";入科病区编码：WardsId = "
//                            + message.getWardsId() + ";入科病区名称：WardsName = "
                            + message.getWardsName());
                return false;
            }
       // }
/*        else if (isTransferOut(message))
        {
            // 验证出科消息中出科去向名称，出科操作者编码，出科操作者名称为非空
            if (!StringUtils.isNotNullAll(message.getDestinationName(),
                    message.getOutExecPersonId(),
                    message.getOutExecPersonName()))
            {
                loggerBS316.error(
                        "Message:{},validate:{}",
                        message.toString(),
                        "非空字段验证未通过! 出科去向名称：DestinationName = "
                            + message.getDestinationName()
                            + ";出科操作者编码：OutExecPersonId = "
                            + message.getOutExecPersonId()
                            + ";出科操作者名称：OutExecPersonName = "
                            + message.getOutExecPersonName());
                return false;
            }
        }*/
/*        else
        {
            return false;
        }*/
        return true;
    }

    /**
     * 检查消息的依赖关系
     */
    @Override
    public boolean checkDependency(PRPAIN400001UV03 message,boolean flag)
    {
        // $Author :tongmeng
        // $Date : 2012/5/29 14:30$
        // [BUG]0006657 MODIFY BEGIN
        // 获取患者本地ID
        patientLid = message.getPatientLid();
        // [BUG]0006657 MODIFY END

        // 判断患者本地ID是否存在
        if (patientLid == null || patientLid.trim().isEmpty())
        {
            logger.error("患者本地ID不存在");
            return false;
        }
        // 获取就诊记录
        medicalVisit = getMedicalVisit(message);
        if (medicalVisit == null)
        {
            logger.error("MessageId:{};在新增或更新入/出科消息时相应的就诊信息不存在，域ID："
                + message.getPatientDomain() + "，患者本地ID：" + patientLid
                + "，就诊次数：" + message.getVisitTimes()+ "，就诊流水号：" + message.getVisitOrdNo()+ "，就诊类型：" + message.getVisitTypeCode(),
                message.getMessage().getId());
            if (flag)
            {
                loggerBS316.error(
                        "Message:{},checkDependency:{}",
                        message.toString(),
                        "在新增或更新入/出科消息时相应的就诊信息不存在，域ID："
                            + message.getPatientDomain() + "，患者本地ID："
                            + patientLid + "，就诊次数："
                            + message.getVisitTimes()+ "，就诊流水号：" + message.getVisitOrdNo()+ "，就诊类型：" + message.getVisitTypeCode());
            }
            return false;
        }  
        
        // 通过就诊获取对应的入\出科记录
        List<Object> transferHistorys = getTransferHistorys(message);
        // 对应入\出科记录不存在
        if (transferHistorys == null || transferHistorys.size() == 0)
        {
        	message.setTriggerEvent(Constants.NEW_MESSAGE_FLAG);
        	loggerBS316.error("不存在入科记录，交互类型置为新增");
        }
        else
        {
        	message.setTriggerEvent(Constants.UPDATE_MESSAGE_FLAG);
            // 获取入\出科记录赋值公共变量
            transferHistoryToUpdate = (TransferHistory) transferHistorys.get(0);
        }
        // $Author:li_shenggen
        // $Date:2014/08/13 10:09
        // $[BUG]0047377 ADD BEGIN
        // 如果是更新消息
        /*if (Constants.UPDATE_MESSAGE_FLAG.equals(message.getTriggerEvent()))
        {

            // 通过就诊获取对应的入\出科记录
            List<Object> transferHistorys = getTransferHistorys(message);
            // 对应入\出科记录不存在
            if (transferHistorys == null || transferHistorys.size() == 0)
            {
                logger.error(
                        "MessageId:{};待更新的的入出科记录不存在，就诊内部序列号："
                            + medicalVisit.getVisitSn() + "，入出科标识："
                            + message.getTransferFlag(),
                            message.getMessage().getId());
                if (flag)
                {
                	loggerBS316.error(
                            "Message:{},checkDependency:{}",
                            message.toString(),
                            "待更新的的入出科记录不存在，就诊内部序列号："
                                + medicalVisit.getVisitSn() + "，入出科标识："
                                + message.getTransferFlag());
                }
                return false;
            }
            else
            {
                // 获取入\出科记录赋值公共变量
                transferHistoryToUpdate = (TransferHistory) transferHistorys.get(0);
            }
        }*/
        // $[BUG]0047377 ADD END
        return true;
    }

    /**
     * 保存校验后的入科出科信息
     */
    @Override
    public void saveOrUpdate(PRPAIN400001UV03 message)
    {
        serviceId = message.getMessage().getServiceId();
        
        // Author :tong_meng
        // Date : 2013/11/26 14:49
        // [BUG]0040006 ADD BEGIN
        // 入科时间
        medicalVisit.setEntranceTime(DateUtils.stringToDate(message.getExecTime()));
        medicalVisit.setUpdateby(serviceId);
        medicalVisit.setUpdateTime(systemTime);
        
        // 就诊医生按照主治医生取值
        medicalVisit.setVisitDoctorId(message.getAttendingDoctor());
        medicalVisit.setVisitDoctorName(DictionaryUtils.getNameFromDictionary(
        		Constants.DOMAIN_STAFF, message.getAttendingDoctor(), message.getAttendingDoctorName()));
        
        // 住院医生编号
        medicalVisit.setResidentDoctorId(message.getResidentDoctor());
//        medicalVisit.setRegisteredDeptName(message.getResidentDoctorName());
        medicalVisit.setResidentDoctorName(DictionaryUtils.getNameFromDictionary(
        		Constants.DOMAIN_STAFF, message.getResidentDoctor(), message.getResidentDoctorName()));
        // 主治医生编号
        medicalVisit.setAttendingDoctorId(message.getAttendingDoctor());
//        medicalVisit.setAttendingDoctorName(message.getAttendingDoctorName());
        medicalVisit.setAttendingDoctorName(DictionaryUtils.getNameFromDictionary(
        		Constants.DOMAIN_STAFF, message.getAttendingDoctor(), message.getAttendingDoctorName()));
        // 主任医生编号
        medicalVisit.setDeptDirectorId(message.getDeptDirector());
//        medicalVisit.setDeptDirectorName(message.getDeptDirectorName());
        medicalVisit.setDeptDirectorName(DictionaryUtils.getNameFromDictionary(
        		Constants.DOMAIN_STAFF, message.getDeptDirector(), message.getDeptDirectorName()));
        // 入院床位
        medicalVisit.setAdmissionBedNo(message.getBedNo());
        // 当前床位（出院床位）
        medicalVisit.setDischargeBedNo(message.getBedNo());
        // 病区
        if(message.getWardsId() != null){
        	medicalVisit.setAdmissionWardId(message.getWardsId());
            medicalVisit.setAdmissionWardName(DictionaryUtils.getNameFromDictionary(Constants.DOMAIN_WARD, 
            		message.getWardsId(), message.getWardsName()));
            medicalVisit.setDischargeWardId(message.getWardsId());
            medicalVisit.setDischargeWardName(DictionaryUtils.getNameFromDictionary(Constants.DOMAIN_WARD, 
            		message.getWardsId(), message.getWardsName()));
        }
        // 科室
        if(message.getDeptId() != null){
            medicalVisit.setVisitDeptId(message.getDeptId());
            medicalVisit.setVisitDeptName(DictionaryUtils.getNameFromDictionary(Constants.DOMAIN_DEPARTMENT, 
            		message.getDeptId(), message.getDeptName()));
        }
        commonService.update(medicalVisit);
        // [BUG]0040006 ADD END
        TransferHistory transferHistory = getTransferHistory(message);
        

        // $Author:li_shenggen
        // $Date:2014/08/13 10:09
        // $[BUG]0047377 ADD BEGIN
        if (Constants.NEW_MESSAGE_FLAG.equals(message.getTriggerEvent()))
        {
            // 增加入/出科信息
            commonService.save(transferHistory);
        }
        else if (Constants.UPDATE_MESSAGE_FLAG.equals(message.getTriggerEvent()))
        {
            // 更新入/出科信息
            commonService.update(transferHistory);
        }
        // $[BUG]0047377 MODIFY BEGIN
    }
    /**
     * 获取入科消息关联的就诊记录
     * @param PRPAIN400001UV03
     * @return 就诊记录
     */
    public MedicalVisit getMedicalVisit(PRPAIN400001UV03 prpain400001uv03)
    {
        // 患者域ID
        String patientDomain = prpain400001uv03.getPatientDomain();

        // 就诊次数
        String visitTimes = prpain400001uv03.getVisitTimes();

        // 根据域ID,患者本地ID，就诊次数获取就诊记录
        MedicalVisit medicalVisit = commonService.mediVisit(patientDomain,
                patientLid, visitTimes,prpain400001uv03.getOrgCode(),
                prpain400001uv03.getVisitOrdNo(),prpain400001uv03.getVisitTypeCode());

        return medicalVisit;
    }
    /**
     * 获取消息中的入科出科消息
     * @param message
     * @return 入科出科
     */
    private TransferHistory getTransferHistory(PRPAIN400001UV03 message)
    {
        // $Author:li_shenggen
        // $Date:2014/08/13 10:09
        // $[BUG]0047377 ADD BEGIN
        TransferHistory transferHistory = null;
        if (Constants.NEW_MESSAGE_FLAG.equals(message.getTriggerEvent()))
        {
            transferHistory = new TransferHistory();
            // 域代码
            transferHistory.setPatientDomain(message.getPatientDomain());
            // 患者本地ID
            transferHistory.setPatientLid(patientLid);
            // 就诊内部序列号
            transferHistory.setVisitSn(medicalVisit.getVisitSn());
            // 患者内部序列号
            transferHistory.setPatientSn(medicalVisit.getPatientSn());
            // 更新回数
            transferHistory.setUpdateCount(Constants.INSERT_UPDATE_COUNT);
            // 创建时间
            transferHistory.setCreateTime(systemTime);
            // 删除标志
            transferHistory.setDeleteFlag(Constants.NO_DELETE_FLAG);
            // 创建者
            transferHistory.setCreateby(DataMigrationUtils.getDataMigration() + serviceId);
            
        }
        else if (Constants.UPDATE_MESSAGE_FLAG.equals(message.getTriggerEvent()))
        {
            transferHistory = transferHistoryToUpdate;
            
            // 修改者
            transferHistory.setUpdateby(serviceId);
        }
     // $[BUG]0047377 ADD END

        // 执行时间
        transferHistory.setExecTime(DateUtils.stringToDate(message.getExecTime()));
        // 更新时间
        transferHistory.setUpdateTime(systemTime);

        // $Author:tong_meng
        // $Date:2013/12/03 11:00
        // [BUG]0040270 ADD BEGIN
        // 医疗机构代码
        transferHistory.setOrgCode(message.getOrgCode());
        // 医疗机构名称
        transferHistory.setOrgName(message.getOrgName());
        // [BUG]0040270 ADD BEGIN       
        
        // 消息为入科消息
       // if (isTransferIn(message))
       // {
            // 转入床号
            transferHistory.setToBed(message.getBedNo());

            // $Author :tong_meng
            // $Date : 2012/6/25 14:33$
            // [BUG]0007418 MODIFY BEGIN
            // 转入科室ID
            transferHistory.setToDeptId(message.getDeptId());
            // [BUG]0007418 MODIFY END

            // $Author :tong_meng
            // $Date : 2012/6/25 14:33$
            // [BUG]0007418 ADD BEGIN
            // 转入科室名称
//            transferHistory.setToDeptName(message.getDeptName());
            transferHistory.setToDeptName(DictionaryUtils.getNameFromDictionary(
            		Constants.DOMAIN_DEPARTMENT, message.getDeptId(), message.getDeptName()));
            // [BUG]0007418 ADD END

            // $Author :tong_meng
            // $Date : 2012/6/25 14:33$
            // [BUG]0007418 MODIFY BEGIN
            // 设置转入病区
            transferHistory.setToWardId(message.getWardsId());
            // [BUG]0007418 MODIFY END

            // $Author :tong_meng
            // $Date : 2012/6/25 14:33$
            // [BUG]0007418 ADD BEGIN
            // 转入病区名称
//            transferHistory.setToWardName(message.getWardsName());
            transferHistory.setToWardName(DictionaryUtils.getNameFromDictionary(
            		Constants.DOMAIN_WARD, message.getWardsId(), message.getWardsName()));
            // [BUG]0007418 ADD END

            // 转入执行人ID
            transferHistory.setExecPersonId(message.getInExecPersonId());
            // 转入执行人姓名
//            transferHistory.setExecPersonName(message.getInExecPersonName());
            transferHistory.setExecPersonName(DictionaryUtils.getNameFromDictionary(
            		Constants.DOMAIN_STAFF, message.getInExecPersonId(), message.getInExecPersonName()));

            // $Author:wei_peng
            // $Date:2012/10/16 10:17
            // [BUG]0010334 ADD BEGIN
            // 操作类型标识
            transferHistory.setOrderTypeCode(Constants.TRANSFER_HISTORY_TRANS_IN);
            // 操作类型名称
            transferHistory.setOrderTypeName("入科");
            // [BUG]0010334 ADD BEGIN
       // }
 /*       // 消息为出科消息
        else if (isTransferOut(message))
        {
            // 转出床号
            transferHistory.setFromBed(message.getBedNo());

            // $Author :tong_meng
            // $Date : 2012/6/25 14:33$
            // [BUG]0007418 MODIFY BEGIN
            // 转出科室编号
            transferHistory.setFromDeptId(message.getDeptId());
            // [BUG]0007418 MODIFY END

            // $Author :tong_meng
            // $Date : 2012/6/25 14:33$
            // [BUG]0007418 ADD BEGIN
            // 转出科室名称
            transferHistory.setFromDeptName(message.getDeptName());
            // [BUG]0007418 ADD END

            // $Author :tong_meng
            // $Date : 2012/6/25 14:33$
            // [BUG]0007418 MODIFY BEGIN
            // 转出病区编码
            transferHistory.setFromWardId(message.getWardsId());
            // [BUG]0007418 MODIFY END

            // $Author :tong_meng
            // $Date : 2012/6/25 14:33$
            // [BUG]0007418 ADD BEGIN
            // 转出病区名称
            transferHistory.setFromWardName(message.getWardsName());
            // [BUG]0007418 ADD END

            // 转出执行人ID
            transferHistory.setExecPersonId(message.getOutExecPersonId());
            // 转出执行人姓名
            transferHistory.setExecPersonName(message.getOutExecPersonName());

            // $Author :tong_meng
            // $Date : 2012/6/25 14:33$
            // [BUG]0007418 MODIFY BEGIN
            // 出科去向编码
            transferHistory.setDestinationCode(message.getDestinationCode());
            // [BUG]0007418 MODIFY BEGIN

            // $Author :tong_meng
            // $Date : 2012/6/25 14:33$
            // [BUG]0007418 ADD BEGIN
            // 出科去向名称
            transferHistory.setDestinationName(message.getDestinationName());
            // [BUG]0007418 ADD END

            // $Author:wei_peng
            // $Date:2012/10/16 10:17
            // [BUG]0010334 ADD BEGIN
            // 操作类型标识
            transferHistory.setOrderTypeCode(Constants.TRANSFER_HISTORY_TRANS_OUT);
            // 操作类型名称
            transferHistory.setOrderTypeName("出科");
            // [BUG]0010334 ADD BEGIN
        }*/
        return transferHistory;
    }
    // $Author:wei_peng
    // $Date:2012/10/15 18:09
    // $[BUG]0010334 ADD BEGIN
    /**
     * 查找就诊对应的入/出科记录
     * @param PRPAIN400001UV03
     * @return 入/出科记录
     */
    private List<Object> getTransferHistorys(PRPAIN400001UV03 prpain400001uv03)
    {
        Map<String, Object> condition = new HashMap<String, Object>();
        condition.put("visitSn", medicalVisit.getVisitSn());
        condition.put("patientLid", medicalVisit.getPatientLid());
        condition.put("patientDomain", medicalVisit.getPatientDomain());
        condition.put("execTime", DateUtils.stringToDate(prpain400001uv03.getExecTime()));
        condition.put("deleteFlag", Constants.NO_DELETE_FLAG);
        condition.put("orgCode",prpain400001uv03.getOrgCode());
        // 入科消息
        //if (isTransferIn(prpain400001uv03))
        //{
            // 查询条件添加入科标识
            condition.put("orderTypeCode",
                    Constants.TRANSFER_HISTORY_TRANS_IN);
        //}
        // 出科消息
/*        else if (isTransferOut(prpain400001uv03))
        {
            // 查询条件添加出科标识
            condition.put("orderTypeCode",
                    Constants.TRANSFER_HISTORY_TRANS_OUT);
        }*/
        return commonService.selectByCondition(TransferHistory.class, condition);
    }
    // $[BUG]0010334 ADD END

    /**
     * 判断消息是否为入科信息
     * @param message
     * @return 是入科信息则返回true，否则返回false
     */
    private boolean isTransferIn(PRPAIN400001UV03 message)
    {
        return message.getTransferFlag().equals(Constants.TRANSFER_IN_FLAG);
    }

    /**
     * 判断消息是否为出科信息
     * @param message
     * @return 是出科信息则返回true，否则返回false
     */
    private boolean isTransferOut(PRPAIN400001UV03 message)
    {
        return message.getTransferFlag().equals(Constants.TRANSFER_OUT_FLAG);
    }

}
