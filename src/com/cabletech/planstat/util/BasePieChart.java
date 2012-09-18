package com.cabletech.planstat.util;

import java.io.PrintWriter;
import javax.servlet.http.HttpSession;
import org.jfree.chart.ChartRenderingInfo;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.entity.StandardEntityCollection;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.servlet.ServletUtilities;
import org.jfree.data.general.DefaultPieDataset;

public abstract class BasePieChart {
	/**
	 * 生成饼图
	 * 
	 * @param session
	 * @param pw
	 * @return String
	 */
	public  String generatePieChart(HttpSession session, PrintWriter pw,int width ,int heigth) {
		String filename = null;
		try {
			// Create and populate a PieDataSet
			DefaultPieDataset data = dataSet(session);

			// Create the chart object
			PiePlot plot = new PiePlot(data);
			// plot.setInsets(new Insets(0, 5, 5, 5));
			plot.setLabelGenerator(new StandardPieSectionLabelGenerator("{0}  {1} 占({2})"));//设置标签 
			
			// plot.setToolTipGenerator(new StandardPieToolTipGenerator());
			JFreeChart chart = new JFreeChart(null,
					JFreeChart.DEFAULT_TITLE_FONT, plot, true);
			chart.setBackgroundPaint(java.awt.Color.lightGray);

			// Write the chart image to the temporary directory
			ChartRenderingInfo info = new ChartRenderingInfo(
					new StandardEntityCollection());
			filename = ServletUtilities.saveChartAsPNG(chart, width,
					heigth, info, session);

			// Write the image map to the PrintWriter
			ChartUtilities.writeImageMap(pw, filename, info, true);
			pw.flush();

		} catch (Exception e) {
			System.out.println("Exception - " + e.toString());
			e.printStackTrace(System.out);
			filename = "public_error_500x300.png";
		}
		return filename;
	}
	abstract DefaultPieDataset dataSet(HttpSession session);
}
