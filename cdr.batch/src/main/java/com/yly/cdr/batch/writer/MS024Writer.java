package com.yly.cdr.batch.writer;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.yly.cdr.core.Constants;
import com.yly.cdr.entity.CodePerson;
import com.yly.cdr.hl7.dto.CodeValueDto;
import com.yly.cdr.hl7.dto.Person;
import com.yly.cdr.util.DateUtils;
import com.yly.cdr.util.StringUtils;

/**
 * 人员字典
 * @author tong_meng
 *
 */
@Component(value = "ms024Writer")
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class MS024Writer extends MSBaseWriter<Person>
{

    @Override
    public Object getNewCodeValue(CodeValueDto codeValue)
    {
        Person person = (Person) codeValue;
        CodePerson cp = new CodePerson();
        cp.setCsId(codeSystem.getCsId());
        cp.setCode(person.getCode());
        cp.setName(person.getName());
        cp.setPyCode(person.getPyCode());
        cp.setSexCode(person.getSexCode());
        cp.setEmploymentStatusCd(person.getEmploymentStatusCd());
//        cp.setDepartCd(StringUtils.strToBigDecimal(person.getDepartCd(), "pe"));
        cp.setDepartCd(person.getDepartCd());
        cp.setEmployeeTypeCd(person.getEmployeeTypeCd());
        cp.setPhoneNumber(StringUtils.strToBigDecimal(person.getPhoneNumber(),
                "pe"));
        cp.setSeqNo(person.getSeqNo());
        cp.setMailAddress(person.getMailAddress());

        // $Author :tong_meng
        // $Date : 2012/9/5 10:00$
        // [BUG]0009337 MODIFY BEGIN
        cp.setJobCategory(person.getJobCategory());
        // [BUG]0009337 MODIFY END

        // $Author:wei_peng
        // $Date:2012/11/27 17:26
        // [BUG]0011945 ADD BEGIN
        // 科室名称
        cp.setDepartName(person.getDepartName());
        // 出生日期
        if(person.getBirthday() != null){
        	String birthDay = parseDate(person.getBirthday());
            cp.setBirthday(birthDay == null ? person.getBirthday() : birthDay);
        }
        // 身份证号
        cp.setNationalIdentifier(person.getNationalIdentifier());
        // 财务科室编码
        cp.setFinancialDepartCd(person.getFinancialDepartCd());
        // 财务科室名称
        cp.setFinancialDepartName(person.getFinancialDepartName());
        // [BUG]0011945 ADD END

        cp.setServiceStartDate(DateUtils.stringToDate(person.getServiceStartDate()));
        cp.setVersionNo(person.getVersionNo());
        cp.setCreateTime(systemDate);
        cp.setUpdateCount(Constants.INSERT_UPDATE_COUNT);
        cp.setUpdateTime(systemDate);
        if (isDeleteMessage(person.getNewUpFlag()))
        {
            cp.setDeleteFlag(Constants.DELETE_FLAG);
            cp.setDeleteTime(systemDate);
        }
        else if (isNewMessage(person.getNewUpFlag())
            || isUpdateMessage(person.getNewUpFlag()))
        {
            cp.setDeleteFlag(Constants.NO_DELETE_FLAG);
            cp.setDeleteTime(null);
        }
        return cp;
    }

    @Override
    public Object getUpdateCodeValue(CodeValueDto codeValue)
    {
        Person person = (Person) codeValue;
        CodePerson cp = getCPList(person.getCode(), codeSystem.getCsId());
        cp.setName(person.getName());
        cp.setPyCode(person.getPyCode());
        cp.setSexCode(person.getSexCode());
        cp.setEmploymentStatusCd(person.getEmploymentStatusCd());
//        cp.setDepartCd(StringUtils.strToBigDecimal(person.getDepartCd(), "pe"));
        cp.setDepartCd(person.getDepartCd());
        cp.setEmployeeTypeCd(person.getEmployeeTypeCd());
        cp.setPhoneNumber(StringUtils.strToBigDecimal(person.getPhoneNumber(),
                "pe"));
        cp.setSeqNo(person.getSeqNo());
        cp.setMailAddress(person.getMailAddress());

        // $Author :tong_meng
        // $Date : 2012/9/5 10:00$
        // [BUG]0009337 MODIFY BEGIN
        cp.setJobCategory(person.getJobCategory());
        // [BUG]0009337 MODIFY END

        // $Author:wei_peng
        // $Date:2012/11/27 17:26
        // [BUG]0011945 ADD BEGIN
        // 科室名称
        cp.setDepartName(person.getDepartName());
        // 出生日期
        if(person.getBirthday() != null){
        	String birthDay = parseDate(person.getBirthday());
            cp.setBirthday(birthDay == null ? person.getBirthday() : birthDay);
        }
        // 身份证号
        cp.setNationalIdentifier(person.getNationalIdentifier());
        // 财务科室编码
        cp.setFinancialDepartCd(person.getFinancialDepartCd());
        // 财务科室名称
        cp.setFinancialDepartName(person.getFinancialDepartName());
        // [BUG]0011945 ADD END

        cp.setServiceStartDate(DateUtils.stringToDate(person.getServiceStartDate()));
        cp.setVersionNo(person.getVersionNo());

        // $Author :tong_meng
        // $Date : 2012/9/6 15:00$
        // [BUG]0009448 ADD BEGIN
        cp.setUpdateTime(systemDate);
        // [BUG]0009448 ADD END

        cp.setDeleteFlag(Constants.NO_DELETE_FLAG);
        cp.setDeleteTime(null);
        return cp;
    }

    @Override
    public Object getDeleteCodeValue(CodeValueDto codeValue)
    {
        Person person = (Person) codeValue;
        CodePerson cp = getCPList(person.getCode(), codeSystem.getCsId());
        cp.setVersionNo(person.getVersionNo());
        cp.setUpdateTime(systemDate);
        cp.setDeleteTime(systemDate);
        cp.setDeleteFlag(Constants.DELETE_FLAG);
        return cp;
    }

    private CodePerson getCPList(String code, BigDecimal csId)
    {
        return dictionaryService.getCPList(code, csId);
    }

    @Override
    public String getTableName()
    {
        return "CODE_PERSON";
    }
    
    private String parseDate(String date){
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyyMMddhhmmss");
		try {
			String time = sdf.format(sdf2.parse(date));
			return time;
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
    }
}
