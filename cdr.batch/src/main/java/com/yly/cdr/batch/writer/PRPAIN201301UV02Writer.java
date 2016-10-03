package com.yly.cdr.batch.writer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.yly.cdr.hl7.dto.prpain201301uv02.PRPAIN201301UV02;
import com.yly.cdr.service.PatientService;

/**
 * 患者基本信息Writer 注意：这里需要根据业务，添加判断条件。如：
 * 1：如果当前消息是子消息，父消息如果存在，则保存子消息内容；如果父消息不存在，则不保存子消息内容。 2：如果是父消息，直接保存父消息内容。
 * 
 * @author wen_ruichao
 * @version 1.0
 */
@Component(value = "prpain201301uv02Writer")
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class PRPAIN201301UV02Writer implements BusinessWriter<PRPAIN201301UV02>
{

    private static final Logger logger = LoggerFactory.getLogger(PRPAIN201301UV02Writer.class);

    @Autowired
    private PatientService patientService;

    /**
     * 数据校验
     * @param baseDto
     */
    public boolean validate(PRPAIN201301UV02 prpain201301uv02)
    {
        return true;
    }

    public boolean checkDependency(PRPAIN201301UV02 prpain201301uv02,boolean flag)
    {
        return true;
    }

    /**
     * 
     * @param baseDto
     */
    @Transactional
    public void saveOrUpdate(PRPAIN201301UV02 prpain201301uv02)
    {
        logger.debug("save()");
        // 保存患者基本信息
        // Patient patient = new Patient();
        // patient.setPatientName(prpain201301uv02.getPatientName());
        // patientService.savePatient(patient);
        // logger.debug("保存患者基本信息: \n{}", patient);
        //
        // BigDecimal patientSn = new BigDecimal(13);

        // 保存患者地址信息(多个)
        // List<PatientAddress> patientAddresses = new
        // ArrayList<PatientAddress>();
        // PatientAddress patientAddress = new PatientAddress();
        // patientAddress.setPatientSn(patientSn);
        // patientAddress.setCreateTime(new Date());
        // patientAddress.setUpdateTime(new Date());
        // patientAddress.setUpdateCount(new BigDecimal(1));
        // patientAddress.setDeleteFlag("0");
        // patientAddresses.add(patientAddress);
        //
        // PatientAddress patientAddress2 = new PatientAddress();
        // patientAddress2.setPatientSn(patientSn);
        // patientAddress2.setCreateTime(new Date());
        // patientAddress2.setUpdateTime(new Date());
        // patientAddress2.setUpdateCount(new BigDecimal(1));
        // patientAddress2.setDeleteFlag("0");
        // patientAddresses.add(patientAddress2);
        //
        // patientService.savePatientAddreses(patientAddresses.toArray(new
        // PatientAddress[0]));
        // logger.debug("保存患者地址信息: \n{}", patientAddress);

        // 保存患者联系人信息(多个)
        // PatientContact patientContact = new PatientContact();
        // patientContact.setPatientSn(patientSn);
        // patientService.savePatientContact(patientContacts.toArray(new
        // PatientContact[0]));
        // logger.debug("保存患者联系人信息: \n{}", patientContact);

        // 保存患者证件信息(多个)
        // PatientCredential patientCredential = new PatientCredential();
        // patientCredential.setPatientSn(patientSn);
        // patientService.savePatientCredential(patientCredentials.toArray(new
        // PatientCredential[0]));
        // logger.debug("保存患者证件信息: \n{}", patientCredential);

        // 保存患者医保信息
        // SocialInsurance socialInsurance = new SocialInsurance();
        // socialInsurance.setPatientSn(patientSn);
        // patientService.saveSocialInsurance(socialInsurance);
        // logger.debug("保存患者医保信息: \n{}", socialInsurance);

        // 保存患者危险性信息(多个)
        // RiskInformation riskInformation = new RiskInformation();
        // riskInformation.setPatientSn(patientSn);
        // patientService.saveRiskInformation(riskInformations.toArray(new
        // RiskInformation[0]));
        // logger.debug("保存患者危险性信息: \n{}", riskInformation);
    }
}
