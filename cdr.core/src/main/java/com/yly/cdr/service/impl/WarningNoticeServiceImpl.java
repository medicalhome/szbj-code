package com.yly.cdr.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.founder.fasf.core.util.daohelper.entity.EntityDao;
import com.yly.cdr.core.Constants;
import com.yly.cdr.dao.LabDao;
import com.yly.cdr.entity.InfectiousContent;
import com.yly.cdr.entity.InfectiousRecord;
import com.yly.cdr.entity.WarningNotice;
import com.yly.cdr.entity.WarningNoticeDetail;
import com.yly.cdr.entity.WarningRecord;
import com.yly.cdr.service.CommonService;
import com.yly.cdr.service.WarningNoticeService;

@Component
public class WarningNoticeServiceImpl implements WarningNoticeService
{

    @Autowired
    private CommonService commonService;

    @Autowired
    private EntityDao entityDao;

    @Autowired
    private LabDao labDao;

    @Override
    @Transactional
    public List<WarningNoticeDetail> selectWarnNoticeDetail(
            BigDecimal warningNoticeSn)
    {
        Map<String, Object> condition = new HashMap<String, Object>();
        condition.put("warningNoticeSn", warningNoticeSn);
        condition.put("deleteFlag", Constants.NO_DELETE_FLAG);
        List result = new ArrayList();
        result = entityDao.selectByCondition(WarningNoticeDetail.class,
                condition);
        if (result.isEmpty())
        {
            return null;
        }
        return result;
    }

    @Override
    @Transactional
    public WarningNotice selectWarningNotice(String userId, String userType,
            String deptId, String ruleGroup, String orgCode)
    {
        Map<String, Object> condition = new HashMap<String, Object>();
        condition.put("userId", userId);
        condition.put("userType", userType);
        condition.put("rulegroupId", ruleGroup);
        condition.put("orgCode", orgCode);
        if (Constants.WARN_OCCUPATION_TYPE_NURSE.equals(userType))
        {
            condition.put("unitId", deptId);
        }
        List<Object> result = new ArrayList<Object>();
        result = entityDao.selectByCondition(WarningNotice.class, condition);
        if (result.isEmpty())
        {
            return null;
        }
        return (WarningNotice) result.get(0);
    }

    @Override
    @Transactional
    public void saveOrDelete(List<WarningNotice> insertWarnigList,
            List<WarningNoticeDetail> insertwarnNoticeDetailList,
            List<WarningNotice> deleteWarningList,
            List<List<WarningNoticeDetail>> deleteWarnNoticeDetailList)
    {
        if (!deleteWarnNoticeDetailList.isEmpty())
        {
            for (List<WarningNoticeDetail> list : deleteWarnNoticeDetailList)
            {
                for (WarningNoticeDetail warnNoticeDetail : list)
                {
                    commonService.delete(warnNoticeDetail);
                }
            }
        }
        if (!deleteWarningList.isEmpty())
        {
            for (WarningNotice object : deleteWarningList)
            {
                commonService.delete(object);
            }
        }
        if (!insertWarnigList.isEmpty())
        {
            for (WarningNotice object : insertWarnigList)
            {
                commonService.save(object);
            }
        }
        if (!insertwarnNoticeDetailList.isEmpty())
        {
            for (WarningNoticeDetail object : insertwarnNoticeDetailList)
            {
                commonService.save(object);
            }
        }
    }

    @Override
    @Transactional
    public WarningRecord selectWarnRecord(BigDecimal labReportSn,
            String triggerEvent, Date reportDate)
    {
        Map<String, Object> condition = new HashMap<String, Object>();
        condition.put("labReportSn", labReportSn);
        condition.put("triggerEvent", triggerEvent);
        condition.put("reportDate", reportDate);
        condition.put("deleteFlag", Constants.NO_DELETE_FLAG);
        List<Object> result = commonService.selectByCondition(
                WarningRecord.class, condition);
        if (result.isEmpty())
        {
            return null;
        }
        return (WarningRecord) result.get(0);
    }

    /**
     * 查询传染病预警业务记录表
     * @param 
     * @return InfectiousRecord
     */
    @Override
    @Transactional
    public InfectiousRecord selectInfectRecord(String patientLid,
            String patientDomain, String businessNo, String businessType,
            String orgCode)
    {
        List<Object> result = null;

        if (!StringUtils.isEmpty(patientLid)
            && !StringUtils.isEmpty(patientDomain))
        {
            Map<String, Object> condition = new HashMap<String, Object>();
            condition.put("patientLid", patientLid);
            condition.put("patientDomain", patientDomain);
            condition.put("businessNo", businessNo);
            condition.put("businessType", businessType);
            condition.put("orgCode", orgCode);
            condition.put("deleteFlag", Constants.NO_DELETE_FLAG);
            result = commonService.selectByCondition(InfectiousRecord.class,
                    condition);
        }
        else
        {
            Map<String, Object> condition = new HashMap<String, Object>();
            condition.put("businessNo", businessNo);
            condition.put("orgCode", orgCode);
            condition.put("deleteFlag", Constants.NO_DELETE_FLAG);
            result = commonService.selectByCondition(InfectiousRecord.class,
                    condition);
        }
        if (result.isEmpty())
        {
            return null;
        }
        return (InfectiousRecord) result.get(0);
    }

    /**
     * 查询传染病预警业务记录表--检验
     * @param 
     * @return InfectiousRecord
     */
    @Override
    @Transactional
    public InfectiousRecord selectInfectRecordForLab(String patientLid,
            String patientDomain, String businessNo, String businessType,
            String orgCode, String sourceSystemId, String reportTypeCode)
    {
        List<Object> result = null;

        if (!StringUtils.isEmpty(patientLid)
            && !StringUtils.isEmpty(patientDomain))
        {
            Map<String, Object> condition = new HashMap<String, Object>();
            condition.put("patientLid", patientLid);
            condition.put("patientDomain", patientDomain);
            condition.put("businessNo", businessNo);
            condition.put("businessType", businessType);
            condition.put("orgCode", orgCode);
            condition.put("sourceSystemId", sourceSystemId);
            
            if (reportTypeCode != null && !"".equals(reportTypeCode))
            {
                condition.put("reportTypeCode", reportTypeCode);
            }
            condition.put("deleteFlag", Constants.NO_DELETE_FLAG);
            result = commonService.selectByCondition(InfectiousRecord.class,
                    condition);
        }
        else
        {
            Map<String, Object> condition = new HashMap<String, Object>();
            condition.put("businessNo", businessNo);
            condition.put("orgCode", orgCode);
            condition.put("deleteFlag", Constants.NO_DELETE_FLAG);
            result = commonService.selectByCondition(InfectiousRecord.class,
                    condition);
        }
        if (result.isEmpty())
        {
            return null;
        }
        return (InfectiousRecord) result.get(0);
    }

    /**
     * 查询传染病预警业务记录表
     * @param 
     * @return InfectiousRecord
     */
    @Override
    @Transactional
    public List<InfectiousContent> selectContentRecord(InfectiousRecord inRecord)
    {
        Map<String, Object> condition = new HashMap<String, Object>();
        List<Object> result = new ArrayList<Object>();
        if (inRecord != null)
        {
            condition.put("businessSn", inRecord.getBusinessSn());
            result = commonService.selectByCondition(InfectiousContent.class,
                    condition);
        }

        if (result.isEmpty())
        {
            return null;
        }
        List<InfectiousContent> resList = new ArrayList<InfectiousContent>();
        for (int i = 0; i < result.size(); i++)
        {
            resList.add((InfectiousContent) result.get(i));
        }

        return resList;
    }

    @Override
    public List<Object> selectInfectCheck(String patientLid,
            String patientDomain, String visitTimes, String orgCode,
            String sourceSystemId)
    {
        // TODO Auto-generated method stub
        List<Object> result = null;

        Map<String, Object> condition = new HashMap<String, Object>();
        condition.put("patientLid", patientLid);
        condition.put("patientDomain", patientDomain);
        condition.put("visitTimes", visitTimes);
        condition.put("orgCode", orgCode);
        if (sourceSystemId != null && !"".equals(sourceSystemId))
        {
            condition.put("sourceSystemId", sourceSystemId);
        }
        condition.put("deleteFlag", Constants.NO_DELETE_FLAG);
        result = commonService.selectByCondition(InfectiousRecord.class,
                condition);

        if (result.isEmpty())
        {
            return null;
        }
        return result;
    }

    @Override
    public boolean isExist(String patientLid, String patientDomain,
            String orgCode, String visitTimes, String infectiousName)
    {
        int result = labDao.selectCount(patientLid, patientDomain, orgCode,
                visitTimes, infectiousName);

        if (result > 0)
        {
            return true;
        }
        else
        {
            return false;
        }
    }

}
