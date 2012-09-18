	package com.cabletech.planstat.util;
	
	import java.awt.Color;
	import java.awt.Font;
	import java.io.IOException;
	import java.util.List;
	import javax.servlet.ServletException;
	import javax.servlet.ServletRequest;
	import javax.servlet.ServletResponse;
	import javax.servlet.http.HttpServlet;
	import javax.servlet.http.HttpServletRequest;
	import org.apache.commons.beanutils.BasicDynaBean;
	import org.apache.log4j.Logger;
	import org.jfree.chart.ChartFactory;
	import org.jfree.chart.ChartUtilities;
	import org.jfree.chart.JFreeChart;
	import org.jfree.chart.axis.NumberAxis;
	import org.jfree.chart.plot.CategoryPlot;
	import org.jfree.chart.plot.PlotOrientation;
	import org.jfree.chart.renderer.category.LineAndShapeRenderer;
	import org.jfree.data.category.CategoryDataset;
	import org.jfree.data.category.DefaultCategoryDataset;
	import org.jfree.util.ShapeUtilities;
	
	public class ShowPmCompVChart extends HttpServlet{
		private static Logger logger = Logger.getLogger("ShowPmCompVChart") ;
		 static List list = null;
		 static int intYear = 0;
		 static int intStartMonth = 0;
		 static int intEndMonth = 0;
		public void service( ServletRequest req, ServletResponse res ) throws
	    ServletException, IOException{
			 list = (List)((HttpServletRequest)req).getSession().getAttribute("pmCompVInfo");
			 String theYearMonth = (String)((HttpServletRequest)req).getSession().getAttribute("YMForPmComp");
			 intYear = Integer.parseInt(theYearMonth.substring(0,theYearMonth.indexOf("年")));
			 intStartMonth = Integer.parseInt(theYearMonth.substring(theYearMonth.indexOf("年")+1,theYearMonth.indexOf("月")));
			 intEndMonth = Integer.parseInt(theYearMonth.substring(theYearMonth.indexOf("-")+1,theYearMonth.length()-1));
			 logger.info("intYear:" + intYear +"--intStartMonth:" + intStartMonth + "--intEndMonth:" + intEndMonth);
			 String name = ((BasicDynaBean)list.get(0)).get("patrolname").toString();
			 logger.info("thename:" + name);
			 String title = name + theYearMonth + "纵向对比分析";
			 res.setContentType( "image/jpeg" );
			 JFreeChart jfreechart = null;
			 if(!list.isEmpty()){
				 logger.info("list is not empty");
			     jfreechart = createChart(createDataset(),title);  
			 }else{
				 logger.info("list is empty");
			 }
			 int width = 700;
	         int height = 500;
	         Font font = new Font( "黑体", Font.CENTER_BASELINE, 12 );
	         ChartUtilities.writeChartAsJPEG( res.getOutputStream(),1.0f, jfreechart, width, height, null );
		}
		
		
		 private static CategoryDataset createDataset() {
			DefaultCategoryDataset defaultcategorydataset
		    = new DefaultCategoryDataset();
			int thePlanPoint = 0;	
			int theFactPoint = 0;
			int SqlStartMonth = 0;
			logger.info("list'size:" + list.size());
			if (!list.isEmpty()){
				/*
				for ( int i = 0;i<list.size();i++){
					int thePlanPoint = Integer.parseInt(((BasicDynaBean)list.get(i)).get("planpoint").toString());	
					int theFactPoint = Integer.parseInt(((BasicDynaBean)list.get(i)).get("factpoint").toString());
					defaultcategorydataset.addValue(thePlanPoint, "计划巡检点次", String.valueOf(intStartMonth) + "月份");
					defaultcategorydataset.addValue(theFactPoint, "实际巡检点次", String.valueOf(intStartMonth)+ "月份");
					intStartMonth++;
				}
				*/
				for ( int i = 0,month = intStartMonth; month <= intEndMonth ;month++){
					if (i < list.size()){
					    SqlStartMonth = Integer.parseInt(((BasicDynaBean)list.get(i)).get("startmonth").toString());
					}else{
						SqlStartMonth=0;
					}
					if (SqlStartMonth == month ){
						thePlanPoint = Integer.parseInt(((BasicDynaBean)list.get(i)).get("planpoint").toString());	
						theFactPoint = Integer.parseInt(((BasicDynaBean)list.get(i)).get("factpoint").toString());
						i++;
						if (i>= list.size()){
							i--;
						}
					}else{
						thePlanPoint = 0;	
						theFactPoint = 0;
					}
					defaultcategorydataset.addValue(thePlanPoint, "计划巡检点次", String.valueOf(month) + "月份");
					defaultcategorydataset.addValue(theFactPoint, "实际巡检点次", String.valueOf(month)+ "月份");
				}
			}   
		    return defaultcategorydataset;
		}
		    
		 private static JFreeChart createChart(CategoryDataset categorydataset,String title) {
			JFreeChart jfreechart
		    = ChartFactory.createLineChart(title, "月份",
						   "巡检点次", categorydataset,
						   PlotOrientation.VERTICAL, true,
						   true, false);
			jfreechart.setBackgroundPaint(Color.white);
			CategoryPlot categoryplot = (CategoryPlot) jfreechart.getPlot();
			categoryplot.setBackgroundPaint(Color.lightGray);
			categoryplot.setRangeGridlinePaint(Color.white);
			NumberAxis numberaxis = (NumberAxis) categoryplot.getRangeAxis();
			numberaxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
			LineAndShapeRenderer lineandshaperenderer
			    = (LineAndShapeRenderer) categoryplot.getRenderer();
			lineandshaperenderer.setSeriesShapesVisible(0, true);
			lineandshaperenderer.setSeriesShapesVisible(1, true);
			lineandshaperenderer.setSeriesShapesVisible(2, true);
			lineandshaperenderer.setSeriesLinesVisible(2, true);
			lineandshaperenderer
			    .setSeriesShape(2, ShapeUtilities.createDiamond(4.0F));
			lineandshaperenderer.setDrawOutlines(true);
			lineandshaperenderer.setUseFillPaint(true);
			lineandshaperenderer.setFillPaint(Color.white);
			return jfreechart;
		}
	
	}
