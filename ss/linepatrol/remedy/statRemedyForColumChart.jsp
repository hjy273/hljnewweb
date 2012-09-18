<%@ page language="java" contentType="text/html; charset=GBK"%> 
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<html>
	<head>
		<title>statRemedyForPieChart1.html</title>

		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="this is my page">
		<meta http-equiv="content-type" content="text/html; charset=UTF-8">

		<!--<link rel="stylesheet" type="text/css" href="./styles.css">-->

	</head>

	<body>
		<center>
			<object classid="clsid:D27CDB6E-AE6D-11cf-96B8-444553540000"
				id="report_process" width="100%" height="100%"
				codebase="http://fpdownload.macromedia.com/get/flashplayer/current/swflash.cab">
				<param name="movie" value="${ctx}/flex/ColumnChart.swf" />
				<param name="quality" value="high" />
				<param name="bgcolor" value="#ffffff" />
				<param name="allowScriptAccess" value="sameDomain" />
				<embed src="${ctx}/flex/ColumnChart.swf" quality="high"
					bgcolor="#ffffff" width="100%" height="100%" name="report_process"
					align="middle" play="true" loop="false" quality="high"
					allowScriptAccess="sameDomain" type="application/x-shockwave-flash"
					pluginspage="http://www.adobe.com/go/getflashplayer">
				</embed>
			</object>
		</center>
	</body>
</html>
				