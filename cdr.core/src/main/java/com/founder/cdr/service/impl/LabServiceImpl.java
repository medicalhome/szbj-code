/**
 * Copryright
 */
package com.founder.cdr.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.founder.cdr.core.Constants;
import com.founder.cdr.dao.DocumentDao;
import com.founder.cdr.dao.LabDao;
import com.founder.cdr.dto.LabDto;
import com.founder.cdr.dto.MorphologyDto;
import com.founder.cdr.dto.OrderStepDto;
import com.founder.cdr.dto.lab.LabListSearchParameters;
import com.founder.cdr.dto.lab.LabResultItemDto;
import com.founder.cdr.entity.AntibacterialDrug;
import com.founder.cdr.entity.ClinicalDocument;
import com.founder.cdr.entity.LabOrder;
import com.founder.cdr.entity.LabOrderLabResult;
import com.founder.cdr.entity.LabResult;
import com.founder.cdr.entity.LabResultCompositeItem;
import com.founder.cdr.entity.LabResultItem;
import com.founder.cdr.entity.MedicalImaging;
import com.founder.cdr.entity.MedicalVisit;
import com.founder.cdr.entity.Message;
import com.founder.cdr.entity.Patient;
import com.founder.cdr.entity.WarningNotice;
import com.founder.cdr.service.CommonService;
import com.founder.cdr.service.LabService;
import com.founder.cdr.util.DateUtils;
import com.founder.fasf.core.util.daohelper.entity.EntityDao;
import com.founder.fasf.web.paging.PagingContext;

/**
 * 用于检验报告相关存储
 * @author jin_peng
 */
@Component
public class LabServiceImpl implements LabService
{
    @Autowired
    private LabDao labDao;

    @Autowired
    private EntityDao entityDao;

    @Autowired
    private DocumentDao documentDao;

    @Autowired
    private CommonService commonService;

    /**
     * 获取检验报告列表
     * @param patientSn 患者内部序列号
     * @param labListSearchParameters 查询条件对象
     * @param pagingContext 分页对象
     * @return 检验报告列表对象集合
     */
    @Transactional
    public LabDto[] selectAllLabList(String patientSn,
            LabListSearchParameters labListSearchParameters,
            PagingContext pagingContext)
    {
        return labDao.selectAllLabList(patientSn, labListSearchParameters,
                pagingContext);
    }

    /**
     * 获取检验项目信息
     * @param compositeItemSn 检查项目内部序列号
     * @return 检验项目集合
     */
    @Transactional
    public List<LabResultItemDto> selectLabResult(String compositeItemSn)
    {
        return labDao.selectLabResult(compositeItemSn);
    }

    /**
     * 通过检验医嘱获取检验报告信息
     * @param orderSn 检验医嘱内部序列号
     * @return 检验报告集合
     */
    @Transactional
    public List<LabResult> selectLabReports(String orderSn)
    {
        return labDao.selectLabReports(orderSn);
    }

    /**
     * 获取检验申请单集合
     * @param patientSn 患者内部序列号
     * @param labReportSn 检验报告内部序列号
     * @param itemCode 检验项目代码
     * @return 检验医嘱集合（其中包含所需的检验申请单）
     */
    @Transactional
    public LabOrder selectLabRequest(String orderSn)
    {
        Map<String, Object> condition = new HashMap<String, Object>();
        condition.put("orderSn", orderSn);
        condition.put("deleteFlag", Constants.NO_DELETE_FLAG);
        List<Object> labOrders = entityDao.selectByCondition(LabOrder.class,
                condition);
        if (labOrders != null && labOrders.size() > 0)
        {
            return (LabOrder) labOrders.get(0);
        }
        return null;
    }

    /**
     * 查询患者所有此项检验的检验项目结果信息
     * @param patientSn 患者内部序列号
     * @param itemCode 项目编码
     * @param itemName 项目中文名
     * @param pagingContext 分页项
     * @return 检验项目结果集合
     */
    @Transactional
    public List<LabResultItemDto> selectRelatedLabResult(String patientSn,
            String itemCode, String itemName, PagingContext pagingContext)
    {
        return labDao.selectRelatedLabResult(patientSn, itemCode, itemName,
                pagingContext);
    }

    /**
     * 获取检验报告信息(通过报告内部序列号)
     * @param reportSn 检验报告内部序列号
     * @return 检验报告对象集合
     */
    @Transactional
    public LabResult selectReportById(String reportSn)
    {
        return (LabResult) entityDao.selectById(LabResult.class, reportSn);
    }

    /**
     * 获取检验报告关联的就诊信息
     * @param map 查询条件
     * @return 检验报告关联就诊对象集合
     */
    @Transactional
    public MedicalVisit selectMedicalVisitById(Map<String, Object> map)
    {
        MedicalVisit medicalVisit = null;
        List<Object> medicalVisitList = entityDao.selectByCondition(
                MedicalVisit.class, map);
        if (medicalVisitList != null && !medicalVisitList.isEmpty())
        {
            medicalVisit = (MedicalVisit) medicalVisitList.get(0);
        }
        return medicalVisit;
    }

    /**
     * 获取检验医嘱信息(通过医嘱内部序列号)
     * @param orderSn 检验医嘱内部序列号
     * @return 检验医嘱对象集合
     */
    @Transactional
    public LabOrder selectLabOrderById(String orderSn)
    {
        return (LabOrder) entityDao.selectById(LabOrder.class, orderSn);
    }

    /**
     * 通过检验报告和检验项目编码查找对应的检验医嘱
     * @param labReportSn 检验报告内部序列号
     * @param itemCode 检验项目编码
     * @return
     */
    @Transactional
    public LabOrder selectLabOrderByReportAndItemCode(String labReportSn,
            String itemCode)
    {

        List<LabOrder> labOrders = labDao.selectOrderByReportAndItemCode(
                labReportSn, itemCode);
        return labOrders != null && labOrders.size() > 0 ? labOrders.get(0)
                : null;
    }

    /**
     * 保存
     * @param entity 根据业务传入entity
     * @return 是否保存成功标识
     */
    @Transactional
    public int save(Object entity)
    {
        return entityDao.insert(entity);
    }

    /**
     * 修改部分信息
     * @param entity 根据业务传入entity
     * @param propertyNames 需要修改的字段名称
     * @return 是否保存成功标识
     */
    @Transactional
    public int modifypartialy(Object entity, String... propertyNames)
    {
        return entityDao.updatePartially(entity, propertyNames);
    }

    /**
     * 修改全部信息
     * @param entity 根据业务传入entity
     * @return 是否保存成功标识
     */
    @Transactional
    public int update(Object entity)
    {
        return entityDao.update(entity);
    }

    /**
     * 获取检验报告信息(根据多个查询条件)
     * @param map 查询条件
     * @return 检验报告对象集合
     */
    @Transactional
    public LabResult selectLabResultByCondition(Map<String, Object> map)
    {
        LabResult labResult = null;
        List<Object> resultList = entityDao.selectByCondition(LabResult.class,
                map);
        if (resultList != null && !resultList.isEmpty())
        {
            labResult = (LabResult) resultList.get(0);
        }
        return labResult;
    }

    /**
     * 获取检验报告项目信息(根据多个查询条件)
     * @param map 查询条件
     * @return 检验报告项目对象集合
     */
    @Transactional
    public List<Object> selectLabResultCompositeByCondition(
            Map<String, Object> map)
    {
        return entityDao.selectByCondition(LabResultCompositeItem.class, map);
    }

    /**
     * 获取检验大项
     * @param itemCode 项目编码
     * @param labReportSn 检验报告内部序列号
     * @return 检验大项
     */
    @Transactional
    public LabResultCompositeItem selectCompItemByCodeAndSn(String itemCode,
            String labReportSn)
    {
        Map<String, Object> condition = new HashMap<String, Object>();
        condition.put("itemCode", itemCode);
        condition.put("labReportSn", labReportSn);
        condition.put("deleteFlag", Constants.NO_DELETE_FLAG);
        List<Object> lrci = selectLabResultCompositeByCondition(condition);
        return lrci != null && lrci.size() > 0 ? (LabResultCompositeItem) lrci.get(0)
                : null;
    }

    /**
     * 获取检验大项集合
     * @param labReportSn 检验报告内部序列号
     * @return 检验大项集合
     */
    @Transactional
    public List<LabResultCompositeItem> selectCompItemBySn(String labReportSn)
    {
        List<LabResultCompositeItem> compositeItems = new ArrayList<LabResultCompositeItem>();
        Map<String, Object> condition = new HashMap<String, Object>();
        condition.put("labReportSn", labReportSn);
        condition.put("deleteFlag", Constants.NO_DELETE_FLAG);
        List<Object> labResultComps = selectLabResultCompositeByCondition(condition);
        if (labResultComps != null && labResultComps.size() > 0)
        {
            for (Object obj : labResultComps)
            {
                LabResultCompositeItem labResultComp = (LabResultCompositeItem) obj;
                compositeItems.add(labResultComp);
            }
        }
        return compositeItems;
    }

    /**
     * 检验大项
     * @param compositeItemSn 检验大项内部序列号
     * @return 检验大项
     */
    @Transactional
    public LabResultCompositeItem selectComItemById(String compositeItemSn)
    {
        Map<String, Object> condition = new HashMap<String, Object>();
        condition.put("compositeItemSn", compositeItemSn);
        condition.put("deleteFlag", Constants.NO_DELETE_FLAG);
        List<Object> lrci = selectLabResultCompositeByCondition(condition);
        return lrci != null && lrci.size() > 0 ? (LabResultCompositeItem) lrci.get(0)
                : null;
    }

    /**
     * 获取检验报告结果信息(根据多个查询条件)
     * @param map 查询条件
     * @return 检验报告结果项目对象集合
     */
    @Transactional
    public List<Object> selectLabResultItemByCondition(Map<String, Object> map)
    {
        return entityDao.selectByCondition(LabResultItem.class, map);
    }

    /**
     * 获取检验影像信息(根据多个查询条件)
     * @param map 查询条件
     * @return 检验影像对象集合
     */
    @Transactional
    public List<Object> selectLabImageByCondition(Map<String, Object> map)
    {
        return entityDao.selectByCondition(MedicalImaging.class, map);
    }

    /**
     * 检验报告新增
     * @param message 消息
     * @param labResult 检验报告对象
     * @param labResultCompositeItemList 检验项目对象集合
     * @param labResultItemList 检验结果对象集合
     * @param labImageList 检验影像对象集合
     * @param clinicalDocument 病历文档
     * @param antibacterialDrugDeleteList 抗菌药物对象集合
     * @param labOrderLabResults 检验报告和检验医嘱关联关系集合
     */
    @Transactional
    public void saveLabResult(Message message, LabResult labResult,
            List<Object> lrciList, List<Object> labResultItemList,
            List<Object> antibacterialDrugList, List<Object> labImageList,
            List<LabOrderLabResult> labOrderLabResults,
            ClinicalDocument clinicalDocument)
    {
        // 新增病历文档
        entityDao.insert(clinicalDocument);
        documentDao.updateClinicalDocumentDocumentCda(message, clinicalDocument);

        // 新增检验报告
        this.save(labResult);

        // 新增检验项目
        if (lrciList != null && lrciList.size() != 0)
        {
            for (Object labResultCompositeItemObject : lrciList)
            {
                LabResultCompositeItem labResultCompositeItem = (LabResultCompositeItem) labResultCompositeItemObject;
                this.save(labResultCompositeItem);
            }
        }

        // 新增检验结果
        if (labResultItemList != null && labResultItemList.size() != 0)
        {
            for (Object labResultItemObject : labResultItemList)
            {
                LabResultItem labResultItem = (LabResultItem) labResultItemObject;
                this.save(labResultItem);
            }
        }

        // 新增抗菌药物
        if (antibacterialDrugList != null && antibacterialDrugList.size() != 0)
        {
            for (Object antibacterialDrugObject : antibacterialDrugList)
            {
                AntibacterialDrug antibacterialDrug = (AntibacterialDrug) antibacterialDrugObject;
                this.save(antibacterialDrug);
            }
        }

        // 新增检验影像
        if (labImageList != null && labImageList.size() != 0)
        {
            for (Object labImageObject : labImageList)
            {
                MedicalImaging labImage = (MedicalImaging) labImageObject;
                byte[] bt = labImage.getImageContent();
                labImage.setImageContent(null);
                this.save(labImage);
                labImage.setImageContent(bt);
                this.update(labImage);
            }
        }

        // $Author: wei_peng
        // $Date: 2013/3/20 15:04
        // [BUG]0014602 ADD BEGIN
        if (labOrderLabResults != null && labOrderLabResults.size() > 0)
        {
            for (LabOrderLabResult labOrderLabResult : labOrderLabResults)
            {
                this.save(labOrderLabResult);
            }
        }
        // [BUG]0014602 ADD END
        // $Author: wei_peng
        // $Date: 2013/3/20 15:04
        // [BUG]0014602 DELETE BEGIN
        // $Author :jin_peng
        // $Date : 2012/09/20 14:47$
        // [BUG]0009691 MODIFY BEGIN
        // 检验医嘱获取方式现改为用标本号
        // 更新检验医嘱中reportSn
        /*
         * this.updateLabOrderReportSn(reportSn, sampleNo, patientLid,
         * patientDomain);
         */
        // [BUG]0009691 MODIFY END
        // [BUG]0014602 DELETE END
    }

    /**
     * 检验报告修改
     * @param message 消息
     * @param labResult 检验报告对象
     * @param antibacterialDrugDeleteList 抗菌药物删除对象集合
     * @param labResultItemDeleteList 检验结果删除对象集合
     * @param lrciDeleteList 检验项目删除对象集合
     * @param labImageDeleteList 检验影像删除对象集合
     * @param lrciList 检验项目对象集合
     * @param labResultItemList 检验结果对象集合
     * @param antibacterialDrugList 抗菌药物对象集合
     * @param labImageList 检验影像对象集合
     * @param clinicalDocument 病历文档
     * @param documentSnToDelete 待删除病历文档序列号
     * @param systemTime 消息处理时间
     */
    @Transactional
    public void updateLabResult(Message message, LabResult labResult,
            List<Object> antibacterialDrugDeleteList,
            List<Object> labResultItemDeleteList, List<Object> lrciDeleteList,
            List<Object> labImageDeleteList, List<Object> lrciList,
            List<Object> labResultItemList, List<Object> antibacterialDrugList,
            List<Object> labImageList, ClinicalDocument clinicalDocument,
            BigDecimal documentSnToDelete, Date systemTime)
    {
        // $Author: jin_peng
        // $Date: 2013/09/03 14:02
        // [BUG]0036979 DELETE BEGIN
        // 新增病历文档
        // entityDao.insert(clinicalDocument);
        // documentDao.updateClinicalDocumentDocumentCda(message,
        // clinicalDocument);

        // [BUG]0036979 DELETE END

        // $Author: jin_peng
        // $Date: 2013/09/03 14:02
        // [BUG]0036979 ADD BEGIN
        // 更新病例文档内容
        if (clinicalDocument != null)
        {
            commonService.update(clinicalDocument);
            documentDao.updateClinicalDocumentDocumentCda(message,
                    clinicalDocument);
        }

        // [BUG]0036979 ADD END

        // 更新检验报告
        this.update(labResult);

        // $Author: jin_peng
        // $Date: 2013/09/03 14:02
        // [BUG]0036979 DELETE BEGIN
        // 删除病历文档
        /**Map<String, Object> condition = new HashMap<String, Object>();
        condition.put("documentSn", documentSnToDelete);
        condition.put("deleteFlag", Constants.NO_DELETE_FLAG);
        List<Object> clinicalDocuments = entityDao.selectByCondition(
                ClinicalDocument.class, condition);
        if (clinicalDocuments != null && clinicalDocuments.size() > 0)
        {
            // Author :jia_yanqing
            // Date : 2013/3/21 13:00
            // [BUG]0014658 MODIFY BEGIN
            ClinicalDocument cd = (ClinicalDocument) clinicalDocuments.get(0);
            // cd.setDeleteFlag(Constants.DELETE_FLAG);
            // cd.setDeleteTime(systemTime);
            entityDao.delete(cd);
        }*/
        // [BUG]0014658 MODIFY END
        // [BUG]0036979 DELETE END

        // 删除抗菌药物
        if (antibacterialDrugDeleteList != null
            && antibacterialDrugDeleteList.size() != 0)
        {
            for (Object antibacterialDrugObject : antibacterialDrugDeleteList)
            {
                AntibacterialDrug antibacterialDrug = (AntibacterialDrug) antibacterialDrugObject;
                antibacterialDrug.setDeleteFlag(Constants.DELETE_FLAG);
                antibacterialDrug.setDeleteTime(systemTime);

                // $Author: tong_meng
                // $Date: 2013/8/30 15:00
                // [BUG]0036757 ADD BEGIN
                antibacterialDrug.setDeleteby(message.getServiceId()); // 设置创建人
                // [BUG]0036757 ADD END

                entityDao.update(antibacterialDrug);
            }
        }

        // 删除检验结果
        if (labResultItemDeleteList != null
            && labResultItemDeleteList.size() != 0)
        {
            for (Object labResultItemObject : labResultItemDeleteList)
            {
                LabResultItem labResultItem = (LabResultItem) labResultItemObject;
                labResultItem.setDeleteFlag(Constants.DELETE_FLAG);
                labResultItem.setDeleteTime(systemTime);

                // $Author: tong_meng
                // $Date: 2013/8/30 15:00
                // [BUG]0036757 ADD BEGIN
                labResultItem.setDeleteby(message.getServiceId()); // 设置创建人
                // [BUG]0036757 ADD END

                entityDao.update(labResultItem);
            }
        }

        // 删除检验项目
        if (lrciDeleteList != null && lrciDeleteList.size() != 0)
        {
            for (Object labResultCompositeItemObject : lrciDeleteList)
            {
                LabResultCompositeItem labResultCompositeItem = (LabResultCompositeItem) labResultCompositeItemObject;
                labResultCompositeItem.setDeleteFlag(Constants.DELETE_FLAG);
                labResultCompositeItem.setDeleteTime(systemTime);

                // $Author: tong_meng
                // $Date: 2013/8/30 15:00
                // [BUG]0036757 ADD BEGIN
                labResultCompositeItem.setDeleteby(message.getServiceId()); // 设置创建人
                // [BUG]0036757 ADD END

                entityDao.update(labResultCompositeItem);
            }
        }

        // 删除检验影像
        if (labImageDeleteList != null && labImageDeleteList.size() != 0)
        {
            for (Object labImageDeleteObject : labImageDeleteList)
            {
                MedicalImaging labImage = (MedicalImaging) labImageDeleteObject;
                labImage.setDeleteFlag(Constants.DELETE_FLAG);
                labImage.setDeleteTime(systemTime);

                // $Author: tong_meng
                // $Date: 2013/8/30 15:00
                // [BUG]0036757 ADD BEGIN
                labImage.setDeleteby(message.getServiceId()); // 设置创建人
                // [BUG]0036757 ADD END

                entityDao.update(labImage);
            }
        }

        // 更新检验项目
        if (lrciList != null && lrciList.size() != 0)
        {
            for (Object labResultCompositeItemObject : lrciList)
            {
                LabResultCompositeItem labResultCompositeItem = (LabResultCompositeItem) labResultCompositeItemObject;
                this.save(labResultCompositeItem);
            }
        }

        // 更新检验结果
        if (labResultItemList != null && labResultItemList.size() != 0)
        {
            for (Object labResultItemObject : labResultItemList)
            {
                LabResultItem labResultItem = (LabResultItem) labResultItemObject;
                this.save(labResultItem);
            }
        }

        // 新增抗菌药物
        if (antibacterialDrugList != null && antibacterialDrugList.size() != 0)
        {
            for (Object antibacterialDrugObject : antibacterialDrugList)
            {
                AntibacterialDrug antibacterialDrug = (AntibacterialDrug) antibacterialDrugObject;
                this.save(antibacterialDrug);
            }
        }

        // 更新检验影像
        if (labImageList != null && labImageList.size() != 0)
        {
            for (Object labImageObject : labImageList)
            {
                MedicalImaging labImage = (MedicalImaging) labImageObject;
                // if (!StringUtils.isEmpty(labImage.getImageContent()))
                // {

                // $Author :tong_meng
                // $Date : 2012/7/11 16:00$
                // [BUG]0007849 MODIFY BEGIN
                byte[] bt = labImage.getImageContent();
                labImage.setImageContent(null);
                this.save(labImage);

                // $Author: tong_meng
                // $Date: 2013/8/30 15:00
                // [BUG]0036757 ADD BEGIN
                labImage.setUpdateby(message.getServiceId()); // 设置创建人
                // [BUG]0036757 ADD END

                labImage.setImageContent(bt);
                this.update(labImage);
                // [BUG]0007849 MODIFY END
                // }
            }
        }
    }

    /**
     * 获取检验报告结果对应的抗菌药物信息(根据多个查询条件)
     * @param map 查询条件
     * @return 抗菌药物结果项目对象集合
     */
    @Transactional
    public List<Object> selectAntibacterialDrugByCondition(
            Map<String, Object> map)
    {
        return entityDao.selectByCondition(AntibacterialDrug.class, map);
    }

    /**
     * 新增形态学检验报告及其相关信息
     */
    // $Author :jia_yanqing
    // $Date : 2013/03/01 14:47$
    // [BUG]0014226 MODIFY BEGIN
    @Transactional
    public void saveMorphologyLabResult(Message message,
            ClinicalDocument clinicalDocument, LabResult labResult,
            List<List<Object>> labResultCompositeItemAndResultList,
            List<List<Object>> labImageList,
            List<LabOrderLabResult> labOrderLabResults)

    {
        // 新增病历文档
        entityDao.insert(clinicalDocument);
        documentDao.updateClinicalDocumentDocumentCda(message, clinicalDocument);

        // 新增检验报告
        this.save(labResult);

        // 检验项目集合
        if (labResultCompositeItemAndResultList.size() > 0)
        {
            List<Object> lrciList = labResultCompositeItemAndResultList.get(0);

            // 新增检验项目
            if (lrciList != null && lrciList.size() != 0)
            {
                for (Object labResultCompositeItemObject : lrciList)
                {
                    LabResultCompositeItem labResultCompositeItem = (LabResultCompositeItem) labResultCompositeItemObject;
                    this.save(labResultCompositeItem);
                }
            }
        }

        // 检验结果集合
        if (labResultCompositeItemAndResultList.size() > 1)
        {
            List<Object> labResultList = labResultCompositeItemAndResultList.get(1);

            // 新增检验结果
            if (labResultList != null && labResultList.size() != 0)
            {
                for (Object labResultItemObject : labResultList)
                {
                    LabResultItem labResultItem = (LabResultItem) labResultItemObject;
                    this.save(labResultItem);
                }
            }
        }

        // 新增检验影像
        if (labImageList.size() > 0)
        {
            List<Object> labImageListForContent = labImageList.get(0);
            if (labImageListForContent != null
                && labImageListForContent.size() != 0)
            {
                for (Object labImageObject : labImageListForContent)
                {
                    MedicalImaging medicalImage = (MedicalImaging) labImageObject;
                    byte[] bt1 = medicalImage.getImageContent();
                    medicalImage.setImageContent(null);
                    this.save(medicalImage);

                    // $Author: tong_meng
                    // $Date: 2013/8/30 15:00
                    // [BUG]0036757 ADD BEGIN
                    medicalImage.setUpdateby(message.getServiceId()); // 设置创建人
                    // [BUG]0036757 ADD END

                    medicalImage.setImageContent(bt1);
                    this.update(medicalImage);
                }
            }
        }

        if (labImageList.size() > 1)
        {
            List<Object> labImageListForInsert = (List<Object>) labImageList.get(1);

            if (labImageListForInsert != null
                && labImageListForInsert.size() != 0)
            {
                for (Object labImageObject : labImageListForInsert)
                {
                    MedicalImaging labImage = (MedicalImaging) labImageObject;
                    byte[] bt2 = labImage.getImageContent();
                    labImage.setImageContent(null);
                    this.save(labImage);

                    // $Author: tong_meng
                    // $Date: 2013/8/30 15:00
                    // [BUG]0036757 ADD BEGIN
                    labImage.setUpdateby(message.getServiceId()); // 设置创建人
                    // [BUG]0036757 ADD END

                    labImage.setImageContent(bt2);
                    this.update(labImage);
                }
            }
        }

        // $Author: wei_peng
        // $Date: 2013/3/20 15:04
        // [BUG]0014602 ADD BEGIN
        if (labOrderLabResults != null && labOrderLabResults.size() > 0)
        {
            for (LabOrderLabResult labOrderLabResult : labOrderLabResults)
            {
                this.save(labOrderLabResult);
            }
        }
        // [BUG]0014602 ADD END
        // $Author: wei_peng
        // $Date: 2013/3/20 15:04
        // [BUG]0014602 DELETE BEGIN
        // $Author :jin_peng
        // $Date : 2012/09/20 14:47$
        // [BUG]0009691 MODIFY BEGIN
        // 更新检验医嘱中reportSn
        /*
         * this.updateLabOrderReportSn( (String)
         * StringUtils.BigDecimalToStr(labResult.getLabReportSn()), sampleNo,
         * labResult.getPatientLid(), labResult.getPatientDomain());
         */
        // [BUG]0009691 MODIFY END
        // [BUG]0014602 DELETE END
    }

    /**
     * 更新形态学检验报告及其相关信息
     */
    @Transactional
    public void updateMorphologyLabResult(Message message,
            ClinicalDocument clinicalDocument, LabResult labResult,
            List<List<Object>> labResultCompositeItemAndResultList,
            List<List<Object>> labImageList,
            List<Object> labImageListForDelete, List<String> orderLid)
    {
        // 新增病历文档
        entityDao.update(clinicalDocument);
        documentDao.updateClinicalDocumentDocumentCda(message, clinicalDocument);

        // 新增检验报告
        this.update(labResult);

        if (labResultCompositeItemAndResultList.size() > 3)
        {
            List<Object> labResultItemForDelete = labResultCompositeItemAndResultList.get(3);

            // 删除检验结果
            if (labResultItemForDelete != null
                && labResultItemForDelete.size() != 0)
            {
                for (Object labResultItemObject : labResultItemForDelete)
                {
                    LabResultItem labResultItem = (LabResultItem) labResultItemObject;
                    // $Author: tong_meng
                    // $Date: 2013/8/30 15:00
                    // [BUG]0036757 ADD BEGIN
                    labResultItem.setDeleteby(message.getServiceId()); // 设置创建人
                    // [BUG]0036757 ADD END
                    labResultItem.setDeleteFlag(Constants.DELETE_FLAG);
                    entityDao.update(labResultItem);
                }
            }
        }

        if (labResultCompositeItemAndResultList.size() > 2)
        {
            List<Object> lrciForDelete = labResultCompositeItemAndResultList.get(2);

            // 删除检验项目
            if (lrciForDelete != null && lrciForDelete.size() != 0)
            {
                for (Object labResultCompositeItemObject : lrciForDelete)
                {
                    LabResultCompositeItem labResultCompositeItem = (LabResultCompositeItem) labResultCompositeItemObject;
                    labResultCompositeItem.setDeleteFlag(Constants.DELETE_FLAG);
                    // $Author: tong_meng
                    // $Date: 2013/8/30 15:00
                    // [BUG]0036757 ADD BEGIN
                    labResultCompositeItem.setDeleteby(message.getServiceId()); // 设置创建人
                    // [BUG]0036757 ADD END
                    entityDao.update(labResultCompositeItem);
                }
            }
        }

        // Author :jia_yanqing
        // Date : 2013/4/17 10:47
        // [BUG]0015141 MODIFY BEGIN
        // if (labImageList.size() > 2)
        // {
        // List<Object> labImageListForDelete = labImageList.get(2);

        // 删除检验影像
        if (labImageListForDelete != null && labImageListForDelete.size() != 0)
        {
            for (Object labImageDeleteObject : labImageListForDelete)
            {
                MedicalImaging labImage = (MedicalImaging) labImageDeleteObject;
                labImage.setDeleteFlag(Constants.DELETE_FLAG);
                labImage.setUpdateTime(DateUtils.getSystemTime());
                labImage.setDeleteTime(DateUtils.getSystemTime());
                // $Author: tong_meng
                // $Date: 2013/8/30 15:00
                // [BUG]0036757 ADD BEGIN
                labImage.setDeleteby(message.getServiceId()); // 设置创建人
                // [BUG]0036757 ADD END
                entityDao.update(labImage);
            }
        }
        // }
        // [BUG]0015141 MODIFY END

        // 检验项目集合
        if (labResultCompositeItemAndResultList.size() > 0)
        {
            List<Object> lrciList = labResultCompositeItemAndResultList.get(0);

            // 新增检验项目
            if (lrciList != null && lrciList.size() != 0)
            {
                for (Object labResultCompositeItemObject : lrciList)
                {
                    LabResultCompositeItem labResultCompositeItem = (LabResultCompositeItem) labResultCompositeItemObject;
                    this.save(labResultCompositeItem);
                }
            }
        }

        // 检验结果集合
        if (labResultCompositeItemAndResultList.size() > 1)
        {
            List<Object> labResultList = labResultCompositeItemAndResultList.get(1);

            // 新增检验结果
            if (labResultList != null && labResultList.size() != 0)
            {
                for (Object labResultItemObject : labResultList)
                {
                    LabResultItem labResultItem = (LabResultItem) labResultItemObject;
                    this.save(labResultItem);
                }
            }
        }

        // 新增检验影像
        if (labImageList.size() > 0)
        {
            List<Object> labImageListForContent = labImageList.get(0);
            if (labImageListForContent != null
                && labImageListForContent.size() != 0)
            {
                for (Object labImageObject : labImageListForContent)
                {
                    MedicalImaging medicalImage = (MedicalImaging) labImageObject;
                    byte[] bt1 = medicalImage.getImageContent();
                    medicalImage.setImageContent(null);
                    this.save(medicalImage);
                    medicalImage.setImageContent(bt1);
                    this.update(medicalImage);
                }
            }
        }

        if (labImageList.size() > 1)
        {
            List<Object> labImageListForInsert = labImageList.get(1);

            if (labImageListForInsert != null
                && labImageListForInsert.size() != 0)
            {
                for (Object labImageObject : labImageListForInsert)
                {
                    MedicalImaging labImage = (MedicalImaging) labImageObject;
                    byte[] bt2 = labImage.getImageContent();
                    labImage.setImageContent(null);
                    this.save(labImage);
                    labImage.setImageContent(bt2);
                    this.update(labImage);
                }
            }
        }
    }

    // [BUG]0014226 MODIFY END

    /**
     * 查询对应报告的报告图片
     */
    @Transactional
    public MedicalImaging selectLabImage(String labReportSn,
            String imageTypeFlag)
    {
        Map<String, Object> condition = new HashMap<String, Object>();
        condition.put("reportSn", labReportSn);
        condition.put("imageType", imageTypeFlag);
        // Author :jia_yanqing
        // Date : 2013/4/17 10:47
        // [BUG]0015154 ADD BEGIN
        condition.put("deleteFlag", Constants.NO_DELETE_FLAG);
        // [BUG]0015154 ADD END
        List<Object> images = entityDao.selectByCondition(MedicalImaging.class,
                condition);
        if (images != null && images.size() != 0)
        {
            return (MedicalImaging) images.get(0);
        }
        return null;
    }

    /**
     * 查询形态学报告具体内容
     */
    @Transactional
    public MorphologyDto selectMorphologyReport(String labReportSn)
    {
        List<MorphologyDto> morphologys = labDao.selectMorphologyReport(labReportSn);
        return morphologys != null && morphologys.size() > 0 ? morphologys.get(0)
                : null;
    }

    // $Author:bin_zhang
    // $Date:2012/9/28 10:42
    // $[BUG]BUG0010082 ADD BEGIN
    @Override
    @Transactional
    public LabDto[] selectLabList(String visitSn)
    {
        LabDto[] labResult = labDao.selectLabList(visitSn);
        return labResult;
    }

    // $[BUG]BUG0010082 ADD END

    // $Author:chang_xuewen
    // $Date:2013/07/01 17:10
    // $[BUG]0032417 ADD BEGIN
    /**
     * 增加pagingContext满足门诊视图翻页需求
     * @param visitSn
     * @param pagingContext
     * @return
     */
    @Override
    @Transactional
    public LabDto[] selectLabList(String visitSn, PagingContext pagingContext)
    {
        LabDto[] labResult = labDao.selectLabList(visitSn, pagingContext);
        return labResult;
    }

    // $[BUG]0032417 ADD BEGIN

    @Override
    @Transactional
    public List<WarningNotice> selectWarningNotice(String requestPerson,
            String deptId, String warnOrUnnormal, String orgCode)
    {
        return labDao.selectWarningNotice(requestPerson, deptId,
                warnOrUnnormal, Constants.WARN_OCCUPATION_TYPE_DOCTOR,
                Constants.WARN_OCCUPATION_TYPE_NURSE, orgCode);
    }
    
 
    @Override
    @Transactional
    public List<WarningNotice> selectWarningInfectNotice(String ruleGroup,
            String orgCode, String wardNo)
    {
        return labDao.selectWarningInfectNotice(ruleGroup, orgCode, wardNo);
    }

    // $Author :jin_peng
    // $Date : 2013/04/22 09:40$
    // [BUG]0014747 ADD BEGIN
    /**
     * 查询警告通知检验具体结果异常或危机内容
     * @param labReportLid 报告本地id
     * @param sourceSystemId 源系统id
     * @param orgCode 医疗机构代码
     * @return 警告通知检验具体结果异常或危机内容
     */
    @Transactional
    public List<LabDto> selectWarningLabResultItem(String labReportLid,
            String sourceSystemId, String orgCode)
    {
        return labDao.selectWarningLabResultItem(labReportLid, sourceSystemId,
                orgCode);
    }

    /**
     * 查询患者记录
     * @param patientSn 患者内部序列号
     * @return 患者记录
     */
    @Transactional
    public Patient getPatient(BigDecimal patientSn)
    {
        Patient patient = null;

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("patientSn", patientSn);
        map.put("deleteFlag", Constants.NO_DELETE_FLAG);

        List<Object> patientList = entityDao.selectByCondition(Patient.class,
                map);

        if (patientList != null && !patientList.isEmpty())
        {
            patient = (Patient) patientList.get(0);
        }

        return patient;
    }

    /**
     * 获取就诊记录
     * @param visitSn 就诊内部序列号
     * @return 就诊记录
     */
    @Transactional
    public MedicalVisit getMedicalVisit(BigDecimal visitSn)
    {
        MedicalVisit medicalVisit = null;

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("visitSn", visitSn);
        map.put("deleteFlag", Constants.NO_DELETE_FLAG);

        List<Object> visitList = entityDao.selectByCondition(
                MedicalVisit.class, map);

        if (visitList != null && !visitList.isEmpty())
        {
            medicalVisit = (MedicalVisit) visitList.get(0);
        }

        return medicalVisit;
    }

    // [BUG]0014747 ADD END

    /**
     * 根据医嘱内部序列号查询医嘱状态集合
     * @param orderSn
     * @return
     * @throws Exception
     */
    @Transactional
    public List<OrderStepDto> selectLabOrderSteps(LabOrder labOrder,String domain)
            throws Exception
    {
        // 保存医嘱状态列表
        List<OrderStepDto> orderSteps = new ArrayList<OrderStepDto>();
        // 若医嘱为临时医嘱
        if (Constants.TEMPORARY_FLAG.equals(labOrder.getTemporaryFlag()))
        {
            String orderStatus = labOrder.getOrderStatus();
            // 三种医嘱状态DTO封装（下达、确认、付费）
            OrderStepDto orderStepIssue = commonService.getOrderStep(
                    Constants.ORDER_STATUS_ISSUE, labOrder);
            OrderStepDto orderStepValidated = commonService.getOrderStep(
                    Constants.ORDER_STATUS_VALIDATED, labOrder);
            OrderStepDto orderStepPayment= commonService.getOrderStep(
					Constants.ORDER_STATUS_PAYMENT, labOrder);
            // 根据不同的情况为医嘱状态列表添加信息
            if (Constants.ORDER_STATUS_ISSUE.equals(orderStatus))
            {
                orderSteps.add(orderStepIssue);
                orderSteps.add(orderStepPayment);
            }
            else if (Constants.ORDER_STATUS_VALIDATED.equals(orderStatus))
            {
                orderSteps.add(orderStepIssue);
                orderSteps.add(orderStepValidated);
            }
            else
            {
                orderSteps.add(orderStepIssue);
                if (Constants.PATIENT_DOMAIN_INPATIENT.equals(domain)){
                	orderSteps.add(orderStepValidated);
                }else{
                	orderSteps.add(orderStepPayment);
                }
                orderSteps.addAll(commonService.getOrderStepDtos(labOrder.getOrderSn().toString()));
            }
            // 获取已收费信息节点
            if (Constants.PATIENT_DOMAIN_OUTPATIENT.equals(labOrder.getPatientDomain())
                && labOrder.getChargeStatusCode() != null
                && !Constants.CHARGE_STATUS_NOTCHARGE.equals(labOrder.getChargeStatusCode()))
            {
                /*OrderStepDto chargeOrderStep = commonService.getChargeOrderStep(labOrder.getOrderSn().toString());
                if (chargeOrderStep != null)
                    orderSteps.add(chargeOrderStep);*/
            }

            // 对列表内容使用执行时间字段进行升序排列
            Collections.sort(orderSteps);
        }
        return orderSteps;
    }

    /**
     * 查询医嘱闭环（港大）
     * @throws Exception 
     * */
    @Transactional
    public List<OrderStepDto> selectLabOrderStepsForGD(LabOrder labOrder,String visitType) throws Exception{
    	// 保存医嘱状态列表
        List<OrderStepDto> orderSteps = new ArrayList<OrderStepDto>();
        // 若医嘱为临时医嘱
        if (Constants.TEMPORARY_FLAG.equals(labOrder.getTemporaryFlag()))
        {
        	OrderStepDto orderStepIssue = commonService.getOrderStep(
                    Constants.ORDER_STATUS_ISSUE, labOrder);
        	orderSteps.add(orderStepIssue);
        	orderSteps.addAll(commonService.getOrderStepDtos(labOrder.getOrderSn().toString()));
        	Collections.sort(orderSteps);
        }   	
		return orderSteps;
    }
    
    
    // $Author:jin_peng
    // $Date:2013/09/23 15:50
    // [BUG]0037540 ADD BEGIN
    /**
     * 插入敏感信息审计内容
     * @param lrciItemCodeWhere 检验大项编码条件
     * @param lriRuleWhere 检验具体结果规则条件
     * @param accountLogSn 登陆日志内部序列号
     * @param patientLid 患者lid
     * @param patientDomain 域id
     * @param dataTypeName 数据类型名称
     * @param businessDataNo 业务数据编号
     * @param labReportSn 检验报告内部序列号
     * @param systemTime 系统时间
     * @param orgCode 医疗机构代码 
     * @param orgName 医疗机构名称
     * @return
     */
    @Transactional
    public void insertLabAuditLog(String lrciItemCodeWhere,
            String lriRuleWhere, BigDecimal accountLogSn, String patientLid,
            String patientDomain, String dataTypeName, String businessDataNo,
            BigDecimal labReportSn, String systemTime, String orgCode,
            String orgName)
    {
        labDao.insertLabAuditLog(lrciItemCodeWhere, lriRuleWhere, accountLogSn,
                patientLid, patientDomain, dataTypeName, businessDataNo,
                labReportSn, systemTime, orgCode, orgName);
    }
    
    /**
     * 根据报告日期查询intervalHours小时内的检验项目
     * @param patientID 患者id
     * @param reportDate 报告日期
     * @param intervalHours 时间间隔（小时）
     * @param itemsList 检验项目list
     * @param unnormal 项
     * @param systemID 系统ID
     * @return
     */
    @Transactional
    public List<String> selectLiverInfectiousItem(String patientID,String reportDate,
    		int intervalHours,List<String> itemsList,String unnormal,String systemID)
    {
    	return labDao.selectLiverInfectiousItem(patientID,reportDate,intervalHours,itemsList,unnormal,systemID);
    }

    // [BUG]0037540 ADD END
}
