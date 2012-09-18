<%@page contentType="text/html;charset=GBK"%>
<%@page import="java.io.PrintWriter"%>
<%@page import="com.cabletech.statistics.utils.WebPieChart"%>
<%
  WebPieChart chart = new WebPieChart();
  chart.setValue("巡检率", 85);
  chart.setValue("漏检率", 15);
  String filename = chart.generatePieChart("巡检率漏检率对比图表", session, new PrintWriter(out));
  System.out.println("filename : " + filename);
  String graphURL = request.getContextPath() + "/servlet/DisplayChart?filename=" + filename;
  //String graphURL = request.getContextPath() + "/servlet/ShowChart";
  System.out.println("graphURL : " + graphURL);
%>
<HTML>
<HEAD>
<TITLE>www.sentom.net</TITLE>
</HEAD>
<BODY>
<span id="mapspan">
  <img src="<%= graphURL %>" width=500 height=300 border=0 usemap="#<%=filename %>" alt="统计图">
</span>
</BODY>
</HTML>
