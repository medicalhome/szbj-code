package com.founder.cdr.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;

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

/**
 * 术语定义接口
 * @author tong_meng
 *
 */
public interface DictionaryService
{
    /**
     * 保存术语  codeValue 表和  codeSystem 实体
     * @param cvUpdateList 要更新的codeValue实体集合
     * @param cvDeleteList 要删除的codeValue实体集合
     * @param cvInsertList 要新增的codeValue实体集合
     * @param codeSystem 要新增或者更新的codeSystem实体
     */
    @Transactional
    public void saveAllList(List<Object> cvUpdateList,
            List<Object> cvDeleteList, List<Object> cvInsertList,
            CodeSystem codeSystem);

    /**
     * 通过关联codeSystem表，查询是否存在数据
     * @param csCode 关联查询条件 cs_code
     * @return 符合条件的codeSystem集合
     */
    @Transactional
    public List<CodeSystem> selectCodeSystems(String csCode);

    /**
     * 找到要更新或者删除的数据
     * @param code
     * @return
     */
    @Transactional
    public CodeValue getCVList(String code, BigDecimal csId, String messageId);

    /**
     * 找到要更新或者删除的病区字典数据
     * @param code
     * @return
     */
    @Transactional
    public CodeWard getCWList(String code, BigDecimal csId, String name);

    /**
     * 找到要更新或者删除的检查项目分组数据
     * @param code
     * @return
     */
    @Transactional
    public CodeExamItemGroup getCEIPList(String code, BigDecimal csId);

    /**
     * 找到要更新或者删除的国际疾病ICD分组数据
     * @param code
     * @return
     */
    @Transactional
    public CodeIcd getCIList(String code, BigDecimal csId);
    
    // Author :jia_yanqing
    // Date : 2013/10/14 10:00
    // [BUG]0037993 ADD BEGIN
    /**
     * 找到要更新或者删除的手术-操作字典数据
     * @param code
     * @return
     */
    @Transactional
    public CodeOperation getCOList(String code, BigDecimal csId);
    // [BUG]0037993 ADD END

    /**
     * 找到要更新或者删除的检验项目字典数据
     * @param code
     * @return
     */
    @Transactional
    public CodeExamItem getCEIList(String code, BigDecimal csId);

    /**
     * 找到要更新或者删除的检查项目字典数据
     * @param code
     * @return
     */
    @Transactional
    public CodeLabItem getLIList(String code, BigDecimal csId);

    /**
     * 找到要更新或者删除的检查子项目字典数据
     * @param code
     * @return
     */
    @Transactional
    public CodeLabSubitem getLSIList(String code, BigDecimal csId);

    /**
     * 找到要更新或者删除的科室字典数据
     * @param code
     * @return
     */
    @Transactional
    public CodeDepartment getCDIPList(String code, BigDecimal csId);

    /**
     * 找到要更新或者删除的药品字典数据
     * @param code
     * @return
     */
    @Transactional
    public CodeDrug getCDList(String code, BigDecimal csId, String Serial);

    /**
     * 找到要更新或者删除的人员字典数据
     * @param code
     * @return
     */
    @Transactional
    public CodePerson getCPList(String code, BigDecimal csId);

    /**
     * 根据表名，查询相关表
     * @param tableName 表名
     * @param code 代码
     * @param csId 编码
     * @return 找到记录的集合
     */
    // $Author :tong_meng
    // $Date : 2012/11/05 11:00$
    // [BUG]0011055 MODIFY BEGIN
    // $Author :tong_meng
    // $Date : 2012/10/25 15:00$
    // [BUG]0010742 MODIFY BEGIN
    @Transactional
    public CodeValueDto selectRecordExistByTableName(String tableName,
            String code, BigDecimal csId, String messageId, String foreignKey);

    // [BUG]0010742 MODIFY END
    // [BUG]0011055 MODIFY END

    /**
     * 根据药品分类码，查询药物信息
     * @param drugId    药品分类码
     * @return
     */
    @Transactional
    public CodeMedicalName selectCodeMedicalName(String drugId);

    /**
     * 根据药品编码和药品名称查询药品名称字典信息
     * @param code  药品编码
     * @param name  药品名称
     * @return
     */
    @Transactional
    public CodeMedicalName getCMNList(String code, String name);

    // $Author :tong_meng
    // $Date : 2013/1/16 14:00$
    // [BUG]0013110 ADD BEGIN
    /**
     * 根据收费项目编码查找收费项项目
     * @param itemCode
     * @param csId
     * @return
     */
    @Transactional
    public CodeChargeItem getCHIList(String itemCode, BigDecimal csId);
    // [BUG]0013110 ADD END
    
    /**
     * 根据医嘱编码查找医嘱（code_order_item）
     * */
    @Transactional
    public CodeOrderItem getOI(String code, BigDecimal csId);
    
}
