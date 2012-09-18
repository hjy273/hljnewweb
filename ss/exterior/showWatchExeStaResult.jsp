<%@ page language="java" contentType="text/html; charset=gbk"
	pageEncoding="gbk"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gbk">
<title>盯防月统计综合呈现</title>
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
function go(url) {
        createXMLHttpRequest();
        xmlHttp.open("post", url, false);
        //xmlHttp.onreadystatechange = startCallback;
        xmlHttp.send(null);
}
    
function lastMonth(){
  		var href="${ctx}/WatchExeAnalysisAction.do?method=getWatchExeStaResult";
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
		go(href);
		document.getElementById("FrmWatchExeSta").src = "${ctx}/exterior/showWatchExeStaRealResult.jsp";
}
function nextMonth(){
  var href="${ctx}/WatchExeAnalysisAction.do?method=getWatchExeStaResult";
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
		go(href);
		document.getElementById("FrmWatchExeSta").src = "${ctx}/exterior/showWatchExeStaRealResult.jsp";
}
//-->
</script>
</head>
	<body>
		<table  border="0" cellspacing="0" cellpadding="0" width="100%" height="7%">
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
		<table  border="0" cellspacing="0" cellpadding="0" width="100%" height="93%">
			<tr>
				<td>
				   <div>
                    <iframe id="FrmWatchExeSta" src="${ctx}/exterior/showWatchExeStaRealResult.jsp" marginWidth="0" marginHeight="0" frameBorder=0 width="100%" scrolling=auto height="100%"> </iframe>
                   </div>
				</td>
			</tr>
		</table>
	</body>
</html>
