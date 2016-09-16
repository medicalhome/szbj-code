package com.founder.cdr.core;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.founder.cdr.entity.AdministrationRecord;
import com.founder.cdr.entity.AllergicHistory;
import com.founder.cdr.entity.Anesthesia;
import com.founder.cdr.entity.BillingItem;
import com.founder.cdr.entity.BillingReceipt;
import com.founder.cdr.entity.BloodRequest;
import com.founder.cdr.entity.CareOrder;
import com.founder.cdr.entity.ClinicalDocument;
import com.founder.cdr.entity.ConsultationOrder;
import com.founder.cdr.entity.CustomizeNotification;
import com.founder.cdr.entity.Diagnosis;
import com.founder.cdr.entity.ExamAppointment;
import com.founder.cdr.entity.ExaminationOrder;
import com.founder.cdr.entity.ExaminationResult;
import com.founder.cdr.entity.GeneralOrder;
import com.founder.cdr.entity.LabOrder;
import com.founder.cdr.entity.LabResult;
import com.founder.cdr.entity.MedicalVisit;
import com.founder.cdr.entity.MedicationOrder;
import com.founder.cdr.entity.MedicationVerdict;
import com.founder.cdr.entity.NursingOperation;
import com.founder.cdr.entity.PatientDoctor;
import com.founder.cdr.entity.PatientDoctorIncharge;
import com.founder.cdr.entity.PatientFav;
import com.founder.cdr.entity.Prescription;
import com.founder.cdr.entity.ProcedureOrder;
import com.founder.cdr.entity.RegistrationFee;
import com.founder.cdr.entity.RiskInformation;
import com.founder.cdr.entity.Settlement;
import com.founder.cdr.entity.SurgicalProcedure;
import com.founder.cdr.entity.TransferHistory;
import com.founder.cdr.entity.TreatmentOrder;

/**
 * CDR常量类。
 * 注意get方法格式必须如下：
 * public 返回类型 get常量字符串() {
 *     return 常量字符串;
 * }
 * @author wen_ruichao
 * @version 1.0
 */
public class Constants
{
    // CDR Portal begin
    /**
     * 下拉框中请选择的value值
     * @return
     */
    public static final String OPTION_SELECT = AppSettings.getConfig("OPTION_SELECT");
    /**
     * 链接场景
     */
    public static final String LINK_SCENE_TYPE = AppSettings.getConfig("LINK_SCENE_TYPE");
    
    /**
     * 视图Id
     */
    public static final String LINK_VIEW_ID = AppSettings.getConfig("LINK_VIEW_ID");
    
    /**
     * 系统Id
     */
    public static final String SYSTEM_ID = AppSettings.getConfig("SYSTEM_ID");
    /**
     * 链接视图类型
     */
    public static final String LINK_VIEW_TYPE = AppSettings.getConfig("LINK_VIEW_TYPE");
    /**
     * 打印权限ID
     */
    public static final String PRINT_AUTH_ID = AppSettings.getConfig("PRINT_AUTH_ID");
    
    /**
     *	document_type文档类别
     */
    public static final String DOCUMENT_TYPE = AppSettings.getConfig("DOCUMENT_TYPE");
    
    /**
     * 文档类别名称document_type_name
     */
    public static final String DOCUMENT_TYPE_NAME = AppSettings.getConfig("DOCUMENT_TYPE_NAME");
    
    /**
     * 检查检验病历文书，pdf，jpg文件保存路径
     */
    public static final String IMAGE_CONTENT_URL = AppSettings.getConfig("IMAGE_CONTENT_URL");

    /**
     * 检查检验病历文书，pdf，jpg文件保存路径
     */
    public static final boolean HISTORY_CDA_PROCESS_FLAG = Boolean.valueOf(AppSettings.getConfig("HISTORY_CDA_PROCESS_FLAG"));
    
    /**
     * 分页时每页显示的最大行数的配置项 
     */
    public static final String CFG_ROWS_PER_PAGE = "rows_per_page";

    /**
     * 请求影像中心的url地址
     */
    public static final String IMAGE_CENTER_URL = "image_center_url";
    
    /**
     * 电子签章的url地址
     */
    public static final String SIGNATURE_URL = "signature_url";

    /**
     * 集成视图患者列表显示的患者记录数 
     */
    public static final String CFG_PATIENTLIST_ROWS_COUNT = "patientlist_rows_count";

    /**
     * 集成视图就诊列表显示的就诊记录数 
     */
    public static final String CFG_VISITLIST_ROWS_COUNT = "visitlist_rows_count";

    /**
     * 集成视图诊断列表显示的诊断记录数 
     */
    public static final String CFG_DIAGNOSISLIST_ROWS_COUNT = "diagnosislist_rows_count";

    /**
     * 集成视图用药列表显示的用药记录数 
     */
    public static final String CFG_DRUGLIST_ROWS_COUNT = "druglist_rows_count";

    /**
     * 集成视图检查列表显示的检查记录数 
     */
    public static final String CFG_EXAMLIST_ROWS_COUNT = "examlist_rows_count";

    /**
     * 集成视图检验列表显示的检验记录数 
     */
    public static final String CFG_LABLIST_ROWS_COUNT = "lablist_rows_count";

    /**
     * 时间轴视图或住院视图列表头显示的就诊记录数 
     */
    public static final String CFG_TIMER_INPATIENT_ROWS_COUNT = "timer_inpatient_rows_count";

    /**
     * 页面分页页码显示部分的显示个数 
     */
    public static final String CFG_PAGING_DISPLAY_COUNT = "every_page_count";

    /**
     *  页面分页页码显示部分点击页面增量
     */
    public static final String CFG_PAGING_ADD_COUNT = "every_page_add_count";

    // 体格检查大便次数代码
    public static final String PHYSICAL_DEFECATE_TIMES = AppSettings.getConfig("PHYSICAL_DEFECATE_TIMES");

    // 体格检查输液量
    public static final String PHYSICAL_INPUT_QUANTITY = AppSettings.getConfig("PHYSICAL_INPUT_QUANTITY");

    // 体格检查尿量
    public static final String PHYSICAL_PEE_QUANTITY = AppSettings.getConfig("PHYSICAL_PEE_QUANTITY");

    // 体格检查体重
    public static final String PHYSICAL_WEIGHT = AppSettings.getConfig("PHYSICAL_WEIGHT");

    // 体格检查体温
    public static final String PHYSICAL_TEMPERATURE = AppSettings.getConfig("PHYSICAL_TEMPERATURE");

    // 体格检查血压
    public static final String PHYSICAL_BLOOD_PRESSURE = AppSettings.getConfig("PHYSICAL_BLOOD_PRESSURE");

    // 体格检查脉搏
    public static final String PHYSICAL_PULSE = AppSettings.getConfig("PHYSICAL_PULSE");

    // 体格检查呼吸
    public static final String PHYSICAL_BREATHE = AppSettings.getConfig("PHYSICAL_BREATHE");

    // $Author:wei_peng
    // $Date:2012/9/29 10:16
    // $[BUG]0010038 ADD BEGIN
    // 疼痛评估指标(静息)
    public static final String PHYSICAL_PAIN_ASSESSMENT = AppSettings.getConfig("PHYSICAL_PAIN_ASSESSMENT");

    // $[BUG]0010038 ADD END

    // $Author:jin_peng
    // $Date:2012/12/28 17:01
    // $[BUG]0012992 ADD BEGIN
    // 疼痛评估指标(活动)
    public static final String PHYSICAL_PAIN_ASSESSMENT_ACTIVE = AppSettings.getConfig("PHYSICAL_PAIN_ASSESSMENT_ACTIVE");

    // $[BUG]0012992 ADD END

    // 时间轴页面和住院视图页面每td中显示内容条数
    public static final int TIMER_INPATIENT_DISPLAY_TD_COUNT = Integer.parseInt(AppSettings.getConfig("TIMER_INPATIENT_DISPLAY_TD_COUNT"));

    // 检查检验报告召回记录标识
    public static final String REPORT_WITHDRAW_FLAG = AppSettings.getConfig("REPORT_WITHDRAW_FLAG");

    // 检查检验报告修改记录标识
    public static final String REPORT_MODIFY_FLAG = AppSettings.getConfig("REPORT_MODIFY_FLAG");

    // $Author:wu_jianfeng
    // $Date : 2012/09/12 14:10
    // $[BUG]0009663 MODIFY BEGIN
    // 访问控制实现
    // 是否使用访问控制
    public static final String ACL_USE = AppSettings.getConfig("ACL_USE");

    public static final String ACL_USE_OPEN = AppSettings.getConfig("ACL_USE_OPEN");

    public static final String ACL_USE_CLOSE = AppSettings.getConfig("ACL_USE_CLOSE");

    // 访问控制-患者范围权限
    // 可以访问我的患者信息
    public static final String ACL_PATIENT_SCOPE_01 = AppSettings.getConfig("ACL_PATIENT_SCOPE_01");

    // 可以访问本科室门诊患者信息
    public static final String ACL_PATIENT_SCOPE_02 = AppSettings.getConfig("ACL_PATIENT_SCOPE_02");

    // 可以访问本科室住院患者信息
    public static final String ACL_PATIENT_SCOPE_03 = AppSettings.getConfig("ACL_PATIENT_SCOPE_03");

    // 可以访问本病区患者信息
    public static final String ACL_PATIENT_SCOPE_04 = AppSettings.getConfig("ACL_PATIENT_SCOPE_04");

    // 可以访问全院患者信息
    public static final String ACL_PATIENT_SCOPE_05 = AppSettings.getConfig("ACL_PATIENT_SCOPE_05");

    // 可以访问出院患者信息
    public static final String ACL_PATIENT_SCOPE_06 = AppSettings.getConfig("ACL_PATIENT_SCOPE_06");

    // 可以访问VIP患者信息
    public static final String ACL_PATIENT_SCOPE_07 = AppSettings.getConfig("ACL_PATIENT_SCOPE_07");

    // 访问控制-患者信息权限
    // 姓名
    public static final String ACL_PATIENT_INFO_01 = AppSettings.getConfig("ACL_PATIENT_INFO_01");

    // 出生日期
    public static final String ACL_PATIENT_INFO_02 = AppSettings.getConfig("ACL_PATIENT_INFO_02");

    // 证件信息
    public static final String ACL_PATIENT_INFO_03 = AppSettings.getConfig("ACL_PATIENT_INFO_03");

    // 家庭住址
    public static final String ACL_PATIENT_INFO_04 = AppSettings.getConfig("ACL_PATIENT_INFO_04");

    // 电话号码
    public static final String ACL_PATIENT_INFO_05 = AppSettings.getConfig("ACL_PATIENT_INFO_05");

    // email
    public static final String ACL_PATIENT_INFO_06 = AppSettings.getConfig("ACL_PATIENT_INFO_06");

    // 访问控制-临床信息权限
    // 诊断信息
    public static final String ACL_CLINICAL_INFO_01 = AppSettings.getConfig("ACL_CLINICAL_INFO_01");

    // 药物医嘱
    public static final String ACL_CLINICAL_INFO_02 = AppSettings.getConfig("ACL_CLINICAL_INFO_02");

    // 非药物医嘱
    public static final String ACL_CLINICAL_INFO_03 = AppSettings.getConfig("ACL_CLINICAL_INFO_03");

    // 手术信息
    public static final String ACL_CLINICAL_INFO_04 = AppSettings.getConfig("ACL_CLINICAL_INFO_04");

    // 检验信息
    public static final String ACL_CLINICAL_INFO_05 = AppSettings.getConfig("ACL_CLINICAL_INFO_05");

    // 检查信息
    public static final String ACL_CLINICAL_INFO_06 = AppSettings.getConfig("ACL_CLINICAL_INFO_06");

    // 病历
    public static final String ACL_CLINICAL_INFO_07 = AppSettings.getConfig("ACL_CLINICAL_INFO_07");

    // 设置访问控制，界面上显示的信息
    public static final String ACL_NOACCESS_MESSAGE = AppSettings.getConfig("ACL_NOACCESS_MESSAGE");

    // 职业类别 医生
    public static final String ACL_OCCUPATION_TYPE_DOCTOR = AppSettings.getConfig("ACL_OCCUPATION_TYPE_DOCTOR");

    // 发送警告信息中的职业类别 医生
    public static final String WARN_OCCUPATION_TYPE_DOCTOR = AppSettings.getConfig("WARN_OCCUPATION_TYPE_DOCTOR");

    // 发送警告信息中的职业类别 护士
    public static final String WARN_OCCUPATION_TYPE_NURSE = AppSettings.getConfig("WARN_OCCUPATION_TYPE_NURSE");

    // $[BUG]0009663 MODIFY END

    // HLA分型报告的项目编码
    public static final String MORPHOLOGY_ITEM_CODE_HLV = AppSettings.getConfig("MORPHOLOGY_ITEM_CODE_HLV");

    // 血清学产前筛查报告的项目编码
    public static final String MORPHOLOGY_ITEM_CODE_SEROLOGY = AppSettings.getConfig("MORPHOLOGY_ITEM_CODE_SEROLOGY");

    // $Author : wu_jianfeng
    // $Date : 2012/09/28 17:49$
    // [BUG]0010107 ADD BEGIN
    // 就诊索引视图修改
    // 就诊索引视图列数
    public static final String VISIT_INDEX_VIEW_COLS = AppSettings.getConfig("VISIT_INDEX_VIEW_COLS");

    // 就诊索引视图行数
    public static final String VISIT_INDEX_VIEW_ROWS = AppSettings.getConfig("VISIT_INDEX_VIEW_ROWS");

    // [BUG]0010107 ADD END

    // 年龄段
    public static final String AGE_GROUP = AppSettings.getConfig("AGE_GROUP");

    // 意见反馈栏-超级用户
    public static final String FEEDBACK_ADMINS = AppSettings.getConfig("FEEDBACK_ADMINS");

    // $Author : wu_jianfeng
    // $Date : 2012/12/20 15:03$
    // [BUG]0012967 ADD BEGIN
    // 视图标识
    public static final String VIEW_SETTING_VISITINDEXVIEW = AppSettings.getConfig("VIEW_SETTING_VISITINDEXVIEW");

    public static final String VIEW_SETTING_OUTPATIENTVIEW = AppSettings.getConfig("VIEW_SETTING_OUTPATIENTVIEW");

    public static final String VIEW_SETTING_INPATIENTVIEW = AppSettings.getConfig("VIEW_SETTING_INPATIENTVIEW");

    public static final String VIEW_SETTING_TIMERVIEW = AppSettings.getConfig("VIEW_SETTING_TIMERVIEW");

    public static final String VIEW_SETTING_NORMALVIEW = AppSettings.getConfig("VIEW_SETTING_NORMALVIEW");

    // 01就诊，02诊断，03药物，04长期药物，05临时药物，06检查，07检验，08手术，09病历，10其他
    // 门诊视图业务标识()
    public static final String VIEW_SETTING_OVIEW_VISIT = AppSettings.getConfig("VIEW_SETTING_OVIEW_VISIT");

    public static final String VIEW_SETTING_OVIEW_DIAGNOSIS = AppSettings.getConfig("VIEW_SETTING_OVIEW_DIAGNOSIS");

    public static final String VIEW_SETTING_OVIEW_DRUG = AppSettings.getConfig("VIEW_SETTING_OVIEW_DRUG");

    public static final String VIEW_SETTING_OVIEW_LONGDRUG = AppSettings.getConfig("VIEW_SETTING_OVIEW_LONGDRUG");

    public static final String VIEW_SETTING_OVIEW_SHORTDRUG = AppSettings.getConfig("VIEW_SETTING_OVIEW_SHORTDRUG");

    public static final String VIEW_SETTING_OVIEW_EXAM = AppSettings.getConfig("VIEW_SETTING_OVIEW_EXAM");

    public static final String VIEW_SETTING_OVIEW_LAB = AppSettings.getConfig("VIEW_SETTING_OVIEW_LAB");

    public static final String VIEW_SETTING_OVIEW_PROCEDURE = AppSettings.getConfig("VIEW_SETTING_OVIEW_PROCEDURE");

    public static final String VIEW_SETTING_OVIEW_DOC = AppSettings.getConfig("VIEW_SETTING_OVIEW_DOC");

    public static final String VIEW_SETTING_OVIEW_OTHER = AppSettings.getConfig("VIEW_SETTING_OVIEW_OTHER");

    // 住院视图业务标识
    public static final String VIEW_SETTING_IVIEW_VISIT = AppSettings.getConfig("VIEW_SETTING_IVIEW_VISIT");

    public static final String VIEW_SETTING_IVIEW_DIAGNOSIS = AppSettings.getConfig("VIEW_SETTING_IVIEW_DIAGNOSIS");

    public static final String VIEW_SETTING_IVIEW_DRUG = AppSettings.getConfig("VIEW_SETTING_IVIEW_DRUG");

    public static final String VIEW_SETTING_IVIEW_LONGDRUG = AppSettings.getConfig("VIEW_SETTING_IVIEW_LONGDRUG");

    public static final String VIEW_SETTING_IVIEW_SHORTDRUG = AppSettings.getConfig("VIEW_SETTING_IVIEW_SHORTDRUG");

    public static final String VIEW_SETTING_IVIEW_EXAM = AppSettings.getConfig("VIEW_SETTING_IVIEW_EXAM");

    public static final String VIEW_SETTING_IVIEW_LAB = AppSettings.getConfig("VIEW_SETTING_IVIEW_LAB");

    public static final String VIEW_SETTING_IVIEW_PROCEDURE = AppSettings.getConfig("VIEW_SETTING_IVIEW_PROCEDURE");

    public static final String VIEW_SETTING_IVIEW_DOC = AppSettings.getConfig("VIEW_SETTING_IVIEW_DOC");

    public static final String VIEW_SETTING_IVIEW_OTHER = AppSettings.getConfig("VIEW_SETTING_IVIEW_OTHER");

    public static final String VIEW_SETTING_IVIEW_TC = AppSettings.getConfig("VIEW_SETTING_IVIEW_TC");

    // 时间轴视图业务标识
    public static final String VIEW_SETTING_TVIEW_VISIT = AppSettings.getConfig("VIEW_SETTING_TVIEW_VISIT");

    public static final String VIEW_SETTING_TVIEW_DIAGNOSIS = AppSettings.getConfig("VIEW_SETTING_TVIEW_DIAGNOSIS");

    public static final String VIEW_SETTING_TVIEW_DRUG = AppSettings.getConfig("VIEW_SETTING_TVIEW_DRUG");

    public static final String VIEW_SETTING_TVIEW_LONGDRUG = AppSettings.getConfig("VIEW_SETTING_TVIEW_LONGDRUG");

    public static final String VIEW_SETTING_TVIEW_SHORTDRUG = AppSettings.getConfig("VIEW_SETTING_TVIEW_SHORTDRUG");

    public static final String VIEW_SETTING_TVIEW_EXAM = AppSettings.getConfig("VIEW_SETTING_TVIEW_EXAM");

    public static final String VIEW_SETTING_TVIEW_LAB = AppSettings.getConfig("VIEW_SETTING_TVIEW_LAB");

    public static final String VIEW_SETTING_TVIEW_PROCEDURE = AppSettings.getConfig("VIEW_SETTING_TVIEW_PROCEDURE");

    public static final String VIEW_SETTING_TVIEW_DOC = AppSettings.getConfig("VIEW_SETTING_TVIEW_DOC");

    public static final String VIEW_SETTING_TVIEW_OTHER = AppSettings.getConfig("VIEW_SETTING_TVIEW_OTHER");

    // 综合视图业务标识
    public static final String VIEW_SETTING_NVIEW_VISIT = AppSettings.getConfig("VIEW_SETTING_NVIEW_VISIT");

    public static final String VIEW_SETTING_NVIEW_DIAGNOSIS = AppSettings.getConfig("VIEW_SETTING_NVIEW_DIAGNOSIS");

    public static final String VIEW_SETTING_NVIEW_DRUG = AppSettings.getConfig("VIEW_SETTING_NVIEW_DRUG");

    public static final String VIEW_SETTING_NVIEW_LONGDRUG = AppSettings.getConfig("VIEW_SETTING_NVIEW_LONGDRUG");

    public static final String VIEW_SETTING_NVIEW_SHORTDRUG = AppSettings.getConfig("VIEW_SETTING_NVIEW_SHORTDRUG");

    public static final String VIEW_SETTING_NVIEW_EXAM = AppSettings.getConfig("VIEW_SETTING_NVIEW_EXAM");

    public static final String VIEW_SETTING_NVIEW_LAB = AppSettings.getConfig("VIEW_SETTING_NVIEW_LAB");

    public static final String VIEW_SETTING_NVIEW_PROCEDURE = AppSettings.getConfig("VIEW_SETTING_NVIEW_PROCEDURE");

    public static final String VIEW_SETTING_NVIEW_DOC = AppSettings.getConfig("VIEW_SETTING_NVIEW_DOC");

    public static final String VIEW_SETTING_NVIEW_OTHER = AppSettings.getConfig("VIEW_SETTING_NVIEW_OTHER");
    /**
     *医嘱执行分类编码--用于区分画面显示检查/检验
    */
    /**
     * 超声检查
    */
    public static final String ORDER_EXEC_ULTRASONIC = AppSettings.getConfig("ORDER_EXEC_ULTRASONIC");
    /**
     * 核医学检查
    */
    public static final String ORDER_EXEC_NUCLEAR = AppSettings.getConfig("ORDER_EXEC_NUCLEAR");
    /**
     * 放射检查
    */
    public static final String ORDER_EXEC_ERADIATION = AppSettings.getConfig("ORDER_EXEC_ERADIATION");
    /**
     * 病理检查
    */
    public static final String ORDER_EXEC_PATHOLOGY = AppSettings.getConfig("ORDER_EXEC_PATHOLOGY");
    /**
     * 内窥镜检查
    */
    public static final String ORDER_EXEC_ENDOSCOPE = AppSettings.getConfig("ORDER_EXEC_ENDOSCOPE");
    /**
     * 心电检查
    */
    public static final String ORDER_EXEC_ECG = AppSettings.getConfig("ORDER_EXEC_ECG");
    /**
     * 膀胱镜系统
    */
    public static final String ORDER_EXEC_CYSTOSCOPE = AppSettings.getConfig("ORDER_EXEC_CYSTOSCOPE");
	/**
     * 膀胱镜系统
    */
    public static final String ORDER_EXEC_HEARTBEAT = AppSettings.getConfig("ORDER_EXEC_HEARTBEAT");
    /**
     * 常规检验
    */
    public static final String ORDER_EXEC_LIS = AppSettings.getConfig("ORDER_EXEC_LIS");
    /**
     * 血液形态学检验
    */
    public static final String ORDER_EXEC_MORPHOLOGY = AppSettings.getConfig("ORDER_EXEC_MORPHOLOGY");
    // [BUG]0012967 ADD END

    // 门诊标识-显示患者信息的判断条件
    public static final String OUTPATIENT_CONDITION_TAG = "01";

    // 住院标识-显示患者信息的判断条件
    public static final String INPATIENT_CONDITION_TAG = "02";

    // CDR Portal end
    public static final String MQ_OPEN = AppSettings.getConfig("mq.open");
    
    public String getMQ_OPEN(){
    	return MQ_OPEN;
    }
    // CDR Batch begin

    // CDR护理医嘱表对应表名
    public static final String CARE_ORDER_TABLE_NAME = AppSettings.getConfig("CARE_ORDER_TABLE_NAME");

    // CDR用药医嘱表对应表名
    public static final String MEDICATION_ORDER_TABLE_NAME = AppSettings.getConfig("MEDICATION_ORDER_TABLE_NAME");

    // CDR诊疗处置医嘱表对应表名
    public static final String TREATMENT_ORDER_TABLE_NAME = AppSettings.getConfig("TREATMENT_ORDER_TABLE_NAME");

    // CDR会诊医嘱表对应表名
    public static final String CONSULTATION_ORDER_TABLE_NAME = AppSettings.getConfig("CONSULTATION_ORDER_TABLE_NAME");

    // CDR手术医嘱表对应表名
    public static final String PROCEDURE_ORDER_TABLE_NAME = AppSettings.getConfig("PROCEDURE_ORDER_TABLE_NAME");

    // CDR检查医嘱表对应表名
    public static final String EXAMINATION_ORDER_TABLE_NAME = AppSettings.getConfig("EXAMINATION_ORDER_TABLE_NAME");

    // CDR检验医嘱表对应表名
    public static final String LAB_ORDER_TABLE_NAME = AppSettings.getConfig("LAB_ORDER_TABLE_NAME");

    // CDR其他医嘱表对应表名
    public static final String GENERAL_ORDER_TABLE_NAME = AppSettings.getConfig("GENERAL_ORDER_TABLE_NAME");

    // CDR门诊用血申请对应表名
    public static final String BLOOD_REQUEST_OUTPATIENT_TABLE_NAME = AppSettings.getConfig("BLOOD_REQUEST_OUTPATIENT_TABLE_NAME");

    // 设置医嘱类型对应的CDR医嘱表
    public static final Map<String, String> ORDER_TYPE_MAP;

    // 证件类型map
    public static final Map<String, String> CREDENTIAL_TYPE_MAP;

    // 患者基本信息服务中需更新的相关业务数据实体class
    public static final List<Class<?>> UPDATE_RELEVANCE_CLASS_LIST;

    // 患者基本信息服务中需更新的相关业务数据表名
    public static final List<String> UPDATE_RELEVANCE_TBALE_NAME_LIST;

    // 患者基本信息服务中需更新的相关业务数据表名 根据visit
    public static final List<String> UPDATE_RELEVANCE_TBALE_NAME_FOR_VISIT_LIST;

    // 患者基本信息服务中需更新的相关业务数据表名 根据patient
    public static final List<String> UPDATE_RELEVANCE_TBALE_NAME_FOR_PATIENT_LIST;

    // CDR-Batch消息状态：未运行
    public static final String STATE_UNRUN = AppSettings.getConfig("STATE_UNRUN");

    // CDR-Batch消息状态： 正在运行
    public static final String STATE_RUNNING = AppSettings.getConfig("STATE_RUNNING");

    // CDR-Batch消息错误标识0：结构错误
    public static final String FLAG_DATA_STRUCTURE = AppSettings.getConfig("FLAG_DATA_STRUCTURE");

    // CDR-Batch消息错误标识1：数据错误
    public static final String FLAG_DATA_INTEGRITY = AppSettings.getConfig("FLAG_DATA_INTEGRITY");

    // CDA新增版本号
    public static final String VERSION_NUMBER_INSERT = AppSettings.getConfig("VERSION_NUMBER_INSERT");

    // CDA修改版本号
    public static final String VERSION_NUMBER_UPDATE = AppSettings.getConfig("VERSION_NUMBER_UPDATE");
    
    // CDA替换版本号
    public static final String VERSION_NUMBER_REPLACE = AppSettings.getConfig("VERSION_NUMBER_REPLACE");

    // $Author :jin_peng
    // $Date : 2013/09/18 15:53$
    // [BUG]0037432 ADD BEGIN
    // CDA修改版本号
    public static final String VERSION_NUMBER_PART_APPROVED = AppSettings.getConfig("VERSION_NUMBER_PART_APPROVED");

    // [BUG]0037432 ADD END

    // CDA作废版本号
    public static final String VERSION_NUMBER_WITHDRAW = AppSettings.getConfig("VERSION_NUMBER_WITHDRAW");

    // CDA未召回状态
    public static final BigDecimal NO_WITH_DRAW_FLAG = BigDecimal.ZERO;

    // CDA召回状态
    public static final BigDecimal WITH_DRAW_FLAG = BigDecimal.ONE;

    // 删除状态
    public static final String DELETE_FLAG = AppSettings.getConfig("DELETE_FLAG");

    // 未删除状态
    public static final String NO_DELETE_FLAG = AppSettings.getConfig("NO_DELETE_FLAG");

    // 费用-婴儿标志-是
    public static final String INFANT_FLAG = AppSettings.getConfig("INFANT_FLAG");

    // 费用-婴儿标志-否
    public static final String NO_INFANT_FLAG = AppSettings.getConfig("NO_INFANT_FLAG");

    // 新增时更新回数
    public static final BigDecimal INSERT_UPDATE_COUNT = BigDecimal.ONE;

    // 新消息标识
    public static final String NEW_MESSAGE_FLAG = AppSettings.getConfig("NEW_MESSAGE_FLAG");

    // 退费重收消息标志（处方消息）
    public static final String RENEW_MESSAGE_FLAG = AppSettings.getConfig("RENEW_MESSAGE_FLAG");

    // 更新消息标识
    public static final String UPDATE_MESSAGE_FLAG = AppSettings.getConfig("UPDATE_MESSAGE_FLAG");

    // 自定义消息新增标识
    public static final String INSERT_MESSAGE_FLAG = AppSettings.getConfig("INSERT_MESSAGE_FLAG");

    // 自定义消息删除标识
    public static final String DELETE_MESSAGE_FLAG = AppSettings.getConfig("DELETE_MESSAGE_FLAG");

    // 撤销消息标识
    public static final String CANCEL_MESSAGE_FLAG = AppSettings.getConfig("CANCEL_MESSAGE_FLAG");

    // 停止消息标识
    public static final String STOP_MESSAGE_FLAG = AppSettings.getConfig("STOP_MESSAGE_FLAG");
    
    // 出院登记消息标识
    public static final String REGISTER_MESSAGE_FLAG = AppSettings.getConfig("REGISTER_MESSAGE_FLAG");

    // 撤销出院登记消息标识
    public static final String CANCEL_REGI_MESSAGE_FLAG = AppSettings.getConfig("CANCEL_REGI_MESSAGE_FLAG");
    
    //V2消息开立标识
    public static final String V2_NEW_MESSAGE_FLAG = AppSettings.getConfig("V2_NEW_MESSAGE_FLAG");
    
    //V2消息更新标识
    public static final String V2_UPDATE_MESSAGE_FLAG = AppSettings.getConfig("V2_UPDATE_MESSAGE_FLAG");
    
    //V2消息更新标识
    public static final String V2_CANCEL_MESSAGE_FLAG = AppSettings.getConfig("V2_CANCEL_MESSAGE_FLAG");
    
    //V2消息开单医生撤销标识
    public static final String V2_DOC_CANCEL_MESSAGE_FLAG = AppSettings.getConfig("V2_DOC_CANCEL_MESSAGE_FLAG");
    
    //V2消息确认标识
    public static final String V2_CONFIRM_OR_CHARGE_MESSAGE_FLAG = AppSettings.getConfig("V2_CONFIRM_OR_CHARGE_MESSAGE_FLAG");
    
    //V2消息执行标识
    public static final String V2_EXEC_MESSAGE_FLAG = AppSettings.getConfig("V2_EXEC_MESSAGE_FLAG");
    
    //V2消息执行取消标识
    public static final String V2_EXEC_CANCEL_MESSAGE_FLAG = AppSettings.getConfig("V2_EXEC_CANCEL_MESSAGE_FLAG");
   
    //V2消息撤销退费标识
    public static final String V2_RETURN_MESSAGE_FLAG = AppSettings.getConfig("V2_RETURN_MESSAGE_FLAG");
    
    //V2字典新增标识
    public static final String V2_DIC_NEW_FLAG = AppSettings.getConfig("V2_DIC_NEW_FLAG");
    
    //V2字典删除标识
    public static final String V2_DIC_DELETE_FLAG = AppSettings.getConfig("V2_DIC_DELETE_FLAG");
    
    //V2字典更新标识
    public static final String V2_DIC_UPDATE_FLAG = AppSettings.getConfig("V2_DIC_UPDATE_FLAG");
    
    //V2字典失效标识
    public static final String V2_DIC_DISABLE_FLAG = AppSettings.getConfig("V2_DIC_DISABLE_FLAG");
    
    //V2字典恢复有效标识
    public static final String V2_DIC_ENABLE_FLAG = AppSettings.getConfig("V2_DIC_ENABLE_FLAG");
    
    // 新消息为入留观的标识
    public static final String IN_OBSERVE_FLAG = AppSettings.getConfig("IN_OBSERVE_FLAG");

    // 新消息为出留观的标识
    public static final String OUT_OBSERVE_FLAG = AppSettings.getConfig("OUT_OBSERVE_FLAG");

    // 主诊断标识
    public static final String MAIN_DIAGNOSIS = AppSettings.getConfig("MAIN_DIAGNOSIS");

    // 次诊断标识
    public static final String MINOR_DIAGNOSIS = AppSettings.getConfig("MINOR_DIAGNOSIS");

    // 是否待查-是（对应消息）
    public static final String CERTAIN = AppSettings.getConfig("CERTAIN");

    // 是否待查-是（对应数据库）
    public static final String CERTAIN_DB = AppSettings.getConfig("CERTAIN_DB");

    // 是否待查-否（对应消息）
    public static final String UN_CERTAIN = AppSettings.getConfig("UN_CERTAIN");

    // 是否待查-否（对应数据库）
    public static final String UN_CERTAIN_DB = AppSettings.getConfig("UN_CERTAIN_DB");

    // Author :jia_yanqing
    // Date : 2012/12/26 16:00
    // [BUG]0012797 ADD BEGIN
    // 与付数无关
    public static final String NUM_UNCONCERNED_FLAG = AppSettings.getConfig("NUM_UNCONCERNED_FLAG");

    // [BUG]0012797 ADD END

    // 是否加急-是
    public static final String URGENT_FLAG = AppSettings.getConfig("URGENT_FLAG");

    // 是否加急-否
    public static final String NO_URGENT_FLAG = AppSettings.getConfig("NO_URGENT_FLAG");

    // 是否药观-是
    public static final String MED_VIEW_FLAG = AppSettings.getConfig("MED_VIEW_FLAG");

    // 是否药观-否
    public static final String NO_MED_VIEW_FLAG = AppSettings.getConfig("NO_MED_VIEW_FLAG");

    // 血压-高压
    public static final String HIGH_PRESSURE = AppSettings.getConfig("HIGH_PRESSURE");

    // 血压-低压
    public static final String LOW_PRESSURE = AppSettings.getConfig("LOW_PRESSURE");

    // 整张报告图片
    public static final String IMAGE_CONTENT = AppSettings.getConfig("IMAGE_CONTENT");

    // 报告影像图片
    public static final String MEDICAL_IMAGE = AppSettings.getConfig("MEDICAL_IMAGE");

    // 就诊状态 正常
    public static final String VISIT_STATUS_NORMAL = AppSettings.getConfig("VISIT_STATUS_NORMAL");

    // 就诊状态 已挂号
    public static final String VISIT_STATUS_REGISTER = AppSettings.getConfig("VISIT_STATUS_REGISTER");
    
    // Author :jia_yanqing
    // Date : 2012/6/14 15:00
    // [BUG]0033791 ADD BEGIN
    // 就诊状态 看完病
    public static final String VISIT_STATUS_SEEOUT = AppSettings.getConfig("VISIT_STATUS_SEEOUT");

    // [BUG]0033791 ADD END

    // 就诊状态 退号
    public static final String VISIT_STATUS_REFUND = AppSettings.getConfig("VISIT_STATUS_REFUND");

    // 就诊状态 已退院
    public static final String VISIT_STATUS_OUT_HOSPITAL = AppSettings.getConfig("VISIT_STATUS_OUT_HOSPITAL");

    // 就诊状态 出院
    public static final String VISIT_STATUS_DISCHARGE_HOSPITAL = AppSettings.getConfig("VISIT_STATUS_DISCHARGE_HOSPITAL");

    // 就诊状态 出院已登记
    public static final String VISIT_STATUS_DISCHARGE_REGISTERED = AppSettings.getConfig("VISIT_STATUS_DISCHARGE_REGISTERED");

    // 就诊状态 在院
    public static final String VISIT_STATUS_IN_HOSPITAL = AppSettings.getConfig("VISIT_STATUS_IN_HOSPITAL");

    // 就诊类别（住院）
    public static final String VISIT_TYPE_CODE_INPATIENT = AppSettings.getConfig("VISIT_TYPE_CODE_INPATIENT");
    
    // 就诊类别（转院）
    public static final String VISIT_TYPE_CODE_INPATIENT_TR = AppSettings.getConfig("VISIT_TYPE_CODE_INPATIENT_TR");

    // 就诊类别（门诊）
    public static final String VISIT_TYPE_CODE_OUTPATIENT = AppSettings.getConfig("VISIT_TYPE_CODE_OUTPATIENT");

    // 就诊类别（急诊）
    public static final String VISIT_TYPE_CODE_EMERGENCY = AppSettings.getConfig("VISIT_TYPE_CODE_EMERGENCY");
    
    // 就诊类别（急诊留观）
    public static final String VISIT_TYPE_CODE_EMERGENCY_ST = AppSettings.getConfig("VISIT_TYPE_CODE_EMERGENCY_ST");

    // 就诊类别（体检）
    public static final String VISIT_TYPE_CODE_PHYSICALEXAM = AppSettings.getConfig("VISIT_TYPE_CODE_PHYSICALEXAM");
    
    // 就诊类别（干休体检）
    public static final String VISIT_TYPE_CODE_PHYSICALEXAM_BOSS = AppSettings.getConfig("VISIT_TYPE_CODE_PHYSICALEXAM_BOSS");

    // 处方类型编码（草药）
    public static final String PRESCRIPTION_TYPE_CODE_HERBAL = AppSettings.getConfig("PRESCRIPTION_TYPE_CODE_HERBAL");

    // 处方类型编码（诊疗）
    public static final String PRESCRIPTION_TYPE_CODE_TREATMENT = AppSettings.getConfig("PRESCRIPTION_TYPE_CODE_TREATMENT");

    // 就诊状态 召回
    public static final String VISIT_STATUS_WITHDRAW = AppSettings.getConfig("VISIT_STATUS_WITHDRAW");

    // 临时医嘱标识
    public static final String TEMPORARY_FLAG = AppSettings.getConfig("TEMPORARY_FLAG");

    // 临时医嘱
    public static final String TEMPORARY_FLAG_TEXT = AppSettings.getConfig("TEMPORARY_FLAG_TEXT");

    // 长期医嘱标识
    public static final String LONG_TERM_FLAG = AppSettings.getConfig("LONG_TERM_FLAG");

    // 医疗机构显示与否开关
    public static final String DISABLE_ORG_CODE = AppSettings.getConfig("DISABLE_ORG_CODE");

    //V2消息SOCKET_SERVER端口参数传递使用
    public static final String SOCKET_SERVER_PORT_KEY = AppSettings.getConfig("SOCKET_SERVER_PORT_KEY");
    
    // 医疗机构显示常量
    public static final String TRUE_ORG_CODE = AppSettings.getConfig("TRUE_ORG_CODE");

    // 医嘱状态--新嘱
    public static final String ORDER_TYPE_NEW = AppSettings.getConfig("ORDER_TYPE_NEW");
    
    /**
     * 医嘱状态-已确认
     * */
    public static final String ORDER_STATUS_CONFIRMED = AppSettings.getConfig("ORDER_STATUS_CONFIRMED");
    
    /**
     * 医嘱状态-检查已完成
     * */
    public static final String ORDER_STATUS_EXAM_FINISH = AppSettings.getConfig("ORDER_STATUS_EXAM_FINISH");
    
    /**
     * 医嘱状态-检验已完成
     * */
    public static final String ORDER_STATUS_LAB_FINISH = AppSettings.getConfig("ORDER_STATUS_LAB_FINISH");
    
    /**
     * 医嘱状态-用药已开始
     * */
    public static final String ORDER_STATUS_MEDICATION_START = AppSettings.getConfig("ORDER_STATUS_MEDICATION_START");
    
    /**
     * 医嘱状态-护理已开始
     * */
    public static final String ORDER_STATUS_CARE_START = AppSettings.getConfig("ORDER_STATUS_CARE_START");
    
    /**
     * 医嘱状态-治疗已接诊
     * */
    public static final String ORDER_STATUS_TREATMENT_START = AppSettings.getConfig("ORDER_STATUS_TREATMENT_START");
    
    /**
     * 医嘱状态-护理已完成
     * */
    public static final String ORDER_STATUS_CARE_FINISH = AppSettings.getConfig("ORDER_STATUS_CARE_FINISH");
    
    /**
     * 医嘱状态-治疗已完成
     * */
    public static final String ORDER_STATUS_TREATMENT_FINISH = AppSettings.getConfig("ORDER_STATUS_TREATMENT_FINISH");
    
    /**
     * 医嘱状态-手术已执行
     * */
    public static final String ORDER_STATUS_PROCEDURE_FINISH = AppSettings.getConfig("ORDER_STATUS_PROCEDURE_FINISH");
    
    /**
     * 医嘱状态-已配药
     * */
    public static final String ORDER_STATUS_DISPENSE = AppSettings.getConfig("ORDER_STATUS_DISPENSE");
    
    /**
     * 医嘱状态-已退药
     * */
    public static final String ORDER_STATUS_RETURN_DRUG = AppSettings.getConfig("ORDER_STATUS_RETURN_DRUG");
    
    /**
     * 医嘱状态-写报告
     * */
    public static final String ORDER_STATUS_WRITING_REPORT = AppSettings.getConfig("ORDER_STATUS_WRITING_REPORT");
    
    // TODO 待确定
    // 医嘱状态为撤销时的编码
    public static final String ORDER_STATUS_CANCEL = AppSettings.getConfig("ORDER_STATUS_CANCEL");

    // TODO 待确定
    // 医嘱状态为停止时的编码
    public static final String ORDER_STATUS_STOP = AppSettings.getConfig("ORDER_STATUS_STOP");

    // 护理已停止
    public static final String CARE_ORDER_STATUS_STOP = AppSettings.getConfig("CARE_ORDER_STATUS_STOP");

    // 药品已停止
    public static final String MEDICATION_ORDER_STATUS_STOP = AppSettings.getConfig("MEDICATION_ORDER_STATUS_STOP");

    // 消息中报告/影像 类型(检验报告/影像类型)
    public static final BigDecimal REPORT_TYPE_LAB = BigDecimal.ONE;

    // 消息中报告/影像 类型(检查报告/影像类型)
    public static final BigDecimal REPORT_TYPE_EXAM = BigDecimal.valueOf(2);

    // $Author :jin_peng
    // $Date : 2012/10/19 16:21$
    // [BUG]0010594 ADD BEGIN
    // 消息中报告/影像 类型(检查报告/影像类型)
    public static final BigDecimal REPORT_TYPE_PROCEDURE = BigDecimal.valueOf(3);

    // [BUG]0010594 ADD END

    // TODO 需要跟his确认后确定
    // 转科转床消息医嘱类型 暂定床位类
    public static final String ORDER_TYPE_TRANSFER = AppSettings.getConfig("ORDER_TYPE_TRANSFER");

    public static final String TRANSFER_HISTORY_TRANS_ANSWER = AppSettings.getConfig("TRANSFER_HISTORY_TRANS_ANSWER");

    // TODO 医嘱状态 录入状态 0
    public final static String ORDER_STATUS_INPUT = AppSettings.getConfig("ORDER_STATUS_INPUT");

    // 医嘱下达:10
    public final static String ORDER_STATUS_ISSUE = AppSettings.getConfig("ORDER_STATUS_ISSUE");

    // 域代码：01:HIS门诊
    public static final String PATIENT_DOMAIN_OUTPATIENT = AppSettings.getConfig("PATIENT_DOMAIN_OUTPATIENT");

    // 域代码：02:HIS住院
    public static final String PATIENT_DOMAIN_INPATIENT = AppSettings.getConfig("PATIENT_DOMAIN_INPATIENT");

    // 域代码：03:影像
//    public static final String PATIENT_DOMAIN_IMAGE = AppSettings.getConfig("PATIENT_DOMAIN_IMAGE");
//
//    // 域代码：04:体检
//    public static final String PATIENT_DOMAIN_PHYSICAL_EXAM = AppSettings.getConfig("PATIENT_DOMAIN_PHYSICAL_EXAM");

    // 退号消息标识
    public final static String WITHDRAW_REGISTER_MESSAGE_FLAG = AppSettings.getConfig("WITHDRAW_REGISTER_MESSAGE_FLAG");

    // 用药医嘱医嘱类型 TODO 待确定
    public static final String ORDER_TYPE_MEDICATION = AppSettings.getConfig("ORDER_TYPE_MEDICATION");

    /** 
     * 医嘱状态
     */
    // public static final String DOMAIN_ORDER_STATUS = "MS073";
    public static final String DOMAIN_ORDER_STATUS = AppSettings.getConfig("DOMAIN_ORDER_STATUS");

    // 用户
    public static final String USER = AppSettings.getConfig("USER");

    // 角色
    public static final String ROLE = AppSettings.getConfig("ROLE");

    // 接口服务ID-电子病历
    public static final String OUTPATIENT_SERVICE_ID = AppSettings.getConfig("OUTPATIENT_SERVICE_ID");

    // 接口服务ID-麻醉记录
    public static final String ANESTHESIA_SERVICE_ID = AppSettings.getConfig("ANESTHESIA_SERVICE_ID");

    // 术语中门急诊信息的来源
    public static final String OUTPATIENT = AppSettings.getConfig("OUTPATIENT");

    // 术语中电子病历信息的来源
    public static final String EMR = AppSettings.getConfig("EMR");

    // 是否怀孕编码
    public static final String PREGNANCY_CODE = AppSettings.getConfig("PREGNANCY_CODE");

    // 是否生产编码
    public static final String CHILDBIRTH_CODE = AppSettings.getConfig("CHILDBIRTH_CODE");

    // 是否有输血史编码
    public static final String HISTORY_CODE = AppSettings.getConfig("HISTORY_CODE");

    // 是否有不良输血史编码
    public static final String ADVERSE_HISTORY_CODE = AppSettings.getConfig("ADVERSE_HISTORY_CODE");

    // 是否新生儿编码
    public static final String NEW_BORN_CODE = AppSettings.getConfig("NEW_BORN_CODE");

    // 是否其他系统疾病
    public static final String OTHER_DISEASE_CODE = AppSettings.getConfig("OTHER_DISEASE_CODE");

    // 是否自采血编码
    public static final String SELF_BLOOD_CODE = AppSettings.getConfig("SELF_BLOOD_CODE");
    
    //是否交叉配血
    public static final String CROSS_MATCH_BLOOD_CODE = AppSettings.getConfig("CROSS_MATCH_BLOOD_CODE");
    
    //是否备用
    public static final String RESERVE_CODE = AppSettings.getConfig("RESERVE_CODE");
    
    //是否干细胞移植后循环
    public static final String STEM_CELL_TRANSPLANT_CODE = AppSettings.getConfig("STEM_CELL_TRANSPLANT_CODE");

    // 中草药类型代码 
    public static final String HERBAL_MEDICINE_CLASS = AppSettings.getConfig("HERBAL_MEDICINE_CLASS");

    // 医嘱状态为召回时的编码  待确认
    public static final String ORDER_WITHDRAW_STATUS = AppSettings.getConfig("ORDER_WITHDRAW_STATUS");

    // $Author :jia_yanqing
    // $Date : 2013/02/20 15:00$
    // [BUG]0014009 ADD BEGIN
    // $Author :jin_peng
    // $Date : 2013/02/19 11:20$
    // [BUG]0013981 DELETE BEGIN
    // 药品类别为中草药标识
    public static final String MEDICAL_CLASS_HERBAL = AppSettings.getConfig("MEDICAL_CLASS_HERBAL");

    // [BUG]0013981 DELETE END
    // [BUG]0014009 ADD END

    /**
     * 港大中草药标识
     * */ 
    public static final String GD_MEDICAL_CLASS_HERBAL = AppSettings.getConfig("GD_MEDICAL_CLASS_HERBAL");
    /**
     * 港大中成药标识
     * */ 
    public static final String GD_MEDICAL_CLASS_PATENT = AppSettings.getConfig("GD_MEDICAL_CLASS_PATENT");
    /**
     * 港大西药标识
     * */ 
    public static final String GD_MEDICAL_CLASS_WESTERN = AppSettings.getConfig("GD_MEDICAL_CLASS_WESTERN");
    
    // 入科信息标识
    public static final String TRANSFER_IN_FLAG = AppSettings.getConfig("TRANSFER_IN_FLAG");

    // 出科信息标识
    public static final String TRANSFER_OUT_FLAG = AppSettings.getConfig("TRANSFER_OUT_FLAG");

    // 转区转床表记录标识-入留观
    public static final String TRANSFER_HISTORY_IN_OBSERVE = AppSettings.getConfig("TRANSFER_HISTORY_IN_OBSERVE");

    // 转区转床表记录标识-出留观
    public static final String TRANSFER_HISTORY_OUT_OBSERVE = AppSettings.getConfig("TRANSFER_HISTORY_OUT_OBSERVE");

    // 转区转床表记录标识-入科
    public static final String TRANSFER_HISTORY_TRANS_IN = AppSettings.getConfig("TRANSFER_HISTORY_TRANS_IN");

    // 转区转床表记录标识-出科
    public static final String TRANSFER_HISTORY_TRANS_OUT = AppSettings.getConfig("TRANSFER_HISTORY_TRANS_OUT");

    // 转区转床表记录标识-转床
    public static final String TRANSFER_HISTORY_TRANS_BED = AppSettings.getConfig("TRANSFER_HISTORY_TRANS_BED");

    // 费用消息交易类型-退费重收标志
    public static final String TRANSACTION_TYPE_REPAY = AppSettings.getConfig("TRANSACTION_TYPE_REPAY");

    // 费用消息交易类型-退费
    public static final String TRANSACTION_TYPE_RETURN = AppSettings.getConfig("TRANSACTION_TYPE_RETURN");

    // 费用消息交易类型-正常收费
    public static final String TRANSACTION_TYPE_CHARGE = AppSettings.getConfig("TRANSACTION_TYPE_CHARGE");

    // 医嘱交费状态编码-未收费
    public static final String CHARGE_STATUS_NOTCHARGE = AppSettings.getConfig("CHARGE_STATUS_NOTCHARGE");

    // 医嘱交费状态编码-已收费
    public static final String CHARGE_STATUS_CHARGED = AppSettings.getConfig("CHARGE_STATUS_CHARGED");

    // 医嘱交费状态编码-退费
    public static final String CHARGE_STATUS_RETURNCHARGE = AppSettings.getConfig("CHARGE_STATUS_RETURNCHARGE");

    // 医嘱交费状态编码-退费重收
    public static final String CHARGE_STATUS_REPAYCHARGE = AppSettings.getConfig("CHARGE_STATUS_REPAYCHARGE");

    // 医嘱交费状态
    public static final String ORDER_CHARGE_STATUS = AppSettings.getConfig("ORDER_CHARGE_STATUS");

    // 检查报告类型编码-病理学
    public static final String EXAM_ITEMCLASS_PATHOLOGY = AppSettings.getConfig("EXAM_ITEMCLASS_PATHOLOGY");

    // 检查报告类型编码-超声心动
    public static final String EXAM_ITEMCLASS_ECHOCARDIOGRAPHY = AppSettings.getConfig("EXAM_ITEMCLASS_ECHOCARDIOGRAPHY");

    // 检查报告类型编码-冠脉造影
    public static final String EXAM_ITEMCLASS_CORONARY_ANGIOGRAPHY = AppSettings.getConfig("EXAM_ITEMCLASS_CORONARY_ANGIOGRAPHY");

    // 检查报告类型编码-冠脉造影及介入手术报告
    public static final String EXAM_ITEMCLASS_CORONARY_ANGIOGRAPHY_OPRERATER = AppSettings.getConfig("EXAM_ITEMCLASS_CORONARY_ANGIOGRAPHY_OPRERATER");

    //检查报告类型编码-心电图检查报告信息服务
    public static final String EXAM_ITEMCLASS_ELECTROCARDIOGRAPHY = AppSettings.getConfig("EXAM_ITEMCLASS_ELECTROCARDIOGRAPHY");
    
    //检查报告类型编码-胃镜检查报告信息服务
    public static final String EXAM_ITEMCLASS_GASTROSCOPE = AppSettings.getConfig("EXAM_ITEMCLASS_GASTROSCOPE");
    
    // 医嘱界面多个tab页的最多条数
    public static final String DETAIL_TABS_COUNT = AppSettings.getConfig("DETAIL_TABS_COUNT");

    // CDR Batch end

    // CDR 公共常量 begin

    /** 
     * 性别
     */
    public static final String DOMAIN_GENDER = AppSettings.getConfig("DOMAIN_GENDER");

    /** 
     * 婚姻
     */
    public static final String DOMAIN_MARRIAGE = AppSettings.getConfig("DOMAIN_MARRIAGE");

    /** 
     * 民族
     */
    public static final String DOMAIN_NATION = AppSettings.getConfig("DOMAIN_NATION");

    /** 
     * 国籍
     */
    public static final String DOMAIN_NATIONALITY = AppSettings.getConfig("DOMAIN_NATIONALITY");

    /** 
     * 职业
     */
    public static final String DOMAIN_OCCUPATION = AppSettings.getConfig("DOMAIN_OCCUPATION");

    /** 
     * 文化程度
     */
    public static final String DOMAIN_EDUCATION_DEGREE = AppSettings.getConfig("DOMAIN_EDUCATION_DEGREE");

    /** 
     * ABO血型
     */
    public static final String DOMAIN_BLOOD_ABO = AppSettings.getConfig("DOMAIN_BLOOD_ABO");

    /** 
     * RH血型
     */
    public static final String DOMAIN_BLOOD_RH = AppSettings.getConfig("DOMAIN_BLOOD_RH");

    /** 
     * 过敏症状
     */
    // public static final String DOMAIN_ALLERGIC_SYMPTOM = "012";

    /** 
     * 过敏原
     */
    // public static final String DOMAIN_ALLERGEN = "013";

    /** 
     * 药物
     */
    // public static final String DOMAIN_DRUG = "014";

    /**
     * 药品字典  有待确认
     */
    public static final String DOMAIN_DRUG_DICTIONARY = AppSettings.getConfig("DOMAIN_DRUG_DICTIONARY");

    /** 
     * 过敏病情状态
     */
    // public static final String DOMAIN_ALLERGIC_CONDITION = "015";

    /** 
     * 过敏严重性
     */
    public static final String DOMAIN_SERIOUSNESS = AppSettings.getConfig("DOMAIN_SERIOUSNESS");

    /** 
     * 个体危险性
     */
    // public static final String DOMAIN_INDIVIDUAL_RISK = "017";

    /** 
     * 与患者关系
     */
    public static final String DOMAIN_RELATIONSHIP = AppSettings.getConfig("DOMAIN_RELATIONSHIP");

    /** 
     * 证件类型
     */
    public static final String DOMAIN_CREDENTIAL_TYPE = AppSettings.getConfig("DOMAIN_CREDENTIAL_TYPE");

    /** 
     * 域
     */
    public static final String DOMAIN_SYSTEM_DOMAIN = AppSettings.getConfig("DOMAIN_SYSTEM_DOMAIN");

    /** 
     * 就诊类别
     */
    public static final String DOMAIN_VISIT_TYPE = AppSettings.getConfig("DOMAIN_VISIT_TYPE");

    /** 
     * 医疗机构编码
     */
    public static final String DOMAIN_ORG_CODE = AppSettings.getConfig("DOMAIN_ORG_CODE");

    /** 
     * 科室
     */
    public static final String DOMAIN_DEPARTMENT = AppSettings.getConfig("DOMAIN_DEPARTMENT");

    /** 
     * 人员
     */
    public static final String DOMAIN_STAFF = AppSettings.getConfig("DOMAIN_STAFF");

    /** 
     * 医疗保险-类别
     */
    // public static final String DOMAIN_INSURANCE_TYPE = "020";

    /** 
     * 就诊号别
     */
    public static final String DOMAIN_REGISTRATION_CLASS = AppSettings.getConfig("DOMAIN_REGISTRATION_CLASS");

    /** 
     * 发送时段星期一
     */
    public static final String SEND_TIME_MONDAY = AppSettings.getConfig("SEND_TIME_MONDAY");

    /** 
     * 发送时段星期二
     */
    public static final String SEND_TIME_TUESDAY = AppSettings.getConfig("SEND_TIME_TUESDAY");

    /** 
     * 发送时段星期三
     */
    public static final String SEND_TIME_WEDNESDAY = AppSettings.getConfig("SEND_TIME_WEDNESDAY");

    /** 
     * 发送时段星期四
     */
    public static final String SEND_TIME_THURSDAY = AppSettings.getConfig("SEND_TIME_THURSDAY");

    /** 
     * 发送时段星期五
     */
    public static final String SEND_TIME_FRIDAY = AppSettings.getConfig("SEND_TIME_FRIDAY");

    /**
     * 发送时段星期六
     */
    public static final String SEND_TIME_SATURDAY = AppSettings.getConfig("SEND_TIME_SATURDAY");

    /** 
     * 发送时段星期日
     */
    public static final String SEND_TIME_SUNDAY = AppSettings.getConfig("SEND_TIME_SUNDAY");

    /** 
     * 就诊号类
     */
    // public static final String DOMAIN_REGISTRATION_METHOD = "022";

    /** 
     * 上下午
     */
    // public static final String DOMAIN_AM_PM = "023";

    /** 
     * 就诊状态
     */
    public static final String DOMAIN_VISIT_STATUS = AppSettings.getConfig("DOMAIN_VISIT_STATUS");

    /** 
     * 挂号方式
     */
    // public static final String DOMAIN_REGISTERED_WAY = "025";

    /** 
     * 出院状态
     */
    // public static final String DOMAIN_DISCHARGE_STATUS = "026";

    /** 
     * 医嘱类型
     */
    // public static final String DOMAIN_ORDER_TYPE = "MS072";
    public static String DOMAIN_ORDER_TYPE = AppSettings.getConfig("DOMAIN_ORDER_TYPE");

    /** 
     * 执行频率
     */
    // public static final String DOMAIN_EXECUTION_FREQUENCY = "028";

    /** 
     * 疾病诊断类别代码
     */
    public static final String DOMAIN_DIAGNOSIS_TYPE = AppSettings.getConfig("DOMAIN_DIAGNOSIS_TYPE");
    
    /**
     * 诊断类型
     * */
    public static final String DOMAIN_DIAGNOSIS_TYPE_CODE = AppSettings.getConfig("DOMAIN_DIAGNOSIS_TYPE_CODE");
    
    /** 
     * 诊断类别/出院诊断
     */
    public static final String DOMAIN_DIAGNOSIS_TYPE_OUTPATIENT = AppSettings.getConfig("DOMAIN_DIAGNOSIS_TYPE_OUTPATIENT");

    /**
     * 国际疾病分类ICD-门急诊
     */
    public static final String DOMAIN_ICD_OUTPATIENT = AppSettings.getConfig("DOMAIN_ICD_OUTPATIENT");

    /** 
     * 诊断依据
     */
    // public static final String DOMAIN_DIAGNOISE_BASIS = "031";

    /** 
     * 治疗处置-项目类别
     */
    // public static final String DOMAIN_TREATMENT_TYPE = "032";

    /** 
     * 病区
     */
    public static final String DOMAIN_WARD = AppSettings.getConfig("DOMAIN_WARD");

    /** 
     * 处方类别
     */
    // public static final String DOMAIN_PRESCRIPTION_CLASS = "034";

    /** 
     * 处方类型
     */
    //public static final String DOMAIN_PRESCRIPTION_TYPE = AppSettings.getConfig("DOMAIN_PRESCRIPTION_TYPE");

    /** 
     * 药品类别
     */
    public static final String DOMAIN_MEDICINE_KIND = AppSettings.getConfig("DOMAIN_MEDICINE_KIND");

    /**
     * 药物类别-西药
     */
    public static final String MEDICAL_CLASS_WESTERN = AppSettings.getConfig("MEDICAL_CLASS_WESTERN");

    /**
     * 药物类别-中成药
     */
    public static final String MEDICAL_CLASS_PATENT = AppSettings.getConfig("MEDICAL_CLASS_PATENT");

    /**
     * 药物类别-草药
     */
    public static final String MEDICAL_CLASS_HERB = AppSettings.getConfig("MEDICAL_CLASS_HERB");

    /**
     * 药物类别-卫生材料
     */
    public static final String MEDICAL_CLASS_HEALTH = AppSettings.getConfig("MEDICAL_CLASS_HEALTH");

    /**
     * 常用频率 
     */
    public static final String DOMAIN_MEDICINE_FREQ = AppSettings.getConfig("DOMAIN_MEDICINE_FREQ");

    /** 
     * 药物类型
     */
    public static final String DOMAIN_MEDICINE_TYPE = AppSettings.getConfig("DOMAIN_MEDICINE_TYPE");

    /** 
     * 用药途径
     */
    public static final String DOMAIN_SUPPLY_ROUTE = AppSettings.getConfig("DOMAIN_SUPPLY_ROUTE");

    /** 
     * 药物剂型
     */
    public static final String DOMAIN_MEDICINE_FORM = AppSettings.getConfig("DOMAIN_MEDICINE_FORM");

    /** 
     * 互斥医嘱类型
     */
    // public static final String DOMAIN_MUTEXES_ORDER_TYPE = "040";

    /** 
     * 检查类别
     */
    // public static final String DOMAIN_EXAM_CLASS = "MS060";

    /**
     * 检查部位
     */
    public static final String DOMAIN_EXAM_REGION = AppSettings.getConfig("DOMAIN_EXAM_REGION");

    /** 
     * 检查方法
     */
    // public static final String DOMAIN_EXAM_METHOD = "043";

    /** 
     * 检验类别
     */
//    public static final String DOMAIN_LAB_CLASS = AppSettings.getConfig("DOMAIN_LAB_CLASS");

    /** 
     * 标本类型
     */
    public static final String DOMAIN_SAMPLE_TYPE = AppSettings.getConfig("DOMAIN_SAMPLE_TYPE");

    /** 
     * 检验项
     */
    public static final String DOMAIN_LAB_ITEM = AppSettings.getConfig("DOMAIN_LAB_ITEM");

    /** 
     * 检查项目
     */
    public static final String DOMAIN_EXAM_ITEM = AppSettings.getConfig("DOMAIN_EXAM_ITEM");
    
    // $Author :jin_peng
    // $Date : 2013/03/27 10:55$
    // [BUG]0014747 ADD BEGIN
    public static final String DOMAIN_LAB_RESULT_ITEM = AppSettings.getConfig("DOMAIN_LAB_RESULT_ITEM");

    // [BUG]0014747 ADD END

    /** 
     * 手术与操作
     */
    public static final String DOMAIN_PROCEDURE = AppSettings.getConfig("DOMAIN_PROCEDURE");

    /** 
     * 手术等级
     */
    public static final String DOMAIN_PROCEDURE_LEVEL = AppSettings.getConfig("DOMAIN_PROCEDURE_LEVEL");

    /** 
     * 麻醉方式
     */
    public static final String DOMAIN_ANESTHESIACODE = AppSettings.getConfig("DOMAIN_ANESTHESIACODE");
    
    /** 
     * 手术参与者身份
     */
    // public static final String DOMAIN_PARTICIPANT_IDENTITY = "049";

    /** 
     * 手术部位
     */
    // public static final String DOMAIN_PROCEDURE_SITE = "050";

    /** 
     * 操作方法(手术)
     */
    // public static final String DOMAIN_OPERATION_METHOD = "051";

    /** 
     * 操作类型(手术)
     */
    // public static final String DOMAIN_OPERATION_TYPE = "052";

    /** 
     * 体格检查项目名称
     */
    public static final String DOMAIN_PHYSICAN_EXAM_ITEM = AppSettings.getConfig("DOMAIN_PHYSICAN_EXAM_ITEM");

    /** 
     * 护理操作项目类目名称
     */
    // public static final String DOMAIN_CARE_OPERATION_TYPE = "054";

    /** 
     * 护理等级
     */
    // public static final String DOMAIN_CARE_LEVEL = "055";

    /** 
     * 护理类型
     */
    // public static final String DOMAIN_CARE_TYPE = "056";

    /**
     * 付款方式
     */
    // public static final String DOMAIN_PAYMENT_METHOD = "057";


    /** 
     * 费用类别
     */
    // public static final String DOMAIN_CHARGE_TYPE = "059";

    /** 
     * 文档类别
     */
    public static final String DOMAIN_CLINICAL_DOCUMENT_TYPE = AppSettings.getConfig("DOMAIN_CLINICAL_DOCUMENT_TYPE");

    /** 
     * 诊断机构级别
     */
    // public static final String DOMAIN_FACILITIES_LEVEL = "061";

    /** 
     * 疾病当前状态
     */
    // public static final String DOMAIN_DISEASE_STATUS = "062";

    /**
     * 症状代码
     */
    // public static final String DOMAIN_DISEASE_SYMPTOM = "063";

    /** 
     * 症状急性程度
     */
    // public static final String DOMAIN_ACUTE_LEVEL = "064";

    /** 
     * 严重程度
     */
    // public static final String DOMAIN_SEVERITY = "065";

    /** 
     * 卫生事件分类
     */
    // public static final String DOMAIN_EVENT_CATEGORY = "066";

    /** 
     * 起病节气归属
     */
    // public static final String DOMAIN_ONSET_SEASON = "067";

    /** 
     * 感染途径
     */
    // public static final String DOMAIN_INFECTION_ROUTE = "068";

    /** 
     * 问诊分类
     */
    // public static final String DOMAIN_INQUIRY_CATEGORY = "069";

    /** 
     * 问诊项目
     */
    // public static final String DOMAIN_INQUIRY_ITEM = "070";

    /** 
     * 免疫类型
     */
    // public static final String DOMAIN_VACCINE_TYPE = "071";

    /** 
     * 免疫接种方法
     */
    // public static final String DOMAIN_INOCULATION_METHOD = "072";

    /** 
     * 免疫接种状态
     */
    // public static final String DOMAIN_INOCULATION_STATUS = "073";

    /** 
     * 疫苗名称代码 
     */
    // public static final String DOMAIN_VACCINE = "074";

    /**
     * 医生
     */
    // public static final String DOMAIN_DOCTOR = "075";

    /** 
     * 麻醉中西医标识
     */
    // public static final String DOMAIN_CHINESE_ANESTHESIA = "076";

    /** 
     * 切口愈合等级 
     */
    public static final String DOMAIN_HEALING_GRADE = AppSettings.getConfig("DOMAIN_HEALING_GRADE");

    /** 
     * 去向
     */
    // public static final String DOMAIN_DISCHARGE_DESTINATION = "078";

    /** 
     * 门诊费用分类
     */
    public static final String DOMAIN_CHARGE_CATEGORY = AppSettings.getConfig("DOMAIN_CHARGE_CATEGORY");
    
    /**
     * 住院费用分类
     * */
    public static final String DOMAIN_CHARGE_INPATIENT = AppSettings.getConfig("DOMAIN_CHARGE_INPATIENT");
    
    /** 
     * 麻醉方式
     */
    // public static final String DOMAIN_ANESTHESIA_METHOD = "081";

    /** 
     * 长期临时 
     */
    public static final String DOMAIN_TEMPORARY_FLAG = AppSettings.getConfig("DOMAIN_TEMPORARY_FLAG");

    /** 
     * 区县 
     */
    public static final String DOMAIN_DISTRICT = AppSettings.getConfig("DOMAIN_DISTRICT");

    /**
     * 布尔 
     */
    public static final String DOMAIN_BOOLEAN = AppSettings.getConfig("DOMAIN_BOOLEAN");

    // 布尔temp
    public static final String DOMAIN_BOOLEAN_UNCERTAIN = AppSettings.getConfig("DOMAIN_BOOLEAN_UNCERTAIN");

    // 检查子项目
    public static final String DOMAIN_LAB_SUBITEM = AppSettings.getConfig("DOMAIN_LAB_SUBITEM");

    //在岗状态
    public static final String EMPLOYMENT_STATUS = AppSettings.getConfig("EMPLOYMENT_STATUS");

    //人员类型
    public static final String EMPLOYEE_TYPE = AppSettings.getConfig("EMPLOYEE_TYPE");
    
    // 紧急程度
    public static final String URGENT_LEVEL = AppSettings.getConfig("URGENT_LEVEL");
    
    // 入院类型
    public static final String DOMAIN_IPATIENT_SOURCE = AppSettings.getConfig("DOMAIN_IPATIENT_SOURCE");
    
    // 医嘱字典
    public static final String DOMAIN_ORDER = AppSettings.getConfig("DOMAIN_ORDER");
    /** 
     * 地址类型 
     */
    // public static final String DOMAIN_ADDRESS_TYPE = "085";

    // 消息或CDA中的是否类字段的一种展现值true
    public final static String TRUE_FLAG = AppSettings.getConfig("TRUE_FLAG");

    // 消息或CDA中的是否类字段的一种展现值false
    public final static String FALSE_FLAG = AppSettings.getConfig("FALSE_FLAG");

    // 消息或CDA中的是否类字段的一种展现值true对应的CDR中该相应字段值
    public final static String T_FLAG_VALUE = AppSettings.getConfig("T_FLAG_VALUE");

    // 消息或CDA中的是否类字段的一种展现值false对应的CDR中该相应字段值
    public final static String F_FLAG_VALUE = AppSettings.getConfig("F_FLAG_VALUE");

    // 系统账户状态-已激活
    public final static String ACCOUNT_STATUS_ACTIVE = AppSettings.getConfig("ACCOUNT_STATUS_ACTIVE");

    // CDR 公共变量 end

    // CDR 其他 begin

    //  医嘱类型字典待完善
    // 检查类医嘱
    public static final String EXAMINATION_ORDER = AppSettings.getConfig("EXAMINATION_ORDER");

    // 检验类医嘱
    public static final String LAB_ORDER = AppSettings.getConfig("LAB_ORDER");

    // 治疗类医嘱
    public static final String TREATMENT_ORDER = AppSettings.getConfig("TREATMENT_ORDER");

    // 护理类医嘱
    public static final String CARE_ORDER = AppSettings.getConfig("CARE_ORDER");

    // 饮食类 营养
    public static final String FOOD_AND_DRINK_ORDER = AppSettings.getConfig("FOOD_AND_DRINK_ORDER");

    // 嘱托类
    public static final String ENTRUST_ORDER = AppSettings.getConfig("ENTRUST_ORDER");

    // 材料类
    public static final String MATERIALS_ORDER = AppSettings.getConfig("MATERIALS_ORDER");

    // 病情类
    public static final String ILLNESS_STATE_ORDER = AppSettings.getConfig("ILLNESS_STATE_ORDER");

    // 会诊类医嘱
    public static final String CONSULTATION_ORDER = AppSettings.getConfig("CONSULTATION_ORDER");
    
    // 管理类医嘱
    public static final String MANAGE_ORDER = AppSettings.getConfig("MANAGE_ORDER");
    
    // 固定
    public static final String FIXED_ORDER = AppSettings.getConfig("FIXED_ORDER");
    
    // 公共
    public static final String COMMON_ORDER = AppSettings.getConfig("COMMON_ORDER");
    
    // 公共(可选)
    public static final String COMMON_CHANGE_ORDER = AppSettings.getConfig("COMMON_CHANGE_ORDER");

    // 自定义门诊用血类
    public static final String BLOOD_ORDER_OUTPATIENT = AppSettings.getConfig("BLOOD_ORDER_OUTPATIENT");

    // 用药类医嘱
    public static final String MEDICATION_ORDER = AppSettings.getConfig("MEDICATION_ORDER");

    // 草药类医嘱
    public static final String HERBAL_ORDER = AppSettings.getConfig("HERBAL_ORDER");
    
    // 手术类医嘱
    public static final String PROCEDURE_ORDER = AppSettings.getConfig("PROCEDURE_ORDER");

    // 其他类医嘱
    public static final String GENERAL_ORDER = AppSettings.getConfig("GENERAL_ORDER");
    
    // [BUG]0012463 ADD END

    // 身份证Oid
    public static final String CREDENTIAL_OID = AppSettings.getConfig("CREDENTIAL_OID");

    // 居民户口簿Oid
    public static final String REGISTRATION_OID = AppSettings.getConfig("REGISTRATION_OID");

    // 护照Oid
    public static final String PASSPORT_OID = AppSettings.getConfig("PASSPORT_OID");

    // 军官证Oid
    public static final String ARMY_CARD_OID = AppSettings.getConfig("ARMY_CARD_OID");

    // 驾驶证Oid
    public static final String DRIVER_LICENSE_OID = AppSettings.getConfig("DRIVER_LICENSE_OID");

    // 港澳居民来往内地通行证Oid
    public static final String HONGKONG_MACAO_PERMIT_OID = AppSettings.getConfig("HONGKONG_MACAO_PERMIT_OID");

    // 台湾居民来往内地通行证Oid
    public static final String TAIWAN_PERMIT_OID = AppSettings.getConfig("TAIWAN_PERMIT_OID");

    // 其他法定有效证件Oid
    public static final String OTHERS_OID = AppSettings.getConfig("OTHERS_OID");

    // 身份证类型code
    public static final String CREDENTIAL_TYPE = AppSettings.getConfig("CREDENTIAL_TYPE");

    // 居民户口簿类型code
    public static final String REGISTRATION_TYPE = AppSettings.getConfig("REGISTRATION_TYPE");

    // 护照类型code
    public static final String PASSPORT_TYPE = AppSettings.getConfig("PASSPORT_TYPE");

    // 军官证类型code
    public static final String ARMY_CARD_TYPE = AppSettings.getConfig("ARMY_CARD_TYPE");

    // 驾驶证类型code
    public static final String DRIVER_LICENSE_TYPE = AppSettings.getConfig("DRIVER_LICENSE_TYPE");

    // 港澳居民来往内地通行证类型code
    public static final String HONGKONG_MACAO_PERMIT_TYPE = AppSettings.getConfig("HONGKONG_MACAO_PERMIT_TYPE");

    // 台湾居民来往内地通行证类型code
    public static final String TAIWAN_PERMIT_TYPE = AppSettings.getConfig("TAIWAN_PERMIT_TYPE");

    // 其他法定有效证件code
    public static final String OTHERS_TYPE = AppSettings.getConfig("OTHERS_TYPE");

    // $Author :tong_meng
    // $Date : 2013/7/1 14:00$
    // [BUG]0033936 ADD BEGIN
    // CVIS检查源系统标识
    public static final String SOURCE_CVIS_EXAM = AppSettings.getConfig("SOURCE_CVIS_EXAM");

    // [BUG]0033936 ADD END

    // 检验源系统标识
    public static final String SOURCE_LAB = AppSettings.getConfig("SOURCE_LAB");
    
    // LIS检验条码系统
    public static final String SOURCE_LAB_BAR_CODE = AppSettings.getConfig("SOURCE_LAB_BAR_CODE");

    // 微生物检验源系统标识
    public static final String SOURCE_MICROBE = AppSettings.getConfig("SOURCE_MICROBE");

    // 形态学检验源系统标识
    public static final String SOURCE_MORPHOLOGY = AppSettings.getConfig("SOURCE_MORPHOLOGY");
    
    // 形态学检验源系统标识
    public static final String SOURCE_XUEKU = AppSettings.getConfig("SOURCE_XUEKU");

    // 地址类型编码:现住地址
    public static final String ADDRESS_TYPE_CODE_CURRENT = AppSettings.getConfig("ADDRESS_TYPE_CODE_CURRENT");

    // 地址类型编码:家庭地址
    public static final String ADDRESS_TYPE_CODE_HOME = AppSettings.getConfig("ADDRESS_TYPE_CODE_HOME");

    // 地址类型编码:户籍地址
    public static final String ADDRESS_TYPE_CODE_REGISTRATION = AppSettings.getConfig("ADDRESS_TYPE_CODE_REGISTRATION");

    // 地址类型编码:工作地址
    public static final String ADDRESS_TYPE_CODE_WORK = AppSettings.getConfig("ADDRESS_TYPE_CODE_WORK");

    // 地址类型编码:现住地址
    public static final String ADDRESS_TYPE_NAME_CURRENT = AppSettings.getConfig("ADDRESS_TYPE_NAME_CURRENT");

    // 地址类型编码:家庭地址
    public static final String ADDRESS_TYPE_NAME_HOME = AppSettings.getConfig("ADDRESS_TYPE_NAME_HOME");

    // 地址类型编码:户籍地址
    public static final String ADDRESS_TYPE_NAME_REGISTRATION = AppSettings.getConfig("ADDRESS_TYPE_NAME_REGISTRATION");

    // 地址类型编码:工作地址
    public static final String ADDRESS_TYPE_NAME_WORK = AppSettings.getConfig("ADDRESS_TYPE_NAME_WORK");

    // 邮件或联系电话默认值
    public static final String EMAIL_PHONE_DEFAULT_VALUE = AppSettings.getConfig("EMAIL_PHONE_DEFAULT_VALUE");

    // $Author :jin_peng
    // $Date : 2012/09/13 17:49$
    // [BUG]0009712 ADD BEGIN
    // 修改密码功能与公用服务集成链接
    public static final String MODIFY_PASSWORD_LINK = AppSettings.getConfig("MODIFY_PASSWORD_LINK");

    // [BUG]0009712 ADD END

    // $Author :jin_peng
    // $Date : 2012/09/21 14:01$
    // [BUG]0009561 ADD BEGIN
    // 登陆集成时针对目前没有密码情况密码暂时用一个恒定的常量(MD5)
    public static final String PASSWORD_MD5_INTEGRATED = AppSettings.getConfig("PASSWORD_MD5_INTEGRATED");

    // 登陆集成时针对目前没有密码情况密码暂时用一个恒定的常量
    public static final String PASSWORD_INTEGRATED = AppSettings.getConfig("PASSWORD_INTEGRATED");

    // [BUG]0009561 ADD END

    // $Author :jin_peng
    // $Date : 2012/09/28 13:48$
    // [BUG]0010132 ADD BEGIN
    // 住院视图分页标识，向前分页
    public static final String PAGING_FORWARD = AppSettings.getConfig("PAGING_FORWARD");

    // 住院视图分页标识，向后分页
    public static final String PAGING_BACKWARD = AppSettings.getConfig("PAGING_BACKWARD");

    // [BUG]0010132 ADD END

    // $Author :jin_peng
    // $Date : 2012/10/12 20:59$
    // [BUG]0010405 ADD BEGIN
    // 就诊状态为退院标识
    public static final String EXIT_INPATIENT = AppSettings.getConfig("EXIT_INPATIENT");

    // [BUG]0010405 ADD END

    // 设置地址类型字典
    public static final Map<String, String> ADDRESS_TYPE_MAP;

    // $Author :jin_peng
    // $Date : 2012/10/29 17:38$
    // [BUG]0010836 ADD BEGIN
    // CDR连接系统方式ID
    public static final String LINK_KINDS_CODE_CDR = AppSettings.getConfig("LINK_KINDS_CODE_CDR");

    // CDR连接系统方式名称
    public static final String LINK_KINDS_NAME_CDR = AppSettings.getConfig("LINK_KINDS_NAME_CDR");

    // 门急诊连接系统方式ID
    public static final String LINK_KINDS_CODE_OUT = AppSettings.getConfig("LINK_KINDS_CODE_OUT");

    // 门急诊连接系统方式名称
    public static final String LINK_KINDS_NAME_OUT = AppSettings.getConfig("LINK_KINDS_NAME_OUT");

    // 住院连接系统方式ID
    public static final String LINK_KINDS_CODE_IN = AppSettings.getConfig("LINK_KINDS_CODE_IN");

    // 住院药房连接系统方式ID
    public static final String LINK_KINDS_CODE_INPATIENT = AppSettings.getConfig("LINK_KINDS_CODE_INPATIENT");

    // 住院药房连接系统方式名称
    public static final String LINK_KINDS_NAME_INPATIENT = AppSettings.getConfig("LINK_KINDS_NAME_INPATIENT");

    // 住院连接系统方式名称
    public static final String LINK_KINDS_NAME_IN = AppSettings.getConfig("LINK_KINDS_NAME_IN");

    // 退出系统方式（退出按钮）
    public static final String EXIT_KINDS_NAME_BUTTON = AppSettings.getConfig("EXIT_KINDS_NAME_BUTTON");

    // 退出系统方式（关闭网页）
    public static final String EXIT_KINDS_NAME_CLOSED = AppSettings.getConfig("EXIT_KINDS_NAME_CLOSED");

    // 退出系统方式（session过期）
    public static final String EXIT_KINDS_NAME_EXPIRY = AppSettings.getConfig("EXIT_KINDS_NAME_EXPIRY");

    // 关闭网页退出标识
    public static final String EXIT_KINDS_FLAG_CLOSED = AppSettings.getConfig("EXIT_KINDS_FLAG_CLOSED");

    // [BUG]0010836 ADD END

    // $Author :jin_peng
    // $Date : 2012/11/09 16:46$
    // [BUG]0011316 ADD BEGIN
    // portal发布版本号
    public static final String VERSION_NO_COPYRIGHT = AppSettings.getConfig("VERSION_NO_COPYRIGHT");
    // $Author :yang_mingjie
	// $Date : 2014/06/16 10:09$
	// [BUG]0045328 MODIFY BEGIN 
    public static final String COPYRIGHT = AppSettings.getConfig("COPYRIGHT");
    // [BUG]0045328 MODIFY END
    // [BUG]0011316 ADD END
    
    //公共服务重置密码 
    //重置密码分两种，0：默认生成和用户Id相同；1：生成随机密码；
    public static final int RESETPW_TYPE= Integer.parseInt( AppSettings.getConfig("RESETPW_TYPE"));
    //邮件提醒方式有四种，0：不提醒；1：短信提醒；2：邮件提醒；3：短信 + 邮件提醒；
    public static final int RIMIND_TYPE= Integer.parseInt( AppSettings.getConfig("RIMIND_TYPE"));
  
    
    // $Author :yang_jianbo
   	// $Date : 2014/8/6 18:09$
   	// [BUG]0046046 MODIFY BEGIN 
    public static final String COMPANY_LOGIN_PIC = "images/login/login-bj.jpg";
    public static final String COMPANY_MAIN_PIC = "images/top/top-left.png";   
    public static final String HOSPITAL_PIC_FOLDER = "images/temp";
    public static final String HOSPITAL_PIC_PATH = "/properties/";
    public static final String HOSPITAL_LOGIN_PIC = AppSettings.getConfig("HOSPITAL_LOGIN_PIC");
    public static final String HOSPITAL_MAIN_PIC = AppSettings.getConfig("HOSPITAL_MAIN_PIC");
    // [BUG]0046046 MODIFY END
    
    
    // Author :jin_peng
    // Date : 2012/11/14 14:56
    // [BUG]0011478 ADD BEGIN
    // 新医嘱住院医嘱执行状态
    public static final String ORDER_STATUS_VALIDATED = AppSettings.getConfig("ORDER_STATUS_VALIDATED");

    // 发药时的医嘱执行状态
    public static final String ORDER_STATUS_DISTRIBUTE = AppSettings.getConfig("ORDER_STATUS_DISTRIBUTE");

    // [BUG]0011478 ADD END

    // Author :jia_yanqing
    // Date : 2013/3/19 14:56
    // [BUG]0014450 ADD BEGIN
    // 医嘱执行状态-已取消
    public static final String ORDER_STATUS_CANCELED = AppSettings.getConfig("ORDER_STATUS_CANCELED");

    // [BUG]0014450 ADD END

    // Author :tong_meng
    // Date : 2013/7/19 11:00
    // [BUG]0034736 ADD BEGIN
    // 医嘱执行状态-检查已预约
    public static final String ORDER_STATUS_APPOINTMENT = AppSettings.getConfig("ORDER_STATUS_APPOINTMENT");
    
    // 医嘱执行状态-已付费
    public static final String ORDER_STATUS_PAYMENT = AppSettings.getConfig("ORDER_STATUS_PAYMENT");

    // 医嘱执行状态-预约已取消
    public static final String ORDER_STATUS_CANCEL_APPOINTMENT = AppSettings.getConfig("ORDER_STATUS_CANCEL_APPOINTMENT");

    // [BUG]0034736 ADD END

    // $Author :jin_peng
    // $Date : 2013/01/07 16:00$
    // [BUG]0012888 ADD BEGIN
    // 饼图显示记录数
    public static final int PIE_DISPLAY_COUNT = Integer.parseInt(AppSettings.getConfig("PIE_DISPLAY_COUNT"));

    // 趋势图显示记录数
    public static final int TREND_DISPLAY_COUNT = Integer.parseInt(AppSettings.getConfig("TREND_DISPLAY_COUNT"));

    // [BUG]0012888 ADD END

    // $Author :jin_peng
    // $Date : 2013/03/27 10:55$
    // [BUG]0014747 ADD BEGIN
    // 构造警告通知接口条件xml的根节点
    public static final String WARNING_XML_ROOT = AppSettings.getConfig("WARNING_XML_ROOT");

    // 警告通知发送消息内容中相应检验报告链接
    public static final String REPORT_LINK = AppSettings.getConfig("REPORT_LINK");

    // 警告通知发送消息服务ID
    public static final String QUEUE_SERVICE_ID = AppSettings.getConfig("QUEUE_SERVICE_ID");

    // 警告通知发送异常标识
    public static final String WARNING_EXCEPTION_FLAG = AppSettings.getConfig("WARNING_EXCEPTION_FLAG");

    // 警告通知发送危急标识
    public static final String WARNING_CRISIS_FLAG = AppSettings.getConfig("WARNING_CRISIS_FLAG");

    // 警告通知发送发送邮件署名
    public static final String WARNING_MAIL_SIGN_NAME = AppSettings.getConfig("WARNING_MAIL_SIGN_NAME");

    // [BUG]0014747 ADD END

    // CDR 其他 end

    // $Author:chang_xuewen
    // $Date : 2013/05/16 15:20
    // $[BUG]0032417 MODIFY BEGIN
    // 门诊试图药物医嘱翻页每页显示条目数量
    public static final String ROWCNT_PER_PAGE_YW = AppSettings.getConfig("ROWCNT_PER_PAGE_YW");

    // $[BUG]0032417 MODIFY END

    public String getROWCNT_PER_PAGE_YW()
    {
        return ROWCNT_PER_PAGE_YW;
    }

    // $Author:wei_peng
    // $Date:2013/7/9 14:40
    // $[BUG]0033452 ADD BEGIN
    public static final String ORDER_TYPE_MIN_LABMICRO = AppSettings.getConfig("ORDER_TYPE_MIN_LABMICRO");

    // $[BUG]0033452 ADD END

    // $Author:jin_peng
    // $Date:2013/07/19 16:26
    // [BUG]0034978 ADD BEGIN
    public static final String REPORT_WITHDRAW_CONTENT = AppSettings.getConfig("REPORT_WITHDRAW_CONTENT");

    // [BUG]0034978 ADD END

    // $Author:jin_peng
    // $Date:2013/07/22 17:33
    // [BUG]0035016 ADD BEGIN
    public static final String RESPONSABILITY_CLAIMING_CONTENT = AppSettings.getConfig("RESPONSABILITY_CLAIMING_CONTENT");

    // [BUG]0035016 ADD END

    // $Author:jin_peng
    // $Date:2013/09/23 15:50
    // [BUG]0037540 ADD BEGIN
    // 乙肝澳抗阳性判定规则
    public static final String AUDIT_RULE_HEPATITIS = AppSettings.getConfig("AUDIT_RULE_HEPATITIS");

    // 艾滋病毒抗体阳性判定规则
    public static final String AUDIT_RULE_AIDS = AppSettings.getConfig("AUDIT_RULE_AIDS");

    // 敏感信息规则条件集合
    public static String[] AUDIT_RULES;

    // 检验报告数据名称
    public static final String AUDIT_DATA_CONTENT_LAB = AppSettings.getConfig("AUDIT_DATA_CONTENT_LAB");

    // 发送消息头中属性DomainId
    public static final String MSG_DOMAIN_ID = AppSettings.getConfig("MSG_DOMAIN_ID");

    // 发送消息头中属性ExecUnitId
    public static final String MSG_EXEC_UNIT_ID = AppSettings.getConfig("MSG_EXEC_UNIT_ID");

    // 发送消息头中属性OrderDispatchType
    public static final String MSG_ORDER_DISPATCH_TYPE = AppSettings.getConfig("MSG_ORDER_DISPATCH_TYPE");

    public static final String HOSPITAL_CODE = AppSettings.getConfig("HOSPITAL_CODE");

    public static final String HOSPITAL_NAME = AppSettings.getConfig("HOSPITAL_NAME");
    
    // 对外应用服务ID们
    public static final String EXTERNAL_MESSAGE_SERVICE_ID = AppSettings.getConfig("EXTERNAL_MESSAGE_SERVICE_ID");
    
    // [BUG]0037540 ADD END
    
    /*
     * $Author: yu_yzh
     * $Date: 2015/8/17 17:49
     * 港大检查检验分类  ADD BEGIN
     * */
    /**
     * 超声检查（全部）
    */
    public static final String ORDER_EXEC_ULTRASONIC_ALL = AppSettings.getConfig("ORDER_EXEC_ULTRASONIC_ALL");
    /**
     * 超声检查（彩B）
    */
    public static final String ORDER_EXEC_ULTRASONIC_CB = AppSettings.getConfig("ORDER_EXEC_ULTRASONIC_CB");
    /**
     * 超声检查（产检）
    */
    public static final String ORDER_EXEC_ULTRASONIC_CJCSXM = AppSettings.getConfig("ORDER_EXEC_ULTRASONIC_CJCSXM");
    /**
     * 放射（全部）
    */
    public static final String ORDER_EXEC_ERADIATION_ALL = AppSettings.getConfig("ORDER_EXEC_ERADIATION_ALL");
    /**
     * 放射（ECT肾动态显像）
    */
    public static final String ORDER_EXEC_ERADIATION_KIDNEY_DYNAMIC = AppSettings.getConfig("ORDER_EXEC_ERADIATION_KIDNEY_DYNAMIC");
    /**
     * 放射（ECT骨显像）
    */
    public static final String ORDER_EXEC_ERADIATION_BONE = AppSettings.getConfig("ORDER_EXEC_ERADIATION_BONE");
    /**
     * 放射（ECT胃肠显像）
    */
    public static final String ORDER_EXEC_ERADIATION_STOMACH_INTESTINE = AppSettings.getConfig("ORDER_EXEC_ERADIATION_STOMACH_INTESTINE");
    /**
     * 放射（ECT甲状旁腺显像）
    */
    public static final String ORDER_EXEC_ERADIATION_EPITHELIAL_BODI2S = AppSettings.getConfig("ORDER_EXEC_ERADIATION_EPITHELIAL_BODI2S");
    /**
     * 放射（ECT肾静态显像）
    */
    public static final String ORDER_EXEC_ERADIATION_KIDNEY_STATIC = AppSettings.getConfig("ORDER_EXEC_ERADIATION_KIDNEY_STATIC");
    /**
     * 放射（ECT心肌灌注）
    */
    public static final String ORDER_EXEC_ERADIATION_HEART_MUSCLE = AppSettings.getConfig("ORDER_EXEC_ERADIATION_HEART_MUSCLE");
    /**
     * 放射（ECT甲状腺显像）
    */
    public static final String ORDER_EXEC_ERADIATION_THYROID = AppSettings.getConfig("ORDER_EXEC_ERADIATION_THYROID");
    /**
     * 放射（X线）
    */
    public static final String ORDER_EXEC_ERADIATION_X = AppSettings.getConfig("ORDER_EXEC_ERADIATION_X");
    /**
     * 放射（CT）
    */
    public static final String ORDER_EXEC_ERADIATION_CT = AppSettings.getConfig("ORDER_EXEC_ERADIATION_CT");
    /**
     * 放射（ECT通用）
    */
    public static final String ORDER_EXEC_ERADIATION_GENERAL = AppSettings.getConfig("ORDER_EXEC_ERADIATION_GENERAL");
    /**
     * 放射（介入诊疗）
    */
    public static final String ORDER_EXEC_ERADIATION_JRZL = AppSettings.getConfig("ORDER_EXEC_ERADIATION_JRZL");
    /**
     * 放射（磁共振）
    */
    public static final String ORDER_EXEC_ERADIATION_CGZ = AppSettings.getConfig("ORDER_EXEC_ERADIATION_CGZ");
    /**
     * 放射（数字胃肠）
    */
    public static final String ORDER_EXEC_ERADIATION_SZCW = AppSettings.getConfig("ORDER_EXEC_ERADIATION_SZCW");
    /**
     * 放射（PET-CT）
    */
    public static final String ORDER_EXEC_ERADIATION_PETCT = AppSettings.getConfig("ORDER_EXEC_ERADIATION_PETCT");
    /**
     * 放射（X线骨密度）
    */
    public static final String ORDER_EXEC_ERADIATION_XXGMD = AppSettings.getConfig("ORDER_EXEC_ERADIATION_XXGMD");
    /**
     * 放射（乳腺X线）
    */
    public static final String ORDER_EXEC_ERADIATION_RXXX = AppSettings.getConfig("ORDER_EXEC_ERADIATION_RXXX");
    /**
     * 放射（PLCT）
    */
    public static final String ORDER_EXEC_ERADIATION_PLCT = AppSettings.getConfig("ORDER_EXEC_ERADIATION_PLCT");
    /**
     * 放射（ECT阴囊显像）
    */
    public static final String ORDER_EXEC_ERADIATION_ECTYNXX = AppSettings.getConfig("ORDER_EXEC_ERADIATION_ECTYNXX");
    /**
     * 放射（I131甲状腺）
    */
    public static final String ORDER_EXEC_ERADIATION_I131JZX = AppSettings.getConfig("ORDER_EXEC_ERADIATION_I131JZX");
    /**
     * 内镜检查（全部）
    */
    public static final String ORDER_EXEC_ENDOSCOPE_ALL = AppSettings.getConfig("ORDER_EXEC_ENDOSCOPE_ALL");
    /**
     * 内镜检查（胃镜）
    */
    public static final String ORDER_EXEC_ENDOSCOPE_WJ = AppSettings.getConfig("ORDER_EXEC_ENDOSCOPE_WJ");
    /**
     * 内镜检查（肠镜）
    */
    public static final String ORDER_EXEC_ENDOSCOPE_CJ = AppSettings.getConfig("ORDER_EXEC_ENDOSCOPE_CJ");
    /**
     * 心电（全部）
    */
    public static final String ORDER_EXEC_ECG_ALL = AppSettings.getConfig("ORDER_EXEC_ECG_ALL");
    /**
     * 心电（心电图）
    */
    public static final String ORDER_EXEC_ECG_XDT = AppSettings.getConfig("ORDER_EXEC_ECG_XDT");
    /**
     * 心电（脑电图）
    */
    public static final String ORDER_EXEC_ECG_NDT = AppSettings.getConfig("ORDER_EXEC_ECG_NDT");
    /**
     * 心电（肌电图）
    */
    public static final String ORDER_EXEC_ECG_JDT = AppSettings.getConfig("ORDER_EXEC_ECG_JDT");
    /**
     * 心电（产检）
    */
    public static final String ORDER_EXEC_ECG_CJXDT = AppSettings.getConfig("ORDER_EXEC_ECG_CJXDT");
    /**
     * 生化(全部)
    */
    public static final String ORDER_LAB_BIOCHEMISTRY_ALL = AppSettings.getConfig("ORDER_LAB_BIOCHEMISTRY_ALL");
    /**
     * 生物化学检验
    */
    public static final String ORDER_LAB_SWHXJY = AppSettings.getConfig("ORDER_LAB_SWHXJY");
    /**
     * 生化急查项目
    */
    public static final String ORDER_LAB_SHJCXM = AppSettings.getConfig("ORDER_LAB_SHJCXM");
    /**
     * 产检(生化项目)
    */
    public static final String ORDER_LAB_CJSHXM = AppSettings.getConfig("ORDER_LAB_CJSHXM");
    /**
     * 微生物检验(全部)
    */
    public static final String ORDER_LAB_MICRO_ALL = AppSettings.getConfig("ORDER_LAB_MICRO_ALL");
    /**
     * 微生物学检验
    */
    public static final String ORDER_LAB_MICRO = AppSettings.getConfig("ORDER_LAB_MICRO");
    /**
     * 微生物急查项目
    */
    public static final String ORDER_LAB_MICRO_JC = AppSettings.getConfig("ORDER_LAB_MICRO_JC");
    /**
     * 产检(微生物项目)
    */
    public static final String ORDER_LAB_MICRO_CJ = AppSettings.getConfig("ORDER_LAB_MICRO_CJ");
    /**
     * 组织和细胞病理检验（全部）
    */
    public static final String ORDER_LAB_PATHOLOGY_ALL = AppSettings.getConfig("ORDER_LAB_PATHOLOGY_ALL");
    /**
     * 组织病理学检验
    */
    public static final String ORDER_LAB_ZZ = AppSettings.getConfig("ORDER_LAB_ZZ");
    /**
     * 细胞病理
    */
    public static final String ORDER_LAB_XB = AppSettings.getConfig("ORDER_LAB_XB");
    /**
     * 血液检验（全部）
    */
    public static final String ORDER_LAB_BLOOD_ALL = AppSettings.getConfig("ORDER_LAB_BLOOD_ALL");
    /**
     * 血液检验（血库）
    */
    public static final String ORDER_LAB_BLOOD_XK = AppSettings.getConfig("ORDER_LAB_BLOOD_XK");
    /**
     * 血液检验（血液学检验）
    */
    public static final String ORDER_LAB_BLOOD_XYXJY = AppSettings.getConfig("ORDER_LAB_BLOOD_XYXJY");
    /**
     * 血液检验（血液急查项目）
    */
    public static final String ORDER_LAB_BLOOD_XYJCXM = AppSettings.getConfig("ORDER_LAB_BLOOD_XYJCXM");
    /**
     * 血液检验（产检血液项目）
    */
    public static final String ORDER_LAB_BLOOD_CJXY = "1423456";//AppSettings.getConfig("ORDER_LAB_BLOOD_CJXY");

    // 港大检查检验分类 ADD END
	
	    
    // 体格检查收缩压（高压）
    public static final String PHYSICAL_BLOOD_SYSTOLIC = AppSettings.getConfig("PHYSICAL_BLOOD_SYSTOLIC");
    
    // 体格检查舒张压（低压）
    public static final String PHYSICAL_BLOOD_DIASTOLIC = AppSettings.getConfig("PHYSICAL_BLOOD_DIASTOLIC");
	
	    /**
     * 医嘱状态-检查报告已审核
     * */
    public static final String ORDER_STATUS_EXAM_RESULT_REVIEWED = AppSettings.getConfig("ORDER_STATUS_EXAM_RESULT_REVIEWED");
    
    /**
     * 医嘱状态-检验报告已审核
     * */
    public static final String ORDER_STATUS_LAB_RESULT_REVIEWED = AppSettings.getConfig("ORDER_STATUS_LAB_RESULT_REVIEWED");
 
    /**
     * 医嘱状态-标本已采集
     * */
    public static final String ORDER_STATUS_LAB_SAMPLE_COLLECTED = AppSettings.getConfig("ORDER_STATUS_LAB_SAMPLE_COLLECTED");

    /**
     * 医嘱状态-标本已签收
     * */
    public static final String ORDER_STATUS_LAB_SAMPLE_SIGNED = AppSettings.getConfig("ORDER_STATUS_LAB_SAMPLE_SIGNED");
    
    /**
     * 医嘱状态-检查已到检
     * */
    public static final String ORDER_STATUS_EXAM_TO_CHECK = AppSettings.getConfig("ORDER_STATUS_EXAM_TO_CHECK");
    
    /**
     * 医嘱状态-已退费
     * */
    public static final String ORDER_STATUS_RETURNED_CHARGE = AppSettings.getConfig("ORDER_STATUS_RETURNED_CHARGE");
    
    /**
     * 医嘱状态-下载条码
     * */
    public static final String ORDER_STATUS_DOWNLOAD = AppSettings.getConfig("ORDER_STATUS_DOWNLOAD");
    
    /**
     * 就诊记录确认逻辑控制
     * 0，通过就诊流水号 1，通过患者lid 就诊次数，医疗机构编码
     * */
    public static final String MEDICAL_VISIT_CONFIM_LOGIC_TYPE = AppSettings.getConfig("MEDICAL_VISIT_CONFIM_LOGIC_TYPE");

    /**
     * 门诊，住院domain区分：门诊
     */
    public static final String DOMAIN_OUTPATIENT = AppSettings.getConfig("DOMAIN_OUTPATIENT");
    
    /**
     * 门诊，住院domain区分：住院
     */
    public static final String DOMAIN_INPATIENT = AppSettings.getConfig("DOMAIN_INPATIENT");
    
    /**
     * 门诊，住院就诊类型区分：门诊
     */
    public static final String VISIT_TYPE_OUTPATIENT = AppSettings.getConfig("VISIT_TYPE_OUTPATIENT");
    
    /**
     * 门诊，住院就诊类型区分：住院
     */
    public static final String VISIT_TYPE_INPATIENT = AppSettings.getConfig("VISIT_TYPE_INPATIENT");
    
    // $Author:wei_peng
    // $Date:2013/09/29 14:26
    // [BUG]0037737 ADD BEGIN
    // 接口服务ID-入院记录信息服务
    public static final String INPATIENT_RECORD_SERVICE_ID = AppSettings.getConfig("INPATIENT_RECORD_SERVICE_ID");

    // 接口服务ID-住院病案首页信息服务
    public static final String INPATIENT_FRONTPAGE_SERVICE_ID = AppSettings.getConfig("INPATIENT_FRONTPAGE_SERVICE_ID");

    // 接口服务ID-24小时入出院记录信息服务
    public static final String INPATIENT24_SERVICE_ID = AppSettings.getConfig("INPATIENT24_SERVICE_ID");

    // 接口服务ID-24小时死亡记录信息服务
    public static final String DEATHRECORD24_SERVICE_ID = AppSettings.getConfig("DEATHRECORD24_SERVICE_ID");

    // 接口服务血液透析记录信息
    public static final String HEMODIALYSIS_SERVICE_ID = AppSettings.getConfig("HEMODIALYSIS_SERVICE_ID");

    // 接口服务透析处方信息服务
    public static final String DIALYSIS_PRESCRIPTION_SERVICE_ID = AppSettings.getConfig("DIALYSIS_PRESCRIPTION_SERVICE_ID");
    
    // [BUG]0037737 END BEGIN

    // $Author :jin_peng
    // $Date : 2013/10/11 15:01$
    // [BUG]0037985 ADD BEGIN
    // 检查报告已审核状态
    public static final String ORDER_REVIEWED_STATUS = AppSettings.getConfig("ORDER_REVIEWED_STATUS");

    // [BUG]0037985 ADD END

    // $Author :jin_peng
    // $Date : 2013/10/25 10:34$
    // [BUG]0038524 ADD BEGIN
    // 麻醉service_id
    public static final String SERVICE_ID_ANESTHESIA = AppSettings.getConfig("SERVICE_ID_ANESTHESIA");

    // 术前service_id
    public static final String SERVICE_ID_PREOPERATION = AppSettings.getConfig("SERVICE_ID_PREOPERATION");

    // [BUG]0038524 ADD END

    // 检验报告类型编码-培养类型
    public static final String LAB_REPORT_TYPE_CODE_CULTIVATE = AppSettings.getConfig("LAB_REPORT_TYPE_CODE_CULTIVATE");

    // 检验报告类型编码-病毒免疫类型
    public static final String LAB_REPORT_TYPE_CODE_IMMUNITY = AppSettings.getConfig("LAB_REPORT_TYPE_CODE_IMMUNITY");

    // 检验报告类型编码-药敏类型
    public static final String LAB_REPORT_TYPE_CODE_DRUGALLERGY = AppSettings.getConfig("LAB_REPORT_TYPE_CODE_DRUGALLERGY");

    // $Author: jin_peng
    // $Date : 2013/11/25 13:41
    // $[BUG]0039829 ADD BEGIN
    // 电子病历登陆编码
    public static final String LINK_KINDS_CODE_EMR = AppSettings.getConfig("LINK_KINDS_CODE_EMR");

    public static final String LINK_KINDS_NAME_EMR = AppSettings.getConfig("LINK_KINDS_NAME_EMR");

    // $[BUG]0039829 ADD END

    // $Author :jin_peng
    // $Date : 2013/11/28 10:07$
    // [BUG]0038987 ADD BEGIN
    public static final String COOKIE_EXPIRE = AppSettings.getConfig("COOKIE_EXPIRE");

    public static final String OBTAIN_PASSWORD_URL = AppSettings.getConfig("OBTAIN_PASSWORD_URL");

    // [BUG]0038987 ADD END

    // $Author :jin_peng
    // $Date : 2013/12/19 09:51$
    // [BUG]0040598 ADD BEGIN
    public static final String ROWS_PER_PAGE_DEFAULT_LAB = AppSettings.getConfig("ROWS_PER_PAGE_DEFAULT_LAB");

    // [BUG]0040598 ADD END

    // $Author :jin_peng
    // $Date : 2013/12/20 16:45$
    // [BUG]0041070 ADD BEGIN
    public static final int LOGIN_EMR_URL_PARAMS_COUNT = Integer.parseInt(AppSettings.getConfig("LOGIN_EMR_URL_PARAMS_COUNT"));

    // [BUG]0041070 ADD END

    // $Author :jin_peng
    // $Date : 2014/02/11
    // 对外应用服务 ADD BEGIN
    public static final String ACCESS_EXTERNAL = AppSettings.getConfig("ACCESS_EXTERNAL");

    public static final String ACCESS_WS = AppSettings.getConfig("ACCESS_WS");

    // 对外应用服务 ADD END

    public static final String YG_NON_WARD = AppSettings.getConfig("YG_NON_WARD");

    // #针对药房链接与住院视图集成，默认登录用户设置
    // $Author :LI_SHENGGEN
    // $Date : 2014/04/01 15:33$
    public static final String USER_NAME = AppSettings.getConfig("USER_NAME");
    
    // #BS359患者医护信息服务，职称类别名称
    // $Author :du_xiaolan
    // $Date :2014/11/27
    public static final String TITLE_TYPE_NAME = AppSettings.getConfig("TITLE_TYPE_NAME");
    
    // 外部接口调用常量
    public static final String COMM_INTERFACE = AppSettings.getConfig("COMM_INTERFACE");

    // 电子签章显示
    public static final String ELECTRONIC_SIGNATURE = AppSettings.getConfig("ELECTRONIC_SIGNATURE");
    
    // 电子签章显示
    public static final String IMAGE_CENTER = AppSettings.getConfig("IMAGE_CENTER");
    
    // 外部链接医嘱闭环
    public static final String ORDER_CLOSED_LOOP = AppSettings.getConfig("ORDER_CLOSED_LOOP");
    
    // 区域挂号信息集成
    public static final String MIDECAL_VISIT_INTEGRATION = AppSettings.getConfig("MIDECAL_VISIT_INTEGRATION");
    
    // 电子病历信息的来源
    public static final String EMR_SOURCE_ID = AppSettings.getConfig("EMR_SOURCE_ID");
    
    // 检查报告做更新操作时数据库中相应报告中召回状态为已召回时才可执行更新操作 控制开关(0:开启 1:关闭)
    public static final String WITH_DRAW_USE_FLAG = AppSettings.getConfig("WITH_DRAW_USE_FLAG");
    
    //检查报告分级控制开关  01:不验证就诊和医嘱信息   02:验证医嘱，不验证就诊  03:验证就诊，不验证医嘱 04:就诊和医嘱都验证
    public static final String EXAMREPORT_LEVEL_CONTROL = AppSettings.getConfig("EXAMREPORT_LEVEL_CONTROL");
   
    // 预警规则疾病名称
    public static final String WARN_RULE_DISEASE = AppSettings.getConfig("WARN_RULE_DISEASE");
    
    // 预警规则疾病名称
    public static final String WARN_RULE_DAY = AppSettings.getConfig("WARN_RULE_DAY");
    
    //保存图像开关false:不保存 true:保存
    public static final String MEDICAL_IMAGE_FLAG = AppSettings.getConfig("MEDICAL_IMAGE_FLAG");
    //检验、检验报告保存时是否校验医嘱0不校验1校验
    public static final String IF_ORDER_CHECK = AppSettings.getConfig("IF_ORDER_CHECK");
    //检验、检验报告前台显示是否是PDF,false否true是
    public static final String IF_PDF_SHOW = AppSettings.getConfig("IF_PDF_SHOW");
    
    //检查报告pdf展示
    public static final String IF_EXAM_PDF_SHOW = AppSettings.getConfig("IF_EXAM_PDF_SHOW");
    //检验报告pdf展示
    public static final String IF_LAB_PDF_SHOW = AppSettings.getConfig("IF_LAB_PDF_SHOW");
    //病历文书pdf展示
    public static final String IF_DOC_PDF_SHOW = AppSettings.getConfig("IF_DOC_PDF_SHOW");
    
    public static final String V2 = "V2";
    public static final String V3 = "V3";
    
    /**
     * base64解码的编码格式
     * */
    public static final String BASE64_FORMAT = AppSettings.getConfig("BASE64_FORMAT");
    
    /**
     * V2消息文本编码格式
     * */
    public static final String V2_MESSAGE_FORMAT = AppSettings.getConfig("V2_MESSAGE_FORMAT");
    
    /**
     * OMP^O09 医嘱分类，拆分消息使用，不在列表中的作为其他医嘱处理，UZ治疗，UN护理，P西药，PCC中草药，PCZ中成药。“|”作为类别分割，“^”连接的视为同一类
     * */
    public static final List<String> OMPO09_ORDER_TYPES;
    
    /**
     * 港大手术医嘱ORM^O01自定义手术段Z01中的分隔符不符合hl7V2标准，对手术人员及手术项目节点做特殊处理。消息修改完成后将此项置为false，按照标准hl7的解析
     * */
    public static final String GD_STUPID_ZOP_SEPARATOR = AppSettings.getConfig("GD_STUPID_ZOP_SEPARATOR");
    
    /**
     * 港大医嘱类别-检查
     * */
    public static final String GD_EXAM_ORDER = AppSettings.getConfig("GD_EXAM_ORDER");
    
    /**
     * 港大医嘱类别-检验
     * */
    public static final String GD_LAB_ORDER = AppSettings.getConfig("GD_LAB_ORDER");
    
    /**
     * 港大医嘱类别-手术
     * */
    public static final String GD_PROCEDURE_ORDER = AppSettings.getConfig("GD_PROCEDURE_ORDER");
    
    /**
     * 主管医生操作类别-首次分配
     * */
    public static final String PATIENT_IN_CHARGE_FIRST = AppSettings.getConfig("PATIENT_IN_CHARGE_FIRST");
    
    /**
     * 主管医生操作类别-变更
     * */
    public static final String PATIENT_IN_CHARGE_CHANGE = AppSettings.getConfig("PATIENT_IN_CHARGE_CHANGE");
    
    /**
     * 日志中打印消息开关 true打印 false不打印
     * */
    public static final String SHOW_MESSAGE_IN_LOG = AppSettings.getConfig("SHOW_MESSAGE_IN_LOG");
    
    /**
     * 账户列表中要屏蔽的管理员账户ID，多个用|分割
     * */
    public static final String HIDDEN_ADMIN_ACCOUNT = AppSettings.getConfig("HIDDEN_ADMIN_ACCOUNT");
    
    static {
    	OMPO09_ORDER_TYPES = new ArrayList<String>();
    	String[] temp = AppSettings.getConfig("OMPO09_ORDER_TYPES").split("\\|");
    	for(String s : temp){
    		OMPO09_ORDER_TYPES.add(s);
    	}
    }
    
    /**
     * 港大挂号方式编码（无字典）- 现场挂号
     * */
    public static final String REGISTER_WAY_CODE_ON_SITE = AppSettings.getConfig("REGISTER_WAY_CODE_ON_SITE");
    
    /**
     * 港大挂号方式名称（无字典）- 现场挂号
     * */
    public static final String REGISTER_WAY_NAME_ON_SITE = AppSettings.getConfig("REGISTER_WAY_NAME_ON_SITE");
    
    /**
     * 港大挂号方式编码（无字典）- 预约挂号
     * */
    public static final String REGISTER_WAY_CODE_RESERVATION = AppSettings.getConfig("REGISTER_WAY_CODE_RESERVATION");
    
    /**
     * 港大挂号方式名称（无字典）- 预约挂号
     * */
    public static final String REGISTER_WAY_NAME_RESERVATION = AppSettings.getConfig("REGISTER_WAY_NAME_RESERVATION");
    
    /**
     * 婴儿标记
     * */
    public static final String BABY_FLAG = AppSettings.getConfig("BABY_FLAG");
    
    /** 港大检查系统编码*/
    public static final List<String> PACS_SYS_CODES;
    static {
    	String codes = AppSettings.getConfig("PACS_SYS_CODES");
    	PACS_SYS_CODES = Arrays.asList(codes.split("\\|"));
    }

    
    public static String getWarnRuleDisease() {
		return WARN_RULE_DISEASE;
	}

	public static String getWarnRuleDay() {
		return WARN_RULE_DAY;
	}

	public String getEXAMREPORT_LEVEL_CONTROL(){
    	return EXAMREPORT_LEVEL_CONTROL;
    }
    
    public String getMIDECAL_VISIT_INTEGRATION()
    {
        return MIDECAL_VISIT_INTEGRATION;
    }
    
    public String getCOMM_INTERFACE()
    {
        return COMM_INTERFACE;
    }
    
    public String getELECTRONIC_SIGNATURE()
    {
        return ELECTRONIC_SIGNATURE;
    }
    
    public String getIMAGE_CENTER()
    {
        return IMAGE_CENTER;
    }

    public String getORDER_TYPE_MIN_LABMICRO()
    {
        return ORDER_TYPE_MIN_LABMICRO;
    }

    // CDR Portal begin

    public String getOPTION_SELECT()
    {
        return OPTION_SELECT;
    }

    public int getTIMER_INPATIENT_DISPLAY_TD_COUNT()
    {
        return TIMER_INPATIENT_DISPLAY_TD_COUNT;
    }

    public String getAGE_GROUP()
    {
        return AGE_GROUP;
    }

    // CDR Portal end

    // CDR Batch begin

    /**
     * 维护医嘱类型所对应的CDR医嘱表,若数据库增加删除或更改医嘱类型字典,维护此即可
     */
    static
    {
        ORDER_TYPE_MAP = new HashMap<String, String>();
        // 检查医嘱
        ORDER_TYPE_MAP.put(EXAMINATION_ORDER, EXAMINATION_ORDER_TABLE_NAME);
        // 检验医嘱
        ORDER_TYPE_MAP.put(LAB_ORDER, LAB_ORDER_TABLE_NAME);
        // 治疗处置医嘱
        ORDER_TYPE_MAP.put(TREATMENT_ORDER, TREATMENT_ORDER_TABLE_NAME);
        // 护理医嘱
        ORDER_TYPE_MAP.put(CARE_ORDER, CARE_ORDER_TABLE_NAME);
        // 嘱托别
        ORDER_TYPE_MAP.put(ENTRUST_ORDER, CARE_ORDER_TABLE_NAME);
        // 饮食营养类
        ORDER_TYPE_MAP.put(FOOD_AND_DRINK_ORDER, CARE_ORDER_TABLE_NAME);
        // 病情类
        ORDER_TYPE_MAP.put(ILLNESS_STATE_ORDER, CARE_ORDER_TABLE_NAME);
        // 材料类
        ORDER_TYPE_MAP.put(MATERIALS_ORDER, CARE_ORDER_TABLE_NAME);
        // 会诊医嘱
        ORDER_TYPE_MAP.put(CONSULTATION_ORDER, CONSULTATION_ORDER_TABLE_NAME);
        // 管理医嘱
        ORDER_TYPE_MAP.put(MANAGE_ORDER, GENERAL_ORDER_TABLE_NAME);
        // 固定
        ORDER_TYPE_MAP.put(FIXED_ORDER, GENERAL_ORDER_TABLE_NAME);
        // 公共
        ORDER_TYPE_MAP.put(COMMON_ORDER, MEDICATION_ORDER_TABLE_NAME);
        // 公共（可选）
        ORDER_TYPE_MAP.put(COMMON_CHANGE_ORDER, MEDICATION_ORDER_TABLE_NAME);
        // 药物医嘱（草药）
        ORDER_TYPE_MAP.put(HERBAL_ORDER, MEDICATION_ORDER_TABLE_NAME);
        // 用药医嘱
        ORDER_TYPE_MAP.put(MEDICATION_ORDER, MEDICATION_ORDER_TABLE_NAME);
        // 手术医嘱
        ORDER_TYPE_MAP.put(PROCEDURE_ORDER, PROCEDURE_ORDER_TABLE_NAME);
        // 自定义门诊用血类
        ORDER_TYPE_MAP.put(BLOOD_ORDER_OUTPATIENT,
                BLOOD_REQUEST_OUTPATIENT_TABLE_NAME);
        // 其他医嘱
        ORDER_TYPE_MAP.put(GENERAL_ORDER, GENERAL_ORDER_TABLE_NAME);
        

        UPDATE_RELEVANCE_CLASS_LIST = new ArrayList<Class<?>>();

        // $Author: jin_peng
        // $Date: 2012/07/02 14:28
        // [BUG]0007617 ADD BEGIN
        // 过敏/不良反应
        UPDATE_RELEVANCE_CLASS_LIST.add(AllergicHistory.class);

        // 危险性信息
        UPDATE_RELEVANCE_CLASS_LIST.add(RiskInformation.class);

        // 自定义警告提示信息
        UPDATE_RELEVANCE_CLASS_LIST.add(CustomizeNotification.class);
        // [BUG]0007617 ADD END

        // 就诊
        UPDATE_RELEVANCE_CLASS_LIST.add(MedicalVisit.class);

        // 检查医嘱
        UPDATE_RELEVANCE_CLASS_LIST.add(ExaminationOrder.class);

        // 检查报告
        UPDATE_RELEVANCE_CLASS_LIST.add(ExaminationResult.class);

        // 诊断
        UPDATE_RELEVANCE_CLASS_LIST.add(Diagnosis.class);

        // 护理医嘱
        UPDATE_RELEVANCE_CLASS_LIST.add(CareOrder.class);

        // 护理操作
        UPDATE_RELEVANCE_CLASS_LIST.add(NursingOperation.class);

        // 用药记录
        UPDATE_RELEVANCE_CLASS_LIST.add(AdministrationRecord.class);

        // 用药医嘱
        UPDATE_RELEVANCE_CLASS_LIST.add(MedicationOrder.class);

        // 转科转床信息
        UPDATE_RELEVANCE_CLASS_LIST.add(TransferHistory.class);

        // 其他医嘱
        UPDATE_RELEVANCE_CLASS_LIST.add(GeneralOrder.class);

        // 会诊医嘱
        UPDATE_RELEVANCE_CLASS_LIST.add(ConsultationOrder.class);

        // 处方
        UPDATE_RELEVANCE_CLASS_LIST.add(Prescription.class);

        // 治疗处置医嘱
        UPDATE_RELEVANCE_CLASS_LIST.add(TreatmentOrder.class);

        // 手术医嘱
        UPDATE_RELEVANCE_CLASS_LIST.add(ProcedureOrder.class);

        // 手术
        UPDATE_RELEVANCE_CLASS_LIST.add(SurgicalProcedure.class);

        // 麻醉
        UPDATE_RELEVANCE_CLASS_LIST.add(Anesthesia.class);

        // 用血申请单
        UPDATE_RELEVANCE_CLASS_LIST.add(BloodRequest.class);

        // 病历文档
        UPDATE_RELEVANCE_CLASS_LIST.add(ClinicalDocument.class);

        // $Author: jin_peng
        // $Date: 2014/05/13 14:46
        // [BUG]0044516 DELETE BEGIN
        // 交易账单信息
        // UPDATE_RELEVANCE_CLASS_LIST.add(BillingReceipt.class);

        // 费用明细
        // UPDATE_RELEVANCE_CLASS_LIST.add(BillingItem.class);
        
        // [BUG]0044516 DELETE END

        // 挂号收费
        UPDATE_RELEVANCE_CLASS_LIST.add(RegistrationFee.class);

        // 检验医嘱
        UPDATE_RELEVANCE_CLASS_LIST.add(LabOrder.class);

        // 检验报告
        UPDATE_RELEVANCE_CLASS_LIST.add(LabResult.class);

        // 检查预约
        UPDATE_RELEVANCE_CLASS_LIST.add(ExamAppointment.class);

        // $Author: jin_peng
        // $Date: 2014/05/13 09:10
        // [BUG]0044516 DELETE BEGIN
        // 迁移所用表去除更新操作
        // 合理用药
        // UPDATE_RELEVANCE_CLASS_LIST.add(MedicationVerdict.class);

        // 费用
        // UPDATE_RELEVANCE_CLASS_LIST.add(Settlement.class);

        // [BUG]0044516 DELETE END

        // 就诊医生
        UPDATE_RELEVANCE_CLASS_LIST.add(PatientDoctor.class);

        // 主管医生
        UPDATE_RELEVANCE_CLASS_LIST.add(PatientDoctorIncharge.class);

        // 患者收藏夹
        UPDATE_RELEVANCE_CLASS_LIST.add(PatientFav.class);

        // 获取需要更新patientSn的相关业务表 通过patientLid PatientDomain orgCode更新
        UPDATE_RELEVANCE_TBALE_NAME_LIST = obtainUpdateRelevance();

        // 获取需要更新patientSn的相关业务表 通过visitSn更新
        UPDATE_RELEVANCE_TBALE_NAME_FOR_VISIT_LIST = obtainUpdateRelevanceForVisit();

        // 获取需要更新patientSn的相关业务表 通过patientSn更新
        UPDATE_RELEVANCE_TBALE_NAME_FOR_PATIENT_LIST = obtainUpdateRelevanceForPatient();

        CREDENTIAL_TYPE_MAP = new HashMap<String, String>();

        // 设置身份证oid与type对应
        CREDENTIAL_TYPE_MAP.put(CREDENTIAL_OID, CREDENTIAL_TYPE);

        // 设置户口簿oid与type对应
        CREDENTIAL_TYPE_MAP.put(REGISTRATION_OID, REGISTRATION_TYPE);

        // 设置护照oid与type对应
        CREDENTIAL_TYPE_MAP.put(PASSPORT_OID, PASSPORT_TYPE);

        // 设置军官证oid与type对应
        CREDENTIAL_TYPE_MAP.put(ARMY_CARD_OID, ARMY_CARD_TYPE);

        // 设置驾驶证oid与type对应
        CREDENTIAL_TYPE_MAP.put(DRIVER_LICENSE_OID, DRIVER_LICENSE_TYPE);

        // 设置港澳通行证oid与type对应
        CREDENTIAL_TYPE_MAP.put(HONGKONG_MACAO_PERMIT_OID,
                HONGKONG_MACAO_PERMIT_TYPE);

        // 设置台湾通行证oid与type对应
        CREDENTIAL_TYPE_MAP.put(TAIWAN_PERMIT_OID, TAIWAN_PERMIT_TYPE);

        // 设置其他有效证件oid与type对应
        CREDENTIAL_TYPE_MAP.put(OTHERS_OID, OTHERS_TYPE);

        // 设置地址类型编码及名称字典
        ADDRESS_TYPE_MAP = new HashMap<String, String>();

        // 现住地址
        ADDRESS_TYPE_MAP.put(ADDRESS_TYPE_CODE_CURRENT,
                ADDRESS_TYPE_NAME_CURRENT);

        // 家庭地址
        ADDRESS_TYPE_MAP.put(ADDRESS_TYPE_CODE_HOME, ADDRESS_TYPE_NAME_HOME);

        // 户籍地址
        ADDRESS_TYPE_MAP.put(ADDRESS_TYPE_CODE_REGISTRATION,
                ADDRESS_TYPE_NAME_REGISTRATION);

        // 工作地址
        ADDRESS_TYPE_MAP.put(ADDRESS_TYPE_CODE_WORK, ADDRESS_TYPE_NAME_WORK);

        // $Author:jin_peng
        // $Date:2013/09/23 15:50
        // [BUG]0037540 ADD BEGIN
        AUDIT_RULES = obtainAuditRulles();

        // [BUG]0037540 ADD END

    }

    // CDR Batch end

    // $Author:jin_peng
    // $Date:2014/04/17 13:14
    // [BUG]0043616 ADD BEGIN
    /**
     * 在empi消息BS512，BS513处理时会更新患者涉及到的相关业务表中patientSn字段，使其正确关联相应患者
     * 通过patientLid，patientDomain，orgCode查找的相关业务表（以后添加存在这些查询字段的表时添加到此集合中)
     */

    private static List<String> obtainUpdateRelevance()
    {
        List<String> updateRelevance = new ArrayList<String>();

        // 过敏/不良反应
        updateRelevance.add("ALLERGIC_HISTORY");

        // 危险性信息
        updateRelevance.add("RISK_INFORMATION");

        // 自定义警告提示信息
        updateRelevance.add("CUSTOMIZE_NOTIFICATION");
        // [BUG]0007617 ADD END

        // 就诊
        updateRelevance.add("MEDICAL_VISIT");

        // 检查医嘱
        updateRelevance.add("EXAMINATION_ORDER");

        // 检查报告
        updateRelevance.add("EXAMINATION_RESULT");

        // 诊断
        updateRelevance.add("DIAGNOSIS");

        // 护理医嘱
        updateRelevance.add("CARE_ORDER");

        // 护理操作
        updateRelevance.add("NURSING_OPERATION");

        // 用药记录
        updateRelevance.add("ADMINISTRATION_RECORD");

        // 用药医嘱
        updateRelevance.add("MEDICATION_ORDER");

        // 转科转床信息
        updateRelevance.add("TRANSFER_HISTORY");

        // 其他医嘱
        updateRelevance.add("GENERAL_ORDER");

        // 会诊医嘱
        updateRelevance.add("CONSULTATION_ORDER");

        // 处方
        updateRelevance.add("PRESCRIPTION");

        // 治疗处置医嘱
        updateRelevance.add("TREATMENT_ORDER");

        // 手术医嘱
        updateRelevance.add("PROCEDURE_ORDER");

        // 手术
        updateRelevance.add("SURGICAL_PROCEDURE");

        // 麻醉
        updateRelevance.add("ANESTHESIA");

        // 用血申请单
        updateRelevance.add("BLOOD_REQUEST");

        // 病历文档
        updateRelevance.add("CLINICAL_DOCUMENT");

        // $Author: jin_peng
        // $Date: 2014/05/13 14:46
        // [BUG]0044516 DELETE BEGIN
        // 交易账单信息
        // updateRelevance.add("BILLING_RECEIPT");

        // 费用明细
        // updateRelevance.add("BILLING_ITEM");
        
        // [BUG]0044516 DELETE END

        // 挂号收费
        updateRelevance.add("REGISTRATION_FEE");

        // 检验医嘱
        updateRelevance.add("LAB_ORDER");

        // 检验报告
        updateRelevance.add("LAB_RESULT");

        // 检查预约
        updateRelevance.add("EXAM_APPOINTMENT");

        // $Author: jin_peng
        // $Date: 2014/05/13 09:10
        // [BUG]0044516 DELETE BEGIN
        // 迁移所用表去除更新操作
        // 合理用药
        // updateRelevance.add("MEDICATION_VERDICT");

        // 费用表
        // updateRelevance.add("SETTLEMENT");

        // [BUG]0044516 DELETE END

        // 输血记录单
        updateRelevance.add("BLOOD_RECORD_MENU");

        return updateRelevance;
    }

    // 通过visitSn查找的相关业务表
    private static List<String> obtainUpdateRelevanceForVisit()
    {
        List<String> updateRelevanceForVisitList = new ArrayList<String>();

        // 就诊医生
        updateRelevanceForVisitList.add("PATIENT_DOCTOR");

        // 主管医生
        updateRelevanceForVisitList.add("PATIENT_DOCTOR_INCHARGE");

        return updateRelevanceForVisitList;
    }

    // 通过patientSn查找的相关业务表（以后添加只存在patientSn关联的相关业务表时添加到此集合中
    private static List<String> obtainUpdateRelevanceForPatient()
    {
        List<String> updateRelevanceForPatientList = new ArrayList<String>();

        // 患者收藏夹
        updateRelevanceForPatientList.add("PATIENT_FAV");

        return updateRelevanceForPatientList;
    }

    // [BUG]0043616 ADD END

    // $Author:jin_peng
    // $Date:2013/09/23 15:50
    // [BUG]0037540 ADD BEGIN
    private static String[] obtainAuditRulles()
    {
        List<String> auditRules = new ArrayList<String>();

        auditRules.add(AUDIT_RULE_HEPATITIS);

        auditRules.add(AUDIT_RULE_AIDS);

        return auditRules.toArray(new String[auditRules.size()]);
    }

    // [BUG]0037540 ADD END
    
    /**
     * 链接场景类型集合缓存
     * @return
     */
    public static Map<String, String> obtainSceneTypesMap()
    {
    	Map<String, String> sceneTypeMap = new HashMap<String, String>();
    	if(null != LINK_SCENE_TYPE && !LINK_SCENE_TYPE.equals("")){
    		if(LINK_SCENE_TYPE.indexOf("|")>0){
    			String[] sceneTypeArr = LINK_SCENE_TYPE.split("\\|");
    			for(int i=0;i<sceneTypeArr.length;i++){
    				String[] sceneParam = sceneTypeArr[i].split("=");
					sceneTypeMap.put(sceneParam[0],
							sceneParam.length > 1 ? sceneParam[1] : "");
    			}
    		}else{
    			String[] sceneParam = LINK_SCENE_TYPE.split("=");
				sceneTypeMap.put(sceneParam[0],
						sceneParam.length > 1 ? sceneParam[1] : "");
    		}
    	}

        return sceneTypeMap;
    }
    /**
     * 链接视图类型集合缓存
     * @return
     */
	public static Map<String, String> obtainViewTypesMap()
    {
    	Map<String, String> sceneTypeMap = new HashMap<String, String>();
    	if(null != LINK_VIEW_TYPE && !LINK_VIEW_TYPE.equals("")){
    		if(LINK_VIEW_TYPE.indexOf("|")>0){
    			String[] sceneTypeArr = LINK_VIEW_TYPE.split("\\|");
    			for(int i=0;i<sceneTypeArr.length;i++){
    				String[] sceneParam = sceneTypeArr[i].split("=");
					sceneTypeMap.put(sceneParam[0],
							sceneParam.length > 1 ? sceneParam[1] : "");
    			}
    		}else{
    			String[] sceneParam = LINK_VIEW_TYPE.split("=");
				sceneTypeMap.put(sceneParam[0],
						sceneParam.length > 1 ? sceneParam[1] : "");
    		}
    	}

        return sceneTypeMap;
    }
	
	   /**
     * 链接视图viewId类型集合缓存
     * @return
     */
	public static Map<String, String> obtainViewIdsMap()
    {
    	Map<String, String> sceneIdMap = new HashMap<String, String>();
    	if(null != LINK_VIEW_ID && !LINK_VIEW_ID.equals("")){
    		if(LINK_VIEW_ID.indexOf("|")>0){
    			String[] sceneTypeArr = LINK_VIEW_ID.split("\\|");
    			for(int i=0;i<sceneTypeArr.length;i++){
    				String[] sceneParam = sceneTypeArr[i].split("=");
    				sceneIdMap.put(sceneParam[0],
							sceneParam.length > 1 ? sceneParam[1] : "");
    			}
    		}else{
    			String[] sceneParam = LINK_VIEW_ID.split("=");
    			sceneIdMap.put(sceneParam[0],
						sceneParam.length > 1 ? sceneParam[1] : "");
    		}
    	}

        return sceneIdMap;
    }
	   /**
     * 系统id类型集合缓存
     * @return
     */
	public static Map<String, String> obtainSystemIdsMap()
    {
    	Map<String, String> sysIdMap = new HashMap<String, String>();
    	if(null != SYSTEM_ID && !SYSTEM_ID.equals("")){
    		if(SYSTEM_ID.indexOf("|")>0){
    			String[] sysTypeArr = SYSTEM_ID.split("\\|");
    			for(int i=0;i<sysTypeArr.length;i++){
    				String[] sysParam = sysTypeArr[i].split("=");
    				sysIdMap.put(sysParam[0],
    						sysParam.length > 1 ? sysParam[1] : "");
    			}
    		}else{
    			String[] sceneParam = SYSTEM_ID.split("=");
    			sysIdMap.put(sceneParam[0],
						sceneParam.length > 1 ? sceneParam[1] : "");
    		}
    	}

        return sysIdMap;
    }
	
	 /**
     * 门诊，住院domain区分：门诊缓存
     * @return
     */
	public static List<String> lstDomainOutPatient()
    {
    	List<String> lstDomain = new ArrayList<String>();
    	if(null != DOMAIN_OUTPATIENT && !DOMAIN_OUTPATIENT.equals("")){
    		if(DOMAIN_OUTPATIENT.indexOf("|")>0){
    			String[] sysTypeArr = DOMAIN_OUTPATIENT.split("\\|");
    			for(int i=0;i<sysTypeArr.length;i++){
    				lstDomain.add(sysTypeArr[i]);
    			}
    		}else{
    			lstDomain.add(DOMAIN_OUTPATIENT);
    		}
    	}

        return lstDomain;
    }
	
	/**
     * 门诊，住院就诊类型区分：门诊缓存
     * @return
     */
	public static List<String> lstDomainInPatient()
    {
    	List<String> lstDomain = new ArrayList<String>();
    	if(null != DOMAIN_INPATIENT && !DOMAIN_INPATIENT.equals("")){
    		if(DOMAIN_INPATIENT.indexOf("|")>0){
    			String[] sysTypeArr = DOMAIN_INPATIENT.split("\\|");
    			for(int i=0;i<sysTypeArr.length;i++){
    				lstDomain.add(sysTypeArr[i]);
    			}
    		}else{
    			lstDomain.add(DOMAIN_INPATIENT);
    		}
    	}

        return lstDomain;
    }
	
	/**
     * 门诊，住院domain区分：住院缓存
     * @return
     */
	public static List<String> lstVisitTypeOutPatient()
    {
    	List<String> lstDomain = new ArrayList<String>();
    	if(null != VISIT_TYPE_OUTPATIENT && !VISIT_TYPE_OUTPATIENT.equals("")){
    		if(VISIT_TYPE_OUTPATIENT.indexOf("|")>0){
    			String[] sysTypeArr = VISIT_TYPE_OUTPATIENT.split("\\|");
    			for(int i=0;i<sysTypeArr.length;i++){
    				lstDomain.add(sysTypeArr[i]);
    			}
    		}else{
    			lstDomain.add(VISIT_TYPE_OUTPATIENT);
    		}
    	}

        return lstDomain;
    }
	
	/**
     * 门诊，住院domain区分：住院缓存
     * @return
     */
	public static List<String> lstVisitTypeInPatient()
    {
    	List<String> lstDomain = new ArrayList<String>();
    	if(null != VISIT_TYPE_INPATIENT && !VISIT_TYPE_INPATIENT.equals("")){
    		if(VISIT_TYPE_INPATIENT.indexOf("|")>0){
    			String[] sysTypeArr = VISIT_TYPE_INPATIENT.split("\\|");
    			for(int i=0;i<sysTypeArr.length;i++){
    				lstDomain.add(sysTypeArr[i]);
    			}
    		}else{
    			lstDomain.add(VISIT_TYPE_INPATIENT);
    		}
    	}

        return lstDomain;
    }
	
	/**
     * 职称类别名称
     * @return
     */
	public static List<String> lstTitleTypeName()
    {
    	List<String> lstTitle = new ArrayList<String>();
    	if(null != TITLE_TYPE_NAME && !TITLE_TYPE_NAME.equals("")){
    		if(TITLE_TYPE_NAME.indexOf("|")>0){
    			String[] sysTypeArr = TITLE_TYPE_NAME.split("\\|");
    			for(int i=0;i<sysTypeArr.length;i++){
    				lstTitle.add(sysTypeArr[i]);
    			}
    		}else{
    			lstTitle.add(TITLE_TYPE_NAME);
    		}
    	}
        return lstTitle;
    }
	
    // CDR 公共变量 begin
    public String getT_FLAG_VALUE()
    {
        return T_FLAG_VALUE;
    }

    public String getF_FLAG_VALUE()
    {
        return F_FLAG_VALUE;
    }

    public String getVISIT_TYPE_CODE_INPATIENT()
    {
        return VISIT_TYPE_CODE_INPATIENT;
    }
    
    public String getCONSULTATION_ORDER()
    {
        return CONSULTATION_ORDER;
    }
    
    public String getVISIT_TYPE_CODE_INPATIENT_TR()
    {
        return VISIT_TYPE_CODE_INPATIENT_TR;
    }

    public String getVISIT_TYPE_CODE_OUTPATIENT()
    {
        return VISIT_TYPE_CODE_OUTPATIENT;
    }

    public String getVISIT_TYPE_CODE_EMERGENCY()
    {
        return VISIT_TYPE_CODE_EMERGENCY;
    }
    
    public String getVISIT_TYPE_CODE_EMERGENCY_ST()
    {
        return VISIT_TYPE_CODE_EMERGENCY_ST;
    }
    
    public String getVISIT_TYPE_CODE_PHYSICALEXAM()
    {
        return VISIT_TYPE_CODE_PHYSICALEXAM;
    }
    
    public String getVISIT_TYPE_CODE_PHYSICALEXAM_BOSS()
    {
        return VISIT_TYPE_CODE_PHYSICALEXAM_BOSS;
    }


    public String getPRESCRIPTION_TYPE_CODE_HERBAL()
    {
        return PRESCRIPTION_TYPE_CODE_HERBAL;
    }

    public String getREPORT_WITHDRAW_FLAG()
    {
        return REPORT_WITHDRAW_FLAG;
    }

    public String getREPORT_MODIFY_FLAG()
    {
        return REPORT_MODIFY_FLAG;
    }

    public String getEXAM_ITEMCLASS_ECHOCARDIOGRAPHY()
    {
        return EXAM_ITEMCLASS_ECHOCARDIOGRAPHY;
    }

    public String getEXAM_ITEMCLASS_CORONARY_ANGIOGRAPHY()
    {
        return EXAM_ITEMCLASS_CORONARY_ANGIOGRAPHY;
    }

    public String getEXAM_ITEMCLASS_CORONARY_ANGIOGRAPHY_OPRERATER()
    {
        return EXAM_ITEMCLASS_CORONARY_ANGIOGRAPHY_OPRERATER;
    }

    public static String getDETAIL_TABS_COUNT()
    {
        return DETAIL_TABS_COUNT;
    }

    public String getDOMAIN_GENDER()
    {
        return DOMAIN_GENDER;
    }

    public String getDOMAIN_MARRIAGE()
    {
        return DOMAIN_MARRIAGE;
    }

    public String getDOMAIN_NATION()
    {
        return DOMAIN_NATION;
    }

    public String getDOMAIN_NATIONALITY()
    {
        return DOMAIN_NATIONALITY;
    }

    public String getDOMAIN_OCCUPATION()
    {
        return DOMAIN_OCCUPATION;
    }

    public String getDOMAIN_EDUCATION_DEGREE()
    {
        return DOMAIN_EDUCATION_DEGREE;
    }

    public String getDOMAIN_BLOOD_ABO()
    {
        return DOMAIN_BLOOD_ABO;
    }

    public String getDOMAIN_BLOOD_RH()
    {
        return DOMAIN_BLOOD_RH;
    }

    // public String getDOMAIN_ALLERGIC_SYMPTOM()
    // {
    // return DOMAIN_ALLERGIC_SYMPTOM;
    // }

    // public String getDOMAIN_ALLERGEN()
    // {
    // return DOMAIN_ALLERGEN;
    // }

    // public String getDOMAIN_DRUG()
    // {
    // return DOMAIN_DRUG;
    // }

    // public String getDOMAIN_ALLERGIC_CONDITION()
    // {
    // return DOMAIN_ALLERGIC_CONDITION;
    // }

    public String getDOMAIN_SERIOUSNESS()
    {
        return DOMAIN_SERIOUSNESS;
    }

    // public String getDOMAIN_INDIVIDUAL_RISK()
    // {
    // return DOMAIN_INDIVIDUAL_RISK;
    // }

    public String getDOMAIN_RELATIONSHIP()
    {
        return DOMAIN_RELATIONSHIP;
    }

    public String getDOMAIN_CREDENTIAL_TYPE()
    {
        return DOMAIN_CREDENTIAL_TYPE;
    }

    public String getDOMAIN_SYSTEM_DOMAIN()
    {
        return DOMAIN_SYSTEM_DOMAIN;
    }

    public String getDOMAIN_VISIT_TYPE()
    {
        return DOMAIN_VISIT_TYPE;
    }

    public String getDOMAIN_ORG_CODE()
    {
        return DOMAIN_ORG_CODE;
    }

    public String getDOMAIN_DEPARTMENT()
    {
        return DOMAIN_DEPARTMENT;
    }

    public String getDOMAIN_STAFF()
    {
        return DOMAIN_STAFF;
    }

    // public String getDOMAIN_INSURANCE_TYPE()
    // {
    // return DOMAIN_INSURANCE_TYPE;
    // }

    public String getDOMAIN_REGISTRATION_CLASS()
    {
        return DOMAIN_REGISTRATION_CLASS;
    }

    // public String getDOMAIN_REGISTRATION_METHOD()
    // {
    // return DOMAIN_REGISTRATION_METHOD;
    // }
    //
    // public String getDOMAIN_AM_PM()
    // {
    // return DOMAIN_AM_PM;
    // }

    public String getDOMAIN_VISIT_STATUS()
    {
        return DOMAIN_VISIT_STATUS;
    }

    // public String getDOMAIN_REGISTERED_WAY()
    // {
    // return DOMAIN_REGISTERED_WAY;
    // }

    // public String getDOMAIN_DISCHARGE_STATUS()
    // {
    // return DOMAIN_DISCHARGE_STATUS;
    // }

    public String getDOMAIN_ORDER_TYPE()
    {
        return DOMAIN_ORDER_TYPE;
    }

    // public String getDOMAIN_EXECUTION_FREQUENCY()
    // {
    // return DOMAIN_EXECUTION_FREQUENCY;
    // }

    public String getDOMAIN_DIAGNOSIS_TYPE()
    {
        return DOMAIN_DIAGNOSIS_TYPE;
    }

    // public String getDOMAIN_DIAGNOISE_BASIS()
    // {
    // return DOMAIN_DIAGNOISE_BASIS;
    // }
    //
    // public String getDOMAIN_TREATMENT_TYPE()
    // {
    // return DOMAIN_TREATMENT_TYPE;
    // }

    public String getDOMAIN_WARD()
    {
        return DOMAIN_WARD;
    }

    // public String getDOMAIN_PRESCRIPTION_CLASS()
    // {
    // return DOMAIN_PRESCRIPTION_CLASS;
    // }

//    public String getDOMAIN_PRESCRIPTION_TYPE()
//    {
//        return DOMAIN_PRESCRIPTION_TYPE;
//    }

    public String getDOMAIN_MEDICINE_KIND()
    {
        return DOMAIN_MEDICINE_KIND;
    }

    public String getDOMAIN_MEDICINE_FREQ()
    {
        return DOMAIN_MEDICINE_FREQ;
    }

    public String getDOMAIN_MEDICINE_TYPE()
    {
        return DOMAIN_MEDICINE_TYPE;
    }

    public String getDOMAIN_SUPPLY_ROUTE()
    {
        return DOMAIN_SUPPLY_ROUTE;
    }

    public String getDOMAIN_MEDICINE_FORM()
    {
        return DOMAIN_MEDICINE_FORM;
    }

    // public String getDOMAIN_MUTEXES_ORDER_TYPE()
    // {
    // return DOMAIN_MUTEXES_ORDER_TYPE;
    // }

    // public String getDOMAIN_EXAM_CLASS()
    // {
    // return DOMAIN_EXAM_CLASS;
    // }

    public String getDOMAIN_EXAM_REGION()
    {
        return DOMAIN_EXAM_REGION;
    }

    // public String getDOMAIN_EXAM_METHOD()
    // {
    // return DOMAIN_EXAM_METHOD;
    // }

//    public String getDOMAIN_LAB_CLASS()
//    {
//        return DOMAIN_LAB_CLASS;
//    }

    public String getDOMAIN_SAMPLE_TYPE()
    {
        return DOMAIN_SAMPLE_TYPE;
    }

    public String getDOMAIN_LAB_ITEM()
    {
        return DOMAIN_LAB_ITEM;
    }

    public String getDOMAIN_PROCEDURE()
    {
        return DOMAIN_PROCEDURE;
    }

    public String getDOMAIN_PROCEDURE_LEVEL()
    {
        return DOMAIN_PROCEDURE_LEVEL;
    }

    // public String getDOMAIN_PARTICIPANT_IDENTITY()
    // {
    // return DOMAIN_PARTICIPANT_IDENTITY;
    // }
    //
    // public String getDOMAIN_PROCEDURE_SITE()
    // {
    // return DOMAIN_PROCEDURE_SITE;
    // }
    //
    // public String getDOMAIN_OPERATION_METHOD()
    // {
    // return DOMAIN_OPERATION_METHOD;
    // }

    public String getDOMAIN_BOOLEAN_UNCERTAIN()
    {
        return DOMAIN_BOOLEAN_UNCERTAIN;
    }

    public String getDOMAIN_LAB_SUBITEM()
    {
        return DOMAIN_LAB_SUBITEM;
    }

    public String getDOMAIN_PHYSICAN_EXAM_ITEM()
    {
        return DOMAIN_PHYSICAN_EXAM_ITEM;
    }

    // public String getDOMAIN_CARE_OPERATION_TYPE()
    // {
    // return DOMAIN_CARE_OPERATION_TYPE;
    // }
    //
    // public String getDOMAIN_CARE_LEVEL()
    // {
    // return DOMAIN_CARE_LEVEL;
    // }
    //
    // public String getDOMAIN_CARE_TYPE()
    // {
    // return DOMAIN_CARE_TYPE;
    // }

    // public String getDOMAIN_PAYMENT_METHOD()
    // {
    // return DOMAIN_PAYMENT_METHOD;
    // }


    // public String getDOMAIN_CHARGE_TYPE()
    // {
    // return DOMAIN_CHARGE_TYPE;
    // }

    public String getDOMAIN_CLINICAL_DOCUMENT_TYPE()
    {
        return DOMAIN_CLINICAL_DOCUMENT_TYPE;
    }

    // public String getDOMAIN_FACILITIES_LEVEL()
    // {
    // return DOMAIN_FACILITIES_LEVEL;
    // }
    //
    // public String getDOMAIN_DISEASE_STATUS()
    // {
    // return DOMAIN_DISEASE_STATUS;
    // }
    //
    // public String getDOMAIN_DISEASE_SYMPTOM()
    // {
    // return DOMAIN_DISEASE_SYMPTOM;
    // }

    // public String getDOMAIN_ACUTE_LEVEL()
    // {
    // return DOMAIN_ACUTE_LEVEL;
    // }
    //
    // public String getDOMAIN_SEVERITY()
    // {
    // return DOMAIN_SEVERITY;
    // }
    //
    // public String getDOMAIN_EVENT_CATEGORY()
    // {
    // return DOMAIN_EVENT_CATEGORY;
    // }

    // public String getDOMAIN_ONSET_SEASON()
    // {
    // return DOMAIN_ONSET_SEASON;
    // }
    //
    // public String getDOMAIN_INFECTION_ROUTE()
    // {
    // return DOMAIN_INFECTION_ROUTE;
    // }
    //
    // public String getDOMAIN_INQUIRY_CATEGORY()
    // {
    // return DOMAIN_INQUIRY_CATEGORY;
    // }

    // public String getDOMAIN_INQUIRY_ITEM()
    // {
    // return DOMAIN_INQUIRY_ITEM;
    // }
    //
    // public String getDOMAIN_VACCINE_TYPE()
    // {
    // return DOMAIN_VACCINE_TYPE;
    // }
    //
    // public String getDOMAIN_INOCULATION_METHOD()
    // {
    // return DOMAIN_INOCULATION_METHOD;
    // }
    //
    // public String getDOMAIN_INOCULATION_STATUS()
    // {
    // return DOMAIN_INOCULATION_STATUS;
    // }

    // public String getDOMAIN_VACCINE()
    // {
    // return DOMAIN_VACCINE;
    // }
    //
    // public String getDOMAIN_DOCTOR()
    // {
    // return DOMAIN_DOCTOR;
    // }
    //
    // public String getDOMAIN_CHINESE_ANESTHESIA()
    // {
    // return DOMAIN_CHINESE_ANESTHESIA;
    // }

    public String getDOMAIN_HEALING_GRADE()
    {
        return DOMAIN_HEALING_GRADE;
    }

    // public String getDOMAIN_DISCHARGE_DESTINATION()
    // {
    // return DOMAIN_DISCHARGE_DESTINATION;
    // }

    public String getDOMAIN_CHARGE_CATEGORY()
    {
        return DOMAIN_CHARGE_CATEGORY;
    }

    // public String getDOMAIN_ANESTHESIA_METHOD()
    // {
    // return DOMAIN_ANESTHESIA_METHOD;
    // }

    public String getDOMAIN_TEMPORARY_FLAG()
    {
        return DOMAIN_TEMPORARY_FLAG;
    }

    public String getDOMAIN_DISTRICT()
    {
        return DOMAIN_DISTRICT;
    }

    public String getDOMAIN_BOOLEAN()
    {
        return DOMAIN_BOOLEAN;
    }

    // public String getDOMAIN_ADDRESS_TYPE()
    // {
    // return DOMAIN_ADDRESS_TYPE;
    // }

    public String getMEDICAL_CLASS_WESTERN()
    {
        return MEDICAL_CLASS_WESTERN;
    }

    public String getMEDICAL_CLASS_PATENT()
    {
        return MEDICAL_CLASS_PATENT;
    }

    public String getMEDICAL_CLASS_HERB()
    {
        return MEDICAL_CLASS_HERB;
    }

    public String getMEDICAL_CLASS_HEALTH()
    {
        return MEDICAL_CLASS_HEALTH;
    }

    public String getACCOUNT_STATUS_ACTIVE()
    {
        return ACCOUNT_STATUS_ACTIVE;
    }

    public String getPATIENT_DOMAIN_OUTPATIENT()
    {
        return PATIENT_DOMAIN_OUTPATIENT;
    }

    public String getPATIENT_DOMAIN_INPATIENT()
    {
        return PATIENT_DOMAIN_INPATIENT;
    }

//    public String getPATIENT_DOMAIN_IMAGE()
//    {
//        return PATIENT_DOMAIN_IMAGE;
//    }
//
//    public String getPATIENT_DOMAIN_PHYSICAL_EXAM()
//    {
//        return PATIENT_DOMAIN_PHYSICAL_EXAM;
//    }

    // $Author:wu_jianfeng
    // $Date : 2012/09/12 14:10
    // $[BUG]0009663 MODIFY BEGIN
    // 访问控制实现
    public String getACL_PATIENT_SCOPE_01()
    {
        return ACL_PATIENT_SCOPE_01;
    }

    public String getACL_PATIENT_SCOPE_02()
    {
        return ACL_PATIENT_SCOPE_02;
    }

    public String getACL_PATIENT_SCOPE_03()
    {
        return ACL_PATIENT_SCOPE_03;
    }

    public String getACL_PATIENT_SCOPE_04()
    {
        return ACL_PATIENT_SCOPE_04;
    }

    public String getACL_PATIENT_SCOPE_05()
    {
        return ACL_PATIENT_SCOPE_05;
    }

    public String getACL_PATIENT_SCOPE_06()
    {
        return ACL_PATIENT_SCOPE_06;
    }

    public String getACL_PATIENT_SCOPE_07()
    {
        return ACL_PATIENT_SCOPE_07;
    }

    public String getACL_NOACCESS_MESSAGE()
    {
        return ACL_NOACCESS_MESSAGE;
    }

    public String getACL_PATIENT_INFO_01()
    {
        return ACL_PATIENT_INFO_01;
    }

    public String getACL_PATIENT_INFO_02()
    {
        return ACL_PATIENT_INFO_02;
    }

    public String getACL_PATIENT_INFO_03()
    {
        return ACL_PATIENT_INFO_03;
    }

    public String getACL_PATIENT_INFO_04()
    {
        return ACL_PATIENT_INFO_04;
    }

    public String getACL_PATIENT_INFO_05()
    {
        return ACL_PATIENT_INFO_05;
    }

    public String getACL_PATIENT_INFO_06()
    {
        return ACL_PATIENT_INFO_06;
    }

    public String getACL_CLINICAL_INFO_01()
    {
        return ACL_CLINICAL_INFO_01;
    }

    public String getACL_CLINICAL_INFO_02()
    {
        return ACL_CLINICAL_INFO_02;
    }

    public String getACL_CLINICAL_INFO_03()
    {
        return ACL_CLINICAL_INFO_03;
    }

    public String getACL_CLINICAL_INFO_04()
    {
        return ACL_CLINICAL_INFO_04;
    }

    public String getACL_CLINICAL_INFO_05()
    {
        return ACL_CLINICAL_INFO_05;
    }

    public String getACL_CLINICAL_INFO_06()
    {
        return ACL_CLINICAL_INFO_06;
    }

    public String getACL_CLINICAL_INFO_07()
    {
        return ACL_CLINICAL_INFO_07;
    }

    public String getACL_OCCUPATION_TYPE_DOCTOR()
    {
        return ACL_OCCUPATION_TYPE_DOCTOR;
    }

    public String getACL_USE()
    {
        return ACL_USE;
    }

    public String getACL_USE_OPEN()
    {
        return ACL_USE_OPEN;
    }

    public String getACL_USE_CLOSE()
    {
        return ACL_USE_CLOSE;
    }

    // $Author :tong_meng
    // $Date : 2012/9/26 16:45$
    // [BUG]0010038 ADD BEGIN
    public String getDOMAIN_ICD_OUTPATIENT()
    {
        return DOMAIN_ICD_OUTPATIENT;
    }

    // [BUG]0010038 ADD END

    // $[BUG]0009663 MODIFY END

    // $Author :jin_peng
    // $Date : 2012/09/13 17:49$
    // [BUG]0009712 ADD BEGIN
    public String getMODIFY_PASSWORD_LINK()
    {
        return MODIFY_PASSWORD_LINK;
    }

    // [BUG]0009712 ADD END

    // $Author:wei_peng
    // $Date:2012/9/21 16:21
    // [BUG]0009892 ADD BEGIN
    public String getVISIT_STATUS_OUT_HOSPITAL()
    {
        return VISIT_STATUS_OUT_HOSPITAL;
    }

    public String getVISIT_STATUS_DISCHARGE_HOSPITAL()
    {
        return VISIT_STATUS_DISCHARGE_HOSPITAL;
    }

    // [BUG]0009892 ADD END

    // Author:jia_yanqing
    // Date:2013/2/26 16:21
    // [BUG]0014113 ADD BEGIN
    public String getVISIT_STATUS_IN_HOSPITAL()
    {
        return VISIT_STATUS_IN_HOSPITAL;
    }

    // [BUG]0014113 ADD END

    // $Author :jin_peng
    // $Date : 2012/09/28 13:48$
    // [BUG]0010132 ADD BEGIN
    public String getPAGING_FORWARD()
    {
        return PAGING_FORWARD;
    }

    public String getPAGING_BACKWARD()
    {
        return PAGING_BACKWARD;
    }

    // [BUG]0010132 ADD END
    // [BUG]0009892 ADD END

    // $Author : wu_jianfeng
    // $Date : 2012/09/28 17:49$
    // [BUG]0010107 ADD BEGIN
    // 就诊索引视图修改
    public static String getVISIT_INDEX_VIEW_COLS()
    {
        return VISIT_INDEX_VIEW_COLS;
    }

    public static String getVISIT_INDEX_VIEW_ROWS()
    {
        return VISIT_INDEX_VIEW_ROWS;
    }

    // [BUG]0010107 ADD END

    // $Author :jin_peng
    // $Date : 2012/10/10 18:28$
    // [BUG]0010239 ADD BEGIN
    public String getTEMPORARY_FLAG()
    {
        return TEMPORARY_FLAG;
    }

    public String getLONG_TERM_FLAG()
    {
        return LONG_TERM_FLAG;
    }

    public String getDISABLE_ORG_CODE()
    {
        return DISABLE_ORG_CODE;
    }

    public String getTRUE_ORG_CODE()
    {
        return TRUE_ORG_CODE;
    }

    public String getORDER_STATUS_CANCEL()
    {
        return ORDER_STATUS_CANCEL;
    }

    // Author :jia_yanqing
    // Date : 2013/3/19 14:56
    // [BUG]0014450 ADD BEGIN
    public String getORDER_STATUS_CANCELED()
    {
        return ORDER_STATUS_CANCELED;
    }

    // [BUG]0014450 ADD END

    // [BUG]0010239 ADD END

    // Author :tong_meng
    // Date : 2013/7/19 11:00
    // [BUG]0034736 ADD BEGIN
    public String getORDER_STATUS_APPOINTMENT()
    {
        return ORDER_STATUS_CANCELED;
    }

    public String getORDER_STATUS_CANCEL_APPOINTMENT()
    {
        return ORDER_STATUS_CANCEL_APPOINTMENT;
    }

    // [BUG]0034736 ADD END

    // $Author :jin_peng
    // $Date : 2012/10/12 20:59$
    // [BUG]0010405 ADD BEGIN
    public String getEXIT_INPATIENT()
    {
        return EXIT_INPATIENT;
    }

    // [BUG]0010405 ADD END

    // $Author :jin_peng
    // $Date : 2012/10/29 17:38$
    // [BUG]0010836 ADD BEGIN
    public String getEXIT_KINDS_NAME_BUTTON()
    {
        return EXIT_KINDS_NAME_BUTTON;
    }

    public String getEXIT_KINDS_NAME_CLOSED()
    {
        return EXIT_KINDS_NAME_CLOSED;
    }

    public String getEXIT_KINDS_NAME_EXPIRY()
    {
        return EXIT_KINDS_NAME_EXPIRY;
    }

    public String getEXIT_KINDS_FLAG_CLOSED()
    {
        return EXIT_KINDS_FLAG_CLOSED;
    }

    // [BUG]0010836 ADD END

    // $Author:wei_peng
    // $Date:2012/10/12 11:03
    // [BUG]0010875 ADD BEGIN
    public String getHERBAL_MEDICINE_CLASS()
    {
        return HERBAL_MEDICINE_CLASS;
    }

    // [BUG]0010875 ADD END

    // $Author :jin_peng
    // $Date : 2013/02/19 11:20$
    // [BUG]0013981 DELETE BEGIN
    /*
     * public String getMEDICAL_CLASS_HERBAL() { return MEDICAL_CLASS_HERBAL; }
     */
    // [BUG]0013981 DELETE END

    // CDR 公共变量 end

    public String getMAIN_DIAGNOSIS()
    {
        return MAIN_DIAGNOSIS;
    }

    public String getCERTAIN_DB()
    {
        return CERTAIN_DB;
    }

    public String getUN_CERTAIN_DB(){
    	return UN_CERTAIN_DB;
    }
    
    // $Author :jin_peng
    // $Date : 2012/11/09 16:46$
    // [BUG]0011316 ADD BEGIN
    public String getVERSION_NO_COPYRIGHT()
    {
        return VERSION_NO_COPYRIGHT;
    }
    // [BUG]0011316 ADD END
    
    // $Author :yang_mingjie
 	// $Date : 2014/06/16 10:09$
 	// [BUG]0045328 MODIFY BEGIN 
    public String getCOPYRIGHT()
    {
        return COPYRIGHT;
    }
    // [BUG]0045328 MODIFY
    
    
    // Author :jia_yanqing
    // Date : 2012/12/26 16:00
    // [BUG]0012797 ADD BEGIN
    public String getNUM_UNCONCERNED_FLAG()
    {
        return NUM_UNCONCERNED_FLAG;
    }

    // [BUG]0012797 ADD END

    public String getSOURCE_CVIS_EXAM()
    {
        return SOURCE_CVIS_EXAM;
    }

    public String getSOURCE_LAB()
    {
        return SOURCE_LAB;
    }

    public String getSOURCE_MICROBE()
    {
        return SOURCE_MICROBE;
    }

    public String getSOURCE_MORPHOLOGY()
    {
        return SOURCE_MORPHOLOGY;
    }

    public String getCHARGE_STATUS_NOTCHARGE()
    {
        return CHARGE_STATUS_NOTCHARGE;
    }

    public String getWARN_OCCUPATION_TYPE_DOCTOR()
    {
        return WARN_OCCUPATION_TYPE_DOCTOR;
    }

    public String getWARN_OCCUPATION_TYPE_NURSE()
    {
        return WARN_OCCUPATION_TYPE_NURSE;
    }

    public String getDOMAIN_DRUG_DICTIONARY()
    {
        return DOMAIN_DRUG_DICTIONARY;
    }

    public static String getSelfBloodCode()
    {
        return SELF_BLOOD_CODE;
    }

    public static String getSEND_TIME_MONDAY()
    {
        return SEND_TIME_MONDAY;
    }

    public static String getSEND_TIME_TUESDAY()
    {
        return SEND_TIME_TUESDAY;
    }

    public static String getSEND_TIME_WEDNESDAY()
    {
        return SEND_TIME_WEDNESDAY;
    }

    public static String getSEND_TIME_THURSDAY()
    {
        return SEND_TIME_THURSDAY;
    }

    public static String getSEND_TIME_FRIDAY()
    {
        return SEND_TIME_FRIDAY;
    }

    public static String getSEND_TIME_SATURDAY()
    {
        return SEND_TIME_SATURDAY;
    }

    public static String getSEND_TIME_SUNDAY()
    {
        return SEND_TIME_SUNDAY;
    }

    public String getREPORT_WITHDRAW_CONTENT()
    {
        return REPORT_WITHDRAW_CONTENT;
    }

    public String getRESPONSABILITY_CLAIMING_CONTENT()
    {
        return RESPONSABILITY_CLAIMING_CONTENT;
    }

    public static String getResponsabilityClaimingContent()
    {
        return RESPONSABILITY_CLAIMING_CONTENT;
    }
    
    public static String getImageCenterUrl()
    {
        return AppSettings.getConfig("image_center_url");
    }
    
    public String getLAB_REPORT_TYPE_CODE_IMMUNITY()
    {
        return LAB_REPORT_TYPE_CODE_IMMUNITY;
    }

    public String getLAB_REPORT_TYPE_CODE_DRUGALLERGY()
    {
        return LAB_REPORT_TYPE_CODE_DRUGALLERGY;
    }

    public String getOBTAIN_PASSWORD_URL()
    {
        return OBTAIN_PASSWORD_URL;
    }

    public String getROWS_PER_PAGE_DEFAULT_LAB()
    {
        return ROWS_PER_PAGE_DEFAULT_LAB;
    }

    public int getLOGIN_EMR_URL_PARAMS_COUNT()
    {
        return LOGIN_EMR_URL_PARAMS_COUNT;
    }

    public static String getDisabledOrgCode()
    {
        return DISABLE_ORG_CODE;
    }

    public static String getTrueOrgCode()
    {
        return TRUE_ORG_CODE;
    }
    
    public String getORDER_EXEC_ULTRASONIC()
    {
        return ORDER_EXEC_ULTRASONIC;
    }
    public String getORDER_EXEC_NUCLEAR()
    {
        return ORDER_EXEC_NUCLEAR;
    }
    public String getORDER_EXEC_ERADIATION()
    {
        return ORDER_EXEC_ERADIATION;
    }
    public String getORDER_EXEC_PATHOLOGY()
    {
        return ORDER_EXEC_PATHOLOGY;
    }
    public String getORDER_EXEC_ENDOSCOPE()
    {
        return ORDER_EXEC_ENDOSCOPE;
    }
    public String getORDER_EXEC_ECG()
    {
        return ORDER_EXEC_ECG;
    }
    public String getORDER_EXEC_CYSTOSCOPE()
    {
        return ORDER_EXEC_CYSTOSCOPE;
    }
    public String getORDER_EXEC_HEARTBEAT()
    {
        return ORDER_EXEC_HEARTBEAT;
    }
    public String getORDER_EXEC_LIS()
    {
        return ORDER_EXEC_LIS;
    }
    public String getORDER_EXEC_MORPHOLOGY()
    {
        return ORDER_EXEC_MORPHOLOGY;
    }

   	public String getEMPLOYMENT_STATUS() {
		return EMPLOYMENT_STATUS;
	}

	public String getEMPLOYEE_TYPE() {
		return EMPLOYEE_TYPE;
	}

	/**
	  * 文档类别代码document_type类型集合缓存
	  * @return
	  */
	public static Map<String, String> obtainDocumentTypeMap()
	 {
	 	Map<String, String> sceneIdMap = new HashMap<String, String>();
	 	if(null != DOCUMENT_TYPE && !DOCUMENT_TYPE.equals("")){
	 		if(DOCUMENT_TYPE.indexOf("|")>0){
	 			String[] sceneTypeArr = DOCUMENT_TYPE.split("\\|");
	 			for(int i=0;i<sceneTypeArr.length;i++){
	 				String[] sceneParam = sceneTypeArr[i].split("=");
	 				sceneIdMap.put(sceneParam[0],
								sceneParam.length > 1 ? sceneParam[1] : "");
	 			}
	 		}else{
	 			String[] sceneParam = DOCUMENT_TYPE.split("=");
	 			sceneIdMap.put(sceneParam[0],
							sceneParam.length > 1 ? sceneParam[1] : "");
	 		}
	 	}
	
	     return sceneIdMap;
	 }
   	/**
	  * 文档类别名称document_type_name集合缓存
	  * @return
	  */
	public static Map<String, String> obtainDocumentTypeNameMap()
	 {
	 	Map<String, String> sceneIdMap = new HashMap<String, String>();
	 	if(null != DOCUMENT_TYPE_NAME && !DOCUMENT_TYPE_NAME.equals("")){
	 		if(DOCUMENT_TYPE_NAME.indexOf("|")>0){
	 			String[] sceneTypeArr = DOCUMENT_TYPE_NAME.split("\\|");
	 			for(int i=0;i<sceneTypeArr.length;i++){
	 				String[] sceneParam = sceneTypeArr[i].split("=");
	 				sceneIdMap.put(sceneParam[0],
								sceneParam.length > 1 ? sceneParam[1] : "");
	 			}
	 		}else{
	 			String[] sceneParam = DOCUMENT_TYPE_NAME.split("=");
	 			sceneIdMap.put(sceneParam[0],
							sceneParam.length > 1 ? sceneParam[1] : "");
	 		}
	 	}
	
	     return sceneIdMap;
	 }
	
	public String getORDER_EXEC_ULTRASONIC_ALL(){
		return ORDER_EXEC_ULTRASONIC_ALL;
	}
	public String getORDER_EXEC_ULTRASONIC_CB(){
		return ORDER_EXEC_ULTRASONIC_CB;
	}
	public String getORDER_EXEC_ULTRASONIC_CJCSXM(){
		return ORDER_EXEC_ULTRASONIC_CJCSXM;
	}
	public String getORDER_EXEC_ERADIATION_ALL(){
		return ORDER_EXEC_ERADIATION_ALL;
	}
	public String getORDER_EXEC_ERADIATION_KIDNEY_DYNAMIC(){
		return ORDER_EXEC_ERADIATION_KIDNEY_DYNAMIC;
	}
	public String getORDER_EXEC_ERADIATION_BONE(){
		return ORDER_EXEC_ERADIATION_BONE;
	}
	public String getORDER_EXEC_ERADIATION_STOMACH_INTESTINE(){
		return ORDER_EXEC_ERADIATION_STOMACH_INTESTINE;
	}
	public String getORDER_EXEC_ERADIATION_EPITHELIAL_BODI2S(){
		return ORDER_EXEC_ERADIATION_EPITHELIAL_BODI2S;
	}
	public String getORDER_EXEC_ERADIATION_KIDNEY_STATIC(){
		return ORDER_EXEC_ULTRASONIC_ALL;
	}
	public String getORDER_EXEC_ERADIATION_HEART_MUSCLE(){
		return ORDER_EXEC_ERADIATION_HEART_MUSCLE;
	}
	public String getORDER_EXEC_ERADIATION_THYROID(){
		return ORDER_EXEC_ERADIATION_THYROID;
	}
	public String getORDER_EXEC_ERADIATION_X(){
		return ORDER_EXEC_ERADIATION_X;
	}
	public String getORDER_EXEC_ERADIATION_CT(){
		return ORDER_EXEC_ERADIATION_CT;
	}
	public String getORDER_EXEC_ERADIATION_GENERAL(){
		return ORDER_EXEC_ERADIATION_GENERAL;
	}
	public String getORDER_EXEC_ERADIATION_JRZL(){
		return ORDER_EXEC_ERADIATION_JRZL;
	}
	public String getORDER_EXEC_ERADIATION_CGZ(){
		return ORDER_EXEC_ERADIATION_CGZ;
	}
	public String getORDER_EXEC_ERADIATION_SZCW(){
		return ORDER_EXEC_ERADIATION_SZCW;
	}
	public String getORDER_EXEC_ERADIATION_PETCT(){
		return ORDER_EXEC_ERADIATION_PETCT;
	}
	public String getORDER_EXEC_ERADIATION_XXGMD(){
		return ORDER_EXEC_ERADIATION_XXGMD;
	}
	public String getORDER_EXEC_ERADIATION_RXXX(){
		return ORDER_EXEC_ERADIATION_RXXX;
	}
	public String getORDER_EXEC_ERADIATION_PLCT(){
		return ORDER_EXEC_ERADIATION_PLCT;
	}
	public String getORDER_EXEC_ERADIATION_ECTYNXX(){
		return ORDER_EXEC_ERADIATION_ECTYNXX;
	}
	public String getORDER_EXEC_ERADIATION_I131JZX(){
		return ORDER_EXEC_ERADIATION_I131JZX;
	}
	public String getORDER_EXEC_ENDOSCOPE_ALL(){
		return ORDER_EXEC_ENDOSCOPE_ALL;
	}
	public String getORDER_EXEC_ENDOSCOPE_WJ(){
		return ORDER_EXEC_ENDOSCOPE_WJ;
	}
	public String getORDER_EXEC_ENDOSCOPE_CJ(){
		return ORDER_EXEC_ENDOSCOPE_CJ;
	}
	public String getORDER_EXEC_ECG_ALL(){
		return ORDER_EXEC_ECG_ALL;
	}
	public String getORDER_EXEC_ECG_XDT(){
		return ORDER_EXEC_ECG_XDT;
	}
	public String getORDER_EXEC_ECG_NDT(){
		return ORDER_EXEC_ECG_NDT;
	}
	public String getORDER_EXEC_ECG_JDT(){
		return ORDER_EXEC_ECG_JDT;
	}
	public String getORDER_EXEC_ECG_CJXDT(){
		return ORDER_EXEC_ECG_CJXDT;
	}
	public String getORDER_LAB_BIOCHEMISTRY_ALL(){
		return ORDER_LAB_BIOCHEMISTRY_ALL;
	}
	public String getORDER_LAB_SWHXJY(){
		return ORDER_LAB_SWHXJY;
	}
	public String getORDER_LAB_SHJCXM(){
		return ORDER_LAB_SHJCXM;
	}
	public String getORDER_LAB_CJSHXM(){
		return ORDER_LAB_CJSHXM;
	}
	public String getORDER_LAB_MICRO_ALL(){
		return ORDER_LAB_MICRO_ALL;
	}
	public String getORDER_LAB_MICRO(){
		return ORDER_LAB_MICRO;
	}
	public String getORDER_LAB_MICRO_JC(){
		return ORDER_LAB_MICRO_JC;
	}
	public String getORDER_LAB_MICRO_CJ(){
		return ORDER_LAB_MICRO_CJ;
	}
	public String getORDER_LAB_PATHOLOGY_ALL(){
		return ORDER_LAB_PATHOLOGY_ALL;
	}
	public String getORDER_LAB_ZZ(){
		return ORDER_LAB_ZZ;
	}
	public String getORDER_LAB_XB(){
		return ORDER_LAB_XB;
	}
	public String getORDER_LAB_BLOOD_ALL(){
		return ORDER_LAB_BLOOD_ALL;
	}
	public String getORDER_LAB_BLOOD_XK(){
		return ORDER_LAB_BLOOD_XK;
	}
	public String getORDER_LAB_BLOOD_XYXJY(){
		return ORDER_LAB_BLOOD_XYXJY;
	}
	public String getORDER_LAB_BLOOD_XYJCXM(){
		return ORDER_LAB_BLOOD_XYJCXM;
	}
	public String getORDER_LAB_BLOOD_CJXY(){
		return ORDER_LAB_BLOOD_CJXY;
	}
}
