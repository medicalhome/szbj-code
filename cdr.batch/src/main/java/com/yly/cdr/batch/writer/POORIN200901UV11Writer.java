package com.yly.cdr.batch.writer;

import java.lang.reflect.Field;
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

import com.yly.cdr.core.Constants;
import com.yly.cdr.entity.ConsultationOrder;
import com.yly.cdr.entity.GeneralOrder;
import com.yly.cdr.entity.NursingOperation;
import com.yly.cdr.hl7.dto.Orders;
import com.yly.cdr.hl7.dto.poorin200901uv11.POORIN200901UV11;
import com.yly.cdr.service.CommonService;
import com.yly.cdr.service.NurseOperationService;
import com.yly.cdr.util.DateUtils;
import com.yly.cdr.util.ObjectUtils;
import com.yly.cdr.util.StringUtils;

/**
 * 医嘱执行
 * @version 1.0, 2012/05/14
 * @author guo_hongyan
 * @since 1.0
 */
@Component(value = "poorin200901uv11Writer")
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class POORIN200901UV11Writer implements BusinessWriter<POORIN200901UV11>
{

    private static final Logger logger = LoggerFactory.getLogger(POORIN200901UV11Writer.class);

    @Autowired
    private CommonService commonService;

    // $Author :jia_yanqing
    // $Date : 2012/7/9 10:33$
    // [BUG]0007781 ADD BEGIN
    @Autowired
    private NurseOperationService nurseOperationService;

    // [BUG]0007781 ADD END

    // 护理操作.护理内部序列号sequences
    private static final String SEQ_NURSING_OPERATION = "SEQ_NURSING_OPERATION";

    // 数据库表中存在执行医嘱List
    private List<Object> execOrderExistsList;

    // 患者本地ID
    private String patientLid;

    // 就诊内部序列号
    private BigDecimal visitSn;

    // 患者内部序列号
    private BigDecimal patientSn;

    // 医嘱内部序列号
    private BigDecimal orderSn;

    // 消息id
    private String serviceId;

    // $Author :jia_yanqing
    // $Date : 2012/9/5 17:30$
    // [BUG]0008782 ADD BEGIN
    /**
     * 非业务数据校验
     */
    @Override
    public boolean validate(POORIN200901UV11 poorin200901uv11)
    {
        List<Orders> orderList = poorin200901uv11.getOrderInfo();
        // $Author :jin_peng
        // $Date : 2012/09/06 14:35$
        // [BUG]0009450 ADD BEGIN
        Class<?> entityClass = null;
        // [BUG]0009450 ADD END

        for (Orders order : orderList)
        {
            // 验证医嘱项中医嘱号，执行时间，执行人编码，执行科室编码，执行状态编码为非空
            if (!StringUtils.isNotNullAll(order.getOrderLid(),
                    order.getBeginTime(), order.getOperatorId(),
                    order.getExecDept(), order.getOrderStatus()))
            {
                logger.error("非空字段验证未通过: {}",
                        "医嘱号：OrderLid = " + order.getOrderLid()
                            + ";执行人编码：BeginTime = " + order.getBeginTime()
                            + ";执行人编码：OperatorId = " + order.getOperatorId()
                            + ";执行科室编码：ExecDept = " + order.getExecDept()
                            + ";执行状态编码：OrderStatus = " + order.getOrderStatus());
                return false;
            }

            // $Author :jin_peng
            // $Date : 2012/09/06 14:35$
            // [BUG]0009450 ADD BEGIN
            // 根据医嘱类型获取相应医嘱的对象class
            entityClass = commonService.getOrderClass(order.getOrderType());

            if (entityClass == null)
            {
                logger.error("医嘱执行消息中的医嘱类型没有匹配CDR医嘱类型 : {}", " orderType = "
                    + order.getOrderType());
                return false;
            }
            // [BUG]0009450 ADD END
        }
        return true;
    }

    // [BUG]0008782 ADD END

    /**
     * 业务校验
     */
    @Override
    public boolean checkDependency(POORIN200901UV11 poorin200901uv11,
            boolean flag)
    {
        serviceId = poorin200901uv11.getMessage().getServiceId();

        // 需要执行医嘱对象集合
        execOrderExistsList = new ArrayList<Object>();
        List<Object> orderList = null;
        Class<?> entityClass = null;

        // 获取医嘱执行消息中医嘱信息
        List<Orders> ordersList = poorin200901uv11.getOrderInfo();

        if (ordersList != null && !ordersList.isEmpty())
        {
            // $Author :tongmeng
            // $Date : 2012/5/29 14:30$
            // [BUG]0006657 MODIFY BEGIN
            // 获得患者本地ID
            patientLid = poorin200901uv11.getPatientLid();
            // [BUG]0006657 MODIFY END

            for (Orders orders : ordersList)
            {
                // 根据医嘱类型获取相应医嘱的对象class(TODO 医嘱类型)
                entityClass = commonService.getOrderClass(orders.getOrderType());

                // $Author :jin_peng
                // $Date : 2012/09/06 14:35$
                // [BUG]0009450 DELETE BEGIN
                // 如果entityClass为null说明没有匹配CDR的医嘱类型
                /*
                 * if (entityClass == null) {
                 * logger.error("医嘱执行消息中的医嘱类型没有匹配CDR医嘱类型 : {}", " orderType = "
                 * + orders.getOrderType()); return false; }
                 */
                // [BUG]0009450 DELETE END

                Map<String, Object> map = new HashMap<String, Object>();
                map.put("orderLid", orders.getOrderLid());
                map.put("patientDomain", poorin200901uv11.getPatientDomain());
                map.put("patientLid", patientLid);
                map.put("deleteFlag", Constants.NO_DELETE_FLAG);

                orderList = commonService.selectByCondition(entityClass, map);

                // 判断相应医嘱是否存在
                if (orderList == null || orderList.isEmpty())
                {
                    logger.error(
                            "需要执行的医嘱不存在! \n {}",
                            entityClass + ": orderLid = "
                                + orders.getOrderLid() + "; patientDomain = "
                                + poorin200901uv11.getPatientDomain()
                                + "; patientLid = " + patientLid);
                    return false;
                }

                // 获取相应医嘱
                Object orderObject = orderList.get(0);

                Map<String, Object> orderMap = new HashMap<String, Object>();

                orderMap.put("updateby", serviceId);

                ObjectUtils.setObjectPrivateFields(orderObject, orderMap);

                // 数据库存在医嘱执行 数据追加
                execOrderExistsList.add(orderObject);
            }
            return true;
        }
        logger.error("医嘱执行消息不存在！");
        return false;
    }

    @Override
    @Transactional
    public void saveOrUpdate(POORIN200901UV11 poorin200901uv11)
    {
        List<Object> nurOperList = getNurOperation(poorin200901uv11);
        nurseOperationService.saveList(nurOperList, execOrderExistsList);
    }

    /**
     * 护理操作返回List
     * @param poorin200901uv11
     * @return 新增的护理操作List
     */
    private List<Object> getNurOperation(POORIN200901UV11 poorin200901uv11)
    {
        List<Object> nurOperationList = new ArrayList<Object>();
        NursingOperation nursingOperation = null;
        List<Orders> orderList = poorin200901uv11.getOrderInfo();
        for (int i = 0; i < orderList.size(); i++)
        {
            nursingOperation = new NursingOperation();
            // 系统时间
            Date sysdate = DateUtils.getSystemTime();

            // $Author :jia_yanqing
            // $Date : 2012/7/9 10:33$
            // [BUG]0007781 MODIFY BEGIN
            // 读取存在医嘱的属性值
            getOrderInfo(execOrderExistsList.get(i), orderList.get(i));
            // [BUG]0007781 MODIFY END

            // 护理内部序列号采番
            BigDecimal seq = commonService.getSequence(SEQ_NURSING_OPERATION);
            // 护理操作内部序列号
            nursingOperation.setOperationSn(seq);
            // 就诊内部序列号
            nursingOperation.setVisitSn(visitSn);
            // 医嘱内部序列号
            nursingOperation.setOrderSn(orderSn);
            // 患者内部序列号
            nursingOperation.setPatientSn(patientSn);
            // 域代码
            nursingOperation.setPatientDomain(poorin200901uv11.getPatientDomain());
            // 患者本地ID
            nursingOperation.setPatientLid(patientLid);
            // 医嘱类型
            nursingOperation.setOrderType(orderList.get(i).getOrderType());

            // $Author :tong_meng
            // $Date : 2012/7/1 15:33$
            // [BUG]0007418 ADD BEGIN
            // 医嘱类型名稱
            nursingOperation.setOrderTypeName(orderList.get(i).getOrderTypeName());
            // [BUG]0007418 ADD END

            // 护理人ID
            nursingOperation.setOperatorId(orderList.get(i).getOperatorId());
            // 护理人姓名
            nursingOperation.setOperatorName(orderList.get(i).getOperatorName());
            // 审核人ID
            nursingOperation.setReviewerId(orderList.get(i).getReviewerId());
            // 审核人姓名
            nursingOperation.setReviewerName(orderList.get(i).getReviewerName());
            // 审核时间
            nursingOperation.setReviewerTime(DateUtils.stringToDate(orderList.get(
                    i).getReviewerTime()));
            // 操作开始时间
            nursingOperation.setOperationTime(DateUtils.stringToDate(orderList.get(
                    i).getBeginTime()));
            // 医嘱执行结果
            nursingOperation.setOperationResult(orderList.get(i).getExecResult());
            // 创建时间
            nursingOperation.setCreateTime(sysdate);
            // 更新时间
            nursingOperation.setUpdateTime(sysdate);
            // 更新回数
            nursingOperation.setUpdateCount(Constants.INSERT_UPDATE_COUNT);
            // 删除标志
            nursingOperation.setDeleteFlag(Constants.NO_DELETE_FLAG);

            // 创建者
            nursingOperation.setCreateby(serviceId);

            nurOperationList.add(nursingOperation);
        }
        return nurOperationList;
    }

    /**
     * 通过不同的医嘱类对象返回相应的医嘱数据表中对应的数据
     * @param obj 医嘱的类对象
     * @return orderSn:医嘱内部序列号;visitSn:就诊内部序列号;patientSn:患者内部序列号
     */
    private void getOrderInfo(Object obj, Orders order)
    {
        try
        {
            // 医嘱内部序列号属性
            Field orderField = obj.getClass().getDeclaredField("orderSn");
            // 同时可以取私有属性(private)
            orderField.setAccessible(true);
            // 就诊内部序列号属性
            Field visitField = obj.getClass().getDeclaredField("visitSn");
            // 同时可以取私有属性(private)
            visitField.setAccessible(true);
            // 患者内部序列号属性
            Field patientField = obj.getClass().getDeclaredField("patientSn");
            // 同时可以取私有属性(private)
            patientField.setAccessible(true);

            // $Author :jia_yanqing
            // $Date : 2012/8/6 16:33$
            // [BUG]0008504 MODIFY BEGIN
            // $Author :jia_yanqing
            // $Date : 2012/7/9 10:33$
            // [BUG]0007781 ADD BEGIN
            Field orderStatus;
            if (GeneralOrder.class.equals(obj.getClass())
                || ConsultationOrder.class.equals(obj.getClass()))
            {
                // 医嘱执行状态
                orderStatus = obj.getClass().getDeclaredField("orderStatusCode");
            }
            else
            {
                orderStatus = obj.getClass().getDeclaredField("orderStatus");
            }
            // 同时可以取私有属性(private)
            orderStatus.setAccessible(true);

            // 医嘱执行状态名称
            Field orderStatusName = obj.getClass().getDeclaredField(
                    "orderStatusName");
            // 同时可以取私有属性(private)
            orderStatusName.setAccessible(true);
            // 更新时间
            Field updateTime = obj.getClass().getDeclaredField("updateTime");
            // 同时可以取私有属性(private)
            updateTime.setAccessible(true);
            // [BUG]0007781 ADD END
            // [BUG]0008504 MODIFY END
            try
            {
                // 医嘱内部序列号DB值
                orderSn = (BigDecimal) orderField.get(obj);
                // 就诊内部序列号DB值
                visitSn = (BigDecimal) visitField.get(obj);
                // 患者内部序列号DB值
                patientSn = (BigDecimal) patientField.get(obj);

                // $Author :jia_yanqing
                // $Date : 2012/7/9 10:33$
                // [BUG]0007781 ADD BEGIN
                // 医嘱执行状态
                orderStatus.set(obj, order.getOrderStatus());
                // 医嘱执行状态名称
                orderStatusName.set(obj, order.getOrderStatusName());
                // 更新时间
                updateTime.set(obj, DateUtils.getSystemTime());
                // [BUG]0007781 ADD END
            }
            catch (IllegalArgumentException e)
            {
                e.printStackTrace();
            }
            catch (IllegalAccessException e)
            {
                e.printStackTrace();
            }
        }
        catch (SecurityException e)
        {
            e.printStackTrace();
        }
        catch (NoSuchFieldException e)
        {
            e.printStackTrace();
        }

    }

}
