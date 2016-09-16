package com.founder.cdr.batch.writer;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.bouncycastle.asn1.cmp.GenRepContent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.founder.cdr.cache.DictionaryCache;
import com.founder.cdr.core.Constants;
import com.founder.cdr.entity.BloodRequest;
import com.founder.cdr.entity.CareOrder;
import com.founder.cdr.entity.ConsultationOrder;
import com.founder.cdr.entity.ExaminationOrder;
import com.founder.cdr.entity.ExaminationResult;
import com.founder.cdr.entity.GeneralOrder;
import com.founder.cdr.entity.LabOrder;
import com.founder.cdr.entity.LabOrderLabResult;
import com.founder.cdr.entity.LabResult;
import com.founder.cdr.entity.MedicalVisit;
import com.founder.cdr.entity.MedicationOrder;
import com.founder.cdr.entity.NursingOperation;
import com.founder.cdr.entity.PatientCrossIndex;
import com.founder.cdr.entity.ProcedureOrder;
import com.founder.cdr.entity.TreatmentOrder;
import com.founder.cdr.entity.WithdrawRecord;
import com.founder.cdr.hl7.dto.Orders;
import com.founder.cdr.hl7.dto.poorin200901uv02.POORIN200901UV02;
import com.founder.cdr.hl7.dto.poorin200901uv14.POORIN200901UV14;
import com.founder.cdr.service.CommonService;
import com.founder.cdr.util.DataMigrationUtils;
import com.founder.cdr.util.DateUtils;
import com.founder.cdr.util.DictionaryUtils;
import com.founder.cdr.util.ObjectUtils;
import com.founder.cdr.util.StringUtils;

/**
 * 医嘱执行状态
 * @version 1.0, 2012/06/15
 * @author jin_peng
 * @since 1.0
 */
@Component(value = "poorin200901uv14Writer")
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class POORIN200901UV14Writer implements BusinessWriter<POORIN200901UV14>
{

    private static final Logger logger = LoggerFactory.getLogger(POORIN200901UV14Writer.class);

    /** $Author :yang_jianbo	
 	$Date : 2014/08/11 12:53$
    [BUG]0047204 MODIFY BEGIN */
    private List<Object> brList;
    // [BUG]0047204 MODIFY END
    
    // $Author :chang_xuewen
    // $Date : 2013/06/05 16:00$
    // [BUG]0033373 MODIFY BEGIN
    private static final Logger loggerBS004 = LoggerFactory.getLogger("BS004");

    // [BUG]0033373 MODIFY END
    // 护理操作.护理内部序列号sequences
    private static final String SEQ_NURSING_OPERATION = "SEQ_NURSING_OPERATION";

    @Autowired
    private CommonService commonService;

    // $Author :jin_peng
    // $Date : 2012/08/13 16:00$
    // [BUG]0008784 MODIFY BEGIN
    // 待更改医嘱执行状态的医嘱对象集合
    private List<Object> execOrderList;

    // 待保存的护理操作记录对象集合
    private List<Object> nurseOperationList;

    // 待保存的报告召回记录对象集合
    private List<Object> withdrawList;

    // 待更改召回状态的相应报告对象集合
    private List<Object> reportList;
    
    // 就诊信息
    private MedicalVisit visitResult;

    // $Author :tong_meng
    // $Date : 2013/10/28 14:00$
    // [BUG]0038527 MODIFY BEGIN
    // 待删除的报告集合
    private List<Object> delReportList;
    // [BUG]0038527 MODIFY END
    
    // 医嘱执行状态消息中医嘱执行信息
    private List<Orders> ordersInfList;

    // 需要把操作记录存入护理操作表中的医嘱
    private Map<List<Object>, Orders> saveNurseOperationOrdersMap;

    // Author :jia_yanqing
    // Date : 2013/4/7 13:21
    // [BUG]0014875 DELETE BEGIN
    // 需要把操作记录存入召回记录表中的检验医嘱
    // private Map<List<Object>, Orders> saveWithdrawLabOrdersMap;

    // 需要把操作记录存入召回记录表中的检查医嘱
    // private Map<List<Object>, Orders> saveWithdrawExamOrdersMap;

    // $Author :jin_peng
    // $Date : 2012/10/19 16:21$
    // [BUG]0010594 ADD BEGIN
    // 需要把操作记录存入召回记录表中的手术医嘱
    // private Map<List<Object>, Orders> saveWithdrawOperatingOrdersMap;
    // [BUG]0014875 DELETE END

    // Author :jia_yanqing
    // Date : 2013/4/7 13:21
    // [BUG]0014875 ADD BEGIN
    // 需要召回的报告
    private Map<List<Object>, Class<?>> saveWithdrawReportMap;

    // [BUG]0014875 ADD END

    // [BUG]0010594 ADD END
    // [BUG]0008784 MODIFY END

    // 患者本地ID
    private String patientLid;

    // 域ID
    private String patientDomain;

    // 就诊内部序列号
    private BigDecimal visitSn;

    // 患者内部序列号
    private BigDecimal patientSn;

    // 医嘱内部序列号
    private BigDecimal orderSn;

    // 系统时间
    private Date systemTime;

    // 就诊号
    private String visitNo;

    /**
     * 初始化参数设置
     */
    public POORIN200901UV14Writer()
    {
        systemTime = DateUtils.getSystemTime();
    }

    /**
     * 非业务数据校验
     */
    @Override
    public boolean validate(POORIN200901UV14 poorin200901uv14)
    {
    	// $Author :chang_xuewen
        // $Date : 2014/1/22 10:30$
        // [BUG]0042086 MODIFY BEGIN
        String headOrgCode = poorin200901uv14.getMessage().getOrgCode();
        String orgCode = poorin200901uv14.getOrgCode();

        /*if (!headOrgCode.equals(orgCode))
        {
            loggerBS004.error("Message:{},validate{}",
                    poorin200901uv14.toString(),
                    "消息体与消息头中医疗机构代码不相同. headOrgCode = " + headOrgCode
                        + "; orgCode = " + orgCode);

            return false;
        }*/
        if(StringUtils.isEmpty(poorin200901uv14.getOrgCode())){
        	// $Author :yang_mingjie
        	// $Date : 2014/06/26 10:09$
        	// [BUG]0045630 MODIFY BEGIN 
        	poorin200901uv14.setOrgCode(Constants.HOSPITAL_CODE);
        	poorin200901uv14.setOrgName(Constants.HOSPITAL_NAME);
        	//[BUG]0045630 MODIFY END
        } 
        // [BUG]0042086 MODIFY END

        Class<?> entityClass = null;
        // $Author :jin_peng
        // $Date : 2012/09/06 14:35$
        // [BUG]0009450 ADD BEGIN
        String orderType = null;
        // [BUG]0009450 ADD END

        // 获取医嘱执行状态消息中医嘱信息
        ordersInfList = poorin200901uv14.getOrderStatusInf();

        // $Author :jin_peng
        // $Date : 2012/09/06 14:35$
        // [BUG]0009450 ADD BEGIN
        // 获取患者域ID
        patientDomain = poorin200901uv14.getPatientDomain();
        // [BUG]0009450 ADD END

        // $Author :jin_peng
        // $Date : 2013/10/12 12:57$
        // [BUG]0037984 ADD BEGIN
        patientLid = poorin200901uv14.getPatientLid();

        visitNo = poorin200901uv14.getVisitNo();

        String visitOrdNo = poorin200901uv14.getVisitOrdNo();
        if (StringUtils.isEmpty(patientLid) && StringUtils.isEmpty(visitNo) && StringUtils.isEmpty(visitOrdNo))
        {
            loggerBS004.error("Message:{},validate{}",
                    poorin200901uv14.toString(),
                    "非空字段验证未通过,就诊号、就诊流水号、患者本地id不能同时为空! 患者本地id：patientLid = "
                        + patientLid + "; 就诊号：visitNo = " + visitNo + "; 就诊流水号：visitOrdNo = " + visitOrdNo);

            return false;
        }

        // [BUG]0037984 ADD END

        // 循环判断必填字段是否为空
        for (Orders orders : ordersInfList)
        {
            // 验证医嘱项中,医嘱执行状态编码，医嘱执行状态名称为非空
            if (!StringUtils.isNotNullAll(orders.getOrderStatus()/*,
                    orders.getOrderStatusName()*/))
            {
                // $Author :chang_xuewen
                // $Date : 2013/06/05 16:00$
                // [BUG]0033373 MODIFY BEGIN
                loggerBS004.error(
                        "Message:{},validate{}",
                        poorin200901uv14.toString(),
                        "非空字段验证未通过!  医嘱执行状态编码：OrderStatus = "
                            + orders.getOrderStatus()
                            + ";医嘱执行状态名称：OrderStatusName = "
                            + orders.getOrderStatusName());
                // [BUG]0033373 MODIFY END
                return false;
            }

            // $Author :jin_peng
            // $Date : 2012/09/06 14:35$
            // [BUG]0009450 MODIFY BEGIN
            // 获取医嘱类型
            orderType = orders.getOrderType();

            entityClass = commonService.getOrderClass(orderType);

/*            // 诊疗，护理，药物，其他医嘱需要医嘱序号
            if(entityClass == CareOrder.class || entityClass == MedicationOrder.class
            		|| entityClass == TreatmentOrder.class || entityClass == GeneralOrder.class){
            	if(StringUtils.isEmpty(orders.getOrderSeq())){
            		loggerBS004.error("Message:{},validate{}",
                            poorin200901uv14.toString(),
                            "诊疗，护理，药物，其他医嘱，医嘱序号不能为空！ 医嘱序号：OrderSeq = " + orders.getOrderSeq());
                    return false;
            	}
            }*/
            
            // 判断消息中的医嘱类型是否匹配术语中的医嘱类型
            if (!isExistsOrderType(entityClass, orderType))
            {
                loggerBS004.error("Message:{},validate{}",
                        poorin200901uv14.toString(),
                        "消息中的医嘱类型与术语中的医嘱类型不匹配！ 医嘱类型：orderType = " + orderType);
                // [BUG]0033373 MODIFY END
                return false;
            }
            // 判断医嘱类型为检验，并且LIS检验，申请单号、医嘱号、标本号三者不能同时为空
            if (entityClass == LabOrder.class && 
            		(Constants.SOURCE_LAB.toUpperCase().equals(poorin200901uv14.getSender().toUpperCase())
            				|| Constants.SOURCE_LAB_BAR_CODE.toUpperCase().equals(poorin200901uv14.getSender().toUpperCase())))
            {
                if (StringUtils.isNullAll(orders.getSampleCode(),orders.getRequestNo(),
                        orders.getOrderLid()))
                {
                    loggerBS004.error(
                            "Message:{},validate{}",
                            poorin200901uv14.toString(),
                            "医嘱类型为检验时申请单号、医嘱号、标本号三者不能同时为空！ 标本号：SampleCode="
                                + orders.getSampleCode()
                                + ",申请单号:RequestNo="+orders.getRequestNo()
                                + ",医嘱号:OrderLid="+orders.getOrderLid());
                    return false;
                }
            }
            //  判断医嘱类型为检验，LIS形态学,申请单号、医嘱号两者不能同时为空
            else if (entityClass == LabOrder.class && 
            		Constants.SOURCE_MORPHOLOGY.toUpperCase().equals(poorin200901uv14.getSender().toUpperCase())){
            	if (StringUtils.isNullAll(orders.getRequestNo(),
                        orders.getOrderLid()))
                {
                    loggerBS004.error(
                            "Message:{},validate{}",
                            poorin200901uv14.toString(),
                            "医嘱类型为检验时申请单号、医嘱号两者不能同时为空！ "
                                + ",申请单号:RequestNo="+orders.getRequestNo()
                                + ",医嘱号:OrderLid="+orders.getOrderLid());
                    return false;
                }
            }
        	 //  判断医嘱类型为检查或者手术学,申请单号、医嘱号两者不能同时为空
            else if (entityClass == ExaminationOrder.class || entityClass == ProcedureOrder.class ){
            	if (StringUtils.isNullAll(orders.getRequestNo(),
                        orders.getOrderLid()))
                {
                    loggerBS004.error(
                            "Message:{},validate{}",
                            poorin200901uv14.toString(),
                            "医嘱类型为检验时申请单号、医嘱号两者不能同时为空！ "
                                + ",申请单号:RequestNo="+orders.getRequestNo()
                                + ",医嘱号:OrderLid="+orders.getOrderLid());
                    return false;
                }
            }
            //  医嘱执行状态编码=报告已召回、报告号不能为空
            else if (Constants.ORDER_WITHDRAW_STATUS.equals(orders.getOrderStatus())){
            	if (StringUtils.isEmpty(
                        orders.getReportNo()))
                {
                   loggerBS004.error(
                           "Message:{},validate{}",
                           poorin200901uv14.toString(),
                           "医嘱执行状态编码为报告已召回,报告号不能为空！ "
	                               + ",报告号:OrderLid="+orders.getReportNo());
	                   return false;
	           }
            }
            else
            {
                // $Author :jin_peng
                // $Date : 2012/09/26 10:44$
                // [BUG]0009808 MODIFY BEGIN
//                if (Constants.lstDomainOutPatient().contains(poorin200901uv14.getPatientDomain())
//                		&& Constants.lstVisitTypeOutPatient().contains(poorin200901uv14.getVisitTypeCode()))
//                {
//                    if (StringUtils.isEmpty(orders.getRequestNo()))
//                    {
//                        // $Author :chang_xuewen
//                        // $Date : 2013/06/05 16:00$
//                        // [BUG]0033373 MODIFY BEGIN
//                        loggerBS004.error(
//                                "Message:{},validate{}",
//                                poorin200901uv14.toString(),
//                                "申请单号不能为空！  申请单号：RequestNo="
//                                    + orders.getRequestNo());
//                        // [BUG]0033373 MODIFY END
//                        return false;
//                    }
//                }
//                else
//                {
            		//医嘱号和申请单号不能同时为空(ORLO22检验执行确认/执行取消，ORGO20检查执行确认/执行取消只有申请单号，无医嘱号)
            		if(isExecMessage(poorin200901uv14)){
            			if (StringUtils.isEmpty(orders.getOrderLid()) && StringUtils.isEmpty(orders.getRequestNo()))
                        {
            				loggerBS004.error("Message:{},validate{}", 
                					poorin200901uv14.toString(), 
                					"本地医嘱号和申请单编号不能同时为空！orderLid=" + orders.getOrderLid() + ", requestNo=" + orders.getRequestNo());
            				return false;
                        }
            		}            	
            		else if (StringUtils.isEmpty(orders.getOrderLid()))
                    {
                        // $Author :chang_xuewen
                        // $Date : 2013/06/05 16:00$
                        // [BUG]0033373 MODIFY BEGIN
                        loggerBS004.error(
                                "Message:{},validate{}",
                                poorin200901uv14.toString(),
                                "本地医嘱号不能为空！ 本地医嘱号: OrderLid="
                                    + orders.getOrderLid());
                        // [BUG]0033373 MODIFY END
                        return false;
                    }
//                }
                // [BUG]0009808 MODIFY END
            }
        }

        return true;
    }

    // Author :jia_yanqing
    // Date : 2013/4/7 13:21
    // [BUG]0014875 MODIFY BEGIN
    /**
     * 业务校验（关联关系）
     */
    @Override
    public boolean checkDependency(POORIN200901UV14 poorin200901uv14,
            boolean flag)
    {
        List<Object> orderList = null;
        Class<?> entityClass = null;
        Class<?> reportEntityClass = null;
        String orderType = null;
        String orderStatus = null;
        BigDecimal visitSn = null;
        
        if (ordersInfList != null && !ordersInfList.isEmpty())
        {
            // $Author :jin_peng
            // $Date : 2013/10/12 12:57$
            // [BUG]0037984 ADD BEGIN
            if (StringUtils.isEmpty(patientLid))
            {
                PatientCrossIndex patientCrossIndex = commonService.getPatientRecordFromPCI(
                        patientDomain, visitNo, poorin200901uv14.getOrgCode(),poorin200901uv14.getVisitTypeCode());

                if (patientCrossIndex != null)
                {
                    patientLid = patientCrossIndex.getPatientLid();
                }
            }

            // [BUG]0037984 ADD END

            // $Author :jin_peng
            // $Date : 2012/09/06 14:35$
            // [BUG]0009450 DELETE BEGIN
            // patientDomain = poorin200901uv14.getPatientDomain();
            // [BUG]0009450 DELETE END

            // 创建需要执行操作的医嘱对应集合
            saveNurseOperationOrdersMap = new HashMap<List<Object>, Orders>();
            // saveWithdrawLabOrdersMap = new HashMap<List<Object>, Orders>();
            // saveWithdrawExamOrdersMap = new HashMap<List<Object>, Orders>();
            // saveWithdrawOperatingOrdersMap = new HashMap<List<Object>,
            // Orders>();
            saveWithdrawReportMap = new HashMap<List<Object>, Class<?>>();

            // 就诊信息取得
            visitResult = commonService.mediVisit(poorin200901uv14.getPatientDomain(), patientLid, poorin200901uv14.getVisitTimes(),
            		poorin200901uv14.getOrgCode(), poorin200901uv14.getVisitOrdNo()
            		, poorin200901uv14.getVisitTypeCode());
            
            // 港大PACS的patientLid和就诊有时不对应，检索时去掉patientLid
            if(visitResult == null && Constants.PACS_SYS_CODES.contains(poorin200901uv14.getSender())){
            	Map<String, Object> condition = new HashMap<String, Object>();
            	condition.put("patientDomain", patientDomain);
            	condition.put("visitOrdNo", poorin200901uv14.getVisitOrdNo());
            	condition.put("visitTypeCode", poorin200901uv14.getVisitTypeCode());
            	condition.put("deleteFlag", Constants.NO_DELETE_FLAG);
            	condition.put("orgCode", poorin200901uv14.getOrgCode());
            	List<Object> re = commonService.selectByCondition(MedicalVisit.class, condition);
            	if(re != null && !re.isEmpty()) visitResult = (MedicalVisit) re.get(0);
            }
            
            //通过申请单号关联就诊信息
            if(visitResult == null){
            	logger.debug("关联就诊失败, patientDomain=" + poorin200901uv14.getPatientDomain() 
            			+ ", patientLid=" + patientLid + ", visitTimes=" + poorin200901uv14.getVisitTimes()
            			+ ", orgCode=" + poorin200901uv14.getOrgCode() + ", visitOrdNo=" +  poorin200901uv14.getVisitOrdNo()
            			+ ", visitTypeCode=" + poorin200901uv14.getVisitTypeCode());
            	
            	logger.debug("通过申请单号关联就诊");
            	orderType = ordersInfList.get(0).getOrderType();
            	entityClass = commonService.getOrderClass(orderType);
            	visitResult = getMedicalVisit(poorin200901uv14, entityClass);
            	if(visitResult != null){
            		patientDomain = visitResult.getPatientDomain();
            		poorin200901uv14.setPatientDomain(visitResult.getPatientDomain());
            		logger.debug("通过申请单号关联就诊成功");
            	} else {
            		
            		logger.debug("通过申请单号关联就诊失败, orderType={}, requestNo={}", orderType, poorin200901uv14.getOrderStatusInf().get(0).getRequestNo());
            	}
            }
            
            if(visitResult != null){
            	visitSn = visitResult.getVisitSn();
            }
            
            // $Author :jia_yanqing
            // $Date : 2013/3/8 13:35$
            // [BUG]0014273 ADD BEGIN
            // 循环获取消息中的医嘱信息进行下列操作
            for (Orders orders : ordersInfList)
            {
                orderType = orders.getOrderType();

                // Author :jin_peng
                // Date : 2013/08/16 16:12
                // [BUG]0036238 MODIFY BEGIN
                // 当医嘱类型为用血申请或者用血类医嘱时，由于退费的该类医嘱是存入检验医嘱中的，所以先检查检验医嘱中是否存在该医嘱
                /**if (Constants.BLOOD_ORDER.equals(orderType)
                    || Constants.BLOOD_TRANS_APPLY_ORDER.equals(orderType))
                {
                    // 不考虑带不带收费
                    // Author :jia_yanqing
                    // Date : 2013/3/18 16:35
                    // [BUG]0014547 MODIFY BEGIN
                    // 就诊类别为住院时
                    if (Constants.PATIENT_DOMAIN_INPATIENT.equals(poorin200901uv14.getPatientDomain()))
                    {
                        entityClass = LabOrder.class;
                        Map<String, Object> map = new HashMap<String, Object>();
                        map.put("orderLid", orders.getOrderLid());
                        map.put("patientDomain", patientDomain);
                        map.put("patientLid", patientLid);
                        orderList = commonService.selectByCondition(
                                entityClass, map);
                        if (orderList == null || orderList.isEmpty())
                        {
                            entityClass = commonService.getOrderClass(orders.getOrderType());
                            orderList = commonService.selectByCondition(
                                    entityClass, map);
                        }
                    }
                    // 就诊类别为门诊时
                    else if (Constants.PATIENT_DOMAIN_OUTPATIENT.equals(poorin200901uv14.getPatientDomain()))
                    {
                        entityClass = BloodRequest.class;
                        Map<String, Object> map = new HashMap<String, Object>();
                        map.put("requestNo", orders.getRequestNo());
                        map.put("patientDomain", patientDomain);
                        map.put("patientLid", patientLid);
                        orderList = commonService.selectByCondition(
                                entityClass, map);
                    }
                    // [BUG]0014547 MODIFY ADD
                    // [BUG]0036238 DELETE END
                }*/
                // else
                // {
                // $Author :jin_peng
                // $Date : 2012/08/13 16:00$
                // [BUG]0008784 MODIFY BEGIN
                // 医嘱类型为用血类时根据门诊还是住院判断是存于用血申请表中还是其他医嘱中,用血情况暂时不存在
                /*
                 * if (Constants.BLOOD_ORDER.equals(orderType) &&
                 * Constants.PATIENT_DOMAIN_OUTPATIENT .equals(patientDomain)) {
                 * orderType = Constants.BLOOD_ORDER_OUTPATIENT; }
                 */
                // [BUG]0008784 MODIFY END

                // $Author :jin_peng
                // $Date : 2012/08/13 16:00$
                // [BUG]0008784 MODIFY BEGIN
                // 根据医嘱类型获取相应医嘱的对象class
                entityClass = commonService.getOrderClass(orderType);

                // $Author :jin_peng
                // $Date : 2012/09/06 14:35$
                // [BUG]0009450 DELETE BEGIN
                // 判断消息中的医嘱类型对应CDR医嘱类型是否匹配
                /*
                 * if (!isExistsOrderType(entityClass,orderType)) { return
                 * false; }
                 */
                // [BUG]0009450 DELETE END

                Map<String, Object> map = new HashMap<String, Object>();

                // 如果医嘱类型为检验医嘱时，通过标本号确定多条医嘱记录
                if (entityClass == LabOrder.class)
                {
                	/** $Author :yang_jianbo	
                 	$Date : 2014/08/11 12:53$
                    [BUG]0047204 MODIFY BEGIN
            	 	如果是来自血库系统，不校验医嘱，改校验输血申请单号是否存在*/
                	if(Constants.SOURCE_XUEKU.toUpperCase().equals(poorin200901uv14.getSender().toUpperCase())){
	            		map = new HashMap<String, Object>();
	            		//patientDomain,patientLid,requestNo三个查询条件，依据的是blood_request表中的唯一约束
	            		map.put("patientDomain", poorin200901uv14.getPatientDomain());
	            		map.put("patientLid", poorin200901uv14.getPatientLid());
	            		//his一次只能开出一个申请单，故取0
	            		map.put("requestNo", orders.getRequestNo());
	            		map.put("visitSn", visitSn);
	            		brList = new ArrayList<Object>();
	            		brList = commonService.selectByCondition(BloodRequest.class, map);
	            	
	            		if(brList.size()==0){
	            			return false;
	            		}else{
	            			return true;
	            		}
	            	}
                	
                	// 标本号不为空，用申请单号查询
                	 if (!StringUtils.isEmpty(orders.getSampleCode()))
                     {
                         // 如果消息中存在标本号
                         map.put("sampleNo", orders.getSampleCode());
                     } 
                	 else
                     {
                         if (!StringUtils.isEmpty(orders.getOrderLid()))
                         {
                             // 如果消息中存在医嘱号
                             map.put("orderLid", orders.getOrderLid());
                         }
                         else
                         {
                             // 如果消息中不存在医嘱号
                             map.put("requestNo", orders.getRequestNo());
                         }
                     } 
                	// [BUG]0047204 MODIFY END
            	
                	// TODO 检验类医嘱校验方式
                	
                    // $Author :jin_peng
                    // $Date : 2012/09/18 12:53$
                    // [BUG]0037130 MODIFY BEGIN
                   /* if (Constants.SOURCE_LAB.equals(poorin200901uv14.getSender().toUpperCase()))
                    {
                        // 门诊时根据申请单号查询
                        if (Constants.PATIENT_DOMAIN_OUTPATIENT.equals(poorin200901uv14.getPatientDomain()))
                        {
                            if (!StringUtils.isEmpty(orders.getRequestNo()))
                            {
                                // 申请单号
                                map.put("requestNo", orders.getRequestNo());
                            }
                        }
                        // 住院时根据医嘱号查询
                        else if (Constants.PATIENT_DOMAIN_INPATIENT.equals(poorin200901uv14.getPatientDomain()))
                        {
                            if (!StringUtils.isEmpty(orders.getOrderLid()))
                            {
                                // 医嘱号
                                map.put("orderLid", orders.getOrderLid());
                            }
                        }
                    }

                    // $Author :tong_meng
                    // $Date : 2013/10/12 16:00$
                    // [BUG]0038029 MODIFY BEGIN
                    if (!Constants.BLOOD_TRANS_APPLY_ORDER.equals(orderType))
                    {
                        map.put("sampleNo", orders.getSampleCode());
                    }
                    else
                    {
                        // 门诊时根据申请单号查询
                        if (Constants.PATIENT_DOMAIN_OUTPATIENT.equals(poorin200901uv14.getPatientDomain()))
                        {
                            // 申请单号
                            map.put("requestNo", orders.getRequestNo());

                        }
                        // 住院时根据医嘱号查询
                        else if (Constants.PATIENT_DOMAIN_INPATIENT.equals(poorin200901uv14.getPatientDomain()))
                        {
                            // 医嘱号
                            map.put("orderLid", orders.getOrderLid());
                        }
                    }*/
                    // [BUG]0037130 MODIFY END
                }
                // Author :jia_yanqing
                // Date : 2013/6/8 9:30
                // [BUG]0033542 MODIFY BEGIN
                // $Author :jin_peng
                // $Date : 2012/09/19 10:35$
                // [BUG]0009808 MODIFY BEGIN
                // 检验医嘱门诊根据申请单号查询，住院根据医嘱号查询
               /* else if (entityClass == ExaminationOrder.class)
                {
                    // 门诊时根据申请单号查询
                    if (Constants.PATIENT_DOMAIN_OUTPATIENT.equals(poorin200901uv14.getPatientDomain()))
                    {
                        // 申请单号
                        map.put("requestNo", orders.getRequestNo());

                    }
                    // 住院时根据医嘱号查询
                    else if (Constants.PATIENT_DOMAIN_INPATIENT.equals(poorin200901uv14.getPatientDomain()))
                    {
                        // 医嘱号
                        map.put("orderLid", orders.getOrderLid());
                    }
                }*/
                else
                {
                    if (!StringUtils.isEmpty(orders.getOrderLid()))
                    {
                        // 如果消息中存在医嘱号
                        map.put("orderLid", orders.getOrderLid());
                    }
                    else
                    {
                        // 如果消息中不存在医嘱号
                        map.put("requestNo", orders.getRequestNo());
                    }
                    // 针对医嘱类型为用血类时进行特殊处理
                    // if (isBloodRequest(orderType))
                    // {
                    // entityClass = BloodRequest.class;
                    // }
                }
                // [BUG]0009808 MODIFY END
                // [BUG]0033542 MODIFY BEGIN
                // [BUG]0008784 MODIFY END

                map.put("patientDomain", patientDomain);
                map.put("patientLid", patientLid);
                map.put("visitSn", visitSn);
                map.put("orgCode", poorin200901uv14.getOrgCode());
                map.put("deleteFlag", Constants.NO_DELETE_FLAG);
/*                // 诊疗，护理，药物，其他医嘱需要医嘱序号
                if(entityClass == CareOrder.class || entityClass == MedicationOrder.class
                		|| entityClass == TreatmentOrder.class || entityClass == GeneralOrder.class){
                	map.put("orderSeqnum",orders.getOrderSeq());
                }*/
                orderList = commonService.selectByCondition(entityClass, map);

                // $Author :jin_peng
                // $Date : 2012/09/19 10:35$
                // [BUG]0009808 ADD BEGIN
                // 在用血类医嘱并且为住院时并且本地医嘱号不为空并且用该本地医嘱号查询输血申请表未查出数据时需要再用相同查询条件查询其他医嘱表是否存在相应数据
                /**if (isBloodRequest(orderType)
                    && Constants.PATIENT_DOMAIN_INPATIENT.equals(poorin200901uv14.getPatientDomain())
                    && !StringUtils.isEmpty(orders.getOrderLid())
                    && (orderList == null || orderList.isEmpty()))
                {
                    entityClass = GeneralOrder.class;

                    orderList = commonService.selectByCondition(entityClass,
                            map);
                }**/
                // [BUG]0009808 ADD END
                // }

                // [BUG]0036238 MODIFY END

                // $Author :jin_peng
                // $Date : 2012/08/13 16:00$
                // [BUG]0008784 MODIFY BEGIN
                // 判断相应CDR医嘱对象是否存在
                if (!isExistsOrder(entityClass, orders, orderList,
                        poorin200901uv14))
                {
                    logger.error("MessageId:{}",
                            poorin200901uv14.getMessage().getId());
                    // $Author :chang_xuewen
                    // $Date : 2013/06/05 16:00$
                    // [BUG]0033373 MODIFY BEGIN
                    if (flag)
                    {
                        loggerBS004.error(
                                "Message:{},checkDependency{}",
                                poorin200901uv14.toString(),
                                "相应CDR医嘱对象不存在！ " + entityClass
                                    + ";标本号： sampleNo = "
                                    + orders.getSampleCode()
                                    + ";域ID： patientDomain = " + patientDomain
                                    + ";患者本地ID patientLid = " + patientLid
                                    + "; orgCode = "
                                    + poorin200901uv14.getOrgCode());
                    }
                    // [BUG]0033373 MODIFY END
                    return false;
                }

                // 医嘱执行状态
                orderStatus = orders.getOrderStatus();

                // $Author :jin_peng
                // $Date : 2013/10/11 15:01$
                // [BUG]0037985 ADD BEGIN
                if (Constants.ORDER_REVIEWED_STATUS.equals(orderStatus))
                {
                    String orderStatusDB = null;

                    if (entityClass == ExaminationOrder.class)
                    {
                        ExaminationOrder examinationOrder = (ExaminationOrder) orderList.get(0);

                        orderStatusDB = examinationOrder.getOrderStatus();
                    }
                    else if (entityClass == ProcedureOrder.class)
                    {
                        ProcedureOrder procedureOrder = (ProcedureOrder) orderList.get(0);

                        orderStatusDB = procedureOrder.getOrderStatus();
                    }

                    if (Constants.ORDER_REVIEWED_STATUS.equals(orderStatusDB))
                    {
                        logger.error(
                                "处理医嘱执行状态为检查报告已审消息时查得相应DB中该医嘱的医嘱状态也为检查报告已审核状态，此情况需等待其他医嘱执行状态消息更新其医嘱状态为非审核状态后再做处理。{}",
                                "requestNo = " + orders.getRequestNo()
                                    + "; orderLid = " + orders.getOrderLid()
                                    + "; patientLid = " + patientLid
                                    + "; patientDomain = " + patientDomain
                                    + "; orgCode = "
                                    + poorin200901uv14.getOrgCode());

                        if (flag)
                        {
                            loggerBS004.error(
                                    "Message:{},checkDependency{}",
                                    poorin200901uv14.toString(),
                                    "处理医嘱执行状态为检查报告已审消息时查得相应DB中该医嘱的医嘱状态也为检查报告已审核状态，此情况需等待其他医嘱执行状态消息更新其医嘱状态为非审核状态后再做处理。 requestNo = "
                                        + orders.getRequestNo()
                                        + "; orderLid = "
                                        + orders.getOrderLid()
                                        + "; patientLid = "
                                        + patientLid
                                        + "; patientDomain = "
                                        + patientDomain
                                        + "; orgCode = "
                                        + poorin200901uv14.getOrgCode());
                        }

                        return false;
                    }
                }

                // [BUG]0037985 ADD END

                // $Author :tong_meng
                // $Date : 2013/10/28 14:00$
                // [BUG]0038527 MODIFY BEGIN
                if (Constants.ORDER_STATUS_CANCELED.equals(orderStatus))
                {
                    // 得到将要取消医嘱和将要删除的报告list的遍历
                    Map<String, Object> classListMap = getEntityClassList(
                            entityClass, reportEntityClass, orders,
                            poorin200901uv14);

                    delReportList = (List<Object>) classListMap.get("list");

                    reportEntityClass = (Class<?>) classListMap.get("class");

                    if (delReportList != null && !delReportList.isEmpty())
                    {
                        // 待更新的实体的字段集合
                        Map<String, Object> updateMap = new HashMap<String, Object>();
                        
                        // deleteby
                        updateMap.put("deleteby", poorin200901uv14.getMessage().getServiceId()
                                + '-' + poorin200901uv14.getSender());
                        // deleteTime
                        updateMap.put("deleteTime", systemTime);
                        // deleteFlag
                        updateMap.put("deleteFlag", Constants.DELETE_FLAG);
                        // updateTime
                        updateMap.put("updateTime", systemTime);
                        
                        ObjectUtils.setObjectListPrivateFields(delReportList, updateMap);
                    }
                }
                // [BUG]0038527 MODIFY END
                
                // $Author :jin_peng
                // $Date : 2012/10/19 16:21$
                // [BUG]0010594 MODIFY BEGIN
                // 手术医嘱也存在检查报告
                // 当检验检查医嘱并且医嘱状态为作废时，判断该报告是否已为作废状态，如果是则不通过验证，说明该报告对应的更新内容还没有执行
                if ((entityClass == ExaminationOrder.class
                    || entityClass == LabOrder.class || entityClass == ProcedureOrder.class)
                    && Constants.ORDER_WITHDRAW_STATUS.equals(orderStatus))
                {
                    // $Author :tong_meng
                    // $Date : 2013/10/28 14:00$
                    // [BUG]0038527 MODIFY BEGIN
                    Map<String, Object> classListMap = getEntityClassList(
                            entityClass, reportEntityClass, orders,
                            poorin200901uv14);

                    reportList = (List<Object>) classListMap.get("list");

                    reportEntityClass = (Class<?>) classListMap.get("class");

                    // $Author :chang_xuewen
                    // $Date : 2013/08/31 9:30$
                    // [BUG]0036757 ADD BEGIN
                    // 设置更新人
                    /*try
                    {
                        Field updateBy = reportEntityClass.getDeclaredField("updateby");
                        updateBy.setAccessible(true);
                        updateBy.set(reportEntityClass,
                                poorin200901uv14.getMessage().getServiceId()
                                    + '-' + poorin200901uv14.getSender());
                    }
                    catch (SecurityException e)
                    {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    catch (NoSuchFieldException e)
                    {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    catch (IllegalArgumentException e)
                    {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    catch (IllegalAccessException e)
                    {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }*/
                    // [BUG]0036757 ADD END
                    // [BUG]0038527 MODIFY END

                    if (reportList == null || reportList.isEmpty())
                    {
                        logger.error("MessageId:{}",
                                poorin200901uv14.getMessage().getId());
                        logger.error("医嘱状态为报告召回时，检验或检查或手术医嘱对应的报告不存在！域ID:"
                            + patientDomain + "患者本地ID:" + patientLid + "报告号:"
                            + orders.getReportNo() + "; orgCode = "
                            + poorin200901uv14.getOrgCode());
                        // $Author :chang_xuewen
                        // $Date : 2013/06/05 16:00$
                        // [BUG]0033373 MODIFY BEGIN
                        if (flag)
                        {
                            loggerBS004.error("Message:{},checkDependency{}",
                                    poorin200901uv14.toString(),
                                    "医嘱状态为报告召回时，检验或检查或手术医嘱对应的报告不存在！域ID: patientDomain="
                                        + patientDomain + "患者本地ID: patientLid="
                                        + patientLid + "报告号: ReportNo="
                                        + orders.getReportNo() + "; orgCode = "
                                        + poorin200901uv14.getOrgCode());
                        }
                        // [BUG]0033373 MODIFY END
                        return false;
                    }

                    for (Object obj : reportList)
                    {
                        // $Author :tong_meng
                        // $Date : 2013/10/28 14:00$
                        // [BUG]0038527 MODIFY BEGIN
                        // $Author :chang_xuewen
                        // $Date : 2013/08/31 9:30$
                        // [BUG]0036757 ADD BEGIN
                        // 通过报告对象获取该报告中的updateby
                        Map<String, Object> updatebyMap = new HashMap<String, Object>();
                        
                        updatebyMap.put("updateby", poorin200901uv14.getMessage().getServiceId()
                                    + '-' + poorin200901uv14.getSender());
                        
                        ObjectUtils.setObjectPrivateFields(obj, updatebyMap);
                        // [BUG]0036757 ADD END
                        // [BUG]0038527 MODIFY END
                        
                        // 通过报告对象获取该报告中的召回状态
                        Map<String, Object> resMap = ObjectUtils.getObjectPrivateFields(
                                obj, "withdrawFlag");
                        
                        // 如果召回状态为已召回时则返回true
                        if (String.valueOf(Constants.WITH_DRAW_FLAG).equals(
                                String.valueOf(resMap.get("withdrawFlag"))))
                        {
                            logger.error("MessageId:{}",
                                    poorin200901uv14.getMessage().getId());
                            logger.error(
                                    "该报告已为作废状态，等待该报告的更新! \n {}",
                                    reportEntityClass + "; reportLid = "
                                        + orders.getReportNo()
                                        + "; patientDomain = " + patientDomain
                                        + "; patientLid = " + patientLid
                                        + "; orgCode = "
                                        + poorin200901uv14.getOrgCode());
                            // $Author :chang_xuewen
                            // $Date : 2013/06/05 16:00$
                            // [BUG]0033373 MODIFY BEGIN
                            if (flag)
                            {
                                loggerBS004.error(
                                        "Message:{},checkDependency{}",
                                        poorin200901uv14.toString(),
                                        "该报告已为作废状态，等待该报告的更新！"
                                            + reportEntityClass
                                            + ";本地报告号： reportLid = "
                                            + orders.getReportNo()
                                            + ";域ID patientDomain = "
                                            + patientDomain
                                            + ";患者本地ID patientLid = "
                                            + patientLid + "; orgCode = "
                                            + poorin200901uv14.getOrgCode());
                            }
                            // [BUG]0033373 MODIFY END
                            return false;
                        }
                    }
                    saveWithdrawReportMap.put(reportList, reportEntityClass);
                    /**
                        // 检查医嘱情况
                        if (entityClass == ExaminationOrder.class)
                        {
                            // 判断该检查医嘱对应的报告中召回状态是否已更改为召回,如果为已召回则不通过验证
                            if (!isNotExistsWithdraw(
                                    ExaminationResult.class,
                                    ((ExaminationOrder) orderList.get(0)).getExamReportSn(),
                                    "检查报告"))
                            {
                                return false;
                            }

                            // 添加检查报告作废对应的检查医嘱信息
                            saveWithdrawExamOrdersMap.put(orderList, orders);
                        }
                        // 手术医嘱情况
                        else if (entityClass == ProcedureOrder.class)
                        {
                            // 判断该手术医嘱对应的报告中召回状态是否已更改为召回,如果为已召回则不通过验证
                            if (!isNotExistsWithdraw(
                                    ExaminationResult.class,
                                    ((ProcedureOrder) orderList.get(0)).getExamReportSn(),
                                    "检查报告"))
                            {
                                return false;
                            }

                            // 添加检查报告作废对应的手术医嘱信息
                            saveWithdrawOperatingOrdersMap.put(orderList,
                                    orders);
                        }
                        // 检验医嘱情况
                        else
                        {
                            // 循环判断检验医嘱对应的检验报告是否已为召回状态
                            for (Object object : orderList)
                            {
                                LabOrder labOrder = (LabOrder) object;

                                // $Author:wei_peng
                                // $Date:2013/3/20 15:43
                                // [BUG]0014602 MODIFY BEGIN
                                List<Object> labOrderLabResults = getLabOrderLabResults(labOrder);

                                if (labOrderLabResults != null
                                    && labOrderLabResults.size() > 0)
                                {
                                    for (Object obj : labOrderLabResults)
                                    {
                                        LabOrderLabResult labOrderLabResult = (LabOrderLabResult) obj;
                                        // 判断该检验医嘱对应的报告中召回状态是否已更改为召回,如果为已召回则不通过验证
                                        if (!isNotExistsWithdraw(
                                                LabResult.class,
                                                labOrderLabResult.getLabReportSn(),
                                                "检验医嘱"))
                                        {
                                            return false;
                                        }
                                    }
                                }
                                // [BUG]0014602 MODIFY END

                                // 添加检验报告作废对应的检验医嘱信息
                                saveWithdrawLabOrdersMap.put(orderList, orders);
                            }
                        }
                        // 添加完成需作废报告对应医嘱后继续执行下一条内容
                        **/
                    // $Author:jin_peng
                    // $Date:2013/07/19 16:26
                    // [BUG]0034978 DELETE BEGIN
                    // continue;

                    // [BUG]0034978 DELETE END
                }
                // [BUG]0010594 MODIFY END

                // 添加需创建护理操作记录的医嘱
                saveNurseOperationOrdersMap.put(orderList, orders);
                // [BUG]0008784 MODIFY BEGIN
            }
        }
        // [BUG]0014875 MODIFY END

        return true;
    }

    public Map<String, Object> getEntityClassList(Class<?> entityClass,
            Class<?> reportEntityClass, Orders orders,
            POORIN200901UV14 poorin200901uv14)
    {
        Map<String, Object> mapOther = new HashMap<String, Object>();
        if (entityClass == ExaminationOrder.class
            || entityClass == ProcedureOrder.class)
        {
            reportEntityClass = ExaminationResult.class;
            mapOther.put("examReportLid", orders.getReportNo());
        }
        else if (entityClass == LabOrder.class)
        {
            reportEntityClass = LabResult.class;
            mapOther.put("labReportLid", orders.getReportNo());
        }

        mapOther.put("patientDomain", patientDomain);
        mapOther.put("patientLid", patientLid);
        mapOther.put("orgCode", poorin200901uv14.getOrgCode());
        mapOther.put("deleteFlag", Constants.NO_DELETE_FLAG);

        Map<String,Object> entityReportMap = new HashMap<String, Object>();
        
        entityReportMap.put("list", commonService.selectByCondition(reportEntityClass, mapOther));
        
        entityReportMap.put("class", reportEntityClass);
        
        return entityReportMap;
    }

    // $Author:wei_peng
    // $Date:2013/3/20 15:43
    // [BUG]0014602 ADD BEGIN
    // 通过检验医嘱获取检验医嘱和检验报告关联关系集合

    private List<Object> getLabOrderLabResults(LabOrder labOrder)
    {
        Map<String, Object> condition = new HashMap<String, Object>();
        condition.put("labOrderSn", labOrder.getOrderSn());
        condition.put("deleteFlag", Constants.NO_DELETE_FLAG);
        // Author:jia_yanqing
        // Date:2013/3/26 11:23
        // [BUG]0014706 MODIFY BEGIN
        List<Object> labOrderLabResults = commonService.selectByCondition(
                LabOrderLabResult.class, condition);
        // [BUG]0014706 MODIFY END
        return labOrderLabResults;
    }

    // [BUG]0014602 ADD END

    // $Author :jin_peng
    // $Date : 2012/09/19 10:35$
    // [BUG]0009808 ADD BEGIN
    /**
     * 判断医嘱类型是否为用血类
     * @param orderType 医嘱类型编码
     * @return 是否为用血类
     */
    /**private boolean isBloodRequest(String orderType)
    {
        return Constants.BLOOD_ORDER.equals(orderType)
            || Constants.BLOOD_TRANS_APPLY_ORDER.equals(orderType)
            || Constants.SYSTEM_BLOOD_APPLICATION_ORDER.equals(orderType);
    }**/
    // [BUG]0009808 ADD END

    // $Author :jin_peng
    // $Date : 2012/08/14 13:49$
    // [BUG]0008784 MODIFY BEGIN
    /**
     * 判断消息中的医嘱类型在CDR医嘱类型数据集中是否存在
     * @param entityClass 医嘱类型对应的相应医嘱表
     * @param orderType 医嘱类型
     * @return 存在标识
     */
    private boolean isExistsOrderType(Class<?> entityClass, String orderType)
    {
        boolean isExistsOrderType = true;

        // 如果entityClass为null说明没有匹配CDR的医嘱类型
        if (entityClass == null)
        {
            logger.error("医嘱执行状态消息中的医嘱类型没有匹配CDR医嘱类型 : {};就诊类型为 : {}",
                    " orderType = " + orderType, " patientDomain = "
                        + patientDomain);

            isExistsOrderType = false;
        }

        return isExistsOrderType;
    }

    // [BUG]0008784 MODIFY END

    // $Author :jin_peng
    // $Date : 2012/08/14 13:49$
    // [BUG]0008784 MODIFY BEGIN
    /**
     * 判断消息对应的CDR医嘱是否存在
     * @param entityClass 医嘱类型对应的相应医嘱表
     * @param orders 消息医嘱执行状态内容对象
     * @param orderList 对应的CDR医嘱对象
     * @return 存在标识
     */
    private boolean isExistsOrder(Class<?> entityClass, Orders orders,
            List<Object> orderList, POORIN200901UV14 poorin200901uv14)
    {
        boolean isExistsOrder = true;

        // 判断相应医嘱是否存在
        if (orderList == null || orderList.isEmpty())
        {
            if (entityClass == LabOrder.class)
            {
                logger.error("需要更改执行状态的医嘱不存在! \n {}",
                        entityClass + ": sampleNo = " + orders.getSampleCode()
                        	+ "; orderLid = " + orders.getOrderLid()
                            + "; patientDomain = " + patientDomain
                            + "; patientLid = " + patientLid + "; orgCode = "
                            + poorin200901uv14.getOrgCode());
            }
            else
            {
                // $Author :jin_peng
                // $Date : 2012/09/19 10:35$
                // [BUG]0009808 MODIFY BEGIN
                // 增加申请单号
                logger.error("需要更改执行状态的医嘱不存在! \n {}", entityClass
                    + ": orderLid = " + orders.getOrderLid() + "; requestNo = "
                    + orders.getRequestNo() + "; patientDomain = "
                    + patientDomain + "; patientLid = " + patientLid
                    + "; orgCode = " + poorin200901uv14.getOrgCode());
                // [BUG]0009808 MODIFY END
            }

            isExistsOrder = false;
        }

        return isExistsOrder;
    }

    // [BUG]0008784 MODIFY END

    // Author :jia_yanqing
    // Date : 2013/4/7 13:21
    // [BUG]0014875 MODIFY BEGIN
    /**
     * 根据业务进行数据库操作
     * @param poorin200901uv14 医嘱执行状态消息对象
     */
    @Transactional
    public void saveOrUpdate(POORIN200901UV14 poorin200901uv14)
    {
        // 创建待更改执行状态的医嘱对象集合
        execOrderList = new ArrayList<Object>();

        // 创建待保存的操作记录对象集合
        nurseOperationList = new ArrayList<Object>();

        // 创建待保存的召回记录对象集合
        withdrawList = new ArrayList<Object>();

        // $Author :jin_peng
        // $Date : 2012/10/19 16:21$
        // [BUG]0010594 ADD BEGIN
        // 设置手术医嘱对应的业务逻辑
        setWholeBusinessLogic(poorin200901uv14, null, saveWithdrawReportMap);

        // [BUG]0010594 ADD END

        // 设置其他医嘱类型对应的业务逻辑
        setWholeBusinessLogic(poorin200901uv14, saveNurseOperationOrdersMap,
                null);

        /** $Author :yang_jianbo	
     	$Date : 2014/08/11 12:53$
        [BUG]0047204 MODIFY BEGIN
	 	如果是来自血库系统，不校验医嘱，改校验输血申请单号是否存在,存在就按照消息内容修改医嘱执行状态*/
    	if(Constants.SOURCE_XUEKU.toUpperCase().equals(poorin200901uv14.getSender().toUpperCase())){
    		BloodRequest br = new BloodRequest();
    		br = (BloodRequest)brList.get(0);
			br.setOrderStatusCode(ordersInfList.get(0).getOrderStatus());
    		br.setOrderStatusName(ordersInfList.get(0).getOrderStatusName());
    		br.setUpdateby(Constants.SOURCE_XUEKU);
    		br.setUpdateTime(DateUtils.stringToDate(poorin200901uv14.getOperationDate()));
    		execOrderList.add(br);
    	}
    	//[BUG]0047204 MODIFY END
    	
        // $Author :tong_meng$
        // $Date : 2013/10/28 14:00$
        // $[BUG]0038527 MODIFY BEGIN$
        // $Note : 增加取消医嘱，逻辑删除对应报告的情况$
        // 根据医嘱状态保存相应操作记录和更新相应医嘱的医嘱状态
        commonService.updateAndSaveOrderStatusOperation(execOrderList,
                nurseOperationList, withdrawList, reportList, delReportList, isConfirmMessage(poorin200901uv14));
        // $[BUG]0038527 MODIFY END$
        
    }

    // [BUG]0014875 MODIFY END

    // $Author :chang_xuewen
    // $Date : 2013/08/26 18:21$
    // [BUG]0036600 ADD BEGIN
    /**
     * 检查护理操作记录中是否已经存在当前操作
     * @param order		当前医嘱
     * @param operDate	操作时间
     * @return
     */
    private int checkOperation(Orders order, Date operDate)
    {
        String orderStatus = order.getOrderStatus();
        
        /*
         * $Author: yu_yzh
         * $Date: 2015/8/7 14:03
         * 若operDate为null，取当前时间
         * ADD BEGIN
         * */
        operDate = operDate == null ? new Date() : operDate;
        // ADD END
        String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(operDate);
        int operExistsFlag = commonService.getOperation(orderSn, orderStatus,
                date);
        if (operExistsFlag != 0)
        {
            logger.debug("护士操作表中已经存在当前医嘱执行状态消息中的相关操作记录{}", " orderSn = "
                + orderSn + "; orderStatus = " + orderStatus
                + "; operationTime = " + operDate);
        }
        return operExistsFlag;
    }

    // [BUG]0036600 ADD END
    // Author :jia_yanqing
    // Date : 2013/4/7 13:21
    // [BUG]0014875 MODIFY BEGIN
    // $Author :jin_peng
    // $Date : 2012/08/14 13:49$
    // [BUG]0008784 MODIFY BEGIN
    /**
     * 操作整体业务（包括状态更改，操作记录添加，作废记录添加，检验，检查医嘱相应报告的作废标识状态更改）
     * @param poorin200901uv14 医嘱执行状态消息对象
     * @param variousMap 检验，检查，手术业务医嘱及相对应的消息中的医嘱内容集合信息
     * @param orderType 医嘱类型（判断所用）
     */
    private void setWholeBusinessLogic(POORIN200901UV14 poorin200901uv14,
            Map<List<Object>, Orders> variousMap,
            Map<List<Object>, Class<?>> saveWithdrawReportMap)
    {
        // 待执行操作的医嘱对象集合
        List<Object> orderList = null;
        // 相对应的消息中医嘱信息
        Orders orders = null;
        // 判断检验或检查时的情况时去除重复作废情况
        // List<BigDecimal> distinctList = null;
        // 检验医嘱对象
        // LabOrder labOrder = null;
        // 检查医嘱对象
        // ExaminationOrder examOrder = null;
        // 手术医嘱对象
        // ProcedureOrder procOrder = null;
        // 检验检查报告内部序列号
        BigDecimal reportSn = null;
        // 检验检查内部序列号
        BigDecimal pVisitSn = null;

        // 待执行操作对象信息存在时执行下面相关业务执行
        if (variousMap != null && !variousMap.isEmpty())
        {
            // distinctList = new ArrayList<BigDecimal>();

            // 获取医嘱执行业务对象集合及消息中的相应医嘱信息迭代对象
            Set<Entry<List<Object>, Orders>> orderEntries = variousMap.entrySet();
            Iterator<Entry<List<Object>, Orders>> iterator = orderEntries.iterator();

            // 循环获取相应业务医嘱信息及消息中的医嘱信息
            while (iterator.hasNext())
            {
                // 分别获取待执行操作的医嘱对象集合及相对应的消息中的医嘱信息
                Entry<List<Object>, Orders> orderEntry = iterator.next();
                orderList = orderEntry.getKey(); // 待执行操作的医嘱对象集合
                orders = orderEntry.getValue(); // 相对应消息中的医嘱内容

                // 循环操作相关业务内容
                for (Object obj : orderList)
                {
                    // $Author:jin_peng
                    // $Date:2013/07/19 16:26
                    // [BUG]0034978 MODIFY BEGIN
                    // 读取存在医嘱的属性值
                    getOrderInfo(obj);
                    // $Author :chang_xuewen
                    // $Date : 2013/08/26 18:21$
                    // [BUG]0036600 ADD BEGIN
                    // 操作记录是否重复检查
                    Date operationDate = getMaxNursingOperationDate(visitSn,
                            orderSn);
                    int operExistsFlag = checkOperation(
                            orders,
                            DateUtils.stringToDate(poorin200901uv14.getOperationDate()));
                    if (operExistsFlag == 0)
                    {
                        // $Author :jin_peng
                        // $Date : 2013/10/12 16:52$
                        // [BUG]0037985 MODIFY BEGIN
                        if (operationDate == null || poorin200901uv14.getOperationDate() == null //部分v2消息中不发操作时间，若为空则继续更改相应的医嘱执行状态
                            || DateUtils.stringToDate(
                                    poorin200901uv14.getOperationDate()).after(
                                    operationDate)
                            || (DateUtils.stringToDate(
                                    poorin200901uv14.getOperationDate()).compareTo(
                                    operationDate) == 0
                                && (Constants.ORDER_REVIEWED_STATUS.equals(orders.getOrderStatus()) || Constants.ORDER_WITHDRAW_STATUS.equals(orders.getOrderStatus())) && !ObjectUtils.getObjectPrivateFields(
                                    obj, "orderStatus").get("orderStatus").equals(
                                    orders.getOrderStatus())))
                        {
                            // 更改相应医嘱的执行状态
                            setOrderExecStatus(obj, orders, poorin200901uv14);

                            // 将状态更改完成的医嘱添加到待数据库操作的执行医嘱对象集合中
                            execOrderList.add(obj);
                        }

                        // [BUG]0037985 MODIFY END
                        else
                        {
                            logger.debug(
                                    "医嘱执行状态消息中当前医嘱操作时间小于此医嘱在操作记录中的最大操作时间，不更新该医嘱表中的执行状态。{}",
                                    "orderType = " + orders.getOrderType()
                                        + "; orderTypeName = "
                                        + orders.getOrderTypeName()
                                        + "; operationTimeForMessage = "
                                        + poorin200901uv14.getOperationDate()
                                        + "; operationTimeForNursingTable = "
                                        + operationDate);
                        }

                        // [BUG]0034978 MODIFY END

                        // 分别判断检查，检验和其他类型的医嘱来确定操作记录的存入表
                        /**if (Constants.REPORT_TYPE_EXAM.equals(orderType))
                        {
                            // 将医嘱对象转换为检查医嘱
                            examOrder = (ExaminationOrder) obj;
                        
                            // 获取检查医嘱对应的检查报告内部序列号
                            reportSn = examOrder.getExamReportSn();
                        
                            // 获取检查医嘱对应的就诊内部序列号
                            visitSn = examOrder.getVisitSn();
                        
                            // 添加检查医嘱对应的报告作废记录
                            withdrawList.add(getWithdrawRecord(poorin200901uv14,
                                    Constants.REPORT_TYPE_EXAM, reportSn, visitSn,
                                    ExaminationResult.class));
                        }
                        
                        // $Author :jin_peng
                        // $Date : 2012/10/19 16:21$
                        // [BUG]0010594 ADD BEGIN
                        // 手术医嘱操作报告
                        else if (Constants.REPORT_TYPE_PROCEDURE.equals(orderType))
                        {
                            // 将医嘱对象转换为手术医嘱
                            procOrder = (ProcedureOrder) obj;
                        
                            // 获取手术医嘱对应的检查报告内部序列号
                            reportSn = procOrder.getExamReportSn();
                        
                            // 获取手术医嘱对应的就诊内部序列号
                            visitSn = procOrder.getVisitSn();
                        
                            // 添加手术医嘱对应的报告作废记录
                            withdrawList.add(getWithdrawRecord(poorin200901uv14,
                                    Constants.REPORT_TYPE_EXAM, reportSn, visitSn,
                                    ExaminationResult.class));
                        }
                        
                        // [BUG]0010594 ADD END
                        
                        else if (Constants.REPORT_TYPE_LAB.equals(orderType))
                        {
                            // 将医嘱对象转换为检验医嘱
                            labOrder = (LabOrder) obj;
                            // 获取检验医嘱对应的就诊内部序列号
                            visitSn = labOrder.getVisitSn();
                        
                            // $Author:wei_peng
                            // $Date:2013/3/20 15:43
                            // [BUG]0014602 MODIFY BEGIN
                            List<Object> labOrderLabResults = getLabOrderLabResults(labOrder);
                            if (labOrderLabResults != null
                                && labOrderLabResults.size() > 0)
                            {
                                for (Object object : labOrderLabResults)
                                {
                                    LabOrderLabResult labOrderLabResult = (LabOrderLabResult) object;
                                    // 判断每条检验医嘱对应的检验报告是否已作废，如果已作废则不再进行作废操作
                                    if (isNotExistsValue(distinctList,
                                            labOrderLabResult.getLabReportSn()))
                                    {
                                        // 添加检验医嘱对应的报告作废记录
                                        withdrawList.add(getWithdrawRecord(
                                                poorin200901uv14,
                                                Constants.REPORT_TYPE_LAB,
                                                labOrderLabResult.getLabReportSn(),
                                                visitSn, LabResult.class));
                                    }
                                    // 添加已作废的检验报告
                                    distinctList.add(labOrderLabResult.getLabReportSn());
                                }
                        
                            }
                            // [BUG]0014602 MODIFY END
                        }
                        else
                        {**/
                        // 添加其他类型医嘱的操作记录
                        nurseOperationList.add(getNurOperation(
                                poorin200901uv14, obj, orders));
                        // }
                        // [BUG]0036600 ADD END
                    }
                }

            }
        }
        else if (saveWithdrawReportMap != null
            && !saveWithdrawReportMap.isEmpty())
        {
            Set<Entry<List<Object>, Class<?>>> reportEntries = saveWithdrawReportMap.entrySet();
            Iterator<Entry<List<Object>, Class<?>>> iterator = reportEntries.iterator();

            // 循环获取相应业务医嘱信息及消息中的医嘱信息
            while (iterator.hasNext())
            {
                // 分别获取待执行操作的医嘱对象集合及相对应的消息中的医嘱信息
                Entry<List<Object>, Class<?>> orderEntry = iterator.next();
                reportList = orderEntry.getKey(); // 待执行操作的医嘱对象集合
                Class<?> c = orderEntry.getValue(); // 相对应消息中的医嘱内容
                for (Object report : reportList)
                {
                    Field reportField = null;
                    Field visitField = null;
                    try
                    {
                        visitField = report.getClass().getDeclaredField(
                                "visitSn");
                        if (LabResult.class.equals(c))
                        {
                            reportField = report.getClass().getDeclaredField(
                                    "labReportSn");
                        }
                        else if (ExaminationResult.class.equals(c))
                        {
                            reportField = report.getClass().getDeclaredField(
                                    "examReportSn");
                        }

                    }
                    catch (SecurityException e1)
                    {
                        e1.printStackTrace();
                    }
                    catch (NoSuchFieldException e1)
                    {
                        e1.printStackTrace();
                    }
                    reportField.setAccessible(true);
                    visitField.setAccessible(true);
                    try
                    {
                        reportSn = (BigDecimal) reportField.get(report);
                        pVisitSn = (BigDecimal) visitField.get(report);
                    }
                    catch (IllegalArgumentException e)
                    {
                        throw new RuntimeException(e);
                    }
                    catch (IllegalAccessException e)
                    {
                        throw new RuntimeException(e);
                    }

                    // 设置医嘱状态为召回状态时相对应的报告中的召回状态
                    Map<String, Object> withdrawMap = new HashMap<String, Object>();
                    withdrawMap.put("withdrawFlag", Constants.WITH_DRAW_FLAG);
                    withdrawMap.put("updateTime", systemTime);

                    // 设置检查报告中的召回状态为已召回
                    ObjectUtils.setObjectPrivateFields(report, withdrawMap);

                    // Author:jia_yanqing
                    // $Date:2013/4/10 15:43
                    // [BUG]0014972 MODIFY BEGIN
                    // 分别判断检查，检验和其他类型的医嘱来确定操作记录的存入表
                    if (ExaminationResult.class.equals(c))
                    {
                        // 添加检查/手术医嘱对应的报告作废记录
                        withdrawList.add(getWithdrawRecord(poorin200901uv14,
                                Constants.REPORT_TYPE_EXAM, reportSn, pVisitSn,
                                ExaminationResult.class));
                    }
                    else if (LabResult.class.equals(c))
                    {
                        // 添加检验医嘱对应的报告作废记录
                        withdrawList.add(getWithdrawRecord(poorin200901uv14,
                                Constants.REPORT_TYPE_LAB, reportSn, pVisitSn,
                                LabResult.class));
                    }
                    // [BUG]0014972 MODIFY END
                }
            }
        }
    }

    // [BUG]0008784 MODIFY END
    // [BUG]0014875 MODIFY END

    // $Author :jin_peng
    // $Date : 2012/08/14 13:49$
    // [BUG]0008784 MODIFY BEGIN
    /**
     * 判断一个值是否已经存在于传入的集合中
     * @param valueList 待验证值集合
     * @param value 待验证值
     * @return 验证结果
     */
    private boolean isNotExistsValue(List<BigDecimal> valueList,
            BigDecimal value)
    {
        // 创建是否存在于集合中标识，默认为不存在
        boolean isNotExists = true;

        if (valueList != null && !valueList.isEmpty())
        {
            // 判断如果值在集合中存在
            if (valueList.contains(value))
            {
                // 存在情况下设置标识为存在
                isNotExists = false;
            }
        }

        return isNotExists;
    }

    // [BUG]0008784 MODIFY END

    // $Author :jin_peng
    // $Date : 2012/08/14 13:49$
    // [BUG]0008784 MODIFY BEGIN
    /**
     * 相应医嘱执行状态更改
     * @param changeStatusOrder 待更改执行状态的医嘱
     * @param orders 消息中的医嘱内容
     */
    private void setOrderExecStatus(Object changeStatusOrder, Orders orders,
            POORIN200901UV14 poorin200901uv14)
    {
        // 添加相应医嘱的医嘱状态
        Map<String, Object> orderStatusMap = new HashMap<String, Object>();

        // $Author :jin_peng
        // $Date : 2012/09/19 10:35$
        // [BUG]0009808 MODIFY BEGIN
        // $Author :jia_yanqing
        // $Date : 2012/8/6 16:33$
        // [BUG]0008504 MODIFY BEGIN
        if (GeneralOrder.class.equals(changeStatusOrder.getClass())
            || ConsultationOrder.class.equals(changeStatusOrder.getClass())
            || BloodRequest.class.equals(changeStatusOrder.getClass()))
        {
            orderStatusMap.put("orderStatusCode", orders.getOrderStatus());
        }
        else
        {
            orderStatusMap.put("orderStatus", orders.getOrderStatus());
        }
        /*
         * Author: yu_yzh
         * Date: 2015/7/27 18:36
         * 医嘱状态名称映射,确认医嘱状态值
         * MODIFY BEGIN
         * */
        String orderStatusName = orders.getOrderStatusName();
        if(orderStatusName == null){
        	orderStatusName  = DictionaryCache.getDictionary(
    				Constants.DOMAIN_ORDER_STATUS).get(
    						orders.getOrderStatus());;
    		orders.setOrderStatusName(orderStatusName);
        }
                
		orderStatusMap.put("orderStatusName", orderStatusName);
		
//      orderStatusMap.put("orderStatusName", orders.getOrderStatusName());
        /*
         * MODIFY END
         * */
		
        // [BUG]0008504 MODIFY END
        // [BUG]0009808 MODIFY END

		/*
		 * $Author: yu_yzh
		 * $Date: 2015/8/20 14:31
		 * 检查，检验设置确认人,确认时间
		 * ADD BEGIN
		 * */
/*		if( (LabOrder.class.equals(changeStatusOrder.getClass())
				|| ExaminationOrder.class.equals(changeStatusOrder.getClass()))
				&& isConfirmMessage(poorin200901uv14)){
			orderStatusMap.put("confirmPerson", poorin200901uv14.getOperationCode());
			if(poorin200901uv14.getOperationDate() != null){
//				orderStatusMap.put("confirmTime", new Date(Long.parseLong(poorin200901uv14.getOperationDate())));
				SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddhhmmss");
				try {
					Date date = sdf.parse(poorin200901uv14.getOperationDate());
					orderStatusMap.put("confirmTime", date);
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					logger.error("日期转换出错，operationDate={}", poorin200901uv14.getOperationDate());
					e.printStackTrace();
				}
			}			
			String name = null;
			if(poorin200901uv14.getOperationCode() != null){
				Map<String, String> dic = DictionaryCache.getDictionary(Constants.DOMAIN_STAFF);
				name = dic != null ? dic.get(poorin200901uv14.getOperationCode()) : null;
			}
			name = name == null ? poorin200901uv14.getOperationName() : name;
			orderStatusMap.put("confirmPersonName", name);
		}*/
		if(isConfirmMessage(poorin200901uv14)){
			Date date = null;
			if(poorin200901uv14.getOperationDate() != null){
				SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddhhmmss");
				try {
					date = sdf.parse(poorin200901uv14.getOperationDate());
				} catch (ParseException e) {
					logger.error("日期转换出错，operationDate={}", poorin200901uv14.getOperationDate());
					e.printStackTrace();
				}
			}
			String person = poorin200901uv14.getOperationCode();
			String name = DictionaryUtils.getNameFromDictionary(
					Constants.DOMAIN_STAFF, poorin200901uv14.getOperationCode(), poorin200901uv14.getOperationName());
			if(LabOrder.class.equals(changeStatusOrder.getClass())
					|| ExaminationOrder.class.equals(changeStatusOrder.getClass())
					|| ProcedureOrder.class.equals(changeStatusOrder.getClass())){
				orderStatusMap.put("confirmPerson", person);
				orderStatusMap.put("confirmPersonName", name);
				orderStatusMap.put("confirmTime", date);
			} else if(MedicationOrder.class.equals(changeStatusOrder.getClass())
					|| TreatmentOrder.class.equals(changeStatusOrder.getClass())
					|| CareOrder.class.equals(changeStatusOrder.getClass())){
				orderStatusMap.put("nurseConfirmPerson", person);
				orderStatusMap.put("nurseConfirmPersonName", name);
				orderStatusMap.put("nurseConfirmTime", date);
			} else if(GeneralOrder.class.equals(changeStatusOrder.getClass())){
				orderStatusMap.put("nurseConfirmPersonId", person);
				orderStatusMap.put("nurseConfirmPersonName", name);
				orderStatusMap.put("nurseConfirmTime", date);
			}
		}
		// ADD END
        orderStatusMap.put("updateTime", systemTime);
        // $Author :chang_xuewen
        // $Date : 2013/08/31 16:00$
        // [BUG]0036757 MODIFY BEGIN
        // 设置更新人
        orderStatusMap.put("updateby",
                poorin200901uv14.getMessage().getServiceId() + '-'
                    + poorin200901uv14.getSender());
        // [BUG]0036757 MODIFY END

        // 设置相应医嘱的医嘱状态
        ObjectUtils.setObjectPrivateFields(changeStatusOrder, orderStatusMap);
    }

    // [BUG]0008784 MODIFY END

    // $Author :jin_peng
    // $Date : 2012/08/14 13:49$
    // [BUG]0008784 MODIFY BEGIN
    /**
     * 获取操作记录对象
     * @param poorin200901uv14 医嘱执行状态消息对象信息
     * @param addNurseOperationOrder 待加入护理操作记录的医嘱对象信息 
     * @param orders 消息中的医嘱内容
     * @return 操作记录对象
     */
    private NursingOperation getNurOperation(POORIN200901UV14 poorin200901uv14,
            Object addNurseOperationOrder, Orders orders)
    {
        NursingOperation nursingOperation = new NursingOperation();

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
        nursingOperation.setPatientDomain(patientDomain);

        // 患者本地ID
        nursingOperation.setPatientLid(patientLid);

        // 医嘱类型
        nursingOperation.setOrderType(orders.getOrderType());

        // $Author :tong_meng
        // $Date : 2012/7/1 15:33$
        // [BUG]0007418 ADD BEGIN
        // 医嘱类型名稱
        nursingOperation.setOrderTypeName(orders.getOrderTypeName());
        // [BUG]0007418 ADD END

        // Author :jia_yanqing
        // Date : 2013/6/28 16:33
        // [BUG]0033848 ADD BEGIN
        // 医嘱状态编码
        nursingOperation.setOrderStatusCode(orders.getOrderStatus());
        // 医嘱状态名称
        nursingOperation.setOrderStatusName(DictionaryUtils.getNameFromDictionary(
        		Constants.DOMAIN_ORDER_STATUS, orders.getOrderStatus(), orders.getOrderStatusName()));
        
        // 执行科室编码
        nursingOperation.setExecuteDeptCode(poorin200901uv14.getExecDept());
        // 执行科室名称        
        nursingOperation.setExecuteDeptName(DictionaryUtils.getNameFromDictionary(
        		Constants.DOMAIN_DEPARTMENT, poorin200901uv14.getExecDept(), poorin200901uv14.getExecDeptName()));
        // [BUG]0033848 ADD END

        // 操作人ID
        nursingOperation.setOperatorId(poorin200901uv14.getOperationCode());

        // 操作人姓名
//        nursingOperation.setOperatorName(poorin200901uv14.getOperationName());
        // 优先根据字典取姓名
        nursingOperation.setOperatorName(DictionaryUtils.getNameFromDictionary(Constants.DOMAIN_STAFF, 
        		poorin200901uv14.getOperationCode(), poorin200901uv14.getOperationName()));
        

        // 操作时间
        nursingOperation.setOperationTime(DateUtils.stringToDate(poorin200901uv14.getOperationDate()));

        // 创建时间
        nursingOperation.setCreateTime(systemTime);

        // 更新时间
        nursingOperation.setUpdateTime(systemTime);

        // 更新回数
        nursingOperation.setUpdateCount(Constants.INSERT_UPDATE_COUNT);

        // 删除标志
        nursingOperation.setDeleteFlag(Constants.NO_DELETE_FLAG);
        // $Author :chang_xuewen
        // $Date : 2013/08/31 16:00$
        // [BUG]0036757 MODIFY BEGIN
        // 增加人
        nursingOperation.setCreateby(DataMigrationUtils.getDataMigration() + poorin200901uv14.getMessage().getServiceId()
            + '-' + poorin200901uv14.getSender());
        // [BUG]0036757 MODIFY END
        nursingOperation.setOrgCode(poorin200901uv14.getOrgCode());
        nursingOperation.setOrgName(poorin200901uv14.getOrgName());

        return nursingOperation;
    }

    // [BUG]0008784 MODIFY END

    // $Author :jin_peng
    // $Date : 2012/08/14 13:49$
    // [BUG]0008784 MODIFY BEGIN
    /**
     * 获取召回记录对象集合并装入已更改召回状态的相应报告
     * @param poorin200901uv14 医嘱执行状态消息对象信息
     * @param reportType 报告类型(检验/检查) 
     * @param reportSn 报告内部序列号 
     * @param visitSn 就诊内部序列号
     * @param entityClass 检验检查报告class
     * @return 召回记录对象
     */
    private WithdrawRecord getWithdrawRecord(POORIN200901UV14 poorin200901uv14,
            BigDecimal reportType, BigDecimal reportSn, BigDecimal visitSn,
            Class<?> entityClass)
    {
        // Author :jia_yanqing
        // Date : 2013/4/7 13:21
        // [BUG]0014875 DELETE BEGIN
        // 得到相应检查医嘱对应的检查报告对象装入结果list中(召回状态已更改)
        // reportList.add(getReport(entityClass, reportSn));
        // [BUG]0014875 DELETE END

        // 创建召回记录对象
        WithdrawRecord withdrawRecord = new WithdrawRecord();

        // 设置报告内部序列号
        withdrawRecord.setReportSn(reportSn);

        // 设置就诊内部序列号
        withdrawRecord.setVisitSn(visitSn);

        // 设置报告类型
        withdrawRecord.setRefType(reportType);

        // 设置召回人
        withdrawRecord.setWithdrawPerson(poorin200901uv14.getOperationCode());

        // 设置召回人姓名
        withdrawRecord.setWithdrawPersonName(poorin200901uv14.getOperationName());

        // 设置召回时间
        withdrawRecord.setWithdrawTime(DateUtils.stringToDate(poorin200901uv14.getOperationDate()));

        // 设置记录创建时间
        withdrawRecord.setCreateTime(systemTime);

        // 设置记录更新时间
        withdrawRecord.setUpdateTime(systemTime);

        // 设置记录更新回数
        withdrawRecord.setUpdateCount(Constants.INSERT_UPDATE_COUNT);

        // 设置新增时删除标识
        withdrawRecord.setDeleteFlag(Constants.NO_DELETE_FLAG);

        // $Author :chang_xuewen
        // $Date : 2013/08/31 16:00$
        // [BUG]0036757 MODIFY BEGIN
        // 设置增加人
        withdrawRecord.setCreateby(DataMigrationUtils.getDataMigration() + poorin200901uv14.getMessage().getServiceId()
            + '-' + poorin200901uv14.getSender());
        // [BUG]0036757 MODIFY END

        return withdrawRecord;
    }

    // [BUG]0008784 MODIFY END

    /**
     * 获取符合条件的相应报告对象
     * @param entityClass 相应报告对象class
     * @param reportSn 相应报告内部序列号
     * @return 返回符合条件的相应报告对象
     */
    private Object getReport(Class<?> entityClass, BigDecimal reportSn)
    {
        // 设置医嘱状态为召回状态时相对应的报告中的召回状态
        Map<String, Object> withdrawMap = new HashMap<String, Object>();
        withdrawMap.put("withdrawFlag", Constants.WITH_DRAW_FLAG);
        withdrawMap.put("updateTime", systemTime);

        // 得到相应检查医嘱对应的检查报告对象
        Object labExamObject = commonService.selectById(entityClass, reportSn);

        // 设置检查报告中的召回状态为已召回
        ObjectUtils.setObjectPrivateFields(labExamObject, withdrawMap);

        return labExamObject;
    }

    // $Author :jin_peng
    // $Date : 2012/08/13 16:00$
    // [BUG]0008784 MODIFY BEGIN
    /**
     * 根据传入的报告内部序列号从相应的报告中进行查询判断该医嘱是否已存入的召回状态
     * @param entityClass 相应报告对象class
     * @param reportSn 相应报告内部序列号
     * @param reportTypeName 报告类型名称
     * @return 是否已记录召回状态标识
     */
    /**private boolean isNotExistsWithdraw(Class<?> entityClass,
            BigDecimal reportSn, String reportTypeName)
    {
        boolean isNotExists = true;

        // $Author :jin_peng
        // $Date : 2012/10/19 16:26$
        // [BUG]0010620 MODIFY BEGIN
        // 如果对应的报告号不存在则说明没有该对应的报告，则不能进行召回操作
        /**if (reportSn == null)
        {
            logger.error(
                    "医嘱类型为检验或检查或手术并且医嘱状态为召回时该医嘱对应的报告内部序列号不存在！报告内部序列号：{}；报告类型为：{}",
                    reportSn, entityClass);

            return false;
        }

        // [BUG]0010620 MODIFY END

        // 根据reportSn获取相应的报告对象
        Object reportObject = commonService.selectById(entityClass, reportSn);

        if (reportObject == null)
        {
            logger.error("检验或检查或手术医嘱对应的报告不存在！");
        }

        // 通过报告对象获取该报告中的召回状态
        Map<String, Object> resMap = ObjectUtils.getObjectPrivateFields(
                reportObject, "withdrawFlag");

        // 如果召回状态为已召回时则返回true
        if (String.valueOf(Constants.WITH_DRAW_FLAG).equals(
                String.valueOf(resMap.get("withdrawFlag"))))
        {
            logger.error("该" + reportTypeName + "已为作废状态，等待该报告的更新! \n {}",
                    entityClass + ": reportSn = " + reportSn
                        + "; patientDomain = " + patientDomain
                        + "; patientLid = " + patientLid);

            isNotExists = false;
        }

        return isNotExists;
    }**/

    // [BUG]0008784 MODIFY END

    /**
     * 通过不同的医嘱类对象返回相应的医嘱数据表中对应的数据
     * @param obj 医嘱的类对象
     * @return orderSn:医嘱内部序列号;visitSn:就诊内部序列号;patientSn:患者内部序列号
     */
    private void getOrderInfo(Object obj)
    {
        try
        {
            // $Author :jin_peng
            // $Date : 2012/09/26 13:57$
            // [BUG]0009808 MODIFY BEGIN
            // 医嘱或申请单内部序列号属性
            Field orderField = null;

            // 输血特殊处理
            try
            {
                orderField = obj.getClass().getDeclaredField("orderSn");

            }
            catch (Exception ex)
            {
                orderField = obj.getClass().getDeclaredField("requestSn");
            }

            // 同时可以取私有属性(private)
            orderField.setAccessible(true);
            // [BUG]0009808 MODIFY END

            // 就诊内部序列号属性
            Field visitField = obj.getClass().getDeclaredField("visitSn");
            // 同时可以取私有属性(private)
            visitField.setAccessible(true);
            // 患者内部序列号属性
            Field patientField = obj.getClass().getDeclaredField("patientSn");
            // 同时可以取私有属性(private)
            patientField.setAccessible(true);
            try
            {
                // 医嘱内部序列号DB值
                orderSn = (BigDecimal) orderField.get(obj);
                // 就诊内部序列号DB值
                visitSn = (BigDecimal) visitField.get(obj);
                // 患者内部序列号DB值
                patientSn = (BigDecimal) patientField.get(obj);
            }
            catch (IllegalArgumentException e)
            {
                throw new RuntimeException(e);
            }
            catch (IllegalAccessException e)
            {
                throw new RuntimeException(e);
            }
        }
        catch (SecurityException e)
        {
            throw new RuntimeException(e);
        }
        catch (NoSuchFieldException e)
        {
            throw new RuntimeException(e);
        }

    }

    private Date getMaxNursingOperationDate(BigDecimal visitSn,
            BigDecimal orderSn)
    {
        return (commonService.getMaxNursingOperDate(visitSn, orderSn)).getOperationDate();
    }
    
	/**
	 * 通过申请单编号获取检查医嘱，获得患者就诊信息
	 * */
	private MedicalVisit getMedicalVisit(POORIN200901UV14 poorin200901uv14, Class<?> clazz){
		if(poorin200901uv14.getOrderStatusInf() == null || poorin200901uv14.getOrderStatusInf().isEmpty()) return null;
		String requestNo = poorin200901uv14.getOrderStatusInf().get(0).getRequestNo();
		if(StringUtils.isEmpty(requestNo)) return null;		
		MedicalVisit mv = null;
		mv = commonService.mediVisitByRequestNo(clazz, patientLid, requestNo);
		return mv;
	}

	private boolean isExecMessage(POORIN200901UV14 poorin200901uv14){
		return Constants.V2_EXEC_MESSAGE_FLAG.equals(poorin200901uv14.getNewUpFlag())
				|| Constants.V2_EXEC_CANCEL_MESSAGE_FLAG.equals(poorin200901uv14.getNewUpFlag());
	}
	
	private boolean isConfirmMessage(POORIN200901UV14 poorin200901uv14){
		return Constants.V2_CONFIRM_OR_CHARGE_MESSAGE_FLAG.equals(poorin200901uv14.getNewUpFlag());
	}
	
}
