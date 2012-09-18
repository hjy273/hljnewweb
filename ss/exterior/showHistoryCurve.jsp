<%@ page language="java" contentType="text/html; charset=gbk"
	pageEncoding="gbk"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ page import="java.io.PrintWriter"%>
<%@ page import="java.util.*" %>
<%@ page
	import="com.cabletech.analysis.beans.HistoryWorkInfoConditionBean"%>
<script language="JavaScript" src="../js/overlib.js"></script>
<%
	HistoryWorkInfoConditionBean bean = (HistoryWorkInfoConditionBean) request
			.getSession().getAttribute("HistoryWorkInfoConBean");
	String strRangeID = bean.getRangeID();
	//String regionName = (String) request.getSession().getAttribute(
			//"LOGIN_USER_REGION_NAME");
	//String contractorName = (String) request.getSession().getAttribute(
			//"LOGIN_USER_DEPT_NAME");
	session.setAttribute("givenDate", "0");
%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=gbk">
		<title>历史在线人数曲线月统计综合呈现</title>
		<script type="text/javascript">
<!--
    var yearValue="<%=request.getAttribute("theyear")%>";
	var monthValue="<%=request.getAttribute("themonth")%>";
	var xmlHttp;
    function createXMLHttpRequest() {
        if (window.ActiveXObject) {
           xmlHttp = new ActiveXObject("Microsoft.XMLHTTP");
        } 
        else if (window.XMLHttpRequest) {
           xmlHttp = new XMLHttpRequest();                
        }
    }

    function go(select) {
        var regionid = select.value;
        createXMLHttpRequest();
        var url="${ctx}/HistoryOnlineNumAction.do?method=getOnlineNumForDaysByChange&selectedRangeID="+regionid;
        xmlHttp.open("get", url, true);
        xmlHttp.onreadystatechange = startCallback;
        xmlHttp.send(null);
        //var pic_span = document.getElementById("FrmCurveNumSta");
		//pic_span.src = "${ctx}/ShowMonthlyOnlineNum";
        
    }
	function startCallback() {
        if (xmlHttp.readyState == 4) {
            var pic_span = document.getElementById("pic");
        	pic_span.innerHTML = xmlHttp.responseText;
        }
    }
function gorightnow(url) {
        createXMLHttpRequest();
        xmlHttp.open("post", url, false);
        //xmlHttp.onreadystatechange = startCallback;
        xmlHttp.send(null);
}

    function initRange(){
	    //var strRangeID = '<%=strRangeID%>';
	    //document.getElementById("rangeID").value= strRangeID;
	    //changeCurve(strRangeID);
	}

	function overlib(strDate){
	    var flag = false;	
		createXMLHttpRequest();
		var url="${ctx}/HistoryOnlineNumAction.do?method=getOnlineInfo&strDate=" + strDate + "&rangeID=" + document.getElementById("rangeID").value;
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
	       ouroverlib(STICKY,HEIGHT, 50,WIDTH,50,LEFT);
	    }
      	return true;
      	//打开窗口
	}
	   
function lastMonth(){
  		var href="${ctx}/HistoryOnlineNumAction.do?method=getOnlineNum";
		var year = parseInt(yearValue);
		var month = parseInt(monthValue);
		month = month - 1;
		if (month <= 0) {
			month = 12;
			year = year - 1;
		}
		yearMonthText.innerText = year + "/";
		if (month < 10) {
			yearMonthText.innerText = yearMonthText.innerText + "0" + month;
		} else {
			yearMonthText.innerText = yearMonthText.innerText + month;
		}
		yearValue = year;
		monthValue = month;
		href = href + "&&theyear=" + year + "&&themonth=" + month;
		//alert(href);
		gorightnow(href);
		var pic_span = document.getElementById("FrmCurveNumSta");
		pic_span.src = "${ctx}/ShowMonthlyOnlineNum";
		//alert(pic_span.src);
		
		
}
function nextMonth(){
        var href="${ctx}/HistoryOnlineNumAction.do?method=getOnlineNum";
		var year = parseInt(yearValue);
		var month = parseInt(monthValue);
		month = month + 1;
		if (month >= 13) {
			month = 1;
			year = year + 1;
		}
		yearMonthText.innerText = year + "/";
		if (month < 10) {
			yearMonthText.innerText = yearMonthText.innerText + "0" + month;
		} else {
			yearMonthText.innerText = yearMonthText.innerText + month;
		}
		yearValue = year;
		monthValue = month;
		href = href + "&&theyear=" + year + "&&themonth=" + month;
		gorightnow(href);
		var pic_span = document.getElementById("FrmCurveNumSta");
		pic_span.src = "${ctx}/ShowMonthlyOnlineNum";
		//alert(pic_span.src);
}

//-->
</script>
<meta http-equiv="Pragma" contect="no-cache">
	</head>
	<body onload="initRange()">
		<div align="center" width="100%">
			<font size="3"><strong>在线人数历史曲线</strong>
			</font>
		</div>
		</br>
		<table class="ooib" id="obody" border="0" cellspacing="0"
			cellpadding="0" width="100%" height="7%">
			<tr>
				<td align="right">
					<input name="btnLastMonth" value="上个月" type="button"
						onclick="lastMonth();" />
				</td>
				<td align="center">
					<span id="yearMonthText"><%=request.getAttribute("theyear")%>/<%=request.getAttribute("themonth")%>
					</span>
				</td>
				<td align="left">
					<input name="btnNextMonth" value="下个月" type="button"
						onclick="nextMonth();" />
				</td>
			</tr>
		</table>
		 
        <div align="right" width="100%">
		<%@include file="common.jsp" %>
		</div>

	<div align="center" id="pic" style="display:">
       <%@ include file="showHistoryCurveChart.jsp"%>
    </div>
		
	
	</body>
</html>
