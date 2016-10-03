package com.yly.cdr.util;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.yly.cdr.hl7.dto.Diagnosis;
import com.yly.cdr.hl7.dto.MedicalVisit;
import com.yly.cdr.hl7.dto.MedicationOrderDto;
import com.yly.cdr.hl7.dto.poorin200901uv01.GroupPOORIN200901UV01;
import com.yly.cdr.hl7.dto.poorin200901uv01.POORIN200901UV01;
import com.yly.cdr.hl7.dto.porxin010370uv01.GroupPORXIN010370UV01;
import com.yly.cdr.hl7.dto.porxin010370uv01.PORXIN010370UV01;
import com.yly.cdr.hl7.dto.prpain201301uv02.GroupPRPAIN201301UV02;
import com.yly.cdr.hl7.dto.prpain201301uv02.PRPAIN201301UV02;
import com.yly.cdr.util.ValidatorUtils;

/**
 * dto校验测试类 
 * @author wen_ruichao
 */
public class ValidatorUtilsTest
{

//    @Test
    public void testSingleNotEmpty()
    {
         PRPAIN201301UV02 prpain201301uv02 = new PRPAIN201301UV02();
         assertEquals(false, ValidatorUtils.validate(prpain201301uv02, GroupPRPAIN201301UV02.class, null));
        
         prpain201301uv02.setPatientName("");
         assertEquals(false, ValidatorUtils.validate(prpain201301uv02, GroupPRPAIN201301UV02.class, null));
    }
    
//    @Test
    public void testListNotEmpty()
    {
        PORXIN010370UV01 porxin010370uv01 = new PORXIN010370UV01();

        List<MedicationOrderDto> medicineOrders = new ArrayList<MedicationOrderDto>();

        MedicationOrderDto medicineOrder1 = new MedicationOrderDto();
        // medicineOrder1.setPriorityNumber("1");
        medicineOrders.add(medicineOrder1);

        MedicationOrderDto medicineOrder2 = new MedicationOrderDto();
        // medicineOrder2.setPriorityNumber("2");
        medicineOrders.add(medicineOrder2);
        // porxin010370uv01.setMedicineOrder(medicineOrders);
        assertEquals(false, ValidatorUtils.validate(porxin010370uv01, GroupPORXIN010370UV01.class, null));
    }
    
    @Test
    public void testNotEmptyWithGroups()
    {
        // 检验申请
        POORIN200901UV01 poorin200901uv01 = new POORIN200901UV01();
        List<Diagnosis> diagnosises2 = new ArrayList<Diagnosis>();

        Diagnosis diagnosis2 = new Diagnosis();
        diagnosis2.setDiagnosisType("2");
//        diagnosis2.setDiagnosisDate("2012-02-02");
        diagnosis2.setDiagnosisNo("2");
        diagnosis2.setDiagnosisName("test2");
        diagnosises2.add(diagnosis2);
        
        MedicalVisit medicalVisit = new MedicalVisit();
        medicalVisit.setDiagnosises(diagnosises2);
        
        List<MedicalVisit> medicalVisits = new ArrayList<MedicalVisit>();
        medicalVisits.add(medicalVisit);
        
        /** POORIN200901UV01消息变更以下dto不存在 guo_hongyan delete
        LabOrderBatch labOrderBatch = new LabOrderBatch();
        labOrderBatch.setVisitList(medicalVisits);
        
        List<LabOrderBatch> labOrders = new ArrayList<LabOrderBatch>();
        labOrders.add(labOrderBatch);
        
        poorin200901uv01.setLabOrders(labOrders);**/
        poorin200901uv01.setVisitList(medicalVisits);
        assertEquals(false, ValidatorUtils.validate(poorin200901uv01, GroupPOORIN200901UV01.class, null));
    }
}
