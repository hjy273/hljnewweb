package com.cabletech.commons.chart;

import java.io.PrintWriter;

import javax.servlet.http.HttpSession;

public interface Chart {
	public String generateChart(HttpSession session, PrintWriter pw);
}
