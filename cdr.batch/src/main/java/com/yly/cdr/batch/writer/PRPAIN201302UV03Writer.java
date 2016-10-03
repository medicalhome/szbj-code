/**
 * Copryright
 */
package com.yly.cdr.batch.writer;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.yly.cdr.batch.writer.prpain201302uv01.PRPAIN201302UV01CommonPart;
import com.yly.cdr.core.Constants;
import com.yly.cdr.entity.Patient;
import com.yly.cdr.entity.PatientAddress;
import com.yly.cdr.entity.PatientContact;
import com.yly.cdr.entity.PatientCredential;
import com.yly.cdr.entity.PatientCrossIndex;
import com.yly.cdr.hl7.dto.PatientDelCollection;
import com.yly.cdr.hl7.dto.prpain201302uv03.PRPAIN201302UV03;
import com.yly.cdr.service.CommonService;
import com.yly.cdr.service.PatientService;
import com.yly.cdr.util.DateUtils;

/**
 * 删除患者基本信息数据接入writer
 * @author jin_peng
 */
@Component(value = "prpain201302uv03Writer")
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class PRPAIN201302UV03Writer implements BusinessWriter<PRPAIN201302UV03>
{
    private static final Logger logger = LoggerFactory.getLogger(PRPAIN201302UV03Writer.class);

    // 系统当前时间
    private Date systemTime;

    // 消息中需要删除的患者对应的empiId集合
    private List<PatientDelCollection> patientEidList;

    // 需要逻辑删除的患者最佳记录对象集合
    private List<Patient> patientDeleteList;

    // 需要逻辑删除的患者对应联系人对象集合
    private List<PatientContact> patientContactDeleteList;

    // 需要逻辑删除的患者对应证件对象集合
    private List<PatientCredential> patientCredentialDeleteList;

    // 需要逻辑删除的患者对应地址对象集合
    private List<PatientAddress> patientAddressDeleteList;

    @Autowired
    private CommonService commonService;

    @Autowired
    private PatientService patientService;

    @Autowired
    private PRPAIN201302UV01CommonPart pcPart;

    /**
     * 设置初始化参数
     */
    public PRPAIN201302UV03Writer()
    {
        systemTime = DateUtils.getSystemTime();
    }

    /**
     * 根据场景字段非空校验
     */
    @Override
    public boolean validate(PRPAIN201302UV03 prpain201302uv03)
    {
    	return true;
    }

    /**
     * 关联消息验证
     */
    @Override
    public boolean checkDependency(PRPAIN201302UV03 prpain201302uv03,boolean flag)
    {
        String patientEid = null;
        List<Object> patientCrossIndexList = null;
        Patient patient = null;

        patientEidList = prpain201302uv03.getPatientEidList();

        for (PatientDelCollection patientDelCollection : patientEidList)
        {
            patientEid = patientDelCollection.getPatientEid();

            patient = pcPart.getPatient(patientEid, commonService);

            if (patient == null)
            {
                logger.error("删除患者基本信息消息对应的新建患者基本信息不存在！patientEid: {}",
                        patientEid);

                return false;
            }

            patientCrossIndexList = getPatientCrossIndexList(patientEid);

            // $Author :jin_peng
            // $Date : 2013/02/27 13:34$
            // [BUG]0014141 MODIFY BEGIN
            if (patientCrossIndexList != null
                && !patientCrossIndexList.isEmpty())
            {
                logger.error("删除患者基本信息消息对应的关联患者信息仍然存在，暂不可删除！patientEid: {}",
                        patientEid);

                return false;
            }

            // [BUG]0014141 MODIFY END
        }

        return true;
    }

    /**
     * 获取到相应删除患者基本信息数据进行数据库操作
     * @param prpain201302uv03 xml文件中获取的删除患者基本信息相应业务数据
     */
    @Override
    @Transactional
    public void saveOrUpdate(PRPAIN201302UV03 prpain201302uv03)
    {
        setTidybusinessLogic(prpain201302uv03);

        patientService.deletePatientRelevance(patientDeleteList,
                patientContactDeleteList, patientCredentialDeleteList,
                patientAddressDeleteList);
    }

    /**
     * 根据删除患者基本信息消息的业务逻辑设置需要更新数据库的相关患者信息
     * @param prpain201302uv03 xml文件中获取的删除患者基本信息相应业务数据
     */
    private void setTidybusinessLogic(PRPAIN201302UV03 prpain201302uv03)
    {
        String patientEid = null;
        BigDecimal patientSn = null;
        Patient deletePatient = null;
        List<PatientContact> deletePatientContactList = null;
        List<PatientCredential> deletePatientCredentialList = null;
        List<PatientAddress> deletePatientAddressList = null;

        patientDeleteList = new ArrayList<Patient>();

        patientContactDeleteList = new ArrayList<PatientContact>();

        patientCredentialDeleteList = new ArrayList<PatientCredential>();

        patientAddressDeleteList = new ArrayList<PatientAddress>();

        for (PatientDelCollection patientDelCollection : patientEidList)
        {
            patientEid = patientDelCollection.getPatientEid();

            deletePatient = pcPart.getPatient(patientEid, commonService);
            
            pcPart.deletePatient(deletePatient, systemTime, prpain201302uv03);

            patientSn = deletePatient.getPatientSn();

            deletePatientContactList = getDeletePatientContact(patientSn,prpain201302uv03);

            deletePatientCredentialList = getDeletePatientCredential(patientSn,prpain201302uv03);

            deletePatientAddressList = getDeletePatientAddress(patientSn,prpain201302uv03);

            patientDeleteList.add(deletePatient);

            if (deletePatientContactList != null
                && !deletePatientContactList.isEmpty())
            {
                patientContactDeleteList.addAll(deletePatientContactList);
            }

            if (deletePatientCredentialList != null
                && !deletePatientCredentialList.isEmpty())
            {
                patientCredentialDeleteList.addAll(deletePatientCredentialList);
            }

            if (deletePatientAddressList != null
                && !deletePatientAddressList.isEmpty())
            {
                patientAddressDeleteList.addAll(deletePatientAddressList);
            }
        }
    }

    /**
     * 通过患者eid获取相关患者交叉索引对象集合
     * @param patientEid 关联患者eid
     * @return 关联患者交叉索引对象集合
     */
    public List<Object> getPatientCrossIndexList(String patientEid)
    {
        List<Object> patientCrossIndexList = null;

        // 设置相应查询条件
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("patientEid", patientEid);
        map.put("deleteFlag", Constants.NO_DELETE_FLAG);

        // 根据相应查询条件查询患者交叉索引对象集合
        patientCrossIndexList = commonService.selectByCondition(
                PatientCrossIndex.class, map);

        return patientCrossIndexList;
    }

    /**
     * 根据患者内部序列号获取逻辑删除的联系人对象集合
     * @param patientSn 患者内部序列号
     * @return 逻辑删除的联系人对象集合
     */
    private List<PatientContact> getDeletePatientContact(BigDecimal patientSn,PRPAIN201302UV03 prpain201302uv03)
    {
        List<PatientContact> deletePatientContactList = null;

        // 设置相应查询条件
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("patientSn", patientSn);
        map.put("deleteFlag", Constants.NO_DELETE_FLAG);

        List<Object> patientContactList = commonService.selectByCondition(
                PatientContact.class, map);

        if (patientContactList != null && !patientContactList.isEmpty())
        {
            deletePatientContactList = new ArrayList<PatientContact>();

            for (Object object : patientContactList)
            {
                PatientContact deletePatientContact = (PatientContact) object;

                deletePatientContact.setDeleteFlag(Constants.DELETE_FLAG);

                deletePatientContact.setDeleteTime(systemTime);

                deletePatientContact.setUpdateTime(systemTime);                
                // $Author :chang_xuewen
                // $Date : 2013/08/31 16:00$
                // [BUG]0036757 MODIFY BEGIN
                // 删除人
                deletePatientContact.setDeleteby(prpain201302uv03.getMessage().getServiceId());
                // [BUG]0036757 MODIFY END
                deletePatientContactList.add(deletePatientContact);
            }
        }

        return deletePatientContactList;
    }

    /**
     * 根据患者内部序列号获取逻辑删除的证件对象集合
     * @param patientSn 患者内部序列号
     * @return 逻辑删除的证件对象集合
     */
    private List<PatientCredential> getDeletePatientCredential(
            BigDecimal patientSn,PRPAIN201302UV03 prpain201302uv03)
    {
        List<PatientCredential> deletePatientCredentialList = null;

        // 设置相应查询条件
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("patientSn", patientSn);
        map.put("deleteFlag", Constants.NO_DELETE_FLAG);

        List<Object> patientCredentialList = commonService.selectByCondition(
                PatientCredential.class, map);

        if (patientCredentialList != null && !patientCredentialList.isEmpty())
        {
            deletePatientCredentialList = new ArrayList<PatientCredential>();

            for (Object object : patientCredentialList)
            {
                PatientCredential deletePatientCredential = (PatientCredential) object;

                deletePatientCredential.setDeleteFlag(Constants.DELETE_FLAG);

                deletePatientCredential.setDeleteTime(systemTime);

                deletePatientCredential.setUpdateTime(systemTime);
                // $Author :chang_xuewen
                // $Date : 2013/08/31 16:00$
                // [BUG]0036757 MODIFY BEGIN
                //删除人
                deletePatientCredential.setDeleteby(prpain201302uv03.getMessage().getServiceId());
                // [BUG]0036757 MODIFY END
                deletePatientCredentialList.add(deletePatientCredential);
            }
        }

        return deletePatientCredentialList;
    }

    /**
     * 根据患者内部序列号获取逻辑删除的地址对象集合
     * @param patientSn 患者内部序列号
     * @return 逻辑删除的地址对象集合
     */
    private List<PatientAddress> getDeletePatientAddress(BigDecimal patientSn,PRPAIN201302UV03 prpain201302uv03)
    {
        List<PatientAddress> deletePatientAddressList = null;

        // 设置相应查询条件
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("patientSn", patientSn);
        map.put("deleteFlag", Constants.NO_DELETE_FLAG);

        List<Object> patientAddressList = commonService.selectByCondition(
                PatientAddress.class, map);

        if (patientAddressList != null && !patientAddressList.isEmpty())
        {
            deletePatientAddressList = new ArrayList<PatientAddress>();

            for (Object object : patientAddressList)
            {
                PatientAddress deletePatientAddress = (PatientAddress) object;

                deletePatientAddress.setDeleteFlag(Constants.DELETE_FLAG);

                deletePatientAddress.setDeleteTime(systemTime);

                deletePatientAddress.setUpdateTime(systemTime);
                // $Author :chang_xuewen
                // $Date : 2013/08/31 16:00$
                // [BUG]0036757 MODIFY BEGIN
                //删除人
                deletePatientAddress.setDeleteby(prpain201302uv03.getMessage().getServiceId());
                // [BUG]0036757 MODIFY END
                deletePatientAddressList.add(deletePatientAddress);
            }
        }

        return deletePatientAddressList;
    }

}
