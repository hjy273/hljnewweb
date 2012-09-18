package com.cabletech.planstat.util;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;
import org.apache.commons.beanutils.BasicDynaBean;
import org.apache.log4j.Logger;
import java.awt.Color;
import java.awt.GradientPaint;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.AxisLocation;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.LayeredBarRenderer;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.util.SortOrder;
import org.jfree.chart.ChartUtilities;
import java.awt.Font;
import java.io.*;
import com.cabletech.planstat.beans.LogPathBean;

public class ShowSubTrafficChart  extends HttpServlet{
	 private Logger logger = Logger.getLogger("ShowSubTrafficChart") ;
	 public void service( ServletRequest req, ServletResponse res ) throws
     ServletException, IOException{
		 List list = (List)((HttpServletRequest)req).getSession().getAttribute("subTrafficList");
		 //String historydate = (String)((HttpServletRequest)req).getSession().getAttribute("mystatdate");
		 res.setContentType( "image/jpeg" );
		 String title = null;
		 if (list.size()==0){
			 logger.info("list is null");
		 }else{
			 //title =  historydate + "短信发送情况显示图";
			 JFreeChart jfreechart = null;
			 //LogPathBean logPathBean =(LogPathBean)((HttpServletRequest)req).getSession().getAttribute("theTrafficBean");
			 //ServletContext context = getServletContext();
			 //Map regionMap =(Map)context.getAttribute("regionMap");
		     //String regionname = (String) regionMap.get(logPathBean.getRegionID());
		     String mainMenuName = (String)((HttpServletRequest)req).getSession().getAttribute("mainMenuName");
			 //title =  regionname + logPathBean.getStartDate() +"至" + logPathBean.getEndDate() + mainMenuName + "下的二级模块访问情况显示图"; 
			 title =  mainMenuName + "下级模块访问情况显示图"; 
			 String selectedType = (String)((HttpServletRequest)req).getSession().getAttribute("SelectedType");
			 if (selectedType.equals("0")){
				 jfreechart = createChartConAndMobile(getDataSetConAndMobile(list),title);
			 }else{
		         jfreechart = createChart(getDataSet(list),title);
			 }
		     int width = 700;
	         int height = 490;
	         Font font = new Font( "黑体", Font.CENTER_BASELINE, 12 );
	         ChartUtilities.writeChartAsJPEG( res.getOutputStream(),1.0f, jfreechart, width, height, null );
		 }

	 }
	 private static DefaultCategoryDataset getDataSet(List list){
		 DefaultCategoryDataset mydata = new DefaultCategoryDataset();
		 if (!list.isEmpty() && list.size()>0){
		    	for ( int i = 0;i<list.size();i++){  
		    		mydata.addValue(Integer.parseInt(((BasicDynaBean)list.get(i)).get("visittimes").toString()), "显示结果", (String)((BasicDynaBean)list.get(i)).get("lablename"));
		    	}
		 }
     return mydata;
		 
	 }
	 
	 private static JFreeChart createChart(CategoryDataset categorydataset,String title) {
		 JFreeChart jfreechart
		    = ChartFactory.createStackedBarChart(title,
							 "分类：模块名称", "访问次数",
							 categorydataset,
							 PlotOrientation.HORIZONTAL,
							 true, true, false);
		jfreechart.setBackgroundPaint(Color.white);
		CategoryPlot categoryplot = (CategoryPlot) jfreechart.getPlot();
		categoryplot.setBackgroundPaint(Color.lightGray);
		categoryplot.setRangeGridlinePaint(Color.white);
		categoryplot.setRangeAxisLocation(AxisLocation.BOTTOM_OR_LEFT);
		BarRenderer barrenderer = (BarRenderer) categoryplot.getRenderer();
		barrenderer.setBaseItemLabelsVisible(true);
		barrenderer.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator());
		CategoryAxis categoryaxis = categoryplot.getDomainAxis();
		categoryaxis.setCategoryMargin(0.0);
		categoryaxis.setUpperMargin(0.02);
		categoryaxis.setLowerMargin(0.02);
		NumberAxis numberaxis = (NumberAxis) categoryplot.getRangeAxis();
		numberaxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
		numberaxis.setUpperMargin(0.1);
		//StackedBarRenderer stackedbarrenderer
		 //   = (StackedBarRenderer) categoryplot.getRenderer();
		//stackedbarrenderer.setDrawBarOutline(false);
		barrenderer.setMaximumBarWidth(0.1);
		//stackedbarrenderer.setItemLabelsVisible(true);
		return jfreechart;
	 }
	 
	 
		private  CategoryDataset getDataSetConAndMobile(List list){
			DefaultCategoryDataset mydataset= new DefaultCategoryDataset();
			 if (!list.isEmpty()){
			    String strmobile = "移动公司";
				String strcon = "代维和移动公司";
				double mvisittimes;
				double cvisittimes;
				String strmvisittimes;
				String strcvisittimes;
				for ( int i = 0;i<list.size();i++){
					strmvisittimes = (String)((Map)list.get(i)).get("mvisittimes");
					if (strmvisittimes == null){
						mvisittimes = 0.0d;
					}else{
						mvisittimes = Double.parseDouble(strmvisittimes);
					}
					strcvisittimes = (String)((Map)list.get(i)).get("cvisittimes");
					if (strcvisittimes == null){
						cvisittimes = 0.0d;
					}else{
						cvisittimes = Double.parseDouble(strcvisittimes);
					}
					double totaltimes = mvisittimes + cvisittimes;
					mydataset.addValue(mvisittimes, strmobile,(String)((Map)list.get(i)).get("lablename"));
					mydataset.addValue(totaltimes, strcon,(String)((Map)list.get(i)).get("lablename"));
				}
				return mydataset;
			 }
			 logger.info("list is null");
			 return mydataset;
		 }
		
		private static JFreeChart createChartConAndMobile(CategoryDataset categorydataset,String title) {
			JFreeChart jfreechart
			    = ChartFactory.createBarChart(title,
							  "模块名称", "访问次数", categorydataset,
							  PlotOrientation.HORIZONTAL, true,
							  true, false);
			jfreechart.setBackgroundPaint(Color.white);
			CategoryPlot categoryplot = (CategoryPlot) jfreechart.getPlot();
			categoryplot.setBackgroundPaint(Color.lightGray);
			categoryplot.setDomainGridlinePaint(Color.white);
			categoryplot.setDomainGridlinesVisible(true);
			categoryplot.setRangeGridlinePaint(Color.white);
			categoryplot.setRangeAxisLocation(AxisLocation.BOTTOM_OR_LEFT);
			NumberAxis numberaxis = (NumberAxis) categoryplot.getRangeAxis();
			numberaxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
			LayeredBarRenderer layeredbarrenderer = new LayeredBarRenderer();
			layeredbarrenderer.setDrawBarOutline(false);
			layeredbarrenderer.setSeriesBarWidth(0, 0.5);
			layeredbarrenderer.setSeriesBarWidth(1, 0.5);
			categoryplot.setRenderer(layeredbarrenderer);
			categoryplot.setRowRenderingOrder(SortOrder.DESCENDING);
			GradientPaint gradientpaint
			    = new GradientPaint(0.0F, 0.0F, Color.blue, 0.0F, 0.0F,
						new Color(0, 0, 64));
			GradientPaint gradientpaint_7_
			    = new GradientPaint(0.0F, 0.0F, Color.green, 0.0F, 0.0F,
						new Color(0, 64, 0));
			layeredbarrenderer.setSeriesPaint(0, gradientpaint);
			layeredbarrenderer.setSeriesPaint(1, gradientpaint_7_);
			return jfreechart;
		    }
}
