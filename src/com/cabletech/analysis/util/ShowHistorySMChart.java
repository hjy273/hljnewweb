package com.cabletech.analysis.util;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;
import org.apache.commons.beanutils.BasicDynaBean;
import org.apache.log4j.Logger;
import java.awt.Color;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.AxisLocation;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.StackedBarRenderer;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.chart.ChartUtilities;

import com.cabletech.baseinfo.domainobjects.UserInfo;
import com.cabletech.commons.config.UserType;

import java.awt.Font;
import java.io.*;

/**
 * 显示短信图例，包括用户输入的起止日期内的图例及用户选定某一天的图例
 */
public class ShowHistorySMChart extends HttpServlet {
	private static Logger logger = Logger.getLogger("ShowHistorySMChart");

	/**
	 * 外部调用最初方法
	 * @param req 请求
	 * @param res 回复
	 */
	public void service(ServletRequest req, ServletResponse res)
			throws ServletException, IOException {
		List list = null;
		// 得到“用户是否选中了某一天”的信息
		String flagGivenDate = (String) ((HttpServletRequest) req).getSession()
				.getAttribute("flagGivenDate");
		logger.info("in showSMGraphic:" + flagGivenDate);
		//如果用户没有选择具体某一天
		if ("0".equals(flagGivenDate)) {
			list = (List) ((HttpServletRequest) req).getSession().getAttribute(
					"smgraphichistoryinfo");
		} else { // 如果用户选择了具体某一天
			list = (List) ((HttpServletRequest) req).getSession().getAttribute(
					"smgraphichistoryinfogivenday");
		}
		res.setContentType("image/jpeg");
		String title = null;

		String SMRangeID = (String) ((HttpServletRequest) req).getSession()
				.getAttribute("SMGraphicRangeID");
		UserInfo userInfo = (UserInfo) ((HttpServletRequest) req).getSession()
				.getAttribute("LOGIN_USER");
		String userType = userInfo.getType();
		JFreeChart jfreechart = null;
		//如果用户没有选择具体某一天
		if ("0".equals(flagGivenDate)) {
			jfreechart = createChart(getDataSet(list, SMRangeID, userType),
					title);
		} else { // 如果用户选择了具体某一天
			title = flagGivenDate + "历史任务执行情况显示图";
			jfreechart = createChart(getDataSet(list, SMRangeID, userType),
					title);
		}
		int width = 700;
		int height = 460;
		Font font = new Font("黑体", Font.CENTER_BASELINE, 12);
		ChartUtilities.writeChartAsJPEG(res.getOutputStream(), 1.0f,
				jfreechart, width, height, null);

	}

	/**
	 * 填充数据集
	 * 
	 * @param list
	 *            数据集
	 * @param smRangeID
	 *            区域ID
	 * @param userType
	 *            用户类型
	 * @return DefaultCategoryDataset
	 */
	private static DefaultCategoryDataset getDataSet(List list,
			String smRangeID, String userType) {
		DefaultCategoryDataset mydata = new DefaultCategoryDataset();
		String strGroupUnit = "";
		if (UserType.CONTRACTOR.equals(userType)) {
			strGroupUnit = "simid";
		} else {
			strGroupUnit = "rangename";
		}
		if (!list.isEmpty() && list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				mydata.addValue(Integer.parseInt(((BasicDynaBean) list.get(i))
						.get("patrolnum").toString()), "巡检",
						(String) ((BasicDynaBean) list.get(i))
								.get(strGroupUnit));
				mydata.addValue(Integer.parseInt(((BasicDynaBean) list.get(i))
						.get("watchnum").toString()), "盯防",
						(String) ((BasicDynaBean) list.get(i))
								.get(strGroupUnit));
				mydata.addValue(Integer.parseInt(((BasicDynaBean) list.get(i))
						.get("collectnum").toString()), "采集",
						(String) ((BasicDynaBean) list.get(i))
								.get(strGroupUnit));
				mydata.addValue(Integer.parseInt(((BasicDynaBean) list.get(i))
						.get("troublenum").toString()), "隐患",
						(String) ((BasicDynaBean) list.get(i))
								.get(strGroupUnit));
				mydata.addValue(Integer.parseInt(((BasicDynaBean) list.get(i))
						.get("othernum").toString()), "其它",
						(String) ((BasicDynaBean) list.get(i))
								.get(strGroupUnit));
			}
		}
		return mydata;
	}

	/**
	 * 构造图，设置图的各种参数
	 * 
	 * @param categorydataset
	 *            数据集
	 * @param title
	 *            图片标题
	 * @return JFreeChart
	 */
	private static JFreeChart createChart(CategoryDataset categorydataset,
			String title) {
		JFreeChart jfreechart = ChartFactory.createStackedBarChart(title,
				"分类：SIM卡号", "任务数量", categorydataset,
				PlotOrientation.HORIZONTAL, true, true, false);
		jfreechart.setBackgroundPaint(Color.white);
		CategoryPlot categoryplot = (CategoryPlot) jfreechart.getPlot();
		categoryplot.setBackgroundPaint(Color.lightGray);
		categoryplot.setRangeGridlinePaint(Color.white);
		categoryplot.setRangeAxisLocation(AxisLocation.BOTTOM_OR_LEFT);
		StackedBarRenderer stackedbarrenderer = (StackedBarRenderer) categoryplot
				.getRenderer();
		stackedbarrenderer.setMaximumBarWidth(0.1);
		stackedbarrenderer.setDrawBarOutline(false);
		stackedbarrenderer.setItemLabelsVisible(true);
		return jfreechart;
	}
}
