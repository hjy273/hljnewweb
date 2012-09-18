package com.cabletech.analysis.util;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.geom.Ellipse2D;
import java.io.PrintWriter;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.jfree.chart.ChartRenderingInfo;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.entity.StandardEntityCollection;
import org.jfree.chart.labels.StandardXYToolTipGenerator;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.StackedXYAreaRenderer;
import org.jfree.chart.renderer.xy.XYAreaRenderer;
import org.jfree.chart.servlet.ServletUtilities;
import org.jfree.data.xy.DefaultTableXYDataset;
import org.jfree.data.xy.XYSeries;

import com.cabletech.commons.config.GisConInfo;
import com.cabletech.commons.util.DateUtil;

/**
 * 根据用户数据生成在线人员实时曲线图
 * 
 */
public class RealTimeOnlineChart {

	
	private GisConInfo config = GisConInfo.newInstance();
	private DateUtil dateUtil = new DateUtil();
	private long spacingtime = 1000L * 60 * Integer.parseInt(config
			.getSpacingTime());

	/**
	 * 生成在线人员实时曲线图图片，返回图片名称。
	 * 
	 * @param session
	 *            HttpSession
	 * @param pw
	 *            PrintWriter
	 * @return string 返回图片名称
	 */
	public static String generateXYChart(HttpSession session, PrintWriter pw) {
		String filename = null;
		try {
			// Retrieve list of WebHits for each section and populate a
			// TableXYDataset
			// MyDataSet mDataSet = new MyDataSet();
			Map map = (Map) session.getAttribute("onlineNum");
			if (map.size() == 0) {
				System.out.println("No data has been found");
				throw new NoDataException();
			}
			DefaultTableXYDataset dataset = new RealTimeOnlineChart().dataSet(
					map, "在线人数");

			// Throw a custom NoDataException if there is no data
			if (dataset.getItemCount() == 0) {
				System.out.println("No data has been found");
				throw new NoDataException();
			}
			// Create tooltip and URL generators
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",
					Locale.CHINA);
			// 设置弹出窗口.
			// StandardXYToolTipGenerator ttg = new StandardXYToolTipGenerator(
			// StandardXYToolTipGenerator.DEFAULT_TOOL_TIP_FORMAT, sdf,
			// NumberFormat.getInstance());
			StandardXYToolTipGenerator ttg = new StandardXYToolTipGenerator(
					"{1}", sdf, NumberFormat.getInstance());
			// 设置url连接到其他页面
			// TimeSeriesURLGenerator urlg = new
			// TimeSeriesURLGenerator(sdf,"bar_chart.jsp", "series", "hitDate");

			// Create the X-Axis
			DateAxis xAxis = new DateAxis(null);
			xAxis.setLowerMargin(0.0);
			xAxis.setUpperMargin(0.0);

			// Create the X-Axis
			NumberAxis yAxis = new NumberAxis(null);
			yAxis.setAutoRangeIncludesZero(true);

			// Create the renderer
			// XYStepAreaRenderer renderer = new
			// XYStepAreaRenderer(XYAreaRenderer.AREA_AND_SHAPES,null, null);
			// 折线图
			StackedXYAreaRenderer renderer = new StackedXYAreaRenderer(
					XYAreaRenderer.AREA_AND_SHAPES, ttg, null);
			// renderer.setSeriesPaint(0, new Color(255, 255, 180));
			// renderer.setSeriesPaint(1, new Color(206, 230, 255));
			// renderer.setSeriesPaint(2, new Color(255, 230, 230));
			renderer.setSeriesPaint(0, new Color(206, 255, 206));
			renderer.setShapePaint(Color.gray);
			renderer.setShapeStroke(new BasicStroke(0.5f));
			renderer.setShape(new Ellipse2D.Double(-3, -3, 6, 6));
			renderer.setOutline(true);

			// Create the plot
			XYPlot plot = new XYPlot(dataset, xAxis, yAxis, renderer);
			plot.setForegroundAlpha(0.65f);

			// Reconfigure Y-Axis so the auto-range knows that the data is
			// stacked
			yAxis.configure();

			// Create the chart
			JFreeChart chart = new JFreeChart(null,
					JFreeChart.DEFAULT_TITLE_FONT, plot, true);
			chart.setBackgroundPaint(java.awt.Color.white);

			// Write the chart image to the temporary directory
			ChartRenderingInfo info = new ChartRenderingInfo(
					new StandardEntityCollection());
			filename = ServletUtilities.saveChartAsPNG(chart, ChartSize.WIDTH,
					ChartSize.HEIGHT, info, session);

			// Write the image map to the PrintWriter
			ChartUtilities.writeImageMap(pw, filename, info, true);
			pw.flush();

		} catch (NoDataException e) {
			System.out.println(e.toString());
			filename = "public_nodata_500x300.png";
		} catch (Exception e) {
			System.out.println("Exception - " + e.toString());
			e.printStackTrace(System.out);
			filename = "public_error_500x300.png";
		}
		return filename;
	}

	/**
	 * 将在线数据信息装载到DefaultTableXYDataset 对象中。
	 * 
	 * @param map
	 *            在线数据信息
	 * @param lineName 显示的曲线名称
	 * @return DefaultTableXYDataset
	 */
	public DefaultTableXYDataset dataSet(Map map, String lineName) {
		DefaultTableXYDataset dataset = new DefaultTableXYDataset();
		XYSeries dataSeries = new XYSeries(lineName, true, false);

		/* 取得巡检开始结束时间 */
		String startTime = config.getPatrolStartTime();// 格式"6:30:00";
		String endTime = config.getPatrolEndTime();// 格式"21:30:00";
		long currentStartTime = dateUtil.strTimeToLong(startTime);
		long currentEndTime = dateUtil.strTimeToLong(endTime) + spacingtime;
		while (currentStartTime <= currentEndTime) {
			//System.out.println("currentStartTime || currentEndTime "+dateUtil.longTostrTime(currentStartTime, "HH:mm:ss") +" = "+ dateUtil.longTostrTime(currentEndTime, "HH:mm:ss"));
			Object object = map.get(dateUtil.longTostrTime(currentStartTime,
					"HH:mm:ss"));
			if (object != null) {
				Integer onlineNum = (Integer) object;
				dataSeries.add(currentStartTime, onlineNum.intValue());
			} else {
				dataSeries.add(currentStartTime, 0);
			}

			currentStartTime = currentStartTime + spacingtime;
		}
		dataset.addSeries(dataSeries);
		return dataset;

	}
}
