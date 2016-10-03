package com.yly.cdr.dao;

import java.util.List;

import com.founder.fasf.core.util.daohelper.annotation.Arguments;
import com.yly.cdr.dto.SystemSettingDto;
import com.yly.cdr.dto.codeValue.HideDrugDto;
import com.yly.cdr.entity.CodeDrug;
import com.yly.cdr.entity.SystemMenu;

public interface SystemDao
{

    public List<SystemMenu> selectAllSystemMenu();

    // $Author:wu_jianfeng
    // $Date : 2012/09/12 14:10
    // $[BUG]0009852 ADD BEGIN
    // 访问控制实现
    @Arguments({ "menuCodes" })
    public List<SystemMenu> selectSystemMenus(List<String> menuCodes);

    // $[BUG]0009852 ADD END

    @Arguments({ "userSn" })
    public SystemSettingDto selectSystemSetting(String patientSn);

    @Arguments({ "codeDrugId" })
    public List<CodeDrug> selectCodeDrug(List<String> codeDrugId);

    /**
     * 根据序列号和药品code删除药品
     * @param serialNo
     * @param drugCode
     */
    @Arguments({ "userSn" , "deleteCodeDrugIds"})
    public void deleteHideDrug(String userSn,
            String[] deleteCodeDrugIds);

    @Arguments({ "userSn" })
    public List<HideDrugDto> selectHideDrugByUserName(String userSn);

    /**
     * 判断是否在系统管理员组中。
     * */
    @Arguments({"userId"})
    public Integer checkSystemAdmin(String userId);
}
