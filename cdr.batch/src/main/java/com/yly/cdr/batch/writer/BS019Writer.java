package com.yly.cdr.batch.writer;

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
import com.yly.cdr.entity.LabOrder;
import com.yly.cdr.entity.MedicalVisit;
import com.yly.cdr.hl7.dto.SampleContent;
import com.yly.cdr.hl7.dto.bs019.BS019;
import com.yly.cdr.service.CommonService;
import com.yly.cdr.util.DateUtils;

/**
 * 标本号信息
 * @version 1.0, 2012/08/15
 * @author jin_peng
 * @since 1.0
 */
@Component(value = "bs019Writer")
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class BS019Writer implements BusinessWriter<BS019>
{
    private Logger logger = LoggerFactory.getLogger(BS019Writer.class);

    private static final Logger loggerBS019 = LoggerFactory.getLogger("BS019");

    // 待更新标本号的相应医嘱对象集合
    private List<Object> orderList;

    // 系统当前时间
    private Date systemTime;

    @Autowired
    private CommonService commonService;
    
    private String orgCode;

    // 就诊信息
    private MedicalVisit visitResult;
    
    /**
     * 初始参数设置
     */
    public BS019Writer()
    {
        systemTime = DateUtils.getSystemTime();
    }

    /**
     * 非业务数据校验
     */
    @Override
    public boolean validate(BS019 bs019)
    {
        // Author :jia_yanqing
        // Date : 2013/1/22 15:00
        // [BUG]0042085 MODIFY BEGIN
        if(bs019.getOrgCode() == null || "".equals(bs019.getOrgCode()))
        {
            orgCode = Constants.HOSPITAL_CODE;
        }
    	// $Author :chang_xuewen
        // $Date : 2013/12/05 11:00$
        // [BUG]0040271 ADD BEGIN
        /*orgCode = bs019.getOrgCode();
    	if(!bs019.getOrgCode().equals(bs019.getMessage().getOrgCode())){
    		loggerBS019.error(
                    "Message:{}, validate:{}",
                    bs019.toString(),
                    "消息头与消息体中的医疗机构代码不一致：头：OrgCode = " 
                    + bs019.getMessage().getOrgCode() 
                    + " ，体：OrgCode ="
                    + bs019.getOrgCode());
        	return false;
        }*/
    	// [BUG]0040271 ADD END
        // [BUG]0042085 MODIFY BEGIN
        return true;
    }

    /**
     * 业务校验（关联关系）
     */
    @Override
    public boolean checkDependency(BS019 bs019, boolean flag)
    {
        // 消息中医嘱号，域ID对应的检验医嘱信息对象集合
        List<Object> validatingList = null;
        // 消息中的医嘱号
        String orderNo = null;
        // 消息中的域ID
        String patientDomain = bs019.getPatientDomain();
        // 标本号
        String sampleNo = bs019.getSampleNo();
        // 标本号信息内容对象集合
        List<SampleContent> sampleList = bs019.getSampleContent();
        // 创建待更新标本号的相应医嘱对象集合
        orderList = new ArrayList<Object>();
        // 需要更新的检验医嘱
        LabOrder labOrder = null;

        // 就诊数据取得
        visitResult = commonService.mediVisit(patientDomain, bs019.getPatientLid(), bs019.getVisitTime(),
        		bs019.getOrgCode(), bs019.getVisitOrdNo()
        		,bs019.getVisitType());
        
        if (visitResult == null)
        {
            logger.error("MessageId:{};就诊表中不存在数据,患者本地ID:" +  bs019.getPatientLid() + "域ID:"
                + patientDomain + "就诊次数:" + bs019.getVisitTime(),
                bs019.getMessage().getId());
            if (flag)
            {
                loggerBS019.error("Message:{},checkDependency:{}",
                		bs019.toString(), "就诊表中不存在数据,患者本地ID:"
                            + bs019.getPatientLid() + "域ID:" + patientDomain + "就诊次数:"
                            + bs019.getVisitTime());
            }
            return false;
        }
        // 循环验证消息对应的检验医嘱是否存在，并将存在的医嘱添加到待更新的医嘱对象集合中，如果其中任何医嘱不存在则验证失败。
        for (SampleContent sample : sampleList)
        {
            // 获取消息中的本地医嘱号
            orderNo = sample.getOrderNo();
            // 创建获取相应检验医嘱的查询条件
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("orderLid", orderNo);
            map.put("visitSn", visitResult.getVisitSn());
            map.put("patientDomain", patientDomain);
            map.put("deleteFlag", Constants.NO_DELETE_FLAG);
            
            // Author :jia_yanqing
            // Date : 2014/2/13 11:00
            // [BUG]0042230 MODIFY BEGIN
            if(bs019.getOrgCode().isEmpty())
            {
               map.put("orgCode", orgCode);
            }
            else
            {
               map.put("orgCode", bs019.getOrgCode());
            }
            // [BUG]0042230 MODIFY END

            // 通过相应查询条件查询出的检验医嘱对象集合
            validatingList = commonService.selectByCondition(LabOrder.class,
                    map);

            // 判断查询出的检验医嘱是否存在，如果不存在则不通过验证
            if (validatingList == null || validatingList.isEmpty())
            {
                logger.error("需要更新标本号的检验医嘱不存在！本地医嘱号：{}，域ID：{}", orderNo,
                        patientDomain);

                if (flag)
                {
                    loggerBS019.error("Message:{},checkDependency:{}",
                            bs019.toString(), "需要更新标本号的检验医嘱不存在！本地医嘱号："
                                + orderNo + "，域ID：" + patientDomain);
                }

                return false;
            }

            // 获取待更新的检验医嘱
            labOrder = (LabOrder) validatingList.get(0);

            // 将标本号与更新时间设置到该检验医嘱中
            setLabOrder(labOrder, sampleNo);

            // 将通过验证的待更新医嘱添加到待更新医嘱对象集合中
            orderList.add(labOrder);
        }

        return true;
    }

    /**
     * 根据业务进行数据库操作
     * @param bs019 标本号信息对象
     */
    @Override
    @Transactional
    public void saveOrUpdate(BS019 bs019)
    {
        // 将消息中的标本号更新到相应的检验医嘱中
        commonService.updatePartiallyAll(orderList, "sampleNo", "updateTime");
    }

    /**
     * 向待更新的检验医嘱中设置需更新的
     * @param bs019 标本号信息对象
     */
    private void setLabOrder(LabOrder labOrder, String sampleNo)
    {
        // 设置标本号
        labOrder.setSampleNo(sampleNo);

        // 设置更新时间
        labOrder.setUpdateTime(systemTime);
    }
}
