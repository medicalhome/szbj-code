package com.yly.cdr.dto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.yly.cdr.core.Constants;
import com.yly.cdr.dao.TimerAndInpatientDao;
import com.yly.cdr.entity.NursingOperation;
import com.yly.cdr.util.DateUtils;
import com.yly.cdr.util.StringUtils;

/**
 * 
 * [FUN]V05-101时间轴列表及住院视图列表
 * @version 1.0, 2012/05/23  
 * @author 金鹏
 * @since 1.0
 * 
 */
public class TimerAndInpatientDto implements Cloneable
{
    // 就诊
    public static final String TIMER_VISIT = "visit";

    // 住院
    public static final String INPATIENT_VISIT = "inpatient";

    // 体格检查
    public static final String PHYSICAL = "physical";

    // 诊断
    public static final String DIAGNOSIS = "diagnosis";

    // 药物医嘱
    public static final String DRUG_ORDER = "drugOrder";

    // $Author :jin_peng
    // $Date : 2012/10/10 18:28$
    // [BUG]0010239 ADD BEGIN
    // 药物长期医嘱
    public static final String LONG_TERM_DRUG_ORDER = "drugOrderLong";

    // 药物长期医嘱（查询总条数）
    public static final String LONG_TERM_DRUG_ORDER_COUNT = "drugOrderLongCount";

    // 长期药物医嘱时第一次匹配给定的住院日期
    public static final int IS_FIRST = 1;

    // 长期药物医嘱时不是第一次匹配给定的住院日期
    public static final int IS_FIRST_NOT = 0;

    // [BUG]0010239 ADD END
    
    // $Author :tong_meng
    // $Date : 2013/1/4 11:00$
    // [BUG]0012698 ADD BEGIN
    // 药物临时医嘱
    public static final String TEMPORARY_DRUG_ORDER = "temporaryDrugOrder";
    
    // [BUG]0012698 ADD END
    
    // 检查
    public static final String EXAMINATION = "examination";

    // 检验
    public static final String LAB = "lab";

    // 存在异常标识
    public static final String EXCEPTION_EXISTS_FLAG = "1";

    // 手术
    public static final String PROCEDURE = "procedure";

    // 病例文书
    public static final String DOCUMENTATION = "documentation";

    // 非药物医嘱
    public static final String NO_DRUG_ORDER = "noDrugOrder";

    // 某日期后的不存在该病人的就诊记录
    public static final String NO_EXISTS_FLAG = "0";

    // 三测单中日期的默认颜色
    public static final String COLOR_BLACK = "#000000";

    // 三测单中就诊
    public static final String COLOR_BLUE = "#2d59b2";

    // 检验报告存在异常时标识颜色
    public static final String COLOR_LAB = "#ff3333";

    // 分页后退标识
    public static final String PAGING_PREVIOUS = "previous";

    // 分页前进标识
    public static final String PAGING_NEXT = "next";

    // 主要诊断标识
    public static final String MAIN_DIAGNOSIS_FLAG = "1";

    // 体温单位
    public static final String TEMPERATURE_ITEM_UNIT = "℃";

    // 血压单位
    public static final String BLOOD_PRESSURE_ITEM_UNIT = "mmHg";

    // 脉搏单位
    public static final String PULSE_ITEM_UNIT = "次";

    // 检验异常提示列表偶数行背景色
    public static final String TR_EVEN = "odd";

    // 检验异常提示列表奇数行背景色
    public static final String TR_ODD = "even";

    // 检验结果低于正常值标识
    public static final String DISPLAY_LOW_VALUE = "低";

    // 检验结果高于正常值标识
    public static final String DISPLAY_HIGH_VALUE = "高";

    // 检验结果正常标识
    public static final String DISPLAY_NORMAL_VALUE = "正常";

    // 某日期后的是否存在就诊标识
    private String existsFlag;

    // 就诊内部序列号
    private BigDecimal visitSn;

    // 入院（就诊）日期
    private Date visitDate;

    // 就诊类型
    private String visitTypeCode;

    private String visitTypeName;

    // 就诊科室
    private String visitDeptId;

    private String visitDeptName;

    // 就诊医生代码
    private String visitDoctorId;

    // 就诊医生姓名
    private String visitDoctorName;

    // 出院日期
    private Date dischargeDate;

    // 诊断内部序列号
    private BigDecimal diagnosisSn;

    // 疾病代码
    private String diseaseCode;

    // 疾病名称
    private String diseaseName;

    // 诊断分类
    private String diagnosisTypeName;

    // 是否主要诊断标识
    private String mainDiagnosisFlag;

    // $Author :jin_peng
    // $Date : 2012/12/25 16:27$
    // [BUG]0012698 ADD BEGIN
    // 下诊断科室
    private String diagnosticDeptName;

    // 诊断医生
    private String diagnosisDoctorName;

    // 是否待查
    private String uncertainFlag;

    // [BUG]0012698 ADD END

    // 药物医嘱内部序列号
    private BigDecimal orderSn;

    // 处方内部序列号
    private BigDecimal prescriptionSn;

    // 药物代码
    private String drugCode;

    // 药物商品名称
    private String drugName;

    // 次剂量
    private String dosage;

    // 次剂量单位
    private String dosageUnit;

    // 用药频率
    private String medicineFrenquency;

    // 检查报告内部序列号
    private BigDecimal examReportSn;

    // 检查方法代码
    private String examinationMethod;

    // 检查方法描述
    private String examinationMethodName;

    
    // 检查项目代码
    private String examinationItem;
    
    // $Author :tong_meng
    // $Date : 2013/1/5 13:49$
    // [BUG]0012698 ADD BEGIN
    // 检查医生姓名
    private String examinationDoctorName;
    
    // $Author :tong_meng
    // $Date : 2013/1/11 13:49$
    // [BUG]0013311 MODIFY BEGIN
    // 检查日期
    private Date examinationDate;
    // [BUG]0013311 MODIFY END
    
    // 检查部位
    private String examinationRegionName;
    
    // 医嘱状态名称
    private String orderStatusName;
    
    // 申请日期
    private String requestDate ;
    
    // [BUG]0012698 ADD END
    
    // 检查项目名称
    private String examinationItemName;

    // 召回标识
    private String withdrawFlag;

    // 检查部分
    private String examinationRegion;

    // 检查项目内部序列号
    private BigDecimal itemSn;

    // 体格检查项目代码
    // 检验大项代码
    private String itemCode;

    // 体格检查项目名称
    // 检验大项名称
    private String itemName;

    // $Author:wei_peng
    // $Date:2012/10/9 16:18
    // [BUG]0010243 ADD BEGIN

    // 检查医嘱内部序列号
    private BigDecimal examOrderSn;

    // 检验医嘱内部序列号
    private BigDecimal labOrderSn;

    // 检查类别 
    private String itemClass;
    
    // 检查类别名称
    private String itemClassName;

    // 报告医生名称
    private String reportDoctorName;

    // 检查科室名称
    private String examDeptName;

    // 检查结果（影像学表现）
    private String imagingFinding;

    // 检验科室
    private String labDeptName;

    // 检验医生
    private String testerName;

    // 检验时间
    private Date testDate;
    
    // 检验结果
    private String testResults;

    // 住院视图 检查或检验项目的鼠标Tip内容
    private String examOrLabTipContent;

    // [BUG]0010243 ADD END

    // 检验大项内部序列号
    private BigDecimal compositeItemSn;

    // 检验报告内部序列号
    private BigDecimal labReportSn;

    // 检验具体结果
    // 项目中文名称
    private String itemNameCn;

    // 项目值
    private String itemValue;

    // 项目单位
    private String itemUnit;

    // 正常低值
    private Double lowValue;

    // 正常高值
    private Double highValue;

    // $Author :jin_peng
    // $Date : 2012/08/23 11:10$
    // [BUG]0007863 ADD BEGIN
    // 警告低值
    private Double warnLowValue;

    // 警告高值
    private Double warnHighValue;

    // 微生物报告特有
    // 药物中文名称
    private String drugNameCn;

    // 药物英文名称
    private String drugNameEn;

    // 折点
    private String breakpoint;

    // KB法测定值
    private String kb;

    // 细菌MIC法测定值
    private String mic;

    // 敏感度
    private String sensitivity;

    // [BUG]0007863 ADD END

    // 检验结果是否存在异常
    private String exceptionFlag;

    // 检验项目异常信息显示内容
    private String exceptionContent;

    // 危险值编码
    private String warnFlag;

    // 手术名称
    private String operationName;

    // $Author :tong_meng
    // $Date : 2013/1/4 13:49$
    // [BUG]0012698 ADD BEGIN
    // 手术执行科室
    private String surgicalDeptName;
    
    // 病区
    private String wardName;
    
    // $Author:tong_meng 
    // $Date : 2013/11/07 10:10
    // $[BUG]0039034 ADD BEGIN
    //住院患者床号
    private String bedNo;
    
    //住院患者床号
    private String dischargeWardName;
    
    //住院患者床号
    private String dischargeBedNo;
    
    
    // $[BUG]0039034 ADD END

    // 医嘱类型名称
    private String orderTypeName;
    
    // 高值低值标志
    private String resultHighLowFlagStr;
    
    // 
    private String normalRefValueText;
    
    // 
    private String qualitativeResults;
    
    // 报告备注
    private String reportMemo;
    
    // [BUG]0012698 ADD BEGIN
    
    // 手术日期
    private Date operationDate;

    // 手术内部序列号
    private BigDecimal procedureSn;

    // 病例文书内部序列号
    private BigDecimal documentSn;
    
    // 病例文书文档ID
    private String documentLid;

    // 文档名称
    private String documentName;

    // 文档类型
    private String documentType;
    
    // 文档作者名称
    private String documentAuthorName;
    
    // 文档修改者姓名
    private String documentModifierName;

    // 文档类型名称
    private String documentTypeName;

    // 编写日期
    private Date writeTime;

    // 文档提交日期
    private Date submitTime;

    // 医嘱标识
    private String orderFlag;

    // 医嘱名称
    private String orderName;

    // 开嘱时间
    private Date orderTime;

    // 医嘱标题
    private String orderTitle;

    // 医嘱部分 路径
    private String orderPath;

    // 医嘱类型
    private String orderType;

    // 住院日期
    private Date inpatientDate;

    // 三测单横坐标值和图形数据
    private String xhData;

    // 体格检查检查时间(小时)
    private int examTimeHour;

    // 项目结果
    private String itemResult;

    // 项目结果单位
    private String itemResultUnit;

    // 当在一页显示的住院日期中就诊改变时改变颜色标识
    private String inpatientDateDisplayColor;

    // 住院次数
    private String inpatientDays;

    // 源系统ID
    private String sourceSystemId;

    // $Author :jin_peng
    // $Date : 2012/09/27 10:48$
    // [BUG]0010132 ADD BEGIN
    // 每页实际显示条数(向前翻页)
    private int actuallyDisplayForwardCount;

    // 每页实际显示条数(向后翻页)
    private int actuallyDisplayBackwardCount;

    // 住院次数循环末值
    private BigDecimal visitTimes;

    // 住院记录实际显示号
    private int rowRn;

    // 实际查询记录数
    private int actuallyCount;

    // [BUG]0010132 ADD END

    // $Author :jin_peng
    // $Date : 2012/10/09 17:09$
    // [BUG]0010239 ADD BEGIN
    // 停嘱时间
    private Date stopTime;

    // 取消时间
    private Date cancelTime;

    // 使用频率名称
    private String medicineFreqName;

    // 皮试结果
    private String skinTestResult;

    // 药品通用名称
    private String approvedDrugName;
    
    // $Author :tong_meng
    // $Date : 2012/12/27 16:27$
    // [BUG]0012698 ADD BEGIN
    // 患者内部序列号
    private String patientSn;
    
    //药物类型
    private String medicineTypeName;
    
    // 药物剂型
    private String medicineForm;
    
    /**
     * $Author :yang_mingjie
     * $Date : 2014/06/09 10:09$
     * [BUG]0044564 MODIFY BEGIN 
     * */
    //药品规格
    private String specification;
    //药物化学名（打印名）
    private String name;
    /**
     * [BUG]0044564 MODIFY End
     * */
    
    // 总剂量
    private String totalDosage;
    
    // 总计量单位
    private String totalDosageUnit;
    
    // 检查项目影像学结论
    private String eiImagingConclusion;
    
    // 检查项目影像学表现
    private String eiImagingFinding;
    
    // 检查报告影像学结论
    private String erImagingConclusion;
    
    // 检查项目影像学表现
    private String erImagingFinding;
    
    // 检查执行科室
    private String execDeptName;
    
    // [BUG]0012698 ADD BEGIN
    
    // 用药途径编码
    private String routeCode;

    // 用药途径名称
    private String routeName;

    // 医嘱开始时间
    private Date orderStartTime;

    // 长期医嘱判断是否是第一次匹配给定住院时间，1为是，0为不是
    private int isFirst;

    // 长期药物医嘱曲线颜色
    private String lineColor;

    // 每个住院日期对应的长期医嘱记录总条数
    private int longTermEachCount;

    // 就诊状态
    private String visitStatusCode;

    // [BUG]0010239 ADD END

    // Author :tong_meng
    // Date : 2013/11/26 14:49
    // [BUG]0040006 ADD BEGIN
    private Date entranceTime;
    // [BUG]0040006 ADD END
    
    // $Author:chang_xuewen
    // $Date : 2013/12/19 14:10
    // $[BUG]0040770 ADD BEGIN
    private String orgCode;
    // $[BUG]0040770 ADD END
    
    // $Author :jin_peng
    // $Date : 2012/08/23 11:10$
    // [BUG]0007863 MODIFY BEGIN
    /**
     * 获取页面显示的检验项目及存在异常的检验具体结果信息
     * @param lrciList 检验大项对象集合
     * @param timerAndInpatientDao 时间轴及住院视图共同用到的dao
     * @return 检验项目及存在异常的检验具体结果信息
     */
    public List<TimerAndInpatientDto> getLabDtoList(
            List<TimerAndInpatientDto> orderList,
            TimerAndInpatientDao timerAndInpatientDao)
    {
        List<TimerAndInpatientDto> resList = null;
        String scSystemId = null;

        // 当检验大项不为空时判断是否有检验异常信息
        if (orderList != null && !orderList.isEmpty())
        {
            // 创建检验项及检验异常结果对象集合
            resList = new ArrayList<TimerAndInpatientDto>();

            for (TimerAndInpatientDto orderDto : orderList)
            {
                // 获取源系统ID
                scSystemId = orderDto.getSourceSystemId();

                // 当报告类型为形态学或源系统id为空时不做异常信息构造处理
                if (Constants.SOURCE_MORPHOLOGY.equals(scSystemId)
                    || StringUtils.isEmpty(scSystemId))
                {
                    resList.add(orderDto);
                    continue;
                }

                // 报告类型为微生物报告时，构造异常信息，否则为普通检验报告构造异常信息
                if (Constants.SOURCE_MICROBE.equals(scSystemId))
                {
                    // 获取微生物报告异常信息内容
                    List<TimerAndInpatientDto> microbeItemList = timerAndInpatientDao.selectMicrobeResultItemList(orderDto.getLabOrderSn());

                    // 如果获取的异常信息内容不为空则说明存在异常
                    if (microbeItemList != null && !microbeItemList.isEmpty())
                    {
                        // 设置微生物报告相关页面展示的异常信息
                        setExceptionMessages(
                                orderDto,
                                microbeStructurePromptHtmlContent(microbeItemList));
                    }
                }
                else if (Constants.SOURCE_LAB.equals(scSystemId))
                {
                    // 当检验大项危险值信息不存在时说明有具体检验结果比较项，获取相应具体检验结果信息集合
                    List<TimerAndInpatientDto> labItemList = timerAndInpatientDao.selectLabResultItemList(orderDto.getLabOrderSn());
                    // 如果存在异常结果信息时，获取页面需提示的相关信息
                    String promptContent = structurePromptHtmlContent(labItemList);

                    // 当异常提示信息存在时，设置相关异常信息到返回集合中
                    if (promptContent != null)
                    {
                        // 设置检验具体结果相关异常显示信息
                        setExceptionMessages(orderDto, promptContent);
                    }
                }

                resList.add(orderDto);
            }
        }
        else
        {
            // 当不存在异常信息时，设置返回集合为原传入检验项集合
            resList = orderList;
        }

        return resList;
    }

    // [BUG]0007863 MODIFY END

    public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	// $Author:tong_meng
    // $Date:2013/11/19 09:02
    // $[BUG]0039539 MODIFY BEGIN
    public List<TimerAndInpatientDto> getExamDtoList(
            List<TimerAndInpatientDto> examList,
            TimerAndInpatientDao timerAndInpatientDao)
    {
        StringBuffer stringBuffer = null;
        
        if (examList != null && !examList.isEmpty())
        {
            for (TimerAndInpatientDto examOrder : examList)
            {
                stringBuffer = new StringBuffer();
                stringBuffer.append("<table id=tblid style=width:400px;border:0px; cellspacing=1 cellpadding=2 class=table>");
                if (examOrder.getExamReportSn() == null)
                {
                    List<NursingOperation> nursingOptList = timerAndInpatientDao.selectNursingOptByOrderSn(examOrder.getExamOrderSn());
    
                    // $Author:jin_peng
                    // $Date:2012/11/01 16:03
                    // $[BUG]0010908 MODIFY BEGIN
                    if (nursingOptList != null && !nursingOptList.isEmpty())
                    {
                        StringBuffer operatorNames = new StringBuffer();
                        StringBuffer operatorItemNames = new StringBuffer();
                        StringBuffer operationTimes = new StringBuffer();
                        for (NursingOperation nursingOpt:nursingOptList)
                        {
                            operatorNames.append("<td>"
                                    + (StringUtils.isEmpty(nursingOpt.getOperatorName()) ? ""
                                            : nursingOpt.getOperatorName()) + "</td>");
                            operatorItemNames.append("<td><FONT>"
                                    // Author:jia_yanqing
                                    // Date:2013/7/2 16:55
                                    // [BUG]0033848 MODIFY BEGIN
                                    + (StringUtils.isEmpty(nursingOpt.getOrderStatusName()) ? ""
                                            : nursingOpt.getOrderStatusName())
                                            + "</FONT></td>");
                                   // [BUG]0033848 MODIFY END
                            operationTimes.append("<td>"
                                    + (nursingOpt.getOperationTime() == null ? ""
                                            : DateUtils.dateToString(
                                                    nursingOpt.getOperationTime(),
                                                    "yyyy-MM-dd HH:mm")) + "</td>");                            
                        }
                        stringBuffer.append("<tr height=24 ><td><b>操作人姓名</b></td>").append(operatorNames).append("</tr>");
                        stringBuffer.append("<tr height=24 ><td><b>操作名称</b></td>").append(operatorItemNames).append("</tr>");
                        stringBuffer.append("<tr height=24 ><td><b>操作时间</b></td>").append(operationTimes).append("</tr>");
                    }
                    else
                    {
                        stringBuffer.append("<tr class=NURSINGNONE height=24></tr>");
                    }
                }
                else
                {
                    stringBuffer.append("<tr class=even height=24>");
                    stringBuffer.append("<td class=label><FONT>检查类别：</FONT></td><td class=dataitem>"
                        + (examOrder.getItemClassName() == null ? ""
                                : examOrder.getItemClassName()) + "</td>");
                    stringBuffer.append("<td class=label>报告医生：</td><td class=dataitem>"
                        + (examOrder.getReportDoctorName() == null ? ""
                                : examOrder.getReportDoctorName()) + "</td>");
                    stringBuffer.append("</tr>");
                    stringBuffer.append("<tr class=odd height=24>");
                    stringBuffer.append("<td class=label>检查科室：</td><td class=dataitem>"
                        + (examOrder.getExamDeptName() == null ? ""
                                : examOrder.getExamDeptName()) + "</td>");
                    stringBuffer.append("<td class=label>检查结果：</td><td class=dataitem>"
                        + (examOrder.getImagingFinding() == null ? ""
                                : StringUtils.replaceStr(examOrder.getImagingFinding()))
                        + "</td>");
                    stringBuffer.append("</tr>");
                }
    
                // $[BUG]0010908 MODIFY END
    
                stringBuffer.append("</table>");
                examOrder.setExamOrLabTipContent(stringBuffer.toString());
            }
        }
        return examList;
    }
    // $[BUG]0039539 MODIFY END    
    
    
    // $Author :jin_peng
    // $Date : 2012/08/23 11:10$
    // [BUG]0007863 MODIFY BEGIN
    /**
     * 获取检验具体结果页面显示异常内容
     * @param labItemList 检验大项对应的检验具体结果信息
     * @return 页面显示的检验具体结果的异常提示信息
     */
    private String structurePromptHtmlContent(
            List<TimerAndInpatientDto> labItemList)
    {
        StringBuffer stringBuffer = null;
        // 是否存在异常标识，用来开始构造异常内容
        boolean isExceptionFlag = false;
        // 用来判断检验具体结果值是否为可判断的数值
        String pattern = "([0-9]+)|([0-9]+\\.{1}\\d+)";

        if (labItemList != null && !labItemList.isEmpty())
        {
            // 用来区分显示行的奇偶项
            int count = 0;
            // 奇偶行的显示样式
            String oddOrEven = null;

            for (TimerAndInpatientDto labItemDto : labItemList)
            {
                // 当检验具体值不存在时继续下一项
                if (labItemDto.getItemValue() == null)
                {
                    continue;
                }

                // 判断具体结果值是否为可用来判断的数值
                if (labItemDto.getItemValue().matches(pattern))
                {
                    // 得到具体结果值
                    Double itemValue = Double.parseDouble(labItemDto.getItemValue());
                    // 得到正常值低值
                    Double lowValue = labItemDto.getLowValue();
                    // 得到正常值高值
                    Double highValue = labItemDto.getHighValue();
                    // 得到正常值低值
                    Double warnLowValue = labItemDto.getWarnLowValue();
                    // 得到正常值低值
                    Double warnHighValue = labItemDto.getWarnHighValue();
                    // 与正常值比较的结果标识
                    int eFlag = compare(lowValue, highValue, itemValue);
                    // 与正常值比较的结果标识
                    int eWarnFlag = compare(warnLowValue, warnHighValue,
                            itemValue);

                    // 当具体结果值超出正常值范围时进行页面异常提示信息构造
                    if ((eFlag != 0 && eFlag != -2)
                        || (eWarnFlag != 0 && eWarnFlag != -2))
                    {
                        // 用来标识只构造一次提示信息头内容
                        if (isExceptionFlag == false)
                        {
                            // 构造异常信息头内容
                            stringBuffer = new StringBuffer();

                            stringBuffer.append("<table id=tblid style=width:400px; cellspacing=1 cellpadding=2 class=table > ");
                            stringBuffer.append("<tr class=tabletitle> ");
                            stringBuffer.append(" <th height=28 align=center>项目名称</th> ");
                            stringBuffer.append(" <th height=28 align=center>项目值</th> ");

                            stringBuffer.append(" <th height=28 align=center>正常低值</th> ");
                            stringBuffer.append(" <th height=28 align=center>正常高值</th> ");
                            stringBuffer.append(" <th height=28 align=center>与正常值比较</th> ");

                            stringBuffer.append(" <th height=28 align=center>警告值低值</th> ");
                            stringBuffer.append(" <th height=28 align=center>警告值高值</th> ");
                            stringBuffer.append(" <th height=28 align=center>与警告值比较</th> ");

                            stringBuffer.append("</tr> ");

                            // 开始进行异常信息具体内容部分构造
                            isExceptionFlag = true;
                        }

                        // 得到奇偶行的显示样式
                        if (count % 2 == 0)
                        {
                            // 偶数行样式
                            oddOrEven = TR_EVEN;
                        }
                        else
                        {
                            // 奇数行样式
                            oddOrEven = TR_ODD;
                        }

                        // 构造异常具体内容信息
                        stringBuffer.append("<tr class=" + oddOrEven + " >");

                        stringBuffer.append(" <td height=24 align=left>"
                            + StringUtils.nullToEmpty(labItemDto.getItemNameCn()
                                + "") + "</td> ");
                        stringBuffer.append(" <td height=24 align=right>"
                            + StringUtils.nullToEmpty(itemValue + "")
                            + "</td> ");

                        // 正常值比较相关信息
                        stringBuffer.append(" <td height=24 align=right>"
                            + StringUtils.nullToEmpty(lowValue + "") + "</td> ");
                        stringBuffer.append(" <td height=24 align=right>"
                            + StringUtils.nullToEmpty(highValue + "")
                            + "</td> ");
                        stringBuffer.append(" <td height=24 align=center style=color:red>"
                            + StringUtils.nullToEmpty(getLowOrHighFlagValue(eFlag)
                                + "") + "</td> ");

                        // 警告值比较相关信息
                        stringBuffer.append(" <td height=24 align=right>"
                            + StringUtils.nullToEmpty(warnLowValue + "")
                            + "</td> ");
                        stringBuffer.append(" <td height=24 align=right>"
                            + StringUtils.nullToEmpty(warnHighValue + "")
                            + "</td> ");
                        stringBuffer.append(" <td height=24 align=center style=color:red>"
                            + StringUtils.nullToEmpty(getLowOrHighFlagValue(eWarnFlag)
                                + "") + "</td> ");

                        stringBuffer.append("</tr>");

                        count++;
                    }
                }
            }

            // 构造一次列表尾标签
            if (isExceptionFlag == true)
            {
                stringBuffer.append("</table>");
            }
        }

        return stringBuffer == null ? null : stringBuffer.toString();
    }

    // [BUG]0007863 MODIFY END

    // $Author :jin_peng
    // $Date : 2012/08/23 14:10$
    // [BUG]0007863 ADD BEGIN
    /**
     * 获取检验具体结果页面显示异常内容(微生物报告)
     * @param microbeItemList 检验大项对应的检验具体结果信息及抗菌药物信息
     * @return 页面显示的检验具体结果及抗菌药物的异常提示信息
     */
    private String microbeStructurePromptHtmlContent(
            List<TimerAndInpatientDto> microbeItemList)
    {
        StringBuffer stringBuffer = null;
        // 是否存在异常标识，用来开始构造异常内容
        boolean isExceptionFlag = false;

        if (microbeItemList != null && !microbeItemList.isEmpty())
        {
            // 用来区分显示行的奇偶项
            int count = 0;
            // 奇偶行的显示样式
            String oddOrEven = null;

            for (TimerAndInpatientDto microbeItemDto : microbeItemList)
            {
                // 用来标识只构造一次提示信息头内容
                if (isExceptionFlag == false)
                {
                    // 构造异常信息头内容
                    stringBuffer = new StringBuffer();

                    stringBuffer.append("<table id=tbl style=width:400px; cellspacing=1 cellpadding=2 class=table > ");
                    stringBuffer.append("<tr class=tabletitle> ");
                    stringBuffer.append(" <th height=28 align=center width=20%>项目名称</th> ");
                    stringBuffer.append(" <th height=28 align=center width=20%>药物名称</th> ");
                    stringBuffer.append(" <th height=28 align=center width=15%>折点</th> ");
                    stringBuffer.append(" <th height=28 align=center width=15%>KB</th> ");
                    stringBuffer.append(" <th height=28 align=center width=15%>MIC</th> ");
                    stringBuffer.append(" <th height=28 align=center width=15%>敏感度</th> ");
                    stringBuffer.append("</tr> ");

                    // 开始进行异常信息具体内容部分构造
                    isExceptionFlag = true;
                }

                // 得到奇偶行的显示样式
                if (count % 2 == 0)
                {
                    // 偶数行样式
                    oddOrEven = TR_EVEN;
                }
                else
                {
                    // 奇数行样式
                    oddOrEven = TR_ODD;
                }

                // 构造异常具体内容信息
                stringBuffer.append("<tr class=" + oddOrEven + " >");

                stringBuffer.append(" <td height=24 align=left>"
                    + StringUtils.nullToEmpty(microbeItemDto.getItemNameCn()
                        + "") + "</td> ");
                stringBuffer.append(" <td height=24 align=left>"
                    + StringUtils.nullToEmpty(microbeItemDto.getDrugNameCn()
                        + "") + "</td> ");
                stringBuffer.append(" <td height=24 align=left>"
                    + StringUtils.nullToEmpty(microbeItemDto.getBreakpoint()
                        + "") + "</td> ");
                stringBuffer.append(" <td height=24 align=left>"
                    + StringUtils.nullToEmpty(microbeItemDto.getKb() + "")
                    + "</td> ");
                stringBuffer.append(" <td height=24 align=left>"
                    + StringUtils.nullToEmpty(microbeItemDto.getMic() + "")
                    + "</td> ");
                stringBuffer.append(" <td height=24 align=left>"
                    + StringUtils.nullToEmpty(microbeItemDto.getSensitivity()
                        + "") + "</td> ");

                stringBuffer.append("</tr>");

                count++;
            }
        }

        // 构造一次列表尾标签
        if (isExceptionFlag == true)
        {
            stringBuffer.append("</table>");
        }

        return stringBuffer == null ? null : stringBuffer.toString();
    }

    // [BUG]0007863 ADD END

    /**
     * 设置页面需要的异常信息
     * @param lrciDto 需设置的检验大项对象
     * @param promptExceptionContent 页面提示的异常信息内容
     */
    private void setExceptionMessages(TimerAndInpatientDto orderDto,
            String promptExceptionContent)
    {
        // 设置否是存在异常
        orderDto.setExceptionFlag(TimerAndInpatientDto.EXCEPTION_EXISTS_FLAG);

        // 设置异常提示内容
        orderDto.setExceptionContent(promptExceptionContent);
    }

    /**
     * 检验具体结果项与正常值比较
     * @param lowValue 正常值低值
     * @param highValue 正常值高值
     * @param itemValue 检验具体值
     * @return 检验具体结果项与正常值比较结果 (0代表正常，-1代表低于范围值，1代表高于范围值，-2代表范围值都为空)
     */
    private int compare(Double lowValue, Double highValue, Double itemValue)
    {
        // 对比默认值正常
        int result = 0;

        // 当正常值高低值都存在时情况
        if (lowValue != null && highValue != null)
        {
            // 判断是否低于正常值低值
            if (itemValue < lowValue)
            {
                result = -1;
            }
            else
            {
                // 判断是否高于正常值高值
                if (itemValue > highValue)
                {
                    result = 1;
                }
            }
        }
        // 当正常值低值存在，高值不存在情况
        else if (lowValue != null && highValue == null)
        {
            // 判断是否低于正常值低值
            if (itemValue < lowValue)
            {
                result = -1;
            }
        }
        // 当正常值高值存在，低值不存在情况
        else if (lowValue == null && highValue != null)
        {
            // 判断是否高于正常值高值
            if (itemValue > highValue)
            {
                result = 1;
            }
        }
        // 上述情况不满足时为比较参考值都为空
        else
        {
            // $Author :jin_peng
            // $Date : 2012/08/23 14:10$
            // [BUG]0007863 MODIFY BEGIN
            result = -2;
            // [BUG]0007863 MODIFY END
        }

        return result;
    }

    // $Author :jin_peng
    // $Date : 2012/08/23 14:10$
    // [BUG]0007863 ADD BEGIN
    /**
     * 获取比较后得到的高低标识值
     * @param eFlag 比较后判断高低值依据
     * @return 高或低
     */
    private String getLowOrHighFlagValue(int eFlag)
    {
        String lowOrHigh = null;

        // 自定义判断等于-1为低值，1为高值，0为正常
        if (eFlag == -1)
        {
            lowOrHigh = DISPLAY_LOW_VALUE;
        }
        else if (eFlag == 1)
        {
            lowOrHigh = DISPLAY_HIGH_VALUE;
        }
        else if (eFlag == 0)
        {
            lowOrHigh = DISPLAY_NORMAL_VALUE;
        }

        return lowOrHigh;
    }

    // [BUG]0007863 ADD END

    public String getResultHighLowFlagStr()
    {
        return resultHighLowFlagStr;
    }

    public void setResultHighLowFlagStr(String resultHighLowFlagStr)
    {
        this.resultHighLowFlagStr = resultHighLowFlagStr;
    }

    public String getNormalRefValueText()
    {
        return normalRefValueText;
    }

    public void setNormalRefValueText(String normalRefValueText)
    {
        this.normalRefValueText = normalRefValueText;
    }

    public String getQualitativeResults()
    {
        return qualitativeResults;
    }

    public void setQualitativeResults(String qualitativeResults)
    {
        this.qualitativeResults = qualitativeResults;
    }

    public String getReportMemo()
    {
        return reportMemo;
    }

    public void setReportMemo(String reportMemo)
    {
        this.reportMemo = reportMemo;
    }

    public String getSourceSystemId()
    {
        return sourceSystemId;
    }

    public void setSourceSystemId(String sourceSystemId)
    {
        this.sourceSystemId = sourceSystemId;
    }

    public Date getVisitDate()
    {
        return visitDate;
    }

    public void setVisitDate(Date visitDate)
    {
        this.visitDate = visitDate;
    }

    public String getVisitTypeCode()
    {
        return visitTypeCode;
    }

    public void setVisitTypeCode(String visitTypeCode)
    {
        this.visitTypeCode = visitTypeCode;
    }

    public String getVisitDeptId()
    {
        return visitDeptId;
    }

    public void setVisitDeptId(String visitDeptId)
    {
        this.visitDeptId = visitDeptId;
    }

    public String getVisitDoctorId()
    {
        return visitDoctorId;
    }

    public void setVisitDoctorId(String visitDoctorId)
    {
        this.visitDoctorId = visitDoctorId;
    }

    public String getVisitDoctorName()
    {
        return visitDoctorName;
    }

    public void setVisitDoctorName(String visitDoctorName)
    {
        this.visitDoctorName = visitDoctorName;
    }

    public String getDiseaseCode()
    {
        return diseaseCode;
    }

    public void setDiseaseCode(String diseaseCode)
    {
        this.diseaseCode = diseaseCode;
    }

    public String getDiseaseName()
    {
        return diseaseName;
    }

    public void setDiseaseName(String diseaseName)
    {
        this.diseaseName = diseaseName;
    }

    public String getDrugCode()
    {
        return drugCode;
    }

    public void setDrugCode(String drugCode)
    {
        this.drugCode = drugCode;
    }

    public String getDrugName()
    {
        return drugName;
    }

    public void setDrugName(String drugName)
    {
        this.drugName = drugName;
    }

    public String getDosage()
    {
        return dosage;
    }

    public void setDosage(String dosage)
    {
        this.dosage = dosage;
    }

    public String getDosageUnit()
    {
        return dosageUnit;
    }

    public void setDosageUnit(String dosageUnit)
    {
        this.dosageUnit = dosageUnit;
    }

    public String getMedicineFrenquency()
    {
        return medicineFrenquency;
    }

    public void setMedicineFrenquency(String medicineFrenquency)
    {
        this.medicineFrenquency = medicineFrenquency;
    }

    public String getExaminationDoctorName()
    {
        return examinationDoctorName;
    }

    public void setExaminationDoctorName(String examinationDoctorName)
    {
        this.examinationDoctorName = examinationDoctorName;
    }

    public String getExaminationMethod()
    {
        return examinationMethod;
    }

    public void setExaminationMethod(String examinationMethod)
    {
        this.examinationMethod = examinationMethod;
    }

    public String getExaminationMethodName()
    {
        return examinationMethodName;
    }

    public void setExaminationMethodName(String examinationMethodName)
    {
        this.examinationMethodName = examinationMethodName;
    }

    public String getExaminationRegionName()
    {
        return examinationRegionName;
    }

    public void setExaminationRegionName(String examinationRegionName)
    {
        this.examinationRegionName = examinationRegionName;
    }

    public String getExecDeptName()
    {
        return execDeptName;
    }

    public void setExecDeptName(String execDeptName)
    {
        this.execDeptName = execDeptName;
    }

    public String getOrderStatusName()
    {
        return orderStatusName;
    }

    public void setOrderStatusName(String orderStatusName)
    {
        this.orderStatusName = orderStatusName;
    }

    public String getRequestDate()
    {
        return requestDate;
    }

    public void setRequestDate(String requestDate)
    {
        this.requestDate = requestDate;
    }

    public String getExaminationItem()
    {
        return examinationItem;
    }

    public void setExaminationItem(String examinationItem)
    {
        this.examinationItem = examinationItem;
    }

    public String getPatientSn()
    {
        return patientSn;
    }

    public void setPatientSn(String patientSn)
    {
        this.patientSn = patientSn;
    }

    public String getExaminationItemName()
    {
        return examinationItemName;
    }

    public void setExaminationItemName(String examinationItemName)
    {
        this.examinationItemName = examinationItemName;
    }

    public String getItemCode()
    {
        return itemCode;
    }

    public void setItemCode(String itemCode)
    {
        this.itemCode = itemCode;
    }

    public String getItemName()
    {
        return itemName;
    }

    public void setItemName(String itemName)
    {
        this.itemName = itemName;
    }

    public String getOperationName()
    {
        return operationName;
    }

    public void setOperationName(String operationName)
    {
        this.operationName = operationName;
    }

    public String getSurgicalDeptName()
    {
        return surgicalDeptName;
    }

    public void setSurgicalDeptName(String surgicalDeptName)
    {
        this.surgicalDeptName = surgicalDeptName;
    }

    public Date getOperationDate()
    {
        return operationDate;
    }

    public void setOperationDate(Date operationDate)
    {
        this.operationDate = operationDate;
    }

    public String getDocumentName()
    {
        return documentName;
    }

    public void setDocumentName(String documentName)
    {
        this.documentName = documentName;
    }

    public String getDocumentType()
    {
        return documentType;
    }

    public void setDocumentType(String documentType)
    {
        this.documentType = documentType;
    }

    public String getDocumentAuthorName()
    {
        return documentAuthorName;
    }

    public void setDocumentAuthorName(String documentAuthorName)
    {
        this.documentAuthorName = documentAuthorName;
    }

    public String getDocumentModifierName()
    {
        return documentModifierName;
    }

    public void setDocumentModifierName(String documentModifierName)
    {
        this.documentModifierName = documentModifierName;
    }

    public Date getWriteTime()
    {
        return writeTime;
    }

    public void setWriteTime(Date writeTime)
    {
        this.writeTime = writeTime;
    }

    public String getOrderFlag()
    {
        return orderFlag;
    }

    public void setOrderFlag(String orderFlag)
    {
        this.orderFlag = orderFlag;
    }

    public String getOrderName()
    {
        return orderName;
    }

    public void setOrderName(String orderName)
    {
        this.orderName = orderName;
    }

    public Date getOrderTime()
    {
        return orderTime;
    }

    public void setOrderTime(Date orderTime)
    {
        this.orderTime = orderTime;
    }

    public Date getDischargeDate()
    {
        return dischargeDate;
    }

    public void setDischargeDate(Date dischargeDate)
    {
        this.dischargeDate = dischargeDate;
    }

    public static String getTimerVisit()
    {
        return TIMER_VISIT;
    }

    public static String getDiagnosis()
    {
        return DIAGNOSIS;
    }

    public String getDIAGNOSIS()
    {
        return DIAGNOSIS;
    }

    public static String getDrugOrder()
    {
        return DRUG_ORDER;
    }

    public String getDRUG_ORDER()
    {
        return DRUG_ORDER;
    }

    public static String getExamination()
    {
        return EXAMINATION;
    }

    public String getEXAMINATION()
    {
        return EXAMINATION;
    }

    public static String getLab()
    {
        return LAB;
    }

    public String getLAB()
    {
        return LAB;
    }

    public static String getProcedure()
    {
        return PROCEDURE;
    }

    public String getPROCEDURE()
    {
        return PROCEDURE;
    }

    public static String getDocumentation()
    {
        return DOCUMENTATION;
    }

    public String getDOCUMENTATION()
    {
        return DOCUMENTATION;
    }

    public static String getNoDrugOrder()
    {
        return NO_DRUG_ORDER;
    }

    public String getNO_DRUG_ORDER()
    {
        return NO_DRUG_ORDER;
    }

    public BigDecimal getVisitSn()
    {
        return visitSn;
    }

    public void setVisitSn(BigDecimal visitSn)
    {
        this.visitSn = visitSn;
    }

    public BigDecimal getOrderSn()
    {
        return orderSn;
    }

    public void setOrderSn(BigDecimal orderSn)
    {
        this.orderSn = orderSn;
    }

    public BigDecimal getPrescriptionSn()
    {
        return prescriptionSn;
    }

    public void setPrescriptionSn(BigDecimal prescriptionSn)
    {
        this.prescriptionSn = prescriptionSn;
    }

    public BigDecimal getExamReportSn()
    {
        return examReportSn;
    }

    public void setExamReportSn(BigDecimal examReportSn)
    {
        this.examReportSn = examReportSn;
    }

    public BigDecimal getDocumentSn()
    {
        return documentSn;
    }

    public void setDocumentSn(BigDecimal documentSn)
    {
        this.documentSn = documentSn;
    }

    public String getDocumentLid()
    {
        return documentLid;
    }

    public void setDocumentLid(String documentLid)
    {
        this.documentLid = documentLid;
    }

    public String getOrderTitle()
    {
        return orderTitle;
    }

    public void setOrderTitle(String orderTitle)
    {
        this.orderTitle = orderTitle;
    }

    public String getOrderPath()
    {
        return orderPath;
    }

    public void setOrderPath(String orderPath)
    {
        this.orderPath = orderPath;
    }

    public String getOrderType()
    {
        return orderType;
    }

    public void setOrderType(String orderType)
    {
        this.orderType = orderType;
    }

    // $Author :tong_meng
    // $Date : 2013/1/11 13:49$
    // [BUG]0013311 MODIFY BEGIN
    public Date getExaminationDate()
    {
        return examinationDate;
    }

    public void setExaminationDate(Date examinationDate)
    {
        this.examinationDate = examinationDate;
    }
    // [BUG]0013311 MODIFY END

    public String getExistsFlag()
    {
        return existsFlag;
    }

    public void setExistsFlag(String existsFlag)
    {
        this.existsFlag = existsFlag;
    }

    public static String getNoExistsFlag()
    {
        return NO_EXISTS_FLAG;
    }

    public String getNO_EXISTS_FLAG()
    {
        return NO_EXISTS_FLAG;
    }

    public Date getInpatientDate()
    {
        return inpatientDate;
    }

    public void setInpatientDate(Date inpatientDate)
    {
        this.inpatientDate = inpatientDate;
    }

    public static String getInpatientVisit()
    {
        return INPATIENT_VISIT;
    }

    public String getItemResult()
    {
        return itemResult;
    }

    public void setItemResult(String itemResult)
    {
        this.itemResult = itemResult;
    }

    public String getEiImagingConclusion()
    {
        return eiImagingConclusion;
    }

    public void setEiImagingConclusion(String eiImagingConclusion)
    {
        this.eiImagingConclusion = eiImagingConclusion;
    }

    public String getEiImagingFinding()
    {
        return eiImagingFinding;
    }

    public void setEiImagingFinding(String eiImagingFinding)
    {
        this.eiImagingFinding = eiImagingFinding;
    }

    public String getErImagingConclusion()
    {
        return erImagingConclusion;
    }

    public void setErImagingConclusion(String erImagingConclusion)
    {
        this.erImagingConclusion = erImagingConclusion;
    }

    public String getErImagingFinding()
    {
        return erImagingFinding;
    }

    public void setErImagingFinding(String erImagingFinding)
    {
        this.erImagingFinding = erImagingFinding;
    }

    public String getItemResultUnit()
    {
        return itemResultUnit;
    }

    public void setItemResultUnit(String itemResultUnit)
    {
        this.itemResultUnit = itemResultUnit;
    }

    public static String getPhysical()
    {
        return PHYSICAL;
    }

    public String getXhData()
    {
        return xhData;
    }

    public void setXhData(String xhData)
    {
        this.xhData = xhData;
    }

    public String getInpatientDateDisplayColor()
    {
        return inpatientDateDisplayColor;
    }

    public void setInpatientDateDisplayColor(String inpatientDateDisplayColor)
    {
        this.inpatientDateDisplayColor = inpatientDateDisplayColor;
    }

    public static String getColorBlack()
    {
        return COLOR_BLACK;
    }

    public static String getColorBlue()
    {
        return COLOR_BLUE;
    }

    public int getExamTimeHour()
    {
        return examTimeHour;
    }

    public void setExamTimeHour(int examTimeHour)
    {
        this.examTimeHour = examTimeHour;
    }

    public BigDecimal getDiagnosisSn()
    {
        return diagnosisSn;
    }

    public void setDiagnosisSn(BigDecimal diagnosisSn)
    {
        this.diagnosisSn = diagnosisSn;
    }

    public static String getPagingPrevious()
    {
        return PAGING_PREVIOUS;
    }

    public static String getPagingNext()
    {
        return PAGING_NEXT;
    }

    public String getWithdrawFlag()
    {
        return withdrawFlag;
    }

    public void setWithdrawFlag(String withdrawFlag)
    {
        this.withdrawFlag = withdrawFlag;
    }

    public String getExaminationRegion()
    {
        return examinationRegion;
    }

    public void setExaminationRegion(String examinationRegion)
    {
        this.examinationRegion = examinationRegion;
    }

    public BigDecimal getItemSn()
    {
        return itemSn;
    }

    public void setItemSn(BigDecimal itemSn)
    {
        this.itemSn = itemSn;
    }

    public BigDecimal getCompositeItemSn()
    {
        return compositeItemSn;
    }

    public void setCompositeItemSn(BigDecimal compositeItemSn)
    {
        this.compositeItemSn = compositeItemSn;
    }

    public BigDecimal getProcedureSn()
    {
        return procedureSn;
    }

    public void setProcedureSn(BigDecimal procedureSn)
    {
        this.procedureSn = procedureSn;
    }

    public BigDecimal getLabReportSn()
    {
        return labReportSn;
    }

    public void setLabReportSn(BigDecimal labReportSn)
    {
        this.labReportSn = labReportSn;
    }

    public Date getSubmitTime()
    {
        return submitTime;
    }

    public void setSubmitTime(Date submitTime)
    {
        this.submitTime = submitTime;
    }

    public String getVisitTypeName()
    {
        return visitTypeName;
    }

    public void setVisitTypeName(String visitTypeName)
    {
        this.visitTypeName = visitTypeName;
    }

    public String getVisitDeptName()
    {
        return visitDeptName;
    }

    public void setVisitDeptName(String visitDeptName)
    {
        this.visitDeptName = visitDeptName;
    }

    public String getMainDiagnosisFlag()
    {
        return mainDiagnosisFlag;
    }

    public void setMainDiagnosisFlag(String mainDiagnosisFlag)
    {
        this.mainDiagnosisFlag = mainDiagnosisFlag;
    }

    public String getMAIN_DIAGNOSIS_FLAG()
    {
        return MAIN_DIAGNOSIS_FLAG;
    }

    public String getInpatientDays()
    {
        return inpatientDays;
    }

    public void setInpatientDays(String inpatientDays)
    {
        this.inpatientDays = inpatientDays;
    }

    public String getDocumentTypeName()
    {
        return documentTypeName;
    }

    public void setDocumentTypeName(String documentTypeName)
    {
        this.documentTypeName = documentTypeName;
    }

    public String getDiagnosisTypeName()
    {
        return diagnosisTypeName;
    }

    public void setDiagnosisTypeName(String diagnosisTypeName)
    {
        this.diagnosisTypeName = diagnosisTypeName;
    }

    public String getItemNameCn()
    {
        return itemNameCn;
    }

    public void setItemNameCn(String itemNameCn)
    {
        this.itemNameCn = itemNameCn;
    }

    public String getItemValue()
    {
        return itemValue;
    }

    public void setItemValue(String itemValue)
    {
        this.itemValue = itemValue;
    }

    public String getItemUnit()
    {
        return itemUnit;
    }

    public void setItemUnit(String itemUnit)
    {
        this.itemUnit = itemUnit;
    }

    public Double getLowValue()
    {
        return lowValue;
    }

    public void setLowValue(double lowValue)
    {
        this.lowValue = lowValue;
    }

    public Double getHighValue()
    {
        return highValue;
    }

    public void setHighValue(double highValue)
    {
        this.highValue = highValue;
    }

    public String getExceptionFlag()
    {
        return exceptionFlag;
    }

    public void setExceptionFlag(String exceptionFlag)
    {
        this.exceptionFlag = exceptionFlag;
    }

    public String getExceptionContent()
    {
        return exceptionContent;
    }

    public String getWardName()
    {
        return wardName;
    }

    public void setWardName(String wardName)
    {
        this.wardName = wardName;
    }

    public String getBedNo()
    {
        return bedNo;
    }

    public void setBedNo(String bedNo)
    {
        this.bedNo = bedNo;
    }

    public String getDischargeWardName()
    {
        return dischargeWardName;
    }

    public void setDischargeWardName(String dischargeWardName)
    {
        this.dischargeWardName = dischargeWardName;
    }

    public String getDischargeBedNo()
    {
        return dischargeBedNo;
    }

    public void setDischargeBedNo(String dischargeBedNo)
    {
        this.dischargeBedNo = dischargeBedNo;
    }

    public void setExceptionContent(String exceptionContent)
    {
        this.exceptionContent = exceptionContent;
    }

    public String getEXCEPTION_EXISTS_FLAG()
    {
        return EXCEPTION_EXISTS_FLAG;
    }

    public static String getExceptionExistsFlag()
    {
        return EXCEPTION_EXISTS_FLAG;
    }

    public void setLowValue(Double lowValue)
    {
        this.lowValue = lowValue;
    }

    public void setHighValue(Double highValue)
    {
        this.highValue = highValue;
    }

    public String getWarnFlag()
    {
        return warnFlag;
    }

    public void setWarnFlag(String warnFlag)
    {
        this.warnFlag = warnFlag;
    }

    public Double getWarnLowValue()
    {
        return warnLowValue;
    }

    public void setWarnLowValue(Double warnLowValue)
    {
        this.warnLowValue = warnLowValue;
    }

    public Double getWarnHighValue()
    {
        return warnHighValue;
    }

    public void setWarnHighValue(Double warnHighValue)
    {
        this.warnHighValue = warnHighValue;
    }

    public String getDrugNameCn()
    {
        return drugNameCn;
    }

    public void setDrugNameCn(String drugNameCn)
    {
        this.drugNameCn = drugNameCn;
    }

    public String getDrugNameEn()
    {
        return drugNameEn;
    }

    public void setDrugNameEn(String drugNameEn)
    {
        this.drugNameEn = drugNameEn;
    }

    public String getBreakpoint()
    {
        return breakpoint;
    }

    public void setBreakpoint(String breakpoint)
    {
        this.breakpoint = breakpoint;
    }

    public String getKb()
    {
        return kb;
    }

    public void setKb(String kb)
    {
        this.kb = kb;
    }

    public String getMic()
    {
        return mic;
    }

    public void setMic(String mic)
    {
        this.mic = mic;
    }

    public String getSensitivity()
    {
        return sensitivity;
    }

    public void setSensitivity(String sensitivity)
    {
        this.sensitivity = sensitivity;
    }

    // $Author:wei_peng
    // $Date:2012/10/9 16:18
    // [BUG]0010243 ADD BEGIN

    public String getTesterName()
    {
        return testerName;
    }

    public void setTesterName(String testerName)
    {
        this.testerName = testerName;
    }

    public Date getTestDate()
    {
        return testDate;
    }

    public void setTestDate(Date testDate)
    {
        this.testDate = testDate;
    }

    public String getLabDeptName()
    {
        return labDeptName;
    }

    public void setLabDeptName(String labDeptName)
    {
        this.labDeptName = labDeptName;
    }

    public BigDecimal getLabOrderSn()
    {
        return labOrderSn;
    }

    public void setLabOrderSn(BigDecimal labOrderSn)
    {
        this.labOrderSn = labOrderSn;
    }

    public BigDecimal getExamOrderSn()
    {
        return examOrderSn;
    }

    public void setExamOrderSn(BigDecimal examOrderSn)
    {
        this.examOrderSn = examOrderSn;
    }

    public String getExamOrLabTipContent()
    {
        return examOrLabTipContent;
    }

    public void setExamOrLabTipContent(String examOrLabTipContent)
    {
        this.examOrLabTipContent = examOrLabTipContent;
    }

    public String getItemClassName()
    {
        return itemClassName;
    }

    public void setItemClassName(String itemClassName)
    {
        this.itemClassName = itemClassName;
    }

    public String getItemClass()
    {
        return itemClass;
    }

    public void setItemClass(String itemClass)
    {
        this.itemClass = itemClass;
    }

    public String getReportDoctorName()
    {
        return reportDoctorName;
    }

    public void setReportDoctorName(String reportDoctorName)
    {
        this.reportDoctorName = reportDoctorName;
    }

    public String getExamDeptName()
    {
        return examDeptName;
    }

    public void setExamDeptName(String examDeptName)
    {
        this.examDeptName = examDeptName;
    }

    public String getImagingFinding()
    {
        return imagingFinding;
    }

    public void setImagingFinding(String imagingFinding)
    {
        this.imagingFinding = imagingFinding;
    }

    // [BUG]0010243 ADD END

    // $Author :jin_peng
    // $Date : 2012/09/27 10:48$
    // [BUG]0010132 ADD BEGIN
    public int getActuallyDisplayForwardCount()
    {
        return actuallyDisplayForwardCount;
    }

    public void setActuallyDisplayForwardCount(int actuallyDisplayForwardCount)
    {
        this.actuallyDisplayForwardCount = actuallyDisplayForwardCount;
    }

    public int getActuallyDisplayBackwardCount()
    {
        return actuallyDisplayBackwardCount;
    }

    public void setActuallyDisplayBackwardCount(int actuallyDisplayBackwardCount)
    {
        this.actuallyDisplayBackwardCount = actuallyDisplayBackwardCount;
    }

    public BigDecimal getVisitTimes()
    {
        return visitTimes;
    }

    public void setVisitTimes(BigDecimal visitTimes)
    {
        this.visitTimes = visitTimes;
    }

    public int getRowRn()
    {
        return rowRn;
    }

    public void setRowRn(int rowRn)
    {
        this.rowRn = rowRn;
    }

    public int getActuallyCount()
    {
        return actuallyCount;
    }

    public void setActuallyCount(int actuallyCount)
    {
        this.actuallyCount = actuallyCount;
    }

    // [BUG]0010132 ADD END

    // $Author :jin_peng
    // $Date : 2012/10/09 17:09$
    // [BUG]0010239 ADD BEGIN
    public Date getStopTime()
    {
        return stopTime;
    }

    public void setStopTime(Date stopTime)
    {
        this.stopTime = stopTime;
    }

    public Date getCancelTime()
    {
        return cancelTime;
    }

    public void setCancelTime(Date cancelTime)
    {
        this.cancelTime = cancelTime;
    }

    public String getMedicineFreqName()
    {
        return medicineFreqName;
    }

    public void setMedicineFreqName(String medicineFreqName)
    {
        this.medicineFreqName = medicineFreqName;
    }

    public String getOrderTypeName()
    {
        return orderTypeName;
    }

    public void setOrderTypeName(String orderTypeName)
    {
        this.orderTypeName = orderTypeName;
    }

    public String getSkinTestResult()
    {
        return skinTestResult;
    }

    public void setSkinTestResult(String skinTestResult)
    {
        this.skinTestResult = skinTestResult;
    }

    public String getApprovedDrugName()
    {
        return approvedDrugName;
    }

    public void setApprovedDrugName(String approvedDrugName)
    {
        this.approvedDrugName = approvedDrugName;
    }

    public String getMedicineTypeName()
    {
        return medicineTypeName;
    }

    public void setMedicineTypeName(String medicineTypeName)
    {
        this.medicineTypeName = medicineTypeName;
    }

    public String getMedicineForm()
    {
        return medicineForm;
    }

    public void setMedicineForm(String medicineForm)
    {
        this.medicineForm = medicineForm;
    }

    /**
     * $Author :yang_mingjie
     * $Date : 2014/06/09 10:09$
     * [BUG]0044564 MODIFY BEGIN 
     * */
    public String getSpecification()
    {
        return specification;
    }

    public void setSpecification(String specification)
    {
        this.specification = specification;
    } 
    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    } 
    /**
     * [BUG]0044564 MODIFY End
     * */
    
    public String getTotalDosage()
    {
        return totalDosage;
    }

    public void setTotalDosage(String totalDosage)
    {
        this.totalDosage = totalDosage;
    }

    public String getTotalDosageUnit()
    {
        return totalDosageUnit;
    }

    public void setTotalDosageUnit(String totalDosageUnit)
    {
        this.totalDosageUnit = totalDosageUnit;
    }

    public String getRouteCode()
    {
        return routeCode;
    }

    public void setRouteCode(String routeCode)
    {
        this.routeCode = routeCode;
    }

    public String getRouteName()
    {
        return routeName;
    }

    public void setRouteName(String routeName)
    {
        this.routeName = routeName;
    }

    public Date getOrderStartTime()
    {
        return orderStartTime;
    }

    public void setOrderStartTime(Date orderStartTime)
    {
        this.orderStartTime = orderStartTime;
    }

    public int getIsFirst()
    {
        return isFirst;
    }

    public void setIsFirst(int isFirst)
    {
        this.isFirst = isFirst;
    }

    public int getIS_FIRST()
    {
        return IS_FIRST;
    }

    public int getIS_FIRST_NOT()
    {
        return IS_FIRST_NOT;
    }

    public String getLineColor()
    {
        return lineColor;
    }

    public void setLineColor(String lineColor)
    {
        this.lineColor = lineColor;
    }

    @Override
    public Object clone() throws CloneNotSupportedException
    {
        return super.clone();
    }

    public int getLongTermEachCount()
    {
        return longTermEachCount;
    }

    public void setLongTermEachCount(int longTermEachCount)
    {
        this.longTermEachCount = longTermEachCount;
    }

    public String getVisitStatusCode()
    {
        return visitStatusCode;
    }

    public void setVisitStatusCode(String visitStatusCode)
    {
        this.visitStatusCode = visitStatusCode;
    }

    // [BUG]0010239 ADD END

    // $Author :jin_peng
    // $Date : 2012/12/25 16:27$
    // [BUG]0012698 ADD BEGIN
    public String getDiagnosticDeptName()
    {
        return diagnosticDeptName;
    }

    public void setDiagnosticDeptName(String diagnosticDeptName)
    {
        this.diagnosticDeptName = diagnosticDeptName;
    }

    public String getDiagnosisDoctorName()
    {
        return diagnosisDoctorName;
    }

    public void setDiagnosisDoctorName(String diagnosisDoctorName)
    {
        this.diagnosisDoctorName = diagnosisDoctorName;
    }

    public String getUncertainFlag()
    {
        return uncertainFlag;
    }

    public void setUncertainFlag(String uncertainFlag)
    {
        this.uncertainFlag = uncertainFlag;
    }
    // [BUG]0012698 ADD END

    public String getTestResults()
    {
        return testResults;
    }

    public void setTestResults(String testResults)
    {
        this.testResults = testResults;
    }

    public Date getEntranceTime()
    {
        return entranceTime;
    }

    public void setEntranceTime(Date entranceTime)
    {
        this.entranceTime = entranceTime;
    }
    
    @Override
    public String toString(){
    	return "\ninpatientDate=" + DateUtils.dateToString(inpatientDate, DateUtils.PATTERN_SLASH_DATETIME)
    			+ ", inpatientDays=" + inpatientDays;
    }
}
