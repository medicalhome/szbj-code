/**
 * Copyright (c) 2012—2012 Founder International Co.Ltd. All rights reserved.
 */
package com.yly.cdr.web;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.rtf.RTFEditorKit;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.xslt.XsltView;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.founder.fasf.web.paging.PagingContext;
import com.founder.fasf.web.paging.PagingContextHolder;
import com.yly.cdr.core.AppSettings;
import com.yly.cdr.core.Constants;
import com.yly.cdr.dto.doc.DocListSearchParameters;
import com.yly.cdr.entity.ClinicalDocument;
import com.yly.cdr.entity.MedicalImaging;
import com.yly.cdr.entity.Patient;
import com.yly.cdr.entity.PatientCrossIndex;
import com.yly.cdr.service.CommonService;
import com.yly.cdr.service.DocService;
import com.yly.cdr.service.PatientService;
import com.yly.cdr.service.PortalService;
import com.yly.cdr.util.DateUtils;
import com.yly.cdr.util.FileUtils;
import com.yly.cdr.web.util.PagingHelper;

/**
 * [FUN]V05-801病历文书列表
 * [FUN]V05-802病历文书详细
 * 
 * @version 1.0, 2012/03/12  20:23:00
 
 * @author wu_jianfeng
 * @since 1.0
*/
@Controller
@RequestMapping("/portalDoc")
public class PortalDocController implements ApplicationContextAware
{
    private static Logger logger = LoggerFactory.getLogger(PortalDocController.class);

    @Autowired
    private DocService docService;
    
    @Autowired
    private PortalService portalService; 
    
    @Autowired
    private PatientService patientService; 

    @Autowired
    private CommonService commonService;

    private ApplicationContext context;

    /**
     * V05-801:病历文书列表
     * 显示病历文书列表画面
     * 
     * @param request
     * @return
     */
    @RequestMapping("/list_{patientSn}")
    public ModelAndView list(WebRequest request,
            @PathVariable("patientSn") String patientSn, HttpSession session)
            throws Exception
    {
        // 初始化pagingContext
        PagingContext pagingContext = PagingContextHolder.getPagingContext();
        String viewId = request.getParameter("vi");
      //获取患者信息
  		Patient patient = patientService.getPatient(patientSn);
  		String age =DateUtils.caluAge(patient.getBirthDate(),new Date());
		// 根据域ID，患者Lid从患者交叉索引查询患者相关信息
        Map<String, Object> condition = new HashMap<String, Object>();
        condition.put("patientDomain", request.getParameter("pd"));
        condition.put("patientLid", request.getParameter("pi"));
        PatientCrossIndex patientCrossIndex = patientService.getPatientCrossIndexByCondition(condition);
        // 定义页面检索元素DTO
        DocListSearchParameters docListSearchParameters = new DocListSearchParameters();
        //就诊次数
        docListSearchParameters.setVisitTimes(request.getParameter("vt"));
        // 如果是时间轴视图或住院视图运行到此则传入已定义好的参数dto
        if (request.getAttribute("commonParameters", WebRequest.SCOPE_REQUEST) != null)
        {
            docListSearchParameters = (DocListSearchParameters) request.getAttribute(
                    "commonParameters", WebRequest.SCOPE_REQUEST);
        }
        // 是否调用session
        String senSave = request.getParameter("senSave");
        if ("true".equals(senSave))
        {
            docListSearchParameters = (DocListSearchParameters) session.getAttribute("DocListSearchParameters");
        }
        else
        {
        	if("V004".equals(viewId)){
//        		docListSearchParameters.setDocumentType("05");
        		docListSearchParameters.setDocumentType("18");
        	}else if("V005".equals(viewId)){
        		String dt = request.getParameter("documentType");
        		dt = dt == null ? Constants.OPTION_SELECT : dt;
        		docListSearchParameters.setDocumentType(dt);
        		List<String> types = Arrays.asList(dt.split(","));
        		docListSearchParameters.setPortalDocumentTypes(types);
        	}else{
        		docListSearchParameters.setDocumentType( request.getParameter("documentType"));
        	}
        	docListSearchParameters.setDocumentName(request.getParameter("documentName"));
            docListSearchParameters.setSubmitTimeFrom(request.getParameter("submitTimeFrom"));
            docListSearchParameters.setSubmitTimeTo(request.getParameter("submitTimeTo"));
            docListSearchParameters.setPortalViewId(viewId);
            session.setAttribute("DocListSearchParameters",	docListSearchParameters);
        }
        String pageTitle;
        if("V004".equals(docListSearchParameters.getPortalViewId())){
        	pageTitle = "麻醉信息";
        } else {
        	pageTitle = "病例文档";
        }
        // 设置页面初始每页显示条目数，pagingContext默认值是10，(其他参数也可以修正，会影响检索结果)可根据需要修正。
        pagingContext.setRowCnt(10);
        // 调用业务层逻辑查询分页控制时对象集合，pagingContext返回值中会覆写totalRowCnt totalPageCnt
//        List<ClinicalDocument> list_paging = docService.selectAllDoc(patientSn,
//                docListSearchParameters,"other",pagingContext);
//        logger.debug("检索条件：文档作者{" + docListSearchParameters.getDocumentAuthor()
//            + "},文档名称{" + docListSearchParameters.getDocumentName() + "},文档类别{"
//            + docListSearchParameters.getDocumentType() + "}");
        
        // Author: tong_meng
        // Date : 2013/6/4 15:30
        // [BUG]0033455 ADD BEGIN 
        ModelAndView mav = new ModelAndView();
        String gotoType = request.getParameter("gotoType");
        List<ClinicalDocument> list_paging = portalService.selectPortalAllDoc(patientSn, docListSearchParameters, pagingContext);
        // 迁移到指定页面
        mav.setViewName("portal/portalDoc/portalClinicalDocumentList");

        // [BUG]0033455 ADD END 
        
        // 检索对象为空check
        if (list_paging == null || list_paging.size() == 0)
        {
            logger.debug("没有检索对象!");
            // 页面显示页面数赋值0
            pagingContext.setPageNo(0);
        }
        String signatureUrl = AppSettings.getConfig(Constants.SIGNATURE_URL);
        mav.addObject("signatureUrl", signatureUrl);
        // 设置每行显示查询的查询对象
        mav.addObject("docList", list_paging);
        // 加载pagingContext对象
        if(list_paging !=null)
        {
            pagingContext.setRows(list_paging.toArray());
        }

        // 页面初始化分页页码显示列表
        PagingHelper.initPaging(pagingContext);

        mav.addObject("pagingContext", pagingContext);
        logger.debug("当前总页数={}, 当前总条数={},当前页数={}", new Object[] {
                pagingContext.getTotalPageCnt(),
                pagingContext.getTotalRowCnt(), pagingContext.getPageNo() });
        // 加载页面检索项目
        mav.addObject("DocListSearchParameters", docListSearchParameters);
        mav.addObject("patientSn", patientSn);
        mav.addObject("documentType", docListSearchParameters.getDocumentType());
        // 标识符清空
        mav.addObject("senSave", null);
     // 患者信息
 		mav.addObject("patient", patient);
 		mav.addObject("vi", viewId);
 		mav.addObject("age", age);
 		mav.addObject("patientCrossIndex", patientCrossIndex);
 		mav.addObject("pageTitle", pageTitle);
        // 设置是否来自导航栏请求的识别标志
        // 根据不同界面显示不同detail的连接
        mav.addObject("gotoType", gotoType);
        return mav;
    }
    

    /**
     * V05-802:病历文书详细
     * 根据病历文书序列号，查看病历文书详细信息
     * @param documentSn 病历文书内部序列号
     * @return
     * @throws Exception
     */
    @RequestMapping("/detail_{documentSn}")
    public ModelAndView detail(@PathVariable("documentSn") String documentSn,
            HttpServletRequest request) throws Exception
    {
        String integratedFlag = request.getParameter("integratedFlag");
        
        ModelAndView mav = new ModelAndView();
        // 根据病历文书内部序列号查找病历文书信息
        ClinicalDocument doc = docService.selectDocDetail(documentSn);
        mav.addObject("doc", doc);
        // 文档服务接口ID
        String serviceId = doc.getServiceId();
        
        if(Boolean.parseBoolean(Constants.IF_DOC_PDF_SHOW)){
        	Map<String, Object> condition = new HashMap<String, Object>();
            condition.put("documentSn", documentSn);
            condition.put("deleteFlag", Constants.NO_DELETE_FLAG);
            List<Object> medicalImagingList = commonService.selectByCondition(
                    MedicalImaging.class, condition);
            if (medicalImagingList != null && medicalImagingList.size() > 0)
            {
                MedicalImaging medicalImaging = (MedicalImaging) medicalImagingList.get(0);
                // 文档类型设定
                mav.addObject("docType", medicalImaging.getImageFormat());
                String filePath = medicalImaging.getFilePath();
                if(FileUtils.isFileExsit(filePath))
                {
                    Random r = new Random();
					mav.addObject("docImagePath", "../doc/image_" + medicalImaging.getImagingSn() + "_.html?" + r.nextInt());
                }
            }
            mav.addObject("portalFlag", "true");
            mav.setViewName("doc/clinicalDocumentDetail");
            return mav;
        }
		
        
        // 如果对应的样式或xml文件为空，则转到错误页面。
        if (serviceId == null || serviceId.isEmpty()
            || doc.getDocumentCda() == null || doc.getDocumentCda().isEmpty())
        {
            mav.setViewName("doc/clinicalDocumentDetail");
            return mav;
        }
        // $Author:wei_peng
        // $Date:2012/10/26 9:57
        // $[BUG]0010715 ADD BEGIN
        if (Constants.ANESTHESIA_SERVICE_ID.equals(serviceId))
        {
            Map<String, Object> condition = new HashMap<String, Object>();
            condition.put("documentSn", documentSn);
            condition.put("deleteFlag", Constants.NO_DELETE_FLAG);
            List<Object> medicalImagingList = commonService.selectByCondition(
                    MedicalImaging.class, condition);
            if (medicalImagingList != null && medicalImagingList.size() > 0)
            {
                MedicalImaging medicalImaging = (MedicalImaging) medicalImagingList.get(0);
                if (medicalImaging.getImageContent() != null)
                {
                    Random r = new Random();
                    mav.addObject("docImagePath",
                            "../portalDoc/image_" + medicalImaging.getImagingSn()
                                + "_.html?" + r.nextInt());
                }
            }
            mav.setViewName("portal/portalDoc/portalClinicalDocumentDetail");
            return mav;
        }
        // $[BUG]0010715 ADD END

        // $Author:wei_peng
        // $Date:2012/8/17 13:49
        // $[BUG]0009010 ADD BEGIN
        // 判断现有样式表中是否含有对应的serviceId
        String path = request.getSession().getServletContext().getRealPath("/");
        File xslFolder = new File(path + "WEB-INF/xsls");
        File[] xsls = xslFolder.listFiles();
        // 样式表存在标志
        Boolean xslExsist = false;
        // 遍历系统中存在的样式表
        for (File xsl : xsls)
        {
            if (xsl.getName().equals(serviceId + ".xsl"))
            {
                xslExsist = true;
                break;
            }
        }
        // 如不存在对应的样式表则跳转到错误页面
        if (!xslExsist)
        {
            mav.setViewName("doc/clinicalDocumentDetail");
            return mav;
        }
        // $[BUG]0009010 ADD END

        // 创建xslt类型的视图对象（由spring提供），并设置视图相关参数。
        XsltView view = new XsltView();
        if(Constants.TRUE_FLAG.equals(integratedFlag) && "BS358".equals(serviceId))
        {
            view.setUrl("/WEB-INF/xsls/" + serviceId + "_Integrated.xsl");
        }
        else 
        {
            view.setUrl("/WEB-INF/xsls/" + serviceId+"_out" + ".xsl");
        }
        view.setSourceKey("cda");
        view.setApplicationContext(this.context);
        Reader reader = new StringReader(doc.getDocumentCda());
        // Author:jia_yanqing
        // Date:2013/1/30 13:30
        // [BUG]0013452 ADD BEGIN
        try
        {
            // 创建流读取文档中cda字符串的内容。
            if ("BS347".equals(serviceId))
            {
                // 获取指导建议内容的路径
                String rftContentPath = "//ClinicalDocument/component/structuredBody/component/section[code/@code='62385-0']/component/section[code/@code='370995009']/entry/observation/value/text()";
                reader = getXmlReader(reader, rftContentPath);
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        // [BUG]0013452 ADD END
        mav.addObject("cda", reader);
        mav.setView(view);
        return mav;
    }

    // $Author:wei_peng
    // $Date:2012/10/26 9:57
    // $[BUG]0010715 ADD BEGIN
    /**
     * 获取数据库中的麻醉记录图片，并写出图片到页面
     * @param imagingSn 医学影像内部序列号
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping("/image_{imagingSnOrDocumentLid}_{serviceId}")
    public ModelAndView selectImage(
            @PathVariable("imagingSnOrDocumentLid") String imagingSnOrDocumentLid,
            @PathVariable("serviceId") String serviceId,
            HttpServletResponse response) throws Exception
    {
        // 设置页面不缓存
        response.setHeader("Pragma", "No-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);

        if (StringUtils.isEmpty(serviceId))
        {
            String imagingSn = imagingSnOrDocumentLid;
            // 查询数据库中的图片
            MedicalImaging medicalImaging = (MedicalImaging) commonService.selectById(
                    MedicalImaging.class, imagingSn);

            if (medicalImaging.getImageContent() != null)
            {
                // 获取并写出图片流
                OutputStream out = response.getOutputStream();
                out.write(medicalImaging.getImageContent());
                out.flush();
                out.close();
            }
        }
        else
        {
            String documentLid = imagingSnOrDocumentLid;

            ClinicalDocument document = docService.selectDocDetail(documentLid,
                    serviceId);
            if (document != null)
            {
                Reader reader = new StringReader(document.getDocumentCda());
                String imageContentPath = "//ClinicalDocument/component/structuredBody/component/section/entry/observationMedia/value/text()";
                byte[] image = getImageStream(reader, imageContentPath);
                if (image != null)
                {
                    // 获取并写出图片流
                    OutputStream out = response.getOutputStream();
                    out.write(image);
                    out.flush();
                    out.close();
                }
            }
        }
        return null;
    }

    // $[BUG]0010715 ADD END

    /**
     * 返回List形式的文档类型集合
     */
    private List<String> getDocumentTypeList(String[] documentTypes)
    {
        List<String> result = new ArrayList<String>();
        for (String docType : documentTypes)
        {
            result.add(docType);
        }
        return result;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext)
            throws BeansException
    {
        this.context = applicationContext;
    }

    // Author:jia_yanqing
    // Date:2013/1/30 13:30
    // [BUG]0013452 ADD BEGIN
    /**
     * 将文档中的RTF形式的内容转换为字符串形式
     * @param reader contentPath
     * @return Rerder
     * @throws ParserConfigurationException
     * @throws SAXException
     * @throws IOException
     * @throws XPathExpressionException
     */
    public static Reader getXmlReader(Reader reader, String contentPath)
            throws ParserConfigurationException, SAXException, IOException,
            XPathExpressionException
    {
        DocumentBuilderFactory domFactory = DocumentBuilderFactory.newInstance();
        domFactory.setNamespaceAware(false);

        DocumentBuilder builder = domFactory.newDocumentBuilder();
        Document doc = builder.parse(new InputSource(reader));

        XPathFactory factory = XPathFactory.newInstance();
        XPath xpath = factory.newXPath();
        XPathExpression expr = xpath.compile(contentPath);

        Object result = expr.evaluate(doc, XPathConstants.NODESET);
        NodeList nodes = (NodeList) result;
        for (int i = 0; i < nodes.getLength(); i++)
        {
            nodes.item(i).setNodeValue(
                    transferRtfToString(nodes.item(i).getNodeValue()));
        }

        return new StringReader(docToString(doc));
    }

    public static byte[] getImageStream(Reader reader, String contentPath)
            throws ParserConfigurationException, SAXException, IOException,
            XPathExpressionException
    {
        DocumentBuilderFactory domFactory = DocumentBuilderFactory.newInstance();
        domFactory.setNamespaceAware(false);

        DocumentBuilder builder = domFactory.newDocumentBuilder();
        Document doc = builder.parse(new InputSource(reader));

        XPathFactory factory = XPathFactory.newInstance();
        XPath xpath = factory.newXPath();
        XPathExpression expr = xpath.compile(contentPath);

        Object result = expr.evaluate(doc, XPathConstants.NODESET);
        NodeList nodes = (NodeList) result;
        byte[] image = null;
        for (int i = 0; i < nodes.getLength(); i++)
        {
            image = Base64.decodeBase64(nodes.item(i).getNodeValue());
        }

        return image;
    }

    /**
     * 转换rtf字符串
     * @param rtfStr
     */
    private static String transferRtfToString(String rtfStr)
    {

        RTFEditorKit kit = new RTFEditorKit();
        byte[] b = null;
        b = rtfStr.getBytes();
        ByteArrayInputStream bais = new ByteArrayInputStream(b);
        DefaultStyledDocument doc = new DefaultStyledDocument();
        String text = null;
        try
        {
            kit.read(bais, doc, 0);
            text = new String(doc.getText(0, doc.getLength()).getBytes(
                    "ISO8859_1"), "GBK");
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        catch (BadLocationException e)
        {
            e.printStackTrace();
        }
        finally
        {
            try
            {
                bais.close();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
        return text;
    }

    /**
     * 将Document转换为String
     * @param doc
     * @return
     */
    public static String docToString(Document doc)
    {
        String xmlStr = "";
        try
        {
            TransformerFactory tf = TransformerFactory.newInstance();
            Transformer t = tf.newTransformer();
            t.setOutputProperty("encoding", "UTF-8");
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            t.transform(new DOMSource(doc), new StreamResult(bos));
            xmlStr = bos.toString();
        }
        catch (TransformerConfigurationException e)
        {
            e.printStackTrace();
        }
        catch (TransformerException e)
        {
            e.printStackTrace();
        }
        return xmlStr;
    }
    // [BUG]0013452 ADD END
}
