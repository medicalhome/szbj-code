package com.founder.cdr.batch.send;

//$Author :wu_biao
//$Date : 2013/03/13 
//警告通知框架 ADD BEGIN
/**
* 业务消息POCDIN000040UV01Send
* 
* @author wu_biao
*/
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

import com.founder.cdr.batch.send.db.WarningDB;
import com.founder.cdr.batch.send.others.WarningOthers;
import com.founder.cdr.cache.DictionaryCache;
import com.founder.cdr.core.Constants;
import com.founder.cdr.dto.DictionaryDto;
import com.founder.cdr.dto.LabDto;
import com.founder.cdr.entity.WarningNotice;
import com.founder.cdr.entity.WarningRecord;
import com.founder.cdr.hl7.dto.Diagnosis;
import com.founder.cdr.hl7.dto.InfectiousItem;
import com.founder.cdr.hl7.dto.InfectiousRules;
import com.founder.cdr.hl7.dto.LabReport;
import com.founder.cdr.hl7.dto.LabReportResult;
import com.founder.cdr.hl7.dto.WarningItem;
import com.founder.cdr.hl7.dto.WarningMessageLabReport;
import com.founder.cdr.hl7.dto.WarningMessageLabReportResult;
import com.founder.cdr.hl7.dto.WarningRules;
import com.founder.cdr.hl7.dto.infectiousbuild.INFECTIOUSBUILD;
import com.founder.cdr.hl7.dto.pocdin000040uv01.POCDIN000040UV01;
import com.founder.cdr.hl7.dto.warningMessage.WARNINGMESSAGE;
import com.founder.cdr.hl7.dto.warningbuild.WARNINGBUILD;
import com.founder.cdr.tool.BatchCallWebservice;
import com.founder.cdr.util.DateUtils;
import com.founder.cdr.util.StringUtils;

@Component(value = "pocdin000040uv01Send")
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class POCDIN000040UV01Send implements BusinessSend<POCDIN000040UV01>
{
    private static final Logger logger = LoggerFactory.getLogger(POCDIN000040UV01Send.class);

    // private static final String TEMPLATE_PATH =
    // "src/main/resources/warningmessage/WARNING_MESSAGE";

    private static final String MAPPING_DTO = "WARNINGBUILD";

    private static final String RULE_COMPLETED = "1";

    private static final String WARNING_SUCCESS = "1";

    private static final String UNCOMMON_FLAG = "0";

    private static final String CRISIS_FLAG = "1";

    private static final String UNCOMMON_ITEM_FLAG = "1";

    private static final String CRISIS_ITEM_FLAG = "1";

    private static final String COMPLETE_NORMAL_STATUS = "1";

    private static final String COMPLETE_EXCEPTION_STATUS = "0";

    private static final String UNCOMMON_NAME = "异常";

    private static final String CRISIS_NAME = "危急";

    private static final String MESSAGE_SERVICE_ID = "BS909";

    private static final String RULE_CONDITION_SERVICE_ID = "BS906";

    private static final String CDR_SYSTEM_CODE = "S015";

    private static final String MESSAGE_SERVICE_NAME = "警告通知信息";

    private static final String RULE_CONDITION_SERVICE_NAME = "规则校验信息条件数据";

    private static final String RULE_UNCOMMON = "ExceptionGroup";

    private static final String RULE_CRISIS = "EmergencyGroup";

 //   private static String QUEUE_NAME;

    // private static final String SEND_MESSAGE_ID = "WARNINGMESSAGE";

    private Date systemTime;
    
    private static final String LAB_PARAM1 = "checkKey";

    private static final String LAB_PARAM2 = "itemNumValue";

    private static final String LAB_PARAM3 = "unit";

    private static final String LAB_PARAM4 = "itemStrValue";
    
    private static final String LAB_PARAM5 = "checkOffice";
    
    private static final String LAB_PARAM6 = "diagnosisCode";
    
    private static final String LAB_ROUTE = "checkItem";
    
    private static final String HOSPITAL_DEPT = "0000000";
/*    @Autowired
    private WMQMessageSender sender;*/

    @Autowired
    private WarningOthers<WARNINGMESSAGE> warningOthers;

    @Autowired
    private BatchCallWebservice callWebservice;

    @Autowired
    private WarningDB warningDB;

/*    static
    {
        QUEUE_NAME = BatchCallWebservice.getWarningPropertiesValue("WARNING_QUEUE_NAME");
    }*/

    public POCDIN000040UV01Send()
    {
        systemTime = DateUtils.getSystemTime();
    }

    @Override
    public void send(POCDIN000040UV01 t) throws Exception
    {
        logger.debug("检验报告警告通知功能开始运行。。。");

        // 是否存在异常标识
        boolean isConducted = false;

        // 是否存在对应警告通知设定对象
        boolean isExistsNotice = false;

        boolean isSelectWarningRecord = false;

        boolean[] isUncommonUpdateOrException = new boolean[2];

        boolean[] isCrisisUpdateOrException = new boolean[2];

/*        boolean[] isMessageUncommonUpdateOrException = new boolean[2];

        boolean[] isMessageCrisisUpdateOrException = new boolean[2];*/

        WarningRecord warningRecord = null;

        List<Map<String, Object>> crisisList = null;

        WARNINGBUILD resultCrisisDto = null;
        //INFECTIOUSBUILD resultCrisisDto = null;
        List<Map<String, Object>> uncommonList = null;

        WARNINGBUILD resultUncommonDto = null;
        warningDB.setSysDate(systemTime);

        warningDB.selectLabResultSn(t.getReportNo(), t.getSourceSystemId(),
                t.getOrgCode());

        warningRecord = getWarningRecord(warningDB.selectWarnRecord(
                t.getVersionNumber(), DateUtils.stringToDate(t.getReportDate())));

        logger.debug("警告通知起始查询警告通知完成情况记录表成功");

        if (warningRecord != null
            && RULE_COMPLETED.equals(warningRecord.getRuleCompleted()))
        {
            logger.debug("通过用DB中的检验具体结果内容获取相应异常或危急项内容。。。");

            List<LabDto> labResultRelated = warningDB.selectWarningLabResultItem(
                    t.getReportNo(), t.getSourceSystemId(), t.getOrgCode());

            if (labResultRelated == null || labResultRelated.isEmpty())
            {
                logger.debug(
                        "在retry调用警告通知功能时，当获取数据库中存在异常或危急检验具体结果时未查询到相关记录，说明该原先存在的记录已被检验报告更新消息重置更新，所以此处理暂不继续处理，待更新检验报告消息时触发的警告通知继续处理。reportNo: {} , sourceSystemId: {}",
                        t.getReportNo(), t.getSourceSystemId() + "; orgCode: "
                            + t.getOrgCode());

                setWarningSucceeded(warningRecord);

                // 更新警告记录
                warningDB.updateWarnRecord(warningRecord);

                return;
            }

            resultUncommonDto = getNewWARNINGBUILD(t);

            uncommonList = getUncommonOrCrisisByDatabase(labResultRelated,
                    UNCOMMON_NAME);

            resultCrisisDto = getNewWARNINGBUILD(t);

            crisisList = getUncommonOrCrisisByDatabase(labResultRelated,
                    CRISIS_NAME);
        }
        else
        {
            try
            {
                logger.debug("通过调用规则接口获取相应异常或危急项内容。。。");

                // 1构建接口传入参数
/*                WARNINGBUILD warningBuild = getWarningBuildDto(t);
                INFECTIOUSBUILD infectious = null;
                infectious = getInfectiousMICDto(t);
                String conditionXml = warningOthers.callCreateByDtoInfectious(
                		warningBuild, Constants.WARNING_XML_ROOT);*/
                // 2调用WS（异常规则校验接口）
                // String ruleUncommonResult = getResultTest(conditionXml);

                logger.debug("开始获取异常设定对象内容。。。");

                // 查询db警告通知方式表，查询出相应需要发送异常值的邮箱，电话，还有各个系统的队列名称
                List<WarningNotice> uncommonNoticeValidation = warningDB.selectWarningNotice(
                        t, Constants.WARNING_EXCEPTION_FLAG, t.getOrgCode());

                if (uncommonNoticeValidation != null
                    && !uncommonNoticeValidation.isEmpty())
                {
                    isExistsNotice = true;

                    logger.debug("开始调用异常规则接口。。。");
                 // 1构建接口传入参数
                    WARNINGBUILD warningbuild = null;
                    warningbuild = getWarningBuildLABDto(t,RULE_UNCOMMON);
                    String conditionXml = warningOthers.callCreateByDtoInfectious(
                    		warningbuild, Constants.WARNING_XML_ROOT);
/*                    String ruleUncommonResult = callWebservice.checkMsg(
                            RULE_UNCOMMON, conditionXml);*/
                    // 2调用WS（异常规则校验接口）
                    // String ruleUncommonResult = getResultTest(conditionXml);
                    String ruleUncommonResult = callWebservice.ruleCheck(conditionXml);
                    logger.debug("调用异常规则接口成功。");
                    logger.debug("调用异常规则返回结果:"+ruleUncommonResult);

                    // 3返回结果映射dto
                    resultUncommonDto = (WARNINGBUILD) warningOthers.getBaseDto(
                            ruleUncommonResult, MAPPING_DTO);
/*                    resultUncommonDto = (WARNINGBUILD) warningOthers.getBaseDto(
                            ruleUncommonResult, MAPPING_DTO);*/
                    logger.debug("映射异常DTO内容为:"+resultUncommonDto);
                    uncommonList = getUncommonOrCrisis(resultUncommonDto);
                    logger.debug("判断异常调用是否需要继续"+String.valueOf(!uncommonList.isEmpty()));
                    if (uncommonList != null && !uncommonList.isEmpty())
                    {
                        // 存在异常需要做以下一系列处理
                        isConducted = true;
                    }
                }

                logger.debug("开始获取危急设定对象内容。。。");

                List<WarningNotice> crisisNoticeValidation = warningDB.selectWarningNotice(
                        t, Constants.WARNING_CRISIS_FLAG, t.getOrgCode());

                if (crisisNoticeValidation != null
                    && !crisisNoticeValidation.isEmpty())
                {
                    isExistsNotice = true;

                    logger.debug("开始调用危急规则接口。。。");
                    // 1构建接口传入参数
                    WARNINGBUILD warningbuild = null;
                    warningbuild = getWarningBuildLABDto(t,RULE_CRISIS);
                    String conditionXml = warningOthers.callCreateByDtoInfectious(
                    		warningbuild, Constants.WARNING_XML_ROOT);
                    // 调用危急规则接口
                    // String ruleCrisisResult = getResultTest(conditionXml);
                    logger.debug("调用危机规则接口输入参数:"+conditionXml);
/*                    String ruleCrisisResult = callWebservice.checkMsg(
                            RULE_CRISIS, conditionXml);*/
                    // 2调用WS（异常规则校验接口）
                    // String ruleUncommonResult = getResultTest(conditionXml);
                    String ruleCrisisResult = callWebservice.ruleCheck(conditionXml);

                    logger.debug("调用危急规则接口成功。");
                    logger.debug("调用危机规则返回结果:"+ruleCrisisResult);
                    // 3返回结果映射dto
                    resultCrisisDto = (WARNINGBUILD) warningOthers.getBaseDto(
                            ruleCrisisResult, MAPPING_DTO);
                    logger.debug("映射危机DTO内容为:"+resultCrisisDto);
                    crisisList = getUncommonOrCrisis(resultCrisisDto);
                    logger.debug("判断危机调用是否需要继续"+String.valueOf(!crisisList.isEmpty()));
                    if (crisisList != null && !crisisList.isEmpty())
                    {
                        // 存在危急需要做以下一系列处理
                        isConducted = true;
                    }
                }

                if (!isExistsNotice)
                {
                    logger.debug("检验报告警告通知对应异常危急对象设定内容均不存在，处理结束。");
                }
                else
                {
                    // 如果不存在异常则不需做以下处理
                    if (!isConducted)
                    {
                        logger.debug("检验报告警告通知规则校验后未发现异常危急值。");
                    }
                }

                if (!isExistsNotice || !isConducted)
                {
                    setWarningSucceeded(warningRecord);

                    // 新增警告记录
                    warningDB.insertWarnRecord(warningRecord,
                            t.getVersionNumber(),
                            DateUtils.stringToDate(t.getReportDate()));

                    return;
                }

                try
                {
                    setRuleCompletedFlag(warningRecord);

                    warningDB.saveRuleSign(t, resultUncommonDto,
                            resultCrisisDto, systemTime, warningRecord, logger);

                    isSelectWarningRecord = true;
                }
                catch (Exception e)
                {
                    logger.debug("保存调用规则接口成功标识与保存异常危急检验具体结果标识失败，执行retry再次重新调用规则验证接口执行保存。。。");

                    e.printStackTrace();

                    throw new Exception("保存规则验证结果失败", e);
                }
            }
            catch (Exception ex)
            {
                logger.debug("调用警告通知规则处理过程中出现异常。。。");

                ex.printStackTrace();

                throw new Exception(ex);
            }
        }

        if (isSelectWarningRecord)
        {
            warningRecord = getWarningRecord(warningDB.selectWarnRecord(
                    t.getVersionNumber(),
                    DateUtils.stringToDate(t.getReportDate())));
        }

        if (uncommonList != null && !uncommonList.isEmpty())
        {
            // 5查询db警告通知方式表，查询出相应需要发送异常值的邮箱，电话，还有各个系统的队列名称
            List<WarningNotice> uncommonNoticeList = warningDB.selectWarningNotice(
                    t, Constants.WARNING_EXCEPTION_FLAG, t.getOrgCode());

            if (uncommonNoticeList != null && !uncommonNoticeList.isEmpty())
            {
                warningRecord = getWarningRecord(warningRecord);

                Map<String, String> addressMap = warningDB.getAddress(
                        uncommonNoticeList,
                        String.valueOf(DateUtils.getDayOfWeek(systemTime)));

                //String uncommonSystemIds = addressMap.get("systemId");
              //江苏版本不需要发送消息，所以注释此段代码
/*                isMessageUncommonUpdateOrException = sendMessage(t,
                        uncommonList, uncommonSystemIds, warningRecord,
                        UNCOMMON_FLAG);*/

                isUncommonUpdateOrException = sendMailOrMobile(
                        resultUncommonDto, uncommonList, uncommonNoticeList,
                        warningRecord, UNCOMMON_FLAG, addressMap,t);
            }
        }

        if (crisisList != null && !crisisList.isEmpty())
        {
            List<WarningNotice> crisisNoticeList = warningDB.selectWarningNotice(
                    t, Constants.WARNING_CRISIS_FLAG, t.getOrgCode());

            if (crisisNoticeList != null && !crisisNoticeList.isEmpty())
            {
                warningRecord = getWarningRecord(warningRecord);

                Map<String, String> addressMap = warningDB.getAddress(
                        crisisNoticeList,
                        String.valueOf(DateUtils.getDayOfWeek(systemTime)));

               // String crisisSystemIds = addressMap.get("systemId");
              //江苏版本不需要发送消息，所以注释此段代码
/*                isMessageCrisisUpdateOrException = sendMessage(t, crisisList,
                        crisisSystemIds, warningRecord, CRISIS_FLAG);*/

                isCrisisUpdateOrException = sendMailOrMobile(resultCrisisDto,
                        crisisList, crisisNoticeList, warningRecord,
                        CRISIS_FLAG, addressMap, t);
            }
        }

        if (warningRecord != null)
        {
        	//江苏版本不需要发送消息，所以修改此段代码
/*            if (isUncommonUpdateOrException[0] || isCrisisUpdateOrException[0]
                || isMessageUncommonUpdateOrException[0]
                || isMessageCrisisUpdateOrException[0])*/
            if (isUncommonUpdateOrException[0] || isCrisisUpdateOrException[0])
            {
            	//江苏版本不需要发送消息，所以修改此段代码
/*                if (!isMessageUncommonUpdateOrException[1]
                    && !isMessageCrisisUpdateOrException[1]
                    && !isUncommonUpdateOrException[1]
                    && !isCrisisUpdateOrException[1])*/
                    if (!isUncommonUpdateOrException[1]
                            && !isCrisisUpdateOrException[1])               	
                {
                    setWarningSucceeded(warningRecord);
                }

                try
                {
                    // 新增或更新警告记录表
                    if (warningRecord.getWarningRecordSn() != null)
                    {
                        logger.debug(
                                "将该检验报告警告通知运行情况存入DB，状态为更新。。。\n 发送短信状态为：{}",
                                    "异常发送短信状态为："
                                    + isUncommonUpdateOrException[1] + "；"
                                    + "危急发送短信状态为："
                                    + isCrisisUpdateOrException[1]);

                        // 更新警告记录
                        warningDB.updateWarnRecord(warningRecord);

                        logger.debug("将该检验报告警告通知运行情况存入DB，success。");
                    }
                    else
                    {
                        logger.debug(
                                "将该检验报告警告通知运行情况存入DB，状态为新增。。。\n 发送短信状态为：{}",
                                new String[] {"异常发送短信状态为："
                                    + isUncommonUpdateOrException[1] + "；"
                                    + "危急发送短信状态为："
                                    + isCrisisUpdateOrException[1] });

                        // 新增警告记录
                        warningDB.insertWarnRecord(warningRecord,
                                t.getVersionNumber(),
                                DateUtils.stringToDate(t.getReportDate()));

                        logger.debug("将该检验报告警告通知运行情况存入DB，状态为新增。。。");
                    }
                }
                catch (Exception ex)
                {
                    logger.debug("警告通知短信状态保存失败，暂时跳过。");

                    ex.printStackTrace();
                }
                	//江苏版本不需要发送消息，所以注释此段代码
/*                if (isMessageUncommonUpdateOrException[1]
                    || isMessageCrisisUpdateOrException[1])
                {
                    logger.debug("警告通知发送消息出错，异常发送消息状态：{}，危急发送消息状态：{}",
                            isMessageUncommonUpdateOrException[1],
                            isMessageCrisisUpdateOrException[1]);

                    throw new Exception("发送消息出错");
                }*/

                if (isUncommonUpdateOrException[1]
                    || isCrisisUpdateOrException[1])
                {
                    logger.debug("警告通知发送短信出错，异常发送短信状态：{}，危急发送短信状态：{}",
                            isUncommonUpdateOrException[1],
                            isCrisisUpdateOrException[1]);

                    throw new Exception("发送短信出错");
                }
            }
            else
            {
                try
                {
                    if (warningRecord.getWarningRecordSn() != null)
                    {
                        logger.debug("警告通知retry运行时未找到相关发送方式的地址或电话或系统，此时标记为成功。");
                    }
                    else
                    {
                        logger.debug("警告通知验证检验具体结果项存在异常或危急，但没有任何发送方式需发送的相关地址或电话或系统，此时标记为成功。");
                    }

                    setWarningSucceeded(warningRecord);

                    warningDB.saveOrUpdateWarningRecord(warningRecord, logger,
                            t);
                }
                catch (Exception ex)
                {
                    logger.debug("保存运行成功标识时失败，此时以运行成功，正常结束。");

                    ex.printStackTrace();
                }
            }
        }
        /*
         * else { logger.warn("检验报告警告通知规则校验后发现异常或危急值，但未找到需发送通知的目标地址。"); }
         */
    }
    // 一般检验实体封装
    public WARNINGBUILD getWarningBuildLABDto(POCDIN000040UV01 pocdin000040uv01,String ruleGroup)
    {
    	WARNINGBUILD in = new WARNINGBUILD();

        in.setMsgId(RULE_CONDITION_SERVICE_ID);

        in.setMsgName(RULE_CONDITION_SERVICE_NAME);

        in.setSourceSysCode(CDR_SYSTEM_CODE);

        in.setTargetSysCode("");

        in.setCreateTime(DateUtils.dateToString(systemTime,
                DateUtils.PATTERN_COMPACT_DATETIME));

        in.setServiceId(warningOthers.getServiceId(pocdin000040uv01.getSourceSystemId()));

        in.setVisitDept(HOSPITAL_DEPT);

        in.setHospitalCode(pocdin000040uv01.getOrgCode());

        in.setHospitalName(pocdin000040uv01.getOrgName());

        in.setRoleGroupType(ruleGroup);

        in.setReportDate(DateUtils.stringToDate(pocdin000040uv01.getReportDate()));

        in.setSendDate(systemTime);

        List<LabReport> labReport = pocdin000040uv01.getReport();

        List<WarningRules> rows = new ArrayList<WarningRules>();
       // int count = 0;
        for (LabReport lr : labReport)
        {
            for (LabReportResult itemm : lr.getReportResult())
            {
                List<WarningItem> itemList = new ArrayList<WarningItem>();

                // 一般检验，循环四个项目
                // 项目checkKey
                WarningItem item1 = new WarningItem();

                item1.setItemName(LAB_PARAM1);

                item1.setValue(itemm.getItemCode());

                itemList.add(item1);

                if (!StringUtils.isEmpty(itemm.getItemValuePQ())
                    && !StringUtils.isEmpty(itemm.getItemUnitPQ()))
                {
                    // 项目itemNumValue
                	WarningItem item2 = new WarningItem();

                    item2.setItemName(LAB_PARAM2);

                    item2.setValue(itemm.getItemValuePQ());

                    itemList.add(item2);

                    // 项目unit
                    WarningItem item3 = new WarningItem();

                    item3.setItemName(LAB_PARAM3);

                    item3.setValue(itemm.getItemUnitPQ());

                    itemList.add(item3);
                }
                else
                {
                    if(!StringUtils.isEmpty(itemm.getItemUnitSC()))
                    {
                        // 项目unitST or SC
                    	WarningItem item3 = new WarningItem();

                        item3.setItemName(LAB_PARAM3);

                        item3.setValue(itemm.getItemUnitSC());

                        itemList.add(item3);
                    }
                }

                // 项目itemStrValue
                WarningItem item4 = new WarningItem();

                item4.setItemName(LAB_PARAM4);

                // Author: jia_yanqing
                // Date: 2014/4/8 10:00
                // [BUG]0043535 MODIFY BEGIN
                String strv = itemm.getItemValueSTAndSC();
                // [BUG]0043535 MODIFY END

                if (!StringUtils.isEmpty(strv))
                {
                    item4.setValue(strv);
                }
                itemList.add(item4);
                // 项目checkOffice
                WarningItem item5 = new WarningItem();
                item5.setItemName(LAB_PARAM5);
                item5.setValue(pocdin000040uv01.getVisitDept());
                itemList.add(item5);
                List<Diagnosis> diagnosisList = pocdin000040uv01.getDiagnosises();
                // 项目diagnosisCode
                for(Diagnosis diagnosis : diagnosisList){
                    WarningItem item6 = new WarningItem();
                    item6.setItemName(LAB_PARAM6);
                    item6.setValue(diagnosis.getDiseaseCode());
                    itemList.add(item6);               	
                }

                
                WarningRules ir = new WarningRules();
              //  count++;
                ir.setRowKey(lr.getLabItemCode());

                ir.setFactType(LAB_ROUTE);

                ir.setItem(itemList);

                rows.add(ir);
            }

        }
        in.setRow(rows);
        return in;
    }
    // 一般检验实体封装
    public INFECTIOUSBUILD getInfectiousLABDto(POCDIN000040UV01 pocdin000040uv01,String ruleGroup)
    {
        INFECTIOUSBUILD in = new INFECTIOUSBUILD();

        in.setMsgId(RULE_CONDITION_SERVICE_ID);

        in.setMsgName(RULE_CONDITION_SERVICE_NAME);

        in.setSourceSysCode(CDR_SYSTEM_CODE);

        in.setTargetSysCode("");

        in.setCreateTime(DateUtils.dateToString(systemTime,
                DateUtils.PATTERN_COMPACT_DATETIME));

        in.setServiceId(warningOthers.getServiceId(pocdin000040uv01.getSourceSystemId()));

        in.setVisitDept(pocdin000040uv01.getVisitDept());

        in.setHospitalCode(pocdin000040uv01.getOrgCode());

        in.setHospitalName(pocdin000040uv01.getOrgName());

        in.setRoleGroupType(ruleGroup);

        in.setReportDate(DateUtils.stringToDate(pocdin000040uv01.getReportDate()));

        in.setSendDate(systemTime);

        List<LabReport> labReport = pocdin000040uv01.getReport();

        List<InfectiousRules> rows = new ArrayList<InfectiousRules>();
        int count = 0;
        for (LabReport lr : labReport)
        {
            for (LabReportResult itemm : lr.getReportResult())
            {
                List<InfectiousItem> itemList = new ArrayList<InfectiousItem>();

                // 一般检验，循环四个项目
                // 项目checkKey
                InfectiousItem item1 = new InfectiousItem();

                item1.setItemName(LAB_PARAM1);

                item1.setValue(itemm.getItemCode());

                itemList.add(item1);

                if (!StringUtils.isEmpty(itemm.getItemValuePQ())
                    && !StringUtils.isEmpty(itemm.getItemUnitPQ()))
                {
                    // 项目itemNumValue
                    InfectiousItem item2 = new InfectiousItem();

                    item2.setItemName(LAB_PARAM2);

                    item2.setValue(itemm.getItemValuePQ());

                    itemList.add(item2);

                    // 项目unit
                    InfectiousItem item3 = new InfectiousItem();

                    item3.setItemName(LAB_PARAM3);

                    item3.setValue(itemm.getItemUnitPQ());

                    itemList.add(item3);
                }
                else
                {
                    if(!StringUtils.isEmpty(itemm.getItemUnitSC()))
                    {
                        // 项目unitST or SC
                        InfectiousItem item3 = new InfectiousItem();

                        item3.setItemName(LAB_PARAM3);

                        item3.setValue(itemm.getItemUnitSC());

                        itemList.add(item3);
                    }
                }

                // 项目itemStrValue
                InfectiousItem item4 = new InfectiousItem();

                item4.setItemName(LAB_PARAM4);

                // Author: jia_yanqing
                // Date: 2014/4/8 10:00
                // [BUG]0043535 MODIFY BEGIN
                String strv = itemm.getItemValueSTAndSC();
                // [BUG]0043535 MODIFY END

                if (!StringUtils.isEmpty(strv))
                {
                    item4.setValue(strv);
                }

                itemList.add(item4);

                InfectiousRules ir = new InfectiousRules();
                count++;
                ir.setRowKey(count + "");

                ir.setFactType(LAB_ROUTE);

                ir.setItem(itemList);

                rows.add(ir);
            }

        }
        in.setRow(rows);
        return in;
    }

    public void setRuleCompletedFlag(WarningRecord warningRecord)
    {
        warningRecord.setRuleCompleted(RULE_COMPLETED);
    }

    public void setWarningSucceeded(WarningRecord warningRecord)
    {
        warningRecord.setWarningSuccess(WARNING_SUCCESS);
    }

    private WARNINGBUILD getNewWARNINGBUILD(POCDIN000040UV01 t)
    {
    	WARNINGBUILD warningBuild = new WARNINGBUILD();

        warningBuild.setPatientName(t.getPatientName());

/*      warningBuild.setPatientDomain(t.getPatientDomain());

        warningBuild.setPatientLid(t.getPatientLid());
*/
        return warningBuild;
    }

    private List<Map<String, Object>> getUncommonOrCrisis(WARNINGBUILD resultDto)
    {
        List<Map<String, Object>> uncommonList = new ArrayList<Map<String, Object>>();
        
        List<WarningRules> reportList = resultDto.getRow();

        for (WarningRules warningLabReport : reportList)
        {
//            boolean isLoad = false;
            Map<String, Object> pUncommonMap = new HashMap<String, Object>();
        	List<Map<String, Object>> itemList = new ArrayList<Map<String, Object>>();
            List<WarningItem> reportResultList = warningLabReport.getItem();
            String itemCode = "";
            String itemNumValue = "";
            String itemStrValue = "";
            String unit = "";
            boolean checkStatus = false;
            String checkMessage = "";
            String suggestion = "";
            String checkOffice = "";
            String diagnosisCode = "";
            
            String labItemCode = warningLabReport.getRowKey();
//            String[] str = new String[]{"checkKey","itemNumValue","itemStrValue","unit",
//            		"checkStatus","checkMessage","suggestion","checkOffice","diagnosisCode"};
            Map<String, Object> uncommonMap = new HashMap<String, Object>();
            for (WarningItem warningLabReportResult : reportResultList)
            {
                String itemName = warningLabReportResult.getItemName();

                String value = warningLabReportResult.getValue();
//              WarningLabReportSpecificResult warningLabReportSpecificResult = new WarningLabReportSpecificResult();
/*              String itemStrValue = warningLabReportResult.getItemStrValue();

                String itemUnit = warningLabReportResult.getItemUnit();

                String checkStatus = warningLabReportResult.getCheckStatus();

                String checkMessage = warningLabReportResult.getCheckMessage();
                *//** 处理建议 *//*
                String suggestion = warningLabReportResult.getSuggestion();*/

                if ("checkStatus".equals(itemName) && "true".equals(value))
                {
                	checkStatus = true;
                }
                if ("checkKey".equals(itemName))
                {
                	itemCode = value;
                }
                if ("itemNumValue".equals(itemName))
                {
                	itemNumValue = value;
                }
                if ("itemStrValue".equals(itemName))
                {
                	itemStrValue = value;
                }
                if ("unit".equals(itemName))
                {
                	unit = value;
                }
                if ("checkMessage".equals(itemName))
                {
                	checkMessage = value;
                }
                if ("suggestion".equals(itemName))
                {
                	suggestion = value;
                }
                if ("checkOffice".equals(itemName))
                {
                	checkOffice = value;
                }
                if ("diagnosisCode".equals(itemName))
                {
                	diagnosisCode = value;
                }
            }

            if (checkStatus)
            {
            	uncommonMap.put("itemCode", itemCode);

                uncommonMap.put("itemNumValue", itemNumValue);

                uncommonMap.put("itemStrValue", itemStrValue);

                uncommonMap.put("unit", unit);

                uncommonMap.put("checkStatus", checkStatus);

                uncommonMap.put("checkMessage", checkMessage);
                
                uncommonMap.put("suggestion", suggestion);
                
                uncommonMap.put("checkOffice", checkOffice);
                
                uncommonMap.put("diagnosisCode", diagnosisCode);

                itemList.add(uncommonMap);
                
                pUncommonMap.put("pItem", labItemCode);
                
               // pUncommonMap.put("pItemName", warningLabReport.getLabItemName());

                pUncommonMap.put("item", itemList);

                uncommonList.add(pUncommonMap);
            }
        }

        return uncommonList;
    }

    private List<Map<String, Object>> getUncommonOrCrisisByDatabase(
            List<LabDto> labDtoList, String uncommonOrCrisis)
    {
        String itemCode = "";

        int count = 0;

        Map<String, Object> pMap = null;

        List<Map<String, Object>> resList = new ArrayList<Map<String, Object>>();

        List<Map<String, Object>> itemList = null;

        for (LabDto labDto : labDtoList)
        {
            count++;

            if (!itemCode.equals(labDto.getItemCode()))
            {
                if (itemList != null && !itemList.isEmpty())
                {
                    pMap.put("item", itemList);

                    resList.add(pMap);
                }

                itemCode = labDto.getItemCode();

                pMap = new HashMap<String, Object>();

                pMap.put("pItem", itemCode);

                itemList = new ArrayList<Map<String, Object>>();
            }

            Map<String, Object> itemMap = new HashMap<String, Object>();

            itemMap.put("itemCode", labDto.getSubitemCode());

            String itemValue = labDto.getItemValue();

            if (WarningOthers.isMathValue(itemValue))
            {
                itemMap.put("itemNumValue", itemValue);
            }
            else
            {
                itemMap.put("itemStrValue", itemValue);
            }

            itemMap.put("unit", labDto.getItemUnit());

            itemMap.put("checkMessage", "");

            String uncommonFlag = labDto.getUncommonFlag();

            String crisisFlag = labDto.getCrisisFlag();

            if (UNCOMMON_NAME.equals(uncommonOrCrisis))
            {
                if (UNCOMMON_ITEM_FLAG.equals(uncommonFlag))
                {
                    itemMap.put("checkStatus", uncommonFlag);

                    itemList.add(itemMap);
                }
            }
            else
            {
                if (CRISIS_ITEM_FLAG.equals(crisisFlag))
                {
                    itemMap.put("checkStatus", crisisFlag);

                    itemList.add(itemMap);
                }
            }

            if (count == labDtoList.size())
            {
                if (itemList != null && !itemList.isEmpty())
                {
                    pMap.put("item", itemList);

                    resList.add(pMap);
                }
            }
        }

        return itemList;
    }

/*    private boolean[] sendMessage(POCDIN000040UV01 pocdin000040uv01,
            List<Map<String, Object>> processList, String systemId,
            WarningRecord warningRecord, String uncommonOrCrisis)
    {
        boolean[] isUpdateOrException = new boolean[2];

        boolean isUpdate = false;

        boolean isException = false;

        WARNINGMESSAGE wmDto = null;

        String sendFlag = null;

        String sendMessageContent = null;

        Hl7Message hl7Message = null;

        String messageStatus = null;

        if (StringUtils.isEmpty(systemId))
        {
            logger.debug("发送消息时所用的系统id为空。。。");

            return isUpdateOrException;
        }

        if (UNCOMMON_FLAG.equals(uncommonOrCrisis))
        {
            // 获取消息发送内容（异常）
            wmDto = getSendMessageDto(pocdin000040uv01, processList, null,
                    UNCOMMON_NAME);

            sendFlag = warningRecord.getUncomQueueCompleted();
        }
        else
        {
            // 获取消息发送内容（危急）
            wmDto = getSendMessageDto(pocdin000040uv01, null, processList,
                    CRISIS_NAME);

            sendFlag = warningRecord.getCrisisQueueCompleted();
        }

        if (!COMPLETE_NORMAL_STATUS.equals(sendFlag))
        {
            try
            {
                isUpdate = true;

                // 获取消息内容
                // sendMessageContent =
                // warningOthers.getUpdateXml(TEMPLATE_PATH,
                // wmDto, SEND_MESSAGE_ID);

                // systemId加引号"'"
                String[] systemIdSplit = systemId.split(",");
                systemId = "";
                int count = 0;

                for (String sysId : systemIdSplit)
                {
                    String resSysId = "'" + sysId + "'";

                    systemId += resSysId;

                    if (count != systemIdSplit.length - 1)
                    {
                        count++;

                        systemId += ",";
                    }
                }

                sendMessageContent = warningOthers.getSendMessageContent(wmDto);

                hl7Message = warningOthers.getHl7Message(
                        pocdin000040uv01.getMessage(),
                        Constants.QUEUE_SERVICE_ID, sendMessageContent,
                        systemId, QUEUE_NAME,
                        pocdin000040uv01.getPatientDomain(),
                        pocdin000040uv01.getOrgCode(), CDR_SYSTEM_CODE);

                // 发送消息
                warningOthers.sendMessges(sender, hl7Message, QUEUE_NAME);

                messageStatus = COMPLETE_NORMAL_STATUS;
            }
            catch (Exception ex)
            {
                messageStatus = COMPLETE_EXCEPTION_STATUS;

                isException = true;

                ex.printStackTrace();
            }

            if (UNCOMMON_FLAG.equals(uncommonOrCrisis))
            {
                warningRecord.setUncomQueueCompleted(messageStatus);
            }
            else
            {
                warningRecord.setCrisisQueueCompleted(messageStatus);
            }
        }

        isUpdateOrException[0] = isUpdate;

        isUpdateOrException[1] = isException;

        return isUpdateOrException;
    }*/

 /*   private WARNINGMESSAGE getSendMessageDto(POCDIN000040UV01 pocdin000040uv01,
            List<Map<String, Object>> contentUncommonList,
            List<Map<String, Object>> contentCrisisList, String warningType)
    {
        BigDecimal patientSn = warningDB.getPatientSn();

        BigDecimal visitSn = warningDB.getVisitSn();

        String telephone = "";

        Date birthDate = null;

        Date visitDate = null;

        if (patientSn != null)
        {
            Patient patient = warningDB.getPatient(patientSn);

            telephone = patient.getTelephone();

            birthDate = patient.getBirthDate();
        }

        if (visitSn != null)
        {
            MedicalVisit medicalVisit = warningDB.getMedicalVisit(visitSn);

            visitDate = medicalVisit.getVisitDate();
        }

        WARNINGMESSAGE wm = new WARNINGMESSAGE();

        wm.setMsgId(MESSAGE_SERVICE_ID);

        wm.setMsgName(MESSAGE_SERVICE_NAME);

        wm.setSourceSysCode(CDR_SYSTEM_CODE);

        wm.setTargetSysCode("");

        wm.setCreateTime(DateUtils.dateToString(systemTime,
                DateUtils.PATTERN_COMPACT_DATETIME));

        wm.setPatientDomain(pocdin000040uv01.getPatientDomain());

        wm.setPatientLid(pocdin000040uv01.getPatientLid());

        wm.setPatientName(pocdin000040uv01.getPatientName());

        wm.setGenderCode(pocdin000040uv01.getGenderCode());

        wm.setAge(DateUtils.caluAge(birthDate, visitDate));

        wm.setVisitDate(DateUtils.dateToString(visitDate,
                DateUtils.PATTERN_COMPACT_DATETIME));

        wm.setVisitTimes(pocdin000040uv01.getVisitTimes());

        wm.setVisitDept(pocdin000040uv01.getVisitDept());

        wm.setVisitDeptName(pocdin000040uv01.getVisitDeptName());

        wm.setOrgCode(pocdin000040uv01.getOrgCode());

        wm.setOrgName(pocdin000040uv01.getOrgName());

        // Author: yang_jianbo
        // Date: 2014/8/15 18:32
        // [BUG]0047610 ADD BEGIN
        wm.setWardsNo(pocdin000040uv01.getWardsNo());
        // [BUG]0047610 ADD END

        wm.setBedNo(pocdin000040uv01.getBedNo());

        wm.setTelcom(telephone);

        wm.setWarningType(warningType);

        wm.setReportNo(pocdin000040uv01.getReportNo());

        wm.setRequestTime(pocdin000040uv01.getRequestTime());

        wm.setApplyPerson(pocdin000040uv01.getApplyPerson());

        wm.setApplyPersonName(pocdin000040uv01.getApplyPersonName());

        wm.setReportLink(getIntegratedUrl(pocdin000040uv01));
        
        *//**
         *  报告时间
         *//*
        wm.setReportDate(pocdin000040uv01.getReportDate());
        
        *//**
         * 报告确认时间
         *//*
        wm.setReviewDate(pocdin000040uv01.getReviewDate());

        if (contentUncommonList != null && !contentUncommonList.isEmpty())
        {
            SetSendMessageDtoFragment(contentUncommonList, wm);
        }

        if (contentCrisisList != null && !contentCrisisList.isEmpty())
        {
            SetSendMessageDtoFragment(contentCrisisList, wm);
        }

        return wm;
    }*/

    private String getIntegratedUrl(POCDIN000040UV01 pocdin000040uv01)
    {
        String integratedParam = "?reportNo=" + pocdin000040uv01.getReportNo();

        return Constants.REPORT_LINK + integratedParam;
    }

    @SuppressWarnings("unchecked")
    private void SetSendMessageDtoFragment(
            List<Map<String, Object>> contentList, WARNINGMESSAGE wm)
    {
        List<WarningMessageLabReport> wmlList = new ArrayList<WarningMessageLabReport>();

        for (Map<String, Object> pItemMap : contentList)
        {
            WarningMessageLabReport wml = new WarningMessageLabReport();

            String pItem = (String) pItemMap.get("pItem");

            /**
             * 此方式取不到值
             */
            /*String pItemName = DictionaryCache.getDictionary(
                    Constants.DOMAIN_LAB_ITEM).get(pItem);*/

            String pItemName = (String) pItemMap.get("pItemName");
            wml.setLabItemCode(pItem);

            wml.setLabItemName(pItemName);

            List<Map<String, Object>> itemList = (List<Map<String, Object>>) pItemMap.get("item");

            List<WarningMessageLabReportResult> wmlrList = new ArrayList<WarningMessageLabReportResult>();

            for (Map<String, Object> itemMap : itemList)
            {
                WarningMessageLabReportResult wmlr = new WarningMessageLabReportResult();

                String itemCode = (String) itemMap.get("itemCode");

                String itemNumValue = (String) itemMap.get("itemNumValue");

                String itemStrValue = (String) itemMap.get("itemStrValue");

                String itemUnit = (String) itemMap.get("itemUnit");

                String checkStatus = (String) itemMap.get("checkStatus");

                String checkMessage = (String) itemMap.get("checkMessage");
                
                /** 处理建议 */
                String suggestion = (String) itemMap.get("suggestion");

                DictionaryDto labResultItemDto = DictionaryCache.getFullDictionary(
                        Constants.DOMAIN_LAB_RESULT_ITEM).get(itemCode);
                String itemNameCn = labResultItemDto.getFullName();

                String itemNameEn = labResultItemDto.getCvValue();

                wmlr.setItemCode(itemCode);

                wmlr.setItemNameCn(itemNameCn);

                wmlr.setItemNameEn(itemNameEn);

                wmlr.setItemNumValue(itemNumValue);

                wmlr.setItemStrValue(itemStrValue);

                wmlr.setItemUnit(itemUnit);

                wmlr.setCheckStatus(checkStatus);

                wmlr.setCheckMessage(checkMessage);
                
                /** 处理建议 */
                wmlr.setSuggestion(suggestion);

                wmlrList.add(wmlr);
            }

            wml.setReportResult(wmlrList);

            wmlList.add(wml);
        }

        wm.setReport(wmlList);
    }

    private String getMailContent(WARNINGBUILD wb,
            List<Map<String, Object>> contentUncommonList,
            List<Map<String, Object>> contentCrisisList)
    {
        StringBuffer fragmentStringBufferUncommon = new StringBuffer();

        StringBuffer fragmentStringBufferCrisis = new StringBuffer();

        String uncommonContent = null;

        String crisisContent = null;

        StringBuffer resultFragment = new StringBuffer();

        resultFragment.append("您好！ <br><br>");

        resultFragment.append(" 患者" + wb.getPatientName() + "，");

        if (contentUncommonList != null && !contentUncommonList.isEmpty())
        {
            uncommonContent = getMailContentFragment(contentUncommonList,
                    fragmentStringBufferUncommon);

            resultFragment.append("检验项目：:paramPitemUncommon ");

            resultFragment.append("在检验报告中出现异常值, 具体异常内容如下：<br>");

            int pItemPositionUncommon = resultFragment.indexOf(":paramPitemUncommon");

            String fragmentStringUncommon = fragmentStringBufferUncommon.toString();

            resultFragment.replace(pItemPositionUncommon,
                    pItemPositionUncommon + 19,
                    fragmentStringUncommon == null ? ""
                            : fragmentStringUncommon);

            resultFragment.append(uncommonContent);

            resultFragment.append("<br>");
        }

        if (contentCrisisList != null && !contentCrisisList.isEmpty())
        {
            crisisContent = getMailContentFragment(contentCrisisList,
                    fragmentStringBufferCrisis);

            resultFragment.append(" 检验项目：:paramPitemCrisis ");

            resultFragment.append("在检验报告中出现危急值, 具体危急内容如下：<br>");

            int pItemPositionCrisis = resultFragment.indexOf(":paramPitemCrisis");

            String fragmentStringCrisis = fragmentStringBufferCrisis.toString();

            resultFragment.replace(pItemPositionCrisis,
                    pItemPositionCrisis + 17, fragmentStringCrisis == null ? ""
                            : fragmentStringCrisis);

            resultFragment.append(crisisContent);

            resultFragment.append("<br>");
        }

        resultFragment.append(Constants.WARNING_MAIL_SIGN_NAME + "<br>");

        resultFragment.append(DateUtils.dateToString(systemTime,
                DateUtils.PATTERN_MINUS_DATE));

        return resultFragment.toString();
    }

    @SuppressWarnings("unchecked")
    private String getMailContentFragment(
            List<Map<String, Object>> contentList, StringBuffer fragmentString)
    {
        int pCount = 0;

        int iCount = 0;

        StringBuffer resultFragment = new StringBuffer();

        if (contentList != null && !contentList.isEmpty())
        {
            for (Map<String, Object> pItemMap : contentList)
            {
                String pItem = (String) pItemMap.get("pItem");

                String pItemName = DictionaryCache.getDictionary(
                        Constants.DOMAIN_LAB_ITEM).get(pItem);

                resultFragment.append(" 检验项目：" + pItemName + "<br>");

                fragmentString.append(pItemName);

                pCount++;

                if (pCount < contentList.size())
                {
                    fragmentString.append("/");
                }

                List<Map<String, Object>> itemList = (List<Map<String, Object>>) pItemMap.get("item");

                for (Map<String, Object> itemMap : itemList)
                {
                    String itemCode = (String) itemMap.get("itemCode");

                    String itemNumValue = (String) itemMap.get("itemNumValue");

                    String itemStrValue = (String) itemMap.get("itemStrValue");

                    String itemUnit = (String) itemMap.get("itemUnit");

                    iCount++;

                    resultFragment.append(" 检验具体项目："
                        + DictionaryCache.getDictionary(
                                Constants.DOMAIN_LAB_RESULT_ITEM).get(itemCode)
                        + "，");

                    if (!StringUtils.isEmpty(itemNumValue))
                    {
                        resultFragment.append("结果：" + itemNumValue + " "
                            + itemUnit);
                    }
                    else
                    {
                        if (!StringUtils.isEmpty(itemUnit))
                        {
                            resultFragment.append("结果：" + itemStrValue + " "
                                + itemUnit);
                        }
                        else
                        {
                            resultFragment.append("结果：" + itemStrValue);
                        }
                    }

                    if (iCount < itemList.size())
                    {
                        resultFragment.append("；");
                    }
                    else
                    {
                        resultFragment.append("。<br>");
                    }
                }
            }
        }

        return resultFragment.toString();
    }

    private String getMobileMessageContent(WARNINGBUILD wb,
            List<Map<String, Object>> contentUncommonList,
            List<Map<String, Object>> contentCrisisList, POCDIN000040UV01 t)
    {
        StringBuffer fragmentStringBufferPitemUncommon = new StringBuffer();

        StringBuffer fragmentStringBufferItemUncommon = new StringBuffer();

        StringBuffer fragmentStringBufferPitemCrisis = new StringBuffer();

        StringBuffer fragmentStringBufferItemCrisis = new StringBuffer();

        StringBuffer resultFragment = new StringBuffer();

        resultFragment.append("您好，");

        // 异常
        resultFragment.append("患者" + t.getPatientName() + "， ");

        if (contentUncommonList != null && !contentUncommonList.isEmpty())
        {
            getMobileMessageContentFragment(contentUncommonList,
                    fragmentStringBufferPitemUncommon,
                    fragmentStringBufferItemUncommon);

            resultFragment.append("检验项目："
                + fragmentStringBufferPitemUncommon.toString());

            resultFragment.append(" 在检验报告中出现异常值，异常内容为：");

            resultFragment.append(fragmentStringBufferItemUncommon.toString());
        }

        if (contentCrisisList != null && !contentCrisisList.isEmpty())
        {
            getMobileMessageContentFragment(contentCrisisList,
                    fragmentStringBufferPitemCrisis,
                    fragmentStringBufferItemCrisis);

            // 危急
            resultFragment.append("检验项目： "
                + fragmentStringBufferPitemCrisis.toString());

            resultFragment.append(" 在检验报告中出现危急值，危急内容为：");

            resultFragment.append(fragmentStringBufferItemCrisis.toString());
        }

        return resultFragment.toString();
    }
    public StringBuffer listToString(List<String> stringList){
        if (stringList==null) {
            return null;
        }
        StringBuffer result = new StringBuffer();
        boolean flag=false;
        for (String string : stringList) {
            if (flag) {
                result.append("/");
            }else {
                flag=true;
            }
            result.append(string);
        }
        return result;
    }
    @SuppressWarnings("unchecked")
    private void getMobileMessageContentFragment(
            List<Map<String, Object>> contentList,
            StringBuffer fragmentStringPitem, StringBuffer fragmentStringItem)
    {
        int pCount = 0;

        if (contentList != null && !contentList.isEmpty())
        {
        	List<String> pItemList  = new ArrayList<String>();
            for (Map<String, Object> pItemMap : contentList)
            {
                String pItem = (String) pItemMap.get("pItem");

                String pItemName = DictionaryCache.getDictionary(
                        Constants.DOMAIN_LAB_ITEM).get(pItem);
                if(!pItemList.contains(pItemName)){
                	pItemList.add(pItemName);
                }
                //fragmentStringPitem.append(pItemName);

                pCount++;

/*                if (pCount < contentList.size())
                {
                    fragmentStringPitem.append("/");
                }*/

                List<Map<String, Object>> itemList = (List<Map<String, Object>>) pItemMap.get("item");

                for (Map<String, Object> itemMap : itemList)
                {
                    String itemCode = (String) itemMap.get("itemCode");

                    String itemNumValue = (String) itemMap.get("itemNumValue");

                    String itemStrValue = (String) itemMap.get("itemStrValue");

                    String itemUnit = (String) itemMap.get("unit");

                    String itemName = DictionaryCache.getDictionary(
                            Constants.DOMAIN_LAB_RESULT_ITEM).get(itemCode);

                    fragmentStringItem.append("检验项目：" + itemName + "，");

                    if (!StringUtils.isEmpty(itemNumValue))
                    {
                        fragmentStringItem.append("结果：" + itemNumValue + " "
                            + itemUnit);
                    }
                    else
                    {
                        if (!StringUtils.isEmpty(itemUnit))
                        {
                            fragmentStringItem.append("结果：" + itemStrValue
                                + " " + itemUnit);
                        }
                        else
                        {
                            fragmentStringItem.append("结果：" + itemStrValue);
                        }
                    }

                    if (pCount < contentList.size())
                    {
                        fragmentStringItem.append("；");
                    }
                    else
                    {
                        fragmentStringItem.append("。");
                    }
                }
            }
            fragmentStringPitem = listToString(pItemList);
        }
    }

    private boolean[] sendMailOrMobile(WARNINGBUILD resultDto,
            List<Map<String, Object>> processList,
            List<WarningNotice> warningNoticeList, WarningRecord warningRecord,
            String uncommonOrCrisis, Map<String, String> addressMap, POCDIN000040UV01 t)
    {
        boolean[] isUpdateOrException = new boolean[2];

        boolean isUpdate = false;

        boolean isException = false;

        String sendFlag = null;

//        String sendMailContent = null;

        String sendMobileMessageContent = null;

//        String mailAddress = addressMap.get("mailAddress");

        String mobileAddress = addressMap.get("mobileAddress");
      //江苏版本不需要发送邮件，所以注释此段代码
/*        if (!StringUtils.isEmpty(mailAddress))
        {
            String mailStatus = null;

            if (UNCOMMON_FLAG.equals(uncommonOrCrisis))
            {
                sendFlag = warningRecord.getUncomMailCompleted();

                // 1) 获取邮件发送内容（异常）
                sendMailContent = getMailContent(resultDto, processList, null);
            }
            else
            {
                sendFlag = warningRecord.getCrisisMailCompleted();

                // 1) 获取邮件发送内容（危急）
                sendMailContent = getMailContent(resultDto, null, processList);
            }

            if (!COMPLETE_NORMAL_STATUS.equals(sendFlag))
            {
                try
                {
                    isUpdate = true;

                    // 发送邮件
                    callWebservice.sendMailTo(mailAddress, "", "",
                            sendMailContent, null, null, null);

                    mailStatus = COMPLETE_NORMAL_STATUS;
                }
                catch (Exception ex)
                {
                    mailStatus = COMPLETE_EXCEPTION_STATUS;

                    isException = true;

                    ex.printStackTrace();
                }

                if (UNCOMMON_FLAG.equals(uncommonOrCrisis))
                {
                    warningRecord.setUncomMailCompleted(mailStatus);
                }
                else
                {
                    warningRecord.setCrisisMailCompleted(mailStatus);
                }
            }
        }
        else
        {
            logger.debug("发送邮件地址为空。。。");
        }
*/
        if (!StringUtils.isEmpty(mobileAddress))
        {
            String mobileStatus = null;

            if (UNCOMMON_FLAG.equals(uncommonOrCrisis))
            {
                sendFlag = warningRecord.getUncomMobileCompleted();

                // 2) 获取短信发送内容（异常）
                sendMobileMessageContent = getMobileMessageContent(resultDto,
                        processList, null,t);
            }
            else
            {
                sendFlag = warningRecord.getCrisisMobileCompleted();

                // 2) 获取短信发送内容（危急）
                sendMobileMessageContent = getMobileMessageContent(resultDto,
                        null, processList,t);
            }

            if (!COMPLETE_NORMAL_STATUS.equals(sendFlag))
            {
                try
                {
                    isUpdate = true;

                    if (UNCOMMON_FLAG.equals(uncommonOrCrisis))
                    {
                        // 发送短信（异常）
/*                        callWebservice.sendMessageByMobileNo(0, mobileAddress,
                                sendMobileMessageContent);*/
                        Object backObj = callWebservice.sendMessageNew(mobileAddress, sendMobileMessageContent);
                        
                        if(backObj instanceof Integer) {
                        	//北大人民返回int类型
                        	int b = (Integer) backObj;
                        	if(b == 1){
                        		mobileStatus = COMPLETE_NORMAL_STATUS;
                        		logger.debug("发送ok");
                        	} else {
                        		logger.debug("短信发送失败！发挥状态为：" + b);
                        	}
                        } else if(backObj instanceof String){
                        	//江苏人民返回String类型
                        	String b = (String) backObj;
                        	if(b.equals("OK")){
                        		mobileStatus = COMPLETE_NORMAL_STATUS;
                        		logger.debug("发送ok");
                        	} else {
                        		logger.debug(b);
                        	}
                        } 
                    }
                    else
                    {
                        // 发送短信（危急）
/*                        callWebservice.sendMessageByMobileNo(1, mobileAddress,
                                sendMobileMessageContent);*/
                        Object backObj = callWebservice.sendMessageNew(mobileAddress, sendMobileMessageContent);
                        
                        if(backObj instanceof Integer) {
                        	//北大人民返回int类型
                        	int b = (Integer) backObj;
                        	if(b == 1){
                        		mobileStatus = COMPLETE_NORMAL_STATUS;
                        		logger.debug("发送ok");
                        	} else {
                        		logger.debug("短信发送失败！发挥状态为：" + b);
                        	}
                        } else if(backObj instanceof String){
                        	//江苏人民返回String类型
                        	String b = (String) backObj;
                        	if(b.equals("OK")){
                        		mobileStatus = COMPLETE_NORMAL_STATUS;
                        		logger.debug("发送ok");
                        	} else {
                        		logger.debug(b);
                        	}
                        } 
                    }

                    mobileStatus = COMPLETE_NORMAL_STATUS;
                }
                catch (Exception ex)
                {
                    mobileStatus = COMPLETE_EXCEPTION_STATUS;

                    isException = true;

                    ex.printStackTrace();
                }

                if (UNCOMMON_FLAG.equals(uncommonOrCrisis))
                {
                    warningRecord.setUncomMobileCompleted(mobileStatus);
                }
                else
                {
                    warningRecord.setCrisisMobileCompleted(mobileStatus);
                }
            }
        }
        else
        {
            logger.debug("发送短信地址为空。。。");
        }

        isUpdateOrException[0] = isUpdate;

        isUpdateOrException[1] = isException;

        return isUpdateOrException;
    }

    private WarningRecord getWarningRecord(WarningRecord warningRecord)
    {
        if (warningRecord == null)
        {
            return new WarningRecord();
        }

        return warningRecord;
    }

    /*
     * private String getResultTest(String v) { String result =
     * "<?xml version='1.0' encoding='UTF-8'?>" + " <msg>" + " <msgId>0</msgId>"
     * + " <msgName>规则校验信息条件数据</msgName>" +
     * " <sourceSysCode>xxx</sourceSysCode>" +
     * " <targetSysCode>xxx</targetSysCode>" +
     * " <createTime>20090101121330</createTime>" + " <reportNo>报告号</reportNo>"
     * + " <visitTimes>就诊次数</visitTimes>" +
     * " <patientDomain>患者域Id</patientDomain>" +
     * " <patientLid>患者Id</patientLid>" + " <patientName>cdr</patientName>" +
     * " <genderCode>患者性别</genderCode>" + " <visitDept>科室id</visitDept>" +
     * " <report>" + " <component>" + " <labItemCode>601054</labItemCode>" +
     * " <reportResult>" + " <component>" + " <itemCode>H0150</itemCode>" +
     * " <itemNumValue>1000</itemNumValue>" + " <itemStrValue></itemStrValue>" +
     * " <itemUnit>fL</itemUnit>" + " <checkStatus>true</checkStatus>" +
     * " <checkMessage>检验结果信息</checkMessage>" + " </component>" +
     * " </reportResult>" + " </component>" + " </report>" + " </msg>";
     * 
     * return result; }
     */

    public static void main(String[] args)
    {
        /*
         * String s = "3:3:4";
         * 
         * System.out.println(s.substring(0, s.length() - 1) + " == " +
         * s.contains("4"));
         * 
         * boolean[] s1 = new boolean[2];
         * 
         * s1[0] = true;
         * 
         * System.out.println(s1[0] + "  " + s1[1]);
         */

        /*
         * Map<String, String> m = new HashMap<String, String>();
         * 
         * List<Map<String, String>> itemList = new ArrayList<Map<String,
         * String>>();
         * 
         * List<Map<String, String>> itemList1 = new ArrayList<Map<String,
         * String>>();
         * 
         * m.put("o", "ooo");
         * 
         * itemList.add(m);
         * 
         * m.put("o", "kkk");
         * 
         * itemList1.add(m);
         * 
         * System.out.println(itemList.get(0).get("o") + "   " +
         * itemList1.get(0).get("o"));
         */

        String systemId = "S008";

        String[] systemIdSplit = systemId.split(",");
        systemId = "";
        int count = 0;

        for (String sysId : systemIdSplit)
        {
            String resSysId = "'" + sysId + "'";

            systemId += resSysId;

            if (count != systemIdSplit.length - 1)
            {
                count++;

                systemId += ",";
            }
        }

        System.out.println(systemId);

    }
}
