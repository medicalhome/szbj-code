//package com.founder.cdr.batch.writer;
//
//import java.math.BigDecimal;
//import java.text.SimpleDateFormat;
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.HashMap;
//import java.util.Iterator;
//import java.util.List;
//import java.util.Map;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//import com.founder.cdr.core.Constants;
//import com.founder.cdr.hl7.dto.BaseDto;
//import com.founder.cdr.entity.MedicalVisit;
//import com.founder.cdr.hl7.dto.prpain400001uv02.*;
//import com.founder.cdr.service.*;
//import com.founder.cdr.util.DateUtils;
//import com.founder.cdr.util.StringUtils;
//import com.founder.fasf.core.util.daohelper.entity.EntityDao;
//import com.founder.sqlparser.util.StringUtil;
//
///**
// * 分诊Writer 注意：这里需要根据业务，添加判断条件。如：
// * 1：如果当前消息是子消息，父消息如果存在，则保存子消息内容；如果父消息不存在，则不保存子消息内容。 2：如果是父消息，直接保存父消息内容。
// * 
// * @author zhangbin
// * @version 1.0
// */
//
//@Component(value = "prpain400001uv02Writer")
//public class PRPAIN400001UV02Writer implements BusinessWriter<PRPAIN400001UV02>
//{
//    private static final Logger logger = LoggerFactory.getLogger(PRPAIN400001UV02Writer.class);
//
//    @Autowired
//    private CommonService commonService;
//
//    @Autowired
//    private VisitService visitService;
//
//    // 就診記錄
//    private MedicalVisit medicalVisit;
//
//    // 就诊内部序列号
//    private BigDecimal visitSn;
//
//    // 患者内部序列号
//    private BigDecimal patientSn;
//
//    // 就诊次数
//    private Integer visitTimes;
//
//    /**
//     * 数据校验
//     * 
//     * @param baseDto
//     */
//    @Override
//    public boolean validate(PRPAIN400001UV02 t)
//    {
//        return true;
//    }
//
//    @Override
//    public boolean checkDependency(PRPAIN400001UV02 t)
//    {
//        medicalVisit = getMedicalVisit(t);
//        if (medicalVisit != null)
//        {
//            visitSn = medicalVisit.getVisitSn();
//            patientSn = medicalVisit.getPatientSn();
//            logger.warn("分诊单消息更新场合,就诊内部序列号 {},患者内部序列号{}", visitSn, patientSn);
//            return true;
//        }
//        return true;
//    }
//
//    /**
//     * 更新分诊表
//     */
//    private void updateVisit(PRPAIN400001UV02 prpain400001uv02,
//            BigDecimal updateCount, Date createTime)
//    {
//        // 就诊表存在数据，更新分诊表
//        MedicalVisit medicalVisit = new MedicalVisit();
//        // $Author :tongmeng
//        // $Date : 2012/5/29 14:30$
//        // [BUG]0006657 MODIFY BEGIN
//        // 患者本地ID号
//        String patientLid = prpain400001uv02.getPatientLid();
//        logger.warn("分诊单消息更新场合,患者本地ID {}", patientLid);
//        // [BUG]0006657 MODIFY END
//
//        String patientDomain = prpain400001uv02.getPatientDomain();
//        // 就诊信息取得
//        medicalVisit.setVisitSn(visitSn);
//        // 就诊日期
//        medicalVisit.setVisitDate(DateUtils.stringToDate(prpain400001uv02.getVisitDate()));
//        // 就诊医生代码
//        medicalVisit.setVisitDoctor(prpain400001uv02.getVisitDoctor());
//        // 就诊医生姓名
//        medicalVisit.setVisitDoctorName(prpain400001uv02.getVisitDoctorName());
//        // 就诊次数
//        medicalVisit.setVisitTimes(StringUtils.strToBigDecimal(prpain400001uv02.getVisitTimes()));
//        // 就诊状态
//        medicalVisit.setVisitStatus(prpain400001uv02.getVisitStatus());
//        // 就诊科室
//        medicalVisit.setVisitDept(prpain400001uv02.getVisitDept());
//        
//        // $Author :tong_meng
//        // $Date : 2012/6/26 10:33$
//        // [BUG]0007418 MODIFY BEGIN
//        // 就诊类别代码
//        medicalVisit.setVisitTypeCode(prpain400001uv02.getVisitType());
//        // [BUG]0007418 MODIFY END
//        
//        // $Author :tong_meng
//        // $Date : 2012/6/26 10:33$
//        // [BUG]0007418 MODIFY BEGIN
//        // 就诊类别名称
////        medicalVisit.setVisitTypeName(prpain400001uv02.getVisitTypeName());
//        // [BUG]0007418 MODIFY END
//
//        // 更新回数
//        medicalVisit.setUpdateCount(updateCount);
//        // 创建时间
//        medicalVisit.setCreateTime(createTime);
//        // 更新时间
//        medicalVisit.setUpdateTime(DateUtils.getSystemTime());
//        // 删除标志
//        medicalVisit.setDeleteFlag(Constants.NO_DELETE_FLAG);
//        commonService.updatePartially(medicalVisit, "visitSn", "visitDate",
//                "visittimes", "visitDoctor", "visitDoctorName", "visitStatus",
//                "visitDept", "updateCount", "updateTime", "deleteFlag");
//    }
//
//    @Override
//    public void saveOrUpdate(PRPAIN400001UV02 t)
//    {
//        // 就诊表更新
//        updateVisit(t, medicalVisit.getUpdateCount(),
//                medicalVisit.getCreateTime());
//
//    }
//
//    /**
//     * 得到消息关联的就诊记录
//     * 
//     * @param pr02
//     * @return 就诊记录
//     */
//    public MedicalVisit getMedicalVisit(PRPAIN400001UV02 pr02)
//    {
//        // $Author :tongmeng
//        // $Date : 2012/5/29 14:30$
//        // [BUG]0006657 MODIFY BEGIN
//        // 患者本地ID号
//        String patientLid = pr02.getPatientLid();
//        logger.warn("分诊单消息更新场合,患者本地ID {}", patientLid);
//        // [BUG]0006657 MODIFY END
//        // 患者域ID
//        String patientDomain = pr02.getPatientDomain();
//        // 就诊次数
//        String visitTimes = pr02.getVisitTimes();
//        logger.warn("分诊消息更新场合,就诊次数 {}", visitTimes);
//        // 就诊信息取得
//        MedicalVisit medicalVisit = commonService.mediVisit(patientDomain,
//                patientLid, Integer.parseInt(visitTimes));
//        return medicalVisit;
//    }
// }
