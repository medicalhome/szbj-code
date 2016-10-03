package com.yly.cdr.hl7.dto;

import org.hibernate.validator.constraints.NotEmpty;

/**
 * 
 * @author tong_meng
 *
 */
public class ExamItem extends CodeValueDto
{
    /**
     * 版本号
     */
    private String versionNo;

    /**
     * 操作类型
     */
    @NotEmpty(message = "{ExamItem.newUpFlag}")
    private String newUpFlag;

    /**
     * 检查项目编码
     */
    @NotEmpty(message = "{ExamItem.code}")
    private String code;

    /**
     * 检查分类码
     */
//    @NotEmpty(message = "{ExamItem.typeCode}")
    private String typeCode;

    /**
     * 检查项目名称
     */
    private String name;

    /**
     * 拼音码
     */
    private String pyCode;

    /**
     * 报表头
     */
    private String reportHeader;

    /**
     * 报表尾
     */
    private String reportFooter;

    /**
     * 类别
     */
    private String classes;

    /**
     * 执行科室
     */
    private String execUnit;

    /**
     * 医嘱码
     */
    private String yzOrderCode;

    /**
     * 子类
     */
    private String subClass;

    /**
     * 检查部位
     */
    private String examRegion;

    /**
     * 空腹标志
     */
    private String kfFlag;

    /**
     * 打药标志
     */
    private String dyFlag;

    /**
     * 胃肠准备标志
     */
    private String wcjcFlag;

    /**
     * 开药标志
     */
    private String kyFlag;

    /**
     * 排序
     */
    private String sortNo;

    /**
     * 到诊时间
     */
    private String arriveTime;

    /**
     * 提前分钟
     */
    private String aheadExamTime;

    /**
     * 与预约中心对照码
     */
    private String compareCode2;

    /**
     * 特殊处理项目标志
     */
    private String groupSn;

    /**
     * 缺省模板号
     */
    private String defaultTemplate;

    /**
     * 备注
     */
    private String comment;

    /**
     * 门诊住院标志
     */
    private String mzZyFlag;

    public String getNewUpFlag()
    {
        return newUpFlag;
    }

    public String getCode()
    {
        return code;
    }

    public String getVersionNo()
    {
        return versionNo;
    }

    public String getTypeCode()
    {
        return typeCode;
    }

    public String getName()
    {
        return name;
    }

    public String getPyCode()
    {
        return pyCode;
    }

    public String getReportHeader()
    {
        return reportHeader;
    }

    public String getReportFooter()
    {
        return reportFooter;
    }

    public String getClasses()
    {
        return classes;
    }

    public String getExecUnit()
    {
        return execUnit;
    }

    public String getYzOrderCode()
    {
        return yzOrderCode;
    }

    public String getSubClass()
    {
        return subClass;
    }

    public String getExamRegion()
    {
        return examRegion;
    }

    public String getKfFlag()
    {
        return kfFlag;
    }

    public String getDyFlag()
    {
        return dyFlag;
    }

    public String getWcjcFlag()
    {
        return wcjcFlag;
    }

    public String getKyFlag()
    {
        return kyFlag;
    }

    public String getSortNo()
    {
        return sortNo;
    }

    public String getArriveTime()
    {
        return arriveTime;
    }

    public String getAheadExamTime()
    {
        return aheadExamTime;
    }

    public String getCompareCode2()
    {
        return compareCode2;
    }

    public String getGroupSn()
    {
        return groupSn;
    }

    public String getDefaultTemplate()
    {
        return defaultTemplate;
    }

    public String getComment()
    {
        return comment;
    }

    public String getMzZyFlag()
    {
        return mzZyFlag;
    }

    public String getUniqueId()
    {
        return code;
    }

    public String getOptType()
    {
        return newUpFlag;
    }
}
