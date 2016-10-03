package com.yly.hl7;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.time.DateUtils;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.yly.hl7.Hl7v3XmlProcessor;
import com.yly.hl7.dto.Diagnosis;
import com.yly.hl7.dto.POCDHD000040;
import com.yly.hl7.maphandler.FileXPathMapHandler;

public class CDABuilderTest
{
    private Hl7v3XmlProcessor<POCDHD000040> builder = null;
    private String templateDir = null;
    
    @BeforeClass
    public static void setUpBeforeClass() throws Exception
    {
    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception
    {
    }

    @Before
    public void setUp() throws Exception
    {
        builder = new Hl7v3XmlProcessor<POCDHD000040>();
        templateDir = "E:\\FIHICT\\SourceCode\\temp\\";
        builder.setTemplateDir(templateDir);
    }

    @After
    public void tearDown() throws Exception
    {
    }
    
    private void writeStringToFile(File file, String data, String encoding) throws IOException
    {
        if(!file.exists())
            file.createNewFile();
        
        FileOutputStream fos = new FileOutputStream(file);
        OutputStreamWriter osw = new OutputStreamWriter(fos, encoding);
        osw.write(data);
        osw.close();
        fos.close();
    }

    @Test
    public void createCDA() throws Exception
    {
        String template = "POCDHD00040";
        POCDHD000040 obj = new POCDHD000040();
        obj.setPatientEid("55521729-aa3e-4ff4-9525-5dd7612a98fa");
        obj.setInpatientNo("000032354");
        obj.setOutpatientNo("000000034323");
        obj.setPatientName("赵四");
        obj.setGenderCode("01");
        obj.setBirthDate(DateUtils.parseDate("1928/03/06", "yyyy/MM/dd"));
        obj.setReportDate(DateUtils.parseDate("2012/03/06", "yyyy/MM/dd"));
        obj.setReporter("00012");
        obj.setReporterName("万密斋");
        obj.setReviewDate(DateUtils.parseDate("2012/03/06", "yyyy/MM/dd"));
        obj.setReviewer("00032");
        obj.setReviewerName("李时珍");
        obj.setVisitType("03");
        obj.setVisitDate(DateUtils.parseDate("2012/03/05", "yyyy/MM/dd"));
        obj.setReportNo("00043243243");
        obj.setExamItemCode("001.03");
        obj.setExamItemName("头部CT");
        obj.setReportMemo("无异常");
        obj.setExamMethod("");
        obj.setExamMethodName("");
        obj.setExamSite("");
        obj.setExamSiteName("");
        obj.setExamDate(null);
        obj.setExamDoctor("");
        obj.setExamDoctorName("");
        obj.setExamDept("");
        obj.setExamDeptName("");
        obj.setRequestNo("0032");
        
        List<Diagnosis> diagnosises = new ArrayList<Diagnosis>();
        obj.setDiagnosises(diagnosises);
        
        Diagnosis diagnosis = null;
        diagnosis = new Diagnosis();
        diagnosis.setCode("I63.902");
        diagnosis.setName("脑梗死");
        diagnosis.setPriorityNumber("01");
        diagnosis.setEffectiveTime("20110219");
        diagnosis.setDiseaseCode("I63.902");
        diagnosis.setDiseaseName("脑梗死");
        diagnosises.add(diagnosis);

        diagnosis = new Diagnosis();
        diagnosis.setCode("E78.501");
        diagnosis.setName("高脂血症");
        diagnosis.setPriorityNumber("02");
        diagnosis.setEffectiveTime("20110219");
        diagnosis.setDiseaseCode("E78.501");
        diagnosis.setDiseaseName("高脂血症");
        diagnosises.add(diagnosis);

        diagnosis = new Diagnosis();
        diagnosis.setCode("R40.204");
        diagnosis.setName("意识障碍");
        diagnosis.setPriorityNumber("03");
        diagnosis.setEffectiveTime("20110219");
        diagnosis.setDiseaseCode("R40.204");
        diagnosis.setDiseaseName("意识障碍");
        diagnosises.add(diagnosis);

        FileXPathMapHandler mapHandler = new FileXPathMapHandler();
        mapHandler.setClassLoader(this.getClass().getClassLoader());
        StringWriter writer = new StringWriter();
        
        long start = System.currentTimeMillis();
        for(int i = 0; i < 10000; i ++)
            builder.create(template, obj, mapHandler, writer);
        
        long usedTime = System.currentTimeMillis() - start;
        System.out.println("总共用时：" + usedTime / 1000);
        
//        System.out.println(writer.toString());
        File file = new File(this.templateDir + "result_" + System.currentTimeMillis() + ".xml");
        writeStringToFile(file, writer.toString(), "UTF-8");
    }

    @Test
    public void parseDocument() throws Exception
    {
        POCDHD000040 obj = new POCDHD000040();
        String template = "POCDHD00040";
        File file = new File(builder.getTemplateDir() + template + ".xml");
        FileXPathMapHandler mapHandler = new FileXPathMapHandler();
        mapHandler.setClassLoader(this.getClass().getClassLoader());
        builder.setXPathMapHandler(mapHandler);
        builder.parse(file, obj);
        assertNotNull(obj.getPatientEid());
        assertEquals("刘小三", obj.getPatientName());
        List<Diagnosis> list = obj.getDiagnosises();
        assertEquals(1, list.size());
        Diagnosis diagnosis = list.get(0);
        assertEquals("12", diagnosis.getPriorityNumber());
//        assertEquals("", diagnosis.getCode());
//        assertEquals("", diagnosis.getName());
        assertEquals("233604007", diagnosis.getDiseaseCode());
        assertEquals("肺炎", diagnosis.getDiseaseName());
    }
}
