/**
 * [FUN]V03-001诊断DAO
 * 
* @version 1.0, 2011/10/28  10:22:00

 * @author  liujingyang * @since 1.0
*/
package com.yly.cdr.dao;

import java.util.List;

import com.founder.fasf.core.util.daohelper.annotation.Arguments;
import com.founder.fasf.web.paging.PagingContext;
import com.yly.cdr.entity.CodeDrug;
import com.yly.cdr.entity.Prescription;

public interface PrescriptionDao
{

    @Arguments({ "patientEid", "prescriptionTime", "prescriptionNo",
            "prescriptionClass", "prescriptionType", "prescriptionDoctorId",
            "reviewPerson", "reviewTime", "visitSn", "visitTime" })
    public boolean insertPrescription(String patientEid,
            String prescriptionTime, String prescriptionNo,
            String prescriptionClass, String prescriptionType,
            String prescriptionDoctorId, String reviewPerson,
            String reviewTime, String visitSn, String visitTime,
            PagingContext pagingContext);

    @Arguments({ "drugCode", "serialNo" })
    public CodeDrug selectCodeDrug(String drugCode, String serialNo);

    // $Author :chang_xuewen
    // $Date : 2013/12/04 11:00$
    // [BUG]0040271 MODIFY BEGIN
    /**
    * 根据就诊内部序列号查找处方集合
    * @param visitSn 就诊内部序列号
    * @param dataSource 数据来源（来自处方消息还是诊疗消息）
    * @return 处方集合
    */
    @Arguments({ "visitSn", "dataSource", "orgCode"})
    public List<Prescription> selectPrescriptionsByVisitSn(String visitSn,
            String dataSource, String orgCode);
    // [BUG]0040271 MODIFY END

    /*
     * Author: yu_yzh
     * Date: 2015/11/06 10:13
     * 根据处方号查询处方 ADD BEGIN
     * */
    /**
     * 根据处方号查询处方
     * @param prescriptionNo 处方号
     * @return 处方
     * */
    @Arguments({"prescriptionNo", "visitSn", "patientSn"})
    public Prescription selectPrescriptionsByPrescriptionNo(String prescriptionNo,
            String visitSn, String patientSn);
    // 根据处方号查询处方 ADD END
}
