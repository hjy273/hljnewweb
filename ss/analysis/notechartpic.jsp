<%@ page import = "com.cabletech.analysis.util.RealTimeNoteChart" %>
<%@ page import = "com.cabletech.analysis.util.ChartSize" %>
<%@ page import = "java.io.PrintWriter" %>
<%@ page import = "java.util.ArrayList" %>
<%@ page import = "java.util.Iterator" %>
<%
System.out.println("awwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwww");
 %>
<%
	String filename = RealTimeNoteChart.generateBarChart(session, new PrintWriter(out));
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