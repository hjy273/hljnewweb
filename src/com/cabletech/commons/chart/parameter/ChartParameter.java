package com.cabletech.commons.chart.parameter;

import java.awt.Color;
import java.awt.Font;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.general.DefaultPieDataset;

/**
 * <p>
 * Title: ChartParameter
 * </p>
 * 
 * <p>
 * Description: 分析图的参数
 * </p>
 * 
 * <p>
 * Copyright: Copyright (c) 2008
 * </p>
 * 
 * <p>
 * Company: cabletech
 * </p>
 * 
 * @author yangjun
 * @version 2.0
 */
public class ChartParameter implements Serializable {
    public static final String BAR_CHART_TYPE = "Bar";

    public static final String BAR3D_CHART_TYPE = "Bar3D";

    public static final String LINE_CHART_TYPE = "Line";

    public static final String PIE_CHART_TYPE = "Pie";

    public static final String PIE3D_CHART_TYPE = "Pie3D";

    public static final String Y_MIN_RATE_VALUE = "0";
    
    public static final String Y_MAX_RATE_VALUE = "120";

    public static final Color[] REF_COLORS = new Color[] { Color.green, Color.blue, Color.red,
            Color.orange, Color.black, Color.yellow, Color.pink };

    // 分析图的标题
    private String title = "Title";

    // 二维分析图的横坐标标题
    private String xTitle = "X_Title";

    // 二维分析图的纵坐标标题
    private String yTitle = "Y_Title";

    // 一维分析图的最小值
    private String minValue = "0";

    // 一维分析图的最小值
    private String maxValue = "0";

    // 二维分析图的横坐标最小值
    private String xMinValue = "0";

    // 二维分析图的纵坐标最小值
    private String yMinValue = "0";

    // 二维分析图的横坐标最大值
    private String xMaxValue = "1";

    // 二维分析图的纵坐标最大值
    private String yMaxValue = "1";

    // 二维分析图的横坐标间隔值
    private String xSpaceValue = "1";

    // 二维分析图的纵坐标间隔值
    private String ySpaceValue = "1";

    // 二维分析图的横坐标间隔数量
    private String xSpaceNumber = "5";

    // 二维分析图的纵坐标间隔数量
    private String ySpaceNumber = "5";

    // 分析图的背景颜色
    private Color bgColor = Color.white;

    // 二维分析图的布局样式
    private PlotOrientation plotOrientation = PlotOrientation.VERTICAL;

    // 分析图的类型
    // Bar： 二维柱状图类型
    // Bar3D： 三维柱状图类型
    // Line： 折线图类型
    // Pie： 二维饼图类型
    // Pie3D： 三维饼图类型
    private String chartType = "Bar";

    // 分析图的数据点类型颜色列表
    private List chartColors = new ArrayList();

    // 分析图的数据点类型图例列表
    private List chartLabels = new ArrayList();

    // 二维分析图的数据信息（柱状图和折线图）
    private CategoryDataset dataSet;

    // 一维分析图的数据信息（饼图）
    private DefaultPieDataset pieDataSet;

    // 二维分析图的数据标注
    private boolean labelDisplayFlag = false;

    // 二维分析图的数据标注字体
    private Font labelDisplayFont = new Font("黑体", Font.PLAIN, 9);

    // 二维分析图的水平标准线数值
    private String standardValue = null;

    // 分析图标题字体
    private Font titleFont = new Font("宋体", Font.BOLD, 12);

    // 二维分析图的坐标轴数值字体
    private Font axisValueFont = new Font("宋体", Font.PLAIN, 9);

    // 二维分析图的坐标轴标注字体
    private Font axisLabelFont = new Font("宋体", Font.PLAIN, 10);

    // 分析图的图例字体
    private Font legendFont = new Font("宋体", Font.PLAIN, 10);

    // 一维分析图的标注字体
    private Font labelFont = new Font("宋体", Font.PLAIN, 8);

    public ChartParameter() {
        chartColors.add(Color.green);
        chartColors.add(Color.red);
        chartColors.add(Color.blue);
        chartLabels.add("green");
        chartLabels.add("red");
        chartLabels.add("blue");
    }

    public List getChartColors() {
        return chartColors;
    }

    public void setChartColors(List chartColors) {
        if (chartColors != null) {
            this.chartColors = chartColors;
        }
    }

    public String getChartType() {
        return chartType;
    }

    public void setChartType(String chartType) {
        if (chartType != null && chartType.trim().length() != 0) {
            this.chartType = chartType;
        }
    }

    public CategoryDataset getDataSet() {
        return dataSet;
    }

    public void setDataSet(CategoryDataset dataSet) {
        this.dataSet = dataSet;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        if (title != null && title.length() != 0) {
            this.title = title;
        }
    }

    public Object getXMaxValue() {
        return xMaxValue;
    }

    public void setXMaxValue(String maxValue) {
        xMaxValue = maxValue;
    }

    public String getXMinValue() {
        return xMinValue;
    }

    public void setXMinValue(String minValue) {
        xMinValue = minValue;
    }

    public String getXSpaceValue() {
        return xSpaceValue;
    }

    public void setXSpaceValue(String spaceValue) {
        xSpaceValue = spaceValue;
    }

    public String getXTitle() {
        return xTitle;
    }

    public void setXTitle(String title) {
        if (title != null && title.length() != 0) {
            xTitle = title;
        }
    }

    public String getYMaxValue() {
        return yMaxValue;
    }

    public void setYMaxValue(String maxValue) {
        yMaxValue = maxValue;
    }

    public String getYMinValue() {
        return yMinValue;
    }

    public void setYMinValue(String minValue) {
        yMinValue = minValue;
    }

    public String getYSpaceValue() {
        return ySpaceValue;
    }

    public void setYSpaceValue(String spaceValue) {
        ySpaceValue = spaceValue;
    }

    public String getYTitle() {
        return yTitle;
    }

    public void setYTitle(String title) {
        if (title != null && title.length() != 0) {
            yTitle = title;
        }
    }

    public String getXSpaceNumber() {
        return xSpaceNumber;
    }

    public void setXSpaceNumber(String spaceNumber) {
        xSpaceNumber = spaceNumber;
    }

    public String getYSpaceNumber() {
        return ySpaceNumber;
    }

    public void setYSpaceNumber(String spaceNumber) {
        ySpaceNumber = spaceNumber;
    }

    public Color getBgColor() {
        return bgColor;
    }

    public void setBgColor(Color bgColor) {
        this.bgColor = bgColor;
    }

    public PlotOrientation getPlotOrientation() {
        return plotOrientation;
    }

    public void setPlotOrientation(PlotOrientation plotOrientation) {
        this.plotOrientation = plotOrientation;
    }

    public DefaultPieDataset getPieDataSet() {
        return pieDataSet;
    }

    public void setPieDataSet(DefaultPieDataset pieDataSet) {
        this.pieDataSet = pieDataSet;
    }

    public boolean isLabelDisplayFlag() {
        return labelDisplayFlag;
    }

    public void setLabelDisplayFlag(boolean labelDisplayFlag) {
        this.labelDisplayFlag = labelDisplayFlag;
    }

    public Font getLabelDisplayFont() {
        return labelDisplayFont;
    }

    public void setLabelDisplayFont(Font labelDisplayFont) {
        this.labelDisplayFont = labelDisplayFont;
    }

    public String getMaxValue() {
        return maxValue;
    }

    public void setMaxValue(String maxValue) {
        this.maxValue = maxValue;
    }

    public String getMinValue() {
        return minValue;
    }

    public void setMinValue(String minValue) {
        this.minValue = minValue;
    }

    public List getChartLabels() {
        return chartLabels;
    }

    public String getStandardValue() {
        return standardValue;
    }

    public Font getAxisLabelFont() {
        return axisLabelFont;
    }

    public Font getAxisValueFont() {
        return axisValueFont;
    }

    public Font getLabelFont() {
        return labelFont;
    }

    public Font getLegendFont() {
        return legendFont;
    }

    public Font getTitleFont() {
        return titleFont;
    }

    public void setChartLabels(List chartLabels) {
        if (chartLabels != null) {
            this.chartLabels = chartLabels;
        }
    }

    public void setStandardValue(String standardValue) {
        this.standardValue = standardValue;
    }

    public void setAxisLabelFont(Font axisLabelFont) {
        this.axisLabelFont = axisLabelFont;
    }

    public void setAxisValueFont(Font axisValueFont) {
        this.axisValueFont = axisValueFont;
    }

    public void setLabelFont(Font labelFont) {
        this.labelFont = labelFont;
    }

    public void setLegendFont(Font legendFont) {
        this.legendFont = legendFont;
    }

    public void setTitleFont(Font titleFont) {
        this.titleFont = titleFont;
    }
}
