<%@ page language="java" contentType="text/html; charset=gbk"
	pageEncoding="gbk"%>
	<%@ page import = "com.cabletech.analysis.beans.HistoryWorkInfoConditionBean" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%
    //HistoryWorkInfoConditionBean bean = (HistoryWorkInfoConditionBean)request.getSession().getAttribute( "HistoryWorkInfoConBean" );
    //String strRangeID = bean.getRangeID();
    //String strRangeID = (String)request.getSession().getAttribute( "SMGraphicRangeID" );
    //String regionName = (String)request.getSession().getAttribute( "LOGIN_USER_REGION_NAME" );
    //String contractorName = (String)request.getSession().getAttribute( "LOGIN_USER_DEPT_NAME" );
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gbk">
<title>任务执行图例月统计合呈现</title>
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
function gorightnow(url) {
        createXMLHttpRequest();
        xmlHttp.open("post", url, false);
        //xmlHttp.onreadystatechange = startCallback;
        xmlHttp.send(null);
}

function startCallback() {
        if (xmlHttp.readyState == 4) {
            var pic_span = document.getElementById("pic");
        	pic_span.innerHTML = xmlHttp.responseText;
        }
    }
    
function lastMonth(){
  		var href="${ctx}/HistoryNoteAction.do?method=getNoteNumInfo";
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
		gorightnow(href);
		document.getElementById("FrmSMGraphic").src = "${ctx}/ShowHistorySMChart";
}
function nextMonth(){
  var href="${ctx}/HistoryNoteAction.do?method=getNoteNumInfo";
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
		document.getElementById("FrmSMGraphic").src = "${ctx}/ShowHistorySMChart";
}

	function go(select) {
        createXMLHttpRequest();
        var regionid = select.value;
        var url="${ctx}/HistoryNoteAction.do?method=getSMInfoByChangeRange&rangeID="+regionid+"&s=true";
        xmlHttp.open("get", url, true);
        xmlHttp.onreadystatechange = startCallback;
        xmlHttp.send(null);
    }
//-->
</script>
</head>
	<body>
	   <div align="center" width="100%">
         <font size="3"><strong>任务执行图例</strong></font>
       </div>
		<table class="ooib" id="obody" border="0" cellspacing="0" cellpadding="0" width="100%" height="7%">
			<tr>
				<td align="right">
					<input name="btnLastMonth" value="上个月" type="button" onclick="lastMonth();"/>
				</td>
				<td align="center">
					<span id="yearMonthText"><%=request.getAttribute("theyear")%>/<%=request.getAttribute("themonth")%>
					</span>
				</td>
				<td align="left">
					<input name="btnNextMonth" value="下个月" type="button" onclick="nextMonth();" />
				</td>
			</tr>
		</table>
		<div align="right" width="100%">
		<%@include file="common.jsp" %>
		</div>
	<div align="center" id="pic" style="display:">
       <%@ include file="..\analysis\commonHistorySMGraphic.jsp"%>
    </div>
	</body>
</html>
