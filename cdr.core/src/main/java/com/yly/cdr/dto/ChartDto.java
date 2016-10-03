package com.yly.cdr.dto;

import java.util.ArrayList;
import java.util.List;

/**
 * [FUN]V05-101图形展示
 * @version 1.0, 2013/01/07  
 * @author 金鹏
 * @since 1.0
 */
public class ChartDto
{
    // 查询相应饼图展示内容总数项目标识
    public static final String DISPLAY_TOTAL_PIE = "drugTotal";

    // 查询相应饼图展示内容项目标识
    public static final String DISPLAY_CONTENT_PIE = "pieChart";

    // 查询相应饼图展示内容总数项目标识
    public static final String DISPLAY_CONTENT_TREND = "trendChart";

    // 相应饼图展示内容的总数
    private int displayPieTotal;

    // 相应饼图展示标注内容
    private String displayPieLabel;

    // 相应饼图每个块展示数量
    private int perPieCount;
    
    // 药物医嘱总剂量
    private String totalDosage;
    
    // 药物医嘱总剂量单位
    private String totalDosageUnit;
    
    // 药物医嘱次剂量
    private String dosage;
    
    // 药物医嘱次剂量单位
    private String dosageUnit;
    
    // 药物医嘱开嘱时间
    private String orderTime;

    /**
     * 分离构造饼图显示内容
     * @param PieChartList 饼图内容对象集合
     * @return 分离构造完成的饼图显示内容
     */
    public static List<String> getPieDisplayContent(List<ChartDto> PieChartList)
    {
        List<String> resList = new ArrayList<String>();

        if (PieChartList != null && !PieChartList.isEmpty())
        {
            for (ChartDto c : PieChartList)
            {
                // 分离出饼图显示内容
                resList.add("\"" + c.getDisplayPieLabel() + "-"
                    + c.getPerPieCount() + "\"");
            }
        }

        return resList;
    }

    public int getDisplayPieTotal()
    {
        return displayPieTotal;
    }

    public void setDisplayPieTotal(int displayPieTotal)
    {
        this.displayPieTotal = displayPieTotal;
    }

    public String getDisplayPieLabel()
    {
        return displayPieLabel;
    }

    public void setDisplayPieLabel(String displayPieLabel)
    {
        this.displayPieLabel = displayPieLabel;
    }

    public int getPerPieCount()
    {
        return perPieCount;
    }

    public void setPerPieCount(int perPieCount)
    {
        this.perPieCount = perPieCount;
    }

    public String getTotalDosage()
    {
        return totalDosage;
    }

    public void setTotalDosage(String totalDosage)
    {
        this.totalDosage = totalDosage;
    }

    public String getTotalDosageUnit()
    {
        return totalDosageUnit;
    }

    public void setTotalDosageUnit(String totalDosageUnit)
    {
        this.totalDosageUnit = totalDosageUnit;
    }

    public String getDosage()
    {
        return dosage;
    }

    public void setDosage(String dosage)
    {
        this.dosage = dosage;
    }

    public String getDosageUnit()
    {
        return dosageUnit;
    }

    public void setDosageUnit(String dosageUnit)
    {
        this.dosageUnit = dosageUnit;
    }

    public String getOrderTime()
    {
        return orderTime;
    }

    public void setOrderTime(String orderTime)
    {
        this.orderTime = orderTime;
    }

}
