/**
 * Copryright
 */
package com.founder.cdr.batch.writer;

import java.math.BigDecimal;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.founder.cdr.cache.DictionaryCache;
import com.founder.cdr.core.Constants;
import com.founder.cdr.entity.MedicalVisit;
import com.founder.cdr.hl7.dto.prpain402003uv02.PRPAIN402003UV02;
import com.founder.cdr.service.CommonService;
import com.founder.cdr.service.VisitService;
import com.founder.cdr.util.DateUtils;
import com.founder.cdr.util.DictionaryUtils;
import com.founder.cdr.util.StringUtils;

/**
 * 出院消息数据接入writer
 * @author jin_peng
 */
@Component(value = "prpain402003uv02Writer")
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class PRPAIN402003UV02Writer implements BusinessWriter<PRPAIN402003UV02>
{

    private static final Logger logger = LoggerFactory.getLogger(PRPAIN402003UV02Writer.class);

    private static final Logger loggerBS313 = LoggerFactory.getLogger("BS313");

    @Autowired
    private CommonService commonService;

    @Autowired
    private VisitService visitService;

    // 就诊对象
    private MedicalVisit medicalVisit;

    // 患者内部序列号
    private BigDecimal patientSn;

    // 系统时间
    private Date systemTime = DateUtils.getSystemTime();

    // 消息id
    private String serviceId;

    /**
     * 出院信息消息业务校验
     */
    @Override
    public boolean validate(PRPAIN402003UV02 prpain402003uv02)
    {
    	// $Author :chang_xuewen
        // $Date : 2014/1/22 10:30$
        // [BUG]0042086 MODIFY BEGIN
        String headOrgCode = prpain402003uv02.getMessage().getOrgCode();
        String orgCode = prpain402003uv02.getOrgCode();

        /*if (!headOrgCode.equals(orgCode))
        {
            logger.debug("消息体与消息头医疗机构代码不相同: {}", "orgCode = " + orgCode
                + "; headOrgCode = " + headOrgCode);

            loggerBS313.error("Message:{},validate:{}",
                    prpain402003uv02.toString(), "消息体与消息头医疗机构代码不相同, orgCode = "
                        + orgCode + "; headOrgCode = " + headOrgCode);

            return false;
        }*/
        if(StringUtils.isEmpty(prpain402003uv02.getOrgCode())){
        	// $Author :yang_mingjie
        	// $Date : 2014/06/26 10:09$
        	// [BUG]0045630 MODIFY BEGIN 
        	prpain402003uv02.setOrgCode(Constants.HOSPITAL_CODE);
        	prpain402003uv02.setOrgName(Constants.HOSPITAL_NAME);
        	//[BUG]0045630 MODIFY END
        }        
        // [BUG]0042086 MODIFY END
        return true;
    }

    /**
     * 有关联时进行业务检验 验证出院消息所对应的入院记录是否存在
     */
    @Override
    public boolean checkDependency(PRPAIN402003UV02 prpain402003uv02,
            boolean flag)
    {
        // $Author :tongmeng
        // $Date : 2012/5/29 14:30$
        // [BUG]0006657 MODIFY BEGIN
        // 获得患者本地ID
        String patientLid = prpain402003uv02.getPatientLid();
        // [BUG]0006657 MODIFY END
        // 获得患者域ID
        String patientDomain = prpain402003uv02.getPatientDomain();
        // 获得就诊次数
        String visitTimes = prpain402003uv02.getVisitTimes();
        // modify by li_shenggen @ 2014/11/18 16:28
        //增加就诊流水号，就诊类型查询
        medicalVisit = commonService.mediVisit(patientDomain, patientLid,
                visitTimes, prpain402003uv02.getOrgCode(), prpain402003uv02.getVisitOrdNo(), prpain402003uv02.getVisitType());
        // end 
        if (medicalVisit != null)
        {
            patientSn = medicalVisit.getPatientSn();
            logger.debug("此条出院消息已找到入院信息true: {}", " patientLid = " + patientLid
                + "; patientDomain = " + patientDomain + "; visitTimes = "
                + visitTimes + "; orgCode = " + prpain402003uv02.getOrgCode()+ "; visitOrdNo = "
                        + prpain402003uv02.getVisitOrdNo() + "; visitType = " + prpain402003uv02.getVisitType());
            return true;
        }

        logger.error("MessageId:{};出院消息关联入院信息不存在，患者本地ID:" + patientLid
            + "，域ID：" + patientDomain + "，就诊次数：" + visitTimes + "; orgCode = "
            + prpain402003uv02.getOrgCode()+ "; visitOrdNo = "
                    + prpain402003uv02.getVisitOrdNo() + "; visitType = " + prpain402003uv02.getVisitType(),
                prpain402003uv02.getMessage().getId());
        if (flag)
        {
            loggerBS313.error("Message:{},checkDependency:{}",
                    prpain402003uv02.toString(),
                    "出院消息关联入院信息不存在，患者本地ID:" + patientLid + "，域ID："
                        + patientDomain + "，就诊次数：" + visitTimes
                        + "; orgCode = " + prpain402003uv02.getOrgCode()+ "; visitOrdNo = "
                                + prpain402003uv02.getVisitOrdNo() + "; visitType = " + prpain402003uv02.getVisitType());
        }
        return false;
    }

    /**
     * 校验成功后把取到的相应数据进行出院信息的更新操作
     * @param prpain402003uv02 xml文件中获取的出院信息相应业务数据
     */
    @Override
    @Transactional
    public void saveOrUpdate(PRPAIN402003UV02 prpain402003uv02)
    {
        serviceId = prpain402003uv02.getMessage().getServiceId();

        // 更新就诊表中已经存在的住院信息,将出院信息插入
        MedicalVisit medicalVisit = this.medicalVisit;
        if(!Constants.CANCEL_REGI_MESSAGE_FLAG.equals(prpain402003uv02.getTriggerEvent())){
        	medicalVisit = getMedicalVisit(prpain402003uv02);
        }
        // $Author: wei_peng
        // $Date: 2012/6/18 17:11$
        // [BUG]0007125 ADD BEGIN
        if (Constants.CANCEL_MESSAGE_FLAG.equals(prpain402003uv02.getTriggerEvent()))
        {
            // Author: jia_yanqing
            // Date: 2013/3/6 14:11$
            // [BUG]0014327 MODIFY BEGIN
            // Author: jia_yanqing
            // Date: 2013/3/4 9:11$
            // [BUG]0014233 MODIFY BEGIN
            // 获取该患者的最小住院次数-1
            int minVisitTimes = visitService.selectMinVisitTimes(patientSn, prpain402003uv02.getOrgCode());
            if (minVisitTimes < 0)
            {
                medicalVisit.setVisitTimes(new BigDecimal(minVisitTimes));
            }
            else
            {
                medicalVisit.setVisitTimes(new BigDecimal(-1));
            }
            medicalVisit.setDeleteFlag(Constants.DELETE_FLAG);
            // 更新时间
            medicalVisit.setDeleteTime(systemTime);
            // 更新者
            medicalVisit.setDeleteby(serviceId);
            // [BUG]0042245 MODIFY END

            // $Author :tong_meng
            // $Date : 2012/6/26 10:33$
            // [BUG]0007418 MODIFY BEGIN
            // 就诊状态代码
            medicalVisit.setVisitStatusCode(Constants.VISIT_STATUS_OUT_HOSPITAL);
            // [BUG]0007418 MODIFY END

            // $Author :tong_meng
            // $Date : 2012/6/26 10:33$
            // [BUG]0007418 MODIFY BEGIN
            // 就诊状态名称
            if(DictionaryCache.getDictionary(Constants.DOMAIN_VISIT_STATUS) != null){
            	medicalVisit.setVisitStatusName(DictionaryCache.getDictionary(
                        Constants.DOMAIN_VISIT_STATUS).get(
                        Constants.VISIT_STATUS_OUT_HOSPITAL));
            }
            
            // [BUG]0007418 MODIFY END

            commonService.updatePartially(medicalVisit, "dischargeDate",
                    "dischargeStatusCode", "dischargeStatusName",
                    "dischargeDeptId", "dischargeDeptName", "dischargeWardId",
                    "dischargeBedNo", "deleteFlag", "deleteTime",
                    "visitStatusCode", "visitStatusName", "visitTimes",
                    "deleteby", "orgCode", "orgName");

            // [BUG]0014233 MODIFY END
            // Author: jia_yanqing
            // Date: 2013/3/4 9:11$
            // [BUG]0014233 ADD BEGIN
            // commonService.delete(medicalVisit);
            // [BUG]0014233 ADD END
            // [BUG]0014327 MODIFY END
        }
        // [BUG]0007125 ADD END
        // Author: yu_yzh
        // Date:2016/4/25 10:58
        // [BUG] 0067499 ADD BEGIN
        else if(Constants.CANCEL_REGI_MESSAGE_FLAG.equals(prpain402003uv02.getTriggerEvent())){
        	medicalVisit.setUpdateby(serviceId);
        	medicalVisit.setUpdateTime(systemTime);
        	medicalVisit.setVisitStatusCode(Constants.VISIT_STATUS_IN_HOSPITAL);
        	medicalVisit.setVisitStatusName(DictionaryUtils.getNameFromDictionary(Constants.DOMAIN_VISIT_STATUS, Constants.VISIT_STATUS_IN_HOSPITAL));
        	commonService.updatePartially(medicalVisit, 
        			"visitStatusCode", "visitStatusName", "updateby");
        }
        // [BUG] 0067499 ADD END
        else
        {
            // 更新
            medicalVisit.setUpdateby(serviceId);

            commonService.updatePartially(medicalVisit, "dischargeDate",
                    "dischargeStatusCode", "dischargeStatusName",
                    "dischargeDeptId", "dischargeDeptName", "dischargeWardId",
                    "dischargeBedNo", "updateTime", "visitStatusCode",
                    "visitStatusName", "updateby", "orgCode", "orgName");
        }
    }

    /**
     * 获取出院信息
     * @param prpain402003uv02 xml文件中获取的出院信息相应业务数据
     * @return 出院信息
     */
    private MedicalVisit getMedicalVisit(PRPAIN402003UV02 prpain402003uv02)
    {
        // 设置出院日期
    	if(prpain402003uv02.getDischargeDate() != null){
    		medicalVisit.setDischargeDate(DateUtils.stringToDate(prpain402003uv02.getDischargeDate()));
    	}

        // Author :jia_yanqing
        // Date : 2012/10/18 14:33
        // [BUG]0009980 ADD BEGIN
        // $Author :tong_meng
        // $Date : 2012/9/27 11:00$
        // [BUG]0009980 MODIFY BEGIN
        // $Author :tong_meng
        // $Date : 2012/6/26 10:33$
        // [BUG]0007418 MODIFY BEGIN
        // 设置出院状态代码
        medicalVisit.setDischargeStatusCode(prpain402003uv02.getDischargeStatus());
        // [BUG]0007418 MODIFY END
        // [BUG]0009980 MODIFY END
        // [BUG]0009980 ADD BEGIN

        // $Author :tong_meng
        // $Date : 2012/6/26 10:33$
        // [BUG]0007418 ADD BEGIN
        // 设置出院状态名称
        medicalVisit.setDischargeStatusName(prpain402003uv02.getDischargeStatusName());
        // [BUG]0007418 ADD END

        // $Author :tong_meng
        // $Date : 2012/6/25 14:33$
        // [BUG]0007418 MODIFY BEGIN
        // 设置出院科室ID
        medicalVisit.setDischargeDeptId(prpain402003uv02.getDischargeDept());
        // [BUG]0007418 MODIFY END

        // $Author :tong_meng
        // $Date : 2012/6/25 14:33$
        // [BUG]0007418 ADD BEGIN
        // 设置出院科室名称
        medicalVisit.setDischargeDeptName(prpain402003uv02.getDischargeDeptName());
        // [BUG]0007418 ADD END

        // 设置出院病区编码
        medicalVisit.setDischargeWardId(prpain402003uv02.getDischargeWards());

        // $Author :jia_yanqing
        // $Date : 2012/9/6 11:33$
        // [BUG]0008782 ADD BEGIN
        // 设置出院病区名称
        medicalVisit.setDischargeWardName(prpain402003uv02.getDischargeWardsName());
        // [BUG]0008782 ADD END

        // Author :jia_yanqing
        // Date : 2012/10/18 14:33
        // [BUG]0009980 ADD BEGIN
        // 设置出院状态/就诊状态
//        medicalVisit.setVisitStatusCode(Constants.VISIT_STATUS_DISCHARGE_HOSPITAL);
//        // 设置出院状态/就诊状态名称
//        if(DictionaryCache.getDictionary(Constants.DOMAIN_VISIT_STATUS) != null){
//        	medicalVisit.setVisitStatusName(DictionaryCache.getDictionary(
//                    Constants.DOMAIN_VISIT_STATUS).get(
//                    Constants.VISIT_STATUS_DISCHARGE_HOSPITAL));
//        }
        // [BUG]0009980 ADD END
        String visitStatusCode = null;
        if(Constants.REGISTER_MESSAGE_FLAG.equals(prpain402003uv02.getTriggerEvent())){
        	visitStatusCode = Constants.VISIT_STATUS_DISCHARGE_REGISTERED;
        } else {
        	visitStatusCode = Constants.VISIT_STATUS_DISCHARGE_HOSPITAL;
        }
        medicalVisit.setVisitStatusCode(visitStatusCode);
        medicalVisit.setVisitStatusName(DictionaryUtils.getNameFromDictionary(
        		Constants.DOMAIN_VISIT_STATUS, visitStatusCode));

        // 设置出院床号
        medicalVisit.setDischargeBedNo(prpain402003uv02.getDischargeBedNo());

        // 设置该记录更新时间
        medicalVisit.setUpdateTime(systemTime);

        // 设置医疗机构代码
        medicalVisit.setOrgCode(prpain402003uv02.getOrgCode());

        // 设置医疗机构名称
        medicalVisit.setOrgName(prpain402003uv02.getOrgName());

        return medicalVisit;
    }

}
