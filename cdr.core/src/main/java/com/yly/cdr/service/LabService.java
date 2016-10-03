/**
 * Copryright
 */
package com.yly.cdr.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Transactional;

import com.founder.fasf.web.paging.PagingContext;
import com.yly.cdr.dto.LabDto;
import com.yly.cdr.dto.MorphologyDto;
import com.yly.cdr.dto.OrderStepDto;
import com.yly.cdr.dto.lab.LabListSearchParameters;
import com.yly.cdr.dto.lab.LabResultItemDto;
import com.yly.cdr.entity.ClinicalDocument;
import com.yly.cdr.entity.LabOrder;
import com.yly.cdr.entity.LabOrderLabResult;
import com.yly.cdr.entity.LabResult;
import com.yly.cdr.entity.LabResultCompositeItem;
import com.yly.cdr.entity.MedicalImaging;
import com.yly.cdr.entity.MedicalVisit;
import com.yly.cdr.entity.Message;
import com.yly.cdr.entity.Patient;
import com.yly.cdr.entity.WarningNotice;

/**
 * 用于检验报告相关存储
 * @author jin_peng
 */
public interface LabService
{

    /**
     * 获取检验报告列表
     * @param patientSn 患者内部序列号
     * @param labListSearchParameters 查询条件对象
     * @param pagingContext 分页对象
     * @return 检验报告列表对象集合
     */
    @Transactional
    public LabDto[] selectAllLabList(String patientSn,
            LabListSearchParameters labListSearchParameters,
            PagingContext pagingContext);

    // $Author:bin_zhang
    // $Date:2012/9/28 10:42
    // $[BUG]BUG0010082 ADD BEGIN
    /**
     * 获取检验报告列表
     * @param visitSn 就诊内部序列号
     * @return 门诊检验列表显示
     */
    @Transactional
    public LabDto[] selectLabList(String visitSn);

    // $[BUG]BUG0010082 ADD END

    // $Author:chang_xuewen
    // $Date:2013/07/01 17:10
    // $[BUG]0032417 ADD BEGIN
    /**
     * 增加pagingContext满足门诊视图翻页需求
     * @param visitSn
     * @param pagingContext
     * @return
     */
    @Transactional
    public LabDto[] selectLabList(String visitSn, PagingContext pagingContext);

    // $[BUG]0032417 ADD BEGIN

    /**
     * 获取检验项目信息
     * @param compositeItemSn 检查项目内部序列号
     * @return 检验项目集合
     */
    @Transactional
    public List<LabResultItemDto> selectLabResult(String compositeItemSn);

    /**
     * 通过检验医嘱获取检验报告信息
     * @param orderSn 检验医嘱内部序列号
     * @return 检验报告集合
     */
    @Transactional
    public List<LabResult> selectLabReports(String orderSn);

    /**
     * 获取检验申请单集合
     * @param patientSn 患者内部序列号
     * @param labReportSn 检验报告内部序列号
     * @param itemCode 检验项目代码
     * @return 检验医嘱
     */
    @Transactional
    public LabOrder selectLabRequest(String orderSn);

    /**
     * 查询患者所有此项检验的检验项目结果信息
     * @param patientSn 患者内部序列号
     * @param itemCode 项目编码
     * @param itemName 项目中文名
     * @param pagingContext 分页项
     * @return 检验项目结果集合
     */
    @Transactional
    public List<LabResultItemDto> selectRelatedLabResult(String patientSn,
            String itemCode, String itemName, PagingContext pagingContext);

    /**
     * 获取检验报告信息(通过报告内部序列号)
     * @param reportSn 检验报告内部序列号
     * @return 检验报告对象集合
     */
    @Transactional
    public LabResult selectReportById(String reportSn);

    /**
     * 获取检验医嘱信息(通过医嘱内部序列号)
     * @param orderSn 检验医嘱内部序列号
     * @return 检验医嘱对象集合
     */
    @Transactional
    public LabOrder selectLabOrderById(String orderSn);

    /**
     * 通过检验报告和检验项目编码查找对应的检验医嘱
     * @param labReportSn 检验报告内部序列号
     * @param itemCode 检验项目编码
     * @return
     */
    @Transactional
    public LabOrder selectLabOrderByReportAndItemCode(String labReportSn,
            String itemCode);

    /**
     * 获取检验报告关联的就诊信息
     * @param map 查询条件
     * @return 检验报告关联就诊对象集合
     */
    @Transactional
    public MedicalVisit selectMedicalVisitById(Map<String, Object> map);

    /**
     * 获取检验报告信息(根据多个查询条件)
     * @param map 查询条件
     * @return 检验报告对象
     */
    @Transactional
    public LabResult selectLabResultByCondition(Map<String, Object> map);

    /**
     * 获取检验报告项目信息(根据多个查询条件)
     * @param map 查询条件
     * @return 检验报告项目对象集合
     */
    @Transactional
    public List<Object> selectLabResultCompositeByCondition(
            Map<String, Object> map);

    /**
     * 获取检验大项
     * @param itemCode 项目编码
     * @param labReportSn 检验报告内部序列号
     * @return 检验大项
     */
    @Transactional
    public LabResultCompositeItem selectCompItemByCodeAndSn(String itemCode,
            String labReportSn);

    /**
     * 获取检验大项集合
     * @param labReportSn 检验报告内部序列号
     * @return 检验大项集合
     */
    @Transactional
    public List<LabResultCompositeItem> selectCompItemBySn(String labReportSn);

    /**
     * 检验大项
     * @param compositeItemSn 检验大项内部序列号
     * @return 检验大项
     */
    @Transactional
    public LabResultCompositeItem selectComItemById(String compositeItemSn);

    /**
     * 获取检验报告结果信息(根据多个查询条件)
     * @param map 查询条件
     * @return 检验报告结果项目对象集合
     */
    @Transactional
    public List<Object> selectLabResultItemByCondition(Map<String, Object> map);

    /**
     * 获取检验报告结果对应的抗菌药物信息(根据多个查询条件)
     * @param map 查询条件
     * @return 抗菌药物结果项目对象集合
     */
    @Transactional
    public List<Object> selectAntibacterialDrugByCondition(
            Map<String, Object> map);

    /**
     * 获取检验影像信息(根据多个查询条件)
     * @param map 查询条件
     * @return 检验影像对象集合
     */
    @Transactional
    public List<Object> selectLabImageByCondition(Map<String, Object> map);

    /**
     * 保存
     * @param entity 根据业务传入entity
     * @return 是否保存成功标识
     */
    @Transactional
    public int save(Object entity);

    /**
     * 修改部分信息
     * @param entity 根据业务传入entity
     * @param propertyNames 需要修改的字段名称
     * @return 是否保存成功标识
     */
    @Transactional
    public int modifypartialy(Object entity, String... propertyNames);

    /**
     * 修改全部信息
     * @param entity 根据业务传入entity
     * @return 是否保存成功标识
     */
    @Transactional
    public int update(Object entity);

    // $Author :jin_peng
    // $Date : 2012/09/20 14:47$
    // [BUG]0009691 MODIFY BEGIN
    // 检验医嘱获取方式现改为用标本号
    /**
     * 检验报告新增
     * @param message 消息
     * @param labResult 检验报告对象
     * @param labResultCompositeItemList 检验项目对象集合
     * @param labResultItemList 检验结果对象集合
     * @param antibacterialDrugList 抗菌药物对象集合
     * @param labImageList 检验影像对象集合
     * @param labOrderLabResults 检验报告和检验医嘱关联关系集合
     * @param clinicalDocument 病历文档
     */
    @Transactional
    public void saveLabResult(Message message, LabResult labResult,
            List<Object> lrciList, List<Object> labResultItemList,
            List<Object> antibacterialDrugList, List<Object> labImageList,
            List<LabOrderLabResult> labOrderLabResults,
            ClinicalDocument clinicalDocument);

    // [BUG]0009691 MODIFY END

    /**
     * 检验报告修改
     * @param message 消息
     * @param labResult 检验报告对象
     * @param antibacterialDrugDeleteList 抗菌药物删除对象集合
     * @param labResultItemDeleteList 检验结果删除对象集合
     * @param lrciDeleteList 检验项目删除对象集合
     * @param labImageDeleteList 检验影像删除对象集合
     * @param lrciList 检验项目对象集合
     * @param labResultItemList 检验结果对象集合
     * @param antibacterialDrugList 抗菌药物对象集合
     * @param labImageList 检验影像对象集合
     * @param clinicalDocument 病历文档
     * @param documentSnToDelete 待删除病历文档序列号
     * @param systemTime 消息处理时间
     */
    @Transactional
    public void updateLabResult(Message message, LabResult labResult,
            List<Object> antibacterialDrugDeleteList,
            List<Object> labResultItemDeleteList, List<Object> lrciDeleteList,
            List<Object> labImageDeleteList, List<Object> lrciList,
            List<Object> labResultItemList, List<Object> antibacterialDrugList,
            List<Object> labImageList, ClinicalDocument clinicalDocument,
            BigDecimal documentSnToDelete, Date systemTime);

    // $Author :jin_peng
    // $Date : 2012/09/20 14:47$
    // [BUG]0009691 MODIFY BEGIN
    /**
     * 新增形态学检验报告及其相关信息
     */
    @Transactional
    public void saveMorphologyLabResult(Message message,
            ClinicalDocument clinicalDocument, LabResult labResult,
            List<List<Object>> labResultCompositeItemAndResultList,
            List<List<Object>> labImageList,
            List<LabOrderLabResult> labOrderLabResults);

    // [BUG]0009691 MODIFY END

    /**
     * 更新形态学检验报告及其相关信息
     */
    // Author :jia_yanqing
    // Date : 2013/4/17 10:47
    // [BUG]0015141 MODIFY BEGIN
    @Transactional
    public void updateMorphologyLabResult(Message message,
            ClinicalDocument clinicalDocument, LabResult labResult,
            List<List<Object>> labResultCompositeItemAndResultList,
            List<List<Object>> labImageList,
            List<Object> labImageListForDelete, List<String> orderLid);

    // [BUG]0015141 MODIFY END

    /**
     * 查询对应报告的报告图片
     */
    @Transactional
    public MedicalImaging selectLabImage(String labReportSn,
            String imageTypeFlag);

    /**
     * 查询形态学报告信息
     */
    @Transactional
    public MorphologyDto selectMorphologyReport(String labReportSn);

    /**
     * 根据申请人和申请科室查找要发送警告信息的警告通知表
     * @param requestPerson
     * @param deptId
     * @param warnOrUnnormal
     * @param orgCode
     * @return
     */
    @Transactional
    public List<WarningNotice> selectWarningNotice(String requestPerson,
            String deptId, String warnOrUnnormal, String orgCode);

    /**
     * 传染病预警人员设定
     * @param ruleGroup 
     * @param orgCode
     * @return
     */
    @Transactional
    public List<WarningNotice> selectWarningInfectNotice(String ruleGroup,
            String orgCode, String wardNo);


    // $Author :jin_peng
    // $Date : 2013/04/22 09:40$
    // [BUG]0014747 ADD BEGIN
    /**
     * 查询警告通知检验具体结果异常或危机内容
     * @param labReportLid 报告本地id
     * @param sourceSystemId 源系统id
     * @param orgCode 医疗机构代码
     * @return 警告通知检验具体结果异常或危机内容
     */
    public List<LabDto> selectWarningLabResultItem(String labReportLid,
            String sourceSystemId, String orgCode);

    /**
     * 查询患者记录
     * @param patientSn 患者内部序列号
     * @return 患者记录
     */
    public Patient getPatient(BigDecimal patientSn);

    /**
     * 获取就诊记录
     * @param visitSn 就诊内部序列号
     * @return 就诊记录
     */
    public MedicalVisit getMedicalVisit(BigDecimal visitSn);

    // [BUG]0014747 ADD END

    /**
     * 根据医嘱内部序列号查询医嘱状态集合
     * @param orderSn
     * @return
     * @throws Exception
     */
    @Transactional
    public List<OrderStepDto> selectLabOrderSteps(LabOrder labOrder,String domain)
            throws Exception;

    
    /**
     * 查询医嘱闭环（港大）
     * @throws Exception 
     * */
    @Transactional
    public List<OrderStepDto> selectLabOrderStepsForGD(LabOrder labOrder,String visitType) throws Exception;
    
    // $Author:jin_peng
    // $Date:2013/09/23 15:50
    // [BUG]0037540 ADD BEGIN
    /**
     * 插入敏感信息审计内容
     * @param lrciItemCodeWhere 检验大项编码条件
     * @param lriRuleWhere 检验具体结果规则条件
     * @param accountLogSn 登陆日志内部序列号
     * @param patientLid 患者lid
     * @param patientDomain 域id
     * @param dataTypeName 数据类型名称
     * @param businessDataNo 业务数据编号
     * @param labReportSn 检验报告内部序列号
     * @param orgCode 医疗机构代码 
     * @param orgName 医疗机构名称
     * @param systemTime 系统时间
     * @return
     */
    @Transactional
    public void insertLabAuditLog(String lrciItemCodeWhere,
            String lriRuleWhere, BigDecimal accountLogSn, String patientLid,
            String patientDomain, String dataTypeName, String businessDataNo,
            BigDecimal labReportSn, String systemTime, String orgCode,
            String orgName);
    
    /**
     * 根据报告日期查询intervalHours小时内的检验项目
     * @param patientID 患者id
     * @param reportDate 报告日期
     * @param intervalHours 时间间隔（小时）
     * @param itemsList 检验项目list
     * @param unnormal 不正常项
     * @param systemID 系统ID
     * @return
     */
    @Transactional
    public List<String> selectLiverInfectiousItem(String patientID,String reportDate,
    		int intervalHours,List<String> itemsList,String unnormal,String systemID);

    // [BUG]0037540 ADD END
}
