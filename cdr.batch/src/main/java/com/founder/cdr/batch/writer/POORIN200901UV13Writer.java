package com.founder.cdr.batch.writer;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.founder.cdr.batch.writer.common.CommonWriterUtils;
import com.founder.cdr.cache.DictionaryCache;
import com.founder.cdr.core.Constants;
import com.founder.cdr.entity.ConsultationOrder;
import com.founder.cdr.entity.GeneralOrder;
import com.founder.cdr.entity.MedicalVisit;
import com.founder.cdr.entity.MedicationOrder;
import com.founder.cdr.entity.PatientDoctor;
import com.founder.cdr.hl7.dto.OrderItems;
import com.founder.cdr.hl7.dto.OtherOrder;
import com.founder.cdr.hl7.dto.poorin200901uv13.POORIN200901UV13;
import com.founder.cdr.service.CommonService;
import com.founder.cdr.service.GeneralOrderService;
import com.founder.cdr.util.DataMigrationUtils;
import com.founder.cdr.util.DateUtils;
import com.founder.cdr.util.DictionaryUtils;
import com.founder.cdr.util.StringUtils;

/**
 * 其他医嘱
 * @author tongmeng
 *
 */
@Component(value = "poorin200901uv13Writer")
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class POORIN200901UV13Writer implements BusinessWriter<POORIN200901UV13>
{
    private static Logger logger = LoggerFactory.getLogger(POORIN200901UV13Writer.class);

    private static Logger loggerBS305 = LoggerFactory.getLogger("BS305");

    @Autowired
    private GeneralOrderService generalOrderService;

    @Autowired
    private CommonService commonService;

    @Autowired
    private CommonWriterUtils commonWriterUtils;

    // 新增标志
    private String newUpFlag;

    // 域ID
    private String patientDomain;

    // 患者本地ID
    private String patientLid;

    // 就诊内部序列号
    private BigDecimal visitSn;

    // 患者内部序列号
    private BigDecimal patientSn;

    // 消息中的数据
    private OtherOrder otherOrder;

    // 消息中循环节点集合
    private List<OrderItems> orderItemList;

    // 要更新的医嘱集合
     private List<Object> generalOrders = new ArrayList<Object>(); ;
     
     // 待更新的诊疗记录
 	private List<Object> generalOrderListForUpdate = new ArrayList<Object>();

 	// 待插入的诊疗记录
 	private List<Object> generalOrderListForInsert = new ArrayList<Object>();

    // $Author :jin_peng
    // $Date:2013/02/06 13:38
    // [BUG]0013909 ADD BEGIN
    // 过滤已存在的医生集合
    private List<String> filterPatientDoctorList = new ArrayList<String>();

    // [BUG]0013909 ADD END

    // $Author:wei_peng
    // $Date:2012/11/06 10:46
    // [BUG]0011030 ADD BEGIN
    // 患者和医生关联记录结合
    private List<PatientDoctor> patientDoctorList = new ArrayList<PatientDoctor>();

    // [BUG]0011030 ADD END
    // $Author:duxiaolan
    // $Date:2014/11/24
    private MedicalVisit medicalVisit;
    
    @Override
    public boolean validate(POORIN200901UV13 t)
    {
    	// $Author :chang_xuewen
        // $Date : 2014/1/22 10:30$
        // [BUG]0042086 MODIFY BEGIN
        String headOrgCode = t.getMessage().getOrgCode();
        String orgCode = t.getOrgCode();

        /*if (!headOrgCode.equals(orgCode))
        {
            logger.error("{}", "消息体与消息头中医疗机构代码不相同。orgCode = " + orgCode
                + "; headOrgCode = " + headOrgCode);

             loggerBS305.error("Message:{},validate:{}", t.toString(),
                    "消息体与消息头中医疗机构代码不相同。orgCode = " + orgCode
                        + "; headOrgCode = " + headOrgCode);

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
        return true;
    }

    /**
     * 新增时关联就诊表 更新时关联其他医嘱表
     */
    @Override
    public boolean checkDependency(POORIN200901UV13 t, boolean flag)
    {
        newUpFlag = t.getNewUpFlag();
        // 取出唯一一条数据
        otherOrder = t.getOtherOrder().get(0);
        // 域ID
        patientDomain = otherOrder.getPatientDomain();

        orderItemList = new ArrayList<OrderItems>();
        for(OtherOrder oo : t.getOtherOrder()){
        	for(OrderItems oi : oo.getOrderItems()){
        		orderItemList.add(oi);
        	}
        }
//        orderItemList = otherOrder.getOrderItems();
        // $Author :tongmeng
        // $Date : 2012/5/9 13:45$
        // [BUG]0006657 MODIFY BEGIN
        // 患者本地ID
        patientLid = otherOrder.getPatientLid();
        logger.debug("患者域ID  {}  ,患者本地ID  {}  ", patientDomain, patientLid);
        // [BUG]0006657 MODIFY END
        // 就诊信息验证
        if(!isMedicalVisitExist(t, flag)){
     	   return false;
        }
        // new 时
        logger.debug("消息    {}  场合", newUpFlag);
        if (isNewMessage())
        {
        	int count;
        	if((count = selectExistOrders()) > 0){
        		logger.info("消息新增场合，共{}条记录，数据库中存在{}条记录", orderItemList.size(), count);
        	}
        	
            // $Author:jia_yanqing
            // $Date:2012/7/26 13:30$
            // [BUG]0008216 ADD BEIGN
            String parentOrderLid = null;
            MedicationOrder medicationOrder;
            // 检查与父医嘱的关联关系
            for (OrderItems orderItem : orderItemList)
            {
                parentOrderLid = orderItem.getParentOrderLid();
                if (parentOrderLid != null && !"".equals(parentOrderLid))
                {
                    medicationOrder = commonService.checkParentOrder(
                            patientDomain, patientLid, parentOrderLid,
                            t.getOrgCode(), medicalVisit.getVisitSn());
                    if (medicationOrder == null)
                    {
                        logger.error(
                                "MessageId:{};其它医嘱消息新增场合,关联的父医嘱不存在,父医嘱号 :"
                                    + parentOrderLid + ",域ID：" + patientDomain
                                    + ",患者本地ID：" + patientLid + "; orgCode："
                                    + t.getOrgCode() + "; visitSn："+medicalVisit.getVisitSn(), t.getMessage().getId());
                        if (flag)
                        {
                            loggerBS305.error(
                                    "Message:{},checkDependency:{}",
                                    t.toString(),
                                    "其它医嘱消息新增场合,关联的父医嘱不存在,父医嘱号 :"
                                        + parentOrderLid + ",域ID："
                                        + patientDomain + ",患者本地ID："
                                        + patientLid + "; orgCode："
                                        + t.getOrgCode() + "visitSn："+medicalVisit.getVisitSn());
                        }
                        return false;
                    }
                }
            }
            // [BUG]0008216 ADD END
            return true;
        }
        // 如果更新消息，检查护理医嘱是否存在
		else if (isUpdateMessage()) {
			return isOtherOrderExist(); 
		}
        else
        {
            logger.error("MessageId:{};错误的消息交互类型：{}", t.getMessage().getId(),
                    newUpFlag);
            if (flag)
            {
                loggerBS305.error("Message:{},checkDependency:{}",
                        t.toString(), "错误的消息交互类型：" + newUpFlag);
            }
            return false;
        }
    }

	/**
	 * 消息类型为新增时，先查询数据库中有没有对应的记录，将已存在的记录放入generalOrders中
	 * @author yu_yzh
	 * @return 返回数据库中存在的医嘱记录数
	 * */
	private int selectExistOrders(){
		int existOrderCount = 0;
		Object generalOrder = null;
		for (OrderItems orderItem : orderItemList) {
			// 确定要更新的每一条医嘱都能找到记录，否则不能更新 generalOrder =
			generalOrder = generalOrderService.selectGeneralOrder(
					patientDomain, patientLid, orderItem.getOrderLid(), 
					/*orderItem.getOrderSeqnum()港大没有医嘱序号*/ null,
					orderItem.getOrderTypeId());
			if (generalOrder != null) {
				generalOrders.add(generalOrder);
				existOrderCount++;
			}
		}
		return existOrderCount;
	}
    
    /**
     * 关联就诊记录
     * 
     * @return
     */
    private boolean isMedicalVisitExist(POORIN200901UV13 t, boolean flag)
    {
        // 查询就诊表
        medicalVisit = commonService.mediVisit(patientDomain,
                patientLid, otherOrder.getVisitTimes(),
                t.getOrgCode(), otherOrder.getVisitOrdNo(), otherOrder.getVisitType());
        // 为空！
        if (medicalVisit == null)
        {
            logger.error(
                    "MessageId:{};关联就诊的记录不存在，患者本地ID：" + patientLid + "，域ID："
                        + patientDomain + "，就诊次数：" + otherOrder.getVisitTimes()
                        + "; orgCode = " + t.getOrgCode() + "，就诊流水号："+ otherOrder.getVisitOrdNo()
                        +"，就诊类型："+ otherOrder.getVisitType(),
                    t.getMessage().getId());
            if (flag)
            {
                loggerBS305.error(
                        "Message:{},checkDependency:{}",
                        t.toString(),
                        "关联就诊的记录不存在，患者本地ID：" + patientLid + "，域ID："
                            + patientDomain + "，就诊次数："
                            + otherOrder.getVisitTimes() + "; orgCode = "
                            + t.getOrgCode()+ "，就诊流水号："+ otherOrder.getVisitOrdNo()
                            +"，就诊类型："+ otherOrder.getVisitType());
            }
            return false;
        }
        // 不为空，取出就诊内部序列号和患者内部序列号
        visitSn = medicalVisit.getVisitSn();
        patientSn = medicalVisit.getPatientSn();
        logger.debug("关联就诊的记录存在，就这内部序列号为    {}，患者内部序列号为     {}", visitSn,
                patientSn);
        return true;
    }

    /**
     * 查询循环节点中的消息是否存在
     * @return
     */
    /*
     * private boolean isOrderItemListExsit() { orderItemList =
     * otherOrder.getOrderItems(); // 循环节点的数据不存在 if (orderItemList == null ||
     * orderItemList.size() == 0) { logger.error("其他医嘱不存在！"); return false; } //
     * 存在，将每一条数据中的本地医嘱号取出 for (OrderItems orderItem : orderItemList)
     * orderLidSet.add(orderItem.getOrderLid()); return true; }
     */

    /**
	 * 查找对应的待更新的其他医嘱记录
	 * 
	 * @param treatmentItem
	 *            消息中一条其他医嘱信息
	 * @return 查找到对应待更新的那条其他医嘱
	 */
	private GeneralOrder getGeneralOrder(OrderItems orderItem) {
		GeneralOrder generalOrder = null;
		ConsultationOrder consultationOrder = null;
		for (Object obj : generalOrders) {
			// 通过医嘱号判断是否该条记录为待更新的医嘱记录
			if(Constants.CONSULTATION_ORDER.equals(orderItem.getOrderTypeId())){
				consultationOrder = (ConsultationOrder)obj;
				if (consultationOrder.getOrderLid() 
						.equals(orderItem.getOrderLid()) 
						// 港大没有医嘱序号
						/*&& consultationOrder.getOrderSeqnum().toString().equals(orderItem.getOrderSeqnum())*/) {
					generalOrder = new GeneralOrder();
					BeanUtils.copyProperties(consultationOrder,generalOrder);
					return generalOrder;
				}
			}else{
				generalOrder = (GeneralOrder)obj;
				if (generalOrder.getOrderLid() 
						.equals(orderItem.getOrderLid()) 
						// 港大没有医嘱序号
						/*&& generalOrder.getOrderSeqnum().toString().equals(orderItem.getOrderSeqnum())*/) {
					return generalOrder;
				}
			}
			
		}
		return null;
	}
    /**
     * 更新时，关联其他医嘱是否存在数据 (域ID，患者本地ID，本地医嘱号)
     * 
     * @return
     */    
     private boolean isOtherOrderExist() {
    	 Object generalOrder = null;
         for (OrderItems orderItem: orderItemList) { 
        	 // 确定要更新的每一条医嘱都能找到记录，否则不能更新 generalOrder =
        	 generalOrder = generalOrderService.selectGeneralOrder( patientDomain, patientLid,
        			 orderItem.getOrderLid(),/*orderItem.getOrderSeqnum()港大没有医嘱序号*/ null,orderItem.getOrderTypeId()); 
        	 if (generalOrder == null) {
        		 logger.error("更新时关联的医嘱不存在！医嘱号为: " + orderItem.getOrderLid()
					 /* + " 医嘱序号为：" + orderItem.getOrderSeqnum()*/); 
        		 return false; 
        	 }
        	 generalOrders.add(generalOrder); 
         } 
         return true; 
     }
      

    @Override
    @Transactional
    public void saveOrUpdate(POORIN200901UV13 t)
    {
        getGeneralOrderList(t);
        /*
         * if (Constants.NEW_MESSAGE_FLAG.equals(newUpFlag)) {
         */
        // 新建
        generalOrderService.saveGeneralOrderList(generalOrderListForInsert);
        // 更新
        generalOrderService.updateGeneralOederList(generalOrderListForUpdate);
        // $Author:wei_peng
        // $Date:2012/11/06 11:46
        // [BUG]0011030 ADD BEGIN
        for (PatientDoctor patientDoctor : patientDoctorList)
        {
            commonService.save(patientDoctor);
        }
        // [BUG]0011030 ADD END
        /* } */
        /*
         * else generalOrderService.updateGeneralOederList(generalOrderList);
         */
    }

    /**
     * 要更新或者新增的其他医嘱的集合
     * 
     * @return
     */
    private void getGeneralOrderList(POORIN200901UV13 t)
    {
        GeneralOrder generalOrder = null;
        GeneralOrder generalOrderUpd = null;
        ConsultationOrder consultationOrder = null;
        Date systemDate = DateUtils.getSystemTime();
        for (OrderItems orderItem : orderItemList)
        {
        	generalOrderUpd = getGeneralOrder(orderItem);	
        	if(generalOrderUpd != null){
        		generalOrder = generalOrderUpd;
        	}else{
        		generalOrder = new GeneralOrder();
        		generalOrder.setUpdateCount(Constants.INSERT_UPDATE_COUNT);
        	}
            generalOrder.setVisitSn(visitSn);
            generalOrder.setPatientSn(patientSn);
            generalOrder.setPatientDomain(patientDomain);
            generalOrder.setPatientLid(patientLid);
            generalOrder.setOrderLid(orderItem.getOrderLid());

            // $Author:jia_yanqing
            // $Date:2012/7/26 17:30$
            // [BUG]0008216 ADD BEIGN
            BigDecimal parentSn = null;
            // 得到关联的父医嘱记录
            MedicationOrder medicationParentOrder = commonService.checkParentOrder(
                    patientDomain, patientLid, orderItem.getParentOrderLid(),
                    t.getOrgCode(), medicalVisit.getVisitSn());
            if (medicationParentOrder != null)
            {
                parentSn = medicationParentOrder.getOrderSn();
            }
            // 父医嘱内部序列号
            generalOrder.setParentOrderSn(parentSn);
            // [BUG]0008216 ADD END

            // $Author :wei_peng
            // $Date : 2012/6/18 15:08$
            // [BUG]0006906 ADD BEGIN
            // 创建时间
            generalOrder.setCreateTime(systemDate);
            // 删除标志
            generalOrder.setDeleteFlag(Constants.NO_DELETE_FLAG);
            // 删除时间
            // generalOrder.setDeleteTime(null);
            // [BUG]0006906 ADD END

            // $Author:wei_peng
            // $Date:2012/11/06 11:46
            // [BUG]0011030 ADD BEGIN
            if (orderItem.getOrderPersonId() != null
                && !orderItem.getOrderPersonId().isEmpty())
            {
                PatientDoctor patientDoctor = commonWriterUtils.getPatientDoctor(
                        visitSn, patientSn, orderItem.getOrderPersonId(),
                        orderItem.getOrderPersonName(), systemDate);
                if (patientDoctor != null)
                {
                    // $Author:jin_peng
                    // $Date:2013/02/06 13:38
                    // [BUG]0013909 MODIFY BEGIN
                    // 如果该医生未被处理则进行添加操作
                    if (!commonWriterUtils.isExistsPatientDoctor(
                            filterPatientDoctorList, visitSn, patientSn,
                            orderItem.getOrderPersonId()))
                    {
                    	// $Author :chang_xuewen
                        // $Date : 2013/08/31 16:00$
                        // [BUG]0036757 MODIFY BEGIN
                    	// 创建人
                    	patientDoctor.setCreateby(DataMigrationUtils.getDataMigration() + t.getMessage().getServiceId());
                        patientDoctorList.add(patientDoctor);
                        // [BUG]0036757 MODIFY END
                    }

                    // [BUG]0013909 MODIFY END
                }
            }
            // [BUG]0011030 ADD END

            // $Author:wei_peng
            // $Date:2012/11/13 17:33
            // [BUG]0011421 ADD BEGIN
            // 医嘱交费状态编码
            generalOrder.setChargeStatusCode(Constants.CHARGE_STATUS_NOTCHARGE);
            // 医嘱交费状态名称
            generalOrder.setChargeStatusName(DictionaryCache.getDictionary(
                    Constants.ORDER_CHARGE_STATUS).get(
                    Constants.CHARGE_STATUS_NOTCHARGE));
            // [BUG]0011421 ADD END
            /*
             * } else { generalOrder = generalOrders.get(i); }
             */
            // 更新时间
            generalOrder.setUpdateTime(systemDate);

            // $Author :tong_meng
            // $Date : 2012/6/26 10:33$
            // [BUG]0007418 MODIFY BEGIN
            // 医嘱类型编码
            generalOrder.setOrderTypeCode(orderItem.getOrderTypeId());
            // [BUG]0007418 MODIFY BEGIN

            // $Author :tong_meng
            // $Date : 2012/6/26 10:33$
            // [BUG]0007418 ADD BEGIN
            // 医嘱类型名称
            
            generalOrder.setOrderTypeName(DictionaryUtils.getNameFromDictionary(
            		Constants.DOMAIN_ORDER_TYPE, orderItem.getOrderTypeId(), orderItem.getOrderTypeName()));
            // [BUG]0007418 ADD END

            // $Author :tongmeng
            // $Date : 2012/5/29 13:45$
            // [BUG]0006721 MODIFY BEGIN
            // 医嘱编码
            generalOrder.setOrderCode(orderItem.getOrderCode());
            // [BUG]0006721 MODIFY END
            
            // 医嘱名称
            generalOrder.setOrderName(DictionaryUtils.getNameFromDictionary(
            		Constants.DOMAIN_ORDER, orderItem.getOrderCode(), orderItem.getOrderName()));
            // 医嘱执行频率编码
            generalOrder.setExecFreqCode(orderItem.getExecFreqCode());
            // 医嘱执行频率名称
            generalOrder.setExecFreqName(DictionaryUtils.getNameFromDictionary(
            		Constants.DOMAIN_MEDICINE_FREQ, orderItem.getExecFreqCode()));
            
            // $Author :tong_meng
            // $Date : 2012/6/26 10:33$
            // [BUG]0007418 MODIFY BEGIN
            // 下医嘱科室ID
            generalOrder.setOrderDeptId(orderItem.getOrderDeptNo());
            // [BUG]0007418 MODIFY BEGIN

            // $Author :tong_meng
            // $Date : 2012/6/26 10:33$
            // [BUG]0007418 ADD BEGIN
            // 下医嘱科室名称
            generalOrder.setOrderDeptName(DictionaryUtils.getNameFromDictionary(
            		Constants.DOMAIN_DEPARTMENT, orderItem.getOrderDeptNo(), orderItem.getOrderDeptName()));
            // [BUG]0007418 ADD END

            // 下医嘱人ID
            generalOrder.setOrderPersonId(orderItem.getOrderPersonId());
            // 下医嘱人姓名
            generalOrder.setOrderPersonName(DictionaryUtils.getNameFromDictionary(
            		Constants.DOMAIN_STAFF, orderItem.getOrderPersonId(), orderItem.getOrderPersonName()));
            // 医嘱开嘱时间
            generalOrder.setInputTime(DateUtils.stringToDate(orderItem.getOrderTime()));
            
            // $Author :tong_meng
            // $Date : 2012/5/29 13:45$
            // [BUG]0006721 MODIFY BEGIN
            // 医嘱确认人ID(医生)
            generalOrder.setDoctorConfirmPersonId(orderItem.getDoctorConfirmPerson());
            
            // 医嘱确认人姓名（医生）
            generalOrder.setDoctorConfirmPersonName(DictionaryUtils.getNameFromDictionary(
            		Constants.DOMAIN_STAFF, orderItem.getDoctorConfirmPerson(), orderItem.getDoctorConfirmPersonName()));
            // 医嘱确认时间（医生）
            generalOrder.setDoctorConfirmTime(DateUtils.stringToDate(orderItem.getDoctorConfirmTime()));
            // [BUG]0006721 MODIFY END

            // $Author :tongmeng
            // $Date : 2012/5/29 13:45$
            // [BUG]0006721 MODIFY BEGIN
            // 医嘱确认人ID(护士)
            generalOrder.setNurseConfirmPersonId(otherOrder.getNurseConfirmPerson());
            // 医嘱确认人姓名（护士）
            generalOrder.setNurseConfirmPersonName(otherOrder.getNurseConfirmPersonName());
            // 确认时间（护士）
            generalOrder.setNurseConfirmTime(DateUtils.stringToDate(otherOrder.getNurseConfirmTime()));
            // [BUG]0006721 MODIFY END

            // $Author :tong_meng
            // $Date : 2012/6/26 10:33$
            // [BUG]0007418 MODIFY BEGIN
            // 医嘱执行科室ID
            generalOrder.setExecDeptId(orderItem.getExcuOrderDeptId());
            // [BUG]0007418 MODIFY END

            // $Author :tong_meng
            // $Date : 2012/6/26 10:33$
            // [BUG]0007418 MODIFY BEGIN
            // 医嘱执行科室 名称
            generalOrder.setExecDeptName(DictionaryUtils.getNameFromDictionary(
            		Constants.DOMAIN_DEPARTMENT, orderItem.getExcuOrderDeptId(), orderItem.getExcuOrderDeptName()));
            // [BUG]0007418 MODIFY END

            // $Author :tongmeng
            // $Date : 2012/5/29 13:45$
            // [BUG]0006721 MODIFY BEGIN
            // 描述
            generalOrder.setDescription(orderItem.getOrderRemark());
            // [BUG]0006721 MODIFY END

            // 数量
            generalOrder.setQuantity(StringUtils.strToBigDecimal(
            		orderItem.getQuantity(), "数量"));
            // 单位
            generalOrder.setQuantityUnit(orderItem.getUnit());
            
            // $Author :tong_meng
            // $Date : 2012/6/26 10:33$
            // [BUG]0007418 MODIFY BEGIN

            // $Author: wei_peng
            // $Date: 2012/6/18 19:18$
            // [BUG]0007095 ADD BEGIN
            // 病区编码
            generalOrder.setWardId(otherOrder.getWardNo());
            // [BUG]0007095 END BEGIN

            // [BUG]0007418 MODIFY END

            // $Author :tong_meng
            // $Date : 2012/6/26 10:33$
            // [BUG]0007418 ADD BEGIN
            // 病区名称
            generalOrder.setWardName(DictionaryUtils.getNameFromDictionary(
            		Constants.DOMAIN_WARD, otherOrder.getWardNo(), otherOrder.getWardName()));
            // [BUG]0007418 ADD END

            // $Author :wei_peng
            // $Date : 2012/6/18 15:08$
            // [BUG]0006906 DELETE BEGIN
            // 预定执行时间
            // generalOrder.setPlanExecTime(null);
            // [BUG]0006906 DELETE END

            // $Author:jia_yanqing
            // $Date:2012/7/26 17:30$
            // [BUG]0008216 ADD BEIGN
            // 互斥医嘱类型编码
            generalOrder.setMutexesOrderTypeCode(orderItem.getMutexesOrderTypeCode());
            // 互斥医嘱类型名称
            generalOrder.setMutexesOrderTypeName(orderItem.getMutexesOrderTypeName());
            // [BUG]0008216 ADD END

            // Author :jin_peng
            // Date : 2012/11/14 14:56
            // [BUG]0011478 MODIFY BEGIN
            String orderStatus = null;
            String orderStatusName = null;

            /*
             * Author: yu_yzh
             * Date: 2015/10/22 17:24
             * 港大门诊住院医嘱开立都为已下达
             * MODIFY BEGIN
             * */
/*            // 门诊医嘱状态为已下达
            if (Constants.PATIENT_DOMAIN_OUTPATIENT.equals(patientDomain))
            {
                orderStatus = Constants.ORDER_STATUS_ISSUE;

                orderStatusName = DictionaryCache.getDictionary(
                        Constants.DOMAIN_ORDER_STATUS).get(
                        Constants.ORDER_STATUS_ISSUE);
            }
            // 住院医嘱状态为已确认
            else
            {
                orderStatus = Constants.ORDER_STATUS_VALIDATED;

                orderStatusName = DictionaryCache.getDictionary(
                        Constants.DOMAIN_ORDER_STATUS).get(
                        Constants.ORDER_STATUS_VALIDATED);
            }*/
            orderStatus = Constants.ORDER_STATUS_ISSUE;
            orderStatusName = DictionaryCache.getDictionary(
                    Constants.DOMAIN_ORDER_STATUS).get(
                    Constants.ORDER_STATUS_ISSUE);
            /*
             * 港大门诊住院医嘱开立都为已下达
             * MODIFY END
             * */
            
            // Author:jia_yanqing
            // Date:2012/10/22 17:33
            // [BUG]0010686 ADD BEGIN
            // 医嘱状态
            generalOrder.setOrderStatusCode(orderStatus);
            // 医嘱状态名称
            generalOrder.setOrderStatusName(orderStatusName);
            // [BUG]0010686 ADD END
            // [BUG]0011478 MODIFY END

            // $Author :tong_meng
            // $Date : 2013/7/31 15:50$
            // [BUG]0035541 MODIFY BEGIN
            // $Author:jia_yanqing
            // $Date:2012/9/1 10:30$
            // [BUG]0008514 ADD BEIGN
            // 是否加急
            generalOrder.setUrgentFlag(StringUtils.getConversionValue(orderItem.getUrgentFlag()));
            // [BUG]0008514 ADD END
            // [BUG]0035541 MODIFY END

            // 长期、临时标识
            String usage = orderItem.getUsage();
            generalOrder.setUsage(Constants.LONG_TERM_FLAG.equals(usage) ? 
            		Constants.LONG_TERM_FLAG : Constants.TEMPORARY_FLAG);
            
            // $Author :jin_peng
            // $Date : 2013/08/08 13:52$
            // [BUG]0035952 ADD BEGIN
            // 医嘱开始时间(下医嘱时填写的预计开始时间)
            generalOrder.setPlanExecTime(DateUtils.stringToDate(orderItem.getOrderStartTime()));
            
            // 医嘱停止时间(下医嘱时填写的预计停止时间)
            generalOrder.setOrderEndTime(DateUtils.stringToDate(orderItem.getOrderEndTime()));
            
            // [BUG]0035952 ADD END
            
            // $Author :jin_peng
            // $Date : 2013/08/09 11:02$
            // [BUG]0035952 ADD BEGIN
            // 是否适应
            generalOrder.setAdaptiveFlag(StringUtils.getConversionValue(orderItem.getAdaptiveFlag()));
            
            // 是否药观
            generalOrder.setMedViewFlag(StringUtils.getConversionValue(orderItem.getMedViewFlag()));
            
            // 是否皮试
            generalOrder.setSkinTestFlag(StringUtils.getConversionValue(orderItem.getSkinTestFlag()));
            
            // [BUG]0035952 ADD END
            
            // $Author:wei_peng
            // $Date:2013/8/12 12:55
            // [BUG]0036092 ADD BEGIN
            // 床号
            generalOrder.setBedNo(otherOrder.getBedNo());
            // 嘱托
            generalOrder.setComments(orderItem.getComments());
            // [BUG]0036092 ADD END
            // $Author :chang_xuewen
            // $Date : 2013/08/31 16:00$
            // [BUG]0036757 MODIFY BEGIN
            // 增加人
            generalOrder.setCreateby(DataMigrationUtils.getDataMigration() + t.getMessage().getServiceId());
            // [BUG]0036757 MODIFY END

            generalOrder.setOrgCode(t.getOrgCode());
            generalOrder.setOrgName(t.getOrgName());
          
            // $Author :du_xiaolan
            // $Date : 2014/11/24
            // jiangsu
            // 医嘱描述
            generalOrder.setOrderDescribe(otherOrder.getOrderDescibe());
            // 港大没有医嘱序号
//            generalOrder.setOrderSeqnum(Integer.valueOf(orderItem.getOrderSeqnum()));
            // 医嘱组套编码
            generalOrder.setOrderSetCode(otherOrder.getOrderSetCode());

            // 会诊医嘱时，保存到会诊医嘱表。其他保存到其他医嘱表
            if(Constants.CONSULTATION_ORDER.equals(orderItem.getOrderTypeId())){
            	consultationOrder =  new ConsultationOrder();
            	BeanUtils.copyProperties(generalOrder, consultationOrder);
            	 // 医嘱开嘱时间
            	consultationOrder.setOrderTime(DateUtils.stringToDate(orderItem.getOrderTime()));
            	 // 医嘱确认人ID(医生)
            	consultationOrder.setConfirmPersonId(orderItem.getDoctorConfirmPerson());
                // 医嘱确认人姓名（医生）
            	consultationOrder.setConfirmPersonName(orderItem.getDoctorConfirmPersonName());
                // 医嘱确认时间（医生）
            	consultationOrder.setConfirmTime(DateUtils.stringToDate(orderItem.getDoctorConfirmTime()));
            	if(generalOrderUpd != null){
            		// 更新人
            		consultationOrder.setUpdateby(t.getMessage().getServiceId());
            		generalOrderListForUpdate.add(consultationOrder);
            	}else{
            		generalOrderListForInsert.add(consultationOrder);
            	}
            	
        	}else{
        		if(generalOrderUpd != null){
            		// 更新人
        			generalOrder.setUpdateby(t.getMessage().getServiceId());
            		generalOrderListForUpdate.add(generalOrder);
            	}else{
            		generalOrderListForInsert.add(generalOrder);
            	}
        	}
            
        }
    }
    
    private boolean isNewMessage(){
    	return Constants.NEW_MESSAGE_FLAG.equals(newUpFlag)
    			|| Constants.V2_NEW_MESSAGE_FLAG.equals(newUpFlag);
    }
        
    private boolean isUpdateMessage(){
    	return Constants.UPDATE_MESSAGE_FLAG.equals(newUpFlag)
    			|| Constants.V2_UPDATE_MESSAGE_FLAG.equals(newUpFlag);
    }
}
