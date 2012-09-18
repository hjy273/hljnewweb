package com.cabletech.analysis.util;

import java.io.PrintWriter;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.jfree.data.general.DefaultPieDataset;

/**
 * 生成线段巡检情况对比的饼图
 */
public class SublineChart extends PieChart {
	private Logger logger = Logger.getLogger("ContrastChart");

	DefaultPieDataset dataSet(HttpSession session) {
		DefaultPieDataset dataset = new DefaultPieDataset();
		Map sublinemap = (Map)session.getAttribute("sublinemap");
		Iterator it = sublinemap.keySet().iterator();
		while (it.hasNext()) {
			String key = it.next().toString();
			Integer value = (Integer)sublinemap.get(key);
			dataset.setValue(key, value);
		}
		return dataset;
	}

	
}
