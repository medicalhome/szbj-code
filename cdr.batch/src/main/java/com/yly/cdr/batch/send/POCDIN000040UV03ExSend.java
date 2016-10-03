package com.yly.cdr.batch.send;

/**
 * $Author :chang_xuewen
 * $Date : 2014/03/20
 * 对外应用服务
 */

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.yly.cdr.batch.exception.message.MessageExternalSendNoWarningException;
import com.yly.cdr.batch.send.db.WarningDB;
import com.yly.cdr.batch.send.others.WarningOthers;
import com.yly.cdr.core.Constants;
import com.yly.cdr.dao.WarnningRuleCheckDao;
import com.yly.cdr.entity.InfectiousContent;
import com.yly.cdr.entity.InfectiousRecord;
import com.yly.cdr.entity.WarningNotice;
import com.yly.cdr.hl7.dto.Diagnosis;
import com.yly.cdr.hl7.dto.InfectiousItem;
import com.yly.cdr.hl7.dto.InfectiousItemsforMessage;
import com.yly.cdr.hl7.dto.InfectiousMessageContent;
import com.yly.cdr.hl7.dto.InfectiousRules;
import com.yly.cdr.hl7.dto.infectiousbuild.INFECTIOUSBUILD;
import com.yly.cdr.hl7.dto.pocdin000040uv03.POCDIN000040UV03;
import com.yly.cdr.hl7.dto.warningMessage.WARNINGMESSAGE;
import com.yly.cdr.tool.BatchCallWebservice;
import com.yly.cdr.util.DateUtils;
import com.yly.cdr.util.PropertiesUtils;
import com.yly.cdr.util.StringUtils;

@Component(value = "pocdin000040uv03ExSend")
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class POCDIN000040UV03ExSend implements BusinessExSend<POCDIN000040UV03>
{
    private static final Logger logger = LoggerFactory.getLogger(POCDIN000040UV03ExSend.class);

    @Autowired
    private WarnningRuleCheckDao warnningRuleCheckDao;
    
    private static final int IS_NOT_SEND_FLAG = 0;

    private static final String RULE_CONDITION_SERVICE_ID = "BS358";
    
    private static final String IN_HOSPITAL_RECORD_SERVICE_ID = "BS335";

    private static final String CDR_SYSTEM_CODE = "S015";

    private static final String HOSPITAL_DEPT = "0000000";

    private static final String ROUTE = "InfectionRoute";
    private static final String DIAGNOSIS_ROUTE ="diagnosisRoute";

    private static final String RULE_CONDITION_SERVICE_NAME = "规则校验信息条件数据";

    private static final String RULE_GROUP = "InfectionGroup";

    private static final String MAPPING_DTO = "INFECTIOUSBUILD";

    private static final String COMPLETE_NORMAL_STATUS = "1";

    private static final String COMPLETE_EXCEPTION_STATUS = "0";

    //传染病报卡
    private static final String BUSINESS_TYPE = "01";
    //入院记录诊断
    private static final String BUSINESS_TYPE_04 = "04";

    private static final String SEND_OPEN = "0";

    private static final String DELETE_FLAG = "-1";

    private Date systemTime;

    @Autowired
    private WarningDB warningDB;

    @Autowired
    private BatchCallWebservice callWebservice;

    @Autowired
    private WarningOthers<WARNINGMESSAGE> warningOthers;

 
    
    public POCDIN000040UV03ExSend()
    {
        logger.debug("对外应用服务功能初始化。。。");
        systemTime = DateUtils.getSystemTime();
    }

    @Override
    public void sendValication(POCDIN000040UV03 pocdin000040uv03)
            throws Exception
    {
        // TODO Auto-generated method stub
        // throw new DuplicateKeyException("");
        // throw new RuntimeException("");
    }

    @Override
    public void send(POCDIN000040UV03 pocdin000040uv03) throws Exception
    {
        logger.debug("对外应用服务功能开始运行。。。");

        if (StringUtils.isEmpty(pocdin000040uv03.getOrganizationCode()))
        {
        	// $Author :yang_mingjie
        	// $Date : 2014/06/26 10:09$
        	// [BUG]0045630 MODIFY BEGIN 
            pocdin000040uv03.setOrganizationCode(Constants.HOSPITAL_CODE);
            pocdin000040uv03.setOrganizationName(Constants.HOSPITAL_NAME);
          //[BUG]0045630 MODIFY END
        }

        INFECTIOUSBUILD infectiousDto = null;

        InfectiousRecord inRecord = null;

        List<InfectiousContent> content = null;

        String mobileAddress = "";

        warningDB.setSysDate(systemTime);
        // 1.查询短信发送人员的设定号码，如果返回为空则以下方法概不执行
        // 不为空时，保存短信发送人员的手机号码待用
        List<WarningNotice> notices = getNotices(pocdin000040uv03);

        String isHistory = PropertiesUtils.getValue("sendMessage.history");

        // 获取手机号码
        Map<String, String> addressMap = null;
        if (notices != null && !notices.isEmpty())
        {
            addressMap = warningDB.getAddress(notices,
                    String.valueOf(DateUtils.getDayOfWeek(systemTime)));
            mobileAddress = addressMap.get("mobileAddress");
        }

        if (!notices.isEmpty() && !StringUtils.isEmpty(mobileAddress))
        {
            // 3.封装条件数据为XML
            INFECTIOUSBUILD infectious = null;
            //入院记录重新封装Dto
            if(IN_HOSPITAL_RECORD_SERVICE_ID.equals(pocdin000040uv03.getServiceId())){
            	infectious = getDiagnosisBuildDto(pocdin000040uv03);
            	//传染病报卡信息服务
            }else if(RULE_CONDITION_SERVICE_ID.equals(pocdin000040uv03.getServiceId())){
            	infectious = getInfectiousBuildDto(pocdin000040uv03);
            }

            String conditionXml = warningOthers.callCreateByDtoInfectious(
                    infectious, Constants.WARNING_XML_ROOT);
            logger.debug("规则条件数据生成成功，内容为：{}",conditionXml);
            // 4.调用规则接口，得到返回值
            String noticeResult = callWebservice.ruleCheck(conditionXml);
            logger.debug("规则接口调用成功，返回结果为：{}",noticeResult);
            // 5.得到返回值，生成实体类，写对应的xpath
            infectiousDto = (INFECTIOUSBUILD) warningOthers.getBaseDto(
                    noticeResult, MAPPING_DTO);

            if (!checkIfHashIll(pocdin000040uv03, infectiousDto))
            {
                logger.error("消息中不存在需要预警的信息【预警结束】");

                throw new MessageExternalSendNoWarningException("【预警结束】");
            }
            
            // 2.使用|p_lid|、|p_domain|和|业务字段|条件查询【external_send_business】表
            // 检查【is_send_message】字段是否为1，为1则已经发送，为0则继续以下步骤
            inRecord = getInfectiousRecord(warningDB.selectInfectRecord(
                    pocdin000040uv03.getPatientLid(),
                    !"46600083-8".equals(pocdin000040uv03.getOrganizationCode())?pocdin000040uv03.getPatientDomain():pocdin000040uv03.getVisitTypeCode(),
                    pocdin000040uv03.getDocumentLid(), RULE_CONDITION_SERVICE_ID.equals(pocdin000040uv03.getServiceId())?BUSINESS_TYPE:BUSINESS_TYPE_04,
                    pocdin000040uv03.getOrganizationCode()));


            //Author: yu_yzh
            //Date: 2015/5/8
            //将查询是否有未发过的传染病提前到数据库操作前，否则进行插入操作后BS335会查询到自己存在已发送
            
            //Author: yu_yzh
            //Date: 2015/5/5
            //[BUG]: 0055330 
            //查询是否有未发过的传染病
            Boolean isWarned = true;
            //疾病名用词 BS335和BS358有区别
            String code1 = !IN_HOSPITAL_RECORD_SERVICE_ID.equals(pocdin000040uv03.getServiceId()) ? "diagnosis" : "diseaseName";
            //传染病类别
            String code2 = "infectionRoute";
            String patientLid = pocdin000040uv03.getPatientLid();
            String organizationCode = pocdin000040uv03.getOrganizationCode();
            String patientDomain = !"46600083-8".equals(organizationCode) ? pocdin000040uv03.getPatientDomain() : pocdin000040uv03.getVisitTypeCode();
            String visitTimes = !"46600083-8".equals(organizationCode) ? pocdin000040uv03.getVisitTimes() : pocdin000040uv03.getVisitOrdNo();

            for (InfectiousRules row : infectiousDto.getRow())
            {            	
            	InfectiousItem infectiousRule = null;
            	InfectiousItem diseaseName = null;
            	//找出该条数据中的传染病名称和传染病类别对应的项
            	for (InfectiousItem ite : row.getItem()) {
            		if(ite.getItemName().equals(code1)){
            			diseaseName = ite;
            			if(infectiousRule != null)break;
            		} else if(ite.getItemName().equals(code2)){
            			infectiousRule = ite;
            			if(diseaseName != null) break;
            		}
            	}
            	//若疾病名项不存在或为空，该条数据不合条件跳过
            	if(diseaseName == null || StringUtils.isEmpty(diseaseName.getValue()))continue;
            	//若传染病类别不存在或为空，跳过
            	else if(infectiousRule == null || StringUtils.isEmpty(infectiousRule.getValue()))continue;
            	//否则查询数据库中该传染病是否存在
            	else {
            		isWarned = warningDB.isExist(patientLid,
            				patientDomain,
            				organizationCode,
            				visitTimes,
            				diseaseName.getValue());
            	}
            	if(!isWarned) {
            		break;
            	}
            }
            //[BUG]: 0055330
            
   
			// 新增之前，判断该条检验项目对应的传染病在指定时间范围内是否已经发送过
			int isHaveWarnflag = 0;
			if (isWarnDisease(pocdin000040uv03.getServiceId(),Constants.WARN_RULE_DISEASE, infectiousDto)) {
				isHaveWarnflag = warnningRuleCheckDao.selectIsHaveWarned

				(pocdin000040uv03.getPatientLid(),
						Constants.getWarnRuleDisease(), Integer.parseInt

						(Constants.getWarnRuleDay()),
						pocdin000040uv03.getWriteTime());
			}
            if ((inRecord == null || inRecord.getBusinessSn() == null)
                && SEND_OPEN.equals(pocdin000040uv03.getVersionNumber()))
            {
                List<InfectiousMessageContent> messageContents = pocdin000040uv03.getInContentList();

                if(isHaveWarnflag==0){
                	InfectiousRecord record = new InfectiousRecord();
                	
                	List<InfectiousContent> inContentList = new ArrayList<InfectiousContent>();
                	logger.debug("消息标识为新增业务并且DB中查不到消息都应数据，开始新增消息对应的数据【Insert操作】");
                	// 新增记录
                	warningDB.insertInfectiousRecord(
                			record,
                			inContentList,
                			messageContents,
                			DateUtils.stringToDate(pocdin000040uv03.getWriteTime()),
                			pocdin000040uv03, infectiousDto);
                }
            }
            else if (DELETE_FLAG.equals(pocdin000040uv03.getVersionNumber()))
            {
                logger.debug("消息标识为删除业务，开始删除消息对应DB的数据【Delete操作】");
                inRecord = warningDB.selectInfectRecord(
                        pocdin000040uv03.getPatientLid(),
                        !"46600083-8".equals(pocdin000040uv03.getOrganizationCode())?pocdin000040uv03.getPatientDomain():pocdin000040uv03.getVisitTypeCode(),
                        pocdin000040uv03.getDocumentLid(), RULE_CONDITION_SERVICE_ID.equals(pocdin000040uv03.getServiceId())?BUSINESS_TYPE:BUSINESS_TYPE_04,
                        pocdin000040uv03.getOrganizationCode());
                if (inRecord == null)
                {
                    logger.error("需要删除的对应消息中的业务数据在DB中不存在！删除失败！：documentLid:"
                        + pocdin000040uv03.getDocumentLid() + "，orgCode:"
                        + pocdin000040uv03.getOrganizationCode());
                }
                else
                {
                    content = warningDB.selectContentRecord(inRecord);
                    warningDB.deleteInfectious(inRecord, content);
                    logger.debug("【Delete操作】结束!");
                }
                return;
            }
            else
            {
                // 隔离事务重新组装一次数据库对象
                inRecord = warningDB.selectInfectRecord(
                        pocdin000040uv03.getPatientLid(),
                        !"46600083-8".equals(pocdin000040uv03.getOrganizationCode())?pocdin000040uv03.getPatientDomain():pocdin000040uv03.getVisitTypeCode(),
                        pocdin000040uv03.getDocumentLid(), RULE_CONDITION_SERVICE_ID.equals(pocdin000040uv03.getServiceId())?BUSINESS_TYPE:BUSINESS_TYPE_04,
                        pocdin000040uv03.getOrganizationCode());
                content = warningDB.selectContentRecord(inRecord);
                logger.debug("消息标识为更新业务，开始更新消息对应DB的数据【Update操作】");
                if (inRecord != null && content != null)
                {
                    warningDB.updateInfectiousRecord(
                            inRecord,
                            content,
                            pocdin000040uv03,
                            infectiousDto,
                            DateUtils.stringToDate(pocdin000040uv03.getWriteTime()));
                }
                else
                {
                    logger.debug("【Update操作】失败！DB中不存在消息对应的数据！");
                }
            }
         
//            Boolean b = false;
//            for (InfectiousRules row : infectiousDto.getRow())
//            {
//                for (InfectiousItem ite : row.getItem())
//                {
//                    if (!IN_HOSPITAL_RECORD_SERVICE_ID.equals(pocdin000040uv03.getServiceId())?"diagnosis".equals(ite.getItemName()):"diseaseName".equals(ite.getItemName())
//                        && !StringUtils.isEmpty(ite.getValue()))
//                    {
//                        b = warningDB.isExist(pocdin000040uv03.getPatientLid(),
//                        		!"46600083-8".equals(pocdin000040uv03.getOrganizationCode())?pocdin000040uv03.getPatientDomain():pocdin000040uv03.getVisitTypeCode(),
//                                pocdin000040uv03.getOrganizationCode(),
//                                !"46600083-8".equals(pocdin000040uv03.getOrganizationCode())?pocdin000040uv03.getVisitTimes():pocdin000040uv03.getVisitOrdNo(),
//                                ite.getValue());
//                    }
//                }
//            }
            
            
            if (!isWarned)
            {
                if (SEND_OPEN.equals(isHistory)
                    && SEND_OPEN.equals(pocdin000040uv03.getVersionNumber()))
                {
                    logger.debug("短信发送开关验证【成功】，准备调用短信发送接口。。。。");
                    // 隔离事务重新组装一次数据库对象
                    inRecord = warningDB.selectInfectRecord(
                            pocdin000040uv03.getPatientLid(),
                            !"46600083-8".equals(pocdin000040uv03.getOrganizationCode())?pocdin000040uv03.getPatientDomain():pocdin000040uv03.getVisitTypeCode(),
                            pocdin000040uv03.getDocumentLid(), RULE_CONDITION_SERVICE_ID.equals(pocdin000040uv03.getServiceId())?BUSINESS_TYPE:BUSINESS_TYPE_04,
                            pocdin000040uv03.getOrganizationCode());
                    content = warningDB.selectContentRecord(inRecord);
                    
                    
                    // 6.将步骤【1】中查询的短信发送人员号码与医生号、送检科室等信息进行封装成短信发送的内容
                    // 7.调用短信发送接口，发送短信
                    String isUncommonUpdateOrException = sendMobile(
                            infectiousDto, notices, inRecord, addressMap,
                            pocdin000040uv03);

                    // 8.更新步骤【2】中的【is_send_message】字段值
                    String flag = null;
                    if (COMPLETE_NORMAL_STATUS.equals(isUncommonUpdateOrException))
                    {
                        flag = "1";
                        warningDB.updateRest(content, flag, "2");
                        logger.debug("将该检验报告传染病预警运行情况存入DB，success。");
                    }
                    else
                    {
                        flag = "0";
                        logger.debug("短信未发送。");
                    }
                }
            }
            else
            {
                logger.debug("该患者该次就诊已存在相同传染病，所以不进行发送。");
            }
        }
        else
        {
            if (notices.isEmpty())
            {
                logger.debug("notice为空。。。。。。。。。。。。。。。。。。。");
            }
            if (StringUtils.isEmpty(mobileAddress))
            {
                logger.debug("联系人号码为空。。。。。。。。。。。。。。。。。");
            }
        }

    }

    private List<WarningNotice> getNotices(POCDIN000040UV03 pocdin000040uv03)
    {
        // 数据表中查找发送人的信息
        List<WarningNotice> noticeValidation = warningDB.selectInfectious(
                RULE_GROUP, pocdin000040uv03.getOrganizationCode(), null);
        return noticeValidation;
    }

	//入院记录规则Dto数据封装
    public INFECTIOUSBUILD getDiagnosisBuildDto(POCDIN000040UV03 pocdin000040uv03){
    	INFECTIOUSBUILD in = new INFECTIOUSBUILD();
    	in.setMsgId(IN_HOSPITAL_RECORD_SERVICE_ID);

        in.setMsgName(RULE_CONDITION_SERVICE_NAME);

        in.setSourceSysCode(CDR_SYSTEM_CODE);

        in.setTargetSysCode("");

        in.setCreateTime(DateUtils.dateToString(systemTime,
                DateUtils.PATTERN_COMPACT_DATETIME));

        in.setServiceId(warningOthers.getServiceId(pocdin000040uv03.getServiceId()));

        in.setVisitDept(HOSPITAL_DEPT);

        in.setHospitalCode(pocdin000040uv03.getOrganizationCode());

        in.setHospitalName(pocdin000040uv03.getOrganizationName());

        in.setRoleGroupType(RULE_GROUP);

        in.setReportDate(DateUtils.stringToDate(pocdin000040uv03.getWriteTime()));

        in.setSendDate(systemTime);

        List<Diagnosis> rulesInMessage = pocdin000040uv03.getInDiagnosis();
        
        List<InfectiousRules> retrules = new ArrayList<InfectiousRules>();
        
        List<InfectiousItem> itemList = null;
        
        InfectiousRules ir = null;
        InfectiousItem item = null;
        int count = 0;
        for (Diagnosis lr : rulesInMessage)
        {

        	itemList = new ArrayList<InfectiousItem>();
        	count++;
        	item = new InfectiousItem();
            item.setItemName("diagnosisType");
            item.setValue(lr.getDiagnosisType());
            itemList.add(item);
            item = new InfectiousItem();
            item.setItemName("diagnosisTypeName");
            item.setValue(lr.getDiagnosisTypeName());
            itemList.add(item);
            item = new InfectiousItem();
            item.setItemName("diseaseCode");
            item.setValue(lr.getDiseaseCode());
            itemList.add(item);
            item = new InfectiousItem();
            item.setItemName("diseaseName");
            item.setValue(lr.getDiseaseName());
            itemList.add(item);

            ir = new InfectiousRules();

            ir.setRowKey(count + "");

            ir.setFactType(DIAGNOSIS_ROUTE);

            ir.setItem(itemList);

            retrules.add(ir);

        }
        in.setRow(retrules);
    	return in;
    }
    //传染病报卡规则Dto数据封装
    public INFECTIOUSBUILD getInfectiousBuildDto(
            POCDIN000040UV03 pocdin000040uv03)
    {
        INFECTIOUSBUILD in = new INFECTIOUSBUILD();

        in.setMsgId(RULE_CONDITION_SERVICE_ID);

        in.setMsgName(RULE_CONDITION_SERVICE_NAME);

        in.setSourceSysCode(CDR_SYSTEM_CODE);

        in.setTargetSysCode("");

        in.setCreateTime(DateUtils.dateToString(systemTime,
                DateUtils.PATTERN_COMPACT_DATETIME));

        in.setServiceId(warningOthers.getServiceId(pocdin000040uv03.getServiceId()));

        in.setVisitDept(HOSPITAL_DEPT);

        in.setHospitalCode(pocdin000040uv03.getOrganizationCode());

        in.setHospitalName(pocdin000040uv03.getOrganizationName());

        in.setRoleGroupType(RULE_GROUP);

        in.setReportDate(DateUtils.stringToDate(pocdin000040uv03.getWriteTime()));

        in.setSendDate(systemTime);

        List<InfectiousMessageContent> rulesInMessage = pocdin000040uv03.getInContentList();

        List<InfectiousRules> retrules = new ArrayList<InfectiousRules>();
        int count = 0;
        for (InfectiousMessageContent lr : rulesInMessage)
        {
            for (InfectiousItemsforMessage itemm : lr.getItems())
            {
                List<InfectiousItem> itemList = new ArrayList<InfectiousItem>();

                InfectiousItem item = new InfectiousItem();

                count++;
                item.setItemName("diagnosis");

                item.setValue(itemm.getValue());

                itemList.add(item);

                InfectiousRules ir = new InfectiousRules();

                ir.setRowKey(count + "");

                ir.setFactType(ROUTE);

                ir.setItem(itemList);

                retrules.add(ir);
            }

        }
        in.setRow(retrules);
        return in;
    }

    private String sendMobile(INFECTIOUSBUILD resultDto,
            List<WarningNotice> notices, InfectiousRecord inRecord,
            Map<String, String> addressMap, POCDIN000040UV03 pocdin000040uv03)
    {
        String mobileStatus = null;

        List<List<String>> sendMobileMessageContent = null;

        String mobileAddress = addressMap.get("mobileAddress");

        if (!StringUtils.isEmpty(mobileAddress))
        {

            // 获取短信发送内容
            sendMobileMessageContent = getMobileMessageContent(resultDto,
                    pocdin000040uv03);
            List<String> sendContent = sendMobileMessageContent.get(0);

            if (sendMobileMessageContent.size() != 0 && sendContent.size() == 0)
            {
                logger.debug("疾病内容不为空，但不是【空气传播疾病,飞沫传播疾病】类型，因此不发送短信！。。。。。");
            }

            if (sendContent != null && sendContent.size() != 0)
            {
                try
                {
                    for (String message : sendContent)
                    {
                        // 发送短信
//                        int back = callWebservice.sendMessageNo(mobileAddress,
//                                message);
//                        if (back == 1)
//                        {
//                            mobileStatus = COMPLETE_NORMAL_STATUS;
//                            logger.debug("发送ok");
//                        }
//                        else if (back != 1)
//                        {
//                            logger.debug("短信发送失败！发挥状态为：" + back);
//                        }
                        
                        //Author: yu_yzh
                        //Date: 2015/5/6
                        //江苏短信接口改造 CODE BEGIN
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
                        //江苏短信接口改造 CODE END
                    }

                }
                catch (Exception ex)
                {
                    mobileStatus = COMPLETE_EXCEPTION_STATUS;

                    ex.printStackTrace();
                }

            }
        }
        else
        {
            logger.debug("发送短信地址为空。。。");
        }

        return mobileStatus;
    }

    private List<List<String>> getMobileMessageContent(INFECTIOUSBUILD ib,
            POCDIN000040UV03 pocdin000040uv03)
    {
        String[] typeList = { "空气传播疾病", "飞沫传播疾病", "接触传播疾病", "血液（或性）传播疾病",
                "虫媒或动物传播疾病" };
        String[] messageContents = {
            	"该疾病主要通过空气传播，请执行感控措施：患者单间隔离，有条件时隔离于负压病房；悬挂黄色标识；患者病容容许时，戴外科口罩，限制其活动范围；做好标准预防；医务人员佩戴帽子和医用防护口罩；诊疗操作可能喷溅时，戴护目镜或防护面罩，穿防护服；接触患者及其血液、体液、分泌物、排泄物等物质时戴手套并严格执行手卫生；病室严格空气消毒。【感染管理办公室】电话：6917/5220",
            	"该疾病主要通过飞沫传播，请执行感控措施：患者单间隔离；悬挂粉色标识；患者病容容许时，戴外科口罩，限制其活动范围；患者之间、患者与探视者相隔距离1m以上，探视者戴外科口罩；做好标准预防；与患者近距离（1m以内）接触，医务人员佩戴帽子和医用防护口罩；诊疗操作可能喷溅时，戴护目镜或防护面罩，穿防护服；接触患者及其血液、体液、分泌物、排泄物等物质时戴手套并严格执行手卫生；病室加强通风，或进行空气消毒。【感染管理办公室】电话：6917/5220",
            	"该疾病主要通过接触传播，请执行感控措施：患者单间或床边隔离；悬挂蓝色标识；接触患者血液、体液、分泌物、排泄物等物质时戴手套，手上有伤口时应戴双层手套；离开病室前，接触污染物品后应摘除手套，洗手或卫生手消毒；从事可能污染工作服的操作时，应穿隔离衣；接触甲类传染病应穿防护服。【感染管理办公室】电话：6917/5220",
            	"HBV、HCV通过接触血液、体液传播，HIV、梅毒通过性接触或接触患者血液、体液传播，淋病通过性接触或接触患者分泌物污染的物品而传播，请执行感控措施：医务人员知情，防止锐器伤；在标准预防的基础上，接触患者及其血液、体液、分泌物、排泄物等物质时戴手套，手上有伤口时应戴双层手套；从事可能污染工作服的操作时，应穿隔离衣。【感染管理办公室】电话：6917/5220",
            	"该疾病主要通过虫媒或动物传播（患者不是传染源），请执行感控措施：病区防虫驱虫；严格执行防护措施；医疗废物双层包装；终末消毒。【感染管理办公室】电话：6917/5220"
        };
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
//        if (!StringUtils.isEmpty(pocdin000040uv03.getWardNo()))
//        {
//            wardNo = pocdin000040uv03.getWardNo();
//        }
//        if (!StringUtils.isEmpty(pocdin000040uv03.getBedNo()))
//        {
//            bedNo = pocdin000040uv03.getBedNo();
//        }

        //BS335根BS358使用匹配字段不同
        String code = null;
        if(pocdin000040uv03.getServiceId().equals(IN_HOSPITAL_RECORD_SERVICE_ID)){
        	//335
        	wardNo = (pocdin000040uv03.getWardNoBS335() == null) ? wardNo : pocdin000040uv03.getWardNoBS335() ;
        	bedNo = (pocdin000040uv03.getBedNoBS335() == null) ? bedNo : pocdin000040uv03.getBedNoBS335();
        	code = "diseaseName";
        } else if(pocdin000040uv03.getServiceId().equals(RULE_CONDITION_SERVICE_ID)){
        	//358
        	wardNo = (pocdin000040uv03.getWardNo() == null) ? wardNo : pocdin000040uv03.getWardNo();
        	bedNo = (pocdin000040uv03.getBedNo() == null) ? bedNo : pocdin000040uv03.getBedNo();
        	code = "diagnosis";
        }
        //

        commenStr = "您好," + wardNo + bedNo + "床  患者"
            + pocdin000040uv03.getPatientName() + "，为【 ";
        commenStr = commenStr.replace("床床", "床");
        String medicalType = "";
        if (!StringUtils.isEmpty(pocdin000040uv03.getMedicalTypeName()))
        {
            medicalType = pocdin000040uv03.getMedicalTypeName();
        }

        for (InfectiousRules rules : ib.getRow())
        {
            List<InfectiousItem> items = rules.getItem();
            String value = "";
            for (InfectiousItem item : items)
            {

                String type = item.getItemName();
                // 如果是值就存储在value中，是类型就存在类型中
                
                if(type.equals(code)) {
                    value = item.getValue();	
                }                
//                if ("diagnosis".equals(type))
//                {
//                    value = item.getValue();
//                }                
                else if ("infectionRoute".equals(type))
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
//                String temp = values[i].substring(0, values[i].length() - 1);
//                values[i].delete(0, values[i].length());
//                values[i].append(temp);
            	//生成短信内容
                values[i].delete(values[i].length() - 1, values[i].length());
                //BS335 BS358短信模板差异
                if(pocdin000040uv03.getServiceId().equals(IN_HOSPITAL_RECORD_SERVICE_ID)){
                	values[i].append("】，");
                } else if(pocdin000040uv03.getServiceId().equals(RULE_CONDITION_SERVICE_ID)){
                	values[i].append("】(" + medicalType + ")，");
                }
                //简化代码
                values[i].append(messageContents[i]);
                String res = values[i].insert(0, commenStr).toString();
                result.add(res);
                if(i == 0 || i == 1){
                	sendList.add(res);
                }
//                if (i == 0)
//                {
//                    values[0].append("】("
//                        + medicalType
//                        + ")，该疾病主要通过空气传播，请执行感控措施：患者单间隔离，有条件时隔离于负压病房；悬挂黄色标识；患者病容容许时，戴外科口罩，限制其活动范围；做好标准预防；医务人员佩戴帽子和医用防护口罩；诊疗操作可能喷溅时，戴护目镜或防护面罩，穿防护服；接触患者及其血液、体液、分泌物、排泄物等物质时戴手套并严格执行手卫生；病室严格空气消毒。【感染管理办公室】电话：6917/5220");
//                    result.add(commenStr + values[0].toString());
//                    sendList.add(commenStr + values[0].toString());
//                }
//                else if (i == 1)
//                {
//                    values[1].append("】("
//                        + medicalType
//                        + ")，该疾病主要通过飞沫传播，请执行感控措施：患者单间隔离；悬挂粉色标识；患者病容容许时，戴外科口罩，限制其活动范围；患者之间、患者与探视者相隔距离1m以上，探视者戴外科口罩；做好标准预防；与患者近距离（1m以内）接触，医务人员佩戴帽子和医用防护口罩；诊疗操作可能喷溅时，戴护目镜或防护面罩，穿防护服；接触患者及其血液、体液、分泌物、排泄物等物质时戴手套并严格执行手卫生；病室加强通风，或进行空气消毒。【感染管理办公室】电话：6917/5220");
//                    result.add(commenStr + values[1].toString());
//                    sendList.add(commenStr + values[1].toString());
//                }
//                else if (i == 2)
//                {
//                    values[2].append("】("
//                        + medicalType
//                        + ")，该疾病主要通过接触传播，请执行感控措施：患者单间或床边隔离；悬挂蓝色标识；接触患者血液、体液、分泌物、排泄物等物质时戴手套，手上有伤口时应戴双层手套；离开病室前，接触污染物品后应摘除手套，洗手或卫生手消毒；从事可能污染工作服的操作时，应穿隔离衣；接触甲类传染病应穿防护服。【感染管理办公室】电话：6917/5220");
//                    result.add(commenStr + values[2].toString());
//                }
//                else if (i == 3)
//                {
//                    values[3].append("】("
//                        + medicalType
//                        + ")，HBV、HCV通过接触血液、体液传播，HIV、梅毒通过性接触或接触患者血液、体液传播，淋病通过性接触或接触患者分泌物污染的物品而传播，请执行感控措施：医务人员知情，防止锐器伤；在标准预防的基础上，接触患者及其血液、体液、分泌物、排泄物等物质时戴手套，手上有伤口时应戴双层手套；从事可能污染工作服的操作时，应穿隔离衣。【感染管理办公室】电话：6917/5220");
//                    result.add(commenStr + values[3].toString());
//                }
//                else if (i == 4)
//                {
//                    values[4].append("】("
//                        + medicalType
//                        + ")，该疾病主要通过虫媒或动物传播（患者不是传染源），请执行感控措施：病区防虫驱虫；严格执行防护措施；医疗废物双层包装；终末消毒。【感染管理办公室】电话：6917/5220");
//                    result.add(commenStr + values[4].toString());
//                }
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

    private boolean isWarnDisease(String serviceId,String disease,INFECTIOUSBUILD infectiousDto){
    	  for (InfectiousRules row : infectiousDto.getRow())
          {
              for (InfectiousItem ite : row.getItem())
              {
                  if(IN_HOSPITAL_RECORD_SERVICE_ID.equals(serviceId)){
                	  if ("diseaseName".equals(ite.getItemName())
                			  && disease.equals(ite.getValue()))
                	  {
                		  return true;
                	  }
                  }
                  else{
                	  if ("diagnosis".equals(ite.getItemName())
                			  && disease.equals(ite.getValue()))
                	  {
                		  return true;
                	  }
                  }
              }
          }
          return false;
     }
    
    private boolean checkIfHashIll(POCDIN000040UV03 pocdin000040uv03,INFECTIOUSBUILD dto){
    	boolean flag = false;
    	if(IN_HOSPITAL_RECORD_SERVICE_ID.equals(pocdin000040uv03.getServiceId())){
    		for(InfectiousRules row :dto.getRow()){
    			for (InfectiousItem ite : row.getItem()){
                    if ("infectionRoute".equals(ite.getItemName())
                        && !StringUtils.isEmpty(ite.getValue()))
                    {
                        flag = true;
                    }
                }
    		}
    	}else{
    		flag = true;
    	}
    	return flag;
    }
}
