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
import javax.servlet.http.HttpSession;

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
	
	public class ShowComCompVChart extends HttpServlet{
		private static Logger logger = Logger.getLogger("ShowComCompVChart") ;
		 static List list = null;
		 static int intYear = 0;
		 static int intStartMonth = 0;
		 static int intEndMonth = 0;
		 int width = 700;
         int height = 500;
		public void service( ServletRequest req, ServletResponse res ) throws
	    ServletException, IOException{
			HttpSession session =((HttpServletRequest)req).getSession();
			 list = (List)session.getAttribute("comCompVInfo");
			 String theYearMonth = (String)session.getAttribute("YMForComComp");
			 String title = "";
			 String endMonth = (String)session.getAttribute("endMonth");
			 String beginMonth = (String)session.getAttribute("beginMonth");
	
			 if(endMonth != null &&  beginMonth != null){
				 intYear = Integer.parseInt((String)session.getAttribute("endYear"));
				 intStartMonth = Integer.parseInt(beginMonth);
				 intEndMonth = Integer.parseInt(endMonth);
				 title = theYearMonth;
			 }else{
				 title = getTitleName(theYearMonth);
			 }
			 res.setContentType( "image/jpeg" );
			 JFreeChart jfreechart = null;
			 if (list.isEmpty()) {
					title="没有您需要的找到数据";
			}
			     jfreechart = createChart(createDataset(),title);  
			
	         Integer w = (Integer)session.getAttribute("width");
	 		Integer h = (Integer)session.getAttribute("height");
	         Font font = new Font( "黑体", Font.CENTER_BASELINE, 12 );
	         ChartUtilities.writeChartAsJPEG( res.getOutputStream(),1.0f, jfreechart, w == null ? width:w.intValue(), h == null ? height:h.intValue(), null );
		}
		private  String getTitleName(String theYearMonth){
			 intYear = Integer.parseInt(theYearMonth.substring(0,theYearMonth.indexOf("年")));
			 intStartMonth = Integer.parseInt(theYearMonth.substring(theYearMonth.indexOf("年")+1,theYearMonth.indexOf("月")));
			 intEndMonth = Integer.parseInt(theYearMonth.substring(theYearMonth.indexOf("-")+1,theYearMonth.length()-1));
			 logger.info("intYear:" + intYear +"--intStartMonth:" + intStartMonth + "--intEndMonth:" + intEndMonth);
			 String name = ((BasicDynaBean)list.get(0)).get("contractorname").toString();
			 logger.info("thename:" + name);
			 String title = name + theYearMonth + "纵向对比分析";
			 return title;
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
				System.out.println("intStartMonth :"+intStartMonth);
				for ( int i = 0,month = intStartMonth;  ;month++){
					System.out.println("month :"+month);
					if (i < list.size()){
					    SqlStartMonth = Integer.parseInt(((BasicDynaBean)list.get(i)).get("startmonth").toString());
					}else{
						SqlStartMonth=0;
					}
					if(month>12){
						month=1;
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
					double p =  (theFactPoint !=0?(thePlanPoint/theFactPoint):0)*10000;
					System.out.println("thePlanPoint/theFactPoint = "+p);
					//defaultcategorydataset.addValue(p, "巡检率(%)", String.valueOf(month)+ "月份");
					System.out.println("month1111111111111111111111111111  --- :"+month);
					if(month == intEndMonth){
						break;
					}
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
