package com.cabletech.planstat.util;

import java.awt.Color;
import java.awt.Font;
import java.awt.GradientPaint;
import java.io.IOException;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.AxisLocation;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.LayeredBarRenderer;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.util.SortOrder;


public class ShowPlanProgressChart   extends HttpServlet{
	private Logger logger = Logger.getLogger("ShowPlanProgressChart") ;
	public void service( ServletRequest req, ServletResponse res ) throws
    ServletException, IOException{
		 List list = (List)((HttpServletRequest)req).getSession().getAttribute("progressinfo");
		 String conname = (String)((HttpServletRequest)req).getSession().getAttribute("mycontractorname");
		 res.setContentType( "image/jpeg" );
		 JFreeChart jfreechart = null;
		 if(list.size()>0){
			 logger.info("list is not empty");
			 GregorianCalendar sysDate = new GregorianCalendar();   
			 String title = conname + "正在执行的巡检计划进度表" + "(截止" + sysDate.get(Calendar.YEAR) + "年" + String.valueOf(sysDate.get(Calendar.MONTH)+1) + "月" + sysDate.get(Calendar.DAY_OF_MONTH) + "日"  + "零时)";
		     jfreechart = createChart(getDataSet(list),title);  
		     int width = 700;
	         int height = 390;
	         Font font = new Font( "黑体", Font.CENTER_BASELINE, 12 );
	         ChartUtilities.writeChartAsJPEG( res.getOutputStream(),1.0f, jfreechart, width, height, null );
		 }else{
			 logger.info("list is empty");
		 }
	}
	
	private  CategoryDataset getDataSet(List list){
		DefaultCategoryDataset mydataset= new DefaultCategoryDataset();
		 if (!list.isEmpty()){
		    String stratual = "实际巡检点次";
			String strplan = "计划巡检点次";
			String strplanname = "计划名称";
			logger.info(strplanname);
			for ( int i = 0;i<list.size();i++){
				mydataset.addValue(Double.parseDouble((String)((Map)list.get(i)).get("actualpointtimes")), stratual,(String)((Map)list.get(i)).get("planname"));
				mydataset.addValue(Double.parseDouble((String)((Map)list.get(i)).get("planpointtimes")), strplan,(String)((Map)list.get(i)).get("planname"));
			}
			return mydataset;
		 }
		 logger.info("list is null");
		 return mydataset;
	 }
	
	private static JFreeChart createChart(CategoryDataset categorydataset,String title) {
		JFreeChart jfreechart
		    = ChartFactory.createBarChart(title,
						  "计划名称", "巡检点次", categorydataset,
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
		layeredbarrenderer.setSeriesBarWidth(0, 0.1);
		layeredbarrenderer.setSeriesBarWidth(1, 0.12);
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
