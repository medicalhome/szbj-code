/**
 * Copryright
 */
package com.founder.cdr.batch.writer.pocdin000040uv01;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.codec.binary.Base64;
import org.springframework.stereotype.Component;

import com.founder.cdr.core.Constants;
import com.founder.cdr.entity.ClinicalDocument;
import com.founder.cdr.entity.LabOrder;
import com.founder.cdr.entity.LabOrderLabResult;
import com.founder.cdr.entity.LabResult;
import com.founder.cdr.entity.MedicalImaging;
import com.founder.cdr.entity.WithdrawRecord;
import com.founder.cdr.hl7.dto.LabImageContent;
import com.founder.cdr.hl7.dto.LabSample;
import com.founder.cdr.hl7.dto.pocdin000040uv01.POCDIN000040UV01;
import com.founder.cdr.service.CommonService;
import com.founder.cdr.service.LabService;
import com.founder.cdr.util.DateUtils;
import com.founder.cdr.util.StringUtils;

/**
 * 检验报告数据接入部分相关功能
 * @author jin_peng
 */
@Component
public class POCDIN000040UV01Part
{
    // $Author: jin_peng
    // $Date: 2013/09/03 14:02
    // [BUG]0036979 MODIFY BEGIN
    /**
     * 病历文档信息赋值
     * @param pocdin000040uv01 检验报告CDA对象
     * @param labResult 检验报告对象
     * @param documentSn 文档唯一标识
     * @param systemTime 系统当前时间
     * @param patientLid 患者本地ID
     * @return 检验报告对应病例文档对象 
     */
    public ClinicalDocument getClinicalDocument(
            POCDIN000040UV01 pocdin000040uv01, LabResult labResult,
            BigDecimal documentSn, Date systemTime, String patientLid,
            String serviceId, CommonService commonService)
    {
        ClinicalDocument clinicalDocument = null;

        if (Constants.VERSION_NUMBER_INSERT.equals(pocdin000040uv01.getVersionNumber()))
        {
            clinicalDocument = new ClinicalDocument();
            // 文档内部序列号
            clinicalDocument.setDocumentSn(documentSn);
            // 创建时间
            clinicalDocument.setCreateTime(systemTime);
            // 删除标志
            clinicalDocument.setDeleteFlag(Constants.NO_DELETE_FLAG);
            // 更新回数
            clinicalDocument.setUpdateCount(Constants.INSERT_UPDATE_COUNT);
            // $Author: tong_meng
            // $Date: 2013/8/30 15:00
            // [BUG]0036757 ADD BEGIN
            clinicalDocument.setCreateby(pocdin000040uv01.getMessage().getServiceId()); // 设置创建人
            // [BUG]0036757 ADD END
        }
        else
        {
            Map<String, Object> condition = new HashMap<String, Object>();
            condition.put("documentSn", labResult.getDocumentSn());
            condition.put("deleteFlag", Constants.NO_DELETE_FLAG);

            List<Object> clinicalDocuments = commonService.selectByCondition(
                    ClinicalDocument.class, condition);

            if (clinicalDocuments != null && !clinicalDocuments.isEmpty())
            {
                // 获取病历文档
                clinicalDocument = (ClinicalDocument) clinicalDocuments.get(0);

                clinicalDocument.setUpdateby(serviceId);
            }
        }

        // 就诊内部序列号
        clinicalDocument.setVisitSn(labResult.getVisitSn());
        // 患者内部序列号
        clinicalDocument.setPatientSn(labResult.getPatientSn());
        // 域代码
        clinicalDocument.setPatientDomain(pocdin000040uv01.getPatientDomain());
        // 患者本地ID
        clinicalDocument.setPatientLid(patientLid);
        // 文档本地ID
        clinicalDocument.setDocumentLid(pocdin000040uv01.getReportNo());
        // 文档类型
        clinicalDocument.setDocumentType(pocdin000040uv01.getDocumentType());
        // $Author:wei_peng
        // $Date:2012/8/30 11:03
        // $[BUG]0009064 MODIFY BEGIN
        // 文档类型名称
        clinicalDocument.setDocumentTypeName(pocdin000040uv01.getDocumentTypeName());
        // $[BUG]0009064 MODIFY END
        // 文档名称
        clinicalDocument.setDocumentName(pocdin000040uv01.getDocumentName());
        // 文档作者名称
        clinicalDocument.setDocumentAuthorName(pocdin000040uv01.getReporterName());
        // 文档作者ID
        clinicalDocument.setDocumentAuthor(pocdin000040uv01.getReporterId());
        // 文档审核人ID
        clinicalDocument.setReviewPerson(pocdin000040uv01.getReviewerId());
        // 文档审核人姓名
        clinicalDocument.setReviewPersonName(pocdin000040uv01.getReviewerName());
        // 文档审核时间
        clinicalDocument.setReviewTime(DateUtils.stringToDate(pocdin000040uv01.getReviewDate()));
        // 文档做成时间
        clinicalDocument.setWriteTime(DateUtils.stringToDate(pocdin000040uv01.getReportDate()));
        // 提交时间（签章时间）
        clinicalDocument.setSignTime(DateUtils.stringToDate(pocdin000040uv01.getSignTime()));
        // 电子签章ID
        clinicalDocument.setSignatureId(pocdin000040uv01.getSignatureId());
        // $Author:tong_meng
        // $Date:2013/8/5 11:03
        // $[BUG]0035707 MODIFY BEGIN
        clinicalDocument.setServiceId(serviceId);
        // $[BUG]0035707 MODIFY END
        // 更新时间
        clinicalDocument.setUpdateTime(systemTime);
        // 医疗机构代码
        clinicalDocument.setOrgCode(pocdin000040uv01.getOrgCode());
        // 医疗机构名称
        clinicalDocument.setOrgName(pocdin000040uv01.getOrgName());

        return clinicalDocument;
    }

    // [BUG]0008030 ADD END
    // [BUG]0036979 MODIFY END

    /**
     * 获取检验报告对象
     * @param pocdin000040uv01 检验报告CDA对象
     * @param versionNumber 版本号
     * @param labResult 检验报告对象
     * @param commonService 共通数据库操作对象
     * @param reportSequence 报告序列
     * @param visitSn 就诊内部序列号
     * @param patientSn 患者内部序列号
     * @param patientLid 患者本地ID
     * @param systemTime 系统当前时间
     * @param noDeleteFlag 新增时删除标记
     * @param documentSn 文档内部序列号
     * @param sourceSystemId 报告源系统ID
     * @return 检验报告对象
     */
    public LabResult getLabResult(POCDIN000040UV01 pocdin000040uv01,
            String versionNumber, LabResult labResult,
            CommonService commonService, String reportSequence,
            BigDecimal visitSn, BigDecimal patientSn, String patientLid,
            Date systemTime, String noDeleteFlag, BigDecimal documentSn,
            String sourceSystemId)
    {
        BigDecimal seq = null;

        // 通过versionNumber判断进行新增，修改或删除操作并设置数据
        if (Constants.VERSION_NUMBER_INSERT.equals(pocdin000040uv01.getVersionNumber()))
        {
            labResult = new LabResult();
            seq = commonService.getSequence(reportSequence); // 获得检验报告内部序列号
            labResult.setLabReportSn(seq); // 设置检验报告内部序列号
            labResult.setDocumentSn(documentSn);// 文档内部序列号
            labResult.setVisitSn(visitSn); // 设置就诊内部序列号
            labResult.setPatientSn(patientSn); // 设置患者内部序列号
            labResult.setPatientDomain(pocdin000040uv01.getPatientDomain()); // 设置域ID
            labResult.setPatientLid(patientLid); // 设置患者本地ID
            labResult.setLabReportLid(pocdin000040uv01.getReportNo()); // 设置报告本地ID
            // $Author: tong_meng
            // $Date: 2013/8/30 15:00
            // [BUG]0036757 ADD BEGIN
            labResult.setCreateby(pocdin000040uv01.getMessage().getServiceId()); // 设置创建人
            // [BUG]0036757 ADD END
            labResult.setCreateTime(systemTime); // 设置创建时间
            labResult.setUpdateCount(Constants.INSERT_UPDATE_COUNT); // 设置新增时初始更新回数
            labResult.setDeleteFlag(noDeleteFlag); // 设置新增时初始删除标记
        }
        // $Author: tong_meng
        // $Date: 2013/8/30 15:00
        // [BUG]0036757 ADD BEGIN
        else if (Constants.VERSION_NUMBER_UPDATE.equals(pocdin000040uv01.getVersionNumber()))
        {
            labResult.setUpdateby(pocdin000040uv01.getMessage().getServiceId()); // 设置创建人
        }
        // [BUG]0036757 ADD END

        // Author :jia_yanqing
        // Date : 2013/3/18 10:36
        // [BUG]0014513 ADD BEGIN
        labResult.setBedNo(pocdin000040uv01.getBedNo()); // 床号
        //wang.meng 普通检验报告与微生物检验报告病区路径区分开
        labResult.setWardId(pocdin000040uv01.getWardsNo()); // 病区ID
        labResult.setWardName(pocdin000040uv01.getWardsName()); // 病区名称
        labResult.setRoomNo(pocdin000040uv01.getRoomNo());//病房号
        labResult.setMethod(pocdin000040uv01.getMethod());//方法
        //end
        // [BUG]0014513 ADD END
        labResult.setLabDept(pocdin000040uv01.getLabDept()); // 设置检验科室
        labResult.setLabDeptName(pocdin000040uv01.getLabDeptName()); // 设置检验科室名称
        labResult.setReporterId(pocdin000040uv01.getReporterId()); // 设置报告人ID
        labResult.setReporterName(pocdin000040uv01.getReporterName()); // 设置报告人姓名
        labResult.setReportDate(DateUtils.stringToDate(pocdin000040uv01.getReportDate())); // 设置报告时间
        labResult.setReviewerId(pocdin000040uv01.getReviewerId()); // 设置审核人ID
        labResult.setReviewerName(pocdin000040uv01.getReviewerName()); // 设置审核人姓名
        labResult.setReviewDate(DateUtils.stringToDate(pocdin000040uv01.getReviewDate())); // 设置审核时间
        labResult.setPhenomenonPerformance(pocdin000040uv01.getSurface()); // 设置表面现象
        labResult.setTechnicalNote(pocdin000040uv01.getTechMemo()); // 设置技术备注

        // $Author :tong_meng
        // $Date : 2013/08/28 10:00$
        // [BUG]0036449 ADD BEGIN
        // $Note: 一个报告出一个标本，如果此关系变更，则修改这两行代码逻辑
        if (Constants.SOURCE_LAB.equals(pocdin000040uv01.getSourceSystemId()))
        {
            if (pocdin000040uv01.getSample() != null)
            {
                labResult.setGathererCode(pocdin000040uv01.getSample().get(0).getCollectionId());
                labResult.setGathererName(pocdin000040uv01.getSample().get(0).getCollectionName());
            }
        }
        // [BUG]0036449 ADD BEGIN

        // $Author :jin_peng
        // $Date : 2012/10/17 15:36$
        // [BUG]0010520 ADD BEGIN
        // 消息为更新状态时召回状态设为已更新状态
        if (Constants.VERSION_NUMBER_UPDATE.equals(pocdin000040uv01.getVersionNumber()))
        {
            labResult.setWithdrawFlag(StringUtils.strToBigDecimal(
                    Constants.REPORT_MODIFY_FLAG, "召回状态")); // 设置召回标识为已更新状态
        }
        else
        {
            labResult.setWithdrawFlag(Constants.NO_WITH_DRAW_FLAG); // 设置召回标识为为召回状态
        }

        // [BUG]0010520 ADD END

        labResult.setUpdateTime(systemTime); // 设置记录更新时间
        // $Author :jin_peng
        // $Date : 2012/07/24 15:25$
        // [BUG]0008030 ADD BEGIN
        labResult.setRequestTime(DateUtils.stringToDate(pocdin000040uv01.getRequestTime())); // 申请时间
        labResult.setSubmittingPersonId(pocdin000040uv01.getApplyPerson()); // 送检医师ID
        labResult.setSubmittingPersonName(pocdin000040uv01.getApplyPersonName()); // 送检医师姓名

        
        // 微生物报告时把评语内容存入报告备注字段中
        if (Constants.SOURCE_MICROBE.equals(sourceSystemId))
        {
            labResult.setReportMemo(pocdin000040uv01.getComment());// 设置评语内容
            labResult.setTestResults(pocdin000040uv01.getDescriptionResult()); // 设置检验结果
            //wang.meng 微生物检验病区用
      //      labResult.setWardId(pocdin000040uv01.getWardsNo()); // 病区ID
        }
        else
        {
            labResult.setReportMemo(pocdin000040uv01.getReportMemo());// 设置报告备注
            //wang.meng 普通检验病区用
       //     labResult.setWardId(pocdin000040uv01.getWardsCode()); // 病区ID
            labResult.setWardName(pocdin000040uv01.getWardsName()); // 病区名称
        }

        labResult.setTesterId(pocdin000040uv01.getTesterId());// 设置检验医生编码
        labResult.setTesterName(pocdin000040uv01.getTesterName());// 设置检验医生姓名
        labResult.setSourceSystemId(sourceSystemId);// 设置源系统id
        // [BUG]0008030 ADD END

        // $Author :jin_peng
        // $Date : 2012/07/26 14:00$
        // [BUG]0010366 ADD BEGIN
        labResult.setReportTypeCode(pocdin000040uv01.getReportTypeCode());// 设置报告类型编码
        labResult.setReportTypeName(pocdin000040uv01.getReportTypeName());// 设置报告类型名称

        // [BUG]0010366 ADD END

        // 设置标本类型
        if (pocdin000040uv01.getSample() != null
            && pocdin000040uv01.getSample().size() != 0)
        {
            List<LabSample> labSampleList = pocdin000040uv01.getSample();
            LabSample labSample = labSampleList.get(0);

            labResult.setSampleType(labSample.getSampleType()); // 设置标本类型

            // $Author :tong_meng
            // $Date : 2012/7/1 19:33$
            // [BUG]0007418 ADD BEGIN
            labResult.setSampleTypeName(labSample.getSampleTypeName()); // 设置标本类型名称
            // [BUG]0007418 ADD END

            // $Author :jin_peng
            // $Date : 2012/07/24 15:25$
            // [BUG]0008030 ADD BEGIN
            labResult.setSamplingSite(labSample.getCollectionPlaceName()); // 设置标本采集地点
            labResult.setSamplingTime(DateUtils.stringToDate(labSample.getEffectiveTime()));// 设置标本采集时间
            labResult.setReceiveTime(DateUtils.stringToDate(labSample.getReceiveTime())); // 设置标本接收时间
            labResult.setSampleNo(labSample.getSampleNo());// 设置标本号
            // [BUG]0008030 ADD END
        }
        
        labResult.setOrgCode(pocdin000040uv01.getOrgCode());
        labResult.setOrgName(pocdin000040uv01.getOrgName());

        return labResult;
    }

    /**
     * 获取检验报告关联的检验影像信息
     * @param pocdin000040uv01 检验报告CDA对象
     * @param versionNumber 版本号
     * @param commonService 共同数据库操作对象
     * @param medicalImagingSequence 检验影像序列名称
     * @param reportSn 报告内部序列号
     * @param systemTime 系统当前时间
     * @param noDeleteFlag 新增时删除标记
     * @param labService 检验报告数据库操作对象
     * @return 检验报告关联的检验影像信息对象集合
     */
    public List<List<Object>> getLabImage(POCDIN000040UV01 pocdin000040uv01,
            String versionNumber, CommonService commonService,
            String medicalImagingSequence, BigDecimal reportSn,
            Date systemTime, String noDeleteFlag, LabService labService)
    {
        List<List<Object>> resList = new ArrayList<List<Object>>();
        BigDecimal seq = null;

        // 新增影像对象
        List<Object> labImageAddList = new ArrayList<Object>();

        // CDA中影像信息
        List<LabImageContent> labImageContentList = pocdin000040uv01.getSample().get(
                0).getImageContent();

        // 读取CDA中的影像信息存入entity检验影像中
        if (labImageContentList != null && labImageContentList.size() != 0)
        {
            for (LabImageContent labImageContent : labImageContentList)
            {
                MedicalImaging labImage = new MedicalImaging();
                seq = commonService.getSequence(medicalImagingSequence);
                labImage.setImagingSn(seq); // 设置影像内部序列号
                labImage.setReportSn(reportSn); // 设置相关联的检验报告内部序列号
                labImage.setRefType(Constants.REPORT_TYPE_LAB); // 设置引用类型
                // $Author :jia_yanqing
                // $Date : 2012/6/13 15:30$
                // [BUG]0006921 MODIFY BEGIN
                labImage.setImageContent(getImageContentAfterDecode(labImageContent)); // 设置影像内容
                // [BUG]0006921 MODIFY ENDwo
                labImage.setPromptMessage(labImageContent.getPrompt()); // 设置提示信息

                // 图片扩展名为image/jpg的形式时进行判断取值
                String imageExtension = labImageContent.getImageExtension();
                if (!StringUtils.isEmpty(imageExtension))
                {
                    if (labImageContent.getImageExtension().indexOf("/") > 0)
                    {
                        if (labImageContent.getImageExtension().split("/").length > 1)
                        {
                            imageExtension = labImageContent.getImageExtension().split(
                                    "/")[1];
                        }
                        else
                        {
                            imageExtension = "";
                        }
                    }
                }

                labImage.setImageFormat(imageExtension); // 设置影像格式
                labImage.setCreateTime(systemTime); // 设置该记录创建时间
                labImage.setUpdateTime(systemTime); // 设置该记录更新时间
                labImage.setUpdateCount(Constants.INSERT_UPDATE_COUNT); // 设置该记录初始更新回数
                labImage.setDeleteFlag(noDeleteFlag); // 设置该记录初始删除标记

                // $Author: tong_meng
                // $Date: 2013/8/30 15:00
                // [BUG]0036757 ADD BEGIN
                labImage.setCreateby(pocdin000040uv01.getMessage().getServiceId()); // 设置报告本地ID
                // [BUG]0036757 ADD END

                labImageAddList.add(labImage);
            }
        }

        resList.add(labImageAddList);

        // 通过versionNumber判断进行新增前的删除,向要返回的结果集合中装入对应影像集合
        if (Constants.VERSION_NUMBER_UPDATE.equals(pocdin000040uv01.getVersionNumber()))
        {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("reportSn", reportSn);
            map.put("deleteFlag", noDeleteFlag);
            List<Object> labImageList = labService.selectLabImageByCondition(map);
            resList.add(labImageList);
        }

        return resList;
    }

    // $Author :jia_yanqing
    // $Date : 2012/9/7 15:30$
    // [BUG]0009507 MODIFY BEGIN
    // $Author :jia_yanqing
    // $Date : 2012/6/13 15:30$
    // [BUG]0006921 MODIFY BEGIN
    /**
    * 将消息中传来的BASE64格式的影像内容解码为字节数组类型
    * @param labImageDto
    * @return byte[]
    */
    public byte[] getImageContentAfterDecode(LabImageContent labImageDto)
    {
        byte[] bt = null;
        if (labImageDto.getImageText() != null
            && !"".equals(labImageDto.getImageText()))
        {
            bt = Base64.decodeBase64(labImageDto.getImageText());
        }
        return bt;
    }

    // [BUG]0006921 MODIFY BEGIN
    // [BUG]0009507 MODIFY END

    /**
     * 获取报告召回信息
     * @param pocdin000040uv01 检验报告CDA对象
     * @param labResult 检验报告对象
     * @param systemTime 系统当前时间
     * @param noDeleteFlag 新增时删除标记
     * @return 报告召回信息
     */
    public List<Object> getLabResultAndWithdraw(
            POCDIN000040UV01 pocdin000040uv01, LabResult labResult,
            Date systemTime, String noDeleteFlag)
    {
        List<Object> resList = new ArrayList<Object>();

        // 更需要召回的检验报告存在时更改召回状态
        if (labResult != null)
        {
            labResult.setWithdrawFlag(Constants.WITH_DRAW_FLAG); // 召回时设置检验报告表中的召回状态
            labResult.setUpdateTime(DateUtils.getSystemTime()); // 召回时设置检验报告该记录的更新时间

            // $Author: tong_meng
            // $Date: 2013/8/30 15:00
            // [BUG]0036757 ADD BEGIN
            labResult.setUpdateby(pocdin000040uv01.getMessage().getServiceId()); // 设置创建人
            // [BUG]0036757 ADD END

            // 新增作废信息
            WithdrawRecord withdrawRecord = new WithdrawRecord();
            withdrawRecord.setReportSn(labResult.getLabReportSn()); // 设置召回记录中的关联检验报告内部序列号
            withdrawRecord.setRefType(Constants.REPORT_TYPE_LAB); // 设置引用类型
            withdrawRecord.setWithdrawPerson(pocdin000040uv01.getWithDrawPerson()); // 设置召回人ID
            withdrawRecord.setWithdrawPersonName(pocdin000040uv01.getWithDrawPersonName()); // 设置召回人姓名
            withdrawRecord.setWithdrawTime(DateUtils.stringToDate(pocdin000040uv01.getWithDrawDate())); // 设置召回时间
            withdrawRecord.setWithdrawReason(pocdin000040uv01.getWithdrawReason()); // 设置召回原因
            withdrawRecord.setCreateTime(systemTime); // 设置召回记录创建时间
            withdrawRecord.setUpdateTime(systemTime); // 设置召回记录更新时间
            withdrawRecord.setUpdateCount(Constants.INSERT_UPDATE_COUNT); // 设置新增时该条召回记录初始更新回数
            withdrawRecord.setDeleteFlag(noDeleteFlag); // 设置新增时该条召回记录初始删除标记

            // $Author: tong_meng
            // $Date: 2013/8/30 15:00
            // [BUG]0036757 ADD BEGIN
            withdrawRecord.setCreateby(pocdin000040uv01.getMessage().getServiceId()); // 设置创建人
            // [BUG]0036757 ADD END

            // 添加要更新的检验报告和召回记录对象
            resList.add(labResult);
            resList.add(withdrawRecord);
        }
        return resList;
    }

    // Author :jin_peng
    // Date : 2013/09/11 10:12$
    // [BUG]0037243 ADD BEGIN
    /**
     * 校验检验报告相应医嘱是否存在
     * @param sampleNo 标本号
     * @param patientLid 患者lid
     * @param patientDomain 域id
     * @param commonService 业务交互对象
     * @return 相应医嘱集合
     */
    public List<Object> checkLabOrder(String sampleNo, String patientLid,
            String patientDomain, CommonService commonService, String orgCode, BigDecimal visitSn)
    {
        // 通过标本号查找检验报告关联的检验医嘱集合
        Map<String, Object> conditionOrder = new HashMap<String, Object>();
        conditionOrder.put("sampleNo", sampleNo);
        conditionOrder.put("patientDomain", patientDomain);
        conditionOrder.put("patientLid", patientLid);
        conditionOrder.put("orgCode", orgCode);
        conditionOrder.put("visitSn", visitSn);
        conditionOrder.put("deleteFlag", Constants.NO_DELETE_FLAG);
        List<Object> labOrders = commonService.selectByCondition(
                LabOrder.class, conditionOrder);
        return labOrders;
    }
    /**
     * 校验检验报告相应医嘱是否存在
     * @param orderNo 医嘱号
     * @param patientLid 患者lid
     * @param patientDomain 域id
     * @param commonService 业务交互对象
     * @return 相应医嘱集合
     */
    public List<Object> checkLabOrderByOrderNo(List<String> orderNo, String patientLid,
            String patientDomain, CommonService commonService, String orgCode, BigDecimal visitSn)
    {
        // 通过标本号查找检验报告关联的检验医嘱集合
    	List<Object> labOrders = new ArrayList<Object>();
    	for(String labOrder : orderNo){
    		List orders = checkLabOrderByOrderLid(labOrder,patientLid,patientDomain,commonService,orgCode,visitSn);
    		if(orders != null && orders.size() > 0){
    			labOrders.add(orders.get(0));
    		}
    	}
        return labOrders;
    }
    /**
     * 校验检验报告相应医嘱是否存在
     * @param orderlid 医嘱号
     * @param patientLid 患者lid
     * @param patientDomain 域id
     * @param commonService 业务交互对象
     * @return 相应医嘱集合
     */
    public List<Object> checkLabOrderByOrderLid(String orderlid, String patientLid,
            String patientDomain, CommonService commonService, String orgCode, BigDecimal visitSn)
    {
        // 通过标本号查找检验报告关联的检验医嘱集合
        Map<String, Object> conditionOrder = new HashMap<String, Object>();
        conditionOrder.put("orderLid", orderlid);
        conditionOrder.put("patientDomain", patientDomain);
        conditionOrder.put("patientLid", patientLid);
        conditionOrder.put("orgCode", orgCode);
        conditionOrder.put("visitSn", visitSn);
        conditionOrder.put("deleteFlag", Constants.NO_DELETE_FLAG);
        List<Object> labOrders = commonService.selectByCondition(
                LabOrder.class, conditionOrder);
        return labOrders;
    }
    // [BUG]0037243 ADD END

    // Author :jin_peng
    // Date : 2013/09/11 10:12$
    // [BUG]0037243 MODIFY BEGIN
    // $Author: wei_peng
    // $Date: 2013/3/20 15:04
    // [BUG]0014602 ADD BEGIN
    /**
     * 获取检验报告和检验医嘱关系集合
     * @param commonService 共同数据库操作对象
     * @param labReportSn 检验报告内部序列号
     * @param labOrders 检验报告对应医嘱集合
     * @param systemTime 系统时间
     * @return 检验报告和检验医嘱关系集合
     */
    public List<LabOrderLabResult> getLabOrderLabResult(
            CommonService commonService, BigDecimal labReportSn,
            List<Object> labOrders, Date systemTime, String serviceId)
    {
        // Author :jin_peng
        // Date : 2013/09/11 10:12$
        // [BUG]0037243 DELETE BEGIN
        // 通过标本号查找检验报告关联的检验医嘱集合
        /**Map<String, Object> condition = new HashMap<String, Object>();
        condition.put("sampleNo", sampleNo);
        condition.put("patientDomain", patientDomain);
        condition.put("patientLid", patientLid);
        condition.put("deleteFlag", Constants.NO_DELETE_FLAG);
        List<Object> labOrders = commonService.selectByCondition(
                LabOrder.class, condition);*/

        // [BUG]0037243 DELETE END

        // 检验报告和检验医嘱关联关系集合
        List<LabOrderLabResult> labOrderLabResults = null;
        if (labOrders != null && labOrders.size() > 0)
        {
            labOrderLabResults = new ArrayList<LabOrderLabResult>();
            for (Object object : labOrders)
            {
                LabOrder labOrder = (LabOrder) object;
                LabOrderLabResult labOrderLabResult = new LabOrderLabResult();
                labOrderLabResult.setLabReportSn(labReportSn);// 检验报告内部序列号
                labOrderLabResult.setLabOrderSn(labOrder.getOrderSn());// 检验医嘱内部序列号
                labOrderLabResult.setDeleteFlag(Constants.NO_DELETE_FLAG);
                labOrderLabResult.setCreateTime(systemTime);
                labOrderLabResult.setUpdateCount(Constants.INSERT_UPDATE_COUNT);
                labOrderLabResult.setUpdateTime(systemTime);

                // $Author: tong_meng
                // $Date: 2013/8/30 15:00
                // [BUG]0036757 ADD BEGIN
                labOrderLabResult.setCreateby(serviceId); // 设置报告本地ID
                // [BUG]0036757 ADD END

                labOrderLabResults.add(labOrderLabResult);
            }
        }

        return labOrderLabResults;
    }
    // [BUG]0014602 ADD END
    // [BUG]0037243 MODIFY END
}
