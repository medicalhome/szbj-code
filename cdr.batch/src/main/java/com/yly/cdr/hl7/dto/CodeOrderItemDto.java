package com.yly.cdr.hl7.dto;

import org.hibernate.validator.constraints.NotEmpty;

/**
 * @author yu_yzh
 * */
public class CodeOrderItemDto extends CodeValueDto{
    /**
     * 版本号
     */
    private String versionNo;

	/**
     * 操作类型
     */
    @NotEmpty(message = "{CodeOrderItemDto.newUpFlag}")
    private String newUpFlag;

	/**
     * 医嘱编码
     * */
    @NotEmpty(message = "{CodeOrderItemDto.code}")
    private String code;

	/**
     * 医嘱名称
     * */
    @NotEmpty(message = "{CodeOrderItemDto.name}")
    private String name;

	/**
     * 拼音码
     * */
    private String pyCode;

	/**
     * 消息分类编码
     * */
    private String messageTypeCode;
    
	/**
     * 消息分类名称
     * */
    private String messageTypeName;

	/**
     * 显示序号
     * */
    private String seqNo;
    
    public String getSeqNo() {
		return seqNo;
	}

	public String getVersionNo() {
		return versionNo;
	}
    
    public String getMessageTypeName() {
		return messageTypeName;
	}

    public String getMessageTypeCode() {
		return messageTypeCode;
	}
    
    public String getPyCode() {
		return pyCode;
	}

    public String getName() {
		return name;
	}
    
    public String getCode() {
		return code;
	}
    
    public String getNewUpFlag() {
		return newUpFlag;
	}
    
    @Override
    public String getOptType(){
    	return newUpFlag;
    }
    
    @Override
    public String toString(){
    	return "CodeOrderItemDto["+
    			"versionNo: " + versionNo +
    			"; newUpFlag: " + newUpFlag +
    			"; code: " + code +
    			"; name: " + name +
    			"; pyCode: " + pyCode +
    			"; messageTypeCode: " + messageTypeCode +
    			"; messageTypeName: " + messageTypeName +
    			"; seqNo: " + seqNo
    			+ "]";
    }
}
