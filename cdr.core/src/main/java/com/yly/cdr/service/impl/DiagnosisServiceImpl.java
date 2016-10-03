/**
 * [FUN]V03-001诊断 service
 * 
* @version 1.0, 2011/10/28  11:00:00

 * @author  liujingyang * @since 1.0
*/
package com.yly.cdr.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import oracle.sql.ARRAY;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.founder.fasf.web.paging.PagingContext;
import com.yly.cdr.core.Constants;
import com.yly.cdr.dao.DiagnosisDao;
import com.yly.cdr.dto.DiagnosisDto;
import com.yly.cdr.dto.diagnosis.DiagnosisListPra;
import com.yly.cdr.dto.diagnosis.DiagnosisListSearchPra;
import com.yly.cdr.dto.visit.VisitDiagnosisDto;
import com.yly.cdr.entity.Diagnosis;
import com.yly.cdr.entity.MedicalVisit;
import com.yly.cdr.service.CommonService;
import com.yly.cdr.service.DiagnosisService;
import com.yly.cdr.util.StringUtils;

@Component
public class DiagnosisServiceImpl implements DiagnosisService
{
    @Autowired
    private DiagnosisDao diagnosisDao;

    @Autowired
    private CommonService commonService;

    @Transactional
    public List<Diagnosis> selectAllDiagnosisByConditions(String diagnosisSn,
            Date diagnosisDate, Date diagnosisDate1, String diagnosisType,
            String diagnosticDept, String diagnosisDoctor, String diseaseName,
            PagingContext pagingContext)
    {
        List<Diagnosis> lpd = diagnosisDao.selectAllDiagnosisByConditions(
                diagnosisSn, diagnosisDate, diagnosisDate1, diagnosisType,
                diagnosticDept, diagnosisDoctor, diseaseName, pagingContext);
        return lpd;
    }

    @Transactional
    public List<DiagnosisDto> selectAllDiagnosis(String patientSn,
            String diagnosisType, String diseaseName, String diagnosticDept,
            String diagnosisDoctor, Date diagnosisDate, Date diagnosisDate1,
            String check1, String check2, String mainDiagnosisFlag,
            PagingContext pagingContext)
    {
        List<DiagnosisDto> lpd = diagnosisDao.selectAllDiagnosis(patientSn,
                diagnosisType, diseaseName, diagnosticDept, diagnosisDoctor,
                diagnosisDate, diagnosisDate1, check1, check2,
                mainDiagnosisFlag, pagingContext);
        return lpd;
    }

    @Transactional
    public DiagnosisDto selectOneDiagnosis(String diagnosisSn)
    {
        DiagnosisDto lpd = diagnosisDao.selectOneDiagnosis(diagnosisSn);
        return lpd;
    }

    /*
     * @Override public void updateDiagnosis(List<Object> diagnosisList,
     * List<Object> diagnosisResultList, MedicalVisit medicalVisit) { for (int i
     * = 0; i < diagnosisList.size(); i++) {
     * commonService.delete(diagnosisList.get(i)); } // 就诊表更新
     * commonService.update(medicalVisit);
     * 
     * // 诊断表新增 commonService.saveList(diagnosisResultList); }
     */

    @Override
    @Transactional
    public DiagnosisDto[] selectDiagnosisList(String patientSn,
            DiagnosisListSearchPra diagnosisListSearchPra,
            PagingContext pagingContext)
    {
        DiagnosisDto[] diagnosisResult = diagnosisDao.selectDiagnosisListSearchOb(
                patientSn, diagnosisListSearchPra, pagingContext);

        return diagnosisResult;
    }

    @Override
    @Transactional
    public void saveList(List<Object> diagnosisList, MedicalVisit medicalVisit)
    {
        // 就诊表更新
        commonService.update(medicalVisit);
        List<String> lstType = new ArrayList<String>();
        // $Author :wu_jianfeng
        // $Date : 2012/9/25 15:00$
        // [BUG]0009863 MODIFY BEGIN
        if (diagnosisList != null && !diagnosisList.isEmpty())
        {
            for (Object obj : diagnosisList)
            {
            	Diagnosis dg = (Diagnosis)obj;
            	if(!lstType.contains(dg.getDiagnosisTypeCode()))
            	lstType.add(dg.getDiagnosisTypeCode());
            }
        }
        //取消删除操作，港大只做新增操作
//        deleteDiagnosis(medicalVisit.getVisitSn().toString(),(String[])lstType.toArray(new String[0]));
        // [BUG]0009863 MODIFY END

        if (diagnosisList != null && !diagnosisList.isEmpty())
        {
            for (Object entity : diagnosisList)
            {
                commonService.save(entity);
            }
        }
    }

    // $Author :wu_jianfeng
    // $Date : 2012/9/25 15:00$
    // [BUG]0009863 ADD BEGIN
    @Override
    @Transactional
    public void deleteDiagnosis(String visitSn, String[] diagnosisTypeCode)
    {
        diagnosisDao.deleteDiagnosis(visitSn,diagnosisTypeCode);
    }

    // [BUG]0009863 ADD END

    @Override
    @Transactional
    public List<DiagnosisDto> selectCiDiagnosis(String visitSn,
            String diagnosisTypeCode)
    {
        List<DiagnosisDto> lpd = diagnosisDao.selectCiDiagnosis(visitSn,
                diagnosisTypeCode);
        return lpd;
    }

    @Override
    @Transactional
    public List<Diagnosis> selectDiagnosisByVisitSn(String visitSn)
    {
        List<Diagnosis> List_dia = new ArrayList<Diagnosis>();
        if(!StringUtils.isEmpty(visitSn))
        {
            Map<String, Object> condition = new HashMap<String, Object>();
            condition.put("visitSn", visitSn);
            condition.put("deleteFlag", Constants.NO_DELETE_FLAG);
            List<Object> tmpList = commonService.selectByCondition(Diagnosis.class,
                    condition);
            for (Object object : tmpList)
            {
                List_dia.add((Diagnosis) object);
            }            
        }
        return List_dia;
    }

    @Override
    @Transactional
    public Map<String, String> selectVisitDiagnosis(List<String> visitSns)
    {
        Map<String, String> vdMap = new HashMap<String, String>();
        DiagnosisListPra diagnosisListPra = new DiagnosisListPra();
        diagnosisListPra.setMainDiagnosisFlag(Constants.MAIN_DIAGNOSIS);
        diagnosisListPra.setVisitSns(visitSns);
        List<DiagnosisDto> mainDiagnosisList = diagnosisDao.selectDiagnosises(diagnosisListPra);
        for (DiagnosisDto diagnosis : mainDiagnosisList)
        {
            String d_visitSn = diagnosis.getVisitSn().toString();
            String diseaseName = diagnosis.getDiseaseName();
            if (!StringUtils.isEmpty(diseaseName))
            {
                if (vdMap.containsKey(d_visitSn))
                {
                    diseaseName = vdMap.get(d_visitSn) + "," + diseaseName;
                    vdMap.put(d_visitSn, diseaseName);
                }
                else
                {
                    vdMap.put(d_visitSn, diseaseName);
                }
            }
        }
        return vdMap;
    }

	@Override
	public List<VisitDiagnosisDto> selectMainDiagnosisByPatientSn(String patientSn) {
		
		return diagnosisDao.selectVisitMainDiagnosisDisease(patientSn);
	}

}
