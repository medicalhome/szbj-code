package com.yly.cdr.hl7.dto.ms036;

import java.util.List;

import org.hibernate.validator.constraints.NotEmpty;

import com.yly.cdr.hl7.dto.CodeOrderItemDto;
import com.yly.cdr.hl7.dto.CodeValueDto;
import com.yly.cdr.hl7.dto.MSBaseDto;

/**
 * 医嘱字典
 * @author yu_yzh
 * */
public class MS036 extends MSBaseDto<CodeOrderItemDto>{
    /**
     * 消息类别代码
     */
    @NotEmpty(message = "{MS036.msgId}")
    private String msgId;
    
    /**
     * 版本号
     */
//    @NotEmpty(message = "{MS036.version}")
    private String version;

    /**
     * 消息名称 
     */
    private String msgName;

    /**
     * 消息源系统编码
     */
    private String sourceSysCode;

    /**
     * 消息目标系统编码
     */
    private String targetSysCode;

    /**
     * 消息创建时间
     */
    private String createTime;
    
    /**
     * 医嘱
     */
    @NotEmpty(message = "{MS036.codeOrderItemDto}")
    private List<CodeOrderItemDto> codeOrderItemDto;

	public String getMsgId() {
		return msgId;
	}
	
	@Override
	public String getMessageId(){
		return msgId;
	}
	
	
	public List<CodeOrderItemDto> getMesageRecords(){
		return codeOrderItemDto;
	}
	
	public String getVersion() {
		return version;
	}

	public String getMsgName() {
		return msgName;
	}

	public String getSourceSysCode() {
		return sourceSysCode;
	}

	public String getTargetSysCode() {
		return targetSysCode;
	}

	public String getCreateTime() {
		return createTime;
	}

	public List<CodeOrderItemDto> getCodeOrderItemDto() {
		return codeOrderItemDto;
	}
	
	@Override
	public String toString(){
		return "MS036[" +
				"msgId: " + msgId +
				"; version: " + version +
				"; msgName: " + msgName + 
				"; sourceSysCode: " + sourceSysCode +
				"; targetSysCode: " + targetSysCode +
				"; createTime: " + createTime +
				"; codeOrderItemDto: " + codeOrderItemDto 
				+ "]";
	}
}
