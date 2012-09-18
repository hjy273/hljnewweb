package com.cabletech.baseinfo.util;

import java.io.PrintWriter;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.jfree.chart.ChartRenderingInfo;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.entity.StandardEntityCollection;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.servlet.ServletUtilities;
import org.jfree.chart.urls.StandardPieURLGenerator;
import org.jfree.data.general.DefaultPieDataset;

import com.cabletech.analysis.util.ChartSize;
import com.cabletech.analysis.util.NoDataException;
import com.cabletech.baseinfo.beans.UseTerminalBean;
import com.cabletech.commons.chart.Chart;
/**
 * 设备使用情况，饼图
 *
 */
public class UseTerminalChart implements Chart {
	/**
	 * 生成饼图图片，并返回图片名称
	 * @param session HttpSession
	 * @param  pw PrintWriter
	 * @return String 返回图片名称。
	 */
	public String generateChart(HttpSession session, PrintWriter pw) {
		String filename = null;
		try {
			// Retrieve list of WebHits
			Map map = (Map) session.getAttribute("utMap");
			UseTerminalBean useTerminal = (UseTerminalBean) session
					.getAttribute("query");
			String title = (String) UseTerminalBean.TYPE_MAP.get(useTerminal
					.getType());
			// Throw a custom NoDataException if there is no data
			if (map.size() == 0) {
				System.out.println("No data has been found");
				throw new NoDataException();
			}

			// Create and populate a PieDataSet
			DefaultPieDataset data = myDataSet(map, useTerminal.getType());

			// Create the chart object
			PiePlot plot = new PiePlot(data);
			// plot.setInsets(new Insets(0, 5, 5, 5));
			plot
					.setURLGenerator(new StandardPieURLGenerator(
							"/WebApp/UseTerminalAction.do?method=getUseTerminalCondition",
							"section"));
			// plot.setToolTipGenerator(new StandardPieToolTipGenerator());
			JFreeChart chart = new JFreeChart(title,
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
	 * 将数据装载到DefaultPieDataset 对象中。
	 * 
	 * @param map
	 *            需要加载的数据
	 * @param type 用户查询的类型 详细信息查看 UseTerminalBean 中的说明
	 * @return 返回DefaultPieDataset对象
	 */
	private DefaultPieDataset myDataSet(Map map, String type) {
		DefaultPieDataset data = new DefaultPieDataset();
		Iterator it = map.keySet().iterator();
		while (it.hasNext()) {
			String key = it.next().toString();
//			if (UseTerminalBean.PATROL_KM.equals(type)) {
//				Double value = (Integer) map.get(key);
//				data.setValue(key, value);
//			} else {
				Integer value = (Integer) map.get(key);
				data.setValue(key, value);
//			}
		}
		return data;
	}
}
