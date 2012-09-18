package com.cabletech.planstat.util;


import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;

import org.apache.commons.beanutils.BasicDynaBean;
import org.apache.log4j.Logger;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.AxisLocation;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.StackedBarRenderer;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;

import org.jfree.chart.ChartUtilities;

import java.awt.Font;
import java.io.*;

public class ShowPmMonthWorkdaysChart  extends HttpServlet{
	 private static Logger logger = Logger.getLogger("ShowPmMonthWorkdaysChart") ;
	 public void service( ServletRequest req, ServletResponse res ) throws
     ServletException, IOException{
		 List list = (List)((HttpServletRequest)req).getSession().getAttribute("mWorkDaysInfoList");
		 String endyear = (String)((HttpServletRequest)req).getSession().getAttribute("endYearStat");
	     String endmonth = (String)((HttpServletRequest)req).getSession().getAttribute("endMonthStat");
	     String yearMonth = endyear + "年" + endmonth + "月";
	     String patrolname = (String)((HttpServletRequest)req).getSession().getAttribute("patrolname");
		 res.setContentType( "image/jpeg" );
		 String title = null;
		 if (list.size()==0){
			 logger.info("list is null");
		 }else{
			 title = patrolname + yearMonth + "任务执行情况显示图";
		     JFreeChart jfreechart = createChart(getDataSet(list),title);  
		     int width = 700;
	         int height = 490;
	         Font font = new Font( "黑体", Font.CENTER_BASELINE, 12 );
	         ChartUtilities.writeChartAsJPEG( res.getOutputStream(),1.0f, jfreechart, width, height, null );
		 }

	 }
	 private static DefaultCategoryDataset getDataSet(List list){
		 DefaultCategoryDataset mydata = new DefaultCategoryDataset();
		 if (!list.isEmpty() && list.size()>0){
			    logger.info("the size of list is:" + list.size());
		    	for ( int i = 0;i<list.size();i++){ 
		    		String simAndDate = (String)((BasicDynaBean)list.get(i)).get("operatedate") + "-" + (String)((BasicDynaBean)list.get(i)).get("simid");
		    		//mydata.addValue(Integer.parseInt(((BasicDynaBean)list.get(i)).get("matchnum").toString()), "匹配", simAndDate);
		    		//mydata.addValue(Integer.parseInt(((BasicDynaBean)list.get(i)).get("unmatchnum").toString()), "未匹配", simAndDate);
		    		//mydata.addValue(Integer.parseInt(((BasicDynaBean)list.get(i)).get("collectnum").toString()), "采集", simAndDate);
		    		//mydata.addValue(Integer.parseInt(((BasicDynaBean)list.get(i)).get("watchmatchnum").toString()), "盯防", (String)((BasicDynaBean)list.get(i)).get("simid"));
		    		mydata.addValue(Integer.parseInt(((BasicDynaBean)list.get(i)).get("patrolnum").toString()), "巡检", simAndDate);
		    		mydata.addValue(Integer.parseInt(((BasicDynaBean)list.get(i)).get("watchnum").toString()), "盯防", simAndDate);
		    		mydata.addValue(Integer.parseInt(((BasicDynaBean)list.get(i)).get("collectnum").toString()), "采集", simAndDate);
		    		mydata.addValue(Integer.parseInt(((BasicDynaBean)list.get(i)).get("troublenum").toString()), "隐患", simAndDate);
		    		mydata.addValue(Integer.parseInt(((BasicDynaBean)list.get(i)).get("othernum").toString()), "其它", simAndDate);

		    	}
		 }
		
     return mydata;
		 
	 }
	 
	 private static JFreeChart createChart(CategoryDataset categorydataset,String title) {
		 JFreeChart jfreechart
		    = ChartFactory.createStackedBarChart(title,
							 "分类：SIM卡号", "短信数",
							 categorydataset,
							 PlotOrientation.HORIZONTAL,
							 true, true, false);
		jfreechart.setBackgroundPaint(Color.white);
		CategoryPlot categoryplot = (CategoryPlot) jfreechart.getPlot();
		categoryplot.setBackgroundPaint(Color.lightGray);
		categoryplot.setRangeGridlinePaint(Color.white);
		categoryplot.setRangeAxisLocation(AxisLocation.BOTTOM_OR_LEFT);
		StackedBarRenderer stackedbarrenderer
		    = (StackedBarRenderer) categoryplot.getRenderer();
		stackedbarrenderer.setDrawBarOutline(false);
		stackedbarrenderer.setItemLabelsVisible(true);
		stackedbarrenderer.setMaximumBarWidth(0.05);
		return jfreechart;
	    }
}
