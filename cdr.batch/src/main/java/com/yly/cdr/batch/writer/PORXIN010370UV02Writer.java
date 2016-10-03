package com.yly.cdr.batch.writer;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.yly.cdr.core.Constants;
import com.yly.cdr.entity.DispensingRecord;
import com.yly.cdr.entity.MedOrderDispensingRecord;
import com.yly.cdr.entity.MedicalVisit;
import com.yly.cdr.entity.MedicationOrder;
import com.yly.cdr.hl7.dto.Drugs;
import com.yly.cdr.hl7.dto.Prescription;
import com.yly.cdr.hl7.dto.porxin010370uv02.PORXIN010370UV02;
import com.yly.cdr.service.CommonService;
import com.yly.cdr.service.DrugListService;
import com.yly.cdr.util.DataMigrationUtils;
import com.yly.cdr.util.DateUtils;
import com.yly.cdr.util.DictionaryUtils;
import com.yly.cdr.util.StringUtils;

@Component(value = "porxin010370uv02Writer")
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class PORXIN010370UV02Writer implements BusinessWriter<PORXIN010370UV02>
{
    private static final Logger logger = LoggerFactory.getLogger(PORXIN010370UV02Writer.class);

    private static final Logger loggerBS307 = LoggerFactory.getLogger("BS307");
    
    @Autowired
    private DrugListService drugListService;
    @Autowired
    private CommonService commonService;
    
    // 域ID
    private String patientDomain;
    // 患者LID
    private String patientLid;
    // 就诊内部序列号
    private BigDecimal visitSn;
/*    // 就诊流水号
    private String visitOrdNo;
    // 就诊类型
    private String visitType;
    // 就诊次数
    private String visitTimes;*/
    // 系统时间
    private Date systemDate = DateUtils.getSystemTime();
    
    // 需要新增的发药记录集合
    List<DispensingRecord> dispensingRecordList;
    // 需要新增的用药医嘱与发药记录集合
    List<MedOrderDispensingRecord> medOrderDispensingRecordList;
    // 需要更新医嘱状态的用药医嘱记录集合
    List<MedicationOrder> medicationOrderList;
    
    private List<DispensingRecord> updateDispensingRecordList;
    private List<MedOrderDispensingRecord> updateMedOrderDispensingRecordList; 
    
    @Override
    public boolean validate(PORXIN010370UV02 porxin010370uv02)
    {
    	// $Author :chang_xuewen
        // $Date : 2014/1/22 10:30$
        // [BUG]0042086 MODIFY BEGIN
        // $Author:tong_meng
        // $Date:2013/12/03 11:00
        // [BUG]0040270 ADD BEGIN
        /*if (!porxin010370uv02.getOrgCode().equals(
                porxin010370uv02.getMessage().getOrgCode()))
        {
            logger.error("消息头中的医疗机构代码与消息体中的医疗机构代码不一致！" +
                    "消息头医疗机构代码：{}，消息体医疗机构代码：{}",
                    porxin010370uv02.getMessage().getOrgCode(),
                    porxin010370uv02.getOrgCode());
            loggerBS307.error("消息头中的医疗机构代码与消息体中的医疗机构代码不一致！" +
                    "消息头医疗机构代码：{}，消息体医疗机构代码：{}",
                    porxin010370uv02.getMessage().getOrgCode(),
                    porxin010370uv02.getOrgCode());
            return false;
        }*/
        // [BUG]0040270 ADD END
    	if(StringUtils.isEmpty(porxin010370uv02.getOrgCode())){
    		// $Author :yang_mingjie
        	// $Date : 2014/06/26 10:09$
        	// [BUG]0045630 MODIFY BEGIN 
    		porxin010370uv02.setOrgCode(Constants.HOSPITAL_CODE);
    		porxin010370uv02.setOrgName(Constants.HOSPITAL_NAME);
    		//[BUG]0045630 MODIFY END
        } 
    	// [BUG]0042086 MODIFY END
        //loggerBS307.info("Message:{}",porxin010370uv02.toString());
        return true;
    }

    @Override
    public boolean checkDependency(PORXIN010370UV02 porxin010370uv02, boolean flag)
    {
        String newUpFlag = porxin010370uv02.getNewUpFlag();
        if (isNewMessage(porxin010370uv02) || isOkMessage(porxin010370uv02)
        		|| isReturnMessage(porxin010370uv02))
        {
        	int doNotExistCount = checkMedicalOrder(porxin010370uv02,flag);
            return doNotExistCount == 0;
        }
        else
        {
            loggerBS307.error("Message:{},checkDependency:{}",
                    porxin010370uv02.toString(), "错误的消息交互类型：" + newUpFlag);
            return false;
        }
    }

    @Override
    @Transactional
    public void saveOrUpdate(PORXIN010370UV02 porxin010370uv02)
    {
    	cleanMedicationOrder();
    	if(isNewMessage(porxin010370uv02) || isOkMessage(porxin010370uv02)){
            // 消息中的处方或者摆药单集合
            List<Prescription> prescriptionList = porxin010370uv02.getPrescription();
            // 获取需要新增和更新的发药记录，发药记录与用药医嘱，用药医嘱集合
            getDispensingRecordList(prescriptionList,porxin010370uv02);
            drugListService.saveDispensing(dispensingRecordList,
                    medOrderDispensingRecordList, medicationOrderList);
            drugListService.updateReturnList(updateDispensingRecordList,
                    updateMedOrderDispensingRecordList, null);
    	} else {
    		setReturnList(porxin010370uv02);
    		drugListService.updateReturnList(dispensingRecordList,
                    medOrderDispensingRecordList, medicationOrderList);
    	}

    }

    private void getDispensingRecordList(List<Prescription> prescriptionList,PORXIN010370UV02 porxin010370uv02)
    {
    	String visitType = porxin010370uv02.getVisitType();
        dispensingRecordList = new ArrayList<DispensingRecord>();
        medOrderDispensingRecordList = new ArrayList<MedOrderDispensingRecord>();
        
        updateDispensingRecordList = new ArrayList<DispensingRecord>();
        updateMedOrderDispensingRecordList = new ArrayList<MedOrderDispensingRecord>();
//        medicationOrderList = new ArrayList<MedicationOrder>();
        
        for(int i = 0; i < prescriptionList.size(); i++){
        	Prescription prescription = prescriptionList.get(i);
        	List<Drugs> drugs = prescription.getDrugs();
        	String recordNo = drugs.get(0).getRecordNo();
        	BigDecimal dispensingSn = null;
        	DispensingRecord dr = null;
        	for(Drugs drug : drugs){
        		MedicationOrder medicationOrder = null;
        		for(MedicationOrder order : medicationOrderList){
        			if(order.getOrderLid().equals(drug.getOrderId())
        					/*&& order.getDrugCode().equals(drug.getDrugCode())*/){
        				medicationOrder = order;
        				break;
        			}
        		}
        		if(medicationOrder == null) continue;
        		else {
/*        			if(Constants.ORDER_STATUS_DISTRIBUTE.equals(medicationOrder.getOrderStatus())
        					&& !isReturnMessage(porxin010370uv02)){
        				logger.debug("药品已发不做处理, orderLid: {}, drugCode: {}"
        						, medicationOrder.getOrderLid(), medicationOrder.getDrugCode());
        				// 该条医嘱不做更新
        				medicationOrderList.remove(medicationOrder);
        				continue;
        			}*/
        			
        			MedOrderDispensingRecord modr = null;
        			List<MedOrderDispensingRecord>  result = null;
        			// Author: yu_yzh
        			// Date: 2016/3/1 17:45
        	    	// [BUG] 0064315
        	    	// MODIFY BEGIN
        			if(Constants.VISIT_TYPE_CODE_INPATIENT.equals(visitType)){
        				result = drugListService.selectMedOrderDispensingRecordList(medicationOrder.getOrderSn(), drug.getBatchNo());
        			} else {
        				result = drugListService.selectMedOrderDispensingRecordList(medicationOrder.getOrderSn(), null);
        			}
        			// [BUG] 0064315
        	    	// MODIFY END
        			if(result == null || result.isEmpty()){
        				// 用药医嘱发药记录关联记录不存在，新建
        				modr = new MedOrderDispensingRecord();
        				if(dispensingSn == null){
            				// 门诊一个处方对应一条发药记录，住院一个药品对应一条发药记录
            				dispensingSn = commonService.getSequence("SEQ_DISPENSING");
            			}
            			// 批次号（住院执行档号用于区分MedOrderDispensingRecord记录）
            			modr.setBatchNo(drug.getBatchNo());
            			// 创建者
            			modr.setCreateby(DataMigrationUtils.getDataMigration() + porxin010370uv02.getMessage().getServiceId());
            			// 创建时间
            			modr.setCreateTime(systemDate);
            			// 更新次数
            			modr.setUpdateCount(Constants.INSERT_UPDATE_COUNT);
            			// 删除标记
            			modr.setDeleteFlag(Constants.NO_DELETE_FLAG);
            			
            			medOrderDispensingRecordList.add(modr);
        			} else {
        				modr = result.get(0);
        				
        				dispensingSn = modr.getDispensingSn();
        				
        				updateMedOrderDispensingRecordList.add(modr);
        			}
        			
        			// 发药量
        			modr.setDispensingQuantity(drug.getQuantity());
        			// 发药记录内部序列号
        			modr.setDispensingSn(dispensingSn);
        			// 领药单位
        			modr.setDispensingUnit(drug.getUnit());
        			// 药品医嘱内部序列号
        			modr.setOrderSn(medicationOrder.getOrderSn());
        			// 更新时间
        			modr.setUpdateTime(systemDate);

        			
        			// 关联的用药医嘱设置医嘱状态
        			if(isNewMessage(porxin010370uv02)){
        				// 已配药
        				medicationOrder.setOrderStatus(Constants.ORDER_STATUS_DISPENSE);
            			medicationOrder.setOrderStatusName(DictionaryUtils.getNameFromDictionary(
            					Constants.DOMAIN_ORDER_STATUS, Constants.ORDER_STATUS_DISPENSE));
        			} else {
        				// 已发药
        				medicationOrder.setOrderStatus(Constants.ORDER_STATUS_DISTRIBUTE);
            			medicationOrder.setOrderStatusName(DictionaryUtils.getNameFromDictionary(
            					Constants.DOMAIN_ORDER_STATUS, Constants.ORDER_STATUS_DISTRIBUTE));
        			}
        			
        		}       		
        	}
        	if(dispensingSn == null) continue;
        	// Author: yu_yzh
			// Date: 2016/3/1 17:45
	    	// [BUG] 0064315
	    	// MODIFY BEGIN
        	dr = drugListService.getDisRecord(dispensingSn, recordNo);  
        	// [BUG] 0064315
	    	// MODIFY END
        	if(dr == null){
            	dr = new DispensingRecord();
            	// 发药记录内部序列号
            	dr.setDispensingSn(dispensingSn);
            	// 创建者
            	dr.setCreateby(DataMigrationUtils.getDataMigration() + porxin010370uv02.getMessage().getServiceId());
            	// 创建时间
            	dr.setCreateTime(systemDate);
                // 更新次数
                dr.setUpdateCount(Constants.INSERT_UPDATE_COUNT);
            	// 删除标记
            	dr.setDeleteFlag(Constants.NO_DELETE_FLAG);
            	
                dispensingRecordList.add(dr);
        	} else {
        		updateDispensingRecordList.add(dr);
        	}

        	// 就诊内部序列号
            dr.setVisitSn(visitSn);

            if(isNewMessage(porxin010370uv02)){
            	// 配药人编码
            	dr.setDispenser(prescription.getConfirmPersonId());
            	// 配药人姓名
            	dr.setDispenserName(DictionaryUtils.getNameFromDictionary(
            			Constants.DOMAIN_STAFF, prescription.getConfirmPersonId(), prescription.getConfirmPersonName()));
        	} 
        	if(isOkMessage(porxin010370uv02)){
        		// 发药人编码
                dr.setSupplyPerson(prescription.getConfirmPersonId());
                // 发药人姓名
                dr.setSupplyPersonName(DictionaryUtils.getNameFromDictionary(
                		Constants.DOMAIN_STAFF, prescription.getConfirmPersonId(), prescription.getConfirmPersonName()));

        	}
       	
        	            // 发药确认时间
            if(prescription.getConfirmTime() != null){
            	dr.setSupplyTime(DateUtils.stringToDate(prescription.getConfirmTime()));
            }
            // 执行科室编码
            dr.setExecDept(prescription.getExecDeptId());
            // 执行科室名称
            dr.setExecDeptName(DictionaryUtils.getNameFromDictionary(
            		Constants.DOMAIN_DEPARTMENT, prescription.getExecDeptId(), prescription.getExecDeptName()));
            // 更新时间
            dr.setUpdateTime(systemDate);
            // 医疗机构代码
            dr.setOrgCode(porxin010370uv02.getOrgCode());
            // 医疗机构名称
            dr.setOrgName(porxin010370uv02.getOrgName());
            // 发药单号
            dr.setRecordNo(recordNo);
        }
        
        /*
        DispensingRecord dispensingRecord;
        // 循环遍历消息中的处方或者摆药单
        for(Prescription prescription : prescriptionList)
        {
            dispensingRecord = new DispensingRecord();
            BigDecimal dispensingSn = commonService.getSequence("SEQ_DISPENSING");
            // 发药记录内部序列号
            dispensingRecord.setDispensingSn(dispensingSn);
            // 就诊内部序列号
            dispensingRecord.setVisitSn(visitSn);
            // 处方号或者摆药单号
            dispensingRecord.setRecordNo(prescription.getPrescriptionNo());
            // 配药人编码
            dispensingRecord.setDispenser(prescription.getDispenserId());
            // 配药人姓名
            dispensingRecord.setDispenserName(prescription.getDispenserName());
            // 发药人编码
            dispensingRecord.setSupplyPerson(prescription.getConfirmPersonId());
            // 发药人姓名
            dispensingRecord.setSupplyPersonName(prescription.getConfirmPersonName());
            // 发药确认时间
            dispensingRecord.setSupplyTime(DateUtils.stringToDate(prescription.getConfirmTime()));
            // 执行科室编码
            dispensingRecord.setExecDept(prescription.getExecDeptId());
            // 执行科室名称
            dispensingRecord.setExecDeptName(prescription.getExecDeptName());
            // 创建时间
            dispensingRecord.setCreateTime(systemDate);
            // 更新次数
            dispensingRecord.setUpdateCount(Constants.INSERT_UPDATE_COUNT);
            // 更新时间
            dispensingRecord.setUpdateTime(systemDate);
            // 删除标志
            dispensingRecord.setDeleteFlag(Constants.NO_DELETE_FLAG);
            
            // $Author:tong_meng
            // $Date:2013/12/03 11:00
            // [BUG]0040270 ADD BEGIN
            // 医疗机构代码
            dispensingRecord.setOrgCode(porxin010370uv02.getOrgCode());
            // 医疗机构名称
            dispensingRecord.setOrgName(porxin010370uv02.getOrgName());
            // [BUG]0040270 ADD END

            // $Author :chang_xuewen
            // $Date : 2013/08/31 16:00$
            // [BUG]0036757 MODIFY BEGIN
            // 增加人
            dispensingRecord.setCreateby(DataMigrationUtils.getDataMigration() + porxin010370uv02.getMessage().getServiceId());
            // [BUG]0036757 MODIFY END
            List<Drugs> drugsList = prescription.getDrugs();
            MedOrderDispensingRecord medOrderDispensingRecord;
            for(Drugs drugs : drugsList)
            {
                // 获取该发药记录关联的用药医嘱
                List<MedicationOrder> medicationOrder = drugListService.selectMedicationOrder(drugs.getDrugCode(),
                        drugs.getOrderId(), patientDomain, patientLid,porxin010370uv02.getOrgCode());
                MedicationOrder mo = new MedicationOrder();
                mo = medicationOrder.get(0);
                if (Double.parseDouble(drugs.getQuantity()) < 0)
                {
                    // 设置医嘱执行状态 - 已取消
                    mo.setOrderStatus(Constants.ORDER_STATUS_CANCELED);
                    mo.setOrderStatusName(DictionaryCache.getDictionary(
                            Constants.DOMAIN_ORDER_STATUS).get(
                            Constants.ORDER_STATUS_CANCELED));
                }
                else
                {
                    // 设置医嘱执行状态 - 已发药
                    mo.setOrderStatus(Constants.ORDER_STATUS_DISTRIBUTE);
                    mo.setOrderStatusName(DictionaryCache.getDictionary(
                            Constants.DOMAIN_ORDER_STATUS).get(
                            Constants.ORDER_STATUS_DISTRIBUTE));
                }
                mo.setUpdateTime(systemDate);
                // $Author :chang_xuewen
                // $Date : 2013/08/31 16:00$
                // [BUG]0036757 MODIFY BEGIN
                // 设置更新人
                mo.setUpdateby(porxin010370uv02.getMessage().getServiceId());
                // [BUG]0036757 MODIFY END
                //medicationOrderList.add(mo);
                boolean flag = true;
                // List中放入第二条及以后的关联用药医嘱
                if (medicationOrderList != null
                    && !medicationOrderList.isEmpty())
                {
                    for (MedicationOrder moo : medicationOrderList)
                    {
                        // 检验该关联医嘱是否已经放入到List中
                        if (moo.getOrderLid().equals(
                                drugs.getOrderId()) && moo.getDrugCode().equals(drugs.getDrugCode()))
                        {
                            flag = false;
                        }
                    }
                    
                    if (flag)
                    {
                        medicationOrderList.add(mo);
                    }
                }
                // List中放入第一条关联用药医嘱
                else
                {
                    medicationOrderList.add(mo);
                }
                
                medOrderDispensingRecord = new MedOrderDispensingRecord();
                // 关联的医嘱内部序列号
                medOrderDispensingRecord.setOrderSn(mo.getOrderSn());
                // 发药记录内部序列号
                medOrderDispensingRecord.setDispensingSn(dispensingSn);
                // 批次号
                medOrderDispensingRecord.setBatchNo(drugs.getBatchNo());
                // 发药量
                medOrderDispensingRecord.setDispensingQuantity(drugs.getQuantity());
                // 领药单位
                medOrderDispensingRecord.setDispensingUnit(drugs.getUnit());
                // 创建时间
                medOrderDispensingRecord.setCreateTime(systemDate);
                // 更新时间
                medOrderDispensingRecord.setUpdateTime(systemDate);
                // 更新次数
                medOrderDispensingRecord.setUpdateCount(Constants.INSERT_UPDATE_COUNT);
                // 删除标志
                medOrderDispensingRecord.setDeleteFlag(Constants.NO_DELETE_FLAG);
                // $Author :chang_xuewen
                // $Date : 2013/08/31 16:00$
                // [BUG]0036757 MODIFY BEGIN
                // 增加人
                medOrderDispensingRecord.setCreateby(DataMigrationUtils.getDataMigration() + porxin010370uv02.getMessage().getServiceId());
                // [BUG]0036757 MODIFY END
                medOrderDispensingRecordList.add(medOrderDispensingRecord);
            }
            dispensingRecordList.add(dispensingRecord);
        }*/
    }
    
    /**
     * 设置退药内容
     * */
    private void setReturnList(PORXIN010370UV02 porxin010370uv02){
    	dispensingRecordList = new ArrayList<DispensingRecord>();
    	medOrderDispensingRecordList = new ArrayList<MedOrderDispensingRecord>();
    	// Author: yu_yzh
    	// Date: 2016/3/1 17:45
    	// [BUG] 0064315
    	// MODIFY BEGIN
    	String visitType = porxin010370uv02.getVisitType();
    	for(Prescription prescription : porxin010370uv02.getPrescription()){
        	List<Drugs> drugs = prescription.getDrugs();
        	for(Drugs drug : drugs){
        		MedicationOrder mo = null;
        		for(MedicationOrder order : medicationOrderList){
        			if(order.getOrderLid().equals(drug.getOrderId())){
        				mo = order;
        				break;
        			}
        		}
        		if(mo == null) continue;
        		
    			MedOrderDispensingRecord modr = null;
    			List<MedOrderDispensingRecord>  result = null;
    			if(Constants.VISIT_TYPE_CODE_INPATIENT.equals(visitType)){
    				result = drugListService.selectMedOrderDispensingRecordList(mo.getOrderSn(), drug.getBatchNo());
    			} else {
    				result = drugListService.selectMedOrderDispensingRecordList(mo.getOrderSn(), null);
    			}
    			if(result != null && !result.isEmpty()){
        			boolean dispensingRecordExist = false;
        			for(Object obj : result){
        				modr = (MedOrderDispensingRecord) obj;
        				modr.setDeleteFlag(Constants.DELETE_FLAG);
        				modr.setDeleteTime(systemDate);
        				modr.setDeleteby(porxin010370uv02.getMessage().getServiceId());
        				modr.setUpdateby(porxin010370uv02.getMessage().getServiceId());
        				modr.setUpdateTime(systemDate);
        				medOrderDispensingRecordList.add(modr);
        				
        				for(DispensingRecord dr : dispensingRecordList){
        					if(dr.getDispensingSn().equals(modr.getDispensingSn())){
        						dispensingRecordExist = true;
        						break;
        					}
        				}
        				
        				if(!dispensingRecordExist){

            				List<DispensingRecord> result2 =  drugListService.selectDispensingRecord(modr.getDispensingSn());
            				if(result2 != null && !result2.isEmpty()){
            					for(Object obj2 : result2){
            						DispensingRecord dr = (DispensingRecord) obj2;
            						dr.setDeleteFlag(Constants.DELETE_FLAG);
            	    				dr.setDeleteTime(systemDate);
            	    				dr.setDeleteby(porxin010370uv02.getMessage().getServiceId());
            	    				dr.setUpdateby(porxin010370uv02.getMessage().getServiceId());
            	    				dr.setUpdateTime(systemDate);
            						dispensingRecordList.add(dr);
            					}
            				}
        				}

        			}
        		}
        		
        		mo.setOrderStatus(Constants.ORDER_STATUS_RETURN_DRUG);
        		mo.setOrderStatusName(DictionaryUtils.getNameFromDictionary(
        				Constants.DOMAIN_ORDER_STATUS, Constants.ORDER_STATUS_RETURN_DRUG));
        		
        	}
    	}
    	// [BUG] 0064315
    	// MODIFY END
    }
    
    /**
     * 检查发药记录关联的用药医嘱是否存在
     */
    public int checkMedicalOrder(PORXIN010370UV02 porxin010370uv02 , boolean flag)
    {
    	medicationOrderList = new ArrayList<MedicationOrder>();
    	
    	int doNotExistCount = 0;
    	
        // 域ID
        patientDomain = porxin010370uv02.getPatientDomain();
        // 患者LID
        patientLid = porxin010370uv02.getPatientLid();
/*        //就诊流水号
        visitOrdNo = porxin010370uv02.getVisitOrdNo();
        //就诊类型
        visitType = porxin010370uv02.getVisitType();
        //就诊次数
        visitTimes = porxin010370uv02.getVisitTimes();*/

        // 消息中的处方或者摆药单集合
        List<Prescription> prescriptionList = porxin010370uv02.getPrescription();
        // 遍历消息中的处方或者摆药单集合
        for(Prescription prescription : prescriptionList)
        {
            List<Drugs> drugList = prescription.getDrugs();
            for(Drugs drug : drugList)
            {
                // 本地医嘱号
                String orderLid = drug.getOrderId();
/*        		// 就诊信息
        		MedicalVisit medicalVisit = commonService.mediVisit(
        				patientDomain, patientLid, visitTimes,porxin010370uv02.getOrgCode(),visitOrdNo,visitType);*/
                // $Author:tong_meng
                // $Date:2013/12/03 11:00
                // [BUG]0040270 MODIFY BEGIN
                // 获取用药医嘱记录
                List<MedicationOrder> medicationOrder = drugListService.selectMedicationOrder(drug.getDrugCode(),
                        orderLid, patientDomain, patientLid,porxin010370uv02.getOrgCode());
                // [BUG]0040270 MODIFY END
                
                // 如果用药医嘱不存在
                if (medicationOrder == null || medicationOrder.size() == 0)
                {
                    logger.error("MessageId:{};关联的用药医嘱不存在 ，本地医嘱号：" + orderLid + "，药品代码:" + drug.getDrugCode()
                        + "，患者域ID：" + patientDomain + "，患者本地ID：" + patientLid
                        + "，医疗机构代码" + porxin010370uv02.getOrgCode(),
                            porxin010370uv02.getMessage().getId());
                    if (flag)
                    {
                        loggerBS307.error("Message:{},checkDependency:{}",
                                porxin010370uv02.toString(), "用药医嘱不存在 ，本地医嘱号："
                                    + orderLid + "，药品代码:" + drug.getDrugCode()+ "，患者域ID：" + patientDomain
                                    + "，患者本地ID：" + patientLid + "，医疗机构代码"
                                    + porxin010370uv02.getOrgCode());
                    }
                    doNotExistCount++;
                }
                else
                {
                	medicationOrderList.add(medicationOrder.get(0));
//                    visitSn = medicationOrder.get(0).getVisitSn();
                }
            }
        }
        if(!medicationOrderList.isEmpty()) {
        	visitSn = medicationOrderList.get(0).getVisitSn();
        }
        return doNotExistCount;
    }
    
    private boolean isNewMessage(PORXIN010370UV02 porxin010370uv02){
    	return Constants.NEW_MESSAGE_FLAG.equals(porxin010370uv02.getNewUpFlag())
    			|| Constants.V2_NEW_MESSAGE_FLAG.equals(porxin010370uv02.getNewUpFlag());
    }
    
    private boolean isOkMessage(PORXIN010370UV02 porxin010370uv02){
    	return Constants.V2_CONFIRM_OR_CHARGE_MESSAGE_FLAG.equals(porxin010370uv02.getNewUpFlag());
    }
    
    private boolean isReturnMessage(PORXIN010370UV02 porxin010370uv02){
    	return Constants.V2_RETURN_MESSAGE_FLAG.equals(porxin010370uv02.getNewUpFlag());
    }
    
    private void cleanMedicationOrder(){
    	List<MedicationOrder> uniqueList = new ArrayList<MedicationOrder>();
    	for(MedicationOrder order : medicationOrderList){
    		boolean exist = false;
    		for(MedicationOrder o : uniqueList){
    			if(order.getOrderLid().equals(o.getOrderLid())){
    				exist = true;
    				break;
    			}
    		}
    		if(!exist){
    			uniqueList.add(order);
    		}
    	}
    	medicationOrderList = uniqueList;
    }
}
