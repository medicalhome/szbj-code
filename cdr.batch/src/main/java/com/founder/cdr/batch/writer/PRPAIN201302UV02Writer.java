/**
 * Copryright
 */
package com.founder.cdr.batch.writer;

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

import com.founder.cdr.batch.writer.prpain201302uv01.PRPAIN201302UV01CommonPart;
import com.founder.cdr.core.Constants;
import com.founder.cdr.entity.Patient;
import com.founder.cdr.entity.PatientAddress;
import com.founder.cdr.entity.PatientContact;
import com.founder.cdr.entity.PatientCredential;
import com.founder.cdr.entity.PatientCrossIndex;
import com.founder.cdr.entity.PatientTemp;
import com.founder.cdr.hl7.dto.Address;
import com.founder.cdr.hl7.dto.Contact;
import com.founder.cdr.hl7.dto.Credential;
import com.founder.cdr.hl7.dto.RelationshipPatient;
import com.founder.cdr.hl7.dto.prpain201302uv01.PRPAIN201302UV01;
import com.founder.cdr.hl7.dto.prpain201302uv02.PRPAIN201302UV02;
import com.founder.cdr.service.CommonService;
import com.founder.cdr.service.PatientService;
import com.founder.cdr.util.DateUtils;
import com.founder.cdr.util.ObjectUtils;
import com.founder.cdr.util.StringUtils;

/**
 * 更新患者基本信息数据接入writer
 * @author jin_peng
 */
@Component(value = "prpain201302uv02Writer")
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class PRPAIN201302UV02Writer implements BusinessWriter<PRPAIN201302UV02>
{
    private static final Logger logger = LoggerFactory.getLogger(PRPAIN201302UV02Writer.class);

    // 患者基本信息对象所用序列名称
    private static final String PATIENT_SEQUENCE = "SEQ_PATIENT";

    // 患者内部序列号
    private BigDecimal patientSn;

    // 患者empiId
    private String patientEid;

    // 系统当前时间
    private Date systemTime;

    // $Author:wei_peng
    // $Date:2012/9/26 15:01
    // $[BUG]0009702 ADD BEGIN
    // 域ID
    private String patientDomain;

    // $[BUG]0009702 ADD END

    // $Author :jin_peng
    // $Date : 2013/02/20 13:37$
    // [BUG]0014005 ADD BEGIN
    // 判断是否因消息中的时间戳比数据库中的小而提早结束此消息的相关操作
    private boolean isEndedEarlyByVersionNo;

    // [BUG]0014005 ADD END

    // 本消息中联系人信息
    private List<Contact> contactList;

    // 本消息中的相关证件信息
    private List<Credential> credentialList;

    // 本消息中的相关地址信息
    private List<Address> addressList;

    // 需要更新的eid对应的患者基本信息对象
    private Patient updatePatient;

    // 需要新增的联系人对象集合
    private List<PatientContact> patientContactList;

    // 需要新增的证件对象集合
    private List<PatientCredential> patientCredentialList;

    // 需要新增的地址信息集合
    private List<PatientAddress> patientAddressList;

    // 需要删除的联系人对象集合
    private List<Object> patientContactDeleteList;

    // 需要删除的证件对象集合
    private List<Object> patientCredentialDeleteList;

    // 需要删除的地址信息对象集合
    private List<Object> patientAddressDeleteList;

    // 需要新增的患者交叉索引信息对象
    private List<PatientCrossIndex> patientCrossIndexAddList;

    // 需要更新的患者交叉索引信息对象
    private List<PatientCrossIndex> patientCrossIndexUpdateList;

    // 需要更新的患者相关联的业务对象集合
    private List<Map<String, Object>> relevanceBusinessList;

    // $Author :jin_peng
    // $Date : 2012/09/03 10:31$
    // [BUG]0009336 ADD BEGIN
    // 患者临时信息对象(最终需要逻辑删除)
    private List<PatientTemp> patientTempList;

    // 需要逻辑删除的患者基本信息对象
    private List<Patient> deleteSnPatientList;

    // [BUG]0009336 ADD END

    @Autowired
    private CommonService commonService;

    @Autowired
    private PatientService patientService;

    @Autowired
    private PRPAIN201302UV01CommonPart pcPart;
    
    // 新建或更新患者表标识,新建为true，更新为false
    private boolean isSaveOrUpdate;
    
    // 需要新增或更新的患者基本信息对象
    private Patient saveOrUpdatePatient;
    /**
     * 设置初始化参数
     */
    public PRPAIN201302UV02Writer()
    {
        systemTime = DateUtils.getSystemTime();
    }

    /**
     * 根据场景字段非空校验
     */
    @Override
    public boolean validate(PRPAIN201302UV02 prpain201302uv02)
    {
    	// $Author :chang_xuewen
        // $Date : 2014/1/22 10:30$
        // [BUG]0042086 MODIFY BEGIN
        String headOrgCode = prpain201302uv02.getMessage().getOrgCode();
//        String orgCode = prpain201302uv02.getRelationshipPatient().get(0).getOrgCode();

        /*if (!headOrgCode.equals(orgCode))
        {
            logger.error("消息体与消息头中医疗机构代码不相同！{}", "orgCode = " + orgCode
                + "; headOrgCode = " + headOrgCode);

            return false;
        }*/
        // $Author :chang_xuewen
        // $Date : 2014/1/26 15:30$
        // [BUG]0042144 MODIFY BEGIN
        // $Author :yang_mingjie
    	// $Date : 2014/06/26 10:09$
    	// [BUG]0045630 MODIFY BEGIN 
        String orgCode=Constants.HOSPITAL_CODE;
        String orgName=Constants.HOSPITAL_NAME;
        //[BUG]0045630 MODIFY END
        // Author :jia_yanqing
        // Date : 2014/2/19 13:30
        // [BUG]0042311 DELETE BEGIN
        
        for(RelationshipPatient rp : prpain201302uv02.getRelationshipPatient()){
        	if(!StringUtils.isEmpty(rp.getOrgCode())){
            	orgCode = rp.getOrgCode();
            	orgName = rp.getOrgName();
            	break;
            }  
        }   
        // [BUG]0042311 DELETE END
        /*for(RelationshipPatient rp : prpain201302uv02.getRelationshipPatient()){
        	if(StringUtils.isEmpty(rp.getOrgCode())){
            	rp.setOrgCode(orgCode);
            	rp.setOrgName(orgName);
            }  
        } */   
        // [BUG]0042144 MODIFY END
        // [BUG]0042086 MODIFY END
        
        // $Author :jin_peng
        // $Date : 2012/08/31 10:20$
        // [BUG]0009283 ADD BEGIN
        // 获取消息中的empi_id
        patientEid = prpain201302uv02.getPatientEid();

        // 通过消息中的empi_id查询相应新建患者基本信息
        updatePatient = pcPart.getPatient(patientEid, commonService);

        // $Author :jin_peng
        // $Date : 2013/02/20 13:37$
        // [BUG]0014005 DELETE BEGIN
        // 相应新建患者基本信息存在时需要判断消息中的时间戳是否大于该记录的时间戳，如果不大于则放弃该消息
        /*
         * if (updatePatient != null) { if
         * (ObjectUtils.compare(updatePatient.getVersionNo(),
         * prpain201302uv02.getTimestamp()) != -1) { logger.error(
         * "更新患者基本信息消息对应的新建患者基本信息记录存在，但该记录中的时间戳  {} 不小于本次更新消息中的时间戳  {}",
         * updatePatient.getVersionNo(), prpain201302uv02.getTimestamp());
         * 
         * return false; } }
         */
        // [BUG]0009283 ADD END
        // [BUG]0009283 DELETE END

        return true;
    }

    /**
     * 关联消息验证
     */
    @Override
    public boolean checkDependency(PRPAIN201302UV02 prpain201302uv02,boolean flag)
    {
        // $Author :jin_peng
        // $Date : 2012/08/31 10:20$
        // [BUG]0009283 DELETE BEGIN
        /*
         * patientEid = prpain201302uv02.getPatientEid();
         * 
         * updatePatient = pcPart.getPatient(patientEid, commonService);
         */
        // [BUG]0009283 DELETE END

        if (updatePatient == null)
        {
            //logger.error("更新患者基本信息消息对应的新建患者基本信息不存在！patientEid: {}", patientEid);
            logger.info("新增患者基本信息消息对应的新建患者基本信息！patientEid: {}", patientEid);

            return true;
        }

        // $Author :jin_peng
        // $Date : 2013/02/20 13:37$
        // [BUG]0014005 ADD BEGIN
        // 相应新建患者基本信息存在时需要判断消息中的时间戳是否大于该记录的时间戳，如果不大于则放弃该消息，直接标记为正常完成
        if(updatePatient != null){
        	logger.error("更新患者基本信息消息对应的新建患者基本信息！patientEid: {}", patientEid);
	        if (ObjectUtils.compare(updatePatient.getVersionNo(),
	                prpain201302uv02.getTimestamp()) != -1)
	        {
	            logger.debug("更新患者基本信息消息对应的新建患者基本信息记录存在patientEid: " + patientEid
	                + "，但该记录中的时间戳  {} 不小于本次更新消息中的时间戳  {}",
	                    updatePatient.getVersionNo(),
	                    prpain201302uv02.getTimestamp());
	
	            isEndedEarlyByVersionNo = true;
	        }        	
        }


        // [BUG]0014005 ADD END

        // $Author :jin_peng
        // $Date : 2012/08/31 10:20$
        // [BUG]0009283 DELETE BEGIN
        /*
         * else { if (ObjectUtils.compare(updatePatient.getVersionNo(),
         * prpain201302uv02.getTimestamp()) != -1) { logger.error(
         * "更新患者基本信息消息对应的新建患者基本信息记录存在，但该记录中的时间戳  {} 不小于本次更新消息中的时间戳  {}",
         * updatePatient.getVersionNo(), prpain201302uv02.getTimestamp());
         * 
         * return false; } }
         */
        // [BUG]0009283 DELETE END

        return true;
    }

    /**
     * 获取到相应更新患者基本信息数据进行数据库操作
     * @param prpain201302uv02 xml文件中获取的更新患者基本信息相应业务数据
     */
    @Override
    @Transactional
    public void saveOrUpdate(PRPAIN201302UV02 prpain201302uv02)
    {
        // $Author :jin_peng
        // $Date : 2013/02/20 13:37$
        // [BUG]0014005 ADD BEGIN
        // 如果没有因消息中时间戳小于数据库时间戳而提早结束操作外，其他情况正常执行相关逻辑操作
    	if(updatePatient != null){
            if (!isEndedEarlyByVersionNo)
            {
                // 根据消息业务逻辑设置需要更新数据库的相关患者信息
                setTidybusinessLogic(prpain201302uv02);

                // $Author :jin_peng
                // $Date : 2012/09/03 10:31$
                // [BUG]0009336 MODIFY BEGIN
                // 根据业务逻辑新增，更新患者相关信息
                patientService.saveOrUpdatePatientRelevance(updatePatient,
                        patientContactList, patientCredentialList,
                        patientAddressList, patientContactDeleteList,
                        patientCredentialDeleteList, patientAddressDeleteList,
                        patientCrossIndexAddList, patientCrossIndexUpdateList,
                        deleteSnPatientList, patientTempList, relevanceBusinessList,
                        prpain201302uv02.getMessage().getServiceId());
                // [BUG]0009336 MODIFY END
            }    		
    	}else{
            // 根据消息业务逻辑设置需要更新数据库的相关患者信息
            setTidybusinessLogic_new(prpain201302uv02);

            // Author :jia_yanqing
            // Date : 2012/09/12 17:00
            // [BUG]0037319 MODIFY BEGIN
            // $Author :jin_peng
            // $Date : 2012/09/03 17:13$
            // [BUG]0009326 MODIFY BEGIN
            // 根据业务逻辑新增，更新，删除患者相关信息
            patientService.saveOrUpdatePatientRelevance(saveOrUpdatePatient,
                    isSaveOrUpdate, deleteSnPatientList, patientContactList,
                    patientCredentialList, patientAddressList,
                    patientCrossIndexAddList, patientCrossIndexUpdateList,
                    patientTempList, relevanceBusinessList,
                    prpain201302uv02.getMessage().getServiceId());
            // [BUG]0009326 MODIFY END
            // [BUG]0037319 MODIFY END    		
    	}

    }

    /**
     * 根据更新患者基本信息消息的业务逻辑设置需要更新数据库的相关患者信息
     * @param prpain201302uv02 xml文件中获取的更新患者基本信息相应业务数据
     */
    private void setTidybusinessLogic(PRPAIN201302UV02 prpain201302uv02)
    {
        // 获取消息中的联系人信息
        contactList = prpain201302uv02.getPatientContact();

        // 获取消息中的证件信息
        credentialList = prpain201302uv02.getCredential();

        // 获取消息中的地址信息
        addressList = prpain201302uv02.getAddress();

        // 添加工作地址信息
        Address address = pcPart.getWorkAddress(prpain201302uv02);
        if (address != null)
        {
            addressList.add(address);
        }

        // 向需更新的患者对象中设置相关内容信息
        pcPart.setPatientContent(prpain201302uv02, updatePatient,
                commonService, PATIENT_SEQUENCE, systemTime,false);
        patientSn = updatePatient.getPatientSn();

        // 给患者信息对应的相关信息进行赋值操作
        setPatientChild(prpain201302uv02);

        // 获取患者关联交叉索引信息对象集合
        List<RelationshipPatient> rlsPatientList = prpain201302uv02.getRelationshipPatient();

        // 创建新增交叉索引对象集合
        patientCrossIndexAddList = new ArrayList<PatientCrossIndex>();

        // 创建更新交叉索引对象集合
        patientCrossIndexUpdateList = new ArrayList<PatientCrossIndex>();

        // 创建各业务表更新对象集合
        relevanceBusinessList = new ArrayList<Map<String, Object>>();

        // $Author :jin_peng
        // $Date : 2012/09/03 13:32$
        // [BUG]0009336 ADD BEGIN
        // 创建患者临时对象集合
        patientTempList = new ArrayList<PatientTemp>();

        // 创建需要逻辑删除的患者基本信息对象集合
        deleteSnPatientList = new ArrayList<Patient>();
        // [BUG]0009336 ADD END

        // 循环处理患者关联关系信息，患者基本信息
        for (int i = 0; i < rlsPatientList.size(); i++)
        {
            // 获取消息中的患者关联信息
            RelationshipPatient rlsPatient = rlsPatientList.get(i);

            // 获取关联患者对应的交叉索引信息
            PatientCrossIndex patientCrossIndex = pcPart.getPatientCrossIndex(
                    rlsPatient.getPatientLid(), rlsPatient.getPatientDomain(),
                    commonService, rlsPatient.getOrgCode());

            // 是否把相关业务表中的patientSn更新为SBR记录中的patientSn
            boolean isUpdatePatientSn = true;

            // 患者交叉索引信息存在时进行该信息的更新操作，否则进行新增操作
            if (patientCrossIndex == null)
            {
                // 新增消息关联的患者交叉索引信息
                patientCrossIndexAddList.add(pcPart.getPatientCrossIndexContent(
                        rlsPatient, new PatientCrossIndex(), systemTime,
                        patientEid, prpain201302uv02.getTimestamp(),
                        prpain201302uv02, true, rlsPatient.getOrgCode(),
                        rlsPatient.getOrgName()));

                // $Author :jin_peng
                // $Date : 2012/09/03 10:31$
                // [BUG]0009336 ADD BEGIN
                // 添加对患者临时表和患者表中临时信息处理
                // 获取关联患者信息对应的患者临时信息
                PatientTemp pt = pcPart.getPatientTemp(rlsPatient,
                        commonService, rlsPatient.getOrgCode());

                if (pt != null)
                {
                    // 获取将要删除或更新的患者基本信息
                    Patient p = pcPart.getPatient(pt.getPatientSn(),
                            commonService);

                    // 逻辑删除消息对应的患者临时表信息
                    pcPart.deleteLogicPatientTemp(pt, systemTime,prpain201302uv02);
                    // 将已设置完成删除信息的患者临时信息装入删除患者临时信息对象集合中
                    patientTempList.add(pt);

                    // 给需要逻辑删除的患者信息赋值(赋予删除标记等信息)
                    pcPart.deletePatient(p, systemTime,prpain201302uv02);
                    // 将已设置完成删除信息的患者基本信息装入删除患者基本信息对象集合中
                    deleteSnPatientList.add(p);
                }
                // [BUG]0009336 ADD END
            }
            else
            {
                // 当交叉索引记录中的eid与SBR记录中的eid相等时用消息中的关联患者信息更新相应交叉索引表中的记录，同时不需更新各业务表中的patientSn
                if (updatePatient.getPatientEid().equals(
                        patientCrossIndex.getPatientEid()))
                {
                    // 此种情况时不需更新各业务表中的patientSn(为普通更新)
                    isUpdatePatientSn = false;
                }

                // 更新交叉索引表记录
                patientCrossIndexUpdateList.add(pcPart.getPatientCrossIndexContent(
                        rlsPatient, patientCrossIndex, systemTime, patientEid,
                        prpain201302uv02.getTimestamp(), prpain201302uv02,
                        false, rlsPatient.getOrgCode(),
                        rlsPatient.getOrgName()));
            }

            // 当患者合并或拆分时更新各相关业务表中的patientSn为最新patientSn
            if (isUpdatePatientSn)
            {
                // 把患者对应的所有拥有patientSn属性的表中的patientSn更新为已唯一存在的患者基本信息所对应的patientSn
                relevanceBusinessList.add(patientService.getHaveSetFieldsAllRelevanceObject(
                        rlsPatient.getPatientDomain(),
                        rlsPatient.getPatientLid(), patientSn, systemTime,
                        rlsPatient.getOrgCode()));
            }
        }
    }

    /**
     * 根据新建患者基本信息消息的业务逻辑设置需要更新数据库的相关患者信息
     * @param prpain201302uv02 xml文件中获取的新建患者基本信息相应业务数据
     */
    private void setTidybusinessLogic_new(PRPAIN201302UV02 prpain201302uv02)
    {
        // $Author :jin_peng
        // $Date : 2012/09/01 11:13$
        // [BUG]0009326 ADD BEGIN
        // 默认该患者信息为更新数据库状态
        isSaveOrUpdate = true;
        // 当存在多条消息中关联患者对应的患者临时表数据时，只更新第一条对应的患者基本信息记录
        boolean isFirst = true;
        // 是否需要更新所有相关业务表
        boolean isBusinessUpdate = true;
        // 循环中档次是否更新患者
        boolean isCurrentUpdate = false;
        // 声明需更新或删除的患者基本信息对象
        Patient p = null;
        // [BUG]0009326 ADD END

        // 获取消息中的联系人信息
        contactList = prpain201302uv02.getPatientContact();

        // 获取消息中的证件信息
        credentialList = prpain201302uv02.getCredential();

        // 获取消息中的地址信息
        addressList = prpain201302uv02.getAddress();

        // 添加工作地址信息
        Address address = pcPart.getWorkAddress(prpain201302uv02);
        if (address != null)
        {
            addressList.add(address);
        }

        // $Author :jin_peng
        // $Date : 2012/09/03 17:13$
        // [BUG]0009326 DELETE BEGIN
        // 创建需新增的患者信息对象
        /*
         * savePatient = new Patient(); // 向需新增的患者对象中设置相关内容信息
         * pcPart.setPatientContent(prpain201302uv02, savePatient,
         * commonService, PATIENT_SEQUENCE, systemTime); patientSn =
         * savePatient.getPatientSn();
         */
        // [BUG]0009326 DELETE END

        // 获取empi_id
        patientEid = prpain201302uv02.getPatientEid();

        // 获取患者关联交叉索引信息对象集合
        List<RelationshipPatient> rlsPatientList = prpain201302uv02.getRelationshipPatient();

        // 创建新增交叉索引对象集合
        patientCrossIndexAddList = new ArrayList<PatientCrossIndex>();

        // 创建更新交叉索引对象集合
        patientCrossIndexUpdateList = new ArrayList<PatientCrossIndex>();

        // 创建各业务表更新对象集合
        relevanceBusinessList = new ArrayList<Map<String, Object>>();

        // 创建患者临时对象集合
        patientTempList = new ArrayList<PatientTemp>();

        // 创建需要逻辑删除的患者基本信息对象集合
        deleteSnPatientList = new ArrayList<Patient>();

        // $Author :jin_peng
        // $Date : 2012/09/01 11:13$
        // [BUG]0009326 MODIFY BEGIN
        List<RelationshipPatient> businessUpdateList = new ArrayList<RelationshipPatient>();

        // 循环处理患者关联关系信息，患者基本信息及患者临时信息
        for (int i = 0; i < rlsPatientList.size(); i++)
        {
            // 每次默认当前没有更新
            isCurrentUpdate = false;

            // 每次默认更新患者相关业务表
            isBusinessUpdate = true;

            // 获取消息中的患者关联信息
            RelationshipPatient rlsPatient = rlsPatientList.get(i);

            // 获取关联患者信息对应的患者临时信息
            PatientTemp pt = pcPart.getPatientTemp(rlsPatient, commonService,
                    rlsPatient.getOrgCode());

            if (pt != null)
            {
                // 获取将要删除或更新的患者基本信息
                p = pcPart.getPatient(pt.getPatientSn(), commonService);

                // 当患者临时表信息存在并且为第一次操作时更新相应患者信息
                if (isFirst)
                {
                    // 获取需要更新的患者基本信息对象
                    saveOrUpdatePatient = p;

                    // 患者基本信息表设置为不是保存状态
                    isSaveOrUpdate = false;

                    // 患者基本信息已更新完成标识
                    isFirst = false;

                    // 设置当前已有患者更新操作
                    isCurrentUpdate = true;
                }

                // 逻辑删除消息对应的患者临时表信息
                pcPart.deleteLogicPatientTemp(pt, systemTime, prpain201302uv02);
                // 将已设置完成删除信息的患者临时信息装入删除患者临时信息对象集合中
                patientTempList.add(pt);

                // 患者临时信息存在并且不是更新患者的情况时需删除临时表对应的相应患者基本信息对象
                if (!isCurrentUpdate)
                {
                    // 给需要逻辑删除的患者信息赋值(赋予删除标记等信息)
                    pcPart.deletePatient(p, systemTime, prpain201302uv02);
                    // 将已设置完成删除信息的患者基本信息装入删除患者基本信息对象集合中
                    deleteSnPatientList.add(p);
                }
            }

            // 获取关联患者对应的交叉索引信息
            PatientCrossIndex patientCrossIndex = pcPart.getPatientCrossIndex(
                    rlsPatient.getPatientLid(), rlsPatient.getPatientDomain(),
                    commonService, rlsPatient.getOrgCode());

            // 患者交叉索引信息存在时进行该信息的更新操作，否则进行新增操作
            if (patientCrossIndex == null)
            {
                // 新增消息关联的患者交叉索引信息
                patientCrossIndexAddList.add(pcPart.getPatientCrossIndexContent(
                        rlsPatient, new PatientCrossIndex(), systemTime,
                        patientEid, prpain201302uv02.getTimestamp(),
                        prpain201302uv02, true, rlsPatient.getOrgCode(),
                        rlsPatient.getOrgName()));

                // $Author :jin_peng
                // $Date : 2013/02/27 10:23$
                // [BUG]0014137 DELETE BEGIN
                // 只有当患者交叉索引表中不存在记录时才不更新相关业务表
                // 当相关交叉索引信息不存在时设置不更新相关业务表信息
                // if (isCurrentUpdate)
                // {
                isBusinessUpdate = false;
                // }

                // [BUG]0014137 DELETE END
            }
            else
            {
                // 用消息中最佳纪录信息中的患者empi_id更新相应交叉索引信息
                patientCrossIndexUpdateList.add(pcPart.getPatientCrossIndexContent(
                        rlsPatient, patientCrossIndex, systemTime, patientEid,
                        prpain201302uv02.getTimestamp(), prpain201302uv02,
                        false, rlsPatient.getOrgCode(), rlsPatient.getOrgName()));
            }

            // 根据是否更新相关业务表标识来确定是否更新
            if (isBusinessUpdate)
            {
                businessUpdateList.add(rlsPatient);
            }
        }

        if (isSaveOrUpdate)
        {
            // 创建需新增的患者信息对象
            saveOrUpdatePatient = new Patient();
        }

        // 向需新增的患者对象中设置相关内容信息
        pcPart.setPatientContent(prpain201302uv02, saveOrUpdatePatient,
                commonService, PATIENT_SEQUENCE, systemTime, true);
        patientSn = saveOrUpdatePatient.getPatientSn();

        // 给患者信息对应的相关信息进行赋值操作
        setPatientChild_new(prpain201302uv02);

        // 更新关联患者对应的各业务表信息
        setBusinessRelationship(businessUpdateList);
        // [BUG]0009326 MODIFY END
    }
    // $Author :jin_peng
    // $Date : 2012/09/03 17:50$
    // [BUG]0009326 ADD BEGIN
    /**
     * 更新关联患者对应的各业务表信息
     * @param rlsPatientList 需要更新各业务表的关联患者信息对象集合
     */
    private void setBusinessRelationship(
            List<RelationshipPatient> rlsPatientList)
    {
        if (rlsPatientList != null && !rlsPatientList.isEmpty())
        {
            for (RelationshipPatient rlsPatient : rlsPatientList)
            {
                // 把患者对应的所有拥有patientSn属性的表中的patientSn更新为已唯一存在的患者基本信息所对应的patientSn
                relevanceBusinessList.add(patientService.getHaveSetFieldsAllRelevanceObject(
                        rlsPatient.getPatientDomain(),
                        rlsPatient.getPatientLid(), patientSn, systemTime,
                        rlsPatient.getOrgCode()));
            }
        }
    }

    // [BUG]0009326 ADD END

    /**
     * 根据业务逻辑设置患者相关联信息对象
     * @param isUpdate 是否为更新患者信息标识
     * @param prpain201302uv02 xml文件中获取的患者基本信息相应业务数据
     */
    private void setPatientChild_new(PRPAIN201302UV02 prpain201302uv02)
    {
        // 设置需新增的联系人对象信息
        patientContactList = pcPart.getCreatePatientContact(contactList,
                patientSn, systemTime, prpain201302uv02);

        // 设置需新增的证件对象信息
        patientCredentialList = pcPart.getCreatePatientCredential(
                credentialList, patientSn, systemTime, prpain201302uv02);

        // 设置需新增的地址对象信息
        patientAddressList = pcPart.getCreatePatientAddress(addressList,
                patientSn, systemTime, prpain201302uv02);
    }

    /**
     * 根据业务逻辑设置患者相关联信息对象
     */
    private void setPatientChild(PRPAIN201302UV02 prpain201302uv02)
    {
        // 设置需新增的联系人对象信息
        patientContactList = pcPart.getCreatePatientContact(contactList,
                patientSn, systemTime, prpain201302uv02);

        // 设置需新增的证件对象信息
        patientCredentialList = pcPart.getCreatePatientCredential(
                credentialList, patientSn, systemTime, prpain201302uv02);

        // 设置需新增的地址对象信息
        patientAddressList = pcPart.getCreatePatientAddress(addressList,
                patientSn, systemTime, prpain201302uv02);

        // 当患者信息为更新状态时，该患者关联信息采用先删后加的形式存储，此处设置需要删除的患者相关信息
        // 设置需删除的联系人对象信息
        patientContactDeleteList = getDeletePatientContact();

        // 设置需删除的证件对象信息
        patientCredentialDeleteList = getDeletePatientCredential();

        // 设置需删除的地址对象信息
        patientAddressDeleteList = getDeletePatientAddress();
    }

    /**
     * 根据患者内部序列号获取需要删除的联系人对象集合
     */
    private List<Object> getDeletePatientContact()
    {
        // 设置相应查询条件
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("patientSn", patientSn);
        map.put("deleteFlag", Constants.NO_DELETE_FLAG);

        // 根据相应查询条件获取需要删除的联系人对象集合
        return commonService.selectByCondition(PatientContact.class, map);
    }

    /**
     * 根据患者内部序列号获取需删除的证件对象集合
     */
    private List<Object> getDeletePatientCredential()
    {
        // 设置相应查询条件
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("patientSn", patientSn);
        map.put("deleteFlag", Constants.NO_DELETE_FLAG);

        // 根据相应查询条件获取需要删除的证件对象集合
        return commonService.selectByCondition(PatientCredential.class, map);
    }

    /**
     * 根据患者内部序列号获取需删除的地址信息对象集合
     */
    private List<Object> getDeletePatientAddress()
    {
        // 设置相应查询条件
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("patientSn", patientSn);
        map.put("deleteFlag", Constants.NO_DELETE_FLAG);

        // 根据相应查询条件获取需要删除的地址信息对象集合
        return commonService.selectByCondition(PatientAddress.class, map);
    }
}
