package com.yly.cdr.batch.writer;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.yly.cdr.batch.writer.common.CommonWriterUtils;
import com.yly.cdr.cache.DictionaryCache;
import com.yly.cdr.core.Constants;
import com.yly.cdr.entity.MedicalVisit;
import com.yly.cdr.entity.PatientDoctor;
import com.yly.cdr.entity.ProcedureOrder;
import com.yly.cdr.hl7.dto.ProOrderBatch;
import com.yly.cdr.hl7.dto.poorin200901uv03.POORIN200901UV03;
import com.yly.cdr.service.CommonService;
import com.yly.cdr.service.SurgicalProcedureService;
import com.yly.cdr.util.DataMigrationUtils;
import com.yly.cdr.util.DateUtils;
import com.yly.cdr.util.DictionaryUtils;
import com.yly.cdr.util.StringUtils;

/**
 * 手术医嘱
 * @author tong_meng
 *
 */

@Component(value = "poorin200901uv03Writer")
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class POORIN200901UV03Writer implements BusinessWriter<POORIN200901UV03>
{

    private static final Logger logger = LoggerFactory.getLogger(POORIN200901UV03Writer.class);

    private static final Logger loggerBS007 = LoggerFactory.getLogger("BS007");

    @Autowired
    private SurgicalProcedureService surgicalProcedureService;

    @Autowired
    private CommonService commonService;

    @Autowired
    private CommonWriterUtils commonWriterUtils;

    // 患者本地ID
    private String patientLid;

    // 就诊信息
    private MedicalVisit visitResult;

    // $Author :jin_peng
    // $Date:2013/02/06 13:38
    // [BUG]0013909 ADD BEGIN
    // 过滤已存在的医生集合
    private List<String> filterPatientDoctorList = new ArrayList<String>();

    // [BUG]0013909 ADD END

    // $Author :jin_peng
    // $Date : 2012/9/17 15:07$
    // [BUG]0009718 ADD BEGIN
    // 需要删除的手术申请对象集合
    private List<Object> proRequestDeleteList;
    
    // 需要更新的手术申请对象集合
    private List<Object> proRequestUpdateList;

    // $Author:wei_peng
    // $Date:2012/11/06 10:46
    // [BUG]0011030 ADD BEGIN
    // 患者和医生关联记录结合
    private List<PatientDoctor> patientDoctorList = new ArrayList<PatientDoctor>();

    // [BUG]0011030 ADD END

    // 系统当前时间
    private Date systemDate;

    // 门诊新增时手术医嘱是否存在标识
    private boolean proOrderExistFlag = false;

    // $Author: tong_meng
    // $Date: 2013/8/30 15:00
    // [BUG]0036757 ADD BEGIN
    private String serviceId;

    // [BUG]0036757 ADD END

    /**
     * 设置初始参数
     */
    public POORIN200901UV03Writer()
    {
        systemDate = DateUtils.getSystemTime();
    }

    // [BUG]0009718 ADD END

    // $Author :jia_yanqing
    // $Date : 2012/9/5 15:00$
    // [BUG]0008782 MODIFY BEGIN
    /**
     * 数据校验  不同的场景中某些字段是否可为空！
     * 
     * @param baseDto
     */
    public boolean validate(POORIN200901UV03 poorin200901uv03)
    {
        // $Author: tong_meng
        // $Date: 2013/8/30 15:00
        // [BUG]0036757 ADD BEGIN
        serviceId = poorin200901uv03.getMessage().getServiceId();
        // [BUG]0036757 ADD END
        
        // 取得消息中的手术医嘱
        List<ProOrderBatch> proOrderList = poorin200901uv03.getProOrders();
        for (ProOrderBatch proOrder : proOrderList)
        {
        	// $Author :chang_xuewen
            // $Date : 2014/1/22 10:30$
            // [BUG]0042086 MODIFY BEGIN   
        	// 消息头中的医疗机构编码与消息中的医疗机构编码对比
     		/*if (poorin200901uv03.getMessage().getOrgCode() == null
     				|| !poorin200901uv03.getMessage().getOrgCode()
     						.equals(proOrder.getOrganizationCode())) {
     			loggerBS007.error(
     					"Message:{},validate:{}",
     					proOrder.toString(),
     					"消息头中的医疗机构编码和消息体中的医疗机构编码不同，消息头中的医疗机构编码="
     							+ poorin200901uv03.getMessage().getOrgCode()
     							+ "，消息体中的医疗机构编码="
     							+ proOrder.getOrganizationCode());
     			return false;
     		}*/
        	if(StringUtils.isEmpty(proOrder.getOrganizationCode())){
        		// $Author :yang_mingjie
            	// $Date : 2014/06/26 10:09$
            	// [BUG]0045630 MODIFY BEGIN 
        		proOrder.setOrganizationCode(Constants.HOSPITAL_CODE);
        		proOrder.setOrganizationName(Constants.HOSPITAL_NAME);
        		//[BUG]0045630 MODIFY END
            }
        	// [BUG]0042086 MODIFY END   
        	// $Author :jin_peng
            // $Date : 2012/9/17 15:07$
            // [BUG]0009718 ADD BEGIN
            // 门诊删除情况验证申请单号是否为空
            if (isDeleteMessage(poorin200901uv03))
            {
                // 门诊删除情况下验证申请单号是否为空
                if (StringUtils.isEmpty(proOrder.getRequestNo()))
                {
                    loggerBS007.error(
                            "Message:{}, validate:{}",
                            proOrder.toString(),
                            "非空字段验证未通过！申请单号：requestNo = "
                                + proOrder.getRequestNo()
                                + "; 域ID：patientDomain = "
                                + proOrder.getDomainID()
                                + "; 出发事件：triggerEvent = "
                                + poorin200901uv03.getNewUpFlag());

                    return false;
                }

                // 门诊删除情况下除上述字段验证外不再验证其他字段
                continue;
            }
            // [BUG]0009718 ADD END

            // 验证手术医嘱中医嘱号，手术名称编码，手术名称，检验申请日期，执行科室编码，执行科室名称
            // 申请医师编码，申请医师，申请科室编码，申请科室为非空，就诊类别，就诊流水号
            if (!StringUtils.isNotNullAll(proOrder.getOrderLid(),
                    proOrder.getOperationNameCode(),proOrder.getPatientLid(),
                    proOrder.getOperationName(), proOrder.getRequestDate(),
//                    proOrder.getExecuteDeptNo(), proOrder.getExecuteDeptName(),
                    proOrder.getRequestPersonId(),
//                    proOrder.getRequestPersonName(),
//                    proOrder.getRequestDeptNo(), proOrder.getRequestDeptName(),
                    proOrder.getMedicaVisit().get(0).getVisitType(),
                    proOrder.getMedicaVisit().get(0).getVisitOrdNo()
                    ))
                    
            {
                loggerBS007.error(
                        "Message:{}, validate:{}",
                        proOrder.toString(),
                        "非空字段验证未通过！OrderLid = " + proOrder.getOrderLid()
                        	+ "; PatientLid = "
                            + proOrder.getPatientLid()
                            + "; OperationNameCode = "
                            + proOrder.getOperationNameCode()
                            + "; OperationName = "
                            + proOrder.getOperationName() + ";RequestDate = "
                            + proOrder.getRequestDate() + "; OperationName = "
                            + proOrder.getOperationName()
//                            + "; ExecuteDeptNo = "
//                            + proOrder.getExecuteDeptNo()
//                            + "; ExecuteDeptName = "
//                            + proOrder.getExecuteDeptName()
                            + "; RequestPersonId = "
                            + proOrder.getRequestPersonId()
                            + "; RequestPersonName = "
                            + proOrder.getRequestPersonName()
//                            + "; RequestDeptNo = "
//                            + proOrder.getRequestDeptNo()
//                            + "; RequestDeptName = "
//                            + proOrder.getRequestDeptName()
                            + "; VisitTypeCode = "
                            + proOrder.getMedicaVisit().get(0).getVisitType()
                              + "; VisitOrdNo = "
                            + proOrder.getMedicaVisit().get(0).getVisitOrdNo()
                            );
                return false;
            }
            // $Author:wei_peng
            // $Date:2012/9/21 14:53
            // [BUG]0009776 ADD BEGIN
            if (Constants.lstDomainInPatient().contains(proOrder.getDomainID()) && 
            		Constants.lstVisitTypeInPatient().contains(proOrder.getMedicaVisit().get(0).getVisitType())
                && !StringUtils.isNotNullAll(proOrder.getWardsId(),
//                        proOrder.getWardsName(), 
                        proOrder.getBedNo(),
                        proOrder.getExecuteDeptNo(),
//                        proOrder.getExecuteDeptName(),
                        proOrder.getRequestDeptNo()
//                        , proOrder.getRequestDeptName()
                		))
            {
                loggerBS007.error("Message:{}, validate:{}",
                        proOrder.toString(),
                        "住院时，非空字段验证未通过：病区编码：" 
                        	+ proOrder.getWardsId()
//                            + "，病区名称：" 
//                        	+ proOrder.getWardsName() 
                            + "，床号："
                            + proOrder.getBedNo()
                             + "，申请科室编码："
                            + proOrder.getRequestDeptNo()
//                             + "，申请科室名称："
//                            + proOrder.getRequestDeptName()
                             + "，执行科室编码："
                            + proOrder.getExecuteDeptNo()
//                             + "，执行科室名称："
//                            + proOrder.getExecuteDeptName()
                            );
                return false;
            }
            // [BUG]0009776 ADD END
        }
        return true;
    }

    // [BUG]0008782 MODIFY END

    // $Author :jin_peng
    // $Date : 2012/9/17 15:07$
    // [BUG]0009718 ADD BEGIN
    /**
     * 判断是否为门诊删除情况
     * @param domainId 域ID
     * @param triggerEvent 触发事件
     * @return 是否为门诊删除情况标识 
     */
    private boolean isOutpatientDelete(String domainId, String visitTypeCode, String triggerEvent)
    {
        return Constants.DELETE_MESSAGE_FLAG.equals(triggerEvent);
    }

    // [BUG]0009718 ADD END

    // $Author :tong_meng
    // $Date : 2012/9/25 14:33$
    // [BUG]0009863 ADD BEGIN 增加new场合的判断
    /**
     * 门诊新增情况
     * @param domainID
     * @param newUpFlag
     * @return
     */
    private boolean isOutpatientNew(String domainID, String visitTypeCode, String newUpFlag)
    {
        return Constants.NEW_MESSAGE_FLAG.equals(newUpFlag);
    }
    
    /**
     * 门诊更新情况
     * @param domainID
     * @param newUpFlag
     * @return
     */
    private boolean isOutpatientUpdate(String domainID, String visitTypeCode, String newUpFlag)
    {
        return Constants.UPDATE_MESSAGE_FLAG.equals(newUpFlag);
    }

    // [BUG]0009863 ADD END

    /**
     * 判断被关联消息是否存在
     * 与就诊表的关联
     */
    public boolean checkDependency(POORIN200901UV03 poorin200901uv03,
            boolean flag)
    {
        // 获取消息中唯一的医嘱
        ProOrderBatch proOrderBatch = poorin200901uv03.getProOrders().get(0);

        // 就诊次数
        String visitTimes = proOrderBatch.getMedicaVisit().get(0).getVisitTimes();
        // 域ID
        String domainId = proOrderBatch.getDomainID();

        patientLid = proOrderBatch.getPatientLid();

        // 就诊信息取得
        visitResult = commonService.mediVisit(domainId, patientLid, visitTimes,
        		proOrderBatch.getOrganizationCode(), proOrderBatch.getMedicaVisit().get(0).getVisitOrdNo()
        		, proOrderBatch.getMedicaVisit().get(0).getVisitType());
        // 新增标识，用来判断消息的场合
        String newUpFlag = poorin200901uv03.getNewUpFlag();
        logger.debug("手术    {}  场合", newUpFlag);
        // 不同的场景，判断
        if (visitResult == null)
        {
            logger.error("MessageId:{};就诊表中不存在数据,患者本地ID:" + patientLid + "域ID:"
                + domainId + "就诊次数:" + visitTimes,
                    poorin200901uv03.getMessage().getId());
            if (flag)
            {
                loggerBS007.error("Message:{},checkDependency:{}",
                        poorin200901uv03.toString(), "就诊表中不存在数据,患者本地ID:"
                            + patientLid + "域ID:" + domainId + "就诊次数:"
                            + visitTimes);
            }
            return false;
        }
        else
        {
            return isExist(poorin200901uv03, newUpFlag, flag);
            // $Author :tong_meng
            // $Date : 2012/9/25 14:33$
            // [BUG]0009863 ADD BEGIN 增加new场合的判断
            // 如果是门诊新增情况，返回true
            /*
             * if (isOutpatientNew(domainId, newUpFlag)) flag = true;
             */
            // [BUG]0009863 ADD BEGIN 增加new场合的判断
        }
    }

    /**
     * 向数据库中存手术医嘱
     */
    @Override
    @Transactional
    public void saveOrUpdate(POORIN200901UV03 poorin200901uv03)
    {
        // $Author :jin_peng
        // $Date : 2012/09/17 15:07$
        // [BUG]0009718 MODIFY BEGIN
        // 将消息取出
/*        ProOrderBatch proOrderBatch = poorin200901uv03.getProOrders().get(0);
        ProcedureOrder procedureOrder = null;*/

        if (isNewMessage(poorin200901uv03)
        		|| isUpdateMessage(poorin200901uv03))
        {
            // 更新或者保存
/*            procedureOrder = getProcedureOrder(proOrderBatch,
                    poorin200901uv03.getNewUpFlag());
            surgicalProcedureService.saveOrUpdateProRequest(procedureOrder,
                    proOrderExistFlag);*/
        	List<ProcedureOrder> orderList = getProcedureOrderList(poorin200901uv03);
        	surgicalProcedureService.saveOrUpdateProList(orderList, proOrderExistFlag);
        }
        else if (isDeleteMessage(poorin200901uv03))
        {
            // delete，删除
            commonService.updatePartiallyAll(proRequestDeleteList,
                    "deleteFlag", "deleteTime", "deleteby");
        }
        // [BUG]0009718 ADD END

        // $Author:wei_peng
        // $Date:2012/11/06 11:00
        // [BUG]0011030 ADD BEGIN
        // 新增时保存开嘱医生和对应患者的关系
        if (isNewMessage(poorin200901uv03))
        {
            for (PatientDoctor patientDoctor : patientDoctorList)
            {
                commonService.save(patientDoctor);
            }
        }
        // [BUG]0011030 ADD END
    }

    /**
     * 将手术消息里插入或者更新的数据，封装到实体里
     * 
     * @param proOrderBatch
     * @param medicalVisit
     * @param poorin200901uv03
     * @return 要插入或者更新的实体
     */
    @Deprecated
    private ProcedureOrder getProcedureOrder(ProOrderBatch proOrderBatch,
            String newUpFlag)
    {
        logger.debug("患者本地ID ：{}", patientLid);
        // $Author :tongmeng
        // $Date : 2012/5/9 13:45$
        // [BUG]0006142 MODIFY BEGIN
        ProcedureOrder procedureOrder = null;

        // $Author :jin_peng
        // $Date : 2012/09/17 15:07$
        // [BUG]0009718 DELETE BEGIN
        // 取系统当前时间
        // Date systemDate = DateUtils.getSystemTime();
        // [BUG]0009718 DELETE END
        List<Object> proRequestList = null;
        // 门诊新增
        if (isUpdateMessage(newUpFlag))
        {
            // 通过申请单号，域ID，患者本地ID 查询手术医嘱表
            proRequestList = surgicalProcedureService.getProRequest(
                    proOrderBatch.getRequestNo(), proOrderBatch.getDomainID(),
                    patientLid,proOrderBatch.getOrganizationCode(), visitResult.getVisitSn());
        }
        if (proRequestList != null && proRequestList.size() > 0)
        {
            procedureOrder = (ProcedureOrder) proRequestList.get(0);

            // $Author: tong_meng
            // $Date: 2013/8/30 15:00
            // [BUG]0036757 ADD BEGIN
            procedureOrder.setUpdateby(serviceId); // 设置创建人
            // [BUG]0036757 ADD END

            proOrderExistFlag = true;
        }
        else
        {
            procedureOrder = new ProcedureOrder();
            // $Author :tongmeng
            // $Date : 2012/5/9 13:45$
            // [BUG]0006119 MODIFY BEGIN
            // 执行科室
            procedureOrder.setExecDept(proOrderBatch.getExecuteDeptNo());
            // [BUG]0006119 MODIFY END

            // $Author :tong_meng
            // $Date : 2012/7/1 16:33$
            // [BUG]0007418 ADD BEGIN
            // 执行科室名称
//            procedureOrder.setExecDeptName(proOrderBatch.getExecuteDeptName());
            if(DictionaryCache.getDictionary(Constants.DOMAIN_DEPARTMENT) != null){
            	procedureOrder.setExecDeptName(DictionaryCache.getDictionary(
                        Constants.DOMAIN_DEPARTMENT).get(
                        		proOrderBatch.getExecuteDeptNo()));
            }
            // [BUG]0007418 ADD BEGIN

            // $Author:wei_peng
            // $Date:2012/7/17 17:50
            // [BUG]0008057 ADD BEGIN
            // 申请单编号
            if(proOrderBatch.getRequestNo() != null){
            	procedureOrder.setRequestNo(proOrderBatch.getRequestNo());
            }

            procedureOrder.setOrderLid(proOrderBatch.getOrderLid());
            // [BUG]0008057 ADD END

            // Author :jin_peng
            // Date : 2012/11/14 14:56
            // [BUG]0011478 MODIFY BEGIN
            String orderStatus = null;
            String orderStatusName = null;

            // 门诊医嘱状态为已下达
/*            if (Constants.lstDomainOutPatient().contains(proOrderBatch.getDomainID()) 
            		&& Constants.lstVisitTypeOutPatient().contains(proOrderBatch.getMedicaVisit().get(0).getVisitType()))
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

            // 港大住院医嘱开立时执行状态和门诊一样，都为已下达，接入医嘱确认消息后才更改为医嘱已确认。
         	orderStatus = Constants.ORDER_STATUS_ISSUE;

         	orderStatusName = DictionaryCache.getDictionary(
         		Constants.DOMAIN_ORDER_STATUS).get(
         		Constants.ORDER_STATUS_ISSUE);
            // $Author:wei_peng
            // $Date:2012/10/19 13:39
            // [BUG]0010589 ADD BEGIN
            // 医嘱状态代码
            procedureOrder.setOrderStatus(orderStatus);
            // 医嘱状态名称
            procedureOrder.setOrderStatusName(orderStatusName);
            // [BUG]0010589 ADD END
            // [BUG]0011478 MODIFY END

            // 更新回数，将更新回数设置成1
            procedureOrder.setUpdateCount(Constants.INSERT_UPDATE_COUNT);
            // 创建时间
            procedureOrder.setCreateTime(systemDate);

            // $Author: tong_meng
            // $Date: 2013/8/30 15:00
            // [BUG]0036757 ADD BEGIN
            procedureOrder.setCreateby(DataMigrationUtils.getDataMigration() + serviceId); // 设置创建人
            // [BUG]0036757 ADD END

            // $Author:wei_peng
            // $Date:2012/11/06 10:52
            // [BUG]0011030 ADD BEGIN
            if (proOrderBatch.getRequestPersonId() != null
                && !proOrderBatch.getRequestPersonId().isEmpty())
            {
                PatientDoctor patientDoctor = commonWriterUtils.getPatientDoctor(
                        visitResult.getVisitSn(), visitResult.getPatientSn(),
                        proOrderBatch.getRequestPersonId(),
                        proOrderBatch.getRequestPersonName(), systemDate);
                if (patientDoctor != null)
                {
                    // $Author:jin_peng
                    // $Date:2013/02/06 13:38
                    // [BUG]0013909 MODIFY BEGIN
                    // 如果该医生未被处理则进行添加操作
                    if (!commonWriterUtils.isExistsPatientDoctor(
                            filterPatientDoctorList, visitResult.getVisitSn(),
                            visitResult.getPatientSn(),
                            proOrderBatch.getRequestPersonId()))
                    {
                        // $Author: tong_meng
                        // $Date: 2013/8/30 15:00
                        // [BUG]0036757 ADD BEGIN
                        patientDoctor.setCreateby(DataMigrationUtils.getDataMigration() + serviceId); // 设置创建人
                        // [BUG]0036757 ADD END

                        patientDoctorList.add(patientDoctor);
                    }

                    // [BUG]0013909 MODIFY END
                }
            }
            // [BUG]0011030 ADD END

            // $Author:wei_peng
            // $Date:2012/11/13 17:33
            // [BUG]0011421 ADD BEGIN
            // 医嘱交费状态编码

            /*
             * procedureOrder.setChargeStatusCode(Constants.CHARGE_STATUS_NOTCHARGE
             * ); // 医嘱交费状态名称
             * procedureOrder.setChargeStatusName(DictionaryCache.getDictionary(
             * Constants.ORDER_CHARGE_STATUS).get(
             * Constants.CHARGE_STATUS_NOTCHARGE));
             */

            // [BUG]0011421 ADD END

        }
        // [BUG]0006142 MODIFY END
        // 就诊内部序列号
        procedureOrder.setVisitSn(visitResult.getVisitSn());
        // 患者内部序列号
        procedureOrder.setPatientSn(visitResult.getPatientSn());
        logger.debug("就诊内部序列号  {} ,患者内部序列号   {}  ", visitResult.getVisitSn(),
                visitResult.getPatientSn());
        // 患者域ID
        procedureOrder.setPatientDomain(proOrderBatch.getDomainID());
        // 患者Lid
        procedureOrder.setPatientLid(patientLid);
        // 长期或者临时
        procedureOrder.setTemporaryFlag(null);
        // $Author :tongmeng
        // $Date : 2012/5/9 13:45$
        // [BUG]0006119 ADD BEGIN
        // 手术名称代码
        procedureOrder.setOperationCode(proOrderBatch.getOperationNameCode());
        // [BUG]0006119 ADD END

        // $Author :jin_peng
        // $Date : 2012/10/8 13:15$
        // [BUG]0037310 MODIFY BEGIN
        // 设计书升级为his从字典中去值给手术名称
        // $Author :tongmeng
        // $Date : 2012/5/9 13:45$
        // [BUG]0037310 MODIFY BEGIN
        /**String operationName = DictionaryCache.getDictionary(
                Constants.DOMAIN_PROCEDURE).get(
                proOrderBatch.getOperationNameCode());
        if (StringUtils.isEmpty(operationName))
        {
            operationName = proOrderBatch.getOperationName();
        }*/
        // 手术名称
        procedureOrder.setOperationName(proOrderBatch.getOperationName());
        // [BUG]0037310 MODIFY END
        // 麻醉code
        procedureOrder.setAnesthesiaCode(proOrderBatch.getAnesthesiaCode());
        // 麻醉名称
        procedureOrder.setAnesthesiaName(proOrderBatch.getAnesthesiaName());
        // [BUG]0037310 MODIFY END

        // Author :jia_yanqing
        // Date : 2013/6/27 13:45
        // [BUG]0034166 ADD BEGIN
        // 医嘱编码
        procedureOrder.setOrderCode(proOrderBatch.getOperationNameCode());
        // [BUG]0034166 ADD EMD

        // $Author :tong_meng
        // $Date : 2012/9/26 9:30$
        // [BUG]0009897 ADD BEGIN
        // 医嘱名称，也是手术名称
        procedureOrder.setOrderName(proOrderBatch.getOperationName());
        // 医嘱类型（手术类）
        procedureOrder.setOrderType(Constants.PROCEDURE_ORDER);
        // $Author :jia_yanqing
        // $Date : 2012/9/29 9:50$
        // [BUG]0010210 ADD BEGIN
        // 医嘱类型名称
        String orderTypeName = DictionaryUtils.getNameFromDictionary(Constants.DOMAIN_ORDER_TYPE, 
        		Constants.PROCEDURE_ORDER);
        procedureOrder.setOrderTypeName(orderTypeName);
        // [BUG]0010210 ADD END

        if(proOrderBatch.getWardsId() != null){
            // 病区编码
            procedureOrder.setWardsId(proOrderBatch.getWardsId());
            // 病区名称
            String wardsName = DictionaryUtils.getNameFromDictionary(Constants.DOMAIN_WARD, 
            		proOrderBatch.getWardsId(), proOrderBatch.getWardsName());
            procedureOrder.setWardsName(wardsName);
        }

        // Author :jia_yanqing
        // Date : 2012/12/24 10:50
        // [BUG]0012776 MODIFY BEGIN
        // 床号
        procedureOrder.setBedNo(proOrderBatch.getBedNo());
        // [BUG]0012776 MODIFY END
        if(proOrderBatch.getRequestDeptNo() != null){
            // 开嘱科室编码
            procedureOrder.setOrderDept(proOrderBatch.getRequestDeptNo());
            // 开嘱科室名称
            String orderDeptName = DictionaryUtils.getNameFromDictionary(Constants.DOMAIN_DEPARTMENT, 
            		proOrderBatch.getRequestDeptNo(), proOrderBatch.getRequestDeptName());
            procedureOrder.setOrderDeptName(orderDeptName);
        }

        // [BUG]0009897 ADD END

        // 手术等级
        procedureOrder.setProcedureLevel(proOrderBatch.getOperationLevel());

        // $Author :tong_meng
        // $Date : 2012/7/1 16:33$
        // [BUG]0007418 ADD BEGIN
        // 手术等级名称
        procedureOrder.setProcedureLevelName(proOrderBatch.getOperationLevelName());
        // [BUG]0007418 ADD BEGIN

        // 建议执行时间
        procedureOrder.setProposalExecTime(DateUtils.stringToDate(proOrderBatch.getSurgicalTime()));

        // $Author :tongmeng
        // $Date : 2012/5/9 13:45$
        // [BUG]0006119 ADD BEGIN
        // 预定执行时间
        procedureOrder.setPlanExecTime(DateUtils.stringToDate(proOrderBatch.getSurgicalTime()));
        // [BUG]0006119 ADD END

        // 注意事项
        procedureOrder.setPrecautions(proOrderBatch.getNoticeItems());

        // 手术申请说明(描述)
        procedureOrder.setDescription(proOrderBatch.getDescript());
        if(proOrderBatch.getRequestPersonId() != null){
            // 申请医师编号
            procedureOrder.setOrderPerson(proOrderBatch.getRequestPersonId());
            String orderPersonName = DictionaryUtils.getNameFromDictionary(
            		Constants.DOMAIN_STAFF, proOrderBatch.getRequestPersonId(), proOrderBatch.getRequestPersonName());
            // 申请医生名称
            procedureOrder.setOrderPersonName(orderPersonName);
        }
        // 将传过来的时间格式化
        if(proOrderBatch.getRequestDate() != null){
            // 申请时间
            procedureOrder.setRequestDate(DateUtils.stringToDate(proOrderBatch.getRequestDate()));
        }
        // $Author :tong_meng
        // $Date : 2012/5/9 13:45$
        // [BUG]0006119 ADD BEGIN
        if(proOrderBatch.getRequestDate() != null){
        	// 医嘱开嘱时间
            procedureOrder.setOrderTime(DateUtils.stringToDate(proOrderBatch.getRequestDate()));
        }       
        // [BUG]0006253 ADD END

        // $Author :tong_meng
        // $Date : 2012/5/16 13:45$
        // [BUG]0006119 ADD BEGIN
        // 确认人ID
        procedureOrder.setConfirmPerson(proOrderBatch.getVerifyPersonID());
        // 确认人姓名
        procedureOrder.setConfirmPersonName(proOrderBatch.getVerifyPersonName());
        // 确认时间
        procedureOrder.setConfirmTime(DateUtils.stringToDate(proOrderBatch.getVerifyTime()));
        // [BUG]0006253 ADD END

        // $Author :tong_meng
        // $Date : 2013/8/9 14:30$
        // [BUG]0035986 ADD BEGIN
        // 是否加急
        procedureOrder.setUrgentFlag(StringUtils.getConversionValue(proOrderBatch.getUrgency()));
        // 是否皮试
        procedureOrder.setSkinTestFlag(StringUtils.getConversionValue(proOrderBatch.getSkinTestFlag()));
        // 是否药观
        procedureOrder.setMedViewFlag(StringUtils.getConversionValue(proOrderBatch.getMedViewFlag()));
        // [BUG]0035986 ADD END
        // 医疗机构编码
        procedureOrder.setOrgCode(proOrderBatch.getOrganizationCode());
        // 医疗机构名称
        procedureOrder.setOrgName(proOrderBatch.getOrganizationName());
        // 更新回数
        logger.debug("申请单编号为   requestNo {}", proOrderBatch.getRequestNo());

        // 更新时间
        procedureOrder.setUpdateTime(systemDate);
        // 删除标志
        procedureOrder.setDeleteFlag(Constants.NO_DELETE_FLAG);
        // 手术性质编码
        procedureOrder.setOperationPropertyCode(proOrderBatch.getOperationKind());
        // 手术性质名称
        procedureOrder.setOperationPropertyName(proOrderBatch.getOperationKindName());
        // 房间号
        procedureOrder.setRoomNo(proOrderBatch.getRoomNo());
        // 预计手术用时
//        procedureOrder.setPlanOperationUseTime(StringUtils.strToBigDecimal(proOrderBatch.getUserTime(),"预计手术用时"));
        procedureOrder.setPlanOperationUseTime(proOrderBatch.getUserTime());
        if(proOrderBatch.getDirectorCode() != null){
            // 指导者编码
            procedureOrder.setDirectorCode(proOrderBatch.getDirectorCode());
            // 指导者名称
            procedureOrder.setDirectorName(DictionaryUtils.getNameFromDictionary(
            		Constants.DOMAIN_STAFF, proOrderBatch.getAssistant1Code(), proOrderBatch.getDirectorName()));
            
        }
        if(proOrderBatch.getAssistant1Code() != null){
            // 助手1编码
            procedureOrder.setAssistantfirCode(proOrderBatch.getAssistant1Code());
            // 助手1编码
            procedureOrder.setAssistantfirName(DictionaryUtils.getNameFromDictionary(
            		Constants.DOMAIN_STAFF, proOrderBatch.getAssistant1Code(), proOrderBatch.getAssistant1Name()));
        }
        if(proOrderBatch.getAssistant2Code() != null){
            // 助手2编码
            procedureOrder.setAssistantsecCode(proOrderBatch.getAssistant2Code());
            // 助手2编码
            procedureOrder.setAssistantsecName(DictionaryUtils.getNameFromDictionary(
            		Constants.DOMAIN_STAFF, proOrderBatch.getAssistant1Code(), proOrderBatch.getAssistant2Name()));
        }
        if(proOrderBatch.getAssistant3Code() != null){
            // 助手3编码
            procedureOrder.setAssistantthiCode(proOrderBatch.getAssistant3Code());
            // 助手3编码
            procedureOrder.setAssistantthiName(DictionaryUtils.getNameFromDictionary(
            		Constants.DOMAIN_STAFF, proOrderBatch.getAssistant1Code(), proOrderBatch.getAssistant3Name()));
        }

        // 洗手护士编码
        procedureOrder.setScrubNurseCode(proOrderBatch.getScrubNurseCode());
        // 洗手护士名称
        procedureOrder.setScrubNurseName(proOrderBatch.getScrubNurseName());
        // 巡回护士编码
        procedureOrder.setCirculatingNurseCode(proOrderBatch.getCirculatingNurseCode());
        // 巡回护士名称
        procedureOrder.setCirculatingNurseName(proOrderBatch.getCirculatingNurseName());
        // 手术台
        procedureOrder.setOperationTable(proOrderBatch.getOperationTable());
        // 台次
        procedureOrder.setTableTimes(proOrderBatch.getTableSeq());
        // 是否计划
        procedureOrder.setPlanFlag(StringUtils.getConversionValue(proOrderBatch.getPlanFlag()));
        // 非计划原因
        procedureOrder.setNoPlanReason(proOrderBatch.getNoPlanReason());
        // 手术医师编码
        procedureOrder.setOperatorCode(proOrderBatch.getOperatorCode());
        // 手术医师名称
        procedureOrder.setOperatorName(proOrderBatch.getOperatorName());
        // 确认时间 
        procedureOrder.setOperationConfigDate(DateUtils.stringToDate(proOrderBatch.getConfigTime()));
        return procedureOrder;
    }

    /**
     * 根据消息返回手术医嘱列表
     * */
    private List<ProcedureOrder> getProcedureOrderList(POORIN200901UV03 poorin200901uv03){
    	logger.debug("患者本地ID ：{}", patientLid);
    	List<ProcedureOrder> procedureOrderList = new ArrayList<ProcedureOrder>();
    	for(ProOrderBatch proOrderDto : poorin200901uv03.getProOrders()){
    		ProcedureOrder procedureOrder = surgicalProcedureService.getProcedureOrder(visitResult.getVisitSn().toString(), proOrderDto.getOrderLid());
    		if(procedureOrder != null){
    			// 医嘱已存在
    			// 设置更新系统
    			procedureOrder.setUpdateby(serviceId);
    			proOrderExistFlag = true;
    		} 
    		else {
    			// 新建手术医嘱
    			procedureOrder = new ProcedureOrder();

                // 申请单编号
                procedureOrder.setRequestNo(proOrderDto.getRequestNo());
                procedureOrder.setOrderLid(proOrderDto.getOrderLid());
                
                String orderStatus = null;
                String orderStatusName = null;

                // 港大住院医嘱开立时执行状态和门诊一样，都为已下达，接入医嘱确认消息后才更改为医嘱已确认。
             	orderStatus = Constants.ORDER_STATUS_ISSUE;

             	orderStatusName = DictionaryCache.getDictionary(
             		Constants.DOMAIN_ORDER_STATUS).get(
             		Constants.ORDER_STATUS_ISSUE);

                // 医嘱状态代码
                procedureOrder.setOrderStatus(orderStatus);
                // 医嘱状态名称
                procedureOrder.setOrderStatusName(orderStatusName);

                // 更新回数，将更新回数设置成1
                procedureOrder.setUpdateCount(Constants.INSERT_UPDATE_COUNT);
                // 创建时间
                procedureOrder.setCreateTime(systemDate);

                procedureOrder.setCreateby(DataMigrationUtils.getDataMigration() + serviceId); // 设置创建人

                if (proOrderDto.getRequestPersonId() != null
                    && !proOrderDto.getRequestPersonId().isEmpty())
                {
                    PatientDoctor patientDoctor = commonWriterUtils.getPatientDoctor(
                            visitResult.getVisitSn(), visitResult.getPatientSn(),
                            proOrderDto.getRequestPersonId(),
                            proOrderDto.getRequestPersonName(), systemDate);
                    if (patientDoctor != null)
                    {
                        // 如果该医生未被处理则进行添加操作
                        if (!commonWriterUtils.isExistsPatientDoctor(
                                filterPatientDoctorList, visitResult.getVisitSn(),
                                visitResult.getPatientSn(),
                                proOrderDto.getRequestPersonId()))
                        {

                            patientDoctor.setCreateby(DataMigrationUtils.getDataMigration() + serviceId); // 设置创建人
                            patientDoctorList.add(patientDoctor);
                        }
                    }
                }
    		}
    		// 新增更新共同要设置的属性值
    		
    		// 就诊内部序列号
            procedureOrder.setVisitSn(visitResult.getVisitSn());
            // 患者内部序列号
            procedureOrder.setPatientSn(visitResult.getPatientSn());
            logger.debug("就诊内部序列号  {} ,患者内部序列号   {}  ", visitResult.getVisitSn(),
                    visitResult.getPatientSn());
            // 患者域ID
            procedureOrder.setPatientDomain(proOrderDto.getDomainID());
            // 患者Lid
            procedureOrder.setPatientLid(patientLid);
            // 长期或者临时
            procedureOrder.setTemporaryFlag(null);
            
			//执行科室
			procedureOrder.setExecDept(proOrderDto.getExecuteDeptNo());

            // 执行科室名称
			procedureOrder.setExecDeptName(DictionaryUtils.getNameFromDictionary(Constants.DOMAIN_DEPARTMENT, 
					proOrderDto.getExecuteDeptNo(), proOrderDto.getExecuteDeptName()));

            if(proOrderDto.getOperationNameCode() != null){
                // 手术名称代码
                procedureOrder.setOperationCode(proOrderDto.getOperationNameCode());
                
                // 手术名称
                procedureOrder.setOperationName(DictionaryUtils.getNameFromDictionary(
                		Constants.DOMAIN_PROCEDURE, proOrderDto.getOperationNameCode(), proOrderDto.getOperationName()));
            }

            if(proOrderDto.getAnesthesiaCode() != null){
                // 麻醉code
                procedureOrder.setAnesthesiaCode(proOrderDto.getAnesthesiaCode());
                
                // 麻醉名称
                procedureOrder.setAnesthesiaName(DictionaryUtils.getNameFromDictionary(
                		Constants.DOMAIN_ANESTHESIACODE, proOrderDto.getAnesthesiaCode(), proOrderDto.getAnesthesiaName()));
            }


            // 医嘱编码
            procedureOrder.setOrderCode(proOrderDto.getOperationNameCode());

            // 医嘱名称，也是手术名称
            procedureOrder.setOrderName(proOrderDto.getOperationName());
            // 医嘱类型（手术类）
            procedureOrder.setOrderType(Constants.PROCEDURE_ORDER);
            // 医嘱类型名称
            String orderTypeName = DictionaryUtils.getNameFromDictionary(Constants.DOMAIN_ORDER_TYPE, 
            		Constants.PROCEDURE_ORDER);
            procedureOrder.setOrderTypeName(orderTypeName);

            if(proOrderDto.getWardsId() != null){
                // 病区编码
                procedureOrder.setWardsId(proOrderDto.getWardsId());
                // 病区名称
                String wardsName = DictionaryUtils.getNameFromDictionary(Constants.DOMAIN_WARD, 
                		proOrderDto.getWardsId(), proOrderDto.getWardsName());
                procedureOrder.setWardsName(wardsName);
            }

            //床号
            procedureOrder.setBedNo(proOrderDto.getBedNo());

            if(proOrderDto.getRequestDeptNo() != null){
                // 开嘱科室编码
                procedureOrder.setOrderDept(proOrderDto.getRequestDeptNo());
                // 开嘱科室名称
                String orderDeptName = DictionaryUtils.getNameFromDictionary(Constants.DOMAIN_DEPARTMENT, 
                		proOrderDto.getRequestDeptNo(), proOrderDto.getRequestDeptName());
                procedureOrder.setOrderDeptName(orderDeptName);
            }
            if(proOrderDto.getOperationLevel() != null){
            	// 手术等级
                procedureOrder.setProcedureLevel(proOrderDto.getOperationLevel());
                // 手术等级名称
                procedureOrder.setProcedureLevelName(DictionaryUtils.getNameFromDictionary(
                		Constants.DOMAIN_PROCEDURE_LEVEL, proOrderDto.getOperationLevel(), proOrderDto.getOperationLevelName()));

            }
            // 建议执行时间
            procedureOrder.setProposalExecTime(DateUtils.stringToDate(proOrderDto.getSurgicalTime()));
            // 预定执行时间
            procedureOrder.setPlanExecTime(DateUtils.stringToDate(proOrderDto.getSurgicalTime()));
            // 注意事项
            procedureOrder.setPrecautions(proOrderDto.getNoticeItems());
            // 手术申请说明(描述)
            procedureOrder.setDescription(proOrderDto.getDescript());
            if(proOrderDto.getRequestPersonId() != null){
                // 申请医师编号
                procedureOrder.setOrderPerson(proOrderDto.getRequestPersonId());
                String orderPersonName = DictionaryUtils.getNameFromDictionary(
                		Constants.DOMAIN_STAFF, proOrderDto.getRequestPersonId(), proOrderDto.getRequestPersonName());
                // 申请医生名称
                procedureOrder.setOrderPersonName(orderPersonName);
            }
            // 将传过来的时间格式化
            if(proOrderDto.getRequestDate() != null){
                // 申请时间
                procedureOrder.setRequestDate(DateUtils.stringToDate(proOrderDto.getRequestDate()));
            }
            if(proOrderDto.getRequestDate() != null){
            	// 医嘱开嘱时间
                procedureOrder.setOrderTime(DateUtils.stringToDate(proOrderDto.getRequestDate()));
            }       
            if(proOrderDto.getVerifyPersonID() != null){
                // 确认人ID
                procedureOrder.setConfirmPerson(proOrderDto.getVerifyPersonID());
                
                // 确认人姓名
                procedureOrder.setConfirmPersonName(DictionaryUtils.getNameFromDictionary(
                		Constants.DOMAIN_STAFF, proOrderDto.getVerifyPersonID(), proOrderDto.getVerifyPersonName()));
            }
            // 确认时间
            procedureOrder.setConfirmTime(DateUtils.stringToDate(proOrderDto.getVerifyTime()));
            if(proOrderDto.getUrgency() != null){
            	
            }
            // 是否加急
            procedureOrder.setUrgentFlag(proOrderDto.getUrgency() == null ? Constants.NO_URGENT_FLAG : Constants.URGENT_FLAG);
            // 是否皮试
            procedureOrder.setSkinTestFlag(StringUtils.getConversionValue(proOrderDto.getSkinTestFlag()));
            // 是否药观
            procedureOrder.setMedViewFlag(StringUtils.getConversionValue(proOrderDto.getMedViewFlag()));
            // 医疗机构编码
            procedureOrder.setOrgCode(proOrderDto.getOrganizationCode());
            // 医疗机构名称
            procedureOrder.setOrgName(proOrderDto.getOrganizationName());
            // 更新回数
            logger.debug("申请单编号为   requestNo {}", proOrderDto.getRequestNo());

            // 更新时间
            procedureOrder.setUpdateTime(systemDate);
            // 删除标志
            procedureOrder.setDeleteFlag(Constants.NO_DELETE_FLAG);
            if(proOrderDto.getOperationKind() != null){
            	String operationPropertyCode = proOrderDto.getOperationKind();
                // 手术性质编码
                procedureOrder.setOperationPropertyCode(operationPropertyCode);
                String operationPropertyName = operationPropertyCode.equals("1") ? "急诊" : "择期";
                // 手术性质名称
                procedureOrder.setOperationPropertyName(operationPropertyName);
            }

            // 房间号
            procedureOrder.setRoomNo(proOrderDto.getRoomNo());
            // 预计手术用时
//            procedureOrder.setPlanOperationUseTime(StringUtils.strToBigDecimal(proOrderDto.getUserTime(),"预计手术用时"));
            procedureOrder.setPlanOperationUseTime(proOrderDto.getUserTime());
            if(proOrderDto.getDirectorCode() != null){
                // 指导者编码
                procedureOrder.setDirectorCode(proOrderDto.getDirectorCode());
                // 指导者名称
                procedureOrder.setDirectorName(DictionaryUtils.getNameFromDictionary(
                		Constants.DOMAIN_STAFF, proOrderDto.getAssistant1Code(), proOrderDto.getDirectorName()));
                
            }
            if(proOrderDto.getAssistant1Code() != null){
                // 助手1编码
                procedureOrder.setAssistantfirCode(proOrderDto.getAssistant1Code());
                // 助手1编码
                procedureOrder.setAssistantfirName(DictionaryUtils.getNameFromDictionary(
                		Constants.DOMAIN_STAFF, proOrderDto.getAssistant1Code(), proOrderDto.getAssistant1Name()));
            }
            if(proOrderDto.getAssistant2Code() != null){
                // 助手2编码
                procedureOrder.setAssistantsecCode(proOrderDto.getAssistant2Code());
                // 助手2编码
                procedureOrder.setAssistantsecName(DictionaryUtils.getNameFromDictionary(
                		Constants.DOMAIN_STAFF, proOrderDto.getAssistant1Code(), proOrderDto.getAssistant2Name()));
            }
            if(proOrderDto.getAssistant3Code() != null){
                // 助手3编码
                procedureOrder.setAssistantthiCode(proOrderDto.getAssistant3Code());
                // 助手3编码
                procedureOrder.setAssistantthiName(DictionaryUtils.getNameFromDictionary(
                		Constants.DOMAIN_STAFF, proOrderDto.getAssistant1Code(), proOrderDto.getAssistant3Name()));
            }
            // 洗手护士编码
            procedureOrder.setScrubNurseCode(proOrderDto.getScrubNurseCode());
            // 洗手护士名称
            procedureOrder.setScrubNurseName(proOrderDto.getScrubNurseName());
            // 巡回护士编码
            procedureOrder.setCirculatingNurseCode(proOrderDto.getCirculatingNurseCode());
            // 巡回护士名称
            procedureOrder.setCirculatingNurseName(proOrderDto.getCirculatingNurseName());
            // 手术台
            procedureOrder.setOperationTable(proOrderDto.getOperationTable());
            // 台次
            procedureOrder.setTableTimes(proOrderDto.getTableSeq());
            // 是否计划
            procedureOrder.setPlanFlag(StringUtils.getConversionValue(proOrderDto.getPlanFlag()));
            // 非计划原因
            procedureOrder.setNoPlanReason(proOrderDto.getNoPlanReason());
            if(proOrderDto.getOperatorCode() != null){
                // 手术医师编码
                procedureOrder.setOperatorCode(proOrderDto.getOperatorCode());
                
                // 手术医师名称
                procedureOrder.setOperatorName(DictionaryUtils.getNameFromDictionary(
                		Constants.DOMAIN_STAFF, proOrderDto.getOperatorCode(), proOrderDto.getOperatorName()));
            }

            // 确认时间 
            procedureOrder.setOperationConfigDate(DateUtils.stringToDate(proOrderDto.getConfigTime()));
    		
            // 将手术医嘱添加到医嘱表中
    		procedureOrderList.add(procedureOrder);
    	}
    	return procedureOrderList;
    }
    
    /**
     * 手术new场合，判断就诊表中是否存在相应的数据，判断是否存在这条申请单记录
     * 手术update场合，判断就诊表和手术医嘱表中是否存在相应的数据
     * @param poorin200901uv03   
     * @param visitResult  就诊信息
     * @param newUpFlag 判断场合的标识
     * @return  
     */
    private boolean isExist(POORIN200901UV03 poorin200901uv03,
            String newUpFlag, boolean flag)
    {
        // 获取消息中唯一的医嘱
        ProOrderBatch proOrderBatch = poorin200901uv03.getProOrders().get(0);
        // $Author :tong_meng
        // $Date : 2012/9/25 14:33$
        // [BUG]0009863 MODIFY BEGIN 增加new场合的判断
        // $Author :jin_peng
        // $Date : 2012/9/17 15:07$
        // [BUG]0009718 ADD BEGIN
        // 消息门诊删除情况
        if (isDeleteMessage(poorin200901uv03))
        {
            // 通过申请单号，域ID，患者本地ID 查询手术医嘱表
            proRequestDeleteList = surgicalProcedureService.getProRequest(
                    proOrderBatch.getRequestNo(), proOrderBatch.getDomainID(),
                    patientLid,proOrderBatch.getOrganizationCode(),visitResult.getVisitSn());

            if (proRequestDeleteList == null || proRequestDeleteList.isEmpty())
            {
                logger.error(
                        "MessageId:{};需要删除的手术申请对象不存在：{}",
                        poorin200901uv03.getMessage().getId(),
                        "申请单号：requestNo = " + proOrderBatch.getRequestNo()
                            + "; 域ID：patientDomain = "
                            + proOrderBatch.getDomainID()
                            + "; 患者本地ID：patientLid = " + patientLid);
                if (flag)
                {
                    loggerBS007.error(
                            "Message:{},checkDependency:{}",
                            proOrderBatch.toString(),
                            "需要删除的手术申请对象不存在：申请单号：requestNo = "
                                + proOrderBatch.getRequestNo()
                                + "; 域ID：patientDomain = "
                                + proOrderBatch.getDomainID()
                                + "; 患者本地ID：patientLid = " + patientLid);
                }
                return false;
            }

            // 设置手术申请逻辑删除信息
            setLogicDeleteMessages(proRequestDeleteList);

        }
        // 消息门诊更新情况
        else if (isUpdateMessage(newUpFlag))
        {
            // 通过申请单号，域ID，患者本地ID 查询手术医嘱表
        	proRequestUpdateList = surgicalProcedureService.getProRequest(
                    proOrderBatch.getRequestNo(), proOrderBatch.getDomainID(),
                    patientLid,proOrderBatch.getOrganizationCode(),visitResult.getVisitSn());

            if (proRequestUpdateList == null || proRequestUpdateList.isEmpty())
            {
                logger.error(
                        "MessageId:{};需要更新的手术申请对象不存在：{}",
                        poorin200901uv03.getMessage().getId(),
                        "申请单号：requestNo = " + proOrderBatch.getRequestNo()
                            + "; 域ID：patientDomain = "
                            + proOrderBatch.getDomainID()
                            + "; 患者本地ID：patientLid = " + patientLid);
                if (flag)
                {
                    loggerBS007.error(
                            "Message:{},checkDependency:{}",
                            proOrderBatch.toString(),
                            "需要更新的手术申请对象不存在：申请单号：requestNo = "
                                + proOrderBatch.getRequestNo()
                                + "; 域ID：patientDomain = "
                                + proOrderBatch.getDomainID()
                                + "; 患者本地ID：patientLid = " + patientLid);
                }
                return false;
            }

        }
        return true;
        // [BUG]0009718 ADD END
        // [BUG]0009863 MODIFY BEGIN
    }

    // $Author :jin_peng
    // $Date : 2012/9/17 15:07$
    // [BUG]0009718 ADD BEGIN
    /**
     * 设置手术申请逻辑删除信息 
     * @param procedureRequestDeleteList
     */
    private void setLogicDeleteMessages(List<Object> procedureRequestDeleteList)
    {
        // 循环设置相应手术申请对象的逻辑删除信息
        for (Object object : procedureRequestDeleteList)
        {
            ProcedureOrder procedureOrder = (ProcedureOrder) object;

            // 设置删除标识为已删除状态
            procedureOrder.setDeleteFlag(Constants.DELETE_FLAG);

            // 设置删除时间
            procedureOrder.setDeleteTime(systemDate);

            // $Author: tong_meng
            // $Date: 2013/8/30 15:00
            // [BUG]0036757 ADD BEGIN
            procedureOrder.setDeleteby(serviceId); // 设置创建人
            // [BUG]0036757 ADD END

        }
    }
    // [BUG]0009718 ADD END
    
    private boolean isNewMessage(POORIN200901UV03 poorin200901uv03){
    	return Constants.NEW_MESSAGE_FLAG.equals(poorin200901uv03.getNewUpFlag())
    			|| Constants.V2_NEW_MESSAGE_FLAG.equals(poorin200901uv03.getNewUpFlag());
    }
    
    private boolean isUpdateMessage(POORIN200901UV03 poorin200901uv03){
    	return Constants.UPDATE_MESSAGE_FLAG.equals(poorin200901uv03.getNewUpFlag())
    			|| Constants.V2_UPDATE_MESSAGE_FLAG.equals(poorin200901uv03.getNewUpFlag());
    }
    
    private boolean isUpdateMessage(String newUpFlag){
    	return Constants.UPDATE_MESSAGE_FLAG.equals(newUpFlag)
    			|| Constants.V2_UPDATE_MESSAGE_FLAG.equals(newUpFlag);
    }
    
    private boolean isDeleteMessage(POORIN200901UV03 poorin200901uv03){
    	return Constants.DELETE_MESSAGE_FLAG.equals(poorin200901uv03.getNewUpFlag())
    			|| Constants.V2_CANCEL_MESSAGE_FLAG.equals(poorin200901uv03.getNewUpFlag());
    }
}
