package com.founder.cdr.batch.send;

/**
 * $Author :chang_xuewen
 * $Date : 2014/03/20
 * 对外应用服务
 */

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

import com.founder.cdr.batch.exception.message.MessageExternalSendNoWarningException;
import com.founder.cdr.batch.send.db.WarningDB;
import com.founder.cdr.batch.send.others.WarningOthers;
import com.founder.cdr.core.Constants;
import com.founder.cdr.dao.WarnningRuleCheckDao;
import com.founder.cdr.entity.InfectiousContent;
import com.founder.cdr.entity.InfectiousRecord;
import com.founder.cdr.entity.LabResult;
import com.founder.cdr.entity.WarningNotice;
import com.founder.cdr.hl7.dto.InfectiousItem;
import com.founder.cdr.hl7.dto.InfectiousRules;
import com.founder.cdr.hl7.dto.LabReport;
import com.founder.cdr.hl7.dto.LabReportResult;
import com.founder.cdr.hl7.dto.infectiousbuild.INFECTIOUSBUILD;
import com.founder.cdr.hl7.dto.pocdin000040uv01.POCDIN000040UV01;
import com.founder.cdr.hl7.dto.warningMessage.WARNINGMESSAGE;
import com.founder.cdr.service.LabService;
import com.founder.cdr.service.WarningNoticeService;
import com.founder.cdr.tool.BatchCallWebservice;
import com.founder.cdr.util.DateUtils;
import com.founder.cdr.util.PropertiesUtils;
import com.founder.cdr.util.StringUtils;

@Component(value = "pocdin000040uv01ExSend")
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class POCDIN000040UV01ExSend implements BusinessExSend<POCDIN000040UV01>
{
	@Autowired
    private WarnningRuleCheckDao warnningRuleCheckDao;
	
    private static final Logger logger = LoggerFactory.getLogger(POCDIN000040UV01ExSend.class);

    // 检验报告全局变量
    private LabResult labResult;

    // 源系统标识
    private String sourceSystemId;

    // 删除标志
    private String noDeleteFlag;

    private static final String LAB_SERVICE_ID = "BS319";

    private static final String MIC_SERVICE_ID = "BS354";

    private static final String CDR_SYSTEM_CODE = "S015";

    private static final String HOSPITAL_DEPT = "0000000";

    private static final String LAB_ROUTE = "checkItem";

    private static final String MIC_ROUTE = "microbialCheckItem";

    private static final String RULE_CONDITION_SERVICE_NAME = "规则校验信息条件数据";

    private static final String RULE_GROUP = "InfectionGroup";// 用作传入的dto时作为传染组
                                                              // /
                                                              // 用作预警人员时作为传染科以外的人员

    private static final String WARNING_GROUP = "WarningGroup";// 传染科预警组人员

    private static final String MAPPING_DTO = "INFECTIOUSBUILD";

    private static final String COMPLETE_NORMAL_STATUS = "1";

    private static final String COMPLETE_EXCEPTION_STATUS = "0";

    private static final String SEND_OPEN = "0";

    private static final String DELETE_FLAG = "-1";

    private static final String LAB_PARAM1 = "checkKey";

    private static final String LAB_PARAM2 = "itemNumValue";

    private static final String LAB_PARAM3 = "unit";

    private static final String LAB_PARAM4 = "itemStrValue";

    private static final String EXISTS_FLAG = "true";

    private static final String NOT_EXISTS_FLAG = "false";

    private static final String MIC_PARAM = "warningValue";

    private static final String NON_DEPT = "1000108";

    private static final String WARNING_NUM_TO_STR = "阳性";

    private Date systemTime;

    @Autowired
    private WarningDB warningDB;

    @Autowired
    private BatchCallWebservice callWebservice;

    @Autowired
    private WarningOthers<WARNINGMESSAGE> warningOthers;

    @Autowired
    private LabService labService;

    @Autowired
    private WarningNoticeService notice;

    Map<BigDecimal, List<String>> noSendMap = null;

    private String businessSource = "";
    
    private String wardsNo = "";

    public POCDIN000040UV01ExSend()
    {
        logger.debug("对外应用服务功能初始化。。。");
        systemTime = DateUtils.getSystemTime();// 获取本地检验报告ID
        noDeleteFlag = Constants.NO_DELETE_FLAG;

    }

    @Override
    public void sendValication(POCDIN000040UV01 pocdin000040uv01)
            throws Exception
    {
        // throw new DuplicateKeyException("");
        // throw new RuntimeException("");
    }

    private void getReport(POCDIN000040UV01 pocdin000040uv01)
    {
        String labReportLid = null;
        String reportTypeCode = null;
        Map<String, Object> map = null;

        labReportLid = pocdin000040uv01.getReportNo();
        // 获取报告类型编码
        reportTypeCode = pocdin000040uv01.getReportTypeCode();

        map = new HashMap<String, Object>();
        map.put("labReportLid", labReportLid);

        // 根据系统源id确定检验报告
        map.put("sourceSystemId", sourceSystemId);
        // [BUG]0008030 MODIFY END

        // 针对微生物检验报告需要增加报告类型过滤
        if (Constants.SOURCE_MICROBE.equals(sourceSystemId))
        {
            map.put("reportTypeCode", reportTypeCode);
        }

        map.put("orgCode", pocdin000040uv01.getOrgCode());

        map.put("deleteFlag", noDeleteFlag);
        labResult = labService.selectLabResultByCondition(map);
        if (labResult == null)
        {
            logger.error("检验报告本身不存在false: {} 检验报告版本状态: {}",
                    "labResult = " + labResult + "，params: labReportLid = "
                        + labReportLid + "，sourceSystemId = " + sourceSystemId
                        + "，reportTypeCode = " + reportTypeCode
                        + "; orgCode = " + pocdin000040uv01.getOrgCode(),
                    pocdin000040uv01.getVersionNumber());
            return;
        }
    }

    @Override
    public void send(POCDIN000040UV01 pocdin000040uv01) throws Exception
    {
        logger.debug("对外应用服务功能开始运行。。。【LIS检验传染病预警业务】");

        String if_Exists = "false";
        
        if (Constants.SOURCE_LAB.equals(pocdin000040uv01.getSourceSystemId()))
        {
            businessSource = "02";
            
            wardsNo = pocdin000040uv01.getWardsName();
        }
        else if (Constants.SOURCE_MICROBE.equals(pocdin000040uv01.getSourceSystemId()))
        {
            businessSource = "03";
            
            wardsNo = pocdin000040uv01.getWardsNo();
        }

        //if ("01".equals(pocdin000040uv01.getPatientDomain()))
        if (Constants.lstDomainOutPatient().contains(
				pocdin000040uv01.getPatientDomain())
				&& Constants.lstVisitTypeOutPatient().contains(
						pocdin000040uv01.getVisitType()))
        {
            logger.error("收到的检验预警消息为门诊类型,VisitType="
                + pocdin000040uv01.getVisitType() + "，不进行发送处理，退出业务!!");
            return;
        }

        if (StringUtils.isEmpty(pocdin000040uv01.getOrgCode()))
        {
        	// $Author :yang_mingjie
        	// $Date : 2014/06/26 10:09$
        	// [BUG]0045630 MODIFY BEGIN 
            pocdin000040uv01.setOrgCode(Constants.HOSPITAL_CODE);
            pocdin000040uv01.setOrgName(Constants.HOSPITAL_NAME);
          //[BUG]0045630 MODIFY END
        }

        // 获取源系统Id
        sourceSystemId = pocdin000040uv01.getSourceSystemId();

        // 从DB中拿出消息中的检验报告
        getReport(pocdin000040uv01);

        INFECTIOUSBUILD infectiousDto = null;

        InfectiousRecord inRecord = null;

        List<InfectiousContent> content = null;

        String mobileAddress = "";
        String mobileAddressOthers = "";

        warningDB.setSysDate(systemTime);
        // 1.查询短信发送人员的设定号码，如果返回为空则以下方法概不执行
        // 不为空时，保存短信发送人员的手机号码待用
        List<WarningNotice> notices = getNotices(pocdin000040uv01, RULE_GROUP);
        // 预警组以外的人员
        List<WarningNotice> otherNotices = getNotices(pocdin000040uv01,
                WARNING_GROUP);

        String isHistory = PropertiesUtils.getValue("sendMessage.history");

        // 获取手机号码
        Map<String, String> addressMap = null;
        // 获取手机号码
        Map<String, String> addressMapOther = null;
        if (notices != null && !notices.isEmpty())
        {
            addressMap = warningDB.getAddress(notices,
                    String.valueOf(DateUtils.getDayOfWeek(systemTime)));
            mobileAddress = addressMap.get("mobileAddress");
        }
        if (otherNotices != null && !otherNotices.isEmpty())
        {
            addressMapOther = warningDB.getAddress(otherNotices,
                    String.valueOf(DateUtils.getDayOfWeek(systemTime)));
            mobileAddressOthers = addressMapOther.get("mobileAddress");
        }

        if ((!notices.isEmpty() && !StringUtils.isEmpty(mobileAddress))
            || (!otherNotices.isEmpty() && !StringUtils.isEmpty(mobileAddressOthers)))
        {
            // 3.封装条件数据为XML
            INFECTIOUSBUILD infectious = null;
            // 针对微生物检验报告需要按照新规则重新拼装实体
            if (Constants.SOURCE_MICROBE.equals(sourceSystemId))
            {
                infectious = getInfectiousMICDto(pocdin000040uv01);
            }
            else if (Constants.SOURCE_LAB.equals(sourceSystemId))
            {
                infectious = getInfectiousLABDto(pocdin000040uv01);
            }
            else
            {
                logger.error(
                        "检验报告类型错误false: {} 检验报告版本状态: {}",
                        "labResult = " + labResult + "，params: labReportLid = "
                            + "，sourceSystemId = " + sourceSystemId
                            + "，reportTypeCode = "
                            + pocdin000040uv01.getReportTypeCode()
                            + "; orgCode = " + pocdin000040uv01.getOrgCode(),
                        pocdin000040uv01.getVersionNumber());
                return;
            }
            String conditionXml = warningOthers.callCreateByDtoInfectious(
                    infectious, Constants.WARNING_XML_ROOT);
            
            if (!StringUtils.isEmpty(conditionXml))
            {
                logger.debug("获取【条件数据】成功：\n" + conditionXml);
            }
            // 4.调用规则接口，得到返回值
            String noticeResult = callWebservice.ruleCheck(conditionXml);
            if (!StringUtils.isEmpty(noticeResult))
            {
                logger.debug("获取【结果数据】成功：\n" + noticeResult);
            }

            // 5.得到返回值，生成实体类，写对应的xpath
            infectiousDto = (INFECTIOUSBUILD) warningOthers.getBaseDto(
                    noticeResult, MAPPING_DTO);
            
            //对应BS319
            if (Constants.SOURCE_LAB.equals(pocdin000040uv01.getSourceSystemId()))
            {
	            //增加肝炎是否预警的信息
	            if(!checkLiverIfHashIll(pocdin000040uv01, infectiousDto))
	            {
	            	logger.error("检验消息中不存在需要预警的信息【预警结束】");
	
	                throw new MessageExternalSendNoWarningException("【预警结束】");
	            }
            }

            if (!checkIfHashIll(pocdin000040uv01, infectiousDto))
            {
                logger.error("检验消息中不存在需要预警的信息【预警结束】");

                throw new MessageExternalSendNoWarningException("【预警结束】");
            }
            int more = checkMore(infectiousDto);
            if (Constants.SOURCE_LAB.equals(pocdin000040uv01.getSourceSystemId())
                && more > 1)
            {
                logger.error(
                        "检验报告中传染病大于1条false: {} 检验报告版本状态: {}",
                        "labResult = " + labResult + "，params: labReportLid = "
                            + "，sourceSystemId = " + sourceSystemId
                            + "，reportTypeCode = "
                            + pocdin000040uv01.getReportTypeCode()
                            + "; orgCode = " + pocdin000040uv01.getOrgCode()
                            + ";共有" + more + "条传染病！",
                        pocdin000040uv01.getVersionNumber());
                return;
            }
            // 新增之前，判断该条检验项目对应的传染病是否已经发送过
            String existsFlag = dataIfExists(pocdin000040uv01, infectiousDto);
            if("true".equals(existsFlag)){
            	logger.debug("该检验项目对应的传染病已经预警过");
            }
            int isHaveWarnflag = 0;
            if(isWarnDisease(Constants.WARN_RULE_DISEASE, infectiousDto)){
            isHaveWarnflag = warnningRuleCheckDao.selectIsHaveWarned(pocdin000040uv01.getPatientLid(),Constants.getWarnRuleDisease(),Integer.parseInt(Constants.getWarnRuleDay()),pocdin000040uv01.getReportDate());
            }
            
           
            
            
            //int count = infectiousDto.getRow();
          //  boolean isWarnDisease = false;
            
            
           /* for (InfectiousRules row : infectiousDto.getRow())
            {
                for (InfectiousItem ite : row.getItem())
                {
                    if ("checkMessage".equals(ite.getItemName())
                        && Constants.getWarnRuleDisease().equals(ite.getValue()))
                    {
                    	isWarnDisease = true;
                    }
                }
            }*/
            
         
            // Author: jia_yanqing
            // Date: 2014/4/8 10:00
            // [BUG]0043513 MODIFY BEGIN
            // 2.使用|p_lid|、|p_domain|和|业务字段|条件查询【external_send_business】表
            // 检查【is_send_message】字段是否为1，为1则已经发送，为0则继续以下步骤
            inRecord = getInfectiousRecord(warningDB.selectInfectRecordForLab(
                    pocdin000040uv01.getPatientLid(),
                    //如果是江苏则用就诊类别
                    !"46600083-8".equals(pocdin000040uv01.getOrgCode())?pocdin000040uv01.getPatientDomain():pocdin000040uv01.getVisitType(),
                    pocdin000040uv01.getReportNo(), businessSource,
                    pocdin000040uv01.getOrgCode(),
                    pocdin000040uv01.getSourceSystemId(),
                    pocdin000040uv01.getReportTypeCode()));
            // [BUG]0043513 MODIFY END

            if ((inRecord == null || inRecord.getBusinessSn() == null)
                && SEND_OPEN.equals(pocdin000040uv01.getVersionNumber()))
            {
                List<LabReport> reprots = pocdin000040uv01.getReport();

                InfectiousRecord record = new InfectiousRecord();

                List<InfectiousContent> inContentList = new ArrayList<InfectiousContent>();

                String isSameInfName = isSameInfName(pocdin000040uv01,
                        infectiousDto, pocdin000040uv01.getSourceSystemId());

                logger.debug("消息标识为新增业务并且DB中查不到消息对应数据，开始新增消息对应的数据【Insert操作】");

                // Author: jia_yanqing
                // Date: 2014/4/8 10:00
                // [BUG]0043535 MODIFY BEGIN
                if (NOT_EXISTS_FLAG.equals(isSameInfName)&&isHaveWarnflag==0)
                // [BUG]0043535 MODIFY END
                {
                    // 新增记录
                    warningDB.insertInfectiousForLab(
                            record,
                            inContentList,
                            reprots,
                            DateUtils.stringToDate(pocdin000040uv01.getReportDate()),
                            pocdin000040uv01, infectiousDto);
                }
                else
                {
                    logger.debug("【Insert操作】失败，原因是数据库中已经存在该患者的相关传染病信息！");
                }

            }
            else if (DELETE_FLAG.equals(pocdin000040uv01.getVersionNumber()))
            {
                logger.debug("消息标识为删除业务，开始删除消息对应DB的数据【Delete操作】");
                // Author: jia_yanqing
                // Date: 2014/4/8 10:00
                // [BUG]0043513 MODIFY BEGIN
                inRecord = warningDB.selectInfectRecordForLab(
                        pocdin000040uv01.getPatientLid(),
                        !"46600083-8".equals(pocdin000040uv01.getOrgCode())?pocdin000040uv01.getPatientDomain():pocdin000040uv01.getVisitType(),
                        pocdin000040uv01.getReportNo(),
                        businessSource,// //////////lab_report 使用什么唯一约束
                        pocdin000040uv01.getOrgCode(),
                        pocdin000040uv01.getSourceSystemId(),
                        pocdin000040uv01.getReportTypeCode());
                // [BUG]0043513 MODIFY END
                if (inRecord == null)
                {
                    logger.debug("需要删除的对应消息中的业务数据在DB中不存在！删除失败！");
                }
                else
                {
                    content = warningDB.selectContentRecord(inRecord);
                    warningDB.deleteInfectious(inRecord, content);
                }
                return;
            }
            else
            {
                // Author: jia_yanqing
                // Date: 2014/4/8 10:00
                // [BUG]0043513 MODIFY BEGIN
                // 隔离事务重新组装一次数据库对象
                inRecord = warningDB.selectInfectRecordForLab(
                        pocdin000040uv01.getPatientLid(),
                        !"46600083-8".equals(pocdin000040uv01.getOrgCode())?pocdin000040uv01.getPatientDomain():pocdin000040uv01.getVisitType(),
                        pocdin000040uv01.getReportNo(), businessSource,
                        pocdin000040uv01.getOrgCode(),
                        pocdin000040uv01.getSourceSystemId(),
                        pocdin000040uv01.getReportTypeCode());
                // [BUG]0043513 MODIFY END
                if (inRecord != null)
                {
                    content = warningDB.selectContentRecord(inRecord);
                }
                logger.debug("消息标识为更新业务，开始更新消息对应DB的数据【Update操作】");
                if (inRecord != null)
                {
                    warningDB.updateInfectiousForLab(
                            inRecord,
                            content,
                            pocdin000040uv01,
                            infectiousDto,
                            DateUtils.stringToDate(pocdin000040uv01.getReportDate()));
                }
                else
                {
                    logger.error("【Update操作】失败！ 源于需要更新的数据不存在InfectiousRecord==null");
                }

            }

            // 隔离事务重新组装一次数据库对象
            inRecord = warningDB.selectInfectRecord(
                    pocdin000040uv01.getPatientLid(),
                    !"46600083-8".equals(pocdin000040uv01.getOrgCode())?pocdin000040uv01.getPatientDomain():pocdin000040uv01.getVisitType(),
                    pocdin000040uv01.getReportNo(), businessSource,
                    pocdin000040uv01.getOrgCode());

            if (inRecord != null)
            {
                content = warningDB.selectContentRecord(inRecord);
            }

            if (SEND_OPEN.equals(isHistory)
                && SEND_OPEN.equals(pocdin000040uv01.getVersionNumber())
                && NOT_EXISTS_FLAG.equals( existsFlag))
            {
                logger.debug("短信发送开关验证【成功】，准备调用短信发送接口。。。。");
                // 6.将步骤【1】中查询的短信发送人员号码与医生号、送检科室等信息进行封装成短信发送的内容
                // 7.调用短信发送接口，发送短信
                // 增加判断业务，在此处由程序自动发送的短信需要判断autoFlag标志是否为true
                Map<String, String> status = sendMobile(infectiousDto, notices,
                        inRecord, addressMap, addressMapOther, pocdin000040uv01);

                String isUncommonUpdateOrException = status.get("sta");
                String isUncommonUpdateOrExceptionOther = status.get("oSta");

                // 8.更新步骤【2】中的【is_send_message】字段值
                String flag = null;
                if (COMPLETE_NORMAL_STATUS.equals(isUncommonUpdateOrException)
                    && isUncommonUpdateOrException != null)
                {
                    flag = "1";
                    if (inRecord != null)
                    {
                        content = warningDB.selectContentRecord(inRecord);
                    }
                    warningDB.updateRest(content, flag, "2");
                    logger.debug("将该检验报告传染病预警运行情况存入DB，success。");
                }
                else
                {
                    flag = "0";
                    logger.debug("向【院感科人员】短信未发送。");
                }
                if (COMPLETE_NORMAL_STATUS.equals(isUncommonUpdateOrExceptionOther)
                    && isUncommonUpdateOrExceptionOther != null)
                {
                    flag = "1";
                    if (inRecord != null)
                    {
                        content = warningDB.selectContentRecord(inRecord);
                    }
                    warningDB.updateRest(content, flag, "5");
                    logger.debug("将该检验报告传染病预警运行情况存入DB，success。");
                }
                else
                {
                    flag = "0";
                    logger.debug("向【其他科室人员】短信未发送。");
                }
            }
        }
        else
        {
            if (notices.isEmpty() && otherNotices.isEmpty())
            {
                logger.debug("notice为空。。。。。。。。。。。。。。。。。。。");
            }
            if (StringUtils.isEmpty(mobileAddress)
                && StringUtils.isEmpty(mobileAddressOthers))
            {
                logger.debug("联系人号码为空。。。。。。。。。。。。。。。。。");
            }
        }

        logger.debug("【预警业务结束，退出】");
    }

    private int checkMore(INFECTIOUSBUILD dto)
    {
        int more = 0;
        for (InfectiousRules row : dto.getRow())
        {
            for (InfectiousItem item : row.getItem())
            {
                if ("checkStatus".equals(item.getItemName())
                    && "true".equals(item.getValue()))
                {
                    more++;
                }
            }
        }
        return more;
    }

    private boolean checkIfHashIll(POCDIN000040UV01 pocdin000040uv01,
            INFECTIOUSBUILD dto)
    {
        boolean flag = false;
        if (Constants.SOURCE_LAB.equals(pocdin000040uv01.getSourceSystemId()))
        {
            for (InfectiousRules row : dto.getRow())
            {
                for (InfectiousItem ite : row.getItem())
                {
                    if ("checkStatus".equals(ite.getItemName())
                        && "true".equals(ite.getValue()))
                    {
                        flag = true;
                    }
                }
            }
        }
        else if (Constants.SOURCE_MICROBE.equals(pocdin000040uv01.getSourceSystemId()))
        {
            for (InfectiousRules row : dto.getRow())
            {
                for (InfectiousItem ite : row.getItem())
                {
                    if ("checkMessage".equals(ite.getItemName())
                        && !StringUtils.isEmpty(ite.getValue()))
                    {
                        flag = true;
                    }
                }
            }
        }
        return flag;
    }
    
    /**
     * 判断是否有乙型肝炎预警信息   add by wp 
     */
    private boolean checkLiverIfHashIll(POCDIN000040UV01 pocdin000040uv01,
            INFECTIOUSBUILD dto)
    {
        boolean checkStatusFlag = false;
        boolean checkKeyFlag = false;
        boolean flag = false;
            for (InfectiousRules row : dto.getRow())
            {
            	List<String> allNum = new ArrayList<String>();
            	List<String> unnormalNum = new ArrayList<String>();
                for (InfectiousItem ite : row.getItem())
                {
                    if ("checkStatus".equals(ite.getItemName())
                        && "true".equals(ite.getValue()))
                    {
                    	checkStatusFlag = true;
                    }
                    
                    if ("checkKey".equals(ite.getItemName())
                            && PropertiesUtils.getValue("liverCode").equals(ite.getValue()))
                    {
                    	checkKeyFlag = true;
                    }
                    
                    if(checkStatusFlag && checkKeyFlag)
                    {
                    	flag = checkLiverIntectionItem(pocdin000040uv01,allNum,unnormalNum);
                    }
                    if(checkStatusFlag && !checkKeyFlag)
                    {
                    	flag = true;
                    }
                }
            }
        return flag;
    }
    
    /**
     * 判断乙型肝炎检查项，通过不合格检查项的报告日期和所有涉及乙型肝炎检查项的查询报告日期比较，判断是否修改flag
     * add by wp
     * 1、没有检查项：flag = true;
     * 2、有检查项，比较日期，去最新报告日期，检查项合格flag = false；不合格flag = true
     */
    private boolean checkLiverIntectionItem(POCDIN000040UV01 pocdin000040uv01,List<String> allNum,
    		List<String> unnormalNum)
    {
    	boolean flag = false;
    	//病人ID
    	String patientID = pocdin000040uv01.getPatientLid();
    	//报告日期
    	String reportDate = pocdin000040uv01.getReportDate();
    	//间隔时间
    	int intervalHours = Integer.parseInt(PropertiesUtils.getValue("intervalHours"));
    	//检验项目编码
    	String[] inspectionItems = PropertiesUtils.getValue("inspectionItems").split("/");
    	List<String> itemsList = java.util.Arrays.asList(inspectionItems);
    	//1、报告结果为阳性且无检验项目则直接预警
    	//2、报告结果为阳性且检验项目正常不预警
    	allNum = labService.selectLiverInfectiousItem(patientID,reportDate,intervalHours,itemsList,"false",pocdin000040uv01.getSourceSystemId());
    	if(allNum == null || allNum.isEmpty()) 
    	{
    		flag = true;
    	}
    	if(allNum !=null && !allNum.isEmpty())   
    	{
    		unnormalNum = labService.selectLiverInfectiousItem(patientID,reportDate,intervalHours,itemsList,"true",pocdin000040uv01.getSourceSystemId());
    		if(unnormalNum != null && !unnormalNum.isEmpty())
    		{
    		   Date date1 = DateUtils.stringToDate(allNum.get(0),DateUtils.PATTERN_MINUS_DATETIME);
    		   Date date2 = DateUtils.stringToDate(unnormalNum.get(0),DateUtils.PATTERN_MINUS_DATETIME);
    		   if(DateUtils.compareDate(date1, date2)==0)
    		   {
        		   flag = true;
    		   }
    		}	
    		
    	}
    	return flag;
    }

    // Author: jia_yanqing
    // Date: 2014/4/8 10:00
    // [BUG]0043535 ADD BEGIN
    private String isSameInfName(POCDIN000040UV01 pocdin000040uv01,
            INFECTIOUSBUILD dto, String sourceSystemId)
    {
        List<Object> rec = notice.selectInfectCheck(
                pocdin000040uv01.getPatientLid(),
                !"46600083-8".equals(pocdin000040uv01.getOrgCode())?pocdin000040uv01.getPatientDomain():pocdin000040uv01.getVisitType(),
                !"46600083-8".equals(pocdin000040uv01.getOrgCode())?pocdin000040uv01.getVisitTimes():pocdin000040uv01.getVisitOrdNo(),
                pocdin000040uv01.getOrgCode(), sourceSystemId);
        if (rec != null)
        {
            for (Object obj : rec)
            {
                InfectiousRecord ir = (InfectiousRecord) obj;
                List<InfectiousContent> items = warningDB.selectContentRecord(ir);
                if (items != null && !items.isEmpty())
                {
                    for (InfectiousRules row : dto.getRow())
                    {
                        for (InfectiousItem ite : row.getItem())
                        {
                            for (InfectiousContent con : items)
                            {
                                if ("checkMessage".equals(ite.getItemName())
                                    && ite.getValue().equals(
                                            con.getInfectiousName()))
                                {
                                    return EXISTS_FLAG;
                                }
                            }
                        }
                    }
                }

            }
        }
        return NOT_EXISTS_FLAG;
    }
    // [BUG]0043535 ADD END

    private String dataIfExists(POCDIN000040UV01 pocdin000040uv01,
            INFECTIOUSBUILD dto)
    {
        noSendMap = new HashMap<BigDecimal, List<String>>();
        List<Object> rec = notice.selectInfectCheck(
                pocdin000040uv01.getPatientLid(),
                !"46600083-8".equals(pocdin000040uv01.getOrgCode())?pocdin000040uv01.getPatientDomain():pocdin000040uv01.getVisitType(),
                !"46600083-8".equals(pocdin000040uv01.getOrgCode())?pocdin000040uv01.getVisitTimes():pocdin000040uv01.getVisitOrdNo(),
                pocdin000040uv01.getOrgCode(), null);
        if (rec != null)
        {
            // int DBsize = 0;
            for (Object obj : rec)
            {
                List<String> names = new ArrayList<String>();
                InfectiousRecord ir = (InfectiousRecord) obj;
                List<InfectiousContent> items = warningDB.selectContentRecord(ir);
                if (items != null && !items.isEmpty())
                {
                    for (InfectiousRules row : dto.getRow())
                    {
                        boolean thisRowEq = false;
                        boolean isIll = false;
                        for (InfectiousContent con : items)
                        {
                            for (InfectiousItem ite : row.getItem())
                            {
                                if ("checkStatus".equals(ite.getItemName())
                                    && "true".equals(ite.getValue()))
                                {
                                    isIll = true;
                                }
                            }
                            if (Constants.SOURCE_LAB.equals(pocdin000040uv01.getSourceSystemId()))
                            {
                                for (InfectiousItem ite : row.getItem())
                                {
                                    if ("checkMessage".equals(ite.getItemName())
                                        && ite.getValue().equals(
                                                con.getInfectiousName())
                                        //&& "1".equals(con.getSendFlag())
                                        && isIll)
                                    {
                                        thisRowEq = true;
                                    }
                                }
                            }
                            else if (Constants.SOURCE_MICROBE.equals(pocdin000040uv01.getSourceSystemId()))
                            {
                                for (InfectiousItem ite : row.getItem())
                                {
                                    if ("checkMessage".equals(ite.getItemName())
                                        && ite.getValue().equals(
                                                con.getInfectiousName())
                                        //&& "1".equals(con.getSendFlag())
                                        )
                                    {
                                        thisRowEq = true;
                                    }
                                }
                            }

                            if (thisRowEq)
                            {
                                names.add(con.getInfectiousName());
                            }
                        }
                    }
                }

                // Author: jia_yanqing
                // Date: 2014/4/1 16:00
                // [BUG]0043396 MODIFY BEGIN
                if (names != null && !names.isEmpty())
                {
                    noSendMap.put(ir.getBusinessSn(), names);
                }
                // [BUG]0043396 MODIFY END
                /*
                 * if (items != null) { DBsize += items.size(); }
                 */
            }
            /*
             * if (DBsize == noSendMap.size()) { return EXISTS_FLAG; }
             */
            if (noSendMap != null && !noSendMap.isEmpty())
            {
                return EXISTS_FLAG;
            }
        }
        return NOT_EXISTS_FLAG;
    }

    private List<WarningNotice> getNotices(POCDIN000040UV01 pocdin000040uv01,
            String group)
    {
        /*
         * $Author :chang_xuewen $Date : 2014/03/21 11:00$ [BUG]0043136 ADD
         * BEGIN
         */
        String wardNo = wardsNo;

        if (RULE_GROUP.equals(group))
        {
            wardNo = null;
        }

        List<WarningNotice> noticeValidation = warningDB.selectInfectious(
                group, pocdin000040uv01.getOrgCode(), wardNo);
        /* [BUG]0043136 ADD END */
        return noticeValidation;
    }

    // 一般检验实体封装
    public INFECTIOUSBUILD getInfectiousLABDto(POCDIN000040UV01 pocdin000040uv01)
    {
        INFECTIOUSBUILD in = new INFECTIOUSBUILD();

        in.setMsgId(LAB_SERVICE_ID);

        in.setMsgName(RULE_CONDITION_SERVICE_NAME);

        in.setSourceSysCode(CDR_SYSTEM_CODE);

        in.setTargetSysCode("");

        in.setCreateTime(DateUtils.dateToString(systemTime,
                DateUtils.PATTERN_COMPACT_DATETIME));

        in.setServiceId(warningOthers.getServiceId(pocdin000040uv01.getSourceSystemId()));

        in.setVisitDept(HOSPITAL_DEPT);

        in.setHospitalCode(pocdin000040uv01.getOrgCode());

        in.setHospitalName(pocdin000040uv01.getOrgName());

        in.setRoleGroupType(RULE_GROUP);

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

    // 微生物检验实体封装
    public INFECTIOUSBUILD getInfectiousMICDto(POCDIN000040UV01 pocdin000040uv01)
    {
        INFECTIOUSBUILD in = new INFECTIOUSBUILD();

        in.setMsgId(MIC_SERVICE_ID);

        in.setMsgName(RULE_CONDITION_SERVICE_NAME);

        in.setSourceSysCode(CDR_SYSTEM_CODE);

        in.setTargetSysCode("");

        in.setCreateTime(DateUtils.dateToString(systemTime,
                DateUtils.PATTERN_COMPACT_DATETIME));

        in.setServiceId(warningOthers.getServiceId(pocdin000040uv01.getSourceSystemId()));

        in.setVisitDept(HOSPITAL_DEPT);

        in.setHospitalCode(pocdin000040uv01.getOrgCode());

        in.setHospitalName(pocdin000040uv01.getOrgName());

        in.setRoleGroupType(RULE_GROUP);

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

                // 微生物检验项目，传入条件
                // 项目warningValue
                InfectiousItem item = new InfectiousItem();

                item.setItemName(MIC_PARAM);

                item.setValue(itemm.getItemNameEn());

                itemList.add(item);

                InfectiousRules ir = new InfectiousRules();
                count++;
                ir.setRowKey(count + "");

                ir.setFactType(MIC_ROUTE);

                ir.setItem(itemList);

                rows.add(ir);
            }

        }
        in.setRow(rows);
        return in;
    }

    private Map<String, String> sendMobile(INFECTIOUSBUILD resultDto,
            List<WarningNotice> notices, InfectiousRecord inRecord,
            Map<String, String> addressMap,
            Map<String, String> addressMapOther,
            POCDIN000040UV01 pocdin000040uv01)
    {
        String mobileStatus = null;
        /*江苏不区分预警组及其他人员*/
//        String mobileStatusToOther = null;
        
        List<List<String>> sendMobileMessageContent = null;
        String mobileAddress = null;
//        String mobileAddressOther = null;
        if (addressMap != null && !addressMap.isEmpty())
        {
            mobileAddress = addressMap.get("mobileAddress");
        }
//        if (addressMapOther != null && !addressMapOther.isEmpty())
//        {
//            mobileAddressOther = addressMapOther.get("mobileAddress");
//        }

        // 获取短信发送内容
        sendMobileMessageContent = getMobileMessageContent(resultDto,
                pocdin000040uv01);
        List<String> sendContent = sendMobileMessageContent.get(0);
//        List<String> sendContentForOther = sendMobileMessageContent.get(1);

        if (sendMobileMessageContent.size() != 0 && sendContent.size() == 0)
        {
            logger.debug("疾病内容不为空，但不是【空气传播疾病,飞沫传播疾病】类型，因此不发送短信！。。。。。");
        }

        // 自动发送项目
        List<String> autoItems = new ArrayList<String>();
        // 非自动发送项目
//        List<String> nonAutoItems = new ArrayList<String>();
        // 自动发送为true
            for (InfectiousRules row : resultDto.getRow())
            {               
               /* for (InfectiousItem item : row.getItem())
                {
                    if ("checkStatus".equals(item.getItemName())
                        && "true".equals(item.getValue()))
                    {// 是否传染标志
                        isInf = true;
                    }
                }
                for (InfectiousItem item : row.getItem())
                {
                    if (isInf && "autoFlag".equals(item.getItemName()))
                    {// 自动发送标志
                        autoFlag = item.getValue();
                    }
                }
                for (InfectiousItem item : row.getItem())
                {
                    if (isInf && "true".equals(autoFlag)
                        && "checkMessage".equals(item.getItemName()))
                    {
                        autoItems.add(item.getValue());
                    }
                } */
                //归为一个循环 fix by wp
            	boolean isInf = false;
                boolean autoFlag = false;
                boolean checkMessage = false;
                for (InfectiousItem item : row.getItem())
                {
                	if("checkMessage".equals(item.getItemName()))
                	{
                		checkMessage = true;
                	}
                	
                	 if ("checkStatus".equals(item.getItemName())
                             && "true".equals(item.getValue()))
                     {// 是否传染标志
                        isInf = true;
                     }
                	 
                     if ("autoFlag".equals(item.getItemName()) 
                    		 && "true".equals(item.getValue()))
                      {// 自动发送标志
                         autoFlag = true;
                      }
                     
                    if(Constants.SOURCE_LAB.equals(pocdin000040uv01.getSourceSystemId()))
                    {
                          if (isInf && autoFlag
                                  && checkMessage)
                          {
                              autoItems.add(item.getValue());
                          }
                    }else{
                    	  if(checkMessage)
                    	  {
                    		  autoItems.add(item.getValue());
                    	  }
                    }
                }
                
                /* 目前只发送autoFlag为true的内容
                for (InfectiousItem item : row.getItem())
                {
                    if (isInf && "false".equals(autoFlag)
                        && "checkMessage".equals(item.getItemName()))
                    {
                        nonAutoItems.add(item.getValue());
                    }
                }
                */
            }
        // 根据不发送表去排除不需要发送的短信内容
        for (Map.Entry<BigDecimal, List<String>> ent : noSendMap.entrySet())
        {
            for (String name : ent.getValue())
            {
                for (int i = 0; i < sendContent.size(); i++)
                {
                    if (sendContent.get(i).contains(name))
                    {
                        sendContent.remove(i);
                    }
                }
//                for (int i = 0; i < sendContentForOther.size(); i++)
//                {
//                    if (sendContentForOther.get(i).contains(name))
//                    {
//                        sendContentForOther.remove(i);
//                    }
//                }
            }
        }
       
        
        /*对other部分暂不做处理
        if (sendContentForOther != null && sendContentForOther.size() != 0)        	
        	/*&& !NON_DEPT.equals(pocdin000040uv01.getVisitDept())
            	&& !Constants.YG_NON_WARD.equals(wardsNo)
            	江苏省人民医院不需要判断【肝病科】和【老院综合51病房】 不予发送信息
            */
        /*
        {
            try
            {
                if (!StringUtils.isEmpty(mobileAddressOther))
                {
                    for (String message : sendContentForOther)
                    {
                        // 发送短信
                        if (autoItems != null
                            && !autoItems.isEmpty()
                            && Constants.SOURCE_LAB.equals(pocdin000040uv01.getSourceSystemId()))
                        {
                            int back = callWebservice.sendMessageNo(
                                    mobileAddressOther, message);
                            if (back == 1)
                            {
                                mobileStatusToOther = COMPLETE_NORMAL_STATUS;
                                logger.debug("发送ok");
                            }
                            else if (back != 1)
                            {
                                logger.debug("短信发送失败！发挥状态为：" + back);
                            }
                        }
                    }
                }
                else
                {
                    logger.debug("短信发送失败！手机号码为空");
                }

            }
            catch (Exception ex)
            {
                mobileStatusToOther = COMPLETE_EXCEPTION_STATUS;

                ex.printStackTrace();
            }

        }
        */
        
        /*江苏省人民医院不做此判断
        else if (NON_DEPT.equals(pocdin000040uv01.getVisitDept())
            || Constants.YG_NON_WARD.equals(wardsNo))
        {
            logger.debug("对于【肝病科】和【老院综合51病房】 不予发送信息！ ");
        }*/

        if (sendContent != null && sendContent.size() != 0)
        {
            try
            {
            	if (!StringUtils.isEmpty(mobileAddress))
                {
                    for (String message : sendContent)
                    {
                        // autoItems不为空发送短信
                    	/* 原代码部分
                    	 * nonAutoItems != null
                            && !nonAutoItems.isEmpty()
                    	 * */
                        if (autoItems != null && !autoItems.isEmpty()){
                        	/* 旧代码
						    if (Constants.SOURCE_LAB.equals(pocdin000040uv01.getSourceSystemId()))
							{
								int back = callWebservice.sendMessageNo(
										mobileAddress, message);
								if (back == 1)
								{
									mobileStatus = COMPLETE_NORMAL_STATUS;
									logger.debug("发送ok");
								}
								else if (back != 1)
								{
									logger.debug("短信发送失败！发挥状态为：" + back);
								}
							}
							else if (Constants.SOURCE_MICROBE.equals(pocdin000040uv01.getSourceSystemId()))
							{
								int back = callWebservice.sendMessageNo(
										mobileAddress, message);
								if (back == 1)
								{
									mobileStatus = COMPLETE_NORMAL_STATUS;
									logger.debug("发送ok");
								}
								else if (back != 1)
								{
									logger.debug("短信发送失败！发挥状态为：" + back);
								}
							}
							*/
	                        //Author: yu_yzh
	                        //Date: 2015/5/6
	                        //江苏短信接口改造 CODE BEGIN
						    if(Constants.SOURCE_LAB.equals(pocdin000040uv01.getSourceSystemId())
						    	|| Constants.SOURCE_MICROBE.equals(pocdin000040uv01.getSourceSystemId())){
						    	
		                        Object backObj = callWebservice.sendMessageNew(mobileAddress, message);
		                        
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
						    //江苏短信接口改造 CODE END
						} 
                    }
                }
                else
                {
                    logger.debug("短信发送失败！手机号码为空");
                }

            }
            catch (Exception ex)
            {
                mobileStatus = COMPLETE_EXCEPTION_STATUS;

                ex.printStackTrace();
            }

        }

        Map<String, String> status = new HashMap<String, String>();
        status.put("sta", mobileStatus);
//        status.put("oSta", mobileStatusToOther);
        return status;
    }

    private Map<String, String> getCodeNameMap(INFECTIOUSBUILD ib,
            POCDIN000040UV01 pocdin000040uv01)
    {
        Map<String, String> codNam = new HashMap<String, String>();
        for (LabReport rep : pocdin000040uv01.getReport())
        {
            for (LabReportResult re : rep.getReportResult())
            {
                for (InfectiousRules row : ib.getRow())
                {
                    if (Constants.SOURCE_LAB.equals(pocdin000040uv01.getSourceSystemId()))
                    {
                        String key = "";
                        String name = "";
                        for (InfectiousItem ite : row.getItem())
                        {
                            if ("checkKey".equals(ite.getItemName()))
                            {
                                key = ite.getValue();
                            }
                        }
                        if (key.equals(re.getItemCode()))
                        {
                            name = re.getItemNameCn();
                            codNam.put(key, name);
                            break;
                        }
                    }
                    else if (Constants.SOURCE_MICROBE.equals(pocdin000040uv01.getSourceSystemId()))
                    {
                        String key = "";
                        String name = "";
                        for (InfectiousItem ite : row.getItem())
                        {
                            if ("warningValue".equals(ite.getItemName()))
                            {
                                key = ite.getValue();
                            }
                        }
                        if (key.equals(re.getItemNameEn()))
                        {
                            name = re.getItemNameEn();
                            codNam.put(key, name);
                            break;
                        }
                    }
                }
            }
        }
        return codNam;
    }

    private List<List<String>> getMobileMessageContent(INFECTIOUSBUILD ib,
            POCDIN000040UV01 pocdin000040uv01)
    {
        String[] typeList = { "空气传播疾病", "飞沫传播疾病", "接触传播疾病", "血液（或性）传播疾病",
                "虫媒或动物传播疾病" };
        
        StringBuffer values1 = new StringBuffer();
        StringBuffer values2 = new StringBuffer();
        StringBuffer values3 = new StringBuffer();
        StringBuffer values4 = new StringBuffer();
        StringBuffer values5 = new StringBuffer();
        StringBuffer[] values = { values1, values2, values3, values4, values5 };

        String commenStr = null;

        List<String> result = new ArrayList<String>();
        List<String> sendList = new ArrayList<String>();
        String wardNo = "";
        String bedNo = "";
        if (!StringUtils.isEmpty(wardsNo))
        {
            wardNo = wardsNo;
        }
        if (!StringUtils.isEmpty(pocdin000040uv01.getBedNo()))
        {
            bedNo = pocdin000040uv01.getBedNo();
        }

        commenStr = "您好," + wardNo + bedNo + "床  患者"
            + pocdin000040uv01.getPatientName() + "，为【 ";
        Map<String, String> codeName = getCodeNameMap(ib, pocdin000040uv01);

        for (InfectiousRules rules : ib.getRow())
        {
            List<InfectiousItem> items = rules.getItem();
            String value = "";

            if (codeName != null
                && Constants.SOURCE_LAB.equals(pocdin000040uv01.getSourceSystemId()))
            {
                for (Map.Entry<String, String> ent : codeName.entrySet())
                {
                    for (InfectiousItem item : items)
                    {
                        if ("checkKey".equals(item.getItemName())
                            && item.getValue().equals(ent.getKey()))
                        {
                            value = ent.getValue();
                        }
                    }
                }
            }
            else if (codeName != null
                && Constants.SOURCE_MICROBE.equals(pocdin000040uv01.getSourceSystemId()))
            {
                for (Map.Entry<String, String> ent : codeName.entrySet())
                {
                    for (InfectiousItem item : items)
                    {
                        if ("warningValue".equals(item.getItemName())
                            && item.getValue().equals(ent.getKey()))
                        {
                            value = ent.getValue();
                        }
                    }
                }
            }

            if (Constants.SOURCE_LAB.equals(pocdin000040uv01.getSourceSystemId()))
            {
                boolean isPlus = false;
                for (InfectiousItem item : items)
                {
                    String type = item.getItemName();
                    if ("itemStrValue".equals(type)
                        && !StringUtils.isEmpty(item.getValue()))
                    {
                        value += "(" + item.getValue() + ")";
                        isPlus = true;
                    }
                }
                if (!isPlus)
                {
                    for (InfectiousItem item : items)
                    {
                        String type = item.getItemName();
                        if ("itemNumValue".equals(type)
                            && !StringUtils.isEmpty(item.getValue()))
                        {
                            // value += "(" + item.getValue() + ")";
                            value += "(" + WARNING_NUM_TO_STR + ")";
                        }

                        // TODO 拼写单位
                    }
                }
            }
            if (Constants.SOURCE_MICROBE.equals(pocdin000040uv01.getSourceSystemId()))
            {
                for (InfectiousItem item : items)
                {
                    String type = item.getItemName();
                    if ("warningValue".equals(type))
                    {
                        value = item.getValue();
                    }
                }
            }

            for (InfectiousItem item : items)
            {
                String type = item.getItemName();
                if ("infectionRoute".equals(type))
                {
                    String typename = item.getValue();
                    if (typeList[0].equals(typename))
                    {
                        values1.append(value);
                        values1.append(",");
                    }
                    else if (typeList[1].equals(typename))
                    {

                        values2.append(value);
                        values2.append(",");

                    }
                    else if (typeList[2].equals(typename))
                    {

                        values3.append(value);
                        values3.append(",");

                    }
                    else if (typeList[3].equals(typename))
                    {

                        values4.append(value);
                        values4.append(",");

                    }
                    else if (typeList[4].equals(typename))
                    {

                        values5.append(value);
                        values5.append(",");

                    }
                }
            }
        }

        for (int i = 0; i < typeList.length; i++)
        {
            if (values[i].toString().length() != 0
                && values[i].toString() != null)
            {
                String temp = values[i].substring(0, values[i].length() - 1);
                values[i].delete(0, values[i].length());
                values[i].append(temp);
                if (i == 0)
                {
                    values[0].append("】，该疾病主要通过空气传播，请执行感控措施：患者单间隔离，有条件时隔离于负压病房；悬挂黄色标识；患者病容容许时，戴外科口罩，限制其活动范围；做好标准预防；医务人员佩戴帽子和医用防护口罩；诊疗操作可能喷溅时，戴护目镜或防护面罩，穿防护服；接触患者及其血液、体液、分泌物、排泄物等物质时戴手套并严格执行手卫生；病室严格空气消毒。【感染管理办公室】电话：6917/5220");
                    result.add(commenStr + values[0].toString());
                    sendList.add(commenStr + values[0].toString());
                }
                else if (i == 1)
                {
                    values[1].append("】，该疾病主要通过飞沫传播，请执行感控措施：患者单间隔离；悬挂粉色标识；患者病容容许时，戴外科口罩，限制其活动范围；患者之间、患者与探视者相隔距离1m以上，探视者戴外科口罩；做好标准预防；与患者近距离（1m以内）接触，医务人员佩戴帽子和医用防护口罩；诊疗操作可能喷溅时，戴护目镜或防护面罩，穿防护服；接触患者及其血液、体液、分泌物、排泄物等物质时戴手套并严格执行手卫生；病室加强通风，或进行空气消毒。【感染管理办公室】电话：6917/5220");
                    result.add(commenStr + values[1].toString());
                    sendList.add(commenStr + values[1].toString());
                }
                else if (i == 2)
                {
                    values[2].append("】，该疾病主要通过接触传播，请执行感控措施：患者单间或床边隔离；悬挂蓝色标识；接触患者血液、体液、分泌物、排泄物等物质时戴手套，手上有伤口时应戴双层手套；离开病室前，接触污染物品后应摘除手套，洗手或卫生手消毒；从事可能污染工作服的操作时，应穿隔离衣；接触甲类传染病应穿防护服。【感染管理办公室】电话：6917/5220");
                    result.add(commenStr + values[2].toString());
                }
                else if (i == 3)
                {
                    values[3].append("】，HBV、HCV通过接触血液、体液传播，HIV、梅毒通过性接触或接触患者血液、体液传播，淋病通过性接触或接触患者分泌物污染的物品而传播，请执行感控措施：医务人员知情，防止锐器伤；在标准预防的基础上，接触患者及其血液、体液、分泌物、排泄物等物质时戴手套，手上有伤口时应戴双层手套；从事可能污染工作服的操作时，应穿隔离衣。【感染管理办公室】电话：6917/5220");
                    result.add(commenStr + values[3].toString());
                }
                else if (i == 4)
                {
                    values[4].append("】，您好, 病区：血液科(10A),床：039,患者：***,为      【预警内容】，该疾病主要通过虫媒或动物传播（患者不是传染源），请执行感控措施：病区防虫驱虫；严格执行防护措施；医疗废物双层包装；终末消毒。【感染管理办公室】电话：6917/5220");
                    result.add(commenStr + values[4].toString());
                }
            }
        }
        // values[0].append("】（LIS检验结果或传染病报卡病例分类内容），该疾病病原体主要通过空气传播，请认真执行以下感染控制措施：患者隔离于负压病房（6B病区），禁止探视，指定医务人员执行诊疗，环境消毒（4次/日），医疗废物双层包装，医务人员严格执行手卫生和防护措施（佩戴N95口罩、帽子，接触体液戴手套，进行可能喷溅的操作穿防护服并戴护目镜）。【医院感染管理办公室】电话：5918/5922");
        // values[1].append("】（LIS检验结果或传染病报卡病例分类内容），该疾病病原体主要通过飞沫传播，请认真执行以下感染控制措施：患者单间隔离，病室保持空气流通，限制探视，分组诊疗，环境消毒（4次/日），医疗废物双层包装，医务人员严格执行手卫生和防护措施（佩戴口罩、帽子，接触体液戴手套，进行可能喷溅的操作穿戴N95口罩、隔离衣和护目镜）。【医院感染管理办公室】电话：5918/5922");
        // values[2].append("】（LIS检验结果或传染病报卡病例分类内容），该疾病病原体主要通过接触传播，请认真执行以下感染控制措施：患者单间或床旁隔离，限制探视，分组诊疗，环境消毒（4次/日），医疗废物双层包装，医务人员严格执行手卫生和防护措施（接触体液戴手套，进行可能喷溅的操作穿戴口罩、隔离衣和护目镜）。【医院感染管理办公室】电话：5918/5922");
        // values[3].append("】（LIS检验结果或传染病报卡病例分类内容），该疾病病原体主要通过血液（或性）传播，请认真执行以下感染控制措施：医务人员防止锐器伤，严格执行防护措施（接触体液戴手套，进行可能喷溅的操作穿戴口罩、隔离衣和护目镜），医疗废物双层包装。【医院感染管理办公室】电话：5918/5922");
        // values[4].append("】（LIS检验结果或传染病报卡病例分类内容），该疾病主要通过虫媒或动物传播（患者不是传染源），请认真执行以下感染控制措施：病区防虫驱虫，医务人员严格执行防护措施（接触体液戴手套，进行可能喷溅的操作穿戴口罩、隔离衣和护目镜），医疗废物双层包装。【医院感染管理办公室】电话：5918/5922");
        List<List<String>> list = new ArrayList<List<String>>();
        list.add(sendList);
        list.add(result);

        return list;
    }

    private InfectiousRecord getInfectiousRecord(InfectiousRecord inRecord)
    {
        if (inRecord == null)
        {
            return new InfectiousRecord();
        }

        return inRecord;
    }
    
 private boolean isWarnDisease(String disease,INFECTIOUSBUILD infectiousDto){
	  for (InfectiousRules row : infectiousDto.getRow())
      {
          for (InfectiousItem ite : row.getItem())
          {
              if ("checkMessage".equals(ite.getItemName())
                  && disease.equals(ite.getValue()))
              {
              	return true;
              }
          }
      }
      return false;
 } 
 
}
