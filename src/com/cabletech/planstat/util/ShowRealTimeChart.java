 package com.cabletech.planstat.util;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;

import org.apache.commons.beanutils.BasicDynaBean;
import org.apache.log4j.Logger;

import java.awt.Color;




import org.jfree.chart.ChartFactory;

import org.jfree.chart.JFreeChart;

import org.jfree.chart.axis.AxisLocation;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.StackedBarRenderer;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;

import org.jfree.chart.ChartUtilities;

import com.cabletech.commons.base.BaseDispatchAction;
/*
import org.jfree.chart.labels.CategoryItemLabelGenerator;
import java.awt.Dimension;
import java.text.NumberFormat;
import javax.swing.JPanel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.labels.StandardCategoryToolTipGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;
*/

import java.awt.Font;
import java.io.*;

public class ShowRealTimeChart  extends HttpServlet{
	 private Logger logger = Logger.getLogger("ShowRealTimeChart") ;
	 
	 public void service( ServletRequest req, ServletResponse res ) throws
     ServletException, IOException{
		 List list = (List)((HttpServletRequest)req).getSession().getAttribute("realtimeinfo");
		 String conname = (String)((HttpServletRequest)req).getSession().getAttribute("mycontractorname");
		 res.setContentType( "image/jpeg" );
		 String title = null;
		 if(list != null && list.size()!=0){
			 title = conname + "实时短信发送情况显示图";
			 JFreeChart jfreechart = createChart(getDataSet(list),title);  
		     int width = 700;
	         int height = 490;
	         Font font = new Font( "黑体", Font.CENTER_BASELINE, 12 );
	         ChartUtilities.writeChartAsJPEG( res.getOutputStream(),1.0f, jfreechart, width, height, null );
		 }else{
			 logger.info("list is empty");
			 title = conname + "实时短信发送情况显示图";
			 JFreeChart jfreechart = createChart(getDataSet0(list),title);  
		     int width = 700;
	         int height = 490;
	         Font font = new Font( "黑体", Font.CENTER_BASELINE, 12 );
	         ChartUtilities.writeChartAsJPEG( res.getOutputStream(),1.0f, jfreechart, width, height, null );
			 //RequestDispatcher   requestDispather   =req.getRequestDispatcher("/planstat/queryVisitorsTraffic.jsp");   
			 //requestDispather.forward(req,res);   
		 }
	 }
	 private static DefaultCategoryDataset getDataSet(List list){
		 DefaultCategoryDataset mydata = new DefaultCategoryDataset();
		 if(list != null && list.size()!=0){
		    	for ( int i = 0;i<list.size();i++){
		    		mydata.addValue((Integer)((Map)list.get(i)).get("patrol"), "巡检", (String)((Map)list.get(i)).get("simnumber"));
		    		mydata.addValue((Integer)((Map)list.get(i)).get("watch"), "盯防", (String)((Map)list.get(i)).get("simnumber"));
		    		mydata.addValue((Integer)((Map)list.get(i)).get("collect"), "采集", (String)((Map)list.get(i)).get("simnumber"));
		    		mydata.addValue((Integer)((Map)list.get(i)).get("trouble"), "隐患", (String)((Map)list.get(i)).get("simnumber"));
		    		mydata.addValue((Integer)((Map)list.get(i)).get("other"), "其它", (String)((Map)list.get(i)).get("simnumber"));
		    		//mydata.addValue((Integer)((Map)list.get(i)).get("match"), "匹配", (String)((Map)list.get(i)).get("simnumber"));
		    		//mydata.addValue((Integer)((Map)list.get(i)).get("unmatch"), "未匹配", (String)((Map)list.get(i)).get("simnumber"));
		    		//mydata.addValue((Integer)((Map)list.get(i)).get("collect"), "采集", (String)((Map)list.get(i)).get("simnumber"));
		    		//mydata.addValue((Integer)((Map)list.get(i)).get("watchmatch"), "盯防", (String)((Map)list.get(i)).get("simnumber"));
		    		// + "\\n" + (String)((Map)list.get(i)).get("name")
		    	}
		 }
		 /*
		 mydata.addValue(10.0, "A", "Jan");
		 mydata.addValue(12.0, "A", "Feb");
		 mydata.addValue(13.0, "A", "Mar");
		 mydata.addValue(4.0, "B", "Jan");
		 mydata.addValue(3.0, "B", "Feb");
		 mydata.addValue(2.0, "B", "Mar");
		 mydata.addValue(2.0, "C", "Jan");
		 mydata.addValue(3.0, "C", "Feb");
		 mydata.addValue(2.0, "C", "Mar");
		 mydata.addValue(2.0, "D", "Jan");
		 mydata.addValue(3.0, "D", "Feb");
		 mydata.addValue(4.0, "D", "Mar");
		 */
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
		//stackedbarrenderer.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator());
		return jfreechart;
		    /*
		    JFreeChart jfreechart
		        = ChartFactory.createStackedBarChart("Stacked Bar Chart Demo 3",
							 "Category", "Value",
							 categorydataset,
							 PlotOrientation.VERTICAL,
							 true, false, false);
		    CategoryPlot categoryplot = (CategoryPlot) jfreechart.getPlot();
		    ExtendedStackedBarRenderer extendedstackedbarrenderer = new ExtendedStackedBarRenderer();
		    extendedstackedbarrenderer.setItemLabelsVisible(true);
		    //extendedstackedbarrenderer.setItemLabelGenerator(new StandardCategoryItemLabelGenerator());
		    extendedstackedbarrenderer.setToolTipGenerator(new StandardCategoryToolTipGenerator());
		    categoryplot.setRenderer(extendedstackedbarrenderer);
		    NumberAxis numberaxis = (NumberAxis) categoryplot.getRangeAxis();
		    numberaxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
		    numberaxis.setLowerMargin(0.15);
			numberaxis.setUpperMargin(0.15);
			numberaxis.setNumberFormatOverride(NumberFormat.getPercentInstance());
			return jfreechart;
			*/
	        
	    }
	 
	 private static DefaultCategoryDataset getDataSet0(List list){
		 DefaultCategoryDataset mydata = new DefaultCategoryDataset();
		 mydata.addValue(new Integer(0), "巡检", "0");
		 mydata.addValue(new Integer(0), "盯防", "0");
		 mydata.addValue(new Integer(0), "采集", "0");
		 mydata.addValue(new Integer(0), "隐患", "0");
		 mydata.addValue(new Integer(0), "其它", "0");
         return mydata;
		 
	 }

}
