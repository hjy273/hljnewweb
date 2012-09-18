<%@ include file="/common/header.jsp"%>
<%@ page import = "com.cabletech.analysis.util.ShowHistoryCurveChart" %>
<%@ page import = "com.cabletech.analysis.util.ShowHistoryZeroOneChart" %>
<%@ page import = "com.cabletech.baseinfo.domainobjects.UserInfo" %>
<%@ page import ="com.cabletech.analysis.util.ChartSize" %>
<%@ page import = "com.cabletech.analysis.beans.HistoryWorkInfoConditionBean" %>
<%@ page import = "java.io.PrintWriter" %>
<script language="JavaScript" src="../js/overlib.js"></script>

<%
    String flagGivenDate = (String)session.getAttribute("flagGivenDate");
    UserInfo userInfo = (UserInfo)request.getSession().getAttribute( "LOGIN_USER" );
    HistoryWorkInfoConditionBean bean = (HistoryWorkInfoConditionBean)request.getSession().getAttribute("HistoryWorkInfoConBean");
    String rangeID = bean.getRangeID();
    session.setAttribute("givenDate",flagGivenDate);
    String filename = "";
    if (!"0".equals(flagGivenDate) && "22".equals(userInfo.getType()) && !"22".equals(rangeID)){
    	filename = ShowHistoryZeroOneChart.generateZeroOnechart(session, new PrintWriter(out)); 
    }else{
    	filename = ShowHistoryCurveChart.generateXYChart(session, new PrintWriter(out));
	}
	System.out.println("filename:" + filename);
	String graphURL = request.getContextPath() + "/servlet/DisplayChart?filename=" + filename;
%>

<html>
<head>
<script language="javascript">
	var xmlHttp;
    function createXMLHttpRequest() {
        if (window.ActiveXObject) {
           xmlHttp = new ActiveXObject("Microsoft.XMLHTTP");
        } 
        else if (window.XMLHttpRequest) {
           xmlHttp = new XMLHttpRequest();                
        }
    }
	function overlib(strDate){	
	    var flag = false;
		createXMLHttpRequest();
		var url="${ctx}/WorkInfoHistoryAction.do?method=getOnlineInfoTime&strDateAndTime=" + strDate;
	    if ( strDate != null){
	       strDate = null;
	    }
        xmlHttp.open("GET", url, false);
        xmlHttp.onreadystatechange = function() {
          if (xmlHttp.readyState == 4) {
            strDate = xmlHttp.responseText;
            flag = true;
          }
        };
        xmlHttp.send(null);
	    //ouroverlib('Setting size and posiztion!', STICKY, '','Sticky!',HEIGHT, 100,WIDTH,120,LEFT);
	    if (flag == true){
	       ouroverlib(STICKY,HEIGHT, 100,WIDTH,100,LEFT);
	    }
      	return true;
      	//打开窗口
	}
</script>
</head>
<body >
	</br>
    <div align="center" width="100%">
	  <font size="3"><strong><%=flagGivenDate%>在线人数历史曲线</strong></font>
	</div>
	<br>
	</br>
	</br>
	</br>
	<% 
	 if(filename.indexOf("_nodata") != -1 || filename.indexOf("_error") != -1){
    %>
       <img src="${ctx}/images/public_nodata_500x300.png" width=580 height=380 border=0>
    <%
	 }else{
	%>
	<div align="center" style="display:">
       <img src="<%= graphURL %>" width=<%=ChartSize.WIDTH %> height=<%=ChartSize.HEIGHT %> border=0 usemap="#<%= filename %>">
    </div>
    <%
	 }
    %>
	
</body>
</html>