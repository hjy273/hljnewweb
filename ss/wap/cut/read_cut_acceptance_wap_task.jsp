<%@include file="/wap/header.jsp"%>
<%@page import="com.cabletech.linepatrol.cut.module.*"%>
<%@ page language="java" contentType="text/html; charset=GBK"%>
<html>
	<head>
		<title>�鿴���ս���</title>
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
		Cut cut = (Cut) map.get("cut");
		CutFeedback cutFeedback = (CutFeedback) map.get("cutFeedback");
		CutAcceptance cutAcceptance = (CutAcceptance) map
				.get("cutAcceptance");
		String sublineIds = (String) map.get("sublineIds");
		request.setAttribute("cut", cut);
		request.setAttribute("cutFeedback", cutFeedback);
		request.setAttribute("cutAcceptance", cutAcceptance);
		request.setAttribute("sublineIds", sublineIds);
	%>
	<body>
		<template:titile value="�鿴���ս���" />
		<html:form action="/wap/cutAcceptanceAction.do?method=readReply"
			styleId="addAcceptanceForm" enctype="multipart/form-data">
			<p>
				<jsp:include page="cut_apply_wap_base.jsp" />
			</p>
			<p>
				<jsp:include page="cut_feedback_wap_base.jsp" />
			</p>
			<p>
				<jsp:include page="cut_acceptance_wap_base.jsp" />
			</p>
			<p>
				<input type="hidden" name="env" value="${env }">
				<input type="hidden" name="cutAcceptanceId"
					value="${cutAcceptance.id }" />
				<html:submit property="button">����</html:submit>
				<html:button property="button"
					onclick="goBack();">����</html:button>
			</p>
		</html:form>
	</body>
</html>
