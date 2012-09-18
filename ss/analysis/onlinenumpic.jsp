
<%@ page import = "com.cabletech.analysis.util.RealTimeOnlineChart" %>
<%@ page import = "com.cabletech.analysis.util.RealTimeOnlineTimeChart" %>
<%@ page import = "com.cabletech.analysis.util.ChartSize" %>
<%@ page import = "java.io.PrintWriter" %>
<%@ page import = "java.util.ArrayList" %>
<%@ page import = "java.util.Iterator" %>
<META http-equiv="Content-Type" content="text/html; charset=GB2312">
<%
	String range = (String)session.getAttribute("range");	
	String filename ="";
	if(range != null){
		filename = RealTimeOnlineTimeChart.generateStepchart(session,new PrintWriter(out));
	}else{
		filename = RealTimeOnlineChart.generateXYChart(session, new PrintWriter(out));
	}
	String graphURL = request.getContextPath() + "/servlet/DisplayChart?filename=" + filename;
	if(filename.indexOf("_nodata") != -1 || filename.indexOf("_error") != -1){
%>
<img src="${ctx}/images/public_nodata_500x300.png" width=500 height=300 border=0>
<%
	}else{
	
	%>
<img src="<%= graphURL %>" width=<%=ChartSize.WIDTH %> height=<%=ChartSize.HEIGHT%> border=0 usemap="#<%= filename %>">
<%
	}
	String sessionid = session.getId();
%>
<script type="text/javascript">
function overlib(time){	
		var over = false;
		createXMLHttpRequest();
		var url="${ctx}/RealTimeOnlineAction.do?method=getspecifyTimeInfo&time=" + time+"&token="+document.getElementById("rangeId").value+"<%=sessionid%>";
	    if ( time != null){
	       time = null;
	    }
        xmlHttp.open("get", url, false);
        xmlHttp.onreadystatechange = function() {
          if (xmlHttp.readyState == 4) {
          	if (xmlHttp.status == 200) {
          		time = xmlHttp.responseText;
            	over = true;
            }
          }
        };
        xmlHttp.send(null);
        if(over == true){
        	ouroverlib(STICKY,HEIGHT, 50,WIDTH,50,LEFT);
        }
      	return true;
	}
</script>