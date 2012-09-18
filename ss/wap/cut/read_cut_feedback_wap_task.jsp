<%@include file="/wap/header.jsp"%>
<%@page import="com.cabletech.linepatrol.cut.module.*"%>
<%@ page language="java" contentType="text/html; charset=GBK"%>
<html>
	<head>
		<title>查看反馈</title>
		<META HTTP-EQUIV="expires" CONTENT="Wed, 26 Feb 1997 08:21:57 GMT">
		<META HTTP-EQUIV="expires" CONTENT="0">
		<script type="text/javascript">
	goBack = function() {
		var url = "${sessionScope.S_BACK_URL}";
		location = url;
	}
</script>
	</head>
	<%
		Map map = (Map) request.getAttribute("map");
		Cut cutBean = (Cut) map.get("cut");
		CutFeedback cutFeedback = (CutFeedback) map.get("cutFeedback");
		String sublineIds = (String) map.get("sublineIds");
		request.setAttribute("cut", cutBean);
		request.setAttribute("cutFeedback", cutFeedback);
		request.setAttribute("sublineIds", sublineIds);
	%>
	<body>
		<template:titile value="查看反馈信息" />
		<html:form action="/wap/cutFeedbackAction.do?method=readReply"
			styleId="addCutFeedback" enctype="multipart/form-data">
			<p>
				<jsp:include page="cut_apply_wap_base.jsp" />
			</p>
			<p>
				<c:if test="${cutFeedback.feedbackType=='0'}">
					<jsp:include page="cut_feedback_wap_base.jsp" />
				</c:if>
				<c:if test="${cutFeedback.feedbackType=='1'}">
				取消原因：
				&nbsp;
				<c:out value="${cutFeedback.cancelCause}" />
				</c:if>
			</p>
			<p>
				<input type="hidden" name="env" value="${env }">
				<input type="hidden" name="cutFeedbackId" value="${cutFeedback.id }" />
				<html:submit property="button">已阅</html:submit>
				<html:button property="button"
					onclick="goBack();">返回待办</html:button>
			</p>
		</html:form>
	</body>
</html>
