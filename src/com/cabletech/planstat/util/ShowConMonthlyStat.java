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

import com.cabletech.planstat.beans.PatrolStatConditionBean;

public class ShowConMonthlyStat extends HttpServlet {
	private Logger logger = Logger.getLogger("ShowConMonthlyStat");
	static int intStartMonth = 0;
	static int intEndMonth = 0;

	public void service(ServletRequest req, ServletResponse res)
			throws ServletException, IOException {
		List list = (List) ((HttpServletRequest) req).getSession()
				.getAttribute("monthcontrast");
		PatrolStatConditionBean bean = (PatrolStatConditionBean) ((HttpServletRequest) req)
				.getSession().getAttribute("queryCon");
		intEndMonth = Integer.parseInt(bean.getEndMonth());
		
		// guixy modify by 2009-3-3 start
//		intStartMonth = intEndMonth - 2;
		if(intEndMonth > 2) {
			intStartMonth = intEndMonth - 2;
		} else {
			intStartMonth = intEndMonth + 12 - 2;
		}
		// guixy modify by 2009-3-3 end
		
		String title = bean.getConName() + "公司近三个月巡检分析";
		String name = null;
		name = "代维公司名称";
		res.setContentType("image/jpeg");
		JFreeChart jfreechart = null;
		if (!list.isEmpty()) {
			logger.info("list is not empty");
			jfreechart = createChart(getDataSet(list), title, name);
		} else {
			logger.info("list is empty");
		}
		int width = 577;
		int height = 395;
		Font font = new Font("黑体", Font.CENTER_BASELINE, 12);
		ChartUtilities.writeChartAsJPEG(res.getOutputStream(), 1.0f,
				jfreechart, width, height, null);
	}

	private CategoryDataset getDataSet(List list) {
		DefaultCategoryDataset defaultcategorydataset = new DefaultCategoryDataset();
		double value = 0;

		int SqlStartMonth = 0;
		if (!list.isEmpty()) {
			
//			for (int i = 0, month = intStartMonth; month <= intEndMonth; month++) {
//				if (i < list.size()) {
//					SqlStartMonth = Integer.parseInt(((BasicDynaBean) list
//							.get(i)).get("factdate").toString());
//				} else {
//					SqlStartMonth = 0;
//				}
//				if (SqlStartMonth == month) {
//					value = Double.parseDouble(((BasicDynaBean) list.get(i))
//							.get("examineresult").toString());
//					i++;
//					if (i >= list.size()) {
//						i--;
//					}
//				} else {
//					value = 0;
//
//				}
//				logger.info("" + value + "   " + month + "月" + SqlStartMonth);
//				defaultcategorydataset
//						.addValue(value, "考核结果(五分制)", month + "月");
//			}
			
			// guixy by 2009-3-3 start 
			int endMonthNum = intEndMonth;
			int month;
			// 顺充的月份数
			if(intStartMonth > intEndMonth) {
				endMonthNum = intEndMonth + 12;
			}
			for (int j = intStartMonth; j <= endMonthNum; j++) {				
				if(j > 12) {
					month = j - 12;
				} else {
					month = j;
				}
				value = 0;
				// 找到月份对就的值
				for (int i = 0; i < list.size(); i++) {
					SqlStartMonth = Integer.parseInt(((BasicDynaBean) list
							.get(i)).get("factdate").toString());
					
					if (SqlStartMonth == month) {
						value = Double.parseDouble(((BasicDynaBean) list.get(i))
								.get("examineresult").toString());
						break;
					} 
					
				}
				logger.info("" + value + "   " + month + "月" + SqlStartMonth);
				defaultcategorydataset
						.addValue(value, "考核结果(五分制)", month + "月");
			}
			// guixy by 2009-3-3 end 
			
			
			
		}
		return defaultcategorydataset;
	}

	private static JFreeChart createChart(CategoryDataset categorydataset,
			String title, String name) {

		JFreeChart jfreechart = ChartFactory.createBarChart3D(title, "月份",
				"等级", categorydataset, PlotOrientation.VERTICAL, false, true,
				false);
		CategoryPlot categoryplot = (CategoryPlot) jfreechart.getPlot();
		CustomBarRenderer3D custombarrenderer3d = new CustomBarRenderer3D();
		custombarrenderer3d
				.setItemLabelGenerator(new StandardCategoryItemLabelGenerator());
		custombarrenderer3d.setBaseItemLabelsVisible(true);
		custombarrenderer3d.setItemLabelAnchorOffset(10.0);
		custombarrenderer3d.setPositiveItemLabelPosition(new ItemLabelPosition(
				ItemLabelAnchor.OUTSIDE12, TextAnchor.BASELINE_LEFT));
		categoryplot.setRenderer(custombarrenderer3d);
		ValueMarker valuemarker = new ValueMarker(3.0,
				new Color(200, 200, 255), new BasicStroke(1.0F), new Color(200,
						200, 255), new BasicStroke(1.0F), 1.0F);
		categoryplot.addRangeMarker(valuemarker, Layer.BACKGROUND);
		custombarrenderer3d.setItemLabelsVisible(true);
		custombarrenderer3d.setMaximumBarWidth(0.05);
		CategoryTextAnnotation categorytextannotation = new CategoryTextAnnotation(
				"达标线", intEndMonth + "月", 3.0);
		categorytextannotation.setCategoryAnchor(CategoryAnchor.START);
		categorytextannotation.setFont(new Font("SansSerif", 0, 12));
		categorytextannotation.setTextAnchor(TextAnchor.BOTTOM_LEFT);
		categoryplot.addAnnotation(categorytextannotation);
		NumberAxis numberaxis = (NumberAxis) categoryplot.getRangeAxis();
		// numberaxis.setNumberFormatOverride(NumberFormat.getIntegerInstance());
		numberaxis.setRange(new Range(0, 5.5));
		numberaxis.setAutoTickUnitSelection(false);
		numberaxis.setUpperMargin(0.1);
		return jfreechart;
	}

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
}
