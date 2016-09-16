/**
 * Copryright
 */
package com.founder.cdr.batch.writer.pocdin000040uv01;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.slf4j.Logger;
import org.springframework.stereotype.Component;

import com.founder.cdr.core.Constants;
import com.founder.cdr.hl7.dto.LabReport;
import com.founder.cdr.hl7.dto.LabSample;
import com.founder.cdr.hl7.dto.PreventAllergyDrug;
import com.founder.cdr.hl7.dto.pocdin000040uv01.POCDIN000040UV01;
import com.founder.cdr.util.StringUtils;

/**
 * 检验报告数据接入验证功能
 * @author jin_peng
 */
@Component
public class POCDIN000040UV01Validation
{
    /**
     * 检验报告在新增或更新场景下非空值验证
     */
    public boolean validate(POCDIN000040UV01 pocdin000040uv01, Logger logger, Logger loggerBS319, Logger loggerBS354)
    {
        boolean b = true;
        List<String> validateList = null;
        // 测试用日志
        Map<String, String> logMap = null;

        // 微生物检验报告执行科室取值与普通检验报告不同
        if(Constants.SOURCE_MICROBE.equals(pocdin000040uv01.getSourceSystemId())){
        	pocdin000040uv01.setLabDept(pocdin000040uv01.getLabDeptBS354());
        	pocdin000040uv01.setLabDeptName(pocdin000040uv01.getLabDeptNameBS354());
        }
        
        if (Constants.VERSION_NUMBER_INSERT.equals(pocdin000040uv01.getVersionNumber())
            || Constants.VERSION_NUMBER_UPDATE.equals(pocdin000040uv01.getVersionNumber()))
        {
            validateList = new ArrayList<String>();
            logMap = new HashMap<String, String>();

            // Author :jia_yanqing
            // Date : 2013/1/22 15:00
            // [BUG]0042085 MODIFY BEGIN
            //String headOrgCode = pocdin000040uv01.getMessage().getOrgCode();
            String orgCode = pocdin000040uv01.getOrgCode();
            //String orgName = pocdin000040uv01.getOrgName();

            // 医疗机构代码
            /*
            validateList.add(orgCode);
            logMap.put("医疗机构代码不能为空！", orgCode);

            // 医疗机构名称
            validateList.add(orgName);
            logMap.put("医疗机构名称不能为空！", orgName);
            */
            
            if(orgCode == null || "".equals(orgCode))
            {
                pocdin000040uv01.setOrgCode(Constants.HOSPITAL_CODE);
                pocdin000040uv01.setOrgName(Constants.HOSPITAL_NAME);
            }
            // [BUG]0042085 MODIFY BEGIN

            // 执行科室
            validateList.add(pocdin000040uv01.getLabDept());
            logMap.put("执行科室不能为空！", pocdin000040uv01.getLabDept());

            // 文档名称
            validateList.add(pocdin000040uv01.getDocumentName());
            logMap.put("文档名称不能为空！", pocdin000040uv01.getDocumentName());

            // 送检医生
            validateList.add(pocdin000040uv01.getApplyPerson());
            logMap.put("送检医生编码不能为空！", pocdin000040uv01.getApplyPerson());

            // 送检医生姓名
            validateList.add(pocdin000040uv01.getApplyPersonName());
            logMap.put("送检医生姓名不能为空！", pocdin000040uv01.getApplyPersonName());

            // 申请时间
            validateList.add(pocdin000040uv01.getRequestTime());
            logMap.put("申请时间不能为空！", pocdin000040uv01.getRequestTime());

            // 报告时间
            validateList.add(pocdin000040uv01.getReportDate());
            logMap.put("报告时间不能为空！", pocdin000040uv01.getReportDate());

            // 报告人ID
            validateList.add(pocdin000040uv01.getReporterId());
            logMap.put("报告人ID不能为空！", pocdin000040uv01.getReporterId());

            // 报告人姓名
            validateList.add(pocdin000040uv01.getReporterName());
            logMap.put("报告人姓名不能为空！", pocdin000040uv01.getReporterName());

            // 审核时间
            validateList.add(pocdin000040uv01.getReviewDate());
            logMap.put("审核时间不能为空！", pocdin000040uv01.getReviewDate());

            // 审核人ID
            validateList.add(pocdin000040uv01.getReviewerId());
            logMap.put("审核人ID不能为空！", pocdin000040uv01.getReviewerId());

            // 审核人姓名
            validateList.add(pocdin000040uv01.getReviewerName());
            logMap.put("审核人姓名不能为空！", pocdin000040uv01.getReviewerName());

            // 检验科室名称
            validateList.add(pocdin000040uv01.getLabDeptName());
            logMap.put("检验科室名称不能为空！", pocdin000040uv01.getLabDeptName());

            // $Author :jin_peng
            // $Date : 2012/07/26 14:00$
            // [BUG]0010366 ADD BEGIN
            // 微生物检验报告需要验证报告类型编码和名称是否为空
            if (Constants.SOURCE_MICROBE.equals(pocdin000040uv01.getSourceSystemId()))
            {
                validateList.add(pocdin000040uv01.getReportTypeCode());
                logMap.put("报告类型编码不能为空！", pocdin000040uv01.getReportTypeCode());

                validateList.add(pocdin000040uv01.getReportTypeName());
                logMap.put("报告类型名称不能为空！", pocdin000040uv01.getReportTypeName());
            }

            // [BUG]0010366 ADD END

            // $Author :jin_peng
            // $Date : 2012/09/20 14:47$
            // [BUG]0009691 DELETE BEGIN
            // 获取检验医嘱现不用本地医嘱号
            // 医嘱编码
            // validateList.add(pocdin000040uv01.getOrderNo().get(0));
            // logMap.put("医嘱编码不能为空！", pocdin000040uv01.getOrderNo().get(0));
            // [BUG]0009691 DELETE END

            for (LabSample labSample : pocdin000040uv01.getSample())
            {
                // $Author :tong_meng
                // $Date : 2013/08/09 13:51$
                // [BUG]0036057 DELETE BEGIN
                /*// 采样时间
                validateList.add(labSample.getEffectiveTime());
                logMap.put("采样时间不能为空！", labSample.getEffectiveTime());*/
                // [BUG]0036057 DELETE END

                // 接收时间
                validateList.add(labSample.getReceiveTime());
                logMap.put("接收时间不能为空！", labSample.getReceiveTime());

                // 标本号
                validateList.add(labSample.getSampleNo());
                logMap.put("标本号不能为空！", labSample.getSampleNo());

                // 标本类型编码
                validateList.add(labSample.getSampleType());
                logMap.put("标本类型编码不能为空！", labSample.getSampleType());

                // 标本类型名称
                validateList.add(labSample.getSampleTypeName());
                logMap.put("标本类型名称不能为空！", labSample.getSampleTypeName());

            }

            if (Constants.SOURCE_LAB.equals(pocdin000040uv01.getSourceSystemId()))
            {
                for (LabSample labSample : pocdin000040uv01.getSample())
                {
                    // $Author :tong_meng
                    // $Date : 2013/08/09 13:51$
                    // [BUG]0036057 DELETE BEGIN
                    /*
                     * // 采集人编码 validateList.add(labSample.getCollectionId());
                     * logMap.put("采集人编码不能为空！", labSample.getCollectionId());
                     * 
                     * // 采集人名称 validateList.add(labSample.getCollectionName());
                     * logMap.put("采集人名称不能为空！", labSample.getCollectionName());
                     */
                    // [BUG]0036057 DELETE END

                    // $Author :chang_xuewen
                    // $Date : 2013/08/22 15:00$
                    // [BUG]0036498 DELETE BEGIN
                    // 采集地址编码
                    /*
                     * validateList.add(labSample.getCollectionPlace());
                     * logMap.put("采集地址编码不能为空！",
                     * labSample.getCollectionPlace());
                     * 
                     * // 采集地址名称
                     * validateList.add(labSample.getCollectionPlaceName());
                     * logMap.put("采集地址名称不能为空！",
                     * labSample.getCollectionPlaceName());
                     */
                    // [BUG]0036498 DELETE END

                    // 标本接收人编码
                    validateList.add(labSample.getReceiveId());
                    logMap.put("标本接收人编码不能为空！", labSample.getReceiveId());

                    // 标本接收人名称
                    validateList.add(labSample.getReceiveName());
                    logMap.put("标本接收人名称不能为空！", labSample.getReceiveName());
                }
                // $Author:wei_peng
                // $Date:2013/01/18 10:51
                // $[BUG]0013491 DELETE BEGIN
                /*
                 * for (Diagnosis diagnosis : pocdin000040uv01.getDiagnosises())
                 * { // 诊断类别编码 validateList.add(diagnosis.getDiagnosisType());
                 * logMap.put("诊断类别编码不能为空！", diagnosis.getDiagnosisType());
                 * 
                 * // 疾病编码 validateList.add(diagnosis.getDiseaseCode());
                 * logMap.put("疾病编码不能为空！", diagnosis.getDiseaseCode());
                 * 
                 * // 疾病名称 validateList.add(diagnosis.getDiseaseName());
                 * logMap.put("疾病名称不能为空！", diagnosis.getDiseaseName()); }
                 */
                // $[BUG]0013491 DELETE END
            }

            List<LabReport> labReportList = pocdin000040uv01.getReport();
            for (int i = 0; i < labReportList.size(); i++)
            {
                if (Constants.SOURCE_MICROBE.equals(pocdin000040uv01.getSourceSystemId())){
                    // 检验大项名称
                    validateList.add(labReportList.get(i).getLabItemName());
                    logMap.put("检验大项名称   {" + i + "} 不能为空！",
                            labReportList.get(i).getLabItemName());
                }
                
                // 检验大项编码
                validateList.add(labReportList.get(i).getLabItemCode());
                logMap.put("检验项目编码 {" + i + "}不能为空！",
                        labReportList.get(i).getLabItemCode());                    

                // $Author :jin_peng
                // $Date : 2012/07/27 16:37$
                // [BUG]0008030 MODIFY BEGIN
                // 根据不同系统分别判断
                if (labReportList.get(i).getReportResult() != null
                    && !labReportList.get(i).getReportResult().isEmpty())
                {
                    for (int j = 0; j < labReportList.get(i).getReportResult().size(); j++)
                    {
                        if(Constants.LAB_REPORT_TYPE_CODE_CULTIVATE.equals(pocdin000040uv01.getReportTypeCode())){
                            // Author :jia_yanqing
                            // Date : 2014/4/9 9:00
                            // [BUG]0043556 MODIFY BEGIN
                            // 检验结果项目编码
                            validateList.add(labReportList.get(i).getReportResult().get(
                                    j).getItemUnitSC());
                            logMap.put(
                                    "检验结果项目编码   {" + j + "} 不能为空！",
                                    labReportList.get(i).getReportResult().get(j).getItemUnitSC());
                            // [BUG]0043556 MODIFY END
                        }else{
                            // 检验结果项目编码
                            validateList.add(labReportList.get(i).getReportResult().get(
                                    j).getItemCode());
                            logMap.put(
                                    "检验结果项目编码   {" + j + "} 不能为空！",
                                    labReportList.get(i).getReportResult().get(j).getItemCode());                            
                        }

                        // 显示序号
                        validateList.add(labReportList.get(i).getReportResult().get(
                                j).getDisplayOrder());
                        logMap.put(
                                "显示序号   {" + j + "} 不能为空！",
                                labReportList.get(i).getReportResult().get(j).getDisplayOrder());
                        // 判断检验报告类型以决定非空字段的判断
                        if (Constants.SOURCE_LAB.equals(pocdin000040uv01.getSourceSystemId()))
                        {
                            // 项目中文名
                            validateList.add(labReportList.get(i).getReportResult().get(
                                    j).getItemNameCn());
                            logMap.put(
                                    "项目中文名   {" + j + "} 不能为空！",
                                    labReportList.get(i).getReportResult().get(
                                            j).getItemNameCn());

                            // Author :jia_yanqing
                            // Date : 2013/1/30 18:00
                            // [BUG]0013725 MODIFY BEGIN
                            // 检验结果
                            String itemValuePQ = labReportList.get(i).getReportResult().get(
                                    j).getItemValuePQ();
                            String itemValueSTAndSC = labReportList.get(i).getReportResult().get(
                                    j).getItemValueSTAndSC();
                            
                            String qualitativeResults = labReportList.get(i).getReportResult().get(
                                    j).getQualitativeResults();
                            if ((itemValuePQ == null || itemValuePQ.isEmpty())&&(itemValueSTAndSC == null || itemValueSTAndSC.isEmpty())
                                && (qualitativeResults == null || qualitativeResults.isEmpty()))
                            // [BUG]0013725 MODIFY END
                            {
                                validateList.add(null);
                                logMap.put("检验结果   {" + j + "} 不能为空！", null);
                            }
                        }
                        else if (Constants.SOURCE_MICROBE.equals(pocdin000040uv01.getSourceSystemId()))
                        {
                            if(!Constants.LAB_REPORT_TYPE_CODE_CULTIVATE.equals(pocdin000040uv01.getReportTypeCode())){
                                // 项目中文名
                                validateList.add(labReportList.get(i).getReportResult().get(
                                        j).getItemNameEn());
                                logMap.put(
                                        "项目中文名   {" + j + "} 不能为空！",
                                        labReportList.get(i).getReportResult().get(
                                                j).getItemNameEn());                                
                            }
                            // 如果是药敏，检验结果可以为空
                            if(!Constants.LAB_REPORT_TYPE_CODE_DRUGALLERGY.equals(pocdin000040uv01.getReportTypeCode()))
                            {
                                // 检验结果
                                validateList.add(labReportList.get(i).getReportResult().get(
                                        j).getLabResult());
                                logMap.put(
                                        "检验结果   {" + j + "} 不能为空！",
                                        labReportList.get(i).getReportResult().get(
                                                j).getLabResult());
                            }
                        }

                        for (PreventAllergyDrug preventAllergyDrug : labReportList.get(
                                i).getReportResult().get(j).getPreventAllergyDrug())
                        {
                            // 抗菌药物-显示序号
                            validateList.add(preventAllergyDrug.getSequenceNo());
                            logMap.put("抗菌药物-显示序号不能为空！",
                                    preventAllergyDrug.getSequenceNo());
                            // 抗菌药物-药物编码
                            validateList.add(preventAllergyDrug.getDrugCode());
                            logMap.put("抗菌药物-药物编码不能为空！",
                                    preventAllergyDrug.getDrugCode());
                            // 抗菌药物-药物名称
                            validateList.add(preventAllergyDrug.getDrugNameCn());
                            logMap.put("抗菌药物-药物名称不能为空！",
                                    preventAllergyDrug.getDrugNameCn());
                            // 抗菌药物-药物英文名称
/*                            validateList.add(preventAllergyDrug.getDrugNameEn());
                            logMap.put("抗菌药物-药物英文名称不能为空！",
                                    preventAllergyDrug.getDrugNameEn());*/
                        }
                    }
                }
                else
                {
                    if (labReportList.get(i).getSmearMessage() != null
                        && !labReportList.get(i).getSmearMessage().isEmpty())
                    {
                        // 检验结果项目编码(涂片结果编码)
                        for (int j = 0; j < labReportList.get(i).getSmearMessage().size(); j++)
                        {
                            validateList.add(labReportList.get(i).getSmearMessage().get(
                                    j).getSmearResultCode());
                            logMap.put(
                                    "涂片结果-检验结果项目编码 {" + j + "} 不能为空！",
                                    labReportList.get(i).getSmearMessage().get(
                                            j).getSmearResultCode());

                            validateList.add(labReportList.get(i).getSmearMessage().get(
                                    j).getSequenceNumber());
                            logMap.put(
                                    "涂片结果-显示序号 {" + j + "} 不能为空！",
                                    labReportList.get(i).getSmearMessage().get(
                                            j).getSequenceNumber());

                            validateList.add(labReportList.get(i).getSmearMessage().get(
                                    j).getSmearResultName());
                            logMap.put(
                                    "涂片结果-项目名称 {" + j + "} 不能为空！",
                                    labReportList.get(i).getSmearMessage().get(
                                            j).getSmearResultName());

                            /*validateList.add(labReportList.get(i).getSmearMessage().get(
                                    j).getRemark());
                            logMap.put(
                                    "涂片结果-备注名称{" + j + "} 不能为空！",
                                    labReportList.get(i).getSmearMessage().get(
                                            j).getRemark());*/
                        }
                    }
                }
                // [BUG]0008030 MODIFY BEGIN
            }

            String[] str = new String[validateList.size()];
            b = StringUtils.isNotNullAll(validateList.toArray(str));

            //logger.debug("新增或更新时是否所有必填项都有值: {}", b);

            // 测试所用非空验证日志
            if (b == false)
            {
                Set<Entry<String, String>> entries = logMap.entrySet();
                Iterator<Entry<String, String>> iterator = entries.iterator();

                StringBuilder validateMessages = new StringBuilder();
                while (iterator.hasNext())
                {
                    Entry<String, String> entry = iterator.next();

                    if (StringUtils.isEmpty(entry.getValue()))
                    {
                        logger.error("{} \n", entry.getKey());
                        validateMessages.append(entry.getKey());
                    }
                }
                if(Constants.SOURCE_LAB.equals(pocdin000040uv01.getSourceSystemId())){
                    // 检验报告消息日志
                    loggerBS319.error("Message:{}, validate:{}",
                            pocdin000040uv01.toString(), validateMessages.toString());                    
                }else{
                    // 微生物检验报告消息日志
                    loggerBS354.error("Message:{}, validate:{}",
                            pocdin000040uv01.toString(),
                            validateMessages.toString());
                }

                return b;
            }

            // Author :jia_yanqing
            // Date : 2013/1/22 15:00
            // [BUG]0042085 DELETE BEGIN
            /*if (!headOrgCode.equals(orgCode))
            {
                b = false;

                if (Constants.SOURCE_LAB.equals(pocdin000040uv01.getSourceSystemId()))
                {
                    // 医疗机构验证未通过
                    loggerBS319.error("Message:{}, {}",
                            pocdin000040uv01.toString(),
                            "消息体中与消息头中医疗机构代码不相同。orgCode=" + orgCode
                                + "; headOrgCode=" + headOrgCode);
                }
                else
                {
                    // 医疗机构验证未通过
                    loggerBS354.error("Message:{}, {}",
                            pocdin000040uv01.toString(),
                            "消息体中与消息头中医疗机构代码不相同。orgCode=" + orgCode
                                + "; headOrgCode=" + headOrgCode);
                }
            }*/
            // [BUG]0042085 DELETE BEGIN
        }

        return b;
    }

}
