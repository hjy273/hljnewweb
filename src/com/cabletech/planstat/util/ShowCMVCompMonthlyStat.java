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
import org.jfree.chart.axis.CategoryAnchor;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.labels.ItemLabelAnchor;
import org.jfree.chart.labels.ItemLabelPosition;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.ValueMarker;
import org.jfree.chart.renderer.category.BarRenderer3D;
import org.jfree.data.Range;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.Layer;
import org.jfree.ui.TextAnchor;
import com.cabletech.planstat.beans.MonthlyStatCityMobileConBean;
import com.cabletech.planstat.util.ShowPmMonthlyCompVChart.CustomBarRenderer3D;

public class ShowCMVCompMonthlyStat extends HttpServlet {
	private static Logger logger = Logger.getLogger("ShowPmMonthlyCompVChart");

	static List list = null;

	static int intStartMonth = 0;

	static int intEndMonth = 0;
	
	static int month = 0;
	
	static int timesInForLoop = 1;
	
	static int intSecondMonth = 0;
	
	static int intThirdMonth = 0;
	
	static int MONTH_SPACE = 2;
	
	static int MONTHS_OF_YEAR = 12;

	static class CustomBarRenderer3D extends BarRenderer3D {
		public CustomBarRenderer3D() {
			/* empty */
		}

		public Paint getItemPaint(int i, int i_0_) {
			CategoryDataset categorydataset = getPlot().getDataset();
			double d = categorydataset.getValue(i, i_0_).doubleValue();
			if (d >= 3.0)
				return Color.green;
			return Color.red;
		}
	}

	public void service(ServletRequest req, ServletResponse res)
			throws ServletException, IOException {
		list = (List) ((HttpServletRequest) req).getSession().getAttribute(
				"cmmonthlystatVcomp");
		MonthlyStatCityMobileConBean conBean = (MonthlyStatCityMobileConBean) ((HttpServletRequest) req)
				.getSession().getAttribute("CMMonthlyStatConBean");
		intEndMonth = Integer.parseInt(conBean.getEndMonth());
		intStartMonth = intEndMonth - MONTH_SPACE;
		if(intStartMonth <=0){
			intStartMonth = MONTHS_OF_YEAR-(MONTH_SPACE-intEndMonth);
		}		
		JFreeChart jfreechart = null;
		String title = null;
		res.setContentType("image/jpeg");
		if (!list.isEmpty()) {
			logger.info("list is not empty");
			jfreechart = createChart(createDataset(), title);
		} else {
			logger.info("list is empty");
		}
		int width = 700;
		int height = 500;
		Font font = new Font("黑体", Font.CENTER_BASELINE, 12);
		ChartUtilities.writeChartAsJPEG(res.getOutputStream(), 1.0f,
				jfreechart, width, height, null);
	}

	private static JFreeChart createChart(CategoryDataset categorydataset,
			String title) {
		JFreeChart jfreechart = ChartFactory.createBarChart3D(title, "月份",
				"考核等级", categorydataset, PlotOrientation.VERTICAL, false, true,
				false);
		CategoryPlot categoryplot = (CategoryPlot) jfreechart.getPlot();
		CustomBarRenderer3D custombarrenderer3d = new CustomBarRenderer3D();
		custombarrenderer3d
				.setItemLabelGenerator(new StandardCategoryItemLabelGenerator());
		custombarrenderer3d.setBaseItemLabelsVisible(true);
		custombarrenderer3d.setItemLabelAnchorOffset(10.0);
		custombarrenderer3d.setPositiveItemLabelPosition(new ItemLabelPosition(
				ItemLabelAnchor.OUTSIDE12, TextAnchor.BOTTOM_RIGHT));
		categoryplot.setRenderer(custombarrenderer3d);
		ValueMarker valuemarker = new ValueMarker(3.0,
				new Color(200, 200, 255), new BasicStroke(1.0F), new Color(200,
						200, 255), new BasicStroke(1.0F), 1.0F);
		categoryplot.addRangeMarker(valuemarker, Layer.BACKGROUND);
		custombarrenderer3d.setItemLabelsVisible(true);
		custombarrenderer3d.setMaximumBarWidth(0.05);
		CategoryTextAnnotation categorytextannotation = new CategoryTextAnnotation(
				"达标线", intEndMonth + "月份", 3.0);
		categorytextannotation.setCategoryAnchor(CategoryAnchor.START);
		categorytextannotation.setFont(new Font("SansSerif", 0, 12));
		categorytextannotation.setTextAnchor(TextAnchor.BOTTOM_LEFT);
		categoryplot.addAnnotation(categorytextannotation);
		NumberAxis numberaxis = (NumberAxis) categoryplot.getRangeAxis();
		numberaxis.setNumberFormatOverride(NumberFormat.getInstance());
		numberaxis.setAutoTickUnitSelection(false);
		numberaxis.setRange(new Range(0, 5.5));
		// categoryplot.setRangeAxisLocation(AxisLocation.BOTTOM_OR_LEFT);//
		// numberaxis.setUpperMargin(0.1);

		return jfreechart;
	}

	private static CategoryDataset createDataset() {
		DefaultCategoryDataset defaultcategorydataset = new DefaultCategoryDataset();
		int SqlStartMonth = 0;
		double theGrade = 0;
        logger.info("intStartMonth:" + intStartMonth);
		if (!list.isEmpty()) {
			for ( int i = 0,month = intStartMonth;  ;month++){
				if (i < list.size()){
				    SqlStartMonth = Integer.parseInt(((BasicDynaBean)list.get(i)).get("startmonth").toString());
				}else{
					SqlStartMonth=0;
				}
				if(month > MONTHS_OF_YEAR){
					month=1;
				}
				if (SqlStartMonth == month ){
					theGrade = Integer.parseInt(((BasicDynaBean)list.get(i)).get("examineresult").toString());	
					i++;
					if (i>= list.size()){
						i--;
					}
				}else{
					theGrade = 0;
				}
				defaultcategorydataset.addValue(theGrade, "考核等级", String
						.valueOf(month)
						+ "月份");
				logger.info("sqlStartMonth is:" + SqlStartMonth
						+ ",and month is:" + month + ",and theGrade is:"
						+ theGrade);
				if(month == intEndMonth){ 
					break;
				}
			}
		}
		return defaultcategorydataset;
	}
}
