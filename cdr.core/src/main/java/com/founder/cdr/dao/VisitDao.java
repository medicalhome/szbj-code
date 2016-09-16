package com.founder.cdr.dao;

import java.math.BigDecimal;
import java.util.List;

import com.founder.cdr.dto.KeyValueDto;
import com.founder.cdr.dto.VisitDateDto;
import com.founder.cdr.dto.VisitDetailDto;
import com.founder.cdr.dto.VisitIndexListDto;
import com.founder.cdr.dto.VisitListDto;
import com.founder.cdr.dto.exam.WithdrawRecordDto;
import com.founder.cdr.dto.visit.LastVisitSearchPra;
import com.founder.cdr.dto.visit.VisitIndexListSearchPra;
import com.founder.cdr.dto.visit.VisitListSearchPra;
import com.founder.cdr.entity.MedicalVisit;
import com.founder.cdr.entity.TransferHistory;
import com.founder.cdr.entity.UserFeedback;
import com.founder.fasf.core.util.daohelper.annotation.Arguments;
import com.founder.fasf.web.paging.PagingContext;

public interface VisitDao
{
    /**
     * 
     * [FUN]V05-101就诊列表
     * @version 1.0, 2012/03/06  
     * @author 郭红燕
     * @since 1.0
     * 
     */
    // $Author:wu_jianfeng
    // $Date : 2012/10/25 14:10
    // $[BUG]0010778 MODIFY BEGIN
    @Arguments({ "visitListSearchPra" })
    public VisitListDto[] selectVisitList(
            VisitListSearchPra visitListSearchPra, PagingContext pagingContext);

    // $[BUG]0010778 MODIFY END

    /**
     * 
     * [FUN]V05-101就诊列表
     * @version 1.0, 2012/03/06  
     * @author 郭红燕
     * @since 1.0
     * 普通搜索
     * 
     */
    @Arguments({ "patientSn", "visitDateFromStr", "visitDateToStr",
            "visitType", "visitDept", "dischargeDateFromStr",
            "dischargeDateToStr", "visitDoctorName", "registrationClass" })
    public VisitListDto[] selectVisitListSearch(String patientSn,
            String visitDateFromStr, String visitDateToStr, String visitType,
            String visitDept, String dischargeDateFromStr,
            String dischargeDateToStr, String visitDoctorName,
            String registrationClass, PagingContext pagingContext);

    /**
     * 
     * [FUN]V05-101就诊列表
     * @version 1.0, 2012/03/12  
     * @author 郭红燕
     * @since 1.0
     * 就诊详细 
     * 
     */
    @Arguments({ "visitSn" })
    public VisitDetailDto selectVisitDetail(String visitSn);

    /**
     * V05-001:临床信息集成视图
     * 根据患者序列号，得到患者最后的就诊记录
     * @author wu_jianfeng
     * @param patientSn 患者序列号
     * @return 患者最后就诊记录
     */
    @Arguments({ "patientSn" })
    public MedicalVisit getLastMedicalVisit(String patientSn);

    /**
     * V05-001:临床信息集成视图
     * 根据患者序列号，得到患者的就诊科室信息
     * @author wu_jianfeng
     * @param patientSn 患者序列号
     * @return 患者就诊科室信息
     */
    @Arguments({ "patientSn" })
    public List<String> getMedicalVisitDepartments(String patientSn,
            PagingContext pagingContext);

    @Arguments({ "patientSn", "visitListSearchPra" })
    public VisitListDto[] selectVisitListSearchOb(String patientSn,
            VisitListSearchPra visitListSearchPra, PagingContext pagingContext);

    @Arguments({ "visitSn" })
    public WithdrawRecordDto[] selectWithdrawRecord(String visitSn);

    // $Author:wu_jianfeng
    // $Date : 2012/09/28 14:10
    // $[BUG]0010082 ADD BEGIN
    // 门诊视图
    // $Author:wu_jianfeng
    // $Date : 2012/10/25 14:10
    // $[BUG]0010778 MODIFY BEGIN
    @Arguments({ "visitListSearchPra" })
    public List<VisitDateDto> selectOutpatientVisitDates(
            VisitListSearchPra visitListSearchPra);

    // $[BUG]0010778 MODIFY END

    // $[BUG]0010082 ADD END

    // $Author:wu_jianfeng
    // $Date : 2012/10/8 14:10
    // $[BUG]0010107 ADD BEGIN
    // 就诊索引视图
    @Arguments({ "patientSn", "visitListSearchPra" })
    public List<VisitIndexListDto> selectVisitIndexList(String patientSn,
            VisitIndexListSearchPra visitListSearchPra);

    // $[BUG]0010107 ADD END

    // $Author:wu_jianfeng
    // $Date : 2012/10/8 14:10
    // $[BUG]0010244 ADD BEGIN
    @Arguments({ "searchPra" })
    public List<MedicalVisit> selectLastMedicalVisits(
            LastVisitSearchPra searchPra);

    // $[BUG]0010244 ADD END

    // $Author:wei_peng
    // $Date:2012/10/18 10:42
    // $[BUG]0010276 ADD BEGIN
    @Arguments({ "userId", "parentFeedbackSn" })
    public List<UserFeedback> selectUserFeedbacks(String userId,
            String parentFeedbackSn);

    // $[BUG]0010276 ADD END

    // $Author:wu_jianfeng
    // $Date : 2012/11/15 14:10
    // $[BUG]0011399 ADD BEGIN
    @Arguments({ "visitSns" })
    public List<MedicalVisit> selectInpatientVisitDoctors(List<String> visitSns);

    // $[BUG]0011399 ADD END

    // $Author:wu_jianfeng
    // $Date : 2012/11/20 14:10
    // $[BUG]0011694 ADD BEGIN
    @Arguments({ "patientSn", "visitTypeInpatientCode", "sysdate" })
    public List<KeyValueDto> selectVisitDepts(String patientSn,
            List visitTypeInpatientCode, String sysdate);
    // $[BUG]0011694 ADD END
    
    // Author:jia_yanqing
    // Date : 2012/12/25 16:21
    // [BUG]0012701 ADD BEGIN
    @Arguments({ "patientSn","sysDate","visitTypeCode"})
    public int getOutPatientCount(String patientSn,String sysDate,String visitTypeCode);
    @Arguments({ "patientSn","visitTypeCode"})
    public int getInPatientCount(String patientSn,String visitTypeCode);
    // [BUG]0012701 ADD END
    
    // Author: jia_yanqing
    // Date: 2013/3/6 14:11$
    // [BUG]0014327 MODIFY BEGIN
    @Arguments({ "patientSn", "orgCode"})
    public int selectMinVisitTimes(BigDecimal patientSn, String orgCode); 
    // [BUG]0014327 MODIFY END
    
    // Author: chang_xuewen
    // Date: 2013/10/22 10:20$
    // [BUG]0038313 ADD BEGIN
    @Arguments({ "patientSn","visitSn"})
    public List<TransferHistory> selectTransferHistory(String patientSn, String visitSn); 
    // [BUG]0038313 ADD END
}
