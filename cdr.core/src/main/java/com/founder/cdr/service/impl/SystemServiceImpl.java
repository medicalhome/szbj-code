package com.founder.cdr.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javassist.bytecode.stackmap.TypeData.NullType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.founder.cdr.cache.DictionaryCache;
import com.founder.cdr.core.Constants;
import com.founder.cdr.dao.SystemDao;
import com.founder.cdr.dto.AccessControlDto;
import com.founder.cdr.dto.DictionaryDto;
import com.founder.cdr.dto.SystemSettingDto;
import com.founder.cdr.dto.codeValue.HideDrugDto;
import com.founder.cdr.entity.CodeDrug;
import com.founder.cdr.entity.HideDrug;
import com.founder.cdr.entity.SystemAccount;
import com.founder.cdr.entity.SystemMenu;
import com.founder.cdr.entity.SystemSetting;
import com.founder.cdr.service.CommonService;
import com.founder.cdr.service.SystemService;
import com.founder.cdr.util.DateUtils;
import com.founder.cdr.util.StringUtils;
import com.founder.fasf.core.util.daohelper.entity.EntityDao;

@Component
public class SystemServiceImpl implements SystemService
{
    @Autowired
    private EntityDao entityDao;

    @Autowired
    private SystemDao systemDao;
    
    @Autowired
    private CommonService commonService;

    @Transactional
    public SystemSettingDto getSystemSetting(String userSn)
    {
        SystemSettingDto systemSetting = systemDao.selectSystemSetting(userSn);
        if (systemSetting == null)
            systemSetting = new SystemSettingDto();
        return systemSetting;
    }

    // $Author:wu_jianfeng
    // $Date : 2012/12/21 14:10
    // $[BUG]0012967 MODIFY BEGIN
    @Transactional
    public void saveSystemSetting(String saveAction,
            SystemSettingDto systemSettingDto)
    {
        Map<String, Object> condition = new HashMap<String, Object>();
        condition.put("userSn", systemSettingDto.getUserSn());
        condition.put("deleteFlag", Constants.NO_DELETE_FLAG);

        Boolean isNewRecord = false;
        Date systemTime = DateUtils.getSystemTime();
        SystemSetting systemSetting = new SystemSetting();

        List<Object> systemSettings = entityDao.selectByCondition(
                SystemSetting.class, condition);
        if (systemSettings == null || systemSettings.size() == 0)
            isNewRecord = true;
        else
            systemSetting = (SystemSetting) systemSettings.get(0);

        if (saveAction.equals("menu") || saveAction.equals("all"))
            systemSetting.setDisplayMenus(systemSettingDto.getDisplayMenus());
        if (saveAction.equals("view") || saveAction.equals("all"))
        {
            //systemSetting.setVisitIndexViews(systemSettingDto.getVisitIndexViews());
            systemSetting.setOutpatientViews(systemSettingDto.getOutpatientViews());
            systemSetting.setInpatientViews(systemSettingDto.getInpatientViews());
            systemSetting.setTimerViews(systemSettingDto.getTimerViews());
            systemSetting.setNormalViews(systemSettingDto.getNormalViews());
            // Author:jia_yanqing
            // Date : 2013/1/11 10:30
            // [BUG]0012699 ADD BEGIN
            systemSetting.setRowsPerPage(systemSettingDto.getRowsPerPage());
            // [BUG]0012699 ADD END

            // $Author :jin_peng
            // $Date : 2013/12/19 09:51$
            // [BUG]0040598 ADD BEGIN
            systemSetting.setRowsPerPageLab(systemSettingDto.getRowsPerPageLab());

            // [BUG]0040598 ADD END
        }

        // 新增记录
        if (isNewRecord)
        {
            systemSetting.setUserSn(systemSettingDto.getUserSn());
            // 创建时间
            systemSetting.setCreateTime(systemTime);
            // 更新时间
            systemSetting.setUpdateTime(systemTime);
            // 更新回数
            systemSetting.setUpdateCount(Constants.INSERT_UPDATE_COUNT);
            // 删除标识
            systemSetting.setDeleteFlag(Constants.NO_DELETE_FLAG);

            entityDao.insert(systemSetting);
        }
        // 更新记录
        else
        {
            // 更新时间
            systemSetting.setUpdateTime(systemTime);
            entityDao.update(systemSetting);
        }
    }

    // $[BUG]0012967 MODIFY BEGIN

    @Override
    @Transactional
    public List<SystemMenu> getAllSystemMenu()
    {
        List systemMenus = new ArrayList<SystemMenu>();
        systemMenus = systemDao.selectAllSystemMenu();
        return systemMenus;
    }

    @Override
    @Transactional
    public List<SystemMenu> getSystemMenusByCondition(
            Map<String, Object> condition)
    {
        List systemMenus = entityDao.selectByCondition(SystemMenu.class,
                condition);
        return systemMenus;
    }

    // $Author:wu_jianfeng
    // $Date : 2012/09/12 14:10
    // $[BUG]0009852 ADD BEGIN
    // 访问控制实现
    @Override
    @Transactional
    public List<SystemMenu> getSystemMenus(List<String> menuCodes)
    {
        List systemMenus = new ArrayList<SystemMenu>();
        systemMenus = systemDao.selectSystemMenus(menuCodes);
        return systemMenus;
    }

    @Override
    public List<SystemMenu> getSystemMenusByACL(List<SystemMenu> systemMenus,
            AccessControlDto aclAuths)
    {
        List<SystemMenu> menus = new ArrayList<SystemMenu>();

        // 根据访问控制权限，从所有的功能菜单中筛选
        for (SystemMenu systemMenu : systemMenus)
        {
            String authsStr = systemMenu.getAuths();
            // 非权限控制之外的功能菜单都显示
            if (authsStr == null || authsStr.trim().isEmpty())
                menus.add(systemMenu);
            else
            {
                // 一个菜单可能对应多个权限，只要有一个权限，该菜单应该显示
                for (String auth : authsStr.split(","))
                {
                    // 根据诊断信息权限，判断显示诊断功能菜单
                    if (Constants.ACL_CLINICAL_INFO_01.equals(auth))
                    {
                        if (aclAuths.getClinicalInfoAuth01())
                        {
                            menus.add(systemMenu);
                            break;
                        }
                    }
                    // 根据药物医嘱信息权限，判断显示药物医嘱功能菜单
                    else if (Constants.ACL_CLINICAL_INFO_02.equals(auth))
                    {
                        if (aclAuths.getClinicalInfoAuth02())
                        {
                            menus.add(systemMenu);
                            break;
                        }
                    }
                    // 根据非药物医嘱信息权限，判断显示非药物医嘱功能菜单
                    else if (Constants.ACL_CLINICAL_INFO_03.equals(auth))
                    {
                        if (aclAuths.getClinicalInfoAuth03())
                        {
                            menus.add(systemMenu);
                            break;
                        }
                    }
                    // 根据手术信息权限，判断显示手术功能菜单
                    else if (Constants.ACL_CLINICAL_INFO_04.equals(auth))
                    {
                        if (aclAuths.getClinicalInfoAuth04())
                        {
                            menus.add(systemMenu);
                            break;
                        }
                    }
                    // 根据检验信息权限，判断显示检验功能菜单
                    else if (Constants.ACL_CLINICAL_INFO_05.equals(auth))
                    {
                        if (aclAuths.getClinicalInfoAuth05())
                        {
                            menus.add(systemMenu);
                            break;
                        }
                    }
                    // 根据检查信息权限，判断显示检查功能菜单
                    else if (Constants.ACL_CLINICAL_INFO_06.equals(auth))
                    {
                        if (aclAuths.getClinicalInfoAuth06())
                        {
                            menus.add(systemMenu);
                            break;
                        }
                    }
                    // 根据病历信息权限，判断显示病历功能菜单
                    else if (Constants.ACL_CLINICAL_INFO_07.equals(auth))
                    {
                        if (aclAuths.getClinicalInfoAuth07())
                        {
                            menus.add(systemMenu);
                            break;
                        }
                    }
                }
            }
        }

        //
        return menus;
    }

    // $[BUG]0009852 ADD END

    @Override
    @Transactional
    public void saveHideDrug(String[] addCodeDrugIds, String userSn)
    {
        Map<String, DictionaryDto> map = DictionaryCache.getFullDictionary(Constants.DOMAIN_DRUG_DICTIONARY);
        List<HideDrug> addHideDrugs = new ArrayList<HideDrug>();
        HideDrug hideDrug;
        // 设置增加hideDrug
        for (String codeDrugId : addCodeDrugIds)
        {
            DictionaryDto dictionaryDto = map.get(codeDrugId);
            String cvCode = null;
            String drugName = null;
            if (dictionaryDto != null)
            {
                // 得到药品编码和包装序列号
                cvCode = dictionaryDto.getCvCode();
                // 药品名称
                drugName = dictionaryDto.getCvValue();
            }
            String drugCode = null;
            String serialNo = null;
            if (!StringUtils.isEmpty(cvCode) && cvCode.length() > 2)
            {
                // $Author: tong_meng
                // $Date : 2013/05/23 14:40
                // $[BUG]0014990 DELETE BEGIN
                // 查找隐藏药物表中的信息
                /*
                 * serialNo = cvCode.substring(cvCode.length() - 2,
                 * cvCode.length());
                 */
                // $[BUG]0014990 DELETE END

                drugCode = cvCode.substring(0, cvCode.length() - 2);
            }
            Date systemDate = DateUtils.getSystemTime();
            hideDrug = new HideDrug();
            hideDrug.setUserName(userSn);
            hideDrug.setDrugCode(drugCode);
            hideDrug.setSerialNo(serialNo);
            hideDrug.setDrugName(drugName);
            hideDrug.setCreateTime(systemDate);
            hideDrug.setUpdateCount(Constants.INSERT_UPDATE_COUNT);
            hideDrug.setDeleteFlag(Constants.NO_DELETE_FLAG);
            hideDrug.setUpdateTime(systemDate);
            addHideDrugs.add(hideDrug);
        }
        commonService.saveList(addHideDrugs);
    }

    @Override
    @Transactional
    public List<HideDrugDto> selectHideDrugByUserName(String userSn)
    {
        return systemDao.selectHideDrugByUserName(userSn);
    }

    @Override
    @Transactional
    public void deleteHideDrug(String userSn, String[] deleteCodeDrugIds)
    {
        systemDao.deleteHideDrug(userSn, deleteCodeDrugIds);
    }

	@Override
	public boolean isSystemAdmin(String userId) {
		if(userId == null || userId.isEmpty())
		return false;
		else {
			Integer result = systemDao.checkSystemAdmin(userId);
			if(result == null || result.equals(0)) return false;
			else return true;
		}
	}
}
