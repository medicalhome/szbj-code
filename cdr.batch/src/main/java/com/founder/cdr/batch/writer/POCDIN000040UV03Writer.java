/**
 * 电子病历
 * 
 * @version 1.0
 
 * @author jia_yanqing
 * @since 1.0
 */
package com.founder.cdr.batch.writer;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.founder.cdr.batch.writer.common.CommonWriterUtils;
import com.founder.cdr.cache.DictionaryCache;
import com.founder.cdr.core.Constants;
import com.founder.cdr.entity.ClinicalDocument;
import com.founder.cdr.entity.Diagnosis;
import com.founder.cdr.entity.InfectiousRecord;
import com.founder.cdr.entity.MedicalImaging;
import com.founder.cdr.entity.MedicalVisit;
import com.founder.cdr.entity.Patient;
import com.founder.cdr.entity.PatientCrossIndex;
import com.founder.cdr.entity.PatientDoctor;
import com.founder.cdr.entity.ProcedureOrder;
import com.founder.cdr.hl7.dto.pocdin000040uv03.POCDIN000040UV03;
import com.founder.cdr.hl7.dto.pocdin000040uv03.ReviewPersonDto;
import com.founder.cdr.service.ClinicalDocumentService;
import com.founder.cdr.service.CommonService;
import com.founder.cdr.service.DiagnosisService;
import com.founder.cdr.util.DateUtils;
import com.founder.cdr.util.StringUtils;

@Component(value = "pocdin000040uv03Writer")
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class POCDIN000040UV03Writer implements BusinessWriter<POCDIN000040UV03> {
	// 就诊记录
	private MedicalVisit medicalVisit;

	// 患者SN
	private BigDecimal patientSn;

	// 就诊SN
	private BigDecimal visitSn;

	// 患者本地ID
	private String patientLid;

	// 患者域ID
	private String patientDomain;

	private ClinicalDocument clinicalDocument;

	private InfectiousRecord infectiousRecord;

	// CDA图片序列名称
	private static final String MEDICAL_IMAGING_SEQUENCE = "SEQ_MEDICAL_IMAGING";

	// 病历文书序列名称
	private static final String DOCUMENT_SEQUENCE = "SEQ_DOCUMENT";

	// 获取系统时间
	private Date systemTime = DateUtils.getSystemTime();

	@Autowired
	private ClinicalDocumentService clinicalDocumentService;

	@Autowired
	private CommonService commonService;

	@Autowired
	private CommonWriterUtils commonWriterUtils;

	@Autowired
	private DiagnosisService diagnosisService;

	private static final Logger logger = LoggerFactory
			.getLogger(POCDIN000040UV03Writer.class);

	private Logger loggerBSDOCUMENT;

	// BS322 急诊患者记录单信息服务
	private static final Logger loggerBS322 = LoggerFactory.getLogger("BS322");

	// BS323 传染病卡信息服务
	private static final Logger loggerBS323 = LoggerFactory.getLogger("BS323");

	// BS324 入院许可证信息服务
	private static final Logger loggerBS324 = LoggerFactory.getLogger("BS324");

	// BS325 休假证明信息服务
	private static final Logger loggerBS325 = LoggerFactory.getLogger("BS325");

	// BS326 诊断证明信息服务
	private static final Logger loggerBS326 = LoggerFactory.getLogger("BS326");

	// BS327 麻醉记录信息服务
	private static final Logger loggerBS327 = LoggerFactory.getLogger("BS327");

	// BS328 麻醉评估信息服务
	private static final Logger loggerBS328 = LoggerFactory.getLogger("BS328");

	// BS329 麻醉术前访视记录信息服务
	private static final Logger loggerBS329 = LoggerFactory.getLogger("BS329");

	// BS379 麻醉术后访视记录信息服务
	private static final Logger loggerBS379 = LoggerFactory.getLogger("BS379");

	// BS331 护理记录信息服务
	private static final Logger loggerBS331 = LoggerFactory.getLogger("BS331");

	// BS332 护理评估信息服务
	private static final Logger loggerBS332 = LoggerFactory.getLogger("BS332");

	// BS333 门急诊病历信息服务
	private static final Logger loggerBS333 = LoggerFactory.getLogger("BS333");

	// BS334 留观病历信息服务
	private static final Logger loggerBS334 = LoggerFactory.getLogger("BS334");

	// BS335 入院记录信息服务
	private static final Logger loggerBS335 = LoggerFactory.getLogger("BS335");

	// BS336 病程记录信息服务
	private static final Logger loggerBS336 = LoggerFactory.getLogger("BS336");

	// BS337 手术操作记录信息服务
	private static final Logger loggerBS337 = LoggerFactory.getLogger("BS337");

	// BS338 知情同意和讨论记录信息服务
	private static final Logger loggerBS338 = LoggerFactory.getLogger("BS338");

	// BS340 会诊记录信息服务
	private static final Logger loggerBS340 = LoggerFactory.getLogger("BS340");

	// BS341 出院小结信息服务
	private static final Logger loggerBS341 = LoggerFactory.getLogger("BS341");

	// BS342 住院病案首页信息服务
	private static final Logger loggerBS342 = LoggerFactory.getLogger("BS342");

	// BS343 死亡记录信息服务
	private static final Logger loggerBS343 = LoggerFactory.getLogger("BS343");

	// BS344 24小时入出院记录信息服务
	private static final Logger loggerBS344 = LoggerFactory.getLogger("BS344");

	// BS345 24小时死亡记录信息服务
	private static final Logger loggerBS345 = LoggerFactory.getLogger("BS345");

	// BS347 体检报告信息服务
	private static final Logger loggerBS347 = LoggerFactory.getLogger("BS347");

	// BS348 病情记录信息服务
	private static final Logger loggerBS348 = LoggerFactory.getLogger("BS348");

	// BS349 入院护理评估信息服务
	private static final Logger loggerBS349 = LoggerFactory.getLogger("BS349");

	// BS350 每日护理评估信息服务
	private static final Logger loggerBS350 = LoggerFactory.getLogger("BS350");

	// BS351 透析记录信息服务
	private static final Logger loggerBS351 = LoggerFactory.getLogger("BS351");

	// BS356 血液透析记录信息服务
	private static final Logger loggerBS356 = LoggerFactory.getLogger("BS356");

	// BS357 营养病历信息服务
	private static final Logger loggerBS357 = LoggerFactory.getLogger("BS357");

	// BS357 传染病卡信息服务
	private static final Logger loggerBS358 = LoggerFactory.getLogger("BS358");

	// BS360 输血记录单信息服务 Added by yang_jianbo @ 2014-3-18
	private static final Logger loggerBS360 = LoggerFactory.getLogger("BS360");

	// BS370术前讨论信息服务 Add By wgy @ 2014-12-30
	private static final Logger loggerBS370 = LoggerFactory.getLogger("BS370");

	// BS372疑难危重病例讨论记录信息服务 Add By wgy @ 2014-12-30
	private static final Logger loggerBS372 = LoggerFactory.getLogger("BS372");

	// BS374 入院跌倒坠床压疮护理单信息服务 Add By du_xiaolan @ 2014-12-24
	private static final Logger loggerBS374 = LoggerFactory.getLogger("BS374");

	// BS371 出院记录信息服务 Add By lishenggen @ 2014-12-25 17:08
	private static final Logger loggerBS371 = LoggerFactory.getLogger("BS371");

	// BS375 术前小结信息服务 Add By lishenggen @ 2014-12-26 16:28
	private static final Logger loggerBS373 = LoggerFactory.getLogger("BS373");

	// BS373 死亡病例讨论服务 Add By wgy @ 2015-1-4 16:28
	private static final Logger loggerBS375 = LoggerFactory.getLogger("BS375");

	// BS376 手术安全核查表信息服务 Add By li_shenggen @ 2015-1-27 15:05
	private static final Logger loggerBS376 = LoggerFactory.getLogger("BS376");

	// BS378 输血执行记录单信息服务 Add By du_xiaolan @ 2015-02-04
	private static final Logger loggerBS378 = LoggerFactory.getLogger("BS378");

	// BS378 输血执行记录单信息服务 Add By du_xiaolan @ 2015-03-25
	private static final Logger loggerBS377 = LoggerFactory.getLogger("BS377");
	
	// BS401 麻醉记录单附页信息服务 Add By yu_yzh @ 2015-12-02
	private static final Logger loggerBS401 = LoggerFactory.getLogger("BS401");
	
	// BS380 首次病程记录信息服务 Add By yu_yzh @ 2015-12-02
	private static final Logger loggerBS380 = LoggerFactory.getLogger("BS380");
	
	// BS382 接班记录信息服务 Add By yu_yzh @ 2015-12-02
	private static final Logger loggerBS382 = LoggerFactory.getLogger("BS382");
	
	// BS385 抢救记录信息服务 Add By yu_yzh @ 2015-12-02
	private static final Logger loggerBS385 = LoggerFactory.getLogger("BS385");
	
	// BS386 术后首次记录信息服务 Add By yu_yzh @ 2015-12-02
	private static final Logger loggerBS386 = LoggerFactory.getLogger("BS386");
	
	// BS400 分娩记录信息服务 Add By yu_yzh @ 2015-12-02
	private static final Logger loggerBS400 = LoggerFactory.getLogger("BS400");
		
	// BS403 新生儿评估单信息服务 Add By yu_yzh @ 2015-12-02
	private static final Logger loggerBS403 = LoggerFactory.getLogger("BS403");	
	
	// BS410 护理计划信息服务 Add By yu_yzh @ 2015-12-02
	private static final Logger loggerBS410 = LoggerFactory.getLogger("BS410");	
	
	// BS408 产科首次病程记录信息服务 Add By yu_yzh @ 2015-12-02
	private static final Logger loggerBS408 = LoggerFactory.getLogger("BS408");	
	
	// BS405 插管记录信息服务 Add By yu_yzh @ 2015-12-02
	private static final Logger loggerBS405 = LoggerFactory.getLogger("BS405");	
	
	// BS406 有创操作记录信息服务 Add By yu_yzh @ 2015-12-02
	private static final Logger loggerBS406 = LoggerFactory.getLogger("BS406");	
	
	// BS418 产后记录信息服务 Add By yu_yzh @ 2015-12-02
	private static final Logger loggerBS418 = LoggerFactory.getLogger("BS418");
	
	// BS412 产钳手术记录信息服务 Add By yu_yzh @ 2015-12-02
	private static final Logger loggerBS412 = LoggerFactory.getLogger("BS412");
	
	// BS392 剖宫产手术记录单信息服务 Add By yu_yzh @ 2015-12-02
	private static final Logger loggerBS392 = LoggerFactory.getLogger("BS392");
	
	// BS420 手术风险评估表信息服务 Add By yu_yzh @ 2015-12-02
	private static final Logger loggerBS420 = LoggerFactory.getLogger("BS420");
	
	// BS413 胎头吸引手术记录信息服务 Add By yu_yzh @ 2015-12-02
	private static final Logger loggerBS413 = LoggerFactory.getLogger("BS413");
	
	// BS417 心脏介入诊疗手术记录信息服务 Add By yu_yzh @ 2015-12-02
	private static final Logger loggerBS417 = LoggerFactory.getLogger("BS417");
	
	// BS421 重大手术特殊手术备案表信息服务 Add By yu_yzh @ 2015-12-02
	private static final Logger loggerBS421 = LoggerFactory.getLogger("BS421");
	
	// BS422 输血不良反应信息反馈表信息服务 Add By yu_yzh @ 2015-12-02
	private static final Logger loggerBS422 = LoggerFactory.getLogger("BS422");
	
	// BS419 临床药师用药史和药物整合记录表信息服务 Add By yu_yzh @ 2015-12-02
	private static final Logger loggerBS419 = LoggerFactory.getLogger("BS419");
	
	// $Author: tong_meng
	// $Date: 2013/8/30 15:00
	// [BUG]0036757 ADD BEGIN
	private String serviceId;

	// [BUG]0036757 ADD END

	// 会诊申请单号
	private String requestNo;

	@Override
	public boolean validate(POCDIN000040UV03 pocdin000040uv03) {

		// $Author: tong_meng
		// $Date: 2013/8/30 15:00
		// [BUG]0036757 ADD BEGIN
		serviceId = pocdin000040uv03.getMessage().getServiceId();
		// [BUG]0036757 ADD END
		// 针对会诊申请单号
		if ("BS340".equals(pocdin000040uv03.getServiceId())) {
			List<String> orderLids = pocdin000040uv03.getOrderLids();
			if (!orderLids.isEmpty()) {
				for (int i = 0; i < orderLids.size(); i++) {
					requestNo = orderLids.get(i);
				}
			}
		}
		// 获取文档日志输出对象
		loggerBSDOCUMENT = getDocumentLogger(serviceId);

		if (StringUtils.isEmpty(pocdin000040uv03.getMessage().getOrgCode())) {
			pocdin000040uv03.getMessage().setOrgCode(Constants.HOSPITAL_CODE);
		}

		// Author :jia_yanqing
		// Date : 2013/1/22 15:00
		// [BUG]0042085 MODIFY BEGIN

		// if (pocdin000040uv03.getOrganizationCode() == null
		// || "".equals(pocdin000040uv03.getOrganizationName()))

		// $Author :yang_mingjie
		// $Date : 2014/06/26 10:09$
		// [BUG]0045630 MODIFY BEGIN
		if (StringUtils.isEmpty(pocdin000040uv03.getOrganizationCode())
				|| StringUtils.isEmpty(pocdin000040uv03.getOrganizationName())) {
			pocdin000040uv03.setOrganizationCode(Constants.HOSPITAL_CODE);
			pocdin000040uv03.setOrganizationName(Constants.HOSPITAL_NAME);
			// [BUG]0045630 MODIFY END
		}
		// 消息头中的医疗机构编码与消息中的医疗机构编码对比
		/*
		 * if (pocdin000040uv03.getMessage().getOrgCode() == null ||
		 * !pocdin000040uv03.getMessage().getOrgCode()
		 * .equals(pocdin000040uv03.getOrganizationCode())) {
		 * loggerBSDOCUMENT.error( "Message:{},validate:{}",
		 * pocdin000040uv03.toString(), "消息头中的医疗机构编码和消息体中的医疗机构编码不同，消息头中的医疗机构编码="
		 * + pocdin000040uv03.getMessage().getOrgCode() + "，消息体中的医疗机构编码=" +
		 * pocdin000040uv03.getOrganizationCode()); return false; }
		 */
		// [BUG]0042085 MODIFY END

		String versionNumber = pocdin000040uv03.getVersionNumber();
		// 非空校验标志
		Boolean flag = true;
		List<String> validateList = new ArrayList<String>();
		// 测试用日志
		Map<String, String> logMap = new HashMap<String, String>();
		// Author :jia_yanqing
		// Date : 2014/3/5 11:00
		// [BUG]0042714 MODIFY BEGIN
		if (!(isDeleteMessage(pocdin000040uv03) && "BS358".equals(serviceId))) {
			// 文档名称
			validateList.add(pocdin000040uv03.getDocumentName());
			logMap.put("文档名称不能为空！", pocdin000040uv03.getDocumentName());

			if (Constants.HEMODIALYSIS_SERVICE_ID.equals(serviceId)
					|| Constants.DIALYSIS_PRESCRIPTION_SERVICE_ID
							.equals(serviceId))
			// bs377 bs356不验证流水号 只验证patientlid
			{
				validateList.add(pocdin000040uv03.getPatientLid());
				logMap.put("患者Lid不能为空！", pocdin000040uv03.getPatientLid());
			} else if (!Constants.INPATIENT_FRONTPAGE_SERVICE_ID
					.equals(serviceId)) {
				// 电子病历系统不验证患者lid
				if (!Constants.EMR_SOURCE_ID.equals(pocdin000040uv03
						.getMessage().getSourceSysCd())) {
					// 患者Lid
					validateList.add(pocdin000040uv03.getPatientLid());
					logMap.put("患者Lid不能为空！", pocdin000040uv03.getPatientLid());
				}

				// 就诊流水号
				validateList.add(pocdin000040uv03.getVisitOrdNo());
				logMap.put("就诊流水号不能为空！", pocdin000040uv03.getVisitOrdNo());
			}

			if (!isDeleteMessage(pocdin000040uv03)) {
				// 文档类型
				validateList.add(pocdin000040uv03.getDocumentType());
				logMap.put("文档类型不能为空！", pocdin000040uv03.getDocumentType());
				// 文档名称
				validateList.add(pocdin000040uv03.getDocumentName());
				logMap.put("文档名称不能为空！", pocdin000040uv03.getDocumentName());
				// 域ID
				validateList.add(pocdin000040uv03.getPatientDomain());
				logMap.put("域ID不能为空！", pocdin000040uv03.getPatientDomain());
				// $Author :jin_peng
				// $Date : 2013/10/25 10:34$
				// [BUG]0038524 MODIFY BEGIN
				// 麻醉或术前文档可能无法提供就诊次数
				// if (!isAnesthesiaPreoperation(serviceId)) {
				// }
				// 就诊次数
				/*
				 * validateList.add(pocdin000040uv03.getVisitTimes());
				 * logMap.put("就诊次数不能为空！", pocdin000040uv03.getVisitTimes());
				 */
				// [BUG]0038524 MODIFY END
				// 服务ID
				validateList.add(pocdin000040uv03.getServiceId());
				logMap.put("服务ID不能为空！", pocdin000040uv03.getServiceId());

				// 就诊类型
				validateList.add(pocdin000040uv03.getVisitTypeCode());
				logMap.put("就诊类型不能为空！", pocdin000040uv03.getVisitTypeCode());
				// $Author :jin_peng
				// $Date : 2013/10/25 10:34$
				// [BUG]0038524 MODIFY BEGIN
				// $Author :tong_meng
				// $Date : 2013/10/10 11:00$
				// [BUG]0037754 ADD BEGIN
				// CVIS EP报告需要用判断关联医嘱号是否存在
				/*
				 * if (isCVISEPReport(pocdin000040uv03.getServiceId()) ||
				 * isAnesthesiaPreoperation(serviceId))
				 */
				if (isCVISEPReport(pocdin000040uv03.getServiceId())) {
					List<String> orderLids = pocdin000040uv03.getOrderLids();
					if (orderLids.isEmpty()) {
						logMap.put("CVIS EP手术操作记录报告关联医嘱号不能为空！", "orderLids = "
								+ orderLids.size());
					} else {
						for (int i = 0; i < orderLids.size(); i++) {
							validateList.add(orderLids.get(i));
							logMap.put("CVIS EP手术操作记录报告关联医嘱号第" + i + "个不能为空！",
									orderLids.get(i));
						}
					}
				}
				// if ("BS340".equals(pocdin000040uv03.getServiceId()))
				// {
				// List<String> orderLids = pocdin000040uv03.getOrderLids();
				// if (orderLids.isEmpty())
				// {
				// logMap.put("会诊申请单号不能为空！", "orderLids = "
				// + orderLids.size());
				// }
				// else
				// {
				// for (int i = 0; i < orderLids.size(); i++)
				// {
				// validateList.add(orderLids.get(i));
				// logMap.put("BS340会诊记录申请单号不能为空！",
				// orderLids.get(i));
				// }
				// }
				// }
				// [BUG]0037754 ADD END
				// [BUG]0038524 MODIFY END

				// 依据消息不同进行不同的非空校验:新增非门急诊病历消息时校验文档作者信息
//				if ((Constants.VERSION_NUMBER_INSERT.equals(versionNumber) && isOutpatientMessage(pocdin000040uv03))
//						|| !isOutpatientMessage(pocdin000040uv03)) {
//					// $Author :jin_peng
//					// $Date : 2013/10/28 11:22$
//					// [BUG]0037371 0036699 MODIFY BEGIN
//					// $Author :jin_peng
//					// $Date : 2013/09/13 14:16$
//					// [BUG]0037371 DELETE BEGIN
//					// 文档作者-填写时间
//					validateList.add(pocdin000040uv03.getWriteTime());
//					logMap.put("文档作者-填写时间不能为空", pocdin000040uv03.getWriteTime());
//
//					// [BUG]0037371 DELETE END
//
//					// $Author :chang_xuewen
//					// $Date : 2013/08/28 14:00$
//					// [BUG]0036699 DELETE BEGIN
//					// Author :jia_yanqing
//					// Date : 2013/10/30 16:00
//					// [BUG]0038777 MODIFY BEGIN
//					// 因为血透BS356无法提供文档作者-医生编码，所以加过滤
//					// 文档作者-医生编码
//					if (!"BS356".equals(serviceId)
//							&& !Constants.INPATIENT_FRONTPAGE_SERVICE_ID
//									.equals(serviceId)) {
//						validateList.add(pocdin000040uv03.getDocumentAuthor());
//						logMap.put("文档作者-医生编码不能为空",
//								pocdin000040uv03.getDocumentAuthor()); // 文档作者-医生姓名
//					}
//
//					// [BUG]0038777 MODIFY BEGIN
//					validateList.add(pocdin000040uv03.getDocumentAuthorName());
//					logMap.put("文档作者-医生姓名不能为空",
//							pocdin000040uv03.getDocumentAuthorName());
//
//					// [BUG]0036699 DELETE END
//					// [BUG]0037371 0036699 MODIFY END
//					String[] str = new String[validateList.size()];
//					flag = StringUtils.isNotNullAll(validateList.toArray(str));
//
//				} else if (Constants.VERSION_NUMBER_UPDATE
//						.equals(versionNumber)
//						&& isOutpatientMessage(pocdin000040uv03)
//						&& !"BS358".equals(serviceId)) {
//					// 文档修改者-填写时间
//					validateList.add(pocdin000040uv03.getModifyTime());
//					logMap.put("文档修改者-修改时间不能为空",
//							pocdin000040uv03.getModifyTime());
//					// 文档修改者-医生编码
//					validateList.add(pocdin000040uv03.getDocumentModifier());
//					logMap.put("文档修改者-医生编码不能为空",
//							pocdin000040uv03.getDocumentModifier());
//					// 文档修改者-医生姓名
//					validateList
//							.add(pocdin000040uv03.getDocumentModifierName());
//					logMap.put("文档修改者-医生姓名不能为空",
//							pocdin000040uv03.getDocumentModifierName());
//					String[] str = new String[validateList.size()];
//					flag = StringUtils.isNotNullAll(validateList.toArray(str));
//				}
			}
		}
		// [BUG]0042714 MODIFY END
		// 测试所用非空验证日志
		if (flag == false) {
			Set<Entry<String, String>> entries = logMap.entrySet();
			Iterator<Entry<String, String>> iterator = entries.iterator();

			// $Author :chang_xuewen
			// $Date : 2013/06/05 16:00$
			// [BUG]0033373 MODIFY BEGIN
			StringBuilder logs = new StringBuilder();

			while (iterator.hasNext()) {
				Entry<String, String> entry = iterator.next();

				if (StringUtils.isEmpty(entry.getValue())) {
					logs.append(entry.getKey() + " ; ");
					// logger.error("{} \n",entry.getKey());
				}
			}

			// $Author :jin_peng
			// $Date : 2013/09/09 16:09$
			// [BUG]0037197 MODIFY BEGIN
			loggerBSDOCUMENT.error("Message:{},validate:{}",
					pocdin000040uv03.toString(), logs.toString());

			// [BUG]0037197 MODIFY END
			// [BUG]0033373 MODIFY END
		}
		return flag;
	}

	/**
	 * 新增/更新时检查与就诊的关联关系
	 */
	@Override
	public boolean checkDependency(POCDIN000040UV03 pocdin000040uv03,
			boolean flag) {
		if (isDeleteMessage(pocdin000040uv03)) {
			// Author :jia_yanqing
			// Date : 2014/3/5 13:00
			// [BUG]0042714 ADD BEGIN
			if ("BS358".equals(serviceId)
					|| Constants.EMR_SOURCE_ID.equals(pocdin000040uv03
							.getMessage().getSourceSysCd())) {
				clinicalDocument = clinicalDocumentService
						.selectDocByLidAndOrg(
								pocdin000040uv03.getDocumentLid(), serviceId,
								pocdin000040uv03.getOrganizationCode());
				// infectiousRecord = clinicalDocumentService.selectIRByLid(
				// pocdin000040uv03.getDocumentLid(),
				// pocdin000040uv03.getOrganizationCode());

			}
			// [BUG]0042714 ADD END
			else {
				// Author :jia_yanqing
				// Date : 2013/11/11 10:09
				// [BUG]0038477 MODIFY BEGIN
				clinicalDocument = clinicalDocumentService.selectDocByLid(
						pocdin000040uv03.getDocumentLid(),
						pocdin000040uv03.getPatientLid(),
						pocdin000040uv03.getServiceId(),
						pocdin000040uv03.getOrganizationCode());
				// [BUG]0038477 MODIFY END
			}

			// Author :jia_yanqing
			// Date : 2014/3/5 13:00
			// [BUG]0042714 ADD BEGIN
			// if ("BS358".equals(serviceId))
			// {
			// if (infectiousRecord == null)
			// {
			// logger.error("在删除传染病报卡消息时，对应记录不存在：documentLid:"
			// + pocdin000040uv03.getDocumentLid() + "，orgCode:"
			// + pocdin000040uv03.getOrganizationCode());
			//
			// if (flag)
			// {
			// loggerBSDOCUMENT.error(
			// "Message:{},checkDependency:{}",
			// pocdin000040uv03.toString(),
			// "在删除住院病案消息时，对应病案消息不存在：documentLid:"
			// + pocdin000040uv03.getDocumentLid()
			// + "，orgCode:"
			// + pocdin000040uv03.getOrganizationCode());
			// }
			// return false;
			// }
			// }
			// [BUG]0042714 ADD END

			if (clinicalDocument == null) {
				logger.error("在删除住院病案消息时，对应病案消息不存在：documentLid:"
						+ pocdin000040uv03.getDocumentLid() + "，patientLid:"
						+ pocdin000040uv03.getPatientLid());

				if (flag) {
					// $Author :jin_peng
					// $Date : 2013/09/09 16:09$
					// [BUG]0037197 MODIFY BEGIN
					loggerBSDOCUMENT.error(
							"Message:{},checkDependency:{}",
							pocdin000040uv03.toString(),
							"在删除住院病案消息时，对应病案消息不存在：documentLid:"
									+ pocdin000040uv03.getDocumentLid()
									+ "，patientLid:"
									+ pocdin000040uv03.getPatientLid());

					// [BUG]0037197 MODIFY END
				}
				return false;
			}
		} else {
			patientLid = pocdin000040uv03.getPatientLid();
			patientDomain = pocdin000040uv03.getPatientDomain();
			PatientCrossIndex patientCrossIndex = null;
			if (Constants.HEMODIALYSIS_SERVICE_ID.equals(serviceId)
					|| Constants.DIALYSIS_PRESCRIPTION_SERVICE_ID
							.equals(serviceId)) {
				// bs356和bs377通过patientLid到交叉索引表找到patientEid 再到patient表查找patientSn
				patientCrossIndex = commonService.getPatientRecordFromPCIByLid(
						patientDomain, pocdin000040uv03.getPatientLid(),
						pocdin000040uv03.getOrganizationCode());
				if (patientCrossIndex != null) {
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("deleteFlag", Constants.NO_DELETE_FLAG);
					map.put("patientEid", patientCrossIndex.getPatientEid());

					List<Object> results = commonService.selectByCondition(
							Patient.class, map);
					if (results != null && results.size() > 0) {
						Patient patient = (Patient) results.get(0);
						patientSn = patient.getPatientSn();
					} else {
						logger.error("患者表中没有对应的患者，patientEid = "
								+ patientCrossIndex.getPatientEid());
						if (flag) {
							loggerBSDOCUMENT
									.error("Message:{}, checkDependency:{}",
											pocdin000040uv03.toString(),
											"患者表中没有对应的患者，patientEid = "
													+ patientCrossIndex
															.getPatientEid());
						}
						return false;
					}

				} else {
					logger.error(
							"MessageId:{};交叉索引表没有该患者："
									+ pocdin000040uv03.getPatientDomain()
									+ "患者ID:" + patientLid + "医疗机构编码:"
									+ pocdin000040uv03.getOrganizationCode()
									+ "患者lid"
									+ pocdin000040uv03.getPatientLid(),
							pocdin000040uv03.getMessage().getId());
					if (flag) {
						loggerBSDOCUMENT.error(
								"Message:{},checkDependency:{}",
								pocdin000040uv03.toString(),
								"交叉索引表没有该患者："
										+ pocdin000040uv03.getPatientDomain()
										+ "患者ID:"
										+ patientLid
										+ "医疗机构编码:"
										+ pocdin000040uv03
												.getOrganizationCode()
										+ "患者lid"
										+ pocdin000040uv03.getPatientLid());
					}
					return false;
				}

			} else {
				// 电子病历消息无患者lid，交叉索引表取得患者lid
				if (StringUtils.isEmpty(patientLid)
						|| Constants.EMR_SOURCE_ID.equals(pocdin000040uv03
								.getMessage().getSourceSysCd())) {
					patientCrossIndex = commonService.getPatientRecordFromPCI(
							patientDomain, pocdin000040uv03.getOutPatientNo(),
							pocdin000040uv03.getOrganizationCode(),
							Constants.VISIT_TYPE_CODE_INPATIENT);
					if (patientCrossIndex != null) {
						// 患者本地ID
						patientLid = patientCrossIndex.getPatientLid();

						logger.info("病历文档,患者本地ID {}", patientLid);
					}
				}

				// 获取就诊记录
				medicalVisit = getMedicalVisit(pocdin000040uv03);
				if (medicalVisit == null) {
					// $Author :chang_xuewen
					// $Date : 2013/06/05 16:00$
					// [BUG]0033373 MODIFY BEGIN
					logger.error("在新增/更新住院病案消息时相应的就诊信息不存在: {}", "住院病案关联消息");
					if (flag) {
						// $Author :jin_peng
						// $Date : 2013/09/09 16:09$
						// [BUG]0037197 MODIFY BEGIN
						loggerBSDOCUMENT.error("Message:{},checkDependency:{}",
								pocdin000040uv03.toString(),
								"在新增/更新住院病案消息时相应的就诊信息不存在:住院病案关联消息");

						// [BUG]0037197 MODIFY END
					}
					// [BUG]0033373 MODIFY END
					return false;
				} else {
					patientSn = medicalVisit.getPatientSn();
					visitSn = medicalVisit.getVisitSn();
				}

			}
			// $Author :tong_meng
			// $Date : 2013/10/10 11:00$
			// [BUG]0037754 MODIFY BEGIN
			// CVIS EP报告需要用关联医嘱号判断手术医嘱是否存在，不需要判断就诊是否存在
			// 其它报告则判断就诊是否存在
			if (isCVISEPReport(pocdin000040uv03.getServiceId())) {
				if (!checkProcedureOrder(pocdin000040uv03, flag, medicalVisit)) {
					// 在此checkProcedureOrder方法中已打出log，所以这里直接返回false
					return false;
				}
			}
			// [BUG]0037754 MODIFY END
		}
		// logger.info("在新增/更新住院病案消息时相应的就诊信息存在: {}", "住院病案关联消息");
		return true;
	}

	/**
	 * 新增/更新
	 */
	@Override
	@Transactional
	public void saveOrUpdate(POCDIN000040UV03 pocdin000040uv03) {
		if (isDeleteMessage(pocdin000040uv03)) {
			clinicalDocument.setDeleteFlag(Constants.DELETE_FLAG);
			clinicalDocument.setDeleteby(serviceId);
			clinicalDocument.setDeleteTime(systemTime);
			clinicalDocumentService.deleteClinicalDocument(clinicalDocument);

			// if("BS358".equals(serviceId))
			// {
			// infectiousRecord.setDeleteFlag(Constants.DELETE_FLAG);
			// infectiousRecord.setDeleteTime(systemTime);
			// clinicalDocumentService.deleteInfectiousRecord(infectiousRecord);
			// }
		} else {
			// String documentCDA = pocdin000040uv03.getMessage().getContent();
			String flag = "";
			MedicalImaging medicalImaging = null;
			clinicalDocument = checkClinicalDocument(pocdin000040uv03, visitSn);
			if (clinicalDocument != null) {
				flag = Constants.VERSION_NUMBER_UPDATE;
				logger.info("数据库存在该记录: {}", "执行更新操作");
				clinicalDocument = getClinicalDocument(pocdin000040uv03, flag);
				
				medicalImaging = getCDAImage(pocdin000040uv03, flag);
				
				// 执行更新操作
				clinicalDocumentService.updateClinicalDocument(
						clinicalDocument, medicalImaging,
						pocdin000040uv03.getMessage(), systemTime);
			} else {
				flag = Constants.VERSION_NUMBER_INSERT;
				logger.info("数据库不存在该记录: {}", "执行新增操作");
				clinicalDocument = getClinicalDocument(pocdin000040uv03, flag);
				
				medicalImaging = getCDAImage(pocdin000040uv03, flag);
				
				if ("BS340".equals(clinicalDocument.getServiceId())) {
					// 会诊申请类型编码 ,01暂代表会诊申请
					clinicalDocument.setRequestCode("01");
					// 会诊申请编号
					clinicalDocument.setRequestNo(requestNo);
				}
				// 执行保存操作
				clinicalDocumentService.saveClinicalDocument(clinicalDocument,
						medicalImaging, pocdin000040uv03.getMessage());
				// $Author:wei_peng
				// $Date:2012/11/06 13:59
				// [BUG]0011030 ADD BEGIN
				if (pocdin000040uv03.getDocumentAuthor() != null
						&& !pocdin000040uv03.getDocumentAuthor().isEmpty()
						&& !Constants.HEMODIALYSIS_SERVICE_ID.equals(serviceId)
						&& !Constants.DIALYSIS_PRESCRIPTION_SERVICE_ID
								.equals(serviceId)) {
					//BS377 BS356不存patient_doctor
					PatientDoctor patientDoctor = commonWriterUtils
							.getPatientDoctor(visitSn, patientSn,
									pocdin000040uv03.getDocumentAuthor(),
									pocdin000040uv03.getDocumentAuthorName(),
									systemTime);
					if (patientDoctor != null) {
						// $Author: tong_meng
						// $Date: 2013/8/30 15:00
						// [BUG]0036757 ADD BEGIN
						patientDoctor.setCreateby(serviceId); // 设置创建人
						// [BUG]0036757 ADD END
						commonService.save(patientDoctor);
					}
				}
				// [BUG]0011030 ADD END
			}
			// 病案首页服务，增加出院诊断
			if (Constants.INPATIENT_FRONTPAGE_SERVICE_ID.equals(serviceId)) {
				List<Object> diagnosisResultList = getDiagnosisMessage(pocdin000040uv03);
				diagnosisService.saveList(diagnosisResultList, medicalVisit);
			}
		}

	}

	// $Author:wei_peng
	// $Date:2012/10/25 17:30
	// $[BUG]0010715 ADD BEGIN
	/**
	 * 从消息中获取CDA图片
	 * 
	 * @param pocdin000040uv03
	 *            消息信息
	 * @return CDA图片信息
	 */
	public MedicalImaging getCDAImage(POCDIN000040UV03 pocdin000040uv03,
			String flag) {
		MedicalImaging CDAImage = new MedicalImaging();
		CDAImage.setImagingSn(commonService
				.getSequence(MEDICAL_IMAGING_SEQUENCE)); // 设置影像内部序列号
		CDAImage.setImageContent(getImageContentAfterDecode(pocdin000040uv03
				.getImageText())); // 设置影像内容
		CDAImage.setPromptMessage(pocdin000040uv03.getPrompt()); // 设置提示信息
		CDAImage.setDocumentSn(clinicalDocument.getDocumentSn());// 文档内部序列号

		// 图片扩展名为image/jpg的形式时进行判断取值
		String imageExtension = pocdin000040uv03.getImageExtension();
		if (!StringUtils.isEmpty(imageExtension)) {
			if (imageExtension.indexOf("/") > 0) {
				if (imageExtension.split("/").length > 1) {
					imageExtension = imageExtension.split("/")[1];
				} else {
					imageExtension = "";
				}
			}
		}
		CDAImage.setImageFormat(imageExtension); // 设置影像格式
		CDAImage.setCreateTime(systemTime); // 设置该记录创建时间
		CDAImage.setUpdateTime(systemTime); // 设置该记录更新时间
		CDAImage.setUpdateCount(Constants.INSERT_UPDATE_COUNT); // 设置该记录初始更新回数
		CDAImage.setDeleteFlag(Constants.NO_DELETE_FLAG); // 设置该记录初始删除标记
		// $Author: tong_meng
		// $Date: 2013/8/30 15:00
		// [BUG]0036757 ADD BEGIN
		CDAImage.setCreateby(serviceId); // 设置创建人
		// [BUG]0036757 ADD END
		return CDAImage;
	}

	/**
	 * 对二进制的图片信息进行base64的转码操作
	 * 
	 * @param imageText
	 *            二进制图片信息
	 * @return 转码后的字节数据图片
	 */
	public byte[] getImageContentAfterDecode(String imageText) {
		byte[] bt = null;
		if (imageText != null && !imageText.isEmpty()) {
			bt = Base64.decodeBase64(imageText);
		}
		return bt;
	}

	// $[BUG]0010715 ADD END

	/**
	 * 封装新增或者更新的电子病历对象
	 * 
	 * @param pocdin000040uv03
	 *            ,flag
	 * @return 电子病历
	 */
	public ClinicalDocument getClinicalDocument(
			POCDIN000040UV03 pocdin000040uv03, String flag) {
		if (Constants.VERSION_NUMBER_INSERT.equals(flag)) {
			clinicalDocument = new ClinicalDocument();

			// 文档内部序列号
			clinicalDocument.setDocumentSn(commonService
					.getSequence(DOCUMENT_SEQUENCE));
			// 就诊内部序列号
			clinicalDocument.setVisitSn(visitSn);
			// 患者内部序列号
			clinicalDocument.setPatientSn(patientSn);
			// 域代码
			clinicalDocument.setPatientDomain(patientDomain);
			// 患者本地ID
			clinicalDocument.setPatientLid(patientLid);
			// 文档本地ID
			clinicalDocument.setDocumentLid(pocdin000040uv03.getDocumentLid());
			// 创建时间
			clinicalDocument.setCreateTime(systemTime);
			// 更新回数
			clinicalDocument.setUpdateCount(Constants.INSERT_UPDATE_COUNT);

			// $Author: tong_meng
			// $Date: 2013/8/30 15:00
			// [BUG]0036757 ADD BEGIN
			clinicalDocument.setCreateby(serviceId); // 设置创建人
		} else {
			clinicalDocument.setUpdateby(serviceId); // 设置创建人
		}
		// [BUG]0036757 ADD END
		// 文档作者ID
		clinicalDocument
				.setDocumentAuthor(pocdin000040uv03.getDocumentAuthor());
		// 文档作者姓名
		clinicalDocument.setDocumentAuthorName(pocdin000040uv03
				.getDocumentAuthorName());
		// 作成时间
		clinicalDocument.setWriteTime(DateUtils.stringToDate(pocdin000040uv03
				.getWriteTime()));

		String document_type = Constants.obtainDocumentTypeMap().get(
				pocdin000040uv03.getDocumentType());
		// 文档类型
		clinicalDocument.setDocumentType(document_type);

		// 入院记录类型编码
		clinicalDocument.setInRecordType(pocdin000040uv03.getInRecordType());
		// $Author :jia_yanqing
		// $Date : 2012/7/31 10:33$
		// [BUG]0008337 MODIFY BEGIN
		// $Author :tong_meng
		// $Date : 2012/7/1 16:33$
		// [BUG]0007418 ADD BEGIN
		// 文档类型名称
		clinicalDocument.setDocumentTypeName(Constants
				.obtainDocumentTypeNameMap().get(document_type));
		// [BUG]0007418 ADD END
		// [BUG]0008337 MODIFY END

		// $Author:wei_peng
		// $Date: 2012/7/16 13:43$
		// [BUG]0007946 ADD BEGIN
		// 文档类型名称
		clinicalDocument.setServiceId(pocdin000040uv03.getServiceId());
		// [BUG]0007946 ADD END

		// $Author:wei_peng
		// $Date: 2012/7/24 11:23$
		// [BUG]0008061 ADD BEGIN
		// 文档修改者ID
		clinicalDocument.setDocumentModifier(pocdin000040uv03
				.getDocumentModifier());
		// 文档修改者名称
		clinicalDocument.setDocumentModifierName(pocdin000040uv03
				.getDocumentModifierName());
		// 文档修改时间
		clinicalDocument.setModifyTime(DateUtils.stringToDate(pocdin000040uv03
				.getModifyTime()));
		// 获取最近审核的审核人信息
		ReviewPersonDto reviewPerson = getLatestReviewPerson(pocdin000040uv03
				.getReviewPersons());
		// 文档审核者ID
		clinicalDocument.setReviewPerson(reviewPerson.getReviewPerson());
		// 文档审核者姓名
		clinicalDocument
				.setReviewPersonName(reviewPerson.getReviewPersonName());
		// 文档审核时间
		clinicalDocument.setReviewTime(DateUtils.stringToDate(reviewPerson
				.getReviewTime()));
		// [BUG]0008061 ADD END

		// 文档名称
		clinicalDocument.setDocumentName(pocdin000040uv03.getDocumentName());

		// Author:jia_yanqing
		// Date: 2013/4/10 14:23
		// [BUG]0014977 MODIFY BEGIN
		// 提交时间（即签章时间或修改时间或作成时间）
		if (pocdin000040uv03.getSignTime() != null
				&& !"".equals(pocdin000040uv03.getSignTime())) {
			clinicalDocument.setSubmitTime(DateUtils
					.stringToDate(pocdin000040uv03.getSignTime()));
		} else if (pocdin000040uv03.getModifyTime() != null
				&& !"".equals(pocdin000040uv03.getModifyTime())) {
			clinicalDocument.setSubmitTime(DateUtils
					.stringToDate(pocdin000040uv03.getModifyTime()));
		} else if (clinicalDocument.getWriteTime() != null
				&& !"".equals(pocdin000040uv03.getWriteTime())) {
			clinicalDocument.setSubmitTime(DateUtils
					.stringToDate(pocdin000040uv03.getWriteTime()));
		}
		// [BUG]0014977 MODIFY END

		// 电子签名时间
		clinicalDocument.setSignTime(DateUtils.stringToDate(pocdin000040uv03
				.getSignTime()));
		// 电子签章ID
		clinicalDocument.setSignatureId(pocdin000040uv03.getSignatureId());
		// 医疗机构编码
		clinicalDocument.setOrgCode(pocdin000040uv03.getOrganizationCode());
		// 医疗机构名称
		clinicalDocument.setOrgName(pocdin000040uv03.getOrganizationName());
		// 更新时间
		clinicalDocument.setUpdateTime(systemTime);
		// 删除标志
		clinicalDocument.setDeleteFlag(Constants.NO_DELETE_FLAG);

		return clinicalDocument;
	}

	/**
	 * 根据域ID，病人本地LID，病历号查询是否已经存在消息中的住院病案
	 * 
	 * @param pocdin000040uv03
	 * @return ClinicalDocument
	 */
	public ClinicalDocument checkClinicalDocument(
			POCDIN000040UV03 pocdin000040uv03, BigDecimal visitSn) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("patientDomain", patientDomain);
		map.put("patientLid", patientLid);
		map.put("documentLid", pocdin000040uv03.getDocumentLid());
		// Author :jia_yanqing
		// Date : 2013/11/11 10:09
		// [BUG]0038477 ADD BEGIN
		map.put("serviceId", pocdin000040uv03.getServiceId());
		// [BUG]0038477 ADD END
		map.put("orgCode", pocdin000040uv03.getOrganizationCode());
		if (visitSn != null) {
			map.put("visitSn", visitSn);
		}
		map.put("deleteFlag", Constants.NO_DELETE_FLAG);

		List<Object> clinicalDocumentList = commonService.selectByCondition(
				ClinicalDocument.class, map);
		if (clinicalDocumentList != null && !clinicalDocumentList.isEmpty()) {
			clinicalDocument = (ClinicalDocument) clinicalDocumentList.get(0);
		}

		return clinicalDocument;
	}

	// $Author :jin_peng
	// $Date : 2013/10/25 10:34$
	// [BUG]0038524 MODIFY BEGIN
	/**
	 * 获取电子病历关联的就诊记录
	 * 
	 * @param pocdin000040uv03
	 * @return 就诊记录
	 */
	public MedicalVisit getMedicalVisit(POCDIN000040UV03 pocdin000040uv03) {

		MedicalVisit medicalVisit = null;

		// 如果为麻醉，术前文档则通过医嘱查询就诊
		/*
		 * if (isAnesthesiaPreoperation(serviceId)) { //
		 * 门诊和住院分别用申请单号和医嘱号获取相应手术医嘱及就诊 Map<String, Object> map = new
		 * HashMap<String, Object>();
		 * 
		 * map.put("deleteFlag", Constants.NO_DELETE_FLAG);
		 * map.put("patientLid", patientLid); map.put("patientDomain",
		 * patientDomain);
		 * 
		 * String orderOrRequest = "";
		 * 
		 * for (String orderOrRequestNo : pocdin000040uv03.getOrderLids()) {
		 * orderOrRequest += orderOrRequestNo + "，";
		 * 
		 * // 门诊住院统一节点但不同意义，门诊：申请单号，住院：医嘱号 if
		 * (Constants.PATIENT_DOMAIN_OUTPATIENT.equals(patientDomain)) {
		 * map.put("requestNo", orderOrRequestNo); } else { map.put("orderLid",
		 * orderOrRequestNo); }
		 * 
		 * List<Object> procedureOrders = commonService.selectByCondition(
		 * ProcedureOrder.class, map);
		 * 
		 * // 判断报告在数据库中是否存在 if (procedureOrders != null &&
		 * !procedureOrders.isEmpty()) { ProcedureOrder procedureOrder =
		 * (ProcedureOrder) procedureOrders.get(0);
		 * 
		 * // 根据手术医嘱中visitSn获取相应就诊 Map<String, Object> mapVisit = new
		 * HashMap<String, Object>();
		 * 
		 * mapVisit.put("visitSn", procedureOrder.getVisitSn());
		 * 
		 * mapVisit.put("deleteFlag", Constants.NO_DELETE_FLAG);
		 * 
		 * List<Object> medicalVisits = commonService.selectByCondition(
		 * MedicalVisit.class, mapVisit);
		 * 
		 * if (medicalVisits != null && !medicalVisits.isEmpty()) { medicalVisit
		 * = (MedicalVisit) medicalVisits.get(0);
		 * 
		 * break; } } }
		 * 
		 * if (medicalVisit == null) {
		 * loggerBSDOCUMENT.error("Message:{},validate:{}",
		 * pocdin000040uv03.toString(),
		 * "麻醉或术前文档获取相应医嘱及就诊为空 : orderLidOrRequestNo = " + orderOrRequest +
		 * "；patientLid = " + patientLid + "；patientDomain = " + patientDomain);
		 * } } else {
		 */
		// 就诊次数
		String visitTimes = pocdin000040uv03.getVisitTimes();

		String visitTypeCode = pocdin000040uv03.getVisitTypeCode();

		if (Constants.INPATIENT_FRONTPAGE_SERVICE_ID.equals(pocdin000040uv03
				.getMessage().getServiceId())) {
			visitTypeCode = Constants.VISIT_TYPE_CODE_INPATIENT;
			// 根据域ID,患者本地ID，就诊次数获取就诊记录
			Map<String, Object> condition = new HashMap<String, Object>();
			condition.put("patientDomain", patientDomain);
			condition.put("patientLid", patientLid);
			//condition.put("orgCode", pocdin000040uv03.getOrganizationCode());
			condition.put("deleteFlag", Constants.NO_DELETE_FLAG);
			condition.put("visitTimes", visitTimes);
			condition.put("visitTypeCode", visitTypeCode);
			List visitResult = new ArrayList<MedicalVisit>();
			visitResult = commonService.selectByCondition(MedicalVisit.class,
					condition);
			if (visitResult != null && !visitResult.isEmpty()) {
				medicalVisit = (MedicalVisit) visitResult.get(0);
			}
		} else {
			// 根据域ID,患者本地ID，就诊流水号获取就诊记录
			medicalVisit = commonService.mediVisit(patientDomain, patientLid,
					visitTimes, pocdin000040uv03.getOrganizationCode(),
					pocdin000040uv03.getVisitOrdNo(), visitTypeCode);
		}

		return medicalVisit;
	}

	// [BUG]0038524 MODIFY END

	/**
	 * 获取最近审核的审核人信息
	 * 
	 * @param reviewPersons
	 * @return
	 */
	public ReviewPersonDto getLatestReviewPerson(
			List<ReviewPersonDto> reviewPersons) {
		ReviewPersonDto reviewPerson = new ReviewPersonDto();
		for (int i = 0; i < reviewPersons.size(); i++) {
			if (i == 0) {
				reviewPerson = reviewPersons.get(i);
			} else {
				Date date1 = DateUtils.stringToDate(reviewPerson
						.getReviewTime());
				Date date2 = DateUtils.stringToDate(reviewPersons.get(i)
						.getReviewTime());
				// $Author:wei_peng
				// $Date:2012/8/24 11:52
				// $[BUG]0009159 MODIFY BEGIN
				if (date1 != null && date2 != null && date2.after(date1)) {
					reviewPerson = reviewPersons.get(i);
				}
				// $[BUG]0009159 MODIFY END
			}
		}
		return reviewPerson;
	}

	/**
	 * CVIS EP手术操作记录报告判断手术医嘱是否存在
	 * 
	 * @param pocdin000040uv03
	 * @return
	 */
	private boolean checkProcedureOrder(POCDIN000040UV03 pocdin000040uv03,
			boolean flag, MedicalVisit medicalVisit) {
		// 门诊和住院分别用申请单号和医嘱号获取相应手术医嘱进行关联关系验证
		Map<String, Object> map = new HashMap<String, Object>();

		map.put("deleteFlag", Constants.NO_DELETE_FLAG);
		map.put("patientLid", patientLid);
		map.put("patientDomain", pocdin000040uv03.getPatientDomain());
		map.put("orgCode", pocdin000040uv03.getOrganizationCode());
		map.put("visitSn", medicalVisit.getVisitSn());

		boolean isExist = true;

		for (String orderOrRequestNo : pocdin000040uv03.getOrderLids()) {
			// 门诊住院统一节点但不同意义，门诊：申请单号，住院：医嘱号
			// if
			// (Constants.PATIENT_DOMAIN_OUTPATIENT.equals(pocdin000040uv03.getPatientDomain()))
			if (Constants.lstDomainOutPatient().contains(
					pocdin000040uv03.getPatientDomain())
					&& Constants.lstVisitTypeOutPatient().contains(
							pocdin000040uv03.getVisitTypeCode())) {
				map.put("requestNo", orderOrRequestNo);
			} else {
				map.put("orderLid", orderOrRequestNo);
			}

			List<Object> procedureOrders = commonService.selectByCondition(
					ProcedureOrder.class, map);

			// 判断报告在数据库中是否存在
			if (procedureOrders == null || procedureOrders.isEmpty()) {
				isExist = false;
				if (flag) {
					loggerBSDOCUMENT.error("Message:{},validate:{}",
							pocdin000040uv03.toString(), "住院医嘱号/门诊申请单号为 : "
									+ orderOrRequestNo + " 的手术医嘱/手术申请单不存在！");
				}
				break;
			}
		}
		return isExist;
	}

	/**
	 * 判断消息是否为门急诊病历消息
	 * 
	 * @param message
	 * @return 是返回true，否则返回false
	 */
	private boolean isOutpatientMessage(POCDIN000040UV03 message) {
		return Constants.OUTPATIENT_SERVICE_ID.equals(message.getServiceId());
	}

	/**
	 * 判断消息的交互类型是否为删除
	 * 
	 * @param message
	 * @return 是返回true，否则返回false
	 */
	private boolean isDeleteMessage(POCDIN000040UV03 message) {
		return Constants.VERSION_NUMBER_WITHDRAW.equals(message
				.getVersionNumber());
	}

	/**
	 * CVIS EP手术操作记录报告
	 * 
	 * @param serviceId
	 * @return
	 */
	private boolean isCVISEPReport(String serviceId) {
		return Constants.SOURCE_CVIS_EXAM.equals(serviceId);
	}

	// $Author :jin_peng
	// $Date : 2013/10/25 10:34$
	// [BUG]0038524 ADD BEGIN
	/**
	 * 是否麻醉，术前文档
	 * 
	 * @param serviceId
	 *            服务id
	 * @return 是否麻醉，术前文档标识
	 */
	/*
	 * private boolean isAnesthesiaPreoperation(String serviceId) { return
	 * Constants.SERVICE_ID_ANESTHESIA.equals(serviceId) ||
	 * Constants.SERVICE_ID_PREOPERATION.equals(serviceId); }
	 */

	// [BUG]0038524 ADD END

	// 获取病历文书对应的日志处理对象
	private Logger getDocumentLogger(String serviceId) {
		Logger result = null;
		Field field = null;
		try {
			field = POCDIN000040UV03Writer.class.getDeclaredField("logger"
					+ serviceId.toUpperCase());
			field.setAccessible(true);
			result = (Logger) field.get(this);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

	public List<Object> getDiagnosisMessage(POCDIN000040UV03 pocdin000040uv03) {
		boolean mainFlag = true;
		List<Object> diagnosisList = new ArrayList<Object>();
		List<com.founder.cdr.hl7.dto.Diagnosis> mainDiagnosisList = pocdin000040uv03
				.getDiagnosis();
		// 循环取出消息中的主诊断
		for (com.founder.cdr.hl7.dto.Diagnosis mainDiagnosis : mainDiagnosisList) {
			// 设置每个诊断共同的属性
			Diagnosis diagnosis = getNewDiagnosis();
			// 诊断类别代码
			diagnosis
					.setDiagnosisTypeCode(Constants.DOMAIN_DIAGNOSIS_TYPE_OUTPATIENT);
			// 诊断类别名称
			diagnosis.setDiagnosisTypeName(DictionaryCache.getDictionary(
					Constants.DOMAIN_DIAGNOSIS_TYPE).get(
					Constants.DOMAIN_DIAGNOSIS_TYPE_OUTPATIENT));

			// 疾病代码
			diagnosis.setDiseaseCode(mainDiagnosis.getDiseaseCode());
			// 疾病名称
			diagnosis.setDiseaseName(mainDiagnosis.getDiseaseName());
			// 诊断时间
			diagnosis.setDiagnosisDate(DateUtils.stringToDate(mainDiagnosis
					.getDiagnosisDate()));
			// 医疗机构代码
			diagnosis.setOrgCode(pocdin000040uv03.getOrganizationCode());

			// 医疗机构名称
			diagnosis.setOrgName(pocdin000040uv03.getOrganizationName());
			// [BUG]0040270 ADD END

			if (mainFlag) {
				// 主诊断标识
				diagnosis.setMainDiagnosisFlag(Constants.MAIN_DIAGNOSIS);
				mainFlag = false;
			} else
				// 次诊断标识
				diagnosis.setMainDiagnosisFlag(Constants.MINOR_DIAGNOSIS);

			diagnosisList.add(diagnosis);

		}
		return diagnosisList;
	}

	private Diagnosis getNewDiagnosis() {
		Diagnosis diagnosis = new Diagnosis();

		// 创建时间
		diagnosis.setCreateTime(systemTime);
		// 更新时间
		diagnosis.setUpdateTime(systemTime);
		// 更新回数
		diagnosis.setUpdateCount(Constants.INSERT_UPDATE_COUNT);
		// 删除标志
		diagnosis.setDeleteFlag(Constants.NO_DELETE_FLAG);
		// 就诊内部序列号
		diagnosis.setVisitSn(medicalVisit.getVisitSn());
		// 患者内部序列号
		diagnosis.setPatientSn(medicalVisit.getPatientSn());
		// 域代码
		diagnosis.setPatientDomain(medicalVisit.getPatientDomain());
		// 患者本地ID
		diagnosis.setPatientLid(medicalVisit.getPatientLid());
		// 文档id
		diagnosis.setDocumentSn(clinicalDocument.getDocumentSn());
		// 设置创建人
		diagnosis.setCreateby(serviceId);

		return diagnosis;
	}

}
