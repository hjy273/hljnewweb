<%@include file="/wap/header.jsp"%>
<%@ page language="java" contentType="text/html; charset=GBK"%>
<html>
	<head>
		<title>�鿴�����������</title>
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
		<template:titile value="�鿴�����������" />
		<html:form action="/wap/drillPlanModifyAction.do?method=readReply"
			enctype="multipart/form-data">
			<p>
				<jsp:include page="drill_plan_wap_base.jsp" />
			</p>
			<p>
				<jsp:include page="drill_plan_modify_wap_base.jsp" />
			</p>
			<p>
				<input type="hidden" name="env" value="${env }" />
				<input type="hidden" name="modifyId" value="${drillPlanModify.id }" />
				<html:submit property="button">����</html:submit>
				<html:button property="button" onclick="goBack();">���ش���</html:button>
			</p>
		</html:form>
	</body>
</html>
