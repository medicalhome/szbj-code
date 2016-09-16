/**
 * Copryright
 */
package com.founder.cdr.dao;

import java.math.BigDecimal;
import java.util.List;

import com.founder.cdr.dto.LabDto;
import com.founder.cdr.dto.MorphologyDto;
import com.founder.cdr.dto.lab.LabListSearchParameters;
import com.founder.cdr.dto.lab.LabResultItemDto;
import com.founder.cdr.entity.LabOrder;
import com.founder.cdr.entity.LabResult;
import com.founder.cdr.entity.WarningNotice;
import com.founder.fasf.core.util.daohelper.annotation.Arguments;
import com.founder.fasf.web.paging.PagingContext;

/**
 * 用于检验报告相关存储
 * @author jin_peng
 */
public interface LabDao
{

    /**
     * 根据检验报告列表查询条件获取检验报告列表信息
     * @param patientSn 患者内部序列号
     * @param labListSearchParameters 查询条件对象
     * @param pagingContext 分页对象
     * @return 检验报告列表对象集合
     */
    @Arguments({ "patientSn", "labListSearchParameters" })
    public LabDto[] selectAllLabList(String patientSn,
            LabListSearchParameters labListSearchParameters,
            PagingContext pagingContext);

    // $Author:bin_zhang
    // $Date:2012/9/28 10:42
    // $[BUG]BUG0010082 ADD BEGIN
    /**
     * 根据检验列表查询条件获取检验报告列表信息
     * @param visitSn 患者内部序列号
     * @return 检验列表对象
     */
    @Arguments({ "visitSn" })
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
    @Arguments({ "visitSn" })
    public LabDto[] selectLabList(String visitSn, PagingContext pagingContext);

    // $[BUG]0032417 ADD BEGIN

    /**
     * 获取检验项目信息
     * @param compositeItemSn 检查项目内部序列号
     * @return 检验项目集合
     */
    @Arguments({ "compositeItemSn" })
    public List<LabResultItemDto> selectLabResult(String compositeItemSn);

    /**
     * 通过检验医嘱获取检验报告信息
     * @param orderSn 检验医嘱内部序列号
     * @return 检验报告集合
     */
    @Arguments({ "orderSn" })
    public List<LabResult> selectLabReports(String orderSn);

    /**
     * 获取检验申请单集合
     * @param patientSn 患者内部序列号
     * @param labReportSn 检验报告内部序列号
     * @param itemCode 检验项目代码
     * @return 检验医嘱集合（其中包含所需的检验申请单）
     */
    @Arguments({ "patientSn", "labReportSn" })
    public List<LabOrder> selectLabRequest(String patientSn, String labReportSn);

    /**
     * 查询患者所有此项检验的检验项目结果信息
     * @param patientSn 患者内部序列号
     * @param itemCode 项目编码
     * @param itemName 项目中文名
     * @param pagingContext 分页项
     * @return 检验项目结果集合
     */
    @Arguments({ "patientSn", "itemCode", "itemName" })
    public List<LabResultItemDto> selectRelatedLabResult(String patientSn,
            String itemCode, String itemName, PagingContext pagingContext);

    /**
     * 根据检验报告内部序列号查找形态学报告信息
     * @param labReportSn 报告内部序列号
     */
    @Arguments({ "labReportSn" })
    public List<MorphologyDto> selectMorphologyReport(String labReportSn);

    /**
     * 根据申请单编号，找到申请单数据
     * @param requestNoList
     * @param patientLid 患者本地ID
     * @param patientDomain 域ID
     * @return
     */
    @Arguments({ "requestNoList", "patientLid", "patientDomain", "orgCode", "visitSn"})
    public List<LabOrder> selectRequestByRequestNo(List<String> requestNoList,
            String patientLid, String patientDomain, String orgCode, BigDecimal visitSn);

    /**
     * 根据申请人和申请科室查找要发送警告信息的警告通知表
     * @param requestPerson
     * @param deptId
     * @param warnOrUnnormal
     * @param warnOccupationTypeNurse 
     * @param orgCode
     * @return
     */
    @Arguments({ "requestPerson", "deptId", "warnOrUnnormal",
            "warnOccupationTypeDoctor", "warnOccupationTypeNurse", "orgCode" })
    public List<WarningNotice> selectWarningNotice(String requestPerson,
            String deptId, String warnOrUnnormal,
            String warnOccupationTypeDoctor, String warnOccupationTypeNurse,
            String orgCode);
    
    /**
     * 传染病预警人员设定
     * @param ruleGroup 
     * @param orgCode
     * @return
     */
    /* $Author :chang_xuewen
     * $Date : 2014/03/21 11:00$
     * [BUG]0043136 ADD BEGIN
     */
    @Arguments({ "ruleGroup", "orgCode" ,"wardNo"})
    public List<WarningNotice> selectWarningInfectNotice(String ruleGroup,
            String orgCode, String wardNo);
    /*[BUG]0043136 ADD END*/
    
    // $Author :jin_peng
    // $Date : 2013/04/22 09:40$
    // [BUG]0014747 ADD BEGIN
    /**
     * 查询警告通知检验具体结果异常或危机内容
     * @param labReportLid 报告本地id
     * @param sourceSystemId 源系统id
     * @return 警告通知检验具体结果异常或危机内容
     */
    @Arguments({ "labReportLid", "sourceSystemId", "orgCode" })
    public List<LabDto> selectWarningLabResultItem(String labReportLid,
            String sourceSystemId, String orgCode);

    // [BUG]0014747 ADD END

    @Arguments({ "labReportSn", "itemCode" })
    public List<LabOrder> selectOrderByReportAndItemCode(String labReportSn,
            String itemCode);

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
     * @param systemTime 系统时间
     * @return
     */
    @Arguments({ "lrciItemCodeWhere", "lriRuleWhere", "accountLogSn",
            "patientLid", "patientDomain", "dataTypeName", "businessDataNo",
            "labReportSn", "systemTime", "orgCode", "orgName" })
    public void insertLabAuditLog(String lrciItemCodeWhere,
            String lriRuleWhere, BigDecimal accountLogSn, String patientLid,
            String patientDomain, String dataTypeName, String businessDataNo,
            BigDecimal labReportSn, String systemTime, String orgCode,
            String orgName);

    // [BUG]0037540 ADD END
    @Arguments({ "patientLid", "patientDomain", "orgCode",
        "visitTimes", "infectiousName" })
    public int selectCount(String patientLid, String patientDomain, String orgCode, String visitTimes, String infectiousName);

    /**
     * 根据报告日期查询intervalHours小时内的检验项目
     * @param patientID 患者id
     * @param reportDate 报告日期
     * @param intervalHours 时间间隔（小时）
     * @param itemsList 检验项目list
     * @param unnormal 查询不正常项
     * @param systemID 系统ID
     * @return
     */
    @Arguments({ "patientID", "reportDate", "intervalHours", "itemsList", "unnormal", "systemID" })
	public List<String> selectLiverInfectiousItem(String patientID,String reportDate,
			int intervalHours,List<String> itemsList,String unnormal,String systemID);

}
