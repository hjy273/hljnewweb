package com.cabletech.analysis.util;

import java.awt.Color;
import java.awt.Font;
import java.awt.GradientPaint;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.labels.ItemLabelAnchor;
import org.jfree.chart.labels.ItemLabelPosition;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer3D;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.TextAnchor;
import org.apache.log4j.*;

import com.cabletech.analysis.beans.BarChartBean;

public class LayeredBarChart extends ApplicationFrame {
	private static final long serialVersionUID = 1L;
	private BarChartBean barChartBean = new BarChartBean();
	private static Logger logger = Logger.getLogger( LayeredBarChart.class.
            getName() );
	//modify by xueyh 20110414 control bar PlotOrientation VERTICAL 1;  HORIZONTAL ;0
	private int arrange_type=0;; 
	
	public LayeredBarChart() {
		super("");
	}

	public  BarChartBean getBarCahrtBean() {
		return barChartBean;
	}

	public  void setBarChartBean(BarChartBean barChartBean) {
		this.barChartBean = barChartBean;
	}

	private CategoryDataset createDataset() {
		return (CategoryDataset) traversalCategoryValues();
	}

   	/**
	 *  add by xueyh 20110505 Ϊ��ֵ ����
	 * @return
	 */
	private  DefaultCategoryDataset  traversalCategoryValues() {
		String category;
		DefaultCategoryDataset localDefaultCategoryDataset= new DefaultCategoryDataset();
		for ( List  categoryValue : barChartBean.getCategoryValues()) {
			String categoryName = categoryValue.get(0).toString();
			Map<String, Object> categoryMap = (HashMap) categoryValue.get(1);
			double value=0;
			for (Map<String, Object> categoryColor : barChartBean.getCategoryColors()) {
				category = categoryColor.get("category").toString();
				value = (Double) categoryMap.get(category);
				localDefaultCategoryDataset.addValue(value, category, categoryName);
			}
		}
		return localDefaultCategoryDataset;
	}
	/** add by xueyh 20110505 
	 * Ϊ����ÿ�����Ӹ���ɫֵ
	 * @param customBarRenderer
	 */
	private void setBarRenderColors(BarRenderer3D customBarRenderer){
		java.awt.Color categoryColor;
		int i=0;
		for (Map<String, Object> categoryColors : barChartBean.getCategoryColors()) {
			categoryColor =(java.awt.Color) categoryColors.get("color");
			customBarRenderer.setSeriesPaint(i,categoryColor);
			i++;
		}
	}

	public JFreeChart createChart() {
		CategoryDataset createDataset = createDataset();
		//JFreeChart localJFreeChart = ChartFactory.createBarChart(barChartBean.getTitle(), "", "", createDataset,PlotOrientation.VERTICAL, true, true, false);//PlotOrientation.HORIZONTAL
		JFreeChart localJFreeChart;
		if (this.arrange_type==0){
			localJFreeChart = ChartFactory.createBarChart(barChartBean.getTitle(), "", "", createDataset,PlotOrientation.HORIZONTAL, true, true, false);//
		}else {
			localJFreeChart = ChartFactory.createBarChart(barChartBean.getTitle(), "", "", createDataset,PlotOrientation.VERTICAL, true, true, false);//PlotOrientation.HORIZONTAL
		}
		// set the textTitle of the chart 
		TextTitle textTitle = localJFreeChart.getTitle(); 
		//���ñ�������� 
		String fontA = "����ϸ��"; 
		String fontB = "����"; 
		textTitle.setFont(new Font(fontA,Font.PLAIN,12)); 
		textTitle.setBackgroundPaint(new GradientPaint(0.0F, 0.0F, Color.WHITE, 
		250F, 0.0F, Color.white, true)); 
		textTitle.setExpandToFitSpace(true); 
		localJFreeChart.setBackgroundPaint(new GradientPaint(0.0F, 0.0F, Color.WHITE, 
		250F, 0.0F, Color.white, true)); 

		        CategoryPlot plot = (CategoryPlot) localJFreeChart.getCategoryPlot(); 
		        //BarRenderer3D customBarRenderer = (BarRenderer3D) plot.getRenderer(); 
		        //CustomBarRenderer3D customBarRenderer = new CustomBarRenderer3D(); 
		        BarRenderer3D customBarRenderer = new BarRenderer3D(); 
/*
		plot.setDomainGridlinePaint(Color.white); 
		plot.setDomainGridlinesVisible(true); 
		plot.setRangeGridlinePaint(Color.black); 
		plot.setBackgroundPaint(Color.WHITE); 
		// �����Ƿ��к��� 
		plot.setRangeGridlinesVisible(false); */
		NumberAxis numberaxis = (NumberAxis) plot.getRangeAxis(); 
		numberaxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits()); 
		//�������������Ƶ����� 
		numberaxis.setLabelFont(new Font(fontA,Font.PLAIN,16)); 
		//��������������ʾ���������� 
		numberaxis.setTickLabelFont(new Font("Fixedsys",Font.PLAIN,13)); 
		//���ú��������Ƶ����� 
		CategoryAxis categoryaxis = plot.getDomainAxis(); 
		categoryaxis.setLabelFont(new Font(fontA,Font.PLAIN,16)); 
		//���ú���������ʾ����ҵ����������� 
		categoryaxis.setTickLabelFont(new Font(fontA,Font.PLAIN,12)); 
		categoryaxis.setMaximumCategoryLabelLines(100); 
		categoryaxis.setMaximumCategoryLabelWidthRatio(100); 
		//������������б45�� 
		categoryaxis.setCategoryLabelPositions(CategoryLabelPositions.UP_45); 
		// �������� 
		numberaxis.setUpperMargin(0.14999999999999999D); 
		// ������ɫ 
		customBarRenderer.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator());//��ʾÿ��������ֵ 
		customBarRenderer.setBaseItemLabelsVisible(true); 
		//ע�⣺�˾�ܹؼ������޴˾䣬�����ֵ���ʾ�ᱻ���ǣ���������û����ʾ���������� 
		customBarRenderer.setBasePositiveItemLabelPosition(new ItemLabelPosition( 
		ItemLabelAnchor.OUTSIDE12, TextAnchor.BASELINE_CENTER)); 
		customBarRenderer.setItemLabelAnchorOffset(10D);// ��������ͼ�ϵ�����ƫ��ֵ 
		customBarRenderer.setItemLabelsVisible(true); 
		setBarRenderColors( customBarRenderer);
		//�趨�����������ɫ 
/*		customBarRenderer.setSeriesPaint(0, Color.decode("#24F4DB")); // ��series1 Bar 
		customBarRenderer.setSeriesPaint(1, Color.decode("#7979FF")); // ��series2 Bar 
		customBarRenderer.setSeriesPaint(2, Color.decode("#FF5555")); // ��series3 Bar 
		customBarRenderer.setSeriesPaint(3, Color.decode("#F8D661")); // ��series4 Bar 
		customBarRenderer.setSeriesPaint(4, Color.decode("#F284DC")); // ��series5 Bar 
		customBarRenderer.setSeriesPaint(5, Color.decode("#00BF00")); // ��series6 Bar 
		customBarRenderer.setSeriesOutlinePaint(0,Color.BLACK);//�߿�Ϊ��ɫ 
		customBarRenderer.setSeriesOutlinePaint(1,Color.BLACK);//�߿�Ϊ��ɫ 
		customBarRenderer.setSeriesOutlinePaint(2,Color.BLACK); //�߿�Ϊ��ɫ 
		customBarRenderer.setSeriesOutlinePaint(3,Color.BLACK);//�߿�Ϊ��ɫ 
		customBarRenderer.setSeriesOutlinePaint(4,Color.BLACK);//�߿�Ϊ��ɫ 
		customBarRenderer.setSeriesOutlinePaint(5,Color.BLACK); //�߿�Ϊ��ɫ 
		*/
		//�������ӵ������ 
		customBarRenderer.setMaximumBarWidth(0.04); 
		customBarRenderer.setItemMargin(0.000000005); 
		plot.setRenderer(customBarRenderer); 

		return localJFreeChart;
	}

	public void setArrangeType(int arrange_type) {
		this.arrange_type = arrange_type;
	}

	public int getArrangeType() {
		return arrange_type;
	}

}