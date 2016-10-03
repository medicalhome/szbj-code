package com.yly.cdr.web.mq.process.audit;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.yly.cdr.core.Constants;
import com.yly.cdr.dto.AuditLogDto;
import com.yly.cdr.entity.PatientCrossIndex;
import com.yly.cdr.entity.SystemAuditLog;
import com.yly.cdr.service.CommonService;
import com.yly.cdr.util.DateUtils;
import com.yly.cdr.util.XmlUtils;
import com.yly.cdr.web.mq.hl7.dto.BodyContent;
import com.yly.cdr.web.mq.hl7.dto.Content;
import com.yly.cdr.web.mq.hl7.dto.HeadContent;
import com.yly.cdr.web.mq.hl7.dto.Item;
import com.yly.cdr.web.mq.hl7.dto.RowOperation;
import com.yly.cdr.web.mq.hl7.dto.bs907.BS907;
import com.yly.cdr.wmq.Hl7MessageWmq;
import com.yly.cdr.wmq.WMQMessageSender;

/**
 * 2013.09.26 
 * @author jin_peng
 */
public class AuditMessageHelper
{
    private static final Logger logger = LoggerFactory.getLogger(AuditMessageHelper.class);

    private static final String MESSAGE_SERVICE_ID = "BS907";

    private static final String MESSAGE_SERVICE_NANME = "审计信息更新服务";

    private static final String CDR_SYSTEM_CODE = "S015";

    private static final String TARGET_SYSTEM_CODE = "";

    private static final String AUDIT_EVENT = "004";

    private static final String AUDIT_SYSTEM_ID = "S015";

    private static final String EMPTY_STRING = "";

    private static final String BS907_ROOT_NAME = "msg";

    private static final String IS_SEND_SUCCESS = "1";

    private static final String IS_NOT_SEND_SUCCESS = "0";

    private String auditQueueName;

    private Date systemTime;

    @Autowired
    private WMQMessageSender sender;

    @Autowired
    private CommonService commonService;

    public AuditMessageHelper()
    {
        systemTime = DateUtils.getSystemTime();
    }

    /**
     * 获取消息内容
     * @param pci 患者交叉索引信息
     * @param auditLogDto 敏感信息审计信息
     * @return 构建消息内容dto对象
     */
    private BS907 buildMessageBs907(PatientCrossIndex pci,
            AuditLogDto auditLogDto)
    {

        BS907 bs907 = new BS907();

        bs907.setHead(setupHeadContent(pci, auditLogDto));
        bs907.setBody(setupBodyContent(pci, auditLogDto));

        return bs907;
    }

    private List<HeadContent> setupHeadContent(PatientCrossIndex pci,
            AuditLogDto auditLogDto)
    {
        List<HeadContent> headList = new ArrayList<HeadContent>();
        HeadContent head = new HeadContent();

        head.setMsgId(MESSAGE_SERVICE_ID);
        head.setMsgName(MESSAGE_SERVICE_NANME);
        head.setSourceSysCode(CDR_SYSTEM_CODE);
        head.setTargetSysCode(TARGET_SYSTEM_CODE);
        head.setCreateTime(DateUtils.dateToString(systemTime,
                DateUtils.PATTERN_COMPACT_DATETIME));

        headList.add(head);

        return headList;
    }

    private List<BodyContent> setupBodyContent(PatientCrossIndex pci,
            AuditLogDto auditLogDto)
    {
        List<BodyContent> bodyList = new ArrayList<BodyContent>();
        BodyContent body = new BodyContent();

        body.setOperDateTime(DateUtils.dateToString(
                auditLogDto.getOperationTime(),
                DateUtils.PATTERN_COMPACT_DATETIME));
        body.setOperUserId(auditLogDto.getUserId());
        body.setOperUnitId(auditLogDto.getDeptCode());
        body.setHospitalCode(auditLogDto.getOrgCode());
        body.setHospitalName(auditLogDto.getOrgName());
        body.setAuditEvent(AUDIT_EVENT);
        body.setAuditSystemId(AUDIT_SYSTEM_ID);
        body.setContent(setupContent(pci, auditLogDto));
        body.setComputerName(auditLogDto.getClientComputerName());
        body.setIpAddress(auditLogDto.getClientIp());
        body.setField1(EMPTY_STRING);
        body.setField2(EMPTY_STRING);
        body.setField3(EMPTY_STRING);

        bodyList.add(body);

        return bodyList;
    }

    private List<Content> setupContent(PatientCrossIndex pci,
            AuditLogDto auditLogDto)
    {
        List<Item> itemList = new ArrayList<Item>();

        List<String> conList = getItemSource(pci, auditLogDto);

        for (String con : conList)
        {
            String[] sources = con.split(";");
            Item item = new Item();

            item.setItemName(sources[0]);
            item.setOldValue(sources[1]);

            itemList.add(item);
        }

        List<RowOperation> rowList = new ArrayList<RowOperation>();
        RowOperation row = new RowOperation();

        row.setItem(itemList);
        rowList.add(row);

        List<Content> contentList = new ArrayList<Content>();
        Content content = new Content();

        content.setRow(rowList);
        contentList.add(content);

        return contentList;
    }

    private List<String> getItemSource(PatientCrossIndex pci,
            AuditLogDto auditLogDto)
    {
        List<String> conList = new ArrayList<String>();

        // 员工编号
        conList.add("员工编号;" + auditLogDto.getUserId());
        // 患者ID
        conList.add("患者ID;" + auditLogDto.getPatientLid());
        // 患者域
        conList.add("患者域;" + auditLogDto.getPatientDomain());
        // 门诊卡号
        conList.add("门诊卡号;" + pci.getOutpatientNo());
        // 住院号
        conList.add("住院号;" + pci.getInpatientNo());
        // 临床数据分类
        conList.add("临床数据分类;" + auditLogDto.getDataTypeName());
        // 临床数据ID
        conList.add("临床数据ID;" + auditLogDto.getBusinessDataNo());
        // 检验项目名称
        conList.add("检验项目名称;" + auditLogDto.getItemName());

        return conList;
    }

    /**
     * 获取消息内容xml
     * @param bs907 构建消息内容dto对象
     * @return 消息内容
     * @throws Exception
     */
    private String buildMessageBs907Send(BS907 bs907) throws Exception
    {
        try
        {
            return XmlUtils.callCreateCommonByDto(bs907, BS907_ROOT_NAME);
        }
        catch (Exception e)
        {
            logger.error("构造审计发送消息失败");

            e.printStackTrace();

            throw new Exception("构造审计发送消息失败", e);
        }
    }

    private Hl7MessageWmq getAuditHl7Message(AuditLogDto auditLogDto,
            String messageContent)
    {
        Hl7MessageWmq hl7message = new Hl7MessageWmq();

        hl7message.setServiceId(MESSAGE_SERVICE_ID);

        hl7message.setHospitalCd(auditLogDto.getOrgCode());

        hl7message.setSourceSysCd(CDR_SYSTEM_CODE);

        hl7message.setDomainId(Constants.MSG_DOMAIN_ID);

        hl7message.setExecUnitId(Constants.MSG_EXEC_UNIT_ID);

        hl7message.setOrderDispatchType(Constants.MSG_ORDER_DISPATCH_TYPE);

        hl7message.setQueueName(auditQueueName);

        hl7message.setContent(messageContent);

        return hl7message;
    }

    private SystemAuditLog getSystemAuditLog(AuditLogDto auditLogDto,
            String messageContent, String isSuccess)
    {
        SystemAuditLog systemAuditLog = null;

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("auditLogSn", auditLogDto.getAuditLogSn());
        map.put("deleteFlag", Constants.NO_DELETE_FLAG);

        List<Object> systemAuditLogList = commonService.selectByCondition(
                SystemAuditLog.class, map);

        if (systemAuditLogList != null && !systemAuditLogList.isEmpty())
        {
            systemAuditLog = (SystemAuditLog) systemAuditLogList.get(0);

            systemAuditLog.setMessageContent(messageContent);

            systemAuditLog.setSuccessSendFlag(isSuccess);

            systemAuditLog.setUpdateTime(systemTime);
        }

        return systemAuditLog;
    }

    /**
     * 敏感信息审计服务发送消息
     * @param pci 患者交叉索引信息
     * @param auditLogDto 敏感信息审计信息
     * @throws Exception
     */
    public void mainBusinessProcess(PatientCrossIndex pci,
            AuditLogDto auditLogDto) throws Exception
    {
        String isSendSuccess = IS_NOT_SEND_SUCCESS;

        String messageContent = null;

        try
        {
            BS907 bs907 = buildMessageBs907(pci, auditLogDto);

            messageContent = buildMessageBs907Send(bs907);

            Hl7MessageWmq hl7message = getAuditHl7Message(auditLogDto,
                    messageContent);

            sender.sendMessageAuditTo(hl7message, auditQueueName);

            isSendSuccess = IS_SEND_SUCCESS;

            logger.debug("敏感信息审计消息发送成功。");
        }
        catch (Exception ex)
        {
            logger.error("敏感信息审计发送消息失败。");

            ex.printStackTrace();
        }

        try
        {
            SystemAuditLog systemAuditLog = getSystemAuditLog(auditLogDto,
                    messageContent, isSendSuccess);

            commonService.updateAuditLog(systemAuditLog);

            logger.debug("敏感信息审计消息内容及是否发送成功标识保存数据库成功。");
        }
        catch (Exception ex)
        {
            logger.error("敏感信息审计发送消息内容及是否发送成功标识保存数据库失败。");

            ex.printStackTrace();
        }

        if (IS_NOT_SEND_SUCCESS.equals(isSendSuccess))
        {
            throw new Exception("敏感信息审计发送消息失败。");
        }
    }

    public String getAuditQueueName()
    {
        return auditQueueName;
    }

    public void setAuditQueueName(String auditQueueName)
    {
        this.auditQueueName = auditQueueName;
    }

}
