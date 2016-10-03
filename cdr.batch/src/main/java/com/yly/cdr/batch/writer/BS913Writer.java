package com.yly.cdr.batch.writer;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.yly.cdr.core.Constants;
import com.yly.cdr.entity.CodeWard;
import com.yly.cdr.entity.WarningNotice;
import com.yly.cdr.entity.WarningNoticeDetail;
import com.yly.cdr.hl7.dto.Notification;
import com.yly.cdr.hl7.dto.WarningDetail;
import com.yly.cdr.hl7.dto.bs913.BS913;
import com.yly.cdr.service.CommonService;
import com.yly.cdr.service.WarningNoticeService;
import com.yly.cdr.util.DateUtils;
import com.yly.cdr.util.StringUtils;

@Component(value = "bs913Writer")
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class BS913Writer implements BusinessWriter<BS913>
{
    private Logger logger = LoggerFactory.getLogger(BS913Writer.class);

    private Logger loggerBS913 = LoggerFactory.getLogger("BS913");

    // 消息是否有节点的标识
    private Map<String,String> noticeLength = new HashMap<String,String>();
    
    // 通知时段
    private List<String> noticeTime = new ArrayList<String>();

    // 当前时间
    private Date syatemTime = DateUtils.getSystemTime();

    @Autowired
    private CommonService commonService;

    @Autowired
    private WarningNoticeService warningNoticeService;

    // 要新增的WarningNoticeDTO集合
    private List<Notification> insertWarnigDtoList = new ArrayList<Notification>();

    // 要删除的WarningNotice实体集合
    private List<WarningNotice> deleteWarningList = new ArrayList<WarningNotice>();

    // 要删除的SystemQueueName实体集合
    private List<List<WarningNoticeDetail>> deletetWarnNoticeDetaiList = new ArrayList<List<WarningNoticeDetail>>();

    // 要新增的WarningNotice实体集合
    private List<WarningNotice> insertWarnigList = new ArrayList<WarningNotice>();

    // 要新增的SystemQueueName实体集合
    private List<WarningNoticeDetail> insertWarnNoticeDetailList = new ArrayList<WarningNoticeDetail>();

    @Override
    public boolean validate(BS913 t)
    {
        List<WarningDetail> warningDetailList ;
        
        for (Notification notification : t.getNotifications())
        {
            // Author :jia_yanqing
            // Date : 2013/1/22 15:00
            // [BUG]0042085 MODIFY BEGIN
            /*if(!notification.getHospitalCode().equals(t.getMessage().getOrgCode())){
            	logger.error("消息体中的医疗机构编码和消息头中的医疗机构编码不同");
                
                loggerBS913.error("Message:{}, validate:{}",
                		notification.toString(), "消息体中的医疗机构编码和消息头中的医疗机构编码不同,消息体中的医疗机构编码："
                            + notification.getHospitalCode()+"消息头中的医疗机构编码："+t.getMessage().getOrgCode());
                return false;
            }*/
            // [BUG]0042085 MODIFY END
            if(notification.getHospitalCode() == null || "".equals(notification.getHospitalCode()))
            {
                notification.setHospitalCode(Constants.HOSPITAL_CODE);
                notification.setHospitalName(Constants.HOSPITAL_NAME);
            }
        	warningDetailList = notification.getMethod();
            
            for(WarningDetail warningDetail:warningDetailList)
            {
                // 通知方式是电话不为空
                if (!StringUtils.isEmpty(warningDetail.getTelephone()))
                {
                    // 通知时段不能为空
                    if (StringUtils.isEmpty(warningDetail.getWeekdays()))
                    {
                        logger.error("通知方式是电话时，通知时段不能为空！");
                        
                        loggerBS913.error("Message:{}, validate:{}",
                                warningDetail.toString(), "通知方式是电话时，通知时段不能为空！"
                                    + warningDetail.getWeekdays());
                        return false;
                    }
                    noticeTime.add(warningDetail.getWeekdays());

                    noticeLength.put(warningDetail.getTelephone(), "1");                
                }
                // 通知方式邮件不为空
                if (!StringUtils.isEmpty(warningDetail.getEmail()))
                {
                    noticeLength.put(warningDetail.getEmail(), "2");                
                }
                // 如果通知方式电话和邮件都是空
                if(StringUtils.isNullAll(warningDetail.getTelephone(),warningDetail.getEmail()))
                {
                    // 系统ID不能为空
                    if (warningDetail.getSystemId() == null || warningDetail.getSystemId().isEmpty())
                    {
                        logger.error("通知方式电话和邮件都为空的情况下，系统ID不能为空！");
                        
                        loggerBS913.error("Message:{}, validate:{}",
                                warningDetail.toString(), "当通知方式电话和邮件都为空时，系统ID不能为空！"
                                    + warningDetail.getSystemId());
                        
                        return false;
                    }
                }
                // 添加系统ID
                if (warningDetail.getSystemId() != null && !warningDetail.getSystemId().isEmpty())
                {
                    noticeLength.put(revoleSysIdList(warningDetail.getSystemId()),"3");
                }
            }
            if (Constants.WARN_OCCUPATION_TYPE_NURSE.equals(notification.getUserType()))
            {
                if (StringUtils.isEmpty(notification.getDeptId()))
                {
                    logger.error("下列值不允许为空！通知对象类型是护士时:科室ID:{}",
                            notification.getDeptId());
                    
                    loggerBS913.error("Message:{}, validate:{}",
                            notification.toString(), "当通知的职业类型是护士时，科室ID不能为空！"
                                + notification.getDeptId());
                    
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public boolean checkDependency(BS913 t,boolean flag)
    {
        for (Notification notification : t.getNotifications())
        {
            // $Author : tong_meng
            // $Date : 2013/05/10 15:34
            // $[BUG]0032269 MODIFY BEGIN
            WarningNotice warningNotice = warningNoticeService.selectWarningNotice(notification.getUserId(),notification.getUserType(),notification.getDeptId(),notification.getRuleGroup(),notification.getHospitalCode());
            // $[BUG]0032269 MODIFY END
            if (Constants.INSERT_MESSAGE_FLAG.equals(notification.getNewUpFlag()))
            {
                logger.debug("新增时...");
                // insert时候，找到记录就删除再插入，找不到就直接插入。
                if (warningNotice != null)
                {
                    logger.warn("要新增的警告通知表中存在记录！执行先删在加操作！通知员工号为：{}",
                            notification.getUserId());
                    deleteWarningList.add(warningNotice);
                    insertWarnigDtoList.add(notification);
                }
                else
                {
                    logger.debug("要新增的警告通知不存在记录，可以新增！通知员工号为：{}",
                            notification.getUserId());
                    insertWarnigDtoList.add(notification);
                }
            }
            else if (Constants.UPDATE_MESSAGE_FLAG.equals(notification.getNewUpFlag()))
            {
                logger.debug("更新时...");
                if (warningNotice != null)
                {
                    deleteWarningList.add(warningNotice);
                    insertWarnigDtoList.add(notification);
                }
                else
                {
                    logger.error("要更新的警告通知不存在，不可以更新！通知员工号为：{}",
                            notification.getUserId());
                }
            }
            else
            {
                logger.debug("删除时...");
                if (warningNotice != null)
                {
                    logger.debug("要删除的警告通知存在，可以删除！通知员工号为：{}",
                            notification.getUserId());
                    deleteWarningList.add(warningNotice);
                }
                else
                {
                    logger.error("要删除的警告通知不存在，不可以删除！通知员工号为：{}",
                            notification.getUserId());
                    
                    if (flag)
                    {
                        loggerBS913.error(
                                "Message:{},checkDependency:{}",
                                notification.toString(),
                                "要删除的警告通知不存在，不可以删除！通知员工号为：{}"
                                        + notification.getUserId());
                    }
                    return false;
                }
            }
        }
        logger.debug("要新增warning_notice表中的数据 {} 条。。。",
                insertWarnigDtoList.isEmpty() ? 0 : insertWarnigDtoList.size());
        
        logger.debug("要删除warning_notice表中的数据 {} 条。。。",
                deleteWarningList.isEmpty() ? 0 : deleteWarningList.size());
        
        return true;
    }

    @Override
    @Transactional
    public void saveOrUpdate(BS913 t)
    {
        setWarningNoticeList(t);
  
        logger.debug("要新增system_queue_name表中的数据 {} 条。。。",
                insertWarnNoticeDetailList.isEmpty() ? 0
                        : insertWarnNoticeDetailList.size());
        
        logger.debug("要删除system_queue_name表中的数据 {} 条。。。",
                deletetWarnNoticeDetaiList.isEmpty() ? 0
                        : deletetWarnNoticeDetaiList.size());
        
        warningNoticeService.saveOrDelete(insertWarnigList,
                insertWarnNoticeDetailList, deleteWarningList,
                deletetWarnNoticeDetaiList);
    }

    /**
     * 解析要insert的DTO集合，然后封装在实体集合里
     * @return 封装在实体集合
     */
    private void setWarningNoticeList(BS913 t)
    {
        WarningNotice notice;
   
        for (Notification notification : insertWarnigDtoList)
        {
            notice = new WarningNotice();
        
            BigDecimal warningNoticeSn = commonService.getSequence("SEQ_WARNING_NOTICE");
            
            notice.setWarningNoticeSn(warningNoticeSn);
            
            notice.setUserId(notification.getUserId());
            
            notice.setUserType(notification.getUserType());
            
            notice.setRulegroupId(notification.getRuleGroup());
            
            notice.setUnitId(notification.getDeptId());
            
            notice.setCreateTime(syatemTime);
            
            notice.setUpdateTime(syatemTime);
            
            notice.setDeleteFlag(Constants.NO_DELETE_FLAG);
            
            notice.setDeleteTime(null);
            
            notice.setUpdateCount(Constants.INSERT_UPDATE_COUNT);
            
            notice.setOrgCode(notification.getHospitalCode());
            
            notice.setOrgName(notification.getHospitalName());
            
            /* $Author :chang_xuewen
             * $Date : 2014/03/21 11:00$
             * [BUG]0043136 ADD BEGIN
             */
            if("WarningGroup".equals(notification.getRuleGroup())){
            	Map<String, Object> map = new HashMap<String, Object>();
                map.put("code", notification.getDeptId());
                map.put("deleteFlag", Constants.NO_DELETE_FLAG);
            	List<Object> wards = commonService.selectByCondition(CodeWard.class,
                        map);
            	String wardName = "";
            	for(int i =0; i<wards.size(); i++){
            		CodeWard ward = (CodeWard)wards.get(i);
            		wardName = ward.getName();
            		break;
            	}
            	
            	notice.setWardNo(wardName);// 这里BS913消息中科室ID节点实际发送的是病区名称
            }
            /*[BUG]0043136 ADD END*/

            insertWarnigList.add(notice);
            
            setWarnNoticeDetailList(warningNoticeSn);
        }
        
        for (WarningNotice warningNotice : deleteWarningList)
        {
            List<WarningNoticeDetail> systemQueueNames = warningNoticeService.selectWarnNoticeDetail(warningNotice.getWarningNoticeSn());
            
            deletetWarnNoticeDetaiList.add(systemQueueNames);
        }
    }

    private void setWarnNoticeDetailList(BigDecimal warningNoticeSn)
    {
        WarningNoticeDetail warningNoticeDetail;
        
        Set<Entry<String, String>> set = noticeLength.entrySet();
        
        Iterator<Entry<String, String>> iterator = set.iterator();
        
        while (iterator.hasNext())
        {
            Entry<String, String> entry = iterator.next();
        
            if (!StringUtils.isEmpty(entry.getValue()))
            {
                warningNoticeDetail = new WarningNoticeDetail();
            
                warningNoticeDetail.setWarningNoticeSn(warningNoticeSn);
                
                warningNoticeDetail.setNoticeType(entry.getValue());

                warningNoticeDetail.setSettingValue(entry.getKey());
                
                warningNoticeDetail.setCreateTime(syatemTime);
                
                warningNoticeDetail.setUpdateTime(syatemTime);
                
                warningNoticeDetail.setDeleteFlag(Constants.NO_DELETE_FLAG);
                
                warningNoticeDetail.setUpdateCount(Constants.INSERT_UPDATE_COUNT);
                
                warningNoticeDetail.setDeleteTime(null);
                
                if ("1".equals(entry.getValue()))
                {
                    // 解析通知时段
                    revoleDetail(warningNoticeDetail);
                }
                insertWarnNoticeDetailList.add(warningNoticeDetail);
            }
        }
    }

    /**
     * 将List 拼接一个字符串返回
     * @return
     */
    private String revoleSysIdList(List<String> sysIdList)
    {
        StringBuffer sBuffer = new StringBuffer();
        
        if(sysIdList == null || sysIdList.isEmpty())
        {
            return null ;
        }
        
        for (String systemId : sysIdList)
        {
            sBuffer.append(systemId);
            
            sBuffer.append(",");
        }
        
        String s = sBuffer.substring(0, sBuffer.length() -1);
        
        return s;
    }

    /**
     * 解析通知时段
     */
    private void revoleDetail(WarningNoticeDetail warningNoticeDetail)
    {
        for (String time : noticeTime)
        {
            String[] timeStrings = time.split(":");
            
            for (String notice : timeStrings)
            {
                if (Constants.SEND_TIME_MONDAY.equals(notice))
                {
                    warningNoticeDetail.setMonFlag("1");
                }
                else if (Constants.SEND_TIME_TUESDAY.equals(notice))
                {
                    warningNoticeDetail.setTusFlag("1");
                }
                else if (Constants.SEND_TIME_WEDNESDAY.equals(notice))
                {
                    warningNoticeDetail.setWedFlag("1");
                }
                else if (Constants.SEND_TIME_THURSDAY.equals(notice))
                {
                    warningNoticeDetail.setThuFlag("1");
                }
                else if (Constants.SEND_TIME_FRIDAY.equals(notice))
                {
                    warningNoticeDetail.setFriFlag("1");
                }
                else if (Constants.SEND_TIME_SATURDAY.equals(notice))
                {
                    warningNoticeDetail.setSatFlag("1");
                }
                else if (Constants.SEND_TIME_SUNDAY.equals(notice)) 
                {
                    warningNoticeDetail.setSunFlag("1");
                }
            }
        }
    }
}
