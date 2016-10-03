package com.yly.cdr.service;

import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Transactional;

import com.yly.cdr.dto.AccessControlDto;
import com.yly.cdr.dto.SystemSettingDto;
import com.yly.cdr.dto.codeValue.HideDrugDto;
import com.yly.cdr.entity.CodeDrug;
import com.yly.cdr.entity.HideDrug;
import com.yly.cdr.entity.SystemMenu;

public interface SystemService
{
    @Transactional
    SystemSettingDto getSystemSetting(String userSn);

    @Transactional
    List<SystemMenu> getAllSystemMenu();

    // $Author:wu_jianfeng
    // $Date : 2012/09/12 14:10
    // $[BUG]0009852 ADD BEGIN
    // 访问控制实现
    List<SystemMenu> getSystemMenus(List<String> menuCodes);

    List<SystemMenu> getSystemMenusByACL(List<SystemMenu> systemMenus,
            AccessControlDto aclAuths);

    // $[BUG]0009852 ADD END

    // 得到系统所有的菜单
    @Transactional
    List<SystemMenu> getSystemMenusByCondition(Map<String, Object> condition);

    // $Author:wu_jianfeng
    // $Date : 2012/12/21 14:10
    // $[BUG]0012967 MODIFY BEGIN
    @Transactional
    void saveSystemSetting(String saveAction, SystemSettingDto systemSettingDto);
    // $[BUG]0012967 MODIFY END
    
    /**
     * 保存隐藏药物表
     * @param addCodeDrugIds
     * @param userSn
     */
    @Transactional
    void saveHideDrug(String[] addCodeDrugIds,String userSn);
    
    /**
     * 根据名称查询隐藏的药物信息
     * @param userSn
     * @return
     */
    @Transactional
    List<HideDrugDto> selectHideDrugByUserName(String userSn);

    /**
     * 根据序列号和药物编码删除该药物
     * @param cvCode
     */
    @Transactional
    void deleteHideDrug(String userSn, String[] deleteCodeDrugIds);

    /**
     * 判断是否在系统管理员组中。
     * */
    @Transactional
    public boolean isSystemAdmin(String userId);
}
