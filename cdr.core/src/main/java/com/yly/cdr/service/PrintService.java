/**
 * Copryright
 */
package com.yly.cdr.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.yly.cdr.dto.TimerAndInpatientDto;
import com.yly.cdr.dto.VisitDateDeptDto;



/**
 * @version 1.0, 2014/04/09  17:38:00
 * @author li_shenggen
 * @since 1.0
 */
public interface PrintService
{

    @Transactional
    List<VisitDateDeptDto> selectPrintVisitDate(String sysDate, String patientSn,String visitTypeCode);
    /**
     * 查寻住院视图住院次数
     * @param patientSn 患者内部序列号
     * @param inpatientType 住院类型
     * @param exitInpatient 退院标识
     * @return 住院视图住院次数
     */
    @Transactional
    public List<TimerAndInpatientDto> selectVisitTimes(String patientSn,
            String inpatientType, String exitInpatient, String orgCode);

}
