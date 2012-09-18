package com.cabletech.analysis.util;

import java.awt.Color;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartRenderingInfo;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.AxisLocation;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.entity.StandardEntityCollection;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.Plot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.StackedBarRenderer;
import org.jfree.chart.servlet.ServletUtilities;
import org.jfree.data.category.DefaultCategoryDataset;

import com.cabletech.baseinfo.domainobjects.UserInfo;
import com.cabletech.commons.config.UserType;
/**
 * 
 * 生成实时短信图例
 *
 */
public class RealTimeNoteChart {
	/**
	 * 生成短信的bar chart
	 * @param session HttpSession
	 * @param pw PrintWriter
	 * @return 返回图片名称
	 */
	public static String generateBarChart(HttpSession session, PrintWriter pw) {
		String filename = null;
		UserInfo user = (UserInfo) session.getAttribute("LOGIN_USER");
		try {
			// Retrieve list of WebHits
			ArrayList list = (ArrayList) session.getAttribute("noteNum");

			// Throw a custom NoDataException if there is no data
			if (list == null) {
				System.out.println("No data has been found");
				throw new NoDataException();
			}

			// Create and populate a CategoryDataset
			DefaultCategoryDataset dataset = new RealTimeNoteChart().dateSet(
					list, user.getType());
			// Iterator iter = list.listIterator();

			// Create the chart object
			CategoryAxis categoryAxis = new CategoryAxis("");
			ValueAxis valueAxis = new NumberAxis("");
			BarRenderer renderer = new BarRenderer();
			// renderer.setItemURLGenerator(new
			// StandardCategoryURLGenerator("xy_chart.jsp", "series",
			// "section"));
			// renderer.setToolTipGenerator(new
			// StandardCategoryToolTipGenerator());

			renderer.setDrawBarOutline(false);
			renderer.setItemLabelsVisible(true);
			Plot plot = new CategoryPlot(dataset, categoryAxis, valueAxis,
					renderer);

			JFreeChart chart = new JFreeChart("",
					JFreeChart.DEFAULT_TITLE_FONT, plot, false);
			chart.setBackgroundPaint(java.awt.Color.white);
			// ///////////////////////////////
			JFreeChart jfreechart = ChartFactory
					.createStackedBarChart("", "", "任务执行数", dataset,
							PlotOrientation.HORIZONTAL, true, true, false);
			jfreechart.setBackgroundPaint(Color.white);
			CategoryPlot categoryplot = (CategoryPlot) jfreechart.getPlot();
			categoryplot.setBackgroundPaint(Color.lightGray);
			categoryplot.setRangeGridlinePaint(Color.white);
			categoryplot.setRangeAxisLocation(AxisLocation.BOTTOM_OR_LEFT);
			StackedBarRenderer stackedbarrenderer = (StackedBarRenderer) categoryplot
					.getRenderer();
			stackedbarrenderer.setDrawBarOutline(false);
			stackedbarrenderer.setItemLabelsVisible(true);
			stackedbarrenderer.setMaximumBarWidth(0.1);

			// //////////////
			// Write the chart image to the temporary directory
			ChartRenderingInfo info = new ChartRenderingInfo(
					new StandardEntityCollection());
			filename = ServletUtilities.saveChartAsPNG(jfreechart,
					ChartSize.WIDTH, ChartSize.HEIGHT, info, session);

			// Write the image map to the PrintWriter
			ChartUtilities.writeImageMap(pw, filename, info, true);
			pw.flush();

			// } catch (NoDataException e) {
			// System.out.println(e.toString());
			// filename = "public_nodata_500x300.png";
		} catch (Exception e) {
			System.out.println("Exception - " + e.toString());
			e.printStackTrace(System.out);
			filename = "public_error_500x300.png";
		}
		return filename;
	}
	/**
	 * 将短信数据信息封装到DefaultCategoryDataset对象中。
	 * @param list 短信数据信息
	 * @param type 用户类型 
	 * @return dateSet 返回以封装的信息。
	 */
	public DefaultCategoryDataset dateSet(List list, String type) {
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		String titleName = "titlename";
		if (UserType.CONTRACTOR.equals(type)) {
			titleName = "simid";
		}
		if (!list.isEmpty() && list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				dataset.addValue(Integer.parseInt(((Map) list.get(i)).get(
						"patrol").toString()), "巡检", (String) ((Map) list
						.get(i)).get(titleName));
				dataset.addValue(Integer.parseInt(((Map) list.get(i)).get(
						"watch").toString()), "盯防",
						(String) ((Map) list.get(i)).get(titleName));
				dataset.addValue(Integer.parseInt(((Map) list.get(i)).get(
						"collect").toString()), "采集", (String) ((Map) list
						.get(i)).get(titleName));
				dataset.addValue(Integer.parseInt(((Map) list.get(i)).get(
						"trouble").toString()), "隐患", (String) ((Map) list
						.get(i)).get(titleName));
				dataset.addValue(Integer.parseInt(((Map) list.get(i)).get(
						"other").toString()), "其它",
						(String) ((Map) list.get(i)).get(titleName));
			}
		}
		// dataset.addValue(14, "巡检", "广州");
		// dataset.addValue(3, "盯防", "广州");
		// dataset.addValue(2, "采集", "广州");
		// dataset.addValue(4, "隐患", "广州");
		// dataset.addValue(1, "其它", "广州");
		//
		// dataset.addValue(23, "巡检", "惠州");
		// dataset.addValue(1, "盯防", "惠州");
		// dataset.addValue(0, "采集", "惠州");
		// dataset.addValue(6, "隐患", "惠州");
		// dataset.addValue(1, "其它", "惠州");
		//		
		// dataset.addValue(14, "巡检", "湛江");
		// dataset.addValue(3, "盯防", "湛江");
		// dataset.addValue(2, "采集", "湛江");
		// dataset.addValue(4, "隐患", "湛江");
		// dataset.addValue(1, "其它", "湛江");
		//
		// dataset.addValue(23, "巡检", "佛山");
		// dataset.addValue(1, "盯防", "佛山");
		// dataset.addValue(0, "采集", "佛山");
		// dataset.addValue(6, "隐患", "佛山");
		// dataset.addValue(1, "其它", "佛山");
		return dataset;
	}

}
