package com.founder.cdr.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.founder.cdr.dao.InpatientDao;
import com.founder.cdr.dao.PrintDao;
import com.founder.cdr.dto.TimerAndInpatientDto;
import com.founder.cdr.dto.VisitDateDeptDto;
import com.founder.cdr.service.PrintService;

@Component
public class PrintServiceImpl implements PrintService {
	@Autowired
	private PrintDao printDao;
    @Autowired
    private InpatientDao inpatientDao;
	@Override
	@Transactional
	public List<VisitDateDeptDto> selectPrintVisitDate(String sysDate,String patientSn,String visitTypeCode) {
//		return getActuallyVisitDate(printDao.selectPrintVisitDate(patientSn));
		return printDao.selectPrintVisitDate(sysDate, patientSn, visitTypeCode);
	}

    /**
     * 查寻住院视图住院次数
     * @param patientSn 患者内部序列号
     * @param inpatientType 住院类型
     * @param exitInpatient 退院标识
     * @return 住院视图住院次数
     */
    @Transactional
    public List<TimerAndInpatientDto> selectVisitTimes(String patientSn,
            String inpatientType, String exitInpatient, String orgCode)
    {
        return inpatientDao.selectVisitTimes(patientSn, inpatientType,
                exitInpatient, orgCode);
    }
}
