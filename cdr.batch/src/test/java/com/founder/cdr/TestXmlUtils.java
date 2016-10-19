package com.founder.cdr;

import com.yly.cdr.entity.Patient;
import com.yly.cdr.entity.TestSqlite;
import com.yly.cdr.hl7.dto.pocdin000040uv02.ReportDoctor;
//import com.yly.cdr.util.JaxbXmlUtil;
import com.yly.cdr.util.XmlUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.CountDownLatch;

/**
 * 类描述
 * Created by on 2016/10/3.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:beanRefContext.xml")
public class TestXmlUtils {
    @Test
    public void test() throws Exception {
        Patient patient = new Patient();
        patient.setPatientName("张三");
        patient.setPatientSn(new BigDecimal("00000001"));
        patient.setBirthDate(Calendar.getInstance().getTime());
        patient.setGenderName("男");

        String[] arr ={"patientEid"};
        System.out.println(XmlUtils.callCreateByDto(patient,"pa",arr));
//        System.out.println(JaxbXmlUtil.beanToXml(patient));
        System.out.println("****************************************************************");
            String[] arr2 = {"patientEid","marriageStatus"};
        System.out.println(XmlUtils.callCreateCommonByDto(patient,"test",arr2));
    }
}
