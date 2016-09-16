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
import com.founder.cdr.entity.ExamAppointment;
import com.founder.cdr.entity.ExaminationOrder;
import com.founder.cdr.entity.NursingOperation;
import com.founder.cdr.hl7.dto.prscin010101uv01.PRSCIN010101UV01;
import com.founder.cdr.service.CommonService;
import com.founder.cdr.util.DateUtils;
import com.founder.cdr.util.StringUtils;

@Component(value = "prscin010101uv01Writer")
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class PRSCIN010101UV01Writer implements BusinessWriter<PRSCIN010101UV01>
{
    private static final Logger logger = LoggerFactory.getLogger(PRSCIN010101UV01Writer.class);

    private static final Logger loggerBS003 = LoggerFactory.getLogger("BS003");
    
    //private static final Logger loggerTS003 = LoggerFactory.getLogger("TS003");
    
    //private Logger loggerCheck;
    
    //private static final String SERVICEE_ID_BS003 = "BS003";
    
    //private static final String SERVICEE_ID_TS003 = "TS003";

    @Autowired
    private CommonService commonService;

    // 要预约的检查申请单或者医嘱
    private ExaminationOrder examOrder = new ExaminationOrder();

    // 预约消息
    private ExamAppointment appointment = new ExamAppointment();

    // 消息id
    private String serviceId;
    
    public PRSCIN010101UV01Writer()
    {
        /*if(SERVICEE_ID_BS003.equals(serviceId))
        {
            loggerCheck = loggerBS003;
        }
        else if(SERVICEE_ID_TS003.equals(serviceId))
        {
            loggerCheck = loggerTS003;
        }*/
    }

    @Override
    public boolean validate(PRSCIN010101UV01 t)
    {
        serviceId = t.getMessage().getServiceId();
        
        if (!isNewMsg(t) && !isUpdateMsg(t) && !isCancelMsg(t))
        {
            logger.error("消息类型不正确！消息类型：{}", t.getNewUpFlag());

            loggerBS003.error("Message:{}, validate:{}", t.toString(),
                    t.getNewUpFlag());

            return false;
        }
        // $Author :chang_xuewen
        // $Date : 2014/1/22 10:30$
        // [BUG]0042086 MODIFY BEGIN
        /*if(!t.getMessage().getOrgCode().equals(t.getOrgCode()))
        {
            logger.error("消息头机构与消息体机构不一致！消息头机构：{},消息体机构：{}", t.getMessage().getOrgCode(),t.getOrgCode());

            loggerBS003.error("Message:{}, 消息头机构与消息体机构不一致！", t.toString());

            return false;
        }*/
        if(StringUtils.isEmpty(t.getOrgCode())){
        	// $Author :yang_mingjie
        	// $Date : 2014/06/26 10:09$
        	// [BUG]0045630 MODIFY BEGIN 
        	t.setOrgCode(Constants.HOSPITAL_CODE);
        	t.setOrgName(Constants.HOSPITAL_NAME);
        	//[BUG]0045630 MODIFY END
        }        
        // [BUG]0042086 MODIFY END       
        /*if(SERVICEE_ID_TS003.equals(serviceId))
        {
            if (StringUtils.isEmpty(t.getAppOrderNo()))
            {
                logger.error("预约排序号不能为空！");

                loggerCheck.error("Message:{}, validate:{}", t.toString(),
                        "非空字段验证未通过！预约排序号：appOrderNo = " + t.getAppOrderNo());

                return false;
            }
        }*/

        if (isOutpatient(t))
        {
            if (StringUtils.isEmpty(t.getRequestNo()))
            {
                logger.error("HIS门诊时，预约申请单号不能为空！");

                loggerBS003.error("Message:{}, validate:{}", t.toString(),
                        "非空字段验证未通过！申请单号：requestNo = " + t.getRequestNo());

                return false;
            }
        }
        else if (isInpatient(t))
        {
            if (StringUtils.isEmpty(t.getOrderLid()))
            {
                logger.error("HIS住院时，医嘱号不能为空！");

                loggerBS003.error("Message:{}, validate:{}", t.toString(),
                        "非空字段验证未通过！医嘱号：requestNo = " + t.getOrderLid());

                return false;
            }
        }
        else
        {
            logger.error("HIS域代码错误，patientDomain={}", t.getPatientDomain());

            loggerBS003.error(
                    "Message:{}, validate:{}",
                    t.toString(),
                    "非空字段验证未通过！HIS域代码错误，patientDomain = "
                        + t.getPatientDomain());

            return false;
        }
        return true;
    }

    @Override
    public boolean checkDependency(PRSCIN010101UV01 t, boolean flag)
    {
        logger.debug(t.getNewUpFlag() + "消息...");

        List<Object> examOrderList = getExaminationOrders(t);

        // 新消息，检查医嘱是否存在
        if (examOrderList != null && !examOrderList.isEmpty())
        {
            examOrder = (ExaminationOrder) examOrderList.get(0);

            logger.debug("已找到检查申请单或者医嘱：申请单号：{}，医嘱号：{}",
                    examOrder.getRequestNo(), examOrder.getOrderLid());
        }

        // $Author:tong_meng
        // $Date:2013/8/06 11:00
        // [BUG]0035811 MODIFY BEGIN
        // $Note:修改if else 消息类型判断
        else
        {
            logger.error("关联的检查医嘱表找不到要预约的检查！");

            if (flag)
            {
                loggerBS003.error("Message:{},checkDependency:{}",
                        t.toString(),
                        "关联的检查医嘱表找不到要预约的检查！医嘱号：" + t.getOrderLid() + "申请单号："
                            + t.getRequestNo());
            }

            return false;
        }
        if (isUpdateMsg(t) || isCancelMsg(t))
        {
            // 更新时，检查预约是否存在
            List<Object> appointmentList = getAppointments(t);
            if (appointmentList != null && !appointmentList.isEmpty())
            {
                appointment = (ExamAppointment) appointmentList.get(0);

                logger.debug("已找到预约消息：预约号：{}，患者本地遗嘱号：{}",
                        appointment.getAppointmentNo(),
                        appointment.getPatientLid());
            }
            else
            {
                logger.error("找不到要更新或者要取消的预约数据！");

                if (flag)
                {
                    loggerBS003.error("Message:{},checkDependency:{}",
                            t.toString(),
                            "找不到要更新或者要取消的预约数据！检查预约号：" + t.getAppointmentNo());
                }

                return false;
            }
        }
        // [BUG]0035811 MODIFY END
        return true;
    }

    @Transactional
    @Override
    public void saveOrUpdate(PRSCIN010101UV01 t)
    {
        Date systemTime = DateUtils.getSystemTime();

        // 检查预约实体
        setAppointment(t, appointment, systemTime);

        // 护理操作表增加一条检查预约的记录
        NursingOperation nursingOperation = null;

        if (isNewMsg(t) || isCancelMsg(t))
        {
            // 更新检查医嘱的医嘱执行状态
            updateExamOrder(t, systemTime);

            nursingOperation = getNursOperation(t, appointment, systemTime);

            if (isNewMsg(t))
            {
                commonService.save(appointment);
            }
            else
            {
                commonService.update(appointment);
            }

            commonService.save(nursingOperation);

            commonService.update(examOrder);
        }
        else
        {
            commonService.update(appointment);
        }
    }

    /**
     * 保存appointment实体，更新检查医嘱状态
     * @param t
     * @param appointment 
     * @param systemTime 
     */
    private void setAppointment(PRSCIN010101UV01 t,
            ExamAppointment appointment, Date systemTime)
    {
        if (isNewMsg(t))
        {
            // 就诊内部序列号
            appointment.setVisitSn(examOrder.getVisitSn());

            // 患者内部序列号
            appointment.setPatientSn(examOrder.getPatientSn());

            // 医嘱内部序列号
            appointment.setOrderSn(examOrder.getOrderSn());

            // 域ID
            appointment.setPatientDomain(t.getPatientDomain());

            // 患者本地ID
            appointment.setPatientLid(t.getPatientLid());
            
            // 医疗机构编码
            appointment.setOrgCode(t.getOrgCode());
            
            // 医疗机构名称
            appointment.setOrgName(t.getOrgName());

            // 创建时间
            appointment.setCreateTime(systemTime);

            // 更新回数
            appointment.setUpdateCount(Constants.INSERT_UPDATE_COUNT);

            // 删除标志
            appointment.setDeleteFlag(Constants.NO_DELETE_FLAG);

            // 创建者
            appointment.setCreateby(serviceId);
        }
        else
        {
            if (!isCancelMsg(t))
            {
                // 修改者
                appointment.setUpdateby(serviceId);
            }
        }

        // 更新时间
        appointment.setUpdateTime(systemTime);

        if (isCancelMsg(t))
        {
            appointment.setDeleteFlag(Constants.DELETE_FLAG);

            // $Author:tong_meng
            // $Date:2013/7/28 17:30
            // [BUG]0035375 MODIFY BEGIN
            appointment.setDeleteTime(systemTime);
            // [BUG]0035375 MODIFY END

            // 删除者
            appointment.setDeleteby(serviceId);

            return;
        }

        // 预约号
        appointment.setAppointmentNo(t.getAppointmentNo());

        // 预约时间
        appointment.setAppointmentTime(DateUtils.stringToDate(t.getAppointmentTime()));

        // 预约设备编码
        appointment.setAppDeviceCode(t.getAppDeviceCode());

        // 预约设备名称
        appointment.setAppDeviceName(t.getAppDeviceName());

        // 预约人员ID
        appointment.setAppPerformerCode(t.getAppPerformerCode());

        // 预约人员名称
        appointment.setAppPerformerName(t.getAppPerformerName());

        // $Author:jin_peng
        // $Date:2013/10/16 13:05
        // [BUG]0038134 ADD BEGIN
        // 预约排序号
        appointment.setAppOrderNo(t.getAppOrderNo());

        // [BUG]0038134 ADD END

        // 执行科室编码
        appointment.setExecDeptCode(t.getExecDeptCode());

        // 执行科室名称
        appointment.setExecDeptName(t.getExecDeptName());
        
    }

    /**
     * 更新检查医嘱状态
     * @param t
     */
    private void updateExamOrder(PRSCIN010101UV01 t, Date systemTime)
    {
        // 医嘱执行状态
        String orderStatus = null;

        // 医嘱执行状态名称
        String orderStatusName = null;

        if (isNewMsg(t))
        {
            orderStatus = Constants.ORDER_STATUS_APPOINTMENT;

            orderStatusName = DictionaryCache.getDictionary(
                    Constants.DOMAIN_ORDER_STATUS).get(
                    Constants.ORDER_STATUS_APPOINTMENT);
        }
        else if (isCancelMsg(t))
        {
            orderStatus = Constants.ORDER_STATUS_CANCEL_APPOINTMENT;

            orderStatusName = DictionaryCache.getDictionary(
                    Constants.DOMAIN_ORDER_STATUS).get(
                    Constants.ORDER_STATUS_CANCEL_APPOINTMENT);
        }

        examOrder.setOrderStatus(orderStatus);

        examOrder.setOrderStatusName(orderStatusName);

        examOrder.setUpdateTime(systemTime);

        examOrder.setUpdateby(serviceId);

    }

    /**
     * 护理操作表
     * @param t 
     * @param systemTime 
     * @param appointment2
     * @return 
     */
    private NursingOperation getNursOperation(PRSCIN010101UV01 t,
            ExamAppointment appointment, Date systemTime)
    {
        NursingOperation nursingOperation = new NursingOperation();

        // 护理内部序列号采番
        BigDecimal seq = commonService.getSequence("SEQ_NURSING_OPERATION");

        // 主键
        nursingOperation.setOperationSn(seq);

        // 就诊内部序列号
        nursingOperation.setVisitSn(appointment.getVisitSn());

        // 患者SN
        nursingOperation.setPatientSn(appointment.getPatientSn());

        // 患者Lid
        nursingOperation.setPatientLid(appointment.getPatientLid());

        // 患者域ID
        nursingOperation.setPatientDomain(appointment.getPatientDomain());

        // 医嘱内部序列号
        nursingOperation.setOrderSn(appointment.getOrderSn());
        
        // 医疗机构编码
        nursingOperation.setOrgCode(t.getOrgCode());
       
        // 医疗机构名称
        nursingOperation.setOrgName(t.getOrgName());

        // 医嘱类型
        nursingOperation.setOrderType(Constants.EXAMINATION_ORDER);

        // 医嘱类型名称
        nursingOperation.setOrderTypeName(DictionaryCache.getDictionary(
                Constants.DOMAIN_ORDER_TYPE).get(Constants.EXAMINATION_ORDER));

        // $Author:tong_meng
        // $Date:2013/8/6 16:30
        // [BUG]0035813 MODIFY BEGIN
        // 执行人
        nursingOperation.setOperatorId(t.getAppPerformerCode());

        // 执行人名称
        nursingOperation.setOperatorName(t.getAppPerformerName());

        // 执行时间
        nursingOperation.setOperationTime(systemTime);
        // [BUG]0035813 MODIFY END

        if (isNewMsg(t))
        {
            // 医嘱状态
            nursingOperation.setOrderStatusCode(Constants.ORDER_STATUS_APPOINTMENT);

            // 医嘱类型名称
            nursingOperation.setOrderStatusName(DictionaryCache.getDictionary(
                    Constants.DOMAIN_ORDER_STATUS).get(
                    Constants.ORDER_STATUS_APPOINTMENT));
        }
        else if (isCancelMsg(t))
        {
            // 医嘱状态
            nursingOperation.setOrderStatusCode(Constants.ORDER_STATUS_CANCEL_APPOINTMENT);

            // 医嘱状态名称
            nursingOperation.setOrderStatusName(DictionaryCache.getDictionary(
                    Constants.DOMAIN_ORDER_STATUS).get(
                    Constants.ORDER_STATUS_CANCEL_APPOINTMENT));
        }

        // 更新时间
        nursingOperation.setUpdateTime(systemTime);

        // 创建者
        nursingOperation.setCreateby(serviceId);
        
        if (isNewMsg(t) || isCancelMsg(t))
        {
            // 创建时间
            nursingOperation.setCreateTime(systemTime);

            // 更新回数
            nursingOperation.setUpdateCount(Constants.INSERT_UPDATE_COUNT);

            // 删除标志
            nursingOperation.setDeleteFlag(Constants.NO_DELETE_FLAG);

        }
        else
        {
            nursingOperation.setDeleteFlag(Constants.DELETE_FLAG);
        }

        return nursingOperation;
    }

    /**
     * 得到预约消息
     * @param t
     * @param examinationOrder 
     * @return
     */
    private List<Object> getAppointments(PRSCIN010101UV01 t)
    {
        Map<String, Object> condition = new HashMap<String, Object>();
        condition.put("orgCode", t.getOrgCode());
        condition.put("deleteFlag", Constants.NO_DELETE_FLAG);

        // $Author:tong_meng
        // $Date:2013/7/25 16:30
        // [BUG]0035329 MODIFY BEGIN
        condition.put("orderSn", examOrder.getOrderSn());
        // [BUG]0035329 MODIFY END

        List<Object> result = new ArrayList<Object>();
        result = commonService.selectByCondition(ExamAppointment.class,
                condition);

        return result;
    }

    /**
     * 得到要预约的检查申请单或者检查医嘱
     * @param t
     * @return
     */
    private List<Object> getExaminationOrders(PRSCIN010101UV01 t)
    {
        Map<String, Object> condition = new HashMap<String, Object>();

        condition.put("deleteFlag", Constants.NO_DELETE_FLAG);
        condition.put("orgCode", t.getOrgCode());
        
        if (isOutpatient(t))
        {
            condition.put("requestNo", t.getRequestNo());
            logger.debug("HIS门急诊时，申请单号：{}", t.getRequestNo());
        }
        else if (isInpatient(t))
        {
            condition.put("orderLid", t.getOrderLid());
            logger.debug("HIS住院时：{}", t.getOrderLid());
        }
        
        List<Object> result = new ArrayList<Object>();
        result = commonService.selectByCondition(ExaminationOrder.class,
                condition);

        return result;
    }

    /**
     * his门诊
     * @param t
     * @return
     */
    private boolean isOutpatient(PRSCIN010101UV01 t)
    {
        return Constants.PATIENT_DOMAIN_OUTPATIENT.equals(t.getPatientDomain());
    }

    /**
     * his住院
     * @param t
     * @return
     */
    private boolean isInpatient(PRSCIN010101UV01 t)
    {
        return Constants.PATIENT_DOMAIN_INPATIENT.equals(t.getPatientDomain());
    }

    /**
     * 新消息
     * @param t
     * @return
     */
    private boolean isNewMsg(PRSCIN010101UV01 t)
    {
        return Constants.NEW_MESSAGE_FLAG.equals(t.getNewUpFlag());
    }

    /**
     * 更新消息
     * @param t
     * @return
     */
    private boolean isUpdateMsg(PRSCIN010101UV01 t)
    {
        return Constants.UPDATE_MESSAGE_FLAG.equals(t.getNewUpFlag());
    }

    /**
     * 取消消息
     * @param t
     * @return
     */
    private boolean isCancelMsg(PRSCIN010101UV01 t)
    {
        return Constants.CANCEL_MESSAGE_FLAG.equals(t.getNewUpFlag());
    }

}
