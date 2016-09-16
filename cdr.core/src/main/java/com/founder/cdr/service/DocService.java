/**
 * [FUN]V05-801病历文书列表
 * [FUN]V05-802病历文书详细
 * 
 * @version 1.0, 2012/03/12  20:23:00
 
 * @author wu_jianfeng
 * @since 1.0
*/
package com.founder.cdr.service;

import java.util.List;

import com.founder.cdr.dto.doc.DocListSearchParameters;
import com.founder.cdr.entity.ClinicalDocument;
import com.founder.fasf.web.paging.PagingContext;

public interface DocService
{
	// $Author :chang_xuewen
    // $Date : 2013/10/24 11:00$
    // [BUG]0038443 MODIFY BEGIN
    /**
     * 根据患者序列号，文档类型，文档名称，文档作者，提交开始日期，提交结束日期，获得病历文书列表
     * @param patientSn 患者序列号
     * @param docListSearchParameters 查询参数
     * @return 病历文书列表
     */
    List<ClinicalDocument> selectAllDoc(String patientSn,
            DocListSearchParameters docListSearchParameters,String viewFlag,
            PagingContext pagingContext);
    // [BUG]0038443 MODIFY END

    // $Author:bin_zhang
    // $Date:2012/9/28 10:42
    // $[BUG]BUG0010082 ADD BEGIN
    /**
     * 根据就诊序列号获得病历文书列表
     * @param visitSn 就诊序列号
     * @return 门诊病历文书列表
     */
    List<ClinicalDocument> selectDoc(String visitSn);

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
    List<ClinicalDocument> selectDoc(String visitSn,PagingContext pagingContext);
    // $[BUG]0032417 ADD BEGIN
    
    /**
     * 根据病历文书序列号，获得病历文书详细
     * @param documentSn 病历文书序列号
     * @return 病历文档
     */
    ClinicalDocument selectDocDetail(String documentSn) throws Exception;
    
    /**
     * 根据病历文书文档本地ID和服务ID，得到病历文书详细
     * @param documentLid 文档本地ID
     * @param serviceId 服务ID
     * @return 病历文档
     */
    ClinicalDocument selectDocDetail(String documentLid,String serviceId) throws Exception;
    
    /**
     * 根据文档内部序列号获取病历文档
     * @param documentSn 文档内部序列号
     * @return 病历文档
     */
    ClinicalDocument selectDocById(String documentSn);    
    
    /**
     * 根据文档内部序列号获取病历文档
     * @param patientSn 患者内部序列号
     * @param visitSn 就诊内部序列号
     * @param requestNo 会诊申请单号
     * 
     * @return 病历文档
     */
    List<Object> selectDocByRequestNo(String patientSn,String visitSn, String requestNo);
}
