package com.cabletech.planstat.util;


import java.awt.Color;

import java.awt.Paint;



import org.jfree.chart.ChartFactory;

import org.jfree.chart.JFreeChart;

import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPosition;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.axis.CategoryLabelWidthType;



import org.jfree.chart.labels.ItemLabelAnchor;
import org.jfree.chart.labels.ItemLabelPosition;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;

import org.jfree.chart.renderer.category.BarRenderer3D;

import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.text.TextBlockAnchor;


import org.jfree.ui.RectangleAnchor;

import org.jfree.ui.TextAnchor;


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


import org.jfree.chart.ChartUtilities;




import com.cabletech.baseinfo.domainobjects.UserInfo;



public class ShowPmMonthlyCompHChart  extends HttpServlet{
    static class CustomBarRenderer3D extends BarRenderer3D
    {
	public CustomBarRenderer3D() {
	    /* empty */
	}
	
	public Paint getItemPaint(int i, int i_0_) {
	    CategoryDataset categorydataset = getPlot().getDataset();
	    double d = categorydataset.getValue(i, i_0_).doubleValue();
	    if (d >= 0.7)
		return Color.green;
	    return Color.red;
	}
    }
	private static Logger logger = Logger.getLogger("ShowPmMonthlyCompHChart") ;
    public void service( ServletRequest req, ServletResponse res ) throws
    ServletException, IOException{
    	List list = (List)((HttpServletRequest)req).getSession().getAttribute("monthlyStatPmHComp");
    	String endyear = (String)((HttpServletRequest)req).getSession().getAttribute("endYearStat");
    	String endmonth = (String)((HttpServletRequest)req).getSession().getAttribute("endMonthStat");
    	String yearmonth = endyear + "年" + endmonth + "月";
    	JFreeChart jfreechart = null;
    	String title = "";
    	res.setContentType( "image/jpeg" );
    	String PMType = ( String )((HttpServletRequest)req).getSession().getAttribute( "PMType" ); //获得巡检人员的管理方式
        String subtitleV = "";
    	if( PMType.equals( "group" ) ){ //按组管理
    		subtitleV ="巡检维护组";
    		title = yearmonth + "本代维公司下属所有巡检维护组巡检情况对比分析";
        }
        else{ //按人管理
        	subtitleV ="巡检员";
        	title = yearmonth + "本代维公司下属所有巡检员巡检情况对比分析";
        }
		if(!list.isEmpty()){
			logger.info("list is not empty");
		    jfreechart = createChart(createDataset(list),title,subtitleV);  
		}else{
			logger.info("list is empty");
		}
		 int width = 700;
         int height = 500;
         Font font = new Font( "黑体", Font.CENTER_BASELINE, 12 );
         ChartUtilities.writeChartAsJPEG( res.getOutputStream(),1.0f, jfreechart, width, height, null );
    }
        
    private static JFreeChart createChart(CategoryDataset categorydataset,String title,String subtitleV) {
    	JFreeChart jfreechart
    	    = ChartFactory.createBarChart3D(title, subtitleV,
    					    "", categorydataset,
    					    PlotOrientation.VERTICAL, true,
    					    true, false);
    	CategoryPlot categoryplot = (CategoryPlot) jfreechart.getPlot();
    	categoryplot.setForegroundAlpha(1.0F);

  
    	
//    	显示每个柱的数值，并修改该数值的字体属性
    	BarRenderer3D render = (BarRenderer3D)categoryplot.getRenderer();
    	render.setItemLabelGenerator(new StandardCategoryItemLabelGenerator());
    	render.setItemLabelsVisible(true);
    	render.setItemLabelAnchorOffset(10.0);
		categoryplot.getRenderer().setPositiveItemLabelPosition(new ItemLabelPosition(ItemLabelAnchor.OUTSIDE12,
					   TextAnchor.BOTTOM_LEFT));
		categoryplot.setRenderer(render);
		
    	CategoryAxis categoryaxis = categoryplot.getDomainAxis();
    	CategoryLabelPositions categorylabelpositions
    	    = categoryaxis.getCategoryLabelPositions();
    	CategoryLabelPosition categorylabelposition
    	    = new CategoryLabelPosition(RectangleAnchor.LEFT,
    					TextBlockAnchor.CENTER_LEFT,
    					TextAnchor.CENTER_LEFT, 0.0,
    					CategoryLabelWidthType.RANGE, 0.3F);
    	categoryaxis.setCategoryLabelPositions(CategoryLabelPositions
    						   .replaceLeftPosition
    					       (categorylabelpositions,
    						categorylabelposition));
    	

    	return jfreechart;
    }
    
    private static CategoryDataset createDataset(List list) {
    	DefaultCategoryDataset mydataset
    	    = new DefaultCategoryDataset();
    	if (!list.isEmpty()){
    		String strplan = "计划巡检点次";
		    String stractual = "实际巡检点次";
			String grade ="考核结果(五分制)";
			for ( int i = 0;i<list.size();i++){
				String patrolName = ((BasicDynaBean)list.get(i)).get("patrolname").toString();
				mydataset.addValue(Double.parseDouble(((BasicDynaBean)list.get(i)).get("planpoint").toString()), strplan,patrolName);	
				mydataset.addValue(Double.parseDouble(((BasicDynaBean)list.get(i)).get("factpoint").toString()), stractual,patrolName);
				mydataset.addValue(Double.parseDouble(((BasicDynaBean)list.get(i)).get("examineresult").toString()), grade,patrolName);	
			}
			return mydataset;
		 }
		 logger.info("list is null");
		 return mydataset;
    	/*
    	defaultcategorydataset.addValue(23.0, "Series 1", "London");
    	defaultcategorydataset.addValue(14.0, "Series 1", "New York");
    	defaultcategorydataset.addValue(14.0, "Series 1", "Istanbul");
    	defaultcategorydataset.addValue(14.0, "Series 1", "Cairo");
    	defaultcategorydataset.addValue(13.0, "Series 2", "London");
    	defaultcategorydataset.addValue(19.0, "Series 2", "New York");
    	defaultcategorydataset.addValue(19.0, "Series 2", "Istanbul");
    	defaultcategorydataset.addValue(19.0, "Series 2", "Cairo");
    	defaultcategorydataset.addValue(7.0, "Series 3", "London");
    	defaultcategorydataset.addValue(9.0, "Series 3", "New York");
    	defaultcategorydataset.addValue(9.0, "Series 3", "Istanbul");
    	defaultcategorydataset.addValue(9.0, "Series 3", "Cairo");
    	return defaultcategorydataset;
    	*/
    }  
        

}
