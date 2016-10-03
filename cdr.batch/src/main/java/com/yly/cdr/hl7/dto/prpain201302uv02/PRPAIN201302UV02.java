package com.yly.cdr.hl7.dto.prpain201302uv02;

import java.util.List;

import org.hibernate.validator.constraints.NotEmpty;

import com.yly.cdr.batch.writer.prpain201302uv01.PRPAIN201302UV01CommonDto;
import com.yly.cdr.hl7.dto.Address;
import com.yly.cdr.hl7.dto.Contact;
import com.yly.cdr.hl7.dto.Credential;
import com.yly.cdr.hl7.dto.RelationshipPatient;

public class PRPAIN201302UV02 extends PRPAIN201302UV01CommonDto
{
    /**
     * 时间戳（版本标识）
     */
    @NotEmpty(message = "{PRPAIN201302UV02.timestamp}")
    private String timestamp;

    /**
     * 患者EMPI标识
     */
    @NotEmpty(message = "{PRPAIN201302UV02.patientEid}")
    private String patientEid;

    /**
     * 住院号
     */
    private String inpatientNo;

    /**
     * 门诊号
     */
    private String outpatientNo;

    /**
     * 域ID
     */
    private String patientDomain;

    /**
     * 患者ID
     */
    private String patientLid;

    /**
     * 地址
     */
    private List<Address> address;

    /**
     * 家庭电话
     */
    private String homePhone;

    /**
     * 移动电话
     */
    private String mobelPhone;

    /**
     * 电子邮件
     */
    private String email;

    /**
     * 证件
     */
    private List<Credential> credential;

    /**
     * 患者姓名
     */
    @NotEmpty(message = "{PRPAIN201302UV02.patientName}")
    private String patientName;

    /**
     * 性别代码
     */
    @NotEmpty(message = "{PRPAIN201302UV02.genderCode}")
    private String genderCode;

    /**
     * 出生日期
     */
    @NotEmpty(message = "{PRPAIN201302UV02.birthDate}")
    private String birthDate;

    /**
     * 婚姻状况类别代码
     */
    private String marriageStatus;

    /**
     * 文化程度代码
     */
    private String educationDegree;

    /**
     * 民族代码
     */
    private String nationCode;

    /**
     * 职业代码
     */
    private String occupationCode;

    /**
     * 工作单位名称
     */
    private String workPlaceName;

    /**
     * 工作单位地址类型
     */
    private String workPlaceType;

    /**
     * 工作单位地址区县码
     */
    private String workDistrictCode;

    /**
     * 工作单位省市地址
     */
    private String workProCityAddress;

    /**
     * 工作单位乡镇街道地址或完整地址
     */
    private String workSubDisNameOrFull;

    /**
     * 工作单位邮政编码
     */
    private String workZipCode;

    /**
     * 工作单位电话
     */
    private String workPhone;

    /**
     * 工作单位电子邮件
     */
    private String workEmail;

    /**
     * 国籍代码
     */
    private String nationalityCode;

    /**
     * 国家名称
     */
    private String nativeName;

    /**
     * 出生地
     */
    private String birthPlace;

    /**
     * 邮政编码
     */
    private String zipCode;

    /**
     * 联系人
     */
    private List<Contact> patientContact;

    /**
     * 关联患者信息
     */
    private List<RelationshipPatient> relationshipPatient;

    /**
     * 医保号
     */
    private String insuranceCardNo;

    /**
     * 影像号
     */
    private String imagingNo;
    
    public String getTimestamp()
    {
        return timestamp;
    }

    public String getPatientEid()
    {
        return patientEid;
    }

    public String getInpatientNo()
    {
        return inpatientNo;
    }

    public String getOutpatientNo()
    {
        return outpatientNo;
    }

    public String getPatientDomain()
    {
        return patientDomain;
    }

    public String getPatientLid()
    {
        return patientLid;
    }

    public List<Address> getAddress()
    {
        return address;
    }

    public String getHomePhone()
    {
        return homePhone;
    }

    public String getMobelPhone()
    {
        return mobelPhone;
    }

    public String getEmail()
    {
        return email;
    }

    public List<Credential> getCredential()
    {
        return credential;
    }

    public String getPatientName()
    {
        return patientName;
    }

    public String getGenderCode()
    {
        return genderCode;
    }

    public String getBirthDate()
    {
        return birthDate;
    }

    public String getMarriageStatus()
    {
        return marriageStatus;
    }

    public String getEducationDegree()
    {
        return educationDegree;
    }

    public String getNationCode()
    {
        return nationCode;
    }

    public String getOccupationCode()
    {
        return occupationCode;
    }

    public String getWorkPlaceName()
    {
        return workPlaceName;
    }

    public String getWorkPlaceType()
    {
        return workPlaceType;
    }

    public String getWorkDistrictCode()
    {
        return workDistrictCode;
    }

    public String getWorkProCityAddress()
    {
        return workProCityAddress;
    }

    public String getWorkSubDisNameOrFull()
    {
        return workSubDisNameOrFull;
    }

    public String getWorkZipCode()
    {
        return workZipCode;
    }

    public String getWorkPhone()
    {
        return workPhone;
    }

    public String getWorkEmail()
    {
        return workEmail;
    }

    public String getNationalityCode()
    {
        return nationalityCode;
    }

    public String getNativeName()
    {
        return nativeName;
    }

    public String getBirthPlace()
    {
        return birthPlace;
    }

    public String getZipCode()
    {
        return zipCode;
    }

    public List<Contact> getPatientContact()
    {
        return patientContact;
    }

    public List<RelationshipPatient> getRelationshipPatient()
    {
        return relationshipPatient;
    }

    public String getInsuranceCardNo()
    {
        return insuranceCardNo;
    }

    public String getImagingNo()
    {
        return imagingNo;
    }
    
}
