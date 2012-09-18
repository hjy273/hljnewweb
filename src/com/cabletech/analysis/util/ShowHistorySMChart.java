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
 * ��ʾ����ͼ���������û��������ֹ�����ڵ�ͼ�����û�ѡ��ĳһ���ͼ��
 */
public class ShowHistorySMChart extends HttpServlet {
	private static Logger logger = Logger.getLogger("ShowHistorySMChart");

	/**
	 * �ⲿ�����������
	 * @param req ����
	 * @param res �ظ�
	 */
	public void service(ServletRequest req, ServletResponse res)
			throws ServletException, IOException {
		List list = null;
		// �õ����û��Ƿ�ѡ����ĳһ�족����Ϣ
		String flagGivenDate = (String) ((HttpServletRequest) req).getSession()
				.getAttribute("flagGivenDate");
		logger.info("in showSMGraphic:" + flagGivenDate);
		//����û�û��ѡ�����ĳһ��
		if ("0".equals(flagGivenDate)) {
			list = (List) ((HttpServletRequest) req).getSession().getAttribute(
					"smgraphichistoryinfo");
		} else { // ����û�ѡ���˾���ĳһ��
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
		//����û�û��ѡ�����ĳһ��
		if ("0".equals(flagGivenDate)) {
			jfreechart = createChart(getDataSet(list, SMRangeID, userType),
					title);
		} else { // ����û�ѡ���˾���ĳһ��
			title = flagGivenDate + "��ʷ����ִ�������ʾͼ";
			jfreechart = createChart(getDataSet(list, SMRangeID, userType),
					title);
		}
		int width = 700;
		int height = 460;
		Font font = new Font("����", Font.CENTER_BASELINE, 12);
		ChartUtilities.writeChartAsJPEG(res.getOutputStream(), 1.0f,
				jfreechart, width, height, null);

	}

	/**
	 * ������ݼ�
	 * 
	 * @param list
	 *            ���ݼ�
	 * @param smRangeID
	 *            ����ID
	 * @param userType
	 *            �û�����
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
						.get("patrolnum").toString()), "Ѳ��",
						(String) ((BasicDynaBean) list.get(i))
								.get(strGroupUnit));
				mydata.addValue(Integer.parseInt(((BasicDynaBean) list.get(i))
						.get("watchnum").toString()), "����",
						(String) ((BasicDynaBean) list.get(i))
								.get(strGroupUnit));
				mydata.addValue(Integer.parseInt(((BasicDynaBean) list.get(i))
						.get("collectnum").toString()), "�ɼ�",
						(String) ((BasicDynaBean) list.get(i))
								.get(strGroupUnit));
				mydata.addValue(Integer.parseInt(((BasicDynaBean) list.get(i))
						.get("troublenum").toString()), "����",
						(String) ((BasicDynaBean) list.get(i))
								.get(strGroupUnit));
				mydata.addValue(Integer.parseInt(((BasicDynaBean) list.get(i))
						.get("othernum").toString()), "����",
						(String) ((BasicDynaBean) list.get(i))
								.get(strGroupUnit));
			}
		}
		return mydata;
	}

	/**
	 * ����ͼ������ͼ�ĸ��ֲ���
	 * 
	 * @param categorydataset
	 *            ���ݼ�
	 * @param title
	 *            ͼƬ����
	 * @return JFreeChart
	 */
	private static JFreeChart createChart(CategoryDataset categorydataset,
			String title) {
		JFreeChart jfreechart = ChartFactory.createStackedBarChart(title,
				"���ࣺSIM����", "��������", categorydataset,
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
