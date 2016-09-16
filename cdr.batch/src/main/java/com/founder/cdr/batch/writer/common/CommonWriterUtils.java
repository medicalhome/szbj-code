package com.founder.cdr.batch.writer.common;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.founder.cdr.core.Constants;
import com.founder.cdr.entity.PatientDoctor;
import com.founder.cdr.service.CommonService;
import com.founder.cdr.util.StringUtils;

@Component
public class CommonWriterUtils
{
    @Autowired
    private CommonService commonService;

    public PatientDoctor getPatientDoctor(BigDecimal visitSn,
            BigDecimal patientSn, String doctorCode, String doctorName,
            Date sysDate)
    {
        Map<String, Object> condition = new HashMap<String, Object>();
        condition.put("visitSn", visitSn);
        condition.put("patientSn", patientSn);
        condition.put("doctorCode", doctorCode);
        condition.put("deleteFlag", Constants.NO_DELETE_FLAG);
        List<Object> patientDoctorList = commonService.selectByCondition(
                PatientDoctor.class, condition);
        // 数据库中存在相应的记录信息，则不新增
        if (patientDoctorList != null && patientDoctorList.size() > 0)
        {
            return null;
        }
        PatientDoctor patientDoctor = new PatientDoctor();
        patientDoctor.setVisitSn(visitSn);
        patientDoctor.setPatientSn(patientSn);
        patientDoctor.setDoctorCode(doctorCode);
        patientDoctor.setDoctorName(doctorName);
        patientDoctor.setCreateTime(sysDate);
        patientDoctor.setDeleteFlag(Constants.NO_DELETE_FLAG);
        patientDoctor.setUpdateTime(sysDate);
        patientDoctor.setUpdateCount(Constants.INSERT_UPDATE_COUNT);
        return patientDoctor;
    }

    // $Author:jin_peng
    // $Date:2013/02/06 13:38
    // [BUG]0013909 ADD BEGIN
    /**
     * 判断某医生被添加是否已处理过
     * @param filterList 标识唯一医生信息集合
     * @param visitSn 就诊内部序列号
     * @param patientSn 患者内部序列号
     * @param doctorCode 医生code
     * @return 是 --已处理过该医生， 否 --未处理过该医生
     */
    public boolean isExistsPatientDoctor(List<String> filterList,
            BigDecimal visitSn, BigDecimal patientSn, String doctorCode)
    {
        boolean isExists = false;

        // 把标识该医生唯一信息拼接成一个内容便于以后比较
        String patientDoctorFlag = StringUtils.BigDecimalToStr(visitSn)
            + StringUtils.BigDecimalToStr(patientSn) + doctorCode;

        // 如果该医生存在于标识唯一医生信息集合中则返回true，则不再进行以后操作，否则做相关处理并向该标识集合中添加该医生信息
        if (filterList.contains(patientDoctorFlag))
        {
            isExists = true;
        }
        else
        {
            filterList.add(patientDoctorFlag);
        }

        return isExists;
    }

    // [BUG]0013909 ADD END
}
