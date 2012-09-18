package com.cabletech.planstat.util;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Paint;
import java.io.IOException;
import java.text.NumberFormat;
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
import org.jfree.chart.annotations.CategoryTextAnnotation;
import org.jfree.chart.axis.AxisLocation;
import org.jfree.chart.axis.CategoryAnchor;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPosition;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.axis.CategoryLabelWidthType;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.labels.ItemLabelAnchor;
import org.jfree.chart.labels.ItemLabelPosition;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.ValueMarker;
import org.jfree.chart.renderer.category.BarRenderer3D;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.text.TextBlockAnchor;
import org.jfree.ui.Layer;
import org.jfree.ui.RectangleAnchor;
import org.jfree.ui.TextAnchor;

import com.cabletech.planstat.beans.PatrolStatConditionBean;


public class ShowRegionMonthlyStat extends HttpServlet{
	private Logger logger = Logger.getLogger("ShowRegionMonthlyStat") ;
	 static int intStartMonth = 0;
	 static int intEndMonth = 0;
	public void service( ServletRequest req, ServletResponse res ) throws
   ServletException, IOException{
		 List list = (List)((HttpServletRequest)req).getSession().getAttribute("concontrast");
		 PatrolStatConditionBean bean = (PatrolStatConditionBean)((HttpServletRequest)req).getSession().getAttribute("queryCon");
		 intEndMonth = Integer.parseInt(bean.getEndMonth());
		 intStartMonth = intEndMonth-2;
		 String title = "该月本地区代维公司巡检分析";
		 String name = null;
		 name = "代维公司名称";
		 res.setContentType( "image/jpeg" );
		 JFreeChart jfreechart = null;
		 if(!list.isEmpty()){
			 logger.info("list is not empty");
		     jfreechart = createChart(getDataSet(list),title,name);  
		 }else{
			 logger.info("list is empty");
		 }
		 int width = 577;
        int height = 395;
        Font font = new Font( "黑体", Font.CENTER_BASELINE, 12 );
        ChartUtilities.writeChartAsJPEG( res.getOutputStream(),1.0f, jfreechart, width, height, null );
	}
	
	private  CategoryDataset getDataSet(List list){
		DefaultCategoryDataset defaultcategorydataset = new DefaultCategoryDataset();
		for(int i=0;i<list.size();i++){
			BasicDynaBean dynabean = (BasicDynaBean)list.get(i);
			double value = Double.parseDouble(dynabean.get("examineresult").toString());
			double planpoint = Double.parseDouble(dynabean.get("planpoint").toString());
			double factpoint = Double.parseDouble(dynabean.get("factpoint").toString());
			String conName = (String)dynabean.get("contractorname");
			defaultcategorydataset.addValue(planpoint, "计划巡检点次", conName);
			defaultcategorydataset.addValue(factpoint, "实际巡检点次", conName);
			defaultcategorydataset.addValue(value, "考核结果(五分制)", conName);
			logger.info("defaultcategorydataset.addValue("+planpoint+", \"计划巡检点次\", "+conName+")");
			logger.info("defaultcategorydataset.addValue("+factpoint+", \"实际巡检点次\", "+conName+")");
			logger.info("defaultcategorydataset.addValue("+value+", \"考核结果(五分制)\", "+conName+")");
		}
		return defaultcategorydataset;
	 }
	
	private static JFreeChart createChart(CategoryDataset categorydataset,String title,String name) {
		
		JFreeChart jfreechart
	    = ChartFactory.createBarChart3D(title, "代维公司",
					    "", categorydataset,
					    PlotOrientation.VERTICAL, true,
					    true, false);
			CategoryPlot categoryplot = (CategoryPlot) jfreechart.getPlot();
			//显示每个柱的数值，并修改该数值的字体属性
			BarRenderer3D renderer = (BarRenderer3D) categoryplot.getRenderer();
			renderer.setItemLabelGenerator(new StandardCategoryItemLabelGenerator());
			renderer.setItemLabelsVisible(true);
			
			renderer.setPositiveItemLabelPosition(new ItemLabelPosition(ItemLabelAnchor.OUTSIDE1,
					   TextAnchor.BOTTOM_RIGHT));
			//设置柱状体上字体的偏移量
			renderer.setItemLabelAnchorOffset(10.0);
			categoryplot.setRenderer(renderer);
			
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

}
