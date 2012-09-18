package com.cabletech.analysis.util;

import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.jfree.data.general.DefaultPieDataset;

/**
 * ����ȫʡ������������ͼ
 */
public class AssessResultChart extends PieChart{
	private Logger logger = Logger.getLogger("AssessResultChart");
	/**
	 * װ������
	 */
	DefaultPieDataset dataSet(HttpSession session) {
		DefaultPieDataset dataset = new DefaultPieDataset();
		Map wholepie = (Map)session.getAttribute("wholepie");
		Iterator it = wholepie.keySet().iterator();
		while (it.hasNext()) {
			String key = it.next().toString();
			Integer value = (Integer)wholepie.get(key);
			logger.info("key "+key);
			dataset.setValue(key, value);
		}
		return dataset;
	}

	
}
