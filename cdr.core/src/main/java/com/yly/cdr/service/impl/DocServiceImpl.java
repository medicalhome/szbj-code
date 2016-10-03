/**
 * [FUN]V05-801病历文书列表
 * [FUN]V05-802病历文书详细
 * 
 * @version 1.0, 2012/03/12  20:23:00
 
 * @author wu_jianfeng
 * @since 1.0
*/
package com.yly.cdr.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.founder.fasf.core.util.daohelper.entity.EntityDao;
import com.founder.fasf.web.paging.PagingContext;
import com.yly.cdr.core.Constants;
import com.yly.cdr.dao.DocDao;
import com.yly.cdr.dto.doc.DocListSearchParameters;
import com.yly.cdr.entity.ClinicalDocument;
import com.yly.cdr.service.DocService;

@Component
public class DocServiceImpl implements DocService
{
    @Autowired
    private DocDao docDao;

    @Autowired
    private EntityDao entityDao;

	// $Author :chang_xuewen
    // $Date : 2013/10/24 11:00$
    // [BUG]0038443 MODIFY BEGIN
    /**
     * 根据患者序列号，文档类型，文档名称，文档作者，提交开始日期，提交结束日期，获得病历文书列表
     * @param patientSn 患者序列号
     * @param docListSearchParameters 查询参数
     * @return 病历文书列表
     */
    @Transactional
    public List<ClinicalDocument> selectAllDoc(String patientSn,
            DocListSearchParameters docListSearchParameters,String viewFlag,
            PagingContext pagingContext)
    {
        return docDao.selectAllDoc(patientSn, docListSearchParameters,viewFlag,
                pagingContext);
    }
    // [BUG]0038443 MODIFY END

    /**
     * 根据病历文书序列号，得到病历文书详细
     * @param documentSn 病历文书序列号
     * @return 病历文档
     */
    @Transactional
    public ClinicalDocument selectDocDetail(String documentSn) throws Exception
    {
        // 查找对应的CDA字段信息
        return docDao.selectDocCDA(documentSn);
    }
    /**
     * 根据病历文书文档本地ID和服务ID，得到病历文书详细
     * @param documentLid 文档本地ID
     * @param serviceId 服务ID
     * @return 病历文档
     */
    @Transactional
    public ClinicalDocument selectDocDetail(String documentLid,String serviceId) throws Exception
    {
        return docDao.selectDocCDAByDocLid(documentLid,serviceId);
    }

    // $Author:bin_zhang
    // $Date:2012/9/28 10:42
    // $[BUG]BUG0010082 ADD BEGIN
    /**
     * 根据就诊序列号，得到病历文书列表
     * @return 病历文档
     */
    @Transactional
    public List<ClinicalDocument> selectDoc(String visitSn)
    {
        List<ClinicalDocument> documentResult = docDao.selectDoc(visitSn);
        return documentResult;
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
    @Transactional
    public List<ClinicalDocument> selectDoc(String visitSn,PagingContext pagingContext)
    {
        List<ClinicalDocument> documentResult = docDao.selectDoc(visitSn,pagingContext);
        return documentResult;
    }
    // $[BUG]0032417 ADD BEGIN
    
    /**
     * 根据文档内部序列号获取病历文档
     * @param documentSn 文档内部序列号
     * @return 病历文档
     */
    @Transactional
    public ClinicalDocument selectDocById(String documentSn)
    {
        Map<String, Object> condition = new HashMap<String, Object>();
        condition.put("documentSn", documentSn);
        condition.put("deleteFlag", Constants.NO_DELETE_FLAG);
        List<Object> docs = entityDao.selectByCondition(
                ClinicalDocument.class, condition);
        return docs != null && docs.size() > 0?(ClinicalDocument) docs.get(0):null;
    }
    
    /**
     * 根据文档内部序列号获取病历文档
     * @param patientSn 患者内部序列号
     * @param visitSn 就诊内部序列号
     * @param requestNo 会诊申请单号
     * 
     * @return 病历文档
     */
    @Transactional
    public List<Object> selectDocByRequestNo(String patientSn,String visitSn, String requestNo)
    {
        Map<String, Object> condition = new HashMap<String, Object>();
        condition.put("patientSn", patientSn);
        condition.put("visitSn", visitSn);
        condition.put("requestNo", requestNo);
        condition.put("deleteFlag", Constants.NO_DELETE_FLAG);
        List<Object> docs = entityDao.selectByCondition(
                ClinicalDocument.class, condition);
        return docs;
    }
}
