<%@ include file="/common/header.jsp"%>
<%@ page import = "com.cabletech.analysis.util.SublineChart" %>
<%@ page import = "com.cabletech.analysis.util.ChartSize" %>
<%@ page import = "java.io.PrintWriter" %>
<%@ page import = "java.util.ArrayList" %>
<%@ page import = "java.util.Iterator" %>
	<div class='title2' style='font-size:14px; font-weight:600;bottom:1' align='center'>
	<%=session.getAttribute("year") %>年<%=session.getAttribute("month") %>月份统计巡检线段信息</div><hr width='100%' size='1'>
<br>
<div align='center'>		
	<%
	SublineChart sublineChart = new SublineChart();
	String filename = sublineChart.generatePieChart(session, new PrintWriter(out),ChartSize.WIDTH,ChartSize.HEIGHT);
	String graphURL = request.getContextPath() + "/servlet/DisplayChart?filename=" + filename;
	if(filename.indexOf("_nodata") != -1 || filename.indexOf("_error") != -1){
	%>
	<img src="${ctx}/images/public_nodata_500x300.png" width=500 height=300 border=0>
	<%
	}else{
	%>
	<img src="<%= graphURL %>" width=<%=ChartSize.WIDTH%> height=<%=ChartSize.HEIGHT%> border=0 usemap="#<%= filename %>">
	<%
	}
	%>
	</div>