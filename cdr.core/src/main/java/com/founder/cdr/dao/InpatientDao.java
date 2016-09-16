/**
 * Copryright
 */
package com.founder.cdr.dao;

import java.math.BigDecimal;
import java.util.List;

import com.founder.cdr.dto.TimerAndInpatientDto;
import com.founder.cdr.entity.TransferHistory;
import com.founder.fasf.core.util.daohelper.annotation.Arguments;

/**
 * 用于住院视图操作
 * @author jin_peng
 * @version 1.0, 2012/05/29
 */
public interface InpatientDao
{
	// $Author:chang_xuewen
	// $Date : 2013/12/23 14:10
	// $[BUG]0040770 MODIFY BEGIN
    /**
     * 根据患者内部序列号和某次就诊的某个住院日期获取住院视图列表头信息
     * @param itemFlag 标识获取住院视图中哪部分信息，例如：inpatient代表获取住院信息
     * @param patientSn 患者内部序列号
     * @param inpatientType 住院类型
     * @param inpatientDate 某次就诊的某个住院日期
     * @param rowNumStart 分页开始记录
     * @param rowNumEnd 分页结束记录
     * @param exitInpatient 退院标识
     * @return 住院视图列表头信息相应对象集合
     */
    @Arguments({ "itemFlag", "patientSn", "inpatientType", "inpatientDate",
            "rowNumStart", "rowNumEnd", "exitInpatient" ,"orgCode"})
    public List<TimerAndInpatientDto> selectInpatientList(String itemFlag,
            String patientSn, String inpatientType, String inpatientDate,
            int rowNumStart, int rowNumEnd, String exitInpatient, String orgCode);
    // $[BUG]0040770 MODIFY END
    /**
     * 根据相应患者的就诊时的就诊内部序列号和该次就诊的某个住院日期获取住院视图具体内容列表，具体信息部分 例如：诊断，用药医嘱等
     * @param itemFlag 标识获取住院视图中哪部分信息，例如：diagnosis代表获取诊断信息
     * @param visitSn 就诊内部序列号
     * @param inpatientDate 某次就诊的某个住院日期
     * @param displayCount 具体内容部分每Td中内容条数
     * @param userSn 用户sn，根据sn排除其在hide_drug表的信息
     * @return 住院视图列表具体信息对象集合
     */
    @Arguments({ "itemFlag", "visitSn", "inpatientDate", "displayCount", "userSn" })
    public List<TimerAndInpatientDto> selectInpatientList(String itemFlag,
            BigDecimal visitSn, String inpatientDate, int displayCount, String userSn);

    // $Author:jin_peng
    // $Date:2012/11/12 15:09
    // $[BUG]0011225 ADD BEGIN
    /**
     * 根据相应患者的就诊时的就诊内部序列号和该次就诊的某个住院日期段（一次性获取）获取住院视图具体内容列表，具体信息部分 例如：诊断，用药医嘱等
     * @param itemFlag 标识获取住院视图中哪部分信息，例如：diagnosis代表获取诊断信息
     * @param visitSn 就诊内部序列号
     * @param inpatientStartDate 某次就诊的某个住院日期段开始时间
     * @param inpatientEndDate 某次就诊的某个住院日期段结束时间
     * @param displayCount 具体内容部分每Td中内容条数
     * @return 住院视图列表具体信息对象集合
     */
    @Arguments({ "itemFlag", "visitSn", "inpatientStartDate",
            "inpatientEndDate", "displayCount", "orgCode"})
    public List<TimerAndInpatientDto> selectInpatientList(String itemFlag,
            BigDecimal visitSn, String inpatientStartDate,
            String inpatientEndDate, int displayCount, String orgCode, int eFlag);

    // $[BUG]0011225 ADD END

    /**
     * 根据相应患者的就诊时的就诊内部序列号和该次就诊的某个住院日期和体格检查项目编码获取住院视图三测单部分内容
     * @param itemFlag 标识获取住院视图中哪部分信息，例如：diagnosis代表获取诊断信息
     * @param visitSn 就诊内部序列号
     * @param inpatientDate 某次就诊的某个住院日期
     * @param itemTypeCode 体格检查项目类型编码
     * @return 住院视图三测单内容
     */
    @Arguments({ "itemFlag", "visitSn", "inpatientDate", "itemTypeCode", "x"})
    public List<TimerAndInpatientDto> selectInpatientList(String itemFlag,
            BigDecimal visitSn, String inpatientDate, String itemTypeCode, int x);

    // $Author :jin_peng
    // $Date : 2012/11/13 15:16$
    // [BUG]0011225 MODIFY BEGIN
    /**
     * 根据相应患者的就诊时的就诊内部序列号和该次就诊的某个住院日期和体格检查项目编码获取住院视图三测单部分内容
     * @param itemFlag 标识获取住院视图中哪部分信息，例如：diagnosis代表获取诊断信息
     * @param visitSn 就诊内部序列号
     * @param itemUnit 项目结果单位
     * @param inpatientStartDate 某次就诊的某段住院日期开始日期
     * @param inpatientEndDate 某次就诊的某段住院日期结束日期
     * @param itemTypeCode 体格检查项目类型编码
     * @param numFlag 是否需为数字标识
     * @return 住院视图三测单内容
     */
    @Arguments({ "itemFlag", "visitSn", "itemUnit", "inpatientStartDate",
            "inpatientEndDate", "itemTypeCode" , "numFlag", "orgCode"})
    public List<TimerAndInpatientDto> selectInpatientList(String itemFlag,
            BigDecimal visitSn, String itemUnit, String inpatientStartDate,
            String inpatientEndDate, String itemTypeCode , String numFlag, String orgCode);

    /**
     * 根据相应患者的就诊时的就诊内部序列号和该次就诊的某个住院日期和体格检查项目编码获取住院视图三测单部分内容(血压信息)
     * @param itemFlag 标识获取住院视图中哪部分信息，例如：diagnosis代表获取诊断信息
     * @param visitSn 就诊内部序列号
     * @param itemUnit 项目结果单位
     * @param inpatientStartDate 某次就诊的某段住院日期开始日期
     * @param inpatientEndDate 某次就诊的某段住院日期结束日期
     * @param itemTypeCode 体格检查项目类型编码
     * @param bpFlag 体格检查项目编码
     * @param numFlag 是否需为数字标识
     * @return 住院视图三测单内容
     */
    @Arguments({ "itemFlag", "visitSn", "itemUnit", "inpatientStartDate",
            "inpatientEndDate", "itemTypeCode", "bpFlag" , "numFlag", "orgCode"})
    public List<TimerAndInpatientDto> selectInpatientList(String itemFlag,
            BigDecimal visitSn, String itemUnit, String inpatientStartDate,
            String inpatientEndDate, String itemTypeCode, String bpFlag , String numFlag, String orgCode);

    // [BUG]0011225 MODIFY END

    // $Author :jin_peng
    // $Date : 2012/11/06 13:48$
    // [BUG]0011026 MODIFY BEGIN
    // $Author :jin_peng
    // $Date : 2012/09/28 13:48$
    // [BUG]0010132 MODIFY BEGIN
    /**
     * 查寻住院视图总记录数
     * @param patientSn 患者内部序列号
     * @param inpatientType 住院类型
     * @param inpatientStartDate 某次住院的住院开始日期
     * @param inpatientEndDate 某次住院的住院结束时间
     * @param dischargeDate 出院日期
     * @param exitInpatient 退院标识
     * @return 住院视图总记录数
     */
    @Arguments({ "patientSn", "inpatientType", "inpatientStartDate",
            "inpatientEndDate", "dischargeDate", "exitInpatient", "orgCode"})
    public int selectTotalCnt(String patientSn, String inpatientType,
            String inpatientStartDate, String inpatientEndDate,
            String dischargeDate, String exitInpatient, String orgCode);

    // [BUG]0010132 MODIFY END

    // $Author :jin_peng
    // $Date : 2012/09/28 13:48$
    // [BUG]0010132 ADD BEGIN
    /**
     * 查找所有的住院视图起始记录号
     * @param patientSn 患者内部序列号
     * @param inpatientType 住院类型
     * @param inpatientStartDate 某次住院的住院开始日期
     * @param inpatientEndDate 某次住院的住院结束时间
     * @param visitSn 就诊内部序列号
     * @param exitInpatient 退院标识
     * @return 记录开始显示号
     */
    @Arguments({ "patientSn", "inpatientType", "inpatientStartDate",
            "inpatientEndDate", "visitSn", "exitInpatient", "orgCode"})
    public int selectStartNumber(String patientSn, String inpatientType,
            String inpatientStartDate, String inpatientEndDate, String visitSn,
            String exitInpatient, String orgCode);

    /**
     * 查寻住院视图住院次数
     * @param patientSn 患者内部序列号
     * @param inpatientType 住院类型
     * @param exitInpatient 退院标识
     * @return 住院视图住院次数
     */
    @Arguments({ "patientSn", "inpatientType", "exitInpatient", "orgCode"})
    public List<TimerAndInpatientDto> selectVisitTimes(String patientSn,
            String inpatientType, String exitInpatient, String orgCode);

    // [BUG]0010132 ADD END
    // [BUG]0011026 MODIFY END

    // $Author :jin_peng
    // $Date : 2012/10/10 18:28$
    // [BUG]0010239 ADD BEGIN
    /**
     * 根据查询条件查询相应药物长期医嘱
     * @param itemFlag 标识获取住院视图中哪部分信息，例如：diagnosis代表获取诊断信息
     * @param visitSn 就诊内部序列号
     * @param inpatientDate1 某次就诊的每页第1住院日期
     * @param inpatientDate2 某次就诊的每页第2住院日期
     * @param inpatientDate3 某次就诊的每页第3住院日期
     * @param inpatientDate4 某次就诊的每页第4住院日期
     * @param inpatientDate5 某次就诊的每页第5住院日期
     * @param inpatientDate6 某次就诊的每页第6住院日期
     * @param inpatientDate7 某次就诊的每页第7住院日期
     * @param cancelOrderStatus 医嘱执行状态
     * @param longTermFlag 长期医嘱标识
     * @param displayCount 具体内容部分每Td中内容条数
     * @param userSn 用户sn，根据sn排除其在hide_drug表的信息
     * @return 住院视图列表具体信息对象集合
     */
    @Arguments({ "itemFlag", "visitSn", "inpatientDate1", "inpatientDate2",
            "inpatientDate3", "inpatientDate4", "inpatientDate5",
            "inpatientDate6", "inpatientDate7", "cancelOrderStatus",
            "longTermFlag", "userSn", "displayCount", "orgCode"})
    public List<TimerAndInpatientDto> selectInpatientList(String itemFlag,
            BigDecimal visitSn, String inpatientDate1, String inpatientDate2,
            String inpatientDate3, String inpatientDate4,
            String inpatientDate5, String inpatientDate6,
            String inpatientDate7, String cancelOrderStatus,
            String longTermFlag, String userSn, int displayCount, String orgCode);

    /**
     * 根据查询条件查询相应药物长期医嘱按给定时间匹配总条数
     * @param itemFlag 标识获取住院视图中哪部分信息，例如：diagnosis代表获取诊断信息
     * @param visitSn 就诊内部序列号
     * @param inpatientDate1 某次就诊的每页第1住院日期
     * @param inpatientDate2 某次就诊的每页第2住院日期
     * @param inpatientDate3 某次就诊的每页第3住院日期
     * @param inpatientDate4 某次就诊的每页第4住院日期
     * @param inpatientDate5 某次就诊的每页第5住院日期
     * @param inpatientDate6 某次就诊的每页第6住院日期
     * @param inpatientDate7 某次就诊的每页第7住院日期
     * @param cancelOrderStatus 医嘱执行状态
     * @param longTermFlag 长期医嘱标识
     * @param userSn 用户sn，根据sn排除其在hide_drug表的信息
     * @return 住院视图列表具体信息对象集合
     */
    @Arguments({ "itemFlag", "visitSn", "inpatientDate1", "inpatientDate2",
            "inpatientDate3", "inpatientDate4", "inpatientDate5",
            "inpatientDate6", "inpatientDate7", "cancelOrderStatus",
            "longTermFlag" ,"userSn", "orgCode"})
    public List<BigDecimal> selectInpatientList(String itemFlag,
            BigDecimal visitSn, String inpatientDate1, String inpatientDate2,
            String inpatientDate3, String inpatientDate4,
            String inpatientDate5, String inpatientDate6,
            String inpatientDate7, String cancelOrderStatus, String longTermFlag
            , String userSn, String orgCode);

    // $Author:jin_peng
    // $Date:2012/11/12 15:09
    // $[BUG]0011225 MODIFY BEGIN
    /**
     * 根据查询条件查询相应药物临时医嘱
     * @param itemFlag 标识获取住院视图中哪部分信息，例如：diagnosis代表获取诊断信息
     * @param visitSn 就诊内部序列号
     * @param inpatientStartDate 某次就诊的某段住院日期开始日期
     * @param inpatientEndDate 某次就诊的某段住院日期结束日期
     * @param cancelOrderStatus 医嘱执行状态
     * @param tempFlag 临时医嘱标识
     * @param methodFlag 方法区分标识
     * @param displayCount 具体内容部分每Td中内容条数
     * @param userSn 用户sn，根据sn排除其在hide_drug表的信息
     * @return 住院视图列表具体信息对象集合
     */
    @Arguments({ "itemFlag", "visitSn", "inpatientStartDate",
            "inpatientEndDate", "cancelOrderStatus", "tempFlag", "userSn", "displayCount", "orgCode"})
    public List<TimerAndInpatientDto> selectInpatientList(String itemFlag,
            BigDecimal visitSn, String inpatientStartDate,
            String inpatientEndDate, String cancelOrderStatus, String tempFlag,
            String userSn, int displayCount, String orgCode, int methodFlag);

    // $[BUG]0011225 MODIFY END
    // [BUG]0010239 ADD END

    // $Author:jin_peng
    // $Date:2012/12/25 16:49
    // $[BUG]0012698 ADD BEGIN
    /**
     * 查询住院视图某天具体业务内容
     * @param itemFlag 业务标识
     * @param visitSn 就诊内部序列号
     * @param inpatientDate 住院日期
     * @return 住院视图某天具体业务内容
     */
    @Arguments({ "itemFlag", "visitSn", "inpatientDate" })
    public List<TimerAndInpatientDto> selectInpatientSpecificallyList(
            String itemFlag, BigDecimal visitSn, String inpatientDate);

    @Arguments({ "itemFlag", "visitSn", "inpatientDate" ,"longTermFlag","cancelOrderStatus", "userSn"})
    public List<TimerAndInpatientDto> selectInpatientSpecificallyList(
            String itemFlag, BigDecimal visitSn, String inpatientDate,String longTermFlag,String cancelOrderStatus
            ,String userSn);

    
    // $[BUG]0012698 ADD END
    //根据病人LID，就诊号查询入科转科表中，最近一条记录
    @Arguments({ "patientlid", "visitSn"})
    public List<TransferHistory> selectTransferHistoryList(String patientlid, BigDecimal visitSn);
}
