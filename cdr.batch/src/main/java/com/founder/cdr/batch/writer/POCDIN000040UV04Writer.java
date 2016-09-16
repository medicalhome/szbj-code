/**
 * 形态学检验报告数据接入
 * @author jia_yanqing
 * @date 2012/08/15
 */
package com.founder.cdr.batch.writer;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.founder.cdr.batch.writer.pocdin000040uv01.POCDIN000040UV01Part;
import com.founder.cdr.core.Constants;
import com.founder.cdr.entity.ClinicalDocument;
import com.founder.cdr.entity.LabOrderLabResult;
import com.founder.cdr.entity.LabResult;
import com.founder.cdr.entity.LabResultCompositeItem;
import com.founder.cdr.entity.LabResultItem;
import com.founder.cdr.entity.MedicalImaging;
import com.founder.cdr.entity.MedicalVisit;
import com.founder.cdr.hl7.dto.Comment;
import com.founder.cdr.hl7.dto.MorphologyImageContent;
import com.founder.cdr.hl7.dto.MorphologyReportItem;
import com.founder.cdr.hl7.dto.MorphologyReportResult;
import com.founder.cdr.hl7.dto.MorphologySample;
import com.founder.cdr.hl7.dto.Reporter;
import com.founder.cdr.hl7.dto.pocdin000040uv04.POCDIN000040UV04;
import com.founder.cdr.service.CommonService;
import com.founder.cdr.service.LabService;
import com.founder.cdr.util.DateUtils;
import com.founder.cdr.util.StringUtils;

@Component(value = "pocdin000040uv04Writer")
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class POCDIN000040UV04Writer implements BusinessWriter<POCDIN000040UV04>
{
    private static final Logger logger = LoggerFactory.getLogger(POCDIN000040UV04Writer.class);

    private static final Logger loggerBS355 = LoggerFactory.getLogger("BS355");

    // 病历文档序列名称
    private static final String DOCUMENT_SEQUENCE = "SEQ_DOCUMENT";

    // 检验报告序列名称
    private static final String REPORT_SEQUENCE = "SEQ_REPORT";

    // 检验项目序列名称
    private static final String LAB_ITEM_SEQUENCE = "SEQ_LAB_ITEM";

    // 医学影像序列名称
    private static final String MEDICAL_IMAGING_SEQUENCE = "SEQ_MEDICAL_IMAGING";

    // 患者本地Lid
    private String patientLid;

    // 域ID
    private String patientDomain;

    // 就诊内部序列号
    private BigDecimal visitSn;

    // 患者内部序列号
    private BigDecimal patientSn;

    // 文档内部序列号
    private BigDecimal documentSn;

    // 系统当前时间
    private Date systemTime = DateUtils.getSystemTime();

    // 就诊记录
    private MedicalVisit medicalVisit;

    // 检验报告
    private LabResult labResult;

    // 病历文档
    private ClinicalDocument clinicalDocument;

    // Author :jia_yanqing
    // Date : 2013/4/17 10:47
    // [BUG]0015141 ADD BEGIN
    // 要删除的影像信息
    private List<Object> labImageListForDelete;
    
    // 保存图像开关-关
// 	private static final String MEDICAL_IMAGE_CLOSE= "0";

    // [BUG]0015141 ADD END

    @Autowired
    private LabService labService;

    @Autowired
    private CommonService commonService;

    @Autowired
    private POCDIN000040UV01Part pPart;

    // $Author: tong_meng
    // $Date: 2013/8/30 15:00
    // [BUG]0036757 ADD BEGIN
    private String serviceId;

    // [BUG]0036757 ADD END

    // Author :jin_peng
    // Date : 2013/09/11 10:12$
    // [BUG]0037243 ADD BEGIN
    // 报告对应医嘱集合
    private List<Object> labOrdersList;

    // [BUG]0037243 ADD BEGIN

    @Override
    public boolean validate(POCDIN000040UV04 pocdin000040uv04)
    {
        if (StringUtils.isEmpty(pocdin000040uv04.getMessage().getOrgCode()))
        {
            pocdin000040uv04.getMessage().setOrgCode(Constants.HOSPITAL_CODE);
        }

        // Author :jia_yanqing
        // Date : 2013/1/22 15:00
        // [BUG]0042085 MODIFY BEGIN
        if (pocdin000040uv04.getOrgCode() == null
            || "".equals(pocdin000040uv04.getOrgCode()))
        {
            pocdin000040uv04.setOrgCode(Constants.HOSPITAL_CODE);
            pocdin000040uv04.setOrgName(Constants.HOSPITAL_NAME);
        }
        // $Author:tong_meng
        // $Date:2013/12/03 11:00
        // [BUG]0040270 ADD BEGIN
        /*
         * if (!pocdin000040uv04.getOrgCode().equals(
         * pocdin000040uv04.getMessage().getOrgCode())) {
         * logger.error("消息头中的医疗机构代码与消息体中的医疗机构代码不一致！" +
         * "消息头医疗机构代码：{}，消息体医疗机构代码：{}",
         * pocdin000040uv04.getMessage().getOrgCode(),
         * pocdin000040uv04.getOrgCode());
         * loggerBS355.error("消息头中的医疗机构代码与消息体中的医疗机构代码不一致！" +
         * "消息头医疗机构代码：{}，消息体医疗机构代码：{}",
         * pocdin000040uv04.getMessage().getOrgCode(),
         * pocdin000040uv04.getOrgCode()); return false; }
         */
        // [BUG]0040270 ADD END
        // [BUG]0042085 MODIFY END

        boolean flag = false;
        List<String> validateList = new ArrayList<String>();
        // 测试用日志
        Map<String, String> logMap = new HashMap<String, String>();
        if (Constants.VERSION_NUMBER_INSERT.equals(pocdin000040uv04.getVersionNumber())
            || Constants.VERSION_NUMBER_UPDATE.equals(pocdin000040uv04.getVersionNumber()))
        {
            // Author :chang_xuewen
            // Date : 2013/8/30 11:00
            // [BUG]0036746 ADD BEGIN
            // 获取形态学报告类型编码和名称
            validateList.add(pocdin000040uv04.getMorphologyReportTypeCode());
            logMap.put("形态学报告类型编码不能为空！morphologyReportTypeCode=",
                    pocdin000040uv04.getMorphologyReportTypeCode());
            validateList.add(pocdin000040uv04.getMorphologyReportTypeName());
            logMap.put("形态学报告类型名称不能为空！morphologyReportTypeName=",
                    pocdin000040uv04.getMorphologyReportTypeName());
            // [BUG]0036746 ADD END

            // 获得检验报告条目信息
            List<MorphologyReportItem> morphologyReportItemList = pocdin000040uv04.getMorphologyReportItem();
            // 判断检验条目信息是否为空
            if (morphologyReportItemList != null
                && !morphologyReportItemList.isEmpty())
            {
                // 检验报告条目信息不为空时，循环遍历验证它的检验项编码和检验项名称是否为空
                for (MorphologyReportItem morphologyReportItem : morphologyReportItemList)
                {
                    // 检验项编码
                    validateList.add(morphologyReportItem.getLabItemCode());
                    logMap.put("检验报告条目中检验项编码不能为空！labItemCode=",
                            morphologyReportItem.getLabItemCode());
                    // 检验项名称
                    validateList.add(morphologyReportItem.getLabItemName());
                    logMap.put("检验报告条目中检验项名称不能为空！labItemName=",
                            morphologyReportItem.getLabItemName());
                    // 获得检验结果项
                    List<MorphologyReportResult> morphologyReportResultList = morphologyReportItem.getMorphologyReportResult();
                    if (morphologyReportResultList != null
                        && !morphologyReportResultList.isEmpty())
                    {
                        // 检验结果项不为空时，循环遍历验证它的显示序号是否为空
                        for (MorphologyReportResult morphologyReportResult : morphologyReportResultList)
                        {
                            // 显示序号
                            validateList.add(morphologyReportResult.getDisplayOrder());
                            logMap.put("检验结果项显示序号不能为空！displayOrder=",
                                    morphologyReportResult.getDisplayOrder());
                        }
                    }
                    // 获得补充意见项
                    List<Comment> commentList = morphologyReportItem.getComment();
                    if (commentList != null && !commentList.isEmpty())
                    {
                        // 补充意见项不为空时，循环遍历验证它的意见顺序是否为空
                        for (Comment comment : commentList)
                        {
                            // 检验项编码
                            validateList.add(comment.getDisplayOrder());
                            logMap.put("补充意见意见顺序不能为空！displayOrder=",
                                    comment.getDisplayOrder());
                        }
                    }
                }
                // 获得标本及其图像信息
                List<MorphologySample> morphologySampleList = pocdin000040uv04.getMorphologySample();
                for (MorphologySample morphologySample : morphologySampleList)
                {
                    // 获得标本的影像信息
                    List<MorphologyImageContent> morphologyImageContentList = morphologySample.getMorphologyImageContent();
                    if (morphologyImageContentList != null
                        && !morphologyImageContentList.isEmpty())
                    {
                        for (MorphologyImageContent morphologyImageContent : morphologyImageContentList)
                        {
                            // 影像信息
                            validateList.add(morphologyImageContent.getImageText());
                            logMap.put("标本的影像信息不能为空！imageText=",
                                    morphologyImageContent.getImageText());
                            // 影像图片后缀名
                            validateList.add(morphologyImageContent.getImageExtension());
                            logMap.put("标本的影像图片后缀名不能为空！imageExtension=",
                                    morphologyImageContent.getImageExtension());
                        }
                    }
                }
            }
            String[] str = new String[validateList.size()];
            flag = StringUtils.isNotNullAll(validateList.toArray(str));

            // loggerBS355.debug("新增或更新时是否所有必填项都有值: {}", flag);

            // 测试所用非空验证日志
            if (flag == false)
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
                loggerBS355.error("Message:{}, validate:{}",
                        pocdin000040uv04.toString(), "非空字段验证未通过！"
                            + validateMessages.toString());
            }
        }
        return flag;
    }

    @Override
    public boolean checkDependency(POCDIN000040UV04 pocdin000040uv04,
            boolean flag)
    {
        // 获得患者本地Lid
        patientLid = pocdin000040uv04.getPatientLid();
        // 获得域ID
        patientDomain = pocdin000040uv04.getPatientDomain();

        if (Constants.VERSION_NUMBER_INSERT.equals(pocdin000040uv04.getVersionNumber()))
        {
            // 获得就诊次数
            String visitTimes = pocdin000040uv04.getVisitTimes();

            // $Author:tong_meng
            // $Date:2013/12/03 11:00
            // [BUG]0040270 MODIFY BEGIN
            // 获得该检验报告关联的就诊记录
            medicalVisit = commonService.mediVisit(patientDomain, patientLid,
                    visitTimes, pocdin000040uv04.getOrgCode(), pocdin000040uv04.getVisitOrdNo(), pocdin000040uv04.getVisitType());
            // [BUG]0040270 MODIFY END

            // 如果就诊记录存在，表明关联关系存在，则可以执行新增操作
            if (medicalVisit != null)
            {
                visitSn = medicalVisit.getVisitSn();
                patientSn = medicalVisit.getPatientSn();
                logger.debug("在新增检验报告时相应的就诊信息存在true: {}",
                        "params: patientLid = " + patientLid
                            + "; patientDomain = " + patientDomain
                            + "; visitTimes = " + visitTimes + "; orgCode = "
                            + pocdin000040uv04.getOrgCode() + "; visitOrdNo = "
                                    + pocdin000040uv04.getVisitOrdNo() + "; visitTypeCode = "
                                            + pocdin000040uv04.getVisitType());
            }
            else
            {
                logger.error("检验报告关联就诊false: {} 检验报告版本状态: {}",
                        "medicalVisit = " + medicalVisit
                            + ";  params: patientLid = " + patientLid
                            + "; patientDomain = " + patientDomain
                            + "; visitTimes = " + visitTimes + "; orgCode = "
                            + pocdin000040uv04.getOrgCode() + "; visitOrdNo = "
                                    + pocdin000040uv04.getVisitOrdNo() + "; visitTypeCode = "
                                    + pocdin000040uv04.getVisitType(),
                        Constants.VERSION_NUMBER_INSERT);
                if (flag)
                {
                    loggerBS355.error("Message:{},checkDependency:{}",
                            pocdin000040uv04.toString(),
                            "检验报告关联就诊false,params: medicalVisit = "
                                + medicalVisit + ";  params: patientLid = "
                                + patientLid + "; patientDomain = "
                                + patientDomain + "; visitTimes = "
                                + visitTimes + "; orgCode = "
                                + pocdin000040uv04.getOrgCode() + "; visitOrdNo = "
                                        + pocdin000040uv04.getVisitOrdNo() + "; visitTypeCode = "
                                        + pocdin000040uv04.getVisitType()+ "; 检验报告版本状态:"
                                + Constants.VERSION_NUMBER_INSERT);
                }
                return false;
            }

            // $Author:tong_meng
            // $Date:2013/12/03 11:00
            // [BUG]0040270 MODIFY BEGIN
            // Author :jin_peng
            // Date : 2013/09/11 10:12$
            // [BUG]0037243 ADD BEGIN
            // 查询相应医嘱集合
            labOrdersList = pPart.checkLabOrderByOrderLid(
            		pocdin000040uv04.getOrderLid().get(0),
                    patientLid, patientDomain, commonService,
                    pocdin000040uv04.getOrgCode(), visitSn);
            // [BUG]0040270 MODIFY END

            if (labOrdersList == null || labOrdersList.isEmpty())
            {
                logger.error(
                        "检验报告新增时关联医嘱不存在：{}",
                        "orderlid = "
                            + pocdin000040uv04.getOrderLid().get(0)
                            + "; patientLid = " + patientLid
                            + "; visitSn = " + visitSn
                            + "; patientDomain = " + patientDomain
                            + "; orgCode = " + pocdin000040uv04.getOrgCode());

                if (flag)
                {
                    loggerBS355.error(
                            "Message:{},checkDependency:{}",
                            pocdin000040uv04.toString(),
                            "检验报告新增时关联医嘱不存在：orderlid = "
                                + pocdin000040uv04.getOrderLid().get(0)
                                + "; patientLid = " + patientLid
                                + "; visitSn = " + visitSn
                                + "; patientDomain = " + patientDomain
                                + "; orgCode = "
                                + pocdin000040uv04.getOrgCode());
                }

                return false;
            }

            return true;

            // [BUG]0037243 ADD END
        }
        // 检验报告更新时验证相应的报告记录是否存在
        if (Constants.VERSION_NUMBER_UPDATE.equals(pocdin000040uv04.getVersionNumber()))
        {
            // Author :jia_yanqing
            // Date : 2013/4/16 17:00
            // [BUG]0015133 MODIFY BEGIN
            // Author :jia_yanqing
            // Date : 2013/3/27 13:36
            // [BUG]0014763 MODIFY BEGIN
            // 获取检验报告ID
            String labReportLid = pocdin000040uv04.getReportNo();
            // [BUG]0014763 MODIFY END
            // [BUG]0015133 MODIFY END
            // 系统源ID
            String sourceSystemId = pocdin000040uv04.getSourceSystemId();
            Map<String, Object> map = new HashMap<String, Object>();

            map.put("labReportLid", labReportLid);
            map.put("sourceSystemId", sourceSystemId);
            map.put("deleteFlag", Constants.NO_DELETE_FLAG);
            // $Author:tong_meng
            // $Date:2013/12/03 11:00
            // [BUG]0040270 MODIFY BEGIN
            map.put("orgCode", pocdin000040uv04.getOrgCode());
            // [BUG]0040270 MODIFY END
            labResult = labService.selectLabResultByCondition(map);

            if (labResult != null)
            {
            	// 检查报告做更新操作时数据库中相应报告中召回状态为已召回时才可执行更新操作 控制开关(0:开启 1:关闭)
				if(Constants.COMM_INTERFACE.equals(Constants.WITH_DRAW_USE_FLAG)){
	                // $Author :jin_peng
	                // $Date : 2012/10/17 15:36$
	                // [BUG]0010520 ADD BEGIN
	                // 检验报告做更新操作时数据库中相应报告中召回状态为已召回时才可执行更新操作
	                if (!Constants.WITH_DRAW_FLAG.equals(labResult.getWithdrawFlag()))
	                {
	                    logger.error(
	                            "更新检验报告时相关数据的召回状态并非为已召回状态，暂不做更新操作: {}",
	                            "params: labReportLid = " + labReportLid
	                                + "; sourceSystemId = " + sourceSystemId
	                                + "; orgCode = "
	                                + pocdin000040uv04.getOrgCode());
	                    if (flag)
	                    {
	                        loggerBS355.error("Message:{},checkDependency:{}",
	                                pocdin000040uv04.toString(),
	                                "更新检验报告时相关数据的召回状态并非为已召回状态，暂不做更新操作,params: labReportLid = "
	                                    + labReportLid + "; sourceSystemId = "
	                                    + sourceSystemId + "; orgCode = "
	                                    + pocdin000040uv04.getOrgCode());
	                    }
	
	                    return false;
	                }
				}
                // [BUG]0010520 ADD END

                documentSn = labResult.getDocumentSn();
                if (documentSn != null && !"".equals(documentSn))
                {
                    // Author :jia_yanqing
                    // Date : 2014/2/13 15:00
                    // [BUG]0042260 MODIFY BEGIN
                    Map<String, Object> condition = new HashMap<String, Object>();
                    condition.put("documentSn", documentSn);
                    condition.put("deleteFlag", Constants.NO_DELETE_FLAG);
                    List<Object> clinicalDocuments = commonService.selectByCondition(
                            ClinicalDocument.class, condition);
                    if(clinicalDocuments != null && !clinicalDocuments.isEmpty())
                    {
                        clinicalDocument = (ClinicalDocument)clinicalDocuments.get(0);
                    }
                    // [BUG]0042260 MODIFY END
                    if (clinicalDocument != null)
                    {
                        logger.debug(
                                "在更新形态学检验报告时对应的病历文档存在true: {}",
                                "params: documentSn = " + documentSn
                                    + "; sourceSystemId = " + sourceSystemId
                                    + "; orgCode = "
                                    + pocdin000040uv04.getOrgCode());
                        logger.debug(
                                "在更新时对应的形态学检验报告存在true: {}",
                                "params: labReportLid = " + labReportLid
                                    + "; sourceSystemId = " + sourceSystemId
                                    + "; orgCode = "
                                    + pocdin000040uv04.getOrgCode());
                        return true;
                    }
                    else
                    {
                        logger.debug(
                                "在更新形态学检验报告时对应的病历文档不存在false: {}",
                                "params: documentSn = " + documentSn
                                    + "; sourceSystemId = " + sourceSystemId
                                    + "; orgCode = "
                                    + pocdin000040uv04.getOrgCode());
                        if (flag)
                        {
                            loggerBS355.error("Message:{},checkDependency:{}",
                                    pocdin000040uv04.toString(),
                                    "在更新形态学检验报告时对应的病历文档不存在false,params: documentSn = "
                                        + documentSn + "; sourceSystemId = "
                                        + sourceSystemId + "; orgCode = "
                                        + pocdin000040uv04.getOrgCode());
                        }
                        return false;
                    }
                }
                else
                {
                    logger.debug(
                            "在更新形态学检验报告时对应的病历文档不存在false: {}",
                            "params: documentSn = " + documentSn
                                + "; sourceSystemId = " + sourceSystemId
                                + "; orgCode = "
                                + pocdin000040uv04.getOrgCode());
                    if (flag)
                    {
                        loggerBS355.error("Message:{},checkDependency:{}",
                                pocdin000040uv04.toString(),
                                "在更新形态学检验报告时对应的病历文档不存在false,params: documentSn = "
                                    + documentSn + "; sourceSystemId = "
                                    + sourceSystemId + "; orgCode = "
                                    + pocdin000040uv04.getOrgCode());
                    }
                    return false;
                }

            }
            else
            {
                logger.error("形态学检验报告本身不存在false: {} 检验报告版本状态: {}",
                        "labResult = " + labResult
                            + ";  params: labReportLid = " + labReportLid
                            + "; sourceSystemId = " + sourceSystemId
                            + "; orgCode = " + pocdin000040uv04.getOrgCode(),
                        pocdin000040uv04.getVersionNumber());
                if (flag)
                {
                    loggerBS355.error("Message:{},checkDependency:{}",
                            pocdin000040uv04.toString(),
                            "形态学检验报告本身不存在false,params: labResult = "
                                + labResult + ";  params: labReportLid = "
                                + labReportLid + "; sourceSystemId = "
                                + sourceSystemId + "; orgCode = "
                                + pocdin000040uv04.getOrgCode() + "; 检验报告版本状态:"
                                + pocdin000040uv04.getVersionNumber());
                }
                return false;
            }
        }
        return true;
    }

    @Override
    @Transactional
    public void saveOrUpdate(POCDIN000040UV04 pocdin000040uv04)
    {
        // $Author: tong_meng
        // $Date: 2013/8/30 15:00
        // [BUG]0036757 ADD BEGIN
        serviceId = pocdin000040uv04.getMessage().getServiceId();
        // [BUG]0036757 ADD END

        // 对形态学检验报告的相关进行新增与更新
        if (Constants.VERSION_NUMBER_INSERT.equals(pocdin000040uv04.getVersionNumber()))
        {
            saveLabResult(pocdin000040uv04);
        }
        else if (Constants.VERSION_NUMBER_UPDATE.equals(pocdin000040uv04.getVersionNumber()))
        {
            updateLabResult(pocdin000040uv04);
        }
    }

    /**
     * 形态学检验报告新增
     * @param pocdin000040uv04 形态学检验报告CDA对象
     */
    private void saveLabResult(POCDIN000040UV04 pocdin000040uv04)
    {
        documentSn = commonService.getSequence(DOCUMENT_SEQUENCE);

        // 获取需要新增的病历文档对象
        ClinicalDocument clinicalDocumentForInsert = getClinicalDocument(pocdin000040uv04);

        // 获取需要新增的形态学检验报告对象
        LabResult labResultForInsert = getLabResult(pocdin000040uv04);

        // 获取需要新增的检验项目对象
        List<List<Object>> labCompositeItemAndResultListForInsert = getLabCompositeItemAndResultList(
                pocdin000040uv04, labResultForInsert);
        // 获取需要新增的影像对象
        /*List<List<Object>> labImageListForInsert =  Constants.MEDICAL_IMAGE_FLAG.equals(MEDICAL_IMAGE_CLOSE) ? new ArrayList<List<Object>>() : getLabImage(
                pocdin000040uv04, labResultForInsert);*/
        List<List<Object>> labImageListForInsert =  !Boolean.parseBoolean(Constants.MEDICAL_IMAGE_FLAG) ? new ArrayList<List<Object>>() : getLabImage(
                pocdin000040uv04, labResultForInsert);
        // Author :jin_peng
        // Date : 2013/09/11 10:12$
        // [BUG]0037243 MODIFY BEGIN
        // $Author: tong_meng
        // $Date: 2013/8/30 15:00
        // [BUG]0036757 MODIFY BEGIN
        // $Author: wei_peng
        // $Date: 2013/3/20 15:04
        // [BUG]0014602 ADD BEGIN
        // 检验报告与检验医嘱关系集合
        List<LabOrderLabResult> labOrderLabResults = pPart.getLabOrderLabResult(
                commonService, labResultForInsert.getLabReportSn(),
                labOrdersList, systemTime, serviceId);
        // [BUG]0014602 ADD END
        // [BUG]0036757 MODIFY END
        // [BUG]0037243 MODIFY END

        // $Author :jin_peng
        // $Date : 2012/09/20 14:47$
        // [BUG]0009691 MODIFY BEGIN
        // 新增形态学检验报告及其关联对象
        labService.saveMorphologyLabResult(pocdin000040uv04.getMessage(),
                clinicalDocumentForInsert, labResultForInsert,
                labCompositeItemAndResultListForInsert, labImageListForInsert,
                labOrderLabResults);
        // [BUG]0009691 MODIFY END

    }

    /**
     * 形态学检验报告修改
     * @param pocdin000040uv04 形态学检验报告CDA对象
     */
    private void updateLabResult(POCDIN000040UV04 pocdin000040uv04)
    {
        // 获取需要更新的病历文档对象
        ClinicalDocument clinicalDocumentForUpdate = getClinicalDocument(pocdin000040uv04);

        // 获取需要更新的形态学检验报告对象
        LabResult labResultForUpdate = getLabResult(pocdin000040uv04);

        // 获取需要新增的和需要删除的检验项目及对应的检验结果对象
        List<List<Object>> labCompositeItemAndResultListForUpdate = getLabCompositeItemAndResultList(
                pocdin000040uv04, labResultForUpdate);

        // 获取需要新增和需要删除的的影像对象
        List<List<Object>> labImageListForUpdate = getLabImage(
                pocdin000040uv04, labResultForUpdate);

        // Author :jia_yanqing
        // Date : 2013/4/17 10:47
        // [BUG]0015141 MODIFY BEGIN
        // 更新形态学检验报告及其关联对象
        labService.updateMorphologyLabResult(pocdin000040uv04.getMessage(),
                clinicalDocumentForUpdate, labResultForUpdate,
                labCompositeItemAndResultListForUpdate, labImageListForUpdate,
                labImageListForDelete, pocdin000040uv04.getOrderLid());
        // [BUG]0015141 MODIFY END
    }

    /**
     * 获取形态学检验报告病历文档对象
     * @param pocdin000040uv04
     * @return 病历文档对象
     */
    public ClinicalDocument getClinicalDocument(
            POCDIN000040UV04 pocdin000040uv04)
    {
        if (Constants.VERSION_NUMBER_INSERT.equals(pocdin000040uv04.getVersionNumber()))
        {
            clinicalDocument = new ClinicalDocument();
            clinicalDocument.setDocumentSn(documentSn);// 文档内部序列号
            clinicalDocument.setVisitSn(visitSn);// 就诊内部序列号
            clinicalDocument.setPatientSn(patientSn);// 患者内部序列号
            clinicalDocument.setPatientDomain(pocdin000040uv04.getPatientDomain());// 域代码
            clinicalDocument.setPatientLid(patientLid);// 患者本地ID
            clinicalDocument.setDocumentLid(pocdin000040uv04.getReportNo());// 文档本地ID
            clinicalDocument.setCreateTime(systemTime);// 创建时间
            clinicalDocument.setDeleteFlag(Constants.NO_DELETE_FLAG);// 删除标志
            clinicalDocument.setUpdateCount(Constants.INSERT_UPDATE_COUNT);// 更新回数

            // $Author:tong_meng
            // $Date:2013/12/03 11:00
            // [BUG]0040270 ADD BEGIN
            clinicalDocument.setOrgCode(pocdin000040uv04.getOrgCode()); // 医疗机构代码
            clinicalDocument.setOrgName(pocdin000040uv04.getOrgName()); // 医疗机构名称
            // [BUG]0040270 ADD END

            // $Author: tong_meng
            // $Date: 2013/8/30 15:00
            // [BUG]0036757 ADD BEGIN
            clinicalDocument.setCreateby(serviceId); // 设置创建人
        }
        else if (Constants.VERSION_NUMBER_UPDATE.equals(pocdin000040uv04.getVersionNumber()))
        {
            clinicalDocument.setUpdateby(serviceId); // 设置创建人
        }
        // [BUG]0036757 ADD END
        clinicalDocument.setDocumentType(pocdin000040uv04.getDocumentType());// 文档类型
        clinicalDocument.setDocumentTypeName(pocdin000040uv04.getDocumentTypeName());// 文档类型名称
        clinicalDocument.setDocumentName(pocdin000040uv04.getTitle());// 文档名称
        // $Author:wei_peng
        // $Date:2012/9/4 11:24
        // $[BUG]0009349 MODIFY BEGIN
        Reporter reporter = getReporter(pocdin000040uv04);
        clinicalDocument.setDocumentAuthorName(reporter.getReporterName());// 文档作者名称
        clinicalDocument.setDocumentAuthor(reporter.getReporterId());// 文档作者ID
        clinicalDocument.setWriteTime(DateUtils.stringToDate(reporter.getReportDate()));// 文档做成时间
        // $[BUG]0009349 MODIFY END
        clinicalDocument.setReviewPerson(pocdin000040uv04.getReviewPerson());// 文档审核人ID
        clinicalDocument.setReviewPersonName(pocdin000040uv04.getReviewPersonName());// 文档审核人姓名
        clinicalDocument.setReviewTime(DateUtils.stringToDate(pocdin000040uv04.getReviewTime()));// 文档审核时间
        clinicalDocument.setSignTime(DateUtils.stringToDate(pocdin000040uv04.getSignTime()));// 提交时间（签章时间）
        clinicalDocument.setSignatureId(pocdin000040uv04.getSignatureId());// 电子签章ID
        // $Author: jin_peng
        // $Date: 2013/10/24 15:50
        // [BUG]0038480 ADD BEGIN
        clinicalDocument.setServiceId(serviceId);

        // [BUG]0038480 ADD END
        clinicalDocument.setUpdateTime(systemTime);// 更新时间

        return clinicalDocument;
    }

    /**
     * 获取形态学检验报告对象
     * @param pocdin000040uv04 形态学检验报告CDA对象
     * @return 检验报告对象
     */
    private LabResult getLabResult(POCDIN000040UV04 pocdin000040uv04)
    {
        if (Constants.VERSION_NUMBER_INSERT.equals(pocdin000040uv04.getVersionNumber()))
        {
            labResult = new LabResult();
            BigDecimal labReportSn = commonService.getSequence(REPORT_SEQUENCE); // 获得检验报告内部序列号
            labResult.setLabReportSn(labReportSn);// 检验报告内部序列号
            labResult.setVisitSn(visitSn); // 就诊内部序列号
            labResult.setPatientSn(patientSn); // 患者内部序列号
            labResult.setDocumentSn(documentSn);// 文档内部序列号
            labResult.setPatientDomain(patientDomain); // 域代码
            labResult.setPatientLid(patientLid); // 患者本地ID
            // Author :jia_yanqing
            // Date : 2013/4/16 17:00
            // [BUG]0015133 MODIFY BEGIN
            // $Author: jia_yanqing
            // $Date: 2012/9/4 14:10
            // [BUG]0009348 MODIFY BEGIN
            labResult.setLabReportLid(pocdin000040uv04.getReportNo()); // 报告本地ID
            // [BUG]0009348 MODIFY END
            // [BUG]0015133 MODIFY END
            labResult.setCreateTime(systemTime); // 创建时间
            labResult.setUpdateCount(Constants.INSERT_UPDATE_COUNT); // 新增时初始更新回数
            labResult.setDeleteFlag(Constants.NO_DELETE_FLAG); // 新增时初始删除标记
            // $Author: tong_meng
            // $Date: 2013/8/30 15:00
            // [BUG]0036757 ADD BEGIN
            labResult.setCreateby(serviceId); // 设置创建人
            labResult.setWithdrawFlag(Constants.NO_WITH_DRAW_FLAG); // 设置召回标识为为召回状态
            // [BUG]0036757 ADD END

            // $Author:tong_meng
            // $Date:2013/12/03 11:00
            // [BUG]0040270 ADD BEGIN
            labResult.setOrgCode(pocdin000040uv04.getOrgCode()); // 医疗机构代码
            labResult.setOrgName(pocdin000040uv04.getOrgName()); // 医疗机构名称
            // [BUG]0040270 ADD END

        }
        // $Author :jin_peng
        // $Date : 2012/10/17 15:36$
        // [BUG]0010520 ADD BEGIN
        // 消息为更新状态时召回状态设为已更新状态
        else if (Constants.VERSION_NUMBER_UPDATE.equals(pocdin000040uv04.getVersionNumber()))
        {
            // $Author: tong_meng
            // $Date: 2013/8/30 15:00
            // [BUG]0036757 ADD BEGIN
            labResult.setUpdateby(serviceId); // 设置创建人
            // [BUG]0036757 ADD END
            labResult.setWithdrawFlag(StringUtils.strToBigDecimal(
                    Constants.REPORT_MODIFY_FLAG, "召回状态")); // 设置召回标识为已更新状态
        }
        else
        {
            labResult.setUpdateby(serviceId); // 设置创建人
            labResult.setWithdrawFlag(Constants.NO_WITH_DRAW_FLAG); // 设置召回标识为为召回状态
        }

        // [BUG]0010520 ADD END

        labResult.setSourceSystemId(pocdin000040uv04.getSourceSystemId());// 源系统id

        // $Author: jin_peng
        // $Date: 2012/10/25 16:20
        // [BUG]0010745 MODIFY BEGIN
        labResult.setWardId(pocdin000040uv04.getWardsNo());// 病区
        // $Author: jia_yanqing
        // $Date: 2012/9/1 14:10
        // [BUG]0009327 ADD BEGIN
        //wang.meng 形态学与微生物共用一个病区
        labResult.setWardName(pocdin000040uv04.getWardsName());// 病区名称
        // [BUG]0009327 ADD END
        // [BUG]0010745 MODIFY END

        labResult.setBedNo(pocdin000040uv04.getBedNo());// 床位号
        labResult.setReviewDate(DateUtils.stringToDate(pocdin000040uv04.getReviewTime())); // 审核日期
        labResult.setReviewerId(pocdin000040uv04.getReviewPerson()); // 审核人ID
        labResult.setReviewerName(pocdin000040uv04.getReviewPersonName()); // 审核人姓名
        // $Author: jia_yanqing
        // $Date: 2012/9/1 14:10
        // [BUG]0009325 MODIFY BEGIN
        labResult.setSubmittingPersonId(pocdin000040uv04.getSubmittingPersonId()); // 送检医师ID
        labResult.setSubmittingPersonName(pocdin000040uv04.getSubmittingPersonName()); // 送检医师姓名
        // [BUG]0009325 MODIFY END
        labResult.setTesterId(pocdin000040uv04.getTesterId());// 检验医师ID
        labResult.setTesterName(pocdin000040uv04.getTesterName());// 检验医师姓名
        labResult.setLabDept(pocdin000040uv04.getLabDept()); // 检验科室
        labResult.setLabDeptName(pocdin000040uv04.getLabDeptName()); // 检验科室名称
        labResult.setRequestTime(DateUtils.stringToDate(pocdin000040uv04.getRequestTime())); // 申请时间
        labResult.setReportMemo(pocdin000040uv04.getReportMemo());// 报告备注
        labResult.setTechnicalNote(pocdin000040uv04.getTechnicalNote()); // 技术备注
        labResult.setCellDevisionStatus(pocdin000040uv04.getCellDevisionStatus());// 细胞分裂状态
        labResult.setCellDevisionName(pocdin000040uv04.getCellDevisionName());// 细胞分裂名称
        labResult.setCellDevisionValue(StringUtils.strToBigDecimal(pocdin000040uv04.getCellDevisionValue(),"细胞分裂值"));// 细胞分裂值
        labResult.setGeneCode(pocdin000040uv04.getGenderCode());// 基因编码
        labResult.setGeneName(pocdin000040uv04.getGeneName()); // 基因名称
        labResult.setGeneValue(pocdin000040uv04.getGeneValue());// 基因值
        //江苏人民医院版本XML不再接入以下注释节点
  /*    labResult.setLaboratoryNo(pocdin000040uv04.getLaboratoryNo()); // 实验室编号
        labResult.setSupplierName(pocdin000040uv04.getSupplierName()); // 提供者姓名
        labResult.setSupplierGender(pocdin000040uv04.getSupplierGender());// 提供者性别
        labResult.setSupplierAge(pocdin000040uv04.getSupplierAge());// 提供者年龄
        labResult.setLastMenstrualTime(DateUtils.stringToDate(pocdin000040uv04.getLastMenstrualPeriod()));// 末次月经时间
        labResult.setMenopause(pocdin000040uv04.getMenopause());// 绝经标识
        labResult.setExpectedAge(StringUtils.strToBigDecimal(
                pocdin000040uv04.getExpectedAge(), "预产年龄"));// 预产年龄
        labResult.setFetalNumber(StringUtils.strToBigDecimal(
                pocdin000040uv04.getFetalNumber(), "胎儿数"));// 胎儿数
        labResult.setGestationalWeekCalcMethod(pocdin000040uv04.getGestationalCalculationBased());// 孕周计算方法
*/        // $Author:wei_peng
        // $Date:2012/9/4 11:24
        // $[BUG]0009349 MODIFY BEGIN
        Reporter reporter = getReporter(pocdin000040uv04);
        labResult.setReporterId(reporter.getReporterId()); // 报告人ID
        labResult.setReporterName(reporter.getReporterName()); // 报告人姓名
        labResult.setReportDate(DateUtils.stringToDate(reporter.getReportDate())); // 报告时间
        // $[BUG]0009349 MODIFY END
        labResult.setPhenomenonPerformance(pocdin000040uv04.getMorphologyReportItem().get(
                0).getPhenomenonPerformance());// 表现现象
        labResult.setTestResultsDetail(pocdin000040uv04.getMorphologyReportItem().get(
                0).getTestResultsDetail());// 检验结果细分
        labResult.setTestResults(pocdin000040uv04.getMorphologyReportItem().get(
                0).getTestResults());// 诊断结果
        labResult.setSamplingTime(DateUtils.stringToDate(pocdin000040uv04.getMorphologySample().get(
                0).getEffectiveTime()));// 采样日期
        labResult.setSamplingPart(pocdin000040uv04.getMorphologySample().get(0).getSamplingPartsBody());// 采样部位
        labResult.setSampleNo(pocdin000040uv04.getMorphologySample().get(0).getSampleNo());// 标本号
        labResult.setSampleType(pocdin000040uv04.getMorphologySample().get(0).getSampleType());// 标本类型代码
        labResult.setSampleTypeName(pocdin000040uv04.getMorphologySample().get(
                0).getSampleTypeName());// 标本类型名称

        // Author :chang_xuewen
        // Date : 2013/8/30 11:00
        // [BUG]0036746 ADD BEGIN
        labResult.setReportTypeCode(pocdin000040uv04.getMorphologyReportTypeCode());
        labResult.setReportTypeName(pocdin000040uv04.getMorphologyReportTypeName());
        // [BUG]0036746 ADD END

        // Author :jia_yanqing
        // Date : 2013/4/26 15:36
        // [BUG]0031630 ADD BEGIN
        // 送检单位
        labResult.setSendHospital(pocdin000040uv04.getMorphologySample().get(0).getSubmitHospital());
        // [BUG]0031630 ADD END

        List<Comment> commentList = pocdin000040uv04.getMorphologyReportItem().get(
                0).getComment();
        if (commentList != null && commentList.size() != 0)
        {
            String comments = "";
            for (int i = 0; i < commentList.size(); i++)
            {
                comments += commentList.get(i).getSupplementaryOpinion() + "&&";
            }
            comments = comments.substring(0, comments.length() - 2);
            labResult.setReportMemo(comments);// 补充意见
        }
        labResult.setUpdateTime(systemTime); // 记录更新时间
        return labResult;
    }

    /**
     * 获取形态学检验报告要新增的与要删除的检验项目及其检验具体结果对象
     * @param pocdin000040uv04
     * @param labResultForInsert
     * @return
     */
    private List<List<Object>> getLabCompositeItemAndResultList(
            POCDIN000040UV04 pocdin000040uv04, LabResult labResultForInsert)
    {
        List<List<Object>> labResultCompositeItemAndResultList = new ArrayList<List<Object>>();
        LabResultCompositeItem labResultCompositeItem = null;
        List<Object> lrci = new ArrayList<Object>();
        List<List<Object>> labResultItem = new ArrayList<List<Object>>();
        List<MorphologyReportItem> morphologyReportItemList = pocdin000040uv04.getMorphologyReportItem();
        if (morphologyReportItemList != null
            && !"".equals(morphologyReportItemList))
        {
            for (MorphologyReportItem morphologyReportItem : morphologyReportItemList)
            {
                labResultCompositeItem = new LabResultCompositeItem();
                BigDecimal compositeItemSn = commonService.getSequence(LAB_ITEM_SEQUENCE);
                labResultCompositeItem.setCompositeItemSn(compositeItemSn);// 检验项目内部序列号
                labResultCompositeItem.setLabReportSn(labResultForInsert.getLabReportSn());// 检验报告内部序列号
                labResultCompositeItem.setItemCode(morphologyReportItem.getLabItemCode());// 检验项编码
                labResultCompositeItem.setItemName(morphologyReportItem.getLabItemName());// 检验项名称
                labResultCompositeItem.setWarnFlag(morphologyReportItem.getCrisisCode());// 检验危险标识代码
                labResultCompositeItem.setWarnFlagName(morphologyReportItem.getCrisisName());// 检验危险标识名称
                labResultCompositeItem.setCreateTime(systemTime); // 创建时间
                labResultCompositeItem.setUpdateTime(systemTime); // 更新时间
                labResultCompositeItem.setUpdateCount(Constants.INSERT_UPDATE_COUNT); // 初始更新回数
                labResultCompositeItem.setDeleteFlag(Constants.NO_DELETE_FLAG); // 初始删除标记

                // $Author: tong_meng
                // $Date: 2013/8/30 15:00
                // [BUG]0036757 ADD BEGIN
                labResultCompositeItem.setCreateby(serviceId); // 设置创建人
                // [BUG]0036757 ADD END

                lrci.add(labResultCompositeItem);
                List<Object> labResultItemList = getLabResultItemList(
                        morphologyReportItem, compositeItemSn);
/*                for(int i=0;i<labResultItemList.size();i++){
                	LabResultItem item = (LabResultItem)labResultItemList.get(i);
                	String itemcode = item.getItemCode();
                	Boolean flag = false;

                	for(int j=i+1;j<labResultItemList.size();j++){
                    	LabResultItem itemR = (LabResultItem)labResultItemList.get(j);
                    	String itemcode1 = itemR.getItemCode();
                		if(itemcode.equals(itemcode1)){
                			flag = true;
                			break;
                		}
                	}
                	if(flag){
                		logger.info("itemcode:"+itemcode+"\n");
                	}
                }*/
                labResultItem.add(labResultItemList);
            }
        }
        labResultCompositeItemAndResultList.add(lrci);
        labResultCompositeItemAndResultList.addAll(labResultItem);

        // 修改操作时需要向返回的结果集中增加的删除对象集合
        if (Constants.VERSION_NUMBER_UPDATE.equals(pocdin000040uv04.getVersionNumber()))
        {
            Map<String, Object> lrciMap = new HashMap<String, Object>();
            lrciMap.put("labReportSn", labResultForInsert.getLabReportSn());
            lrciMap.put("deleteFlag", Constants.NO_DELETE_FLAG);
            List<Object> labResultCompositeItemList = labService.selectLabResultCompositeByCondition(lrciMap);

            // 检验结果删除对象集合
            List<Object> labItemList = new ArrayList<Object>();

            for (Object object : labResultCompositeItemList)
            {
                LabResultCompositeItem resultItem = (LabResultCompositeItem) object;

                Map<String, Object> itemMap = new HashMap<String, Object>();
                itemMap.put("labResultCompositeItemSn",
                        resultItem.getCompositeItemSn());
                itemMap.put("deleteFlag", Constants.NO_DELETE_FLAG);
                List<Object> labResultItemList = labService.selectLabResultItemByCondition(itemMap);
                if (labResultItemList != null && !labResultItemList.isEmpty())
                {
                    for (Object labResultItemDeleteObject : labResultItemList)
                    {
                        labItemList.add(labResultItemDeleteObject);
                    }
                }
            }
            labResultCompositeItemAndResultList.add(labResultCompositeItemList);
            labResultCompositeItemAndResultList.add(labItemList);
        }
        return labResultCompositeItemAndResultList;
    }

    /**
     * 获取检验项目对应的检验结果对象
     * @param morphologyReportItem
     * @param compositeItemSn
     * @return
     */
    private List<Object> getLabResultItemList(
            MorphologyReportItem morphologyReportItem,
            BigDecimal compositeItemSn)
    {
        List<Object> lri = new ArrayList<Object>();
        LabResultItem labResultItem = null;
        List<MorphologyReportResult> morphologyReportResultList = morphologyReportItem.getMorphologyReportResult();
        for (MorphologyReportResult morphologyReportResult : morphologyReportResultList)
        {
            labResultItem = new LabResultItem();
            labResultItem.setLabResultCompositeItemSn(compositeItemSn);// 检验项目内部序列号
            labResultItem.setDisplayOrder(StringUtils.strToBigDecimal(
                    morphologyReportResult.getDisplayOrder(), "显示顺序"));// 显示顺序
            labResultItem.setItemNameCn(morphologyReportResult.getItemNameCn());// 项目中文名称
            labResultItem.setItemCode(morphologyReportResult.getItemCode());// 项目编码
            if (morphologyReportResult.getItemValueOne() != null
                && !"".equals(morphologyReportResult.getItemValueOne()))
            {
                labResultItem.setItemValue(morphologyReportResult.getItemValueOne());// 项目值
                labResultItem.setItemUnit(morphologyReportResult.getItemUnit());// 项目单位
            }
            if (morphologyReportResult.getItemValueTwo() != null
                && !"".equals(morphologyReportResult.getItemValueTwo()))
            {
                labResultItem.setItemValue(morphologyReportResult.getItemValueTwo());// 项目值
            }
            labResultItem.setCorrectMom(StringUtils.strToBigDecimal(
                    morphologyReportResult.getCorrectMom(), "校正值"));// 校正值
            labResultItem.setNormalRefValueText(morphologyReportResult.getNormalRefValueText());// 正常参考值文本
            labResultItem.setRiskValue(morphologyReportResult.getTesterRiskValue());// 风险值
            labResultItem.setAgeRiskValue(morphologyReportResult.getAgeRiskValue());// 年龄风险值
            labResultItem.setNormalRefValueText(morphologyReportResult.getRiskCutoffValue());// 风险截断值
            labResultItem.setLowValue(StringUtils.strToBigDecimal(
                    morphologyReportResult.getLowValue(), "正常低值"));// 正常低值
            labResultItem.setHighValue(StringUtils.strToBigDecimal(
                    morphologyReportResult.getHighValue(), "正常高值"));// 正常高值
            labResultItem.setAverageValue(StringUtils.strToBigDecimal(
                    morphologyReportResult.getAverageValue(), "平均值"));// 平均值
            labResultItem.setStandardDeviation(StringUtils.strToBigDecimal(
                    morphologyReportResult.getSdValue(), "标准差"));// 标准差
            labResultItem.setCreateTime(systemTime); // 创建时间
            labResultItem.setUpdateTime(systemTime); // 更新时间
            labResultItem.setUpdateCount(Constants.INSERT_UPDATE_COUNT); // 初始更新回数
            labResultItem.setDeleteFlag(Constants.NO_DELETE_FLAG); // 初始删除标记

            // $Author: tong_meng
            // $Date: 2013/8/30 15:00
            // [BUG]0036757 ADD BEGIN
            labResultItem.setCreateby(serviceId); // 设置创建人
            // [BUG]0036757 ADD END

            lri.add(labResultItem);
        }
        return lri;
    }

    /**
     * 获取医学影像对象
     * @param pocdin000040uv04
     * @param labResultForInsert
     * @return
     */
    private List<List<Object>> getLabImage(POCDIN000040UV04 pocdin000040uv04,
            LabResult labResultForInsert)
    {
        BigDecimal seq = null;
        List<List<Object>> labImageList = new ArrayList<List<Object>>();
        List<Object> labResultForContent = new ArrayList<Object>();
        MedicalImaging labImage = new MedicalImaging();
        seq = commonService.getSequence(MEDICAL_IMAGING_SEQUENCE);
        labImage.setImagingSn(seq); // 设置影像内部序列号
        labImage.setReportSn(labResultForInsert.getLabReportSn());// 检验报告内部序列号
        labImage.setRefType(Constants.REPORT_TYPE_LAB); // 引用类型
        labImage.setImageContent(getImageContentAfterDecode(pocdin000040uv04.getImageText())); // 影像内容
        labImage.setPromptMessage(pocdin000040uv04.getPrompt()); // 提示信息
        String imageExtension = getImageExtension(pocdin000040uv04.getImageExtension());
        labImage.setImageFormat(imageExtension); // 设置影像格式
        labImage.setImageType(Constants.IMAGE_CONTENT);// 设置图片类型
        labImage.setCreateTime(systemTime); // 创建时间
        labImage.setUpdateTime(systemTime); // 更新时间
        labImage.setUpdateCount(Constants.INSERT_UPDATE_COUNT); // 初始更新回数
        labImage.setDeleteFlag(Constants.NO_DELETE_FLAG); // 初始删除标记
        // $Author: tong_meng
        // $Date: 2013/8/30 15:00
        // [BUG]0036757 ADD BEGIN
        labImage.setCreateby(serviceId); // 设置创建人
        // [BUG]0036757 ADD END

        labResultForContent.add(labImage);
        labImageList.add(labResultForContent);

        // CDA中影像信息
        List<MorphologyImageContent> morphologyImageContentContentList = pocdin000040uv04.getMorphologySample().get(
                0).getMorphologyImageContent();
        if (morphologyImageContentContentList != null
            && morphologyImageContentContentList.size() != 0)
        {
            List<Object> labImageListForInsert = new ArrayList<Object>();
            for (MorphologyImageContent morphologyImageContent : morphologyImageContentContentList)
            {
                labImage = new MedicalImaging();
                seq = commonService.getSequence(MEDICAL_IMAGING_SEQUENCE);
                labImage.setImagingSn(seq); // 设置影像内部序列号
                labImage.setReportSn(labResultForInsert.getLabReportSn());// 检验报告内部序列号
                labImage.setRefType(Constants.REPORT_TYPE_LAB); // 引用类型
                labImage.setImageContent(getImageContentAfterDecode(morphologyImageContent.getImageText())); // 影像内容
                labImage.setPromptMessage(morphologyImageContent.getPrompt()); // 提示信息
                String cdaImageExtension = getImageExtension(morphologyImageContent.getImageExtension());
                labImage.setImageFormat(cdaImageExtension); // 设置影像格式
                labImage.setImageType(Constants.MEDICAL_IMAGE);// 设置图片类型
                labImage.setCreateTime(systemTime); // 创建时间
                labImage.setUpdateTime(systemTime); // 更新时间
                labImage.setUpdateCount(Constants.INSERT_UPDATE_COUNT); // 初始更新回数
                labImage.setDeleteFlag(Constants.NO_DELETE_FLAG); // 初始删除标记
                // $Author: tong_meng
                // $Date: 2013/8/30 15:00
                // [BUG]0036757 ADD BEGIN
                labImage.setCreateby(serviceId); // 设置创建人
                // [BUG]0036757 ADD END

                labImageListForInsert.add(labImage);
            }
            labImageList.add(labImageListForInsert);
        }

        // 通过versionNumber判断进行新增前的删除,向要返回的结果集合中装入对应影像集合
        if (Constants.VERSION_NUMBER_UPDATE.equals(pocdin000040uv04.getVersionNumber()))
        {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("reportSn", labResultForInsert.getLabReportSn());
            map.put("refType", Constants.REPORT_TYPE_LAB);
            map.put("deleteFlag", Constants.NO_DELETE_FLAG);
            // Author :jia_yanqing
            // Date : 2013/4/17 10:47
            // [BUG]0015141 MODIFY BEGIN
            labImageListForDelete = labService.selectLabImageByCondition(map);
            // [BUG]0015141 MODIFY END
        }
        return labImageList;
    }

    /**
     * 图片扩展名为image/jpg的形式时进行判断取值
     * @param imageExtension
     * @return
     */
    public String getImageExtension(String imageExtension)
    {
        String extension = null;
        if (!StringUtils.isEmpty(imageExtension))
        {
            if (imageExtension.indexOf("/") > 0)
            {
                if (imageExtension.split("/").length > 1)
                {
                    extension = imageExtension.split("/")[1];
                }
                else
                {
                    extension = "";
                }
            }
        }
        return extension;
    }

    // $Author :jia_yanqing
    // $Date : 2012/9/7 15:30$
    // [BUG]0009507 MODIFY BEGIN
    /**
     * 将消息中传来的BASE64格式的影像内容解码为字节数组类型
     * @param labImageDto
     * @return byte[]
     */
    public byte[] getImageContentAfterDecode(String imageText)
    {
        byte[] bt = null;
        if (imageText != null && !"".equals(imageText))
        {
            bt = Base64.decodeBase64(imageText);
        }
        return bt;
    }

    // [BUG]0009507 MODIFY END

    // $Author:wei_peng
    // $Date:2012/9/4 11:24
    // $[BUG]0009349 ADD BEGIN
    /**
     * 获取消息中的报告者信息（时间最近的报告人信息）
     * @param pocdin000040uv04
     * @return 报告人
     */
    private Reporter getReporter(POCDIN000040UV04 pocdin000040uv04)
    {
        // 报告人（时间最近的）
        Reporter reporter = new Reporter();
        List<Reporter> reporters = pocdin000040uv04.getReporter();
        // 遍历消息中的报告人信息
        for (int i = 0; i < reporters.size(); i++)
        {
            Date date1 = DateUtils.stringToDate(reporter.getReportDate());
            Date date2 = DateUtils.stringToDate(reporters.get(i).getReportDate());
            // 比较报告时间，将报告时间最近的赋值给结果变量
            if ((date1 != null && date2 != null && date2.after(date1))
                || date1 == null)
            {
                reporter = reporters.get(i);
            }
        }
        return reporter;
    }
    // $[BUG]0009349 ADD END

}
