package com.yly.cdr.batch.writer;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.yly.cdr.cache.DictionaryCache;
import com.yly.cdr.core.Constants;
import com.yly.cdr.entity.BloodDeliveryOrder;
import com.yly.cdr.entity.BloodRequest;
import com.yly.cdr.hl7.dto.poorin200901uv07.POORIN200901UV07;
import com.yly.cdr.service.BloodSpecialRequestService;
import com.yly.cdr.service.CommonService;
import com.yly.cdr.util.DateUtils;
import com.yly.cdr.util.StringUtils;

/**
 *取血单Writer 注意：这里需要根据业务，添加判断条件。如：
 * 1：如果当前消息是子消息，父消息如果存在，则保存子消息内容；如果父消息不存在，则不保存子消息内容。 2：如果是父消息，直
 * 
 * 接保存父消息内容。
 * 
 * @author liu_jingyang/tong_meng
 * @version 1.0
 */
@Component(value = "poorin200901uv07Writer")
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class POORIN200901UV07Writer implements BusinessWriter<POORIN200901UV07>
{

    private static final Logger logger = LoggerFactory.getLogger(POORIN200901UV07Writer.class);

    private static final Logger loggerBS309 = LoggerFactory.getLogger("BS309");

    @Autowired
    private CommonService commonService;

    @Autowired
    private BloodSpecialRequestService bloodSpecialRequestService;

    // 新增标志
    private String newUpFlag;

    // 取血单编号
    private String orderLid;

    // 取血单信息
    private List<BloodDeliveryOrder> deliveryOrderResult;

    // 用血申请单信息
    private BloodRequest bloodRequest;

    // $Author :jin_peng
    // $Date : 2012/08/27 11:03$
    // [BUG]0009142 ADD BEGIN
    // 系统当前时间
    private Date systemTime;
    
    // 消息id
    private String serviceId;

    /**
     * 初始数据参数赋值
     */
    public POORIN200901UV07Writer()
    {
        systemTime = DateUtils.getSystemTime();
    }

    // [BUG]0009142 ADD END

    /**
     * 数据校验
     * 
     * @param baseDto
     */
    public boolean validate(POORIN200901UV07 poorin200901uv07)
    {
    	// $Author :chang_xuewen
        // $Date : 2014/1/22 10:30$
        // [BUG]0042086 MODIFY BEGIN   
    	/*if(!poorin200901uv07.getOrganizationCode().equals(poorin200901uv07.getMessage().getOrgCode()))
    	{
    		loggerBS309.error(
					"Message:{},validate:{}",
					poorin200901uv07.toString(),
					"消息头中的医疗机构编码和消息体中的医疗机构编码不同，消息头中的医疗机构编码="
							+ poorin200901uv07.getMessage().getOrgCode()
							+ "，消息体中的医疗机构编码="
							+ poorin200901uv07.getOrganizationCode());
			return false;
    	}*/
    	if(StringUtils.isEmpty(poorin200901uv07.getOrganizationCode())){
    		// $Author :yang_mingjie
        	// $Date : 2014/06/26 10:09$
        	// [BUG]0045630 MODIFY BEGIN 
    		poorin200901uv07.setOrganizationCode(Constants.HOSPITAL_CODE);
    		poorin200901uv07.setOrganizationName(Constants.HOSPITAL_NAME);
    		//[BUG]0045630 MODIFY END
        }
    	// [BUG]0042086 MODIFY END   
    	// $Author:wei_peng
        // $Date:2012/9/21 15:31
        // $[BUG]0009776 ADD BEGIN
        if (Constants.PATIENT_DOMAIN_INPATIENT.equals(poorin200901uv07.getPatientDomain())
            && !StringUtils.isNotNullAll(poorin200901uv07.getWardCode(),
                    poorin200901uv07.getWardName(),
                    poorin200901uv07.getBedNo(),
                    poorin200901uv07.getOrderLids()))
        {
            loggerBS309.error("Message:{},validate:{}",
                    poorin200901uv07.toString(), "住院时，非空字段验证未通过：病区编码："
                        + poorin200901uv07.getWardCode() + "，病区名称："
                        + poorin200901uv07.getWardName() + "，床号："
                        + poorin200901uv07.getBedNo() + "，医嘱号："
                        + poorin200901uv07.getOrderLids());
            return false;
        }
        if (Constants.PATIENT_DOMAIN_OUTPATIENT.equals(poorin200901uv07.getPatientDomain())
            && !StringUtils.isNotNullAll(poorin200901uv07.getRequestNo(),
                    poorin200901uv07.getOrderLid()))
        {
            loggerBS309.error("Message:{},validate:{}",
                    poorin200901uv07.toString(), "门诊时，非空字段验证未通过：输血申请单号："
                        + poorin200901uv07.getRequestNo() + "，取血单号："
                        + poorin200901uv07.getOrderLid());
            return false;
        }
        // $[BUG]0009776 ADD END
        return true;
    }

    /**
     * 关联校验，判断用血申请单是否存在
     * @param poorin200901uv07
     * @return
     */
    @Override
    public boolean checkDependency(POORIN200901UV07 poorin200901uv07,
            boolean flag)
    {
        // 新增标识
        newUpFlag = poorin200901uv07.getNewUpFlag();

        // 取血单号
        orderLid = poorin200901uv07.getOrderLid();

        // 患者本地ID
        String patientLid = poorin200901uv07.getPatientLid();

        // $Author :jin_peng
        // $Date : 2012/08/13 11:23$
        // [BUG]0008712 MODIFY BEGIN
        // 用本地申请单号、域ID、患者本地ID查询用血申请单是否存在
        List<BloodRequest> bloodRequests = bloodSpecialRequestService.selectBloodRequest(
                poorin200901uv07.getRequestNo(),
                poorin200901uv07.getPatientDomain(), patientLid,poorin200901uv07.getOrganizationCode());
        // [BUG]0008712 MODIFY END

        logger.debug("取血单       {}  场合", newUpFlag);
        
        // Author :jia_yanqing
        // Date : 2013/06/27 16:23
        // [BUG]0034273 ADD BEGIN
        // 用血申请单中没有数据，就不能更新和插入
        if (bloodRequests.size() == 0)
        {
            logger.error(
                    "MessageId:{};用血申请单不存在，域ID："
                        + poorin200901uv07.getPatientDomain() + "，申请单号："
                        + poorin200901uv07.getRequestNo() + "，患者本地ID："
                        + poorin200901uv07.getPatientLid(),
                    poorin200901uv07.getMessage().getId());
            if (flag)
            {
                loggerBS309.error("Message:{},checkDependency:{}",
                        poorin200901uv07.toString(), "用血申请单不存在，域ID："
                            + poorin200901uv07.getPatientDomain() + "，申请单号："
                            + poorin200901uv07.getRequestNo() + "，患者本地ID："
                            + poorin200901uv07.getPatientLid());
            }
            return false;
        }
        // 用血申请单中有数据
        else
        {
            logger.debug("用血申请单中存在数据！");
            // 取出唯一一条
            bloodRequest = bloodRequests.get(0);
        }
        // [BUG]0034273 ADD END

        // 如果是 new 判断用血申请单是否存在
        if (Constants.NEW_MESSAGE_FLAG.equals(newUpFlag))
        {
            return true;
        }
        // 如果是 delete 判断取血单是否存在
        else if (Constants.DELETE_MESSAGE_FLAG.equals(newUpFlag))
        {
            return isBloodDeliveryOrderExist(poorin200901uv07, flag);
        }
        else
        {
            logger.error("MessageId:{};错误的消息交互类型：{}",
                    poorin200901uv07.getMessage().getId(), newUpFlag);
            if (flag)
            {
                loggerBS309.error("Message:{},checkDependency:{}",
                        poorin200901uv07.toString(), "错误的消息交互类型：{}" + newUpFlag);
            }
            return false;
        }
    }

    /**
     * 取血单的保存或更新
     * @param poorin200901uv07
     */
    @Transactional
    public void saveOrUpdate(POORIN200901UV07 poorin200901uv07)
    {
        serviceId = poorin200901uv07.getMessage().getServiceId();
        
        // $Author:wei_peng
        // $Date:2012/9/24 15:24
        // $[BUG]0009863 MODIFY BEGIN
        if (Constants.NEW_MESSAGE_FLAG.equals(newUpFlag)
            && Constants.PATIENT_DOMAIN_OUTPATIENT.equals(poorin200901uv07.getPatientDomain()))
        {
            BigDecimal requestSn = bloodRequest.getRequestSn();
            deliveryOrderResult = bloodSpecialRequestService.selectBloodDeliveryOrder(
                    requestSn, orderLid, poorin200901uv07.getPatientDomain(),poorin200901uv07.getOrganizationCode());
        }
        // $[BUG]0009863 MODIFY END

        BloodDeliveryOrder Blooddeliveryorder = getBloodDeliveryOrder(poorin200901uv07);
        logger.debug("取血单       {}  场合", newUpFlag);

        // $Author :jin_peng
        // $Date : 2012/08/27 11:03$
        // [BUG]0009142 MODIFY BEGIN
        // $Author:wei_peng
        // $Date:2012/9/24 15:24
        // $[BUG]0009863 MODIFY BEGIN
        // 新增取血信息（住院时）
        if (Constants.NEW_MESSAGE_FLAG.equals(newUpFlag)
            && Constants.PATIENT_DOMAIN_INPATIENT.equals(poorin200901uv07.getPatientDomain()))
        {
            commonService.save(Blooddeliveryorder);
        }
        // 新增取血信息（门诊时）
        else if (Constants.NEW_MESSAGE_FLAG.equals(newUpFlag)
            && Constants.PATIENT_DOMAIN_OUTPATIENT.equals(poorin200901uv07.getPatientDomain()))
        {
            if (deliveryOrderResult != null && deliveryOrderResult.size() != 0)
            {
                commonService.update(Blooddeliveryorder);
            }
            else
            {
                commonService.save(Blooddeliveryorder);
            }
        }
        // update
        /*
         * else if (Constants.UPDATE_MESSAGE_FLAG.equals(newUpFlag)) {
         * commonService.update(Blooddeliveryorder); }
         */
        // $[BUG]0009863 MODIFY END
        // delete
        else
        {
            // 设置取血单的删除属性信息
            setLogicDeleteBloodDeliveryOrder(Blooddeliveryorder);
            // $Author:chang_xuewen
            // $Date:2013/12/19 15:00
            // $[BUG]0040944 MODIFY BEGIN
            commonService.updatePartially(Blooddeliveryorder, "deleteFlag",
                    "deleteTime", "deleteby","updateTime");
            // $[BUG]0040944 MODIFY END
        }
        // [BUG]0009142 MODIFY END
    }

    /**
     * 将要更新或者插入的数据，封装到实体中
     * @param poorin200901uv07
     * @return  返回封装数据的实体
     */
    private BloodDeliveryOrder getBloodDeliveryOrder(
            POORIN200901UV07 poorin200901uv07)
    {
        // $Author :jin_peng
        // $Date : 2012/08/27 11:03$
        // [BUG]0009142 DELETE BEGIN
        // 取系统时间
        // Date systemDate = DateUtils.getSystemTime();
        // [BUG]0009142 DELETE END

        BloodDeliveryOrder bloodDeliveryOrder = null;
        if (deliveryOrderResult == null || deliveryOrderResult.size() == 0)
        {
            bloodDeliveryOrder = new BloodDeliveryOrder();
            // 用血申请单内部序列号
            bloodDeliveryOrder.setRequestSn(bloodRequest.getRequestSn());
            // $Author :jin_peng
            // $Date : 2012/08/27 11:03$
            // [BUG]0009142 MODIFY BEGIN
            // 创建时间
            bloodDeliveryOrder.setCreateTime(systemTime);
            // [BUG]0009142 MODIFY END
            // 将更新次数设置为1
            bloodDeliveryOrder.setUpdateCount(Constants.INSERT_UPDATE_COUNT);
            
            // 创建者
            bloodDeliveryOrder.setCreateby(serviceId);
        }
        else
        {
            bloodDeliveryOrder = deliveryOrderResult.get(0);
            
            // 修改者
            bloodDeliveryOrder.setUpdateby(serviceId);
        }

        // $Author :jin_peng
        // $Date : 2012/08/13 11:23$
        // [BUG]0008712 MODIFY BEGIN
        // 取血单号
        bloodDeliveryOrder.setOrderLid(orderLid);
        // [BUG]0008712 MODIFY END

        // 患者域ID
        bloodDeliveryOrder.setPatientDomain(poorin200901uv07.getPatientDomain());
        // 患者姓名
        bloodDeliveryOrder.setPatientName(poorin200901uv07.getPatientName());

        // $Author :tong_meng
        // $Date : 2012/6/26 10:33$
        // [BUG]0007418 MODIFY BEGIN
        // 性别代码
        bloodDeliveryOrder.setPatientGenderCode(poorin200901uv07.getPatientGender());
        // [BUG]0007418 MODIFY END

        // $Author :tong_meng
        // $Date : 2012/6/26 10:33$
        // [BUG]0007418 ADD BEGIN
        // 性别名称
        bloodDeliveryOrder.setPatientGenderName(DictionaryCache.getDictionary(
                Constants.DOMAIN_GENDER).get(
                poorin200901uv07.getPatientGender()));
        // [BUG]0007418 ADD END

        // 年龄
        bloodDeliveryOrder.setPatientAge(poorin200901uv07.getPatientAge());

        // $Author :tong_meng
        // $Date : 2012/6/26 10:33$
        // [BUG]0007418 MODIFY BEGIN
        // 科室ID
        bloodDeliveryOrder.setOrderDeptId(poorin200901uv07.getOrderDept());
        // [BUG]0007418 MODIFY END

        // $Author :tong_meng
        // $Date : 2012/6/26 10:33$
        // [BUG]0007418 ADD BEGIN
        // 科室名称
        bloodDeliveryOrder.setOrderDeptName(poorin200901uv07.getOrderDeptName());
        // [BUG]0007418 ADD END

        // $Author :jin_peng
        // $Date : 2012/08/13 11:23$
        // [BUG]0008712 MODIFY BEGIN
        bloodDeliveryOrder.setWardId(poorin200901uv07.getWardCode()); // 设置病区id
        bloodDeliveryOrder.setWardName(poorin200901uv07.getWardName()); // 设置病区名称
        // [BUG]0008712 MODIFY END

        // 床号
        bloodDeliveryOrder.setBedNo(poorin200901uv07.getBedNo());
        // // 临床诊断编号
        // bloodDeliveryOrder.setDiagnoseCode(poorin200901uv07.getDiagnoseCode());
        // // 临床诊断名称
        // bloodDeliveryOrder.setDiagnoseName(poorin200901uv07.getDiagnoseName());

        // $Author :tong_meng
        // $Date : 2012/6/26 10:33$
        // [BUG]0007418 MODIFY BEGIN
        // 输血目的
        bloodDeliveryOrder.setTransfusionReasonCode(poorin200901uv07.getTransfusionReason());
        // [BUG]0007418 MODIFY END

        // $Author :tong_meng
        // $Date : 2012/6/26 10:33$
        // [BUG]0007418 ADD BEGIN
        // 输血名称
        bloodDeliveryOrder.setTransfusionReasonName(poorin200901uv07.getTransfusionReasonName());
        // [BUG]0007418 ADD END

        // $Author :tong_meng
        // $Date : 2012/10/13 10:33$
        // [BUG]0010391 ADD BEGIN
        bloodDeliveryOrder.setBloodDeliveryCount(StringUtils.strToBigDecimal(
                poorin200901uv07.getOrderTimes(), "pe"));
        // [BUG]0010391 ADD END

        // 输血成分名称
        bloodDeliveryOrder.setTransfusionComponents(poorin200901uv07.getTransfusionComponents());
        // 取血时间
        bloodDeliveryOrder.setDeliveryTime(DateUtils.stringToDate(poorin200901uv07.getDeliveryTime()));
        // 取血数量
        bloodDeliveryOrder.setDeliveryQuality(StringUtils.strToBigDecimal(
                poorin200901uv07.getQuantity(), "取血数"));
        // 取血单位
        bloodDeliveryOrder.setDeliveryUnit(poorin200901uv07.getUnit());
        // 申请医生ID
        bloodDeliveryOrder.setRequestDoctorId(poorin200901uv07.getRequestDoctorId());
        // 申请医生姓名
        bloodDeliveryOrder.setRequestDoctorName(poorin200901uv07.getRequestDoctorName());
        // 送单人ID
        bloodDeliveryOrder.setApplicationSenderId(poorin200901uv07.getApplicationDenderId());
        // 送单人姓名
        bloodDeliveryOrder.setAppSenderName(poorin200901uv07.getApplicationDenderName());
        // 接收人ID
        bloodDeliveryOrder.setApplicationReceiverId(poorin200901uv07.getApplicationReceiverId());
        // 接收人姓名
        bloodDeliveryOrder.setAppReceiverName(poorin200901uv07.getApplicationReceiverName());
        // 医疗机构编码
        bloodDeliveryOrder.setOrgCode(poorin200901uv07.getOrganizationCode());
        // 医疗机构名称
        bloodDeliveryOrder.setOrgName(poorin200901uv07.getOrganizationName());
        // $Author :jin_peng
        // $Date : 2012/08/27 11:03$
        // [BUG]0009142 MODIFY BEGIN
        // 更新时间
        bloodDeliveryOrder.setUpdateTime(systemTime);
        // [BUG]0009142 MODIFY END
        // 删除标志
        bloodDeliveryOrder.setDeleteFlag(Constants.NO_DELETE_FLAG);
        return bloodDeliveryOrder;
    }

    // $Author :jin_peng
    // $Date : 2012/08/27 11:03$
    // [BUG]0009142 ADD BEGIN
    /**
     * 逻辑删除取血单信息
     * @param deliveryOrderResult 待逻辑删除的取血单信息对象
     */
    private void setLogicDeleteBloodDeliveryOrder(
            BloodDeliveryOrder deliveryOrderResult)
    {
        // 设置删除标记为已删除
        deliveryOrderResult.setDeleteFlag(Constants.DELETE_FLAG);

        // 设置删除时间
        deliveryOrderResult.setDeleteTime(systemTime);
        
        // 设置删除者
        deliveryOrderResult.setDeleteby(serviceId);
    }

    // [BUG]0009142 ADD END

    /**
     * 更新,删除操作时，检查取血单中的数据是否存在
     * @param bloodRequests
     * @param poorin200901uv07
     * @return
     */
    private boolean isBloodDeliveryOrderExist(
            POORIN200901UV07 poorin200901uv07, boolean flag)
    {
        BigDecimal requestSn = bloodRequest.getRequestSn();

        // $Author :jin_peng
        // $Date : 2012/08/13 11:23$
        // [BUG]0008712 MODIFY BEGIN
        // 查询取血单中的数据
        deliveryOrderResult = bloodSpecialRequestService.selectBloodDeliveryOrder(
                requestSn, orderLid, poorin200901uv07.getPatientDomain(),poorin200901uv07.getOrganizationCode());
        // [BUG]0008712 MODIFY END

        // 不存在
        if (deliveryOrderResult.size() == 0)
        {
            logger.error(
                    "MessageId:{};取血单不存在，域ID："
                        + poorin200901uv07.getPatientDomain() + "，取血单号编号："
                        + orderLid + "，输血申请单内部序列号：" + requestSn,
                    poorin200901uv07.getMessage().getId());
            if (flag)
            {
                loggerBS309.error("Message:{},checkDependency:{}",
                        poorin200901uv07.toString(), "取血单不存在，域ID："
                            + poorin200901uv07.getPatientDomain() + "，取血单号编号："
                            + orderLid + "，输血申请单内部序列号：" + requestSn);
            }
            return false;
        }
        // 存在
        else
        {
            logger.debug("取血单中存在数据！");
            return true;
        }
    }

    // Author :jia_yanqing
    // Date : 2013/06/27 16:23
    // [BUG]0034273 DELETE BEGIN
    /**
     * 检查用血申请单中是否存在数据
     * @param bloodRequests
     * @return
     */
    //private boolean isBloodRequestExist(List<BloodRequest> bloodRequests,
    //        POORIN200901UV07 poorin200901uv07, boolean flag)
    //{
    //    // 用血申请单中没有数据，就不能更新和插入
    //    if (bloodRequests.size() == 0)
    //    {
    //        logger.error(
    //                "MessageId:{};用血申请单不存在，域ID："
    //                    + poorin200901uv07.getPatientDomain() + "，申请单号："
    //                    + poorin200901uv07.getRequestNo() + "，患者本地ID："
    //                    + poorin200901uv07.getPatientLid(),
    //                poorin200901uv07.getMessage().getId());
    //        if (flag)
    //        {
    //            loggerBS309.error("Message:{},checkDependency:{}",
    //                    poorin200901uv07.toString(), "用血申请单不存在，域ID："
    //                        + poorin200901uv07.getPatientDomain() + "，申请单号："
    //                        + poorin200901uv07.getRequestNo() + "，患者本地ID："
    //                        + poorin200901uv07.getPatientLid());
    //        }
    //        return false;
    //    }
    //    // 用血申请单中有数据
    //    else
    //    {
    //        logger.debug("用血申请单中存在数据！");
    //        // 取出唯一一条
    //        bloodRequest = bloodRequests.get(0);
    //        return true;
    //    }
    //}
    // [BUG]0034273 DELETE END

}
