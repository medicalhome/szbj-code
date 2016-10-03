/**
 * Copryright
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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.yly.cdr.cache.DictionaryCache;
import com.yly.cdr.core.Constants;
import com.yly.cdr.entity.BloodRequest;
import com.yly.cdr.entity.CareOrder;
import com.yly.cdr.entity.ConsultationOrder;
import com.yly.cdr.entity.ExaminationOrder;
import com.yly.cdr.entity.GeneralOrder;
import com.yly.cdr.entity.HerbalFormula;
import com.yly.cdr.entity.LabOrder;
import com.yly.cdr.entity.MedicalVisit;
import com.yly.cdr.entity.MedicationOrder;
import com.yly.cdr.entity.NursingOperation;
import com.yly.cdr.entity.Prescription;
import com.yly.cdr.entity.ProcedureOrder;
import com.yly.cdr.entity.TreatmentOrder;
import com.yly.cdr.hl7.dto.Orders;
import com.yly.cdr.hl7.dto.poorin200901uv09.POORIN200901UV09;
import com.yly.cdr.service.CommonService;
import com.yly.cdr.service.PrescriptionService;
import com.yly.cdr.util.DataMigrationUtils;
import com.yly.cdr.util.DateUtils;
import com.yly.cdr.util.DictionaryUtils;
import com.yly.cdr.util.ObjectUtils;
import com.yly.cdr.util.StringUtils;

/**
 * 医嘱撤销或停止数据接入writer
 * @author jin_peng
 */
@Component(value = "poorin200901uv09Writer")
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class POORIN200901UV09Writer implements BusinessWriter<POORIN200901UV09>
{
    private static final Logger logger = LoggerFactory.getLogger(POORIN200901UV09Writer.class);

    private static final Logger loggerBS005 = LoggerFactory.getLogger("BS005");

    // 医嘱撤销时操作人属性名
    private static final String CANCEL_PERSON = "cancelPerson";

    // 医嘱撤销时操作人属性名
    private static final String CANCEL_PERSON_ID = "cancelPersonId";

    // 医嘱撤销时操作人姓名属性名
    private static final String CANCEL_PERSON_NAME = "cancelPersonName";

    // 医嘱撤销时操作时间属性名
    private static final String CANCEL_TIME = "cancelTime";

    // 医嘱停止时操作人属性名
    private static final String STOP_PERSON = "stopPerson";

    // 医嘱停止时操作人属性名
    private static final String STOP_PERSON_ID = "stopPersonId";

    // 医嘱停止时操作人姓名属性名
    private static final String STOP_PERSON_NAME = "stopPersonName";

    // 医嘱停止时操作时间属性名
    private static final String STOP_TIME = "stopTime";

    // 医嘱状态
    private static final String ORDER_STATUS = "orderStatus";

    // 医嘱状态名称
    private static final String ORDER_STATUS_NAME = "orderStatusName";

    // 其它医嘱与会诊医嘱的医嘱状态
    private static final String ORDER_STATUS_CODE = "orderStatusCode";

    // 费用状态
    private static final String CHARGE_STATUS_CODE = "chargeStatusCode";
    
    // 费用状态名称
    private static final String CHARGE_STATUS_NAME = "chargeStatusName";
    
    // 设置医嘱撤销或停止操作时的操作人字段名
    private String cancelOrStopPerson;

    // 设置医嘱撤销或停止操作时的操作人姓名字段名
    private String cancelOrStopPersonName;

    // 设置医嘱撤销或停止操作时的操作时间字段名
    private String cancelOrStopTime;

    // 医嘱状态
    private String orderStatus;

    // 医嘱状态名称
    private String orderStatusName;

    // 设置医嘱撤销或停止操作时的医嘱状态值
    private String cancelOrStopOrderStatusValue;

    // 设置医嘱撤销或停止操作时的医嘱状态名称
    private String cancelOrStopOrderStatusName;

    // 当消息中的撤销或停止医嘱存在时装入此集合以便进行医嘱撤销或停止操作
    private List<Object> resWaitCancelOrStopOrderList;

    // 消息中的待撤销或停止医嘱信息集合
    private List<Orders> ordersList;

    // 药物医嘱（中草药）撤销时，需删除的关联草药配方集合
    private List<Object> herbalFormulas;

    @Autowired
    private CommonService commonService;
    
    @Autowired
    private PrescriptionService prescriptionService;

    // 护理操作.护理内部序列号sequences
    private static final String SEQ_NURSING_OPERATION = "SEQ_NURSING_OPERATION";

    // 时间
    private Date systemTime = DateUtils.getSystemTime();

    // 消息id
    private String serviceId;
    
    // 关联就诊记录
    private MedicalVisit medicalVisit;

    // $Author :jia_yanqing
    // $Date : 2012/7/17 15:30$
    // [BUG]0008042 MODIFY BEGIN
    /**
     * 医嘱撤销或停止消息字段非空校验
     */
    @Override
    public boolean validate(POORIN200901UV09 poorin200901uv09)
    {
        ordersList = poorin200901uv09.getOrder();
        // $Author :chang_xuewen
        // $Date : 2014/1/22 10:30$
        // [BUG]0042086 MODIFY BEGIN   
        // $Author :chang_xuewen
        // $Date : 2013/12/05 11:00$
        // [BUG]0040271 ADD BEGIN
        /*if(!poorin200901uv09.getOrgCode().equals(poorin200901uv09.getMessage().getOrgCode())){
        	loggerBS005.error(
                    "Message:{}, validate:{}",
                    poorin200901uv09.toString(),
                    "消息头与消息体中的医疗机构代码不一致：头：OrgCode = " 
                    + poorin200901uv09.getMessage().getOrgCode() 
                    + " ，体：OrgCode ="
                    + poorin200901uv09.getOrgCode());
        	return false;
        }*/
        if(StringUtils.isEmpty(poorin200901uv09.getOrgCode())){
        	// $Author :yang_mingjie
        	// $Date : 2014/06/26 10:09$
        	// [BUG]0045630 MODIFY BEGIN 
        	poorin200901uv09.setOrgCode(Constants.HOSPITAL_CODE);
        	poorin200901uv09.setOrgName(Constants.HOSPITAL_NAME);
        	//[BUG]0045630 MODIFY END
        }
        // [BUG]0040271 ADD END
        // [BUG]0042086 MODIFY E   
        if (ordersList != null && !ordersList.isEmpty())
        {
            for (Orders orders : ordersList)
            {
                // $Author :jia_yanqing
                // $Date : 2012/9/5 17:30$
                // [BUG]0008782 ADD BEGIN
                // 验证医嘱项中撤消或停止时间，撤消或停止人编码，撤消或停止人名称为非空
            	/*
            	 * Author: yu_yzh
            	 * Date: 2015/7/28 10:53
            	 * ORG^O20消息中不包含取消信息，暂时取消验证
            	 * */
                if (!StringUtils.isNotNullAll(/*orders.getCancelOrStopTime(),
                        orders.getCancelOrStopPerson(),
                        orders.getCancelOrStopPersonName(),*/
                        poorin200901uv09.getOrgCode()))
                {
                    loggerBS005.error(
                            "Message:{}, validate:{}",
                            poorin200901uv09.toString(),
                            "非空字段验证未通过！撤消或停止时间：CancelOrStopTime = "
                                + orders.getCancelOrStopTime()
                                + ";撤消或停止人编码：CancelOrStopPerson = "
                                + orders.getCancelOrStopPerson()
                                + ";撤消或停止人名称：CancelOrStopPersonName = "
                                + orders.getCancelOrStopPersonName()
                                + ";医疗机构代码：OrgCode = "
                                + poorin200901uv09.getOrgCode());
                    return false;
                }
                // [BUG]0008782 ADD END

                // $Author:wei_peng
                // $Date:2013/01/15 16:42
                // [BUG]0013375 DELETE BEGIN
                // $Author:wei_peng
                // $Date:2012/9/21 14:22
                // $[BUG]0009776 ADD BEGIN
                /*
                 * if (Constants.LAB_ORDER.equals(orders.getOrderType()) &&
                 * StringUtils.isEmpty(orders.getSampleCode())) {
                 * logger.error("医嘱类型为检验类时，标本号不能为空！"); return false; }
                 */
                // $[BUG]0009776 ADD END
                // [BUG]0013375 DELETE END
                // 根据医嘱类型获取相应医嘱的对象class
                Class<?> entityClass = commonService.getOrderClass(orders.getOrderType());

                // 如果entityClass为null说明没有匹配CDR的医嘱类型
                if (entityClass == null)
                {
                    loggerBS005.error("Message:{}, validate:{}",
                            poorin200901uv09.toString(),
                            "医嘱撤销或停止消息中的医嘱类型没有匹配CDR医嘱类型 : orderType = "
                                + orders.getOrderType());
                    return false;
                }
                // Author :jia_yanqing
                // Date : 2013/3/7 11:30
                // [BUG]0014364 DELETE BEGIN
                // $Author :jia_yanqing
                // $Date : 2012/8/13 18:30$
                // [BUG]0008216 ADD BEGIN
                /**
                if (!GeneralOrder.class.equals(entityClass)
                    && !CareOrder.class.equals(entityClass)
                    && !MedicationOrder.class.equals(entityClass)
                    && !TreatmentOrder.class.equals(entityClass))
                {
                    // $Author :jia_yanqing
                    // $Date : 2012/9/24 16:30$
                    // [BUG]0010018 MODIFY BEGIN
                    if (orders.getMutexesOrderLid() != null
                        && !"".equals(orders.getMutexesOrderLid()))
                    // [BUG]0010018 MODIFY END
                    {
                        logger.error("按照现在逻辑，该医嘱不可以有互斥医嘱");
                        return false;
                    }
                }
                
                // [BUG]0008216 ADD END

                // $Author :jin_peng
                // $Date : 2012/08/28 11:15$
                // [BUG]0009087 ADD BEGIN
                else
                {**/
                // [BUG]0014364 DELETE END
                if (orders.getMutexesOrderType() != null
                    && !"".equals(orders.getMutexesOrderType()))
                {
                    // 根据互斥医嘱类型获取相应医嘱的互斥医嘱对象class
                    Class<?> mutexesEntityClass = commonService.getOrderClass(orders.getMutexesOrderType());

                    if (mutexesEntityClass == null)
                    {
                        loggerBS005.error(
                                "Message:{}, validate:{}",
                                poorin200901uv09.toString(),
                                "医嘱撤销或停止消息中的互斥医嘱类型没有匹配CDR医嘱类型 : "
                                    + " mutexesOrderType = "
                                    + orders.getMutexesOrderType());
                        return false;
                    }
                }
                // }
                // [BUG]0009087 ADD END

//                if (Constants.STOP_MESSAGE_FLAG.equals(poorin200901uv09.getTriggerEvent()))
//                {
//                    // Author :jia_yanqing
//                    // Date : 2012/10/15 15:50
//                    // [BUG]0010426 MODIFY BEGIN
//                    // 会诊医为临时医嘱，没有医嘱停止操作
//                    if (ConsultationOrder.class.equals(entityClass))
//                    {
//                        loggerBS005.error("Message:{}, validate:{}",
//                                poorin200901uv09.toString(),
//                                "该医嘱没有医嘱停止操作：entityClass = " + entityClass);
//                        return false;
//                    }
//                }
                // [BUG]0010426 MODIFY END
            }
        }
        return true;
    }

    // [BUG]0008042 MODIFY END

    /**
     * 有关联时进行业务校验 根据医嘱号，域代码，患者本地ID判断相应医嘱是否存在并且校验消息中的医嘱类型是否在CDR字典中存在
     */
    @Override
    public boolean checkDependency(POORIN200901UV09 poorin200901uv09,
            boolean flag)
    {
        // 需要撤销或停止的医嘱对象集合
        resWaitCancelOrStopOrderList = new ArrayList<Object>();
        List<Object> orderList = null;
        List<Object> mutexesOrderList = null;
        Class<?> entityClass = null;
        Class<?> mutexesEntityClass = null;
        String patientLid = null;
        String patientDomain = null;
        String visitOrdNo = null;
        String visitType = null;
        String visitTimes = null;
        String OrgCode = null;
        BigDecimal visitSn = null;

        // $Author :jia_yanqing
        // $Date : 2012/7/17 15:30$
        // [BUG]0008042 DELETE BEGIN
        // 获取医嘱撤销或停止消息中的所有医嘱信息
        // ordersList = poorin200901uv09.getOrder();
        // [BUG]0008042 DELETE END

        // 当消息中的医嘱不为空时验证相应医嘱是否存在,如果存在做相应赋值操作
        if (ordersList != null && !ordersList.isEmpty())
        {
            // $Author :tongmeng
            // $Date : 2012/5/29 14:30$
            // [BUG]0006657 MODIFY BEGIN
            // 获得患者本地ID
            patientLid = poorin200901uv09.getPatientLid();
            // [BUG]0006657 MODIFY END

            // 获得患者域ID
            patientDomain = poorin200901uv09.getPatientDomain();
            // 就诊流水号
            visitOrdNo = poorin200901uv09.getVisitOrdNo();
            // 就诊类型编码
            visitType = poorin200901uv09.getVisitType();
            // 就诊次数
            visitTimes = poorin200901uv09.getVisitTimes();
            OrgCode = poorin200901uv09.getOrgCode();
            
            if(Constants.PACS_SYS_CODES.contains(poorin200901uv09.getSender())){
            	Map<String, Object> condition = new HashMap<String, Object>();
            	condition.put("patientDomain", patientDomain);
            	condition.put("visitOrdNo", visitOrdNo);
            	condition.put("visitTypeCode", visitType);
            	condition.put("deleteFlag", Constants.NO_DELETE_FLAG);
            	condition.put("orgCode", OrgCode);
            	List<Object> re = commonService.selectByCondition(MedicalVisit.class, condition);
            	if(re != null && !re.isEmpty()) medicalVisit = (MedicalVisit) re.get(0);
            } else {
            	// 获取关联就诊记录
            	medicalVisit = commonService.mediVisit(patientDomain, patientLid,
            			visitTimes, OrgCode, visitOrdNo, visitType);
            }
            /*
             * $Author: yu_yzh
             * $Date: 2015/7/28 11:17
             * 根据检查申请单号获取就诊
             * Add Begin
             * */
            if(medicalVisit == null){
            	medicalVisit = commonService.mediVisitByRequestNo(ExaminationOrder.class, patientLid, 
            			poorin200901uv09.getOrder().get(0).getRequestNo());
            }
            /*
             * Add end
             * */
            if(null != medicalVisit){
            	visitSn = medicalVisit.getVisitSn();
            }

            for (Orders orders : ordersList)
            {
                // Author :jin_peng
                // Date : 2013/08/16 16:12
                // [BUG]0036238 MODIFY BEGIN
                /**if (Constants.BLOOD_ORDER.equals(orders.getOrderType())
                    || Constants.BLOOD_TRANS_APPLY_ORDER.equals(orders.getOrderType()))
                {
                    // 不考虑带不带收费
                    entityClass = LabOrder.class;
                    Map<String, Object> map = new HashMap<String, Object>();
                    map.put("orderLid", orders.getOrderLid());
                    map.put("patientDomain", patientDomain);
                    map.put("patientLid", patientLid);
                    orderList = commonService.selectByCondition(entityClass,
                            map);
                    if (orderList == null || orderList.isEmpty())
                    {
                        entityClass = commonService.getOrderClass(orders.getOrderType());
                        orderList = commonService.selectByCondition(
                                entityClass, map);
                    }

                }*/
                // else
                // {
                // 根据医嘱类型获取相应医嘱的对象class
                entityClass = commonService.getOrderClass(orders.getOrderType());      
                
                // $Author :jia_yanqing
                // $Date : 2012/7/17 15:30$
                // [BUG]0008042 DELETE BEGIN
                // 如果entityClass为null说明没有匹配CDR的医嘱类型
                // if (entityClass == null)
                // {
                // logger.error("医嘱撤销或停止消息中的医嘱类型没有匹配CDR医嘱类型 : {}",
                // " orderType = " + orders.getOrderType());
                // return false;
                // }
                // [BUG]0008042 DELETE END

                
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("orderLid", orders.getOrderLid());
                map.put("patientDomain", patientDomain);
                map.put("patientLid", patientLid);
                map.put("visitSn", visitSn);
                // $Author :chang_xuewen
                // $Date : 2013/12/03 11:00$
                // [BUG]0040271 ADD BEGIN
                map.put("orgCode", poorin200901uv09.getOrgCode());
                // [BUG]0040271 ADD END
                // $Author :jin_peng
                // $Date : 2012/12/17 14:54$
                // [BUG]0012540 DELETE BEGIN
                // 针对费用消息已把相应医嘱更改为已退费状态情况处理
                // map.put("deleteFlag", Constants.NO_DELETE_FLAG);
                // [BUG]0012540 DELETE END

                orderList = commonService.selectByCondition(entityClass, map);
                // }

                // [BUG]0036238 MODIFY END

                // 判断相应医嘱是否存在
                if (orderList == null || orderList.isEmpty())
                {
                    logger.error("MessageId:{};需要撤销或停止的医嘱不存在! \n {}",
                            poorin200901uv09.getMessage().getId(), entityClass
                            	+ ": visitSn = " + visitSn
                                + "; orderLid = " + orders.getOrderLid()
                                + "; patientDomain = " + patientDomain
                                + "; patientLid = " + patientLid
                                + "; orgCode = " + poorin200901uv09.getOrgCode());
                    if (flag)
                    {
                        loggerBS005.error("Message:{},checkDependency:{}",
                                poorin200901uv09.toString(),
                                "需要撤销或停止的医嘱不存在!" + entityClass
                                	+ ": visitSn = " + visitSn
                                    + "; orderLid = " + orders.getOrderLid()
                                    + "; patientDomain = " + patientDomain
                                    + "; patientLid = " + patientLid
                                    + "; orgCode = " + poorin200901uv09.getOrgCode());
                    }
                    return false;
                }
                // $Author :jia_yanqing
                // $Date : 2012/7/27 15:30$
                // [BUG]0008216 ADD BEGIN
                else
                {
                    // $Author :jin_peng
                    // $Date : 2012/12/17 14:54$
                    // [BUG]0012540 ADD BEGIN
                    // 针对费用消息已把相应医嘱更改为已退费状态情况处理
                    // 判断是否该医嘱已匹配并正常处理，1为已退费并对该条医嘱不做处理，0正常匹配并正常处理，-1为未找到该医嘱。
                    int inferNormal = -1;

                    for (Object orderObject : orderList)
                    {
                        // 取相应医嘱费用状态和删除标示
                        Map<String, Object> orderMap = ObjectUtils.getObjectPrivateFields(
                                orderObject, "chargeStatusCode", "deleteFlag");

                        // 相应医嘱费用状态
                        String chargeStatusCode = orderMap.get("chargeStatusCode") == null ? null
                                : String.valueOf(orderMap.get("chargeStatusCode"));

                        // 相应医嘱删除标示
                        String deleteFlag = String.valueOf(orderMap.get("deleteFlag"));

                        // 医嘱为退费时，做标记退出循环
                        if (Constants.CHARGE_STATUS_RETURNCHARGE.equals(chargeStatusCode))
                        {
                            inferNormal = 1;

                            break;
                        }
                        // 医嘱为正常时，做标记退出循环
                        else if (Constants.NO_DELETE_FLAG.equals(deleteFlag))
                        {
                            inferNormal = 0;

                            break;
                        }
                    }

                    // 当相应医嘱为退费时则不再对该医嘱进行处理，继续下一条医嘱处理
                    if (inferNormal == 1)
                    {
                        logger.debug(
                                "需要撤销或停止的医嘱为已退费状态，此时不再对该条医嘱进行操作，跳过此医嘱! \n {}",
                                entityClass + ": visitSn = " + visitSn + ": orderLid = "
                                    + orders.getOrderLid());

                        continue;
                    }
                    // 当相应医嘱没有正常情况时则说明需处理的医嘱还没有接入需等待...
                    else if (inferNormal == -1)
                    {
                        logger.error(
                                "MessageId:{};需要撤销或停止的医嘱不存在! \n {}",
                                poorin200901uv09.getMessage().getId(),entityClass
                                	+ ": visitSn = " + visitSn
                                	+ "; orderLid = "
                                    + orders.getOrderLid()
                                    + "; patientDomain = " + patientDomain
                                    + "; patientLid = " + patientLid);
                        if (flag)
                        {
                            loggerBS005.error(
                                    "Message:{},checkDependency:{}",
                                    poorin200901uv09.toString(),
                                    "需要撤销或停止的医嘱不存在!" + entityClass
                                    	+ ": visitSn = " + visitSn
                                        + "; orderLid = "
                                        + orders.getOrderLid()
                                        + "; patientDomain = " + patientDomain
                                        + "; patientLid = " + patientLid);
                        }
                        return false;
                    }

                    if (orders.getMutexesOrderType() != null
                        && !"".equals(orders.getMutexesOrderType()))
                    {
                        // 根据互斥医嘱类型获取相应医嘱的互斥医嘱对象class
                        mutexesEntityClass = commonService.getOrderClass(orders.getMutexesOrderType());
                        // $Author :jia_yanqing
                        // $Date : 2012/8/13 17:30$
                        // [BUG]0008216 DELETE BEGIN
                        // if (GeneralOrder.class.equals(mutexesEntityClass)
                        // || CareOrder.class.equals(mutexesEntityClass)
                        // || MedicationOrder.class.equals(mutexesEntityClass)
                        // || TreatmentOrder.class.equals(mutexesEntityClass))
                        // {
                        // [BUG]0008216 DELETE END
                        Map<String, Object> codition = new HashMap<String, Object>();
                        codition.put("orderLid", orders.getMutexesOrderLid());
                        codition.put("visitSn", visitSn);
                        codition.put("patientDomain", patientDomain);
                        codition.put("patientLid", patientLid);
                        // $Author :chang_xuewen
                        // $Date : 2013/12/03 11:00$
                        // [BUG]0040271 ADD BEGIN
                        codition.put("orgCode", poorin200901uv09.getOrgCode());
                        // [BUG]0040271 ADD END
                        // $Author :jin_peng
                        // $Date : 2012/12/17 14:54$
                        // [BUG]0012540 DELETE BEGIN
                        // 针对费用消息已把相应医嘱更改为已退费状态情况处理
                        // codition.put("deleteFlag", Constants.NO_DELETE_FLAG);

                        // [BUG]0012540 DELETE END

                        mutexesOrderList = commonService.selectByCondition(
                                mutexesEntityClass, codition);

                        if (mutexesOrderList == null
                            || mutexesOrderList.isEmpty())
                        {
                            logger.error(
                                    "MessageId:{};需要撤销或停止医嘱的互斥医嘱不存在! \n {}",
                                    poorin200901uv09.getMessage().getId(),mutexesEntityClass
                                    	+ ": visitSn = " + visitSn
                                    	+ "; orderLid = "
                                        + orders.getMutexesOrderLid()
                                        + "; patientDomain = " + patientDomain
                                        + "; patientLid = " + patientLid
                                        + "; orgCode = " + poorin200901uv09.getOrgCode());
                            if (flag)
                            {
                                loggerBS005.error(
                                        "Message:{},checkDependency:{}",
                                        poorin200901uv09.toString(),
                                        "需要撤销或停止医嘱的互斥医嘱不存在!"
                                            + mutexesEntityClass
                                            + ": visitSn = " + visitSn
                                            + "; orderLid = "
                                            + orders.getMutexesOrderLid()
                                            + "; patientDomain = "
                                            + patientDomain + "; patientLid = "
                                            + patientLid
                                            + "; orgCode = " + poorin200901uv09.getOrgCode());
                            }
                            return false;
                        }
                        else
                        {
                            // 针对费用消息已把相应医嘱更改为已退费状态情况处理
                            // 判断是否该互斥医嘱已匹配并正常处理，1为已退费并对该条医嘱不做处理，0正常匹配并正常处理，-1为未找到该医嘱。
                            int mutexesInferNormal = -1;

                            for (Object mutexesOrderObject : mutexesOrderList)
                            {
                                // 取相应医嘱费用状态和删除标示
                                Map<String, Object> mutexesOrderMap = ObjectUtils.getObjectPrivateFields(
                                        mutexesOrderObject, "chargeStatusCode",
                                        "deleteFlag");

                                // 相应医嘱费用状态
                                String chargeStatusCode = mutexesOrderMap.get("chargeStatusCode") == null ? null
                                        : String.valueOf(mutexesOrderMap.get("chargeStatusCode"));

                                // 相应医嘱删除标示
                                String deleteFlag = String.valueOf(mutexesOrderMap.get("deleteFlag"));

                                // 医嘱为退费时，做标记退出循环
                                if (Constants.CHARGE_STATUS_RETURNCHARGE.equals(chargeStatusCode))
                                {
                                    mutexesInferNormal = 1;

                                    break;
                                }
                                // 医嘱为正常时，做标记退出循环
                                else if (Constants.NO_DELETE_FLAG.equals(deleteFlag))
                                {
                                    mutexesInferNormal = 0;

                                    break;
                                }
                            }

                            // 当相应互斥医嘱为正常对应时则正常处理相应操作
                            if (mutexesInferNormal == 0)
                            {
                                try
                                {
                                    // 取互斥医嘱的医嘱内部序列号
                                    Field orderSn = mutexesOrderList.get(0).getClass().getDeclaredField(
                                            "orderSn");
                                    orderSn.setAccessible(true);
                                    Object obj = orderSn.get(mutexesOrderList.get(0));
                                    Field mutexesOrderSn = orderList.get(0).getClass().getDeclaredField(
                                            "mutexesOrderSn");
                                    mutexesOrderSn.setAccessible(true);
                                    // 将互斥医嘱的医嘱内部序列号赋值
                                    mutexesOrderSn.set(orderList.get(0), obj);

                                    // $Author : wu_jianfeng
                                    // $Date : 2013/3/14 17:30$
                                    // [BUG]0014531 ADD BEGIN

                                    // 得到互斥医嘱的医嘱类型
                                    Field orderTypeField = null;
                                    Class<?> mutexesOrderClass = mutexesOrderList.get(
                                            0).getClass();
                                    if (LabOrder.class.equals(mutexesOrderClass)
                                        || ExaminationOrder.class.equals(mutexesOrderClass)
                                        || MedicationOrder.class.equals(mutexesOrderClass)
                                        || CareOrder.class.equals(mutexesOrderClass)
                                        || ProcedureOrder.class.equals(mutexesOrderClass)
                                        || TreatmentOrder.class.equals(mutexesOrderClass))
                                        orderTypeField = mutexesOrderClass.getDeclaredField("orderType");
                                    else if (GeneralOrder.class.equals(mutexesOrderClass)
                                        || ConsultationOrder.class.equals(mutexesOrderClass))
                                        orderTypeField = mutexesOrderClass.getDeclaredField("orderTypeCode");
                                    if (orderTypeField != null)
                                    {
                                        orderTypeField.setAccessible(true);
                                        Object orderTypeValue = orderTypeField.get(mutexesOrderList.get(0));

                                        // 将互斥医嘱的医嘱类型赋给要撤销或停止的医嘱
                                        Field mutexesOrderNoTypeField = orderList.get(
                                                0).getClass().getDeclaredField(
                                                "mutexesOrderNoType");
                                        mutexesOrderNoTypeField.setAccessible(true);
                                        // 将互斥医嘱的医嘱内部序列号赋值
                                        mutexesOrderNoTypeField.set(
                                                orderList.get(0),
                                                orderTypeValue);
                                    }
                                    // [BUG]0014531 ADD END
                                }
                                catch (IllegalArgumentException e)
                                {
                                    throw new RuntimeException(e);
                                }
                                catch (SecurityException e)
                                {
                                    throw new RuntimeException(e);
                                }
                                catch (IllegalAccessException e)
                                {
                                    throw new RuntimeException(e);
                                }
                                catch (NoSuchFieldException e)
                                {
                                    throw new RuntimeException(e);
                                }
                            }
                            // 当相应医嘱没有正常情况时则说明需处理的互斥医嘱还没有接入需等待...
                            else if (mutexesInferNormal == -1)
                            {
                                logger.error(
                                        "MessageId:{};需要撤销或停止医嘱的互斥医嘱不存在! \n {}",
                                        poorin200901uv09.getMessage().getId(),
                                        mutexesEntityClass + ": visitSn = " + visitSn + "; orderLid = "
                                            + orders.getMutexesOrderLid()
                                            + "; patientDomain = "
                                            + patientDomain + "; patientLid = "
                                            + patientLid);
                                if (flag)
                                {
                                    loggerBS005.error(
                                            "Message:{},checkDependency:{}",
                                            poorin200901uv09.toString(),
                                            "需要撤销或停止医嘱的互斥医嘱不存在!"
                                                + mutexesEntityClass
                                                + ": visitSn = " + visitSn
                                                + "; orderLid = "
                                                + orders.getMutexesOrderLid()
                                                + "; patientDomain = "
                                                + patientDomain
                                                + "; patientLid = "
                                                + patientLid);
                                }
                                return false;
                            }
                            else
                            {
                                logger.debug(
                                        "需要撤销或停止医嘱的互斥医嘱为已退费状态，此时不再对该条互斥医嘱进行操作，视为此医嘱没有相应互斥医嘱! \n {}",
                                        mutexesEntityClass + ": visitSn = " + visitSn + "; orderLid = "
                                            + orders.getMutexesOrderLid());
                            }
                        }

                        // [BUG]0012540 ADD END

                        // $Author :jia_yanqing
                        // $Date : 2012/8/13 17:30$
                        // [BUG]0008216 DELETE BEGIN
                        // }
                        // else
                        // {
                        // logger.error("互斥医嘱不可能为该类型! \n {}",
                        // mutexesEntityClass);
                        // return false;
                        // }
                        // [BUG]0008216 DELETE END
                    }
                }

                resWaitCancelOrStopOrderList.addAll(orderList);
            }
            // [BUG]0008216 ADD END
        }
        return true;
    }

    /**
     * 获取到相应医嘱数据进行撤销或停止数据更新操作
     * @param poorin200901uv09 xml文件中获取的医嘱撤销或停止相应业务数据
     */
    @Override
    @Transactional
    public void saveOrUpdate(POORIN200901UV09 poorin200901uv09)
    {
        serviceId = poorin200901uv09.getMessage().getServiceId();

        // 获得所已撤销或停止的医嘱
        setCancelOrStopOrders(poorin200901uv09.getTriggerEvent(),poorin200901uv09);

        if (resWaitCancelOrStopOrderList != null
            && !resWaitCancelOrStopOrderList.isEmpty())
        {
            // $Author:wei_peng
            // $Date:2013/2/22 15:48
            // $[BUG]0014072 MODIFY BEGIN

            // $Author:wu_jianfeng
            // $Date:2013/3/14 15:48
            // $[BUG]0014531 MODIFY BEGIN

        	/*
        	 * Author: yu_yzh
        	 * Date: 2015/11/25 16:55
        	 * 检验退费会把小项的退费都发过来，导致同时更新一个医嘱多次引发乐观锁。每个医嘱只更新一次即可
        	 * */
        	uniqueLabOrder();
        	/*
        	 * $Author: yu_yzh
        	 * $Date: 2015/8/3 15:52
        	 * 添加费用字段更新
        	 * MODIFY BEGIN
        	 * */ 
            commonService.updatePartiallyAll(resWaitCancelOrStopOrderList,
                    cancelOrStopPerson, cancelOrStopPersonName,
                    cancelOrStopTime, orderStatus, orderStatusName,
                    "updateTime", "mutexesOrderSn", "mutexesOrderNoType",
                    "deleteFlag", "deleteTime", "updateby", "deleteby", 
                    CHARGE_STATUS_CODE, CHARGE_STATUS_NAME);
            // MODIFY END
            
            // $[BUG]0014531 MODIFY END

            // 若草药医嘱关联的草药配方信息存在则删除
            commonService.updateList(herbalFormulas);
            // $[BUG]0014072 MODIFY END
        }

        // $Author:wei_peng
        // $Date:2013/7/8 13:02
        // $[BUG]0033848 ADD BEGIN
        // 封装护理操作记录，并保存数据库
        List<NursingOperation> nursingOpts = getNursingOpts(poorin200901uv09);
        commonService.saveList(nursingOpts);
        // $[BUG]0033848 ADD END
        
        /*
         * Author: yu_yzh
         * Date: 2015/11/08 20:09
         * 若门诊处方中药品全部被撤销，则逻辑删除该处方
         * */
        if(isOutpatient() && isMedicationOrder(poorin200901uv09.getOrder().get(0).getOrderType())){
        	List<Prescription> deleteList = getDeletePrescriptionList(poorin200901uv09);
        	commonService.updateList(deleteList);
        }
        
    }

    // $Author:wei_peng
    // $Date:2013/7/8 13:02
    // $[BUG]0033848 ADD BEGIN
    /**
     * 获取封装护理操作记录集合
     * @param poorin200901uv09
     * @return
     */
    private List<NursingOperation> getNursingOpts(
            POORIN200901UV09 poorin200901uv09)
    {
        List<NursingOperation> nursingOpts = new ArrayList<NursingOperation>();
        String patientDomain = poorin200901uv09.getPatientDomain();
        String patientLid = poorin200901uv09.getPatientLid();

        // $Author:jin_peng
        // $Date:2013/07/31 14:45
        // $[BUG]0035408 MODIFY BEGIN
        // 更改就诊内部序列号与患者内部序列号获取方式，更改为从医嘱表中获取
        // MedicalVisit medicalVisit = commonService.mediVisit(patientDomain,
        // patientLid, Integer.parseInt(poorin200901uv09.getVisitTimes()));
        for (int i = 0; i < resWaitCancelOrStopOrderList.size(); i++)
        {
            Object obj = resWaitCancelOrStopOrderList.get(i);
            String orderSnField = "orderSn";
            if (obj instanceof BloodRequest)
            {
                orderSnField = "requestSn";
            }
            Object orderSn = getObjectPrivateFields(obj, orderSnField);

            Object visitSn = getObjectPrivateFields(obj, "visitSn");
            Object patientSn = getObjectPrivateFields(obj, "patientSn");

            NursingOperation nursingOpt = new NursingOperation();
            // 护理内部序列号采番
            BigDecimal seq = commonService.getSequence(SEQ_NURSING_OPERATION);
            // 护理操作内部序列号
            nursingOpt.setOperationSn(seq);
            // 医嘱内部序列号
            nursingOpt.setOrderSn((BigDecimal) orderSn);
            // 就诊内部序列号
            nursingOpt.setVisitSn((BigDecimal) visitSn);
            // 患者内部序列号
            nursingOpt.setPatientSn((BigDecimal) patientSn);
            // $Author :chang_xuewen
            // $Date : 2013/12/03 11:00$
            // [BUG]0040271 ADD BEGIN
            nursingOpt.setOrgCode(poorin200901uv09.getOrgCode());
            nursingOpt.setOrgName(poorin200901uv09.getOrgName());
            // [BUG]0040271 ADD END
            // $[BUG]0035408 MODIFY END
            // 患者域ID
            nursingOpt.setPatientDomain(patientDomain);
            // 患者本地ID
            nursingOpt.setPatientLid(patientLid);
            // 获取当前医嘱的lid
            Class<?> cls = resWaitCancelOrStopOrderList.get(i).getClass();
            Object objVal;
            try {
				Field fl = cls.getDeclaredField("orderLid");
				fl.setAccessible(true);
				objVal = fl.get(resWaitCancelOrStopOrderList.get(i));
			} catch (NoSuchFieldException e) {
				throw new RuntimeException(e);
			} catch (SecurityException e) {
				throw new RuntimeException(e);
			} catch (IllegalArgumentException e) {
				throw new RuntimeException(e);
			} catch (IllegalAccessException e) {
				throw new RuntimeException(e);
			}
            for (Orders orders : ordersList)
            {
            	if(orders.getOrderLid().equals(objVal.toString())){
		            // 医嘱类型编码
		            nursingOpt.setOrderType(orders.getOrderType());
		            // 医嘱类型名称
		            nursingOpt.setOrderTypeName(orders.getOrderTypeName());
		            // 操作人ID
		            nursingOpt.setOperatorId(orders.getCancelOrStopPerson());
		            // 操作人姓名
//		            nursingOpt.setOperatorName(orders.getCancelOrStopPersonName());
		         // 优先根据字典取姓名
		            String operatorName = DictionaryUtils.getNameFromDictionary(
		            		Constants.DOMAIN_STAFF, orders.getCancelOrStopPerson(), orders.getCancelOrStopPersonName());
		            nursingOpt.setOperatorName(operatorName);
		            // 操作时间
		            nursingOpt.setOperationTime(DateUtils.stringToDate(orders.getCancelOrStopTime()));
            	}
            }
            // 执行科室ID
            nursingOpt.setExecuteDeptCode(poorin200901uv09.getExecDept());
            // 执行科室名称
//            nursingOpt.setExecuteDeptName(poorin200901uv09.getExecDeptName());
            // 根据字典取科室名称
            String executeDeptName = DictionaryUtils.getNameFromDictionary(
            		Constants.DOMAIN_DEPARTMENT, poorin200901uv09.getExecDept(), poorin200901uv09.getExecDeptName());
            nursingOpt.setExecuteDeptName(executeDeptName);
            if (isCancelMessage(poorin200901uv09.getTriggerEvent()))
            {
                // 医嘱执行状态编码
                nursingOpt.setOrderStatusCode(Constants.ORDER_STATUS_CANCEL);
                // 医嘱执行状态名称
                nursingOpt.setOrderStatusName(DictionaryCache.getDictionary(
                        Constants.DOMAIN_ORDER_STATUS).get(
                        Constants.ORDER_STATUS_CANCEL));
            }
            else if (isStopMessage(poorin200901uv09.getTriggerEvent()))
            {
                nursingOpt.setOrderStatusCode(Constants.ORDER_STATUS_STOP);
                nursingOpt.setOrderStatusName(DictionaryCache.getDictionary(
                        Constants.DOMAIN_ORDER_STATUS).get(
                        Constants.ORDER_STATUS_STOP));
            }
            if(isOutpatient() && isReturnMessage(poorin200901uv09.getTriggerEvent())){
                // 医嘱执行状态编码
                nursingOpt.setOrderStatusCode(Constants.ORDER_STATUS_RETURNED_CHARGE);
                // 医嘱执行状态名称
                nursingOpt.setOrderStatusName(DictionaryCache.getDictionary(
                        Constants.DOMAIN_ORDER_STATUS).get(
                        Constants.ORDER_STATUS_RETURNED_CHARGE));
            }
            nursingOpt.setCreateTime(systemTime);
            nursingOpt.setUpdateTime(systemTime);
            nursingOpt.setUpdateCount(Constants.INSERT_UPDATE_COUNT);
            nursingOpt.setDeleteFlag(Constants.NO_DELETE_FLAG);
            nursingOpt.setCreateby(DataMigrationUtils.getDataMigration() + serviceId);
            nursingOpts.add(nursingOpt);
        }
        return nursingOpts;
    }

    // $[BUG]0033848 ADD END

    /**
     * 根据不同的场景，设置在医嘱撤销或停止场景时需要操作的属性的属性名和属性值
     * @param order 带设置的医嘱
     * @param newUpFlag 消息交互类型
     */
    private void setCancelOrStopFeild(Object order, String newUpFlag)
    {
        if (GeneralOrder.class.equals(order.getClass())
            || ConsultationOrder.class.equals(order.getClass()))
        {
            // 设置其它医嘱和会诊医嘱撤销或停止场景时需要操作的属性的属性名和属性值
            setCancelOrStopFeildNameAndValueNew(newUpFlag);
        }
        else if (LabOrder.class.equals(order.getClass())
            || ExaminationOrder.class.equals(order.getClass()))
        {
            // 设置检查医嘱和检验医嘱撤销或停止场景时需要操作的属性的属性名和属性值
            setCancelOrStopFeildNameAndValueForExamAndLab(newUpFlag);
        }
        else
        {
            // 设置医嘱撤销或停止场景时需要操作的属性的属性名和属性值
            setCancelOrStopFeildNameAndValue(newUpFlag);
        }
    }

    // $Author :jia_yanqing
    // $Date : 2012/9/27 10:33$
    // [BUG]0010020 MODIFY BEGIN
    /**
     * 设置在医嘱撤销或停止场景时需要操作的属性的属性名和属性值
     * @param newUpFlag 消息交互类型
     */
    private void setCancelOrStopFeildNameAndValue(String newUpFlag)
    {

        // 医嘱状态
        orderStatus = ORDER_STATUS;
        // 医嘱状态名称
        orderStatusName = ORDER_STATUS_NAME;

        if (isCancelMessage(newUpFlag))
        {
            // 设置撤销时医嘱操作人属性名
            cancelOrStopPerson = CANCEL_PERSON;

            // 设置撤销时医嘱操作人姓名属性名
            cancelOrStopPersonName = CANCEL_PERSON_NAME;

            // 设置撤销时医嘱操作事件属性名
            cancelOrStopTime = CANCEL_TIME;

            // 设置撤销时医嘱状态值
            cancelOrStopOrderStatusValue = Constants.ORDER_STATUS_CANCEL;

            // 设置撤销时医嘱状态名称
            cancelOrStopOrderStatusName = DictionaryCache.getDictionary(
                    Constants.DOMAIN_ORDER_STATUS).get(
                    Constants.ORDER_STATUS_CANCEL);
        }
        else if (isStopMessage(newUpFlag))
        {
            // 设置停止时医嘱操作人属性名
            cancelOrStopPerson = STOP_PERSON;

            // 设置停止时医嘱操作人姓名属性名
            cancelOrStopPersonName = STOP_PERSON_NAME;

            // 设置停止时医嘱操作事件属性名
            cancelOrStopTime = STOP_TIME;

            // 设置停止时医嘱状态值
            cancelOrStopOrderStatusValue = Constants.ORDER_STATUS_STOP;

            // Author :jia_yanqing
            // Date : 2012/10/15 14:33
            // [BUG]0010415 ADD BEGIN
            // 设置停止时医嘱状态名称值
            cancelOrStopOrderStatusName = DictionaryCache.getDictionary(
                    Constants.DOMAIN_ORDER_STATUS).get(
                    Constants.ORDER_STATUS_STOP);
            // [BUG]0010415 ADD END
        }
    }

    // $Author :jia_yanqing
    // $Date : 2012/7/12 15:33$
    // [BUG]0007925 ADD BEGIN
    /**
     * 设置在医嘱撤销或停止场景时需要操作的属性的属性名和属性值
     * @param newUpFlag 消息交互类型
     */
    private void setCancelOrStopFeildNameAndValueNew(String newUpFlag)
    {
        // 医嘱状态
        orderStatus = ORDER_STATUS_CODE;
        // 医嘱状态名称
        orderStatusName = ORDER_STATUS_NAME;

        if (isCancelMessage(newUpFlag))
        {
            // 设置撤销时医嘱操作人属性名
            cancelOrStopPerson = CANCEL_PERSON_ID;

            // 设置撤销时医嘱操作人姓名属性名
            cancelOrStopPersonName = CANCEL_PERSON_NAME;

            // 设置撤销时医嘱操作事件属性名
            cancelOrStopTime = CANCEL_TIME;

            // 设置撤销时医嘱状态值
            cancelOrStopOrderStatusValue = Constants.ORDER_STATUS_CANCEL;
            // 设置撤销时医嘱状态名称
            cancelOrStopOrderStatusName = DictionaryCache.getDictionary(
                    Constants.DOMAIN_ORDER_STATUS).get(
                    Constants.ORDER_STATUS_CANCEL);
        }
        else if (isStopMessage(newUpFlag))
        {
            // 设置停止时医嘱操作人属性名
            cancelOrStopPerson = STOP_PERSON_ID;

            // 设置停止时医嘱操作人姓名属性名
            cancelOrStopPersonName = STOP_PERSON_NAME;

            // 设置停止时医嘱操作事件属性名
            cancelOrStopTime = STOP_TIME;

            // 设置停止时医嘱状态值
            cancelOrStopOrderStatusValue = Constants.ORDER_STATUS_STOP;

            // Author :jin_peng
            // Date : 2012/11/14 14:56
            // [BUG]0011478 ADD BEGIN
            // 设置停止时医嘱状态名称
            cancelOrStopOrderStatusName = DictionaryCache.getDictionary(
                    Constants.DOMAIN_ORDER_STATUS).get(
                    Constants.ORDER_STATUS_STOP);

            // [BUG]0011478 ADD END
        }
    }

    // [BUG]0007925 ADD END
    // [BUG]0010020 MODIFY END

    // $Author :jia_yanqing
    // $Date : 2012/10/16 13:33$
    // [BUG]0010426 ADD BEGIN
    /**
     * 设置在医嘱撤销或停止场景时需要操作的属性的属性名和属性值
     * @param newUpFlag 消息交互类型
     */
    private void setCancelOrStopFeildNameAndValueForExamAndLab(String newUpFlag)
    {
        // 医嘱状态
        orderStatus = ORDER_STATUS;
        // 医嘱状态名称
        orderStatusName = ORDER_STATUS_NAME;

        if (isCancelMessage(newUpFlag))
        {
            // 设置撤销时医嘱操作人属性名
            cancelOrStopPerson = CANCEL_PERSON;

            // 设置撤销时医嘱操作人姓名属性名
            cancelOrStopPersonName = CANCEL_PERSON_NAME;

            // 设置撤销时医嘱操作事件属性名
            cancelOrStopTime = CANCEL_TIME;

            // 设置撤销时医嘱状态值
            cancelOrStopOrderStatusValue = Constants.ORDER_STATUS_CANCEL;
            // 设置撤销时医嘱状态名称
            cancelOrStopOrderStatusName = DictionaryCache.getDictionary(
                    Constants.DOMAIN_ORDER_STATUS).get(
                    Constants.ORDER_STATUS_CANCEL);
            
        }
        else if (isStopMessage(newUpFlag))
        {
            // 设置停止时医嘱操作人属性名
            cancelOrStopPerson = STOP_PERSON_ID;

            // 设置停止时医嘱操作人姓名属性名
            cancelOrStopPersonName = STOP_PERSON_NAME;

            // 设置停止时医嘱操作事件属性名
            cancelOrStopTime = STOP_TIME;

            // 设置停止时医嘱状态值
            cancelOrStopOrderStatusValue = Constants.ORDER_STATUS_STOP;

            // 设置时医嘱状态名称
            cancelOrStopOrderStatusName = DictionaryCache.getDictionary(
                    Constants.DOMAIN_ORDER_STATUS).get(
                    Constants.ORDER_STATUS_STOP);
        }
        
        if(isOutpatient() && isReturnMessage(newUpFlag)){
            // 设置退费时医嘱状态值
            cancelOrStopOrderStatusValue = Constants.ORDER_STATUS_RETURNED_CHARGE;

            // 设置退费时医嘱状态名称
            cancelOrStopOrderStatusName = DictionaryCache.getDictionary(
                    Constants.DOMAIN_ORDER_STATUS).get(
                    Constants.ORDER_STATUS_RETURNED_CHARGE);
        }
    }

    // [BUG]0010426 ADD END

    /**
     * 获取到相应已撤销或停止医嘱数据
     * @param poorin200901uv09 xml文件中获取的医嘱撤销或停止相应业务数据
     * @return 与消息对应的相应已撤销或停止医嘱对象集合
     */
    private void setCancelOrStopOrders(String newUpFlag,POORIN200901UV09 poorin200901uv09)
    {
        for (int i = 0; i < resWaitCancelOrStopOrderList.size(); i++)
        {
            setCancelOrStopFeild(resWaitCancelOrStopOrderList.get(i), newUpFlag);
            // 指定相应医嘱对象的属性名和属性值
            Map<String, Object> orderMap = new HashMap<String, Object>();
            // 获取当前医嘱的lid
            Class<?> cls = resWaitCancelOrStopOrderList.get(i).getClass();
            Object objVal;
            try {
				Field fl = cls.getDeclaredField("orderLid");
				fl.setAccessible(true);
				objVal = fl.get(resWaitCancelOrStopOrderList.get(i));
			} catch (NoSuchFieldException e) {
				throw new RuntimeException(e);
			} catch (SecurityException e) {
				throw new RuntimeException(e);
			} catch (IllegalArgumentException e) {
				throw new RuntimeException(e);
			} catch (IllegalAccessException e) {
				throw new RuntimeException(e);
			}
            for (Orders orders : ordersList)
            {
            	if(orders.getOrderLid().equals(objVal.toString())){
		            orderMap.put(cancelOrStopPerson,
		            		orders.getCancelOrStopPerson());
//		            orderMap.put(cancelOrStopPersonName,
//		                    orders.getCancelOrStopPersonName());
		            //优先根据字典取姓名
		            String name = DictionaryUtils.getNameFromDictionary(Constants.DOMAIN_STAFF, 
		            		orders.getCancelOrStopPerson(), orders.getCancelOrStopPersonName());
		            orderMap.put(cancelOrStopPersonName, name);
		            
		            orderMap.put(
		                    cancelOrStopTime,
		                    DateUtils.stringToDate(orders.getCancelOrStopTime()));
            	}
            }
            orderMap.put(orderStatus, cancelOrStopOrderStatusValue);
            orderMap.put(orderStatusName, cancelOrStopOrderStatusName);
            orderMap.put("updateTime", systemTime);
            // $Author :chang_xuewen
            // $Date : 2013/12/03 11:00$
            // [BUG]0040271 ADD BEGIN
            orderMap.put("orgCode",poorin200901uv09.getOrgCode());
            // [BUG]0040271 ADD END
            // $Author:wei_peng
            // $Date:2013/2/22 15:48
            // $[BUG]0014072 MODIFY BEGIN
            // 当撤销的医嘱为草药医嘱时，对该医嘱进行逻辑删除
            if (isCancelMessage(newUpFlag)
                && MedicationOrder.class.equals(resWaitCancelOrStopOrderList.get(
                        i).getClass()))
            {
                MedicationOrder medicationOrder = (MedicationOrder) resWaitCancelOrStopOrderList.get(i);
                if (Constants.HERBAL_ORDER.equals(medicationOrder.getOrderType()))
                {
                    orderMap.put("deleteFlag", Constants.DELETE_FLAG);
                    orderMap.put("deleteTime", systemTime);
                    orderMap.remove("updateTime");
                    orderMap.put("deleteby", serviceId);
                    // 查找草药医嘱关联的草药配方信息，并致删除标志为逻辑删除
                    Map<String, Object> condition = new HashMap<String, Object>();
                    condition.put("orderSn", medicationOrder.getOrderSn());
                    herbalFormulas = commonService.selectByCondition(
                            HerbalFormula.class, condition);
                    if (herbalFormulas != null && herbalFormulas.size() > 0)
                    {
                        for (Object object : herbalFormulas)
                        {
                            HerbalFormula herbal = (HerbalFormula) object;
                            herbal.setDeleteFlag(Constants.DELETE_FLAG);
                            herbal.setDeleteTime(systemTime);
                            herbal.setDeleteby(serviceId);
                        }
                    }
                }
            }
            else
            {
                orderMap.put("updateby", serviceId);
            }
            // $[BUG]0014072 MODIFY END

            /*
             * $Author: yu_yzh
             * $Date: 2015/8/3 11:06
             * 退费消息更改费用状态
             * ADD BEGIN
             * */
            if(isOutpatient() && isReturnMessage(newUpFlag)){
            	orderMap.put(CHARGE_STATUS_CODE, Constants.CHARGE_STATUS_RETURNCHARGE);
            	orderMap.put(CHARGE_STATUS_NAME, DictionaryCache
						.getDictionary(Constants.ORDER_CHARGE_STATUS)
						.get(Constants.CHARGE_STATUS_RETURNCHARGE));
            }
            // ADD END
            
            // 给相应医嘱的指定属性赋值
            setObjectPrivateFields(resWaitCancelOrStopOrderList.get(i),
                    orderMap);
        }

    }

    /**
     * 给传入对象私有属性赋值(多个)
     * @param obj 待操作属性所在对象
     * @param map 属性名和该属性值集合
     */
    private void setObjectPrivateFields(Object obj, Map<String, Object> map)
    {
        Field field = null;

        if (obj != null && map != null && !map.isEmpty())
        {
            try
            {
                Set<Entry<String, Object>> entrySet = map.entrySet();
                Iterator<Entry<String, Object>> entryIterator = entrySet.iterator();

                while (entryIterator.hasNext())
                {
                    Entry<String, Object> entry = entryIterator.next();

                    // 通过map中的属性名给该所在对象中的对应属性赋值
                    field = obj.getClass().getDeclaredField(entry.getKey());
                    field.setAccessible(true);
                    field.set(obj, entry.getValue());
                }
            }
            catch (IllegalArgumentException e)
            {
                throw new RuntimeException(e);
            }
            catch (SecurityException e)
            {
                throw new RuntimeException(e);
            }
            catch (IllegalAccessException e)
            {
                throw new RuntimeException(e);
            }
            catch (NoSuchFieldException e)
            {
                throw new RuntimeException(e);
            }
        }
    }

    private Object getObjectPrivateFields(Object obj, String fieldName)
    {
        Object fieldValue = null;
        try
        {
            Field field = obj.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            fieldValue = field.get(obj);
        }
        catch (IllegalArgumentException e)
        {
            throw new RuntimeException(e);
        }
        catch (SecurityException e)
        {
            throw new RuntimeException(e);
        }
        catch (IllegalAccessException e)
        {
            throw new RuntimeException(e);
        }
        catch (NoSuchFieldException e)
        {
            throw new RuntimeException(e);
        }
        return fieldValue;
    }

    /*
     * $Author: yu_yzh
     * $Date: 2015/8/3 10:05
     * 增加判断消息是否是撤销退费消息，判断是否V2消息
     * ADD BEGIN
     * */
    /**
     * 判断消息是否是撤销退费消息
     * */
    private boolean isReturnMessage(String newUpFlag){
    	return Constants.V2_RETURN_MESSAGE_FLAG.equals(newUpFlag);
    }
    
    /**
     * 判断是否V2消息
     * */
    private boolean isV2Message(POORIN200901UV09 poorin200901uv09){
    	return Constants.V2.equals(poorin200901uv09.getMessage().getMsgMode());
    }
    
    /**
     * 判断是否撤销消息(V2消息包含开嘱医生撤销，撤销退费)
     * */
    private boolean isCancelMessage(String newUpFlag){
    	boolean flag = Constants.CANCEL_MESSAGE_FLAG.equals(newUpFlag)
    			|| Constants.V2_DOC_CANCEL_MESSAGE_FLAG.equals(newUpFlag)
//    			|| Constants.V2_EXEC_CANCEL_MESSAGE_FLAG.equals(poorin200901uv09.getTriggerEvent())
    			|| Constants.V2_RETURN_MESSAGE_FLAG.equals(newUpFlag);
    	return flag;    			
    }
    /**
     * 判断是否停止消息
     * */
    private boolean isStopMessage(String newUpFlag){
    	return Constants.STOP_MESSAGE_FLAG.equals(newUpFlag);
    }
    // ADD END
    
    private boolean isMedicationOrder(String orderType){
    	return MedicationOrder.class.equals(commonService.getOrderClass(orderType));
    }
    
    /*
     * 门诊用药医嘱作废时对药品处方做判断，若全部被撤销则删除处方
     * */
    private List<Prescription> getDeletePrescriptionList(POORIN200901UV09 poorin200901uv09){
    	List<Prescription> deleteList = new ArrayList<Prescription>();
    	List<MedicationOrder> medicationOrderList = new ArrayList<MedicationOrder>();
    	List<Prescription> prescriptionList = new ArrayList<Prescription>();
    	// 从数据库中获取处方和用药医嘱
    	for(Orders order : poorin200901uv09.getOrder()){
    		MedicationOrder medicationOrder = prescriptionService.selectMedicationOrderByOrderLid(order.getOrderLid(), 
    				medicalVisit.getVisitSn().toString());
    		if(medicationOrder == null) continue;
    		medicationOrderList.add(medicationOrder);
    		boolean existFlag = false;
    		for(Prescription prescription : prescriptionList){
    			if(medicationOrder.getPrescriptionSn().equals(prescription.getPrescriptionSn())){
    				existFlag = true;
    				break;
    			}
    		}
    		if(!existFlag){
    			Prescription prescription = prescriptionService.selectPrescriptionByPrescriptionSn(
    					medicationOrder.getPrescriptionSn().toString(), medicationOrder.getVisitSn().toString());
    			if(prescription != null){
    				prescriptionList.add(prescription);
    			}
    		}
    	}
    	// 判断处方中的用药医嘱是否已经全部被撤销，若全部被撤销则删除处方
    	for(Prescription prescription : prescriptionList){
    		List<MedicationOrder> orderList = prescriptionService.selectMedicationOrdersByPrescriptionSn(
    				prescription.getPrescriptionSn().toString(), prescription.getVisitSn().toString());
    		int count = 0;
    		for(MedicationOrder o1 : orderList){
    			for(MedicationOrder o2 : medicationOrderList){
    				if(o1.getOrderSn().equals(o2.getOrderSn())){
    					count++;
    				}
    			}
    		}
    		if(count == orderList.size()){
    			deleteList.add(prescription);
    			prescription.setUpdateby(serviceId);
    			prescription.setUpdateTime(systemTime);
    			prescription.setDeleteby(serviceId);
    			prescription.setDeleteFlag(Constants.DELETE_FLAG);
    			prescription.setDeleteTime(systemTime);
    		}
    	}
    	return deleteList;
    }
    
    /**
     * 检验退费会把小项的退费都发过来，导致同时更新一个医嘱多次引发乐观锁。每个检验医嘱只保留一个
     * */
    private void uniqueLabOrder(){
    	List<LabOrder> labOrderList = new ArrayList<LabOrder>();
    	List<LabOrder> uniqueList = new ArrayList<LabOrder>();
    	for(Object obj : resWaitCancelOrStopOrderList){
    		LabOrder lo = null;
    		if(obj instanceof LabOrder){
    			lo = (LabOrder) obj;
    			labOrderList.add(lo);
    			boolean uniqueFlag = true;
    			for(LabOrder unique : uniqueList){
    				if(unique.getOrderSn().equals(lo.getOrderSn())){
    					uniqueFlag = false;
    					break;
    				}
    			}
    			if(uniqueFlag){
    				uniqueList.add(lo);
    			}
    		}
    	}
    	//将重复的医嘱删除
    	resWaitCancelOrStopOrderList.removeAll(labOrderList);
    	resWaitCancelOrStopOrderList.addAll(uniqueList);
    }
    
    private boolean isOutpatient(){
    	return Constants.VISIT_TYPE_CODE_OUTPATIENT.equals(medicalVisit.getVisitTypeCode());
    }
}
