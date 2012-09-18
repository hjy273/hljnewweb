<%@include file="/wap/header.jsp"%>
<%@ page language="java" contentType="text/html; charset=GBK"%>
<html>
	<head>
		<title>割接验收基本数据</title>
	</head>
	<body>
		<fmt:formatNumber value="${cutAcceptance.actualValue}" pattern="#.##"
			var="actualValue" />
		结算金额：
		<c:out value="${actualValue }" />
		元
	</body>
</html>
