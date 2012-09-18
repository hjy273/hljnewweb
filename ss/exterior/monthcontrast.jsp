
<%@ page language="java" contentType="text/html; charset=gbk"
	pageEncoding="gbk"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=gbk">
		<title>故障处理及时率综合呈现</title>
		<script type="text/javascript">
		var yearValue="<%=request.getAttribute("year")%>";
		var monthValue="<%=request.getAttribute("month")%>";
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
		function lastMonth() {
			var href="${ctx}/MonthlyContrastAction.do?method=getmMonthlyContrastForWhole";
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
			href = href + "&&year=" + year + "&&month=" + month;
			//chartImg.src = href + "&&random=" + Math.random();
			go(href);
			chartImg.src ="${ctx}/ShowComCompVChart"; 
		}
		function nextMonth() {
			var href="${ctx}/MonthlyContrastAction.do?method=getmMonthlyContrastForWhole";
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
			href = href + "&&year=" + year + "&&month=" + month;
			//chartImg.src = href + "&&random=" + Math.random();
			go(href);
			chartImg.src ="${ctx}/ShowComCompVChart"; 
		}
		</script>
	</head>
	<body>
		<table>
			<tr>
				<td align="right">
					<input name="btnLastMonth" value="上个月" type="button"
						onclick="lastMonth();" />
				</td>
				<td align="center">
					<span id="yearMonthText"><%=request.getAttribute("year")%>/<%=request.getAttribute("month")%>
					</span>
				</td>
				<td align="left">
					<input name="btnNextMonth" value="下个月" type="button"
						onclick="nextMonth();" />
				</td>
			</tr>
			<tr>
				<td colspan="3" id="imgTd">
					<input id="chartImg" type="image"
						src="${ctx}/ShowComCompVChart" />
				</td>
			</tr>
		</table>
	</body>
</html>
