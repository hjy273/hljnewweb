package com.cabletech.commons.chart.utils;

import java.awt.Color;
import java.awt.GradientPaint;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.AxisLocation;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.labels.StandardCategorySeriesLabelGenerator;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.BarRenderer3D;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;

import org.jfree.chart.plot.ValueMarker;
import java.awt.BasicStroke;
import org.jfree.chart.annotations.CategoryTextAnnotation;
import org.jfree.chart.axis.CategoryAnchor;
import java.awt.Font;
import org.jfree.ui.TextAnchor;
import org.jfree.ui.Layer;
import org.jfree.chart.labels.ItemLabelPosition;
import org.jfree.chart.title.TextTitle;
import org.jfree.chart.plot.PiePlot3D;

import com.cabletech.commons.chart.parameter.ChartParameter;

/**
 * <p>
 * Title: ChartUtils
 * </p>
 * 
 * <p>
 * Description: 分析图工具类
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
public class ChartUtils {
    private static double maxValue = 0;

    private static double minValue = 0;

    private static double pieMaxValue = 0;

    private static double pieMinValue = 0;

    public ChartUtils() {
    }

    /**
     * 根据分析图参数对象产生二维柱状分析图
     * 
     * @param chartParam
     *            ChartParameter 分析图参数对象
     * @return JFreeChart 返回产生的二维柱状分析图对象
     */
    public static JFreeChart generateBarChart(ChartParameter chartParam) {
        CategoryDataset dataSet = chartParam.getDataSet();

        JFreeChart chart = ChartFactory.createBarChart(chartParam.getTitle(), chartParam
                .getXTitle(), chartParam.getYTitle(), dataSet, chartParam.getPlotOrientation(),
                true, true, false);

        chart.getLegend().setItemFont(chartParam.getLegendFont());
        TextTitle title = chart.getTitle();
        title.setFont(chartParam.getTitleFont());
        chart.setTitle(title);
        chart.setBackgroundPaint(chartParam.getBgColor());

        CategoryPlot plot = (CategoryPlot) chart.getPlot();
        CategoryAxis axis = plot.getDomainAxis();
        axis.setLabelFont(chartParam.getAxisLabelFont());
        axis.setTickLabelFont(chartParam.getAxisValueFont());
        plot.setDomainAxis(axis);

        plot.setBackgroundPaint(chartParam.getBgColor());
        plot.setRangeGridlinePaint(chartParam.getBgColor());
        plot.setRangeAxisLocation(AxisLocation.BOTTOM_OR_LEFT);

        NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
        // 设置纵坐标的数值范围
        rangeAxis.setRange(Double.parseDouble(chartParam.getYMinValue()), Double
                .parseDouble(chartParam.getYMaxValue()));
        rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
        rangeAxis.setLabelFont(chartParam.getAxisLabelFont());
        rangeAxis.setTickLabelFont(chartParam.getAxisValueFont());

        BarRenderer renderer = (BarRenderer) plot.getRenderer();
        renderer.setDrawBarOutline(false);

        List colorList = chartParam.getChartColors();
        for (int i = 0; colorList != null && i < colorList.size(); i++) {
            Color color = (Color) colorList.get(i);
            renderer.setSeriesPaint(i, new GradientPaint(0.0f, 0.0f, color, 0.0f, 0.0f, color));
        }

        renderer.setLegendItemToolTipGenerator(new StandardCategorySeriesLabelGenerator(
                "Tooltip: {0}"));
        renderer.setItemLabelGenerator(new StandardCategoryItemLabelGenerator());
        renderer.setItemLabelFont(chartParam.getLabelDisplayFont());
        renderer.setItemLabelsVisible(chartParam.isLabelDisplayFlag());
        renderer.setPositiveItemLabelPosition(new ItemLabelPosition());
        renderer.setItemMargin(0);
        renderer.setMaximumBarWidth(0.1);

        if (chartParam.getStandardValue() != null) {
            ValueMarker marker = new ValueMarker(Integer.parseInt(chartParam.getStandardValue()),
                    new Color(200, 200, 255), new BasicStroke(1.0f), new Color(200, 200, 255),
                    new BasicStroke(1.0f), 1.0f);
            CategoryTextAnnotation a = new CategoryTextAnnotation("", dataSet.getColumnKey(0),
                    Integer.parseInt(chartParam.getStandardValue()));
            a.setCategoryAnchor(CategoryAnchor.START);
            a.setFont(new Font("SansSerif", 0, 12));
            a.setTextAnchor(TextAnchor.BOTTOM_LEFT);
            plot.addAnnotation(a);
            plot.addRangeMarker(marker, Layer.BACKGROUND);
        }

        return chart;
    }

    /**
     * 根据分析图参数对象产生二维3D柱状分析图
     * 
     * @param chartParam
     *            ChartParameter 分析图参数对象
     * @return JFreeChart 返回产生的二维3D柱状分析图对象
     */
    public static JFreeChart generateBar3DChart(ChartParameter chartParam) {
        CategoryDataset dataSet = chartParam.getDataSet();
        JFreeChart chart = ChartFactory.createBarChart3D(chartParam.getTitle(), chartParam
                .getXTitle(), chartParam.getYTitle(), dataSet, chartParam.getPlotOrientation(),
                true, true, false);

        chart.getLegend().setItemFont(chartParam.getLegendFont());
        TextTitle title = chart.getTitle();
        title.setFont(chartParam.getTitleFont());
        chart.setTitle(title);
        chart.setBackgroundPaint(chartParam.getBgColor());

        CategoryPlot plot = (CategoryPlot) chart.getPlot();
        CategoryAxis axis = plot.getDomainAxis();
        axis.setLabelFont(chartParam.getAxisLabelFont());
        axis.setTickLabelFont(chartParam.getAxisValueFont());
        plot.setDomainAxis(axis);

        plot.setBackgroundPaint(chartParam.getBgColor());
        plot.setRangeGridlinePaint(chartParam.getBgColor());
        plot.setRangeAxisLocation(AxisLocation.BOTTOM_OR_LEFT);

        NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
        // 设置纵坐标的数值范围
        rangeAxis.setRange(Double.parseDouble(chartParam.getYMinValue()), Double
                .parseDouble(chartParam.getYMaxValue()));
        rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
        rangeAxis.setLabelFont(chartParam.getAxisLabelFont());
        rangeAxis.setTickLabelFont(chartParam.getAxisValueFont());

        BarRenderer3D renderer = (BarRenderer3D) plot.getRenderer();
        renderer.setDrawBarOutline(false);

        List colorList = chartParam.getChartColors();

        for (int i = 0; colorList != null && i < colorList.size(); i++) {
            Color color = (Color) colorList.get(i);
            renderer.setSeriesPaint(i, new GradientPaint(0.0f, 0.0f, color, 0.0f, 0.0f, color));
        }

        renderer.setLegendItemToolTipGenerator(new StandardCategorySeriesLabelGenerator(
                "Tooltip: {0}"));
        renderer.setItemLabelGenerator(new StandardCategoryItemLabelGenerator());
        renderer.setItemLabelFont(chartParam.getLabelDisplayFont());
        renderer.setItemLabelsVisible(chartParam.isLabelDisplayFlag());
        renderer.setPositiveItemLabelPosition(new ItemLabelPosition());
        renderer.setItemMargin(0);
        renderer.setMaximumBarWidth(0.1);

        if (chartParam.getStandardValue() != null && dataSet.getColumnCount() != 0) {
            ValueMarker marker = new ValueMarker(Integer.parseInt(chartParam.getStandardValue()),
                    new Color(200, 200, 255), new BasicStroke(1.0f), new Color(200, 200, 255),
                    new BasicStroke(1.0f), 1.0f);
            CategoryTextAnnotation a = new CategoryTextAnnotation("", dataSet.getColumnKey(0),
                    Integer.parseInt(chartParam.getStandardValue()));
            a.setCategoryAnchor(CategoryAnchor.START);
            a.setFont(new Font("SansSerif", 0, 12));
            a.setTextAnchor(TextAnchor.BOTTOM_LEFT);
            plot.addAnnotation(a);
            plot.addRangeMarker(marker, Layer.BACKGROUND);
        }

        return chart;
    }

    /**
     * 根据分析图参数对象产生二维曲线分析图
     * 
     * @param chartParam
     *            ChartParameter 分析图参数对象
     * @return JFreeChart 返回产生的二维曲线分析图对象
     */
    public static JFreeChart generateLineChart(ChartParameter chartParam) {
        CategoryDataset dataSet = chartParam.getDataSet();

        JFreeChart chart = ChartFactory.createLineChart(chartParam.getTitle(), chartParam
                .getXTitle(), chartParam.getYTitle(), dataSet, chartParam.getPlotOrientation(),
                true, true, false);

        chart.getLegend().setItemFont(chartParam.getLegendFont());
        TextTitle title = chart.getTitle();
        title.setFont(chartParam.getTitleFont());
        chart.setTitle(title);
        chart.setBackgroundPaint(chartParam.getBgColor());

        CategoryPlot plot = (CategoryPlot) chart.getPlot();
        CategoryAxis axis = plot.getDomainAxis();
        axis.setLabelFont(chartParam.getAxisLabelFont());
        axis.setTickLabelFont(chartParam.getAxisValueFont());
        plot.setDomainAxis(axis);

        plot.setBackgroundPaint(chartParam.getBgColor());
        plot.setRangeGridlinePaint(chartParam.getBgColor());
        plot.setRangeAxisLocation(AxisLocation.BOTTOM_OR_LEFT);

        NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
        // 设置纵坐标的数值范围
        rangeAxis.setRange(Double.parseDouble(chartParam.getYMinValue()), Double
                .parseDouble(chartParam.getYMaxValue()));
        rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
        rangeAxis.setLabelFont(chartParam.getAxisLabelFont());
        rangeAxis.setTickLabelFont(chartParam.getAxisValueFont());

        LineAndShapeRenderer renderer = (LineAndShapeRenderer) plot.getRenderer();
        renderer.setShapesVisible(true);
        renderer.setDrawOutlines(true);
        renderer.setUseFillPaint(true);

        List colorList = chartParam.getChartColors();
        for (int i = 0; colorList != null && i < colorList.size(); i++) {
            Color color = (Color) colorList.get(i);
            renderer.setSeriesPaint(i, new GradientPaint(0.0f, 0.0f, color, 0.0f, 0.0f, color));
        }

        renderer.setLegendItemToolTipGenerator(new StandardCategorySeriesLabelGenerator(
                "Tooltip: {0}"));
        renderer.setItemLabelGenerator(new StandardCategoryItemLabelGenerator());
        renderer.setItemLabelFont(chartParam.getLabelDisplayFont());
        renderer.setItemLabelsVisible(chartParam.isLabelDisplayFlag());

        return chart;
    }

    /**
     * 根据分析图参数对象产生一维饼状分析图
     * 
     * @param chartParam
     *            ChartParameter 分析图参数对象
     * @return JFreeChart 返回产生的一维饼状分析图对象
     */
    public static JFreeChart generatePieChart(ChartParameter chartParam) {
        DefaultPieDataset dataSet = chartParam.getPieDataSet();

        JFreeChart chart = ChartFactory.createPieChart(chartParam.getTitle(), dataSet, true, true,
                false);

        chart.getLegend().setItemFont(chartParam.getLegendFont());
        TextTitle title = chart.getTitle();
        title.setFont(chartParam.getTitleFont());
        chart.setTitle(title);
        chart.setBackgroundPaint(chartParam.getBgColor());

        PiePlot plot = (PiePlot) chart.getPlot();
        plot.setBackgroundPaint(chartParam.getBgColor());
        plot.setForegroundAlpha(0.5f);

        List colorList = chartParam.getChartColors();
        for (int i = 0; colorList != null && i < colorList.size(); i++) {
            Color color = (Color) colorList.get(i);
            plot.setSectionPaint(i, color);
        }

        StandardPieSectionLabelGenerator labelGenerator = new StandardPieSectionLabelGenerator(
                "{0} {1} 占 {2}");
        plot.setLabelGenerator(labelGenerator);

        plot.setLabelFont(chartParam.getLabelFont());

        plot.setLegendLabelToolTipGenerator(new StandardPieSectionLabelGenerator(
                "Tooltip for legend item {0}"));

        return chart;
    }

    /**
     * 根据分析图参数对象产生一维饼状分析图
     * 
     * @param chartParam
     *            ChartParameter 分析图参数对象
     * @return JFreeChart 返回产生的一维饼状分析图对象
     */
    public static JFreeChart generatePie3DChart(ChartParameter chartParam) {
        DefaultPieDataset dataSet = chartParam.getPieDataSet();

        JFreeChart chart = ChartFactory.createPieChart3D(chartParam.getTitle(), dataSet, true,
                true, false);

        chart.getLegend().setItemFont(chartParam.getLegendFont());
        TextTitle title = chart.getTitle();
        title.setFont(chartParam.getTitleFont());
        chart.setTitle(title);
        chart.setBackgroundPaint(chartParam.getBgColor());

        PiePlot3D plot = (PiePlot3D) chart.getPlot();
        plot.setBackgroundPaint(chartParam.getBgColor());
        plot.setForegroundAlpha(0.5f);

        List colorList = chartParam.getChartColors();
        for (int i = 0; colorList != null && i < colorList.size(); i++) {
            Color color = (Color) colorList.get(i);
            plot.setSectionPaint(i, color);
        }

        StandardPieSectionLabelGenerator labelGenerator = new StandardPieSectionLabelGenerator(
                "{0} {1} 占 {2}");
        plot.setLabelGenerator(labelGenerator);

        plot.setLabelFont(chartParam.getLabelFont());

        plot.setLegendLabelToolTipGenerator(new StandardPieSectionLabelGenerator(
                "Tooltip for legend item {0}"));

        return chart;
    }

    /**
     * 根据数据集合产生二维分析图的数据集
     * 
     * @param dataSet
     *            List 二维分析图的数据集合 List<LinkedHashMap<key,value>>
     * @param chartParameter
     *            ChartParameter 分析图的参数集合
     * @return CategoryDataset 返回分析图的数据集
     */
    public static CategoryDataset createDataSet(List dataSet, ChartParameter chartParameter) {
        // TODO Auto-generated method stub
        minValue = 0;
        maxValue = 0;
        List labelList = chartParameter.getChartLabels();
        DefaultCategoryDataset chartDataSet = new DefaultCategoryDataset();

        if (dataSet != null) {
            double[][] data = new double[dataSet.size()][];
            for (int i = 0; i < dataSet.size(); i++) {
                LinkedHashMap valueMap = (LinkedHashMap) dataSet.get(i);
                if (valueMap != null) {
                    Set keySet = valueMap.keySet();
                    if (keySet != null) {
                        Iterator it = keySet.iterator();
                        data[i] = new double[valueMap.size()];
                        int j = 0;
                        while (it != null && it.hasNext()) {
                            String key = (String) it.next();
                            data[i][j] = Double.parseDouble(valueMap.get(key).toString());
                            chartDataSet.addValue(data[i][j], (String) labelList.get(i),
                                    (String) key);
                            if (data[i][j] > maxValue) {
                                maxValue = data[i][j];
                            }
                            if (data[i][j] < minValue) {
                                minValue = data[i][j];
                            }
                            j++;
                        }
                    }
                }
            }
            chartParameter.setYMinValue(minValue + "");
            chartParameter.setYMaxValue(maxValue + "");
        }

        return chartDataSet;
    }

    /**
     * 根据数据集合产生一维分析图的数据集
     * 
     * @param chartParameter
     *            ChartParamter 分析图参数对象
     * @param dataSet
     *            LinkedHashMap 一维分析图的数据集合 LinkedHashMap<key,value>
     * @return CategoryDataset 返回一维分析图的数据集
     */
    public static DefaultPieDataset createPieDataSet(LinkedHashMap dataSet,
            ChartParameter chartParameter) {
        // TODO Auto-generated method stub
        pieMinValue = 0;
        pieMaxValue = 0;
        DefaultPieDataset chartDataSet = new DefaultPieDataset();

        if (dataSet != null) {
            Set keySet = dataSet.keySet();
            if (keySet != null) {
                Iterator it = keySet.iterator();
                while (it != null && it.hasNext()) {
                    Object key = it.next();
                    double data = Double.parseDouble(dataSet.get(key).toString());
                    chartDataSet.setValue(key.toString(), data);
                    if (data > pieMaxValue) {
                        pieMaxValue = data;
                    }
                    if (data < pieMinValue) {
                        pieMinValue = data;
                    }
                }
            }
        }

        return chartDataSet;
    }

    /**
     * 获取二维数据集中的最大值
     * 
     * @return double 返回二维数据集中的最大值
     */
    public static double getMaxValue() {
        return maxValue;
    }

    /**
     * 获取二维数据集中的最小值
     * 
     * @return double 返回二维数据集中的最小值
     */
    public static double getMinValue() {
        return minValue;
    }

    /**
     * 获取一维数据集中的最大值
     * 
     * @return double 返回一维数据集中的最大值
     */
    public static double getPieMaxValue() {
        return pieMaxValue;
    }

    /**
     * 获取一维数据集中的最小值
     * 
     * @return double 返回一维数据集中的最小值
     */
    public static double getPieMinValue() {
        return pieMinValue;
    }
}
