package com.yly.cdr.dao;

import java.math.BigDecimal;
import java.util.List;

import com.founder.fasf.core.util.daohelper.annotation.Arguments;
import com.yly.cdr.dto.AuditLogDto;
import com.yly.cdr.dto.CommonDto;

public interface CommonDao
{
    /**
     * 
     * [FUN]V05-101共通
     * @version 1.0, 2012/04/09  
     * @author liujingyang
     * @since 1.0
     * 
     */
    @Arguments({ "sequenceName" })
    public BigDecimal getSequence(String sequenceName);

    @Arguments({ "visitSn", "orderSn" })
    public CommonDto getMaxNursingOperDate(BigDecimal visitSn,
            BigDecimal orderSn);

    // $Author :chang_xuewen
    // $Date : 2013/08/26 18:21$
    // [BUG]0036600 ADD BEGIN
    @Arguments({ "orderSn", "orderStatus", "operationDate" })
    public int selectOperation(BigDecimal orderSn, String orderStatus,
            String operationDate);

    // [BUG]0036600 ADD END

    // $Author:jin_peng
    // $Date:2013/09/23 15:50
    // [BUG]0037540 ADD BEGIN
    /**
     * 获取敏感信息审计相关内容
     * @param accountLogSn 用户登陆系统日志内部序列号
     * @param businessDataNo 业务数据标识号
     * @param operationTime 操作时间
     * @return 敏感信息审计相关内容
     */
    @Arguments({ "accountLogSn", "businessDataNo", "operationTime" })
    public List<AuditLogDto> selectAuditLog(BigDecimal accountLogSn,
            String businessDataNo, String operationTime);

    // [BUG]0037540 ADD END
}
