<%@ page import = "com.cabletech.analysis.util.ShowHistoryCurveChart" %>
<%@ page import = "java.io.PrintWriter" %>
<%@ page import ="com.cabletech.analysis.util.ChartSize" %>
<% 
	String filename = ShowHistoryCurveChart.generateXYChart(session, new PrintWriter(out));
	String graphURL = request.getContextPath() + "/servlet/DisplayChart?filename=" + filename;
	if(filename.indexOf("_nodata") != -1 || filename.indexOf("_error") != -1){
%>
<img src="${ctx}/images/public_nodata_500x300.png" width=580 height=380 border=0>
<%
	}else{
	%>
<img src="<%= graphURL %>" width=<%=ChartSize.WIDTH %> height=<%=ChartSize.HEIGHT %> border=0 usemap="#<%= filename %>">
<%
	}
%>
