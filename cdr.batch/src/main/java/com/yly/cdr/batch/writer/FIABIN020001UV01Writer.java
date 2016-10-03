/**
 * 费用数据接入
 * @author jia_yanqing
 * @date 2012/05/21
 */
package com.yly.cdr.batch.writer;

import java.lang.reflect.Method;
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

import com.yly.cdr.cache.DictionaryCache;
import com.yly.cdr.core.Constants;
import com.yly.cdr.entity.BillingItem;
import com.yly.cdr.entity.BillingReceipt;
import com.yly.cdr.entity.MedicalVisit;
import com.yly.cdr.entity.Prescription;
import com.yly.cdr.hl7.dto.fiabin020001uv01.FIABIN020001UV01;
import com.yly.cdr.service.BillingService;
import com.yly.cdr.service.CommonService;
import com.yly.cdr.util.DateUtils;
import com.yly.cdr.util.StringUtils;

@Component(value = "fiabin020001uv01Writer")
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class FIABIN020001UV01Writer implements BusinessWriter<FIABIN020001UV01>
{

    private static final Logger logger = LoggerFactory.getLogger(FIABIN020001UV01Writer.class);

    private static final Logger loggerBS353 = LoggerFactory.getLogger("BS353");

    // 就诊记录
    private MedicalVisit medicalVisit;

    // 患者本地ID
    private String patientLid;

    // 域代码
    private String patientDomain;

    // 获取系统时间
    private Date systemTime = DateUtils.getSystemTime();

    // 存放多个帐单对应的明细
    private List<BillingItem> billingItem = new ArrayList<BillingItem>();

    @Autowired
    private CommonService commonService;

    @Autowired
    private BillingService billingService;

    // 需更新的医嘱记录
    private List<Object> orders = new ArrayList<Object>();

    // 需更新的处方记录
    private List<Prescription> prescriptions = new ArrayList<Prescription>();

    // $Author :jin_peng
    // $Date : 2012/12/12 17:30$
    // [BUG]0012353 ADD BEGIN
    // 判断是否已存在相应费用对应的医嘱
    private List<String> orderInferList = new ArrayList<String>();

    // 判断是否已存在相应费用对应的处方
    // private List<String> presInferList = new ArrayList<String>();

    // [BUG]0012353 ADD END

    /**
     * 非空验证
     */
    @Override
    public boolean validate(FIABIN020001UV01 fiabin020001uv01)
    {
        // $Author :tong_meng
        // $Date : 2012/9/21 15:07$
        // [BUG]9776 ADD BEGIN
        if (Constants.PATIENT_DOMAIN_OUTPATIENT.equals(fiabin020001uv01.getPatientDomain()))
        {
            String transactionType = fiabin020001uv01.getTransactionType();
            if (StringUtils.isEmpty(transactionType))
            {
                loggerBS353.error("Message:{},validate{}",
                        fiabin020001uv01.toString(), "门诊时，消息的交易类型不能为空！");
                return false;
            }
            for (com.yly.cdr.hl7.dto.BillingReceipt billingReceipt : fiabin020001uv01.getBillingReceipt())
            {
                for (com.yly.cdr.hl7.dto.BillingItem billingItem : billingReceipt.getBillingItem())
                {
                    if (StringUtils.isEmpty(billingItem.getVisitTimes()))
                    {
                        loggerBS353.error("Message:{},validate{}",
                                fiabin020001uv01.toString(), "门诊时，就诊次数不能为空！");
                        return false;
                    }
                }
            }
            // $Author:wei_peng
            // $Date:2012/11/20 10:58
            // [BUG]0011421 DELETE BEGIN
            /*
             * List<com.yly.cdr.hl7.dto.BillingReceipt> billingReceiptList =
             * fiabin020001uv01.getBillingReceipt();
             * List<com.yly.cdr.hl7.dto.BillingItem> billingItemList = null;
             * for (com.yly.cdr.hl7.dto.BillingReceipt billingReceipt :
             * billingReceiptList) { billingItemList =
             * billingReceipt.getBillingItem(); for
             * (com.yly.cdr.hl7.dto.BillingItem billingItem :
             * billingItemList) { if (!StringUtils.isNotNullAll(
             * billingItem.getPrescription(),
             * billingItem.getPrescriptionType())) { logger.error(
             * "非空字段验证未通过: {}", "处方号：" + billingItem.getPrescription() +
             * "；处方类别编码：" + billingItem.getPrescriptionType()); return false; }
             * } }
             */
            // [BUG]0011421 DELETE END
        }
        // [BUG]9776 ADD END
        else if (Constants.PATIENT_DOMAIN_INPATIENT.equals(fiabin020001uv01.getPatientDomain()))
        {
            if (StringUtils.isEmpty(fiabin020001uv01.getVisitTimes()))
            {
                loggerBS353.error("Message:{},validate{}",
                        fiabin020001uv01.toString(), "住院时，就诊次数不能为空！");
                return false;
            }
        }
        return true;
    }

    /**
     * 判断被关联消息是否存在
     */
    @Override
    public boolean checkDependency(FIABIN020001UV01 fiabin020001uv01,
            boolean flag)
    {
        // 获取患者域代码
        patientDomain = fiabin020001uv01.getPatientDomain();

        // $Author :tongmeng
        // $Date : 2012/5/29 14:30$
        // [BUG]0006657 MODIFY BEGIN
        // 获取患者本地ID
        patientLid = fiabin020001uv01.getPatientLid();
        // [BUG]0006657 MODIFY END

        // 住院时判断就诊是否存在
        if (Constants.PATIENT_DOMAIN_INPATIENT.equals(patientDomain))
        {
            // 获取就诊记录并判断
            medicalVisit = getMedicalVisit(fiabin020001uv01.getVisitTimes());
            if (medicalVisit == null)
            {
                logger.error("MessageId:{};住院时，费用消息关联的就诊不存在，域ID：" + patientDomain
                    + "，患者本地ID：" + patientLid + "，就诊次数："
                    + fiabin020001uv01.getVisitTimes(),fiabin020001uv01.getMessage().getId());
                if (flag)
                {
                    loggerBS353.error("Message:{},checkDependency:{}",
                            fiabin020001uv01.toString(),
                            "住院时，费用消息关联的就诊不存在，域ID：" + patientDomain
                                + "，患者本地ID：" + patientLid + "，就诊次数："
                                + fiabin020001uv01.getVisitTimes());
                }
                return false;
            }
        }

        for (com.yly.cdr.hl7.dto.BillingReceipt billingReceipt : fiabin020001uv01.getBillingReceipt())
        {
            for (com.yly.cdr.hl7.dto.BillingItem billingItem : billingReceipt.getBillingItem())
            {
                // 判断是否已处理过相应医嘱，如果处理过则不再对其操作
                if (isExistsOrder(billingItem.getOrderLid(),
                        billingItem.getOrderType()))
                {
                    continue;
                }
                // 门诊时判断就诊是否存在
                if (Constants.PATIENT_DOMAIN_OUTPATIENT.equals(patientDomain))
                {
                    // 获取就诊记录并判断
                    if (getMedicalVisit(billingItem.getVisitTimes()) == null)
                    {
                        logger.error("MessageId:{};门诊时，费用消息关联的就诊不存在，域ID：" + patientDomain
                            + "，患者本地ID：" + patientLid + "，就诊次数："
                            + billingItem.getVisitTimes(),fiabin020001uv01.getMessage().getId());
                        if (flag)
                        {
                            loggerBS353.error("Message:{},checkDependency:{}",
                                    fiabin020001uv01.toString(),
                                    "门诊时，费用消息关联的就诊不存在，域ID：" + patientDomain
                                    + "，患者本地ID：" + patientLid + "，就诊次数："
                                    + billingItem.getVisitTimes());
                        }
                        return false;
                    }
                    // 处理所有医嘱（其他类医嘱不处理）
                    if (!Constants.GENERAL_ORDER_TABLE_NAME.equals(Constants.ORDER_TYPE_MAP.get(billingItem.getOrderType())))
                    {
                        // 医嘱收费状态
                        String chargeStatus = null;
                        String chargeMessage = null;
                        if (isChargeMessage(fiabin020001uv01))
                        {
                            chargeStatus = Constants.CHARGE_STATUS_CHARGED;
                            chargeMessage = "正常交费";
                        }
                        else if (isReturnMessage(fiabin020001uv01)
                            && !Constants.TREATMENT_ORDER_TABLE_NAME.equals(Constants.ORDER_TYPE_MAP.get(billingItem.getOrderType()))
                            && !Constants.MEDICATION_ORDER_TABLE_NAME.equals(Constants.ORDER_TYPE_MAP.get(billingItem.getOrderType())))
                        {
                            /*
                             * // 医嘱删除标志 String deleteFlag = null; //
                             * 退费时，当退费医嘱类型为诊疗和用药时，会传递处方号，需要对相关的处方号进行关联校验 if
                             * (Constants
                             * .TREATMENT_ORDER_TABLE_NAME.equals(Constants
                             * .ORDER_TYPE_MAP.get(billingItem.getOrderType()))
                             * || Constants.MEDICATION_ORDER_TABLE_NAME.equals(
                             * Constants
                             * .ORDER_TYPE_MAP.get(billingItem.getOrderType())))
                             * { // 判断是否已处理过相应处方，如果处理过则不再对其操作 if
                             * (billingItem.getPrescription() == null ||
                             * !isExistsPres(billingItem.getPrescription())) {
                             * Prescription prescription =
                             * getDeletePrescription(
                             * billingItem.getPrescription()); if (prescription
                             * == null) {
                             * logger.error("退费时，且医嘱类型为诊疗或用药，找不到关联的处方！处方号为：" +
                             * billingItem.getPrescription()); return false; }
                             * prescriptions.add(prescription); } // 诊疗和用药医嘱需要删除
                             * deleteFlag = Constants.DELETE_FLAG; } else { //
                             * 除去诊疗和用药的其他医嘱不进行逻辑删除，仅修改其医嘱交费状态 deleteFlag =
                             * Constants.NO_DELETE_FLAG; }
                             */
                            chargeStatus = Constants.CHARGE_STATUS_RETURNCHARGE;
                            chargeMessage = "退费";
                        }
                        else if (isRepayMessage(fiabin020001uv01)
                            && !Constants.MEDICATION_ORDER_TABLE_NAME.equals(Constants.ORDER_TYPE_MAP.get(billingItem.getOrderType()))
                            && !Constants.TREATMENT_ORDER_TABLE_NAME.equals(Constants.ORDER_TYPE_MAP.get(billingItem.getOrderType())))
                        {
                            chargeStatus = Constants.CHARGE_STATUS_REPAYCHARGE;
                            chargeMessage = "退费重收";
                        }

                        // 当医嘱消费状态未赋值时，则不需查找
                        if (chargeStatus != null)
                        {
                            Object order = getOrder(billingItem.getOrderLid(),
                                    billingItem.getOrderType(), chargeStatus,
                                    Constants.NO_DELETE_FLAG);
                            if (order == null)
                            {
                                logger.error("MessageId:{};"+chargeMessage + "时，找不到关联的医嘱，医嘱号："
                                    + billingItem.getOrderLid(),fiabin020001uv01.getMessage().getId());
                                if (flag)
                                {
                                    loggerBS353.error("Message:{},checkDependency:{}",
                                            fiabin020001uv01.toString(),
                                            chargeMessage + "时，找不到关联的医嘱，医嘱号："
                                                    + billingItem.getOrderLid());
                                }
                                return false;
                            }
                            orders.add(order);
                        }
                    }
                }
            }
        }
        return true;
    }

    // $Author :jin_peng
    // $Date : 2012/12/12 17:30$
    // [BUG]0012353 ADD BEGIN
    /**
     * 判断费用对应的医嘱是否已处理过
     * @param orderLid 本地医嘱号
     * @param orderType 医嘱类型
     * @return 是否已处理标识
     */
    private boolean isExistsOrder(String orderLid, String orderType)
    {
        boolean isExistsOrder = true;

        // 判断是否已处理过相应医嘱
        if (!orderInferList.contains(orderType + orderLid.toLowerCase()))
        {
            orderInferList.add(orderType + orderLid.toLowerCase());

            isExistsOrder = false;
        }

        return isExistsOrder;
    }

    /**
     * 判断费用对应的处方是否已处理过
     * @param presNo 处方号
     * @return 是否已处理标识
     */
    /*
     * private boolean isExistsPres(String presNo) { boolean isExistsPres =
     * true;
     * 
     * // 判断是否已处理过相应处方 if (!presInferList.contains(presNo)) {
     * presInferList.add(presNo);
     * 
     * isExistsPres = false; }
     * 
     * return isExistsPres; }
     */

    // [BUG]0012353 ADD END

    @Override
    @Transactional
    public void saveOrUpdate(FIABIN020001UV01 fiabin020001uv01)
    {
        // 从消息中获取帐单列表
        List<BillingReceipt> billingReceipt = getBillingReceipt(fiabin020001uv01);

        if (Constants.NEW_MESSAGE_FLAG.equals(fiabin020001uv01.getNewUpFlag()))
        {
            // 执行帐单及其明细信息的添加
            billingService.saveBilling(billingReceipt, billingItem,
                    prescriptions, orders);
        }

    }

    /**
     * 获取消息中传来的多个帐单
     * @param fiabin020001uv01
     * @return 帐单列表
     */
    public List<BillingReceipt> getBillingReceipt(
            FIABIN020001UV01 fiabin020001uv01)
    {
        // 存放多个帐单
        List<BillingReceipt> billingReceipt = new ArrayList<BillingReceipt>();

        // 如果是新增
        if (Constants.NEW_MESSAGE_FLAG.equals(fiabin020001uv01.getNewUpFlag()))
        {
            // 获取帐单信息
            List<com.yly.cdr.hl7.dto.BillingReceipt> billingReceiptDtoList = fiabin020001uv01.getBillingReceipt();

            if (billingReceiptDtoList != null)
            {
                // 遍历消息中的所有帐单信息
                for (com.yly.cdr.hl7.dto.BillingReceipt billingReceiptDto : billingReceiptDtoList)
                {
                    // $Author :jia_yanqing
                    // $Date : 2012/6/18 11:20$
                    // [BUG]0006804 ADD BEGIN
                    // 医保内金额
                    String insurancePaid = fiabin020001uv01.getInsurancePaid();
                    // 医保外金额
                    String ownExpense = fiabin020001uv01.getOwnExpense();
                    // [BUG]0006804 ADD END
                    // 帐单对象
                    BillingReceipt billingReceiptEntity = new BillingReceipt();
                    // 域代码
                    billingReceiptEntity.setPatientDomain(patientDomain);
                    // 患者本地ID
                    billingReceiptEntity.setPatientLid(patientLid);
                    // 收款日期
                    billingReceiptEntity.setReceiptTime(DateUtils.stringToDate(fiabin020001uv01.getReceiptTime()));

                    // $Author :jia_yanqing
                    // $Date : 2012/6/18 11:20$
                    // [BUG]0006818 MODIFY BEGIN
                    // 收款人编码
                    billingReceiptEntity.setReceiver(fiabin020001uv01.getReceiver());
                    // 收款人姓名
                    billingReceiptEntity.setReceiverName(fiabin020001uv01.getReceiverName());
                    // [BUG]0006818 MODIFY END

                    // 帐单内部序列号
                    BigDecimal receiptSn = commonService.getSequence("SEQ_BILLING_RECEIPT");
                    // 帐单内部序列号
                    billingReceiptEntity.setReceiptSn(receiptSn);
                    // 收据号
                    billingReceiptEntity.setReceiptNo(billingReceiptDto.getReceiptNo());
                    // 红冲账单流水号
                    billingReceiptEntity.setWriteBackNo(billingReceiptDto.getWriteBackNo());
                    // 帐单日期
                    billingReceiptEntity.setReceiptDate(DateUtils.stringToDate(billingReceiptDto.getReceiptDate()));
                    // 帐单金额
                    billingReceiptEntity.setReceiptTotal(StringUtils.strToBigDecimal(
                            billingReceiptDto.getReceiptTotal(), "帐单金额"));

                    // $Author :jia_yanqing
                    // $Date : 2012/7/23 15:20$
                    // [BUG]0008131 DELETE BEGIN
                    // 应收金额
                    // billingReceiptEntity.setAmountReceivable(StringUtils.strToBigDecimal(billingReceiptDto.getAmountReceivable()));
                    // 费别代码
                    billingReceiptEntity.setChargeClass(billingReceiptDto.getBillingItem().get(
                            0).getChargeClass());
                    // 费别名称
                    billingReceiptEntity.setChargeClassName(billingReceiptDto.getBillingItem().get(
                            0).getChargeClassName());
                    // [BUG]0008131 DELETE END
                    // 更新回数
                    billingReceiptEntity.setUpdateCount(Constants.INSERT_UPDATE_COUNT);
                    // 创建时间
                    billingReceiptEntity.setCreateTime(systemTime);
                    // 删除标志
                    billingReceiptEntity.setDeleteFlag(Constants.NO_DELETE_FLAG);
                    // 更新时间
                    billingReceiptEntity.setUpdateTime(systemTime);
                    // 获取该帐单下对应的多个帐单明细

                    // $Author :jia_yanqing
                    // $Date : 2012/6/18 11:20$
                    // [BUG]0006804 ADD BEGIN
                    this.getBillingItem(billingReceiptDto, receiptSn,
                            insurancePaid, ownExpense);
                    // [BUG]0006804 ADD END
                    // 就诊内部序列号(账单记录不关联就诊，可能多个就诊对应一个账单)
                    // billingReceiptEntity.setVisitSn(medicalVisit.getVisitSn());
                    // 患者内部序列号
                    billingReceiptEntity.setPatientSn(medicalVisit.getPatientSn());
                    // 将每个帐单对象放入一个帐单List中
                    billingReceipt.add(billingReceiptEntity);
                }
            }
        }
        return billingReceipt;
    }

    /**
     * 获取消息中传来的多个帐单对应的多个明细列表
     * @param billingReceiptDto
     * @param receiptSn
     * @return 多个帐单明细List
     */
    // $Author :jia_yanqing
    // $Date : 2012/6/18 11:20$
    // [BUG]0006804 ADD BEGIN
    public List<BillingItem> getBillingItem(
            com.yly.cdr.hl7.dto.BillingReceipt billingReceiptDto,
            BigDecimal receiptSn, String insurancePaid, String ownExpense)
    // [BUG]0006804 ADD END
    {
        // 获取从消息中传来的明细信息列表
        List<com.yly.cdr.hl7.dto.BillingItem> billingItemDtoList = billingReceiptDto.getBillingItem();
        if (billingItemDtoList != null)
        {
            // 遍历从消息中传来的明细信息列表将其放入billingItemEntityList中
            for (com.yly.cdr.hl7.dto.BillingItem billingItemDto : billingItemDtoList)
            {
                if (Constants.PATIENT_DOMAIN_OUTPATIENT.equals(patientDomain))
                {
                    medicalVisit = getMedicalVisit(billingItemDto.getVisitTimes());
                }
                // 明细对象
                BillingItem billingItemEntity = new BillingItem();
                // 就诊内部序列号
                billingItemEntity.setVisitSn(medicalVisit.getVisitSn());
                // 帐单内部序列号
                billingItemEntity.setReceiptSn(receiptSn);
                // 患者内部序列号
                billingItemEntity.setPatientSn(medicalVisit.getPatientSn());
                // 域代码
                billingItemEntity.setPatientDomain(patientDomain);
                // 患者本地ID
                billingItemEntity.setPatientLid(patientLid);

                // $Author :jia_yanqing
                // $Date : 2012/7/23 15:20$
                // [BUG]0008131 MODIFY BEGIN
                // 项目分组
                billingItemEntity.setChargeGroup(billingItemDto.getChargeGroup());
                // 项目分组名称
                billingItemEntity.setChargeGroupName(billingItemDto.getChargeGroupName());
                // 婴儿标志
                if (Constants.TRUE_FLAG.equals(billingItemDto.getInfantFlag()))
                {
                    billingItemEntity.setInfantFlag(Constants.INFANT_FLAG);
                }
                else if (Constants.FALSE_FLAG.equals(billingItemDto.getInfantFlag()))
                {
                    billingItemEntity.setInfantFlag(Constants.NO_INFANT_FLAG);
                }

                // [BUG]0008131 MODIFY END

                // $Author :jia_yanqing
                // $Date : 2012/6/18 11:20$
                // [BUG]0006804 ADD BEGIN
                // 医保内金额
                billingItemEntity.setInsurancePaid(StringUtils.strToBigDecimal(
                        insurancePaid, "医保内金额"));
                // 医保外金额
                billingItemEntity.setOwnExpense(StringUtils.strToBigDecimal(
                        ownExpense, "医保外金额"));
                // [BUG]0006804 ADD END

                // 帐单明细内部序列号
                BigDecimal billingItemSn = commonService.getSequence("SEQ_BILLING_ITEM");
                // 帐单明细内部序列号
                billingItemEntity.setBillingItemSn(billingItemSn);
                // 细目号
                billingItemEntity.setItemNo(billingItemDto.getItemNo());
                // 结算次数
                billingItemEntity.setLedgerCount(billingItemDto.getLedgerCount());
                // 费用状态
                billingItemEntity.setChargeStatus(billingItemDto.getChargeStatus());

                // $Author :jia_yanqing
                // $Date : 2012/6/18 11:20$
                // [BUG]0008408 ADD BEGIN
                // 费用状态名称
                billingItemEntity.setChargeStatusName(billingItemDto.getChargeStatusName());
                // 费用确认人编码
                billingItemEntity.setConfirmPerson(billingItemDto.getConfirmPerson());
                // 费用确认人名称
                billingItemEntity.setConfirmPersonName(billingItemDto.getConfirmPersonName());
                // [BUG]0008408 ADD END

                // Author :jia_yanqing
                // Date : 2012/11/28 16:20
                // [BUG]0011872 ADD BEGIN
                // 费用号
                billingItemEntity.setChargeNo(billingItemDto.getChargeNo());
                // [BUG]0011872 ADD END

                // 项目发生时间
                billingItemEntity.setOccurrenceTime(DateUtils.stringToDate(billingItemDto.getOccurrenceTime()));
                // 数量
                billingItemEntity.setAmount(StringUtils.strToBigDecimal(
                        billingItemDto.getAmount(), "数量"));
                // 单价
                billingItemEntity.setChargePrice(StringUtils.strToBigDecimal(
                        billingItemDto.getChargePrice(), "单价"));
                // 费用编码
                billingItemEntity.setChargeCode(billingItemDto.getCharge());
                // 费用名称
                billingItemEntity.setItemName(billingItemDto.getChargeName());
                // 更新回数
                billingItemEntity.setUpdateCount(Constants.INSERT_UPDATE_COUNT);
                // 创建时间
                billingItemEntity.setCreateTime(systemTime);
                // 删除标志
                billingItemEntity.setDeleteFlag(Constants.NO_DELETE_FLAG);
                // 更新时间
                billingItemEntity.setUpdateTime(systemTime);
                // 将明细对象放入对应的List中
                billingItem.add(billingItemEntity);
            }
        }
        return billingItem;
    }

    /**
     * 获取费用消息关联的就诊记录
     * @param visitTimes 就诊次数
     * @return 就诊记录
     */
    public MedicalVisit getMedicalVisit(String visitTimes)
    {
        // 根据域ID,患者本地ID，就诊次数获取就诊记录
        MedicalVisit medicalVisit = commonService.mediVisit(patientDomain,
                patientLid, Integer.parseInt(visitTimes));

        return medicalVisit;
    }

    /**
     * 判断是否为正常收费
     * @param fiabin020001uv01
     * @return
     */
    private boolean isChargeMessage(FIABIN020001UV01 fiabin020001uv01)
    {
        return Constants.TRANSACTION_TYPE_CHARGE.equals(fiabin020001uv01.getTransactionType());
    }

    /**
     * 判断是否为退费消息
     * @param fiabin020001uv01
     * @return
     */
    private boolean isReturnMessage(FIABIN020001UV01 fiabin020001uv01)
    {
        return Constants.TRANSACTION_TYPE_RETURN.equals(fiabin020001uv01.getTransactionType());
    }

    /**
     * 判断是否为退费重收消息
     * @param fiabin020001uv01
     * @return
     */
    private boolean isRepayMessage(FIABIN020001UV01 fiabin020001uv01)
    {
        return Constants.TRANSACTION_TYPE_REPAY.equals(fiabin020001uv01.getTransactionType());
    }

    /**
     * 通过医嘱类别查找先关的医嘱表通过本地医嘱号找到对应的医嘱条目,修改医嘱交费状态为退费，并更近删除标志位逻辑删除
     * @param orderLid
     * @param orderType
     * @return
     */
    private Object getOrder(String orderLid, String orderType,
            String chargeStatusCode, String deleteFlag)
    {
        Object order = null;
        List<Object> results = null;
        // 医嘱查询条件
        Map<String, Object> condition = new HashMap<String, Object>();
        condition.put("orderLid", orderLid);
        condition.put("deleteFlag", Constants.NO_DELETE_FLAG);
        // 获取医嘱的类型
        Class<?> orderClass = commonService.getOrderClass(orderType);
        // 查询对应医嘱类型的医嘱信息
        results = commonService.selectByCondition(orderClass, condition);
        if (results != null && results.size() > 0)
        {
            order = results.get(0);
            try
            {
                Method setChargeStatusCode = orderClass.getDeclaredMethod(
                        "setChargeStatusCode", String.class);
                Method setChargeStatusName = orderClass.getDeclaredMethod(
                        "setChargeStatusName", String.class);
                Method setDeleteFlag = orderClass.getDeclaredMethod(
                        "setDeleteFlag", String.class);
                Method setUpdateTime = orderClass.getDeclaredMethod(
                        "setUpdateTime", Date.class);
                Method setDeleteTime = orderClass.getDeclaredMethod(
                        "setDeleteTime", Date.class);
                // 医嘱交费类别编码
                setChargeStatusCode.invoke(order, chargeStatusCode);
                // 医嘱交费类别名称
                setChargeStatusName.invoke(
                        order,
                        DictionaryCache.getDictionary(
                                Constants.ORDER_CHARGE_STATUS).get(
                                chargeStatusCode));
                // 医嘱删除标志
                setDeleteFlag.invoke(order, deleteFlag);
                // 如为删除情况，则更新删除时间，否则更新更新时间
                if (Constants.DELETE_FLAG.equals(deleteFlag))
                {
                    setDeleteTime.invoke(order, systemTime);
                }
                else
                {
                    setUpdateTime.invoke(order, systemTime);
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
        return order;
    }

    /**
     * 通过处方号获取待删除的处方记录
     * @param prescriptionNo
     * @return
     */
    /*
     * private Prescription getDeletePrescription(String prescriptionNo) {
     * Prescription prescription = null; Map<String, Object> condition = new
     * HashMap<String, Object>(); condition.put("prescriptionNo",
     * prescriptionNo); condition.put("deleteFlag", Constants.NO_DELETE_FLAG);
     * List<Object> results = commonService.selectByCondition(
     * Prescription.class, condition); if (results != null && results.size() >
     * 0) { prescription = (Prescription) results.get(0);
     * prescription.setDeleteFlag(Constants.DELETE_FLAG);
     * prescription.setDeleteTime(systemTime); } return prescription; }
     */

}
