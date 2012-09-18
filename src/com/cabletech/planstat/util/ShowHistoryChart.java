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

public class ShowHistoryChart  extends HttpServlet{
	 private Logger logger = Logger.getLogger("ShowHistoryChart") ;
	 public void service( ServletRequest req, ServletResponse res ) throws
     ServletException, IOException{
		 List list = (List)((HttpServletRequest)req).getSession().getAttribute("historyperdayinfo");
		 String conname = (String)((HttpServletRequest)req).getSession().getAttribute("mycontractorname");
		 String historydate = (String)((HttpServletRequest)req).getSession().getAttribute("historydate");
		 
		 res.setContentType( "image/jpeg" );
		 String title = null;
		 if (list.size()==0){
			 logger.info("list is null");
		 }else{
			 title = conname + historydate + "短信发送情况显示图";
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
		    	for ( int i = 0;i<list.size();i++){  
		    		mydata.addValue(Integer.parseInt(((BasicDynaBean)list.get(i)).get("patrolnum").toString()), "巡检", (String)((BasicDynaBean)list.get(i)).get("simid"));
		    		mydata.addValue(Integer.parseInt(((BasicDynaBean)list.get(i)).get("watchnum").toString()), "盯防", (String)((BasicDynaBean)list.get(i)).get("simid"));
		    		mydata.addValue(Integer.parseInt(((BasicDynaBean)list.get(i)).get("collectnum").toString()), "采集", (String)((BasicDynaBean)list.get(i)).get("simid"));
		    		mydata.addValue(Integer.parseInt(((BasicDynaBean)list.get(i)).get("troublenum").toString()), "隐患", (String)((BasicDynaBean)list.get(i)).get("simid"));
		    		mydata.addValue(Integer.parseInt(((BasicDynaBean)list.get(i)).get("othernum").toString()), "其它", (String)((BasicDynaBean)list.get(i)).get("simid"));
		    		//mydata.addValue(Integer.parseInt(((BasicDynaBean)list.get(i)).get("watchmatchnum").toString()), "盯防", (String)((BasicDynaBean)list.get(i)).get("simid"));
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
		return jfreechart;
	    }
}
