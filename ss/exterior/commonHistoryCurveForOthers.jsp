<%@ page import = "com.cabletech.analysis.util.ShowHistoryCurveChart" %>
<%@ page import = "java.io.PrintWriter" %>
<%@ page import ="com.cabletech.analysis.util.ChartSize" %>
<% 
	String filename = ShowHistoryCurveChart.generateXYChart(session, new PrintWriter(out));
	System.out.println(filename);
	String graphURL = request.getContextPath() + "/servlet/DisplayChart?filename=" + filename;
	System.out.println(graphURL);
	System.out.println("filename  :" + (filename.indexOf("_nodata") != -1 ));
	//if(filename.indexOf("_nodata") != -1 || filename.indexOf("_error") != -1){
%>
<!-- 
<!-- img src="${ctx}/images/public_nodata_500x300.png" width=580 height=380 border=0> -->
<%
	//}else{
	%>
<!-- 	
	<div align="center" id="pic">
<img src="<%= graphURL %>" width=<%=ChartSize.WIDTH %> height=<%=ChartSize.HEIGHT %> border=0 usemap="#<%= filename %>">
</div>
 -->
<%
//}

%>


