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
 * ����ʵʱ����ͼ��
 *
 */
public class RealTimeNoteChart {
	/**
	 * ���ɶ��ŵ�bar chart
	 * @param session HttpSession
	 * @param pw PrintWriter
	 * @return ����ͼƬ����
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
					.createStackedBarChart("", "", "����ִ����", dataset,
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
	 * ������������Ϣ��װ��DefaultCategoryDataset�����С�
	 * @param list ����������Ϣ
	 * @param type �û����� 
	 * @return dateSet �����Է�װ����Ϣ��
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
						"patrol").toString()), "Ѳ��", (String) ((Map) list
						.get(i)).get(titleName));
				dataset.addValue(Integer.parseInt(((Map) list.get(i)).get(
						"watch").toString()), "����",
						(String) ((Map) list.get(i)).get(titleName));
				dataset.addValue(Integer.parseInt(((Map) list.get(i)).get(
						"collect").toString()), "�ɼ�", (String) ((Map) list
						.get(i)).get(titleName));
				dataset.addValue(Integer.parseInt(((Map) list.get(i)).get(
						"trouble").toString()), "����", (String) ((Map) list
						.get(i)).get(titleName));
				dataset.addValue(Integer.parseInt(((Map) list.get(i)).get(
						"other").toString()), "����",
						(String) ((Map) list.get(i)).get(titleName));
			}
		}
		// dataset.addValue(14, "Ѳ��", "����");
		// dataset.addValue(3, "����", "����");
		// dataset.addValue(2, "�ɼ�", "����");
		// dataset.addValue(4, "����", "����");
		// dataset.addValue(1, "����", "����");
		//
		// dataset.addValue(23, "Ѳ��", "����");
		// dataset.addValue(1, "����", "����");
		// dataset.addValue(0, "�ɼ�", "����");
		// dataset.addValue(6, "����", "����");
		// dataset.addValue(1, "����", "����");
		//		
		// dataset.addValue(14, "Ѳ��", "տ��");
		// dataset.addValue(3, "����", "տ��");
		// dataset.addValue(2, "�ɼ�", "տ��");
		// dataset.addValue(4, "����", "տ��");
		// dataset.addValue(1, "����", "տ��");
		//
		// dataset.addValue(23, "Ѳ��", "��ɽ");
		// dataset.addValue(1, "����", "��ɽ");
		// dataset.addValue(0, "�ɼ�", "��ɽ");
		// dataset.addValue(6, "����", "��ɽ");
		// dataset.addValue(1, "����", "��ɽ");
		return dataset;
	}

}
