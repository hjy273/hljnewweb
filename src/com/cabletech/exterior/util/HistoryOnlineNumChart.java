package com.cabletech.exterior.util;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.geom.Ellipse2D;
import java.io.IOException;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.DateTickUnit;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.labels.StandardXYToolTipGenerator;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.StackedXYAreaRenderer;
import org.jfree.chart.renderer.xy.XYAreaRenderer;
import org.jfree.chart.urls.TimeSeriesURLGenerator;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.xy.DefaultTableXYDataset;
import org.jfree.data.xy.XYSeries;

import com.cabletech.analysis.beans.HistoryWorkInfoConditionBean;
import com.cabletech.analysis.util.NoDataException;
import com.cabletech.commons.config.GisConInfo;
import com.cabletech.commons.util.DateUtil;

/**
 * 显示在线人数曲线图,包括用户输入的起止日期内的图例及用户选定某一天的图例
 */
public class HistoryOnlineNumChart extends HttpServlet {
	private DefaultPieDataset data = new DefaultPieDataset();

	private GisConInfo config = GisConInfo.newInstance();

	// 将配置文件中的间隔时间取出并转为ms
	private long spacingtime = 1000L * 60 * Integer.parseInt(config
			.getSpacingTime());

	private DateUtil dateUtil = new DateUtil();

	private long interval = 1000L * 60 * 60 * 24;

	private static HistoryWorkInfoConditionBean bean = null;

	private static String givenDay = "";

	private Logger logger = Logger.getLogger("ShowRealTimeChart");

	public void service(ServletRequest req, ServletResponse res)
			throws ServletException, IOException {
		HttpSession session = ((HttpServletRequest) req).getSession();
		Map map = null;
		try {
			givenDay = (String) session.getAttribute("givenDate");
			String title = "";
			map = (Map) session.getAttribute("MapOnlineNumberForDays");
			if (map.size() == 0) {
				System.out.println("No data has been found");
				title = "未找到该月统计数据";
			}
			bean = (HistoryWorkInfoConditionBean) session
					.getAttribute("HistoryWorkInfoConBean");
			DefaultTableXYDataset dataset = dataSet(map, "在线人数");

			// Throw a custom NoDataException if there is no data
			if (dataset.getItemCount() == 0) {
				System.out.println("No data has been found");
				throw new NoDataException();
			}

			JFreeChart jfreechart = generateXYChart(title, dataset);
			int width = 700;
			int height = 490;
			ChartUtilities.writeChartAsJPEG(res.getOutputStream(), 1.0f,
					jfreechart, width, height, null);
			
		} catch (NoDataException e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}

	}

	/**
	 * 生成折线图
	 * 
	 * @param session
	 *            session
	 * @param pw
	 *            用于输出
	 * @return String
	 */
	public JFreeChart generateXYChart(String title,
			DefaultTableXYDataset dataset) {
		String filename = null;
		// Create tooltip and URL generators
		SimpleDateFormat sdf = null;
		if ("0".equals(givenDay)) {
			sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
		} else {
			sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINESE);
		}
		// 设置弹出窗口.
		// StandardXYToolTipGenerator ttg = new StandardXYToolTipGenerator(
		// StandardXYToolTipGenerator.DEFAULT_TOOL_TIP_FORMAT, sdf,
		// NumberFormat.getInstance());
		StandardXYToolTipGenerator ttg = new StandardXYToolTipGenerator("{1}",
				sdf, NumberFormat.getInstance());
		// 设置url连接到其他页面
		TimeSeriesURLGenerator urlg = null;
		// 如果显示的是以天为单位,并且再符合下列条件之一（1、rangeID长度为2，即没有选具体的对象ID。2、用户为市代维）
		// 显示在线人数图，则有链接，否则，以时段（如半小时）为单位，没有链接
		// Create the X-Axis
		DateAxis xAxis = new DateAxis(null);
		if ("0".equals(givenDay)) {
			int lastChat = bean.getStartDate().lastIndexOf("-");
			int end = bean.getStartDate().length();
			int startDay = Integer.parseInt(bean.getStartDate().substring(
					lastChat + 1, end), 10);
			lastChat = bean.getEndDate().lastIndexOf("-");
			end = bean.getEndDate().length();
			int endDay = Integer.parseInt(bean.getEndDate().substring(lastChat + 1,
					end), 10);
			if (endDay - startDay < 11) {
				// x轴单位间隔为1天
				xAxis.setTickUnit(new DateTickUnit(DateTickUnit.DAY, 1));
			}
			xAxis.setDateFormatOverride(new SimpleDateFormat("dd",
					Locale.CHINESE));
		}
		// Create the X-Axis
		NumberAxis yAxis = new NumberAxis(null);
		yAxis.setAutoRangeIncludesZero(true);
		// NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
		yAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
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
		JFreeChart chart = new JFreeChart(title, JFreeChart.DEFAULT_TITLE_FONT,
				plot, true);
		chart.setBackgroundPaint(java.awt.Color.white);

		return chart;
	}

	/**
	 * 设置并填充数据
	 * 
	 * @param map
	 *            map数据
	 * @param lineName
	 *            图例名称
	 * @return DefaultTableXYDataset
	 */
	public DefaultTableXYDataset dataSet(Map map, String lineName) {
		DefaultTableXYDataset dataset = new DefaultTableXYDataset();
		XYSeries dataSeries = new XYSeries(lineName, true, false);
		/* 取得巡检开始结束日期 */
		String startDate = bean.getStartDate();
		startDate = startDate.replace('/', '-'); // 转换格式
		String endDate = bean.getEndDate();
		endDate = endDate.replace('/', '-');
		long startDateLong = dateUtil.strDateToLong(startDate, "yyyy-MM-dd");
		long endDateLong = dateUtil.strDateToLong(endDate, "yyyy-MM-dd");
		while (startDateLong <= endDateLong) {
			Object object = map.get(startDate);
			if (object != null) {
				Integer onlineNum = (Integer) object;
				dataSeries.add(startDateLong, onlineNum.intValue());
			} else {
				dataSeries.add(startDateLong, 0);
			}
			startDateLong = startDateLong + interval; // 日期加1
			// 将毫秒数转化为日期型字符串
			startDate = dateUtil.longTostrTime(startDateLong, "yyyy-MM-dd");
		}
		dataset.addSeries(dataSeries);
		return dataset;
	}
}
