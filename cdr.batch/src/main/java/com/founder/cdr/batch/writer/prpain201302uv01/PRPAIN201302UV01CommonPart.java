/**
 * Copryright
 */
package com.founder.cdr.batch.writer.prpain201302uv01;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.founder.cdr.cache.DictionaryCache;
import com.founder.cdr.core.Constants;
import com.founder.cdr.entity.Patient;
import com.founder.cdr.entity.PatientAddress;
import com.founder.cdr.entity.PatientContact;
import com.founder.cdr.entity.PatientCredential;
import com.founder.cdr.entity.PatientCrossIndex;
import com.founder.cdr.entity.PatientTemp;
import com.founder.cdr.hl7.dto.Address;
import com.founder.cdr.hl7.dto.BaseDto;
import com.founder.cdr.hl7.dto.Contact;
import com.founder.cdr.hl7.dto.Credential;
import com.founder.cdr.hl7.dto.RelationshipPatient;
import com.founder.cdr.hl7.dto.prpain201302uv03.PRPAIN201302UV03;
import com.founder.cdr.service.CommonService;
import com.founder.cdr.util.DateUtils;
import com.founder.cdr.util.StringUtils;

/**
 * 患者基本信息数据接入writer部分功能（公用）
 * @author jin_peng
 */
@Component
public class PRPAIN201302UV01CommonPart
{
    /**
     * 用消息中的信息设置传入的患者基本信息对象内容
     * @param prpain201302uv01CommonDto xml文件中获取的患者基本信息相应业务数据
     * @param patient 需新增或更新的患者对象
     * @param commonService 公用数据库操作对象
     * @param patientSequence 患者序列
     * @param systemTime 系统当前时间
     */
    public void setPatientContent(
            PRPAIN201302UV01CommonDto prpain201302uv01CommonDto,
            Patient patient, CommonService commonService,
            String patientSequence, Date systemTime,boolean newFlag)
    {
        // 获取传入患者对象的患者内部序列号
        BigDecimal patientSn = patient.getPatientSn();

        // 当该患者内部序列号为空时表明该患者信息为新增对象并设置相应属性值
        if (patientSn == null)
        {
            // 获取新的患者内部序列号
            patientSn = commonService.getSequence(patientSequence);

            // 设置已获取的患者内部序列号
            patient.setPatientSn(patientSn);

            // 设置创建时间
            patient.setCreateTime(systemTime);

            // 设置新增记录时的更新回数
            patient.setUpdateCount(Constants.INSERT_UPDATE_COUNT);

            // 设置新增记录时的删除标记
            patient.setDeleteFlag(Constants.NO_DELETE_FLAG);
        }

        // 设置患者empi id
        patient.setPatientEid(prpain201302uv01CommonDto.getPatientEid());

        // 设置患者姓名
        patient.setPatientName(prpain201302uv01CommonDto.getPatientName());

        // 设置患者性别
        patient.setGenderCode(prpain201302uv01CommonDto.getGenderCode());

        // $Author :tong_meng
        // $Date : 2012/7/1 10:33$
        // [BUG]0007418 ADD BEGIN
        // 设置患者性别名称
        if(DictionaryCache.getDictionary(Constants.DOMAIN_GENDER) != null){
        	patient.setGenderName(DictionaryCache.getDictionary(
                    Constants.DOMAIN_GENDER).get(
                    prpain201302uv01CommonDto.getGenderCode()));
        }
        
        // [BUG]0007418 ADD END

        // 设置患者出生日期
        patient.setBirthDate(DateUtils.stringToDate(prpain201302uv01CommonDto.getBirthDate()));

        // 设置患者婚姻状态
        patient.setMarriageStatus(prpain201302uv01CommonDto.getMarriageStatus());

        // $Author :tong_meng
        // $Date : 2012/7/1 10:33$
        // [BUG]0007418 ADD BEGIN
        // 设置患者婚姻状态名称
        if(DictionaryCache.getDictionary(Constants.DOMAIN_MARRIAGE) != null){
        patient.setMarriageStatusName(DictionaryCache.getDictionary(
                Constants.DOMAIN_MARRIAGE).get(
                prpain201302uv01CommonDto.getMarriageStatus()));
        }
        // [BUG]0007418 ADD END

        // 设置患者民族代码
        patient.setNationCode(prpain201302uv01CommonDto.getNationCode());

        // $Author :tong_meng
        // $Date : 2012/7/1 10:33$
        // [BUG]0007418 ADD BEGIN
        // 设置患者民族名称
        if(DictionaryCache.getDictionary(Constants.DOMAIN_NATION) != null){
        patient.setNationName(DictionaryCache.getDictionary(
                Constants.DOMAIN_NATION).get(
                prpain201302uv01CommonDto.getNationCode()));
        }
        // [BUG]0007418 ADD END

        // $Author:wei_peng
        // $Date:2012/9/27 10:39
        // $[BUG]0009698 MODIFY BEGIN
        // 设置患者出生地区县码
        patient.setBirthplaceDistCode(prpain201302uv01CommonDto.getBirthPlace());
        // 设置患者出生地（通过系统传来的区县码ID从字典中获取）
        if(DictionaryCache.getDictionary(Constants.DOMAIN_DISTRICT) != null){
        patient.setBirthPlace(DictionaryCache.getDictionary(
                Constants.DOMAIN_DISTRICT).get(
                prpain201302uv01CommonDto.getBirthPlace()));
        }
        // $[BUG]0009698 MODIFY END

        // 设置患者国籍代码
        patient.setNationalityCode(prpain201302uv01CommonDto.getNationalityCode());

        // $Author :jin_peng
        // $Date : 2012/08/31 16:34$
        // [BUG]0009064 MODIFY BEGIN
        // $Author :tong_meng
        // $Date : 2012/7/1 10:33$
        // [BUG]0007418 ADD BEGIN
        // 设置患者国籍名称
        if(DictionaryCache.getDictionary(Constants.DOMAIN_NATIONALITY) != null){
        patient.setNationalityName(DictionaryCache.getDictionary(
                Constants.DOMAIN_NATIONALITY).get(
                prpain201302uv01CommonDto.getNationalityCode()));
        }
        // [BUG]0007418 ADD END
        // [BUG]0009064 MODIFY END

        // 设置患者职业代码
        patient.setOccupationCode(prpain201302uv01CommonDto.getOccupationCode());

        // $Author :tong_meng
        // $Date : 2012/7/1 10:33$
        // [BUG]0007418 ADD BEGIN
        // 设置患者职业名称
        if(DictionaryCache.getDictionary(Constants.DOMAIN_OCCUPATION) != null){
        patient.setOccupationName(DictionaryCache.getDictionary(
                Constants.DOMAIN_OCCUPATION).get(
                prpain201302uv01CommonDto.getOccupationCode()));
        }
        // [BUG]0007418 ADD END

        // 设置患者文化程度代码
        patient.setEducationDegree(prpain201302uv01CommonDto.getEducationDegree());

        // $Author :tong_meng
        // $Date : 2012/7/1 10:33$
        // [BUG]0007418 ADD BEGIN
        // 设置患者文化程度名称
        if(DictionaryCache.getDictionary(Constants.DOMAIN_EDUCATION_DEGREE) != null){
        patient.setEducationDegreeName(DictionaryCache.getDictionary(
                Constants.DOMAIN_EDUCATION_DEGREE).get(
                prpain201302uv01CommonDto.getEducationDegree()));
        }
        // [BUG]0007418 ADD END

        // 设置患者工作单位名称
        patient.setWorkPlace(prpain201302uv01CommonDto.getWorkPlaceName());

        // $Author :jin_peng
        // $Date : 2012/09/11 19:27$
        // [BUG]0009653 MODIFY BEGIN
        // $Author :jin_peng
        // $Date : 2012/08/21 14:33$
        // [BUG]0009075 ADD BEGIN
        // 设置患者家庭电话
        patient.setTelephone(prpain201302uv01CommonDto.getHomePhone());

        // 设置患者移动电话
        patient.setMobile(prpain201302uv01CommonDto.getMobelPhone());

        // 设置患者电子邮件地址
        String email = prpain201302uv01CommonDto.getEmail();
        // $Author:wei_peng
        // $Date:2012/9/26 14:14
        // $[BUG]0009705 MODIFY BEGIN
        if (!StringUtils.isEmpty(email)
            && !Constants.EMAIL_PHONE_DEFAULT_VALUE.equals(email.substring(7)))
        {
            patient.setEmail(email.substring(7));
        }
        // $[BUG]0009705 MODIFY BEGIN
        // [BUG]0009075 ADD END
        // [BUG]0009653 MODIFY END

        // 设置记录时间戳
        patient.setVersionNo(prpain201302uv01CommonDto.getTimestamp());

        // 设置记录更新时间
        patient.setUpdateTime(systemTime);
        
        // 设置新增人或更新人
        if(newFlag){
        	patient.setCreateby(prpain201302uv01CommonDto.getMessage().getServiceId());
        }else if(!newFlag){
        	patient.setUpdateby(prpain201302uv01CommonDto.getMessage().getServiceId());
        }
    }

    /**
     * 根据消息中的域id和患者本地id获取对应的患者临时表信息
     * @param rlsPatient xml文件中获取的患者交叉索引业务数据
     * @param commonService 公用数据库操作对象
     * @return 患者临时信息
     */
    public PatientTemp getPatientTemp(RelationshipPatient rlsPatient,
            CommonService commonService, String orgCode)
    {
        PatientTemp patientTemp = null;

        // 设置相应查询条件
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("patientDomain", rlsPatient.getPatientDomain());
        map.put("patientLid", rlsPatient.getPatientLid());
        map.put("orgCode", orgCode);
        map.put("deleteFlag", Constants.NO_DELETE_FLAG);

        // 根据相应查询条件获取患者临时信息
        List<Object> patientTempList = commonService.selectByCondition(
                PatientTemp.class, map);

        // 当查询结果存在时设置需返回的患者临时信息对象
        if (patientTempList != null && !patientTempList.isEmpty())
        {
            // 根据查询结果设置患者临时信息对象
            patientTemp = (PatientTemp) patientTempList.get(0);
        }

        return patientTemp;
    }

    /**
     * 给传入的患者临时对象设置逻辑删除信息
     * @param patientTemp 患者临时信息对象
     * @param systemTime 系统当前时间
     */
    public void deleteLogicPatientTemp(PatientTemp patientTemp, Date systemTime , Object obj)
    {
        // 设置删除标记为已删除
        patientTemp.setDeleteFlag(Constants.DELETE_FLAG);

        // 设置更新时间
        patientTemp.setUpdateTime(systemTime);

        // 设置删除时间
        patientTemp.setDeleteTime(systemTime);
        
        // 设置删除人
        patientTemp.setDeleteby(((BaseDto) obj).getMessage().getServiceId());
        
    }

    /**
     * 获取需新增或更新的患者关联关系对象并已设置相应属性值
     * @param rlsPatient xml文件中获取的患者关联交叉索引业务数据
     * @param pci 患者交叉索引对象
     * @param systemTime 系统当前时间
     * @param patientEid 患者empi_id
     * @param versionNo 版本号
     * @return 患者交叉索引对象
     */
    public PatientCrossIndex getPatientCrossIndexContent(
            RelationshipPatient rlsPatient, PatientCrossIndex pci,
            Date systemTime, String patientEid, String versionNo, Object obj,
            boolean newFlag, String orgCode, String orgName)
    {
        if (pci != null)
        {
            // 设置域id
            pci.setPatientDomain(rlsPatient.getPatientDomain());

            // 设置患者本地id
            pci.setPatientLid(rlsPatient.getPatientLid());

            // 设置empi id
            pci.setPatientEid(patientEid);

            // 设置门诊号
            pci.setOutpatientNo(rlsPatient.getOutpatientNo());

            // 设置住院号
            pci.setInpatientNo(rlsPatient.getInpatientNo());

            // 设置影像号
            pci.setImagingNo(rlsPatient.getImageNo());

            // 设置医保号
            pci.setInsuranceCardNo(rlsPatient.getInsuranceCardNo());

            // 设置时间戳信息
            pci.setVersionNo(versionNo);
            
            // $Author :chang_xuewen
            // $Date : 2013/08/30 15:20$
            // [BUG]0036757 ADD BEGIN            
            // 设置新增或更新人
            if(newFlag){
            	pci.setCreateby(((BaseDto) obj).getMessage().getServiceId());
            }
            else if(!newFlag){
            	pci.setUpdateby(((BaseDto) obj).getMessage().getServiceId());
            }
            // [BUG]0036757 ADD END   

            // 设置记录创建时间
            if (pci.getCreateTime() == null)
            {
                pci.setCreateTime(systemTime);
            }

            // 设置记录更新时间
            pci.setUpdateTime(systemTime);

            // 设置新建记录时的更新回数
            if (pci.getUpdateCount() == null)
            {
                pci.setUpdateCount(Constants.INSERT_UPDATE_COUNT);
            }

            // 设置新建记录时的删除标记
            if (pci.getDeleteFlag() == null)
            {
                pci.setDeleteFlag(Constants.NO_DELETE_FLAG);
            }
            
            // 设置医疗机构代码
            pci.setOrgCode(orgCode);
            
            // 设置医疗机构名称 
            pci.setOrgName(orgName);
        }

        return pci;
    }

    /**
     * 获取患者内部序列号对应的患者基本信息对象
     * @param patientSn 患者内部序列号
     * @param commonService 公用数据库操作对象
     * @return 患者基本信息对象
     */
    public Patient getPatient(BigDecimal patientSn, CommonService commonService)
    {
        Patient patient = null;

        // 设置相应查询条件
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("patientSn", patientSn);
        map.put("deleteFlag", Constants.NO_DELETE_FLAG);

        // 根据相应查询条件查询患者基本信息对象
        List<Object> patientList = commonService.selectByCondition(
                Patient.class, map);

        // 如果查询结果存在则设置需返回的患者基本信息对象
        if (patientList != null && !patientList.isEmpty())
        {
            // 设置需返回的患者基本信息对象
            patient = (Patient) patientList.get(0);
        }

        return patient;
    }

    /**
     * 获取消息中的eid对应的患者基本信息对象
     * @param patientEid 患者内部序列号
     * @param commonService 公用数据库操作对象
     * @return 患者基本信息对象
     */
    public Patient getPatient(String patientEid, CommonService commonService)
    {
        Patient patient = null;

        // 设置相应查询条件
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("patientEid", patientEid);
        map.put("deleteFlag", Constants.NO_DELETE_FLAG);

        // 根据相应查询条件查询患者基本信息对象
        List<Object> patientList = commonService.selectByCondition(
                Patient.class, map);

        // 如果查询结果存在则设置需返回的患者基本信息对象
        if (patientList != null && !patientList.isEmpty())
        {
            // 设置需返回的患者基本信息对象
            patient = (Patient) patientList.get(0);
        }

        return patient;
    }

    /**
     * 给传入的患者基本信息对象设置逻辑删除信息
     * @param patient 患者基本信息对象
     * @param systemTime 系统当前时间
     */
    public void deletePatient(Patient patient, Date systemTime,Object obj)
    {
        // 设置删除标记为已删除
        patient.setDeleteFlag(Constants.DELETE_FLAG);

        // 设置记录更新时间
        patient.setUpdateTime(systemTime);

        // 设置记录删除时间
        patient.setDeleteTime(systemTime);
        
        // 设置删除人
        patient.setDeleteby(((BaseDto) obj).getMessage().getServiceId());
    }

    /**
     * 通过患者本地id和域id获取相关患者交叉索引对象
     * @param patientLid 关联患者本地id
     * @param patientDomain 关联患者域id
     * @param commonService 公用数据库操作对象
     * @return 关联患者交叉索引对象
     */
    public PatientCrossIndex getPatientCrossIndex(String patientLid,
            String patientDomain, CommonService commonService, String orgCode)
    {
        PatientCrossIndex patientCrossIndex = null;

        // 设置相应查询条件
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("patientLid", patientLid);
        map.put("patientDomain", patientDomain);
        map.put("orgCode", orgCode);
        map.put("deleteFlag", Constants.NO_DELETE_FLAG);

        // 根据相应查询条件查询患者交叉索引对象
        List<Object> patientCrossIndexList = commonService.selectByCondition(
                PatientCrossIndex.class, map);

        // 如果查询结果存在则设置需返回的患者交叉索引对象
        if (patientCrossIndexList != null && !patientCrossIndexList.isEmpty())
        {
            // 设置需返回的患者交叉索引对象
            patientCrossIndex = (PatientCrossIndex) patientCrossIndexList.get(0);
        }

        return patientCrossIndex;
    }

    /**
     * 获取需新增的联系人信息对象集合
     * @param contactList 需要添加的患者联系人对象集合
     * @param patientSn 患者内部序列号
     * @param systemTime 系统当前时间
     * @return 联系人对象集合
     */
    public List<PatientContact> getCreatePatientContact(
            List<Contact> contactList, BigDecimal patientSn, Date systemTime, Object obj)
    {
        List<PatientContact> resList = null;
        String homePhone = null;
        String email = null;
        // $Author :jin_peng
        // $Date : 2012/08/31 10:20$
        // [BUG]0009283 ADD BEGIN
        // 下列变量用于判断是否需要接入相应联系人记录
        String relationship = null;
        String contactName = null;
        String contactMobile = null;
        String contactDistrictCode = null;
        String contactProCityAddress = null;
        String contactSubDisNameOrFull = null;
        String zipCode = null;
        // [BUG]0009283 ADD END

        if (contactList != null && !contactList.isEmpty())
        {
            resList = new ArrayList<PatientContact>();

            // 循环设置联系人相关信息
            for (Contact contact : contactList)
            {
                // $Author :jin_peng
                // $Date : 2012/08/31 10:20$
                // [BUG]0009283 DELETE BEGIN
                // $Author :jin_peng
                // $Date : 2012/08/21 11:20$
                // [BUG]0009075 ADD BEGIN
                /*
                 * if (StringUtils.isEmpty(contact.getContactName())) {
                 * continue; }
                 */
                // [BUG]0009075 ADD END
                // [BUG]0009283 DELETE END

                // $Author :jin_peng
                // $Date : 2012/08/31 10:20$
                // [BUG]0009283 MODIFY BEGIN
                // 与患者关系
                relationship = contact.getRelationship();
                // 联系人名称
                contactName = contact.getContactName();
                // 联系人移动电话
                contactMobile = contact.getContactMobelPhone();
                // 联系人地址区县码
                contactDistrictCode = contact.getContactDistrictCode();
                // 联系人街道名称
                contactProCityAddress = contact.getContactProCityAddress();
                // 联系人完整地址
                contactSubDisNameOrFull = contact.getContactSubDisNameOrFull();
                // 联系人邮政编码
                zipCode = contact.getContactZipCode();
                // 联系人家庭地址
                homePhone = contact.getContactPhone();
                // 联系人电子邮件
                email = contact.getContactEmail();

                // 如果添加联系人时所需字段都为空时则不保存该联系人信息
                if (StringUtils.isNullAll(relationship, contactName,
                        contactMobile, contactDistrictCode,
                        contactProCityAddress, contactSubDisNameOrFull,
                        zipCode, homePhone, email))
                {
                    continue;
                }

                // 创建联系人对象
                PatientContact patientContact = new PatientContact();

                // 设置患者内部序列号
                patientContact.setPatientSn(patientSn);

                // 设置与患者关系
                patientContact.setRelationship(relationship);

                // $Author :tong_meng
                // $Date : 2012/7/1 10:33$
                // [BUG]0007418 ADD BEGIN
                // 设置与患者关系名称
                if(DictionaryCache.getDictionary(Constants.DOMAIN_RELATIONSHIP) != null){
                patientContact.setRelationshipName(DictionaryCache.getDictionary(
                        Constants.DOMAIN_RELATIONSHIP).get(
                        contact.getRelationship()));
                }
                // [BUG]0007418 ADD END

                // 设置联系人姓名
                patientContact.setContactName(contactName);

                // $Author:wei_peng
                // $Date:2012/9/26 14:14
                // $[BUG]0009705 MODIFY BEGIN
                // $Author :jin_peng
                // $Date : 2012/09/11 19:27$
                // [BUG]0009653 MODIFY BEGIN
                // $Author :jin_peng
                // $Date : 2012/08/21 14:33$
                // [BUG]0009075 ADD BEGIN
                if (!Constants.EMAIL_PHONE_DEFAULT_VALUE.equals(homePhone))
                {
                    // 设置联系电话
                    patientContact.setContactTelephone(homePhone);
                }
                // [BUG]0009075 ADD END
                // [BUG]0009653 MODIFY END
                // $[BUG]0009705 MODIFY END

                // $Author:wei_peng
                // $Date:2012/9/26 14:14
                // $[BUG]0009705 MODIFY BEGIN
                if (!Constants.EMAIL_PHONE_DEFAULT_VALUE.equals(contactMobile))
                {
                    // 设置联系人移动电话
                    patientContact.setContactMobile(contactMobile);
                }
                // 设置联系人区县码
                patientContact.setDistrictCode(contactDistrictCode);

                // $Author :tongmeng
                // $Date : 2012/7/13 11:00$
                // [BUG]0007956 ADD BEGIN
                // 设置联系人区县名称
                if(DictionaryCache.getDictionary(Constants.DOMAIN_DISTRICT) != null){
                patientContact.setDistrictName(DictionaryCache.getDictionary(
                        Constants.DOMAIN_DISTRICT).get(contactDistrictCode));
                }
                // [BUG]0007956 ADD END

                // 设置街道名称
                patientContact.setSubDistrictName(contactProCityAddress);

                // 当联系人区县码不为空时，联系人完整地址为区县码+街道名称
                if (contactDistrictCode != null
                    && !contactDistrictCode.isEmpty())
                {
                	if(DictionaryCache.getDictionary(Constants.DOMAIN_DISTRICT) != null){
                    String contactDistrictName = DictionaryCache.getDictionary(
                            Constants.DOMAIN_DISTRICT).get(contactDistrictCode);
                    
                    if (contactDistrictName != null)
                    {
                        // 设置联系人完整住址 (已格式化时完整地址为省市地址 + 街道地址)
                        patientContact.setContactAddress(contactDistrictName
                            + contactProCityAddress);
                    }
                	}
                }
                // 当联系人区县码为空时，联系人街道地址为完整地址
                else
                {
                    // 设置联系人完整住址(地址信息未格式化)
                    patientContact.setContactAddress(contactSubDisNameOrFull);
                }
                // $[BUG]0009705 MODIFY END

                // 设置邮政编码
                patientContact.setPostCode(zipCode);

                // $Author :jin_peng
                // $Date : 2012/09/11 19:27$
                // [BUG]0009653 MODIFY BEGIN
                // $Author :jin_peng
                // $Date : 2012/08/21 14:33$
                // [BUG]0009075 ADD BEGIN
                // 设置电子邮件地址
                if (!StringUtils.isEmpty(email))
                {
                    patientContact.setContactEmail(email.substring(7));
                }
                // [BUG]0009075 ADD END
                // [BUG]0009653 MODIFY END
                // [BUG]0009283 MODIFY END

                // 设置记录创建时间
                patientContact.setCreateTime(systemTime);

                // 设置记录更新时间
                patientContact.setUpdateTime(systemTime);

                // 设置新增记录时的更新回数
                patientContact.setUpdateCount(Constants.INSERT_UPDATE_COUNT);

                // 设置新增记录时的删除标记
                patientContact.setDeleteFlag(Constants.NO_DELETE_FLAG);
                
                // 设置创建人
                patientContact.setCreateby(((BaseDto) obj).getMessage().getServiceId());

                // 将已添加内容的联系人对象添加到集合中
                resList.add(patientContact);
            }
        }

        return resList;
    }

    /**
     * 获取需新增的证件对象集合
     * @param credentialList 需要添加的证件对象集合
     * @param patientSn 患者内部序列号
     * @param systemTime 系统当前时间
     * @return 证件对象集合
     */
    public List<PatientCredential> getCreatePatientCredential(
            List<Credential> credentialList, BigDecimal patientSn,
            Date systemTime ,Object obj)
    {
        List<PatientCredential> resList = null;

        if (credentialList != null && !credentialList.isEmpty())
        {
            resList = new ArrayList<PatientCredential>();

            // 循环设置相应证件信息
            for (Credential credential : credentialList)
            {
                // $Author :jin_peng
                // $Date : 2012/08/21 11:20$
                // [BUG]0009075 ADD BEGIN
                if (StringUtils.isEmpty(credential.getCredentialNo()))
                {
                    continue;
                }
                // [BUG]0009075 ADD END

                // 创建证件对象
                PatientCredential patientCredential = new PatientCredential();

                // 设置患者内部序列号
                patientCredential.setPatientSn(patientSn);

                // 设置证件类型
                patientCredential.setCredentialType(Constants.CREDENTIAL_TYPE_MAP.get(credential.getCredentialType()));

                // $Author :tong_meng
                // $Date : 2012/7/1 10:33$
                // [BUG]0007418 ADD BEGIN
                // 设置证件类型名称
                if(DictionaryCache.getDictionary(Constants.DOMAIN_CREDENTIAL_TYPE) != null){
	                patientCredential.setCredentialTypeName(DictionaryCache.getDictionary(
	                        Constants.DOMAIN_CREDENTIAL_TYPE).get(
	                        Constants.CREDENTIAL_TYPE_MAP.get(credential.getCredentialType())));
                }
                // [BUG]0007418 ADD END
                // 设置证件号
                patientCredential.setCredentialNo(credential.getCredentialNo());

                // 设置记录创建时间
                patientCredential.setCreateTime(systemTime);

                // 设置记录更新时间
                patientCredential.setUpdateTime(systemTime);

                // 设置新增记录时的更新回数
                patientCredential.setUpdateCount(Constants.INSERT_UPDATE_COUNT);

                // 设置新增记录时的删除标记
                patientCredential.setDeleteFlag(Constants.NO_DELETE_FLAG);
                
                // 设置创建人
                patientCredential.setCreateby(((BaseDto) obj).getMessage().getServiceId());

                // 将已添加内容的证件对象添加到集合中
                resList.add(patientCredential);
            }
        }

        return resList;
    }

    /**
     * 获取需新增的地址信息对象集合
     * @param addressList 需要添加的地址对象集合
     * @param patientSn 患者内部序列号
     * @param systemTime 系统当前时间 
     * @param patientDomain 患者域ID 
     * @return 地址对象集合
     */
    public List<PatientAddress> getCreatePatientAddress(
            List<Address> addressList, BigDecimal patientSn, Date systemTime, Object obj)
    {
        List<PatientAddress> resList = null;
        // $Author :jin_peng
        // $Date : 2012/08/31 10:20$
        // [BUG]0009283 ADD BEGIN
        String districtCode = null;
        String proCityAddress = null;
        String subDisNameOrFull = null;
        String zipCode = null;
        // [BUG]0009283 ADD END

        if (addressList != null && !addressList.isEmpty())
        {
            resList = new ArrayList<PatientAddress>();

            // 循环设置地址信息内容
            for (Address address : addressList)
            {
                // $Author :jin_peng
                // $Date : 2012/08/31 10:20$
                // [BUG]0009283 MODIFY BEGIN
                // 获取区县码
                districtCode = address.getDistrictCode();

                // 获取街道名称
                proCityAddress = address.getProCityAddress();

                // 获取所在地区完整住址
                subDisNameOrFull = address.getSubDisNameOrFull();

                // 获取邮政编码
                zipCode = address.getZipCode();

                // 如果相关地址信息都为空时，对应的该条记录不插入db
                if (StringUtils.isNullAll(districtCode, proCityAddress,
                        subDisNameOrFull, zipCode))
                {
                    continue;
                }

                // 创建地址信息对象
                PatientAddress patientAddress = new PatientAddress();

                // 设置患者内部序列号
                patientAddress.setPatientSn(patientSn);

                // 设置地址类型
                patientAddress.setAddressType(address.getAddressType());

                // 设置地址类型名称
                patientAddress.setAddressTypeName(Constants.ADDRESS_TYPE_MAP.get(address.getAddressType()));

                // $Author:wei_peng
                // $Date:2012/9/26 15:11
                // $[BUG]0009702 MODIFY BEGIN
                // 设置患者所在地区区县码
                patientAddress.setDistrictCode(districtCode);

                // $Author :tong_meng
                // $Date : 2012/7/3 10:00$
                // [BUG]0007418 ADD BEGIN
                // 设置患者所在地区区县名称
                if(DictionaryCache.getDictionary(Constants.DOMAIN_DISTRICT) != null){
                	patientAddress.setDistrictName(DictionaryCache.getDictionary(
                            Constants.DOMAIN_DISTRICT).get(districtCode));
                }
                
                // [BUG]0007418 ADD END

                // 设置街道名称
                patientAddress.setSubDistrictName(proCityAddress);
                // 当区县码不为空时，患者完整地址为区县码+街道名称
                if (districtCode != null && !districtCode.isEmpty())
                {
                	if(DictionaryCache.getDictionary( Constants.DOMAIN_DISTRICT) != null){
                		String districtName = DictionaryCache.getDictionary(
	                            Constants.DOMAIN_DISTRICT).get(districtCode);
	                    if (districtName != null)
	                    {
	                        // 设置患者所在地区完整住址
	                        patientAddress.setFullAddress(districtName
	                            + proCityAddress);
	                    }
                	}
                }
                // 当区县码为空时，患者完整地址为完整地址
                else
                {
                    // 设置患者所在地区完整住址
                    patientAddress.setFullAddress(subDisNameOrFull);
                }
                // $[BUG]0009702 MODIFY END

                // 设置邮政编码
                patientAddress.setZipCode(zipCode);
                // [BUG]0009283 MODIFY END

                // 设置记录创建时间
                patientAddress.setCreateTime(systemTime);

                // 设置记录更新时间
                patientAddress.setUpdateTime(systemTime);

                // 设置新增记录时的更新回数
                patientAddress.setUpdateCount(Constants.INSERT_UPDATE_COUNT);

                // 设置新增记录时的删除标记
                patientAddress.setDeleteFlag(Constants.NO_DELETE_FLAG);
                
                // 设置创建人
                patientAddress.setCreateby(((BaseDto) obj).getMessage().getServiceId());

                // 将已添加内容的地址信息对象添加到集合中
                resList.add(patientAddress);
            }
        }

        return resList;
    }

    /**
     * 从消息中获取患者工作地址
     * @param prpain201302uv01CommonDto 消息对象
     * @return 地址对象
     */
    public Address getWorkAddress(
            PRPAIN201302UV01CommonDto prpain201302uv01CommonDto)
    {
        // 创建工作地址对象
        Address workAddress = null;
        String workPlaceType = null;
        String workDistrictCode = null;
        String workProCityAddress = null;
        String workSubDisNameOrFull = null;
        String workZipCode = null;

        // 获取工作地址类型
        workPlaceType = prpain201302uv01CommonDto.getWorkPlaceType();

        // 获取工作地址区县码
        workDistrictCode = prpain201302uv01CommonDto.getWorkDistrictCode();

        // 获取工作地址省市名称
        workProCityAddress = prpain201302uv01CommonDto.getWorkProCityAddress();

        // 获取工作乡镇街道地址或完整地址
        workSubDisNameOrFull = prpain201302uv01CommonDto.getWorkSubDisNameOrFull();

        // 获取工作地址邮政编码
        workZipCode = prpain201302uv01CommonDto.getWorkZipCode();

        // 如果有工作地址内容不为空时再创建该地址记录
        if (!StringUtils.isNullAll(workDistrictCode, workProCityAddress,
                workSubDisNameOrFull, workZipCode))
        {
            workAddress = new Address();

            // 设置地址类型
            workAddress.setAddressType(workPlaceType);

            // 设置地址区县码
            workAddress.setDistrictCode(workDistrictCode);

            // 设置省市地址
            workAddress.setProCityAddress(workProCityAddress);

            // 设置乡镇街道地址或完整地址
            workAddress.setSubDisNameOrFull(workSubDisNameOrFull);

            // 设置地址邮政编码
            workAddress.setZipCode(workZipCode);
            
        }

        return workAddress;
    }

}
