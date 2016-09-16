/**
 * 术语定义实现类
 */
package com.founder.cdr.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.founder.cdr.core.Constants;
import com.founder.cdr.dto.CodeValueDto;
import com.founder.cdr.entity.CodeChargeItem;
import com.founder.cdr.entity.CodeDepartment;
import com.founder.cdr.entity.CodeDrug;
import com.founder.cdr.entity.CodeExamItem;
import com.founder.cdr.entity.CodeExamItemGroup;
import com.founder.cdr.entity.CodeIcd;
import com.founder.cdr.entity.CodeLabItem;
import com.founder.cdr.entity.CodeLabSubitem;
import com.founder.cdr.entity.CodeMedicalName;
import com.founder.cdr.entity.CodeOperation;
import com.founder.cdr.entity.CodeOrderItem;
import com.founder.cdr.entity.CodePerson;
import com.founder.cdr.entity.CodeSystem;
import com.founder.cdr.entity.CodeValue;
import com.founder.cdr.entity.CodeWard;
import com.founder.cdr.service.CommonService;
import com.founder.cdr.service.DictionaryService;
import com.founder.cdr.util.StringUtils;
import com.founder.fasf.core.util.daohelper.entity.EntityDao;

@Component
public class DictionaryServiceImpl implements DictionaryService
{
    @Autowired
    private CommonService commonService;

    @Autowired
    private EntityDao entityDao;

    // CODE_VALUE 表
    private static final String CODE_VALUE = "CODE_VALUE";

    // CODE_LAB_ITEM 表
    private static final String CODE_LAB_ITEM = "CODE_LAB_ITEM";

    // CODE_LAB_SUBITEM 表
    private static final String CODE_LAB_SUBITEM = "CODE_LAB_SUBITEM";

    // CODE_WARD 表
    private static final String CODE_WARD = "CODE_WARD";

    // CODE_EXAM_ITEM_GROUP 表
    private static final String CODE_EXAM_ITEM_GROUP = "CODE_EXAM_ITEM_GROUP";

    // CODE_EXAM_ITEM 表
    private static final String CODE_EXAM_ITEM = "CODE_EXAM_ITEM";

    // CODE_DRUG 表
    private static final String CODE_DRUG = "CODE_DRUG";

    // CODE_DEPARTMENT表
    private static final String CODE_DEPARTMENT = "CODE_DEPARTMENT";

    // CODE_PERSON 表
    private static final String CODE_PERSON = "CODE_PERSON";

    // CODE_ICD 表
    private static final String CODE_ICD = "CODE_ICD";
    
    // CODE_OPERATION 表
    private static final String CODE_OPERATION = "CODE_OPERATION";

    // CODE_MEDICAL_NAME 表
    private static final String CODE_MEDICAL_NAME = "CODE_MEDICAL_NAME";
    
    // $Author :tong_meng
    // $Date : 2013/1/16 14:00$
    // [BUG]0013110 MODIFY BEGIN
    private static final String CODE_CHARGE_ITEM = "CODE_CHARGE_ITEM";
    // [BUG]0013110 MODIFY END

    @Override
    @Transactional
    public void saveAllList(List<Object> insertList, List<Object> updateList,
            List<Object> deleteList, CodeSystem codeSystem)
    {
        if (codeSystem != null)
            commonService.update(codeSystem);
        saveInserList(insertList);
        deleteDeleteList(deleteList);
        updateUpdateList(updateList);
    }

    @Transactional
    private void deleteDeleteList(List<Object> deleteList)
    {
        for (Object codeValue : deleteList)
        {
            commonService.update(codeValue);
        }
    }

    @Transactional
    private void updateUpdateList(List<Object> updateList)
    {
        for (Object codeValue : updateList)
            commonService.update(codeValue);
    }

    @Transactional
    private void saveInserList(List<Object> insertList)
    {
        for (Object codeValue : insertList)
            commonService.save(codeValue);
    }

    @Override
    @Transactional
    public List<CodeSystem> selectCodeSystems(String csCode)
    {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("csCode", csCode);
        map.put("deleteFlag", Constants.NO_DELETE_FLAG);
        List result = new ArrayList<CodeSystem>();
        result = entityDao.selectByCondition(CodeSystem.class, map);
        return result;
    }

    @Override
    @Transactional
    public CodeValue getCVList(String code, BigDecimal csId, String messageId)
    {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("cvCode", code);
        map.put("csId", StringUtils.BigDecimalToStr(csId));
        map.put("deleteFlag", "0");
        List result = new ArrayList<CodeValue>();
        result = entityDao.selectByCondition(CodeValue.class, map);
        if (result == null || result.size() == 0)
            return null;
        return (CodeValue) result.get(0);
    }

    @Override
    @Transactional
    public CodeWard getCWList(String code, BigDecimal csId, String name)
    {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("code", code);
        if (!StringUtils.isEmpty(name))
            // $Author :tong_meng
            // $Date : 2012/11/05 11:00$
            // [BUG]0011055 MODIFY BEGIN
            map.put("deptSn", name.trim());
        // [BUG]0011055 MODIFY END
        map.put("csId", StringUtils.BigDecimalToStr(csId));
        List result = new ArrayList<CodeWard>();
        result = entityDao.selectByCondition(CodeWard.class, map);
        if (result == null || result.size() == 0)
            return null;
        return (CodeWard) result.get(0);
    }

    @Override
    @Transactional
    public CodeExamItemGroup getCEIPList(String code, BigDecimal csId)
    {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("code", code);
        map.put("csId", StringUtils.BigDecimalToStr(csId));
        List result = new ArrayList<CodeExamItemGroup>();
        result = entityDao.selectByCondition(CodeExamItemGroup.class, map);
        if (result == null || result.size() == 0)
            return null;
        return (CodeExamItemGroup) result.get(0);
    }

    @Override
    @Transactional
    public CodeIcd getCIList(String code, BigDecimal csId)
    {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("code", code);
        map.put("csId", StringUtils.BigDecimalToStr(csId));
        List result = new ArrayList<CodeIcd>();
        result = entityDao.selectByCondition(CodeIcd.class, map);
        if (result == null || result.size() == 0)
            return null;
        return (CodeIcd) result.get(0);
    }
    
    // Author :jia_yanqing
    // Date : 2013/10/14 10:00
    // [BUG]0037993 ADD BEGIN
    /**
     * 查询手术-操作字典
     */
    @Override
    @Transactional
    public CodeOperation getCOList(String code, BigDecimal csId)
    {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("code", code);
        map.put("csId", StringUtils.BigDecimalToStr(csId));
        List result = new ArrayList<CodeOperation>();
        result = entityDao.selectByCondition(CodeOperation.class, map);
        if (result == null || result.size() == 0)
            return null;
        return (CodeOperation) result.get(0);
    }
    // [BUG]0037993 ADD END

    /**
     * 查询药品名称字典
     */
    @Override
    @Transactional
    public CodeMedicalName getCMNList(String code, String name)
    {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("code", code);
        map.put("name", name);
        List result = new ArrayList<CodeMedicalName>();
        result = entityDao.selectByCondition(CodeMedicalName.class, map);
        if (result == null || result.size() == 0)
            return null;
        return (CodeMedicalName) result.get(0);
    }

    @Override
    @Transactional
    public CodeExamItem getCEIList(String code, BigDecimal csId)
    {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("code", code);
        map.put("csId", StringUtils.BigDecimalToStr(csId));
        List result = new ArrayList<CodeIcd>();
        result = entityDao.selectByCondition(CodeExamItem.class, map);
        if (result == null || result.size() == 0)
            return null;
        return (CodeExamItem) result.get(0);
    }

    @Override
    @Transactional
    public CodeLabItem getLIList(String code, BigDecimal csId)
    {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("code", code);
        map.put("csId", StringUtils.BigDecimalToStr(csId));
        List result = new ArrayList<CodeLabItem>();
        result = entityDao.selectByCondition(CodeLabItem.class, map);
        if (result == null || result.size() == 0)
            return null;
        return (CodeLabItem) result.get(0);
    }

    @Override
    @Transactional
    public CodeLabSubitem getLSIList(String code, BigDecimal csId)
    {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("code", code);
        map.put("csId", StringUtils.BigDecimalToStr(csId));
        List result = new ArrayList<CodeLabSubitem>();
        result = entityDao.selectByCondition(CodeLabSubitem.class, map);
        if (result == null || result.size() == 0)
            return null;
        return (CodeLabSubitem) result.get(0);
    }

    @Override
    @Transactional
    public CodeDepartment getCDIPList(String code, BigDecimal csId)
    {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("code", code);
        map.put("csId", StringUtils.BigDecimalToStr(csId));
        List result = new ArrayList<CodeDepartment>();
        result = entityDao.selectByCondition(CodeDepartment.class, map);
        if (result == null || result.size() == 0)
            return null;
        return (CodeDepartment) result.get(0);
    }

    @Override
    @Transactional
    public CodeDrug getCDList(String code, BigDecimal csId, String Serial)
    {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("code", code);
        // Author: yu_yzh
        // Date: 2016/3/23 16:30
        // 港大消息中无序列号，取消药品序列号检索 MODIFY BEGIN
//        map.put("serial", Serial);
        // MODIFY END
        map.put("csId", StringUtils.BigDecimalToStr(csId));
        List result = new ArrayList<CodeDrug>();
        result = entityDao.selectByCondition(CodeDrug.class, map);
        if (result == null || result.size() == 0)
            return null;
        return (CodeDrug) result.get(0);
    }

    @Override
    @Transactional
    public CodePerson getCPList(String code, BigDecimal csId)
    {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("code", code);
        map.put("csId", StringUtils.BigDecimalToStr(csId));
        List result = new ArrayList<CodePerson>();
        result = entityDao.selectByCondition(CodePerson.class, map);
        if (result == null || result.size() == 0)
            return null;
        return (CodePerson) result.get(0);
    }

    @Override
    @Transactional
    public CodeMedicalName selectCodeMedicalName(String drugId)
    {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("code", drugId);
//        map.put("flag", "1");
        map.put("deleteFlag", Constants.NO_DELETE_FLAG);
        List result = new ArrayList<CodeMedicalName>();
        result = entityDao.selectByCondition(CodeMedicalName.class, map);
        if (result == null || result.size() == 0)
            return null;
        return (CodeMedicalName) result.get(0);
    }
    
    // $Author :tong_meng
    // $Date : 2013/1/16 14:00$
    // [BUG]0013110 MODIFY BEGIN
    @Override
    @Transactional
    public CodeChargeItem getCHIList(String itemCode, BigDecimal csId)
    {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("itemCode", itemCode);
        map.put("csId", csId);
        List result = new ArrayList<CodeChargeItem>();
        result = entityDao.selectByCondition(CodeChargeItem.class, map);
        if (result == null || result.size() == 0)
            return null;
        return (CodeChargeItem) result.get(0);
    }
    // [BUG]0013110 MODIFY END

    // $Author :tong_meng
    // $Date : 2012/11/05 11:00$
    // [BUG]0011055 MODIFY BEGIN
    // $Author :tong_meng
    // $Date : 2012/10/25 15:00$
    // [BUG]0010742 MODIFY BEGIN
    @Override
    public CodeValueDto selectRecordExistByTableName(String tableName,
            String code, BigDecimal csId, String messageId, String foreignKey)
    {
        CodeValueDto codeValueDto = null;
        if (CODE_VALUE.equals(tableName))
        {
            CodeValue codeValue = getCVList(code, csId, messageId);
            if (codeValue != null)
                codeValueDto = getCodeValueDto(codeValue.getDeleteFlag(),
                        codeValue.getVersionNo(), codeValue.getUpdateTime());
        }
        else if (CODE_LAB_ITEM.equals(tableName))
        {
            CodeLabItem codeLabItem = getLIList(code, csId);
            if (codeLabItem != null)
                codeValueDto = getCodeValueDto(codeLabItem.getDeleteFlag(),
                        codeLabItem.getVersionNo(), codeLabItem.getUpdateTime());
        }
        else if (CODE_LAB_SUBITEM.equals(tableName))
        {
            CodeLabSubitem codeLabSubitem = getLSIList(code, csId);
            if (codeLabSubitem != null)
                codeValueDto = getCodeValueDto(codeLabSubitem.getDeleteFlag(),
                        codeLabSubitem.getVersionNo(), codeLabSubitem.getUpdateTime());
        }
        else if (CODE_WARD.equals(tableName))
        {
            CodeWard codeWard = getCWList(code, csId, foreignKey);
            if (codeWard != null)
                codeValueDto = getCodeValueDto(codeWard.getDeleteFlag(),
                        codeWard.getVersionNo(), codeWard.getUpdateTime());
        }
        else if (CODE_EXAM_ITEM_GROUP.equals(tableName))
        {
            CodeExamItemGroup codeExamItemGroup = getCEIPList(code, csId);
            if (codeExamItemGroup != null)
                codeValueDto = getCodeValueDto(
                        codeExamItemGroup.getDeleteFlag(),
                        codeExamItemGroup.getVersionNo(), codeExamItemGroup.getUpdateTime());
        }
        else if (CODE_EXAM_ITEM.equals(tableName))
        {
            CodeExamItem codeExamItem = getCEIList(code, csId);
            if (codeExamItem != null)
                codeValueDto = getCodeValueDto(codeExamItem.getDeleteFlag(),
                        codeExamItem.getVersionNo(), codeExamItem.getUpdateTime());
        }
        else if (CODE_DRUG.equals(tableName))
        {
            CodeDrug codeDrug = getCDList(code, csId, foreignKey);
            if (codeDrug != null)
                codeValueDto = getCodeValueDto(codeDrug.getDeleteFlag(),
                        codeDrug.getVersionNo(), codeDrug.getUpdateTime());
        }
        else if (CODE_DEPARTMENT.equals(tableName))
        {
            CodeDepartment codeDepartment = getCDIPList(code, csId);
            if (codeDepartment != null)
                codeValueDto = getCodeValueDto(codeDepartment.getDeleteFlag(),
                        codeDepartment.getVersionNo(), codeDepartment.getUpdateTime());
        }
        else if (CODE_PERSON.equals(tableName))
        {
            CodePerson codePerson = getCPList(code, csId);
            if (codePerson != null)
                codeValueDto = getCodeValueDto(codePerson.getDeleteFlag(),
                        codePerson.getVersionNo(), codePerson.getUpdateTime());
        }
        else if (CODE_ICD.equals(tableName))
        {
            CodeIcd codeIcd = getCIList(code, csId);
            if (codeIcd != null)
                codeValueDto = getCodeValueDto(codeIcd.getDeleteFlag(),
                        codeIcd.getVersionNo(), codeIcd.getUpdateTime());
        }
        
        // Author :jia_yanqing
        // Date : 2013/10/14 14:00
        // [BUG]0037993 ADD BEGIN
        else if (CODE_OPERATION.equals(tableName))
        {
            CodeOperation codeOperation = getCOList(code, csId);
            if (codeOperation != null)
                codeValueDto = getCodeValueDto(codeOperation.getDeleteFlag(),
                        codeOperation.getVersionNo(), codeOperation.getUpdateTime());
        }
        // [BUG]0037993 ADD END
        
        else if (CODE_MEDICAL_NAME.equals(tableName))
        {
            CodeMedicalName codeMedicalName = getCMNList(code, foreignKey);
            if (codeMedicalName != null)
                codeValueDto = getCodeValueDto(codeMedicalName.getDeleteFlag(),
                        codeMedicalName.getVersionNo(), codeMedicalName.getUpdateTime());
        }
        // $Author :tong_meng
        // $Date : 2013/1/16 14:00$
        // [BUG]0013110 MODIFY BEGIN
        else if(CODE_CHARGE_ITEM.equals(tableName))
        {
            CodeChargeItem codeChargeItem = getCHIList(code, csId);
            if(codeChargeItem != null)
                codeValueDto = getCodeValueDto(codeChargeItem.getDeleteFlag(), codeChargeItem.getVersionNo(),
                		codeChargeItem.getUpdateTime());
        }
        // [BUG]0013110 MODIFY END
        return codeValueDto;
    }

    // [BUG]0010742 MODIFY END
    // [BUG]0011055 MODIFY END

    private CodeValueDto getCodeValueDto(String deleteFlag, String versionNo, Date updateTime)
    {
        CodeValueDto codeValueDto = new CodeValueDto();
        codeValueDto.setDeleteFlag(deleteFlag);
        codeValueDto.setVersionNo(versionNo);
        codeValueDto.setUpdateTime(updateTime);
        return codeValueDto;
    }

	@Override
	@Transactional
	public CodeOrderItem getOI(String code, BigDecimal csId) {
		Map<String, Object> map = new HashMap<String, Object>();
        map.put("itemCode", code);
        map.put("csId", csId);
        List result = entityDao.selectByCondition(CodeOrderItem.class, map);
        if (result == null || result.size() == 0)
            return null;
		return (CodeOrderItem) result.get(0);
	}
}
