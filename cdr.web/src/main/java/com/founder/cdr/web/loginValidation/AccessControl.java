package com.founder.cdr.web.loginValidation;

import java.util.List;

import com.founder.cdr.core.Constants;
import com.founder.cdr.dto.AccessControlDto;

//$Author:wu_jianfeng
//$Date : 2012/09/12 14:10
//$[BUG]0009663 ADD BEGIN
// 访问控制实现

public class AccessControl 
{
    private List<String> auths;

    private AccessControlDto accessControlAuths;

    public AccessControl(List<String> userAuths)
    {
        this.auths = userAuths;
        initAccessControlAuths();
    }

    /**
     * 是否有权限
     * @param authId
     * @return
     */
    private boolean hasAuth(String authId)
    {
        if (auths.indexOf(authId) != -1)
            return true;
        return false;
    }

    private void initAccessControlAuths()
    {
        accessControlAuths = new AccessControlDto();
        accessControlAuths.setPatientScopeAuth01(hasAuth(Constants.ACL_PATIENT_SCOPE_01));
        accessControlAuths.setPatientScopeAuth02(hasAuth(Constants.ACL_PATIENT_SCOPE_02));
        accessControlAuths.setPatientScopeAuth03(hasAuth(Constants.ACL_PATIENT_SCOPE_03));
        accessControlAuths.setPatientScopeAuth04(hasAuth(Constants.ACL_PATIENT_SCOPE_04));
        accessControlAuths.setPatientScopeAuth05(hasAuth(Constants.ACL_PATIENT_SCOPE_05));
        accessControlAuths.setPatientScopeAuth06(hasAuth(Constants.ACL_PATIENT_SCOPE_06));
        accessControlAuths.setPatientScopeAuth07(hasAuth(Constants.ACL_PATIENT_SCOPE_07));

        accessControlAuths.setPatientInfoAuth01(hasAuth(Constants.ACL_PATIENT_INFO_01));
        accessControlAuths.setPatientInfoAuth02(hasAuth(Constants.ACL_PATIENT_INFO_02));
        accessControlAuths.setPatientInfoAuth03(hasAuth(Constants.ACL_PATIENT_INFO_03));
        accessControlAuths.setPatientInfoAuth04(hasAuth(Constants.ACL_PATIENT_INFO_04));
        accessControlAuths.setPatientInfoAuth05(hasAuth(Constants.ACL_PATIENT_INFO_05));
        accessControlAuths.setPatientInfoAuth06(hasAuth(Constants.ACL_PATIENT_INFO_06));

        accessControlAuths.setClinicalInfoAuth01(hasAuth(Constants.ACL_CLINICAL_INFO_01));
        accessControlAuths.setClinicalInfoAuth02(hasAuth(Constants.ACL_CLINICAL_INFO_02));
        accessControlAuths.setClinicalInfoAuth03(hasAuth(Constants.ACL_CLINICAL_INFO_03));
        accessControlAuths.setClinicalInfoAuth04(hasAuth(Constants.ACL_CLINICAL_INFO_04));
        accessControlAuths.setClinicalInfoAuth05(hasAuth(Constants.ACL_CLINICAL_INFO_05));
        accessControlAuths.setClinicalInfoAuth06(hasAuth(Constants.ACL_CLINICAL_INFO_06));
        accessControlAuths.setClinicalInfoAuth07(hasAuth(Constants.ACL_CLINICAL_INFO_07));

        // 如果允许访问全院患者，其他的访问控制不适用
        if (accessControlAuths.getPatientScopeAuth05())
        {
            accessControlAuths.setPatientScopeAuth01(false);
            accessControlAuths.setPatientScopeAuth02(false);
            accessControlAuths.setPatientScopeAuth03(false);
            accessControlAuths.setPatientScopeAuth04(false);
            accessControlAuths.setPatientScopeAuth06(false);
            accessControlAuths.setPatientScopeAuth07(false);
        }
        // if (accessControlAuths.getPatientScopeAuth05())
    }

    /**
     * 是否有患者范围权限
     * @return
     */
    public boolean hasPatientScopeAuths()
    {
        boolean hasAuth = accessControlAuths.getPatientScopeAuth01()
            || accessControlAuths.getPatientScopeAuth02()
            || accessControlAuths.getPatientScopeAuth03()
            || accessControlAuths.getPatientScopeAuth04()
            || accessControlAuths.getPatientScopeAuth05()
            || accessControlAuths.getPatientScopeAuth06()
            || accessControlAuths.getPatientScopeAuth07();
        return hasAuth;
    }

    /**
     * 是否有有患者信息权限
     * @return
     */
    public boolean hasPatientInfoAuths()
    {
        boolean hasAuth = accessControlAuths.getPatientInfoAuth01()
            || accessControlAuths.getPatientInfoAuth02()
            || accessControlAuths.getPatientInfoAuth03()
            || accessControlAuths.getPatientInfoAuth04()
            || accessControlAuths.getPatientInfoAuth05()
            || accessControlAuths.getPatientInfoAuth06();
        return hasAuth;
    }

    /**
     * 是否有有临床信息权限
     * @return
     */
    public boolean hasClinicalInfoAuths()
    {
        boolean hasAuth = accessControlAuths.getClinicalInfoAuth01()
            || accessControlAuths.getClinicalInfoAuth02()
            || accessControlAuths.getClinicalInfoAuth03()
            || accessControlAuths.getClinicalInfoAuth04()
            || accessControlAuths.getClinicalInfoAuth05()
            || accessControlAuths.getClinicalInfoAuth06()
            || accessControlAuths.getClinicalInfoAuth07();
        return hasAuth;
    }

    public static boolean useACL()
    {
        if (Constants.ACL_USE.equals(Constants.ACL_USE_OPEN))
            return true;
        return false;
    }

    public AccessControlDto getAccessControlAuths()
    {
        return accessControlAuths;
    }
}

// $[BUG]0009663 ADD END
