<%@include file="/common/header.jsp"%>
<%@ page language="java" contentType="text/html; charset=GBK"%>
<html>
	<head>
		<title>查看割接申请</title>
		<META HTTP-EQUIV="expires" CONTENT="Wed, 26 Feb 1997 08:21:57 GMT">
		<META HTTP-EQUIV="expires" CONTENT="0">
		<script type="text/javascript">
	goBack = function() {
		var url = "${sessionScope.S_BACK_URL}";
		location = url;
	}
</script>
	</head>
	<body>
		<template:titile value="查看割接申请" />
		<html:form action="/wap/cutAction.do?method=readReply"
			styleId="addCutApply" enctype="multipart/form-data">
			<p>
				<jsp:include page="cut_apply_wap_base.jsp" />
			</p>
			<p>
				<input type="hidden" name="env" value="${env }">
				<input type="hidden" value="${cut.id }" name="cutId" />
				<html:submit property="button">已阅</html:submit>
				<html:button property="button"
					onclick="goBack();">返回待办</html:button>
			</p>
		</html:form>
	</body>
</html>
