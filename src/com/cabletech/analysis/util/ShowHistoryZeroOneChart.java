package com.cabletech.analysis.util;

import java.awt.Font;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import javax.servlet.http.HttpSession;
import org.jfree.chart.ChartRenderingInfo;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.NumberTickUnit;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.entity.StandardEntityCollection;
import org.jfree.chart.plot.CombinedDomainXYPlot;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYStepRenderer;
import org.jfree.chart.servlet.ServletUtilities;
import org.jfree.data.Range;
import org.jfree.data.xy.DefaultTableXYDataset;
import com.cabletech.analysis.util.ChartSize;

/**
 * ��ʾ01ͼ
 */
public class ShowHistoryZeroOneChart {
	private static String givenDate = "";

	/**
	 * �ⲿ���õ�����01ͼ�ķ���
	 * @param session session
	 * @param pw �������
	 * @return String
	 */
	public static String generateZeroOnechart(HttpSession session,
			PrintWriter pw) {
		String filename = null;
		try {
			// Retrieve list of WebHits for each section and populate a
			// TableXYDataset
			Map zeroOne = (Map) session.getAttribute("MapOnlineNumberForHours");
			// �õ����û��Ƿ�ѡ����ĳһ�족����Ϣ
			givenDate = (String) session.getAttribute("flagGivenDate");
			List datasetList = dataSetList(zeroOne);
			// Throw a custom NoDataException if there is no data
			if (datasetList.size() == 0) {
				System.out.println("No data has been found");
				throw new NoDataException();
			}
			// Create tooltip and URL generators
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",
					Locale.CHINA);
			// Create the X-Axis
			DateAxis xAxis = new DateAxis(null);
			xAxis.setLowerMargin(0.0);
			xAxis.setUpperMargin(0.0);
			xAxis.setDateFormatOverride(new SimpleDateFormat("HH:mm:ss",
					Locale.CHINESE));
			// Create the X-Axis
			NumberAxis yAxis = new NumberAxis(null);
			yAxis.setAutoRangeIncludesZero(true);
			yAxis.setLowerMargin(1);
			Range range = new Range(-0.5d, 1.5);
			yAxis.setRange(range);
			yAxis.setLowerBound(-1);
			yAxis.setTickUnit(new NumberTickUnit(1D));
			// Create the renderer
			// Create the plot
			List xyPlotList = createXYPlot(datasetList, xAxis, yAxis);

			// Reconfigure Y-Axis so the auto-range knows that the data is
			// stacked
			yAxis.configure();

			// Create the chart
			JFreeChart chart = createChart(xyPlotList);
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
		}
		return filename;
	}

	/**
	 * ����ͼ��
	 * 
	 * @param xyPlotList ���ڴ���ͼ����List
	 * @return JFreeChart
	 */
	private static JFreeChart createChart(List xyPlotList) {
		ValueAxis valueAxis = new DateAxis();
		CombinedDomainXYPlot plot = new CombinedDomainXYPlot(valueAxis);
		int size = xyPlotList.size();
		for (int i = 0; i < size; i++) {
			XYPlot xyPlot = (XYPlot) xyPlotList.get(i);
			plot.add(xyPlot, 1);
		}
		plot.setDomainGridlinesVisible(true);
		JFreeChart chart = new JFreeChart("", new Font("SansSerif", Font.BOLD,
				12), plot, true);
		return chart;
	}

	/**
	 * ����dataset�����Լ�xAxis��yAxis�����ᣬ����XYPlot����ʵ���������ӵ�List�з��ء�
	 * 
	 * @param datasetList
	 *            ��Ҫ���صĸ������ݡ�
	 * @param xAxis
	 *            x����������
	 * @param yAxis
	 *            y����������
	 * @return xyPlotList XYPlot����ʵ���б���
	 */
	private static List createXYPlot(List datasetList, DateAxis xAxis,
			NumberAxis yAxis) {
		List xyPlotList = new ArrayList();
		int size = datasetList.size();
		for (int i = 0; i < size; i++) {
			DefaultTableXYDataset dataset = (DefaultTableXYDataset) datasetList
					.get(i);
			XYPlot plot = new XYPlot(dataset, xAxis, yAxis,
					new XYStepRenderer());
			plot.setForegroundAlpha(0.65f);
			xyPlotList.add(plot);
		}
		return xyPlotList;
	}

	/**
	 * ȡ��01ͼ�е����ݲ�����ShowHistoryCurveChart���dataSetZeroOne����������ʾ01ͼ
	 * 
	 * @param zeroOne
	 *            Map�͵�01����
	 * @return List
	 */
	private static List dataSetList(Map zeroOne) {
		//SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",
				//Locale.CHINA);
		ShowHistoryCurveChart curveChart = new ShowHistoryCurveChart();
		List dataSet = new ArrayList();
		Iterator it = zeroOne.keySet().iterator();
		while (it.hasNext()) {
			String key = it.next().toString();
			//zeroOneList��ʽ�磺01010101010101001010101010101010
			List zeroOneList = (List) zeroOne.get(key);
			DefaultTableXYDataset dataset = curveChart.dataSetZeroOne(
					zeroOneList, key, givenDate); // ������֯����01ͼ����
			dataSet.add(dataset);
		}
		return dataSet;
	}
}