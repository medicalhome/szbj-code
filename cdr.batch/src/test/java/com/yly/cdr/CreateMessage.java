package com.yly.cdr;

import ca.uhn.hl7v2.DefaultHapiContext;
import ca.uhn.hl7v2.HL7Exception;
import ca.uhn.hl7v2.HapiContext;
import ca.uhn.hl7v2.model.Message;
import ca.uhn.hl7v2.model.v24.group.ORG_O20_PATIENT;
import ca.uhn.hl7v2.model.v24.group.ORG_O20_RESPONSE;
import ca.uhn.hl7v2.model.v24.message.ORG_O20;
import ca.uhn.hl7v2.model.v24.segment.MSH;
import ca.uhn.hl7v2.model.v24.segment.PID;
import ca.uhn.hl7v2.parser.*;
import ca.uhn.hl7v2.util.Terser;
import com.founder.hie.rce.util.FileUtils;
import com.sun.org.apache.xerces.internal.impl.xs.opti.DefaultDocument;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.w3c.dom.Document;

import java.io.File;

/**
 * 类描述
 * Created by masong1 on 2016/10/6.
 */

public class CreateMessage {
    public static void main(String[] args) throws Exception{
        ORG_O20 org_o20 = new ORG_O20();

        //MSH消息段(Segment)
        MSH mshSegment = org_o20.getMSH();
        mshSegment.getFieldSeparator().setValue("|");
        mshSegment.getEncodingCharacters().setValue("^~\\&");
        mshSegment.getSendingApplication().getNamespaceID().setValue("NIS");
        mshSegment.getReceivingApplication().getNamespaceID().setValue("EAI");
        mshSegment.getDateTimeOfMessage().getTimeOfAnEvent().setValue("200701011539");
        mshSegment.getMessageType().getMessageType().setValue("ORG");
        mshSegment.getMessageType().getTriggerEvent().setValue("O20");
        mshSegment.getMessageControlID().setValue("38572804");
        mshSegment.getProcessingID().getProcessingID().setValue("P");
        mshSegment.getVersionID().getVersionID().setValue("2.4");
        mshSegment.getAcceptAcknowledgmentType().setValue("AL");
        mshSegment.getApplicationAcknowledgmentType().setValue("AL");
        mshSegment.getCountryCode().setValue("CHN");

        //MSH消息段(PID)
        ORG_O20_RESPONSE org_o20_response = org_o20.getRESPONSE();
        ORG_O20_PATIENT org_o20_patient = org_o20_response.getPATIENT();
        PID pid = org_o20_patient.getPID();
        pid.getPatientName(0).getGivenName().setValue("张五常");
        pid.getPatientIdentifierList(0).getID().setValue("0000028866^^^^patientNo~0000640656^^^^IDCard~H0423887201");


        HapiContext context = new DefaultHapiContext();
        Parser parser = context.getPipeParser();
        String encodedMessage = parser.encode(org_o20);
        System.out.println("Printing ER7 Encoded Message:");
        FileUtils.writeToFile(encodedMessage,new File("/a.txt"));
        System.out.println(encodedMessage);

                  /*
                   * Prints:
                   *
                   * MSH|^~\&|TestSendingSystem||||200701011539||ADT^A01^ADT A01||||123
                   * PID|||123456||Doe^John
                   */
        // Next, let's use the XML parser to encode as XML
        parser = context.getXMLParser();
        encodedMessage = parser.encode(org_o20);
        System.out.println("Printing XML Encoded Message:");
        System.out.println(encodedMessage);

        XMLParser xmlParser = new DefaultXMLParser();
        Document xmlMessage = new DefaultDocument();
        Message message = xmlParser.parseDocument(xmlMessage,"2.4");


//        Parser parser1 = new GenericParser();
//        System.out.println(parser1.encode(org_o20));
//        //HL7在应用程序中的传输格式
//        Parser parser = new PipeParser();
//        String encodedMessage = parser.doEncode(mshSegment, EncodingCharacters.getInstance(org_o20));
//        System.out.println("ER7格式:");
//        System.out.println(encodedMessage);
//
//        //XML格式
//        parser = new DefaultXMLParser();
//        encodedMessage = parser.encode(org_o20);
//        System.out.println("XML格式:");
//        System.out.println(encodedMessage);
    }

    @Test
    public void testSetSegmentRepetitions(){
        try {
            String m = "MSH|^~\\&|hl7Integration|hl7Integration|||||ADT^A01|||2.4|\r" +
                    "EVN|A01|20130617154644\r" +
//                    "PID|1|465 306 5961||407623|Wood^Patrick^^^MR||19700101|1||||||||||\r" +
                    "PV1|1||Location||||||||||||||||261938_6_201306171546|||||||||||||||||||||||||20130617134644|||||||||";
//Create the Terser
            PipeParser pipeParser = new PipeParser();
            Message message = pipeParser.parse(m);
            Terser terser = new Terser(message);
            terser.set("/.PID-1-1", "1");
            terser.set("/.PID-2-1", "465 306 5961");
            terser.set("/.PID-4-1", "407623");
            terser.set("/.PID-5-1", "Wood");
            terser.set("/.PID-5-2", "Patrick");
            terser.set("/.PID-5-5", "MR");
            terser.set("/.PID-7-1", "19700101");
            terser.set("/.PID-8-1", "1");
//Add first next of Kin
//NK1|1|Jones^Joe|Father||||||
            terser.set("/.NK1(1)-1-1", "1");
            terser.set("/.NK1(1)-2-1", "Jones");
            terser.set("/.NK1(1)-2-2", "Joe");
            terser.set("/.NK1(1)-3-1", "Father");
//Add Second next of kin
//NK1|2|Hall^Anna|Mother
            terser.set("/.NK1(2)-1-1", "2");
            terser.set("/.NK1(2)-2-1", "Hall");
            terser.set("/.NK1(2)-2-2", "Anna");
            terser.set("/.NK1(2)-3-1", "Mother");
            System.out.println(message.encode());
            FileUtils.writeToFile(message.encode(), new File("/b.txt"));
        } catch (HL7Exception e) {
            e.printStackTrace();
        }
    }
}
