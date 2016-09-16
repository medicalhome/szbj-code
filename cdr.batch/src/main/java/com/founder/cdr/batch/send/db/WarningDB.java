package com.founder.cdr.batch.send.db;

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

import com.founder.cdr.core.Constants;
import com.founder.cdr.dto.LabDto;
import com.founder.cdr.entity.InfectiousContent;
import com.founder.cdr.entity.InfectiousLog;
import com.founder.cdr.entity.InfectiousRecord;
import com.founder.cdr.entity.LabResult;
import com.founder.cdr.entity.LabResultCompositeItem;
import com.founder.cdr.entity.LabResultItem;
import com.founder.cdr.entity.MedicalVisit;
import com.founder.cdr.entity.Patient;
import com.founder.cdr.entity.WarningNotice;
import com.founder.cdr.entity.WarningNoticeDetail;
import com.founder.cdr.entity.WarningRecord;
import com.founder.cdr.hl7.dto.Diagnosis;
import com.founder.cdr.hl7.dto.InfectiousItem;
import com.founder.cdr.hl7.dto.InfectiousItemsforMessage;
import com.founder.cdr.hl7.dto.InfectiousMessageContent;
import com.founder.cdr.hl7.dto.InfectiousRules;
import com.founder.cdr.hl7.dto.LabReport;
import com.founder.cdr.hl7.dto.LabReportResult;
import com.founder.cdr.hl7.dto.WarningItem;
import com.founder.cdr.hl7.dto.WarningLabReportSpecificResult;
import com.founder.cdr.hl7.dto.WarningRules;
import com.founder.cdr.hl7.dto.infectiousbuild.INFECTIOUSBUILD;
import com.founder.cdr.hl7.dto.pocdin000040uv01.POCDIN000040UV01;
import com.founder.cdr.hl7.dto.pocdin000040uv03.POCDIN000040UV03;
import com.founder.cdr.hl7.dto.warningbuild.WARNINGBUILD;
import com.founder.cdr.service.CommonService;
import com.founder.cdr.service.LabService;
import com.founder.cdr.service.WarningNoticeService;
import com.founder.cdr.util.DateUtils;
import com.founder.cdr.util.StringUtils;

@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class WarningDB
{
    private static final Logger logger = LoggerFactory.getLogger(WarningDB.class);

    // 异常标识
    private final static String UNNORMAL = "unnormal";

    // 危机标识
    private final static String CRISIS = "crisis";

/*    // 更新其他标识
    private static final String UPDATE_REST = "00";

    // 更新发送标记标识
    private static final String UPDATE_SEND_FLAG = "11";*/

    // 检验具体项目结果-异常集合
//    private Map<String, WarningItem> unnormalMap = new HashMap<String, WarningItem>();
    private Map<String, List<WarningLabReportSpecificResult>> pUncommonMap = new HashMap<String, List<WarningLabReportSpecificResult>>();
    // 检验具体项目结果-危机集合
//    private private Map<String, WarningItem> crisisMap = new HashMap<String, WarningItem>();
    private Map<String, List<WarningLabReportSpecificResult>> pCrisisMap = new HashMap<String, List<WarningLabReportSpecificResult>>();
    // 检验具体项目结果-危机集合
//    private Map<String, WarningItem> crisisAndUnnormalMap = new HashMap<String, WarningItem>();
    private Map<String, List<WarningLabReportSpecificResult>> pCrisisAndUnnormalMap = new HashMap<String, List<WarningLabReportSpecificResult>>();
    // 要更新的检验具体结果的集合
    List<LabResultItem> labResultItemList = new ArrayList<LabResultItem>();
    
    // 检验报告内部序列号
    private BigDecimal labReportSn;

    // 患者内部序列号
    private BigDecimal patientSn;

    // 就诊内部序列号
    private BigDecimal visitSn;

    // 检验报告内部序列号
//    private BigDecimal contentSn;

    // 系统时间
    private Date sysDate;

    @Autowired
    private LabService labService;

    @Autowired
    private CommonService commonService;

    @Autowired
    private WarningNoticeService notice;

    /**
     * 根据条件更新检验项目具体结果表
     * @param t 检验报告DTO
     * @param unnormal 异常值DTO
     * @param crisis 危机值DTO
     */
    public void updateLabResultItem(POCDIN000040UV01 t, WARNINGBUILD unnormal,
            WARNINGBUILD crisis, Date systemDate)
    {
        sysDate = systemDate;
        boolean unnormalFlag = checkParameter(unnormal, UNNORMAL);

        boolean crisisFlag = checkParameter(crisis, CRISIS);

        if (unnormalFlag || crisisFlag)
        {
            selectLabReItemByItemCode(t);

            if (labResultItemList.isEmpty())
            {
                logger.error("未找到要更新的检验报告具体结果集");

                return;
            }

            commonService.updatePartiallyAll(labResultItemList, "uncommonFlag",
                    "crisisFlag", "updateTime");
        }
    }

    /**
     * 更新检验项目结果表
     * @param warningBuild 异常值List
     * @param crisis 危险值List
     * @return 更新成功标志
     */
    private void selectLabReItemByItemCode(POCDIN000040UV01 t)
    {
        // 解析异常MAP
        resolveMap(pUncommonMap, UNNORMAL, t);

        // 解析危机MAP
        resolveMap(pCrisisMap, CRISIS, t);

        // 解析异常危机MAP
        resolveMap(pCrisisAndUnnormalMap, null, t);
    }

    /**
     * 关联查找检验报告，并查询出检验报告内部序列号
     * @return 无结果返回null;有结果返回检验报告内部序列号
     */
    public BigDecimal selectLabResultSn(String reportNo, String sourceSysCode,
            String orgCode)
    {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("labReportLid", reportNo);
        map.put("sourceSystemId", sourceSysCode);
        map.put("orgCode", orgCode);
        map.put("deleteFlag", Constants.NO_DELETE_FLAG);
        LabResult labResult = labService.selectLabResultByCondition(map);
        if (labResult == null)
        {
            logger.error("检验报告为空！报告号为：{}", reportNo);
            return null;
        }
        patientSn = labResult.getPatientSn();
        visitSn = labResult.getVisitSn();
        labReportSn = labResult.getLabReportSn();
        return labReportSn;
    }

    /**
     * 解析异常或者危机map 
     * @param unnormalOrCrisisMap 异常或危机MAP
     * @param identification 异常危机标识
     * @param t
     */
    private void resolveMap(
            Map<String, List<WarningLabReportSpecificResult>> unnormalOrCrisisMap,
            String identification, POCDIN000040UV01 t)
    {
        LabResultItem labresultItem = new LabResultItem();

        List<Object> labResultItems;

        Map<String, Object> map = new HashMap<String, Object>();

        Set<Entry<String, List<WarningLabReportSpecificResult>>> set = unnormalOrCrisisMap.entrySet();

        Iterator<Entry<String, List<WarningLabReportSpecificResult>>> iterator = set.iterator();

        while (iterator.hasNext())
        {
            Entry<String, List<WarningLabReportSpecificResult>> entry = iterator.next();

            String labItemCode = entry.getKey();

            List<WarningLabReportSpecificResult> warningLabReportSpecificResultList = entry.getValue();

            BigDecimal compositeItemSn = selectCompositeItemSn(labItemCode,
                    t.getReportNo(), t.getSourceSystemId(), t.getOrgCode());

            if (compositeItemSn == null)
            {
                break;
            }
            for(WarningLabReportSpecificResult warningLabReportSpecificResult : warningLabReportSpecificResultList){
                map.put("labResultCompositeItemSn", compositeItemSn);

                map.put("itemCode", warningLabReportSpecificResult.getItemCode());

                map.put("deleteFlag", Constants.NO_DELETE_FLAG);

                labResultItems = labService.selectLabResultItemByCondition(map);  


                if (labResultItems.isEmpty())
                {
                    logger.error("检查项目具体结果为空！项目编码为：{}",
                    		warningLabReportSpecificResult.getItemCode());
                    //break;
                }
                else
                {
                    labresultItem = this.setLabReItem(
                            (LabResultItem) labResultItems.get(0), identification,
                            warningLabReportSpecificResult);

                    if (labresultItem != null)
                    {
                        labResultItemList.add(labresultItem);
                    }
                }
            }

        }
    }

    /**
     * 根据不同的DTO，设置要更新的检验项目具体结果
     * @param labResultItem 要更新的检验具体结果实体
     * @param warningLabReportResult 判断传的是异常值的DTO还是危机值的DTO
     * @return 返回要更新的检验项目具体结果集合
     */
    private LabResultItem setLabReItem(LabResultItem labResultItem,
            String identification, WarningLabReportSpecificResult warningLabReportResult)
    {
        // 异常
        if (UNNORMAL.equals(identification))
        {
            labResultItem.setUncommonFlag("1");
            labResultItem.setUpdateTime(sysDate);
        }
        // 危机
        else if (CRISIS.equals(identification))
        {
            labResultItem.setCrisisFlag("1");
            labResultItem.setUpdateTime(sysDate);
        }
        // 异常和危机
        else
        {
            labResultItem.setCrisisFlag("1");
            labResultItem.setUncommonFlag("1");
            labResultItem.setUpdateTime(sysDate);
        }
        return labResultItem;
    }

    /**
     * 根据检验报告的内部序列号查找检验项目的内部序列号
     * 并根据检验项编码查找唯一检验项目实体
     * @param labItemCode 检验项编码
     * @param reportNo  报告号
     * @param sourceSysCode  系统源ID
     * @return 无结果返回null;有结果返回检验项目内部序列号
     */
    private BigDecimal selectCompositeItemSn(String labItemCode,
            String reportNo, String sourceSysCode, String orgCode)
    {
        Map<String, Object> map = new HashMap<String, Object>();

        BigDecimal labResultSn = null;

        if (labReportSn == null)
        {
            labResultSn = selectLabResultSn(reportNo, sourceSysCode, orgCode);
        }
        else
        {
            labResultSn = labReportSn;
        }

        if (labResultSn == null)
        {
            return null;
        }

        map.put("labReportSn", labResultSn);

        map.put("deleteFlag", Constants.NO_DELETE_FLAG);

        List<Object> labResultCompositeItemList = labService.selectLabResultCompositeByCondition(map);

        if (labResultCompositeItemList == null
            || labResultCompositeItemList.isEmpty())
        {
            logger.error("检验项目为空！检验报告内部序列号为：{}", labResultSn);
            return null;
        }

        for (Object object : labResultCompositeItemList)
        {
            LabResultCompositeItem compositeItem = (LabResultCompositeItem) object;

            // 一个检验项编码只对应一个检验报告下的一个检验项目。所以匹配上就返回检验项目内部序列号
            if (labItemCode.equals(compositeItem.getItemCode()))
            {
                return compositeItem.getCompositeItemSn();
            }
        }

        logger.error("没有找到与检验项代码:{} 一致的检验项目！", labItemCode);
        return null;
    }

    /**
     * 检查WARNINGBUILD参数是否有值，并且解析WARNINGBUILD
     * 检查DTO中是否存在异常值和危机值都是false 的情况，存在则remove掉
     * @param WARNINGBUILD
     * @return true:有值;false:没有值
     */
    private boolean checkParameter(WARNINGBUILD unnormalOrcrisis,
            String identification)
    {
    	//只有异常没有危机时校验报错
    	if(unnormalOrcrisis!=null){
    		
//    		Map<String, List<WarningLabReportSpecificResult>> pUncommonMap = new HashMap<String, List<WarningLabReportSpecificResult>>();
	        for (WarningRules report : unnormalOrcrisis.getRow())
	        {
	        	List<WarningLabReportSpecificResult> warningItemList = new ArrayList<WarningLabReportSpecificResult>();
	        	//Map<String, Object> uncommonMap = new HashMap<String, Object>();
	            String itemCode = "";
	            String itemNumValue = "";
	            String itemStrValue = "";
	            String unit = "";
	            boolean checkStatus = false;
	            String checkMessage = "";
	            String suggestion = "";
	            String checkOffice = "";
	            String diagnosisCode = "";
	            
	            for (WarningItem warningItem : report.getItem())
	            {                
		            String itemName = warningItem.getItemName();
	
	                String value = warningItem.getValue();
	
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
	            	WarningLabReportSpecificResult warningLabReportSpecificResult = new WarningLabReportSpecificResult();
	            	warningLabReportSpecificResult.setItemCode(itemCode);
	            	warningLabReportSpecificResult.setItemNumValue(itemNumValue);
	            	warningLabReportSpecificResult.setItemStrValue(itemStrValue);
	            	warningLabReportSpecificResult.setDiagnosisCode(diagnosisCode);
	            	warningLabReportSpecificResult.setCheckMessage(checkMessage);
	            	warningLabReportSpecificResult.setUnit(unit);
	            	warningLabReportSpecificResult.setSuggestion(suggestion);
	            	warningLabReportSpecificResult.setCheckStatus(checkStatus);
	            	warningLabReportSpecificResult.setCheckOffice(checkOffice);
	            	warningItemList.add(warningLabReportSpecificResult);
	            	// 如果异常或危机状态为true,添加到待处理的map中
	                    // 异常和危机放在不同的map里
                    if (identification.equals(UNNORMAL))
                    {
                        Set<Entry<String, List<WarningLabReportSpecificResult>>> set = pUncommonMap.entrySet();

                        Iterator<Entry<String, List<WarningLabReportSpecificResult>>> iterator = set.iterator();
                        boolean isPut = false;
                        while (iterator.hasNext())
                        {
                            Entry<String, List<WarningLabReportSpecificResult>> entry = iterator.next();

                            String labItemCode = entry.getKey();
                            if(report.getRowKey().equals(labItemCode)){
                            	entry.getValue().add(warningLabReportSpecificResult);
                            	isPut = true;
                            }
                        }
                        if(!isPut){
                        	pUncommonMap.put(report.getRowKey(), warningItemList);
                        }
                    	
                    }
                    else
                    {
                        Set<Entry<String, List<WarningLabReportSpecificResult>>> set = pCrisisMap.entrySet();

                        Iterator<Entry<String, List<WarningLabReportSpecificResult>>> iterator = set.iterator();
                        boolean isPut = false;
                        while (iterator.hasNext())
                        {
                            Entry<String, List<WarningLabReportSpecificResult>> entry = iterator.next();

                            String labItemCode = entry.getKey();
                            if(report.getRowKey().equals(labItemCode)){
                            	entry.getValue().add(warningLabReportSpecificResult);
                            	isPut = true;
                            }
                        }
                        if(!isPut){
                        	pCrisisMap.put(report.getRowKey(), warningItemList);
                        }
                    }

                    checkCrisisAndUnnormal(warningItemList);
	            }	            
	        }
    	}
        if (pUncommonMap.isEmpty() && pUncommonMap.isEmpty()
            && pCrisisAndUnnormalMap.isEmpty())
        {
            logger.error("消息中，待处理的数据中没有异常值和危机值！");

            return false;
        }
        return true;
    }

    /**
     * 检查异常和危机map中是否存在同一itemCode的结果
     * @param report
     * @param warningResult
     */
    private void checkCrisisAndUnnormal(List<WarningLabReportSpecificResult> warningResult)
    {
        List<String> unnormalList = getKeyList(pUncommonMap);

        List<String> crisisList = getKeyList(pCrisisMap);

        for (String unnormalKey : unnormalList)
        {
            for (String crisisKey : crisisList)
            {
                if (unnormalKey.equals(crisisKey))
                {
                    // 如果异常或危机map中的itemCode相等，说明这条记录既是危机又是异常，将此记录从异常危机map中移除，然后添加到新的map中
                	pUncommonMap.remove(unnormalKey);

                	pCrisisMap.remove(crisisKey);

                    pCrisisAndUnnormalMap.put(crisisKey, warningResult);
                }
            }
        }
    }

    /**
     * 将异常或者危机map中的itemCode取出
     * @param unnormalOrCrisisMap 异常或危机map
     * @return 取出来的itemCode集合
     */
    private List<String> getKeyList(
            Map<String, List<WarningLabReportSpecificResult>> unnormalOrCrisisMap)
    {
        List<String> keyList = new ArrayList<String>();

        Set<Entry<String, List<WarningLabReportSpecificResult>>> set = unnormalOrCrisisMap.entrySet();

        Iterator<Entry<String, List<WarningLabReportSpecificResult>>> iterator = set.iterator();

        while (iterator.hasNext())
        {
            Entry<String, List<WarningLabReportSpecificResult>> entry = iterator.next();

            String labItemCode = entry.getKey();

            keyList.add(labItemCode);
        }

        return keyList;
    }

    /**
     * 通过检验报告的dto中的送检医生ID，异常/警告值
     * 查询警告通知表
     * @param t 检验报告DTO
     * @param warnOrUnnormal 异常警告值
     * @return 返回警告通知实体集合
     */
    public List<WarningNotice> selectWarningNotice(POCDIN000040UV01 t,
            String warnOrUnnormal, String orgCode)
    {
        // 送检医生ID
        String requestPerson = t.getApplyPerson();

        // 就诊科室
        String deptId = t.getVisitDept();

        List<WarningNotice> warningNotice = labService.selectWarningNotice(
                requestPerson, deptId, warnOrUnnormal, orgCode);

        return warningNotice;
    }

    /**
     * 通过检验报告的dto中的送检医生ID，异常/警告值
     * 查询警告通知表
     * @param t 检验报告DTO
     * @param warnOrUnnormal 异常警告值
     * @return 返回警告通知实体集合
     */
    public List<WarningNotice> selectInfectious(String ruleGroup,
            String orgCode, String wardNo)
    {

        List<WarningNotice> warningNotice = labService.selectWarningInfectNotice(
                ruleGroup, orgCode, wardNo);

        return warningNotice;
    }

    /**
     * 通过警告通知内部序列号查找系统队列名称表
     * @param warningNoticeSn 警告通知内部序列号
     * @return  返回系统队列名称表
     */
    /*
     * public List<SystemQueueName> selectSysQueueName(BigDecimal
     * warningNoticeSn) { return notice.selectSystems(warningNoticeSn); }
     */

    /**
     * 查询警告通知记录表
     * @param labReportSn 检验报告内部序列号
     * @return
     */
    public WarningRecord selectWarnRecord(String triggerEvent, Date reportDate)
    {
        return notice.selectWarnRecord(labReportSn, triggerEvent, reportDate);
    }

    /**
     * 查询传染病预警业务记录表
     * @param labReportSn 检验报告内部序列号
     * @return
     */
    public InfectiousRecord selectInfectRecord(String patientLid,
            String patientDomain, String businessNo, String businessType,
            String orgCode)
    {
        return notice.selectInfectRecord(patientLid, patientDomain, businessNo,
                businessType, orgCode);
    }

    /**
     * 查询传染病预警业务记录表
     * @param labReportSn 检验报告内部序列号
     * @return
     */
    public InfectiousRecord selectInfectRecordForLab(String patientLid,
            String patientDomain, String businessNo, String businessType,
            String orgCode, String sourceSystemId, String reportTypeCode)
    {
        return notice.selectInfectRecordForLab(patientLid, patientDomain,
                businessNo, businessType, orgCode, sourceSystemId,
                reportTypeCode);
    }

    /**
     * 查询传染病预警业务子表记录表
     * @param
     * @return
     */
    public List<InfectiousContent> selectContentRecord(InfectiousRecord inRecord)
    {
        return notice.selectContentRecord(inRecord);
    }

    /**
     * 新增警告记录表
     * @param warningRecord 警告记录实体
     */
    public void insertWarnRecord(WarningRecord warningRecord,
            String triggerEvent, Date reportDate)
    {
        warningRecord.setLabReportSn(labReportSn);

        warningRecord.setTriggerEvent(triggerEvent);

        warningRecord.setReportDate(reportDate);

        warningRecord.setCreateTime(sysDate);

        warningRecord.setUpdateTime(sysDate);

        warningRecord.setUpdateCount(Constants.INSERT_UPDATE_COUNT);

        warningRecord.setDeleteFlag(Constants.NO_DELETE_FLAG);

        commonService.save(warningRecord);
    }

    public void updateWarnRecord(WarningRecord warningRecord)
    {
        warningRecord.setUpdateTime(sysDate);

        commonService.update(warningRecord);
    }

    /**
     * 新增传染病记录表--检验报告
     * @param 
     */
    @Transactional
    public void insertInfectiousForLab(InfectiousRecord inRecord,
            List<InfectiousContent> inContentList, List<LabReport> labReport,
            Date reportDate, POCDIN000040UV01 pocdin000040uv01,
            INFECTIOUSBUILD dto)
    {
        BigDecimal businessSn = commonService.getSequence("SEQ_EXTERNAL_SEND_BUSINESS");

        inRecord.setBusinessSn(businessSn);

        inRecord.setReportDate(reportDate);

        String wardsNo = "";

        if (Constants.SOURCE_LAB.equals(pocdin000040uv01.getSourceSystemId()))
        {
            wardsNo = pocdin000040uv01.getWardsName();
        }
        else if (Constants.SOURCE_MICROBE.equals(pocdin000040uv01.getSourceSystemId()))
        {
            wardsNo = pocdin000040uv01.getWardsNo();
        }

        inRecord.setWardNo(wardsNo);

        inRecord.setPatientName(pocdin000040uv01.getPatientName());

        if (Constants.SOURCE_LAB.equals(pocdin000040uv01.getSourceSystemId()))
        {
            inRecord.setBusinessType("02");

            inRecord.setBusinessTypeName("LIS检验报告");
        }
        else if (Constants.SOURCE_MICROBE.equals(pocdin000040uv01.getSourceSystemId()))
        {
            inRecord.setBusinessType("03");

            inRecord.setBusinessTypeName("微生物检验报告");
        }

        inRecord.setReportTypeCode(pocdin000040uv01.getReportTypeCode());

        inRecord.setReportTypeName(pocdin000040uv01.getReportTypeName());

        inRecord.setBusinessNo(pocdin000040uv01.getReportNo());// 该号码是lab_report_lid

        /**
         * 修改bug55008
         * 江苏版本domainId无法区分是分诊和住院，用就诊类别“VistType”区分
         */
        inRecord.setPatientDomain(!"46600083-8".equals(pocdin000040uv01.getOrgCode())?pocdin000040uv01.getPatientDomain():pocdin000040uv01.getVisitType());

        inRecord.setPatientLid(pocdin000040uv01.getPatientLid());

        inRecord.setBedNo(pocdin000040uv01.getBedNo());

        inRecord.setGenderCode(pocdin000040uv01.getGenderCode());

        inRecord.setGenderName(pocdin000040uv01.getGenderName());

        Date birthday = DateUtils.stringToDate(pocdin000040uv01.getBirthDate());

        Date visitday = DateUtils.stringToDate(pocdin000040uv01.getVisitDate());

        inRecord.setAge(DateUtils.caluAge(birthday, visitday));

        inRecord.setBirthDay(birthday);

        inRecord.setReportDoctorCode(pocdin000040uv01.getReporterId());

        inRecord.setReportDoctorName(pocdin000040uv01.getReporterName());

        // Author :jia_yanqing
        // Date : 2014/04/29
        // [BUG]0044109 MODIFY BEGIN
        inRecord.setVisitDeptCode(pocdin000040uv01.getVisitDept());

        inRecord.setVisitDeptName(pocdin000040uv01.getVisitDeptName());
        // [BUG]0044109 MODIFY END

        //江苏存的是就诊流水号
        inRecord.setVisitTimes(!"46600083-8".equals(pocdin000040uv01.getOrgCode())?pocdin000040uv01.getVisitTimes():pocdin000040uv01.getVisitOrdNo());

        inRecord.setSourceSystemId(pocdin000040uv01.getSourceSystemId());

        // inRecord.setWardCode(pocdin000040uv01.getWardCode());//...........................................

        inRecord.setCreateTime(sysDate);

        inRecord.setUpdateTime(sysDate);

        inRecord.setUpdateCount(Constants.INSERT_UPDATE_COUNT);

        inRecord.setDeleteFlag(Constants.NO_DELETE_FLAG);

        inRecord.setOrgCode(pocdin000040uv01.getOrgCode());

        inRecord.setOrgName(pocdin000040uv01.getOrgName());

        // Author: jia_yanqing
        // Date: 2014/4/1 10:00
        // [BUG]0043368 ADD BEGIN
        inRecord.setModifyDate(DateUtils.stringToDate(pocdin000040uv01.getReviewDate()));
        // [BUG]0043368 ADD END

        // Author: jia_yanqing
        // Date: 2014/3/28 15:00
        // [BUG]0043289 ADD BEGIN
        inRecord.setMedicalRecordNo(pocdin000040uv01.getMedicalNo());
        // [BUG]0043289 ADD END

        commonService.save(inRecord);

        if (Constants.SOURCE_LAB.equals(pocdin000040uv01.getSourceSystemId()))
        {
            insertOrUpdateSubBusinessForlab(inRecord, labReport, dto, "insert",
                    inContentList);
        }
        else if (Constants.SOURCE_MICROBE.equals(pocdin000040uv01.getSourceSystemId()))
            insertOrUpdateSubBusinessFormic(inRecord, labReport, dto, "insert",
                    inContentList);

    }

    /**
     * 新增传染病记录表
     * @param 
     */
    @Transactional
    public void insertInfectiousRecord(InfectiousRecord inRecord,
            List<InfectiousContent> inContentList,
            List<InfectiousMessageContent> messageContents, Date reportDate,
            POCDIN000040UV03 pocdin000040uv03, INFECTIOUSBUILD dto)
    {

//
//        inRecord.setReportDate(reportDate);
//        //做BS335跟BS38的区分
////        inRecord.setWardNo(pocdin000040uv03.getWardNo());
//
//        inRecord.setPatientName(pocdin000040uv03.getPatientName());
//
//        //Author : yu_yzh
//        //Date : 2015/05/04
//        //[BUG]55257
//        if("BS335".equals(pocdin000040uv03.getServiceId())){
//        	inRecord.setBusinessType("04");
//        	
//	        inRecord.setBusinessTypeName("入院记录");
//	        //BS335病区，床号，年龄的xpath跟BS358不一致，新建字段
//	        inRecord.setWardNo(pocdin000040uv03.getWardNoBS335());
//	        inRecord.setBedNo(pocdin000040uv03.getBedNoBS335());
//	        inRecord.setAge(pocdin000040uv03.getAgekkkkk());
////	        logger.debug("BS335 wardNo: " + pocdin000040uv03.getWardNoBS335());
////	        logger.debug("BS335 bedNo: " + pocdin000040uv03.getBedNoBS335());
////	        logger.debug("BS335 age: " + pocdin000040uv03.getAgeBS335());
//	        inRecord.setVisitDeptName(pocdin000040uv03.getVisitDeptNameBS335());
//	        //
//        }else{
//	        inRecord.setBusinessType("01");
//	
//	        inRecord.setBusinessTypeName("传染病报卡");
//	        //
//	        inRecord.setWardNo(pocdin000040uv03.getWardNo());
//	        inRecord.setBedNo(pocdin000040uv03.getBedNo());
//	        inRecord.setAge(pocdin000040uv03.getAge());
//	        inRecord.setVisitDeptName(pocdin000040uv03.getVisitDeptName());
//	        //
//        }
//        //[BUG]55257
//
//        inRecord.setBusinessNo(pocdin000040uv03.getDocumentLid());
//
//        /**
//         * 修改bug55008
//         * 江苏版本domainId无法区分是分诊和住院，用就诊类别“VistType”区分
//         */
//        inRecord.setPatientDomain(!"46600083-8".equals(pocdin000040uv03.getOrganizationCode())?pocdin000040uv03.getPatientDomain():pocdin000040uv03.getVisitTypeCode());
//
//
//        inRecord.setPatientLid(pocdin000040uv03.getPatientLid());
//      //做BS335跟BS358的区分
////        inRecord.setBedNo(pocdin000040uv03.getBedNo());
//
//        inRecord.setGenderCode(pocdin000040uv03.getGenderCode());
//
//        // 消息中命名为出院号，其实是就诊号，这里无误
//        inRecord.setMedicalRecordNo(pocdin000040uv03.getOutPatientNo());
//
//        inRecord.setGenderName(pocdin000040uv03.getGenderName());
//
////        inRecord.setAge(pocdin000040uv03.getAge());
//
//        inRecord.setBirthDay(DateUtils.stringToDate(pocdin000040uv03.getBirthDay()));
//
//        inRecord.setReportDoctorCode(pocdin000040uv03.getDocumentAuthor());
//
//        inRecord.setReportDoctorName(pocdin000040uv03.getDocumentAuthorName());
//
//        // Author :jia_yanqing
//        // Date : 2014/04/29
//        // [BUG]0044109 MODIFY BEGIN
//        inRecord.setVisitDeptCode(pocdin000040uv03.getVisitDept());
//
////        inRecord.setVisitDeptName(pocdin000040uv03.getVisitDeptName());
//        // [BUG]0044109 MODIFY END
//
//        //江苏用的是就诊流水号
//        inRecord.setVisitTimes(!"46600083-8".equals(pocdin000040uv03.getOrganizationCode())?pocdin000040uv03.getVisitTimes():pocdin000040uv03.getVisitOrdNo());
//
//        inRecord.setWardCode(pocdin000040uv03.getWardCode());
//
//        inRecord.setCreateTime(sysDate);
//
//        inRecord.setUpdateTime(sysDate);
//        String legal = pocdin000040uv03.getLegalStatus();
//
//        inRecord.setLegalStatus(legal);
//
//        inRecord.setUpdateCount(Constants.INSERT_UPDATE_COUNT);
//
//        inRecord.setDeleteFlag(Constants.NO_DELETE_FLAG);
//
//        inRecord.setOrgCode(pocdin000040uv03.getOrganizationCode());
//
//        inRecord.setOrgName(pocdin000040uv03.getOrganizationName());
//
//        // Author: guo_hongyan
//        // Date: 2014/4/1 13:50
//        // [BUG]0043368 ADD BEGIN
//        inRecord.setModifyDate(DateUtils.stringToDate(pocdin000040uv03.getModifyTime()));
//        // [BUG]0043368 ADD END

        BigDecimal businessSn = commonService.getSequence("SEQ_EXTERNAL_SEND_BUSINESS");

        inRecord.setBusinessSn(businessSn);
    	
        inRecord = this.setInfectiousRecord(inRecord, reportDate, pocdin000040uv03);
        
        commonService.save(inRecord);

        insertOrUpdateSubBusiness(inRecord, messageContents, dto, "insert",
                inContentList, pocdin000040uv03);

    }

    private Map<String, String> getInfectiousRoute(INFECTIOUSBUILD dto)
    {
        String[] typeList = { "空气传播疾病", "飞沫传播疾病", "接触传播疾病", "血液（或性）传播疾病",
                "虫媒或动物传播疾病" };
        Map<String, String> routesMap = new HashMap<String, String>();
        // 循环从结果dto中取出传播途径
        for (InfectiousRules rules : dto.getRow())
        {
            List<InfectiousItem> items = rules.getItem();
            String value = "";
            for (InfectiousItem item : items)
            {
                String type = item.getItemName();
                // 如果是值就存储在value中，是类型就存在类型中
                if ("diagnosis".equals(type))
                {
                    value = item.getValue();
                }else if("diseaseCode".equals(type)){
                	value = item.getValue();
                }
                else if ("infectionRoute".equals(type))
                {
                    String typename = item.getValue();
                    if (typeList[0].equals(typename))
                    {
                        routesMap.put(value, typeList[0]);
                    }
                    else if (typeList[1].equals(typename))
                    {
                        routesMap.put(value, typeList[1]);
                    }
                    else if (typeList[2].equals(typename))
                    {
                        routesMap.put(value, typeList[2]);
                    }
                    else if (typeList[3].equals(typename))
                    {
                        routesMap.put(value, typeList[3]);
                    }
                    else if (typeList[4].equals(typename))
                    {
                        routesMap.put(value, typeList[4]);
                    }
                }
            }
        }
        return routesMap;
    }

    /**
     * 更新业务检查，当发现消息中要更新的项目与DB中的不符时，将DB中的不一致项目DELETE_FLAG置为1，将消息中不一致的项目插入到DB
     * @param messageContents
     * @param content
     */
    private void modifyItem(List<InfectiousMessageContent> messageContents,
            List<InfectiousContent> content, Map<String, String> routes,
            BigDecimal businessSn, String medicalType)
    {
        List<String> meItems = new ArrayList<String>();
        List<String> dbItems = new ArrayList<String>();
        List<InfectiousContent> contentForInsert = new ArrayList<InfectiousContent>();
        List<InfectiousContent> contentForDelete = new ArrayList<InfectiousContent>();
        List<String> insertItems = new ArrayList<String>();
        List<String> deleteItems = new ArrayList<String>();
        Date curDate = new Date();
        // 从消息和DB中遍历取出项目标识编码
        for (InfectiousMessageContent con : messageContents)
        {
            for (InfectiousItemsforMessage mes : con.getItems())
            {
                meItems.add(mes.getName());
            }
        }
        for (InfectiousContent ite : content)
        {
            dbItems.add(ite.getReportContentItemCode());
        }

        // 分别对比消息和DB的item进行新增和删除操作
        if (!meItems.isEmpty() && !dbItems.isEmpty() && meItems != null
            && dbItems != null)
        {
            for (String msitem : meItems)
            {// 遍历消息中的items，如果在db的itmes中找不到，则新增到DB
                if (!dbItems.contains(msitem))
                {
                    insertItems.add(msitem);
                }
                /*无用逻辑
                else if (dbItems.contains(msitem))
                {
                    for (InfectiousContent con : content)
                    {
                        if (msitem.equals(con.getReportContentItemCode())
                            && !medicalType.equals(con.getMedicalType()))
                        {// 消息中的并与数据库中的病名称相同

                            String senFlag = con.getSendFlag();
                            Date sendDate = con.getSendDate();
                            Date sendOthersDate = con.getSendOthersDate();
                            String sendOthersFlag = con.getSendOthersFlag();
                            // 新增消息中的该条病
                            for (InfectiousMessageContent mc : messageContents)
                            {
                                for (InfectiousItemsforMessage mes : mc.getItems())
                                {
                                    InfectiousContent inContent = new InfectiousContent();
                                    if (msitem.equals(mes.getName()))
                                    {// 找到消息中的这一条，用消息中的值去新增到DB中
                                        inContent.setBusinessSn(businessSn);
                                        BigDecimal reportContentSn = commonService.getSequence("SEQ_REPORT_CONTENT");
                                        inContent.setReportContentSn(reportContentSn);
                                        inContent.setReportContentItemCode(mes.getName());
                                        inContent.setReportContentItemName(mes.getValue());
                                        inContent.setInfectiousName(mes.getValue());
                                        inContent.setMedicalType(medicalType);
                                        inContent.setCreateTime(curDate);
                                        inContent.setUpdateTime(curDate);
                                        // 以下四个是要从旧的DB的条目中替换过来的内容
                                        inContent.setSendOthersFlag(sendOthersFlag);
                                        inContent.setSendFlag(senFlag);
                                        inContent.setSendDate(sendDate);
                                        inContent.setSendOthersDate(sendOthersDate);
                                        //
                                        inContent.setUpdateCount(Constants.INSERT_UPDATE_COUNT);
                                        inContent.setDeleteFlag(Constants.NO_DELETE_FLAG);
                                        for (Map.Entry<String, String> entry : routes.entrySet())
                                        {
                                            if (mes.getValue().equals(
                                                    entry.getKey()))
                                            {
                                                inContent.setSpreadWay(entry.getValue());
                                            }
                                        }
                                        contentForInsert.add(inContent);
                                    }
                                }
                            }

                            // 逻辑删除该条传染病
                            Date date = new Date();
                            con.setDeleteFlag(Constants.DELETE_FLAG);
                            con.setUpdateTime(date);
                            con.setDeleteTime(date);
                            commonService.update(con);
                            logger.debug("修改了不匹配的项目新增item到DB，由于消息中的item大于DB中!");

                        }
                    } 
                }*/
            }
            for (String dbitem : dbItems)
            {// 遍历DB中的items，如果在消息Items中找不到，则将DB的该item置为删除
                if (!meItems.contains(dbitem))
                {
                    deleteItems.add(dbitem);
                }
            }
        }

        // 遍历需要新增的项目增加到待新增列表中
        if (!insertItems.isEmpty() && insertItems != null)
        {
            for (InfectiousMessageContent con : messageContents)
            {
                for (InfectiousItemsforMessage mes : con.getItems())
                {
                    for (String item : insertItems)
                    {
                        InfectiousContent inContent = new InfectiousContent();
                        if (item.equals(mes.getName()))
                        {
                            inContent.setBusinessSn(businessSn);
                            BigDecimal reportContentSn = commonService.getSequence("SEQ_REPORT_CONTENT");
                            inContent.setReportContentSn(reportContentSn);
                            inContent.setReportContentItemCode(mes.getName());
                            inContent.setReportContentItemName(mes.getValue());
                            inContent.setInfectiousName(mes.getValue());
                            inContent.setMedicalType(medicalType);
                            inContent.setCreateTime(curDate);
                            inContent.setUpdateTime(curDate);
                            inContent.setSendOthersFlag("0");
                            inContent.setSendFlag("0");
                            inContent.setUpdateCount(Constants.INSERT_UPDATE_COUNT);
                            inContent.setDeleteFlag(Constants.NO_DELETE_FLAG);
                            for (Map.Entry<String, String> entry : routes.entrySet())
                            {
                                if (mes.getValue().equals(entry.getKey()))
                                {
                                    inContent.setSpreadWay(entry.getValue());
                                }
                            }
                            contentForInsert.add(inContent);
                        }
                    }
                }
            }
        }

        // 遍历要删除的项目并增加到待删除的列表中
        if (!deleteItems.isEmpty() && deleteItems != null)
        {
            for (InfectiousContent ite : content)
            {
                for (String item : deleteItems)
                {
                    if (item.equals(ite.getReportContentItemCode()))
                    {
                        contentForDelete.add(ite);
                    }
                }
            }
        }

        // 新增和删除操作
        if (!contentForInsert.isEmpty() && contentForInsert != null)
        {
            for (InfectiousContent a : contentForInsert)
            {
                commonService.save(a);
                logger.debug("修改了不匹配的项目新增item到DB，由于消息中的item大于DB中!");
            }
        }
        if (!contentForDelete.isEmpty() && contentForDelete != null)
        {
            Date date = new Date();
            for (InfectiousContent con : contentForDelete)
            {
                con.setDeleteFlag(Constants.DELETE_FLAG);
                con.setDeleteTime(date);
                con.setUpdateTime(date);
                commonService.update(con);
                logger.debug("修改了不匹配的项目从DB中删除item，由于消息中的item大于DB中!");
            }
        }
    }

    private void insertOrUpdateSubBusiness(InfectiousRecord inRecord,
            List<InfectiousMessageContent> contents, INFECTIOUSBUILD dto,
            String insertOrUpdate, List<InfectiousContent> content,
            POCDIN000040UV03 pocdin000040uv03)
    {
        //将数据库中delete_flag不为零的去除
        List<InfectiousContent> contentNoDeleteFlag = new ArrayList<InfectiousContent>();
        for(InfectiousContent con : content){
        	if(con.getDeleteFlag().equals(Constants.NO_DELETE_FLAG)){
        		contentNoDeleteFlag.add(con);
        	}
        }
        //保存原队列
       // List<InfectiousContent> contentOld = content;
        content = contentNoDeleteFlag;
        //
    	
    	
    	// 获取传播途径
    	Map<String, String> routes = getInfectiousRoute(dto);
        if (!contents.isEmpty() && contents.size() != 0)
        {

            List<InfectiousMessageContent> messageContents = contents;
            List<InfectiousContent> resultContentList = new ArrayList<InfectiousContent>();
            List<InfectiousContent> resultContentListforUpdate = new ArrayList<InfectiousContent>();
            
            for (InfectiousMessageContent con : messageContents)
            {
                for (InfectiousItemsforMessage mes : con.getItems())
                {
                    if ("update".equals(insertOrUpdate))
                    {
                        for (InfectiousContent cont : content)
                        {
                            if (mes.getName().equals(
                                    cont.getReportContentItemCode()))
                            {
                            	logger.debug("Code:{},Name:{}",mes.getName(),mes.getValue());
                            	logger.debug("Code:{},Name:{}",cont.getReportContentItemCode(),cont.getReportContentItemName());
                                cont.setReportContentItemName(mes.getValue());// 此处寸的是具体传染病名称
                                cont.setInfectiousName(mes.getValue());// 传染病报卡中传染病名称和项目名称相同
                                for (Map.Entry<String, String> entry : routes.entrySet())
                                {
                                    if (mes.getValue().equals(entry.getKey()))
                                    {
                                        cont.setSpreadWay(entry.getValue());
                                    }
                                }
                                Date date = new Date();
                                cont.setUpdateTime(date);
                                resultContentListforUpdate.add(cont);
                                break;
                            }
                        }
                    }
                    InfectiousContent inContent = new InfectiousContent();
                    if ("insert".equals(insertOrUpdate))
                    {
                        inContent.setBusinessSn(inRecord.getBusinessSn());
                        BigDecimal reportContentSn = commonService.getSequence("SEQ_REPORT_CONTENT");
                        inContent.setReportContentSn(reportContentSn);
                        inContent.setReportContentItemCode(mes.getName());
                        inContent.setReportContentItemName(mes.getValue());
                        inContent.setInfectiousName(mes.getValue());
                        inContent.setMedicalType(pocdin000040uv03.getMedicalTypeName());
                        inContent.setCreateTime(sysDate);
                        inContent.setUpdateTime(sysDate);
                        inContent.setSendOthersFlag("0");
                        inContent.setUpdateCount(Constants.INSERT_UPDATE_COUNT);
                        inContent.setDeleteFlag(Constants.NO_DELETE_FLAG);
                        inContent.setSendFlag("0");
                        for (Map.Entry<String, String> entry : routes.entrySet())
                        {
                            if (mes.getValue().equals(entry.getKey()))
                            {
                                inContent.setSpreadWay(entry.getValue());
                            }
                        }
                    }

                    resultContentList.add(inContent);
                }
            }
            if ("insert".equals(insertOrUpdate))
            {
                for (InfectiousContent a : resultContentList)
                {
                    commonService.save(a);
                }
            }
            else if ("update".equals(insertOrUpdate))
            {
                modifyItem(messageContents, content, routes,
                        inRecord.getBusinessSn(),
                        pocdin000040uv03.getMedicalTypeName());
                for (InfectiousContent a : resultContentListforUpdate)
                {
                    commonService.update(a);
                }
            }

        }
        //入院记录中诊断
        else if(pocdin000040uv03.getInDiagnosis().size()>0){
        	List<Diagnosis> diagnosis = pocdin000040uv03.getInDiagnosis();
        	if ("insert".equals(insertOrUpdate)){
        		for(Diagnosis diaObj :diagnosis){
        			if(routes.get(diaObj.getDiseaseCode())!=null){
		    			InfectiousContent inContent = new InfectiousContent();
		    			inContent.setBusinessSn(inRecord.getBusinessSn());
		                BigDecimal reportContentSn = commonService.getSequence("SEQ_REPORT_CONTENT");
		                inContent.setReportContentSn(reportContentSn);
		                inContent.setReportContentItemCode(diaObj.getDiseaseCode());
		                inContent.setReportContentItemName(diaObj.getDiseaseName());
		                inContent.setInfectiousName(diaObj.getDiseaseName());
		                inContent.setMedicalType(pocdin000040uv03.getMedicalTypeName());
		                inContent.setCreateTime(sysDate);
		                inContent.setUpdateTime(sysDate);
		                inContent.setSendOthersFlag("0");
		                inContent.setUpdateCount(Constants.INSERT_UPDATE_COUNT);
		                inContent.setDeleteFlag(Constants.NO_DELETE_FLAG);
		                inContent.setSendFlag("0");
		               	inContent.setSpreadWay(routes.get(diaObj.getDiseaseCode()));
		               
		                commonService.save(inContent);
		                logger.debug("Code:{},Name:{}",diaObj.getDiseaseCode(),diaObj.getDiseaseName());
        			}
            	}
            }
        	else if("update".equals(insertOrUpdate)){
        		List<?> result = null;
        		List<?> logList = null;
        		Map<String, Object> condition = new HashMap<String, Object>();
        		Map<String, Object> conditionLog = new HashMap<String, Object>();
        		Map<String, Object> conditionReport = new HashMap<String, Object>();
        		condition.put("businessSn", inRecord.getBusinessSn());
                result = commonService.selectByCondition(InfectiousContent.class,
                        condition);
                //删除log表
                for(Object obj:result){
                	InfectiousContent ic = (InfectiousContent)obj;
                	//已经发过的短信记录
                	if("1".equals(ic.getSendFlag()))
                	conditionReport.put(ic.getReportContentItemCode(), ic.getReportContentItemName());
                	conditionLog.put("sendReportSn", ic.getReportContentSn());
                	logList = commonService.selectByCondition(InfectiousLog.class,
                			conditionLog);
                	commonService.deleteList(logList);
                }
                
                //删除report表
                commonService.deleteList(result);
                
        		for(Diagnosis diaObj :diagnosis){
        			if(routes.get(diaObj.getDiseaseCode())!=null){
        			InfectiousContent inContent = new InfectiousContent();
        			inContent.setBusinessSn(inRecord.getBusinessSn());
                    BigDecimal reportContentSn = commonService.getSequence("SEQ_REPORT_CONTENT");
                    inContent.setReportContentSn(reportContentSn);
                    inContent.setReportContentItemCode(diaObj.getDiseaseCode());
                    inContent.setReportContentItemName(diaObj.getDiseaseName());
                    inContent.setInfectiousName(diaObj.getDiseaseName());
                    inContent.setMedicalType(pocdin000040uv03.getMedicalTypeName());
                    //将旧记录中的时间信息拷贝到新纪录中
                    boolean hasOldRecord = false;
                    InfectiousContent ic = null;
                    //查找旧记录中是否有对应的记录
                    for(Object obj : result){
                    	ic = (InfectiousContent) obj;
                    	if(diaObj.getDiseaseCode().equals(ic.getReportContentItemCode())){
                    		hasOldRecord = true;
                    		break;
                    	}
                    }
                    if(hasOldRecord && ic != null) {
                    	inContent.setCreateTime(ic.getCreateTime());
                    	inContent.setUpdateTime(ic.getUpdateTime());
                    	inContent.setUpdateCount(ic.getUpdateCount().add(new BigDecimal(1)));
                    	inContent.setSendFlag(ic.getSendFlag());
                    	inContent.setSendDate(ic.getSendDate());
                    } else {
                        inContent.setCreateTime(sysDate);
                        inContent.setUpdateTime(sysDate);
                        inContent.setUpdateCount(Constants.INSERT_UPDATE_COUNT);
                        Object obj = conditionReport.get(diaObj.getDiseaseCode());
                        inContent.setSendFlag(obj!=null&&!"".equals(obj)?"1":"0");
                    }
                    
                    //

                    inContent.setSendOthersFlag("0");
                    inContent.setDeleteFlag(Constants.NO_DELETE_FLAG);                                
                    inContent.setSpreadWay(routes.get(diaObj.getDiseaseCode()));
                    commonService.save(inContent);
                    logger.debug("Code:{},Name:{}",diaObj.getDiseaseCode(),diaObj.getDiseaseName());
        			}
        		}
        	}
        }
    }

    @Transactional
    public void updateRest(List<InfectiousContent> content, String flag,
            String num)
    {
        // inRecord.setUpdateTime(sysDate);
        // inRecord.setIsSendFlag(inRecord.getIsSendFlag()+1);
        // commonService.update(inRecord);

        insertLog(content, num);
        // 更新传染病项目表的发送标志
        for (InfectiousContent con : content)
        {
            Date date = new Date();
            con.setUpdateTime(date);
            if ("2".equals(num))
            {
                if ("空气传播疾病".equals(con.getSpreadWay())
                    || "飞沫传播疾病".equals(con.getSpreadWay()))
                {
                    con.setSendFlag(flag);
                    con.setSendDate(sysDate);
                }
            }
            else if ("5".equals(num))
            {
                con.setSendOthersFlag(flag);
                con.setSendOthersDate(sysDate);
            }
            commonService.update(con);
        }

    }

    private void insertLog(List<InfectiousContent> content, String num)
    {
        // TODO Auto-generated method stub
        for (InfectiousContent con : content)
        {
            if ("2".equals(num))
            {
                if ("空气传播疾病".equals(con.getSpreadWay())
                    || "飞沫传播疾病".equals(con.getSpreadWay()))
                {
                    BigDecimal sendLogSn = commonService.getSequence("SEQ_EXTERNAL_SEND_RECORD");
                    InfectiousLog log = new InfectiousLog();
                    log.setSendLogSn(sendLogSn);
                    log.setSendReportSn(con.getReportContentSn());
                    log.setSendDate(sysDate);
                    log.setSendPersonCode("-1");
                    log.setSendPersonName("后台");
                    log.setCreateTime(sysDate);
                    log.setUpdateTime(sysDate);
                    log.setUpdateCount(Constants.INSERT_UPDATE_COUNT);
                    log.setDeleteFlag(Constants.NO_DELETE_FLAG);
                    commonService.save(log);
                }
            }
            else if ("5".equals(num))
            {
                BigDecimal sendLogSn = commonService.getSequence("SEQ_EXTERNAL_SEND_RECORD");
                InfectiousLog log = new InfectiousLog();
                log.setSendLogSn(sendLogSn);
                log.setSendReportSn(con.getReportContentSn());
                log.setSendDate(sysDate);
                log.setSendPersonCode("-1");
                log.setSendPersonName("后台");
                log.setCreateTime(sysDate);
                log.setUpdateTime(sysDate);
                log.setUpdateCount(Constants.INSERT_UPDATE_COUNT);
                log.setDeleteFlag(Constants.NO_DELETE_FLAG);
                commonService.save(log);
            }
        }
    }

    public void deleteInfectious(InfectiousRecord inRecord,
            List<InfectiousContent> content)
    {
        Date date = new Date();
        inRecord.setDeleteFlag(Constants.DELETE_FLAG);
        inRecord.setDeleteTime(date);
        inRecord.setUpdateTime(date);
        commonService.update(inRecord);
        for (InfectiousContent con : content)
        {
            con.setDeleteFlag(Constants.DELETE_FLAG);
            con.setDeleteTime(date);
            con.setUpdateTime(date);
            commonService.update(con);
        }
    }

    /**
     * 更新传染病业务表
     * @param warningRecord
     */
    @Transactional
    public void updateInfectiousRecord(InfectiousRecord inRecord,
            List<InfectiousContent> content, POCDIN000040UV03 pocdin000040uv03,
            INFECTIOUSBUILD dto, Date reportDate)
    {
//        inRecord.setReportDate(reportDate);
//
////        inRecord.setWardNo(pocdin000040uv03.getWardNo());
//
//        inRecord.setWardCode(pocdin000040uv03.getWardCode());
//
//        inRecord.setPatientName(pocdin000040uv03.getPatientName());
//
////        // Author :jia_yanqing
////        // Date : 2014/03/21
////        // [BUG]0043155 MODIFY BEGIN
////        if("BS335".equals(pocdin000040uv03.getServiceId())){
////        	inRecord.setBusinessType("04");
////        	
////        	inRecord.setBusinessTypeName("入院记录");
////        }else{
////        	inRecord.setBusinessType("01");
////        	
////        	inRecord.setBusinessTypeName("传染病报卡");
////        }
////
////        // [BUG]0043155 MODIFY END
//        
//        //Author : yu_yzh
//        //Date : 2015/05/04
//        //[BUG]55257
//        if("BS335".equals(pocdin000040uv03.getServiceId())){
//        	inRecord.setBusinessType("04");
//        	
//	        inRecord.setBusinessTypeName("入院记录");
//	        //BS335病区，床号，年龄的xpath跟BS358不一致，新建字段
//	        inRecord.setWardNo(pocdin000040uv03.getWardNoBS335());
//	        inRecord.setBedNo(pocdin000040uv03.getBedNoBS335());
//	        inRecord.setAge(pocdin000040uv03.getAgeBS335());
////	        logger.debug("BS335 wardNo: " + pocdin000040uv03.getWardNoBS335());
////	        logger.debug("BS335 bedNo: " + pocdin000040uv03.getBedNoBS335());
////	        logger.debug("BS335 age: " + pocdin000040uv03.getAgeBS335());
//	        inRecord.setVisitDeptName(pocdin000040uv03.getVisitDeptNameBS335());
//	        
//	        //
//        }else{
//	        inRecord.setBusinessType("01");
//	
//	        inRecord.setBusinessTypeName("传染病报卡");
//	        //
//	        inRecord.setWardNo(pocdin000040uv03.getWardNo());
//	        inRecord.setBedNo(pocdin000040uv03.getBedNo());
//	        inRecord.setAge(pocdin000040uv03.getAge());
//	        inRecord.setVisitDeptName(pocdin000040uv03.getVisitDeptName());
//	        //
//        }
//        //[BUG]55257
//        
//        
//        inRecord.setBusinessNo(pocdin000040uv03.getDocumentLid());
//
//        /**
//         * 修改bug55008
//         * 江苏版本domainId无法区分是分诊和住院，用就诊类别“VistType”区分
//         */
//        inRecord.setPatientDomain(!"46600083-8".equals(pocdin000040uv03.getOrganizationCode())?pocdin000040uv03.getPatientDomain():pocdin000040uv03.getVisitTypeCode());
//
//
//        inRecord.setPatientLid(pocdin000040uv03.getPatientLid());
//
////        inRecord.setBedNo(pocdin000040uv03.getBedNo());
//
//        inRecord.setGenderCode(pocdin000040uv03.getGenderCode());
//
//        // 消息中命名为出院号，其实是就诊号，这里无误
//        inRecord.setMedicalRecordNo(pocdin000040uv03.getOutPatientNo());
//
//        inRecord.setGenderName(pocdin000040uv03.getGenderName());
//
////        inRecord.setAge(pocdin000040uv03.getAge());
//
//        inRecord.setBirthDay(DateUtils.stringToDate(pocdin000040uv03.getBirthDay()));
//
//        inRecord.setReportDoctorCode(pocdin000040uv03.getDocumentAuthor());
//
//        inRecord.setReportDoctorName(pocdin000040uv03.getDocumentAuthorName());
//
//        // Author :jia_yanqing
//        // Date : 2014/04/29
//        // [BUG]0044109 MODIFY BEGIN
//        inRecord.setVisitDeptCode(pocdin000040uv03.getVisitDept());
//
////        inRecord.setVisitDeptName(pocdin000040uv03.getVisitDeptName());
//        // [BUG]0044109 MODIFY END
//
//        //江苏存的是就诊流水号
//        inRecord.setVisitTimes(!"46600083-8".equals(pocdin000040uv03.getOrganizationCode())?pocdin000040uv03.getVisitTimes():pocdin000040uv03.getVisitOrdNo());
//
//        Date date = new Date();
//
//        inRecord.setUpdateTime(date);
//
//        inRecord.setLegalStatus(pocdin000040uv03.getLegalStatus());
//
//        inRecord.setDeleteFlag(Constants.NO_DELETE_FLAG);
//
//        inRecord.setOrgCode(pocdin000040uv03.getOrganizationCode());
//
//        inRecord.setOrgName(pocdin000040uv03.getOrganizationName());
//
//        // Author: guo_hongyan
//        // Date: 2014/4/1 13:50
//        // [BUG]0043368 ADD BEGIN
//        inRecord.setModifyDate(DateUtils.stringToDate(pocdin000040uv03.getModifyTime()));
//        // [BUG]0043368 ADD END

        // 更新子表
    	
    	inRecord = setInfectiousRecord(inRecord, reportDate, pocdin000040uv03);
    	
        insertOrUpdateSubBusiness(inRecord,
                pocdin000040uv03.getInContentList(), dto, "update", content,
                pocdin000040uv03);
        commonService.update(inRecord);

    }

    /**
     * 更新传染病业务表--LIS检验
     * @param warningRecord
     */
    @Transactional
    public void updateInfectiousForLab(InfectiousRecord inRecord,
            List<InfectiousContent> content, POCDIN000040UV01 pocdin000040uv01,
            INFECTIOUSBUILD dto, Date reportDate)
    {
        inRecord.setReportDate(reportDate);

        String wardsNo = "";

        if (Constants.SOURCE_LAB.equals(pocdin000040uv01.getSourceSystemId()))
        {
            wardsNo = pocdin000040uv01.getWardsName();
        }
        else if (Constants.SOURCE_MICROBE.equals(pocdin000040uv01.getSourceSystemId()))
        {
            wardsNo = pocdin000040uv01.getWardsNo();
        }

        inRecord.setWardNo(wardsNo);

        // inRecord.setWardCode(pocdin000040uv01.getWardCode());//............................................

        inRecord.setPatientName(pocdin000040uv01.getPatientName());

        inRecord.setSourceSystemId(pocdin000040uv01.getSourceSystemId());

        if (Constants.SOURCE_LAB.equals(pocdin000040uv01.getSourceSystemId()))
        {
            inRecord.setBusinessType("02");

            inRecord.setBusinessTypeName("LIS检验报告");
        }
        else if (Constants.SOURCE_MICROBE.equals(pocdin000040uv01.getSourceSystemId()))
        {
            inRecord.setBusinessType("03");

            inRecord.setBusinessTypeName("微生物检验报告");
        }

        inRecord.setBusinessNo(pocdin000040uv01.getReportNo());// 业务编码

        /**
         * 修改bug55008
         * 江苏版本domainId无法区分是分诊和住院，用就诊类别“VistType”区分
         */
        inRecord.setPatientDomain(!"46600083-8".equals(pocdin000040uv01.getOrgCode())?pocdin000040uv01.getPatientDomain():pocdin000040uv01.getVisitType());

        inRecord.setPatientLid(pocdin000040uv01.getPatientLid());

        inRecord.setBedNo(pocdin000040uv01.getBedNo());

        inRecord.setGenderCode(pocdin000040uv01.getGenderCode());

        inRecord.setGenderName(pocdin000040uv01.getGenderName());

        Date birthday = DateUtils.stringToDate(pocdin000040uv01.getBirthDate());

        Date visitday = DateUtils.stringToDate(pocdin000040uv01.getVisitDate());

        inRecord.setAge(DateUtils.caluAge(birthday, visitday));

        inRecord.setBirthDay(birthday);

        inRecord.setReportDoctorCode(pocdin000040uv01.getReporterId());

        inRecord.setReportDoctorName(pocdin000040uv01.getReporterName());

        // Author :jia_yanqing
        // Date : 2014/04/29
        // [BUG]0044109 MODIFY BEGIN
        inRecord.setVisitDeptCode(pocdin000040uv01.getVisitDept());

        inRecord.setVisitDeptName(pocdin000040uv01.getVisitDeptName());
        // [BUG]0044109 MODIFY END

        //江苏用的是就诊流水号
        inRecord.setVisitTimes(!"46600083-8".equals(pocdin000040uv01.getOrgCode())?pocdin000040uv01.getVisitTimes():pocdin000040uv01.getVisitOrdNo());

        Date date = new Date();

        inRecord.setUpdateTime(date);

        inRecord.setDeleteFlag(Constants.NO_DELETE_FLAG);

        inRecord.setOrgCode(pocdin000040uv01.getOrgCode());

        inRecord.setOrgName(pocdin000040uv01.getOrgName());

        // Author: jia_yanqing
        // Date: 2014/4/1 10:00
        // [BUG]0043368 ADD BEGIN
        inRecord.setModifyDate(DateUtils.stringToDate(pocdin000040uv01.getReviewDate()));
        // [BUG]0043368 ADD END

        // Author: jia_yanqing
        // Date: 2014/3/28 15:00
        // [BUG]0043289 ADD BEGIN
        inRecord.setMedicalRecordNo(pocdin000040uv01.getMedicalNo());
        // [BUG]0043289 ADD END

        if (content != null)
        {
            // 更新子表
        	 if (Constants.SOURCE_LAB.equals(pocdin000040uv01.getSourceSystemId())){
                 insertOrUpdateSubBusinessForlab(inRecord,pocdin000040uv01.getReport(), dto, "update", content);
             }
             else if (Constants.SOURCE_MICROBE.equals(pocdin000040uv01.getSourceSystemId())){
                 insertOrUpdateSubBusinessFormic(inRecord, pocdin000040uv01.getReport(), dto, "update", content);
             }
        }
        else
        {
            logger.debug("预警病信息表为空，不进行更新。");
        }
        commonService.update(inRecord);

    }

    private Map<String, String> getItemName(INFECTIOUSBUILD dto)
    {
        Map<String, String> illNames = new HashMap<String, String>();
        if (dto != null)
        {
            for (InfectiousRules row : dto.getRow())
            {
                boolean thisRowFlag = false;// 本行是否是传染病
                String thisRowIllName = null;// 本行传染病名称
                String thisRowIllCode = null;// 本行药品编码
                // 遍历出checkStatus，看看本行是否是传染病
                for (InfectiousItem item : row.getItem())
                {
                    if ("checkStatus".equals(item.getItemName())
                        && "true".equals(item.getValue()))
                    {// 如果该item是checkStatus，并且为true；那么去拿传染病名称
                        thisRowFlag = true;
                    }
                }
                // 遍历出checkMessage，如果checkStatus是true，则取出传染病名称
                for (InfectiousItem item : row.getItem())
                {
                    if ("checkMessage".equals(item.getItemName())
                        && thisRowFlag)
                    {// 如果该item是checkMessage并且本行中标识为true，则直接取传染病名称
                        thisRowIllName = item.getValue();
                    }
                }
                // 如果本行标识为true，并且本行传染病名称不为空，则将本行的药物标识存为key
                if (thisRowFlag && !StringUtils.isEmpty(thisRowIllName))
                {
                    for (InfectiousItem item : row.getItem())
                    {
                        if ("checkKey".equals(item.getItemName()))
                        {// 如果该item是checkKey，则取药品编码
                            thisRowIllCode = item.getValue();
                        }
                    }
                }
                if (thisRowFlag && !StringUtils.isEmpty(thisRowIllName)
                    && !StringUtils.isEmpty(thisRowIllCode))
                {
                    illNames.put(thisRowIllCode, thisRowIllName);
                }
            }
        }
        return illNames;
    }

    private void insertOrUpdateSubBusinessForlab(InfectiousRecord inRecord,
            List<LabReport> reports, INFECTIOUSBUILD dto,
            String insertOrUpdate, List<InfectiousContent> content)
    {
        if (!reports.isEmpty() && reports.size() != 0)
        {
            // 获取传染病名称
            Map<String, String> illNames = getItemName(dto);
            // 获取传播途径
            Map<String, String> routes = new HashMap<String, String>();
            for (InfectiousRules row : dto.getRow())
            {
                boolean thisRowFlag = false;// 本行是否是传染病
                String thisRowRoute = null;// 本行传染病传播途径
                for (Map.Entry<String, String> entry : illNames.entrySet())
                {// 传播途径赋值
                    for (InfectiousItem item : row.getItem())
                    {
                        if ("checkStatus".equals(item.getItemName())
                            && "true".equals(item.getValue()))
                        {// 如果该item是checkStatus，并且为true；那么去拿传染病名称
                            thisRowFlag = true;
                        }
                    }
                    // 遍历出checkMessage，如果checkStatus是true，则取出传播途径
                    for (InfectiousItem item : row.getItem())
                    {
                        if ("infectionRoute".equals(item.getItemName())
                            && thisRowFlag)
                        {// 如果该item是checkMessage并且本行中标识为true，则直接取传播途径
                            thisRowRoute = item.getValue();
                        }
                    }
                    for (InfectiousItem item : row.getItem())
                    {
                        if ("checkKey".equals(item.getItemName())
                            && item.getValue().equals(entry.getKey())
                            && thisRowFlag
                            && !StringUtils.isEmpty(thisRowRoute))
                        {
                            routes.put(item.getValue(), thisRowRoute);
                        }
                    }
                }
            }

            List<LabReport> labReports = reports;
            List<InfectiousContent> resultContentList = new ArrayList<InfectiousContent>();
            List<InfectiousContent> resultContentListforUpdate = new ArrayList<InfectiousContent>();
            for (LabReport con : labReports)
            {
                for (LabReportResult mes : con.getReportResult())
                {
                    for (Map.Entry<String, String> en : illNames.entrySet())
                    {
                        if (mes.getItemCode().equals(en.getKey()))
                        {
                            if ("update".equals(insertOrUpdate))
                            {
                                for (InfectiousContent cont : content)
                                {
                                    if (mes.getItemCode().equals(
                                            cont.getReportContentItemCode()))
                                    {

                                        cont.setReportContentItemName(mes.getItemNameCn());// lis检验中该项为药物名称
                                        // 添加预警值内容infactResult
                                        cont.setInfectResult(StringUtils.isEmpty(mes.getItemValuePQ()) ? mes.getItemValueSTAndSC()
                                                : mes.getItemValuePQ());
                                        cont.setInfectiousName(en.getValue());// lis检验中该项为传染病名称
                                        for (Map.Entry<String, String> entry : routes.entrySet())
                                        {// 传播途径赋值
                                            if (mes.getItemCode().equals(
                                                    entry.getKey()))
                                            {
                                                cont.setSpreadWay(entry.getValue());
                                            }
                                        }
                                        Date date = new Date();
                                        cont.setUpdateTime(date);
                                        resultContentListforUpdate.add(cont);
                                    }
                                }
                            }
                            InfectiousContent inContent = new InfectiousContent();
                            if ("insert".equals(insertOrUpdate))
                            {
                                inContent.setBusinessSn(inRecord.getBusinessSn());
                                BigDecimal reportContentSn = commonService.getSequence("SEQ_REPORT_CONTENT");
                                inContent.setReportContentSn(reportContentSn);
                                inContent.setReportContentItemCode(mes.getItemCode());
                                inContent.setReportContentItemName(mes.getItemNameCn());// lis检验中该项为药物名称
                                // 添加预警值内容infactResult
                                inContent.setInfectResult(StringUtils.isEmpty(mes.getItemValuePQ()) ? mes.getItemValueSTAndSC()
                                        : mes.getItemValuePQ());
                                inContent.setInfectiousName(en.getValue());// lis检验中该项为传染病名称
                                inContent.setCreateTime(sysDate);
                                inContent.setUpdateTime(sysDate);
                                inContent.setSendOthersFlag("0");
                                inContent.setUpdateCount(Constants.INSERT_UPDATE_COUNT);
                                inContent.setDeleteFlag(Constants.NO_DELETE_FLAG);
                                inContent.setSendFlag("0");
                                for (Map.Entry<String, String> entry : routes.entrySet())
                                {
                                    if (mes.getItemCode().equals(entry.getKey()))
                                    {
                                        inContent.setSpreadWay(entry.getValue());
                                    }
                                }
                            }
                            resultContentList.add(inContent);
                        }
                    }
                }
            }
            if ("insert".equals(insertOrUpdate))
            {
                for (InfectiousContent a : resultContentList)
                {
                    commonService.save(a);
                }
            }
            else if ("update".equals(insertOrUpdate))
            {
                modifyItemForLab(labReports, illNames, content, routes,
                        inRecord.getBusinessSn());
                for (InfectiousContent a : resultContentListforUpdate)
                {
                    commonService.update(a);
                }
            }
        }
    }

    private Map<String, List<String>> illToRoute(INFECTIOUSBUILD dto)
    {
        Map<String, List<String>> illToRoute = new HashMap<String, List<String>>();
        List<String> illAndRoute = null;
        for (InfectiousRules row : dto.getRow())
        {   //每次循环重新new fix by wp
        	illAndRoute = new ArrayList<String>();
            String thisRowMedName = null;// 本行药物名称
            String thisRowIllName = null;// 本行传染病名称
            String thisRowRoute = null;// 本行传染病途径
            String otherthisRowRoute = null;//临时传染病途径
           /* for (InfectiousItem item : row.getItem())
            {
                if (!StringUtils.isEmpty(item.getItemName())
                    && "warningValue".equals(item.getItemName()))
                {
                    thisRowMedName = item.getValue();
                }
            }
            for (InfectiousItem item : row.getItem())
            {
                if (!StringUtils.isEmpty(item.getItemName())
                    && "checkMessage".equals(item.getItemName()))
                {
                    thisRowIllName = item.getValue();
                }
            }
            for (InfectiousItem item : row.getItem())
            {
                if (!StringUtils.isEmpty(item.getItemName())
                    && "infectionRoute".equals(item.getItemName())
                    && !StringUtils.isEmpty(thisRowIllName))
                {
                    thisRowRoute = item.getValue();
                }
            } */
            //归为两个循环 fix by wp
            for (InfectiousItem item : row.getItem())
            {
                if (!StringUtils.isEmpty(item.getItemName())
                    && "warningValue".equals(item.getItemName()))
                {
                    thisRowMedName = item.getValue();
                }
                if (!StringUtils.isEmpty(item.getItemName())
                        && "checkMessage".equals(item.getItemName()))
                {
                    thisRowIllName = item.getValue();
                }
                if (!StringUtils.isEmpty(item.getItemName())
                        && "infectionRoute".equals(item.getItemName()))
                {
                	otherthisRowRoute = item.getValue();
                }
                
                if (!StringUtils.isEmpty(otherthisRowRoute)
                        && !StringUtils.isEmpty(thisRowIllName))
                {
                    thisRowRoute = otherthisRowRoute;
                }
            }
            if (!StringUtils.isEmpty(thisRowMedName)
                && !StringUtils.isEmpty(thisRowIllName)
                && !StringUtils.isEmpty(thisRowRoute))
            {
                illAndRoute.add(thisRowIllName);
                illAndRoute.add(thisRowRoute);
                illToRoute.put(thisRowMedName, illAndRoute);
            }
        }
        return illToRoute;
    }

    private void insertOrUpdateSubBusinessFormic(InfectiousRecord inRecord,
            List<LabReport> reports, INFECTIOUSBUILD dto,
            String insertOrUpdate, List<InfectiousContent> content)
    {
        if (!reports.isEmpty() && reports.size() != 0)
        {
            // 获取传染病名称以及传播途径的组合
            Map<String, List<String>> illAndRoute = illToRoute(dto);
            List<LabReport> labReports = reports;
            List<InfectiousContent> resultContentList = new ArrayList<InfectiousContent>();
            List<InfectiousContent> resultContentListforUpdate = new ArrayList<InfectiousContent>();
            for (LabReport con : labReports)
            {
                for (LabReportResult mes : con.getReportResult())
                {
                    if ("update".equals(insertOrUpdate))
                    {
                        for (InfectiousContent cont : content)
                        {
                            if (mes.getItemCode().equals(
                                    cont.getReportContentItemCode()))
                            {
                                cont.setReportContentItemName(mes.getItemNameEn());// 微生物检验中该项为药物名称
                                for (Map.Entry<String, List<String>> entry : illAndRoute.entrySet())
                                {// 传播途径赋值

                                    if (mes.getItemNameEn().equals(
                                            entry.getKey()))
                                    {
                                        cont.setInfectiousName(entry.getValue().get(
                                                0));// 微生物检验中该项为传染病名称
                                        cont.setSpreadWay(entry.getValue().get(
                                                1));
                                    }
                                }
                                cont.setInfectResult(mes.getItemNameEn());// 微生物为预警值
                                Date date = new Date();
                                cont.setUpdateTime(date);
                                resultContentListforUpdate.add(cont);
                            }
                        }
                    }
                    InfectiousContent inContent = new InfectiousContent();
                    if ("insert".equals(insertOrUpdate))
                    {
                        inContent.setBusinessSn(inRecord.getBusinessSn());
                        BigDecimal reportContentSn = commonService.getSequence("SEQ_REPORT_CONTENT");
                        inContent.setReportContentSn(reportContentSn);
                        inContent.setReportContentItemCode(mes.getItemCode());
                        inContent.setReportContentItemName(mes.getItemNameEn());// lis检验中该项为药物名称
                        inContent.setInfectResult(mes.getItemNameEn());// 微生物为预警值
                        inContent.setCreateTime(sysDate);
                        inContent.setUpdateTime(sysDate);
                        inContent.setSendOthersFlag("0");
                        inContent.setUpdateCount(Constants.INSERT_UPDATE_COUNT);
                        inContent.setDeleteFlag(Constants.NO_DELETE_FLAG);
                        inContent.setSendFlag("0");
                        for (Map.Entry<String, List<String>> entry : illAndRoute.entrySet())
                        {

                            if (mes.getItemNameEn().equals(entry.getKey()))
                            {
                                inContent.setInfectiousName(entry.getValue().get(
                                        0));// 微生物检验中该项为传染病名称
                                inContent.setSpreadWay(entry.getValue().get(1));
                            }
                        }
                    }
                    resultContentList.add(inContent);
                }

            }
            if ("insert".equals(insertOrUpdate))
            {
                for (InfectiousContent a : resultContentList)
                {
                    commonService.save(a);
                }
            }
            else if ("update".equals(insertOrUpdate))
            {
                for (InfectiousContent a : resultContentListforUpdate)
                {
                    commonService.update(a);
                }
                // modifyItemForLab(labReports,illNames,content,routes,inRecord.getBusinessSn());
            }
        }
    }

    private void modifyItemForLab(List<LabReport> labReports,
            Map<String, String> illNames, List<InfectiousContent> content,
            Map<String, String> routes, BigDecimal businessSn)
    {
        List<String> meItems = new ArrayList<String>();
        List<String> dbItems = new ArrayList<String>();
        List<InfectiousContent> contentForInsert = new ArrayList<InfectiousContent>();
        List<InfectiousContent> contentForDelete = new ArrayList<InfectiousContent>();
        List<String> insertItems = new ArrayList<String>();
        List<String> deleteItems = new ArrayList<String>();
        for (InfectiousContent ite : content)
        {
            dbItems.add(ite.getReportContentItemCode());
        }
        for (Map.Entry<String, String> entry : illNames.entrySet())
        {
            meItems.add(entry.getKey());
        }

        if (!meItems.isEmpty() && !dbItems.isEmpty() && meItems != null
            && dbItems != null)
        {
            for (String db : dbItems)
            {
                if (!meItems.contains(db))
                {
                    insertItems.add(db);
                }
            }
            for (String me : meItems)
            {
                if (!dbItems.contains(me))
                {
                    deleteItems.add(me);
                }
            }
        }

        for (Map.Entry<String, String> entry : illNames.entrySet())
        {
            for (String code : insertItems)
            {
                if (code.equals(entry.getKey()))
                {
                    // 找到消息中的这一条，用消息中的值去新增到DB中
                    InfectiousContent inContent = new InfectiousContent();
                    inContent.setBusinessSn(businessSn);
                    BigDecimal reportContentSn = commonService.getSequence("SEQ_REPORT_CONTENT");
                    inContent.setReportContentSn(reportContentSn);
                    inContent.setReportContentItemCode(entry.getKey());
                    for (LabReport con : labReports)
                    {
                        for (LabReportResult mes : con.getReportResult())
                        {
                            if (mes.getItemCode().equals(entry.getKey()))
                            {
                                inContent.setReportContentItemName(mes.getItemNameCn());
                                // 添加预警值内容infactResult
                                inContent.setInfectResult(StringUtils.isEmpty(mes.getItemValuePQ()) ? mes.getItemValueSTAndSC()
                                        : mes.getItemValuePQ());
                            }
                        }
                    }
                    inContent.setInfectiousName(entry.getValue());
                    // inContent.setMedicalType(medicalType);
                    inContent.setCreateTime(sysDate);
                    inContent.setUpdateTime(sysDate);
                    // 以下四个是要从旧的DB的条目中替换过来的内容
                    inContent.setSendOthersFlag("0");
                    inContent.setSendFlag("0");
                    // inContent.setSendDate(sendDate);
                    // inContent.setSendOthersDate(sendOthersDate);
                    //
                    inContent.setUpdateCount(Constants.INSERT_UPDATE_COUNT);
                    inContent.setDeleteFlag(Constants.NO_DELETE_FLAG);
                    for (Map.Entry<String, String> en : routes.entrySet())
                    {
                        if (entry.getKey().equals(en.getKey()))
                        {
                            inContent.setSpreadWay(en.getValue());
                        }
                    }
                    contentForInsert.add(inContent);
                }
            }
        }
        // 遍历要删除的项目并增加到待删除的列表中
        if (!deleteItems.isEmpty() && deleteItems != null)
        {
            for (InfectiousContent ite : content)
            {
                for (String item : deleteItems)
                {
                    if (item.equals(ite.getReportContentItemCode()))
                    {
                        contentForDelete.add(ite);
                    }
                }
            }
        }

        // 新增和删除操作
        if (!contentForInsert.isEmpty() && contentForInsert != null)
        {
            for (InfectiousContent a : contentForInsert)
            {
                commonService.save(a);
                logger.debug("修改了不匹配的项目新增item到DB，由于消息中的item大于DB中!");
            }
        }
        if (!contentForDelete.isEmpty() && contentForDelete != null)
        {
            Date date = new Date();
            for (InfectiousContent con : contentForDelete)
            {
                con.setDeleteFlag(Constants.DELETE_FLAG);
                con.setDeleteTime(date);
                con.setUpdateTime(date);
                commonService.update(con);
                logger.debug("修改了不匹配的项目从DB中删除item，由于消息中的item大于DB中!");
            }
        }

    }

    /**
     * 根据警告通知查询系统队列名称表中的系统ID，拼接成一个字符串返回
     * @param warnNotices
     * @return
     */
    public Map<String, String> getAddress(List<WarningNotice> warnNotices,
            String weekday)
    {
        Map<String, String> telEmailSys = new HashMap<String, String>();

        if (warnNotices != null && !warnNotices.isEmpty())
        {
            StringBuffer mobileString = new StringBuffer();

            StringBuffer sysString = new StringBuffer();

            StringBuffer emailString = new StringBuffer();

            for (WarningNotice warningNotice : warnNotices)
            {
                List<WarningNoticeDetail> warnNoticeDetails = notice.selectWarnNoticeDetail(warningNotice.getWarningNoticeSn());

                if (warnNoticeDetails != null && !warnNoticeDetails.isEmpty())
                {
                    for (WarningNoticeDetail noticeDetail : warnNoticeDetails)
                    {
                        String noticeType = noticeDetail.getNoticeType();

                        if ("1".equals(noticeType))
                        {
                            String flag = getWeekdayFlag(weekday, noticeDetail);

                            if ("1".equals(flag))
                            {
                                mobileString.append(noticeDetail.getSettingValue()
                                    + "/");
                            }
                        }
                        else if ("2".equals(noticeType))
                        {
                            emailString.append(noticeDetail.getSettingValue()
                                + ",");
                        }
                        else if ("3".equals(noticeType))
                        {
                            sysString.append(noticeDetail.getSettingValue()
                                + ",");
                        }
                    }

                }
            }

            String mobile = mobileString.toString();

            String system = sysString.toString();

            String email = emailString.toString();

            if (!StringUtils.isEmpty(mobile))
            {
                mobile = mobile.substring(0, mobile.length() - 1);
            }

            if (!StringUtils.isEmpty(system))
            {
                system = system.substring(0, system.length() - 1);
            }

            if (!StringUtils.isEmpty(email))
            {
                email = email.substring(0, email.length() - 1);
            }

            telEmailSys.put("mobileAddress", mobile);

            telEmailSys.put("systemId", system);

            telEmailSys.put("mailAddress", email);

            return telEmailSys;

        }
        else
        {
            logger.error("警告通知集合为空！");

            return null;
        }
    }

    private String getWeekdayFlag(String weekday,
            WarningNoticeDetail noticeDetail)
    {
        String flag = null;

        if ("1".equals(weekday))
        {
            flag = noticeDetail.getMonFlag();
        }
        else if ("2".equals(weekday))
        {
            flag = noticeDetail.getTusFlag();
        }
        else if ("3".equals(weekday))
        {
            flag = noticeDetail.getWedFlag();
        }
        else if ("4".equals(weekday))
        {
            flag = noticeDetail.getThuFlag();
        }
        else if ("5".equals(weekday))
        {
            flag = noticeDetail.getFriFlag();
        }
        else if ("6".equals(weekday))
        {
            flag = noticeDetail.getSatFlag();
        }
        else if ("7".equals(weekday))
        {
            flag = noticeDetail.getSunFlag();
        }

        return flag;
    }

    /**
     * 解析系统队列名称集合，将系统ID解析，并返回String，其中用“，”隔开
     * @param systemQueueNames 系统队列名称集合
     * @return 
     */
    /*
     * public String resolveSysQueNameList(List<SystemQueueName>
     * systemQueueNames) { if (systemQueueNames != null &&
     * !systemQueueNames.isEmpty()) { StringBuffer buffer = new StringBuffer();
     * 
     * for (int i = 0; i < systemQueueNames.size(); i++) { SystemQueueName
     * systemQueueName = systemQueueNames.get(i);
     * 
     * if (!StringUtils.isEmpty(systemQueueName.getSystemId())) {
     * buffer.append(systemQueueName.getSystemId());
     * 
     * if (i < systemQueueNames.size() - 1) { buffer.append(","); } } } return
     * buffer.toString(); } else { logger.error("系统队列集合为空！"); return null; }
     * 
     * }
     */

    public BigDecimal getLabReportSn()
    {
        return labReportSn;
    }

    // $Author :jin_peng
    // $Date : 2013/04/22 09:40$
    // [BUG]0014747 ADD BEGIN
    /**
     * 查询警告通知检验具体结果异常或危机内容
     * @param labReportLid 报告本地id
     * @param sourceSystemId 源系统id
     * @return 警告通知检验具体结果异常或危机内容
     */
    public List<LabDto> selectWarningLabResultItem(String labReportLid,
            String sourceSystemId, String orgCode)
    {
        return labService.selectWarningLabResultItem(labReportLid,
                sourceSystemId, orgCode);
    }

    @Transactional
    public void saveRuleSign(POCDIN000040UV01 t,
            WARNINGBUILD resultUncommonDto, WARNINGBUILD resultCrisisDto,
            Date systemTime, WarningRecord warningRecord, Logger sendLogger)
    {
        // 4返回结果DB保存
        updateLabResultItem(t, resultUncommonDto, resultCrisisDto, systemTime);

        saveOrUpdateWarningRecord(warningRecord, sendLogger, t);
    }

    public void saveOrUpdateWarningRecord(WarningRecord warningRecord,
            Logger sendLogger, POCDIN000040UV01 t)
    {
        // 新增或更新警告记录表
        if (warningRecord.getWarningRecordSn() != null)
        {
            sendLogger.debug("将该检验报告警告通知运行情况存入DB，状态为更新。。。");

            // 更新警告记录
            updateWarnRecord(warningRecord);

            sendLogger.debug("将该检验报告警告通知运行情况存入DB，success。");
        }
        else
        {
            sendLogger.debug("将该检验报告警告通知运行情况存入DB，状态为新增。。。");

            // 新增警告记录
            insertWarnRecord(warningRecord, t.getVersionNumber(),
                    DateUtils.stringToDate(t.getReportDate()));

            sendLogger.debug("将该检验报告警告通知运行情况存入DB，success.");
        }
    }

    /**
     * 获取患者记录
     * @param patientSn 患者内部序列号
     * @return 患者记录
     */
    public Patient getPatient(BigDecimal patientSn)
    {
        Patient patient = null;

        if (patientSn != null)
        {
            patient = labService.getPatient(patientSn);
        }

        return patient;
    }

    public MedicalVisit getMedicalVisit(BigDecimal visitSn)
    {
        MedicalVisit medicalVisit = null;

        if (visitSn != null)
        {
            medicalVisit = labService.getMedicalVisit(visitSn);
        }

        return medicalVisit;
    }

    /**
     * 查询传染病预警业务记录表
     * @param labReportSn 检验报告内部序列号
     * @return
     */
    public boolean isExist(String patientLid, String patientDomain,
            String orgCode, String visitTimes, String infectiousName)
    {
        return notice.isExist(patientLid, patientDomain, orgCode, visitTimes,
                infectiousName);
    }

    public Date getSysDate()
    {
        return sysDate;
    }

    public void setSysDate(Date sysDate)
    {
        this.sysDate = sysDate;
    }

    public BigDecimal getPatientSn()
    {
        return patientSn;
    }

    public void setPatientSn(BigDecimal patientSn)
    {
        this.patientSn = patientSn;
    }

    public BigDecimal getVisitSn()
    {
        return visitSn;
    }

    public void setVisitSn(BigDecimal visitSn)
    {
        this.visitSn = visitSn;
    }

    // [BUG]0014747 ADD END

    /**
     * Author: yu_yzh
     * Date: 2015/5/8
     * 将insertInfectiousRecord跟updateInfectiousRecord中公共部分提取出来
     * */
    public InfectiousRecord setInfectiousRecord(InfectiousRecord inRecord, Date reportDate,
            POCDIN000040UV03 pocdin000040uv03) {
    	
    	inRecord.setReportDate(reportDate);
    	inRecord.setPatientName(pocdin000040uv03.getPatientName());
        //Author : yu_yzh
        //Date : 2015/05/04
        //[BUG]55257
        if("BS335".equals(pocdin000040uv03.getServiceId())){
        	inRecord.setBusinessType("04");
        	
	        inRecord.setBusinessTypeName("入院记录");
	        //BS335病区，床号，年龄的xpath跟BS358不一致，新建字段
	        inRecord.setWardNo(pocdin000040uv03.getWardNoBS335());
	        inRecord.setBedNo(pocdin000040uv03.getBedNoBS335());
	        inRecord.setAge(pocdin000040uv03.getAgeBS335());
	        inRecord.setVisitDeptName(pocdin000040uv03.getVisitDeptNameBS335());
	        inRecord.setVisitDeptCode(pocdin000040uv03.getVisitDeptBS335());
	        inRecord.setWardCode(pocdin000040uv03.getWardCodeBS335());
	        //
        }else{
	        inRecord.setBusinessType("01");
	
	        inRecord.setBusinessTypeName("传染病报卡");
	        //
	        inRecord.setWardNo(pocdin000040uv03.getWardNo());
	        inRecord.setBedNo(pocdin000040uv03.getBedNo());
	        inRecord.setAge(pocdin000040uv03.getAge());
	        inRecord.setVisitDeptName(pocdin000040uv03.getVisitDeptName());
	        inRecord.setVisitDeptCode(pocdin000040uv03.getVisitDept());
	        inRecord.setWardCode(pocdin000040uv03.getWardCode());
	        //
        }
        //[BUG]55257
        
        inRecord.setBusinessNo(pocdin000040uv03.getDocumentLid());

        /**
         * 修改bug55008
         * 江苏版本domainId无法区分是分诊和住院，用就诊类别“VistType”区分
         */
        inRecord.setPatientDomain(!"46600083-8".equals(pocdin000040uv03.getOrganizationCode())?pocdin000040uv03.getPatientDomain():pocdin000040uv03.getVisitTypeCode());


        inRecord.setPatientLid(pocdin000040uv03.getPatientLid());
      //做BS335跟BS358的区分
//        inRecord.setBedNo(pocdin000040uv03.getBedNo());

        inRecord.setGenderCode(pocdin000040uv03.getGenderCode());

        // 消息中命名为出院号，其实是就诊号，这里无误
        inRecord.setMedicalRecordNo(pocdin000040uv03.getOutPatientNo());

        inRecord.setGenderName(pocdin000040uv03.getGenderName());

        inRecord.setBirthDay(DateUtils.stringToDate(pocdin000040uv03.getBirthDay()));

        inRecord.setReportDoctorCode(pocdin000040uv03.getDocumentAuthor());

        inRecord.setReportDoctorName(pocdin000040uv03.getDocumentAuthorName());

        // Author :jia_yanqing
        // Date : 2014/04/29
        // [BUG]0044109 MODIFY BEGIN
//        inRecord.setVisitDeptCode(pocdin000040uv03.getVisitDept());
//        inRecord.setVisitDeptName(pocdin000040uv03.getVisitDeptName());
        // [BUG]0044109 MODIFY END

        //江苏用的是就诊流水号
        inRecord.setVisitTimes(!"46600083-8".equals(pocdin000040uv03.getOrganizationCode())?pocdin000040uv03.getVisitTimes():pocdin000040uv03.getVisitOrdNo());

//        inRecord.setWardCode(pocdin000040uv03.getWardCode());

        inRecord.setCreateTime(sysDate);

        inRecord.setUpdateTime(sysDate);
        String legal = pocdin000040uv03.getLegalStatus();

        inRecord.setLegalStatus(legal);

        inRecord.setUpdateCount(Constants.INSERT_UPDATE_COUNT);

        inRecord.setDeleteFlag(Constants.NO_DELETE_FLAG);

        inRecord.setOrgCode(pocdin000040uv03.getOrganizationCode());

        inRecord.setOrgName(pocdin000040uv03.getOrganizationName());

        // Author: guo_hongyan
        // Date: 2014/4/1 13:50
        // [BUG]0043368 ADD BEGIN
        inRecord.setModifyDate(DateUtils.stringToDate(pocdin000040uv03.getModifyTime()));
        // [BUG]0043368 ADD END
      
    	return inRecord;
    }
    
    
    
}
