<%@include file="/wap/header.jsp"%>
<%@ page language="java" contentType="text/html; charset=GBK"%>
<html>
	<head>
		<title>������ջ�������</title>
	</head>
	<body>
		<fmt:formatNumber value="${cutAcceptance.actualValue}" pattern="#.##"
			var="actualValue" />
		�����
		<c:out value="${actualValue }" />
		Ԫ
	</body>
</html>
