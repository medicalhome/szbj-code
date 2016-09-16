package com.founder.cdr.hl7.dto.poorin200901uv02;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.validator.constraints.NotEmpty;

import com.founder.cdr.hl7.dto.BaseDto;
import com.founder.cdr.util.DateUtils;

public class POORIN200901UV02 extends BaseDto
{
    /**
     * 域ID
     */
//    @NotEmpty(message = "{POORIN200901UV02.patientDomain}")
    private String patientDomain;

    /**
     * 门诊患者ID
     */
    @NotEmpty(message = "{POORIN200901UV02.patientLid}")
    private String patientLid;

    /**
     * 诊断次数
     */
 //   @NotEmpty(message = "{POORIN200901UV02.visitTimes}")
    private String visitTimes;

    /**
     * 床号
     */
    private String bedNo;

    /**
     * 病区ID
     */
    private String wardsId;

    /**
     * 病区名称
     */
    private String wardsName;

    /**
     * 开嘱科室ID
     */
    private String orderDept;

    /**
     * 开嘱科室名称
     */
    private String orderDeptName;

    /**
     * 开嘱人ID
     */
    private String orderPerson;

    /**
     * 开嘱人姓名
     */
    private String orderPersonName;

    /**
     * 开嘱时间
     */
    private String orderTime;

    /**
     * 医嘱确认人ID
     */
    private String confirmPerson;

    /**
     * 医嘱确认人姓名
     */
    private String confirmPersonName;

    /**
     * 确认时间
     */
    private String confirmTime;

    /**
     * 检查申请单
     */
    @NotEmpty(message = "{POORIN200901UV02.examinationApplications}")
    private List<ExaminationApplication> examinationApplications;

    /**
     * 触发事件
     */
    @NotEmpty(message = "{POORIN200901UV02.triggerEventCode}")
    private String triggerEventCode;

    /**
     * 诊断
     */
    private String diagnosis;

    /**
     * 既往史疾病
     */
    private List<PastDisease> pastDiseases;
    // $Author :chang_xuewen
    // $Date : 2014/1/22 10:30$
    // [BUG]0042086 MODIFY BEGIN 
    // $Author :chang_xuewen
    // $Date : 2013/12/03 11:00$
    // [BUG]0040271 ADD BEGIN
    /*@NotEmpty(message = "{POORIN200901UV02.orgCode}")*/
    private String orgCode;

	/*@NotEmpty(message = "{POORIN200901UV02.orgName}")*/
    private String orgName;
    
    /**
     * 就诊类型代码
     */
//    @NotEmpty(message = "{POORIN200901UV02.visitTypeCode}")
    private String visitTypeCode;
    
    /**
     * 就诊类型名称
     */
    
    private String visitTypeName;
    
    /**
     * 就诊流水号
     */
//    @NotEmpty(message = "{POORIN200901UV02.visitOrdNo}")
    private String visitOrdNo;
    
    /*
     * Author: yu_yzh
     * Date: 2015/7/27 09:06
     * */
    /*
     * v2消息中OBX段数据
     * */
//    private List<Obxs> obxs;
    
    
    /*
     * $Author: yu_yzh 
     * $Date: 2015/8/7 9:14
     * 发送方
     * ADD BEGIN
     * */
    
    private String sender;
    
    // ADD END
    
    public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
    // [BUG]0040271 ADD END
    // [BUG]0042086 MODIFY END 
    public String getPatientDomain()
    {
        return patientDomain;
    }

    public void setPatientDomain(String patientDomain){
    	this.patientDomain = patientDomain;
    }
    
    public String getPatientLid()
    {
        return patientLid;
    }

    public String getVisitTimes()
    {
        return visitTimes;
    }

    public String getOrgCode() {
		return orgCode;
	}

	public String getOrgName() {
		return orgName;
	}

	public String getBedNo()
    {
        return bedNo;
    }

    public String getWardsId()
    {
        return wardsId;
    }

    public String getWardsName()
    {
        return wardsName;
    }

    public String getOrderDept()
    {
        return orderDept;
    }

    public String getOrderDeptName()
    {
        return orderDeptName;
    }

    public String getOrderPerson()
    {
        return orderPerson;
    }

    public String getOrderPersonName()
    {
        return orderPersonName;
    }

    public String getOrderTime()
    {
        return orderTime;
    }

    public String getConfirmPerson()
    {
        return confirmPerson;
    }

    public String getConfirmPersonName()
    {
        return confirmPersonName;
    }

    public String getConfirmTime()
    {
        return confirmTime;
    }

    public List<ExaminationApplication> getExaminationApplications()
    {
        return examinationApplications;
    }

    public String getTriggerEventCode()
    {
        return triggerEventCode;
    }

    public String getDiagnosis()
    {
        return diagnosis;
    }

    public List<PastDisease> getPastDiseases()
    {
        return pastDiseases;
    }
	public String getVisitTypeCode() {
		return visitTypeCode;
	}

	public void setVisitTypeCode(String visitTypeCode) {
		this.visitTypeCode = visitTypeCode;
	}

	public String getVisitTypeName() {
		return visitTypeName;
	}

	public void setVisitTypeName(String visitTypeName) {
		this.visitTypeName = visitTypeName;
	}

	public String getvisitOrdNo() {
		return visitOrdNo;
	}

	public void setvisitOrdNo(String visitOrdNo) {
		this.visitOrdNo = visitOrdNo;
	}

	/*
	 * 由于V2消息中obx段中信息不能直接取出到bean中，做一步转换
	 * */
	public void changeObxsToDiagnosisAndPastDiseases(){
		/*if(this.obxs != null && !obxs.isEmpty()){
			for(Obxs obx : obxs){
				if(obx.getValue() == null || obx.getIdentifier() == null) continue;
				if(obx.getIdentifier().equals("SIGN")){
					
				} else if(obx.getIdentifier().equals("HISTORY")){
					if(this.pastDiseases == null){
						this.pastDiseases = new ArrayList<PastDisease>();
					}
					PastDisease pd = new PastDisease();
					pd.setPastDiseaseName(obx.getValue());
					this.pastDiseases.add(pd);
				} else if(obx.getIdentifier().equals("SITE")){
					String site = obx.getValue();
					site = site.replace("部位：", "");
					site = site.replace("部位:", "");
					this.examinationApplications.get(0).getExaminationOrderDtos().get(0).setRegionName(obx.getValue());
				} else if(obx.getIdentifier().equals("DIAG")){
					this.diagnosis = obx.getValue();
					diagnosis = diagnosis.replace("诊断：", "");
					diagnosis = diagnosis.replace("诊断:", "");
				}
			}
		}*/
		int i = 0;
		for(ExaminationApplication ea : examinationApplications){
			List<Obxs> obxs = ea.getObxs();
			if(obxs != null && !obxs.isEmpty()){
				for(Obxs obx : obxs){
					if(obx.getValue() == null || obx.getIdentifier() == null) continue;
					if(obx.getIdentifier().equals("SIGN")){
						
					} else if(obx.getIdentifier().equals("HISTORY")){
						/*if(this.pastDiseases == null){
							this.pastDiseases = new ArrayList<PastDisease>();
						}
						PastDisease pd = new PastDisease();
						pd.setPastDiseaseName(obx.getValue());
						this.pastDiseases.add(pd);*/
					} else if(obx.getIdentifier().equals("SITE")){
						String site = obx.getValue();
						site = site.replace("部位：", "");
						site = site.replace("部位:", "");
						this.examinationApplications.get(i).getExaminationOrderDtos().get(0).setRegionName(site);
					} else if(i == 0 && obx.getIdentifier().equals("DIAG")){
						this.diagnosis = obx.getValue();
						diagnosis = diagnosis.replace("诊断：", "");
						diagnosis = diagnosis.replace("诊断:", "");
					}
				}
			}
			i++;
		}
	}
	
    @Override
    public String toString()
    {
        StringBuilder builder = new StringBuilder();
        builder.append("MessageId=");
        builder.append(getMessage().getId()+",");
        builder.append("Datetime=");
        builder.append(DateUtils.dateToString(getMessage().getDatetime(),
                null) + ",");
        builder.append("POORIN200901UV02 [triggerEventCode=");
        builder.append(triggerEventCode);
        builder.append(", patientDomain=");
        builder.append(patientDomain);
        builder.append(", patientLid=");
        builder.append(patientLid);
        builder.append(", visitTimes=");
        builder.append(visitTimes);
        builder.append(", examinationApplications=");
        builder.append(examinationApplications);
        builder.append(", diagnosis=");
        builder.append(diagnosis);
        builder.append(", pastDiseases=");
        builder.append(pastDiseases);
        builder.append(", orgCode=");
        builder.append(orgCode);
        builder.append(", orgName=");
        builder.append(orgName);
        builder.append(", visitOrdNo");
        builder.append(visitOrdNo);
        builder.append(", visitTypeCode");
        builder.append(visitTypeCode);
        builder.append(", visitTypeName");
        builder.append(visitTypeName);
        builder.append("]");
        return builder.toString();
    }

	public String getSender() {
		return sender;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}

}
