package com.founder.cdr.hl7.dto;

import java.util.List;

public class MorphologyReportItem extends BaseDto
{
    /**
     * 检验项编码
     */
    private String labItemCode;
    /**
     * 检验项名称
     */
    private String labItemName;
    /**
     * 危机编码
     */
    private String crisisCode;
    /**
     * 危机名称
     */
    private String crisisName;
    /**
     * 客观所见内容
     */
    private String phenomenonPerformance;
    /**
     * 诊断细分(诊断说明)
     */
    private String testResultsDetail;
    /**
     * 主观诊断内容
     */
    private String testResults;
    /**
     * 补充意见
     */
    private List<Comment> comment;
    /**
     * 检验结果项
     */
    private List<MorphologyReportResult> morphologyReportResult;

    public String getLabItemCode()
    {
        return labItemCode;
    }

    public String getLabItemName()
    {
        return labItemName;
    }

    public String getCrisisCode()
    {
        return crisisCode;
    }

    public String getCrisisName()
    {
        return crisisName;
    }

    public String getPhenomenonPerformance()
    {
        return phenomenonPerformance;
    }

    public String getTestResultsDetail()
    {
        return testResultsDetail;
    }

    public String getTestResults()
    {
        return testResults;
    }

    public List<Comment> getComment()
    {
        return comment;
    }

    public List<MorphologyReportResult> getMorphologyReportResult()
    {
        return morphologyReportResult;
    }
}
