/**
 * 会诊申请消息数据接入
 * 
 * @date 2012/04/23
 * @author jia_yanqing
 * @version 1.0
 */
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

import com.yly.cdr.cache.DictionaryCache;
import com.yly.cdr.core.Constants;
import com.yly.cdr.entity.ConsultationRequest;
import com.yly.cdr.entity.MedicalVisit;
import com.yly.cdr.hl7.dto.ConsultationDept;
import com.yly.cdr.hl7.dto.poorin200901uv06.POORIN200901UV06;
import com.yly.cdr.service.CommonService;
import com.yly.cdr.service.ConsultationService;
import com.yly.cdr.util.DateUtils;
import com.yly.cdr.util.StringUtils;

@Component(value = "poorin200901uv06Writer")
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class POORIN200901UV06Writer implements BusinessWriter<POORIN200901UV06>
{

    private static final Logger logger = LoggerFactory.getLogger(POORIN200901UV06Writer.class);

    // 就诊记录
    private MedicalVisit medicalVisit;

    // 会诊申请
    private ConsultationRequest consultationRequest;

    // 患者本地ID
    private String patientLid;

    // 会诊申请单编号
    private String requestNo;

    // 获取系统时间
    private Date systemTime = DateUtils.getSystemTime();

    @Autowired
    private CommonService commonService;

    @Autowired
    private ConsultationService consultationService;
    
    // 消息id
    private String serviceId;

    /**
     * 业务数据非空检验
     */
    @Override
    public boolean validate(POORIN200901UV06 poorin200901uv06)
    {
    	// $Author :chang_xuewen
        // $Date : 2014/1/22 10:30$
        // [BUG]0042086 MODIFY BEGIN   
        String headOrgCode = poorin200901uv06.getMessage().getOrgCode();
        String orgCode = poorin200901uv06.getOrgCode();

        /*if (!headOrgCode.equals(orgCode))
        {
            logger.info("消息体与消息头中医疗机构代码不相同: {}", "orgCode = " + orgCode
                + "; headOrgCode = " + headOrgCode);

            return false;
        }*/
        if(StringUtils.isEmpty(poorin200901uv06.getOrgCode())){
        	// $Author :yang_mingjie
        	// $Date : 2014/06/26 10:09$
        	// [BUG]0045630 MODIFY BEGIN 
        	poorin200901uv06.setOrgCode(Constants.HOSPITAL_CODE);
        	poorin200901uv06.setOrgName(Constants.HOSPITAL_NAME);
        	//[BUG]0045630 MODIFY END
        }
        // [BUG]0042086 MODIFY END   
        List<ConsultationDept> consultationDepts = poorin200901uv06.getConsultationDept();
        if (consultationDepts != null && !consultationDepts.isEmpty())
        {
            for (ConsultationDept consultationDept : consultationDepts)
            {
                if (!StringUtils.isNotNullAll(
                        consultationDept.getConsultationDept(),
                        consultationDept.getConsultationDeptName(),
                        consultationDept.getConsultationPerson(),
                        consultationDept.getConsultationPersonName()))
                {
                    logger.error("\n 参与会诊科室ID不能为空："
                        + consultationDept.getConsultationDept()
                        + "\n 参与科室名称不能为空："
                        + consultationDept.getConsultationDeptName()
                        + "\n 会诊医生编码不能为空："
                        + consultationDept.getConsultationPerson()
                        + "\n 会诊医生姓名不能为空："
                        + consultationDept.getConsultationPersonName());
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * 检查消息的依赖关系
     */
    @Override
    public boolean checkDependency(POORIN200901UV06 poorin200901uv06,boolean flag)
    {
        // $Author :tongmeng
        // $Date : 2012/5/29 14:30$
        // [BUG]0006657 MODIFY BEGIN
        // 获取患者本地ID
        patientLid = poorin200901uv06.getPatientLid();
        // [BUG]0006657 MODIFY END

        // 会诊申请单编号
        requestNo = poorin200901uv06.getRequestNo();

        // 新增会诊申请消息，检查与就诊的依赖关系
        if (Constants.NEW_MESSAGE_FLAG.equals(poorin200901uv06.getNewUpFlag()))
        {
            // 获取就诊记录
            medicalVisit = getMedicalVisit(poorin200901uv06);
            if (medicalVisit != null)
            {
                logger.info("在新增会诊申请信息時相應的就診信息存在true: {}", "会诊申请信息关联消息");
                return true;
            }
            else
            {
                logger.error("在新增会诊申请信息時相應的就診信息存在false: {}", "会诊申请信息关联消息");
                return false;
            }
        }

        // 更新会诊申请消息时，检查是否存在要更新的会诊消息记录
        else if (Constants.CANCEL_MESSAGE_FLAG.equals(poorin200901uv06.getNewUpFlag()))
        {

            // 根据域代码，患者本地ID，会诊申请单编号获取会诊申请记录
            consultationRequest = getConsultationRequest(poorin200901uv06);

            // 判断要更新的会诊申请记录是否存在
            if (consultationRequest != null)
            {
                logger.info("在撤销会诊申请信息時相應的會診記錄存在true: {}", "会诊申请信息关联消息");
                return true;
            }
            else
            {
                logger.error("在撤销会诊申请信息時相應的會診記錄存在false: {}", "会诊申请信息关联消息");
                return false;
            }
        }
        else
        {
            logger.error("错误的消息交互类型：" + poorin200901uv06.getNewUpFlag());
            return false;
        }
    }

    /**
     * 依赖关系检验之后对会诊申请数据执行相应的保存或者更新操作
     * @param poorin200901uv06
     */
    @Override
    @Transactional
    public void saveOrUpdate(POORIN200901UV06 poorin200901uv06)
    {
        serviceId = poorin200901uv06.getMessage().getServiceId();

        // 获取封装后的会诊申请对象
        ConsultationRequest consultationRequest = getConsultationRequestContent(poorin200901uv06);

        if (Constants.NEW_MESSAGE_FLAG.equals(poorin200901uv06.getNewUpFlag()))
        {
            commonService.save(consultationRequest);
        }
        else if (Constants.CANCEL_MESSAGE_FLAG.equals(poorin200901uv06.getNewUpFlag()))
        {
            commonService.update(consultationRequest);
        }
    }

    /**
     * 封装新增或者更新的会诊申请对象
     * @param poorin200901uv06
     * @return 会诊申请
     */
    public ConsultationRequest getConsultationRequestContent(
            POORIN200901UV06 poorin200901uv06)
    {
        // 新建会诊申请对象
        ConsultationRequest consultationRequest = new ConsultationRequest();

        if (Constants.NEW_MESSAGE_FLAG.equals(poorin200901uv06.getNewUpFlag()))
        {
            // 就诊内部序列号
            consultationRequest.setVisitSn(medicalVisit.getVisitSn());
            // 患者内部序列号
            consultationRequest.setPatientSn(medicalVisit.getPatientSn());
            // 申请科室ID
            consultationRequest.setOrderDeptId(poorin200901uv06.getOrderDept());
            // 申请科室名称
            consultationRequest.setOrderDeptName(poorin200901uv06.getOrderDeptName());
            // 紧急程度编码
            consultationRequest.setUrgencyCode(poorin200901uv06.getUrgencyCode());
            // 紧急程度名称
            consultationRequest.setUrgencyName(poorin200901uv06.getUrgencyName());                     
            // 域代码
            consultationRequest.setPatientDomain(poorin200901uv06.getPatientDomain());
            
            // 设置会诊类型
            consultationRequest.setConsultationType(poorin200901uv06.getConsultationType());
            // 设置会诊类型名称
            consultationRequest.setConsultationTypeName(poorin200901uv06.getConsultationTypeName());

           // 设置医嘱类别ID
            consultationRequest.setOrderCode(poorin200901uv06.getOrderCode());
            // 设置医嘱类别名称
            consultationRequest.setOrderName(poorin200901uv06.getOrderName());

            // 患者本地ID
            consultationRequest.setPatientLid(patientLid);

            // 会诊申请单编号
            consultationRequest.setRequestNo(requestNo);

            // 会诊申请创建时间
            consultationRequest.setCreateTime(systemTime);

            // 设置会诊申请更新回数
            consultationRequest.setUpdateCount(Constants.INSERT_UPDATE_COUNT);

            // Author :jin_peng
            // Date : 2012/11/14 14:56
            // [BUG]0011478 MODIFY BEGIN
            String orderStatus = "";
            String orderStatusName = "";

/*            // 门诊医嘱状态为已下达
            if (Constants.PATIENT_DOMAIN_OUTPATIENT.equals(poorin200901uv06.getPatientDomain()))
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
            //会诊状态暂定为"已申请"
            orderStatusName = "会诊已申请";
            // $Author:wei_peng
            // $Date:2012/7/23 20:39
            // [BUG]0008065 ADD BEGIN
            // 设置申请状态代码
            consultationRequest.setRequestStatusCode(orderStatus);

            // 设置申请状态名称
            consultationRequest.setRequestStatusName(orderStatusName);
            // [BUG]0008065 ADD END
            
            // 创建者
            consultationRequest.setCreateby(serviceId);
        }
        else if (Constants.CANCEL_MESSAGE_FLAG.equals(poorin200901uv06.getNewUpFlag()))
        {
            // 根据域代码，患者本地ID，会诊申请单编号获取需要更新的会诊申请记录
            consultationRequest = getConsultationRequest(poorin200901uv06);
            // $Author:wei_peng
            // $Date:2012/7/23 20:39
            // [BUG]0008065 ADD BEGIN
            // 设置申请状态代码
            consultationRequest.setRequestStatusCode(Constants.ORDER_STATUS_CANCEL);

            // 设置申请状态名称
            consultationRequest.setRequestStatusName(DictionaryCache.getDictionary(
                    Constants.DOMAIN_ORDER_STATUS).get(
                    Constants.ORDER_STATUS_CANCEL));
            // [BUG]0008065 ADD END
            
            consultationRequest.setUpdateby(serviceId);
        }

        // [BUG]0011478 MODIFY END

/*        // 设置紧急程度
        consultationRequest.setUrgencyCode(poorin200901uv06.getUrgencyCode());
        // 设置紧急程度名称
        consultationRequest.setUrgencyName(poorin200901uv06.getUrgencyName());*/

        // 设置会诊理由
        consultationRequest.setReason(poorin200901uv06.getReason());

        // 设置会诊申请时间
        consultationRequest.setRequestTime(DateUtils.stringToDate(poorin200901uv06.getRequestTime()));

        // 设置申请人
        consultationRequest.setRequestDoctorId(poorin200901uv06.getRequestDoctor());

        // 设置申请人姓名
        consultationRequest.setRequestDoctorName(poorin200901uv06.getRequestDoctorName());

        // 设置会诊地点
        consultationRequest.setConsultationPlace(poorin200901uv06.getConsultationPlaceName());

        // $Author:wei_peng
        // $Date:2012/7/23 20:39
        // [BUG]0008065 ADD BEGIN
        // 会诊科室ID列表
        List<String> consultationDeptList = new ArrayList<String>();
        // 会诊科室名称列表
        List<String> consultationDeptNameList = new ArrayList<String>();
        // 会诊医生ID列表
        List<String> consultationPersonList = new ArrayList<String>();
        // 会诊医生名称列表
        List<String> consultationPersonNameList = new ArrayList<String>();
        // 会诊医院ID列表
        List<String> consultationOrgCodeList = new ArrayList<String>();
        // 会诊医院名称列表
        List<String> consultationOrgNameList = new ArrayList<String>();
        for (ConsultationDept consultationDept : poorin200901uv06.getConsultationDept())
        {
            consultationDeptList.add(consultationDept.getConsultationDept());
            consultationDeptNameList.add(consultationDept.getConsultationDeptName());
            consultationPersonList.add(consultationDept.getConsultationPerson());
            consultationPersonNameList.add(consultationDept.getConsultationPersonName());
            consultationOrgCodeList.add(consultationDept.getConsultationOrgCode());
            consultationOrgNameList.add(consultationDept.getConsultationOrgName());
        }
        // 设置会诊科室ID
        consultationRequest.setConsultationDeptId(listToString(consultationDeptList));

        // 设置会诊科室名称
        consultationRequest.setConsultationDeptName(listToString(consultationDeptNameList));

        // 设置会诊医生ID
        consultationRequest.setConsultationPersonId(listToString(consultationPersonList));

        // 设置会诊医生名称
        consultationRequest.setConsultationPersonName(listToString(consultationPersonNameList));

        // 设置会诊医疗机构ID
        consultationRequest.setConsultationOrgCode(listToString(consultationOrgCodeList));

        // 设置会诊医疗机构名称
        consultationRequest.setConsultationOrgName(listToString(consultationOrgNameList));
        // [BUG]0008065 ADD END

        // 设置会诊医院ID
        consultationRequest.setConsultationOrgCode(listToString(consultationOrgCodeList));

        // 设置会诊医生名称
        consultationRequest.setConsultationOrgName(listToString(consultationOrgNameList));
        // 设置会诊申请更新时间
        consultationRequest.setUpdateTime(systemTime);

        // 设置会诊申请删除标志
        consultationRequest.setDeleteFlag(Constants.NO_DELETE_FLAG);
        
        // 设置医疗机构代码
        consultationRequest.setOrgCode(poorin200901uv06.getOrgCode());
        
        // 设置医疗机构名称
        consultationRequest.setOrgName(poorin200901uv06.getOrgName());

        return consultationRequest;
    }

    /**
     * 得到消息关联的就诊记录
     * @param poorin200901uv06
     * @return 就诊记录
     */
    public MedicalVisit getMedicalVisit(POORIN200901UV06 poorin200901uv06)
    {
        // 患者域ID
        String patientDomain = poorin200901uv06.getPatientDomain();

        // 就诊次数
        String visitTimes = poorin200901uv06.getVisit().get(0).getVisitTimes();

        MedicalVisit medicalVisit = commonService.mediVisit(patientDomain,
                patientLid, visitTimes, poorin200901uv06.getOrgCode());

        return medicalVisit;
    }

    /**
     * 得到消息关联的会诊申请记录
     * @param poorin200901uv06
     * @return 会诊申请记录
     */
    public ConsultationRequest getConsultationRequest(
            POORIN200901UV06 poorin200901uv06)
    {
        // 患者域ID
        String patientDomain = poorin200901uv06.getPatientDomain();

        // 获取会诊申请记录
        ConsultationRequest consultationRequest = consultationService.getConsulationRequest(
                patientDomain, patientLid, requestNo, poorin200901uv06.getOrgCode());

        return consultationRequest;
    }

    /**
     * 将list形式的数据循环拼接成一个字符串返回
     * @return
     */
    public String listToString(List<String> list)
    {
        if (list != null && list.size() != 0)
        {
            String result = "";
            for (int i = 0; i < list.size(); i++)
            {
                result += list.get(i) + ",";
            }
            result = result.substring(0, result.length() - 1);
            return result;
        }
        else
        {
            return null;
        }
    }

}
