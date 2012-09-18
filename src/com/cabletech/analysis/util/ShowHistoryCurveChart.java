package com.cabletech.analysis.util;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.geom.Ellipse2D;
import java.io.PrintWriter;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.jfree.chart.ChartRenderingInfo;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.DateTickUnit;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.entity.StandardEntityCollection;
import org.jfree.chart.labels.StandardXYToolTipGenerator;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.StackedXYAreaRenderer;
import org.jfree.chart.renderer.xy.XYAreaRenderer;
import org.jfree.chart.servlet.ServletUtilities;
import org.jfree.chart.urls.TimeSeriesURLGenerator;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.xy.DefaultTableXYDataset;
import org.jfree.data.xy.XYSeries;

import com.cabletech.analysis.beans.HistoryWorkInfoConditionBean;
import com.cabletech.baseinfo.domainobjects.UserInfo;
import com.cabletech.commons.config.GisConInfo;
import com.cabletech.commons.config.UserType;
import com.cabletech.commons.util.DateUtil;

/**
 * 显示在线人数曲线图,包括用户输入的起止日期内的图例及用户选定某一天的图例
 */
public class ShowHistoryCurveChart {
	private DefaultPieDataset data = new DefaultPieDataset();

	private GisConInfo config = GisConInfo.newInstance();

	// 将配置文件中的间隔时间取出并转为ms
	private long spacingtime = 1000L * 60 * Integer.parseInt(config
			.getSpacingTime());

	private DateUtil dateUtil = new DateUtil();

	private long interval = 1000L * 60 * 60 * 24;

	private static HistoryWorkInfoConditionBean bean = null;

	private static String givenDay = "";


	/**
	 * 生成折线图
	 * 
	 * @param session
	 *            session
	 * @param pw
	 *            用于输出
	 * @return String
	 */
	public static String generateXYChart(HttpSession session, PrintWriter pw) {
		String filename = null;
		try {
			// Retrieve list of WebHits for each section and populate a
			// TableXYDataset
			// MyDataSet mDataSet = new MyDataSet();
			Map map = null;
			UserInfo userInfo = (UserInfo) session.getAttribute("LOGIN_USER");
			String userType = userInfo.getType();
			// 得到“用户是否选中了某一天”的信息
			givenDay = (String) session.getAttribute("givenDate");
			if ("0".equals(givenDay)) { // 如果用户没有选择具体某一天
				map = (Map) session.getAttribute("MapOnlineNumberForDays");
			} else { // 如果用户选择了具体某一天
				map = (Map) session.getAttribute("MapOnlineNumberForHours");
			}
			if (map.size() == 0) {
				System.out.println("No data has been found");
				throw new NoDataException();
			}
			bean = (HistoryWorkInfoConditionBean) session
					.getAttribute("HistoryWorkInfoConBean");
			DefaultTableXYDataset dataset = new ShowHistoryCurveChart()
					.dataSet(map, "在线人数");

			// Throw a custom NoDataException if there is no data
			if (dataset.getItemCount() == 0) {
				System.out.println("No data has been found");
				throw new NoDataException();
			}
			// Create tooltip and URL generators
			SimpleDateFormat sdf = null;
			if ("0".equals(givenDay)) {
				sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
			} else {
				sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",
						Locale.CHINESE);
			}
			// 设置弹出窗口.
			// StandardXYToolTipGenerator ttg = new StandardXYToolTipGenerator(
			// StandardXYToolTipGenerator.DEFAULT_TOOL_TIP_FORMAT, sdf,
			// NumberFormat.getInstance());
			StandardXYToolTipGenerator ttg = new StandardXYToolTipGenerator(
					"{1}", sdf, NumberFormat.getInstance());
			// 设置url连接到其他页面
			TimeSeriesURLGenerator urlg = null;
			// 如果显示的是以天为单位,并且再符合下列条件之一（1、rangeID长度为2，即没有选具体的对象ID。2、用户为市代维）
			// 显示在线人数图，则有链接，否则，以时段（如半小时）为单位，没有链接
			if (("0".equals(givenDay))
					&& (((bean.getRangeID()).length() == 2) 
							|| UserType.CONTRACTOR
							.equals(userType))) {
				urlg = new TimeSeriesURLGenerator(
						sdf,
						"/WebApp/WorkInfoHistoryAction.do?method=getOnlineNumberForHours",
						"series", "hitDate");
			}
			// Create the X-Axis
			DateAxis xAxis = new DateAxis(null);
			// xAxis.setLowerMargin(0.1);
			// xAxis.setUpperMargin(0.1);
			if ("0".equals(givenDay)) {
				int lastChat = bean.getStartDate().lastIndexOf("/");
//				if (lastChat == -1){
//					lastChat = bean.getStartDate().lastIndexOf("-");
//				}
				int end = bean.getStartDate().length();
				int startDay = Integer.parseInt(bean.getStartDate().substring(lastChat + 1, end));
				lastChat = bean.getEndDate().lastIndexOf("/");
//				if (lastChat == -1){
//					lastChat = bean.getEndDate().lastIndexOf("-");
//				}
				end = bean.getEndDate().length();
				int endDay = Integer.parseInt(bean.getEndDate()
						.substring(lastChat + 1, end), 10);
				if (endDay - startDay < 13) {
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
					XYAreaRenderer.AREA_AND_SHAPES, ttg, urlg);
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
			filename = "public_nodata_600x380.png";
		} catch (Exception e) {
			System.out.println("Exception - " + e.toString());
			e.printStackTrace(System.out);
			filename = "public_error_600x380.png";
		} finally {
			session.setAttribute("givenDate", "0");
		}
		return filename;
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
		if ("0".equals(givenDay)) { // 如果用户没有选择具体某一天
			/* 取得巡检开始结束日期 */
			String startDate = bean.getStartDate();
			startDate = startDate.replace('/', '-'); // 转换格式
			String endDate = bean.getEndDate();
			endDate = endDate.replace('/', '-');
			long startDateLong = dateUtil
					.strDateToLong(startDate, "yyyy-MM-dd");
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
				startDate = dateUtil.longTostrTime(
						startDateLong, "yyyy-MM-dd");
			}
		} else {
			/* 取得巡检开始结束时间 */
			String startTime = config.getPatrolStartTime(); // 格式"6:30:00";
			String endTime = config.getPatrolEndTime(); // 格式"21:30:00";
			String startDateAndTime = givenDay + " " + startTime;
			String endDateAndTime = givenDay + " " + endTime;
			long startDateAndTimeLong = dateUtil
					.strDateAndTimeToLong(startDateAndTime);
			long endDateAndTimeLong = dateUtil
					.strDateAndTimeToLong(endDateAndTime)
					+ spacingtime;
			while (startDateAndTimeLong != endDateAndTimeLong) {
				// 将毫秒数转化为日期（年月日时分秒）
				Object object = map.get(dateUtil.longTostrTime(
						startDateAndTimeLong, "yyyy-MM-dd HH:mm:ss"));
				if (object != null) {
					Integer onlineNum = (Integer) object;
					dataSeries.add(startDateAndTimeLong, onlineNum.intValue());
				} else {
					dataSeries.add(startDateAndTimeLong, 0);
				}
				startDateAndTimeLong = startDateAndTimeLong + spacingtime;
			}
		}
		dataset.addSeries(dataSeries);
		return dataset;
	}

	/**
	 * 组织最终01图
	 * 
	 * @param list
	 *            01数据列表,格式如：01010101010101001010101010101010
	 * @param lineName
	 *            SIM卡号（同一巡检组下可能有多个SIM卡）
	 * @param givenDay
	 *            选定具体日期
	 * @return DefaultTableXYDataset
	 */
	public DefaultTableXYDataset dataSetZeroOne(List list, String lineName,
			String givenDay) {
		DefaultTableXYDataset dataset = new DefaultTableXYDataset();
		XYSeries dataSeries = new XYSeries(lineName, true, false);
		/* 取得巡检开始结束时间 */
		String startTime = config.getPatrolStartTime(); // 格式"6:30:00";
		String endTime = config.getPatrolEndTime(); // 格式"21:30:00";
		String startDateAndTime = givenDay + " " + startTime;
		String endDateAndTime = givenDay + " " + endTime;
		//得到开始日期和时间的毫秒数
		long startDateAndTimeLong = dateUtil
				.strDateAndTimeToLong(startDateAndTime);
		//得到终止日期和时间的毫秒数
		long endDateAndTimeLong = dateUtil.strDateAndTimeToLong(endDateAndTime)
				+ spacingtime;
		int i = 0;
		// 以下代码为循环代码，先将long型的起止时间转为String型，然后在起止时间范围内逐个装载数据,将小时段与是否在线一一对应起来
		while (startDateAndTimeLong != endDateAndTimeLong) {
			Object object = list.get(i);
			if (object != null) {
				Integer zeroOrFlag = (Integer) object;
				//不管是0是1，都放入dataSeries
				dataSeries.add(startDateAndTimeLong, zeroOrFlag.intValue());
			} else {
				dataSeries.add(startDateAndTimeLong, 0);
			}
			i++;
			startDateAndTimeLong = startDateAndTimeLong + spacingtime;
		}
		dataset.addSeries(dataSeries);
		return dataset;
	}
}
