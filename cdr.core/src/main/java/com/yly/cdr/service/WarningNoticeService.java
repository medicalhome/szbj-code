package com.yly.cdr.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.yly.cdr.entity.InfectiousContent;
import com.yly.cdr.entity.InfectiousRecord;
import com.yly.cdr.entity.WarningNotice;
import com.yly.cdr.entity.WarningNoticeDetail;
import com.yly.cdr.entity.WarningRecord;

/**
 * 警告通知对象设定信息服务接口
 * @author tong_meng
 *
 */
public interface WarningNoticeService
{
    /**
     * 查找存在的记录
     * @param string 
     * @param deptId 
     * @param userType 
     * @param orgCode 
     * @return
     */
    @Transactional
    public WarningNotice selectWarningNotice(String string, String userType,
            String deptId, String ruleGroup, String orgCode);

    /**
     * 根据外键查询相关SystemQueueName
     * @param warningNoticeSn
     * @return
     */
    @Transactional
    public List<WarningNoticeDetail> selectWarnNoticeDetail(
            BigDecimal warningNoticeSn);

    /**
     * 对不同的实体集合，做insert或者delete处理
     * @param insertWarnigList
     * @param insertWarnNoticeDetailList 
     * @param deleteWarningList
     * @param deleteSystemQueueNameList
     */
    @Transactional
    public void saveOrDelete(List<WarningNotice> insertWarnigList,
            List<WarningNoticeDetail> warningNoticeDetail,
            List<WarningNotice> deleteWarningList,
            List<List<WarningNoticeDetail>> deleteWarnNoticeDetailList);

    /**
     * 查询警告记录表
     * @param labReportSn
     * @return
     */
    @Transactional
    public WarningRecord selectWarnRecord(BigDecimal labReportSn,
            String triggerEvent, Date reportDate);

    /**
     * 查询传染病预警业务记录表
     * @param labReportSn
     * @return InfectiousRecord
     */
    @Transactional
    public InfectiousRecord selectInfectRecord(String patientLid,
            String patientDomain, String businessNo, String businessType,
            String orgCode);

    /**
     * 查询传染病预警业务记录表--检验
     * @param labReportSn
     * @return InfectiousRecord
     */
    @Transactional
    public InfectiousRecord selectInfectRecordForLab(String patientLid,
            String patientDomain, String businessNo, String businessType,
            String orgCode, String sourceSystemId, String reportTypeCode);

    /**
     * 查询传染病预警业务记录表--检验
     * @param labReportSn
     * @return InfectiousRecord
     */
    @Transactional
    public List<Object> selectInfectCheck(String patientLid,
            String patientDomain, String visitTimes, String orgCode,
            String sourceSystemId);

    /**
     * 查询传染病预警业务子表记录表
     * @param labReportSn
     * @return InfectiousRecord
     */
    @Transactional
    public List<InfectiousContent> selectContentRecord(InfectiousRecord inRecord);

    /**
     * 查询是否存在
     * @param infectioutName
     * @return
     */
    @Transactional
    public boolean isExist(String patientLid, String patientDomain,
            String orgCode, String visitTimes, String infectiousName);

}
